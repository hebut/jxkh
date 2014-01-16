package org.iti.xypt.personal.infoCollect;

/**
 * <li>站点权限编辑组件，对应的是admin.content.website.websiteauthedit.zul
 * @author whm
 * @2010-7-19
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.iti.xypt.personal.infoCollect.entity.WkTExtractask;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Window;

import com.uniwin.common.util.IPUtil;
import com.uniwin.framework.common.listbox.DeptListbox;
import com.uniwin.framework.common.listbox.DeptRoleListbox;
import com.uniwin.framework.common.listbox.RoleListbox;
import com.uniwin.framework.common.listbox.WebsiteAuthListbox;
import com.uniwin.framework.entity.WkTAuth;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTMlog;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.AuthService;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.service.MLogService;
import com.uniwin.framework.service.RoleService;

public class TaskAuthEditWindow extends Window implements AfterCompose {

	private static final long serialVersionUID = 6646263082699757069L;
	WkTUser user;
	// 角色搜索组件
	Combobox ktrole;
	// 部门搜索组件
	Combobox ktdept;
	RoleListbox krole;
	// 角色数据访问接口
	RoleService roleService;
//	ChanelService chanelService;
	AuthService authService;
	DepartmentService departmentService;
	ListModel rlistModel;
	//当前用户部门列表
	List userDeptList;
	// 要编辑权限的任务列表
	List cList = new ArrayList();
	
	//角色列表组件	
	DeptRoleListbox rolelist;
	//组织机构选择组件
	DeptListbox deptlist;
	// 权限列表
	WebsiteAuthListbox hasAuth;
	//任务选择按钮组件，可以选择多个任务
	Toolbarbutton taskChoose;
	// 页面显示的任务名称
	Textbox ktname, authIp;
	// 当前权限
	Label curauth;	
	// 可是设置的权限
	Checkbox  managerAuth,auditAuth;
	// 设置权限的类型
	Radio totalSet, addSet;
	public static final Short check = 1, uncheck = 0;
	List rids, dids;
	//日志数据访问接口
	MLogService mlogService;
	WkTExtractask editTask;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		this.setTitle("编辑分类权限");
		user=(WkTUser)Sessions.getCurrent().getAttribute("user");
		userDeptList=(List)Sessions.getCurrent().getAttribute("userDeptList");

		// 当选中列表框项时，底下角色自动选中
		ktrole.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event event) throws Exception {
				Comboitem it = ktrole.getSelectedItem();
				if (it != null) {
					WkTRole sr = (WkTRole) it.getValue();
					setRoleListBoxSelected(sr.getKrId());
				}
			}

		});
		

		// 选择设置权限的部门
		ktdept.addEventListener(Events.ON_SELECT, new EventListener() {

			public void onEvent(Event event) throws Exception {
				Comboitem it = ktdept.getSelectedItem();
				if (it != null) {
					Long did = (Long) it.getValue();
					setDeptListBoxSelected(did);
				}

			}
		});

		// 分类选择按钮触发的事件，点击弹出选择分类及其中任务的窗口组件
		taskChoose.addEventListener(Events.ON_CLICK, new EventListener() {
			public void onEvent(Event event) throws Exception {
				TaskAuditMulSelecteWindow w = (TaskAuditMulSelecteWindow) Executions
						.createComponents(
								"/admin/personal/infoExtract/task/wMulWindow.zul", null,
								null);
				w.doHighlighted();
				w.initWindow();
				w.addEventListener(Events.ON_CHANGE, new EventListener() {

					public void onEvent(Event event) throws Exception {
						TaskAuditMulSelecteWindow w = (TaskAuditMulSelecteWindow)event.getTarget();
						Set sel = w.getTree().getSelectedItems();
						Iterator it = sel.iterator();
						String tname = "";
						while (it.hasNext()) {
							Treeitem item = (Treeitem) it.next();
							WkTExtractask ti = (WkTExtractask) item.getValue();
							cList.add(ti);
							tname = tname + ti.getKeName().trim() + ",";
						}
						ktname.setValue(tname);
						w.detach();
					}

				});
			}
		});
	}
	
	/**
	 * <li>功能描述：初始化任务权限界面
	 */
