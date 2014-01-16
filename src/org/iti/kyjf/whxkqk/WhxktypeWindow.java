package org.iti.kyjf.whxkqk;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.jxgl.ui.listbox.YearListbox;
import org.iti.kyjf.entity.Zbdeptype;
import org.iti.kyjf.service.ZbdeptypeService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Space;

public class WhxktypeWindow extends BaseWindow {
	YearListbox yearlist;
	ZbdeptypeService zbdeptypeService;
	Listbox typelist;
	@Override
	public void initShow() {
		typelist.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				final Zbdeptype zd=(Zbdeptype)arg1;
				arg0.setValue(zd);
				Listcell c1=new Listcell(zd.getZxyear()+"");
				Listcell c2=new Listcell(zd.getDept().getKdName().trim());
				Listcell c3=new Listcell();
				final Radiogroup r3=new Radiogroup();
				Radio r31=new Radio();
				Space s3=new Space();s3.setWidth("20px");
				Radio r32=new Radio();
				if(zd.getZdTsxk()!=null&&zd.getZdTsxk().shortValue()==Zbdeptype.TSXK_YES){
					r32.setChecked(true);
				}else if(zd.getZdTsxk()!=null&&zd.getZdTsxk().shortValue()==Zbdeptype.TSXK_NO){
					r31.setChecked(true);
				}else{
					r31.setChecked(true);
				}
				r3.appendChild(r31);r3.appendChild(s3);r3.appendChild(r32);
				c3.appendChild(r3);
				Listcell c4=new Listcell();
				final Radiogroup r4=new Radiogroup();
				Radio r41=new Radio();
				Space s4=new Space();s4.setWidth("20px");
				Radio r42=new Radio();
				if(zd.getZdZdjs()!=null&&zd.getZdZdjs().shortValue()==Zbdeptype.ZDJS_YES){
					r42.setChecked(true);
				}else if(zd.getZdZdjs()!=null&&zd.getZdZdjs().shortValue()==Zbdeptype.ZDJS_NO){
					r41.setChecked(true);
				}else{
					r41.setChecked(true);
				}
				r4.appendChild(r41);r4.appendChild(s4);r4.appendChild(r42);
				c4.appendChild(r4);
				Listcell c5=new Listcell();
				final Radiogroup r5=new Radiogroup();
				Radio r51=new Radio();
				Space s5=new Space();s5.setWidth("20px");
				Radio r52=new Radio();
				if(zd.getZdBsd()!=null&&zd.getZdBsd().shortValue()==Zbdeptype.BSD_YES){
					r52.setChecked(true);
				}else if(zd.getZdBsd()!=null&&zd.getZdBsd().shortValue()==Zbdeptype.BSD_NO){
					r51.setChecked(true);
				}else{
					r51.setChecked(true);
				}
				r5.appendChild(r51);r5.appendChild(s5);r5.appendChild(r52);
				c5.appendChild(r5);
				Listcell c6=new Listcell();
				final Radiogroup r6=new Radiogroup();
				Radio r61=new Radio();
				Space s6=new Space();s6.setWidth("20px");
				Radio r62=new Radio();
				if(zd.getZdZdxk()!=null&&zd.getZdZdxk().shortValue()==Zbdeptype.ZDXK_YES){
					r62.setChecked(true);
				}else if(zd.getZdZdxk()!=null&&zd.getZdZdxk().shortValue()==Zbdeptype.ZDXK_NO){
					r61.setChecked(true);
				}else{
					r61.setChecked(true);
				}
				r6.appendChild(r61);r6.appendChild(s6);r6.appendChild(r62);
				c6.appendChild(r6);
				Listcell c7=new Listcell();
				final Radiogroup r7=new Radiogroup();
				Radio r71=new Radio();
				Space s7=new Space();s7.setWidth("20px");
				Radio r72=new Radio();
				if(zd.getZdZdsys()!=null&&zd.getZdZdsys().shortValue()==Zbdeptype.ZDSYS_YES){
					r72.setChecked(true);
				}else if(zd.getZdZdsys()!=null&&zd.getZdZdsys().shortValue()==Zbdeptype.ZDSYS_NO){
					r71.setChecked(true);
				}else{
					r71.setChecked(true);
				}
				r7.appendChild(r71);r7.appendChild(s7);r7.appendChild(r72);
				c7.appendChild(r7);
				Listcell c8=new Listcell();
				InnerButton ib=new InnerButton("保存");
				ib.addEventListener(Events.ON_CLICK, new EventListener(){

					public void onEvent(Event arg0) throws Exception {
					
						if(r3.getSelectedIndex()!=-1){
						zd.setZdTsxk(Short.parseShort(r3.getSelectedIndex()+""));
						}
						if(r4.getSelectedIndex()!=-1){
						zd.setZdZdjs(Short.parseShort(r4.getSelectedIndex()+""));
						}
						if(r5.getSelectedIndex()!=-1){
						zd.setZdBsd(Short.parseShort(r5.getSelectedIndex()+""));
						}
						if(r6.getSelectedIndex()!=-1){
						zd.setZdZdxk(Short.parseShort(r6.getSelectedIndex()+"") );
						}
						if(r7.getSelectedIndex()!=-1){
						zd.setZdZdsys( Short.parseShort(r7.getSelectedIndex()+""));
						}
						zbdeptypeService.update(zd);
						try {
							Messagebox.show("更新成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
						} catch (InterruptedException e) {
							System.out.println("更新信息异常");
						}
						onClick$query();
					}
					
				});
				c8.appendChild(ib);
				arg0.appendChild(c1);arg0.appendChild(c2);arg0.appendChild(c3);arg0.appendChild(c4);
				arg0.appendChild(c5);arg0.appendChild(c6);arg0.appendChild(c7);arg0.appendChild(c8);
			}
			
		});

	}

	@Override
	public void initWindow() {
		yearlist.initCqYearlist(getXyUserRole().getDept().getKdSchid());
		List dlist=zbdeptypeService.queryByKdid(getXyUserRole().getKdId());
		if(dlist!=null&&dlist.size()>0){
			typelist.setModel(new ListModelList(dlist));
		}else{
			typelist.setModel(new ListModelList());
		}
	}
    public void onClick$query(){
    	Integer year;
		if(yearlist.getSelectedItem()==null){
			Date now=new Date();
			year=now.getYear()+1900;
		}else{
		year=Integer.parseInt(yearlist.getSelectedItem().getLabel());
		}
		List alist=new ArrayList();
		Zbdeptype zbdeptype=zbdeptypeService.queryByYearAndKdid(year, getXyUserRole().getKdId());
		if(zbdeptype!=null){
			alist.add(zbdeptype);
		}
		if(alist!=null&&alist.size()>0){
			typelist.setModel(new ListModelList(alist));
		}else{
			typelist.setModel(new ListModelList());
		}
		
    }
    public void onClick$add(){
    	if(yearlist.getSelectedItem()==null){
			try {
				Messagebox.show("请选择年份！", "警告", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return;
		}
    	Integer year=Integer.parseInt(yearlist.getSelectedItem().getLabel());
    	if(zbdeptypeService.queryByYearAndKdid(year, getXyUserRole().getKdId())!=null){
    		try {
				Messagebox.show("已经设置了该年的学科情况，如若修改点击相应的“编辑”按钮", "警告", Messagebox.OK, Messagebox.EXCLAMATION);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    return;
    	}
    	Map arg=new HashMap();
		arg.put("year", year);
		arg.put("kdid", getXyUserRole().getKdId());
    	AddxkqkWindow AW=(AddxkqkWindow)Executions.createComponents("/admin/kyjf/whxkxs/addtype.zul", null, arg);
    	Zbdeptype zbdeptype=zbdeptypeService.queryByYearAndKdid(year-1, getXyUserRole().getKdId());
    	if(zbdeptype!=null){
    		AW.setZbdeptype(zbdeptype);
    	}
    	AW.initWindow();
    	AW.doHighlighted();
    	AW.addEventListener(Events.ON_CHANGE, new EventListener(){

			public void onEvent(Event arg0) throws Exception {
				onClick$query();
			}
			
		});
    }
}
