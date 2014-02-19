package org.iti.jxkh.deptbusiness.meeting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import jxl.write.WriteException;
import org.iti.gh.common.util.ExportExcel;
import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxkh.entity.JXKH_MEETING;
import org.iti.jxkh.entity.JXKH_MeetingDept;
import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.service.JXKHMeetingService;
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

public class MeetingWindow extends Window implements AfterCompose {

	/**
	 * @author CuiXiaoxin
	 */
	private static final long serialVersionUID = 3678502505243772953L;

	private Listbox meetingListbox;
	private Paging awardPaging;
	private Groupbox cxtj;
	private Boolean isQuery = false;
	private YearListbox year;
	private Textbox name;
	private Listbox auditState, rank;
	private String nameSearch, yearSearch;
	private Integer auditStateSearch;
	private JXKHMeetingService jxkhMeetingService;
	private WkTUser user;
	private List<JXKH_MEETING> meetingList = new ArrayList<JXKH_MEETING>();
	private Long indicatorId;
	public static final Integer qikan = 71;

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		year.initYearListbox("");
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");// 获取当前登录用户
		meetingListbox.setItemRenderer(new MeetingRenderer());
		initWindow();
		String[] s = { "","填写中", "待审核", "部门审核中", "部门通过", "部门不通过","业务办暂缓通过", "业务办通过", "业务办不通过",
		"归档" };
		List<String> lwjbList = new ArrayList<String>();
		for (int i = 0; i < 8; i++) {
			lwjbList.add(s[i]);
		}
		auditState.setModel(new ListModelList(lwjbList));
		auditState.setSelectedIndex(0);
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

	/**
	 * <li>功能描述：會議列表渲染器
	 */
	public class MeetingRenderer implements ListitemRenderer {

		@Override
		public void render(Listitem item, Object data) throws Exception {

			if (data == null)
				return;
			final JXKH_MEETING meeting = (JXKH_MEETING) data;
			item.setValue(meeting);
			Listcell c0 = new Listcell();
			Listcell c1 = new Listcell(item.getIndex() + 1 + "");
			Listcell c2 = new Listcell(meeting.getMtName().length()>10?meeting.getMtName().substring(0, 10)+"...":meeting.getMtName());
			c2.setTooltiptext(meeting.getMtName());
			c2.setStyle("color:blue");
			c2.addEventListener(Events.ON_CLICK, new EditListener());
			//会议级别
			Listcell c3 = new Listcell("");
			if (meeting.getMtDegree() == null) {
				c3.setLabel("");
			} else {
				c3.setLabel(meeting.getMtDegree().getKbName());
			}
			//积分年度
			Listcell c4 = new Listcell(meeting.getjxYear());
			//该项得分
			Listcell c5 = new Listcell(meeting.getScore() == null ? ""
					: meeting.getScore().toString());
			//填写人
			Listcell c6 = new Listcell();
			c6.setLabel(meeting.getMtWriter());
			//审核状态
			Listcell c7 = new Listcell();
			c7.setTooltiptext("点击查看审核结果");
			if (meeting.getMtState() == null) {
				c7.setLabel("待审核");
				c7.setStyle("color:red");
			} else {
				switch (meeting.getMtState()) {
				case 0:
					c7.setLabel("待审核");
					c7.setStyle("color:red");
					break;
				case 1:
					c7.setLabel("部门通过");
					c7.setStyle("color:red");
					break;
				case 2:
					c7.setLabel("部门审核中");
					c7.setStyle("color:red");
					break;
				case 3:
					c7.setLabel("部门不通过");
					c7.setStyle("color:red");
					break;
				case 4:
					c7.setLabel("业务办通过");
					c7.setStyle("color:red");
					break;
				case 5:
					c7.setLabel("业务办不通过");
					c7.setStyle("color:red");
					break;
				case 6:
					c7.setLabel("归档");
					c7.setStyle("color:red");
					break;
				case JXKH_MEETING.WRITING:
					c7.setLabel("填写中");
					c7.setStyle("color:red");
					break;
				case JXKH_MEETING.BUSINESS_TEMP_PASS:
					c7.setLabel("业务办暂缓通过");
					c7.setStyle("color:red");
					break;
				}
			}
			// 弹出审核意见事件
			c7.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event event) throws Exception {

					org.iti.jxkh.business.meeting.AdviceWin w = (org.iti.jxkh.business.meeting.AdviceWin) Executions
							.createComponents(
									"/admin/personal/businessdata/meeting/advice.zul",
									null, null);
					try {
						w.setMeeting(meeting);
						w.initWindow();
						w.doModal();
					} catch (SuspendNotAllowedException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
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
		}
	}

