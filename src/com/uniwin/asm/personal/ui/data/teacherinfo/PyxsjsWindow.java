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
	
	//�ݴ渽��
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
			
			//����
			cgmc.setValue(px.getPxMc());
			
			//���쵥λ
			if(px.getPxDw()!=null){
				danwei.setValue(px.getPxDw());
			}
			
			//��ý���
			if(px.getPxJx()!=null){
				jiangxiang.setValue(px.getPxJx());
			}

			//��������
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
		// ����
		if (px == null) {// �����
			rowFile.setVisible(false);
		} else {// �޸�
			List fjList = ghfileService.findByFxmIdandFType(px.getPxId(), 16);
			if (fjList.size() == 0) {// �޸���
				downFileZip.setDisabled(true);
				rowFile.setVisible(false);
			} else {// �и���
				// ��ʼ������
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
		
		//����
		if(cgmc.getValue()==null||"".equals(cgmc.getValue().trim())){
			throw new WrongValueException(cgmc, "����û����д���ƣ�");
		}
		//��ʱ��
		if(time.getValue()==null||"".equals(time.getValue().trim())){
			throw new WrongValueException(time, "����û����дʱ�䣬��ʽ��2008/9/28��2008��2008/9��");
		
		}else{
			try{
				String str = time.getValue().trim();
				if(str.length()<4){
					throw new WrongValueException(time, "�������ʱ���ʽ�д�������������д����ʽ��2008/9/28��2008��2008/9��");
				}
				else if(str.length()==4||'/'==str.charAt(4)){
					String s[] = str.split("/");
					if(s.length==1||s.length==2||s.length==3){
						for(int i=0;i<s.length;i++){
							String si = s[i];
							Integer.parseInt(si);
						}
					}else{
						throw new WrongValueException(time, "�������ʱ���ʽ�д�������������д����ʽ��2008/9/28��2008��2008/9��");
					}
				}else{
					throw new WrongValueException(time, "�������ʱ���ʽ�д�������������д����ʽ��2008/9/28��2008��2008/9��");
					
				}
			}catch(NumberFormatException e){
				throw new WrongValueException(time, "�������ʱ���ʽ�д�������������д����ʽ��2008/9/28��2008��2008/9��");
			}
		
		}
		//��ý���
		if(jiangxiang.getValue()==null||"".equals(jiangxiang.getValue().trim())){
			throw new WrongValueException(jiangxiang, "����û����д��ý��");
		}
		//���쵥λ
		if(danwei.getValue()==null||"".equals(danwei.getValue().trim())){
			throw new WrongValueException(danwei, "����û����д���쵥λ��");
		}
		//��������
		if(zuoyong.getValue()==null||"".equals(zuoyong.getValue().trim())){
			throw new WrongValueException(zuoyong, "����û����д�������ã�");
		}
	
		
		//false��ʾ���޸ģ�true��ʾ���½�
		boolean index = false; 
		if(px==null){//˵�������½���һ����Ŀ
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
				Messagebox.show("��ֹ�ظ���ӣ�","����",Messagebox.OK,Messagebox.EXCLAMATION);
				px=null;
				return;
			}
			px.setKuId(kuid);
			pxService.save(px);
			// �����洢
			UploadFJ.ListModelListUploadCommand(fileModel, px.getPxId());
			//
			Messagebox.show("����ɹ���","��ʾ",Messagebox.OK,Messagebox.INFORMATION);
		}else{
			px.setAuditState(null);
			px.setAuditUid(null);
			px.setReason(null);
			pxService.update(px);
			// �����洢
			UploadFJ.ListModelListUploadCommand(fileModel, px.getPxId());
			//
			Messagebox.show("����ɹ���","��ʾ",Messagebox.OK,Messagebox.INFORMATION);
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
		Media media = ufj.Upload(this.getDesktop(),16, 10, "�������ܳ���10MB",
				"����ѧ���μӾ����������------�������ܳ���10MB");
		/* Object media = Fileupload.get(); */
		if (media == null) {
			return;
		}
		rowFile.setVisible(true);
		fileModel.add(ufj);
	}
	/**
	 * <li>ɾ���ϴ����ļ�������ѡ��
	 * 
	 * @author bobo 2010-4-11
	 */
	public void onClick$deUpload() {
		// ȥ���ɵĴ������
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
		// ȥ���ɵĴ������
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
	//������ظ���
	public void onClick$downFileZip(){
		DownloadFJ.ListModelListDownloadCommand(this.getDesktop(), this.px.getPxId(), fileModel);
	}
	//�����ļ�����
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
