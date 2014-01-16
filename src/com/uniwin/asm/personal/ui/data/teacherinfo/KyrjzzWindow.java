package com.uniwin.asm.personal.ui.data.teacherinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iti.gh.entity.GhCg;
import org.iti.gh.entity.GhFile;
import org.iti.gh.entity.GhJslw;
import org.iti.gh.entity.GhJsxm;
import org.iti.gh.entity.GhRjzz;
import org.iti.gh.entity.GhYjfx;
import org.iti.gh.service.GhFileService;
import org.iti.gh.service.JslwService;
import org.iti.gh.service.RjzzService;
import org.iti.gh.service.YjfxService;
import org.iti.gh.ui.listbox.YjfxListbox;
import org.iti.xypt.ui.base.FrameworkWindow;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

import com.uniwin.asm.personal.ui.data.teacherinfo.jyqk.SelectRjWindow;
import com.uniwin.asm.personal.ui.data.teacherinfo.kyqk.SelectcgWindow;
import com.uniwin.framework.common.fileuploadex.DownloadFJ;
import com.uniwin.framework.common.fileuploadex.UploadFJ;
import com.uniwin.framework.entity.WkTUser;

public class KyrjzzWindow extends FrameworkWindow{

	Textbox dengjino,dengjisj,rjno,menbers,firtime;
	Label rank,writer;
	Button submit,reset,close;
	YjfxListbox research;
	Textbox xmmc;
	Long kuid;
	GhRjzz rjzz;
	
	//�ݴ渽��
	private Row rowFile;
	Listbox upList;
	ListModelList fileModel;
	RjzzService rjzzService;
	YjfxService yjfxService;
	GhFileService ghfileService;
	JslwService jslwService;
	WkTUser user;
	Button downFile,deUpload,upFile,downFileZip;
	@Override
	public void initShow() {
	
		
	}

