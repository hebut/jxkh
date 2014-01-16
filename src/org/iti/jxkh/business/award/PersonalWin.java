package org.iti.jxkh.business.award;

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
import org.iti.jxkh.entity.Jxkh_Award;
import org.iti.jxkh.entity.Jxkh_AwardDept;
import org.iti.jxkh.entity.Jxkh_AwardFile;
import org.iti.jxkh.entity.Jxkh_AwardMember;
import org.iti.jxkh.service.JxkhAwardService;
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
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.iti.common.util.ConvertUtil;
import com.uniwin.framework.entity.WkTUser;

public class PersonalWin extends Window implements AfterCompose {

	/**
	 * @author ZhangyuGuang
	 */
	private static final long serialVersionUID = 424055975325539799L;
	private Listbox awardListbox;
	private JxkhAwardService jxkhAwardService;
	private List<Jxkh_Award> awardList = new ArrayList<Jxkh_Award>();
	private Set<Jxkh_AwardFile> filesList;
	private WkTUser user;
	private Textbox name;
	private YearListbox year;
	private String nameSearch, yearSearch;
	private Listbox auditState;
	private Integer auditStateSearch;

//	private BusinessIndicatorService businessIndicatorService;

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");// ��ȡ��ǰ��¼�û�
		awardListbox.setItemRenderer(new AwardRenderer());
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

	public void initWindow() {
		awardList = jxkhAwardService.findAwardByKuLid("", null, null,
				user.getKuLid());
		awardListbox.setModel(new ListModelList(awardList));
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
			if (user.getKuLid().equals(award.getSubmitId())) {
				c2.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event event) throws Exception {
						Listitem item = (Listitem) event.getTarget()
								.getParent();
						Jxkh_Award award = (Jxkh_Award) item.getValue();
						if (award.getState() == JXKH_MEETING.WRITING || award.getState() == Jxkh_Award.NOT_AUDIT
								|| award.getState() == Jxkh_Award.DEPT_NOT_PASS
								|| award.getState() == Jxkh_Award.BUSINESS_NOT_PASS) {
						} else {

							Messagebox.show(
									"�����Ѿ����ͨ������ҵ����Ѿ����ͨ������ֻ�ܲ鿴����Ȩ�ٱ༭ ��", "��ʾ",
									Messagebox.OK, Messagebox.ERROR);
						}
						AddAwardWin w = (AddAwardWin) Executions
								.createComponents(
										"/admin/personal/businessdata/award/addaward.zul",
										null, null);
						try {
							w.setAward(award);
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
						Jxkh_Award award = (Jxkh_Award) item.getValue();
						AddAwardWin w = (AddAwardWin) Executions
								.createComponents(
										"/admin/personal/businessdata/award/addaward.zul",
										null, null);
						try {
							w.setAward(award);
							w.setDetail("Detail");
							w.initWindow();
							w.doModal();
						} catch (SuspendNotAllowedException e) {
							e.printStackTrace();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				});
			}
			Listcell c3 = new Listcell();
			if (award.getRank() != null) {
				c3 = new Listcell(award.getRank().getKbName());
			} else {
				c3 = new Listcell("");
			}
			Listcell c4 = new Listcell(award.getjxYear());

			Listcell c5 = new Listcell();
			c5.setTooltiptext("�����ĵ�");
			Toolbarbutton downlowd = new Toolbarbutton();
			downlowd.setImage("/css/default/images/button/down.gif");
			downlowd.setParent(c5);
			downlowd.setHeight("20px");
			downlowd.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					DownloadWindow win = (DownloadWindow) Executions
							.createComponents(
									"/admin/personal/businessdata/meeting/download.zul",
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
			Listcell c7 = new Listcell("");
			List<Jxkh_AwardMember> mlist = award.getAwardMember();
			for (int j = 0; j < mlist.size(); j++) {
				Jxkh_AwardMember m = mlist.get(j);
				if (user.getKuName().equals(m.getName())) {
					if (m.getScore() != null && !m.getScore().equals("")) {
						c7.setLabel(m.getScore() + "");
					}
				}
			}
			Listcell c8 = new Listcell();
			c8.setTooltiptext("����鿴��˽��");
			if (award.getState() == null || award.getState() == 0) {
				c8.setLabel("�����");
				c8.setStyle("color:red");
			} else {
				switch (award.getState()) {
				case 0:
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
							"/admin/personal/businessdata/award/advice.zul",
							null, null);
					try {
						w.setMeeting(award);
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


	// ������Ӱ�ť��������ӽ�������
	public void onClick$add() {
		AddAwardWin win = (AddAwardWin) Executions.createComponents(
				"/admin/personal/businessdata/award/addaward.zul", null, null);
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
		if (awardListbox.getSelectedItems() == null
				|| awardListbox.getSelectedItems().size() <= 0) {
			try {
				Messagebox.show("��ѡ��Ҫɾ���Ľ�����", "��ʾ", Messagebox.OK,
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
							Iterator<Listitem> items = awardListbox
									.getSelectedItems().iterator();
							List<Jxkh_Award> awardList = new ArrayList<Jxkh_Award>();
							Jxkh_Award award = new Jxkh_Award();
							while (items.hasNext()) {
								award = (Jxkh_Award) items.next().getValue();
								awardList.add(award);
								if (!user.getKuLid()
										.equals(award.getSubmitId())) {
									try {
										Messagebox.show("���Ǳ����ύ�Ľ�������ɾ����", "��ʾ",
												Messagebox.OK,
												Messagebox.INFORMATION);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									return;
								}
								if (award.getState() == Jxkh_Award.DEPT_PASS
										|| award.getState() == Jxkh_Award.First_Dept_Pass || award.getState().equals(JXKH_MEETING.BUSINESS_TEMP_PASS)
										|| award.getState() == Jxkh_Award.BUSINESS_PASS
										|| award.getState() == Jxkh_Award.SAVE_RECORD) {
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
							jxkhAwardService.deleteAll(awardList);
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

		List<Jxkh_Award> awardList = jxkhAwardService.findAwardByKuLid(
				nameSearch, auditStateSearch, yearSearch, user.getKuLid());
		awardListbox.setModel(new ListModelList(awardList));
	}

	public void onClick$reset() {
		name.setValue("");
		auditState.getItemAtIndex(0).setSelected(true);
		year.getItemAtIndex(0).setSelected(true);
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
