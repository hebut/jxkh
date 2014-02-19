package org.iti.jxkh.business.award;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.iti.jxkh.service.JxkhAwardService;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;

public class ChooseDeptWin extends Window implements AfterCompose{

	/**
	 * @author ZhangyuGuang
	 */
	private static final long serialVersionUID = -2019769304570907363L;
	private Textbox name,number;
	private Listbox listbox1,listbox2;
	private List<WkTDept> deptList = new ArrayList<WkTDept>();
	private List<WkTDept> oldDeptList = new ArrayList<WkTDept>();
	private JxkhAwardService jxkhAwardService;
	
	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		this.addEventListener(Events.ON_CLOSE, new EventListener() {
			
			@Override
			public void onEvent(Event arg0) throws Exception {
				deptList.clear();
				deptList.addAll(oldDeptList);
			}
		});
		listbox1.setItemRenderer(new ListitemRenderer(){
			public void render(Listitem arg0, Object arg1) throws Exception {
				WkTDept user = (WkTDept) arg1;
				Listcell c0 = new Listcell();
				Listcell c1 = new Listcell(arg0.getIndex()+1+"");
				/*Listcell c2 = new Listcell(user.getKdNumber());*/
				Listcell c3 = new Listcell(user.getKdName());
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				//arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.setValue(arg1);
			}
		});
		listbox2.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				WkTDept dept = (WkTDept) arg1;
				Listcell c0 = new Listcell();
				Listcell c1 = new Listcell(arg0.getIndex()+1+"");
			/*	Listcell c2 = new Listcell(dept.getKdNumber());*/
				Listcell c3 = new Listcell(dept.getKdName());
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				//arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.setValue(arg1);
				arg0.setDraggable("true");
				arg0.setDroppable("true");
				arg0.addEventListener(Events.ON_DROP, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						DropEvent event = (DropEvent) arg0;
						Component self = (Component) arg0.getTarget();
						Listitem dragged = (Listitem) event.getDragged();
						if (self instanceof Listitem) {
							self.getParent().insertBefore(dragged, self.getNextSibling());
							initListbox2();
						} else {
							self.appendChild(dragged);
						}
					}
				});
			}
		});
	}
	
	public void initWindow() {
		if (deptList.size() != 0){
			listbox2.setModel(new ListModelList(deptList));
			oldDeptList.addAll(deptList);
		}
		initFirstListbox();
	}
	
	public void onClick$add(){
//		Object[] items = listbox1.getSelectedItems().toArray();
//		for (int i = 0; i < items.length; i++) {
//			Listitem item = (Listitem) items[i];
//			if (deptList.contains(item.getValue())) {
//				continue;
//			}
//			if (listbox2.getItemCount() == 0) {
//				listbox2.appendChild(item);
//			} else {
//				Listitem topItem = (Listitem) listbox2.getItems().get(0);
//				listbox2.insertBefore(item, topItem);
//			}
//		}
//		initListbox1();
//		initListbox2();
//		rebuild();
		initListbox(this.listbox1, this.dlist, this.listbox2, this.deptList);
	}
	
	public void onClick$delete() {
//		Object[] items = listbox2.getSelectedItems().toArray();
//		for (int i = 0; i < items.length; i++) {
//			Listitem item = (Listitem) items[i];
//			if (listbox1.getItemCount() == 0) {
//				listbox1.appendChild(item);
//			} else {
//				Listitem topItem = (Listitem) listbox1.getItems().get(0);
//				listbox1.insertBefore(item, topItem);
//			}
//		}
//		initListbox1();
//		initListbox2();
//		rebuild();
		initListbox(this.listbox2, this.deptList, this.listbox1, this.dlist);
	}
	private void initListbox(Listbox sourceListbox, List<WkTDept> sourceList,
			Listbox targetListbox, List<WkTDept> targetList) {
		Set<?> items = sourceListbox.getSelectedItems();
		for (Object o : items) {
			Listitem item = (Listitem) o;
			WkTDept d = (WkTDept) item.getValue();
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

	public void initListbox1(){
		Iterator <Listitem> items1 = listbox1.getItems().iterator();
		deptList.clear();
		while(items1.hasNext()){
			deptList.add((WkTDept) items1.next().getValue());
		}
		listbox1.setModel(new ListModelList(deptList));
	}
	public void initListbox2(){
		Iterator <Listitem> items2 = listbox2.getItems().iterator();
		deptList.clear();
		while(items2.hasNext()){
			deptList.add((WkTDept) items2.next().getValue());
		}
		listbox2.setModel(new ListModelList(deptList));
	}

	public void rebuild() {
		deptList.clear();
		Iterator<?> it = listbox2.getItems().iterator();
		while (it.hasNext()) {
			deptList.add((WkTDept) ((Listitem) it.next()).getValue());
		}
//		initFirstListbox();
	}
	List<WkTDept> dlist = null;
	public void initFirstListbox() {
		String deptIdSearch="";
		if(deptList.size()!=0){
			for(int i=0;i<deptList.size();i++){
				WkTDept dept=(WkTDept) deptList.get(i);
				deptIdSearch+="'"+dept.getKdNumber()+"',";	
			}
			deptIdSearch=deptIdSearch.substring(0, deptIdSearch.length()-1);
		}
		 dlist = jxkhAwardService.findWktDeptNotInListBox2(deptIdSearch);
		listbox1.setModel(new ListModelList(dlist));
	}
	
	public void onClick$submit() {
		rebuild();
		Events.postEvent(Events.ON_CHANGE, this, null);
	}
	
	public List<WkTDept> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<WkTDept> deptList) {
		this.deptList = deptList;
	}
	
	public void onClick$view(){
		List<WkTDept> deptList = new ArrayList<WkTDept>();
		Iterator <Listitem> items2 = listbox2.getItems().iterator();
		deptList.clear();
		while(items2.hasNext()){
			deptList.add((WkTDept) items2.next().getValue());
		}
		String deptIdSearch="";
		if(deptList.size()!=0){
			for(int i=0;i<deptList.size();i++){
				WkTDept dept=(WkTDept) deptList.get(i);
				deptIdSearch+=dept.getKdNumber()+",";	
			}
			deptIdSearch=deptIdSearch.substring(0, deptIdSearch.length()-1);
		}
		List<WkTDept> dlist = jxkhAwardService.findWktDeptByCondition(name.getValue(), number.getValue(), deptIdSearch);
		listbox1.setModel(new ListModelList(dlist));
		
	}
	
	
	public void onClick$close() {
		deptList.clear();
		deptList.addAll(oldDeptList);
		this.detach();
	}
	
	
	
	
	
	
}
