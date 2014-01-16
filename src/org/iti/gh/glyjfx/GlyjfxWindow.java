package org.iti.gh.glyjfx;

import java.util.List;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.entity.GhYjfx;
import org.iti.gh.service.UseryjfxService;
import org.iti.gh.service.YjfxService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;

public class GlyjfxWindow extends BaseWindow {
	Listbox yjfxlist;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	YjfxService yjfxService;
	UseryjfxService useryjfxService;

	@Override
	public void initShow() {
		
		yjfxlist.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhYjfx yjfx=(GhYjfx)arg1;
				Listcell c1=new Listcell(arg0.getIndex()+1+"");
				Listcell c2=new Listcell(yjfx.getGyName());
				Listcell c3=new Listcell();
				InnerButton xg=new InnerButton();
				xg.setLabel("修改");
				c3.appendChild(xg);
				xg.addEventListener(Events.ON_CLICK, new EventListener(){
					public void onEvent(Event arg0) throws Exception {
						final EdityjfxWindow w=(EdityjfxWindow)Executions.createComponents("/admin/personal/data/glyjfx/edit.zul",null,null);
						w.setXyUserRole(getXyUserRole());
						w.setYjfx(yjfx);
						w.setTitle("修改研究方向");
						w.doHighlighted();
						w.initWindow();
						w.addEventListener(Events.ON_CHANGE, new EventListener(){ 
							public void onEvent(Event arg0) throws Exception {
								 initWindow();
								 w.detach();
							} 
						});
					}});
				List listuy=useryjfxService.findByGyid(yjfx.getGyId());
				if(listuy==null ||listuy.size()==0){
					InnerButton sc=new InnerButton();
					sc.setLabel("   删除");
					c3.appendChild(sc);
					sc.addEventListener(Events.ON_CLICK, new EventListener(){
						public void onEvent(Event arg0) throws Exception {
							if(	Messagebox.show("确定删除吗?", "提示", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==1){
								yjfxService.delete(yjfx);
							}
							initWindow();
						}						
					});
				}else{
					Label sd=new Label("   已锁定");
					c3.appendChild(sd);
				}
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
			}
			
		});
	}

	@Override
	public void initWindow() {
		List<GhYjfx> listyjfx=yjfxService.findByKdid(getXyUserRole().getKdId());
		yjfxlist.setModel(new ListModelList(listyjfx));

	}
	public void onClick$add(){
		final AddyjfxWindow w=(AddyjfxWindow)Executions.createComponents("/admin/personal/data/glyjfx/add.zul",null,null);
		w.doHighlighted();
		w.setTitle("添加研究方向");
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
