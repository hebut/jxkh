package org.iti.gh.glyjfx.fxfzr.xmgl;

import java.util.List;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.entity.GhFxfz;
import org.iti.gh.entity.GhYjfxxm;
import org.iti.gh.glyjfx.EdityjfxWindow;
import org.iti.gh.service.UserfxfzService;
import org.iti.gh.service.YjfxxmService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;

public class XmglWindow extends BaseWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	UserfxfzService userfxfzService;
	YjfxxmService yjfxxmService;
	Listbox xmlist;

	@Override
	public void initShow() {
		 
		xmlist.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhYjfxxm xm=(GhYjfxxm) arg1;
				arg0.setValue(xm);
				Listcell c1=new Listcell(arg0.getIndex()+1+"");
				Listcell c2=new Listcell(xm.getGxName());
				Listcell c3=new Listcell(xm.getUser().getKuName());
				Listcell c4=new Listcell();
				Listcell c5=new Listcell();
				Listcell c6=new Listcell();
				if(xm.getGxJb()!=null){
					c4.setLabel(GhYjfxxm.JB[xm.getGxJb()]);
				}
				if(xm.getGxXmlb()!=null){
					c5.setLabel(GhYjfxxm.XMLB[xm.getGxXmlb()]);
				}
				if(xm.getGxLb()!=null){
					c6.setLabel(GhYjfxxm.LB[xm.getGxLb()]);
				}
				Listcell c7=new Listcell(xm.getGxYear()==null?"":xm.getGxYear()+"");
				Listcell c8=new Listcell();
				InnerButton xg=new InnerButton();
				xg.addEventListener(Events.ON_CLICK, new EventListener(){
					public void onEvent(Event arg0) throws Exception {
						List<GhFxfz> listfz=userfxfzService.findByKuid(getXyUserRole().getId().getKuId());
						GhFxfz fxfz=listfz.get(0);
						final EditxmWindow w=(EditxmWindow)Executions.createComponents("/admin/xkgl/glyjfx/fxfzr/xmgl/edit.zul",null,null);
						w.setXyUserRole(getXyUserRole()); 
						w.setGyid(fxfz.getId().getGyId());
						w.setXm(xm);
						w.doHighlighted();
						w.initWindow();
						w.addEventListener(Events.ON_CHANGE, new EventListener(){ 
							public void onEvent(Event arg0) throws Exception {
								 initWindow();
								 w.detach();
							} 
						});
					}}); 
				InnerButton sc=new InnerButton();
				sc.setLabel("删除");xg.setLabel("编辑/");
				c8.appendChild(xg);c8.appendChild(sc);
				sc.addEventListener(Events.ON_CLICK, new EventListener(){
					public void onEvent(Event arg0) throws Exception {
						if(	Messagebox.show("确定删除吗?", "提示", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==1){
							yjfxxmService.delete(xm);
						}
						initWindow();
					}						
				});
				arg0.appendChild(c1);arg0.appendChild(c2);arg0.appendChild(c3);arg0.appendChild(c4);
				arg0.appendChild(c5);arg0.appendChild(c6);arg0.appendChild(c7);arg0.appendChild(c8);
				
			}
			
		});

	}

	@Override
	public void initWindow() {
		List<GhFxfz> listfz=userfxfzService.findByKuid(getXyUserRole().getId().getKuId());
		GhFxfz fxfz=listfz.get(0);
		List<GhYjfxxm> listxm=yjfxxmService.findByGyId(fxfz.getId().getGyId());
		xmlist.setModel(new ListModelList(listxm));

	}
	public void onClick$addXm(){
		List<GhFxfz> listfz=userfxfzService.findByKuid(getXyUserRole().getId().getKuId());
		GhFxfz fxfz=listfz.get(0);
		final AddxmWindow w=(AddxmWindow)Executions.createComponents("/admin/xkgl/glyjfx/fxfzr/xmgl/add.zul",null,null);
		w.doHighlighted();
		w.setTitle("添加项目");
		w.setGyid(fxfz.getId().getGyId());
		w.setXyUserRole(getXyUserRole());
		w.initWindow();
		w.addEventListener(Events.ON_CHANGE, new EventListener(){
			public void onEvent(Event arg0) throws Exception {
				   initWindow();
				   w.detach();
			}
		});
	}
	

}
