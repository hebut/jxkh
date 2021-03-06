package org.iti.jxkh.busiAudit.project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import jxl.write.WriteException;

import org.iti.gh.common.util.ExportExcel;
import org.iti.jxkh.business.meeting.DownloadWindow;
import org.iti.jxkh.entity.JXKH_MEETING;
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
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.iti.common.util.ConvertUtil;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;

public class CprojectWindow extends Window implements AfterCompose {
	/**
	 * @author SongYu
	 */
	private static final long serialVersionUID = -4018673049983784473L;
	private Textbox name3;
	private Groupbox cxtj3;
	private Listbox zsListbox,auditState3, rank3, dept3;
	WkTUser user;
	Boolean cx3;
	private JxkhProjectService jxkhProjectService;
	private JXKHMeetingService jxkhMeetingService;
	public static final Integer qikan = 41;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");// 获取当前登录用户
		zsListbox.setItemRenderer(new CProjectRenderer());
		cx3 = false;
		auditState3.setItemRenderer(new auditStateRenderer());
		initShow();
		rank3.setItemRenderer(new qkTypeRenderer());
		List<Jxkh_BusinessIndicator> holdList = new ArrayList<Jxkh_BusinessIndicator>();
		holdList.add(new Jxkh_BusinessIndicator());
		if (jxkhMeetingService.findRank(qikan) != null) {
			holdList.addAll(jxkhMeetingService.findRank(qikan));
		}
		rank3.setModel(new ListModelList(holdList));
		rank3.setSelectedIndex(0);

