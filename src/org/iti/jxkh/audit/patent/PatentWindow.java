package org.iti.jxkh.audit.patent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import jxl.write.WriteException;

import org.iti.gh.common.util.ExportExcel;
import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxkh.business.meeting.DownloadWindow;
import org.iti.jxkh.business.patent.AddPatentWindow;
import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.entity.Jxkh_Patent;
import org.iti.jxkh.entity.Jxkh_PatentDept;
import org.iti.jxkh.entity.Jxkh_PatentInventor;
import org.iti.jxkh.service.JXKHMeetingService;
import org.iti.jxkh.service.JxkhProjectService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.iti.common.util.ConvertUtil;
import com.uniwin.framework.entity.WkTUser;

public class PatentWindow extends Window implements AfterCompose {

	/**
	 * @author SongYu
	 */
	private static final long serialVersionUID = -4018673049983784473L;
	private Textbox name;
	private Groupbox cxtj;
	private Listbox zxListbox, auditState, rank;
	private YearListbox year;
	private Long indicatorId;
	WkTUser user;
	private JxkhProjectService jxkhProjectService;
	private Paging zxPaging;
	Boolean cx;
	String nameSearch, yearSearch;
	Short auditStateSearch;
	private JXKHMeetingService jxkhMeetingService;
	public static final Integer qikan = 51;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		year.initYearListbox("");
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");// 获取当前登录用户
		zxListbox.setItemRenderer(new ZProjectRenderer());
		auditState.setItemRenderer(new auditStateRenderer());
		zxPaging.addEventListener("onPaging", new ZXPagingListener());
		initShow();
		rank.setItemRenderer(new qkTypeRenderer());
		List<Jxkh_BusinessIndicator> holdList = new ArrayList<Jxkh_BusinessIndicator>();
		holdList.add(new Jxkh_BusinessIndicator());
		if (jxkhMeetingService.findRank(qikan) != null) {
			holdList.addAll(jxkhMeetingService.findRank(qikan));
		}
		rank.setModel(new ListModelList(holdList));
		rank.setSelectedIndex(0);
	}

	/** 类别列表渲染器 */
	public class qkTypeRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			Jxkh_BusinessIndicator type = (Jxkh_BusinessIndicator) data;
			item.setValue(type);
			Listcell c0 = new Listcell();
			if (type.getKbName() == null) {
				c0.setLabel("--请选择--");
			} else {
				c0.setLabel(type.getKbName());
			}
			item.appendChild(c0);

			if (item.getIndex() == 0)
				item.setSelected(true);
		}
	}

	public void initShow() {
		List<Jxkh_Patent> patentList1 = jxkhProjectService
				.findAllPatentByDept(user.getDept().getKdNumber());
		zxPaging.setTotalSize(patentList1.size());
		List<Jxkh_Patent> patentList = jxkhProjectService.findAllPatentByDept(
				user.getDept().getKdNumber(), zxPaging.getActivePage(),
				zxPaging.getPageSize());
		zxListbox.setModel(new ListModelList(patentList));
		cx = false;
		String[] a = { "", "待审核","部门审核中", "部门通过",  "部门不通过" };
		List<String> auditStateList = new ArrayList<String>();
		for (int i = 0; i < 5; i++) {
			auditStateList.add(a[i]);
		}
		auditState.setModel(new ListModelList(auditStateList));
		auditState.setSelectedIndex(0);

	}

	/**
	 * <li>功能描述：纵向项目列表渲染器 。 void
	 * 
	 * @author YuSong
	 */
	public class ZProjectRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			final Jxkh_Patent project = (Jxkh_Patent) data;
			item.setValue(project);
			Listcell c0 = new Listcell();
			Listcell c1 = new Listcell(item.getIndex() + 1 + "");
			Listcell c2 = new Listcell(project.getName().length() <= 14?project.getName():project.getName().substring(0, 14) + "...");
			c2.setStyle("color:blue");
			c2.setTooltiptext("点击查看专利(软件)信息");
			c2.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {

					AddPatentWindow w = (AddPatentWindow) Executions
							.createComponents(
									"/admin/personal/businessdata/patent/addPatent.zul",
									null, null);
					try {
						w.setTitle("查看专利(软件)信息");
						w.setProject(project);
						w.setAudit("AUDIT");
						w.initShow();
						w.initWindow();
						w.doModal();
					} catch (SuspendNotAllowedException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// initShow();

				}
			});

			Listcell c3 = new Listcell(project.getSort().getKbName());
			Listcell c4 = new Listcell();
			if (project.getjxYear() != null) {
				c4 = new Listcell(project.getjxYear());
			} else {
				c4 = new Listcell("");
			}
			Listcell c5 = new Listcell();
			c5.setTooltiptext("下载文档");
			Image download = new Image("/css/default/images/button/down.gif");
			download.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					DownloadWindow win = (DownloadWindow) Executions
							.createComponents(
									"/admin/personal/businessdata/meeting/download.zul",
									null, null);
					win.setFiles(project.getPatentFile());
					win.setFlag("patent");
					win.initWindow();
					win.doModal();
				}
			});
			c5.appendChild(download);
			Listcell c6 = new Listcell(project.getScore() == null ? ""
					: project.getScore().toString());
			String strC8;
			switch (project.getState()) {
			case Jxkh_Patent.NOT_AUDIT:
				strC8 = "未审核";
				break;
			case Jxkh_Patent.DEPT_PASS:
				strC8 = "部门通过";
				break;
			case Jxkh_Patent.First_Dept_Pass:
				strC8 = "部门审核中";
				break;
			case Jxkh_Patent.DEPT_NOT_PASS:
				strC8 = "部门不通过";
				break;
			case Jxkh_Patent.BUSINESS_PASS:
				strC8 = "业务办通过";
				break;
			case Jxkh_Patent.BUSINESS_NOT_PASS:
				strC8 = "业务办不通过";
				break;
			case Jxkh_Patent.BUSINESS_TEMP_PASS:
				strC8 = "业务办暂缓通过";
				break;
			default:
				strC8 = "归档";
				break;
			}
			Listcell c8 = new Listcell(strC8);
			c8.setStyle("color:red");
			c8.setTooltiptext("点击填写审核意见");
			// 弹出审核意见事件
			c8.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event event) throws Exception {
					AdviceWin w = (AdviceWin) Executions.createComponents(
							"/admin/jxkh/audit/patent/advice.zul", null, null);
					try {
						w.setMeeting(project);
						w.initWindow();
						w.doModal();
					} catch (SuspendNotAllowedException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (cx == true) {
						onClick$query();
					} else {
						initShow();
					}
				}
			});
			item.appendChild(c0);
			item.appendChild(c1);
			item.appendChild(c2);
			item.appendChild(c3);
			item.appendChild(c4);
			item.appendChild(c5);
			item.appendChild(c6);
			item.appendChild(c8);
		}
	}

	public class ZXPagingListener implements EventListener {
		@Override
		public void onEvent(Event event) throws Exception {
			if (cx == true) {
				List<Jxkh_Patent> patentList = jxkhProjectService
						.findPatentByCondition(nameSearch, auditStateSearch,
								indicatorId, yearSearch, user.getDept()
										.getKdNumber(), zxPaging
										.getActivePage(), zxPaging
										.getPageSize());
				zxListbox.setModel(new ListModelList(patentList));
			} else {
				List<Jxkh_Patent> patentList = jxkhProjectService
						.findAllPatentByDept(user.getDept().getKdNumber(),
								zxPaging.getActivePage(),
								zxPaging.getPageSize());
				zxListbox.setModel(new ListModelList(patentList));
			}
		}
	}

	/**
	 * <li>功能描述：批量审核。 void
	 * 
	 * @author YuSong
	 */
	public void onClick$passAll() {
		if (zxListbox.getSelectedItems() == null
				|| zxListbox.getSelectedItems().size() <= 0) {
			try {
				Messagebox.show("请选择要审核专利(软件)！", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		Iterator<Listitem> items = zxListbox.getSelectedItems().iterator();
		List<Jxkh_Patent> awardList = new ArrayList<Jxkh_Patent>();
		Jxkh_Patent award = new Jxkh_Patent();
		while (items.hasNext()) {

			award = (Jxkh_Patent) items.next().getValue();
			int rank = 0, index = 0;
			List<Jxkh_PatentDept> meetingDepList = jxkhProjectService.findPatentDept(award);
			for (int t = 0; t < meetingDepList.size(); t++) {
				Jxkh_PatentDept dep = meetingDepList.get(t);
				if (dep.getDeptId().equals(user.getDept().getKdNumber())) {
					rank = dep.getRank();
					index = t;
				}
			}
			if ((rank == 1 || award.getState() == Jxkh_Patent.First_Dept_Pass)
					&& award.getTempState().charAt(index) == '0') {
				awardList.add(award);
			}

		}
		BatchAuditWin win = (BatchAuditWin) Executions.createComponents(
				"/admin/jxkh/audit/patent/batchAudit.zul", null, null);
		try {
			win.setAwardList(awardList);
			win.doModal();

		} catch (SuspendNotAllowedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (cx == true) {
			onClick$query();
		} else {
			initShow();
		}
	}

	/** 状态列表渲染器 */
	public class auditStateRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			String auditState = (String) data;
			item.setValue(auditState);
			Listcell c0 = new Listcell();
			if (auditState == null || auditState.equals(""))
				c0.setLabel("--请选择--");
			else {
				c0.setLabel(auditState);
			}
			item.appendChild(c0);
		}
	}

	public void onClick$query() {
		nameSearch = name.getValue();
		auditStateSearch = null;
		if (auditState.getSelectedItem().getValue().equals("")) {
			auditStateSearch = null;
		} else if (auditState.getSelectedItem().getValue().equals("待审核")) {
			auditStateSearch = 0;
		} else if (auditState.getSelectedItem().getValue().equals("部门通过")) {
			auditStateSearch = 1;
		} else if (auditState.getSelectedItem().getValue().equals("部门审核中")) {
			auditStateSearch = 2;
		} else if (auditState.getSelectedItem().getValue().equals("部门不通过")) {
			auditStateSearch = 3;
		} else if (auditState.getSelectedItem().getValue().equals("业务办通过")) {
			auditStateSearch = 4;
		} else if (auditState.getSelectedItem().getValue().equals("业务办不通过")) {
			auditStateSearch = 5;
		} else if (auditState.getSelectedItem().getValue().equals("归档")) {
			auditStateSearch = 6;
		}
		indicatorId = null;
		if (rank.getSelectedIndex() != 0) {
			Jxkh_BusinessIndicator qkType = (Jxkh_BusinessIndicator) rank
					.getSelectedItem().getValue();
			indicatorId = qkType.getKbId();
		}
		yearSearch = null;
		if (year.getSelectedIndex() != 0 && year.getSelectedItem() != null)
			yearSearch = year.getSelectedItem().getValue() + "";
		List<Jxkh_Patent> patentList1 = jxkhProjectService
				.findPatentByCondition(nameSearch, auditStateSearch,
						indicatorId, yearSearch, user.getDept().getKdNumber());
		zxPaging.setTotalSize(patentList1.size());
		List<Jxkh_Patent> patentList = jxkhProjectService
				.findPatentByCondition(nameSearch, auditStateSearch,
						indicatorId, yearSearch, user.getDept().getKdNumber(),
						zxPaging.getActivePage(), zxPaging.getPageSize());
		zxListbox.setModel(new ListModelList(patentList));
		cx = true;
	}

	public void onClick$searchcbutton() {
		if (cxtj.isVisible()) {
			cxtj.setVisible(false);
		} else {
			cxtj.setVisible(true);
		}
	}

	public void onClick$reset() {
		name.setValue("");
		auditState.getItemAtIndex(0).setSelected(true);
		rank.getItemAtIndex(0).setSelected(true);
		year.getItemAtIndex(0).setSelected(true);
	}

	// 导出
	public void onClick$export1() throws WriteException, IOException {
		if (zxListbox.getSelectedCount() == 0) {
			try {
				Messagebox.show("提示请选择要导出的数据", "提示", Messagebox.OK,
						Messagebox.EXCLAMATION);
			} catch (InterruptedException e) {
				// ignore
			}
			return;
		} else {
			int i = 0;
			ArrayList<Jxkh_Patent> expertlist = new ArrayList<Jxkh_Patent>();
			@SuppressWarnings("unchecked")
			Set<Listitem> sel = zxListbox.getSelectedItems();
			Iterator<Listitem> it = sel.iterator();
			while (it.hasNext()) {
				Listitem item = (Listitem) it.next();
				Jxkh_Patent p = (Jxkh_Patent) item.getValue();
				expertlist.add(i, p);
				i++;
			}
			Date now = new Date();
			String fileName = ConvertUtil.convertDateString(now)
					+ "zhuanlixinxi" + ".xls";
			String Title = "奖励情况";
			List<String> titlelist = new ArrayList<String>();
			titlelist.add("序号");
			titlelist.add("专利(软件)名称");
			titlelist.add("发明人");
			titlelist.add("院内部门");

			titlelist.add("专利(软件)类型");
			titlelist.add("知识产权人");
			titlelist.add("授权时间");
			titlelist.add("信息填写人");
			titlelist.add("审核状态");
			String c[][] = new String[expertlist.size()][titlelist.size()];
			// 从SQL中读数据
			for (int j = 0; j < expertlist.size(); j++) {
				Jxkh_Patent award = (Jxkh_Patent) expertlist.get(j);
				String member = "";
				c[j][0] = j + 1 + "";
				c[j][1] = award.getName();
				List<Jxkh_PatentInventor> mlist = jxkhProjectService
						.findPatentMember(award);
				if (mlist.size() != 0) {
					for (int m = 0; m < mlist.size(); m++) {
						Jxkh_PatentInventor mem = (Jxkh_PatentInventor) mlist
								.get(m);
						member += mem.getName() + "、";
					}
					c[j][2] = member.substring(0, member.length() - 1);
				}
				List<Jxkh_PatentDept> dlist = jxkhProjectService
						.findPatentDept(award);
				String dept = "";
				if (dlist.size() != 0) {
					for (int m = 0; m < dlist.size(); m++) {
						Jxkh_PatentDept de = (Jxkh_PatentDept) dlist.get(m);
						dept += de.getName() + "、";
					}
					c[j][3] = dept.substring(0, dept.length() - 1);
				}
				c[j][4] = award.getSort().getKbName();
				c[j][5] = award.getOwner();
				c[j][6] = award.getGrantDate();
				c[j][7] = award.getInfoWriter();
				String strC8;
				switch (award.getState()) {
				case Jxkh_Patent.NOT_AUDIT:
					strC8 = "待审核";
					break;
				case Jxkh_Patent.DEPT_PASS:
					strC8 = "部门通过";
					break;
				case Jxkh_Patent.First_Dept_Pass:
					strC8 = "部门审核中";
					break;
				case Jxkh_Patent.DEPT_NOT_PASS:
					strC8 = "部门未通过";
					break;
				case Jxkh_Patent.BUSINESS_PASS:
					strC8 = "业务办通过";
					break;
				case Jxkh_Patent.BUSINESS_NOT_PASS:
					strC8 = "业务办不通过";
					break;
				case Jxkh_Patent.SAVE_RECORD:
					strC8 = "归档";
					break;
				default:
					strC8 = "待审核";
					break;
				}
				c[j][8] = strC8;
			}
			ExportExcel ee = new ExportExcel();
			ee.exportExcel(fileName, Title, titlelist, expertlist.size(), c);
		}
	}
}
