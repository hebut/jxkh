package org.iti.jxkh.audit.article.journal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxkh.entity.JXKH_QKLW;
import org.iti.jxkh.entity.JXKH_QKLWDept;
import org.iti.jxkh.entity.JXKH_QKLWMember;
import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.service.JXKHMeetingService;
import org.iti.jxkh.service.JxkhAwardService;
import org.iti.jxkh.service.JxkhQklwService;
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

public class JournalDetailWindow extends Window implements AfterCompose {

	/**
	 * @author CXX
	 */
	private static final long serialVersionUID = -1938041619984991768L;

	private Textbox lwName, author, department, coUnit, infoAuthor,
			jourName, qc, startPage, recordId;
	private Row coUnitRow;
	private Label  writer;
	private Radio yes, no, first, unFirst;
	private Datebox shouLuTime, publicDate;
	private Listbox slType, qkType;//, lwjb;// 收录类别、期刊类别、论文等级
	private JXKH_QKLW meeting;
	private WkTUser user;
	private Button print;
	private YearListbox jiFenTime;
	private JxkhQklwService jxkhQklwService;
	private JxkhAwardService jxkhAwardService;
	private JXKHMeetingService jxkhMeetingService;
	private List<WkTUser> memberList = new ArrayList<WkTUser>();
	private List<WkTDept> deptList = new ArrayList<WkTDept>();
	private List<JXKH_QKLWMember> qklwMemberList = new ArrayList<JXKH_QKLWMember>();
	private List<JXKH_QKLWDept> qklwDeptList = new ArrayList<JXKH_QKLWDept>();
	public static final Integer shoulu = 11, qikan = 12;

	public JXKH_QKLW getMeeting() {
		return meeting;
	}

	public void setMeeting(JXKH_QKLW meeting) {
		this.meeting = meeting;
	}

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		slType.setItemRenderer(new slTypeRenderer());
		qkType.setItemRenderer(new qkTypeRenderer());
		jiFenTime.initYearListbox("");
		/*lwjb.setItemRenderer(new lwjbRenderer());*/
		initListbox();
	}

	public void initWindow() {
		print.setHref("/print.action?n=journal&id=" + meeting.getLwId());
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

		writer.setValue(meeting.getLwWriter());
		infoAuthor.setValue(meeting.getLwAuthor());// 通讯作者
//		if (meeting.getLwTime() != null && !"".equals(meeting.getLwTime())) {
//			shouLuTime.setValue(ConvertUtil.convertDate(meeting.getLwTime()));
//		}
		if (meeting.getLwDate() != null) {
			publicDate.setValue(ConvertUtil.convertDate(meeting.getLwDate()));
		}
		if (meeting.getIfSign() == 1) {
			first.setSelected(true);
		} else {
			unFirst.setSelected(true);
		}
		jourName.setValue(meeting.getKwName());
		qc.setValue(meeting.getLwQC());
		startPage.setValue(meeting.getLwPages());
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
		qklwMemberList = jxkhQklwService.findAwardMemberByAwardId(meeting);
		for (int i = 0; i < qklwMemberList.size(); i++) {
			JXKH_QKLWMember mem = (JXKH_QKLWMember) qklwMemberList.get(i);
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
		qklwDeptList = jxkhQklwService.findMeetingDeptByMeetingId(meeting);
		for (int i = 0; i < qklwDeptList.size(); i++) {
			JXKH_QKLWDept dept = (JXKH_QKLWDept) qklwDeptList.get(i);
			d += dept.getName() + "、";
			WkTDept depts = jxkhAwardService.findWkTDeptByDept(dept.getDepId());
			deptList.add(depts);
		}
		if (d != "" && d != null)
			d = d.substring(0, d.length() - 1);
		department.setValue(d);
		initListbox();
	}

	// 导出收录类别、期刊类别listbox列表
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
		if (jxkhMeetingService.findRank(qikan) != null) {
			holdList.addAll(jxkhMeetingService.findRank(qikan));
		}
		qkType.setModel(new ListModelList(holdList));
		qkType.setSelectedIndex(0);

		/*String[] s = { "一级", " 二级", "权威", "CSSCI", "其他公开刊物" };

		List<String> lwjbList = new ArrayList<String>();
		for (int i = 0; i < 5; i++) {
			lwjbList.add(s[i]);
		}
		lwjb.setModel(new ListModelList(lwjbList));*/
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
			if (meeting != null && type.equals(meeting.getQkGrade())) {
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
