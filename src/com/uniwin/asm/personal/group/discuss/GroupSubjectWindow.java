package com.uniwin.asm.personal.group.discuss;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

import com.uniwin.asm.personal.entity.QzGroup;
import com.uniwin.asm.personal.entity.QzMessage;
import com.uniwin.asm.personal.entity.QzRelation;
import com.uniwin.asm.personal.entity.QzSubject;
import com.uniwin.asm.personal.service.GroupService;
import com.uniwin.asm.personal.service.MemberService;
import com.uniwin.asm.personal.service.QzMessageService;
import com.uniwin.asm.personal.service.RelationService;
import com.uniwin.asm.personal.service.SubjectService;
import com.uniwin.common.util.ConvertUtil;
import com.uniwin.framework.entity.WkTUser;

public class GroupSubjectWindow extends Window implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GroupService groupService;
	private SubjectService subjectService;
	private QzMessageService qzMessageService;
	private RelationService relationService;
	private Grid subject, group, reply;
	//private Tab selectGroupTopic, selectMyTopic, selectMyReply;
	private Tabpanel groupTopic;
	private WkTUser user;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		subjectService = (SubjectService) SpringUtil.getBean("subjectService");
		qzMessageService = (QzMessageService) SpringUtil.getBean("qzMessageService");
		initSow();
	}

	public void initSow() {
		group.setVisible(true);
		subject.setVisible(true);
		List glist = groupService.findgroupBykuid(user.getKuId());
		group.setModel(new ListModelList(glist));
		List slist = subjectService.findByKuid(user.getKuId());
		subject.setModel(new ListModelList(slist));
		List rlist = qzMessageService.findMyReply(user.getKuId());
		reply.setModel(new ListModelList(rlist));
		group.setRowRenderer(new RowRenderer() {
			public void render(Row arg0, Object arg1) throws Exception {
				final QzGroup qg = (QzGroup) arg1;
				arg0.setValue(qg);
				arg0.setStyle("padding:0;border-width:medium 0px 1px");
				arg0.setValign("center");
				arg0.setHeight("90px");
				Image img = new Image();
				img.setWidth("80px");
				img.setHeight("80px");
				img.setStyle("filter:Chroma(color=#ffffff)");
				// 群组图片
				if (qg.getQzPath() != null) {
					img.setSrc(qg.getQzPath());
				} else {
					img.setSrc("/admin/image/group/intro_friend.gif");
				}
				// 群组信息
				Vbox vb = new Vbox();
				Toolbarbutton ib = new Toolbarbutton();
				ib.setLabel(qg.getQzName());
				ib.setStyle("font-family:'宋体';color:blue;font-size:14px");
				ib.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						Map mapTList = new HashMap();
						mapTList.put("qzid", qg.getQzId());
						GroupMessageWindow qmw = (GroupMessageWindow) Executions.createComponents("/admin/personal/group/discuss/groupmessage.zul", null, mapTList);
						groupTopic.appendChild(qmw);
						qmw.ReloadMgrid();
						group.setVisible(false);
						qmw.addEventListener(Events.ON_CHANGE, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								initSow();
							}
						});
					}
				});
				Label l1 = new Label(qg.getQzdescrib());
				Hbox hb = new Hbox();
				Label l2 = new Label("创建者: " + qg.getUser().getKuName());
				Label l3 = new Label(ConvertUtil.convertDateAndTimeString(qg.getQzTime()));
				l3.setStyle("color:gray");
				hb.appendChild(l2);
				hb.appendChild(l3);
				vb.appendChild(ib);
				vb.appendChild(l1);
				vb.appendChild(hb);
				// 群组最近主题
				Vbox sb = new Vbox();
				List slist = subjectService.findByQzid(qg.getQzId());
				if (slist != null && slist.size() > 0) {
					int max = 0;
					if (slist.size() < 4)
						max = slist.size();
					else
						max = 4;
					for (int i = 0; i < max; i++) {
						final QzSubject QS = (QzSubject) slist.get(i);
						Hbox HB = new Hbox();
						Toolbarbutton t = new Toolbarbutton(QS.getSjTitle());
						t.setStyle("color:blue");
						t.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								Map mapTList = new HashMap();
								mapTList.put("subject", QS);
								MessageWindow mw = (MessageWindow) Executions.createComponents("/admin/personal/group/discuss/message.zul", null, mapTList);
								mw.doHighlighted();
								mw.addEventListener(Events.ON_CHANGE, new EventListener() {
									public void onEvent(Event arg0) throws Exception {
										initSow();
									}
								});
							}
						});
						Label time = new Label();
						if (QS.getSjTime() != null) {
							time.setValue(ConvertUtil.convertDateAndTimeString(QS.getSjTime()));
						}
						time.setStyle("color:gray");
						HB.appendChild(t);
						HB.appendChild(time);
						sb.appendChild(HB);
					}
				}
				arg0.appendChild(img);
				arg0.appendChild(vb);
				arg0.appendChild(sb);
			}
		});
		subject.setRowRenderer(new RowRenderer() {
			public void render(Row arg0, Object arg1) throws Exception {
				final QzSubject qs = (QzSubject) arg1;
				arg0.setValue(qs);
				arg0.setStyle("padding:0;border-width:medium 0px 0px");
				Image image = new Image();
				if (qs.getSjImg() == 0 || qs.getSjImg() == null) {
					image.setSrc("/admin/image/group/icon1.gif");
				} else {
					image.setSrc("/admin/image/group/icon" + qs.getSjImg() + ".gif");
				}
				image.setWidth("16px");
				image.setHeight("16px");
				arg0.setHeight("30px");
				Toolbarbutton title = new Toolbarbutton(qs.getSjTitle());
				title.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						Map mapTList = new HashMap();
						mapTList.put("subject", qs);
						MessageWindow mw = (MessageWindow) Executions.createComponents("/admin/personal/group/discuss/message.zul", null, mapTList);
						mw.doHighlighted();
						mw.addEventListener(Events.ON_CHANGE, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								initSow();
							}
						});
					}
				});
				title.setStyle("color:blue");
				final QzGroup group = (QzGroup) groupService.get(QzGroup.class, qs.getQzId());
				Label groupName = new Label(group.getQzName());
				final List mlist = qzMessageService.findBtSjid(qs.getSjId(), Short.parseShort("1"));
				Hbox lastReply = new Hbox();
				lastReply.setAlign("center");
				if (mlist != null && mlist.size() > 0) {
					QzMessage qm = (QzMessage) mlist.get(0);
					Label name = new Label(qm.getUser().getKuName());
					Label time = new Label(ConvertUtil.convertDateAndTimeString(qm.getMgTime()));
					time.setStyle("color:gray");
					lastReply.appendChild(name);
					lastReply.appendChild(time);
				} else {
					Label name = new Label(qs.getUser().getKuName());
					Label time = new Label(ConvertUtil.convertDateAndTimeString(qs.getSjTime()));
					time.setStyle("color:gray");
					lastReply.appendChild(name);
					lastReply.appendChild(time);
				}
				Hbox operate = new Hbox();
				Toolbarbutton edit = new Toolbarbutton("编辑");
				edit.setStyle("color:blue");
				edit.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						Map mapTList = new HashMap();
						mapTList.put("subject", qs);
						EditSubjectWindow esw = (EditSubjectWindow) Executions.createComponents("/admin/personal/group/discuss/editmessage.zul", null, mapTList);
						esw.doHighlighted();
						esw.initWindow();
						esw.addEventListener(Events.ON_CHANGE, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								initSow();
							}
						});
					}
				});
				Toolbarbutton delete = new Toolbarbutton("删除");
				delete.setStyle("color:blue");
				delete.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						if (Messagebox.show("确定要删除该条主题吗？", "询问", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK) {
							for (int j = 0; j < mlist.size(); j++) {
								QzMessage qm = (QzMessage) mlist.get(j);
								deleteFile(qm.getMgPath());
								qzMessageService.delete(qm);
							}
							relationService.deleteAll(relationService.findMemberBySubject(qs.getSjId(), false));
							subjectService.delete(qs);
							deleteFile(qs.getSjPath());
							initSow();
						} else {
							return;
						}
					}
				});
				operate.appendChild(edit);
				operate.appendChild(delete);
				arg0.appendChild(image);
				arg0.appendChild(title);
				arg0.appendChild(groupName);
				arg0.appendChild(lastReply);
				arg0.appendChild(operate);
			}
		});
		reply.setRowRenderer(new RowRenderer() {
			public void render(Row arg0, Object arg1) throws Exception {
				final QzSubject qs = (QzSubject) arg1;
				arg0.setValue(qs);
				arg0.setStyle("padding:0;border-width:medium 0px 0px");
				Image image = new Image();
				if (qs.getSjImg() == 0 || qs.getSjImg() == null) {
					image.setSrc("/admin/image/group/icon1.gif");
				} else {
					image.setSrc("/admin/image/group/icon" + qs.getSjImg() + ".gif");
				}
				image.setWidth("16px");
				image.setHeight("16px");
				arg0.setHeight("30px");
				Toolbarbutton title = new Toolbarbutton(qs.getSjTitle());
				title.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						Map mapTList = new HashMap();
						mapTList.put("subject", qs);
						MessageWindow mw = (MessageWindow) Executions.createComponents("/admin/personal/group/discuss/message.zul", null, mapTList);
						mw.doHighlighted();
						mw.addEventListener(Events.ON_CHANGE, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								initSow();
							}
						});
					}
				});
				title.setStyle("color:blue");
				final QzGroup group = (QzGroup) groupService.get(QzGroup.class, qs.getQzId());
				Label groupName = new Label(group.getQzName());
				final List mlist = qzMessageService.findBtSjid(qs.getSjId(), Short.parseShort("1"));
				Hbox lastReply = new Hbox();
				lastReply.setAlign("center");
				if (mlist != null && mlist.size() > 0) {
					QzMessage qm = (QzMessage) mlist.get(0);
					Label time = new Label(ConvertUtil.convertDateAndTimeString(qm.getMgTime()));
					time.setStyle("color:gray");
					lastReply.appendChild(time);
				} else {
					Label time = new Label(ConvertUtil.convertDateAndTimeString(qs.getSjTime()));
					time.setStyle("color:gray");
					lastReply.appendChild(time);
				}
				arg0.appendChild(image);
				arg0.appendChild(title);
				arg0.appendChild(groupName);
				arg0.appendChild(lastReply);
			}
		});
	}

	public void deleteFile(String Path) {
		if (Path != null && Path.length() != 0) {
			String RealPath = this.getDesktop().getWebApp().getRealPath(Path);
			File file = new File(RealPath);
			if (file.exists())
				file.delete();
		}
	}

	public void onClick$selectGroupTopic() {
		initSow();
	}

	public void onClick$selectMyTopic() {
		initSow();
	}

	public void onClick$selectMyReply() {
		initSow();
	}
}
