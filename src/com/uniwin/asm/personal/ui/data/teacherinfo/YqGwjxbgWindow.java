package com.uniwin.asm.personal.ui.data.teacherinfo;

import org.iti.gh.entity.GhJxbg;
import org.iti.gh.entity.GhXshy;
import org.iti.gh.service.JxbgService;
import org.iti.gh.service.XmService;
import org.iti.xypt.ui.base.FrameworkWindow;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

public class YqGwjxbgWindow extends FrameworkWindow{

	Textbox cgmc,shijian, huiyi,baogao,zhuti,didian,beizhu,remarks;
	
	GhJxbg bg;
	Long kuid;
	
	JxbgService jxbgService;
	Button submit;
	@Override
	public void initShow() {
		
	}

	@Override
	public void initWindow() {
		
		if(bg!=null){
			
			//��ѧ�򱨸�������
			if(bg.getJxJxmc() != null){
				cgmc.setValue(bg.getJxJxmc());
			}else{
				cgmc.setValue("");
			}
		
			
			//�����ѧ���ƻ���ʻ�������
			if(bg.getJxHymc()!=null){
				huiyi.setValue(bg.getJxHymc());
			}else{
				huiyi.setValue("");
			}
			
			//ʱ��
			if(bg.getJxSj()!=null){
				shijian.setValue(bg.getJxSj());
			}else{
				shijian.setValue("");
			}
			//��ѧ�򱨸�����
			if(bg.getJxBgmc()!=null){
				baogao.setValue(bg.getJxBgmc());
			}else{
				baogao.setValue("");
			}
			if(bg.getJxSubject() != null){
				zhuti.setValue(bg.getJxSubject());
			}else{
				zhuti.setValue("");
			}
			if(bg.getJxPlace() != null){
				didian.setValue(bg.getJxPlace());
			}else{
				didian.setValue("");
			}
			if(bg.getJxRemarks() != null){
				remarks.setValue(bg.getJxRemarks());
			}else{
				remarks.setValue("");
			}
			if(bg.getAuditState()!=null&&bg.getAuditState().shortValue()==GhXshy.AUDIT_YES){
				submit.setDisabled(true);
			}
		}else{
			cgmc.setValue("");
			shijian.setValue("");
			huiyi.setValue("");
			baogao.setValue("");
			zhuti.setValue("");
			didian.setValue("");
			remarks.setValue("");
		}
	}
	
	public void onClick$submit() throws InterruptedException{
		
		//��ѧ�򱨸�������
		if(cgmc.getValue()==null||"".equals(cgmc.getValue().trim())){
			throw new WrongValueException(cgmc, "����û����д�㱨�����ƣ�");
		}
		//�����ѧ���ƻ���ʻ�������
		if(huiyi.getValue()==null||"".equals(huiyi.getValue().trim())){
			throw new WrongValueException(huiyi, "����û����д�������ƣ�");
		}
		//��ѧ�򱨸�����
		if(baogao.getValue()==null||"".equals(baogao.getValue().trim())){
			throw new WrongValueException(baogao, "����û����д��ѧ�򱨸����ƣ�");
		}
		//ʱ��
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
		
	
		
		//false��ʾ���޸ģ�true��ʾ���½�
		boolean index = false; 
		if(bg==null){//˵�������½���һ����Ŀ
			bg = new GhJxbg();
			index = true;
		}
		bg.setJxSj(shijian.getValue());
		bg.setJxJxmc(cgmc.getValue());
		bg.setJxHymc(huiyi.getValue());
		bg.setJxBgmc(baogao.getValue());
		bg.setJxPlace(didian.getValue());
		bg.setJxRemarks(remarks.getValue());
		bg.setJxSubject(zhuti.getValue());
		if(index){
//			if(jxbgService.CheckOnlyOne(null, baogao.getValue(), huiyi.getValue().trim(), GhJxbg.IFCJ_NO)){
//				Messagebox.show("���ύ�ı�����Ϣ�Ѵ��ڣ��������ظ���ӣ��رմ��ڼ���������","����",Messagebox.OK,Messagebox.EXCLAMATION);
//				bg=null;
//			return;
//			}
			bg.setJxIfcj(GhJxbg.IFCJ_NO);
			bg.setKuId(kuid);
			jxbgService.save(bg);
			Messagebox.show("����ɹ���","��ʾ",Messagebox.OK,Messagebox.INFORMATION);
			
		}else{
//			if(jxbgService.CheckOnlyOne(bg, baogao.getValue(), huiyi.getValue().trim(), GhJxbg.IFCJ_NO)){
//				Messagebox.show("���ύ�ı�����Ϣ�Ѵ��ڣ����豣����µ���Ϣ���رմ��ڼ���������","����",Messagebox.OK,Messagebox.EXCLAMATION);
//				return;
//			}
			bg.setAuditState(null);
			bg.setAuditUid(null);
			bg.setReason(null);
			jxbgService.update(bg);
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

	public GhJxbg getBg() {
		return bg;
	}

	public void setBg(GhJxbg bg) {
		this.bg = bg;
	}



}
