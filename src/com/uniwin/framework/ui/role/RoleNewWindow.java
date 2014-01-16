package com.uniwin.framework.ui.role;

/**
 * <li>新建角色窗口解析器,对应页面admin/system/role/roleNew.zul
 * @author DaLei
 * @2010-3-17
 */
import java.util.List;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import com.uniwin.framework.entity.WkTMlog;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.service.MLogService;
import com.uniwin.framework.service.RoleService;

public class RoleNewWindow extends Window implements AfterCompose {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 新建角色时默认的角色组
	WkTRole groupRole;
	// 角色名称和角色描述
	Textbox roleName, roleDesc;
	// 是否默认和共享
	Checkbox isPro, isShare, isDd, isGZL;
	RoleService roleService;
	DepartmentService departmentService;
	WkTUser user;
	MLogService mlogService;
	/**
	 * 下面毕设新加
	 */
	Listbox roleGradeSelect;//roleTypeSelect
//	Row rowAdmins;
	Label schName;//roleAdmins, roleAdminID, 
	Row xtRow, ptRow, bsRow;;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		/*roleTypeSelect.addEventListener(Events.ON_SELECT, new EventListener() {

			public void onEvent(Event arg0) throws Exception {
				if (roleTypeSelect.getSelectedIndex() == 2 || roleTypeSelect.getSelectedIndex() == 4) {
					rowAdmins.setVisible(true);
				} else {
					rowAdmins.setVisible(false);
				}
			}
		});*/
	}

	public void initWindow(WkTRole groupRole) {
		this.groupRole = groupRole;
		this.setTitle("增加角色");
		if (groupRole.getWkTDept() == null) {
			schName.setValue("系统管理");
		} else {
			schName.setValue(groupRole.getWkTDept().getKdName());
		}
//		rowAdmins.setVisible(false);
		if (groupRole.getKdId().longValue() == 0L) {
			ptRow.setVisible(false);
			xtRow.setVisible(true);
//			bsRow.setVisible(false);
		} else {
			ptRow.setVisible(true);
			xtRow.setVisible(false);
//			bsRow.setVisible(true);
		}
	}

	public void onClick$submit() {
		WkTRole newRole = new WkTRole();
		newRole.setKrName(roleName.getValue());
		newRole.setKrDesc(roleDesc.getValue());
		newRole.setKrPid(groupRole.getKrId());
		newRole.setKdId(groupRole.getKdId());
		if (isPro.isChecked()) {
			newRole.setKrDefault("1");
		} else {
			newRole.setKrDefault("0");
		}
		if (isShare.isChecked()) {
			newRole.setKrShare("1");
		} else {
			newRole.setKrShare("0");
		}
		/**
		 * 毕设新加
		 */
//		if (isDd.isChecked()) {
//			newRole.setKrArgs(WkTRole.INDEX_ISDD, WkTRole.ISDD_YES);
//		} else {
//			newRole.setKrArgs(WkTRole.INDEX_ISDD, WkTRole.ISDD_NO);
//		}
//		if (isGZL.isChecked()) {
//			newRole.setKrArgs(WkTRole.INDEX_GZL, WkTRole.GZL_YES);
//		} else {
//			newRole.setKrArgs(WkTRole.INDEX_GZL, WkTRole.GZL_NO);
//		}
//		newRole.setKrArgs(WkTRole.INDEX_TYPE, getTypeSelect());
//		newRole.setKrArgs(WkTRole.INDEX_GRADE, getGradeSelect());
		/*if (roleTypeSelect.getSelectedIndex() == 2 || roleTypeSelect.getSelectedIndex() == 4) {
			newRole.setKrAdmins(roleAdminID.getValue());
		}*/
		roleService.save(newRole);
		mlogService.saveMLog(WkTMlog.FUNC_ADMIN, "新建角色，id：" + newRole.getKrId(), user);
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	/*private char getTypeSelect() {
		char type = ' ';
		switch (roleTypeSelect.getSelectedIndex()) {
		case 0:
			type = WkTRole.TYPE_XS;
			break;
		case 1:
			type = WkTRole.TYPE_JS;
			break;
		case 2:
			type = WkTRole.TYPE_LD;
			break;
		case 3:
			type = WkTRole.TYPE_DD;
			break;
		case 4:
			type = WkTRole.TYPE_FDY;
			break;
		case 5:
			type = WkTRole.TYPE_XSGB;
			break;
		case 6:
			type = WkTRole.TYPE_YJS;
			break;
		case 7:
			type = WkTRole.TYPE_XK;
			break;
		}
		return type;
	}*/

	private char getGradeSelect() {
		char grade = ' ';
		switch (roleGradeSelect.getSelectedIndex()) {
		case 0:
			grade = '0';
			break;
		case 1:
			grade = '1';
			break;
		case 2:
			grade = '2';
			break;
		case 3:
			grade = '3';
			break;
		}
		return grade;
	}

	/*public void onClick$editAdmins() {
		final RoleAdminSelectWindow rw = (RoleAdminSelectWindow) Executions.createComponents("/admin/system/role/roleAdminSelect.zul", null, null);
		rw.doHighlighted();
//		rw.setArgAdmins(roleAdminID.getValue());
		rw.initWindow(groupRole.getKdId(), getGradeSelect());
		rw.addEventListener(Events.ON_CHANGE, new EventListener() {

			public void onEvent(Event arg0) throws Exception {
				List<WkTRole> rlist = rw.getSelectRoles();
				StringBuffer sb = new StringBuffer("");
				StringBuffer sb2 = new StringBuffer("");
				for (int i = 0; i < rlist.size(); i++) {
					WkTRole r = (WkTRole) rlist.get(i);
					sb.append(r.getKrId());
					sb2.append(r.getKrName());
					if (i < (rlist.size() - 1)) {
						sb.append(",");
						sb2.append(",");
					}
				}
//				roleAdminID.setValue(sb.toString());
//				roleAdmins.setValue(sb2.toString());
				rw.detach();
			}
		});
	}*/

	public void onClick$reset() {
		initWindow(groupRole);
//		roleTypeSelect.setSelectedIndex(0);
		roleGradeSelect.setSelectedIndex(0);
		roleName.setRawValue(null);
		roleDesc.setRawValue(null);
		isShare.setChecked(false);
		isPro.setChecked(false);
//		isDd.setChecked(false);
	}

	public void onClick$close() {
		this.detach();
	}

	public WkTRole getGroupRole() {
		return groupRole;
	}

	public void setGroupRole(WkTRole groupRole) {
		this.groupRole = groupRole;
	}
}
