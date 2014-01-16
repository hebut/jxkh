package org.iti.gh.ghtj.hzqkdc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.entity.GhCg;
import org.iti.gh.entity.GhHylw;
import org.iti.gh.entity.GhJslw;
import org.iti.gh.entity.GhJsxm;
import org.iti.gh.entity.GhQklw;
import org.iti.gh.entity.GhXm;
import org.iti.gh.entity.GhZz;
import org.iti.gh.ghtj.hzqkdc.TjkyqkWindow.CgInnerListener;
import org.iti.gh.ghtj.hzqkdc.TjkyqkWindow.LwInnerListener;
import org.iti.gh.ghtj.hzqkdc.TjkyqkWindow.XmInnerListener;
import org.iti.gh.service.CgService;
import org.iti.gh.service.FmzlService;
import org.iti.gh.service.HylwService;
import org.iti.gh.service.JlhzService;
import org.iti.gh.service.JxbgService;
import org.iti.gh.service.KyjhService;
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
import org.iti.gh.ui.listbox.TeacherListbox;
import org.iti.gh.ui.listbox.YearListbox;
import org.iti.xypt.entity.Teacher;
import org.iti.xypt.entity.XyUserrole;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;

import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;

public class TjjxqkWindow extends BaseWindow {

	DeptListbox deplist;
	YearListbox yearlist;
	TeacherListbox tealist;
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
	Button exportKjjl,query;
	Hbox gjxm,sbxm,hxxm,qtxm,hylw,qklw,kyzz,cbjc,gjjl,sbjl,qtjl;
	WkTUser user;
	Map<InnerButton,List> ughmap=new HashMap();
	Map<InnerButton,Short> jlmap=new HashMap();
	//项目
	InnerButton ib1=new InnerButton();InnerButton ib2=new InnerButton();InnerButton ib3=new InnerButton();InnerButton ib4=new InnerButton();
	//奖励
	InnerButton jl1=new InnerButton();InnerButton jl2=new InnerButton();InnerButton jl3=new InnerButton();
	//论文
	InnerButton hysci=new InnerButton();InnerButton hyei=new InnerButton();InnerButton hyistp=new InnerButton();InnerButton qthy=new InnerButton();
	InnerButton qksci=new InnerButton();InnerButton qkei=new InnerButton();InnerButton qkistp=new InnerButton();InnerButton qkhx=new InnerButton();
	InnerButton qkqt=new InnerButton();
	//科研专著和出版教材
	InnerButton zz=new InnerButton();InnerButton jc=new InnerButton();
	@Override
	public void initShow() {
		deplist.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				WkTDept dept = (WkTDept) arg1;
				arg0.setValue(dept);
				arg0.setLabel(dept.getKdName());
			}
		});
		tealist.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				WkTUser user = (WkTUser) arg1;
				arg0.setValue(user);
				arg0.setLabel(user.getKuName());
			}
		});
		//项目
		gjxm.appendChild(ib1);sbxm.appendChild(ib2);hxxm.appendChild(ib3);qtxm.appendChild(ib4);
		ib1.addEventListener(Events.ON_CLICK, new XmInnerListener());
		ib2.addEventListener(Events.ON_CLICK, new XmInnerListener());
		ib3.addEventListener(Events.ON_CLICK, new XmInnerListener());
		ib4.addEventListener(Events.ON_CLICK, new XmInnerListener());
		//论文
		hylw.appendChild(hysci);hylw.appendChild(hyei);hylw.appendChild(hyistp);hylw.appendChild(qthy);
		qklw.appendChild(qksci);qklw.appendChild(qkei);qklw.appendChild(qkistp);qklw.appendChild(qkhx);
		qklw.appendChild(qkqt);
		hysci.addEventListener(Events.ON_CLICK, new LwInnerListener());
		hyei.addEventListener(Events.ON_CLICK, new LwInnerListener());
		hyistp.addEventListener(Events.ON_CLICK, new LwInnerListener());
		qthy.addEventListener(Events.ON_CLICK, new LwInnerListener());
		qksci.addEventListener(Events.ON_CLICK, new LwInnerListener());
		qkei.addEventListener(Events.ON_CLICK, new LwInnerListener());
		qkistp.addEventListener(Events.ON_CLICK, new LwInnerListener());
		qkhx.addEventListener(Events.ON_CLICK, new LwInnerListener());
		qkqt.addEventListener(Events.ON_CLICK, new LwInnerListener());
		//出版教材和科研专著
		kyzz.appendChild(zz);cbjc.appendChild(jc);
		zz.addEventListener(Events.ON_CLICK, new ZJInnerListener());
		jc.addEventListener(Events.ON_CLICK, new ZJInnerListener());
	}

	@Override
	public void initWindow() {
		xyuserole=getXyUserRole();
		Teacher tea = new Teacher();
		final Long rid = tea.getRoleId(xyuserole.getDept().getKdSchid());
		deplist.initdeplistxy(xyuserole.getKdId());
		deplist.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				Long selectkdid = Long.valueOf("0");
				if (deplist.getSelectedItem() == null) {
					WkTDept wd = (WkTDept) deplist.getModel().getElementAt(0);
					selectkdid = wd.getKdId();
				} else {
					WkTDept wd = (WkTDept) deplist.getSelectedItem().getValue();
					selectkdid = wd.getKdId();
				}
				tealist.initAsignTeacher(rid, selectkdid, xyuserole.getKdId());
			}
		});
		yearlist.initAnyyearlist();
		tealist.initAsignTeacher(rid, xyuserole.getKdId(), xyuserole.getKdId());
		user=(WkTUser)Sessions.getCurrent().getAttribute("user");
		initJyGrid(xyuserole.getKdId(),null,null );
		initJlGrid(xyuserole.getKdId(),null,null );
		initJCgGrid(xyuserole.getKdId(),null,null );
	}
	public void initJyGrid(Long kdid,String year,Long kuid){
		initXmGrid(kdid,year,kuid);
	}
	public void initJCgGrid(Long kdid,String year,Long kuid){
		initCgGrid(kdid,year,kuid);
	}
	public void initXmGrid(Long kdid,String year,Long kuid){
		//项目
		List gjls= xmService.findByKdidAndYearAndKuidAndTypeAndJb(kdid, year, kuid, GhJsxm.JYXM, GhXm.GJJ,null);
		List Sbjs=xmService.findByKdidAndYearAndKuidAndTypeAndJb(kdid, year, kuid, GhJsxm.JYXM, GhXm.SBJ,null);
		List hxs=xmService.findByKdidAndYearAndKuidAndTypeAndJb(kdid, year, kuid, GhJsxm.JYXM, null,GhXm.HX);
		List qts=xmService.findQtByKdidAndYearAndKuid(kdid, year, kuid, GhJsxm.JYXM);
		//System.out.println("各项目类型的个数："+gjls.size()+","+Sbjs.size()+","+hxs.size()+","+qts.size());
		ib1.setLabel(gjls!=null?gjls.size()+"项":"0项");addMap(ib1,gjls);
		ib2.setLabel(Sbjs!=null?Sbjs.size()+"项":"0项");addMap(ib2,Sbjs);
		ib3.setLabel(hxs!=null?hxs.size()+"项":"0项");addMap(ib3,hxs);
		ib4.setLabel(qts!=null?qts.size()+"项":"0项");
		addMap(ib4,qts);
		
	}
	public void initCgGrid(Long kdid,String year,Long kuid){
		//成果(会议论文、期刊论文)
//		List hyscilist=hylwService.findtjlw(kdid, year, GhJslw.JYHY, GhHylw.SCI, kuid);
//		List hyeilist=hylwService.findtjlw(kdid, year, GhJslw.JYHY, GhHylw.EI, kuid);
//		List hyistplist=hylwService.findtjlw(kdid, year, GhJslw.JYHY, GhHylw.ISTP, kuid);
//		List hyqtlist=hylwService.findQtlw(kdid, year, GhJslw.JYHY, kuid);
//		List qkscilist=qklwService.findByKdidAndYearAndKuidAndTypeAndRecordAndHx(kdid, year, kuid, GhQklw.SCI, null, GhJslw.JYQK);
//		List qkeilist=qklwService.findByKdidAndYearAndKuidAndTypeAndRecordAndHx(kdid, year, kuid, GhQklw.EI, null, GhJslw.JYQK);
//		List qktplist=qklwService.findByKdidAndYearAndKuidAndTypeAndRecordAndHx(kdid, year, kuid, GhQklw.ISTP, null, GhJslw.JYQK);
//		List qkhxlist=qklwService.findByKdidAndYearAndKuidAndTypeAndRecordAndHx(kdid, year, kuid, null, GhQklw.LWHX_YES, GhJslw.JYQK);
//		List qkqtlist=qklwService.findQtlw(kdid, year, kuid, GhJslw.JYQK);
//		hysci.setLabel(hyscilist!=null?hyscilist.size()+"/":"0/");addMap(hysci, hyscilist);addJlMap(hysci, Short.parseShort("1"));
//		hyei.setLabel(hyeilist!=null?hyeilist.size()+"/":"0/");addMap(hyei, hyeilist);addJlMap(hyei, Short.parseShort("1"));
//		hyistp.setLabel(hyistplist!=null?hyistplist.size()+"/":"0/");addMap(hyistp, hyistplist);addJlMap(hyistp, Short.parseShort("1"));
//		qthy.setLabel(hyqtlist!=null?hyqtlist.size()+"":"0");addMap(qthy, hyqtlist);addJlMap(qthy, Short.parseShort("1"));
//		qksci.setLabel(qkscilist!=null?qkscilist.size()+"/":"0/");addMap(qksci, qkscilist);addJlMap(qksci, Short.parseShort("2"));
//		qkei.setLabel(qkeilist!=null?qkeilist.size()+"/":"0/");addMap(qkei, qkeilist);addJlMap(qkei, Short.parseShort("2"));
//		qkistp.setLabel(qktplist!=null?qktplist.size()+"/":"0/");addMap(qkistp, qktplist);addJlMap(qkistp, Short.parseShort("2"));
//		qkhx.setLabel(qkhxlist!=null?qkhxlist.size()+"/":"0/");addMap(qkhx, qkhxlist);addJlMap(qkhx, Short.parseShort("2"));
//	    qkqt.setLabel(qkqtlist!=null?qkqtlist.size()+"":"0");addMap(qkqt,qkqtlist);addJlMap(qkqt,Short.parseShort("2"));
		//科研专著、出版教材
		List zzlist=zzService.findtjzj(kdid,year,GhZz.ZZ,kuid);
		List jclist=zzService.findtjzj(kdid, year, GhZz.JC, kuid);
		//System.out.println("科研专著、出版教材:"+zzlist.size()+","+jclist.size());
		zz.setLabel(zzlist!=null?zzlist.size()+"":"0");addMap(zz, zzlist);addJlMap(zz, Short.parseShort("2"));
		jc.setLabel(jclist!=null?jclist.size()+"":"0");addMap(jc, jclist);addJlMap(jc, Short.parseShort("1"));
	}
	public void initJlGrid(Long kdid,String year,Long kuid){
		// 奖励
		initjlCgGrid(gjjl , kdid, year, kuid,GhJsxm.HJJY,GhCg.CG_GJ);
		initjlCgGrid(sbjl , kdid, year, kuid,GhJsxm.HJJY,GhCg.CG_SB);
		initjlCgGrid(qtjl , kdid, year, kuid,GhJsxm.HJJY,GhCg.CG_QT);
	}
	public void initjlCgGrid(Hbox ib ,Long kdid,String year,Long kuid,Integer type,Short lx){
		List ch=ib.getChildren();
		if(ch!=null&&ch.size()>0){
			for(int j=0;j<ch.size();j++){
				InnerButton I=(InnerButton)ch.get(j);
				I.detach();
			}
		}
	//	List gjlist=cgService.findByKdidAndYearAndKuid(kdid,year,kuid,type,lx);
		//System.out.println(gjlist.size()+"成果的个数");
//		if (gjlist != null && gjlist.size() > 0) {
//			for (int i = 0; i < gjlist.size(); i++) {
//				Object[] c = (Object[]) gjlist.get(i);
//				InnerButton ib1=new InnerButton((String)c[0]+"/"+(Long)c[1]+"项");
//				if(i<gjlist.size()-1){
//					ib1.setLabel(ib1.getLabel()+"、" );
//				}
//				List cglist=cgService.findBykdidAndYearAndKuidAndCgmcAndType(kdid,year,kuid,type,lx,((String)c[0]).trim());
//				addMap(ib1,cglist);ib1.addEventListener(Events.ON_CLICK, new CgInnerListener());
//				ib.appendChild(ib1);
//			}
//		}else{
//			InnerButton ib1=new InnerButton("未知");
//			ib.appendChild(ib1);
//		}
//		
	}
	class XmInnerListener implements EventListener{

		public void onEvent(Event arg0) throws Exception {
			InnerButton l=(InnerButton)arg0.getTarget();
			if(l.getLabel().trim().equals("0")||l.getLabel().trim().equals("0/")){
				Messagebox.show("没有数据","警告",Messagebox.OK,Messagebox.EXCLAMATION);
			}else{
				Map args=new HashMap();
				args.put("xmlist", ughmap.get(l));
				XmxqWindow xw=(XmxqWindow)Executions.createComponents("/admin/xkgl/ghtj/kyqk/xm.zul", null, args);
				xw.initWindow();
				xw.doHighlighted();
			}
			
		}
	}
	class LwInnerListener implements EventListener{

		public void onEvent(Event arg0) throws Exception {
			InnerButton ib=(InnerButton)arg0.getTarget();
			if(ib.getLabel().trim().equals("0")||ib.getLabel().trim().equals("0/")){
				Messagebox.show("没有数据","警告",Messagebox.OK,Messagebox.EXCLAMATION);
			}else{
				Map args=new HashMap();
				args.put("lwlist", ughmap.get(ib));
				args.put("type", jlmap.get(ib));
				LwxqWindow cw=(LwxqWindow)Executions.createComponents("/admin/xkgl/ghtj/kyqk/lw.zul", null, args);
				cw.initWindow();
				cw.doHighlighted();
				
			}
		}
		
	}
	class ZJInnerListener implements EventListener{

		public void onEvent(Event arg0) throws Exception {
			InnerButton ib=(InnerButton)arg0.getTarget();
			if(ib.getLabel().trim().equals("0")){
				Messagebox.show("没有数据","警告",Messagebox.OK,Messagebox.EXCLAMATION);
			}else{
				Map args=new HashMap();
				args.put("zjlist", ughmap.get(ib));
				args.put("type", jlmap.get(ib));
				ZjqkWindow cw=(ZjqkWindow)Executions.createComponents("/admin/xkgl/ghtj/jxqk/zj.zul", null, args);
				cw.initWindow();
				cw.doHighlighted();
				
			}
			
		}
		
	}
	class CgInnerListener implements EventListener{

		public void onEvent(Event arg0) throws Exception {
			InnerButton ib=(InnerButton)arg0.getTarget();
			if(ib.getLabel().trim().equals("0")||ib.getLabel().trim().equals("0/")){
				Messagebox.show("没有数据","警告",Messagebox.OK,Messagebox.EXCLAMATION);
			}else{
				Map args=new HashMap();
				args.put("cglist", ughmap.get(ib));
				CgxqWindow cw=(CgxqWindow)Executions.createComponents("/admin/xkgl/ghtj/kyqk/cg.zul", null, args);
				cw.initWindow();
				cw.doHighlighted();
				
			}
		}
		
	}
	public void addMap(InnerButton tb,List list){
		ughmap.put(tb, list);
	}
	public void addJlMap(InnerButton tb,Short lx){
		jlmap.put(tb, lx);
	}
	public void onClick$query(){
		Long kdid,kuid;
		String year;
		if(deplist.getSelectedIndex()!=0){
			kdid=((WkTDept)deplist.getSelectedItem().getValue()).getKdId();
		}else{
			kdid=xyuserole.getKdId();
		}
		if(yearlist.getSelectedIndex()!=0){
			year=yearlist.getSelectedItem().getLabel().substring(0, 4);
		}else{
			year=null;
		}
		if(tealist.getSelectedIndex()!=0){
			kuid=((WkTUser)tealist.getSelectedItem().getValue()).getKuId();
		}else{
			kuid=null;
		}
		initJyGrid(kdid,year,kuid);
		initJlGrid(kdid,year,kuid);
		initJCgGrid(kdid,year,kuid);
	}
}
