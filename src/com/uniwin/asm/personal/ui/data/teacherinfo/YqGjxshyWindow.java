package com.uniwin.asm.personal.ui.data.teacherinfo;

import java.util.ArrayList;
import java.util.List;

import org.iti.gh.entity.GhXshy;
import org.iti.gh.service.XshyService;
import org.iti.xypt.ui.base.FrameworkWindow;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

public class YqGjxshyWindow extends FrameworkWindow{

	Textbox cgmc,shijian,cgzt,cgdd,remarks;
	Intbox zrs,jwrs;
	Listbox cdrw;
	
	GhXshy xs;
	Long kuid;
	
	XshyService xshyService;
	Button submit;
	@Override
	public void initShow() {
		
	}

	@Override
	public void initWindow() {
		List subcdrw = new ArrayList();
		String[] Subcdrw = {"--��ѡ��--","����","�μ�","����"};
		for(int i = 0;i < Subcdrw.length; i++){
			subcdrw.add(Subcdrw[i]);
		}
		//���˳е����������
		cdrw.setModel(new ListModelList(subcdrw));
		
		if(xs!=null){
			
			//����
			if(xs.getHyMc() != null){
				cgmc.setValue(xs.getHyMc());
			}else{
				cgmc.setValue("");
			}
			//�ٰ�ʱ��
			if(xs.getHySj()!=null){
				shijian.setValue(xs.getHySj());
			}else{
				shijian.setValue("");
			}
			
			//������
			if(xs.getHyZrs()!=null){
				zrs.setValue(xs.getHyZrs());
			}else{
				zrs.setValue(null);
			}
			//��������
			if(xs.getHyJwrs()!=null){
				jwrs.setValue(xs.getHyJwrs());
			}else{
				jwrs.setValue(null);
			}
			//���˳е����������
			if(xs.getHyEffect() == null || xs.getHyEffect() == ""){
				cdrw.setSelectedIndex(0);
			}else {
				cdrw.setSelectedIndex(Integer.valueOf(xs.getHyEffect().trim()));
			}
			if(xs.getHyTheme() != null){
				cgzt.setValue(xs.getHyTheme());	
			}else{
				cgzt.setValue("");	
			}
			if(xs.getHyPlace() != null){
				cgdd.setValue(xs.getHyPlace());	
			}else{
				cgdd.setValue("");	
			}
			if(xs.getHyRemarks() != null){
				remarks.setValue(xs.getHyRemarks());	
			}else{
				remarks.setValue("");
			}
			if(xs.getAuditState()!=null&&xs.getAuditState().shortValue()==GhXshy.AUDIT_YES){
				submit.setDisabled(true);
			}
		
		}else{
			cgmc.setValue("");
			shijian.setValue("");
			zrs.setValue(null);
			jwrs.setValue(null);
			remarks.setValue("");
			cgdd.setValue("");
			cgzt.setValue("");
		}
	}
	
	public void onClick$submit() throws InterruptedException{
		
		//��������
		if(cgmc.getValue()==null||"".equals(cgmc.getValue().trim())){
			throw new WrongValueException(cgmc, "����û����д�������ƣ�");
		}
		//�ٰ�ʱ��
		if(shijian.getValue()==null||"".equals(shijian.getValue().trim())){
			throw new WrongValueException(shijian, "����û����дʱ�䣬��ʽ��2008/9/28��2008��2008/9��");
		
		}else{
			try{
				String str = shijian.getValue().trim();
				if(str.length()<4){
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
		//������
		if(zrs.getValue()==null){
			throw new WrongValueException(zrs, "����û����д��������");
		}
		//������Ա��
		if(jwrs.getValue()==null){
			throw new WrongValueException(jwrs, "����û����д������Ա����");
		}
		if(zrs.getValue()<jwrs.getValue())
		{
			throw new WrongValueException(zrs, "��ע���������;�����Ա��������д�����߼���������");
		}
		//false��ʾ���޸ģ�true��ʾ���½�
		boolean index = false; 
		if(xs==null){//˵�������½���һ����Ŀ
			xs = new GhXshy();
			index = true;
		}
		xs.setHyMc(cgmc.getValue());
		xs.setHySj(shijian.getValue());
		xs.setHyZrs(zrs.getValue());
		xs.setHyJwrs(jwrs.getValue());
		xs.setHyEffect(String.valueOf(cdrw.getSelectedIndex()));
		xs.setHyRemarks(remarks.getValue());
		xs.setHyPlace(cgdd.getValue());
		xs.setHyTheme(cgzt.getValue());
		
		if(index){
			xs.setHyIfcj(GhXshy.IFCJ_NO);
			xs.setKuId(kuid);
			xshyService.save(xs);
			Messagebox.show("����ɹ���","��ʾ",Messagebox.OK,Messagebox.INFORMATION);
		}else{
			xs.setAuditState(null);
			xs.setAuditUid(null);
			xs.setReason(null);
			xshyService.update(xs);
			Messagebox.show("����ɹ���","��ʾ",Messagebox.OK,Messagebox.INFORMATION);
		}
		Events.postEvent(Events.ON_CHANGE, this, null);
	}
	
	public void onClick$reset(){
		initWindow();
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

	public GhXshy getXs() {
		return xs;
	}

	public void setXs(GhXshy xs) {
		this.xs = xs;
	}



}
