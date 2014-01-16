package com.uniwin.asm.personal.ui.data.teacherinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iti.gh.entity.GhFile;
import org.iti.gh.entity.GhFmzl;
import org.iti.gh.entity.GhJslw;
import org.iti.gh.entity.GhYjfx;
import org.iti.gh.service.FmzlService;
import org.iti.gh.service.GhFileService;
import org.iti.gh.service.JslwService;
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
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.api.Intbox;

import com.uniwin.asm.personal.ui.data.teacherinfo.jyqk.SelectJcWindow;
import com.uniwin.asm.personal.ui.data.teacherinfo.kyqk.SelectFmWindow;
import com.uniwin.framework.common.fileuploadex.DownloadFJ;
import com.uniwin.framework.common.fileuploadex.IsZipExists;
import com.uniwin.framework.common.fileuploadex.UploadFJ;
import com.uniwin.framework.entity.WkTUser;

public class FmzlqkWindow extends FrameworkWindow {

	Textbox  shijian, kanwu, country, requesino, requestdate, reqpublino,inventor;
	Textbox cgmc;
	Label rank,writer;
	Listbox types;
	YjfxListbox research;
	private Radiogroup status;
	GhFmzl fm;
	Long kuid;
	// �ݴ渽��
	private Row rowFile;
	Listbox upList;
	ListModelList fileModel;

	FmzlService fmzlService;
	YjfxService yjfxService;
	GhFileService ghfileService;
	JslwService jslwService;
	WkTUser user;
	Button downFile,deUpload,upFile,downFileZip,submit;

	@Override
	public void initShow() {

	}

