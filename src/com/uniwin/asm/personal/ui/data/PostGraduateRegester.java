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
	// 用户数据访问接口
	private UserService userService;
	// 用户输入框组件
	/**
	 * 真实姓名
	 */
	private Textbox kuName,kuUsedname,nativeplace,healthstate,homaddress;
	/**
	 * 学号
	 */
	private Label stid;
	// 班级，
	private Label stClassList, xueyuan, bumen;
	//
	private Label yuan, xi;
	/**
	 * 邮箱地址
	 */
	private Textbox kuEmail;
	/**
	 * 电话
	 */
	private Textbox kuPhone;
	/**
	 * 绑定IP
	 */
	private Textbox uBandIp;
	/**
	 * 用户登录名
	 */
	private Label kuLid;
	/**
	 * 自动登录
	 */
	private Checkbox kuAutoenter;
	/**
	 * 用户生日
	 */
	private Datebox kuBirthday;
	/**
	 * 选择男生女生
	 */
	private Radiogroup kuSex;
	/**
	 * 绑定类型
	 */
	private Listbox bangType,kuNation;
	/**
	 * 用户简介
	 */
	private Textbox uinfo,kuPolitical;
	/**
	 * 身份证号和政治面貌
	 */
	private Textbox kuIdentity;
	/**
	 * 学籍状态
	 */
	private Label xueji;
	// 用户实体
	WkTUser user;
	YjsService yjsService;
	// 重置按钮
	private Button reset;
	Listbox marrystate;
	private org.zkoss.zul.Image img;
	//头像按钮
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
		initUser();// 调用初始化窗口函数
	}

	/**
	 * <li>功能描述：初始化 register页面数据。 void
	 * 
	 * @author bobo
	 * @2010-3-13
	 */
	public void initUser() {
//		List pol = new ArrayList();
		List nat = new ArrayList();
//		String[] political = { "-请选择-", "中国共产党党员", "中国共产党预备党员", "中国共产主义青年团团员",
//				"中国国民党革命委员会会员", "中国民主同盟盟员", "中国民主建国会会员", "中国民主促进会会员",
//				"中国农工民主党党员", "中国致公党党员", "九三学社社员", "台湾民主自治同盟盟员", "无党派民主人士", "群众" };
//		for (int j = 0; j < political.length; j++) {
//			pol.add(political[j]);
//		}
//		kuPolitical.setModel(new ListModelList(pol));
		String[] nation = { "-请选择-", "汉族", "蒙古族", "回族", "藏族", "维吾尔组", "苗族",
				"彝族", "壮族", "布依族", "朝鲜族", "满族", "侗族", "瑶族", "白族", "土家族", "哈尼族",
				"哈萨克族", "傣族", "黎族", "僳僳族", "佤族", "畲族", "高山族", "拉祜族", "水族",
				"东乡族", "纳西族", "景颇族", "柯尔克孜族", "土族", "达斡尔族", "仫佬族", "羌族", "布朗族",
				"撒拉族", "毛难族", "仡佬族", "锡伯族", "阿昌族", "塔吉克族", "普米族", "怒族",
				"乌孜别克族", "俄罗斯族", "鄂温克族", "德昂族", "保安族", "裕固族", "京族", "塔塔尔族",
				"独龙族", "鄂伦春族", "赫哲族", "门巴族", "基诺族", "珞巴族" };
		for (int i = 0; i < nation.length; i++) {
			nat.add(nation[i]);
		}
		kuNation.setModel(new ListModelList(nat));
//		List mar = new ArrayList();
//		String[] marry = { "-请选择-", "未婚", "已婚", "离异", "再婚" };
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
		if (user.getKuSex().trim().equalsIgnoreCase("2")) { // 性别是字符，要去空格，否则该语句失灵
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
		bangTypeHandle(); // 调用函数
		if (user.getKuAutoenter().trim().equalsIgnoreCase("1")) {
			kuAutoenter.setChecked(true);
		} else {
			kuAutoenter.setChecked(false);
		}
		if (user.getKuIntro() != null) {
			uinfo.setValue(user.getKuIntro());
		}
		// 改进的头像显示
		// 删除cache目录中的遗留文件
		delete.setDisabled(false);
		// 避免浏览器缓存图片
		if (user.getKuPath() == null||user.getKuPath().length()==0) {
			delete.setDisabled(true);
			img.setSrc("/admin/image/head/default.jpg");
		} else {
			String srcPath = this.getDesktop().getWebApp().getRealPath(
					user.getKuPath());
			File srcFile = new File(srcPath);
			if (!srcFile.exists()) {// 图片不存在显示默认头像
				delete.setDisabled(true);
				img.setSrc("/admin/image/head/default.jpg");
			} else {
				img.setSrc(user.getKuPath());
			}
		}
	}

	/**
	 * <li>功能描述：用户信息更新功能。 void
	 * 
	 * @author bobo
	 * @throws InterruptedException
	 * @2010-3-1
	 */
	public void onClick$save() throws InterruptedException {
		if ("".equals(kuName.getValue())) {
			Messagebox.show("用户名不能为空！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			kuName.focus();
			return;
		}
		if ("".equals(kuEmail.getValue())) {
			Messagebox.show("电子邮箱不能为空！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			kuEmail.focus();
			return;
		}
		if ("".equals(kuPhone.getValue())) {
			Messagebox.show("电话不能为空！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			kuPhone.focus();
			return;
		}
		if ("".equals(kuIdentity.getValue())) {
			Messagebox.show("身份证号不能为空！", "提示", Messagebox.OK, Messagebox.INFORMATION);
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
			user.setKuSex("1"); // 1代表“男”，2代表“女”
		} else {
			user.setKuSex("2");
		}
		user.setKuHomeaddress(homaddress.getValue());
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
		if (uinfo.getValue() == null || "".equals(uinfo.getValue())) {
			user.setKuIntro("");
		} else {
			user.setKuIntro(uinfo.getValue());
		}
		userService.update(user);
		Sessions.getCurrent().setAttribute("user", user);
		Messagebox.show("保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
	}

	/**
	 * <li>功能描述：用户信息重置功能。 void
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
	 * 针对绑定与不绑定进行操作
	 */
	private void bangTypeHandle() {
		if (bangType.getSelectedIndex() == 0) {// 不绑定
			uBandIp.setRawValue(null);
			uBandIp.setReadonly(true);
			kuAutoenter.setChecked(false);
			kuAutoenter.setDisabled(true);
		} else {// 绑定IP
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
		String fileTooLarge="上传的图片文件过大,文件大小应小于1MB";
		String confPath=Sessions.getCurrent().getWebApp().getRealPath("/")+"/WEB-INF/classes/conf.properties";
		SetFileUploadError.SetErrorToConf(fileTooLarge, confPath,"fileTooLarge");
		String Path, Type;
		MediaUploadPackage iUP=Fileupload.get_MUP("图片上传-----文件应小于1MB");
		////
		Media media = null;
		if (iUP.getIsCancel()) {
			// 按下取消按钮
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
				Messagebox.show("文件类型只能为jpg,bmp,gif,png格式！", "提示", Messagebox.OK,
						Messagebox.EXCLAMATION);
				return;
			}
		} else {
			Messagebox.show("文件不存在！", "提示", Messagebox.OK,
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
