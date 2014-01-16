package org.iti.jxkh.deptbusiness.report;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxkh.business.award.ChooseDeptWin;
import org.iti.jxkh.business.award.ChooseMemberWin;
import org.iti.jxkh.business.meeting.AssignDeptWindow;
import org.iti.jxkh.business.meeting.UpfileWindow;
import org.iti.jxkh.entity.JXKH_MEETING;
import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.entity.Jxkh_Report;
import org.iti.jxkh.entity.Jxkh_ReportDept;
import org.iti.jxkh.entity.Jxkh_ReportFile;
import org.iti.jxkh.entity.Jxkh_ReportMember;
import org.iti.jxkh.service.BusinessIndicatorService;
import org.iti.jxkh.service.JXKHMeetingService;
import org.iti.jxkh.service.JxkhAwardService;
import org.iti.jxkh.service.JxkhReportService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Row;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.iti.common.util.ConvertUtil;
import com.iti.common.util.PropertiesLoader;
import com.iti.common.util.UploadUtil;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;

public class AddReportWin extends Window implements AfterCompose {

	/**
	 * @author ZhangyuGuang
	 */
	private static final long serialVersionUID = -3024844576435140022L;
	private Tab baseTab, fileTab, scoreTab;
	private Listbox personScore, departmentScore;
	private Toolbarbutton tempSave, save, close, submitScore, ups1, ups2, ups3;
	private Textbox name, reportMember, reportDept, coCompany, filed;
	private Label submitName;
	private Listbox type, leader;
	private Row outDeptRow;
	private Radio cooperationTrue, cooperationFalse, firstSignTrue, firstSignFalse;
	private Datebox date;
	private Jxkh_Report report;
	private Button print;
	private YearListbox jiFenTime;
	private List<WkTUser> memberList = new ArrayList<WkTUser>();
	private List<WkTDept> deptList = new ArrayList<WkTDept>();
	private List<Jxkh_ReportMember> reportMemberList = new ArrayList<Jxkh_ReportMember>();
	private List<Jxkh_ReportDept> reportDeptList = new ArrayList<Jxkh_ReportDept>();
	private JxkhReportService jxkhReportService;
	private JxkhAwardService jxkhAwardService;
	private JXKHMeetingService jxkhMeetingService;
	private BusinessIndicatorService businessIndicatorService;
	private WkTUser user;
	public static final Long lead = (long) 52;

	private Listbox applyList1, applyList2, applyList3;

	private String audit;// 如果有值，就表示是部门审核调用这个后台，其保存按钮就隐藏

