package org.iti.jxkh.common;

import java.util.ArrayList;
import java.util.List;

import org.iti.jxkh.entity.JXKH_VoteConfig;
import org.iti.jxkh.entity.JXKH_VoteResult;
import org.iti.jxkh.service.VoteConfigService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTUser;

public class ShowWindow extends Window implements AfterCompose {
	private static final long serialVersionUID = -2619948856660042952L;
	
	private Listbox voteList;
	JXKH_VoteConfig vc;
	
	VoteConfigService voteConfigService;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		voteList.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				JXKH_VoteResult jv = (JXKH_VoteResult)arg1;
				arg0.setValue(jv);
				Listcell c0 = new Listcell(arg0.getIndex()+1+"");
				Listcell c1 = new Listcell(jv.getUser().getStaffId());
				Listcell c2 = new Listcell(jv.getUser().getKuName());
				Listcell c3 = new Listcell(jv.getUser().getKuSex().equals(WkTUser.SEX_MAN)?"ÄÐ":"Å®");
				Listcell c4 = new Listcell(jv.getUser().getDept().getKdName());
				arg0.appendChild(c0);arg0.appendChild(c1);
				arg0.appendChild(c2);arg0.appendChild(c3);arg0.appendChild(c4);
			}
		});
	}
	
	public void initWindow(JXKH_VoteConfig vc) {
		this.vc = vc;		
		List<JXKH_VoteConfig> vlist = voteConfigService.findConfig(vc.getVcId());
		if(vlist.size() != 0) {
			JXKH_VoteConfig result = vlist.get(0);
			voteList.setModel(new ListModelList(result.getVcObject()));			
		}		
	}	
	
	public void onClick$close() {
		this.detach();
	}
}
