package org.iti.jxkh.audit.article.meetartical;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxkh.entity.JXKH_HULWMember;
import org.iti.jxkh.entity.JXKH_HYLW;
import org.iti.jxkh.entity.JXKH_HYLWDept;
import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.service.JXKHMeetingService;
import org.iti.jxkh.service.JxkhAwardService;
import org.iti.jxkh.service.JxkhHylwService;
import org.zkoss.zk.ui.Components;
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

public class MeetarticalDetailWindow extends Window implements AfterCompose {

	/**
	 * @author CXX
	 */
	private static final long serialVersionUID = -6866218056861140180L;
	private Textbox lwName, author, department, coUnit, jourName,
			startPage, infoAuthor, meetName, zdep, cdep, xdep, recordId;
	private Row coUnitRow;
	private Label  writer;
	private Radio yes, no, first, unFirst;
	private Listbox slType, hyRank;// , lwjb;
	private Datebox publicDate, startDate;// 发表时间，会议召开时间
	private WkTUser user;
	private Button print;
	private YearListbox jiFenTime;
	private JxkhHylwService jxkhHylwService;
	private JxkhAwardService jxkhAwardService;
	private JXKHMeetingService jxkhMeetingService;
	private JXKH_HYLW meeting;
	private List<WkTUser> memberList = new ArrayList<WkTUser>();
	private List<WkTDept> deptList = new ArrayList<WkTDept>();
	private List<JXKH_HULWMember> hylwMemberList = new ArrayList<JXKH_HULWMember>();
	private List<JXKH_HYLWDept> hylwDeptList = new ArrayList<JXKH_HYLWDept>();

	public static final Integer shoulu = 11, huiyi = 13;

	public JXKH_HYLW getMeeting() {
		return meeting;
	}

	public void setMeeting(JXKH_HYLW meeting) {
		this.meeting = meeting;
	}

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		slType.setItemRenderer(new slTypeRenderer());
		hyRank.setItemRenderer(new hyRankRenderer());
		jiFenTime.initYearListbox("");
		/* lwjb.setItemRenderer(new lwjbRenderer()); */
		initListbox();
	}

	public void initWindow() {
		print.setHref("/print.action?n=meetArtical&id=" + meeting.getLwId());
		lwName.setValue(meeting.getLwName());
		jiFenTime.initYearListbox(meeting.getjxYear());
		if (meeting.getIfUnion() == 1) {
			yes.setSelected(true);
			coUnitRow.setVisible(true);
			coUnit.setValue(meeting.getLwCoDep());
		} else {
			no.setSelected(true);
			coUnitRow.setVisible(false);
		}
		if (meeting.getIfSign() == 1) {
			first.setSelected(true);
		} else {
			unFirst.setSelected(true);
		}
		writer.setValue(meeting.getLwWriter());
		infoAuthor.setValue(meeting.getLwAuthor());// 通讯作者
		if (meeting.getLwTime() != null) {
			publicDate.setValue(ConvertUtil.convertDate(meeting.getLwTime()));
		}
		// if (meeting.getLwDate() != null) {
		// startDate.setValue(ConvertUtil.convertDate(meeting.getLwDate()));
		// }
		/* qc.setValue(meeting.getLwQC()); */
		startPage.setValue(meeting.getLwPages());
		jourName.setValue(meeting.getBookName());
		// meetName.setValue(meeting.getLwHyName());
		// zdep.setValue(meeting.getLwZDep());
		// cdep.setValue(meeting.getLwCDep());
		// xdep.setValue(meeting.getLwXDep());
		
		// 归档后显示档案号
//		if (meeting.getLwState() == null || meeting.getLwState() == 0
//				|| meeting.getLwState() == 1 || meeting.getLwState() == 2
//				|| meeting.getLwState() == 3 || meeting.getLwState() == 4) {
//			recordLable.setVisible(false);
//			recordId.setVisible(false);
//		} else if (meeting.getLwState() == 5) {
//			recordLable.setVisible(true);
//			recordId.setVisible(true);
//			recordId.setValue(meeting.getRecordCode());
//		}

		// 初始化全部作者
		String memberName = "";
		hylwMemberList = jxkhHylwService.findAwardMemberByAwardId(meeting);
		for (int i = 0; i < hylwMemberList.size(); i++) {
			JXKH_HULWMember mem = (JXKH_HULWMember) hylwMemberList.get(i);
			memberName += mem.getName() + "、";
			if (mem.getType() == 0 + "") {
				WkTUser user = jxkhAwardService.findWktUserByMemberUserId(mem
						.getPersonId());
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
		author.setValue(memberName);

		// 初始化内部部门
		String d = "";
		hylwDeptList = jxkhHylwService.findMeetingDeptByMeetingId(meeting);
		for (int i = 0; i < hylwDeptList.size(); i++) {
			JXKH_HYLWDept dept = (JXKH_HYLWDept) hylwDeptList.get(i);
			d += dept.getName() + "、";
			WkTDept depts = jxkhAwardService.findWkTDeptByDept(dept.getDepId());
			deptList.add(depts);
		}
		if (d != "" && d != null)
			d = d.substring(0, d.length() - 1);
		department.setValue(d);

		initListbox();
	}

	private void initListbox() {
		List<Jxkh_BusinessIndicator> rankList = new ArrayList<Jxkh_BusinessIndicator>();
		rankList.add(new Jxkh_BusinessIndicator());
		if (jxkhMeetingService.findRank(shoulu) != null) {
			rankList.addAll(jxkhMeetingService.findRank(shoulu));
		}
		slType.setModel(new ListModelList(rankList));
		slType.setSelectedIndex(0);

		List<Jxkh_BusinessIndicator> holdList = new ArrayList<Jxkh_BusinessIndicator>();
		holdList.add(new Jxkh_BusinessIndicator());
		if (jxkhMeetingService.findRank(huiyi) != null) {
			holdList.addAll(jxkhMeetingService.findRank(huiyi));
		}
		hyRank.setModel(new ListModelList(holdList));
		hyRank.setSelectedIndex(0);

		/*
		 * String[] s = { "一级", " 二级", "权威", "CSSCI", "其他公开刊物" }; List<String>
		 * lwjbList = new ArrayList<String>(); for (int i = 0; i < 5; i++) {
		 * lwjbList.add(s[i]); } lwjb.setModel(new ListModelList(lwjbList));
		 */
	}

	/** 收录类别列表渲染器 */
	public class slTypeRenderer implements ListitemRenderer {
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
//			if (meeting != null && type.equals(meeting.getLwType())) {
//				item.setSelected(true);
//			}
		}
	}

	/** 会议级别列表渲染器 */
	public class hyRankRenderer implements ListitemRenderer {
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
			if (meeting != null && type.equals(meeting.getLwGrade())) {
				item.setSelected(true);
			}
		}
	}

	/** 论文等级列表渲染器 */
	public class lwjbRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			String rank = (String) data;
			item.setValue(rank);
			item.setLabel(rank);
			if (item.getIndex() == 0)
				item.setSelected(true);
			if (meeting != null && meeting.getLwRank().equals(rank))
				item.setSelected(true);
		}
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
	public void onClick$closeScore(){
		this.onClose();
	}
}
