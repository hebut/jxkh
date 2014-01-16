package org.iti.xypt.personal.notice;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.iti.xypt.entity.XyMReceiver;
import org.iti.xypt.entity.XyMReceiverId;
import org.iti.xypt.entity.XyMessage;
import org.iti.xypt.service.MessageService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Html;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

import com.uniwin.common.util.ConvertUtil;
import com.uniwin.framework.entity.WkTUser;

public class NoticeViewWindow extends Window implements AfterCompose {
	// 消息数据接口
	private MessageService messageService;
	// 消息输入框
	private Label xmSubject, xmReceive, xmSource, xmSendTime;
	// Html标签显示内容
	private Html xmContent;
	// 消息实体
	private XyMessage notice;
	// 行
	private Row rowFile;
	// list模型
	Listbox downList;
	List fileNameList = new ArrayList();;
	ListModelList modelListbox;
	// 收消息列表数据接口
	WkTUser wkTUser;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		wkTUser = (WkTUser) Sessions.getCurrent().getAttribute("user");
		modelListbox = new ListModelList(fileNameList);
		downList.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				arg0.setValue(arg1);
				File f = (File) arg1;
				arg0.setValue(arg1);
				arg0.setLabel(f.getName() + "   " + "(" + f.length() / 1024 + "KB" + ")");
			}
		});
		downList.setModel(modelListbox);
	}

	/**
	 * <Li>用于在perReceive页面加载数据，初始化窗口 2010-4-8
	 * 
	 * @author bobo
	 * 
	 */
	public void initWindow(XyMessage notice) throws IOException {
		this.notice = notice;
		xmSubject.setValue(notice.getXmSubject());
		xmSource.setValue((notice.getDept() == null ? "" : notice.getDept().getKdName()) + " " + (notice.getRole() == null ? "" : notice.getRole().getKrName()) + " " + notice.getXmSender());
		xmReceive.setValue(notice.getXmReceivers());
		xmContent.setContent(notice.getXmContent());
		xmSendTime.setValue(ConvertUtil.convertDateAndTimeString(notice.getXmSndtime()));
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
						modelListbox.add(readfile);
					}
				}
			}
		}
		XyMReceiverId xymId = new XyMReceiverId(notice.getXmId(), wkTUser.getKuId());
		XyMReceiver xym = (XyMReceiver) messageService.get(XyMReceiver.class, xymId);
		if (xym == null) {
			xym = new XyMReceiver(xymId, XyMReceiver.STATE_READED);
			messageService.save(xym);
		}
	}

	/**
	 * <Li>ZK组件下载附件 2010-4-11
	 * 
	 * @author bobo
	 * @throws FileNotFoundException
	 * 
	 */
	public void onClick$download() throws InterruptedException, FileNotFoundException {
		Listitem it = downList.getSelectedItem();
		if (it == null) {
			if (modelListbox.getSize() > 0) {
				it = downList.getItemAtIndex(0);
			} else {
				return;
			}
		}
		File f = (File) it.getValue();
		Filedownload.save(f, null);
	}

	public void onClick$concel() {
		this.detach();
	}

	public ListModelList getModelList() {
		return modelListbox;
	}
}
