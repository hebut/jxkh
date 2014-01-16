package com.uniwin.asm.personal.ui.data.teacherinfo.kyqk;

import java.util.List;

import org.iti.gh.entity.GhXm;
import org.iti.gh.service.XmService;
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

public class SelectXmWindow extends BaseWindow {

	WkTUser user=(WkTUser)Executions.getCurrent().getArg().get("user");
	Short lx=(Short)Executions.getCurrent().getArg().get("lx");
	Integer type=(Integer)Executions.getCurrent().getArg().get("type");
	Listbox xmlist;
	Textbox xmmc,ly,fzr;
	XmService xmService ;
	Button submit;
	Panel xmpanel,exception;
	@Override
	public void initShow() {
		initWindow();
		submit.addForward(Events.ON_CLICK, this, Events.ON_CHANGE);
		xmlist.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				GhXm xm=(GhXm)arg1;
				arg0.setValue(xm);
				Listcell c0=new Listcell(arg0.getIndex()+1+"");
				Listcell c1=new Listcell(xm.getKyMc());
				Listcell c2=new Listcell(xm.getKyLy());
				Listcell c3=new Listcell(xm.getKyProman());
				Listcell c4=new Listcell(xm.getKyKssj());
				Listcell c5=new Listcell(xm.getKyJssj());
				Listcell c6=new Listcell();
				if(xm.getKyProgress()!=null&&xm.getKyProgress().equals(GhXm.PROGRESS_DOING)){
					c6.setLabel("申请中");
				}else if(xm.getKyProgress()!=null&&xm.getKyProgress().equals(GhXm.PROGRESS_DO)){
					c6.setLabel("在研");
				}else if(xm.getKyProgress()!=null&&xm.getKyProgress().equals(GhXm.PROGRESS_DONE)){
					c6.setLabel("已完成");
				}else{
					c6.setLabel("");
				}
				Listcell c7=new Listcell(xm.getUser().getKuName());
				arg0.appendChild(c0);arg0.appendChild(c1);arg0.appendChild(c2);
				arg0.appendChild(c3);arg0.appendChild(c4);arg0.appendChild(c5);
				arg0.appendChild(c6);arg0.appendChild(c7);
				
			}
			
		});

	}

	@Override
	public void initWindow() {
		List xlist=xmService.findAllname(user.getKuId(), user.getKuName(), lx, type);
		if(xmlist!=null){
			xmlist.setModel(new ListModelList(xlist));
		}else{
			xmlist.setModel(new ListModelList());
		}
	}
    public void onClick$query(){
    	if("".equals(xmmc.getValue())&&"".equals(ly.getValue())&&"".equals(fzr.getValue())){
    		try {
				Messagebox.show("请填写检索条件，至少填写一个！","警告",Messagebox.OK,Messagebox.EXCLAMATION);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
    	}
    	List xlist=xmService.findByMcAndLyAndFzr(user.getKuId(), xmmc.getValue().trim(), lx, ly.getValue().trim(), fzr.getValue().trim(), type);
    	
    	System.out.println("-------------------查询结果======"+xlist.size());
		if(xmlist!=null&&xlist.size()>0){
			xmlist.setModel(new ListModelList(xlist));
			xmpanel.setVisible(true);
			exception.setVisible(false);
		}else{
			xmpanel.setVisible(false);
			exception.setVisible(true);
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
