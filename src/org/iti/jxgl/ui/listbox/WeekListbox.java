package org.iti.jxgl.ui.listbox;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.iti.jxgl.entity.JxCalendar;
import org.iti.jxgl.service.CalendarService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;


public class WeekListbox extends Listbox implements AfterCompose {

	CalendarService calendarService;
	Integer week;
    
	public void afterCompose() {
		Components.wireVariables(this, this);
	}

	public void initSelectList(String cyear, short term,final Long schid) {
		final JxCalendar calendar = (JxCalendar) calendarService.findByCyearAndTermAndKdid(cyear, term,schid);
		List weeklist = new ArrayList();
		if (calendar != null) {
			week = Integer.parseInt(calendar.getWeeks());
			for (int i = 0; i < week; i++) {
				weeklist.add(i + 1);
			}
		}
		if (weeklist != null && weeklist.size() > 0) {
			this.setModel(new ListModelList(weeklist));
		} else {
			this.setModel(new ListModelList());
		}
		
		this.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception {
				Integer it = (Integer) data;
				item.setValue(it);
				final Calculateweek cw = new Calculateweek();
				Date now = new Date();
				if(now.compareTo(calendar.getStarttime())<0&&item.getIndex()==0){
					item.setSelected(true);
				}else if (now.compareTo(calendar.getStarttime())>=0&&cw.getCurrentWeek(schid) == it) {
					item.setSelected(true);
				}
				item.setLabel("µÚ"+it+ "ÖÜ");
			}
		});
	}
	
	public String getSelectWeek() {
		if (this.getSelectedItem() == null) {
			return this.getModel().getElementAt(0)+"";
		} else {
			return  this.getSelectedItem().getValue()+"";
		}
	}
}
