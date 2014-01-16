package org.iti.xypt.personal.infoCollect;


import java.util.List;


import org.iti.xypt.personal.infoCollect.entity.WkTExtractask;
import org.iti.xypt.personal.infoCollect.entity.WkTInfo;
import org.iti.xypt.personal.infoCollect.entity.WkTTasktype;
import org.iti.xypt.personal.infoCollect.service.TaskService;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Menu;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.Menuseparator;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;

import com.uniwin.framework.entity.WkTAuth;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.RoleService;
import com.zkoss.org.messageboxshow.MessageBox;


public class TasktypeTreeComposer extends BaseLeftTreeComposer {


	
	private static final long serialVersionUID = 5818071448713236671L;
	Tree tree;
	TasktypeTreeModel ctm;
	WkTTasktype t,tp;
    TaskService taskService;
    RoleService roleService;
	TaskEditWindow weditWindow ;
	NewSortWindow  addWindow;
	Panel taskpanel;
	 WkTUser user;
	 List userDeptList,clist;
	public static final Short check = 1, uncheck = 0;
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		loadTree();
		user=(WkTUser)Sessions.getCurrent().getAttribute("user");
		userDeptList=(List)Sessions.getCurrent().getAttribute("userDeptList");
	     tree.setTreeitemRenderer(new TaskItemRenderer()
	     {
	    		public void render(final Treeitem item, Object data) throws Exception {
	    			final WkTTasktype task=(WkTTasktype)data;
	    			item.setValue(task);
	    			item.setLabel(task.getKtaName());
	    			item.setOpen(true);
	    			Menupopup pop=new Menupopup();
					pop.setId(item+"");
					Menu menu=new Menu();
					menu.setLabel("新建分类");
					Menupopup pop1=new Menupopup();
					Menuitem cmenu1=new Menuitem();
					cmenu1.setLabel("单条新建");
					Menuitem cmenu2=new Menuitem();
					cmenu2.setLabel("批量新建");
					Menuitem menu2=new Menuitem();
					menu2.setLabel("编辑分类");
					Menuitem menu3=new Menuitem();
					menu3.setLabel("删除分类");
					Menuseparator sep=new  Menuseparator();
					 final Menuitem menu4=new Menuitem();
					menu4.setLabel("排序");
					Menuitem menu5=new Menuitem("添加抽取任务");
					
					
					pop1.appendChild(cmenu1);
					pop1.appendChild(cmenu2);
					menu.appendChild(pop1);
					pop.appendChild(menu);
					pop.appendChild(menu2);
					pop.appendChild(menu3);
					pop.appendChild(sep);
					pop.appendChild(menu4);
					pop.appendChild(menu5);
					Treecell treecell=(Treecell) item.getTreerow().getFirstChild();
					treecell.setContext(item+"");
					treecell.appendChild(pop);
				
					item.getTreerow().addEventListener(Events.ON_CLICK,new EventListener(){
						public void onEvent(Event arg0) throws Exception {
							WkTTasktype tEntity=(WkTTasktype)item.getValue();
							LoadTaskList(tEntity);
						}
					});//监听folder列表，显示task
					
					
					
					//抽取配置
					menu5.addEventListener(Events.ON_CLICK, new EventListener(){
						public void onEvent(Event arg0) throws Exception {
							createNewTask(item);
						}
						
					});
					
					//单条新建分类
					cmenu1.addEventListener(Events.ON_CLICK, new EventListener(){
						public void onEvent(Event event) throws Exception {
							NewSortWindow w=(NewSortWindow)Executions.createComponents("/admin/personal/infoExtract/task/newsort.zul", null,null);
							w.doHighlighted();
							w.initWindow(task);
							w.addEventListener(Events.ON_CHANGE, new EventListener() {
								public void onEvent(Event arg0) throws Exception {
									loadTree();
								}
							});
						}
						
					});
					//批量新建分类
					cmenu2.addEventListener(Events.ON_CLICK, new EventListener(){
						public void onEvent(Event event) throws Exception {
							if(tree.getSelectedCount()==0)
							{
								Messagebox.show("请选择分类");
							}
							else
							{
								if(task.getKtaPid().toString().trim().equals("0"))
								{
									Messagebox.show("请选择分类节点");
								}
								else
								{
								ImportTypeWindow w=(ImportTypeWindow) Executions.createComponents("/admin/personal/infoExtract/task/imports.zul", null,null);
							    w.doHighlighted();
							    w.initWindow(task);
								w.addEventListener(Events.ON_CHANGE, new EventListener(){
									public void onEvent(Event arg0) throws Exception {
										loadTree();	
									} 
								 });
								}
							}
							
						}
						
					});
					
					//排序
					menu4.addEventListener(Events.ON_CLICK, new EventListener(){
						public void onEvent(Event event) throws Exception {
							     TasktypeSortWindow w=(TasktypeSortWindow)Executions.createComponents("/admin/personal/infoExtract/task/typeSort.zul", null, null);
								w.doHighlighted();
								w.initWindow(task);
								w.addEventListener(Events.ON_CHANGE, new EventListener(){
									public void onEvent(Event arg0) throws Exception {
										loadTree();
									}			
								});
						}		
					});
					//编辑分类
					menu2.addEventListener(Events.ON_CLICK, new EventListener(){
						public void onEvent(Event event) throws Exception {
							SortEditWindow s=(SortEditWindow)Executions.createComponents("/admin/personal/infoExtract/task/sortedit.zul", null,null);
							s.doHighlighted();
							s.initWindow(task);
							s.addEventListener(Events.ON_CHANGE, new EventListener() {
								public void onEvent(Event arg0) throws Exception {
									loadTree();
								}

							});
						}		
					});
					//删除分类
					menu3.addEventListener(Events.ON_CLICK, new EventListener(){

						public void onEvent(Event event) throws Exception {
							if(task.getKtaPid().toString().trim().equals("0"))
							{
								Messagebox.show("根文件夹不允许删除");
							}
							else
							{
							List tlist=taskService.getChildType(task.getKtaId());
							if(tlist.size()!=0)
							{
								Messagebox.show("存在子类型不能删除");
							}
							else
							{
							if (Messagebox.show("确实要删除该分类?", "请确认", Messagebox.OK
									| Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK) {
								//删除分类权限
								List alist=taskService.getTypeAuth(task.getKtaId());
								if(alist.size()!=0)
								{
									for(int i=0;i<alist.size();i++)
									{
									WkTAuth a=(WkTAuth) alist.get(i);
									taskService.delete(a);
									}
								}
								//删除分类下的任务
								List list=taskService.getTaskByKtaid(task.getKtaId());
								if(list.size()!=0)
								{
									for(int i=0;i<tlist.size();i++)
									{
								    WkTExtractask e=(WkTExtractask) tlist.get(i);
								    //删除任务下的信息
								    List ilist=taskService.getInfoBykeid(e.getKeId());
								    if(ilist.size()!=0)
								    {
								    	for(int j=0;j<ilist.size();j++)
								    	{
								    		WkTInfo info=(WkTInfo) ilist.get(j);
								    		taskService.delete(info);
								    	}
								    }
									taskService.delete(e);
									}
								}
								taskService.delete(task);
								Messagebox.show("删除成功");
								loadTree();
							}
							}					
						}
						}
					});
					
	    		}

	     });
	     tree.addEventListener(Events.ON_RIGHT_CLICK, new EventListener() {
			public void onEvent(Event event) throws Exception {
			  
				
			}	 
	     });
		 tree.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event event) throws Exception {
				WkTTasktype t=(WkTTasktype)tree.getSelectedItem().getValue();
				if(!t.getKtaPid().toString().trim().equals("0"))
				{
				    openEditWindow();
				}
			}
		});
		clist=taskService.getChildType(Long.parseLong("0"));
		if(clist==null||clist.size()==0){
			Component ca=creatTab("websiteEdit", "任务分类", "/admin/personal/infoExtract/task/newsort.zul", taskpanel);
			if(ca!=null){					
				addWindow=(NewSortWindow)ca;					 
			 }
			addWindow.addEventListener(Events.ON_CHANGE, new EventListener(){
				public void onEvent(Event arg0) throws Exception {
					loadTree();	
				}
				
			});
		}else {
		//默认打开
			TaskWindow wTotal=(TaskWindow)creatTab("taskEdit", "任务管理", "/admin/personal/infoExtract/task/task.zul",taskpanel);
			wTotal.initWindow(Long.parseLong("0"));
			wTotal.addEventListener(Events.ON_CHANGE, new EventListener(){
				public void onEvent(Event arg0) throws Exception {
					loadTree();	
				}
				
			});
		}
		
	}
        //加载分类树
		private void loadTree() {
			clist=taskService.getChildType(Long.parseLong("0"));
			ctm=new TasktypeTreeModel(clist,taskService);
			tree.setModel(ctm);
	}
		//打开任务编辑窗口
		public void openEditWindow() {
			
			 Treeitem it=tree.getSelectedItem();
			 WkTTasktype t=(WkTTasktype)it.getValue();				 
			 Component w=creatTab("taskEdit", "任务管理", "/admin/personal/infoExtract/task/tasklist.zul", taskpanel);
			 if(w!=null){					
				 weditWindow=(TaskEditWindow)w;					 
			 }
			 weditWindow.addEventListener(Events.ON_CHANGE, new EventListener(){
					public void onEvent(Event arg0) throws Exception {
					  loadTree();
					}
								   
			});
		}
		
		
	/* 抽取任务*/
		private void createNewTask(Treeitem item){
			
			if(tree.getSelectedItem()==null){
				MessageBox.showInfo("请选择左侧树文件夹！");
			}else{
			final WkTTasktype t=(WkTTasktype) tree.getSelectedItem().getValue();
			Configure taskConfigure=(Configure)Executions.createComponents("/admin/personal/infoExtract/task/configure.zul",null,null);
			taskConfigure.initWindow(t);
			taskConfigure.doHighlighted();
			taskConfigure.addEventListener(Events.ON_CHANGE, new EventListener(){
				public void onEvent(Event arg0) throws Exception {
					LoadTaskList(t);
				}
	    	});
			}
		}

		/*加载task列表*/
		public  void LoadTaskList(final WkTTasktype t) {
			
			TaskEditWindow taskEdit=(TaskEditWindow)creatTab("taskEdit", "任务编辑", "/admin/personal/infoExtract/task/tasklist.zul",null);
			taskEdit.initWindow(t);
		}	
}
