package org.iti.gh.ghtj.hzqkdc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.entity.GhCg;
import org.iti.gh.entity.GhHylw;
import org.iti.gh.entity.GhJlhz;
import org.iti.gh.entity.GhJslw;
import org.iti.gh.entity.GhJsxm;
import org.iti.gh.entity.GhJxbg;
import org.iti.gh.entity.GhQklw;
import org.iti.gh.entity.GhXm;
import org.iti.gh.entity.GhXshy;
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
import org.iti.gh.service.RjzzService;
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
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;

import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;

public class TjkyqkWindow extends BaseWindow {
	Hbox gjj, sbj, hx, qt ;
	Hbox kyhylw, kyqklw,zz ;
	Hbox gjjjl, sbjjl, qtjl ;
	Hbox gjhy,gnhy,gjjx,gnjx,gjhz,gnhz,qtqk;
	DeptListbox deplist;
	YearListbox yearlist;
	TeacherListbox tealist;
	CgService cgService;
	RjzzService rjzzService;
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
	Map<InnerButton,List> ughmap=new HashMap();
	Map<InnerButton,Short> jlmap=new HashMap();
	WkTUser user;
	//项目
	InnerButton ib1=new InnerButton();InnerButton ib2=new InnerButton();InnerButton ib3=new InnerButton();InnerButton ib4=new InnerButton();
	// 奖励
	InnerButton jl1=new InnerButton();InnerButton jl2=new InnerButton();InnerButton jl3=new InnerButton();
	//会议论文
	InnerButton sci=new InnerButton();InnerButton ei=new InnerButton();InnerButton istp=new InnerButton();InnerButton qthy=new InnerButton();
	InnerButton qksci=new InnerButton();InnerButton qkei=new InnerButton();InnerButton qkistp=new InnerButton();InnerButton qkhx=new InnerButton();
	InnerButton qkqt=new InnerButton();
	//软件著作、发明专利
	InnerButton rz=new InnerButton(); InnerButton fm=new InnerButton();
	//国际国内会议
	InnerButton jh1=new InnerButton();InnerButton jh2=new InnerButton();InnerButton jh3=new InnerButton();
	InnerButton jh4=new InnerButton();InnerButton jh5=new InnerButton();InnerButton jh6=new InnerButton();
	//国际国内讲学
	InnerButton jx1=new InnerButton();InnerButton jx2=new InnerButton();
//	InnerButton jx3=new InnerButton();InnerButton jx4=new InnerButton();
//	InnerButton jx5=new InnerButton();InnerButton jx6=new InnerButton();
	//国际国内合作
	InnerButton hz1=new InnerButton();InnerButton hz2=new InnerButton();
//	InnerButton hz3=new InnerButton();InnerButton hz4=new InnerButton();
//	InnerButton hz5=new InnerButton();InnerButton hz6=new InnerButton();
	
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
		gjj.appendChild(ib1);sbj.appendChild(ib2);hx.appendChild(ib3);qt.appendChild(ib4);
		kyhylw.appendChild(sci);kyhylw.appendChild(ei);kyhylw.appendChild(istp);kyhylw.appendChild(qthy);
		kyqklw.appendChild(qksci);kyqklw.appendChild(qkei);kyqklw.appendChild(qkistp);kyqklw.appendChild(qkhx);kyqklw.appendChild(qkqt);
		zz.appendChild(rz);zz.appendChild(fm);
		gjhy.appendChild(jh1);gjhy.appendChild(jh2);gjhy.appendChild(jh3);
		gnhy.appendChild(jh4);gnhy.appendChild(jh5);gnhy.appendChild(jh6);
		gjjx.appendChild(jx1);gnjx.appendChild(jx2);
		gjhz.appendChild(hz1);gnhz.appendChild(hz2);
//		qtqk;
		ib1.addEventListener(Events.ON_CLICK, new XmInnerListener());
		ib2.addEventListener(Events.ON_CLICK, new XmInnerListener());
		ib3.addEventListener(Events.ON_CLICK, new XmInnerListener());
		ib4.addEventListener(Events.ON_CLICK, new XmInnerListener());
		//论文
		sci.addEventListener(Events.ON_CLICK, new LwInnerListener());
		ei.addEventListener(Events.ON_CLICK, new LwInnerListener());
		istp.addEventListener(Events.ON_CLICK, new LwInnerListener());
		qthy.addEventListener(Events.ON_CLICK, new LwInnerListener());
		qksci.addEventListener(Events.ON_CLICK, new LwInnerListener());
		qkei.addEventListener(Events.ON_CLICK, new LwInnerListener());
		qkistp.addEventListener(Events.ON_CLICK, new LwInnerListener());
		qkhx.addEventListener(Events.ON_CLICK, new LwInnerListener());
		qkqt.addEventListener(Events.ON_CLICK, new LwInnerListener());
		//软著和发明
		rz.addEventListener(Events.ON_CLICK, new RzInnerListener());
		fm.addEventListener(Events.ON_CLICK, new RzInnerListener());
		//交流
		jl1.addEventListener(Events.ON_CLICK, new CgInnerListener());
		jl2.addEventListener(Events.ON_CLICK, new CgInnerListener());
		jl3.addEventListener(Events.ON_CLICK, new CgInnerListener());
		jh1.addEventListener(Events.ON_CLICK, new JlhzInnerListener());
		jh2.addEventListener(Events.ON_CLICK, new JlhzInnerListener());
		jh3.addEventListener(Events.ON_CLICK, new JlhzInnerListener());
		jh4.addEventListener(Events.ON_CLICK, new JlhzInnerListener());
		jh5.addEventListener(Events.ON_CLICK, new JlhzInnerListener());
		jh6.addEventListener(Events.ON_CLICK, new JlhzInnerListener());
		jx1.addEventListener(Events.ON_CLICK, new JlhzInnerListener());
		jx2.addEventListener(Events.ON_CLICK, new JlhzInnerListener());
//		jx3.addEventListener(Events.ON_CLICK, new JlhzInnerListener());
//		jx4.addEventListener(Events.ON_CLICK, new JlhzInnerListener());
//		jx5.addEventListener(Events.ON_CLICK, new JlhzInnerListener());
//		jx6.addEventListener(Events.ON_CLICK, new JlhzInnerListener());
		hz1.addEventListener(Events.ON_CLICK, new JlhzInnerListener());
		hz2.addEventListener(Events.ON_CLICK, new JlhzInnerListener());
//		hz3.addEventListener(Events.ON_CLICK, new JlhzInnerListener());
//		hz4.addEventListener(Events.ON_CLICK, new JlhzInnerListener());
//		hz5.addEventListener(Events.ON_CLICK, new JlhzInnerListener());
//		hz6.addEventListener(Events.ON_CLICK, new JlhzInnerListener());
		
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
				// System.out.println("selectedid==="+selectkdid);
				tealist.initAsignTeacher(rid, selectkdid, xyuserole.getKdId());
			}
		});
		yearlist.initAnyyearlist();
		tealist.initAsignTeacher(rid, xyuserole.getKdId(), xyuserole.getKdId());
		user=(WkTUser)Sessions.getCurrent().getAttribute("user");
		
		initKyGrid(xyuserole.getKdId(),null,null );
		initJlGrid(xyuserole.getKdId(),null,null );
		//initJlHzGrid(xyuserole.getKdId(),null,null);
		initRzGrid(xyuserole.getKdId(),null,null);
		//initLwGrid(xyuserole.getKdId(),null,null);
	}
	public void initKyGrid(Long kdid,String year,Long kuid){
		initXmGrid(kdid,year,kuid);
	}
	
	public void initXmGrid(Long kdid,String year,Long kuid){
		//国家级项目
		List gjls= xmService.findByKdidAndYearAndKuidAndTypeAndJb(kdid, year, kuid, GhJsxm.KYXM, GhXm.GJJ,null);
		List Sbjs=xmService.findByKdidAndYearAndKuidAndTypeAndJb(kdid, year, kuid, GhJsxm.KYXM, GhXm.SBJ,null);
		List hxs=xmService.findByKdidAndYearAndKuidAndTypeAndJb(kdid, year, kuid, GhJsxm.KYXM, null,GhXm.HX);
		List qts=xmService.findQtByKdidAndYearAndKuid(kdid, year, kuid, GhJsxm.KYXM);
		ib1.setLabel(gjls!=null?gjls.size()+"项":"0项");addMap(ib1,gjls);
		ib2.setLabel(Sbjs!=null?Sbjs.size()+"项":"0项");addMap(ib2,Sbjs);
		ib3.setLabel(hxs!=null?hxs.size()+"项":"0项");addMap(ib3,hxs);
		ib4.setLabel(qts!=null?qts.size()+"项":"0项");addMap(ib4,qts);
	
		
	}
