package com.uniwin.framework.ui.userlogin;

/**
 * <li>����������������ϵͳά��ҳ�沼�֣���ʼ��ϵͳһ���������������˵�,��admin/index.zul��Ӧ
 */
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.iti.xypt.personal.infoCollect.MessageCenter.InfoShowWindow;

import org.iti.xypt.personal.message.MessageReceivePanel;
import org.iti.xypt.personal.message.MessageReceiveWindow;


import org.iti.xypt.personal.notice.NoticeReceivePanel;
import org.iti.xypt.personal.notice.NoticeReceiveWindow;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.DesktopUnavailableException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Menu;
import org.zkoss.zul.Menubar;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.Panelchildren;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zkex.zul.West;

import com.uniwin.asm.personal.entity.QzGroup;
import com.uniwin.asm.personal.entity.QzMember;
import com.uniwin.asm.personal.service.GroupService;
import com.uniwin.asm.personal.service.MemberService;
import com.uniwin.asm.personal.service.RelationService;
import com.uniwin.asm.personal.service.UsrfavService;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTTitle;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.service.TitleService;
import com.uniwin.framework.service.UserService;

public class BorderLayoutComposer1 extends Window implements AfterCompose {
	private static Log log=LogFactory.getLog(BorderLayoutComposer1.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// ά��ҳ���еı�ǩҳ���
	Tab centerTab;
	Tabbox centerTabbox;
	Textbox kufName;
//	Image Logo;
	// ҳ������ർ�������ֶ�Ӧ����
	West westLeft;
	// ҳ���ж�Ӧ��һ���˵��Ͷ����˵�
	Menubar onebar;
	// �������ݷ��ʽӿ�
	TitleService titleService;

	ListModelList infoListModel;
	// ҳ�����������ʾ�������
	Panelchildren leftPanel;
	// ҳ����������û���½ʱ�估�ϴε�½IP��ʾ
	Label loginTime, lastIP;
	Menuitem loguser, loginOut;
	// session�д洢��½�û���Ȩ���б�,�ڵ�½��������õ�
	List<WkTTitle> ulist;
	// �û��������ż��¼�����id�б���������ģ��ʹ��
	List<Long> userDeptList = new ArrayList<Long>();
	DepartmentService departmentService;
	WkTUser user;
	// ������Զ����б��
	boolean showleft = false;
	// 2010��9��14��10:01:00 ��������֪ͨ����Ϣ����ʾ
	NoticeReceivePanel noticePanel;
	MessageReceivePanel messagePanel;
	private UsrfavService usrfavService;
	private MemberService memberService;
	//private SubjectService subjectService;
	private RelationService relationService;
	private GroupService groupService;
	private UserService userService;
	Listbox groupList, worklist, netlist;

	@SuppressWarnings("unchecked")
	public void afterCompose() {
		Components.wireController(this, this);
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		ulist = (List<WkTTitle>) Sessions.getCurrent().getAttribute("ulist");
		addUserDept(user.getKdId());
		Sessions.getCurrent().setAttribute("userDeptList", userDeptList);
		Sessions.getCurrent().setAttribute("centerTabbox",centerTabbox);
		Sessions.getCurrent().setAttribute("westLeft",westLeft);
		// ���һ�������б�
		List<WkTTitle> olist = titleService.getChildTitle(Long.parseLong("1"));
		// ��ʼ��һ������
		for (int i = 0; i < olist.size(); i++) {
			WkTTitle title = olist.get(i);
			// �ж��û��Ƿ��д�Ȩ����ʾ�˱���
			if (checkTitle(title)) {
				Menu menu = new Menu();
				menu.setLabel(title.getKtName());
				onebar.appendChild(menu);
				// ��ӵ��һ��������ʾ���������¼�����
				Menupopup menpop = new Menupopup();
				menu.appendChild(menpop);
				appendMenuItem(menpop, title.getKtId());
			}
		}
		DateFormat datetime1 = DateFormat.getDateInstance(DateFormat.LONG, Locale.CHINA);
		DateFormat datetime2 = DateFormat.getTimeInstance(DateFormat.LONG, Locale.CHINA);
		String datetime = datetime1.format(new Date()) + " " + datetime2.format(new Date());
		loginTime.setValue("����ʱ����:" + " " + datetime);
		String lastip = (String) Sessions.getCurrent().getAttribute("lastIP");
		lastIP.setValue("�ϴε�½IP:" + lastip);
		Sessions.getCurrent().removeAttribute("lastIP");
		loguser.setLabel(user.getKuName().trim() + "�����ã���ӭ��¼��");
		log.info("�������ţ�" + user.getKdId());
		 /**
		  * ��Ҫ�Ĺ���
		  */
		/*initNoticeAlert();*/
		final Desktop desktop = Executions.getCurrent().getDesktop();
		if (desktop.isServerPushEnabled()) {
		} else {
			desktop.enableServerPush(true);
			new NoticeAlert(noticePanel, messagePanel, centerTab).start();
		}
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
  /**
   * ��Ҫ�Ĺ���
   */
		List network=new ArrayList();
		List listnet=usrfavService.getUsrfavDiyList(user.getKuId());
		List listwork=usrfavService.getUsrfavTitleList(user.getKuId());
		network.addAll(listnet);
		network.addAll(listwork);
	/**
	 * ��Ҫ�Ĺ���
	 */
		loadGroupList();
	}
//�ȴ���һ���޸�
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
	}
 /**
  * ��Ҫ�Ĺ���
  */
	

	public void onClick$loginOut() {
		user.setKuAutoenter("0");
		userService.update(user);
		Executions.getCurrent().sendRedirect("/");
	}

	/**
	 * <li>������������ʾһ������tid������������
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
	 * <li>������������ʾ��������ʱ��Ĭ����ʾ��������
	 * 
	 * @param it ��������Ĳ˵��� void
	 * @author DaLei
	 */
	public void appendLeftTree(Menuitem it) {
		Long tid = (Long) it.getAttribute("t_id");
		centerTabbox.setAttribute("tid", tid);
		westLeft.setTitle(it.getLabel());
		westLeft.getChildren().clear();
		WkTTitle title = (WkTTitle) titleService.get(WkTTitle.class, tid);
		if (titleService.getChildTitle(tid).size() == 0) {
			// ���û���¼����⣬�����ǰҪ������ĳ�������˵�����ӦΪĳ���˵�zulҳ��
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
	 * <li>������������ʾ�ж��û��Ƿ��д˱���Ȩ��
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
					//initNoticeList();
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
		win.setTitle("�鿴֪ͨ");
		win.setBorder("normal");
	}

	public void onChange$messagePanel() {
		MessageReceiveWindow win = (MessageReceiveWindow) Executions.createComponents("/admin/personal/message/receive/index.zul", null, null);
		win.doHighlighted();
		win.setClosable(true);
		win.setTitle("�鿴��Ϣ");
		win.setBorder("normal");
	}
	public void onChange$infopanel() {
		InfoShowWindow win = (InfoShowWindow) Executions.createComponents("/admin/personal/newscenter/infoshow.zul", null, null);
		win.doHighlighted();
		win.setClosable(true);
		win.setTitle("�鿴����");
		win.setBorder("normal");
	}
	
}
