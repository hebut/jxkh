package org.iti.gh.ui.listbox;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.iti.gh.service.ZsService;
import org.iti.jxgl.entity.JxYear;
import org.iti.jxgl.service.CalendarService;
import org.iti.jxgl.service.YearService;
import org.iti.xypt.entity.Yjs;
import org.iti.xypt.service.XyClassService;
import org.iti.xypt.service.YjsService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.uniwin.basehs.util.BeanFactory;

public class GradeListbox extends Listbox implements AfterCompose {
	List gradelist = new ArrayList();
	ListModelList gradeModelList = new ListModelList();
	YearService yearService;
	CalendarService calendarService;
	XyClassService xyClassService;
	YjsService yjsService = (YjsService) BeanFactory.getBean("yjsService");
	ZsService zsService =(ZsService)BeanFactory.getBean("zsService");
	Integer defaultYear = null;

	public void afterCompose() {
		Components.wireVariables(this, this);
	}

	public void initGradelist() {
		gradelist.clear();
		gradeModelList.clear();
		gradelist.addAll(yearService.findAll(JxYear.class));
		if (gradelist.size() != 0) {
			gradeModelList.addAll(gradelist);
			this.setModel(gradeModelList);
			this.setSelectedIndex(0);
		} else {
			this.setModel(gradeModelList);
		}
		String[] YearTerm = calendarService.getNowYearTerm();
		final String grade = YearTerm[0];
		this.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception {
				JxYear year = (JxYear) data;
				item.setValue(year);
				if (year.getgrade().substring(0, 4).equals(grade)) {
					item.setSelected(true);
				}
				item.setLabel(year.getgrade());
			}
		});
	}

	public void initCqGradelist(Long kdid) {
		List gradelist = new ArrayList();
		JxYear jy = new JxYear();
		jy.setgrade("-请选择-");
		jy.setId(0L);
		gradelist.add(jy);
		if (kdid != null) {
			List clist = xyClassService.findgradeByKdid(kdid);
			for (int i = 0; i < clist.size(); i++) {
				JxYear j = new JxYear();
				j.setgrade(clist.get(i) + "");
				gradelist.add(j);
			}
		}
		this.setModel(new ListModelList(gradelist));
		this.setSelectedIndex(0);
		this.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception {
				JxYear year = (JxYear) data;
				item.setValue(year);
				item.setLabel(year.getgrade());
			}
		});
	}

	public void initGradelist(Long kdid) {
		List gradelist = yjsService.findgradeByKdid(kdid);
		this.setModel(new ListModelList(gradelist));
	}

	public void initGradelistll(Long kdid) {
		Date now = new Date();
		final int year = now.getYear() + 1900 - 1;
		List gradelist = yjsService.findgradeByKdid(kdid);
		if (gradelist.contains(year)) {
			defaultYear = year;
		} else {
			defaultYear = (Integer) gradelist.get(0);
		}
		this.setModel(new ListModelList(gradelist));
		this.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception {
				Integer ye = (Integer) data;
				item.setValue(ye);
				item.setLabel(ye + "");
				if (ye == year) {
					item.setSelected(true);
				}
			}
		});
	}

	public Integer getSelectGradell() {
		if (this.getSelectedItem() == null) {
			return defaultYear;
		} else {
			return (Integer) this.getSelectedItem().getValue();
		}
	}

	public Integer getSelectGrade() {
		if (this.getSelectedItem() == null) {
			return (Integer) this.getModel().getElementAt(0);
		} else {
			return (Integer) this.getSelectedItem().getValue();
		}
	}
	/**
	 * <li>功能描述：查找所有年级，默认显示“-请选择-”
	 */
	public void initlist() {
		gradelist.clear();
		gradeModelList.clear();
		JxYear jy = new JxYear();
		jy.setgrade("-请选择-");
		jy.setId(0L);
		gradelist.add(jy);
		gradelist.addAll(yearService.findAll(JxYear.class));
		if (gradelist.size() != 0) {
			gradeModelList.addAll(gradelist);
			this.setModel(gradeModelList);
			this.setSelectedIndex(0);
		} else {
			this.setModel(gradeModelList);
		}
		/*String[] YearTerm = calendarService.getNowYearTerm();
		final String grade = YearTerm[0];*/
		this.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception {
				JxYear year = (JxYear) data;
				item.setValue(year);
				/*if (year.getgrade().substring(0, 4).equals(grade)) {
					item.setSelected(true);
				}*/
				item.setLabel(year.getgrade());
			}
		});
	}
	
	public void initZsGradelist(Long kuid){
		gradelist.clear();
		gradeModelList.clear();
		
		List ylist=yearService.findAll(JxYear.class);
		for(int i=0;i<ylist.size();i++){
			JxYear year = (JxYear)ylist.get(i);
			List zlist=zsService.findByKuidAndYear(kuid, year.getYears());
			if(zlist!=null&&zlist.size()>0){
				continue;
			}else{
				gradelist.add(year);
			}
		}
		if (gradelist.size() != 0) {
			gradeModelList.addAll(gradelist);
			this.setModel(gradeModelList);
			this.setSelectedIndex(0);
		} else {
			this.setModel(gradeModelList);
		}
		String[] YearTerm = calendarService.getNowYearTerm();
		final String grade = YearTerm[0];
		this.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception {
				JxYear year = (JxYear) data;
				item.setValue(year);
				if (year.getgrade().substring(0, 4).equals(grade)) {
					item.setSelected(true);
				}
				item.setLabel(year.getgrade());
			}
		});
		
	}
	
}
