package org.iti.kyjf.jszs;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.jxgl.ui.listbox.YearListbox;
import org.iti.kyjf.entity.Kyzb;
import org.iti.kyjf.entity.Zbteacher;
import org.iti.kyjf.service.ZbService;
import org.iti.kyjf.service.ZbteacherService;
import org.iti.xypt.entity.Teacher;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listfooter;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Space;

public class JszsWindow extends BaseWindow {

	ZbteacherService zbteacherService;
	ZbService zbService;
	YearListbox yearlist;
	Listbox zbtealist;
    Panel noteacher, teapanel;
    Listfooter heji;
    Float footsum=0F;
    NumberFormat format = new DecimalFormat("#0.0000");
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
			    	r11.setChecked(true);
			    }else if(teacher.getZtZc()!=null&&teacher.getZtZc().shortValue()==Zbteacher.FGZ){
			    	r12.setChecked(true);
			    }else{
			    	r13.setChecked(true);
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
						r22.setChecked(true);
					}else if(teacher.getTeacher().getThAdvisor()!=null&&teacher.getTeacher().getThAdvisor().intValue() == Teacher.ADVISOR_BD){
						r21.setChecked(true);
					}else{
						r23.setChecked(true);
					}
				}else{
					if(teacher.getZtDs().shortValue()==Zbteacher.BD){
						r21.setChecked(true);
					}else if(teacher.getZtDs().shortValue()==Zbteacher.SD){
						r22.setChecked(true);
					}else{
						r23.setChecked(true);
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
					r31.setChecked(true);
				}else if(teacher.getZtIfgg()!=null&&teacher.getZtIfgg().shortValue()==Zbteacher.FGG){
					r32.setChecked(true);
				}else{
					r32.setChecked(true);
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
					r41.setChecked(true);
				}else if(teacher.getZtIfcj()!=null&&teacher.getZtIfcj().shortValue()==Zbteacher.CJYX){
					r42.setChecked(true);
				}else{
					r42.setChecked(true);
				}
				r4.appendChild(r41);r4.appendChild(s41);r4.appendChild(r42);
				l6.appendChild(r4);
				Listcell l7=new Listcell();
				
				if(teacher.getZtSum()!=null){
					l7.setLabel(format.format(teacher.getZtSum())+"");
				}else{
					InnerButton ib=new InnerButton("计算");
					l7.appendChild(ib);
					ib.addEventListener(Events.ON_CLICK, new EventListener(){

						public void onEvent(Event arg0) throws Exception {
							Kyzb zb=zbService.findByYear(teacher.getZtYear(),teacher.getKdId());
							if(zb==null){
								Messagebox.show("请确定已设置该年度的指数！", "提示", Messagebox.OK, Messagebox.INFORMATION);
								return;
							}
							Float xyxs=zb.getZxxyxs();
							Float sum=Float.parseFloat("0");
							Float zhicheng=Float.parseFloat("0"), daoshi= Float.parseFloat("0"),jichu=Float.parseFloat("0"),gangwei=Float.parseFloat("0");
							if(r1.getSelectedIndex()==0){
								zhicheng=zb.getZxzg();
							}else if(r1.getSelectedIndex()==1){
								zhicheng=zb.getZxfg();
							}else{
								zhicheng=zb.getZxzcqt();
							}
							if(r2.getSelectedIndex()==0){
								daoshi=zb.getZxbd();
							}else if(r2.getSelectedIndex()==1){
								daoshi=zb.getZxSd();
							}else{
								daoshi=zb.getZxDsqt();
							}
							
							if(r3.getSelectedIndex()==0){
								jichu=zb.getZxgg();
							}else if(r3.getSelectedIndex()==1){
								jichu=zb.getZxfgg();
							}else{
								jichu=zb.getZxfgg();
							}
							if(r4.getSelectedIndex()==0){
								gangwei=zb.getZxcjys();
							}else if(r4.getSelectedIndex()==1){
								gangwei=zb.getZxcj();
							}else{
								gangwei=zb.getZxcj();
							}
							sum=xyxs*zhicheng*daoshi*jichu*gangwei;
							teacher.setZtIfcj(Short.parseShort(r4.getSelectedIndex()!=-1?r4.getSelectedIndex()+"":"1"));
							teacher.setZtIfgg(Short.parseShort(r3.getSelectedIndex()!=-1?r3.getSelectedIndex()+"":"1"));
							teacher.setZtZc(Short.parseShort(r1.getSelectedIndex()!=-1?r1.getSelectedIndex()+"":"2"));
							teacher.setZtDs(Short.parseShort(r2.getSelectedIndex()!=-1?r2.getSelectedIndex()+"":"2"));
							teacher.setZtSum(sum);
							zbteacherService.update(teacher);
							onClick$query();
						}
						
					});
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
		if(tlist!=null&&tlist.size()>0){
			zbtealist.setModel(new ListModelList(tlist));
			for(int i=0;i<tlist.size();i++){
				Zbteacher t=(Zbteacher)tlist.get(i);
				if(t.getZtSum()!=null){
					footsum=footsum+t.getZtSum();
				}
			}
			heji.setLabel(format.format(footsum)+"");
		}else{
			zbtealist.setModel(new ListModelList());
		}
		if(tlist==null||tlist.size()==0){
			 noteacher.setVisible(true);
			 teapanel.setVisible(false);
		}else{
			 noteacher.setVisible(false);
			 teapanel.setVisible(true);
		}
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
					Zbteacher t=(Zbteacher)tlist.get(i);
					if(t.getZtSum()!=null){
						footsum=footsum+t.getZtSum();
					}
				}
				heji.setLabel(format.format(footsum)+"");
				zbtealist.setModel(new ListModelList(tlist));
			}else{
				zbtealist.setModel(new ListModelList());
			}
			if(tlist==null||tlist.size()==0){
				 noteacher.setVisible(true);
				 teapanel.setVisible(false);
			}else{
				 noteacher.setVisible(false);
				 teapanel.setVisible(true);
			}
	 }
	
   public void onClick$compute(){
	    Integer  year=Integer.parseInt(yearlist.getSelectedItem().getLabel());
		List selectlist=new ArrayList();
		Set s=zbtealist.getSelectedItems();
		Iterator it=s.iterator();
		while(it.hasNext()){
			Listitem item=(Listitem)it.next();
			selectlist.add(item);
		}
	    if(selectlist.size()==0){
	    	try {
				Messagebox.show("请选择要计算的教师","提示",Messagebox.OK,Messagebox.EXCLAMATION);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }else{
	    	Kyzb zb=zbService.findByYear(year,getXyUserRole().getKdId());
	    	for(int i=0;i<selectlist.size();i++){
	    		Listitem item=(Listitem)selectlist.get(i);
	    		Zbteacher teacher=(Zbteacher)item.getValue();
				if(zb==null){
					try {
						Messagebox.show("请确定已设置该年度的指数！", "提示", Messagebox.OK, Messagebox.INFORMATION);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return;
				}
				Float xyxs=zb.getZxxyxs();
				Float sum=Float.parseFloat("0");
				Float zhicheng=Float.parseFloat("0"), daoshi= Float.parseFloat("0"),jichu=Float.parseFloat("0"),gangwei=Float.parseFloat("0");
				Radiogroup r1=(Radiogroup)((Listcell)item.getChildren().get(2)).getFirstChild();
				Radiogroup r2=(Radiogroup)((Listcell)item.getChildren().get(3)).getFirstChild();
				Radiogroup r3=(Radiogroup)((Listcell)item.getChildren().get(4)).getFirstChild();
				Radiogroup r4=(Radiogroup)((Listcell)item.getChildren().get(5)).getFirstChild();
				if(r1.getSelectedIndex()==0){
					zhicheng=zb.getZxzg();
				}else if(r1.getSelectedIndex()==1){
					zhicheng=zb.getZxfg();
				}else{
					zhicheng=zb.getZxzcqt();
				}
				if(r2.getSelectedIndex()==0){
					daoshi=zb.getZxbd();
				}else if(r2.getSelectedIndex()==1){
					daoshi=zb.getZxSd();
				}else{
					daoshi=zb.getZxDsqt();
				}
				
				if(r3.getSelectedIndex()==0){
					jichu=zb.getZxgg();
				}else if(r3.getSelectedIndex()==1){
					jichu=zb.getZxfgg();
				}else{
					jichu=zb.getZxfgg();
				}
				if(r4.getSelectedIndex()==0){
					gangwei=zb.getZxcjys();
				}else if(r4.getSelectedIndex()==1){
					gangwei=zb.getZxcj();
				}else{
					gangwei=zb.getZxcj();
				}
				sum=xyxs*zhicheng*daoshi*jichu*gangwei;
				teacher.setZtIfcj(Short.parseShort(r4.getSelectedIndex()!=-1?r4.getSelectedIndex()+"":"1"));
				teacher.setZtIfgg(Short.parseShort(r3.getSelectedIndex()!=-1?r3.getSelectedIndex()+"":"1"));
				teacher.setZtZc(Short.parseShort(r1.getSelectedIndex()!=-1?r1.getSelectedIndex()+"":"2"));
				teacher.setZtDs(Short.parseShort(r2.getSelectedIndex()!=-1?r2.getSelectedIndex()+"":"2"));
				teacher.setZtSum(sum);
				zbteacherService.update(teacher);
			
	    	}
	    	onClick$query();
	    }
	 
   }
   public void ClearFooter(){
	   footsum=0F;
	   heji.setLabel("0.0");
   }
}
