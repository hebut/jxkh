package com.uniwin.framework.ui.login;

/**
 * <li>标题树的显呈器,确定每个树节点显示内容及值
 * @author DaLei
 * @2010-3-15
 */
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;

import com.uniwin.framework.entity.WkTTitle;

public class TitleItemRenderer implements TreeitemRenderer {
	public void render(Treeitem item, Object data) throws Exception {
		WkTTitle title = (WkTTitle) data;
		item.setValue(title);
		item.setLabel(title.getKtName());
	}
}
