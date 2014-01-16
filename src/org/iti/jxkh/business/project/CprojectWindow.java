package org.iti.jxkh.business.project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import jxl.write.WriteException;

import org.iti.gh.common.util.ExportExcel;
import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxkh.audit.project.CPWindow;
import org.iti.jxkh.business.meeting.DownloadWindow;
import org.iti.jxkh.entity.JXKH_MEETING;
import org.iti.jxkh.entity.Jxkh_Project;
import org.iti.jxkh.entity.Jxkh_ProjectDept;
import org.iti.jxkh.entity.Jxkh_ProjectMember;
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

public class CprojectWindow extends Window implements AfterCompose {
	/**
	 * @author SongYu
	 */
	private static final long serialVersionUID = -4018673049983784473L;
	private Listbox zsListbox;
	WkTUser user;
	private JxkhProjectService jxkhProjectService;
	private Textbox name3;
	private YearListbox year3;
	private String nameSearch3, yearSearch3;
	private Listbox auditState3;
	private Integer auditStateSearch3;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");// ��ȡ��ǰ��¼�û�
		zsListbox.setItemRenderer(new CProjectRenderer());
		year3.initYearListbox("");
		initShow();
		String[] s = { "-��ѡ��-", "��д��", "�����", "���������", "����ͨ��", "���Ų�ͨ��", "ҵ����ݻ�ͨ��", "ҵ���ͨ��", "ҵ��첻ͨ��", "�鵵" };
		List<String> lwjbList = new ArrayList<String>();
		for (int i = 0; i < 8; i++) {
			lwjbList.add(s[i]);
		}
		auditState3.setModel(new ListModelList(lwjbList));
		auditState3.setSelectedIndex(0);
	}

	public void initShow() {
		List<Jxkh_Project> cProjectList = jxkhProjectService.findCPBymemberId(user.getKuLid());
		zsListbox.setModel(new ListModelList(cProjectList));
	}

	public class CProjectRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			final Jxkh_Project project = (Jxkh_Project) data;
			item.setValue(project);
			Listcell c0 = new Listcell();
			Listcell c1 = new Listcell(item.getIndex() + 1 + "");
			Listcell c2 = new Listcell(project.getName().length() <= 14?
					project.getName():project.getName().substring(0, 14) + "...");
			c2.setTooltiptext("����鿴Ժ��������Ŀ��Ϣ");
			c2.setStyle("color:blue");

			if (user.getKuLid().equals(project.getInfoWriter())) {
				if (project.getState() == JXKH_MEETING.WRITING || project.getState() == Jxkh_Project.NOT_AUDIT || project.getState() == Jxkh_Project.DEPT_NOT_PASS || project.getState() == Jxkh_Project.BUSINESS_NOT_PASS) {

					c2.setTooltiptext("����༭������Ŀ��Ϣ");
					c2.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event arg0) throws Exception {

							AddCPWindow w = (AddCPWindow) Executions.createComponents("/admin/personal/businessdata/project/addCP.zul", null, null);
							try {
								w.setTitle("�༭������Ŀ��Ϣ");
								w.setProject(project);
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

					c2.setTooltiptext("����鿴������Ŀ��Ϣ");
					c2.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event arg0) throws Exception {

							CPWindow w = (CPWindow) Executions.createComponents("/admin/personal/businessdata/project/showCP.zul", null, null);
							try {
								w.setTitle("�鿴������Ŀ��Ϣ");
								w.setProject(project);
								w.setDept("dept");
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
			} else {

				c2.setTooltiptext("����鿴������Ŀ��Ϣ");
				c2.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {

						CPWindow w = (CPWindow) Executions.createComponents("/admin/personal/businessdata/project/showCP.zul", null, null);
						try {
							w.setTitle("�鿴��Ŀ��Ϣ");
							w.setProject(project);
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
			
			Listcell c4 = new Listcell(project.getHeader());
			Listcell c05 = new Listcell(project.getjxYear()!=null?project.getjxYear():"");
			Listcell c5 = new Listcell(project.getBeginDate());
			Listcell c6 = new Listcell();
			Image download = new Image("/css/default/images/button/down.gif");
			download.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					DownloadWindow win = (DownloadWindow) Executions.createComponents("/admin/personal/businessdata/meeting/download.zul", null, null);
					win.setFiles(project.getProjectFile());
					win.setFlag("cp");
					win.initWindow();
					win.doModal();
				}
			});
			c6.appendChild(download);
			String strC8;
			switch (project.getState()) {
			case Jxkh_Project.NOT_AUDIT:
				strC8 = "�����";
				break;
			case Jxkh_Project.DEPT_PASS:
				strC8 = "����ͨ��";
				break;
			case Jxkh_Project.First_Dept_Pass:
				strC8 = "���������";
				break;
			case Jxkh_Project.DEPT_NOT_PASS:
				strC8 = "���Ų�ͨ��";
				break;
			case Jxkh_Project.BUSINESS_PASS:
				strC8 = "ҵ���ͨ��";
				break;
			case Jxkh_Project.BUSINESS_NOT_PASS:
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
					AdviceWindow pa = (AdviceWindow) Executions.createComponents("/admin/personal/businessdata/project/advice.zul", null, null);
					pa.setMeeting(project);
					pa.initWindow();
					pa.doModal();
				}
			});
			item.appendChild(c0);
			item.appendChild(c1);
			item.appendChild(c2);
			item.appendChild(c4);
			item.appendChild(c05);
			item.appendChild(c5);
			item.appendChild(c6);
			item.appendChild(c8);
		}
	}

	/**
	 * <li>�������������������Ŀ�� void
	 * 
	 * @author YuSong
	 */
	public void onClick$add3() {
		AddCPWindow w = (AddCPWindow) Executions.createComponents("/admin/personal/businessdata/project/addCP.zul", null, null);
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
	public void onClick$del3() throws InterruptedException {
		if (zsListbox.getSelectedItems() == null || zsListbox.getSelectedItems().size() <= 0) {
			try {
				Messagebox.show("��ѡ��Ҫɾ����������Ŀ��", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		Messagebox.show("ȷ��ɾ����?", "ȷ��", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
			public void onEvent(Event evt) throws InterruptedException {
				if (evt.getName().equals("onOK")) {
					Iterator<?> it = zsListbox.getSelectedItems().iterator();
					while (it.hasNext()) {
						Listitem item = (Listitem) it.next();
						Jxkh_Project project = (Jxkh_Project) item.getValue();
						if (!user.getKuLid().equals(project.getInfoWriter())) {
							try {
								Messagebox.show("���Ǳ����ύ�Ĳ���ɾ����", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							return;
						}
						if (project.getState() == Jxkh_Project.DEPT_PASS || project.getState() == Jxkh_Project.First_Dept_Pass || project.getState() == Jxkh_Project.BUSINESS_PASS || project.getState().equals(JXKH_MEETING.BUSINESS_TEMP_PASS)
								|| project.getState() == Jxkh_Project.SAVE_RECORD) {
							try {
								Messagebox.show("���Ż�ҵ������ͨ���Ĳ���ɾ����", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							return;
						}

						jxkhProjectService.delete(project);

					}
					try {
						Messagebox.show("������Ŀɾ���ɹ���", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		initShow();
	}

	public void onClick$query3() {
		nameSearch3 = name3.getValue();
		if (auditState3.getSelectedItem().getValue().equals("-��ѡ��-")) {
			auditStateSearch3 = null;
		} else if (auditState3.getSelectedItem().getValue().equals("�����")) {
			auditStateSearch3 = 0;
		} else if (auditState3.getSelectedItem().getValue().equals("����ͨ��")) {
			auditStateSearch3 = 1;
		} else if (auditState3.getSelectedItem().getValue().equals("���������")) {
			auditStateSearch3 = 2;
		} else if (auditState3.getSelectedItem().getValue().equals("���Ų�ͨ��")) {
			auditStateSearch3 = 3;
		} else if (auditState3.getSelectedItem().getValue().equals("ҵ���ͨ��")) {
			auditStateSearch3 = 4;
		} else if (auditState3.getSelectedItem().getValue().equals("ҵ��첻ͨ��")) {
			auditStateSearch3 = 5;
		} else if (auditState3.getSelectedItem().getValue().equals("�鵵")) {
			auditStateSearch3 = 6;
		} else if (auditState3.getSelectedItem().getValue().equals("ҵ����ݻ�ͨ��")) {
			auditStateSearch3 = 7;
		} else if (auditState3.getSelectedItem().getValue().equals("��д��")) {
			auditStateSearch3 = 8;
		}
		if (year3.getSelectedIndex() != 0 && year3.getSelectedItem() != null)
			yearSearch3 = year3.getSelectedItem().getValue() + "";

		List<Jxkh_Project> meetingList = jxkhProjectService.findZPByCondition(nameSearch3, auditStateSearch3, yearSearch3, user.getKuLid());
		zsListbox.setModel(new ListModelList(meetingList));
	}

	public void onClick$reset3() {
		name3.setValue("");
		auditState3.getItemAtIndex(0).setSelected(true);
		year3.getItemAtIndex(0).setSelected(true);
	}

	// ����
	public void onClick$export3() throws WriteException, IOException {
		if (zsListbox.getSelectedCount() == 0) {
			try {
				Messagebox.show("��ʾ��ѡ��Ҫ����������", "��ʾ", Messagebox.OK, Messagebox.EXCLAMATION);
			} catch (InterruptedException e) {
				// ignore
			}
			return;
		} else {
			int i = 0;
			ArrayList<Jxkh_Project> expertlist = new ArrayList<Jxkh_Project>();
			@SuppressWarnings("unchecked")
			Set<Listitem> sel = zsListbox.getSelectedItems();
			Iterator<Listitem> it = sel.iterator();
			while (it.hasNext()) {
				Listitem item = (Listitem) it.next();
				Jxkh_Project p = (Jxkh_Project) item.getValue();
				expertlist.add(i, p);
				i++;
			}
			Date now = new Date();
			String fileName = ConvertUtil.convertDateString(now) + "zishexiangmu" + ".xls";
			String Title = "�������";
			List<String> titlelist = new ArrayList<String>();
			titlelist.add("���");
			titlelist.add("��Ŀ����");
			titlelist.add("��Ŀ��Ա");
			titlelist.add("Ժ�ڲ���");

			titlelist.add("��Ŀ����");
			titlelist.add("��Ŀ������");
			titlelist.add("��ͬʼ��");
			titlelist.add("��Ϣ��д��");
			titlelist.add("���״̬");
			String c[][] = new String[expertlist.size()][titlelist.size()];
			// ��SQL�ж�����
			for (int j = 0; j < expertlist.size(); j++) {
				Jxkh_Project award = (Jxkh_Project) expertlist.get(j);
				String member = "";
				c[j][0] = j + 1 + "";
				c[j][1] = award.getName();
				List<Jxkh_ProjectMember> mlist = jxkhProjectService.findProjectMember(award);
				if (mlist.size() != 0) {
					for (int m = 0; m < mlist.size(); m++) {
						Jxkh_ProjectMember mem = (Jxkh_ProjectMember) mlist.get(m);
						member += mem.getName() + "��";
					}
					c[j][2] = member.substring(0, member.length() - 1);
				}
				List<Jxkh_ProjectDept> dlist = jxkhProjectService.findProjectDept(award);
				String dept = "";
				if (dlist.size() != 0) {
					for (int m = 0; m < dlist.size(); m++) {
						Jxkh_ProjectDept de = (Jxkh_ProjectDept) dlist.get(m);
						dept += de.getName() + "��";
					}
					c[j][3] = dept.substring(0, dept.length() - 1);
				}
				c[j][4] = "Ժ��������Ŀ";
				c[j][5] = award.getHeader();
				c[j][6] = award.getBeginDate();
				c[j][7] = award.getInfoWriter();
				String strC8;
				switch (award.getState()) {
				case Jxkh_Project.NOT_AUDIT:
					strC8 = "�����";
					break;
				case Jxkh_Project.DEPT_PASS:
					strC8 = "����ͨ��";
					break;
				case Jxkh_Project.First_Dept_Pass:
					strC8 = "���������";
					break;
				case Jxkh_Project.DEPT_NOT_PASS:
					strC8 = "���Ų�ͨ��";
					break;
				case Jxkh_Project.BUSINESS_PASS:
					strC8 = "ҵ���ͨ��";
					break;
				case Jxkh_Project.BUSINESS_NOT_PASS:
					strC8 = "ҵ��첻ͨ��";
					break;
				case Jxkh_Project.SAVE_RECORD:
					strC8 = "�鵵";
					break;
				case 7:
					strC8 = "ҵ����ݻ�ͨ��";
					break;
				case 8:
					strC8 = "��д��";
					break;
				default:
					strC8 = "�����";
					break;
				}
				c[j][8] = strC8;
			}
			ExportExcel ee = new ExportExcel();
			ee.exportExcel(fileName, Title, titlelist, expertlist.size(), c);
		}
	}
}
