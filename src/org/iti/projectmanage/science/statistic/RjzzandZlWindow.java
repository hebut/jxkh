package org.iti.projectmanage.science.statistic;

import java.util.List;

import org.iti.gh.entity.GhFmzl;
import org.iti.gh.entity.GhHylw;
import org.iti.gh.entity.GhJsxm;
import org.iti.gh.entity.GhQklw;
import org.iti.gh.entity.GhRjzz;
import org.iti.gh.entity.GhXm;
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

public  class RjzzandZlWindow extends Window implements AfterCompose {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	WkTUser user; 
	Listbox rjzzlist,fmzllist;
	ProjectMemberService projectmemberService;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);	
		user=(WkTUser)Sessions.getCurrent().getAttribute("user");
		List list=(List)Executions.getCurrent().getArg().get("rzorzllist");
		if(list.get(0) instanceof GhRjzz)
		{
			rjzzlist.setModel(new ListModelList(list));
			rjzzlist.setVisible(true);
			fmzllist.setVisible(false);
			rjzzlist.setItemRenderer(new ListitemRenderer(){
				public void render(Listitem arg0,Object arg1){
					
					GhRjzz rjzz=(GhRjzz)arg1;
					Listcell c1=new Listcell(arg0.getIndex()+1+"");
					Listcell c2=new Listcell(rjzz.getRjName());
					Listcell c3=new Listcell(rjzz.getRjFirtime());
					Listcell c4=new Listcell(rjzz.getRjRegisno());
					Listcell c5=new Listcell(rjzz.getRjTime());
					Listcell c6 = new Listcell(rjzz.getRjSoftno());
					Listcell c7 = new Listcell(rjzz.getUser().getKuName());
					arg0.appendChild(c1);arg0.appendChild(c2);arg0.appendChild(c3);
					arg0.appendChild(c4);arg0.appendChild(c5);arg0.appendChild(c6);
					arg0.appendChild(c7); 
          		}
			});
			
		}
		else if(list.get(0) instanceof  GhFmzl)
		{
			fmzllist.setModel(new ListModelList(list));
			fmzllist.setVisible(true);
			rjzzlist.setVisible(false);
			fmzllist.setItemRenderer(new ListitemRenderer(){
				public void render(Listitem arg0,Object arg1){
					GhFmzl fm=(GhFmzl)arg1;
					Listcell c1=new Listcell(arg0.getIndex()+1+"");
					Listcell c2=new Listcell(fm.getFmMc());
					Listcell c3=new Listcell(fm.getFmSj());
					Listcell c4=new Listcell(fm.getFmSqh());
					Listcell c5=new Listcell(fm.getUser().getKuName());
					arg0.appendChild(c1);arg0.appendChild(c2);arg0.appendChild(c3);
					arg0.appendChild(c4);arg0.appendChild(c5);
          		}
			});
		}

	}
	
	
}