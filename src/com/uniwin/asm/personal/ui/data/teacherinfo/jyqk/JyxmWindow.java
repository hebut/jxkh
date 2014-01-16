package com.uniwin.asm.personal.ui.data.teacherinfo.jyqk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.common.util.ExportExcel;
import org.iti.gh.entity.GhFile;
import org.iti.gh.entity.GhJslw;
import org.iti.gh.entity.GhJsxm;
import org.iti.gh.entity.GhXm;
import org.iti.gh.entity.GhXmzz;
import org.iti.gh.entity.GhYjfx;
import org.iti.gh.service.CgService;
import org.iti.gh.service.FmzlService;
import org.iti.gh.service.GhFileService;
import org.iti.gh.service.JlhzService;
import org.iti.gh.service.JsxmService;
import org.iti.gh.service.JxbgService;
import org.iti.gh.service.KyjhService;
import org.iti.gh.service.LwzlService;
import org.iti.gh.service.PkpyService;
import org.iti.gh.service.PxService;
import org.iti.gh.service.QkdcService;
import org.iti.gh.service.QtService;
import org.iti.gh.service.SkService;
import org.iti.gh.service.XmService;
import org.iti.gh.service.XmzzService;
import org.iti.gh.service.XshyService;
import org.iti.gh.service.YjfxService;
import org.iti.gh.service.ZsService;
import org.iti.projectmanage.science.member.service.ProjectMemberService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.uniwin.asm.personal.ui.data.teacherinfo.CkshyjWindow;
import com.uniwin.asm.personal.ui.data.teacherinfo.JyxmYwcWindow;
import com.uniwin.framework.common.fileuploadex.UploadFJ;
import com.uniwin.framework.entity.WkTUser;

public class JyxmWindow extends BaseWindow {

