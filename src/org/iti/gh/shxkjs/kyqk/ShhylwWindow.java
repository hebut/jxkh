package org.iti.gh.shxkjs.kyqk;

import java.util.ArrayList;
import java.util.List;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.entity.GhFile;
import org.iti.gh.entity.GhHylw;
import org.iti.gh.service.GhFileService;
import org.iti.gh.service.HylwService;
import org.iti.gh.service.LwzlService;
import org.iti.xypt.ui.base.BaseWindow;
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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

import com.uniwin.asm.personal.ui.data.teacherinfo.KyxmzzlistWindow;
import com.uniwin.framework.common.fileuploadex.DownloadFJ;
import com.uniwin.framework.common.fileuploadex.UploadFJ;
import com.uniwin.framework.entity.WkTUser;

public class ShhylwWindow extends BaseWindow {

	Listbox hylwlist;
	Grid shhylw;
	Label cgmc,kanwu,shijian,time,palce,host,all,publish,num,pages;
	Listbox record;
	Row zdqk,rowFile;
	HylwService hylwService;
	Long kdid=(Long)Executions.getCurrent().getArg().get("kdid");
	Short lx=(Short)Executions.getCurrent().getArg().get("lx");
//	Short type=(Short)Executions.getCurrent().getArg().get("type");
	GhHylw Lwzl;
	Textbox reason;
	Radiogroup audit,zd;
	WkTUser user;
	GhFileService ghfileService;
	ListModelList fileModel=new ListModelList();
	Listbox upList;
	Button downFileZip;
	@Override
	public void initShow() {
		hylwlist.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhHylw lwzl=(GhHylw)arg1;
				Listcell c0=new Listcell(1+"");
				Listcell c1=new Listcell("绩效考核管理系统的研究与实现");
				
				Listcell c2=new Listcell("2010-06");
				Listcell c3=new Listcell("计算机科学与工程");
				
				Listcell c4=new Listcell("2011-02");
				Listcell c5=new Listcell("张三、王立");
//				InnerButton bt=new InnerButton("查看");
//				bt.addEventListener(Events.ON_CLICK, new EventListener(){
//					public void onEvent(Event arg0) throws Exception {
//						 KyxmzzlistWindow cgWin = (KyxmzzlistWindow) Executions.createComponents
//							("/admin/personal/data/teacherinfo/kyxmzzlist.zul", null, null);
//						 	cgWin.setKuid(lwzl.getUser().getKuId());
//							cgWin.setLw(lwzl);
//							cgWin.initWindow();
//							cgWin.doHighlighted();
//					}});
//				c5.appendChild(bt);	
				Listcell c6=new Listcell("EI收录");
				Listcell c7=new Listcell();
				InnerButton ib=new InnerButton("审核");
				c7.appendChild(ib);
				ib.addEventListener(Events.ON_CLICK, new EventListener(){

					public void onEvent(Event arg0) throws Exception {
						hylwlist.setVisible(false);
						shhylw.setVisible(true);
					}
					
				});
				arg0.appendChild(c0);arg0.appendChild(c1);arg0.appendChild(c2);
				arg0.appendChild(c3);arg0.appendChild(c4);arg0.appendChild(c5);
				arg0.appendChild(c6); arg0.appendChild(c7);
			}
			
		});
	}

	@Override
	public void initWindow() {
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		hylwlist.setVisible(true);
		shhylw.setVisible(false);
		reason.setValue("");
		audit.setSelectedIndex(0);
		if(kdid==null){
			kdid=getXyUserRole().getKdId();
		}	
		List lwlist=hylwService.findSumKdId(kdid, lx, null);
		if(lwlist!=null&&lwlist.size()>0){
			hylwlist.setModel(new ListModelList(lwlist));
		}else{
			hylwlist.setModel(new ListModelList());
		}
		
		
	}
   public void ininGrid(GhHylw lwzl){
	   fileModel.clear();
	   List fjList=new ArrayList();
	   if(lx==GhHylw.JYLW){
		   zdqk.setVisible(true);
		   fjList = ghfileService.findByFxmIdandFType(lwzl.getLwId(), 13);
	   }else if(lx==GhHylw.KYLW){
		   zdqk.setVisible(false);
		   fjList = ghfileService.findByFxmIdandFType(lwzl.getLwId(), 3);
	   }
	   if (fjList==null||fjList!=null&&fjList.size() == 0) {// 无附件
			rowFile.setVisible(false);
			downFileZip.setDisabled(true);
		} else {// 有附件
			rowFile.setVisible(true);
			downFileZip.setDisabled(false);
			// 初始化附件
			for (int i = 0; i < fjList.size(); i++) {
				UploadFJ ufj = new UploadFJ(false);
				try {
					ufj.ReadFJ(getDesktop(), (GhFile) fjList.get(i));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				fileModel.add(ufj);
			}
			upList.setModel(fileModel);
			rowFile.setVisible(true);
		}
	   List recordtype = new ArrayList();
		String[] Recordtype = {"-请选择-","SCI(科学引文索引)收录","EI(工程索引)收录","ISTP(科技会议录索引 )收录","CSCD(中国科学引文数据库)收录","CSSCI(中文社会科学引文索引)收录","SSCI(社会科学引文索引)收录","A&HCI(艺术与人文科学引文索引)收录","《新华文摘》 全文转载","《中国数学文摘》全文转载","《中国社会科学文摘》全文转载","《中国数学》全文转载","《中国人大复印资料》全文转载","《全国高等院校文科学报文摘》全文转载", "《新华文摘》摘要转载","《中国社会科学文摘》摘要转载","《中国人大复印资料》摘要转载" ,"《中国数学》摘要转载"};
		for(int i = 0;i < Recordtype.length; i++){
			recordtype.add(Recordtype[i]);
		}
		record.setModel(new ListModelList(recordtype));
		if(Lwzl.getLwRecord() == null || Lwzl.getLwRecord() == ""){
			record.setSelectedIndex(0);
		}else {
			record.setSelectedIndex(Integer.valueOf(Lwzl.getLwRecord().trim()));
		}
		cgmc.setValue(lwzl.getLwMc());
		kanwu.setValue(lwzl.getLwKw());
		shijian.setValue(lwzl.getLwFbsj());
		time.setValue(lwzl.getLwTime());
		palce.setValue(lwzl.getLwPlace());
		host.setValue(lwzl.getLwHost());
		all.setValue(lwzl.getLwAll());
		publish.setValue(lwzl.getLwPublish());
		num.setValue(lwzl.getLwNum());
		pages.setValue(lwzl.getLwPages());
		if(lx==GhHylw.JYLW){
			if (lwzl.getLwZd().shortValue() == (GhHylw.LWZDXS.shortValue())) { // 教师论文还是指导学生论文
				zd.setSelectedIndex(0);
			} else {
				zd.setSelectedIndex(1);
			}
		}
		
   }
 //打包下载附件
	public void onClick$downFileZip(){
		DownloadFJ.ListModelListDownloadCommand(this.getDesktop(), Lwzl.getLwId(), fileModel);
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
//   public void onClick$submit(){
//	   Lwzl.setAuditState(Short.parseShort(audit.getSelectedIndex()+""));
//	   Lwzl.setAuditUid(user.getKuId());
//	   Lwzl.setAuditReason(reason.getValue());
//	   hylwService.update(Lwzl);
//	   try {
//			Messagebox.show("审核成功！","提示",Messagebox.OK,Messagebox.INFORMATION);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		initWindow();
//   }
//   public void onClick$close(){
//   	initWindow();
////   	Events.postEvent(Events.ON_CHANGE, this, null);
//   }
   public void onClick$back(){
   	this.detach();
   	Events.postEvent(Events.ON_CHANGE, this, null);
   }
}
