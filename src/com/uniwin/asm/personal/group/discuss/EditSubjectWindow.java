package com.uniwin.asm.personal.group.discuss;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Date;
import java.util.List;

import org.zkforge.fckez.FCKeditor;
import org.zkoss.io.Files;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zk.ui.util.Configuration;
import org.zkoss.zul.Button;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.uniwin.asm.personal.entity.QzGroup;
import com.uniwin.asm.personal.entity.QzRelation;
import com.uniwin.asm.personal.entity.QzSubject;
import com.uniwin.asm.personal.service.GroupService;
import com.uniwin.asm.personal.service.MemberService;
import com.uniwin.asm.personal.service.RelationService;
import com.uniwin.asm.personal.service.SubjectService;
import com.uniwin.framework.entity.WkTUser;

public class EditSubjectWindow extends Window implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Listbox imagelist, typelist;
	Textbox title;
	FCKeditor content;
	WkTUser user;
	GroupService groupService;
	SubjectService subjectService;
	MemberService memberService;
	RelationService relationService;
	Label filename, groupName;
	Button upload, delete;
	Image image;
	QzSubject subject = (QzSubject) Executions.getCurrent().getArg().get("subject");
	String Path = "";

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
	}

	public void initWindow() {
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		QzGroup group = (QzGroup) groupService.get(QzGroup.class, subject.getQzId());
		groupName.setValue(group.getQzName());
		title.setValue(subject.getSjTitle());
		// 图片设置
		if (subject.getSjImg() == 0 || subject.getSjImg() == null) {
			image.setSrc("/admin/image/group/icon" + 1 + ".gif");
			image.setVisible(true);
		} else {
			image.setSrc("/admin/image/group/icon" + subject.getSjImg() + ".gif");
			image.setVisible(true);
		}
		content.setValue(subject.getSjContent());
		String file = subject.getSjPath();
		if (file != null && file.length() != 0) {
			file = file.substring(file.lastIndexOf("-") + 1);
			filename.setValue(file);
			upload.setVisible(false);
			delete.setVisible(true);
			Path = subject.getSjPath();
		} else {
			upload.setVisible(true);
			delete.setVisible(false);
		}
		imagelist.setSelectedIndex(subject.getSjImg());
		typelist.setSelectedIndex(subject.getSjType());
		imagelist.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				if (arg0.getIndex() == subject.getSjImg()) {
					arg0.setSelected(true);
				} else if (arg0.getIndex() == 0) {
					arg0.setSelected(true);
				}
			}
		});
		typelist.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				if (arg0.getIndex() == subject.getSjType()) {
					arg0.setSelected(true);
				} else if (arg0.getIndex() == 0) {
					arg0.setSelected(true);
				}
			}
		});
		imagelist.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				switch (imagelist.getSelectedIndex()) {
				case 0:
					image.setVisible(false);
					break;
				case 1:
					image.setSrc("/admin/image/group/icon" + 1 + ".gif");
					image.setVisible(true);
					break;
				case 2:
					image.setSrc("/admin/image/group/icon" + 2 + ".gif");
					image.setVisible(true);
					break;
				case 3:
					image.setSrc("/admin/image/group/icon" + 3 + ".gif");
					image.setVisible(true);
					break;
				case 4:
					image.setSrc("/admin/image/group/icon" + 4 + ".gif");
					image.setVisible(true);
					break;
				case 5:
					image.setSrc("/admin/image/group/icon" + 5 + ".gif");
					image.setVisible(true);
					break;
				case 6:
					image.setSrc("/admin/image/group/icon" + 6 + ".gif");
					image.setVisible(true);
					break;
				case 7:
					image.setSrc("/admin/image/group/icon" + 7 + ".gif");
					image.setVisible(true);
					break;
				case 8:
					image.setSrc("/admin/image/group/icon" + 8 + ".gif");
					image.setVisible(true);
					break;
				case 9:
					image.setSrc("/admin/image/group/icon" + 9 + ".gif");
					image.setVisible(true);
					break;
				case 10:
					image.setSrc("/admin/image/group/icon" + 10 + ".gif");
					image.setVisible(true);
					break;
				}
			}
		});
	}

	public void onClick$publish() throws InterruptedException {
		if (title.getValue().equals("") || title.getValue().length() == 0) {
			Messagebox.show("请填写话题的标题！", "提示", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		subject.setSjTitle(title.getValue());
		subject.setSjType(typelist.getSelectedIndex());
		subject.setSjImg(imagelist.getSelectedItem().getIndex());
		if (Path != "")
			subject.setSjPath(Path);
		else
			subject.setSjPath(null);
		subject.setSjContent(content.getValue());
		subjectService.update(subject);
		List mlist = memberService.findJoinGroupExceptMyself(subject.getQzId(), user.getKuId(), 2);
		List rlist = relationService.findMemberBySubject(subject.getSjId(), true);
		for (int i = 0; i < mlist.size(); i++) {
			WkTUser user = (WkTUser) mlist.get(i);
			if (!rlist.contains(user.getKuId())) {
				QzRelation relation = new QzRelation();
				relation.setSjId(subject.getSjId());
				relation.setKuId(user.getKuId());
				relation.setRlState((short) 0);
				relation.setRlShow((short) 0);
				memberService.save(relation);
			}
		}
		Messagebox.show("发布成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
		Events.postEvent(Events.ON_CHANGE, this, null);
		this.detach();
	}

	public void onClick$upload() throws InterruptedException, IOException {
		Media media = Fileupload.get();
		if (media != null) {
			Long timekey = (new Date()).getTime();
			Path = this.getDesktop().getWebApp().getRealPath("/upload/group/" + user.getKuId());
			Configuration conf = this.getDesktop().getWebApp().getConfiguration();
			conf.setMaxUploadSize(1024 * 1024); // 最大1G
			conf.setUploadCharset("UTF-8");
			WkTUser wkTUser = (WkTUser) Sessions.getCurrent().getAttribute("user");// 获得登录用户
			String kuId = wkTUser.getKuId().toString();
				String fileName = media.getName();
				if (fileName.endsWith(".txt") || fileName.endsWith(".project")) {
					Reader r = media.getReaderData();
					//String path = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/");
					if (Path == null) {
						System.out.println("无法访问存储目录！");
						return;
					}
					File fUploadDir = new File(Path);
					if (!fUploadDir.exists()) {
						if (!fUploadDir.mkdir()) {
							System.out.println("无法创建存储目录！");
							return;
						}
					}
					String uploadDir = this.getDesktop().getWebApp().getRealPath("/upload/group");
					if (uploadDir == null) {
						System.out.println("无法访问存储目录！");
						return;
					}
					File fUploadDir2 = new File(uploadDir + "\\" + kuId); // 在upload文件下创建用户文件夹
					//String path2 = fUploadDir2.getCanonicalPath(); // 保存在path路径下
					if (!fUploadDir2.exists()) {
						if (!fUploadDir2.mkdir()) {
							System.out.println("无法创建存储目录！");
							return;
						}
					}
					String name = Executions.getCurrent().getDesktop().getWebApp().getRealPath("\\upload\\group") + "\\" + kuId + "\\" + timekey + "-" + fileName.trim(); // 文件保存名
					File f = new File(name);
					Files.copy(f, r, null);
					Path = "\\upload\\group" + "\\" + kuId + "\\" + timekey + "-" + fileName.trim();
					Messagebox.show("上传成功: " + media.getName(), "提示", Messagebox.OK, Messagebox.INFORMATION);
					filename.setValue(media.getName());
					upload.setVisible(false);
					delete.setVisible(true);
				} else {
					InputStream objin = media.getStreamData();
//					String fName = media.getName();
//					String path = this.getDesktop().getWebApp().getRealPath("/upload/");
					String pa = this.getDesktop().getWebApp().getRealPath("/upload/group");
					if (pa == null) {
						System.out.println("无法访问存储目录！");
						return;
					}
					File fUploadDir = new File(pa);
					if (!fUploadDir.exists()) {
						if (!fUploadDir.mkdir()) {
							System.out.println("无法创建存储目录！");
							return;
						}
					}
					String uploadDir = this.getDesktop().getWebApp().getRealPath("/upload/group/");
					if (uploadDir == null) {
						System.out.println("无法访问存储目录！");
						return;
					}
					File fUploadDir2 = new File(uploadDir + "\\" + kuId); // 在upload文件下创建用户文件夹
//					String path2 = fUploadDir2.getCanonicalPath(); // 保存在path路径下
					if (!fUploadDir2.exists()) {
						if (!fUploadDir2.mkdir()) {
							System.out.println("无法创建存储目录！");
							return;
						}
					}
					String name = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/group") + "//" + kuId + "//" + timekey + "-" + fileName.trim(); // 文件保存名
					FileOutputStream out = new FileOutputStream(name);
					DataOutputStream objout = new DataOutputStream(out);
					Files.copy(objout, objin);
					Messagebox.show("上传成功: " + media.getName(), "提示", Messagebox.OK, Messagebox.INFORMATION);
					Path = "\\upload\\group" + "\\" + kuId + "\\" + timekey + "-" + fileName.trim();
					filename.setValue(media.getName());
					upload.setVisible(false);
					delete.setVisible(true);
				
			} 
		}
		else {
			Messagebox.show("您没有选择上传文件，请选择！ ", "提示", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
	}

	public void onClick$delete() {
		if (Path != null && Path.length() != 0) {
			String RealPath = this.getDesktop().getWebApp().getRealPath(Path);
			File file = new File(RealPath);
			if (file.exists())
				file.delete();
		}
		upload.setVisible(true);
		delete.setVisible(false);
		filename.setValue("");
		Path = "";
	}
}
