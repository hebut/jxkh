package com.uniwin.framework.ui.resetPassword;

/**
 * <li>������������֤������ʾ����𰸣���Ӧҳ��/admin/resetPssword/loginQuestion.zul��
 * void 
 * @author bobo
 * @2010-4-16
 */
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTUser;

public class LoginQuestionWindow extends Window implements AfterCompose {
	private static final long serialVersionUID = 6876014833626537484L;
	// �û����ݽӿ�
	//private UserService userService;
	// ������ʾ�����ǩ���
	private Label kuQuestion;
	// ����ش���ı������
	private Textbox kuAnswer;
	// ȷ�������ð�ť
	private Button submit;
	private String answer;
	WkTUser ulogin = (WkTUser) Sessions.getCurrent().getAttribute("uRelog");

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		//userService = (UserService) SpringUtil.getBean("userService");
		kuAnswer.focus();
		kuQuestion.setValue(ulogin.getKuQuestion());
		answer = ulogin.getKuAnswer();
		// ʹ�û��ڽ�������س���½
		this.addForward(Events.ON_OK, submit, Events.ON_CLICK);
	}

	/**
	 * <li>��������������������ʾ�𰸣�������֤���ɹ�����ת�����޸�ҳ�档 void
	 * 
	 * @author bobo
	 * @2010-4-16
	 */
	public void onClick$submit() throws InterruptedException {
		if (ulogin.getKuQuestion() == null) {
			Messagebox.show("��û������������ʾ������ȷ����ť���˳�����ϵϵͳ����Ա��", "��ʾ", Messagebox.OK, Messagebox.ERROR);
			Executions.getCurrent().sendRedirect("/admin/");
		} else {
			if (kuAnswer.getValue() != "") {
				if (!kuAnswer.getValue().trim().equals(answer.trim())) {
					Messagebox.show("�����������𰸺;ɴ𰸲�һ�£�", "��ʾ", Messagebox.OK, Messagebox.ERROR);
				} else {
					// ��ת�������޸�ҳ��
					this.detach();
					Executions.getCurrent().sendRedirect("/admin/resetPassword/restPW.zul");
				}
			} else {
				Messagebox.show("�������������𰸣�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
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
		kuAnswer.setValue("");
	}

	public void onClick$reto() {
		Executions.getCurrent().sendRedirect("/admin/resetPassword/UnameLogin.zul");
	}
}
