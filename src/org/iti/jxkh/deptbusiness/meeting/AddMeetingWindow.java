package org.iti.jxkh.deptbusiness.meeting;

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
import org.iti.jxkh.entity.JXKH_MeetingDept;
import org.iti.jxkh.entity.JXKH_MeetingFile;
import org.iti.jxkh.entity.JXKH_MeetingMember;
import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.service.BusinessIndicatorService;
import org.iti.jxkh.service.JXKHMeetingService;
import org.iti.jxkh.service.JxkhAwardService;
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

public class AddMeetingWindow extends Window implements AfterCompose {

	/**
	 * @author CuiXiaoxin
	 */
	private static final long serialVersionUID = -3755074204648272061L;
	private Tab baseTab, fileTab, scoreTab;
	private Listbox personScore, departmentScore;
	private Toolbarbutton tempSave, submit, close, submitScore, ups1, ups2, ups3;
	private Textbox meetName, meetingMember, department, coUnit, // sponsor,
			address, subject, scope;
	private Datebox time;
	private Label meetingWriter;
	private Listbox meetingRank, holdCategray;// 会议级别，举办类型
	private Radio yes, no, first, unFirst;
	private Row coUnitRow, zcRow, zxRow, cxRow;
	private Textbox zdep1, zdep2, cdep1, cdep2, xdep1, xdep2;
	private Button print;
	private YearListbox jiFenTime;
	private WkTUser user;
	private List<WkTUser> memberList = new ArrayList<WkTUser>();
	private List<WkTDept> deptList = new ArrayList<WkTDept>();
	private JXKHMeetingService jxkhMeetingService;
	private JxkhAwardService jxkhAwardService;
	private JXKH_MEETING meeting;
	private List<JXKH_MeetingMember> meetingMemberList = new ArrayList<JXKH_MeetingMember>();
	private List<JXKH_MeetingDept> meetDeptList = new ArrayList<JXKH_MeetingDept>();
	private JXKH_MeetingFile file;
	public static final Integer mtRank = 71, mtHold = 72;
	private BusinessIndicatorService businessIndicatorService;

	private Listbox applyList1, applyList2, applyList3;
	private List<JXKH_MeetingFile> fileList1;
	private List<JXKH_MeetingFile> fileList2;
	private List<JXKH_MeetingFile> fileList3;

	private String audit;// 如果有值，就表示是部门审核调用这个后台，其保存按钮就隐藏

	public void setAudit(String audit) {
		this.audit = audit;
	}

	public JXKH_MEETING getMeeting() {
		return meeting;
	}

	public void setMeeting(JXKH_MEETING meeting) {
		this.meeting = meeting;
	}

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		submit.setVisible(true);
		tempSave.setVisible(true);
		this.addForward(Events.ON_OK, submit, Events.ON_CLICK);
		jiFenTime.initYearListbox("");
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");// 获取当前登录用户
		System.out.println(user);
		meetingRank.setItemRenderer(new meetingRankRenderer());
		holdCategray.setItemRenderer(new holdTypeRenderer());
		personScore.setItemRenderer(new personScoreRenderer());
		departmentScore.setItemRenderer(new departmentScoreRenderer());

