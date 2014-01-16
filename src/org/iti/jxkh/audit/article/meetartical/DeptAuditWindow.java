package org.iti.jxkh.audit.article.meetartical;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import jxl.write.WriteException;

import org.iti.gh.common.util.ExportExcel;
import org.iti.jxkh.business.meeting.DownloadWindow;
import org.iti.jxkh.deptbusiness.artical.meetArtical.AddMeetArticalWindow;
import org.iti.jxkh.entity.JXKH_HULWMember;
import org.iti.jxkh.entity.JXKH_HYLW;
import org.iti.jxkh.entity.JXKH_HYLWDept;
import org.iti.jxkh.entity.JXKH_HYLWFile;
import org.iti.jxkh.entity.JXKH_MEETING;
import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.service.JXKHMeetingService;
import org.iti.jxkh.service.JxkhHylwService;
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

public class DeptAuditWindow extends Window implements AfterCompose {

	/**
	 * @author CXX
	 */
	private static final long serialVersionUID = -7925742957619408396L;
	private Listbox meetArtListbox;
	private JxkhHylwService jxkhHylwService;
	private JXKHMeetingService jxkhMeetingService;
	private WkTUser user;
	private List<JXKH_HYLW> meetingList = new ArrayList<JXKH_HYLW>();
	private Set<JXKH_HYLWFile> filesList;
	private Paging zxPaging;
	private Groupbox cxtj;
	private Boolean isQuery = false;
	private Textbox name;
	private Listbox auditState, rank;
	private String nameSearch;
	private Integer auditStateSearch;
	private Long indicatorId;
	public static final Integer huiyi = 13;

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");// 获取当前登录用户
		meetArtListbox.setItemRenderer(new MeetingRenderer());
		initWindow();

