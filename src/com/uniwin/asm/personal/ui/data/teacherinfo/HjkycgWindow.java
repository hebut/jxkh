package com.uniwin.asm.personal.ui.data.teacherinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iti.gh.entity.GhCg;
import org.iti.gh.entity.GhFile;
import org.iti.gh.entity.GhJsxm;
import org.iti.gh.entity.GhXm;
import org.iti.gh.service.CgService;
import org.iti.gh.service.GhFileService;
import org.iti.gh.service.JsxmService;
import org.iti.gh.service.XmService;
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
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

import com.uniwin.asm.personal.ui.data.teacherinfo.kyqk.SelectcgWindow;
import com.uniwin.common.util.DateUtil;
import com.uniwin.framework.entity.WkTUser;

import com.uniwin.framework.common.fileuploadex.DownloadFJ;
import com.uniwin.framework.common.fileuploadex.IsZipExists;
import com.uniwin.framework.common.fileuploadex.UploadFJ;
public class HjkycgWindow extends FrameworkWindow{

	
	Combobox dengji,ly,hjmc,prizedep;
	Label writer,mingci;
	Textbox prizenum,prizepersons;
	Listbox cdrw,jb;
	Textbox time, number;
	Textbox cgmc;
	GhCg cg;
	Long kuid;
	//�ݴ渽��
	private Row rowFile;
	Listbox upList;
	ListModelList fileModel;
	JsxmService jsxmService;
	XmService xmService;
	CgService cgService;
	WkTUser user;
	GhFileService ghfileService;
	Button downFile,deUpload,upFile,downFileZip,submit;
	@Override
	public void initShow() {
		
	}