	/**
	 * 监听学术会议名称列的单击事件
	 */
	public class EditListener implements EventListener {
		@Override
		public void onEvent(Event event) throws Exception {
			Listitem item = (Listitem) event.getTarget().getParent();
			JXKH_MEETING meeting = (JXKH_MEETING) item.getValue();
			AddMeetingWindow w = (AddMeetingWindow) Executions
					.createComponents(
							"/admin/personal/deptbusinessdata/meeting/addmeeting.zul",
							null, null);
			try {
				w.setMeeting(meeting);
				w.initWindow();
				w.doModal();

			} catch (SuspendNotAllowedException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (isQuery) {
				onClick$query();
			} else {
				initWindow();
			}
		}
	}

	public void initWindow() {
		awardPaging.addEventListener("onPaging", new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				meetingList = jxkhMeetingService
						.findMeetingByConditionAndPaging("", null, null, null,
								user.getDept().getKdNumber(),
								awardPaging.getActivePage(),
								awardPaging.getPageSize());
				meetingListbox.setModel(new ListModelList(meetingList));
			}
		});

		int totalNum = jxkhMeetingService.findMeetingByCondition("", null,
				null, null, user.getDept().getKdNumber());
		awardPaging.setTotalSize(totalNum);
		meetingList = jxkhMeetingService.findMeetingByConditionAndPaging("",
				null, null, null, user.getDept().getKdNumber(),
				awardPaging.getActivePage(), awardPaging.getPageSize());
		meetingListbox.setModel(new ListModelList(meetingList));
	}