	@Override
	public void initWindow() {
		menbers.addEventListener(Events.ON_CHANGE, new EventListener(){

			public void onEvent(Event arg0) throws Exception {
				List namelist=new ArrayList();
				String str=menbers.getValue().trim();
				if(str.contains("��")||str.contains(",")){
					throw new WrongValueException(menbers, "��Ŀ����Ա��д������ѡ��ٺţ�");
				}else{
				while (str.contains("��")) {
						Label lb = new Label(str.substring(0,str.indexOf("��")));
						namelist.add(lb.getValue().trim());
						str = str.substring(str.indexOf("��") + 1, str.length());
					
				}
				namelist.add(str.trim());
				rank.setValue(namelist.indexOf(user.getKuName())+1+"");
			}
			}
			
		});
		xmmc.addEventListener(Events.ON_CHANGE, new EventListener(){

			public void onEvent(Event arg0) throws Exception {
				initRjWindow(xmmc.getValue().trim(),rjno.getValue().trim());
			}
			
		});
		rjno.addEventListener(Events.ON_CHANGE, new EventListener(){

			public void onEvent(Event arg0) throws Exception {
				initRjWindow(xmmc.getValue().trim(),rjno.getValue().trim());
			}
			
		});
		// �о������б�
		research.initYjfzList(user.getKuId(), null);
		
		if(rjzz != null){
			if(rjzz.getRjName() != null){
				xmmc.setValue(rjzz.getRjName());xmmc.setDisabled(true);	
			}else{
				xmmc.setValue("");	
			}
			if(rjzz.getRjRegisno() != null){
				dengjino.setValue(rjzz.getRjRegisno());
			}else{
				dengjino.setValue("");
			}
			if(rjzz.getRjFirtime() != null){
				firtime.setValue(rjzz.getRjFirtime());
			}else {
				firtime.setValue("");
			}
			if(rjzz.getRjSoftno() != null){
				rjno.setValue(rjzz.getRjSoftno());
			}else {
				rjno.setValue("");
			}
			if(rjzz.getRjTime() != null){
				dengjisj.setValue(rjzz.getRjTime());
			}else {
				dengjisj.setValue("");
			}
			if(rjzz.getRjPerson() != null){
				menbers.setValue(rjzz.getRjPerson());
			}else{
				menbers.setValue("");
			}
			writer.setValue(rjzz.getUser().getKuName());
			
			GhJslw jslw=jslwService.findByKuidAndLwidAndType(user.getKuId(), rjzz.getRjId(), GhJslw.RJZZ);
			if(jslw!=null){
				if(jslw.getLwSelfs() != null){
					rank.setValue(jslw.getLwSelfs()+"");
//					rank.setDisabled(true);
				}else{
					List namelist=new ArrayList();
					String str=menbers.getValue().trim();
					if(str.contains("��")||str.contains(",")){
						throw new WrongValueException(menbers, "��Ŀ����Ա��д������ѡ���Ƕ��ţ�");
					}else{
					while (str.contains("��")) {
							Label lb = new Label(str.substring(0,str.indexOf("��")));
							namelist.add(lb.getValue().trim());
							str = str.substring(str.indexOf("��") + 1, str.length());
						
					}
					namelist.add(str.trim());
					rank.setValue(namelist.indexOf(user.getKuName())+1+"");
//					rank.setDisabled(true);
				}
				}
				
				//�о�����
				research.initYjfzList(user.getKuId(), jslw.getYjId());
			}else{
				research.initYjfzList(user.getKuId(), null);
				List namelist=new ArrayList();
				String str=menbers.getValue().trim();
				if(str.contains("��")||str.contains(",")){
					throw new WrongValueException(menbers, "��Ŀ����Ա��д������ѡ��ٺţ�");
				}else{
				while (str.contains("��")) {
						Label lb = new Label(str.substring(0,str.indexOf("��")));
						namelist.add(lb.getValue().trim());
						str = str.substring(str.indexOf("��") + 1, str.length());
					
				}
				namelist.add(str.trim());
				rank.setValue(namelist.indexOf(user.getKuName())+1+"");
				}
				research.setSelectedIndex(0);
			}
			if(user.getKuId().intValue()!=rjzz.getKuId().intValue()){
				dengjino.setDisabled(true);
				firtime.setDisabled(true);
				rjno.setDisabled(true);
				dengjisj.setDisabled(true);
				menbers.setDisabled(true);
				//�������
				deUpload.setDisabled(true);
				upFile.setDisabled(true);
			}
//			if(rjzz.getAuditState()!=null&&rjzz.getAuditState().shortValue()==GhRjzz.AUDIT_YES){
//				  
//				submit.setDisabled(true);
//				  upFile.setDisabled(true);
//			}
		}else{
			xmmc.setValue("");
			dengjino.setValue("");
			firtime.setValue("");
			rjno.setValue("");
			dengjisj.setValue("");
			menbers.setValue("");
			rank.setValue("0");
			research.initYjfzList(user.getKuId(), null);
			writer.setValue(user.getKuName());
			deUpload.setDisabled(true);
			downFileZip.setDisabled(true);
			downFile.setDisabled(true);
			// ѡ����Ŀ����
//			if (xmmc.getModel().getSize() > 0) {
//				xmmc.addEventListener(Events.ON_SELECT, new EventListener() {
//
//					public void onEvent(Event arg0) throws Exception {
//						if(xmmc.getSelectedItem()!=null){
//						Object [] mc=(Object [])xmmc.getSelectedItem().getValue();
//						GhRjzz zz=(GhRjzz)rjzzService.get(GhRjzz.class, (Long)mc[1]);
//						dengjino.setValue(zz.getRjRegisno()!=null?zz.getRjRegisno():"");dengjino.setDisabled(true);
//						firtime.setValue(zz.getRjFirtime()!=null?zz.getRjFirtime():"");firtime.setDisabled(true);
//						rjno.setValue(zz.getRjSoftno()!=null?zz.getRjSoftno():"");rjno.setDisabled(true);
//						dengjisj.setValue(zz.getRjTime()!=null?zz.getRjTime():"");dengjisj.setDisabled(true);
//						menbers.setValue(zz.getRjPerson()!=null?zz.getRjPerson():"");menbers.setDisabled(true);
//						List namelist=new ArrayList();
//						String str=menbers.getValue().trim();
//						if(str.contains("��")){
//							throw new WrongValueException(menbers, "��Ŀ����Ա��д������ѡ��ٺţ�");
//						}else{
//						while (str.contains("��")) {
//								Label lb = new Label(str.substring(0,str.indexOf("��")));
//								namelist.add(lb.getValue());
//								str = str.substring(str.indexOf("��") + 1, str.length());
//							
//						}
//						namelist.add(str);
//						rank.setValue(namelist.indexOf(user.getKuName())+1+"");
//						rank.setDisabled(true);
//						//�������
//						deUpload.setDisabled(true);
//						upFile.setDisabled(true);
//						List fjList = ghfileService.findByFxmIdandFType(zz.getRjId(), 6);
//						if (fjList.size() == 0) {// �޸���
//							rowFile.setVisible(false);
//							downFileZip.setDisabled(true);
//						} else {// �и���
//							// ��ʼ������
//							downFileZip.setDisabled(false);
//							for (int i = 0; i < fjList.size(); i++) {
//								UploadFJ ufj = new UploadFJ(false);
//								try {
//									ufj.ReadFJ(getDesktop(), (GhFile) fjList.get(i));
//								} catch (InterruptedException e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								}
//								fileModel.add(ufj);
//							}
//							upList.setModel(fileModel);
//							rowFile.setVisible(true);
//						}
//					
//					}
//					}
//					}
//				});
//			}
		}
		// ����
		if (rjzz == null) {// �����
			rowFile.setVisible(false);
			
		} else {// �޸�
			List fjList = ghfileService.findByFxmIdandFType(rjzz.getRjId(), 6);
			if (fjList.size() == 0) {// �޸���
				rowFile.setVisible(false);
				downFileZip.setDisabled(true);
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
	
		//ר������
		if(xmmc.getValue()==null||"".equals(xmmc.getValue().trim())){
			throw new WrongValueException(xmmc, "����û����дר�����ƣ�");
		}
		//�����ǼǺ�
		if(rjno.getValue()==null||"".equals(rjno.getValue().trim())){
			throw new WrongValueException(firtime, "����û����д�����ǼǺţ�");
		}
		//�ǼǺ�
		if(dengjino.getValue()==null||"".equals(dengjino.getValue().trim())){
			throw new WrongValueException(firtime, "����û����д�ǼǺţ�");
		}
		//�Ǽ�ʱ��
		if(dengjisj.getValue()==null||"".equals(dengjisj.getValue().trim())){
			throw new WrongValueException(dengjisj, "����û����дʱ�䣬��ʽ��2008/9/28��2008��2008/9��");
			}else{
			try{
				String str = dengjisj.getValue().trim();
				if(str.length() < 4){
					throw new WrongValueException(dengjisj, "�������ʱ���ʽ�д�������������д����ʽ��2008/9/28��2008��2008/9��");
					
				}
				else if(str.length()==4||'/'==str.charAt(4)){
					String s[] = str.split("/");
					if(s.length==1||s.length==2||s.length==3){
						for(int i=0;i<s.length;i++){
							String si = s[i];
							Integer.parseInt(si);
						}
					}else{
						throw new WrongValueException(dengjisj, "�������ʱ���ʽ�д�������������д����ʽ��2008/9/28��2008��2008/9��");
						
					}
				}else{
					throw new WrongValueException(dengjisj, "�������ʱ���ʽ�д�������������д����ʽ��2008/9/28��2008��2008/9��");
					
				}
			}catch(NumberFormatException e){
				throw new WrongValueException(dengjisj, "�������ʱ���ʽ�д�������������д����ʽ��2008/9/28��2008��2008/9��");
			}
		}
		//�״η���ʱ��
		if(firtime.getValue()==null||"".equals(firtime.getValue().trim())){
			throw new WrongValueException(firtime, "����û����дʱ�䣬��ʽ��2008/9/28��2008��2008/9��");
			}else{
			try{
				String str = dengjisj.getValue().trim();
				if(str.length() < 4){
					throw new WrongValueException(firtime, "�������ʱ���ʽ�д�������������д����ʽ��2008/9/28��2008��2008/9��");
					
				}
				else if(str.length()==4||'/'==str.charAt(4)){
					String s[] = str.split("/");
					if(s.length==1||s.length==2||s.length==3){
						for(int i=0;i<s.length;i++){
							String si = s[i];
							Integer.parseInt(si);
						}
					}else{
						throw new WrongValueException(firtime, "�������ʱ���ʽ�д�������������д����ʽ��2008/9/28��2008��2008/9��");
						
					}
				}else{
					throw new WrongValueException(firtime, "�������ʱ���ʽ�д�������������д����ʽ��2008/9/28��2008��2008/9��");
					
				}
			}catch(NumberFormatException e){
				throw new WrongValueException(firtime, "�������ʱ���ʽ�д�������������д����ʽ��2008/9/28��2008��2008/9��");
			}
		}
	
		//false��ʾ���޸ģ�true��ʾ���½�
		boolean index = false,owner=false; 
		if(rjzz==null){//˵�������½���һ����Ŀ
			rjzz = new GhRjzz();
			index = true;
			if(!dengjisj.isDisabled()){
				owner=true;
			}
		}
//		if(xmmc.getSelectedItem()==null){
//			//û��ѡ����Ŀ���ƣ�����Ŀ���ڸ��û�
//			owner=true;
//		}else{
//			owner=false;
//		}
		
		if(index){
			if(owner){
				if(!menbers.getValue().trim().contains(user.getKuName())){
					Messagebox.show("��Ŀ���Ա����������������", "����", Messagebox.OK, Messagebox.EXCLAMATION);
					rjzz=null;
					return;
				}
				//�����������¼����һ���������Ŀ���ƣ��������������ͽ�ʦ���ı�������ѡ�����˴����ļ�¼��ֻ�����ʦ���ı�
				if(rjzzService.CheckOnlyOne(null, xmmc.getValue().trim(), rjno.getValue().trim())){
					Messagebox.show("��������Ѵ��ڣ�", "����", Messagebox.OK, Messagebox.EXCLAMATION);
					rjzz=null;
					return;
				}
				rjzz.setRjName(xmmc.getValue());
				rjzz.setRjTime(dengjisj.getValue());
				rjzz.setRjFirtime(firtime.getValue());
				rjzz.setRjSoftno(rjno.getValue());
				rjzz.setRjRegisno(dengjino.getValue());
				rjzz.setRjPerson(menbers.getValue());
				rjzz.setKuId(kuid);
				rjzzService.save(rjzz);
				// �����洢
				UploadFJ.ListModelListUploadCommand(fileModel, rjzz.getRjId());
				//
				GhJslw jslw=new GhJslw();
				jslw.setLwzlId(rjzz.getRjId());
				jslw.setJslwtype(GhJslw.RJZZ);
				jslw.setKuId(user.getKuId());
				jslw.setLwSelfs(Integer.parseInt(rank.getValue().trim()));
				jslw.setYjId(((GhYjfx)research.getSelectedItem().getValue()).getGyId());
				jslwService.save(jslw);
			}else{
				if(!menbers.getValue().trim().contains(user.getKuName())){
					Messagebox.show("��Ŀ���Ա����������������", "����", Messagebox.OK, Messagebox.EXCLAMATION);
					rjzz=null;
					return;
				}
				GhJslw jslw=new GhJslw();
				jslw.setLwzlId(rjzz.getRjId());
				jslw.setJslwtype(GhJslw.RJZZ);
				jslw.setKuId(user.getKuId());
				jslw.setLwSelfs(Integer.parseInt(rank.getValue().trim()));
				jslw.setYjId(((GhYjfx)research.getSelectedItem().getValue()).getGyId());
				jslwService.save(jslw);
			}
			Messagebox.show("����ɹ���","��ʾ",Messagebox.OK,Messagebox.INFORMATION);
		}else{
			//�޸ļ�¼����һ�����ڸ��û���������Ŀ���������������ͽ�ʦ���ı����������ڸ��û������ģ�ֻ���½�ʦ���ı�
			if(user.getKuId().intValue()==rjzz.getKuId().intValue()){
				if(rjzzService.CheckOnlyOne(rjzz, xmmc.getValue().trim(), rjno.getValue().trim())){
					Messagebox.show("��������Ѵ��ڣ�", "����", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
				rjzz.setRjTime(dengjisj.getValue());
				rjzz.setRjFirtime(firtime.getValue());
				rjzz.setRjSoftno(rjno.getValue());
				rjzz.setRjRegisno(dengjino.getValue());
				rjzz.setRjPerson(menbers.getValue());
				rjzz.setAuditState(null);
				rjzz.setAuditUid(null);
				rjzz.setReason(null);
				rjzzService.update(rjzz);
				// �����洢
				UploadFJ.ListModelListUploadCommand(fileModel, rjzz.getRjId());
				GhJslw jslw=jslwService.findByKuidAndLwidAndType(user.getKuId(), rjzz.getRjId(), GhJslw.RJZZ);
				if(jslw!=null){
					jslw.setLwSelfs(Integer.parseInt(rank.getValue().trim()));
					jslw.setYjId(((GhYjfx)research.getSelectedItem().getValue()).getGyId());
					jslwService.update(jslw);
				}else{
					jslw=new GhJslw();
					jslw.setLwzlId(rjzz.getRjId());
					jslw.setJslwtype(GhJslw.RJZZ);
					jslw.setKuId(user.getKuId());
					jslw.setLwSelfs(Integer.parseInt(rank.getValue().trim()));
					jslw.setYjId(((GhYjfx)research.getSelectedItem().getValue()).getGyId());
					jslwService.save(jslw);
				}
				Messagebox.show("����ɹ���","��ʾ",Messagebox.OK,Messagebox.INFORMATION);
				
			}else{
				if(!menbers.getValue().trim().contains(user.getKuName())){
					Messagebox.show("��Ŀ���Ա����������������", "����", Messagebox.OK, Messagebox.EXCLAMATION);
					rjzz=null;
					return;
				}
				GhJslw jslw=jslwService.findByKuidAndLwidAndType(user.getKuId(), rjzz.getRjId(), GhJslw.RJZZ);
				if(jslw!=null){
					jslw.setLwSelfs(Integer.parseInt(rank.getValue().trim()));
					jslw.setYjId(((GhYjfx)research.getSelectedItem().getValue()).getGyId());
					jslwService.update(jslw);
				}else{
					jslw=new GhJslw();
					jslw.setLwzlId(rjzz.getRjId());
					jslw.setJslwtype(GhJslw.RJZZ);
					jslw.setKuId(user.getKuId());
					jslw.setLwSelfs(Integer.parseInt(rank.getValue().trim()));
					jslw.setYjId(((GhYjfx)research.getSelectedItem().getValue()).getGyId());
					jslwService.save(jslw);
				}
			}
			Messagebox.show("����ɹ���","��ʾ",Messagebox.OK,Messagebox.INFORMATION);
		}
		Events.postEvent(Events.ON_CHANGE, this, null);
	}
	
	
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		fileModel = new ListModelList(new ArrayList());
		upList.setModel(fileModel);
//		xmmc.setModel(new ListModelList(rjzzService.findByKuidAndUname(user.getKuId(),user.getKuName())));
//		xmmc.setItemRenderer(new ComboitemRenderer(){
//
//			public void render(Comboitem arg0, Object arg1) throws Exception {
//				Object[] name=(Object[])arg1;
//				arg0.setWidth("200px");
//				arg0.setValue(name);
//				arg0.setLabel((String)name[0]);
//			}
//			
//		});
	}
	public void initRjWindow(String mc,String softno){
		GhRjzz Rjzz=rjzzService.findBynameAndSoftno(mc, softno);
		if(Rjzz!=null){
			rjzz=Rjzz;
			dengjino.setValue(rjzz.getRjRegisno()!=null?rjzz.getRjRegisno():"");dengjino.setDisabled(true);
			firtime.setValue(rjzz.getRjFirtime()!=null?rjzz.getRjFirtime():"");firtime.setDisabled(true);
		//	rjno.setValue(rjzz.getRjSoftno()!=null?rjzz.getRjSoftno():"");rjno.setDisabled(true);
			dengjisj.setValue(rjzz.getRjTime()!=null?rjzz.getRjTime():"");dengjisj.setDisabled(true);
			menbers.setValue(rjzz.getRjPerson()!=null?rjzz.getRjPerson():"");menbers.setDisabled(true);
			List namelist=new ArrayList();
			String str=menbers.getValue().trim();
			if(str.contains("��")||str.contains(",")){
				throw new WrongValueException(menbers, "��Ŀ����Ա��д������ѡ��ٺţ�");
			}else{
			while (str.contains("��")) {
					Label lb = new Label(str.substring(0,str.indexOf("��")));
					namelist.add(lb.getValue().trim());
					str = str.substring(str.indexOf("��") + 1, str.length());
				
			}
			namelist.add(str.trim());
			rank.setValue(namelist.indexOf(user.getKuName())+1+"");
			}
			writer.setValue(rjzz.getUser().getKuName());
		}
		else{
			rjzz=null;
			dengjino.setValue("");dengjino.setDisabled(false);
			firtime.setValue("");firtime.setDisabled(false);
//			rjno.setValue("");
			rjno.setDisabled(false);
			dengjisj.setValue("");dengjisj.setDisabled(false);
			menbers.setValue("");menbers.setDisabled(false);
			rank.setValue("0"); writer.setValue(user.getKuName());
		}
	}
	public void onClick$choice(){
		Map args=new HashMap();
    	args.put("user", user);
//    	args.put("lx", GhCg.CG_JY);
//    	args.put("type", GhJsxm.HJJY);
    	SelectRjWindow srw=(SelectRjWindow)Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/rjzz/selectrj.zul", null, args);
    	srw.initShow();
    	srw.doHighlighted();
    	srw.addEventListener(Events.ON_CHANGE, new EventListener(){

			public void onEvent(Event arg0) throws Exception {
				SelectRjWindow srw=(SelectRjWindow)arg0.getTarget();
				if(srw.getRjlist().getSelectedItem()!=null){
					GhRjzz  zz=(GhRjzz)srw.getRjlist().getSelectedItem().getValue();
					if(!zz.getRjPerson().contains(user.getKuName())){
						Messagebox.show("��������Ŀ���Ա������ϵ��Ŀ��д��������룡", "��ʾ��", Messagebox.OK, Messagebox.EXCLAMATION);
					}
					else{
					rjzz= zz;
					initWindow();
//					dengjino.setValue(zz.getRjRegisno()!=null?zz.getRjRegisno():"");dengjino.setDisabled(true);
//					firtime.setValue(zz.getRjFirtime()!=null?zz.getRjFirtime():"");firtime.setDisabled(true);
//					rjno.setValue(zz.getRjSoftno()!=null?zz.getRjSoftno():"");rjno.setDisabled(true);
//					dengjisj.setValue(zz.getRjTime()!=null?zz.getRjTime():"");dengjisj.setDisabled(true);
//					menbers.setValue(zz.getRjPerson()!=null?zz.getRjPerson():"");menbers.setDisabled(true);
//					List namelist=new ArrayList();
//					String str=menbers.getValue().trim();
//					if(str.contains("��")){
//						throw new WrongValueException(menbers, "��Ŀ����Ա��д������ѡ��ٺţ�");
//					}else{
//					while (str.contains("��")) {
//							Label lb = new Label(str.substring(0,str.indexOf("��")));
//							namelist.add(lb.getValue());
//							str = str.substring(str.indexOf("��") + 1, str.length());
//						
//					}
//					namelist.add(str);
//					rank.setValue(namelist.indexOf(user.getKuName())+1+"");
//					writer.setValue(rjzz.getUser().getKuName());
//					} 	 
					}
				srw.detach();
				}}
			});
	}
	public void onClick$upFile() throws InterruptedException {
		/*Object media = Fileupload.get();
		if (media == null) {
			return;
		}
		rowFile.setVisible(true);
		fileModel.add(media);*/
		UploadFJ ufj = new UploadFJ(true);
		Media media = ufj.Upload(this.getDesktop(),6, 10, "�������ܳ���10MB",
				"�����������------�������ܳ���10MB");
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
	//������ظ���
	public void onClick$downFileZip(){
		DownloadFJ.ListModelListDownloadCommand(this.getDesktop(), this.rjzz.getRjId(), fileModel);
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
	public GhRjzz getRjzz() {
		return rjzz;
	}

	public void setRjzz(GhRjzz rjzz) {
		this.rjzz = rjzz;
	}

}
