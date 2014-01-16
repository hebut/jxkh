package com.uniwin.framework.ui.dept;

/**
 * <li>组织机构树的显呈器
 * @author DaLei
 * @2010-3-17
 */
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;

import com.uniwin.framework.entity.WkTDept;

public class DepartmentItemRenderer implements TreeitemRenderer {
	public void render(Treeitem item, Object data) throws Exception {
		WkTDept d = (WkTDept) data;
		if (d.getKdType().trim().equalsIgnoreCase(WkTDept.BUMEN)) {
			item.setImage("/images/tree/dept/bumen.gif");
		} else if(d.getKdType().trim().equalsIgnoreCase(WkTDept.DANWEI)){
			item.setImage("/images/tree/dept/danwei.gif");
		}else{
			item.setImage("/images/tree/dept/xueke.gif");
		}
		item.setHeight("25px");
		item.setValue(d);
		item.setLabel(d.getKdName());
	}
}
