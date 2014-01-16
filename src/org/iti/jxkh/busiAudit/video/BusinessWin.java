package org.iti.jxkh.busiAudit.video;

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
import org.iti.jxkh.entity.Jxkh_Video;
import org.iti.jxkh.entity.Jxkh_VideoDept;
import org.iti.jxkh.entity.Jxkh_VideoFile;
import org.iti.jxkh.entity.Jxkh_VideoMember;
import org.iti.jxkh.service.JXKHMeetingService;
import org.iti.jxkh.service.JxkhVideoService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
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
import com.uniwin.framework.entity.WkTDept;

public class BusinessWin extends Window implements AfterCompose {

	/**
	 * @author ZhangyuGuang
	 */
	private static final long serialVersionUID = -7946537169891069778L;
	private Textbox videoName;
	private Listbox videoListbox, auditState, rank, dept;
	private Groupbox cxtj;
	private JxkhVideoService jxkhVideoService;
	private JXKHMeetingService jxkhMeetingService;
	private List<Jxkh_Video> videoList = new ArrayList<Jxkh_Video>();
	private Set<Jxkh_VideoFile> filesList;
	private Paging videoPaging;
	private boolean isQuery = false;
	private String nameSearch, depName, indicatorId;
	private Short auditStateSearch;

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		videoListbox.setItemRenderer(new VideoRenderer());
		auditState.setItemRenderer(new auditStateRenderer());
		initWindow();

		String[] video_Types = { "", "视频短片", "科技宣传专题片", "科普教育专题片", "新闻采访" };
		List<String> videoTpyeList = new ArrayList<String>();
		for (int i = 0; i < 5; i++) {
			videoTpyeList.add(video_Types[i]);
		}
		rank.setModel(new ListModelList(videoTpyeList));
		rank.setSelectedIndex(0);