public void initWindow(WkTExtractask editTask) {
		
		WkTDept pdept=(WkTDept)departmentService.get(WkTDept.class, user.getKdId());
		deptlist.setRootDept(pdept);
		deptlist.setRootID(user.getKdId());		
		deptlist.initNewDeptSelect(deptlist.getRootDept());	
		if(user.getKdId().intValue()!=0){
        WkTDept rootDept=new WkTDept();
		rootDept.setKdName(" ");
	    rootDept.setKdId(0L);	
	    rootDept.setDep(0);
		deptlist.getDmodelList().add(0, rootDept);
		deptlist.setRootDept(rootDept);	
		}else{
			deptlist.getRootDept().setKdName(" ");
		}
		rolelist.setRootRole(null);
		rolelist.setDeptList(userDeptList);
		rolelist.initRoleSelect(0L);		
		rids=rolelist.getRidList();
		dids=deptlist.getDidList();
		cList.clear();
		if(editTask!=null){
		 hasAuth.initAuthList(editTask.getKeId(), rids, dids);
		 cList.add(editTask);		
		 this.editTask = editTask;
		 this.setTitle("编辑任务权限："+editTask.getKeName());
		//更新标题名称
		 ktname.setValue(editTask.getKeName());
		}
		curauth.setValue("");
		managerAuth.setChecked(false);
	    authIp.setValue("");
	}
	
