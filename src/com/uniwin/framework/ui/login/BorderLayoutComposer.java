package com.uniwin.framework.ui.login;

/**
 * <li>功能描述：负责定义系统维护页面布局，初始化系统一级、二级和三级菜单,与admin/index.zul对应
 */
import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.iti.bysj.service.BatchService;
import org.iti.bysj.service.BsTeacherService;
import org.iti.bysj.service.GpunitService;
import org.iti.bysj.service.PhaseService;
import org.iti.bysj.ui.base.InnerButton;
import org.iti.xypt.personal.message.MessageReceivePanel;
import org.iti.xypt.personal.message.MessageReceiveWindow;
import org.iti.xypt.personal.newsaudit.InfoShowWindow;
import org.iti.xypt.personal.notice.NoticeReceivePanel;
import org.iti.xypt.personal.notice.NoticeReceiveWindow;
import org.iti.xypt.service.XyUserRoleService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.DesktopUnavailableException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Menu;
import org.zkoss.zul.Menubar;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Panelchildren;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zkex.zul.West;

import com.uniwin.asm.personal.entity.QzGroup;
import com.uniwin.asm.personal.entity.QzMember;
import com.uniwin.asm.personal.entity.QzRelation;
import com.uniwin.asm.personal.entity.QzSubject;
import com.uniwin.asm.personal.entity.WkTUsrfav;
import com.uniwin.asm.personal.group.discuss.MessageWindow;
import com.uniwin.asm.personal.service.GroupService;
import com.uniwin.asm.personal.service.MemberService;
import com.uniwin.asm.personal.service.RelationService;
//import com.uniwin.asm.personal.service.SubjectService;
import com.uniwin.asm.personal.service.UsrfavService;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTTitle;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.service.TitleService;
import com.uniwin.framework.service.UserService;

