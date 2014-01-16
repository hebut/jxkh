package com.uniwin.asm.personal.ui.data;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Random;
import javax.imageio.ImageIO;
import org.iti.xypt.entity.Yjs;
import org.iti.xypt.service.YjsService;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import com.uniwin.common.util.ConvertUtil;
import com.uniwin.common.util.DateUtil;
import com.uniwin.framework.common.fileuploadex.FileuploadEx;
import com.uniwin.framework.common.fileuploadex.ImageUploadPackage;
import com.uniwin.framework.common.fileuploadex.SetFileUploadError;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;

public class YjsBasic extends Window implements AfterCompose {

	private org.zkoss.zul.Image img;
	private Button delete;
	private Label stid, kuName;
	private Label stClassList, xueyuan, bumen;
	private Label politics, english, math, major, total;
	/**
	 * 考试类型
	 */
	private Label type;
	private Label yuan, xi;
	private Label kuLid;
	private Label kuBirthday, address, code;
	private Label kuSex;
	private Label kuIdentity, kuPolitical;
	/**
	 * 学籍状态
	 */
	private Label xueji, gdyear, gdschool, gdmajor;
	// 用户实体
	WkTUser user;
	Yjs yjs;
	YjsService yjsService;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		yuan.setValue(user.getDept().getGradeName(WkTDept.GRADE_YUAN));
		xi.setValue(user.getDept().getGradeName(WkTDept.GRADE_XI));
		initUser();// 调用初始化窗口函数
	}

	public void initUser() {
		kuLid.setValue(user.getKuLid());
		kuName.setValue(user.getKuName());
		if (user.getKuBirthday() != null && user.getKuBirthday().length() > 0) {
			DateFormat nf = new SimpleDateFormat("yyyy年MM月dd日");
			kuBirthday.setValue(nf.format(ConvertUtil.convertDate(DateUtil.getDateString(user.getKuBirthday()))));
		}
		if (user.getKuSex().trim().equalsIgnoreCase("2")) { // 性别是字符，要去空格，否则该语句失灵
			kuSex.setValue("女");
		} else {
			kuSex.setValue("男");
		}
		yjs = yjsService.findByKuid(user.getKuId());
		stid.setValue(user.getKuLid());
		if (yjs.getClId() == 0L) {
			stClassList.setValue("暂未分配");
		}
		xueyuan.setValue(user.getDept().getPdept().getKdName());
		bumen.setValue(user.getDept().getKdName());
		xueji.setValue("正常");
		type.setValue(yjs.getKsfs());
		politics.setValue(yjs.getPolitics() + "");
		english.setValue(yjs.getEnglish() + "");
		math.setValue(yjs.getMath() + "");
		major.setValue(yjs.getMajor() + "");
		total.setValue(yjs.getTotal() + "");
		kuIdentity.setValue(user.getKuSfzh());
		kuPolitical.setValue(user.getKuPolitical());
		address.setValue(yjs.getAddress());
		gdyear.setValue(yjs.getByny());
		gdschool.setValue(yjs.getBydw());
		gdmajor.setValue(yjs.getByzymc());
		code.setValue(yjs.getCode());
		delete.setDisabled(false);
		if (user.getKuPath() == null || user.getKuPath().length() == 0) {
			delete.setDisabled(true);
		} else {
			String srcPath = this.getDesktop().getWebApp().getRealPath(user.getKuPath());
			File srcFile = new File(srcPath);
			if (!srcFile.exists()) {// 图片不存在显示默认头像
				delete.setDisabled(true);
			} else {
				img.setSrc(user.getKuPath());
				delete.setDisabled(false);
			}
		}
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
		String kuPath = "/admin/image/head/" + stid.getValue().trim() + "_" + r.nextInt() + Type;// ".jpg";
		user.setKuPath(kuPath.trim());
		yjsService.update(user);
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
		this.yjsService.update(user);
		initUser();
	}
}
