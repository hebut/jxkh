package org.iti.jxkh.common;

import java.util.ArrayList;
import java.util.List;

import org.iti.jxkh.entity.JXKH_VoteConfig;
import org.iti.jxkh.entity.JXKH_VoteResult;
import org.iti.jxkh.service.AuditResultService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTUser;

public class ShowVoterWindow extends Window implements AfterCompose {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Listbox voteList;
	JXKH_VoteConfig vc;	
	
	private AuditResultService auditResultService;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		voteList.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				WkTUser user = (WkTUser)arg1;
				arg0.setValue(user);
				Listcell c0 = new Listcell(arg0.getIndex()+1+"");
				Listcell c1 = new Listcell(user.getStaffId());
				Listcell c2 = new Listcell(user.getKuName());
				Listcell c3 = new Listcell(user.getKuSex().equals(WkTUser.SEX_MAN)?"ÄÐ":"Å®");
				Listcell c4 = new Listcell(user.getDept().getKdName());
				arg0.appendChild(c0);arg0.appendChild(c1);
				arg0.appendChild(c2);arg0.appendChild(c3);arg0.appendChild(c4);
			}
		});
	}
	
	public void initWindow(JXKH_VoteConfig vc) {
		this.vc = vc;
		String kuList = vc.getVcVoter();
		List<WkTUser> userList = new ArrayList<WkTUser>();
		String[] kuidList = kuList.split("-");
		for(int i=1;i<kuidList.length;i++) {
			if(kuidList[i] != null || !"".equals(kuidList[i])) {
				WkTUser user = (WkTUser) auditResultService.loadById(WkTUser.class, Long.valueOf(kuidList[i]));
				userList.add(user);
			}
		}
		voteList.setModel(new ListModelList(userList));		
	}
	
	public void onClick$close() {
		this.detach();
	}
}
