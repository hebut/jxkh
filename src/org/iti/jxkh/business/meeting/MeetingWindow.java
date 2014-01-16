package org.iti.jxkh.business.meeting;

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
import org.iti.jxkh.entity.JXKH_MeetingFile;
import org.iti.jxkh.entity.JXKH_MeetingMember;
import org.iti.jxkh.service.JXKHMeetingService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
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
import com.uniwin.framework.entity.WkTUser;

public class MeetingWindow extends Window implements AfterCompose {

	/**
	 * @author CuiXiaoxin
	 */
	private static final long serialVersionUID = 3678502505243772953L;

	private Listbox meetingListbox;
	private Paging meetingPaging;
	private JXKHMeetingService jxkhMeetingService;
	private WkTUser user;
	private List<JXKH_MEETING> meetingList = new ArrayList<JXKH_MEETING>();
	private Set<JXKH_MeetingFile> filesList;
	private Textbox name;
	private YearListbox year;
	private String nameSearch, yearSearch;
	private Listbox auditState;
	private Integer auditStateSearch;

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");// 获取当前登录用户
		meetingListbox.setItemRenderer(new MeetingRenderer());
		year.initYearListbox("");
		initWindow();
		String[] s = { "-请选择-", "填写中","待审核","部门审核中", "部门通过", "部门不通过","业务办暂缓通过", "业务办通过", "业务办不通过",
		"归档" };
		List<String> lwjbList = new ArrayList<String>();
		for (int i = 0; i < 8; i++) {
			lwjbList.add(s[i]);
		}
		auditState.setModel(new ListModelList(lwjbList));
		auditState.setSelectedIndex(0);
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
			Listcell c2 = new Listcell(meeting.getMtName().length() <= 12?
					meeting.getMtName():meeting.getMtName().substring(0, 12) + "...");
			c2.setTooltiptext("点击查看学术会议信息");
			c2.setStyle("color:blue");
			c2.addEventListener(Events.ON_CLICK, new EditListener());
			Listcell c3 = new Listcell("");
			if (meeting.getMtDegree() == null) {
				c3.setLabel("");
			} else {
				c3.setLabel(meeting.getMtDegree().getKbName());
			}
			Listcell c4 = new Listcell(meeting.getjxYear());
			Listcell c5 = new Listcell();
			c5.setTooltiptext("下载文档");
			Toolbarbutton downlowd = new Toolbarbutton();
			downlowd.setImage("/css/default/images/button/down.gif");
			downlowd.setParent(c5);
			downlowd.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					DownloadWindow win = (DownloadWindow) Executions
							.createComponents(
									"/admin/personal/businessdata/meeting/download.zul",
									null, null);

