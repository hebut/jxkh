package org.iti.gh.shxkjs.xsjl;
 

import java.util.List;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.entity.GhXshy;
import org.iti.gh.service.XshyService;
import org.iti.xypt.ui.base.BaseWindow;
 
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;

import com.uniwin.framework.entity.WkTUser;
 

public class ShxsjlWindow extends BaseWindow {

	Grid AuditXshy;
	Listbox gjxshylist;
	Label cgmc,cgzt,shijian,cgdd,zrs,jwrs;
	WkTUser	 user;
	Long kdid=(Long)Executions.getCurrent().getArg().get("kdid");
	Short type=(Short)Executions.getCurrent().getArg().get("type");
	Short state=(Short)Executions.getCurrent().getArg().get("state");
	Textbox reason;
	Radiogroup audit;
	GhXshy Xshy;
	XshyService xshyService;
	@Override
	public void initShow() {
		gjxshylist.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhXshy xs = (GhXshy) arg1;
				// 序号
				Listcell c1 = new Listcell(arg0.getIndex() + 1 + "");
				// 会议名称
				Listcell c2 = new Listcell(xs.getHyMc());
				// 举办会议时间

				Listcell c3 = new Listcell(xs.getHySj());

				// 参与总人数
				Listcell c4 = new Listcell();
				if (xs.getHyZrs() != null) {
					c4.setLabel(xs.getHyZrs() + "");
				}

				// 境外人员数
				Listcell c5 = new Listcell();
				if (xs.getHyJwrs() != null) {
					c5.setLabel(xs.getHyJwrs() + "");
				} else {
					c5.setLabel("0");
				}
                Listcell c6=new Listcell(xs.getUser().getKuName());
                
				// 功能
				Listcell c7 = new Listcell();

				InnerButton gn = new InnerButton();
				gn.setLabel("审核");
				gn.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						Xshy=xs;
						AuditXshy.setVisible(true);
						gjxshylist.setVisible(false);
						initGrid(xs);
					}
				});
				c7.appendChild(gn);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
				arg0.appendChild(c5);
				arg0.appendChild(c6);
				arg0.appendChild(c7);

			}
		});
	}

	@Override
	public void initWindow() {}
	
	public void initGrid(GhXshy xshy){
		cgmc.setValue(xshy.getHyMc());
		cgzt.setValue(xshy.getHyTheme());
		shijian.setValue(xshy.getHySj());
		cgdd.setValue(xshy.getHyPlace());
		zrs.setValue(xshy.getHyZrs()+"");
		jwrs.setValue(xshy.getHyJwrs()+"");
	}
   public void onClick$submit(){}
   public void onClick$close(){
	   initWindow();
   }
   public void onClick$back(){
	   	this.detach();
	   	Events.postEvent(Events.ON_CHANGE, this, null);
	   }
}
