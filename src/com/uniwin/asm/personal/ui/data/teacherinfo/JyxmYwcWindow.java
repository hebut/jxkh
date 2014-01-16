package com.uniwin.asm.personal.ui.data.teacherinfo;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iti.gh.entity.GhCg;
import org.iti.gh.entity.GhFile;
import org.iti.gh.entity.GhJsxm;
import org.iti.gh.entity.GhXm;
import org.iti.gh.entity.GhYjfx;
import org.iti.gh.service.CgService;
import org.iti.gh.service.GhFileService;
import org.iti.gh.service.JsxmService;
import org.iti.gh.service.XmService;
import org.iti.gh.service.YjfxService;
import org.iti.gh.ui.listbox.YjfxListbox;
import org.iti.projectmanage.science.member.service.ProjectMemberService;
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
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

import com.uniwin.asm.personal.ui.data.teacherinfo.kyqk.SelectXmWindow;
import com.uniwin.framework.entity.WkTUser;

import com.uniwin.framework.common.fileuploadex.DownloadFJ;
import com.uniwin.framework.common.fileuploadex.IsZipExists;
import com.uniwin.framework.common.fileuploadex.UploadFJ;

public class JyxmYwcWindow extends FrameworkWindow{

	Textbox ly,lxsj,kaishi,jieshu;
	Decimalbox jingfei;
	Label gongxian;
	Textbox cgmc;
	Textbox number,prostaffs,proman,target,identtime,level;
	Listbox subjetype,kyclass,scale,progress,cdrw;
	YjfxListbox research;
	Label writeuser;
//	private Radiogroup prize;
	GhXm xm;
	Long kuid;
	//�ݴ渽��
	private Row rowFile;
	Listbox upList;
	ListModelList fileModel;
	Row members;
	XmService xmService;
	YjfxService yjfxService;
	CgService cgService;
	WkTUser user;
	GhFileService ghfileService;
	JsxmService jsxmService;
	String staff="";
	ProjectMemberService projectmemberService;
	Button downFile,deUpload,upFile,downFileZip,submit;
	@Override
	public void initShow() {
		
	}