	public void setAudit(String audit) {
		this.audit = audit;
	}

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		jiFenTime.initYearListbox("");
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");// 获取当前登录用户
		leader.setItemRenderer(new reportLeaderRenderer());
		type.setItemRenderer(new reportTypeRenderer());
		personScore.setItemRenderer(new personScoreRenderer());
		departmentScore.setItemRenderer(new departmentScoreRenderer());
		// 点击radio"cooperationTrue"触发事件
		cooperationTrue.addEventListener(Events.ON_CHECK, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				outDeptRow.setVisible(true);
			}
		});
		// 点击radio"cooperationFalse"触发事件
		cooperationFalse.addEventListener(Events.ON_CHECK, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				outDeptRow.setVisible(false);
			}
		});

		// 点击绩分页面时会议信息页面和文档信息页面的保存和退出按钮隐藏
		scoreTab.addEventListener(Events.ON_CLICK, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				save.setVisible(false);
				tempSave.setVisible(false);
				close.setVisible(false);
				if (report != null) {
					// 判断当前登录人员是否是信息填写人的部门负责人(20120314)是否是主审部门
					List<WkTUser> wktUser = jxkhMeetingService.findWkTUserByManId(report.getSubmitId());
					WkTUser u = wktUser.get(0);
					Jxkh_ReportDept d = jxkhReportService.findReportDept(report, user.getDept().getKdNumber());
					if (user.getDept().getKdNumber().equals(u.getDept().getKdNumber()) || d.getRank() == 1) {
						if (report.getState() == null || report.getState() == 0 || report.getState() == 3 || report.getState() == 5) {
							submitScore.setVisible(true);
						} else {
							submitScore.setVisible(false);
						}
					} else
						submitScore.setVisible(false);
				}
				if (audit == "AUDIT")
					submitScore.setVisible(false);
			}
		});
		// 点击会议信息页面和文档信息页面时根据审核状态来控制保存和退出按钮的显隐性
		baseTab.addEventListener(Events.ON_CLICK, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				save.setVisible(true);
				tempSave.setVisible(true);
				close.setVisible(true);
				if (report != null) {
					if (report.getState() == null || report.getState() == 0 || report.getState().shortValue() == JXKH_MEETING.WRITING || report.getState() == 3 || report.getState() == 5) {
						save.setVisible(true);
						tempSave.setVisible(true);
					} else {
						save.setVisible(false);
						tempSave.setVisible(false);
					}
					// 判断当前登录人员是否是信息填写人的部门负责人(20120314)
					List<WkTUser> wktUser = jxkhMeetingService.findWkTUserByManId(report.getSubmitId());
					WkTUser u = wktUser.get(0);
					if (!user.getDept().getKdNumber().equals(u.getDept().getKdNumber())) {
						save.setVisible(false);
						tempSave.setVisible(false);
					}
				}
				if (audit == "AUDIT") {
					save.setVisible(false);
					tempSave.setVisible(false);
				}
			}
		});
		fileTab.addEventListener(Events.ON_CLICK, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				save.setVisible(true);
				tempSave.setVisible(true);
				close.setVisible(true);
				if (report != null) {
					if (report.getState() == null || report.getState() == 0 || report.getState().shortValue() == JXKH_MEETING.WRITING || report.getState() == 3 || report.getState() == 5) {
						save.setVisible(true);
						tempSave.setVisible(true);
					} else {
						save.setVisible(false);
						tempSave.setVisible(false);
					}
					// 判断当前登录人员是否是信息填写人的部门负责人(20120314)
					List<WkTUser> wktUser = jxkhMeetingService.findWkTUserByManId(report.getSubmitId());
					WkTUser u = wktUser.get(0);
					if (!user.getDept().getKdNumber().equals(u.getDept().getKdNumber())) {
						save.setVisible(false);
						tempSave.setVisible(false);
					}
				}
				if (audit == "AUDIT") {
					save.setVisible(false);
					tempSave.setVisible(false);
				}
			}
		});
	}

	public void initWindow() {
		String memberName = "";
		String deptName = "";
		if (report == null) {
			cooperationFalse.setChecked(true);
			System.out.print(user.getKuName());
			submitName.setValue(user.getKuName());
			scoreTab.setDisabled(true);
			if (audit == "AUDIT") {
				save.setVisible(false);
				tempSave.setVisible(false);
			}
		} else {
			scoreTab.setDisabled(false);
			if (report.getState() == null || report.getState() == 0 || report.getState().shortValue() == JXKH_MEETING.WRITING || report.getState() == 3 || report.getState() == 5) {
				ups1.setDisabled(false);
				ups2.setDisabled(false);
				ups3.setDisabled(false);
			} else {
				save.setVisible(false);
				tempSave.setVisible(false);
				ups1.setDisabled(true);
				ups2.setDisabled(true);
				ups3.setDisabled(true);
				if (audit != "AUDIT")
					try {
						Messagebox.show("部门已经审核通过或者业务办已经审核通过，您只能查看，无权再编辑 ！", "提示", Messagebox.OK, Messagebox.ERROR);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			}
			// 判断当前登录人员是否是信息填写人的部门负责人(20120314)
			List<WkTUser> wktUser = jxkhMeetingService.findWkTUserByManId(report.getSubmitId());
			WkTUser u = wktUser.get(0);
			if (!user.getDept().getKdNumber().equals(u.getDept().getKdNumber())) {
				save.setVisible(false);
				tempSave.setVisible(false);
				ups1.setDisabled(true);
				ups2.setDisabled(true);
				ups3.setDisabled(true);
			}
			if (audit == "AUDIT") {
				save.setVisible(false);
				tempSave.setVisible(false);
				ups1.setDisabled(true);
				ups2.setDisabled(true);
				ups3.setDisabled(true);
			}
			jiFenTime.initYearListbox(report.getjxYear());
			print.setHref("/print.action?n=report&id=" + report.getId());
			name.setValue(report.getName());
			if (report.getFirstSign() == 1) {
				firstSignTrue.setChecked(true);
				firstSignFalse.setChecked(false);
			} else {
				firstSignTrue.setChecked(false);
				firstSignFalse.setChecked(true);
			}
			if (report.getCoState() == 1) {
				cooperationTrue.setChecked(true);
				cooperationFalse.setChecked(false);
				outDeptRow.setVisible(true);
				coCompany.setValue(report.getCoCompany());
			} else {
				cooperationTrue.setChecked(false);
				cooperationFalse.setChecked(true);
			}
			date.setValue(ConvertUtil.convertDate(report.getDate()));
			filed.setValue(report.getFiled());
			submitName.setValue(report.getSubmitName());
			reportMemberList = report.getReportMember();
			for (int i = 0; i < reportMemberList.size(); i++) {
				Jxkh_ReportMember mem = (Jxkh_ReportMember) reportMemberList.get(i);
				memberName += mem.getName() + "、";
				if (mem.getType() == 0) {
					WkTUser user = jxkhAwardService.findWktUserByMemberUserId(mem.getPersonId());
					memberList.add(user);
				} else {
					WkTUser out = new WkTUser();
					WkTDept d = new WkTDept();
					d.setKdName("校外");
					out.setDept(d);
					out.setKuId(new Random().nextLong());
					out.setKuLid("校外");
					out.setKuName(mem.getName());
					out.setType((short) 1);
					memberList.add(out);
				}
			}
			reportMember.setValue(memberName.substring(0, memberName.length() - 1));

			reportDeptList = report.getReprotDept();
			for (int i = 0; i < reportDeptList.size(); i++) {
				Jxkh_ReportDept dept = (Jxkh_ReportDept) reportDeptList.get(i);
				deptName += dept.getName() + "、";
				WkTDept depts = jxkhAwardService.findWkTDeptByDept(dept.getDeptId());
				deptList.add(depts);
			}

			reportDept.setValue(deptName.substring(0, deptName.length() - 1));

			// 新的附件初始化20120306
			arrList1.clear();
			arrList2.clear();
			arrList3.clear();
			Set<?> files = report.getReportFile();
			Object[] file = files.toArray();
			for (int i = 0; i < file.length; i++) {
				Jxkh_ReportFile f = (Jxkh_ReportFile) file[i];
				if (f.getBelongType().equals("领导正面批示文件")) {
					String[] arr = new String[4];
					arr[0] = f.getPath();
					arr[1] = f.getName();
					arr[2] = f.getDate();
					arr[3] = f.getBelongType();
					arrList1.add(arr);
				}
				if (f.getBelongType().equals("采用证明")) {
					String[] arr = new String[4];
					arr[0] = f.getPath();
					arr[1] = f.getName();
					arr[2] = f.getDate();
					arr[3] = f.getBelongType();
					arrList2.add(arr);
				}
				if (f.getBelongType().equals("报告")) {
					String[] arr = new String[4];
					arr[0] = f.getPath();
					arr[1] = f.getName();
					arr[2] = f.getDate();
					arr[3] = f.getBelongType();
					arrList3.add(arr);
				}
			}
			applyList1.setItemRenderer(new FilesRenderer1());
			applyList1.setModel(new ListModelList(arrList1));
			applyList2.setItemRenderer(new FilesRenderer2());
			applyList2.setModel(new ListModelList(arrList2));
			applyList3.setItemRenderer(new FilesRenderer3());
			applyList3.setModel(new ListModelList(arrList3));
		}
		initListbox();
	}

	private void initListbox() {
		List<Jxkh_BusinessIndicator> rankList = new ArrayList<Jxkh_BusinessIndicator>();
		rankList.add(new Jxkh_BusinessIndicator());
		rankList.addAll(jxkhAwardService.findRank(lead));
		leader.setModel(new ListModelList(rankList));
		leader.setSelectedIndex(0);

		String[] report_Types = { "", "调研报告", "分析报告" };
		List<String> reportTpyeList = new ArrayList<String>();
		for (int i = 0; i < 3; i++) {
			reportTpyeList.add(report_Types[i]);
		}
		type.setModel(new ListModelList(reportTpyeList));

		personScore.setModel(new ListModelList(reportMemberList));
		departmentScore.setModel(new ListModelList(reportDeptList));
	}

	private void checkNull() {
		if (reportMember.getValue() == null || reportMember.getValue().equals("")) {
			try {
				Messagebox.show("请选择报告成员！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		if (reportDept.getValue() == null || reportDept.getValue().equals("")) {
			try {
				Messagebox.show("请选择报告部门！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		if (type.getSelectedItem().getIndex() == 0) {
			try {
				Messagebox.show("请选择报告种类！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		if (leader.getSelectedItem().getIndex() == 0) {
			try {
				Messagebox.show("请选择报告的批示领导！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		if (jiFenTime.getSelectedIndex() == 0 || jiFenTime.getSelectedItem() == null) {
			try {
				Messagebox.show("请选择绩分年度！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		if(report != null && report.getId() != null) {
			if(arrList1.size() <=0 && arrList2.size() <= 0) {
				try {
					Messagebox.show("领导正面批示文件和采用证明要至少上传一项！");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return;
			}
		}
	}

	public void onClick$tempSave() {
		checkNull();
		if (report == null) {
			report = new Jxkh_Report();
			report.setSubmitId(user.getKuLid());
			report.setSubmitName(user.getKuName());
			report.setSubmitTime(ConvertUtil.convertDateAndTimeString(new Date()));
			setEntity();
		} else {
			setEntity();
		}
		report.setState(JXKH_MEETING.WRITING);
		try {
			jxkhReportService.saveOrUpdate(report);
			Messagebox.show("报告保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				Messagebox.show("报告保存失败，请重试！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		this.onClose();
	}

	public void onClick$save() {
		checkNull();
		if (report == null) {
			report = new Jxkh_Report();
			report.setSubmitId(user.getKuLid());
			report.setSubmitName(user.getKuName());
			report.setSubmitTime(ConvertUtil.convertDateAndTimeString(new Date()));
			setEntity();
		} else {
			setEntity();
		}
		report.setState(Jxkh_Report.NOT_AUDIT);
		try {
			jxkhReportService.saveOrUpdate(report);
			Messagebox.show("报告保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				Messagebox.show("报告保存失败，请重试！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		this.onClose();
	}

	private Float computeScore(Jxkh_BusinessIndicator kdRank, Short sign) {
		float result = 0f;
		String type = "";
		float res = 0f;
		Properties property = PropertiesLoader.loader("title", "title.properties");
		float reportgov = 0, reportting = 0;
		if (kdRank.getKbName().equals(property.getProperty("reportgov"))) {
			if (sign.shortValue() == Jxkh_Report.YES) {
				reportgov += 1f;
				result = reportgov;
				type = property.getProperty("reportgov");
			} else if (sign.shortValue() == Jxkh_Report.NO) {
				reportting += 1f;
				result = reportting;
				type = property.getProperty("reportting");
			}

		} else if (kdRank.getKbName().equals(property.getProperty("reportting"))) {
			if (sign.shortValue() == Jxkh_Report.YES) {
				reportting += 1f;
				result = reportting;
				type = property.getProperty("reportting");
			} else if (sign.shortValue() == Jxkh_Report.NO) {
				reportting += 1f;
				result = reportting;
				type = property.getProperty("reportscience");
			}
		} else if (kdRank.getKbName().equals(property.getProperty("reportscience"))) {
			if (sign.shortValue() == Jxkh_Report.YES) {
				reportting += 1f;
				result = reportting;
				type = property.getProperty("reportscience");
			} else if (sign.shortValue() == Jxkh_Report.NO) {
				reportting += 1f * 0.5;
				result = reportting;
				type = property.getProperty("reportscience");
			}
		}
		Jxkh_BusinessIndicator bi = (Jxkh_BusinessIndicator) businessIndicatorService.getEntityByName(type);
		if (bi == null) {
			res += result * 0;
		} else {
			res += result * bi.getKbScore() * bi.getKbIndex();
		}
		return Float.valueOf(res);

	}

	public void setEntity() {
		Short sign;
		Jxkh_BusinessIndicator kdRank = new Jxkh_BusinessIndicator();
		report.setName(name.getValue());
		report.setTempState("0000000");// 为临时状态赋初值
		report.setDep1Reason("");
		report.setDep2Reason("");
		report.setDep3Reason("");
		report.setDep4Reason("");
		report.setDep5Reason("");
		report.setDep6Reason("");
		report.setDep7Reason("");
		report.setbAdvice("");
		report.setjxYear(jiFenTime.getSelectedItem().getValue() + "");
		if (firstSignTrue.isChecked()) {
			report.setFirstSign(Jxkh_Report.YES);
			sign = Jxkh_Report.YES;
		} else {
			report.setFirstSign(Jxkh_Report.NO);
			sign = Jxkh_Report.NO;
		}
		if ((Jxkh_BusinessIndicator) leader.getSelectedItem().getValue() != null) {
			report.setLeader((Jxkh_BusinessIndicator) leader.getSelectedItem().getValue());
			kdRank = (Jxkh_BusinessIndicator) leader.getSelectedItem().getValue();
		}
		Float score = computeScore(kdRank, sign);
		report.setScore(score);
		if ((Jxkh_BusinessIndicator) leader.getSelectedItem().getValue() != null) {
			report.setLeader((Jxkh_BusinessIndicator) leader.getSelectedItem().getValue());
		}
		if ((String) type.getSelectedItem().getValue() != null) {
			report.setType((String) type.getSelectedItem().getValue());
		}
		if (cooperationTrue.isChecked()) {
			report.setCoState(Jxkh_Report.YES);
			report.setCoCompany(coCompany.getValue());
		} else {
			report.setCoState(Jxkh_Report.NO);
			report.setCoCompany(null);
		}

		report.setDate(ConvertUtil.convertDateString(date.getValue()));
		report.setFiled(filed.getValue());
		int j = 1;
		List<Jxkh_ReportMember> mlist = new ArrayList<Jxkh_ReportMember>();
		List<Jxkh_ReportDept> dlist = new ArrayList<Jxkh_ReportDept>();
		if (report.getReportMember() != null) {
			mlist = report.getReportMember();
			jxkhAwardService.deleteAll(mlist);
			mlist.clear();
		}
		for (WkTUser user : memberList) {
			Jxkh_ReportMember member = new Jxkh_ReportMember();
			member.setReport(report);
			member.setDept(user.getDept().getKdName());
			member.setName(user.getKuName());
			member.setPersonId(user.getKuLid());
			member.setRank(j);
			switch (user.getType()) {
			case WkTUser.TYPE_IN:
				member.setType(Short.valueOf(WkTUser.TYPE_IN));
				break;
			case WkTUser.TYPE_OUT:
				member.setType(Short.valueOf(WkTUser.TYPE_OUT));
				break;
			}

			// 人员的比例和分值0503
			float percent = 0;
			int len = memberList.size();
			switch (len) {
			case 1:
				percent = 10;
				break;
			case 2:
				if (j == 1)
					percent = 7;
				else if (j == 2)
					percent = 3;
				break;
			case 3:
				if (j == 1)
					percent = 6;
				else if (j == 2)
					percent = 3;
				else if (j == 3)
					percent = 1;
				break;
			case 4:
				if (j == 1)
					percent = 5;
				else if (j == 2)
					percent = 3;
				else if (j == 3)
					percent = 1;
				else if (j == 4)
					percent = 1;
				break;
			case 5:
				if (j == 1)
					percent = 5;
				else if (j == 2)
					percent = 2;
				else if (j == 3)
					percent = 1;
				else if (j == 4)
					percent = 1;
				else if (j == 5)
					percent = 1;
				break;
			case 6:
				if (j == 1)
					percent = 5;
				else if (j == 2)
					percent = 2;
				else if (j == 3)
					percent = 1;
				else if (j == 4)
					percent = 1;
				else if (j == 5)
					percent = 0.5f;
				else if (j == 6)
					percent = 0.5f;
				break;
			case 7:
				if (j == 1)
					percent = 5;
				else if (j == 2)
					percent = 1.5f;
				else if (j == 3)
					percent = 1;
				else if (j == 4)
					percent = 1;
				else if (j == 5)
					percent = 0.5f;
				else if (j == 6)
					percent = 0.5f;
				else if (j == 7)
					percent = 0.5f;
				break;
			default:
				if (j == 1)
					percent = 5;
				else if (j == 2)
					percent = 1.5f;
				else if (j == 3)
					percent = 1;
				else if (j == 4)
					percent = 1;
				else if (j == 5)
					percent = 0.5f;
				else if (j == 6)
					percent = 0.5f;
				else if (j == 7)
					percent = 0.5f;
				else
					percent = 0;
				break;

			}
			float f = 0;
			if (percent != 0)
				f = score * percent / 10;
			float sco = (float) (Math.round(f * 1000)) / 1000;// 保留三位小数
			member.setPer(percent);
			member.setScore(sco);
			if (user.getDept().getKdNumber().equals(WkTDept.guanlidept))
				member.setAssignDep(deptList.get(0).getKdName());
			else
				member.setAssignDep(user.getDept().getKdName());
			j++;
			mlist.add(member);
		}
		report.setReportMember(mlist);
		int i = 1;
		if (report.getReprotDept() != null) {
			dlist = report.getReprotDept();
			jxkhAwardService.deleteAll(dlist);
			dlist.clear();
		}
		for (WkTDept wktDept : deptList) {
			Jxkh_ReportDept dept = new Jxkh_ReportDept();
			dept.setReport(report);
			dept.setName(wktDept.getKdName());
			dept.setDeptId(wktDept.getKdNumber());
			dept.setRank(i);
			i++;
			// 部门默认的分值0503
			float fen = 0.0f;
			for (int g = 0; g < mlist.size(); g++) {
				Jxkh_ReportMember m = mlist.get(g);
				if (m.getAssignDep().equals(dept.getName())) {
					fen += m.getScore();
				}
			}
			float scor = (float) (Math.round(fen * 1000)) / 1000;// 保留三位小数
			dept.setScore(scor);
			dlist.add(dept);
		}
		report.setReprotDept(dlist);
		// 保存附件数据
		List<String[]> arrList = new ArrayList<String[]>();
		Set<Jxkh_ReportFile> fset = new HashSet<Jxkh_ReportFile>();
		Set<Jxkh_ReportFile> oldFile = report.getReportFile();
		if (oldFile != null) {
			for (Object o : oldFile.toArray()) {
				Jxkh_ReportFile f = (Jxkh_ReportFile) o;
				if (f != null)
					jxkhReportService.delete(f);
			}
		}
		if (report.getId() == null) {
			jxkhReportService.save(report);
		}
		if (report.getReportFile() != null)
			report.getReportFile().clear();
		if (arrList1.size() != 0 || arrList1 != null)
			arrList.addAll(arrList1);
		if (arrList2.size() != 0 || arrList2 != null)
			arrList.addAll(arrList2);
		if (arrList3.size() != 0 || arrList3 != null)
			arrList.addAll(arrList3);
		for (int r = 0; r < arrList.size(); r++) {
			String[] s = arrList.get(r);
			Jxkh_ReportFile file = new Jxkh_ReportFile();
			file.setReport(report);
			file.setPath(s[0]);
			file.setName(s[1]);
			file.setDate(s[2]);
			file.setBelongType(s[3]);
			fset.add(file);
		}
		report.setReportFile(fset);
	}

	// 单击选择成员按钮，触发弹出选择成员页面事件
	public void onClick$chooseMember() throws SuspendNotAllowedException, InterruptedException {
		final ChooseMemberWin win = (ChooseMemberWin) Executions.createComponents("/admin/personal/deptbusinessdata/award/choosemember.zul", null, null);
		win.setMemberList(memberList);
		win.initWindow();
		win.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				memberList = win.getMemberList();
				String members = "";
				for (WkTUser user : memberList) {
					members += user.getKuName() + ",";
				}
				if (members.endsWith(",")) {
					members = members.substring(0, members.length() - 1);
				}
				reportMember.setValue(members);
				win.detach();
			}
		});
		win.doModal();

		// 根据选择的人员，部门自动与之对应 《2012年03月07号》
		deptList.clear();
		for (int i = 0; i < memberList.size(); i++) {
			int k = 0;// 当人员所在的部门和前面重复，k=1
			WkTUser ui = (WkTUser) memberList.get(i);
			for (int j = 0; j < i; j++) {
				WkTUser uj = (WkTUser) memberList.get(j);
				if (ui.getDept().getKdName() == "校外" || ui.getDept().getKdId().equals(uj.getDept().getKdId())) {
					k = 1;
				}
			}
			if (k == 0 && ui.getType() != 1)
				deptList.add(ui.getDept());
		}
		String depts = "";
		for (WkTDept dept : deptList) {
			depts += dept.getKdName() + ",";
		}
		if (depts.endsWith(",")) {
			depts = depts.substring(0, depts.length() - 1);
		}
		reportDept.setValue(null);
		reportDept.setValue(depts);
	}

	// 单击选择部门按钮，触发弹出选择成员页面事件
	public void onClick$chooseDept() throws SuspendNotAllowedException, InterruptedException {
		final ChooseDeptWin win = (ChooseDeptWin) Executions.createComponents("/admin/personal/deptbusinessdata/award/choosedept.zul", null, null);
		win.setDeptList(deptList);
		win.initWindow();
		win.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				deptList = win.getDeptList();
				String depts = "";
				for (WkTDept dept : deptList) {
					depts += dept.getKdName() + ",";
				}
				if (depts.endsWith(",")) {
					depts = depts.substring(0, depts.length() - 1);
				}
				reportDept.setValue(depts);
				win.detach();
			}
		});
		win.doModal();
	}

	public Jxkh_Report getReport() {
		return report;
	}

	public void setReport(Jxkh_Report report) {
		this.report = report;
	}

	// 报告领导渲染器
	public class reportLeaderRenderer implements ListitemRenderer {

		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			Jxkh_BusinessIndicator leader = (Jxkh_BusinessIndicator) data;
			item.setValue(leader);
			Listcell c0 = new Listcell();
			if (leader.getKbName() == null) {
				c0.setLabel("--请选择--");
			} else {
				c0.setLabel(leader.getKbName());
			}
			item.appendChild(c0);
			if (item.getIndex() == 0)
				item.setSelected(true);
			if (report != null && leader.equals(report.getLeader())) {
				item.setSelected(true);
			}
		}
	}

	// 报告种类渲染器
	public class reportTypeRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {
			String report_Type = (String) data;
			item.setValue(report_Type);
			Listcell c0 = new Listcell();
			if (report_Type == null || report_Type.equals(""))
				c0.setLabel("--请选择--");
			else {
				c0.setLabel(report_Type);
			}
			item.appendChild(c0);
			if (item.getIndex() == 0)
				item.setSelected(true);
			if (report != null && report_Type.equals(report.getType())) {
				item.setSelected(true);
			}
		}
	}

	/**
	 * <li>功能描述：关闭当前窗口。
	 */
	public void onClick$close() {
		this.onClose();
	}

	// 20120306
	private List<String[]> arrList1 = new ArrayList<String[]>();

	public void onClick$ups1() {
		final UpfileWindow w = (UpfileWindow) Executions.createComponents("/admin/personal/businessdata/meeting/upfile.zul", null, null);
		w.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				String filePath = (String) Executions.getCurrent().getAttribute("path");
				System.out.println("输出文件路径是path=" + filePath);
				String fileName = (String) Executions.getCurrent().getAttribute("title");
				System.out.println("输出的文件标题是title=" + fileName);
				String upTime = (String) Executions.getCurrent().getAttribute("upTime");
				System.out.println("输出文件的上传时间time=" + upTime);
				applyList1.setItemRenderer(new FilesRenderer1());
				String[] arr = new String[4];
				arr[0] = filePath;
				arr[1] = fileName;
				arr[2] = upTime;
				arr[3] = "领导正面批示文件";
				arrList1.add(arr);
				applyList1.setModel(new ListModelList(arrList1));
			}
		});
		try {
			w.doModal();
		} catch (SuspendNotAllowedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <li>功能描述：文档信息的listbox(20120306)
	 */
	public class FilesRenderer1 implements ListitemRenderer {

		@Override
		public void render(Listitem arg0, Object arg1) throws Exception {
			final String[] str = (String[]) arg1;
			arg0.setValue(str);
			Listcell c1 = new Listcell(arg0.getIndex() + 1 + "");
			Listcell c2 = new Listcell(str[1]);
			Listcell c3 = new Listcell(str[2]);
			Listcell c4 = new Listcell();
			Toolbarbutton downlowd = new Toolbarbutton();
			downlowd.setImage("/css/default/images/button/down.gif");
			downlowd.setParent(c4);
			downlowd.addEventListener(Events.ON_CLICK, new EventListener() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					download(str[0], str[1]);
				}
			});
			Toolbarbutton del = new Toolbarbutton();
			del.setImage("/css/default/images/button/del.gif");
			del.setParent(c4);
			del.addEventListener(Events.ON_CLICK, new EventListener() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					Messagebox.show("确定删除吗?", "确定", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
						public void onEvent(Event evt) throws InterruptedException {
							if (evt.getName().equals("onOK")) {
								arrList1.remove(str);
								applyList1.setModel(new ListModelList(arrList1));
							}
						}
					});
				}
			});
			arg0.appendChild(c1);
			arg0.appendChild(c2);
			arg0.appendChild(c3);
			arg0.appendChild(c4);
		}
	}

	// 20120306
	private List<String[]> arrList2 = new ArrayList<String[]>();

	public void onClick$ups2() {
		final UpfileWindow w = (UpfileWindow) Executions.createComponents("/admin/personal/businessdata/meeting/upfile.zul", null, null);
		w.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				String filePath = (String) Executions.getCurrent().getAttribute("path");
				String fileName = (String) Executions.getCurrent().getAttribute("title");
				String upTime = (String) Executions.getCurrent().getAttribute("upTime");
				applyList2.setItemRenderer(new FilesRenderer2());
				String[] arr = new String[4];
				arr[0] = filePath;
				arr[1] = fileName;
				arr[2] = upTime;
				arr[3] = "采用证明";
				arrList2.add(arr);
				applyList2.setModel(new ListModelList(arrList2));
			}
		});
		try {
			w.doModal();
		} catch (SuspendNotAllowedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <li>功能描述：文档信息的listbox(20120306)
	 */
	public class FilesRenderer2 implements ListitemRenderer {

		@Override
		public void render(Listitem arg0, Object arg1) throws Exception {
			final String[] str = (String[]) arg1;
			arg0.setValue(str);
			Listcell c1 = new Listcell(arg0.getIndex() + 1 + "");
			Listcell c2 = new Listcell(str[1]);
			Listcell c3 = new Listcell(str[2]);
			Listcell c4 = new Listcell();
			Toolbarbutton downlowd = new Toolbarbutton();
			downlowd.setImage("/css/default/images/button/down.gif");
			downlowd.setParent(c4);
			downlowd.addEventListener(Events.ON_CLICK, new EventListener() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					download(str[0], str[1]);
				}
			});
			Toolbarbutton del = new Toolbarbutton();
			del.setImage("/css/default/images/button/del.gif");
			del.setParent(c4);
			del.addEventListener(Events.ON_CLICK, new EventListener() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					Messagebox.show("确定删除吗?", "确定", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
						public void onEvent(Event evt) throws InterruptedException {
							if (evt.getName().equals("onOK")) {
								arrList2.remove(str);
								applyList2.setModel(new ListModelList(arrList2));
							}
						}
					});
				}
			});
			arg0.appendChild(c1);
			arg0.appendChild(c2);
			arg0.appendChild(c3);
			arg0.appendChild(c4);
		}
	}

	// 20120306
	private List<String[]> arrList3 = new ArrayList<String[]>();

	public void onClick$ups3() {
		final UpfileWindow w = (UpfileWindow) Executions.createComponents("/admin/personal/businessdata/meeting/upfile.zul", null, null);
		w.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				String filePath = (String) Executions.getCurrent().getAttribute("path");
				String fileName = (String) Executions.getCurrent().getAttribute("title");
				String upTime = (String) Executions.getCurrent().getAttribute("upTime");
				applyList3.setItemRenderer(new FilesRenderer3());
				String[] arr = new String[4];
				arr[0] = filePath;
				arr[1] = fileName;
				arr[2] = upTime;
				arr[3] = "报告";
				arrList3.add(arr);
				applyList3.setModel(new ListModelList(arrList3));
			}
		});
		try {
			w.doModal();
		} catch (SuspendNotAllowedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <li>功能描述：文档信息的listbox(20120306)
	 */
	public class FilesRenderer3 implements ListitemRenderer {

		@Override
		public void render(Listitem arg0, Object arg1) throws Exception {
			final String[] str = (String[]) arg1;
			arg0.setValue(str);
			Listcell c1 = new Listcell(arg0.getIndex() + 1 + "");
			Listcell c2 = new Listcell(str[1]);
			Listcell c3 = new Listcell(str[2]);
			Listcell c4 = new Listcell();
			Toolbarbutton downlowd = new Toolbarbutton();
			downlowd.setImage("/css/default/images/button/down.gif");
			downlowd.setParent(c4);
			downlowd.addEventListener(Events.ON_CLICK, new EventListener() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					download(str[0], str[1]);
				}
			});
			Toolbarbutton del = new Toolbarbutton();
			del.setImage("/css/default/images/button/del.gif");
			del.setParent(c4);
			del.addEventListener(Events.ON_CLICK, new EventListener() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					Messagebox.show("确定删除吗?", "确定", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
						public void onEvent(Event evt) throws InterruptedException {
							if (evt.getName().equals("onOK")) {
								arrList3.remove(str);
								applyList3.setModel(new ListModelList(arrList3));
							}
						}
					});
				}
			});
			arg0.appendChild(c1);
			arg0.appendChild(c2);
			arg0.appendChild(c3);
			arg0.appendChild(c4);
		}
	}

	public void download(String fpath, String fname) throws InterruptedException {
		File file = new File(UploadUtil.getRootPath() + fpath);
		if (file.exists()) {
			try {
				Filedownload.save(file, fname);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			Messagebox.show("无法下载，可能是因为文件没有被上传！ ", "错误", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/** 个人绩分渲染器 */
	public class personScoreRenderer implements ListitemRenderer {

		@Override
		public void render(Listitem item, Object data) throws Exception {

			if (data == null)
				return;
			final Jxkh_ReportMember member = (Jxkh_ReportMember) data;
			item.setValue(member);
			Listcell c1 = new Listcell(item.getIndex() + 1 + "");
			Listcell c2 = new Listcell(member.getName());
			Listcell c3 = new Listcell();
			if (member.getType() == 1) {
				c3 = new Listcell("院外");
			} else {
				c3 = new Listcell("院内");
			}
			Listcell c4 = new Listcell();
			if (member.getType() == 1) {
				c4 = new Listcell("院外");
			} else {
				c4 = new Listcell(member.getDept());
			}
			Listcell c5 = new Listcell();
			c5.setTooltiptext("请输入数字");
			Textbox t = new Textbox();
			t.setParent(c5);
			if (member.getPer() != null)
				t.setValue(member.getPer() + "");
			Listcell c6 = new Listcell();
			Toolbarbutton bar = new Toolbarbutton();
			if (member.getAssignDep() == null || member.getAssignDep().equals("")) {
				bar.setLabel("指定");
				bar.setStyle("color:blue");
			} else {
				bar.setLabel(member.getAssignDep());
			}
			c6.appendChild(bar);
			Listcell c7 = new Listcell();
			if (member.getScore() != null)
				c7.setLabel(member.getScore() + "");

			final List<Jxkh_ReportDept> temp = jxkhReportService.findReportDepByReportId(report);
			// 弹出指定部门页面
			bar.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event event) throws Exception {

					AssignDeptWindow w = (AssignDeptWindow) Executions.createComponents("/admin/personal/businessdata/meeting/assignDept.zul", null, null);
					try {
						w.setFlag("REPORT");
						w.setState(report.getState());
						w.setDept(temp);
						w.setMember(member);
						w.initWindow();
						w.doModal();

						// 指定部门完成后自动计算得分
						w.addEventListener(Events.ON_CHANGE, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								List<Jxkh_ReportMember> tempMemberList = report.getReportMember();
								personScore.setModel(new ListModelList(tempMemberList));
								for (int k = 0; k < reportDeptList.size(); k++) {
									Jxkh_ReportDept d = reportDeptList.get(k);
									float f = 0.0f;
									for (int i = 0; i < tempMemberList.size(); i++) {
										Jxkh_ReportMember m = tempMemberList.get(i);
										if (m.getAssignDep() == null || m.getAssignDep().equals("")) {
											if (m.getDept().equals(d.getName())) {
												f += m.getScore();
											}
										} else if (m.getAssignDep().equals(d.getName())) {
											f += m.getScore();
										}
									}
									d.setScore(f);
									jxkhReportService.update(d);
								}
								List<Jxkh_ReportDept> tempDeptList = report.getReprotDept();
								departmentScore.setModel(new ListModelList(tempDeptList));
							}
						});
					} catch (SuspendNotAllowedException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
			item.appendChild(c1);
			item.appendChild(c2);
			item.appendChild(c3);
			item.appendChild(c4);
			item.appendChild(c5);
			item.appendChild(c6);
			item.appendChild(c7);
		}
	}

	/** 部门绩分列表渲染器 */
	public class departmentScoreRenderer implements ListitemRenderer {

		@Override
		public void render(Listitem arg0, Object arg1) throws Exception {
			if (arg0 == null)
				return;
			final Jxkh_ReportDept dept = (Jxkh_ReportDept) arg1;
			arg0.setValue(dept);
			Listcell c1 = new Listcell(arg0.getIndex() + 1 + "");
			Listcell c2 = new Listcell(dept.getName());
			Listcell c3 = new Listcell();
			if (dept.getScore() != null)
				c3.setLabel(dept.getScore() + "");
			arg0.appendChild(c1);
			arg0.appendChild(c2);
			arg0.appendChild(c3);
		}
	}

	// 绩分信息的保存按钮
	public void onClick$submitScore() {
		float a = 0.0f;
		for (int i = 0; i < reportMemberList.size(); i++) {
			Listitem item = personScore.getItemAtIndex(i);
			Listcell lc = (Listcell) item.getChildren().get(4);
			Textbox temp = (Textbox) lc.getChildren().get(0);
			if (temp.getValue() != null && temp.getValue() != "")
				try {
					a += Float.parseFloat(temp.getValue());
				} catch (Exception e) {
					e.printStackTrace();
					try {
						Messagebox.show("只能输入数字！", "提示", Messagebox.OK, Messagebox.EXCLAMATION);
						return;
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}

		}
		if (a != 10.0) {
			try {
				Messagebox.show("请检查人员所占比例的总和是否为10.0！", "提示", Messagebox.OK, Messagebox.EXCLAMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		for (int i = 0; i < reportMemberList.size(); i++) {
			Listitem item = personScore.getItemAtIndex(i);
			Listcell lc = (Listcell) item.getChildren().get(4);
			Textbox temp = (Textbox) lc.getChildren().get(0);
			float s = 0.0f;// 比例
			float f = 0.0f;// 分值
			if (temp.getValue() != null && temp.getValue() != "")
				s = Float.parseFloat(temp.getValue());
			if (report.getScore() != null)
				f = s * report.getScore() / 10;
			float score = (float) (Math.round(f * 1000)) / 1000;
			Jxkh_ReportMember member = (Jxkh_ReportMember) item.getValue();
			member.setPer(s);
			member.setScore(score);
			jxkhReportService.update(member);
		}
		List<Jxkh_ReportMember> tempMemberList = report.getReportMember();
		personScore.setModel(new ListModelList(tempMemberList));
		for (int k = 0; k < reportDeptList.size(); k++) {
			Jxkh_ReportDept d = reportDeptList.get(k);
			float f = 0.0f;
			for (int i = 0; i < tempMemberList.size(); i++) {
				Jxkh_ReportMember m = tempMemberList.get(i);
				if (m.getAssignDep() == null || m.getAssignDep().equals("")) {
					if (m.getDept().equals(d.getName())) {
						f += m.getScore();
					}
				} else if (m.getAssignDep().equals(d.getName())) {
					f += m.getScore();
				}
			}
			d.setScore(f);
			jxkhReportService.update(d);
		}
		try {
			Messagebox.show("绩分信息保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		List<Jxkh_ReportDept> tempDeptList = report.getReprotDept();
		departmentScore.setModel(new ListModelList(tempDeptList));
	}

	// 绩分信息的关闭按钮
	public void onClick$closeScore() {
		this.onClose();
	}
}
