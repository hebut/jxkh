package com.uniwin.asm.personal.ui.data;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import org.iti.xypt.entity.Yjs;
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
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import com.uniwin.common.util.IPUtil;
import com.uniwin.framework.common.fileuploadex.FileuploadEx;
import com.uniwin.framework.common.fileuploadex.ImageUploadPackage;
import com.uniwin.framework.common.fileuploadex.SetFileUploadError;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

public class YjsRegister extends Window implements AfterCompose {

	private UserService userService;
	private org.zkoss.zul.Image imgShow;
	private Button deleteShow;
	private Label stid, sname;
	private Textbox kuEmail, kuqq;
	private Textbox kuPhone;
	private Textbox uBandIp;
	private Checkbox kuAutoenter;
	private Listbox bangType;
	private Textbox uinfo;
	private Textbox name1, name2, work1, work2, phone1, phone2, contact1, contact2;
	// 用户实体
	WkTUser user;
	Yjs yjs;
	YjsService yjsService;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		bangType.addEventListener(Events.ON_SELECT, new EventListener() {

			public void onEvent(Event arg0) throws Exception {
				bangTypeHandle();
			}
		});
		initUser();// 调用初始化窗口函数
	}

	public void initUser() {
		if (user.getKuEmail() != null) {
			kuEmail.setValue(user.getKuEmail().trim());
		}
		if (user.getKuPhone() != null) {
			kuPhone.setValue(user.getKuPhone().trim());
		}
		yjs = yjsService.findByKuid(user.getKuId());
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
		if (yjs.getPath() == null || yjs.getPath().length() == 0) {
			deleteShow.setDisabled(true);
		} else {
			String srcPath = this.getDesktop().getWebApp().getRealPath(yjs.getPath());
			File srcFile = new File(srcPath);
			if (!srcFile.exists()) {// 图片不存在显示默认头像
				deleteShow.setDisabled(true);
			} else {
				imgShow.setSrc(yjs.getPath());
				deleteShow.setDisabled(false);
			}
		}
		stid.setValue(yjs.getYjsId());
		sname.setValue(user.getKuName());
		// 家庭成员
		if (yjs.getStFname() != null) {
			name1.setValue(yjs.getStFname());
		}
		if (yjs.getStMname() != null) {
			name2.setValue(yjs.getStMname());
		}
		if (yjs.getStFrelation() != null) {// 关系
			contact1.setValue(yjs.getStFrelation());
		}
		if (yjs.getStMrelation() != null) {// 关系
			contact2.setValue(yjs.getStMrelation());
		}
		if (yjs.getStMrelation() != null) {// 关系
			work1.setValue(yjs.getStMrelation());
		}
		if (yjs.getStFwork() != null) {// 工作单位
			work1.setValue(yjs.getStFwork());
		}
		if (yjs.getStMwork() != null) {// 工作单位
			work2.setValue(yjs.getStMwork());
		}
		if (yjs.getStFphone() != null) {// 工作单位
			phone1.setValue(yjs.getStFphone());
		}
		if (yjs.getStMphon() != null) {// 工作单位
			phone2.setValue(yjs.getStMphon());
		}
		if (yjs.getQq() != null) {
			kuqq.setValue(yjs.getQq().trim());
		}
	}

	public void onClick$uploadShow() throws InterruptedException, IOException {
		Integer maxSize = 5000;
		this.getDesktop().getWebApp().getConfiguration().setMaxUploadSize(maxSize.intValue());
		String fileTooLarge = "上传的图片文件过大,文件大小应小于5MB";
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
			Path = this.getDesktop().getWebApp().getRealPath("/admin/image/head/personal/" + media.getName());
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
		BufferedImage src = ImageIO.read(srcfile);
		double differ = src.getWidth() / (double) 700.0;
		int height = (int) (src.getHeight() / differ);
		BufferedImage pic = new BufferedImage(700, height, BufferedImage.TYPE_INT_RGB);
		pic.getGraphics().drawImage(src.getScaledInstance(700, height, Image.SCALE_SMOOTH), 0, 0, null);
		if (yjs.getPath() == null || yjs.getPath().equals("")) {
			yjs.setPath("");
		} else {
			File oldImage = new File(this.getDesktop().getWebApp().getRealPath(yjs.getPath()));
			if (oldImage.exists())
				oldImage.delete();
		}
		String kuPath = "/admin/image/head/personal/" + user.getKuLid().trim() + Type;
		yjs.setPath(kuPath.trim());
		yjsService.update(yjs);
		ImageIO.write(pic, Type.substring(1, Type.length()), new File(this.getDesktop().getWebApp().getRealPath(kuPath)));
		File file = new File(Path);
		if (file.exists())
			file.delete();
		initUser();
	}

	public void onClick$deleteShow() {
		String path = this.getDesktop().getWebApp().getRealPath(yjs.getPath());
		File file = new File(path);
		if (file.exists())
			file.delete();
		yjs.setPath("".trim());
		this.yjsService.update(yjs);
		initUser();
		imgShow.setSrc("/admin/image/head/personal/default.jpg");
	}

	/**
	 * <li>功能描述：用户信息更新功能。 void
	 */
	public void onClick$save() throws InterruptedException {
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
		user.setKuEmail(kuEmail.getValue());
		user.setKuPhone(kuPhone.getValue());
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
		Yjs yjs = (Yjs) yjsService.findByKuid(user.getKuId());
		yjs.setStFname(name1.getValue().trim());
		yjs.setStFphone(phone1.getValue());
		yjs.setStFrelation(contact1.getValue());
		yjs.setStFwork(work1.getValue());
		yjs.setStMname(name2.getValue().trim());
		yjs.setStMphon(phone2.getValue());
		yjs.setStMrelation(contact2.getValue());
		yjs.setStMwork(work2.getValue());
		yjs.setQq(kuqq.getValue());
		userService.update(yjs);
		Sessions.getCurrent().setAttribute("user", user);
		Messagebox.show("保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
	}

	public void onClick$reset() {
		kuEmail.setRawValue("");
		kuPhone.setValue("");
		bangType.setSelectedIndex(0);
		kuAutoenter.setChecked(false);
		kuAutoenter.setDisabled(true);
		uBandIp.setValue("");
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
}
