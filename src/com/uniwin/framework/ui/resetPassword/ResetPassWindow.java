package com.uniwin.framework.ui.resetPassword;

/**
 * <li>功能描述：修改密码，对应页面/admin/resetPssword/restPW.zul。
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
	// 用户数据接口
	private UserService userService;
	// 用户新密码和确认密码文本框组件
	private Textbox new_password, sure_password;
	private Button submit;
	WkTUser wkTUser = (WkTUser) Sessions.getCurrent().getAttribute("uRelog");

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		userService = (UserService) SpringUtil.getBean("userService");
		new_password.focus();
		// 使用户在界面输入回车登陆
		this.addForward(Events.ON_OK, submit, Events.ON_CLICK);
	}

	/**
	 * <li>功能描述：修改密码。 void
	 * 
	 * @author bobo
	 * @2010-4-16
	 */
	public void onClick$submit() throws InterruptedException {
		if (new_password.getValue() == "") {
			Messagebox.show("新密码不能为空，请输入新密码！", "提示", Messagebox.OK, Messagebox.INFORMATION);
		} else if (sure_password.getValue() == "") {
			Messagebox.show("确认密码不能为空，请输入确认密码！", "提示", Messagebox.OK, Messagebox.INFORMATION);
		} else {
			if (!new_password.getValue().trim().equals(sure_password.getValue().trim())) {
				Messagebox.show("两次输入密码不一致！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			} else {
				wkTUser.setKuPasswd(EncodeUtil.encodeByMD5(new_password.getValue()));
				userService.update(wkTUser);
				Messagebox.show("密码修改成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
				this.detach();
				Executions.getCurrent().sendRedirect("/admin/");
			}
		}
	}

	/**
	 * <li>功能描述：页面输入框数据重置。 void
	 * 
	 * @author bobo
	 * @2010-4-16
	 */
	public void onClick$reset() {
		new_password.setValue("");
		sure_password.setValue("");
	}
}
