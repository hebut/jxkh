package org.iti.gh.ui.listbox;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.iti.jxgl.entity.JxYear;
import org.iti.jxgl.service.CalendarService;
import org.iti.jxgl.service.YearService;
import org.jfree.data.time.Year;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class YearListbox extends Listbox implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8972021543323301456L;
	List<Object> yearlist = new ArrayList<Object>();
	ListModelList yearModelList;
	YearService yearService;
	CalendarService calendarService;
	String defaultYear = null;

	public void afterCompose() {
		Components.wireVariables(this, this);
	}

	public void initYearlist() {
		// JxYear year=new JxYear();
		// year.setYears("«Î—°‘Ò");
		// yearlist.add(year);
		yearlist.clear();
		yearlist.addAll(yearService.getAllYear());
		yearModelList = new ListModelList();
		yearModelList.addAll(yearlist);
		this.setModel(yearModelList);
		Date now = new Date();
		/*----------------changed--------------*/
		final String Cyear = Calendar.getInstance().get(Calendar.YEAR) + "";
		/*----------------changed--------------*/
		//final String Cyear = now.getYear() + 1900 + "";
		this.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception {
				JxYear year = (JxYear) data;
				item.setValue(year);
				if (Cyear.equals(year.getYears())) {
					item.setSelected(true);
				}
				item.setLabel(year.getYears());
			}
		});
	}

	public void initCyearlist() {
		yearlist.clear();
		yearlist.addAll(yearService.getAllYear());
		yearModelList = new ListModelList();
		yearModelList.addAll(yearlist);
		this.setModel(yearModelList);
		//Date now = new Date();
		/*----------------changed--------------*/
		final String Cyear = Calendar.getInstance().get(Calendar.YEAR) + "";
		/*----------------changed--------------*/
		//final String Cyear = now.getYear() + 1900 + "";
		this.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception {
				JxYear year = (JxYear) data;
				item.setValue(year);
				if (Cyear.equals(year.getYears())) {
					item.setSelected(true);
				}
				item.setLabel(year.getYears());
			}
		});
	}
	public void initYearListbox(final String y) {	
		yearlist.clear();
		yearlist.add("");
		Integer endYear = Integer.parseInt(new Year()+"");	
		for(Integer i=endYear,j=1;i>=2011;i--,j++)
		{
			yearlist.add(i.toString());
		}
		this.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception {
				String year = (String) data;
				if(year.equals("")) {
					item.setLabel("-«Î—°‘Ò-");
				}else {
					item.setValue(year);
					item.setLabel(year);					
				}	
				if (y.equals(year)) {
					item.setSelected(true);
				}
			}
		});
		this.setModel(new ListModelList(yearlist));		
	}
	public void initAnyyearlist() {
		yearlist.clear();
		JxYear year = new JxYear();
		year.setYears("-«Î—°‘Ò-");
		yearlist.add(year);
		yearlist.addAll(yearService.getAllYear());
		yearModelList = new ListModelList();
		yearModelList.addAll(yearlist);
		this.setModel(yearModelList);
		this.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception {
				JxYear year = (JxYear) data;
				item.setValue(year);
				item.setLabel(year.getYears());
				if (year.getYears().equals("-«Î—°‘Ò-")) {
					item.setSelected(true);
				}
			}
		});
	}

	public void initYearAndTermList() {
		yearlist.clear();
		List templist = yearService.getAllYear();
		//Date now = new Date();
		/*----------------changed--------------*/
		final Integer Year = Calendar.getInstance().get(Calendar.YEAR);
		/*----------------changed--------------*/
		//final Integer Year = now.getYear() + 1900;
		final String[] Term = calendarService.getNowYearTerm();
		for (int i = 0; i < templist.size(); i++) {
			JxYear year = (JxYear) templist.get(i);
			yearlist.add(new Object[] { year.getYears(), "0" });
			yearlist.add(new Object[] { year.getYears(), "1" });
		}
		yearModelList = new ListModelList();
		yearModelList.addAll(yearlist);
		this.setModel(yearModelList);
		this.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception {
				Object[] year = (Object[]) data;
				item.setValue(year);
				if (year[1].toString().equals("0"))
					item.setLabel(year[0] + "ƒÍ-¥∫ºæ");
				else
					item.setLabel(year[0] + "ƒÍ-«Ôºæ");
				if (year[0].toString().equals(Year.toString()) && year[1].toString().equals(Term[1].toString())) {
					item.setSelected(true);
				}
			}
		}); 
	}

	public void initCqYearlist(Long schid) {
		Calendar ca = Calendar.getInstance();
		String cyear = String.valueOf(ca.get(Calendar.YEAR));
		this.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception {
				String year = (String) data;
				item.setValue(year);
				item.setLabel(year);
				if (year.equals(defaultYear)) {
					item.setSelected(true);
				}
			}
		});
		List yearlist = new ArrayList();
		if (schid != null) {
			List year = calendarService.findyearByschid(schid);
			yearlist.addAll(year);
		}
		if (yearlist.contains(cyear)) {
			defaultYear = cyear;
		} else {
			defaultYear = (String) yearlist.get(0);
		}
		this.setModel(new ListModelList(yearlist));
	}

	public String getSelectYear() {
		if (this.getSelectedItem() == null) {
			return defaultYear;
		} else {
			return (String) this.getSelectedItem().getValue();
		}
	}
}
