package com.uniwin.framework.ui.login;

/**
 * <li>��װ�Ļ���������������Ҫ��װ�˶�ϵͳά��ҳ���б�ǩҳ�Ĳ���,���̳�֮��
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
	// ϵͳά��ҳ���еı�ǩҳ��������Զ���ʼ��
	public Tabbox centerTabbox;
	West westLeft;
	TitleService titleService;
	// ��ʶ��ǰTab�д򿪵�URL��ַ
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
	 * <li>������������һ����ǩҳ������Ѿ������л����˱�ǩҳ��
	 * 
	 * @param tabid
	 *            ��ǩҳ�ı�ʶ
	 * @param tname��ǩҳ����
	 * @param url
	 *            ��ǩҳ������ʾ��URL��ַ
	 * @param tid
	 *            �򿪵�ǰ��ǩ��������������
	 * @return Component
	 * @author DaLei
	 * @2010-3-16
	 */
	@SuppressWarnings("unchecked")
	public Component creatTab(String tabid, final String tname, String url, final Panel treePanel, boolean reopen,final Listitem item,final Tree srcTree,long ktid) {
		Component c = null;
		// ���δ�򿪱�ǩҳ���򴴽����л����˱�ǩҳ
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
			// Tab��˫���رմ�����
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
			// ����Ѿ��򿪱�ǩҳ�����л�����˱�ǩҳ
			Tab newTab = (Tab) centerTabbox.getFellow(tabid);
			// ����Ѿ��򿪱�ǩҳ�����д�ҳ�治һ���������´�ҳ��
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
