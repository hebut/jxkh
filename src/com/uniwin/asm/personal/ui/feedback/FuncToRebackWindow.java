package com.uniwin.asm.personal.ui.feedback;

import java.util.List;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTAuth;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.entity.WkTTitle;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.AuthService;
import com.uniwin.framework.service.RoleService;
import com.uniwin.framework.service.TitleService;
import com.uniwin.framework.service.UserService;

public class FuncToRebackWindow extends Window implements AfterCompose {
	Listbox func, role, rolegroup;
	WkTUser user;
	RoleService roleService;
	UserService userService;
	AuthService authService;
	// 标题数据访问
	TitleService titleService;
	// Listbox 第一项控制，添加的一字符串
	String topTitle;
	// session中存储登陆用户的权限列表,在登陆组件中设置的
	List ulist;
	// 标题模型列表
	ListModelList tmodelList;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		ulist = (List) Sessions.getCurrent().getAttribute("ulist");
		titleService = (TitleService) SpringUtil.getBean("titleService");
		rolegroup.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				WkTRole role = (WkTRole) arg1;
				arg0.setValue(arg1);
				arg0.setLabel(role.getKrName());
			}
		});
		role.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				WkTRole role = (WkTRole) arg1;
				arg0.setValue(arg1);
				arg0.setLabel(role.getKrName());
			}
		});
		func.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception {
				WkTTitle t = (WkTTitle) data;
				item.setValue(t);
				int dep = t.getDep();
				String bla = "";
				while (dep > 0) {
					bla += "　";
					dep--;
				}
				item.setLabel(bla + t.getKtName());
			}
		});
		rolegroup.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				loadrole();
			}
		});
		role.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				loadfunc();
			}
		});
	}

	public void loadList() {
		// 获得用户的所有用户组
		List rgroupList = roleService.getProleOfUser(user.getKuId());
		rolegroup.setModel(new ListModelList(rgroupList));
		// 根据某个用户组来确定这个用户所拥有的角色
		loadrole();
		loadfunc();
		// 根据某个角色来列出其所有功能模块
	}

	/**
	 * 当选中角色组后，初始化某个角色组中的角色
	 */
	public void loadrole() {
		WkTRole ro;
		if (rolegroup.getSelectedItem() != null) {
			ro = (WkTRole) rolegroup.getSelectedItem().getValue();
		} else {
			ro = (WkTRole) rolegroup.getModel().getElementAt(0);
		}
		List roleList = roleService.getChildRoleByKuid(ro.getKrId(), user.getKuId());
		role.setModel(new ListModelList(roleList));
	}

	public void loadfunc() {
		tmodelList = new ListModelList();
		if (getTopTitle() != null) { // 用于控制LIstbox表第一列，为空不显示。
			WkTTitle t = new WkTTitle();
			t.setKtId(0L);
			t.setKtName(getTopTitle());
			t.setDep(0);
			tmodelList.add(t);
		}
		addTitleListBoxItem(tmodelList);
		func.setModel(tmodelList);
		if(func.getItemCount()>0){
			WkTTitle tit =(WkTTitle)Sessions.getCurrent().getAttribute("leftTitleTreeitem");
			for(int i=0;i<tmodelList.size();i++){
				WkTTitle t=(WkTTitle)tmodelList.get(i);
				if(tit!=null&&t.getKtName().trim().equals(tit.getKtName().trim())){
					func.setSelectedIndex(i);
					break;
				}
			}
			
		}
	}

	/**
	 * <li>功能描述：添加收藏时,标题只显示到第三级。线面初始化一级标题
	 * 
	 * @param ListModelList
	 *            rml
	 * @param Long
	 *            pid Title表的部门号
	 * @author bobo
	 * @2010-4-16
	 */
	public void addTitleListBoxItem(ListModelList rml) {
		WkTRole ro;
		if (role.getSelectedItem() != null) {
			ro = (WkTRole) role.getSelectedItem().getValue();
		} else {
			ro = (WkTRole) role.getModel().getElementAt(0);
		}
		List olist = authService.findByKridAndTypeAndCode(ro.getKrId(), WkTAuth.TYPE_TITLE, WkTAuth.KACODE1_OK);
		// 初始化一级标题
		for (int i = 0; i < olist.size(); i++) {
			WkTAuth auth = (WkTAuth) olist.get(i);
			WkTTitle title = auth.getTitle();
			// 判断此标题是否是顶级标题
			if (title.getKtPid() == 1) {
				Long ktid = title.getKtId();
				title.setDep(1);
				rml.add(title);
				appendTwoTitle(rml, ktid, ro.getKrId());
			}
		}
	}

	/**
	 * <li>功能描述：添加二级标题
	 * 
	 * @param ListModelList
	 *            rml
	 * @param Long
	 *            pid Title表的部门号
	 * @author bobo
	 * @2010-4-16
	 */
	public void appendTwoTitle(ListModelList rml, Long pid, Long krid) {
		List twolist = titleService.getChildTitleInlist(pid, krid, WkTAuth.TYPE_TITLE, WkTAuth.KACODE1_OK);
		for (int i = 0; i < twolist.size(); i++) {
			WkTTitle title = (WkTTitle) twolist.get(i);
			title.setDep(2);
			rml.add(title);
			appendThreeTitle(rml, title.getKtId(), krid);
		}
	}

	/**
	 * <li>功能描述：添加三级标题
	 * 
	 * @param ListModelList
	 *            rml
	 * @param Long
	 *            pid Title表的部门号
	 * @author bobo
	 * @2010-4-16
	 */
	public void appendThreeTitle(ListModelList rml, Long pid, Long krid) {
		List threelist = titleService.getChildTitleInlist(pid, krid, WkTAuth.TYPE_TITLE, WkTAuth.KACODE1_OK);
		for (int i = 0; i < threelist.size(); i++) {
			WkTTitle title = (WkTTitle) threelist.get(i);
			title.setDep(3);
			rml.add(title);
		}
	}

	public void onClick$submit() {
		final NewFeedbackWindow newfeedWin = (NewFeedbackWindow) Executions.createComponents("/admin/feedback/new/new_feedback.zul", null, null);
		Listitem item;
		if (role.getSelectedItem() != null) {
			item = role.getSelectedItem();
			newfeedWin.setRole((WkTRole) item.getValue());
		} else {
			newfeedWin.setRole((WkTRole) role.getModel().getElementAt(0));
		}
		if (func.getSelectedItem() != null) {
			item = func.getSelectedItem();
			newfeedWin.setTi((WkTTitle) item.getValue());
		}else{
			item = func.getItemAtIndex(0);
			newfeedWin.setTi((WkTTitle) item.getValue());
		}
		newfeedWin.setUser(user);
		newfeedWin.initWindow();
		newfeedWin.doHighlighted();
		newfeedWin.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				newfeedWin.detach();
				sendEvents();
			}
		});
	}

	public void sendEvents() {
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	public void onClick$cancel() {
		this.detach();
	}

	public WkTUser getUser() {
		return user;
	}

	public void setUser(WkTUser user) {
		this.user = user;
	}

	public String getTopTitle() {
		return topTitle;
	}

	public void setTopRole(String topTitle) {
		this.topTitle = topTitle;
	}
}
