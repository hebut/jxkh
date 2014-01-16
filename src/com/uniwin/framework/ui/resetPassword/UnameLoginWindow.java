package com.uniwin.framework.ui.resetPassword;

/**
 * <li>������������½ҳ��� ����������ת����Ӧҳ��/admin/resetPssword/UnameLogin.zul��
 * void 
 * @author bobo
 * @2010-4-16
 */
import java.util.List;

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

public class UnameLoginWindow extends Window implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Textbox kuLid;
	private UserService userService;
	private Button submit;
	List<WkTUser> ulist;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		userService = (UserService) SpringUtil.getBean("userService");
		kuLid.focus();
		// ʹ�û��ڽ�������س���½
		this.addForward(Events.ON_OK, submit, Events.ON_CLICK);
	}

	/**
	 * <li>���������� �����û���¼���� void
	 * 
	 * @author bobo
	 * @throws InterruptedException
	 * @2010-4-16
	 */
	public void onClick$submit() throws InterruptedException {
		if (kuLid.getValue() != null && kuLid.getValue() != "") {
			ulist = userService.getUsersByLogin(kuLid.getValue());
			if (ulist.size() != 0) {
				WkTUser uu = (WkTUser) ulist.get(0);
				Sessions.getCurrent().setAttribute("uRelog", uu);
				this.detach();
				Executions.getCurrent().sendRedirect("/admin/resetPassword/loginQuestion.zul");
			} else {
				Messagebox.show("������ĵ�¼�������ڣ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			}
		} else {
			Messagebox.show("���������ĵ�¼����", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
		}
	}

	/**
	 * <li>���������������¼���� void
	 * 
	 * @author bobo
	 * @2010-4-16
	 */
	public void onClick$reset() {
		kuLid.setValue("");
	}

	/**
	 * <li>�����������رմ��ڡ� void
	 * 
	 * @author bobo
	 * @2010-4-16
	 */
	public void onClick$reto() {
		Executions.getCurrent().sendRedirect("/admin/login.zul");
	}
}
