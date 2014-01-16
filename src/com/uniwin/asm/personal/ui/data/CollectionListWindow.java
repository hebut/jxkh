package com.uniwin.asm.personal.ui.data;

/**
 * 个人收藏页面
 * @author bobo
 * @date 创建日期 Mar 23, 2010 
 * 
 */

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.iti.bysj.ui.base.InnerButton;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zkex.zul.West;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Window;
import com.uniwin.asm.personal.entity.WkTUsrfav;
import com.uniwin.asm.personal.service.UsrfavService;
import com.uniwin.framework.entity.WkTTitle;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.TitleService;

public class CollectionListWindow extends Window implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UsrfavService usrfavService;
	// 数据绑定器
	private AnnotateDataBinder binder;
	// 收藏集合
	private Map params;
	// 收藏列表
	private List usrfavList;
	// 发送消息列表框
	private Listbox userfavlistbox;
	// 消息实体
	private WkTUsrfav userFavTList, selected;
	// 添加和删除按钮
	private Button delCol;
	// 维护页面中的标签页组件
	Tabbox centerTabbox;
	// 页面中左侧导航条部分对应对象
	West westLeft;
	// 标题数据访问接口
	TitleService titleService;
	// session中存储登陆用户的权限列表,在登陆组件中设置的
	List ulist;
	// 登录时的用户
	WkTUser wkTUser = (WkTUser) Sessions.getCurrent().getAttribute("user");

	public void afterCompose() {
		params = this.getAttributes();
		this.selected = (WkTUsrfav) params.get("userFavTList");
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		ulist = (List) Sessions.getCurrent().getAttribute("ulist");
		binder = new AnnotateDataBinder(this);
		reloadGrid();
		// userfavlistbox监听器
		userfavlistbox.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				WkTUsrfav node = (WkTUsrfav) arg1;
				if (checkTitle(node) || Long.parseLong(node.getKtId()) == 0) { // 判断显示标题权限
					arg0.setValue(node);
					arg0.setHeight("25px");
					Listcell c = new Listcell("");
					Listcell c0 = new Listcell(arg0.getIndex() + 1 + "");
					Listcell c1 = new Listcell();
					Long ktid = Long.parseLong(node.getKtId());
					Listcell c2 = new Listcell();
					Listcell c3 = new Listcell();
					if (ktid == 0) {
						InnerButton inb1 = new InnerButton(node.getKufName());
						inb1.setHref(node.getKufUrl());
						inb1.setTarget("_blank");
						c1.appendChild(inb1);
						c2 = new Listcell("");
						c3 = new Listcell();
						InnerButton inb = new InnerButton(node.getKufUrl());
						inb.setHref(node.getKufUrl());
						inb.setTarget("_blank");
						c3.appendChild(inb);
					} else {
						final WkTTitle tt = (WkTTitle) titleService.get(WkTTitle.class, ktid);
						String name = tt.getKtName();
						c2 = new Listcell();
						c3 = new Listcell("");
						InnerButton inb1 = new InnerButton(node.getKufName());
						InnerButton inb = new InnerButton(name);
						c1.appendChild(inb1);
						c2.appendChild(inb);
						inb1.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								centerTabbox.setAttribute("tid", tt.getKtId());
								westLeft.setTitle(tt.getKtName());
								westLeft.getChildren().clear();
								if (titleService.getChildTitle(tt.getKtId()).size() == 0) {
									// 如果没有下级标题，则代表当前要在左侧打开某个操作菜单树，应为某个菜单zul页面
									Executions.createComponents(tt.getKtContent() + "index.zul", westLeft, null);
								} else {
									if (tt.getKtType().trim().equalsIgnoreCase(WkTTitle.TYPE_ZD)) {
										Executions.createComponents(tt.getKtContent() + "index.zul", westLeft, null);
									} else {
										Executions.createComponents("/admin/left.zul", westLeft, null);
									}
								}
							}
						});
					}
					arg0.appendChild(c);
					arg0.appendChild(c0);
					arg0.appendChild(c1);
					arg0.appendChild(c2);
					arg0.appendChild(c3);
				} else {
					usrfavService.delete(node); // 如果标题权限，已被去掉，这里就将Ulist中不存在的标题，从UserFav表中删除
					arg0.setVisible(false);
				}
			}
		});
		// 删除收藏
		delCol.addEventListener(Events.ON_CLICK, new EventListener() {
			public void onEvent(Event event) throws Exception {
				if (getSelected() == null) {
					Messagebox.show("请您选择要删除的收藏项！", "提示", Messagebox.OK, Messagebox.INFORMATION);
					return;
				} else {
					Set sel = userfavlistbox.getSelectedItems();
					if (sel == null) {
						Messagebox.show("请您选择要删除的收藏项！", "提示", Messagebox.OK, Messagebox.INFORMATION);
					} else {
						Iterator it = sel.iterator();
						if (Messagebox.show("确定要删除此收藏吗？", "提示", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
							while (it.hasNext()) {
								Listitem item = (Listitem) it.next();
								WkTUsrfav fav = (WkTUsrfav) item.getValue();
								usrfavService.delete(fav);
							}
						}
						reloadGrid();
					}
				}
			}
		});
	}

	class pointListener implements EventListener {
		public void onEvent(Event event) throws Exception {
			Listitem c = (Listitem) event.getTarget().getParent();
			WkTUsrfav fav = (WkTUsrfav) c.getValue();
			if (fav.getKtId().equals("0")) {
				Executions.getCurrent().sendRedirect(fav.getKufUrl(), "blank");
			} else {
				Long tid = Long.parseLong(fav.getKtId());
				WkTTitle tt = (WkTTitle) titleService.get(WkTTitle.class, tid);
				String westname = tt.getKtName();
				Sessions.getCurrent().setAttribute("tid", tid);
				centerTabbox.setAttribute("tid", tid);
				westLeft.setTitle(westname); // 设置左侧树的Title。点击不同收藏，随之而变
				westLeft.getChildren().clear();
				if (titleService.getChildTitle(tid).size() == 0) {
					WkTTitle title = (WkTTitle) titleService.get(WkTTitle.class, tid);
					Executions.createComponents(title.getKtContent() + "index.zul", westLeft, null);
				} else {
					Executions.createComponents("/admin/left.zul", westLeft, null);
				}
			}
		}
	}

	/**
	 * <Li>功能：转接到添加收藏页面 void
	 * 
	 * @author bobo
	 * @date 创建日期 Mar 23, 2010
	 * 
	 */
	public void onClick$addFav() {
		CollectionNewWin win = (CollectionNewWin) Executions.createComponents("/admin/personal/data/collection/colNew.zul", null, null);
		win.doHighlighted();
		win.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				reloadGrid();
			}
		});
	}

	/**
	 * <Li>功能：加载数据 void
	 * 
	 * @author bobo
	 * @date 创建日期 Mar 23, 2010
	 * 
	 */
	public void reloadGrid() {
		usrfavList = usrfavService.getUsrfavList(wkTUser.getKuId());
		binder.loadAll();
	}

	/**
	 * <li>功能描述：显示判断WkTUsrfav中的标题和Ulist中的标题，有就添加到列表中显示
	 * 
	 * @param WkTUsrfav
	 *            fav
	 * @return boolean
	 * @author bobo
	 */
	public boolean checkTitle(WkTUsrfav fav) {
		boolean flag = false;
		for (int j = 0; j < ulist.size(); j++) {
			WkTTitle ti = (WkTTitle) ulist.get(j);
			if (ti.getKtId().intValue() == Long.parseLong(fav.getKtId())) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	public UsrfavService getUsrfavService() {
		return usrfavService;
	}

	public void setUsrfavService(UsrfavService usrfavService) {
		this.usrfavService = usrfavService;
	}

	public List getUsrfavList() {
		return usrfavList;
	}

	public void setUsrfavList(List usrfavList) {
		this.usrfavList = usrfavList;
	}

	public Listbox getUserfavlistbox() {
		return userfavlistbox;
	}

	public void setUserfavlistbox(Listbox userfavlistbox) {
		this.userfavlistbox = userfavlistbox;
	}

	public WkTUsrfav getUserFavTList() {
		return userFavTList;
	}

	public void setUserFavTList(WkTUsrfav userFavTList) {
		this.userFavTList = userFavTList;
	}

	public WkTUsrfav getSelected() {
		return selected;
	}

	public void setSelected(WkTUsrfav selected) {
		this.selected = selected;
	}
}
