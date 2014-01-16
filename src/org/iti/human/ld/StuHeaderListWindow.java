package org.iti.human.ld;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.iti.xypt.entity.Student;
import org.iti.xypt.entity.XyClass;
import org.iti.xypt.service.StudentService;
import org.iti.xypt.service.XyClassService;
import org.iti.xypt.ui.base.FrameworkWindow;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

public class StuHeaderListWindow extends FrameworkWindow {
	Listbox stulist;
	Button submit, close;
	Object SelectItem;
	StudentService studentService;
	UserService userService;
	List showlist, templist;
	XyClassService xyClassService;
	Long kuId;

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}

	public void initShow() {
		stulist.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				arg0.setValue(arg1);
				Student stu = (Student) arg1;
				WkTUser sname = (WkTUser) userService.findSnameBystid(stu.getStId()).get(0);
				XyClass cla = (XyClass) userService.get(XyClass.class, stu.getClId());
				WkTDept dept = (WkTDept) userService.get(WkTDept.class, cla.getKdId());
				if (templist.contains(stu.getStId())) {
					arg0.setSelected(true);
				}
				Listcell c0 = new Listcell();
				Listcell c1 = new Listcell(stu.getStId());
				Listcell c2 = new Listcell(sname.getKuName());
				Listcell c3 = new Listcell(stu.getStClass());
				Listcell c4 = new Listcell(dept.getKdName());
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
			}
		});
	}

	public void initWindow() {
		templist = new ArrayList();
		for (int i = 0; i < showlist.size(); i++) {
			Student stu = (Student) showlist.get(i);
			templist.add(stu.getStId());
		}
		List slist = new ArrayList();
		if (SelectItem instanceof WkTDept) {
			WkTDept dept = (WkTDept) SelectItem;
			List classList = xyClassService.findClassForFDY(dept.getKdId(), kuId);
			for (int i = 0; i < classList.size(); i++) {
				XyClass cla = (XyClass) classList.get(i);
				slist.addAll(studentService.findStuByClid(cla.getClId()));
			}
		} else if (SelectItem instanceof XyClass) {
			XyClass cla = (XyClass) SelectItem;
			slist = studentService.findStuByClid(cla.getClId());
		}
		stulist.setModel(new ListModelList(slist));
	}

	public void onClick$close() {
		this.detach();
	}

	public void onClick$submit() {
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	public List getShowlist() {
		return showlist;
	}

	public void setShowlist(List showlist) {
		this.showlist = showlist;
	}

	public Object getSelectItem() {
		return SelectItem;
	}

	public void setSelectItem(Object selectItem) {
		SelectItem = selectItem;
	}

	public List getSelectUser() {
		List slist = new ArrayList();
		Set s = stulist.getSelectedItems();
		Iterator it = s.iterator();
		while (it.hasNext()) {
			Listitem item = (Listitem) it.next();
			slist.add(item.getValue());
		}
		return slist;
	}
}
