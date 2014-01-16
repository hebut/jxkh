package org.iti.xypt.personal.infoCollect.newsaudit;
/**
 * <li>封装的基础左侧树组件，主要封装了对信息审核页面中标签页的操作,供继承之用
 * @2010-3-17
 * @author whm
 */
import java.util.List;

import org.iti.xypt.personal.infoCollect.BaseLeftTreeComposer;
import org.iti.xypt.personal.infoCollect.entity.WkTExtractask;
import org.iti.xypt.personal.infoCollect.entity.WkTTasktype;
import org.iti.xypt.personal.infoCollect.newspub.NewsItemRenderer;
import org.iti.xypt.personal.infoCollect.service.NewsService;
import org.iti.xypt.personal.infoCollect.service.TaskService;
import org.iti.xypt.personal.infoCollect.service.WebsiteService;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;

import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.RoleService;




public class NewsauditTreeComposer extends BaseLeftTreeComposer {
	
	private static final long serialVersionUID = 1L;
	////栏目树组件
	Tree tree;	
	//栏目信息数据访问接口
	NewsService newsService;
	TaskService taskService;
	//树的模型组件
	NewsauditTreeModel natm;
	RoleService roleService;
	//信息审核初始化窗口
	NewsauditEditWindow naWindow;
	 WebsiteService websiteService;
	Panel newsauditpanel;
	 WkTUser user;
	 List userDeptList,nlist;
	WebsiteListbox list;
	public void doAfterCompose(Component comp) throws Exception {		
		super.doAfterCompose(comp);
		user=(WkTUser)Sessions.getCurrent().getAttribute("user");
		userDeptList=(List)Sessions.getCurrent().getAttribute("userDeptList");
		inittree();
		//点击左侧树的响应事件
		tree.addEventListener(Events.ON_SELECT, new EventListener(){
			public void onEvent(Event event) throws Exception {
				 Treeitem it=tree.getSelectedItem();
				 if(it.getValue() instanceof WkTExtractask)
				 {
				 WkTExtractask etask=(WkTExtractask) it.getValue();
				 if(it.getValue() instanceof WkTExtractask)
				 {		 
					 Component c=creatTab("news", "信息审核", "/admin/personal/infoExtract/newsaudit/audit.zul",newsauditpanel);
					 if(c!=null){					
						 naWindow=(NewsauditEditWindow)c;	
						 naWindow.initWindow(etask);
					 }
				 }
				 }
			}			
		});
		
	}
	public void inittree()
	{
		 nlist=taskService.getChildType(Long.parseLong("0"));
		natm=new NewsauditTreeModel(nlist,taskService);
		tree.setModel(natm);
		tree.setTreeitemRenderer(new NewsItemRenderer()
		{public void render(Treeitem item, Object data) throws Exception {
			if(data instanceof WkTExtractask)
			{
				WkTExtractask et=(WkTExtractask)data;
			item.setValue(et);
			item.setLabel(et.getKeName());
			item.setOpen(true);
			//判断用户权限
			List rlist=roleService.getUserRoleId(user.getKuId());
			List alist=taskService.getTaskOfAuditManage(user,userDeptList,rlist);
			int count=0;
			for(int i=0;i<alist.size();i++)
			{
			WkTExtractask etask=(WkTExtractask) alist.get(i);
			if(etask.getKeId().toString().trim().equals(et.getKeId().toString().trim()))
			{
				count++;
			}
			}
			if(count==0)
			{
				item.setDisabled(true);
			}
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
	}
}
