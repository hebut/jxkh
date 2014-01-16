package org.iti.gh.glyjfx.fxfzr.lwgl;

import java.util.List;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.entity.GhFxfz;
import org.iti.gh.entity.GhYjfxlw;
import org.iti.gh.service.UserfxfzService;
import org.iti.gh.service.YjfxlwService;
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

public class LwglWindox extends BaseWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	UserfxfzService userfxfzService;
	YjfxlwService yjfxlwService;
	Listbox typeList;

	@Override
	public void initShow() {
		typeList.setItemRenderer(new ListitemRenderer(){
			public void render(Listitem arg0, Object arg1) throws Exception {
				final Object[] type=(Object[]) arg1;
				Listcell c1=new Listcell(GhYjfxlw.TYPE[Short.valueOf(type[0].toString())]);
				Long num1=yjfxlwService.countByGyidAndTypeAndYear(Long.valueOf(type[1].toString()), Short.valueOf(type[0].toString()), 2011);
				Long num2= yjfxlwService.countByGyidAndTypeAndYear(Long.valueOf(type[1].toString()), Short.valueOf(type[0].toString()), 2012);
				Long num3= yjfxlwService.countByGyidAndTypeAndYear(Long.valueOf(type[1].toString()), Short.valueOf(type[0].toString()), 2013);
				Long num4= yjfxlwService.countByGyidAndTypeAndYear(Long.valueOf(type[1].toString()), Short.valueOf(type[0].toString()), 2014);
				Long num5= yjfxlwService.countByGyidAndTypeAndYear(Long.valueOf(type[1].toString()), Short.valueOf(type[0].toString()), 2015);
		 
				  Listcell c2=new Listcell();
				  if(num1!=null){
					  InnerButton bj=new InnerButton();
					  c2.appendChild(bj);
					  bj.setLabel(num1.toString());
					  final List list1=yjfxlwService.findByGyidAndTypeAndYear(Long.valueOf(type[1].toString()), Short.valueOf(type[0].toString()), 2011);
					  bj.addEventListener(Events.ON_CLICK, new EventListener(){
							public void onEvent(Event arg0) throws Exception {
								 ShowWindow sh=(ShowWindow)Executions.createComponents("/admin/xkgl/glyjfx/fxfzr/lwgl/show.zul", null, null);
								 Executions.getCurrent().setAttribute("list", list1);
								 Executions.getCurrent().setAttribute("type", GhYjfxlw.TYPE[Short.valueOf(type[0].toString())]);
								 sh.doHighlighted();
								 sh.initWindow();
							}							  
						  });
				  }
				  else c2.setLabel(0+"");
				  Listcell c3=new Listcell();
				  Listcell c4=new Listcell();
				  Listcell c5=new Listcell();
				  Listcell c6=new Listcell();
				  if(num2!=null){
					  InnerButton bj=new InnerButton();
					  c3.appendChild(bj);
					  bj.setLabel(num2.toString());
					  final List list2=yjfxlwService.findByGyidAndTypeAndYear(Long.valueOf(type[1].toString()), Short.valueOf(type[0].toString()), 2012);
						 bj.addEventListener(Events.ON_CLICK, new EventListener(){
							public void onEvent(Event arg0) throws Exception {
								 ShowWindow sh=(ShowWindow)Executions.createComponents("/admin/xkgl/glyjfx/fxfzr/lwgl/show.zul", null, null);
								 Executions.getCurrent().setAttribute("list", list2);
								 Executions.getCurrent().setAttribute("type", GhYjfxlw.TYPE[Short.valueOf(type[0].toString())]);
								 sh.doHighlighted();
								 sh.initWindow();
							}
							  
						  });
				  }
				  else c3.setLabel(0+"");				  
				  if(num3!=null){
					  InnerButton bj=new InnerButton();
					  c4.appendChild(bj);
					  bj.setLabel(num3.toString());
					  final List list2=yjfxlwService.findByGyidAndTypeAndYear(Long.valueOf(type[1].toString()), Short.valueOf(type[0].toString()), 2013);
						 bj.addEventListener(Events.ON_CLICK, new EventListener(){
							public void onEvent(Event arg0) throws Exception {
								 ShowWindow sh=(ShowWindow)Executions.createComponents("/admin/xkgl/glyjfx/fxfzr/lwgl/show.zul", null, null);
								 Executions.getCurrent().setAttribute("list", list2);
								 Executions.getCurrent().setAttribute("type", GhYjfxlw.TYPE[Short.valueOf(type[0].toString())]);
								 sh.doHighlighted();
								 sh.initWindow();
							}
							  
						  });
				  }
				  else c4.setLabel(0+"");
				  if(num4!=null){
					  InnerButton bj=new InnerButton();
					  c5.appendChild(bj);
					  bj.setLabel(num4.toString());
					  final List list2=yjfxlwService.findByGyidAndTypeAndYear(Long.valueOf(type[1].toString()), Short.valueOf(type[0].toString()), 2014);
						 bj.addEventListener(Events.ON_CLICK, new EventListener(){
							public void onEvent(Event arg0) throws Exception {
								 ShowWindow sh=(ShowWindow)Executions.createComponents("/admin/xkgl/glyjfx/fxfzr/lwgl/show.zul", null, null);
								 Executions.getCurrent().setAttribute("list", list2);
								 Executions.getCurrent().setAttribute("type", GhYjfxlw.TYPE[Short.valueOf(type[0].toString())]);
								 sh.doHighlighted();
								 sh.initWindow();
							}
							  
						  });
				  }
				  else c5.setLabel(0+"");
				  if(num5!=null){
					  InnerButton bj=new InnerButton();
					  c6.appendChild(bj);
					  bj.setLabel(num5.toString());
					  final List list2=yjfxlwService.findByGyidAndTypeAndYear(Long.valueOf(type[1].toString()), Short.valueOf(type[0].toString()), 2015);
						 bj.addEventListener(Events.ON_CLICK, new EventListener(){
							public void onEvent(Event arg0) throws Exception {
								 ShowWindow sh=(ShowWindow)Executions.createComponents("/admin/xkgl/glyjfx/fxfzr/lwgl/show.zul", null, null);
								 Executions.getCurrent().setAttribute("list", list2);
								 Executions.getCurrent().setAttribute("type", GhYjfxlw.TYPE[Short.valueOf(type[0].toString())]);
								 sh.doHighlighted();
								 sh.initWindow();
							}
							  
						  });
				  }
				  else c6.setLabel(0+"");
				  		 
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
				arg0.appendChild(c5);
				arg0.appendChild(c6);
			}
		});
	}
	@Override
	public void initWindow() {
		
		List<GhFxfz> listfz=userfxfzService.findByKuid(getXyUserRole().getId().getKuId());
		GhFxfz fxfz=listfz.get(0);
		List listtype=yjfxlwService.findTypeByGyid(fxfz.getId().getGyId());
		typeList.setModel(new ListModelList(listtype));
	}
	
	public void onClick$addLw(){
		List<GhFxfz> listfz=userfxfzService.findByKuid(getXyUserRole().getId().getKuId());
		GhFxfz fxfz=listfz.get(0);
		final AddlwWindow w=(AddlwWindow)Executions.createComponents("/admin/xkgl/glyjfx/fxfzr/lwgl/add.zul",null,null);
		w.doHighlighted();
		w.setTitle("Ìí¼ÓÂÛÎÄ");
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
