package com.uniwin.framework.ui.role;

/**
 * <li>½ÇÉ«Ê÷µÄÏÔ³ÌÆ÷
 * @author DaLei
 * @2010-3-17
 */
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;

import com.uniwin.framework.entity.WkTRole;

public class RoleItemRenderer implements TreeitemRenderer {
	public void render(Treeitem item, Object data) throws Exception {
		WkTRole r = (WkTRole) data;
		item.setValue(r);
		item.setLabel(r.getKrName());
	}
}