					filesList = jxkhMeetingService
							.findMeetingFilesByMeetingId(meeting);
					win.setFiles(filesList);
					win.setFlag("M");
					win.initWindow();
					win.doModal();
				}
			});
			Listcell c6 = new Listcell(meeting.getScore() == null ? ""
					: meeting.getScore().toString());

			Listcell c7 = new Listcell("");
			List<JXKH_MeetingMember> mlist = jxkhMeetingService
					.findMeetingMemberByMeetingId(meeting);
			for (int j = 0; j < mlist.size(); j++) {
				JXKH_MeetingMember m = mlist.get(j);
				if (user.getKuName().equals(m.getName())) {
					if (m.getScore() != null && !m.getScore().equals("")) {
						c7.setLabel(m.getScore() + "");
					}
				}
			}

			Listcell c8 = new Listcell();
			c8.setTooltiptext("点击查看审核结果");
			if (meeting.getMtState() == null) {
				c8.setLabel("待审核");
				c8.setStyle("color:red");
			} else {
				switch (meeting.getMtState()) {
				case 0:
					c8.setLabel("待审核");
					c8.setStyle("color:red");
					break;
				case 1:
					c8.setLabel("部门通过");
					c8.setStyle("color:red");
					break;
				case 2:
					c8.setLabel("部门审核中");
					c8.setStyle("color:red");
					break;
				case 3:
					c8.setLabel("部门不通过");
					c8.setStyle("color:red");
					break;
				case 4:
					c8.setLabel("业务办通过");
					c8.setStyle("color:red");
					break;
				case 5:
					c8.setLabel("业务办不通过");
					c8.setStyle("color:red");
					break;
				case 6:
					c8.setLabel("归档");
					c8.setStyle("color:red");
					break;
				case 7:
					c8.setLabel("业务办暂缓通过");
					c8.setStyle("color:red");
					break;
				case 8:
					c8.setLabel("填写中");
					c8.setStyle("color:red");
					break;
				case 9:
					c8.setLabel("部门审核中");
					c8.setStyle("color:red");
					break;
				}
			}
			// 弹出审核意见事件
			c8.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event event) throws Exception {

					AdviceWin w = (AdviceWin) Executions.createComponents(
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
			item.appendChild(c8);
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
			if (user.getKuLid().equals(meeting.getWriterId())) {
				if (meeting.getMtState() == null || meeting.getMtState() == 0
						|| meeting.getMtState() == 3
						|| meeting.getMtState() == 5 || meeting.getMtState()== JXKH_MEETING.WRITING) {
				} else {

					Messagebox.show("部门已经审核通过或者业务办已经审核通过，您只能查看，无权再编辑 ！", "提示",
							Messagebox.OK, Messagebox.ERROR);
				}
				AddMeetingWindow w = (AddMeetingWindow) Executions
						.createComponents(
								"/admin/personal/businessdata/meeting/addmeeting.zul",
								null, null);
				w.setMeeting(meeting);
				w.initWindow();
				w.doModal();
				initWindow();
			} else {
				AddMeetingWindow w = (AddMeetingWindow) Executions
						.createComponents(
								"/admin/personal/businessdata/meeting/addmeeting.zul",
								null, null);
				try {
					w.setMeeting(meeting);
					w.setDetail("Detail");
					w.initWindow();
					w.doModal();
				} catch (SuspendNotAllowedException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void initWindow() {
		meetingPaging.addEventListener("onPaging", new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				meetingList = jxkhMeetingService.findMeetingByKuLidAndPaging(
						"", null, null, user.getKuLid(),
						meetingPaging.getActivePage(),
						meetingPaging.getPageSize());
				meetingListbox.setModel(new ListModelList(meetingList));
			}
		});
		int totalNum = jxkhMeetingService.findMeetingByKuLid("", null, null,
				user.getKuLid());
		meetingPaging.setTotalSize(totalNum);
		meetingList = jxkhMeetingService.findMeetingByKuLidAndPaging("", null,
				null, user.getKuLid(), meetingPaging.getActivePage(),
				meetingPaging.getPageSize());
		meetingListbox.setModel(new ListModelList(meetingList));
	}

	// 單擊添加按鈕后响应的事件
	public void onClick$addMeetartical() {
		AddMeetingWindow win = (AddMeetingWindow) Executions.createComponents(
				"/admin/personal/businessdata/meeting/addmeeting.zul", null,
				null);
		win.initWindow();		
		win.addEventListener(Events.ON_CHANGE, new EventListener() {

			public void onEvent(Event arg0) throws Exception {
				initWindow();
			}
		});
		try {
			win.doModal();
		} catch (SuspendNotAllowedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
							Iterator<?> it = meetingListbox
									.getSelectedItems().iterator();
							while (it.hasNext()) {
								Listitem item = (Listitem) it.next();
								JXKH_MEETING lw = (JXKH_MEETING) item.getValue();
								if (lw.getMtState() == null) {
								} else if (lw.getMtState() == JXKH_MEETING.First_Dept_Pass
										|| lw.getMtState() == JXKH_MEETING.DEPT_PASS || lw.getMtState().equals(JXKH_MEETING.BUSINESS_TEMP_PASS)
										|| lw.getMtState() == JXKH_MEETING.BUSINESS_PASS
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
								if (!user.getKuLid().equals(lw.getWriterId())) {
									try {
										Messagebox.show(
												"非本人提交的期刊论文，只能查看，不能删除！", "提示",
												Messagebox.OK,
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

	public void onClick$query() {
		nameSearch = null;
		auditStateSearch = null;
		yearSearch = null;
		nameSearch = name.getValue();
		if (auditState.getSelectedItem().getValue().equals("-请选择-")) {
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
		}else if(auditState.getSelectedItem().getValue().equals("业务办暂缓通过")) {
			auditStateSearch = 7;
		}else if(auditState.getSelectedItem().getValue().equals("填写中")) {
			auditStateSearch = 8;
		}
		if (year.getSelectedIndex() != 0 && year.getSelectedItem() != null)
			yearSearch = year.getSelectedItem().getValue() + "";

		meetingPaging.addEventListener("onPaging", new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				meetingList = jxkhMeetingService.findMeetingByKuLidAndPaging(
						nameSearch, auditStateSearch, yearSearch,
						user.getKuLid(), meetingPaging.getActivePage(),
						meetingPaging.getPageSize());
				meetingListbox.setModel(new ListModelList(meetingList));
			}
		});
		int totalNum = jxkhMeetingService.findMeetingByKuLid(nameSearch,
				auditStateSearch, yearSearch, user.getKuLid());
		meetingPaging.setTotalSize(totalNum);
		meetingList = jxkhMeetingService.findMeetingByKuLidAndPaging(
				nameSearch, auditStateSearch, yearSearch, user.getKuLid(),
				meetingPaging.getActivePage(), meetingPaging.getPageSize());
		meetingListbox.setModel(new ListModelList(meetingList));
	}

	public void onClick$reset() {
		name.setValue("");
		auditState.getItemAtIndex(0).setSelected(true);
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
