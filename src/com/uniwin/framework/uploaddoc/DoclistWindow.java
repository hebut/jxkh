package com.uniwin.framework.uploaddoc;

import java.util.Date;
import java.util.List;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;

import com.uniwin.common.util.DateUtil;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTDoc;
import com.uniwin.framework.entity.WkTTitle;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.service.DocService;
import com.uniwin.framework.service.UserService;

public class DoclistWindow extends BaseWindow {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    Listbox wlist;
    WkTUser user;
    DocService docService;
    UserService userService;
    DepartmentService departmentService;
    WkTTitle t;
    Button add;
	@Override
	public void initShow() {
		if(t==null){
			t =(WkTTitle) Executions.getCurrent().getAttribute("wktTitle");
			Executions.getCurrent().removeAttribute("wktTitle");
		}
		initWindow();
		wlist.setItemRenderer(new ListitemRenderer(){
			public void render(Listitem arg0, Object arg1) throws Exception {
				final WkTDoc wdc=(WkTDoc)arg1;
				arg0.setValue(wdc);
				Listcell c1=new Listcell(arg0.getIndex()+1+"");
				Listcell c2=new Listcell(wdc.getDocInfo());
				c2.setStyle("color:blue");
				Listcell c3=new Listcell(wdc.getWktdoctype().getDoctName());
				Listcell c4=new Listcell();
				if(wdc.getDocLevel()==WkTDoc.ULevel) c4.setLabel("校级");
				if(wdc.getDocLevel()==WkTDoc.DLevel) c4.setLabel("院级");
				if(wdc.getDocLevel()==WkTDoc.CLevel) c4.setLabel("系级");
				WkTDept wdt=(WkTDept)departmentService.get(WkTDept.class, wdc.getDocKdid());
				Listcell c5=new Listcell(wdt.getKdName());
				Listcell c6=new Listcell(wdc.getDocKuname());
				Listcell c7=new Listcell();
				Listcell c8=new Listcell(DateUtil.getDate(new Date(wdc.getDoctime())));
				InnerButton ib2=new InnerButton();
				ib2.setLabel("删除");
				ib2.addEventListener(Events.ON_CLICK, new EventListener(){
					public void onEvent(Event arg0) throws Exception {
						if(	Messagebox.show("确定删除吗?", "提示", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==1){
							deleteFile(wdc.getDocPath());
							docService.delete(wdc);
						}
						initWindow();
					}						
				});
				Label label=new Label();
				label.setValue("/");
				InnerButton im=new InnerButton();
				im.setLabel("下载");
				im.addEventListener(Events.ON_CLICK, new EventListener(){
					public void onEvent(Event arg0) throws Exception {
						downloadFile(wdc.getDocPath(),wdc.getDocName());
					}						 
				 });
				c7.appendChild(ib2);
				c7.appendChild(label);
				c7.appendChild(im);
				arg0.appendChild(c1);arg0.appendChild(c2);arg0.appendChild(c3);
				arg0.appendChild(c4);arg0.appendChild(c5);arg0.appendChild(c6);
				arg0.appendChild(c8);
				arg0.appendChild(c7);
			}
		});
	}
	@Override
	public void initWindow() {
		user=(WkTUser)Sessions.getCurrent().getAttribute("user");
		List list=docService.findbykuid(user.getKuId()); 
    	wlist.setModel(new ListModelList(list));
	}
	public void onClick$add(){
		Executions.getCurrent().setAttribute("wktTitle", t);
		 NewDocWindow nw=(NewDocWindow)Executions.createComponents("/admin/uploadtempdoc/upload/add/index.zul",null,null);
		 nw.addEventListener(Events.ON_CHANGE, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					initWindow();
				}
			});
		 nw.doHighlighted();
	  }
}
