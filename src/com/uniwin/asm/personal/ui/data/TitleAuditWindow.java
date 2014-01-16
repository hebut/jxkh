package com.uniwin.asm.personal.ui.data;

import java.util.GregorianCalendar;
import java.util.List;

import org.iti.gh.entity.GhJsps;
import org.iti.gh.service.JspsService;
import org.iti.xypt.entity.Teacher;
import org.iti.xypt.service.TeacherService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

public class TitleAuditWindow extends Window implements AfterCompose {

	private static final long serialVersionUID = -6550826077347068723L;
	private Listbox teacherListbox;
	private Paging teacherPaging;
	private TitleAuditWindow titleAuditWin;
	
	private UserService userService;
	private TeacherService teacherService;
	private JspsService jspsService;
	
	private int curYear = 0;
	
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		teacherListbox.setItemRenderer(new TeacherRenderer());
		teacherPaging.addEventListener("onPaging", new PageListener());
		GregorianCalendar calendar=new GregorianCalendar(); 
		curYear = calendar.get(calendar.YEAR);//当前年份
		initWindow();
	}
	
	public void initWindow(){
		int teacherTotal = 0;
		List allList = jspsService.findByYear(curYear);
		if(null!=allList&&allList.size()>0)
			teacherTotal = allList.size();
		teacherPaging.setTotalSize(teacherTotal);
		List teacherList = jspsService.findByYearAndPage(curYear, 0, teacherPaging.getPageSize());//当年提出职称评审申请的教师列表
		ListModel listModel = new ListModelList(teacherList);
		teacherListbox.setModel(listModel);
	}

	public class TeacherRenderer implements ListitemRenderer {

		public void render(Listitem item, Object data) throws Exception {
			if(data==null)
				return;
			final GhJsps jsps = (GhJsps)data;
			item.setValue(jsps);
			Listcell c0 = new Listcell(item.getIndex()+1+"");
			WkTUser user = userService.getUserBykuid(jsps.getKuId());
			Teacher teacher = teacherService.findBtKuid(jsps.getKuId());
			Listcell c1 = new Listcell(user.getKuName());
			Listcell c2 = new Listcell(teacher.getYuDept(teacher.getKdId()));
			Listcell c3 = new Listcell(jsps.getJspsSubject());
			Listcell c4 = new Listcell(jsps.getJspsPosition());
			Listcell c5 = new Listcell(jsps.getJspsType());
			Listcell c6 = new Listcell(jsps.getJspsStatus());
			Listcell c7 = new Listcell();
			Hbox hbox = new Hbox();
			Toolbarbutton tb = new Toolbarbutton();
			tb.addEventListener(Events.ON_CLICK, new EventListener(){

				public void onEvent(Event arg0) throws Exception {
					TitleAuditDetailsWindow w = (TitleAuditDetailsWindow)Executions.createComponents
						("/admin/personal/data/titleAudit/audit.zul",null, null);
					w.setTitleAuditWin(titleAuditWin);
					w.setJsps(jsps);
					w.initWindow();
					w.doModal();
				}
				
			});
			tb.setLabel("审核");
			tb.setStyle("color:blue");
			hbox.appendChild(tb);
			c7.appendChild(hbox);
			
			item.appendChild(c0);item.appendChild(c1);item.appendChild(c2);
			item.appendChild(c3);item.appendChild(c4);item.appendChild(c5);
			item.appendChild(c6);item.appendChild(c7);
		}

	}
	
	public class PageListener implements EventListener {

		public void onEvent(Event arg0) throws Exception {
			List teacherList = jspsService.findByYearAndPage(curYear, teacherPaging.getActivePage(), teacherPaging.getPageSize());//当年提出职称评审申请的教师列表
			ListModel listModel = new ListModelList(teacherList);
			teacherListbox.setModel(listModel);
		}

	}
	
}
