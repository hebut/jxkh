package org.iti.jxkh.personal;

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

import org.iti.gh.entity.GhPxqk;
import org.iti.gh.entity.GhZcqc;
import org.iti.gh.service.PxqkService;
import org.iti.gh.service.ZcqkService;
import org.iti.jxkh.base.TitleSelectListbox;
import org.iti.jxkh.business.fruit.AddFruitWin.FilesRenderer1;
import org.iti.jxkh.business.meeting.UpfileWindow;
import org.iti.jxkh.dutyChange.AddDutyChangeWindow;
import org.iti.jxkh.entity.JXKH_PerTrans;
import org.iti.jxkh.entity.Jxkh_DataFile;
import org.iti.jxkh.entity.Jxkh_UserDetail;
import org.iti.jxkh.service.DutyChangeService;
import org.iti.jxkh.service.UserDetailService;
import org.iti.xypt.entity.Title;
import org.iti.xypt.service.XyptTitleService;
import org.iti.xypt.ui.base.BaseWindow;
import org.iti.xypt.ui.base.TitleSelectHbox;
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
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.iti.common.util.ConvertUtil;
import com.uniwin.asm.personal.ui.data.EditTitle;
import com.uniwin.asm.personal.ui.data.EditTrain;
import com.uniwin.asm.personal.ui.data.TeacherTitle;
import com.uniwin.asm.personal.ui.data.TeacherTrain;
import com.uniwin.common.util.DateUtil;
import com.uniwin.common.util.IPUtil;
import com.uniwin.framework.common.fileuploadex.FileuploadEx;
import com.uniwin.framework.common.fileuploadex.ImageUploadPackage;
import com.uniwin.framework.common.fileuploadex.SetFileUploadError;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

public class PersonBasicWindow extends BaseWindow {
	// �û����ݷ��ʽӿ�
	private UserService userService;
	// �û���������
	/**
	 * ������Ϣ
	 */
	private Button print;
	// �û���¼��
	// private Label kuLid;
	// ��ʵ������������,Ա����ţ�����״̬�����֤�ţ�����
	private Textbox kuName, kuUsedname, thid, healthstate, kuIdentity, nativeplace;
	// ͷ��
	private org.zkoss.zul.Image img;
	// ��������
	private Datebox kuBirthday;
	// �Ա�
	private Radiogroup kuSex;
	// ���塢����״̬
	Listbox kuNation, marrystate;
	// ͷ����ذ�ť
	// private Button upload;
	private Button delete;
	// ��������
	private Label deptSelect;
	// ְ�ơ�ְ�Ƽ���
	TitleSelectHbox selectTitle;
	// ����ˮƽ ������ò
	private Listbox language, kuPolitical;
	// �뵳ʱ��
	private Datebox partytime;
	// ���˼��
	private Textbox uinfo;
	// ������
	private Listbox bangType;
	// �󶨵�ַ
	private Textbox uBandIp;
	// �Զ���¼
	private Checkbox kuAutoenter;
	// �󶨵�ַ��
	private Row Band;
	private Label partyLabel;
	// ���� ����
	// private Toolbarbutton submit1, reset1;

	/**
	 * ��������
	 */
	/** ��һѧ�� */
	// ѧ�� ѧλ ѧ��
	private Listbox fedubackgr, facdegree, fduyear;// ??????????????????????
	// ��ҵԺУ ����רҵ ѧ��֤����
	private Textbox fkuSchool, fhighmajor, fcetificateno;
	// ��ҵʱ��
	private Datebox fgradutime, firstInTime;
	/** ���ѧ�� */
	// ѧ�� ѧλ ѧ��
	private Listbox hedubackgr, hacdegree, heduyear;// ???????????????????
	// ��ҵԺУ ����רҵ ѧ��֤����
	private Textbox kuSchool, highmajor, hcetificateno;
	private Listbox firstFileListbox, hightFileListbox;

	private List<String[]> firstFileList = new ArrayList<String[]>();
	private List<String[]> hightFileList = new ArrayList<String[]>();

	// ��ҵʱ��
	private Datebox highgradutime, hightInTime;
	// ���� ����
	// private Toolbarbutton submit2, reset2;
	/** �μ���ѵ��� */
	// �����ѵ��Ϣ
	private Toolbarbutton addPeiXun;
	// ��ѵ�б�
	private Listbox show;

	/**
	 * ������Ϣ
	 */
	// �μӹ���ʱ�� ��Ժ����ʱ��
	private Datebox starworktime, entertime;
	// ��λ���� ְ�� ְ�񼶱� ��Ƹרҵ������λ �ȼ� �ù����� ְ����� ְ������ ְ��״̬//????????????????????
	private Listbox teaqualiry, workproper, worktype;// ,staffstate;
														// //staffproperty,
	private Textbox post, rank, nowPost;// ,nowRank;
	// ��ְʱ��
	private Label leave, reason;
	private Datebox entiretime;
	// ��ְԭ��
	private Textbox entirerea;
//	private Textbox emplquali;
	/** Ƹ����Ϣ */
	// Ƹ���ʸ� Ƹ�ü��� Ƹ�ø�λ Ƹ��״̬
	private Textbox emplrank, emplstate;// emplposition,
//	private Datebox getTime;
	private Listbox empFileListbox;
	private List<String[]> empFileList = new ArrayList<String[]>();
	// Ƹ��ʱ�� Ƹ�ý�ֹʱ�� ��Ƹʱ��
	private Datebox empltime, endtime, dismisstime;
	// ��Ƹ�ĺ� ��Ƹԭ��
	private Textbox dismissno, dismissreason;
	/** ��λ������Ϣ */

	// ��λ������Ϣ
	private Listbox history;
	// ��ҳ
	private Paging zxPaging1;
	// ���� ����
	private Toolbarbutton submit3, reset3;
	/**
	 * ����ְ����Ϣ
	 */
	// ���ְ����Ϣ
//	private Toolbarbutton addtitle;
	// ְ����Ϣ��
	private Listbox titleList;
	/**
	 * ��ϵ��ʽ
	 */
	// ��ͥסַ����ͥ�绰�������绰���ֻ����������䡢MSN��QQ��������ҳ�����桢������ϵ��ʽ
	Textbox address, kuhomePhone, kuworkPhone, kuPhone, kuEmail, msn, qq, homepage, chuanzhen, others;
	// ���� ����
	// private Toolbarbutton submit4,reset4;

	/**
	 * �õ���ʵ��
	 */
	WkTUser user;
	Jxkh_UserDetail detail = new Jxkh_UserDetail();
	/**
	 * �õ���service
	 */
	XyptTitleService xypttitleService;
	UserDetailService userDetailService;
	PxqkService pxqkService;
	ZcqkService zcqkService;
	DutyChangeService dutyChangeService;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");

		List<Jxkh_UserDetail> detailList = userDetailService.findDetailByKuid(user.getKuId());
		if (detailList.size() != 0) {
			detail = detailList.get(0);
		}

