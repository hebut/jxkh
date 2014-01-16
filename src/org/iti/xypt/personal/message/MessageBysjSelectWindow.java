package org.iti.xypt.personal.message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.iti.bysj.entity.BsDbchoose;
import org.iti.bysj.entity.BsGproces;
import org.iti.bysj.entity.BsGpunit;
import org.iti.bysj.entity.BsStudent;
import org.iti.bysj.entity.BsTeacher;
import org.iti.bysj.service.BsStudentService;
import org.iti.bysj.service.BsTeacherService;
import org.iti.bysj.service.DbchooseService;
import org.iti.bysj.service.GprocesService;
import org.iti.bysj.service.GpunitService;
import org.iti.bysj.ui.base.InnerButton;
import org.iti.xypt.entity.Student;
import org.iti.xypt.entity.Teacher;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.AbstractTreeModel;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;

import com.uniwin.framework.entity.WkTUser;

public class MessageBysjSelectWindow extends MessageUserSelectWindow {
	Tree bysjMessageTree;
	WkTUser wkTUser;
	List uidList = new ArrayList();
	BsTeacherService bsteacherService;
	BsStudentService bsStudentService;
	GpunitService gpunitService;
	GprocesService gprocesService;
	DbchooseService dbchooseService;
	List bglist;
	boolean isTeacher = true;
	List recelist = new ArrayList();

	public List<WkTUser> getSelectUser() {
		Set sitem = bysjMessageTree.getSelectedItems();
		Iterator ite = sitem.iterator();
		List<WkTUser> ulist = new ArrayList<WkTUser>();
		while (ite.hasNext()) {
			Treeitem item = (Treeitem) ite.next();
			if (item.getValue() instanceof WkTUser) {
				ulist.add((WkTUser) item.getValue());
			}
		}
		return ulist;
	}

