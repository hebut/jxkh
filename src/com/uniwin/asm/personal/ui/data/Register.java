package com.uniwin.asm.personal.ui.data;

/**
 * <li>用户注册组件,对应页面admin/persongal/data/register/index.zul
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
	// 用户数据访问接口
	private UserService userService;
	// 用户输入框组件
	private Textbox kuName, kuAnswer, kuQuestion, kuEmail, kuPhone, kuCompany, uBandIp, kuIdentity, kuPolitical, kuEducational, kuSchool;
	private Label kuLid;
	// 用户选择框组件
	private Checkbox kuAutoenter;
	// 用户日期输入框组件
	private Datebox kuBirthday;
	// 用户选择框组件
	private Radiogroup kuSex;
	// 用户下拉列表框组件
	private Listbox kuStyle, uStatus, bangType;
	// 用户实体
	WkTUser user;
	// 重置按钮
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
		initUser(); // 调用初始化窗口函数
	}

	/**
	 * <li>功能描述：初始化 register页面数据。 void
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
		if (user.getKuSex().trim().equalsIgnoreCase("2")) { // 性别是字符，要去空格，否则该语句失灵
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
		bangTypeHandle(); // 调用函数
		if (user.getKuAutoenter().trim().equalsIgnoreCase("1")) {
			kuAutoenter.setChecked(true);
		} else {
			kuAutoenter.setChecked(false);
		}
		kuStyle.setSelectedIndex(0);
	}

	/**
	 * <li>功能描述：用户信息更新功能。 void
	 * 
	 * @author bobo
	 * @throws InterruptedException
	 * @2010-3-1
	 */
	public void onClick$save() throws InterruptedException {
		if (kuName.getValue().equals("")) {
			Messagebox.show("用户名不能为空！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			kuName.focus();
			return;
		}
		if (kuEmail.getValue().equals("")) {
			Messagebox.show("电子邮箱不能为空！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			kuEmail.focus();
			return;
		}
		if (kuIdentity.getValue().equals("")) {
			Messagebox.show("身份证号不能为空！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			kuIdentity.focus();
			return;
		}
		if (kuPhone.getValue().equals("")) {
			Messagebox.show("电话不能为空！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			kuPhone.focus();
			return;
		}
		if (kuPolitical.getValue().equals("")) {
			Messagebox.show("政治面貌不能为空！", "提示", Messagebox.OK, Messagebox.INFORMATION);
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
			user.setKuSex("1"); // 1代表“男”，2代表“女”
		} else {
			user.setKuSex("2");
		}
		// 如果用户选择不绑定，则设置其不能自动登陆，同时将绑定IP地址设置空.
		// 如果用户选择IP绑定，首先设置绑定的IP地址，如果输入则设置为输入IP，否则设置该用户上传登陆IP地址。
		// 选择IP绑定并且设置绑定IP后，判断用户是否设置自动登陆
		if (bangType.getSelectedIndex() == 0) {// 选择不绑定
			user.setKuBindtype(WkTUser.BAND_NO);
			user.setKuAutoenter(WkTUser.AUTOENTER_NO);
			user.setKuBindaddr("");
		} else {
			user.setKuBindtype(WkTUser.BAND_YES);
			try {
				IPUtil.getIPLong(uBandIp.getValue());
				user.setKuBindaddr(uBandIp.getValue());
			} catch (Exception e) {
				throw new WrongValueException(uBandIp, "绑定地址输入错误!");
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
		Messagebox.show("保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
	}

	/**
	 * <li>功能描述：用户信息重置功能。 void
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
		if (bangType.getSelectedIndex() == 0) {// 不绑定
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
