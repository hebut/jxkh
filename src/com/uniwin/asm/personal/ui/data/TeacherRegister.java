package com.uniwin.asm.personal.ui.data;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import org.iti.xypt.entity.Teacher;
import org.iti.xypt.entity.Title;
import org.iti.xypt.service.TeacherService;
import org.iti.xypt.service.XyptTitleService;
import org.iti.xypt.ui.base.BaseWindow;
import org.iti.xypt.ui.base.TitleSelectHbox;
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
import org.zkoss.zul.Fileupload; //import org.iti.zk.ex.*;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.data.struct.MediaUploadPackage;

import com.uniwin.common.util.ConvertUtil;
import com.uniwin.common.util.DateUtil;
import com.uniwin.common.util.IPUtil;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

public class TeacherRegister extends BaseWindow {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// �û����ݷ��ʽӿ�
	private UserService userService;
	// �û���������
	/**
	 * ��ʵ������������
	 */
	private Textbox kuName, kuUsedname;
	
	/**
	 * ��IP
	 */
	private Textbox uBandIp;
	/**
	 * �û���¼��
	 */
	private Label kuLid;
	/**
	 * ��ʦ��
	 */
	private Textbox thid;
	/**
	 * �Զ���¼
	 */
	private Checkbox kuAutoenter;
	/**
	 * �û�����
	 */
	private Datebox kuBirthday;
	/**
	 * ѡ������Ů��
	 */
	private Radiogroup kuSex;
	/**
	 * �Ƿ�˶��������
	 */
	private Radiogroup shuodao, bodao;
	/**
	 * ������
	 */
	private Listbox kuPolitical, kuEducational, kuXuewei, bangType;

	/**
	 * ѡ����
	 */
	Label deptSelect, schoolSelect;


	private Textbox uinfo, kuIdentity, healthstate,nativeplace,zhiwu;
	Listbox marrystate,kuNation,mandarin,computer;

	Datebox partytime;

	

	// �û�ʵ��
	WkTUser user=(WkTUser)Executions.getCurrent().getArg().get("user");
	// Teacher teacher;
	// ���ð�ť
	private org.zkoss.zul.Image img;
	TeacherService teacherService;
	
