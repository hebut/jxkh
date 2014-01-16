package org.iti.jxkh.dutyChange;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.iti.jxkh.entity.JXKH_PerTrans;
import org.iti.jxkh.service.DutyChangeService;
import org.iti.jxkh.service.JxkhAwardService;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

public class ChooseMemberWin extends Window implements AfterCompose {
	private static final long serialVersionUID = -5847256695635389492L;
	private Listbox listbox1;
	//,listbox2;
	private Toolbarbutton view;
	private Textbox userName,staffId;
	//private Button add,delete;
	private Paging zxPaging1;
	//,zxPaging2;
	
	JXKH_PerTrans jp;
	WkTDept dept;
	String username;
	WkTUser user;
	
	DutyChangeService dutyChangeService;
	UserService userService;
	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		listbox1.setItemRenderer(new ListitemRenderer(){
			public void render(Listitem arg0, Object arg1) throws Exception {
				WkTUser user = (WkTUser) arg1;
				Listcell c0 = new Listcell();
				Listcell c1 = new Listcell(arg0.getIndex()+1+"");
				Listcell c2 = new Listcell(user.getStaffId());
				Listcell c3 = new Listcell(user.getKuName());				
				Listcell c4 = new Listcell();
				if(user.getKdId()==0){
					c4 = new Listcell("");
				}else{
				c4 = new Listcell(user.getDept().getKdName());
				}
				
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
				arg0.setValue(user);
			}
		});
//		listbox2.setItemRenderer(new ListitemRenderer() {
//			public void render(Listitem arg0, Object arg1) throws Exception {
//				WkTUser user = (WkTUser) arg1;
//				Listcell c0 = new Listcell();
//				Listcell c1 = new Listcell(arg0.getIndex()+1+"");
//				Listcell c2 = new Listcell(user.getStaffId());
//				Listcell c3 = new Listcell(user.getKuName());
//				Listcell c4 = new Listcell();
//				if(user.getKdId()==0){
//					c4 = new Listcell("");
//				}else{
//				c4 = new Listcell(user.getDept().getKdName());
//				}
//				arg0.appendChild(c0);
//				arg0.appendChild(c1);
//				arg0.appendChild(c2);
//				arg0.appendChild(c3);
//				arg0.appendChild(c4);
//				arg0.setValue(user);
//			}
//		});			
	}
	
	public void initListboxFirst() {
		List dutyChange = new ArrayList();
		dutyChange.clear();
		List<WkTUser> userlist1 = dutyChangeService.findUser("",null,dept.getKdId());
		List<WkTUser> userlist2 = dutyChangeService.findUserByPage(zxPaging1.getActivePage(), zxPaging1.getPageSize());
		if(userlist1.size() != 0) {
			zxPaging1.setTotalSize(userlist1.size());
			if(userlist2.size() != 0) {
				for(int i=0;i<userlist2.size();i++) {
					WkTUser user1 = userlist2.get(i);
					dutyChange.add(user1);
				}
			}
			listbox1.setModel(new ListModelList(dutyChange));
			zxPaging1.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					List<WkTUser> userlistByPage2 = dutyChangeService.findUserByPage(zxPaging1.getActivePage(), zxPaging1.getPageSize());
					List dutyChange2 = new ArrayList();
					dutyChange2.clear();
					if(userlistByPage2.size() != 0) {
						for(int i=0;i<userlistByPage2.size();i++) {
							WkTUser jp2 = userlistByPage2.get(i);
							dutyChange2.add(jp2);
						}
					}
					listbox1.setModel(new ListModelList(dutyChange2));
				}
			});
		}else {
			listbox1.setModel(new ListModelList(dutyChange));
		}
	}
	
	public void initWindow(JXKH_PerTrans jp,WkTDept dept) {
		this.jp = jp;
		this.dept = dept;
		List<WkTUser> userList = new ArrayList();
		userList.clear();
		userList = userService.findByKdId(dept.getKdId());
		listbox1.setModel(new ListModelList(userList));
		/**
		 * 判断是进行编辑还是进行添加
		 */
		
		/*if(jp != null) {
			//进行编辑
			List userlist = userService.FindBykuId(jp.getKuId());
			WkTUser user = (WkTUser)userlist.get(0);
			userList.add(user);
			listbox1.setModel(new ListModelList(userList));
		}else {
			//进行添加
			initListboxFirst();
		}*/
		
	}
	
	public void onClick$view() {
		List<WkTUser> userList = new ArrayList<WkTUser>();
		//模糊查询
		userList = dutyChangeService.findUser(userName.getValue(), staffId.getValue(),dept.getKdId());		
		listbox1.setModel(new ListModelList(userList));		
	}
	
//	public void onClick$add(){
//		Object[] items = listbox1.getSelectedItems().toArray();
//		for (int i = 0; i < items.length; i++) {
//			Listitem item = (Listitem) items[i];			
//			if (listbox2.getItemCount() == 0) {
//				listbox2.appendChild(item);
//			} else {
//				Listitem topItem = (Listitem) listbox2.getItems().get(0);
//				listbox2.insertBefore(item, topItem);
//			}
//			item.detach();
//		}
//		initListbox1();
//		initListbox2();
//		//rebuild();
//	}
//	
//	public void onClick$delete() {
//		Object[] items = listbox2.getSelectedItems().toArray();
//		for (int i = 0; i < items.length; i++) {
//			Listitem item = (Listitem) items[i];
//			if (listbox1.getItemCount() == 0) {
//				listbox1.appendChild(item);
//			} else {
//				Listitem topItem = (Listitem) listbox1.getItems().get(0);
//				listbox1.insertBefore(item, topItem);
//			}
//			item.detach();
//		}
//		initListbox1();
//		initListbox2();
//		//rebuild();
//	}	
	
//	public void initListbox1(){
//		Iterator <Listitem> items1 = listbox1.getItems().iterator();
//		List ul = new ArrayList();
//		ul.clear();
//		while(items1.hasNext()){
//			ul.add((WkTUser) items1.next().getValue());
//		}
//		listbox1.setModel(new ListModelList(ul));
//	}
//	public void initListbox2(){
//		Iterator <Listitem> items2 = listbox2.getItems().iterator();
//		List ul = new ArrayList();
//		ul.clear();
//		while(items2.hasNext()){
//			ul.add((WkTUser) items2.next().getValue());
//		}
//		listbox2.setModel(new ListModelList(ul));
//	}

//	public void rebuild() {
//		Iterator <Listitem> items1 = listbox1.getItems().iterator();
//		while(items1.hasNext()){
//			WkTUser user = (WkTUser) items1.next().getValue();
//			this.user = user;
//		}
//	}
	public void rebuild() {
		WkTUser user = (WkTUser)listbox1.getSelectedItem().getValue();
		this.user = user;
	}
	
	
	public void onClick$submit() {
		if(listbox1.getItems().size() == 0) {
			try {
				Messagebox.show("请选择人员！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		rebuild();
		Events.postEvent(Events.ON_CHANGE, this, null);
	}	
	
	public void onClick$close() {
		this.detach();
	}
	
	public WkTUser getUser() {
		return user;
	}

	public void setUser(WkTUser user) {
		this.user = user;
	}

}
