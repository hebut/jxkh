package org.iti.jxkh.honour;

import java.util.ArrayList;
import java.util.List;

import org.iti.jxkh.entity.Jxkh_Honour;
import org.iti.jxkh.service.RychService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Window;

public class BatchAuditWindow extends Window implements AfterCompose {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Radio pass,out;
	private List<Jxkh_Honour> hlist = new ArrayList();
	
	private RychService rychService;
	
	
	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);		
		pass.setChecked(true);
	}
	
	public void iniWindow(List<Jxkh_Honour> hlist) {
		this.hlist = hlist;
	}
	
	public void onClick$submit() {
		if(hlist.size() != 0) {
			if(pass.isChecked()) {			
				for(int i=0;i<hlist.size();i++) {
					Jxkh_Honour honour = hlist.get(i);
					honour.setState(Jxkh_Honour.BUSI_PASS);
					rychService.update(honour);
				}			
				
			}else {
				for(int i=0;i<hlist.size();i++) {
					Jxkh_Honour honour = hlist.get(i);
					honour.setState(Jxkh_Honour.BUSI_OUT);
					rychService.update(honour);
				}	
			}
		}
		Events.postEvent(Events.ON_CHANGE, this, null);
	}
	
	public void onClick$close() {
		this.detach();
	}

}
