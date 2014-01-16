package org.iti.xypt.personal.infoCollect.newsmanage;

import org.iti.xypt.personal.infoCollect.entity.WkTWebsite;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;



public class ManageItemRenderer implements TreeitemRenderer {

	public void render(Treeitem item, Object data) throws Exception {
		WkTWebsite chanel=(WkTWebsite)data;
		item.setValue(chanel);
		item.setLabel(chanel.getkwName());
		item.setOpen(true);
	}

}
