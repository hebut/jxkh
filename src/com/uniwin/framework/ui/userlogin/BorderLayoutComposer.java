package com.uniwin.framework.ui.userlogin;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.iti.jxkh.entity.Jxkh_STitle;
import org.iti.jxkh.service.AuditConfigService;
import org.iti.jxkh.service.UserDetailService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zkex.zul.West;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Menu;
import org.zkoss.zul.Menubar;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.Panelchildren;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Space;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTTitle;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.service.TitleService;
import com.uniwin.framework.service.UserService;

public class BorderLayoutComposer extends Window implements AfterCompose {
	private static Log log = LogFactory.getLog(BorderLayoutComposer1.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 维护页面中的标签页组件
	Tab centerTab;
	Tabbox centerTabbox;
	Textbox kufName;
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
	boolean showleft = false;
	private UserService userService;
	Listbox groupList, worklist, netlist;
	//首页
	private Tab firstPage;
	//显示快捷操作的hbox
	private Hbox fastHbox;
	private UserDetailService userDetailService;
	private AuditConfigService auditConfigService;
	private Tabs centerTabs;
	private Tabpanels centerTabpanels;

	@SuppressWarnings("unchecked")
	public void afterCompose() {
		Components.wireController(this, this);
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		ulist = (List<WkTTitle>) Sessions.getCurrent().getAttribute("ulist");
		addUserDept(user.getKdId());
		Sessions.getCurrent().setAttribute("userDeptList", userDeptList);
		Sessions.getCurrent().setAttribute("centerTabbox", centerTabbox);
		Sessions.getCurrent().setAttribute("westLeft", westLeft);
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
		DateFormat datetime1 = DateFormat.getDateInstance(DateFormat.LONG, Locale.CHINA);
		DateFormat datetime2 = DateFormat.getTimeInstance(DateFormat.LONG, Locale.CHINA);
		String datetime = datetime1.format(new Date()) + " " + datetime2.format(new Date());
		loginTime.setValue("现在时间是:" + " " + datetime);
		String lastip = (String) Sessions.getCurrent().getAttribute("lastIP");
		lastIP.setValue("上次登陆IP:" + lastip);
		Sessions.getCurrent().removeAttribute("lastIP");
		loguser.setLabel(user.getKuName().trim() + "　您好，欢迎登录！");
		log.info("所属部门：" + user.getKdId());
		/**
		 * 需要改过来
		 */
		final Desktop desktop = Executions.getCurrent().getDesktop();
		if (desktop.isServerPushEnabled()) {
		} else {
			desktop.enableServerPush(true);
//			new NoticeAlert(noticePanel, messagePanel, centerTab).start();
		}
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		//初始化快捷操作
		initFastOperation();
	}
	
	private void initFastOperation() {
		List<Jxkh_STitle> stlist = userDetailService.getUserSelectedTilte(user.getKuId());
		if(stlist != null && stlist.size() > 0) {
			initFastTitle(stlist);
		}
	}
	private void initFastTitle(List<Jxkh_STitle> stlist) {
		int count = 6;
		String /*space = "30px",*/ theight = "100px", twidth = "100px";
		Grid grid = new Grid();
		grid.setWidth("760px");
		grid.setHeight("500px");
		Columns cs = new Columns();
		Column c1 = new Column();
		c1.setWidth("16.5%");
		c1.setAlign("center");
		Column c2 = new Column();
		c2.setWidth("16.5%");
		c2.setAlign("center");
		Column c3 = new Column();
		c3.setWidth("16.5%");
		c3.setAlign("center");
		Column c4 = new Column();
		c4.setWidth("16.5%");
		c4.setAlign("center");
		Column c5 = new Column();
		c5.setWidth("16.5%");
		c5.setAlign("center");
		Column c6 = new Column();
		c6.setAlign("center");
		cs.appendChild(c1);
		cs.appendChild(c2);
		cs.appendChild(c3);
		cs.appendChild(c4);
		cs.appendChild(c5);
		cs.appendChild(c6);
		cs.setParent(grid);
//		Vbox vbox = new Vbox();
//		Hbox hbox = null;
		Rows rows = new Rows();
		rows.setParent(grid);
		Row row = null;		
		for(int i=0;i<stlist.size();i++) {			
			if(i % count == 0) {
//				hbox = new Hbox();		
//				vbox.appendChild(hbox);
				row = new Row();
				row.setHeight("40px");
				row.setParent(rows);				
//				Separator sep = new Separator();
//				sep.setHeight("100px");
//				vbox.appendChild(sep);
			}
//			Hbox inner = new Hbox();
//			inner.setWidth(twidth);
//			inner.setHeight(theight);
//			inner.setStyle("background-color:#96CDCD;");
			Jxkh_STitle st = stlist.get(i);
			Toolbarbutton img = new Toolbarbutton();
			img.setParent(row);
			img.setHeight(theight);
			img.setWidth(twidth);
			img.setStyle("color:blue;font-size:13px;font-weight:bold;");
//			img.setStyle("background-image:url(../images/index_title/index-back.png)");
			final WkTTitle t = (WkTTitle) auditConfigService.loadById(WkTTitle.class, st.getTitleId());
			img.setLabel(t.getKtName());
			//点击链接另起tab页
			img.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					Tab tab = new Tab();
					tab.setWidth("160px");
					tab.setClosable(true);
					tab.setLabel(t.getKtName());
					tab.setTooltiptext(t.getKtName());
					tab.setParent(centerTabs);
					Tabpanel tabpanel = new Tabpanel();
					tabpanel.setParent(centerTabpanels);
					Window w = (Window) Executions.createComponents(t.getKtContent()+"index.zul", tabpanel, null);
					if(w.getWidth().equals(""))
						w.setWidth("760px");
					tab.setSelected(true);
				}
			});
//			hbox.appendChild(img);
			row.appendChild(img);
//			Space interSpace = new Space();
//			interSpace.setWidth(space);
//			if(hbox.getChildren().size() < 6)
//				hbox.appendChild(interSpace);
		}
//		fastHbox.appendChild(vbox);
		fastHbox.appendChild(grid);
	}

	public void onClick$loginOut() {
		user.setKuAutoenter("0");
		userService.update(user);
		Executions.getCurrent().sendRedirect("/");
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
				
				//201311251147
				/*if(title.getKtContent().endsWith("/")){
					Jxkh_STitle sTitle = new Jxkh_STitle();
					sTitle.setTitleId(title.getKtId());
					sTitle.setUserId(user.getKuId());
					userDetailService.save(title.getKtId(), user.getKuId(), sTitle);
				}*/
				
				
				menpop.appendChild(tit);
				tit.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						firstPage.setSelected(true);
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
	 * @param it
	 *            二级标题的菜单项 void
	 * @author DaLei
	 */
	public void appendLeftTree(Menuitem it) {
		Long tid = (Long) it.getAttribute("t_id");
		centerTabbox.setAttribute("tid", tid);
		westLeft.setTitle(it.getLabel());
		westLeft.getChildren().clear();
		WkTTitle title = (WkTTitle) titleService.get(WkTTitle.class, tid);
		if (titleService.getChildTitle(tid).size() == 0) {
			
			//201311251147
			/*if(title.getKtContent().endsWith("/")){
				Jxkh_STitle sTitle = new Jxkh_STitle();
				sTitle.setTitleId(title.getKtId());
				sTitle.setUserId(user.getKuId());
				userDetailService.save(title.getKtId(), user.getKuId(), sTitle);
			}*/
			
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
}
