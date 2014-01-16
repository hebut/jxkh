package org.iti.projectmanage.science.member;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.iti.gh.entity.GH_PHASEREPORT;
import org.iti.gh.entity.GhXm;
import org.iti.gh.service.GH_PHASEREPORTService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
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
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

public class ReportSearchWindow extends Window implements AfterCompose {
	Window searchReport;
	GhXm xm = new GhXm();
	Toolbarbutton search;
	Listbox searchFild,report;
	Textbox fild;
	Paging srPaging;
	GH_PHASEREPORTService gh_phasereportService;
	public GhXm getXm() {
		return xm;
	}

	public void setXm(GhXm xm) {
		this.xm = xm;
	}

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		searchFild.setSelectedIndex(0);
		report.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				final GH_PHASEREPORT ghReport = (GH_PHASEREPORT)arg1;
				Listcell c0 = new Listcell();
				Listcell c1 = new Listcell(arg0.getIndex()+1+"");
				String c2String;
				if((ghReport.getPhRepoName()==null?0:ghReport.getPhRepoName().length()) > 12) {
					c2String = ghReport.getPhRepoName().substring(0, 11)+"...";
				}else {
					c2String = ghReport.getPhRepoName();
				}
				Listcell c2 = new Listcell(c2String);
				c2.setStyle("color:blue");
				c2.setTooltiptext(ghReport.getPhRepoName());
				c2.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {					
						SearchReportViewWindow srvw = (SearchReportViewWindow)Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/member/viewSearchReport.zul", null, null);						
						srvw.initWindow(ghReport,xm);						
						srvw.doModal();
					}
					
				});
				String c3String;
				if((ghReport.getKeyWord()==null?0:ghReport.getKeyWord().length()) > 10) {
					c3String = ghReport.getKeyWord().substring(0, 9)+"...";
				}else {
					c3String = ghReport.getKeyWord();
				}
				Listcell c3 = new Listcell(c3String);
				c3.setTooltiptext(ghReport.getKeyWord());
				Listcell c4 = new Listcell(ghReport.getPhRepoUser());
				Listcell c5 = new Listcell(ghReport.getPhRepoDate());
				Listcell c6 = new Listcell();
				Hbox hb = new Hbox();
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
				Toolbarbutton tb2 = new Toolbarbutton();
				tb2.setImage("/css/sat/sear_ico.jpg");
				tb2.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						SearchReportViewWindow srvw = (SearchReportViewWindow)Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/member/viewSearchReport.zul", null, null);						
						srvw.initWindow(ghReport,xm);						
						srvw.doModal();						
					}
					
				});
				tb1.setParent(hb);
				tb2.setParent(hb);
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
	
	public void onClick$search() {
		if(searchFild.getSelectedIndex() == 0) {			
			List reportList0 = gh_phasereportService.findByKyxmId(xm.getKyId(), srPaging.getActivePage(), srPaging.getPageSize());
			List count0 = gh_phasereportService.findReportSum(xm.getKyId());
			if(count0.get(0) != null) {
				srPaging.setTotalSize(((Long)count0.get(0)).intValue());
				report.setModel(new ListModelList(reportList0));
				srPaging.addEventListener("onPaging", new EventListener() { //实现点击下一页/上一页时的侦听
					public void onEvent(Event arg0) throws Exception {
						List report0 =gh_phasereportService.findByKyxmId(xm.getKyId(),srPaging.getActivePage(), srPaging.getPageSize());
						report.setModel(new ListModelList(report0));				
					}
				});
			}
		}
		else if(searchFild.getSelectedIndex() == 1) {
			List reportList1 = gh_phasereportService.findByKyxmIdAndReportName(xm.getKyId(), fild.getValue(), srPaging.getActivePage(), srPaging.getPageSize());
			List count1 = gh_phasereportService.findReportTotalSum(xm.getKyId(), fild.getValue());
			if(count1.get(0) != null) {
				srPaging.setTotalSize(((Long)count1.get(0)).intValue());
				report.setModel(new ListModelList(reportList1));
				srPaging.addEventListener("onPaging", new EventListener() { //实现点击下一页/上一页时的侦听
					public void onEvent(Event arg0) throws Exception {
						List report1 =gh_phasereportService.findByKyxmIdAndReportName(xm.getKyId(), fild.getValue(), srPaging.getActivePage(), srPaging.getPageSize());
						report.setModel(new ListModelList(report1));				
					}
				});
			}
		}else {
			List reportList2 = gh_phasereportService.findByKyxmIdAndKeyWord(xm.getKyId(), fild.getValue(), srPaging.getActivePage(), srPaging.getPageSize());
			List count2 = gh_phasereportService.findReportTotalSumByKeyWord(xm.getKyId(), fild.getValue());
			if(count2.get(0) != null) {
				srPaging.setTotalSize(((Long)count2.get(0)).intValue());
				report.setModel(new ListModelList(reportList2));
				srPaging.addEventListener("onPaging", new EventListener() { //实现点击下一页/上一页时的侦听
					public void onEvent(Event arg0) throws Exception {
						List report2 =gh_phasereportService.findByKyxmIdAndReportName(xm.getKyId(), fild.getValue(), srPaging.getActivePage(), srPaging.getPageSize());
						report.setModel(new ListModelList(report2));				
					}
				});
			}
		}
	}
	
	public void onClick$back() {
		Events.postEvent(Events.ON_CHANGE, this, null);
		searchReport.detach();
	}

}
