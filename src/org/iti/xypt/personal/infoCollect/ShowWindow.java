package org.iti.xypt.personal.infoCollect;
/**
 * 添加新站点
 */
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.iti.xypt.personal.infoCollect.entity.WkTChanel;
import org.iti.xypt.personal.infoCollect.entity.WkTUsersort;
import org.iti.xypt.personal.infoCollect.entity.WkTWebsite;
import org.iti.xypt.personal.infoCollect.newsaudit.ChanelTreeModel;
import org.iti.xypt.personal.infoCollect.newsmanage.UsersortreeModel;
import org.iti.xypt.personal.infoCollect.service.ChanelService;
import org.iti.xypt.personal.infoCollect.service.TaskService;
import org.iti.xypt.personal.infoCollect.service.WebsiteService;
import org.zkoss.zul.Window;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Menu;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.Menuseparator;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;

import com.uniwin.framework.entity.WkTUser;





public class ShowWindow extends Window implements AfterCompose {

	WkTWebsite website;
	WkTChanel chanel;
	TaskService taskService;
	WebsiteService websiteService;
	ChanelService chanelService;
	Radiogroup sta;
	 WkTUser user;
	 List userDeptList,clist,wlist;
	Tree tree,chaneltree;
	UsersortreeModel wtm;
	ChanelTreeModel ctm;
	Menuitem exit,addUrl,order,newteam,sort,newteam1,search;
	Image img;
	Tab con,news;
	Toolbarbutton add,add1,edit,fresh,stop,search1,del,advice;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user=(WkTUser)Sessions.getCurrent().getAttribute("user");
		userDeptList=(List)Sessions.getCurrent().getAttribute("userDeptList");
		add.setTooltiptext("添加分类");
		add1.setTooltiptext("定制任务");
		edit.setTooltiptext("编辑分类");
		fresh.setTooltiptext("刷新");
		stop.setTooltiptext("停止刷新");
		search1.setTooltiptext("搜索");
		del.setTooltiptext("删除分类");
		advice.setTooltiptext("意见反馈");
		loadTree();
	     tree.setTreeitemRenderer(new TaskItemRenderer()
	     {
	    		public void render(Treeitem item, Object data) throws Exception {
	    			final WkTUsersort website=(WkTUsersort)data;
	    			item.setValue(website);
	    			item.setLabel(website.getkusName());
	    			item.setOpen(true);
	    			Menupopup pop=new Menupopup();
					pop.setId(item+"");
					Menuitem menu1=new Menuitem();
					menu1.setLabel("刷新");
					Menuitem menu2=new Menuitem();
					menu2.setLabel("停止刷新");
					Menuitem menu3=new Menuitem();
					menu3.setLabel("搜索");
					Menuseparator sep=new  Menuseparator();
					Menuitem menu4=new Menuitem();
					menu4.setLabel("标记为已读");
					Menuitem menu5=new Menuitem();
					menu5.setLabel("标记为未读");
					Menuseparator sep1=new  Menuseparator();
					Menu menu=new Menu();
					menu.setLabel("新建");
					Menupopup pop1=new Menupopup();
					Menuitem cmenu1=new Menuitem();
					cmenu1.setLabel("新建分类");
					Menuitem cmenu2=new Menuitem();
					cmenu2.setLabel("订阅新频道");
					Menuitem menu6=new Menuitem();
					menu6.setLabel("删除");
					Menuseparator sep2=new  Menuseparator();
					Menuitem menu7=new Menuitem();
					menu7.setLabel("排序");
					Menu menu8=new Menu();
					menu8.setLabel("导出");
					Menupopup pop2=new Menupopup();
					Menuitem dmenu1=new Menuitem();
					dmenu1.setLabel("Excel文件");
					Menuitem dmenu2=new Menuitem();
					dmenu2.setLabel("文本文件");
					Menuseparator sep3=new  Menuseparator();
					Menuitem menu9=new Menuitem();
					menu9.setLabel("属性");				
					pop.appendChild(menu1);
					pop.appendChild(menu2);
					pop.appendChild(menu3);
					pop.appendChild(sep);
					pop.appendChild(menu4);
					pop.appendChild(menu5);
					pop.appendChild(sep1);
					pop1.appendChild(cmenu1);
					pop1.appendChild(cmenu2);
					menu.appendChild(pop1);
					pop.appendChild(menu);
					pop.appendChild(menu6);
					pop.appendChild(sep2);
					pop.appendChild(menu7);					
					pop2.appendChild(dmenu1);
					pop2.appendChild(dmenu2);
					menu8.appendChild(pop2);
					pop.appendChild(menu8);
					pop.appendChild(sep3);
					pop.appendChild(menu9);				
					Treecell treecell=(Treecell) item.getTreerow().getFirstChild();
					treecell.setContext(item+"");
					treecell.appendChild(pop);
					
					cmenu2.addEventListener(Events.ON_CLICK,new EventListener() {
							public void onEvent(Event event) throws Exception {
								if(tree.getSelectedCount()!=0)
								{
									Executions.getCurrent().setAttribute("kind","0");
								}
								else if(chaneltree.getSelectedCount()!=0)
								{
									Executions.getCurrent().setAttribute("kind","1");
								}
								OrderChanelWindow w=(OrderChanelWindow) Executions.createComponents("/admin/content/task/orderchanel.zul", null,null);
								w.doHighlighted();
								w.initWindow();
							}
					    	 
					     });
					
					  menu9.addEventListener(Events.ON_CLICK,new EventListener() {
							public void onEvent(Event event) throws Exception {
							List clist=taskService.getUserSort(website.getKusId());
							if(clist.size()!=0)
							{
								Window w=(Window) Executions.createComponents("/admin/content/task/teamedit.zul", null,null);
								w.doHighlighted();
							}
							else if(clist.size()==0)
							{
								Window w=(Window) Executions.createComponents("/admin/content/task/chaneledit.zul", null,null);
								w.doHighlighted();
							}
								
							}
					     });
					  
					  menu3.addEventListener(Events.ON_CLICK,new EventListener() {
							public void onEvent(Event event) throws Exception {
								Window w=(Window) Executions.createComponents("/admin/content/task/search.zul", null,null);
								w.doHighlighted();
								
							}
					     });
					  
					  cmenu1.addEventListener(Events.ON_CLICK,new EventListener() {
							public void onEvent(Event event) throws Exception {
								Window w=(Window) Executions.createComponents("/admin/content/task/newteam.zul", null,null);
								w.doHighlighted();
							}
					     });
	    		}

	     });
	     tree.addEventListener(Events.ON_SELECT, new EventListener() {

			public void onEvent(Event event) throws Exception {
				news.setVisible(true);
				news.setSelected(true);
			}
	    	 
	     });
	     chaneltree.setTreeitemRenderer(new TaskItemRenderer()
	     {
	    		public void render(Treeitem item, Object data) throws Exception {
	    			WkTChanel chanel=(WkTChanel)data;
	    			item.setValue(chanel);
	    			item.setLabel(chanel.getKcName());
	    			item.setOpen(true);
	    			Menupopup pop=new Menupopup();
					pop.setId(item+"");
					Menuitem menu1=new Menuitem();
					menu1.setLabel("刷新");
					Menuitem menu2=new Menuitem();
					menu2.setLabel("停止刷新");
					Menuitem menu3=new Menuitem();
					menu3.setLabel("搜索");
					Menuseparator sep=new  Menuseparator();
					Menuitem menu4=new Menuitem();
					menu4.setLabel("标记为已读");
					Menuitem menu5=new Menuitem();
					menu5.setLabel("标记为未读");
					Menuseparator sep1=new  Menuseparator();
					Menu menu=new Menu();
					menu.setLabel("新建");
					Menupopup pop1=new Menupopup();
					Menuitem cmenu1=new Menuitem();
					cmenu1.setLabel("新建分类");
					Menuitem cmenu2=new Menuitem();
					cmenu2.setLabel("订阅新频道");
					Menuitem menu6=new Menuitem();
					menu6.setLabel("删除");
					Menuseparator sep2=new  Menuseparator();
					Menuitem menu7=new Menuitem();
					menu7.setLabel("排序");
					Menuitem menu8=new Menuitem();
					menu8.setLabel("导出");
					Menuseparator sep3=new  Menuseparator();
					Menuitem menu9=new Menuitem();
					menu9.setLabel("属性");				
					pop.appendChild(menu1);
					pop.appendChild(menu2);
					pop.appendChild(menu3);
					pop.appendChild(sep);
					pop.appendChild(menu4);
					pop.appendChild(menu5);
					pop.appendChild(sep1);
					pop1.appendChild(cmenu1);
					pop1.appendChild(cmenu2);
					menu.appendChild(pop1);
					pop.appendChild(menu);
					pop.appendChild(menu6);
					pop.appendChild(sep2);
					pop.appendChild(menu7);					
					pop.appendChild(menu8);
					pop.appendChild(sep3);
					pop.appendChild(menu9);				
					Treecell treecell=(Treecell) item.getTreerow().getFirstChild();
					treecell.setContext(item+"");
					treecell.appendChild(pop);
					
					cmenu2.addEventListener(Events.ON_CLICK,new EventListener() {
							public void onEvent(Event event) throws Exception {
								if(tree.getSelectedCount()!=0)
								{
									Executions.getCurrent().setAttribute("kind","0");
								}
								else if(chaneltree.getSelectedCount()!=0)
								{
									Executions.getCurrent().setAttribute("kind","1");
								}
								OrderChanelWindow w=(OrderChanelWindow) Executions.createComponents("/admin/content/task/orderchanel.zul", null,null);
								w.doHighlighted();
								w.initWindow();
							}
					    	 
					     });
					
					  menu9.addEventListener(Events.ON_CLICK,new EventListener() {
							public void onEvent(Event event) throws Exception {
							Treeitem item=tree.getSelectedItem();
							if(item.getValue() instanceof WkTWebsite)
							{
								Window w=(Window) Executions.createComponents("/admin/content/task/teamedit.zul", null,null);
								w.doHighlighted();
							}
							else if(item.getValue() instanceof WkTUsersort)
							{
								Window w=(Window) Executions.createComponents("/admin/content/task/chaneledit.zul", null,null);
								w.doHighlighted();
							}
								
							}
					     });
					  
					  menu3.addEventListener(Events.ON_CLICK,new EventListener() {
							public void onEvent(Event event) throws Exception {
								Window w=(Window) Executions.createComponents("/admin/content/task/search.zul", null,null);
								w.doHighlighted();
								
							}
					     });
					  
					  cmenu1.addEventListener(Events.ON_CLICK,new EventListener() {
							public void onEvent(Event event) throws Exception {
								Window w=(Window) Executions.createComponents("/admin/content/task/newteam.zul", null,null);
								w.doHighlighted();
							}
					     });
	    		}

	     });
	     addUrl.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event event) throws Exception {
					OrderChanelWindow w=(OrderChanelWindow) Executions.createComponents("/admin/content/task/orderchanel.zul", null,null);
					w.doHighlighted();
					w.initWindow();
				}
			});
	   
	     newteam.addEventListener(Events.ON_CLICK,new EventListener() {
			public void onEvent(Event event) throws Exception {
				Window w=(Window) Executions.createComponents("/admin/content/task/newteam.zul", null,null);
				w.doHighlighted();
			}
	     });
	    
	   
	   
	}
	private void loadTree() {
		wlist=websiteService.getChildUsersort(Long.parseLong("0"));
		wtm=new UsersortreeModel(wlist,websiteService);
		tree.setModel(wtm);
		
		clist=chanelService.getChildChanel(Long.parseLong("0"));
		ctm=new ChanelTreeModel(clist,chanelService);
		chaneltree.setModel(ctm);
		
}
	public void onClick$exit()
	{
		this.detach();
	}
}