	XyptTitleService xypttitleService;
	Row state,Band;
	Label leave,reason;
	//ͷ��ť
	private Button delete;
	TitleSelectHbox selectTitle;
	Teacher teacher;
    private Button save1,reset1;
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		if(user==null){
			user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		}
		teacher = teacherService.findBykuid(user.getKuId());
		bangType.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				bangTypeHandle();
			}
		});

		initUser(); // ���ó�ʼ�����ں���
	}
	public void onClick$upload() throws InterruptedException, IOException {
//		Integer maxSize=30*1024;
//		Integer max=this.getDesktop().getWebApp().getConfiguration().getMaxUploadSize();
//		System.out.println(max);
//		this.getDesktop().getWebApp().getConfiguration().setMaxUploadSize(maxSize.intValue());
//		String fileTooLarge="�ϴ���ͼƬ�ļ�����,�ļ���СӦС��1MB";
//		String confPath=Sessions.getCurrent().getWebApp().getRealPath("/")+"/WEB-INF/classes/conf.properties";
//		SetFileUploadError.SetErrorToConf(fileTooLarge, confPath,"fileTooLarge");
		// //test
		//System.out.println(SetFileUploadError.GetErrorFromConf(confPath));
		// //test
		//this.getDesktop().getWebApp().getConfiguration().addErrorPage("ajax", SizeLimitExceededException.class, "error.zul");
		// �ϴ�֮ǰɾ��cacheĿ¼�е����������ļ�
		// ɾ��cacheĿ¼�е������ļ�
		// WkTUser user=(WkTUser) xypttitleService.get(WkTUser.class,
		// getXyUserRole().getUser().getKuId());
		// user.setKuPath("");
		// xypttitleService.update(user);
		//try{
		String Path, Type;
		//ImageUploadPackage iUP = FileuploadEx.get();
		////test
		MediaUploadPackage iUP=Fileupload.get_MUP("ͼƬ�ϴ�-----�ļ�ӦС��1MB");
		////
		Media media = null;
		if (iUP.getIsCancel()) {
			// ����ȡ����ť
			return;
		}
		// media=iUP.getMedia0();
		if (iUP.getMedia0() != null) {
			media = iUP.getMedia0();
			Path = this.getDesktop().getWebApp().getRealPath(
					"/admin/image/head/" + media.getName());
			Type = media.getName().substring(media.getName().lastIndexOf("."),
					media.getName().length());
			Type=Type.toLowerCase();
			if (Type.equals(".jpg")||Type.equals(".bmp")||Type.equals(".gif")||Type.equals(".png")||Type.equals(".JPG")||Type.equals(".BMP")||Type.equals(".GIF")||Type.equals(".PNG")) {
				File file = new File(Path);
				InputStream ins = media.getStreamData();
				OutputStream out = new FileOutputStream(file);
				byte[] buf = new byte[1024];
				int len;
				long finallen = 0L;
				while ((len = ins.read(buf)) > 0) {
					out.write(buf, 0, len);
					finallen = finallen + len;
				}
				out.close();
				ins.close();
//				OutputStream out = new FileOutputStream(newFile);
				if (finallen > 1 * 1024 * 1024) {
					//System.out.println(finallen);
					Messagebox.show("�ϴ��ļ�����������ƣ�" + ",�ļ����" + 1 + "M", "ע��",
							Messagebox.OK, Messagebox.ERROR);
					File tempFile = new File(Path + "\\" + media.getName() + "."
							+ Type);
					if (tempFile.exists()) {
						tempFile.delete();
					}
					return ;
				}
			} else {
				Messagebox.show("�ļ�����ֻ��Ϊjpg,bmp,gif,png��ʽ��", "��ʾ", Messagebox.OK,
						Messagebox.EXCLAMATION);
				return;
			}
		} else {
			Messagebox.show("��δѡ����Ҫ�ύ���ļ���", "��ʾ", Messagebox.OK,
					Messagebox.EXCLAMATION);
			return;
		}
		File srcfile = new File(Path);
		if (!srcfile.exists()) {
			return;
		}
		Image src = ImageIO.read(srcfile);
		BufferedImage pic = new BufferedImage(150, 170,
				BufferedImage.TYPE_INT_RGB);
		pic.getGraphics()
				.drawImage(src.getScaledInstance(150, 170, Image.SCALE_SMOOTH),
						0, 0, null);
		Random r = new Random();
		if(user.getKuPath()==null ||user.getKuPath().equals(""))
		{
			user.setKuPath("");
		}
		else{
			File oldImage=new File(this.getDesktop().getWebApp().getRealPath(user.getKuPath()));
			if(oldImage.exists())
				oldImage.delete();
		}
		String kuPath = "/admin/image/head/" + thid.getValue().trim() + "_"
				+ r.nextInt() + Type;//".jpg";
		user.setKuPath(kuPath.trim());
		xypttitleService.update(user);
		ImageIO.write(pic, Type.substring(1, Type.length()), new File(this
				.getDesktop().getWebApp().getRealPath(kuPath)));
		File file = new File(Path);
		if (file.exists())
			file.delete();
		initUser();
	//}catch(Exception fileSizeE){
//		System.out.println(fileSizeE);
	//}
	}

	public void onClick$delete() {
		String path = this.getDesktop().getWebApp().getRealPath(
				user.getKuPath());
		File file = new File(path);
		if (file.exists())
			file.delete();
		user.setKuPath("".trim());
		this.xypttitleService.update(user);
		initUser();
	}

	/**
	 * <li>������������ʼ�� registerҳ�����ݡ� void
	 * 
	 * @author bobo
	 * @2010-3-13
	 */
	public void initUser() {
		List pol = new ArrayList();
		List edu = new ArrayList();
		List xw = new ArrayList();
		List nat = new ArrayList();
		List mar = new ArrayList();
		List mand = new ArrayList();
		List comp = new ArrayList();
		List tea = new ArrayList();
		List worp = new ArrayList();
		List wort = new ArrayList();
		List stafs = new ArrayList();
		List stafp = new ArrayList();
		List fyear = new ArrayList();
		List hyear = new ArrayList();
		String[] political = { "-��ѡ��-", "�й���������Ա", "�й�������Ԥ����Ա", "�й�����������������Ա",
				"�й����񵳸���ίԱ���Ա", "�й�����ͬ����Ա", "�й������������Ա", "�й������ٽ����Ա",
				"�й�ũ����������Ա", "�й��¹�����Ա", "����ѧ����Ա", "̨����������ͬ����Ա", "�޵���������ʿ", "Ⱥ��" };
		for (int j = 0; j < political.length; j++) {
			pol.add(political[j]);
		}
		kuPolitical.setModel(new ListModelList(pol));
		String[] Educational = { "-��ѡ��-", "��ʿ��ҵ", "��ʿ��ҵ", "��ʿ��ҵ", "˫˶ʿ",
				"˶ʿ��ҵ", "˶ʿ��ҵ", "˶ʿ��ҵ", "�൱˶ʿ��ҵ", "�о������ҵ", "�о������ҵ", "�о�������ҵ",
				"˫����", "���Ʊ�ҵ", "���ƽ�ҵ", "������ҵ", "�൱���Ʊ�ҵ", "˫��ר", "��ר��ҵ", "��ר��ҵ",
				"�൱��ר��ҵ", "��ר��ҵ", "��ר��ҵ" };
		for (int j = 0; j < Educational.length; j++) {
			edu.add(Educational[j]);
		}
		kuEducational.setModel(new ListModelList(edu));
		String[] xuewei = { "-��ѡ��-", "������ʿ", "��ʿ", "˶ʿ", "˫˶ʿ", "ѧʿ", "��" };
		for (int j = 0; j < xuewei.length; j++) {
			xw.add(xuewei[j]);
		}
		kuXuewei.setModel(new ListModelList(xw));
		String[] nation = { "-��ѡ��-", "����", "�ɹ���", "����", "����", "ά�����", "����",
				"����", "׳��", "������", "������", "����", "����", "����", "����", "������", "������",
				"��������", "����", "����", "������", "����", "���", "��ɽ��", "������", "ˮ��",
				"������", "������", "������", "�¶�������", "����", "���Ӷ���", "������", "Ǽ��", "������",
				"������", "ë����", "������", "������", "������", "��������", "������", "ŭ��",
				"���α����", "����˹��", "���¿���", "�°���", "������", "ԣ����", "����", "��������",
				"������", "���״���", "������", "�Ű���", "��ŵ��", "�����" };
		for (int i = 0; i < nation.length; i++) {
			nat.add(nation[i]);
		}
		kuNation.setModel(new ListModelList(nat));
		String[] marry = { "-��ѡ��-", "δ��", "�ѻ�", "����", "�ٻ�" };
		for (int i = 0; i < marry.length; i++) {
			mar.add(marry[i]);
		}
		marrystate.setModel(new ListModelList(mar));
		String[] mandari = { "-��ѡ��-", "һ���׵�", "һ���ҵ�", "�����׵�", "�����ҵ�" };
		for (int i = 0; i < mandari.length; i++) {
			mand.add(mandari[i]);
		}
		mandarin.setModel(new ListModelList(mand));
		String[] com = { "-��ѡ��-", "����һ��", "���Ҷ���", "��������", "�����ļ�" };
		for (int i = 0; i < com.length; i++) {
			comp.add(com[i]);
		}
		computer.setModel(new ListModelList(comp));
		String[] teacherqualify = { "-��ѡ��-", "�׶�԰��ʦ�ʸ�", "Сѧ��ʦ�ʸ�", "������ѧ��ʦ�ʸ�",
				"�߼���ѧ��ʦ�ʸ�", "�е�ְҵѧУ��ʦ�ʸ�", "�ߵ�ѧУ��ʦ�ʸ�" };
		for (int i = 0; i < teacherqualify.length; i++) {
			tea.add(teacherqualify[i]);
		}
		
		kuLid.setValue(user.getKuLid());
		kuName.setValue(user.getKuName());
		if (user.getKuUsedname() != null) {
			kuUsedname.setValue(user.getKuUsedname());
		}
		
		if (user.getKuBirthday() != null && user.getKuBirthday().length() > 0) {
			kuBirthday.setValue(ConvertUtil.convertDate(DateUtil
					.getDateString(user.getKuBirthday())));
		}
		if (user.getKuPartytime() != null && user.getKuPartytime().length() > 0) {
			partytime.setValue(ConvertUtil.convertDate(DateUtil
					.getDateString(user.getKuPartytime())));
		}
		if (user.getKuHealth() != null) {
			healthstate.setValue(user.getKuHealth());
		}
		if (user.getKuNativeplace() != null) {
			nativeplace.setValue(user.getKuNativeplace());
		}
	
		if(teacher.getThPosition() != null){

			zhiwu.setValue(teacher.getThPosition());
		}
		if (user.getKuSex().trim().equalsIgnoreCase("2")) { // �Ա����ַ���Ҫȥ�ո񣬷�������ʧ��
			kuSex.setSelectedIndex(1);
		} else {
			kuSex.setSelectedIndex(0);
		}
		uBandIp.setValue(user.getKuBindaddr());
		bangType.setSelectedIndex(Integer.valueOf(user.getKuBindtype().trim()));
		bangTypeHandle(); // ���ú���
		if (user.getKuAutoenter().trim().equalsIgnoreCase("1")) {
			kuAutoenter.setChecked(true);
		} else {
			kuAutoenter.setChecked(false);
		}
		deptSelect.setValue(user.getDept().getKdName());
		schoolSelect.setValue(user.getXxDept());
		kuIdentity.setValue(user.getKuSfzh());
		if (user.getKuPolitical() == null || user.getKuPolitical() == "") {
			kuPolitical.setSelectedIndex(0);
		} else {
			kuPolitical.setSelectedIndex(Integer.valueOf(user.getKuPolitical()
					.trim()));
		}
		if (user.getKuEducational() == null || user.getKuEducational() == "") {
			kuEducational.setSelectedIndex(0);
		} else {
			kuEducational.setSelectedIndex(Integer.valueOf(user
					.getKuEducational().trim()));
		}
		if (user.getKuXuewei() == null || user.getKuXuewei() == "") {
			kuXuewei.setSelectedIndex(0);
		} else {
			kuXuewei.setSelectedIndex(Integer
					.valueOf(user.getKuXuewei().trim()));
		}
		if (user.getKuNation() == null || user.getKuNation() == "") {
			kuNation.setSelectedIndex(0);
		} else {
			kuNation.setSelectedIndex(Integer.valueOf(user.getKuNation()));
		}
		if (user.getKuMarstatus() == null || user.getKuMarstatus() == "") {
			marrystate.setSelectedIndex(0);
		} else {
			marrystate.setSelectedIndex(Integer.valueOf(user.getKuMarstatus()));
		}
		if (user.getKuSpoken() == null || user.getKuSpoken() == "") {
			mandarin.setSelectedIndex(0);
		} else {
			mandarin.setSelectedIndex(Integer.valueOf(user.getKuSpoken()));
		}
		if (user.getKuComputer() == null || user.getKuComputer() == "") {
			computer.setSelectedIndex(0);
		} else {
			computer.setSelectedIndex(Integer.valueOf(user.getKuComputer()));
		}
		
		/*
		 * if(teacher.getThFirsub()!=null){ List
		 * mlst=majorService.findByZyid(teacher.getThFirsub());
		 * if(mlst!=null&&mlst.size()!=0){ GhZy z=(GhZy) mlst.get(0);
		 * System.out.println("ss....."+z.getZyId()); fhighmajor.setMajor(z); }
		 * 
		 * }
		 */

		
//		if(fedubackgr.getSelectedItem()!=null){
//			teacher.setThFiredu(Long.parseLong(fedubackgr.getSelectedIndex()+""));
//		}
//		if(facdegree.getSelectedItem()!=null){
//			teacher.setThFirdeg(Long.parseLong(facdegree.getSelectedIndex()+""));
//		}
//		//facdegree   fedubackgr hedubackgr hacdegree
//		 
//		if(hedubackgr.getSelectedItem()!=null){
//			teacher.setThEducation(hedubackgr.getSelectedIndex()+"");
//		}
//		if(hacdegree.getSelectedItem()!=null){
//			teacher.setThSupsesub(hacdegree.getSelectedIndex()+"");
//		}
		
		thid.setValue(teacher.getThId());
		thid.setReadonly(true);
		
	
		
		
		/*
		 * List yjfx = yjfxService.findByKuid(user.getKuId());
		 * 
		 * if(yjfx ==null ||yjfx.size()==0){ search.setValue(""); }else if(yjfx
		 * != null && yjfx.size()!=0){ String res=""; if(yjfx.size()!=1){
		 * for(int i=0;i<yjfx.size()-1;i++){ GhYjfx yj=(GhYjfx) yjfx.get(i);
		 * res=res+yj.getYjResearch()+","; } int m=(yjfx.size())-1; GhYjfx
		 * yj1=(GhYjfx)yjfx.get(m); res=res+yj1.getYjResearch(); }else
		 * if(yjfx.size()==1){ GhYjfx yj=(GhYjfx) yjfx.get(0);
		 * res=res+yj.getYjResearch(); } search.setValue(res); }
		 */
		if (user.getKuIntro() != null) {
			uinfo.setValue(user.getKuIntro());
		}
		// titleHbox.setTitle(teacher.getTitle()); //ְ����Ϊһ��tab�����г�����
		if (teacher.getThAdvisor() != null) {
			if (teacher.getThAdvisor().intValue() == Teacher.ADVISOR_NONE) {
				shuodao.setSelectedIndex(1);
				bodao.setSelectedIndex(1);
			} else if (teacher.getThAdvisor().intValue() == Teacher.ADVISOR_SD) {
				shuodao.setSelectedIndex(0);
				bodao.setSelectedIndex(1);
			} else if (teacher.getThAdvisor().intValue() == Teacher.ADVISOR_BD) {
				shuodao.setSelectedIndex(1);
				bodao.setSelectedIndex(0);
			} else {
				shuodao.setSelectedIndex(0);
				bodao.setSelectedIndex(0);
			}
		} else {
			shuodao.setSelectedIndex(1);
			bodao.setSelectedIndex(1);
		}
		// �Ľ���ͷ����ʾ
		// ɾ��cacheĿ¼�е������ļ�
		delete.setDisabled(false);
		// �������������ͼƬ
		if (user.getKuPath() == null||user.getKuPath().length()==0) {
			delete.setDisabled(true);
			img.setSrc("/admin/image/head/default.jpg");
		} else {
			//System.out.println("kuPAth="+user.getKuPath());
			String srcPath = this.getDesktop().getWebApp().getRealPath(
					user.getKuPath());
			File srcFile = new File(srcPath);
			if (!srcFile.exists()) {// ͼƬ��������ʾĬ��ͷ��
				delete.setDisabled(true);
				img.setSrc("/admin/image/head/default.jpg");
			} else {
				img.setSrc(user.getKuPath());
			}
		}

		
		selectTitle.setTitle(teacher.getTitle());
	}

	/**
	 * <li>�����������û���Ϣ���¹��ܡ� void
	 * 
	 * @author bobo
	 * @throws InterruptedException
	 * @2010-3-1
	 */
	public void onClick$save1() throws InterruptedException {
		if ("".equals(kuName.getValue())) {
			Messagebox.show("�û�������Ϊ�գ�", "��ʾ", Messagebox.OK,
					Messagebox.INFORMATION);
			kuName.focus();
			return;
		}
		if (kuIdentity.getValue().equals("")) {
			Messagebox.show("���֤�Ų���Ϊ�գ�", "��ʾ", Messagebox.OK,
					Messagebox.INFORMATION);
			kuIdentity.focus();
			return;
		}

		user.setKuName(kuName.getText());
		user.setKuUsedname(kuUsedname.getText());
		user.setKuPolitical(String.valueOf(kuPolitical.getSelectedIndex()));
		user.setKuSfzh(kuIdentity.getValue());
		user.setKuNation(String.valueOf(kuNation.getSelectedIndex()));
		user.setKuEducational(String.valueOf(kuEducational.getSelectedIndex()));
		user.setKuXuewei(String.valueOf(kuXuewei.getSelectedIndex()));
		user.setKuMarstatus(String.valueOf(marrystate.getSelectedIndex()));
		user.setKuSpoken(String.valueOf(mandarin.getSelectedIndex()));
		user.setKuComputer(String.valueOf(computer.getSelectedIndex()));
		if (kuBirthday.getValue() != null) {
			user.setKuBirthday(DateUtil.getDateString(kuBirthday.getValue()));
		}
		if (kuSex.getSelectedIndex() == 0) {
			user.setKuSex("1"); // 1�����С���2����Ů��
		} else {
			user.setKuSex("2");
		}
		user.setKuNativeplace(nativeplace.getText());
		// ����û�ѡ�񲻰󶨣��������䲻���Զ���½��ͬʱ����IP��ַ���ÿ�.
		// ����û�ѡ��IP�󶨣��������ð󶨵�IP��ַ���������������Ϊ����IP���������ø��û��ϴ���½IP��ַ��
		// ѡ��IP�󶨲������ð�IP���ж��û��Ƿ������Զ���½
		if (bangType.getSelectedIndex() == 0) {// ѡ�񲻰�
			user.setKuBindtype(WkTUser.BAND_NO);
			user.setKuAutoenter(WkTUser.AUTOENTER_NO);
			user.setKuBindaddr("");
		} else {
			user.setKuBindtype(WkTUser.BAND_YES);
			try {
				IPUtil.getIPLong(uBandIp.getValue());
				user.setKuBindaddr(uBandIp.getValue());
			} catch (Exception e) {
				throw new WrongValueException(uBandIp, "�󶨵�ַ�������!");
			}
			if (kuAutoenter.isChecked()) {
				user.setKuAutoenter(WkTUser.AUTOENTER_YES);
			} else {
				user.setKuAutoenter(WkTUser.AUTOENTER_NO);
			}
		}
		user.setKuIntro(uinfo.getValue());
		Teacher teacher = teacherService.findBykuid(user.getKuId());
		Listbox tlist=(Listbox)selectTitle.getChildren().get(1);
		teacher.setThTitle(((Title)(tlist.getSelectedItem()!=null?tlist.getSelectedItem().getValue():tlist.getItemAtIndex(0).getValue())).getTiId());
		if (shuodao.getSelectedIndex() == 0 && bodao.getSelectedIndex() == 0) {
			teacher.setThAdvisor(Teacher.ADVISOR_BOTH);
		} else if (shuodao.getSelectedIndex() == 0
				&& bodao.getSelectedIndex() == 1) {
			teacher.setThAdvisor(Teacher.ADVISOR_SD);
		} else if (bodao.getSelectedIndex() == 0
				&& shuodao.getSelectedIndex() == 1) {
			teacher.setThAdvisor(Teacher.ADVISOR_BD);
		} else {
			teacher.setThAdvisor(Teacher.ADVISOR_NONE);
		}
		if (healthstate.getValue() != null) {
			user.setKuHealth(healthstate.getValue());
		}
		if (zhiwu.getValue() != null) {
			teacher.setThPosition(zhiwu.getValue());
		}
		if (partytime.getValue() != null) {
			user.setKuPartytime(DateUtil.getDateString(partytime.getValue()));
		}
		
		
		/*//�ж��о�����������洢
		String[] a= search.getValue().split(",");
		if(yjfx == null || yjfx.size() == 0){
			for(int i=0;i<a.length;i++){
				String[] a1= a[i].split("��");
				if(a1.length !=0){
					for(int j = 0;j < a1.length;j++){
					GhYjfx yj=new GhYjfx();
					yj.setYjResearch(a1[j]);
					yj.setKuId(user.getKuId());
					yjfxService.save(yj);
					}
				}else {
					GhYjfx yj=new GhYjfx();
					yj.setYjResearch(a[i]);
					yj.setKuId(user.getKuId());
					yjfxService.save(yj);
				}
			}
		}else if(yjfx != null && yjfx.size() != 0){
			for(int j = 0;j < yjfx.size(); j++){
				GhYjfx yj=(GhYjfx) yjfx.get(j);
				yjfxService.delete(yj);
			}
			for(int i=0;i<a.length;i++){
				String[] a1= a[i].split("��");
				if(a1.length !=0){
					for(int j = 0;j < a1.length;j++){
						GhYjfx yj=new GhYjfx();
						yj.setYjResearch(a1[j]);
						yj.setKuId(user.getKuId());
						yjfxService.save(yj);
					}
				}else{
					GhYjfx yj=new GhYjfx();
					yj.setYjResearch(a[i]);
					yj.setKuId(user.getKuId());
					yjfxService.save(yj);
				}
			}
		}*/
		teacherService.update(teacher);
		// teacherService.save(teacher);
		userService.update(user);
		Sessions.getCurrent().setAttribute("user", user);
		Messagebox.show("����ɹ���", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
	}


	/**
	 * <li>�����������û���Ϣ���ù��ܡ� void
	 * 
	 * @author bobo
	 * @2010-4-13
	 */
	public void onClick$reset1() {
		kuName.setValue("");
		thid.setRawValue(null);
		kuUsedname.setRawValue(null);
		kuNation.setSelectedIndex(0);
		healthstate.setRawValue(null);
		marrystate.setSelectedIndex(0);
		nativeplace.setRawValue(null);
		zhiwu.setRawValue(null);
		partytime.setRawValue(null);
		kuBirthday.setRawValue(null);
		kuSex.setSelectedIndex(0);
		shuodao.setSelectedIndex(1);
		bodao.setSelectedIndex(1);
		bangType.setSelectedIndex(0);
		kuAutoenter.setChecked(false);
		kuAutoenter.setDisabled(true);
		uBandIp.setValue("");

		uinfo.setRawValue("");
		kuIdentity.setValue("");
		kuPolitical.setSelectedIndex(0);
		kuEducational.setSelectedIndex(0);
		kuXuewei.setSelectedIndex(0);
		//ְ��
		selectTitle.setTitle(teacher.getTitle());
//		List yjfx = yjfxService.findByKuid(user.getKuId());
//		if(yjfx!=null&&yjfx.size()>0){
//			GhYjfx fx=(GhYjfx) yjfx.get(0);
////			if(fx.getYjResearch1()!=null&&fx.getYjResearch1().length()>0){
////				search1.setSelectedIndex(Integer.parseInt(fx.getYjResearch1().trim()));
////				}else{
////					search1.setSelectedIndex(0);
////				}
////			if(fx.getYjResearch2()!=null&&fx.getYjResearch2().length()>0){
////				search2.setSelectedIndex(Integer.parseInt(fx.getYjResearch2().trim()));
////				}else{
////					search2.setSelectedIndex(0);
////				}
////			if(fx.getYjResearch3()!=null&&fx.getYjResearch3().length()>0){
////				search3.setSelectedIndex(Integer.parseInt(fx.getYjResearch3().trim()));
////				}else{
////					search3.setSelectedIndex(0);
////				}
////			if(fx.getYjResearch4()!=null&&fx.getYjResearch4().length()>0){
////				search4.setSelectedIndex(Integer.parseInt(fx.getYjResearch4().trim()));
////				}else{
////					search4.setSelectedIndex(0);
////				}
//			if(fx.getYjResearch5()!=null){
//				search5.setValue(fx.getYjResearch5());
//			}
//	}
	}
	/**
	 * ��԰��벻�󶨽��в���
	 */
	private void bangTypeHandle() {
		if (bangType.getSelectedIndex() == 0) {// ����
			Band.setVisible(false);
			uBandIp.setRawValue(null);
			uBandIp.setReadonly(true);
			kuAutoenter.setChecked(false);
			kuAutoenter.setDisabled(true);
		} else {// ��IP
			Band.setVisible(true);
			uBandIp.setReadonly(false);
			uBandIp.setValue(user.getKuBindaddr());
			kuAutoenter.setDisabled(false);
			if ("1".equalsIgnoreCase(user.getKuAutoenter().trim())) {
				kuAutoenter.setChecked(true);
			} else {
				kuAutoenter.setChecked(false);
			}
		}
	}

	@Override
	public void initShow() {
	}

	
	@Override
	public void initWindow() {

	}
	
	public void setButtonAble(){
		save1.setVisible(false);reset1.setVisible(false);
	}
}
