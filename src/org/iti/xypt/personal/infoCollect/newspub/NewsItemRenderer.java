package org.iti.xypt.personal.infoCollect.newspub;
import org.iti.xypt.personal.infoCollect.entity.WkTWebsite;
import org.iti.xypt.personal.infoCollect.service.NewsService;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;




public class NewsItemRenderer implements TreeitemRenderer {
	NewsService newsService;
	public void render(Treeitem item, Object data) throws Exception {
		WkTWebsite title=(WkTWebsite)data;
		item.setValue(title);
		item.setLabel(title.getkwName());	
		item.setHeight("20px");
}
}
