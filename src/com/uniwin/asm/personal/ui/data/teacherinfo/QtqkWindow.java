package com.uniwin.asm.personal.ui.data.teacherinfo;

import java.util.ArrayList;
import java.util.List;

import org.iti.gh.entity.GhFile;
import org.iti.gh.entity.GhQt;
import org.iti.gh.service.GhFileService;
import org.iti.gh.service.QtService;
import org.iti.xypt.ui.base.FrameworkWindow;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
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

public class QtqkWindow extends FrameworkWindow{

	Textbox cgmc,shijian,bz;
	
	GhQt qt;
	Long kuid;
	
	//�ݴ渽��
	private Row rowFile;
	Listbox upList;
	ListModelList fileModel;
	
	QtService qtService;
	GhFileService ghfileService;
	@Override
	public void initShow() {
		
	}

	@Override
	public void initWindow() {
		
		if(qt!=null){
			
			//����
			if(qt.getQtMc() != null){
				cgmc.setValue(qt.getQtMc());
			}else {
				cgmc.setValue("");
			}
		
			
			//ʱ��
			if(qt.getQtSj()!=null){
				shijian.setValue(qt.getQtSj());
			}else {
				shijian.setValue("");	
			}
			
			//��ע��֤���ţ�
			if(qt.getQtBz()!=null){
				bz.setValue(qt.getQtBz());
			}else{
				bz.setValue("");
			}
		}else{
			cgmc.setValue("");
			shijian.setValue("");
			bz.setValue("");
		}
		// ����
		if (qt == null) {// �����
			rowFile.setVisible(false);
		} else {// �޸�
			List fjList = ghfileService.findByFxmIdandFType(qt.getQtId(), 9);
			if (fjList.size() == 0) {// �޸���
				rowFile.setVisible(false);
			} else {// �и���
				// ��ʼ������
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
		//ʱ��
		if(shijian.getValue()==null||"".equals(shijian.getValue().trim())){
			throw new WrongValueException(shijian, "����û����дʱ�䣬��ʽ��2008/9/28��2008��2008/9��");
			}else{
			try{
				String str = shijian.getValue().trim();
				if(str.length() < 4){
					throw new WrongValueException(shijian, "�������ʱ���ʽ�д�������������д����ʽ��2008/9/28��2008��2008/9��");
					
				}
				else if(str.length()==4||'/'==str.charAt(4)){
					String s[] = str.split("/");
					if(s.length==1||s.length==2||s.length==3){
						for(int i=0;i<s.length;i++){
							String si = s[i];
							Integer.parseInt(si);
						}
					}else{
						throw new WrongValueException(shijian, "�������ʱ���ʽ�д�������������д����ʽ��2008/9/28��2008��2008/9��");
						
					}
				}else{
					throw new WrongValueException(shijian, "�������ʱ���ʽ�д�������������д����ʽ��2008/9/28��2008��2008/9��");
					
				}
			}catch(NumberFormatException e){
				throw new WrongValueException(shijian, "�������ʱ���ʽ�д�������������д����ʽ��2008/9/28��2008��2008/9��");
				
			}
			
		}
		//��ע��֤���ţ�
		if(bz.getValue()==null||"".equals(bz.getValue().trim())){
			throw new WrongValueException(bz, "����û����дר����Ȩ�ţ�");
		}
		
		//false��ʾ���޸ģ�true��ʾ���½�
		boolean index = false; 
		if(qt==null){//˵�������½���һ����Ŀ
			qt = new GhQt();
			index = true;
		}
		qt.setQtMc(cgmc.getValue());
		qt.setQtSj(shijian.getValue());
		qt.setQtBz(bz.getValue());
		if(index){
			qt.setKuId(kuid);
			qtService.save(qt);
			// �����洢
			UploadFJ.ListModelListUploadCommand(fileModel,qt.getQtId());
			//
			Messagebox.show("����ɹ���","��ʾ",Messagebox.OK,Messagebox.INFORMATION);
		}else{
			qtService.update(qt);
			// �����洢
			UploadFJ.ListModelListUploadCommand(fileModel,qt.getQtId());
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
		Media media = ufj.Upload(this.getDesktop(), 9, 10, "�������ܳ���10MB",
				"��������------�������ܳ���10MB");
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
				+ "\\" + "_" +this.qt.getQtId() + "_" + ".zip");
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
				+ "\\" + "_" +this.qt.getQtId() + "_" + ".zip");
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
		DownloadFJ.ListModelListDownloadCommand(this.getDesktop(),this.qt.getQtId(), fileModel);
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

	public GhQt getQt() {
		return qt;
	}

	public void setQt(GhQt qt) {
		this.qt = qt;
	}


}
