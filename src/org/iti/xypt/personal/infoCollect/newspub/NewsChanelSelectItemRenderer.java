package org.iti.xypt.personal.infoCollect.newspub;

/**
 * <li>À¸Ä¿Ê÷µÄÏÔ³ÊÆ÷
 * @author whm
 * @2010-3-29
 */
import java.util.ArrayList;
import java.util.List;

import org.iti.xypt.personal.infoCollect.entity.WkTChanel;
import org.iti.xypt.personal.infoCollect.entity.WkTWebsite;
import org.iti.xypt.personal.infoCollect.service.NewsService;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;

import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.RoleService;


public class NewsChanelSelectItemRenderer implements TreeitemRenderer {

	List slist,alist;
	WkTUser user;
	List userDeptList;
	NewsService newsService;
	RoleService roleService;
	public NewsChanelSelectItemRenderer(List slist){
		if(slist==null){
		   slist=new ArrayList();
		}
		 this.slist=slist;
	}
	
	public void render(Treeitem item, Object data) throws Exception {
		if(data instanceof WkTWebsite)
		{
			WkTWebsite web=(WkTWebsite)data;
			item.setValue(web);
			item.setLabel(web.getkwName());	
			item.setSelected(false);
			item.setCheckable(false);
		}
		else if(data instanceof WkTChanel)
		{
		  WkTChanel c=(WkTChanel)data;
		  item.setValue(c);
		  item.setLabel(c.getKcName());	
			 for(int i=0;i<slist.size();i++){
				 WkTChanel ci=(WkTChanel)slist.get(i);
				 if(ci.getKcId().intValue()==c.getKcId().intValue())
				 {
					 item.setSelected(true);
				 }
		}

			 user=(WkTUser)Sessions.getCurrent().getAttribute("user");
				userDeptList=(List)Sessions.getCurrent().getAttribute("userDeptList");
               newsService=(NewsService) Sessions.getCurrent().getAttribute("newsService");
               roleService=(RoleService) Sessions.getCurrent().getAttribute("roleService");
               List rlist=roleService.getUserRoleId(user.getKuId());
			  alist=newsService.getChanelOfManage(user,userDeptList,rlist);
			  int count=0;
			  for(int j=0;j<alist.size();j++)
				 {
					 WkTChanel cc=(WkTChanel) alist.get(j);
					 if(cc.getKcId().toString().trim().equals(c.getKcId().toString().trim()))
					 {
						 count++;
					 }
				 }
				 if(count==0)
				 {
					 item.setCheckable(false);
					 item.setDisabled(true);
				 }
			}
		item.setHeight("20px");
		item.addEventListener(Events.ON_CLICK, new EventListener(){
			public void onEvent(Event arg0) throws Exception {				
				Treeitem item=(Treeitem)arg0.getTarget();
				if(item.getValue() instanceof WkTWebsite)
				{
					Messagebox.show("ÇëÑ¡ÔñÀ¸Ä¿");
				}
			}			
		});
	}
}
