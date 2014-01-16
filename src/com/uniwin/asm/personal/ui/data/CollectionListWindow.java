package com.uniwin.asm.personal.ui.data;

/**
 * �����ղ�ҳ��
 * @author bobo
 * @date �������� Mar 23, 2010 
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
	// ���ݰ���
	private AnnotateDataBinder binder;
	// �ղؼ���
	private Map params;
	// �ղ��б�
	private List usrfavList;
	// ������Ϣ�б��
	private Listbox userfavlistbox;
	// ��Ϣʵ��
	private WkTUsrfav userFavTList, selected;
	// ��Ӻ�ɾ����ť
	private Button delCol;
	// ά��ҳ���еı�ǩҳ���
	Tabbox centerTabbox;
	// ҳ������ർ�������ֶ�Ӧ����
	West westLeft;
	// �������ݷ��ʽӿ�
	TitleService titleService;
	// session�д洢��½�û���Ȩ���б�,�ڵ�½��������õ�
	List ulist;
	// ��¼ʱ���û�
	WkTUser wkTUser = (WkTUser) Sessions.getCurrent().getAttribute("user");

	public void afterCompose() {
		params = this.getAttributes();
		this.selected = (WkTUsrfav) params.get("userFavTList");
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		ulist = (List) Sessions.getCurrent().getAttribute("ulist");
		binder = new AnnotateDataBinder(this);
		reloadGrid();
		// userfavlistbox������
		userfavlistbox.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				WkTUsrfav node = (WkTUsrfav) arg1;
				if (checkTitle(node) || Long.parseLong(node.getKtId()) == 0) { // �ж���ʾ����Ȩ��
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
									// ���û���¼����⣬�����ǰҪ������ĳ�������˵�����ӦΪĳ���˵�zulҳ��
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
					usrfavService.delete(node); // �������Ȩ�ޣ��ѱ�ȥ��������ͽ�Ulist�в����ڵı��⣬��UserFav����ɾ��
					arg0.setVisible(false);
				}
			}
		});
		// ɾ���ղ�
		delCol.addEventListener(Events.ON_CLICK, new EventListener() {
			public void onEvent(Event event) throws Exception {
				if (getSelected() == null) {
					Messagebox.show("����ѡ��Ҫɾ�����ղ��", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
					return;
				} else {
					Set sel = userfavlistbox.getSelectedItems();
					if (sel == null) {
						Messagebox.show("����ѡ��Ҫɾ�����ղ��", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
					} else {
						Iterator it = sel.iterator();
						if (Messagebox.show("ȷ��Ҫɾ�����ղ���", "��ʾ", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
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
				westLeft.setTitle(westname); // �����������Title�������ͬ�ղأ���֮����
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
	 * <Li>���ܣ�ת�ӵ�����ղ�ҳ�� void
	 * 
	 * @author bobo
	 * @date �������� Mar 23, 2010
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
	 * <Li>���ܣ��������� void
	 * 
	 * @author bobo
	 * @date �������� Mar 23, 2010
	 * 
	 */
	public void reloadGrid() {
		usrfavList = usrfavService.getUsrfavList(wkTUser.getKuId());
		binder.loadAll();
	}

	/**
	 * <li>������������ʾ�ж�WkTUsrfav�еı����Ulist�еı��⣬�о���ӵ��б�����ʾ
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
