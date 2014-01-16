package org.iti.gh.glyjfx.fxfzr.lwgl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.iti.gh.entity.GhUseryjfx;
import org.iti.gh.entity.GhYjfxlw;
import org.iti.gh.entity.GhYjfxxm;
import org.iti.gh.service.UseryjfxService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

public class AddlwWindow extends BaseWindow  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Listbox fzrlist,ndlist,lwlxlist;
	Textbox content;
	Intbox num;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initWindow() {
        List listlw=new ArrayList();
		List listfzr=new ArrayList(); 
		List listnd=new ArrayList();
		List listuser=useryjfxService.findByGyid(gyid);
		for(int i=0;i<listuser.size();i++){
			GhUseryjfx useryjfx=(GhUseryjfx) listuser.get(i); 
			//listfzr.add(useryjfx.getUser().getKuName());
		    listfzr.add(useryjfx);
		}
		String[] ty=GhYjfxlw.TYPE;
		for(int i=0;i<ty.length;i++){
			listlw.add(ty[i]);
		}
		Date now=new Date(); 
		listnd.add(now.getYear()+1900+1);
		listnd.add(now.getYear()+1900+2);
		listnd.add(now.getYear()+1900+3);
		listnd.add(now.getYear()+1900+4);
		listnd.add(now.getYear()+1900+5); 
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
		lwlxlist.setModel(new ListModelList(listlw));
	    lwlxlist.setSelectedIndex(0);
		
	}
     public void onClick$submit() throws InterruptedException{
    	 GhYjfxlw lw=new GhYjfxlw();
    	 lw.setGlContent(content.getValue());
    	 lw.setGlNum(num.getValue());
    	 lw.setGlType((short)lwlxlist.getSelectedIndex());
    	 lw.setKuId(Long.parseLong(fzrlist.getSelectedItem().getValue()+""));
    	 lw.setGlYear(Integer.parseInt(ndlist.getSelectedItem().getValue()+""));
    	 lw.setGyId(gyid);
    	 useryjfxService.save(lw);
    	 Messagebox.show("提交成功！", "提示：", Messagebox.OK, Messagebox.INFORMATION);
 		Events.postEvent(Events.ON_CHANGE,this,null);
 		this.detach();
     }
     public void onClick$close(){
 		this.detach();
 	}
 	public void onClick$reset(){
 		num.setValue(0);
 		content.setValue("");
 		fzrlist.setSelectedIndex(0);
 		lwlxlist.setSelectedIndex(0);
 		ndlist.setSelectedIndex(0);
 	}

}
