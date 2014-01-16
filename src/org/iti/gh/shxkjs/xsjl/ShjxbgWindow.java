package org.iti.gh.shxkjs.xsjl;

import java.util.List;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.entity.GhJxbg;
import org.iti.gh.service.JxbgService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zhtml.Messagebox;
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
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import com.uniwin.framework.entity.WkTUser;

public class ShjxbgWindow extends BaseWindow {

	Listbox jxbglist;
	Grid AuditJxbg;
	WkTUser	 user;
	Long kdid=(Long)Executions.getCurrent().getArg().get("kdid");
	Short type=(Short)Executions.getCurrent().getArg().get("type");
	Short state=(Short)Executions.getCurrent().getArg().get("state");
	Label cgmc,huiyi,baogao,zhuti,shijian,didian;
	Textbox reason;
	Radiogroup audit;
	JxbgService jxbgService;
	GhJxbg Jxbg;
	@Override
	public void initShow() {
		jxbglist.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhJxbg bg = (GhJxbg) arg1;
				// 序号
				Listcell c1 = new Listcell(arg0.getIndex() + 1 + "");
				// 讲学或报告人姓名
				Listcell c2 = new Listcell(bg.getJxJxmc());
				// 国外大学名称或国际会议名称
				Listcell c3 = new Listcell(bg.getJxHymc());
				// 讲学或报告名称
				Listcell c4 = new Listcell();
				if (bg.getJxBgmc() != null) {
					c4.setLabel(bg.getJxBgmc());
				}
				// 报告举办时间
				Listcell c5 = new Listcell(bg.getJxSj());
				Listcell c6 = new Listcell(bg.getUser().getKuName());
				// 操作
				Listcell c7 = new Listcell();

				InnerButton gn = new InnerButton();
				gn.setLabel("审核");
				gn.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						Jxbg=bg;
						jxbglist.setVisible(false);
						AuditJxbg.setVisible(true);
						initAuditJxbg(bg);
						
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
	public void initWindow() {
		 user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		 jxbglist.setVisible(true);
		 AuditJxbg.setVisible(false);
		 reason.setValue("");
		 audit.setSelectedIndex(0);
		 if (kdid == null) {
		 	 kdid = getXyUserRole().getKdId();
		 }	
		 List jxlist=jxbgService.findByKdidAndCjAndState(kdid, type, state);
		 if(jxlist!=null&&jxlist.size()>0){
			 jxbglist.setModel(new ListModelList(jxlist));
		 }else{
			 jxbglist.setModel(new ListModelList());
		 }
		 
	}
	
	public void initAuditJxbg(GhJxbg jxbg){
		cgmc.setValue(jxbg.getJxJxmc());
		huiyi.setValue(jxbg.getJxHymc());
		baogao.setValue(jxbg.getJxBgmc());
		zhuti.setValue(jxbg.getJxSubject());
		shijian.setValue(jxbg.getJxSj());
		didian.setValue(jxbg.getJxPlace());
	}
	
	public void onClick$submit(){
		Jxbg.setAuditState(Short.parseShort(audit.getSelectedIndex()+""));
		Jxbg.setAuditUid(user.getKuId());
		Jxbg.setReason(reason.getValue());
		jxbgService.update(Jxbg);
		 try {
				Messagebox.show("审核成功！","提示",Messagebox.OK,Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			initWindow();
	   }
	public void onClick$close(){
		   initWindow();
	  }
	 public void onClick$back(){
		   	this.detach();
		   	Events.postEvent(Events.ON_CHANGE, this, null);
		   }
}
