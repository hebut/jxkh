package org.iti.xypt.personal.infoCollect.MessageCenter;

import java.util.List;

import org.iti.xypt.personal.infoCollect.entity.WkTExtractask;
import org.iti.xypt.personal.infoCollect.entity.WkTNewsRead;
import org.iti.xypt.personal.infoCollect.entity.WkTNewsReadId;
import org.iti.xypt.personal.infoCollect.entity.WkTOrinfo;
import org.iti.xypt.personal.infoCollect.service.InfoService;
import org.iti.xypt.personal.infoCollect.service.NewsService;
import org.iti.xypt.personal.infoCollect.service.OriNewsService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Window;
import com.uniwin.framework.entity.WkTUser;

public class InfoShowWindow extends Window implements AfterCompose {

	private Listbox infoshowlistbox;
	NewsService newsService;
	InfoService infoService;
	OriNewsService orinewsService;
	
	ListModelList infoListModel;
	WkTUser wkTUser;
	 WkTExtractask etask1;
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		wkTUser = (WkTUser) Sessions.getCurrent().getAttribute("user");
	}

	public void initWindow( WkTExtractask etask)
	{
	
        this.etask1=etask;
        this.setTitle("["+etask1.getKeName()+"]ÐÂÎÅ");
        
		List nlist=orinewsService.findAllInfo(etask1.getKeId());
		infoListModel=new ListModelList(nlist);
		infoshowlistbox.setModel(infoListModel);
		
		infoshowlistbox.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception 
			{
				WkTOrinfo  info=(WkTOrinfo)data;
				item.setValue(info);
				 Listcell c0=new Listcell(item.getIndex()+1+"");
				 item.appendChild(c0);
				 final Listcell c1=new Listcell();
				 List rlist=newsService.findRead(info.getKoiId(),wkTUser.getKuId());
				 if(rlist.size()==0)
				 {
					
					 c1.setImage("/images/ims.news.gif");		
				     item.appendChild(c1);
				 }
				 else  if(rlist.size()!=0)
				 {
					 c1.setImage("/images/ims.readed.gif");
				     item.appendChild(c1);
				 }
				 Listcell c2=new Listcell();
				 if(info.getKoiTitle().trim().length()>30){
						String str1 = "";                                                      
						str1=info.getKoiTitle().trim().substring(0,30);
							c2=new Listcell(str1+"......");
					}else{
						 c2=new Listcell(info.getKoiTitle());					
					}
				 c2.setTooltiptext(info.getKoiTitle());
				  c2.addEventListener(Events.ON_CLICK, new EventListener(){
					  public void onEvent(Event event) throws Exception 
						{
							 Listitem c=(Listitem)event.getTarget().getParent();
						     final WkTOrinfo i=(WkTOrinfo)c.getValue();
//						     WkTDistribute dis=newsService.getDistri(i.getKiId(),i.getKeId());

						     List rlist=newsService.findRead(i.getKoiId(),wkTUser.getKuId());
							 if(rlist.size()==0)
							 {

						     WkTNewsReadId readid=new WkTNewsReadId(i.getKoiId(),etask1.getKeId(),wkTUser.getKuId());
						     WkTNewsRead read=new WkTNewsRead(readid,Short.valueOf("1"));
						     newsService.save(read);
						     initWindow(etask1);
							 }
						 
						 	 Executions.getCurrent().setAttribute("kiTitle", i);
						     NewsShowDetailWindow w=(NewsShowDetailWindow)Executions.createComponents("/admin/personal/message/send/showdetail.zul",null, null);	
						     w.doHighlighted();
						     w.initWindow(i);
						}
				  });
				 item.appendChild(c2);
				 Listcell c3=new Listcell();
				 String source=info.getKoiSource();
				 if(source!=null && !source.equals("")){
					 c3.setLabel(source.trim());
				 }else{
					 c3.setLabel(source);
				 }
				 item.appendChild(c3);
				 Listcell c4=new Listcell(info.getKoiCtime().substring(0,10));
				 item.appendChild(c4);
			}
		});
		
	}
	
}
