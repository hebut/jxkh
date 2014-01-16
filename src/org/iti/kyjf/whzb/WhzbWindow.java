package org.iti.kyjf.whzb;

import java.lang.reflect.Field;
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
import org.iti.kyjf.service.ZbService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Space;

public class WhzbWindow extends BaseWindow {

	YearListbox yearlist;
	Listbox zblist;
	ZbService zbService;
	Listbox exportlist;
	Listhead head;
	List clist=new ArrayList();
	@Override
	public void initShow() {
		zblist.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				 final Kyzb zb=(Kyzb)arg1;
				 arg0.setValue(zb);
				 Listcell c1=new Listcell(zb.get���()+"");
				 //ְ��ϵ��
				 Listcell c2=new Listcell();
				 Hbox h1=new Hbox();
				 Label l11=new Label(zb.getZxzg()!=null?zb.getZxzg()+"":"");//����ְ
				 Label l12=new Label(zb.getZxfg()!=null?zb.getZxfg()+"":"");//����ְ
				 Label l13=new Label(zb.getZxzcqt()!=null?zb.getZxzcqt()+"":"");//����
				 h1.appendChild(l11);
				 h1.appendChild(l12);
				 h1.appendChild(l13);
				 c2.appendChild(h1);
				 //��ʦϵ��
				 Listcell c3=new Listcell();
				 Hbox h2=new Hbox();
				 Label l21=new Label(zb.getZxbd()!=null?zb.getZxbd()+"":"");//����
				 Label l22=new Label(zb.getZxSd()!=null?zb.getZxSd()+"":"");//˶��
				 Label l23=new Label(zb.getZxDsqt()!=null?zb.getZxDsqt()+"":"");//����
				 h2.appendChild(l21);h2.appendChild(l22);h2.appendChild(l23);
				 c3.appendChild(h2);
				 //������ϵ��
				 Listcell c4=new Listcell();
				 Hbox h3=new Hbox();
				 Label l31=new Label(zb.getZxgg()!=null?zb.getZxgg()+"":"");//����������
				 Space s31=new Space();s31.setWidth("10px");
				 Label l32=new Label(zb.getZxfgg()!=null?zb.getZxfgg()+"":"");//�ǹ���������
				 h3.appendChild(l31);h3.appendChild(s31);h3.appendChild(l32);
				 c4.appendChild(h3);
				 //��λϵ��
				 Listcell c5=new Listcell();
				 Hbox h4=new Hbox();
				 Label l41=new Label(zb.getZxcjys()!=null?zb.getZxcjys()+"":"");//��������
				 Space s41=new Space();s41.setWidth("10px");
				 Label l42=new Label(zb.getZxcj()!=null?zb.getZxcj()+"":"");//����
				 h4.appendChild(l41); h4.appendChild(s41);h4.appendChild(l42);
				 c5.appendChild(h4);
				 //ѧԺϵ��
				 Listcell c6=new Listcell(zb.getZxxyxs()!=null?zb.getZxxyxs()+"":"");
//				 Hbox h5=new Hbox();
//				 Label l51=new Label(zb.getZxgkxs()!=null?zb.getZxgkxs()+"":"");//����ѧԺ
//					Space s51=new Space();s51.setWidth("20px");
//				 Label l52=new Label(zb.getZxqtxy()!=null?zb.getZxqtxy()+"":"");//����ѧԺ������
//					Space s52=new Space();s52.setWidth("20px");
//				 Label l53=new Label(zb.getZxwfxs()!=null?zb.getZxwfxs()+"":"");//�ķ�ѧԺ
//				 Space s53=new Space();s53.setWidth("20px");
//				 Label l54=new Label(zb.getZxwyxs()!=null?zb.getZxwyxs()+"":"");//����ѧԺ
//				 h5.appendChild(l51);h5.appendChild(s51);h5.appendChild(l52);
//				 h5.appendChild(s52); h5.appendChild(l53); h5.appendChild(s53);
//				 h5.appendChild(l54);
//				 c6.appendChild(h5);
				 //ѧ��ϵ��
				 Listcell c7=new Listcell();
				 Hbox h6=new Hbox();
				 Label l61=new Label(zb.getZxtsxk()!=null?zb.getZxtsxk()+"":"");//��ɫѧ��
				 Space s61=new Space();s61.setWidth("20px");
				 Label l62=new Label(zb.getZxzdjs()!=null?zb.getZxzdjs()+"":"");//211�ص㽨��
				 Space s62=new Space();s62.setWidth("20px");
				 Label l63=new Label(zb.getZxbszd()!=null?zb.getZxbszd()+"":"");//��ʿ��
				 Space s63=new Space();s63.setWidth("20px");
				 Label l64=new Label(zb.getZxzdxk()!=null?zb.getZxzdxk()+"":"");//�ص�ѧ�Ƶ�
				 Space s64=new Space();s64.setWidth("20px");
				 Label l65=new Label(zb.getZxzdsys( )!=null?zb.getZxzdsys()+"":"");//�ص�ʵ����
				 h6.appendChild(l61);h6.appendChild(s61);
				 h6.appendChild(l62); h6.appendChild(s62);
				 h6.appendChild(l63);h6.appendChild(s63);
				 h6.appendChild(l64);h6.appendChild(s64);
				 h6.appendChild(l65);
				 c7.appendChild(h6);
				 //����
				 Listcell c8=new Listcell();
				 InnerButton edit=new InnerButton("�༭");
				 c8.appendChild(edit);
				 edit.addEventListener(Events.ON_CLICK, new EventListener(){

					public void onEvent(Event arg0) throws Exception {
						if(yearlist.getSelectedItem()==null){
							try {
								Messagebox.show("��ѡ����ݣ�", "����", Messagebox.OK, Messagebox.INFORMATION);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							return;
						}
					
						Integer year=Integer.parseInt(yearlist.getSelectedItem().getLabel());
						Map arg=new HashMap();
						arg.put("year", year);
						arg.put("kdid", getXyUserRole().getKdId());
						EditZbWindow ew=(EditZbWindow)Executions.createComponents("/admin/kyjf/whzb/editzb.zul", null, arg);
					    ew.setKyzb(zb);
					    ew.doHighlighted();
					    ew.initWindow();
					   
					    ew.addEventListener(Events.ON_CHANGE, new EventListener(){

							public void onEvent(Event arg0) throws Exception {
								onClick$query();
							}
					    	
					    });
					}
					 
				 });
				 arg0.appendChild(c1);arg0.appendChild(c2);arg0.appendChild(c3);
				 arg0.appendChild(c4);arg0.appendChild(c5);arg0.appendChild(c6);
				 arg0.appendChild(c7);arg0.appendChild(c8);
				
			}
			
		});
	}

	@Override
	public void initWindow() {
		yearlist.initCqYearlist(getXyUserRole().getDept().getKdSchid());
		List list=zbService.findByKdid(getXyUserRole().getKdId());
		if(list!=null&&list.size()>0){
			zblist.setModel(new ListModelList(list));
		}else{
			zblist.setModel(new ListModelList());
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
		Kyzb kyzb=zbService.findByYear(year,getXyUserRole().getKdId());
		List alist=new ArrayList();
		if(kyzb!=null){
			alist.add(kyzb);
		}
		if(alist!=null&&alist.size()>0){
			zblist.setModel(new ListModelList(alist));
		}else{
			zblist.setModel(new ListModelList());
		}
	}
	public void onClick$add(){
		
		if(yearlist.getSelectedItem()==null){
			try {
				Messagebox.show("��ѡ����ݣ�", "����", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			return;
		}
	
		Integer year=Integer.parseInt(yearlist.getSelectedItem().getLabel());
		if(zbService.findByYear(year,getXyUserRole().getKdId())!=null){
			try {
				Messagebox.show("ѡ�������Ѿ���ָ��ϵ���������޸ĵ����Ӧ�ġ��༭����ť", "����", Messagebox.OK, Messagebox.EXCLAMATION);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    return;
		}
		Map arg=new HashMap();
		arg.put("year", year);
		arg.put("kdid", getXyUserRole().getKdId());
//		System.out.println(year+"---");
		AddZbWindow e=(AddZbWindow)Executions.createComponents("/admin/kyjf/whzb/addzb.zul", null, arg);
		Kyzb kyzb=zbService.findByYear(year-1,getXyUserRole().getKdId());
		if(kyzb!=null){
			e.setKyzb(kyzb);
		}
		e.initWindow();
		e.doHighlighted();
		e.addEventListener(Events.ON_CHANGE, new EventListener(){

			public void onEvent(Event arg0) throws Exception {
				onClick$query();
			}
			
		});
	}

}