	// 單擊添加按鈕后响应的事件
	public void onClick$addMeetartical() {
		AddMeetingWindow win = (AddMeetingWindow) Executions.createComponents(
				"/admin/personal/deptbusinessdata/meeting/addmeeting.zul",
				null, null);
		win.initWindow();
		// win.doHighlighted();
		try {
			win.doModal();
		} catch (SuspendNotAllowedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		win.addEventListener(Events.ON_CHANGE, new EventListener() {

			public void onEvent(Event arg0) throws Exception {
				initWindow();
			}
		});
	}

	// 單擊刪除按鈕后响应的事件
	public void onClick$del() throws InterruptedException {

		if (meetingListbox.getSelectedItems() == null
				|| meetingListbox.getSelectedItems().size() <= 0) {
			try {
				Messagebox.show("请选择要删除的学术会议！", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		Messagebox.show("确定删除吗?", "确定", Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						if (evt.getName().equals("onOK")) {
							Object[] obj = meetingListbox.getSelectedItems()
									.toArray();
							JXKH_MEETING lw = new JXKH_MEETING();
							for (int i = 0; i < obj.length; i++) {
								Listitem item = (Listitem) obj[i];
								lw = (JXKH_MEETING) item.getValue();
								if (lw.getMtState() == null) {
								} else if (lw.getMtState() == JXKH_MEETING.First_Dept_Pass
										|| lw.getMtState() == JXKH_MEETING.DEPT_PASS
										|| lw.getMtState() == JXKH_MEETING.BUSINESS_PASS || lw.getMtState().shortValue() == JXKH_MEETING.BUSINESS_TEMP_PASS
										|| lw.getMtState() == JXKH_MEETING.SAVE_RECORD) {
									try {
										Messagebox.show(
												"部门或业务办审核通过,或者已经归档的会议论文不能删除！",
												"提示", Messagebox.OK,
												Messagebox.EXCLAMATION);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									return;
								}
								jxkhMeetingService.delete(lw);
							}
							try {
								Messagebox.show("学术会议删除成功！", "提示",
										Messagebox.OK, Messagebox.INFORMATION);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							initWindow();
						}
					}
				});
	}

	public void onClick$searchcbutton() {
		if (cxtj.isVisible()) {
			cxtj.setVisible(false);
		} else {
			cxtj.setVisible(true);
		}
	}

	public void onClick$query() {
		isQuery = true;
		nameSearch = name.getValue();
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
		}else if (auditState.getSelectedItem().getValue().equals("填写中")) {
			auditStateSearch = Integer.valueOf(JXKH_MEETING.WRITING);
		}else if (auditState.getSelectedItem().getValue().equals("业务办暂缓通过")) {
			auditStateSearch = Integer.valueOf(JXKH_MEETING.BUSINESS_TEMP_PASS);
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

		awardPaging.addEventListener("onPaging", new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				meetingList = jxkhMeetingService
						.findMeetingByConditionAndPaging(nameSearch,
								auditStateSearch, indicatorId, yearSearch, user
										.getDept().getKdNumber(), awardPaging
										.getActivePage(), awardPaging
										.getPageSize());
				meetingListbox.setModel(new ListModelList(meetingList));
			}
		});
		int totalNum = jxkhMeetingService.findMeetingByCondition(nameSearch,
				auditStateSearch, indicatorId, yearSearch, user.getDept()
						.getKdNumber());
		awardPaging.setTotalSize(totalNum);
		meetingList = jxkhMeetingService.findMeetingByConditionAndPaging(
				nameSearch, auditStateSearch, indicatorId, yearSearch, user
						.getDept().getKdNumber(), awardPaging.getActivePage(),
				awardPaging.getPageSize());
		meetingListbox.setModel(new ListModelList(meetingList));
	}

	public void onClick$reset() {
		name.setValue("");
		auditState.getItemAtIndex(0).setSelected(true);
		rank.getItemAtIndex(0).setSelected(true);
		year.getItemAtIndex(0).setSelected(true);
	}

	// 导出
	public void onClick$exportExcel() {
		if (meetingListbox.getSelectedCount() == 0) {
			try {
				Messagebox.show("请选择要导出的数据", "提示", Messagebox.OK,
						Messagebox.EXCLAMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		} else {
			int i = 0;
			ArrayList<JXKH_MEETING> expertlist = new ArrayList<JXKH_MEETING>();
			@SuppressWarnings("unchecked")
			Set<Listitem> sel = meetingListbox.getSelectedItems();
			Iterator<Listitem> it = sel.iterator();
			while (it.hasNext()) {
				Listitem item = (Listitem) it.next();
				JXKH_MEETING p = (JXKH_MEETING) item.getValue();
				expertlist.add(i, p);
				i++;
			}
			Date now = new Date();
			String fileName = ConvertUtil.convertDateString(now)
					+ "XueShuHuiYiXinXi" + ".xls";
			String Title = "学术会议";
			List<String> titlelist = new ArrayList<String>();
			titlelist.add("序号");
			titlelist.add("学术会议名称");
			titlelist.add("院内部门");
			titlelist.add("合作单位");
			titlelist.add("会议级别");
			titlelist.add("举办类型");
			titlelist.add("主办单位");
			titlelist.add("承办单位");
			titlelist.add("协办单位");
			titlelist.add("会议时间");
			titlelist.add("会议地点");
			titlelist.add("会议主题");
			titlelist.add("会议规模");
			titlelist.add("信息填写人");
			String c[][] = new String[expertlist.size()][titlelist.size()];
			// 从SQL中读数据
			for (int j = 0; j < expertlist.size(); j++) {
				JXKH_MEETING meeting = (JXKH_MEETING) expertlist.get(j);
				c[j][0] = j + 1 + "";
				c[j][1] = meeting.getMtName();
				String d = "";
				List<JXKH_MeetingDept> meetDeptList = jxkhMeetingService
						.findMeetingDeptByMeetingId(meeting);
				for (int k = 0; k < meetDeptList.size(); k++) {
					JXKH_MeetingDept dept = (JXKH_MeetingDept) meetDeptList
							.get(k);
					d += dept.getName() + "、";
				}
				if (d != "" && d != null)
					d = d.substring(0, d.length() - 1);
				c[j][2] = d;
				c[j][3] = meeting.getMtCoDep();
				c[j][4] = meeting.getMtDegree().getKbName();
				c[j][5] = meeting.getMtType().getKbName();
				c[j][6] = meeting.getMtZDep();
				c[j][7] = meeting.getMtCDep();
				c[j][8] = meeting.getMtXDep();
				c[j][9] = meeting.getMtTime();
				c[j][10] = meeting.getMtAddress();
				c[j][11] = meeting.getMtTheme();
				c[j][12] = meeting.getMtScope();
				c[j][13] = meeting.getMtWriter();
			}
			ExportExcel ee = new ExportExcel();
			try {
				ee.exportExcel(fileName, Title, titlelist, expertlist.size(), c);
			} catch (WriteException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
