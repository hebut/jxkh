package org.iti.gh.glfzfzr;

import java.util.List;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.entity.GhFxfz;
import org.iti.gh.entity.GhFxfzId;
import org.iti.gh.entity.GhYjfx;
import org.iti.gh.service.UserfxfzService;
import org.iti.gh.service.YjfxService;
import org.iti.xypt.entity.Teacher;
import org.iti.xypt.entity.XyUserrole;
import org.iti.xypt.entity.XyUserroleId;
import org.iti.xypt.service.MessageService;
import org.iti.xypt.service.XyUserRoleService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

public class GlfxfzrWindow extends BaseWindow {
	Listbox teacherlist;
	Long zyfzRoleId;
	UserService userService;
	UserfxfzService userfxfzService;
	XyUserRoleService xyUserRoleService;
	MessageService messageService;
	YjfxService yjfxService;
	@Override
	public void initShow() {

		teacherlist.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				 final GhYjfx yjfx=(GhYjfx)arg1;
				Listcell c1 = new Listcell();
				c1.setLabel(arg0.getIndex() + 1 + "");
				Listcell c2 = new Listcell(yjfx.getGyName());
				Listcell c3 = new Listcell();
				Listcell c4 = new Listcell();
				final List fxfzr=userfxfzService.findByGyid(yjfx.getGyId());
				InnerButton bt1 = new InnerButton("添加");
				InnerButton bt2 = new InnerButton(setLabelValue(fxfzr));
				if(fxfzr.size()!=0){
				c3.appendChild(bt2);
				}else{
					c3.setLabel("暂无");
				}
				c4.appendChild(bt1);
				bt2.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						TzfxfzrWindow win=(TzfxfzrWindow)Executions.createComponents("/admin/personal/data/glfxfzr/tzfxfzr.zul",null,null);
						win.setGyid(yjfx.getGyId());
						win.setSch(getSchoolDept());
						win.setXyrole(getXyUserRole());
						win.setRole((WkTRole) xyUserRoleService.get(WkTRole.class, Teacher.getRoleId(getSchoolDept().getKdId())));
						win.setZyfzRoleId(zyfzRoleId);
						win.doHighlighted();
						win.initWindow();	
						win.addEventListener(Events.ON_CLOSE, new EventListener(){
							public void onEvent(Event arg0) throws Exception {
								initWindow();
							}});
					}});
				bt1.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						final SelFzrWindow w=(SelFzrWindow)Executions.createComponents("/admin/personal/data/glfxfzr/addtea.zul",null,null);
						w.setGyid(yjfx.getGyId());
						w.setRootKdId(getSchoolDept().getKdId());
						w.setRole((WkTRole) xyUserRoleService.get(WkTRole.class, Teacher.getRoleId(getSchoolDept().getKdId())));
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
										 fxfzid.setGyId(yjfx.getGyId());
										 fxfzid.setKuId(t.getKuId());
										 fxfz.setId(fxfzid);
										 xyUserRoleService.save(fxfz);										 
										 if(xyr==null){
											 xyr=new XyUserrole(xyrId,getXyUserRole().getKdId());
											 xyUserRoleService.saveXyUserrole(xyr);
										 }else{
											 xyr.setKdId(getXyUserRole().getKdId());
											 messageService.saveXyNUrByXyUserrole(xyr);
										 }
										 				
									 }
									 initWindow();
									 w.detach();
									}});
					}
				});
			
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
				
			}
		});
	

	}

	@Override
	public void initWindow() {
		zyfzRoleId = xyUserRoleService.getRoleId("方向负责人", getSchoolDept().getKdId());
		teacherlist.setModel(new ListModelList(yjfxService.findByKdid(getXyUserRole().getKdId())));

	}
	public String setLabelValue(List ulist){
		StringBuffer lb=new StringBuffer("");
		for(int i=0;i<ulist.size();i++){
			GhFxfz ghyjfx=(GhFxfz)ulist.get(i);
			WkTUser u=(WkTUser)userService.get(WkTUser.class, ghyjfx.getId().getKuId());
			lb.append(u.getKuName());
			if(i<ulist.size()-1){
				lb.append(", ");
			}
		}
		return lb.toString();
		
	}
}
