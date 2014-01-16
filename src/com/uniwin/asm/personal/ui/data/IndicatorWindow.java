package com.uniwin.asm.personal.ui.data;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iti.gh.common.util.ChineseUpperCaser;
import org.iti.gh.common.util.DateUtil;
import org.iti.gh.common.util.KyglControlUtil;
import org.iti.gh.entity.GhCg;
import org.iti.gh.entity.GhHylw;
import org.iti.gh.entity.GhJslw;
import org.iti.gh.entity.GhJsps;
import org.iti.gh.entity.GhJsxm;
import org.iti.gh.entity.GhJszz;
import org.iti.gh.entity.GhPx;
import org.iti.gh.entity.GhQklw;
import org.iti.gh.entity.GhRjzz;
import org.iti.gh.entity.GhSk;
import org.iti.gh.entity.GhXm;
import org.iti.gh.entity.GhZz;
import org.iti.gh.service.CgService;
import org.iti.gh.service.FmzlService;
import org.iti.gh.service.HylwService;
import org.iti.gh.service.JslwService;
import org.iti.gh.service.JspsService;
import org.iti.gh.service.JsxmService;
import org.iti.gh.service.JszzService;
import org.iti.gh.service.PxService;
import org.iti.gh.service.QklwService;
import org.iti.gh.service.RjzzService;
import org.iti.gh.service.SkService;
import org.iti.gh.service.XmService;
import org.iti.gh.service.ZzService;
import org.iti.jxkh.entity.Jxkh_Honour;
import org.iti.xypt.entity.Teacher;
import org.iti.xypt.entity.Title;
import org.iti.xypt.service.TeacherService;
import org.iti.xypt.service.XyptTitleService;
import org.zkoss.zhtml.Filedownload;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.uniwin.common.util.FileUtil;
import com.uniwin.framework.entity.WkTUser;

public class IndicatorWindow extends Window implements AfterCompose{
	private static final long serialVersionUID = -6066299046822270593L;
	WkTUser user=(WkTUser)Executions.getCurrent().getArg().get("user");	
	//数据访问接口
	private CgService cgService;
	private HylwService hylwService;
	private QklwService qklwService;
	private RjzzService rjzzService;
	private ZzService zzService;
	private SkService skService;
	private org.iti.jxkh.service.RychService rychService;
	private PxService pxService;
	private JslwService jslwService;
	private JspsService jspsService;
	private XyptTitleService xypttitleService;
	private TeacherService teacherService;
	private XmService xmService;
	private JsxmService jsxmService;
	private JszzService jszzService;
	private FmzlService fmzlService;
	
	//组件
	private Textbox subject,position,remarkTextbox;
	private Label year,status;
	private Listbox type,assessment,computer,foreign;
	
	private Rows teaCourceRows,fruitRows,proRows,softAuthRows,magaPaperRows,mettingPaperRows,pubTeaRows;
	private Row teaCourseRow1,teaCourseRow2,fruitRow1,fruitRow2,proRow1,proRow2,softAuthRow1,softAuthRow2;
	private Row magaPaperRow1,magaPaperRow2,mettPaperRow1,mettPaperRow2,pubTeaRow1,pubTeaRow2;
	private Checkbox teaCourseYear,teaCourseName,teaCourseUnit,teaCourseTime;
	private Checkbox fruitName,fruitTime,HjName,fruitClass,fruitRank;
	private Checkbox proName,proSource,proNum,proRank;
	private Checkbox softAuthName,softPubTime,softDjNum,softDjTime,softRank;
	private Checkbox magaPaperName,magaPaperKw,magaTime,magaPages,magaRank;
	private Checkbox mettPaperName,mettPaperNaS,mettTime,mettPlace,mettRank;
	private Checkbox pubTeaName,pubTeaUnit,pubTeaTime,pubTeaRank;
	private Checkbox allCheck;
	
	private Div archive;
	private Groupbox information;
	private Button save;
	
	//实体
	private GhJsps ps;
	private int curYear;
	private Teacher teacher;
	
