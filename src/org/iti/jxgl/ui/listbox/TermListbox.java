package org.iti.jxgl.ui.listbox;

import java.util.List;

import org.iti.jxgl.entity.JxCalendar;
import org.iti.jxgl.service.CalendarService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class TermListbox extends Listbox implements AfterCompose {
	CalendarService calendarService;

	public void afterCompose() {
		Components.wireVariables(this, this);
	}

	public void initTermlist() {
		this.getChildren().clear();
		Listitem l1 = new Listitem("¥∫ºæ");
		l1.setValue("0");
		Listitem l2 = new Listitem("«Ôºæ");
		l2.setValue("1");
		this.appendChild(l1);
		this.appendChild(l2);
		String[] YearTerm = calendarService.getNowYearTerm();
		switch (Integer.parseInt(YearTerm[1])) {
		case 0:
			this.setSelectedIndex(0);
			break;
		case 1:
			this.setSelectedIndex(1);
			break;
		}
	}

	public void initAnytermlist() {
		this.getChildren().clear();
		Listitem l0 = new Listitem("-»Œ“‚—ß∆⁄-");
		l0.setSelected(true);
		Listitem l1 = new Listitem("¥∫ºæ");
		Listitem l2 = new Listitem("«Ôºæ");
		this.appendChild(l0);
		this.appendChild(l1);
		this.appendChild(l2);
	}
	
	public void initCqtermlist(){
		this.getChildren().clear();
		Listitem l0 = new Listitem("-«Î—°‘Ò-");
		l0.setValue(2);
		l0.setSelected(true);
		Listitem l1 = new Listitem("¥∫ºæ");
		l1.setValue(0);	
		Listitem l2 = new Listitem("«Ôºæ");
		l2.setValue(1);
		this.appendChild(l0);
		this.appendChild(l1);
		this.appendChild(l2);
	}
	//duqing
	public void initlist() {
		this.getChildren().clear();
		Listitem l1 = new Listitem("¥∫ºæ");
		l1.setValue("0");
		Listitem l2 = new Listitem("«Ôºæ");
		l2.setValue("1");
		this.appendChild(l1);
		this.appendChild(l2);
		}
	
	public void initCtermlist(Long schid,final String year){
		this.getChildren().clear();
		List clist=calendarService.findByYearAndSchid(year, schid);
		final String []YearTerm =calendarService.getNowYearTerm();
		this.setItemRenderer(new ListitemRenderer(){ 
			public void render(Listitem arg0, Object arg1) throws Exception {
				JxCalendar jc=(JxCalendar)arg1;
				arg0.setValue(jc.getTerm());
				arg0.setLabel(jc.getTerm()==1?"«Ôºæ":"¥∫ºæ");
				if(year.equals(YearTerm[0])&&jc.getTerm()==Short.parseShort(YearTerm[1])){
					arg0.setSelected(true);
				}else{
					if(arg0.getIndex()==0){
						arg0.setSelected(true);
					}
				}				
			} 
		});
		
		this.setModel(new ListModelList(clist)); 
     }
	
	public Short getSelectTerm(){
		if(this.getSelectedItem()==null){
			return ((JxCalendar) this.getModel().getElementAt(0)).getTerm();
		}else{
			return (Short) this.getSelectedItem().getValue();
		}
	}
}
