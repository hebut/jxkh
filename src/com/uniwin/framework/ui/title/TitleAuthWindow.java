package com.uniwin.framework.ui.title;

/**
 * <li>标题权限设置解析器,对应的页面为admin\system\title\titleAuth.zul
 * @2010-3-15
 * @author DaLei
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.api.Treeitem;

import com.uniwin.common.util.IPUtil;
import com.uniwin.framework.common.listbox.DeptListbox;
import com.uniwin.framework.common.listbox.DeptRoleListbox;
import com.uniwin.framework.common.listbox.TitleAuthListbox;
import com.uniwin.framework.entity.WkTAuth;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTMlog;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.entity.WkTTitle;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.AuthService;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.service.MLogService;
import com.uniwin.framework.service.RoleService;

@SuppressWarnings("unchecked")
public class TitleAuthWindow extends Window implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6646263082699757069L;
	// 当前编辑权限的标题
	WkTTitle editTitle;
	// 角色搜索组件
	Combobox ktrole, ktdept;
	// 角色列表组件
	DeptRoleListbox rolelist;
	RoleService roleService;
	// 标题选择组件，可以一次选择多个标题
	Button titleChoose;
	// 页面显示选择标题名称
	Textbox ktname, authIp;
	// 组织机构选择组件
	DeptListbox deptlist;
	WkTUser user;
	DepartmentService departmentService;
	// 当前用户部门列表
	List<Long> userDeptList;
	TitleAuthListbox hasAuth;
	// 编辑的标题列表
	List<WkTTitle> tList = new ArrayList<WkTTitle>();
	// 显示当前权限
	Label nowAuth;
	Checkbox accessAuth, managerAuth;
	Radio totalSet, addSet;
	AuthService authService;
	public static final Short check = 1, uncheck = 0;
	List<Long> rids, dids;
	MLogService mlogService;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		userDeptList = (List<Long>) Sessions.getCurrent().getAttribute("userDeptList");
		// 当选中列表框项时，底下角色自动选中
		ktrole.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event event) throws Exception {
				Comboitem it = ktrole.getSelectedItem();
				if (it != null) {
					WkTRole sr = (WkTRole) it.getValue();
					setRoleListBoxSelected(sr.getKrId());
				}
			}
		});
		ktdept.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event event) throws Exception {
				Comboitem it = ktdept.getSelectedItem();
				if (it != null) {
					Long did = (Long) it.getValue();
					setDeptListBoxSelected(did);
				}
			}
		});
		// 重新选择编辑权限的标题
		titleChoose.addEventListener(Events.ON_CLICK, new EventListener() {
			public void onEvent(Event event) throws Exception {
				TitleAuditMulSelecteWindow w = (TitleAuditMulSelecteWindow) Executions.createComponents("/admin/system/title/tMulSelected.zul", null, null);
				w.setClosable(true);
				w.doHighlighted();
				w.initWindow(tList);
				w.addEventListener(Events.ON_CHANGE, new EventListener() {
					public void onEvent(Event event) throws Exception {
						TitleAuditMulSelecteWindow w = (TitleAuditMulSelecteWindow) event.getTarget();
						Set<Treeitem> sel = w.getTree().getSelectedItems();
						Iterator<Treeitem> it = sel.iterator();
						String tname = "";
						while (it.hasNext()) {
							Treeitem item = (Treeitem) it.next();
							WkTTitle ti = (WkTTitle) item.getValue();
							tList.add(ti);
							tname = tname + ti.getKtName() + ",";
						}
						ktname.setValue(tname);
						w.detach();
					}
				});
			}
		});
		rolelist.addEventListener(Events.ON_SELECT,new EventListener() {
			public void onEvent(Event event) throws Exception {
				 Set<Listitem> items=rolelist.getSelectedItems();
				 Iterator<Listitem>  iteratoritems=items.iterator();
				 while(iteratoritems.hasNext()){
					 Listitem item=iteratoritems.next();
					 WkTRole wktRole=(WkTRole)item.getValue();
					 if(wktRole.getKrId().intValue()==0) return;
					 chooseRoleListBoxSelected(wktRole.getKrId());
				 }

			}
		});
		deptlist.addEventListener(Events.ON_SELECT,new EventListener() {
			public void onEvent(Event event) throws Exception {
				 Set<Listitem> items=deptlist.getSelectedItems();
				 Iterator<Listitem>  iteratoritems=items.iterator();
				 while(iteratoritems.hasNext()){
					 Listitem item=iteratoritems.next();
					 WkTDept wktDept=(WkTDept)item.getValue();
					 if(wktDept.getKdId().intValue()==0) return;
					 chooseDeptListBoxSelected(wktDept.getKdId());
				 }

			}
		});
	}

	protected void chooseDeptListBoxSelected(Long kdId) {
		List<WkTDept> dlist= departmentService.getChildDepdw(kdId);
		List<WkTDept> dclist;
		StringBuffer sb=new StringBuffer(",");
		for(WkTDept w: dlist){
			sb.append(w.getKdId()+",");
			dclist=departmentService.getChildDepdw(w.getKdId());
			for(WkTDept wdc: dclist){
				sb.append(wdc.getKdId()+",");
			}
		}
		if(sb.toString().length()>1){
			
			List<Listitem> rlist = deptlist.getChildren();
			for (int i = 0; i < rlist.size(); i++) {
				
				Listitem it = (Listitem) rlist.get(i);
				
				WkTDept rl = (WkTDept) it.getValue();
				if(rl!=null){
					if(sb.toString().contains(","+rl.getKdId().toString()+",")) {
						it.setSelected(true);
					}
				}
			}
		}
	}

	private void chooseRoleListBoxSelected(Long krId) {
		
		List<WkTRole> clist = roleService.getChildRole(krId);
		StringBuffer sb=new StringBuffer(",");
		for(WkTRole w:clist){
			sb.append(w.getKrId()+",");
		}
		List<Listitem> rlist = rolelist.getChildren();
		for (int i = 0; i < rlist.size(); i++) {
			
			Listitem it = (Listitem) rlist.get(i);
			
			WkTRole rl = (WkTRole) it.getValue();
			if(sb.toString().contains(","+rl.getKrId().toString()+",")) {
				it.setSelected(true);
			}
		}
	}

	public void onClick$submit() throws InterruptedException {
		if (tList.size() == 0) {
			Messagebox.show("请选择标题", "错误提示", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		List<WkTAuth> aulist = hasAuth.getAmodelList().getInnerList();
		StringBuffer sb = new StringBuffer("编辑标题权限，id:");
		if (totalSet.isChecked()) {// 完全设置，需要现将原的删除
			for (int i = 0; i < tList.size(); i++) {
				WkTTitle t = (WkTTitle) tList.get(i);
				authService.deleteAuthOfTitle(t.getKtId(), rids, dids);
				for (int j = 0; j < aulist.size(); j++) {
					WkTAuth au = (WkTAuth) aulist.get(j);
					au.setKaRid(t.getKtId());
					authService.save(au);
				}
				sb.append(t.getKtId() + ",");
			}
		} else { // 增量设置，首先需要判断已有权限中是否已经含有相同权限了
			for (int i = 0; i < tList.size(); i++) {
				WkTTitle t = (WkTTitle) tList.get(i);
				sb.append(t.getKtId() + ",");
				for (int j = 0; j < aulist.size(); j++) {
					WkTAuth au = (WkTAuth) aulist.get(j);
					au.setKaRid(t.getKtId());
					List<WkTAuth> mlist = authService.findByExample(au);
					if (mlist.size() == 0) {
						authService.save(au);
					}
				}
			}
		}
		Messagebox.show("保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
		mlogService.saveMLog(WkTMlog.FUNC_ADMIN, sb.toString(), user);
		Events.postEvent(Events.ON_CHANGE, this, null);
		this.detach();
	}
	public void onClick$addAuth() throws InterruptedException {
		if (!(accessAuth.isChecked() || managerAuth.isChecked())) {
			Messagebox.show("请设置权限类别", "错误提示", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		
		Set<Listitem> rset = rolelist.getSelectedItems();
		Set<Listitem> dset = deptlist.getSelectedItems();
		List<Listitem> rlist = new ArrayList<Listitem>(rset);
		List<Listitem> dlist = new ArrayList<Listitem>(dset);
		for (int i = 0; i < rlist.size(); i++) {
			Listitem ritem = (Listitem) rlist.get(i);
			WkTRole role = (WkTRole) ritem.getValue();
			if (role.getKrPid() != null && role.getKrPid().intValue() == 0)
				continue;
			for (int j = 0; j < dlist.size(); j++) {
				Listitem ditem = (Listitem) dlist.get(j);
				WkTDept dept = (WkTDept) ditem.getValue();
				// 只有超级管理员才可以设置任意角色、任意部门同时选择
				if (role.getKrId().intValue() == 0 && dept.getKdId().intValue() == 0) {
					if (user.getKdId().intValue() != 0) {
						return;
					}
				}
				WkTAuth au = new WkTAuth();
				au.setKaType(WkTAuth.TYPE_TITLE);
				au.setKrId(role.getKrId());
				au.setKdId(dept.getKdId());
				if (accessAuth.isChecked()) {
					au.setKaCode1(TitleAuthWindow.check);
				} else {
					au.setKaCode1(TitleAuthWindow.uncheck);
				}
				if (managerAuth.isChecked()) {
					au.setKaCode2(TitleAuthWindow.check);
				} else {
					au.setKaCode2(TitleAuthWindow.uncheck);
				}
				au.setKaCode(au.getKaCode1() + "" + au.getKaCode2());
				try {
					if (authIp.getValue().length() > 0)
						IPUtil.setIP(au, authIp.getValue());
				} catch (Exception e) {
					Messagebox.show("IP地址错误(正确格式如:192.168.*.100-255)", "错误提示", Messagebox.OK, Messagebox.INFORMATION);
					return;
				}
				au.setKaType(WkTAuth.TYPE_TITLE + " ");
				hasAuth.getAmodelList().add(au);
			}
		}
	}
	public void onClick$deleteAuth() {
		Set<Listitem> sitems = hasAuth.getSelectedItems();
		Iterator<Listitem> it = sitems.iterator();
		List<Object> dlist = new ArrayList<Object>();
		while (it.hasNext()) {
			Listitem item = (Listitem) it.next();
			dlist.add(item.getValue());
		}
		hasAuth.getAmodelList().removeAll(dlist);
	}

	public void onClick$clearAuth() {
		hasAuth.getAmodelList().clear();
	}

	public void onSelect$hasAuth() {
		Listitem se = hasAuth.getSelectedItem();
		WkTAuth au = (WkTAuth) se.getValue();
		setRoleListBoxSelected(au.getKrId());
		setDeptListBoxSelected(au.getKdId());
		nowAuth.setValue(se.getLabel());
		authIp.setValue(au.getIP());
		if (au.getKaCode1().intValue() == 1) {
			accessAuth.setChecked(true);
		} else {
			accessAuth.setChecked(false);
		}
		if (au.getKaCode2().intValue() == 1) {
			managerAuth.setChecked(true);
		} else {
			managerAuth.setChecked(false);
		}
	}

	public void onClick$reset() {
		initWindow(getEditTitle());
	}

	public void onClick$close() {
		this.detach();
	}

	/**
	 * <li>功能描述：选中角色列表中一项。
	 * 
	 * @param rid
	 *            角色编号 void
	 * @author DaLei
	 */
	public void setRoleListBoxSelected(Long rid) {
		
		List<Listitem> rlist = rolelist.getChildren();
		for (int i = 0; i < rlist.size(); i++) {
			Listitem it = (Listitem) rlist.get(i);
			it.setSelected(false);
			WkTRole rl = (WkTRole) it.getValue();
			if (rid.intValue() == rl.getKrId().intValue()) {
				it.setSelected(true);
			}
		}
	}

	/**
	 * <li>功能描述：选中部门列表中一项。
	 * 
	 * @param did
	 *            部门编号 void
	 * @author DaLei
	 */
	public void setDeptListBoxSelected(Long did) {
		List<Listitem> rlist = deptlist.getChildren();
		for (int i = 0; i < rlist.size(); i++) {
			Listitem it = (Listitem) rlist.get(i);
			it.setSelected(false);
			WkTDept d = (WkTDept) it.getValue();
			if(d!=null){
				if (did.intValue() == d.getKdId().intValue()) {
					it.setSelected(true);
				}
			}
		}
	}

	public WkTTitle getEditTitle() {
		return editTitle;
	}

	/**
	 * <li>功能描述：初始化标题权限界面
	 * 
	 * @param editTitle
	 *            void
	 * @author DaLei
	 * @2010-3-16
	 */
	public void initWindow(WkTTitle editTitle) {
		WkTDept pdept = (WkTDept) departmentService.get(WkTDept.class, user.getKdId());
		deptlist.setRootDept(pdept);
		deptlist.setRootID(user.getKdId());
		deptlist.initNewDeptSelect(deptlist.getRootDept());
		if (user.getKdId().intValue() != 0) {
			WkTDept rootDept = new WkTDept();
			rootDept.setKdName(" ");
			rootDept.setKdId(0L);
			rootDept.setDep(0);
			deptlist.getDmodelList().add(0, rootDept);
			deptlist.setRootDept(rootDept);
		} else {
			deptlist.getRootDept().setKdName(" ");
		}
		rolelist.setRootRole(null);
		rolelist.setDeptList(userDeptList);
		rolelist.initRoleSelect(0L);
		rids = rolelist.getRidList();
		dids = deptlist.getDidList();
		tList.clear();
		if (editTitle != null) {
			hasAuth.initAuthList(editTitle.getKtId(), rids, dids);
			tList.add(editTitle);
			this.editTitle = editTitle;
			this.setTitle("编辑标题：" + editTitle.getKtName());
			// 更新标题名称
			ktname.setValue(editTitle.getKtName());
		}
		nowAuth.setValue("");
		accessAuth.setChecked(false);
		managerAuth.setChecked(false);
		authIp.setValue("");
	}
}
