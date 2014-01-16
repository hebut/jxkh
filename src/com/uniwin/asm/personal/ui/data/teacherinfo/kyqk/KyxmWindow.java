package com.uniwin.asm.personal.ui.data.teacherinfo.kyqk;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.log4j.Logger;
import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.common.util.ExportExcel;
import org.iti.gh.entity.GH_PHASEREPORT;
import org.iti.gh.entity.GH_PROMEMBER;
import org.iti.gh.entity.GhFile;
import org.iti.gh.entity.GhJsxm;
import org.iti.gh.entity.GhXm;
import org.iti.gh.entity.GhXmzz;
import org.iti.gh.service.CgService;
import org.iti.gh.service.GH_PHASEREPORTService;
import org.iti.gh.service.GhFileService;
import org.iti.gh.service.JsxmService;
import org.iti.gh.service.XmService;
import org.iti.gh.service.XmzzService;
import org.iti.gh.service.YjfxService;
import org.iti.projectmanage.science.member.service.ProjectMemberService;
import org.iti.projectmanage.science.project.KyHProWindow;
import org.iti.projectmanage.science.project.KycgYhdWindow;
import org.iti.xypt.ui.base.BaseWindow;
import org.mvel.optimizers.impl.refl.Fold;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.uniwin.asm.personal.ui.data.teacherinfo.CkshyjWindow;
import com.uniwin.framework.common.fileuploadex.UploadFJ;
import com.uniwin.framework.entity.WkTUser;

public class KyxmWindow extends BaseWindow {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8887043916596977149L;
	private Logger  log  = Logger.getLogger(KyxmWindow.class);

	//	/*科研情况*/已获得的科研成果（验收或鉴定）/目前承担的项目情况
	Toolbarbutton button1;//添加按钮
	Listbox zxListbox,hxxmListbox;
	Toolbarbutton submit1add,reset1add,cancel1add;	
	//获奖科研成果
	Button button2;//添加按钮
	
	//发表会议论文情况
	Button button3;
	Listbox listbox3;
	
	//发表期刊论文情况
	Button button31;
	Listbox listbox31;
	
	//出版学术专著情况
	Button button4;
	Listbox listbox4;	
	
	//获授权发明专利情况
	Button button5;
	Listbox listbox5;	
	
	//其他
	Button button6;
	Listbox listbox6;	
	
/*	//目前承担的项目情况
	Button button7;
	Listbox listbox7;*/
	
	//软件著作
	Button button16;
	Listbox listbox16;
	
	//未来五年科研计划
	Grid grid7;
	Textbox yjnr,jjwt,yqcg;
	XmService xmService;
	XmzzService xmzzService;
	CgService cgService;
	YjfxService yjfxService;
	GhFileService ghfileService;
	GH_PHASEREPORTService gh_phasereportService;
	ProjectMemberService projectmemberService;
	WkTUser user;	
	JsxmService jsxmService;

