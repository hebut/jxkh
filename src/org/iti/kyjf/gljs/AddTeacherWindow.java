package org.iti.kyjf.gljs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.iti.xypt.entity.Teacher;
import org.iti.xypt.entity.XyUserrole;
import org.iti.xypt.service.TeacherService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.ui.dept.DepartmentTreeModel;

public class AddTeacherWindow extends Window implements AfterCompose {

	DepartmentService departmentService;
	TeacherService teacherService;
	Integer year=(Integer)Executions.getCurrent().getArg().get("year");
	Tree westTree;
	Panel userPanel;
	Listbox leaderlist;
	Textbox nameSearch,tnoSearch;
	Listheader gradeTwoLabel,gradeThrLabel;
	/**
	 * 列表中需要列出的角色用户的角色
	 */
	WkTRole role=(WkTRole)Executions.getCurrent().getArg().get("role");
	XyUserrole xyuserrole=(XyUserrole)Executions.getCurrent().getArg().get("xyuserrole");
	public void afterCompose() {
		 Components.wireVariables(this, this);
		  Components.addForwards(this, this);
		  initShow();
		
	}
	public void setTitle(String title) {		 
		super.setTitle(title);
		userPanel.setTitle(role.getKrName()+"列表");		
	}
	public void initWindow() {		
		
		 WkTDept rootDept=(WkTDept)	departmentService.get(WkTDept.class, xyuserrole.getKdId())	;
		 gradeTwoLabel.setLabel(rootDept.getGradeName(WkTDept.GRADE_YUAN));
		 gradeThrLabel.setLabel(rootDept.getGradeName(WkTDept.GRADE_XI));
		 List rlist=new ArrayList();
		 rlist.add(rootDept);
		 westTree.setModel(new DepartmentTreeModel(rlist,departmentService,WkTDept.QUANBU));		
	}
	
	public void onClick$submit(){
		Events.postEvent(Events.ON_CHANGE, this, null);
	}
	public void onClick$search(){
		if(westTree.getSelectedItem()==null){
			throw new WrongValueException(westTree,"请选择要搜索的单位");
		} 
		List dlist=new ArrayList();		 
		addDept(westTree.getSelectedItem(),dlist);
		List tlist=teacherService.findNoByYear(year,dlist,role.getKrId(),nameSearch.getValue(),tnoSearch.getValue());		
		leaderlist.setModel(new ListModelList(tlist));
	}
	private void addDept(Treeitem item,List dlist){
		dlist.add(item.getValue());
		if(item.getTreechildren()==null){			
			return;
		}
		List clist=item.getTreechildren().getChildren();
		for(int i=0;i<clist.size();i++){
			Treeitem it=(Treeitem)clist.get(i);
			addDept(it,dlist);
		}
	}
	private void initShow() {
		westTree.setTreeitemRenderer(new TreeitemRenderer(){
			public void render(Treeitem item, Object data) throws Exception {
				WkTDept dept=(WkTDept)data;				 
				item.setValue(data);
				item.setLabel(dept.getKdName());
				item.setOpen(true);
//				WkTDept department = (WkTDept) departmentService.get(WkTDept.class, role.getKdId());
//				if (!department.getKdName().equals(dept.getKdName()))
//					item.setOpen(false);
//				Treerow tr=new Treerow();
				
//				Treecell tc1=new Treecell(dept.getKdName());
//				tr.appendChild(tc1);
//				item.appendChild(tr);
//			   Treecell tc2=new Treecell(xyUserRoleService.countNoCqTeacherAndschid(getRole().getKrId(),dept.getKdId(),rootKdId)+"");
//				tr.appendChild(tc2);
			}			
		});		
		
		westTree.addEventListener(Events.ON_SELECT, new EventListener(){
			public void onEvent(Event arg0) throws Exception {				 
				onClick$search();
			}

					
		});
		leaderlist.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem item, Object data) throws Exception {
				Teacher tea=(Teacher)data;
				item.setValue(data);
				Listcell c1=new Listcell(item.getIndex()+1+"");	
				Listcell c2=new Listcell(tea.getThId());	
				Listcell c3=new Listcell(tea.getUser().getKuName());	
				Listcell c4=new Listcell(tea.getXiDept(role.getKdId()));	
				Listcell c5=new Listcell(tea.getYuDept(role.getKdId()));				
				item.appendChild(c1);item.appendChild(c2);item.appendChild(c3);
				item.appendChild(c4);item.appendChild(c5);
			}
			
		});
	}
	public List getSelectUser(){
		List slist=new ArrayList();
		Set s=leaderlist.getSelectedItems();
		Iterator it=s.iterator();
		while(it.hasNext()){
			Listitem item=(Listitem)it.next();
			slist.add(item.getValue());
		}
		return slist;
	}

}
