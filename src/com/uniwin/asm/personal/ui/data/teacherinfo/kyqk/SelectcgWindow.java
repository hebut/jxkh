package com.uniwin.asm.personal.ui.data.teacherinfo.kyqk;

import java.util.List;

import org.iti.gh.entity.GhCg;
import org.iti.gh.service.CgService;
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

public class SelectcgWindow extends BaseWindow {

	WkTUser user=(WkTUser)Executions.getCurrent().getArg().get("user");
	Short lx=(Short)Executions.getCurrent().getArg().get("lx");
	Integer type=(Integer)Executions.getCurrent().getArg().get("type");
	Listbox cglist;
	CgService cgService;
	Textbox xmmc,ly;
	Button submit;
	Panel exception,cgpanel;
	@Override
	public void initShow() {
		initWindow();
		cglist.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				GhCg cg=(GhCg)arg1;
				arg0.setValue(cg);
				Listcell c0=new Listcell(arg0.getIndex()+1+"");
				Listcell c1=new Listcell(cg.getKyMc());
//				Listcell c2=new Listcell(cg.getKyLy());
				Listcell c3=new Listcell(cg.getKyPrizeper());
				Listcell c4=new Listcell(cg.getKySj());
				Listcell c5=new Listcell(cg.getKyDj());
				Listcell c6=new Listcell(cg.getUser().getKuName());
				arg0.appendChild(c0);arg0.appendChild(c1);
//				arg0.appendChild(c2);
				arg0.appendChild(c3);arg0.appendChild(c4);arg0.appendChild(c5);
				arg0.appendChild(c6); 
			}
			
		});
		submit.addForward(Events.ON_CLICK, this, Events.ON_CHANGE);
	}

	@Override
	public void initWindow() {
	    List clist=cgService.findByKuidAndKunameAndLxAndType(user.getKuId(), user.getKuName(), lx, type);
		if(clist!=null&&clist.size()>0){
			 cglist.setModel(new ListModelList(clist));
		}else{
			cglist.setModel(new ListModelList());
		}
	    
	}
	public void onClick$query(){
		if(xmmc.getValue().equals("")&&ly.getValue().equals("")){
			try {
				Messagebox.show("至少填写一个！", "警告", Messagebox.OK, Messagebox.EXCLAMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		 List clist=cgService.findByNameAndLxAndDj(xmmc.getValue().trim(), lx, ly.getValue().trim(),user.getKuId(),type);
			if(clist!=null&&clist.size()>0){
				 cglist.setModel(new ListModelList(clist));
				 exception.setVisible(false);
				 cgpanel.setVisible(true);
			}else{
				 exception.setVisible(true);
				 cgpanel.setVisible(false);
				cglist.setModel(new ListModelList());
			}
	}
	public Listbox getCglist() {
		return cglist;
	}

	public void setCglist(Listbox cglist) {
		this.cglist = cglist;
	}
	public void onClick$close(){
		this.detach();
	}
}
