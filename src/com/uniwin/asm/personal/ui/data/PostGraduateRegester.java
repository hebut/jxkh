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

import org.iti.xypt.entity.Yjs;
import org.iti.xypt.service.XyptTitleService;
import org.iti.xypt.service.YjsService;
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
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.data.struct.MediaUploadPackage;

import com.uniwin.common.util.ConvertUtil;
import com.uniwin.common.util.DateUtil;
import com.uniwin.common.util.IPUtil;
import com.uniwin.framework.common.fileuploadex.SetFileUploadError;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

public class PostGraduateRegester extends Window implements AfterCompose {
	// �û����ݷ��ʽӿ�
	private UserService userService;
	// �û���������
	/**
	 * ��ʵ����
	 */
	private Textbox kuName,kuUsedname,nativeplace,healthstate,homaddress;
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
	private Listbox bangType,kuNation;
	/**
	 * �û����
	 */
	private Textbox uinfo,kuPolitical;
	/**
	 * ���֤�ź�������ò
	 */
	private Textbox kuIdentity;
	/**
	 * ѧ��״̬
	 */
	private Label xueji;
	// �û�ʵ��
	WkTUser user;
	YjsService yjsService;
	// ���ð�ť
	private Button reset;
	Listbox marrystate;
	private org.zkoss.zul.Image img;
	//ͷ��ť
	private Button upload;
	private Button delete;
	XyptTitleService xypttitleService;
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
		xi.setValue(user.getDept().getGradeName(Integer.parseInt(WkTDept.XUEKE)));
		initUser();// ���ó�ʼ�����ں���
	}

	/**
	 * <li>������������ʼ�� registerҳ�����ݡ� void
	 * 
	 * @author bobo
	 * @2010-3-13
	 */
	public void initUser() {
//		List pol = new ArrayList();
		List nat = new ArrayList();
//		String[] political = { "-��ѡ��-", "�й���������Ա", "�й�������Ԥ����Ա", "�й�����������������Ա",
//				"�й����񵳸���ίԱ���Ա", "�й�����ͬ����Ա", "�й������������Ա", "�й������ٽ����Ա",
//				"�й�ũ����������Ա", "�й��¹�����Ա", "����ѧ����Ա", "̨����������ͬ����Ա", "�޵���������ʿ", "Ⱥ��" };
//		for (int j = 0; j < political.length; j++) {
//			pol.add(political[j]);
//		}
//		kuPolitical.setModel(new ListModelList(pol));
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
//		List mar = new ArrayList();
//		String[] marry = { "-��ѡ��-", "δ��", "�ѻ�", "����", "�ٻ�" };
//		for (int i = 0; i < marry.length; i++) {
//			mar.add(marry[i]);
//		}
//		marrystate.setModel(new ListModelList(mar));
		kuLid.setValue(user.getKuLid());
		kuName.setValue(user.getKuName());
		if (user.getKuUsedname() != null) {
			kuUsedname.setValue(user.getKuUsedname());
		}
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
		if (user.getKuNation() == null || user.getKuNation() == "") {
			kuNation.setSelectedIndex(0);
		} else {
			kuNation.setSelectedIndex(Integer.valueOf(user.getKuNation()));
		}
		Yjs yjs = yjsService.findByKuid(user.getKuId());
		stid.setValue(yjs.getYjsId().toString());
		
		stClassList.setValue(yjs.getXyClass().getClSname());
		xueyuan.setValue(user.getDept().getPdept().getKdName());
		bumen.setValue(user.getDept().getKdName());
		xueji.setValue(Yjs.NORMALS[yjs.getYjsNormal()]);
		kuIdentity.setValue(user.getKuSfzh());
		kuPolitical.setValue(user.getKuPolitical()!=null?user.getKuPolitical():"");
//		if (user.getKuPolitical() == null || user.getKuPolitical() == "") {
//			kuPolitical.setSelectedIndex(0);
//		} else {
//			kuPolitical.setSelectedIndex(Integer.valueOf(user.getKuPolitical()
//					.trim()));
//		}
		if (user.getKuHealth() != null) {
			healthstate.setValue(user.getKuHealth());
		}
		if (user.getKuNativeplace() != null) {
			nativeplace.setValue(user.getKuNativeplace());
		}
		homaddress.setValue(user.getKuHomeaddress()!=null?user.getKuHomeaddress():"");
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
		if (user.getKuPath() == null||user.getKuPath().length()==0) {
			delete.setDisabled(true);
			img.setSrc("/admin/image/head/default.jpg");
		} else {
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
		user.setKuName(kuName.getValue());
		user.setKuUsedname(kuUsedname.getValue());
		user.setKuNativeplace(nativeplace.getValue());
		if (healthstate.getValue() != null) {
			user.setKuHealth(healthstate.getValue());
		}
		user.setKuEmail(kuEmail.getValue());
		user.setKuPhone(kuPhone.getValue());
		user.setKuSfzh(kuIdentity.getValue());
		user.setKuPolitical(kuPolitical.getValue());
		user.setKuNation(String.valueOf(kuNation.getSelectedIndex()));
//		user.setKuPolitical(String.valueOf(kuPolitical.getSelectedIndex()));
		if (kuBirthday.getValue() != null) {
			user.setKuBirthday(DateUtil.getDateString(kuBirthday.getValue()));
		}
		if (kuSex.getSelectedIndex() == 0) {
			user.setKuSex("1"); // 1�����С���2����Ů��
		} else {
			user.setKuSex("2");
		}
		user.setKuHomeaddress(homaddress.getValue());
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
		initUser();
//		kuName.setValue("");
//		kuEmail.setRawValue("");
//		kuPhone.setValue("");
//		kuBirthday.setRawValue(null);
//		kuSex.setSelectedIndex(0);
//		bangType.setSelectedIndex(0);
//		kuAutoenter.setChecked(false);
//		kuAutoenter.setDisabled(true);
//		uBandIp.setValue("");
//		kuIdentity.setValue("");
//		kuPolitical.setValue("");
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
	public void onClick$upload() throws InterruptedException, IOException {
		Integer maxSize=1024;
		this.getDesktop().getWebApp().getConfiguration().setMaxUploadSize(maxSize.intValue());
		String fileTooLarge="�ϴ���ͼƬ�ļ�����,�ļ���СӦС��1MB";
		String confPath=Sessions.getCurrent().getWebApp().getRealPath("/")+"/WEB-INF/classes/conf.properties";
		SetFileUploadError.SetErrorToConf(fileTooLarge, confPath,"fileTooLarge");
		String Path, Type;
		MediaUploadPackage iUP=Fileupload.get_MUP("ͼƬ�ϴ�-----�ļ�ӦС��1MB");
		////
		Media media = null;
		if (iUP.getIsCancel()) {
			// ����ȡ����ť
			return;
		}
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
				while ((len = ins.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				out.close();
				ins.close();
			} else {
				Messagebox.show("�ļ�����ֻ��Ϊjpg,bmp,gif,png��ʽ��", "��ʾ", Messagebox.OK,
						Messagebox.EXCLAMATION);
				return;
			}
		} else {
			Messagebox.show("�ļ������ڣ�", "��ʾ", Messagebox.OK,
					Messagebox.EXCLAMATION);
			return;
		}
		File srcfile = new File(Path);
		if (!srcfile.exists()) {
			return;
		}
		Image src = ImageIO.read(srcfile);
		BufferedImage pic = new BufferedImage(150, 170,BufferedImage.TYPE_INT_RGB);
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
		String kuPath = "/admin/image/head/" + user.getKuLid().trim() + "_"
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
		xypttitleService.update(user);
		initUser();
	}
}
