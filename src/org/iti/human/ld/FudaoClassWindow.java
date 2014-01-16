package org.iti.human.ld;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.iti.xypt.entity.XyClass;
import org.iti.xypt.entity.XyFudao;
import org.iti.xypt.service.FudaoService;
import org.iti.xypt.ui.base.FrameworkWindow;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.api.Listheader;

import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.service.DepartmentService;

public class FudaoClassWindow extends FrameworkWindow {
	Listbox classlist;
	Listheader ban;
	Long kdId;
	Long kuId;
	List list;

	public Long getKdId() {
		return kdId;
	}

	public void setKdId(Long kdId) {
		this.kdId = kdId;
	}

	public Long getKuId() {
		return kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}

	DepartmentService departmentService;
	FudaoService fudaoService;

	public void initShow() {
		classlist.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				XyClass cla = (XyClass) arg1;
				arg0.setValue(arg1);
				Listcell c1 = new Listcell();
				if (list.contains(cla.getClId())) {
					arg0.setSelected(true);
				}
				Listcell c2 = new Listcell(cla.getClSname());
				Listcell c3 = new Listcell(cla.getXiDept());
				Listcell c4 = new Listcell(cla.getClGrade().toString());
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
			}
		});
	}

	public void initWindow() {
		WkTDept dept = (WkTDept) fudaoService.get(WkTDept.class, kdId);
		List templist = fudaoService.findClassByKdId(kdId);
		classlist.setModel(new ListModelList(templist));
		/* 查找所负责的班级 */
		List fdlist = fudaoService.findClassByKuIdAndSchid(kuId,dept.getSchDept().getKdId());
		list = new ArrayList();
		for (int i = 0; i < fdlist.size(); i++) {
			XyFudao userdao = (XyFudao) fdlist.get(i);
			list.add(userdao.getId().getClId());
		}
	}

	public void onClick$close() {
		this.detach();
	}

	public void onClick$submit() {
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	public List getSelectClass() {
		List slist = new ArrayList();
		Set s = classlist.getSelectedItems();
		Iterator it = s.iterator();
		while (it.hasNext()) {
			Listitem item = (Listitem) it.next();
			slist.add(item.getValue());
		}
		return slist;
	}
}