package org.iti.jxkh.business.writing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import jxl.write.WriteException;

import org.iti.gh.common.util.ExportExcel;
import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxkh.business.meeting.DownloadWindow;
import org.iti.jxkh.entity.JXKH_MEETING;
import org.iti.jxkh.entity.Jxkh_Patent;
import org.iti.jxkh.entity.Jxkh_Writer;
import org.iti.jxkh.entity.Jxkh_Writing;
import org.iti.jxkh.entity.Jxkh_WritingDept;
import org.iti.jxkh.service.JxkhProjectService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Image;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.iti.common.util.ConvertUtil;
import com.uniwin.framework.entity.WkTUser;

public class WritingWindow extends Window implements AfterCompose {

	/**
	 * @author SongYu
	 */
	private static final long serialVersionUID = -4018673049983784473L;
	private Listbox zxListbox;
	WkTUser user;
	private JxkhProjectService jxkhProjectService;
	private Textbox name;
	private YearListbox year;
	private String nameSearch, yearSearch;
	private Listbox auditState;
	private Integer auditStateSearch;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");// ��ȡ��ǰ��¼�û�
		zxListbox.setItemRenderer(new ZProjectRenderer());
		year.initYearListbox("");
		initShow();

		String[] s = { "-��ѡ��-", "��д��","�����","���������", "����ͨ��", "���Ų�ͨ��","ҵ����ݻ�ͨ��", "ҵ���ͨ��", "ҵ��첻ͨ��",
		"�鵵" };
		List<String> lwjbList = new ArrayList<String>();
		for (int i = 0; i < 8; i++) {
			lwjbList.add(s[i]);
		}
		auditState.setModel(new ListModelList(lwjbList));
		auditState.setSelectedIndex(0);
	}

	public void initShow() {
		List<Jxkh_Writing> writingList = jxkhProjectService
				.findWritingBymemberId(user.getKuLid());
		zxListbox.setModel(new ListModelList(writingList));
	}

	/**
	 * <li>����������������Ŀ�б���Ⱦ�� �� void
	 * 
	 * @author YuSong
	 */
	public class ZProjectRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			final Jxkh_Writing project = (Jxkh_Writing) data;
			item.setValue(project);
			Listcell c0 = new Listcell();
			Listcell c1 = new Listcell(item.getIndex() + 1 + "");
			Listcell c2 = new Listcell(project.getName().length() <= 12?
					project.getName():project.getName().substring(0, 12) + "...");
			c2.setTooltiptext("����鿴������Ϣ");
			c2.setStyle("color:blue");
			if (user.getKuLid().equals(project.getInfoWriter())) {
				c2.setTooltiptext("����༭������Ϣ");
				c2.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						if (project.getState() == Jxkh_Writing.NOT_AUDIT
								|| project.getState() == Jxkh_Writing.DEPT_NOT_PASS || project.getState() == JXKH_MEETING.WRITING
								|| project.getState() == Jxkh_Writing.BUSINESS_NOT_PASS) {
						} else {
							Messagebox.show(
									"�����Ѿ����ͨ������ҵ����Ѿ����ͨ������ֻ�ܲ鿴����Ȩ�ٱ༭ ��", "��ʾ",
									Messagebox.OK, Messagebox.ERROR);
						}
						AddWritingWindow w = (AddWritingWindow) Executions
								.createComponents(
										"/admin/personal/businessdata/writing/addWriting.zul",
										null, null);
						try {
							w.setTitle("�༭������Ϣ");
							w.setProject(project);
							if (project.getState() == Jxkh_Patent.First_Dept_Pass
									|| project.getState() == Jxkh_Patent.BUSINESS_PASS
									|| project.getState() == Jxkh_Patent.DEPT_PASS || project.getState()== JXKH_MEETING.BUSINESS_TEMP_PASS
									|| project.getState() == Jxkh_Patent.SAVE_RECORD)
								w.setDetail("Detail");
							w.initShow();
							w.initWindow();
							w.doModal();
						} catch (SuspendNotAllowedException e) {
							e.printStackTrace();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						initShow();
					}
				});

			} else {
				c2.setTooltiptext("����鿴������Ϣ");
				c2.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						AddWritingWindow w = (AddWritingWindow) Executions
								.createComponents(
										"/admin/personal/businessdata/writing/addWriting.zul",
										null, null);
						try {
							w.setTitle("�鿴������Ϣ");
							w.setProject(project);
							w.setDetail("Detail");
							w.initShow();
							w.initWindow();
							w.doModal();
						} catch (SuspendNotAllowedException e) {
							e.printStackTrace();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						initShow();
					}
				});
			}
			Listcell c3 = new Listcell(project.getSort().getKbName());
			Listcell c4 = new Listcell(project.getjxYear());
			Listcell c5 = new Listcell();
			Image download = new Image("/css/default/images/button/down.gif");
			download.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					DownloadWindow win = (DownloadWindow) Executions
							.createComponents(
									"/admin/personal/businessdata/meeting/download.zul",
									null, null);
					win.setFiles(project.getWritingFile());
					win.setFlag("writing");
					win.initWindow();
					win.doModal();
				}
			});
			c5.appendChild(download);
			Listcell c6 = new Listcell(project.getScore() == null ? "0"
					: project.getScore().toString());
			Listcell c7 = new Listcell("");
			List<Jxkh_Writer> mlist = jxkhProjectService.findWritingMember(project);
			for (int j = 0; j < mlist.size(); j++) {
				Jxkh_Writer m = mlist.get(j);
				if (user.getKuName().equals(m.getName())) {
					if (m.getScore() != null && !m.getScore().equals("")) {
						c7.setLabel(m.getScore() + "");
					}
				}
			}
			String strC8;
			switch (project.getState()) {
			case Jxkh_Writing.NOT_AUDIT:
				strC8 = "�����";
				break;
			case Jxkh_Writing.DEPT_PASS:
				strC8 = "����ͨ��";
				break;
			case Jxkh_Writing.First_Dept_Pass:
				strC8 = "���������";
				break;
			case Jxkh_Writing.DEPT_NOT_PASS:
				strC8 = "���Ų�ͨ��";
				break;
			case Jxkh_Writing.BUSINESS_PASS:
				strC8 = "ҵ���ͨ��";
				break;
			case Jxkh_Writing.BUSINESS_NOT_PASS:
				strC8 = "ҵ��첻ͨ��";
				break;
			case 7:
				strC8 = "ҵ����ݻ�ͨ��";
				break;
			case 8:
				strC8 = "��д��";
				break;
			case 9:
				strC8 = "���������";
				break;
			default:
				strC8 = "�鵵";
				break;
			}
			Listcell c8 = new Listcell(strC8);
			c8.setStyle("color:red");
			c8.setTooltiptext("����鿴��˽��");
			c8.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					AdviceWindow pa = (AdviceWindow) Executions
							.createComponents(
									"/admin/personal/businessdata/writing/advice.zul",
									null, null);
					pa.setMeeting(project);
					pa.initWindow();
					pa.doModal();
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
	 * <li>�������������������Ŀ�� void
	 * 
	 * @author YuSong
	 */
	public void onClick$add1() {
		AddWritingWindow w = (AddWritingWindow) Executions.createComponents(
				"/admin/personal/businessdata/writing/addWriting.zul", null,
				null);
		try {
			w.initShow();
			w.doModal();
		} catch (SuspendNotAllowedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		initShow();
	}

	/**
	 * <li>����������ɾ��������Ŀ�� void
	 * 
	 * @author YuSong
	 */
	public void onClick$del1() throws InterruptedException {
		if (zxListbox.getSelectedItems() == null
				|| zxListbox.getSelectedItems().size() <= 0) {
			try {
				Messagebox.show("��ѡ��Ҫɾ����������", "��ʾ", Messagebox.OK,
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
							Iterator<?> it = zxListbox
									.getSelectedItems().iterator();
							while (it.hasNext()) {
								Listitem item = (Listitem) it.next();
								Jxkh_Writing project = (Jxkh_Writing) item
										.getValue();
								if (!user.getKuLid().equals(
										project.getInfoWriter())) {
									try {
										Messagebox.show("���Ǳ����ύ�Ĳ���ɾ����", "��ʾ",
												Messagebox.OK,
												Messagebox.INFORMATION);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									return;
								}
								if (project.getState() == Jxkh_Patent.DEPT_PASS
										|| project.getState() == Jxkh_Patent.First_Dept_Pass
										|| project.getState() == Jxkh_Patent.BUSINESS_PASS
										|| project.getState() == Jxkh_Patent.SAVE_RECORD || project.getState() == JXKH_MEETING.BUSINESS_TEMP_PASS) {
									try {
										Messagebox.show("���Ż�ҵ������ͨ���Ĳ���ɾ����",
												"��ʾ", Messagebox.OK,
												Messagebox.INFORMATION);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									return;
								}
								jxkhProjectService.delete(project);
							}
							try {
								Messagebox.show("����ɾ���ɹ���", "��ʾ", Messagebox.OK,
										Messagebox.INFORMATION);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				});
		initShow();
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

		List<Jxkh_Writing> meetingList = jxkhProjectService
				.findWritingByCondition(nameSearch, auditStateSearch,
						yearSearch, user.getKuLid());
		zxListbox.setModel(new ListModelList(meetingList));

	}

	public void onClick$reset() {
		name.setValue("");
		auditState.getItemAtIndex(0).setSelected(true);
		year.getItemAtIndex(0).setSelected(true);
	}

	// ����
	public void onClick$export1() throws WriteException, IOException {
		if (zxListbox.getSelectedCount() == 0) {
			try {
				Messagebox.show("��ʾ��ѡ��Ҫ����������", "��ʾ", Messagebox.OK,
						Messagebox.EXCLAMATION);
			} catch (InterruptedException e) {
				// ignore
			}
			return;
		} else {
			int i = 0;
			ArrayList<Jxkh_Writing> expertlist = new ArrayList<Jxkh_Writing>();
			@SuppressWarnings("unchecked")
			Set<Listitem> sel = zxListbox.getSelectedItems();
			Iterator<Listitem> it = sel.iterator();
			while (it.hasNext()) {
				Listitem item = (Listitem) it.next();
				Jxkh_Writing p = (Jxkh_Writing) item.getValue();
				expertlist.add(i, p);
				i++;
			}
			Date now = new Date();
			String fileName = ConvertUtil.convertDateString(now)
					+ "zhuzuoxinxi" + ".xls";
			String Title = "�������";
			List<String> titlelist = new ArrayList<String>();
			titlelist.add("���");
			titlelist.add("��������");
			titlelist.add("�����");
			titlelist.add("Ժ�ڲ���");

			titlelist.add("��������");
			titlelist.add("������");
			titlelist.add("����ʱ��");
			titlelist.add("��Ϣ��д��");
			titlelist.add("���״̬");
			String c[][] = new String[expertlist.size()][titlelist.size()];
			// ��SQL�ж�����
			for (int j = 0; j < expertlist.size(); j++) {
				Jxkh_Writing award = (Jxkh_Writing) expertlist.get(j);
				String member = "";
				c[j][0] = j + 1 + "";
				c[j][1] = award.getName();
				List<Jxkh_Writer> mlist = jxkhProjectService
						.findWritingMember(award);
				if (mlist.size() != 0) {
					for (int m = 0; m < mlist.size(); m++) {
						Jxkh_Writer mem = (Jxkh_Writer) mlist.get(m);
						member += mem.getName() + "��";
					}
					c[j][2] = member.substring(0, member.length() - 1);
				}
				List<Jxkh_WritingDept> dlist = jxkhProjectService
						.findWritingDept(award);
				String dept = "";
				if (dlist.size() != 0) {
					for (int m = 0; m < dlist.size(); m++) {
						Jxkh_WritingDept de = (Jxkh_WritingDept) dlist.get(m);
						dept += de.getName() + "��";
					}
					c[j][3] = dept.substring(0, dept.length() - 1);
				}
				c[j][4] = award.getSort().getKbName();
				c[j][5] = award.getPublishName();
				c[j][6] = award.getPublishDate();
				c[j][7] = award.getInfoWriter();
				String strC8;
				switch (award.getState()) {
				case Jxkh_Writing.NOT_AUDIT:
					strC8 = "�����";
					break;
				case Jxkh_Writing.DEPT_PASS:
					strC8 = "����ͨ��";
					break;
				case Jxkh_Writing.First_Dept_Pass:
					strC8 = "���������";
					break;
				case Jxkh_Writing.DEPT_NOT_PASS:
					strC8 = "���Ų�ͨ��";
					break;
				case Jxkh_Writing.BUSINESS_PASS:
					strC8 = "ҵ���ͨ��";
					break;
				case Jxkh_Writing.BUSINESS_NOT_PASS:
					strC8 = "ҵ��첻ͨ��";
					break;
				case JXKH_MEETING.BUSINESS_TEMP_PASS:
					strC8 = "ҵ����ݻ�ͨ��";
					break;
				case JXKH_MEETING.WRITING:
					strC8 = "��д��";
					break;
				
				default:
					strC8 = "�鵵";
					break;
				}
				c[j][8] = strC8;
			}
			ExportExcel ee = new ExportExcel();
			ee.exportExcel(fileName, Title, titlelist, expertlist.size(), c);
		}
	}
}
