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
	// 用户数据访问接口
	private UserService userService;
	// 用户输入框组件
	/**
	 * 基本信息
	 */
	private Button print;
	// 用户登录名
	// private Label kuLid;
	// 真实姓名，曾用名,员工编号，健康状态，身份证号，籍贯
	private Textbox kuName, kuUsedname, thid, healthstate, kuIdentity, nativeplace;
	// 头像
	private org.zkoss.zul.Image img;
	// 出生日期
	private Datebox kuBirthday;
	// 性别
	private Radiogroup kuSex;
	// 民族、婚姻状态
	Listbox kuNation, marrystate;
	// 头像相关按钮
	// private Button upload;
	private Button delete;
	// 所属部门
	private Label deptSelect;
	// 职称、职称级别
	TitleSelectHbox selectTitle;
	// 外语水平 政治面貌
	private Listbox language, kuPolitical;
	// 入党时间
	private Datebox partytime;
	// 个人简介
	private Textbox uinfo;
	// 绑定类型
	private Listbox bangType;
	// 绑定地址
	private Textbox uBandIp;
	// 自动登录
	private Checkbox kuAutoenter;
	// 绑定地址行
	private Row Band;
	private Label partyLabel;
	// 保存 重置
	// private Toolbarbutton submit1, reset1;

	/**
	 * 教育经历
	 */
	/** 第一学历 */
	// 学历 学位 学制
	private Listbox fedubackgr, facdegree, fduyear;// ??????????????????????
	// 毕业院校 主修专业 学历证书编号
	private Textbox fkuSchool, fhighmajor, fcetificateno;
	// 毕业时间
	private Datebox fgradutime, firstInTime;
	/** 最高学历 */
	// 学历 学位 学制
	private Listbox hedubackgr, hacdegree, heduyear;// ???????????????????
	// 毕业院校 主修专业 学历证书编号
	private Textbox kuSchool, highmajor, hcetificateno;
	private Listbox firstFileListbox, hightFileListbox;

	private List<String[]> firstFileList = new ArrayList<String[]>();
	private List<String[]> hightFileList = new ArrayList<String[]>();

	// 毕业时间
	private Datebox highgradutime, hightInTime;
	// 保存 重置
	// private Toolbarbutton submit2, reset2;
	/** 参加培训情况 */
	// 添加培训信息
	private Toolbarbutton addPeiXun;
	// 培训列表
	private Listbox show;

	/**
	 * 工作信息
	 */
	// 参加工作时间 到院工作时间
	private Datebox starworktime, entertime;
	// 岗位性质 职务 职务级别 现聘专业技术岗位 等级 用工性质 职工类别 职工性质 职工状态//????????????????????
	private Listbox teaqualiry, workproper, worktype;// ,staffstate;
														// //staffproperty,
	private Textbox post, rank, nowPost;// ,nowRank;
	// 离职时间
	private Label leave, reason;
	private Datebox entiretime;
	// 离职原因
	private Textbox entirerea;
//	private Textbox emplquali;
	/** 聘用信息 */
	// 聘用资格 聘用级别 聘用岗位 聘用状态
	private Textbox emplrank, emplstate;// emplposition,
//	private Datebox getTime;
	private Listbox empFileListbox;
	private List<String[]> empFileList = new ArrayList<String[]>();
	// 聘用时间 聘用截止时间 解聘时间
	private Datebox empltime, endtime, dismisstime;
	// 解聘文号 解聘原因
	private Textbox dismissno, dismissreason;
	/** 岗位调动信息 */

	// 岗位调动信息
	private Listbox history;
	// 分页
	private Paging zxPaging1;
	// 保存 重置
	private Toolbarbutton submit3, reset3;
	/**
	 * 个人职称信息
	 */
	// 添加职称信息
