package org.iti.xypt.personal.group;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.imageio.ImageIO;

import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.uniwin.asm.personal.entity.QzGroup;
import com.uniwin.asm.personal.entity.QzMember;
import com.uniwin.asm.personal.service.GroupService;
import com.uniwin.framework.entity.WkTUser;

public class AddNewWindow extends Window implements AfterCompose {
	Textbox name, descript;
	Listbox typeList;
	Image groupLogo;
	Button upload, submit, cancel;
	String LogoPath = "/admin/image/group/default.jpg";
	GroupService groupService;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
	}

	public void onClick$upload() throws IOException, InterruptedException {
		String Path, Type;
		Media media = Fileupload.get();
		if (media != null) {
			Path = this.getDesktop().getWebApp().getRealPath("/admin/image/group/logo/" + media.getName());
			LogoPath = "/admin/image/group/logo/" + media.getName();
			Type = media.getName().substring(media.getName().lastIndexOf("."), media.getName().length());
			if (Type.equalsIgnoreCase(".jpg")) {
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
				Messagebox.show("文件类型只能为JPG格式！", "提示", Messagebox.OK, Messagebox.EXCLAMATION);
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
		java.awt.Image src = ImageIO.read(srcfile);
		BufferedImage logo = new BufferedImage(80, 80, BufferedImage.TYPE_INT_RGB);
		logo.getGraphics().drawImage(src.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
		ImageIO.write(logo, Type.substring(1, Type.length()), new File(this.getDesktop().getWebApp().getRealPath("/admin/image/group/logo/" + media.getName())));
		groupLogo.setSrc(LogoPath);
	}

	public void onClick$submit() {
		QzGroup group = new QzGroup();
		WkTUser user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		group.setQzUser(user.getKuId());
		group.setQzTime(new Date());
		group.setQzName(name.getValue());
		group.setQzType(typeList.getSelectedIndex());
		group.setQzPath(LogoPath);
		group.setQzdescrib(descript.getValue());
		groupService.save(group);
		QzMember member = new QzMember();
		QzGroup newGroup = groupService.findNewCreateGroup(user.getKuId());
		member.setQzId(newGroup.getQzId());
		member.setMbMember(user.getKuId());
		member.setMbAccept(QzMember.ACCEPT_YES);
		member.setMbAgree(QzMember.AGREE_YES);
		member.setMbRole(QzMember.Admin);
		groupService.save(member);
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	public void onClick$cancel() {
		this.detach();
	}
}
