package com.uniwin.asm.personal.ui.data;

/**
 * <li>用户登录组件,对应页面admin/personal/data/register/index.zul
 * @author bobo
 * @date 2010-3-15
 */
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import com.uniwin.common.util.EncodeUtil;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

public class ResetPassword1 extends GenericForwardComposer {

	private static final long serialVersionUID = -164191727309417123L;
	// 用户数据访问接口
	private UserService userService;
	// 用户输入框组件
	private Textbox old_password, new_password, sure_password;
	private Label user_to;
	// 用户实体
	private WkTUser wkTUser;
	// 密码框中用户输入密码加密后字符串
	private String password;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		userService = (UserService) SpringUtil.getBean("userService");
		wkTUser = (WkTUser) Sessions.getCurrent().getAttribute("user");
		user_to.setValue(wkTUser.getKuLid());
		password = wkTUser.getKuPasswd();
	}

	/**
	 * <li>功能描述：用户修改密码功能。 void
	 * 
	 * @author bobo
	 * @2010-3-13
	 */
	public void onClick$submit() {
		if (!EncodeUtil.encodeByDES(old_password.getValue().trim()).equals(password.trim())) {
			try {
				Messagebox.show("您输入的密码和旧密码不一致！", "提示", Messagebox.OK, Messagebox.ERROR);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else if (!new_password.getValue().trim().equals(sure_password.getValue().trim())) {
			alert("两次输入的密码不一致！");
		} else {
			wkTUser.setKuPasswd(EncodeUtil.encodeByDES(new_password.getValue()));
			userService.update(wkTUser);
			try {
				Messagebox.show("密码修改成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * <li>功能描述：页面输入框数据重置。 void
	 * 
	 * @author bobo
	 * @2010-3-13
	 */
	public void onClick$reset() {
		old_password.setValue("");
		new_password.setValue("");
		sure_password.setValue("");
	}
}
