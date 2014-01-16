package com.uniwin.asm.personal.ui.data;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.naming.SizeLimitExceededException;

import org.iti.gh.entity.GhPxqk;
import org.iti.gh.entity.GhUseryjfx;
import org.iti.gh.entity.GhUseryjfxId;
import org.iti.gh.entity.GhYjfx;
import org.iti.gh.entity.GhZcqc;
import org.iti.gh.entity.GhZy;
import org.iti.gh.service.MajorService;
import org.iti.gh.service.PxqkService;
import org.iti.gh.service.UseryjfxService;
import org.iti.gh.service.YjfxService;
import org.iti.gh.service.ZcqkService;
import org.iti.gh.ui.listbox.YjfxListbox;
import org.iti.xypt.entity.Teacher;
import org.iti.xypt.entity.Title;
import org.iti.xypt.service.TeacherService;
import org.iti.xypt.service.XyptTitleService;
import org.iti.xypt.ui.base.BaseWindow;
import org.iti.xypt.ui.base.TitleSelectHbox;
import org.iti.xypt.ui.base.YjfxSelectListbox;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Fileupload; //import org.iti.zk.ex.*;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.data.struct.MediaUploadPackage;

import com.uniwin.common.util.ConvertUtil;
import com.uniwin.common.util.DateUtil;
import com.uniwin.common.util.IPUtil;
import com.uniwin.framework.common.fileuploadex.FileuploadEx;
import com.uniwin.framework.common.fileuploadex.ImageUploadPackage;
import com.uniwin.framework.common.fileuploadex.SetFileUploadError;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

public class TeacherWork extends BaseWindow {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 用户数据访问接口
	private UserService userService;
	// 用户输入框组件

	Textbox entirerea, emplquali, emplrank, emplposition, emplstate, dismissno,
			dismissreason;
	Listbox teaqualiry, workproper, worktype, staffstate, staffproperty;
	Datebox starworktime, entertime, entiretime, empltime, endtime,
			dismisstime;

	// 用户实体
	WkTUser user=(WkTUser)Executions.getCurrent().getArg().get("user");
	// Teacher teacher;

	TeacherService teacherService;
	UseryjfxService useryjfxService;
	PxqkService pxqkService;
	ZcqkService zcqkService;
	MajorService majorService;
	XyptTitleService xypttitleService;
	Label leave,reason;
	Teacher teacher;
    private Button save2,reset2;
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
		
		String[] teacherqualify = { "-请选择-", "幼儿园教师资格", "小学教师资格", "初级中学教师资格",
				"高级中学教师资格", "中等职业学校教师资格", "高等学校教师资格" };
		for (int i = 0; i < teacherqualify.length; i++) {
			tea.add(teacherqualify[i]);
		}
		teaqualiry.setModel(new ListModelList(tea));
		String[] workpro = { "-请选择-", "合同制", "聘用制", "临时工" };
		for (int i = 0; i < workpro.length; i++) {
			worp.add(workpro[i]);
		}
		workproper.setModel(new ListModelList(worp));
		String[] worktyp = { "-请选择-", "干部", "工人", "群众" };
		for (int i = 0; i < worktyp.length; i++) {
			wort.add(worktyp[i]);
		}
		worktype.setModel(new ListModelList(wort));
		String[] stafstate = { "-请选择-", "实习", "在职", "离职", "退休" };
		for (int i = 0; i < stafstate.length; i++) {
			stafs.add(stafstate[i]);
		}
		staffstate.setModel(new ListModelList(stafs));
		staffstate.addEventListener(Events.ON_SELECT, new EventListener() {

			public void onEvent(Event arg0) throws Exception {
				if (staffstate.getSelectedIndex() == 3) {
					leave.setVisible(true);
					reason.setVisible(true);
					entiretime.setVisible(true);
					entirerea.setVisible(true);
				} else {
					leave.setVisible(false);
					reason.setVisible(false);
					entiretime.setVisible(false);
					entirerea.setVisible(false);
				}
			}

		});
		String[] stafpro = {"-请选择-","公立","私立","其他"};
		for(int i=0;i<stafpro.length;i++){
			stafp.add(stafpro[i]);
		}
		staffproperty.setModel(new ListModelList(stafp));
		
