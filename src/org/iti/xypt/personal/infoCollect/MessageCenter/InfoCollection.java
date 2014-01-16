package org.iti.xypt.personal.infoCollect.MessageCenter;

import java.util.List;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.xypt.personal.infoCollect.AnalyBegUrl;
import org.iti.xypt.personal.infoCollect.entity.WkTExtractask;
import org.iti.xypt.personal.infoCollect.entity.WkTTasktype;
import org.iti.xypt.personal.infoCollect.service.InfoService;
import org.iti.xypt.personal.infoCollect.service.OriNewsService;
import org.iti.xypt.personal.infoCollect.service.TaskService;
import org.iti.xypt.personal.infoCollect.service.TreeService;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zkex.zul.West;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Group;
import org.zkoss.zul.Groupfoot;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;

import com.uniwin.framework.entity.WkTUser;

public class InfoCollection extends Panel implements AfterCompose{

	
	Listbox infolist;
	ListModelList modelList;
	TreeService treeService;
	TaskService  taskService;
	WkTUser wkTUser;
	InfoService infoService;
	OriNewsService orinewsService;

	private String openurl="";
	Panel newscenterpanel;
	InfoShowWindow npWindow;
	
	Grid infoGrid;
	Rows chanelRow;
	
	public void afterCompose() {
		// TODO Auto-generated method stub
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		wkTUser = (WkTUser) Sessions.getCurrent().getAttribute("user");
		InitWin();
	}

	
	private void InitWin() {
		// TODO Auto-generated method stub
		List chanel=taskService.findTaskAndExtract();
		if(chanel!=null && chanel.size()>0){
			
		modelList=new ListModelList(chanel);
		
		Row row;
		for(int i=0;i<chanel.size();i++){
			row=new Row();
			if(chanel.get(i) instanceof WkTTasktype){
				WkTTasktype tasktype=(WkTTasktype)chanel.get(i);
				Group group=new Group(tasktype.getKtaName());
				group.setAlign("left");
				infoGrid.getRows().appendChild(group);
			}else if(chanel.get(i) instanceof WkTExtractask){
				final WkTExtractask extractask=(WkTExtractask)chanel.get(i);
				row.setValue(extractask);
				List allCount=orinewsService.getNewsOfOrinfo(extractask.getKeId());
				List readList=orinewsService.findReadInfo(extractask.getKeId(),wkTUser.getKuId());
				int unRead=0;int total=0;
				if(allCount!=null){
					if(readList!=null){
						unRead=allCount.size()-readList.size();
					}
					total=allCount.size();
				}
				
				Label label0=new Label(extractask.getKeName());
				label0.setStyle("color:blue;cursor:hand");
				label0.setParent(row);
				label0.addEventListener(Events.ON_CLICK, new EventListener(){

					public void onEvent(Event arg0) throws Exception {
						// TODO Auto-generated method stub
						West westLeft=(West)Sessions.getCurrent().getAttribute("westLeft");
						westLeft.getChildren().clear();
						Sessions.getCurrent().setAttribute("wkTExtractask",extractask);
						Executions.createComponents("/admin/personal/newscenter/index.zul", westLeft, null);
					}
					
				});
				
				String url = extractask.getKeBeginurl();
				InnerButton inb = new InnerButton();
				inb.addEventListener(Events.ON_CLICK, new EventListener(){
					
					public void onEvent(Event arg0) throws Exception {
						// TODO Auto-generated method stub
						String url = extractask.getKeBeginurl();
						AnalyBegUrl analyBegUrl=new AnalyBegUrl();
						List rList;
						if(url.indexOf("{")!=-1){
							rList=analyBegUrl.analyUrls(url);
							url=(String) rList.get(0);
						}
						url=url.substring(0,url.indexOf("$"));
						Executions.getCurrent().sendRedirect(url,"_blank");
					}
					
				});
				
				if(url.length()>20){
					url=extractask.getKeBeginurl().substring(0,20);
				}
				
				inb.setLabel(url);
				inb.setParent(row);
				if(unRead<0){
					unRead=0;
				}
				Label label=new Label(unRead+"");
				label.setStyle("color:red;");//text-decoration: underline;color:red;cursor:hand
				label.setParent(row);

				bindData(row, total+"");
				Long type=extractask.getKePubtype();
				Long time=extractask.getKeDefinite();
				String t;
				if(type==1 && time!=null){
					t="定时采集";
				}else{
					t="手动采集";
				}
				bindData(row, t);
				infoGrid.getRows().appendChild(row);
			}
			
		}
		
		Groupfoot groupfoot=new Groupfoot();
		groupfoot.setSpans("5");
		Label label3=new Label("新闻采集结束");
		groupfoot.appendChild(label3);
		infoGrid.getRows().appendChild(groupfoot);
	}
		
  }

