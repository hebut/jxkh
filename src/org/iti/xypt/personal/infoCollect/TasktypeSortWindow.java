package org.iti.xypt.personal.infoCollect;

/**
 * <li>分类排序
 */

import java.util.List;

import org.iti.xypt.personal.infoCollect.entity.WkTTasktype;
import org.iti.xypt.personal.infoCollect.service.TaskService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTMlog;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.MLogService;


public class TasktypeSortWindow extends Window implements AfterCompose {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	WkTChanel chanel;	 
	WkTTasktype task;
	WkTUser user; 
	Listbox sortList;
	ListModelList sortModel;
	List userDeptList; 
	TaskService taskService;
	MLogService mlogService;	
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);	
		user=(WkTUser)Sessions.getCurrent().getAttribute("user");
		userDeptList=(List)Sessions.getCurrent().getAttribute("userDeptList");
		sortList.setItemRenderer(new TaskListItemRenderer());
		sortModel=new ListModelList();
	}
	public WkTTasktype getTask() {
		return task;
	}
	public void initWindow(WkTTasktype task) {		
		this.task = task;
		reloadList();
	}
	/**保存排序结果*/
	public void onClick$submit(){
		List itemList=sortList.getItems();
		StringBuffer sb=new StringBuffer("编辑分类顺序,ids:");
		if(itemList.size()>0){
		  for(int i=0;i<itemList.size();i++){
			Listitem item=(Listitem)itemList.get(i);
			WkTTasktype c=(WkTTasktype)item.getValue();
			int j=i+1;
			c.setKtaOrdno(Long.parseLong(j+""));
			taskService.update(c);
			sb.append(c.getKtaId()+",");
		  }
		}
		mlogService.saveMLog(WkTMlog.FUNC_CMS, sb.toString(), user);
		Events.postEvent(Events.ON_CHANGE, this, null);
		this.detach();
	}
	
	/**
	 * <li>功能描述：加载要排序的分类列表
	 */
	private void reloadList(){
		sortModel.clear();
		 List plist;
		 plist=taskService.getChildType(task.getKtaPid());
		  sortModel.addAll(plist);	
	    sortList.setModel(sortModel);
	}
	/**
	 * 重置
	 */
	public void onClick$reset(){
		initWindow(getTask());		
	}
	
	public void onClick$close(){
		this.detach();
	}
}
