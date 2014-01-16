package com.uniwin.framework.ui.title;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

import com.uniwin.framework.common.listbox.TitleListbox;
import com.uniwin.framework.entity.WkTAuth;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.entity.WkTTitle;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.AuthService;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.service.TitleService;

/**
 * <li>功能描述: 点击标题管理时显示的标题统揽界面
 * 
 * @author DaLei
 * @date 2010-4-7
 */
public class TitltTotalWindow extends Window implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Listbox tList;
	ListModelList tmodelList;
	// 用户可以具有管理权限标题列表
	TitleService titleService;
	WkTUser user;
	AuthService authService;
	// 页面中显示标题的父标题编号，0则全部显示
	Long tpid;
	// 选择父标题
	TitleListbox tselect;
	List<Checkbox> cklist = new ArrayList<Checkbox>();
	// 用户所在部门及下级部门列表
	List<Long> userDeptList;
	DepartmentService departmentService;

	@SuppressWarnings("unchecked")
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		userDeptList = (List<Long>) Sessions.getCurrent().getAttribute("userDeptList");
		tmodelList = new ListModelList();
		tList.setItemRenderer(new TitleTotalListRenderer());
		initWindow(0L);
		tselect.setTopName("全部");
		tselect.initNewTitleSelect(null);
		tList.setModel(tmodelList);
		tselect.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event event) throws Exception {
				Listitem sitem = tselect.getSelectedItem();
				WkTTitle t = (WkTTitle) sitem.getValue();
				initWindow(t.getKtId());
			}
		});
	}

	public void initWindow(Long tpid) {
		List<WkTTitle> tlist;
		tmodelList.clear();
		cklist.clear();
		this.tpid = tpid;
		// 用户能够访问标题列表
		if (tpid.intValue() == 0) {
			tlist = titleService.getTitlesOfUserAccess(user);
		} else {
			tlist = titleService.getTitlesOfUserAccess(user, tpid);
		}
		if (tlist != null && tlist.size() != 0) {
			tmodelList.addAll(titleService.getTitlesOfUserManager(user, userDeptList, tlist));
		}
	}

	class TitleTotalListRenderer implements ListitemRenderer {
		public void render(Listitem item, Object data) throws Exception {
			final WkTTitle t = (WkTTitle) data;
			if (tpid.intValue() != 0 && tpid.intValue() != t.getKtPid().intValue()) {
				item.setVisible(false);
			}
			item.setValue(t);
			List<WkTAuth> aulist = authService.getAuthOfTitle(userDeptList, t.getKtId());
			if (aulist.size() == 0) {
				item.setVisible(false);
			}
			Listcell c0 = new Listcell(item.getIndex() + 1 + "");
			Checkbox ck = new Checkbox(t.getKtName());
			Listcell c1 = new Listcell();
			c1.appendChild(ck);
			Listcell c2 = new Listcell();
			Listcell c3 = new Listcell();
			Listcell c4 = new Listcell();
			Vbox vb2 = new Vbox();
			c2.appendChild(vb2);
			Vbox vb3 = new Vbox();
			vb3.setSpacing("10");
			c3.appendChild(vb3);
			Vbox vb4 = new Vbox();
			c4.appendChild(vb4);
			final List<Checkbox> cboxs = new ArrayList<Checkbox>();
			for (int i = 0; i < aulist.size(); i++) {
				WkTAuth au = (WkTAuth) aulist.get(i);
				Checkbox cb2 = new Checkbox();
				cb2.setLabel(getAuthRoleName(au.getKrId()) + "/" + getAuthDeptName(au.getKdId()) + "/" + au.getIP());
				vb2.appendChild(cb2);
				cb2.setAttribute("auth", au);
				cklist.add(cb2);
				cboxs.add(cb2);
				StringBuffer sb = new StringBuffer("");
				if (au.getKaCode1().intValue() == 1) {
					sb.append("访问　");
				} else {
					sb.append("　　　");
				}
				if (au.getKaCode2().intValue() == 1) {
					sb.append("管理");
				}
				vb3.appendChild(new Label(sb.toString()));
				Hbox hb = new Hbox();
				Button b1 = new Button("新建");
				b1.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event event) throws Exception {
						WkTAuth auth = new WkTAuth();
						auth.setKrId(0L);
						auth.setKdId(0L);
						auth.setKaRid(t.getKtId());
						auth.setKaCode1(TitleAuthWindow.uncheck);
						auth.setKaCode2(TitleAuthWindow.uncheck);
						final TitleAuthDetWindow w = (TitleAuthDetWindow) Executions.createComponents("/admin/system/title/titleAuthDet.zul", null, null);
						w.doHighlighted();
						w.ininWindow(auth);
						w.addEventListener(Events.ON_CHANGE, new EventListener() {
							public void onEvent(Event event) throws Exception {
								initWindow(tpid);
								w.detach();
							}
						});
					}
				});
				Button b2 = new Button("编辑");
				b2.setAttribute("auth", au);
				b2.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event event) throws Exception {
						Button bu = (Button) event.getTarget();
						WkTAuth au = (WkTAuth) bu.getAttribute("auth");
						final TitleAuthDetWindow w = (TitleAuthDetWindow) Executions.createComponents("/admin/system/title/titleAuthDet.zul", null, null);
						w.doHighlighted();
						w.ininWindow(au);
						w.addEventListener(Events.ON_CHANGE, new EventListener() {
							public void onEvent(Event event) throws Exception {
								initWindow(tpid);
								w.detach();
							}
						});
					}
				});
				Button b3 = new Button("删除");
				b3.setAttribute("auth", au);
				b3.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event event) throws Exception {
						if (Messagebox.show("您确定要删除吗?", "请确认", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == 1) {
							Button bu = (Button) event.getTarget();
							WkTAuth au = (WkTAuth) bu.getAttribute("auth");
							authService.delete(au);
							initWindow(tpid);
						}
					}
				});
				hb.appendChild(b1);
				hb.appendChild(b2);
				hb.appendChild(b3);
				vb4.appendChild(hb);
			}
			ck.addEventListener(Events.ON_CHECK, new EventListener() {
				public void onEvent(Event event) throws Exception {
					Checkbox ck = (Checkbox) event.getTarget();
					for (int i = 0; i < cboxs.size(); i++) {
						Checkbox c = (Checkbox) cboxs.get(i);
						c.setChecked(ck.isChecked());
					}
				}
			});
			item.appendChild(c0);
			item.appendChild(c1);
			item.appendChild(c2);
			item.appendChild(c3);
			item.appendChild(c4);
		}
	}

	public void onClick$delete() throws InterruptedException {
		if (Messagebox.show("您确定要删除吗?", "请确认", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == 1) {
			for (int i = 0; i < cklist.size(); i++) {
				Checkbox cb = (Checkbox) cklist.get(i);
				if (cb.isChecked()) {
					WkTAuth au = (WkTAuth) cb.getAttribute("auth");
					authService.delete(au);
					initWindow(tpid);
				}
			}
		}
	}

	public void onClick$addTitle() {
		final TitleNewWindow w = (TitleNewWindow) Executions.createComponents("/admin/system/title/titleNew.zul", null, null);
		w.doHighlighted();
		w.setClosable(true);
		w.initWindow(null);
		w.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				refreshTree();
				w.detach();
			}
		});
	}

	public void refreshTree() {
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	public void onClick$authButton() {
		TitleAuthWindow w = (TitleAuthWindow) Executions.createComponents("/admin/system/title/titleAuth.zul", null, null);
		w.doHighlighted();
		w.initWindow(null);
		w.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event event) throws Exception {
				initWindow(tpid);
			}
		});
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
}