	private Map map= new HashMap();
	
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		if(user==null){
			user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		}
		teacher = teacherService.findBykuid(user.getKuId());
		GregorianCalendar calendar=new GregorianCalendar(); 
		curYear = calendar.get(calendar.YEAR);//当前年份
		ps = jspsService.getByKuidYear(user.getKuId(), curYear);
		initWindow();
		if(null!=ps&&GhJsps.pass.equals(ps.getJspsStatus()))
		{
			save.setDisabled(true);
			archive.setVisible(true);
			initList();
		}else{
			information.setVisible(true);
		}
	}
	
	/**
	* <li>功能描述：初始化页面。
	 *
	 */
	public void initWindow()
	{
		
		//默认申请职务
		Teacher teacher = teacherService.findBykuid(user.getKuId());
		year.setValue(teacher.getSchDept(teacher.getKdId()).trim()+curYear+"年职称申请");
		List titleList = xypttitleService.findBytid(teacher.getThTitle());
		Title teacherTitle = (Title)titleList.get(0);
		List ptitleList = xypttitleService.findByPtid(teacherTitle.getPtiId());
		String declareTitle = "";
		for(int i=0;i<ptitleList.size();i++)
		{
			Title pTitle = (Title)ptitleList.get(i);
			if(pTitle.getTiId().equals(teacherTitle.getTiId())){
				if(i<ptitleList.size()-1)
				{
					declareTitle = ((Title)ptitleList.get(i+1)).getTiName();
				}else{
					declareTitle = "";
				}
			}
		}
		if(ps!=null)
		{
			if(ps.getJspsSubject()!=null)
			{
				subject.setValue(ps.getJspsSubject().trim());
			}
			if(ps.getJspsPosition()==null)
			{
				position.setValue(declareTitle);
			}else{
				position.setValue(ps.getJspsPosition().trim());
			}
			if(null==ps.getJspsStatus())
			{
				status.setValue(GhJsps.notApplied);
			}else{
				status.setValue(ps.getJspsStatus().trim());
			}
			if(null==ps.getJspsAnnualAssessment())
			{
				assessment.setSelectedIndex(1);
			}else{
				if(GhJsps.unQualified.equals(ps.getJspsAnnualAssessment().trim()))
					assessment.setSelectedIndex(1);
			}
				
			if(null==ps.getJspsComputer())
			{
				computer.setSelectedIndex(1);
			}else{
				if(GhJsps.unQualified.equals(ps.getJspsComputer().trim()))
					computer.setSelectedIndex(1);
			}
			if(null==ps.getJspsForeign())
			{
				foreign.setSelectedIndex(1);
			}else{
				if(GhJsps.unQualified.equals(ps.getJspsForeign().trim()))
					foreign.setSelectedIndex(1);
			}
			if(null==ps.getJspsType())
			{
				type.setSelectedIndex(0);
			}else{
				if(GhJsps.unRegular.equals(ps.getJspsType().trim()))
					type.setSelectedIndex(1);
			}
			if(null!=ps.getJspsRemark()&&!"".equals(ps.getJspsRemark()))
			{
				remarkTextbox.setValue(ps.getJspsRemark());
			}
		}else{
			status.setValue(GhJsps.notApplied);
			
		}
	}
	
	/**
	 * 初始化用户档案信息列表
	 */
	public void initList(){
		//授课情况
		List<GhSk> teaCourelist = skService.findByKuid(user.getKuId());
		int teasize= teaCourelist.size();
		int tealastCols=teasize%4;
		int teacount = (teasize/4)  + (tealastCols>0?1:0);
		if(teasize>0){
			boolean flag=true; 
			if(teasize<=4){
				flag =false;
			}
			for(int i=0;i<teacount;i++){
				Row proRow = new Row();
				proRow.setId("teacouRowId"+i);
				proRow.setParent(teaCourceRows); 
			    for(int a=0;a<4;a++){	
			    	GhSk sk = (GhSk)teaCourelist.get(i*4+a);
			    	Checkbox ch = new Checkbox();
					ch.setId("teacoucheck"+sk.getSkId());
					ch.setChecked(true);
					Label lab = new Label();
					lab.setId("teacoulabel"+sk.getSkId());
					lab.setValue(sk.getSkMc().trim());					
					map.put(ch.getId(), ch);
					ch.setParent(proRow);
					lab.setParent(proRow);
					if((i*4+a)==teasize-1){
						break;
					}
			    }
			    if(!flag){
			    	break;
			    }
			}
		}else{
			teaCourseRow1.setVisible(false);teaCourseRow2.setVisible(false);
		}
		//科研获奖成果
		List<GhCg> cglist = cgService.findByKuid(user.getKuId(),GhCg.CG_KY,GhJsxm.HjKY);
		List<GhCg> teaCglist = cgService.findByKuid(user.getKuId(), GhCg.CG_JY,GhJsxm.HJJY);
		cglist.addAll(teaCglist);
		int cgsize= cglist.size();
		int cglastCols=cgsize%4;
		int cgcount =(cgsize/4) + (cglastCols>0?1:0);
		if(cglist.size()>0){
			boolean flag=true; 
			if(cgsize<=4){
				flag =false;
			}
			for(int i=0;i<cgcount;i++){
				Row proRow = new Row();
				proRow.setId("fruitRowId"+i);
				proRow.setParent(fruitRows); 
			    for(int a=0;a<4;a++){				
			    	GhCg cg = (GhCg)cglist.get(i*4+a);
			    	Checkbox ch = new Checkbox();
					ch.setId("cgcheck"+cg.getKyId());
					ch.setChecked(true);
					Label lab = new Label();
					lab.setId("cglabel"+cg.getKyId());
					lab.setValue(cg.getKyHjmc().trim());					
					map.put(ch.getId(), ch);
					ch.setParent(proRow);
					lab.setParent(proRow);
					if((i*4+a)==cglist.size()-1){
						break;
					}
			    }
			    if(!flag){
			    	break;
			    }
			}
		}else{
			fruitRow1.setVisible(false);fruitRow2.setVisible(false);
		}
		//科研项目
		List<GhXm> xmlist =xmService.findByKuidAndType(user.getKuId(), GhJsxm.KYXM);		
		int size= xmlist.size();
		int lastCols=size%4;
		int count =(size/4) + (lastCols>0?1:0);
		if(xmlist.size()>0){
			boolean flag=true; 
			if(size<=4){
				flag =false;
			}
			for(int i=0;i<count;i++){
				Row proRow = new Row();
				proRow.setId("proRowId"+i);
				proRow.setParent(proRows); 
			    for(int a=0;a<4;a++){				
			    	GhXm xm = (GhXm)xmlist.get(i*4+a);
			    	Checkbox ch = new Checkbox();
					ch.setId("xmcheck"+xm.getKyId());
					ch.setChecked(true);
					Label lab = new Label();
					lab.setId("xmlabel"+xm.getKyId());
					lab.setValue(xm.getKyMc().trim());					
					map.put(ch.getId(), ch);
					ch.setParent(proRow);
					lab.setParent(proRow);					
					if((i*4+a)==xmlist.size()-1){
						break;
					}
			    }
			    if(!flag){
			    	break;
			    }
			}
		}else{
			proRow1.setVisible(false);proRow2.setVisible(false);
		}
		//软件著作权
		List<GhRjzz> softAuthlist = rjzzService.findByKuid(user.getKuId());	
		int softsize= softAuthlist.size();
		int softlastCols=softsize%4;
		int softcount =(softsize/4) + (softlastCols>0?1:0);
		if(softsize>0){
			boolean flag=true; 
			if(softsize<=4){
				flag =false;
			}
			for(int i=0;i<softcount;i++){
				Row proRow = new Row();
				proRow.setId("softRowId"+i);
				proRow.setParent(softAuthRows); 
			    for(int a=0;a<4;a++){	
			    	GhRjzz soft = (GhRjzz)softAuthlist.get(i*4+a);
			    	Checkbox ch = new Checkbox();
					ch.setId("softcheck"+soft.getRjId());
					ch.setChecked(true);
					Label lab = new Label();
					lab.setId("softlabel"+soft.getRjId());
					lab.setValue(soft.getRjName().trim());					
					map.put(ch.getId(), ch);
					ch.setParent(proRow);
					lab.setParent(proRow);
					if((i*4+a)==softsize-1){
						break;
					}
			    }
			    if(!flag){
			    	break;
			    }
			}
		}else{
			softAuthRow1.setVisible(false);softAuthRow2.setVisible(false);
		}
		//科研期刊论文
		List<GhQklw> magaPapelist = qklwService.findByKuidAndType(user.getKuId(), GhQklw.KYLW, GhJslw.KYQK);
		List<GhQklw> teamagaPapelist = qklwService.findByKuidAndType(user.getKuId(), GhQklw.JYLW, GhJslw.JYQK);
		magaPapelist.addAll(teamagaPapelist);
		int magasize= magaPapelist.size();
		int magalastCols=magasize%4;
		int magacount =(magasize/4) + (magalastCols>0?1:0);
		if(magasize>0){
			boolean flag=true; 
			if(magasize<=4){
				flag =false;
			}
			for(int i=0;i<=magacount;i++){
				Row proRow = new Row();
				proRow.setId("magaRowId"+i);
				proRow.setParent(magaPaperRows); 
			    for(int a=0;a<4;a++){	
			    	GhQklw maga = (GhQklw)magaPapelist.get(i*4+a);
			    	Checkbox ch = new Checkbox();
					ch.setId("magacheck"+maga.getLwId());
					ch.setChecked(true);
					Label lab = new Label();
					lab.setId("magalabel"+maga.getLwId());
					lab.setValue(maga.getLwMc().trim());					
					map.put(ch.getId(), ch);
					ch.setParent(proRow);
					lab.setParent(proRow);
					if((i*4+a)==magasize-1){
						break;
					}
			    }
			    if(!flag){
			    	break;
			    }
			}
		}else{
			magaPaperRow1.setVisible(false);magaPaperRow2.setVisible(false);
		}
		//科研会议论文
		List<GhHylw> mettPapList = hylwService.findByKuidAndType(user.getKuId(), GhHylw.KYLW, GhJslw.KYHY);	
		List<GhHylw> teamettPapList = hylwService.findByKuidAndType(user.getKuId(), GhHylw.JYLW, GhJslw.JYHY);
		mettPapList.addAll(teamettPapList);
		int mettsize= mettPapList.size();
		int mettlastCols=mettsize%4;
		int mettcount =(mettsize/4) + (mettlastCols>0?1:0);
		if(mettPapList.size()>0){
			boolean flag=true; 
			if(mettsize<=4){
				flag =false;
			}
			for(int i=0;i<=mettcount;i++){
				Row proRow = new Row();
				proRow.setId("mettPapRowId"+i);
				proRow.setParent(mettingPaperRows); 
			    for(int a=0;a<4;a++){				
			    	GhHylw mepa = (GhHylw)mettPapList.get(i*4+a);
			    	Checkbox ch = new Checkbox();
					ch.setId("mettcheck"+mepa.getLwId());
					ch.setChecked(true);
					Label lab = new Label();
					lab.setId("mettlabel"+mepa.getLwId());
					lab.setValue(mepa.getLwMc().trim());					
					map.put(ch.getId(), ch);
					ch.setParent(proRow);
					lab.setParent(proRow);
					if((i*4+a)==mettPapList.size()-1){
						break;
					}
			    }
			    if(!flag){
			    	break;
			    }
			}
		}else{
			mettPaperRow1.setVisible(false);mettPaperRow2.setVisible(false);
		}
		//出版教材
		List<GhZz> pubtealist = zzService.findByKuidAndType(user.getKuId(),GhZz.JC,GhJszz.JC);
		int pubteasize= pubtealist.size();
		int pubtealastCols=pubteasize%4;
		int pubteacount =(pubteasize/4) + (pubtealastCols>0?1:0);
		if(pubteasize>0){
			boolean flag=true; 
			if(pubteasize<=4){
				flag =false;
			}
			for(int i=0;i<=pubteacount;i++){
				Row proRow = new Row();
				proRow.setId("pubteaRowId"+i);
				proRow.setParent(pubTeaRows); 
			    for(int a=0;a<4;a++){	
			    	GhZz zz = (GhZz)pubtealist.get(i*4+a);
			    	Checkbox ch = new Checkbox();
					ch.setId("pubteacheck"+zz.getZzId());
					ch.setChecked(true);
					Label lab = new Label();
					lab.setId("pubtealabel"+zz.getZzId());
					lab.setValue(zz.getZzMc().trim());					
					map.put(ch.getId(), ch);
					ch.setParent(proRow);
					lab.setParent(proRow);
					if((i*4+a)==pubteasize-1){
						break;
					}
			    }
			    if(!flag){
			    	break;
			    }
			}
		}else{
			pubTeaRow1.setVisible(false);pubTeaRow2.setVisible(false);
		}
	}
	
	/**
	* <li>功能描述：选择或不选择全部属性。
	 * @param null
	 * @return null
	 * @author bobo
	 */
	public void onCheck$allCheck(){
		//授课情况
		List<GhSk> teaCourelist = skService.findByKuid(user.getKuId());
		//科研获奖成果
		List<GhCg> cglist = cgService.findByKuid(user.getKuId(),GhCg.CG_KY,GhJsxm.HjKY);
		List<GhCg> teaCglist = cgService.findByKuid(user.getKuId(), GhCg.CG_JY,GhJsxm.HJJY);
		cglist.addAll(teaCglist);
		//科研项目
		List<GhXm> xmlist =xmService.findByKuidAndType(user.getKuId(), GhJsxm.KYXM);
		//软件著作权
		List<GhRjzz> softAuthlist = rjzzService.findByKuid(user.getKuId());
		//科研期刊论文
		List<GhQklw> magaPapelist = qklwService.findByKuidAndType(user.getKuId(), GhQklw.KYLW, GhJslw.KYQK);
		List<GhQklw> teamagaPapelist = qklwService.findByKuidAndType(user.getKuId(), GhQklw.JYLW, GhJslw.JYQK);
		magaPapelist.addAll(teamagaPapelist);
		//科研会议论文
		List<GhHylw> mettPapList = hylwService.findByKuidAndType(user.getKuId(), GhHylw.KYLW, GhJslw.KYHY);	
		List<GhHylw> teamettPapList = hylwService.findByKuidAndType(user.getKuId(), GhHylw.JYLW, GhJslw.JYHY);
		mettPapList.addAll(teamettPapList);
		//出版教材
		List<GhZz> pubtealist = zzService.findByKuidAndType(user.getKuId(),GhZz.JC,GhJszz.JC);
		
		
		if(allCheck.isChecked()){		
			for(int i=0;i<xmlist.size();i++){
				GhXm xm = (GhXm)xmlist.get(i);
				Checkbox ch = (Checkbox)map.get("xmcheck"+xm.getKyId()); 
			    ch.setChecked(true);
			}	
			for(int i=0;i<cglist.size();i++){
				GhCg cg = (GhCg)cglist.get(i);
				Checkbox ch = (Checkbox)map.get("cgcheck"+cg.getKyId()); 
			    ch.setChecked(true);
			}
			for(int i=0;i<mettPapList.size();i++){
				GhHylw mp = (GhHylw)mettPapList.get(i);
				Checkbox ch = (Checkbox)map.get("mettcheck"+mp.getLwId()); 
			    ch.setChecked(true);
			}
			for(int i=0;i<magaPapelist.size();i++){
				GhQklw mp = (GhQklw)magaPapelist.get(i);
				Checkbox ch = (Checkbox)map.get("magacheck"+mp.getLwId()); 
			    ch.setChecked(true);
			}
			for(int i=0;i<softAuthlist.size();i++){
				GhRjzz soft = (GhRjzz)softAuthlist.get(i);
				Checkbox ch = (Checkbox)map.get("softcheck"+soft.getRjId()); 
			    ch.setChecked(true);
			}
			for(int i=0;i<pubtealist.size();i++){
				GhZz zz = (GhZz)pubtealist.get(i);
				Checkbox ch = (Checkbox)map.get("pubteacheck"+zz.getZzId()); 
			    ch.setChecked(true);
			}
			for(int i=0;i<teaCourelist.size();i++){
				GhSk sk = (GhSk)teaCourelist.get(i);
				Checkbox ch = (Checkbox)map.get("teacoucheck"+sk.getSkId()); 
			    ch.setChecked(true);
			}
			proName.setChecked(true);proSource.setChecked(true);proNum.setChecked(true);proRank.setChecked(true);
			fruitName.setChecked(true);fruitTime.setChecked(true);fruitClass.setChecked(true);HjName.setChecked(true);
			mettPaperName.setChecked(true);mettPaperNaS.setChecked(true);mettTime.setChecked(true);mettPlace.setChecked(true);
			magaPaperName.setChecked(true);magaPaperKw.setChecked(true);magaTime.setChecked(true);magaPages.setChecked(true);
			softAuthName.setChecked(true);softPubTime.setChecked(true);softDjNum.setChecked(true);softDjTime.setChecked(true);
			pubTeaName.setChecked(true);pubTeaUnit.setChecked(true);pubTeaTime.setChecked(true);teaCourseName.setChecked(true);
			teaCourseUnit.setChecked(true);teaCourseTime.setChecked(true);teaCourseYear.setChecked(true);mettRank.setChecked(true);
			magaRank.setChecked(true);softRank.setChecked(true);pubTeaRank.setChecked(true);
		}else{			
			for(int i=0;i<xmlist.size();i++){
				GhXm xm = (GhXm)xmlist.get(i);
				Checkbox ch = (Checkbox)map.get("xmcheck"+xm.getKyId()); 
			    ch.setChecked(false);
			}
			for(int i=0;i<cglist.size();i++){
				GhCg cg = (GhCg)cglist.get(i);
				Checkbox ch = (Checkbox)map.get("cgcheck"+cg.getKyId()); 
			    ch.setChecked(false);
			}
			for(int i=0;i<mettPapList.size();i++){
				GhHylw mp = (GhHylw)mettPapList.get(i);
				Checkbox ch = (Checkbox)map.get("mettcheck"+mp.getLwId()); 
			    ch.setChecked(false);
			}
			for(int i=0;i<magaPapelist.size();i++){
				GhQklw mp = (GhQklw)magaPapelist.get(i);
				Checkbox ch = (Checkbox)map.get("magacheck"+mp.getLwId()); 
			    ch.setChecked(false);
			}
			for(int i=0;i<softAuthlist.size();i++){
				GhRjzz soft = (GhRjzz)softAuthlist.get(i);
				Checkbox ch = (Checkbox)map.get("softcheck"+soft.getRjId()); 
			    ch.setChecked(false);
			}
			for(int i=0;i<pubtealist.size();i++){
				GhZz zz = (GhZz)pubtealist.get(i);
				Checkbox ch = (Checkbox)map.get("pubteacheck"+zz.getZzId()); 
			    ch.setChecked(false);
			}
			for(int i=0;i<teaCourelist.size();i++){
				GhSk sk = (GhSk)teaCourelist.get(i);
				Checkbox ch = (Checkbox)map.get("teacoucheck"+sk.getSkId()); 
			    ch.setChecked(false);
			}
			proName.setChecked(false);proSource.setChecked(false);proNum.setChecked(false);proRank.setChecked(false);
			fruitName.setChecked(false);fruitTime.setChecked(false);fruitClass.setChecked(false);HjName.setChecked(false);
			mettPaperName.setChecked(false);mettPaperNaS.setChecked(false);mettTime.setChecked(false);mettPlace.setChecked(false);
			magaPaperName.setChecked(false);magaPaperKw.setChecked(false);magaTime.setChecked(false);magaPages.setChecked(false);
			softAuthName.setChecked(false);softPubTime.setChecked(false);softDjNum.setChecked(false);softDjTime.setChecked(false);
			pubTeaName.setChecked(false);pubTeaUnit.setChecked(false);pubTeaTime.setChecked(false);teaCourseName.setChecked(false);
			teaCourseUnit.setChecked(false);teaCourseTime.setChecked(false);teaCourseYear.setChecked(false);fruitRank.setChecked(false);
			mettRank.setChecked(false);magaRank.setChecked(false);softRank.setChecked(false);pubTeaRank.setChecked(false);
		}
	}
	
	/**
	 * 根据定制内容和显示模板导出个人评审信息
	 */
	public void onClick$export()
	{
		if(ps==null)
		{
			try {
				Messagebox.show("您尚未提出职称评审申请，请填写申请的基本信息并保存。","提示", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		String filePath = KyglControlUtil.getPath("/personalHomePageTemp") ;  
		//读取模板exportTemplate.xml	
		String temp = FileUtil.readUTF8(filePath+"\\"+"indicatorTemplate.xml");
		//文档标题
		GregorianCalendar calendar=new GregorianCalendar(); 
		int curYear = calendar.get(calendar.YEAR);//当前年份
		temp = temp.replaceAll("Title", teacher.getSchDept(teacher.getKdId()).trim()+curYear+"年职称评审基本信息表");
		//申请人姓名
		if(null==user.getKuName()||"".equals(user.getKuName()))
		{
			temp = temp.replaceAll("KuName","");
		}else{
			temp = temp.replaceAll("KuName",user.getKuName().trim());
		}
		//申请人单位 
		if(null==teacher.getYuDept(teacher.getKdId())||"".equals(teacher.getYuDept(teacher.getKdId())))
		{
			temp = temp.replaceAll("KuCompany", "");
		}else{
			temp = temp.replaceAll("KuCompany",teacher.getYuDept(teacher.getKdId()));
		}
		//申报专业一级学科 
		if(null==ps.getJspsSubject()||"".equals(ps.getJspsSubject()))
		{
			temp = temp.replaceAll("DeclareSubject","");
		}else{
			temp = temp.replaceAll("DeclareSubject",ps.getJspsSubject());
		}
		//晋升方式 
		if(null==ps.getJspsType()||"".equals(ps.getJspsType()))
		{
			temp = temp.replaceAll("Promotion", "正常");
		}else{
			temp = temp.replaceAll("Promotion",ps.getJspsType());
		}
		
		//申报专业技术职务
		List titleList = xypttitleService.findBytid(teacher.getThTitle());
		Title teacherTitle = new Title();
		if(null!=titleList&&titleList.size()>0)
		{
			teacherTitle = (Title)titleList.get(0);
		}
		if(null==ps.getJspsPosition()||"".equals(ps.getJspsPosition()))
		{
			String declareTitle = "";
			if(null!=teacherTitle.getPtiId()){
				List ptitleList = xypttitleService.findByPtid(teacherTitle.getPtiId());
				
				for(int i=0;i<ptitleList.size();i++)
				{
					Title pTitle = (Title)ptitleList.get(i);
					if(pTitle.getTiId().equals(teacherTitle.getTiId())){
						if(i<ptitleList.size()-1)
						{
							declareTitle = ((Title)ptitleList.get(i+1)).getTiName();
						}else{
							declareTitle = "";
						}
					}
				}
			}
			temp = temp.replaceAll("DeclareProfession",declareTitle);//待定
		}else{
			temp = temp.replaceAll("DeclareProfession",ps.getJspsPosition());
		}
		
		//工作时间
		if(null==teacher.getThStart()||"".equals(teacher.getThStart()))
		{
			temp = temp.replaceAll("WorkDate","");
		}else{
			temp = temp.replaceAll("WorkDate",DateUtil.getMonthString(teacher.getThStart()));//参加工作时间
		}
		//出生年月 
		if(null==user.getKuBirthday()||"".equals(user.getKuBirthday()))
		{
			temp = temp.replaceAll("Birthday", "");
		}else{
			temp = temp.replaceAll("Birthday", DateUtil.getMonthString(user.getKuBirthday()));
		}
		//最高学历
		String[] Educational = { " ", "博士毕业", "博士结业", "博士肄业", "双硕士",
				"硕士毕业", "硕士结业", "硕士肄业", "相当硕士毕业", "研究生班毕业", "研究生班结业", "研究生班肄业",
				"双本科", "本科毕业", "本科结业", "本科肄业", "相当本科毕业", "双大专", "大专毕业", "大专结业",
				"相当大专毕业", "中专毕业", "中专结业" };
		String edu=null;
		if (user.getKuEducational() == null || user.getKuEducational() == ""||user.getKuEducational().trim().equalsIgnoreCase("0")) {
			edu=" ";
		} else {
			edu = Educational[Integer.valueOf(user.getKuEducational())];
		}
		temp = temp.replaceAll("Hdegree", edu.trim());
		//学位
		String[] xuewei = { " ", "名誉博士", "博士", "硕士", "双硕士", "学士", "无" };
		String hxw=null;
		if (user.getKuEducational() == null || user.getKuEducational() == ""||user.getKuXuewei().trim().equalsIgnoreCase("0")) {
			hxw=" ";
		} else {
			hxw = xuewei[Integer.valueOf(user.getKuXuewei())];
		}
		temp = temp.replaceAll("Degree",hxw.trim());
		//最高学位毕业学校和专业
		String supchoo=null;
		String highm=null;
		if(teacher.getThSupschool()==null||teacher.getThSupschool().trim().equals("")){
			supchoo="";
		}else{
			supchoo=teacher.getThSupschool();
		}
		if(teacher.getThSupsub()==null||teacher.getThSupsub().trim().equals(" ")){
			highm="";
		}else{
			highm=teacher.getThSupsub();
		}		
		temp = temp.replaceAll("SchoolAndSubject",supchoo.trim()+" "+highm.trim());
		//最高学位毕业时间
		String hgtime=null;
		if(teacher.getThSupgratime()==null||teacher.getThSupgratime().length()==0){	
			hgtime = "";
		}else{
			hgtime =DateUtil.getMonthString(teacher.getThSupgratime());
		}			
		temp = temp.replaceAll("GraduTime",hgtime.trim());
		
		//修业年限 
		String[] year = { "", "两年", "两年半", "三年", "四年", "五年" };
		String hEduYear = "";
		if (teacher.getThSupeyear() != null && !"".equals(teacher.getThSupeyear())) {
			hEduYear = year[(Integer.valueOf(teacher.getThSupeyear()))];
		}
		temp = temp.replaceAll("StuYear",hEduYear);
		//现技术职务
		if(null==teacherTitle.getTiName()||"".equals(teacherTitle.getTiName()))
		{
			temp = temp.replaceAll("CurrentProfession","");	
		}else{
			temp = temp.replaceAll("CurrentProfession",teacherTitle.getTiName());		
		}
		//资格时间
		if(null==teacher.getThEmplTime()||"".equals(teacher.getThEmplTime()))
		{
			temp = temp.replaceAll("QualificationTime","");
		}else{
			temp = temp.replaceAll("QualificationTime",DateUtil.getMonthString(teacher.getThEmplTime()));
		}
		
		StringBuilder teachingSB = new StringBuilder();
		
		//教学工作量
		String teachingWorkload = "<w:p><w:pPr><w:ind w:first-line-chars=\"151\" /><w:rPr>" +
				"<w:sz w:val=\"24\" /></w:rPr></w:pPr><w:r><w:rPr><w:sz w:val=\"24\" />" +
				"</w:rPr><w:t>TeachingWorkload</w:t></w:r></w:p>";
		String termStart = "<w:p><w:pPr><w:rPr><w:rFonts w:ascii=\"宋体\" w:h-ansi=\"宋体\" w:hint=\"fareast\"/>" +
				"<wx:font wx:val=\"宋体\"/><w:sz w:val=\"24\"/></w:rPr></w:pPr><w:r><w:rPr><w:rFonts w:ascii=\"宋体\"" +
				" w:h-ansi=\"宋体\" w:hint=\"fareast\"/><wx:font wx:val=\"宋体\"/><w:sz w:val=\"24\"/></w:rPr><w:t>";
		String termEnd = "</w:t></w:r></w:p>";
		String courseStart ="<w:p><w:pPr><w:ind w:first-line-chars=\"100\"/><w:rPr><w:rFonts w:ascii=\"宋体\"" +
				" w:hint=\"fareast\"/><wx:font wx:val=\"宋体\"/><w:sz w:val=\"24\"/></w:rPr></w:pPr><w:r><w:rPr>" +
				"<w:rFonts w:ascii=\"宋体\" w:h-ansi=\"宋体\" w:hint=\"fareast\"/><wx:font wx:val=\"宋体\"/>" +
				"<w:sz w:val=\"24\"/></w:rPr><w:t>";
		String courseClass = "</w:t></w:r><w:proofErr w:type=\"gramStart\"/><w:r><w:rPr>" +
				"<w:rFonts w:ascii=\"宋体\" w:h-ansi=\"宋体\" w:hint=\"fareast\"/><wx:font wx:val=\"宋体\"/>" +
				"<w:sz w:val=\"24\"/></w:rPr><w:t>";
		String classHours = "</w:t></w:r><w:r><w:rPr><w:rFonts w:ascii=\"宋体\" w:h-ansi=\"宋体\"/>" +
				"<wx:font wx:val=\"宋体\"/><w:sz w:val=\"24\"/></w:rPr><w:t>";
		String hoursEnd = "</w:t></w:r><w:r><w:rPr><w:rFonts w:ascii=\"宋体\" w:hint=\"fareast\"/>" +
				"<wx:font wx:val=\"宋体\"/><w:sz w:val=\"24\"/></w:rPr><w:t>学时</w:t></w:r></w:p>";
		
		List skList = skService.findByKuid(user.getKuId());
		for(int i=0;i<skList.size();i++)
		{
			GhSk gs = (GhSk)skList.get(i);
			Checkbox ch = (Checkbox)map.get("teacoucheck"+gs.getSkId());  
			if(ch.isChecked()){
				//学期
				teachingSB.append(termStart);
				if(teaCourseYear.isChecked()){
					String term = "";
					if(null!=gs.getSkYear()&&!"".equals(gs.getSkYear()))
					{
						term = gs.getSkYear();
					}
					teachingSB.append(term);
				}
				teachingSB.append(termEnd);
				//课程名称
				teachingSB.append(courseStart);
				if(teaCourseName.isChecked()){
					String course = "";
					if(null!=gs.getSkMc()&&!"".equals(gs.getSkMc()))
					{
						course = gs.getSkMc();
					}
					teachingSB.append(course+"，");
				}
				teachingSB.append(courseClass);
				
				//课程班级
				if(teaCourseUnit.isChecked()){
					String grade = "";
					if(null!=gs.getSkGrade()&&!"".equals(gs.getSkGrade()))
					{
						grade = gs.getSkGrade();
					}
					teachingSB.append(grade+"，");
				}
				teachingSB.append(classHours);
				
				//课程学时
				if(teaCourseTime.isChecked()){
					String work = 0+"";
					if(null!=gs.getThWork()&&!"".equals(gs.getThWork()))
					{
						work = gs.getThWork();
					}
					teachingSB.append(work);
				}
				teachingSB.append(hoursEnd);
			}
		}
		temp = temp.replaceAll(teachingWorkload,teachingSB.toString());	
		
		//总教学学时
		List teachList = skService.findByKuid(user.getKuId());
		int total = 0;
		int startYear = curYear;
		int endYear = 0;
		if(null!=teacher.getThEmplTime()&&!"".equals(teacher.getThEmplTime())){
			endYear = Integer.valueOf(teacher.getThEmplTime().substring(0, 4));
		}
		for(int i=0;i<teachList.size();i++)
		{
			GhSk gs = (GhSk)teachList.get(i);
			String hours = 0+"";
			if(null!=gs.getThWork()&&!"".equals(gs.getThWork()))
			{
				hours = gs.getThWork();
			}
			total = total + Integer.valueOf(hours);
			if(null==gs.getSkYear()||"".equals(gs.getSkYear()))
			{
			}else if(null!=gs.getSkYear()&&gs.getSkYear().length()>4){
				int curThYear = Integer.valueOf(gs.getSkYear().substring(0, 4));
				if(curThYear>endYear)
					endYear = curThYear;
				if(curThYear<startYear)
					startYear = curThYear;
			}
			
		}
		temp = temp.replaceAll("TotalClassHours",total+"");
		//年均学时数
		int teachYears = 0;
		if(total>0)
		{
			teachYears = endYear - startYear + 1;
			temp = temp.replaceAll("AverageClassHours",total/teachYears+"");
		}else{
			temp = temp.replaceAll("AverageClassHours",0+"");
		}
		//其他教学经历  待定
		String otherWorkload = "<w:p><w:pPr><w:rPr><w:sz w:val=\"24\" /></w:rPr></w:pPr><w:r><w:rPr>" +
				"<w:rFonts w:hint=\"fareast\" /><w:sz w:val=\"24\" /></w:rPr><w:t>OtherWorkload</w:t></w:r></w:p>";
		
		String otherWorkStart = "<w:p><w:pPr><w:rPr><w:rFonts w:ascii=\"宋体\" w:hint=\"fareast\"/>" +
				"<wx:font wx:val=\"宋体\"/><w:sz w:val=\"24\"/></w:rPr></w:pPr><w:r><w:rPr><w:rFonts" +
				" w:ascii=\"宋体\" w:h-ansi=\"宋体\" w:hint=\"fareast\"/><wx:font wx:val=\"宋体\"/>" +
				"<w:sz w:val=\"24\"/></w:rPr><w:t>";
		String otherWorkEnd="</w:t></w:r></w:p>";
		StringBuilder otherSB = new StringBuilder();
		List zdxsList = pxService.findByKuid(user.getKuId());
		if(zdxsList.size()>0)
		{
			for(int i=0;i<zdxsList.size();i++)
			{
				GhPx px = (GhPx)zdxsList.get(i);
				String cen = "";
				if(null!=px.getPxBrzy()&&!"".equals(px.getPxBrzy()))
				{
					cen = cen + px.getPxBrzy()+"参加";
				}
				if(null!=px.getPxMc()&&!"".equals(px.getPxMc()))
				{
					cen = cen + px.getPxMc()+"，获";
				}
				if(null!=px.getPxJx()&&!"".equals(px.getPxJx()))
				{
					cen = cen + px.getPxJx();
				}
				otherSB.append(otherWorkStart + (i+2)+ "、" + cen + otherWorkEnd);
			}
			temp = temp.replaceAll(otherWorkload, otherSB.toString());
		}else{
			temp = temp.replaceAll("OtherWorkload", "");
		}
		//获奖情况 
		String patentStart = "<w:p><w:pPr><w:rPr><w:rFonts w:ascii=\"仿宋_GB2312\" w:fareast=\"仿宋_GB2312\"" +
			" w:hint=\"fareast\"/><wx:font wx:val=\"仿宋_GB2312\"/><w:sz-cs w:val=\"21\"/></w:rPr></w:pPr>" +
			"<w:r><w:rPr><w:rFonts w:ascii=\"仿宋_GB2312\" w:fareast=\"仿宋_GB2312\" w:hint=\"fareast\"/>" +
			"<wx:font wx:val=\"仿宋_GB2312\"/><w:sz-cs w:val=\"21\"/></w:rPr><w:t>";
		String patentEnd = "</w:t></w:r></w:p>";
		String awards = "<w:p><w:pPr><w:rPr><w:sz-cs w:val=\"21\" /></w:rPr></w:pPr><w:r><w:rPr>" +
				"<w:rFonts w:hint=\"fareast\" /><w:sz-cs w:val=\"21\" /></w:rPr><w:t>Awards</w:t></w:r></w:p>";
		String cgString="";		
		List<GhCg> cglist = cgService.findByKuid(user.getKuId(),GhCg.CG_KY,GhJsxm.HjKY);
		List<GhCg> teaCglist = cgService.findByKuid(user.getKuId(), GhCg.CG_JY,GhJsxm.HJJY);
		cglist.addAll(teaCglist);
		if(cglist.size()>0){
			int cgNum = 0;
			for(int i=0;i<cglist.size();i++){
				GhCg cg = (GhCg)cglist.get(i);
				Checkbox ch = (Checkbox)map.get("cgcheck"+cg.getKyId()); 
			    if(ch.isChecked()){	
			    	cgNum = cgNum +1;
			    	String cen="";
			    	if(fruitName.isChecked()){
			    		cen = cen +cg.getKyMc().trim()+"，";
			    	}
			    	if(fruitTime.isChecked()){
			    		if(!cg.getKySj().equals("")||cg.getKySj()!=null){
			    			cen = cen +cg.getKySj().trim()+"，";
				        }
			    	}
			    	if(HjName.isChecked()){
			    		if(!cg.getKyHjmc().equals("")||cg.getKyHjmc()!=null){
			    			cen = cen +cg.getKyHjmc().trim()+"，";
				        }
			    	}
			    	if(fruitClass.isChecked()){
			    		if(!cg.getKyDj().equals("")||cg.getKyDj()!=null){
				        	cen =cen +cg.getKyDj().trim()+"，";
				        }
			    	}		
			    	if(fruitRank.isChecked()){
			    		GhJsxm jx=jsxmService.findByXmidAndKuidAndType(cg.getKyId(), user.getKuId(), GhJsxm.HjKY);
			    		if(null!=jx&&jx.getKyGx()>0){
			    			cen = cen + "第"+ new ChineseUpperCaser(jx.getKyGx())+"参与者";
			    		}
			    	}
			    	cgString= cgString + patentStart +"["+ (cgNum)+ "] " + cen + patentEnd;
				}
			}		
			temp = temp.replaceAll(awards, cgString);
		}else{
			temp = temp.replaceAll("Awards", "");
		}
		//科研项目
		String project = "<w:p><w:pPr><w:rPr><w:sz w:val=\"24\" /></w:rPr></w:pPr><w:r><w:rPr>" +
				"<w:rFonts w:hint=\"fareast\" /><w:sz w:val=\"24\" /></w:rPr><w:t>Project</w:t></w:r></w:p>";
		String kyxmListString="";	
		List<GhXm> xmlist =xmService.findByKuidAndType(user.getKuId(), GhJsxm.KYXM);
//		List<GhXm> teaProlist = xmService.findByKuidAndType(user.getKuId(), GhJsxm.JYXM);
//		xmlist.addAll(teaProlist);
		if(xmlist.size()>0)
		{
			int xmNum = 0;
			for(int i=0;i<xmlist.size();i++){
				GhXm xm = (GhXm)xmlist.get(i);
				Checkbox ch = (Checkbox)map.get("xmcheck"+xm.getKyId()); 
			    if(ch.isChecked()){
			    	xmNum = xmNum + 1;
			    	String cen="";	
			    	if(proName.isChecked()){
			    		if(!xm.getKyMc().equals("")||xm.getKyMc()!=null){
			    			cen = cen +xm.getKyMc().trim()+"，";
				        }
			    	}
			    	if(proSource.isChecked()){
			    		if(!xm.getKyLy().equals("")||xm.getKyLy()!=null){
				        	cen = cen + xm.getKyLy().trim()+"，";
				        }
			    	}
			    	if(proNum.isChecked()){
			    		if(!xm.getKyNumber().equals("")||xm.getKyNumber()!=null){
			    			cen = cen +xm.getKyNumber().trim()+"，";
				        }
			    	}
			    	if(proRank.isChecked()){
			    		GhJsxm jx=(GhJsxm)jsxmService.findByXmidAndKuidAndType(xm.getKyId(), user.getKuId(),GhJsxm.KYXM);
			    		if(null!=jx&&jx.getKyGx()>0)
			    		{
			    			cen = cen + "第"+ new ChineseUpperCaser(jx.getKyGx())+"参与者";
			    		}
			    			
			    	}
			    	kyxmListString = kyxmListString + patentStart +"["+ (xmNum)+ "] " + cen + patentEnd;
				}
			}
			temp = temp.replaceAll(project, kyxmListString);
		}else{
			temp = temp.replaceAll("Project", "");
		}
		
		//软件著作权 
		String patent = "<w:p><w:pPr><w:rPr><w:sz w:val=\"24\" /></w:rPr></w:pPr><w:r><w:rPr>" +
				"<w:rFonts w:hint=\"fareast\" /><w:sz w:val=\"24\" /></w:rPr><w:t>Patents</w:t></w:r></w:p>";
		String softString="";	
		List<GhRjzz> softAuthlist = rjzzService.findByKuid(user.getKuId());	
		if(softAuthlist.size()>0){
			int zzNum = 0;
			for(int i=0;i<softAuthlist.size();i++){
				GhRjzz soft = (GhRjzz)softAuthlist.get(i);
				Checkbox ch = (Checkbox)map.get("softcheck"+soft.getRjId()); 
			    if(ch.isChecked()){
			    	zzNum = zzNum + 1;
			    	String cen="";
			    	if(softAuthName.isChecked()){
			    		if(soft.getRjName()!=null)
			    			cen = cen +soft.getRjName().trim()+"，";
			    	}
			    	if(softPubTime.isChecked()){
			    		if(soft.getRjFirtime()!=null)
			    			cen = cen +soft.getRjFirtime().trim()+"，";
			    	}
			    	if(softDjNum.isChecked()){
			    		if(soft.getRjRegisno()!=null)
			    			cen = cen +soft.getRjRegisno().trim()+"，";
			    	}
			    	if(softDjTime.isChecked()){
			    		if(soft.getRjTime()!=null)
			    			cen = cen +soft.getRjTime().trim()+"，";
			    	}
			    	if(softRank.isChecked()){
			    		GhJslw jslw=jslwService.findByKuidAndLwidAndType(user.getKuId(), soft.getRjId(), GhJslw.RJZZ);
			    		if(null!=jslw&&jslw.getLwSelfs()>0){
			    			cen = cen + "第"+ new ChineseUpperCaser(jslw.getLwSelfs())+"参与者";
			    		}
			    	}
			    	softString= softString + patentStart+"["+(zzNum)+"] "+cen+patentEnd;
				}
			}
			temp = temp.replaceAll(patent, softString);
		}else{
			temp = temp.replaceAll("Patents", "");
		}
		
		//论文
		String materialStart = "<w:p><w:r><w:rPr><w:rFonts w:hint=\"fareast\"/><w:kern w:val=\"0\"/>" +
			"<w:sz-cs w:val=\"21\"/><w:lang w:val=\"IT\"/></w:rPr><w:t>";
		String materialEnd = "</w:t></w:r></w:p>";
		String papersStart = "<w:p><w:pPr><w:spacing w:line=\"264\" w:line-rule=\"auto\" /><w:rPr><w:lang w:val=\"IT\" />" +
				"</w:rPr></w:pPr><w:r><w:rPr><w:rFonts w:hint=\"fareast\" /><w:lang w:val=\"IT\" /></w:rPr><w:t>";
		String papersEnd = "</w:t></w:r></w:p>";
		String kyPageString="";		
		List<GhQklw> magaPapelist = qklwService.findByKuidAndType(user.getKuId(), GhQklw.KYLW, GhJslw.KYQK);
		List<GhQklw> teamagaPapelist = qklwService.findByKuidAndType(user.getKuId(), GhQklw.JYLW, GhJslw.JYQK);
		magaPapelist.addAll(teamagaPapelist);
		int paperSelectNum = 0;//选中的期刊论文总数
		if(magaPapelist.size()>0){
			for(int i=0;i<magaPapelist.size();i++){
				GhQklw maga = (GhQklw)magaPapelist.get(i);
				Checkbox ch = (Checkbox)map.get("magacheck"+maga.getLwId()); 
				 if(ch.isChecked()){
					 paperSelectNum = paperSelectNum+1;
					 String cen="";
					 if(magaPaperName.isChecked()){
						 if(maga.getLwMc()!=null)
				    			cen = cen +maga.getLwMc().trim()+"，";
					 }
					 if(magaPaperKw.isChecked()){
						 if(maga.getLwKw()!=null)
				    			cen = cen +maga.getLwKw().trim()+"，";
					 }
					 if(magaTime.isChecked()){
						 if(maga.getLwFbsj()!=null)
				    			cen = cen +maga.getLwFbsj().trim()+"，";
					 }
					 if(magaPages.isChecked()){
						 if(maga.getLwPages()!=null)
				    			cen = cen +maga.getLwPages().trim()+"，";
					 }
					 if(magaRank.isChecked()){
						 GhJslw jslw = jslwService.findByKuidAndLwidAndType(user.getKuId(), maga.getLwId(), GhJslw.KYQK);
				    		if(jslw.getLwSelfs()!=null)
				    			cen = cen + "第" +new ChineseUpperCaser(jslw.getLwSelfs())+"作者";
					 }
			    		if(maga.getLwCenter() == (GhQklw.LWHX_YES.shortValue()))
			    			cen = cen +".核心期刊";
			    		if(null!=maga.getLwLocation()&&!"".equals(maga.getLwLocation()))
			    			cen = cen +"(，"+maga.getLwLocation()+")";
			    	kyPageString = kyPageString + materialStart+"["+paperSelectNum+"] " + cen + materialEnd;
				 }
			    	
			}
			temp = temp.replaceAll(papersStart+"Papers"+papersEnd, kyPageString);
		}else{
			temp = temp.replaceAll("Papers", "");
		}
		//科研会议论文		
    	String kyMeetString="";		
		List<GhHylw> mettPapList = hylwService.findByKuidAndType(user.getKuId(), GhHylw.KYLW, GhJslw.KYHY);	
		List<GhHylw> teamettPapList = hylwService.findByKuidAndType(user.getKuId(), GhHylw.JYLW, GhJslw.JYHY);
		mettPapList.addAll(teamettPapList);
		if(mettPapList.size()>0){
			for(int i=0;i<mettPapList.size();i++){
				GhHylw mepa = (GhHylw)mettPapList.get(i);
				Checkbox ch = (Checkbox)map.get("mettcheck"+mepa.getLwId()); 
			    if(ch.isChecked()){	
			    	paperSelectNum = paperSelectNum + 1;
			    	String cen="";
			    	if(mettPaperName.isChecked()){
			    		cen = cen +mepa.getLwMc().trim()+"，";
			    	}
			    	if(mettPaperNaS.isChecked()){			    	
			    		if(!mepa.getLwKw().equals("")||mepa.getLwKw()!=null){
			    			cen = cen +mepa.getLwKw().trim()+"，";
				        }
			    	}
			    	if(mettTime.isChecked()){
			    		if(mepa.getLwTime()!=null){
			    			cen = cen +mepa.getLwTime().trim()+"，";
			    		}			    		
			    	}
			    	if(mettPlace.isChecked()){
			    		if(!mepa.getLwPlace().equals("")||mepa.getLwPlace()!=null){
				        	cen =cen + mepa.getLwPlace().trim()+"，";
				        }
			    	}	
			    	if(mettRank.isChecked()){
			    		GhJslw gj = jslwService.findByKuidAndLwidAndType(user.getKuId(), mepa.getLwId(), GhJslw.KYHY);
			    		if(null!=gj&&gj.getLwSelfs()>0){
			    			cen = cen + "第"+ new ChineseUpperCaser(gj.getLwSelfs())+"作者";
			    		}
			    	}
			    	kyMeetString = kyPageString + materialStart+"["+paperSelectNum+"] " + cen + materialEnd;
				}
			}
			temp = temp.replaceAll(papersStart+"MettPapers"+papersEnd, kyMeetString);
		}else{
			temp = temp.replaceAll("MettPapers", "");
		}
		
		//教材
		String material = "<w:p><w:pPr><w:rPr><w:sz w:val=\"24\" /></w:rPr></w:pPr><w:r><w:rPr>" +
				"<w:sz w:val=\"24\" /></w:rPr><w:t>TeachingMaterial</w:t></w:r></w:p>";
		String pubString="";		
		List<GhZz> pubtealist = zzService.findByKuidAndType(user.getKuId(),GhZz.JC,GhJszz.JC);
		if(pubtealist.size()>0){
			int jcNum = 0;
			for(int i=0;i<pubtealist.size();i++){
				GhZz zz= (GhZz)pubtealist.get(i);
				Checkbox ch = (Checkbox)map.get("pubtealabel"+zz.getZzId()); 
			    if(ch.isChecked()){
			    	jcNum = jcNum +1;
			    	String cen="";
			    	if(pubTeaName.isChecked()){
			    		if(zz.getZzMc()!=null)
			    			cen = cen +zz.getZzMc().trim()+"，";
			    	}
			    	if(pubTeaUnit.isChecked()){
			    		if(zz.getZzKw()!=null)
			    			cen = cen +zz.getZzKw().trim()+"，";
			    	}
			    	if(pubTeaTime.isChecked()){
			    		if(zz.getZzPublitime()!=null)
			    			cen = cen +zz.getZzPublitime().trim()+"，";
			    	}
			    	if(pubTeaRank.isChecked()){
			    		GhJszz jszz=jszzService.findByKuidAndLwidAndType(user.getKuId(), zz.getZzId(), GhJszz.JC);
			    		if(null!=jszz){
			    			cen = cen + jszz.getZzSelfs();
			    		}
			    	}
			    	pubString = pubString +  materialStart + "["+(jcNum)+"] "+cen+ materialEnd;
			    }
			temp = temp.replaceAll(material, pubString);
			}
		}else{
			temp = temp.replaceAll("TeachingMaterial", "");
		}
		//任职资历(年)
		if(null==teacher.getThEnter()||"".equals(teacher.getThEnter()))
		{
			temp = temp.replaceAll("WorkingTime",1+"");
		}else{
			int enterYear = Integer.valueOf(teacher.getThEnter().substring(0, 4));
			temp = temp.replaceAll("WorkingTime",curYear-enterYear+1+"");
		}
		//年度考核
		temp = temp.replaceAll("AnnualAssessment",ps.getJspsAnnualAssessment());//待定
		//荣誉称号
		List rychList = rychService.FindByKuid(user.getKuId());
		String rychString = "";
		if(rychList.size()>0){
			for(int i=0;i<rychList.size();i++)
			{
				Jxkh_Honour rych = (Jxkh_Honour)rychList.get(i);
				rychString = rychString + rych.getRyYear() + "年" +rych.getRyName()+";"; 
			}
			temp = temp.replaceAll("Honor",rychString.substring(0, rychString.lastIndexOf(";")));
		}else{
			temp = temp.replaceAll("Honor","");
		}
		
//		//学历
//		temp = temp.replaceAll("Degree",hgtime.trim());
		//外语考试(是否合格)
		temp = temp.replaceAll("ForeignLanguageExamtion",ps.getJspsForeign());
		//计算机考试(是否合格)
		temp = temp.replaceAll("ComputerExamtion",ps.getJspsComputer());	
		
		//获奖项目统计
		int allGjjHjCount = xmService.getHjCountByKuidGx(user.getKuId(), GhXm.GJJ, 0);
		int allSbjHjCount = xmService.getHjCountByKuidGx(user.getKuId(), GhXm.SBJ, 0);
		int allStjHjCount = xmService.getHjCountByKuidGx(user.getKuId(), GhXm.STJ, 0);
		int firGjjHjCount = xmService.getHjCountByKuidGx(user.getKuId(), GhXm.GJJ, 1);
		int firSbjHjCount = xmService.getHjCountByKuidGx(user.getKuId(), GhXm.SBJ, 1);
		int firStjHjCount = xmService.getHjCountByKuidGx(user.getKuId(), GhXm.STJ, 1);
		int secGjjHjCount = xmService.getHjCountByKuidGx(user.getKuId(), GhXm.GJJ, 2);
		int secSbjHjCount = xmService.getHjCountByKuidGx(user.getKuId(), GhXm.SBJ, 2);
		int secStjHjCount = xmService.getHjCountByKuidGx(user.getKuId(), GhXm.STJ, 2);
		int thiGjjHjCount = xmService.getHjCountByKuidGx(user.getKuId(), GhXm.GJJ, 3);
		int thiSbjHjCount = xmService.getHjCountByKuidGx(user.getKuId(), GhXm.SBJ, 3);
		int thiStjHjCount = xmService.getHjCountByKuidGx(user.getKuId(), GhXm.STJ, 3);
		int fouGjjHjCount = xmService.getHjCountByKuidGx(user.getKuId(), GhXm.GJJ, 4);
		int fouSbjHjCount = xmService.getHjCountByKuidGx(user.getKuId(), GhXm.SBJ, 4);
		int fouStjHjCount = xmService.getHjCountByKuidGx(user.getKuId(), GhXm.STJ, 4);
		int fivGjjHjCount = xmService.getHjCountByKuidGx(user.getKuId(), GhXm.GJJ, 5);
		int fivSbjHjCount = xmService.getHjCountByKuidGx(user.getKuId(), GhXm.SBJ, 5);
		int fivStjHjCount = xmService.getHjCountByKuidGx(user.getKuId(), GhXm.STJ, 5);
		int elseGjjHjCount = allGjjHjCount-firGjjHjCount-secGjjHjCount-thiGjjHjCount-fouGjjHjCount-fivGjjHjCount;
		int elseSbjHjCount = allSbjHjCount-firGjjHjCount-secSbjHjCount-thiSbjHjCount-fouSbjHjCount-fivSbjHjCount;
		int elseStjHjCount = allStjHjCount-firStjHjCount-secStjHjCount-thiStjHjCount-fouStjHjCount-fivStjHjCount;
		
		//项目统计
		int allGjjCount = xmService.getCountByKuidGx(user.getKuId(), GhXm.GJJ, 0);
		int allSbjCount = xmService.getCountByKuidGx(user.getKuId(), GhXm.SBJ, 0);
		int allStjCount = xmService.getCountByKuidGx(user.getKuId(), GhXm.STJ, 0);
		int firGjjCount = xmService.getCountByKuidGx(user.getKuId(), GhXm.GJJ, 1);
		int firSbjCount = xmService.getCountByKuidGx(user.getKuId(), GhXm.SBJ, 1);
		int firStjCount = xmService.getCountByKuidGx(user.getKuId(), GhXm.STJ, 1);
		int secGjjCount = xmService.getCountByKuidGx(user.getKuId(), GhXm.GJJ, 2);
		int secSbjCount = xmService.getCountByKuidGx(user.getKuId(), GhXm.SBJ, 2);
		int secStjCount = xmService.getCountByKuidGx(user.getKuId(), GhXm.STJ, 2);
		int thiGjjCount = xmService.getCountByKuidGx(user.getKuId(), GhXm.GJJ, 3);
		int thiSbjCount = xmService.getCountByKuidGx(user.getKuId(), GhXm.SBJ, 3);
		int thiStjCount = xmService.getCountByKuidGx(user.getKuId(), GhXm.STJ, 3);
		int fouGjjCount = xmService.getCountByKuidGx(user.getKuId(), GhXm.GJJ, 4);
		int fouSbjCount = xmService.getCountByKuidGx(user.getKuId(), GhXm.SBJ, 4);
		int fouStjCount = xmService.getCountByKuidGx(user.getKuId(), GhXm.STJ, 4);
		int fivGjjCount = xmService.getCountByKuidGx(user.getKuId(), GhXm.GJJ, 5);
		int fivSbjCount = xmService.getCountByKuidGx(user.getKuId(), GhXm.SBJ, 5);
		int fivStjCount = xmService.getCountByKuidGx(user.getKuId(), GhXm.STJ, 5);
		int elseGjjCount = allGjjCount-firGjjCount-secGjjCount-thiGjjCount-fouGjjCount-fivGjjCount;
		int elseSbjCount = allSbjCount-firGjjCount-secSbjCount-thiSbjCount-fouSbjCount-fivSbjCount;
		int elseStjCount = allStjCount-firStjCount-secStjCount-thiStjCount-fouStjCount-fivStjCount;
		
		//发明专利
		int allFmzlCount = fmzlService.getCountBykuidSelfs(user.getKuId(), 0);
		int firFmzlCount = fmzlService.getCountBykuidSelfs(user.getKuId(), 1);
		int secFmzlCount = fmzlService.getCountBykuidSelfs(user.getKuId(), 2);
		int thiFmzlCount = fmzlService.getCountBykuidSelfs(user.getKuId(), 3);
		int fouFmzlCount = fmzlService.getCountBykuidSelfs(user.getKuId(), 4);
		int fivFmzlCount = fmzlService.getCountBykuidSelfs(user.getKuId(), 5);
		int elseFmzlCount = allFmzlCount-firFmzlCount-secFmzlCount-thiFmzlCount-fouFmzlCount-fivFmzlCount;
		
		//教材统计
		List<Long> zbjcList = jszzService.findCountByKuidAndType(user.getKuId(), GhJszz.JC, GhJszz.chiefEditor);//主编的教材
		String zbjcCount = zbjcList!=null?zbjcList.get(0)+"":"";

		List<Long> fzbjcList = jszzService.findCountByKuidAndType(user.getKuId(), GhJszz.JC, GhJszz.DeputyEditor);//副主编的教材
		String fzbjcCount = fzbjcList!=null?fzbjcList.get(0)+"":"";

		List<Long> cbjcList = jszzService.findCountByKuidAndType(user.getKuId(), GhJszz.JC, GhJszz.editor);//参编的教材
		String cbjcCount = cbjcList!=null?cbjcList.get(0)+"":"";

		int jcWords = jszzService.getWordsByKuidType(user.getKuId(), GhJszz.JC);
		
		//论著统计统计
		List<Long> firstzzList = jszzService.findCountByKuidAndType(user.getKuId(), GhJszz.ZZ, GhJszz.chiefEditor);//
		int firstzzCount = firstzzList!=null?((Long)firstzzList.get(0)).intValue():0;

		List<Long> secondzzList = jszzService.findCountByKuidAndType(user.getKuId(), GhJszz.ZZ, GhJszz.DeputyEditor);//
		int secondzzCount = secondzzList!=null?((Long)secondzzList.get(0)).intValue():0;

		List<Long> thirdzzList = jszzService.findCountByKuidAndType(user.getKuId(), GhJszz.ZZ, GhJszz.editor);//
		int thirdzzCount = thirdzzList!=null?((Long)thirdzzList.get(0)).intValue():0;

		int zzWords = jszzService.getWordsByKuidType(user.getKuId(), GhJszz.ZZ);
		
		//期刊论文统计
		List<Long> allQkList = qklwService.findCountByKuidCenterSelfs(user.getKuId(), GhQklw.LWHX_NO, GhQklw.allAuthor);
		int allQkCount = allQkList!=null?((Long)allQkList.get(0)).intValue():0;

		List<Long> fQkList = qklwService.findCountByKuidCenterSelfs(user.getKuId(), GhQklw.LWHX_NO, GhQklw.firstAuthor);
		int fQkCount = fQkList!=null?((Long)fQkList.get(0)).intValue():0;

		List<Long> sQkList = qklwService.findCountByKuidCenterSelfs(user.getKuId(), GhQklw.LWHX_NO, GhQklw.secondAuthor);
		int sQkCount = sQkList!=null?((Long)sQkList.get(0)).intValue():0;

		List<Long> tQkList = qklwService.findCountByKuidCenterSelfs(user.getKuId(), GhQklw.LWHX_NO, GhQklw.thirdAuthor);
		int tQkCount = tQkList!=null?((Long)tQkList.get(0)).intValue():0;

		int elseQkCount = allQkCount-fQkCount-sQkCount-tQkCount;//其他
		
		String indexes = "'"+GhQklw.SCI+"','"+GhQklw.EI+"','"+GhQklw.ISTP+"'";
		List<Long> allMCenterQkList = qklwService.findCountByKuidRecordSelfs(user.getKuId(),indexes, GhQklw.allAuthor);
		int allMCenterQkCount = allMCenterQkList!=null?((Long)allMCenterQkList.get(0)).intValue():0;
		
		List<Long> fMCenterQkList = qklwService.findCountByKuidRecordSelfs(user.getKuId(), indexes, GhQklw.firstAuthor);
		int fMCenterQkCount = fMCenterQkList!=null?((Long)fMCenterQkList.get(0)).intValue():0;

		List<Long> sMCenterQkList = qklwService.findCountByKuidRecordSelfs(user.getKuId(), indexes, GhQklw.secondAuthor);
		int sMCenterQkCount = sMCenterQkList!=null?((Long)sMCenterQkList.get(0)).intValue():0;

		List<Long> tMCenterQkList = qklwService.findCountByKuidRecordSelfs(user.getKuId(), indexes, GhQklw.thirdAuthor);
		int tMCenterQkCount = tMCenterQkList!=null?((Long)tMCenterQkList.get(0)).intValue():0;

		int elseMCenterQkCount = allMCenterQkCount-fMCenterQkCount-sMCenterQkCount-tMCenterQkCount;
		
		
		List<Long> allCenterQkList = qklwService.findCountByKuidCenterSelfs(user.getKuId(), GhQklw.LWHX_YES, GhQklw.allAuthor);
		int allCenterQkCount = allCenterQkList!=null?((Long)allCenterQkList.get(0)).intValue():0;

		List<Long> fCenterQkList = qklwService.findCountByKuidCenterSelfs(user.getKuId(), GhQklw.LWHX_YES, GhQklw.firstAuthor);
		int fCenterQkCount = fCenterQkList!=null?((Long)fCenterQkList.get(0)).intValue():0;
		
		List<Long> sCenterQkList = qklwService.findCountByKuidCenterSelfs(user.getKuId(), GhQklw.LWHX_YES, GhQklw.secondAuthor);
		int sCenterQkCount = sCenterQkList!=null?((Long)sCenterQkList.get(0)).intValue():0;

		List<Long> tCenterQkList = qklwService.findCountByKuidCenterSelfs(user.getKuId(), GhQklw.LWHX_YES, GhQklw.thirdAuthor);
		int tCenterQkCount = tCenterQkList!=null?((Long)tCenterQkList.get(0)).intValue():0;

		int elseCenterQkCount = allCenterQkCount-fCenterQkCount-sCenterQkCount-tCenterQkCount-elseMCenterQkCount;
		
		int lwWords = qklwService.getWordsByKuidIsCenter(user.getKuId(), GhQklw.LWHX_NO);
		int centerLwWords = qklwService.getWordsByKuidRecord(user.getKuId(), indexes);
		int mCenterLwWords = qklwService.getWordsByKuidIsCenter(user.getKuId(), GhQklw.LWHX_YES);
		int elseCenterLwWords = centerLwWords - mCenterLwWords;
		
		temp = temp.replaceAll("Kgx1",firGjjCount+"");
		temp = temp.replaceAll("Kgx2",secGjjCount+"");
		temp = temp.replaceAll("Kgx3",thiGjjCount+"");
		temp = temp.replaceAll("Kgx4",fouGjjCount+"");
		temp = temp.replaceAll("Kgx5",fivGjjCount+"");
		temp = temp.replaceAll("Kgx6",elseGjjCount+"");
		temp = temp.replaceAll("Kgh1",firGjjHjCount+"");
		temp = temp.replaceAll("Kgh2",secGjjHjCount+"");
		temp = temp.replaceAll("Kgh3",thiGjjHjCount+"");
		temp = temp.replaceAll("Kgh4",fouGjjHjCount+"");
		temp = temp.replaceAll("Kgh5",fivGjjHjCount+"");
		temp = temp.replaceAll("Kgh6",elseGjjHjCount+"");
		temp = temp.replaceAll("Ksx1",firSbjCount+"");
		temp = temp.replaceAll("Ksx2",secSbjCount+"");
		temp = temp.replaceAll("Ksx3",thiSbjCount+"");
		temp = temp.replaceAll("Ksx4",fouSbjCount+"");
		temp = temp.replaceAll("Ksx5",fivSbjCount+"");
		temp = temp.replaceAll("Ksx6",elseSbjCount+"");
		temp = temp.replaceAll("Ksh1",firSbjHjCount+"");
		temp = temp.replaceAll("Ksh2",secSbjHjCount+"");
		temp = temp.replaceAll("Ksh3",thiSbjHjCount+"");
		temp = temp.replaceAll("Ksh4",fouSbjHjCount+"");
		temp = temp.replaceAll("Ksh5",fivSbjHjCount+"");
		temp = temp.replaceAll("Ksh6",elseSbjHjCount+"");
		temp = temp.replaceAll("Ktx1",firStjCount+"");
		temp = temp.replaceAll("Ktx2",secStjCount+"");
		temp = temp.replaceAll("Ktx3",thiStjCount+"");
		temp = temp.replaceAll("Ktx4",fouStjCount+"");
		temp = temp.replaceAll("Ktx5",fivStjCount+"");
		temp = temp.replaceAll("Ktx6",elseStjCount+"");
		temp = temp.replaceAll("Kth1",firStjHjCount+"");
		temp = temp.replaceAll("Kth2",secStjHjCount+"");
		temp = temp.replaceAll("Kth3",thiStjHjCount+"");
		temp = temp.replaceAll("Kth4",fouStjHjCount+"");
		temp = temp.replaceAll("Kth5",fivStjHjCount+"");
		temp = temp.replaceAll("Kth6",elseStjHjCount+"");
		temp = temp.replaceAll("Kz1",firFmzlCount+"");
		temp = temp.replaceAll("Kz2",secFmzlCount+"");
		temp = temp.replaceAll("Kz3",thiFmzlCount+"");
		temp = temp.replaceAll("Kz4",fouFmzlCount+"");
		temp = temp.replaceAll("Kz5",fivFmzlCount+"");
		temp = temp.replaceAll("Kz6",elseFmzlCount+"");
		temp = temp.replaceAll("Jgx1",firGjjCount+"");
		temp = temp.replaceAll("Jgx2",secGjjCount+"");
		temp = temp.replaceAll("Jgx3",thiGjjCount+"");
		temp = temp.replaceAll("Jgx4",fouGjjCount+"");
		temp = temp.replaceAll("Jgx5",fivGjjCount+"");
		temp = temp.replaceAll("Jgx6",elseGjjCount+"");
		temp = temp.replaceAll("Jgh1",firGjjHjCount+"");
		temp = temp.replaceAll("Jgh2",secGjjHjCount+"");
		temp = temp.replaceAll("Jgh3",thiGjjHjCount+"");
		temp = temp.replaceAll("Jgh4",fouGjjHjCount+"");
		temp = temp.replaceAll("Jgh5",fivGjjHjCount+"");
		temp = temp.replaceAll("Jgh6",elseGjjHjCount+"");
		temp = temp.replaceAll("Jsx1",firSbjCount+"");
		temp = temp.replaceAll("Jsx2",secSbjCount+"");
		temp = temp.replaceAll("Jsx3",thiSbjCount+"");
		temp = temp.replaceAll("Jsx4",fouSbjCount+"");
		temp = temp.replaceAll("Jsx5",fivSbjCount+"");
		temp = temp.replaceAll("Jsx6",elseSbjCount+"");
		temp = temp.replaceAll("Jsh1",firSbjHjCount+"");
		temp = temp.replaceAll("Jsh2",secSbjHjCount+"");
		temp = temp.replaceAll("Jsh3",thiSbjHjCount+"");
		temp = temp.replaceAll("Jsh4",fouSbjHjCount+"");
		temp = temp.replaceAll("Jsh5",fivSbjHjCount+"");
		temp = temp.replaceAll("Jsh6",elseSbjHjCount+"");
		temp = temp.replaceAll("Jtx1",firStjCount+"");
		temp = temp.replaceAll("Jtx2",secStjCount+"");
		temp = temp.replaceAll("Jtx3",thiStjCount+"");
		temp = temp.replaceAll("Jtx4",fouStjCount+"");
		temp = temp.replaceAll("Jtx5",fivStjCount+"");
		temp = temp.replaceAll("Jtx6",elseStjCount+"");
		temp = temp.replaceAll("Jth1",firStjHjCount+"");
		temp = temp.replaceAll("Jth2",secStjHjCount+"");
		temp = temp.replaceAll("Jth3",thiStjHjCount+"");
		temp = temp.replaceAll("Jth4",fouStjHjCount+"");
		temp = temp.replaceAll("Jth5",fivStjHjCount+"");
		temp = temp.replaceAll("Jth6",elseStjHjCount+"");
		temp = temp.replaceAll("Xgx1",firGjjCount+"");
		temp = temp.replaceAll("Xgx2",secGjjCount+"");
		temp = temp.replaceAll("Xgx3",thiGjjCount+"");
		temp = temp.replaceAll("Xgx4",fouGjjCount+"");
		temp = temp.replaceAll("Xgx5",fivGjjCount+"");
		temp = temp.replaceAll("Xgx6",elseGjjCount+"");
		temp = temp.replaceAll("Xgh1",firGjjHjCount+"");
		temp = temp.replaceAll("Xgh2",secGjjHjCount+"");
		temp = temp.replaceAll("Xgh3",thiGjjHjCount+"");
		temp = temp.replaceAll("Xgh4",fouGjjHjCount+"");
		temp = temp.replaceAll("Xgh5",fivGjjHjCount+"");
		temp = temp.replaceAll("Xgh6",elseGjjHjCount+"");
		temp = temp.replaceAll("Xsx1",firSbjCount+"");
		temp = temp.replaceAll("Xsx2",secSbjCount+"");
		temp = temp.replaceAll("Xsx3",thiSbjCount+"");
		temp = temp.replaceAll("Xsx4",fouSbjCount+"");
		temp = temp.replaceAll("Xsx5",fivSbjCount+"");
		temp = temp.replaceAll("Xsx6",elseSbjCount+"");
		temp = temp.replaceAll("Xsh1",firSbjHjCount+"");
		temp = temp.replaceAll("Xsh2",secSbjHjCount+"");
		temp = temp.replaceAll("Xsh3",thiSbjHjCount+"");
		temp = temp.replaceAll("Xsh4",fouSbjHjCount+"");
		temp = temp.replaceAll("Xsh5",fivSbjHjCount+"");
		temp = temp.replaceAll("Xsh6",elseSbjHjCount+"");
		temp = temp.replaceAll("Xtx1",firStjCount+"");
		temp = temp.replaceAll("Xtx2",secStjCount+"");
		temp = temp.replaceAll("Xtx3",thiStjCount+"");
		temp = temp.replaceAll("Xtx4",fouStjCount+"");
		temp = temp.replaceAll("Xtx5",fivStjCount+"");
		temp = temp.replaceAll("Xtx6",elseStjCount+"");
		temp = temp.replaceAll("Xth1",firStjHjCount+"");
		temp = temp.replaceAll("Xth2",secStjHjCount+"");
		temp = temp.replaceAll("Xth3",thiStjHjCount+"");
		temp = temp.replaceAll("Xth4",fouStjHjCount+"");
		temp = temp.replaceAll("Xth5",fivStjHjCount+"");
		temp = temp.replaceAll("Xth6",elseStjHjCount+"");
		temp = temp.replaceAll("IndexQk1",fMCenterQkCount+"");
		temp = temp.replaceAll("IndexQk2",sMCenterQkCount+"");
		temp = temp.replaceAll("IndexQk3",tMCenterQkCount+"");
		temp = temp.replaceAll("IndexQk4",elseMCenterQkCount+"");
		temp = temp.replaceAll("IndexQkWords",mCenterLwWords+"");
		temp = temp.replaceAll("CenterQk1",fCenterQkCount+"");
		temp = temp.replaceAll("CenterQk2",sCenterQkCount+"");
		temp = temp.replaceAll("CenterQk3",tCenterQkCount+"");
		temp = temp.replaceAll("CenterQk4",elseCenterQkCount+"");
		temp = temp.replaceAll("CenterQkWords",elseCenterLwWords+"");
		temp = temp.replaceAll("SimpleQk1",fQkCount+"");
		temp = temp.replaceAll("SimpleQk2",sQkCount+"");
		temp = temp.replaceAll("SimpleQk3",tQkCount+"");
		temp = temp.replaceAll("SimpleQk4",elseQkCount+"");
		temp = temp.replaceAll("SimpleQkWords",lwWords+"");
		temp = temp.replaceAll("Jc1",zbjcCount);
		temp = temp.replaceAll("Jc2",fzbjcCount);
		temp = temp.replaceAll("Jc3",cbjcCount);
		temp = temp.replaceAll("Jc4","0");
		temp = temp.replaceAll("JcWords",jcWords+"");
		temp = temp.replaceAll("Lz1",firstzzCount+"");
		temp = temp.replaceAll("Lz2",secondzzCount+"");
		temp = temp.replaceAll("Lz3",thirdzzCount+"");
		temp = temp.replaceAll("Lz4","0");
		temp = temp.replaceAll("LzWords",zzWords+"");
		
		Filedownload.save(temp, "UTF-8", teacher.getSchDept(teacher.getKdId()).trim()+curYear+"年职称评审基本信息表.doc");
	}
	
	
	public String addLabelValue(String tempContent, String idLabel,String valueLabel,String labelValue){
		String befCon=null,aftCon=null;
		Integer startIndex = tempContent.indexOf(idLabel);
		befCon=tempContent.substring(0, startIndex);
		aftCon=tempContent.substring(startIndex, tempContent.length());
		return tempContent = befCon.trim()+" "+ valueLabel + labelValue +"\""+ " " +aftCon.trim();
	}
	
	/**
	 * 保存职称评审申请信息
	 */
	public void onClick$save()
	{
		if(ps==null)
		{
			ps = new GhJsps();
			ps.setJspsStatus(GhJsps.auditing);
		}
		if(assessment.getSelectedIndex()==0)
		{
			ps.setJspsAnnualAssessment(GhJsps.qualified);
		}else{
			ps.setJspsAnnualAssessment(GhJsps.unQualified);
		}
		if(computer.getSelectedIndex()==0){
			ps.setJspsComputer(GhJsps.qualified);
		}else{
			ps.setJspsComputer(GhJsps.unQualified);
		}
		if(foreign.getSelectedIndex()==0)
		{
			ps.setJspsForeign(GhJsps.qualified);
		}else{
			ps.setJspsForeign(GhJsps.unQualified);
		}
		ps.setJspsPosition(position.getValue().trim());
		ps.setJspsSubject(subject.getValue().trim());
		if(type.getSelectedIndex()==0)
		{
			ps.setJspsType(GhJsps.regular);
		}else{
			ps.setJspsType(GhJsps.unRegular);
		}
		ps.setJspsYear(curYear);
		ps.setKuId(user.getKuId());
		jspsService.saveOrUpdate(ps);
		try {
			Messagebox.show("保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		initWindow();
	}
	
	public void onClick$reset()
	{
		initWindow();
	}
	
	/**
	 * 根据用户的选择打开职称申请基本信息报表，从而打印报表
	 */
	public void onClick$report(){
		if(ps==null)
		{
			try {
				Messagebox.show("您尚未提出职称评审申请，请填写申请的基本信息并保存。","提示", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		Map reportMap = new HashMap();
		List skList = skService.findByKuid(user.getKuId());
		if(skList.size()>0){
			String skIds = "";
			for(int i=0;i<skList.size();i++)
			{
				GhSk gs = (GhSk)skList.get(i);
				Checkbox ch = (Checkbox)map.get("teacoucheck"+gs.getSkId());  
				if(ch.isChecked())
				{
					skIds = skIds+gs.getSkId()+",";
				}
			}
			if(skIds.length()>0)
				reportMap.put("skIds", skIds.substring(0, skIds.length()-1));
		}
		

		List<GhCg> cglist = cgService.findByKuid(user.getKuId(),GhCg.CG_KY,GhJsxm.HjKY);
		List<GhCg> teaCglist = cgService.findByKuid(user.getKuId(), GhCg.CG_JY,GhJsxm.HJJY);
		cglist.addAll(teaCglist);
		if(cglist.size()>0){
			String cgIds = "";
			for(int i=0;i<cglist.size();i++){
				GhCg cg = (GhCg)cglist.get(i);
				Checkbox ch = (Checkbox)map.get("cgcheck"+cg.getKyId()); 
				if(ch.isChecked()){
					cgIds = cgIds + cg.getKyId()+",";
				}
			}
			if(cgIds.length()>0)
				reportMap.put("cgIds", cgIds.substring(0, cgIds.length()-1));
		}

		List<GhXm> xmlist =xmService.findByKuidAndType(user.getKuId(), GhJsxm.KYXM);
//		List<GhXm> teaProlist = xmService.findByKuidAndType(user.getKuId(), GhJsxm.JYXM);
//		xmlist.addAll(teaProlist);
		if(xmlist.size()>0)
		{
			String xmIds = "";
			for(int i=0;i<xmlist.size();i++){
				GhXm xm = (GhXm)xmlist.get(i);
				Checkbox ch = (Checkbox)map.get("xmcheck"+xm.getKyId()); 
				if(ch.isChecked()){
					xmIds = xmIds + xm.getKyId()+",";
				}
			}
			if(xmIds.length()>0)
				reportMap.put("xmIds", xmIds.substring(0, xmIds.length()-1));
		}

		List<GhRjzz> softAuthlist = rjzzService.findByKuid(user.getKuId());	
		if(softAuthlist.size()>0){
			String rjzzIds = "";
			for(int i=0;i<softAuthlist.size();i++){
				GhRjzz soft = (GhRjzz)softAuthlist.get(i);
				Checkbox ch = (Checkbox)map.get("softcheck"+soft.getRjId()); 
			    if(ch.isChecked()){
			    	rjzzIds = rjzzIds +soft.getRjId()+",";
			    }
			}
			if(rjzzIds.length()>0)
				reportMap.put("rjzzIds", rjzzIds.substring(0, rjzzIds.length()-1));
		}

		List<GhQklw> magaPapelist = qklwService.findByKuidAndType(user.getKuId(), GhQklw.KYLW, GhJslw.KYQK);
		List<GhQklw> teamagaPapelist = qklwService.findByKuidAndType(user.getKuId(), GhQklw.JYLW, GhJslw.JYQK);
		magaPapelist.addAll(teamagaPapelist);
		if(magaPapelist.size()>0){
			String magaIds = "";
			for(int i=0;i<magaPapelist.size();i++){
				GhQklw maga = (GhQklw)magaPapelist.get(i);
				Checkbox ch = (Checkbox)map.get("magacheck"+maga.getLwId()); 
				 if(ch.isChecked()){
					 magaIds = magaIds +maga.getLwId()+",";
				 }
			}
			if(magaIds.length()>0)
				reportMap.put("magaIds", magaIds.substring(0, magaIds.length()-1));
		}

		List<GhHylw> mettPapList = hylwService.findByKuidAndType(user.getKuId(), GhHylw.KYLW, GhJslw.KYHY);	
		List<GhHylw> teamettPapList = hylwService.findByKuidAndType(user.getKuId(), GhHylw.JYLW, GhJslw.JYHY);
		mettPapList.addAll(teamettPapList);
		if(mettPapList.size()>0){
			String mepaIds = "";
			for(int i=0;i<mettPapList.size();i++){
				GhHylw mepa = (GhHylw)mettPapList.get(i);
				Checkbox ch = (Checkbox)map.get("mettcheck"+mepa.getLwId()); 
			    if(ch.isChecked()){	
			    	mepaIds = mepaIds+mepa.getLwId()+",";
			    }
			}
			if(mepaIds.length()>0)
				reportMap.put("mepaIds", mepaIds.substring(0, mepaIds.length()-1));
		}

		List<GhZz> pubtealist = zzService.findByKuidAndType(user.getKuId(),GhZz.JC,GhJszz.JC);
		if(pubtealist.size()>0){
			String zzIds = "";
			for(int i=0;i<pubtealist.size();i++){
				GhZz zz= (GhZz)pubtealist.get(i);
				Checkbox ch = (Checkbox)map.get("pubtealabel"+zz.getZzId()); 
			    if(ch.isChecked()){
			    	zzIds = zzIds+zz.getZzId()+",";
			    }
			}
			if(zzIds.length()>0)
				reportMap.put("zzIds", zzIds.substring(0, zzIds.length()-1));
		}
		Sessions.getCurrent().setAttribute("reportMap", reportMap);
		Executions.getCurrent().sendRedirect("/com/uniwin/asm/personal/ui/data/report.do?action=indicator&kuid="+user.getKuId(),"_blank");
	}
}
