package org.iti.projectmanage.science.statistic;

import java.util.List;

import org.iti.gh.entity.GhHylw;
import org.iti.gh.entity.GhJsxm;
import org.iti.gh.entity.GhQklw;
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

public  class ArticalWindow extends Window implements AfterCompose {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	WkTUser user; 
	Listbox lwlist;

	ProjectMemberService projectmemberService;
	GhHylw hylw;
	GhQklw qklw;
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);	
		user=(WkTUser)Sessions.getCurrent().getAttribute("user");
		List list=(List)Executions.getCurrent().getArg().get("hylwlist");
		if(list!=null&&list.size()>0){
			lwlist.setModel(new ListModelList(list));
		}else{
			lwlist.setModel(new ListModelList());
		}
		lwlist.setItemRenderer(new ListitemRenderer(){
			public void render(Listitem arg0,Object arg1){
				if(arg1 instanceof GhHylw)
				{
				GhHylw hylw=(GhHylw)arg1;
				Listcell c1=new Listcell(arg0.getIndex()+1+"");
				Listcell c2=new Listcell(hylw.getLwMc());
				Listcell c3=new Listcell(hylw.getLwFbsj());
				Listcell c4=new Listcell(hylw.getLwKw());
				Listcell c5=new Listcell(hylw.getLwAll());
				arg0.appendChild(c1);arg0.appendChild(c2);arg0.appendChild(c3);
				arg0.appendChild(c4);arg0.appendChild(c5);
				}
				else if(arg1 instanceof GhQklw)
				{
					GhQklw qklw=(GhQklw)arg1;
					Listcell c1=new Listcell(arg0.getIndex()+1+"");
					Listcell c2=new Listcell(qklw.getLwMc());
					Listcell c3=new Listcell(qklw.getLwFbsj());
					Listcell c4=new Listcell(qklw.getLwKw());
					Listcell c5=new Listcell(qklw.getLwAll());
					arg0.appendChild(c1);arg0.appendChild(c2);arg0.appendChild(c3);
					arg0.appendChild(c4);arg0.appendChild(c5);
				}
				
				
				
			}
		});
	}
	
	
}