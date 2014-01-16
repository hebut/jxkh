package org.iti.projectmanage.science.project;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jxl.write.WriteException;


import org.apache.log4j.Logger;
import org.iti.gh.common.util.DateUtil;
import org.iti.gh.entity.GhCg;
import org.iti.gh.entity.GhFile;
import org.iti.gh.entity.GhJsxm;
import org.iti.gh.entity.GhXm;
import org.iti.gh.entity.GhYjfx;
import org.iti.gh.service.CgService;
import org.iti.gh.service.GhFileService;
import org.iti.gh.service.JsxmService;
import org.iti.gh.service.XmService;
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
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.uniwin.asm.personal.ui.data.teacherinfo.kyqk.SelectXmWindow;
import com.uniwin.framework.common.fileuploadex.DownloadFJ;
import com.uniwin.framework.common.fileuploadex.IsZipExists;
import com.uniwin.framework.common.fileuploadex.UploadFJ;
import com.uniwin.framework.entity.WkTUser;
/**
 * <li>��Ŀ����: Kygl���й���
 * <li>��������: ������Ŀ����ɾ�� 
 * <li>��Ȩ: Copyright (c) ��Ϣ�����о���
 */
public class KyHProWindow extends FrameworkWindow {	
	private static final long serialVersionUID = 1477614318452721480L;
	private Logger  log  = Logger.getLogger(KyHProWindow.class);
	GhXm xm = (GhXm) Executions.getCurrent().getArg().get("hxxm");
	Long kuid = (Long) Executions.getCurrent().getArg().get("useKuid");
	private WkTUser user;	
	
	private XmService xmService;
	private CgService cgService;
	private GhFileService ghfileService;
	private JsxmService jsxmService;
	
