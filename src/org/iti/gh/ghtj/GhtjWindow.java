package org.iti.gh.ghtj;

import java.util.HashMap;
import java.util.Map;

import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Tabpanels;

public class GhtjWindow extends BaseWindow {

	Tabpanels panels;
	org.iti.gh.ghtj.GhqkdctjTPanel qkPanel;
	GhtjPanel hzPanel;
//	public  void onClick$exportQkdt() throws FileNotFoundException, IOException, InterruptedException{ 
//		String fpath=qkPanel.exportExcel();
//		downloadFile(fpath,"情况调查表统计.xls");
//	} 

	@Override
	public void initShow() {
		
	}
	@Override
	public void initWindow() {
		Map userlist=new HashMap();
		userlist.put("xyuserrole",getXyUserRole());
		qkPanel=(GhqkdctjTPanel)Executions.createComponents("/admin/xkgl/ghtj/xkjstj/tpanel1.zul", panels, userlist);
		hzPanel=(GhtjPanel) Executions.createComponents("/admin/xkgl/ghtj/xkjstj/tpanel2.zul", panels, userlist); 
	}

}
