   package org.iti.xypt.personal.infoCollect.newsaudit;
/**
 * 显示信息详情界面
 * @author whm
 * 2010-3-20
 */

import java.io.IOException;
import java.util.List;

import org.iti.xypt.personal.infoCollect.entity.WkTDistribute;
import org.iti.xypt.personal.infoCollect.entity.WkTFile;
import org.iti.xypt.personal.infoCollect.entity.WkTInfo;
import org.iti.xypt.personal.infoCollect.entity.WkTInfocnt;
import org.iti.xypt.personal.infoCollect.service.NewsService;
import org.iti.xypt.personal.infoCollect.service.TaskService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zk.ui.util.Clients;
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
	NewsService newsService;
	TaskService taskService;
	Label title,source,author,time;
	Html content;
	Button print;
	WkTInfo info;
	Hbox pics;
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
	}

	
//判断信息类型，根据类型显示不一样的界面，并获得详细信息
	public void initWindow(WkTInfo info) throws IOException
	{
		this.info=info;
		this.setTitle(info.getKiTitle());
		WkTDistribute dis=newsService.getDistri(info.getKiId(),info.getKeId());
		title.setValue(info.getKiTitle());
		source.setValue(info.getKiSource());
		author.setValue(info.getKuName());
		time.setValue(dis.getKbDtime().substring(0, 10));
		List list=newsService.getInfocnt(info.getKiId());
		initInfocnt(list);
		List flist=newsService.getFile(info.getKiId());
		if(flist.size()!=0)
		{ for(int i=0;i<flist.size();i++)
		{
			WkTFile file=(WkTFile) flist.get(i);
			 Image img=new Image();
			 String pa = "/upload/info"+"/"+file.getId().getKfName().trim();
				img.setSrc(pa);
				img.setWidth("200px");
				   img.setHeight("200px");
				   img.setParent(pics);
		}
		}
		print.setHref("/newsprint.do?action=newsprint&infoid=" + EncodeUtil.encodeByDES(info.getKiId())+"&disid="+EncodeUtil.encodeByDES(dis.getKbId()));
		print.setTarget("_blank");
	}	

//获取信息内容
public void  initInfocnt(List rlist)
	{
		String con="";
		for(int i=0;i<rlist.size();i++)
		{
			WkTInfocnt inf=(WkTInfocnt)rlist.get(i);
			con+=inf.getKiContent();
		}
		content.setContent(con);
	}

public void onClick$print()
{
	Clients.print();
}

}
