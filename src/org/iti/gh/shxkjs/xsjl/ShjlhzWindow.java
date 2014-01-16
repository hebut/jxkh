package org.iti.gh.shxkjs.xsjl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.entity.GhJlhz;
import org.iti.gh.service.JlhzService;
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

import com.uniwin.asm.personal.ui.data.teacherinfo.CdgjhzWindow;
import com.uniwin.asm.personal.ui.data.teacherinfo.CkshyjWindow;
import com.uniwin.framework.entity.WkTUser;

public class ShjlhzWindow extends BaseWindow {

	Listbox jlhzlist;
	Grid AuditJlhz;
	Label cgmc,shijian,duixiang,remarks;
	WkTUser	 user;
	Long kdid=(Long)Executions.getCurrent().getArg().get("kdid");
	Short type=(Short)Executions.getCurrent().getArg().get("type");
	Short state=(Short)Executions.getCurrent().getArg().get("state");
	Textbox reason;
	Radiogroup audit;
	GhJlhz Jlhz;
	JlhzService jlhzService;
	@Override
	public void initShow() {
		jlhzlist.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhJlhz jl = (GhJlhz) arg1;
				// ���
				Listcell c1 = new Listcell(arg0.getIndex() + 1 + "");
				// ���ʽ���������Ŀ����
				Listcell c2 = new Listcell(jl.getHzMc());
				// ��Ŀ��ֹʱ��
				Listcell c3 = new Listcell();
				String sj="";
				if(jl.getHzKssj()!=null){
					sj=sj+jl.getHzKssj();
				}
				if(jl.getHzJssj()!=null){
					sj=sj+"~"+jl.getHzJssj();
				}
				c3.setLabel(sj);	
				// ��������
				Listcell c4 = new Listcell();
				if (jl.getHzDx() != null) {
					c4.setLabel(jl.getHzDx());
				}
				// ����
				Listcell c5 = new Listcell(jl.getUser().getKuName());
				Listcell c6 = new Listcell();
				InnerButton gn = new InnerButton();
				gn.setLabel("���");
				gn.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						Jlhz=jl;
						jlhzlist.setVisible(false);
						AuditJlhz.setVisible(true);
						initAuditJlhz(jl);
					}
				});

				c6.appendChild(gn);
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
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		jlhzlist.setVisible(true);
		AuditJlhz.setVisible(false);
		reason.setValue("");
		audit.setSelectedIndex(0);
		if (kdid == null) {
			kdid = getXyUserRole().getKdId();
		}
		List jlist = jlhzService.findByKdidAndCjAndState(kdid, type, state);
		if(jlist!=null&&jlist.size()>0){
			jlhzlist.setModel(new ListModelList(jlist));
		}else{
			jlhzlist.setModel(new ListModelList());
		}
	
	}
	/**
	 * ��ʼ�����ҳ��
	 * @param jlhz
	 */
	public void initAuditJlhz(GhJlhz jlhz){
		cgmc.setValue(jlhz.getHzMc());
		shijian.setValue(jlhz.getHzSj());
		duixiang.setValue(jlhz.getHzDx());
		remarks.setValue(jlhz.getHzRemark());
	}
	public void onClick$submit(){
		Jlhz.setAuditState(Short.parseShort(audit.getSelectedIndex()+""));
		Jlhz.setAuditUid(user.getKuId());
		Jlhz.setReason(reason.getValue());
		jlhzService.update(Jlhz);
		try {
			Messagebox.show("��˳ɹ���","��ʾ",Messagebox.OK,Messagebox.INFORMATION);
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
