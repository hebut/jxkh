package org.iti.kyjf.gljs;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.jxgl.ui.listbox.YearListbox;
import org.iti.kyjf.entity.Zbteacher;
import org.iti.kyjf.service.ZbteacherService;
import org.iti.xypt.entity.Teacher;
import org.iti.xypt.service.TeacherService;
import org.iti.xypt.service.XyUserRoleService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.entity.WkTUser;

public class GljfjsWindow extends BaseWindow {

	TeacherService teacherService;
	ZbteacherService  zbteacherService;
	Listbox teacherlist;
	YearListbox yearlist;
	XyUserRoleService xyUserRoleService;
	WkTUser user=(WkTUser)Sessions.getCurrent().getAttribute("user");
	Textbox nameSearch,tnoSearch;
	@Override
	public void initShow() {
//		initWindow();
		teacherlist.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				final Zbteacher teacher=(Zbteacher)arg1;
				arg0.setValue(teacher);
				Listcell c0=new Listcell(arg0.getIndex()+1+"");
				Listcell c1=new Listcell(teacher.getUser().getKuLid());
				Listcell c2=new Listcell(teacher.getUser().getKuName());
				Listcell c3=new Listcell(teacher.getTeacher().getTitle().getTiName());
				Listcell c4=new Listcell(teacher.getTeacher().getXiDept(getSchoolDept().getKdId()));
				Listcell c5=new Listcell(teacher.getTeacher().getYuDept(getSchoolDept().getKdId()));
				Listcell c6=new Listcell();
				InnerButton bt1=new InnerButton("删除");
				bt1.addEventListener(Events.ON_CLICK, new EventListener(){

					public void onEvent(Event arg0) throws Exception {
						if(Messagebox.show("您确定要删除该教师吗？", "提示", Messagebox.OK|Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.OK){
							zbteacherService.delete(teacher);
							Messagebox.show("删除成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
							onClick$query();
						}
					}
					
				});
				c6.appendChild(bt1);
				arg0.appendChild(c0);arg0.appendChild(c1); arg0.appendChild(c2);
				arg0.appendChild(c3);arg0.appendChild(c4); arg0.appendChild(c5);
				arg0.appendChild(c6);
			}
			
		});

	}

	@Override
	public void initWindow() {
		yearlist.initCqYearlist(getXyUserRole().getDept().getKdSchid());
		Date now=new Date();
		List tlist=zbteacherService.findByYear(now.getYear()+1900,getXyUserRole().getKdId(),tnoSearch.getValue(),nameSearch.getValue());
		if(tlist!=null){
			teacherlist.setModel(new ListModelList(tlist));
		}else{
			teacherlist.setModel(new ListModelList());
		}

	}
	
	public void onClick$query(){
		Integer year;
		if(yearlist.getSelectedItem()!=null){
			year=Integer.parseInt(yearlist.getSelectedItem().getLabel());
		}else{
			Date now=new Date();
			year=now.getYear()+1900;
		}
		List tlist=zbteacherService.findByYear(year,getXyUserRole().getKdId(),tnoSearch.getValue(),nameSearch.getValue());
		if(tlist!=null){
			teacherlist.setModel(new ListModelList(tlist));
		}else{
			teacherlist.setModel(new ListModelList());
		}
		
	}
	public void onClick$addTeacher(){
		if(yearlist.getSelectedItem()==null){
			try {
				Messagebox.show("请选择有效的年份！", "提示",Messagebox.OK , Messagebox.EXCLAMATION);
			} catch (InterruptedException e) {
				System.out.println("年份");
			}
			return;
		}		
		Map arg=new HashMap();
		final Integer year=Integer.parseInt(yearlist.getSelectedItem().getLabel());
		arg.put("year", year);
		arg.put("xyuserrole",getXyUserRole());
//		System.out.println(Teacher.getRoleId( getSchoolDept().getKdId())+"---");
		arg.put("role", (WkTRole) xyUserRoleService.get(WkTRole.class, Teacher.getRoleId( getSchoolDept().getKdId())));
		final AddTeacherWindow win=(AddTeacherWindow)Executions.createComponents("/admin/kyjf/gljs/addteacher.zul", null, arg);
	    win.setTitle("管理指标分解人员");
		win.doHighlighted();
		win.initWindow();
		win.addEventListener(Events.ON_CHANGE, new EventListener(){

			public void onEvent(Event arg0) throws Exception {
				List slist=win.getSelectUser();
				 for(int i=0;i<slist.size();i++){
					 Teacher t=(Teacher)slist.get(i);
					 Zbteacher Zt=new Zbteacher();
					 Zt.setKuId(t.getKuId());
					 Zt.setKdId(getXyUserRole().getKdId());
					 Zt.setZtYear(year);
					 zbteacherService.save(Zt);
					 }
				 win.detach();
				onClick$query();
			}
			
		});
	
	}

}
