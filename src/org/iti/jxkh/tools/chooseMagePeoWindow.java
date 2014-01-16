package org.iti.jxkh.tools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.iti.jxkh.service.AuditConfigService;
import org.iti.jxkh.service.AuditResultService;
import org.iti.jxkh.service.JxkhAwardService;
import org.iti.jxkh.service.JxkhJfResultService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;

public class chooseMagePeoWindow extends Window implements AfterCompose{

	/**
	 * @author WXY
	 */
	private static final long serialVersionUID = -1269944072463624806L;
	private Listbox listbox1,listbox2,deptList;//分别是已选人员和为选择人员
	private List<WkTUser>managePeoList=new ArrayList<WkTUser>();//listbox1加载
	private List<WkTUser>oldmanagerPeoList=new ArrayList<WkTUser>();
	private List<WkTUser> dlist = new ArrayList<WkTUser>(); 
    private Textbox userName,staffId;
	private AuditResultService auditResultService;//用来查找管理部门
	private AuditConfigService auditConfigService;//查找人员
	private int number=0;
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
        this.addEventListener(Events.ON_CLOSE, new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				//清空并添加listbox2中选中部门
				managePeoList.clear();
				managePeoList.addAll(oldmanagerPeoList);
			}
		});
        deptList.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				WkTDept dept = (WkTDept) arg1;
				arg0.setLabel(dept.getKdName());
				arg0.setValue(dept);
			}
		});
		listbox1.setItemRenderer(new ManagerRenderer());
		listbox2.setItemRenderer(new ManagerYxRenderer());
		deptList.setModel(new ListModelList(auditResultService.findManageDept()));
		deptList.setSelectedIndex(0);
	}
	public class ManagerRenderer implements ListitemRenderer{
		public void render(Listitem item, Object data) throws Exception {
			if(data==null) return;
			final WkTUser person=(WkTUser)data;
			item.setValue(data);
			Listcell c0=new Listcell();
			Listcell c1 = new Listcell(item.getIndex()+1+"");
			Listcell c2=new Listcell(person.getStaffId());
			Listcell c3=new Listcell(person.getKuName());
			Listcell c4=new Listcell(String.valueOf(person.getDept().getKdName()));
			item.appendChild(c0);
			item.appendChild(c1);
			item.appendChild(c2);
			item.appendChild(c3);
			item.appendChild(c4);
		}
	}
	public class ManagerYxRenderer implements ListitemRenderer{

		@Override
		public void render(Listitem item, Object data) throws Exception {
			if(data==null) return;
			final WkTUser person=(WkTUser)data;
			item.setValue(data);
			Listcell c0=new Listcell();
			Listcell c1 = new Listcell(item.getIndex()+1+"");
			Listcell c2=new Listcell(person.getStaffId());
			Listcell c3=new Listcell(person.getKuName());
			Listcell c4=new Listcell(String.valueOf(person.getDept().getKdName()));
			item.appendChild(c0);
			item.appendChild(c1);
			item.appendChild(c2);
			item.appendChild(c3);
			item.appendChild(c4);
		}
	}
	public void initWindow(){
		if (managePeoList.size() != 0){
			listbox1.setModel(new ListModelList(managePeoList));
			oldmanagerPeoList.addAll(managePeoList);
		}
	}
	public void onClick$add() throws InterruptedException{//添加按钮
		Set<?> items = listbox1.getSelectedItems();
		int number2=listbox2.getItemCount();//获取listbox2的数目
		int number1=items.size();//获取listbox1的数目
		if(number1>number){
			Messagebox.show("要添加的人数大于已经设定的人数值！");
			return;
		}else if((number1+number2)>number){
			Messagebox.show("要添加的人数大于已经设定的人数值！");
			return;
		}
		else{
		initListbox(this.listbox1,  this.managePeoList, this.listbox2, this.dlist);
		}
	}
	public void onClick$delete() {//删除按钮
		initListbox(this.listbox2,  this.dlist, this.listbox1, this.managePeoList);
	}
	private void initListbox(Listbox sourceListbox, List<WkTUser> sourceList,
			Listbox targetListbox, List<WkTUser> targetList) {//初始化
		Set<?> items = sourceListbox.getSelectedItems();
		for (Object o : items) {
			Listitem item = (Listitem) o;
			WkTUser d = (WkTUser) item.getValue();
			if (!targetList.contains(d))
				targetList.add(d);
			if (sourceList.contains(d))
				sourceList.remove(d);
		}
		sourceListbox.setModel(new ListModelList(sourceList));
		targetListbox.setModel(new ListModelList(targetList));
		targetListbox.onInitRender();
		sourceListbox.onInitRender();
	}
	public void onClick$submit() {//保存
		managePeoList.clear();
		managePeoList.addAll(dlist);
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	public List<WkTUser> getManagerList() {
		return managePeoList;
	}
	public void setManagerList(List<WkTUser> managePeoList) {
		this.managePeoList = managePeoList;
	}
	
	public int getNumber() {//用来接收传过来的可选的人员数目
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public void onClick$close() {
		managePeoList.clear();
		managePeoList.addAll(oldmanagerPeoList);
		this.detach();
	}
	public void onClick$view() {
	WkTDept dept = (WkTDept) deptList.getSelectedItem().getValue();
	List<WkTUser>dlist=auditConfigService.findDeptMemberListByName(dept.getKdId(),userName.getValue().trim(),staffId.getValue().trim());
	listbox1.setModel(new ListModelList(dlist));
	}
}