public class BorderLayoutComposer extends Window implements AfterCompose {
	private static Log log=LogFactory.getLog(BorderLayoutComposer.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 维护页面中的标签页组件
	Tab centerTab;
	Tabbox centerTabbox;
	Textbox kufName;
	Image Logo;
	// 页面中左侧导航条部分对应对象
	West westLeft;
	// 页面中对应的一级菜单和二级菜单
	Menubar onebar;
	// 标题数据访问接口
	TitleService titleService;

	ListModelList infoListModel;
	// 页面左侧用来显示树的面板
	Panelchildren leftPanel;
	// 页面底下左侧的用户登陆时间及上次登陆IP显示
	Label loginTime, lastIP;
	Menuitem loguser, loginOut;
	// session中存储登陆用户的权限列表,在登陆组件中设置的
	List<WkTTitle> ulist;
	// 用户所属部门及下级部门id列表，备各个子模块使用
	List<Long> userDeptList = new ArrayList<Long>();
	DepartmentService departmentService;
	WkTUser user;
	// 标题和自定义列表框
	//private Listbox TitleListbox, DiyListbox;
	//private ListModelList TitleusrfavList, DiyusrfavList;
	boolean showleft = false;
	// 2010年9月14日10:01:00 新增加新通知和消息的提示
	NoticeReceivePanel noticePanel;
	MessageReceivePanel messagePanel;
	private UsrfavService usrfavService;
	private MemberService memberService;
	//private SubjectService subjectService;
	private RelationService relationService;
	private GroupService groupService;
	private UserService userService;
	Listbox groupList, worklist, netlist;
	private BsTeacherService bsteacherService;
	private XyUserRoleService xyUserRoleService;
	private PhaseService bsphaseService;
	private GpunitService gpunitService;
	private BatchService batchService;
    Iframe iframe;

	@SuppressWarnings("unchecked")
	public void afterCompose() {
		Components.wireController(this, this);
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		ulist = (List<WkTTitle>) Sessions.getCurrent().getAttribute("ulist");
		addUserDept(user.getKdId());
		Sessions.getCurrent().setAttribute("userDeptList", userDeptList);
		// 获得一级标题列表
		List<WkTTitle> olist = titleService.getChildTitle(Long.parseLong("1"));
		// 初始化一级标题
		for (int i = 0; i < olist.size(); i++) {
			WkTTitle title = olist.get(i);
			// 判断用户是否有此权限显示此标题
			if (checkTitle(title)) {
				Menu menu = new Menu();
				menu.setLabel(title.getKtName());
				onebar.appendChild(menu);
				// 添加点击一级标题显示二级标题事件监听
				Menupopup menpop = new Menupopup();
				menu.appendChild(menpop);
				appendMenuItem(menpop, title.getKtId());
			}
		}
		// loginOut.setParent(onebar);
		// Date d = new Date();
		// loginTime.setValue("今天是:" + ConvertUtil.convertDateAndTimeString(d.getTime()));
		DateFormat datetime1 = DateFormat.getDateInstance(DateFormat.LONG, Locale.CHINA);
		DateFormat datetime2 = DateFormat.getTimeInstance(DateFormat.LONG, Locale.CHINA);
		String datetime = datetime1.format(new Date()) + " " + datetime2.format(new Date());
		loginTime.setValue("现在时间是:" + " " + datetime);
		String lastip = (String) Sessions.getCurrent().getAttribute("lastIP");
		lastIP.setValue("上次登陆IP:" + lastip);
		Sessions.getCurrent().removeAttribute("lastIP");
		loguser.setLabel(user.getKuName().trim() + "　您好，欢迎登陆！");
		File fileLogo = new File(this.getDesktop().getWebApp().getRealPath("/logo/" + user.getKdId() + "L.jpg"));
		log.info("所属部门：" + user.getKdId());
	/**
	 * 需要改过来
	 */
		if (fileLogo.exists()) {
			Logo.setSrc("/logo/" + user.getKdId() + "L.jpg");
		} else {
			WkTDept dept = (WkTDept) departmentService.get(WkTDept.class, user.getKdId());
			log.info("所属部门父部门：" + dept.getKdPid());
			File filePlogo = new File(this.getDesktop().getWebApp().getRealPath("/logo/" + dept.getKdPid() + "L.jpg"));
			File fileSlogo=new File(this.getDesktop().getWebApp().getRealPath("/logo/" + dept.getKdSchid() + "L.jpg"));
			if (filePlogo.exists())
				Logo.setSrc("/logo/" + dept.getKdPid() + "L.jpg");
			else if(fileSlogo.exists())
				Logo.setSrc("/logo/" + dept.getKdSchid() + "L.jpg");
			else
				Logo.setSrc("/logo/0L.jpg");
		}
		
		initNoticeAlert();
		final Desktop desktop = Executions.getCurrent().getDesktop();
		if (desktop.isServerPushEnabled()) {
			// System.out.println("zhichia");
		} else {
			// System.out.println("no zhichia");
			desktop.enableServerPush(true);
			new NoticeAlert(noticePanel, messagePanel, centerTab).start();
		}
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");

		worklist.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				arg0.setValue(arg1);
				final WkTUsrfav node = (WkTUsrfav) arg1;
				Listcell lc = new Listcell();
				arg0.appendChild(lc);
				final InnerButton inb = new InnerButton(node.getKufName());
				inb.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						//System.out.println(node.getKtId());
						if(node.getKtId().trim().equalsIgnoreCase("0")){
							inb.setHref(node.getKufUrl());
							inb.setTarget("_blank");
						}else{
							Long tid = Long.parseLong(node.getKtId());
							WkTTitle tt = (WkTTitle) titleService.get(WkTTitle.class, tid);
							centerTabbox.setAttribute("tid", tt.getKtId());
							westLeft.setTitle(tt.getKtName());
							westLeft.getChildren().clear();
							if (titleService.getChildTitle(tt.getKtId()).size() == 0) {
								// 如果没有下级标题，则代表当前要在左侧打开某个操作菜单树，应为某个菜单zul页面
								Executions.createComponents(tt.getKtContent() + "index.zul", westLeft, null);
							} else {
								if (tt.getKtType().trim().equalsIgnoreCase(WkTTitle.TYPE_ZD)) {
									Executions.createComponents(tt.getKtContent() + "index.zul", westLeft, null);
								} else {
									Executions.createComponents("/admin/left.zul", westLeft, null);
								}
							}
						}
						
					}
				});
				lc.appendChild(inb);
				
			}
		});
		List network=new ArrayList();
		List listnet=usrfavService.getUsrfavDiyList(user.getKuId());
		List listwork=usrfavService.getUsrfavTitleList(user.getKuId());
		network.addAll(listnet);
		network.addAll(listwork);
		
		worklist.setModel(new ListModelList(network));
		loadGroupList();
		iframe.setSrc("/com/uniwin/framework/ui/login/notice.do?action=notice&kuid="+user.getKuId());
	}
