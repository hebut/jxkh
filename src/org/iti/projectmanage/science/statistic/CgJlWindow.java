package org.iti.projectmanage.science.statistic;

import java.util.List;
import org.iti.gh.entity.GhCg;
import org.iti.projectmanage.science.member.service.ProjectMemberService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTUser;

public  class CgJlWindow extends Window implements AfterCompose {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	WkTUser user; 
	Listbox hjcg;
	ProjectMemberService projectmemberService;
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);	
		user=(WkTUser)Sessions.getCurrent().getAttribute("user");
		List jllist=(List)Executions.getCurrent().getArg().get("jllist");
		if(jllist!=null&&jllist.size()>0){
			hjcg.setModel(new ListModelList(jllist));
		}else{
			hjcg.setModel(new ListModelList());
		}
		hjcg.setItemRenderer(new ListitemRenderer(){
			public void render(Listitem arg0,Object arg1){
				GhCg cg=(GhCg)arg1;
				Listcell c0 = new Listcell(arg0.getIndex() + 1 + "");
				Listcell c1 = new Listcell(cg.getKyMc());
				Listcell c2 = new Listcell(cg.getKySj());
				Listcell c3 = new Listcell(cg.getKyHjmc());
				Listcell c4 = new Listcell(cg.getKyDj());
				Listcell c5 = new Listcell(cg.getKyPrizeper());
				arg0.appendChild(c1);arg0.appendChild(c2);arg0.appendChild(c3);
				arg0.appendChild(c4);arg0.appendChild(c5);
			}
		});
	}
	
	
}