	private Decimalbox jingfei ;
	private Textbox ly,prostaffs,number,proman, target,cgmc;
	private Label gongxian,writeuser,internalNum;
	private Listbox  subjetype, scale, progress, cdrw,contraTypeListbox;	
	private YjfxListbox research;
	private Datebox identtime;
	private Row rowFile;
	private Listbox upList;
	private ListModelList fileModel;
	private Button downFile,deUpload,upFile;//�ϴ���ͬ��
	private Checkbox checkBackup;
	private Toolbarbutton exportMiddReport,resultReport;
	private Textbox contractNum,finishUnit,conFinishCondition,fruit,level,commitCom,commitComplace,acceptCom,acceptComPlace;
	private Datebox setFinishTime,realFinishTime;	

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");		
		//�ϴ���ͬ��
		fileModel = new ListModelList(new ArrayList());
		upList.setModel(fileModel);		
		upList.addEventListener(Events.ON_SELECT, new EventListener(){
			public void onEvent(Event arg0) throws Exception {
 				 Listitem item =(Listitem)upList.getSelectedItem();
 				 UploadFJ file =(UploadFJ)item.getValue();
 				 if(file.getGf()!=null&&!file.getGf().equals("")){
			         if(file.getGf().getFbackup()==1){
							checkBackup.setChecked(true);							
					 }else if(file.getGf().getFbackup()==0){
							checkBackup.setChecked(false);							
					 }
 				 }
		    }
		});		 	
	}
	
	/**
	* <li>������������ʼ�����ڡ�
	 * @param null
	 * @return null
	 */
	public void initWindow() {
		List subtype = new ArrayList();
		List subclass = new ArrayList();
		List subscale = new ArrayList();
		List subprogress = new ArrayList();
		List subcdrw = new ArrayList();
		List contraType = new ArrayList();
		String[] Subtype = { "-��ѡ��-", "��Ȼ��ѧ", "����ѧ", "����"};
		for (int i = 0; i < Subtype.length; i++) {
			subtype.add(Subtype[i]);
		}
		String[] Subclass = { "-��ѡ��-", "����", "����" };
		for (int i = 0; i < Subclass.length; i++) {
			subclass.add(Subclass[i]);
		}
		String[] Subscale = { "-��ѡ��-", "���ʺ�����Ŀ", "���Ҽ���Ŀ", "����ί��ʡ������Ŀ", "��������Ŀ", "ί�м�������Ŀ", "ѧУ������Ŀ", "����" };
		for (int i = 0; i < Subscale.length; i++) {
			subscale.add(Subscale[i]);
		}
		String[] Subprogress = { "-��ѡ��-", "������", "����", "�����" };
		for (int i = 0; i < Subprogress.length; i++) {
			subprogress.add(Subprogress[i]);
		}
		String[] Subcdrw = { "-��ѡ��-", "����", "�μ�", "����" };
		for (int i = 0; i < Subcdrw.length; i++) {
			subcdrw.add(Subcdrw[i]);
		}
		String[] ConType = {"-��ѡ��-","��������","����ת��","��������","������ѯ"};
		for(int i=0;i<ConType.length;i++){
			contraType.add(ConType[i]);
		}
		//��ͬ����
		contraTypeListbox.setModel(new ListModelList(contraType));
		// ѧ�Ʒ���
		subjetype.setModel(new ListModelList(subtype));
		// ��Ŀ����
		scale.setModel(new ListModelList(subscale));
		// ��Ŀ��չ
		progress.setModel(new ListModelList(subprogress));
		// ���˳е����������
		cdrw.setModel(new ListModelList(subcdrw));
		research.initYjfzList(user.getKuId(),null);
			
		if (xm != null) {//�û����ڱ༭��Ŀ��Ϣ	
			// ��Ŀ���
			number.setValue(xm.getKyNumber());
			// ��Ŀ����
			cgmc.setValue(xm.getKyMc());cgmc.setDisabled(true);	
			// ��Դ--����Ϊ��
			if (xm.getKyLy() != null) {
				ly.setValue(xm.getKyLy());
			}
			//��Ŀ�ڲ����
			internalNum.setValue(xm.getInternalNum());	
			// ����--����Ϊ��		
			if (xm.getKyJf() != null) {
				jingfei.setValue(BigDecimal.valueOf(xm.getKyJf()));
			}	
			// ѧ�Ʒ���
			if (xm.getKySubjetype() == null || xm.getKySubjetype() == "") {
				subjetype.setSelectedIndex(0);
			} else {
				subjetype.setSelectedIndex(Integer.valueOf(xm.getKySubjetype().trim()));
			}	
			// ��ͬ����
			if (xm.getContractType() == null || xm.getContractType() == "") {
				contraTypeListbox.setSelectedIndex(0);
			} else {
				contraTypeListbox.setSelectedIndex(Integer.valueOf(xm.getContractType().trim()));
			}	
			// ��Ŀ����
			if (xm.getKyScale() == null || xm.getKyScale() == "") {
				scale.setSelectedIndex(0);
			} else {
				scale.setSelectedIndex(Integer.valueOf(xm.getKyScale().trim()));
			}
			// ��Ŀ��չ
			if (xm.getKyProgress() == null || xm.getKyProgress() == "") {
				progress.setSelectedIndex(0);
			} else {
				progress.setSelectedIndex(Integer.valueOf(xm.getKyProgress().trim()));
			}			
			// ��Ŀ����Ա
			if (xm.getKyProstaffs() != null) {
				prostaffs.setValue(xm.getKyProstaffs());
			} else {
				prostaffs.setValue("");
			}
			// ��Ŀ������
			if (xm.getKyProman() != null) {
				proman.setValue(xm.getKyProman());
			} else {
				proman.setValue("");
			}
			// ��Ŀָ��
			if (xm.getKyTarget() != null) {
				target.setValue(xm.getKyTarget());
			} else {
				target.setValue("");
			}
			// ����ʱ��
			if(xm.getKyIdenttime()==null||xm.getKyIdenttime().equals("")){
				identtime.setValue(null);
    		}else{
    			identtime.setValue(DateUtil.getDate(xm.getKyIdenttime()));
    		}
			// ����ˮƽ
			if (xm.getKyLevel() != null) {
				level.setValue(xm.getKyLevel());
			} else {
				level.setValue("");
			}
			//��ʦ��Ŀ
            GhJsxm jx=(GhJsxm)jsxmService.findByXmidAndKuidAndType(xm.getKyId(), user.getKuId(),GhJsxm.KYXM);
            if(jx!=null){
            	// ���˹���
            	gongxian.setValue(jx.getKyGx()!=null?jx.getKyGx()+"":"0");
            	research.initYjfzList(user.getKuId(), jx.getYjId());
    			// ���˳е����������
    			if (jx.getKyCdrw() == null || jx.getKyCdrw() == "") {
    				cdrw.setSelectedIndex(0);
    			} else {
    				cdrw.setSelectedIndex(Integer.valueOf(jx.getKyCdrw().trim()));
    			}
            }else{
            	gongxian.setValue("0");
            	research.setSelectedIndex(0);
            	cdrw.setSelectedIndex(0);
            }            
            //��ͬ���
            if (xm.getKyContraNum()!= null&&!xm.getKyContraNum().equals("")) {
            	contractNum.setValue(xm.getKyContraNum());
			} else {
				contractNum.setValue("");
			}
            //ί�е�λ
            if (xm.getCommitCom()!= null&&!xm.getCommitCom().equals("")) {
            	commitCom.setValue(xm.getCommitCom());
			} else {
				commitCom.setValue("");
			}
            //ί�е�λ��ַ
            if (xm.getCommitComPlace()!= null&&!xm.getCommitComPlace().equals("")) {
            	commitComplace.setValue(xm.getCommitComPlace());
			} else {
				commitComplace.setValue("");
			}
            //���е�λ��ַ
            if (xm.getAcceptCom()!= null&&!xm.getAcceptCom().equals("")) {
            	acceptCom.setValue(xm.getAcceptCom());
			} else {
				acceptCom.setValue("");
			}
            //���е�λ��ַ
            if (xm.getAcceptComPlace()!= null&&!xm.getAcceptComPlace().equals("")) {
            	acceptComPlace.setValue(xm.getAcceptComPlace());
			} else {
				acceptComPlace.setValue("");
			}
            //��ɵ�λ
            if (xm.getKyFinishUnit()!= null&&!xm.getKyFinishUnit().equals("")) {
            	finishUnit.setValue(xm.getKyFinishUnit());
			} else {
				finishUnit.setValue("");
			}
            //�涨���ʱ��
            if(xm.getKySetFinishTime()==null||xm.getKySetFinishTime().equals("")){
            	setFinishTime.setValue(null);
    		}else{
    			setFinishTime.setValue(DateUtil.getDate(xm.getKySetFinishTime()));
    		}
            //ʵ��������
            if(xm.getKyRealFinishTime()==null||xm.getKyRealFinishTime().equals("")){
            	realFinishTime.setValue(null);
    		}else{
    			realFinishTime.setValue(DateUtil.getDate(xm.getKyRealFinishTime()));
    		}
            //������
            if (xm.getKyContentFinishConditon()!= null&&!xm.getKyContentFinishConditon().equals("")) {
            	conFinishCondition.setValue(xm.getKyContentFinishConditon());
			} else {
				conFinishCondition.setValue("");
			}
            //�ɹ�����Ч
            if (xm.getKyFruit()!= null&&!xm.getKyFruit().equals("")) {
            	fruit.setValue(xm.getKyFruit());
			} else {
				fruit.setValue("");
			}          
            writeuser.setValue(xm.getUser().getKuName());
			if(xm.getKuId().intValue()!=user.getKuId().intValue()){//�Ǹ��û���������Ŀ�������Բ����Ա༭				
				number.setDisabled(true);ly.setDisabled(true);
				jingfei.setDisabled(true);prostaffs.setDisabled(true);
				proman.setDisabled(true);
				target.setDisabled(true);identtime.setDisabled(true);
				level.setDisabled(true);subjetype.setDisabled(true);
				scale.setDisabled(true);contraTypeListbox.setDisabled(true);
				progress.setDisabled(true);
				
				//������Ŀ��Ϣ
				contractNum.setDisabled(true);finishUnit.setDisabled(true);
				conFinishCondition.setDisabled(true);fruit.setDisabled(true);
				setFinishTime.setDisabled(true);realFinishTime.setDisabled(true);
				commitCom.setDisabled(true);commitComplace.setDisabled(true);
				acceptCom.setDisabled(true);acceptComPlace.setDisabled(true);
				
				//�������ϴ�����				
				deUpload.setDisabled(true);
				upFile.setDisabled(true);
				downFile.setDisabled(true);
				checkBackup.setDisabled(true);
			}	
//			if(xm.getAuditState()!=null&&xm.getAuditState().shortValue()==GhXm.AUDIT_YES){
//				submit.setDisabled(true);
//				upFile.setDisabled(true);
//			}		
		} else {
			cgmc.addEventListener(Events.ON_CHANGE, new EventListener(){
				public void onEvent(Event arg0) throws Exception {
					initXmWindow(cgmc.getValue().trim(),ly.getValue().trim(),proman.getValue().trim(),GhXm.KYXM);
				}
			});
			ly.addEventListener(Events.ON_CHANGE, new EventListener(){
				public void onEvent(Event arg0) throws Exception {
					initXmWindow(cgmc.getValue().trim(),ly.getValue().trim(),proman.getValue().trim(),GhXm.KYXM);
				}
			});
			proman.addEventListener(Events.ON_CHANGE, new EventListener(){
				public void onEvent(Event arg0) throws Exception {
					initXmWindow(cgmc.getValue().trim(),ly.getValue().trim(),proman.getValue().trim(),GhXm.KYXM);
				}
			});
			cgmc.setValue("");
			ly.setValue("");
			jingfei.setValue(BigDecimal.valueOf(0l));
			gongxian.setValue("0");
			prostaffs.setValue("");
			proman.setValue("");
			target.setValue("");
			identtime.setValue(null);
			level.setValue("");
			writeuser.setValue(user.getKuName());
			
			//������Ŀ��Ϣ
			contractNum.setValue("");finishUnit.setValue("");
			conFinishCondition.setValue("");fruit.setValue("");
			setFinishTime.setValue(null);realFinishTime.setValue(null);
			commitCom.setValue("");commitComplace.setValue("");
			acceptCom.setValue("");acceptComPlace.setValue("");
			
			deUpload.setDisabled(true);
			downFile.setDisabled(true);
			checkBackup.setDisabled(true);
			
			//��������
			exportMiddReport.setDisabled(true);
			resultReport.setDisabled(true);			
		}
		// ����
		if (xm == null) {// �����
			rowFile.setVisible(true);
		} else {
			//�޸ģ�������ͬ��
			List fjList = ghfileService.findByFxmIdandFType(xm.getKyId(), 23);
			if (fjList.size() == 0) {// �޸���
				rowFile.setVisible(true);	
			} else {// �и�������ʼ������
				for (int i = fjList.size()-1; i >=0 ; i--) {
					UploadFJ ufj = new UploadFJ(false);
					try {
						ufj.ReadFJ(this.getDesktop(), (GhFile) fjList.get(i));					
						GhFile f = (GhFile)fjList.get(fjList.size()-1);
						if(f.getFbackup()==1){
							checkBackup.setChecked(true);							
						}else if(f.getFbackup()==0){
							checkBackup.setChecked(false);							
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					fileModel.add(ufj);
				}				
				upList.setModel(fileModel);		
				upList.setVisible(true);downFile.setVisible(true);
				deUpload.setVisible(true);rowFile.setVisible(true);
				checkBackup.setVisible(true);				
			}
						
		}
 }
	/**
	 * �ύ����
	 * 2011-5
	 * @throws InterruptedException 
	 * @throws InterruptedException
	 */
	public void onClick$submit() throws InterruptedException {		
		//��Ŀ��� Ҫô���Ҫ����д����Ψһ if(number.getValue()==null||"".equals(number.getValue().trim())){ }else{ if(xm==null){//�½���Ŀ //��Ŀ��Ψһ���� if(xmService.findByKynumber(number.getValue()).size()>=1){ throw new WrongValueException(number, "��Ŀ��ű���Ψһ������Ŀ��Ŵ��ڣ����ʵ����д��"); } }else{//�޸���Ŀ if(xmService.findByKynumber(number.getValue()).size()>=2){ throw new WrongValueException(number, "��Ŀ��ű���Ψһ������Ŀ��Ŵ��ڣ����ʵ����д��"); } } }
		// ��������
		if (cgmc.getValue() == null || "".equals(cgmc.getValue().trim())) {
			throw new WrongValueException(cgmc, "����û����д�������ƣ�");
		}	
		//��Ŀ��Դ
		if(ly.getValue()==null||ly.getValue().trim().equals("")){
			throw new WrongValueException(ly, "����û����д��Ŀ��Դ��");
		}
		//��Ŀ��ͬ���
		if(contractNum.getValue()==null||"".equals(contractNum.getValue().trim())){
			throw new WrongValueException(contractNum, "����û����д��Ŀ��ͬ��ţ�");
		}
			
		//������
		if (proman.getValue() == null || "".equals(proman.getValue().trim())) {
			throw new WrongValueException(proman, "����û����д��Ŀ�����ˣ�");
		}
		
		// ����
		Float kyJf = 0F;
		if (jingfei.getValue() != null &&!"".equals(jingfei.getValue().toString().trim())) {
			kyJf = Float.parseFloat(jingfei.getValue()+"");
		}
		// ���˹���
		Integer kyGx = 1;
		if(gongxian.getValue()==null||"".equals(gongxian.getValue().trim())){
			throw new WrongValueException(gongxian, "������Ŀ���Ա�Ƿ��зǷ��ַ���������һ���淶�����֣�");
		}else{
			try{
				kyGx = Integer.parseInt(gongxian.getValue());
			}catch(NumberFormatException e){
				throw new WrongValueException(gongxian, "��Ŀ���Ա�зǷ��ַ���");
			}		
		}	
				
		//�涨���ʱ��
		if(setFinishTime.getValue()==null||setFinishTime.getValue().equals("")){
			throw new WrongValueException(setFinishTime,"����û����д��Ŀ�ƻ���ʼʱ�䣡");
		}
		//�涨���ʱ��
		if(realFinishTime.getValue()==null||realFinishTime.getValue().equals("")){
			throw new WrongValueException(realFinishTime,"����û����д��Ŀ�ƻ�����ʱ�䣡");
		}		
		// false��ʾ���޸ģ�true��ʾ���½�
		boolean index = false,owner=false;
		if (xm == null) {// ˵�������½���һ����Ŀ
//			 if(!lxsj.isDisabled()){
//				 owner=true;
//			    }
			owner=true;
			xm = new GhXm();
			index = true;
		}
	
		//2011-5-19 �ļ��ϴ��Ƿ�ѡ��		
		Sessions.getCurrent().setAttribute("check",checkBackup); 
		if (index) {
			//�½���Ŀ,�����������һ����ǰ�û���������Ŀ���ƣ����浽��Ŀ��ͽ�ʦ��Ŀ�����������û�ѡ�����Ŀ���ƣ�ֻ�����ʦ��Ŀ��
			if(owner){//��ǰ�û�����Ŀ
//				if(!xmService.CheckOnlyOne(cgmc.getValue().trim(), GhXm.KYXM, ly.getValue().trim(),proman.getValue().trim())){
//					Messagebox.show("���ύ����Ŀ��Ϣ�Ѵ��ڣ��������ظ���ӣ�","����",Messagebox.OK,Messagebox.EXCLAMATION);
//					xm=null;
//					return;
//				}
				if(!prostaffs.getValue().trim().contains(user.getKuName())&&!proman.getValue().trim().contains(user.getKuName())){
					Messagebox.show("��Ŀ���ж�������������������������ӣ�","����",Messagebox.OK,Messagebox.EXCLAMATION);
					xm=null;
					return;
				}
				xm.setKyNumber(number.getValue().trim());
				xm.setKyMc(cgmc.getValue().trim());
				xm.setKyProstaffs(user.getKuName());
				xm.setKyProman(proman.getValue().trim());
				xm.setKyTarget(target.getValue().trim());
				if(identtime.getValue()!=null){
					xm.setKyIdenttime(DateUtil.getDateString(identtime.getValue()));
				}
				xm.setKyLevel(level.getValue().trim());
				xm.setKySubjetype(String.valueOf(subjetype.getSelectedIndex()));
				xm.setContractType(String.valueOf(contraTypeListbox.getSelectedIndex()));
				xm.setKyClass("1");				//����Ϊ1
				xm.setKyScale(String.valueOf(scale.getSelectedIndex()));
				xm.setKyJf(kyJf);
				xm.setKyLy(ly.getValue().trim());
				xm.setKuId(user.getKuId());			
				xm.setKyLx(GhXm.KYXM);
				if (xm.getKyPrize() == "2") {// �񽱣�����Ŀ��չ�����
					xm.setKyProgress("3");// ��Ŀ��չ�����
				} else {
					xm.setKyProgress(String.valueOf(progress.getSelectedIndex()));
				}
				xm.setAuditState(null);
				xm.setAuditUid(null);
				xm.setReason(null);
				xmService.save(xm);
				//2011-5-18��Ŀ�ڲ����  ��ĸ+��ͬ���							
				if(Integer.valueOf(xm.getKyClass().trim())==1){
					xm.setInternalNum("H"+contractNum.getValue());
				}else if(Integer.valueOf(xm.getKyClass().trim())==2){
					xm.setInternalNum("Z"+contractNum.getValue());
				}
				
				xm.setCommitCom(commitCom.getValue());
				xm.setCommitComPlace(commitComplace.getValue());
				xm.setAcceptCom(acceptCom.getValue());
				xm.setAcceptComPlace(acceptComPlace.getValue());
				
				xm.setKyContraNum(contractNum.getValue());
				xm.setKyFinishUnit(finishUnit.getValue());			
				if(setFinishTime.getValue()!=null){
					xm.setKySetFinishTime(DateUtil.getDateString(setFinishTime.getValue()));
				}
				if(realFinishTime.getValue()!=null){
					xm.setKyRealFinishTime(DateUtil.getDateString(realFinishTime.getValue()));
				}				
				xm.setKyContentFinishConditon(conFinishCondition.getValue());
				xm.setKyFruit(fruit.getValue());				
				xmService.update(xm);
				
				// �����洢
				UploadFJ.ListModelListUploadCommand(fileModel, xm.getKyId());//��ͬ��
				
				//���浽��ʦ��Ŀ��
				GhJsxm jsxm=new GhJsxm();
				jsxm.setKyId(xm.getKyId());
				jsxm.setJsxmType(GhJsxm.KYXM);
				jsxm.setKuId(user.getKuId());
				jsxm.setKyCdrw(String.valueOf(cdrw.getSelectedIndex()));
				jsxm.setYjId(research.getSelectedIndex()!=0?((GhYjfx)research.getSelectedItem().getValue()).getGyId():0L);
				jsxm.setKyGx(kyGx);
				jsxm.setKyMemberName(user.getKuName());
				jsxm.setKyTaskDesc("");
				jsxmService.save(jsxm);
				if (xm.getKyPrize() == "2") {// �񽱣����»񽱿��н��б�
					GhCg cg = new GhCg();
					cg.setKyMc(xm.getKyMc());
					cg.setKyNumber(xm.getKyNumber());
					cg.setKyXmid(xm.getKyId());
					cg.setKuId(user.getKuId());
					cg.setKyLx(GhCg.CG_KY);
					cg.setKyLy(xm.getKyLy());
					// cg.setKyZrs(num);
					cgService.save(cg);
				}
				Messagebox.show("����ɹ���","��ʾ",Messagebox.OK,Messagebox.INFORMATION);
				Events.postEvent(Events.ON_CHANGE, this, null);
			}else{
				if(!prostaffs.getValue().trim().contains(user.getKuName())&&!proman.getValue().trim().contains(user.getKuName())){
					Messagebox.show("��Ŀ����Ա��������������������ϵ��Ŀ��Ϣ����ˣ�", "����", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
				//���浽��ʦ��Ŀ��
				GhJsxm jsxm=new GhJsxm();
				jsxm.setKyId(xm.getKyId());
				jsxm.setJsxmType(1);
				jsxm.setKuId(user.getKuId());
				jsxm.setKyCdrw(String.valueOf(cdrw.getSelectedIndex()));
				jsxm.setYjId(research.getSelectedIndex()!=0?((GhYjfx)research.getSelectedItem().getValue()).getGyId():0L);
				jsxm.setKyGx(kyGx);
				jsxm.setKyMemberName(user.getKuName());
				jsxm.setKyTaskDesc("");
				jsxmService.save(jsxm);
				Messagebox.show("����ɹ���","��ʾ",Messagebox.OK,Messagebox.INFORMATION);
				Events.postEvent(Events.ON_CHANGE, this, null);
			}	
		} else {//�޸���Ŀ��ԭ����Ŀ�Ǹ��û������ĸ�����Ŀ��ͽ�ʦ��Ŀ��ԭ����Ŀ�Ǹ��û�������ֻ���½�ʦ��Ŀ��			   
				if(user.getKuId().intValue()==xm.getKuId().intValue()){
					if(!xmService.findByNameAndLxAndLy(xm, cgmc.getValue().trim(),GhXm.KYXM, ly.getValue().trim(),proman.getValue().trim())){
						Messagebox.show("���ύ����Ŀ��Ϣ�Ѵ��ڣ��˴��޸Ĳ��豣�棡","����",Messagebox.OK,Messagebox.EXCLAMATION);
						return;
					}
					if(!prostaffs.getValue().trim().contains(user.getKuName())&&!proman.getValue().trim().contains(user.getKuName())){
						Messagebox.show("��Ŀ���ж�������������������������ӣ�","����",Messagebox.OK,Messagebox.EXCLAMATION);
						return;
					}
					xm.setKyNumber(number.getValue().trim());
//					xm.setKyMc(cgmc.getValue());//���Ʋ��ɸ�
					xm.setKyProstaffs(user.getKuName());
					xm.setKyProman(proman.getValue().trim());
					xm.setKyTarget(target.getValue().trim());
					if(identtime.getValue()!=null){
						xm.setKyIdenttime(DateUtil.getDateString(identtime.getValue()));
					}
					xm.setKyLevel(level.getValue().trim());
					xm.setKySubjetype(String.valueOf(subjetype.getSelectedIndex()));
					xm.setContractType(String.valueOf(contraTypeListbox.getSelectedIndex()));
					xm.setKyClass("1");					
					//2011-5-18				
					if(xm.getKyClass().equals("1")){
						xm.setInternalNum("H"+contractNum.getValue());
					}else if(xm.getKyClass().equals("2")){
						xm.setInternalNum("Z"+contractNum.getValue());
					}					
					xm.setKyScale(String.valueOf(scale.getSelectedIndex()));
					xm.setKyJf(kyJf);
					xm.setKyLy(ly.getValue().trim());
					xm.setKuId(user.getKuId());
					xm.setKyLx(GhXm.KYXM);
					xm.setKyProgress(String.valueOf(progress.getSelectedIndex()));
					xm.setAuditState(null);
					xm.setAuditUid(null);
					xm.setReason(null);
					
					xm.setCommitCom(commitCom.getValue());
					xm.setCommitComPlace(commitComplace.getValue());
					xm.setAcceptCom(acceptCom.getValue());
					xm.setAcceptComPlace(acceptComPlace.getValue());
					
					xm.setKyContraNum(contractNum.getValue());
					xm.setKyFinishUnit(finishUnit.getValue());
					if(setFinishTime.getValue()!=null){
						xm.setKySetFinishTime(DateUtil.getDateString(setFinishTime.getValue()));
					}
					if(realFinishTime.getValue()!=null){
						xm.setKyRealFinishTime(DateUtil.getDateString(realFinishTime.getValue()));
					}
					xm.setKyContentFinishConditon(conFinishCondition.getValue());
					xm.setKyFruit(fruit.getValue());
					
					xmService.update(xm);
					
//					List jslwlist=jsxmService.findByXmidAndtype(xm.getKyId(), GhJsxm.KYXM);
//					for(int i=0;i<jslwlist.size();i++){
//						GhJsxm js=(GhJsxm)jslwlist.get(i);
//						if(!prostaffs.getValue().trim().contains(js.getUser().getKuName().trim())){
//							jsxmService.delete(js);
//						}
//					}
					
					// �����洢
					UploadFJ.ListModelListUploadCommand(fileModel, xm.getKyId());//��ͬ��
					
					GhJsxm jsxm=jsxmService.findByXmidAndKuidAndType(xm.getKyId(), user.getKuId(),GhJsxm.KYXM);
					if(jsxm!=null){
						jsxm.setKyCdrw(String.valueOf(cdrw.getSelectedIndex()));
						jsxm.setYjId(research.getSelectedIndex()!=0?((GhYjfx)research.getSelectedItem().getValue()).getGyId():0L);
						jsxm.setKyGx(kyGx);
						jsxmService.update(jsxm);
						Messagebox.show("����ɹ���","��ʾ",Messagebox.OK,Messagebox.INFORMATION);
						Events.postEvent(Events.ON_CHANGE, this, null);
					}else{
						jsxm=new GhJsxm();
						jsxm.setKyId(xm.getKyId());
						jsxm.setJsxmType(GhJsxm.KYXM);
						jsxm.setKuId(user.getKuId());
						jsxm.setKyCdrw(String.valueOf(cdrw.getSelectedIndex()));
						jsxm.setYjId(research.getSelectedIndex()!=0?((GhYjfx)research.getSelectedItem().getValue()).getGyId():0L);
						jsxm.setKyGx(kyGx);
						jsxm.setKyMemberName(user.getKuName());
						jsxm.setKyTaskDesc("");
						jsxmService.save(jsxm);
						Messagebox.show("����ɹ���","��ʾ",Messagebox.OK,Messagebox.INFORMATION);
						Events.postEvent(Events.ON_CHANGE, this, null);
					}
				}else{
					if(!prostaffs.getValue().trim().contains(user.getKuName())&&!proman.getValue().trim().contains(user.getKuName())){
						Messagebox.show("��Ŀ����Ա��������������������ϵ��Ŀ��Ϣ����ˣ�", "����", Messagebox.OK, Messagebox.EXCLAMATION);
						return;
					}
					//ֻ���½�ʦ��Ŀ��
					GhJsxm jsxm=jsxmService.findByXmidAndKuidAndType(xm.getKyId(), user.getKuId(),GhJsxm.KYXM);
					if(jsxm!=null){
						jsxm.setKyCdrw(String.valueOf(cdrw.getSelectedIndex()));
						jsxm.setYjId(research.getSelectedIndex()!=0?((GhYjfx)research.getSelectedItem().getValue()).getGyId():0L);
						jsxm.setKyGx(kyGx);
						jsxmService.update(jsxm);
						Messagebox.show("����ɹ���","��ʾ",Messagebox.OK,Messagebox.INFORMATION);
						Events.postEvent(Events.ON_CHANGE, this, null);
					}else{
						jsxm=new GhJsxm();
						jsxm.setKyId(xm.getKyId());
						jsxm.setJsxmType(GhJsxm.KYXM);
						jsxm.setKuId(user.getKuId());
						jsxm.setKyCdrw(String.valueOf(cdrw.getSelectedIndex()));
						jsxm.setYjId(research.getSelectedIndex()!=0?((GhYjfx)research.getSelectedItem().getValue()).getGyId():0L);
						jsxm.setKyGx(kyGx);
						jsxm.setKyMemberName(user.getKuName());
						jsxm.setKyTaskDesc("");
						jsxmService.save(jsxm);
						Messagebox.show("����ɹ���","��ʾ",Messagebox.OK,Messagebox.INFORMATION);
						Events.postEvent(Events.ON_CHANGE, this, null);
					}
				}
			}
	}
	
		
    public void initXmWindow(String xmmc,String xmly,String porman,Short lx){
    	GhXm cxm=xmService.findByNameAndLyAndLxAndFzr(xmmc, lx, xmly, porman);
    	if(cxm!=null){
    		xm=cxm;
    		cgmc.setDisabled(true);
    		number.setValue(cxm.getKyNumber());number.setDisabled(true);
			ly.setValue(cxm.getKyLy());  ly.setDisabled(true);
			jingfei.setValue(BigDecimal.valueOf(cxm.getKyJf()));jingfei.setDisabled(true);
			prostaffs.setValue(cxm.getKyProstaffs());prostaffs.setDisabled(true);
			proman.setValue(cxm.getKyProman());proman.setDisabled(true);
			target.setValue(cxm.getKyTarget());target.setDisabled(true);
			identtime.setValue(DateUtil.getDate(cxm.getKyIdenttime()));identtime.setDisabled(true);
			level.setValue(cxm.getKyLevel());level.setDisabled(true);
			
			//������Ŀ��Ϣ
			contractNum.setValue(cxm.getKyContraNum());contractNum.setDisabled(true);
			finishUnit.setValue(cxm.getKyFinishUnit());finishUnit.setDisabled(true);
			commitCom.setValue(cxm.getCommitCom());commitCom.setDisabled(true);
			commitComplace.setValue(cxm.getCommitComPlace());commitComplace.setDisabled(true);
			acceptCom.setValue(cxm.getAcceptCom());acceptCom.setDisabled(true);
			acceptComPlace.setValue(cxm.getAcceptComPlace());acceptComPlace.setDisabled(true);
			
			conFinishCondition.setValue(cxm.getKyContentFinishConditon());conFinishCondition.setDisabled(true);
			fruit.setValue(cxm.getKyFruit());fruit.setDisabled(true);
			setFinishTime.setValue(DateUtil.getDate(cxm.getKySetFinishTime()));setFinishTime.setDisabled(true);
			realFinishTime.setValue(DateUtil.getDate(cxm.getKyRealFinishTime()));realFinishTime.setDisabled(true);
			
			// ѧ�Ʒ���
			if (cxm.getKySubjetype() == null || cxm.getKySubjetype() == "") {
				subjetype.setSelectedIndex(0);
				subjetype.setDisabled(true);
			} else {
				subjetype.setSelectedIndex(Integer.valueOf(cxm.getKySubjetype().trim()));
				subjetype.setDisabled(true);
			}
			// ��ͬ����
			if (cxm.getContractType()== null || cxm.getContractType() == "") {
				contraTypeListbox.setSelectedIndex(0);
				contraTypeListbox.setDisabled(true);
			} else {
				contraTypeListbox.setSelectedIndex(Integer.valueOf(cxm.getContractType().trim()));
				contraTypeListbox.setDisabled(true);
			}
			// ��Ŀ����
			if (cxm.getKyScale() == null || cxm.getKyScale() == "") {
				scale.setSelectedIndex(0);
				scale.setDisabled(true);
			} else {
				scale.setSelectedIndex(Integer.valueOf(cxm.getKyScale().trim()));
				scale.setDisabled(true);
			}
			// ��Ŀ��չ
			if (cxm.getKyProgress() == null || cxm.getKyProgress() == "") {
				progress.setSelectedIndex(0);
				progress.setDisabled(true);
			} else {
				progress.setSelectedIndex(Integer.valueOf(cxm.getKyProgress().trim()));
				progress.setDisabled(true);
			}
		    writeuser.setValue(cxm.getUser().getKuName());
            //��ͬ���
            if (xm.getKyContraNum()!= null&&!xm.getKyContraNum().equals("")) {
            	contractNum.setValue(xm.getKyContraNum());
			} else {
				contractNum.setValue("");
			}
//            //��ɵ�λ
//            if (xm.getKyFinishUnit()!= null&&!xm.getKyFinishUnit().equals("")) {
//            	finishUnit.setValue(xm.getKyFinishUnit());
//			} else {
//				finishUnit.setValue("");
//			}
            //�涨���ʱ��
            if(xm.getKySetFinishTime()==null||xm.getKySetFinishTime().equals("")){
            	setFinishTime.setValue(null);
    		}else{
    			setFinishTime.setValue(DateUtil.getDate(xm.getKySetFinishTime()));
    		}
            //ʵ��������
            if(xm.getKyRealFinishTime()==null||xm.getKyRealFinishTime().equals("")){
            	realFinishTime.setValue(null);
    		}else{
    			realFinishTime.setValue(DateUtil.getDate(xm.getKyRealFinishTime()));
    		}
            //������
            if (xm.getKyContentFinishConditon()!= null&&!xm.getKyContentFinishConditon().equals("")) {
            	conFinishCondition.setValue(xm.getKyContentFinishConditon());
			} else {
				conFinishCondition.setValue("");
			}
            //�ɹ�����Ч
            if (xm.getKyFruit()!= null&&!xm.getKyFruit().equals("")) {
            	fruit.setValue(xm.getKyFruit());
			} else {
				fruit.setValue("");
			}
		    List namelist=new ArrayList();
			String str=prostaffs.getValue().trim();
			if(str.contains("��")||str.contains(",")){
				throw new WrongValueException(prostaffs, "ȫ��������д������ѡ��ٺţ�");
			}else{
			while (str.contains("��")) {
					Label lb = new Label(str.substring(0,str.indexOf("��")));
					namelist.add(lb.getValue().trim());
					str = str.substring(str.indexOf("��") + 1, str.length());
				
			}
			namelist.add(str.trim());
			gongxian.setValue(namelist.indexOf(user.getKuName())+1+"");
			}
    	}
    	else{
    		xm=null;
//			kaishi.setValue("");
//			number.setValue("");
//			jieshu.setValue(""); 
//			jingfei.setValue("");
//			gongxian.setValue("0");
//			prostaffs.setValue("");
////		register.setValue("");
//			target.setValue("");
//			identtime.setValue("");
//			level.setValue("");
//			writeuser.setValue(user.getKuName());
			
//			number.setDisabled(false);
//			identtime.setDisabled(false);
//			level.setDisabled(false);
//			target.setDisabled(false);
//			lxsj.setValue("");
//			lxsj.setDisabled(false);
//			kaishi.setDisabled(false);
//			jieshu.setDisabled(false);
//			jingfei.setDisabled(false);
//			prostaffs.setDisabled(false);
//			subjetype.setSelectedIndex(0);
//			subjetype.setDisabled(false);
//			kyclass.setSelectedIndex(0);
//			kyclass.setDisabled(false);
//			scale.setSelectedIndex(0);
//			scale.setDisabled(false);
//			progress.setSelectedIndex(0);
//			progress.setDisabled(false);
    	}
    }
    /**
	* <li>����������ѡ����Ŀ���ơ�
	 * @param null
	 * @return null
	 */
    public void onClick$choice(){
    	Map args=new HashMap();
    	args.put("user", user);
    	args.put("lx", GhXm.KYXM);
    	args.put("type", GhJsxm.KYXM);
    	SelectXmWindow sxw=(SelectXmWindow)Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/kyxm/selectxm.zul", null, args);
    	sxw.initShow();
    	sxw.doHighlighted();
    	sxw.addEventListener(Events.ON_CHANGE, new EventListener(){

			public void onEvent(Event arg0) throws Exception {
				SelectXmWindow sxw=(SelectXmWindow)arg0.getTarget();
				if(sxw.getXmlist().getSelectedItem()!=null){
				GhXm Xm=(GhXm)sxw.getXmlist().getSelectedItem().getValue();
				if(!Xm.getKyProstaffs().contains(user.getKuName())){
					Messagebox.show("��������Ŀ���Ա������ϵ��Ŀ��д��������룡", "��ʾ��", Messagebox.OK, Messagebox.EXCLAMATION);
				}
				else{
				xm=Xm;
				cgmc.setValue(Xm.getKyMc());cgmc.setDisabled(true);
				number.setValue(Xm.getKyNumber());number.setDisabled(true);
				ly.setDisabled(true);
				jingfei.setValue(BigDecimal.valueOf(Xm.getKyJf()));jingfei.setDisabled(true);
//				prostaffs.setValue(Xm.getKyProstaffs());prostaffs.setDisabled(true);
				proman.setValue(Xm.getKyProman());proman.setDisabled(true);
				target.setValue(Xm.getKyTarget());target.setDisabled(true);
				identtime.setValue(DateUtil.getDate(Xm.getKyIdenttime()));identtime.setDisabled(true);
				level.setValue(Xm.getKyLevel());level.setDisabled(true);				
				
				commitCom.setValue(Xm.getCommitCom());commitCom.setDisabled(true);
				commitComplace.setValue(Xm.getCommitComPlace());commitComplace.setDisabled(true);
				acceptCom.setValue(Xm.getAcceptCom());acceptCom.setDisabled(true);
				acceptComPlace.setValue(Xm.getAcceptComPlace());acceptComPlace.setDisabled(true);
				
				// ѧ�Ʒ���
				if (Xm.getKySubjetype() == null || Xm.getKySubjetype() == "") {
					subjetype.setSelectedIndex(0);
					subjetype.setDisabled(true);
				} else {
					subjetype.setSelectedIndex(Integer.valueOf(Xm.getKySubjetype().trim()));
					subjetype.setDisabled(true);
				}
				// ��ͬ����
				if (Xm.getContractType() == null || Xm.getContractType() == "") {
					contraTypeListbox.setSelectedIndex(0);
					contraTypeListbox.setDisabled(true);
				} else {
					contraTypeListbox.setSelectedIndex(Integer.valueOf(Xm.getContractType().trim()));
					contraTypeListbox.setDisabled(true);
				}
				
				// ��Ŀ����
				if (Xm.getKyScale() == null || Xm.getKyScale() == "") {
					scale.setSelectedIndex(0);
					scale.setDisabled(true);
				} else {
					scale.setSelectedIndex(Integer.valueOf(Xm.getKyScale().trim()));
					scale.setDisabled(true);
				}
				// ��Ŀ��չ
				if (Xm.getKyProgress() == null || Xm.getKyProgress() == "") {
					progress.setSelectedIndex(0);
					progress.setDisabled(true);
				} else {
					progress.setSelectedIndex(Integer.valueOf(Xm.getKyProgress().trim()));
					progress.setDisabled(true);
				}

				 writeuser.setValue(Xm.getUser().getKuName());		
	            //��ͬ���
	            if (xm.getKyContraNum()!= null&&!xm.getKyContraNum().equals("")) {
	            	contractNum.setValue(xm.getKyContraNum());
				} else {
					contractNum.setValue("");
				}
//	            //��ɵ�λ
//	            if (xm.getKyFinishUnit()!= null&&!xm.getKyFinishUnit().equals("")) {
//	            	finishUnit.setValue(xm.getKyFinishUnit());
//				} else {
//					finishUnit.setValue("");
//				}
	            //�涨���ʱ��
	            if(xm.getKySetFinishTime()==null||xm.getKySetFinishTime().equals("")){
	            	setFinishTime.setValue(null);
	    		}else{
	    			setFinishTime.setValue(DateUtil.getDate(xm.getKySetFinishTime()));
	    		}
	            //ʵ��������
	            if(xm.getKyRealFinishTime()==null||xm.getKyRealFinishTime().equals("")){
	            	realFinishTime.setValue(null);
	    		}else{
	    			realFinishTime.setValue(DateUtil.getDate(xm.getKyRealFinishTime()));
	    		}
	            //������
	            if (xm.getKyContentFinishConditon()!= null&&!xm.getKyContentFinishConditon().equals("")) {
	            	conFinishCondition.setValue(xm.getKyContentFinishConditon());
				} else {
					conFinishCondition.setValue("");
				}
	            //�ɹ�����Ч
	            if (xm.getKyFruit()!= null&&!xm.getKyFruit().equals("")) {
	            	fruit.setValue(xm.getKyFruit());
				} else {
					fruit.setValue("");
				}
				sxw.detach();				
				}
			 }
			}
    	});
    }
    /**
     * �ϴ�������ͬ��
     * @date 2011-5-18
     * @throws InterruptedException
     */
	public void onClick$upFile() throws InterruptedException {
		UploadFJ ufj = new UploadFJ(true);
		Media media = ufj.Upload(this.getDesktop(), 23, 10, "�������ܳ���10MB","����------�ļ���С���ܳ���10MB");
		if (media == null) {
			return;
		}
		fileModel.add(ufj);
		rowFile.setVisible(true);upList.setVisible(true);
		deUpload.setVisible(true);checkBackup.setVisible(true);
	}
	/**
	 * <li>ɾ���ϴ����ļ�������ѡ��
	 * @author bobo 2010-4-11
	 */
	public void onClick$deUpload() {
		// ȥ���ɵĴ������
		IsZipExists.delZipFile(this.getDesktop().getWebApp().getRealPath("/upload/xkjs/").trim()+ "\\" + "_" + xm.getKyId() + "_" + ".zip");
		Listitem it = upList.getSelectedItem();
		if (it == null) {
			if (fileModel.getSize() > 0) {
				it = upList.getItemAtIndex(0);
			}
		}
		UploadFJ temp = (UploadFJ) it.getValue();
		if (fileModel.getSize() == 1) {
			temp.DeleteFJ();
			fileModel.remove(it.getValue());
			rowFile.setVisible(true);
		} else if (fileModel.getSize() > 1) {
			temp.DeleteFJ();
			fileModel.remove(it.getValue());
			rowFile.setVisible(true);upList.setVisible(true);
			deUpload.setVisible(true);
			checkBackup.setVisible(true);
		}
	}

	public void onClick$reset() {
		// ȥ���ɵĴ������
		IsZipExists.delZipFile(this.getDesktop().getWebApp().getRealPath("/upload/xkjs/").trim()+ "\\" + "_" + xm.getKyId() + "_" + ".zip");
		List list = fileModel.getInnerList();
		for (int i = 0; i < list.size(); i++) {
			((UploadFJ) list.get(i)).DeleteFJ();
		}
		initWindow();
		fileModel.clear();
		rowFile.setVisible(true);
	}
	
	/**
	* <li>����������������ظ�����
	 * @param null
	 * @return null
	 */
	public void onClick$downFileZip(){
		DownloadFJ.ListModelListDownloadCommand(this.getDesktop(), this.xm.getKyId(), fileModel);
	}

	/**
	* <li>���������������ļ����ء�
	 * @param null
	 * @return null
	 */
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
	
	/**
	 * �������ڱ���
	 * @throws IOException 
	 * @throws WriteException 
	 * @throws InterruptedException 
	 */
	public void onClick$exportMiddReport() throws WriteException, IOException, InterruptedException{
		Map arg=new HashMap();
		arg.put("hProgram", this.xm);
		HMiddReportWindow hmWin = (HMiddReportWindow) Executions.createComponents
								("/admin/personal/data/teacherinfo/kyqk/kyxm/HmiddReport.zul", null, arg);
		hmWin.doHighlighted();
	}

	/**
	 * ���ɽ����
	 * @throws IOException 
	 * @throws WriteException 
	 * @throws InterruptedException 
	 */
	public void onClick$resultReport() throws WriteException, IOException, InterruptedException{
		Map arg=new HashMap();
		arg.put("hProgram", this.xm);
		HResultReportWindow hrWin = (HResultReportWindow) Executions.createComponents
								("/admin/personal/data/teacherinfo/kyqk/kyxm/HresultReport.zul", null, arg);
		hrWin.doHighlighted();
	}

	public void initShow() {
	}
	public void onClick$close() {
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
