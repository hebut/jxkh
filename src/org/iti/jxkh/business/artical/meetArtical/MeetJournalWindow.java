package org.iti.jxkh.business.artical.meetArtical;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import jxl.write.WriteException;

import org.iti.gh.common.util.ExportExcel;
import org.iti.jxkh.entity.JXKH_HULWMember;
import org.iti.jxkh.entity.JXKH_HYLW;
import org.iti.jxkh.entity.JXKH_HYLWDept;
import org.iti.jxkh.entity.JXKH_MEETING;
import org.iti.jxkh.entity.JXKH_QKLW;
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

public class MeetJournalWindow extends Window implements AfterCompose {

	/**
	 * @author CuiXiaoxin
	 */

	private static final long serialVersionUID = -1271025754692227364L;

	private Listbox indexListbox;
	private Paging zxPaging;
	private JxkhHylwService jxkhHylwService;
	private JXKHMeetingService jxkhMeetingService;
	private WkTUser user;
	private List<JXKH_HYLW> meetingList = new ArrayList<JXKH_HYLW>();
	private String nameSearch;
	private Textbox name;
	private Listbox auditState, rank;
	private Integer auditStateSearch;
	private Long indicatorId;
	public static final Integer huiyi = 13;

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");// ��ȡ��ǰ��¼�û�
		initWindow();
		indexListbox.setItemRenderer(new MeetingRenderer());

		String[] s = { "-��ѡ��-", "��д��","�����","���������", "����ͨ��", "���Ų�ͨ��","ҵ����ݻ�ͨ��", "ҵ���ͨ��", "ҵ��첻ͨ��",
		"�鵵" };
		List<String> lwjbList = new ArrayList<String>();
		for (int i = 0; i < 8; i++) {
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

	/**
	 * <li>�������������������б���Ⱦ��
	 */
	public class MeetingRenderer implements ListitemRenderer {

		@Override
		public void render(Listitem item, Object data) throws Exception {

			if (data == null)
				return;
			final JXKH_HYLW meeting = (JXKH_HYLW) data;
			item.setValue(meeting);
			Listcell c0 = new Listcell();
			Listcell c1 = new Listcell(item.getIndex() + 1 + "");
			//������Ŀ
			Listcell c2 = new Listcell(meeting.getLwName().length() <= 12?
					meeting.getLwName():meeting.getLwName().substring(0, 12) + "...");
			c2.setTooltiptext(meeting.getLwName());
			c2.setStyle("color:blue");
			c2.addEventListener(Events.ON_CLICK, new EditListener());
			//���鼶��
			Listcell c3 = new Listcell();
			if (meeting.getLwGrade() == null) {
				c3.setLabel("");
			} else {
				c3.setLabel(meeting.getLwGrade().getKbName());
			}
			//�������
			Listcell c4 = new Listcell(meeting.getjxYear());
			//����÷�
			Listcell c5 = new Listcell(meeting.getScore() == null ? ""
					: meeting.getScore().toString());
			//���˵÷�
			Listcell c6 = new Listcell("");
			List<JXKH_HULWMember> mlist = jxkhHylwService
					.findAwardMemberByAwardId(meeting);
			for (int j = 0; j < mlist.size(); j++) {
				JXKH_HULWMember m = mlist.get(j);
				if (user.getKuName().equals(m.getName())) {
					if (m.getScore() != null && !m.getScore().equals("")) {
						c6.setLabel(m.getScore() + "");
					}
				}
			}
			//��д��
			Listcell c61 = new Listcell();
			c61.setLabel(meeting.getLwWriter());
			//���״̬
			Listcell c7 = new Listcell();
			c7.setTooltiptext("����鿴��˽��");
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
				case 8:
					c7.setLabel("��д��");
					c7.setStyle("color:red");
					break;
			
				}
			}
			// �����������¼�
			c7.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event event) throws Exception {

					AdviceWin w = (AdviceWin) Executions
							.createComponents(
									"/admin/personal/businessdata/artical/meetartical/advice.zul",
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
			item.appendChild(c61);
			item.appendChild(c7);
		}
	}

