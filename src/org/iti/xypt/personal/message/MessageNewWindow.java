package org.iti.xypt.personal.message;

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
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
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
import com.uniwin.framework.entity.WkTUser;

public class MessageNewWindow extends Window implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6583512750938805591L;
	// ��Ϣ���ݽӿ�
	private MessageService messageService;
	// ��Ϣ��������
	private Textbox mlSubject, user_to;
	private FCKeditor mlContent;
	// ����ռ��˰�ť
	private Button addUser;
	// �ݴ��ռ����б�
	List recelist = new ArrayList();
	private Row rowFile;
	Listbox upList;
	ListModelList fileModel;
	WkTUser wkTUser;
	// �����ӹؼ���2010��8��27��19:51:58
	Combobox keyword;

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
		// ����ռ���
		addUser.addEventListener(Events.ON_CLICK, new EventListener() {
			public void onEvent(Event event) throws Exception {
				MessageSelectUserWindow addwin = (MessageSelectUserWindow) Executions.createComponents("/admin/personal/message/new/selectUser.zul", null, null);
				addwin.doHighlighted();
				addwin.setTitle("ѡ���ռ���");
				addwin.initWindow(recelist);
				addwin.addEventListener(Events.ON_CHANGE, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						MessageSelectUserWindow addwin = (MessageSelectUserWindow) arg0.getTarget();
						addReceiver(addwin.getSelectUsers());
						addwin.detach();
					}
				});
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
			WkTUser u = (WkTUser) recelist.get(i);
			sb.append(u.getKuName());
			if (i < (recelist.size() - 1)) {
				sb.append(",");
			}
		}
		user_to.setValue(sb.toString());
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
	 * <li>����Ϣ�� �����͡� ����
	 * 
	 * @author bobo 2010-03-01
	 */
	public void onClick$save() throws IOException, InterruptedException {
		if (mlSubject.getValue().equals("") || mlSubject.getValue().getBytes().length > 40) {
			Messagebox.show("���������⣬���������д�����Ҳ��ܳ���50���ַ�(25������)��", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			mlSubject.focus();
			return;
		} else if (user_to.getValue().equals("") && user_to.getValue() != null) {
			Messagebox.show("����ѡ���ռ��ˣ��ռ��˲���Ϊ�գ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			user_to.focus();
			return;
		} else {
			XyMessage msg = new XyMessage();
			msg.setXmKeyword(keyword.getValue());
			msg.setXmType(XyMessage.TYPE_MESSAGE);
			msg.setKuId(wkTUser.getKuId());
			msg.setXmSubject(mlSubject.getText());
			msg.setXmSender(wkTUser.getKuName());
			msg.setXmContent(mlContent.getValue());
			msg.setXmReceivers(user_to.getValue());
			if (fileModel.getInnerList().size() > 0) {
				msg.setXmHfile(XyMessage.HFILE_YES);
			}
			messageService.save(msg);
			for (int i = 0; i < recelist.size(); i++) {
				WkTUser u = (WkTUser) recelist.get(i);
				XyMReceiverId mrid = new XyMReceiverId(msg.getXmId(), u.getKuId());
				XyMReceiver mr = new XyMReceiver(mrid, XyMReceiver.STATE_UNREAD);
				messageService.save(mr);
			}
			Messagebox.show("���ͳɹ���", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			List flist = fileModel.getInnerList();
			for (int i = 0; i < flist.size(); i++) {
				Media media = (Media) flist.get(i);
				saveToFile(media, msg);
			}
			onClick$reSend();
			Events.postEvent(Events.ON_CHANGE, this, null);
		}// else �ռ��˲���Ϊ��
	}

	/**
	 * <li>ʹ��ZK�ϴ���������ϴ����ļ����浽Ӳ��,��ȡ��ϢID�Ŵ����ļ���
	 * 
	 * @param Media
	 *            media ����
	 * @param String
	 *            mlid ��Ϣ��Id��
	 * @author bobo 2010-4-11
	 */
	public void saveToFile(Media media, XyMessage message) throws IOException {
		if (media != null) {
			String fileName = media.getName();
			String basePath = Executions.getCurrent().getDesktop().getWebApp().getRealPath(XyMessage.BASE_FILE);
			File folder = new File(basePath + "\\" + message.getReleativeFilePath());
			// ���Mlid�ļ��в����ڣ�������upload�ļ��´���Mlid�ŵ��ļ���
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

	/**
	 * <li>ɾ���ϴ����ļ�������ѡ��
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
		if (fileModel.getSize() == 1) {
			fileModel.remove(it.getValue());
			rowFile.setVisible(false);
		} else if (fileModel.getSize() > 1) {
			fileModel.remove(it.getValue());
		}
	}

	/**
	 * <li>ҳ��������������
	 * 
	 * @author bobo 2010-4-11
	 */
	public void onClick$reSend() {
		rowFile.setVisible(false);
		mlSubject.setValue("");
		user_to.setValue("");
		mlContent.setValue("");
		fileModel.clear();
		recelist = new ArrayList();
	}
}