		// 点击radio"yes"触发事件
		yes.addEventListener(Events.ON_CHECK, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				coUnitRow.setVisible(true);
			}
		});
		// 点击radio"no"触发事件
		no.addEventListener(Events.ON_CHECK, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				coUnitRow.setVisible(false);
			}
		});
		type();

		// 点击绩分页面时会议信息页面和文档信息页面的保存和退出按钮隐藏
		scoreTab.addEventListener(Events.ON_CLICK, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				submit.setVisible(false);
				tempSave.setVisible(false);
				close.setVisible(false);
				if (meeting != null) {
					// 判断当前登录人员是否是信息填写人的部门负责人(20120314)是否是主审部门
					List<WkTUser> wktUser = jxkhMeetingService.findWkTUserByManId(meeting.getWriterId());
					WkTUser u = wktUser.get(0);
					JXKH_MeetingDept d = jxkhMeetingService.findMeetingDept(meeting, user.getDept().getKdNumber());
					if (user.getDept().getKdNumber().equals(u.getDept().getKdNumber()) || d.getRank() == 1) {
						if (meeting.getMtState() == null || meeting.getMtState() == 0 || meeting.getMtState().shortValue() == JXKH_MEETING.WRITING || meeting.getMtState() == 3 || meeting.getMtState() == 5) {
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
				submit.setVisible(true);
				tempSave.setVisible(true);
				close.setVisible(true);
				if (meeting != null) {
					if (meeting.getMtState() == null || meeting.getMtState() == 0 || meeting.getMtState().shortValue() == JXKH_MEETING.WRITING || meeting.getMtState() == 3 || meeting.getMtState() == 5) {
						submit.setVisible(true);
						tempSave.setVisible(true);
					} else {
						submit.setVisible(false);
						tempSave.setVisible(false);
					}
					// 判断当前登录人员是否是信息填写人的部门负责人(20120314)
					List<WkTUser> wktUser = jxkhMeetingService.findWkTUserByManId(meeting.getWriterId());
					WkTUser u = wktUser.get(0);
					if (!user.getDept().getKdNumber().equals(u.getDept().getKdNumber())) {
						submit.setVisible(false);
						tempSave.setVisible(false);
					}
				}
				if (audit == "AUDIT") {
					submit.setVisible(false);
					tempSave.setVisible(false);
				}
			}
		});
		fileTab.addEventListener(Events.ON_CLICK, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				submit.setVisible(true);
				tempSave.setVisible(true);
				close.setVisible(true);
				if (meeting != null) {
					if (meeting.getMtState() == null || meeting.getMtState() == 0 || meeting.getMtState().shortValue() == JXKH_MEETING.WRITING || meeting.getMtState() == 3 || meeting.getMtState() == 5) {
						submit.setVisible(true);
						tempSave.setVisible(true);
					} else {
						submit.setVisible(false);
						tempSave.setVisible(false);
					}
					// 判断当前登录人员是否是信息填写人的部门负责人(20120314)
					List<WkTUser> wktUser = jxkhMeetingService.findWkTUserByManId(meeting.getWriterId());
					WkTUser u = wktUser.get(0);
					if (!user.getDept().getKdNumber().equals(u.getDept().getKdNumber())) {
						submit.setVisible(false);
						tempSave.setVisible(false);
					}
				}
				if (audit == "AUDIT") {
					submit.setVisible(false);
					tempSave.setVisible(false);
				}
			}
		});
	}

	public void initWindow() {
		if (meeting == null) {
			meetingWriter.setValue(user.getKuName());
			scoreTab.setDisabled(true);
			if (audit == "AUDIT") {
				submit.setVisible(false);
				tempSave.setVisible(false);
			}
		} else {
			scoreTab.setDisabled(false);
			if (meeting.getMtState() == null || meeting.getMtState() == 0 || meeting.getMtState().shortValue() == JXKH_MEETING.WRITING || meeting.getMtState() == 3 || meeting.getMtState() == 5) {
				ups1.setDisabled(false);
				ups2.setDisabled(false);
				ups3.setDisabled(false);
			} else {
				submit.setVisible(false);
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
			// 判断当前登录人员是否是信息填写人的部门负责人
			List<WkTUser> wktUser = jxkhMeetingService.findWkTUserByManId(meeting.getWriterId());
			WkTUser u = wktUser.get(0);
			if (!user.getDept().getKdNumber().equals(u.getDept().getKdNumber())) {
				submit.setVisible(false);
				tempSave.setVisible(false);
				ups1.setDisabled(true);
				ups2.setDisabled(true);
				ups3.setDisabled(true);
			}
			if (audit == "AUDIT") {
				submit.setVisible(false);
				tempSave.setVisible(false);
				ups1.setDisabled(true);
				ups2.setDisabled(true);
				ups3.setDisabled(true);
			}
			jiFenTime.initYearListbox(meeting.getjxYear());
			print.setHref("/print.action?n=meeting&id=" + meeting.getMtId());
			meetName.setValue(meeting.getMtName());
			if (meeting.getIfUnion() == 1) {
				yes.setSelected(true);
				coUnitRow.setVisible(true);
				coUnit.setValue(meeting.getMtCoDep());
			} else {
				no.setSelected(true);
				coUnitRow.setVisible(false);
			}
			if (meeting.getIfSign() == 1) {
				first.setSelected(true);
			} else {
				unFirst.setSelected(true);
			}
			meetingWriter.setValue(meeting.getMtWriter());
			/* sponsor.setValue(meeting.getMtPerson());// 负责人 */
			if (meeting.getMtTime() != null) {
				time.setValue(ConvertUtil.convertDate(meeting.getMtTime()));
			}
			address.setValue(meeting.getMtAddress());
			subject.setValue(meeting.getMtTheme());
			scope.setValue(meeting.getMtScope());
			// 归档后显示档案号
			// if (meeting.getMtState() == null || meeting.getMtState() == 0
			// || meeting.get() == 1 || meeting.getMtState() == 2
			// || meeting.getMtState() == 3 || meeting.getMtState() == 4) {
			// record.setVisible(false);
			// cordId.setVisible(false);
			// } else if (meeting.getMtState() == 5) {
			// record.setVisible(true);
			// cordId.setVisible(true);
			// record.setValue(meeting.getRecordCode());
			// }

			// 初始化全部作者
			String memberName = "";
			meetingMemberList = jxkhMeetingService.findMeetingMemberByMeetingId(meeting);
			for (int i = 0; i < meetingMemberList.size(); i++) {
				JXKH_MeetingMember mem = (JXKH_MeetingMember) meetingMemberList.get(i);
				memberName += mem.getName() + "、";
				// WkTUser user = jxkhAwardService.findWktUserByMemberUserId(mem
				// .getPersonId());
				// memberList.add(user);
				if (mem.getType().equals("0")) {
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
			if (memberName != "" && memberName != null)
				memberName = memberName.substring(0, memberName.length() - 1);
			meetingMember.setValue(memberName);

			// 显示部门的数据
			String d = "";
			meetDeptList = jxkhMeetingService.findMeetingDeptByMeetingId(meeting);
			for (int i = 0; i < meetDeptList.size(); i++) {
				JXKH_MeetingDept dept = (JXKH_MeetingDept) meetDeptList.get(i);
				d += dept.getName() + "、";
				WkTDept depts = jxkhAwardService.findWkTDeptByDept(dept.getDepId());
				deptList.add(depts);
			}
			if (d != "" && d != null)
				d = d.substring(0, d.length() - 1);
			department.setValue(d);

			holdTypeRenderer h = new holdTypeRenderer();
			System.out.println("initwingdow中的值" + h.getType());
			if (h.getType().equals(null)) {
				zcRow.setVisible(false);
				zxRow.setVisible(false);
				cxRow.setVisible(false);
			}
			if (h.getType().equals("主办")) {
				zcRow.setVisible(false);
				zxRow.setVisible(false);
				cxRow.setVisible(true);

				cdep2.setValue(meeting.getMtCDep());
				xdep2.setValue(meeting.getMtXDep());
			}
			if (h.getType().equals("承办")) {
				zcRow.setVisible(false);
				zxRow.setVisible(true);
				cxRow.setVisible(false);

				zdep2.setValue(meeting.getMtZDep());
				xdep1.setValue(meeting.getMtXDep());
			}
			if (h.getType().equals("协办")) {
				System.out.println("*****");
				zcRow.setVisible(true);
				zxRow.setVisible(false);
				cxRow.setVisible(false);

				cdep1.setValue(meeting.getMtCDep());
				zdep1.setValue(meeting.getMtZDep());
			}

			// 新的附件初始化（20120305）
			fileList1 = jxkhMeetingService.findFilesByIdAndType(meeting, "会议照片");
			arrList1.clear();
			for (int x = 0; x < fileList1.size(); x++) {
				JXKH_MeetingFile f = fileList1.get(x);
				String[] arr = new String[4];
				arr[0] = f.getPath();
				arr[1] = f.getName();
				arr[2] = f.getDate();
				arr[3] = f.getFileType();
				arrList1.add(arr);
			}
			applyList1.setItemRenderer(new FilesRenderer1());
			applyList1.setModel(new ListModelList(arrList1));

			fileList2 = jxkhMeetingService.findFilesByIdAndType(meeting, "会议通知");
			arrList2.clear();
			for (int x = 0; x < fileList2.size(); x++) {
				JXKH_MeetingFile f = fileList2.get(x);
				String[] arr = new String[4];
				arr[0] = f.getPath();
				arr[1] = f.getName();
				arr[2] = f.getDate();
				arr[3] = f.getFileType();
				arrList2.add(arr);
			}
			applyList2.setItemRenderer(new FilesRenderer2());
			applyList2.setModel(new ListModelList(arrList2));

			fileList3 = jxkhMeetingService.findFilesByIdAndType(meeting, "会议总结");
			arrList3.clear();
			for (int x = 0; x < fileList3.size(); x++) {
				JXKH_MeetingFile f = fileList3.get(x);
				String[] arr = new String[4];
				arr[0] = f.getPath();
				arr[1] = f.getName();
				arr[2] = f.getDate();
				arr[3] = f.getFileType();
				arrList3.add(arr);
			}
			applyList3.setItemRenderer(new FilesRenderer3());
			applyList3.setModel(new ListModelList(arrList3));
		}
		initListbox();
	}

	// 导出会议级别，举办类型listbox列表
	private void initListbox() {
		List<Jxkh_BusinessIndicator> rankList = new ArrayList<Jxkh_BusinessIndicator>();
		rankList.add(new Jxkh_BusinessIndicator());
		System.out.println("指标中取出的值是：" + jxkhMeetingService.findRank(mtRank).size());
		if (jxkhMeetingService.findRank(mtRank) != null) {
			rankList.addAll(jxkhMeetingService.findRank(mtRank));
		}
		meetingRank.setModel(new ListModelList(rankList));
		meetingRank.setSelectedIndex(0);

		List<Jxkh_BusinessIndicator> holdList = new ArrayList<Jxkh_BusinessIndicator>();
		holdList.add(new Jxkh_BusinessIndicator());
		if (jxkhMeetingService.findRank(mtHold) != null) {
			holdList.addAll(jxkhMeetingService.findRank(mtHold));
		}
		holdCategray.setModel(new ListModelList(holdList));
		holdCategray.setSelectedIndex(0);

		personScore.setModel(new ListModelList(meetingMemberList));
		departmentScore.setModel(new ListModelList(meetDeptList));
	}

	/** 会议级别列表渲染器 */
	public class meetingRankRenderer implements ListitemRenderer {
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
			if (meeting != null && type.equals(meeting.getMtDegree())) {
				item.setSelected(true);
			}
		}
	}

	/** 举办类型渲染器 */
	public class holdTypeRenderer implements ListitemRenderer {
		private String TypeSelect = null;

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
			if (meeting != null && type.equals(meeting.getMtType())) {
				item.setSelected(true);
			}
		}

		public String getType() {
			TypeSelect = ((Jxkh_BusinessIndicator) meeting.getMtType()).getKbName();
			System.out.println("渲染器get中得值" + TypeSelect);
			return TypeSelect;
		}
	}

	// 根据举办类型（主办、承办、协办）来显示另外两个（主办单位、承办单位、协办单位）
	public void type() {
		holdCategray.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event event) throws Exception {
				System.out.println(((Jxkh_BusinessIndicator) holdCategray.getSelectedItem().getValue()).getKbName());
				if (((Jxkh_BusinessIndicator) holdCategray.getSelectedItem().getValue()).getKbName() == null || ((Jxkh_BusinessIndicator) holdCategray.getSelectedItem().getValue()).getKbName().equals(null)) {
					zcRow.setVisible(false);
					zxRow.setVisible(false);
					cxRow.setVisible(false);
				}
				if (((Jxkh_BusinessIndicator) holdCategray.getSelectedItem().getValue()).getKbName().equals("主办")) {
					zcRow.setVisible(false);
					zxRow.setVisible(false);
					cxRow.setVisible(true);
				}
				if (((Jxkh_BusinessIndicator) holdCategray.getSelectedItem().getValue()).getKbName().equals("承办")) {
					zcRow.setVisible(false);
					zxRow.setVisible(true);
					cxRow.setVisible(false);
				}
				if (((Jxkh_BusinessIndicator) holdCategray.getSelectedItem().getValue()).getKbName().equals("协办")) {
					zcRow.setVisible(true);
					zxRow.setVisible(false);
					cxRow.setVisible(false);
				}
			}
		});
	}

	// 单击选择成员按钮，触发弹出选择成员页面事件
	public void onClick$chooseMember() throws SuspendNotAllowedException, InterruptedException {
		final ChooseMemberWin win = (ChooseMemberWin) Executions.createComponents("/admin/personal/businessdata/award/choosemember.zul", null, null);
		win.setMemberList(memberList);
		win.initWindow();
		win.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				memberList = win.getMemberList();
				String members = "";
				for (WkTUser user : memberList) {
					// for (int i = 0; i < memberList.size(); i++) {
					members += user.getKuName() + ",";
				}
				if (members.endsWith(",")) {
					members = members.substring(0, members.length() - 1);
				}
				meetingMember.setValue(members);
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
		department.setValue(null);
		department.setValue(depts);
	}

	// 单击选择部门按钮，触发弹出选择部門页面事件
	public void onClick$chooseDept() throws SuspendNotAllowedException, InterruptedException {
		final ChooseDeptWin win = (ChooseDeptWin) Executions.createComponents("/admin/personal/businessdata/award/choosedept.zul", null, null);
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
				department.setValue(depts);
				win.detach();
			}
		});
		win.doModal();
	}

	private void checkNull() {
		if (department.getValue() == null || department.getValue() == "") {
			try {
				Messagebox.show("院内部门不能为空！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			department.focus();
			return;
		}
		if (meetingRank.getSelectedIndex() == 0) {
			try {
				Messagebox.show("会议级别不能为空！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			meetingRank.focus();
			return;
		}
		if (holdCategray.getSelectedIndex() == 0) {
			try {
				Messagebox.show("举办类型不能为空！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			holdCategray.focus();
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
		if(meeting != null && meeting.getMtId() != null) {
			if(arrList1.size() <= 0) {
				try {
					Messagebox.show("会议照片必须上传！");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return;
			}
			if(arrList2.size() <= 0) {
				try {
					Messagebox.show("会议通知必须上传！");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return;
			}
		}
	}
	
	public void onClick$tempSave() {
		checkNull();
		if (meeting == null) {
			meeting = new JXKH_MEETING();
			meeting.setMtWriter(user.getKuName());
			meeting.setWriterId(user.getKuLid());
			meeting.setSubmitTime(ConvertUtil.convertDateAndTimeString(new Date()));
			setEntity();
			meeting.setMtState(JXKH_MEETING.WRITING);
			jxkhAwardService.saveOrUpdate(meeting);
		} else {
			if (meeting.getMtState() == null || meeting.getMtState() == 0 || meeting.getMtState().shortValue() == JXKH_MEETING.WRITING
					|| meeting.getMtState() == 3 || meeting.getMtState() == 5) {
				setEntity();
				meeting.setMtState(JXKH_MEETING.WRITING);
				jxkhAwardService.saveOrUpdate(meeting);
			} else {
				try {
					Messagebox.show("学术会议保存失败，部门已经审核通过或者业务办已经审核通过，您只能查看，无权再编辑 ！", "提示", Messagebox.OK, Messagebox.ERROR);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return;
			}
		}
		try {
			Messagebox.show("学术会议保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			this.detach();
			Events.postEvent(Events.ON_CHANGE, this, null);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				Messagebox.show("学术会议保存失败，请重试！", "提示", Messagebox.OK, Messagebox.ERROR);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}

	// 单击保存按钮触发保存事件
	public void onClick$submit() {
		checkNull();
		if (meeting == null) {
			meeting = new JXKH_MEETING();
			meeting.setMtWriter(user.getKuName());
			meeting.setWriterId(user.getKuLid());
			meeting.setSubmitTime(ConvertUtil.convertDateAndTimeString(new Date()));
			setEntity();
			meeting.setMtState(JXKH_MEETING.NOT_AUDIT);
			jxkhAwardService.saveOrUpdate(meeting);
		} else {
			if (meeting.getMtState() == null || meeting.getMtState() == 0 || meeting.getMtState().shortValue() == JXKH_MEETING.WRITING
					|| meeting.getMtState() == 3 || meeting.getMtState() == 5) {
				setEntity();
				meeting.setMtState(JXKH_MEETING.NOT_AUDIT);
				jxkhAwardService.saveOrUpdate(meeting);
			} else {
				try {
					Messagebox.show("学术会议保存失败，部门已经审核通过或者业务办已经审核通过，您只能查看，无权再编辑 ！", "提示", Messagebox.OK, Messagebox.ERROR);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return;
			}
		}
		try {
			Messagebox.show("学术会议保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			this.detach();
			Events.postEvent(Events.ON_CHANGE, this, null);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				Messagebox.show("学术会议保存失败，请重试！", "提示", Messagebox.OK, Messagebox.ERROR);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}

	private Float computeScore(Jxkh_BusinessIndicator lwRank, Jxkh_BusinessIndicator holdType) {
		float result = 0f;
		String type = "";
		float res = 0f;
		Properties property = PropertiesLoader.loader("title", "title.properties");
		float nationmeeting = 0, provincemeeting = 0, majormeeting = 0;
		if (lwRank.getKbName().equals(property.getProperty("nationmeeting"))) {
			if (holdType.getKbName().equals("主办")) {
				nationmeeting += 1.5f;
				result = nationmeeting;
				type = property.getProperty("nationmeeting");
			} else if (holdType.getKbName().equals("承办")) {
				nationmeeting += 1f;
				result = nationmeeting;
				type = property.getProperty("nationmeeting");
			} else if (holdType.getKbName().equals("协办")) {
				provincemeeting += 1f;
				result = provincemeeting;
				type = property.getProperty("provincemeeting");
			}
		} else if (lwRank.getKbName().equals(property.getProperty("provincemeeting"))) {
			if (holdType.getKbName().equals("主办")) {
				provincemeeting += 1.5f;
				result = provincemeeting;
				type = property.getProperty("provincemeeting");
			} else if (holdType.getKbName().equals("承办")) {
				provincemeeting += 1f;
				result = provincemeeting;
				type = property.getProperty("provincemeeting");
			} else if (holdType.getKbName().equals("协办")) {
				majormeeting += 1f;
				result = majormeeting;
				type = property.getProperty("majormeeting");
			}
		} else if (lwRank.getKbName().equals(property.getProperty("interprovince"))) {
			if (holdType.getKbName().equals("主办")) {
				provincemeeting += 1.5f;
				result = provincemeeting;
				type = property.getProperty("interprovince");
			} else if (holdType.getKbName().equals("承办")) {
				provincemeeting += 1f;
				result = provincemeeting;
				type = property.getProperty("interprovince");
			} else if (holdType.getKbName().equals("协办")) {
				majormeeting += 1f;
				result = majormeeting;
				type = property.getProperty("majormeeting");
			}
		} else if (lwRank.getKbName().equals(property.getProperty("majormeeting"))) {
			if (holdType.getKbName().equals("主办")) {
				majormeeting += 1.5f;
				result = majormeeting;
				type = property.getProperty("majormeeting");
			} else if (holdType.getKbName().equals("承办")) {
				majormeeting += 1f;
				result = majormeeting;
				type = property.getProperty("majormeeting");
			} else if (holdType.getKbName().equals("协办")) {
				majormeeting += 0.5f;
				result = majormeeting;
				type = property.getProperty("majormeeting");
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
		Jxkh_BusinessIndicator kdRank = new Jxkh_BusinessIndicator();
		Jxkh_BusinessIndicator kdHold = new Jxkh_BusinessIndicator();
		meeting.setMtName(meetName.getValue());
		meeting.setTempState("0000000");// 为临时状态赋初值
		meeting.setDep1Reason("");
		meeting.setDep2Reason("");
		meeting.setDep3Reason("");
		meeting.setDep4Reason("");
		meeting.setDep5Reason("");
		meeting.setDep6Reason("");
		meeting.setDep7Reason("");
		meeting.setYwReason("");
		meeting.setjxYear(jiFenTime.getSelectedItem().getValue() + "");
		if (yes.isChecked()) {
			meeting.setIfUnion(JXKH_MEETING.YES);
			meeting.setMtCoDep(coUnit.getValue());
		} else {
			meeting.setIfUnion(JXKH_MEETING.NO);
			meeting.setMtCoDep(null);
		}
		if (unFirst.isChecked()) {
			meeting.setIfSign(JXKH_MEETING.NO);
		} else {
			meeting.setIfSign(JXKH_MEETING.YES);
		}
		if (meetingRank.getSelectedItem() != null) {
			meeting.setMtDegree((Jxkh_BusinessIndicator) meetingRank.getSelectedItem().getValue());
			kdRank = (Jxkh_BusinessIndicator) meetingRank.getSelectedItem().getValue();
		}
		if (holdCategray.getSelectedItem() != null) {
			meeting.setMtType((Jxkh_BusinessIndicator) holdCategray.getSelectedItem().getValue());
			kdHold = (Jxkh_BusinessIndicator) holdCategray.getSelectedItem().getValue();
		}
		Float score = computeScore(kdRank, kdHold);
		meeting.setScore(score);
		
		if (time.getValue() != null) {
			meeting.setMtTime(ConvertUtil.convertDateString(time.getValue()));
		}
		meeting.setMtAddress(address.getValue());
		meeting.setMtTheme(subject.getValue());
		meeting.setMtScope(scope.getValue());
		if (((Jxkh_BusinessIndicator) holdCategray.getSelectedItem().getValue()).getKbName() == null || ((Jxkh_BusinessIndicator) holdCategray.getSelectedItem().getValue()).getKbName().equals(null)) {

		} else if (((Jxkh_BusinessIndicator) holdCategray.getSelectedItem().getValue()).getKbName().equals("主办")) {
			meeting.setMtZDep("河北省科技情报研究院");
			meeting.setMtCDep(cdep2.getValue());
			meeting.setMtXDep(xdep2.getValue());
		} else if (((Jxkh_BusinessIndicator) holdCategray.getSelectedItem().getValue()).getKbName().equals("承办")) {
			meeting.setMtZDep(zdep2.getValue());
			meeting.setMtCDep("河北省科技情报研究院");
			meeting.setMtXDep(xdep1.getValue());
		} else if (((Jxkh_BusinessIndicator) holdCategray.getSelectedItem().getValue()).getKbName().equals("协办")) {
			meeting.setMtXDep("河北省科技情报研究院");
			meeting.setMtCDep(cdep1.getValue());
			meeting.setMtZDep(zdep1.getValue());
		}

		// 把成员放入JXKH_MeetingMember表中
		List<JXKH_MeetingMember> mlist = new ArrayList<JXKH_MeetingMember>();
		if (meeting.getMtId() != null) {
			mlist = jxkhMeetingService.findMeetingMemberByMeetingId(meeting);
			if (mlist != null && mlist.size() != 0) {
				jxkhMeetingService.deleteAll(mlist);
			}
		}
		mlist.clear();
		int j = 1;
		for (WkTUser user : memberList) {
			JXKH_MeetingMember member = new JXKH_MeetingMember();
			member.setMeetingName(meeting);
			member.setDept(user.getDept().getKdName());
			member.setName(user.getKuName());
			switch (user.getType()) {
			case WkTUser.TYPE_IN:
				member.setType(WkTUser.TYPE_IN + "");
				break;
			case WkTUser.TYPE_OUT:
				member.setType(WkTUser.TYPE_OUT + "");
				break;
			}
			member.setPersonId(user.getKuLid());
			member.setRank(j + "");

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
		meeting.setMeetingAuthor(mlist);

		// 选择部门后传过来的值
		List<JXKH_MeetingDept> dlist = new ArrayList<JXKH_MeetingDept>();
		if (meeting.getMtId() != null) {
			dlist = jxkhMeetingService.findMeetingDeptByMeetingId(meeting);
			if (dlist != null && dlist.size() != 0) {
				jxkhMeetingService.deleteAll(dlist);
			}
		}
		dlist.clear();
		int i = 1;
		for (WkTDept wktDept : deptList) {
			JXKH_MeetingDept dept = new JXKH_MeetingDept();
			dept.setMeeting(meeting);
			dept.setName(wktDept.getKdName());
			dept.setDepId(wktDept.getKdNumber());
			dept.setRank(i);
			i++;

			// 部门默认的分值0503
			float fen = 0.0f;
			for (int g = 0; g < mlist.size(); g++) {
				JXKH_MeetingMember m = mlist.get(g);
				if (m.getAssignDep().equals(dept.getName())) {
					fen += m.getScore();
				}
			}
			float scor = (float) (Math.round(fen * 1000)) / 1000;// 保留三位小数
			dept.setScore(scor);
			dlist.add(dept);
		}
		meeting.setMtDep(dlist);

		// 新的附件保存(20120305)
		List<String[]> arrList = new ArrayList<String[]>();
		Set<JXKH_MeetingFile> fset = new HashSet<JXKH_MeetingFile>();
		Set<JXKH_MeetingFile> oldFile = meeting.getFiles();
		if (oldFile != null) {
			for (Object o : oldFile.toArray()) {
				JXKH_MeetingFile f = (JXKH_MeetingFile) o;
				if (f != null)
					jxkhMeetingService.delete(f);
			}
			meeting.getFiles().clear();
		}
		if (arrList1.size() != 0 || arrList1 != null)
			arrList.addAll(arrList1);
		if (arrList2.size() != 0 || arrList2 != null)
			arrList.addAll(arrList2);
		if (arrList3.size() != 0 || arrList3 != null)
			arrList.addAll(arrList3);
		for (int r = 0; r < arrList.size(); r++) {
			String[] s = arrList.get(r);
			file = new JXKH_MeetingFile();
			file.setMeeting(meeting);
			file.setPath(s[0]);
			file.setName(s[1]);
			file.setDate(s[2]);
			file.setFileType(s[3]);
			fset.add(file);
		}
		meeting.setFiles(fset);
	}

	/**
	 * <li>功能描述：关闭当前窗口。
	 */
	public void onClick$close() {
		this.onClose();
	}

	// 20120302
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
				arr[3] = "会议照片";
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
	 * <li>功能描述：文档信息的listbox(20120302)
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

	// 20120304
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
				arr[3] = "会议通知";
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
	 * <li>功能描述：文档信息的listbox(20120304)
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

	// 20120304
	private List<String[]> arrList3 = new ArrayList<String[]>();

	public void onClick$ups3() {
		final UpfileWindow w = (UpfileWindow) Executions.createComponents("/admin/personal/businessdata/meeting/upfile.zul", null, null);
		w.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				String filePath = (String) Executions.getCurrent().getAttribute("path");
				System.out.println("输出文件路径是path=" + filePath);
				String fileName = (String) Executions.getCurrent().getAttribute("title");
				System.out.println("输出的文件标题是title=" + fileName);
				String upTime = (String) Executions.getCurrent().getAttribute("upTime");
				System.out.println("输出文件的上传时间time=" + upTime);
				applyList3.setItemRenderer(new FilesRenderer3());
				String[] arr = new String[4];
				arr[0] = filePath;
				arr[1] = fileName;
				arr[2] = upTime;
				arr[3] = "会议总结";
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
	 * <li>功能描述：文档信息的listbox(20120304)
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
		System.out.println("20120304path=" + UploadUtil.getRootPath() + fpath);
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
			final JXKH_MeetingMember member = (JXKH_MeetingMember) data;
			item.setValue(member);
			Listcell c1 = new Listcell(item.getIndex() + 1 + "");
			Listcell c2 = new Listcell(member.getName());
			Listcell c3 = new Listcell();
			if (member.getType().equals("1")) {
				c3 = new Listcell("院外");
			} else {
				c3 = new Listcell("院内");
			}
			Listcell c4 = new Listcell();
			if (member.getType().equals("1")) {
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

			final List<JXKH_MeetingDept> temp = jxkhMeetingService.findMeetingDepByMeetingId(meeting);
			// 弹出指定部门页面
			bar.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event event) throws Exception {

					AssignDeptWindow w = (AssignDeptWindow) Executions.createComponents("/admin/personal/businessdata/meeting/assignDept.zul", null, null);
					try {
						w.setFlag("M");
						w.setState(meeting.getMtState());
						w.setDept(temp);
						w.setMember(member);
						w.initWindow();
						w.doModal();
						// 指定部门完成后自动计算得分
						w.addEventListener(Events.ON_CHANGE, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								List<JXKH_MeetingMember> tempMemberList = jxkhMeetingService.findMeetingMemberByMeetingId(meeting);
								personScore.setModel(new ListModelList(tempMemberList));
								for (int k = 0; k < meetDeptList.size(); k++) {
									JXKH_MeetingDept d = meetDeptList.get(k);
									float f = 0.0f;
									for (int i = 0; i < tempMemberList.size(); i++) {
										JXKH_MeetingMember m = tempMemberList.get(i);
										if (m.getAssignDep() == null || m.getAssignDep().equals("")) {
											if (m.getDept().equals(d.getName())) {
												f += m.getScore();
											}
										} else if (m.getAssignDep().equals(d.getName())) {
											f += m.getScore();
										}
									}
									d.setScore(f);
									jxkhMeetingService.update(d);
								}
								List<JXKH_MeetingDept> tempDeptList = jxkhMeetingService.findMeetingDeptByMeetingId(meeting);
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
			final JXKH_MeetingDept dept = (JXKH_MeetingDept) arg1;
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
		for (int i = 0; i < meetingMemberList.size(); i++) {
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
		for (int i = 0; i < meetingMemberList.size(); i++) {
			Listitem item = personScore.getItemAtIndex(i);
			Listcell lc = (Listcell) item.getChildren().get(4);
			Textbox temp = (Textbox) lc.getChildren().get(0);
			float s = 0.0f;// 比例
			float f = 0.0f;// 分值
			if (temp.getValue() != null && temp.getValue() != "")
				s = Float.parseFloat(temp.getValue());
			if (meeting.getScore() != null)
				f = s * meeting.getScore() / 10;
			float score = (float) (Math.round(f * 1000)) / 1000;
			JXKH_MeetingMember member = (JXKH_MeetingMember) item.getValue();
			member.setPer(s);
			member.setScore(score);
			jxkhMeetingService.update(member);
		}
		List<JXKH_MeetingMember> tempMemberList = jxkhMeetingService.findMeetingMemberByMeetingId(meeting);
		personScore.setModel(new ListModelList(tempMemberList));
		for (int k = 0; k < meetDeptList.size(); k++) {
			JXKH_MeetingDept d = meetDeptList.get(k);
			float f = 0.0f;
			for (int i = 0; i < tempMemberList.size(); i++) {
				JXKH_MeetingMember m = tempMemberList.get(i);
				if (m.getAssignDep() == null || m.getAssignDep().equals("")) {
					if (m.getDept().equals(d.getName())) {
						f += m.getScore();
					}
				} else if (m.getAssignDep().equals(d.getName())) {
					f += m.getScore();
				}
			}
			d.setScore(f);
			jxkhMeetingService.update(d);
		}
		try {
			Messagebox.show("绩分信息保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<JXKH_MeetingDept> tempDeptList = jxkhMeetingService.findMeetingDeptByMeetingId(meeting);
		departmentScore.setModel(new ListModelList(tempDeptList));
	}

	// 绩分信息的关闭按钮
	public void onClick$closeScore() {
		this.onClose();
	}
}
