package com.uniwin.framework.ui.title;

/**
 * <li>��������ҳ�����������Ӧҳ��admin\system\title\titleNew.zul
 * @author DaLei
 * @2010-3-15
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.uniwin.common.util.DateUtil;
import com.uniwin.framework.common.listbox.TitleListbox;
import com.uniwin.framework.entity.WkTMlog;
import com.uniwin.framework.entity.WkTTitle;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.MLogService;
import com.uniwin.framework.service.TitleService;

public class TitleNewWindow extends Window implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Ĭ�ϵ��¼ӱ��⸸�������
	WkTTitle editTitle;
	// ����ҳ���еı༭��
	Textbox ktname, ktsecu, ktcontent;
	Listbox kttype;
	Checkbox newWindow;
	Intbox ktOrder;
	Button upImage;
	// �������ݷ��ʽӿ�
	TitleService titleService;
	// ������ѡ�����
	TitleListbox pselect;
	// ����ͼƬ��ʾ����
	Hbox pics;
	WkTUser user;
	MLogService mlogService;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
	}

	public WkTTitle getEditTitle() {
		return editTitle;
	}

	public void onClick$upImage() throws InterruptedException {
		Media media = null;
		try {
			media = Fileupload.get();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		if (media instanceof org.zkoss.image.Image) {
			pics.getChildren().clear();
			org.zkoss.zul.Image image = new org.zkoss.zul.Image();
			image.setContent((org.zkoss.image.Image) media);
			image.setWidth("25px");
			image.setHeight("25px");
			image.setParent(pics);
			Button b = new Button();
			b.setLabel("ɾ��");
			b.addEventListener(Events.ON_CLICK, new org.zkoss.zk.ui.event.EventListener() {
				public void onEvent(Event event) throws Exception {
					pics.getChildren().clear();
				}
			});
			b.setParent(pics);
		} else {
			Messagebox.show("��ѡ��ͼƬ�ϴ���", "�ϴ�����", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@SuppressWarnings("unchecked")
	public void onClick$submit() throws InterruptedException, IOException {
		WkTTitle newTitle = new WkTTitle();
		newTitle.setKtName(ktname.getValue());
		newTitle.setKtSecurity(ktsecu.getValue());
		newTitle.setKtOrdno(Long.parseLong(ktOrder.getValue() + ""));
		newTitle.setKtContent(ktcontent.getValue());
		switch (kttype.getSelectedIndex()) {
		case 0:
			newTitle.setKtType("21");
			break;
		case 1:
			newTitle.setKtType("22");
			break;
		case 2:
			newTitle.setKtType("23");
			break;
		default:
			break;
		}
		if (newWindow.isChecked()) {
			newTitle.setKtNewwin("1");
		} else {
			newTitle.setKtNewwin("0");
		}
		WkTTitle t = (WkTTitle) pselect.getSelectedItem().getValue();
		newTitle.setKtPid(t.getKtId());
		newTitle.setKsId(1L);
		titleService.save(newTitle);
		// ����ͼ��
		List<Image> clist = pics.getChildren();
		if (clist.size() > 0) {
			Image img = (Image) clist.get(0);
			byte[] out = img.getContent().getByteData();
			String repSrc = Sessions.getCurrent().getWebApp().getRealPath("/upload/title");
			String fileName = editTitle.getKtId() + "_" + DateUtil.getDateTimeString(new Date()) + img.getContent().getName().substring(img.getContent().getName().indexOf("."));
			String path = repSrc + "\\" + fileName;
			System.out.println(path);
			//File f = new File(path);
			FileOutputStream fos = new FileOutputStream(new File(path));
			fos.write(out);
			fos.close();
			newTitle.setKtImage(fileName);
			titleService.update(newTitle);
		}
		mlogService.saveMLog(WkTMlog.FUNC_ADMIN, "�½����⣬id:" + newTitle.getKtId(), user);
		if (Messagebox.show("����ɹ�,�Ƿ���������?", "�½�����", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == 1) {
			Events.postEvent(Events.ON_CHANGE, this, null);
		} else {
			this.detach();
		}
	}

	public void onClick$reset() {
		initWindow(getEditTitle());
		ktname.setRawValue(null);
		ktsecu.setRawValue(null);
		ktcontent.setRawValue(null);
		newWindow.setChecked(false);
		ktOrder.setRawValue(null);
	}

	public void onClick$close() {
		this.detach();
	}

	/**
	 * <li>������������ʼ���½����ⴰ�ڣ���Ҫ��ʼ��������ѡ�����
	 * 
	 * @param editTitle
	 *            Ĭ��ѡ��ĸ����� void
	 * @author DaLei
	 * @2010-3-15
	 */
	public void initWindow(WkTTitle editTitle) {
		this.editTitle = editTitle;
		pselect.initNewTitleSelect(editTitle);
		if (editTitle == null) {
			pselect.setSelectedIndex(0);
		}
	}
}
