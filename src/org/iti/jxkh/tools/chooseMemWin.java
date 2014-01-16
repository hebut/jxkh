package org.iti.jxkh.tools;

import java.util.ArrayList;
//import java.util.Iterator;
import java.util.List;
import java.util.Set;
//import org.iti.jxkh.service.JxkhJfResultService;
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
//import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTUser;

public class chooseMemWin extends Window implements AfterCompose{

	/**
	 * @author 马静伟
	 */
	private static final long serialVersionUID = -5858243242855319109L;
	private Listbox listbox1,listbox2;//分别是已选人员和为选择人员
	private List<WkTUser> managePeoList = new ArrayList<WkTUser>();//listbox1加载
	private List<WkTUser> oldmanagerPeoList = new ArrayList<WkTUser>();//放置选中部门
	private List<WkTUser> dlist = new ArrayList<WkTUser>(); 
   //private Textbox userName;
	//private JxkhJfResultService jxkhJfResultService;
	private int number = 0;
	
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
		
		listbox1.setItemRenderer(new ManagerRenderer());
		listbox2.setItemRenderer(new ManagerRenderer());
	}
	
	public class ManagerRenderer implements ListitemRenderer{
		
		public void render(Listitem item, Object data) throws Exception {
			
			if(data == null) return;
			final WkTUser person = (WkTUser)data;
			item.setValue(data);
			
			Listcell c0 = new Listcell();
			Listcell c1 = new Listcell(item.getIndex()+1+"");
			Listcell c2 = new Listcell(person.getStaffId());
			Listcell c3 = new Listcell(person.getKuName());
			Listcell c4 = new Listcell(String.valueOf(person.getDept().getKdName()));
			
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
	
	public void onClick$add() throws InterruptedException{
		
		Set<?> items = listbox1.getSelectedItems();
		int number2 = listbox2.getItemCount();//获取listbox2的数目
		int number1 = items.size();//获取listbox1的数目
		if(number1 > number){
			Messagebox.show("要添加的人数大于已经设定的人数值！");
			return;
		}else if((number1 + number2) > number){
			Messagebox.show("要添加的人数大于已经设定的人数值！");
			return;
		}else{
			initListbox(this.listbox1,  this.managePeoList, this.listbox2, this.dlist);
		}
	}
	
	public void onClick$delete() {
		
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
	
	public void onClick$submit() {
		
		managePeoList.clear();
		//managePeoList.addAll(dlist);
		setManagerList(dlist);
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	public void onClick$close() {
		
		managePeoList.clear();
		//managePeoList.addAll(oldmanagerPeoList);
		setManagerList(oldmanagerPeoList);
		this.detach();
	}
	
	/*public void onClick$view(){
		
		String userIdSearch = "";
		List<WkTUser> memberList = new ArrayList<WkTUser>();
		
		for(WkTUser d : memberList){
			d.setKuStyle("1");
		}
		
		Iterator<?> items2 = listbox2.getItems().iterator();
		
		while (items2.hasNext()) {
			Listitem item = (Listitem) items2.next();
			memberList.add((WkTUser) item.getValue());
		}
		
		if (memberList.size() != 0) {
			for (int i = 0; i < memberList.size(); i++) {
				WkTUser user = (WkTUser) memberList.get(i);
				userIdSearch += "'"+user.getKuLid() + "',";
			}
			userIdSearch = userIdSearch.substring(0, userIdSearch.length() - 1);
		}
		
		List<WkTUser> dlist = jxkhJfResultService.findWkTUserByCondition(userName.getValue(), userIdSearch);
		listbox1.setModel(new ListModelList(dlist));
	}*/
	
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
}