	@Override
	public void initWindow() {
		
		String[] Subcdrw = {"-��ѡ��-","����","�μ�","����"};
		List subcdrw = new ArrayList();
		for(int i = 0;i < Subcdrw.length; i++){
			subcdrw.add(Subcdrw[i]);
		}
		//���˳е����������
		cdrw.setModel(new ListModelList(subcdrw));
		String[] jbn={"-��ѡ��-","���Ҽ�","ʡ����","����"};
		List jblist=new ArrayList();
		for(int i=0;i<jbn.length;i++){
			jblist.add(jbn[i]);
		}
		jb.setModel(new ListModelList(jblist));
		prizepersons.addEventListener(Events.ON_CHANGE, new EventListener(){

			public void onEvent(Event arg0) throws Exception {
				List namelist=new ArrayList();
				String str=prizepersons.getValue().trim();
				if(str.contains("��")||str.contains(",")){
					throw new WrongValueException(prizepersons, "ȫ��������д������ѡ��ٺţ�");
				}else{
				while (str.contains("��")) {
						Label lb = new Label(str.substring(0,str.indexOf("��")));
						namelist.add(lb.getValue().trim());
						str = str.substring(str.indexOf("��") + 1, str.length());
					
				}
				namelist.add(str.trim());
				mingci.setValue(namelist.indexOf(user.getKuName())+1+"/"+namelist.size());
			}
			}
		});
//		cgmc.addEventListener(Events.ON_CHANGE, new EventListener(){
//
//			public void onEvent(Event arg0) throws Exception {
//				initCgWindow(cgmc.getValue().trim(),ly.getValue().trim(),GhCg.CG_KY);
//			}
//			
//		});
//		ly.addEventListener(Events.ON_CHANGE, new EventListener(){
//
//			public void onEvent(Event arg0) throws Exception {
//				initCgWindow(cgmc.getValue().trim(),ly.getValue().trim(),GhCg.CG_KY);
//			}
//			
//		});
		if(cg!=null){
			//��Ŀ���
			if(cg.getKyNumber() != null){
				number.setValue(cg.getKyNumber()+"");
			}else {
				number.setValue("");
			}
			//����
			if(cg.getKyMc() != null){
				cgmc.setValue(cg.getKyMc());
				cgmc.setDisabled(true);
			}else {
				cgmc.setValue("");
			}
			//��Դ
			if(cg.getKyLy()!=null){
				ly.setValue(cg.getKyLy());
			}else{
				ly.setValue("");
			}
			
			//��ʱ��
			if(cg.getKySj()!=null){
				time.setValue(cg.getKySj());
				
			}
			//�񽱵ȼ�
			if(cg.getKyDj()!=null){
				dengji.setValue(cg.getKyDj());
			}else{
				dengji.setValue("");
			}
			//������
			if(cg.getKyHjmc()!=null){
				hjmc.setValue(cg.getKyHjmc());
			}else{
				hjmc.setValue("");
			}
			//��������
			List namelist=new ArrayList();
			String str=cg.getKyPrizeper().trim();
			while (str.contains("��")) {
				Label lb = new Label(str.substring(0,str.indexOf("��")));
				namelist.add(lb.getValue().trim());
				str = str.substring(str.indexOf("��") + 1, str.length());
			}
			namelist.add(str.trim());
			mingci.setValue(namelist.indexOf(user.getKuName())+1+"/"+namelist.size());
//			if(cg.getKyGrpm()!=null){
//				mingci.setValue(cg.getKyGrpm()+"/"+cg.getKyZrs());
//			}else{
//				mingci.setValue("0/0");
//			}
			
			//���˳е�������������Խ�ʦ��Ŀ��
			GhJsxm jsxm=jsxmService.findByXmidAndKuidAndType(cg.getKyId(), user.getKuId(), GhJsxm.HjKY);
			if (jsxm != null&&jsxm.getKyCdrw() != null) {
				System.out.println("dadadsa"+cg.getKyId()+""+jsxm.getJsxmId());
				cdrw.setSelectedIndex(Integer.valueOf(jsxm.getKyCdrw().trim()));
			} else {
				cdrw.setSelectedIndex(0);
			}
		
			if(cg.getKyJb()!=null&&cg.getKyJb().shortValue()==GhCg.CG_GJ){
				jb.setSelectedIndex(1);
			}else if(cg.getKyJb()!=null&&cg.getKyJb().shortValue()==GhCg.CG_SB){
				jb.setSelectedIndex(2);
			}else if(cg.getKyJb()!=null&&cg.getKyJb().shortValue()==GhCg.CG_QT){
				jb.setSelectedIndex(3);
			}else{
				jb.setSelectedIndex(0);
			}
			
			//֤����
			if(cg.getKyPrizenum() != null){
				prizenum.setValue(cg.getKyPrizenum());	
			}else {
				prizenum.setValue("");	
			}
			if(cg.getKyPrizedep() != null){
				prizedep.setValue(cg.getKyPrizedep());
			}else{
				prizedep.setValue("");
			}
			if(cg.getKyPrizeper() != null){
				prizepersons.setValue(cg.getKyPrizeper());	
			}else{
				prizepersons.setValue("");	
			}
			writer.setValue(cg.getUser().getKuName());
			if(user.getKuId().intValue()!=cg.getKuId().intValue()){
				ly.setDisabled(true);time.setDisabled(true); dengji.setDisabled(true);
			    prizenum.setDisabled(true);
				prizedep.setDisabled(true); prizepersons.setDisabled(true);
				deUpload.setDisabled(true);
				upFile.setDisabled(true);
			}
//			if(cg.getAuditState()!=null&&cg.getAuditState().shortValue()==GhXm.AUDIT_YES){
//				submit.setDisabled(true);
//				upFile.setDisabled(true);
//			}
			
		}else{
			cgmc.addEventListener(Events.ON_CHANGE, new EventListener(){

				public void onEvent(Event arg0) throws Exception {
					initCgWindow(cgmc.getValue().trim(),ly.getValue().trim(),GhCg.CG_KY);
				}
				
			});
			ly.addEventListener(Events.ON_CHANGE, new EventListener(){

				public void onEvent(Event arg0) throws Exception {
					initCgWindow(cgmc.getValue().trim(),ly.getValue().trim(),GhCg.CG_KY);
				}
				
			});
			cgmc.setValue("");
			ly.setValue("");
			time.setValue("");
			dengji.setValue("");
			hjmc.setValue("");
			prizepersons.setValue("");
			prizedep.setValue("");
			prizenum.setValue("");
			writer.setValue(user.getKuName());
//			mingci.setDisabled(true);
			cdrw.setSelectedIndex(0);
			jb.setSelectedIndex(0);
			deUpload.setDisabled(true);
			downFile.setDisabled(true);
			downFileZip.setDisabled(true);
//			//ѡ����Ŀ����
//			if (cgmc.getModel().getSize() > 0) {
//				cgmc.addEventListener(Events.ON_SELECT, new EventListener() {
//
//					public void onEvent(Event arg0) throws Exception {
//						if(cgmc.getSelectedItem()!=null){
//						Object[] mc = (Object[]) cgmc.getSelectedItem().getValue();
//						GhCg cg = (GhCg) cgService.get(GhCg.class, (Long) mc[1]);
//						if(cg.getKyNumber()!=null&&cg.getKyNumber().trim().length()>0){
//							number.setValue(cg.getKyNumber());number.setDisabled(true);
//						}else{
//							number.setValue("");number.setDisabled(true);
//						}
//						
//						if(cg.getKyLy()!=null&&cg.getKyLy().trim().length()>0){
//							ly.setValue(cg.getKyLy());ly.setDisabled(true);
//							
//						}else{
//							ly.setValue("");ly.setDisabled(true);
//						}
//						cdrw.setSelectedIndex(0);
//						if (cg.getKySj() != null) {
//							shijian.setValue(cg.getKySj());shijian.setDisabled(true);
//						} else {
//							shijian.setValue("");shijian.setDisabled(true);
//						}
//						if (cg.getKyDj() != null) {
//							dengji.setValue(cg.getKyDj());dengji.setDisabled(true);
//						} else {
//							dengji.setValue("");dengji.setDisabled(true);
//						}
//						if(cg.getKyPrizenum()!=null){
//							prizenum.setValue(cg.getKyPrizenum());prizenum.setDisabled(true);
//						}else{
//							prizenum.setValue("");prizenum.setDisabled(true);
//						}
//						if (cg.getKyPrizeper() != null) {
//							prizepersons.setValue(cg.getKyPrizeper());prizepersons.setDisabled(true);
//						} else {
//							prizepersons.setValue("");prizepersons.setDisabled(true);
//						}
//						if (cg.getKyPrizedep() != null) {
//							prizedep.setValue(cg.getKyPrizedep());
//							prizedep.setDisabled(true);
//						} else {
//							prizedep.setValue("");prizedep.setDisabled(true);
//						}
//						mingci.setValue(cg.getKyGrpm()+"/"+cg.getKyZrs());mingci.setDisabled(true);
//						List fjList = ghfileService.findByFxmIdandFType(cg.getKyId(), 2);
//						if (fjList.size() == 0) {// �޸���
//							rowFile.setVisible(false);
//							downFileZip.setDisabled(true);
//						} else {// �и���
//								// ��ʼ������
//							downFileZip.setDisabled(false);
//							for (int i = 0; i < fjList.size(); i++) {
//								UploadFJ ufj = new UploadFJ(false);
//								try {
//									ufj.ReadFJ(getDesktop(), (GhFile) fjList.get(i));
//								} catch (InterruptedException e) {
//									e.printStackTrace();
//								}
//								fileModel.add(ufj);
//							}
//							upList.setModel(fileModel);
//							rowFile.setVisible(true);
//						}
//
//					}
//
//				}
//					
//				});
//			}
		}
		// ����
		if (cg == null) {// �����
			rowFile.setVisible(false);
		} else {// �޸�
			List fjList = ghfileService.findByFxmIdandFType(cg.getKyId(), 2);
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
	public void onClick$submit() throws InterruptedException{
	
		//�ɹ�����
		if(cgmc.getValue()==null||"".equals(cgmc.getValue().trim())){
			throw new WrongValueException(cgmc, "����û����д�ɹ����ƣ�");
		}
		//��ʱ��
		if(time.getValue()==null){
			throw new WrongValueException(time, "����û����дʱ�䣬��ʽ��2008/9/28��2008��2008/9��");
		}else{
			try{
				String str = time.getValue();
				if(str.length() < 4){
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
		//�ȼ�����
		if(dengji.getValue()==null||"".equals(dengji.getValue().trim())){
			throw new WrongValueException(dengji, "����û����д������/�ȼ���");
		}
		
		//����
		if(prizepersons.getValue()==null||"".equals(prizepersons.getValue().trim())){
			throw new WrongValueException(prizepersons, "����û����д���ˣ�");
		}
		//��������
		int zrs = 0; int pm = 0;
		if(mingci.getValue() == null|| "".equals(mingci.getValue().trim())){
			throw new WrongValueException(mingci, "����û����д�������Σ�");
		}else{
			
			try{
				String str = mingci.getValue();
				String s[] = str.split("/");
				if(s.length==2){
					pm = Integer.parseInt(s[0]);
					zrs = Integer.parseInt(s[1]);
					if(pm > zrs){
						throw new WrongValueException(mingci, "�����������ܴ�����Ŀ����������");
					}
				}else{
					throw new WrongValueException(mingci, "����������θ�ʽ�д�������������д��");
				}
				
			}catch(NumberFormatException e){
				throw new WrongValueException(mingci, "����������θ�ʽ�д�������������д��");
			}
		}
		//false��ʾ���޸ģ�true��ʾ���½�
		boolean index = false,owner=false; 
		if(cg==null){//˵�������½���һ����Ŀ
			if(!time.isDisabled()){
				owner=true;
			}
			cg = new GhCg();
			index = true;
		}
//		if(cgmc.getSelectedItem()==null){
//			//û��ѡ����Ŀ���ƣ�����Ŀ���ڸ��û�
//			owner=true;
//		}else{
//			owner=false;
//		}
//		
		if(index){
			//�½�����Ŀ����һ�����ڵ�ǰ�û������ģ�������Ŀ���ɹ���ͽ�ʦ��Ŀ������ѡ�����˴�������Ŀ��ֻ�����ʦ��Ŀ
			if(owner){
				if(!prizepersons.getValue().trim().contains(user.getKuName())){
					Messagebox.show("�����в�����������������������ӣ�","����",Messagebox.OK,Messagebox.EXCLAMATION);
					cg=null;
					return;
				}
				//�ɹ���
				if(cgService.CheckByNameAndLxAndDj(null, cgmc.getValue().trim(), GhCg.CG_KY, ly.getValue().trim(),dengji.getValue().trim())){
					Messagebox.show("���ύ�Ļ񽱳ɹ���Ϣ�Ѵ��ڣ��������ظ���ӣ�","����",Messagebox.OK,Messagebox.EXCLAMATION);
					cg=null;
					return;
				}
				cg.setKySj(time.getValue());
				cg.setKyHjmc(hjmc.getValue().trim());
				cg.setKyDj(dengji.getValue().trim());
				cg.setKyGrpm(pm); cg.setKyZrs(zrs);
				cg.setKyPrizenum(prizenum.getValue().trim());
				cg.setKyPrizedep(prizedep.getValue().trim());
				cg.setKyPrizeper(prizepersons.getValue().trim());
				cg.setKyMc(cgmc.getValue().trim());
				cg.setKyLy(ly.getValue().trim());
				cg.setKyNumber(number.getValue().trim());
//				cg.setKyXmid(xm.getKyId()); //ɾ������Ŀ��Ĺ���
				cg.setKyLx(GhCg.CG_KY);
				cg.setKuId(user.getKuId());
				cg.setKyJb(Short.parseShort(jb.getSelectedIndex()+""));
				cgService.save(cg);
				// �����洢
				UploadFJ.ListModelListUploadCommand(fileModel, cg.getKyId());
				//
				GhJsxm jsxm=new GhJsxm();
				jsxm.setKyId(cg.getKyId());
				jsxm.setJsxmType(GhJsxm.HjKY);
				jsxm.setKyMemberName(user.getKuName());
				jsxm.setKyTaskDesc("");
				jsxm.setYjId(Long.parseLong("0"));
				jsxm.setKuId(user.getKuId());
				jsxm.setKyGx(Integer.parseInt(mingci.getValue().split("/")[0]));
				jsxm.setKyCdrw(String.valueOf(cdrw.getSelectedIndex()));
				jsxmService.save(jsxm);
				Messagebox.show("����ɹ���","��ʾ",Messagebox.OK,Messagebox.INFORMATION);
			}else{
				if(!prizepersons.getValue().trim().contains(user.getKuName())){
					Messagebox.show("�����в�����������������������ӣ�","����",Messagebox.OK,Messagebox.EXCLAMATION);
					return;
				}
				GhJsxm jsxm=new GhJsxm();
			    jsxm.setKuId(user.getKuId());
				jsxm.setJsxmType(GhJsxm.HjKY);
				jsxm.setKyMemberName(user.getKuName());
				jsxm.setKyTaskDesc("");
				jsxm.setYjId(Long.parseLong("0"));
				jsxm.setKyId(cg.getKyId());
				jsxm.setKyGx(Integer.parseInt(mingci.getValue().split("/")[0]));
				jsxm.setKyCdrw(String.valueOf(cdrw.getSelectedIndex()));
				jsxmService.save(jsxm);
				Messagebox.show("����ɹ���","��ʾ",Messagebox.OK,Messagebox.INFORMATION);
			}
		}else{
			if(user.getKuId().intValue()==cg.getKuId().intValue()){
				if(cgService.CheckByNameAndLxAndDj(cg, cgmc.getValue().trim(), GhCg.CG_KY, ly.getValue().trim(),dengji.getValue().trim())){
					Messagebox.show("���ύ�Ļ񽱳ɹ���Ϣ�Ѵ��ڣ��˴��޸Ĳ��豣�棡","����",Messagebox.OK,Messagebox.EXCLAMATION);
					return;
				}
				cg.setKySj(time.getValue());
				cg.setKyHjmc(hjmc.getValue().trim());
				cg.setKyDj(dengji.getValue().trim());
				cg.setKyGrpm(pm); cg.setKyZrs(zrs);
				cg.setKyPrizenum(prizenum.getValue().trim());
				cg.setKyPrizedep(prizedep.getValue().trim());
				cg.setKyPrizeper(prizepersons.getValue().trim());
				cg.setKyMc(cgmc.getValue().trim());
				cg.setKyLy(ly.getValue().trim());
				cg.setKyJb(Short.parseShort(jb.getSelectedIndex()+""));
				cg.setKyNumber(number.getValue().trim());
				cg.setAuditState(null);
				cg.setAuditUid(null);
				cg.setReason(null);
				cgService.update(cg);
				List jslwlist=jsxmService.findByXmidAndtype(cg.getKyId(), GhJsxm.HjKY);
				for(int i=0;i<jslwlist.size();i++){
					GhJsxm js=(GhJsxm)jslwlist.get(i);
					if(!prizepersons.getValue().trim().contains(js.getUser().getKuName().trim())){
						jsxmService.delete(js);
					}
				}
				// �����洢
				UploadFJ.ListModelListUploadCommand(fileModel, cg.getKyId());
				//
				GhJsxm jsxm= jsxmService.findByXmidAndKuidAndType(cg.getKyId(), user.getKuId(), GhJsxm.HjKY);
				if(jsxm!=null){
					jsxm.setKyGx(Integer.parseInt(mingci.getValue().split("/")[0]));
					jsxm.setKyCdrw(String.valueOf(cdrw.getSelectedIndex()));
					jsxmService.update(jsxm);
				}else{
					jsxm=new GhJsxm();
					jsxm.setKuId(user.getKuId());
					jsxm.setJsxmType(GhJsxm.HjKY);
					jsxm.setKyMemberName(user.getKuName());
					jsxm.setKyTaskDesc("");
					jsxm.setYjId(Long.parseLong("0"));
					jsxm.setKyId(cg.getKyId());
					jsxm.setKyGx(Integer.parseInt(mingci.getValue().split("/")[0]));
					jsxm.setKyCdrw(String.valueOf(cdrw.getSelectedIndex()));
					jsxmService.save(jsxm);
				}
				
			}else{
				if(!prizepersons.getValue().trim().contains(user.getKuName())){
					Messagebox.show("�����в�����������������������ӣ�","����",Messagebox.OK,Messagebox.EXCLAMATION);
					cg=null;
					return;
				}
				GhJsxm jsxm= jsxmService.findByXmidAndKuidAndType(cg.getKyId(), user.getKuId(), GhJsxm.HjKY);
				if(jsxm!=null){
					jsxm.setKyGx(Integer.parseInt(mingci.getValue().split("/")[0]));
					jsxm.setKyCdrw(String.valueOf(cdrw.getSelectedIndex()));
					jsxmService.update(jsxm);
				}else{
					jsxm=new GhJsxm();
					jsxm.setKuId(user.getKuId());
					jsxm.setKyMemberName(user.getKuName());
					jsxm.setKyTaskDesc("");
					jsxm.setYjId(Long.parseLong("0"));
					jsxm.setJsxmType(GhJsxm.HjKY);
					jsxm.setKyId(cg.getKyId());
					jsxm.setKyGx(Integer.parseInt(mingci.getValue().split("/")[0]));
					jsxm.setKyCdrw(String.valueOf(cdrw.getSelectedIndex()));
					jsxmService.save(jsxm);
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
//		cgmc.setModel(new ListModelList(cgService.findByKuidAndKunameAndLxAndType(user.getKuId(),user.getKuName(),GhCg.CG_KY,GhJsxm.HjKY)));
//		cgmc.setItemRenderer(new ComboitemRenderer(){
//
//			public void render(Comboitem arg0, Object arg1) throws Exception {
//				Object[] name=(Object[])arg1;
//				arg0.setWidth("200px");
//				arg0.setValue(name);
//				arg0.setLabel((String)name[0]);
//			}
//		});
	}
	public void onClick$choice(){
		Map args=new HashMap();
    	args.put("user", user);
    	args.put("lx", GhCg.CG_KY);
    	args.put("type", GhJsxm.HjKY);
    	SelectcgWindow scw=(SelectcgWindow)Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/hjky/selectcg.zul", null, args);
    	scw.initShow();
    	scw.doHighlighted();
    	scw.addEventListener(Events.ON_CHANGE, new EventListener(){

			public void onEvent(Event arg0) throws Exception {
				SelectcgWindow scw=(SelectcgWindow)arg0.getTarget();
				if(scw.getCglist().getSelectedItem()!=null){
					GhCg Cg=(GhCg)scw.getCglist().getSelectedItem().getValue();
					if(!Cg.getKyPrizeper().contains(user.getKuName())){
						Messagebox.show("��������Ŀ���Ա������ϵ��Ŀ��д��������룡", "��ʾ��", Messagebox.OK, Messagebox.EXCLAMATION);
					}
					else{
					cg=Cg;
					initWindow();
					scw.detach();
					
				}
				
				}}
    	});
	}
	
	public void initCgWindow(String Cgmc,String Ly,Short lx){
		GhCg Cg=cgService.findByNameAndLxAndly(Cgmc, lx, Ly);
		if(Cg!=null){
			cg=Cg;
			cdrw.setSelectedIndex(0);
			if (cg.getKySj() != null) {
				time.setValue(cg.getKySj());time.setDisabled(true);
			} else {
				time.setValue("");time.setDisabled(true);
			}
			if (cg.getKyDj() != null) {
				dengji.setValue(cg.getKyDj());dengji.setDisabled(true);
			} else {
				dengji.setValue("");dengji.setDisabled(true);
			}
			if(cg.getKyHjmc()!=null){
				hjmc.setValue(cg.getKyHjmc());hjmc.setDisabled(true);
			}else{
				hjmc.setValue("");hjmc.setDisabled(true);
			}
			if(cg.getKyPrizenum()!=null){
				prizenum.setValue(cg.getKyPrizenum());prizenum.setDisabled(true);
			}else{
				prizenum.setValue("");prizenum.setDisabled(true);
			}
			if (cg.getKyPrizeper() != null) {
				prizepersons.setValue(cg.getKyPrizeper());prizepersons.setDisabled(true);
			} else {
				prizepersons.setValue("");prizepersons.setDisabled(true);
			}
			if (cg.getKyPrizedep() != null) {
				prizedep.setValue(cg.getKyPrizedep());
				prizedep.setDisabled(true);
			} else {
				prizedep.setValue("");prizedep.setDisabled(true);
			}
			if(cg.getKyJb()!=null&&cg.getKyJb().shortValue()==GhCg.CG_GJ){
				jb.setSelectedIndex(1);
			}else if(cg.getKyJb()!=null&&cg.getKyJb().shortValue()==GhCg.CG_SB){
				jb.setSelectedIndex(2);
			}else if(cg.getKyJb()!=null&&cg.getKyJb().shortValue()==GhCg.CG_QT){
				jb.setSelectedIndex(3);
			}else{
				jb.setSelectedIndex(0);
			}
			writer.setValue(cg.getUser().getKuName());
			List namelist=new ArrayList();
			String str=prizepersons.getValue().trim();
			
			while (str.contains("��")) {
					Label lb = new Label(str.substring(0,str.indexOf("��")));
					namelist.add(lb.getValue().trim());
					str = str.substring(str.indexOf("��") + 1, str.length());
				
			}
			namelist.add(str.trim());
			mingci.setValue(namelist.indexOf(user.getKuName())+1+"/"+namelist.size());
//			mingci.setDisabled(true);
//			List fjList = ghfileService.findByFxmIdandFType(cg.getKyId(), 2);
//			if (fjList.size() == 0) {// �޸���
//				rowFile.setVisible(false);
//				downFileZip.setDisabled(true);
//			} else {// �и���
//					// ��ʼ������
//				downFileZip.setDisabled(false);
//				for (int i = 0; i < fjList.size(); i++) {
//					UploadFJ ufj = new UploadFJ(false);
//					try {
//						ufj.ReadFJ(getDesktop(), (GhFile) fjList.get(i));
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//					fileModel.add(ufj);
//				}
//				upList.setModel(fileModel);
//				rowFile.setVisible(true);
		}else{
			cg=null;
//			shijian.setValue("");shijian.setDisabled(false);
//			dengji.setValue("");dengji.setDisabled(false);
//			prizepersons.setValue("");prizepersons.setDisabled(false);
//			prizedep.setValue("");prizedep.setDisabled(false);
//			prizenum.setValue("");prizenum.setDisabled(false);
//			mingci.setValue("0");
		}
	}
	public void onClick$upFile() throws InterruptedException {
		/*Object media = Fileupload.get();
		if (media == null) {
			return;
		}
		rowFile.setVisible(true);
		fileModel.add(media);*/
		UploadFJ ufj = new UploadFJ(true);
		Media media = ufj.Upload(this.getDesktop(), 2, 10, "�������ܳ���10MB",
				"�񽱿��гɹ�����------�������ܳ���10MB");
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
	 * @throws InterruptedException 
	 */
	public void onClick$deUpload() throws InterruptedException {
		if(Messagebox.show("ȷ��ɾ����?", "��ʾ", Messagebox.OK|Messagebox.CANCEL,
				Messagebox.QUESTION)==1){
			// ȥ���ɵĴ������
			IsZipExists.delZipFile(this.getDesktop().getWebApp().getRealPath(
					"/upload/xkjs/").trim()
					+ "\\" + "_" +cg.getKyId() + "_" + ".zip");
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
			fileModel.remove(it.getValue());
			rowFile.setVisible(false);
		} else if (fileModel.getSize() > 1) {
			//
			temp.DeleteFJ();
			//
			fileModel.remove(it.getValue());
		}}
	}

	public void onClick$reset(){
		// ȥ���ɵĴ������
		IsZipExists.delZipFile(this.getDesktop().getWebApp().getRealPath(
				"/upload/xkjs/").trim()
				+ "\\" + "_" +cg.getKyId() + "_" + ".zip");
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
		DownloadFJ.ListModelListDownloadCommand(this.getDesktop(),this.cg.getKyId(), fileModel);
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

	public GhCg getCg() {
		return cg;
	}

	public void setCg(GhCg cg) {
		this.cg = cg;
	}

}