//等待进一步修改
	@SuppressWarnings("unchecked")
	private void loadGroupList() {
		@SuppressWarnings("rawtypes")
		List grouplist = memberService.findGroup(user.getKuId(), QzMember.ACCEPT_NO, QzMember.AGREE_NULL);
		List<QzGroup> glist = groupService.findMyGroup(user.getKuId());
		for (int i = 0; i < glist.size(); i++) {
			QzGroup group = (QzGroup) glist.get(i);
			grouplist.addAll(memberService.findApplyGroup(group.getQzId()));
		}
		grouplist.addAll(relationService.findRelation(user.getKuId(), (short) 2));
		grouplist.addAll(relationService.findRelation(user.getKuId(), (short) 0));
		groupList.setModel(new ListModelList(grouplist));
		groupList.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception {
				item.setValue(data);
				item.setHeight("25px");
				Listcell c1 = new Listcell();
				Listcell c2 = new Listcell();
				Listcell c3 = new Listcell();
				if (data instanceof QzMember) {
					final QzMember member = (QzMember) data;
					c1.setImage("/images/notice_new.gif");
					QzGroup group = (QzGroup) memberService.get(QzGroup.class, member.getQzId());
					if (member.getMbAccept() == QzMember.ACCEPT_NO.shortValue()) {
						WkTUser user = (WkTUser) memberService.get(WkTUser.class, group.getQzUser());
						Label front = new Label("收到");
						Label sender = new Label(user.getKuName());
						sender.setStyle("color:blue");
						Label back = new Label("发来的邀请");
						Hbox hbox = new Hbox();
						item.setTooltiptext("群主名称：" + user.getKuName().toString().trim() + "\n" + "所属部门：" + user.getDept().getKdName().toString().trim() + "\n" + "群组名称：" + group.getQzName().toString().trim());
						hbox.appendChild(front);
						hbox.appendChild(sender);
						hbox.appendChild(back);
						c2.appendChild(hbox);
						InnerButton accept = new InnerButton("接受");
						accept.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								member.setMbAccept(QzMember.ACCEPT_YES);
								member.setMbAgree(QzMember.AGREE_YES);
								member.setMbRole(QzMember.Normal);
								memberService.update(member);
								Messagebox.show("接受邀请成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
								loadGroupList();
							}
						});
						InnerButton refuse = new InnerButton("拒绝");
						refuse.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								if (Messagebox.show("确定要拒绝加入吗！", "提示", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
									memberService.delete(member);
									loadGroupList();
								}
							}
						});
						Label depart = new Label("/ ");
						depart.setStyle("color:blue");
						c3.appendChild(accept);
						c3.appendChild(depart);
						c3.appendChild(refuse);
					} else if (member.getMbAgree() == QzMember.AGREE_NO.shortValue()) {
						WkTUser user = (WkTUser) memberService.get(WkTUser.class, member.getMbMember());
						Label apply = new Label(user.getKuName());
						apply.setStyle("color:blue");
						Label center = new Label("申请加入");
						Label host = new Label(group.getQzName());
						host.setStyle("color:blue");
						Hbox hbox = new Hbox();
						hbox.appendChild(apply);
						hbox.appendChild(center);
						hbox.appendChild(host);
						c2.appendChild(hbox);
						InnerButton accept = new InnerButton("接受");
						accept.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								member.setMbAccept(QzMember.ACCEPT_YES);
								member.setMbAgree(QzMember.AGREE_YES);
								member.setMbRole(QzMember.Normal);
								memberService.update(member);
								Messagebox.show("同意该用户加入此群组！", "提示", Messagebox.OK, Messagebox.INFORMATION);
								loadGroupList();
							}
						});
						InnerButton refuse = new InnerButton("拒绝");
						refuse.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								if (Messagebox.show("确定要拒绝加入申请吗！", "提示", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
									memberService.delete(member);
									loadGroupList();
								}
							}
						});
						Label depart = new Label("/ ");
						depart.setStyle("color:blue");
						c3.appendChild(accept);
						c3.appendChild(depart);
						c3.appendChild(refuse);
					} else {
						log.error("群组部分数据异常，出错了!!!");
					}
				} else {
					final QzRelation relation = (QzRelation) data;
					Image img = new Image();
					Label newTopic = new Label();
					// img.setSrc("/images/group.png");
					// img.setStyle("filter:Chroma(color=#d4e6ea)");
					if (relation.getRlState() == (short) 0) {
						img.setSrc("/images/ims.news.gif");
						newTopic.setValue("[新话题] ");
					} else if (relation.getRlState() == (short) 2){
						img.setSrc("/images/ims.news.gif");
						newTopic.setValue("[回复话题] ");
					}else {
						img.setSrc("/images/ims.readed.gif");
						newTopic.setValue("[已读话题] ");
					}
					c1.appendChild(img);
					final QzSubject subject = (QzSubject) memberService.get(QzSubject.class, relation.getSjId());
					QzGroup group = (QzGroup) memberService.get(QzGroup.class, subject.getQzId());
					WkTUser user = (WkTUser) memberService.get(WkTUser.class, group.getQzUser());
					String title = subject.getSjTitle();
					InnerButton ibtn = new InnerButton(title);
					ibtn.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event arg0) throws Exception {
							relation.setRlState((short) 1);
							relation.setRlShow((short) 1);
							memberService.update(relation);
							Map<String, QzSubject> mapTList = new HashMap<String, QzSubject>();
							mapTList.put("subject", subject);
							MessageWindow win = (MessageWindow) Executions.createComponents("/admin/personal/group/discuss/message.zul", null, mapTList);
							win.doHighlighted();
							loadGroupList();
						}
					});
					c2.appendChild(newTopic);
					c2.appendChild(ibtn);
					item.setTooltiptext("群主名称：" + user.getKuName().toString().trim() + "\n" + "所属部门：" + user.getDept().getKdName().toString().trim() + "\n" + "群组名称：" + group.getQzName().toString().trim());
					InnerButton noshow = new InnerButton("不再显示");
					noshow.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event arg0) throws Exception {
							relation.setRlShow((short) 1);
							memberService.update(relation);
							loadGroupList();
						}
					});
					c3.appendChild(noshow);
				}
				item.appendChild(c1);
				item.appendChild(c2);
				item.appendChild(c3);
			}
		});
		
	}
  /**
   * 需要改过来
   */
	private void initNoticeAlert() {
		if (messagePanel.isNewNotice() || noticePanel.isNewNotice()) {
			centerTab.setImage("/images/notice_new.gif");
		} else {
			centerTab.setImage(null);
		}
	}

