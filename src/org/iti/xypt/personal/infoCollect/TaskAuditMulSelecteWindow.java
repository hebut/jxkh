package org.iti.xypt.personal.infoCollect;
/**
 * 站点选择界面
 */
import java.util.ArrayList;
import java.util.List;

import org.iti.xypt.personal.infoCollect.entity.WkTExtractask;
import org.iti.xypt.personal.infoCollect.entity.WkTTasktype;
import org.iti.xypt.personal.infoCollect.newspub.NewsTreeModel;
import org.iti.xypt.personal.infoCollect.service.TaskService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.RoleService;



public class TaskAuditMulSelecteWindow extends Window implements AfterCompose {

	
	private static final long serialVersionUID = -2413959345167806957L;
	Tree tree;
	//任务数据访问接口
	TaskService taskService;
	RoleService roleService;
	//任务树模型
	NewsTreeModel ctm;
    //确定选择站点的按钮组件
	Toolbarbutton choosed,chooseall;
	 WkTUser user;
	 List userDeptList;
	List hasTlist;
	List cList = new ArrayList();
	
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		choosed.addForward(Events.ON_CLICK, this, Events.ON_CHANGE);
		this.setTitle("选择任务");
	}
	
	public void initWindow(){
		user=(WkTUser)Sessions.getCurrent().getAttribute("user");
		userDeptList=(List)Sessions.getCurrent().getAttribute("userDeptList");
		 List clist=taskService.getChildType(Long.parseLong("0"));
			ctm=new NewsTreeModel(clist,taskService);
			tree.setModel(ctm);
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

	public Tree getTree() {
		return tree;
	}

	public void setTree(Tree tree) {
		this.tree = tree;
	}
	//关闭当前窗口
	public void onClick$close(){
		   this.detach();	
		}
}
