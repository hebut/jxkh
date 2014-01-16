package org.iti.kyjf.jszs;

import java.util.List;

import org.iti.jxgl.ui.listbox.YearListbox;
import org.iti.kyjf.entity.Zbteacher;
import org.iti.kyjf.service.ZbteacherService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Space;

public class ComputeWindow extends BaseWindow {

	ZbteacherService zbteacherService;
	YearListbox yearlist;
	Listbox zbtealist;
	Integer year=(Integer)Executions.getCurrent().getArg().get("year");
	Long kdid=(Long)Executions.getCurrent().getArg().get("kdid");
	@Override
	public void initShow() {
		zbtealist.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				Zbteacher teacher=(Zbteacher)arg1;
				arg0.setValue(teacher);
				Listcell l1=new Listcell(arg0.getIndex()+1+"");
				Listcell l2=new Listcell(teacher.getUser().getKuName());
				Listcell l3=new Listcell();
				Radiogroup r1=new Radiogroup();
				Radio r11=new Radio();
				Space s1=new Space();s1.setWidth("20px");
				Radio r12=new Radio();
				Space s2=new Space();s2.setWidth("20px");
				Radio r13=new Radio();
				r1.appendChild(r11);r1.appendChild(s1);r1.appendChild(r12);
				r1.appendChild(s2);r1.appendChild(r13);
				l3.appendChild(r1);
				Listcell l4=new Listcell();
				Radiogroup r2=new Radiogroup();
				Radio r21=new Radio();
				Space s21=new Space();s21.setWidth("20px");
				Radio r22=new Radio();
				Space s22=new Space();s22.setWidth("20px");
				Radio r23=new Radio();
				r2.appendChild(r21);r2.appendChild(s21);r2.appendChild(r22);
				r2.appendChild(s22); r2.appendChild(r23); 
				l4.appendChild(r2);
				Listcell l5=new Listcell();
				Radiogroup r3=new Radiogroup();
				Radio r31=new Radio();
				Space s31=new Space();s31.setWidth("30px");
				Radio r32=new Radio();
				r3.appendChild(r31);r3.appendChild(s31);r3.appendChild(r32);
				l5.appendChild(r3);
				Listcell l6=new Listcell();
				Radiogroup r4=new Radiogroup();
				Radio r41=new Radio();
				Space s41=new Space();s41.setWidth("30px");
				Radio r42=new Radio();
				r4.appendChild(r41);r4.appendChild(s41);r4.appendChild(r42);
				l6.appendChild(r4);
				Listcell l7=new Listcell(teacher.getZtSum()!=null?teacher.getZtSum()+"":"");
				arg0.appendChild(l1);arg0.appendChild(l2);arg0.appendChild(l3);
				arg0.appendChild(l4);arg0.appendChild(l5);arg0.appendChild(l6);
				arg0.appendChild(l7);
			}
			
		});

	}

	@Override
	public void initWindow() {
		List tlist=zbteacherService.findByYear(year,kdid,"","");
		if(tlist!=null){
			zbtealist.setModel(new ListModelList(tlist));
		}else{
			zbtealist.setModel(new ListModelList());
		}

	}

}