		dept3.setItemRenderer(new deptRenderer());
		List<WkTDept> depList = new ArrayList<WkTDept>();
		depList.add(new WkTDept());
		if (jxkhMeetingService.findAllDep() != null)
			depList.addAll(jxkhMeetingService.findAllDep());
		dept3.setModel(new ListModelList(depList));
		dept3.setSelectedIndex(0);
	}

	/** 期刊类别列表渲染器 */
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

	/** 部门列表渲染器 */
	public class deptRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			WkTDept d = (WkTDept) data;
			item.setValue(d);
			Listcell c0 = new Listcell();
			if (d.getKdName() == null) {
				c0.setLabel("--请选择--");
			} else {
				c0.setLabel(d.getKdName());
			}
			item.appendChild(c0);

			if (item.getIndex() == 0)
				item.setSelected(true);
		}
	}

	public void initShow() {
		List<Jxkh_Project> cProjectList = jxkhProjectService.findAllCPByBusi();
		zsListbox.setModel(new ListModelList(cProjectList));
		String[] a = { "", "待审核", "部门审核中","部门通过",  "部门不通过","业务办暂缓通过", "业务办通过", "业务办不通过",
		"归档" };
		List<String> auditStateList = new ArrayList<String>();
		for (int i = 0; i < 8; i++) {
			auditStateList.add(a[i]);
		}
		auditState3.setModel(new ListModelList(auditStateList));
		auditState3.setSelectedIndex(0);
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
			c2.setTooltiptext("点击编辑自设项目信息");
			c2.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {

					AddCPWindow w = (AddCPWindow) Executions.createComponents(
							"/admin/jxkh/busiAudit/project/addCP.zul", null,
							null);
					try {
						w.setTitle("编辑自设项目信息");
						w.setProject(project);
						w.initShow();
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
			Listcell c4 = new Listcell(project.getHeader());
			Listcell c5 = new Listcell(project.getBeginDate());
			Listcell c6 = new Listcell();
			Image download = new Image("/css/default/images/button/down.gif");
			download.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					DownloadWindow win = (DownloadWindow) Executions
							.createComponents(
									"/admin/jxkh/busiAudit/award/download.zul",
									null, null);
					win.setFiles(project.getProjectFile());
					win.setFlag("cp");
					win.initWindow();
					win.doModal();
				}
			});
			c6.appendChild(download);
			Listcell c7 = new Listcell();
			Image addrecode = new Image(
					"/css/default/images/button/actEdit.gif");
			addrecode.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					if (project.getState() == 0 
							|| project.getState() == 1
							|| project.getState() == 2
							|| project.getState() == 3
							|| project.getState() == 5
							|| project.getState() == 7) {
						try {
							Messagebox.show("业务办还未审核通过，不能进行归档！", "提示",
									Messagebox.OK, Messagebox.INFORMATION);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						return;
					}
					RecordCodeWin win = (RecordCodeWin) Executions
							.createComponents(
									"/admin/jxkh/busiAudit/project/recordcode.zul",
									null, null);
					win.setAward(project);
					win.doModal();
					win.initShow();

					if (cx3 == true) {
						onClick$query3();
					} else {
						initShow();
					}
				}

			});
			c7.appendChild(addrecode);
			String strC8;
			switch (project.getState()) {
			case Jxkh_Project.NOT_AUDIT:
				strC8 = "待审核";
				break;
			case Jxkh_Project.DEPT_PASS:
				strC8 = "部门通过";
				break;
			case Jxkh_Project.First_Dept_Pass:
				strC8 = "部门审核中";
				break;
			case Jxkh_Project.DEPT_NOT_PASS:
				strC8 = "部门不通过";
				break;
			case Jxkh_Project.BUSINESS_PASS:
				strC8 = "业务办通过";
				break;
			case Jxkh_Project.BUSINESS_NOT_PASS:
				strC8 = "业务办不通过";
				break;
			case Jxkh_Project.SAVE_RECORD:
				strC8 = "归档";
				break;
			case JXKH_MEETING.BUSINESS_TEMP_PASS:
				strC8 = "业务办暂缓通过";
				break;
			default:
				strC8 = "待审核";
				break;
			}
			Listcell c8 = new Listcell(strC8);
			c8.setStyle("color:red");
			c7.setTooltiptext("点击填写审核意见");
			// 弹出审核意见事件
			c8.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event event) throws Exception {
					AdviceWin w = (AdviceWin) Executions.createComponents(
							"/admin/jxkh/busiAudit/project/advice.zul", null,
							null);
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
			item.appendChild(c7);
		}
	}

	
	/**
	 * <li>功能描述：批量审核。 void
	 * 
	 * @author YuSong
	 */
	public void onClick$passAll3() {
		if (zsListbox.getSelectedItems() == null
				|| zsListbox.getSelectedItems().size() <= 0) {
			try {
				Messagebox.show("请选择要审核项目！", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		Iterator<?> items = zsListbox.getSelectedItems().iterator();
		List<Jxkh_Project> awardList = new ArrayList<Jxkh_Project>();
		Jxkh_Project award = new Jxkh_Project();
		while (items.hasNext()) {
			Listitem item = (Listitem) items.next();
			award = (Jxkh_Project) item.getValue();
			awardList.add(award);
		}
		BatchAuditWin win = (BatchAuditWin) Executions.createComponents(
				"/admin/jxkh/busiAudit/project/batchAudit.zul", null, null);
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


	public void onClick$query3() {
		String nameSearch = name3.getValue();
		Short auditStateSearch = null;
		cx3 = true;
		if (auditState3.getSelectedItem().getValue().equals("")) {
			auditStateSearch = null;
		} else if (auditState3.getSelectedItem().getValue().equals("待审核")) {
			auditStateSearch = 0;
		} else if (auditState3.getSelectedItem().getValue().equals("部门通过")) {
			auditStateSearch = 1;
		} else if (auditState3.getSelectedItem().getValue().equals("部门审核中")) {
			auditStateSearch = 2;
		} else if (auditState3.getSelectedItem().getValue().equals("部门不通过")) {
			auditStateSearch = 3;
		} else if (auditState3.getSelectedItem().getValue().equals("业务办通过")) {
			auditStateSearch = 4;
		} else if (auditState3.getSelectedItem().getValue().equals("业务办不通过")) {
			auditStateSearch = 5;
		} else if (auditState3.getSelectedItem().getValue().equals("归档")) {
			auditStateSearch = 6;
		}else if (auditState3.getSelectedItem().getValue().equals("业务办暂缓通过")) {
			auditStateSearch = JXKH_MEETING.BUSINESS_TEMP_PASS;
		}
		Long indicatorId = null;
		if (rank3.getSelectedIndex() != 0) {
			Jxkh_BusinessIndicator qkType = (Jxkh_BusinessIndicator) rank3
					.getSelectedItem().getValue();
			indicatorId = qkType.getKbId();
		}
		String depName = null;
		if (dept3.getSelectedIndex() != 0) {
			WkTDept d = (WkTDept) dept3.getSelectedItem().getValue();
			depName = d.getKdName();
		}
		List<Jxkh_Project> projectList = jxkhProjectService.findCPByCondition(
				nameSearch, auditStateSearch, indicatorId, depName);
		zsListbox.setModel(new ListModelList(projectList));
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
		dept3.getItemAtIndex(0).setSelected(true);
	}

	

	// 导出
	public void onClick$export3() throws WriteException, IOException {
		if (zsListbox.getSelectedCount() == 0) {
			try {
				Messagebox.show("提示请选择要导出的数据", "提示", Messagebox.OK,
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
			String Title = "奖励情况";
			List<String> titlelist = new ArrayList<String>();
			titlelist.add("序号");
			titlelist.add("项目名称");
			titlelist.add("项目成员");
			titlelist.add("院内部门");

			titlelist.add("项目级别");
			titlelist.add("项目负责人");
			titlelist.add("合同始期");
			titlelist.add("信息填写人");
			titlelist.add("审核状态");
			String c[][] = new String[expertlist.size()][titlelist.size()];
			// 从SQL中读数据
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
						member += mem.getName() + "、";
					}
					c[j][2] = member.substring(0, member.length() - 1);
				}
				List<Jxkh_ProjectDept> dlist = jxkhProjectService
						.findProjectDept(award);
				String dept = "";
				if (dlist.size() != 0) {
					for (int m = 0; m < dlist.size(); m++) {
						Jxkh_ProjectDept de = (Jxkh_ProjectDept) dlist.get(m);
						dept += de.getName() + "、";
					}
					c[j][3] = dept.substring(0, dept.length() - 1);
				}
				c[j][4] = "院内自设项目";
				c[j][5] = award.getHeader();
				c[j][6] = award.getBeginDate();
				c[j][7] = award.getInfoWriter();
				String strC8;
				switch (award.getState()) {
				case Jxkh_Project.NOT_AUDIT:
					strC8 = "待审核";
					break;
				case Jxkh_Project.DEPT_PASS:
					strC8 = "部门通过";
					break;
				case Jxkh_Project.First_Dept_Pass:
					strC8 = "部门审核中";
					break;
				case Jxkh_Project.DEPT_NOT_PASS:
					strC8 = "部门不通过";
					break;
				case Jxkh_Project.BUSINESS_PASS:
					strC8 = "业务办通过";
					break;
				case Jxkh_Project.BUSINESS_NOT_PASS:
					strC8 = "业务办不通过";
					break;
				case Jxkh_Project.SAVE_RECORD:
					strC8 = "归档";
					break;
				case JXKH_MEETING.BUSINESS_TEMP_PASS:
					strC8 = "业务办暂缓通过";
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
