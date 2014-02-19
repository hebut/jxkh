package org.iti.jxkh.business.fruit;

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
import org.iti.jxkh.entity.Jxkh_Fruit;
import org.iti.jxkh.entity.Jxkh_FruitDept;
import org.iti.jxkh.entity.Jxkh_FruitMember;
import org.iti.jxkh.service.JxkhFruitService;
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
	 * @author
	 */
	private static final long serialVersionUID = 3012211588520459129L;
	private WkTUser user;
	private Listbox fruitListbox;
	private List<Jxkh_Fruit> fruitList = new ArrayList<Jxkh_Fruit>();
	private JxkhFruitService jxkhFruitService;
	private Textbox name;
	private YearListbox year;
	private String nameSearch, yearSearch;
	private Listbox auditState;
	private Integer auditStateSearch;

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		year.initYearListbox("");
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");// ��ȡ��ǰ��¼�û�
		fruitListbox.setItemRenderer(new FruitRenderer());
		initWindow();
		String[] s = { "-��ѡ��-", "��д��","�����","���������",  "����ͨ��", "���Ų�ͨ��","ҵ����ݻ�ͨ��", "ҵ���ͨ��", "ҵ��첻ͨ��",
		"�鵵" };
		List<String> lwjbList = new ArrayList<String>();
		for (int i = 0; i < 8; i++) {
			lwjbList.add(s[i]);
		}
		auditState.setModel(new ListModelList(lwjbList));
		auditState.setSelectedIndex(0);
	}

	public void initWindow() {
		fruitList = jxkhFruitService.findFruitByKuLid("", null, null,
				user.getKuLid());
		fruitListbox.setModel(new ListModelList(fruitList));
	}

	// �ɹ��б���Ⱦ��
	public class FruitRenderer implements ListitemRenderer {

		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			final Jxkh_Fruit fruit = (Jxkh_Fruit) data;
			item.setValue(fruit);
			Listcell c0 = new Listcell();
			Listcell c1 = new Listcell(item.getIndex() + 1 + "");
			Listcell c2 = new Listcell(fruit.getName().length() <= 11?
					fruit.getName():fruit.getName().substring(0, 11) + "...");
			c2.setTooltiptext(fruit.getName());
			c2.setStyle("color:blue");
			if (user.getKuLid().equals(fruit.getSubmitId())) {
				c2.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event event) throws Exception {
						Listitem item = (Listitem) event.getTarget()
								.getParent();
						Jxkh_Fruit fruit = (Jxkh_Fruit) item.getValue();
						/*if (fruit.getState() == JXKH_MEETING.WRITING || fruit.getState() == Jxkh_Fruit.NOT_AUDIT
								|| fruit.getState() == Jxkh_Fruit.DEPT_NOT_PASS
								|| fruit.getState() == Jxkh_Fruit.BUSINESS_NOT_PASS) {
						} else {
							Messagebox.show(
									"�����Ѿ����ͨ������ҵ����Ѿ����ͨ������ֻ�ܲ鿴����Ȩ�ٱ༭ ��", "��ʾ",
									Messagebox.OK, Messagebox.ERROR);
						}*/
						AddFruitWin w = (AddFruitWin) Executions
								.createComponents(
										"/admin/personal/businessdata/fruit/addfruit.zul",
										null, null);
						try {
							w.setFruit(fruit);
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
						Jxkh_Fruit fruit = (Jxkh_Fruit) item.getValue();
						AddFruitWin w = (AddFruitWin) Executions
								.createComponents(
										"/admin/personal/businessdata/fruit/addfruit.zul",
										null, null);
						try {
							w.setFruit(fruit);
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
			//�ɹ�ˮƽ
			Listcell c3 = new Listcell();
			if (fruit.getAppraiseRank() != null)
				c3 = new Listcell(fruit.getAppraiseRank().getKbName());
			//�������
			Listcell c4 = new Listcell(fruit.getjxYear());
			//����÷�
			Listcell c6 = new Listcell(fruit.getScore() == null ? "0" : fruit
					.getScore().toString());
			//���˵÷�
			Listcell c7 = new Listcell("");
			List<Jxkh_FruitMember> mlist = fruit.getFruitMember();
			for (int j = 0; j < mlist.size(); j++) {
				Jxkh_FruitMember m = mlist.get(j);
				if (user.getKuName().equals(m.getName())) {
					if (m.getScore() != null && !m.getScore().equals("")) {
						c7.setLabel(m.getScore() + "");
					}
				}
			}
			//��д��
			Listcell c72 = new Listcell();
			c72.setLabel(fruit.getSubmitName());
			//���״̬
			Listcell c8 = new Listcell();
			c8.setTooltiptext("����鿴��˽��");
			if (fruit.getState() == null || fruit.getState() == 0) {
				c8.setLabel("�����");
				c8.setStyle("color:red");
			} else {
				switch (fruit.getState()) {
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
			c8.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event event) throws Exception {
					AdviceWin w = (AdviceWin) Executions.createComponents(
							"/admin/personal/businessdata/fruit/advice.zul",
							null, null);
					try {
						w.setMeeting(fruit);
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
			item.appendChild(c6);
			item.appendChild(c7);
			item.appendChild(c72);
			item.appendChild(c8);
		}
	}

	// ������Ӱ�ť��������ӳɹ�����
	public void onClick$add() {
		AddFruitWin win = (AddFruitWin) Executions.createComponents(
				"/admin/personal/businessdata/fruit/addfruit.zul", null, null);
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
		if (fruitListbox.getSelectedItems() == null
				|| fruitListbox.getSelectedItems().size() <= 0) {
			try {
				Messagebox.show("��ѡ��Ҫɾ���ĳɹ���", "��ʾ", Messagebox.OK,
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
							Iterator<Listitem> items = fruitListbox
									.getSelectedItems().iterator();
							List<Jxkh_Fruit> fruitList = new ArrayList<Jxkh_Fruit>();
							Jxkh_Fruit fruit = new Jxkh_Fruit();
							while (items.hasNext()) {
								fruit = (Jxkh_Fruit) items.next().getValue();
								fruitList.add(fruit);
								if (!user.getKuLid()
										.equals(fruit.getSubmitId())) {
									try {
										Messagebox.show("���Ǳ����ύ�ĳɹ�����ɾ����", "��ʾ",
												Messagebox.OK,
												Messagebox.INFORMATION);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									return;
								}
								if (fruit.getState() == Jxkh_Fruit.DEPT_PASS
										|| fruit.getState() == Jxkh_Fruit.First_Dept_Pass
										|| fruit.getState() == Jxkh_Fruit.BUSINESS_PASS || fruit.getState().equals(JXKH_MEETING.BUSINESS_TEMP_PASS)
										|| fruit.getState() == Jxkh_Fruit.SAVE_RECORD) {
									try {
										Messagebox.show(
												"���Ż�ҵ������ͨ����鵵�ĳɹ�����ɾ����", "��ʾ",
												Messagebox.OK,
												Messagebox.INFORMATION);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									return;
								}
							}
							jxkhFruitService.deleteAll(fruitList);
							try {
								Messagebox.show("�ɹ�ɾ���ɹ���", "��ʾ", Messagebox.OK,
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

		List<Jxkh_Fruit> fruitList = jxkhFruitService.findFruitByKuLid(
				nameSearch, auditStateSearch, yearSearch, user.getKuLid());
		fruitListbox.setModel(new ListModelList(fruitList));
	}

	public void onClick$reset() {
		name.setValue("");
		auditState.getItemAtIndex(0).setSelected(true);
		year.getItemAtIndex(0).setSelected(true);
	}

	// ����
	public void onClick$expert() throws WriteException, IOException {
		if (fruitListbox.getSelectedCount() == 0) {
			try {
				Messagebox.show("��ʾ��ѡ��Ҫ����������", "��ʾ", Messagebox.OK,
						Messagebox.EXCLAMATION);
			} catch (InterruptedException e) {
				// ignore
			}
			return;
		} else {
			int i = 0;
			ArrayList<Jxkh_Fruit> expertlist = new ArrayList<Jxkh_Fruit>();
			@SuppressWarnings("unchecked")
			Set<Listitem> sel = fruitListbox.getSelectedItems();
			Iterator<Listitem> it = sel.iterator();
			while (it.hasNext()) {
				Listitem item = (Listitem) it.next();
				Jxkh_Fruit p = (Jxkh_Fruit) item.getValue();
				expertlist.add(i, p);
				i++;
			}
			Date now = new Date();
			String fileName = ConvertUtil.convertDateString(now)
					+ "chengguoxinxi" + ".xls";
			String Title = "�ɹ����";
			List<String> titlelist = new ArrayList<String>();
			titlelist.add("���");
			titlelist.add("�ɹ�����");
			titlelist.add("�����");
			titlelist.add("Ժ�ڲ���");
			titlelist.add("Ժ�ⲿ��");
			titlelist.add("�ɹ�ˮƽ");
			titlelist.add("������");
			titlelist.add("������ʽ");
			titlelist.add("��������");
			titlelist.add("��֯������λ");
			titlelist.add("���ּ�����λ");
			titlelist.add("���յȼ�");
			titlelist.add("���պ�");
			titlelist.add("������֯����");
			titlelist.add("��������");
			titlelist.add("��Ϣ��д��");
			String c[][] = new String[expertlist.size()][titlelist.size()];
			// ��SQL�ж�����
			for (int j = 0; j < expertlist.size(); j++) {
				Jxkh_Fruit fruit = (Jxkh_Fruit) expertlist.get(j);
				List<Jxkh_FruitMember> mlist = fruit.getFruitMember();
				String member = "";
				c[j][0] = j + 1 + "";
				c[j][1] = fruit.getName();
				if (mlist.size() != 0) {
					for (int m = 0; m < mlist.size(); m++) {
						Jxkh_FruitMember mem = (Jxkh_FruitMember) mlist.get(m);
						member += mem.getName() + "��";
					}
					c[j][2] = member.substring(0, member.length() - 1);
				}
				List<Jxkh_FruitDept> dlist = fruit.getFruitDept();
				String dept = "";
				if (dlist.size() != 0) {
					for (int m = 0; m < dlist.size(); m++) {
						Jxkh_FruitDept de = (Jxkh_FruitDept) dlist.get(m);
						dept += de.getName() + "��";
					}
					c[j][3] = dept.substring(0, dept.length() - 1);
				}
				c[j][4] = fruit.getCoCompany();
				if (fruit.getAppraiseRank() == null)
					c[j][5] = "";
				else
					c[j][5] = fruit.getAppraiseRank().getKbName();
				c[j][6] = fruit.getAppraiseCode();
				c[j][7] = fruit.getAppraiseType();
				c[j][8] = fruit.getAcceptDate();
				c[j][9] = fruit.getOrganAppraiseCompany();
				c[j][10] = fruit.getHoldAppraiseCompany();
				if (fruit.getAcceptRank() == null)
					c[j][11] = "";
				else
					c[j][11] = fruit.getAcceptRank().getKbName();
				c[j][12] = fruit.getAcceptCode();
				c[j][13] = fruit.getAcceptDetp();
				c[j][14] = fruit.getAcceptDate();
				c[j][15] = fruit.getSubmitName();
			}
			ExportExcel ee = new ExportExcel();
			ee.exportExcel(fileName, Title, titlelist, expertlist.size(), c);
		}
	}

}
