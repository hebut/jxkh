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
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");// ��ȡ��ǰ��¼�û�
		meetArtListbox.setItemRenderer(new MeetingRenderer());
		initWindow();

		// String[] s = { "", "�����", "����ͨ��", "���Ų�ͨ��", "ҵ���ͨ��", "ҵ��첻ͨ��", "�鵵" };
		String[] s = { "", "�����", "���������","����ͨ��",  "���Ų�ͨ��" };
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

	/** ���鼶���б���Ⱦ�� */
	public class qkTypeRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			Jxkh_BusinessIndicator type = (Jxkh_BusinessIndicator) data;
			item.setValue(type);
			Listcell c0 = new Listcell();
			if (type.getKbName() == null) {
				c0.setLabel("--��ѡ��--");
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

	// �����б���Ⱦ��
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
			c2.setTooltiptext("����鿴����������Ϣ");
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
			c4.setTooltiptext("�����ĵ�");
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
			c7.setTooltiptext("�����д������");
			if (meeting.getLwState() == null) {
				c7.setLabel("�����");
				c7.setStyle("color:red");
			} else {
				switch (meeting.getLwState()) {
				case 0:
					c7.setLabel("�����");
					c7.setStyle("color:red");
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
				Messagebox.show("��ѡ��Ҫ��˵Ļ������ģ�", "��ʾ", Messagebox.OK,
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

	// ����
	public void onClick$exportExcel() {
		if (meetArtListbox.getSelectedCount() == 0) {
			try {
				Messagebox.show("��ѡ��Ҫ����������", "��ʾ", Messagebox.OK,
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
			String Title = "��������";
			List<String> titlelist = new ArrayList<String>();
			titlelist.add("���");
			titlelist.add("������������");
			titlelist.add("ȫ������");
			titlelist.add("Ժ�ڲ���");
			titlelist.add("������λ");
			titlelist.add("���鼶��");
			// titlelist.add("��¼���");
			titlelist.add("���ļ���");
			titlelist.add("����ʱ��");
			titlelist.add("����������");
			titlelist.add("�������ڴ�");
			titlelist.add("��������");
			titlelist.add("�ٿ�ʱ��");
			titlelist.add("��Ϣ��д��");
			String c[][] = new String[expertlist.size()][titlelist.size()];
			// ��SQL�ж�����
			for (int j = 0; j < expertlist.size(); j++) {
				JXKH_HYLW meeting = (JXKH_HYLW) expertlist.get(j);
				c[j][0] = j + 1 + "";
				c[j][1] = meeting.getLwName();
				// ȫ������
				String memberName = "";
				List<JXKH_HULWMember> qklwMemberList = jxkhHylwService
						.findAwardMemberByAwardId(meeting);
				for (int t = 0; t < qklwMemberList.size(); t++) {
					JXKH_HULWMember mem = (JXKH_HULWMember) qklwMemberList
							.get(t);
					memberName += mem.getName() + "��";
				}
				if (memberName != "" && memberName != null)
					memberName = memberName.substring(0,
							memberName.length() - 1);
				c[j][2] = memberName;
				// ����
				String d = "";
				List<JXKH_HYLWDept> meetDeptList = jxkhHylwService
						.findMeetingDeptByMeetingId(meeting);
				for (int k = 0; k < meetDeptList.size(); k++) {
					JXKH_HYLWDept dept = (JXKH_HYLWDept) meetDeptList.get(k);
					d += dept.getName() + "��";
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
