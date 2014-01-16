package org.iti.jxkh.busiAudit.project;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import jxl.write.WriteException;

import org.iti.gh.common.util.ExportExcel;
import org.iti.jxkh.business.meeting.DownloadWindow;
import org.iti.jxkh.entity.JXKH_MEETING;
import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.entity.Jxkh_Project;
import org.iti.jxkh.entity.Jxkh_ProjectDept;
import org.iti.jxkh.entity.Jxkh_ProjectMember;
import org.iti.jxkh.service.JXKHMeetingService;
import org.iti.jxkh.service.JxkhProjectService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Groupbox;
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
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;

public class ZprojectWindow extends Window implements AfterCompose {
	/**
	 * @author SongYu
	 */
	private static final long serialVersionUID = -4018673049983784473L;
	private Textbox name1;
	private Groupbox cxtj1;
	private Listbox zxListbox, auditState1, rank1, dept1;
	WkTUser user;
	Boolean cx1;
	private JxkhProjectService jxkhProjectService;
	private JXKHMeetingService jxkhMeetingService;
	public static final Integer qikan = 41;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");// ��ȡ��ǰ��¼�û�
		zxListbox.setItemRenderer(new ZProjectRenderer());
		auditState1.setItemRenderer(new auditStateRenderer());
		cx1 = false;
		initShow();
		rank1.setItemRenderer(new qkTypeRenderer());
		List<Jxkh_BusinessIndicator> holdList = new ArrayList<Jxkh_BusinessIndicator>();
		holdList.add(new Jxkh_BusinessIndicator());
		if (jxkhMeetingService.findRank(qikan) != null) {
			holdList.addAll(jxkhMeetingService.findRank(qikan));
		}
		rank1.setModel(new ListModelList(holdList));
		rank1.setSelectedIndex(0);

