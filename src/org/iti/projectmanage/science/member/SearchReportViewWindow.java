package org.iti.projectmanage.science.member;

import java.io.File;
import java.io.FileNotFoundException;

import org.iti.gh.entity.GH_PHASEREPORT;
import org.iti.gh.entity.GhXm;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

public class SearchReportViewWindow extends Window implements AfterCompose {

	Label projectName,reportName,keyWord,remark;
	Label downloadFile;
	GH_PHASEREPORT ghReport;
	GhXm xm;
	Toolbarbutton download,back;
	String path;
	//GH_PHASEREPORT ghReport = new GH_PHASEREPORT();
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);		
	}
	
	public void initWindow(GH_PHASEREPORT ghReport,GhXm xm) {
		this.ghReport = ghReport;
		this.xm = xm;
		String fileName;
		projectName.setValue(xm.getKyMc());
		reportName.setValue(ghReport.getPhRepoName());
		keyWord.setValue(ghReport.getKeyWord());
		remark.setValue(ghReport.getPhRepoRemark());
		path=ghReport.getPhRepoPath();
		if(path.length()>10) {
			fileName=path.substring(0, 9)+"...";
		}else {
			fileName=path;
		}
		downloadFile.setValue(fileName);
		downloadFile.setStyle("color:blue");
	}
	
	public void onClick$download() {
		String reportPath =Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/report")
		+ File.separator+xm.getKyId() + File.separator + path;
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
	
	public void onClick$back() {
		this.detach();
	}

}
