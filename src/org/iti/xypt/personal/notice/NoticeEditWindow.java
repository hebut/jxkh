package org.iti.xypt.personal.notice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.iti.xypt.entity.XyMessage;
import org.iti.xypt.service.MessageService;
import org.iti.xypt.service.XyUserRoleService;
import org.zkforge.fckez.FCKeditor;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.RoleService;

public class NoticeEditWindow extends Window implements AfterCompose {
	// 消息数据接口
	private MessageService messageService;
	// 消息输入框组件
	private Textbox mlSubject;
	private FCKeditor mlContent;
	private Row rowFile;
	Listbox upList;
	ListModelList fileModel;
	WkTUser wkTUser;
	XyMessage notice;
	XyUserRoleService xyUserRoleService;
	RoleService roleService;
	List fileNameList = new ArrayList();
	Label nTarget;
	// 新增加关键词2010年8月27日19:51:58
	Combobox keyword;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		wkTUser = (WkTUser) Sessions.getCurrent().getAttribute("user");
		upList.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				arg0.setValue(arg1);
				if (arg1 instanceof Media) {
					Media name = (Media) arg1;
					arg0.setLabel(name.getName());
				} else {
					File f = (File) arg1;
					arg0.setLabel(f.getName());
				}
			}
		});
		keyword.setModel(new ListModelList(messageService.getKeywords(wkTUser.getKuId())));
		fileModel = new ListModelList(fileNameList);
		upList.setModel(fileModel);
	}

	public void initWindow(XyMessage notice) {
		this.notice = notice;
		mlSubject.setValue(notice.getXmSubject());
		mlContent.setValue(notice.getXmContent());
		nTarget.setValue(notice.getXmReceivers());
		keyword.setValue(notice.getXmKeyword());
		if (notice.getXmHfile().shortValue() == XyMessage.HFILE_NO.shortValue()) {
			rowFile.setVisible(false);
		} else {
			rowFile.setVisible(true);
			String basepath = Executions.getCurrent().getDesktop().getWebApp().getRealPath(XyMessage.BASE_FILE);
			// 给查看消息页面的附件列表框modelListbox加载数据
			File folder = new File(basepath + "\\" + notice.getReleativeFilePath());
			String[] filelist = folder.list();
			if (filelist != null && filelist.length != 0) {
				for (int i = 0; i < filelist.length; i++) {
					File readfile = new File(folder.getAbsolutePath() + "\\" + filelist[i]);
					if (readfile.exists()) {
						fileModel.add(readfile);
					}
				}
			}
		}
	}

	public void onClick$upFile() throws InterruptedException {
		Object media = Fileupload.get();
		if (media == null) {
			return;
		}
		rowFile.setVisible(true);
		fileModel.add(media);
	}

	/**
	 * <li>新消息的 “发送” 功能
	 * 
	 * @author bobo 2010-03-01
	 */
	public void onClick$save() throws IOException, InterruptedException {
		if (mlSubject.getValue().equals("") || mlSubject.getValue().getBytes().length > 40) {
			throw new WrongValueException(mlSubject, "请您检查标题，标题必须填写，并且不能超过50个字符(25个汉字)！");
		}
		notice.setXmSubject(mlSubject.getText());
		notice.setXmContent(mlContent.getValue());
		notice.setXmKeyword(keyword.getValue());
		if (fileModel.getInnerList().size() > 0) {
			notice.setXmHfile(XyMessage.HFILE_YES);
		}
		messageService.updateNotice(notice);
		List flist = fileModel.getInnerList();
		for (int i = 0; i < flist.size(); i++) {
			if (flist.get(i) instanceof Media) {
				Media media = (Media) flist.get(i);
				saveToFile(media, notice);
			}
		}
		Messagebox.show("更新成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	/**
	 * <li>使用ZK上传组件，将上传的文件保存到硬盘,获取消息ID号创建文件夹
	 * 
	 * @param Media
	 *            media 附件
	 * @param String
	 *            mlid 消息的Id号
	 * @author bobo 2010-4-11
	 */
	public void saveToFile(Media media, XyMessage message) throws IOException {
		if (media != null) {
			String fileName = media.getName();
			String basePath = Executions.getCurrent().getDesktop().getWebApp().getRealPath(XyMessage.BASE_FILE);
			File folder = new File(basePath + "\\" + message.getReleativeFilePath());
			// 如果Mlid文件夹不存在，用于在upload文件下创建Mlid号的文件夹
			if (!folder.exists()) {
				folder.mkdirs();
			}
			String path = folder.getAbsolutePath() + "\\" + fileName;
			File newFile = new File(path);
			if (newFile.exists()) {
				newFile.delete();
			} else {
				newFile.createNewFile();
			}
			OutputStream out = new FileOutputStream(newFile);
			InputStream objin = media.getStreamData();
			byte[] buf = new byte[1024];
			int len;
			long finallen = 0L;
			while ((len = objin.read(buf)) > 0) {
				out.write(buf, 0, len);
				finallen = finallen + len;
			}
			out.close();
			objin.close();
		}
	}

	/**
	 * <li>删除上传的文件，重新选择
	 * 
	 * @author bobo 2010-4-11
	 */
	public void onClick$deUpload() {
		Listitem it = upList.getSelectedItem();
		if (it == null) {
			if (fileModel.getSize() > 0) {
				it = upList.getItemAtIndex(0);
			}
		}
		if (it.getValue() instanceof File) {
			File f = (File) it.getValue();
			f.delete();
		}
		fileModel.remove(it.getValue());
		if (fileModel.getSize() == 0) {
			rowFile.setVisible(false);
		}
	}

	/**
	 * <li>页面输入框进行重置
	 * 
	 * @author bobo 2010-4-11
	 */
	public void onClick$reSend() {
		initWindow(notice);
	}

	public void onClick$close() {
		this.detach();
	}
}
