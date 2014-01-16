package org.iti.jxkh.indicator.businessdept;


import java.util.List;
import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.service.BusinessIndicatorService;
import org.zkoss.zul.AbstractTreeModel;




public class BusinessTreeModel extends AbstractTreeModel {

	private static final long serialVersionUID = -9031880675642847049L;
	BusinessIndicatorService businessIndicatorService;

	public BusinessTreeModel(Object root, BusinessIndicatorService businessIndicatorService) {
		super(root);
		this.businessIndicatorService = businessIndicatorService;
	}
//由父栏目对象找孩子栏目对象
	public Object getChild(Object parent, int index) {
		if (parent instanceof Jxkh_BusinessIndicator) {
			Jxkh_BusinessIndicator b= (Jxkh_BusinessIndicator) parent;
			return businessIndicatorService.getChildBusiness(b.getKbId()).get(index);
		} else if (parent instanceof List) {
			List clist = (List) parent;
			return clist.get(index);
		}
		return null;
	}
//获得孩子的数目
	public int getChildCount(Object parent) {
		if (parent instanceof Jxkh_BusinessIndicator) {
			Jxkh_BusinessIndicator b = (Jxkh_BusinessIndicator) parent;
			return businessIndicatorService.getChildBusiness(b.getKbId()).size();
		} else if (parent instanceof List) {
			List clist = (List) parent;
			return clist.size();
		}
		return 0;
	}
//判断当前节点是否为叶子节点
	public boolean isLeaf(Object node) {
		if (node instanceof Jxkh_BusinessIndicator) {
			Jxkh_BusinessIndicator b = (Jxkh_BusinessIndicator) node;
			return businessIndicatorService.getChildBusiness(b.getKbId()).size() > 0 ? false
					: true;
		} else if (node instanceof List) {
			List clist = (List) node;
			return clist.size() > 0 ? false : true;
		}
		return true;
	}

}
