package com.uniwin.asm.personal.ui.data.teacherinfo;


import java.util.ArrayList;
import java.util.List;

import org.iti.gh.entity.GhHylw;
import org.iti.gh.entity.GhJsxm;
import org.iti.gh.entity.GhQklw;
import org.iti.gh.entity.GhXm;
import org.iti.gh.entity.GhXmzz;
import org.iti.gh.entity.GhZz;
import org.iti.gh.service.XmService;
import org.iti.gh.service.XmzzService;
import org.iti.xypt.ui.base.FrameworkWindow;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.uniwin.framework.entity.WkTUser;


public class JyxmzzlistWindow extends FrameworkWindow{
	Long kuid;
	Listbox listbox1,listbox2;
	XmService xmService;
	XmzzService xmzzService;
	GhHylw lw;
	GhQklw qklw;
	GhZz jc;
	GhZz zz;
    WkTUser user;
	GhXm xm;
	GhXmzz xmzz;
	Button add,delete,submit;
	@Override
	public void initShow() {
		listbox1.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhXm xm = (GhXm) arg1;
				arg0.setValue(arg1);
				Listcell c0 = new Listcell();
				//编号
				Listcell c1 = new Listcell(xm.getKyNumber());
				c1.setValue(xm.getKyId());
				//项目名称
				Listcell c2 = new Listcell(xm.getKyMc());
				
				arg0.appendChild(c0);arg0.appendChild(c1); arg0.appendChild(c2); 
				}
			});
		listbox2.setItemRenderer(new ListitemRenderer(){
			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhXmzz xmzz = (GhXmzz) arg1;
				arg0.setValue(arg1);
				if(xmzz != null ){
					GhXm xm = (GhXm) xmService.get(GhXm.class, xmzz.getKyId());
					Listcell c0 = new Listcell();
					//项目编号
					Listcell c1 = new Listcell(xm.getKyNumber());
					c1.setValue(xm.getKyId());
					//名称
					Listcell c2 = new Listcell(xm.getKyMc());
					arg0.appendChild(c0);arg0.appendChild(c1); arg0.appendChild(c2);
					}
				}
			});
		
	}

	@Override
	public void initWindow() {
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		List xm=new ArrayList();
		List xmzz=new ArrayList();
		if(lw!=null){
			xm = xmService.findByKuidAndLwidNotZz(kuid,GhXm.JYXM,GhXmzz.HYLW,GhJsxm.JYXM,lw.getLwId());
			listbox1.setModel(new ListModelList(xm));
			xmzz= xmzzService.findByLwidAndKuid(lw.getLwId(),GhXmzz.HYLW);
			listbox2.setModel(new ListModelList(xmzz));
		}else if(qklw!=null){
			xm = xmService.findByKuidAndLwidNotZz(kuid,GhXm.JYXM,GhXmzz.QKLW,GhJsxm.JYXM,qklw.getLwId());
			listbox1.setModel(new ListModelList(xm));
			xmzz= xmzzService.findByLwidAndKuid(qklw.getLwId(),GhXmzz.QKLW);
			listbox2.setModel(new ListModelList(xmzz));
		}else if(zz!=null){
			xm = xmService.findByKuidAndLwidNotZz(kuid,GhXm.JYXM,GhXmzz.KYZZ,GhJsxm.JYXM,zz.getZzId());
			listbox1.setModel(new ListModelList(xm));
			xmzz= xmzzService.findByLwidAndKuid(zz.getZzId(),GhXmzz.KYZZ);
			listbox2.setModel(new ListModelList(xmzz));
		}else if(jc!=null){
			xm = xmService.findByKuidAndLwidNotZz(kuid,GhXm.JYXM,GhXmzz.JYJC,GhJsxm.JYXM,jc.getZzId());
			listbox1.setModel(new ListModelList(xm));
			xmzz= xmzzService.findByLwidAndKuid(jc.getZzId(),GhXmzz.JYJC);
			listbox2.setModel(new ListModelList(xmzz));
		}
		if(user.getKuId().intValue()!=kuid.intValue()){
			add.setDisabled(true);
			delete.setDisabled(true);
			submit.setDisabled(true);
		}
	}

	public void onClick$submit() throws InterruptedException {
		Long kyid = null;
		if(lw!=null){
		List xmzzlist = xmzzService.findByLwidAndKuid(lw.getLwId(),GhXmzz.HYLW);
		if(xmzzlist != null && xmzzlist.size() != 0){
			for(int j = 0;j < xmzzlist.size();j ++ ){
				GhXmzz zz = (GhXmzz) xmzzlist.get(j);
				xmzzService.delete(zz);
				}
		}
		for (int i = 0; i < listbox2.getItemCount(); i++) {
			kyid = Long.parseLong(((Listcell) listbox2.getItemAtIndex(i).getChildren().get(1)).getValue().toString());
			GhXm xm = (GhXm)xmService.get(GhXm.class, kyid);
				GhXmzz xmzz = new GhXmzz();
				xmzz.setKyId(xm.getKyId());
				xmzz.setLwId(lw.getLwId());
				xmzz.setLwType(GhXmzz.HYLW);
				xmzz.setKuId(kuid);
				xmzzService.save(xmzz);
		}
		Messagebox.show("保存成功！");
		Events.postEvent(Events.ON_CHANGE, this, null);
		}else if(qklw!=null){
			List xmzzlist = xmzzService.findByLwidAndKuid(qklw.getLwId(),GhXmzz.QKLW);
			if(xmzzlist != null && xmzzlist.size() != 0){
				for(int j = 0;j < xmzzlist.size();j ++ ){
					GhXmzz zz = (GhXmzz) xmzzlist.get(j);
					xmzzService.delete(zz);
					}
			}
			for (int i = 0; i < listbox2.getItemCount(); i++) {
				kyid = Long.parseLong(((Listcell) listbox2.getItemAtIndex(i).getChildren().get(1)).getValue().toString());
				GhXm xm = (GhXm)xmService.get(GhXm.class, kyid);
					GhXmzz xmzz = new GhXmzz();
					xmzz.setKyId(xm.getKyId());
					xmzz.setLwId(qklw.getLwId());
					xmzz.setLwType(GhXmzz.QKLW);
					xmzz.setKuId(kuid);
					xmzzService.save(xmzz);
			}
			Messagebox.show("保存成功！");
			Events.postEvent(Events.ON_CHANGE, this, null);
		}else if(zz!=null){
			List xmzzlist = xmzzService.findByLwidAndKuid(zz.getZzId(),GhXmzz.KYZZ);
			if(xmzzlist != null && xmzzlist.size() != 0){
				for(int j = 0;j < xmzzlist.size();j ++ ){
					GhXmzz zz = (GhXmzz) xmzzlist.get(j);
					xmzzService.delete(zz);
					}
			}
			for (int i = 0; i < listbox2.getItemCount(); i++) {
				kyid = Long.parseLong(((Listcell) listbox2.getItemAtIndex(i).getChildren().get(1)).getValue().toString());
				GhXm xm = (GhXm)xmService.get(GhXm.class, kyid);
					GhXmzz xmzz = new GhXmzz();
					xmzz.setKyId(xm.getKyId());
					xmzz.setLwId(zz.getZzId());
					xmzz.setLwType(GhXmzz.KYZZ);
					xmzz.setKuId(kuid);
					xmzzService.save(xmzz);
			}
			Messagebox.show("保存成功！");
			Events.postEvent(Events.ON_CHANGE, this, null);
		}else if(jc!=null){
			List xmzzlist = xmzzService.findByLwidAndKuid(jc.getZzId(),GhXmzz.JYJC);
			if(xmzzlist != null && xmzzlist.size() != 0){
				for(int j = 0;j < xmzzlist.size();j ++ ){
					GhXmzz zz = (GhXmzz) xmzzlist.get(j);
					xmzzService.delete(zz);
					}
			}
			for (int i = 0; i < listbox2.getItemCount(); i++) {
				kyid = Long.parseLong(((Listcell) listbox2.getItemAtIndex(i).getChildren().get(1)).getValue().toString());
				GhXm xm = (GhXm)xmService.get(GhXm.class, kyid);
					GhXmzz xmzz = new GhXmzz();
					xmzz.setKyId(xm.getKyId());
					xmzz.setLwId(jc.getZzId());
					xmzz.setLwType(GhXmzz.JYJC);
					xmzz.setKuId(kuid);
					xmzzService.save(xmzz);
			}
			Messagebox.show("保存成功！");
			Events.postEvent(Events.ON_CHANGE, this, null);
		}
	}


	public void onClick$add() {
		while (listbox1.getSelectedItem() != null) {
			Listitem item = listbox1.getSelectedItem();
			if (listbox2.getItemCount() != 0)
				listbox2.insertBefore(item, listbox2.getItemAtIndex(0));
			else
				item.setParent(listbox2);
			item.setSelected(false);
		}
	}

	public void onClick$delete() throws InterruptedException {
		while (listbox2.getSelectedItem() != null) {
			Listitem item = listbox2.getSelectedItem();
			if (listbox1.getItemCount() != 0)
				listbox1.insertBefore(item, listbox1.getItemAtIndex(0));
			else
				item.setParent(listbox1);
			item.setSelected(false);
		}
	}


	public void onClick$close(){
		this.detach();
	}


	public Long getKuid() {
		return kuid;
	}

	public void setKuid(Long kuid) {
		this.kuid = kuid;
	}
	public GhHylw getLw() {
		return lw;
	}

	public void setLw(GhHylw lw) {
		this.lw = lw;
	}

	public GhQklw getQklw() {
		return qklw;
	}

	public void setQklw(GhQklw qklw) {
		this.qklw = qklw;
	}

	public GhZz getJc() {
		return jc;
	}

	public void setJc(GhZz jc) {
		this.jc = jc;
	}

	public GhZz getZz() {
		return zz;
	}

	public void setZz(GhZz zz) {
		this.zz = zz;
	}


}
