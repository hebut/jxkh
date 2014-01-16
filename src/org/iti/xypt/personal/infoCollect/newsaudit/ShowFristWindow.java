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
	
	
	//ά��ҳ���еı�ǩҳ���
	Tabbox centerTabbox;  	
	//ҳ������ർ�������ֶ�Ӧ����
	//South south;	
	//ҳ���ж�Ӧ��һ���˵��Ͷ����˵�	
	Menubar onebar,twobar;	
	NewsService newsService;	
	//ҳ�����������ʾ�������
	Panelchildren leftPanel;	
	//session�д洢��½�û���Ȩ���б�,�ڵ�½��������õ�
	List ulist;	
	Listbox list;
	//�û��������ż��¼�����id�б���������ģ��ʹ��
	List userDeptList=new ArrayList(); 
	DepartmentService departmentService;
	WkTUser user;
	
	//������Զ����б��
	private Listbox TitleListbox,DiyListbox,SkinListbox,siteListbox;
	private ListModelList TitleusrfavList, DiyusrfavList;
	
	//�������ݽӿ�
	private UsrfavService usrfavService;
	TaskService taskService;
	//�û����ݷ��ʽӿ�
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
		//���һ�������б�
		List olist=taskService.getChildType(Long.parseLong("1"));
		
		//��ʼ��һ������
		for(int i=0;i<olist.size();i++){
    		WkTTasktype title=(WkTTasktype)olist.get(i);
    		//�ж��û��Ƿ��д�Ȩ����ʾ�˱���    		
    		  Menuitem it=new Menuitem();
    		  it.setLabel(title.getKtaName());
    		  it.setAttribute("t_id", title);
    		  onebar.appendChild(it);
    		  //��ӵ��һ��������ʾ���������¼�����
    		  it.addEventListener(Events.ON_CLICK,new EventListener(){			
			   public void onEvent(Event arg0) throws Exception {
				  Menuitem it=(Menuitem)arg0.getTarget();	
				  WkTTasktype tid=(WkTTasktype)it.getAttribute("t_id");
				  reloadList(tid);
				  appendMenuItem(tid.getKtaId());
				 
			    }			  
		      });		
    		}
		//Ĭ�ϳ�ʼ����ʾ�����˵�
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
	 * <li>������������ʾһ������tid������������
	 * @param tid ������ID
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
	//������Ϣ�б�
	public void reloadList(WkTTasktype t)
	{ 
		List newsList=newsService.getNewsOfinfo(t.getKtaId());
		infoListModel=new ListModelList();
		infoListModel.addAll(newsList);
		list.setModel(infoListModel);
		list.setItemRenderer(new NewsShowListRenderer(newsService,infoListModel,list));
	}	
	/**
	 * <li>������������ʾ��������ʱ��Ĭ����ʾ��������
	 * @param it ��������Ĳ˵���
	 * void 
	 * @author DaLei
	 */
	public void appendLeftTree(Menuitem it){
		Long tid=(Long)it.getAttribute("t_id");	
		//centerTabbox.setAttribute("tid", tid);
		if(taskService.getChildType(tid).size()==0){
			//���û���¼����⣬�����ǰҪ������ĳ�������˵�����ӦΪĳ���˵�zulҳ��
			WkTTasktype title=(WkTTasktype)taskService.get(WkTTasktype.class, tid);
		}else{
		}
	}
	
	/**
	 * <li>������������ʾ�ж��û��Ƿ��д˱���Ȩ��
	 * @param title�������
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
//				westLeft.setTitle(westname);                                                      //�����������Title�������ͬ�ղأ���֮����
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

