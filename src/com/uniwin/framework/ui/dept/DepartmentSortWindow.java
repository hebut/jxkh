package com.uniwin.framework.ui.dept;

/**
 * <li>组织机构排序窗口,对应的页面为admin/system/organize/deptSort.zul
 * @author DaLei
 * @2010-3-17
 */
import java.util.List;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTMlog;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.service.MLogService;
@SuppressWarnings("unchecked")
public class DepartmentSortWindow extends Window implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	WkTDept dept;
	DepartmentService departmentService;
	Listbox sortList;
	ListModelList sortModel;
	WkTUser user;
	MLogService mlogService;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		sortList.setItemRenderer(new DepartmentListitemRenderer());
		sortModel = new ListModelList();
	}

	public WkTDept getDept() {
		return dept;
	}

	public void initWindow(WkTDept dept) {
		this.dept = dept;
		reloadList();
	}

	public void onClick$submit() {
		List<Listitem> itemList = sortList.getItems();
		StringBuffer sb = new StringBuffer("编辑部门顺序,ids:");
		if (itemList.size() > 0) {
			for (int i = 0; i < itemList.size(); i++) {
				Listitem item = (Listitem) itemList.get(i);
				WkTDept d = (WkTDept) item.getValue();
				d.setKdOrder(Long.parseLong(i + ""));
				departmentService.update(d);
				sb.append(d.getKdId() + ",");
			}
		}
		mlogService.saveMLog(WkTMlog.FUNC_ADMIN, sb.toString(), user);
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	/**
	 * <li>功能描述：加载要排序的部门列表，如果部门正好自己属于部门，则只显示本部门。 void
	 * 
	 * @author DaLei
	 * @2010-3-17
	 */
	private void reloadList() {
		sortModel.clear();
		if (user.getKdId().intValue() == dept.getKdId().intValue()) {
			sortModel.add(dept);
		} else {
			List<WkTDept> plist = departmentService.getChildDepartment(dept.getKdPid(), WkTDept.QUANBU);
			sortModel.addAll(plist);
		}
		sortList.setModel(sortModel);
	}

	public void onClick$reset() {
		initWindow(getDept());
	}

	public void onClick$close() {
		this.detach();
	}
}
