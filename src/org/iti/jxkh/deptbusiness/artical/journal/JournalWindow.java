package org.iti.jxkh.deptbusiness.artical.journal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import jxl.write.WriteException;

import org.iti.gh.common.util.ExportExcel;
import org.iti.jxkh.business.artical.journal.AdviceWin;
import org.iti.jxkh.business.meeting.DownloadWindow;
import org.iti.jxkh.entity.JXKH_MEETING;
import org.iti.jxkh.entity.JXKH_QKLW;
import org.iti.jxkh.entity.JXKH_QKLWDept;
import org.iti.jxkh.entity.JXKH_QKLWFile;
import org.iti.jxkh.entity.JXKH_QKLWMember;
import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.service.JXKHMeetingService;
import org.iti.jxkh.service.JxkhQklwService;
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
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.iti.common.util.ConvertUtil;
import com.uniwin.framework.entity.WkTUser;

public class JournalWindow extends Window implements AfterCompose {

	/**
	 * @author CuiXiaoxin
	 */

	private static final long serialVersionUID = -1271025754692227364L;

	private Listbox journalListbox;
	private Paging meetingPaging;
	private Groupbox cxtj;
	private Textbox name;
	private Listbox auditState, rank;
	private Boolean isQuery = false;
	private String nameSearch;
	private Integer auditStateSearch;
	private JxkhQklwService jxkhQklwService;
	private JXKHMeetingService jxkhMeetingService;
	private WkTUser user;
	private List<JXKH_QKLW> qklwList = new ArrayList<JXKH_QKLW>();
	private List<JXKH_QKLW> meetingList = new ArrayList<JXKH_QKLW>();
	private Set<JXKH_QKLWFile> filesList;
	public static final Integer qikan = 12;
	private Long indicatorId;

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");// 获取当前登录用户
		String[] s = { "","填写中", "待审核", "部门审核中", "部门通过", "部门不通过","业务办暂缓通过", "业务办通过", "业务办不通过",
				"归档" };
		List<String> lwjbList = new ArrayList<String>();
		for (int i = 0; i < 8; i++) {
			lwjbList.add(s[i]);
		}
		auditState.setModel(new ListModelList(lwjbList));
		auditState.setSelectedIndex(0);
		initWindow();
		journalListbox.setItemRenderer(new MeetingRenderer());
		rank.setItemRenderer(new qkTypeRenderer());
		List<Jxkh_BusinessIndicator> holdList = new ArrayList<Jxkh_BusinessIndicator>();
		holdList.add(new Jxkh_BusinessIndicator());
		if (jxkhMeetingService.findRank(qikan) != null) {
			holdList.addAll(jxkhMeetingService.findRank(qikan));
		}
		rank.setModel(new ListModelList(holdList));
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
		}
	}

	/**
	 * <li>功能描述：会议论文列表渲染器
	 */
	public class MeetingRenderer implements ListitemRenderer {

		@Override
		public void render(Listitem item, Object data) throws Exception {

			if (data == null)
				return;
			final JXKH_QKLW meeting = (JXKH_QKLW) data;
			item.setValue(meeting);
			Listcell c0 = new Listcell();
			Listcell c1 = new Listcell(item.getIndex() + 1 + "");
			Listcell c2 = new Listcell(meeting.getLwName().length()>10?meeting.getLwName().substring(0, 10)+"...":meeting.getLwName());
			c2.setTooltiptext(meeting.getLwName());
			c2.setStyle("color:blue");
			c2.addEventListener(Events.ON_CLICK, new EditListener());
			Listcell c3 = new Listcell();
			if (meeting.getQkGrade() == null)
				c3.setLabel("");
			else
				c3.setLabel(meeting.getQkGrade().getKbName());

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
									"/admin/personal/businessdata/meeting/download.zul",
									null, null);
					filesList = jxkhQklwService
							.findMeetingFilesByMeetingId(meeting);
					win.setFiles(filesList);
					win.setFlag("QKLW");
					win.initWindow();
					win.doModal();
				}
			});
			Listcell c6 = new Listcell(meeting.getScore() == null ? ""
					: meeting.getScore().toString());
			Listcell c7 = new Listcell();
			c7.setTooltiptext("点击查看审核结果");
			if (meeting.getLwState() == null) {
				c7.setLabel("待审核");
				c7.setStyle("color:red");
			} else {
				switch (meeting.getLwState()) {
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

					AdviceWin w = (AdviceWin) Executions
							.createComponents(
									"/admin/personal/businessdata/artical/journal/advice.zul",
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
			JXKH_QKLW meeting = (JXKH_QKLW) item.getValue();
			AddJournalWindow w = (AddJournalWindow) Executions
					.createComponents(
							"/admin/personal/deptbusinessdata/artical/journal/addJournal.zul",
							null, null);
			try {
				w.setMeeting(meeting);
				w.initWindow();
				w.addEventListener(Events.ON_CHANGE, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						if (isQuery) {
							onClick$query();
						} else {
							initWindow();
						}
					}
				});
				w.doModal();
			} catch (SuspendNotAllowedException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void initWindow() {
		meetingPaging.addEventListener("onPaging", new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				qklwList = jxkhQklwService.findMeetingByConditionAndPage("",
						null, null, user.getDept().getKdNumber(),
						meetingPaging.getActivePage(),
						meetingPaging.getPageSize());
				journalListbox.setModel(new ListModelList(qklwList));
			}
		});
		int totalNum = jxkhQklwService.findMeetingByCondition("", null, null,
				user.getDept().getKdNumber());
		meetingPaging.setTotalSize(totalNum);
		qklwList = jxkhQklwService.findMeetingByConditionAndPage("", null,
				null, user.getDept().getKdNumber(),
				meetingPaging.getActivePage(), meetingPaging.getPageSize());
		journalListbox.setModel(new ListModelList(qklwList));
	}

	// 單擊添加按鈕后响应的事件
	public void onClick$addJournal() {
		AddJournalWindow win = (AddJournalWindow) Executions
				.createComponents(
						"/admin/personal/deptbusinessdata/artical/journal/addJournal.zul",
						null, null);
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

		if (journalListbox.getSelectedItems() == null
				|| journalListbox.getSelectedItems().size() <= 0) {
			try {
				Messagebox.show("请选择要删除的期刊论文！", "提示", Messagebox.OK,
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
							Iterator<?> it = journalListbox
									.getSelectedItems().iterator();
							JXKH_QKLW lw = new JXKH_QKLW();
							while (it.hasNext()) {
								Listitem item = (Listitem) it.next();
								lw = (JXKH_QKLW) item.getValue();
								if (lw.getLwState() == null) {
								} else if (lw.getLwState() == JXKH_QKLW.First_Dept_Pass
										|| lw.getLwState() == JXKH_QKLW.DEPT_PASS
										|| lw.getLwState() == JXKH_QKLW.BUSINESS_PASS || lw.getLwState().shortValue() == JXKH_MEETING.BUSINESS_TEMP_PASS
										|| lw.getLwState() == JXKH_QKLW.SAVE_RECORD) {
									try {
										Messagebox.show(
												"部门或业务办审核通过,或者已经归档的期刊论文不能删除！",
												"提示", Messagebox.OK,
												Messagebox.EXCLAMATION);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									return;
								}
								jxkhQklwService.delete(lw);
							}
							try {
								Messagebox.show("期刊论文删除成功！", "提示",
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
		nameSearch = null;
		auditStateSearch = null;
		indicatorId = null;
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
		if (rank.getSelectedIndex() != 0) {
			Jxkh_BusinessIndicator qkType = (Jxkh_BusinessIndicator) rank
					.getSelectedItem().getValue();
			indicatorId = qkType.getKbId();
		}

		meetingPaging.addEventListener("onPaging", new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				meetingList = jxkhQklwService.findMeetingByConditionAndPage(
						nameSearch, auditStateSearch, indicatorId, user
								.getDept().getKdNumber(), meetingPaging
								.getActivePage(), meetingPaging.getPageSize());
				journalListbox.setModel(new ListModelList(meetingList));
			}
		});
		int totalNum = jxkhQklwService.findMeetingByCondition(nameSearch,
				auditStateSearch, indicatorId, user.getDept().getKdNumber());
		meetingPaging.setTotalSize(totalNum);
		meetingList = jxkhQklwService.findMeetingByConditionAndPage(nameSearch,
				auditStateSearch, indicatorId, user.getDept().getKdNumber(),
				meetingPaging.getActivePage(), meetingPaging.getPageSize());
		journalListbox.setModel(new ListModelList(meetingList));
	}

	public void onClick$reset() {
		name.setValue("");
		auditState.getItemAtIndex(0).setSelected(true);
		rank.getItemAtIndex(0).setSelected(true);
	}

	// 导出
	public void onClick$exportExcel() {
		if (journalListbox.getSelectedCount() == 0) {
			try {
				Messagebox.show("请选择要导出的数据", "提示", Messagebox.OK,
						Messagebox.EXCLAMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		} else {
			int i = 0;
			ArrayList<JXKH_QKLW> expertlist = new ArrayList<JXKH_QKLW>();
			@SuppressWarnings("unchecked")
			Set<Listitem> sel = journalListbox.getSelectedItems();
			Iterator<Listitem> it = sel.iterator();
			while (it.hasNext()) {
				Listitem item = (Listitem) it.next();
				JXKH_QKLW p = (JXKH_QKLW) item.getValue();
				expertlist.add(i, p);
				i++;
			}
			Date now = new Date();
			String fileName = ConvertUtil.convertDateString(now)
					+ "QiKanLunWenXinXi" + ".xls";
			String Title = "期刊论文";
			List<String> titlelist = new ArrayList<String>();
			titlelist.add("序号");
			titlelist.add("期刊论文名称");
			titlelist.add("全部作者");
			titlelist.add("院内部门");
			titlelist.add("合作单位");
			titlelist.add("期刊级别");
			titlelist.add("论文级别");
			titlelist.add("发表时间");
			titlelist.add("发表刊物名称");
			titlelist.add("发表刊物期次");
			titlelist.add("起止页");
			titlelist.add("通讯作者");
			titlelist.add("信息填写人");
			String c[][] = new String[expertlist.size()][titlelist.size()];
			// 从SQL中读数据
			for (int j = 0; j < expertlist.size(); j++) {
				JXKH_QKLW meeting = (JXKH_QKLW) expertlist.get(j);
				c[j][0] = j + 1 + "";
				c[j][1] = meeting.getLwName();
				// 全部作者
				String memberName = "";
				List<JXKH_QKLWMember> qklwMemberList = jxkhQklwService
						.findAwardMemberByAwardId(meeting);
				for (int t = 0; t < qklwMemberList.size(); t++) {
					JXKH_QKLWMember mem = (JXKH_QKLWMember) qklwMemberList
							.get(t);
					memberName += mem.getName() + "、";
				}
				if (memberName != "" && memberName != null)
					memberName = memberName.substring(0,
							memberName.length() - 1);
				c[j][2] = memberName;
				// 部门
				String d = "";
				List<JXKH_QKLWDept> meetDeptList = jxkhQklwService
						.findMeetingDeptByMeetingId(meeting);
				for (int k = 0; k < meetDeptList.size(); k++) {
					JXKH_QKLWDept dept = (JXKH_QKLWDept) meetDeptList.get(k);
					d += dept.getName() + "、";
				}
				if (d != "" && d != null)
					d = d.substring(0, d.length() - 1);
				c[j][3] = d;
				c[j][4] = meeting.getLwCoDep();
				if (meeting.getQkGrade() == null) {
					c[j][5] = "";
				} else {
					c[j][5] = meeting.getQkGrade().getKbName();
				}
				c[j][6] = meeting.getLwRank();
				c[j][7] = meeting.getLwDate();
				c[j][8] = meeting.getKwName();
				c[j][9] = meeting.getLwQC();
				c[j][10] = meeting.getLwPages();
				c[j][11] = meeting.getLwAuthor();
				c[j][12] = meeting.getLwWriter();
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
