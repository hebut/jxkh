package org.iti.jxkh.audit.video;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import jxl.write.WriteException;
import org.iti.gh.common.util.ExportExcel;
import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxkh.deptbusiness.video.AddVideoWin;
import org.iti.jxkh.entity.Jxkh_Video;
import org.iti.jxkh.entity.Jxkh_VideoDept;
import org.iti.jxkh.entity.Jxkh_VideoMember;
import org.iti.jxkh.service.JxkhVideoService;
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

public class DeptWin extends Window implements AfterCompose {

	/**
	 * @author ZhangyuGuang
	 */
	private static final long serialVersionUID = -3182140690538860586L;
	private Textbox videoName;
	private Listbox videoListbox, auditState, rank;
	private Groupbox cxtj;
	private YearListbox year;
	private JxkhVideoService jxkhVideoService;
	private List<Jxkh_Video> videoList = new ArrayList<Jxkh_Video>();
	private WkTUser user;
	private Paging videoPaging;
	private boolean isQuery = false;
	private String nameSearch, indicatorId, yearSearch;
	private Short auditStateSearch;

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		year.initYearListbox("");
		videoListbox.setItemRenderer(new VideoRenderer());
		auditState.setItemRenderer(new auditStateRenderer());
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");// ��ȡ��ǰ��¼�û�
		initWindow();
		String[] video_Types = { "", "��Ƶ��Ƭ", "�Ƽ�����ר��Ƭ", "���ս���ר��Ƭ", "���Ųɷ�" };
		List<String> videoTpyeList = new ArrayList<String>();
		for (int i = 0; i < 5; i++) {
			videoTpyeList.add(video_Types[i]);
		}
		rank.setModel(new ListModelList(videoTpyeList));
		rank.setSelectedIndex(0);
	}

	public void initWindow() {
		videoPaging.addEventListener("onPaging", new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				videoList = jxkhVideoService.findVideoByKbNumber2(user
						.getDept().getKdNumber(), videoPaging.getActivePage(),
						videoPaging.getPageSize());
				videoListbox.setModel(new ListModelList(videoList));
			}
		});
		int totalNum = jxkhVideoService.findVideoByKbNumber2(user.getDept()
				.getKdNumber());
		videoPaging.setTotalSize(totalNum);
		videoList = jxkhVideoService.findVideoByKbNumber2(user.getDept()
				.getKdNumber(), videoPaging.getActivePage(), videoPaging
				.getPageSize());
		videoListbox.setModel(new ListModelList(videoList));
		String[] a = { "", "�����","���������", "����ͨ��",  "���Ų�ͨ��" };
		List<String> auditStateList = new ArrayList<String>();
		for (int i = 0; i < 5; i++) {
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
			Listcell c2 = new Listcell(video.getName().length() <= 14?
					video.getName():video.getName().substring(0, 14) + "...");
			c2.setTooltiptext(video.getName());
			c2.setStyle("color:blue");
			c2.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event event) throws Exception {
					Listitem item = (Listitem) event.getTarget().getParent();
					Jxkh_Video video = (Jxkh_Video) item.getValue();
					AddVideoWin w = (AddVideoWin) Executions
							.createComponents(
									"/admin/personal/deptbusinessdata/video/addvideo.zul",
									null, null);
					try {
						w.setVideo(video);
						w.setAudit("AUDIT");
						w.initWindow();
						w.doModal();
					} catch (SuspendNotAllowedException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
			//Ӱ������
			Listcell c3 = new Listcell(video.getType());
			//�������
			Listcell c4 = new Listcell();
			if (video.getjxYear() != null) {
				c4 = new Listcell(video.getjxYear());
			} else {
				c4 = new Listcell("");
			}
			//����÷�
			Listcell c5 = new Listcell(video.getScore() == null ? "" : video
					.getScore().toString());
			//��д��
			Listcell c6 = new Listcell();
			c6.setLabel(video.getSubmitName());
			//���״̬
			Listcell c7 = new Listcell();
			c7.setTooltiptext("�����д������");
			if (video.getState() == null || video.getState() == 0) {
				c7.setLabel("�����");
				c7.setStyle("color:red");
			} else {
				switch (video.getState()) {
				case 0:
					break;
				case 1:
					c7.setLabel("����ͨ��");
					c7.setStyle("color:red");
					break;
				case 2:
					c7.setLabel("���������");
					c7.setStyle("color:red");
					break;
				case 3:
					c7.setLabel("���Ų�ͨ��");
					c7.setStyle("color:red");
					break;
				case 4:
					c7.setLabel("ҵ���ͨ��");
					c7.setStyle("color:red");
					break;
				case 5:
					c7.setLabel("ҵ��첻ͨ��");
					c7.setStyle("color:red");
					break;
				case 6:
					c7.setLabel("�鵵");
					c7.setStyle("color:red");
					break;
				case 7:
					c7.setLabel("ҵ����ݻ�ͨ��");
					c7.setStyle("color:red");
					break;
				}
			}
			// �����������¼�
			c7.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event event) throws Exception {
					AdviceWin w = (AdviceWin) Executions.createComponents(
							"/admin/jxkh/audit/video/advice.zul", null, null);
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
		}
	}

	public void onClick$passAll() {
		if (videoListbox.getSelectedItems() == null
				|| videoListbox.getSelectedItems().size() <= 0) {
			try {
				Messagebox.show("��ѡ��Ҫ���Ӱ�ӣ�", "��ʾ", Messagebox.OK,
						Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		@SuppressWarnings("unchecked")
		Iterator<Listitem> items = videoListbox.getSelectedItems().iterator();
		List<Jxkh_Video> videoList = new ArrayList<Jxkh_Video>();
		Jxkh_Video video = new Jxkh_Video();
		while (items.hasNext()) {

			video = (Jxkh_Video) items.next().getValue();
			int rank = 0, index = 0;
			List<Jxkh_VideoDept> meetingDepList = video.getVideoDept();
			for (int t = 0; t < meetingDepList.size(); t++) {
				Jxkh_VideoDept dep = meetingDepList.get(t);
				if (dep.getDeptId().equals(user.getDept().getKdNumber())) {
					rank = dep.getRank();
					index = t;
				}
			}
			if ((rank == 1 || video.getState() == Jxkh_Video.First_Dept_Pass)
					&& video.getTempState().charAt(index) == '0') {
				videoList.add(video);
			}

		}
		BatchAuditWin win = (BatchAuditWin) Executions.createComponents(
				"/admin/jxkh/audit/video/batchAudit.zul", null, null);
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

	/** ״̬�б���Ⱦ�� */
	public class auditStateRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			String auditState = (String) data;
			item.setValue(auditState);
			Listcell c0 = new Listcell();
			if (auditState == null || auditState.equals(""))
				c0.setLabel("--��ѡ��--");
			else {
				c0.setLabel(auditState);
			}
			item.appendChild(c0);
		}
	}

	public void onClick$query() {
		nameSearch = videoName.getValue();
		auditStateSearch = null;
		isQuery = true;
		if (auditState.getSelectedItem().getValue().equals("")) {
			auditStateSearch = null;
		} else if (auditState.getSelectedItem().getValue().equals("�����")) {
			auditStateSearch = 0;
		} else if (auditState.getSelectedItem().getValue().equals("����ͨ��")) {
			auditStateSearch = 1;
		} else if (auditState.getSelectedItem().getValue().equals("���������")) {
			auditStateSearch = 2;
		} else if (auditState.getSelectedItem().getValue().equals("���Ų�ͨ��")) {
			auditStateSearch = 3;
		} else if (auditState.getSelectedItem().getValue().equals("ҵ���ͨ��")) {
			auditStateSearch = 4;
		} else if (auditState.getSelectedItem().getValue().equals("ҵ��첻ͨ��")) {
			auditStateSearch = 5;
		} else if (auditState.getSelectedItem().getValue().equals("�鵵")) {
			auditStateSearch = 6;
		}
		indicatorId = null;
		if (rank.getSelectedIndex() != 0) {
			indicatorId = rank.getSelectedItem().getValue() + "";
		}
		yearSearch = null;
		if (year.getSelectedIndex() != 0 && year.getSelectedItem() != null)
			yearSearch = year.getSelectedItem().getValue() + "";

		videoPaging.addEventListener("onPaging", new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				videoList = jxkhVideoService.findVideoByCondition(nameSearch,
						auditStateSearch, indicatorId, yearSearch, user
								.getDept().getKdNumber(), videoPaging
								.getActivePage(), videoPaging.getPageSize());
				videoListbox.setModel(new ListModelList(videoList));
			}
		});
		int totalNum = jxkhVideoService.findVideoByCondition(nameSearch,
				auditStateSearch, indicatorId, yearSearch, user.getDept()
						.getKdNumber());
		videoPaging.setTotalSize(totalNum);
		videoList = jxkhVideoService.findVideoByCondition(nameSearch,
				auditStateSearch, indicatorId, yearSearch, user.getDept()
						.getKdNumber(), videoPaging.getActivePage(),
				videoPaging.getPageSize());
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
		year.getItemAtIndex(0).setSelected(true);
	}

	// ����
	public void onClick$expert() throws WriteException, IOException {
		if (videoListbox.getSelectedCount() == 0) {
			try {
				Messagebox.show("��ʾ��ѡ��Ҫ����������", "��ʾ", Messagebox.OK,
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
			String Title = "Ӱ�����";
			List<String> titlelist = new ArrayList<String>();
			titlelist.add("���");
			titlelist.add("Ӱ������");
			titlelist.add("�����");
			titlelist.add("Ժ�ڲ���");
			titlelist.add("Ժ�ⲿ��");
			titlelist.add("��ʾ�쵼");
			titlelist.add("Ӱ������");
			titlelist.add("����ʱ��");
			titlelist.add("����ý�弶��");
			titlelist.add("����ʱ��");
			titlelist.add("����ʱ�������ӣ�");
			titlelist.add("����ý��");
			titlelist.add("��Ϣ��д��");
			String c[][] = new String[expertlist.size()][titlelist.size()];
			// ��SQL�ж�����
			for (int j = 0; j < expertlist.size(); j++) {
				Jxkh_Video video = (Jxkh_Video) expertlist.get(j);
				List<Jxkh_VideoMember> mlist = video.getVideoMember();
				String member = "";
				c[j][0] = j + 1 + "";
				c[j][1] = video.getName();
				if (mlist.size() != 0) {
					for (int m = 0; m < mlist.size(); m++) {
						Jxkh_VideoMember mem = (Jxkh_VideoMember) mlist.get(m);
						member += mem.getName() + "��";
					}
					c[j][2] = member.substring(0, member.length() - 1);
				}
				List<Jxkh_VideoDept> dlist = video.getVideoDept();
				String dept = "";
				if (dlist.size() != 0) {
					for (int m = 0; m < dlist.size(); m++) {
						Jxkh_VideoDept de = (Jxkh_VideoDept) dlist.get(m);
						dept += de.getName() + "��";
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
