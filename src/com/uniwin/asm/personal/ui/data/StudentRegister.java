package com.uniwin.asm.personal.ui.data;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

import org.iti.xypt.entity.Student;
import org.iti.xypt.service.StudentService;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.uniwin.common.util.ConvertUtil;
import com.uniwin.common.util.DateUtil;
import com.uniwin.common.util.IPUtil;
import com.uniwin.framework.common.fileuploadex.FileuploadEx;
import com.uniwin.framework.common.fileuploadex.ImageUploadPackage;
import com.uniwin.framework.common.fileuploadex.SetFileUploadError;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

public class StudentRegister extends Window implements AfterCompose {
	// �û����ݷ��ʽӿ�
	private UserService userService;
	// �û���������
	/**
	 * ��ʵ����
	 */
	private Textbox kuName,homaddress;
	private org.zkoss.zul.Image img;
	private Button upload;
	private Button delete;
	/**
	 * ѧ��
	 */
	private Label stid;
	// �༶��
	private Label stClassList, xueyuan, bumen;
	//
	private Label yuan, xi;
	/**
	 * �����ַ
	 */
	private Textbox kuEmail;
	/**
	 * �����
	 */
	private Textbox dorm;
	
	/**
	 * �绰
	 */
	private Textbox kuPhone;
	/**
	 * ��IP
	 */
	private Textbox uBandIp;
	/**
	 * �û���¼��
	 */
	private Label kuLid;
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
	 * ������
	 */
	private Listbox bangType;
	/**
	 * �û����
	 */
	private Textbox uinfo;
	/**
	 * ���֤�ź�������ò
	 */
	private Textbox kuIdentity, kuPolitical;
	/**
	 * ѧ��״̬
	 */
	private Label xueji;
	private Textbox name1,name2,work1,work2,phone1,phone2,contact1,contact2;
	private Textbox name3,name4,name5,work3,work4,work5,phone3,phone4,phone5,contact3,contact4,contact5;
	// �û�ʵ��
	WkTUser user;
	StudentService studentService;
	// ���ð�ť
	private Button reset;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		bangType.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				bangTypeHandle();
			}
		});
		yuan.setValue(user.getDept().getGradeName(WkTDept.GRADE_YUAN));
		xi.setValue(user.getDept().getGradeName(WkTDept.GRADE_XI));
		initUser();// ���ó�ʼ�����ں���
	}

	/**
	 * <li>������������ʼ�� registerҳ�����ݡ� void
	 * 
	 * @author bobo
	 * @2010-3-13
	 */
	public void initUser() {
		kuLid.setValue(user.getKuLid());
		kuName.setValue(user.getKuName());
		if (user.getKuEmail() != null) {
			kuEmail.setValue(user.getKuEmail().trim());
		}
		
		
		if (user.getKuPhone() != null) {
			kuPhone.setValue(user.getKuPhone().trim());
		}
		if (user.getKuBirthday() != null && user.getKuBirthday().length() > 0) {
			kuBirthday.setValue(ConvertUtil.convertDate(DateUtil.getDateString(user.getKuBirthday())));
		}
		if (user.getKuSex().trim().equalsIgnoreCase("2")) { // �Ա����ַ���Ҫȥ�ո񣬷�������ʧ��
			kuSex.setSelectedIndex(1);
		} else {
			kuSex.setSelectedIndex(0);
		}
		Student student = studentService.findByKuid(user.getKuId());
		stid.setValue(student.getStId());
		stClassList.setValue(student.getStClass());
		xueyuan.setValue(user.getDept().getPdept().getKdName());
		bumen.setValue(user.getDept().getKdName());
		xueji.setValue(Student.NORMALS[student.getStNormal()]);
		kuIdentity.setValue(user.getKuSfzh());
		kuPolitical.setValue(user.getKuPolitical());
		uBandIp.setValue(user.getKuBindaddr());
		bangType.setSelectedIndex(Integer.valueOf(user.getKuBindtype().trim()));
		bangTypeHandle(); // ���ú���
		if (user.getKuAutoenter().trim().equalsIgnoreCase("1")) {
			kuAutoenter.setChecked(true);
		} else {
			kuAutoenter.setChecked(false);
		}
		if (user.getKuIntro() != null) {
			uinfo.setValue(user.getKuIntro());
		}
		// �Ľ���ͷ����ʾ
		// ɾ��cacheĿ¼�е������ļ�
		delete.setDisabled(false);
		// �������������ͼƬ
		if (user.getKuPath() == null || user.getKuPath().length() == 0) {
			delete.setDisabled(true);
			img.setSrc("/admin/image/head/default.jpg");
		} else {
			// System.out.println("kuPAth="+user.getKuPath());
			String srcPath = this.getDesktop().getWebApp().getRealPath(user.getKuPath());
			File srcFile = new File(srcPath);
			if (!srcFile.exists()) {// ͼƬ��������ʾĬ��ͷ��
				delete.setDisabled(true);
				img.setSrc("/admin/image/head/default.jpg");
			} else {
				img.setSrc(user.getKuPath());
			}
		}
		//�¼�--ѧ����
		Student stu=(Student)studentService.get(Student.class, user.getKuId());
		if(stu.getStRoom()!=null){//�����
			dorm.setValue(stu.getStRoom());
		}
		//��ͥ��Ա
		if(stu.getStFname()!=null){
			name1.setValue(stu.getStFname());
		}
		if(stu.getStMname()!=null){
			name2.setValue(stu.getStMname());
		}
		if(stu.getStFrelation()!=null){//��ϵ
			contact1.setValue(stu.getStFrelation());
		}
		if(stu.getStMrelation()!=null){//��ϵ
			contact2.setValue(stu.getStMrelation());
		}
		if(stu.getStMrelation()!=null){//��ϵ
			work1.setValue(stu.getStMrelation());
		}
		if(stu.getStFwork()!=null){//������λ
			work1.setValue(stu.getStFwork());
		}
		if(stu.getStMwork()!=null){//������λ
			work2.setValue(stu.getStMwork());
		}
		if(stu.getStFphone()!=null){//������λ
			phone1.setValue(stu.getStFphone());
		}
		if(stu.getStMphon()!=null){//������λ
			phone2.setValue(stu.getStMphon());
		}
		if(stu.getStAddress()!=null){//��ͥסַ
			homaddress.setValue(stu.getStAddress());
		}
	//����ϵ
		if(stu.getStSnamefirst()!=null){
			name3.setValue(stu.getStSnamefirst());
		}
		if(stu.getStSrelationfirst()!=null){
			contact3.setValue(stu.getStSrelationfirst());
		}
		if(stu.getStSworkfirst()!=null){
			work3.setValue(stu.getStSworkfirst());
		}
		if(stu.getStSphonefirst()!=null){
			phone3.setValue(stu.getStSphonefirst());
		}
		//��2��
		if(stu.getStSnamesecond()!=null){
			name4.setValue(stu.getStSnamesecond());
		}
		if(stu.getStSrelationsecond()!=null){
			contact4.setValue(stu.getStSrelationsecond());
		}
		if(stu.getStSworksecond()!=null){
			work4.setValue(stu.getStSworksecond());
		}
		if(stu.getStSphonesecond()!=null){
			phone4.setValue(stu.getStSphonesecond());
		}
		//��3��
		if(stu.getStSnamethird()!=null){
			name5.setValue(stu.getStSnamethird());
		}
		if(stu.getStSrelationthird()!=null){
			contact5.setValue(stu.getStSrelationthird());
		}
		if(stu.getStSworkthird()!=null){
			work5.setValue(stu.getStSworkthird());
		}
		if(stu.getStSphonethird()!=null){
			phone5.setValue(stu.getStSphonethird());
		}

		
	}

	public void onClick$upload() throws InterruptedException, IOException {
		Integer maxSize = 1024;
		this.getDesktop().getWebApp().getConfiguration().setMaxUploadSize(maxSize.intValue());
		String fileTooLarge = "�ϴ���ͼƬ�ļ�����,�ļ���СӦС��1MB";
		String confPath = Sessions.getCurrent().getWebApp().getRealPath("/") + "/WEB-INF/classes/conf.properties";
		SetFileUploadError.SetErrorToConf(fileTooLarge, confPath, "fileTooLarge");
		// //test
		// System.out.println(SetFileUploadError.GetErrorFromConf(confPath));
		// //test
		// this.getDesktop().getWebApp().getConfiguration().addErrorPage("ajax", SizeLimitExceededException.class, "error.zul");
		// �ϴ�֮ǰɾ��cacheĿ¼�е����������ļ�
		// ɾ��cacheĿ¼�е������ļ�
		// WkTUser user=(WkTUser) xypttitleService.get(WkTUser.class,
		// getXyUserRole().getUser().getKuId());
		// user.setKuPath("");
		// xypttitleService.update(user);
		// try{
		String Path, Type;
		ImageUploadPackage iUP = FileuploadEx.get();
		Media media = null;
		if (iUP.getIsCancel()) {
			// ����ȡ����ť
			return;
		}
		// media=iUP.getMedia0();
		if (iUP.getMedia0() != null) {
			media = iUP.getMedia0();
			Path = this.getDesktop().getWebApp().getRealPath("/admin/image/head/" + media.getName());
			Type = media.getName().substring(media.getName().lastIndexOf("."), media.getName().length());
			Type = Type.toLowerCase();
			if (Type.equals(".jpg") || Type.equals(".bmp") || Type.equals(".gif") || Type.equals(".png") || Type.equals(".JPG") || Type.equals(".BMP") || Type.equals(".GIF") || Type.equals(".PNG")) {
				File file = new File(Path);
				InputStream ins = media.getStreamData();
				OutputStream out = new FileOutputStream(file);
				byte[] buf = new byte[1024];
				int len;
				while ((len = ins.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				out.close();
				ins.close();
			} else {
				Messagebox.show("�ļ�����ֻ��Ϊjpg,bmp,gif,png��ʽ��", "��ʾ", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
		} else {
			Messagebox.show("�ļ������ڣ�", "��ʾ", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		File srcfile = new File(Path);
		if (!srcfile.exists()) {
			return;
		}
		Image src = ImageIO.read(srcfile);
		BufferedImage pic = new BufferedImage(150, 170, BufferedImage.TYPE_INT_RGB);
		pic.getGraphics().drawImage(src.getScaledInstance(150, 170, Image.SCALE_SMOOTH), 0, 0, null);
		Random r = new Random();
		if (user.getKuPath() == null || user.getKuPath().equals("")) {
			user.setKuPath("");
		} else {
			File oldImage = new File(this.getDesktop().getWebApp().getRealPath(user.getKuPath()));
			if (oldImage.exists())
				oldImage.delete();
		}
		String kuPath = "/admin/image/head/" + stid.getValue().trim() + "_" + r.nextInt() + Type;// ".jpg";
		user.setKuPath(kuPath.trim());
		studentService.update(user);
		ImageIO.write(pic, Type.substring(1, Type.length()), new File(this.getDesktop().getWebApp().getRealPath(kuPath)));
		File file = new File(Path);
		if (file.exists())
			file.delete();
		initUser();
		// }catch(Exception fileSizeE){
		// System.out.println(fileSizeE);
		// }
	}

	public void onClick$delete() {
		String path = this.getDesktop().getWebApp().getRealPath(user.getKuPath());
		File file = new File(path);
		if (file.exists())
			file.delete();
		user.setKuPath("".trim());
		this.studentService.update(user);
		initUser();
	}

	/**
	 * <li>�����������û���Ϣ���¹��ܡ� void
	 * 
	 * @author bobo
	 * @throws InterruptedException
	 * @2010-3-1
	 */
	public void onClick$save() throws InterruptedException {
		if ("".equals(kuName.getValue())) {
			Messagebox.show("�û�������Ϊ�գ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			kuName.focus();
			return;
		}
		if ("".equals(kuEmail.getValue())) {
			Messagebox.show("�������䲻��Ϊ�գ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			kuEmail.focus();
			return;
		}
		if ("".equals(kuPhone.getValue())) {
			Messagebox.show("�绰����Ϊ�գ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			kuPhone.focus();
			return;
		}
		if ("".equals(kuIdentity.getValue())) {
			Messagebox.show("���֤�Ų���Ϊ�գ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			kuIdentity.focus();
			return;
		}
		if ("".equals(kuPolitical.getValue())) {
			Messagebox.show("������ò����Ϊ�գ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			kuPolitical.focus();
			return;
		}
		user.setKuName(kuName.getValue());
		user.setKuEmail(kuEmail.getValue());
		user.setKuPhone(kuPhone.getValue());
		user.setKuSfzh(kuIdentity.getValue());
		user.setKuPolitical(kuPolitical.getValue());
		if (kuBirthday.getValue() != null) {
			user.setKuBirthday(DateUtil.getDateString(kuBirthday.getValue()));
		}
		if (kuSex.getSelectedIndex() == 0) {
			user.setKuSex("1"); // 1�����С���2����Ů��
		} else {
			user.setKuSex("2");
		}
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
		if (uinfo.getValue() == null || "".equals(uinfo.getValue())) {
			user.setKuIntro("");
		} else {
			user.setKuIntro(uinfo.getValue());
		}
		userService.update(user);
		//�¼�--����ѧ������Ϣ
		//ͨ��KUID�õ�ѧ��ʵ��student
		Student stu=(Student)studentService.get(Student.class, user.getKuId());
		//����
		if (dorm.getValue() == null || "".equals(dorm.getValue())) {
			stu.setStRoom("");
		} else {
			stu.setStRoom(dorm.getValue());
		}
		//��ͥ��Ա
		if (name1.getValue() == null ){
			stu.setStFname("");
		}else  stu.setStFname(name1.getValue());
		if (name2.getValue() == null ){
			stu.setStMname("");
		}else  stu.setStMname(name2.getValue());	
		                  //��ϵ
		if (contact1.getValue() == null ){
			stu.setStFrelation("");
		}else  stu.setStFrelation(contact1.getValue());
		if (contact2.getValue() == null ){
			stu.setStMrelation("");
		}else  stu.setStMrelation(contact2.getValue());		
				//������λ
		if (work1.getValue() == null ){
			stu.setStFwork("");
		}else  stu.setStFwork(work1.getValue());
		if (work2.getValue() == null ){
			stu.setStMwork("");
		}else  stu.setStMwork(work2.getValue());			
				//��ϵ��ʽ
		if (phone1.getValue() == null ){
			stu.setStFphone("");   
		}else  stu.setStFphone(phone1.getValue());
		if (phone2.getValue() == null ){
			stu.setStMphon("");
		}else  stu.setStMphon(phone2.getValue());		
		//��ͥסַ
		if(homaddress.getValue()== null){
			stu.setStAddress("");
		}else   stu.setStAddress(homaddress.getValue());
		//����ϵ����
		if (name3.getValue() == null ){
			stu.setStSnamefirst("");
		}else  stu.setStSnamefirst(name3.getValue());
		if (contact3.getValue() == null ){
			stu.setStSrelationfirst("");
		}else  stu.setStSrelationfirst(contact3.getValue());
		
		if (work3.getValue() == null ){
			stu.setStSworkfirst("");
		}else  stu.setStSworkfirst(work3.getValue());			
				//��ϵ��ʽ
		if (phone3.getValue() == null ){
			stu.setStSphonefirst("");   
		}else  stu.setStSphonefirst(phone3.getValue());
		//��2��
		if (name4.getValue() == null ){
			stu.setStSnamesecond("");
		}else  stu.setStSnamesecond(name4.getValue());
		if (contact4.getValue() == null ){
			stu.setStSrelationsecond("");
		}else  stu.setStSrelationsecond(contact4.getValue());
		
		if (work4.getValue() == null ){
			stu.setStSworksecond("");
		}else  stu.setStSworksecond(work4.getValue());			
				//��ϵ��ʽ
		if (phone4.getValue() == null ){
			stu.setStSphonesecond("");   
		}else  stu.setStSphonesecond(phone4.getValue());
		//��3��
		if (name5.getValue() == null ){
			stu.setStSnamethird("");
		}else  stu.setStSnamethird(name5.getValue());
		if (contact5.getValue() == null ){
			stu.setStSrelationthird("");
		}else  stu.setStSrelationthird(contact5.getValue());
		
		if (work5.getValue() == null ){
			stu.setStSworkthird("");
		}else  stu.setStSworkthird(work5.getValue());			
				//��ϵ��ʽ
		if (phone5.getValue() == null ){
			stu.setStSphonethird("");   
		}else  stu.setStSphonethird(phone5.getValue());
		userService.update(stu);
		Sessions.getCurrent().setAttribute("user", user);
		Messagebox.show("����ɹ���", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
	}

	/**
	 * <li>�����������û���Ϣ���ù��ܡ� void
	 * 
	 * @author bobo
	 * @2010-4-13
	 */
	public void onClick$reset() {
		kuName.setValue("");
		kuEmail.setRawValue("");
		kuPhone.setValue("");
		kuBirthday.setRawValue(null);
		kuSex.setSelectedIndex(0);
		bangType.setSelectedIndex(0);
		kuAutoenter.setChecked(false);
		kuAutoenter.setDisabled(true);
		uBandIp.setValue("");
		kuIdentity.setValue("");
		kuPolitical.setValue("");
	}

	/**
	 * ��԰��벻�󶨽��в���
	 */
	private void bangTypeHandle() {
		if (bangType.getSelectedIndex() == 0) {// ����
			uBandIp.setRawValue(null);
			uBandIp.setReadonly(true);
			kuAutoenter.setChecked(false);
			kuAutoenter.setDisabled(true);
		} else {// ��IP
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
}
