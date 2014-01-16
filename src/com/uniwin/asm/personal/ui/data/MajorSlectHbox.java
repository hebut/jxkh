package com.uniwin.asm.personal.ui.data;

import java.util.ArrayList;
import java.util.List;

import org.iti.gh.entity.GhZy;
import org.iti.gh.service.MajorService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;



public class MajorSlectHbox extends Hbox implements AfterCompose{

	MajorService majorService;
	GhZy zy1,zy2,zy3;
	
	Listbox flist,slist,tlist;
	
	public void afterCompose() {
		Components.wireVariables(this, this);
		flist=new Listbox();
		slist=new Listbox();
		tlist=new Listbox();
		flist.setMold("select");
		slist.setMold("select");
		tlist.setMold("select");
		
		flist.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				arg0.setValue(arg1);
				GhZy zy=(GhZy) arg1;
				arg0.setLabel(zy.getZySubname().trim());
				if(zy1!=null&&zy1.getZyId().trim().equalsIgnoreCase(zy.getZyId().trim())){				
					arg0.setSelected(true);
					loadMlist();
				}
			}
			
		});
		slist.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				arg0.setValue(arg1);
				GhZy zy=(GhZy) arg1;
				arg0.setLabel(zy.getZySubname().trim());			
				if(zy2!=null&&zy2.getZyId().trim().equals(zy.getZyPid().trim())){
					arg0.setSelected(true);
					loadMlist2(arg0);
				}
			}
			
		});
		tlist.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				arg0.setValue(arg1);
				GhZy zy=(GhZy) arg1;
				arg0.setLabel(zy.getZySubname().trim());
				if(zy3!=null&&zy3.getZyId().trim().equalsIgnoreCase(zy.getZyId().trim())){					
					arg0.setSelected(true);
				}
			}
			
		});
		flist.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				loadMlist();
			}
		});
		slist.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				loadMlist2(slist.getSelectedItem());
			}
		});
		this.appendChild(flist);
		this.appendChild(slist);
		this.appendChild(tlist);
		initHbox();
	}
	
	private void loadMlist2(Listitem item) {
		
		GhZy szy;
		szy=(GhZy) item.getValue();
		tlist.setModel(new ListModelList(majorService.findByPtid(szy.getZyId())));
		
	}
	private void initHbox() {
		List fmajor=majorService.findFirstmajor();
		System.out.println("m...s"+fmajor.size());
		if(fmajor!=null&&fmajor.size()!=0){
			List f=new ArrayList();
			GhZy g=new GhZy();
			g.setZySubname("-«Î—°‘Ò-");
			f.add(g);
			f.addAll(fmajor);
			flist.setModel(new ListModelList(f));
			if(zy1==null){
				loadMlist();
		}
		}
		
		
	}
	private void loadMlist() {
		GhZy fzy,szy;
		
		if(flist.getSelectedItem()==null){
			fzy=(GhZy) flist.getModel().getElementAt(0);
		}else{
			fzy=(GhZy) flist.getSelectedItem().getValue();
		}
		
		slist.setModel(new ListModelList(majorService.findByPtid(fzy.getZyId())));
		if(slist.getSelectedItem()==null){
			szy=(GhZy) slist.getModel().getElementAt(0);
		}else{
			szy=(GhZy) slist.getSelectedItem().getValue();
		}
		
		tlist.setModel(new ListModelList(majorService.findByPtid(szy.getZyId())));
	}

	public GhZy getSelectMajor(){
		GhZy z2;
		if(tlist.getSelectedItem()==null){
			z2=(GhZy) tlist.getModel().getElementAt(0);
		}else{
			z2=(GhZy) tlist.getSelectedItem().getValue();
		}
		return z2;
	}
	public void setMajor(GhZy z){
		this.zy3=z;		
		this.zy2=(GhZy) majorService.get(GhZy.class, zy3.getZyPid());
		this.zy1=(GhZy) majorService.get(GhZy.class, zy2.getZyPid());
		
		/*List zyList =majorService.findByZyid(z.getZyId());
		tlist.setModel(new ListModelList(zyList));
		Integer index3 = 0;
		for(int i=0;i<zyList.size();i++){
			GhZy zy = (GhZy)zyList.get(i);
			if(zy3.equals(zy)){
				index3= i;
			}
		}
		tlist.setSelectedIndex(index3);*/
		/*slist.setModel(new ListModelList(majorService.findByZyid(z.getZyPid())));
		Integer index3 = 0;
		for(int i=0;i<zyList.size();i++){
			GhZy zy = (GhZy)zyList.get(i);
			if(zy3.equals(zy)){
				index3= i;
			}
		}
		tlist.setSelectedIndex(index3);
		*/
		
	}
	
	
}
