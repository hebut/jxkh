package org.iti.gh.ghtj.hzqkdc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.iti.gh.entity.GhCg;
import org.iti.gh.entity.GhFmzl;
import org.iti.gh.entity.GhHylw;
import org.iti.gh.entity.GhJlhz;
import org.iti.gh.entity.GhJxbg;
import org.iti.gh.entity.GhPx;
import org.iti.gh.entity.GhXm;
import org.iti.gh.entity.GhXshy;
import org.iti.gh.entity.GhZz;
import org.iti.gh.ghtj.tpanel2.FmzlWindow;
import org.iti.gh.ghtj.tpanel2.GhcgWindow;
import org.iti.gh.ghtj.tpanel2.GhxmWindow;
import org.iti.gh.ghtj.tpanel2.KyjhWindow;
import org.iti.gh.ghtj.tpanel2.LwzlWindow;
import org.iti.gh.ghtj.tpanel2.PxskWindow;
import org.iti.gh.ghtj.tpanel2.XsjlWindow;
import org.iti.gh.service.CgService;
import org.iti.gh.service.FmzlService;
import org.iti.gh.service.HylwService;
import org.iti.gh.service.JlhzService;
import org.iti.gh.service.JxbgService;
import org.iti.gh.service.KyjhService;
import org.iti.gh.service.LwzlService;
import org.iti.gh.service.PxService;
import org.iti.gh.service.QkdcService;
import org.iti.gh.service.QklwService;
import org.iti.gh.service.QtService;
import org.iti.gh.service.SkService;
import org.iti.gh.service.XmService;
import org.iti.gh.service.XshyService;
import org.iti.gh.service.ZsService;
import org.iti.gh.service.ZzService;
import org.iti.gh.ui.listbox.DeptListbox;
import org.iti.gh.ui.listbox.YearListbox;
import org.iti.xypt.entity.XyUserrole;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Toolbarbutton;

import com.uniwin.framework.entity.WkTUser;

public class XkjshzWindow extends BaseWindow {

	Toolbarbutton c11, c12, c13, c14, c15, c16, c17, c18;
	Toolbarbutton c21, c22, c23, c24, c25, c26, c27, c28;
	Toolbarbutton c31, c32, c33, c34, c35, c36;
	DeptListbox deplist;
	YearListbox yearlist;
	CgService cgService;
	FmzlService fmzlService;
	JlhzService jlhzService;
	JxbgService jxbgService;
	KyjhService kyjhService;
//	LwzlService lwzlService;
	PxService pxService;
	QkdcService qkdcService;
	QtService qtService;
	SkService skService;
	XmService xmService;
	XshyService xshyService;
	ZsService zsService;
	HylwService hylwService;
	QklwService qklwService;
	ZzService zzService;
	XyUserrole xyuserole;
	
