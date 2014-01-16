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
import org.iti.projectmanage.science.member.service.ProjectMemberService;
import org.iti.xypt.ui.base.FrameworkWindow;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
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
 * @author bobo
 */
public class KycgYhdWindow extends FrameworkWindow {	
	private static final long serialVersionUID = -1373071045258031974L;
	private Logger  log  = Logger.getLogger(KycgYhdWindow.class);	
	GhXm xm = (GhXm) Executions.getCurrent().getArg().get("zxxm");
	Long kuid = (Long) Executions.getCurrent().getArg().get("useKuid");

	private WkTUser user;	
	
	private Decimalbox jingfei ;
	private Textbox ly,number,prostaffs, proman, target,cgmc;
	private Label gongxian,writeuser,internalNum,writeuser1;
	private Listbox  subjetype,scale, progress, cdrw;	
	private YjfxListbox research;
	private Datebox identtime,lxsj,kaishi,jieshu;
//	Button downFileZip;
	private Row rowFile,rowFile2,rowFile3,rowFile4,proStaffsRow;
	private Listbox upList,upList2,upList3,upList4;
	private ListModelList fileModel,fileModel2,fileModel3,fileModel4;
	private Button downFile,deUpload,upFile;//�ϴ�������
	private Button upFile2,downFile2,deUpload2;//�ϴ���ͬ��
	private Button upFile3,downFile3,deUpload3;//�ϴ����ڱ���
	private Button upFile4,downFile4,deUpload4;//�ϴ������	
	private Checkbox checkBackup,checkBackup2,checkBackup3,checkBackup4;
	private Toolbarbutton exportMiddReport,resultReport;
	//������Ϣ
	private Textbox contractNum,finishUnit,conFinishCondition,fruit,level;
	private Datebox setFinishTime,realFinishTime;
	private Decimalbox grants;
	
	private XmService xmService;
	private CgService cgService;
	private GhFileService ghfileService;
	private JsxmService jsxmService;
	private ProjectMemberService projectmemberService;
		 	
	private String  staff="";	
	
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");		
		//�ϴ�������
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
		//�ϴ���ͬ��
		fileModel2 = new ListModelList(new ArrayList());
		upList2.setModel(fileModel2);
		upList2.addEventListener(Events.ON_SELECT, new EventListener(){
			public void onEvent(Event arg0) throws Exception {
				 Listitem item =(Listitem)upList2.getSelectedItem();
				 UploadFJ file =(UploadFJ)item.getValue();
				 if(file.getGf()!=null&&!file.getGf().equals("")){
			         if(file.getGf().getFbackup()==1){
							checkBackup2.setChecked(true);							
					 }else if(file.getGf().getFbackup()==0){
							checkBackup2.setChecked(false);							
					 }
				 }
		    }
		});	
		