	private Paging zxPaging,hxPaging;
	private int   zxRowNum = 0;
	List zxProlist= new ArrayList(); //返回纵向项目查询的结果集
	List hxProlist=new ArrayList();  //返回横向项目查询的结果集
	/**
	 * 功能：初始化窗口
	 * @Data  2011-5-18
	 * @param null
	 * @author bobo
	 */
	public void initShow() {
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		initWindow();
		initHxProWindow();
		
		zxProlist = xmService.findCountByKuidAndTypeAndKyclass(user.getKuId(), GhJsxm.KYXM,"2");
		int totalZxSize = 0;
        if (zxProlist.size() > 0){            	
        	totalZxSize = ((Long) zxProlist.get(0)).intValue();
        }
		zxPaging.setTotalSize(totalZxSize);	
		zxPaging.addEventListener("onPaging", new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				List<GhXm> xm1list =xmService.getListPageByKuidAndTypeAndKyclass(user.getKuId(), GhJsxm.KYXM,"2",zxPaging.getActivePage(), zxPaging.getPageSize());			
				zxListbox.setModel(new ListModelList(xm1list));				
			}
		});
		
		hxProlist = xmService.findCountByKuidAndTypeAndKyclass(user.getKuId(), GhJsxm.KYXM,"1");
		int totalHxSize = 0;
        if (hxProlist.size() > 0){            	
        	totalHxSize = ((Long) hxProlist.get(0)).intValue();
        }
		hxPaging.setTotalSize(totalHxSize);		
		zxPaging.addEventListener("onPaging", new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				List<GhXm> xm2list =xmService.getListPageByKuidAndTypeAndKyclass(user.getKuId(), GhJsxm.KYXM,"1",zxPaging.getActivePage(), zxPaging.getPageSize());					
				hxxmListbox.setModel(new ListModelList(xm2list));			
			}
		});
		
		zxListbox.setItemRenderer(new ListitemRenderer(){
			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhXm xm = (GhXm) arg1;
				zxRowNum = zxPaging.getActivePage() * zxPaging.getPageSize() + arg0.getIndex() + 1;
				Listcell c1 = new Listcell(zxRowNum+"");
				InnerButton xmc;
				//项目名称
				Listcell c2 = new Listcell();
				if(xm.getKyMc()!=null){
					if(xm.getKyMc().trim().length()>12){
						String mc=xm.getKyMc().substring(0, 12);
						xmc= new InnerButton(mc+"...");
					}
					else{
						xmc = new InnerButton(xm.getKyMc());
					}
					xmc.addEventListener(Events.ON_CLICK, new EventListener(){
						public void onEvent(Event arg0) throws Exception {
							Map arg=new HashMap();
							arg.put("zxxm", xm);
							arg.put("useKuid",user.getKuId());
							final KycgYhdWindow cgWin = (KycgYhdWindow) Executions.createComponents
							("/admin/personal/data/teacherinfo/kyqk/kyxm/kycgyhd.zul", null, arg);							
							cgWin.initWindow();
							cgWin.doModal();
							cgWin.addEventListener(Events.ON_CHANGE, new EventListener(){
								public void onEvent(Event arg0)
								throws Exception {
									initWindow();
									cgWin.initWindow();
									cgWin.detach();
								}
							});
						}
					});
					c2.appendChild(xmc);
					c2.setTooltiptext(xm.getKyMc());
				}
				//项目来源
				Listcell c3 = new Listcell();
				if(xm.getKyLy()!=null){
					if(xm.getKyLy().trim().length()>8){
						c3.setLabel(xm.getKyLy().substring(0, 8)+"...");
						c3.setTooltiptext(xm.getKyLy());
					}
					else{
					c3.setLabel(xm.getKyLy());
					c3.setTooltiptext(xm.getKyLy());
					}
				}
				//负责人
				Listcell c4 = new Listcell();
				if(xm.getKyProman()!=null){
					c4.setLabel(xm.getKyProman()+"");
				}
				//本人贡献
				Listcell c5 = new Listcell();
				List manager= projectmemberService.findByKyIdAndKuId(xm.getKyId(),user.getKuId());
				if(manager.size()!=0)
				{
					GhJsxm man=(GhJsxm) manager.get(0);
					c5.setLabel(man.getKyGx().toString());
				}
				else
				{
					c5.setLabel("1");
				}
//				List manager= projectmemberService.findByKyIdAndKuId(xm.getKyId(),user.getKuLid());
//				if(manager.size()!=0){
//					GH_PROMEMBER man=(GH_PROMEMBER) manager.get(0);
//					c5.setLabel(man.getContribBank().toString());
//				}
//				else{
//					c5.setLabel("1");
//				}		
				
				Listcell c6=new Listcell();
				InnerButton ib = new InnerButton();
				if(xm.getAuditState()==null){
					c6.setLabel("未审核");
				}else if(xm.getAuditState()!=null&&xm.getAuditState().shortValue()==GhXm.AUDIT_NO){
					ib.setLabel("未通过");
					c6.appendChild(ib);
					ib.addEventListener(Events.ON_CLICK, new EventListener(){
						public void onEvent(Event arg0) throws Exception {
						 Map arg=new HashMap();
						 arg.put("type", 1);
						 arg.put("id",xm.getKyId());
						 CkshyjWindow cw=(CkshyjWindow)Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/ckpsyj.zul", null, arg);
						 cw.initWindow();
						 cw.doHighlighted();
						}						
					});
				}else if(xm.getAuditState()!=null&&xm.getAuditState().shortValue()==GhXm.AUDIT_YES){
					ib.setLabel("通过");
					c6.appendChild(ib);
					ib.addEventListener(Events.ON_CLICK, new EventListener(){
						public void onEvent(Event arg0) throws Exception {
						 Map arg=new HashMap();
						 arg.put("type", 1);
						 arg.put("id",xm.getKyId());
						 CkshyjWindow cw=(CkshyjWindow)Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/ckpsyj.zul", null, arg);
						 cw.initWindow();
						 cw.doHighlighted();
						}						
					});
				}
				//功能
				Listcell c7 =  new Listcell();
				InnerButton gn = new InnerButton();
				gn.setImage("/css/sat/actDel.gif");
				gn.addEventListener(Events.ON_CLICK, new EventListener(){
					public void onEvent(Event arg0) throws Exception {
						if(xm.getKuId().intValue()!=user.getKuId().intValue()){
							Messagebox.show("您不是此项目提交人，故不能删除！","提示", Messagebox.OK,Messagebox.EXCLAMATION);
						}else{
						if(Messagebox.show("确定删除吗?", "提示", Messagebox.OK|Messagebox.CANCEL,
								Messagebox.QUESTION)==1){
							List xmzzlist =	xmzzService.findByKyidAndKuidAndType(xm.getKyId(), user.getKuId(),GhJsxm.KYXM.shortValue());
							if(xmzzlist != null && xmzzlist.size() !=0){
								for(int i = 0;i < xmzzlist.size();i++){
									GhXmzz xmzz = (GhXmzz) xmzzlist.get(i);
									xmzzService.delete(xmzz);
								}
							}
							List cglist =jsxmService.findByXmidAndtype(xm.getKyId(), GhJsxm.KYXM);
							if(cglist != null && cglist.size() != 0){
								for(int i=0;i<cglist.size();i++){
									GhJsxm jsxm=(GhJsxm)cglist.get(i);
									jsxmService.delete(jsxm);
								}
							}
							//删除附件
							List list=ghfileService.findByFxmIdandFType(xm.getKyId(),1);
							if(list.size()>0){
								//存在附件
								for(int i=0;i<list.size();i++){
									UploadFJ ufj=new UploadFJ(false);
									ufj.ReadFJ(getDesktop(), (GhFile) list.get(i));
									ufj.DeleteFJ();
								}
							}
							xmService.delete(xm);
							/**
							 * 级联删除该项目所有成员的阶段报告记录
							 */							
							List reportList = gh_phasereportService.findByKyxmId(xm.getKyId());
							if(reportList.size()!=0) {
								for(int i=0;i<reportList.size();i++) {
									GH_PHASEREPORT phaseReport = (GH_PHASEREPORT)reportList.get(i);
									//删除数据库中该项目的所有成员阶段报告记录
									gh_phasereportService.delete(phaseReport);
									//删除服务器上所上传的属于该项目的所有阶段报告
									String archivePath =Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/report")
									+ File.separator+xm.getKyId() + File.separator + phaseReport.getPhRepoPath();
									File archiveFile = new File(archivePath);						
									boolean isDel = false;
									if(archiveFile.exists())
									{
										isDel = archiveFile.delete();
									}else{
										isDel = true;
									}
								}
							}
							/**
							 * 
							 */
							initWindow();
						}
						}
					}
				});
				c7.appendChild(gn);
				arg0.appendChild(c1); arg0.appendChild(c2); arg0.appendChild(c3);
				arg0.appendChild(c4); 
				arg0.appendChild(c5); arg0.appendChild(c6); arg0.appendChild(c7); 
			}
		});

		//横向项目列表
		hxxmListbox.setItemRenderer(new ListitemRenderer(){
			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhXm xm = (GhXm) arg1;
				Listcell c1 = new Listcell(arg0.getIndex()+1+"");
				InnerButton xmc;
				Listcell c2 = new Listcell();
				if(xm.getKyMc()!=null){
					if(xm.getKyMc().trim().length()>12){
						String mc=xm.getKyMc().substring(0, 12);
						xmc= new InnerButton(mc+"...");
					}
					else{
						xmc = new InnerButton(xm.getKyMc());
					}
					xmc.addEventListener(Events.ON_CLICK, new EventListener(){
						public void onEvent(Event arg0) throws Exception {
							Map arg=new HashMap();
							arg.put("hxxm", xm);
							arg.put("useKuid",user.getKuId());
							final KyHProWindow cgWin = (KyHProWindow) Executions.createComponents
											("/admin/personal/data/teacherinfo/kyqk/kyxm/hengxiang.zul", null, arg);							
							cgWin.initWindow();
							cgWin.doModal();
							cgWin.addEventListener(Events.ON_CHANGE, new EventListener(){
								public void onEvent(Event arg0)
								throws Exception {
									initHxProWindow();
									cgWin.initWindow();
									cgWin.detach();
								}
							});
						}
					});
					
					c2.appendChild(xmc);
					c2.setTooltiptext(xm.getKyMc());
				}
				//项目来源
				Listcell c3 = new Listcell();
				if(xm.getKyLy()!=null){
					if(xm.getKyLy().trim().length()>8){
						c3.setLabel(xm.getKyLy().substring(0, 8)+"...");
						c3.setTooltiptext(xm.getKyLy());
					}
					else{
					c3.setLabel(xm.getKyLy());
					c3.setTooltiptext(xm.getKyLy());
					}
				}
				//负责人
				Listcell c4 = new Listcell();
				if(xm.getKyProman()!=null){
					c4.setLabel(xm.getKyProman()+"");
				}
				//本人贡献
				Listcell c5 = new Listcell();
				GhJsxm jsxm=jsxmService.findByXmidAndKuidAndType(xm.getKyId(),user.getKuId(),GhJsxm.KYXM);
				if(jsxm!=null&&jsxm.getKyGx()!=null){
					c5.setLabel(jsxm.getKyGx()+"");
				}
		
								
				Listcell c6=new Listcell();
				InnerButton ib = new InnerButton();
				if(xm.getAuditState()==null){
					c6.setLabel("未审核");
				}else if(xm.getAuditState()!=null&&xm.getAuditState().shortValue()==GhXm.AUDIT_NO){
					ib.setLabel("未通过");
					c6.appendChild(ib);
					ib.addEventListener(Events.ON_CLICK, new EventListener(){
						public void onEvent(Event arg0) throws Exception {
						 Map arg=new HashMap();
						 arg.put("type", 1);
						 arg.put("id",xm.getKyId());
						 CkshyjWindow cw=(CkshyjWindow)Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/ckpsyj.zul", null, arg);
						 cw.initWindow();
						 cw.doHighlighted();
						}
						
					});
				}else if(xm.getAuditState()!=null&&xm.getAuditState().shortValue()==GhXm.AUDIT_YES){
					ib.setLabel("通过");
					c6.appendChild(ib);
					ib.addEventListener(Events.ON_CLICK, new EventListener(){
						public void onEvent(Event arg0) throws Exception {
						 Map arg=new HashMap();
						 arg.put("type", 1);
						 arg.put("id",xm.getKyId());
						 CkshyjWindow cw=(CkshyjWindow)Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/ckpsyj.zul", null, arg);
						 cw.initWindow();
						 cw.doHighlighted();
						}
					});
				}
				
				//功能
				Listcell c7 =  new Listcell();
				InnerButton gn = new InnerButton();
				gn.setImage("/css/sat/actDel.gif");
				gn.addEventListener(Events.ON_CLICK, new EventListener(){
					public void onEvent(Event arg0) throws Exception {
						if(xm.getKuId().intValue()!=user.getKuId().intValue()){
							Messagebox.show("您不是此项目提交人，故不能删除！","提示", Messagebox.OK,Messagebox.EXCLAMATION);
						}else{
						if(Messagebox.show("确定删除吗?", "提示", Messagebox.OK|Messagebox.CANCEL,
								Messagebox.QUESTION)==1){
							List xmzzlist =	xmzzService.findByKyidAndKuidAndType(xm.getKyId(), user.getKuId(),GhJsxm.KYXM.shortValue());
							if(xmzzlist != null && xmzzlist.size() !=0){
								for(int i = 0;i < xmzzlist.size();i++){
									GhXmzz xmzz = (GhXmzz) xmzzlist.get(i);
									xmzzService.delete(xmzz);
								}
							}
							List cglist =jsxmService.findByXmidAndtype(xm.getKyId(), GhJsxm.KYXM);
							if(cglist != null && cglist.size() != 0){
								for(int i=0;i<cglist.size();i++){
									GhJsxm jsxm=(GhJsxm)cglist.get(i);
									jsxmService.delete(jsxm);
								}
							}
							//删除附件
							List list=ghfileService.findByFxmIdandFType(xm.getKyId(),1);
							if(list.size()>0){
								//存在附件
								for(int i=0;i<list.size();i++){
									UploadFJ ufj=new UploadFJ(false);
									ufj.ReadFJ(getDesktop(), (GhFile) list.get(i));
									ufj.DeleteFJ();
								}
							}
							xmService.delete(xm);
							/**
							 * 级联删除该项目所有成员的阶段报告记录
							 */							
							List reportList = gh_phasereportService.findByKyxmId(xm.getKyId());
							if(reportList.size()!=0) {
								for(int i=0;i<reportList.size();i++) {
									GH_PHASEREPORT phaseReport = (GH_PHASEREPORT)reportList.get(i);
									//删除数据库中该项目的所有成员阶段报告记录
									gh_phasereportService.delete(phaseReport);
									//删除服务器上所上传的属于该项目的所有阶段报告
									String archivePath =Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/report")
									+ File.separator+xm.getKyId() + File.separator + phaseReport.getPhRepoPath();
									File archiveFile = new File(archivePath);						
									boolean isDel = false;
									if(archiveFile.exists())
									{
										isDel = archiveFile.delete();
									}else{
										isDel = true;
									}
								}
							}
							/**
							 * 
							 */
							initHxProWindow();
						}
						}
					}
				});
				c7.appendChild(gn);
				arg0.appendChild(c1); arg0.appendChild(c2); arg0.appendChild(c3);
				arg0.appendChild(c4); 
				arg0.appendChild(c5); arg0.appendChild(c6); arg0.appendChild(c7); 
			}
		});
	}
	/**
	 * 功能：查询纵向项目列表
	 * @Data  2011-5-18
	 * @param null
	 * @author bobo
	 */
	public void initWindow() {
		List<GhXm> zxlist = xmService.getListPageByKuidAndTypeAndKyclass(user.getKuId(), GhJsxm.KYXM,"2",zxPaging.getActivePage(), zxPaging.getPageSize());			
		zxListbox.setModel(new ListModelList(zxlist));
	}
	
	/**
	 * 功能：查询横向项目列表
	 * @Data  2011-5-18
	 * @param null
	 * @author bobo
	 */
	public void initHxProWindow() {
		List hxProlist = xmService.getListPageByKuidAndTypeAndKyclass(user.getKuId(), GhJsxm.KYXM,"1",zxPaging.getActivePage(), zxPaging.getPageSize());			
		hxxmListbox.setModel(new ListModelList(hxProlist));
	}
	
	/**
	 * 功能：添加纵向项目
	 * @Data  2011-5-18
	 * @param null
	 * @author bobo
	 */
	public void onClick$button1(){
		Map arg=new HashMap();
		arg.put("useKuid",user.getKuId());
		final KycgYhdWindow cgWin = (KycgYhdWindow) Executions.createComponents
									("/admin/personal/data/teacherinfo/kyqk/kyxm/kycgyhd.zul", null, arg);
		cgWin.initWindow();
		try {
			cgWin.doModal();
		} catch (SuspendNotAllowedException e) {
			log.error("当前系统时间："+ new Date().toLocaleString()
                    + " "+ "添加纵向项目窗口"+ e.getMessage());
			e.printStackTrace();
		} catch (InterruptedException e) {
			log.error("当前系统时间："+ new Date().toLocaleString()
                    + " "+ "添加纵向项目窗口"+ e.getMessage());
			e.printStackTrace();
		}
		cgWin.addEventListener(Events.ON_CHANGE, new EventListener(){
			public void onEvent(Event arg0)
			throws Exception {
				initWindow();
				cgWin.detach();
			}
		});
		initShow();
	}	
	/**
	 * 功能：添加横向项目
	 * @Data  2011-5-18
	 * @param null
	 * @author bobo
	 */
	public void onClick$button2(){
		Map arg=new HashMap();
		arg.put("useKuid",user.getKuId());
		final KyHProWindow cgWin = (KyHProWindow) Executions.createComponents
									("/admin/personal/data/teacherinfo/kyqk/kyxm/hengxiang.zul", null, arg);
		cgWin.initWindow();		
		try {
			cgWin.doModal();
		} catch (SuspendNotAllowedException e) {
			log.error("当前系统时间："+ new Date().toLocaleString()
                    + " "+ "添加横向项目窗口"+ e.getMessage());
			e.printStackTrace();
		} catch (InterruptedException e) {
			log.error("当前系统时间："+ new Date().toLocaleString()
                    + " "+ "添加横向项目窗口"+ e.getMessage());
			e.printStackTrace();
		}
		cgWin.addEventListener(Events.ON_CHANGE, new EventListener(){
			public void onEvent(Event arg0)
			throws Exception {
				initHxProWindow();
				cgWin.detach();
			}
		});
		initShow();
	}
	
	/**
	 * 纵向项目信息 导出
	 * @throws IOException
	 * @throws WriteException
	 */
	 public void onClick$button1out()  throws IOException, WriteException{
			//科研成果（验收或鉴定）
			List list1 = xmService.findByKuidAndTypeAndKyclass(user.getKuId(), GhJsxm.KYXM,"2");
			 if(list1.size()==0){
				 try {
		 				Messagebox.show("空数据，导出错误！", "错误", Messagebox.OK,Messagebox.INFORMATION);
		 			} catch (InterruptedException e) {
		 				e.printStackTrace();
		 			}
		 			  return;
			}else{
		    	Date now=new Date();
		    	String fileName = "纵向科研项目.xls";
		    	String Title = "纵向科研项目";
		    	WritableWorkbook workbook;
				List titlelist=new ArrayList();
				titlelist.add("序号");
				titlelist.add("项目编号");
				titlelist.add("项目名称");
				titlelist.add("项目来源");
				titlelist.add("开始时间");
				titlelist.add("结束时间");
				titlelist.add("项目经费（万元）");
				titlelist.add("本人贡献排名");
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
//				titlelist.add("是否获奖");
				titlelist.add("本人承担任务或作用");
				titlelist.add("项目内部编号");
				String c[][]=new String[list1.size()][titlelist.size()];
				//从SQL中读数据
				for(int j=0;j<list1.size();j++){
					GhXm xm  =(GhXm)list1.get(j);
				    c[j][0]=j+1+"";
				    c[j][1]=xm.getKyNumber();
					c[j][2]=xm.getKyMc();
				    c[j][3]=xm.getKyLy();
					c[j][4]=xm.getKyKssj();
					c[j][5]=xm.getKyJssj();
					c[j][6]=xm.getKyJf()!=null?xm.getKyJf().toString():"";
					GhJsxm jsxm=jsxmService.findByXmidAndKuidAndType(xm.getKyId(),user.getKuId(),GhJsxm.KYXM);
					if(jsxm!=null&&jsxm.getKyGx()!=null){
						c[j][7]=jsxm.getKyGx()+"";						
					}else{
						c[j][7]="";
					}
					if(jsxm!=null&&jsxm.getYjId()!=null&&jsxm.getYjfx()!=null){
						c[j][8]=jsxm.getYjfx().getGyName();
					}else{
						c[j][8]="";
					}
					//学科分类
					if(xm.getKySubjetype() == null ||xm.getKySubjetype().equals("") || xm.getKySubjetype().trim().equalsIgnoreCase("-1")){
						c[j][9]="";
					}else if(xm.getKySubjetype().equalsIgnoreCase( "1")){
						c[j][9]="自然科学";
					}else if(xm.getKySubjetype().equalsIgnoreCase( "2")){
						c[j][9]="社会科学";
					}else if(xm.getKySubjetype().equalsIgnoreCase( "3")){
						c[j][9]="其他";
					}					
					c[j][10]=xm.getKyProman();
					c[j][11]=xm.getKyProstaffs();
					c[j][12]=xm.getKyRegister();
					//项目类别
					if(xm.getKyClass() == null ||xm.getKyClass().equals("") || xm.getKyClass().trim().equalsIgnoreCase("-1")){
						c[j][13]="";
					}else {if(xm.getKyClass().trim().equalsIgnoreCase( "1")){
						c[j][13]="横向";
					}else if(xm.getKyClass().trim().equalsIgnoreCase( "2")){
						c[j][13]="纵向";
					}}
					//项目级别
					if(xm.getKyScale() == null || xm.getKyScale().equals("") || xm.getKyScale().trim() == "-1"){
						c[j][14]="";
					}else{ if(xm.getKyScale().trim().equalsIgnoreCase( "1")){
						c[j][14]="国际合作项目";
					}else if(xm.getKyScale().trim().equalsIgnoreCase( "2")){
						c[j][14]="国家级项目";
					}else if(xm.getKyScale().trim().equalsIgnoreCase( "3")){
						c[j][14]="部（委、省）级项目";
					}else if(xm.getKyScale().trim().equalsIgnoreCase( "4")){
						c[j][14]="市厅级项目";
					}else if(xm.getKyScale().trim().equalsIgnoreCase( "5")){
						c[j][14]="委托及开发项目";
					}else if(xm.getKyScale().trim().equalsIgnoreCase( "6")){
						c[j][14]="学校基金项目";
					}else {
						c[j][14]="其他";
					}}
					//项目进展
					if(xm.getKyProgress() == null ||xm.getKyProgress().equals("") || xm.getKyProgress().trim() ==""){
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
					
//					//项目获奖
//					if(xm.getKyPrize() == null || xm.getKyPrize().trim() == "" ||xm.getKyPrize().trim().equalsIgnoreCase( "1")){
//							c[j][19]="否";
//
//					}else if(xm.getKyPrize().trim().equalsIgnoreCase( "2")){
//						c[j][19]="是";
//					}
					//本人承担任务或作用
					if(jsxm.getKyCdrw() == null || jsxm.getKyCdrw().equals("") || jsxm.getKyCdrw().trim().equalsIgnoreCase("-1")){
						c[j][19]="";
					}else if(jsxm.getKyCdrw().trim().equalsIgnoreCase( "1")){
						c[j][19]="主持";
					}else if(jsxm.getKyCdrw().trim().equalsIgnoreCase( "2")){
						c[j][19]="参加";
					}else if(jsxm.getKyCdrw().trim().equalsIgnoreCase( "3")){
						c[j][19]="独立";
					}	
					if(xm.getInternalNum()==null||xm.getInternalNum().equals("")){
						c[j][20]="";
					}else{
						c[j][20]=xm.getInternalNum().toString();
					}					
				}
		         ExportExcel ee=new ExportExcel();
				ee.exportExcel(fileName, Title, titlelist, list1.size(), c);
		    	}
		     }
	 
	 /**
		 * 横向项目信息 导出
		 * @throws IOException
		 * @throws WriteException
		 */
		 public void onClick$button2out()  throws IOException, WriteException{
				List list1 = xmService.findByKuidAndTypeAndKyclass(user.getKuId(), GhJsxm.KYXM,"1");
				 if(list1.size()==0){
					 try {
			 				Messagebox.show("空数据，导出错误！", "错误", Messagebox.OK,Messagebox.INFORMATION);
			 			} catch (InterruptedException e) {
			 				e.printStackTrace();
			 			}
			 			  return;
				}else{
			    	Date now=new Date();
			    	String fileName = "横向科研项目.xls";
			    	String Title = "横向科研项目";
			    	WritableWorkbook workbook;
					List titlelist=new ArrayList();
					titlelist.add("序号");
					titlelist.add("项目编号");
					titlelist.add("项目名称");
					titlelist.add("项目来源");
					titlelist.add("开始时间");
					titlelist.add("结束时间");
					titlelist.add("项目经费（万元）");
					titlelist.add("本人贡献排名");
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
//					titlelist.add("是否获奖");
					titlelist.add("本人承担任务或作用");
					titlelist.add("项目内部编号");
					String c[][]=new String[list1.size()][titlelist.size()];
					//从SQL中读数据
					for(int j=0;j<list1.size();j++){
						GhXm xm  =(GhXm)list1.get(j);
					    c[j][0]=j+1+"";
					    c[j][1]=xm.getKyNumber();
						c[j][2]=xm.getKyMc();
					    c[j][3]=xm.getKyLy();
						c[j][4]=xm.getKyKssj();
						c[j][5]=xm.getKyJssj();
						c[j][6]=xm.getKyJf()!=null?xm.getKyJf().toString():"";
						GhJsxm jsxm=jsxmService.findByXmidAndKuidAndType(xm.getKyId(),user.getKuId(),GhJsxm.KYXM);
						if(jsxm!=null&&jsxm.getKyGx()!=null){
							c[j][7]=jsxm.getKyGx()+"";
							
						}else{
							c[j][7]="";
						}
						if(jsxm!=null&&jsxm.getYjId()!=null&&jsxm.getYjfx()!=null){
							c[j][8]=jsxm.getYjfx().getGyName();
						}else{
							c[j][8]="";
						}
						//学科分类
						if(xm.getKySubjetype() == null ||xm.getKySubjetype().equals("") || xm.getKySubjetype().trim().equalsIgnoreCase("-1")){
							c[j][9]="";
						}else if(xm.getKySubjetype().equalsIgnoreCase( "1")){
							c[j][9]="自然科学";
						}else if(xm.getKySubjetype().equalsIgnoreCase( "2")){
							c[j][9]="社会科学";
						}else if(xm.getKySubjetype().equalsIgnoreCase( "3")){
							c[j][9]="其他";
						}
						
						c[j][10]=xm.getKyProman();
						c[j][11]=xm.getKyProstaffs();
						c[j][12]=xm.getKyRegister();
						//项目类别
						if(xm.getKyClass() == null ||xm.getKyClass().equals("") || xm.getKyClass().trim().equalsIgnoreCase("-1")){
							c[j][13]="";
						}else {if(xm.getKyClass().trim().equalsIgnoreCase( "1")){
							c[j][13]="横向";
						}else if(xm.getKyClass().trim().equalsIgnoreCase( "2")){
							c[j][13]="纵向";
						}}
						
						//项目级别
						if(xm.getKyScale() == null || xm.getKyScale().equals("") || xm.getKyScale().trim() == "-1"){
							c[j][14]="";
						}else{ if(xm.getKyScale().trim().equalsIgnoreCase( "1")){
							c[j][14]="国际合作项目";
						}else if(xm.getKyScale().trim().equalsIgnoreCase( "2")){
							c[j][14]="国家级项目";
						}else if(xm.getKyScale().trim().equalsIgnoreCase( "3")){
							c[j][14]="部（委、省）级项目";
						}else if(xm.getKyScale().trim().equalsIgnoreCase( "4")){
							c[j][14]="市厅级项目";
						}else if(xm.getKyScale().trim().equalsIgnoreCase( "5")){
							c[j][14]="委托及开发项目";
						}else if(xm.getKyScale().trim().equalsIgnoreCase( "6")){
							c[j][14]="学校基金项目";
						}else {
							c[j][14]="其他";
						}}
						//项目进展
						if(xm.getKyProgress() == null ||xm.getKyProgress().equals("") || xm.getKyProgress().trim() ==""){
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
						
//						//项目获奖
//						if(xm.getKyPrize() == null || xm.getKyPrize().trim() == "" ||xm.getKyPrize().trim().equalsIgnoreCase( "1")){
//								c[j][19]="否";
	//
//						}else if(xm.getKyPrize().trim().equalsIgnoreCase( "2")){
//							c[j][19]="是";
//						}
						//本人承担任务或作用
						if(jsxm.getKyCdrw() == null || jsxm.getKyCdrw().equals("") || jsxm.getKyCdrw().trim().equalsIgnoreCase("-1")){
							c[j][19]="";
						}else if(jsxm.getKyCdrw().trim().equalsIgnoreCase( "1")){
							c[j][19]="主持";
						}else if(jsxm.getKyCdrw().trim().equalsIgnoreCase( "2")){
							c[j][19]="参加";
						}else if(jsxm.getKyCdrw().trim().equalsIgnoreCase( "3")){
							c[j][19]="独立";
						}
						//项目内部编号
						if(xm.getInternalNum()==null||xm.getInternalNum().equals("")){
							c[j][20]="";
						}else{
							c[j][20]=xm.getInternalNum().toString();
						}	
						
					}
			         ExportExcel ee=new ExportExcel();
					ee.exportExcel(fileName, Title, titlelist, list1.size(), c);
			    	}
			     }
}
