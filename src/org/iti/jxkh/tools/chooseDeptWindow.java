package org.iti.jxkh.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTDept;

public class chooseDeptWindow extends Window implements AfterCompose{

	/**
	 * @author 马静伟
	 */
	private static final long serialVersionUID = -2019769304570907363L;
	private Listbox listbox1,listbox2;
	private List<WkTDept> deptList = new ArrayList<WkTDept>();
	private List<WkTDept> oldDeptList = new ArrayList<WkTDept>();
	
	//new add var
	List<WkTDept> dlist = new ArrayList<WkTDept>(); 
	
	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		this.addEventListener(Events.ON_CLOSE, new EventListener() {
			
			@Override
			public void onEvent(Event arg0) throws Exception {
				//清空并添加listbox2中选中部门
				deptList.clear();
				deptList.addAll(oldDeptList);
			}
		});
		listbox1.setItemRenderer(new ListitemRenderer(){
			public void render(Listitem arg0, Object arg1) throws Exception {
				WkTDept user = (WkTDept) arg1;
				Listcell c0 = new Listcell();
				Listcell c1 = new Listcell(arg0.getIndex()+1+"");
				Listcell c2 = new Listcell(user.getKdNumber());
				Listcell c3 = new Listcell(user.getKdName());
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.setValue(arg1);
			}
		});
		listbox2.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				WkTDept dept = (WkTDept) arg1;
				Listcell c0 = new Listcell();
				Listcell c1 = new Listcell(arg0.getIndex()+1+"");
				Listcell c2 = new Listcell(dept.getKdNumber());
				Listcell c3 = new Listcell(dept.getKdName());
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.setValue(arg1);
			}
		});
	}
	
	public void initWindow() {
		if (deptList.size() != 0){
			listbox1.setModel(new ListModelList(deptList));
			oldDeptList.addAll(deptList);
		}
		
	}
	
	public void onClick$add(){
		initListbox(this.listbox1,  this.deptList, this.listbox2, this.dlist);
	}
	
	public void onClick$delete() {
		initListbox(this.listbox2,  this.dlist, this.listbox1, this.deptList);
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
	
	public void onClick$submit() {
		//rebuild();
		deptList.clear();
		deptList.addAll(dlist);
		Events.postEvent(Events.ON_CHANGE, this, null);
	}
	
	public List<WkTDept> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<WkTDept> deptList) {
		this.deptList = deptList;
	}
	
	public void onClick$close() {
		deptList.clear();
		deptList.addAll(oldDeptList);
		this.detach();
	}
	
}