//保存权限设置
	public void onClick$submit() throws InterruptedException {

		if (cList.size() == 0) {
			Messagebox.show("请选择任务", "错误提示", Messagebox.OK,
					Messagebox.INFORMATION);
			return;
		}
		if(hasAuth.getItemCount()==0)
		{
			Messagebox.show("请添加权限", "错误提示", Messagebox.OK,
					Messagebox.INFORMATION);
			return;
		}
		List aulist = hasAuth.getAmodelList().getInnerList();
		StringBuffer sb=new StringBuffer( "编辑任务权限，id:");
		if (totalSet.isChecked()) {// 完全设置，需要将原有 的删除
			for (int i = 0; i < cList.size(); i++) {
				WkTExtractask w = (WkTExtractask) cList.get(i);
				authService.deleteAuthOfTask(w.getKeId(), rids, dids);
				for (int j = 0; j < aulist.size(); j++) {
					WkTAuth au = (WkTAuth) aulist.get(j);
					au.setKaRid(w.getKeId());
					authService.save(au);
				}
				  sb.append(w.getKeId()+",");
			}
		} else { // 增量设置，首先需要判断已有权限中是否已经含有相同权限了
			for (int i = 0; i < cList.size(); i++)
			{
				WkTExtractask w = (WkTExtractask) cList.get(i);
				  sb.append(w.getKeId()+",");
				for (int j = 0; j < aulist.size(); j++) {
					WkTAuth au = (WkTAuth) aulist.get(j);
					au.setKaRid(w.getKeId());
					List mlist = authService.findByExample(au);
					if (mlist.size() == 0) {
						authService.save(au);
					}
				}
			}
		}
		//保存日志信息
		mlogService.saveMLog(WkTMlog.FUNC_CMS, sb.toString(), user);
		Messagebox.show("保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
		Events.postEvent(Events.ON_CHANGE, this, null);
		this.detach();
	}
//增加权限
	public void onClick$addAuth() throws InterruptedException {

		if (!(managerAuth.isChecked()||auditAuth.isChecked())) {
			Messagebox.show("请设置权限类别", "错误提示", Messagebox.OK,
					Messagebox.INFORMATION);
			return;
		}
		Set rset = rolelist.getSelectedItems();
		Set dset = deptlist.getSelectedItems();

		List rlist = new ArrayList(rset);
		List dlist = new ArrayList(dset);

		for (int i = 0; i < rlist.size(); i++) {
			Listitem ritem = (Listitem) rlist.get(i);
			WkTRole role = (WkTRole) ritem.getValue();
			if (role.getKrPid() != null && role.getKrPid().intValue() == 0)
				continue;
			for (int j = 0; j < dlist.size(); j++) {
				Listitem ditem = (Listitem) dlist.get(j);
				WkTDept dept = (WkTDept) ditem.getValue();
				WkTAuth au = new WkTAuth();
				au.setKaType(WkTAuth.TYPE_TASK);
				au.setKrId(role.getKrId());
				au.setKdId(dept.getKdId());
				if (managerAuth.isChecked()) {
					au.setKaCode2(this.check);
				} else {
					au.setKaCode2(this.uncheck);
				}
				if(auditAuth.isChecked()){
					au.setKaCode3(this.check);
				} else {
					au.setKaCode3(this.uncheck);
				}
				au.setKaCode(au.getKaCode2()+""+au.getKaCode3());
				try {
					if (authIp.getValue().length() > 0)
						IPUtil.setIP(au, authIp.getValue());
				} catch (Exception e) {
					Messagebox.show("IP地址错误(正确格式如:192.168.*.100-255)", "错误提示",
							Messagebox.OK, Messagebox.INFORMATION);
					return;
				}
				au.setKaType(WkTAuth.TYPE_TASK + " ");
				hasAuth.getAmodelList().add(au);
			}
		}
	}
//删除权限
	public void onClick$deleteAuth() {
		Set sitems = hasAuth.getSelectedItems();
		Iterator it = sitems.iterator();
		List dlist = new ArrayList();
		while (it.hasNext()) {
			Listitem item = (Listitem) it.next();
			dlist.add(item.getValue());
		}
		hasAuth.getAmodelList().removeAll(dlist);
	}
//清空权限
	public void onClick$clearAuth() {
		hasAuth.getAmodelList().clear();
	}
	
	public void onSelect$hasAuth(){
		Listitem se=hasAuth.getSelectedItem();
		WkTAuth au=(WkTAuth)se.getValue();
		setRoleListBoxSelected(au.getKrId());
		setDeptListBoxSelected(au.getKdId());
		curauth.setValue(se.getLabel());
		authIp.setValue(au.getIP());		
		if(au.getKaCode2().intValue()==1){
			managerAuth.setChecked(true);
		}else{
			managerAuth.setChecked(false);
		}
		if(au.getKaCode3().intValue()==1){
			auditAuth.setChecked(true);
		}else{
			auditAuth.setChecked(false);
		}
	}
	/**
	 * <li>功能描述：选中角色列表中一项。
	 * @param rid 角色编号
	 * void 
	 * @author FengXinhong
	 */
	public void setRoleListBoxSelected(Long r) {
		List rlist = rolelist.getChildren();
		for (int i = 0; i < rlist.size(); i++) {
			Listitem it = (Listitem) rlist.get(i);
			it.setSelected(false);
			WkTRole rl = (WkTRole) it.getValue();
			if (r.intValue() == rl.getKrId().intValue()) {
				it.setSelected(true);
			}
		}
	}

	// 为角色列表增加新节点
	public void addRoleListBoxItem(ListModelList rml, Long pid) {
		List roleList = roleService.getChildRole(pid);
		for (int i = 0; i < roleList.size(); i++) {
			WkTRole r = (WkTRole) roleList.get(i);
			rml.add(r);
			addRoleListBoxItem(rml, r.getKrId());
		}
	}

	/**
	 * <li>功能描述：选中部门列表中一项。
	 * @param did 部门编号
	 * void 
	 * @author FengXinhong
	 */
	private void setDeptListBoxSelected(Long did) {
		List dlist = deptlist.getChildren();
		for (int i = 0; i < dlist.size(); i++) {
			Listitem it = (Listitem) dlist.get(i);
			it.setSelected(false);
			WkTDept dl = (WkTDept) it.getValue();
			if (did.intValue() ==dl.getKdId().intValue()) {
				it.setSelected(true);
			}
		}

	}

	private void addDeptListBoxitem(ListModelList drolelist, long did) {
		List deptlist = departmentService.getChildDepartment(did);
		for (int i = 0; i < deptlist.size(); i++) {
			WkTDept d = (WkTDept) deptlist.get(i);
			drolelist.add(d);
			addDeptListBoxitem(drolelist, d.getKdId());
		}
	}
	public WkTExtractask getEditTask() {
		return editTask;
	}
	
	
//点击重置按钮触发 的事件
public void onClick$reset(){
	initWindow(getEditTask());
	ktname.setValue("");
}
//返回按钮
public void onClick$close(){
	this.detach();
}
}
