package com.uniwin.asm.personal.ui.data.teacherinfo.jyqk;

import java.util.List;

import org.iti.gh.entity.GhZz;
import org.iti.gh.service.ZzService;
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

public class SelectJcWindow extends BaseWindow {

	WkTUser user=(WkTUser)Executions.getCurrent().getArg().get("user");
	Short lx=(Short)Executions.getCurrent().getArg().get("lx");
	Short type=(Short)Executions.getCurrent().getArg().get("type");
	Listbox jclist;
	Textbox jcmc,zbj;
	ZzService zzService;
	Button submit;
	Panel jcpanel,exception;
	@Override
	public void initShow() {
		initWindow();
		jclist.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				GhZz zz=(GhZz)arg1;
				arg0.setValue(zz);
				Listcell c1=new Listcell(arg0.getIndex()+1+"");
				Listcell c2=new Listcell(zz.getZzMc());
				Listcell c3=new Listcell(zz.getZzKw());
				Listcell c4=new Listcell(zz.getZzPublitime());
				Listcell c5=new Listcell(zz.getZzZb());
				Listcell c6=new Listcell(zz.getUser().getKuName());
				arg0.appendChild(c1);arg0.appendChild(c2);arg0.appendChild(c3);
				arg0.appendChild(c4);arg0.appendChild(c5);arg0.appendChild(c6);
				
			}
			
		});
		submit.addForward(Events.ON_CLICK, this, Events.ON_CHANGE);

	}

	@Override
	public void initWindow() {
		List clist=zzService.findAllname(user.getKuId(), user.getKuName(), lx, type);
		if(clist!=null&&clist.size()>0){
			jclist.setModel(new ListModelList(clist));
		}else{
			jclist.setModel(new ListModelList());
		}
		
	}
	
	public void onClick$query(){
		if( "".equals(jcmc.getValue().trim())&&"".equals(zbj.getValue().trim())){
			try {
				Messagebox.show("请填写检索条件，至少填写一个！","警告",Messagebox.OK,Messagebox.EXCLAMATION);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		List jlist=zzService.findByKuidAndJcmcAndZbAndLxAndType(user.getKuId(), jcmc.getValue().trim(), zbj.getValue().trim(), lx, type);
		if(jlist!=null&&jlist.size()>0){
			jclist.setModel(new ListModelList(jlist));
			jcpanel.setVisible(true);
			exception.setVisible(false);
		}else{
			jclist.setModel(new ListModelList());
			jcpanel.setVisible(false);
			exception.setVisible(true);
		}
	}
	public void onClick$close() {
		this.detach();
	}

	public Listbox getJclist() {
		return jclist;
	}

	public void setJclist(Listbox jclist) {
		this.jclist = jclist;
	}

}
