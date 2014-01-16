package org.iti.jxkh.busiAudit.meeting;

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
import org.iti.jxkh.entity.JXKH_MeetingDept;
import org.iti.jxkh.entity.JXKH_MeetingFile;
import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.service.JXKHMeetingService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
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
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.iti.common.util.ConvertUtil;
import com.uniwin.framework.entity.WkTDept;

public class BusiAuditWindow extends Window implements AfterCompose {
	/**
	 * @author CXX
	 */
	private static final long serialVersionUID = -126711047575812092L;
	private Listbox meetingListbox;
	private JXKHMeetingService jxkhMeetingService;
	private List<JXKH_MEETING> meetingList = new ArrayList<JXKH_MEETING>();
	private Set<JXKH_MeetingFile> filesList;
	private Paging awardPaging;
	private Groupbox cxtj;
	private Boolean isQuery = false;
	private Textbox name;
	private Listbox auditState, rank, dept;
	private String nameSearch, depName;
	private Integer auditStateSearch;
	private Long indicatorId;
	public static final Integer qikan = 71;

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		meetingListbox.setItemRenderer(new MeetingRenderer());
		initWindow();

		String[] s = { "", "待审核", "部门审核中","部门通过",  "部门不通过","业务办暂缓通过", "业务办通过", "业务办不通过",
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

		dept.setItemRenderer(new deptRenderer());
		List<WkTDept> depList = new ArrayList<WkTDept>();
		depList.add(new WkTDept());
		if (jxkhMeetingService.findAllDep() != null)
			depList.addAll(jxkhMeetingService.findAllDep());
		dept.setModel(new ListModelList(depList));
		dept.setSelectedIndex(0);
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

	public void initWindow() {
		
		awardPaging.addEventListener("onPaging", new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				meetingList = jxkhMeetingService.findMeetingByAudit(
						awardPaging.getActivePage(), awardPaging.getPageSize());
				meetingListbox.setModel(new ListModelList(meetingList));
			}
		});
		int totalNum = jxkhMeetingService.findMeetingByAudit();
		awardPaging.setTotalSize(totalNum);
		meetingList = jxkhMeetingService.findMeetingByAudit(
				awardPaging.getActivePage(), awardPaging.getPageSize());
		meetingListbox.setModel(new ListModelList(meetingList));

	}

	// 会议列表渲染器
	public class MeetingRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {

			if (data == null)
				return;
			final JXKH_MEETING meeting = (JXKH_MEETING) data;
			item.setValue(meeting);
			Listcell c0 = new Listcell();
			Listcell c1 = new Listcell(item.getIndex() + 1 + "");
			Listcell c2 = new Listcell(meeting.getMtName().length() <= 12?
					meeting.getMtName():meeting.getMtName().substring(0,12) + "...");
			c2.setTooltiptext("点击查看学术会议信息");
			c2.setStyle("color:blue");
			c2.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event event) throws Exception {
					Listitem item = (Listitem) event.getTarget().getParent();
					JXKH_MEETING meeting = (JXKH_MEETING) item.getValue();
					MeetingDetailWindow w = (MeetingDetailWindow) Executions
							.createComponents(
									"/admin/jxkh/busiAudit/meeting/meetingDetail.zul",
									null, null);
					try {
						w.setMeeting(meeting);
						w.initWindow();

						w.addEventListener(Events.ON_CHANGE,
								new EventListener() {

									@Override
									public void onEvent(Event arg0)
											throws Exception {
										initWindow();
									}
								});
						w.doModal();

					} catch (SuspendNotAllowedException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}

			});
			Listcell c3 = new Listcell("");
			if (meeting.getMtDegree() == null) {
				c3.setLabel("");
			} else {
				c3.setLabel(meeting.getMtDegree().getKbName());
			}
			Listcell c4 = new Listcell(meeting.getjxYear());
			Listcell c5 = new Listcell();
			c5.setTooltiptext("附件");
			Toolbarbutton downlowd = new Toolbarbutton();
			downlowd.setImage("/css/default/images/button/down.gif");
			downlowd.setParent(c5);
			downlowd.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					DownloadWindow win = (DownloadWindow) Executions
							.createComponents(
									"/admin/jxkh/busiAudit/award/download.zul",
									null, null);

					filesList = jxkhMeetingService
							.findMeetingFilesByMeetingId(meeting);
					// win.setFiles(meeting.getFiles());
					win.setFiles(filesList);
					win.setFlag("M");
					win.initWindow();
					win.doModal();
				}
			});
			Listcell c6 = new Listcell(meeting.getScore() == null ? ""
					: meeting.getScore().toString());
			Listcell c7 = new Listcell();
			c7.setTooltiptext("点击填写审核意见");
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
				case 7:
					c7.setLabel("业务办暂缓通过");
					c7.setStyle("color:red");
					break;
				}
			}
			// 弹出审核意见事件
			c7.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event event) throws Exception {
					if (meeting.getMtState() == null
							|| meeting.getMtState() == 0
							|| meeting.getMtState() == 2
							|| meeting.getMtState() == 3) {
						try {
							Messagebox.show("部门审核通过后，业务办才可以审核！", "提示",
									Messagebox.OK, Messagebox.INFORMATION);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					AdviceWindow w = (AdviceWindow) Executions
							.createComponents(
									"/admin/jxkh/busiAudit/meeting/advice.zul",
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
					initWindow();
				}
			});
			Listcell c8 = new Listcell();
			Image addrecode = new Image(
					"/css/default/images/button/actEdit.gif");
			
			addrecode.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					if (meeting.getMtState() == null
							|| meeting.getMtState() == 0
							|| meeting.getMtState() == 1
							|| meeting.getMtState() == 2
							|| meeting.getMtState() == 3
							|| meeting.getMtState() == 5
							|| meeting.getMtState() == 7) {
						try {
							Messagebox.show("业务办审核通过后才可以填写档案号！", "提示",
									Messagebox.OK, Messagebox.INFORMATION);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						return;
					}
					RecordCodeWin win = (RecordCodeWin) Executions
							.createComponents(
									"/admin/jxkh/busiAudit/meeting/recordcode.zul",
									null, null);
					win.setAward(meeting);
					win.initShow();
					win.doModal();
					initWindow();
				}
			});
			addrecode.setParent(c8);
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

	public void onClick$passAll() {
		if (meetingListbox.getSelectedItems() == null
				|| meetingListbox.getSelectedItems().size() <= 0) {
			try {
				Messagebox.show("请选择要审核的学术会议！", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		Iterator<Listitem> items = meetingListbox.getSelectedItems().iterator();
		List<JXKH_MEETING> meetingList = new ArrayList<JXKH_MEETING>();
		JXKH_MEETING meeting = new JXKH_MEETING();
		while (items.hasNext()) {
			meeting = (JXKH_MEETING) items.next().getValue();
			if (meeting.getMtState() == 1 || meeting.getMtState() == 4
					|| meeting.getMtState() == 5) {
				meetingList.add(meeting);
			}
		}
		BatchAuditWindow win = (BatchAuditWindow) Executions.createComponents(
				"/admin/jxkh/busiAudit/meeting/batchAudit.zul", null, null);
		try {
			win.setMeetingList(meetingList);
			win.doModal();

		} catch (SuspendNotAllowedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		initWindow();
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
		}else if (auditState.getSelectedItem().getValue().equals("业务办暂缓通过")) {
			auditStateSearch = Integer.valueOf(JXKH_MEETING.BUSINESS_TEMP_PASS);
		}
		indicatorId = null;
		if (rank.getSelectedIndex() != 0) {
			Jxkh_BusinessIndicator qkType = (Jxkh_BusinessIndicator) rank
					.getSelectedItem().getValue();
			indicatorId = qkType.getKbId();
		}
		depName = null;
		if (dept.getSelectedIndex() != 0) {
			WkTDept d = (WkTDept) dept.getSelectedItem().getValue();
			depName = d.getKdName();
		}

		awardPaging.addEventListener("onPaging", new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				meetingList = jxkhMeetingService
						.findMeetingByBusiAduitConditionAndPages(nameSearch,
								auditStateSearch, indicatorId, depName,
								awardPaging.getActivePage(),
								awardPaging.getPageSize());
				meetingListbox.setModel(new ListModelList(meetingList));
			}
		});

		int totalNum = jxkhMeetingService.findMeetingByBusiAduitConditions(
				nameSearch, auditStateSearch, indicatorId, depName);
		awardPaging.setTotalSize(totalNum);
		meetingList = jxkhMeetingService
				.findMeetingByBusiAduitConditionAndPages(nameSearch,
						auditStateSearch, indicatorId, depName,
						awardPaging.getActivePage(), awardPaging.getPageSize());
		meetingListbox.setModel(new ListModelList(meetingList));
	}

	public void onClick$reset() {
		name.setValue("");
		auditState.getItemAtIndex(0).setSelected(true);
		rank.getItemAtIndex(0).setSelected(true);
		dept.getItemAtIndex(0).setSelected(true);
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
