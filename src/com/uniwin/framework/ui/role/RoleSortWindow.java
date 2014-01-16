package com.uniwin.framework.ui.role;

/**
 * <li>角色排序窗口解析器,对应页面为admin/system/role/roleSort.zul
 * @author DaLei
 * @2010-3-17
 */
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Window;
import com.uniwin.framework.entity.WkTMlog;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.MLogService;
import com.uniwin.framework.service.RoleService;

public class RoleSortWindow extends Window implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	RoleService roleService;
	Listbox roleListBox;
	ListModelList listModel;
	/**
	 * 用户选择的角色或角色组，系统获得其同目录下的同等级的角色列表进行排序
	 */
	WkTRole sortRole;
	List<Long> userDeptList;
	WkTUser user;
	MLogService mlogService;

	@SuppressWarnings("unchecked")
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		userDeptList = (List<Long>) Sessions.getCurrent().getAttribute("userDeptList");
		listModel = new ListModelList();
		roleListBox.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception {
				WkTRole r = (WkTRole) data;
				item.setValue(r);
				item.setLabel(r.getKrName());
				item.setDraggable("true");
				item.setDroppable("true");
				item.setHeight("25px");
				item.addEventListener(Events.ON_DROP, new EventListener() {
					public void onEvent(Event event) throws Exception {
						DropEvent de = (DropEvent) event;
						Component self = (Component) event.getTarget();
						Listitem dragged = (Listitem) de.getDragged();
						if (self instanceof Listitem) {
							self.getParent().insertBefore(dragged, self.getNextSibling());
						} else {
							self.appendChild(dragged);
						}
					}
				});
			}
		});
	}

	@SuppressWarnings("unchecked")
	public void onClick$submit() {
		List<Listitem> itemList = roleListBox.getItems();
		StringBuffer sb = new StringBuffer("编辑角色顺序,ids:");
		for (int i = 0; i < itemList.size(); i++) {
			Listitem item = (Listitem) itemList.get(i);
			WkTRole d = (WkTRole) item.getValue();
			d.setKrOrder(Long.parseLong(i + ""));
			roleService.update(d);
			sb.append(d.getKrId() + ",");
		}
		mlogService.saveMLog(WkTMlog.FUNC_ADMIN, sb.toString(), user);
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	public void onClick$reset() {
		reloadList();
	}

	public void onClick$close() {
		this.detach();
	}

	public WkTRole getSortRole() {
		return sortRole;
	}

	/**
	 * <li>功能描述：初始化排序窗口
	 * 
	 * @param sortRole
	 *            void
	 * @author DaLei
	 * @2010-3-17
	 */
	public void initWindow(WkTRole sortRole) {
		this.sortRole = sortRole;
		reloadList();
	}

	/**
	 * <li>功能描述：重新加载角色列表。 void
	 * 
	 * @author DaLei
	 */
	private void reloadList() {
		listModel.clear();
		List<WkTRole> sortlist;
		if (sortRole.getKrPid().intValue() == 0) {
			sortlist = roleService.getChildRole(sortRole.getKrPid());
		} else {
			sortlist = roleService.getChildRole(sortRole.getKrPid(), userDeptList);
		}
		listModel.addAll(sortlist);
		roleListBox.setModel(listModel);
	}
}
