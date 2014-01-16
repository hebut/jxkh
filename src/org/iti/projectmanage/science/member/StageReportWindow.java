package org.iti.projectmanage.science.member;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.iti.gh.entity.GH_PHASEREPORT;
import org.iti.gh.entity.GhJsxm;
import org.iti.gh.entity.GhXm;
import org.iti.gh.service.GH_PHASEREPORTService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Div;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTUser;


public class StageReportWindow extends BaseWindow {
	Window stageReport;
	Toolbarbutton addReport,search;
	Div report;
	Listbox stage;
	Paging cyPaging;
	Long proId;
	GhJsxm member = new GhJsxm();
	public GhJsxm getMember() {
		return member;
	}

	public void setMember(GhJsxm member) {
		this.member = member;
	}
	GhXm xm = new GhXm();
	WkTUser user;
	GH_PHASEREPORTService gh_phasereportService;
	public GhXm getXm() {
		return xm;
	}

	public void setXm(GhXm xm) {
		this.xm = xm;
	}

	public Long getProId() {
		return proId;
	}

	public void setProId(Long proId) {
		this.proId = proId;
	}

	
	public void initShow() {		
		stage.setItemRenderer(new ListitemRenderer() {			
			public void render(Listitem arg0, Object arg1) throws Exception {				
				final GH_PHASEREPORT ghReport = (GH_PHASEREPORT)arg1;				
				Listcell c0 = new Listcell();
				Listcell c1 = new Listcell(arg0.getIndex()+1+"");
				Listcell c2 = new Listcell();
				String c2String;
				if((ghReport.getPhRepoName()==null?0:ghReport.getPhRepoName().length()) > 12) {
					c2String = ghReport.getPhRepoName().substring(0, 11)+"...";
				}else {
					c2String = ghReport.getPhRepoName();
				}
				c2.setLabel(c2String);
				c2.setStyle("color:blue");
				c2.setTooltiptext(ghReport.getPhRepoName());
				c2.addEventListener(Events.ON_CLICK, new EventListener() {

					@Override
					public void onEvent(Event arg0) throws Exception {					
						StageReportViewWindow srv = (StageReportViewWindow)Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/member/reportDetail.zul", null, null);
						
						srv.initWindow(xm, ghReport);
						srv.addEventListener(Events.ON_CHANGE, new EventListener() {						
							public void onEvent(Event arg0) throws Exception {								
								initWindow();
							}
							
						});
						srv.doModal();
					}
					
				});
				Listcell c3 = new Listcell();
				String c3String;
				if((ghReport.getKeyWord()==null?0:ghReport.getKeyWord().length()) > 10) {
					c3String = ghReport.getKeyWord().substring(0, 9)+"...";
				}else {
					c3String = ghReport.getKeyWord();
				}
				c3.setLabel(c3String);
				c3.setTooltiptext(ghReport.getKeyWord());
				Listcell c4 = new Listcell(ghReport.getPhRepoUser());
				Listcell c5 = new Listcell(ghReport.getPhRepoDate());				
				Listcell c6 = new Listcell();
				Hbox hb = new Hbox();
				//下载阶段报告
				Toolbarbutton tb1 = new Toolbarbutton();
				tb1.setImage("/css/sat/down.png");
				
				tb1.setTooltiptext("下载阶段报告");
				tb1.addEventListener(Events.ON_CLICK, new EventListener() {					
					public void onEvent(Event arg0) throws Exception {						
						String reportPath =Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/report")
						+ File.separator+xm.getKyId() + File.separator + ghReport.getPhRepoPath();
					File reportFile = new File(reportPath);
					try 
					{
						if(!reportFile.exists())
						{
							Messagebox.show("阶段报告已经被删除！", "提示", Messagebox.OK, Messagebox.INFORMATION);
							return;
						}
						Filedownload.save(reportFile,null);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					}
					
				});
				//tb1.setParent(hb);
				
				//tb2.setParent(hb);
				//查看/编辑阶段报告
				Toolbarbutton tb3 = new Toolbarbutton();
				tb3.setImage("/css/sat/edit.gif");
				tb3.setTooltiptext("查看/编辑");
				tb3.addEventListener(Events.ON_CLICK, new EventListener() {				
					public void onEvent(Event arg0) throws Exception {						
						StageReportViewWindow srv = (StageReportViewWindow)Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/member/reportDetail.zul", null, null);
						srv.initWindow(xm, ghReport);
						//当编辑完保存时相应的显示页面进行改变
						srv.addEventListener(Events.ON_CHANGE, new EventListener() {							
							public void onEvent(Event arg0) throws Exception {								
								initWindow();
							}
							
						});
						
						srv.doModal();
						
						
						
					}
					
				});
				//删除阶段报告
				Toolbarbutton tb2 = new Toolbarbutton();
				tb2.setImage("/css/sat/actDel.gif");
				tb2.setTooltiptext("删除");
				tb2.addEventListener(Events.ON_CLICK, new EventListener() {					
					public void onEvent(Event arg0) throws Exception { 						
						if(Messagebox.show("确定要删除该阶段报告？", "确认", 
								Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.OK)
						{  
							gh_phasereportService.delete(ghReport);
							initWindow();
							String archivePath =Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/report")
							+ File.separator+xm.getKyId() + File.separator + ghReport.getPhRepoPath();
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
					
				});
				//tb3.setParent(hb);
				if(!ghReport.getPhRepoUser().trim().equals(user.getKuName().trim())) {
					hb.appendChild(tb1);
					hb.appendChild(tb3);
				}else {
					hb.appendChild(tb1);
					hb.appendChild(tb3);
					hb.appendChild(tb2);
				}
				
				hb.setParent(c6);
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
				arg0.appendChild(c5);
				arg0.appendChild(c6);
			}
			
		});
	}
	public void initWindow() {		
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		if(!member.getKuId().equals(user.getKuId())) {
			addReport.setVisible(false);
		}		
		//List reportList = gh_phasereportService.findStageByProidAndKulid(xm.getKyId(), member.getKuId());
		List report = gh_phasereportService.findStageByProidAndKulid(xm.getKyId(), member.getKuId(),cyPaging.getActivePage(),cyPaging.getPageSize());
		List count = gh_phasereportService.findPageCount(xm.getKyId(), member.getKuId());
		if(count.get(0)!=null) {
			cyPaging.setTotalSize(((Long)count.get(0)).intValue()); //要显示总数量
			stage.setModel(new ListModelList(report)); //进入页面时只显示当前第一页
			cyPaging.addEventListener("onPaging", new EventListener() { //实现点击下一页/上一页时的侦听
				public void onEvent(Event arg0) throws Exception {
					List report1 =gh_phasereportService.findStageByProidAndKulid(xm.getKyId(),member.getKuId(),cyPaging.getActivePage(), cyPaging.getPageSize());
					stage.setModel(new ListModelList(report1));				
				}
			});
		}
		

	}
	
	public void onClick$addReport() throws SuspendNotAllowedException, InterruptedException {
		StageReportAddWindow sraw = (StageReportAddWindow)Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/member/newReport.zul", null, null);
		sraw.setXm(xm);
		sraw.initWindow();
		sraw.addEventListener(Events.ON_CHANGE, new EventListener() {			
			public void onEvent(Event arg0) throws Exception {				
				initWindow();
			}
			
		});
		sraw.doModal();		

	}
	
	public void initShow1() {
		report.setVisible(true);
	}
	
	public void onClick$search() {
		report.setVisible(false);
		ReportSearchWindow rsw = (ReportSearchWindow)Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/member/searchReport.zul", null, null);
		rsw.setXm(xm);
		rsw.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				initShow1();
			}
		});
		stageReport.appendChild(rsw);
	}
	
	public void onClick$reback()
	{
		Events.postEvent(Events.ON_CHANGE, this, null);
		stageReport.detach();
	}

}
