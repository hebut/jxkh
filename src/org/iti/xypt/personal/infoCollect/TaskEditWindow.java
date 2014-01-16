package org.iti.xypt.personal.infoCollect;

/**
 * 编辑信息界面
 */

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.iti.xypt.personal.infoCollect.entity.WkTExtractask;
import org.iti.xypt.personal.infoCollect.entity.WkTGuidereg;
import org.iti.xypt.personal.infoCollect.entity.WkTPickreg;
import org.iti.xypt.personal.infoCollect.entity.WkTTasktype;
import org.iti.xypt.personal.infoCollect.service.GuideService;
import org.iti.xypt.personal.infoCollect.service.InfoService;
import org.iti.xypt.personal.infoCollect.service.LinkService;
import org.iti.xypt.personal.infoCollect.service.PickService;
import org.iti.xypt.personal.infoCollect.service.TaskService;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Window;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;





public class TaskEditWindow extends Window implements AfterCompose {

	
	private WkTTasktype tasktype;
	public WkTTasktype getTasktype() {
		return tasktype;
	}
	public void setTasktype(WkTTasktype tasktype) {
		this.tasktype = tasktype;
	}

	TaskService taskService;
	GuideService guideService;
	PickService pickService;
	LinkService linkService;InfoService infoService;
	Listbox taskList;
	ListModelList modelList;
	Tabs tabs;Tabbox box;
	Button addtask,signSet;
	
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this,this);
		addtask.addEventListener(Events.ON_CLICK, new EventListener(){
			public void onEvent(Event event) throws Exception {
				Configure taskConfigure=(Configure)Executions.createComponents("/admin/personal/infoExtract/task/configure.zul",null,null);
				taskConfigure.initWindow(tasktype);
				taskConfigure.doHighlighted();
				taskConfigure.addEventListener(Events.ON_CHANGE, new EventListener(){
					public void onEvent(Event arg0) throws Exception {
						loadTaskList();
					}
		    	});			
				}		
		});
	}

	public void initWindow(WkTTasktype t) {
		this.tasktype=t;
		loadTaskList();
	}
	
	public void loadTaskList(){
		List<WkTExtractask> tList=taskService.findByFolderId(tasktype.getKtaId());
		if(tList.size()!=0){
		modelList=new ListModelList(tList);
		taskList.setModel(modelList);
		taskList.setItemRenderer(new ListitemRenderer(){
			public void render(final Listitem item, Object arg1) throws Exception {
				final WkTExtractask task=(WkTExtractask)arg1;
				item.setValue(task);
				Listcell c1=new Listcell(item.getIndex()+1+"");
				Listcell c2=new Listcell(task.getKeName());
				Listcell c3=new Listcell(task.getKeBegincount());
				Listcell c4=new Listcell(task.getKeRemk());
				final Listcell c5 = new Listcell();
				if(task.getKeStatus()==Long.parseLong("0")){
					c5.setStyle("color:red");
					c5.setLabel(task.END);
				}if(task.getKeStatus()==Long.parseLong("1")){
					c5.setStyle("color:blue");
					c5.setLabel(task.BEGIN);
				}if(task.getKeStatus()==Long.parseLong("2")){
					c5.setStyle("color:green");
					c5.setLabel(task.TIME_TASK);
				}
				Listcell c6=new Listcell(task.getKeTime());
				
				item.appendChild(c1);item.appendChild(c2);
				item.appendChild(c3);item.appendChild(c4);
				item.appendChild(c5);item.appendChild(c6);
				
				item.setContext(item+"");//注意
				Menupopup pop=new Menupopup();
				pop.setId(item+"");
				Menuitem menu=new Menuitem();
				menu.setLabel("开始");
				Menuitem menu2=new Menuitem();
				menu2.setLabel("停止");
				Menuitem menu4=new Menuitem();
				menu4.setLabel("编辑");
				Menuitem menu3=new Menuitem();
				menu3.setLabel("删除");
				pop.appendChild(menu);pop.appendChild(menu2);
				pop.appendChild(menu4);pop.appendChild(menu3);
				c1.appendChild(pop);c2.appendChild(pop);
				
				menu.addEventListener(Events.ON_CLICK, new EventListener(){
					public void onEvent(Event arg0) throws Exception {
						c5.setStyle("color:blue");
						c5.setLabel(task.BEGIN);
						c5.addEventListener(Events.ON_CHANGE, new EventListener(){
							public void onEvent(Event arg0) throws Exception {
								c5.setStyle("color:blue");
								c5.setLabel(task.BEGIN);
							}
							
						});
						
						beginOnRightClick(c5,task,tabs,box);
					}
				});//taskBegin
				
				menu2.addEventListener(Events.ON_CLICK, new EventListener(){

					public void onEvent(Event arg0) throws Exception {
						ServerPush push=new ServerPush(box);
						Executions.getCurrent().getDesktop().enableServerPush(false);
						push.setDone();
						LinkCollection.getUnVisitedUrl().deleteAll();
						LinkCollection.getVisitedUrl().clear();
						c5.setStyle("color:red");
						c5.setLabel(task.END);
					}
					
				});
				
				menu3.addEventListener(Events.ON_CLICK,new EventListener(){
					public void onEvent(Event arg0) throws Exception {
						if(Messagebox.show("确定删除任务信息？","提示信息",Messagebox.YES|Messagebox.NO,Messagebox.QUESTION)==Messagebox.YES){
							taskService.delete(task);
							deleteGuideAndReg(task);
							loadChooseFolder(tasktype);
						}
					}
				});//Delete
				menu4.addEventListener(Events.ON_CLICK, new EventListener(){
					public void onEvent(Event arg0) throws Exception {
						EditTask(task);
					}
					
				});
				//双击任务
				item.addEventListener(Events.ON_DOUBLE_CLICK,new EventListener(){
					public void onEvent(Event arg0) throws Exception {
						EditTask(task);
					}
				});
			}
		});//Task Show
		}else{
			modelList=new ListModelList(tList);
			taskList.setModel(modelList);
		}
	}
	/*编辑任务*/
	public void EditTask(final WkTExtractask extractask){
		Configure configure=(Configure)Executions.createComponents("/admin/personal/infoExtract/task/configure.zul",null,null);
		configure.doInit(extractask);
		configure.doHighlighted();
		configure.addEventListener(Events.ON_CHANGE, new EventListener(){
			public void onEvent(Event arg0) throws Exception {
				WkTTasktype tEntity=taskService.findByFolderID(extractask.getKtaId());
				loadChooseFolder(tEntity);
			}
		});
	}
	
	public void deleteGuideAndReg(WkTExtractask extractTask){
		List<WkTGuidereg> gList=guideService.findGuideListById(extractTask.getKeId());
		List<WkTPickreg> pList=pickService.findpickReg(extractTask.getKeId());
		WkTGuidereg guideReg;
		for(int g=0;g<gList.size();g++){
			guideReg=gList.get(g);
			guideService.delete(guideReg);
		}
		WkTPickreg pReg;
		for(int p=0;p<pList.size();p++){
			pReg=pList.get(p);
			pickService.delete(pReg);
		}
		
	}
	
	public void loadChooseFolder(WkTTasktype t){
		List tList=taskService.findByFolderId(t.getKtaId());
		modelList=new ListModelList(tList);
		taskList.setModel(modelList);
	}
	
	// 右键抽取开始 
    public synchronized void beginOnRightClick(final Listcell cell,WkTExtractask eTask,Tabs tabs,final Tabbox box){
		
	
	Tab findTab=findTab(eTask,tabs);
	if(findTab!=null){
		findTab.setSelected(true);
	}else{

		Long taskId=eTask.getKeId();
		List<WkTGuidereg> guideList=guideService.findGuideListById(taskId);
		final List<WkTPickreg> pickRegList=pickService.findpickReg(taskId);
		Date date=new Date();
		String dateResult=new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
		eTask.setKeTime(dateResult);
		taskService.update(eTask);
//		Callable myCallable = null;
		MyCallable myCallable;
		MyCallableSave myCallable2;
		if(eTask.getKePubtype()==Long.parseLong("0")){
			//动态添加
			Tabpanel tabpanel=(Tabpanel)Executions.createComponents("/admin/personal/infoExtract/task/Tabpanel.zul", null, null);
			Tab t1=new Tab(eTask.getKeName());
			t1.setId(eTask.getKeId()+"");
			t1.setClosable(true);
			tabs.appendChild(t1);
			box.appendChild(tabs);
				
			
			Listbox listbox=(Listbox)Executions.createComponents("/admin/personal/infoExtract/task/listbox.zul", null, null);
			Listhead head=new Listhead();
			head.setSizable(true);
			Listheader header = null;
			WkTPickreg pReg;
			double width;
			for(int h=0;h<pickRegList.size();h++){
				pReg=(WkTPickreg)pickRegList.get(h); 
				header=new Listheader(pReg.getKpRegname());
				width=1/(double)pickRegList.size();
				header.setWidth(100*width+"%");
				header.setAlign("left");
				head.appendChild(header);
			}
			listbox.appendChild(head);
			tabpanel.appendChild(listbox);
			box.getTabpanels().appendChild(tabpanel);
			
			
			myCallable=new MyCallable(cell,box,eTask,guideList,pickRegList,linkService,infoService);
			myCallable.call();
			
			box.getSelectedTab().addEventListener(Events.ON_CLOSE, new EventListener(){
				public void onEvent(Event arg0) throws Exception {
					ServerPush push=new ServerPush(box);
					Executions.getCurrent().getDesktop().enableServerPush(false);
					push.setDone();
					LinkCollection.getUnVisitedUrl().deleteAll();
					LinkCollection.getVisitedUrl().clear();
					Tabpanel pTabpanel=box.getSelectedPanel();
					box.removeChild(pTabpanel);
				}
			});
			
		}else if(eTask.getKePubtype()==Long.parseLong("1")){
			myCallable2=new MyCallableSave(eTask,guideList,pickRegList,linkService,infoService);
			myCallable2.call();
			cell.setStyle("color:red");
			cell.setLabel(eTask.END);
		}
		
	/*try {
	  
		ExecutorService exs=Executors.newCachedThreadPool();
		Future f1=exs.submit(myCallable);
		List rList=(List) f1.get();
		exs.shutdown();
	
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}*/
	}//else
	
 }
	
	private Tab findTab(WkTExtractask task,Tabs tabs) {
		for(int t=0;t<tabs.getChildren().size();t++){
			Tab tab=(Tab)tabs.getChildren().get(t);
			if(tab.getId().equalsIgnoreCase(task.getKeId()+"")){
				return tab;
			}
		}
		return null;
		
	}
	/*html标记设置 */
	public void onClick$signSet(){
		
		HtmlSign sign=(HtmlSign)Executions.createComponents("/admin/personal/infoExtract/task/htmlSign.zul", null, null);
		sign.Init();
		sign.doHighlighted();
	}
	

}