	public void onClick$submit() {
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	public void onClick$reset() {
		initWindow(recelist);
	}

	public boolean isBsTeacher() {
		return bglist.size() > 0;
	}

	public void initShow() {
		wkTUser = (WkTUser) Sessions.getCurrent().getAttribute("user");
		bysjMessageTree.setTreeitemRenderer(new TreeitemRenderer() {
			public void render(Treeitem item, Object data) throws Exception {
				Treecell c_label = new Treecell();
				Treecell c_do = new Treecell("");
				Treerow tr = new Treerow();
				item.setCheckable(false);
				if (data instanceof BsGproces) {
					BsGproces bg = (BsGproces) data;
					c_label.setLabel(bg.getBgName());
					item.setValue(bg);
					tr.appendChild(c_label);
					tr.appendChild(c_do);
					item.appendChild(tr);
				} else if (data instanceof BsGpunit) {
					BsGpunit gu = (BsGpunit) data;
					c_label.setLabel(gu.getDept().getKdName());
					item.setValue(gu);
					InnerButton inb = new InnerButton();
					inb.setLabel("全选");
					inb.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event arg0) throws Exception {
							InnerButton in = (InnerButton) arg0.getTarget();
							if (in.getLabel().equalsIgnoreCase("全选")) {
								in.setLabel("取消");
								checkItem((Treeitem) in.getParent().getParent().getParent(), true);
							} else {
								in.setLabel("全选");
								checkItem((Treeitem) in.getParent().getParent().getParent(), false);
							}
						}
					});
					c_do.appendChild(inb);
					tr.appendChild(c_label);
					tr.appendChild(c_do);
					item.appendChild(tr);
				} else if (data instanceof BsDbchoose) {
					BsDbchoose db = (BsDbchoose) data;
					WkTUser u = null;
					if (isTeacher) {
						u = db.getStudent().getStudent().getUser();
					} else {
						u = db.getTeacher().getTeacher().getUser();
					}
					c_label.setLabel(u.getKuName());
					item.setValue(u);
					if (uidList.contains(u.getKuId())) {
						item.setSelected(true);
					}
					item.setCheckable(true);
					tr.appendChild(c_label);
					tr.appendChild(c_do);
					item.appendChild(tr);
				}
				item.setOpen(true);
			}
		});
	}

	private void checkItem(Treeitem item, boolean check) {
		Treechildren ic = item.getTreechildren();
		if (ic != null && ic.getChildren().size() > 0) {
			for (int i = 0; i < ic.getChildren().size(); i++) {
				Treeitem citem = (Treeitem) ic.getChildren().get(i);
				if (citem.getValue() instanceof WkTUser) {
					citem.setSelected(check);
				} else {
					checkItem(citem, check);
				}
			}
		}
	}

	public void initWindow(List recelist) {
		this.recelist = recelist;
		for (int i = 0; i < recelist.size(); i++) {
			WkTUser u = (WkTUser) recelist.get(i);
			uidList.add(u.getKuId());
		}
		List gplist = new ArrayList();
		List glist = gprocesService.findByUser(wkTUser.getKuId());
		Teacher tea = (Teacher) bsteacherService.get(Teacher.class, wkTUser.getKuId());
		if (tea != null) {
			for (int i = 0; i < glist.size(); i++) {
				BsGproces bg = (BsGproces) glist.get(i);
				List btlist = bsteacherService.findByKuidAndGpid(wkTUser.getKuId(), bg.getBgId());
				if (btlist.size() > 0) {
					gplist.add(bg);
				}
			}
		} else {
			Student stu = (Student) bsStudentService.get(Student.class, wkTUser.getKuId());
			if (stu != null) {
				for (int i = 0; i < glist.size(); i++) {
					BsGproces bg = (BsGproces) glist.get(i);
					BsGpunit gpunit = gpunitService.findByKdidAndGpid(wkTUser.getKdId(), bg.getBgId());
					BsStudent bst = bsStudentService.findByKuidAndBuid(wkTUser.getKuId(), gpunit.getBuId());
					if (bst != null) {
						isTeacher = false;
						gplist.add(bg);
					}
				}
			}
		}
		this.bglist = gplist;
		bysjMessageTree.setModel(new BysjMessageModel(bglist));
	}

	class BysjMessageModel extends AbstractTreeModel {
		public BysjMessageModel(Object root) {
			super(root);
		}

		public Object getChild(Object parent, int arg1) {
			if (parent instanceof BsGproces) {
				BsGproces bg = (BsGproces) parent;
				if (isTeacher) {
					return gpunitService.findByKuidAndBgid(wkTUser.getKuId(), bg.getBgId()).get(arg1);
				} else {
					return gpunitService.findByKdidAndGpid(wkTUser.getKdId(), bg.getBgId());
				}
			} else if (parent instanceof BsGpunit) {
				BsGpunit bu = (BsGpunit) parent;
				if (isTeacher) {
					BsTeacher bt = bsteacherService.findByKuidAndBuid(wkTUser.getKuId(), bu.getBuId());
					return dbchooseService.findByBtid(bt.getBtId()).get(arg1);
				} else {
					BsStudent bs = bsStudentService.findByKuidAndBuid(wkTUser.getKuId(), bu.getBuId());
					return dbchooseService.findByBsid(bs.getBsId()).get(arg1);
				}
			} else if (parent instanceof BsDbchoose) {
				return parent;
			} else if (parent instanceof List) {
				List li = (List) parent;
				return li.get(arg1);
			}
			return null;
		}

		public int getChildCount(Object parent) {
			if (parent instanceof BsGproces) {
				BsGproces bg = (BsGproces) parent;
				if (isTeacher) {
					return gpunitService.findByKuidAndBgid(wkTUser.getKuId(), bg.getBgId()).size();
				} else {
					return 1;
				}
			} else if (parent instanceof BsGpunit) {
				BsGpunit bu = (BsGpunit) parent;
				if (isTeacher) {
					BsTeacher bt = bsteacherService.findByKuidAndBuid(wkTUser.getKuId(), bu.getBuId());
					return dbchooseService.findByBtid(bt.getBtId()).size();
				} else {
					BsStudent bs = bsStudentService.findByKuidAndBuid(wkTUser.getKuId(), bu.getBuId());
					return dbchooseService.findByBsid(bs.getBsId()).size();
				}
			} else if (parent instanceof BsDbchoose) {
				return 0;
			} else if (parent instanceof List) {
				List li = (List) parent;
				return li.size();
			}
			return 0;
		}

		public boolean isLeaf(Object parent) {
			if (parent instanceof BsGproces) {
				return false;
			} else if (parent instanceof BsGpunit) {
				return false;
			} else if (parent instanceof BsDbchoose) {
				return true;
			}
			return false;
		}
	}
}