		if (teacher.getThQualify() == null || teacher.getThQualify() == "") {
			teaqualiry.setSelectedIndex(0);
		} else {
			teaqualiry
					.setSelectedIndex(Integer.valueOf(teacher.getThQualify()));
		}
		if (teacher.getThHire() == null || teacher.getThHire() == "") {
			workproper.setSelectedIndex(0);
		} else {
			workproper.setSelectedIndex(Integer.valueOf(teacher.getThHire()));
		}
		if (teacher.getThSort() == null || teacher.getThSort() == "") {
			worktype.setSelectedIndex(0);
		} else {
			worktype.setSelectedIndex(Integer.valueOf(teacher.getThSort()));
		}
		if (teacher.getThState() == null || teacher.getThState() == "") {
			staffstate.setSelectedIndex(0);
			leave.setVisible(false);
			reason.setVisible(false);
			entiretime.setVisible(false);
			entirerea.setVisible(false);
		}else{
			staffstate.setSelectedIndex(Integer.valueOf(teacher.getThState()));
			if(Integer.valueOf(teacher.getThState())==3){
				leave.setVisible(true);
				reason.setVisible(true);
				entiretime.setVisible(true);
				entirerea.setVisible(true);
			}else{
				leave.setVisible(false);
				reason.setVisible(false);
				entiretime.setVisible(false);
				entirerea.setVisible(false);
			}
		}
		if (teacher.getThType() == null || teacher.getThType() == "") {
			staffproperty.setSelectedIndex(0);
		} else {
			staffproperty
					.setSelectedIndex(Integer.valueOf(teacher.getThType()));
		}
		/*
		 * if(teacher.getThFirsub()!=null){ List
		 * mlst=majorService.findByZyid(teacher.getThFirsub());
		 * if(mlst!=null&&mlst.size()!=0){ GhZy z=(GhZy) mlst.get(0);
		 * System.out.println("ss....."+z.getZyId()); fhighmajor.setMajor(z); }
		 * 
		 * }
		 */

		if (teacher.getThLeare() != null) {
			entirerea.setValue(teacher.getThLeare());
		}
		if(teacher.getThEmplqualify() != null){
			emplquali.setValue(teacher.getThEmplqualify());
		}
		if(teacher.getThEmpllevel() != null){
			emplrank.setValue(teacher.getThEmpllevel());
		}
		if( teacher.getThEmplposition() != null){
			emplposition.setValue(teacher.getThEmplposition());
		}
		if(teacher.getThEmplstate() != null){
			emplstate.setValue(teacher.getThEmplstate());
		}
		if( teacher.getThDeemplno() != null){
			dismissno.setValue(teacher.getThDeemplno());
		}
		if( teacher.getThDeemplre() != null){
			dismissreason.setValue(teacher.getThDeemplre());
		}
		if(teacher.getThLeatime()!=null&&teacher.getThLeatime().length()>0){
			entiretime.setValue(ConvertUtil.convertDate(DateUtil.getDateString(teacher.getThLeatime())));
		}
		if(teacher.getThEmplTime()!=null&&teacher.getThEmplTime().length()>0){
			empltime.setValue(ConvertUtil.convertDate(DateUtil.getDateString(teacher.getThEmplTime())));
		}
		if(teacher.getThEmplend()!=null&&teacher.getThEmplend().length()>0){
			endtime.setValue(ConvertUtil.convertDate(DateUtil.getDateString(teacher.getThEmplend())));
		}
		
