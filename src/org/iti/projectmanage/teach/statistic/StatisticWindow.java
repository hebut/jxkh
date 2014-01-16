package org.iti.projectmanage.teach.statistic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.iti.gh.common.util.DateUtil;
import org.iti.gh.common.util.ExportExcel;
import org.iti.gh.entity.GhCg;
import org.iti.gh.entity.GhFmzl;
import org.iti.gh.entity.GhHylw;
import org.iti.gh.entity.GhJlhz;
import org.iti.gh.entity.GhJslw;
import org.iti.gh.entity.GhJsxm;
import org.iti.gh.entity.GhJxbg;
import org.iti.gh.entity.GhQklw;
import org.iti.gh.entity.GhXm;
import org.iti.gh.entity.GhXmzz;
import org.iti.gh.entity.GhXshy;
import org.iti.gh.entity.GhZz;
import org.iti.gh.service.CgService;
import org.iti.gh.service.FmzlService;
import org.iti.gh.service.HylwService;
import org.iti.gh.service.JlhzService;
import org.iti.gh.service.JxbgService;
import org.iti.gh.service.QklwService;
import org.iti.gh.service.RjzzService;
import org.iti.gh.service.XshyService;
import org.iti.gh.service.ZzService;
import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxgl.entity.JxYear;
import org.iti.projectmanage.science.member.service.ProjectMemberService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTUser;

public  class StatisticWindow extends Window implements AfterCompose {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	WkTUser user; 

