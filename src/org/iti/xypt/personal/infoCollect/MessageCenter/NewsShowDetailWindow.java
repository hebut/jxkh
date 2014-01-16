   package org.iti.xypt.personal.infoCollect.MessageCenter;
/**
 * 显示信息详情界面
 * @author whm
 * 2010-3-20
 */

import java.io.IOException;
import java.util.List;

import org.iti.xypt.personal.infoCollect.entity.WkTOrifile;
import org.iti.xypt.personal.infoCollect.entity.WkTOrinfo;
import org.iti.xypt.personal.infoCollect.entity.WkTOrinfocnt;
import org.iti.xypt.personal.infoCollect.service.OriNewsService;
import org.iti.xypt.personal.infoCollect.service.TaskService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Html;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import com.uniwin.common.util.EncodeUtil;



public class NewsShowDetailWindow extends Window implements AfterCompose {

	private static final long serialVersionUID = 1L;
	//信息数据访问接口
	OriNewsService orinewsService;
	TaskService showtaskService;
	Label title,source,author,time,url;
	Html content;
	Button print;
	WkTOrinfo info;
	Hbox pics;
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
	}

	
//判断信息类型，根据类型显示不一样的界面，并获得详细信息
	public void initWindow(WkTOrinfo info) throws IOException
	{
		this.info=info;
		this.setTitle(info.getKoiTitle());
//		WkTDistribute dis=newsService.getDistri(info.getKoiId(),info.getKeId());
		title.setValue(info.getKoiTitle());
		source.setValue(info.getKoiSource());
		author.setValue(info.getKoiAuthname());
//		time.setValue(dis.getKbDtime().substring(0, 10));
		time.setValue(info.getKoiCtime().substring(0, 10));
		final String u=info.getKoiUrl();
		String r = null;
		if(u!=null && u.length()>20){
			r=u.substring(0,20)+"...";
		}
		url.setValue(r);
		url.addEventListener(Events.ON_CLICK, new EventListener(){
			public void onEvent(Event arg0) throws Exception {
				Executions.getCurrent().sendRedirect(u,"_blank");
			}
			
		});
		
		List list=orinewsService.getOriInfocnt(info.getKoiId());
		initInfocnt(list);
		List flist=orinewsService.getOrifile(info.getKoiId());
		if(flist.size()!=0)
		{ for(int i=0;i<flist.size();i++)
		{
			WkTOrifile file=(WkTOrifile) flist.get(i);
			 Image img=new Image();
			 String pa = "/upload/info"+"/"+file.getId().getKofName().trim();
				img.setSrc(pa);
				img.setWidth("200px");
				   img.setHeight("200px");
				   img.setParent(pics);
		}
		}//+"&disid="+EncodeUtil.encodeByDES(dis.getKbId()))
		print.setHref("/newsprint.do?action=newsprint&infoid=" + EncodeUtil.encodeByDES(info.getKoiId()));
		print.setTarget("_blank");
	}	

//获取信息内容
public void  initInfocnt(List rlist)
	{
		String con="";
		for(int i=0;i<rlist.size();i++)
		{
			WkTOrinfocnt inf=(WkTOrinfocnt)rlist.get(i);
			con+=inf.getKoiContent();
		}
		content.setContent(con);
	}


public void onClick$close()
{
	this.detach();
}

}