	//已完成的教研项目
	Button button8;
	Listbox listbox8;
	ProjectMemberService projectmemberService;
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
	PkpyService pkpyService;
	YjfxService yjfxService;
	GhFileService ghfileService;
	WkTUser user;
	JsxmService jsxmService;
	@Override
	public void initShow() {
		initWindow();
		listbox8.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhXm xm = (GhXm) arg1;

				//序号
				Listcell c1 = new Listcell(arg0.getIndex()+1+"");

			
				/*//项目编号
				Listcell c2 = new Listcell(xm.getKyNumber());*/
				//名称
				Listcell c2 = new Listcell();
				if(xm.getKyMc()!=null){
					InnerButton ib = new InnerButton(xm.getKyMc());
					ib.addEventListener(Events.ON_CLICK, new EventListener(){

						public void onEvent(Event arg0) throws Exception {
							final JyxmYwcWindow cgWin = (JyxmYwcWindow) Executions.createComponents
							("/admin/personal/data/teacherinfo/jxqk/jyxmywc.zul", null, null);
							cgWin.setXm(xm);
							cgWin.setKuid(user.getKuId());
							cgWin.doHighlighted();
							cgWin.initWindow();
							cgWin.addEventListener(Events.ON_CHANGE, new EventListener(){

								public void onEvent(Event arg0)
								throws Exception {
								    initWindow();
									cgWin.detach();
								}

							});
						}
					});
					c2.appendChild(ib);
				}
				
				
				//项目来源
				Listcell c3 = new Listcell();
				if(xm.getKyLy()!=null){
					c3.setLabel(xm.getKyLy());
				}
				//项目负责人
				Listcell c4 = new Listcell();
				if(xm.getKyProman()!=null){
					c4.setLabel(xm.getKyProman()+"");
				}
//				//开始时间
//				Listcell c5 = new Listcell();
//				if(xm.getKyKssj()!=null){
//					c5.setLabel(xm.getKyKssj());
//				}
//
//				//结束时间
//				Listcell c6 = new Listcell();
//				if(xm.getKyJssj()!=null){
//					c6.setLabel(xm.getKyJssj());
//				}

				
                
				//本人贡献
				Listcell c7=new Listcell();
				List manager= projectmemberService.findByKyIdAndKuId(xm.getKyId(),user.getKuId());
				if(manager.size()!=0)
				{
					GhJsxm man=(GhJsxm) manager.get(0);
					c7.setLabel(man.getKyGx().toString());
				}
				else
				{
					c7.setLabel("1");
				}

				//功能
				Listcell c8 =  new Listcell();
				InnerButton gn = new InnerButton();

				gn.setLabel("删除");
				gn.addEventListener(Events.ON_CLICK, new EventListener(){

					public void onEvent(Event arg0) throws Exception {
						if(xm.getKuId().intValue()!=user.getKuId().intValue()){
							Messagebox.show("您不是此项目提交人，故不能删除！","提示", Messagebox.OK,Messagebox.EXCLAMATION);
						}else{
						if(Messagebox.show("确定删除吗?", "提示", Messagebox.OK|Messagebox.CANCEL,
								Messagebox.QUESTION)==1){
							//删除附件
							List list=ghfileService.findByFxmIdandFType(xm.getKyId(), 10);
							if(list.size()>0){
								//存在附件
								//List[] fjList=new List[list.size()];
								for(int i=0;i<list.size();i++){
									UploadFJ ufj=new UploadFJ(false);
									ufj.ReadFJ(getDesktop(), (GhFile) list.get(i));
									ufj.DeleteFJ();
								}
							}
							//
							List xmzzlist =	xmzzService.findByKyidAndKuidAndType(xm.getKyId(), user.getKuId(),GhJsxm.JYXM.shortValue());
							if(xmzzlist != null && xmzzlist.size() !=0){
								for(int i = 0;i < xmzzlist.size();i++){
									GhXmzz xmzz = (GhXmzz) xmzzlist.get(i);
									xmzzService.delete(xmzz);
								}
							}
							//同一个项目可能有多个人报奖
							List cglist1 = cgService.findByKyXmidAndKuId(xm.getKyId(), user.getKuId());
							if(cglist1 != null && cglist1.size() != 0){
								cgService.delete(cglist1.get(0));
							}
							List cglist =jsxmService.findByXmidAndtype(xm.getKyId(), GhJsxm.JYXM);
							if(cglist != null && cglist.size() != 0){
								for(int i=0;i<cglist.size();i++){
									GhJsxm jsxm=(GhJsxm)cglist.get(i);
									jsxmService.delete(jsxm);
								}
							}
							xmService.delete(xm);
						    initWindow();
						}
						}
					}
				});
				c8.appendChild(gn);
				Listcell c9=new Listcell();
				InnerButton ib = new InnerButton();
				if(xm.getAuditState()==null){
					c9.setLabel("未审核");
				}else if(xm.getAuditState()!=null&&xm.getAuditState().shortValue()==GhXm.AUDIT_NO){
					ib.setLabel("未通过");
					c9.appendChild(ib);
					ib.addEventListener(Events.ON_CLICK, new EventListener(){

						public void onEvent(Event arg0) throws Exception {
						 Map arg=new HashMap();
						 arg.put("type", 10);
						 arg.put("id",xm.getKyId());
						 CkshyjWindow cw=(CkshyjWindow)Executions.createComponents("/admin/personal/data/teacherinfo/jxqk/ckpsyj.zul", null, arg);
						 cw.initWindow();
						 cw.doHighlighted();
						}
						
					});
				}else if(xm.getAuditState()!=null&&xm.getAuditState().shortValue()==GhXm.AUDIT_YES){
					ib.setLabel("通过");
					c9.appendChild(ib);
					ib.addEventListener(Events.ON_CLICK, new EventListener(){

						public void onEvent(Event arg0) throws Exception {
						 Map arg=new HashMap();
						 arg.put("type", 10);
						 arg.put("id",xm.getKyId());
						 CkshyjWindow cw=(CkshyjWindow)Executions.createComponents("/admin/personal/data/teacherinfo/jxqk/ckpsyj.zul", null, arg);
						 cw.initWindow();
						 cw.doHighlighted();
						}
						
					});
				}
				
				arg0.appendChild(c1); arg0.appendChild(c2); arg0.appendChild(c3);
				arg0.appendChild(c4);
//				arg0.appendChild(c5); arg0.appendChild(c6);
				arg0.appendChild(c7); arg0.appendChild(c8); arg0.appendChild(c9);
				
			}
		});


	}

	@Override
	public void initWindow() {
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		//教研项目
		List list8 = xmService.findByKuidAndType(user.getKuId(), GhJsxm.JYXM);
		listbox8.setModel(new ListModelList(list8));

	}
	public void onClick$button8(){
		final JyxmYwcWindow cgWin = (JyxmYwcWindow) Executions.createComponents
		("/admin/personal/data/teacherinfo/jxqk/jyxmywc.zul", null, null);
		cgWin.setKuid(user.getKuId());
		cgWin.initWindow();
		cgWin.doHighlighted();
		cgWin.addEventListener(Events.ON_CHANGE, new EventListener(){

			public void onEvent(Event arg0)
			throws Exception {
			    initWindow();
				cgWin.detach();
			}

		});
	}
	public void onClick$button8out()  throws IOException, WriteException{
		//教研项目
		List list8 = xmService.findByKuidAndType(user.getKuId(), GhJsxm.JYXM);
		if(list8 == null || list8.size() == 0){
			 try {
	 				Messagebox.show("空数据，导出错误！", "错误", Messagebox.OK,Messagebox.INFORMATION);
	 			} catch (InterruptedException e) {
	 				e.printStackTrace();
	 			}
	 			  return;
		}else {
			String fileName = "教研项目.xls";
			String Title = "教研项目";
			WritableWorkbook workbook;
			List titlelist=new ArrayList();
			titlelist.add("序号");
			titlelist.add("项目编号");
			titlelist.add("项目名称");
			titlelist.add("项目来源");
			titlelist.add("开始时间");
			titlelist.add("结束时间");
			titlelist.add("项目经费（万元）");
			titlelist.add("本人贡献");
			titlelist.add("研究方向");
			titlelist.add("学科分类");
			titlelist.add("项目负责人");
			titlelist.add("项目组人员");
			titlelist.add("申报人员");
			titlelist.add("项目类别");
			titlelist.add("项目级别");
			titlelist.add("项目进展");
			titlelist.add("项目指标");
			titlelist.add("项目鉴定（验收）时间");
			titlelist.add("鉴定水平");
//			titlelist.add("是否获奖");
			titlelist.add("本人承担任务或作用");
			String c[][]=new String[list8.size()][titlelist.size()];
			//从SQL中读数据
			for(int j=0;j<list8.size();j++){
				GhXm xm  =(GhXm)list8.get(j);
			    c[j][0]=j+1+"";
			    c[j][1]=xm.getKyNumber();
				c[j][2]=xm.getKyMc();
			    c[j][3]=xm.getKyLy();
				c[j][4]=xm.getKyKssj();
				c[j][5]=xm.getKyJssj();
				c[j][6]=xm.getKyJf().toString();
				GhJsxm jsxm=jsxmService.findByXmidAndKuidAndType(xm.getKyId(),user.getKuId(),GhJsxm.JYXM);
				if(jsxm!=null&&jsxm.getKyGx()!=null){
					c[j][7]=jsxm.getKyGx()+"";
				}else{
					c[j][7]="";
				}
				if(jsxm!=null&&jsxm.getYjId()!=null){
					Long yjfx=jsxm.getYjId();
					if(0<Integer.parseInt(yjfx.toString())&&Integer.parseInt(yjfx.toString())<7)
					{
					GhYjfx fx=jsxmService.getYjfx(yjfx);
					c[j][8]=fx.getGyName();
					}
					else
					{
						c[j][8]="";
					}
				}else{
					c[j][8]="";
				}
				
				//学科分类
				if(xm.getKySubjetype() == null ||xm.getKySubjetype() == ""){
					c[j][9]="";
				}else
					if(xm.getKySubjetype().equalsIgnoreCase( "1")){
					c[j][9]="自然科学";
				}else
					if(xm.getKySubjetype().equalsIgnoreCase( "2")){
					c[j][9]="社会科学";
				}
				
				c[j][10]=xm.getKyProman();
				c[j][11]=xm.getKyProstaffs();
				c[j][12]=xm.getKyRegister();
				//项目类别
				if(xm.getKyClass() == null || xm.getKyClass().trim().equalsIgnoreCase("-1")){
					c[j][13]="";
				}else{
					if(xm.getKyClass().trim().equalsIgnoreCase( "1")){
					c[j][13]="横向";
				}else {
					if(xm.getKyClass().trim().equalsIgnoreCase( "2")){
					c[j][13]="纵向";
					}
				}
				}
				//项目级别
				if(xm.getKyScale() == null || xm.getKyScale().trim() == "-1"){
					c[j][14]="";
				}else{ if(xm.getKyScale().trim().equalsIgnoreCase( "1")){
					c[j][14]="国际合作项目";
				}else if(xm.getKyClass().trim().equalsIgnoreCase( "2")){
					c[j][14]="国家级项目";
				}else if(xm.getKyClass().trim().equalsIgnoreCase( "3")){
					c[j][14]="部（委、省）级项目";
				}else if(xm.getKyClass().trim().equalsIgnoreCase( "4")){
					c[j][14]="市厅级项目";
				}else if(xm.getKyClass().trim().equalsIgnoreCase( "5")){
					c[j][14]="委托及开发项目";
				}else if(xm.getKyClass().trim().equalsIgnoreCase( "6")){
					c[j][14]="学校基金项目";
				}else {
					c[j][14]="";
				}}
				//项目进展
				if(xm.getKyProgress() == null ||xm.getKyProgress().trim() ==""){
					c[j][15]="";
				}else if(xm.getKyProgress().trim().equalsIgnoreCase( "1")){
					c[j][15]="申请中";
				}else if(xm.getKyProgress().trim().equalsIgnoreCase( "2")){
					c[j][15]="已完成";
				}else if(xm.getKyProgress().trim().equalsIgnoreCase( "3")){
					c[j][15]="在研";
				}
				
				c[j][16]=xm.getKyTarget();
				c[j][17]=xm.getKyIdenttime();
				c[j][18]=xm.getKyLevel();
				
//				//项目获奖
//				
//
//				if(xm.getKyPrize() == null || xm.getKyPrize().trim() == "" ||xm.getKyPrize().trim().equalsIgnoreCase( "1")){
//				if(xm.getKyPrize() == null || xm.getKyPrize() == "" ||xm.getKyPrize().equalsIgnoreCase( "1")){
//
//						c[j][19]="否";
//
//				}else if(xm.getKyPrize().trim().equalsIgnoreCase( "2")){
//				}else if(xm.getKyPrize().equalsIgnoreCase( "2")){
//
//					c[j][19]="是";
//				}
				//本人承担任务或作用
				if(jsxm==null||jsxm!=null&&(jsxm.getKyCdrw() == null || jsxm.getKyCdrw().trim() == "")){
					c[j][19]="";
				}else if(jsxm.getKyCdrw().trim().equalsIgnoreCase( "1")){
					c[j][19]="主持";
				}else if(jsxm.getKyCdrw().trim().equalsIgnoreCase( "2")){
					c[j][19]="参加";
				}else if(jsxm.getKyCdrw().trim().equalsIgnoreCase( "3")){
					c[j][19]="独立";
				}
				
			}
	         ExportExcel ee=new ExportExcel();
			ee.exportExcel(fileName, Title, titlelist, list8.size(), c);
			
			}
		}

}
