package com.uniwin.asm.personal.ui.data.teacherinfo;

import java.util.List;

import org.iti.gh.entity.GhHylw;
import org.iti.gh.entity.GhJslw;
import org.iti.gh.service.HylwService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Row;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Window;
import com.uniwin.framework.entity.WkTUser;

public class SelectHylwWindow extends Window implements AfterCompose {
	WkTUser user = (WkTUser) Sessions.getCurrent().getAttribute("user");;
	HylwService hylwService;
	Listbox lwList;
	Textbox lwName, kwName;
	GhHylw hylw;
	Short p1, p2;
    Row pl,list,hb;
    Label ll;
	public void setP1(Short p1) {
		this.p1 = p1;
	}

	public void setP2(Short p2) {
		this.p2 = p2;
	}

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
	}

	public GhHylw getHylw() {
		return hylw;
	}

	public void initShow() {
	}

	public void initWindow() {
		List lwlist = hylwService.findAllname(user.getKuId(), user.getKuName(), p1, p2);
		lwList.setModel(new ListModelList(lwlist));
		lwList.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				Object[] arg = (Object[]) arg1;
				Listcell c0 = new Listcell();
				GhHylw ghhylw = (GhHylw) hylwService.get(GhHylw.class, Long.parseLong(arg[1].toString()));
				Listcell c1 = new Listcell(ghhylw.getLwMc());
				Listcell c2 = new Listcell(ghhylw.getLwKw());
				Listcell c3 = new Listcell(ghhylw.getLwFbsj());
				Listcell c4 = new Listcell(ghhylw.getLwPages());
				Listcell c5 = new Listcell(ghhylw.getUser().getKuName());
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
				arg0.appendChild(c5);
				arg0.setValue(ghhylw);
			}
		});
	}

	public void onClick$query() {
		List lwlist = hylwService.findbyMcOrKw(user.getKuId(), user.getKuName(), p1, p2, lwName.getValue(), kwName.getValue());
		if(lwlist.size()==0){
			list.setVisible(false); hb.setVisible(false);
			pl.setVisible(true); 
			ll.setValue("没有符合条件的会议论文,请尝试选择其他条件......"); 
		}
		else{
			list.setVisible(true); hb.setVisible(true);
			pl.setVisible(false); 
		}
		lwList.setModel(new ListModelList(lwlist));
	}

	public void onClick$submit() {
		if (lwList.getSelectedCount() != 0) {
			hylw = (GhHylw) lwList.getSelectedItem().getValue();
			Events.postEvent(Events.ON_CHANGE, this, null);
		} else {
			onClick$close();
		}
	}

	public void onClick$close() {
		this.detach();
	}
}