	/**
	 * ����ѧ�����������еĵ����¼�
	 */
	public class EditListener implements EventListener {
		@Override
		public void onEvent(Event event) throws Exception {
			Listitem item = (Listitem) event.getTarget().getParent();
			JXKH_HYLW meeting = (JXKH_HYLW) item.getValue();
			if (user.getKuLid().equals(meeting.getWriterId())) {
				/*if (meeting.getLwState() == null || meeting.getLwState() == JXKH_MEETING.WRITING || meeting.getLwState() == 0
						|| meeting.getLwState() == 3
						|| meeting.getLwState() == 5) {
				} else {
					Messagebox.show("�����Ѿ����ͨ������ҵ����Ѿ����ͨ������ֻ�ܲ鿴����Ȩ�ٱ༭ ��", "��ʾ",
							Messagebox.OK, Messagebox.ERROR);
				}*/
				AddMeetArticalWindow w = (AddMeetArticalWindow) Executions
						.createComponents(
								"/admin/personal/businessdata/artical/meetartical/addMeetarti.zul",
								null, null);
				w.setMeeting(meeting);
				w.initWindow();
				w.addEventListener(Events.ON_CHANGE, new EventListener() {

					public void onEvent(Event arg0) throws Exception {
						initWindow();
					}
				});
				w.doModal();
			} else {
				AddMeetArticalWindow w = (AddMeetArticalWindow) Executions
						.createComponents(
								"/admin/personal/businessdata/artical/meetartical/addMeetarti.zul",
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
		zxPaging.addEventListener("onPaging", new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				meetingList = jxkhHylwService.findMeetingByKuLidAndpPaging("",
						null, null, user.getKuLid(), zxPaging.getActivePage(),
						zxPaging.getPageSize());
				indexListbox.setModel(new ListModelList(meetingList));
			}
		});
		int totalNum = jxkhHylwService.findMeetingByKuLid("", null, null,
				user.getKuLid());
		zxPaging.setTotalSize(totalNum);
		meetingList = jxkhHylwService.findMeetingByKuLidAndpPaging("", null,
				null, user.getKuLid(), zxPaging.getActivePage(),
				zxPaging.getPageSize());
		indexListbox.setModel(new ListModelList(meetingList));
	}

	// �Γ���Ӱ��o����Ӧ���¼�
	public void onClick$addMeetartical() {
		AddMeetArticalWindow win = (AddMeetArticalWindow) Executions
				.createComponents(
						"/admin/personal/businessdata/artical/meetartical/addMeetarti.zul",
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

	// �Γ�h�����o����Ӧ���¼�
	public void onClick$del() throws InterruptedException {

		if (indexListbox.getSelectedItems() == null
				|| indexListbox.getSelectedItems().size() <= 0) {
			try {
				Messagebox.show("��ѡ��Ҫɾ���Ļ������ģ�", "��ʾ", Messagebox.OK,
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
							Iterator<?> it = indexListbox
									.getSelectedItems().iterator();							
							while (it.hasNext()) {
								Listitem item = (Listitem) it.next();
								JXKH_HYLW lw = (JXKH_HYLW) item.getValue();
								if (lw.getLwState() == null) {
								} else if (lw.getLwState() == JXKH_MEETING.First_Dept_Pass
										|| lw.getLwState() == JXKH_QKLW.DEPT_PASS || lw.getLwState().equals(JXKH_MEETING.BUSINESS_TEMP_PASS)
										|| lw.getLwState() == JXKH_QKLW.BUSINESS_PASS
										|| lw.getLwState() == JXKH_QKLW.SAVE_RECORD) {
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
								jxkhHylwService.delete(lw);
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
		indicatorId = null;
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
		if (rank.getSelectedIndex() != 0) {
			Jxkh_BusinessIndicator qkType = (Jxkh_BusinessIndicator) rank
					.getSelectedItem().getValue();
			indicatorId = qkType.getKbId();
		}

		zxPaging.addEventListener("onPaging", new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				meetingList = jxkhHylwService.findMeetingByKuLidAndpPaging(
						nameSearch, auditStateSearch, indicatorId,
						user.getKuLid(), zxPaging.getActivePage(),
						zxPaging.getPageSize());
				indexListbox.setModel(new ListModelList(meetingList));
			}
		});
		int totalNum = jxkhHylwService.findMeetingByKuLid(nameSearch,
				auditStateSearch, indicatorId, user.getKuLid());
		zxPaging.setTotalSize(totalNum);
		meetingList = jxkhHylwService.findMeetingByKuLidAndpPaging(nameSearch,
				auditStateSearch, indicatorId, user.getKuLid(),
				zxPaging.getActivePage(), zxPaging.getPageSize());
		indexListbox.setModel(new ListModelList(meetingList));
	}

	public void onClick$reset() {
		name.setValue("");
		auditState.getItemAtIndex(0).setSelected(true);
		rank.getItemAtIndex(0).setSelected(true);
	}

	// ����
	public void onClick$exportExcel() {
		if (indexListbox.getSelectedCount() == 0) {
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
			Set<Listitem> sel = indexListbox.getSelectedItems();
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
