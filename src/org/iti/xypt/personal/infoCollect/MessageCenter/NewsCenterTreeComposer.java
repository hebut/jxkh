package org.iti.xypt.personal.infoCollect.MessageCenter;
/**
 * <li>封装的基础左侧树组件，主要封装了对信息发布页面中标签页的操作,供继承之用
 * @2010-3-16
 * @author whm
 */
 
import java.util.List;

import org.iti.xypt.personal.infoCollect.BaseLeftTreeComposer;
import org.iti.xypt.personal.infoCollect.entity.WkTExtractask;
import org.iti.xypt.personal.infoCollect.entity.WkTTasktype;
import org.iti.xypt.personal.infoCollect.service.TaskService;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;

import com.uniwin.framework.entity.WkTUser;



public class NewsCenterTreeComposer  extends BaseLeftTreeComposer {
	
	private static final long serialVersionUID = 1L;
	//栏目树组件,与页面中定义的
	Tree tree;	
	TaskService taskService;
	private WkTExtractask wkTExtractask;
	//树的模型组件
	NewsCenterTreeModel ntm;
	
	InfoShowWindow npWindow;
	Panel newscenterpanel;
	WkTUser user;
	List userDeptList,nlist;
	public void doAfterCompose(Component comp) throws Exception {		
		super.doAfterCompose(comp);
		user=(WkTUser)Sessions.getCurrent().getAttribute("user");
		userDeptList=(List)Sessions.getCurrent().getAttribute("userDeptList");	
		wkTExtractask=(WkTExtractask) Sessions.getCurrent().getAttribute("wkTExtractask");
		inittree();
		//点击左侧树的响应事件
		tree.addEventListener(Events.ON_SELECT, new EventListener(){
			public void onEvent(Event event) throws Exception 
			{
				 Treeitem it=tree.getSelectedItem();
				 if(it.getValue() instanceof WkTExtractask)
				 {
				 WkTExtractask etask=(WkTExtractask) it.getValue();
				 if(it.getValue() instanceof WkTExtractask)
				 {		 
					 Component c=creatTab("newscenter", "新闻中心", "/admin/personal/newscenter/infoshow.zul",newscenterpanel);
					 if(c!=null){					
						 npWindow=(InfoShowWindow)c;	
						 npWindow.initWindow(etask);
					 }
					 
				 }
				 }
			}			
		});
		
	}
	
	public void initSelect(WkTExtractask extractask){
			this.wkTExtractask=extractask;
	}
	
	public void inittree()
	{
		nlist=taskService.getChildType(Long.parseLong("0"));
		ntm=new NewsCenterTreeModel(nlist,taskService);
		tree.setModel(ntm);
		tree.setTreeitemRenderer(new TreeitemRenderer()
		{
			public void render(Treeitem item, Object data) throws Exception {
			if(data instanceof WkTExtractask)
			{
			WkTExtractask et=(WkTExtractask)data;		
				item.setValue(et);
				item.setImage("/images/chanel/globe1.gif");
				item.setLabel(et.getKeName());
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
		List list=taskService.getChildType(Long.parseLong("1"));
		if(list.size()!=0)
		{
			WkTTasktype tt=(WkTTasktype)list.get(0);
			List elist=taskService.getChildTask(tt.getKtaId());
			if(elist.size()!=0)
			{
				WkTExtractask e=(WkTExtractask) elist.get(0);
				Component c=creatTab("newscenter", "新闻中心", "/admin/personal/newscenter/infoshow.zul",newscenterpanel);
				 if(c!=null){					
					 npWindow=(InfoShowWindow)c;
					 if(wkTExtractask!=null){
						npWindow.initWindow(wkTExtractask);
					 }else{
						 npWindow.initWindow(e);
					 }
				 }
				
			}
		}
	}

	
	
	
	
	
	
}
