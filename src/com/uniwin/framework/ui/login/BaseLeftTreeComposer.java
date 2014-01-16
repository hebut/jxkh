package com.uniwin.framework.ui.login;

/**
 * <li>封装的基础左侧树组件，主要封装了对系统维护页面中标签页的操作,供继承之用
 * @2010-3-16
 * @author DaLei
 */
import java.util.Iterator;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericAutowireComposer;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zkex.zul.West;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;

import com.uniwin.framework.entity.WkTTitle;
import com.uniwin.framework.service.TitleService;

public class BaseLeftTreeComposer extends GenericAutowireComposer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 系统维护页面中的标签页组件，会自动初始化
	public Tabbox centerTabbox;
	West westLeft;
	TitleService titleService;
	// 标识当前Tab中打开的URL地址
	private String openurl = "";
	public static final String[] CLOSETABS = { "titleEdit", "roleEdit", "dept", "userEdit", "news", "newsaudit", "chanelEdit" };

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		// closeTabs();
	}

	public void closeTabs() {
		for (int i = 0; i < CLOSETABS.length; i++) {
			if (centerTabbox.getTabs().hasFellow(CLOSETABS[i])) {
				Tab tba = (Tab) centerTabbox.getFellow(CLOSETABS[i]);
				Tab tp = (Tab) tba.getNextSibling();
				if (tp == null) {
					tp = (Tab) tba.getPreviousSibling();
				}
				tp.setSelected(true);
				tba.onClose();
			}
		}
	}

	public Component creatTab(String tabid, String tname, String url) {
		return creatTab(tabid, tname, url, null);
	}

	public Component creatTab(String tabid, final String tname, String url, final Panel treePanel) {
		return creatTab(tabid, tname, url, treePanel, false);
	}

	public Component creatTab(String tabid, final String tname, String url, final Panel treePanel, boolean reopen) {
		return creatTab(tabid,tname,url,treePanel,reopen,null,null,0);
	}
	/**
	 * <li>功能描述：打开一个标签页，如果已经打开则切换到此标签页。
	 * 
	 * @param tabid
	 *            标签页的标识
	 * @param tname标签页名称
	 * @param url
	 *            标签页面中显示的URL地址
	 * @param tid
	 *            打开当前标签所属二级标题编号
	 * @return Component
	 * @author DaLei
	 * @2010-3-16
	 */
	@SuppressWarnings("unchecked")
	public Component creatTab(String tabid, final String tname, String url, final Panel treePanel, boolean reopen,final Listitem item,final Tree srcTree,long ktid) {
		Component c = null;
		// 如果未打开标签页，则创建并切换到此标签页
		if (!centerTabbox.getTabs().hasFellow(tabid)) {
			Tab newTab = new Tab();
			newTab.setId(tabid);
			newTab.setLabel(tname);
			newTab.setWidth("160px");
			Tabpanel newtabpanel = new Tabpanel();
			Borderlayout layout = new Borderlayout();
			Center center = new Center();
			center.setAutoscroll(true);
			center.setStyle("border:0px");
			Hbox h = new Hbox();
			h.setPack("center");
			h.setWidth("100%");
			center.appendChild(h);
			layout.appendChild(center);
			newtabpanel.appendChild(layout);
			centerTabbox.getTabs().getChildren().add(newTab);
			centerTabbox.getTabpanels().getChildren().add(newtabpanel);
			newTab.setSelected(true);
			newTab.setClosable(true);
			newTab.setAttribute("westLeft", westLeft.getTitle());
			//System.out.println(treePanel);
			// Tab的双击关闭处理函数
			newTab.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener() {
				public void onEvent(Event event) throws Exception {
					Tab tba = (Tab) event.getTarget();
					Tab tp = (Tab) tba.getNextSibling();
					if (tp == null) {
						tp = (Tab) tba.getPreviousSibling();
					}
					tp.setSelected(true);
					tba.onClose();
				}
			});
			addlistener(newTab, treePanel, item, srcTree,ktid);
			c = Executions.createComponents(url, h, null);
			return c;
		} else {
			// 如果已经打开标签页，则切换搭配此标签页
			Tab newTab = (Tab) centerTabbox.getFellow(tabid);
			// 如果已经打开标签页但其中打开页面不一样，则重新打开页面
			if (!url.equalsIgnoreCase(openurl) || reopen) {
				Tabpanel newtabpanel = newTab.getLinkedPanel();
				newtabpanel.getChildren().clear();
				Borderlayout layout = new Borderlayout();
				Center center = new Center();
				center.setAutoscroll(true);
				center.setStyle("border:0px");
				Hbox h = new Hbox();
				h.setPack("center");
				h.setWidth("100%");
				center.appendChild(h);
				layout.appendChild(center);
				newtabpanel.appendChild(layout);
				c = Executions.createComponents(url, h, null);
			}
			addlistener(newTab, treePanel, item, srcTree,ktid);
			newTab.setSelected(true);
		}
	//	this.openurl = url;
		return c;
	}
	@SuppressWarnings("unchecked")
	private void checkTitle(List<Treeitem> srctlist,long ktid) {
		//List tlist = srctlist;
		for (int i = 0; i < srctlist.size(); i++) {
			Treeitem item = (Treeitem) srctlist.get(i);
			if (item.getTreechildren() == null || item.getTreechildren().getChildren() == null) {
				item.setVisible(false);
			} else {
				item.setOpen(true);
				
				List<Treeitem> itemlist=item.getTreechildren().getChildren();
				for(int j=0;j<itemlist.size();j++){
					Treeitem chitem=(Treeitem)itemlist.get(j);
					if(ktid==((WkTTitle)chitem.getValue()).getKtId()){
						chitem.setOpen(true);
					}
				}
			}
		}
	}
	private void addlistener(Tab newTab,final Panel treePanel,final Listitem item,final Tree srcTree,final long ktid){
		newTab.addEventListener(Events.ON_SELECT, new EventListener() {
			@SuppressWarnings("unchecked")
			public void onEvent(Event arg0) throws Exception {
				Component coms = arg0.getTarget();
				String westtitle = (String) coms.getAttribute("westLeft");
				if (treePanel != null) {
					if(item!=null&&srcTree!=null){
						Component c=treePanel.getFirstChild();
//						System.out.println(c.getChildren().toString());
//						System.out.println(c.getChildren().size());
						Iterator<?> it=c.getChildren().iterator();
						while(it.hasNext()){
							Object object=it.next();
							if(object instanceof Listbox){
								Listbox delist=(Listbox)object;
								delist.setSelectedItem(item);
							}
							if(object instanceof Tree){
								Tree tree=(Tree)object;
								tree.setModel(srcTree.getModel());
								checkTitle(tree.getTreechildren().getChildren(),ktid);
							}
						}
					}
					
					westLeft.getChildren().clear();
					westLeft.appendChild(treePanel);
					westLeft.setTitle(westtitle);
				}
			}
		});
	}
}