		firstFileListbox.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				final String[] s = (String[]) arg1;
				if (s != null) {
					Listcell c0 = new Listcell(Integer.valueOf(arg0.getIndex() + 1).toString());
					Listcell c1 = new Listcell(s[1]);
					Listcell c2 = new Listcell();
					Toolbarbutton tb = new Toolbarbutton();
					tb.setImage("/css/default/images/button/del.gif");
					tb.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event arg0) throws Exception {
							firstFileList.remove(s);
							firstFileListbox.setModel(new ListModelList(firstFileList));
						}
					});
					tb.setParent(c2);
					arg0.setValue(s);
					arg0.appendChild(c0);
					arg0.appendChild(c1);
					arg0.appendChild(c2);
				}
			}
		});
		hightFileListbox.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				final String[] s = (String[]) arg1;
				if (s != null) {
					Listcell c0 = new Listcell(Integer.valueOf(arg0.getIndex() + 1).toString());
					Listcell c1 = new Listcell(s[1]);
					Listcell c2 = new Listcell();
					Toolbarbutton tb = new Toolbarbutton();
					tb.setImage("/css/default/images/button/del.gif");
					tb.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event arg0) throws Exception {
							hightFileList.remove(s);
							hightFileListbox.setModel(new ListModelList(hightFileList));
						}
					});
					arg0.setValue(s);
					arg0.appendChild(c0);
					arg0.appendChild(c1);
					arg0.appendChild(c2);
				}
			}
		});
		empFileListbox.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				final String[] s = (String[]) arg1;
				if (s != null) {
					Listcell c0 = new Listcell(Integer.valueOf(arg0.getIndex() + 1).toString());
					Listcell c1 = new Listcell(s[1]);
					Listcell c2 = new Listcell();
					Toolbarbutton tb = new Toolbarbutton();
					tb.setImage("/css/default/images/button/del.gif");
					tb.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event arg0) throws Exception {
							empFileList.remove(s);
							empFileListbox.setModel(new ListModelList(empFileList));
						}
					});
					tb.setParent(c2);
					arg0.setValue(s);
					arg0.appendChild(c0);
					arg0.appendChild(c1);
					arg0.appendChild(c2);
				}				
			}
		});

		bangType.addEventListener(Events.ON_SELECT, new org.zkoss.zk.ui.event.EventListener() {
			public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
				bangTypeHandle();
			}
		});
		initUser(); // ���ó�ʼ�����ں���
		initMenu();
		initTitle();
		initDutyChange();
		print.setHref("/print.action?n=user&id=" + user.getKuId());
	}

	public void onClick$upload() throws InterruptedException, IOException {
		Integer maxSize = 1024;
		this.getDesktop().getWebApp().getConfiguration().setMaxUploadSize(maxSize.intValue());
		String fileTooLarge = "�ϴ���ͼƬ�ļ�����,�ļ���СӦС��1MB";
		String confPath = Sessions.getCurrent().getWebApp().getRealPath("/") + "/WEB-INF/classes/conf.properties";
		SetFileUploadError.SetErrorToConf(fileTooLarge, confPath, "fileTooLarge");
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
		String kuPath = "/admin/image/head/" + thid.getValue().trim() + "_" + r.nextInt() + Type;// ".jpg";
		user.setKuPath(kuPath.trim());
		xypttitleService.update(user);
		ImageIO.write(pic, Type.substring(1, Type.length()), new File(this.getDesktop().getWebApp().getRealPath(kuPath)));
		File file = new File(Path);
		if (file.exists())
			file.delete();
		initUser();
	}

	public void onClick$delete() {
		String path = this.getDesktop().getWebApp().getRealPath(user.getKuPath());
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
		if (user.getStaffId() != null) {
			thid.setValue(user.getStaffId());
			thid.setReadonly(true);
		}
		if (user.getKuIntro() != null) {
			uinfo.setValue(user.getKuIntro());
		}
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
		/**
		 * ��Listbox�ĳ�ʼ��
		 */
		// ������ò
		final String[] political = { "-��ѡ��-", "�й���������Ա", "�й�������Ԥ����Ա", "�й�����������������Ա", "�й����񵳸���ίԱ���Ա", "�й�����ͬ����Ա", "�й������������Ա", "�й������ٽ����Ա", "�й�ũ����������Ա", "�й��¹�����Ա", "����ѧ����Ա", "̨����������ͬ����Ա", "�޵���������ʿ", "Ⱥ��" };
		for (int j = 0; j < political.length; j++) {
			pol.add(political[j]);
		}
		kuPolitical.setModel(new ListModelList(pol));
		kuPolitical.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				if (kuPolitical.getSelectedIndex() == political.length - 1) {
					partyLabel.setVisible(false);
					partytime.setVisible(false);
				} else {
					partyLabel.setVisible(true);
					partytime.setVisible(true);
				}
			}
		});
		// ����ˮƽ
		String[] languagelist = { "-��ѡ��-", "һ��", "����", "��ͨ" };
		initListbox(languagelist, language);
		// ְ��
		if (user.getTitle() == null || "".equals(user.getTitle())) {
			List titleLsit = xypttitleService.findBytid("011");
			Title t = (Title) titleLsit.get(0);
			selectTitle.setTitle(t);
		} else {
			/**
			 * �����������������������������������������������������������������������
			 */
			List tlist = xypttitleService.findBytid(user.getTitle().trim());
			if (tlist.size() != 0) {
				Title titleEntity = (Title) tlist.get(0);
				selectTitle.setTitle(titleEntity);// ???????????????
			}

		}
		// ftitle.initFListbox();
		// ftitle.addEventListener(Events.ON_SELECT, new EventListener() {
		// public void onEvent(Event arg0) throws Exception {
		// //ְ�Ƽ���
		// Title title = (Title)ftitle.getSelectedItem().getValue();
		// titleRank.initSListbox(title.getPtiId());
		// }
		// });

		// ѧ��
		String[] Educational = { "-��ѡ��-", "��ʿ��ҵ", "��ʿ��ҵ", "��ʿ��ҵ", "˫˶ʿ", "˶ʿ��ҵ", "˶ʿ��ҵ", "˶ʿ��ҵ", "�൱˶ʿ��ҵ", "�о������ҵ", "�о������ҵ", "�о�������ҵ", "˫����", "���Ʊ�ҵ", "���ƽ�ҵ", "������ҵ", "�൱���Ʊ�ҵ", "˫��ר", "��ר��ҵ", "��ר��ҵ", "�൱��ר��ҵ", "��ר��ҵ", "��ר��ҵ" };
		for (int j = 0; j < Educational.length; j++) {
			edu.add(Educational[j]);
		}
		// kuEducational.setModel(new ListModelList(edu));
		fedubackgr.setModel(new ListModelList(edu));
		hedubackgr.setModel(new ListModelList(edu));
		// ѧλ
		String[] xuewei = { "-��ѡ��-", "������ʿ", "��ʿ", "˶ʿ", "˫˶ʿ", "ѧʿ", "˫ѧʿ", "��" };
		for (int j = 0; j < xuewei.length; j++) {
			xw.add(xuewei[j]);
		}
		// kuXuewei.setModel(new ListModelList(xw));
		facdegree.setModel(new ListModelList(xw));
		hacdegree.setModel(new ListModelList(xw));
		// ����
		String[] nation = { "-��ѡ��-", "����", "�ɹ���", "����", "����", "ά���", "����", "����", "׳��", "������", "������", "����", "����", "����", "����", "������", "������", "��������", "����", "����", "������", "����", "���", "��ɽ��", "������", "ˮ��", "������", "������", "������", "�¶�������", "����", "���Ӷ���", "������", "Ǽ��",
				"������", "������", "ë����", "������", "������", "������", "��������", "������", "ŭ��", "���α����", "����˹��", "���¿���", "�°���", "������", "ԣ����", "����", "��������", "������", "���״���", "������", "�Ű���", "��ŵ��", "�����" };
		for (int i = 0; i < nation.length; i++) {
			nat.add(nation[i]);
		}
		kuNation.setModel(new ListModelList(nat));
		// ����״̬
		String[] marry = { "-��ѡ��-", "δ��", "�ѻ�", "����", "�ٻ�" };
		for (int i = 0; i < marry.length; i++) {
			mar.add(marry[i]);
		}
		marrystate.setModel(new ListModelList(mar));
		// ��λ����
		String[] teacherqualify = { "-��ѡ��-", "��ְ�ڸ�", "��ְ���ڸ�", "���ݷ�Ƹ", "��Ƹ", "��ְ", "����" };
		initListbox(teacherqualify, teaqualiry);// ///???????????????????????????????????????????????
		/*
		 * for (int i = 0; i < teacherqualify.length; i++) {
		 * tea.add(teacherqualify[i]); } teaqualiry.setModel(new
		 * ListModelList(tea));
		 */

		// ְλ

		// ְλ����

		// ��Ƹרҵ������λ

		// �ȼ�

		// �ù�����
		String[] workpro = { "-��ѡ��-", "��ͬ��", "Ƹ����", "��ʱ��", "��Ƹ" };
		for (int i = 0; i < workpro.length; i++) {
			worp.add(workpro[i]);
		}
		workproper.setModel(new ListModelList(worp));
		// ְ�����
		String[] worktyp = { "-��ѡ��-", "������Ա", "רҵ������Ա", "������Ա" };
		for (int i = 0; i < worktyp.length; i++) {
			wort.add(worktyp[i]);
		}
		worktype.setModel(new ListModelList(wort));
		// ְ������
		/*
		 * String[] stafpro = { "-��ѡ��-", "����", "˽��", "����" }; for (int i = 0; i <
		 * stafpro.length; i++) { stafp.add(stafpro[i]); }
		 * staffproperty.setModel(new ListModelList(stafp));
		 */
		// ְ��״̬
		/*
		 * String[] stafstate = { "-��ѡ��-", "ʵϰ", "��ְ", "��ְ", "����" }; for (int i
		 * = 0; i < stafstate.length; i++) { stafs.add(stafstate[i]); }
		 * staffstate.setModel(new ListModelList(stafs));
		 */
		/*
		 * staffstate.addEventListener(Events.ON_SELECT, new
		 * org.zkoss.zk.ui.event.EventListener() { public void
		 * onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception { if
		 * (staffstate.getSelectedIndex() == 3) { leave.setVisible(true);
		 * reason.setVisible(true); entiretime.setVisible(true);
		 * entirerea.setVisible(true); } else { leave.setVisible(false);
		 * reason.setVisible(false); entiretime.setVisible(false);
		 * entirerea.setVisible(false); } } });
		 */

		// ѧ��
		String[] year = { "-��ѡ��-", "����", "�����", "����", "����", "����" };
		for (int i = 0; i < year.length; i++) {
			fyear.add(year[i]);
			hyear.add(year[i]);
		}
		fduyear.setModel(new ListModelList(fyear));
		heduyear.setModel(new ListModelList(hyear));

		/**
		 * ��Textbox�ĳ�ʼ��
		 */
		// kuLid.setValue(user.getKuLid());
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
		if (user.getKuPartytime() != null && user.getKuPartytime().length() > 0) {
			partytime.setValue(ConvertUtil.convertDate(DateUtil.getDateString(user.getKuPartytime())));
		}
		if (user.getKuHealth() != null) {
			healthstate.setValue(user.getKuHealth());
		}
		if (user.getKuNativeplace() != null) {
			nativeplace.setValue(user.getKuNativeplace());
		}
		/*
		 * if (teacher.getThPosition() != null) {
		 * zhiwu.setValue(teacher.getThPosition()); }
		 */

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
		kuIdentity.setValue(user.getKuIdentity());
		if (user.getKuPolitical() == null || user.getKuPolitical() == "") {
			kuPolitical.setSelectedIndex(0);
		} else {
			kuPolitical.setSelectedIndex(Integer.valueOf(user.getKuPolitical().trim()));
		}
		/*
		 * if (user.getKuEducational() == null || user.getKuEducational() == "")
		 * { kuEducational.setSelectedIndex(0); } else {
		 * kuEducational.setSelectedIndex
		 * (Integer.valueOf(user.getKuEducational().trim())); } if
		 * (user.getKuXuewei() == null || user.getKuXuewei() == "") {
		 * kuXuewei.setSelectedIndex(0); } else {
		 * kuXuewei.setSelectedIndex(Integer
		 * .valueOf(user.getKuXuewei().trim())); }
		 */
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
		/*
		 * if (user.getKuSpoken() == null || user.getKuSpoken() == "") {
		 * mandarin.setSelectedIndex(0); } else {
		 * mandarin.setSelectedIndex(Integer.valueOf(user.getKuSpoken())); }
		 */
		/*
		 * if (user.getKuComputer() == null || user.getKuComputer() == "") {
		 * computer.setSelectedIndex(0); } else {
		 * computer.setSelectedIndex(Integer.valueOf(user.getKuComputer())); }
		 */
		if (detail != null) {
			if (detail.getfMajor() != null) {
				fhighmajor.setValue(detail.getfMajor());
			}
			if (detail.gethMajor() != null) {
				highmajor.setValue(detail.gethMajor());
			}
			if (detail.getJobQuality() == null || detail.getJobQuality() == "") {
				teaqualiry.setSelectedIndex(0);
			} else {
				teaqualiry.setSelectedIndex(Integer.valueOf(detail.getJobQuality()));
			}
			if (detail.getWorkQulity() == null || detail.getWorkQulity() == "") {
				workproper.setSelectedIndex(0);
			} else {
				workproper.setSelectedIndex(Integer.valueOf(detail.getWorkQulity()));
			}
			if (detail.getWorkType() == null || detail.getWorkType() == "") {
				worktype.setSelectedIndex(0);
			} else {
				worktype.setSelectedIndex(Integer.valueOf(detail.getWorkType()));
			}
			/*
			 * if (detail.getStaffState() == null || detail.getStaffState() ==
			 * "") { staffstate.setSelectedIndex(0); leave.setVisible(false);
			 * reason.setVisible(false); entiretime.setVisible(false);
			 * entirerea.setVisible(false); } else {
			 * staffstate.setSelectedIndex(
			 * Integer.valueOf(detail.getStaffState())); if
			 * (Integer.valueOf(detail.getStaffState()) == 3) {
			 * leave.setVisible(true); reason.setVisible(true);
			 * entiretime.setVisible(true); entirerea.setVisible(true); } else {
			 * leave.setVisible(false); reason.setVisible(false);
			 * entiretime.setVisible(false); entirerea.setVisible(false); } }
			 */
			/*
			 * if (detail.getStaffQuality() == null || detail.getStaffQuality()
			 * == "") { staffproperty.setSelectedIndex(0); } else {
			 * staffproperty
			 * .setSelectedIndex(Integer.valueOf(detail.getStaffQuality())); }
			 */
			// ��һѧ�������ѧ��
			if (detail.getfDegree() == null || detail.getfDegree() == "") {
				fedubackgr.setSelectedIndex(0);
			} else {
				fedubackgr.setSelectedIndex(Integer.valueOf(detail.getfDegree()));
			}
			if (detail.gethDegree() == null || detail.gethDegree() == "") {
				hedubackgr.setSelectedIndex(0);
			} else {
				hedubackgr.setSelectedIndex(Integer.valueOf(detail.gethDegree()));
			}
			// ��һѧλ�����ѧλ
			if (detail.getfQulification() == null || detail.getfQulification() == "") {
				facdegree.setSelectedIndex(0);
			} else {
				facdegree.setSelectedIndex(Integer.valueOf(detail.getfQulification()));
			}
			if (detail.gethQulification() == null || detail.gethQulification() == "") {
				hacdegree.setSelectedIndex(0);
			} else {
				hacdegree.setSelectedIndex(Integer.valueOf(detail.gethQulification()));
			}
			if (detail.getfSchoolYear() == null || detail.getfSchoolYear() == "") {
				fduyear.setSelectedIndex(0);
			} else {
				fduyear.setSelectedIndex(Integer.valueOf(detail.getfSchoolYear()));
			}
			if (detail.gethSchoolYear() == null || detail.gethSchoolYear() == "") {
				heduyear.setSelectedIndex(0);
			} else {
				heduyear.setSelectedIndex(Integer.valueOf(detail.gethSchoolYear()));
			}
			if (detail.getEntireReason() != null) {
				entirerea.setValue(detail.getEntireReason());
			}
			if (detail.getEnterTime() != null && detail.getEnterTime().length() > 0) {
				entiretime.setValue(ConvertUtil.convertDate(DateUtil.getDateString(detail.getEnterTime())));
			}
			if (detail.getfGraduTime() != null) {
				fgradutime.setValue(ConvertUtil.convertDate(detail.getfGraduTime()));
			}
			if (detail.getfInTime() != null) {
				firstInTime.setValue(ConvertUtil.convertDate(detail.getfInTime()));
			}
			if (detail.gethGraduTime() != null) {
				highgradutime.setValue(ConvertUtil.convertDate(detail.gethGraduTime()));
			}
			if (detail.gethInTime() != null) {
				hightInTime.setValue(ConvertUtil.convertDate(detail.gethInTime()));
			}
			/**
			 * ��ʼ��ѧ��ѧλ�ĸ���
			 */
			firstFileList.clear();
			hightFileList.clear();
			List<Jxkh_DataFile> firstList = userDetailService.getFileByUser(detail.getKuId(), Jxkh_DataFile.FIRST);
			if (firstList != null && firstList.size() > 0) {
				for (Jxkh_DataFile df : firstList) {
					if (df != null) {
						String[] array = new String[4];
						array[0] = df.getFilePath();
						array[1] = df.getFileName();
						array[2] = df.getUpTime();
						array[3] = Jxkh_DataFile.FIRST.toString();
						firstFileList.add(array);
					}
				}
			}
			firstFileListbox.setModel(new ListModelList(firstFileList));
			List<Jxkh_DataFile> hightList = userDetailService.getFileByUser(detail.getKuId(), Jxkh_DataFile.HIGHT);
			if (hightList != null && hightList.size() > 0) {
				for (Jxkh_DataFile df : hightList) {
					if (df != null) {
						String[] array = new String[4];
						array[0] = df.getFilePath();
						array[1] = df.getFileName();
						array[2] = df.getUpTime();
						array[3] = Jxkh_DataFile.FIRST.toString();
						hightFileList.add(array);
					}
				}
			}
			hightFileListbox.setModel(new ListModelList(hightFileList));

			if (detail.getfSchool() != null || detail.getfQuliNumb() != null || detail.gethSchool() != null || detail.gethQuliNumb() != null) {
				fkuSchool.setValue(detail.getfSchool());
				fcetificateno.setValue(detail.getfQuliNumb());
				kuSchool.setValue(detail.gethSchool());
				hcetificateno.setValue(detail.gethQuliNumb());
			}
			if (detail.getStartTime() != null && detail.getStartTime().length() > 0) {
				starworktime.setValue(ConvertUtil.convertDate(DateUtil.getDateString(detail.getStartTime())));
			}
			if (detail.getEnterTime() != null && detail.getEnterTime().length() > 0) {
				entertime.setValue(ConvertUtil.convertDate(DateUtil.getDateString(detail.getEnterTime())));
			}
			/** ������Ϣ */
			if (detail.getPost() != null && !"".equals(detail.getPost())) {
				post.setValue(detail.getPost());
			}
			if (detail.getPostRank() != null && !"".equals(detail.getPostRank())) {
				rank.setValue(detail.getPostRank());
			}
			
			/*
			 * if(detail.getNowPtRank() != null &&
			 * !"".equals(detail.getNowPtRank())) {
			 * nowRank.setValue(detail.getNowPtRank()); }
			 */

		}

		/**
		 * ��Listbox�ĳ�ʼ��
		 */
		if (user.getLanguage() == null || "".equals(user.getLanguage())) {
			language.setSelectedIndex(0);
		} else {
			language.setSelectedIndex(Integer.valueOf(user.getLanguage().trim()));
		}

		/*
		 * if(teacher.getThFirsub()!=null){ List
		 * mlst=majorService.findByZyid(teacher.getThFirsub());
		 * if(mlst!=null&&mlst.size()!=0){ GhZy z=(GhZy) mlst.get(0);
		 * System.out.println("ss....."+z.getZyId()); fhighmajor.setMajor(z); }
		 * 
		 * }
		 */

		if (user.getKuHomeaddress() != null || user.getKuHometel() != null || user.getKuWorktel() != null || user.getKuMsn() != null || user.getKuQq() != null || user.getKuHomepage() != null || user.getKuFax() != null || user.getKuOthercontact() != null) {
			address.setValue(user.getKuHomeaddress());
			kuhomePhone.setValue(user.getKuHometel());
			kuworkPhone.setValue(user.getKuWorktel());
			msn.setValue(user.getKuMsn());
			qq.setValue(user.getKuQq());
			homepage.setValue(user.getKuHomepage());
			chuanzhen.setValue(user.getKuFax());
			others.setValue(user.getKuOthercontact());
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
		/*
		 * delete.setDisabled(false); String path =
		 * this.getDesktop().getWebApp().getRealPath("/admin/image/head/" +
		 * thid.getValue().trim() + ".jpg"); String srcPath="/admin/image/head/"
		 * + thid.getValue().trim() + ".jpg"; File file = new File(path); if
		 * (file.exists()) { img.setSrc(srcPath.replace("\\", "/")); } else {
		 * delete.setDisabled(true);
		 * img.setSrc("/admin/image/head/default.jpg"); }
		 * ///System.out.println(path+"11111");
		 * //System.out.println(srcPath+"22222222");
		 */

		/* selectTitle.setTitle(teacher.getTitle()); */
	}

	/**
	 * ��ʼ��listbox�����ݵķ���
	 * 
	 * @param s
	 * @param listbox
	 */
	public void initListbox(String[] s, Listbox listbox) {
		List list = new ArrayList();
		for (int j = 0; j < s.length; j++) {
			list.add(s[j]);
		}
		listbox.setModel(new ListModelList(list));
	}

	/**
	 * <li>�����������û���Ϣ���¹��ܡ� void
	 * 
	 * @author bobo
	 * @throws InterruptedException
	 * @2010-3-1
	 */
	public void onClick$submit1() throws InterruptedException {
		if ("".equals(kuName.getValue())) {
			Messagebox.show("�û�������Ϊ�գ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			kuName.focus();
			return;
		}
		if ("".equals(thid.getValue())) {
			Messagebox.show("Ա������Ϊ�գ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			thid.focus();
			return;
		}
		if (kuIdentity.getValue().equals("")) {
			Messagebox.show("���֤�Ų���Ϊ�գ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			kuIdentity.focus();
			return;
		}
		user.setKuName(kuName.getText());
		user.setStaffId(thid.getValue().trim());
		user.setKuUsedname(kuUsedname.getText());
		user.setLanguage(String.valueOf(language.getSelectedIndex()));
		user.setKuPolitical(String.valueOf(kuPolitical.getSelectedIndex()));
		user.setKuIdentity(kuIdentity.getText());
		user.setKuHealth(healthstate.getText());
		user.setKuNativeplace(nativeplace.getText());
		user.setKuNation(String.valueOf(kuNation.getSelectedIndex()));
		/*
		 * user.setKuEducational(String.valueOf(kuEducational.getSelectedIndex())
		 * ); user.setKuXuewei(String.valueOf(kuXuewei.getSelectedIndex()));
		 */
		user.setKuMarstatus(String.valueOf(marrystate.getSelectedIndex()));
		/*
		 * user.setKuSpoken(String.valueOf(mandarin.getSelectedIndex()));
		 * user.setKuComputer(String.valueOf(computer.getSelectedIndex()));
		 */
		user.setKuSchool(kuSchool.getText());
		if (kuBirthday.getValue() != null) {
			user.setKuBirthday(DateUtil.getDateString(kuBirthday.getValue()));
		}
		if (kuSex.getSelectedIndex() == 0) {
			user.setKuSex("1"); // 1�����С���2����Ů��
		} else {
			user.setKuSex("2");
		}
		user.setKuNativeplace(nativeplace.getText());

		// ְ�Ƽ�ְ�Ƽ���
		user.setTitle(selectTitle.getSelectTitle().getTiId());
		// user.setTitle(ftitle.getSelectTitle().getTiId());
		// user.setTitleRank(titleRank.getSelectTitle().getTiId());

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
		if (healthstate.getValue() != null) {
			user.setKuHealth(healthstate.getValue());
		}
		if (partytime.getValue() != null) {
			user.setKuPartytime(DateUtil.getDateString(partytime.getValue()));
		}
		/*
		 * Teacher teacher = teacherService.findBykuid(user.getKuId()); if
		 * (shuodao.getSelectedIndex() == 0 && bodao.getSelectedIndex() == 0) {
		 * teacher.setThAdvisor(Teacher.ADVISOR_BOTH); } else if
		 * (shuodao.getSelectedIndex() == 0 && bodao.getSelectedIndex() == 1) {
		 * teacher.setThAdvisor(Teacher.ADVISOR_SD); } else if
		 * (bodao.getSelectedIndex() == 0 && shuodao.getSelectedIndex() == 1) {
		 * teacher.setThAdvisor(Teacher.ADVISOR_BD); } else {
		 * teacher.setThAdvisor(Teacher.ADVISOR_NONE); }
		 */

		/*
		 * if (zhiwu.getValue() != null) {
		 * teacher.setThPosition(zhiwu.getValue()); }
		 */

		/*
		 * teacher.setThQualify(String.valueOf(teaqualiry.getSelectedIndex()));
		 * List yjfx = yjfxService.findByKuid(user.getKuId()); if (yjfx != null
		 * && yjfx.size() > 0) { GhYjfx fx = (GhYjfx) yjfx.get(0); if
		 * (search1.getValue() != null || search2.getValue() != null ||
		 * search3.getValue() != null || search4.getValue() != null ||
		 * search5.getValue() != null) { fx.setYjResearch1(search1.getValue());
		 * fx.setYjResearch2(search2.getValue());
		 * fx.setYjResearch3(search3.getValue());
		 * fx.setYjResearch4(search4.getValue());
		 * fx.setYjResearch5(search5.getValue()); } yjfxService.update(fx); }
		 * else { GhYjfx fx = new GhYjfx(); if (search1.getValue() != null ||
		 * search2.getValue() != null || search3.getValue() != null ||
		 * search4.getValue() != null || search5.getValue() != null) {
		 * fx.setKuId(user.getKuId()); fx.setYjResearch1(search1.getValue());
		 * fx.setYjResearch2(search2.getValue());
		 * fx.setYjResearch3(search3.getValue());
		 * fx.setYjResearch4(search4.getValue());
		 * fx.setYjResearch5(search5.getValue()); } yjfxService.save(fx); }
		 */
		/*
		 * //�ж��о�����������洢 String[] a= search.getValue().split(","); if(yjfx ==
		 * null || yjfx.size() == 0){ for(int i=0;i<a.length;i++){ String[] a1=
		 * a[i].split("��"); if(a1.length !=0){ for(int j = 0;j < a1.length;j++){
		 * GhYjfx yj=new GhYjfx(); yj.setYjResearch(a1[j]);
		 * yj.setKuId(user.getKuId()); yjfxService.save(yj); } }else { GhYjfx
		 * yj=new GhYjfx(); yj.setYjResearch(a[i]); yj.setKuId(user.getKuId());
		 * yjfxService.save(yj); } } }else if(yjfx != null && yjfx.size() != 0){
		 * for(int j = 0;j < yjfx.size(); j++){ GhYjfx yj=(GhYjfx) yjfx.get(j);
		 * yjfxService.delete(yj); } for(int i=0;i<a.length;i++){ String[] a1=
		 * a[i].split("��"); if(a1.length !=0){ for(int j = 0;j < a1.length;j++){
		 * GhYjfx yj=new GhYjfx(); yj.setYjResearch(a1[j]);
		 * yj.setKuId(user.getKuId()); yjfxService.save(yj); } }else{ GhYjfx
		 * yj=new GhYjfx(); yj.setYjResearch(a[i]); yj.setKuId(user.getKuId());
		 * yjfxService.save(yj); } } }
		 */
		/*
		 * teacher.setThTitle(selectTitle.getSelectTitle().getTiId());
		 * teacherService.update(teacher);
		 */
		// teacherService.save(teacher);
		userService.update(user);
		Sessions.getCurrent().setAttribute("user", user);
		Messagebox.show("����ɹ���", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
	}

	public void onClick$submit3() throws InterruptedException {
		if (user == null) {
			Messagebox.show("���ȱ����û�������Ϣ��", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		if (starworktime.getValue() != null) {
			detail.setStartTime(DateUtil.getDateString(starworktime.getValue()));
		}
		if (entertime.getValue() != null) {
			detail.setEnterTime(DateUtil.getDateString(entertime.getValue()));
		}
		detail.setJobQuality(String.valueOf(teaqualiry.getSelectedIndex()));
		detail.setWorkQulity(String.valueOf(workproper.getSelectedIndex()));
		detail.setWorkType(String.valueOf(worktype.getSelectedIndex()));
		/* detail.setStaffState(String.valueOf(staffstate.getSelectedIndex())); */
		/*
		 * detail.setStaffQuality(String.valueOf(staffproperty.getSelectedIndex()
		 * ));
		 */
		if (entirerea.getValue() != null) {
			detail.setEntireReason(entirerea.getValue());
		}
//		if (emplquali.getValue() != null) {
//			detail.setEmpQualy(emplquali.getValue());
//		}
//		if (getTime.getValue() != null) {
//			detail.setGetTime(DateUtil.getDateString(getTime.getValue()));
//		}
		if (emplrank.getValue() != null) {
			detail.setEmpRank(emplrank.getValue());
		}
		/*
		 * if (emplposition.getValue() != null) {
		 * detail.setEmpJob(emplposition.getValue()); }
		 */
		if (emplstate.getValue() != null) {
			detail.setEmpState(emplstate.getValue());
		}
		if (dismissno.getValue() != null) {
			detail.setDismisNum(dismissno.getValue());
		}
		if (dismissreason.getValue() != null) {
			detail.setDismissReasn(dismissreason.getValue());
		}
		if (entiretime.getValue() != null) {
			detail.setEnterTime(DateUtil.getDateString(entiretime.getValue()));
		}
		if (empltime.getValue() != null) {
			detail.setEmpTime(DateUtil.getDateString(empltime.getValue()));
		}
		if (endtime.getValue() != null) {
			detail.setEmpEndTime(DateUtil.getDateString(endtime.getValue()));
		}
		if (dismisstime.getValue() != null) {
			detail.setDismisTime(DateUtil.getDateString(dismisstime.getValue()));
		}
		if (post.getValue() != null) {
			detail.setPost(post.getValue());
		}
		if (rank.getValue() != null) {
			detail.setPostRank(rank.getValue());
		}
		// if (nowPost.getValue() != null) {
		// detail.setNowPost(nowPost.getValue());
		// }
		/*
		 * if (nowRank.getValue() != null) {
		 * detail.setNowPtRank(nowRank.getValue()); }
		 */
		if (detail.getKuId() == null) {
			detail.setKuId(user.getKuId());
			userDetailService.save(detail);
		} else {
			userDetailService.update(detail);
		}
		/**
		 * ���渽��
		 */
		//��ɾ��ԭ���ĸ���
		List<Jxkh_DataFile> list = userDetailService.getFileByUser(detail.getKuId(), Jxkh_DataFile.EMP);
		if(list != null && list.size() > 0) {
			for(Jxkh_DataFile f : list) {
				if(f != null)
					userDetailService.delete(f);
			}
		}		
		//�ٱ����µĸ���
		if(empFileList.size() > 0) {
			for(String[] s : empFileList) {
				Jxkh_DataFile f = new Jxkh_DataFile();
				f.setUserId(detail.getKuId());
				f.setFilePath(s[0]);
				f.setFileName(s[1]);
				f.setUpTime(s[2]);
				f.setFileType(Jxkh_DataFile.EMP);
				userDetailService.save(f);
			}
		}
		
		Messagebox.show("����ɹ���", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
	}

	public void onClick$submit4() {
		if (user == null) {
			try {
				Messagebox.show("���ȱ����û�������Ϣ��", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
//		if (emplquali.getValue() != null && !emplquali.getValue().equals("")) {
//			detail.setEmpQualy(emplquali.getValue().trim());
//		}
//		if (getTime.getValue() != null) {
//			detail.setGetTime(ConvertUtil.convertDateString(getTime.getValue()));
//		}
		if (nowPost.getValue() != null && !nowPost.getValue().equals(""))
			detail.setNowPost(nowPost.getValue().trim());
		if (emplrank.getValue() != null && !emplrank.getValue().equals(""))
			detail.setEmpRank(emplrank.getValue().trim());
		if (empltime.getValue() != null)
			detail.setEmpTime(ConvertUtil.convertDateString(empltime.getValue()));
		if (endtime.getValue() != null)
			detail.setEmpEndTime(ConvertUtil.convertDateString(endtime.getValue()));
		if (emplstate.getValue() != null && !emplstate.getValue().equals(""))
			detail.setEmpState(emplstate.getValue().trim());
		if (dismisstime.getValue() != null)
			detail.setDismisTime(ConvertUtil.convertDateString(dismisstime.getValue()));
		if (dismissno.getValue() != null && !dismissno.getValue().equals(""))
			detail.setDismisNum(dismissno.getValue().trim());
		if (dismissreason.getValue() != null && !dismissreason.getValue().equals(""))
			detail.setDismissReasn(dismissreason.getValue().trim());
		if (detail.getUdId() == null) {
			detail.setKuId(user.getKuId());
			userDetailService.save(detail);
		} else {
			userDetailService.update(detail);
		}
	}

	public void onClick$reset4() {
		initTitle();
	}

	public void onClick$submit5() throws InterruptedException {
		if (user == null) {
			Messagebox.show("���ȱ����û�������Ϣ��", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		if (kuPhone.getValue().equals("")) {
			Messagebox.show("�ֻ��Ų���Ϊ�գ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			kuPhone.focus();
			return;
		}
		user.setKuPhone(kuPhone.getValue());
		if (kuEmail.getValue().equals("")) {
			Messagebox.show("�������䲻��Ϊ�գ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			kuEmail.focus();
			return;
		}
		user.setKuEmail(kuEmail.getValue());
		if (address.getValue() != null || kuhomePhone.getValue() != null || kuworkPhone.getValue() != null || msn.getValue() != null || qq.getValue() != null || homepage.getValue() != null || chuanzhen.getValue() != null || others.getValue() != null) {
			user.setKuHomeaddress(address.getValue());
			user.setKuHometel(kuhomePhone.getValue());
			user.setKuWorktel(kuworkPhone.getValue());
			user.setKuMsn(msn.getValue());
			user.setKuQq(qq.getValue());
			user.setKuHomepage(homepage.getValue());
			user.setKuFax(chuanzhen.getValue());
			user.setKuOthercontact(others.getValue());
		}
		userService.update(user);
		Sessions.getCurrent().setAttribute("user", user);
		Messagebox.show("����ɹ���", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
	}

	public void onClick$submit2() throws InterruptedException {
		if (user == null) {
			Messagebox.show("���ȱ����û�������Ϣ��", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		if (fgradutime.getValue() != null) {
			detail.setfGraduTime(ConvertUtil.convertDateString(fgradutime.getValue()));
		}
		if (firstInTime.getValue() != null) {
			detail.setfInTime(ConvertUtil.convertDateString(firstInTime.getValue()));
		}
		if (highgradutime.getValue() != null) {
			detail.sethGraduTime(ConvertUtil.convertDateString(highgradutime.getValue()));
		}
		if (hightInTime.getValue() != null) {
			detail.sethInTime(ConvertUtil.convertDateString(hightInTime.getValue()));
		}
		if (fedubackgr.getSelectedIndex() != 0) {
			detail.setfDegree(String.valueOf(fedubackgr.getSelectedIndex()));
		}
		if (hedubackgr.getSelectedIndex() != 0) {
			detail.sethDegree(String.valueOf(hedubackgr.getSelectedIndex()));
		}
		if (facdegree.getSelectedIndex() != 0) {
			detail.setfQulification(String.valueOf(facdegree.getSelectedIndex()));
		}
		if (hacdegree.getSelectedIndex() != 0) {
			detail.sethQulification(String.valueOf(hacdegree.getSelectedIndex()));
		}
		detail.setfSchoolYear(String.valueOf(fduyear.getSelectedIndex()));
		detail.sethSchoolYear(String.valueOf(heduyear.getSelectedIndex()));
		if (fkuSchool.getValue() != null || fcetificateno.getValue() != null || kuSchool.getValue() != null || hcetificateno.getValue() != null) {
			detail.setfSchool(fkuSchool.getValue());
			detail.setfQuliNumb(fcetificateno.getValue());
			detail.sethSchool(kuSchool.getValue());
			detail.sethQuliNumb(hcetificateno.getValue());
		}
		if (fhighmajor.getValue() != null) {
			detail.setfMajor(fhighmajor.getValue().trim());
		}
		if (highmajor.getValue() != null) {
			detail.sethMajor(highmajor.getValue().trim());
		}

		if (detail.getUdId() == null) {
			detail.setKuId(user.getKuId());
			userDetailService.save(detail);
		} else {
			userDetailService.update(detail);
		}
		/**
		 * ����ѧ��ѧλ����
		 */
		// �ֽ���ǰ�ĸ�����ɾ����
		List<Jxkh_DataFile> firstList = userDetailService.getFileByUser(detail.getKuId(), Jxkh_DataFile.FIRST);
		List<Jxkh_DataFile> hightList = userDetailService.getFileByUser(detail.getKuId(), Jxkh_DataFile.HIGHT);
		if (firstList != null && firstList.size() > 0) {
			for (Jxkh_DataFile f : firstList) {
				if (f != null)
					userDetailService.delete(f);
			}
		}
		if (hightList != null && hightList.size() > 0) {
			for (Jxkh_DataFile f : hightList) {
				if (f != null)
					userDetailService.delete(f);
			}
		}
		// �ٱ����µĸ���
		if (firstFileList.size() > 0) {
			for (String[] s : firstFileList) {
				Jxkh_DataFile df = new Jxkh_DataFile();
				df.setUserId(detail.getKuId());
				df.setFilePath(s[0]);
				df.setFileName(s[1]);
				df.setUpTime(s[2]);
				df.setFileType(Jxkh_DataFile.FIRST);
				userDetailService.save(df);
			}
		}
		if (hightFileList.size() > 0) {
			for (String[] s : hightFileList) {
				Jxkh_DataFile df = new Jxkh_DataFile();
				df.setUserId(detail.getKuId());
				df.setFilePath(s[0]);
				df.setFileName(s[1]);
				df.setUpTime(s[2]);
				df.setFileType(Jxkh_DataFile.HIGHT);
				userDetailService.save(df);
			}
		}
		/*
		 * userService.update(user); Sessions.getCurrent().setAttribute("user",
		 * user);
		 */
		Messagebox.show("����ɹ���", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
	}

	/**
	 * <li>�����������û���Ϣ���ù��ܡ� void
	 * 
	 * @author bobo
	 * @2010-4-13
	 */
	public void onClick$reset1() {
		// kuName.setValue("");
		// thid.setRawValue(null);
		// kuUsedname.setRawValue(null);
		// kuNation.setSelectedIndex(0);
		// healthstate.setRawValue(null);
		// marrystate.setSelectedIndex(0);
		// nativeplace.setRawValue(null);
		// partytime.setRawValue(null);
		// kuBirthday.setRawValue(null);
		// kuSex.setSelectedIndex(0);
		// bangType.setSelectedIndex(0);
		// kuAutoenter.setChecked(false);
		// kuAutoenter.setDisabled(true);
		// uBandIp.setValue("");
		// uinfo.setRawValue("");
		// kuIdentity.setValue("");
		// kuPolitical.setSelectedIndex(0);
		// kuSchool.setValue("");
		/*
		 * search1.setRawValue(null); search2.setRawValue(null);
		 * search3.setRawValue(null); search4.setRawValue(null);
		 * search5.setRawValue(null);
		 */
		initMenu();
		// initTitle();
		initDutyChange();
		initUser();

	}

	public void onClick$reset2() {
		// starworktime.setRawValue(null);
		// entertime.setRawValue(null);
		// teaqualiry.setSelectedIndex(0);
		// workproper.setSelectedIndex(0);
		// worktype.setSelectedIndex(0);
		// /*staffproperty.setSelectedIndex(0);*/
		// /*staffstate.setSelectedIndex(0);*/
		// entiretime.setRawValue(null);
		// entirerea.setRawValue(null);
		// emplquali.setRawValue(null);
		// getTime.setRawValue(null);
		// emplrank.setRawValue(null);
		// /*emplposition.setRawValue(null);*/
		// empltime.setRawValue(null);
		// endtime.setRawValue(null);
		// emplstate.setRawValue(null);
		// dismisstime.setRawValue(null);
		// dismissno.setRawValue(null);
		// dismissreason.setRawValue(null);
		initMenu();
		// initTitle();
		initDutyChange();
		initUser();
	}

	public void onClick$reset3() {
		// address.setRawValue(null);
		// kuhomePhone.setRawValue(null);
		// kuworkPhone.setRawValue(null);
		// kuPhone.setRawValue(null);
		// kuEmail.setRawValue(null);
		// msn.setRawValue(null);
		// qq.setRawValue(null);
		// homepage.setRawValue(null);
		// chuanzhen.setRawValue(null);
		// others.setRawValue(null);
		initMenu();
		// initTitle();
		initDutyChange();
		initUser();
	}

	public void onClick$reset() {
		// fedubackgr.setSelectedIndex(0);
		// facdegree.setSelectedIndex(0);
		// fduyear.setSelectedIndex(0);
		// fkuSchool.setRawValue(null);
		// fgradutime.setRawValue(null);
		// fcetificateno.setRawValue(null);
		// hedubackgr.setSelectedIndex(0);
		// hacdegree.setSelectedIndex(0);
		// heduyear.setSelectedIndex(0);
		// kuSchool.setRawValue(null);
		// highgradutime.setRawValue(null);
		// hcetificateno.setRawValue(null);
		// post.setValue("");
		// nowPost.setValue("");
		// rank.setValue("");
		/* nowRank.setValue(""); */
		initMenu();
		// initTitle();
		initDutyChange();
		initUser();
	}

	public void onClick$addPeiXun() {
		final TeacherTrain w = (TeacherTrain) Executions.createComponents("/admin/personal/basicdata/peixun.zul", null, null);
		w.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
				initMenu();
			}
		});
		try {
			w.doModal();
		} catch (SuspendNotAllowedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void onClick$addtitle() throws SuspendNotAllowedException, InterruptedException {
		final TeacherTitle w = (TeacherTitle) Executions.createComponents("/admin/personal/basicdata/zhicheng.zul", null, null);

		w.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				initTitle();
			}
		});
		w.doModal();
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

	// ��ʼ����ѵ����б�
	public void initMenu() {
		List pxlst;
		pxlst = pxqkService.findByKuid(user.getKuId());
		show.setModel(new ListModelList(pxlst));
		initShow();
	}

	// ��ʼ��ְ���б�
	public void initTitle() {
		if(detail.getNowPost() != null) 
			nowPost.setValue(detail.getNowPost());
		if(detail.getEmpRank() != null)
			emplrank.setValue(detail.getEmpRank());
		if(detail.getEmpTime() != null)
			empltime.setValue(ConvertUtil.getDate(detail.getEmpTime()));
		if(detail.getEmpEndTime() != null)
			endtime.setValue(ConvertUtil.getDate(detail.getEmpEndTime()));
		emplstate.setValue(detail.getEmpState()!=null?detail.getEmpState():"");
		if(detail.getDismisTime() != null)
			dismisstime.setValue(ConvertUtil.getDate(detail.getDismisTime()));
		dismissno.setValue(detail.getDismisNum()!=null?detail.getDismisNum():"");
		dismissreason.setValue(detail.getDismissReasn()!=null?detail.getDismissReasn():"");
		//��ʼ������
		List<Jxkh_DataFile> flist = userDetailService.getFileByUser(detail.getKuId(), Jxkh_DataFile.EMP);
		if(flist != null && flist.size() > 0) {
			for(Jxkh_DataFile f : flist) {
				if(f != null) {
					String[] s = new String[4];
					s[0] = f.getFilePath();
					s[1] = f.getFileName();
					s[2] = f.getUpTime();
					s[3] = Jxkh_DataFile.EMP.toString();
					empFileList.add(s);
				}
			}
		}
		empFileListbox.setModel(new ListModelList(empFileList));
		initShow();
		List tlist = zcqkService.findByKuid(user.getKuId());
		titleList.setModel(new ListModelList(tlist));
		
	}

	// ��ʼ��ְλ������Ϣ
	public void initDutyChange() {
		List hlist = dutyChangeService.findChangeByKuid(user.getKuId());
		history.setModel(new ListModelList(hlist));
		initShow();
	}

	@Override
	public void initShow() {
		/*
		 * show.addEventListener(Events.ON_SELECT, new EventListener() { public
		 * void onEvent(Event arg0) throws Exception { } });
		 */

		show.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhPxqk p = (GhPxqk) arg1;
				Hbox hbox = new Hbox();
				final Toolbarbutton btnedit = new Toolbarbutton();// �༭
				final Toolbarbutton btndele = new Toolbarbutton();// ɾ��
				btnedit.setLabel("�༭");
				btnedit.setStyle("color:blue");
				btnedit.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						edit(p);
					}
				});
				btnedit.setParent(hbox);
				btndele.setLabel("ɾ��");
				btndele.setStyle("color:blue");
				btndele.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						delete(p);
					}
				});
				btndele.setParent(hbox);
				Listcell c1 = new Listcell(arg0.getIndex() + 1 + "");
				Listcell c2 = new Listcell();
				c2.setLabel(p.getPxMainpoint());
				Listcell c3 = new Listcell();
				c3.setLabel(p.getPxStarttime());
				Listcell c4 = new Listcell();
				c4.setLabel(p.getPxEndtime());
				Listcell c5 = new Listcell();
				c5.setLabel(p.getPxContent());
				Listcell c6 = new Listcell();
				c6.setLabel(p.getPxPlace());
				Listcell c7 = new Listcell();
				c7.setLabel(p.getPxProve());
				Listcell c8 = new Listcell();
				hbox.setParent(c8);
				arg0.setValue(arg1);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
				arg0.appendChild(c5);
				arg0.appendChild(c6);
				arg0.appendChild(c7);
				arg0.appendChild(c8);
			}
		});
		/*
		 * titleList.addEventListener(Events.ON_SELECT, new EventListener() {
		 * public void onEvent(Event arg0) throws Exception { } });
		 */
		titleList.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object GhZcqc) throws Exception {
				final GhZcqc z = (GhZcqc) GhZcqc;
				Hbox hbox = new Hbox();
				final Toolbarbutton btnedit = new Toolbarbutton();// �༭
				final Toolbarbutton btndele = new Toolbarbutton();// ɾ��
				btnedit.setLabel("�༭");
				btnedit.setStyle("color:blue");
				btnedit.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						edittitle(z);
					}
				});
				btnedit.setParent(hbox);
				btndele.setLabel("ɾ��");
				btndele.setStyle("color:blue");
				btndele.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						deletetitle(z);
					}
				});
				btndele.setParent(hbox);
				Listcell c1 = new Listcell(arg0.getIndex() + 1 + "");
				Listcell c2 = new Listcell();
				List titlelst = xypttitleService.findBytid(z.getTId());
				if (titlelst != null && titlelst.size() > 0) {
					Title t = (Title) titlelst.get(0);
					c2.setLabel(t.getTiName());
				}
				Listcell c3 = new Listcell(z.getZcTime());
				Listcell c4 = new Listcell(z.getZcPubtime());
				Listcell c5 = new Listcell(z.getZcNum());
				Listcell c6 = new Listcell(z.getZcPubdept());
				Listcell c7 = new Listcell(z.getZcQuasym());
				Listcell c8 = new Listcell(z.getZcIdentdept());
				Listcell c9 = new Listcell();
				hbox.setParent(c9);
				arg0.setValue(GhZcqc);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				// arg0.appendChild(c4);
				arg0.appendChild(c5);
				arg0.appendChild(c6);
				// arg0.appendChild(c7);
				arg0.appendChild(c8);
				arg0.appendChild(c9);
			}
		});
		history.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				final JXKH_PerTrans pt = (JXKH_PerTrans) arg1;
				Toolbarbutton tb = new Toolbarbutton();
				tb.setLabel("�鿴��ϸ");
				tb.setStyle("color:blue");
				tb.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						viewDutyChange(pt);
					}
				});
				Listcell c1 = new Listcell(arg0.getIndex() + 1 + "");
				Listcell c2 = new Listcell(pt.getOldDept().getKdName());
				Listcell c3 = new Listcell(pt.getOldPost());
				Listcell c4 = new Listcell(pt.getNewDept().getKdName());
				Listcell c5 = new Listcell(pt.getNewPost());
				Listcell c6 = new Listcell(pt.getPtDate());
				Listcell c7 = new Listcell();
				c7.appendChild(tb);
				arg0.setValue(pt);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
				arg0.appendChild(c5);
				arg0.appendChild(c6);
				arg0.appendChild(c7);
			}
		});
	}

	private void edit(GhPxqk p) {
		final EditTrain w = (EditTrain) Executions.createComponents("/admin/personal/basicdata/edit_train.zul", null, null);
		w.init(p);
		w.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				initMenu();
			}
		});
		w.doHighlighted();
	}

	private void delete(GhPxqk p) throws InterruptedException {
		if (Messagebox.show("ȷ��ɾ������ѵ��¼��", "ȷ��", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK) {
			pxqkService.delete(p);
		}
		initMenu();
	}

	private void edittitle(GhZcqc z) throws SuspendNotAllowedException, InterruptedException {
		final EditTitle w = (EditTitle) Executions.createComponents("/admin/personal/basicdata/edit_title.zul", null, null);
		w.init(z);
		w.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				initTitle();
			}
		});
		w.doModal();
	}

	private void deletetitle(GhZcqc z) throws InterruptedException {
		if (Messagebox.show("ȷ��ɾ����ְ����Ϣ��", "ȷ��", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK) {
			xypttitleService.delete(z);
		}
		initTitle();
	}

	private void viewDutyChange(JXKH_PerTrans pt) {
		AddDutyChangeWindow acw = (AddDutyChangeWindow) Executions.createComponents("/admin/personal/basicdata/addHistory.zul", null, null);
		acw.initWindow(pt, pt.getOldDept());
		acw.save.setVisible(false);
		acw.reset.setVisible(false);
		try {
			acw.doModal();
		} catch (SuspendNotAllowedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initWindow() {
	}

	public void onClick$firstUpBn() {
		final UpfileWindow w = (UpfileWindow) Executions.createComponents("/admin/personal/businessdata/meeting/upfile.zul", null, null);
		w.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				String filePath = (String) Executions.getCurrent().getAttribute("path");
				String fileName = (String) Executions.getCurrent().getAttribute("title");
				String upTime = (String) Executions.getCurrent().getAttribute("upTime");
				String[] arr = new String[4];
				arr[0] = filePath;
				arr[1] = fileName;
				arr[2] = upTime;
				arr[3] = Jxkh_DataFile.FIRST.toString();
				firstFileList.add(arr);
				firstFileListbox.setModel(new ListModelList(firstFileList));
			}
		});
		try {
			w.doModal();
		} catch (SuspendNotAllowedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void onClick$hightUpBn() {
		final UpfileWindow w = (UpfileWindow) Executions.createComponents("/admin/personal/businessdata/meeting/upfile.zul", null, null);
		w.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				String filePath = (String) Executions.getCurrent().getAttribute("path");
				String fileName = (String) Executions.getCurrent().getAttribute("title");
				String upTime = (String) Executions.getCurrent().getAttribute("upTime");
				String[] arr = new String[4];
				arr[0] = filePath;
				arr[1] = fileName;
				arr[2] = upTime;
				arr[3] = Jxkh_DataFile.HIGHT.toString();
				hightFileList.add(arr);
				hightFileListbox.setModel(new ListModelList(hightFileList));
			}
		});
		try {
			w.doModal();
		} catch (SuspendNotAllowedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void onClick$upEmpFileBn() {
		final UpfileWindow w = (UpfileWindow) Executions.createComponents("/admin/personal/businessdata/meeting/upfile.zul", null, null);
		w.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				String filePath = (String) Executions.getCurrent().getAttribute("path");
				String fileName = (String) Executions.getCurrent().getAttribute("title");
				String upTime = (String) Executions.getCurrent().getAttribute("upTime");
				String[] arr = new String[4];
				arr[0] = filePath;
				arr[1] = fileName;
				arr[2] = upTime;
				arr[3] = Jxkh_DataFile.EMP.toString();
				empFileList.add(arr);
				empFileListbox.setModel(new ListModelList(empFileList));
			}
		});
		try {
			w.doModal();
		} catch (SuspendNotAllowedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
