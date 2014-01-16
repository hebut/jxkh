package com.uniwin.asm.personal.ui.data;
import java.io.File;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.write.WriteException;

import org.iti.gh.common.util.DateUtil;
import org.iti.gh.common.util.KyglControlUtil;
import org.iti.gh.entity.GhCg;
import org.iti.gh.entity.GhFmzl;
import org.iti.gh.entity.GhHylw;
import org.iti.gh.entity.GhJslw;
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
import org.iti.gh.service.PxService;
import org.iti.gh.service.QklwService;
import org.iti.gh.service.RjzzService;
import org.iti.gh.service.SkService;
import org.iti.gh.service.XmService;
import org.iti.gh.service.ZzService;
import org.iti.jxkh.entity.Jxkh_Honour;
import org.iti.jxkh.service.RychService;
import org.iti.xypt.entity.Teacher;
import org.iti.xypt.entity.Title;
import org.iti.xypt.service.TeacherService;
import org.iti.xypt.service.XyptTitleService;
import org.zkoss.zhtml.Filedownload;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Window;

import com.uniwin.common.util.ConvertUtil;
import com.uniwin.common.util.FileUtil;
import com.uniwin.framework.entity.WkTUser;

public class IndicatorWindow111 extends Window implements AfterCompose{
	private static final long serialVersionUID = -6066299046822270593L;
	WkTUser user=(WkTUser)Executions.getCurrent().getArg().get("user");	
	//数据访问接口
	private TeacherService teacherService;	
	private XmService xmService;
	private CgService cgService;
	private HylwService hylwService;
	private QklwService qklwService;
	private RjzzService rjzzService;
	private FmzlService fmzlService;
	private ZzService zzService;
	private SkService skService;
	private RychService rychService;
	private XyptTitleService xypttitleService;
	private PxService pxService;
	private JslwService jslwService;
	
	//实体
	private Teacher teacher;
	
	//组件
	private Rows proRows,fruitRows,mettingPaperRows,magaPaperRows,softAuthRows,patentRows;
	private Rows teaProRows,teaFruitRows,teamettPaperRows,teamagaPaperRows,pubTeaRows,teaCourceRows;
	private Checkbox allCheck;
	private Checkbox kuName,kuUsedname,kuSex,kuNation,nativeplace,kuEducational,img,kuXuewei,kuPolitical,facdegree,hacdegree,fkuSchool,fgradutime,fhighmajor;
	private Checkbox kuSchool,highgradutime,highmajor,address,kuhomePhone,kuworkPhone,kuPhone,kuEmail,msn,qq;
	
	private Checkbox proName,proSource,proNum,teaproName,teaproSource,teaproNum,fruitName,fruitTime,fruitClass,HjName,teafruitName,teafruitTime,teafruitClass,teaHjName;
	private Checkbox mettPaperName,mettPaperNaS,mettTime,mettPlace,teamettPaperName,teamettPaperNaS,teamettTime,teamettPlace;
	private Checkbox magaPaperName,magaPaperKw,magaTime,magaPages,teamagaPaperName,teamagaPaperKw,teamagaTime,teamagaPages;
	private Checkbox softAuthName,softPubTime,softDjNum,softDjTime,patentName,patentTime,patentNum;
	private Checkbox pubTeaName,pubTeaUnit,pubTeaTime,teaCourseName,teaCourseUnit,teaCourseTime;
	private Row proRow1,proRow2,teaProRow1,teaProRow2,softAuthRow1,softAuthRow2,patentRow1,patentRow2,pubTeaRow1,pubTeaRow2,teaCourseRow1,teaCourseRow2;
	private Row fruitRow1,fruitRow2,teaFrRow1,teaFrRow2,mettPaperRow1,mettPaperRow2,teamettPaperRow1,teamettPaperRow2,magaPaperRow1,magaPaperRow2,teamagaPaperRow1,teamagaPaperRow2;
	
	private Button preView,report;	
	private Map map= new HashMap();
	
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		if(user==null){
			user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		}
		teacher = teacherService.findBykuid(user.getKuId());
		Map map = new HashMap();
		List<GhXm> xmlist =xmService.findByKuidAndType(user.getKuId(), GhJsxm.KYXM);		
		map.put("xmlist", xmlist);
		report.setHref("/com/uniwin/asm/personal/ui/data/report.do?action=indicator&kuid="+user.getKuId()+"&xmlist = "+xmlist);
		report.setTarget("_blank");

