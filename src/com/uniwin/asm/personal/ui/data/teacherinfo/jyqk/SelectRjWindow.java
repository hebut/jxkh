package com.uniwin.asm.personal.ui.data.teacherinfo.jyqk;

import java.util.List;

import org.iti.gh.entity.GhCg;
import org.iti.gh.entity.GhRjzz;
import org.iti.gh.entity.GhXmzz;
import org.iti.gh.service.RjzzService;
import org.iti.gh.service.XmzzService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Textbox;

import com.uniwin.framework.entity.WkTUser;

public class SelectRjWindow extends BaseWindow {

	Textbox rjmc,djh;
	Listbox rjlist;
	WkTUser user=(WkTUser)Executions.getCurrent().getArg().get("user");
//	Short lx=(Short)Executions.getCurrent().getArg().get("lx");
//	Integer type=(Integer)Executions.getCurrent().getArg().get("type");
	Button submit;
	XmzzService xmzzService;
	RjzzService rjzzService;
	Panel rjpanel,exception;
	@Override
	public void initShow() {
		initWindow();
		rjlist.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				GhRjzz cg=(GhRjzz)arg1;
				arg0.setValue(cg);
				Listcell c0=new Listcell(arg0.getIndex()+1+"");
				Listcell c1=new Listcell(cg.getRjName());
				Listcell c2=new Listcell(cg.getRjFirtime());
				Listcell c3=new Listcell(cg.getRjRegisno());
				Listcell c4=new Listcell(cg.getRjSoftno());
//				Listcell c5=new Listcell(cg.getRjPerson());
//				List zzlist= xmzzService.findByLwidAndKuid(cg.getRjId(), GhXmzz.RJZZ);
				Listcell c6=new Listcell(cg.getUser().getKuName());
				arg0.appendChild(c0);arg0.appendChild(c1);arg0.appendChild(c2);
				arg0.appendChild(c3);arg0.appendChild(c4);
//				arg0.appendChild(c5);
				arg0.appendChild(c6); 
			}
			
		});
		submit.addForward(Events.ON_CLICK, this, Events.ON_CHANGE);
	}

	@Override
	public void initWindow() {
		List list=rjzzService.findByKuidAndUname(user.getKuId(), user.getKuName());
		if(list!=null&&list.size()>0){
			rjlist.setModel(new ListModelList(list));
		}else{
			rjlist.setModel(new ListModelList());
		}

	}
	public void onClick$query(){
		if("".equals(rjmc.getValue().trim())&&"".equals(djh.getValue().trim())){
			try {
				Messagebox.show("至少填写一个！", "警告", Messagebox.OK, Messagebox.EXCLAMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		List list =rjzzService.findByRjnameAndDjh(rjmc.getValue().trim(),djh.getValue().trim());
		if(list!=null&&list.size()>0){
			rjlist.setModel(new ListModelList(list));
			rjpanel.setVisible(true);
			exception.setVisible(false);
		}else{
			rjpanel.setVisible(false);
			exception.setVisible(true);
			rjlist.setModel(new ListModelList());
		}
	}

	public Listbox getRjlist() {
		return rjlist;
	}

	public void setRjlist(Listbox rjlist) {
		this.rjlist = rjlist;
	}
	public void onClick$close(){
		this.detach();
	}
	
}
