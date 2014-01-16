package org.iti.xypt.personal.infoCollect.newspub;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.iti.xypt.personal.infoCollect.entity.WkTExtractask;
import org.iti.xypt.personal.infoCollect.entity.WkTTasktype;
import org.iti.xypt.personal.infoCollect.service.TaskService;
import org.iti.xypt.personal.infoCollect.service.WebsiteService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.RoleService;


public class NewsTaskSelectWindow extends Window implements AfterCompose {

	
	private static final long serialVersionUID = -2413959345167806957L;
	Tree tree,chaneltree;
	//站点数据访问接口
	TaskService taskService;
	WebsiteService websiteService;
	RoleService roleService;
    //确定选择站点的按钮组件
	Toolbarbutton choosed,chooseall;
	 WkTUser user;
	 List userDeptList,clist,wlist;
	List hasTlist,slist;
	List cList = new ArrayList();
	Radio part;
	Tab common,keys;
	String kind;
	NewsTreeModel wtm;
	ChanelTreeModel ctm;
	
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
	}
	
	public void initWindow(){
	String mul=	(String) Executions.getCurrent().getAttribute("mul");
	if(mul.equals("1"))
	{
		tree.setMultiple(true);
	}
	else if(mul.equals("0"))
	{
		tree.setMultiple(false);
	}
		user=(WkTUser)Sessions.getCurrent().getAttribute("user");
		userDeptList=(List)Sessions.getCurrent().getAttribute("userDeptList");
		 List clist=taskService.getChildType(Long.parseLong("0"));
			wtm=new NewsTreeModel(clist,taskService);
			tree.setModel(wtm);
		tree.setTreeitemRenderer(new TreeitemRenderer(){
			public void render(Treeitem item, Object data) throws Exception {
				if(data instanceof WkTExtractask)
				{
				WkTExtractask et=(WkTExtractask)data;
				item.setValue(et);
				item.setLabel(et.getKeName());
				item.setOpen(false);
				}
				else if(data instanceof WkTTasktype)
				{
					WkTTasktype tt=(WkTTasktype)data;
					item.setValue(tt);
					item.setLabel(tt.getKtaName());
					item.setCheckable(false);
					item.setOpen(true);
				}
				}			
		});
	}
public void onClick$choosed()
{
	Set set=tree.getSelectedItems();
	Iterator it=set.iterator();
	slist=new ArrayList();
	while(it.hasNext()){
		Treeitem titem=(Treeitem)it.next();
		Object o=titem.getValue();
		if(o instanceof WkTExtractask){
			slist.add(o);
		}
	}
    this.detach();
	Events.postEvent(Events.ON_CHANGE, this, null);
}
public List getSlist() {
	return slist;
}

public void setSlist(List slist) {
	this.slist = slist;
}
	//关闭当前窗口
	public void onClick$close()
	{
		   this.detach();	
	}
	
}