//	private void initNoticeList() {
//		messagePanel.reloadGrid();
//		noticePanel.reloadGrid();
//		initNoticeAlert();
//	}

	public void onClick$loginOut() {
		user.setKuAutoenter("0");
		userService.update(user);
		Executions.getCurrent().sendRedirect("/admin/login.zul");
	}

	/**
	 * <li>功能描述：显示一级标题tid所属二级标题
	 */
	public void appendMenuItem(Menupopup menpop, Long tid) {
		List<WkTTitle> olist = titleService.getChildTitle(tid);
		for (int i = 0; i < olist.size(); i++) {
			WkTTitle title = (WkTTitle) olist.get(i);
			if (checkTitle(title)) {
				Menuitem tit = new Menuitem();
				tit.setLabel(title.getKtName());
				tit.setAttribute("t_id", title.getKtId());
				menpop.appendChild(tit);
				tit.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						Menuitem it = (Menuitem) arg0.getTarget();
						appendLeftTree(it);
					}
				});
				if (!showleft) {
					appendLeftTree(tit);
					showleft = true;
				}
			}
		}
	}

	/**
	 * <li>功能描述：显示二级标题时候默认显示三级标题
	 * 
	 * @param it 二级标题的菜单项 void
	 * @author DaLei
	 */
	public void appendLeftTree(Menuitem it) {
		Long tid = (Long) it.getAttribute("t_id");
		centerTabbox.setAttribute("tid", tid);
		westLeft.setTitle(it.getLabel());
		westLeft.getChildren().clear();
		WkTTitle title = (WkTTitle) titleService.get(WkTTitle.class, tid);
		if (titleService.getChildTitle(tid).size() == 0) {
			// 如果没有下级标题，则代表当前要在左侧打开某个操作菜单树，应为某个菜单zul页面
			Executions.createComponents(title.getKtContent() + "index.zul", westLeft, null);
		} else {
			if (title.getKtType().trim().equalsIgnoreCase(WkTTitle.TYPE_ZD)) {
				Executions.createComponents(title.getKtContent() + "index.zul", westLeft, null);
			} else {
				Executions.createComponents("/admin/left.zul", westLeft, null);
			}
		}
	}

	/**
	 * <li>功能描述：显示判断用户是否有此标题权限
	 */
	public boolean checkTitle(WkTTitle title) {
		boolean flag = false;
		for (int j = 0; j < ulist.size(); j++) {
			WkTTitle ti = ulist.get(j);
			if (ti.getKtId().intValue() == title.getKtId().intValue()) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	public void addUserDept(Long did) {
		userDeptList.add(did.longValue());
		List<WkTDept> cdlist = departmentService.getChildDepartment(did, WkTDept.QUANBU);
		for (int i = 0; i < cdlist.size(); i++) {
			WkTDept d = (WkTDept) cdlist.get(i);
			addUserDept(d.getKdId());
		}
	}

	private static class NoticeAlert extends Thread {
		NoticeReceivePanel noticePanel;
		MessageReceivePanel messagePanel;
		Tab centerTab;
		private final Desktop _desktop;

		private NoticeAlert(NoticeReceivePanel noticePanel, MessageReceivePanel messagePanel, Tab centertab) {
			this.centerTab = centertab;
			this.noticePanel = noticePanel;
			this.messagePanel = messagePanel;
			this._desktop = centertab.getDesktop();
		}

		public void run() {
			while (true) {
				try {
					Thread.sleep(1000 * 30);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (centerTab.getDesktop() == null || !_desktop.isServerPushEnabled()) {
					_desktop.enableServerPush(false);
					return;
				}
				try {
					Executions.activate(_desktop);
					initNoticeList();
				} catch (DesktopUnavailableException e) {
					log.info(e);
				} catch (InterruptedException e) {
					log.info(e);
				} finally {
					Executions.deactivate(_desktop);
				}
			}
		}

		private void initNoticeAlert() {
			if (messagePanel.isNewNotice() || noticePanel.isNewNotice()) {
				centerTab.setImage("/images/notice_new.gif");
			} else {
				centerTab.setImage(null);
			}
		}

		private void initNoticeList() {
			messagePanel.reloadGrid();
			noticePanel.reloadGrid();
			initNoticeAlert();
		}
	}

	public void onChange$noticePanel() {
		NoticeReceiveWindow win = (NoticeReceiveWindow) Executions.createComponents("/admin/personal/notice/receive/index.zul", null, null);
		win.doHighlighted();
		win.setClosable(true);
		win.setTitle("查看通知");
		win.setBorder("normal");
	}

	public void onChange$messagePanel() {
		MessageReceiveWindow win = (MessageReceiveWindow) Executions.createComponents("/admin/personal/message/receive/index.zul", null, null);
		win.doHighlighted();
		win.setClosable(true);
		win.setTitle("查看消息");
		win.setBorder("normal");
	}
	public void onChange$infopanel() {
		InfoShowWindow win = (InfoShowWindow) Executions.createComponents("/admin/personal/message/send/infoshow.zul", null, null);
		win.doHighlighted();
		win.setClosable(true);
		win.setTitle("查看新闻");
		win.setBorder("normal");
	}
}
