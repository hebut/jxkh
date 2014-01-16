package org.iti.projectmanage.science.project;

import java.util.List;

import org.iti.gh.entity.GH_PROJECTSOURCE;
import org.iti.gh.service.ProjectSourceService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ProjectSourceWindow extends Window implements AfterCompose {

	private static final long serialVersionUID = 2966451277135844454L;
	private Textbox source;
	private Listbox sourceListbox;
	private Paging sourcePaging;
	String searchContent;
	ProjectSourceService projectSourceService;
	Textbox sourceTextbox = (Textbox)Executions.getCurrent().getArg().get("sourceTextbox");
	private int sourceRowNum = 0;
	
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		sourceListbox.setItemRenderer(new SourceListitmeRenderer());
		sourcePaging.addEventListener("onPaging", new SourcePagingListener());
		sourcePaging.setActivePage(0);
	}

	public void initWindow()
	{
		source.setValue(sourceTextbox.getValue());
		searchContent = source.getValue();
		int pageCount = 0;
		List<Long> count = projectSourceService.getCountByName(searchContent);
		if(null!=count&&count.size()>0)
		{
			pageCount = ((Long)count.get(0)).intValue();
		}
		sourcePaging.setTotalSize(pageCount);
		List<GH_PROJECTSOURCE> psList = projectSourceService.findByNameAndPaging(searchContent,sourcePaging.getActivePage(), sourcePaging.getPageSize());
		ListModel psListModel = new ListModelList(psList);
		sourceListbox.setModel(psListModel);
	}
	
	public void onClick$search()
	{
		sourcePaging.setActivePage(0);
		searchContent = source.getValue();
		int pageCount = 0;
		List<Long> count = projectSourceService.getCountByName(searchContent);
		if(null!=count&&count.size()>0)
		{
			pageCount = ((Long)count.get(0)).intValue();
		}
		sourcePaging.setTotalSize(pageCount);
		List<GH_PROJECTSOURCE> psList = projectSourceService.findByNameAndPaging(searchContent,sourcePaging.getActivePage(), sourcePaging.getPageSize());
		ListModel psListModel = new ListModelList(psList);
		sourceListbox.setModel(psListModel);
	}
	
	public void onClick$add()
	{
		try{
			if(null==source.getValue()||"".equals(source.getValue()))
			{
				Messagebox.show("请输入来源名称！", "提示", Messagebox.OK, Messagebox.INFORMATION);
				return;
			}
			if(projectSourceService.isNameRepeat(source.getValue()))
			{
				Messagebox.show("已存在相同名称的项目来源，请确认！", "提示", Messagebox.OK, Messagebox.INFORMATION);
				return;
			}
			
		}catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		GH_PROJECTSOURCE ps = new GH_PROJECTSOURCE();
		ps.setPsName(source.getValue());
		try{
			projectSourceService.save(ps);
			Messagebox.show("保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
		}catch(Exception e){
			try {
				Messagebox.show("保存失败，请重试！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		sourcePaging.setActivePage(sourcePaging.getPageCount()-1);
		initWindow();
		
	}
	
	public void onClick$select()
	{
		try {
			if(null==sourceListbox.getSelectedItem()||sourceListbox.getSelectedCount()==0)
			{
				Messagebox.show("您尚未选中项目来源！", "提示", Messagebox.OK, Messagebox.INFORMATION);
				return;
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		GH_PROJECTSOURCE ps = (GH_PROJECTSOURCE)sourceListbox.getSelectedItem().getValue();
		sourceTextbox.setValue(ps.getPsName());
		this.detach();
	}
	
	public void onClick$quit()
	{
		this.detach();
	}
	
	public class SourceListitmeRenderer implements ListitemRenderer {

		public void render(Listitem item, Object data) throws Exception {
			if(null==data)
				return;
			GH_PROJECTSOURCE ps = (GH_PROJECTSOURCE)data;
			item.setValue(ps);
			sourceRowNum = sourcePaging.getActivePage() * sourcePaging.getPageSize() + item.getIndex() + 1;
			Listcell c0 = new Listcell();
			Listcell c1 = new Listcell(sourceRowNum+"");
			Listcell c2 = new Listcell(ps.getPsName());
			item.appendChild(c0);
			item.appendChild(c1);
			item.appendChild(c2);
		}
	}
	
	public class SourcePagingListener implements EventListener {

		public void onEvent(Event event) throws Exception {
			List<GH_PROJECTSOURCE> psList = projectSourceService.findByNameAndPaging(searchContent, sourcePaging.getActivePage(), sourcePaging.getPageSize());
			ListModel psListModel = new ListModelList(psList);
			sourceListbox.setModel(psListModel);
		}

	}
}
