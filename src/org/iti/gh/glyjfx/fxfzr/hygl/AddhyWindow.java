package org.iti.gh.glyjfx.fxfzr.hygl;

import org.iti.gh.entity.GhUseryjfx;
import org.iti.gh.entity.GhYjfxhy;
import org.iti.gh.service.UseryjfxService;
import org.iti.gh.service.YjfxhyService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;



public class AddhyWindow extends Window implements AfterCompose {
	Listbox rylist,hytype,hyfs,hynd;
	Textbox content;
	Long gyId;
	UseryjfxService useryjfxService;
	YjfxhyService yjfxhyService;
	public void setGyId(Long gyId) {
		this.gyId = gyId;
	}
		public void afterCompose() {
			Components.wireVariables(this, this);
			Components.addForwards(this, this);
			initShow();
		}
		public void initShow() {
			rylist.setItemRenderer(new ListitemRenderer(){
				public void render(Listitem item, Object arg1) throws Exception {
					GhUseryjfx fx=(GhUseryjfx)arg1;
					item.setValue(fx.getUser().getKuId());
					item.setLabel(fx.getUser().getKuName());
				}});
		}
		public void initWindow() {
			rylist.setModel(new ListModelList(useryjfxService.findByGyid(gyId)));
			rylist.setSelectedIndex(0);
			String[] fs=GhYjfxhy.SF;
			String[] type=GhYjfxhy.TYPE;
			Integer[] nd={2011,2012,2013,2014,2015};
			for(int i=0;i<fs.length;i++){
				hyfs.appendItem(fs[i],i+"" );
			}
			hyfs.setSelectedIndex(0);
			
			for(int i=0;i<type.length;i++){
				hytype.appendItem(type[i],i+"" );
			}
			hytype.setSelectedIndex(0);
			for(int i=0;i<nd.length;i++){
				hynd.appendItem(nd[i]+"", nd[i]+"");
			}
			hynd.setSelectedIndex(0);
		}
		public void onClick$submit() throws InterruptedException{
			 
			GhYjfxhy hyy=new GhYjfxhy();
			hyy.setGhContent(content.getValue());
			hyy.setGhSf((short)hyfs.getSelectedIndex());
			hyy.setGhType((short)hytype.getSelectedIndex());
			hyy.setGhYear(Integer.valueOf(hynd.getSelectedItem().getLabel()).intValue());
			hyy.setKuId((Long)rylist.getSelectedItem().getValue());
			hyy.setGyId(gyId);
			yjfxhyService.save(hyy);
		    Messagebox.show("提交成功！", "提示：", Messagebox.OK, Messagebox.INFORMATION);
			Events.postEvent(Events.ON_CHANGE,this,null);
			this.detach();
			
		}
		public void onClick$close(){
			this.detach();
		}
		public void onClick$reset(){
			content.setValue("");
			rylist.setSelectedIndex(0);
			hytype.setSelectedIndex(0);
			hyfs.setSelectedIndex(0);
			hynd.setSelectedIndex(0);
		}

}
