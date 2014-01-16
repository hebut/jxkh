package com.uniwin.asm.personal.ui.data.teacherinfo.jyqk;

import java.util.List;

import org.iti.gh.entity.GhZs;
import org.iti.gh.service.CgService;
import org.iti.gh.service.FmzlService;
import org.iti.gh.service.GhFileService;
import org.iti.gh.service.JlhzService;
import org.iti.gh.service.JsxmService;
import org.iti.gh.service.JxbgService;
import org.iti.gh.service.KyjhService;
import org.iti.gh.service.LwzlService;
import org.iti.gh.service.PkpyService;
import org.iti.gh.service.PxService;
import org.iti.gh.service.QkdcService;
import org.iti.gh.service.QtService;
import org.iti.gh.service.SkService;
import org.iti.gh.service.XmService;
import org.iti.gh.service.XmzzService;
import org.iti.gh.service.XshyService;
import org.iti.gh.service.YjfxService;
import org.iti.gh.service.ZsService;
import org.iti.gh.ui.listbox.GradeListbox;
import org.iti.jxgl.service.CalendarService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Textbox;
 
import com.uniwin.framework.entity.WkTUser;

public class ZsqkWindow extends BaseWindow {

	//研究生招生情况
    Listbox zsqk;
    Button search;
    Label ll;
    Panel pl;
    GradeListbox gradelist;
    Button add,all;
    Grid grid;
	//修改研究生招生情况
	Hbox hb;
	FmzlService fmzlService;
	QtService qtService;
	KyjhService kyjhService;
	PxService pxService;
	XshyService xshyService;
	SkService skService;
	ZsService zsService;
	JlhzService jlhzService;
	JxbgService jxbgService;
	XmService xmService;
	XmzzService xmzzService;
	QkdcService qkdcService;
	CgService cgService;
	LwzlService lwzlService;
	PkpyService pkpyService;
	YjfxService yjfxService;
	GhFileService ghfileService;
	WkTUser user;
	JsxmService jsxmService;
	CalendarService calendarService;
//	YearService yearService;
	@Override
	public void initShow() { 
		initWindow();
		zsqk.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				GhZs gz=(GhZs)arg1;
				arg0.setValue(gz);
				final Listcell c1=new Listcell(gz.getYear());
				final Listcell c2=new Listcell(gz.getNum()+"");
				final Listcell c3=new Listcell(gz.getName());
				final Listcell c4=new Listcell();
				final Label lb=new Label();lb.setValue("编辑");lb.setStyle("color:blue");
				final Label lbl=new Label();lbl.setValue("提交");lbl.setStyle("color:blue");
				c4.appendChild(lb);
				final Textbox t3=new Textbox();
				t3.setHeight("30px");
				t3.setWidth("400px");
				lb.addEventListener(Events.ON_CLICK, new EventListener(){

					public void onEvent(Event arg0) throws Exception { 
					t3.setValue(c3.getLabel().trim());
						c3.appendChild(t3);
						c3.setLabel(" ");
						t3.addEventListener(Events.ON_CHANGE, new EventListener(){

							public void onEvent(Event arg0) throws Exception {
								if(t3.getValue().toString().trim().equals("")){
									c2.setLabel(0+"");
								}
								else{
								String[] ming=t3.getValue().toString().trim().split("、");
								int n=ming.length;
								c2.setLabel(n+"");
								}
							}
							
						});
						c4.removeChild(lb);
						c4.appendChild(lbl);
				 
					
					}});
				lbl.addEventListener(Events.ON_CLICK, new EventListener(){

					public void onEvent(Event arg0) throws Exception { 
						if (t3.getValue().trim().contains("，")||t3.getValue().trim().contains(",")) {
							throw new WrongValueException(t3, "学生名单填写错误，请选择顿号！");
						} else {
						GhZs zs;
						List list = zsService.findByKuidAndYear(user.getKuId(),c1.getLabel());
						//if(list!=null&&list.size()!=0){
						zs = (GhZs) list.get(0);
					    zs.setNum(Integer.parseInt(c2.getLabel()));
					    zs.setName(t3.getValue());
					    zsService.update(zs);
					    Messagebox.show("更新成功！","提示", Messagebox.OK,Messagebox.INFORMATION);
					    initWindow();
						/*}
						else{
						    zs=new GhZs();
							zs.setYear(c1.getLabel().substring(0, 4));
							zs.setKuId(user.getKuId());
							zs.setNum(Integer.parseInt(c2.getLabel().toString().trim()));
							zs.setName(c3.getLabel().toString().trim());
							zsService.save(zs);
							Messagebox.show("提交成功！","提示",Messagebox.OK,Messagebox.EXCLAMATION);
							 initWindow();
						}*/
						//select(c1.getLabel());
					}
					
					}});
				arg0.appendChild(c1);arg0.appendChild(c2);arg0.appendChild(c3);arg0.appendChild(c4);
			}
			
		});

	}

	@Override
	public void initWindow() {
		user = (WkTUser) Sessions.getCurrent().getAttribute("user"); 
		List list = zsService.findByKuid(user.getKuId());
		if(list.size()!=0){
			zsqk.setModel(new ListModelList(list));
			zsqk.setVisible(true);
			pl.setVisible(false);
			}
			else{
				pl.setVisible(true);
				ll.setValue("此学年暂无招生情况,点击‘添加’按钮进行添加");
				zsqk.setVisible(false);
			}
		
	}
	public void onClick$add(){
		 
		 final Addwindow addw=(Addwindow)Executions.createComponents("/admin/personal/data/teacherinfo/jxqk/zsqk/add.zul", null, null);
		 addw.doHighlighted();
		 addw.setUid(user.getKuId());
		 addw.initWindow();
		 addw.addEventListener(Events.ON_CHANGE, new EventListener(){

			public void onEvent(Event arg0) throws Exception {
				initWindow();
				addw.detach();
			}
			 
		 });
	}
/*	public void select(String year){
		List list = zsService.findByKuidAndYear(user.getKuId(),year); 
		zsqk.setModel(new ListModelList(list));
		zsqk.setVisible(true);
	    pl.setVisible(false);
	}*/
	/*public void onClick$search(){
		List list = zsService.findByKuidAndYear(user.getKuId(),gradelist.getSelectedItem().getLabel().substring(0, 4));
		if(list!=null&&list.size()!=0){
		zsqk.setModel(new ListModelList(list));
		zsqk.setVisible(true);
		pl.setVisible(false);
		}
		else{
			pl.setVisible(true);
			ll.setValue("此学年暂无招生情况,点击‘添加’按钮进行添加");
			zsqk.setVisible(false);
		}
	}*/
	
	/*public void onClick$all(){
		List list = zsService.findByKuid(user.getKuId());
		if(list.size()!=0){
			zsqk.setModel(new ListModelList(list));
			zsqk.setVisible(true);
			pl.setVisible(false);
			}
			else{
				pl.setVisible(true);
				ll.setValue("此学年暂无招生情况,点击‘添加’按钮进行添加");
				zsqk.setVisible(false);
			}
	}*/
	
	 

}
