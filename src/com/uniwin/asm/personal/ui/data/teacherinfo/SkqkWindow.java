package com.uniwin.asm.personal.ui.data.teacherinfo;

import java.util.ArrayList;
import java.util.List;

import org.iti.gh.common.util.MessageBoxshow;
import org.iti.gh.entity.GhFile;
import org.iti.gh.entity.GhSk;
import org.iti.gh.service.GhFileService;
import org.iti.gh.service.SkService;
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
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

import com.uniwin.framework.common.fileuploadex.DownloadFJ;
import com.uniwin.framework.common.fileuploadex.IsZipExists;
import com.uniwin.framework.common.fileuploadex.UploadFJ;

public class SkqkWindow extends FrameworkWindow{

	Textbox cgmc,zhuanye,keshi,work,grade,year;
	
	GhSk sk;
	Long kuid;
	
	//暂存附件
	private Row rowFile;
	Listbox upList;
	ListModelList fileModel;
	
	
	SkService skService;
	GhFileService ghfileService;
	Button downFile,deUpload,upFile,downFileZip,submit;
	@Override
	public void initShow() {
		
	}

	@Override
	public void initWindow() {
		
		if(sk!=null){
			
			//课程名称
			cgmc.setValue(sk.getSkMc());
			
			//专业
			if(sk.getSkZy()!=null){
				zhuanye.setValue(sk.getSkZy());
			}
			
			//学时（理论+试验）
			if(sk.getSkXs()!=null){
				keshi.setValue(sk.getSkXs());
			}
			if(sk.getThWork() != null){
				work.setValue(sk.getThWork());
				
			}
			if(sk.getSkGrade() != null){
				grade.setValue(sk.getSkGrade());
			}else {
				grade.setValue("");
			}
			if(sk.getSkYear() != null){
				year.setValue(sk.getSkYear());
			}else {
				year.setValue("");
			}
			
		
		}else{
			cgmc.setValue("");
			zhuanye.setValue("");
			keshi.setValue("");
			work.setValue("");
			grade.setValue("");
			year.setValue("");
			downFile.setDisabled(true);
			downFileZip.setDisabled(true);
		}
		// 附件
		if (sk == null) {// 新添加
			rowFile.setVisible(false);
		} else {// 修改
			List fjList = ghfileService.findByFxmIdandFType(sk.getSkId(), 16);
			if (fjList.size() == 0) {// 无附件
				rowFile.setVisible(false);
				downFileZip.setDisabled(true);
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
		
		//课程名称
		if(cgmc.getValue()==null||"".equals(cgmc.getValue().trim())){
			throw new WrongValueException(cgmc, "您还没有填写课程名称！");
		}
		//专业
		if(zhuanye.getValue()==null||"".equals(zhuanye.getValue().trim())){
			throw new WrongValueException(zhuanye, "您还没有填写专业！");
		}
		//学时（理论+试验）
		if(keshi.getValue()==null||"".equals(keshi.getValue().trim())){
			throw new WrongValueException(keshi, "您还没有填写学时（理论+试验)！");
		}
		
		if(year.getValue()==null||"".equals(year.getValue().trim())){
			throw new WrongValueException(year, "您还没有填写时间!");
		}
		if(grade.getValue()==null||"".equals(grade.getValue().trim())){
			throw new WrongValueException(grade, "您还没有填写授课对象!");
		}
		/*//年份
		else{
			try{
				String str = year.getValue().trim();
				if(str.length() < 4){
					throw new WrongValueException(year, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
				}
				else if(str.length()==4||'/'==str.charAt(4)){
					String s[] = str.split("/");
					if(s.length==1||s.length==2||s.length==3){
						for(int i=0;i<s.length;i++){
							String si = s[i];
							Integer.parseInt(si);
						}
					}else{
						throw new WrongValueException(year, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
					}
				}else{
					throw new WrongValueException(year, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
				}
			}catch(NumberFormatException e){
				throw new WrongValueException(year, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
			}
		
		}*/
		boolean index = false; 
		if(sk==null){//说明这是新建的一个项目
			sk = new GhSk();
			index = true;
		}
		sk.setSkMc(cgmc.getValue());
		sk.setSkZy(zhuanye.getValue());
		sk.setSkXs(keshi.getValue());
		sk.setThWork(work.getValue());
		sk.setSkGrade(grade.getValue());
		sk.setSkYear(year.getValue());
		sk.setAuditState(Short.valueOf("0"));
		if(index){
			sk.setKuId(kuid);
			skService.save(sk);
			// 附件存储
			UploadFJ.ListModelListUploadCommand(fileModel, sk.getSkId());
			//
			MessageBoxshow.showInfo("保存成功！");
		}else{
			
			skService.update(sk);
			// 附件存储
			UploadFJ.ListModelListUploadCommand(fileModel, sk.getSkId());
			//
			MessageBoxshow.showInfo("保存成功！");
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
				"授课情况附件------附件不能超过10MB");
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
				+ "\\" + "_" + this.sk.getSkId() + "_" + ".zip");
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

	public void onClick$reset(){
		// 去掉旧的打包附件
		IsZipExists.delZipFile(this.getDesktop().getWebApp().getRealPath(
				"/upload/xkjs/").trim()
				+ "\\" + "_" + this.sk.getSkId() + "_" + ".zip");
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
		DownloadFJ.ListModelListDownloadCommand(this.getDesktop(), this.sk.getSkId(), fileModel);
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

	public GhSk getSk() {
		return sk;
	}

	public void setSk(GhSk sk) {
		this.sk = sk;
	}

}
