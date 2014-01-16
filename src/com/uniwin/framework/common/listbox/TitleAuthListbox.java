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

/**
 * <li>功能描述:某个标题的权限列表，在标题的权限管理中显示
 * 
 * @author DaLei
 * @date 2010-4-8
 */
public class TitleAuthListbox extends Listbox implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 管理部门编号列表
	//List dlist;
	// 管理角色编号列表
	//List rlist;
	ListModelList amodelList;
	AuthService authService;

	public void afterCompose() {
		Components.wireVariables(this, this);
		setItemRenderer(new AuthListitemRenderer());
		amodelList = new ListModelList();
		this.setModel(amodelList);
	}

	public void initAuthList(Long tid, List<Long> rlist, List<Long> dlist) {
		amodelList.clear();
		List<WkTAuth> alist = authService.getAuthOfTitle(tid, rlist, dlist);
		amodelList.addAll(alist);
	}

	class AuthListitemRenderer implements ListitemRenderer {
		public void render(Listitem item, Object arg1) throws Exception {
			WkTAuth ta = (WkTAuth) arg1;
			item.setLabel("　" + getAuthRoleName(ta.getKrId()) + "," + getAuthDeptName(ta.getKdId()) + "," + ta.getIP() + "," + ta.getKaCode());
			item.setValue(arg1);
		}
	}

	public String getAuthRoleName(Long rid) {
		if (rid.intValue() == 0)
			return "";
		WkTRole r = (WkTRole) authService.get(WkTRole.class, rid);
		return r.getKrName();
	}

	public String getAuthDeptName(Long did) {
		if (did.intValue() == 0)
			return "";
		WkTDept r = (WkTDept) authService.get(WkTDept.class, did);
		return r.getKdName();
	}

	public ListModelList getAmodelList() {
		return amodelList;
	}

	public void setAmodelList(ListModelList amodelList) {
		this.amodelList = amodelList;
	}
}
