package org.iti.gh.glyjfx.fxfzr.xmgl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.iti.gh.entity.GhUseryjfx;
import org.iti.gh.entity.GhYjfxxm;
import org.iti.gh.service.UseryjfxService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

public class AddxmWindow extends BaseWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Listbox fzrlist,xmjblist,xmlblist,lblist,ndlist;
	Textbox content,name;
	Long gyid;
	UseryjfxService useryjfxService;
	Button submit,reset,close;

	public Long getGyid() {
		return gyid;
	}

	public void setGyid(Long gyid) {
		this.gyid = gyid;
	}
	 

	@Override
	public void initShow() {
		
	}

	@Override
	public void initWindow() {
		List listjb=new ArrayList();
		List listxmlb=new ArrayList();
		List listlb=new ArrayList();
		List listfzr=new ArrayList(); 
		List listnd=new ArrayList();
		String[] jb=GhYjfxxm.JB;
		String[] xmlb=GhYjfxxm.XMLB;
		String[] lb=GhYjfxxm.LB;
		for(int i=0;i<jb.length;i++){
			listjb.add(jb[i]);
		}
		for(int i=0;i<xmlb.length;i++){
			listxmlb.add(xmlb[i]);
		}
		for(int i=0;i<lb.length;i++){
			listlb.add(lb[i]);
		}
		List listuser=useryjfxService.findByGyid(gyid);
		 
		for(int i=0;i<listuser.size();i++){
			GhUseryjfx useryjfx=(GhUseryjfx) listuser.get(i); 
			//listfzr.add(useryjfx.getUser().getKuName());
		    listfzr.add(useryjfx);
		}
	   
		Date now=new Date(); 
		listnd.add(now.getYear()+1900+1);
		listnd.add(now.getYear()+1900+2);
		listnd.add(now.getYear()+1900+3);
		listnd.add(now.getYear()+1900+4);
		listnd.add(now.getYear()+1900+5);
		xmjblist.setModel(new ListModelList(listjb));
		xmjblist.setSelectedIndex(0);
		xmlblist.setModel(new ListModelList(listxmlb));
		xmlblist.setSelectedIndex(0);
		lblist.setModel(new ListModelList(listlb));
		lblist.setSelectedIndex(0);
		fzrlist.setModel(new ListModelList(listfzr));
 
		fzrlist.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem item, Object arg1) throws Exception {
				GhUseryjfx fx=(GhUseryjfx)arg1;
				item.setValue(fx.getUser().getKuId());
				item.setLabel(fx.getUser().getKuName());
				
			}
			
		});
		fzrlist.setSelectedIndex(0);
		ndlist.setModel(new ListModelList(listnd));
		ndlist.setSelectedIndex(0);

	}
	public void onClick$submit() throws InterruptedException{
		 
		GhYjfxxm xm=new GhYjfxxm();
		xm.setGxContent(content.getValue());
		xm.setGxJb((short) xmjblist.getSelectedIndex());
		xm.setGxLb((short) lblist.getSelectedIndex());
		xm.setGxName(name.getValue().trim());
		xm.setGxYear(Integer.valueOf(ndlist.getSelectedItem().getValue()+""));
		xm.setKuId(Long.parseLong(fzrlist.getSelectedItem().getValue().toString()));
		xm.setGyId(gyid);
		Date now=new Date();
		xm.setGxTime(now.getTime());
		xm.setGxXmlb((short) xmlblist.getSelectedIndex());
		useryjfxService.save(xm);
		Messagebox.show("提交成功！", "提示：", Messagebox.OK, Messagebox.INFORMATION);
		Events.postEvent(Events.ON_CHANGE,this,null);
		this.detach();
	}
	public void onClick$close(){
		this.detach();
	}
	public void onClick$reset(){
		name.setValue("");
		content.setValue("");
		fzrlist.setSelectedIndex(0);
		xmjblist.setSelectedIndex(0);
		xmlblist.setSelectedIndex(0);
		lblist.setSelectedIndex(0);
		ndlist.setSelectedIndex(0);
	}

}