	Label level1,level2,level3;//纵向-级别
	Label level21,level22,level23;//横向-级别
	Label stage1,stage2,stage3;//纵向-进展
	Label stage21,stage22,stage23;//横向-进展
	Label hysci1,hyei1,hyistp1,hyother1,yiban1,hexin1,qksci1,qkei1,qkistp1,qkother1;//纵向-论文
	Label hysci2,hyei2,hyistp2,hyother2,yiban2,hexin2,qksci2,qkei2,qkistp2,qkother2;//横向-论文
	Label gjjl1,sbjl1,qtjl1;//纵向-奖励
	Label gjjl2,sbjl2,qtjl2;//横向-奖励
   Label jiaocai1,zhuanzhu1;//纵向-出版教材、学术专著
   Label jiaocai2,zhuanzhu2;//横向-出版教材、学术专著
	YearListbox yearlist;
	Listbox projectlist;
	List plist = new ArrayList();
	ListModelList projectModelList;
	Tab zx,hx;
	ProjectMemberService projectmemberService;
	HylwService hylwService;
	QklwService qklwService;
	FmzlService fmzlService;
	RjzzService rjzzService;
	ZzService zzService;
	CgService cgService;
	XshyService xshyService;
	JxbgService jxbgService;
	JlhzService jlhzService;
	Map jbmap=new HashMap();
	Map typemap=new HashMap();
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);	

		user=(WkTUser)Sessions.getCurrent().getAttribute("user");	
		yearlist.initAnyyearlist();
		/**
		 * 项目基本、进展
		 */
		//纵向		

		level1.addEventListener(Events.ON_CLICK, new XmInnerListener());
		level2.addEventListener(Events.ON_CLICK, new XmInnerListener());
		level3.addEventListener(Events.ON_CLICK, new XmInnerListener());
		stage1.addEventListener(Events.ON_CLICK, new XmInnerListener());
		stage2.addEventListener(Events.ON_CLICK, new XmInnerListener());
		stage3.addEventListener(Events.ON_CLICK, new XmInnerListener());
		
		//横向
		level21.addEventListener(Events.ON_CLICK, new XmInnerListener());
		level22.addEventListener(Events.ON_CLICK, new XmInnerListener());
		level23.addEventListener(Events.ON_CLICK, new XmInnerListener());
		stage21.addEventListener(Events.ON_CLICK, new XmInnerListener());
		stage22.addEventListener(Events.ON_CLICK, new XmInnerListener());
		stage23.addEventListener(Events.ON_CLICK, new XmInnerListener());
		/**
		 * 论文
		 */
		//纵向
		hysci1.addEventListener(Events.ON_CLICK, new LwInnerListener());
		hyei1.addEventListener(Events.ON_CLICK, new LwInnerListener());
		hyistp1.addEventListener(Events.ON_CLICK, new LwInnerListener());
		hyother1.addEventListener(Events.ON_CLICK, new LwInnerListener());
		yiban1.addEventListener(Events.ON_CLICK, new LwInnerListener());
		hexin1.addEventListener(Events.ON_CLICK, new LwInnerListener());
		qksci1.addEventListener(Events.ON_CLICK, new LwInnerListener());
		qkei1.addEventListener(Events.ON_CLICK, new LwInnerListener());
		qkistp1.addEventListener(Events.ON_CLICK, new LwInnerListener());
		qkother1.addEventListener(Events.ON_CLICK, new LwInnerListener());
		//横向
		hysci2.addEventListener(Events.ON_CLICK, new LwInnerListener());
		hyei2.addEventListener(Events.ON_CLICK, new LwInnerListener());
		hyistp2.addEventListener(Events.ON_CLICK, new LwInnerListener());
		hyother2.addEventListener(Events.ON_CLICK, new LwInnerListener());
		yiban2.addEventListener(Events.ON_CLICK, new LwInnerListener());
		hexin2.addEventListener(Events.ON_CLICK, new LwInnerListener());
		qksci2.addEventListener(Events.ON_CLICK, new LwInnerListener());
		qkei2.addEventListener(Events.ON_CLICK, new LwInnerListener());
		qkistp2.addEventListener(Events.ON_CLICK, new LwInnerListener());
		qkother2.addEventListener(Events.ON_CLICK, new LwInnerListener());
	
		/**
		 * 奖励
		 */
		//纵向
		qtjl1.addEventListener(Events.ON_CLICK, new JlInnerListener());
		gjjl1.addEventListener(Events.ON_CLICK, new JlInnerListener());
		sbjl1.addEventListener(Events.ON_CLICK, new JlInnerListener());
		//横向
		qtjl2.addEventListener(Events.ON_CLICK, new JlInnerListener());
		gjjl2.addEventListener(Events.ON_CLICK, new JlInnerListener());
		sbjl2.addEventListener(Events.ON_CLICK, new JlInnerListener());
		//initWindow(null);
		/**
		 * 教材和专著
		 * 
		 */
		//纵向
		jiaocai1.addEventListener(Events.ON_CLICK, new JCInnerListener());
		zhuanzhu1.addEventListener(Events.ON_CLICK, new ZZInnerListener());
		
		//横向
		jiaocai2.addEventListener(Events.ON_CLICK, new JCInnerListener());
		zhuanzhu2.addEventListener(Events.ON_CLICK, new ZZInnerListener());
		

		initZXWindow(null);
		yearlist.addEventListener(Events.ON_SELECT, new EventListener()
		{
			public void onEvent(Event arg0) throws Exception {
				if(yearlist.getSelectedIndex()!=0){
				String	year=yearlist.getSelectedItem().getLabel().substring(0, 4);
			    if(zx.isSelected())
			    {
			    	List project=projectmemberService.findByKdidAndYearAndKuidAndTypeAndJb(user.getKuId(),year,GhJsxm.JYXM,null,GhXm.ZX,null);
			    	plist.clear();
			    	GhXm xm = new GhXm();
					xm.setKyMc("-请选择-");
					plist.add(xm.getKyMc());
					for(int i=0;i<project.size();i++)
					{
						GhXm xm1=(GhXm)project.get(i);
						plist.add(i+1, xm1.getKyMc());
					}
					projectModelList = new ListModelList();
					projectModelList.addAll(plist);
					projectlist.setModel(projectModelList);
					projectlist.setSelectedIndex(0);
			    }
			    else if(hx.isSelected())
			    {
			    	List project=projectmemberService.findByKdidAndYearAndKuidAndTypeAndJb(user.getKuId(),year,GhJsxm.JYXM,null,GhXm.HX,null);
			    	plist.clear();
			    	GhXm xm = new GhXm();
					xm.setKyMc("-请选择-");
					plist.add(xm.getKyMc());
					for(int i=0;i<project.size();i++)
					{
						GhXm xm1=(GhXm)project.get(i);
						plist.add(i+1, xm1.getKyMc());
					}
					projectModelList = new ListModelList();
					projectModelList.addAll(plist);
					projectlist.setModel(projectModelList);
					projectlist.setSelectedIndex(0);
			    }
				}
				
			}
			
		});

	}
	
	
	public void initZXWindow(String year)
	{

		/**
		 * 统计项目级别
		 */
		//纵向
		List gjlist1=projectmemberService.findByKdidAndYearAndKuidAndTypeAndJb(user.getKuId(), year,GhJsxm.JYXM, "2",GhXm.ZX,null);//国家级
		List sblist1=projectmemberService.findByKdidAndYearAndKuidAndTypeAndJb(user.getKuId(), year,GhJsxm.JYXM, "3",GhXm.ZX,null);//省部级
		List qtlist1=projectmemberService.findQtByYearAndKuid(year,user.getKuId(),GhJsxm.JYXM, GhXm.ZX);//其它
	    level1.setValue(gjlist1!=null?gjlist1.size()+"":"0");
	    level1.setStyle("color:blue");
	    level2.setValue(sblist1!=null?sblist1.size()+"":"0");
	    level2.setStyle("color:blue");
	    level3.setValue(qtlist1!=null?qtlist1.size()+"":"0");
	    level3.setStyle("color:blue");
	    jbmap.put(level1,gjlist1);
	    jbmap.put(level2,sblist1);
	    jbmap.put(level3,qtlist1);
	    //横向
	    List gjlist2=projectmemberService.findByKdidAndYearAndKuidAndTypeAndJb(user.getKuId(), year,GhJsxm.JYXM, "2",GhXm.HX,null);//国家级
		List sblist2=projectmemberService.findByKdidAndYearAndKuidAndTypeAndJb(user.getKuId(), year,GhJsxm.JYXM, "3",GhXm.HX,null);//省部级
		List qtlist2=projectmemberService.findQtByYearAndKuid(year,user.getKuId(),GhJsxm.JYXM, GhXm.HX);//其它
	    level21.setValue(gjlist2!=null?gjlist2.size()+"":"0");
	    level21.setStyle("color:blue");
	    level22.setValue(sblist2!=null?sblist2.size()+"":"0");
	    level22.setStyle("color:blue");
	    level23.setValue(qtlist2!=null?qtlist2.size()+"":"0");
	    level23.setStyle("color:blue");
	    jbmap.put(level21,gjlist2);
	    jbmap.put(level22,sblist2);
	    jbmap.put(level23,qtlist2);
	    /**
	     * 统计项目进展
	     */
	    //纵向
	    List wclist1=projectmemberService.findByKdidAndYearAndKuidAndTypeAndJb(user.getKuId(), year,GhJsxm.JYXM, null,GhXm.ZX,GhXm.PROGRESS_DO);//已完成
		List zylist1=projectmemberService.findByKdidAndYearAndKuidAndTypeAndJb(user.getKuId(), year,GhJsxm.JYXM, null,GhXm.ZX,GhXm.PROGRESS_DONE);//在研
		List sqlist1=projectmemberService.findByKdidAndYearAndKuidAndTypeAndJb(user.getKuId(), year,GhJsxm.JYXM, null,GhXm.ZX,GhXm.PROGRESS_DOING);//申请中
		stage1.setValue(sqlist1!=null?sqlist1.size()+"":"0");
		stage1.setStyle("color:blue");
		stage2.setValue(zylist1!=null?zylist1.size()+"":"0");
		stage2.setStyle("color:blue");
		stage3.setValue(wclist1!=null?wclist1.size()+"":"0");
		stage3.setStyle("color:blue");
		jbmap.put(stage1, sqlist1);
		jbmap.put(stage2, zylist1);
		jbmap.put(stage3, wclist1);
		//横向
		List wclist2=projectmemberService.findByKdidAndYearAndKuidAndTypeAndJb(user.getKuId(), year,GhJsxm.JYXM, null,GhXm.HX,GhXm.PROGRESS_DO);//已完成
		List zylist2=projectmemberService.findByKdidAndYearAndKuidAndTypeAndJb(user.getKuId(), year,GhJsxm.JYXM, null,GhXm.HX,GhXm.PROGRESS_DONE);//在研
		List sqlist2=projectmemberService.findByKdidAndYearAndKuidAndTypeAndJb(user.getKuId(), year,GhJsxm.JYXM, null,GhXm.HX,GhXm.PROGRESS_DOING);//申请中
		stage21.setValue(sqlist2!=null?sqlist2.size()+"":"0");
		stage21.setStyle("color:blue");
		stage22.setValue(zylist2!=null?zylist2.size()+"":"0");
		stage22.setStyle("color:blue");
		stage23.setValue(wclist2!=null?wclist2.size()+"":"0");
		stage23.setStyle("color:blue");
		jbmap.put(stage21, sqlist2);
		jbmap.put(stage22, zylist2);
		jbmap.put(stage23, wclist2);
		/**
		 * 统计会议论文
		 */
		//纵向
		List hysci11=hylwService.findtjlw(year,GhJslw.JYHY,GhHylw.SCI,user.getKuId());//SCI
		List hyei11=hylwService.findtjlw(year,GhJslw.JYHY,GhHylw.EI,user.getKuId());//EI
		List hyistp11=hylwService.findtjlw(year,GhJslw.JYHY,GhHylw.ISTP,user.getKuId());//ISTP
		List hyqt11=hylwService.findQtlw(year,GhJslw.JYHY,user.getKuId());//其它
		//hysci1.setValue(hysci11!=null?hysci11.size()+"":"0");
		hysci1.setValue(hysci11!=null?hysci11.size()+"":"0");
		hysci1.setStyle("color:blue");
		hyei1.setValue(hyei11!=null?hyei11.size()+"":"0");
		hyei1.setStyle("color:blue");
		hyistp1.setValue(hyistp11!=null?hyistp11.size()+"":"0");
		hyistp1.setStyle("color:blue");
		hyother1.setValue(hyqt11!=null?hyqt11.size()+"":"0");
		hyother1.setStyle("color:blue");
		jbmap.put(hysci1, hysci11);
		jbmap.put(hyei1, hyei11);
		jbmap.put(hyistp1, hyistp11);
		jbmap.put(hyother1, hyqt11);
		//横向
		List hysci12=hylwService.findtjlw(year,GhJslw.JYHY,GhHylw.SCI,user.getKuId());//SCI
		List hyei12=hylwService.findtjlw(year,GhJslw.JYHY,GhHylw.EI,user.getKuId());//EI
		List hyistp12=hylwService.findtjlw(year,GhJslw.JYHY,GhHylw.ISTP,user.getKuId());//ISTP
		List hyqt12=hylwService.findQtlw(year,GhJslw.JYHY,user.getKuId());//其它
		hysci2.setValue(hysci12!=null?hysci12.size()+"":"0");
		hysci2.setStyle("color:blue");
		hyei2.setValue(hyei12!=null?hyei12.size()+"":"0");
		hyei2.setStyle("color:blue");
		hyistp2.setValue(hyistp12!=null?hyistp12.size()+"":"0");
		hyistp2.setStyle("color:blue");
		hyother2.setValue(hyqt12!=null?hyqt12.size()+"":"0");
		hyother2.setStyle("color:blue");
		jbmap.put(hysci2, hysci12);
		jbmap.put(hyei2, hyei12);
		jbmap.put(hyistp2, hyistp12);
		jbmap.put(hyother2, hyqt12);
	    /**
	     * 统计期刊论文
	     */
		//纵向
		List qkscilist1=qklwService.findByKdidAndYearAndKuidAndTypeAndRecordAndHx(year, user.getKuId(), GhQklw.SCI, null, GhJslw.JYQK);
		List qkeilist1=qklwService.findByKdidAndYearAndKuidAndTypeAndRecordAndHx(year, user.getKuId(), GhQklw.EI, null, GhJslw.JYQK);
		List qkistplist1=qklwService.findByKdidAndYearAndKuidAndTypeAndRecordAndHx(year, user.getKuId(), GhQklw.ISTP, null, GhJslw.JYQK);
		List qkhxlist1=qklwService.findByKdidAndYearAndKuidAndTypeAndRecordAndHx(year, user.getKuId(), null, GhQklw.LWHX_YES, GhJslw.JYQK);
		List qkyblist1=qklwService.findByKdidAndYearAndKuidAndTypeAndRecordAndHx(year, user.getKuId(), null, GhQklw.LWHX_NO, GhJslw.JYQK);
		List qkqtlist1=qklwService.findQtlw(year, user.getKuId(),  GhJslw.JYQK);
		yiban1.setValue(qkyblist1!=null?qkyblist1.size()+"":"0");
		yiban1.setStyle("color:blue");
		hexin1.setValue(qkhxlist1!=null?qkhxlist1.size()+"":"0");
		hexin1.setStyle("color:blue");
		qksci1.setValue(qkscilist1!=null?qkscilist1.size()+"":"0");
		qksci1.setStyle("color:blue");
		qkei1.setValue(qkeilist1!=null?qkeilist1.size()+"":"0");
		qkei1.setStyle("color:blue");
		qkistp1.setValue(qkistplist1!=null?qkistplist1.size()+"":"0");
		qkistp1.setStyle("color:blue");
		qkother1.setValue(qkqtlist1!=null?qkqtlist1.size()+"":"0");
		qkother1.setStyle("color:blue");
		jbmap.put(yiban1, qkyblist1);
		jbmap.put(hexin1, qkhxlist1);
		jbmap.put(qksci1, qkscilist1);
		jbmap.put(qkei1, qkeilist1);
		jbmap.put(qkistp1, qkistplist1);
		jbmap.put(qkother1, qkqtlist1);
		//横向
		List qkscilist2=qklwService.findByKdidAndYearAndKuidAndTypeAndRecordAndHx(year, user.getKuId(), GhQklw.SCI, null, GhJslw.JYQK);
		List qkeilist2=qklwService.findByKdidAndYearAndKuidAndTypeAndRecordAndHx(year, user.getKuId(), GhQklw.EI, null, GhJslw.JYQK);
		List qkistplist2=qklwService.findByKdidAndYearAndKuidAndTypeAndRecordAndHx(year, user.getKuId(), GhQklw.ISTP, null, GhJslw.JYQK);
		List qkhxlist2=qklwService.findByKdidAndYearAndKuidAndTypeAndRecordAndHx(year, user.getKuId(), null, GhQklw.LWHX_YES, GhJslw.JYQK);
		List qkyblist2=qklwService.findByKdidAndYearAndKuidAndTypeAndRecordAndHx(year, user.getKuId(), null, GhQklw.LWHX_NO, GhJslw.JYQK);
		List qkqtlist2=qklwService.findQtlw(year, user.getKuId(),  GhJslw.JYQK);
		yiban2.setValue(qkyblist2!=null?qkyblist2.size()+"":"0");
		yiban2.setStyle("color:blue");
		hexin2.setValue(qkhxlist2!=null?qkhxlist2.size()+"":"0");
		hexin2.setStyle("color:blue");
		qksci2.setValue(qkscilist2!=null?qkscilist2.size()+"":"0");
		qksci2.setStyle("color:blue");
		qkei2.setValue(qkeilist2!=null?qkeilist2.size()+"":"0");
		qkei2.setStyle("color:blue");
		qkistp2.setValue(qkistplist2!=null?qkistplist2.size()+"":"0");
		qkistp2.setStyle("color:blue");
		qkother2.setValue(qkqtlist2!=null?qkqtlist2.size()+"":"0");
		qkother2.setStyle("color:blue");
		jbmap.put(yiban2, qkyblist2);
		jbmap.put(hexin2, qkhxlist2);
		jbmap.put(qksci2, qkscilist2);
		jbmap.put(qkei2, qkeilist2);
		jbmap.put(qkistp2, qkistplist2);
		jbmap.put(qkother2, qkqtlist2);
		/**
		 * 奖励
		 */
		//纵向
		List gjjllist1=cgService.findByKdidAndYearAndKuid(year,user.getKuId(),GhJsxm.HJJY,GhCg.CG_GJ);//国家级
		List sbjllist1=cgService.findByKdidAndYearAndKuid(year,user.getKuId(),GhJsxm.HJJY,GhCg.CG_SB);//省部级
		List qtjllist1=cgService.findByKdidAndYearAndKuid(year,user.getKuId(),GhJsxm.HJJY,GhCg.CG_QT);//其它
		gjjl1.setValue(gjjllist1!=null?gjjllist1.size()+"":"0");
		gjjl1.setStyle("color:blue");
		sbjl1.setValue(sbjllist1!=null?sbjllist1.size()+"":"0");
		sbjl1.setStyle("color:blue");
		qtjl1.setValue(qtjllist1!=null?qtjllist1.size()+"":"0");
		qtjl1.setStyle("color:blue");
		jbmap.put(gjjl1, gjjllist1);
		jbmap.put(sbjl1, sbjllist1);
		jbmap.put(qtjl1, qtjllist1);
		//教材情况
		List jcqklist=zzService.findSumKdId(user.getKuId(), GhZz.JC, null);
		jiaocai1.setValue(jcqklist!=null&&jcqklist.size()>0?jcqklist.size()+"":"0");
		jbmap.put(jiaocai1, jcqklist);
		jiaocai1.setStyle("color:blue");
		//学术专著
		List xszzlist=zzService.findSumKdId(user.getKuId(), GhZz.ZZ, null);
		zhuanzhu1.setValue(xszzlist!=null&&xszzlist.size()>0?xszzlist.size()+"":"0");
		jbmap.put(zhuanzhu1, xszzlist);
		zhuanzhu1.setStyle("color:blue");
		//横向
		List gjjllist2=cgService.findByYearAndKuidAndCgmcAndType(year,user.getKuId(),GhJsxm.HJJY,GhCg.CG_GJ);//国家级
		List sbjllist2=cgService.findByYearAndKuidAndCgmcAndType(year,user.getKuId(),GhJsxm.HJJY,GhCg.CG_SB);//省部级
		List qtjllist2=cgService.findByYearAndKuidAndCgmcAndType(year,user.getKuId(),GhJsxm.HJJY,GhCg.CG_QT);//其它
		gjjl2.setValue(gjjllist2!=null?gjjllist2.size()+"":"0");
		gjjl2.setStyle("color:blue");
		sbjl2.setValue(sbjllist2!=null?sbjllist2.size()+"":"0");
		sbjl2.setStyle("color:blue");
		qtjl2.setValue(qtjllist2!=null?qtjllist2.size()+"":"0");
		qtjl2.setStyle("color:blue");
		jbmap.put(gjjl2, gjjllist2);
		jbmap.put(sbjl2, sbjllist2);
		jbmap.put(qtjl2, qtjllist2);	
		//统计项目级别
		List gjlist=projectmemberService.findByKdidAndYearAndKuidAndTypeAndJb(user.getKuId(), year,GhJsxm.JYXM, "2",GhXm.ZX,null);//国家级
		List sblist=projectmemberService.findByKdidAndYearAndKuidAndTypeAndJb(user.getKuId(), year,GhJsxm.JYXM, "3",GhXm.ZX,null);//省部级
		List qtlist=projectmemberService.findQtByYearAndKuid(year,user.getKuId(),GhJsxm.JYXM, GhXm.ZX);//其它
	    level1.setValue(gjlist!=null?gjlist.size()+"":"0");level2.setValue(sblist!=null?sblist.size()+"":"0");level3.setValue(qtlist!=null?qtlist.size()+"":"0");
	    jbmap.put(level1,gjlist);jbmap.put(level2,sblist);jbmap.put(level3,qtlist);
	    //统计项目进展
	    List wclist=projectmemberService.findByKdidAndYearAndKuidAndTypeAndJb(user.getKuId(), year,GhJsxm.JYXM, null,GhXm.ZX,GhXm.PROGRESS_DO);//已完成
		List zylist=projectmemberService.findByKdidAndYearAndKuidAndTypeAndJb(user.getKuId(), year,GhJsxm.JYXM, null,GhXm.ZX,GhXm.PROGRESS_DONE);//在研
		List sqlist=projectmemberService.findByKdidAndYearAndKuidAndTypeAndJb(user.getKuId(), year,GhJsxm.JYXM, null,GhXm.ZX,GhXm.PROGRESS_DOING);//申请中
		stage1.setValue(sqlist!=null?sqlist.size()+"":"0");stage2.setValue(zylist!=null?zylist.size()+"":"0");stage3.setValue(wclist!=null?wclist.size()+"":"0");
		jbmap.put(stage1, sqlist);jbmap.put(stage2, zylist);jbmap.put(stage3, wclist);
		//统计会议论文
		List hysci=hylwService.findtjlw(year,GhJslw.JYHY,GhHylw.SCI,user.getKuId());//SCI
		List hyei=hylwService.findtjlw(year,GhJslw.JYHY,GhHylw.EI,user.getKuId());//EI
		List hyistp=hylwService.findtjlw(year,GhJslw.JYHY,GhHylw.ISTP,user.getKuId());//ISTP
		List hyqt=hylwService.findQtlw(year,GhJslw.JYHY,user.getKuId());//其它
		hysci1.setValue(hysci!=null?hysci.size()+"":"0");hyei1.setValue(hyei!=null?hyei.size()+"":"0");
		hyistp1.setValue(hyistp!=null?hyistp.size()+"":"0");hyother1.setValue(hyqt!=null?hyqt.size()+"":"0");
		jbmap.put(hysci1, hysci);jbmap.put(hyei1, hyei);jbmap.put(hyistp1, hyistp);jbmap.put(hyother1, hyqt);
	    //统计期刊论文
		List qkscilist=qklwService.findByKdidAndYearAndKuidAndTypeAndRecordAndHx(year, user.getKuId(), GhQklw.SCI, null, GhJslw.JYQK);
		List qkeilist=qklwService.findByKdidAndYearAndKuidAndTypeAndRecordAndHx(year, user.getKuId(), GhQklw.EI, null, GhJslw.JYQK);
		List qkistplist=qklwService.findByKdidAndYearAndKuidAndTypeAndRecordAndHx(year, user.getKuId(), GhQklw.ISTP, null, GhJslw.JYQK);
		List qkhxlist=qklwService.findByKdidAndYearAndKuidAndTypeAndRecordAndHx(year, user.getKuId(), null, GhQklw.LWHX_YES, GhJslw.JYQK);
		List qkyblist=qklwService.findByKdidAndYearAndKuidAndTypeAndRecordAndHx(year, user.getKuId(), null, GhQklw.LWHX_NO, GhJslw.JYQK);
		List qkqtlist=qklwService.findQtlw(year, user.getKuId(),  GhJslw.JYQK);
		yiban1.setValue(qkyblist!=null?qkyblist.size()+"":"0");hexin1.setValue(qkhxlist!=null?qkhxlist.size()+"":"0");
		qksci1.setValue(qkscilist!=null?qkscilist.size()+"":"0");qkei1.setValue(qkeilist!=null?qkeilist.size()+"":"0");
		qkistp1.setValue(qkistplist!=null?qkistplist.size()+"":"0");qkother1.setValue(qkqtlist!=null?qkqtlist.size()+"":"0");
		jbmap.put(yiban1, qkyblist);jbmap.put(hexin1, qkhxlist);jbmap.put(qksci1, qkscilist);jbmap.put(qkei1, qkeilist);
		jbmap.put(qkistp1, qkistplist);jbmap.put(qkother1, qkqtlist);
	    //奖励
		List gjjllist=cgService.findByYearAndKuidAndCgmcAndType(year,user.getKuId(),GhJsxm.HJJY,GhCg.CG_GJ);//国家级
		List sbjllist=cgService.findByYearAndKuidAndCgmcAndType(year,user.getKuId(),GhJsxm.HJJY,GhCg.CG_SB);//省部级
		List qtjllist=cgService.findByYearAndKuidAndCgmcAndType(year,user.getKuId(),GhJsxm.HJJY,GhCg.CG_QT);//其它
		gjjl1.setValue(gjjllist!=null?gjjllist.size()+"":"0");
		sbjl1.setValue(sbjllist!=null?sbjllist.size()+"":"0");
		qtjl1.setValue(qtjllist!=null?qtjllist.size()+"":"0");
		jbmap.put(gjjl1, gjjllist);jbmap.put(sbjl1, sbjllist);jbmap.put(qtjl1, qtjllist);
		//教材情况
		List jcqk1list=zzService.findSumKdId(user.getKuId(), GhZz.JC, null);
		jiaocai2.setValue(jcqk1list!=null&&jcqk1list.size()>0?jcqk1list.size()+"":"0");
		jbmap.put(jiaocai2, jcqk1list);
		//学术专著
		List xszz1list=zzService.findSumKdId(user.getKuId(), GhZz.ZZ, null);
		zhuanzhu2.setValue(xszz1list!=null&&xszz1list.size()>0?xszz1list.size()+"":"0");
		jbmap.put(zhuanzhu2, xszz1list);
	}
	//项目列表
  class XmInnerListener implements EventListener{

		public void onEvent(Event arg0) throws Exception {
			Label l=(Label)arg0.getTarget();
			if(l.getValue().trim().equals("0")){
				Messagebox.show("没有数据","警告",Messagebox.OK,Messagebox.EXCLAMATION);
			}else{
				Map xmmap=new HashMap();
				xmmap.put("xmlist", jbmap.get(l));
			 ProjectWindow w=(ProjectWindow) Executions.createComponents
				("/admin/personal/data/teacherinfo/jxqk/statistics/project.zul", null, xmmap);
				w.doModal();
			}
		}
	}
  //论文列表
  class LwInnerListener implements EventListener{

		public void onEvent(Event arg0) throws Exception {
			Label l=(Label)arg0.getTarget();
			if(l.getValue().trim().equals("0")){
				Messagebox.show("没有数据","警告",Messagebox.OK,Messagebox.EXCLAMATION);
			}else{
				Map xmmap=new HashMap();
				xmmap.put("hylwlist", jbmap.get(l));
				ArticalWindow w=(ArticalWindow) Executions.createComponents
				("/admin/personal/data/teacherinfo/jxqk/statistics/artical.zul", null, xmmap);
				w.doModal();
			}
		}
	}
 
  //奖励列表
	class JlInnerListener implements EventListener{

		public void onEvent(Event arg0) throws Exception {
			Label l=(Label)arg0.getTarget();
			if(l.getValue().trim().equals("0")){
				Messagebox.show("没有数据","警告",Messagebox.OK,Messagebox.EXCLAMATION);
			}else{
				Map xmmap=new HashMap();
				xmmap.put("jllist", jbmap.get(l));
				CgJlWindow cw=(CgJlWindow)Executions.createComponents("/admin/personal/data/teacherinfo/jxqk/statistics/reward.zul", null, xmmap);
				cw.doModal();	
				
			}
		}
		
	}
	//交流合作
	class JlhzInnerListener implements EventListener{

		public void onEvent(Event arg0) throws Exception {
			Label l=(Label)arg0.getTarget();
			if(l.getValue().trim().equals("0")){
				Messagebox.show("没有数据","警告",Messagebox.OK,Messagebox.EXCLAMATION);
			}else{
				Map xmmap=new HashMap();
				xmmap.put("jlhzlist", jbmap.get(l));
				xmmap.put("type", typemap.get(l));
				JlhzWindow cw=(JlhzWindow)Executions.createComponents("/admin/personal/data/teacherinfo/jxqk/statistics/cooperation.zul", null, xmmap);
				cw.doModal();	
				
			}
		}
		
	}
	//教材
	class JCInnerListener implements EventListener{

		public void onEvent(Event arg0) throws Exception {
			Label l=(Label)arg0.getTarget();
			if(l.getValue().trim().equals("0")){
				Messagebox.show("没有数据","警告",Messagebox.OK,Messagebox.EXCLAMATION);
			}else{
				Map xmmap=new HashMap();
				xmmap.put("zjlist", jbmap.get(l));
				xmmap.put("type", "1");
				ZjqkWindow jc=(ZjqkWindow)Executions.createComponents("/admin/personal/data/teacherinfo/jxqk/statistics/zj.zul", null, xmmap);
				jc.doModal();	
				
			}
		}
		
	}
	//专著
	class ZZInnerListener implements EventListener{

		public void onEvent(Event arg0) throws Exception {
			Label l=(Label)arg0.getTarget();
			if(l.getValue().trim().equals("0")){
				Messagebox.show("没有数据","警告",Messagebox.OK,Messagebox.EXCLAMATION);
			}else{
				Map xmmap=new HashMap();
				xmmap.put("zjlist", jbmap.get(l));
				xmmap.put("type", "2");
				ZjqkWindow cw=(ZjqkWindow)Executions.createComponents("/admin/personal/data/teacherinfo/jxqk/statistics/zj.zul", null, xmmap);
				cw.doModal();	
				
			}
		}
		
	}
	//查询
	public void onClick$search() throws InterruptedException
	{
		if(yearlist.getSelectedIndex()==0)
		{
			Messagebox.show("请选择年份","警告",Messagebox.OK,Messagebox.EXCLAMATION);
		}
		else
		{
			zx.setVisible(true);
			hx.setVisible(true);
			if(projectlist.getSelectedIndex()==0)//只选择年份
			{
				JxYear year= (JxYear) yearlist.getSelectedItem().getValue();	
				initZXWindow(year.getYears());//初始化项目
			}
			else // 按项目统计
			{
			String pname=(String) projectlist.getSelectedItem().getValue();
			GhXm xm=projectmemberService.findByName(pname);
			initProject(user.getKuId(),xm);
			}
		}
	}
	//按项目统计数据
	public void initProject(Long kuid,GhXm xm)
	{
		List prolist=projectmemberService.findXmByid(xm.getKyId());//项目信息
		List hylw= projectmemberService.findHyLwByXm(xm.getKyId(),user.getKuId(),GhXmzz.HYLW);//会议论文
		List qklw= projectmemberService.findQkLwByXm(xm.getKyId(),user.getKuId(),GhXmzz.QKLW);//期刊论文
		List xszz=projectmemberService.findZzByXm(xm.getKyId(),user.getKuId(), GhXmzz.KYZZ);//学术专著
	if(xm.getKyClass().trim().equals("2"))//纵向
	 {
		hx.setVisible(false);
		if(prolist.size()!=0)
		{
			GhXm pro=(GhXm) prolist.get(0);
			//项目级别
			if(pro.getKyScale().equals("2")) 
			{
				//纵向
				level1.setValue("1");
				jbmap.put(level1,prolist);
				level2.setValue("0");
				level3.setValue("0");
				//横向
				level21.setValue("0");				
				level22.setValue("0");
				level23.setValue("0");
				
				}
			else if(pro.getKyScale().equals("3")) 
			{
				//纵向
				level2.setValue("1");
				jbmap.put(level2,prolist);
				level1.setValue("0");
				level3.setValue("0");
				//横向
				level22.setValue("0");				
				level21.setValue("0");
				level23.setValue("0");
				}
			else 
			{
				//纵向
				level3.setValue("1");
				jbmap.put(level3,prolist);
				level1.setValue("0");
				level2.setValue("0");
				//横向
				level23.setValue("0");				
				level21.setValue("0");
				level22.setValue("0");
				}
			
			}
			if(prolist.size()!=0)
			{
				
				GhXm pro=(GhXm) prolist.get(0);
				//项目进展
				if(pro.getKyProgress().trim().equals("1")) 
				{
					//纵向
					stage1.setValue("1");
					jbmap.put(stage1,prolist);
					stage2.setValue("0");
					stage3.setValue("0");
					//横向
					stage21.setValue("0");
					stage22.setValue("0");
					stage23.setValue("0");
					
				}
				else if(pro.getKyProgress().trim().equals("3")) 
				{
					//纵向
					stage2.setValue("1");
					jbmap.put(stage2,prolist);
					stage1.setValue("0");
					stage3.setValue("0");
					//横向
					stage21.setValue("0");
					stage22.setValue("0");
					stage23.setValue("0");
					
					}
				else if(pro.getKyProgress().trim().equals("2"))
				{
					//纵向
					stage3.setValue("1");
					jbmap.put(stage3,prolist);
					stage1.setValue("0");
					stage2.setValue("0");
					//横向
					stage21.setValue("0");
					stage22.setValue("0");
					stage23.setValue("0");
					
				}
			}
		//会议论文
		if(hylw.size()!=0)
		{
			int sci=0,ei=0,istp=0,qt=0;
			List scilist=new ArrayList();
			List eilist=new ArrayList();
			List istplist=new ArrayList();
			List qtlist=new ArrayList();
			for(int i=0;i<hylw.size();i++)
			{
			GhHylw hy=(GhHylw) hylw.get(i);
			if(hy.getLwRecord().equals("1")) 
			{scilist.add(sci, hy);   sci++;}
			else if(hy.getLwRecord().equals("2")) 
			{eilist.add(ei, hy);    ei++;}
			else if(hy.getLwRecord().equals("3")) 
			{istplist.add(istp, hy);   istp++;}
			hysci1.setValue(sci+"");jbmap.put(hysci1,scilist);
			hyei1.setValue(ei+"");jbmap.put(hyei1,eilist);
			hyistp1.setValue(istp+"");jbmap.put(hyistp1,istplist);
			hyother1.setValue(qt+"");jbmap.put(hyother1,qtlist);
		}
		}
			else
			{
				hysci1.setValue("0");hyei1.setValue("0");hyistp1.setValue("0");hyother1.setValue("0");
			}
		//横向论文
		hysci2.setValue("0");hyei2.setValue("0");hyistp2.setValue("0");hyother2.setValue("0");
		yiban2.setValue("0");hexin2.setValue("0");qksci2.setValue("0");qkei2.setValue("0");
		qkistp2.setValue("0");qkother2.setValue("0");
		//期刊论文
		if(qklw.size()!=0)
		{
			int yiban=0,hexin=0,qksci=0,qkei=0,qkistp=0,qkother=0;
			List yibanlist=new ArrayList();
			List hexinlist=new ArrayList();
			List qkscilist=new ArrayList();
			List qkeilist=new ArrayList();
			List qkistplist=new ArrayList();
			List qkqtlist=new ArrayList();
			for(int i=0;i<qklw.size();i++)
			{
				GhQklw qk=(GhQklw) qklw.get(i);
				//一般or核心
				if(qk.getLwCenter().toString().trim().equals("0"))
				{
					yibanlist.add(yiban, qk);
					yiban++;
				}
				else
				{
					hexinlist.add(hexin, qk);
					hexin++;
				}
				//sci\ei]istp\qt
				if(qk.getLwRecord().equals("1")) 
				{qkscilist.add(qksci, qk);   qksci++;}
				else if(qk.getLwRecord().equals("2")) 
				{qkeilist.add(qkei, qk);    qkei++;}
				else if(qk.getLwRecord().equals("3")) 
				{qkistplist.add(qkistp, qk);   qkistp++;}
				else
				{qkqtlist.add(qkother, qk);  qkother++;}
			}
			yiban1.setValue(yiban+"");jbmap.put(yiban1,yibanlist);
			hexin1.setValue(hexin+"");jbmap.put(hexin1,hexinlist);
			qksci1.setValue(qksci+"");jbmap.put(qksci1,qkscilist);
			qkei1.setValue(qkei+"");jbmap.put(qkei1,qkeilist);
			qkistp1.setValue(qkistp+"");jbmap.put(qkistp1,qkistplist);
			qkother1.setValue(qkother+"");jbmap.put(qkother1,qkqtlist);
		}
		else
		{
			yiban1.setValue("0");hexin1.setValue("0");qksci1.setValue("0");qkei1.setValue("0");
			qkistp1.setValue("0");qkother1.setValue("0");
		}
		//专著
		if(xszz.size()!=0)
		{
			zhuanzhu1.setValue(xszz.size()+"");
		}
		else
		{
			zhuanzhu1.setValue("0");
		}
	 }
	else if(xm.getKyClass().trim().equals("1"))//横向
	{
		zx.setVisible(false);
		if(prolist.size()!=0)
		{
			GhXm pro=(GhXm) prolist.get(0);
			//项目级别
			if(pro.getKyScale().equals("2")) 
			{
				//纵向
				level1.setValue("0");
				level2.setValue("0");
				level3.setValue("0");
				//横向
				level21.setValue("1");		
				jbmap.put(level21,prolist);
				level22.setValue("0");
				level23.setValue("0");
				
				}
			else if(pro.getKyScale().equals("3")) 
			{
				//纵向
				level2.setValue("0");
				level1.setValue("0");
				level3.setValue("0");
				//横向
				level22.setValue("1");		
				jbmap.put(level22,prolist);
				level21.setValue("0");
				level23.setValue("0");
				}
			else 
			{
				//纵向
				level3.setValue("0");
				level1.setValue("0");
				level2.setValue("0");
				//横向
				level23.setValue("1");	
				jbmap.put(level23,prolist);
				level21.setValue("0");
				level22.setValue("0");
				}
			}
			if(prolist.size()!=0)
			{	
				GhXm pro=(GhXm) prolist.get(0);
				//项目进展
				if(pro.getKyProgress().trim().equals("1")) 
				{
					//纵向
					stage1.setValue("0");
					stage2.setValue("0");
					stage3.setValue("0");
					//横向
					stage21.setValue("1");
					jbmap.put(stage21,prolist);
					stage22.setValue("0");
					stage23.setValue("0");
					
				}
				else if(pro.getKyProgress().trim().equals("3")) 
				{
					//纵向
					stage2.setValue("0");
					stage1.setValue("0");
					stage3.setValue("0");
					//横向
					stage21.setValue("0");
					stage22.setValue("1");
					jbmap.put(stage22,prolist);
					stage23.setValue("0");
					
					}
				else if(pro.getKyProgress().trim().equals("2"))
				{
					//纵向
					stage3.setValue("0");
					stage1.setValue("0");
					stage2.setValue("0");
					//横向
					stage21.setValue("0");
					stage22.setValue("0");
					stage23.setValue("1");
					jbmap.put(stage23,prolist);				
				}
			}
		//会议论文
		if(hylw.size()!=0)
		{
			int sci=0,ei=0,istp=0,qt=0;
			List scilist=new ArrayList();
			List eilist=new ArrayList();
			List istplist=new ArrayList();
			List qtlist=new ArrayList();
			for(int i=0;i<hylw.size();i++)
			{
			GhHylw hy=(GhHylw) hylw.get(i);
			if(hy.getLwRecord().equals("1")) 
			{scilist.add(sci, hy);   sci++;}
			else if(hy.getLwRecord().equals("2")) 
			{eilist.add(ei, hy);    ei++;}
			else if(hy.getLwRecord().equals("3")) 
			{istplist.add(istp, hy);   istp++;}
			hysci2.setValue(sci+"");jbmap.put(hysci2,scilist);
			hyei2.setValue(ei+"");jbmap.put(hyei2,eilist);
			hyistp2.setValue(istp+"");jbmap.put(hyistp2,istplist);
			hyother2.setValue(qt+"");jbmap.put(hyother2,qtlist);
		}
		}
			else
			{
				hysci2.setValue("0");hyei2.setValue("0");hyistp2.setValue("0");hyother2.setValue("0");
			}
		//纵向论文
		hysci1.setValue("0");hyei1.setValue("0");hyistp1.setValue("0");hyother1.setValue("0");
		yiban1.setValue("0");hexin1.setValue("0");qksci1.setValue("0");qkei1.setValue("0");
		qkistp1.setValue("0");qkother1.setValue("0");
		//期刊论文
		if(qklw.size()!=0)
		{
			int yiban=0,hexin=0,qksci=0,qkei=0,qkistp=0,qkother=0;
			List yibanlist=new ArrayList();
			List hexinlist=new ArrayList();
			List qkscilist=new ArrayList();
			List qkeilist=new ArrayList();
			List qkistplist=new ArrayList();
			List qkqtlist=new ArrayList();
			for(int i=0;i<qklw.size();i++)
			{
				GhQklw qk=(GhQklw) qklw.get(i);
				//一般or核心
				if(qk.getLwCenter().toString().trim().equals("0"))
				{
					yibanlist.add(yiban, qk);
					yiban++;
				}
				else
				{
					hexinlist.add(hexin, qk);
					hexin++;
				}
				//sci\ei]istp\qt
				if(qk.getLwRecord().equals("1")) 
				{qkscilist.add(qksci, qk);   qksci++;}
				else if(qk.getLwRecord().equals("2")) 
				{qkeilist.add(qkei, qk);    qkei++;}
				else if(qk.getLwRecord().equals("3")) 
				{qkistplist.add(qkistp, qk);   qkistp++;}
				else
				{qkqtlist.add(qkother, qk);  qkother++;}
			}
			yiban2.setValue(yiban+"");jbmap.put(yiban2,yibanlist);
			hexin2.setValue(hexin+"");jbmap.put(hexin2,hexinlist);
			qksci2.setValue(qksci+"");jbmap.put(qksci2,qkscilist);
			qkei2.setValue(qkei+"");jbmap.put(qkei2,qkeilist);
			qkistp2.setValue(qkistp+"");jbmap.put(qkistp2,qkistplist);
			qkother2.setValue(qkother+"");jbmap.put(qkother2,qkqtlist);
		}
		else
		{
			yiban2.setValue("0");hexin2.setValue("0");qksci2.setValue("0");qkei2.setValue("0");
			qkistp2.setValue("0");qkother2.setValue("0");
		}
		//专著
		if(xszz.size()!=0)
		{
			zhuanzhu2.setValue(xszz.size()+"");
		}
		else
		{
			zhuanzhu2.setValue("0");
		}
	}
	gjjl1.setValue("0");sbjl1.setValue("0");qtjl1.setValue("0");//奖励
	gjjl2.setValue("0");sbjl2.setValue("0");qtjl2.setValue("0");
	jiaocai1.setValue("0");jiaocai2.setValue("0");
}
	
	/**
	 * 导出excel
	 * @throws IOException 
	 * @throws BiffException 
	 * @throws WriteException 
	 */
	public void onClick$expert() throws BiffException, IOException, WriteException
	{
		Date now=new Date();
		String Title;
    	String fileName = "教研情况统计汇总"+DateUtil.getDateString(now)+".xls";
    	if(projectlist.getSelectedIndex()==0&&yearlist.getSelectedIndex()==0)
    	{
    	Title ="个人教研情况统计汇总";//一级标题
    	Sessions.getCurrent().setAttribute("type", "0");
    	}
    	else  if(projectlist.getSelectedIndex()==0)
    	{
    		JxYear year=(JxYear) yearlist.getSelectedItem().getValue();
    		Title =year.getYearsname()+"个人教研情况统计汇总";	
    		Sessions.getCurrent().setAttribute("type", "1");
    	}
    	else
    	{
    		String name=(String) projectlist.getSelectedItem().getValue();
    		Title ="项目【"+name+"】统计情况汇总";	
    		GhXm xm=projectmemberService.findByName(name);
    		if(xm.getKyClass().trim().toString().equals("1"))//横向
    		{
    		Sessions.getCurrent().setAttribute("type", "2");
    		}
    		else if(xm.getKyClass().trim().toString().equals("2"))//纵向
    		{
    			Sessions.getCurrent().setAttribute("type", "3");
    		}
    	}
     		
    	List titlehxlist1=new ArrayList();//横向二级标题
		List namehxlist = new ArrayList();//横向数据列
		List titlezxlist1=new ArrayList();//纵向二级标题
		List namezxlist = new ArrayList();//纵向数据列
    	List titlelist2=new ArrayList();//三级标题
        String[] titlehx1 = {"横向项目统计：","项目级别","项目进展","会议论文","期刊论文","出版教材","学术专著","奖励级别"};
	    for(int i=0;i<titlehx1.length;i++)
	      {
		    titlehxlist1.add(titlehx1[i]);
	      }
	    String[] titlezx1 = {"纵向项目统计：","项目级别","项目进展","会议论文","期刊论文","出版教材","学术专著","奖励级别"};
	    for(int i=0;i<titlezx1.length;i++)
	      {
	     	titlezxlist1.add(titlezx1[i]);
	      }
	    String[] titlehx2 = {"国家级","省部级","其它","申请中","在研","已完成","SCI","EI","ISTP","其它","一般期刊","核心期刊","SCI","EI","ISTP","其它","教材",
	    		"专著","国家级","省部级","其它"};
	    for(int i=0;i<titlehx2.length;i++)
	      {
		    titlelist2.add(titlehx2[i]);
	      }
	    //纵向数据
	   String[] zxshuju= {level1.getValue(),level2.getValue(),level3.getValue(),stage1.getValue(),stage2.getValue(),stage3.getValue(),
			   hysci1.getValue(),hyei1.getValue(),hyistp1.getValue(),hyother1.getValue(),yiban1.getValue(),hexin1.getValue(),qksci1.getValue(),
			   qkei1.getValue(),qkistp1.getValue(),qkother1.getValue(),jiaocai1.getValue(),zhuanzhu1.getValue()
			  ,gjjl1.getValue(),sbjl1.getValue(),qtjl1.getValue()};
	   for(int a=0;a<zxshuju.length;a++)
	   {
		   namezxlist.add(zxshuju[a]);
	   }
	   //横向数据
	   String[] hxshuju= {level21.getValue(),level22.getValue(),level23.getValue(),stage21.getValue(),stage22.getValue(),stage23.getValue(),
			   hysci2.getValue(),hyei2.getValue(),hyistp2.getValue(),hyother2.getValue(),yiban2.getValue(),hexin2.getValue(),qksci2.getValue(),
			   qkei2.getValue(),qkistp2.getValue(),qkother2.getValue(),jiaocai2.getValue(),zhuanzhu2.getValue()
			  ,gjjl2.getValue(),sbjl2.getValue(),qtjl2.getValue()};
			  
	   for(int a=0;a<zxshuju.length;a++)
	   {
		   namehxlist.add(hxshuju[a]);
	   }
	    ExportExcel ee=new ExportExcel();
			ee.exportjyqkExcel(fileName, Title, titlehxlist1,titlezxlist1,titlelist2,namehxlist,namezxlist);
	}
	 }