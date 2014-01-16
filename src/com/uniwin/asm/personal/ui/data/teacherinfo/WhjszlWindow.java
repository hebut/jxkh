package com.uniwin.asm.personal.ui.data.teacherinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.xypt.service.TeacherService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.uniwin.asm.personal.ui.data.TeacherRegister;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.service.UserService;

public class WhjszlWindow extends Window implements AfterCompose {

	Listbox deplist;
	Listbox teacherlist;
	Textbox teaname, teano;
	WkTUser user;
	TeacherService teacherService;
	DepartmentService departmentService;
	UserService userService;

	public void initShow() {
		
		
		deplist.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				WkTDept dep=(WkTDept)arg1;
				arg0.setValue(dep);
				arg0.setLabel(dep.getKdName());
			}
			
		});
		teacherlist.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				
				final WkTUser user=(WkTUser)arg1;
				arg0.setValue(user);
				Listcell c0=new Listcell(arg0.getIndex()+1+"");
				Listcell c1=new Listcell(user.getKuLid());
				Listcell c2=new Listcell(user.getKuName());
				Listcell c3=new Listcell(user.getDept().getKdName());
				Listcell c4=new Listcell();
				InnerButton ib=new InnerButton("²é¿´");
				c4.appendChild(ib);
				ib.addEventListener(Events.ON_CLICK, new EventListener(){

					public void onEvent(Event arg0) throws Exception {
						Map userlist=new HashMap();
						userlist.put("user", user);
						TeacherRegister tr=(TeacherRegister)Executions.createComponents("/admin/personal/data/teacherregister/index.zul", null, userlist);
						tr.setClosable(true);
						tr.setWidth("800px");
					    tr.setButtonAble();
						tr.doHighlighted();
					}	
				});
				arg0.appendChild(c0);arg0.appendChild(c1);arg0.appendChild(c2);
				arg0.appendChild(c3);arg0.appendChild(c4);
				
			}
			
		});
	}

	public void initWindow() {
		List dlist=new ArrayList();
		WkTDept de = new WkTDept();
		de.setKdName("--ÇëÑ¡Ôñ--");
		de.setKdId(0L);
		dlist.add(de);
	    if(user.getDept().getKdLevel()==2){
		dlist.addAll(departmentService.getChildDepartment(user.getKdId(), WkTDept.QUANBU));
	    }else if(user.getDept().getKdLevel()==3){
	    	dlist.addAll(departmentService.getChildDepartment(user.getDept().getKdPid(), WkTDept.QUANBU));
	    }
		deplist.setModel(new ListModelList(dlist));
		deplist.setSelectedIndex(0);
		ReloadTealist(user.getKdId(),teaname.getValue(),teano.getValue());
	}
	
	public void ReloadTealist(Long kdid,String teaname,String teano){
//		System.out.println(kdid+teaname+teano);
		WkTDept dep=null;
		List dlist=new ArrayList();
		if(kdid!=0L){
			dep=(WkTDept)departmentService.get(WkTDept.class,kdid);
			if(dep!=null&&dep.getKdLevel()==2){
				dlist.add(dep);
				dlist.addAll(departmentService.getChildDepartment(kdid, WkTDept.QUANBU));
			}else if(dep!=null&&dep.getKdLevel()==3){
				WkTDept d=(WkTDept)departmentService.get(WkTDept.class,user.getDept().getKdPid());
				dlist.add(d);
				dlist.addAll(departmentService.getChildDepartment(user.getDept().getKdPid(), WkTDept.QUANBU));
			}
		}
		List tlist=userService.findByDlistAndTnameAndTno(dlist,teaname,teano);
		teacherlist.setModel(new ListModelList(tlist));
	}

	public void afterCompose() {
		Components.addForwards(this, this);
		Components.wireVariables(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
     	initShow();
		initWindow();
	}
	
	public void onClick$query(){
		Long kdid=((WkTDept)deplist.getSelectedItem().getValue()).getKdId();
		ReloadTealist(kdid,teaname.getValue(),teano.getValue());
	}
}