//	public void initLwGrid(Long kdid,String year,Long kuid){
//		List hyscilist=hylwService.findtjlw(kdid, year, GhJslw.KYHY, GhHylw.SCI, kuid);
//		List hyeilist=hylwService.findtjlw(kdid, year, GhJslw.KYHY, GhHylw.EI, kuid);
//		List hytplist=hylwService.findtjlw(kdid, year, GhJslw.KYHY, GhHylw.ISTP, kuid);
//		List hyqtlist=hylwService.findQtlw(kdid, year, GhJslw.KYHY, kuid);
//		List qkscilist=qklwService.findByKdidAndYearAndKuidAndTypeAndRecordAndHx(kdid, year, kuid, GhQklw.SCI, null, GhJslw.KYQK);
//		List qkeilist=qklwService.findByKdidAndYearAndKuidAndTypeAndRecordAndHx(kdid, year, kuid, GhQklw.EI, null, GhJslw.KYQK);
//		List qktplist=qklwService.findByKdidAndYearAndKuidAndTypeAndRecordAndHx(kdid, year, kuid, GhQklw.ISTP, null, GhJslw.KYQK);
//		List qkhxlist=qklwService.findByKdidAndYearAndKuidAndTypeAndRecordAndHx(kdid, year, kuid, null, GhQklw.LWHX_YES, GhJslw.KYQK);
//		List qkqtlist=qklwService.findQtlw(kdid, year, kuid,  GhJslw.KYQK);
//		sci.setLabel(hyscilist!=null?hyscilist.size()+"/":"0/");addMap(sci, hyscilist);addJlMap(sci, Short.parseShort("1"));
//		ei.setLabel(hyeilist!=null?hyeilist.size()+"/":"0/");addMap(ei, hyeilist);addJlMap(ei, Short.parseShort("1"));
//		istp.setLabel(hytplist!=null?hytplist.size()+"/":"0/");addMap(istp, hytplist);addJlMap(istp, Short.parseShort("1"));
//		qthy.setLabel(hyqtlist!=null?hyqtlist.size()+"":"0");addMap(qthy, hyqtlist);addJlMap(qthy, Short.parseShort("1"));
//		qksci.setLabel(qkscilist!=null?qkscilist.size()+"/":"0/");addMap(qksci, qkscilist);addJlMap(qksci, Short.parseShort("2"));
//		qkei.setLabel(qkeilist!=null?qkeilist.size()+"/":"0/");addMap(qkei, qkeilist);addJlMap(qkei, Short.parseShort("2"));
//		qkistp.setLabel(qktplist!=null?qktplist.size()+"/":"0/");addMap(qkistp, qktplist);addJlMap(qkistp, Short.parseShort("2"));
//		qkhx.setLabel(qkhxlist!=null?qkhxlist.size()+"/":"0/");addMap(qkhx, qkhxlist);addJlMap(qkhx, Short.parseShort("2"));
//		qkqt.setLabel(qkqtlist!=null?qkqtlist.size()+"":"0");addMap(qkqt,qkqtlist);addJlMap(qkqt,Short.parseShort("2"));
//	}
	public void initRzGrid(Long kdid,String year,Long kuid){
//		List fmlist=fmzlService.findBykdidAndYearAndKuid(kdid, year, kuid);
//		List rjlist=rjzzService.findBykdidAndYearAndKuid(kdid, year, kuid);
//		rz.setLabel(rjlist!=null&&rjlist.size()>0?rjlist.size()+"/":"0/");addMap(rz, rjlist);addJlMap(rz, Short.parseShort("1"));
//		fm.setLabel(fmlist!=null&&fmlist.size()>0?fmlist.size()+"":"0");addMap(fm,fmlist);addJlMap(fm, Short.parseShort("2"));
	}
	public void initJlGrid(Long kdid,String year,Long kuid){
		// gjjjl, sbjjl, qtjl ;
		initjlCgGrid(gjjjl , kdid, year, kuid,GhJsxm.HjKY,GhCg.CG_GJ);
		initjlCgGrid(sbjjl , kdid, year, kuid,GhJsxm.HjKY,GhCg.CG_SB);
		initjlCgGrid(qtjl , kdid, year, kuid,GhJsxm.HjKY,GhCg.CG_QT);
	}
	/**
	 * 
	 * @param ib
	 * @param kdid
	 * @param year
	 * @param kuid
	 * @param type
	 * @param lx
	 */
	public void initjlCgGrid(Hbox ib ,Long kdid,String year,Long kuid,Integer type,Short lx){
		List ch=ib.getChildren();
		if(ch!=null&&ch.size()>0){
			for(int j=0;j<ch.size();j++){
				InnerButton I=(InnerButton)ch.get(j);
				I.detach();
			}
		}
//		List gjlist=cgService.findByKdidAndYearAndKuid(kdid,year,kuid,type,lx);
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
	
//	public void initJlHzGrid(Long kdid,String year,Long kuid ){
//		List jh1ist=xshyService.findByKdidAndYearAndKuidAndIfcjAndLx(kdid, year, kuid, GhXshy.IFCJ_YES, GhXshy.GJHY, GhXshy.EFF_ZC);
//		List jh2ist=xshyService.findByKdidAndYearAndKuidAndIfcjAndLx(kdid, year, kuid, GhXshy.IFCJ_YES, GhXshy.GJHY, GhXshy.EFF_CJ);
//		List jh3ist=xshyService.findByKdidAndYearAndKuidAndIfcjAndLx(kdid, year, kuid, GhXshy.IFCJ_YES, GhXshy.GJHY, GhXshy.EFF_DL);
//		List jh4ist=xshyService.findByKdidAndYearAndKuidAndIfcjAndLx(kdid, year, kuid, GhXshy.IFCJ_YES, GhXshy.GNHY, GhXshy.EFF_ZC);
//		List jh5ist=xshyService.findByKdidAndYearAndKuidAndIfcjAndLx(kdid, year, kuid, GhXshy.IFCJ_YES, GhXshy.GNHY, GhXshy.EFF_CJ);
//		List jh6ist=xshyService.findByKdidAndYearAndKuidAndIfcjAndLx(kdid, year, kuid, GhXshy.IFCJ_YES, GhXshy.GNHY, GhXshy.EFF_DL);
//		List jx1list=jxbgService.findByKdidAndYearAndKuidAndIfcj(kdid, year, kuid, GhJxbg.IFCJ_YES, GhJxbg.GJJX);
//		List jx2list=jxbgService.findByKdidAndYearAndKuidAndIfcj(kdid, year, kuid, GhJxbg.IFCJ_YES, GhJxbg.GNJX);
//		List hz1list=jlhzService.findBykdidAndYearAndKuidAndifcj(kdid, year, kuid, GhJlhz.IFCJ_YES, GhJlhz.GJHZ);
//		List hz2list=jlhzService.findBykdidAndYearAndKuidAndifcj(kdid, year, kuid, GhJlhz.IFCJ_YES, GhJlhz.GNHZ);
//		if(jh1ist!=null&&jh1ist.size()>0){
//			jh1.setLabel(jh1ist.size()+"/");
//		}else{
//			jh1.setLabel(0+"/");
//		}
//		if(jh2ist!=null&&jh2ist.size()>0){
//			jh2.setLabel(jh2ist.size()+"/");
//		}else{
//			jh2.setLabel(0+"/");
//		}
//		if(jh3ist!=null&&jh3ist.size()>0){
//			jh3.setLabel(jh3ist.size()+"");
//		}else{
//			jh3.setLabel("0");
//		}
//		if(jh4ist!=null&&jh4ist.size()>0){
//			jh4.setLabel(jh4ist.size()+"/");
//		}else{
//			jh4.setLabel("0/");
//		}
//		if(jh5ist!=null&&jh5ist.size()>0){
//			jh5.setLabel(jh5ist.size()+"/");
//		}else{
//			jh5.setLabel("0/");
//		}
//		if(jh6ist!=null&&jh6ist.size()>0){
//			jh6.setLabel(jh6ist.size()+"");
//		}else{
//			jh6.setLabel("0");
//		}
//		if(jx1list!=null&&jx1list.size()>0){
//			jx1.setLabel(jx1list.size()+"");
//		}else{
//			jx1.setLabel("0");
//		}
//		if(jx2list!=null&&jx2list.size()>0){
//			jx2.setLabel(jx2list.size()+"");
//		}else{
//			jx2.setLabel("0");
//		}
//		if(hz1list!=null&&hz1list.size()>0){
//			hz1.setLabel(hz1list.size()+"");
//		}else{
//			hz1.setLabel("0");
//		}
//		if(hz2list!=null&&hz2list.size()>0){
//			hz2.setLabel(hz2list.size()+"");
//		}else{
//			hz2.setLabel("0");
//		}
//		
//		addMap(jh1,jh1ist);addMap(jh2,jh2ist);addMap(jh3,jh3ist);
//		addMap(jh4,jh4ist);addMap(jh5,jh5ist);addMap(jh6,jh6ist);
//		addMap(jx1,jx1list);addMap(jx2,jx2list);
//		addMap(hz1,hz1list);addMap(hz2,hz2list);
//		addJlMap(jh1, Short.parseShort("1"));addJlMap(jh2, Short.parseShort("1"));
//		addJlMap(jh3, Short.parseShort("1"));addJlMap(jh4, Short.parseShort("1"));
//		addJlMap(jh5, Short.parseShort("1"));addJlMap(jh6, Short.parseShort("1"));
//		
//		addJlMap(jx1, Short.parseShort("2"));addJlMap(jx2, Short.parseShort("2"));
//		addJlMap(hz1, Short.parseShort("3"));addJlMap(hz2, Short.parseShort("3"));
//	}
	
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
	class RzInnerListener implements EventListener{

		public void onEvent(Event arg0) throws Exception {

			InnerButton ib=(InnerButton)arg0.getTarget();
			if(ib.getLabel().trim().equals("0")||ib.getLabel().trim().equals("0/")){
				Messagebox.show("没有数据","警告",Messagebox.OK,Messagebox.EXCLAMATION);
			}else{
				Map args=new HashMap();
				args.put("rzlist", ughmap.get(ib));
				args.put("type",jlmap.get(ib));
				RjzzxqWindow cw=(RjzzxqWindow)Executions.createComponents("/admin/xkgl/ghtj/kyqk/rz.zul", null, args);
				cw.initWindow();
				cw.doHighlighted();
				
			}
		
			
		}
		
	}
	class JlhzInnerListener implements EventListener{

		public void onEvent(Event arg0) throws Exception {
			InnerButton ib=(InnerButton)arg0.getTarget();
			if(ib.getLabel().trim().equals("0")||ib.getLabel().trim().equals("0/")){
				Messagebox.show("没有数据","警告",Messagebox.OK,Messagebox.EXCLAMATION);
			}else{
				Map args=new HashMap();
				args.put("hylist", ughmap.get(ib));
				args.put("lx", jlmap.get(ib));
				JlhzWindow cw=(JlhzWindow)Executions.createComponents("/admin/xkgl/ghtj/kyqk/jlhz.zul", null, args);
				cw.initWindow();
				cw.doHighlighted();
				
			}
		}
		
	}
	
	public void addMap(InnerButton tb,List list){
//		Map ughmap=new HashMap();
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
		initKyGrid(kdid,year,kuid);
		initJlGrid(kdid,year,kuid);
		//initJlHzGrid(kdid,year,kuid);
		initRzGrid(kdid,year,kuid);
		//initLwGrid(kdid,year,kuid);
	}
	
}
