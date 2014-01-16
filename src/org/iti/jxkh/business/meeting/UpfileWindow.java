package org.iti.jxkh.business.meeting;

import java.util.Date;

import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zk.ui.util.Configuration;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.iti.common.util.ConvertUtil;
import com.iti.common.util.UploadUtil;
import com.uniwin.common.util.DateUtil;

public class UpfileWindow extends Window implements AfterCompose {

	/**
	 * @CXX
	 */
	private static final long serialVersionUID = 379536472648629464L;

	private Textbox fileTitle;
	private Media media;
	private Label hasUp;

	public Media getMedia() {
		return media;
	}

	public void setMedia(Media media) {
		this.media = media;
	}

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		hasUp.setVisible(false);
	}

	public void onClick$up() {
		Desktop desktop = this.getDesktop();
		Configuration conf = desktop.getWebApp().getConfiguration();
		conf.setMaxUploadSize(1024 * 10);
		conf.setUploadCharset("GBK");
		try {
			media = Fileupload.get();
			if (null == media) {
				hasUp.setValue("�ϴ�ʧ��");
				hasUp.setVisible(true);
				return;
			}else {
				hasUp.setValue("�ϴ��ɹ�");
				hasUp.setVisible(true);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// �������水ť���������¼�
	public void onClick$submit() {
		if (fileTitle.getValue() == null || fileTitle.getValue() == "") {
			try {
				Messagebox.show("����д�ļ����⣡", "��ʾ", Messagebox.OK,
						Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			fileTitle.focus();
			return;
		}
		if (media == null) {
			try {
				Messagebox.show("���ϴ��ļ���", "��ʾ", Messagebox.OK,
						Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		String path = "";
		String fileName = DateUtil.getDateTimeString(new Date());// �ĵ����������ϴ���ʱ�����
		try {
			path = UploadUtil.saveFile(
					media,
					"\\jxkh\\files\\" + fileName,
					media.getName().substring(0,
							media.getName().lastIndexOf(".")), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Executions.getCurrent().setAttribute("path", path);
		String title = fileTitle.getValue();// �ĵ�����������
		Executions.getCurrent().setAttribute("title", title);
		String upTime = ConvertUtil.convertDateAndTimeString(new Date());
		Executions.getCurrent().setAttribute("upTime", upTime);
		Events.postEvent(Events.ON_CHANGE, this, null);
		this.detach();
	}

	/**
	 * <li>�����������رյ�ǰ���ڡ�
	 */
	public void onClick$close() {
		this.onClose();
	}

}
