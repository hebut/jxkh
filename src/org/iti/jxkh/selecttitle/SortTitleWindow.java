package org.iti.jxkh.selecttitle;

import java.util.ArrayList;
import java.util.List;

import org.iti.jxkh.service.AuditConfigService;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTTitle;

public class SortTitleWindow extends Window implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6357806568359907275L;

	private Listbox titleListbox;

	private AuditConfigService auditConfigService;
	
	private List<WkTTitle> titleList = null;

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		titleListbox.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				WkTTitle t = (WkTTitle) arg1;
				if (t != null) {
					arg0.setValue(t);
					Listcell c0 = new Listcell(Integer.valueOf(arg0.getIndex() + 1).toString());
					Listcell c1 = new Listcell(t.getKtName() != null ? t.getKtName() : "");
					Listcell c2 = new Listcell();
					if (t.getKtPid() != null) {
						WkTTitle pt = (WkTTitle) auditConfigService.loadById(WkTTitle.class, t.getKtPid());
						c2.setLabel(pt != null && pt.getKtName() != null ? pt.getKtName() : "нч");
					}
					arg0.setDraggable(Boolean.TRUE.toString());
					arg0.setDroppable(Boolean.TRUE.toString());
					arg0.addEventListener(Events.ON_DROP, new EventListener() {
						public void onEvent(Event arg0) throws Exception {
							DropEvent event = (DropEvent) arg0;
							Component self = event.getTarget();
							Listitem dragged = (Listitem) event.getDragged();
							if(self instanceof Listitem) {
								self.getParent().insertBefore(dragged, self.getNextSibling());
							}else {
								self.appendChild(dragged);
							}
						}
					});
					arg0.appendChild(c0);
					arg0.appendChild(c1);
					arg0.appendChild(c2);
				}
			}
		});
	}

	public void initWindow() {
		if(titleList != null) {
			titleListbox.setModel(new ListModelList(titleList));
		}
	}

	public void onClick$saveBn() {
		if(titleList != null) 
			titleList.clear();
		else 
			titleList = new ArrayList<WkTTitle>();
		for(Object o : titleListbox.getItems()) {
			if(o != null) {
				Listitem item = (Listitem) o;
				if(item.getValue() != null) {
					titleList.add((WkTTitle)item.getValue());
				}
			}
		}
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	public void onClick$closeBn() {
		this.detach();
	}

	public List<WkTTitle> getTitleList() {
		return titleList;
	}

	public void setTitleList(List<WkTTitle> titleList) {
		this.titleList = titleList;
	}
}
