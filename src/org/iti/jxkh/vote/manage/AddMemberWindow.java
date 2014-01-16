package org.iti.jxkh.vote.manage;

import java.util.ArrayList;
import java.util.List;

import org.iti.jxkh.entity.JXKH_AppraisalMember;
import org.iti.jxkh.service.AuditConfigService;
import org.iti.jxkh.service.VoteConfigService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;

public class AddMemberWindow extends Window implements AfterCompose {
	private static final long serialVersionUID = -2619948856660042952L;
	private VoteConfigService voteConfigService;
	private AuditConfigService auditConfigService;
	private Listbox deptlist, listbox1, listbox2;
	private Textbox perName;
	private Textbox perId;
	public short type;//1表示投票者，0表示投票对象
	private List<WkTUser> ulist = new ArrayList<WkTUser>();
	private List<WkTUser> voteList = new ArrayList<WkTUser>();
	private List<WkTUser> voterList = new ArrayList<WkTUser>();
	private List<WkTUser> userlist = new ArrayList<WkTUser>();	
	public List<WkTUser> getVoteList() {
		return voteList;
	}

	public void setVoteList(List<WkTUser> voteList) {
		this.voteList = voteList;
	}

	public List<WkTUser> getVoterList() {
		return voterList;
	}

	public void setVoterList(List<WkTUser> voterList) {
		this.voterList = voterList;
	}

	public List<WkTUser> getUlist() {
		return ulist;
	}

	public void setUlist(List<WkTUser> ulist) {
		this.ulist = ulist;
	}

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
	    deptlist.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				WkTDept dept = (WkTDept) arg1;
				arg0.setLabel(dept.getKdName());
				arg0.setValue(dept);
			}
		});
		listbox1.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				WkTUser user = (WkTUser) arg1;
				Listcell c0 = new Listcell();
				Listcell c1 = new Listcell(user.getKuLid());
				Listcell c2 = new Listcell(user.getKuName());
				Listcell c3 = new Listcell(deptlist.getSelectedItem().getLabel());
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.setValue(arg1);
			}
		});
		listbox2.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				WkTUser user = (WkTUser) arg1;
				Listcell c0 = new Listcell();
				Listcell c1 = new Listcell(user.getKuLid());
				Listcell c2 = new Listcell(user.getKuName());
				Listcell c3 = new Listcell(user.getDept().getKdName());
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.setValue(arg1);
			}
		});
	}

	public void initWindow(List<WkTUser> userlist,short type) {
		this.type = type;
		if(userlist != null) {
			this.userlist = userlist;
			listbox2.setModel(new ListModelList(userlist));
		}	
		deptlist.setModel(new ListModelList(voteConfigService.findManageDept()));
		deptlist.setSelectedIndex(0);
	}

	public void initWindow1(List<WkTUser> userlist,short type) {
		this.type = type;
		if(userlist != null) {
			this.userlist = userlist;
			listbox2.setModel(new ListModelList(userlist));
		}	
		deptlist.setModel(new ListModelList(voteConfigService.findAllDept()));
		deptlist.setSelectedIndex(0);
	}
	public void onClick$view() {
		WkTDept dept = (WkTDept) deptlist.getSelectedItem().getValue();
		/*如果JXKH_AppraisalMember中有再去WKTUser中查询**/
		//首先去JXKH_AppraisalMember中查询
		List<JXKH_AppraisalMember>managerList = auditConfigService.findManagerPeo(perId.getValue(), perName.getValue(), dept.getKdId());
		List<WkTUser> checkedmanagerList = new ArrayList<WkTUser>();	
		if(managerList.size()>0){
			for(int i=0;i < managerList.size();i++){
				List<WkTUser> manage =  auditConfigService.findUser(managerList.get(i).getKuId());
				manage.get(0).getKuName();
				checkedmanagerList.add(manage.get(0));
			}
		}
		listbox1.setModel(new ListModelList(checkedmanagerList) );
		checkedmanagerList.clear();
	}
	public void onClick$add() {
		Object[] items = listbox1.getSelectedItems().toArray();
		for (int i = 0; i < items.length; i++) {
			Listitem item = (Listitem) items[i];
			if (listbox2.getItemCount() == 0) {
				listbox2.appendChild(item);
			} else {
				Listitem topItem = (Listitem) listbox2.getItems().get(0);
				listbox2.insertBefore(item, topItem);
			}
		}
	}

	public void onClick$delete() {
		Object[] items = listbox2.getSelectedItems().toArray();
		for (int i = 0; i < items.length; i++) {
			Listitem item = (Listitem) items[i];
			if (listbox1.getItemCount() == 0) {
				listbox1.appendChild(item);
			} else {
				Listitem topItem = (Listitem) listbox1.getItems().get(0);
				listbox1.insertBefore(item, topItem);
			}
		}
	}

	public void onClick$submit() {
		ulist.clear();
		Object[] items = listbox2.getItems().toArray();
		for (int i = 0; i < items.length; i++) {
			Listitem item = (Listitem) items[i];
			ulist.add((WkTUser) item.getValue());
		}
		if(type == 0) {
			voteList.addAll(ulist);
		}else {
			voterList.addAll(ulist);
		}
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	public void onClick$close() {
		this.detach();
	}
}
