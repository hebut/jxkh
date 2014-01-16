package com.uniwin.framework.ui.parameter;

/**
 * <li>����������ͨ����¼������ʵ����ʵ���û�������Ϣ����������
 *              ���Ը��û������޸ĵı�������ù���
 * ��Ӧ��ҳ��Ϊadmin/system/parameters/changePassword/index.zul
 * @author fang
 * @2010.4
 */
import java.util.List;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.uniwin.common.util.EncodeUtil;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

public class ChangePasswordWindow extends Window implements AfterCompose {
	private static final long serialVersionUID = -183785734754000982L;
	private Textbox loginName; // ��¼��
	private Textbox realName; // ��ʵ����
	private Label loginName2; // �������ĵ�¼��
	private Label realName2; // ����������ʵ����
	private Label company; // ����
	private Label sex; // �Ա�
	private Textbox password; // ����
	private Textbox repassword; // ȷ������
	Button search; // ������ť
	Button reset; // ���ð�ť
	WkTUser wkuser; // �û���Ϣ���ݷ��ʽӿ�
	Row changepassword; // ����ҳ�����
	UserService userService; // �޸��������ݷ��ʽӿ�
	List<Long> userDeptList;

	@SuppressWarnings("unchecked")
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		userDeptList = (List<Long>) Sessions.getCurrent().getAttribute("userDeptList");
		this.addForward(Events.ON_OK, search, Events.ON_CLICK);
	}

	/**
	 * <li>����������ͨ����¼������ʵ�������ѵ��û�������Ϣ �������ݿ��еĵ�¼������ʵ���������š��Ա���Ϣ��ʾ��ҳ��
	 */
	public void onClick$search() {
		List<WkTUser> rlist = userService.searchUserInfo(loginName.getValue(), realName.getValue(), userDeptList);
		if (rlist != null && rlist.size() > 0) {
			changepassword.setVisible(true);
			wkuser = (WkTUser) rlist.get(0);
			loginName2.setValue(wkuser.getKuLid());
			realName2.setValue(wkuser.getKuName());
			company.setValue(wkuser.getKuCompany());
			sex.setValue(wkuser.getKuSex());
		} else {
			changepassword.setVisible(false);
		}
	}

	/**
	 * ʵ�ֵ�¼������ʵ���������ù���
	 */
	public void onClick$reset() {
		loginName.setValue("");
		realName.setValue("");
	}

	/**
	 * <li>����������ʵ���޸ĺ�����뱣�� ��������������벻һ�£���ʾ������Ϣ ���ʵ�����뱣�棬��ʾ�����ɹ�
	 */
	public void onClick$save() throws InterruptedException {
		if (!repassword.getValue().equalsIgnoreCase(password.getValue())) {
			Messagebox.show("��������������벻һ�£�", "����", Messagebox.OK, Messagebox.INFORMATION);
		} else {
			wkuser.setKuPasswd(EncodeUtil.encodeByMD5(password.getValue()));
			userService.update(wkuser);
			Messagebox.show("�����ɹ���", "��ʾ", Messagebox.OK, Messagebox.INFORMATION); // ��������ɹ�����ʾ�������ɹ�������
		}
	}

	/**
	 * ʵ�������ȷ�����������
	 */
	public void onClick$reset2() {
		password.setRawValue(null);
		repassword.setRawValue(null);
	}
}
