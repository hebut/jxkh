package com.uniwin.asm.personal.ui.data.teacherinfo.qtqk;

import java.util.List;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.entity.GhShjz;
import org.iti.gh.service.QtService;
import org.iti.gh.service.ShjzService;
import org.iti.xypt.entity.Teacher;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.uniwin.asm.personal.ui.data.teacherinfo.XsttWindow;
import com.uniwin.framework.entity.WkTUser;

public class ShjzWindow extends BaseWindow {

	//学术团体
	Button button1;
	Listbox listbox1;
	QtService qtService;
	WkTUser user;
	Teacher teacher;
	long kuid;
	ShjzService shjzService;
	@Override
	public void initShow() {
		initWindow();
		listbox1.setItemRenderer(new ListitemRenderer(){
			public void render(Listitem arg0, Object arg1) throws Exception {
			final GhShjz jz = (GhShjz) arg1;
				//序号
				Listcell c1 = new Listcell(arg0.getIndex()+1+"");
				//团体名称
				Listcell c2 = new Listcell();
				InnerButton ib = new InnerButton();
				ib.setLabel(jz.getJzName());
				ib.addEventListener(Events.ON_CLICK, new EventListener(){
					public void onEvent(Event arg0) throws Exception {

						final XsttWindow w = (XsttWindow) Executions.createComponents
						("/admin/personal/data/teacherinfo/qtqk/shjz/xstt.zul", null, null);
						w.setKuid(user.getKuId());
						w.setShjz(jz);
						w.doHighlighted();
						w.initWindow();
						w.addEventListener(Events.ON_CHANGE, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								initWindow();
								
							}
						});
					}
				});
				c2.appendChild(ib);

				//担任职务
				Listcell c3 = new Listcell();
				c3.setLabel(jz.getJzPosition());
				//任职时间
				Listcell c4 = new Listcell("");
				c4.setLabel(jz.getJzTime());
				//功能
				Listcell c5 =  new Listcell();
				InnerButton gn = new InnerButton();

				gn.setLabel("删除");
				gn.addEventListener(Events.ON_CLICK, new EventListener(){

					public void onEvent(Event arg0) throws Exception {
						if(Messagebox.show("确定删除吗?", "提示", Messagebox.OK|Messagebox.CANCEL,
								Messagebox.QUESTION)==1){
							shjzService.delete(jz);
							initWindow();
						}
					}
				});
				c5.appendChild(gn);
				arg0.appendChild(c1); arg0.appendChild(c2); arg0.appendChild(c3);
				arg0.appendChild(c4); arg0.appendChild(c5);
			}
		});

	}

	@Override
	public void initWindow() {
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		kuid=user.getKuId();
		List jzlist =shjzService.FindByKuid(kuid);
		listbox1.setModel(new ListModelList(jzlist));

	}
	public void onClick$button1(){

		final XsttWindow cgWin = (XsttWindow) Executions.createComponents
		("/admin/personal/data/teacherinfo/qtqk/shjz/xstt.zul", null, null);
		cgWin.setKuid(user.getKuId());
		cgWin.doHighlighted();
		cgWin.initWindow();
		cgWin.addEventListener(Events.ON_CHANGE, new EventListener(){

			public void onEvent(Event arg0)
			throws Exception {
				initWindow();
				cgWin.detach();
			}
		});
	}

}
