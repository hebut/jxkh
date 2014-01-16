package com.uniwin.framework.ui.parameter;

/**
 * <li>功能描述：实现网站信息修改后的保存和重置
 * 对应的页面为admin/system/parameters/websiteInfo/index.zul
 * @author fang
 * @2010.3
 */
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.uniwin.basehs.service.BaseService;
import com.uniwin.framework.entity.WkTSite;

public class WebsiteInfoEditWindow extends Window implements AfterCompose {
	private static final long serialVersionUID = -2518102178114497440L;
	private Textbox enterpriseName; // 单位名称
	private Textbox legalPerson; // 法人代表
	private Textbox telephone; // 电话
	private Textbox fax; // 传真
	private Textbox email; // E-MAIL地址
	private Textbox address; // 地址
	private Textbox postCoder; // 邮编
	private Listbox style; // 默认界面风格
	Button sava, reset; // 保存和重置
	WkTSite website; // 网站信息数据访问接口
	BaseService baseService; // 网站信息数据访问接口

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		this.initializeWindow();
	}

	/**
	 * <li>功能描述：初始化网站信息界面，从数据库中读出存放信息
	 */
	public void initializeWindow() {
		website = (WkTSite) baseService.get(WkTSite.class, 1L); // 从数据库中读出第一条记录
		enterpriseName.setValue(website.getKsEpname());
		legalPerson.setValue(website.getKsGenmgr());
		telephone.setValue(website.getKsPhone());
		fax.setValue(website.getKsFax());
		email.setValue(website.getKsEmail());
		address.setValue(website.getKsAddress());
		postCoder.setValue(website.getKsPostid());
		if (website.getKsStyle().trim().equalsIgnoreCase("默认")) {
			style.setSelectedIndex(0);
		} // 如果数据库中界面风格存放的是“默认”，则打开界面时显示“默认”
		else if (website.getKsStyle().trim().equalsIgnoreCase("水晶蓝")) {
			style.setSelectedIndex(1);
		} // 如果数据库中界面风格存放的是“水晶蓝”，则打开界面时显示“水晶蓝”
		else {
			style.setSelectedIndex(2);
		} // 最后一种情况即界面风格为“red”
	}

	/**
	 * <li>功能描述：实现网站信息修改后的保存功能 将页面内获取的数据存放到数据库对应的位置
	 * 
	 * @throws InterruptedException
	 */
	public void onClick$save() throws InterruptedException {
		website.setKsEpname(enterpriseName.getValue());
		website.setKsGenmgr(legalPerson.getValue());
		website.setKsPhone(telephone.getValue());
		website.setKsFax(fax.getValue());
		website.setKsEmail(email.getValue());
		if (postCoder.getValue().length() > 6) {
			Messagebox.show("邮政编码过长!", "错误提示", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		website.setKsPostid(postCoder.getValue());
		website.setKsAddress(address.getValue());
		website.setKsStyle(style.getSelectedItem().getLabel());
		baseService.update(website);
		Messagebox.show("保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);// 如果操作成功，提示“保存成功”窗口
	}

	/**
	 *<li>功能描述：实现修改网站信息的重置功能 点击重置后页面将显示原来存放在数据库中的内容
	 */
	public void onClick$reset() {
		enterpriseName.setValue(website.getKsEpname());
		legalPerson.setValue(website.getKsGenmgr());
		telephone.setValue(website.getKsPhone());
		fax.setValue(website.getKsFax());
		email.setValue(website.getKsEmail());
		address.setValue(website.getKsAddress());
		postCoder.setValue(website.getKsPostid());
	}
}
