package com.uniwin.framework.ui.dept;

/**
 * <li>��֯�����������������,��Ӧ��ҳ��Ϊ/admin/system/organize/index.zul
 * @author DaLei
 * @2010-3-17
 */
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.ui.login.BaseLeftTreeComposer;

public class OrganizationTreeComposer extends BaseLeftTreeComposer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Tree deptTree;
	DepartmentService departmentService;
	DepartmentTreeModel dtm;
	/**
	 * ���ű༭����
	 */
	DepartmentEditWindow deptWindow;
	Treeitem selectItem;
	// ��ǰ��½�û�
	WkTUser user;
	Panel deptPanel;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		user = (WkTUser) session.getAttribute("user");
		deptTree.setTreeitemRenderer(new DepartmentItemRenderer());
		deptTree.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event event) throws Exception {
				Treeitem item = deptTree.getSelectedItem();
				if (item != null) {
					// ����ԭ��ѡ���ͼ��
					if (selectItem != null) {
						WkTDept d = (WkTDept) selectItem.getValue();
						if (d.getKdType().trim().equalsIgnoreCase(WkTDept.BUMEN)) {
							selectItem.setImage("/images/tree/dept/bumen.gif");
						} else if (d.getKdType().trim().equalsIgnoreCase(WkTDept.DANWEI)) {
							selectItem.setImage("/images/tree/dept/danwei.gif");
						} else {
							selectItem.setImage("/images/tree/dept/xueke.gif");
						}
					}
					selectItem = item;
					// ��������ѡ��ͼ��
					if (selectItem != null) {
						WkTDept d = (WkTDept) selectItem.getValue();
						if (d.getKdType().trim().equalsIgnoreCase(WkTDept.BUMEN)) {
							selectItem.setImage("/images/tree/dept/bumenxz.gif");
						} else if (d.getKdType().trim().equalsIgnoreCase(WkTDept.DANWEI)) {
							selectItem.setImage("/images/tree/dept/danweixz.gif");
						} else {
							selectItem.setImage("/images/tree/dept/xuekexz.gif");
						}
						openEditWindow(d);
					}
				}
			}
		});
		loadTree();
		if (user.getKdId() == 0) {
			openTree(deptTree.getTreechildren(), (WkTDept) departmentService.get(WkTDept.class, 1L));
		} else {
			openTree(deptTree.getTreechildren(), (WkTDept) departmentService.get(WkTDept.class, user.getKdId()));
		}
	}

	/**
	 * <li>�������������¼������� void
	 * 
	 * @author DaLei
	 * @date 2010-3-29 11:15:58
	 */
	private void loadTree() {
		List<WkTDept> rlist;
		if (user.getKdId() == 0) {
			rlist = departmentService.getChildDepartment(Long.parseLong("0"), WkTDept.QUANBU);
		} else {
			rlist = new ArrayList<WkTDept>();
			rlist.add((WkTDept) departmentService.get(WkTDept.class, user.getKdId()));
		}
		dtm = new DepartmentTreeModel(rlist, departmentService, WkTDept.QUANBU);
		deptTree.setModel(dtm);
	}

	/**
	 * <li>���������������ڵ�չ����
	 * 
	 * @param chi void
	 * @author DaLei
	 */
//	private void openTree(Treechildren chi) {
//		if (chi == null)
//			return;
//		List tlist = chi.getChildren();
//		for (int i = 0; i < tlist.size(); i++) {
//			Treeitem item = (Treeitem) tlist.get(i);
//			item.setOpen(true);
//			openTree(item.getTreechildren());
//		}
//	}

	/**
	 * <li>���������������ڵ�չ����Ĭ�ϴ�ĳ�����ű༭��
	 * 
	 * @param chi
	 * @param dept void
	 * @author DaLei
	 */
	@SuppressWarnings("unchecked")
	private void openTree(Treechildren chi, WkTDept dept) {
		if (chi == null)
			return;
		if (dept == null) {
			if (user.getKdId().longValue() == 0) {
				List<WkTDept> clist = departmentService.getChildDepartment(0L, WkTDept.QUANBU);
				if (clist.size() == 0) {
					return;
				} else {
					dept = (WkTDept) clist.get(0);
					openTree(chi, dept);
				}
			}
		}
		List<Treeitem> tlist = chi.getChildren();
		for (int i = 0; i < tlist.size(); i++) {
			Treeitem item = (Treeitem) tlist.get(i);
			WkTDept d = (WkTDept) item.getValue();
			if (d.getKdId().intValue() == dept.getKdId().intValue()) {
				deptTree.setSelectedItem(item);
				if (deptWindow != null) {
					deptWindow.initWindow(d);
				} else {
					openEditWindow(d);
				}
			}
			if (d.getKdLevel() == WkTDept.GRADE_SCH.intValue())
				item.setOpen(true);
			else
				item.setOpen(false);
			openTree(item.getTreechildren(), dept);
		}
	}

	/**
	 * <li>������������ĳ���ű༭���ڡ�
	 * 
	 * @param d void
	 * @author DaLei
	 */
	public void openEditWindow(WkTDept d) {
		Component c = creatTab("dept", "��֯����", "/admin/system/organize/deptEdit.zul", deptPanel);
		if (c != null) {
			deptWindow = (DepartmentEditWindow) c;
		}
		deptWindow.initWindow(d);
		deptWindow.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				loadTree();
				openTree(deptTree.getTreechildren(), deptWindow.getDept());
			}
		});
	}
}
