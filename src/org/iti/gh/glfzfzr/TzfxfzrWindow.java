package org.iti.gh.glfzfzr;

import java.util.Iterator;
import java.util.List;
import java.util.Set;


import org.iti.gh.entity.GhFxfz;
import org.iti.gh.entity.GhFxfzId;
import org.iti.gh.entity.GhYjfx;
import org.iti.gh.service.UserfxfzService;
import org.iti.gh.service.UseryjfxService;
import org.iti.xypt.entity.Teacher;
import org.iti.xypt.entity.XyUserrole;
import org.iti.xypt.entity.XyUserroleId;
import org.iti.xypt.service.MessageService;
import org.iti.xypt.service.TeacherService;
import org.iti.xypt.service.XyUserRoleService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTRole;

public class TzfxfzrWindow extends Window implements AfterCompose {
Listbox fxfzrlist;
Listheader gradeThrLabel,gradeTwoLabel;
TeacherService teacherService;
UserfxfzService userfxfzService;
XyUserRoleService xyUserRoleService;
MessageService messageService;
UseryjfxService useryjfxService;
Long gyid;
WkTDept sch;
Long zyfzRoleId;
XyUserrole xyrole;
WkTRole role;

public void setXyrole(XyUserrole xyrole) {
	this.xyrole = xyrole;
}
public void setZyfzRoleId(Long zyfzRoleId) {
	this.zyfzRoleId = zyfzRoleId;
}
public void setSch(WkTDept sch) {
	this.sch = sch;
}
	
	public void setRole(WkTRole role) {
	this.role = role;
}
	public void setGyid(Long gyid) {
	this.gyid = gyid;
}
	
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		initShow();
	}
	public void initShow() {
		fxfzrlist.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				GhFxfz fxfz=(GhFxfz)arg1;
				final Listitem it = arg0;
				arg0.setValue(arg1);
				Teacher tea=(Teacher)teacherService.get(Teacher.class, fxfz.getId().getKuId());
				Listcell c1 = new Listcell();
				c1.setLabel(arg0.getIndex() + 1 + "");
				// 指导教师编号
				Listcell c2 = new Listcell(tea.getThId());
				// 通过用户编号（KUID）得到用户对象（WKTUSER）
				Listcell c3 = new Listcell(tea.getUser().getKuName());
				Listcell c4 = new Listcell(tea.getTitle().getTiName());
				Listcell c5 = new Listcell(tea.getXiDept(tea.getKdId()));
				Listcell c6 = new Listcell(tea.getYuDept(tea.getKdId()));
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
				arg0.appendChild(c5);
				arg0.appendChild(c6);
			}});
	}
	public void initWindow() {
		gradeTwoLabel.setLabel(sch.getGradeName(WkTDept.GRADE_YUAN));
		gradeThrLabel.setLabel(sch.getGradeName(WkTDept.GRADE_XI));
		fxfzrlist.setModel(new ListModelList(userfxfzService.findByGyid(gyid)));

	}
	public void onClick$submit() {
		
		final SelFzrWindow w=(SelFzrWindow)Executions.createComponents("/admin/personal/data/glfxfzr/addtea.zul",null,null);
		w.setGyid(gyid);
		w.setRootKdId(sch.getKdId());
		w.setRole((WkTRole) xyUserRoleService.get(WkTRole.class, Teacher.getRoleId(sch.getKdId())));
		w.setTitle("选择方向负责人");
		w.doHighlighted();
		w.initWindow();
		w.addEventListener(Events.ON_CHANGE, new EventListener(){
				public void onEvent(Event arg0) throws Exception {
					List slist=w.getSelectUser();
					//新增方向负责人
					 for(int i=0;i<slist.size();i++){
						 Teacher t=(Teacher)slist.get(i);
						 XyUserroleId xyrId=new XyUserroleId(zyfzRoleId,t.getKuId());
						 XyUserrole xyr=(XyUserrole) xyUserRoleService.get(XyUserrole.class, xyrId);										 
						 GhFxfz fxfz=new GhFxfz();
						 GhFxfzId fxfzid=new GhFxfzId();
						 fxfzid.setGyId(gyid);
						 fxfzid.setKuId(t.getKuId());
						 fxfz.setId(fxfzid);
						 xyUserRoleService.save(fxfz);										 
						 if(xyr==null){
							 xyr=new XyUserrole(xyrId,xyrole.getKdId());
							 xyUserRoleService.saveXyUserrole(xyr);
						 }else{
							 xyr.setKdId(xyrole.getKdId());
							 messageService.saveXyNUrByXyUserrole(xyr);
						 }
						 				
					 }
					 initWindow();
					 w.detach();
					
					}});
		Events.postEvent(Events.ON_CHANGE, this, null);
	}
	public void onClick$delete() throws InterruptedException {
		if (Messagebox.show("确定删除已选的方向负责人吗?", "提示", 
				Messagebox.OK|Messagebox.CANCEL, Messagebox.QUESTION) == 1){
			Set suser = fxfzrlist.getSelectedItems();
			Iterator it = suser.iterator();
			while (it.hasNext()) {
				Listitem sitem = (Listitem) it.next();
				GhFxfz st = (GhFxfz) sitem.getValue();
				userfxfzService.delete(st);
				XyUserroleId xyrId=new XyUserroleId(zyfzRoleId,st.getId().getKuId());
				XyUserrole xyr=(XyUserrole) xyUserRoleService.get(XyUserrole.class, xyrId);
				List bslist=userfxfzService.findFxfzByGyid(sch.getKdId(), st.getId().getKuId());
				if(bslist.size()>0){
					//删除指导教师全部通知关系
					messageService.deleteXyNUrByXyUserrole(xyr);
					for(int i=0;i<bslist.size();i++){
					//逐个添加教师通知关系
						GhFxfz bst=(GhFxfz) bslist.get(i);
						GhYjfx yj=(GhYjfx)useryjfxService.get(GhYjfx.class,bst.getId().getGyId());
					   xyr.setKdId(yj.getKdId());
					   messageService.saveXyNUrByXyUserrole(xyr);
					}
				}else{
					xyUserRoleService.deleteXyUserrole(xyr);
				}
				 
			}
			initWindow();
		}
		
		Events.postEvent(Events.ON_CHANGE, this, null);
	}
}
