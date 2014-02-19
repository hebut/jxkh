package org.iti.jxkh.business.project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import jxl.write.WriteException;
import org.iti.gh.common.util.ExportExcel;
import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxkh.audit.project.HPWindow;
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

public class DeptHprojectWindow extends Window implements AfterCompose {

	/**
	 * @author SongYu
	 */
	private static final long serialVersionUID = -4018673049983784473L;
	private Textbox name2;
	private Groupbox cxtj2;
	Boolean cx2;
	private Long indicatorId2;
	private YearListbox year2;
	private String yearSearch2;
	private String nameSearch2;
	private Short auditStateSearch2;
	private Listbox hxListbox, auditState2, rank2;
	private Paging hxPaging;
	WkTUser user;
	private JxkhProjectService jxkhProjectService;
	private JXKHMeetingService jxkhMeetingService;
	public static final Integer qikan = 41;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		year2.initYearListbox("");
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");// 获取当前登录用户
		hxListbox.setItemRenderer(new HProjectRenderer());
		auditState2.setItemRenderer(new auditStateRenderer());
		hxPaging.addEventListener("onPaging", new HXPagingListener());
		initShow();
		rank2.setItemRenderer(new qkTypeRenderer());
		List<Jxkh_BusinessIndicator> holdList = new ArrayList<Jxkh_BusinessIndicator>();
		holdList.add(new Jxkh_BusinessIndicator());
		if (jxkhMeetingService.findRank(qikan) != null) {
			holdList.addAll(jxkhMeetingService.findRank(qikan));
		}
		rank2.setModel(new ListModelList(holdList));
		rank2.setSelectedIndex(0);
	}

	/** 项目级别列表渲染器 */
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
		String[] a = { "","填写中", "待审核", "部门审核中", "部门通过", "部门不通过","业务办暂缓通过", "业务办通过", "业务办不通过",
		"归档" };
		List<String> auditStateList = new ArrayList<String>();
		for (int i = 0; i < 8; i++) {
			auditStateList.add(a[i]);
		}
		cx2 = false;
		auditState2.setModel(new ListModelList(auditStateList));
		auditState2.setSelectedIndex(0);
		List<Jxkh_Project> hProjectList = jxkhProjectService.findAllHPByDept1(user.getDept().getKdNumber());
		hxListbox.setModel(new ListModelList(hProjectList));
		
		hxPaging.setTotalSize(hProjectList.size());
	}

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

	public class HXPagingListener implements EventListener {
		@Override
		public void onEvent(Event event) throws Exception {
			if (cx2 == true) {
				List<Jxkh_Project> projectList = jxkhProjectService.findHPByCondition(nameSearch2, auditStateSearch2, indicatorId2, yearSearch2, user.getDept().getKdNumber(), hxPaging.getActivePage(), hxPaging.getPageSize());

				hxListbox.setModel(new ListModelList(projectList));
			} else {
				List<Jxkh_Project> hProjectList = jxkhProjectService.findAllHPByDept(user.getDept().getKdNumber(), hxPaging.getActivePage(), hxPaging.getPageSize());
				hxListbox.setModel(new ListModelList(hProjectList));
			}
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
			Listcell c2 = new Listcell(project.getName().length()>10?project.getName().substring(0, 10)+"...":project.getName());
			c2.setTooltiptext(project.getName());
			c2.setStyle("color:blue");
			c2.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {

					HPWindow w = (HPWindow) Executions.createComponents("/admin/personal/businessdata/project/showHP.zul", null, null);
					try {
						w.setTitle("查看项目信息");
						w.setProject(project);
						//w.setDept("dept");
						w.initShow();
						w.initWindow();
						w.doModal();

					} catch (SuspendNotAllowedException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					initShow();
				}
			});
			//项目级别
			Listcell c3 = new Listcell("");
			if (project.getRank() == null) {
				c3.setLabel("");
			} else {
				c3.setLabel(project.getRank().getKbName());
			}
			//积分年度
			Listcell c4 = new Listcell(project.getjxYear());
			//该项得分
			Listcell c5 = new Listcell(project.getScore() == null ? "0" : project.getScore().toString());
			//填写人
			Listcell c6 = new Listcell();
			WkTUser user = jxkhProjectService.findWktUserByMemberUserId(project.getInfoWriter());
			c6.setLabel(user.getKuName());
			//审核状态
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
			case JXKH_MEETING.WRITING:
				strC8 = "填写中";
				break;
			case JXKH_MEETING.BUSINESS_TEMP_PASS:
				strC8 = "业务办暂缓通过";
				break;
			default:
				strC8 = "归档";
				break;
			}
			Listcell c8 = new Listcell(strC8);
			c8.setStyle("color:red");
			c8.setTooltiptext("点击查看审核结果");
			c8.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					AdviceWindow pa = (AdviceWindow) Executions.createComponents("/admin/personal/businessdata/project/advice.zul", null, null);
					pa.setMeeting(project);
					pa.initWindow();
					pa.doModal();
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

	/**
	 * <li>功能描述：添加横向项目。 void
	 * 
	 * @author YuSong
	 */
	public void onClick$add2() {
		AddHPWindow w = (AddHPWindow) Executions.createComponents("/admin/personal/businessdata/project/addHP.zul", null, null);
		try {
			w.setDept1("dept");
			w.initShow();
			w.doModal();
		} catch (SuspendNotAllowedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		initShow();
	}

	/**
	 * <li>功能描述：删除横向项目。 void
	 * 
	 * @author YuSong
	 */
	public void onClick$del2() throws InterruptedException {
		if (hxListbox.getSelectedItems() == null || hxListbox.getSelectedItems().size() <= 0) {
			try {
				Messagebox.show("请选择要删除的横向项目！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		Messagebox.show("确定删除吗?", "确定", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
			public void onEvent(Event evt) throws InterruptedException {
				if (evt.getName().equals("onOK")) {
					Iterator<?> it = hxListbox.getSelectedItems().iterator();
					while (it.hasNext()) {
						Listitem item = (Listitem) it.next();
						Jxkh_Project project = (Jxkh_Project) item.getValue();

						if (project.getState() == Jxkh_Project.DEPT_PASS || project.getState() == Jxkh_Project.First_Dept_Pass || project.getState() == Jxkh_Project.BUSINESS_PASS || project.getState().shortValue() == JXKH_MEETING.BUSINESS_TEMP_PASS || project.getState() == Jxkh_Project.SAVE_RECORD) {
							try {
								Messagebox.show("部门或业务办审核通过的不能删除！", "提示", Messagebox.OK, Messagebox.INFORMATION);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							return;
						}
						jxkhProjectService.delete(project);

					}
					try {
						Messagebox.show("横向项目删除成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		initShow();
	}

	public void onClick$query2() {
		nameSearch2 = name2.getValue();
		auditStateSearch2 = null;
		if (auditState2.getSelectedItem().getValue().equals("")) {
			auditStateSearch2 = null;
		} else if (auditState2.getSelectedItem().getValue().equals("待审核")) {
			auditStateSearch2 = 0;
		} else if (auditState2.getSelectedItem().getValue().equals("部门通过")) {
			auditStateSearch2 = 1;
		} else if (auditState2.getSelectedItem().getValue().equals("部门审核中")) {
			auditStateSearch2 = 2;
		} else if (auditState2.getSelectedItem().getValue().equals("部门不通过")) {
			auditStateSearch2 = 3;
		} else if (auditState2.getSelectedItem().getValue().equals("业务办通过")) {
			auditStateSearch2 = 4;
		} else if (auditState2.getSelectedItem().getValue().equals("业务办不通过")) {
			auditStateSearch2 = 5;
		} else if (auditState2.getSelectedItem().getValue().equals("归档")) {
			auditStateSearch2 = 6;
		}else if (auditState2.getSelectedItem().getValue().equals("填写中")) {
			auditStateSearch2 = JXKH_MEETING.WRITING;
		}else if (auditState2.getSelectedItem().getValue().equals("业务办暂缓通过")) {
			auditStateSearch2 = JXKH_MEETING.BUSINESS_TEMP_PASS;
		}
		indicatorId2 = null;
		if (rank2.getSelectedIndex() != 0) {
			Jxkh_BusinessIndicator qkType = (Jxkh_BusinessIndicator) rank2.getSelectedItem().getValue();
			indicatorId2 = qkType.getKbId();
		}
		yearSearch2 = null;
		if (year2.getSelectedIndex() != 0 && year2.getSelectedItem() != null)
			yearSearch2 = year2.getSelectedItem().getValue() + "";

		List<Jxkh_Project> projectList1 = jxkhProjectService.findHPByCondition2(nameSearch2, auditStateSearch2, indicatorId2, yearSearch2, user.getDept().getKdNumber());
		hxPaging.setTotalSize(projectList1.size());
		List<Jxkh_Project> projectList = jxkhProjectService.findHPByCondition2(nameSearch2, auditStateSearch2, indicatorId2, yearSearch2, user.getDept().getKdNumber(), hxPaging.getActivePage(), hxPaging.getPageSize());
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

	// 导出
	public void onClick$export2() throws WriteException, IOException {
		if (hxListbox.getSelectedCount() == 0) {
			try {
				Messagebox.show("提示请选择要导出的数据", "提示", Messagebox.OK, Messagebox.EXCLAMATION);
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
			String fileName = ConvertUtil.convertDateString(now) + "hengxiangxiangmu" + ".xls";
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
				List<Jxkh_ProjectMember> mlist = jxkhProjectService.findProjectMember(award);
				if (mlist.size() != 0) {
					for (int m = 0; m < mlist.size(); m++) {
						Jxkh_ProjectMember mem = (Jxkh_ProjectMember) mlist.get(m);
						member += mem.getName() + "、";
					}
					c[j][2] = member.substring(0, member.length() - 1);
				}
				List<Jxkh_ProjectDept> dlist = jxkhProjectService.findProjectDept(award);
				String dept = "";
				if (dlist.size() != 0) {
					for (int m = 0; m < dlist.size(); m++) {
						Jxkh_ProjectDept de = (Jxkh_ProjectDept) dlist.get(m);
						dept += de.getName() + "、";
					}
					c[j][3] = dept.substring(0, dept.length() - 1);
				}
				c[j][4] = "横向项目";
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
				case JXKH_MEETING.WRITING:
					strC8 = "填写中";
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
