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
	// �������ݷ���
	TitleService titleService;
	// Listbox ��һ����ƣ���ӵ�һ�ַ���
	String topTitle;
	// session�д洢��½�û���Ȩ���б�,�ڵ�½��������õ�
	List ulist;
	// ����ģ���б�
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
					bla += "��";
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
		// ����û��������û���
		List rgroupList = roleService.getProleOfUser(user.getKuId());
		rolegroup.setModel(new ListModelList(rgroupList));
		// ����ĳ���û�����ȷ������û���ӵ�еĽ�ɫ
		loadrole();
		loadfunc();
		// ����ĳ����ɫ���г������й���ģ��
	}

	/**
	 * ��ѡ�н�ɫ��󣬳�ʼ��ĳ����ɫ���еĽ�ɫ
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
		if (getTopTitle() != null) { // ���ڿ���LIstbox���һ�У�Ϊ�ղ���ʾ��
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
	 * <li>��������������ղ�ʱ,����ֻ��ʾ���������������ʼ��һ������
	 * 
	 * @param ListModelList
	 *            rml
	 * @param Long
	 *            pid Title��Ĳ��ź�
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
		// ��ʼ��һ������
		for (int i = 0; i < olist.size(); i++) {
			WkTAuth auth = (WkTAuth) olist.get(i);
			WkTTitle title = auth.getTitle();
			// �жϴ˱����Ƿ��Ƕ�������
			if (title.getKtPid() == 1) {
				Long ktid = title.getKtId();
				title.setDep(1);
				rml.add(title);
				appendTwoTitle(rml, ktid, ro.getKrId());
			}
		}
	}

	/**
	 * <li>������������Ӷ�������
	 * 
	 * @param ListModelList
	 *            rml
	 * @param Long
	 *            pid Title��Ĳ��ź�
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
	 * <li>���������������������
	 * 
	 * @param ListModelList
	 *            rml
	 * @param Long
	 *            pid Title��Ĳ��ź�
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
