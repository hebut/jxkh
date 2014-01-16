package com.uniwin.asm.personal.ui.data;

import java.util.GregorianCalendar;
import java.util.List;

import org.iti.gh.common.util.ChineseUpperCaser;
import org.iti.gh.entity.GhCg;
import org.iti.gh.entity.GhHylw;
import org.iti.gh.entity.GhJslw;
import org.iti.gh.entity.GhJsps;
import org.iti.gh.entity.GhJsxm;
import org.iti.gh.entity.GhJszz;
import org.iti.gh.entity.GhQklw;
import org.iti.gh.entity.GhRjzz;
import org.iti.gh.entity.GhSk;
import org.iti.gh.entity.GhXm;
import org.iti.gh.entity.GhZz;
import org.iti.gh.service.CgService;
import org.iti.gh.service.HylwService;
import org.iti.gh.service.JslwService;
import org.iti.gh.service.JspsService;
import org.iti.gh.service.JsxmService;
import org.iti.gh.service.JszzService;
import org.iti.gh.service.QklwService;
import org.iti.gh.service.RjzzService;
import org.iti.gh.service.SkService;
import org.iti.gh.service.XmService;
import org.iti.gh.service.ZzService;
import org.iti.xypt.entity.Teacher;
import org.iti.xypt.entity.Title;
import org.iti.xypt.service.TeacherService;
import org.iti.xypt.service.XyptTitleService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class TitleAuditDetailsWindow extends Window implements AfterCompose{

	private static final long serialVersionUID = 6224571349788677491L;

	//数据访问接口
	private CgService cgService;
	private HylwService hylwService;
	private QklwService qklwService;
	private RjzzService rjzzService;
	private ZzService zzService;
	private SkService skService;
	private XyptTitleService xypttitleService;
	private TeacherService teacherService;
	private XmService xmService;
	private JspsService jspsService;
	private JszzService jszzService;
	private JslwService jslwService;
	private JsxmService jsxmService;
	
	//组件
	private Textbox subject,position,remarkTextbox;
	private Label year;
	private Listbox type,assessment,computer,foreign;
	private Radio notPassRadio,passRadio;
	
	private Listbox teaCourseListbox,fruitListbox,proListbox,softAuthListbox,magaPaperListbox,mettPaperListbox,pubTeaListbox;
	private TitleAuditWindow titleAuditWin;
	private Rows teaCourceRows,fruitRows,proRows,softAuthRows,magaPaperRows,mettingPaperRows,pubTeaRows;
	private Row teaCourseRow1,teaCourseRow2,fruitRow1,fruitRow2,proRow1,proRow2,softAuthRow1,softAuthRow2;
	private Row magaPaperRow1,magaPaperRow2,mettPaperRow1,mettPaperRow2,pubTeaRow1,pubTeaRow2;
	
	private int curYear = 0;
	private GhJsps jsps;
	
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		teaCourseListbox.setItemRenderer(new TeaCourseRenderer());
		fruitListbox.setItemRenderer(new FruitRenderer());
		proListbox.setItemRenderer(new ProRenderer());
		softAuthListbox.setItemRenderer(new SoftAuthRenderer());
		magaPaperListbox.setItemRenderer(new MagaPaperRenderer());
		mettPaperListbox.setItemRenderer(new MettPaperRenderer());
		pubTeaListbox.setItemRenderer(new PubTeaRenderer());
		GregorianCalendar calendar=new GregorianCalendar(); 
		curYear = calendar.get(calendar.YEAR);//当前年份
	}
	
	public void initWindow()
	{
		Teacher teacher = teacherService.findBykuid(jsps.getKuId());
		year.setValue(teacher.getSchDept(teacher.getKdId()).trim()+curYear+"年职称申请");
		List titleList = xypttitleService.findBytid(teacher.getThTitle());
		Title teacherTitle = (Title)titleList.get(0);
		List ptitleList = xypttitleService.findByPtid(teacherTitle.getPtiId());
		if(jsps!=null)
		{
			if(jsps.getJspsSubject()!=null)
			{
				subject.setValue(jsps.getJspsSubject().trim());
			}
			if(jsps.getJspsPosition()!=null)
			{
				position.setValue(jsps.getJspsPosition().trim());
			}
			if(null!=jsps.getJspsStatus()&&GhJsps.pass.equals(jsps.getJspsStatus()))
			{
				passRadio.setSelected(true);
			}
			if(null==jsps.getJspsAnnualAssessment())
			{
				assessment.setSelectedIndex(1);
			}else{
				if(GhJsps.unQualified.equals(jsps.getJspsAnnualAssessment().trim()))
					assessment.setSelectedIndex(1);
			}
				
			if(null==jsps.getJspsComputer())
			{
				computer.setSelectedIndex(1);
			}else{
				if(GhJsps.unQualified.equals(jsps.getJspsComputer().trim()))
					computer.setSelectedIndex(1);
			}
			if(null==jsps.getJspsForeign())
			{
				foreign.setSelectedIndex(1);
			}else{
				if(GhJsps.unQualified.equals(jsps.getJspsForeign().trim()))
					foreign.setSelectedIndex(1);
			}
			if(null==jsps.getJspsType())
			{
				type.setSelectedIndex(0);
			}else{
				if(GhJsps.unRegular.equals(jsps.getJspsType().trim()))
					type.setSelectedIndex(1);
			}
			if(null!=jsps.getJspsRemark()&&!"".equals(jsps.getJspsRemark()))
			{
				remarkTextbox.setValue(jsps.getJspsRemark().trim());
			}
		}
		
		initListbox();
		
	}
	
	public void initListbox(){
		//授课情况
		List<GhSk> teaCourelist = skService.findByKuid(jsps.getKuId());
		teaCourseListbox.setModel(new ListModelList(teaCourelist));
		//科研获奖成果
		List<GhCg> cglist = cgService.findByKuid(jsps.getKuId(),GhCg.CG_KY,GhJsxm.HjKY);
		List<GhCg> teaCglist = cgService.findByKuid(jsps.getKuId(), GhCg.CG_JY,GhJsxm.HJJY);
		cglist.addAll(teaCglist);
		fruitListbox.setModel(new ListModelList(cglist));
		//科研项目
		List<GhXm> xmlist =xmService.findByKuidAndType(jsps.getKuId(), GhJsxm.KYXM);	
		proListbox.setModel(new ListModelList(xmlist));
		//软件著作权
		List<GhRjzz> softAuthlist = rjzzService.findByKuid(jsps.getKuId());	
		softAuthListbox.setModel(new ListModelList(softAuthlist));
		//科研期刊论文
		List<GhQklw> magaPapelist = qklwService.findByKuidAndType(jsps.getKuId(), GhQklw.KYLW, GhJslw.KYQK);
		List<GhQklw> teamagaPapelist = qklwService.findByKuidAndType(jsps.getKuId(), GhQklw.JYLW, GhJslw.JYQK);
		magaPapelist.addAll(teamagaPapelist);
		magaPaperListbox.setModel(new ListModelList(magaPapelist));
		//科研会议论文
		List<GhHylw> mettPapList = hylwService.findByKuidAndType(jsps.getKuId(), GhHylw.KYLW, GhJslw.KYHY);	
		List<GhHylw> teamettPapList = hylwService.findByKuidAndType(jsps.getKuId(), GhHylw.JYLW, GhJslw.JYHY);
		mettPapList.addAll(teamettPapList);
		mettPaperListbox.setModel(new ListModelList(mettPapList));
		//出版教材
		List<GhZz> pubtealist = zzService.findByKuidAndType(jsps.getKuId(),GhZz.JC,GhJszz.JC);
		pubTeaListbox.setModel(new ListModelList(pubtealist));
	}
	
	public void onClick$submit(){
		if(notPassRadio.isChecked())
		{
			jsps.setJspsStatus(GhJsps.notPass);
		}else if(passRadio.isChecked()){
			jsps.setJspsStatus(GhJsps.pass);
		}
		jsps.setJspsRemark(remarkTextbox.getValue().trim());
		jspsService.update(jsps);
		try {
			Messagebox.show("提交成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.detach();
		titleAuditWin.initWindow();
	}
	
	public void onClick$back(){
		this.detach();
	}

	public class PubTeaRenderer implements ListitemRenderer {

		public void render(Listitem item, Object data) throws Exception {
			GhZz zz = (GhZz)data;
			item.setValue(zz);
			String cen = "";
    		if(zz.getZzMc()!=null)
	    			cen = cen +zz.getZzMc().trim()+"，";
	    		if(zz.getZzKw()!=null)
	    			cen = cen +zz.getZzKw().trim()+"，";
	    		if(zz.getZzPublitime()!=null)
	    			cen = cen +zz.getZzPublitime().trim()+"，";
	    		GhJszz jszz=jszzService.findByKuidAndLwidAndType(jsps.getKuId(), zz.getZzId(), GhJszz.JC);
	    		if(null!=jszz){
	    			cen = cen + jszz.getZzSelfs();
	    		}
			Listcell c0 = new Listcell(item.getIndex()+1+"、"+cen);
			item.appendChild(c0);
		}

	}

	public class MettPaperRenderer implements ListitemRenderer {

		public void render(Listitem item, Object data) throws Exception {
			GhHylw mepa = (GhHylw)data;
			item.setValue(mepa);
			String cen="";
    		cen = cen +mepa.getLwMc().trim()+"，";
    		if(!mepa.getLwKw().equals("")||mepa.getLwKw()!=null){
    			cen = cen +mepa.getLwKw().trim()+"，";
	        }
    		if(mepa.getLwTime()!=null){
    			cen = cen +mepa.getLwTime().trim()+"，";
    		}			    		
    		if(!mepa.getLwPlace().equals("")||mepa.getLwPlace()!=null){
	        	cen =cen + mepa.getLwPlace().trim()+"，";
	        }
    		GhJslw gj = jslwService.findByKuidAndLwidAndType(jsps.getKuId(), mepa.getLwId(), GhJslw.KYHY);
    		if(null!=gj&&gj.getLwSelfs()>0){
    			cen = cen + "第"+ new ChineseUpperCaser(gj.getLwSelfs())+"作者";
    		}
    		Listcell c0 = new Listcell(item.getIndex()+1+"、"+cen);
    		item.appendChild(c0);
		}

	}

	public class MagaPaperRenderer implements ListitemRenderer {

		public void render(Listitem item, Object data) throws Exception {
			GhQklw maga = (GhQklw)data;
			item.setValue(maga);
			String cen = "";
			if(maga.getLwMc()!=null)
	    		cen = cen +maga.getLwMc().trim()+"，";
			if(maga.getLwKw()!=null)
	    		cen = cen +maga.getLwKw().trim()+"，";
			if(maga.getLwFbsj()!=null)
	    		cen = cen +maga.getLwFbsj().trim()+"，";
			if(maga.getLwPages()!=null)
	    		cen = cen +maga.getLwPages().trim()+"，";
			GhJslw jslw = jslwService.findByKuidAndLwidAndType(jsps.getKuId(), maga.getLwId(), GhJslw.KYQK);
	    	if(jslw.getLwSelfs()!=null)
	    		cen = cen + "第" +new ChineseUpperCaser(jslw.getLwSelfs())+"作者";
	    	if(maga.getLwCenter() == (GhQklw.LWHX_YES.shortValue()))
				cen = cen +".核心期刊";
			if(null!=maga.getLwLocation()&&!"".equals(maga.getLwLocation()))
				cen = cen +"(，"+maga.getLwLocation()+")";
			Listcell c0 = new Listcell(item.getIndex()+1+"、"+cen);
    		item.appendChild(c0);
		}

	}

	public class SoftAuthRenderer implements ListitemRenderer {

		public void render(Listitem item, Object data) throws Exception {
			GhRjzz soft = (GhRjzz)data;
			item.setValue(soft);
			String cen="";
    		if(soft.getRjName()!=null)
    			cen = cen +soft.getRjName().trim()+"，";
    		if(soft.getRjFirtime()!=null)
    			cen = cen +soft.getRjFirtime().trim()+"，";
    		if(soft.getRjRegisno()!=null)
    			cen = cen +soft.getRjRegisno().trim()+"，";
    		if(soft.getRjTime()!=null)
    			cen = cen +soft.getRjTime().trim()+"，";
    		GhJslw jslw=jslwService.findByKuidAndLwidAndType(jsps.getKuId(), soft.getRjId(), GhJslw.RJZZ);
    		if(null!=jslw&&jslw.getLwSelfs()>0){
    			cen = cen + "第"+ new ChineseUpperCaser(jslw.getLwSelfs())+"参与者";
    		}
			Listcell c0 = new Listcell(item.getIndex()+1+"、"+cen);
    		item.appendChild(c0);
		}

	}

	public class ProRenderer implements ListitemRenderer {

		public void render(Listitem item, Object data) throws Exception {
			GhXm xm = (GhXm)data;
			item.setValue(xm);
			String cen="";	
    		if(!xm.getKyMc().equals("")||xm.getKyMc()!=null){
    			cen = cen +xm.getKyMc().trim()+"，";
	        }
    		if(!xm.getKyLy().equals("")||xm.getKyLy()!=null){
	        	cen = cen + xm.getKyLy().trim()+"，";
	        }
    		if(!xm.getKyNumber().equals("")||xm.getKyNumber()!=null){
    			cen = cen +xm.getKyNumber().trim()+"，";
	        }
    		GhJsxm jx=(GhJsxm)jsxmService.findByXmidAndKuidAndType(xm.getKyId(), jsps.getKuId(),GhJsxm.KYXM);
    		if(null!=jx&&jx.getKyGx()>0)
    		{
    			cen = cen + "第"+ new ChineseUpperCaser(jx.getKyGx())+"参与者";
    		}
			Listcell c0 = new Listcell(item.getIndex()+1+"、"+cen);
    		item.appendChild(c0);
		}

	}

	public class FruitRenderer implements ListitemRenderer {

		public void render(Listitem item, Object data) throws Exception {
			GhCg cg = (GhCg)data;
			item.setValue(cg);
			String cen="";
	    	if(!cg.getKyMc().equals("")||cg.getKyMc()!=null){
	    		cen = cen +cg.getKyMc().trim()+"，";
	    	}
    		if(!cg.getKySj().equals("")||cg.getKySj()!=null){
    			cen = cen +cg.getKySj().trim()+"，";
	        }
    		if(!cg.getKyHjmc().equals("")||cg.getKyHjmc()!=null){
    			cen = cen +cg.getKyHjmc().trim()+"，";
	        }
    		if(!cg.getKyDj().equals("")||cg.getKyDj()!=null){
	        	cen =cen +cg.getKyDj().trim()+"，";
	        }
    		GhJsxm jx=jsxmService.findByXmidAndKuidAndType(cg.getKyId(), jsps.getKuId(), GhJsxm.HjKY);
    		if(null!=jx&&jx.getKyGx()>0){
    			cen = cen + "第"+ new ChineseUpperCaser(jx.getKyGx())+"参与者";
    		}
			Listcell c0 = new Listcell(item.getIndex()+1+"、"+cen);
    		item.appendChild(c0);
		}

	}

	public class TeaCourseRenderer implements ListitemRenderer {

		public void render(Listitem item, Object data) throws Exception {
			GhSk gs = (GhSk)data;
			item.setValue(gs);
			StringBuilder teachingSB = new StringBuilder();
			String term = "";
			if(null!=gs.getSkYear()&&!"".equals(gs.getSkYear()))
			{
				term = gs.getSkYear()+"，";
			}
			teachingSB.append(term);
			//课程名称
			String course = "";
			if(null!=gs.getSkMc()&&!"".equals(gs.getSkMc()))
			{
				course = gs.getSkMc()+"，";
			}
			teachingSB.append(course);
			
			//课程班级
			String grade = "";
			if(null!=gs.getSkGrade()&&!"".equals(gs.getSkGrade()))
			{
				grade = gs.getSkGrade()+"，";
			}
			teachingSB.append(grade);
			
			//课程学时
			String work = 0+"";
			if(null!=gs.getThWork()&&!"".equals(gs.getThWork()))
			{
				work = gs.getThWork();
			}
			teachingSB.append("共" + work+"学时");
			Listcell c0 = new Listcell(item.getIndex()+1+"、"+teachingSB.toString());
    		item.appendChild(c0);
		}

	}

	public GhJsps getJsps() {
		return jsps;
	}

	public void setJsps(GhJsps jsps) {
		this.jsps = jsps;
	}

	public TitleAuditWindow getTitleAuditWin() {
		return titleAuditWin;
	}

	public void setTitleAuditWin(TitleAuditWindow titleAuditWin) {
		this.titleAuditWin = titleAuditWin;
	}
	
	
}
