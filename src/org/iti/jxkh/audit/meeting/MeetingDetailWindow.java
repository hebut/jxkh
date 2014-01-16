package org.iti.jxkh.audit.meeting;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxkh.entity.JXKH_MEETING;
import org.iti.jxkh.entity.JXKH_MeetingDept;
import org.iti.jxkh.entity.JXKH_MeetingMember;
import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.service.JXKHMeetingService;
import org.iti.jxkh.service.JxkhAwardService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.iti.common.util.ConvertUtil;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;

public class MeetingDetailWindow extends Window implements AfterCompose {

	/**
	 * @author CXX
	 */
	private static final long serialVersionUID = 2667514159479952344L;
	private Textbox meetName, department, coUnit,  //sponsor,
			address, subject, scope, record, meetingMember;
	private Datebox time;
	private Label meetingWriter;
	private Button print;
	private YearListbox jiFenTime;
	private Listbox meetingRank, holdCategray;// 会议级别，举办类型
	private Radio yes, no, first, unFirst;
	private Row coUnitRow, zcRow, zxRow, cxRow;
	private Textbox zdep1, zdep2, cdep1, cdep2, xdep1, xdep2;
	private List<WkTUser> memberList = new ArrayList<WkTUser>();
	private List<WkTDept> deptList = new ArrayList<WkTDept>();
	private JXKHMeetingService jxkhMeetingService;
	private JxkhAwardService jxkhAwardService;
	private JXKH_MEETING meeting;
	private List<JXKH_MeetingMember> meetingMemberList = new ArrayList<JXKH_MeetingMember>();
	private List<JXKH_MeetingDept> meetDeptList = new ArrayList<JXKH_MeetingDept>();
	public static final Integer mtRank = 71, mtHold = 72;

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
		meetingRank.setItemRenderer(new meetingRankRenderer());
		holdCategray.setItemRenderer(new holdTypeRenderer());
		jiFenTime.initYearListbox("");
		initListbox();
		type();
	}

	public void initWindow() {
		print.setHref("/print.action?n=meeting&id="+meeting.getMtId());
		meetName.setValue(meeting.getMtName());
		jiFenTime.initYearListbox(meeting.getjxYear());
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
		/*sponsor.setValue(meeting.getMtPerson());// 负责人
*/		if (meeting.getMtTime() != null) {
			time.setValue(ConvertUtil.convertDate(meeting.getMtTime()));
		}
		address.setValue(meeting.getMtAddress());
		subject.setValue(meeting.getMtTheme());
		scope.setValue(meeting.getMtScope());
		// 归档后显示档案号
//		if (meeting.getMtState() == null || meeting.getMtState() == 0
//				|| meeting.getMtState() == 1 || meeting.getMtState() == 2
//				|| meeting.getMtState() == 3 || meeting.getMtState() == 4) {
//			record.setVisible(false);
//			cordId.setVisible(false);
//		} else if (meeting.getMtState() == 5) {
//			record.setVisible(true);
//			cordId.setVisible(true);
//			record.setValue(meeting.getRecordCode());
//		}
		
		// 初始化全部作者
		String memberName = "";
		meetingMemberList = jxkhMeetingService
				.findMeetingMemberByMeetingId(meeting);
		for (int i = 0; i < meetingMemberList.size(); i++) {
			JXKH_MeetingMember mem = (JXKH_MeetingMember) meetingMemberList
					.get(i);
			memberName += mem.getName() + "、";
			// WkTUser user = jxkhAwardService.findWktUserByMemberUserId(mem
			// .getPersonId());
			// memberList.add(user);
			if (mem.getType().equals("0")) {
				WkTUser user = jxkhAwardService
						.findWktUserByMemberUserId(mem.getPersonId());
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
			zcRow.setVisible(true);
			zxRow.setVisible(false);
			cxRow.setVisible(false);

			cdep1.setValue(meeting.getMtCDep());
			zdep1.setValue(meeting.getMtZDep());
		}

		initListbox();
	}

	// 导出会议级别，举办类型listbox列表
	private void initListbox() {
		List<Jxkh_BusinessIndicator> rankList = new ArrayList<Jxkh_BusinessIndicator>();
		rankList.add(new Jxkh_BusinessIndicator());
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
			TypeSelect = ((Jxkh_BusinessIndicator) meeting.getMtType())
					.getKbName();
			System.out.println("渲染器get中得值" + TypeSelect);
			return TypeSelect;
		}
	}

	// 根据举办类型（主办、承办、协办）来显示另外两个（主办单位、承办单位、协办单位）
	public void type() {
		holdCategray.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event event) throws Exception {
				System.out.println(((Jxkh_BusinessIndicator) holdCategray
						.getSelectedItem().getValue()).getKbName());
				if (((Jxkh_BusinessIndicator) holdCategray.getSelectedItem()
						.getValue()).getKbName() == null
						|| ((Jxkh_BusinessIndicator) holdCategray
								.getSelectedItem().getValue()).getKbName()
								.equals(null)) {
					zcRow.setVisible(false);
					zxRow.setVisible(false);
					cxRow.setVisible(false);
				}
				if (((Jxkh_BusinessIndicator) holdCategray.getSelectedItem()
						.getValue()).getKbName().equals("主办")) {
					zcRow.setVisible(false);
					zxRow.setVisible(false);
					cxRow.setVisible(true);
				}
				if (((Jxkh_BusinessIndicator) holdCategray.getSelectedItem()
						.getValue()).getKbName().equals("承办")) {
					zcRow.setVisible(false);
					zxRow.setVisible(true);
					cxRow.setVisible(false);
				}
				if (((Jxkh_BusinessIndicator) holdCategray.getSelectedItem()
						.getValue()).getKbName().equals("协办")) {
					zcRow.setVisible(true);
					zxRow.setVisible(false);
					cxRow.setVisible(false);
				}
			}
		});
	}

	/**
	 * <li>功能描述：关闭当前窗口。
	 */
	public void onClick$close() {
		this.onClose();
	}
	public void onClick$closeAudit(){
		this.onClose();
	}
}
