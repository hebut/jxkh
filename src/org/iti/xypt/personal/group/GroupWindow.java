package org.iti.xypt.personal.group;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.iti.bysj.ui.base.InnerButton;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.uniwin.asm.personal.entity.QzGroup;
import com.uniwin.asm.personal.entity.QzMember;
import com.uniwin.asm.personal.service.MemberService;
import com.uniwin.framework.common.listbox.DeptListbox;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

public class GroupWindow extends Window implements AfterCompose {
	Button submit, upload, query, invite, cancel;
	DeptListbox deptSelect;
	Textbox name, descript, userName;
	Listbox typeList, memberList, applyList, choiceList, inviteList;
	Checkbox teacherCheck, studentCheck, graduateCheck;
	Image groupLogo;
	QzGroup group;
	String LogoPath;
	MemberService memberService;
	UserService userService;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
	}

	public void initWindow() {
		WkTUser user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		deptSelect.setRootDept(null);
		deptSelect.setRootID(0L);
		deptSelect.initNewDeptSelect(user.getDept());
		LogoPath = group.getQzPath();
		groupLogo.setSrc(LogoPath);
		name.setValue(group.getQzName());
		descript.setValue(group.getQzdescrib());
		typeList.setSelectedIndex(group.getQzType());
		final String[] SexArray = { "", "男", "女" };
		final String[] RoleArray = { "普通成员", "管理员" };
		List memberlist = memberService.findJoinGroup(group.getQzId());
		memberList.setModel(new ListModelList(memberlist));
		memberList.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				final QzMember member = (QzMember) arg1;
				Listcell c0 = new Listcell(arg0.getIndex() + 1 + "");
				WkTUser user = (WkTUser) memberService.get(WkTUser.class, member.getMbMember());
				Listcell c1 = new Listcell(user.getKuName());
				Listcell c2 = new Listcell(SexArray[Integer.parseInt(user.getKuSex().trim())]);
				Listcell c3 = new Listcell(user.getDept().getKdName());
				Listcell c4 = new Listcell(RoleArray[member.getMbRole()].toString());
				Listcell c5 = new Listcell();
				if (member.getMbRole() != 1) {
					InnerButton delete = new InnerButton("踢出成员");
					delete.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event arg0) throws Exception {
							if (Messagebox.show("是否真的要踢出该用户？", "提示", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
								memberService.delete(member);
								initWindow();
							}
						}
					});
					c5.appendChild(delete);
				} else {
					c5.setLabel("--");
				}
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
				arg0.appendChild(c5);
			}
		});
		List applylist = memberService.findApplyGroup(group.getQzId());
		applyList.setModel(new ListModelList(applylist));
		applyList.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				final QzMember member = (QzMember) arg1;
				Listcell c0 = new Listcell(arg0.getIndex() + 1 + "");
				WkTUser user = (WkTUser) memberService.get(WkTUser.class, member.getMbMember());
				Listcell c1 = new Listcell(user.getKuName());
				Listcell c2 = new Listcell(SexArray[Integer.parseInt(user.getKuSex().trim())]);
				Listcell c3 = new Listcell(user.getDept().getKdName());
				Listcell c4 = new Listcell();
				InnerButton agree = new InnerButton("同意");
				agree.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						member.setMbAccept(QzMember.ACCEPT_YES);
						member.setMbAgree(QzMember.AGREE_YES);
						member.setMbRole(QzMember.Normal);
						memberService.update(member);
						initWindow();
					}
				});
				InnerButton disagree = new InnerButton("拒绝");
				disagree.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						memberService.delete(member);
						initWindow();
					}
				});
				c4.appendChild(agree);
				c4.appendChild(new Label("/"));
				c4.appendChild(disagree);
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
			}
		});
		List mlist = memberService.findInviteMember(group.getQzId());
		inviteList.setModel(new ListModelList(mlist));
		inviteList.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				QzMember member = (QzMember) arg1;
				Listcell c0 = new Listcell();
				WkTUser user = (WkTUser) memberService.get(WkTUser.class, member.getMbMember());
				Listcell c1 = new Listcell(user.getKuName());
				Listcell c2 = new Listcell(user.getDept().getKdName());
				arg0.setValue(user);
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
			}
		});
	}

	public void setGroup(QzGroup group) {
		this.group = group;
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
		group.setQzName(name.getValue());
		group.setQzType(typeList.getSelectedIndex());
		group.setQzdescrib(descript.getValue());
		group.setQzPath(LogoPath);
		memberService.update(group);
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	public void onClick$query() {
		WkTDept dept = (WkTDept) deptSelect.getSelectedItem().getValue();
		List userlist = userService.findUserForGroupByKdIdAndName(memberService.findInviteMember(group.getQzId()),memberService.findJoinGroup(group.getQzId()),dept.getKdId(), userName.getValue(), teacherCheck.isChecked(), studentCheck.isChecked(), graduateCheck.isChecked());
		choiceList.setModel(new ListModelList(userlist));
		choiceList.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				WkTUser user = (WkTUser) arg1;
				WkTUser myself = (WkTUser) Sessions.getCurrent().getAttribute("user");
				Listcell c0 = new Listcell();
				Listcell c1 = new Listcell(user.getKuName());
				Listcell c2 = new Listcell(user.getDept().getKdName());
				arg0.setValue(user);
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				if (user.getKuId() == myself.getKuId().longValue()) {
					arg0.setVisible(false);
				}
			}
		});
	}

	public void onClick$invite() {
		while (choiceList.getSelectedItem() != null) {
			Listitem item = choiceList.getSelectedItem();
			WkTUser user = (WkTUser) item.getValue();
			WkTUser myself = (WkTUser) Sessions.getCurrent().getAttribute("user");
			if (user.getKuId() != myself.getKuId().longValue()) {
				QzMember member = new QzMember();
				member.setQzId(group.getQzId());
				member.setMbMember(user.getKuId());
				member.setMbAccept(QzMember.ACCEPT_NO);
				member.setMbAgree(QzMember.AGREE_NULL);
				member.setMbRole(QzMember.NULL);
				memberService.save(member);
				if (choiceList.getItemCount() != 0)
					inviteList.insertBefore(item, choiceList.getItemAtIndex(0));
				else
					item.setParent(inviteList);
			}
			item.setSelected(false);
		}
	}

	public void onClick$cancel() {
		while (inviteList.getSelectedItem() != null) {
			Listitem item = inviteList.getSelectedItem();
			WkTUser user = (WkTUser) item.getValue();
			List memberlist = memberService.findByQzIdAndKuId(group.getQzId(), user.getKuId());
			if (memberlist.size() != 0) {
				QzMember member = (QzMember) memberlist.get(0);
				memberService.delete(member);
			}
			if (inviteList.getItemCount() != 0)
				choiceList.insertBefore(item, inviteList.getItemAtIndex(0));
			else
				item.setParent(choiceList);
			item.setSelected(false);
		}
	}
}
