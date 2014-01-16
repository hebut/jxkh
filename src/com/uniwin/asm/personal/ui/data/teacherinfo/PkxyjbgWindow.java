package com.uniwin.asm.personal.ui.data.teacherinfo;

import java.util.ArrayList;
import java.util.List;

import org.iti.gh.entity.GhFile;
import org.iti.gh.entity.GhPkpy;
import org.iti.gh.service.GhFileService;
import org.iti.gh.service.PkpyService;
import org.iti.gh.service.XmService;
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

public class PkxyjbgWindow extends FrameworkWindow{

	Textbox pkname,pklevel,pktime,pkdept;
	
	GhPkpy pkpy;
	Long kuid;
	
	//暂存附件
	private Row rowFile;
	Listbox upList;
	ListModelList fileModel;
	
	
	XmService xmService;
	PkpyService pkpyService;
	GhFileService ghfileService;
	Button deUpload,upFile,downFileZip,submit;
	@Override
	public void initShow() {
		
	}

	@Override
	public void initWindow() {
		if(pkpy!=null){
			//名称
			if(pkpy.getPkName() != null){
				pkname.setValue(pkpy.getPkName());
			}
			
			//获奖名称/等级
			if(pkpy.getPkLevel()!= null){
				pklevel.setValue(pkpy.getPkLevel());
			}else{
				pklevel.setValue("");
			}
			
			//获奖时间
			if(pkpy.getPkTime()!= null){
				pktime.setValue(pkpy.getPkTime());
			}else{
				pktime.setValue("");
			}
			//颁奖部门
			if(pkpy.getPkDept() != null){
				pkdept.setValue(pkpy.getPkDept());
			}else {
				pkdept.setValue("");
			}
			if(pkpy.getAuditState()!=null&&pkpy.getAuditState().shortValue()==GhPkpy.AUDIT_YES){
				 submit.setDisabled(true);
    	    	 upFile.setDisabled(true);
			}
		}else{
			pkname.setValue("");
			pklevel.setValue("");
			pkdept.setValue("");
			pktime.setValue("");
			downFileZip.setDisabled(true);
		}
		// 附件
		if (pkpy == null) {// 新添加
			rowFile.setVisible(false);
		} else {// 修改
			List fjList = ghfileService.findByFxmIdandFType(pkpy.getPkId(), 12);
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
	
		//赛事名称
		if(pkname.getValue()==null||"".equals(pkname.getValue().trim())){
			throw new WrongValueException(pkname, "您还没有填写赛事名称！");
		}
		//获奖等级
		if(pklevel.getValue()==null||"".equals(pklevel.getValue().trim())){
			throw new WrongValueException(pkname, "您还没有填写获奖等级！");
		}
		//获奖时间
		if(pktime.getValue()==null||"".equals(pktime.getValue().trim())){
			throw new WrongValueException(pktime, "您还没有填写时间，格式如2008/9/28、2008、2008/9！");
		}else{
			try{
				String str = pktime.getValue().trim();
				if(str.length()<4){
					throw new WrongValueException(pktime, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
				}
				else if(str.length()==4||'/'==str.charAt(4)){
					String s[] = str.split("/");
					if(s.length==1||s.length==2||s.length==3){
						for(int i=0;i<s.length;i++){
							String si = s[i];
							Integer.parseInt(si);
						}
					}else{
						throw new WrongValueException(pktime, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
					}
				}else{
					throw new WrongValueException(pktime, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
				}
			}catch(NumberFormatException e){
				throw new WrongValueException(pktime, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
			}
		}
		
		//颁奖部门
		if(pkdept.getValue()==null||"".equals(pkdept.getValue().trim())){
			throw new WrongValueException(pkdept, "您还没有填写获奖名称/等级！");
		}
		
		//false表示是修改，true表示是新建
		boolean index = false; 
		if(pkpy==null){//说明这是新建的一个项目
			pkpy = new GhPkpy();
			index = true;
		}
		pkpy.setPkName(pkname.getValue());
		pkpy.setPkLevel(pklevel.getValue());
		pkpy.setPkTime(pktime.getValue());
		pkpy.setPkDept(pkdept.getValue());
		if(index){
		   GhPkpy Pk=pkpyService.findBykuidAndMc(kuid, pkname.getValue().trim());
		   if(Pk!=null){
			   Messagebox.show("禁止重复添加！","警告",Messagebox.OK,Messagebox.EXCLAMATION);
			   pkpy=null;
		   }
			pkpy.setKuId(kuid);
			pkpyService.save(pkpy);
			// 附件存储
			UploadFJ.ListModelListUploadCommand(fileModel,pkpy.getPkId());
			//
			Messagebox.show("保存成功！","提示",Messagebox.OK,Messagebox.INFORMATION);
		}else{
			pkpy.setAuditState(null);
			pkpy.setAuditUid(null);
			pkpy.setReason(null);
			pkpyService.update(pkpy);
			// 附件存储
			UploadFJ.ListModelListUploadCommand(fileModel,pkpy.getPkId());
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
		Media media = ufj.Upload(this.getDesktop(),12, 10, "附件不能超过10MB",
				"各类评课选优、基本功竞赛附件------附件不能超过10MB");
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
	 * @throws InterruptedException 
	 */
	public void onClick$deUpload() throws InterruptedException {
		if(Messagebox.show("确定删除吗?", "提示", Messagebox.OK|Messagebox.CANCEL,
				Messagebox.QUESTION)==1){
		Listitem it = upList.getSelectedItem();
		if (it == null) {
			if (fileModel.getSize() > 0) {
				it = upList.getItemAtIndex(0);
			}
		}
		// 去掉旧的打包附件
		IsZipExists.delZipFile(this.getDesktop().getWebApp().getRealPath(
				"/upload/xkjs/").trim()
				+ "\\" + "_" + this.pkpy.getPkId() + "_" + ".zip");
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
		}}
	}

	public void onClick$reset(){
		// 去掉旧的打包附件
		IsZipExists.delZipFile(this.getDesktop().getWebApp().getRealPath(
				"/upload/xkjs/").trim()
				+ "\\" + "_" + this.pkpy.getPkId() + "_" + ".zip");
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
		DownloadFJ.ListModelListDownloadCommand(this.getDesktop(), this.pkpy.getPkId(), fileModel);
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
	public GhPkpy getPkpy() {
		return pkpy;
	}

	public void setPkpy(GhPkpy pkpy) {
		this.pkpy = pkpy;
	}



}
