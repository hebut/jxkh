/**
 * 
 */
package org.iti.xypt.ui.base;

import org.iti.xypt.entity.Title;
import org.iti.xypt.service.XyptTitleService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;


/**
 * @author DaLei
 * @version $Id: TitleSelectHbox.java,v 1.2 2011/11/13 14:06:48 wwf Exp $
 */
public class TitleSelectHbox extends Hbox implements AfterCompose {
	XyptTitleService xypttitleService;
	Title fisrtTitle, secondTitle;
	Listbox flist, slist;

	public void afterCompose() {
		Components.wireVariables(this, this);
		flist = new Listbox();
		slist = new Listbox();
		flist.setMold("select");
		flist.setWidth("150px");
		slist.setMold("select");
		slist.setWidth("100px");
		flist.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception {
				
				Title t = (Title) data;
				item.setValue(t);
				item.setLabel(t.getTiName().trim());
				if (fisrtTitle != null && fisrtTitle.getTiId().trim().equalsIgnoreCase(t.getTiId().trim())) {
					item.setSelected(true);
				}
			}
		});
		slist.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception {
				
				Title t = (Title) data;
				item.setValue(t);
				item.setLabel(t.getTiName().trim());
				if (secondTitle != null && secondTitle.getTiId().trim().equalsIgnoreCase(t.getTiId().trim())) {
					item.setSelected(true);
				}
			}
		});
		flist.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				loadSlist();
			}
		});
		this.appendChild(flist);
		this.appendChild(slist);
		initHbox();
	}

	/**
	 * 初始化整个级联选择框
	 */
	public void initHbox() {
		flist.setModel(new ListModelList(xypttitleService.findFirstTitles()));
		if (fisrtTitle == null) {
			loadSlist();
		} else {
			slist.setModel(new ListModelList(xypttitleService.findByPtid(fisrtTitle.getTiId())));
		}
	}

	private void loadSlist() {
		Title ftitle;
		if (flist.getSelectedItem() == null) {
			ftitle = (Title) flist.getModel().getElementAt(0);
		} else {
			ftitle = (Title) flist.getSelectedItem().getValue();
		}
		slist.setModel(new ListModelList(xypttitleService.findByPtid(ftitle.getTiId())));
	}

	public Title getSelectTitle() {
		Title ftitle;
		if (slist.getSelectedItem() == null) {
			ftitle = (Title) slist.getModel().getElementAt(0);
		} else {
			ftitle = (Title) slist.getSelectedItem().getValue();
		}
		return ftitle;
	}

	/**
	 * 设置级联选择框，选择某职称
	 * 
	 * @param t
	 */
	public void setTitle(Title t) {
		this.secondTitle = t;
		this.fisrtTitle = (Title) xypttitleService.get(Title.class, t.getPtiId());
		initHbox();
	}
}
