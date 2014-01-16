package com.uniwin.framework.ui.parameter;

/**
 * <li>功能描述：通过登录名和真实姓名实现用户基本信息的搜索功能
 *              及对该用户密码修改的保存和重置功能
 * 对应的页面为admin/system/parameters/changePassword/index.zul
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
	private Textbox loginName; // 登录名
	private Textbox realName; // 真实姓名
	private Label loginName2; // 搜索到的登录名
	private Label realName2; // 搜索到的真实姓名
	private Label company; // 部门
	private Label sex; // 性别
	private Textbox password; // 密码
	private Textbox repassword; // 确认密码
	Button search; // 搜索按钮
	Button reset; // 重置按钮
	WkTUser wkuser; // 用户信息数据访问接口
	Row changepassword; // 搜索页面对象
	UserService userService; // 修改密码数据访问接口
	List<Long> userDeptList;

	@SuppressWarnings("unchecked")
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		userDeptList = (List<Long>) Sessions.getCurrent().getAttribute("userDeptList");
		this.addForward(Events.ON_OK, search, Events.ON_CLICK);
	}

	/**
	 * <li>功能描述：通过登录名和真实姓名所搜到用户基本信息 读出数据库中的登录名、真实姓名、部门、性别信息显示到页面
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
	 * 实现登录名和真实姓名的重置功能
	 */
	public void onClick$reset() {
		loginName.setValue("");
		realName.setValue("");
	}

	/**
	 * <li>功能描述：实现修改后的密码保存 如果两次输入密码不一致，提示错误信息 如果实现密码保存，提示操作成功
	 */
	public void onClick$save() throws InterruptedException {
		if (!repassword.getValue().equalsIgnoreCase(password.getValue())) {
			Messagebox.show("您两次输入的密码不一致！", "错误", Messagebox.OK, Messagebox.INFORMATION);
		} else {
			wkuser.setKuPasswd(EncodeUtil.encodeByMD5(password.getValue()));
			userService.update(wkuser);
			Messagebox.show("操作成功！", "提示", Messagebox.OK, Messagebox.INFORMATION); // 如果操作成功，提示“操作成功”窗口
		}
	}

	/**
	 * 实现密码和确认密码的重置
	 */
	public void onClick$reset2() {
		password.setRawValue(null);
		repassword.setRawValue(null);
	}
}
