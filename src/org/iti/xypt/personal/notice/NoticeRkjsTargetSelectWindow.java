package org.iti.xypt.personal.notice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.iti.jxgl.entity.JxCourse;
import org.iti.jxgl.entity.JxTask;
import org.iti.jxgl.service.CalendarService;
import org.iti.jxgl.service.TaskService;
import org.iti.xypt.entity.XyClass;
import org.iti.xypt.entity.XyUserrole;
import org.iti.xypt.service.MessageService;
import org.iti.xypt.service.XyClassService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.AbstractTreeModel;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.DepartmentService;

public class NoticeRkjsTargetSelectWindow extends Window implements AfterCompose {
	Tree targetTree;
	MessageService messageService;
	DepartmentService departmentService;
	XyClassService xyClassService;
	XyUserrole xyUserrole;
	WkTRole role;
	// 已经选择 的编辑
	List cllist = new ArrayList();
	List cidlist = new ArrayList();
	WkTUser user;
	TaskService taskService;
	CalendarService calendarService;
	Map<Long, List> jctaskmap = new HashMap();
	ClassTreeModel classTreeMode;

	public void initShow() {
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		targetTree.setTreeitemRenderer(new TreeitemRenderer() {
			public void render(Treeitem item, Object data) throws Exception {
				item.setValue(data);
				if (data instanceof JxCourse) {
					JxCourse course = (JxCourse) data;
					item.setLabel(course.getJcName());
					item.setOpen(true);
					item.setCheckable(false);
				} else if (data instanceof XyClass) {
					XyClass cl = (XyClass) data;
					item.setLabel(cl.getClName());
					if (cidlist.contains(cl.getClId())) {
						item.setSelected(true);
					}
				}
				//System.out.println(item.getLabel());
			}
		});
	}

	/**
	 * 初始化树
	 * 
	 * @param kdid
	 *            ,学校编号
	 */
	public void initWindow(List cllist, XyUserrole xyu) {
		for (int i = 0; cllist != null && i < cllist.size(); i++) {
			XyClass xyc = (XyClass) cllist.get(i);
			cidlist.add(xyc.getClId());
		}
		String[] YearTerm = calendarService.getNowYearTerm();
		String year = YearTerm[0];// 得到年份
		String tempterm = YearTerm[1];// 得到学期
		Integer term1 = Integer.valueOf(tempterm);
		short term = Short.parseShort(term1.toString());
		List tlist = taskService.findTaskByYearTermThidKdidcourseid(year, term, user.getKuId(), xyu.getRole().getKdId(), 0L);
		//System.out.println(tlist.size());
		List jcidlist = new ArrayList();
		List jclist = new ArrayList();
		for (int i = 0; i < tlist.size(); i++) {
			JxTask t = (JxTask) tlist.get(i);
			if (!jcidlist.contains(t.getJcId())) {
				jcidlist.add(t.getJcId());
				JxCourse jc = (JxCourse) taskService.get(JxCourse.class, t.getJcId());
				jclist.add(jc);
				List ttlist = new ArrayList();
				List clist = xyClassService.findByScnameAndKdid(t.getJtClass(), xyu.getRole().getKdId());
				if (clist.size() > 0) {
					ttlist.add(clist.get(0));
					jctaskmap.put(t.getJcId(), ttlist);
				}
			} else {
				List ttlist = jctaskmap.get(t.getJcId());
				List clist = xyClassService.findByScnameAndKdid(t.getJtClass(), xyu.getRole().getKdId());
				if (clist.size() > 0) {
					ttlist.add(clist.get(0));
					jctaskmap.put(t.getJcId(), ttlist);
				}
			}
		}
		classTreeMode = new ClassTreeModel(jclist);
		loadTree();
	}

	private void loadTree() {
		targetTree.setModel(classTreeMode);
	}

	public void onClick$submit() {
		List temlist = new ArrayList();
		cidlist = new ArrayList();
		Set rset = targetTree.getSelectedItems();
		Iterator it = rset.iterator();
		while (it.hasNext()) {
			Treeitem item = (Treeitem) it.next();
			XyClass cla = (XyClass) item.getValue();
			if (!cidlist.contains(cla.getClId())) {
				temlist.add(item.getValue());
				cidlist.add(cla.getClId());
			}
		}
		cllist = temlist;
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	public void onClick$close() {
		this.detach();
	}

	public void onClick$reset() {
		loadTree();
	}

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		initShow();
	}

	class ClassTreeModel extends AbstractTreeModel {
		public ClassTreeModel(Object root) {
			super(root);
		}

		public Object getChild(Object parent, int index) {
			if (parent instanceof List) {
				return ((List) parent).get(index);
			} else if (parent instanceof JxCourse) {
				JxCourse jc = (JxCourse) parent;
				List ttlist = jctaskmap.get(jc.getJcId());
				return ttlist.get(index);
			}
			return null;
		}

		public int getChildCount(Object parent) {
			if (parent instanceof List) {
				return ((List) parent).size();
			} else if (parent instanceof JxCourse) {
				JxCourse jc = (JxCourse) parent;
				List ttlist = jctaskmap.get(jc.getJcId());
				return ttlist.size();
			} else {
				return 0;
			}
		}

		public boolean isLeaf(Object node) {
			if (node instanceof List) {
				return false;
			} else if (node instanceof JxCourse) {
				return false;
			} else {
				return true;
			}
		}
	}

	public List getCllist() {
		return cllist;
	}

	public XyUserrole getXyUserrole() {
		return xyUserrole;
	}

	public void setXyUserrole(XyUserrole xyUserrole) {
		this.xyUserrole = xyUserrole;
	}

	public void setCllist(List cllist) {
		this.cllist = cllist;
	}
}