//	private Toolbarbutton addtitle;
	// 职称信息表
	private Listbox titleList;
	/**
	 * 联系方式
	 */
	// 家庭住址、家庭电话、工作电话、手机、电子邮箱、MSN、QQ、个人主页、传真、其他联系方式
	Textbox address, kuhomePhone, kuworkPhone, kuPhone, kuEmail, msn, qq, homepage, chuanzhen, others;
	// 保存 重置
	// private Toolbarbutton submit4,reset4;

	/**
	 * 用到的实体
	 */
	WkTUser user;
	Jxkh_UserDetail detail = new Jxkh_UserDetail();
	/**
	 * 用到的service
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
		initUser(); // 调用初始化窗口函数
		initMenu();
		initTitle();
		initDutyChange();
		print.setHref("/print.action?n=user&id=" + user.getKuId());
	}

	public void onClick$upload() throws InterruptedException, IOException {
		Integer maxSize = 1024;
		this.getDesktop().getWebApp().getConfiguration().setMaxUploadSize(maxSize.intValue());
		String fileTooLarge = "上传的图片文件过大,文件大小应小于1MB";
		String confPath = Sessions.getCurrent().getWebApp().getRealPath("/") + "/WEB-INF/classes/conf.properties";
		SetFileUploadError.SetErrorToConf(fileTooLarge, confPath, "fileTooLarge");
		String Path, Type;
		ImageUploadPackage iUP = FileuploadEx.get();
		Media media = null;
		if (iUP.getIsCancel()) {
			// 按下取消按钮
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
				Messagebox.show("文件类型只能为jpg,bmp,gif,png格式！", "提示", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
		} else {
			Messagebox.show("文件不存在！", "提示", Messagebox.OK, Messagebox.EXCLAMATION);
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
	 * <li>功能描述：初始化 register页面数据。 void
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
		 * 各Listbox的初始化
		 */
		// 政治面貌
		final String[] political = { "-请选择-", "中国共产党党员", "中国共产党预备党员", "中国共产主义青年团团员", "中国国民党革命委员会会员", "中国民主同盟盟员", "中国民主建国会会员", "中国民主促进会会员", "中国农工民主党党员", "中国致公党党员", "九三学社社员", "台湾民主自治同盟盟员", "无党派民主人士", "群众" };
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
		// 外语水平
		String[] languagelist = { "-请选择-", "一般", "熟练", "精通" };
		initListbox(languagelist, language);
		// 职称
		if (user.getTitle() == null || "".equals(user.getTitle())) {
			List titleLsit = xypttitleService.findBytid("011");
			Title t = (Title) titleLsit.get(0);
			selectTitle.setTitle(t);
		} else {
			/**
			 * 待解决？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？
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
		// //职称级别
		// Title title = (Title)ftitle.getSelectedItem().getValue();
		// titleRank.initSListbox(title.getPtiId());
		// }
		// });

		// 学历
		String[] Educational = { "-请选择-", "博士毕业", "博士结业", "博士肄业", "双硕士", "硕士毕业", "硕士结业", "硕士肄业", "相当硕士毕业", "研究生班毕业", "研究生班结业", "研究生班肄业", "双本科", "本科毕业", "本科结业", "本科肄业", "相当本科毕业", "双大专", "大专毕业", "大专结业", "相当大专毕业", "中专毕业", "中专结业" };
		for (int j = 0; j < Educational.length; j++) {
			edu.add(Educational[j]);
		}
		// kuEducational.setModel(new ListModelList(edu));
		fedubackgr.setModel(new ListModelList(edu));
		hedubackgr.setModel(new ListModelList(edu));
		// 学位
		String[] xuewei = { "-请选择-", "名誉博士", "博士", "硕士", "双硕士", "学士", "双学士", "无" };
		for (int j = 0; j < xuewei.length; j++) {
			xw.add(xuewei[j]);
		}
		// kuXuewei.setModel(new ListModelList(xw));
		facdegree.setModel(new ListModelList(xw));
		hacdegree.setModel(new ListModelList(xw));
		// 民族
		String[] nation = { "-请选择-", "汉族", "蒙古族", "回族", "藏族", "维吾尔", "苗族", "彝族", "壮族", "布依族", "朝鲜族", "满族", "侗族", "瑶族", "白族", "土家族", "哈尼族", "哈萨克族", "傣族", "黎族", "僳僳族", "佤族", "畲族", "高山族", "拉祜族", "水族", "东乡族", "纳西族", "景颇族", "柯尔克孜族", "土族", "达斡尔族", "仫佬族", "羌族",
				"布朗族", "撒拉族", "毛难族", "仡佬族", "锡伯族", "阿昌族", "塔吉克族", "普米族", "怒族", "乌孜别克族", "俄罗斯族", "鄂温克族", "德昂族", "保安族", "裕固族", "京族", "塔塔尔族", "独龙族", "鄂伦春族", "赫哲族", "门巴族", "基诺族", "珞巴族" };
		for (int i = 0; i < nation.length; i++) {
			nat.add(nation[i]);
		}
		kuNation.setModel(new ListModelList(nat));
		// 婚姻状态
		String[] marry = { "-请选择-", "未婚", "已婚", "离异", "再婚" };
		for (int i = 0; i < marry.length; i++) {
			mar.add(marry[i]);
		}
		marrystate.setModel(new ListModelList(mar));
		// 岗位性质
		String[] teacherqualify = { "-请选择-", "在职在岗", "在职不在岗", "退休返聘", "外聘", "离职", "退休" };
		initListbox(teacherqualify, teaqualiry);// ///???????????????????????????????????????????????
		/*
		 * for (int i = 0; i < teacherqualify.length; i++) {
		 * tea.add(teacherqualify[i]); } teaqualiry.setModel(new
		 * ListModelList(tea));
		 */

		// 职位

		// 职位级别

		// 现聘专业技术岗位

		// 等级

		// 用工性质
		String[] workpro = { "-请选择-", "合同制", "聘用制", "临时工", "外聘" };
		for (int i = 0; i < workpro.length; i++) {
			worp.add(workpro[i]);
		}
		workproper.setModel(new ListModelList(worp));
		// 职工类别
		String[] worktyp = { "-请选择-", "管理人员", "专业技术人员", "工勤人员" };
		for (int i = 0; i < worktyp.length; i++) {
			wort.add(worktyp[i]);
		}
		worktype.setModel(new ListModelList(wort));
		// 职工性质
		/*
		 * String[] stafpro = { "-请选择-", "公立", "私立", "其他" }; for (int i = 0; i <
		 * stafpro.length; i++) { stafp.add(stafpro[i]); }
		 * staffproperty.setModel(new ListModelList(stafp));
		 */
		// 职工状态
		/*
		 * String[] stafstate = { "-请选择-", "实习", "在职", "离职", "退休" }; for (int i
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

		// 学制
		String[] year = { "-请选择-", "两年", "两年半", "三年", "四年", "五年" };
		for (int i = 0; i < year.length; i++) {
			fyear.add(year[i]);
			hyear.add(year[i]);
		}
		fduyear.setModel(new ListModelList(fyear));
		heduyear.setModel(new ListModelList(hyear));

		/**
		 * 各Textbox的初始化
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

		if (user.getKuSex().trim().equalsIgnoreCase("2")) { // 性别是字符，要去空格，否则该语句失灵
			kuSex.setSelectedIndex(1);
		} else {
			kuSex.setSelectedIndex(0);
		}
		uBandIp.setValue(user.getKuBindaddr());
		bangType.setSelectedIndex(Integer.valueOf(user.getKuBindtype().trim()));
		bangTypeHandle(); // 调用函数
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
			// 第一学历、最高学历
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
			// 第一学位、最高学位
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
			 * 初始化学历学位的附件
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
			/** 工作信息 */
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
		 * 各Listbox的初始化
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

		// 改进的头像显示
		// 删除cache目录中的遗留文件
		delete.setDisabled(false);
		// 避免浏览器缓存图片
		if (user.getKuPath() == null || user.getKuPath().length() == 0) {
			delete.setDisabled(true);
			img.setSrc("/admin/image/head/default.jpg");
		} else {
			// System.out.println("kuPAth="+user.getKuPath());
			String srcPath = this.getDesktop().getWebApp().getRealPath(user.getKuPath());
			File srcFile = new File(srcPath);
			if (!srcFile.exists()) {// 图片不存在显示默认头像
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
	 * 初始化listbox中数据的方法
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
	 * <li>功能描述：用户信息更新功能。 void
	 * 
	 * @author bobo
	 * @throws InterruptedException
	 * @2010-3-1
	 */
	public void onClick$submit1() throws InterruptedException {
		if ("".equals(kuName.getValue())) {
			Messagebox.show("用户名不能为空！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			kuName.focus();
			return;
		}
		if ("".equals(thid.getValue())) {
			Messagebox.show("员工不能为空！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			thid.focus();
			return;
		}
		if (kuIdentity.getValue().equals("")) {
			Messagebox.show("身份证号不能为空！", "提示", Messagebox.OK, Messagebox.INFORMATION);
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
			user.setKuSex("1"); // 1代表“男”，2代表“女”
		} else {
			user.setKuSex("2");
		}
		user.setKuNativeplace(nativeplace.getText());

		// 职称及职称级别
		user.setTitle(selectTitle.getSelectTitle().getTiId());
		// user.setTitle(ftitle.getSelectTitle().getTiId());
		// user.setTitleRank(titleRank.getSelectTitle().getTiId());

		// 如果用户选择不绑定，则设置其不能自动登陆，同时将绑定IP地址设置空.
		// 如果用户选择IP绑定，首先设置绑定的IP地址，如果输入则设置为输入IP，否则设置该用户上传登陆IP地址。
		// 选择IP绑定并且设置绑定IP后，判断用户是否设置自动登陆
		if (bangType.getSelectedIndex() == 0) {// 选择不绑定
			user.setKuBindtype(WkTUser.BAND_NO);
			user.setKuAutoenter(WkTUser.AUTOENTER_NO);
			user.setKuBindaddr("");
		} else {
			user.setKuBindtype(WkTUser.BAND_YES);
			try {
				IPUtil.getIPLong(uBandIp.getValue());
				user.setKuBindaddr(uBandIp.getValue());
			} catch (Exception e) {
				throw new WrongValueException(uBandIp, "绑定地址输入错误!");
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
		 * //判断研究方向个数并存储 String[] a= search.getValue().split(","); if(yjfx ==
		 * null || yjfx.size() == 0){ for(int i=0;i<a.length;i++){ String[] a1=
		 * a[i].split("，"); if(a1.length !=0){ for(int j = 0;j < a1.length;j++){
		 * GhYjfx yj=new GhYjfx(); yj.setYjResearch(a1[j]);
		 * yj.setKuId(user.getKuId()); yjfxService.save(yj); } }else { GhYjfx
		 * yj=new GhYjfx(); yj.setYjResearch(a[i]); yj.setKuId(user.getKuId());
		 * yjfxService.save(yj); } } }else if(yjfx != null && yjfx.size() != 0){
		 * for(int j = 0;j < yjfx.size(); j++){ GhYjfx yj=(GhYjfx) yjfx.get(j);
		 * yjfxService.delete(yj); } for(int i=0;i<a.length;i++){ String[] a1=
		 * a[i].split("，"); if(a1.length !=0){ for(int j = 0;j < a1.length;j++){
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
		Messagebox.show("保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
	}

	public void onClick$submit3() throws InterruptedException {
		if (user == null) {
			Messagebox.show("请先保存用户基本信息！", "提示", Messagebox.OK, Messagebox.INFORMATION);
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
		 * 保存附件
		 */
		//先删除原来的附件
		List<Jxkh_DataFile> list = userDetailService.getFileByUser(detail.getKuId(), Jxkh_DataFile.EMP);
		if(list != null && list.size() > 0) {
			for(Jxkh_DataFile f : list) {
				if(f != null)
					userDetailService.delete(f);
			}
		}		
		//再保存新的附件
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
		
		Messagebox.show("保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
	}

	public void onClick$submit4() {
		if (user == null) {
			try {
				Messagebox.show("请先保存用户基本信息！", "提示", Messagebox.OK, Messagebox.INFORMATION);
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
			Messagebox.show("请先保存用户基本信息！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		if (kuPhone.getValue().equals("")) {
			Messagebox.show("手机号不能为空！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			kuPhone.focus();
			return;
		}
		user.setKuPhone(kuPhone.getValue());
		if (kuEmail.getValue().equals("")) {
			Messagebox.show("电子邮箱不能为空！", "提示", Messagebox.OK, Messagebox.INFORMATION);
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
		Messagebox.show("保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
	}

	public void onClick$submit2() throws InterruptedException {
		if (user == null) {
			Messagebox.show("请先保存用户基本信息！", "提示", Messagebox.OK, Messagebox.INFORMATION);
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
		 * 保存学历学位附件
		 */
		// 现将以前的附件都删除了
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
		// 再保存新的附件
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
		Messagebox.show("保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
	}

	/**
	 * <li>功能描述：用户信息重置功能。 void
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
	 * 针对绑定与不绑定进行操作
	 */
	private void bangTypeHandle() {
		if (bangType.getSelectedIndex() == 0) {// 不绑定
			Band.setVisible(false);
			uBandIp.setRawValue(null);
			uBandIp.setReadonly(true);
			kuAutoenter.setChecked(false);
			kuAutoenter.setDisabled(true);
		} else {// 绑定IP
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

	// 初始化培训情况列表
	public void initMenu() {
		List pxlst;
		pxlst = pxqkService.findByKuid(user.getKuId());
		show.setModel(new ListModelList(pxlst));
		initShow();
	}

	// 初始化职称列表
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
		//初始化附件
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

	// 初始化职位调动信息
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
				final Toolbarbutton btnedit = new Toolbarbutton();// 编辑
				final Toolbarbutton btndele = new Toolbarbutton();// 删除
				btnedit.setLabel("编辑");
				btnedit.setStyle("color:blue");
				btnedit.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						edit(p);
					}
				});
				btnedit.setParent(hbox);
				btndele.setLabel("删除");
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
				final Toolbarbutton btnedit = new Toolbarbutton();// 编辑
				final Toolbarbutton btndele = new Toolbarbutton();// 删除
				btnedit.setLabel("编辑");
				btnedit.setStyle("color:blue");
				btnedit.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						edittitle(z);
					}
				});
				btnedit.setParent(hbox);
				btndele.setLabel("删除");
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
				tb.setLabel("查看详细");
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
		if (Messagebox.show("确定删除此培训记录吗？", "确认", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK) {
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
		if (Messagebox.show("确定删除此职称信息吗？", "确认", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK) {
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
