package com.uniwin.framework.ui.userlogin;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;

import com.uniwin.framework.entity.WkTTitle;

public class TitleItemRenderer implements TreeitemRenderer {
	public void render(Treeitem item, Object data) throws Exception {
		WkTTitle title = (WkTTitle) data;
		item.setValue(title);
		Treerow row = new Treerow();
		Treecell cell = new Treecell();
		cell.setLabel(title.getKtName());
		row.appendChild(cell);
		item.appendChild(row);
		item.setHeight("50px");
		cell.addEventListener("onClick", new EventListener(){
			public void onEvent(Event event) throws Exception {
				Treeitem item = (Treeitem)event.getTarget().getParent().getParent();
				if(item.getTreechildren()!=null)
				{
					if(item.isOpen())
					{
						item.setOpen(false);
					}else{
						item.setOpen(true);
					}
				}
			}
			
		});
	}
}
