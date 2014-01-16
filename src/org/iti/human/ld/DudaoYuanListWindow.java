package org.iti.human.ld;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.iti.bysj.entity.BsUserDudao;
import org.iti.bysj.service.CheckService;
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

public class DudaoYuanListWindow extends FrameworkWindow {
	Listbox xueyuan;
	Listheader yuan;
	Long kdId;
	Long kuId;
	List list;
	DepartmentService departmentService;
	CheckService checkService;

	public void initShow() {
		xueyuan.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				WkTDept dept = (WkTDept) arg1;
				arg0.setValue(arg1);
				Listcell c1 = new Listcell();
				if (list.contains(dept.getKdId())) {
					arg0.setSelected(true);
				}
				Listcell c2 = new Listcell(dept.getKdName());
				arg0.appendChild(c1);
				arg0.appendChild(c2);
			}
		});
	}

	public void initWindow() {
		WkTDept dept = (WkTDept) checkService.get(WkTDept.class, kdId);
		this.setTitle("选择所负责的" + dept.getGradeName(WkTDept.GRADE_YUAN));
		yuan.setLabel(dept.getGradeName(WkTDept.GRADE_YUAN));
		List templist = departmentService.getChildDepdw(kdId);
		xueyuan.setModel(new ListModelList(templist));
		/* 查找所负责的学院 */
		List ddlist = checkService.findKdidByKuidAndKdid(kuId, kdId);
		list = new ArrayList();
		for (int i = 0; i < ddlist.size(); i++) {
			BsUserDudao userdao = (BsUserDudao) ddlist.get(i);
			list.add(userdao.getId().getKdId());
		}
	}

	public void onClick$close() {
		this.detach();
	}

	public void onClick$submit() {
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	public List getSelectUser() {
		List slist = new ArrayList();
		Set s = xueyuan.getSelectedItems();
		Iterator it = s.iterator();
		while (it.hasNext()) {
			Listitem item = (Listitem) it.next();
			slist.add(item.getValue());
		}
		return slist;
	}

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
}
