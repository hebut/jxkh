package com.uniwin.asm.personal.ui.data.teacherinfo;

import org.iti.gh.entity.GhXm;
import org.iti.gh.service.XmService;
import org.iti.xypt.ui.base.FrameworkWindow;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

public class KyxmWindow extends FrameworkWindow{

	Textbox cgmc,ly,kaishi,jieshu,jingfei,gongxian;
	
	GhXm xm;
	Long kuid;
	
	XmService xmService;
	@Override
	public void initShow() {
		
	}

	@Override
	public void initWindow() {
		
		if(xm!=null){
			
			//����
			if(xm.getKyMc() != null){
				cgmc.setValue(xm.getKyMc());	
			}else{
				cgmc.setValue("");	
			}
			

			//��Դ
			if(xm.getKyLy()!=null){
				ly.setValue(xm.getKyLy());
			}else {
				ly.setValue("");
			}
			
			//��ʼʱ��
			if(xm.getKyKssj()!=null){
				kaishi.setValue(xm.getKyKssj());
			}else{
				kaishi.setValue("");
			}
			//����ʱ��
			if(xm.getKyJssj()!=null){
				jieshu.setValue(xm.getKyJssj());
			}else{
				jieshu.setValue("");
			}
			
			//����
			if(xm.getKyJf()!=null){
				jingfei.setValue(xm.getKyJf()+"");
			}else{
				jingfei.setValue("");
			}
			//���˹���
			if(xm.getKyCdrw()!=null){
				gongxian.setValue(xm.getKyCdrw());
			}else {
				gongxian.setValue("");
			}
		}else{
			cgmc.setValue("");
			ly.setValue("");
			kaishi.setValue("");
			jieshu.setValue("");
			jingfei.setValue("");
			gongxian.setValue("");
		}
	}
	
	public void onClick$submit() throws InterruptedException{
		//false��ʾ���޸ģ�true��ʾ���½�
		boolean index = false; 
		if(xm==null){//˵�������½���һ����Ŀ
			xm = new GhXm();
			index = true;
		}
		//��������
		if(cgmc.getValue()==null||"".equals(cgmc.getValue().trim())){
			Messagebox.show("����û����д��Ŀ����");
			if(index){
				xm = null;
			}
			return;
		}else{
			xm.setKyMc(cgmc.getValue());
		}
		//��Դ
		if(ly.getValue()==null||"".equals(ly.getValue().trim())){
			Messagebox.show("����û����д��Դ");
			if(index){
				xm = null;
			}
			return;
		}else{
			xm.setKyLy(ly.getValue());
		}
		//��ʼʱ��
		if(kaishi.getValue()==null||"".equals(kaishi.getValue().trim())){
			Messagebox.show("����û����д��ʼʱ��");
			if(index){
				xm = null;
			}
			return;
		}else{
			try{
				String str = kaishi.getValue().trim();
				if(str.length() < 4){
					Messagebox.show("������Ŀ�ʼʱ���ʽ�д�������������д��");
					if(index){
						xm = null;
					}
					return;
				}
				else if(str.length()==4||'/'==str.charAt(4)){
					String s[] = str.split("/");
					if(s.length==1||s.length==2||s.length==3){
						for(int i=0;i<s.length;i++){
							String si = s[i];
							Integer.parseInt(si);
						}
					}else{
						Messagebox.show("������Ŀ�ʼʱ���ʽ�д�������������д��");
						if(index){
							xm = null;
						}
						return;
					}
				}else{
					Messagebox.show("������Ŀ�ʼʱ���ʽ�д�������������д��");
					if(index){
						xm = null;
					}
					return;
				}
			}catch(NumberFormatException e){
				Messagebox.show("������Ŀ�ʼʱ���ʽ�д�������������д��");
				if(index){
					xm = null;
				}
				return;
			}
			xm.setKyKssj(kaishi.getValue());
		}
		//����ʱ��
		if(jieshu.getValue()==null||"".equals(jieshu.getValue().trim())){
			Messagebox.show("����û����д����ʱ��");
			if(index){
				xm = null;
			}
			return;
		}else{
			try{
				String str = jieshu.getValue().trim();
				if(str.length() < 4){
					Messagebox.show("������Ľ���ʱ���ʽ�д�������������д��");
					if(index){
						xm = null;
					}
					return;
				}
				else if(str.length()==4||'/'==str.charAt(4)){
					String s[] = str.split("/");
					if(s.length==1||s.length==2||s.length==3){
						for(int i=0;i<s.length;i++){
							String si = s[i];
							Integer.parseInt(si);
						}
					}else{
						Messagebox.show("������Ľ���ʱ���ʽ�д�������������д��");
						if(index){
							xm = null;
						}
						return;
					}
				}else{
					Messagebox.show("������Ľ���ʱ���ʽ�д�������������д��");
					if(index){
						xm = null;
					}
					return;
				}
			}catch(NumberFormatException e){
				Messagebox.show("������Ľ���ʱ���ʽ�д�������������д��");
				if(index){
					xm = null;
				}
				return;
			}
			xm.setKyJssj(jieshu.getValue());
		}
		//����
		if(jingfei.getValue()==null||"".equals(jingfei.getValue().trim())){
			Messagebox.show("����û����д������Ŀ");
			if(index){
				xm = null;
			}
			return;
		}else{
			Float kyJf = 0F;
			try{
				kyJf = Float.parseFloat(jingfei.getValue());
			}catch(NumberFormatException e){
				Messagebox.show("������ľ��Ѳ�����һ���淶�����֣�");
				if(index){
					xm=null;
				}
				return;
			}
			xm.setKyJf(kyJf);
		}
		//���˹���
		if(gongxian.getValue()==null||"".equals(gongxian.getValue().trim())){
			Messagebox.show("����û����д���˳е�����");
			if(index){
				xm = null;
			}
			return;
		}else{
			xm.setKyCdrw(gongxian.getValue());
		}
		if(index){
			xm.setKyLx(GhXm.KYDQ);
			xm.setKuId(kuid);
			xmService.save(xm);
			Messagebox.show("����ɹ���");
		}else{
			xmService.update(xm);
			Messagebox.show("����ɹ���");
		}
		Events.postEvent(Events.ON_CHANGE, this, null);
	}
	
	public void onClick$reset(){
		cgmc.setRawValue("");
		ly.setRawValue("");
		kaishi.setRawValue("");
		jieshu.setRawValue("");
		jingfei.setRawValue("");
		gongxian.setRawValue("");
	}
	
	public void onClick$close(){
		this.detach();
	}

	public GhXm getXm() {
		return xm;
	}

	public void setXm(GhXm xm) {
		this.xm = xm;
	}

	public Long getKuid() {
		return kuid;
	}

	public void setKuid(Long kuid) {
		this.kuid = kuid;
	}

}
