package org.iti.jxkh.score.work;

import java.util.ArrayList;
import java.util.List;

import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxkh.entity.JXKH_AuditResult;
import org.iti.jxkh.service.AuditConfigService;
import org.iti.jxkh.service.AuditResultService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTDept;

public class SetUpNumberWindow extends Window implements AfterCompose {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Listbox listbox;
	private YearListbox yearList;
	
	private List<Textbox> idList = new ArrayList<Textbox>();
	private AuditConfigService auditConfigService;
	private AuditResultService auditResultService;
	

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);	
		idList.clear();
		listbox.setItemRenderer(new ListitemRenderer() {
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
				Label label = new Label("µµ");
				c3.appendChild(rank);
				c3.appendChild(label);
				arg0.appendChild(c0);arg0.appendChild(c1);
				arg0.appendChild(c2);arg0.appendChild(c3);			
			}
		});
	}

}