		//�ϴ����ڱ���
		fileModel3 = new ListModelList(new ArrayList());
		upList3.setModel(fileModel3);
		upList3.addEventListener(Events.ON_SELECT, new EventListener(){
			public void onEvent(Event arg0) throws Exception {
 				 Listitem item =(Listitem)upList3.getSelectedItem();
 				 UploadFJ file =(UploadFJ)item.getValue();
 				 if(file.getGf()!=null&&!file.getGf().equals("")){
			         if(file.getGf().getFbackup()==1){
							checkBackup3.setChecked(true);							
					 }else if(file.getGf().getFbackup()==0){
							checkBackup3.setChecked(false);							
					 }
 				 }
		    }
		});
		//�ϴ������
		fileModel4 = new ListModelList(new ArrayList());
		upList4.setModel(fileModel4);
		upList4.addEventListener(Events.ON_SELECT, new EventListener(){
			public void onEvent(Event arg0) throws Exception {
 				 Listitem item =(Listitem)upList4.getSelectedItem();
 				 UploadFJ file =(UploadFJ)item.getValue();
 				 if(file.getGf()!=null&&!file.getGf().equals("")){
			         if(file.getGf().getFbackup()==1){
							checkBackup4.setChecked(true);							
					 }else if(file.getGf().getFbackup()==0){
							checkBackup4.setChecked(false);							
					 }
 				 }
		    }
		});		
	}
	
	/**
	 * ���ܣ���ʼ������
	 * @Data  2011-5-18
	 * @param null
	 */
	public void initWindow() {
		List subtype = new ArrayList();
		List subscale = new ArrayList();
		List subprogress = new ArrayList();
		List subcdrw = new ArrayList();
		String[] Subtype = { "-��ѡ��-", "��Ȼ��ѧ", "����ѧ", "����"};
		for (int i = 0; i < Subtype.length; i++) {
			subtype.add(Subtype[i]);
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
			number.setValue(xm.getKyNumber());// ��Ŀ���
			cgmc.setValue(xm.getKyMc());cgmc.setDisabled(true);// ����			
			if (xm.getKyLy() != null) {// ��Դ����Ϊ��
				ly.setValue(xm.getKyLy());
			}
//2011-5-18			
			internalNum.setValue(xm.getInternalNum());	
			if(xm.getKyLxsj()==null||xm.getKyLxsj().equals("")){//����ʱ��
				lxsj.setValue(null);
    		}else{
    			lxsj.setValue(DateUtil.getDate(xm.getKyLxsj()));
    		}
			if(xm.getKyKssj()==null||xm.getKyKssj().equals("")){// ��ʼʱ��
				kaishi.setValue(null);
    		}else{
    			kaishi.setValue(DateUtil.getDate(xm.getKyKssj()));
    		}
			if(xm.getKyJssj()==null||xm.getKyJssj().equals("")){// ����ʱ��
				jieshu.setValue(null);
    		}else{
    			jieshu.setValue(DateUtil.getDate(xm.getKyJssj()));
    		}
				
			//��Ŀ���Ա����ѯ����Ŀ��Ա��GH_PROMEMBER
			proStaffsRow.setVisible(true);		
			
			List mlist=projectmemberService.findByKyXmId(xm.getKyId());
			proStaffsRow.setVisible(true);
			if(mlist.size()!=0){
				staff="";
				String proMem="";
				for(int i=0;i<mlist.size();i++){
					GhJsxm member=(GhJsxm)mlist.get(i);						
					staff+=member.getKyMemberName()+"��";
				}
				if(staff.length()>=25) {
					proMem = staff.substring(0, 24)+"...";							
					prostaffs.setValue(proMem);
				}
				else {
					prostaffs.setValue(staff.substring(0, staff.length()-1));
				}		
			}
			else{
				prostaffs.setValue(xm.getKyProman());
			}
			
			if (xm.getKyJf() != null) {// ���ѿ���Ϊ��
				jingfei.setValue(BigDecimal.valueOf(xm.getKyJf()));
			}			
			if (xm.getKySubjetype() == null || xm.getKySubjetype() == "") {// ѧ�Ʒ���
				subjetype.setSelectedIndex(0);
			} else {
				subjetype.setSelectedIndex(Integer.valueOf(xm.getKySubjetype().trim()));
			}				
			if (xm.getKyScale() == null || xm.getKyScale() == "") {// ��Ŀ����
				scale.setSelectedIndex(0);
			} else {
				scale.setSelectedIndex(Integer.valueOf(xm.getKyScale().trim()));
			}
			
			if (xm.getKyProgress() == null || xm.getKyProgress() == "") {// ��Ŀ��չ
				progress.setSelectedIndex(0);
			} else {
				progress.setSelectedIndex(Integer.valueOf(xm.getKyProgress().trim()));
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
			// ���˹���
			
			List manager= projectmemberService.findByKyIdAndKuId(xm.getKyId(),user.getKuId());
			if(manager.size()!=0)
			{
				GhJsxm man=(GhJsxm) manager.get(0);
				gongxian.setValue(man.getKyGx().toString());
			}
			else
			{
				gongxian.setValue("1");
			}
			//��ʦ��Ŀ
            GhJsxm jx=(GhJsxm)jsxmService.findByXmidAndKuidAndType(xm.getKyId(), user.getKuId(),GhJsxm.KYXM);
            if(jx!=null){
            	// ���˹���
//            	gongxian.setValue(jx.getKyGx()!=null?jx.getKyGx()+"":"0");
            	research.initYjfzList(user.getKuId(), jx.getYjId());
    			// ���˳е����������
    			if (jx.getKyCdrw() == null || jx.getKyCdrw() == "") {
    				cdrw.setSelectedIndex(0);
    			} else {
    				cdrw.setSelectedIndex(Integer.valueOf(jx.getKyCdrw().trim()));
    			}
            }else{
//            	gongxian.setValue("0");
            	research.setSelectedIndex(0);
            	cdrw.setSelectedIndex(0);
            }
            
            //������Ϣ����ͬ���
            if (xm.getKyContraNum()!= null&&!xm.getKyContraNum().equals("")) {
            	contractNum.setValue(xm.getKyContraNum());
			} else {
				contractNum.setValue("");
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
            // ��������
            if (xm.getKyGrants()!= null) {
            	grants.setValue(BigDecimal.valueOf(xm.getKyGrants()));
			}
        	            
            writeuser.setValue(xm.getUser().getKuName());
            writeuser1.setValue(xm.getUser().getKuName());
			if(xm.getKuId().intValue()!=user.getKuId().intValue()){//�Ǹ��û���������Ŀ�������Բ����Ա༭				
				number.setDisabled(true);ly.setDisabled(true);lxsj.setDisabled(true);
				kaishi.setDisabled(true);jieshu.setDisabled(true);
				jingfei.setDisabled(true);prostaffs.setDisabled(true);
				proman.setDisabled(true);
//				register.setDisabled(true);
				target.setDisabled(true);identtime.setDisabled(true);
				level.setDisabled(true);subjetype.setDisabled(true);
				scale.setDisabled(true);
				progress.setDisabled(true);
				
				//������Ϣ
				contractNum.setDisabled(true);finishUnit.setDisabled(true);
				conFinishCondition.setDisabled(true);fruit.setDisabled(true);
				setFinishTime.setDisabled(true);realFinishTime.setDisabled(true);
				grants.setDisabled(true);
				
				//�������ϴ�����				
				deUpload.setDisabled(true);
				upFile.setDisabled(true);
				downFile.setDisabled(true);
				checkBackup.setDisabled(true);
				
				deUpload2.setDisabled(true);
				upFile2.setDisabled(true);
				downFile2.setDisabled(true);
				checkBackup2.setDisabled(true);
				
				deUpload3.setDisabled(true);
				upFile3.setDisabled(true);
				downFile3.setDisabled(true);
				checkBackup3.setDisabled(true);
				
				deUpload4.setDisabled(true);
				upFile4.setDisabled(true);
				downFile4.setDisabled(true);
				checkBackup4.setDisabled(true);
//				downFileZip2.setDisabled(true);
				
				//��������
				exportMiddReport.setDisabled(true);
				resultReport.setDisabled(true);
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
			kaishi.setValue(null);
			jieshu.setValue(null);
			jingfei.setValue(BigDecimal.valueOf(0l));
//			gongxian.setValue("0");
//			xm.setKyProstaffs("");
			proman.setValue("");
//			register.setValue("");
			target.setValue("");
			identtime.setValue(null);
			level.setValue("");
			writeuser.setValue(user.getKuName());
			writeuser1.setValue(user.getKuName());
			
			//������Ϣ
			contractNum.setValue("");finishUnit.setValue("");
			conFinishCondition.setValue("");fruit.setValue("");
			setFinishTime.setValue(null);realFinishTime.setValue(null);
			grants.setValue(BigDecimal.valueOf(0l));
			
			deUpload.setDisabled(true);
			downFile.setDisabled(true);
			checkBackup.setDisabled(true);
			
			deUpload2.setDisabled(true);
			downFile2.setDisabled(true);
			checkBackup2.setDisabled(true);
			
			deUpload3.setDisabled(true);
			downFile3.setDisabled(true);
			checkBackup3.setDisabled(true);
			
			deUpload4.setDisabled(true);
			downFile4.setDisabled(true);
			checkBackup4.setDisabled(true);
//			downFileZip.setDisabled(true);
			//ѡ����Ŀ����
//			if(cgmc.getModel().getSize()>0){
//			cgmc.addEventListener(Events.ON_SELECT, new EventListener(){
//
//				public void onEvent(Event arg0) throws Exception {
//					if(cgmc.getSelectedItem()!=null){
//						Object [] mc=(Object [])cgmc.getSelectedItem().getValue();
//						GhXm xm=(GhXm)xmService.get(GhXm.class,(Long)mc[1]);
//						number.setValue(xm.getKyNumber());number.setDisabled(true);
//						ly.setValue(xm.getKyLy());ly.setDisabled(true);
//						lxsj.setValue(xm.getKyLxsj());lxsj.setDisabled(true);
//						kaishi.setValue(xm.getKyKssj());kaishi.setDisabled(true);
//						jieshu.setValue(xm.getKyJssj());jieshu.setDisabled(true);
//						jingfei.setValue(xm.getKyJf()+"");jingfei.setDisabled(true);
//						prostaffs.setValue(xm.getKyProstaffs());prostaffs.setDisabled(true);
//						proman.setValue(xm.getKyProman());proman.setDisabled(true);
////						register.setValue(xm.getKyRegister());register.setDisabled(true);
//						target.setValue(xm.getKyTarget());target.setDisabled(true);
//						identtime.setValue(xm.getKyIdenttime());identtime.setDisabled(true);
//						level.setValue(xm.getKyLevel());level.setDisabled(true);
//						// ѧ�Ʒ���
//						if (xm.getKySubjetype() == null || xm.getKySubjetype() == "") {
//							subjetype.setSelectedIndex(0);
//							subjetype.setDisabled(true);
//						} else {
//							subjetype.setSelectedIndex(Integer.valueOf(xm.getKySubjetype().trim()));
//							subjetype.setDisabled(true);
//						}
//						// ��Ŀ���
//						if (xm.getKyClass() == null || xm.getKyClass() == "") {
//							kyclass.setSelectedIndex(0);
//							kyclass.setDisabled(true);
//						} else {
//							
//							kyclass.setSelectedIndex(Integer.valueOf(xm.getKyClass().trim()));
//							kyclass.setDisabled(true);
//						}
//						// ��Ŀ����
//						if (xm.getKyScale() == null || xm.getKyScale() == "") {
//							scale.setSelectedIndex(0);
//							scale.setDisabled(true);
//						} else {
//							scale.setSelectedIndex(Integer.valueOf(xm.getKyScale().trim()));
//							scale.setDisabled(true);
//						}
//						// ��Ŀ��չ
//						if (xm.getKyProgress() == null || xm.getKyProgress() == "") {
//							progress.setSelectedIndex(0);
//							progress.setDisabled(true);
//						} else {
//							progress.setSelectedIndex(Integer.valueOf(xm.getKyProgress().trim()));
//							progress.setDisabled(true);
//						}
////						// �Ƿ��
////						if (xm.getKyPrize() == null || xm.getKyPrize() == "" || xm.getKyPrize().trim().equalsIgnoreCase("1")) {
////							prize.setSelectedIndex(0);
////						} else if (xm.getKyPrize().trim().equalsIgnoreCase("2")) {// �� 1������2�����ǡ�
////							prize.setSelectedIndex(1);
////						}
//						if(xm.getKyProstaffs().contains(user.getKuName())&&xm.getKyProstaffs().contains(",")){
//							List namelist=new ArrayList();
//							String str=xm.getKyProstaffs().trim();
//							while (str.contains("��")) {
//									Label lb = new Label(str.substring(0,str.indexOf("��")));
//									namelist.add(lb.getValue());
//									str = str.substring(str.indexOf("��") + 1, str.length());
//								
//							}
//							namelist.add(str);
//							gongxian.setValue(namelist.indexOf(user.getKuName())+1+"");
//							gongxian.setDisabled(true);
//						}else{
//							gongxian.setValue("0");
//							gongxian.setDisabled(true);
//						}
//						//�������
//						deUpload.setDisabled(true);
//						upFile.setDisabled(true);
//						List fjList = ghfileService.findByFxmIdandFType(xm.getKyId(), 1);
//						if (fjList.size() == 0) {// �޸���
//							rowFile.setVisible(false);
//							downFileZip.setDisabled(true);//�޸������ذ�ť��Ч
//						} else {// �и���
//							// ��ʼ������
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
//							}
//						
//					}
//				}
//				});
//			}
		}
		// ����
		if (xm == null) {// �����
			rowFile.setVisible(true);
			rowFile2.setVisible(true);
			rowFile3.setVisible(true);
			rowFile4.setVisible(true);
		} else {
			//�༭�޸ģ�������
			List fjList = ghfileService.findByFxmIdandFType(xm.getKyId(), 19);
			if (fjList.size() == 0) {// �޸���
				rowFile.setVisible(true);	
			} else {				// �и�������ʼ������
				for (int i = fjList.size()-1; i >=0 ; i--) {
					UploadFJ ufj = new UploadFJ(false);
					try {
						ufj.ReadFJ(this.getDesktop(), (GhFile) fjList.get(i));
						//2011-5-19						
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
			
			//��ͬ��
			List fjList2 = ghfileService.findByFxmIdandFType(xm.getKyId(), 20);
			if (fjList2.size() == 0) {// �޸���
				rowFile2.setVisible(true);
			} else {// �и�������ʼ������
				for (int i = fjList2.size()-1; i >=0 ; i--) {
					UploadFJ ufj = new UploadFJ(false);
					try {
						ufj.ReadFJ(this.getDesktop(), (GhFile) fjList2.get(i));
						//2011-5-19
						GhFile f2 = (GhFile)fjList2.get(fjList2.size()-1);
						if(f2.getFbackup()==1){
							checkBackup2.setChecked(true);							
						}else if(f2.getFbackup()==0){
							checkBackup2.setChecked(false);							
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					fileModel2.add(ufj);
				}
				upList2.setModel(fileModel2);
				upList2.setVisible(true);downFile2.setVisible(true);
				deUpload2.setVisible(true);rowFile2.setVisible(true);
				checkBackup2.setVisible(true);
			}
			
			//���ڱ���
			List fjList3 = ghfileService.findByFxmIdandFType(xm.getKyId(), 21);
			if (fjList3.size() == 0) {// �޸���
				rowFile3.setVisible(true);
			} else {// �и�������ʼ������
				for (int i = fjList3.size()-1; i >=0 ; i--) {
					UploadFJ ufj = new UploadFJ(false);
					try {
						ufj.ReadFJ(this.getDesktop(), (GhFile) fjList3.get(i));
						//2011-5-19						
						GhFile f3 = (GhFile)fjList3.get(fjList3.size()-1);
						if(f3.getFbackup()==1){
							checkBackup3.setChecked(true);							
						}else if(f3.getFbackup()==0){
							checkBackup3.setChecked(false);							
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					fileModel3.add(ufj);
				}
				upList3.setModel(fileModel3);
				upList3.setVisible(true);downFile3.setVisible(true);
				deUpload3.setVisible(true);rowFile3.setVisible(true);
				checkBackup3.setVisible(true);
			}
			
			//�����
			List fjList4 = ghfileService.findByFxmIdandFType(xm.getKyId(), 22);
			if (fjList4.size() == 0) {// �޸���
				rowFile4.setVisible(true);
			} else {// �и�������ʼ������
				for (int i = fjList4.size()-1; i >=0 ; i--) {
					UploadFJ ufj = new UploadFJ(false);
					try {
						ufj.ReadFJ(this.getDesktop(), (GhFile) fjList4.get(i));
						//2011-5-19						
						GhFile f4 = (GhFile)fjList4.get(fjList4.size()-1);
						if(f4.getFbackup()==1){
							checkBackup4.setChecked(true);							
						}else if(f4.getFbackup()==0){
							checkBackup4.setChecked(false);							
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					fileModel4.add(ufj);
				}
				upList4.setModel(fileModel4);
				upList4.setVisible(true);downFile4.setVisible(true);
				deUpload4.setVisible(true);rowFile4.setVisible(true);
				checkBackup4.setVisible(true);
			}
			
		}
 }
	/**
	 * ���ܣ��ύ����
	 * 2011-5
	 * @throws InterruptedException 
	 * @throws InterruptedException
	 */
	public void onClick$submit() throws InterruptedException {		
		//��Ŀ��� Ҫô���Ҫ����д����Ψһ if(number.getValue()==null||"".equals(number.getValue().trim())){ }else{ if(xm==null){//�½���Ŀ //��Ŀ��Ψһ���� if(xmService.findByKynumber(number.getValue()).size()>=1){ throw new WrongValueException(number, "��Ŀ��ű���Ψһ������Ŀ��Ŵ��ڣ����ʵ����д��"); } }else{//�޸���Ŀ if(xmService.findByKynumber(number.getValue()).size()>=2){ throw new WrongValueException(number, "��Ŀ��ű���Ψһ������Ŀ��Ŵ��ڣ����ʵ����д��"); } } }
		if(number.getValue()==null||"".equals(number.getValue().trim())){
			throw new WrongValueException(number, "����û����д��Ŀ��ţ�");
		}
		// ��������
		if (cgmc.getValue() == null || "".equals(cgmc.getValue().trim())) {
			throw new WrongValueException(cgmc, "����û����д�������ƣ�");
		}	
		//������
		if (proman.getValue() == null || "".equals(proman.getValue().trim())) {
			throw new WrongValueException(proman, "����û����д��Ŀ�����ˣ�");
		}
		//����ʱ��
		if(lxsj.getValue()==null||lxsj.getValue().equals("")){
			throw new WrongValueException(lxsj,"����û����д��Ŀ����ʱ�䣡");
		}
		// ��ʼʱ��
		if (kaishi.getValue() == null || "".equals(kaishi.getValue())) {
			throw new WrongValueException(kaishi, "����û����д��ʼʱ�䣡");
		} 
		// ����ʱ��
		if (jieshu.getValue() == null || "".equals(jieshu.getValue())) {
			throw new WrongValueException(jieshu, "����û����д����ʱ�䣡");
		} 
		// ����
		Float kyJf = 0F;
		if (jingfei.getValue() != null &&!"".equals(jingfei.getValue().toString().trim())) {
			kyJf = Float.parseFloat(jingfei.getValue()+"");
		}
		// ���˹���
		Integer kyGx = 1;

//		if(contractNum.getValue() == null || "".equals(contractNum.getValue().trim())||
//			finishUnit.getValue() == null || "".equals(finishUnit.getValue().trim())||
//			setFinishTime.getValue()==null||setFinishTime.getValue().equals("")||
//			realFinishTime.getValue()==null||realFinishTime.getValue().equals("")){
//			Messagebox.show("����û����д��Ŀ����������Ϣ��", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);			  
//			return;
//		}
//		//��ͬ���		
//		if (contractNum.getValue() == null || "".equals(contractNum.getValue().trim())) {
//			throw new WrongValueException(contractNum, "����û����д��Ŀ��ͬ��ţ�");			
//		}
//		//��ɵ�λ		
//		if (finishUnit.getValue() == null || "".equals(finishUnit.getValue().trim())) {
//			throw new WrongValueException(finishUnit, "����û����д��Ŀ��ɵ�λ��");
//		}
//		//�涨���ʱ��
//		if(setFinishTime.getValue()==null||setFinishTime.getValue().equals("")){
//			throw new WrongValueException(setFinishTime,"����û����д��Ŀ�涨���ʱ�䣡");
//		}
//		//�涨���ʱ��
//		if(realFinishTime.getValue()==null||realFinishTime.getValue().equals("")){
//			throw new WrongValueException(realFinishTime,"����û����д��Ŀʵ�����ʱ�䣡");
//		}		
		// false��ʾ���޸ģ�true��ʾ���½�
		boolean index = false,owner=false;
		if (xm == null) {// ˵�������½���һ����Ŀ
			 if(!lxsj.isDisabled()){
				 owner=true;
			    }
			xm = new GhXm();
			index = true;
		}
	
		//2011-5-19 �ļ��ϴ��Ƿ�ѡ��		
		Sessions.getCurrent().setAttribute("check",checkBackup); 
		Sessions.getCurrent().setAttribute("check",checkBackup2); 
		Sessions.getCurrent().setAttribute("check",checkBackup3); 
		Sessions.getCurrent().setAttribute("check",checkBackup4); 
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
				if(lxsj.getValue()!=null){
					xm.setKyLxsj(DateUtil.getDateString(lxsj.getValue()));
				}
				
				if(kaishi.getValue()!=null){
					xm.setKyKssj(DateUtil.getDateString(kaishi.getValue()));
				}
				if(jieshu.getValue()!=null){
					xm.setKyJssj(DateUtil.getDateString(jieshu.getValue()));
				}
				
				//�������Ŀ��ʱ����Ŀ��Ա��GH_PROMEMBER�϶�ΪΪ�գ��Ƚ������˷����Ա��				
				xm.setKyProstaffs(user.getKuName());
				xm.setKyProman(proman.getValue().trim());
//				xm.setKyRegister(register.getValue().trim());
				xm.setKyTarget(target.getValue().trim());
				if(identtime.getValue()!=null){
					xm.setKyIdenttime(DateUtil.getDateString(identtime.getValue()));
				}else{
					xm.setKyIdenttime("");
				}
				xm.setKyLevel(level.getValue().trim());
				xm.setKySubjetype(String.valueOf(subjetype.getSelectedIndex()));
				xm.setKyClass("2");				
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
				//2011-5-18��Ŀ�ڲ����							
				if(Integer.valueOf(xm.getKyClass().trim())==1){
					xm.setInternalNum("H"+number.getValue());
				}else if(Integer.valueOf(xm.getKyClass().trim())==2){
					xm.setInternalNum("Z"+number.getValue());
				}
								
				xm.setKyContraNum(contractNum.getValue());//��ͬ���
				xm.setKyFinishUnit(finishUnit.getValue());//��ɵ�λ				
				if(setFinishTime.getValue()!=null){
					xm.setKySetFinishTime(DateUtil.getDateString(setFinishTime.getValue()));//�涨���ʱ��
				}
				if(realFinishTime.getValue()!=null){
					xm.setKyRealFinishTime(DateUtil.getDateString(realFinishTime.getValue()));//ʵ�����ʱ��
				}				
				xm.setKyGrants(Float.valueOf(grants.getValue().toString()));//��������
				xm.setKyContentFinishConditon(conFinishCondition.getValue());//������
				xm.setKyFruit(fruit.getValue());//�ɹ�����Ч				
				xmService.update(xm);
				
				// �����洢
				UploadFJ.ListModelListUploadCommand(fileModel, xm.getKyId());//������
				UploadFJ.ListModelListUploadCommand(fileModel2, xm.getKyId());//��ͬ��
				UploadFJ.ListModelListUploadCommand(fileModel3, xm.getKyId());//���ڱ���
				UploadFJ.ListModelListUploadCommand(fileModel4, xm.getKyId());//�����
				
				//���浽��ʦ��Ŀ��
				GhJsxm jsxm=new GhJsxm();
				jsxm.setKyId(xm.getKyId());
				jsxm.setJsxmType(GhJsxm.KYXM);
				jsxm.setKuId(user.getKuId());
				jsxm.setKyCdrw(String.valueOf(cdrw.getSelectedIndex()));
				jsxm.setYjId(research.getSelectedIndex()!=0?((GhYjfx)research.getSelectedItem().getValue()).getGyId():0L);
				jsxm.setKyGx(1);
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
		} else {//�޸���Ŀ��ԭ����Ŀ�Ǹ��û������ĸ�����Ŀ��ͽ�ʦ��Ŀ��ԭ����Ŀ�Ǹ��û�������ֻ���½�ʦ��Ŀ��
			// ���˹���
			
			List manager= projectmemberService.findByKyIdAndKuId(xm.getKyId(),user.getKuId());
			if(manager.size()!=0)
			{
				GhJsxm man=(GhJsxm) manager.get(0);
				kyGx = Integer.parseInt(man.getKyGx().toString());
			}
			
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
				if(lxsj.getValue()!=null){
					xm.setKyLxsj(DateUtil.getDateString(lxsj.getValue()));
				}
				if(kaishi.getValue()!=null){
					xm.setKyKssj(DateUtil.getDateString(kaishi.getValue()));
				}
				if(jieshu.getValue()!=null){
					xm.setKyJssj(DateUtil.getDateString(jieshu.getValue()));
				}
				//���༭��ʱ�򣬲�ѯ����Ŀ��Ա��GH_PROMEMBER
				List mlist=projectmemberService.findByKyXmId(xm.getKyId());
				proStaffsRow.setVisible(true);
				if(mlist.size()!=0){
					staff="";
					String proMem="";
					for(int i=0;i<mlist.size();i++){
						GhJsxm member=(GhJsxm)mlist.get(i);						
						staff+=member.getKyMemberName()+"��";
					}
					if(staff.length()>=25) {
						proMem = staff.substring(0, 24)+"...";							
						xm.setKyProstaffs(proMem);
					}
					else {
						xm.setKyProstaffs(staff.substring(0, staff.length()-1));
					}		
				}
				else{
					xm.setKyProstaffs(user.getKuName());
				}
				xm.setKyProman(proman.getValue().trim());
//					xm.setKyRegister(register.getValue().trim());
				xm.setKyTarget(target.getValue().trim());
				if(identtime.getValue()!=null){
					xm.setKyIdenttime(DateUtil.getDateString(identtime.getValue()));
				}else{
					xm.setKyIdenttime("");
				}
				xm.setKyLevel(level.getValue().trim());
				xm.setKySubjetype(String.valueOf(subjetype.getSelectedIndex()));
				xm.setKyClass("2");					
				//2011-5-18				
				if(xm.getKyClass().equals("1")){
					xm.setInternalNum("H"+number.getValue());
				}else if(xm.getKyClass().equals("2")){
					xm.setInternalNum("Z"+number.getValue());
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
				
				xm.setKyContraNum(contractNum.getValue());//��ͬ���
				xm.setKyFinishUnit(finishUnit.getValue());//��ɵ�λ
				if(setFinishTime.getValue()!=null){
					xm.setKySetFinishTime(DateUtil.getDateString(setFinishTime.getValue()));//�涨���ʱ��
				}
				if(realFinishTime.getValue()!=null){
					xm.setKyRealFinishTime(DateUtil.getDateString(realFinishTime.getValue()));//ʵ�����ʱ��
				}
				xm.setKyGrants(Float.valueOf(grants.getValue().toString()));//��������
				xm.setKyContentFinishConditon(conFinishCondition.getValue());//������
				xm.setKyFruit(fruit.getValue());//�ɹ�����Ч
				
				xmService.update(xm);
				
				List jslwlist=jsxmService.findByXmidAndtype(xm.getKyId(), GhJsxm.KYXM);
				for(int i=0;i<jslwlist.size();i++){
					GhJsxm js=(GhJsxm)jslwlist.get(i);
					if(!prostaffs.getValue().trim().contains(js.getUser().getKuName().trim())){
						jsxmService.delete(js);
					}
				}
				
				// �����洢
				UploadFJ.ListModelListUploadCommand(fileModel, xm.getKyId());//������
				UploadFJ.ListModelListUploadCommand(fileModel2, xm.getKyId());//��ͬ��	
				UploadFJ.ListModelListUploadCommand(fileModel3, xm.getKyId());//���ڱ���
				UploadFJ.ListModelListUploadCommand(fileModel4, xm.getKyId());//�����
														
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
					jsxmService.save(jsxm);
					Messagebox.show("����ɹ���","��ʾ",Messagebox.OK,Messagebox.INFORMATION);
					Events.postEvent(Events.ON_CHANGE, this, null);
				}
			}
		}
	}
		
	/**
	 * ���ܣ���ʼ������
	 * @Data  2011-5-18
	 * @param null
	 */
    public void initXmWindow(String xmmc,String xmly,String porman,Short lx){
    	GhXm cxm=xmService.findByNameAndLyAndLxAndFzr(xmmc, lx, xmly, porman);
    	if(cxm!=null){
    		xm=cxm;
    		cgmc.setDisabled(true);
    		number.setValue(cxm.getKyNumber());number.setDisabled(true);
			ly.setValue(cxm.getKyLy());  ly.setDisabled(true);
			lxsj.setValue(DateUtil.getDate(cxm.getKyLxsj()));lxsj.setDisabled(true);
			kaishi.setValue(DateUtil.getDate(cxm.getKyKssj()));kaishi.setDisabled(true);
			jieshu.setValue(DateUtil.getDate(cxm.getKyJssj()));jieshu.setDisabled(true);
			jingfei.setValue(BigDecimal.valueOf(cxm.getKyJf()));jingfei.setDisabled(true);
			
			//���༭��ʱ�򣬲�ѯ����Ŀ��Ա��GH_PROMEMBER
			List mlist=projectmemberService.findByKyXmId(cxm.getKyId());
			proStaffsRow.setVisible(true);
			if(mlist.size()!=0){
				staff="";
				String proMem="";
				for(int i=0;i<mlist.size();i++){
					GhJsxm member=(GhJsxm)mlist.get(i);						
					staff+=member.getKyMemberName()+"��";
				}
				if(staff.length()>=25) {
					proMem = staff.substring(0, 24)+"...";							
					prostaffs.setValue(proMem);
				}
				else {
					prostaffs.setValue(staff.substring(0, staff.length()-1));
				}		
			}
			else{
				prostaffs.setValue(cxm.getKyProman());
			}
//			prostaffs.setValue(cxm.getKyProstaffs());
			prostaffs.setDisabled(true);
			proman.setValue(cxm.getKyProman());proman.setDisabled(true);
//			register.setValue(xm.getKyRegister());register.setDisabled(true);
			target.setValue(cxm.getKyTarget());target.setDisabled(true);
			identtime.setValue(DateUtil.getDate(cxm.getKyIdenttime()));identtime.setDisabled(true);
			level.setValue(cxm.getKyLevel());level.setDisabled(true);
			
			//������Ϣ
			contractNum.setValue(cxm.getKyContraNum());contractNum.setDisabled(true);
			finishUnit.setValue(cxm.getKyFinishUnit());finishUnit.setDisabled(true);
			conFinishCondition.setValue(cxm.getKyContentFinishConditon());conFinishCondition.setDisabled(true);
			fruit.setValue(cxm.getKyFruit());fruit.setDisabled(true);
			setFinishTime.setValue(DateUtil.getDate(cxm.getKySetFinishTime()));setFinishTime.setDisabled(true);
			realFinishTime.setValue(DateUtil.getDate(cxm.getKyRealFinishTime()));realFinishTime.setDisabled(true);
			grants.setValue(BigDecimal.valueOf(cxm.getKyGrants()));grants.setDisabled(true);
			
			// ѧ�Ʒ���
			if (cxm.getKySubjetype() == null || cxm.getKySubjetype() == "") {
				subjetype.setSelectedIndex(0);
				subjetype.setDisabled(true);
			} else {
				subjetype.setSelectedIndex(Integer.valueOf(cxm.getKySubjetype().trim()));
				subjetype.setDisabled(true);
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
		    writeuser1.setValue(cxm.getUser().getKuName());
		    
		       //������Ϣ
            //��ͬ���
            if (xm.getKyContraNum()!= null&&!xm.getKyContraNum().equals("")) {
            	contractNum.setValue(xm.getKyContraNum());
			} else {
				contractNum.setValue("");
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
            // ��������
            if (xm.getKyGrants()!= null) {
            	grants.setValue(BigDecimal.valueOf(xm.getKyGrants()));
			}
            //��ѯGH_PROMEMBER����ȡ����           
			List manager= projectmemberService.findByKyIdAndKuId(xm.getKyId(),user.getKuId());
			if(manager.size()!=0)
			{
				GhJsxm man=(GhJsxm) manager.get(0);
				gongxian.setValue(man.getKyGx().toString());
			}
			else
			{
				gongxian.setValue("1");
			}
    	}
    	else{
    		xm=null;
    	}
    }
    
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
					if(!Xm.getKyProstaffs().contains(user.getKuName().trim())){
						Messagebox.show("��������Ŀ���Ա������ϵ��Ŀ��д��������룡", "��ʾ��", Messagebox.OK, Messagebox.EXCLAMATION);
				}
				else{
					xm=Xm;
					cgmc.setValue(Xm.getKyMc());cgmc.setDisabled(true);
					number.setValue(Xm.getKyNumber());number.setDisabled(true);
					//ly.setValue(Xm.getKyLy());
					ly.setDisabled(true);
					lxsj.setValue(DateUtil.getDate(Xm.getKyLxsj()));lxsj.setDisabled(true);
					kaishi.setValue(DateUtil.getDate(Xm.getKyKssj()));kaishi.setDisabled(true);
					jieshu.setValue(DateUtil.getDate(Xm.getKyJssj()));jieshu.setDisabled(true);
					jingfei.setValue(BigDecimal.valueOf(Xm.getKyJf()));jingfei.setDisabled(true);
					prostaffs.setValue(Xm.getKyProstaffs());prostaffs.setDisabled(true);
					proman.setValue(Xm.getKyProman());proman.setDisabled(true);
	//				register.setValue(xm.getKyRegister());register.setDisabled(true);
					target.setValue(Xm.getKyTarget());target.setDisabled(true);
					identtime.setValue(DateUtil.getDate(Xm.getKyIdenttime()));identtime.setDisabled(true);
					level.setValue(Xm.getKyLevel());level.setDisabled(true);
					// ѧ�Ʒ���
					if (Xm.getKySubjetype() == null || Xm.getKySubjetype() == "") {
						subjetype.setSelectedIndex(0);
						subjetype.setDisabled(true);
					} else {
						subjetype.setSelectedIndex(Integer.valueOf(Xm.getKySubjetype().trim()));
						subjetype.setDisabled(true);
					}
					// ��Ŀ���
	//				if (Xm.getKyClass() == null || Xm.getKyClass() == "") {
	//					kyclass.setSelectedIndex(0);
	//					kyclass.setDisabled(true);
	//				} else {
	//					
	//					kyclass.setSelectedIndex(Integer.valueOf(Xm.getKyClass().trim()));
	//					kyclass.setDisabled(true);
	//				}
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
					if(Xm.getKyProstaffs().contains(user.getKuName())){
						List namelist=new ArrayList();
						String str=Xm.getKyProstaffs().trim();
						while (str.contains("��")) {
								Label lb = new Label(str.substring(0,str.indexOf("��")));
								namelist.add(lb.getValue().trim());
								str = str.substring(str.indexOf("��") + 1, str.length());
						}
						namelist.add(str.trim());
						gongxian.setValue(namelist.indexOf(user.getKuName())+1+"");
					}else{
						gongxian.setValue("0");
					}
					 writeuser.setValue(Xm.getUser().getKuName());
					 writeuser1.setValue(Xm.getUser().getKuName());
					 
					   //������Ϣ
			            //��ͬ���
			            if (xm.getKyContraNum()!= null&&!xm.getKyContraNum().equals("")) {
			            	contractNum.setValue(xm.getKyContraNum());
						} else {
							contractNum.setValue("");
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
			            // ��������
			            if (xm.getKyGrants()!= null) {
			            	grants.setValue(BigDecimal.valueOf(xm.getKyGrants()));
						}
					sxw.detach();				
				}
				}
			}
    	});
    }
    /**
     * �ϴ�������
     * @date 2011-5-18
     * @throws InterruptedException
     */
	public void onClick$upFile() throws InterruptedException {
		UploadFJ ufj = new UploadFJ(true);
		Media media = ufj.Upload(this.getDesktop(), 19, 10, "�������ܳ���10MB","����------�ļ���С���ܳ���10MB");
		if (media == null) {
			return;
		}
		fileModel.add(ufj);
		rowFile.setVisible(true);upList.setVisible(true);
		deUpload.setVisible(true);checkBackup.setVisible(true);
	}
	/**
     * �ϴ���ͬ��
     * @date 2011-5-18
     * @throws InterruptedException
     */
	public void onClick$upFile2() throws InterruptedException {
		UploadFJ ufj = new UploadFJ(true);
		Media media = ufj.Upload(this.getDesktop(), 20, 10, "�������ܳ���10MB","����------�ļ���С���ܳ���10MB");
		if (media == null) {
			return;
		}
		rowFile2.setVisible(true);upList2.setVisible(true);
		deUpload2.setVisible(true);fileModel2.add(ufj);
		checkBackup2.setVisible(true);
	}
	/**
     * �ϴ����ڱ���
     * @date 2011-5-18
     * @throws InterruptedException
     */
	public void onClick$upFile3() throws InterruptedException {
		UploadFJ ufj = new UploadFJ(true);
		Media media = ufj.Upload(this.getDesktop(), 21, 10, "�������ܳ���10MB","����------�ļ���С���ܳ���10MB");
		if (media == null) {
			return;
		}
		rowFile3.setVisible(true);upList3.setVisible(true);
		deUpload3.setVisible(true);fileModel3.add(ufj);
		checkBackup3.setVisible(true);
	}
	/**
     * �ϴ������
     * @date 2011-5-18
     * @throws InterruptedException
     */
	public void onClick$upFile4() throws InterruptedException {
		UploadFJ ufj = new UploadFJ(true);
		Media media = ufj.Upload(this.getDesktop(), 22, 10, "�������ܳ���10MB","����------�ļ���С���ܳ���10MB");
		if (media == null) {
			return;
		}
		rowFile4.setVisible(true);upList4.setVisible(true);
		deUpload4.setVisible(true);fileModel4.add(ufj);
		checkBackup4.setVisible(true);
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
	
	/**
	 * <li>ɾ���ϴ����ļ�������ѡ��
	 * @author bobo 2010-4-11
	 */
	public void onClick$deUpload2() {
		// ȥ���ɵĴ������
		IsZipExists.delZipFile(this.getDesktop().getWebApp().getRealPath("/upload/xkjs/").trim()+ "\\" + "_" + xm.getKyId() + "_" + ".zip");
		Listitem it = upList2.getSelectedItem();
		if (it == null) {
			if (fileModel2.getSize() > 0) {
				it = upList2.getItemAtIndex(0);
			}
		}
		UploadFJ temp = (UploadFJ) it.getValue();
		if (fileModel2.getSize() == 1) {
			temp.DeleteFJ();
			fileModel2.remove(it.getValue());
			rowFile2.setVisible(true);
		} else if (fileModel2.getSize() > 1) {
			temp.DeleteFJ();
			fileModel2.remove(it.getValue());
			rowFile2.setVisible(true);upList2.setVisible(true);
			deUpload2.setVisible(true);
			checkBackup2.setVisible(true);
		}
	}

	/**
	 * <li>ɾ���ϴ����ļ�������ѡ��
	 * @author bobo 2010-4-11
	 */
	public void onClick$deUpload3() {
		// ȥ���ɵĴ������
		IsZipExists.delZipFile(this.getDesktop().getWebApp().getRealPath("/upload/xkjs/").trim()+ "\\" + "_" + xm.getKyId() + "_" + ".zip");
		Listitem it = upList3.getSelectedItem();
		if (it == null) {
			if (fileModel3.getSize() > 0) {
				it = upList3.getItemAtIndex(0);
			}
		}
		UploadFJ temp = (UploadFJ) it.getValue();
		if (fileModel3.getSize() == 1) {
			temp.DeleteFJ();
			fileModel3.remove(it.getValue());
			rowFile3.setVisible(true);
		} else if (fileModel3.getSize() > 1) {
			temp.DeleteFJ();
			fileModel3.remove(it.getValue());
			rowFile3.setVisible(true);upList3.setVisible(true);
			deUpload3.setVisible(true);
			checkBackup3.setVisible(true);
		}
	}
	
	/**
	 * ɾ���ϴ��������
	 * @date 2011-5-18
	 * 
	 */
	public void onClick$deUpload4() {
		// ȥ���ɵĴ������
		IsZipExists.delZipFile(this.getDesktop().getWebApp().getRealPath("/upload/xkjs/").trim()+ "\\" + "_" + xm.getKyId() + "_" + ".zip");
		Listitem it = upList4.getSelectedItem();
		if (it == null) {
			if (fileModel4.getSize() > 0) {
				it = upList4.getItemAtIndex(0);
			}
		}
		UploadFJ temp = (UploadFJ) it.getValue();
		if (fileModel4.getSize() == 1) {
			temp.DeleteFJ();
			fileModel4.remove(it.getValue());
			rowFile4.setVisible(true);
		} else if (fileModel4.getSize() > 1) {
			temp.DeleteFJ();
			fileModel4.remove(it.getValue());
			rowFile4.setVisible(true);upList4.setVisible(true);
			deUpload4.setVisible(true);
			checkBackup4.setVisible(true);
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
	
	public void onClick$reset2() {
		// ȥ���ɵĴ������
		IsZipExists.delZipFile(this.getDesktop().getWebApp().getRealPath("/upload/xkjs/").trim()+ "\\" + "_" + xm.getKyId() + "_" + ".zip");
		List list = fileModel2.getInnerList();
		for (int i = 0; i < list.size(); i++) {
			((UploadFJ) list.get(i)).DeleteFJ();
		}
		initWindow();
		fileModel2.clear();
		rowFile2.setVisible(true);
	}
	
	public void onClick$reset3() {
		// ȥ���ɵĴ������
		IsZipExists.delZipFile(this.getDesktop().getWebApp().getRealPath("/upload/xkjs/").trim()+ "\\" + "_" + xm.getKyId() + "_" + ".zip");
		List list = fileModel3.getInnerList();
		for (int i = 0; i < list.size(); i++) {
			((UploadFJ) list.get(i)).DeleteFJ();
		}
		initWindow();
		fileModel3.clear();
		rowFile3.setVisible(true);
	}
	
	public void onClick$reset4(){
		IsZipExists.delZipFile(this.getDesktop().getWebApp().getRealPath("/upload/xkjs/").trim()+ "\\" + "_" + xm.getKyId() + "_" + ".zip");
		List list = fileModel4.getInnerList();
		for(int i=0;i<list.size();i++){
			((UploadFJ)list.get(i)).DeleteFJ();
		}
		initWindow();
		fileModel4.clear();
		rowFile4.setVisible(true);
	}

	//������ظ���
	public void onClick$downFileZip(){
		DownloadFJ.ListModelListDownloadCommand(this.getDesktop(), this.xm.getKyId(), fileModel);
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
	
	//������ظ���
	public void onClick$downFileZip2(){
		DownloadFJ.ListModelListDownloadCommand(this.getDesktop(), this.xm.getKyId(), fileModel2);
	}	
	//�����ļ�����
	public void onClick$downFile2(){
		Listitem it = upList2.getSelectedItem();
		if (it == null) {
			if (fileModel2.getSize() > 0) {
				it = upList2.getItemAtIndex(0);
			}
		}
		UploadFJ temp = (UploadFJ) it.getValue();
		DownloadFJ.DownloadCommand(temp);
	}
	
	//������ظ���
	public void onClick$downFileZip3(){
		DownloadFJ.ListModelListDownloadCommand(this.getDesktop(), this.xm.getKyId(), fileModel3);
	}	
	//�����ļ�����
	public void onClick$downFile3(){
		Listitem it = upList3.getSelectedItem();
		if (it == null) {
			if (fileModel3.getSize() > 0) {
				it = upList3.getItemAtIndex(0);
			}
		}
		UploadFJ temp = (UploadFJ) it.getValue();
		DownloadFJ.DownloadCommand(temp);
	}
	
	//������ظ���
	public void onClick$downFileZip4(){
		DownloadFJ.ListModelListDownloadCommand(this.getDesktop(), this.xm.getKyId(), fileModel4);
	}	
	//�����ļ�����
	public void onClick$downFile4(){
		Listitem it = upList4.getSelectedItem();
		if (it == null) {
			if (fileModel4.getSize() > 0) {
				it = upList4.getItemAtIndex(0);
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
		arg.put("zProgram", this.xm);
		ZMiddReportWindow zmWin = (ZMiddReportWindow) Executions.createComponents
								("/admin/personal/data/teacherinfo/kyqk/kyxm/ZmiddReport.zul", null, arg);
		zmWin.doHighlighted();
	}

	/**
	 * ���ɽ����
	 * @throws IOException 
	 * @throws WriteException 
	 * @throws InterruptedException 
	 */
	public void onClick$resultReport() throws WriteException, IOException, InterruptedException{
		Map arg=new HashMap();
		arg.put("zProgram", this.xm);
		ZResultReportWindow zrWin = (ZResultReportWindow) Executions.createComponents
								("/admin/personal/data/teacherinfo/kyqk/kyxm/ZresultReport.zul", null, arg);
		zrWin.doHighlighted();
	}
	
	/**
	 * ����Ŀ��Դ������
	 */
	public void onClick$sourceSelect()
	{
		Map map=new HashMap();
		map.put("sourceTextbox", this.ly);
		ProjectSourceWindow psWin = (ProjectSourceWindow)Executions.createComponents
								("/admin/personal/data/teacherinfo/kyqk/kyxm/projectSource.zul", null, map);
		psWin.initWindow();
		try {
			psWin.doModal();
		} catch (SuspendNotAllowedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public void initShow() {
	}
	
	public void onClick$close() {
		this.detach();
	}
//	public GhXm getXm() {
//		return xm;
//	}
//	public void setXm(GhXm xm) {
//		this.xm = xm;
//	}
//	public Long getKuid() {
//		return kuid;
//	}
//	public void setKuid(Long kuid) {
//		this.kuid = kuid;
//	}
}
