package org.iti.xypt.personal.notice;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.iti.xypt.entity.XyClass;
import org.iti.xypt.entity.XyFudao;
import org.iti.xypt.service.XyClassService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Window;

public class NoticeFdyClassAddWindow extends Window implements AfterCompose {
	Listbox classSelect;
	/**
	 * 能够选择的角色列表，传过来的参数
	 */
	List classList;
	/**
	 * 已经选择的角色的编号列表和角色列表
	 */
	List cidList, selectedClassList;
	XyClassService xyClassService;

	public void initShow() {
		classSelect.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception {
				XyFudao cl = (XyFudao) data;
				XyClass cla = (XyClass) xyClassService.get(XyClass.class, cl.getId().getClId());
				Listcell c0 = new Listcell(item.getIndex() + 1 + "");
				Listcell c1 = new Listcell(cla.getClName());
				if (cidList.contains(cla.getClId())) {
					item.setSelected(true);
				}
				item.appendChild(c0);
				item.appendChild(c1);
				item.setValue(cla);
			}
		});
	}

	/**
	 * 初始化选择窗口
	 */
	public void initWindow(List classList, List selectList) {
		cidList = new ArrayList();
		this.classList = classList;
		classSelect.setModel(new ListModelList(classList));
		selectedClassList = selectList;
		if (selectList != null) {
			for (int i = 0; i < selectList.size(); i++) {
				XyClass r = (XyClass) selectList.get(i);
				cidList.add(r.getClId());
			}
		}
	}

	public void onClick$submit() {
		List temlist = new ArrayList();
		Set rset = classSelect.getSelectedItems();
		Iterator it = rset.iterator();
		while (it.hasNext()) {
			Listitem item = (Listitem) it.next();
			temlist.add(item.getValue());
		}
		this.selectedClassList = temlist;
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	public void onClick$close() {
		this.detach();
	}

	public void onClick$reset() {
		initWindow(classList, selectedClassList);
	}

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		initShow();
	}

	public List getSelectedClassList() {
		return selectedClassList;
	}

	public void setSelectedClassList(List selectedClassList) {
		this.selectedClassList = selectedClassList;
	}
}
