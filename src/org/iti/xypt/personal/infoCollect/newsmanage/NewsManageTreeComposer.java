package org.iti.xypt.personal.infoCollect.newsmanage;

import java.util.List;

import org.iti.xypt.personal.infoCollect.BaseLeftTreeComposer;
import org.iti.xypt.personal.infoCollect.entity.WkTExtractask;
import org.iti.xypt.personal.infoCollect.entity.WkTTasktype;
import org.iti.xypt.personal.infoCollect.entity.WkTWebsite;
import org.iti.xypt.personal.infoCollect.service.TaskService;
import org.iti.xypt.personal.infoCollect.service.WebsiteService;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;

import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.RoleService;



public class NewsManageTreeComposer extends BaseLeftTreeComposer {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5818071448713236671L;
	Tree tree;
	NewsManageTreeModel ctm;
	WkTWebsite website;
	WebsiteService websiteService;
	TaskService taskService;
	RoleService roleService;
	ListModelList wmodelList;
	NewsManageEditWindow manageeditWindow ;
	Panel managepanel;
	List clist,alist;
	 WkTUser user;
	 List userDeptList;
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		user=(WkTUser)Sessions.getCurrent().getAttribute("user");
		userDeptList=(List)Sessions.getCurrent().getAttribute("userDeptList");
		inittree();
		}
	 public void inittree()
		{
		    clist=taskService.getChildType(Long.parseLong("0"));
			ctm=new NewsManageTreeModel(clist,taskService);
			tree.setModel(ctm);
			tree.setTreeitemRenderer(new ManageItemRenderer(){
				public void render(Treeitem item, Object data) throws Exception {
					if(data instanceof WkTExtractask)
					{
						WkTExtractask et=(WkTExtractask)data;
					item.setValue(et);
					item.setLabel(et.getKeName());
					item.setOpen(true);
					}
					else if(data instanceof WkTTasktype)
					{
						WkTTasktype tt=(WkTTasktype)data;
						item.setValue(tt);
						item.setLabel(tt.getKtaName());
						item.setOpen(true);
					}
					}
				});
			tree.addEventListener(Events.ON_SELECT, new EventListener() {
				public void onEvent(Event event) throws Exception {
					openEditWindow();
				}
			});
			
		}
//	public void updateTree()
//	{
//		Listitem item=list.getSelectedItem();
//		WkTWebsite website=(WkTWebsite)item.getValue();
//		clist.clear();
//		inittree(website.getKwId());
//	}
	
		public void openEditWindow() {
			 Treeitem it=tree.getSelectedItem();
			 if(it.getValue() instanceof WkTExtractask)
			 {
			 WkTExtractask etask=(WkTExtractask) it.getValue();
			 if(it.getValue() instanceof WkTExtractask)
			 {		 
			 Component c=creatTab("manage", "信息管理", "/admin/personal/infoExtract/newsmanage/manage.zul",managepanel);
			 if(c!=null){					
				 manageeditWindow=(NewsManageEditWindow)c;	
				 manageeditWindow.initManage(etask);
			 }
			 }
			 }
		}
}
