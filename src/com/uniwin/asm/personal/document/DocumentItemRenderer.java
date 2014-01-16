package com.uniwin.asm.personal.document;

import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;

import com.uniwin.asm.personal.entity.DocTree;

public class DocumentItemRenderer implements TreeitemRenderer {

	public void render(Treeitem item, Object data) throws Exception {
		DocTree document=(DocTree)data;
		item.setValue(document);
		item.setLabel(document.getDtName());
		item.setOpen(true);
		if(document.getDtpId()==0){
			item.setSelected(true);
		}
	}

}