	WkTUser user;
	@Override
	public void initShow() {
//		System.out.println(xyuserole+"等等等等等等等等等等等等");
//		final WkTUser user=(WkTUser)Sessions.getCurrent().getAttribute("user");
		
//		List l11=xmService.findByKdId(user.getKdId(), GhXm.KYCG);
		
		c11.addEventListener(Events.ON_CLICK, new EventListener(){
			public void onEvent(Event arg0) throws Exception {
				if(c11.getLabel().trim().equals("0")){
					Messagebox.show("没有项目信息", "提示", Messagebox.OK, Messagebox.INFORMATION);
				}else{
				Map args=new HashMap();
				args.put("kdid", xyuserole.getKdId());
				args.put("lx", GhXm.KYXM);
				args.put("progress", GhXm.PROGRESS_DONE);
				GhxmWindow cg=(GhxmWindow)Executions.createComponents("/admin/xkgl/ghtj/xkjstj/tpanel2/kyjyxm.zul",null,args);
				cg.initWindow();
				cg.doHighlighted();
				cg.setTitle("已获得的科研成果（验收或鉴定）");
				}
			}
		});
	
		c12.addEventListener(Events.ON_CLICK, new EventListener(){
			public void onEvent(Event arg0) throws Exception {
				if(c12.getLabel().trim().equals("0")){
					Messagebox.show("没有成果信息!", "提示", Messagebox.OK, Messagebox.INFORMATION);
				}else{
					Map args=new HashMap();
					args.put("kdid", xyuserole.getKdId());
					args.put("lx", GhCg.CG_KY);
				GhcgWindow cg=(GhcgWindow)Executions.createComponents("/admin/xkgl/ghtj/xkjstj/tpanel2/kyjycg.zul",null,args);
				cg.initWindow();
				cg.doHighlighted();
				cg.setTitle("获奖科研成果");
				}
			}
		});
	
		c13.addEventListener(Events.ON_CLICK, new EventListener(){
			
			public void onEvent(Event arg0) throws Exception {
				if(c13.getLabel().trim().equals("0")){
					Messagebox.show("没有科研论文信息!", "提示", Messagebox.OK, Messagebox.INFORMATION);
				}else{
					Map args=new HashMap();
					args.put("lx",GhHylw.KYLW);
//					args.put("type", GhLwzl.LWHY);
					args.put("kdid",xyuserole.getKdId());
			 	LwzlWindow cg=(LwzlWindow)Executions.createComponents("/admin/xkgl/ghtj/xkjstj/tpanel2/lwzl.zul",null,args);
				cg.initWindow();
				cg.doHighlighted();
				cg.setTitle("发表论文情况");
				}
			}
		});
		
	
		c14.addEventListener(Events.ON_CLICK, new EventListener(){
			public void onEvent(Event arg0) throws Exception {
				if(c14.getLabel().trim().equals("0")){
					Messagebox.show("没有出版学术专著信息!", "提示", Messagebox.OK, Messagebox.INFORMATION);
				}else{
				Map args=new HashMap();
				args.put("lx",GhZz.ZZ);
//				args.put("type", null);
				args.put("kdid",xyuserole.getKdId());
				LwzlWindow cg=(LwzlWindow)Executions.createComponents("/admin/xkgl/ghtj/xkjstj/tpanel2/lwzl.zul",null,args);
				cg.initWindow();
				cg.doHighlighted();
				cg.setTitle("出版学术专著情况");
				}
			}
		});
//		
		c15.addEventListener(Events.ON_CLICK, new EventListener(){
			public void onEvent(Event arg0) throws Exception {
				if(c15.getLabel().trim().equals("0")){
					Messagebox.show("没有发明专利信息!", "提示", Messagebox.OK, Messagebox.INFORMATION);
				}else{
				Map args=new HashMap();
				args.put("type", Short.parseShort("1"));
				args.put("kdid",xyuserole.getKdId());
				FmzlWindow cg=(FmzlWindow)Executions.createComponents("/admin/xkgl/ghtj/xkjstj/tpanel2/fmzl.zul",null,args);
				cg.initWindow();
				cg.doHighlighted();
				cg.setTitle("获授权发明专利情况");
				}
			}
		});
//		
		c16.addEventListener(Events.ON_CLICK, new EventListener(){
			public void onEvent(Event arg0) throws Exception {
				if(c16.getLabel().trim().equals("0")){
					Messagebox.show("没有项目信息!", "提示", Messagebox.OK, Messagebox.INFORMATION);
				}else{
				Map args=new HashMap();
				args.put("kdid", xyuserole.getKdId());
				args.put("lx", GhXm.KYXM);
				args.put("progress", GhXm.PROGRESS_DO);
				GhxmWindow cg=(GhxmWindow)Executions.createComponents("/admin/xkgl/ghtj/xkjstj/tpanel2/kyjyxm.zul",null,args);				
				cg.initWindow();
				cg.doHighlighted();
				cg.setTitle("目前承担的项目情况");
				}
			}
		});
	
		c17.addEventListener(Events.ON_CLICK, new EventListener(){
			public void onEvent(Event arg0) throws Exception {
				if(c17.getLabel().trim().equals("0")){
					Messagebox.show("没有其他科研信息!", "提示", Messagebox.OK, Messagebox.INFORMATION);
				}else{
					Map args=new HashMap();
					args.put("type", Short.parseShort("2"));
					args.put("kdid",xyuserole.getKdId());
				FmzlWindow cg=(FmzlWindow)Executions.createComponents("/admin/xkgl/ghtj/xkjstj/tpanel2/fmzl.zul",null,args);
				cg.initWindow();
				cg.doHighlighted();
				cg.setTitle("科研其他");
				}
			}
		});
//		List list18=kyjhService.findByKdId(user.getKdId());
//		c18.setLabel((list18==null?0:list18.size())+"");//未来五年计划
		
		c18.addEventListener(Events.ON_CLICK, new EventListener(){
			public void onEvent(Event arg0) throws Exception {
				KyjhWindow cg=(KyjhWindow)Executions.createComponents("/admin/xkgl/ghtj/xkjstj/tpanel2/kyjh.zul",null,null);
				cg.setKdid(xyuserole.getKdId());
				cg.initWindow();
				cg.doHighlighted();
				cg.setTitle("未来五年科研教研计划");
			}
		});
		
	
		c21.addEventListener(Events.ON_CLICK, new EventListener(){
			public void onEvent(Event arg0) throws Exception {
				if(c21.getLabel().trim().equals("0")){
					Messagebox.show("没有已完成教研项目信息", "提示", Messagebox.OK, Messagebox.INFORMATION);
				}else{
				Map args=new HashMap();
				args.put("kdid", xyuserole.getKdId());
				args.put("lx", GhXm.JYXM);
				args.put("progress", GhXm.PROGRESS_DONE);
				GhxmWindow cg=(GhxmWindow)Executions.createComponents("/admin/xkgl/ghtj/xkjstj/tpanel2/kyjyxm.zul",null,args);
				cg.initWindow();
				cg.doHighlighted();
				cg.setTitle("已完成教研项目");
				}
			}
		});
	
		c22.addEventListener(Events.ON_CLICK, new EventListener(){
			public void onEvent(Event arg0) throws Exception {
				if(c22.getLabel().trim().equals("0")){
					Messagebox.show("没有获奖项目信息", "提示", Messagebox.OK, Messagebox.INFORMATION);
				}else{
				Map args=new HashMap();
				args.put("kdid", xyuserole.getKdId());
				args.put("lx", GhCg.CG_JY);
				GhcgWindow cg=(GhcgWindow)Executions.createComponents("/admin/xkgl/ghtj/xkjstj/tpanel2/kyjycg.zul",null,args);
				cg.initWindow();
				cg.doHighlighted();
				cg.setTitle("获奖情况");
				}
			}
		});
	
		c23.addEventListener(Events.ON_CLICK, new EventListener(){
			public void onEvent(Event arg0) throws Exception {
				if(c23.getLabel().trim().equals("0")){
					Messagebox.show("没有论文信息", "提示", Messagebox.OK, Messagebox.INFORMATION);
				}else{
				Map args=new HashMap();
				args.put("lx",GhHylw.JYLW);
//				args.put("type", null);
				args.put("kdid",xyuserole.getKdId());
				LwzlWindow cg=(LwzlWindow)Executions.createComponents("/admin/xkgl/ghtj/xkjstj/tpanel2/lwzl.zul",null,args);
				cg.initWindow();
				cg.doHighlighted();
				cg.setTitle("论文情况");
				}
			}
		});

		c24.addEventListener(Events.ON_CLICK, new EventListener(){
			public void onEvent(Event arg0) throws Exception {
				if(c24.getLabel().trim().equals("0")){
					Messagebox.show("没有教材信息", "提示", Messagebox.OK, Messagebox.INFORMATION);
				}else{
				Map args=new HashMap();
				args.put("lx",GhZz.JC);
//				args.put("type", null);
				args.put("kdid",xyuserole.getKdId());
				LwzlWindow cg=(LwzlWindow)Executions.createComponents("/admin/xkgl/ghtj/xkjstj/tpanel2/lwzl.zul",null,args);
				cg.initWindow();
				cg.doHighlighted();
				cg.setTitle("教材情况");
				}
			}
		});

		c25.addEventListener(Events.ON_CLICK, new EventListener(){
			public void onEvent(Event arg0) throws Exception {
				if(c25.getLabel().trim().equals("0")){
					Messagebox.show("没有竞赛信息", "提示", Messagebox.OK, Messagebox.INFORMATION);
				}else{
				PxskWindow cg=(PxskWindow)Executions.createComponents("/admin/xkgl/ghtj/xkjstj/tpanel2/pxsk.zul",null,null);
				cg.setKdid(xyuserole.getKdId());
				cg.setType(1);
				cg.initWindow();
				cg.doHighlighted();
				cg.setTitle("培养学生参加竞赛情况");
				}
			}
		});

		c26.addEventListener(Events.ON_CLICK, new EventListener(){
			public void onEvent(Event arg0) throws Exception {
				if(c26.getLabel().trim().equals("0")){
					Messagebox.show("没有授课信息", "提示", Messagebox.OK, Messagebox.INFORMATION);
				}else{
				PxskWindow cg=(PxskWindow)Executions.createComponents("/admin/xkgl/ghtj/xkjstj/tpanel2/pxsk.zul",null,null);
				cg.setKdid(xyuserole.getKdId());
				cg.setType(2);
				cg.initWindow();
				cg.doHighlighted();
				cg.setTitle("授课情况（2006-2010）");
				}
			}
		});
//	
		c27.addEventListener(Events.ON_CLICK, new EventListener(){
			public void onEvent(Event arg0) throws Exception {
				if(c27.getLabel().trim().equals("0")){
					Messagebox.show("没有教研项目信息", "提示", Messagebox.OK, Messagebox.INFORMATION);
				}else{
				Map args=new HashMap();
				args.put("kdid", xyuserole.getKdId());
				args.put("lx", GhXm.JYXM);
				args.put("progress", GhXm.PROGRESS_DO);
				GhxmWindow cg=(GhxmWindow)Executions.createComponents("/admin/xkgl/ghtj/xkjstj/tpanel2/kyjyxm.zul",null,args);
				cg.initWindow();
				cg.doHighlighted();
				cg.setTitle("目前主持/参加的教研项目");
				}
			}
		});
//		List list28=zsService.findByKdId(user.getKdId());
//		c28.setLabel((list28==null?0:list28.size())+"");//授课
		c28.setLabel("查看");//招生情况
		c28.addEventListener(Events.ON_CLICK, new EventListener(){
			public void onEvent(Event arg0) throws Exception {
				PxskWindow cg=(PxskWindow)Executions.createComponents("/admin/xkgl/ghtj/xkjstj/tpanel2/pxsk.zul",null,null);
				cg.setKdid(xyuserole.getKdId());
				cg.setType(3);
				cg.initWindow();
				cg.doHighlighted();
				cg.setTitle("研究生招生情况");
			}
		});
//		
		c31.addEventListener(Events.ON_CLICK, new EventListener(){
			public void onEvent(Event arg0) throws Exception {
				if(c31.getLabel().trim().equals("0")){
					Messagebox.show("没有已参加的国际国内学术会议信息", "提示", Messagebox.OK, Messagebox.INFORMATION);
				}else{
				Map args=new HashMap();
				args.put("kdid", xyuserole.getKdId());
				args.put("type", 1);
				args.put("cj", GhXshy.IFCJ_YES);
				args.put("state", GhXshy.AUDIT_YES);
				XsjlWindow cg=(XsjlWindow)Executions.createComponents("/admin/xkgl/ghtj/xkjstj/tpanel2/xsjl.zul",null,args);
//				cg.setKdid(xyuserole.getKdId());
//				cg.setType(1);
//				cg.setCj(GhXshy.IFCJ_YES);
				cg.initWindow();
				cg.doHighlighted();
				cg.setTitle("已参加的国际国内学术会议");
				}
			}
		});
//		
		c32.addEventListener(Events.ON_CLICK, new EventListener(){
			public void onEvent(Event arg0) throws Exception {
				if(c32.getLabel().trim().equals("0")){
					Messagebox.show("没有预期参加的国际国内学术会议信息", "提示", Messagebox.OK, Messagebox.INFORMATION);
				}else{
					Map args=new HashMap();
					args.put("kdid", xyuserole.getKdId());
					args.put("type", 1);
					args.put("cj", GhXshy.IFCJ_NO);
					args.put("state",null);
				XsjlWindow cg=(XsjlWindow)Executions.createComponents("/admin/xkgl/ghtj/xkjstj/tpanel2/xsjl.zul",null,args);
			
				cg.initWindow();
				cg.doHighlighted();
				cg.setTitle("预期参加的国际国内学术会议");
				}
			}
		});
	
		c33.addEventListener(Events.ON_CLICK, new EventListener(){
			public void onEvent(Event arg0) throws Exception {
				if(c33.getLabel().trim().equals("0")){
					Messagebox.show("没有参加国外讲学或在国际会议上做报告信息", "提示", Messagebox.OK, Messagebox.INFORMATION);
				}else{
					Map args=new HashMap();
					args.put("kdid", xyuserole.getKdId());
					args.put("type", 2);
					args.put("cj", GhXshy.IFCJ_YES);
					args.put("state",GhXshy.AUDIT_YES);
					XsjlWindow cg=(XsjlWindow)Executions.createComponents("/admin/xkgl/ghtj/xkjstj/tpanel2/xsjl.zul",null,args);
				    cg.initWindow();
				    cg.doHighlighted();
				    cg.setTitle("国外讲学或在国际会议上做报告情况");
				}
			}
		});
	
		c34.addEventListener(Events.ON_CLICK, new EventListener(){
			public void onEvent(Event arg0) throws Exception {
				if(c34.getLabel().trim().equals("0")){
					Messagebox.show("没有预期参加国外讲学或在国际会议上做报告信息", "提示", Messagebox.OK, Messagebox.INFORMATION);
				}else{
					Map args=new HashMap();
					args.put("kdid", xyuserole.getKdId());
					args.put("type", 2);
					args.put("cj", GhJxbg.IFCJ_NO);
					args.put("state",null);	
				XsjlWindow cg=(XsjlWindow)Executions.createComponents("/admin/xkgl/ghtj/xkjstj/tpanel2/xsjl.zul",null,args);
				cg.initWindow();
				cg.doHighlighted();
				cg.setTitle("预期国外讲学或在国际会议上做报告情况");
				}
			}
		});
//	
		c35.addEventListener(Events.ON_CLICK, new EventListener(){
			public void onEvent(Event arg0) throws Exception {
				if(c35.getLabel().trim().equals("0")){
					Messagebox.show("没有承担国际交流合作项目信息", "提示", Messagebox.OK, Messagebox.INFORMATION);
				}else{
					Map args=new HashMap();
					args.put("kdid", xyuserole.getKdId());
					args.put("type", 3);
					args.put("cj", GhXshy.IFCJ_YES);
					args.put("state",GhXshy.AUDIT_YES);	
				    XsjlWindow cg=(XsjlWindow)Executions.createComponents("/admin/xkgl/ghtj/xkjstj/tpanel2/xsjl.zul",null,args);
				    cg.initWindow();
					cg.doHighlighted();
					cg.setTitle("承担国际交流合作项目");
				}
			}
		});
//	
		c36.addEventListener(Events.ON_CLICK, new EventListener(){
			public void onEvent(Event arg0) throws Exception {
				if(c36.getLabel().trim().equals("0")){
					Messagebox.show("预期开展国际交流合作项目信息", "提示", Messagebox.OK, Messagebox.INFORMATION);
				}else{
					Map  args=new HashMap();
					args.put("kdid", xyuserole.getKdId());
					args.put("type", 3);
					args.put("cj", GhXshy.IFCJ_NO);
					args.put("state",null);	
				    XsjlWindow cg=(XsjlWindow)Executions.createComponents("/admin/xkgl/ghtj/xkjstj/tpanel2/xsjl.zul",null,args);
				    cg.initWindow();
				    cg.doHighlighted();
				    cg.setTitle("预期开展国际交流合作项目");
				}
			}
		});
		

	}

