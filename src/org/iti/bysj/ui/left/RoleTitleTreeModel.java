/**
 * 
 */
package org.iti.bysj.ui.left;

import java.util.List;

import org.iti.xypt.entity.XyUserrole;
import org.zkoss.zul.AbstractTreeModel;

import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.entity.WkTTitle;
import com.uniwin.framework.service.TitleService;

/**
 *
 * @author DaLei
 * @version $Id: RoleTitleTreeModel.java,v 1.1 2011/08/31 07:03:09 ljb Exp $
 */
public class RoleTitleTreeModel extends AbstractTreeModel {
	
	TitleService titleService;
	Long ptid;
	
	public RoleTitleTreeModel(Object root,TitleService titleService,Long ptid){
		super(root);
		this.titleService=titleService;
		this.ptid=ptid;
	}

	/* (non-Javadoc)
	 * @see org.zkoss.zul.TreeModel#getChild(java.lang.Object, int)
	 */	
	public Object getChild(Object arg0, int arg1) {
		if(arg0 instanceof XyUserrole){
			XyUserrole urole=(XyUserrole)arg0;
			return titleService.getTitleOfRoleAccess(ptid, urole.getId().getKrId()).get(arg1);
		}else if(arg0 instanceof List){
			List tlist=(List)arg0;
			return tlist.get(arg1);
		}else if(arg0 instanceof WkTTitle){
			WkTTitle t=(WkTTitle)arg0;
			return titleService.getChildTitle(t.getKtId()).get(arg1);
		}else if(arg0 instanceof WkTRole){
			WkTRole r=(WkTRole)arg0;
			return titleService.getTitleOfRoleAccess(ptid, r.getKrId()).get(arg1);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.zkoss.zul.TreeModel#getChildCount(java.lang.Object)
	 */
	public int getChildCount(Object arg0) {
		if(arg0 instanceof XyUserrole){
			XyUserrole urole=(XyUserrole)arg0;
			return titleService.getTitleOfRoleAccess(ptid, urole.getId().getKrId()).size();
		}else if(arg0 instanceof List){
			List tlist=(List)arg0;
			return tlist.size();
		}else if(arg0 instanceof WkTTitle){
			WkTTitle t=(WkTTitle)arg0;
			return titleService.getChildTitle(t.getKtId()).size();
		}else if(arg0 instanceof WkTRole){
			WkTRole r=(WkTRole)arg0;
			return titleService.getTitleOfRoleAccess(ptid, r.getKrId()).size();
		}
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.zkoss.zul.TreeModel#isLeaf(java.lang.Object)
	 */
	public boolean isLeaf(Object arg0) {
		if(arg0 instanceof XyUserrole){
			XyUserrole urole=(XyUserrole)arg0;
			return titleService.getTitleOfRoleAccess(ptid, urole.getId().getKrId()).size()==0;
		}else if(arg0 instanceof List){
			List tlist=(List)arg0;
			return tlist.size()==0;
		}else if(arg0 instanceof WkTTitle){
			WkTTitle t=(WkTTitle)arg0;
			return titleService.getChildTitle(t.getKtId()).size()==0;
		}else if(arg0 instanceof WkTRole){
			WkTRole r=(WkTRole)arg0;
			return titleService.getTitleOfRoleAccess(ptid, r.getKrId()).size()==0;
		}
		return true;
	}

}