		if(teacher.getThDeempltime()!=null&&teacher.getThDeempltime().length()>0){
			dismisstime.setValue(ConvertUtil.convertDate(DateUtil.getDateString(teacher.getThDeempltime())));
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

		if (teacher.getThStart() != null && teacher.getThStart().length() > 0) {
			starworktime.setValue(ConvertUtil.convertDate(DateUtil
					.getDateString(teacher.getThStart())));
		}
		if (teacher.getThEnter() != null && teacher.getThEnter().length() > 0) {
			entertime.setValue(ConvertUtil.convertDate(DateUtil
					.getDateString(teacher.getThEnter())));
		}
		if(teacher.getThEnter()!=null&&teacher.getThEnter().length()>0){
			entertime.setValue(ConvertUtil.convertDate(DateUtil.getDateString(teacher.getThEnter())));
		}
	}

	/**
	 * <li>功能描述：用户信息更新功能。 void
	 * 
	 * @author bobo
	 * @throws InterruptedException
	 * @2010-3-1
	 */

	public void onClick$save2() throws InterruptedException {

		Teacher teacher = teacherService.findBykuid(user.getKuId());
		if (starworktime.getValue() != null) {
			teacher.setThStart(DateUtil.getDateString(starworktime.getValue()));
		}
		if (entertime.getValue() != null) {
			teacher.setThEnter(DateUtil.getDateString(entertime.getValue()));
		}
		teacher.setThQualify(String.valueOf(teaqualiry.getSelectedIndex()));
		teacher.setThHire(String.valueOf(workproper.getSelectedIndex()));
		teacher.setThSort(String.valueOf(worktype.getSelectedIndex()));
		teacher.setThState(String.valueOf(staffstate.getSelectedIndex()));
		teacher.setThType(String.valueOf(staffproperty.getSelectedIndex()));
		if (entirerea.getValue() != null || emplquali.getValue() != null
				|| emplrank.getValue() != null
				|| emplposition.getValue() != null
				|| emplstate.getValue() != null || dismissno.getValue() != null
				|| dismissreason.getValue() != null) {
			teacher.setThLeare(entirerea.getValue());
			teacher.setThEmplqualify(emplquali.getValue());
			teacher.setThEmpllevel(emplrank.getValue());
			teacher.setThEmplposition(emplposition.getValue());
			teacher.setThEmplstate(emplstate.getValue());
			teacher.setThDeemplno(dismissno.getValue());
			teacher.setThDeemplre(dismissreason.getValue());
		}
		if (entiretime.getValue() != null) {
			teacher.setThLeatime(DateUtil.getDateString(entiretime.getValue()));
		} else {
			teacher.setThLeatime(null);
		}
		if (empltime.getValue() != null) {
			teacher.setThEmplTime(DateUtil.getDateString(empltime.getValue()));
		} else {
			teacher.setThEmplTime(null);
		}
		if (endtime.getValue() != null) {
			teacher.setThEmplend(DateUtil.getDateString(endtime.getValue()));
		} else {
			teacher.setThEmplend(null);
		}
		if (dismisstime.getValue() != null) {
			teacher.setThDeempltime(DateUtil.getDateString(dismisstime
					.getValue()));
		} else {
			teacher.setThDeempltime(null);
		}
		teacherService.update(teacher);
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


	public void onClick$reset2() {
		starworktime.setRawValue(null);
		entertime.setRawValue(null);
		teaqualiry.setSelectedIndex(0);
		workproper.setSelectedIndex(0);
		worktype.setSelectedIndex(0);
		staffproperty.setSelectedIndex(0);
		staffstate.setSelectedIndex(0);
		entiretime.setRawValue(null);
		entirerea.setRawValue(null);
		emplquali.setRawValue(null);
		emplrank.setRawValue(null);
		emplposition.setRawValue(null);
		empltime.setRawValue(null);
		endtime.setRawValue(null);
		emplstate.setRawValue(null);
		dismisstime.setRawValue(null);
		dismissno.setRawValue(null);
		dismissreason.setRawValue(null);
	}
	@Override
	public void initShow() {}



	@Override
	public void initWindow() {

	}
	
	public void setButtonAble(){
		save2.setVisible(false);
		reset2.setVisible(false);
	}
}
