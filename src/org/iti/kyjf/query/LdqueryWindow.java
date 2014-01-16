package org.iti.kyjf.query;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import org.iti.jxgl.ui.listbox.YearListbox;
import org.iti.kyjf.entity.Kyzb;
import org.iti.kyjf.entity.Zbdeptype;
import org.iti.kyjf.entity.Zbteacher;
import org.iti.kyjf.service.ZbService;
import org.iti.kyjf.service.ZbdeptypeService;
import org.iti.kyjf.service.ZbteacherService;
import org.iti.xypt.entity.Teacher;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listfooter;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Space;

public class LdqueryWindow extends BaseWindow {

	Listbox zbtealist;
	YearListbox yearlist;
	ZbteacherService zbteacherService;
	ZbdeptypeService zbdeptypeService;
	ZbService zbService;
	Button export;
    Listfooter heji;
	Float footsum=0F;
	NumberFormat format = new DecimalFormat("#0.0000");
	Label tsxk,zdjs,bsd,zdxk,zdsys;
	@Override
	public void initShow() {
		zbtealist.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				final Zbteacher teacher=(Zbteacher)arg1;
				arg0.setValue(teacher);
				Listcell l1=new Listcell(arg0.getIndex()+1+"");
				Listcell l2=new Listcell(teacher.getUser().getKuName());
				Listcell l3=new Listcell();
				//处理职称系数
				final Radiogroup r1=new Radiogroup();
				Radio r11=new Radio();
				Space s1=new Space();s1.setWidth("20px");
				Radio r12=new Radio();
				Space s2=new Space();s2.setWidth("20px");
				Radio r13=new Radio();
			    if(teacher.getZtZc()!=null&&teacher.getZtZc().shortValue()==Zbteacher.ZGZ){
			    	r11.setChecked(true);  r11.setDisabled(true);	r12.setDisabled(true); r13.setDisabled(true);
			    }else if(teacher.getZtZc()!=null&&teacher.getZtZc().shortValue()==Zbteacher.FGZ){
			    	r12.setChecked(true); r11.setDisabled(true); r12.setDisabled(true); r13.setDisabled(true);
			    }else{
			    	r13.setChecked(true); r11.setDisabled(true); r12.setDisabled(true); r13.setDisabled(true);
			    }
			  
				r1.appendChild(r11);r1.appendChild(s1);r1.appendChild(r12);
				r1.appendChild(s2);r1.appendChild(r13);
				l3.appendChild(r1);
				//处理导师系数
				Listcell l4=new Listcell();
				final Radiogroup r2=new Radiogroup();
				Radio r21=new Radio();
				Space s21=new Space();s21.setWidth("20px");
				Radio r22=new Radio();
				Space s22=new Space();s22.setWidth("20px");
				Radio r23=new Radio();
				if(teacher.getZtDs()==null){
					if(teacher.getTeacher().getThAdvisor()!=null&&teacher.getTeacher().getThAdvisor().intValue() == Teacher.ADVISOR_SD){
						r22.setChecked(true);r22.setDisabled(true);r21.setDisabled(true);r23.setDisabled(true);
					}else if(teacher.getTeacher().getThAdvisor()!=null&&teacher.getTeacher().getThAdvisor().intValue() == Teacher.ADVISOR_BD){
						r21.setChecked(true);r22.setDisabled(true);r21.setDisabled(true);r23.setDisabled(true);
					}else{
						r23.setChecked(true);r22.setDisabled(true);r21.setDisabled(true);r23.setDisabled(true);
					}
				}else{
					if(teacher.getZtDs().shortValue()==Zbteacher.BD){
						r21.setChecked(true);r22.setDisabled(true);r21.setDisabled(true);r23.setDisabled(true);
					}else if(teacher.getZtDs().shortValue()==Zbteacher.SD){
						r22.setChecked(true);r22.setDisabled(true);r21.setDisabled(true);r23.setDisabled(true);
					}else{
						r23.setChecked(true);r22.setDisabled(true);r21.setDisabled(true);r23.setDisabled(true);
					}
				}
			
				r2.appendChild(r21);r2.appendChild(s21);r2.appendChild(r22);
				r2.appendChild(s22); r2.appendChild(r23); 
				l4.appendChild(r2);
				//处理基础课系数
				Listcell l5=new Listcell();
				final Radiogroup r3=new Radiogroup();
				Radio r31=new Radio();
				Space s31=new Space();s31.setWidth("30px");
				Radio r32=new Radio();
				if(teacher.getZtIfgg()!=null&&teacher.getZtIfgg().shortValue()==Zbteacher.GG){
					r31.setChecked(true);r32.setDisabled(true);r31.setDisabled(true); 
				}else if(teacher.getZtIfgg()!=null&&teacher.getZtIfgg().shortValue()==Zbteacher.FGG){
					r32.setChecked(true);r32.setDisabled(true);r31.setDisabled(true); 
				}else{
					r32.setChecked(true);r32.setDisabled(true);r31.setDisabled(true); 
				}
				r3.appendChild(r31);r3.appendChild(s31);r3.appendChild(r32);
				l5.appendChild(r3);
				//处理岗位系数
				Listcell l6=new Listcell();
				final Radiogroup r4=new Radiogroup();
				Radio r41=new Radio();
				Space s41=new Space();s41.setWidth("30px");
				Radio r42=new Radio();
				if(teacher.getZtIfcj()!=null&&teacher.getZtIfcj().shortValue()==Zbteacher.CJYS){
					r41.setChecked(true);r42.setDisabled(true);r41.setDisabled(true); 
				}else if(teacher.getZtIfcj()!=null&&teacher.getZtIfcj().shortValue()==Zbteacher.CJYX){
					r42.setChecked(true);r42.setDisabled(true);r41.setDisabled(true); 
				}else{
					r42.setChecked(true);r42.setDisabled(true);r41.setDisabled(true); 
				}
				r4.appendChild(r41);r4.appendChild(s41);r4.appendChild(r42);
				l6.appendChild(r4);
				Listcell l7=new Listcell();
				if(teacher.getZtSum()!=null){
					l7.setLabel(format.format(teacher.getZtSum())+"");
				}else{
					l7.setLabel("还未计算");
				}
				arg0.appendChild(l1);arg0.appendChild(l2);arg0.appendChild(l3);
				arg0.appendChild(l4);arg0.appendChild(l5);arg0.appendChild(l6);
				arg0.appendChild(l7);
			}
			
		});
	}

	@Override
	public void initWindow() {
		ClearFooter();
		yearlist.initCqYearlist(getXyUserRole().getDept().getKdSchid());
		Integer year;
		if(yearlist.getSelectedItem()!=null){
			year=Integer.parseInt(yearlist.getSelectedItem().getLabel());
		}else{
			Date now=new Date();
			year=now.getYear()+1900;
		}
		List tlist=zbteacherService.findByYear(year,getXyUserRole().getKdId(),"","");
		if(tlist!=null){
			for(int i=0;i<tlist.size();i++){
				Zbteacher teacher=(Zbteacher)tlist.get(i);
				if(teacher.getZtSum()!=null){
					footsum=footsum+teacher.getZtSum();
				}
			}
			heji.setLabel(format.format(footsum)+"");
			zbtealist.setModel(new ListModelList(tlist));
		}else{
			zbtealist.setModel(new ListModelList());
		}
		Kyzb zb=zbService.findByYear(year, getXyUserRole().getKdId());
		Zbdeptype zbdeptype=zbdeptypeService.queryByYearAndKdid(year, getXyUserRole().getKdId());
		if(zb!=null&&zb.getZxtsxk()!=null&&zbdeptype!=null&&zbdeptype.getZdTsxk()!=null){
			tsxk.setValue(zb.getZxtsxk()*zbdeptype.getZdTsxk()+"");
		}else{
			tsxk.setValue(0.0+"");
		}
		if(zb!=null&&zb.getZxzdjs()!=null&&zbdeptype!=null&&zbdeptype.getZdZdjs()!=null){
			zdjs.setValue(zb.getZxzdjs()*zbdeptype.getZdZdjs()+"");
		}else {
			zdjs.setValue("0.0");
		}
		if(zb!=null&&zb.getZxbszd()!=null&&zbdeptype!=null&&zbdeptype.getZdBsd()!=null){
			bsd.setValue(zb.getZxbszd()*zbdeptype.getZdBsd()+"");
		}else{
			bsd.setValue("0.0");
		}
		if(zb!=null&&zb.getZxzdxk()!=null&&zbdeptype!=null&&zbdeptype.getZdZdxk()!=null){
			 zdxk.setValue(zb.getZxzdxk()*zbdeptype.getZdZdxk()+"");
		}else{
			zdxk.setValue("0.0");
		}
		if(zb!=null&&zb.getZxzdsys()!=null&&zbdeptype!=null&&zbdeptype.getZdZdsys()!=null){
			zdsys.setValue(zb.getZxzdsys()*zbdeptype.getZdZdsys()+"");
		}else{
			zdsys.setValue("0.0");
		}
		 
		export.setHref("/exportzbfj.do?action=export&year="+(yearlist.getSelectedItem()!=null?Integer.parseInt(yearlist.getSelectedItem().getLabel()):year)+"&kdid="+getXyUserRole().getKdId());
		export.setTarget("_blank");
	}
	 public void onClick$query(){
		 ClearFooter();
		   Integer year;
			if(yearlist.getSelectedItem()!=null){
				year=Integer.parseInt(yearlist.getSelectedItem().getLabel());
			}else{
				Date now=new Date();
				year=now.getYear()+1900;
			}
			List tlist=zbteacherService.findByYear(year,getXyUserRole().getKdId(),"","");
			if(tlist!=null&&tlist.size()>0){
				for(int i=0;i<tlist.size();i++){
					Zbteacher teacher=(Zbteacher)tlist.get(i);
					footsum=footsum+teacher.getZtSum();
				}
				heji.setLabel(format.format(footsum)+"");
				zbtealist.setModel(new ListModelList(tlist));
			}else{
				zbtealist.setModel(new ListModelList());
			}
			export.setHref("/exportzbfj.do?action=export&year="+(yearlist.getSelectedItem()!=null?Integer.parseInt(yearlist.getSelectedItem().getLabel()):year)+"&kdid="+getXyUserRole().getKdId());
			export.setTarget("_blank");
	 }
	 public void ClearFooter(){
		   footsum=0F;
		   heji.setLabel("0.0");
	   }
}
