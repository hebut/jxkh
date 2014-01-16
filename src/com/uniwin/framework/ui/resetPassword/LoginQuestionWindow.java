package com.uniwin.framework.ui.resetPassword;

/**
 * <li>功能描述：验证密码提示问题答案，对应页面/admin/resetPssword/loginQuestion.zul。
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
	// 用户数据接口
	//private UserService userService;
	// 密码提示问题标签组件
	private Label kuQuestion;
	// 密码回答答案文本框组件
	private Textbox kuAnswer;
	// 确定和重置按钮
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
		// 使用户在界面输入回车登陆
		this.addForward(Events.ON_OK, submit, Events.ON_CLICK);
	}

	/**
	 * <li>功能描述：输入密码提示答案，进行验证，成功则跳转密码修改页面。 void
	 * 
	 * @author bobo
	 * @2010-4-16
	 */
	public void onClick$submit() throws InterruptedException {
		if (ulogin.getKuQuestion() == null) {
			Messagebox.show("您没有设置问题提示！请点击确定按钮，退出后联系系统管理员！", "提示", Messagebox.OK, Messagebox.ERROR);
			Executions.getCurrent().sendRedirect("/admin/");
		} else {
			if (kuAnswer.getValue() != "") {
				if (!kuAnswer.getValue().trim().equals(answer.trim())) {
					Messagebox.show("您输入的密码答案和旧答案不一致！", "提示", Messagebox.OK, Messagebox.ERROR);
				} else {
					// 跳转到密码修改页面
					this.detach();
					Executions.getCurrent().sendRedirect("/admin/resetPassword/restPW.zul");
				}
			} else {
				Messagebox.show("请您输入的密码答案！", "提示", Messagebox.OK, Messagebox.INFORMATION);
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
		kuAnswer.setValue("");
	}

	public void onClick$reto() {
		Executions.getCurrent().sendRedirect("/admin/resetPassword/UnameLogin.zul");
	}
}
