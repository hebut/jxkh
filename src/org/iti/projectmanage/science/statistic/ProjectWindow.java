package org.iti.projectmanage.science.statistic;

import java.util.List;

import org.iti.gh.entity.GhJsxm;
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

public  class ProjectWindow extends Window implements AfterCompose {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	WkTUser user; 
	Listbox xminfo;
	Toolbarbutton back;
	ProjectMemberService projectmemberService;
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);	
		user=(WkTUser)Sessions.getCurrent().getAttribute("user");
		List xmlist=(List)Executions.getCurrent().getArg().get("xmlist");
		if(xmlist!=null&&xmlist.size()>0){
			xminfo.setModel(new ListModelList(xmlist));
		}else{
			xminfo.setModel(new ListModelList());
		}
		xminfo.setItemRenderer(new ListitemRenderer(){
			public void render(Listitem arg0,Object arg1){
				GhXm xm=(GhXm)arg1;
				Listcell c1=new Listcell(arg0.getIndex()+1+"");
				Listcell c2=new Listcell(xm.getKyMc());
				Listcell c3=new Listcell(xm.getKyLy());
				Listcell c4=new Listcell(xm.getKyProman());
				Listcell c5=new Listcell(xm.getKyLxsj());
				Listcell c6=new Listcell(xm.getKyKssj());
				
				List member=projectmemberService.findByKyXmId(xm.getKyId());
				String mem="";
				if(member.size()!=0)
				{
				for(int i=0;i<member.size();i++)
				{
					GhJsxm jsxm=(GhJsxm) member.get(i);
					mem+=jsxm.getKyMemberName()+"¡¢";
				}
				}
				
				Listcell c7=new Listcell(mem.substring(0, mem.length()-1));
				arg0.appendChild(c1);arg0.appendChild(c2);arg0.appendChild(c3);
				arg0.appendChild(c4);arg0.appendChild(c5);arg0.appendChild(c6);
				arg0.appendChild(c7);
				
			}
		});
	}
	
	
}