		initWindow();
	}
	
	public void  onClick$preView() throws SuspendNotAllowedException, InterruptedException{		
		//读取订制好的个人主页,并写入文件进行访问
		String filePath = ConvertUtil.getPath("/personalHomePage");  
		String tempContent = FileUtil.readUTF8(filePath+user.getKuLid().trim()+"\\"+"index.zul");
	 
		String savePath = ConvertUtil.getPath("/admin/personal/data/teacherregister/customHomePage") ;
		FileUtil.writeFile(savePath,tempContent,"/preview.zul");
		
		Window cgWin = (Window) Executions.createComponents("/admin/personal/data/teacherregister/customHomePage/preview.zul", null, null);	
		cgWin.doModal();
		cgWin.setHeight("600px");
		cgWin.setWidth("700px");
		cgWin.setClosable(true);
		
	}
	/**
	* <li>功能描述：初始化页面。
	 * @param null
	 * @return null
	 * @author bobo
	 */
	public void initWindow(){		
		//科研项目
		List<GhXm> xmlist =xmService.findByKuidAndType(user.getKuId(), GhJsxm.KYXM);		
		int size= xmlist.size();
		int lastCols=size%4;
		int count =size/4  + lastCols>0?1:0;
		if(xmlist.size()>0){
			boolean flag=true; 
			if(size<=4){
				flag =false;
			}
			for(int i=0;i<=count;i++){
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
		
		//教研项目
		List<GhXm> teaProlist = xmService.findByKuidAndType(user.getKuId(), GhJsxm.JYXM);		
		int teaProsize= teaProlist.size();
		int teaProlastCols=teaProsize%4;
		int teaProcount =teaProsize/4  + teaProlastCols>0?1:0;
		if(teaProsize>0){
			boolean flag=true; 
			if(teaProsize<=4){
				flag =false;
			}
			for(int i=0;i<=teaProcount;i++){
				Row proRow = new Row();
				proRow.setId("teaProRowId"+i);
				proRow.setParent(teaProRows); 
			    for(int a=0;a<4;a++){				
			    	GhXm xm = (GhXm)teaProlist.get(i*4+a);
			    	Checkbox ch = new Checkbox();
					ch.setId("teaProcheck"+xm.getKyId());
					ch.setChecked(true);
					Label lab = new Label();
					lab.setId("teaProlabel"+xm.getKyId());
					lab.setValue(xm.getKyMc().trim());					
					map.put(ch.getId(), ch);
					ch.setParent(proRow);
					lab.setParent(proRow);					
					if((i*4+a)==teaProlist.size()-1){
						break;
					}
			    }
			    if(!flag){
			    	break;
			    }
			}
		}else{
			teaProRow1.setVisible(false);teaProRow2.setVisible(false);
		}
		
		//科研获奖成果
		List<GhCg> cglist = cgService.findByKuid(user.getKuId(),GhCg.CG_KY,GhJsxm.HjKY);
		int cgsize= cglist.size();
		int cglastCols=cgsize%4;
		int cgcount =cgsize/4  + cglastCols>0?1:0;
		if(cglist.size()>0){
			boolean flag=true; 
			if(cgsize<=4){
				flag =false;
			}
			for(int i=0;i<=cgcount;i++){
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
		
		//教研获奖成果
		List<GhCg> teaCglist = cgService.findByKuid(user.getKuId(), GhCg.CG_JY,GhJsxm.HJJY);
		int teacgsize= teaCglist.size();
		int teacglastCols=teacgsize%4;
		int teacgcount =teacgsize/4  + teacglastCols>0?1:0;
		if(teacgsize>0){
			boolean flag=true; 
			if(teacgsize<=4){
				flag =false;
			}
			for(int i=0;i<=teacgcount;i++){
				Row proRow = new Row();
				proRow.setId("teafruitRowId"+i);
				proRow.setParent(teaFruitRows); 
			    for(int a=0;a<4;a++){				
			    	GhCg cg = (GhCg)teaCglist.get(i*4+a);
			    	Checkbox ch = new Checkbox();
					ch.setId("teacgcheck"+cg.getKyId());
					ch.setChecked(true);
					Label lab = new Label();
					lab.setId("teacglabel"+cg.getKyId());
					lab.setValue(cg.getKyHjmc().trim());					
					map.put(ch.getId(), ch);
					ch.setParent(proRow);
					lab.setParent(proRow);
					if((i*4+a)==teacgsize-1){
						break;
					}
			    }
			    if(!flag){
			    	break;
			    }
			}
		}else{
			teaFrRow1.setVisible(false);teaFrRow2.setVisible(false);
		}
		
		//科研会议论文
		List<GhHylw> mettPapList = hylwService.findByKuidAndType(user.getKuId(), GhHylw.KYLW, GhJslw.KYHY);		
		int mettsize= mettPapList.size();
		int mettlastCols=mettsize%4;
		int mettcount =mettsize/4  + mettlastCols>0?1:0;
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
		
		//教研会议论文
		List<GhHylw> teamettPapList = hylwService.findByKuidAndType(user.getKuId(), GhHylw.JYLW, GhJslw.JYHY);		
		int teamettsize= teamettPapList.size();
		int teamettlastCols=teamettsize%4;
		int teamettcount =teamettsize/4  + teamettlastCols>0?1:0;
		if(teamettsize>0){
			boolean flag=true; 
			if(teamettsize<=4){
				flag =false;
			}
			for(int i=0;i<=teamettcount;i++){
				Row proRow = new Row();
				proRow.setId("teamettPapRowId"+i);
				proRow.setParent(teamettPaperRows); 
			    for(int a=0;a<4;a++){				
			    	GhHylw mepa = (GhHylw)teamettPapList.get(i*4+a);
			    	Checkbox ch = new Checkbox();
					ch.setId("teamettcheck"+mepa.getLwId());
					ch.setChecked(true);
					Label lab = new Label();
					lab.setId("teamettlabel"+mepa.getLwId());
					lab.setValue(mepa.getLwMc().trim());					
					map.put(ch.getId(), ch);
					ch.setParent(proRow);
					lab.setParent(proRow);
					if((i*4+a)==teamettsize-1){
						break;
					}
			    }
			    if(!flag){
			    	break;
			    }
			}
		}else{
			teamettPaperRow1.setVisible(false);teamettPaperRow2.setVisible(false);
		}
		
		//科研期刊论文
		List<GhQklw> magaPapelist = qklwService.findByKuidAndType(user.getKuId(), GhQklw.KYLW, GhJslw.KYQK);	
		int magasize= magaPapelist.size();
		int magalastCols=magasize%4;
		int magacount =magasize/4  + magalastCols>0?1:0;
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
		
		//教研期刊论文
		List<GhQklw> teamagaPapelist = qklwService.findByKuidAndType(user.getKuId(), GhQklw.JYLW, GhJslw.JYQK);
		int teamagasize= teamagaPapelist.size();
		int teamagalastCols=teamagasize%4;
		int teamagacount =teamagasize/4  + teamagalastCols>0?1:0;
		if(teamagasize>0){
			boolean flag=true; 
			if(teamagasize<=4){
				flag =false;
			}
			for(int i=0;i<=teamagacount;i++){
				Row proRow = new Row();
				proRow.setId("jyqkRowId"+i);
				proRow.setParent(teamagaPaperRows); 
			    for(int a=0;a<4;a++){	
			    	GhQklw maga = (GhQklw)teamagaPapelist.get(i*4+a);
			    	Checkbox ch = new Checkbox();
					ch.setId("teamagacheck"+maga.getLwId());
					ch.setChecked(true);
					Label lab = new Label();
					lab.setId("teamagalabel"+maga.getLwId());
					lab.setValue(maga.getLwMc().trim());					
					map.put(ch.getId(), ch);
					ch.setParent(proRow);
					lab.setParent(proRow);
					if((i*4+a)==teamagasize-1){
						break;
					}
			    }
			    if(!flag){
			    	break;
			    }
			}
		}else{
			teamagaPaperRow1.setVisible(false);teamagaPaperRow2.setVisible(false);
		}
		
		//软件著作权
		List<GhRjzz> softAuthlist = rjzzService.findByKuid(user.getKuId());	
		int softsize= softAuthlist.size();
		int softlastCols=softsize%4;
		int softcount =softsize/4  + softlastCols>0?1:0;
		if(softsize>0){
			boolean flag=true; 
			if(softsize<=4){
				flag =false;
			}
			for(int i=0;i<=softcount;i++){
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
		
		//发明专利
		List<GhFmzl> patentlist = fmzlService.findByKuid(user.getKuId());
		int patentsize= patentlist.size();
		int patentlastCols=patentsize%4;
		int patentcount =patentsize/4  + patentlastCols>0?1:0;
		if(patentsize>0){
			boolean flag=true; 
			if(patentsize<=4){
				flag =false;
			}
			for(int i=0;i<=patentcount;i++){
				Row proRow = new Row();
				proRow.setId("patentRowId"+i);
				proRow.setParent(patentRows); 
			    for(int a=0;a<4;a++){	
			    	GhFmzl pa = (GhFmzl)patentlist.get(i*4+a);
			    	Checkbox ch = new Checkbox();
					ch.setId("patentcheck"+pa.getFmId());
					ch.setChecked(true);
					Label lab = new Label();
					lab.setId("patentlabel"+pa.getFmId());
					lab.setValue(pa.getFmMc().trim());					
					map.put(ch.getId(), ch);
					ch.setParent(proRow);
					lab.setParent(proRow);
					if((i*4+a)==patentsize-1){
						break;
					}
			    }
			    if(!flag){
			    	break;
			    }
			}
		}else{
			patentRow1.setVisible(false);patentRow2.setVisible(false);
		}
		
		//出版教材
		List<GhZz> pubtealist = zzService.findByKuidAndType(user.getKuId(),GhZz.JC,GhJszz.JC);
		int pubteasize= pubtealist.size();
		int pubtealastCols=pubteasize%4;
		int pubteacount =pubteasize/4  + pubtealastCols>0?1:0;
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
		
		//授课情况
		List<GhSk> teaCourelist = skService.findByKuid(user.getKuId());
		int teasize= teaCourelist.size();
		int tealastCols=teasize%4;
		int teacount =teasize/4  + tealastCols>0?1:0;
		if(teasize>0){
			boolean flag=true; 
			if(teasize<=4){
				flag =false;
			}
			for(int i=0;i<=teacount;i++){
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
	}
	/**
	* <li>功能描述：选择或不选择全部属性。
	 * @param null
	 * @return null
	 * @author bobo
	 */
	public void onCheck$allCheck(){
		//科研项目
		List<GhXm> xmlist =xmService.findByKuidAndType(user.getKuId(), GhJsxm.KYXM);
		//教研项目
		List<GhXm> teaProlist = xmService.findByKuidAndType(user.getKuId(), GhJsxm.JYXM);
		//科研获奖成果
		List<GhCg> cglist = cgService.findByKuid(user.getKuId(),GhCg.CG_KY,GhJsxm.HjKY);
		//教研获奖成果
		List<GhCg> teaCglist = cgService.findByKuid(user.getKuId(), GhCg.CG_JY,GhJsxm.HJJY);
		//科研会议论文
		List<GhHylw> mettPapList = hylwService.findByKuidAndType(user.getKuId(), GhHylw.KYLW, GhJslw.KYHY);	
		//教研会议论文
		List<GhHylw> teamettPapList = hylwService.findByKuidAndType(user.getKuId(), GhHylw.JYLW, GhJslw.JYHY);		
		//科研期刊论文
		List<GhQklw> magaPapelist = qklwService.findByKuidAndType(user.getKuId(), GhQklw.KYLW, GhJslw.KYQK);
		//教研期刊论文
		List<GhQklw> teamagaPapelist = qklwService.findByKuidAndType(user.getKuId(), GhQklw.JYLW, GhJslw.JYQK);
		//软件著作权
		List<GhRjzz> softAuthlist = rjzzService.findByKuid(user.getKuId());
		//发明专利
		List<GhFmzl> patentlist = fmzlService.findByKuid(user.getKuId());
		//出版教材
		List<GhZz> pubtealist = zzService.findByKuidAndType(user.getKuId(),GhZz.JC,GhJszz.JC);
		//授课情况
		List<GhSk> teaCourelist = skService.findByKuid(user.getKuId());
		
		if(allCheck.isChecked()){		
			for(int i=0;i<xmlist.size();i++){
				GhXm xm = (GhXm)xmlist.get(i);
				Checkbox ch = (Checkbox)map.get("xmcheck"+xm.getKyId()); 
			    ch.setChecked(true);
			}	
			for(int i=0;i<teaProlist.size();i++){
				GhXm xm = (GhXm)teaProlist.get(i);
				Checkbox ch = (Checkbox)map.get("teaProcheck"+xm.getKyId()); 
			    ch.setChecked(true);
			}				
			for(int i=0;i<cglist.size();i++){
				GhCg cg = (GhCg)cglist.get(i);
				Checkbox ch = (Checkbox)map.get("cgcheck"+cg.getKyId()); 
			    ch.setChecked(true);
			}
			for(int i=0;i<teaCglist.size();i++){
				GhCg teacg = (GhCg)teaCglist.get(i);
				Checkbox ch = (Checkbox)map.get("teacgcheck"+teacg.getKyId()); 
			    ch.setChecked(true);
			}
			for(int i=0;i<mettPapList.size();i++){
				GhHylw mp = (GhHylw)mettPapList.get(i);
				Checkbox ch = (Checkbox)map.get("mettcheck"+mp.getLwId()); 
			    ch.setChecked(true);
			}
			for(int i=0;i<teamettPapList.size();i++){
				GhHylw teamp = (GhHylw)teamettPapList.get(i);
				Checkbox ch = (Checkbox)map.get("teamettcheck"+teamp.getLwId()); 
			    ch.setChecked(true);
			}
			for(int i=0;i<magaPapelist.size();i++){
				GhQklw mp = (GhQklw)magaPapelist.get(i);
				Checkbox ch = (Checkbox)map.get("magacheck"+mp.getLwId()); 
			    ch.setChecked(true);
			}
			for(int i=0;i<teamagaPapelist.size();i++){
				GhQklw mp = (GhQklw)teamagaPapelist.get(i);
				Checkbox ch = (Checkbox)map.get("teamagacheck"+mp.getLwId()); 
			    ch.setChecked(true);
			}
			for(int i=0;i<softAuthlist.size();i++){
				GhRjzz soft = (GhRjzz)softAuthlist.get(i);
				Checkbox ch = (Checkbox)map.get("softcheck"+soft.getRjId()); 
			    ch.setChecked(true);
			}
			for(int i=0;i<patentlist.size();i++){
				GhFmzl pa = (GhFmzl)patentlist.get(i);
				Checkbox ch = (Checkbox)map.get("patentcheck"+pa.getFmId()); 
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
			kuName.setChecked(true);kuUsedname.setChecked(true);kuSex.setChecked(true);kuNation.setChecked(true);
			nativeplace.setChecked(true);kuEducational.setChecked(true);img.setChecked(true);kuXuewei.setChecked(true);
			facdegree.setChecked(true);hacdegree.setChecked(true);fkuSchool.setChecked(true);fgradutime.setChecked(true);fhighmajor.setChecked(true);
			kuSchool.setChecked(true);highgradutime.setChecked(true);highmajor.setChecked(true);
			address.setChecked(true);kuhomePhone.setChecked(true);kuworkPhone.setChecked(true);
			kuPhone.setChecked(true);kuEmail.setChecked(true);msn.setChecked(true);qq.setChecked(true);
		}else{			
			for(int i=0;i<xmlist.size();i++){
				GhXm xm = (GhXm)xmlist.get(i);
				Checkbox ch = (Checkbox)map.get("xmcheck"+xm.getKyId()); 
			    ch.setChecked(false);
			}
			for(int i=0;i<teaProlist.size();i++){
				GhXm xm = (GhXm)teaProlist.get(i);
				Checkbox ch = (Checkbox)map.get("teaProcheck"+xm.getKyId()); 
			    ch.setChecked(false);
			}	
			for(int i=0;i<cglist.size();i++){
				GhCg cg = (GhCg)cglist.get(i);
				Checkbox ch = (Checkbox)map.get("cgcheck"+cg.getKyId()); 
			    ch.setChecked(false);
			}
			for(int i=0;i<teaCglist.size();i++){
				GhCg teacg = (GhCg)teaCglist.get(i);
				Checkbox ch = (Checkbox)map.get("teacgcheck"+teacg.getKyId()); 
			    ch.setChecked(false);
			}
			for(int i=0;i<mettPapList.size();i++){
				GhHylw mp = (GhHylw)mettPapList.get(i);
				Checkbox ch = (Checkbox)map.get("mettcheck"+mp.getLwId()); 
			    ch.setChecked(false);
			}
			for(int i=0;i<teamettPapList.size();i++){
				GhHylw teamp = (GhHylw)teamettPapList.get(i);
				Checkbox ch = (Checkbox)map.get("teamettcheck"+teamp.getLwId()); 
			    ch.setChecked(true);
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
			for(int i=0;i<patentlist.size();i++){
				GhFmzl pa = (GhFmzl)patentlist.get(i);
				Checkbox ch = (Checkbox)map.get("patentcheck"+pa.getFmId()); 
			    ch.setChecked(false);
			}
			for(int i=0;i<teamagaPapelist.size();i++){
				GhQklw mp = (GhQklw)teamagaPapelist.get(i);
				Checkbox ch = (Checkbox)map.get("teamagacheck"+mp.getLwId()); 
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
			kuName.setChecked(false);kuUsedname.setChecked(false); kuSex.setChecked(false);kuNation.setChecked(false);
			nativeplace.setChecked(false);kuEducational.setChecked(false);img.setChecked(false);kuXuewei.setChecked(false);
			facdegree.setChecked(false);hacdegree.setChecked(false);fkuSchool.setChecked(false);fgradutime.setChecked(false);fhighmajor.setChecked(false);
			kuSchool.setChecked(false);highgradutime.setChecked(false);highmajor.setChecked(false);
			address.setChecked(false);kuhomePhone.setChecked(false);kuworkPhone.setChecked(false);
			kuPhone.setChecked(false);kuEmail.setChecked(false);msn.setChecked(false);qq.setChecked(false);
		}
	}
	
	/**
	* <li>功能描述：将选择的中期报告属性，导出到.xml文档中。
	 * @param null
	 * @return null
	 * @author bobo
	 * @throws InterruptedException 
	 */
	public void onClick$buildHomePage() throws WriteException, InterruptedException{
		String filePath = KyglControlUtil.getPath("/personalHomePageTemp") ;  
		String tempContent = FileUtil.readUTF8(filePath+"\\"+"copyIndex.zul");//1、读取模板copyIndex.zul页面	
		
		if(kuName.isChecked()){
			String kuName="";
			if (user.getKuName() == null || user.getKuName().equals("")) {
				kuName=" ";
			} else {
				kuName = user.getKuName().trim();
			}
			tempContent = addLabelValue(tempContent, "id=\"kuName\"","value=\"",kuName);
		}
		
		if(kuUsedname.isChecked()){
			String kuUserdName="";
			if (user.getKuUsedname()== null ||user.getKuUsedname().equals("")) {
				kuUserdName=" ";
			} else {
				kuUserdName = user.getKuUsedname().trim();
			}
			tempContent = addLabelValue(tempContent, "id=\"kuUsedname\"","value=\"",kuUserdName);
		}
		
		if(kuSex.isChecked()){
			String sex=null;
			if(user.getKuSex().trim().equalsIgnoreCase("1")){
				sex="男";
			}else if(user.getKuSex().trim().equalsIgnoreCase("2")){
				sex="女";
			}
			tempContent = addLabelValue(tempContent, "id=\"kuSex\"","value=\"",sex.trim());
		}
		if(kuNation.isChecked()){
			String kuNat = null;
			String[] nation = {" ","汉族", "蒙古族", "回族", "藏族", "维吾尔组", "苗族",
					"彝族", "壮族", "布依族", "朝鲜族", "满族", "侗族", "瑶族", "白族", "土家族", "哈尼族",
					"哈萨克族", "傣族", "黎族", "僳僳族", "佤族", "畲族", "高山族", "拉祜族", "水族",
					"东乡族", "纳西族", "景颇族", "柯尔克孜族", "土族", "达斡尔族", "仫佬族", "羌族", "布朗族",
					"撒拉族", "毛难族", "仡佬族", "锡伯族", "阿昌族", "塔吉克族", "普米族", "怒族",
					"乌孜别克族", "俄罗斯族", "鄂温克族", "德昂族", "保安族", "裕固族", "京族", "塔塔尔族",
					"独龙族", "鄂伦春族", "赫哲族", "门巴族", "基诺族", "珞巴族" };
			
			if (user.getKuNation() == null || user.getKuNation() == ""||user.getKuNation().trim().equalsIgnoreCase("0")) {
				kuNat=" ";
			} else {
				kuNat = nation[Integer.valueOf(user.getKuNation())];
			}
			tempContent = addLabelValue(tempContent, "id=\"kuNation\"","value=\"",kuNat.trim());
		}
		
		if(nativeplace.isChecked()){
			String kuNativePlace="";
			if (user.getKuNativeplace()== null ||user.getKuNativeplace().equals("")) {
				kuNativePlace=" ";
			} else {
				kuNativePlace = user.getKuNativeplace().trim();
			}
			tempContent = addLabelValue(tempContent, "id=\"nativeplace\"","value=\"",kuNativePlace);
		}
		
		if(kuEducational.isChecked()){
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
			tempContent = addLabelValue(tempContent, "id=\"kuEducational\"","value=\"",edu.trim());
		}
		if(img.isChecked()){
			String path=null;	
			if(user.getKuPath()==null||user.getKuPath().equals("")){
				
			}else{
				path= Executions.getCurrent().getDesktop().getWebApp().getRealPath(user.getKuPath());
				path=path.replace("\\", "/");
				tempContent = addLabelValue(tempContent, "id=\"img\"","src=\"",path.trim());
			}			
		}
		if(kuXuewei.isChecked()){
			String[] xuewei = { " ", "名誉博士", "博士", "硕士", "双硕士", "学士", "无" };
			String xw=null;
			if (user.getKuEducational() == null || user.getKuEducational() == ""||user.getKuXuewei().trim().equalsIgnoreCase("0")) {
				xw=" ";
			} else {
				xw = xuewei[Integer.valueOf(user.getKuXuewei())];
			}
			tempContent = addLabelValue(tempContent, "id=\"kuXuewei\"","value=\"",xw.trim());
		}
		if(kuPolitical.isChecked()){
			String[] political = { " ", "中国共产党党员", "中国共产党预备党员", "中国共产主义青年团团员",
					"中国国民党革命委员会会员", "中国民主同盟盟员", "中国民主建国会会员", "中国民主促进会会员",
					"中国农工民主党党员", "中国致公党党员", "九三学社社员", "台湾民主自治同盟盟员", "无党派民主人士", "群众" };
			String poli =null;
			if (user.getKuPolitical() == null || user.getKuPolitical() == ""||user.getKuPolitical().trim().equalsIgnoreCase("0")) {
				poli=" ";
			} else {
				poli = political[Integer.valueOf(user.getKuPolitical())];
			}
			tempContent = addLabelValue(tempContent, "id=\"kuPolitical\"","value=\"",poli.trim());
		}
		if(facdegree.isChecked()){
			String[] xuewei = { " ", "名誉博士", "博士", "硕士", "双硕士", "学士", "无" };
			String fxw=null;
			if (teacher.getThFirdeg()==null||teacher.getThFirdeg().intValue()==0) {
				fxw=" ";
			} else {
				fxw = xuewei[teacher.getThFirdeg().intValue()];
			}
			tempContent = addLabelValue(tempContent, "id=\"facdegree\"","value=\"",fxw.trim());
		}
		if(fkuSchool.isChecked()){
			String kschoo=null;
			if(teacher.getThFirschool()==null||teacher.getThFirschool().trim().equals("")){
				kschoo="";
			}else{
				kschoo=teacher.getThFirschool();
			}
			tempContent = addLabelValue(tempContent, "id=\"fkuSchool\"","value=\"",kschoo.trim());
		}
		if(fgradutime.isChecked()){
			String fgtime="";
			if(teacher.getThFirgratime()==null||teacher.getThFirgratime().length()==0){	
				fgtime = "";
			}else{
				fgtime =DateUtil.getDateString(teacher.getThFirgratime());
			}			
			tempContent = addLabelValue(tempContent, "id=\"fgradutime\"","value=\"",fgtime.trim());
		}
		if(fhighmajor.isChecked()){
			String fhighm=null;
			if(teacher.getThFirsesub()==null||teacher.getThFirsesub().trim().equals(" ")){
				fhighm="";
			}else{
				fhighm=teacher.getThFirsesub();
			}		
			tempContent = addLabelValue(tempContent, "id=\"fhighmajor\"","value=\"",fhighm.trim());
		}
		if(hacdegree.isChecked()){
			String[] xuewei = { " ", "名誉博士", "博士", "硕士", "双硕士", "学士", "无" };
			String hxw=null;
			if (user.getKuEducational() == null || user.getKuEducational() == ""||user.getKuXuewei().trim().equalsIgnoreCase("0")) {
				hxw=" ";
			} else {
				hxw = xuewei[Integer.valueOf(user.getKuXuewei())];
			}
			tempContent = addLabelValue(tempContent, "id=\"hacdegree\"","value=\"",hxw.trim());
		}
		if(kuSchool.isChecked()){
			String supchoo=null;
			if(teacher.getThSupschool()==null||teacher.getThSupschool().trim().equals("")){
				supchoo="";
			}else{
				supchoo=teacher.getThSupschool();
			}
			tempContent = addLabelValue(tempContent, "id=\"kuSchool\"","value=\"",supchoo.trim());
		}
		if(highgradutime.isChecked()){
			String hgtime=null;
			if(teacher.getThSupgratime()==null||teacher.getThSupgratime().length()==0){	
				hgtime = "";
			}else{
				hgtime =DateUtil.getDateString(teacher.getThSupgratime());
			}			
			tempContent = addLabelValue(tempContent, "id=\"highgradutime\"","value=\"",hgtime.trim());
		}
		if(highmajor.isChecked()){
			String highm=null;
			if(teacher.getThSupsub()==null||teacher.getThSupsub().trim().equals(" ")){
				highm="";
			}else{
				highm=teacher.getThSupsub();
			}		
			tempContent = addLabelValue(tempContent, "id=\"highmajor\"","value=\"",highm.trim());
		}
		if(address.isChecked()){
			String adr=null;
			if(user.getKuHomeaddress()==null||user.getKuHomeaddress().trim().equals("")){
				adr="";
			}else{
				adr = user.getKuHomeaddress();
			}
			tempContent = addLabelValue(tempContent, "id=\"address\"","value=\"",adr.trim());
		}
		if(kuhomePhone.isChecked()){
			String pho=null;
			if(user.getKuHometel()==null||user.getKuHometel().trim().equals("")){
				pho="";
			}else{
				pho = user.getKuHometel();
			}
			tempContent = addLabelValue(tempContent, "id=\"kuhomePhone\"","value=\"",pho.trim());
		}
		
		if(kuworkPhone.isChecked()){
			String tel=null;
			if(user.getKuWorktel()==null||user.getKuWorktel().trim().equals("")){
				tel="";
			}else{
				tel = user.getKuWorktel();
			}
			tempContent = addLabelValue(tempContent, "id=\"kuworkPhone\"","value=\"",tel.trim());
		}
		
		if(kuPhone.isChecked()){
			String tel=null;
			if(user.getKuPhone()==null||user.getKuPhone().trim().equals("")){
				tel="";
			}else{
				tel = user.getKuPhone();
			}
			tempContent = addLabelValue(tempContent, "id=\"kuPhone\"","value=\"",tel.trim());
		}
		if(kuEmail.isChecked()){
			String ema=null;
			if(user.getKuEmail()==null||user.getKuEmail().trim().equals("")){
				ema="";
			}else{
				ema = user.getKuEmail();
			}
			tempContent = addLabelValue(tempContent, "id=\"kuEmail\"","value=\"",ema.trim());
		}
		if(msn.isChecked()){
			String ms=null;
			if(user.getKuMsn()==null||user.getKuMsn().trim().equals("")){
				ms="";
			}else{
				ms = user.getKuMsn();
			}
			tempContent = addLabelValue(tempContent, "id=\"msn\"","value=\"",ms.trim());
		}
		if(qq.isChecked()){
			String usqq=null;
			if(user.getKuQq()==null||user.getKuQq().trim().equals("")){
				usqq="";
			}else{
				usqq = user.getKuQq();
			}
			tempContent = addLabelValue(tempContent, "id=\"qq\"","value=\"",usqq.trim());
		}
		
		//科研项目
    	String strlistItem="";	
		List<GhXm> xmlist =xmService.findByKuidAndType(user.getKuId(), GhJsxm.KYXM);	
		if(xmlist.size()>0){
			String listboxstr ="<row spans=\"4\">"
						      + "<div align=\"left\">"
						       +"<label value=\"科研项目\" sclass=\"blue\" style=\"text-align:center\"/>"
						       +"</div>	"			
							   +"</row>"
							+"<row spans=\"4\" >"
						    +"	<listbox id=\"proListbox\"> ";
			for(int i=0;i<xmlist.size();i++){
				GhXm xm = (GhXm)xmlist.get(i);
				Checkbox ch = (Checkbox)map.get("xmcheck"+xm.getKyId()); 
			    if(ch.isChecked()){
			    	String cen="";
			    	if(proSource.isChecked()){
			    		if(!xm.getKyLy().equals("")||xm.getKyLy()!=null){
				        	cen = xm.getKyLy().trim()+"：";
				        }
			    	}
			    	if(proName.isChecked()){
			    		if(!xm.getKyMc().equals("")||xm.getKyMc()!=null){
			    			cen = cen +xm.getKyMc().trim()+",";
				        }
			    	}
			    	if(proNum.isChecked()){
			    		if(!xm.getKyNumber().equals("")||xm.getKyNumber()!=null){
			    			cen = cen +xm.getKyNumber().trim()+",";
				        }
			    	}
					strlistItem= strlistItem+"<listitem label=\""+(i+1)+"、"+cen+"\""+"/>"+"\r";
				}else{
			    	continue;
			    }
			}
			tempContent = tempContent+""+listboxstr.trim()+" "+ strlistItem.trim()+""+"</listbox></row>";
		}
		
		//教研项目
    	String teaprostrlistItem="";		
		List<GhXm> teaProlist = xmService.findByKuidAndType(user.getKuId(), GhJsxm.JYXM);	
		if(teaProlist.size()>0){
			String listboxstr ="<row spans=\"4\">"
			       + "<div align=\"left\">"
			       +"<label value=\"教研项目\" sclass=\"blue\" style=\"text-align:center\"/>"
			       +"</div>	"			
				   +"</row>"
				   +"<row spans=\"4\" >"
			       +"<listbox id=\"teaProListbox\"> ";
			for(int i=0;i<teaProlist.size();i++){
				GhXm xm = (GhXm)teaProlist.get(i);
				Checkbox ch = (Checkbox)map.get("teaProcheck"+xm.getKyId()); 
			    if(ch.isChecked()){
			    	String cen="";
			    	if(teaproSource.isChecked()){
			    		if(!xm.getKyLy().equals("")||xm.getKyLy()!=null){
				        	cen = xm.getKyLy().trim()+"：";
				        }
			    	}
			    	if(teaproName.isChecked()){
			    		if(!xm.getKyMc().equals("")||xm.getKyMc()!=null){
			    			cen = cen +xm.getKyMc().trim()+",";
				        }
			    	}
			    	if(teaproNum.isChecked()){
			    		if(!xm.getKyNumber().equals("")||xm.getKyNumber()!=null){
			    			cen = cen +xm.getKyNumber().trim()+",";
				        }
			    	}
			    	teaprostrlistItem= teaprostrlistItem+"<listitem label=\""+(i+1)+"、"+ cen +"\""+"/>"+"\r";
				}else{
			    	continue;
			    }
			}
			tempContent = tempContent+""+listboxstr.trim()+" "+ teaprostrlistItem.trim()+""+"</listbox></row>";
		}
		
		//获奖成果		
    	String cgstrlistItem="";		
		List<GhCg> cglist = cgService.findByKuid(user.getKuId(),GhCg.CG_KY,GhJsxm.HjKY);	
		if(cglist.size()>0){
			String listboxstr ="<row spans=\"4\">"
			       + "<div align=\"left\">"
			       +"<label value=\"科研获奖成果\" sclass=\"blue\" style=\"text-align:center\"/>"
			       +"</div>	"			
				   +"</row>"
				   +"<row spans=\"4\" >"
			       +"<listbox id=\"fruitListbox\"> ";
			for(int i=0;i<cglist.size();i++){
				GhCg cg = (GhCg)cglist.get(i);
				Checkbox ch = (Checkbox)map.get("cgcheck"+cg.getKyId()); 
			    if(ch.isChecked()){			    	
			    	String cen="";
			    	if(fruitName.isChecked()){
			    		cen = cen +cg.getKyMc().trim()+",";
			    	}
			    	if(fruitTime.isChecked()){
			    		if(!cg.getKySj().equals("")||cg.getKySj()!=null){
			    			cen = cen +cg.getKySj().trim()+",";
				        }
			    	}
			    	if(HjName.isChecked()){
			    		if(!cg.getKyHjmc().equals("")||cg.getKyHjmc()!=null){
			    			cen = cen +cg.getKyHjmc().trim()+",";
				        }
			    	}
			    	if(fruitClass.isChecked()){
			    		if(!cg.getKyDj().equals("")||cg.getKyDj()!=null){
				        	cen =cen +cg.getKyDj().trim()+"等奖";
				        }
			    	}			    	
					cgstrlistItem= cgstrlistItem+"<listitem label=\""+(i+1)+"、"+cen+"\""+"/>"+"\r";
				}else{
			    	continue;
			    }
			}		
			tempContent = tempContent+""+listboxstr.trim()+" "+ cgstrlistItem.trim()+""+"</listbox></row>";
		}
			
		//教研获奖成果		
    	String teacgstrlistItem="";		
		List<GhCg> teaCglist = cgService.findByKuid(user.getKuId(), GhCg.CG_JY,GhJsxm.HJJY);
		if(teaCglist.size()>0){
			String listboxstr ="<row spans=\"4\">"
			       + "<div align=\"left\">"
			       +"<label value=\"教研获奖成果\" sclass=\"blue\" style=\"text-align:center\"/>"
			       +"</div>	"			
				   +"</row>"
				   +"<row spans=\"4\" >"
			       +"<listbox id=\"teaFruitListbox\"> ";
			for(int i=0;i<teaCglist.size();i++){
				GhCg teacg = (GhCg)teaCglist.get(i);
				Checkbox ch = (Checkbox)map.get("teacglabel"+teacg.getKyId()); 
			    if(ch.isChecked()){
			    	String cen="";
			    	if(teafruitName.isChecked()){
			    		cen = cen +teacg.getKyMc().trim()+",";
			    	}
			    	if(teafruitTime.isChecked()){			    		
			    		if(!teacg.getKySj().equals("")||teacg.getKySj()!=null){
			    			cen = cen +teacg.getKySj().trim()+",";
				        }
			    	}
			    	if(teaHjName.isChecked()){			    		
			    		if(!teacg.getKyHjmc().equals("")||teacg.getKyHjmc()!=null){
			    			cen = cen +teacg.getKyHjmc().trim()+",";
				        }
			    	}
			    	if(teafruitClass.isChecked()){
			    		if(!teacg.getKyDj().equals("")||teacg.getKyDj()!=null){
				        	cen =cen +teacg.getKyDj().trim()+"等奖";
				        }
			    	}		
			    	teacgstrlistItem= teacgstrlistItem+"<listitem label=\""+(i+1)+"、"+cen+"\""+"/>"+"\r";
				}else{
			    	continue;
			    }
			}		
			tempContent = tempContent+""+listboxstr.trim()+" "+ teacgstrlistItem.trim()+""+"</listbox></row>";
		}		
		
		//科研会议论文		
    	String mpstrlistItem="";		
		List<GhHylw> mettPapList = hylwService.findByKuidAndType(user.getKuId(), GhHylw.KYLW, GhJslw.KYHY);		
		if(mettPapList.size()>0){
			String listboxstr ="<row spans=\"4\">"
			       + "<div align=\"left\">"
			       +"<label value=\"科研会议论文\" sclass=\"blue\" style=\"text-align:center\"/>"
			       +"</div>	"			
				   +"</row>"
				   +"<row spans=\"4\" >"
			       +"<listbox id=\"mettListbox\"> ";
			for(int i=0;i<mettPapList.size();i++){
				GhHylw mepa = (GhHylw)mettPapList.get(i);
				Checkbox ch = (Checkbox)map.get("mettcheck"+mepa.getLwId()); 
			    if(ch.isChecked()){			    	
			    	String cen="";
			    	if(mettPaperName.isChecked()){
			    		cen = cen +mepa.getLwMc().trim()+",";
			    	}
			    	if(mettPaperNaS.isChecked()){			    	
			    		if(!mepa.getLwKw().equals("")||mepa.getLwKw()!=null){
			    			cen = cen +mepa.getLwKw().trim()+",";
				        }
			    	}
			    	if(mettTime.isChecked()){
			    		if(mepa.getLwTime()!=null){
			    			cen = cen +mepa.getLwTime().trim()+",";
			    		}			    		
			    	}
			    	if(mettPlace.isChecked()){
			    		if(!mepa.getLwPlace().equals("")||mepa.getLwPlace()!=null){
				        	cen =cen + mepa.getLwPlace().trim();
				        }
			    	}			    	
			    	mpstrlistItem= mpstrlistItem+"<listitem label=\""+(i+1)+"、"+cen+"\""+"/>"+"\r";
				}else{
			    	continue;
			    }
			}
			tempContent = tempContent+""+listboxstr.trim()+" "+ mpstrlistItem.trim()+""+"</listbox></row>";
		}		
		
		//教研会议论文		
    	String teampstrlistItem="";		
		List<GhHylw> teamettPapList = hylwService.findByKuidAndType(user.getKuId(), GhHylw.JYLW, GhJslw.JYHY);	
		if(teamettPapList.size()>0){
			String listboxstr ="<row spans=\"4\">"
			       + "<div align=\"left\">"
			       +"<label value=\"教研会议论文\" sclass=\"blue\" style=\"text-align:center\"/>"
			       +"</div>	"			
				   +"</row>"
				   +"<row spans=\"4\" >"
			       +"<listbox id=\"teamettListbox\"> ";
			for(int i=0;i<teamettPapList.size();i++){
				GhHylw mepa = (GhHylw)teamettPapList.get(i);
				Checkbox ch = (Checkbox)map.get("teamettcheck"+mepa.getLwId()); 
			    if(ch.isChecked()){
			    	String cen="";
			    	if(teamettPaperName.isChecked()){
			    		cen = cen +mepa.getLwMc().trim()+",";
			    	}
			    	if(teamettPaperNaS.isChecked()){			    	
			    		if(!mepa.getLwKw().equals("")||mepa.getLwKw()!=null){
			    			cen = cen +mepa.getLwKw().trim()+",";
				        }
			    	}
			    	if(teamettTime.isChecked()){
			    		if(mepa.getLwTime()!=null){
			    			cen = cen +mepa.getLwTime().trim()+",";
			    		}			    		
			    	}
			    	if(teamettPlace.isChecked()){
			    		if(!mepa.getLwPlace().equals("")||mepa.getLwPlace()!=null){
				        	cen =cen + mepa.getLwPlace().trim();
				        }
			    	}
			    	teampstrlistItem= teampstrlistItem+"<listitem label=\""+(i+1)+"、"+cen+"\""+"/>"+"\r";
				}else{
			    	continue;
			    }
			}
			tempContent = tempContent+""+listboxstr.trim()+" "+ teampstrlistItem.trim()+""+"</listbox></row>";
		}
		
		//科研期刊论文		
    	String magastrlistItem="";		
		List<GhQklw> magaPapelist = qklwService.findByKuidAndType(user.getKuId(), GhQklw.KYLW, GhJslw.KYQK);
		if(magaPapelist.size()>0){
			String listboxstr ="<row spans=\"4\">"
			       + "<div align=\"left\">"
			       +"<label value=\"科研期刊论文\" sclass=\"blue\" style=\"text-align:center\"/>"
			       +"</div>	"			
				   +"</row>"
				   +"<row spans=\"4\" >"
			       +"<listbox id=\"magaListbox\"> ";
			for(int i=0;i<magaPapelist.size();i++){
				GhQklw maga = (GhQklw)magaPapelist.get(i);
				Checkbox ch = (Checkbox)map.get("magacheck"+maga.getLwId()); 
			    if(ch.isChecked()){
			    	String cen="";
			    	if(magaPaperName.isChecked()){
			    		if(maga.getLwMc()!=null)
			    			cen = cen +maga.getLwMc().trim()+",";
			    	}
			    	if(magaPaperKw.isChecked()){
			    		if(maga.getLwKw()!=null)
			    			cen = cen +maga.getLwKw().trim()+",";
			    	}
			    	if(magaTime.isChecked()){
			    		if(maga.getLwFbsj()!=null)
			    			cen = cen +maga.getLwFbsj().trim()+",";
			    	}
			    	if(magaPages.isChecked()){
			    		if(maga.getLwPages()!=null)
			    			cen = cen +maga.getLwPages().trim();
			    	}
			    	magastrlistItem= magastrlistItem+"<listitem label=\""+(i+1)+"、"+cen+"\""+"/>"+"\r";
				}else{
			    	continue;
			    }
			}
			tempContent = tempContent+""+listboxstr.trim()+" "+ magastrlistItem.trim()+""+"</listbox></row>";
		}		
		
		//教研期刊论文		
    	String teamagastrlistItem="";		
		List<GhQklw> teamagaPapelist = qklwService.findByKuidAndType(user.getKuId(), GhQklw.JYLW, GhJslw.JYQK);	
		if(teamagaPapelist.size()>0){
			String listboxstr ="<row spans=\"4\">"
			       + "<div align=\"left\">"
			       +"<label value=\"教研期刊论文\" sclass=\"blue\" style=\"text-align:center\"/>"
			       +"</div>	"			
				   +"</row>"
				   +"<row spans=\"4\" >"
			       +"<listbox id=\"teamagaListbox\"> ";
			for(int i=0;i<teamagaPapelist.size();i++){
				GhQklw maga = (GhQklw)teamagaPapelist.get(i);
				Checkbox ch = (Checkbox)map.get("teamagacheck"+maga.getLwId()); 
			    if(ch.isChecked()){
			    	String cen="";
			    	if(teamagaPaperName.isChecked()){
			    		if(maga.getLwMc()!=null)
			    			cen = cen +maga.getLwMc().trim()+",";
			    	}
			    	if(teamagaPaperKw.isChecked()){
			    		if(maga.getLwKw()!=null)
			    			cen = cen +maga.getLwKw().trim()+",";
			    	}
			    	if(teamagaTime.isChecked()){
			    		if(maga.getLwFbsj()!=null)
			    			cen = cen +maga.getLwFbsj().trim()+",";
			    	}
			    	if(teamagaPages.isChecked()){
			    		if(maga.getLwPages()!=null)
			    			cen = cen +maga.getLwPages().trim();
			    	}
			    	teamagastrlistItem= teamagastrlistItem+"<listitem label=\""+(i+1)+"、"+cen+"\""+"/>"+"\r";
				}else{
			    	continue;
			    }
			}
			tempContent = tempContent+""+listboxstr.trim()+" "+ teamagastrlistItem.trim()+""+"</listbox></row>";
		}		
		
		//软件著作权			
    	String softstrlistItem="";	
		List<GhRjzz> softAuthlist = rjzzService.findByKuid(user.getKuId());	
		if(softAuthlist.size()>0){
			String listboxstr ="<row spans=\"4\">"
			       + "<div align=\"left\">"
			       +"<label value=\"软件著作权\" sclass=\"blue\" style=\"text-align:center\"/>"
			       +"</div>	"			
				   +"</row>"
				   +"<row spans=\"4\" >"
			       +"<listbox id=\"softListbox\"> ";
			for(int i=0;i<softAuthlist.size();i++){
				GhRjzz soft = (GhRjzz)softAuthlist.get(i);
				Checkbox ch = (Checkbox)map.get("softcheck"+soft.getRjId()); 
			    if(ch.isChecked()){
			    	String cen="";
			    	if(softAuthName.isChecked()){
			    		if(soft.getRjName()!=null)
			    			cen = cen +soft.getRjName().trim()+",";
			    	}
			    	if(softPubTime.isChecked()){
			    		if(soft.getRjFirtime()!=null)
			    			cen = cen +soft.getRjFirtime().trim()+",";
			    	}
			    	if(softDjNum.isChecked()){
			    		if(soft.getRjRegisno()!=null)
			    			cen = cen +soft.getRjRegisno().trim()+",";
			    	}
			    	if(softDjTime.isChecked()){
			    		if(soft.getRjTime()!=null)
			    			cen = cen +soft.getRjTime().trim();
			    	}
			    	softstrlistItem= softstrlistItem+"<listitem label=\""+(i+1)+"、"+cen+"\""+"/>"+"\r";
				}else{
			    	continue;
			    }
			}
			tempContent = tempContent+""+listboxstr.trim()+" "+ softstrlistItem.trim()+""+"</listbox></row>";
		}		
		
		//发明专利		
    	String patentstrlistItem="";	
		List<GhFmzl> patentlist = fmzlService.findByKuid(user.getKuId());
		if(patentlist.size()>0){
			String listboxstr ="<row spans=\"4\">"
			       + "<div align=\"left\">"
			       +"<label value=\"发明专利\" sclass=\"blue\" style=\"text-align:center\"/>"
			       +"</div>	"			
				   +"</row>"
				   +"<row spans=\"4\" >"
			       +"<listbox id=\"pateListbox\"> ";
			for(int i=0;i<patentlist.size();i++){
				GhFmzl pa= (GhFmzl)patentlist.get(i);
				Checkbox ch = (Checkbox)map.get("patentcheck"+pa.getFmId()); 
			    if(ch.isChecked()){
			    	String cen="";
			    	if(patentName.isChecked()){
			    		if(pa.getFmMc()!=null)
			    			cen = cen +pa.getFmMc().trim()+",";
			    	}
			    	if(patentTime.isChecked()){
			    		if(pa.getFmSj()!=null)
			    			cen = cen +pa.getFmSj().trim()+",";
			    	}
			    	if(patentNum.isChecked()){
			    		if(pa.getFmSqh()!=null)
			    			cen = cen +pa.getFmSqh().trim();
			    	}
			    	patentstrlistItem= patentstrlistItem+"<listitem label=\""+(i+1)+"、"+cen+"\""+"/>"+"\r";
				}else{
			    	continue;
			    }
			}
			tempContent = tempContent+""+listboxstr.trim()+" "+ patentstrlistItem.trim()+""+"</listbox></row>";
		}
				
		//出版教材
    	String pubteastrlistItem="";		
		List<GhZz> pubtealist = zzService.findByKuidAndType(user.getKuId(),GhZz.JC,GhJszz.JC);
		if(pubtealist.size()>0){
			String listboxstr ="<row spans=\"4\">"
			       + "<div align=\"left\">"
			       +"<label value=\"出版教材\" sclass=\"blue\" style=\"text-align:center\"/>"
			       +"</div>	"			
				   +"</row>"
				   +"<row spans=\"4\" >"
			       +"<listbox id=\"pubteaListbox\"> ";
			for(int i=0;i<pubtealist.size();i++){
				GhZz zz= (GhZz)pubtealist.get(i);
				Checkbox ch = (Checkbox)map.get("pubtealabel"+zz.getZzId()); 
			    if(ch.isChecked()){
			    	String cen="";
			    	if(pubTeaName.isChecked()){
			    		if(zz.getZzMc()!=null)
			    			cen = cen +zz.getZzMc().trim()+",";
			    	}
			    	if(pubTeaUnit.isChecked()){
			    		if(zz.getZzKw()!=null)
			    			cen = cen +zz.getZzKw().trim()+",";
			    	}
			    	if(pubTeaTime.isChecked()){
			    		if(zz.getZzPublitime()!=null)
			    			cen = cen +zz.getZzPublitime().trim();
			    	}
			    	pubteastrlistItem= pubteastrlistItem+"<listitem label=\""+(i+1)+"、"+cen+"\""+"/>"+"\r";
				}else{
			    	continue;
			    }
			}
			tempContent = tempContent+""+listboxstr.trim()+" "+ pubteastrlistItem.trim()+""+"</listbox></row>";
		}
				
		//授课情况		
    	String teastrlistItem="";		
		List<GhSk> teaCourelist = skService.findByKuid(user.getKuId());
		if(teaCourelist.size()>0){
			String listboxstr ="<row spans=\"4\">"
			      + "<div align=\"left\">"
			       +"<label value=\"授课情况\" sclass=\"blue\" style=\"text-align:center\"/>"
			       +"</div>	"			
				   +"</row>"
				+"<row spans=\"4\" >"
			    +"	<listbox id=\"teacouListbox\"> ";
			for(int i=0;i<teaCourelist.size();i++){
				GhSk sk= (GhSk)teaCourelist.get(i);
				Checkbox ch = (Checkbox)map.get("teacoucheck"+sk.getSkId()); 
			    if(ch.isChecked()){
			    	String cen="";
			    	if(teaCourseName.isChecked()){
			    		if(sk.getSkMc()!=null)
			    			cen = cen +sk.getSkMc().trim()+",";
			    	}
			    	if(teaCourseUnit.isChecked()){
			    		if(sk.getSkGrade()!=null)
			    			cen = cen +sk.getSkGrade().trim()+",";
			    	}
			    	if(teaCourseTime.isChecked()){
			    		if(sk.getSkXs()!=null)
			    			cen = cen +sk.getSkXs().trim();
			    	}
			    	teastrlistItem= teastrlistItem+"<listitem label=\""+(i+1)+"、"+cen+"\""+"/>"+"\r";
				}else{
			    	continue;
			    }
			}
			tempContent = tempContent+""+listboxstr.trim()+" "+ teastrlistItem.trim()+""+"</listbox></row>";
		}		
		
		String last="</rows></grid></div></window></zk>";
		tempContent = tempContent.trim()+last;
			
		//生成个人主页,存放在 "/personalHomePage/教师号/index.zul"
		String savePath = KyglControlUtil.getPath("/personalHomePage") ; 
		savePath = savePath+user.getKuLid().trim(); 
	    File fDir = new File(savePath);	
	    if(!fDir.exists()){
			    if(!fDir.mkdir()){
			    	   System.out.println("无法创建存储目录！");      //如果文件夹不存在，用于创建该文件夹
				       return;
			    }			
	     }
		FileUtil.writeFile(savePath, tempContent,  "index.zul");
		Messagebox.show("您订制的个人页面成功生成！", "提示", Messagebox.OK,Messagebox.INFORMATION);
		preView.setVisible(true);
//		map.clear();
	}
	
	/**
	 * 根据定制内容和显示模板导出个人评审信息
	 */
	public void onClick$export()
	{
		String filePath = KyglControlUtil.getPath("/personalHomePageTemp") ;  
		//读取模板exportTemplate.xml	
		String temp = FileUtil.readUTF8(filePath+"\\"+"indicatorTemplate.xml");
		//文档标题
		GregorianCalendar calendar=new GregorianCalendar(); 
		int curYear = calendar.get(calendar.YEAR);//当前年份
		temp = temp.replaceAll("Title", "河北工业大学"+curYear+"年职称评审基本信息表");
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
		if(null==teacher.getXiDept(teacher.getKdId())||"".equals(teacher.getXiDept(teacher.getKdId())))
		{
			temp = temp.replaceAll("DeclareSubject","");
		}else{
			temp = temp.replaceAll("DeclareSubject",teacher.getXiDept(teacher.getKdId()));//待定
		}
		//晋升方式 
		temp = temp.replaceAll("Promotion", "正常");//待定
		//申报专业技术职务
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
		temp = temp.replaceAll("DeclareProfession",declareTitle);//待定
		
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

		StringBuilder teachingSB = new StringBuilder();
		List skList = skService.findByKuid(user.getKuId());
		for(int i=0;i<skList.size();i++)
		{
			GhSk gs = (GhSk)skList.get(i);
			String term = "";
			if(null!=gs.getSkYear()&&!"".equals(gs.getSkYear()))
			{
				term = gs.getSkYear();
			}
			teachingSB.append(termStart+term+termEnd);
			String course = "";
			if(null!=gs.getSkMc()&&!"".equals(gs.getSkMc()))
			{
				course = gs.getSkMc();
			}
			teachingSB.append(courseStart+course+"，"+courseClass);
			String work = 0+"";
			String grade = "";
			if(null!=gs.getThWork()&&!"".equals(gs.getThWork()))
			{
				work = gs.getThWork();
			}
			if(null!=gs.getSkGrade()&&!"".equals(gs.getSkGrade()))
			{
				grade = gs.getSkGrade();
			}
			teachingSB.append(grade+"，"+classHours+work+hoursEnd);
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
		//其他教学经历
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
//		String jycgString="";		
//		List<GhCg> teaCglist = cgService.findByKuid(user.getKuId(), GhCg.CG_JY,GhJsxm.HJJY);
//		if(teaCglist.size()>0){
//			for(int i=0;i<teaCglist.size();i++){
//				GhCg teacg = (GhCg)teaCglist.get(i);
//			    String cen="";
//			    cen = cen +teacg.getKyMc().trim()+"，";
//			    if(!teacg.getKySj().equals("")||teacg.getKySj()!=null){
//			    	cen = cen +teacg.getKySj().trim()+"，";
//			    }
//			    if(!teacg.getKyHjmc().equals("")||teacg.getKyHjmc()!=null){
//			    	cen = cen +teacg.getKyHjmc().trim()+"，";
//			    }
//			    if(!teacg.getKyDj().equals("")||teacg.getKyDj()!=null){
//				   	cen =cen +teacg.getKyDj().trim()+"等奖";
//			    }		
//			    jycgString = jycgString + otherWorkStart + (i+2)+ "、" + cen + otherWorkEnd;
//			}		
//			temp = temp.replaceAll(otherWorkload, jycgString);
//		}else{
//			temp = temp.replaceAll("OtherWorkload", "");
//		}
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
		if(cglist.size()>0){
			for(int i=0;i<cglist.size();i++){
				GhCg cg = (GhCg)cglist.get(i);
				Checkbox ch = (Checkbox)map.get("cgcheck"+cg.getKyId()); 
			    if(ch.isChecked()){			    	
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
				        	cen =cen +cg.getKyDj().trim();
				        }
			    	}			    	
			    	cgString= cgString + patentStart +"["+ (i+1)+ "] " + cen + patentEnd;
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
		if(xmlist.size()>0)
		{
			for(int i=0;i<xmlist.size();i++){
				GhXm xm = (GhXm)xmlist.get(i);
				Checkbox ch = (Checkbox)map.get("xmcheck"+xm.getKyId()); 
			    if(ch.isChecked()){
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
			    			cen = cen +xm.getKyNumber().trim()+"";
				        }
			    	}
			    	kyxmListString = kyxmListString + patentStart +"["+ (i+1)+ "] " + cen + patentEnd;
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
			for(int i=0;i<softAuthlist.size();i++){
				GhRjzz soft = (GhRjzz)softAuthlist.get(i);
				Checkbox ch = (Checkbox)map.get("softcheck"+soft.getRjId()); 
			    if(ch.isChecked()){
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
			    			cen = cen +soft.getRjTime().trim();
			    	}
			    	softString= softString + patentStart+"["+(i+1)+"] "+cen+patentEnd;
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
		String papers = "<w:p><w:pPr><w:spacing w:line=\"264\" w:line-rule=\"auto\" /><w:rPr><w:lang w:val=\"IT\" />" +
				"</w:rPr></w:pPr><w:r><w:rPr><w:rFonts w:hint=\"fareast\" /><w:lang w:val=\"IT\" />" +
				"</w:rPr><w:t>Papers</w:t></w:r></w:p>";
		String kyPageString="";		
		List<GhQklw> magaPapelist = qklwService.findByKuidAndType(user.getKuId(), GhQklw.KYLW, GhJslw.KYQK);
		if(magaPapelist.size()>0){
			for(int i=0;i<magaPapelist.size();i++){
				GhQklw maga = (GhQklw)magaPapelist.get(i);
			    	String cen="";
			    		if(maga.getLwMc()!=null)
			    			cen = cen +maga.getLwMc().trim()+"，";
			    		if(maga.getLwKw()!=null)
			    			cen = cen +maga.getLwKw().trim()+"，";
			    		if(maga.getLwFbsj()!=null)
			    			cen = cen +maga.getLwFbsj().trim()+"，";
			    		if(maga.getLwPages()!=null)
			    			cen = cen +maga.getLwPages().trim()+"，";
			    		GhJslw jslw = jslwService.findByKuidAndLwidAndType(user.getKuId(), maga.getLwId(), GhJslw.KYQK);
			    		if(jslw.getLwSelfs()!=null)
			    			cen = cen + "第" +jslw.getLwSelfs()+"作者";
			    		if(maga.getLwCenter() == (GhQklw.LWHX_YES.shortValue()))
			    			cen = cen +".核心期刊";
			    		if(null!=maga.getLwLocation()&&!"".equals(maga.getLwLocation()))
			    			cen = cen +"("+maga.getLwLocation()+")";
			    	kyPageString = kyPageString + materialStart+"["+(i+1)+"] " + cen + materialEnd;
			}
			temp = temp.replaceAll(papers, kyPageString);
		}else{
			temp = temp.replaceAll("Papers", "");
		}
		//教材
		String material = "<w:p><w:pPr><w:rPr><w:sz w:val=\"24\" /></w:rPr></w:pPr><w:r><w:rPr>" +
				"<w:sz w:val=\"24\" /></w:rPr><w:t>TeachingMaterial</w:t></w:r></w:p>";
		String pubString="";		
		List<GhZz> pubtealist = zzService.findByKuidAndType(user.getKuId(),GhZz.JC,GhJszz.JC);
		if(pubtealist.size()>0){
			for(int i=0;i<pubtealist.size();i++){
				GhZz zz= (GhZz)pubtealist.get(i);
				Checkbox ch = (Checkbox)map.get("pubtealabel"+zz.getZzId()); 
			    if(ch.isChecked()){
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
			    			cen = cen +zz.getZzPublitime().trim();
			    	}
			    	pubString = pubString +  materialStart + "["+(i+1)+"] "+cen+ materialEnd;
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
		temp = temp.replaceAll("AnnualAssessment","");//待定
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
		temp = temp.replaceAll("ForeignLanguageExamtion","合格");//待定
		//计算机考试(是否合格)
		temp = temp.replaceAll("ComputerExamtion","合格");//待定		
		Filedownload.save(temp, "UTF-8", user.getKuName().trim()+"职称评审基本信息表.doc");
	}
	
	
	public String addLabelValue(String tempContent, String idLabel,String valueLabel,String labelValue){
		String befCon=null,aftCon=null;
		Integer startIndex = tempContent.indexOf(idLabel);
		befCon=tempContent.substring(0, startIndex);
		aftCon=tempContent.substring(startIndex, tempContent.length());
		return tempContent = befCon.trim()+" "+ valueLabel + labelValue +"\""+ " " +aftCon.trim();
	}
}
