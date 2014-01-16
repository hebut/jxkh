package com.uniwin.framework.common.listbox;

import java.util.List;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.uniwin.framework.entity.WkTAuth;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.service.AuthService;



public class WebsiteAuthListbox extends Listbox implements AfterCompose {
	
	List dlist;
	List rlist;
	ListModelList amodelList;
	AuthService authService;

	public void afterCompose() {
		Components.wireVariables(this, this);		
		setItemRenderer(new AuthListitemRenderer());
		amodelList=new ListModelList();
		this.setModel(amodelList);
		

	}
	public void initAuthList(Long wid,List rlist,List dlist){
		amodelList.clear();
		//List alist=authService.getAuthOfWebsite(wid, rlist, dlist); 
	//	amodelList.addAll(alist);		
	}
	
	class AuthListitemRenderer implements ListitemRenderer{
		public void render(Listitem item, Object arg1) throws Exception {
			WkTAuth ta=(WkTAuth)arg1;
			item.setLabel("¡¡"+getAuthRoleName(ta.getKrId())+","+getAuthDeptName(ta.getKdId())+","+ta.getIP()+","+ta.getKaCode());
			item.setValue(arg1);
		}		
	}

	public String getAuthRoleName(Long rid){
		if(rid.intValue()==0) return "";
		WkTRole r=(WkTRole)authService.get(WkTRole.class,rid);
		return r.getKrName();
	}
	public String getAuthDeptName(Long did){
		if(did.intValue()==0) return "";
		WkTDept r=(WkTDept)authService.get(WkTDept.class, did);
		return r.getKdName();
	}

	public ListModelList getAmodelList() {
		return amodelList;
	}

	public void setAmodelList(ListModelList amodelList) {
		this.amodelList = amodelList;
	}
}
