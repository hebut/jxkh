package org.iti.jxkh.busiAudit.article.meetartical;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxkh.business.award.ChooseDeptWin;
import org.iti.jxkh.business.award.ChooseMemberWin;
import org.iti.jxkh.business.meeting.AssignDeptWindow;
import org.iti.jxkh.business.meeting.UpfileWindow;
import org.iti.jxkh.entity.JXKH_HULWMember;
import org.iti.jxkh.entity.JXKH_HYLW;
import org.iti.jxkh.entity.JXKH_HYLWDept;
import org.iti.jxkh.entity.JXKH_HYLWFile;
import org.iti.jxkh.entity.JXKH_HYlwSlMessage;
import org.iti.jxkh.entity.JXKH_MEETING;
import org.iti.jxkh.entity.Jxkh_Award;
import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.service.BusinessIndicatorService;
import org.iti.jxkh.service.JXKHMeetingService;
import org.iti.jxkh.service.JxkhAwardService;
import org.iti.jxkh.service.JxkhHylwService;
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

public class MeetarticalDetailWindow extends Window implements AfterCompose {

	/**
	 * @author CuiXiaoxin
	 */
	private static final long serialVersionUID = 5535799099919802996L;
	private Tab baseTab, fileTab, scoreTab;
	private Listbox personScore, departmentScore;
	private Toolbarbutton submit, close/* , submitScore */;
	private Textbox lwName, author, department, coUnit, jourName, startPage, infoAuthor/*
																						 * ,
																						 * meetName
																						 * ,
																						 * zdep
																						 * ,
																						 * cdep
																						 * ,
																						 * xdep
																						 */, recordId;
	private Row coUnitRow;
	private Label recordLable, writer;
	private Radio yes, no, first, unFirst;
	private Listbox slType, hyRank, slMessListbox;// , lwjb;
	private Datebox publicDate/* , startDate */, shouLuTime;// 发表时间，会议召开时间
	private WkTUser user;
	private Button print;
	private YearListbox jiFenTime, jiFenTime1;
	private JxkhHylwService jxkhHylwService;
	private JxkhAwardService jxkhAwardService;
	private JXKHMeetingService jxkhMeetingService;
	private JXKH_HYLW meeting;
	private List<WkTUser> memberList = new ArrayList<WkTUser>();
	private List<WkTDept> deptList = new ArrayList<WkTDept>();
	private List<JXKH_HULWMember> hylwMemberList = new ArrayList<JXKH_HULWMember>();
	private List<JXKH_HYLWDept> hylwDeptList = new ArrayList<JXKH_HYLWDept>();
	private JXKH_HYLWFile file;
	public static final Integer shoulu = 11, huiyi = 13;
	private BusinessIndicatorService businessIndicatorService;
	private List<JXKH_HYlwSlMessage> slMessages = new ArrayList<JXKH_HYlwSlMessage>();
	private List<String[]> slMessagesTemp = new ArrayList<String[]>();
	private Listbox applyList1, applyList2, applyList3, applyList4, applyList5;
	private Set<JXKH_HYLWFile> fileList;

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
		submit.setVisible(true);
		this.addForward(Events.ON_OK, submit, Events.ON_CLICK);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");// 获取当前登录用户
		slType.setItemRenderer(new slTypeRenderer());
		hyRank.setItemRenderer(new hyRankRenderer());
		/* lwjb.setItemRenderer(new lwjbRenderer()); */
		jiFenTime.initYearListbox("");
		jiFenTime1.initYearListbox("");
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
		// 点击绩分页面时会议信息页面和文档信息页面的保存和退出按钮隐藏
		scoreTab.addEventListener(Events.ON_CLICK, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				submit.setVisible(false);
				close.setVisible(false);
			}
		});
		baseTab.addEventListener(Events.ON_CLICK, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				submit.setVisible(true);
				close.setVisible(true);
			}
		});
		fileTab.addEventListener(Events.ON_CLICK, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				submit.setVisible(true);
				close.setVisible(true);
			}
		});
	}

	public void initWindow() {
		if (meeting == null) {
			writer.setValue(user.getKuName());
			scoreTab.setDisabled(true);
			fileTab.setDisabled(true);
		} else {
			scoreTab.setDisabled(false);
			fileTab.setDisabled(false);
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
			// startDate
			// .setValue(ConvertUtil.convertDate(meeting.getLwDate()));
			// }
			/* qc.setValue(meeting.getLwQC()); */
			startPage.setValue(meeting.getLwPages());
			jourName.setValue(meeting.getBookName());
			// meetName.setValue(meeting.getLwHyName());
			// zdep.setValue(meeting.getLwZDep());
			// cdep.setValue(meeting.getLwCDep());
			// xdep.setValue(meeting.getLwXDep());
			// 归档后显示档案号
			if (meeting.getLwState() == null || meeting.getLwState() == 0 || meeting.getLwState() == 1 || meeting.getLwState() == 2 || meeting.getLwState() == 3 || meeting.getLwState() == 4 || meeting.getLwState() == 5) {
				recordLable.setVisible(false);
				recordId.setVisible(false);
			} else if (meeting.getLwState() == 6) {
				recordLable.setVisible(true);
				recordId.setVisible(true);
				recordId.setValue(meeting.getRecordCode());
			}

			// 初始化全部作者
			String memberName = "";
			hylwMemberList = jxkhHylwService.findAwardMemberByAwardId(meeting);
			for (int i = 0; i < hylwMemberList.size(); i++) {
				JXKH_HULWMember mem = (JXKH_HULWMember) hylwMemberList.get(i);
				memberName += mem.getName() + "、";
				if (mem.getType().equals(WkTUser.TYPE_IN + "")) {
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

			// 新的附件初始化20120305
			arrList1.clear();
			arrList2.clear();
			arrList3.clear();
			arrList4.clear();
			arrList5.clear();
			fileList = meeting.getFiles();
			Object[] file = fileList.toArray();
			for (int j = 0; j < file.length; j++) {
				JXKH_HYLWFile f = (JXKH_HYLWFile) file[j];

				if (f.getFileType().equals("杂志封面")) {
					String[] arr = new String[4];
					arr[0] = f.getPath();
					arr[1] = f.getName();
					arr[2] = f.getDate();
					arr[3] = f.getFileType();
					arrList1.add(arr);
				}
				if (f.getFileType().equals("杂志出版信息页")) {
					String[] arr = new String[4];
					arr[0] = f.getPath();
					arr[1] = f.getName();
					arr[2] = f.getDate();
					arr[3] = f.getFileType();
					arrList2.add(arr);
				}
				if (f.getFileType().equals("杂志目录页")) {
					String[] arr = new String[4];
					arr[0] = f.getPath();
					arr[1] = f.getName();
					arr[2] = f.getDate();
					arr[3] = f.getFileType();
					arrList3.add(arr);
				}
				if (f.getFileType().equals("杂志论文所在页")) {
					String[] arr = new String[4];
					arr[0] = f.getPath();
					arr[1] = f.getName();
					arr[2] = f.getDate();
					arr[3] = f.getFileType();
					arrList4.add(arr);
				}
				if (f.getFileType().equals("获奖证书")) {
					String[] arr = new String[4];
					arr[0] = f.getPath();
					arr[1] = f.getName();
					arr[2] = f.getDate();
					arr[3] = f.getFileType();
					arrList5.add(arr);
				}

			}
			applyList1.setItemRenderer(new FilesRenderer1());
			applyList1.setModel(new ListModelList(arrList1));
			applyList2.setItemRenderer(new FilesRenderer2());
			applyList2.setModel(new ListModelList(arrList2));
			applyList3.setItemRenderer(new FilesRenderer3());
			applyList3.setModel(new ListModelList(arrList3));
			applyList4.setItemRenderer(new FilesRenderer4());
			applyList4.setModel(new ListModelList(arrList4));
			applyList5.setItemRenderer(new FilesRenderer5());
			applyList5.setModel(new ListModelList(arrList5));

			// 收录信息初始化
			slMessagesTemp.clear();
			List<JXKH_HYlwSlMessage> slMessages = jxkhHylwService.findHylwSlMessageByMeetingId(meeting);
			for (int k = 0; k < slMessages.size(); k++) {
				JXKH_HYlwSlMessage q = slMessages.get(k);
				String[] arr = new String[3];
				arr[0] = q.getLwType();
				arr[1] = q.getLwTime();
				arr[2] = q.getJxYear();
				slMessagesTemp.add(arr);
			}
			slMessListbox.setItemRenderer(new SlMessageRenderer());
			ComparatorsTemp c = new ComparatorsTemp();
			Collections.sort(slMessagesTemp, c);
			slMessListbox.setModel(new ListModelList(slMessagesTemp));
		}
		initListbox();
	}

	private void initListbox() {
		List<Jxkh_BusinessIndicator> rankList = new ArrayList<Jxkh_BusinessIndicator>();
		rankList.add(new Jxkh_BusinessIndicator());
		if (jxkhMeetingService.findRank(shoulu) == null) {
		} else {
			rankList.addAll(jxkhMeetingService.findRank(shoulu));
		}
		slType.setModel(new ListModelList(rankList));
		slType.setSelectedIndex(0);

		List<Jxkh_BusinessIndicator> holdList = new ArrayList<Jxkh_BusinessIndicator>();
		holdList.add(new Jxkh_BusinessIndicator());
		if (jxkhMeetingService.findRank(huiyi) == null) {
		} else {
			holdList.addAll(jxkhMeetingService.findRank(huiyi));
		}
		hyRank.setModel(new ListModelList(holdList));
		hyRank.setSelectedIndex(0);

		personScore.setModel(new ListModelList(hylwMemberList));
		departmentScore.setModel(new ListModelList(hylwDeptList));
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
			// if (meeting != null && type.equals(meeting.getLwType())) {
			// item.setSelected(true);
			// }
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

	// 单击保存按钮触发保存事件
	public void onClick$submit() {
		if (author.getValue() == null || author.getValue() == "") {
			try {
				Messagebox.show("全部作者不能为空！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			author.focus();
			return;
		}
		if (department.getValue() == null || department.getValue() == "") {
			try {
				Messagebox.show("院内部门不能为空！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			department.focus();
			return;
		}
		if (hyRank.getSelectedIndex() == 0) {
			try {
				Messagebox.show("会议级别不能为空！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			hyRank.focus();
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
		if (meeting != null && meeting.getLwId() != null) {
			if (arrList1.size() <= 0) {
				try {
					Messagebox.show("文集封面必须上传！");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return;
			}
			if (arrList2.size() <= 0) {
				try {
					Messagebox.show("文集出版信息页必须上传！");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return;
			}
			if (arrList3.size() <= 0) {
				try {
					Messagebox.show("文集目录页必须上传！");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return;
			}
			if (arrList4.size() <= 0) {
				try {
					Messagebox.show("文集论文所在页（全部）必须上传！");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return;
			}
		}
		if (meeting == null) {
			meeting = new JXKH_HYLW();
			meeting.setLwWriter(user.getKuName());
			meeting.setWriterId(user.getKuLid());
		}
		setEntity();
		jxkhAwardService.saveOrUpdate(meeting);
		// 收录信息的保存（20120322）
		List<JXKH_HYlwSlMessage> oldlist = new ArrayList<JXKH_HYlwSlMessage>();
		oldlist = jxkhHylwService.findHylwSlMessageByMeetingId(meeting);
		if (oldlist != null)
			jxkhAwardService.deleteAll(oldlist);
		for (int r = 0; r < slMessagesTemp.size(); r++) {
			String[] mes = slMessagesTemp.get(r);
			JXKH_HYlwSlMessage sl = new JXKH_HYlwSlMessage();
			sl.setLwType(mes[0]);
			sl.setLwTime(mes[1]);
			sl.setJxYear(mes[2]);
			sl.setMeeting(meeting);
			jxkhAwardService.saveOrUpdate(sl);
			slMessages.add(sl);
		}
		meeting.setSlMessage(slMessages);
		jxkhAwardService.saveOrUpdate(meeting);

		try {
			Messagebox.show("会议论文保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			scoreTab.setDisabled(false);
			fileTab.setDisabled(false);
			/*List<JXKH_HULWMember> memberList = jxkhHylwService.findAwardMemberByAwardId(meeting);
			List<JXKH_HYLWDept> deptList = jxkhHylwService.findMeetingDeptByMeetingId(meeting);*/
			hylwMemberList = jxkhHylwService.findAwardMemberByAwardId(meeting);
			hylwDeptList = jxkhHylwService.findMeetingDeptByMeetingId(meeting);
			personScore.setModel(new ListModelList(hylwMemberList));
			departmentScore.setModel(new ListModelList(hylwDeptList));
			Events.postEvent(Events.ON_CHANGE, this, null);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				Messagebox.show("会议论文保存失败，请重试！", "提示", Messagebox.OK, Messagebox.ERROR);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}

	private Float computeScore(Jxkh_BusinessIndicator lwRank, Jxkh_BusinessIndicator lwType, Short sign) {
		float result = 0f;
		String type = "";
		String typeThisYear = "";
		float res = 0f;
		Properties property = PropertiesLoader.loader("title", "title.properties");
		float sci = 0, ei = 0, istp = 0, core = 0, nation = 0, province = 0;
		boolean isSameYear = true;
		List<JXKH_HYLW> qlist = this.jxkhHylwService.findHYLWbyName(meeting.getLwName(), jiFenTime.getSelectedItem().getValue() + "");
		if (qlist != null && qlist.size() > 0) {
			isSameYear = false;
		}
		Jxkh_BusinessIndicator lwTypeThisYear = new Jxkh_BusinessIndicator();
		List<Jxkh_BusinessIndicator> slBusinessIndecatorList = new ArrayList<Jxkh_BusinessIndicator>();
		for (String[] sl : this.slMessagesTemp) {
			slBusinessIndecatorList.add(this.businessIndicatorService.getEntityByName(sl[0]));
		}
		if (isSameYear) {
			if (slBusinessIndecatorList.size() == 0) {
			} else {
				lwType = slBusinessIndecatorList.get(slBusinessIndecatorList.size() - 1);
			}
		} else {
			for (int j = slMessagesTemp.size() - 1; j >= 0; j--) {
				if (slMessagesTemp.get(j)[2].equals(qlist.get(0).getjxYear())) {
					lwType = slBusinessIndecatorList.get(j);
					break;
				}
			}
			for (int j = slMessagesTemp.size() - 1; j >= 0; j--) {
				if (slMessagesTemp.get(j)[2].equals(jiFenTime.getSelectedItem().getValue() + "")) {
					lwTypeThisYear = slBusinessIndecatorList.get(j);
					if (lwTypeThisYear.getKbName().equals(property.getProperty("sci"))) {
						if (sign.shortValue() == Jxkh_Award.YES) {
							typeThisYear = property.getProperty("sci");
						} else if (sign.shortValue() == Jxkh_Award.NO) {
							typeThisYear = property.getProperty("ei");
						}
					} else if (lwTypeThisYear.getKbName().equals(property.getProperty("ei"))) {
						if (sign.shortValue() == Jxkh_Award.YES) {
							typeThisYear = property.getProperty("ei");
						} else if (sign.shortValue() == Jxkh_Award.NO) {
							typeThisYear = property.getProperty("istp");
						}
					} else if (lwTypeThisYear.getKbName().equals(property.getProperty("istp"))) {
						if (sign.shortValue() == Jxkh_Award.YES) {
							typeThisYear = property.getProperty("istp");
						} else if (sign.shortValue() == Jxkh_Award.NO) {
							typeThisYear = property.getProperty("core");
						}
					}

					break;
				}
			}
		}
		if (lwType.getKbName() != null) {
			if (lwType.getKbName().equals(property.getProperty("sci"))) {
				if (sign.shortValue() == JXKH_HYLW.YES) {
					sci += 1f;
					result = sci;
					type = property.getProperty("sci");
				} else if (sign.shortValue() == JXKH_HYLW.NO) {
					ei += 1f;
					result = ei;
					type = property.getProperty("ei");
				}
			} else if (lwType.getKbName().equals(property.getProperty("ei"))) {
				if (sign.shortValue() == JXKH_HYLW.YES) {
					ei += 1f;
					result = ei;
					type = property.getProperty("ei");
				} else if (sign.shortValue() == JXKH_HYLW.NO) {
					istp += 1f;
					result = istp;
					type = property.getProperty("istp");
				}
			} else if (lwType.getKbName().equals(property.getProperty("istp"))) {
				if (sign.shortValue() == JXKH_HYLW.YES) {
					istp += 1f;
					result = istp;
					type = property.getProperty("istp");
				} else if (sign.shortValue() == JXKH_HYLW.NO) {
					core += 1f;
					result = core;
					type = property.getProperty("international");
				}
			}
		}
		if (lwRank.getKbName() != null && lwType.getKbName() == null) {
			if (lwRank.getKbName().equals(property.getProperty("international"))) {
				if (sign.shortValue() == JXKH_HYLW.YES) {
					core += 1f;
					result = core;
					type = property.getProperty("international");
				} else if (sign.shortValue() == JXKH_HYLW.NO) {
					nation += 1f;
					result = nation;
					type = property.getProperty("nation");
				}
			} else if (lwRank.getKbName().equals(property.getProperty("nation"))) {
				if (sign.shortValue() == JXKH_HYLW.YES) {
					nation += 1f;
					result = nation;
					type = property.getProperty("nation");
				} else if (sign.shortValue() == JXKH_HYLW.NO) {
					province += 1f;
					result = province;
					type = property.getProperty("province");
				}
			} else if (lwRank.getKbName().equals(property.getProperty("province"))) {
				if (sign.shortValue() == JXKH_HYLW.YES) {
					province += 1f;
					result = province;
					type = property.getProperty("province");
				} else if (sign.shortValue() == JXKH_HYLW.NO) {
					province += 1f * 0.5f;
					result = province;
					type = property.getProperty("province");
				}
			}
		}
		Jxkh_BusinessIndicator bi = (Jxkh_BusinessIndicator) businessIndicatorService.getEntityByName(type);
		lwTypeThisYear = (Jxkh_BusinessIndicator) businessIndicatorService.getEntityByName(typeThisYear);
		if (bi == null) {
			res += result * 0;
		} else if (isSameYear) {
			res += result * bi.getKbScore() * bi.getKbIndex();
			this.meeting.setComputeType(lwType != null ? lwType.getKbId() : lwRank.getKbId());
		} else {
			if ((lwType.getKbIndex() != null && lwType.getKbIndex() >= lwTypeThisYear.getKbIndex()) || lwTypeThisYear == null) {
				res = 0;
			} else {
				res += result * bi.getKbScore() * (lwTypeThisYear.getKbIndex() - bi.getKbIndex());
				this.meeting.setComputeType(lwTypeThisYear.getKbId());
			}
		}
		return Float.valueOf(res);
	}

	public void setEntity() {
		Short sign;
		Jxkh_BusinessIndicator lwRank = new Jxkh_BusinessIndicator();
		Jxkh_BusinessIndicator lwType = new Jxkh_BusinessIndicator();
		meeting.setLwName(lwName.getValue());
		if (yes.isChecked()) {
			meeting.setIfUnion(JXKH_HYLW.YES);
			meeting.setLwCoDep(coUnit.getValue());
		} else {
			meeting.setIfUnion(JXKH_HYLW.NO);
			meeting.setLwCoDep(null);
		}
		if (unFirst.isChecked()) {
			meeting.setIfSign(JXKH_MEETING.NO);
			sign = JXKH_MEETING.NO;
		} else {
			meeting.setIfSign(JXKH_MEETING.YES);
			sign = JXKH_MEETING.YES;
		}
		if (hyRank.getSelectedItem() != null) {
			meeting.setLwGrade((Jxkh_BusinessIndicator) hyRank.getSelectedItem().getValue());
			lwRank = (Jxkh_BusinessIndicator) hyRank.getSelectedItem().getValue();
		}
		Float score = computeScore(lwRank, lwType, sign);
		meeting.setScore(score);
		meeting.setjxYear(jiFenTime.getSelectedItem().getValue() + "");
		meeting.setRecordCode(recordId.getValue());// 档案号
		meeting.setLwAuthor(infoAuthor.getValue());// 通讯作者
		if (publicDate.getValue() != null) {
			meeting.setLwTime(ConvertUtil.convertDateString(publicDate.getValue()));
		}
		meeting.setBookName(jourName.getValue());
		meeting.setLwPages(startPage.getValue());

		// 把成员放入JXKH_HULWMember表中
		List<JXKH_HULWMember> mlist = new ArrayList<JXKH_HULWMember>();
		if (meeting.getLwId() != null) {
			mlist = jxkhHylwService.findAwardMemberByAwardId(meeting);
			if (mlist != null && mlist.size() != 0) {
				jxkhHylwService.deleteAll(mlist);
			}
		}
		mlist.clear();
		int j = 1;
		for (WkTUser user : memberList) {
			JXKH_HULWMember member = new JXKH_HULWMember();
			member.setHYLWName(meeting);
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
			if ((user.getKdId() != null && user.getDept() != null && user.getDept().getKdNumber().equals(WkTDept.guanlidept)) || user.getKdId() == null)
				member.setAssignDep(deptList.get(0).getKdName());
			else
				member.setAssignDep(user.getDept().getKdName());
			j++;
			mlist.add(member);
		}
		meeting.setLwAll(mlist);

		// 把部门放入JXKH_HYLWDept表中
		List<JXKH_HYLWDept> dlist = new ArrayList<JXKH_HYLWDept>();
		if (meeting.getLwId() != null) {
			dlist = jxkhHylwService.findMeetingDeptByMeetingId(meeting);
			if (dlist != null && dlist.size() != 0) {
				jxkhHylwService.deleteAll(dlist);
			}
		}
		dlist.clear();
		int i = 1;
		for (WkTDept wktDept : deptList) {
			JXKH_HYLWDept dept = new JXKH_HYLWDept();
			dept.setMeeting(meeting);
			dept.setName(wktDept.getKdName());
			dept.setDepId(wktDept.getKdNumber());
			dept.setRank(i);
			i++;

			// 部门默认的分值0503
			float fen = 0.0f;
			for (int g = 0; g < mlist.size(); g++) {
				JXKH_HULWMember m = mlist.get(g);
				if (m.getAssignDep().equals(dept.getName())) {
					fen += m.getScore();
				}
			}
			float scor = (float) (Math.round(fen * 1000)) / 1000;
			dept.setScore(scor);

			dlist.add(dept);
		}
		meeting.setLwDep(dlist);

		// 新的附件保存(20120305)
		List<String[]> arrList = new ArrayList<String[]>();
		Set<JXKH_HYLWFile> fset = new HashSet<JXKH_HYLWFile>();
		Set<JXKH_HYLWFile> oldFile = new HashSet<JXKH_HYLWFile>();
		oldFile = meeting.getFiles();
		if (oldFile != null) {
			for (Object o : oldFile.toArray()) {
				JXKH_HYLWFile f = (JXKH_HYLWFile) o;
				if (f != null)
					jxkhMeetingService.delete(f);
			}
		}
		if (meeting.getFiles() != null)
			meeting.getFiles().clear();
		if (arrList1.size() != 0 || arrList1 != null)
			arrList.addAll(arrList1);
		if (arrList2.size() != 0 || arrList2 != null)
			arrList.addAll(arrList2);
		if (arrList3.size() != 0 || arrList3 != null)
			arrList.addAll(arrList3);
		if (arrList4.size() != 0 || arrList4 != null)
			arrList.addAll(arrList4);
		if (arrList5.size() != 0 || arrList5 != null)
			arrList.addAll(arrList5);
		for (int r = 0; r < arrList.size(); r++) {
			String[] s = arrList.get(r);
			file = new JXKH_HYLWFile();
			file.setMeeting(meeting);
			file.setPath(s[0]);
			file.setName(s[1]);
			file.setDate(s[2]);
			file.setFileType(s[3]);
			fset.add(file);
		}
		meeting.setFiles(fset);
	}

	// 单击选择成员按钮，触发弹出选择成员页面事件
	public void onClick$chooseMember() throws SuspendNotAllowedException, InterruptedException {
		final ChooseMemberWin win = (ChooseMemberWin) Executions.createComponents("/admin/jxkh/busiAudit/meeting/choosemember.zul", null, null);
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
				author.setValue(members);
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
				if (ui.getDept().getKdId().equals(uj.getDept().getKdId())) {
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
		final ChooseDeptWin win = (ChooseDeptWin) Executions.createComponents("/admin/jxkh/busiAudit/meeting/choosedept.zul", null, null);
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

	/**
	 * <li>功能描述：关闭当前窗口。
	 */
	public void onClick$close() {
		this.onClose();
	}

	// 20120305
	private List<String[]> arrList1 = new ArrayList<String[]>();

	public void onClick$ups1() {
		final UpfileWindow w = (UpfileWindow) Executions.createComponents("/admin/jxkh/busiAudit/meeting/upfile.zul", null, null);
		w.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				String filePath = (String) Executions.getCurrent().getAttribute("path");
				String fileName = (String) Executions.getCurrent().getAttribute("title");
				String upTime = (String) Executions.getCurrent().getAttribute("upTime");
				applyList1.setItemRenderer(new FilesRenderer1());
				String[] arr = new String[4];
				arr[0] = filePath;
				arr[1] = fileName;
				arr[2] = upTime;
				arr[3] = "杂志封面";
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
	 * <li>功能描述：文档信息的listbox(20120305)
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

	// 20120305
	private List<String[]> arrList2 = new ArrayList<String[]>();

	public void onClick$ups2() {
		final UpfileWindow w = (UpfileWindow) Executions.createComponents("/admin/jxkh/busiAudit/meeting/upfile.zul", null, null);
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
				arr[3] = "杂志出版信息页";
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
	 * <li>功能描述：文档信息的listbox(20120305)
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

	// 20120305
	private List<String[]> arrList3 = new ArrayList<String[]>();

	public void onClick$ups3() {
		final UpfileWindow w = (UpfileWindow) Executions.createComponents("/admin/jxkh/busiAudit/meeting/upfile.zul", null, null);
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
				arr[3] = "杂志目录页";
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
	 * <li>功能描述：文档信息的listbox(20120305)
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

	// 20120305
	private List<String[]> arrList4 = new ArrayList<String[]>();

	public void onClick$ups4() {
		final UpfileWindow w = (UpfileWindow) Executions.createComponents("/admin/jxkh/busiAudit/meeting/upfile.zul", null, null);
		w.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				String filePath = (String) Executions.getCurrent().getAttribute("path");
				String fileName = (String) Executions.getCurrent().getAttribute("title");
				String upTime = (String) Executions.getCurrent().getAttribute("upTime");
				applyList4.setItemRenderer(new FilesRenderer4());
				String[] arr = new String[4];
				arr[0] = filePath;
				arr[1] = fileName;
				arr[2] = upTime;
				arr[3] = "杂志论文所在页";
				arrList4.add(arr);
				applyList4.setModel(new ListModelList(arrList4));
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
	 * <li>功能描述：文档信息的listbox(20120305)
	 */
	public class FilesRenderer4 implements ListitemRenderer {

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
								arrList4.remove(str);
								applyList4.setModel(new ListModelList(arrList4));
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

	// 20120305
	private List<String[]> arrList5 = new ArrayList<String[]>();

	public void onClick$ups5() {
		final UpfileWindow w = (UpfileWindow) Executions.createComponents("/admin/jxkh/busiAudit/meeting/upfile.zul", null, null);
		w.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				String filePath = (String) Executions.getCurrent().getAttribute("path");
				String fileName = (String) Executions.getCurrent().getAttribute("title");
				String upTime = (String) Executions.getCurrent().getAttribute("upTime");
				applyList5.setItemRenderer(new FilesRenderer5());
				String[] arr = new String[4];
				arr[0] = filePath;
				arr[1] = fileName;
				arr[2] = upTime;
				arr[3] = "获奖证书";
				arrList5.add(arr);
				applyList5.setModel(new ListModelList(arrList5));
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
	 * <li>功能描述：文档信息的listbox(20120305)
	 */
	public class FilesRenderer5 implements ListitemRenderer {

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
								arrList5.remove(str);
								applyList5.setModel(new ListModelList(arrList5));
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

	public void onClick$add() {
		if (slType.getSelectedIndex() == 0) {
			try {
				Messagebox.show("收录类别不能为空！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			slType.focus();
			return;
		}
		Jxkh_BusinessIndicator lwType = (Jxkh_BusinessIndicator) slType.getSelectedItem().getValue();
		if (shouLuTime.getValue() == null) {
			try {
				Messagebox.show("收录时间不能为空！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			shouLuTime.focus();
			return;
		}
		String time = ConvertUtil.convertDateString(shouLuTime.getValue());
		if (jiFenTime1.getSelectedIndex() == 0 || jiFenTime1.getSelectedItem() == null) {
			try {
				Messagebox.show("请选择绩分年度！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			jiFenTime1.focus();
			return;
		}
		String jxYear = jiFenTime1.getSelectedItem().getValue() + "";
		String[] str = new String[3];
		str[0] = lwType.getKbName();
		str[1] = time;
		str[2] = jxYear;
		slMessagesTemp.add(str);
		slMessListbox.setItemRenderer(new SlMessageRenderer());
		ComparatorsTemp c = new ComparatorsTemp();
		Collections.sort(slMessagesTemp, c);
		slMessListbox.setModel(new ListModelList(slMessagesTemp));
	}

	public class SlMessageRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem arg0, Object arg1) throws Exception {
			final String[] str = (String[]) arg1;
			arg0.setValue(str);
			Listcell c0 = new Listcell();
			Listcell c1 = new Listcell(str[0]);
			Listcell c2 = new Listcell(str[1]);
			Listcell c3 = new Listcell(str[2]);
			arg0.appendChild(c0);
			arg0.appendChild(c1);
			arg0.appendChild(c2);
			arg0.appendChild(c3);
		}
	}

	public void onClick$del() throws InterruptedException {
		if (slMessListbox.getSelectedItems() == null || slMessListbox.getSelectedItems().size() <= 0) {
			try {
				Messagebox.show("请选择要删除的收录信息！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		Messagebox.show("确定删除吗?", "确定", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
			public void onEvent(Event evt) throws InterruptedException {
				if (evt.getName().equals("onOK")) {
					@SuppressWarnings("unchecked")
					Iterator<Listitem> it = slMessListbox.getSelectedItems().iterator();
					String[] lw = new String[3];
					while (it.hasNext()) {
						lw = (String[]) it.next().getValue();
						slMessagesTemp.remove(lw);
					}
					ComparatorsTemp c = new ComparatorsTemp();
					Collections.sort(slMessagesTemp, c);
					slMessListbox.setModel(new ListModelList(slMessagesTemp));
				}
			}
		});
	}

	/** 个人绩分渲染器 */
	public class personScoreRenderer implements ListitemRenderer {

		@Override
		public void render(Listitem item, Object data) throws Exception {

			if (data == null)
				return;
			final JXKH_HULWMember member = (JXKH_HULWMember) data;
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

			final List<JXKH_HYLWDept> temp = jxkhHylwService.findMeetingDepByMeetingId(meeting);
			// 弹出指定部门页面
			bar.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event event) throws Exception {

					AssignDeptWindow w = (AssignDeptWindow) Executions.createComponents("/admin/jxkh/busiAudit/meeting/assignDept.zul", null, null);
					try {
						w.setFlag("HYLW");
						w.setDept(temp);
						w.setMember(member);
						w.initWindow();
						w.doModal();

						// 指定部门完成后自动计算得分
						w.addEventListener(Events.ON_CHANGE, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								List<JXKH_HULWMember> tempMemberList = jxkhHylwService.findAwardMemberByAwardId(meeting);
								personScore.setModel(new ListModelList(tempMemberList));
								for (int k = 0; k < hylwDeptList.size(); k++) {
									JXKH_HYLWDept d = hylwDeptList.get(k);
									float f = 0.0f;
									for (int i = 0; i < tempMemberList.size(); i++) {
										JXKH_HULWMember m = tempMemberList.get(i);
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
								List<JXKH_HYLWDept> tempDeptList = jxkhHylwService.findMeetingDeptByMeetingId(meeting);
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
			final JXKH_HYLWDept dept = (JXKH_HYLWDept) arg1;
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
		for (int i = 0; i < hylwMemberList.size(); i++) {
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
		for (int i = 0; i < hylwMemberList.size(); i++) {
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
			JXKH_HULWMember member = (JXKH_HULWMember) item.getValue();
			member.setPer(s);
			member.setScore(score);
			jxkhMeetingService.update(member);
		}
		List<JXKH_HULWMember> tempMemberList = jxkhHylwService.findAwardMemberByAwardId(meeting);
		personScore.setModel(new ListModelList(tempMemberList));
		/*for (int k = 0; k < hylwDeptList.size(); k++) {
			JXKH_HYLWDept d = hylwDeptList.get(k);
			float f = 0.0f;
			for (int i = 0; i < tempMemberList.size(); i++) {
				JXKH_HULWMember m = tempMemberList.get(i);
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
		}*/
		try {
			Messagebox.show("绩分信息保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		List<JXKH_HYLWDept> tempDeptList = jxkhHylwService.findMeetingDeptByMeetingId(meeting);
		departmentScore.setModel(new ListModelList(tempDeptList));
	}

	public class ComparatorsTemp implements Comparator<Object> {
		@Override
		public int compare(Object arg0, Object arg1) {
			String[] b1 = (String[]) arg0;
			String[] b2 = (String[]) arg1;
			int flag;
			if (b1[0].equals("ISTP收录")) {
				flag = 0;
			} else if (b1[0].equals("SCI收录")) {
				flag = 1;
			} else {
				if (b2[0].equals("ISTP收录")) {
					flag = 1;
				} else if (b2[0].equals("SCI收录")) {
					flag = 0;
				} else {
					flag = 0;
				}
			}
			return flag;
		}

	}

	// 绩分信息的关闭按钮
	public void onClick$closeScore() {
		this.onClose();
	}
}
