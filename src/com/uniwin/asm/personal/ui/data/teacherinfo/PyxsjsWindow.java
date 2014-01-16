package com.uniwin.asm.personal.ui.data.teacherinfo;

import java.util.ArrayList;
import java.util.List;

import org.iti.gh.entity.GhFile;
import org.iti.gh.entity.GhPx;
import org.iti.gh.service.GhFileService;
import org.iti.gh.service.PxService;
import org.iti.xypt.ui.base.FrameworkWindow;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

import com.uniwin.framework.common.fileuploadex.DownloadFJ;
import com.uniwin.framework.common.fileuploadex.IsZipExists;
import com.uniwin.framework.common.fileuploadex.UploadFJ;

public class PyxsjsWindow extends FrameworkWindow{

	Textbox cgmc,danwei,jiangxiang,zuoyong,dj,time,stu;
	
	GhPx px;
	Long kuid;
	
	//暂存附件
	private Row rowFile;
	Listbox upList;
	ListModelList fileModel;
	
	PxService pxService;
	GhFileService ghfileService;
	Button downFile,deUpload,upFile,downFileZip,submit;
	@Override
	public void initShow() {
		
	}

	@Override
	public void initWindow() {
		
		if(px!=null){
			
			//名称
			cgmc.setValue(px.getPxMc());
			
			//主办单位
			if(px.getPxDw()!=null){
				danwei.setValue(px.getPxDw());
			}
			
			//获得奖项
			if(px.getPxJx()!=null){
				jiangxiang.setValue(px.getPxJx());
			}

			//本人作用
			if(px.getPxBrzy()!=null){
				zuoyong.setValue(px.getPxBrzy());
			}
			if(px.getPxDj() != null){
				dj.setValue(px.getPxDj());
			}else {
				dj.setValue("");
			}
			if(px.getPxTime() != null){
				time.setValue(px.getPxTime());
			}else{
				time.setValue("");
			}
			if(px.getPxStu() != null){
				stu.setValue(px.getPxStu());
			}else{
				stu.setValue("");
			}
			if(px.getAuditState()!=null&&px.getAuditState().shortValue()==GhPx.AUDIT_YES){
				 submit.setDisabled(true);
    	    	 upFile.setDisabled(true);
			}
			
		}else{
			cgmc.setValue("");
			danwei.setValue("");
			jiangxiang.setValue("");
			zuoyong.setValue("");
			time.setValue("");
			dj.setValue("");
			stu.setValue("");
			downFileZip.setDisabled(true);
			downFile.setDisabled(true);
		}
		// 附件
		if (px == null) {// 新添加
			rowFile.setVisible(false);
		} else {// 修改
			List fjList = ghfileService.findByFxmIdandFType(px.getPxId(), 16);
			if (fjList.size() == 0) {// 无附件
				downFileZip.setDisabled(true);
				rowFile.setVisible(false);
			} else {// 有附件
				// 初始化附件
				downFileZip.setDisabled(false);
				for (int i = 0; i < fjList.size(); i++) {
					UploadFJ ufj = new UploadFJ(false);
					try {
						ufj.ReadFJ(this.getDesktop(), (GhFile) fjList.get(i));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					fileModel.add(ufj);
				}
				upList.setModel(fileModel);
				rowFile.setVisible(true);
			}
		}
		//
	}
	
	public void onClick$submit() throws InterruptedException{
		
		//名称
		if(cgmc.getValue()==null||"".equals(cgmc.getValue().trim())){
			throw new WrongValueException(cgmc, "您还没有填写名称！");
		}
		//获奖时间
		if(time.getValue()==null||"".equals(time.getValue().trim())){
			throw new WrongValueException(time, "您还没有填写时间，格式如2008/9/28、2008、2008/9！");
		
		}else{
			try{
				String str = time.getValue().trim();
				if(str.length()<4){
					throw new WrongValueException(time, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
				}
				else if(str.length()==4||'/'==str.charAt(4)){
					String s[] = str.split("/");
					if(s.length==1||s.length==2||s.length==3){
						for(int i=0;i<s.length;i++){
							String si = s[i];
							Integer.parseInt(si);
						}
					}else{
						throw new WrongValueException(time, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
					}
				}else{
					throw new WrongValueException(time, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
					
				}
			}catch(NumberFormatException e){
				throw new WrongValueException(time, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
			}
		
		}
		//获得奖项
		if(jiangxiang.getValue()==null||"".equals(jiangxiang.getValue().trim())){
			throw new WrongValueException(jiangxiang, "您还没有填写获得奖项！");
		}
		//主办单位
		if(danwei.getValue()==null||"".equals(danwei.getValue().trim())){
			throw new WrongValueException(danwei, "您还没有填写主办单位！");
		}
		//本人作用
		if(zuoyong.getValue()==null||"".equals(zuoyong.getValue().trim())){
			throw new WrongValueException(zuoyong, "您还没有填写本人作用！");
		}
	
		
		//false表示是修改，true表示是新建
		boolean index = false; 
		if(px==null){//说明这是新建的一个项目
			px = new GhPx();
			index = true;
		}
		px.setPxMc(cgmc.getValue().trim());
		px.setPxTime(time.getValue().trim());
		px.setPxJx(jiangxiang.getValue().trim());
		px.setPxDj(dj.getValue().trim());
		px.setPxDw(danwei.getValue().trim());
		px.setPxStu(stu.getValue().trim());
		px.setPxBrzy(zuoyong.getValue().trim());
		if(index){
			GhPx Px=pxService.findByKuidAndMc(kuid, cgmc.getValue().trim(), time.getValue().trim());
//			System.out.println(kuid+","+cgmc.getValue()+time.getValue().);
			if(Px!=null){
				Messagebox.show("禁止重复添加！","警告",Messagebox.OK,Messagebox.EXCLAMATION);
				px=null;
				return;
			}
			px.setKuId(kuid);
			pxService.save(px);
			// 附件存储
			UploadFJ.ListModelListUploadCommand(fileModel, px.getPxId());
			//
			Messagebox.show("保存成功！","提示",Messagebox.OK,Messagebox.INFORMATION);
		}else{
			px.setAuditState(null);
			px.setAuditUid(null);
			px.setReason(null);
			pxService.update(px);
			// 附件存储
			UploadFJ.ListModelListUploadCommand(fileModel, px.getPxId());
			//
			Messagebox.show("保存成功！","提示",Messagebox.OK,Messagebox.INFORMATION);
		}
		Events.postEvent(Events.ON_CHANGE, this, null);
	}
	
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		fileModel = new ListModelList(new ArrayList());
		upList.setModel(fileModel);
	}
	public void onClick$upFile() throws InterruptedException {
		/*Object media = Fileupload.get();
		if (media == null) {
			return;
		}
		rowFile.setVisible(true);
		fileModel.add(media);*/
		UploadFJ ufj = new UploadFJ(true);
		Media media = ufj.Upload(this.getDesktop(),16, 10, "附件不能超过10MB",
				"培养学生参加竞赛情况附件------附件不能超过10MB");
		/* Object media = Fileupload.get(); */
		if (media == null) {
			return;
		}
		rowFile.setVisible(true);
		fileModel.add(ufj);
	}
	/**
	 * <li>删除上传的文件，重新选择
	 * 
	 * @author bobo 2010-4-11
	 */
	public void onClick$deUpload() {
		// 去掉旧的打包附件
		IsZipExists.delZipFile(this.getDesktop().getWebApp().getRealPath(
				"/upload/xkjs/").trim()
				+ "\\" + "_" + px.getPxId() + "_" + ".zip");
		Listitem it = upList.getSelectedItem();
		if (it == null) {
			if (fileModel.getSize() > 0) {
				it = upList.getItemAtIndex(0);
			}
		}
		//
		UploadFJ temp = (UploadFJ) it.getValue();
		//
		if (fileModel.getSize() == 1) {
			temp.DeleteFJ();
			fileModel.remove(it.getValue());
			rowFile.setVisible(false);
		} else if (fileModel.getSize() > 1) {
			temp.DeleteFJ();
			fileModel.remove(it.getValue());
		}
	}
	@SuppressWarnings("unchecked")
	public void onClick$reset(){
		// 去掉旧的打包附件
		IsZipExists.delZipFile(this.getDesktop().getWebApp().getRealPath(
				"/upload/xkjs/").trim()
				+ "\\" + "_" + px.getPxId() + "_" + ".zip");
		List list = fileModel.getInnerList();
		for (int i = 0; i < list.size(); i++) {
			((UploadFJ) list.get(i)).DeleteFJ();
		}
		initWindow();
		fileModel.clear();
		rowFile.setVisible(false);
	}
	//打包下载附件
	public void onClick$downFileZip(){
		DownloadFJ.ListModelListDownloadCommand(this.getDesktop(), this.px.getPxId(), fileModel);
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
		this.detach();
	}


	public Long getKuid() {
		return kuid;
	}

	public void setKuid(Long kuid) {
		this.kuid = kuid;
	}

	public GhPx getPx() {
		return px;
	}

	public void setPx(GhPx px) {
		this.px = px;
	}


}