	@Override
	public void initWindow() {
		
		xyuserole=getXyUserRole();
		deplist.initdeplist(xyuserole.getKdId());
		yearlist.initAnyyearlist();
		user=(WkTUser)Sessions.getCurrent().getAttribute("user");
		List xmlist=xmService.findSumKdId(getXyUserRole().getKdId(), GhXm.KYXM,GhXm.PROGRESS_DONE,GhXm.AUDIT_YES);
		c11.setLabel(xmlist!=null?xmlist.size()+"":"0");//已获得的科研成果
		List cglist=cgService.findSumKdId(getXyUserRole().getKdId(), GhCg.CG_KY,GhCg.AUDIT_YES);
		c12.setLabel(cglist!=null?cglist.size()+"":"0");//获奖科研成果
		List kylist=hylwService.findSumKdId(xyuserole.getKdId(), GhHylw.KYLW,GhHylw.AUDIT_YES);
		c13.setLabel(kylist!=null?kylist.size()+"":"0");//科研论文
		List lwlist=zzService.findSumKdId(xyuserole.getKdId(), GhZz.ZZ,GhZz.AUDIT_YES);
		c14.setLabel(lwlist!=null?lwlist.size()+"":"0");//科研著作
		List l15=fmzlService.findByKdId(user.getKdId());
		List fmlist=fmzlService.findSumKdId(xyuserole.getKdId(),GhFmzl.AUDIT_YES);
		c15.setLabel(fmlist!=null?fmlist.size()+"":"0");//发明专利
		List xlist=xmService.findSumKdId(xyuserole.getKdId(),GhXm.KYXM,GhXm.PROGRESS_DO,GhXm.AUDIT_YES);
		c16.setLabel(xlist!=null?xlist.size()+"":"0");//当前正在承担的项目
		List qtlist=qtService.findSumKdId(xyuserole.getKdId());
		c17.setLabel(qtlist!=null?qtlist.size()+"":"0");//其他情况
		List l21=xmService.findSumKdId(xyuserole.getKdId(), GhXm.JYXM,GhXm.PROGRESS_DONE,GhXm.AUDIT_YES);
		c21.setLabel(l21!=null?l21.size()+"":"0");//已获得教研成果
		List l22=cgService.findSumKdId(xyuserole.getKdId(), GhCg.CG_JY,GhCg.AUDIT_YES);
		c22.setLabel(l22!=null?l22.size()+"":"0");//获奖教研成果
		List l23=hylwService.findSumKdId(xyuserole.getKdId(), GhHylw.JYLW,GhHylw.AUDIT_YES);
		c23.setLabel(l23!=null?l23.size()+"":"0");//教研论文
		List l24=zzService.findSumKdId(xyuserole.getKdId(), GhZz.JC,GhZz.AUDIT_YES);		
		c24.setLabel(l24!=null?l24.size()+"":"0");//教研教材
		List l25=pxService.findSumKdId(xyuserole.getKdId(),GhPx.AUDIT_YES);
		c25.setLabel(l25!=null?l25.size()+"":"0");//培训
//		List l26=skService.findByKdId(user.getKdId());
		c26.setLabel(skService.findSumKdId(xyuserole.getKdId())+"");//授课
		List l27=xmService.findSumKdId(xyuserole.getKdId(), GhXm.JYXM,GhXm.PROGRESS_DO, GhXm.AUDIT_YES);
		c27.setLabel(l27!=null?l27.size()+"":"0");//当前正在承担的教研项目
		c18.setLabel("查看");//未来五年计划
		
		List l31=xshyService.findSumKdId(user.getKdId(), GhXshy.IFCJ_YES,GhXshy.AUDIT_YES);
		c31.setLabel(l31!=null?l31.size()+"":"0");//学术交流-参加的学术会议
		List l32=xshyService.findSumKdId(xyuserole.getKdId(), GhXshy.IFCJ_NO,null);
		c32.setLabel(l32!=null?l32.size()+"":"0");//学术交流-预参加的学术会议
		List l33=jxbgService.findByKdIdAndCj(xyuserole.getKdId(), GhJxbg.IFCJ_YES,GhJxbg.AUDIT_YES);
		c33.setLabel((l33==null?0:l33.size())+"");
		List l34=jxbgService.findByKdIdAndCj(xyuserole.getKdId(), GhJxbg.IFCJ_NO,null);
		c34.setLabel((l34==null?0:l34.size())+"");
		List l35=jlhzService.findByKdId(user.getKdId(), GhJlhz.IFCJ_YES,GhJlhz.AUDIT_YES);
		c35.setLabel((l35==null?0:l35.size())+"");
		List l36=jlhzService.findByKdId(user.getKdId(), GhJlhz.IFCJ_NO,null);
		c36.setLabel((l36==null?0:l36.size())+"");

	}