		dept.setItemRenderer(new deptRenderer());
		List<WkTDept> depList = new ArrayList<WkTDept>();
		depList.add(new WkTDept());
		if (jxkhMeetingService.findAllDep() != null)
			depList.addAll(jxkhMeetingService.findAllDep());
		dept.setModel(new ListModelList(depList));
		dept.setSelectedIndex(0);
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
		videoPaging.addEventListener("onPaging", new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				videoList = jxkhVideoService.findVideoByState(
						videoPaging.getActivePage(), videoPaging.getPageSize());
				videoListbox.setModel(new ListModelList(videoList));
			}
		});
		int totalNum = jxkhVideoService.findVideoByState();
		videoPaging.setTotalSize(totalNum);
		videoList = jxkhVideoService.findVideoByState(
				videoPaging.getActivePage(), videoPaging.getPageSize());
		videoListbox.setModel(new ListModelList(videoList));

		String[] a = { "", "待审核", "部门审核中","部门通过",  "部门不通过","业务办暂缓通过", "业务办通过", "业务办不通过",
		"归档" };
		List<String> auditStateList = new ArrayList<String>();
		for (int i = 0; i < 8; i++) {
			auditStateList.add(a[i]);
		}
		auditState.setModel(new ListModelList(auditStateList));
		auditState.setSelectedIndex(0);
	}

	public class VideoRenderer implements ListitemRenderer {

		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			final Jxkh_Video video = (Jxkh_Video) data;
			item.setValue(video);
			Listcell c0 = new Listcell();
			Listcell c1 = new Listcell(item.getIndex() + 1 + "");
			Listcell c2 = new Listcell(video.getName().length() <= 12?
					video.getName():video.getName().substring(0, 12) + "...");
			c2.setStyle("color:blue");
			c2.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event event) throws Exception {
					Listitem item = (Listitem) event.getTarget().getParent();
					Jxkh_Video video = (Jxkh_Video) item.getValue();
					AddVideoWin w = (AddVideoWin) Executions.createComponents(
							"/admin/jxkh/busiAudit/video/addvideo.zul", null,
							null);
					try {
						w.setVideo(video);
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

			Listcell c3 = new Listcell(video.getType());
			Listcell c4 = new Listcell();
			if (video.getjxYear() != null) {
				c4 = new Listcell(video.getjxYear());
			} else {
				c4 = new Listcell("");
			}
			Listcell c5 = new Listcell();
			Listcell c8 = new Listcell();
			Toolbarbutton downlowd = new Toolbarbutton();
			Toolbarbutton addrecode = new Toolbarbutton();
			downlowd.setImage("/css/default/images/button/down.gif");
			addrecode.setImage("/css/default/images/button/actEdit.gif");
			downlowd.setParent(c5);
			downlowd.setHeight("20px");
			addrecode.setParent(c8);
			addrecode.setHeight("20px");
			downlowd.setTooltip("下载文档");
			addrecode.setTooltip("填写档案号");
			downlowd.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					DownloadWindow win = (DownloadWindow) Executions
							.createComponents(
									"/admin/jxkh/busiAudit/award/download.zul",
									null, null);
					filesList = video.getVideoFile();
					win.setFiles(filesList);
					win.setFlag("VIDEO");
					win.initWindow();
					win.doModal();
				}
			});
			addrecode.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					if (video.getState() == 0 
							|| video.getState() == 1
							|| video.getState() == 2 
							|| video.getState() == 3
							|| video.getState() == 5
							|| video.getState() == 7) {
						try {
							Messagebox.show("业务办还未审核通过，不能进行归档！", "提示",
									Messagebox.OK, Messagebox.INFORMATION);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						return;
					}
					RecordCodeWin win = (RecordCodeWin) Executions
							.createComponents(
									"/admin/jxkh/busiAudit/video/recordcode.zul",
									null, null);
					try {
						win.setVideo(video);
						win.initWindow();
						win.doModal();
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
			Listcell c6 = new Listcell(video.getScore() == null ? "" : video
					.getScore().toString());
			Listcell c7 = new Listcell();
			c7.setTooltiptext("点击填写审核意见");
			if (video.getState() == null || video.getState() == 0) {
				c7.setLabel("待审核");
				c7.setStyle("color:red");
			} else {
				switch (video.getState()) {
				case 0:
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
				case JXKH_MEETING.BUSINESS_TEMP_PASS:
					c7.setLabel("业务办暂缓通过");
					c7.setStyle("color:red");
					break;
				}
			}
			// 弹出审核意见事件
			c7.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event event) throws Exception {
					AdviceWin w = (AdviceWin) Executions.createComponents(
							"/admin/jxkh/busiAudit/video/advice.zul", null,
							null);
					try {
						w.setMeeting(video);
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
		if (videoListbox.getSelectedItems() == null
				|| videoListbox.getSelectedItems().size() <= 0) {
			try {
				Messagebox.show("请选择要审核影视！", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		Iterator<Listitem> items = videoListbox.getSelectedItems().iterator();
		List<Jxkh_Video> videoList = new ArrayList<Jxkh_Video>();
		Jxkh_Video video = new Jxkh_Video();
		while (items.hasNext()) {
			video = (Jxkh_Video) items.next().getValue();
			videoList.add(video);
			if (video.getState() == 1 || video.getState() == 4
					|| video.getState() == 5) {
				videoList.add(video);
			}
		}
		BatchAuditWin win = (BatchAuditWin) Executions.createComponents(
				"/admin/jxkh/busiAudit/video/batchAudit.zul", null, null);
		try {
			win.setVideoList(videoList);
			win.doModal();

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

	/** 状态列表渲染器 */
	public class auditStateRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			String auditState = (String) data;
			item.setValue(auditState);
			Listcell c0 = new Listcell();
			if (auditState == null || auditState.equals(""))
				c0.setLabel("--请选择--");
			else {
				c0.setLabel(auditState);
			}
			item.appendChild(c0);
		}
	}

	public void onClick$query() {
		isQuery = true;
		nameSearch = videoName.getValue();
		auditStateSearch = null;
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
			auditStateSearch = JXKH_MEETING.BUSINESS_TEMP_PASS;
		}
		indicatorId = null;
		if (rank.getSelectedIndex() != 0) {
			indicatorId = rank.getSelectedItem().getValue() + "";
		}
		depName = null;
		if (dept.getSelectedIndex() != 0) {
			WkTDept d = (WkTDept) dept.getSelectedItem().getValue();
			depName = d.getKdName();
		}

		videoPaging.addEventListener("onPaging", new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				videoList = jxkhVideoService.findVideoByCondition(nameSearch,
						auditStateSearch, indicatorId, depName,
						videoPaging.getActivePage(), videoPaging.getPageSize());
				videoListbox.setModel(new ListModelList(videoList));
			}
		});
		int totalNum = jxkhVideoService.findVideoByCondition(nameSearch,
				auditStateSearch, indicatorId, depName);
		videoPaging.setTotalSize(totalNum);
		videoList = jxkhVideoService.findVideoByCondition(nameSearch,
				auditStateSearch, indicatorId, depName,
				videoPaging.getActivePage(), videoPaging.getPageSize());
		videoListbox.setModel(new ListModelList(videoList));
	}

	public void onClick$searchcbutton() {
		if (cxtj.isVisible()) {
			cxtj.setVisible(false);
		} else {
			cxtj.setVisible(true);
		}
	}

	public void onClick$reset() {
		videoName.setValue("");
		auditState.getItemAtIndex(0).setSelected(true);
		rank.getItemAtIndex(0).setSelected(true);
		dept.getItemAtIndex(0).setSelected(true);
	}

	// 导出
	public void onClick$expert() throws WriteException, IOException {
		if (videoListbox.getSelectedCount() == 0) {
			try {
				Messagebox.show("提示请选择要导出的数据", "提示", Messagebox.OK,
						Messagebox.EXCLAMATION);
			} catch (InterruptedException e) {
				// ignore
			}
			return;
		} else {
			int i = 0;
			ArrayList<Jxkh_Video> expertlist = new ArrayList<Jxkh_Video>();
			@SuppressWarnings("unchecked")
			Set<Listitem> sel = videoListbox.getSelectedItems();
			Iterator<Listitem> it = sel.iterator();
			while (it.hasNext()) {
				Listitem item = (Listitem) it.next();
				Jxkh_Video p = (Jxkh_Video) item.getValue();
				expertlist.add(i, p);
				i++;
			}
			Date now = new Date();
			String fileName = ConvertUtil.convertDateString(now)
					+ "yingshixinxi" + ".xls";
			String Title = "影视情况";
			List<String> titlelist = new ArrayList<String>();
			titlelist.add("序号");
			titlelist.add("影视名称");
			titlelist.add("完成人");
			titlelist.add("院内部门");
			titlelist.add("院外部门");
			titlelist.add("批示领导");
			titlelist.add("影视种类");
			titlelist.add("拍摄时间");
			titlelist.add("播出媒体级别");
			titlelist.add("播出时间");
			titlelist.add("播出时长（分钟）");
			titlelist.add("播出媒体");
			titlelist.add("信息填写人");
			String c[][] = new String[expertlist.size()][titlelist.size()];
			// 从SQL中读数据
			for (int j = 0; j < expertlist.size(); j++) {
				Jxkh_Video video = (Jxkh_Video) expertlist.get(j);
				List<Jxkh_VideoMember> mlist = video.getVideoMember();
				String member = "";
				c[j][0] = j + 1 + "";
				c[j][1] = video.getName();
				if (mlist.size() != 0) {
					for (int m = 0; m < mlist.size(); m++) {
						Jxkh_VideoMember mem = (Jxkh_VideoMember) mlist.get(m);
						member += mem.getName() + "、";
					}
					c[j][2] = member.substring(0, member.length() - 1);
				}
				List<Jxkh_VideoDept> dlist = video.getVideoDept();
				String dept = "";
				if (dlist.size() != 0) {
					for (int m = 0; m < dlist.size(); m++) {
						Jxkh_VideoDept de = (Jxkh_VideoDept) dlist.get(m);
						dept += de.getName() + "、";
					}
					c[j][3] = dept.substring(0, dept.length() - 1);
				}
				c[j][4] = video.getCoCompany();
				c[j][5] = video.getLeader().getKbName();
				c[j][6] = video.getType();
				c[j][7] = video.getShootTime();
				c[j][8] = video.getRank().getKbName();
				c[j][9] = video.getPlayTime();
				c[j][10] = video.getLongTime().toString();
				c[j][11] = video.getMedia();
				c[j][12] = video.getSubmitName();
			}
			ExportExcel ee = new ExportExcel();
			ee.exportExcel(fileName, Title, titlelist, expertlist.size(), c);
		}
	}
}
