package org.iti.gh.glyjfx.fxfzr.hygl;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.iti.bysj.entity.BsStudent;
import org.iti.gh.entity.GhYjfxhy;
import org.iti.gh.service.YjfxhyService;
import org.iti.xypt.entity.Student;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

public class CkhyWindow extends Window implements AfterCompose {
Listbox hydetailList;
UserService userService;
YjfxhyService yjfxhyService;
Long gyid;
public void setGyid(Long gyid) {
	this.gyid = gyid;
}
public void setType(Short type) {
	this.type = type;
}
public void setSf(Short sf) {
	this.sf = sf;
}
public void setYear(Integer year) {
	this.year = year;
}
Short type,sf;
Integer year;
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		initShow();
	}
	public void initShow() {
		hydetailList.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhYjfxhy hy = (GhYjfxhy) arg1;
				final Listitem it = arg0;
				arg0.setValue(arg1);
				Listcell c0=new Listcell(arg0.getIndex()+1+"");
				Listcell c1=new Listcell(GhYjfxhy.TYPE[hy.getGhType().intValue()]);
				Listcell c2=new Listcell(GhYjfxhy.SF[hy.getGhSf().intValue()]);
				Listcell c3=new Listcell(hy.getGhYear()+"");
				WkTUser hyuser=(WkTUser)userService.get(WkTUser.class, hy.getKuId());
				Listcell c4=new Listcell(hyuser.getKuName());
				Listcell c5=new Listcell(hy.getGhContent());
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
				arg0.appendChild(c5);
				}});	
	}
	public void initWindow(){
		List detaillist=(List)yjfxhyService.findByGyidAndTypeAndSfAndYear(gyid, type, sf, year);
		hydetailList.setModel(new ListModelList(detaillist));	
	}
	public void onClick$close(){
		this.detach();
	}
	public void onClick$delete() throws InterruptedException{
		Set suser = hydetailList.getSelectedItems();
		Iterator it = suser.iterator();
		if (Messagebox.show("确定删除吗?", "提示", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == 1) 
		{
		while (it.hasNext()) {
			Listitem sitem = (Listitem) it.next();
			GhYjfxhy dlhy = (GhYjfxhy) sitem.getValue();
			yjfxhyService.delete(dlhy);
		}
		initWindow();
		Events.postEvent(Events.ON_CHANGE, this, null);
		}
		
	}
}
