package org.iti.jxkh.busiAudit.award;

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
import org.iti.jxkh.entity.Jxkh_Award;
import org.iti.jxkh.entity.Jxkh_AwardDept;
import org.iti.jxkh.entity.Jxkh_AwardFile;
import org.iti.jxkh.entity.Jxkh_AwardMember;
import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.service.JXKHMeetingService;
import org.iti.jxkh.service.JxkhAwardService;
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
	private static final long serialVersionUID = 8962429994368490346L;
	private Textbox awardName;
	private Listbox awardListbox, auditState, rank, dept;
	private Groupbox cxtj;
	private JxkhAwardService jxkhAwardService;
	private List<Jxkh_Award> awardList = new ArrayList<Jxkh_Award>();
	private Set<Jxkh_AwardFile> filesList;
	private Paging awardPaging;
	private String nameSearch, depName;
	private Long indicatorId;
	private Short auditStateSearch;
	private boolean isQuery = false;
	private JXKHMeetingService jxkhMeetingService;
	public static final Integer qikan = 31;

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		awardListbox.setItemRenderer(new AwardRenderer());
		auditState.setItemRenderer(new auditStateRenderer());
		initWindow();
		rank.setItemRenderer(new videoRankRenderer());

		rank.setItemRenderer(new qkTypeRenderer());
		List<Jxkh_BusinessIndicator> holdList = new ArrayList<Jxkh_BusinessIndicator>();
		holdList.add(new Jxkh_BusinessIndicator());
		if (jxkhMeetingService.findRank(qikan) != null) {
			holdList.addAll(jxkhMeetingService.findRank(qikan));
		}
		rank.setModel(new ListModelList(holdList));
		rank.setSelectedIndex(0);

		dept.setItemRenderer(new deptRenderer());
		List<WkTDept> depList = new ArrayList<WkTDept>();
		depList.add(new WkTDept());
		if (jxkhMeetingService.findAllDep() != null)
			depList.addAll(jxkhMeetingService.findAllDep());
		dept.setModel(new ListModelList(depList));
		dept.setSelectedIndex(0);
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

	public void initWindow() {
		awardPaging.addEventListener("onPaging", new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				awardList = jxkhAwardService.findAwardByAudit(
						awardPaging.getActivePage(), awardPaging.getPageSize());
				awardListbox.setModel(new ListModelList(awardList));
			}
		});
		int totalNum = jxkhAwardService.findAwardByAudit();
		awardPaging.setTotalSize(totalNum);
		awardList = jxkhAwardService.findAwardByAudit(
				awardPaging.getActivePage(), awardPaging.getPageSize());
		awardListbox.setModel(new ListModelList(awardList));
		String[] a = { "", "�����", "���������","����ͨ��",  "���Ų�ͨ��","ҵ����ݻ�ͨ��", "ҵ���ͨ��", "ҵ��첻ͨ��",
		"�鵵" };
		List<String> auditStateList = new ArrayList<String>();
		for (int i = 0; i < 8; i++) {
			auditStateList.add(a[i]);
		}
		auditState.setModel(new ListModelList(auditStateList));
		auditState.setSelectedIndex(0);

	}

	// Ӱ���쵼��Ⱦ��
	public class videoRankRenderer implements ListitemRenderer {

		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			Jxkh_BusinessIndicator rank = (Jxkh_BusinessIndicator) data;
			item.setValue(rank);
			Listcell c0 = new Listcell();
			if (rank.getKbName() == null) {
				c0.setLabel("--��ѡ��--");
			} else {
				c0.setLabel(rank.getKbName());
			}
			item.appendChild(c0);
		}
	}

	// �����б���Ⱦ��
	public class AwardRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			final Jxkh_Award award = (Jxkh_Award) data;
			
			item.setValue(award);
			Listcell c0 = new Listcell();
			Listcell c1 = new Listcell(item.getIndex() + 1 + "");
			Listcell c2 = new Listcell(award.getName().length() <= 12?
					award.getName():award.getName().substring(0, 12) + "...");
			c2.setTooltiptext("����鿴�Ƽ�������Ϣ");
			c2.setStyle("color:blue");
			
			c2.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event event) throws Exception {
					Listitem item = (Listitem) event.getTarget().getParent();
					Jxkh_Award award = (Jxkh_Award) item.getValue();
					AddAwardWin w = (AddAwardWin) Executions.createComponents(
							"/admin/jxkh/busiAudit/award/addaward.zul", null,
							null);
					try {
						w.setAward(award);
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
			if (award.getRank() != null) {
				c3 = new Listcell(award.getRank().getKbName());
			} else {
				c3 = new Listcell("");
			}
			Listcell c4 = new Listcell();
			if (award.getjxYear() != null) {
				c4 = new Listcell(award.getjxYear());
			} else {
				c4 = new Listcell("");
			}
			Listcell c5 = new Listcell();
			Toolbarbutton downlowd = new Toolbarbutton();
			downlowd.setImage("/css/default/images/button/down.gif");
			c5.setTooltip("����");
			downlowd.setParent(c5);
			downlowd.setHeight("20px");
			downlowd.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					DownloadWindow win = (DownloadWindow) Executions
							.createComponents(
									"/admin/jxkh/busiAudit/award/download.zul",
									null, null);

					filesList = award.getAwardFile();
					win.setFiles(filesList);
					win.setFlag("AWARD");
					win.initWindow();
					win.doModal();
				}
			});
			Listcell c6 = new Listcell(award.getScore() == null ? "" : award
					.getScore().toString());
			Listcell c7 = new Listcell();
			c7.setTooltiptext("�����д������");
			if (award.getState() == null || award.getState() == 0) {
				c7.setLabel("�����");
				c7.setStyle("color:red");
			} else {
				switch (award.getState()) {
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
					if (award.getState() == null || award.getState() == 0
							|| award.getState() == 2 || award.getState() == 3) {
						try {
							Messagebox.show("�������ͨ����ҵ���ſ�����ˣ�", "��ʾ",
									Messagebox.OK, Messagebox.INFORMATION);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					AdviceWin w = (AdviceWin) Executions.createComponents(
							"/admin/jxkh/busiAudit/award/advice.zul", null,
							null);
					try {
						w.setMeeting(award);
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
			Listcell c8 = new Listcell();
			Toolbarbutton addrecode = new Toolbarbutton();
			addrecode.setImage("/css/default/images/button/actEdit.gif");
			
			c8.setTooltip("��д������");
			addrecode.setParent(c8);
			addrecode.setHeight("20px");
			addrecode.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					if (award.getState() == 0 || award.getState() == 1
							|| award.getState() == 2 || award.getState() == 3
							|| award.getState() == 5
							|| award.getState() == 7) {
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
									"/admin/jxkh/busiAudit/award/recordcode.zul",
									null, null);
					try {
						win.setAward(award);
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

	/** ����״̬�б���Ⱦ�� */
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

	public void onClick$passAll() {
		if (awardListbox.getSelectedItems() == null
				|| awardListbox.getSelectedItems().size() <= 0) {
			try {
				Messagebox.show("��ѡ��Ҫ��˽�����", "��ʾ", Messagebox.OK,
						Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		Iterator<Listitem> items = awardListbox.getSelectedItems().iterator();
		List<Jxkh_Award> awardList = new ArrayList<Jxkh_Award>();
		Jxkh_Award award = new Jxkh_Award();
		while (items.hasNext()) {
			award = (Jxkh_Award) items.next().getValue();
			if (award.getState() == 1 || award.getState() == 4
					|| award.getState() == 5) {
				awardList.add(award);
			}
		}
		BatchAuditWin win = (BatchAuditWin) Executions.createComponents(
				"/admin/jxkh/busiAudit/award/batchAudit.zul", null, null);
		try {
			win.setAwardList(awardList);
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

	public void onClick$query() {
		nameSearch = awardName.getValue();
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
		}else if (auditState.getSelectedItem().getValue().equals("ҵ����ݻ�ͨ��")) {
			auditStateSearch = JXKH_MEETING.BUSINESS_TEMP_PASS;
		}
		indicatorId = null;
		if (rank.getSelectedIndex() != 0) {
			Jxkh_BusinessIndicator qkType = (Jxkh_BusinessIndicator) rank
					.getSelectedItem().getValue();
			indicatorId = qkType.getKbId();
		}
		depName = null;
		if (dept.getSelectedIndex() != 0) {
			WkTDept d = (WkTDept) dept.getSelectedItem().getValue();
			depName = d.getKdName();
		}
		awardPaging.addEventListener("onPaging", new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				awardList = jxkhAwardService.findAwardByCondition(nameSearch,
						auditStateSearch, indicatorId, depName,
						awardPaging.getActivePage(), awardPaging.getPageSize());
				awardListbox.setModel(new ListModelList(awardList));
			}
		});

		int totalNum = jxkhAwardService.findAwardByCondition(nameSearch,
				auditStateSearch, indicatorId, depName);
		awardPaging.setTotalSize(totalNum);
		awardList = jxkhAwardService.findAwardByCondition(nameSearch,
				auditStateSearch, indicatorId, depName,
				awardPaging.getActivePage(), awardPaging.getPageSize());
		awardListbox.setModel(new ListModelList(awardList));
	}

	public void onClick$searchcbutton() {
		if (cxtj.isVisible()) {
			cxtj.setVisible(false);
		} else {
			cxtj.setVisible(true);
		}

	}

	public void onClick$reset() {
		awardName.setValue("");
		auditState.getItemAtIndex(0).setSelected(true);
		rank.getItemAtIndex(0).setSelected(true);
		dept.getItemAtIndex(0).setSelected(true);
	}

	// ����
	public void onClick$expert() throws WriteException, IOException {
		if (awardListbox.getSelectedCount() == 0) {
			try {
				Messagebox.show("��ʾ��ѡ��Ҫ����������", "��ʾ", Messagebox.OK,
						Messagebox.EXCLAMATION);
			} catch (InterruptedException e) {
				// ignore
			}
			return;
		} else {
			int i = 0;
			ArrayList<Jxkh_Award> expertlist = new ArrayList<Jxkh_Award>();
			@SuppressWarnings("unchecked")
			Set<Listitem> sel = awardListbox.getSelectedItems();
			Iterator<Listitem> it = sel.iterator();
			while (it.hasNext()) {
				Listitem item = (Listitem) it.next();
				Jxkh_Award p = (Jxkh_Award) item.getValue();
				expertlist.add(i, p);
				i++;
			}
			Date now = new Date();
			String fileName = ConvertUtil.convertDateString(now)
					+ "jianglixinxi" + ".xls";
			String Title = "�������";
			List<String> titlelist = new ArrayList<String>();
			titlelist.add("���");
			titlelist.add("��������");
			titlelist.add("�����");
			titlelist.add("Ժ�ڲ���");
			titlelist.add("Ժ�ⲿ��");
			titlelist.add("��������");
			titlelist.add("����֤���");
			titlelist.add("��ʱ��");
			titlelist.add("�ڽ���λ");
			titlelist.add("��Ϣ��д��");
			String c[][] = new String[expertlist.size()][titlelist.size()];
			// ��SQL�ж�����
			for (int j = 0; j < expertlist.size(); j++) {
				Jxkh_Award award = (Jxkh_Award) expertlist.get(j);
				List<Jxkh_AwardMember> mlist = jxkhAwardService
						.findAwardMemberByAwardId(award);
				String member = "";
				c[j][0] = j + 1 + "";
				c[j][1] = award.getName();
				if (mlist.size() != 0) {
					for (int m = 0; m < mlist.size(); m++) {
						Jxkh_AwardMember mem = (Jxkh_AwardMember) mlist.get(m);
						member += mem.getName() + "��";
					}
					c[j][2] = member.substring(0, member.length() - 1);
				}
				List<Jxkh_AwardDept> dlist = jxkhAwardService
						.findAwardDeptByAwardId(award);
				String dept = "";
				if (dlist.size() != 0) {
					for (int m = 0; m < dlist.size(); m++) {
						Jxkh_AwardDept de = (Jxkh_AwardDept) dlist.get(m);
						dept += de.getName() + "��";
					}
					c[j][3] = dept.substring(0, dept.length() - 1);
				}
				c[j][4] = award.getCoCompany();
				c[j][5] = award.getRank().getKbName();
				c[j][6] = award.getRegisterCode();
				c[j][7] = award.getDate();
				c[j][8] = award.getAuthorizeCompany();
				c[j][9] = award.getSubmitName();
			}
			ExportExcel ee = new ExportExcel();
			ee.exportExcel(fileName, Title, titlelist, expertlist.size(), c);
		}
	}
}