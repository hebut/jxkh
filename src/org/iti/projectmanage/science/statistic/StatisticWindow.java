package org.iti.projectmanage.science.statistic;

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
import org.iti.gh.service.CgService;
import org.iti.gh.service.FmzlService;
import org.iti.gh.service.HylwService;
import org.iti.gh.service.JlhzService;
import org.iti.gh.service.JxbgService;
import org.iti.gh.service.QklwService;
import org.iti.gh.service.RjzzService;
import org.iti.gh.service.XshyService;
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
	Label soft1,faming1,shiyong1,waiguan1;//纵向-著作权及专利
	Label soft2,faming2,shiyong2,waiguan2;//横向-著作权及专利
	Label gjhyzc1,gjhycj1,gjhydl1,gnhyzc1,gnhycj1,gnhydl1,gjjx1,gnjx1,gjhz1,gnhz1;//纵向-交流与合作
	Label gjhyzc2,gjhycj2,gjhydl2,gnhyzc2,gnhycj2,gnhydl2,gjjx2,gnjx2,gjhz2,gnhz2;//横向-交流与合作

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
		 * 著作权及专利
		 */
		//纵向
		soft1.addEventListener(Events.ON_CLICK, new RzInnerListener());
		faming1.addEventListener(Events.ON_CLICK, new RzInnerListener());
		shiyong1.addEventListener(Events.ON_CLICK, new RzInnerListener());
		waiguan1.addEventListener(Events.ON_CLICK, new RzInnerListener());
		//横向
		soft2.addEventListener(Events.ON_CLICK, new RzInnerListener());
		faming2.addEventListener(Events.ON_CLICK, new RzInnerListener());
		shiyong2.addEventListener(Events.ON_CLICK, new RzInnerListener());
		waiguan2.addEventListener(Events.ON_CLICK, new RzInnerListener());
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
		/**
		 * 交流与合作
		 */
		//纵向
		gjhyzc1.addEventListener(Events.ON_CLICK, new JlhzInnerListener());
		gjhycj1.addEventListener(Events.ON_CLICK, new JlhzInnerListener());
		gjhydl1.addEventListener(Events.ON_CLICK, new JlhzInnerListener());
		gnhyzc1.addEventListener(Events.ON_CLICK, new JlhzInnerListener());
		gnhycj1.addEventListener(Events.ON_CLICK, new JlhzInnerListener());
		gnhydl1.addEventListener(Events.ON_CLICK, new JlhzInnerListener());
		gjjx1.addEventListener(Events.ON_CLICK, new JlhzInnerListener());
		gnjx1.addEventListener(Events.ON_CLICK, new JlhzInnerListener());
		gjhz1.addEventListener(Events.ON_CLICK, new JlhzInnerListener());
		gnhz1.addEventListener(Events.ON_CLICK, new JlhzInnerListener());

		//横向
		gjhyzc2.addEventListener(Events.ON_CLICK, new JlhzInnerListener());
		gjhycj2.addEventListener(Events.ON_CLICK, new JlhzInnerListener());
		gjhydl2.addEventListener(Events.ON_CLICK, new JlhzInnerListener());
		gnhyzc2.addEventListener(Events.ON_CLICK, new JlhzInnerListener());
		gnhycj2.addEventListener(Events.ON_CLICK, new JlhzInnerListener());
		gnhydl2.addEventListener(Events.ON_CLICK, new JlhzInnerListener());
		gjjx2.addEventListener(Events.ON_CLICK, new JlhzInnerListener());
		gnjx2.addEventListener(Events.ON_CLICK, new JlhzInnerListener());
		gjhz2.addEventListener(Events.ON_CLICK, new JlhzInnerListener());
		gnhz2.addEventListener(Events.ON_CLICK, new JlhzInnerListener());
		//initWindow(null);

		initZXWindow(null);
		yearlist.addEventListener(Events.ON_SELECT, new EventListener()
		{
			public void onEvent(Event arg0) throws Exception {
				if(yearlist.getSelectedIndex()!=0){
				String	year=yearlist.getSelectedItem().getLabel().substring(0, 4);
			    if(zx.isSelected())
			    {
			    	List project=projectmemberService.findByKdidAndYearAndKuidAndTypeAndJb(user.getKuId(),year,GhJsxm.KYXM,null,GhXm.ZX,null);
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
			    	List project=projectmemberService.findByKdidAndYearAndKuidAndTypeAndJb(user.getKuId(),year,GhJsxm.KYXM,null,GhXm.HX,null);
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
		List gjlist1=projectmemberService.findByKdidAndYearAndKuidAndTypeAndJb(user.getKuId(), year,GhJsxm.KYXM, "2",GhXm.ZX,null);//国家级
		List sblist1=projectmemberService.findByKdidAndYearAndKuidAndTypeAndJb(user.getKuId(), year,GhJsxm.KYXM, "3",GhXm.ZX,null);//省部级
		List qtlist1=projectmemberService.findQtByYearAndKuid(year,user.getKuId(),GhJsxm.KYXM, GhXm.ZX);//其它
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
	    List gjlist2=projectmemberService.findByKdidAndYearAndKuidAndTypeAndJb(user.getKuId(), year,GhJsxm.KYXM, "2",GhXm.HX,null);//国家级
		List sblist2=projectmemberService.findByKdidAndYearAndKuidAndTypeAndJb(user.getKuId(), year,GhJsxm.KYXM, "3",GhXm.HX,null);//省部级
		List qtlist2=projectmemberService.findQtByYearAndKuid(year,user.getKuId(),GhJsxm.KYXM, GhXm.HX);//其它
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
	    List wclist1=projectmemberService.findByKdidAndYearAndKuidAndTypeAndJb(user.getKuId(), year,GhJsxm.KYXM, null,GhXm.ZX,GhXm.PROGRESS_DO);//已完成
		List zylist1=projectmemberService.findByKdidAndYearAndKuidAndTypeAndJb(user.getKuId(), year,GhJsxm.KYXM, null,GhXm.ZX,GhXm.PROGRESS_DONE);//在研
		List sqlist1=projectmemberService.findByKdidAndYearAndKuidAndTypeAndJb(user.getKuId(), year,GhJsxm.KYXM, null,GhXm.ZX,GhXm.PROGRESS_DOING);//申请中
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
		List wclist2=projectmemberService.findByKdidAndYearAndKuidAndTypeAndJb(user.getKuId(), year,GhJsxm.KYXM, null,GhXm.HX,GhXm.PROGRESS_DO);//已完成
		List zylist2=projectmemberService.findByKdidAndYearAndKuidAndTypeAndJb(user.getKuId(), year,GhJsxm.KYXM, null,GhXm.HX,GhXm.PROGRESS_DONE);//在研
		List sqlist2=projectmemberService.findByKdidAndYearAndKuidAndTypeAndJb(user.getKuId(), year,GhJsxm.KYXM, null,GhXm.HX,GhXm.PROGRESS_DOING);//申请中
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
		List hysci11=hylwService.findtjlw(year,GhJslw.KYHY,GhHylw.SCI,user.getKuId());//SCI
		List hyei11=hylwService.findtjlw(year,GhJslw.KYHY,GhHylw.EI,user.getKuId());//EI
		List hyistp11=hylwService.findtjlw(year,GhJslw.KYHY,GhHylw.ISTP,user.getKuId());//ISTP
		List hyqt11=hylwService.findQtlw(year,GhJslw.KYHY,user.getKuId());//其它
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
		List hysci12=hylwService.findtjlw(year,GhJslw.KYHY,GhHylw.SCI,user.getKuId());//SCI
		List hyei12=hylwService.findtjlw(year,GhJslw.KYHY,GhHylw.EI,user.getKuId());//EI
		List hyistp12=hylwService.findtjlw(year,GhJslw.KYHY,GhHylw.ISTP,user.getKuId());//ISTP
		List hyqt12=hylwService.findQtlw(year,GhJslw.KYHY,user.getKuId());//其它
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
		List qkscilist1=qklwService.findByKdidAndYearAndKuidAndTypeAndRecordAndHx(year, user.getKuId(), GhQklw.SCI, null, GhJslw.KYQK);
		List qkeilist1=qklwService.findByKdidAndYearAndKuidAndTypeAndRecordAndHx(year, user.getKuId(), GhQklw.EI, null, GhJslw.KYQK);
		List qkistplist1=qklwService.findByKdidAndYearAndKuidAndTypeAndRecordAndHx(year, user.getKuId(), GhQklw.ISTP, null, GhJslw.KYQK);
		List qkhxlist1=qklwService.findByKdidAndYearAndKuidAndTypeAndRecordAndHx(year, user.getKuId(), null, GhQklw.LWHX_YES, GhJslw.KYQK);
		List qkyblist1=qklwService.findByKdidAndYearAndKuidAndTypeAndRecordAndHx(year, user.getKuId(), null, GhQklw.LWHX_NO, GhJslw.KYQK);
		List qkqtlist1=qklwService.findQtlw(year, user.getKuId(),  GhJslw.KYQK);
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
		List qkscilist2=qklwService.findByKdidAndYearAndKuidAndTypeAndRecordAndHx(year, user.getKuId(), GhQklw.SCI, null, GhJslw.KYQK);
		List qkeilist2=qklwService.findByKdidAndYearAndKuidAndTypeAndRecordAndHx(year, user.getKuId(), GhQklw.EI, null, GhJslw.KYQK);
		List qkistplist2=qklwService.findByKdidAndYearAndKuidAndTypeAndRecordAndHx(year, user.getKuId(), GhQklw.ISTP, null, GhJslw.KYQK);
		List qkhxlist2=qklwService.findByKdidAndYearAndKuidAndTypeAndRecordAndHx(year, user.getKuId(), null, GhQklw.LWHX_YES, GhJslw.KYQK);
		List qkyblist2=qklwService.findByKdidAndYearAndKuidAndTypeAndRecordAndHx(year, user.getKuId(), null, GhQklw.LWHX_NO, GhJslw.KYQK);
		List qkqtlist2=qklwService.findQtlw(year, user.getKuId(),  GhJslw.KYQK);
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
		 * 统计著作权及专利
		 */
		//纵向
		List fmlist1=fmzlService.findBykdidAndYearAndKuid(year, user.getKuId(),GhFmzl.FM_FM);
		List newlist1=fmzlService.findBykdidAndYearAndKuid(year, user.getKuId(),GhFmzl.FM_NEW);
		List designlist1=fmzlService.findBykdidAndYearAndKuid(year, user.getKuId(),GhFmzl.FM_DESIGN);
		List softlist1=rjzzService.findBykdidAndYearAndKuid(year, user.getKuId());
		soft1.setValue(softlist1!=null?softlist1.size()+"":"0");
		soft1.setStyle("color:blue");
		faming1.setValue(fmlist1!=null?fmlist1.size()+"":"0");
		faming1.setStyle("color:blue");
		shiyong1.setValue(newlist1!=null?newlist1.size()+"":"0");
		shiyong1.setStyle("color:blue");
		waiguan1.setValue(designlist1!=null?designlist1.size()+"":"0");
		waiguan1.setStyle("color:blue");
		jbmap.put(soft1, softlist1);
		jbmap.put(faming1, fmlist1);
		jbmap.put(waiguan1, designlist1);
		jbmap.put(shiyong1, newlist1);
	    //横向
		List fmlist2=fmzlService.findBykdidAndYearAndKuid(year, user.getKuId(),GhFmzl.FM_FM);
		List newlist2=fmzlService.findBykdidAndYearAndKuid(year, user.getKuId(),GhFmzl.FM_NEW);
		List designlist2=fmzlService.findBykdidAndYearAndKuid(year, user.getKuId(),GhFmzl.FM_DESIGN);
		List softlist2=rjzzService.findBykdidAndYearAndKuid(year, user.getKuId());
		soft2.setValue(softlist2!=null?softlist2.size()+"":"0");
		soft2.setStyle("color:blue");
		faming2.setValue(fmlist2!=null?fmlist2.size()+"":"0");
		faming2.setStyle("color:blue");
		shiyong2.setValue(newlist2!=null?newlist2.size()+"":"0");
		shiyong2.setStyle("color:blue");
		waiguan2.setValue(designlist2!=null?designlist2.size()+"":"0");
		waiguan2.setStyle("color:blue");
		jbmap.put(soft2, softlist2);
		jbmap.put(faming2, fmlist2);
		jbmap.put(waiguan2, designlist2);
		jbmap.put(shiyong2, newlist2);
		/**
		 * 奖励
		 */
		//纵向
		List gjjllist1=cgService.findByKdidAndYearAndKuid(year,user.getKuId(),GhJsxm.HjKY,GhCg.CG_GJ);//国家级
		List sbjllist1=cgService.findByKdidAndYearAndKuid(year,user.getKuId(),GhJsxm.HjKY,GhCg.CG_SB);//省部级
		List qtjllist1=cgService.findByKdidAndYearAndKuid(year,user.getKuId(),GhJsxm.HjKY,GhCg.CG_QT);//其它
		gjjl1.setValue(gjjllist1!=null?gjjllist1.size()+"":"0");
		gjjl1.setStyle("color:blue");
		sbjl1.setValue(sbjllist1!=null?sbjllist1.size()+"":"0");
		sbjl1.setStyle("color:blue");
		qtjl1.setValue(qtjllist1!=null?qtjllist1.size()+"":"0");
		qtjl1.setStyle("color:blue");
		jbmap.put(gjjl1, gjjllist1);
		jbmap.put(sbjl1, sbjllist1);
		jbmap.put(qtjl1, qtjllist1);
		//横向
		List gjjllist2=cgService.findByKdidAndYearAndKuid(year,user.getKuId(),GhJsxm.HjKY,GhCg.CG_GJ);//国家级
		List sbjllist2=cgService.findByKdidAndYearAndKuid(year,user.getKuId(),GhJsxm.HjKY,GhCg.CG_SB);//省部级
		List qtjllist2=cgService.findByKdidAndYearAndKuid(year,user.getKuId(),GhJsxm.HjKY,GhCg.CG_QT);//其它
		gjjl2.setValue(gjjllist2!=null?gjjllist2.size()+"":"0");
		gjjl2.setStyle("color:blue");
		sbjl2.setValue(sbjllist2!=null?sbjllist2.size()+"":"0");
		sbjl2.setStyle("color:blue");
		qtjl2.setValue(qtjllist2!=null?qtjllist2.size()+"":"0");
		qtjl2.setStyle("color:blue");
		jbmap.put(gjjl2, gjjllist2);
		jbmap.put(sbjl2, sbjllist2);
		jbmap.put(qtjl2, qtjllist2);
		/**
		 * 参加会议、讲学及合作项目
		 */
		//纵向
		List gjhyzclist1=xshyService.findByKdidAndYearAndKuidAndIfcjAndLx( year, user.getKuId(), GhXshy.IFCJ_YES, GhXshy.GJHY, GhXshy.EFF_ZC);
		List gjhycjlist1=xshyService.findByKdidAndYearAndKuidAndIfcjAndLx(year, user.getKuId(), GhXshy.IFCJ_YES, GhXshy.GJHY, GhXshy.EFF_CJ);
		List gjhydlist1=xshyService.findByKdidAndYearAndKuidAndIfcjAndLx(year, user.getKuId(), GhXshy.IFCJ_YES, GhXshy.GJHY, GhXshy.EFF_DL);
		List gnhyzclist1=xshyService.findByKdidAndYearAndKuidAndIfcjAndLx(year, user.getKuId(), GhXshy.IFCJ_YES, GhXshy.GNHY, GhXshy.EFF_ZC);
		List gnhycjlist1=xshyService.findByKdidAndYearAndKuidAndIfcjAndLx(year, user.getKuId(), GhXshy.IFCJ_YES, GhXshy.GNHY, GhXshy.EFF_CJ);
		List gnhydlist1=xshyService.findByKdidAndYearAndKuidAndIfcjAndLx( year, user.getKuId(), GhXshy.IFCJ_YES, GhXshy.GNHY, GhXshy.EFF_DL);
		List gjjxlist1=jxbgService.findByKdidAndYearAndKuidAndIfcj( year, user.getKuId(), GhJxbg.IFCJ_YES, GhJxbg.GJJX);
		List gnjxlist1=jxbgService.findByKdidAndYearAndKuidAndIfcj( year, user.getKuId(), GhJxbg.IFCJ_YES, GhJxbg.GNJX);
		List gjhzlist1=jlhzService.findBykdidAndYearAndKuidAndifcj( year, user.getKuId(), GhJlhz.IFCJ_YES, GhJlhz.GJHZ);
		List gnhzlist1=jlhzService.findBykdidAndYearAndKuidAndifcj( year, user.getKuId(), GhJlhz.IFCJ_YES, GhJlhz.GNHZ);
		gjhyzc1.setValue(gjhyzclist1!=null?gjhyzclist1.size()+"":"0");
		gjhyzc1.setStyle("color:blue");
		gjhycj1.setValue(gjhycjlist1!=null?gjhycjlist1.size()+"":"0");
		gjhycj1.setStyle("color:blue");
		gjhydl1.setValue(gjhydlist1!=null?gjhydlist1.size()+"":"0");
		gjhydl1.setStyle("color:blue");
		gnhyzc1.setValue(gnhyzclist1!=null?gnhyzclist1.size()+"":"0");
		gnhyzc1.setStyle("color:blue");
		gnhycj1.setValue(gnhycjlist1!=null?gnhycjlist1.size()+"":"0");
		gnhycj1.setStyle("color:blue");
		gnhydl1.setValue(gnhydlist1!=null?gnhydlist1.size()+"":"0");
		gnhydl1.setStyle("color:blue");
		gjjx1.setValue(gjjxlist1!=null?gjjxlist1.size()+"":"0");
		gjjx1.setStyle("color:blue");
		gnjx1.setValue(gnjxlist1!=null?gnjxlist1.size()+"":"0");
		gnjx1.setStyle("color:blue");
		gjhz1.setValue(gjhzlist1!=null?gjhzlist1.size()+"":"0");
		gjhz1.setStyle("color:blue");
		gnhz1.setValue(gnhzlist1!=null?gnhzlist1.size()+"":"0");
		gnhz1.setStyle("color:blue");
		jbmap.put(gjhyzc1, gjhyzclist1);
		jbmap.put(gjhycj1, gjhycjlist1);
		jbmap.put(gjhydl1, gjhydlist1);
		jbmap.put(gnhyzc1, gnhyzclist1);
		jbmap.put(gnhycj1, gnhycjlist1);
		jbmap.put(gnhydl1, gnhydlist1);
		jbmap.put(gjjx1, gjjxlist1);
		jbmap.put(gnjx1, gnjxlist1);
		jbmap.put(gjhz1, gjhzlist1);
		jbmap.put(gnhz1, gnhzlist1);
		typemap.put(gjhyzc1, Short.parseShort("1"));
		typemap.put(gjhycj1, Short.parseShort("1"));
		typemap.put(gjhydl1, Short.parseShort("1"));
		typemap.put(gnhyzc1, Short.parseShort("1"));
		typemap.put(gnhycj1, Short.parseShort("1"));
		typemap.put(gnhydl1, Short.parseShort("1"));
		
		typemap.put(gjjx1, Short.parseShort("2"));
		typemap.put(gnjx1, Short.parseShort("2"));
		typemap.put(gjhz1, Short.parseShort("3"));
		typemap.put(gnhz1, Short.parseShort("3"));
		//横向
		List gjhyzclist2=xshyService.findByKdidAndYearAndKuidAndIfcjAndLx( year, user.getKuId(), GhXshy.IFCJ_YES, GhXshy.GJHY, GhXshy.EFF_ZC);
		List gjhycjlist2=xshyService.findByKdidAndYearAndKuidAndIfcjAndLx(year, user.getKuId(), GhXshy.IFCJ_YES, GhXshy.GJHY, GhXshy.EFF_CJ);
		List gjhydlist2=xshyService.findByKdidAndYearAndKuidAndIfcjAndLx(year, user.getKuId(), GhXshy.IFCJ_YES, GhXshy.GJHY, GhXshy.EFF_DL);
		List gnhyzclist2=xshyService.findByKdidAndYearAndKuidAndIfcjAndLx(year, user.getKuId(), GhXshy.IFCJ_YES, GhXshy.GNHY, GhXshy.EFF_ZC);
		List gnhycjlist2=xshyService.findByKdidAndYearAndKuidAndIfcjAndLx(year, user.getKuId(), GhXshy.IFCJ_YES, GhXshy.GNHY, GhXshy.EFF_CJ);
		List gnhydlist2=xshyService.findByKdidAndYearAndKuidAndIfcjAndLx( year, user.getKuId(), GhXshy.IFCJ_YES, GhXshy.GNHY, GhXshy.EFF_DL);
		List gjjxlist2=jxbgService.findByKdidAndYearAndKuidAndIfcj( year, user.getKuId(), GhJxbg.IFCJ_YES, GhJxbg.GJJX);
		List gnjxlist2=jxbgService.findByKdidAndYearAndKuidAndIfcj( year, user.getKuId(), GhJxbg.IFCJ_YES, GhJxbg.GNJX);
		List gjhzlist2=jlhzService.findBykdidAndYearAndKuidAndifcj( year, user.getKuId(), GhJlhz.IFCJ_YES, GhJlhz.GJHZ);
		List gnhzlist2=jlhzService.findBykdidAndYearAndKuidAndifcj( year, user.getKuId(), GhJlhz.IFCJ_YES, GhJlhz.GNHZ);
		gjhyzc2.setValue(gjhyzclist2!=null?gjhyzclist2.size()+"":"0");
		gjhyzc2.setStyle("color:blue");
		gjhycj2.setValue(gjhycjlist2!=null?gjhycjlist2.size()+"":"0");
		gjhycj2.setStyle("color:blue");
		gjhydl2.setValue(gjhydlist2!=null?gjhydlist2.size()+"":"0");
		gjhydl2.setStyle("color:blue");
		gnhyzc2.setValue(gnhyzclist2!=null?gnhyzclist2.size()+"":"0");
		gnhyzc2.setStyle("color:blue");
		gnhycj2.setValue(gnhycjlist2!=null?gnhycjlist2.size()+"":"0");
		gnhycj2.setStyle("color:blue");
		gnhydl2.setValue(gnhydlist2!=null?gnhydlist2.size()+"":"0");
		gnhydl2.setStyle("color:blue");
		gjjx2.setValue(gjjxlist2!=null?gjjxlist2.size()+"":"0");
		gjjx2.setStyle("color:blue");
		gnjx2.setValue(gnjxlist2!=null?gnjxlist2.size()+"":"0");
		gnjx2.setStyle("color:blue");
		gjhz2.setValue(gjhzlist2!=null?gjhzlist2.size()+"":"0");
		gjhz2.setStyle("color:blue");
		gnhz2.setValue(gnhzlist2!=null?gnhzlist2.size()+"":"0");
		gnhz2.setStyle("color:blue");
		jbmap.put(gjhyzc2, gjhyzclist2);
		jbmap.put(gjhycj2, gjhycjlist2);
		jbmap.put(gjhydl2, gjhydlist2);
		jbmap.put(gnhyzc2, gnhyzclist2);
		jbmap.put(gnhycj2, gnhycjlist2);
		jbmap.put(gnhydl2, gnhydlist2);
		jbmap.put(gjjx2, gjjxlist2);
		jbmap.put(gnjx2, gnjxlist2);
		jbmap.put(gjhz2, gjhzlist2);
		jbmap.put(gnhz2, gnhzlist2);
		typemap.put(gjhyzc2, Short.parseShort("1"));
		typemap.put(gjhycj2, Short.parseShort("1"));
		typemap.put(gjhydl2, Short.parseShort("1"));
		typemap.put(gnhyzc2, Short.parseShort("1"));
		typemap.put(gnhycj2, Short.parseShort("1"));
		typemap.put(gnhydl2, Short.parseShort("1"));

	
		//统计项目级别
		List gjlist=projectmemberService.findByKdidAndYearAndKuidAndTypeAndJb(user.getKuId(), year,GhJsxm.KYXM, "2",GhXm.ZX,null);//国家级
		List sblist=projectmemberService.findByKdidAndYearAndKuidAndTypeAndJb(user.getKuId(), year,GhJsxm.KYXM, "3",GhXm.ZX,null);//省部级
		List qtlist=projectmemberService.findQtByYearAndKuid(year,user.getKuId(),GhJsxm.KYXM, GhXm.ZX);//其它
	    level1.setValue(gjlist!=null?gjlist.size()+"":"0");level2.setValue(sblist!=null?sblist.size()+"":"0");level3.setValue(qtlist!=null?qtlist.size()+"":"0");
	    jbmap.put(level1,gjlist);jbmap.put(level2,sblist);jbmap.put(level3,qtlist);
	    //统计项目进展
	    List wclist=projectmemberService.findByKdidAndYearAndKuidAndTypeAndJb(user.getKuId(), year,GhJsxm.KYXM, null,GhXm.ZX,GhXm.PROGRESS_DO);//已完成
		List zylist=projectmemberService.findByKdidAndYearAndKuidAndTypeAndJb(user.getKuId(), year,GhJsxm.KYXM, null,GhXm.ZX,GhXm.PROGRESS_DONE);//在研
		List sqlist=projectmemberService.findByKdidAndYearAndKuidAndTypeAndJb(user.getKuId(), year,GhJsxm.KYXM, null,GhXm.ZX,GhXm.PROGRESS_DOING);//申请中
		stage1.setValue(sqlist!=null?sqlist.size()+"":"0");stage2.setValue(zylist!=null?zylist.size()+"":"0");stage3.setValue(wclist!=null?wclist.size()+"":"0");
		jbmap.put(stage1, sqlist);jbmap.put(stage2, zylist);jbmap.put(stage3, wclist);
		//统计会议论文
		List hysci=hylwService.findtjlw(year,GhJslw.KYHY,GhHylw.SCI,user.getKuId());//SCI
		List hyei=hylwService.findtjlw(year,GhJslw.KYHY,GhHylw.EI,user.getKuId());//EI
		List hyistp=hylwService.findtjlw(year,GhJslw.KYHY,GhHylw.ISTP,user.getKuId());//ISTP
		List hyqt=hylwService.findQtlw(year,GhJslw.KYHY,user.getKuId());//其它
		hysci1.setValue(hysci!=null?hysci.size()+"":"0");hyei1.setValue(hyei!=null?hyei.size()+"":"0");
		hyistp1.setValue(hyistp!=null?hyistp.size()+"":"0");hyother1.setValue(hyqt!=null?hyqt.size()+"":"0");
		jbmap.put(hysci1, hysci);jbmap.put(hyei1, hyei);jbmap.put(hyistp1, hyistp);jbmap.put(hyother1, hyqt);
	    //统计期刊论文
		List qkscilist=qklwService.findByKdidAndYearAndKuidAndTypeAndRecordAndHx(year, user.getKuId(), GhQklw.SCI, null, GhJslw.KYQK);
		List qkeilist=qklwService.findByKdidAndYearAndKuidAndTypeAndRecordAndHx(year, user.getKuId(), GhQklw.EI, null, GhJslw.KYQK);
		List qkistplist=qklwService.findByKdidAndYearAndKuidAndTypeAndRecordAndHx(year, user.getKuId(), GhQklw.ISTP, null, GhJslw.KYQK);
		List qkhxlist=qklwService.findByKdidAndYearAndKuidAndTypeAndRecordAndHx(year, user.getKuId(), null, GhQklw.LWHX_YES, GhJslw.KYQK);
		List qkyblist=qklwService.findByKdidAndYearAndKuidAndTypeAndRecordAndHx(year, user.getKuId(), null, GhQklw.LWHX_NO, GhJslw.KYQK);
		List qkqtlist=qklwService.findQtlw(year, user.getKuId(),  GhJslw.KYQK);
		yiban1.setValue(qkyblist!=null?qkyblist.size()+"":"0");hexin1.setValue(qkhxlist!=null?qkhxlist.size()+"":"0");
		qksci1.setValue(qkscilist!=null?qkscilist.size()+"":"0");qkei1.setValue(qkeilist!=null?qkeilist.size()+"":"0");
		qkistp1.setValue(qkistplist!=null?qkistplist.size()+"":"0");qkother1.setValue(qkqtlist!=null?qkqtlist.size()+"":"0");
		jbmap.put(yiban1, qkyblist);jbmap.put(hexin1, qkhxlist);jbmap.put(qksci1, qkscilist);jbmap.put(qkei1, qkeilist);
		jbmap.put(qkistp1, qkistplist);jbmap.put(qkother1, qkqtlist);
		//统计著作权及专利
		List fmlist=fmzlService.findBykdidAndYearAndKuid(year, user.getKuId(),GhFmzl.FM_FM);
		List newlist=fmzlService.findBykdidAndYearAndKuid(year, user.getKuId(),GhFmzl.FM_NEW);
		List designlist=fmzlService.findBykdidAndYearAndKuid(year, user.getKuId(),GhFmzl.FM_DESIGN);
		List softlist=rjzzService.findBykdidAndYearAndKuid(year, user.getKuId());
		soft1.setValue(softlist!=null?softlist.size()+"":"0");faming1.setValue(fmlist!=null?fmlist.size()+"":"0");
		shiyong1.setValue(newlist!=null?newlist.size()+"":"0");waiguan1.setValue(designlist!=null?designlist.size()+"":"0");
		jbmap.put(soft1, softlist);jbmap.put(faming1, fmlist);jbmap.put(waiguan1, designlist);jbmap.put(shiyong1, newlist);
	    //奖励
		List gjjllist=cgService.findByKdidAndYearAndKuid(year,user.getKuId(),GhJsxm.HjKY,GhCg.CG_GJ);//国家级
		List sbjllist=cgService.findByKdidAndYearAndKuid(year,user.getKuId(),GhJsxm.HjKY,GhCg.CG_SB);//省部级
		List qtjllist=cgService.findByKdidAndYearAndKuid(year,user.getKuId(),GhJsxm.HjKY,GhCg.CG_QT);//其它
		gjjl1.setValue(gjjllist!=null?gjjllist.size()+"":"0");
		sbjl1.setValue(sbjllist!=null?sbjllist.size()+"":"0");
		qtjl1.setValue(qtjllist!=null?qtjllist.size()+"":"0");
		jbmap.put(gjjl1, gjjllist);jbmap.put(sbjl1, sbjllist);jbmap.put(qtjl1, qtjllist);
		//参加会议、讲学及合作项目
		List gjhyzclist=xshyService.findByKdidAndYearAndKuidAndIfcjAndLx( year, user.getKuId(), GhXshy.IFCJ_YES, GhXshy.GJHY, GhXshy.EFF_ZC);
		List gjhycjlist=xshyService.findByKdidAndYearAndKuidAndIfcjAndLx(year, user.getKuId(), GhXshy.IFCJ_YES, GhXshy.GJHY, GhXshy.EFF_CJ);
		List gjhydlist=xshyService.findByKdidAndYearAndKuidAndIfcjAndLx(year, user.getKuId(), GhXshy.IFCJ_YES, GhXshy.GJHY, GhXshy.EFF_DL);
		List gnhyzclist=xshyService.findByKdidAndYearAndKuidAndIfcjAndLx(year, user.getKuId(), GhXshy.IFCJ_YES, GhXshy.GNHY, GhXshy.EFF_ZC);
		List gnhycjlist=xshyService.findByKdidAndYearAndKuidAndIfcjAndLx(year, user.getKuId(), GhXshy.IFCJ_YES, GhXshy.GNHY, GhXshy.EFF_CJ);
		List gnhydlist=xshyService.findByKdidAndYearAndKuidAndIfcjAndLx( year, user.getKuId(), GhXshy.IFCJ_YES, GhXshy.GNHY, GhXshy.EFF_DL);
		List gjjxlist=jxbgService.findByKdidAndYearAndKuidAndIfcj( year, user.getKuId(), GhJxbg.IFCJ_YES, GhJxbg.GJJX);
		List gnjxlist=jxbgService.findByKdidAndYearAndKuidAndIfcj( year, user.getKuId(), GhJxbg.IFCJ_YES, GhJxbg.GNJX);
		List gjhzlist=jlhzService.findBykdidAndYearAndKuidAndifcj( year, user.getKuId(), GhJlhz.IFCJ_YES, GhJlhz.GJHZ);
		List gnhzlist=jlhzService.findBykdidAndYearAndKuidAndifcj( year, user.getKuId(), GhJlhz.IFCJ_YES, GhJlhz.GNHZ);
		gjhyzc1.setValue(gjhyzclist!=null?gjhyzclist.size()+"":"0");gjhycj1.setValue(gjhycjlist!=null?gjhycjlist.size()+"":"0");
		gjhydl1.setValue(gjhydlist!=null?gjhydlist.size()+"":"0");gnhyzc1.setValue(gnhyzclist!=null?gnhyzclist.size()+"":"0");
		gnhycj1.setValue(gnhycjlist!=null?gnhycjlist.size()+"":"0");gnhydl1.setValue(gnhydlist!=null?gnhydlist.size()+"":"0");
		gjjx1.setValue(gjjxlist!=null?gjjxlist.size()+"":"0");gnjx1.setValue(gnjxlist!=null?gnjxlist.size()+"":"0");
		gjhz1.setValue(gjhzlist!=null?gjhzlist.size()+"":"0");gnhz1.setValue(gnhzlist!=null?gnhzlist.size()+"":"0");
		jbmap.put(gjhyzc1, gjhyzclist);jbmap.put(gjhycj1, gjhycjlist);jbmap.put(gjhydl1, gjhydlist);
		jbmap.put(gnhyzc1, gnhyzclist);jbmap.put(gnhycj1, gnhycjlist);jbmap.put(gnhydl1, gnhydlist);
		jbmap.put(gjjx1, gjjxlist);
		jbmap.put(gnjx1, gnjxlist);jbmap.put(gjhz1, gjhzlist);jbmap.put(gnhz1, gnhzlist);
		typemap.put(gjhyzc1, Short.parseShort("1"));typemap.put(gjhycj1, Short.parseShort("1"));
		typemap.put(gjhydl1, Short.parseShort("1"));typemap.put(gnhyzc1, Short.parseShort("1"));
		typemap.put(gnhycj1, Short.parseShort("1"));typemap.put(gnhydl1, Short.parseShort("1"));

		
		typemap.put(gjjx2, Short.parseShort("2"));
		typemap.put(gnjx2, Short.parseShort("2"));
		typemap.put(gjhz2, Short.parseShort("3"));
		typemap.put(gnhz2, Short.parseShort("3"));
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
				("/admin/personal/data/teacherinfo/kyqk/statistics/project.zul", null, xmmap);
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
				("/admin/personal/data/teacherinfo/kyqk/statistics/artical.zul", null, xmmap);
				w.doModal();
			}
		}
	}
  //软件著作及专利
  class RzInnerListener implements EventListener{

		public void onEvent(Event arg0) throws Exception {

			Label l=(Label)arg0.getTarget();
			if(l.getValue().trim().equals("0")){
				Messagebox.show("没有数据","警告",Messagebox.OK,Messagebox.EXCLAMATION);
			}else{
				Map xmmap=new HashMap();
				xmmap.put("rzorzllist", jbmap.get(l));
				RjzzandZlWindow cw=(RjzzandZlWindow)Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/statistics/patent.zul", null, xmmap);
				cw.doModal();	
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
				CgJlWindow cw=(CgJlWindow)Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/statistics/reward.zul", null, xmmap);
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
				JlhzWindow cw=(JlhzWindow)Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/statistics/cooperation.zul", null, xmmap);
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
				initZXWindow(year.getYears());//初始化纵向项目
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
		List rjzz=projectmemberService.findRjzzByXm(xm.getKyId(),user.getKuId(),GhXmzz.RJZZ);//软件著作
		List fmzl=projectmemberService.findFmzlByXm(xm.getKyId(),user.getKuId(),GhXmzz.FMZL);//专利
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
		//著作权及专利
		if(rjzz.size()!=0)
		{
			soft1.setValue(rjzz.size()+"");
			jbmap.put(soft1,rjzz);
		}
		else
		{
			soft1.setValue("0");
		}
		if(fmzl.size()!=0)
		{
			int faming=0,shiyong=0,waiguan=0;
			List fmlist=new ArrayList();
			List sylist=new ArrayList();
			List wglist=new ArrayList();
			for(int i=0;i<fmzl.size();i++)
			{
				GhFmzl fm=(GhFmzl) fmzl.get(i);
				if(fm.getFmTypes().trim().equals("1"))
				{
					fmlist.add(faming, fm);faming++;
				}
				else if(fm.getFmTypes().trim().equals("2"))
				{
					sylist.add(shiyong, fm);shiyong++;
				}
				else
				{
					wglist.add(waiguan, fm);waiguan++;
				}
			}
			faming1.setValue(faming+"");jbmap.put(faming1,fmlist);
			shiyong1.setValue(shiyong+"");jbmap.put(shiyong1,sylist);
			waiguan1.setValue(waiguan+"");jbmap.put(waiguan1,wglist);
		}
		else
		{
			faming1.setValue("0");shiyong1.setValue("0");waiguan1.setValue("0");
		}
		//横向著作权及专利
		soft2.setValue("0");faming2.setValue("0");shiyong2.setValue("0");waiguan2.setValue("0");
		
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
		//著作权及专利
		if(rjzz.size()!=0)
		{
			soft2.setValue(rjzz.size()+"");
			jbmap.put(soft2,rjzz);
		}
		else
		{
			soft2.setValue("0");
		}
		if(fmzl.size()!=0)
		{
			int faming=0,shiyong=0,waiguan=0;
			List fmlist=new ArrayList();
			List sylist=new ArrayList();
			List wglist=new ArrayList();
			for(int i=0;i<fmzl.size();i++)
			{
				GhFmzl fm=(GhFmzl) fmzl.get(i);
				if(fm.getFmTypes().trim().equals("1"))
				{
					fmlist.add(faming, fm);faming++;
				}
				else if(fm.getFmTypes().trim().equals("2"))
				{
					sylist.add(shiyong, fm);shiyong++;
				}
				else
				{
					wglist.add(waiguan, fm);waiguan++;
				}
			}
			faming2.setValue(faming+"");jbmap.put(faming2,fmlist);
			shiyong2.setValue(shiyong+"");jbmap.put(shiyong2,sylist);
			waiguan2.setValue(waiguan+"");jbmap.put(waiguan2,wglist);
		}
		else
		{
			faming2.setValue("0");shiyong2.setValue("0");waiguan2.setValue("0");
		}
		//纵向著作权及专利
		soft1.setValue("0");faming1.setValue("0");shiyong1.setValue("0");waiguan1.setValue("0");
		
	 
	}
	gjjl1.setValue("0");sbjl1.setValue("0");qtjl1.setValue("0");//奖励
	gjjl2.setValue("0");sbjl2.setValue("0");qtjl2.setValue("0");
	//交流合作
	gjhyzc1.setValue("0");gjhycj1.setValue("0");gjhydl1.setValue("0");gnhyzc1.setValue("0");
	gnhycj1.setValue("0");gnhydl1.setValue("0");gjjx1.setValue("0");gnjx1.setValue("0");
	gjhz1.setValue("0");gnhz1.setValue("0");
	gjhyzc2.setValue("0");gjhycj2.setValue("0");gjhydl2.setValue("0");gnhyzc2.setValue("0");
	gnhycj2.setValue("0");gnhydl2.setValue("0");gjjx2.setValue("0");gnjx2.setValue("0");gjhz2.setValue("0");
	gnhz2.setValue("0");
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
    	String fileName = "科研情况统计汇总"+DateUtil.getDateString(now)+".xls";
    	if(projectlist.getSelectedIndex()==0&&yearlist.getSelectedIndex()==0)
    	{
    	Title ="个人科研情况统计汇总";//一级标题
    	Sessions.getCurrent().setAttribute("type", "0");
    	}
    	else  if(projectlist.getSelectedIndex()==0)
    	{
    		JxYear year=(JxYear) yearlist.getSelectedItem().getValue();
    		Title =year.getYearsname()+"个人科研情况统计汇总";	
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
        String[] titlehx1 = {"横向项目统计：","项目级别","项目进展","会议论文","期刊论文","软件著作权","发明专利","奖励级别","国际会议","国内会议","讲学","合作项目"};
	    for(int i=0;i<titlehx1.length;i++)
	      {
		    titlehxlist1.add(titlehx1[i]);
	      }
	    String[] titlezx1 = {"纵向项目统计：","项目级别","项目进展","会议论文","期刊论文","软件著作权","发明专利","奖励级别","国际会议","国内会议","讲学","合作项目"};
	    for(int i=0;i<titlezx1.length;i++)
	      {
	     	titlezxlist1.add(titlezx1[i]);
	      }
	    String[] titlehx2 = {"国家级","省部级","其它","申请中","在研","已完成","SCI","EI","ISTP","其它","一般期刊","核心期刊","SCI","EI","ISTP","其它","著作权",
	    		"发明","实用新型","外观设计","国家级","省部级","其它","主持","参加","独立","主持","参加","独立","国际讲学","国内讲学","国际合作","国内合作"};
	    for(int i=0;i<titlehx2.length;i++)
	      {
		    titlelist2.add(titlehx2[i]);
	      }
	    //纵向数据
	   String[] zxshuju= {level1.getValue(),level2.getValue(),level3.getValue(),stage1.getValue(),stage2.getValue(),stage3.getValue(),
			   hysci1.getValue(),hyei1.getValue(),hyistp1.getValue(),hyother1.getValue(),yiban1.getValue(),hexin1.getValue(),qksci1.getValue(),
			   qkei1.getValue(),qkistp1.getValue(),qkother1.getValue(),soft1.getValue(),faming1.getValue(),shiyong1.getValue(),
			   waiguan1.getValue(),gjjl1.getValue(),sbjl1.getValue(),qtjl1.getValue(),gjhyzc1.getValue(),gjhycj1.getValue(),gjhydl1.getValue(),
			   gnhyzc1.getValue(),gnhycj1.getValue(),gnhydl1.getValue(),gjjx1.getValue(),gnjx1.getValue(),gjhz1.getValue(),gnhz1.getValue()};
	   for(int a=0;a<zxshuju.length;a++)
	   {
		   namezxlist.add(zxshuju[a]);
	   }
	   //横向数据
	   String[] hxshuju= {level21.getValue(),level22.getValue(),level23.getValue(),stage21.getValue(),stage22.getValue(),stage23.getValue(),
			   hysci2.getValue(),hyei2.getValue(),hyistp2.getValue(),hyother2.getValue(),yiban2.getValue(),hexin2.getValue(),qksci2.getValue(),
			   qkei2.getValue(),qkistp2.getValue(),qkother2.getValue(),soft2.getValue(),faming2.getValue(),shiyong2.getValue(),
			   waiguan2.getValue(),gjjl2.getValue(),sbjl2.getValue(),qtjl2.getValue(),gjhyzc2.getValue(),gjhycj2.getValue(),gjhydl2.getValue(),
			   gnhyzc2.getValue(),gnhycj2.getValue(),gnhydl2.getValue(),gjjx2.getValue(),gnjx2.getValue(),gjhz2.getValue(),gnhz2.getValue()};
	   for(int a=0;a<zxshuju.length;a++)
	   {
		   namehxlist.add(hxshuju[a]);
	   }
	    ExportExcel ee=new ExportExcel();
			ee.exportkyqkExcel(fileName, Title, titlehxlist1,titlezxlist1,titlelist2,namehxlist,namezxlist);
	}
	 }