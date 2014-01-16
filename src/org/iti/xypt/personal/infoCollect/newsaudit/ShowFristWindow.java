package org.iti.xypt.personal.infoCollect.newsaudit;


import java.util.ArrayList;
import java.util.List;


import org.iti.xypt.personal.infoCollect.entity.WkTTasktype;
import org.iti.xypt.personal.infoCollect.service.NewsService;
import org.iti.xypt.personal.infoCollect.service.TaskService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Menubar;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Panelchildren;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Window;
import org.zkoss.zkmax.zul.Tablelayout;


import org.zkoss.zkplus.databind.AnnotateDataBinder;

import com.uniwin.asm.personal.service.UsrfavService;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.service.UserService;



public class ShowFristWindow extends Window implements AfterCompose{

	private static final long serialVersionUID = 5576528748215362191L;
	
	
	//维护页面中的标签页组件
	Tabbox centerTabbox;  	
	//页面中左侧导航条部分对应对象
	//South south;	
	//页面中对应的一级菜单和二级菜单	
	Menubar onebar,twobar;	
	NewsService newsService;	
	//页面左侧用来显示树的面板
	Panelchildren leftPanel;	
	//session中存储登陆用户的权限列表,在登陆组件中设置的
	List ulist;	
	Listbox list;
	//用户所属部门及下级部门id列表，备各个子模块使用
	List userDeptList=new ArrayList(); 
	DepartmentService departmentService;
	WkTUser user;
	
	//标题和自定义列表框
	private Listbox TitleListbox,DiyListbox,SkinListbox,siteListbox;
	private ListModelList TitleusrfavList, DiyusrfavList;
	
	//标题数据接口
	private UsrfavService usrfavService;
	TaskService taskService;
	//用户数据访问接口
	ListModelList infoListModel;
	private UserService userService;
	private AnnotateDataBinder binder;
	private Tabpanel coll;
	private Tab centerTab;
	private Grid gridTitle,gridDiy;
	private Tablelayout tableTitle,tableDiy;


	public void afterCompose() {
		
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user=(WkTUser)Sessions.getCurrent().getAttribute("user");
		
	}
	
	
	public void initWindow()
	{
		//获得一级标题列表
		List olist=taskService.getChildType(Long.parseLong("1"));
		
		//初始化一级标题
		for(int i=0;i<olist.size();i++){
    		WkTTasktype title=(WkTTasktype)olist.get(i);
    		//判断用户是否有此权限显示此标题    		
    		  Menuitem it=new Menuitem();
    		  it.setLabel(title.getKtaName());
    		  it.setAttribute("t_id", title);
    		  onebar.appendChild(it);
    		  //添加点击一级标题显示二级标题事件监听
    		  it.addEventListener(Events.ON_CLICK,new EventListener(){			
			   public void onEvent(Event arg0) throws Exception {
				  Menuitem it=(Menuitem)arg0.getTarget();	
				  WkTTasktype tid=(WkTTasktype)it.getAttribute("t_id");
				  reloadList(tid);
				  appendMenuItem(tid.getKtaId());
				 
			    }			  
		      });		
    		}
		//默认初始化显示二级菜单
		if(olist.size()>0){
			WkTTasktype title=(WkTTasktype)olist.get(0);
			appendMenuItem(title.getKtaId());						
		}   
		WkTTasktype title=(WkTTasktype)olist.get(0);
		List clist=taskService.getChildType(title.getKtaId());
		WkTTasktype ti=(WkTTasktype)clist.get(0);
		reloadList(ti);
	}
	/**
	 * <li>功能描述：显示一级标题tid所属二级标题
	 * @param tid 父标题ID
	 * void 
	 * @author DaLei
	 */
	public void appendMenuItem(Long tid){
		List olist=taskService.getChildType(tid);
		twobar.getChildren().clear();
		boolean showleft=false;
		if(olist.size()!=0)
		{
			twobar.setVisible(true);
		for(int i=0;i<olist.size();i++){
			final WkTTasktype title=(WkTTasktype)olist.get(i);
    		 Menuitem tit=new Menuitem();
    		tit.setLabel(title.getKtaName());
    		tit.setAttribute("t_id", title.getKtaId());
    		twobar.appendChild(tit);
    		tit.addEventListener(Events.ON_CLICK,new EventListener(){			
			public void onEvent(Event arg0) throws Exception {
				Menuitem it=(Menuitem)arg0.getTarget();	
				reloadList(title);
			  }			  
		    });		
    		if(!showleft){
    			appendLeftTree(tit);
    			showleft=true;
    		}
    	  }
		}
		else
		{
			twobar.setVisible(false);
		}
    	}
	//加载信息列表
	public void reloadList(WkTTasktype t)
	{ 
		List newsList=newsService.getNewsOfinfo(t.getKtaId());
		infoListModel=new ListModelList();
		infoListModel.addAll(newsList);
		list.setModel(infoListModel);
		list.setItemRenderer(new NewsShowListRenderer(newsService,infoListModel,list));
	}	
	/**
	 * <li>功能描述：显示二级标题时候默认显示三级标题
	 * @param it 二级标题的菜单项
	 * void 
	 * @author DaLei
	 */
	public void appendLeftTree(Menuitem it){
		Long tid=(Long)it.getAttribute("t_id");	
		//centerTabbox.setAttribute("tid", tid);
		if(taskService.getChildType(tid).size()==0){
			//如果没有下级标题，则代表当前要在左侧打开某个操作菜单树，应为某个菜单zul页面
			WkTTasktype title=(WkTTasktype)taskService.get(WkTTasktype.class, tid);
		}else{
		}
	}
	
	/**
	 * <li>功能描述：显示判断用户是否有此标题权限
	 * @param title标题对象
	 * @return
	 * boolean 
	 * @author DaLei
	 */
	
	
//	class pointListener implements EventListener{
//		public void onEvent(Event event) throws Exception {
//			Listitem c=( Listitem )event.getTarget().getParent();
//			WkTUsrfav fav = (WkTUsrfav)c.getValue(); 
//			if(fav.getKtId().equals("0")){
//				Executions.getCurrent().sendRedirect(fav.getKufUrl(),"blank");		
//			}else{				
//				Long tid=Long.parseLong(fav.getKtId());		
//				WkTTitle tt = (WkTTitle)titleService.get(WkTTitle.class, tid);
//				String westname = tt.getKtName();
//				
//				Sessions.getCurrent().setAttribute("tid", tid);
//				centerTabbox.setAttribute("tid", tid);
//				westLeft.setTitle(westname);                                                      //设置左侧树的Title。点击不同收藏，随之而变
//				westLeft.getChildren().clear();       
//				if(titleService.getChildTitle(tid).size()==0){			
//					WkTTitle title=(WkTTitle)titleService.get(WkTTitle.class, tid);
//					Executions.createComponents(title.getKtContent()+"index.zul",westLeft,null);
//				}else{
//				    Executions.createComponents("/admin/left.zul",westLeft,null);	
//				}
//			}
//		}
//	}
}

