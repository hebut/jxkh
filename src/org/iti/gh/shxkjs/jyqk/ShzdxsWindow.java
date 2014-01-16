package org.iti.gh.shxkjs.jyqk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.entity.GhFile;
import org.iti.gh.entity.GhPx;
import org.iti.gh.service.GhFileService;
import org.iti.gh.service.PxService;
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

import com.uniwin.framework.common.fileuploadex.DownloadFJ;
import com.uniwin.framework.common.fileuploadex.UploadFJ;
import com.uniwin.framework.entity.WkTUser;

public class ShzdxsWindow extends BaseWindow {

	Listbox zdxsList;
	Grid auditZdxs;
	Label cgmc,time,jiangxiang,dj,danwei,stu;
	WkTUser user;
	Long kdid=(Long)Executions.getCurrent().getArg().get("kdid");
	Short state=(Short)Executions.getCurrent().getArg().get("state");
	Textbox reason;
	Radiogroup audit;
	GhPx Px;
	PxService pxService;
	//暂存附件
	private Row rowFile;
	Listbox upList;
	ListModelList fileModel=new ListModelList();
	GhFileService ghfileService;
	Button downFileZip;
	@Override
	public void initShow() {
		zdxsList.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhPx px = (GhPx) arg1;
				//序号
				Listcell c1 = new Listcell(arg0.getIndex()+1+"");
				//获奖名称
				Listcell c2 = new Listcell(px.getPxMc());

				//主办单位
				Listcell c3 = new Listcell(px.getPxDw());

				//获得奖项
				Listcell c4 = new Listcell(px.getPxJx());
				//指导人
				Listcell c5 = new Listcell(px.getUser().getKuName());
				//本人作用
				Listcell c6 = new Listcell();
				if(px.getPxBrzy()!=null){
					c6.setLabel(px.getPxBrzy());
				}
				//操作
				Listcell c7 = new Listcell();
				InnerButton gn = new InnerButton();

				gn.setLabel("审核");
				gn.addEventListener(Events.ON_CLICK, new EventListener(){

					public void onEvent(Event arg0) throws Exception {
						Px=px;
						zdxsList.setVisible(false);
						auditZdxs.setVisible(true);
						initAuditZdxs(px);
					}
				});
				c7.appendChild(gn);
				arg0.appendChild(c1); arg0.appendChild(c2); arg0.appendChild(c3);
				arg0.appendChild(c4); arg0.appendChild(c5); arg0.appendChild(c6);
				arg0.appendChild(c7);
			
				
			}
		});
	}

	@Override
	public void initWindow() {
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		zdxsList.setVisible(true);
		auditZdxs.setVisible(false);
		reason.setValue("");
		audit.setSelectedIndex(0);
		if (kdid == null) {
			kdid = getXyUserRole().getKdId();
		}
		List zdlist=pxService.findByKdidAndState(kdid, state);
		if(zdlist!=null&&zdlist.size()>0){
			zdxsList.setModel(new ListModelList(zdlist));
		}else{
			zdxsList.setModel(new ListModelList());
		}
		
	}
	
	public void initAuditZdxs(GhPx px){
		cgmc.setValue(px.getPxMc());
		time.setValue(px.getPxTime());
		jiangxiang.setValue(px.getPxJx());
		dj.setValue(px.getPxDj());
		danwei.setValue(px.getPxDw());
		stu.setValue(px.getPxStu());
	    List fjList=new ArrayList();
		fjList = ghfileService.findByFxmIdandFType(px.getPxId(), 16);
	    
	    if (fjList.size() == 0) {// 无附件
			rowFile.setVisible(false);
			downFileZip.setDisabled(true);
		 } else {// 有附件
			downFileZip.setDisabled(false);
			// 初始化附件
			if(fjList!=null){
			for (int i = 0; i < fjList.size(); i++) {
				UploadFJ ufj = new UploadFJ(false);
				try {
					ufj.ReadFJ(getDesktop(), (GhFile) fjList.get(i));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				fileModel.add(ufj);
			}
			}
			upList.setModel(fileModel);
			rowFile.setVisible(true);
		}
	}
	public void onClick$submit(){
		Px.setAuditState(Short.parseShort(audit.getSelectedIndex()+""));
		Px.setAuditUid(user.getKuId());
		Px.setReason(reason.getValue());
		pxService.update(Px);
		try {
			Messagebox.show("审核成功！","提示",Messagebox.OK,Messagebox.INFORMATION);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		initWindow();
   }
	  //打包下载附件
	public void onClick$downFileZip(){
		DownloadFJ.ListModelListDownloadCommand(this.getDesktop(), Px.getPxId(), fileModel);
	}
	//单个文件下载
	public void onClick$downFile(){
		Listitem it = upList.getSelectedItem();
		if (it == null) {
			if (fileModel.getSize() > 0) {
				it = upList.getItemAtIndex(0);
			}
		}
		UploadFJ temp = (UploadFJ) it.getValue();
		DownloadFJ.DownloadCommand(temp);
	}
   public void onClick$close(){
	   initWindow();
  }
   public void onClick$back() {
		this.detach();
		Events.postEvent(Events.ON_CHANGE, this, null);
	}
}
