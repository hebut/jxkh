package org.iti.jxkh.audit.project;

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
import org.iti.jxkh.business.project.AddCPWindow;
import org.iti.jxkh.business.project.AddHPWindow;
import org.iti.jxkh.business.project.AddZPWindow;
import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.entity.Jxkh_Project;
import org.iti.jxkh.entity.Jxkh_ProjectDept;
import org.iti.jxkh.entity.Jxkh_ProjectMember;
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

public class ProjectWindow extends Window implements AfterCompose {

	/**
	 * @author SongYu
	 */
	private static final long serialVersionUID = -4018673049983784473L;
	private Textbox name1, name2, name3;
	private Groupbox cxtj1, cxtj2, cxtj3;
	private Listbox zxListbox, hxListbox, zsListbox, auditState1, auditState2,
			auditState3, rank1, rank2, rank3;
	private YearListbox year1, year2, year3;
	private String yearSearch1, yearSearch2, yearSearch3;
	private Long indicatorId1, indicatorId2, indicatorId3;
	WkTUser user;
	private JxkhProjectService jxkhProjectService;
	private Paging zxPaging, hxPaging, zsPaging;
	Boolean cx1, cx2, cx3;
	String nameSearch, nameSearch2, nameSearch3;
	Short auditStateSearch, auditStateSearch2, auditStateSearch3;
	private JXKHMeetingService jxkhMeetingService;
	public static final Integer qikan = 41;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		year1.initYearListbox("");
		year2.initYearListbox("");
		year3.initYearListbox("");
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");// ��ȡ��ǰ��¼�û�
		zxListbox.setItemRenderer(new ZProjectRenderer());
		hxListbox.setItemRenderer(new HProjectRenderer());
		zsListbox.setItemRenderer(new CProjectRenderer());
		auditState1.setItemRenderer(new auditStateRenderer());
		auditState2.setItemRenderer(new auditStateRenderer());
		auditState3.setItemRenderer(new auditStateRenderer());
		zxPaging.addEventListener("onPaging", new ZXPagingListener());
		hxPaging.addEventListener("onPaging", new HXPagingListener());
		zsPaging.addEventListener("onPaging", new ZSPagingListener());
		initShow();
		rank1.setItemRenderer(new qkTypeRenderer());
		rank2.setItemRenderer(new qkTypeRenderer());
		rank3.setItemRenderer(new qkTypeRenderer());
		List<Jxkh_BusinessIndicator> holdList = new ArrayList<Jxkh_BusinessIndicator>();
		holdList.add(new Jxkh_BusinessIndicator());
		if (jxkhMeetingService.findRank(qikan) != null) {
			holdList.addAll(jxkhMeetingService.findRank(qikan));
		}
		rank1.setModel(new ListModelList(holdList));
		rank1.setSelectedIndex(0);
		rank2.setModel(new ListModelList(holdList));
		rank2.setSelectedIndex(0);
		rank3.setModel(new ListModelList(holdList));
		rank3.setSelectedIndex(0);
	}

	/** ��Ŀ�����б���Ⱦ�� */
	public class qkTypeRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			Jxkh_BusinessIndicator type = (Jxkh_BusinessIndicator) data;
			item.setValue(type);
			Listcell c0 = new Listcell();
			if (type.getKbName() == null) {
				c0.setLabel("--��ѡ��--");
			} else {
				c0.setLabel(type.getKbName());
			}
			item.appendChild(c0);

			if (item.getIndex() == 0)
				item.setSelected(true);
		}
	}

	public void initShow() {
		List<Jxkh_Project> zProjectList1 = jxkhProjectService
				.findAllZPByDept(user.getDept().getKdNumber());
		zxPaging.setTotalSize(zProjectList1.size());
		List<Jxkh_Project> zProjectList = jxkhProjectService.findAllZPByDept(
				user.getDept().getKdNumber(), zxPaging.getActivePage(),
				zxPaging.getPageSize());
		zxListbox.setModel(new ListModelList(zProjectList));

		List<Jxkh_Project> hProjectList1 = jxkhProjectService
				.findAllHPByDept(user.getDept().getKdNumber());
		hxPaging.setTotalSize(hProjectList1.size());
		List<Jxkh_Project> hProjectList = jxkhProjectService.findAllHPByDept(
				user.getDept().getKdNumber(), hxPaging.getActivePage(),
				hxPaging.getPageSize());
		hxListbox.setModel(new ListModelList(hProjectList));

		List<Jxkh_Project> cProjectList1 = jxkhProjectService
				.findAllCPByDept(user.getDept().getKdNumber());
		zsPaging.setTotalSize(cProjectList1.size());
		List<Jxkh_Project> cProjectList = jxkhProjectService.findAllCPByDept(
				user.getDept().getKdNumber(), zsPaging.getActivePage(),
				zsPaging.getPageSize());
		zsListbox.setModel(new ListModelList(cProjectList));
		cx1 = false;
		cx2 = false;
		cx3 = false;
		String[] a = { "", "�����","���������", "����ͨ��",  "���Ų�ͨ��" };
		List<String> auditStateList = new ArrayList<String>();
		for (int i = 0; i < 5; i++) {
			auditStateList.add(a[i]);
		}
		auditState1.setModel(new ListModelList(auditStateList));
		auditState1.setSelectedIndex(0);
		auditState2.setModel(new ListModelList(auditStateList));
		auditState2.setSelectedIndex(0);
		auditState3.setModel(new ListModelList(auditStateList));
		auditState3.setSelectedIndex(0);
	}

	/**
	 * <li>����������������Ŀ�б���Ⱦ�� �� void
	 * 
	 * @author YuSong
	 */
	public class ZProjectRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			final Jxkh_Project project = (Jxkh_Project) data;
			item.setValue(project);
			Listcell c0 = new Listcell();
			Listcell c1 = new Listcell(item.getIndex() + 1 + "");
			Listcell c2 = new Listcell(project.getName().length() <= 14?project.getName():
				project.getName().substring(0, 14) + "...");
			c2.setStyle("color:blue");
			c2.setTooltiptext("����鿴������Ŀ��Ϣ");
			c2.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {

					AddZPWindow w = (AddZPWindow) Executions.createComponents(
							"/admin/personal/businessdata/project/addZP.zul",
							null, null);
					try {
						w.setTitle("�鿴��Ŀ��Ϣ");
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
			Listcell c3 = new Listcell(project.getRank().getKbName());
			Listcell c4 = new Listcell(project.getjxYear());
			Listcell c5 = new Listcell(project.getBeginDate());
			Listcell c6 = new Listcell();
			c6.setTooltiptext("�����ĵ�");
			Image download = new Image("/css/default/images/button/down.gif");
			download.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					DownloadWindow win = (DownloadWindow) Executions
							.createComponents(
									"/admin/personal/businessdata/meeting/download.zul",
									null, null);
					win.setFiles(project.getProjectFile());
					win.setFlag("zp");
					win.initWindow();
					win.doModal();
				}
			});
			c6.appendChild(download);
			Listcell c7 = new Listcell(project.getScore() == null ? "0"
					: project.getScore().toString());
			String strC8;
			switch (project.getState()) {
			case Jxkh_Project.NOT_AUDIT:
				strC8 = "δ���";
				break;
			case Jxkh_Project.DEPT_PASS:
				strC8 = "����ͨ��";
				break;
			case Jxkh_Project.First_Dept_Pass:
				strC8 = "���������";
				break;
			case Jxkh_Project.DEPT_NOT_PASS:
				strC8 = "���Ų�ͨ��";
				break;
			case Jxkh_Project.BUSINESS_PASS:
				strC8 = "ҵ���ͨ��";
				break;
			case Jxkh_Project.BUSINESS_NOT_PASS:
				strC8 = "ҵ��첻ͨ��";
				break;
			case Jxkh_Project.BUSINESS_TEMP_PASS:
				strC8 = "ҵ����ݻ�ͨ��";
				break;
			default:
				strC8 = "�鵵";
				break;
			}
			Listcell c8 = new Listcell(strC8);
			c8.setStyle("color:red");
			c8.setTooltiptext("�����д������");
			// �����������¼�
			c8.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event event) throws Exception {
					AdviceWin w = (AdviceWin) Executions.createComponents(
							"/admin/jxkh/audit/project/advice.zul", null, null);
					try {
						w.setMeeting(project);
						w.initWindow();
						w.doModal();
					} catch (SuspendNotAllowedException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (cx1 == true) {
						onClick$query1();
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
			item.appendChild(c7);
			item.appendChild(c8);
		}
	}

	public class HProjectRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			final Jxkh_Project project = (Jxkh_Project) data;
			item.setValue(project);
			Listcell c0 = new Listcell();
			Listcell c1 = new Listcell(item.getIndex() + 1 + "");
			Listcell c2 = new Listcell(project.getName().length() <= 14?
					project.getName():project.getName().substring(0, 14) + "...");
			c2.setStyle("color:blue");
			c2.setTooltiptext("����鿴������Ŀ��Ϣ");
			c2.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {

					AddHPWindow w = (AddHPWindow) Executions.createComponents(
							"/admin/personal/businessdata/project/addHP.zul",
							null, null);
					try {
						w.setTitle("�鿴��Ŀ��Ϣ");
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

			Listcell c3 = new Listcell("");
			if (project.getRank() == null) {
				c3.setLabel("");
			} else {
				c3.setLabel(project.getRank().getKbName());
			}
			Listcell c4 = new Listcell(project.getjxYear());
			Listcell c5 = new Listcell(project.getBeginDate());
			Listcell c6 = new Listcell();
			c6.setTooltiptext("�����ĵ�");
			Image download = new Image("/css/default/images/button/down.gif");
			download.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					DownloadWindow win = (DownloadWindow) Executions
							.createComponents(
									"/admin/personal/businessdata/meeting/download.zul",
									null, null);
					win.setFiles(project.getProjectFile());
					win.setFlag("hp");
					win.initWindow();
					win.doModal();
				}
			});
			c6.appendChild(download);
			Listcell c7 = new Listcell(project.getScore() == null ? "0"
					: project.getScore().toString());
			String strC8;
			switch (project.getState()) {
			case Jxkh_Project.NOT_AUDIT:
				strC8 = "δ���";
				break;
			case Jxkh_Project.DEPT_PASS:
				strC8 = "����ͨ��";
				break;
			case Jxkh_Project.First_Dept_Pass:
				strC8 = "���������";
				break;
			case Jxkh_Project.DEPT_NOT_PASS:
				strC8 = "���Ų�ͨ��";
				break;
			case Jxkh_Project.BUSINESS_PASS:
				strC8 = "ҵ���ͨ��";
				break;
			case Jxkh_Project.BUSINESS_NOT_PASS:
				strC8 = "ҵ��첻ͨ��";
				break;
			case Jxkh_Project.BUSINESS_TEMP_PASS:
				strC8 = "ҵ����ݻ�ͨ��";
				break;
			default:
				strC8 = "�鵵";
				break;
			}
			Listcell c8 = new Listcell(strC8);
			c8.setStyle("color:red");
			c8.setTooltiptext("�����д������");
			// �����������¼�
			c8.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event event) throws Exception {
					AdviceWin w = (AdviceWin) Executions.createComponents(
							"/admin/jxkh/audit/project/advice.zul", null, null);
					try {
						w.setMeeting(project);
						w.initWindow();
						w.doModal();
					} catch (SuspendNotAllowedException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (cx2 == true) {
						onClick$query2();
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
			item.appendChild(c7);
			item.appendChild(c8);
		}
	}

	public class CProjectRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			final Jxkh_Project project = (Jxkh_Project) data;
			item.setValue(project);
			Listcell c0 = new Listcell();
			Listcell c1 = new Listcell(item.getIndex() + 1 + "");
			Listcell c2 = new Listcell(project.getName().length() <= 14?
					project.getName():project.getName().substring(0, 14) + "...");
			c2.setStyle("color:blue");
			c2.setTooltiptext("����鿴������Ŀ��Ϣ");
			c2.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {

					// CPWindow w = (CPWindow) Executions.createComponents(
					// "/admin/jxkh/audit/project/showCP.zul", null, null);
					AddCPWindow w = (AddCPWindow) Executions.createComponents(
							"/admin/personal/businessdata/project/addCP.zul",
							null, null);
					try {
						w.setTitle("�鿴��Ŀ��Ϣ");
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

			// Listcell c3 = new Listcell(project.getProjecCode());
			Listcell c4 = new Listcell(project.getHeader());
			Listcell c5 = new Listcell(project.getBeginDate());
			Listcell c6 = new Listcell();
			c6.setTooltiptext("�����ĵ�");
			Image download = new Image("/css/default/images/button/down.gif");
			download.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					DownloadWindow win = (DownloadWindow) Executions
							.createComponents(
									"/admin/personal/businessdata/meeting/download.zul",
									null, null);
					win.setFiles(project.getProjectFile());
					win.setFlag("cp");
					win.initWindow();
					win.doModal();
				}
			});
			c6.appendChild(download);
			String strC8;
			switch (project.getState()) {
			case Jxkh_Project.NOT_AUDIT:
				strC8 = "δ���";
				break;
			case Jxkh_Project.DEPT_PASS:
				strC8 = "����ͨ��";
				break;
			case Jxkh_Project.First_Dept_Pass:
				strC8 = "���������";
				break;
			case Jxkh_Project.DEPT_NOT_PASS:
				strC8 = "���Ų�ͨ��";
				break;
			case Jxkh_Project.BUSINESS_PASS:
				strC8 = "ҵ���ͨ��";
				break;
			case Jxkh_Project.BUSINESS_NOT_PASS:
				strC8 = "ҵ��첻ͨ��";
				break;
			case Jxkh_Project.BUSINESS_TEMP_PASS:
				strC8 = "ҵ����ݻ�ͨ��";
				break;
			default:
				strC8 = "�鵵";
				break;
			}
			Listcell c8 = new Listcell(strC8);
			c8.setStyle("color:red");
			c8.setTooltiptext("�����д������");
			// �����������¼�
			c8.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event event) throws Exception {
					AdviceWin w = (AdviceWin) Executions.createComponents(
							"/admin/jxkh/audit/project/advice.zul", null, null);
					try {
						w.setMeeting(project);
						w.initWindow();
						w.doModal();
					} catch (SuspendNotAllowedException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (cx3 == true) {
						onClick$query3();
					} else {
						initShow();
					}
				}
			});
			item.appendChild(c0);
			item.appendChild(c1);
			item.appendChild(c2);
			// item.appendChild(c3);
			item.appendChild(c4);
			item.appendChild(c5);
			item.appendChild(c6);
			item.appendChild(c8);
		}
	}

	public class ZXPagingListener implements EventListener {
		@Override
		public void onEvent(Event event) throws Exception {
			if (cx1 == true) {
				List<Jxkh_Project> projectList = jxkhProjectService
						.findZPByCondition(nameSearch, auditStateSearch,
								indicatorId1, yearSearch1, user.getDept()
										.getKdNumber(), zxPaging
										.getActivePage(), zxPaging
										.getPageSize());
				zxListbox.setModel(new ListModelList(projectList));
			} else {
				List<Jxkh_Project> zProjectList = jxkhProjectService
						.findAllZPByDept(user.getDept().getKdNumber(),
								zxPaging.getActivePage(),
								zxPaging.getPageSize());
				zxListbox.setModel(new ListModelList(zProjectList));
			}
		}
	}

	public class HXPagingListener implements EventListener {
		@Override
		public void onEvent(Event event) throws Exception {
			if (cx2 == true) {
				List<Jxkh_Project> projectList = jxkhProjectService
						.findHPByCondition(nameSearch2, auditStateSearch2,
								indicatorId2, yearSearch2, user.getDept()
										.getKdNumber(), hxPaging
										.getActivePage(), hxPaging
										.getPageSize());
				hxListbox.setModel(new ListModelList(projectList));
			} else {
				List<Jxkh_Project> hProjectList = jxkhProjectService
						.findAllHPByDept(user.getDept().getKdNumber(),
								hxPaging.getActivePage(),
								hxPaging.getPageSize());
				hxListbox.setModel(new ListModelList(hProjectList));
			}
		}
	}

	public class ZSPagingListener implements EventListener {
		@Override
		public void onEvent(Event event) throws Exception {
			if (cx3 == true) {
				List<Jxkh_Project> projectList = jxkhProjectService
						.findCPByCondition(nameSearch3, auditStateSearch3,
								indicatorId3, yearSearch3, user.getDept()
										.getKdNumber(), zsPaging
										.getActivePage(), zsPaging
										.getPageSize());
				zsListbox.setModel(new ListModelList(projectList));
			} else {
				List<Jxkh_Project> cProjectList = jxkhProjectService
						.findAllCPByDept(user.getDept().getKdNumber(),
								zsPaging.getActivePage(),
								zsPaging.getPageSize());
				zsListbox.setModel(new ListModelList(cProjectList));
			}
		}
	}

	/**
	 * <li>����������������ˡ� void
	 * 
	 * @author YuSong
	 */
	public void onClick$passAll1() {
		if (zxListbox.getSelectedItems() == null
				|| zxListbox.getSelectedItems().size() <= 0) {
			try {
				Messagebox.show("��ѡ��Ҫ�����Ŀ��", "��ʾ", Messagebox.OK,
						Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		Iterator<?> items = zxListbox.getSelectedItems().iterator();
		List<Jxkh_Project> awardList = new ArrayList<Jxkh_Project>();
		Jxkh_Project award = new Jxkh_Project();
		while (items.hasNext()) {
			Listitem item = (Listitem) items.next();
			award = (Jxkh_Project) item.getValue();
			int rank = 0, index = 0;
			List<Jxkh_ProjectDept> meetingDepList = jxkhProjectService.findProjectDept(award);
			for (int t = 0; t < meetingDepList.size(); t++) {
				Jxkh_ProjectDept dep = meetingDepList.get(t);
				if (dep.getDeptId().equals(user.getDept().getKdNumber())) {
					rank = dep.getRank();
					index = t;
				}
			}
			if ((rank == 1 || award.getState() == Jxkh_Project.First_Dept_Pass)
					&& award.getTempState().charAt(index) == '0') {
				awardList.add(award);
			}
		
		}
		BatchAuditWin win = (BatchAuditWin) Executions.createComponents(
				"/admin/jxkh/audit/project/batchAudit.zul", null, null);
		try {
			win.setAwardList(awardList);
			win.doModal();

		} catch (SuspendNotAllowedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (cx1 == true) {
			onClick$query1();
		} else {
			initShow();
		}
	}

	/**
	 * <li>����������������ˡ� void
	 * 
	 * @author YuSong
	 */
	public void onClick$passAll2() {
		if (hxListbox.getSelectedItems() == null
				|| hxListbox.getSelectedItems().size() <= 0) {
			try {
				Messagebox.show("��ѡ��Ҫ�����Ŀ��", "��ʾ", Messagebox.OK,
						Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		Iterator<Listitem> items = hxListbox.getSelectedItems().iterator();
		List<Jxkh_Project> awardList = new ArrayList<Jxkh_Project>();
		Jxkh_Project award = new Jxkh_Project();
		while (items.hasNext()) {
			award = (Jxkh_Project) items.next().getValue();
			awardList.add(award);
		}
		BatchAuditWin win = (BatchAuditWin) Executions.createComponents(
				"/admin/jxkh/audit/project/batchAudit.zul", null, null);
		try {
			win.setAwardList(awardList);
			win.doModal();

		} catch (SuspendNotAllowedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (cx2 == true) {
			onClick$query2();
		} else {
			initShow();
		}
	}

	/**
	 * <li>����������������ˡ� void
	 * 
	 * @author YuSong
	 */
	public void onClick$passAll3() {
		if (zsListbox.getSelectedItems() == null
				|| zsListbox.getSelectedItems().size() <= 0) {
			try {
				Messagebox.show("��ѡ��Ҫ�����Ŀ��", "��ʾ", Messagebox.OK,
						Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		Iterator<Listitem> items = zsListbox.getSelectedItems().iterator();
		List<Jxkh_Project> awardList = new ArrayList<Jxkh_Project>();
		Jxkh_Project award = new Jxkh_Project();
		while (items.hasNext()) {
			award = (Jxkh_Project) items.next().getValue();
			awardList.add(award);
		}
		BatchAuditWin win = (BatchAuditWin) Executions.createComponents(
				"/admin/jxkh/audit/project/batchAudit.zul", null, null);
		try {
			win.setAwardList(awardList);
			win.doModal();

		} catch (SuspendNotAllowedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (cx3 == true) {
			onClick$query3();
		} else {
			initShow();
		}
	}

	/** ״̬�б���Ⱦ�� */
	public class auditStateRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			String auditState = (String) data;
			item.setValue(auditState);
			Listcell c0 = new Listcell();
			if (auditState == null || auditState.equals(""))
				c0.setLabel("--��ѡ��--");
			else {
				c0.setLabel(auditState);
			}
			item.appendChild(c0);
		}
	}

	public void onClick$query1() {
		nameSearch = name1.getValue();
		auditStateSearch = null;
		if (auditState1.getSelectedItem().getValue().equals("")) {
			auditStateSearch = null;
		} else if (auditState1.getSelectedItem().getValue().equals("�����")) {
			auditStateSearch = 0;
		} else if (auditState1.getSelectedItem().getValue().equals("����ͨ��")) {
			auditStateSearch = 1;
		} else if (auditState1.getSelectedItem().getValue().equals("���������")) {
			auditStateSearch = 2;
		} else if (auditState1.getSelectedItem().getValue().equals("���Ų�ͨ��")) {
			auditStateSearch = 3;
		} else if (auditState1.getSelectedItem().getValue().equals("ҵ���ͨ��")) {
			auditStateSearch = 4;
		} else if (auditState1.getSelectedItem().getValue().equals("ҵ��첻ͨ��")) {
			auditStateSearch = 5;
		} else if (auditState1.getSelectedItem().getValue().equals("�鵵")) {
			auditStateSearch = 6;
		}
		indicatorId1 = null;
		if (rank1.getSelectedIndex() != 0) {
			Jxkh_BusinessIndicator qkType = (Jxkh_BusinessIndicator) rank1
					.getSelectedItem().getValue();
			indicatorId1 = qkType.getKbId();
		}
		yearSearch1 = null;
		if (year1.getSelectedIndex() != 0 && year1.getSelectedItem() != null)
			yearSearch1 = year1.getSelectedItem().getValue() + "";
		List<Jxkh_Project> projectList1 = jxkhProjectService.findZPByCondition(
				nameSearch, auditStateSearch, user.getDept().getKdNumber());
		zxPaging.setTotalSize(projectList1.size());
		List<Jxkh_Project> projectList = jxkhProjectService.findZPByCondition(
				nameSearch, auditStateSearch, indicatorId1, yearSearch1, user
						.getDept().getKdNumber(), zxPaging.getActivePage(),
				zxPaging.getPageSize());
		zxListbox.setModel(new ListModelList(projectList));
		cx1 = true;
	}

	public void onClick$searchcbutton1() {
		if (cxtj1.isVisible()) {
			cxtj1.setVisible(false);
		} else {
			cxtj1.setVisible(true);
		}

	}

	public void onClick$reset1() {
		name1.setValue("");
		auditState1.getItemAtIndex(0).setSelected(true);
		rank1.getItemAtIndex(0).setSelected(true);
		year1.getItemAtIndex(0).setSelected(true);
	}

	public void onClick$query2() {
		nameSearch2 = name2.getValue();
		auditStateSearch2 = null;
		if (auditState2.getSelectedItem().getValue().equals("")) {
			auditStateSearch2 = null;
		} else if (auditState2.getSelectedItem().getValue().equals("�����")) {
			auditStateSearch2 = 0;
		} else if (auditState2.getSelectedItem().getValue().equals("����ͨ��")) {
			auditStateSearch2 = 1;
		} else if (auditState1.getSelectedItem().getValue().equals("���������")) {
			auditStateSearch2 = 2;
		} else if (auditState1.getSelectedItem().getValue().equals("���Ų�ͨ��")) {
			auditStateSearch2 = 3;
		} else if (auditState1.getSelectedItem().getValue().equals("ҵ���ͨ��")) {
			auditStateSearch2 = 4;
		} else if (auditState1.getSelectedItem().getValue().equals("ҵ��첻ͨ��")) {
			auditStateSearch2 = 5;
		} else if (auditState1.getSelectedItem().getValue().equals("�鵵")) {
			auditStateSearch2 = 6;
		}
		indicatorId2 = null;
		if (rank2.getSelectedIndex() != 0) {
			Jxkh_BusinessIndicator qkType = (Jxkh_BusinessIndicator) rank2
					.getSelectedItem().getValue();
			indicatorId2 = qkType.getKbId();
		}
		yearSearch2 = null;
		if (year2.getSelectedIndex() != 0 && year2.getSelectedItem() != null)
			yearSearch2 = year2.getSelectedItem().getValue() + "";

		List<Jxkh_Project> projectList1 = jxkhProjectService.findHPByCondition(
				nameSearch2, auditStateSearch2, user.getDept().getKdNumber());
		hxPaging.setTotalSize(projectList1.size());
		List<Jxkh_Project> projectList = jxkhProjectService.findHPByCondition(
				nameSearch2, auditStateSearch2, indicatorId2, yearSearch2, user
						.getDept().getKdNumber(), hxPaging.getActivePage(),
				hxPaging.getPageSize());
		hxListbox.setModel(new ListModelList(projectList));
		cx2 = true;
	}

	public void onClick$searchcbutton2() {
		if (cxtj2.isVisible()) {
			cxtj2.setVisible(false);
		} else {
			cxtj2.setVisible(true);
		}

	}

	public void onClick$reset2() {
		name2.setValue("");
		auditState2.getItemAtIndex(0).setSelected(true);
		rank2.getItemAtIndex(0).setSelected(true);
		year2.getItemAtIndex(0).setSelected(true);
	}

	public void onClick$query3() {
		nameSearch3 = name3.getValue();
		auditStateSearch3 = null;
		if (auditState3.getSelectedItem().getValue().equals("")) {
			auditStateSearch3 = null;
		} else if (auditState3.getSelectedItem().getValue().equals("�����")) {
			auditStateSearch3 = 0;
		} else if (auditState3.getSelectedItem().getValue().equals("����ͨ��")) {
			auditStateSearch3 = 1;
		} else if (auditState1.getSelectedItem().getValue().equals("���������")) {
			auditStateSearch3 = 2;
		} else if (auditState1.getSelectedItem().getValue().equals("���Ų�ͨ��")) {
			auditStateSearch3 = 3;
		} else if (auditState1.getSelectedItem().getValue().equals("ҵ���ͨ��")) {
			auditStateSearch3 = 4;
		} else if (auditState1.getSelectedItem().getValue().equals("ҵ��첻ͨ��")) {
			auditStateSearch3 = 5;
		} else if (auditState1.getSelectedItem().getValue().equals("�鵵")) {
			auditStateSearch3 = 6;
		}
		indicatorId3 = null;
		if (rank3.getSelectedIndex() != 0) {
			Jxkh_BusinessIndicator qkType = (Jxkh_BusinessIndicator) rank3
					.getSelectedItem().getValue();
			indicatorId3 = qkType.getKbId();
		}
		yearSearch3 = null;
		if (year3.getSelectedIndex() != 0 && year3.getSelectedItem() != null)
			yearSearch3 = year3.getSelectedItem().getValue() + "";

		List<Jxkh_Project> projectList1 = jxkhProjectService.findCPByCondition(
				nameSearch3, auditStateSearch3, user.getDept().getKdNumber());
		zsPaging.setTotalSize(projectList1.size());
		List<Jxkh_Project> projectList = jxkhProjectService.findCPByCondition(
				nameSearch3, auditStateSearch3, indicatorId3, yearSearch3, user
						.getDept().getKdNumber(), zsPaging.getActivePage(),
				zsPaging.getPageSize());
		zsListbox.setModel(new ListModelList(projectList));
		cx3 = true;
	}

	public void onClick$searchcbutton3() {
		if (cxtj3.isVisible()) {
			cxtj3.setVisible(false);
		} else {
			cxtj3.setVisible(true);
		}
	}

	public void onClick$reset3() {
		name3.setValue("");
		auditState3.getItemAtIndex(0).setSelected(true);
		rank3.getItemAtIndex(0).setSelected(true);
		year3.getItemAtIndex(0).setSelected(true);
	}

	// ����
	public void onClick$export1() throws WriteException, IOException {
		if (zxListbox.getSelectedCount() == 0) {
			try {
				Messagebox.show("��ʾ��ѡ��Ҫ����������", "��ʾ", Messagebox.OK,
						Messagebox.EXCLAMATION);
			} catch (InterruptedException e) {
				// ignore
			}
			return;
		} else {
			int i = 0;
			ArrayList<Jxkh_Project> expertlist = new ArrayList<Jxkh_Project>();
			@SuppressWarnings("unchecked")
			Set<Listitem> sel = zxListbox.getSelectedItems();
			Iterator<Listitem> it = sel.iterator();
			while (it.hasNext()) {
				Listitem item = (Listitem) it.next();
				Jxkh_Project p = (Jxkh_Project) item.getValue();
				expertlist.add(i, p);
				i++;
			}
			Date now = new Date();
			String fileName = ConvertUtil.convertDateString(now)
					+ "zongxiangxiangmu" + ".xls";
			String Title = "�������";
			List<String> titlelist = new ArrayList<String>();
			titlelist.add("���");
			titlelist.add("��Ŀ����");
			titlelist.add("��Ŀ��Ա");
			titlelist.add("Ժ�ڲ���");

			titlelist.add("��Ŀ����");
			titlelist.add("��Ŀ������");
			titlelist.add("��ͬʼ��");
			titlelist.add("��Ϣ��д��");
			titlelist.add("���״̬");
			String c[][] = new String[expertlist.size()][titlelist.size()];
			// ��SQL�ж�����
			for (int j = 0; j < expertlist.size(); j++) {
				Jxkh_Project award = (Jxkh_Project) expertlist.get(j);
				String member = "";
				c[j][0] = j + 1 + "";
				c[j][1] = award.getName();
				List<Jxkh_ProjectMember> mlist = jxkhProjectService
						.findProjectMember(award);
				if (mlist.size() != 0) {
					for (int m = 0; m < mlist.size(); m++) {
						Jxkh_ProjectMember mem = (Jxkh_ProjectMember) mlist
								.get(m);
						member += mem.getName() + "��";
					}
					c[j][2] = member.substring(0, member.length() - 1);
				}
				List<Jxkh_ProjectDept> dlist = jxkhProjectService
						.findProjectDept(award);
				String dept = "";
				if (dlist.size() != 0) {
					for (int m = 0; m < dlist.size(); m++) {
						Jxkh_ProjectDept de = (Jxkh_ProjectDept) dlist.get(m);
						dept += de.getName() + "��";
					}
					c[j][3] = dept.substring(0, dept.length() - 1);
				}
				c[j][4] = award.getRank().getKbName();
				c[j][5] = award.getHeader();
				c[j][6] = award.getBeginDate();
				c[j][7] = award.getInfoWriter();
				String strC8;
				switch (award.getState()) {
				case Jxkh_Project.NOT_AUDIT:
					strC8 = "�����";
					break;
				case Jxkh_Project.DEPT_PASS:
					strC8 = "����ͨ��";
					break;
				case Jxkh_Project.First_Dept_Pass:
					strC8 = "���������";
					break;
				case Jxkh_Project.DEPT_NOT_PASS:
					strC8 = "���Ų�ͨ��";
					break;
				case Jxkh_Project.BUSINESS_PASS:
					strC8 = "ҵ���ͨ��";
					break;
				case Jxkh_Project.BUSINESS_NOT_PASS:
					strC8 = "ҵ��첻ͨ��";
					break;
				case Jxkh_Project.SAVE_RECORD:
					strC8 = "�鵵";
					break;
				default:
					strC8 = "�����";
					break;
				}
				c[j][8] = strC8;
			}
			ExportExcel ee = new ExportExcel();
			ee.exportExcel(fileName, Title, titlelist, expertlist.size(), c);
		}
	}

	// ����
	public void onClick$export2() throws WriteException, IOException {
		if (hxListbox.getSelectedCount() == 0) {
			try {
				Messagebox.show("��ʾ��ѡ��Ҫ����������", "��ʾ", Messagebox.OK,
						Messagebox.EXCLAMATION);
			} catch (InterruptedException e) {
				// ignore
			}
			return;
		} else {
			int i = 0;
			ArrayList<Jxkh_Project> expertlist = new ArrayList<Jxkh_Project>();
			@SuppressWarnings("unchecked")
			Set<Listitem> sel = hxListbox.getSelectedItems();
			Iterator<Listitem> it = sel.iterator();
			while (it.hasNext()) {
				Listitem item = (Listitem) it.next();
				Jxkh_Project p = (Jxkh_Project) item.getValue();
				expertlist.add(i, p);
				i++;
			}
			Date now = new Date();
			String fileName = ConvertUtil.convertDateString(now)
					+ "hengxiangxiangmu" + ".xls";
			String Title = "�������";
			List<String> titlelist = new ArrayList<String>();
			titlelist.add("���");
			titlelist.add("��Ŀ����");
			titlelist.add("��Ŀ��Ա");
			titlelist.add("Ժ�ڲ���");

			titlelist.add("��Ŀ����");
			titlelist.add("��Ŀ������");
			titlelist.add("��ͬʼ��");
			titlelist.add("��Ϣ��д��");
			titlelist.add("���״̬");
			String c[][] = new String[expertlist.size()][titlelist.size()];
			// ��SQL�ж�����
			for (int j = 0; j < expertlist.size(); j++) {
				Jxkh_Project award = (Jxkh_Project) expertlist.get(j);
				String member = "";
				c[j][0] = j + 1 + "";
				c[j][1] = award.getName();
				List<Jxkh_ProjectMember> mlist = jxkhProjectService
						.findProjectMember(award);
				if (mlist.size() != 0) {
					for (int m = 0; m < mlist.size(); m++) {
						Jxkh_ProjectMember mem = (Jxkh_ProjectMember) mlist
								.get(m);
						member += mem.getName() + "��";
					}
					c[j][2] = member.substring(0, member.length() - 1);
				}
				List<Jxkh_ProjectDept> dlist = jxkhProjectService
						.findProjectDept(award);
				String dept = "";
				if (dlist.size() != 0) {
					for (int m = 0; m < dlist.size(); m++) {
						Jxkh_ProjectDept de = (Jxkh_ProjectDept) dlist.get(m);
						dept += de.getName() + "��";
					}
					c[j][3] = dept.substring(0, dept.length() - 1);
				}
				c[j][4] = "������Ŀ";
				c[j][5] = award.getHeader();
				c[j][6] = award.getBeginDate();
				c[j][7] = award.getInfoWriter();
				String strC8;
				switch (award.getState()) {
				case Jxkh_Project.NOT_AUDIT:
					strC8 = "�����";
					break;
				case Jxkh_Project.DEPT_PASS:
					strC8 = "����ͨ��";
					break;
				case Jxkh_Project.First_Dept_Pass:
					strC8 = "���������";
					break;
				case Jxkh_Project.DEPT_NOT_PASS:
					strC8 = "���Ų�ͨ��";
					break;
				case Jxkh_Project.BUSINESS_PASS:
					strC8 = "ҵ���ͨ��";
					break;
				case Jxkh_Project.BUSINESS_NOT_PASS:
					strC8 = "ҵ��첻ͨ��";
					break;
				case Jxkh_Project.SAVE_RECORD:
					strC8 = "�鵵";
					break;
				default:
					strC8 = "�����";
					break;
				}
				c[j][8] = strC8;
			}
			ExportExcel ee = new ExportExcel();
			ee.exportExcel(fileName, Title, titlelist, expertlist.size(), c);
		}
	}

	// ����
	public void onClick$export3() throws WriteException, IOException {
		if (zsListbox.getSelectedCount() == 0) {
			try {
				Messagebox.show("��ʾ��ѡ��Ҫ����������", "��ʾ", Messagebox.OK,
						Messagebox.EXCLAMATION);
			} catch (InterruptedException e) {
				// ignore
			}
			return;
		} else {
			int i = 0;
			ArrayList<Jxkh_Project> expertlist = new ArrayList<Jxkh_Project>();
			@SuppressWarnings("unchecked")
			Set<Listitem> sel = zsListbox.getSelectedItems();
			Iterator<Listitem> it = sel.iterator();
			while (it.hasNext()) {
				Listitem item = (Listitem) it.next();
				Jxkh_Project p = (Jxkh_Project) item.getValue();
				expertlist.add(i, p);
				i++;
			}
			Date now = new Date();
			String fileName = ConvertUtil.convertDateString(now)
					+ "zishexiangmu" + ".xls";
			String Title = "�������";
			List<String> titlelist = new ArrayList<String>();
			titlelist.add("���");
			titlelist.add("��Ŀ����");
			titlelist.add("��Ŀ��Ա");
			titlelist.add("Ժ�ڲ���");

			titlelist.add("��Ŀ����");
			titlelist.add("��Ŀ������");
			titlelist.add("��ͬʼ��");
			titlelist.add("��Ϣ��д��");
			titlelist.add("���״̬");
			String c[][] = new String[expertlist.size()][titlelist.size()];
			// ��SQL�ж�����
			for (int j = 0; j < expertlist.size(); j++) {
				Jxkh_Project award = (Jxkh_Project) expertlist.get(j);
				String member = "";
				c[j][0] = j + 1 + "";
				c[j][1] = award.getName();
				List<Jxkh_ProjectMember> mlist = jxkhProjectService
						.findProjectMember(award);
				if (mlist.size() != 0) {
					for (int m = 0; m < mlist.size(); m++) {
						Jxkh_ProjectMember mem = (Jxkh_ProjectMember) mlist
								.get(m);
						member += mem.getName() + "��";
					}
					c[j][2] = member.substring(0, member.length() - 1);
				}
				List<Jxkh_ProjectDept> dlist = jxkhProjectService
						.findProjectDept(award);
				String dept = "";
				if (dlist.size() != 0) {
					for (int m = 0; m < dlist.size(); m++) {
						Jxkh_ProjectDept de = (Jxkh_ProjectDept) dlist.get(m);
						dept += de.getName() + "��";
					}
					c[j][3] = dept.substring(0, dept.length() - 1);
				}
				c[j][4] = "Ժ��������Ŀ";
				c[j][5] = award.getHeader();
				c[j][6] = award.getBeginDate();
				c[j][7] = award.getInfoWriter();
				String strC8;
				switch (award.getState()) {
				case Jxkh_Project.NOT_AUDIT:
					strC8 = "�����";
					break;
				case Jxkh_Project.DEPT_PASS:
					strC8 = "����ͨ��";
					break;
				case Jxkh_Project.First_Dept_Pass:
					strC8 = "���������";
					break;
				case Jxkh_Project.DEPT_NOT_PASS:
					strC8 = "���Ų�ͨ��";
					break;
				case Jxkh_Project.BUSINESS_PASS:
					strC8 = "ҵ���ͨ��";
					break;
				case Jxkh_Project.BUSINESS_NOT_PASS:
					strC8 = "ҵ��첻ͨ��";
					break;
				case Jxkh_Project.SAVE_RECORD:
					strC8 = "�鵵";
					break;
				default:
					strC8 = "�����";
					break;
				}
				c[j][8] = strC8;
			}
			ExportExcel ee = new ExportExcel();
			ee.exportExcel(fileName, Title, titlelist, expertlist.size(), c);
		}
	}
}
