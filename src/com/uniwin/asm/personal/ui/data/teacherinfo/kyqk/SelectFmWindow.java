package com.uniwin.asm.personal.ui.data.teacherinfo.kyqk;

import java.util.List;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.entity.GhFmzl;
import org.iti.gh.service.FmzlService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Textbox;

import com.uniwin.framework.entity.WkTUser;

public class SelectFmWindow extends BaseWindow {
	WkTUser user=(WkTUser)Executions.getCurrent().getArg().get("user");
	Listbox xmlist;
	Textbox fmmc, fmr;
	Button submit;
	FmzlService fmzlService;
    Panel pl1,pl2;
    Label ll;
	@Override
	public void initShow() {
		submit.addForward(Events.ON_CLICK, this, Events.ON_CHANGE);
		xmlist.setItemRenderer(new ListitemRenderer() {

			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhFmzl fm = (GhFmzl) arg1;
				arg0.setValue(fm);
				// 序号
				Listcell c1 = new Listcell(arg0.getIndex() + 1 + "");

				// 发明专利名称
				Listcell c2 = new Listcell(fm.getFmMc());
				//授权时间
				Listcell c3 = new Listcell(fm.getFmSj());

				//专利授权号
				Listcell c4 = new Listcell(fm.getFmSqh());
				//Listcell c5 = new Listcell(fm.getFmInventor());
				Listcell c5 = new Listcell(fm.getUser().getKuName());
				arg0.appendChild(c1); arg0.appendChild(c2); arg0.appendChild(c3);
				arg0.appendChild(c4); arg0.appendChild(c5); 
			}
		});

	}

	@Override
	public void initWindow() {
		List list=fmzlService.findByKuidAndUname(user.getKuId(), user.getKuName());
		if (list != null && list.size() > 0) {
			xmlist.setModel(new ListModelList(list));
		} else {
			xmlist.setModel(new ListModelList());
		}

	}
	 public void onClick$query(){
	    	if("".equals(fmmc.getValue())&&"".equals(fmr.getValue())){
	    		try {
					Messagebox.show("请填写检索条件，至少填写一个！","警告",Messagebox.OK,Messagebox.EXCLAMATION);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
	    	}
	    	List list=fmzlService.findByFmmcAndFmrname(fmmc.getValue().trim(),fmr.getValue().trim(),user.getKuId());
			if (list != null && list.size() > 0) {
				pl1.setVisible(true);pl2.setVisible(false);
				xmlist.setModel(new ListModelList(list));
			} else {
				pl1.setVisible(false);pl2.setVisible(true);
				ll.setValue("没有符合条件的发明专利,请尝试选择其他条件......");
				xmlist.setModel(new ListModelList());
			}
	 }
	 
	 public void onClick$close() {
			this.detach();
		}

	public Listbox getXmlist() {
		return xmlist;
	}

	public void setXmlist(Listbox xmlist) {
		this.xmlist = xmlist;
	}
	 
}
