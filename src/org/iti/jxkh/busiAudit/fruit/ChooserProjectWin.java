package org.iti.jxkh.busiAudit.fruit;

import java.util.ArrayList;
import java.util.List;

import org.iti.jxkh.business.award.ChooseFruitWin.FruitRenderer;
import org.iti.jxkh.entity.Jxkh_Fruit;
import org.iti.jxkh.entity.Jxkh_Project;
import org.iti.jxkh.service.JxkhAwardService;
import org.iti.jxkh.service.JxkhProjectService;
import org.zkoss.zk.ui.Components;
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

public class ChooserProjectWin extends Window implements AfterCompose{

	/**
	 * @author ZhangyuGuang 
	 */
	private static final long serialVersionUID = 7199782770088056551L;
	private Textbox name;
	private Listbox sort,projectListbox;
	private JxkhAwardService jxkhAwardService;
	private Jxkh_Project project;
	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		projectListbox.setItemRenderer(new ProjectRenderer());
		sort.setItemRenderer(new sortRenderer());
		initListbox();
	}
	public void initListbox(){
//		List <Jxkh_Project> projectList = jxkhProjectServce.findAllZP();
		List <Jxkh_Project> projectList = jxkhAwardService.findAllPByConditon("",null);
		projectListbox.setModel(new ListModelList(projectList));
		String[] report_Types={"","纵向项目","横向项目","院内自设项目"};
		List<String> sortList= new ArrayList<String>();
		for(int i = 0; i < 4; i++)
		{
			sortList.add(report_Types[i]);
		}
		sort.setModel(new ListModelList(sortList));
		sort.setSelectedIndex(0);
	}
	public class ProjectRenderer implements ListitemRenderer {

		@Override
		public void render(Listitem item, Object data) throws Exception {
			Jxkh_Project project=(Jxkh_Project) data;
			item.setValue(project);
			Listcell c0=new Listcell("");
			Listcell c1=new Listcell(item.getIndex()+1+"");
			Listcell c2=new Listcell(project.getName());
			Listcell c3=new Listcell("");
			if(project.getSort()==0){
			c3=new Listcell("纵向项目");
			}else if(project.getSort()==1){
				c3=new Listcell("横向项目");
			}else if(project.getSort()==2){
				c3=new Listcell("院内自设项目");
			}
			item.appendChild(c0);item.appendChild(c1);item.appendChild(c2);
			item.appendChild(c3);
		}
	}
	public class sortRenderer implements ListitemRenderer {

		@Override
		public void render(Listitem item, Object data) throws Exception {
			String report_Type = (String)data;
			item.setValue(report_Type);
			Listcell c0 = new Listcell();
			if(report_Type==null||report_Type.equals(""))
				c0.setLabel("--请选择--");
			else{
				c0.setLabel(report_Type);				
			}
			item.appendChild(c0);
		}
	}
	public void onClick$query(){
		String nameSearch=name.getValue();
		Short auditStateSearch=null;
		if(sort.getSelectedItem().getValue().equals("")){
			auditStateSearch=null;
		}else if (sort.getSelectedItem().getValue().equals("纵向项目")){
			auditStateSearch=0;
		}else if(sort.getSelectedItem().getValue().equals("横向项目")){
			auditStateSearch=1;
		}else if(sort.getSelectedItem().getValue().equals("院内自设项目")){
			auditStateSearch=2;
		}
		List <Jxkh_Project> projectList = jxkhAwardService.findAllPByConditon(nameSearch,auditStateSearch);
		projectListbox.setModel(new ListModelList(projectList));
	}
	
	public void onClick$choose(){
		if(projectListbox.getSelectedItems()==null||projectListbox.getSelectedItems().size()<=0){
			try {
				Messagebox.show("请选择项目！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		Events.postEvent(Events.ON_CHANGE, this, null);
		Listitem it=projectListbox.getSelectedItem();
		project=(Jxkh_Project)it.getValue();	
	}
	public void onClick$close() {
		this.detach();
	}
	public Jxkh_Project getProject() {
		return project;
	}
	public void setProject(Jxkh_Project project) {
		this.project = project;
	}
	
}
