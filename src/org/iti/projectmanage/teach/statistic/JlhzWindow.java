package org.iti.projectmanage.teach.statistic;

import java.util.List;

import org.iti.gh.entity.GhFmzl;
import org.iti.gh.entity.GhHylw;
import org.iti.gh.entity.GhJlhz;
import org.iti.gh.entity.GhJsxm;
import org.iti.gh.entity.GhJxbg;
import org.iti.gh.entity.GhQklw;
import org.iti.gh.entity.GhRjzz;
import org.iti.gh.entity.GhXm;
import org.iti.gh.entity.GhXshy;
import org.iti.gh.service.JlhzService;
import org.iti.gh.service.XshyService;
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

public  class JlhzWindow extends Window implements AfterCompose {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	WkTUser user; 
	Listbox xshy,jxbg,jlhz;
	ProjectMemberService projectmemberService;
	JlhzService jlhzService;
	XshyService xshyService;
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);	
		user=(WkTUser)Sessions.getCurrent().getAttribute("user");
		List list=(List)Executions.getCurrent().getArg().get("jlhzlist");
		Short type=(Short)Executions.getCurrent().getArg().get("type");
		if(type==Short.parseShort("2"))
		{
			jxbg.setModel(new ListModelList(list));
			jxbg.setVisible(true);
			jlhz.setVisible(false);
			xshy.setVisible(false);
			jxbg.setItemRenderer(new ListitemRenderer(){
				public void render(Listitem arg0,Object arg1){
					GhJxbg jx=(GhJxbg)arg1;
					Listcell c1=new Listcell(arg0.getIndex()+1+"");
					Listcell c2=new Listcell(jx.getJxJxmc());
					Listcell c3=new Listcell(jx.getJxHymc());
					Listcell c4=new Listcell(jx.getJxSj());
					Listcell c5=new Listcell(jx.getJxBgmc());
					Listcell c6=new Listcell(jx.getUser().getKuName());
					arg0.appendChild(c1);arg0.appendChild(c2);arg0.appendChild(c3);
					arg0.appendChild(c4);arg0.appendChild(c5);arg0.appendChild(c6);
          		}
			});
			
		}
		else if(type==Short.parseShort("1"))
		{
			xshy.setModel(new ListModelList(list));
			xshy.setVisible(true);
			jxbg.setVisible(false);
			jlhz.setVisible(false);
			xshy.setItemRenderer(new ListitemRenderer(){
				public void render(Listitem arg0,Object arg1){
					String mc=(String)arg1;
					List xslist=xshyService.findByMc(mc, GhXshy.IFCJ_YES,null);
					GhXshy xs=(GhXshy)xslist.get(0);
					Listcell c1=new Listcell(arg0.getIndex()+1+"");
					Listcell c2=new Listcell(xs.getHyMc());
					Listcell c3=new Listcell(xs.getHyTheme());
					Listcell c4=new Listcell(xs.getHySj());
					Listcell c5=new Listcell(xs.getHyZrs()+"");
					Listcell c6=new Listcell(xs.getHyJwrs()+"");
					
					arg0.appendChild(c1);arg0.appendChild(c2);arg0.appendChild(c3);
					arg0.appendChild(c4);arg0.appendChild(c5);arg0.appendChild(c6);
          		}
			});
		}

		else if(type==Short.parseShort("3"))
		{
			jlhz.setModel(new ListModelList(list));
			jlhz.setVisible(true);
			jxbg.setVisible(false);
			xshy.setVisible(false);
			jlhz.setItemRenderer(new ListitemRenderer(){
				public void render(Listitem arg0,Object arg1){
					String mc=(String)arg1;
					List hzlist=jlhzService.findByMc(mc, GhXshy.IFCJ_YES,null);
					GhJlhz jl=(GhJlhz)hzlist.get(0);
					Listcell c1=new Listcell(arg0.getIndex()+1+"");
					Listcell c2=new Listcell(jl.getHzMc());
					Listcell c3=new Listcell(jl.getHzSj());
					Listcell c4=new Listcell(jl.getHzDx());
					arg0.appendChild(c1);arg0.appendChild(c2);arg0.appendChild(c3);
					arg0.appendChild(c4);
          		}
			});
		}

	}
	
}