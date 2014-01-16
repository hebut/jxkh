package org.iti.gh.shxkjs.kyqk;

import java.util.List;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.entity.GhFile;
import org.iti.gh.entity.GhRjzz;
import org.iti.gh.service.GhFileService;
import org.iti.gh.service.RjzzService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

import com.uniwin.asm.personal.ui.data.teacherinfo.KyrjzzWindow;
import com.uniwin.framework.common.fileuploadex.DownloadFJ;
import com.uniwin.framework.common.fileuploadex.UploadFJ;
import com.uniwin.framework.entity.WkTUser;

public class ShrjzzWindow extends BaseWindow {

	Listbox rjzzlist;
	Grid AuditRjzz;
	GhRjzz Rjzz;
	Label xmmc,rjno,dengjino,dengjisj,firtime,menbers;
	Textbox reason;
	Radiogroup audit,zd;
	WkTUser user;
	Long kdid=(Long)Executions.getCurrent().getArg().get("kdid");
	GhFileService ghfileService;
	RjzzService rjzzService;
	ListModelList fileModel=new ListModelList();
	Listbox upList;
	Button downFileZip;
	Row rowFile;
	@Override
	public void initShow() {}

	@Override
	public void initWindow() {}
	public void initAuditrjzz(GhRjzz rjzz){}
	//打包下载附件
	public void onClick$downFileZip(){}
	//单个文件下载
	public void onClick$downFile(){}
	public void onClick$submit(){}
	public void onClick$close(){
	   	initWindow();
	   }
	  public void onClick$back() {}

}
