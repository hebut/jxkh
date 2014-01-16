package com.uniwin.asm.personal.document;

import java.util.List;

import org.zkoss.zul.AbstractTreeModel;

import com.uniwin.asm.personal.entity.DocTree;
import com.uniwin.asm.personal.service.DocTreeService;

public class DocumentTreeModel extends AbstractTreeModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DocTreeService documentService;
	public DocumentTreeModel(Object root,DocTreeService documentService) {
		super(root);
		this.documentService=documentService;
	}

	public boolean isLeaf(Object node) {
		if (node instanceof DocTree) {
			DocTree d = (DocTree) node;
			return documentService.getChildernDocTree(d.getDtId()).size() > 0 ? false : true;
		} else if (node instanceof List) {
			List clist = (List) node;
			return clist.size() > 0 ? false : true;
		}
		return true;
	}

	public Object getChild(Object parent, int index) {
		if (parent instanceof DocTree) {
			DocTree d = (DocTree) parent;
			return documentService.getChildernDocTree(d.getDtId()).get(index);
		} else if (parent instanceof List) {
			List clist = (List) parent;
			return clist.get(index);
		}
		return null;
	}

	public int getChildCount(Object parent) {
		if (parent instanceof DocTree) {
			DocTree d = (DocTree) parent;
			return documentService.getChildernDocTree(d.getDtId()).size();
		} else if (parent instanceof List) {
			List clist = (List) parent;
			return clist.size();
		}
		return 0;
	}

}