	@SuppressWarnings("unchecked")
	@Override
	public void initWindow() {
		List fmtype = new ArrayList();
		List fx = new ArrayList();
		String[] Fmtype = { "-��ѡ��-", "����", "ʵ������", "������" };
		for (int i = 0; i < Fmtype.length; i++) {
			fmtype.add(Fmtype[i]);
		}
		types.setModel(new ListModelList(fmtype));
		// �о������б�
		research.initYjfzList(user.getKuId(), null);
		inventor.addEventListener(Events.ON_CHANGE, new EventListener(){

			public void onEvent(Event arg0) throws Exception {
				List namelist=new ArrayList();
				String str=inventor.getValue().trim();
				if(str.contains("��")||str.contains(",")){
					throw new WrongValueException(inventor, "��Ŀ����Ա��д������ѡ��ٺţ�");
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
		
		if (fm != null) {
			// ����
			cgmc.setValue(fm.getFmMc());cgmc.setDisabled(true);
			writer.setValue(fm.getUser().getKuName());
			// ��Ȩʱ��
			if (fm.getFmSj() != null) {
				shijian.setValue(fm.getFmSj());
			} else {
				shijian.setValue("");
			}

			// ����Ŀ���
			if (fm.getFmSqh() != null) {
				kanwu.setValue(fm.getFmSqh());
			} else {
				kanwu.setValue("");
			}
			// ����
			if (fm.getFmCountry() != null) {
				country.setValue(fm.getFmCountry());
			} else {
				country.setValue("");
			}
			// �����
			if (fm.getFmRequestno() != null) {
				requesino.setValue(fm.getFmRequestno());
			} else {
				requesino.setValue("");
			}
			if (fm.getFmRequestdate() != null) {
				requestdate.setValue(fm.getFmRequestdate());
			} else {
				requestdate.setValue("");
			}
			if (fm.getFmReqpublino() != null) {
				reqpublino.setValue(fm.getFmReqpublino());
			} else {
				reqpublino.setValue("");
			}
			if (fm.getFmInventor() != null) {
				inventor.setValue(fm.getFmInventor());
			} else {
				inventor.setValue("");
			}
			

			if (fm.getFmTypes() == null || fm.getFmTypes() == "") {
				types.setSelectedIndex(0);
			} else {
				types.setSelectedIndex(Integer.valueOf(fm.getFmTypes().trim()));
			}
			if (fm.getFmStatus().shortValue() == (GhFmzl.STA_NO.shortValue())) { // �Ƿ���Чר��
				status.setSelectedIndex(0);
			} else {
				status.setSelectedIndex(1);
			}
			GhJslw jslw=jslwService.findByKuidAndLwidAndType(user.getKuId(), fm.getFmId(), GhJslw.FMZL);
			if(jslw!=null){
				//��������
				if (jslw.getLwSelfs() != null) {
					rank.setValue(jslw.getLwSelfs()+"");
				} else {
					List namelist=new ArrayList();
					String str=inventor.getValue().trim();
					if(str.contains("��")||str.contains(",")){
						throw new WrongValueException(inventor, "��Ŀ����Ա��д������ѡ��ٺţ�");
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
				// �о�����
				research.initYjfzList(kuid, jslw.getYjId());
				
			}else{
				List namelist=new ArrayList();
				String str=inventor.getValue().trim();
				if(str.contains("��")||str.contains(",")){
					throw new WrongValueException(inventor, "��Ŀ����Ա��д������ѡ���Ƕٺţ�");
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
			if(user.getKuId().intValue()!=fm.getKuId().intValue()){
				shijian.setDisabled(true); kanwu.setDisabled(true); country.setDisabled(true);
				requesino.setDisabled(true);requestdate.setDisabled(true); reqpublino.setDisabled(true);
				inventor.setDisabled(true);types.setDisabled(true);
				//�������
				deUpload.setDisabled(true);
				upFile.setDisabled(true);
			} 
//			if(fm.getAuditState()!=null&&fm.getAuditState().shortValue()==GhFmzl.AUDIT_YES){
//				 submit.setDisabled(true);
//		    	 upFile.setDisabled(true);
//			}
		} else {
			cgmc.addEventListener(Events.ON_CHANGE, new EventListener(){

				public void onEvent(Event arg0) throws Exception {
					initFmWindow(cgmc.getValue().trim());
				}
			});
			cgmc.setValue("");
			shijian.setValue("");
			kanwu.setValue("");
			country.setValue("");
			requesino.setValue("");
			requestdate.setValue("");
			reqpublino.setValue("");
			inventor.setValue("");
			rank.setValue(null);
			deUpload.setDisabled(true);
			downFileZip.setDisabled(true);
			downFile.setDisabled(true);
			writer.setValue(user.getKuName());
//			if(cgmc.getModel().getSize()>0){
//			cgmc.addEventListener(Events.ON_SELECT, new EventListener() {
//
//					public void onEvent(Event arg0) throws Exception {
//						if(cgmc.getSelectedItem()!=null){
//						Object[] mc = (Object[]) cgmc.getSelectedItem()
//								.getValue();
//						GhFmzl fm = (GhFmzl) fmzlService.get(GhFmzl.class,(Long) mc[1]);
//						shijian.setDisabled(true);
//						kanwu.setDisabled(true);
//						country.setDisabled(true);
//						requesino.setDisabled(true);
//						requestdate.setDisabled(true);
//						reqpublino.setDisabled(true);
//						inventor.setDisabled(true);
//						types.setDisabled(true);
//						// ��Ȩʱ��
//						if (fm.getFmSj() != null) {
//							shijian.setValue(fm.getFmSj());
//						} else {
//							shijian.setValue("");
//						}
//
//						// ����Ŀ���
//						if (fm.getFmSqh() != null) {
//							kanwu.setValue(fm.getFmSqh());
//						} else {
//							kanwu.setValue("");
//						}
//						// ����
//						if (fm.getFmCountry() != null) {
//							country.setValue(fm.getFmCountry());
//						} else {
//							country.setValue("");
//						}
//						// �����
//						if (fm.getFmRequestno() != null) {
//							requesino.setValue(fm.getFmRequestno());
//						} else {
//							requesino.setValue("");
//						}
//						if (fm.getFmRequestdate() != null) {
//							requestdate.setValue(fm.getFmRequestdate());
//						} else {
//							requestdate.setValue("");
//						}
//						if (fm.getFmReqpublino() != null) {
//							reqpublino.setValue(fm.getFmReqpublino());
//						} else {
//							reqpublino.setValue("");
//						}
//						if (fm.getFmInventor() != null) {
//							inventor.setValue(fm.getFmInventor());
//						} else {
//							inventor.setValue("");
//						}
//						if (fm.getFmTypes() == null || fm.getFmTypes() == "") {
//							types.setSelectedIndex(0);
//						} else {
//							types.setSelectedIndex(Integer.valueOf(fm.getFmTypes().trim()));
//						}
//						if (fm.getFmStatus().shortValue() == (GhFmzl.STA_NO.shortValue())) { // �Ƿ���Чר��
//							status.setSelectedIndex(0);
//						} else {
//							status.setSelectedIndex(1);
//						}
//						List namelist = new ArrayList();
//						String str = inventor.getValue().trim();
//						if (str.contains("��")) {
//							throw new WrongValueException(inventor,
//									"��Ŀ����Ա��д������ѡ���Ƕ��ţ�");
//						} else {
//							while (str.contains(",")) {
//								Label lb = new Label(str.substring(0, str.indexOf(",")));
//								namelist.add(lb.getValue());
//								str = str.substring(str.indexOf(",") + 1, str.length());
//
//							}
//							namelist.add(str);
//							rank.setValue(namelist.indexOf(user.getKuName()) + 1);
//							rank.setDisabled(true);
//						}
//						deUpload.setDisabled(true);upFile.setDisabled(true);
//						List fjList = ghfileService.findByFxmIdandFType(fm.getFmId(), 7);
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
//								 
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
//				});
//			}
		}
		// ����
		if (fm == null) {// �����
			rowFile.setVisible(false);
		} else {// �޸�
			List fjList = ghfileService.findByFxmIdandFType(fm.getFmId(), 7);
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
						e.printStackTrace();
					}
					fileModel.add(ufj);
				}
				upList.setModel(fileModel);
				rowFile.setVisible(true);
			}
		}
	}

	public void onClick$submit() throws InterruptedException {
		// ר������
		if (cgmc.getValue() == null || "".equals(cgmc.getValue().trim())) {
			throw new WrongValueException(cgmc, "����û����д���ƣ�");
		}
		// ��Ȩʱ��
		if (shijian.getValue() == null || "".equals(shijian.getValue().trim())) {
			throw new WrongValueException(shijian,
					"����û����дʱ�䣬��ʽ��2008/9/28��2008��2008/9��");
		} else {
			try {
				String str = shijian.getValue().trim();
				if (str.length() < 4) {
					throw new WrongValueException(shijian,
							"�������ʱ���ʽ�д�������������д����ʽ��2008/9/28��2008��2008/9��");

				} else if (str.length() == 4 || '/' == str.charAt(4)) {
					String s[] = str.split("/");
					if (s.length == 1 || s.length == 2 || s.length == 3) {
						for (int i = 0; i < s.length; i++) {
							String si = s[i];
							Integer.parseInt(si);
						}
					} else {
						throw new WrongValueException(shijian,
								"�������ʱ���ʽ�д�������������д����ʽ��2008/9/28��2008��2008/9��");

					}
				} else {
					throw new WrongValueException(shijian,
							"�������ʱ���ʽ�д�������������д����ʽ��2008/9/28��2008��2008/9��");

				}
			} catch (NumberFormatException e) {
				throw new WrongValueException(shijian,
						"�������ʱ���ʽ�д�������������д����ʽ��2008/9/28��2008��2008/9��");
			}
		}

		// ר����Ȩ��
		if (kanwu.getValue() == null || "".equals(kanwu.getValue().trim())) {
			throw new WrongValueException(kanwu, "����û����дר����Ȩ�ţ�");
		}
		boolean index = false,owner=false;  // false��ʾ���޸ģ�true��ʾ���½�
		if (fm == null) {// ˵�������½���һ����Ŀ
			fm = new GhFmzl();
			index = true;
		}
		if(!shijian.isDisabled()){
			//û��ѡ����Ŀ���ƣ�����Ŀ���ڸ��û�
			owner=true;
		}else{
			owner=false;
		}
		//
		if (index) {
			if(owner){
				if(!inventor.getValue().contains(user.getKuName())){
					Messagebox.show("�����˲�����������������������ӣ�","����",Messagebox.OK,Messagebox.EXCLAMATION);
					fm=null;
					return;
				}
				if(fmzlService.CheckOnlyOne(null, cgmc.getValue().trim(), null)){
					Messagebox.show("���ύ�ķ�����Ϣ�Ѵ��ڣ��������ظ���ӣ�","����",Messagebox.OK,Messagebox.EXCLAMATION);
					fm=null;
					return;
				}
				fm.setFmMc(cgmc.getValue());
				fm.setFmSj(shijian.getValue());
				fm.setFmSqh(kanwu.getValue());
				fm.setFmCountry(country.getValue());
				fm.setFmRequestno(requesino.getValue());
				fm.setFmRequestdate(requestdate.getValue());
				fm.setFmReqpublino(reqpublino.getValue());
				fm.setFmInventor(inventor.getValue());
//				fm.setFmRank(rank.getValue());
				fm.setFmTypes(String.valueOf(types.getSelectedIndex()));
//				fm.setYjId(String.valueOf(research.getSelectedIndex()));
				if (status.getSelectedIndex() == 0) {
					fm.setFmStatus(GhFmzl.STA_NO.shortValue());// ��Чר��
				} else {
					fm.setFmStatus(GhFmzl.STA_YES.shortValue());// ��Чר��
				}
				fm.setKuId(kuid);
				fmzlService.save(fm);
				// �����洢
				UploadFJ.ListModelListUploadCommand(fileModel, fm.getFmId());
				GhJslw jslw=new GhJslw();
				jslw.setLwzlId(fm.getFmId());
				jslw.setJslwtype(GhJslw.FMZL);
				jslw.setKuId(user.getKuId());
				jslw.setLwSelfs(Integer.parseInt(rank.getValue()));
				jslw.setYjId(((GhYjfx)research.getSelectedItem().getValue()).getGyId());
				jslwService.save(jslw);
				Messagebox.show("����ɹ���","��ʾ",Messagebox.OK,Messagebox.INFORMATION);
			}else{
				if(!inventor.getValue().contains(user.getKuName())){
					Messagebox.show("�����˲�����������������������ӣ�","����",Messagebox.OK,Messagebox.EXCLAMATION);
					fm=null;
					return;
				}
				GhJslw jslw=new GhJslw();
				jslw.setLwzlId(fm.getFmId());
				jslw.setJslwtype(GhJslw.FMZL);
				jslw.setKuId(user.getKuId());
				jslw.setLwSelfs(Integer.parseInt(rank.getValue()));
				jslw.setYjId(((GhYjfx)research.getSelectedItem().getValue()).getGyId());
				jslwService.save(jslw);
				Messagebox.show("����ɹ���","��ʾ",Messagebox.OK,Messagebox.INFORMATION);
			}
			Events.postEvent(Events.ON_CHANGE, this, null);
			
		} else {
			if(fm.getKuId()!=null&&!fm.getKuId().equals("")){
		
				if(user.getKuId()!=fm.getKuId()){//ֻ���½�ʦ���ı�
					GhJslw jslw=jslwService.findByKuidAndLwidAndType(user.getKuId(), fm.getFmId(), GhJslw.FMZL);
					if(jslw!=null){
						jslw.setLwSelfs(Integer.parseInt(rank.getValue()));
						jslw.setYjId(((GhYjfx)research.getSelectedItem().getValue()).getGyId());
						jslwService.update(jslw);
					}else{
						jslw=new GhJslw();
						jslw.setLwzlId(fm.getFmId());
						jslw.setJslwtype(GhJslw.FMZL);
						jslw.setKuId(user.getKuId());
						jslw.setLwSelfs(Integer.parseInt(rank.getValue()));
						jslw.setYjId(((GhYjfx)research.getSelectedItem().getValue()).getGyId());
						jslwService.save(jslw);
					}
					Messagebox.show("����ɹ���","��ʾ",Messagebox.OK,Messagebox.INFORMATION);
				}else{
					if(!inventor.getValue().contains(user.getKuName())){
						Messagebox.show("�����˲�����������������������ӣ�","����",Messagebox.OK,Messagebox.EXCLAMATION);
						return;
					}
	//				ͬʱ���·���ר����ͽ�ʦ���ı�
					if(fmzlService.CheckOnlyOne(fm, cgmc.getValue().trim(), null)){
						Messagebox.show("���ύ�ķ�����Ϣ�Ѵ��ڣ�������Ϣ�����Ա��棡","����",Messagebox.OK,Messagebox.EXCLAMATION);
						return;
					}
					fm.setFmSj(shijian.getValue());
					fm.setFmSqh(kanwu.getValue());
					fm.setFmCountry(country.getValue());
					fm.setFmRequestno(requesino.getValue());
					fm.setFmRequestdate(requestdate.getValue());
					fm.setFmReqpublino(reqpublino.getValue());
					fm.setFmInventor(inventor.getValue());
					fm.setFmTypes(String.valueOf(types.getSelectedIndex()));
					if (status.getSelectedIndex() == 0) {
						fm.setFmStatus(GhFmzl.STA_NO.shortValue());// ��Чר��
					} else {
						fm.setFmStatus(GhFmzl.STA_YES.shortValue());// ��Чר��
					}
					fm.setAuditState(null);
					fm.setAuditUid(null);
					fm.setReason(null);
					fmzlService.update(fm);
					List jslwlist=jslwService.findByLwidAndType(fm.getFmId(), GhJslw.FMZL);
					if(jslwlist!=null&&jslwlist.size()>0){
						for(int i=0;i<jslwlist.size();i++){
							GhJslw jslw=(GhJslw)jslwlist.get(i);
							jslwService.delete(jslw);
						}
					}
					// �����洢
					UploadFJ.ListModelListUploadCommand(fileModel, fm.getFmId());
					GhJslw jslw=jslwService.findByKuidAndLwidAndType(user.getKuId(), fm.getFmId(), GhJslw.FMZL);
					if(jslw!=null){
						jslw.setLwSelfs(Integer.parseInt(rank.getValue()));
						jslw.setYjId(((GhYjfx)research.getSelectedItem().getValue()).getGyId());
						jslwService.update(jslw);
					}else{
						jslw=new GhJslw();
						jslw.setLwzlId(fm.getFmId());
						jslw.setJslwtype(GhJslw.FMZL);
						jslw.setKuId(user.getKuId());
						jslw.setLwSelfs(Integer.parseInt(rank.getValue()));
						jslw.setYjId(((GhYjfx)research.getSelectedItem().getValue()).getGyId());
						jslwService.save(jslw);
					}
					Messagebox.show("����ɹ���","��ʾ",Messagebox.OK,Messagebox.INFORMATION);
				}
				Events.postEvent(Events.ON_CHANGE, this, null);
			}
		}
	}

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		fileModel = new ListModelList(new ArrayList());
		upList.setModel(fileModel);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
//		cgmc.setModel(new ListModelList(fmzlService.findByKuidAndUname(user.getKuId(),user.getKuName())));
//		cgmc.setItemRenderer(new ComboitemRenderer(){
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
	public void initFmWindow(String fmname){
		GhFmzl Fmzl=fmzlService.finByFmmc(fmname);
		if(Fmzl!=null){
			fm=Fmzl;
			cgmc.setDisabled(true);
			shijian.setDisabled(true);
			kanwu.setDisabled(true);
			country.setDisabled(true);
			requesino.setDisabled(true);
			requestdate.setDisabled(true);
			reqpublino.setDisabled(true);
			inventor.setDisabled(true);
			types.setDisabled(true);
			// ��Ȩʱ��
			if (fm.getFmSj() != null) {
				shijian.setValue(fm.getFmSj());
			} else {
				shijian.setValue("");
			}

			// ����Ŀ���
			if (fm.getFmSqh() != null) {
				kanwu.setValue(fm.getFmSqh());
			} else {
				kanwu.setValue("");
			}
			// ����
			if (fm.getFmCountry() != null) {
				country.setValue(fm.getFmCountry());
			} else {
				country.setValue("");
			}
			// �����
			if (fm.getFmRequestno() != null) {
				requesino.setValue(fm.getFmRequestno());
			} else {
				requesino.setValue("");
			}
			if (fm.getFmRequestdate() != null) {
				requestdate.setValue(fm.getFmRequestdate());
			} else {
				requestdate.setValue("");
			}
			if (fm.getFmReqpublino() != null) {
				reqpublino.setValue(fm.getFmReqpublino());
			} else {
				reqpublino.setValue("");
			}
			if (fm.getFmInventor() != null) {
				inventor.setValue(fm.getFmInventor());
			} else {
				inventor.setValue("");
			}
			if (fm.getFmTypes() == null || fm.getFmTypes() == "") {
				types.setSelectedIndex(0);types.setDisabled(true);
			} else {
				types.setSelectedIndex(Integer.valueOf(fm.getFmTypes().trim()));
			}
			if (fm.getFmStatus().shortValue() == (GhFmzl.STA_NO.shortValue())) { // �Ƿ���Чר��
				status.setSelectedIndex(0);
			} else {
				status.setSelectedIndex(1);
			}
			List namelist = new ArrayList();
			String str = inventor.getValue().trim();
			if (str.contains("��")||str.contains(",")) {
				throw new WrongValueException(inventor,
						"��Ŀ����Ա��д������ѡ��ٺţ�");
			} else {
				while (str.contains("��")) {
					Label lb = new Label(str.substring(0, str.indexOf("��")));
					namelist.add(lb.getValue().trim());
					str = str.substring(str.indexOf("��") + 1, str.length());

				}
				namelist.add(str.trim());
				rank.setValue(namelist.indexOf(user.getKuName()) + 1+"");
			}
			
		}else{
			fm=null;
//			cgmc.setValue("");
			/*shijian.setValue("");shijian.setDisabled(false);
			kanwu.setValue("");kanwu.setDisabled(false);
			country.setValue("");country.setDisabled(false);
			requesino.setValue("");requesino.setDisabled(false);
			requestdate.setValue("");requestdate.setDisabled(false);
			reqpublino.setValue("");reqpublino.setDisabled(false);
			inventor.setValue("");inventor.setDisabled(false);
			types.setSelectedIndex(0);types.setDisabled(false);
			rank.setValue(null);
			deUpload.setDisabled(true);
			downFileZip.setDisabled(true);
			downFile.setDisabled(true);*/
 
		}
	}
	
	public void onClick$choice(){
		Map arg=new HashMap();
		arg.put("user", user);
		SelectFmWindow sfw=(SelectFmWindow)Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/fmzl/selectfm.zul", null, arg);
		sfw.doHighlighted();
		sfw.initWindow();
		sfw.addEventListener(Events.ON_CHANGE,  new EventListener(){

			public void onEvent(Event arg0) throws Exception {
				SelectFmWindow sfw=(SelectFmWindow)arg0.getTarget();
				if(sfw.getXmlist().getSelectedItem()!=null){
					GhFmzl fmzl=(GhFmzl)sfw.getXmlist().getSelectedItem().getValue();
					if(!fmzl.getFmInventor().contains(user.getKuName())){
						Messagebox.show("��������Ŀ���Ա������ϵ��Ŀ��д��������룡", "��ʾ��", Messagebox.OK, Messagebox.EXCLAMATION);
					}
					else{
					fm=fmzl;
					cgmc.setValue(fm.getFmMc());
					cgmc.setDisabled(true);
					shijian.setDisabled(true);
					kanwu.setDisabled(true);
					country.setDisabled(true);
					requesino.setDisabled(true);
					requestdate.setDisabled(true);
					reqpublino.setDisabled(true);
					inventor.setDisabled(true);
					types.setDisabled(true);
					// ��Ȩʱ��
					if (fm.getFmSj() != null) {
						shijian.setValue(fm.getFmSj());
					} else {
						shijian.setValue("");
					}

					// ����Ŀ���
					if (fm.getFmSqh() != null) {
						kanwu.setValue(fm.getFmSqh());
					} else {
						kanwu.setValue("");
					}
					// ����
					if (fm.getFmCountry() != null) {
						country.setValue(fm.getFmCountry());
					} else {
						country.setValue("");
					}
					// �����
					if (fm.getFmRequestno() != null) {
						requesino.setValue(fm.getFmRequestno());
					} else {
						requesino.setValue("");
					}
					if (fm.getFmRequestdate() != null) {
						requestdate.setValue(fm.getFmRequestdate());
					} else {
						requestdate.setValue("");
					}
					if (fm.getFmReqpublino() != null) {
						reqpublino.setValue(fm.getFmReqpublino());
					} else {
						reqpublino.setValue("");
					}
					if (fm.getFmInventor() != null) {
						inventor.setValue(fm.getFmInventor());
					} else {
						inventor.setValue("");
					}
					if (fm.getFmTypes() == null || fm.getFmTypes() == "") {
						types.setSelectedIndex(0);
					} else {
						types.setSelectedIndex(Integer.valueOf(fm.getFmTypes().trim()));
					}
					if (fm.getFmStatus().shortValue() == (GhFmzl.STA_NO.shortValue())) { // �Ƿ���Чר��
						status.setSelectedIndex(0);
					} else {
						status.setSelectedIndex(1);
					}
					List namelist = new ArrayList();
					String str = inventor.getValue().trim();
					if (str.contains("��")||str.contains(",")) {
						throw new WrongValueException(inventor,
								"��Ŀ����Ա��д������ѡ��ٺţ�");
					} else {
						while (str.contains("��")) {
							Label lb = new Label(str.substring(0, str.indexOf("��")));
							namelist.add(lb.getValue().trim());
							str = str.substring(str.indexOf("��") + 1, str.length());

						}
						namelist.add(str.trim());
						rank.setValue(namelist.indexOf(user.getKuName()) + 1+"");
					}
					sfw.detach();
				}
				}}});
	}
	// 2010-1104
	// magicube

	public void onClick$upFile() throws InterruptedException {
		UploadFJ ufj = new UploadFJ(true);
		Media media = ufj.Upload(this.getDesktop(), 7, 10, "�������ܳ���10MB",
				"����Ȩ����ר������------�������ܳ���10MB");
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
			//
			temp.DeleteFJ();
			//
			fileModel.remove(temp);
			rowFile.setVisible(false);
		} else if (fileModel.getSize() > 1) {
			//
			temp.DeleteFJ();
			//
			fileModel.remove(temp);
		}
	}
	
	
	//������ظ���
	public void onClick$downFileZip(){
		
		/*//���� word
		DocumentImpl doc=new DocumentImpl();
		doc.CreateDocument(1l, null, this.getDesktop().getWebApp().getRealPath("/upload/word/aaa.docx"),this.getDesktop().getWebApp().getRealPath("/upload/"), kuid);
		//
*/		
		
		
		DownloadFJ.ListModelListDownloadCommand(this.getDesktop(), this.fm.getFmId(), fileModel);
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

	public void onClick$close() {
		this.detach();
	}

	public Long getKuid() {
		return kuid;
	}

	public void setKuid(Long kuid) {
		this.kuid = kuid;
	}

	public GhFmzl getFm() {
		return fm;
	}

	public void setFm(GhFmzl fm) {
		this.fm = fm;
	}

}
