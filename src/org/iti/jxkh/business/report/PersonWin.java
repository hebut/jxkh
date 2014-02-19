package org.iti.jxkh.business.report;

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
import org.iti.jxkh.entity.Jxkh_Award;
import org.iti.jxkh.entity.Jxkh_Report;
import org.iti.jxkh.entity.Jxkh_ReportDept;
import org.iti.jxkh.entity.Jxkh_ReportMember;
import org.iti.jxkh.service.JxkhReportService;
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
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import com.iti.common.util.ConvertUtil;
import com.uniwin.framework.entity.WkTUser;

public class PersonWin extends Window implements AfterCompose {

	/**
	 * @author ZhangyuGuang
	 */
	private static final long serialVersionUID = -3373087402377032549L;
	private Listbox reportListbox;
	private JxkhReportService jxkhReportService;
	private List<Jxkh_Report> reportList = new ArrayList<Jxkh_Report>();
	private WkTUser user;
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
		reportListbox.setItemRenderer(new ReportRenderer());
		year.initYearListbox("");
		initWindow();
		String[] s = { "-��ѡ��-", "��д��","�����","���������","����ͨ��", "���Ų�ͨ��","ҵ����ݻ�ͨ��", "ҵ���ͨ��", "ҵ��첻ͨ��",
		"�鵵" };
		List<String> lwjbList = new ArrayList<String>();
		for (int i = 0; i < 8; i++) {
			lwjbList.add(s[i]);
		}
		auditState.setModel(new ListModelList(lwjbList));
		auditState.setSelectedIndex(0);
	}

	public void initWindow() {
		reportList = jxkhReportService.findReportByKuLid(user.getKuLid());
		reportListbox.setModel(new ListModelList(reportList));
	}

	// �����б���Ⱦ��
	public class ReportRenderer implements ListitemRenderer {

		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			final Jxkh_Report report = (Jxkh_Report) data;
			item.setValue(report);
			Listcell c0 = new Listcell();
			Listcell c1 = new Listcell(item.getIndex() + 1 + "");
			Listcell c2 = new Listcell(report.getName().length() <= 12?
					report.getName():report.getName().substring(0, 12) + "...");
			c2.setTooltiptext(report.getName());
			c2.setStyle("color:blue");
			if (user.getKuLid().equals(report.getSubmitId())) {
				c2.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event event) throws Exception {
						Listitem item = (Listitem) event.getTarget()
								.getParent();
						Jxkh_Report report = (Jxkh_Report) item.getValue();
						/*if (report.getState() == Jxkh_Report.NOT_AUDIT
								|| report.getState() == Jxkh_Report.DEPT_NOT_PASS || report.getState() == JXKH_MEETING.WRITING 
								|| report.getState() == Jxkh_Report.BUSINESS_NOT_PASS) {

						} else {
							Messagebox.show(
									"�����Ѿ����ͨ������ҵ����Ѿ����ͨ������ֻ�ܲ鿴����Ȩ�ٱ༭ ��", "��ʾ",
									Messagebox.OK, Messagebox.ERROR);
						}*/
						AddReportWin w = (AddReportWin) Executions
								.createComponents(
										"/admin/personal/businessdata/report/addreport.zul",
										null, null);
						try {
							w.setReport(report);
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

			} else {
				c2.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event event) throws Exception {
						Listitem item = (Listitem) event.getTarget()
								.getParent();
						Jxkh_Report report = (Jxkh_Report) item.getValue();
						AddReportWin w = (AddReportWin) Executions
								.createComponents(
										"/admin/personal/businessdata/report/addreport.zul",
										null, null);
						try {
							w.setReport(report);
							w.setDetail("Detail");
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
			}
			//��������
			Listcell c3 = new Listcell(report.getType());
			//�������
			Listcell c4 = new Listcell(report.getjxYear());
			//����÷�
			Listcell c5 = new Listcell(report.getScore() == null ? "" : report
					.getScore().toString());
			//���˵÷�
			Listcell c6 = new Listcell("");
			List<Jxkh_ReportMember> mlist = report.getReportMember();
			for (int j = 0; j < mlist.size(); j++) {
				Jxkh_ReportMember m = mlist.get(j);
				if (user.getKuName().equals(m.getName())) {
					if (m.getScore() != null && !m.getScore().equals("")) {
						c6.setLabel(m.getScore() + "");
					}
				}
			}
			Listcell c7 = new Listcell();
			c7.setLabel(report.getSubmitName());
			//���״̬
			Listcell c8 = new Listcell();
			c8.setTooltiptext("����鿴��˽��");
			if (report.getState() == null || report.getState() == 0) {
				c8.setLabel("�����");
				c8.setStyle("color:red");
			} else {
				switch (report.getState()) {
				case 0:
					c8.setLabel("δ���");
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
							"/admin/personal/businessdata/report/advice.zul",
							null, null);
					try {
						w.setMeeting(report);
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

	// ������Ӱ�ť��������ӱ������
	public void onClick$add() {
		AddReportWin win = (AddReportWin) Executions
				.createComponents(
						"/admin/personal/businessdata/report/addreport.zul",
						null, null);
		try {

			win.initWindow();
			win.doModal();

		} catch (SuspendNotAllowedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		initWindow();
	}

	// ɾ���ǵ���ȷ��ɾ����
	public void onClick$del() throws InterruptedException {
		if (reportListbox.getSelectedItems() == null
				|| reportListbox.getSelectedItems().size() <= 0) {
			try {
				Messagebox.show("��ѡ��Ҫɾ���ı��棡", "��ʾ", Messagebox.OK,
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
							@SuppressWarnings("unchecked")
							Iterator<Listitem> items = reportListbox
									.getSelectedItems().iterator();
							List<Jxkh_Report> reportList = new ArrayList<Jxkh_Report>();
							Jxkh_Report report = new Jxkh_Report();
							while (items.hasNext()) {
								report = (Jxkh_Report) items.next().getValue();
								reportList.add(report);
								if (!user.getKuLid().equals(
										report.getSubmitId())) {
									try {
										Messagebox.show("���Ǳ����ύ�ı��治��ɾ����", "��ʾ",
												Messagebox.OK,
												Messagebox.INFORMATION);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									return;
								}
								if (report.getState() == Jxkh_Award.DEPT_PASS
										|| report.getState() == Jxkh_Award.First_Dept_Pass
										|| report.getState() == Jxkh_Award.BUSINESS_PASS || report.getState().equals(JXKH_MEETING.BUSINESS_TEMP_PASS)
										|| report.getState() == Jxkh_Award.SAVE_RECORD) {
									try {
										Messagebox.show(
												"���Ż�ҵ������ͨ����鵵�Ľ�������ɾ����", "��ʾ",
												Messagebox.OK,
												Messagebox.INFORMATION);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									return;
								}
							}
							jxkhReportService.deleteAll(reportList);
							try {
								Messagebox.show("����ɾ���ɹ���", "��ʾ", Messagebox.OK,
										Messagebox.INFORMATION);
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

		List<Jxkh_Report> reportList = jxkhReportService
				.findReportListByCondition(nameSearch, auditStateSearch,yearSearch, user.getKuLid());
		reportListbox.setModel(new ListModelList(reportList));
	}

	public void onClick$reset() {
		name.setValue("");
		auditState.getItemAtIndex(0).setSelected(true);
		year.getItemAtIndex(0).setSelected(true);
	}

	// ����
	public void onClick$expert() throws WriteException, IOException {
		if (reportListbox.getSelectedCount() == 0) {
			try {
				Messagebox.show("��ʾ��ѡ��Ҫ����������", "��ʾ", Messagebox.OK,
						Messagebox.EXCLAMATION);
			} catch (InterruptedException e) {
				// ignore
			}
			return;
		} else {
			int i = 0;
			ArrayList<Jxkh_Report> expertlist = new ArrayList<Jxkh_Report>();
			@SuppressWarnings("unchecked")
			Set<Listitem> sel = reportListbox.getSelectedItems();
			Iterator<Listitem> it = sel.iterator();
			while (it.hasNext()) {
				Listitem item = (Listitem) it.next();
				Jxkh_Report p = (Jxkh_Report) item.getValue();
				expertlist.add(i, p);
				i++;
			}
			Date now = new Date();
			String fileName = ConvertUtil.convertDateString(now)
					+ "baogaoxinxi" + ".xls";
			String Title = "�������";
			List<String> titlelist = new ArrayList<String>();
			titlelist.add("���");
			titlelist.add("��������");
			titlelist.add("�����");
			titlelist.add("Ժ�ڲ���");
			titlelist.add("Ժ�ⲿ��");
			titlelist.add("��������");
			titlelist.add("��ʾ�쵼");
			titlelist.add("���ʱ��");
			titlelist.add("��ѧ����");
			titlelist.add("��Ϣ��д��");
			String c[][] = new String[expertlist.size()][titlelist.size()];
			// ��SQL�ж�����
			for (int j = 0; j < expertlist.size(); j++) {
				Jxkh_Report report = (Jxkh_Report) expertlist.get(j);
				List<Jxkh_ReportMember> mlist = report.getReportMember();
				String member = "";
				c[j][0] = j + 1 + "";
				c[j][1] = report.getName();
				if (mlist.size() != 0) {
					for (int m = 0; m < mlist.size(); m++) {
						Jxkh_ReportMember mem = (Jxkh_ReportMember) mlist
								.get(m);
						member += mem.getName() + "��";
					}
					c[j][2] = member.substring(0, member.length() - 1);
				}
				List<Jxkh_ReportDept> dlist = report.getReprotDept();
				String dept = "";
				if (dlist.size() != 0) {
					for (int m = 0; m < dlist.size(); m++) {
						Jxkh_ReportDept de = (Jxkh_ReportDept) dlist.get(m);
						dept += de.getName() + "��";
					}
					c[j][3] = dept.substring(0, dept.length() - 1);
				}
				c[j][4] = report.getCoCompany();
				c[j][5] = report.getType();
				c[j][6] = report.getLeader().getKbName();
				c[j][7] = report.getDate();
				c[j][8] = report.getFiled();
				c[j][9] = report.getSubmitId();
			}
			ExportExcel ee = new ExportExcel();
			ee.exportExcel(fileName, Title, titlelist, expertlist.size(), c);
		}
	}

}
