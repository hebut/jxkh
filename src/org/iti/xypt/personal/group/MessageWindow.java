package org.iti.xypt.personal.group;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.iti.xypt.entity.XyMReceiver;
import org.iti.xypt.entity.XyMReceiverId;
import org.iti.xypt.entity.XyMessage;
import org.iti.xypt.service.MessageService;
import org.zkforge.fckez.FCKeditor;
import org.zkoss.io.Files;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.uniwin.asm.personal.entity.QzMember;
import com.uniwin.framework.entity.WkTUser;

public class MessageWindow extends Window implements AfterCompose {
	Textbox mlSubject;
	FCKeditor mlContent;
	Row rowFile;
	Listbox upList;
	ListModelList fileModel;
	WkTUser wkTUser;
	Combobox keyword;
	MessageService messageService;
	String group_member;
	List recelist = new ArrayList();

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		wkTUser = (WkTUser) Sessions.getCurrent().getAttribute("user");
		upList.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				Media name = (Media) arg1;
				arg0.setValue(arg1);
				arg0.setLabel(name.getName());
			}
		});
		keyword.setModel(new ListModelList(messageService.getKeywords(wkTUser.getKuId())));
		fileModel = new ListModelList(new ArrayList());
		upList.setModel(fileModel);
	}

	public void addReceiver(List userList) {
		recelist = userList;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < recelist.size(); i++) {
			QzMember member = (QzMember) recelist.get(i);
			WkTUser user = (WkTUser) messageService.get(WkTUser.class, member.getMbMember());
			sb.append(user.getKuName());
			if (i < (recelist.size() - 1)) {
				sb.append(",");
			}
		}
		group_member = sb.toString();
		// System.out.println("群发至：" + group_member);
	}

	public void onClick$upFile() throws InterruptedException {
		Object media = Fileupload.get();
		if (media == null) {
			return;
		}
		rowFile.setVisible(true);
		fileModel.add(media);
	}

	public void onClick$save() throws IOException, InterruptedException {
		if (mlSubject.getValue().equals("") || mlSubject.getValue().getBytes().length > 40) {
			Messagebox.show("请您检查标题，标题必须填写，并且不能超过50个字符(25个汉字)！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			mlSubject.focus();
			return;
		} else if (group_member.equals("") && group_member != null) {
			Messagebox.show("该群组暂无成员！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			return;
		} else {
			XyMessage msg = new XyMessage();
			msg.setXmKeyword(keyword.getValue());
			msg.setXmType(XyMessage.TYPE_MESSAGE);
			msg.setKuId(wkTUser.getKuId());
			msg.setXmSubject(mlSubject.getText());
			msg.setXmSender(wkTUser.getKuName());
			msg.setXmContent(mlContent.getValue());
			msg.setXmReceivers(group_member);
			if (fileModel.getInnerList().size() > 0) {
				msg.setXmHfile(XyMessage.HFILE_YES);
			}
			messageService.save(msg);
			for (int i = 0; i < recelist.size(); i++) {
				QzMember member = (QzMember) recelist.get(i);
				XyMReceiverId mrid = new XyMReceiverId(msg.getXmId(), member.getMbMember());
				XyMReceiver mr = new XyMReceiver(mrid, XyMReceiver.STATE_UNREAD);
				messageService.save(mr);
			}
			Messagebox.show("发送成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			List flist = fileModel.getInnerList();
			for (int i = 0; i < flist.size(); i++) {
				Media media = (Media) flist.get(i);
				saveToFile(media, msg);
			}
			onClick$reSend();
			this.detach();
		}
	}

	public void saveToFile(Media media, XyMessage message) throws IOException {
		if (media != null) {
			String fileName = media.getName();
			String basePath = Executions.getCurrent().getDesktop().getWebApp().getRealPath(XyMessage.BASE_FILE);
			File folder = new File(basePath + "\\" + message.getReleativeFilePath());
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
			if (fileName.endsWith(".txt") || fileName.endsWith(".project")) {
				Reader r = media.getReaderData();
				File f = new File(path);
				Files.copy(f, r, null);
				Files.close(r);
			} else {
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
	}

	public void onClick$deUpload() {
		Listitem it = upList.getSelectedItem();
		if (it == null) {
			if (fileModel.getSize() > 0) {
				it = upList.getItemAtIndex(0);
			}
		}
		if (fileModel.getSize() == 1) {
			fileModel.remove(it.getValue());
			rowFile.setVisible(false);
		} else if (fileModel.getSize() > 1) {
			fileModel.remove(it.getValue());
		}
	}

	public void onClick$reSend() {
		rowFile.setVisible(false);
		mlSubject.setValue("");
		mlContent.setValue("");
		fileModel.clear();
		recelist = new ArrayList();
		group_member = "";
	}
}
