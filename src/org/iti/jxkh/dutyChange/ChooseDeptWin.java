package org.iti.jxkh.dutyChange;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.iti.jxkh.entity.JXKH_PerTrans;
import org.iti.jxkh.service.DutyChangeService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.service.UserService;

public class ChooseDeptWin extends Window implements AfterCompose {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Listbox listbox1;
	//,listbox2;
	private Textbox deptName;
	//private Button add,delete;
	private Paging zxPaging1;
	//,zxPaging2;
	
	JXKH_PerTrans jp;
	WkTDept dept;	
	List<WkTDept> deptlt = new ArrayList<WkTDept>();

	DutyChangeService dutyChangeService;
	UserService userService;

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		listbox1.setItemRenderer(new ListitemRenderer(){
			public void render(Listitem arg0, Object arg1) throws Exception {
				WkTDept dept = (WkTDept) arg1;
				Listcell c0 = new Listcell();
				Listcell c1 = new Listcell(arg0.getIndex()+1+"");
				Listcell c2 = new Listcell(dept.getKdName());				
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.setValue(dept);
			}
		});
//		listbox2.setItemRenderer(new ListitemRenderer() {
//			public void render(Listitem arg0, Object arg1) throws Exception {
//				WkTDept dept = (WkTDept) arg1;
//				Listcell c0 = new Listcell();
//				Listcell c1 = new Listcell(arg0.getIndex()+1+"");
//				Listcell c2 = new Listcell(dept.getKdName());				
//				arg0.appendChild(c0);
//				arg0.appendChild(c1);
//				arg0.appendChild(c2);
//				arg0.setValue(dept);
//			}
//		});		
	}
	
	public void initListboxFirst() {
		List dutyChange = new ArrayList();
		dutyChange.clear();
		List<WkTDept> deptlist = dutyChangeService.findAllDept("");
		listbox1.setModel(new ListModelList(deptlist));		
	}
	
	public void initWindow(JXKH_PerTrans jp,short deptType) {
		this.jp = jp;
		List userList = new ArrayList();
		userList.clear();
		/**
		 * 判断是进行选择新部门还是旧部门
		 */
		switch(deptType) {
		case 0:
			initNewDeptWindow();
			break;
		case 1:
			initOldDeptWindow();
			break;
		}		
	}
	
	public void initNewDeptWindow() {
		/**
		 * 判断是进行编辑还是进行添加
		 */
		if(jp != null) {
			//进行编辑
			List dutyChange = new ArrayList();
			dutyChange.clear();
			dutyChange.add(jp.getNewDept());
			listbox1.setModel(new ListModelList(dutyChange));
		}else {
			//进行添加
			initListboxFirst();
		}
	}
	
	public void initOldDeptWindow() {
		/**
		 * 判断是进行编辑还是进行添加
		 */
		if(jp != null) {
			//进行编辑
			List dutyChange = new ArrayList();
			dutyChange.clear();
			dutyChange.add(jp.getOldDept());
			listbox1.setModel(new ListModelList(dutyChange));
		}else {
			//进行添加
			initListboxFirst();
		}
	}
	
	public void onClick$view() {
		List<WkTDept> deptlist = dutyChangeService.findAllDept(deptName.getValue());
		listbox1.setModel(new ListModelList(deptlist));		
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
//		deptlt.clear();
//		Iterator <Listitem> items1 = listbox1.getItems().iterator();
//		while(items1.hasNext()){
//			deptlt.add((WkTDept)items1.next().getValue());
//		}
//		listbox1.setModel(new ListModelList(deptlt));
//	}
//	public void initListbox2(){
//		deptlt.clear();
//		Iterator <Listitem> items2 = listbox2.getItems().iterator();		
//		while(items2.hasNext()){
//			deptlt.add((WkTDept)items2.next().getValue());
//		}
//		listbox2.setModel(new ListModelList(deptlt));
//	}

//	public void rebuild() {
//		Iterator <Listitem> items2 = listbox2.getItems().iterator();
//		while(items2.hasNext()){
//			WkTDept dept = (WkTDept) items2.next().getValue();
//			this.dept = dept;
//		}
//	}
	
	public void rebuild() {
		WkTDept dept = (WkTDept) listbox1.getSelectedItem().getValue();
		this.dept = dept;
		
	}
	
	
	public void onClick$submit() {
		if(listbox1.getItems().size() == 0) {
			try {
				Messagebox.show("请选择部门！", "提示", Messagebox.OK, Messagebox.INFORMATION);
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
	
	public WkTDept getDept() {
		return dept;
	}

	public void setDept(WkTDept dept) {
		this.dept = dept;
	}
}