	@Override
	public void initWindow() {
		
		List subtype = new ArrayList();
		List subclass = new ArrayList();
		List subscale = new ArrayList();
		List subprogress = new ArrayList();
		List subcdrw = new ArrayList();
		String[] Subtype = {"-��ѡ��-","��Ȼ��ѧ","����ѧ", "����"};
		for(int i = 0;i < Subtype.length; i++){
			subtype.add(Subtype[i]);
		}
		String[] Subclass = {"-��ѡ��-","����","����"};
		for(int i = 0;i < Subclass.length; i++){
			subclass.add(Subclass[i]);
		}
		String[] Subscale = {"-��ѡ��-","���ʺ�����Ŀ","���Ҽ���Ŀ","����ί��ʡ������Ŀ","��������Ŀ","ί�м�������Ŀ","ѧУ������Ŀ", "����"};
		for(int i = 0;i < Subscale.length; i++){
			subscale.add(Subscale[i]);
		}
		String[] Subprogress = {"-��ѡ��-","������","����","�����"};
		for(int i = 0;i < Subprogress.length; i++){
			subprogress.add(Subprogress[i]);
		}
		String[] Subcdrw = {"-��ѡ��-","����","�μ�","����"};
		for(int i = 0;i < Subcdrw.length; i++){
			subcdrw.add(Subcdrw[i]);
		}
//		//�о������б�
//		List yjfxlist= yjfxService.findByKuid(kuid);
//		
//		List fx = new ArrayList();
//		if(yjfxlist != null && yjfxlist.size() !=0 ){
//			GhYjfx	yjfx = (GhYjfx) yjfxlist.get(0);
//			String[] Yjfx = {"-��ѡ��-",yjfx.getYjResearch1(),yjfx.getYjResearch2(),yjfx.getYjResearch3(),yjfx.getYjResearch4(),yjfx.getYjResearch5()};
//			for(int i = 0;i < Yjfx.length; i++){
//				fx.add(Yjfx[i]);
//			}
//		
//		}else{
//			fx.add("--��ѡ��--");
//		}
//		research.setModel(new ListModelList(fx));
		//ѧ�Ʒ���
		subjetype.setModel(new ListModelList(subtype));
		//��Ŀ���
		kyclass.setModel(new ListModelList(subclass));
		//��Ŀ����
		scale.setModel(new ListModelList(subscale));
		//��Ŀ��չ
		progress.setModel(new ListModelList(subprogress));
		//���˳е����������
		cdrw.setModel(new ListModelList(subcdrw));
//		prostaffs.addEventListener(Events.ON_CHANGE, new EventListener(){
//
//			public void onEvent(Event arg0) throws Exception {
//				List namelist=new ArrayList();
//				String str=prostaffs.getValue().trim();
//				if(str.contains("��")||str.contains(",")){
//					throw new WrongValueException(prostaffs, "��Ŀ����Ա��д����,��ѡ��ٺţ�");
//				}else{
//				while (str.contains("��")) {
//						Label lb = new Label(str.substring(0,str.indexOf("��")));
//						namelist.add(lb.getValue().trim());
//						str = str.substring(str.indexOf("��") + 1, str.length());
//					
//				}
//				namelist.add(str.trim());
//				gongxian.setValue(namelist.indexOf(user.getKuName())+1+"");
//			}
//			}
//			
//		});
//		cgmc.addEventListener(Events.ON_CHANGE, new EventListener(){
//
//			public void onEvent(Event arg0) throws Exception {
//				initXmWindow(cgmc.getValue().trim(),ly.getValue().trim(),proman.getValue().trim(),GhXm.JYXM);
//			}
//		});
//		ly.addEventListener(Events.ON_CHANGE, new EventListener(){
//
//			public void onEvent(Event arg0) throws Exception {
//				initXmWindow(cgmc.getValue().trim(),ly.getValue().trim(),proman.getValue().trim(),GhXm.JYXM);
//			}
//		});
//		proman.addEventListener(Events.ON_CHANGE, new EventListener(){
//
//			public void onEvent(Event arg0) throws Exception {
//				initXmWindow(cgmc.getValue().trim(),ly.getValue().trim(),proman.getValue().trim(),GhXm.JYXM);
//			}
//		});
		if(xm!=null){
			//��Ŀ���
			number.setValue(xm.getKyNumber());
			
			//����
			cgmc.setValue(xm.getKyMc());cgmc.setDisabled(true);

			//��Դ--����Ϊ��
			if(xm.getKyLy()!=null){
				ly.setValue(xm.getKyLy());
			}
			if(xm.getKyLxsj()!=null){
			lxsj.setValue(xm.getKyLxsj());
			}
			//��ʼʱ��
			if(xm.getKyKssj()!=null){
				kaishi.setValue(xm.getKyKssj());
			}
			//����ʱ��
			if(xm.getKyJssj()!=null){
				jieshu.setValue(xm.getKyJssj());
			}
			
			//����--����Ϊ��
			if(xm.getKyJf()!=null){
				jingfei.setValue(BigDecimal.valueOf(xm.getKyJf()));
			}
//			//���˹���
//			if(xm.getKyGx()!=null){
//				gongxian.setValue(xm.getKyGx()+"");
//			}
			//ѧ�Ʒ���
			if(xm.getKySubjetype() == null || xm.getKySubjetype() == ""){
				subjetype.setSelectedIndex(0);
			}else {
				subjetype.setSelectedIndex(Integer.valueOf(xm.getKySubjetype().trim()));
			}
			//��Ŀ���
			if(xm.getKyClass() == null || xm.getKyClass() == ""){
				kyclass.setSelectedIndex(0);
			}else {
				kyclass.setSelectedIndex(Integer.valueOf(xm.getKyClass().trim()));
			}
			//��Ŀ����
			if(xm.getKyScale() == null || xm.getKyScale() == ""){
				scale.setSelectedIndex(0);
			}else {
				scale.setSelectedIndex(Integer.valueOf(xm.getKyScale().trim()));
			}
			//��Ŀ��չ
			if(xm.getKyProgress() == null || xm.getKyProgress() == ""){
				progress.setSelectedIndex(0);
			}else {
				progress.setSelectedIndex(Integer.valueOf(xm.getKyProgress().trim()));
			}
			//��Ŀ����Ա
			List mlist=projectmemberService.findByKyXmId(xm.getKyId());
			if(mlist.size()!=0)
			{
				staff="";
				for(int i=0;i<mlist.size();i++)
				{
					GhJsxm member=(GhJsxm)mlist.get(i);
				
				staff+=member.getKyMemberName()+"��";
				}
			}
			prostaffs.setValue(staff.substring(0, staff.length()-1));
			
			//��Ŀ������
			if(xm.getKyProman() != null){
				proman.setValue(xm.getKyProman());
			}else{
				proman.setValue("");
			}
			
//			//�걨��
//			if(xm.getKyRegister() != null){
//				register.setValue(xm.getKyRegister());	
//			}else{
//				register.setValue("");	
//			}
			
			//��Ŀָ��
			if(xm.getKyTarget() != null){
				target.setValue(xm.getKyTarget());
			}else{
				target.setValue("");
			}
			
			//����ʱ��
			if(xm.getKyIdenttime() != null){
				identtime.setValue(xm.getKyIdenttime());
			}else{
				identtime.setValue("");	
			}
			
			//����ˮƽ
			if(xm.getKyLevel() != null){
				level.setValue(xm.getKyLevel());
			}else{
				level.setValue("");
			}
			
			//��ʦ��Ŀ
            GhJsxm jx=(GhJsxm)jsxmService.findByXmidAndKuidAndType(xm.getKyId(), user.getKuId(),GhJsxm.JYXM);
            if(jx!=null){
            	// ���˹���
            	gongxian.setValue(jx.getKyGx()!=null?jx.getKyGx()+"":"0");
//            	// �о�����
//    			if (jx.getYjId() == null || jx.getYjId() == "") {
//    				research.setSelectedIndex(0);
//    			} else {
//    				research.setSelectedIndex(Integer.valueOf(jx.getYjId().trim()));
//    			}
    			research.initYjfzList(user.getKuId(),jx.getYjId());
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
            writeuser.setValue(xm.getUser().getKuName());
            if(xm.getKuId().intValue()!=user.getKuId().intValue()){
				//�Ǹ��û���������Ŀ�������Բ����Ա༭
				number.setDisabled(true);ly.setDisabled(true);lxsj.setDisabled(true);
				kaishi.setDisabled(true);jieshu.setDisabled(true);
				jingfei.setDisabled(true);prostaffs.setDisabled(true);
				proman.setDisabled(true);
//				register.setDisabled(true);
				target.setDisabled(true);identtime.setDisabled(true);
				level.setDisabled(true);subjetype.setDisabled(true);
				kyclass.setDisabled(true);scale.setDisabled(true);
				progress.setDisabled(true);
				//�Ǳ��˴�������Ŀ�����Թ�����Ŀ�ĸ���
				deUpload.setDisabled(true);
				upFile.setDisabled(true);
			}
//            if(xm.getAuditState()!=null&&xm.getAuditState().shortValue()==GhXm.AUDIT_YES){
//            	 submit.setDisabled(true);
//    	    	 upFile.setDisabled(true);
//            }
		}else{
			members.setVisible(false);
			cgmc.addEventListener(Events.ON_CHANGE, new EventListener(){

				public void onEvent(Event arg0) throws Exception {
					initXmWindow(cgmc.getValue().trim(),ly.getValue().trim(),proman.getValue().trim(),GhXm.JYXM);
				}
			});
			ly.addEventListener(Events.ON_CHANGE, new EventListener(){

				public void onEvent(Event arg0) throws Exception {
					initXmWindow(cgmc.getValue().trim(),ly.getValue().trim(),proman.getValue().trim(),GhXm.JYXM);
				}
			});
			proman.addEventListener(Events.ON_CHANGE, new EventListener(){

				public void onEvent(Event arg0) throws Exception {
					initXmWindow(cgmc.getValue().trim(),ly.getValue().trim(),proman.getValue().trim(),GhXm.JYXM);
				}
			});
			cgmc.setValue("");
			ly.setValue("");
			kaishi.setValue("");
			jieshu.setValue("");
			jingfei.setValue(BigDecimal.valueOf(0l));
			gongxian.setValue("0");
			prostaffs.setValue("");
			proman.setValue("");
//			register.setValue("");
			target.setValue("");
			identtime.setValue("");
			level.setValue("");
			writeuser.setValue(user.getKuName());
			deUpload.setDisabled(true);
			downFileZip.setDisabled(true);
			downFile.setDisabled(true);
			research.initYjfzList(user.getKuId(),null);
			//ѡ����Ŀ����
//			if(cgmc.getModel().getSize()>0){
//			cgmc.addEventListener(Events.ON_SELECT, new EventListener(){
//
//				public void onEvent(Event arg0) throws Exception {
//					if(cgmc.getSelectedItem()!=null){
//					Object [] mc=(Object [])cgmc.getSelectedItem().getValue();
//					GhXm xm=(GhXm)xmService.get(GhXm.class,(Long)mc[1]);
//					number.setValue(xm.getKyNumber());number.setDisabled(true);
//					ly.setValue(xm.getKyLy());ly.setDisabled(true);
//					lxsj.setValue(xm.getKyLxsj());lxsj.setDisabled(true);
//					kaishi.setValue(xm.getKyKssj());kaishi.setDisabled(true);
//					jieshu.setValue(xm.getKyJssj());jieshu.setDisabled(true);
//					jingfei.setValue(xm.getKyJf()+"");jingfei.setDisabled(true);
//					prostaffs.setValue(xm.getKyProstaffs());prostaffs.setDisabled(true);
//					proman.setValue(xm.getKyProman());proman.setDisabled(true);
//					register.setValue(xm.getKyRegister());register.setDisabled(true);
//					target.setValue(xm.getKyTarget());target.setDisabled(true);
//					identtime.setValue(xm.getKyIdenttime());identtime.setDisabled(true);
//					level.setValue(xm.getKyLevel());level.setDisabled(true);
//					// ѧ�Ʒ���
//					if (xm.getKySubjetype() == null || xm.getKySubjetype() == "") {
//						subjetype.setSelectedIndex(0);
//						subjetype.setDisabled(true);
//					} else {
//						subjetype.setSelectedIndex(Integer.valueOf(xm.getKySubjetype().trim()));
//						subjetype.setDisabled(true);
//					}
//					// ��Ŀ���
//					if (xm.getKyClass() == null || xm.getKyClass() == "") {
//						kyclass.setSelectedIndex(0);
//						kyclass.setDisabled(true);
//					} else {
//						
//						kyclass.setSelectedIndex(Integer.valueOf(xm.getKyClass().trim()));
//						kyclass.setDisabled(true);
//					}
//					// ��Ŀ����
//					if (xm.getKyScale() == null || xm.getKyScale() == "") {
//						scale.setSelectedIndex(0);
//						scale.setDisabled(true);
//					} else {
//						scale.setSelectedIndex(Integer.valueOf(xm.getKyScale().trim()));
//						scale.setDisabled(true);
//					}
//					// ��Ŀ��չ
//					if (xm.getKyProgress() == null || xm.getKyProgress() == "") {
//						progress.setSelectedIndex(0);
//						progress.setDisabled(true);
//					} else {
//						progress.setSelectedIndex(Integer.valueOf(xm.getKyProgress().trim()));
//						progress.setDisabled(true);
//					}
//					if(xm.getKyProstaffs().contains(user.getKuName())&&xm.getKyProstaffs().contains(",")){
//						List namelist=new ArrayList();
//						String str=xm.getKyProstaffs().trim();
//						while (str.contains(",")) {
//								Label lb = new Label(str.substring(0,str.indexOf(",")));
//								namelist.add(lb.getValue());
//								str = str.substring(str.indexOf(",") + 1, str.length());
//							
//						}
//						namelist.add(str);
//						gongxian.setValue(namelist.indexOf(user.getKuName())+1+"");
//						gongxian.setDisabled(true);
//					}else{
//						gongxian.setValue("0");
//						gongxian.setDisabled(true);
//					}
//					// �������
//					List fjList = ghfileService.findByFxmIdandFType(xm.getKyId(), 10);
//					if (fjList.size() == 0) {// �޸���
//						rowFile.setVisible(false);
//						downFileZip.setDisabled(true);
//					} else {// �и���
//						// ��ʼ������
//						downFileZip.setDisabled(false);
//						for (int i = 0; i < fjList.size(); i++) {
//							UploadFJ ufj = new UploadFJ(false);
//							try {
//								ufj.ReadFJ(getDesktop(), (GhFile) fjList.get(i));
//							} catch (InterruptedException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//							fileModel.add(ufj);
//						}
//						upList.setModel(fileModel);
//						rowFile.setVisible(true);
//					}
//					}
//					
//				}
//				
//			});
//			}
			
		}
		// ����
		if (xm == null) {// �����
			rowFile.setVisible(false);
			downFileZip.setDisabled(true);
		} else {// �޸�
			List fjList = ghfileService.findByFxmIdandFType(xm.getKyId(), 10);
			if (fjList==null||fjList!=null&&fjList.size() == 0) {// �޸���
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
	/*	if(xm==null){//�������Ŀ
		//��Ŀ��Ψһ����
			
		if(xmService.findByKynumber(number.getValue()).size()!=0){
			throw new WrongValueException(number, "��Ŀ��ű���Ψһ������Ŀ��Ŵ��ڣ����ʵ����д��");
		}}else{//�޸�����Ŀ
			if(xmService.findByKynumber(number.getValue()).size()!=1){
				throw new WrongValueException(number, "��Ŀ��ű���Ψһ������Ŀ��Ŵ��ڣ����ʵ����д��");
			}
		}
		//��Ŀ���
		if(number.getValue()==null||"".equals(number.getValue().trim())){
			throw new WrongValueException(number, "����û����д��Ŀ��ţ�");
		}*/
		//��������
		if(cgmc.getValue()==null||"".equals(cgmc.getValue().trim())){
			throw new WrongValueException(cgmc, "����û����д�������ƣ�");
		}
		//��Դ
		if(ly.getValue()==null||"".equals(ly.getValue().trim())){
			throw new WrongValueException(ly, "����û����д��Ŀ��Դ��");
		}
		//��Ŀ������
		if(proman.getValue()==null||"".equals(proman.getValue().trim())){
			throw new WrongValueException(proman, "����û����д��Ŀ�����ˣ�");
		}
		//����ʱ��
		if(lxsj.getValue()==null||"".equals(lxsj.getValue().trim())){
			throw new WrongValueException(lxsj, "����û����д����ʱ�䣬��ʽ��2008/9/28��2008��2008/9��");
		}else{
			try{
				String str = lxsj.getValue().trim();
				if(str.length()<4){
					throw new WrongValueException(lxsj, "�����������ʱ���ʽ�д�������������д����ʽ��2008/9/28��2008��2008/9��");
				}
				else if(str.length()==4||'/'==str.charAt(4)){
					String s[] = str.split("/");
					 if(s.length==1||s.length==2||s.length==3){
						for(int i=0;i<s.length;i++){
							String si = s[i];
							Integer.parseInt(si);
						}
					}else{
						throw new WrongValueException(lxsj, "�����������ʱ���ʽ�д�������������д����ʽ��2008/9/28��2008��2008/9��");
					}
				}else{
					throw new WrongValueException(lxsj, "�����������ʱ���ʽ�д�������������д����ʽ��2008/9/28��2008��2008/9��");
				}
			}catch(NumberFormatException e){
				throw new WrongValueException(lxsj, "�����������ʱ���ʽ�д�������������д����ʽ��2008/9/28��2008��2008/9��");
			}
		}
		//��ʼʱ��
		if(kaishi.getValue()==null||"".equals(kaishi.getValue().trim())){
			throw new WrongValueException(kaishi, "����û����д��ʼʱ�䣬��ʽ��2008/9/28��2008��2008/9��");
		}else{
			try{
				String str = kaishi.getValue().trim();
				if(str.length()<4){
					throw new WrongValueException(kaishi, "������Ŀ�ʼʱ���ʽ�д�������������д����ʽ��2008/9/28��2008��2008/9��");
				}
				else if(str.length()==4||'/'==str.charAt(4)){
					String s[] = str.split("/");
					 if(s.length==1||s.length==2||s.length==3){
						for(int i=0;i<s.length;i++){
							String si = s[i];
							Integer.parseInt(si);
						}
					}else{
						throw new WrongValueException(kaishi, "������Ŀ�ʼʱ���ʽ�д�������������д����ʽ��2008/9/28��2008��2008/9��");
					}
				}else{
					throw new WrongValueException(kaishi, "������Ŀ�ʼʱ���ʽ�д�������������д����ʽ��2008/9/28��2008��2008/9��");
				}
			}catch(NumberFormatException e){
				throw new WrongValueException(kaishi, "������Ŀ�ʼʱ���ʽ�д�������������д����ʽ��2008/9/28��2008��2008/9��");
			}
		}
		//����ʱ��
		if(jieshu.getValue()==null||"".equals(jieshu.getValue().trim())){
			throw new WrongValueException(jieshu, "����û����д����ʱ�䣬��ʽ��2008/9/28��2008��2008/9��");
		}else{
			try{
				String str = jieshu.getValue().trim();
				if(str.length()<4){
					throw new WrongValueException(jieshu, "������Ľ���ʱ���ʽ�д��󣬸�ʽ��2008/9/28��2008��2008/9��");
				}
				else if(str.length()==4||'/'==str.charAt(4)){
					String s[] = str.split("/");
					if(s.length==1||s.length==2||s.length==3){
						for(int i=0;i<s.length;i++){
							String si = s[i];
							Integer.parseInt(si);
						}
					}else{
						throw new WrongValueException(jieshu, "������Ľ���ʱ���ʽ�д��󣬸�ʽ��2008/9/28��2008��2008/9��");
						
					}
				}else{
					throw new WrongValueException(jieshu, "������Ľ���ʱ���ʽ�д��󣬸�ʽ��2008/9/28��2008��2008/9��");
					
				}
			}catch(NumberFormatException e){
				throw new WrongValueException(jieshu, "������Ľ���ʱ���ʽ�д��󣬸�ʽ��2008/9/28��2008��2008/9��");
				
			}
			
		}
		
		//����
		Float kyJf = 0F;
		if (jingfei.getValue() != null &&!"".equals(jingfei.getValue().toString().trim())) {
			kyJf = Float.parseFloat(jingfei.getValue()+"");
		}
		//���˹���
		Integer kyGx = 1;
//		if(gongxian.getValue()==null||"".equals(gongxian.getValue().trim())){
//			throw new WrongValueException(gongxian, "������Ŀ���Ա�Ƿ��зǷ��ַ���������һ���淶�����֣�");
//		}else{
//			try{
//				kyGx = Integer.parseInt(gongxian.getValue());
//			}catch(NumberFormatException e){
//				throw new WrongValueException(gongxian, "��Ŀ���Ա�зǷ��ַ���");
//			}
//		
//		}	
		//false��ʾ���޸ģ�true��ʾ���½�
		boolean index = false,owner=false; 
		if(xm==null){//˵�������½���һ����Ŀ
			 if(!lxsj.isDisabled()){
				 owner=true;
			    }
			xm = new GhXm();
			index = true;
		}
//		if(cgmc.getSelectedItem()==null){
//			//û��ѡ����Ŀ���ƣ�����Ŀ���ڸ��û�
//			owner=true;
//		}else{
//			owner=false;
//		}		
		if(index){
			//�½���Ŀ,�����������һ����ǰ�û���������Ŀ���ƣ����浽��Ŀ��ͽ�ʦ��Ŀ�����������û�ѡ�����Ŀ���ƣ�ֻ�����ʦ��Ŀ��
			if(owner){
//				if(!prostaffs.getValue().trim().contains(user.getKuName())&&!proman.getValue().trim().contains(user.getKuName())){
//					Messagebox.show("��Ŀ����Ա��������������������ϵ��Ŀ��Ϣ����ˣ�", "����", Messagebox.OK, Messagebox.EXCLAMATION);
//					xm=null;
//					return;
//				}
				if(!xmService.CheckOnlyOne(cgmc.getValue().trim(), GhXm.JYXM, ly.getValue().trim(),proman.getValue().trim())){
					Messagebox.show("���ύ����Ŀ��Ϣ�Ѵ��ڣ��������ظ���ӣ�","����",Messagebox.OK,Messagebox.EXCLAMATION);
					xm=null;
					return;
				}
				xm.setKyNumber(number.getValue().trim());
				xm.setKyMc(cgmc.getValue().trim());
				xm.setKyLxsj(lxsj.getValue().trim());
				xm.setKyKssj(kaishi.getValue().trim());
				xm.setKyJssj(jieshu.getValue().trim());
				xm.setKyJf(kyJf);
				xm.setKyLy(ly.getValue().trim());
				xm.setKyProstaffs(proman.getValue().trim());
				xm.setKyProman(proman.getValue().trim());
//				xm.setKyRegister(register.getValue().trim());
				xm.setKyTarget(target.getValue().trim());
				xm.setKyIdenttime(identtime.getValue().trim());
				xm.setKyLevel(level.getValue().trim());
				xm.setKySubjetype(String.valueOf(subjetype.getSelectedIndex()));
				xm.setKyClass(String.valueOf(kyclass.getSelectedIndex()));
				xm.setKyScale(String.valueOf(scale.getSelectedIndex()));
				xm.setKuId(kuid);
				xm.setKyLx(GhXm.JYXM);
				xm.setKyProgress(String.valueOf(progress.getSelectedIndex()));
				xmService.save(xm);
				//���浽��ʦ��Ŀ��
				GhJsxm jsxm=new GhJsxm();
				jsxm.setKyId(xm.getKyId());
				jsxm.setJsxmType(GhJsxm.JYXM);
				jsxm.setKuId(user.getKuId());
				jsxm.setKyCdrw(String.valueOf(cdrw.getSelectedIndex()));
				jsxm.setYjId(research.getSelectedIndex()!=0?((GhYjfx)research.getSelectedItem().getValue()).getGyId():0L);
				jsxm.setKyGx(kyGx);
				jsxm.setKyMemberName(user.getKuName());
				jsxm.setKyTaskDesc("");
				jsxmService.save(jsxm);
				// �����洢
				UploadFJ.ListModelListUploadCommand(fileModel, xm.getKyId());
				Messagebox.show("����ɹ���","��ʾ",Messagebox.OK,Messagebox.INFORMATION);
				Events.postEvent(Events.ON_CHANGE, this, null);
			}else{
				//���浽��ʦ��Ŀ��
//				if(!prostaffs.getValue().trim().contains(user.getKuName())&&!proman.getValue().trim().contains(user.getKuName())){
//					Messagebox.show("��Ŀ����Ա��������������������ϵ��Ŀ��Ϣ����ˣ�", "����", Messagebox.OK, Messagebox.EXCLAMATION);
//					xm=null;
//					return;
//				}
//				GhJsxm jsxm=new GhJsxm();
//				jsxm.setKyId(xm.getKyId());
//				jsxm.setJsxmType(GhJsxm.JYXM);
//				jsxm.setKuId(user.getKuId());
//				jsxm.setKyCdrw(String.valueOf(cdrw.getSelectedIndex()));
//				jsxm.setYjId(research.getSelectedIndex()!=0?((GhYjfx)research.getSelectedItem().getValue()).getGyId():0L);
//				jsxm.setKyGx(kyGx);
//				jsxmService.save(jsxm);
				Messagebox.show("����ɹ���","��ʾ",Messagebox.OK,Messagebox.INFORMATION);
				Events.postEvent(Events.ON_CHANGE, this, null);
			}
		}else{
			//�޸���Ŀ��ԭ����Ŀ�Ǹ��û������ĸ�����Ŀ��ͽ�ʦ��Ŀ��ԭ����Ŀ�Ǹ��û�������ֻ���½�ʦ��Ŀ��
			if(user.getKuId().intValue()==xm.getKuId().intValue()){
				if(!xmService.findByNameAndLxAndLy(xm, cgmc.getValue().trim(),GhXm.JYXM, ly.getValue().trim(),proman.getValue().trim())){
					Messagebox.show("���ύ����Ŀ��Ϣ�Ѵ��ڣ��������ظ���ӣ�","����",Messagebox.OK,Messagebox.EXCLAMATION);
					return;
				}
				
				xm.setKyNumber(number.getValue().trim());
//				xm.setKyMc(cgmc.getValue());//���Ʋ��ɸ�
				xm.setKyLxsj(lxsj.getValue().trim());
				xm.setKyKssj(kaishi.getValue().trim());
				xm.setKyJssj(jieshu.getValue().trim());
				xm.setKyProstaffs(prostaffs.getValue().trim());
				xm.setKyProman(proman.getValue().trim());
//				xm.setKyRegister(register.getValue().trim());
				xm.setKyTarget(target.getValue().trim());
				xm.setKyIdenttime(identtime.getValue().trim());
				xm.setKyLevel(level.getValue().trim());
				xm.setKySubjetype(String.valueOf(subjetype.getSelectedIndex()));
				xm.setKyClass(String.valueOf(kyclass.getSelectedIndex()));
				xm.setKyScale(String.valueOf(scale.getSelectedIndex()));
				xm.setKyJf(kyJf);
				xm.setKyLy(ly.getValue());
				xm.setKuId(user.getKuId());
				xm.setKyLx(GhXm.JYXM);
				xm.setKyProgress(String.valueOf(progress.getSelectedIndex()));
				xm.setAuditState(null);
				xm.setAuditUid(null);
				xm.setReason(null);
				xmService.update(xm);
				List jslwlist=jsxmService.findByXmidAndtype(xm.getKyId(), GhJsxm.JYXM);
				for(int i=0;i<jslwlist.size();i++){
					GhJsxm js=(GhJsxm)jslwlist.get(i);
					if(!prostaffs.getValue().trim().contains(js.getUser().getKuName().trim())){
						jsxmService.delete(js);
					}
				}
				// �����洢
				UploadFJ.ListModelListUploadCommand(fileModel, xm.getKyId());
				GhJsxm jsxm=jsxmService.findByXmidAndKuidAndType(xm.getKyId(), user.getKuId(), GhJsxm.JYXM);
				if(jsxm!=null){
					jsxm.setKyCdrw(String.valueOf(cdrw.getSelectedIndex()));
					jsxm.setYjId(research.getSelectedIndex()!=0?((GhYjfx)research.getSelectedItem().getValue()).getGyId():0L);
					jsxm.setKyGx(kyGx);
					jsxmService.update(jsxm);
					Messagebox.show("����ɹ���","��ʾ",Messagebox.OK,Messagebox.INFORMATION);
					Events.postEvent(Events.ON_CHANGE, this, null);
				}else{
				
					Messagebox.show("����ɹ���","��ʾ",Messagebox.OK,Messagebox.INFORMATION);
					Events.postEvent(Events.ON_CHANGE, this, null);
				}
			}else{
				if(!prostaffs.getValue().trim().contains(user.getKuName())&&!proman.getValue().trim().contains(user.getKuName())){
					Messagebox.show("��Ŀ����Ա��������������������ϵ��Ŀ��Ϣ����ˣ�", "����", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
				//ֻ���½�ʦ��Ŀ��
				GhJsxm jsxm=jsxmService.findByXmidAndKuidAndType(xm.getKyId(), user.getKuId(),GhJsxm.JYXM);
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
					jsxm.setJsxmType(GhJsxm.JYXM);
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
	
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		fileModel = new ListModelList(new ArrayList());
		upList.setModel(fileModel);
	
//		cgmc.setModel(new ListModelList(xmService.findAllname(user.getKuId(),user.getKuName(),GhXm.JYXM,GhJsxm.JYXM)));
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
	
	 public void initXmWindow(String xmmc,String xmly,String porman,Short lx){
	    	GhXm cxm=xmService.findByNameAndLyAndLxAndFzr(xmmc, lx, xmly, porman);
	    	if(cxm!=null){
	    		xm=cxm;
	    		number.setValue(cxm.getKyNumber());number.setDisabled(true);
				ly.setValue(cxm.getKyLy()); 
				lxsj.setValue(cxm.getKyLxsj());lxsj.setDisabled(true);
				kaishi.setValue(cxm.getKyKssj());kaishi.setDisabled(true);
				jieshu.setValue(cxm.getKyJssj());jieshu.setDisabled(true);
				jingfei.setValue(BigDecimal.valueOf(cxm.getKyJf()));jingfei.setDisabled(true);
				prostaffs.setValue(cxm.getKyProstaffs());prostaffs.setDisabled(true);
				proman.setValue(cxm.getKyProman());
//				register.setValue(xm.getKyRegister());register.setDisabled(true);
				target.setValue(cxm.getKyTarget());target.setDisabled(true);
				identtime.setValue(cxm.getKyIdenttime());identtime.setDisabled(true);
				level.setValue(cxm.getKyLevel());level.setDisabled(true);
				// ѧ�Ʒ���
				if (cxm.getKySubjetype() == null || cxm.getKySubjetype() == "") {
					subjetype.setSelectedIndex(0);
					subjetype.setDisabled(true);
				} else {
					subjetype.setSelectedIndex(Integer.valueOf(cxm.getKySubjetype().trim()));
					subjetype.setDisabled(true);
				}
				// ��Ŀ���
				if (cxm.getKyClass() == null || cxm.getKyClass() == "") {
					kyclass.setSelectedIndex(0);
					kyclass.setDisabled(true);
				} else {
					kyclass.setSelectedIndex(Integer.valueOf(cxm.getKyClass().trim()));
					kyclass.setDisabled(true);
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
			    List namelist=new ArrayList();
				String str=prostaffs.getValue().trim();
//				if(str.contains("��")||str.contains(",")){
//					throw new WrongValueException(prostaffs, "��Ŀ����Ա��д����,��ѡ��ٺţ�");
//				}else{
//				while (str.contains("��")) {
//						Label lb = new Label(str.substring(0,str.indexOf("��")));
//						namelist.add(lb.getValue().trim());
//						str = str.substring(str.indexOf("��") + 1, str.length());
//					
//				}
//				namelist.add(str.trim());
//				gongxian.setValue(namelist.indexOf(user.getKuName())+1+"");
//	    	  }
				List listxm=projectmemberService.findByKyIdAndKuId(xm.getKyId(), user.getKuId());
				GhJsxm jsxm=(GhJsxm) listxm.get(0);
				gongxian.setValue(jsxm.getKyGx().toString());
	    	}
	    	else{
	    		xm=null;
//				kaishi.setValue("");
//				number.setValue("");
//				jieshu.setValue(""); 
//				jingfei.setValue("");
//				gongxian.setValue("0");
//				prostaffs.setValue("");
////				register.setValue("");
//				target.setValue("");
//				identtime.setValue("");
//				level.setValue("");
//				writeuser.setValue(user.getKuName());
//				number.setDisabled(false);
//				identtime.setDisabled(false);
//				level.setDisabled(false);
//				target.setDisabled(false);
//				lxsj.setValue("");
//				lxsj.setDisabled(false);
//				kaishi.setDisabled(false);
//				jieshu.setDisabled(false);
//				jingfei.setDisabled(false);
//				prostaffs.setDisabled(false);
//				subjetype.setSelectedIndex(0);subjetype.setDisabled(false);
//				kyclass.setSelectedIndex(0); kyclass.setDisabled(false);
//				scale.setSelectedIndex(0); scale.setDisabled(false);
//				progress.setSelectedIndex(0); progress.setDisabled(false);
	    	}
	    }
	 public void onClick$choice(){
	    	Map args=new HashMap();
	    	args.put("user", user);
	    	args.put("lx", GhXm.JYXM);
	    	args.put("type", GhJsxm.JYXM);
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
					ly.setValue(Xm.getKyLy());ly.setDisabled(true);
					lxsj.setValue(Xm.getKyLxsj());lxsj.setDisabled(true);
					kaishi.setValue(Xm.getKyKssj());kaishi.setDisabled(true);
					jieshu.setValue(Xm.getKyJssj());jieshu.setDisabled(true);
					jingfei.setValue(BigDecimal.valueOf(Xm.getKyJf()));jingfei.setDisabled(true);
					prostaffs.setValue(Xm.getKyProstaffs());prostaffs.setDisabled(true);
					proman.setValue(Xm.getKyProman());proman.setDisabled(true);
//					register.setValue(xm.getKyRegister());register.setDisabled(true);
					target.setValue(Xm.getKyTarget());target.setDisabled(true);
					identtime.setValue(Xm.getKyIdenttime());identtime.setDisabled(true);
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
					if (Xm.getKyClass() == null || Xm.getKyClass() == "") {
						kyclass.setSelectedIndex(0);
						kyclass.setDisabled(true);
					} else {
						
						kyclass.setSelectedIndex(Integer.valueOf(Xm.getKyClass().trim()));
						kyclass.setDisabled(true);
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
				   	sxw.detach();
				 	
					}
					}}
	    	});
	    }
	public void onClick$upFile() throws InterruptedException {
	/*	Object media = Fileupload.get();
		if (media == null) {
			return;
		}
		rowFile.setVisible(true);
		fileModel.add(media);*/
		UploadFJ ufj = new UploadFJ(true);
		Media media = ufj.Upload(this.getDesktop(),10, 10, "�������ܳ���10MB",
				"������Ŀ����------�������ܳ���10MB");
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
				+ "\\" + "_" + this.xm.getKyId() + "_" + ".zip");
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

	public void onClick$reset(){
		// ȥ���ɵĴ������
		IsZipExists.delZipFile(this.getDesktop().getWebApp().getRealPath(
				"/upload/xkjs/").trim()
				+ "\\" + "_" + this.xm.getKyId() + "_" + ".zip");
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