		dept1.setItemRenderer(new deptRenderer());
		List<WkTDept> depList = new ArrayList<WkTDept>();
		depList.add(new WkTDept());
		if (jxkhMeetingService.findAllDep() != null)
			depList.addAll(jxkhMeetingService.findAllDep());
		dept1.setModel(new ListModelList(depList));
		dept1.setSelectedIndex(0);
	}

	/** �ڿ�����б���Ⱦ�� */
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

	/** �����б���Ⱦ�� */
	public class deptRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			WkTDept d = (WkTDept) data;
			item.setValue(d);
			Listcell c0 = new Listcell();
			if (d.getKdName() == null) {
				c0.setLabel("--��ѡ��--");
			} else {
				c0.setLabel(d.getKdName());
			}
			item.appendChild(c0);

			if (item.getIndex() == 0)
				item.setSelected(true);
		}
	}

	public void initShow() {
		List<Jxkh_Project> zProjectList = jxkhProjectService.findAllZPByBusi();
		zxListbox.setModel(new ListModelList(zProjectList));
		String[] a = { "", "�����", "���������","����ͨ��",  "���Ų�ͨ��","ҵ����ݻ�ͨ��", "ҵ���ͨ��", "ҵ��첻ͨ��",
		"�鵵" };
		List<String> auditStateList = new ArrayList<String>();
		for (int i = 0; i < 8; i++) {
			auditStateList.add(a[i]);
		}
		auditState1.setModel(new ListModelList(auditStateList));
		auditState1.setSelectedIndex(0);
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
			final Jxkh_Project project = (Jxkh_Project) data;
			item.setValue(project);
			Listcell c0 = new Listcell();
			Listcell c1 = new Listcell(item.getIndex() + 1 + "");
			Listcell c2 = new Listcell(project.getName().length() <= 9?
					project.getName():project.getName().substring(0, 9) + "...");
			c2.setStyle("color:blue");
			c2.setTooltiptext("����༭������Ŀ��Ϣ");
			c2.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {

					AddZPWindow w = (AddZPWindow) Executions.createComponents(
							"/admin/jxkh/busiAudit/project/addZP.zul", null,
							null);
					try {
						w.setTitle("�༭������Ŀ��Ϣ");
						w.setProject(project);
//						w.initShow();
						w.initWindow();
						w.doModal();
					} catch (SuspendNotAllowedException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (cx1 == true) {
						onClick$query1();
					} else {
						initShow();
					}
				}
			});
			Listcell c3 = new Listcell(project.getRank().getKbName());
			Listcell c4 = new Listcell(project.getjxYear());
			Listcell c5 = new Listcell(project.getBeginDate());
			Listcell c6 = new Listcell();
			Image download = new Image("/css/default/images/button/down.gif");
			download.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					DownloadWindow win = (DownloadWindow) Executions
							.createComponents(
									"/admin/jxkh/busiAudit/award/download.zul",
									null, null);
					win.setFiles(project.getProjectFile());
					win.setFlag("zp");
					win.initWindow();
					win.doModal();
				}
			});
			c6.appendChild(download);
			Listcell c9 = new Listcell();
			if(project.getScore() != null) {
				BigDecimal bd = new BigDecimal(project.getScore());
				bd.setScale(1, RoundingMode.HALF_UP);
				c9.setLabel(Float.valueOf(bd.floatValue()).toString());
			}else {
				c9.setLabel("0");
			}
			Listcell c7 = new Listcell();
			Image addrecode = new Image(
					"/css/default/images/button/actEdit.gif");
			addrecode.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					if (project.getState() == 0 
							|| project.getState() == 1
							|| project.getState() == 2
							|| project.getState() == 3
							|| project.getState() == 5
							|| project.getState() == 7) {
						try {
							Messagebox.show("ҵ��컹δ���ͨ�������ܽ��й鵵��", "��ʾ",
									Messagebox.OK, Messagebox.INFORMATION);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						return;
					}
					RecordCodeWin win = (RecordCodeWin) Executions
							.createComponents(
									"/admin/jxkh/busiAudit/project/recordcode.zul",
									null, null);
					win.setAward(project);
					win.initShow();
					win.doModal();
					if (cx1 == true) {
						onClick$query1();
					} else {
						initShow();
					}
				}

			});
			c7.appendChild(addrecode);
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
			case Jxkh_Project.SAVE_RECORD:
				strC8 = "�鵵";
				break;
			case JXKH_MEETING.BUSINESS_TEMP_PASS:
				strC8 = "ҵ����ݻ�ͨ��";
				break;
			default:
				strC8 = "�����";
				break;
			}
			Listcell c8 = new Listcell(strC8);
			c8.setStyle("color:red");
			c8.setTooltiptext("�����д������");
			// �����������¼�
			c8.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event event) throws Exception {
					AdviceWin w = (AdviceWin) Executions.createComponents(
							"/admin/jxkh/busiAudit/project/advice.zul", null,
							null);
					try {
						w.setMeeting(project);
						w.initWindow();
						w.doModal();
					} catch (SuspendNotAllowedException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (cx1 == true) {
						onClick$query1();
					} else {
						initShow();
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
			item.appendChild(c9);
			item.appendChild(c8);
			item.appendChild(c7);
		}
	}


	/**
	 * <li>����������������ˡ� void
	 * 
	 * @author YuSong
	 */
	public void onClick$passAll1() {
		if (zxListbox.getSelectedItems() == null
				|| zxListbox.getSelectedItems().size() <= 0) {
			try {
				Messagebox.show("��ѡ��Ҫ�����Ŀ��", "��ʾ", Messagebox.OK,
						Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		Iterator<?> items = zxListbox.getSelectedItems().iterator();
		List<Jxkh_Project> awardList = new ArrayList<Jxkh_Project>();
		Jxkh_Project award = new Jxkh_Project();
		
		while (items.hasNext()) {
			Listitem item = (Listitem) items.next();
			award = (Jxkh_Project) item.getValue();
			if (award.getState() == 1 || award.getState() == 4
					|| award.getState() == 5) {
				awardList.add(award);
			}
		}
		BatchAuditWin win = (BatchAuditWin) Executions.createComponents(
				"/admin/jxkh/busiAudit/project/batchAudit.zul", null, null);
		try {
			win.setAwardList(awardList);
			win.doModal();

		} catch (SuspendNotAllowedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (cx1 == true) {
			onClick$query1();
		} else {
			initShow();
		}
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
			ArrayList<Jxkh_Project> expertlist = new ArrayList<Jxkh_Project>();
			@SuppressWarnings("unchecked")
			Set<Listitem> sel = zxListbox.getSelectedItems();
			Iterator<Listitem> it = sel.iterator();
			while (it.hasNext()) {
				Listitem item = (Listitem) it.next();
				Jxkh_Project p = (Jxkh_Project) item.getValue();
				expertlist.add(i, p);
				i++;
			}
			Date now = new Date();
			String fileName = ConvertUtil.convertDateString(now)
					+ "zongxiangxiangmu" + ".xls";
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
				List<Jxkh_ProjectMember> mlist = jxkhProjectService
						.findProjectMember(award);
				if (mlist.size() != 0) {
					for (int m = 0; m < mlist.size(); m++) {
						Jxkh_ProjectMember mem = (Jxkh_ProjectMember) mlist
								.get(m);
						member += mem.getName() + "��";
					}
					c[j][2] = member.substring(0, member.length() - 1);
				}
				List<Jxkh_ProjectDept> dlist = jxkhProjectService
						.findProjectDept(award);
				String dept = "";
				if (dlist.size() != 0) {
					for (int m = 0; m < dlist.size(); m++) {
						Jxkh_ProjectDept de = (Jxkh_ProjectDept) dlist.get(m);
						dept += de.getName() + "��";
					}
					c[j][3] = dept.substring(0, dept.length() - 1);
				}
				c[j][4] = award.getRank().getKbName();
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
				case JXKH_MEETING.BUSINESS_TEMP_PASS:
					strC8 = "ҵ����ݻ�ͨ��";
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

	public void onClick$query1() {
		String nameSearch = name1.getValue();
		Short auditStateSearch = null;
		cx1 = true;
		if (auditState1.getSelectedItem().getValue().equals("")) {
			auditStateSearch = null;
		} else if (auditState1.getSelectedItem().getValue().equals("�����")) {
			auditStateSearch = 0;
		} else if (auditState1.getSelectedItem().getValue().equals("����ͨ��")) {
			auditStateSearch = 1;
		} else if (auditState1.getSelectedItem().getValue().equals("���������")) {
			auditStateSearch = 2;
		} else if (auditState1.getSelectedItem().getValue().equals("���Ų�ͨ��")) {
			auditStateSearch = 3;
		} else if (auditState1.getSelectedItem().getValue().equals("ҵ���ͨ��")) {
			auditStateSearch = 4;
		} else if (auditState1.getSelectedItem().getValue().equals("ҵ��첻ͨ��")) {
			auditStateSearch = 5;
		} else if (auditState1.getSelectedItem().getValue().equals("�鵵")) {
			auditStateSearch = 6;
		}else if (auditState1.getSelectedItem().getValue().equals("ҵ����ݻ�ͨ��")) {
			auditStateSearch = JXKH_MEETING.BUSINESS_TEMP_PASS;
		}
		Long indicatorId = null;
		if (rank1.getSelectedIndex() != 0) {
			Jxkh_BusinessIndicator qkType = (Jxkh_BusinessIndicator) rank1
					.getSelectedItem().getValue();
			indicatorId = qkType.getKbId();
		}
		String depName = null;
		if (dept1.getSelectedIndex() != 0) {
			WkTDept d = (WkTDept) dept1.getSelectedItem().getValue();
			depName = d.getKdName();
		}

		List<Jxkh_Project> projectList = jxkhProjectService.findZPByCondition(
				nameSearch, auditStateSearch, indicatorId, depName);
		zxListbox.setModel(new ListModelList(projectList));
	}

	public void onClick$searchcbutton1() {
		if (cxtj1.isVisible()) {
			cxtj1.setVisible(false);
		} else {
			cxtj1.setVisible(true);
		}

	}

	public void onClick$reset1() {
		name1.setValue("");
		auditState1.getItemAtIndex(0).setSelected(true);
		rank1.getItemAtIndex(0).setSelected(true);
		dept1.getItemAtIndex(0).setSelected(true);
	}
}
