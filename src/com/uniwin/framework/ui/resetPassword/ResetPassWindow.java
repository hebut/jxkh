package com.uniwin.framework.ui.resetPassword;

/**
 * <li>�����������޸����룬��Ӧҳ��/admin/resetPssword/restPW.zul��
 * void 
 * @author bobo
 * @2010-4-16
 */
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import com.uniwin.common.util.EncodeUtil;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

public class ResetPassWindow extends Window implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// �û����ݽӿ�
	private UserService userService;
	// �û��������ȷ�������ı������
	private Textbox new_password, sure_password;
	private Button submit;
	WkTUser wkTUser = (WkTUser) Sessions.getCurrent().getAttribute("uRelog");

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		userService = (UserService) SpringUtil.getBean("userService");
		new_password.focus();
		// ʹ�û��ڽ�������س���½
		this.addForward(Events.ON_OK, submit, Events.ON_CLICK);
	}

	/**
	 * <li>�����������޸����롣 void
	 * 
	 * @author bobo
	 * @2010-4-16
	 */
	public void onClick$submit() throws InterruptedException {
		if (new_password.getValue() == "") {
			Messagebox.show("�����벻��Ϊ�գ������������룡", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
		} else if (sure_password.getValue() == "") {
			Messagebox.show("ȷ�����벻��Ϊ�գ�������ȷ�����룡", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
		} else {
			if (!new_password.getValue().trim().equals(sure_password.getValue().trim())) {
				Messagebox.show("�����������벻һ�£�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			} else {
				wkTUser.setKuPasswd(EncodeUtil.encodeByMD5(new_password.getValue()));
				userService.update(wkTUser);
				Messagebox.show("�����޸ĳɹ���", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
				this.detach();
				Executions.getCurrent().sendRedirect("/admin/");
			}
		}
	}

	/**
	 * <li>����������ҳ��������������á� void
	 * 
	 * @author bobo
	 * @2010-4-16
	 */
	public void onClick$reset() {
		new_password.setValue("");
		sure_password.setValue("");
	}
}
