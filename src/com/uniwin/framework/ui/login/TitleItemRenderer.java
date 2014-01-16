package com.uniwin.framework.ui.login;

/**
 * <li>���������Գ���,ȷ��ÿ�����ڵ���ʾ���ݼ�ֵ
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
