package org.iti.gh.ui.listbox;

import java.util.ArrayList;
import java.util.List;

import org.iti.gh.entity.GhUserdept;
import org.iti.gh.entity.GhYjfx;
import org.iti.gh.service.UserdeptService;
import org.iti.gh.service.YjfxService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.uniwin.basehs.util.BeanFactory;

public class YjfxListbox extends Listbox implements AfterCompose{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	YjfxService  yjfxService=(YjfxService)BeanFactory.getBean("yjfxService");
	UserdeptService userdeptService =(UserdeptService)BeanFactory.getBean("userdeptService");
	public void afterCompose() {
		Components.addForwards(this, this);
		Components.wireVariables(this, this);
		
	}
    public void initYjfzList(Long kuid,final Long gyid){
    	GhYjfx gy=new GhYjfx();
    	gy.setGyId(0L);
    	gy.setGyName("-«Î—°‘Ò-");
    	List yjfxlist=new ArrayList();
    	List fxlist=yjfxService.findByKuid(kuid);
    	yjfxlist.add(gy);
    	yjfxlist.addAll(fxlist);
    	this.setModel(new ListModelList(yjfxlist));
    	this.setSelectedIndex(0);
    	this.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				GhYjfx yjfx=(GhYjfx)arg1;
				arg0.setValue(yjfx);
				arg0.setLabel(yjfx.getGyName().trim());
				if(gyid!=null&&yjfx.getGyId().intValue()==gyid.intValue()){
					arg0.setSelected(true);
				}
			}
    	});
    }
    public void initYjfxList(Long kuid,final Long gyid){
    	List<GhYjfx> listyjfx=new ArrayList<GhYjfx>();
    	GhYjfx gy=new GhYjfx();
    	gy.setGyId(0L);
    	gy.setGyName("-«Î—°‘Ò-");
    	listyjfx.add(gy);
    	List listuserdept=userdeptService.findByKuId(kuid);
    	if(listuserdept!=null&&listuserdept.size()!=0){
    		for(int i=0;i<listuserdept.size();i++){
    			GhUserdept ud=(GhUserdept) listuserdept.get(i);
    			List<GhYjfx> listyj=yjfxService.findByKdid(ud.getId().getKdId());
    			if(listyj!=null&&listyj.size()!=0){
    				listyjfx.addAll(listyj);
    			}
    			
    		}
    	}
    	this.setModel(new ListModelList(listyjfx));
    	this.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				GhYjfx yjfx=(GhYjfx)arg1;
				arg0.setValue(yjfx);
				arg0.setLabel(yjfx.getGyName().trim());
				if(yjfx.getGyId().intValue()==gyid.intValue()){
					arg0.setSelected(true);
				}				
			}
    		
    	});
    }
	
}
