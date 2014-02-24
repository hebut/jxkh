package org.iti.jxkh.business.patent;

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
import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.entity.Jxkh_Patent;
import org.iti.jxkh.entity.Jxkh_PatentDept;
import org.iti.jxkh.entity.Jxkh_PatentInventor;
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

public class DeptPatentWindow extends Window implements AfterCompose {

	/**
	 * @author SongYu
	 */
	private static final long serialVersionUID = -4018673049983784473L;
	private Listbox zxListbox;
	private Textbox name;
	private Groupbox cxtj;
	Boolean cx;
	private YearListbox year;
	private Long indicatorId;
	private Listbox auditState, rank;
	private String nameSearch, yearSearch;
	private Short auditStateSearch;
	//private Paging zxPaging;
	WkTUser user;
	private JxkhProjectService jxkhProjectService;
	private JXKHMeetingService jxkhMeetingService;
	public static final Integer qikan = 51;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		year.initYearListbox("");
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");// ��ȡ��ǰ��¼�û�
		zxListbox.setItemRenderer(new ZProjectRenderer());
		auditState.setItemRenderer(new auditStateRenderer());
		//zxPaging.addEventListener("onPaging", new ZXPagingListener());
		initShow();
		String[] a = { "","��д��", "�����", "���������", "����ͨ��", "���Ų�ͨ��","ҵ����ݻ�ͨ��", "ҵ���ͨ��", "ҵ��첻ͨ��",
		"�鵵" };
		List<String> auditStateList = new ArrayList<String>();
		for (int i = 0; i < 8; i++) {
			auditStateList.add(a[i]);
		}
		auditState.setModel(new ListModelList(auditStateList));
		auditState.setSelectedIndex(0);
		rank.setItemRenderer(new qkTypeRenderer());
		List<Jxkh_BusinessIndicator> holdList = new ArrayList<Jxkh_BusinessIndicator>();
		holdList.add(new Jxkh_BusinessIndicator());
		if (jxkhMeetingService.findRank(qikan) != null) {
			holdList.addAll(jxkhMeetingService.findRank(qikan));
		}
		rank.setModel(new ListModelList(holdList));
		rank.setSelectedIndex(0);
	}

	/** ����б���Ⱦ�� */
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

	public void initShow() {
		List<Jxkh_Patent> patentList = jxkhProjectService
				.findAllPatentByDept1(user.getDept().getKdNumber());
		zxListbox.setModel(new ListModelList(patentList));
		
		//zxPaging.setTotalSize(patentList.size());
		cx = false;
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

	public class ZXPagingListener implements EventListener {
		@Override
		public void onEvent(Event event) throws Exception {
			if (cx == true) {
				List<Jxkh_Patent> patentList = jxkhProjectService
						.findPatentByCondition(nameSearch, auditStateSearch,
								indicatorId, yearSearch, user.getDept()
										.getKdNumber(), 0, 0);
				zxListbox.setModel(new ListModelList(patentList));
			} else {
				List<Jxkh_Patent> patentList = jxkhProjectService
						.findAllPatentByDept(user.getDept().getKdNumber(), 0,	0);
				zxListbox.setModel(new ListModelList(patentList));
			}
		}
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
			final Jxkh_Patent project = (Jxkh_Patent) data;
			item.setValue(project);
			Listcell c0 = new Listcell();
			Listcell c1 = new Listcell(item.getIndex() + 1 + "");
			Listcell c2 = new Listcell(project.getName().length()>10?project.getName().substring(0, 10)+"...":project.getName());
			c2.setTooltiptext(project.getName());
			c2.setStyle("color:blue");

			c2.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {

					AddPatentWindow w = (AddPatentWindow) Executions
							.createComponents(
									"/admin/personal/businessdata/patent/addPatent.zul",
									null, null);
					try {
						w.setTitle("�༭ר��(���)��Ϣ");
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
					if (cx == true) {
						onClick$query();
					} else {
						initShow();
					}
				}
			});
			//ר������
			Listcell c3 = new Listcell(project.getSort().getKbName());
			//�������
			Listcell c4 = new Listcell(project.getjxYear());
			//����÷�
			Listcell c5 = new Listcell(project.getScore() == null ? ""
					: project.getScore().toString());
			//��д��
			Listcell c6 = new Listcell();
			WkTUser user = jxkhProjectService.findWktUserByMemberUserId(project.getInfoWriter());
			c6.setLabel(user.getKuName());
			//���״̬
			String strC8;
			switch (project.getState()) {
			case Jxkh_Patent.NOT_AUDIT:
				strC8 = "�����";
				break;
			case Jxkh_Patent.DEPT_PASS:
				strC8 = "����ͨ��";
				break;
			case Jxkh_Patent.First_Dept_Pass:
				strC8 = "���������";
				break;
			case Jxkh_Patent.DEPT_NOT_PASS:
				strC8 = "���Ų�ͨ��";
				break;
			case Jxkh_Patent.BUSINESS_PASS:
				strC8 = "ҵ���ͨ��";
				break;
			case Jxkh_Patent.BUSINESS_NOT_PASS:
				strC8 = "ҵ��첻ͨ��";
				break;
			case JXKH_MEETING.WRITING:
				strC8 = "��д��";
				break;
			case JXKH_MEETING.BUSINESS_TEMP_PASS:
				strC8 = "ҵ����ݻ�ͨ��";
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
									"/admin/personal/businessdata/patent/advice.zul",
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
			item.appendChild(c8);
		}
	}

	/**
	 * <li>�������������������Ŀ�� void
	 * 
	 * @author YuSong
	 */
	public void onClick$add1() {
		AddPatentWindow w = (AddPatentWindow) Executions
				.createComponents(
						"/admin/personal/businessdata/patent/addPatent.zul",
						null, null);
		try {
			w.setDept1("dept");
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
				Messagebox.show("��ѡ��Ҫɾ����ר��(���)��", "��ʾ", Messagebox.OK,
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
								Jxkh_Patent project = (Jxkh_Patent) item.getValue();

								if (project.getState() == Jxkh_Patent.DEPT_PASS
										|| project.getState() == Jxkh_Patent.First_Dept_Pass
										|| project.getState() == Jxkh_Patent.BUSINESS_PASS || project.getState().shortValue() == JXKH_MEETING.BUSINESS_TEMP_PASS
										|| project.getState() == Jxkh_Patent.SAVE_RECORD) {
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
								Messagebox.show("ר��(���)ɾ���ɹ���", "��ʾ",
										Messagebox.OK, Messagebox.INFORMATION);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				});
		initShow();
	}

	public void onClick$query() {
		nameSearch = name.getValue();
		auditStateSearch = null;
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
		}else if (auditState.getSelectedItem().getValue().equals("��д��")) {
			auditStateSearch = JXKH_MEETING.WRITING;
		}else if (auditState.getSelectedItem().getValue().equals("ҵ����ݻ�ͨ��")) {
			auditStateSearch = JXKH_MEETING.BUSINESS_TEMP_PASS;
		}
		indicatorId = null;
		if (rank.getSelectedIndex() != 0) {
			Jxkh_BusinessIndicator qkType = (Jxkh_BusinessIndicator) rank
					.getSelectedItem().getValue();
			indicatorId = qkType.getKbId();
		}
		yearSearch = null;
		if (year.getSelectedIndex() != 0 && year.getSelectedItem() != null)
			yearSearch = year.getSelectedItem().getValue() + "";

//		List<Jxkh_Patent> patentList1 = jxkhProjectService
//				.findPatentByCondition(nameSearch, auditStateSearch,
//						indicatorId, yearSearch, user.getDept().getKdNumber());
		//zxPaging.setTotalSize(patentList1.size());
		List<Jxkh_Patent> patentList = jxkhProjectService
				.findPatentByCondition(nameSearch, auditStateSearch,
						indicatorId, yearSearch, user.getDept().getKdNumber(),
						0,0);
		zxListbox.setModel(new ListModelList(patentList));
		cx = true;
	}

	public void onClick$searchcbutton() {
		if (cxtj.isVisible()) {
			cxtj.setVisible(false);
		} else {
			cxtj.setVisible(true);
		}
	}

	public void onClick$reset() {
		name.setValue("");
		auditState.getItemAtIndex(0).setSelected(true);
		rank.getItemAtIndex(0).setSelected(true);
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
			ArrayList<Jxkh_Patent> expertlist = new ArrayList<Jxkh_Patent>();
			@SuppressWarnings("unchecked")
			Set<Listitem> sel = zxListbox.getSelectedItems();
			Iterator<Listitem> it = sel.iterator();
			while (it.hasNext()) {
				Listitem item = (Listitem) it.next();
				Jxkh_Patent p = (Jxkh_Patent) item.getValue();
				expertlist.add(i, p);
				i++;
			}
			Date now = new Date();
			String fileName = ConvertUtil.convertDateString(now)
					+ "zhuanlixinxi" + ".xls";
			String Title = "�������";
			List<String> titlelist = new ArrayList<String>();
			titlelist.add("���");
			titlelist.add("ר��(���)����");
			titlelist.add("������");
			titlelist.add("Ժ�ڲ���");

			titlelist.add("ר��(���)����");
			titlelist.add("֪ʶ��Ȩ��");
			titlelist.add("��Ȩʱ��");
			titlelist.add("��Ϣ��д��");
			titlelist.add("���״̬");
			String c[][] = new String[expertlist.size()][titlelist.size()];
			// ��SQL�ж�����
			for (int j = 0; j < expertlist.size(); j++) {
				Jxkh_Patent award = (Jxkh_Patent) expertlist.get(j);
				String member = "";
				c[j][0] = j + 1 + "";
				c[j][1] = award.getName();
				List<Jxkh_PatentInventor> mlist = jxkhProjectService
						.findPatentMember(award);
				if (mlist.size() != 0) {
					for (int m = 0; m < mlist.size(); m++) {
						Jxkh_PatentInventor mem = (Jxkh_PatentInventor) mlist
								.get(m);
						member += mem.getName() + "��";
					}
					c[j][2] = member.substring(0, member.length() - 1);
				}
				List<Jxkh_PatentDept> dlist = jxkhProjectService
						.findPatentDept(award);
				String dept = "";
				if (dlist.size() != 0) {
					for (int m = 0; m < dlist.size(); m++) {
						Jxkh_PatentDept de = (Jxkh_PatentDept) dlist.get(m);
						dept += de.getName() + "��";
					}
					c[j][3] = dept.substring(0, dept.length() - 1);
				}
				c[j][4] = award.getSort().getKbName();
				c[j][5] = award.getOwner();
				c[j][6] = award.getGrantDate();
				c[j][7] = award.getInfoWriter();
				String strC8;
				switch (award.getState()) {
				case Jxkh_Patent.NOT_AUDIT:
					strC8 = "�����";
					break;
				case Jxkh_Patent.DEPT_PASS:
					strC8 = "����ͨ��";
					break;
				case Jxkh_Patent.First_Dept_Pass:
					strC8 = "���������";
					break;
				case Jxkh_Patent.DEPT_NOT_PASS:
					strC8 = "���Ų�ͨ��";
					break;
				case Jxkh_Patent.BUSINESS_PASS:
					strC8 = "ҵ���ͨ��";
					break;
				case Jxkh_Patent.BUSINESS_NOT_PASS:
					strC8 = "ҵ��첻ͨ��";
					break;
				case Jxkh_Patent.SAVE_RECORD:
					strC8 = "�鵵";
					break;
				case JXKH_MEETING.WRITING:
					strC8 = "��д��";
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

}
