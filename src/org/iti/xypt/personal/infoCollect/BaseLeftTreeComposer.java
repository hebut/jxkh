package org.iti.xypt.personal.infoCollect;
/**
 * <li>��װ�Ļ���������������Ҫ��װ�˶�ϵͳά��ҳ���б�ǩҳ�Ĳ���,���̳�֮��
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

	//ϵͳά��ҳ���еı�ǩҳ��������Զ���ʼ��
	public Tabbox centerTabbox;
	
	West westLeft;
	
	TitleService titleService;
	
	//��ʶ��ǰTab�д򿪵�URL��ַ
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
	 * <li>������������һ����ǩҳ������Ѿ������л����˱�ǩҳ��
	 * @param tabid ��ǩҳ�ı�ʶ
	 * @param tname��ǩҳ����
	 * @param url  ��ǩҳ������ʾ��URL��ַ
	 * @param tid  �򿪵�ǰ��ǩ��������������
	 * @return  
	 * Component 
	 * @author DaLei
	 * @2010-3-16
	 */
	public Component creatTab(String tabid,final String tname,String url,final Panel treePanel){
		Component c=null;	
		
		//���δ�򿪱�ǩҳ���򴴽����л����˱�ǩҳ
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
			  //Tab��˫���رմ�����
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
				
				//����Ѿ��򿪱�ǩҳ�����л�����˱�ǩҳ
				Tab newTab=(Tab)centerTabbox.getFellow(tabid);
				
				//����Ѿ��򿪱�ǩҳ�����д�ҳ�治һ���������´�ҳ��
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
