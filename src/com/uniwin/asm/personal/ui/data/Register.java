package com.uniwin.asm.personal.ui.data;

/**
 * <li>�û�ע�����,��Ӧҳ��admin/persongal/data/register/index.zul
 * @author bobo
 * @date 2010-3-12
 */

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import com.uniwin.common.util.ConvertUtil;
import com.uniwin.common.util.DateUtil;
import com.uniwin.common.util.IPUtil;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

public class Register extends Window implements AfterCompose {
	private static final long serialVersionUID = -8080661133741540227L;
	// �û����ݷ��ʽӿ�
	private UserService userService;
	// �û���������
	private Textbox kuName, kuAnswer, kuQuestion, kuEmail, kuPhone, kuCompany, uBandIp, kuIdentity, kuPolitical, kuEducational, kuSchool;
	private Label kuLid;
	// �û�ѡ������
	private Checkbox kuAutoenter;
	// �û�������������
	private Datebox kuBirthday;
	// �û�ѡ������
	private Radiogroup kuSex;
	// �û������б�����
	private Listbox kuStyle, uStatus, bangType;
	// �û�ʵ��
	WkTUser user;
	// ���ð�ť
	private Button reset;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		bangType.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				bangTypeHandle();
			}
		});
		initUser(); // ���ó�ʼ�����ں���
	}

	/**
	 * <li>������������ʼ�� registerҳ�����ݡ� void
	 * 
	 * @author bobo
	 * @2010-3-13
	 */
	public void initUser() {
		kuLid.setValue(user.getKuLid());
		kuName.setValue(user.getKuName());
		kuAnswer.setValue(user.getKuAnswer());
		kuQuestion.setValue(user.getKuQuestion());
		kuEmail.setValue(user.getKuEmail().trim());
		kuPhone.setValue(user.getKuPhone().trim());
		if (user.getKuBirthday() != null && user.getKuBirthday().length() > 0) {
			kuBirthday.setValue(ConvertUtil.convertDate(DateUtil.getDateString(user.getKuBirthday())));
		}
		if (user.getKuSex().trim().equalsIgnoreCase("2")) { // �Ա����ַ���Ҫȥ�ո񣬷�������ʧ��
			kuSex.setSelectedIndex(1);
		} else {
			kuSex.setSelectedIndex(0);
		}
		kuIdentity.setValue(user.getKuIdentity());
		kuPolitical.setValue(user.getKuPolitical());
		kuEducational.setValue(user.getKuEducational());
		kuSchool.setValue(user.getKuSchool());
		kuCompany.setValue(user.getKuCompany());
		uBandIp.setValue(user.getKuBindaddr());
		bangType.setSelectedIndex(Integer.valueOf(user.getKuBindtype().trim()));
		bangTypeHandle(); // ���ú���
		if (user.getKuAutoenter().trim().equalsIgnoreCase("1")) {
			kuAutoenter.setChecked(true);
		} else {
			kuAutoenter.setChecked(false);
		}
		kuStyle.setSelectedIndex(0);
	}

	/**
	 * <li>�����������û���Ϣ���¹��ܡ� void
	 * 
	 * @author bobo
	 * @throws InterruptedException
	 * @2010-3-1
	 */
	public void onClick$save() throws InterruptedException {
		if (kuName.getValue().equals("")) {
			Messagebox.show("�û�������Ϊ�գ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			kuName.focus();
			return;
		}
		if (kuEmail.getValue().equals("")) {
			Messagebox.show("�������䲻��Ϊ�գ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			kuEmail.focus();
			return;
		}
		if (kuIdentity.getValue().equals("")) {
			Messagebox.show("���֤�Ų���Ϊ�գ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			kuIdentity.focus();
			return;
		}
		if (kuPhone.getValue().equals("")) {
			Messagebox.show("�绰����Ϊ�գ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			kuPhone.focus();
			return;
		}
		if (kuPolitical.getValue().equals("")) {
			Messagebox.show("������ò����Ϊ�գ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			kuPolitical.focus();
			return;
		}
		user.setKuName(kuName.getText());
		user.setKuAnswer(kuAnswer.getText());
		user.setKuQuestion(kuQuestion.getText());
		user.setKuEmail(kuEmail.getText());
		user.setKuPhone(kuPhone.getText());
		user.setKuPolitical(kuPolitical.getText());
		user.setKuIdentity(kuIdentity.getText());
		user.setKuEducational(kuEducational.getText());
		user.setKuSchool(kuSchool.getText());
		if (kuBirthday.getValue() != null) {
			user.setKuBirthday(DateUtil.getDateString(kuBirthday.getValue()));
		}
		user.setKuCompany(kuCompany.getText());
		if (kuSex.getSelectedIndex() == 0) {
			user.setKuSex("1"); // 1�����С���2����Ů��
		} else {
			user.setKuSex("2");
		}
		// ����û�ѡ�񲻰󶨣��������䲻���Զ���½��ͬʱ����IP��ַ���ÿ�.
		// ����û�ѡ��IP�󶨣��������ð󶨵�IP��ַ���������������Ϊ����IP���������ø��û��ϴ���½IP��ַ��
		// ѡ��IP�󶨲������ð�IP���ж��û��Ƿ������Զ���½
		if (bangType.getSelectedIndex() == 0) {// ѡ�񲻰�
			user.setKuBindtype(WkTUser.BAND_NO);
			user.setKuAutoenter(WkTUser.AUTOENTER_NO);
			user.setKuBindaddr("");
		} else {
			user.setKuBindtype(WkTUser.BAND_YES);
			try {
				IPUtil.getIPLong(uBandIp.getValue());
				user.setKuBindaddr(uBandIp.getValue());
			} catch (Exception e) {
				throw new WrongValueException(uBandIp, "�󶨵�ַ�������!");
			}
			if (kuAutoenter.isChecked()) {
				user.setKuAutoenter(WkTUser.AUTOENTER_YES);
			} else {
				user.setKuAutoenter(WkTUser.AUTOENTER_NO);
			}
		}
		user.setKuStyle(String.valueOf(kuStyle.getSelectedItem().getValue()));
		userService.update(user);
		Sessions.getCurrent().setAttribute("user", user);
		Messagebox.show("����ɹ���", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
	}

	/**
	 * <li>�����������û���Ϣ���ù��ܡ� void
	 * 
	 * @author bobo
	 * @2010-4-13
	 */
	public void onClick$reset() {
		kuName.setValue("");
		kuAnswer.setValue("");
		kuQuestion.setValue("");
		kuEmail.setRawValue("");
		kuPhone.setValue("");
		kuBirthday.setRawValue(null);
		kuSex.setSelectedIndex(0);
		kuCompany.setValue("");
		bangType.setSelectedIndex(0);
		kuAutoenter.setChecked(false);
		kuAutoenter.setDisabled(true);
		uBandIp.setValue("");
		kuStyle.setSelectedIndex(0);
		kuIdentity.setValue("");
		kuPolitical.setValue("");
		kuEducational.setValue("");
		kuSchool.setValue("");
	}

	private void bangTypeHandle() {
		if (bangType.getSelectedIndex() == 0) {// ����
			uBandIp.setRawValue(null);
			uBandIp.setReadonly(true);
			kuAutoenter.setChecked(false);
			kuAutoenter.setDisabled(true);
		} else {
			uBandIp.setReadonly(false);
			uBandIp.setValue(user.getKuBindaddr());
			kuAutoenter.setDisabled(false);
			if (user.getKuAutoenter().trim().equalsIgnoreCase("1")) {
				kuAutoenter.setChecked(true);
			} else {
				kuAutoenter.setChecked(false);
			}
		}
	}
}
