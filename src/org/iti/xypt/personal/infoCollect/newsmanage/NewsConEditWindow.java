package org.iti.xypt.personal.infoCollect.newsmanage;
import java.util.List;
import java.util.Map;

import org.iti.xypt.personal.infoCollect.entity.WkTOrinfo;
import org.iti.xypt.personal.infoCollect.entity.WkTOrinfocnt;
import org.iti.xypt.personal.infoCollect.service.OriNewsService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Html;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTMlog;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.MLogService;



public class NewsConEditWindow extends Window implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Map params;	
	WkTOrinfo oinfo;
	Textbox kititle,kititle2,kikeys,kisource,kimemo,ptime;
	Html kicontent;
	OriNewsService orinewsService;
	MLogService mlogService;
	Toolbarbutton delete;
	public void afterCompose()
	{
		params=this.getAttributes();
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
	}
	public void initWindow(WkTOrinfo oinfo)
	{
		this.oinfo=oinfo;
		kititle.setValue(oinfo.getKoiTitle());
		kititle2.setValue(oinfo.getKoiTitle2());
		kisource.setValue(oinfo.getKoiSource());
		kimemo.setValue(oinfo.getKoiMemo());
		ptime.setValue(oinfo.getKoiPtime());
		initOinfocnt(orinewsService.getOriInfocnt(oinfo.getKoiId()));
		
	}
	public void  initOinfocnt(List rlist)
	{
		String con="";
		for(int i=0;i<rlist.size();i++)
		{
			WkTOrinfocnt infcnt=(WkTOrinfocnt)rlist.get(i);
			con+=infcnt.getKoiContent();
		}
		kicontent.setContent(con);
	}
	public void onClick$delete() throws InterruptedException
	{
		if(Messagebox.show("确定要删除吗？", "确认", 
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.OK)
		{
			List clist=orinewsService.getOriInfocnt(oinfo.getKoiId());
			if(clist.size()!=0)
			{
				for(int i=0;i<clist.size();i++)
				{
					WkTOrinfocnt infocnt=(WkTOrinfocnt) clist.get(i);
					orinewsService.delete(infocnt);
				}
			}
			orinewsService.delete(oinfo);
			 WkTUser user=(WkTUser)Sessions.getCurrent().getAttribute("user");
	    	 mlogService.saveMLog(WkTMlog.FUNC_CMS, "删除信息，id:"+oinfo.getKoiId(), user);
	    	 Messagebox.show("删除成功");
	    	 this.detach();
	    	Events.postEvent(Events.ON_CHANGE, this, null);
		}
	}
}