		// String[] s = { "", "待审核", "部门通过", "部门不通过", "业务办通过", "业务办不通过", "归档" };
		String[] s = { "", "待审核", "部门审核中","部门通过",  "部门不通过" };
		List<String> lwjbList = new ArrayList<String>();
		for (int i = 0; i < 5; i++) {
			lwjbList.add(s[i]);
		}
		auditState.setModel(new ListModelList(lwjbList));
		auditState.setSelectedIndex(0);
		rank.setItemRenderer(new qkTypeRenderer());
		List<Jxkh_BusinessIndicator> holdList = new ArrayList<Jxkh_BusinessIndicator>();
		holdList.add(new Jxkh_BusinessIndicator());
		if (jxkhMeetingService.findRank(huiyi) != null) {
			holdList.addAll(jxkhMeetingService.findRank(huiyi));
		}
		rank.setModel(new ListModelList(holdList));
		rank.setSelectedIndex(0);
	}

	/** 会议级别列表渲染器 */
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

	public void initWindow() {
		zxPaging.addEventListener("onPaging", new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				meetingList = jxkhHylwService.findMeetingByKdNumberAndPagings(
						user.getDept().getKdNumber(), zxPaging.getActivePage(),
						zxPaging.getPageSize());
				meetArtListbox.setModel(new ListModelList(meetingList));
			}
		});
		int totalNum = jxkhHylwService.findMeetingByKdNumbers(user.getDept()
				.getKdNumber());
		zxPaging.setTotalSize(totalNum);
		meetingList = jxkhHylwService.findMeetingByKdNumberAndPagings(user
				.getDept().getKdNumber(), zxPaging.getActivePage(), zxPaging
				.getPageSize());
		meetArtListbox.setModel(new ListModelList(meetingList));
	}

	// 会议列表渲染器
	public class MeetingRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {

			if (data == null)
				return;
			final JXKH_HYLW meeting = (JXKH_HYLW) data;
			item.setValue(meeting);
			Listcell c0 = new Listcell();
			Listcell c1 = new Listcell(item.getIndex() + 1 + "");
			Listcell c2 = new Listcell(meeting.getLwName().length() <= 14?meeting.getLwName():meeting.getLwName().substring(0, 14) + "...");
			c2.setTooltiptext("点击查看会议论文信息");
			c2.setStyle("color:blue");
			c2.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event event) throws Exception {
					Listitem item = (Listitem) event.getTarget().getParent();
					JXKH_HYLW meeting = (JXKH_HYLW) item.getValue();
					AddMeetArticalWindow w = (AddMeetArticalWindow) Executions
							.createComponents(
									"/admin/personal/deptbusinessdata/artical/meetartical/addMeetarti.zul",
									null, null);
					try {
						w.setMeeting(meeting);
						w.setAudit("AUDIT");
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
			});
			Listcell c3 = new Listcell();
			if (meeting.getLwGrade() == null)
				c3.setLabel("");
			else
				c3.setLabel(meeting.getLwGrade().getKbName());
			Listcell c6 = new Listcell(meeting.getjxYear());
			Listcell c4 = new Listcell();
			c4.setTooltiptext("下载文档");
			Toolbarbutton downlowd = new Toolbarbutton();
			downlowd.setImage("/css/default/images/button/down.gif");
			downlowd.setParent(c4);
			downlowd.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					DownloadWindow win = (DownloadWindow) Executions
							.createComponents(
									"/admin/personal/businessdata/meeting/download.zul",
									null, null);
					filesList = jxkhHylwService
							.findMeetingFilesByMeetingId(meeting);
					// win.setFiles(meeting.getFiles());
					win.setFiles(filesList);
					win.setFlag("HYLW");
					win.initWindow();
					win.doModal();
				}
			});
			Listcell c5 = new Listcell(meeting.getScore() == null ? ""
					: meeting.getScore().toString());
			Listcell c7 = new Listcell();
			c7.setTooltiptext("点击填写审核意见");
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
				case 7:
					c7.setLabel("业务办暂缓通过");
					c7.setStyle("color:red");
					break;
				}
			}
			// 弹出审核意见事件
			c7.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event event) throws Exception {
					AdviceWindow w = (AdviceWindow) Executions
							.createComponents(
									"/admin/jxkh/audit/article/meetarticle/advice.zul",
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
			item.appendChild(c0);
			item.appendChild(c1);
			item.appendChild(c2);
			item.appendChild(c3);
			item.appendChild(c6);
			item.appendChild(c4);
			item.appendChild(c5);
			item.appendChild(c7);
		}
	}

	public void onClick$passAll() {
		if (meetArtListbox.getSelectedItems() == null
				|| meetArtListbox.getSelectedItems().size() <= 0) {
			try {
				Messagebox.show("请选择要审核的会议论文！", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		Iterator<Listitem> items = meetArtListbox.getSelectedItems().iterator();
		List<JXKH_HYLW> meetingList = new ArrayList<JXKH_HYLW>();
		JXKH_HYLW meeting = new JXKH_HYLW();
		while (items.hasNext()) {
			meeting = (JXKH_HYLW) items.next().getValue();
			int rank = 0, index = 0;
			List<JXKH_HYLWDept> meetingDepList = meeting.getLwDep();
			for (int t = 0; t < meetingDepList.size(); t++) {
				JXKH_HYLWDept dep = meetingDepList.get(t);
				if (dep.getDepId().equals(user.getDept().getKdNumber())) {
					rank = dep.getRank();
					index = t;
				}
			}
			if ((rank == 1 || meeting.getLwState() == JXKH_MEETING.First_Dept_Pass)
					&& meeting.getTempState().charAt(index) == '0') {
				meetingList.add(meeting);
			}
		}
		BatchAuditWindow win = (BatchAuditWindow) Executions.createComponents(
				"/admin/jxkh/audit/article/meetarticle/batchAudit.zul", null,
				null);
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
		nameSearch = null;
		auditStateSearch = null;
		indicatorId = null;
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
		}
		if (rank.getSelectedIndex() != 0) {
			Jxkh_BusinessIndicator qkType = (Jxkh_BusinessIndicator) rank
					.getSelectedItem().getValue();
			indicatorId = qkType.getKbId();
		}
		zxPaging.addEventListener("onPaging", new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				meetingList = jxkhHylwService.findMeetingByConditionAndPage(
						nameSearch, auditStateSearch, indicatorId, user
								.getDept().getKdNumber(), zxPaging
								.getActivePage(), zxPaging.getPageSize());
				meetArtListbox.setModel(new ListModelList(meetingList));
			}
		});
		int totalNum = jxkhHylwService.findMeetingByCondition(nameSearch,
				auditStateSearch, indicatorId, user.getDept().getKdNumber());
		zxPaging.setTotalSize(totalNum);
		meetingList = jxkhHylwService.findMeetingByConditionAndPage(nameSearch,
				auditStateSearch, indicatorId, user.getDept().getKdNumber(),
				zxPaging.getActivePage(), zxPaging.getPageSize());
		meetArtListbox.setModel(new ListModelList(meetingList));
	}

	public void onClick$reset() {
		name.setValue("");
		auditState.getItemAtIndex(0).setSelected(true);
		rank.getItemAtIndex(0).setSelected(true);
	}

	// 导出
	public void onClick$exportExcel() {
		if (meetArtListbox.getSelectedCount() == 0) {
			try {
				Messagebox.show("请选择要导出的数据", "提示", Messagebox.OK,
						Messagebox.EXCLAMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		} else {
			int i = 0;
			ArrayList<JXKH_HYLW> expertlist = new ArrayList<JXKH_HYLW>();
			@SuppressWarnings("unchecked")
			Set<Listitem> sel = meetArtListbox.getSelectedItems();
			Iterator<Listitem> it = sel.iterator();
			while (it.hasNext()) {
				Listitem item = (Listitem) it.next();
				JXKH_HYLW p = (JXKH_HYLW) item.getValue();
				expertlist.add(i, p);
				i++;
			}
			Date now = new Date();
			String fileName = ConvertUtil.convertDateString(now)
					+ "HuiYiLunWenXinXi" + ".xls";
			String Title = "会议论文";
			List<String> titlelist = new ArrayList<String>();
			titlelist.add("序号");
			titlelist.add("会议论文名称");
			titlelist.add("全部作者");
			titlelist.add("院内部门");
			titlelist.add("合作单位");
			titlelist.add("会议级别");
			// titlelist.add("收录类别");
			titlelist.add("论文级别");
			titlelist.add("发表时间");
			titlelist.add("发表刊物名称");
			titlelist.add("发表刊物期次");
			titlelist.add("会议名称");
			titlelist.add("召开时间");
			titlelist.add("信息填写人");
			String c[][] = new String[expertlist.size()][titlelist.size()];
			// 从SQL中读数据
			for (int j = 0; j < expertlist.size(); j++) {
				JXKH_HYLW meeting = (JXKH_HYLW) expertlist.get(j);
				c[j][0] = j + 1 + "";
				c[j][1] = meeting.getLwName();
				// 全部作者
				String memberName = "";
				List<JXKH_HULWMember> qklwMemberList = jxkhHylwService
						.findAwardMemberByAwardId(meeting);
				for (int t = 0; t < qklwMemberList.size(); t++) {
					JXKH_HULWMember mem = (JXKH_HULWMember) qklwMemberList
							.get(t);
					memberName += mem.getName() + "、";
				}
				if (memberName != "" && memberName != null)
					memberName = memberName.substring(0,
							memberName.length() - 1);
				c[j][2] = memberName;
				// 部门
				String d = "";
				List<JXKH_HYLWDept> meetDeptList = jxkhHylwService
						.findMeetingDeptByMeetingId(meeting);
				for (int k = 0; k < meetDeptList.size(); k++) {
					JXKH_HYLWDept dept = (JXKH_HYLWDept) meetDeptList.get(k);
					d += dept.getName() + "、";
				}
				if (d != "" && d != null)
					d = d.substring(0, d.length() - 1);
				c[j][3] = d;
				c[j][4] = meeting.getLwCoDep();
				if (meeting.getLwGrade() == null) {
					c[j][5] = "";
				} else {
					c[j][5] = meeting.getLwGrade().getKbName();
				}
				// if (meeting.getLwType() == null) {
				// c[j][6] = "";
				// } else {
				// c[j][6] = meeting.getLwType().getKbName();
				// }
				c[j][6] = meeting.getLwRank();
				c[j][7] = meeting.getLwTime();
				c[j][8] = meeting.getBookName();
				c[j][9] = meeting.getLwQC();
				c[j][10] = meeting.getLwHyName();
				c[j][11] = meeting.getLwDate();
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