	private void bindData(Row row,String d ){  
		new Label(d).setParent(row);  
	}  
	

	private void Init() {
		
		List chanel=treeService.findAll();
		modelList=new ListModelList(chanel);
		infolist.setModel(modelList);
		infolist.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem item, Object obj) throws Exception {
				// TODO Auto-generated method stub
				WkTTasktype tasktype=(WkTTasktype)obj;
				item.setValue(tasktype);
				
				Listcell c1=new Listcell();
				c1.setLabel(tasktype.getKtaName());
				
				List eList=taskService.findByFolderId(tasktype.getKtaId());
				
				Listcell c3=new Listcell();
				c3.setStyle("color:blue");
				
				Listbox listbox=new Listbox();
				Listhead listhead=new Listhead();
				Listheader listheader=new Listheader();
				Listheader listheader2=new Listheader();
				listheader.setWidth("75%");
				listheader2.setWidth("25%");
				listhead.appendChild(listheader);listhead.appendChild(listheader2);
				
				listbox.appendChild(listhead);
				listbox.setFixedLayout(true);
				
				ListModelList model=new ListModelList(eList);
				listbox.setItemRenderer(new ListitemRenderer(){

					public void render(Listitem item, Object arg1)
							throws Exception {
						final WkTExtractask extractask=(WkTExtractask)arg1;
						item.setValue(extractask);
						Listcell c1=new Listcell();
						Listcell c2=new Listcell();
						
						InnerButton inb = new InnerButton();
						inb.setLabel(extractask.getKeName());
						inb.setTooltiptext(extractask.getKeName());
						c1.appendChild(inb);
						List list=infoService.findBySite(extractask.getKeId());
						if(list!=null && list.size()>0){
							c2.setLabel(list.size()+"");
						}else{
							c2.setLabel("0");
						}
						
						item.appendChild(c1);item.appendChild(c2);
						
						inb.addEventListener(Events.ON_CLICK, new EventListener(){

							public void onEvent(Event arg0) throws Exception {
								
								InfoShowWindow showWindow=(InfoShowWindow)Executions.createComponents("/admin/personal/newscenter/infoshow.zul", null, null);
								showWindow.initWindow(extractask);
								showWindow.doHighlighted();
							}
							
						});
						
					}
					
				});
				
				listbox.setModel(model);
				c3.appendChild(listbox);
				
				item.appendChild(c1);
				item.appendChild(c3);
			}
			
		});
		
	}

	public void onClick$allinfo() {
		Events.postEvent(Events.ON_CHANGE, this, null);
	}
	
	
	
	public Component creatTab(String tabid,final String tname,String url,final Panel treePanel){
		Component c=null;	
		
		Tabbox center=(Tabbox) Sessions.getCurrent().getAttribute("centerTabbox");
		final West westLeft=(West)Sessions.getCurrent().getAttribute("westLeft");
		//���δ�򿪱�ǩҳ���򴴽����л����˱�ǩҳ
		if(!center.getTabs().hasFellow(tabid)){
			  Tab newTab=new Tab();
			  newTab.setId(tabid);						
			  newTab.setLabel(tname); 
			  Tabpanel newtabpanel=new Tabpanel();
			  center.getTabs().getChildren().add(newTab);
			  center.getTabpanels().getChildren().add(newtabpanel);
			  Hbox h=new Hbox();	
			  h.setAlign("left");
			  h.setPack("center");
			  h.setWidth("100%");
			  newtabpanel.appendChild(h);			  			 
			  newTab.setSelected(true);
			  newTab.setClosable(true);				  
			  //Tab��˫��رմ��?��
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
				Tab newTab=(Tab)center.getFellow(tabid);
				
				//����Ѿ��򿪱�ǩҳ�����д�ҳ�治һ�������´�ҳ��
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

		return c;
	}	 
	
}
