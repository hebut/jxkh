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
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");// ��ȡ��ǰ��¼�û�
		meetingListbox.setItemRenderer(new MeetingRenderer());
		year.initYearListbox("");
		initWindow();
		String[] s = { "-��ѡ��-", "��д��","�����","���������", "����ͨ��", "���Ų�ͨ��","ҵ����ݻ�ͨ��", "ҵ���ͨ��", "ҵ��첻ͨ��",
		"�鵵" };
		List<String> lwjbList = new ArrayList<String>();
		for (int i = 0; i < 8; i++) {
			lwjbList.add(s[i]);
		}
		auditState.setModel(new ListModelList(lwjbList));
		auditState.setSelectedIndex(0);
	}

	/**
	 * <li>�������������h�б���Ⱦ��
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
			c2.setTooltiptext("����鿴ѧ��������Ϣ");
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
			c5.setTooltiptext("�����ĵ�");
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
			c8.setTooltiptext("����鿴��˽��");
			if (meeting.getMtState() == null) {
				c8.setLabel("�����");
				c8.setStyle("color:red");
			} else {
				switch (meeting.getMtState()) {
				case 0:
					c8.setLabel("�����");
					c8.setStyle("color:red");
					break;
				case 1:
					c8.setLabel("����ͨ��");
					c8.setStyle("color:red");
					break;
				case 2:
					c8.setLabel("���������");
					c8.setStyle("color:red");
					break;
				case 3:
					c8.setLabel("���Ų�ͨ��");
					c8.setStyle("color:red");
					break;
				case 4:
					c8.setLabel("ҵ���ͨ��");
					c8.setStyle("color:red");
					break;
				case 5:
					c8.setLabel("ҵ��첻ͨ��");
					c8.setStyle("color:red");
					break;
				case 6:
					c8.setLabel("�鵵");
					c8.setStyle("color:red");
					break;
				case 7:
					c8.setLabel("ҵ����ݻ�ͨ��");
					c8.setStyle("color:red");
					break;
				case 8:
					c8.setLabel("��д��");
					c8.setStyle("color:red");
					break;
				case 9:
					c8.setLabel("���������");
					c8.setStyle("color:red");
					break;
				}
			}
			// �����������¼�
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
	 * ����ѧ�����������еĵ����¼�
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

					Messagebox.show("�����Ѿ����ͨ������ҵ����Ѿ����ͨ������ֻ�ܲ鿴����Ȩ�ٱ༭ ��", "��ʾ",
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

	// �Γ���Ӱ��o����Ӧ���¼�
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

	// �Γ�h�����o����Ӧ���¼�
	public void onClick$del() throws InterruptedException {

		if (meetingListbox.getSelectedItems() == null
				|| meetingListbox.getSelectedItems().size() <= 0) {
			try {
				Messagebox.show("��ѡ��Ҫɾ����ѧ�����飡", "��ʾ", Messagebox.OK,
						Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		Messagebox.show("ȷ��ɾ����?", "ȷ��", Messagebox.OK | Messagebox.CANCEL,
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
												"���Ż�ҵ������ͨ��,�����Ѿ��鵵�Ļ������Ĳ���ɾ����",
												"��ʾ", Messagebox.OK,
												Messagebox.EXCLAMATION);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									return;
								}
								if (!user.getKuLid().equals(lw.getWriterId())) {
									try {
										Messagebox.show(
												"�Ǳ����ύ���ڿ����ģ�ֻ�ܲ鿴������ɾ����", "��ʾ",
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
								Messagebox.show("ѧ������ɾ���ɹ���", "��ʾ",
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
		if (auditState.getSelectedItem().getValue().equals("-��ѡ��-")) {
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
		}else if(auditState.getSelectedItem().getValue().equals("ҵ����ݻ�ͨ��")) {
			auditStateSearch = 7;
		}else if(auditState.getSelectedItem().getValue().equals("��д��")) {
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

	// ����
	public void onClick$exportExcel() {
		if (meetingListbox.getSelectedCount() == 0) {
			try {
				Messagebox.show("��ѡ��Ҫ����������", "��ʾ", Messagebox.OK,
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
			String Title = "ѧ������";
			List<String> titlelist = new ArrayList<String>();
			titlelist.add("���");
			titlelist.add("ѧ����������");
			titlelist.add("Ժ�ڲ���");
			titlelist.add("������λ");
			titlelist.add("���鼶��");
			titlelist.add("�ٰ�����");
			titlelist.add("���쵥λ");
			titlelist.add("�а쵥λ");
			titlelist.add("Э�쵥λ");
			titlelist.add("����ʱ��");
			titlelist.add("����ص�");
			titlelist.add("��������");
			titlelist.add("�����ģ");
			titlelist.add("��Ϣ��д��");
			String c[][] = new String[expertlist.size()][titlelist.size()];
			// ��SQL�ж�����
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
					d += dept.getName() + "��";
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
