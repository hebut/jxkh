package com.uniwin.asm.personal.ui.data;


import java.util.ArrayList;
import java.util.List;

import org.iti.gh.service.MajorService;
import org.iti.gh.service.PxqkService;
import org.iti.gh.service.UseryjfxService;
import org.iti.gh.service.ZcqkService;
import org.iti.xypt.entity.Teacher;
import org.iti.xypt.service.TeacherService;
import org.iti.xypt.service.XyptTitleService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

public class TeacherContact extends BaseWindow {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 用户数据访问接口
	private UserService userService;
	
	Textbox address, kuhomePhone,kuworkPhone, kuPhone, kuEmail, msn, qq, homepage,chuanzhen,others;


	// 用户实体
	WkTUser user=(WkTUser)Executions.getCurrent().getArg().get("user");
	// Teacher teacher;
	TeacherService teacherService;
	UseryjfxService useryjfxService;
	PxqkService pxqkService;
	ZcqkService zcqkService;
	MajorService majorService;
	XyptTitleService xypttitleService;
	Teacher teacher;
 
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		if(user==null){
			user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		}
		teacher = teacherService.findBykuid(user.getKuId());

		initUser(); // 调用初始化窗口函数

	}

	/**
	 * <li>功能描述：初始化 register页面数据。 void
	 * 
	 * @author bobo
	 * @2010-3-13
	 */
	public void initUser() {
		List pol = new ArrayList();
		List edu = new ArrayList();
		List xw = new ArrayList();
		List nat = new ArrayList();
		List mar = new ArrayList();
		List mand = new ArrayList();
		List comp = new ArrayList();
		List tea = new ArrayList();
		List worp = new ArrayList();
		List wort = new ArrayList();
		List stafs = new ArrayList();
		List stafp = new ArrayList();
		List fyear = new ArrayList();
		List hyear = new ArrayList();
		
		if(user.getKuHomeaddress()!=null){
			address.setValue(user.getKuHomeaddress());
		}
		if(user.getKuHometel()!=null){
			kuhomePhone.setValue(user.getKuHometel());
		}
		if( user.getKuWorktel()!=null){
			kuworkPhone.setValue(user.getKuWorktel());
		}
		if(user.getKuPhone()!=null){
			kuPhone.setValue(user.getKuPhone());
		}
		if(user.getKuEmail()!=null){
			kuEmail.setValue(user.getKuEmail());
		}
		if(	user.getKuMsn()!=null){
			msn.setValue(user.getKuMsn());
		}
		if( user.getKuQq()!=null ){
			qq.setValue(user.getKuQq());
		}
		if(user.getKuHomepage()!=null){
			homepage.setValue(user.getKuHomepage());
		}
		if( user.getKuFax()!=null){
			chuanzhen.setValue(user.getKuFax());
		}
		if(user.getKuOthercontact()!=null){
			others.setValue(user.getKuOthercontact());
		}
		
	}
		/*
		 * List yjfx = yjfxService.findByKuid(user.getKuId());
		 * 
		 * if(yjfx ==null ||yjfx.size()==0){ search.setValue(""); }else if(yjfx
		 * != null && yjfx.size()!=0){ String res=""; if(yjfx.size()!=1){
		 * for(int i=0;i<yjfx.size()-1;i++){ GhYjfx yj=(GhYjfx) yjfx.get(i);
		 * res=res+yj.getYjResearch()+","; } int m=(yjfx.size())-1; GhYjfx
		 * yj1=(GhYjfx)yjfx.get(m); res=res+yj1.getYjResearch(); }else
		 * if(yjfx.size()==1){ GhYjfx yj=(GhYjfx) yjfx.get(0);
		 * res=res+yj.getYjResearch(); } search.setValue(res); }
		 */
	
	/**
	 * <li>功能描述：用户信息更新功能。 void
	 * 
	 * @author bobo
	 * @throws InterruptedException
	 * @2010-3-1
	 */


	public void onClick$save() throws InterruptedException {

		if (kuPhone.getValue().equals("")) {

			Messagebox.show("手机号不能为空！", "提示", Messagebox.OK,
					Messagebox.INFORMATION);
			kuPhone.focus();
			return;
		}
		
		if (kuEmail.getValue().equals("")) {

			Messagebox.show("电子邮箱不能为空！", "提示", Messagebox.OK,
					Messagebox.INFORMATION);
			kuEmail.focus();
			return;
		}
		
		user.setKuPhone(kuPhone.getValue().trim());
		user.setKuEmail(kuEmail.getValue().trim());

		if (address.getValue() != null || kuhomePhone.getValue() != null
				|| kuworkPhone.getValue() != null || msn.getValue() != null
				|| qq.getValue() != null || homepage.getValue() != null
				|| chuanzhen.getValue() != null || others.getValue() != null) {
			user.setKuHomeaddress(address.getValue());
			user.setKuHometel(kuhomePhone.getValue());
			user.setKuWorktel(kuworkPhone.getValue());
			user.setKuMsn(msn.getValue());
			user.setKuQq(qq.getValue());
			user.setKuHomepage(homepage.getValue());
			user.setKuFax(chuanzhen.getValue());
			user.setKuOthercontact(others.getValue());
		}

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

	public void onClick$reset3() {
		address.setRawValue(null);
		kuhomePhone.setRawValue(null);
		kuworkPhone.setRawValue(null);
		kuPhone.setRawValue(null);
		kuEmail.setRawValue(null);
		msn.setRawValue(null);
		qq.setRawValue(null);
		homepage.setRawValue(null);
		chuanzhen.setRawValue(null);
		others.setRawValue(null);
	}


	/**
	 * 针对绑定与不绑定进行操作
	 */


	@Override
	public void initWindow() {

	}
	
//	public void setButtonAble(){
//save3.setVisible(false);reset3.setVisible(false);
//	}

	@Override
	public void initShow() {
		// TODO Auto-generated method stub
		
	}
}
