package org.iti.xypt.personal.infoCollect;
/**
 * <li>站点权限更新界面,更加详细的的单独权限权限设置,对应页面admin/content/website/websiteauthupdate.zul
 * @author whm
 * @2010-7-20
 */
import java.util.List;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.uniwin.common.util.IPUtil;
import com.uniwin.framework.common.listbox.DeptListbox;
import com.uniwin.framework.common.listbox.DeptRoleListbox;
import com.uniwin.framework.entity.WkTAuth;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTMlog;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.AuthService;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.service.MLogService;


public class TaskAuthUpdateWindow extends Window implements AfterCompose {

	DeptRoleListbox roleSelect;
	DeptListbox deptSelect;
	List userDeptList;
	WkTUser user;
	WkTAuth auth;
	DepartmentService departmentService;
	AuthService authService;
	//IP地址绑定输入框
	Textbox IPAddr;
	//权限设置的多择组件
	Checkbox  managec, audit;
	
	//日志数据访问接口
	MLogService mlogService;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user=(WkTUser)Sessions.getCurrent().getAttribute("user");
		userDeptList=(List)Sessions.getCurrent().getAttribute("userDeptList");
	}
//初始化更新窗口
	public void ininWindow(WkTAuth auth) {

		this.auth = auth;
		roleSelect.setRootRole(null);
		roleSelect.setDeptList(userDeptList);
		roleSelect.initRoleSelect(auth.getKrId());

		WkTDept pdept = (WkTDept) departmentService.get(WkTDept.class, user.getKdId());
		deptSelect.setRootDept(pdept);
		deptSelect.setRootID(user.getKdId());
		if (auth.getKdId() == null) {
			deptSelect.initNewDeptSelect(deptSelect.getRootDept());
		} else {
			WkTDept dept = (WkTDept) departmentService.get(WkTDept.class, auth.getKdId());
			if (dept == null) {
				deptSelect.initNewDeptSelect(deptSelect.getRootDept());
			} else {
				deptSelect.initNewDeptSelect(dept);
			}
		}
		if (user.getKdId().intValue() != 0) {
			WkTDept rootDept = new WkTDept();
			rootDept.setKdName(" ");
			rootDept.setKdId(0L);
			rootDept.setDep(0);
			deptSelect.getDmodelList().add(0, rootDept);
			deptSelect.setRootDept(rootDept);
		} else {
			deptSelect.getRootDept().setKdName(" ");
		}

		IPAddr.setValue(auth.getIP());
		// 初始化管理权限
		if (auth.getKaCode2().intValue() == 1) {
			managec.setChecked(true);
		} else {
			managec.setChecked(false);
		}
		if (auth.getKaCode3().intValue() == 1) {
			audit.setChecked(true);
		} else {
			audit.setChecked(false);
		}

	}
	//保存栏目权限的编辑或者新建结果
   public void onClick$submit()throws InterruptedException{
	   WkTRole role=(WkTRole)roleSelect.getSelectedItem().getValue();
		WkTDept dept=(WkTDept)deptSelect.getSelectedItem().getValue();	
			if(!managec.isChecked())
			{
				if(!audit.isChecked())
				{
			Messagebox.show("请设置权限类别", "错误提示", Messagebox.OK, Messagebox.INFORMATION);
			return;
				}
			}
		//权限类型设置为栏目
		auth.setKaType(WkTAuth.TYPE_TASK);
		auth.setKrId(role.getKrId());
		auth.setKdId(dept.getKdId());
		
		if(IPAddr.getValue().length()>0){
		  IPUtil.setIP(auth, IPAddr.getValue());
		}
		
	
		
		if(role.getKrPid()!=null&&role.getKrPid().intValue()==0) {
			return;
		}
		
		if(role.getKrId().intValue()==0&&dept.getKdId().intValue()==0){
			if(user.getKdId().intValue()!=0){
				return;
			}
		}
		//两种权限的设置
		if(managec.isChecked()){
			auth.setKaCode2(TaskAuthEditWindow.check);
		}else{
			auth.setKaCode2(TaskAuthEditWindow.uncheck);
		}
		if(audit.isChecked()){
			auth.setKaCode3(TaskAuthEditWindow.check);
		}else{
			auth.setKaCode3(TaskAuthEditWindow.uncheck);
		}
		auth.setKaCode(auth.getKaCode2()+""+auth.getKaCode3());
		
		//更新和增加权限共享这段代码
		if(auth.getKaId()==null){
			authService.save(auth);
			mlogService.saveMLog(WkTMlog.FUNC_CMS,"新建站点权限，id: "+auth.getKaId(), user);
		}else{
			authService.update(auth);
			mlogService.saveMLog(WkTMlog.FUNC_CMS, "修改站点权限，id："+auth.getKaId(), user);
		}
		
		Events.postEvent(Events.ON_CHANGE, this, null);
	   
   }
   //点击重置按钮--初始化当前窗口
	public void onClick$reset() {
		ininWindow(auth);
	}
   //返回--关闭当前窗口
	public void onClick$close() {
		this.detach();
	}
   
}
