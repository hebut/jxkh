package com.uniwin.asm.personal.ui.data.teacherinfo.kyqk;

import java.util.List;

import org.iti.gh.entity.GhKyjh;
import org.iti.gh.service.CgService;
import org.iti.gh.service.FmzlService;
import org.iti.gh.service.GhFileService;
import org.iti.gh.service.JlhzService;
import org.iti.gh.service.JsxmService;
import org.iti.gh.service.JxbgService;
import org.iti.gh.service.KyjhService;
import org.iti.gh.service.LwzlService;
import org.iti.gh.service.PxService;
import org.iti.gh.service.QkdcService;
import org.iti.gh.service.QtService;
import org.iti.gh.service.RjzzService;
import org.iti.gh.service.SkService;
import org.iti.gh.service.XmService;
import org.iti.gh.service.XmzzService;
import org.iti.gh.service.XshyService;
import org.iti.gh.service.YjfxService;
import org.iti.gh.service.ZsService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Textbox;

import com.uniwin.framework.entity.WkTUser;

public class WlghWindow extends BaseWindow {

	//未来五年科研计划
	Grid grid7;
	Textbox yjnr,jjwt,yqcg;
	FmzlService fmzlService;
	QtService qtService;
	KyjhService kyjhService;
	PxService pxService;
	XshyService xshyService;
	SkService skService;
	ZsService zsService;
	JlhzService jlhzService;
	JxbgService jxbgService;
	XmService xmService;
	XmzzService xmzzService;
	QkdcService qkdcService;
	CgService cgService;
	LwzlService lwzlService;
	RjzzService rjzzService;
	YjfxService yjfxService;
	GhFileService ghfileService;
	WkTUser user;
	
	JsxmService jsxmService;
	@Override
	public void initShow() {
		initWindow();

	}

	@Override
	public void initWindow() {
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		//未来五年科研计划
		List list8 = kyjhService.findbyKuid(user.getKuId());
		if(list8.size()>0){
			GhKyjh jh = (GhKyjh) list8.get(0);
			if(jh.getJhNr()!=null){
				yjnr.setValue(jh.getJhNr());
			}else{
				yjnr.setValue("");
			}
			if(jh.getJhGjwt()!=null){
				jjwt.setValue(jh.getJhGjwt());
			}else{
				jjwt.setValue("");
			}
			if(jh.getJhYjcg()!=null){
				yqcg.setValue(jh.getJhYjcg());
			}else{
				yqcg.setValue("");
			}
		}

	}
	public void onClick$submit() throws InterruptedException{
		List list7 = kyjhService.findbyKuid(user.getKuId());
		if(list7.size()>0){
			GhKyjh jh = (GhKyjh) list7.get(0);
			if(yjnr.getValue()==null||"".equals(yjnr.getValue().trim())){
				Messagebox.show("您还没有输入研究内容。");
				return;
			}else{
				jh.setJhNr(yjnr.getValue());
			}
			if(jjwt.getValue()==null||"".equals(jjwt.getValue().trim())){
				Messagebox.show("您还没有输入要解决的关键问题。");
				return;
			}else{
				jh.setJhGjwt(jjwt.getValue());
			}
			if(yqcg.getValue()==null||"".equals(yqcg.getValue().trim())){
				Messagebox.show("您还没有输入预期的研究成果。");
				return;
			}else{
				jh.setJhYjcg(yqcg.getValue());
			}
			kyjhService.update(jh);
			Messagebox.show("保存成功！");
		}else{
			GhKyjh jh = new GhKyjh() ;
			jh.setKuId(user.getKuId());
			if(yjnr.getValue()==null||"".equals(yjnr.getValue().trim())){
				Messagebox.show("您还没有输入研究内容。");
				return;
			}else{
				System.out.println();
				jh.setJhNr(yjnr.getValue());
			}
			if(jjwt.getValue()==null||"".equals(jjwt.getValue().trim())){
				Messagebox.show("您还没有输入要解决的关键问题。");
				return;
			}else{
				jh.setJhGjwt(jjwt.getValue());
			}
			if(yqcg.getValue()==null||"".equals(yqcg.getValue().trim())){
				Messagebox.show("您还没有输入预期的研究成果。");
				return;
			}else{
				jh.setJhYjcg(yqcg.getValue());
			}
			kyjhService.save(jh);
			Messagebox.show("保存成功！");
		}
		
		
	}

	public void onClick$reset(){
		initWindow();
	}

}
