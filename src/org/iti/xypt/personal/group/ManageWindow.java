package org.iti.xypt.personal.group;

import java.util.List;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

import com.uniwin.asm.personal.entity.QzGroup;
import com.uniwin.asm.personal.entity.QzMember;
import com.uniwin.asm.personal.service.MemberService;
import com.uniwin.framework.entity.WkTUser;

public class ManageWindow extends Window implements AfterCompose {
	Rows groupRows;
	Button add, search, notice;
	MemberService memberService;
	WkTUser user = (WkTUser) Sessions.getCurrent().getAttribute("user");

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		initWindow();
	}

	public void initWindow() {
		String[] typeArray = { "��ͨȺ��", "��Ȥ����", "��֯����", "���ѽ���" };
		groupRows.getChildren().clear();
		List groupList = memberService.findGroup(user.getKuId(), QzMember.ACCEPT_YES, QzMember.AGREE_YES);
		for (int i = 0; i < groupList.size(); i++) {
			final QzMember member = (QzMember) groupList.get(i);
			final QzGroup group = (QzGroup) memberService.get(QzGroup.class, member.getQzId());
			Row newRow = new Row();
			newRow.setStyle("padding:0;border-width:medium 0px 1px");
			newRow.setValign("center");
			newRow.setHeight("90px");
			{
				Hbox hboxLeft = new Hbox();
				{
					Space spaceLeft = new Space();
					Image imageLogo = new Image();
					imageLogo.setHeight("80px");
					imageLogo.setSrc(group.getQzPath());
					imageLogo.setStyle("filter:Chroma(color=#ffffff)");
					hboxLeft.appendChild(spaceLeft);
					hboxLeft.appendChild(imageLogo);
				}
				Hbox hboxCenter = new Hbox();
				{
					Space spaceCenter = new Space();
					spaceCenter.setSpacing("25px");
					Vbox vboxCenter = new Vbox();
					{
						Label name = new Label("Ⱥ�����ƣ�" + group.getQzName() + " �� " + typeArray[group.getQzType()]);
						Label describe = new Label("������" + group.getQzdescrib());
						if (group.getQzdescrib().equals(null) || group.getQzdescrib().length() == 0)
							describe.setValue("����������");
						Label admin = new Label("Ⱥ����" + ((WkTUser) memberService.get(WkTUser.class, group.getQzUser())).getKuName());
						vboxCenter.appendChild(name);
						vboxCenter.appendChild(describe);
						vboxCenter.appendChild(admin);
					}
					hboxCenter.appendChild(spaceCenter);
					hboxCenter.appendChild(vboxCenter);
				}
				Vbox vboxRight = new Vbox();
				{
					Button memberButton = new Button();
					Button messageButton = new Button();
					Button deleteButton = new Button();
					memberButton.setImage("/admin/image/group/member.gif");
					messageButton.setImage("/admin/image/group/message.gif");
					deleteButton.setImage("/admin/image/group/delete.gif");
					if (group.getQzUser().equals(user.getKuId())) {
						memberButton.setLabel("����Ⱥ��");
						memberButton.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								final GroupWindow win = (GroupWindow) Executions.createComponents("/admin/personal/group/manage/group.zul", null, null);
								win.doHighlighted();
								win.setGroup(group);
								win.initWindow();
								win.addEventListener(Events.ON_CHANGE, new EventListener() {
									public void onEvent(Event arg0) throws Exception {
										initWindow();
										Messagebox.show("Ⱥ�������Ϣ����ɹ���", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
									}
								});
							}
						});
						messageButton.setLabel("Ⱥ����Ϣ");
						messageButton.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								List userList = memberService.findJoinGroupExceptMyself(group.getQzId(), user.getKuId(), 1);
								if (userList.size() != 0) {
									MessageWindow win = (MessageWindow) Executions.createComponents("/admin/personal/group/manage/message.zul", null, null);
									win.doHighlighted();
									win.addReceiver(userList);
								} else {
									Messagebox.show("��Ⱥ����û��������Ա���޷�Ⱥ����Ϣ��", "��ʾ", Messagebox.OK, Messagebox.EXCLAMATION);
								}
							}
						});
						deleteButton.setLabel("��ɢȺ��");
						deleteButton.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								if (Messagebox.show("�Ƿ����Ҫ��ɢ��Ⱥ�飿", "��ʾ", Messagebox.YES | Messagebox.NO, Messagebox.EXCLAMATION) == Messagebox.YES) {
									memberService.delete(group);
									memberService.deleteAll(memberService.findMemberInSameGroup(member.getQzId()));
									initWindow();
								}
							}
						});
					} else {
						memberButton.setLabel("�鿴��Ա");
						memberButton.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								MemberWindow win = (MemberWindow) Executions.createComponents("/admin/personal/group/manage/member.zul", null, null);
								win.doHighlighted();
								win.setGroup(group);
								win.initWindow();
							}
						});
						messageButton.setLabel("Ⱥ����Ϣ");
						messageButton.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								List userList = memberService.findJoinGroupExceptMyself(group.getQzId(), user.getKuId(), 1);
								if (userList.size() != 0) {
									MessageWindow win = (MessageWindow) Executions.createComponents("/admin/personal/group/manage/message.zul", null, null);
									win.doHighlighted();
									win.addReceiver(userList);
								} else {
									Messagebox.show("��Ⱥ����û��������Ա���޷�Ⱥ����Ϣ��", "��ʾ", Messagebox.OK, Messagebox.EXCLAMATION);
								}
							}
						});
						deleteButton.setLabel("�˳�Ⱥ��");
						deleteButton.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								if (Messagebox.show("�Ƿ����Ҫ�˳���Ⱥ�飿", "��ʾ", Messagebox.YES | Messagebox.NO, Messagebox.EXCLAMATION) == Messagebox.YES) {
									memberService.delete(member);
									initWindow();
								}
							}
						});
					}
					vboxRight.appendChild(memberButton);
					vboxRight.appendChild(messageButton);
					vboxRight.appendChild(deleteButton);
				}
				newRow.appendChild(hboxLeft);
				newRow.appendChild(hboxCenter);
				newRow.appendChild(vboxRight);
			}
			groupRows.appendChild(newRow);
		}
		List flaglist = memberService.findGroup(user.getKuId(), QzMember.ACCEPT_NO, QzMember.AGREE_NULL);
		if (flaglist.size() == 0)
			notice.setVisible(false);
	}

	public void onClick$add() {
		final AddNewWindow win = (AddNewWindow) Executions.createComponents("/admin/personal/group/manage/addnew.zul", null, null);
		win.doHighlighted();
		win.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				initWindow();
				win.detach();
			}
		});
	}

	public void onClick$search() {
		final SearchWindow win = (SearchWindow) Executions.createComponents("/admin/personal/group/manage/search.zul", null, null);
		win.doHighlighted();
		win.setKuId(user.getKuId());
		win.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				initWindow();
				win.detach();
			}
		});
	}

	public void onClick$notice() {
		final InviteWindow win = (InviteWindow) Executions.createComponents("/admin/personal/group/manage/invite.zul", null, null);
		win.doHighlighted();
		win.setKuId(user.getKuId());
		win.initWindow();
		win.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				initWindow();
			}
		});
	}
}
