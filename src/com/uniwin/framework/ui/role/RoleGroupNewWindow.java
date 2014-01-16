package com.uniwin.framework.ui.role;

/**
 * <li>角色组新建窗口，对应的页面admin/system/role/roleGroup.zul
 * @author DaLei
 * @2010-3-17
 */
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import com.uniwin.framework.entity.WkTMlog;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.MLogService;
import com.uniwin.framework.service.RoleService;

public class RoleGroupNewWindow extends Window implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Textbox roleGname, roleGdesc;
	RoleService roleService;
	MLogService mlogService;
	WkTUser user;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
	}

	public void onClick$submit() {
		WkTRole newRole = new WkTRole();
		newRole.setKrName(roleGname.getValue());
		newRole.setKrDesc(roleGdesc.getValue());
		newRole.setKdId(0L);
		newRole.setKrDefault("0");
		newRole.setKrPid(0L);
		roleService.save(newRole);
		mlogService.saveMLog(WkTMlog.FUNC_ADMIN, "新建角色组，id：" + newRole.getKrId(), user);
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	public void onClick$reset() {
		roleGname.setRawValue(null);
		roleGdesc.setRawValue(null);
	}

	public void onClick$close() {
		this.detach();
	}
}