	public void onClick$exportKjjl() throws FileNotFoundException, IOException,
			InterruptedException {
		String fpath = this.exportExcel();
		java.io.InputStream is = this.getDesktop().getWebApp()
				.getResourceAsStream(fpath);
		if (is != null) {
			Filedownload.save(is, "text/html", "科教交流汇总.xls");
		} else {
			Messagebox
					.show("服务器不存在此文件 ", "错误", Messagebox.OK, Messagebox.ERROR);
		}
	}
	public String  exportExcel() throws FileNotFoundException, IOException{
		//将excel中数据导入
		POIFSFileSystem fs;
		HSSFWorkbook  wb;
		String filePath = this.getDesktop().getWebApp().getRealPath("/upload/info/plan/");
		fs = new POIFSFileSystem(new FileInputStream(filePath+"\\kyhz.xls"));
		wb = new HSSFWorkbook(fs);   
		HSSFSheet sheet = wb.getSheetAt(0);  
		
		
		Short index_c1=2;
		Short index_c2=4;
		
		HSSFRow  row=sheet.getRow(1); 
		HSSFCell rc1=row.getCell(index_c1);
		HSSFCell rc2=row.getCell(index_c2); 
		setCell(rc1, c11, row, index_c1);
		setCell(rc2, c12, row, index_c2);
		
		row=sheet.getRow(2); 
		rc1=row.getCell(index_c1);
	    rc2=row.getCell(index_c2); 
		setCell(rc1, c13, row, index_c1);
		setCell(rc2, c14, row, index_c2);
		
		row=sheet.getRow(3); 
		rc1=row.getCell(index_c1);
	    rc2=row.getCell(index_c2); 
		setCell(rc1, c15, row, index_c1);
		setCell(rc2, c16, row, index_c2);
		
		row=sheet.getRow(4); 
		rc1=row.getCell(index_c1); 
		setCell(rc1, c17, row, index_c1);
		 
		row=sheet.getRow(5); 
		rc1=row.getCell(index_c1);
	    rc2=row.getCell(index_c2); 
		setCell(rc1, c21, row, index_c1);
		setCell(rc2, c22, row, index_c2);
		
		row=sheet.getRow(6); 
		rc1=row.getCell(index_c1);
	    rc2=row.getCell(index_c2); 
		setCell(rc1, c23, row, index_c1);
		setCell(rc2, c24, row, index_c2);
		
		row=sheet.getRow(7); 
		rc1=row.getCell(index_c1);
	    rc2=row.getCell(index_c2); 
		setCell(rc1, c25, row, index_c1);
		setCell(rc2, c26, row, index_c2);
		
		row=sheet.getRow(8); 
		rc1=row.getCell(index_c1); 
		setCell(rc1, c27, row, index_c1);
		
		row=sheet.getRow(9); 
		rc1=row.getCell(index_c1);
	    rc2=row.getCell(index_c2); 
		setCell(rc1, c31, row, index_c1);
		setCell(rc2, c32, row, index_c2);
		
		row=sheet.getRow(10); 
		rc1=row.getCell(index_c1);
	    rc2=row.getCell(index_c2); 
		setCell(rc1, c33, row, index_c1);
		setCell(rc2, c34, row, index_c2);
		
		row=sheet.getRow(11); 
		rc1=row.getCell(index_c1);
	    rc2=row.getCell(index_c2); 
		setCell(rc1, c35, row, index_c1);
		setCell(rc2, c36, row, index_c2);
		
		
		Date d=new Date();
		String resultPath=filePath+"\\expTemp\\"+d.getTime()+".xls"; 
		File resFile=new File(resultPath);
		FileOutputStream fileOut= new FileOutputStream(resFile);  
		wb.write(fileOut);   
		fileOut.flush();
		fileOut.close();   
		return "/upload/info/plan/expTemp/"+d.getTime()+".xls";
	}
	
	private void setCell(HSSFCell cell,Toolbarbutton tb,HSSFRow  row,Short index){
		if(tb.getLabel().length()==0){
			return;
		}
		if(cell==null){
			System.out.println("cell=null");
			System.out.println(row.getRowNum()+"| "+index);
			cell=row.createCell(index, HSSFCell.CELL_TYPE_STRING);	 
		}else{ 
			 
		}
		cell.setCellValue(tb.getLabel()); 
	}
}
