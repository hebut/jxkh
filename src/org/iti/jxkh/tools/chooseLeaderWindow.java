package org.iti.jxkh.tools;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.iti.jxkh.service.AuditConfigService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;


import com.uniwin.framework.entity.WkTUser;


public class chooseLeaderWindow extends Window implements AfterCompose {
	/**
	 * @author WXY
	 */
	private static final long serialVersionUID = -1498644011762408819L;
	
    private Listbox  listbox1,listbox2;//分别是已选人员和为选择人员
    private List<WkTUser>leaderPeoList=new ArrayList<WkTUser>();//listbox1加载
	private List<WkTUser>oldleaderPeoList=new ArrayList<WkTUser>();
	private List<WkTUser> dlist = new ArrayList<WkTUser>(); 
	private AuditConfigService auditConfigService;
	private int number=0;
	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		  this.addEventListener(Events.ON_CLOSE, new EventListener() {
				@SuppressWarnings("unused")
				public void onEvent(Event arg0) throws Exception {
					//清空并添加listbox2中选中部门
					leaderPeoList.clear();
					leaderPeoList.addAll(oldleaderPeoList);
				}
			});
		
		listbox1.setItemRenderer(new LeaderRenderer());
		listbox2.setItemRenderer(new LeaderYxRenderer());
	}

	private void addEventListener(String onClose,
			java.util.EventListener eventListener) {
	}

	public class LeaderRenderer implements ListitemRenderer{
		public void render(Listitem item, Object data) throws Exception {
			if(data==null) return;
			final WkTUser person=(WkTUser)data;
			item.setValue(data);
			Listcell c0=new Listcell();
			Listcell c1 = new Listcell(item.getIndex()+1+"");
			Listcell c2=new Listcell(person.getStaffId());
			Listcell c3=new Listcell(person.getKuName());
			Listcell c4=new Listcell(String.valueOf(person.getDept().getKdName()));//
			item.appendChild(c0);
			item.appendChild(c1);
			item.appendChild(c2);
			item.appendChild(c3);
			item.appendChild(c4);
		}
	}
	public class LeaderYxRenderer implements ListitemRenderer{

		@Override
		public void render(Listitem item, Object data) throws Exception {
			if(data==null) return;
			final WkTUser person=(WkTUser)data;
			item.setValue(data);
			Listcell c0=new Listcell();
			Listcell c1 = new Listcell(item.getIndex()+1+"");
			Listcell c2=new Listcell(person.getStaffId());
			Listcell c3=new Listcell(person.getKuName());
			Listcell c4=new Listcell(String.valueOf(person.getDept().getKdName()));//
			item.appendChild(c0);
			item.appendChild(c1);
			item.appendChild(c2);
			item.appendChild(c3);
			item.appendChild(c4);
		}
	}
	
	public void initWindow(){
		if (leaderPeoList.size() != 0){
			listbox1.setModel(new ListModelList(leaderPeoList));
			oldleaderPeoList.addAll(leaderPeoList);
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
		initListbox(this.listbox1,  this.leaderPeoList, this.listbox2, this.dlist);
		}
	}
	public void onClick$delete() {//删除按钮
		initListbox(this.listbox2,  this.dlist, this.listbox1, this.leaderPeoList);
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
		leaderPeoList.clear();
		leaderPeoList.addAll(dlist);
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	public List<WkTUser> getManagerList() {
		return leaderPeoList;
	}
	public void setManagerList(List<WkTUser> leaderPeoList) {
		this.leaderPeoList = leaderPeoList;
	}
	
	public int getNumber() {//用来接收传过来的可选的人员数目
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public void onClick$close() {
		leaderPeoList.clear();
		leaderPeoList.addAll(oldleaderPeoList);
		this.detach();
	}
	public void onClick$view(){
		String userIdSearch="";
		List<WkTUser>memberList=new ArrayList<WkTUser>();
		for(WkTUser d:memberList){
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
		List<WkTUser> dlist = auditConfigService.findLeaderList();
		listbox1.setModel(new ListModelList(dlist));
	}
}

