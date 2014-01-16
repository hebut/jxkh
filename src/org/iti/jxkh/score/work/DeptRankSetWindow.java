package org.iti.jxkh.score.work;

import java.util.ArrayList;
import java.util.List;

import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxgl.entity.JxYear;
import org.iti.jxkh.entity.JXKH_AuditResult;
import org.iti.jxkh.service.AuditConfigService;
import org.iti.jxkh.service.AuditResultService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTDept;

public class DeptRankSetWindow extends Window implements AfterCompose {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Listbox deptlist;
	private YearListbox yearList;
	
	private AuditConfigService auditConfigService;
	private AuditResultService auditResultService;
	
	private List<Textbox> idList = new ArrayList<Textbox>();
	List<JXKH_AuditResult> resultList = new ArrayList<JXKH_AuditResult>();
	private String year;

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);	
		idList.clear();
		
		initWindow();
		
		deptlist.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				JXKH_AuditResult ja = (JXKH_AuditResult)arg1;
				WkTDept dept = (WkTDept) auditConfigService.loadById(WkTDept.class, ja.getKdId());
				arg0.setValue(ja);
				Listcell c0 = new Listcell(arg0.getIndex()+1+"");
				Listcell c1 = new Listcell(dept.getKdName());
				Listcell c2 = new Listcell(ja.getArLevel()+"");
				Listcell c3 = new Listcell();
				Textbox rank = new Textbox();
				rank.setWidth("10px");
				rank.setValue(ja.getArLevel().toString());
				String tid = "t"+arg0.getIndex()+1;
				rank.setId(tid);
				idList.add(rank);
				Label label = new Label("档");
				c3.appendChild(rank);
				c3.appendChild(label);
				arg0.appendChild(c0);arg0.appendChild(c1);
				arg0.appendChild(c2);arg0.appendChild(c3);			
			}
		});
	}
	
	private void initWindow() {
		yearList.initAnyyearlist();
	}
	
	public void onClick$view() throws InterruptedException {
		if(yearList.getSelectedIndex() == 0 ) {
			Messagebox.show("请选择年份！");
			return;
		}
		JxYear jy = (JxYear)yearList.getSelectedItem().getValue();
		year = jy.getYears();
		resultList.clear();
		idList.clear();
		resultList = auditResultService.findDept(year);
		deptlist.setModel(new ListModelList(resultList));
	}
	
	public void onClick$submit() throws InterruptedException {
		for(int j=0;j<resultList.size();j++) {
			JXKH_AuditResult ar = resultList.get(j);
			Textbox t = idList.get(j);
			if(t.getValue() != null || !"".equals(t.getValue())) {
				ar.setArLevel(Short.valueOf(t.getValue()));
			}	
			auditResultService.update(ar);			
		}
		
		try {
			Messagebox.show("保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		onClick$view();
		
	}
	
	public void onClick$reset() {
		for(int i=0;i<idList.size();i++) {
			Textbox t = idList.get(i);
			t.setValue("");
		}
	}
}
