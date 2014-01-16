package org.iti.xypt.personal.infoCollect;
/**
 * <li>封装的基础左侧树组件，主要封装了对系统维护页面中标签页的操作,供继承之用
 * @2010-3-16
 * @author DaLei
 */
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericAutowireComposer;
import org.zkoss.zkex.zul.West;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;

import com.uniwin.framework.service.TitleService;




public class BaseLeftTreeComposer extends GenericAutowireComposer {

	//系统维护页面中的标签页组件，会自动初始化
	public Tabbox centerTabbox;
	
	West westLeft;
	
	TitleService titleService;
	
	//标识当前Tab中打开的URL地址
	private String openurl="";
    
	public static final String[] CLOSETABS={"titleEdit","roleEdit",
		"dept","userEdit","news","newsaudit","chanelEdit"};
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		 //closeTabs();
	}

	public  void closeTabs(){
		for(int i=0;i<CLOSETABS.length;i++){
			if(centerTabbox.getTabs().hasFellow(CLOSETABS[i])){
				Tab tba=(Tab)centerTabbox.getFellow(CLOSETABS[i]);
				Tab tp=(Tab)tba.getNextSibling();
				 if(tp==null){
					 tp=(Tab)tba.getPreviousSibling();
				 }
				 tp.setSelected(true);
				 tba.onClose();
			}
		}
	}
	public Component creatTab(String tabid,String tname,String url){
		return creatTab(tabid,tname,url,null);
	}
	/**
	 * <li>功能描述：打开一个标签页，如果已经打开则切换到此标签页。
	 * @param tabid 标签页的标识
	 * @param tname标签页名称
	 * @param url  标签页面中显示的URL地址
	 * @param tid  打开当前标签所属二级标题编号
	 * @return  
	 * Component 
	 * @author DaLei
	 * @2010-3-16
	 */
	public Component creatTab(String tabid,final String tname,String url,final Panel treePanel){
		Component c=null;	
		
		//如果未打开标签页，则创建并切换到此标签页
		if(!centerTabbox.getTabs().hasFellow(tabid)){
			  Tab newTab=new Tab();
			  newTab.setId(tabid);						
			  newTab.setLabel(tname); 
			  Tabpanel newtabpanel=new Tabpanel();
			  centerTabbox.getTabs().getChildren().add(newTab);
			  centerTabbox.getTabpanels().getChildren().add(newtabpanel);
			  Hbox h=new Hbox();	
			  h.setAlign("left");
			  h.setPack("center");
			  h.setWidth("100%");
			  newtabpanel.appendChild(h);			  			 
			  newTab.setSelected(true);
			  newTab.setClosable(true);				  
			  //Tab的双击关闭处理函数
			  newTab.addEventListener(Events.ON_DOUBLE_CLICK,new EventListener(){
				public void onEvent(Event event) throws Exception {								
					 Tab tba=(Tab)event.getTarget();
					 Tab tp=(Tab)tba.getNextSibling();
					 if(tp==null){
						 tp=(Tab)tba.getPreviousSibling();
					 }
					 tp.setSelected(true);
					 tba.onClose();
				}
			  });
			  newTab.addEventListener(Events.ON_SELECT, new EventListener(){
				public void onEvent(Event arg0) throws Exception {
					if(treePanel!=null){
						westLeft.getChildren().clear();
						westLeft.appendChild(treePanel);
						westLeft.setTitle(tname);
					}
				}
			  });
			  c=Executions.createComponents(url,h,null);
			  return c;
			}else{
				
				//如果已经打开标签页，则切换搭配此标签页
				Tab newTab=(Tab)centerTabbox.getFellow(tabid);
				
				//如果已经打开标签页但其中打开页面不一样，则重新打开页面
				if(!url.equalsIgnoreCase(openurl)){
					Tabpanel newtabpanel=newTab.getLinkedPanel();
					newtabpanel.getChildren().clear();
					  Hbox h=new Hbox();	
					  h.setAlign("left");
					  h.setPack("center");
					  h.setWidth("100%");
					  newtabpanel.appendChild(h);
					  c= Executions.createComponents(url,h,null);					  
				}
				newTab.setSelected(true);				
			}

//		this.openurl=url;
		return c;
	}	 
}
