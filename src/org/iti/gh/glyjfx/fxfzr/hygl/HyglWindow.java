package org.iti.gh.glyjfx.fxfzr.hygl;

import java.util.ArrayList;
import java.util.List;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.entity.GhFxfz;
import org.iti.gh.entity.GhYjfxhy;
import org.iti.gh.service.UserfxfzService;
import org.iti.gh.service.YjfxhyService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
public class HyglWindow extends BaseWindow {
	Grid hygrid;
	YjfxhyService yjfxhyService;
	UserfxfzService userfxfzService;
	@Override
	public void initShow() {
		hygrid.setRowRenderer(new RowRenderer(){
			public void render(Row arg0, Object arg1) throws Exception {
				 final Short hyname=(Short)arg1;
				 Grid sf=new Grid();
				 sf.setStyle("border:0");
				 final Columns col=new Columns();
				 col.setVisible(false);
				 Column col1=new Column();
				 col1.setWidth("15%");
				 col1.setAlign("center");
				 Column col2=new Column("2011");
				 col2.setWidth("15%");
				 col2.setAlign("center");
				 Column col3=new Column("2012");
				 col3.setWidth("15%");
				 col3.setAlign("center");
				 Column col4=new Column("2013");
				 col4.setWidth("15%");
				 col4.setAlign("center");
				 Column col5=new Column("2014");
				 col5.setWidth("15%");
				 col5.setAlign("center");
				 Column col6=new Column("2015");
				 col6.setWidth("15%");
				 col6.setAlign("center");
				 col.appendChild(col1); col.appendChild(col2); col.appendChild(col3);
				 col.appendChild(col4); col.appendChild(col5); col.appendChild(col6);
				 sf.appendChild(col);
				 sf.setRowRenderer(new RowRenderer(){
						public void render(Row arg0, Object arg1) throws Exception {
							 final Short sfm=(Short)arg1;
							 if(hyname==(short)0){
							 col.setVisible(true);} 
							 arg0.appendChild(new Label(GhYjfxhy.SF[sfm]));
							 GhFxfz fz=(GhFxfz)userfxfzService.findByKuid(getXyUserRole().getId().getKuId()).get(0);
							 InnerButton bu1=new InnerButton();
							 InnerButton bu2=new InnerButton();
							 InnerButton bu3=new InnerButton();
							 InnerButton bu4=new InnerButton();
							 InnerButton bu5=new InnerButton();
//							 Label bu1=new Label();
//							 Label bu2=new Label();
//							 Label bu3=new Label();
//							 Label bu4=new Label();
//							 Label bu5=new Label();
							 load(bu1,fz.getId().getGyId(),hyname,sfm,2011);
							 load(bu2,fz.getId().getGyId(),hyname,sfm,2012);
							 load(bu3,fz.getId().getGyId(),hyname,sfm,2013);
							 load(bu4,fz.getId().getGyId(),hyname,sfm,2014);
							 load(bu5,fz.getId().getGyId(),hyname,sfm,2015);
							 arg0.appendChild(bu1);
							 arg0.appendChild(bu2);
							 arg0.appendChild(bu3);
							 arg0.appendChild(bu4);
							 arg0.appendChild(bu5);
							 }});
				 String[] sfl=GhYjfxhy.SF;
					List sflist=new ArrayList();
					for(int i=0;i<sfl.length;i++){
						sflist.add((short)i);
					}
				sf.setModel(new ListModelList(sflist));
				 arg0.appendChild(new Label(GhYjfxhy.TYPE[hyname]));
				 arg0.appendChild(sf);
			}});
	}
	public void load(InnerButton ibutton,final Long gyid,final Short type,final Short sff,final Integer year){
		 List hy11=yjfxhyService.findByGyidAndTypeAndSfAndYear(gyid, type, sff, year);
		 ibutton.setLabel(hy11.size()+"");
		// ibutton.setStyle("color:blue");
		ibutton.addEventListener(Events.ON_CLICK, new EventListener(){
			public void onEvent(Event arg0) throws Exception {
			CkhyWindow ckwin=(CkhyWindow)Executions.createComponents("/admin/xkgl/glyjfx/fxfzr/hygl/ckhy.zul",null,null);
			ckwin.setGyid(gyid);
			ckwin.setSf(sff);
			ckwin.setType(type);
			ckwin.setYear(year);
			ckwin.doHighlighted();
			ckwin.initWindow();
			ckwin.addEventListener(Events.ON_CHANGE, new EventListener(){
			public void onEvent(Event arg0) throws Exception {
				initWindow();
			}});
			}
		});
		
		}
	@Override
	public void initWindow() {
		String[] type=GhYjfxhy.TYPE;
		List hylist=new ArrayList();
		for(int i=0;i<type.length;i++){
			hylist.add((short)i);
		}
		hygrid.setModel(new ListModelList(hylist));
	}
	public void onClick$addhy(){
		final GhFxfz fz=(GhFxfz)userfxfzService.findByKuid(getXyUserRole().getId().getKuId()).get(0);
		AddhyWindow win=(AddhyWindow)Executions.createComponents("/admin/xkgl/glyjfx/fxfzr/hygl/addhy.zul",null,null);
		win.setGyId(fz.getId().getGyId());
        win.doHighlighted();
		win.initWindow();
	win.addEventListener(Events.ON_CHANGE, new EventListener(){
			public void onEvent(Event arg0) throws Exception {
				initWindow();
			}
		});
		
	}

}
