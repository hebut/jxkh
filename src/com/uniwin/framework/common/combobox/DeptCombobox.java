package com.uniwin.framework.common.combobox;

/**
 * <li>部门下拉框组件,显示当前登陆用户的所在部门及下级部门列表
 */
import java.util.List;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.ListModelList;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.service.DepartmentService;

public class DeptCombobox extends Combobox implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ListModelList dmodelList;
	//List userDeptList;
	DepartmentService departmentService;

	@SuppressWarnings("unchecked")
	public void afterCompose() {
		Components.wireVariables(this, this);
		//userDeptList = (List) Sessions.getCurrent().getAttribute("userDeptList");
		dmodelList = new ListModelList((List<Long>) Sessions.getCurrent().getAttribute("userDeptList"));
		this.setModel(dmodelList);
		this.setItemRenderer(new ComboitemRenderer() {
			public void render(Comboitem item, Object data) throws Exception {
				Long did = (Long) data;
				item.setValue(did);
				WkTDept d = (WkTDept) departmentService.get(WkTDept.class, did);
				if (d != null) {
					item.setLabel(d.getKdName().trim());
				} else {
					item.setLabel("　");
				}
			}
		});
	}
}
