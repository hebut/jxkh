package org.iti.jxkh.busiAudit.fruit;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxkh.business.award.ChooseDeptWin;
import org.iti.jxkh.business.award.ChooseFruitWin;
import org.iti.jxkh.business.award.ChooseMemberWin;
import org.iti.jxkh.business.meeting.AssignDeptWindow;
import org.iti.jxkh.business.meeting.UpfileWindow;
import org.iti.jxkh.entity.Jxkh_Award;
import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.entity.Jxkh_Fruit;
import org.iti.jxkh.entity.Jxkh_FruitDept;
import org.iti.jxkh.entity.Jxkh_FruitFile;
import org.iti.jxkh.entity.Jxkh_FruitMember;
import org.iti.jxkh.service.JxkhAwardService;
import org.iti.jxkh.service.JxkhFruitService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Row;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.iti.common.util.ConvertUtil;
import com.iti.common.util.UploadUtil;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;

public class AddFruitWin extends Window implements AfterCompose {
	/**
	 * @author ZhangyuGuang
	 */
	private static final long serialVersionUID = 3298094030343482781L;
	private Tab baseTab, fileTab, scoreTab;
	private Listbox personScore, departmentScore;
	private Toolbarbutton save, close;
	private Textbox name, fruitMember, fruitDept, coCompany, appraiseCode, acceptCode, appraiseType, organAppraiseCompany, holdAppraiseCompany, acceptDept, record;
	private Listbox fruitRank, acceptRank, rate;
	private Radio cooperationTrue, cooperationFalse, firstSignTrue, firstSignFalse;
	private Row outDeptRow;
	private Button print;
	private YearListbox jiFenTime;
	private Hbox recordhbox;
	private Label recordlabel, submitName;
	private Datebox acceptDate, appraiseDate;
	private Jxkh_Fruit fruit;
	private List<WkTUser> memberList = new ArrayList<WkTUser>();
	private List<WkTDept> deptList = new ArrayList<WkTDept>();
	private List<Jxkh_FruitMember> fruitMemberList = new ArrayList<Jxkh_FruitMember>();
	private List<Jxkh_FruitDept> fruitDeptList = new ArrayList<Jxkh_FruitDept>();
	private JxkhFruitService jxkhFruitService;
	private JxkhAwardService jxkhAwardService;
	private WkTUser user;
	public static final Long appraise = (long) 35;
	public static final Long accept = (long) 37;
	public static final Long indent = (long) 110;
	private Listbox applyList1, applyList2, applyList3;

	private Toolbarbutton chooseMember, chooseDept;
	private boolean isChooseFruit = false;

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");// ��ȡ��ǰ��¼�û�
		// ����listbox��Ⱦ��
		fruitRank.setItemRenderer(new fruitRankRenderer());
		acceptRank.setItemRenderer(new receiveRankRenderer());
		personScore.setItemRenderer(new personScoreRenderer());
		departmentScore.setItemRenderer(new departmentScoreRenderer());
		rate.setItemRenderer(new indentRankRenderer());
		jiFenTime.initYearListbox("");
		// ���radio"cooperationTrue"�����¼�
		cooperationTrue.addEventListener(Events.ON_CHECK, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				outDeptRow.setVisible(true);
			}
		});
		// ���radio"cooperationFalse"�����¼�
		cooperationFalse.addEventListener(Events.ON_CHECK, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				outDeptRow.setVisible(false);
			}
		});

		// �������ҳ��ʱ������Ϣҳ����ĵ���Ϣҳ��ı�����˳���ť����
		scoreTab.addEventListener(Events.ON_CLICK, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				save.setVisible(false);
				close.setVisible(false);
			}
		});
		baseTab.addEventListener(Events.ON_CLICK, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				save.setVisible(true);
				close.setVisible(true);
			}
		});
		fileTab.addEventListener(Events.ON_CLICK, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				save.setVisible(true);
				close.setVisible(true);
			}
		});
	}

	public void initWindow() {
		String memberName = "";
		String deptName = "";
		if (fruit == null) {
			cooperationFalse.setChecked(true);
			submitName.setValue(user.getKuName());

		} else {
			jiFenTime.initYearListbox(fruit.getjxYear());
			print.setHref("/print.action?n=fruit&id=" + fruit.getId());
			name.setValue(fruit.getName());
			if (fruit.getFirstSign() == 1) {
				firstSignTrue.setChecked(true);
				firstSignFalse.setChecked(false);
			} else {
				firstSignTrue.setChecked(false);
				firstSignFalse.setChecked(true);
			}
			if (fruit.getCoState() == 1) {
				cooperationTrue.setChecked(true);
				cooperationFalse.setChecked(false);
				outDeptRow.setVisible(true);
				coCompany.setValue(fruit.getCoCompany());
			} else {
				cooperationTrue.setChecked(false);
				cooperationFalse.setChecked(true);
			}
			if (fruit.getState() == Jxkh_Fruit.SAVE_RECORD) {
				record.setVisible(true);
				recordlabel.setVisible(true);
				recordhbox.setVisible(true);
				record.setValue(fruit.getRecordCode());
			}
			if (fruit.getAcceptDate() != null)
				acceptDate.setValue(ConvertUtil.convertDate(fruit.getAcceptDate()));
			if (fruit.getAppraiseDate() != null)
				appraiseDate.setValue(ConvertUtil.convertDate(fruit.getAppraiseDate()));
			submitName.setValue(fruit.getSubmitName());
			appraiseCode.setValue(fruit.getAppraiseCode());
			appraiseType.setValue(fruit.getAppraiseType());
			organAppraiseCompany.setValue(fruit.getOrganAppraiseCompany());
			holdAppraiseCompany.setValue(fruit.getHoldAppraiseCompany());
			acceptCode.setValue(fruit.getAcceptCode());
			acceptDept.setValue(fruit.getAcceptDetp());
			fruitMemberList = fruit.getFruitMember();
			for (int i = 0; i < fruitMemberList.size(); i++) {
				Jxkh_FruitMember mem = (Jxkh_FruitMember) fruitMemberList.get(i);
				memberName += mem.getName() + "��";
				if (mem.getType() == 0) {
					WkTUser user = jxkhAwardService.findWktUserByMemberUserId(mem.getPersonId());
					memberList.add(user);
				} else {
					WkTUser out = new WkTUser();
					WkTDept d = new WkTDept();
					d.setKdName("У��");
					out.setDept(d);
					out.setKuId(new Random().nextLong());
					out.setKuLid("У��");
					out.setKuName(mem.getName());
					out.setType((short) 1);
					memberList.add(out);
				}
			}
			fruitMember.setValue(memberName.substring(0, memberName.length() - 1));
			fruitDeptList = fruit.getFruitDept();
			for (int i = 0; i < fruitDeptList.size(); i++) {
				Jxkh_FruitDept dept = (Jxkh_FruitDept) fruitDeptList.get(i);
				deptName += dept.getName() + "��";
				WkTDept depts = jxkhAwardService.findWkTDeptByDept(dept.getDeptId());
				deptList.add(depts);
			}
			fruitDept.setValue(deptName.substring(0, deptName.length() - 1));

			// ��ʼ�������б�
			arrList1.clear();
			arrList2.clear();
			arrList3.clear();
			Set<?> files = fruit.getFruitFile();
			Object[] file = files.toArray();
			for (int i = 0; i < file.length; i++) {
				Jxkh_FruitFile f = (Jxkh_FruitFile) file[i];
				if (f.getBelongType().equals("����͸��˳ɹ�֤��")) {
					String[] arr = new String[4];
					arr[0] = f.getPath();
					arr[1] = f.getName();
					arr[2] = f.getDate();
					arr[3] = f.getBelongType();
					arrList1.add(arr);
				}
				if (f.getBelongType().equals("���ղ���")) {
					String[] arr = new String[4];
					arr[0] = f.getPath();
					arr[1] = f.getName();
					arr[2] = f.getDate();
					arr[3] = f.getBelongType();
					arrList2.add(arr);
				}
				if (f.getBelongType().equals("����֤��")) {
					String[] arr = new String[4];
					arr[0] = f.getPath();
					arr[1] = f.getName();
					arr[2] = f.getDate();
					arr[3] = f.getBelongType();
					arrList3.add(arr);
				}
			}
			applyList1.setItemRenderer(new FilesRenderer1());
			applyList1.setModel(new ListModelList(arrList1));
			applyList2.setItemRenderer(new FilesRenderer2());
			applyList2.setModel(new ListModelList(arrList2));
			applyList3.setItemRenderer(new FilesRenderer3());
			applyList3.setModel(new ListModelList(arrList3));
		}
		initListbox();
	}

	private void initListbox() {
		List<Jxkh_BusinessIndicator> rankList = new ArrayList<Jxkh_BusinessIndicator>();
		rankList.add(new Jxkh_BusinessIndicator());
		rankList.addAll(jxkhAwardService.findRank(appraise));
		List<Jxkh_BusinessIndicator> acceptList = new ArrayList<Jxkh_BusinessIndicator>();
		acceptList.add(new Jxkh_BusinessIndicator());
		acceptList.addAll(jxkhAwardService.findRank(accept));
		List<Jxkh_BusinessIndicator> indentList = new ArrayList<Jxkh_BusinessIndicator>();
		indentList.add(new Jxkh_BusinessIndicator());
		indentList.addAll(jxkhAwardService.findRank(indent));
		fruitRank.setModel(new ListModelList(rankList));
		fruitRank.setSelectedIndex(0);

		acceptRank.setModel(new ListModelList(acceptList));
		acceptRank.setSelectedIndex(0);

		rate.setModel(new ListModelList(indentList));
		rate.setSelectedIndex(0);

		personScore.setModel(new ListModelList(fruitMemberList));
		departmentScore.setModel(new ListModelList(fruitDeptList));
	}

	public void onClick$save() {

		try {
			if (fruitMember.getValue() == null || fruitMember.getValue().equals("")) {
				Messagebox.show("��ѡ��ɹ���Ա��", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
				return;
			}
			if (fruitDept.getValue() == null || fruitDept.getValue().equals("")) {
				Messagebox.show("��ѡ��ɹ����ţ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
				return;
			}
			if (fruitRank.getSelectedItem().getIndex() == 0 && acceptRank.getSelectedItem().getIndex() == 0) {
				Messagebox.show("������Ϣ��������Ϣ������дһ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
				return;
			}
			if (rate.getSelectedItem().getIndex() != 0 && fruitRank.getSelectedItem().getIndex() == 0) {
				Messagebox.show("��ѡ��ɹ�ˮƽ��", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
				fruitRank.focus();
				return;
			}
			if (rate.getSelectedItem().getIndex() == 0 && fruitRank.getSelectedItem().getIndex() != 0) {
				Messagebox.show("��ѡ������ȼ���", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
				rate.focus();
				return;
			}
			if (fruitRank.getSelectedItem().getIndex() != 0 && (appraiseDate.getValue() == null || appraiseDate.getValue().equals(""))) {
				Messagebox.show("��ѡ��������ڣ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
				return;
			}
			if (acceptRank.getSelectedItem().getIndex() != 0 && (acceptDate.getValue() == null || acceptDate.getValue().equals(""))) {
				Messagebox.show("��ѡ���������ڣ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
				return;
			}
			if (jiFenTime.getSelectedIndex() == 0 || jiFenTime.getSelectedItem() == null) {
				try {
					Messagebox.show("��ѡ�񼨷���ȣ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return;
			}
			/**
			 * �ж������ջ��Ǽ���������Ǽ������򡰳ɹ�֤�顱�����������ϡ�Ϊ�������������գ������ղ��ϡ�Ϊ�Ǳ�����
			 */
			if (fruit != null && fruit.getId() != null) {
				// ����Ǽ��������жϡ��ɹ�֤�顱�����������ϡ�Ϊ������
				if (fruitRank.getSelectedItem().getIndex() != 0) {
					if (arrList1.size() <= 0) {
						Messagebox.show("����͸��˳ɹ�֤������ϴ���");
						return;
					}
				}
				// ��������գ����жϡ����ղ��ϡ�Ϊ�Ǳ�����
				if (acceptRank.getSelectedItem().getIndex() != 0) {
					if (arrList3.size() <= 0) {
						Messagebox.show("����֤������ϴ���");
						return;
					}
				}
			}
			if (fruit == null) {
				fruit = new Jxkh_Fruit();
			}
			if (isChooseFruit) {
				fruit.setId(null);
				fruit.setFruitDept(null);
				fruit.setFruitMember(null);
			}
			setEntity();
			jxkhFruitService.saveOrUpdate(fruit);
			Messagebox.show("�ɹ�����ɹ���", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			List<Jxkh_FruitMember> mlist = fruit.getFruitMember();
			List<Jxkh_FruitDept> dlist = fruit.getFruitDept();
			personScore.setModel(new ListModelList(mlist));
			departmentScore.setModel(new ListModelList(dlist));
		} catch (Exception e) {
			e.printStackTrace();
			try {
				Messagebox.show("�ɹ�����ʧ�ܣ������ԣ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void setEntity() {
		fruit.setName(name.getValue());
		fruit.setjxYear(jiFenTime.getSelectedItem().getValue() + "");
		if (fruitRank.getSelectedItem().getIndex() != 0) {
			fruit.setAppraiseRank((Jxkh_BusinessIndicator) fruitRank.getSelectedItem().getValue());
		} else {
			fruit.setAppraiseRank(null);
		}
		if (acceptRank.getSelectedItem().getIndex() != 0) {
			fruit.setAcceptRank((Jxkh_BusinessIndicator) acceptRank.getSelectedItem().getValue());
		} else {
			fruit.setAcceptRank(null);
		}
		if (rate.getSelectedItem().getIndex() != 0) {
			fruit.setIndentRank((Jxkh_BusinessIndicator) rate.getSelectedItem().getValue());
		} else {
			fruit.setIndentRank(null);
		}
		if (firstSignTrue.isChecked()) {
			fruit.setFirstSign(Jxkh_Award.YES);
		} else {
			fruit.setFirstSign(Jxkh_Award.NO);
		}
		if (cooperationTrue.isChecked()) {
			fruit.setCoState(Jxkh_Fruit.YES);
			fruit.setCoCompany(coCompany.getValue());
		} else {
			fruit.setCoState(Jxkh_Fruit.NO);
			fruit.setCoCompany(null);
		}
		// fruit.setState(Jxkh_Fruit.NOT_AUDIT);
		if (appraiseDate.getValue() != null && !appraiseDate.getValue().equals(""))
			fruit.setAppraiseDate(ConvertUtil.convertDateString(appraiseDate.getValue()));
		if (acceptDate.getValue() != null && !acceptDate.getValue().equals(""))
			fruit.setAcceptDate(ConvertUtil.convertDateString(acceptDate.getValue()));
		fruit.setAppraiseCode(appraiseCode.getValue());
		fruit.setAppraiseType(appraiseType.getValue());
		fruit.setOrganAppraiseCompany(organAppraiseCompany.getValue());
		fruit.setHoldAppraiseCompany(holdAppraiseCompany.getValue());
		fruit.setAcceptCode(acceptCode.getValue());
		fruit.setAcceptDetp(acceptDept.getValue());
		fruit.setRecordCode(record.getValue());
		int j = 1;
		List<Jxkh_FruitMember> mlist = new ArrayList<Jxkh_FruitMember>();
		List<Jxkh_FruitDept> dlist = new ArrayList<Jxkh_FruitDept>();
		if (fruit.getFruitMember() != null) {
			mlist = fruit.getFruitMember();
			jxkhFruitService.deleteAll(mlist);
			mlist.clear();
		}
		for (WkTUser user : memberList) {
			Jxkh_FruitMember member = new Jxkh_FruitMember();
			member.setFruit(fruit);
			member.setDept(user.getDept().getKdName());
			member.setName(user.getKuName());
			member.setPersonId(user.getKuLid());
			member.setRank(j);
			switch (user.getType()) {
			case WkTUser.TYPE_IN:
				member.setType(Short.valueOf(WkTUser.TYPE_IN));
				break;
			case WkTUser.TYPE_OUT:
				member.setType(Short.valueOf(WkTUser.TYPE_OUT));
				break;
			}

			// ��Ա�ı����ͷ�ֵ0503
			float percent = 0;
			int len = memberList.size();
			switch (len) {
			case 1:
				percent = 10;
				break;
			case 2:
				if (j == 1)
					percent = 7;
				else if (j == 2)
					percent = 3;
				break;
			case 3:
				if (j == 1)
					percent = 6;
				else if (j == 2)
					percent = 3;
				else if (j == 3)
					percent = 1;
				break;
			case 4:
				if (j == 1)
					percent = 5;
				else if (j == 2)
					percent = 3;
				else if (j == 3)
					percent = 1;
				else if (j == 4)
					percent = 1;
				break;
			case 5:
				if (j == 1)
					percent = 5;
				else if (j == 2)
					percent = 2;
				else if (j == 3)
					percent = 1;
				else if (j == 4)
					percent = 1;
				else if (j == 5)
					percent = 1;
				break;
			case 6:
				if (j == 1)
					percent = 5;
				else if (j == 2)
					percent = 2;
				else if (j == 3)
					percent = 1;
				else if (j == 4)
					percent = 1;
				else if (j == 5)
					percent = 0.5f;
				else if (j == 6)
					percent = 0.5f;
				break;
			case 7:
				if (j == 1)
					percent = 5;
				else if (j == 2)
					percent = 1.5f;
				else if (j == 3)
					percent = 1;
				else if (j == 4)
					percent = 1;
				else if (j == 5)
					percent = 0.5f;
				else if (j == 6)
					percent = 0.5f;
				else if (j == 7)
					percent = 0.5f;
				break;
			default:
				if (j == 1)
					percent = 5;
				else if (j == 2)
					percent = 1.5f;
				else if (j == 3)
					percent = 1;
				else if (j == 4)
					percent = 1;
				else if (j == 5)
					percent = 0.5f;
				else if (j == 6)
					percent = 0.5f;
				else if (j == 7)
					percent = 0.5f;
				else
					percent = 0;
				break;

			}

			// ��Ҫ�ļ����ȼ��ͳɹ��ȼ����ȼ���ǩ�����޸�

			Jxkh_BusinessIndicator ftRank = new Jxkh_BusinessIndicator();
			Jxkh_BusinessIndicator ftIndent = new Jxkh_BusinessIndicator();
			Jxkh_BusinessIndicator atRank = new Jxkh_BusinessIndicator();
			if (this.fruitRank.getSelectedItem() != null) {
				ftRank = (Jxkh_BusinessIndicator) this.fruitRank.getSelectedItem().getValue();
			}
			if (this.rate.getSelectedItem() != null) {
				ftIndent = (Jxkh_BusinessIndicator) this.rate.getSelectedItem().getValue();
			}
			if (this.acceptRank.getSelectedItem() != null) {
				atRank = (Jxkh_BusinessIndicator) this.acceptRank.getSelectedItem().getValue();
			}
			float score = computeScore(ftRank, ftIndent, atRank);
			fruit.setScore(score);
			float f = 0;
			if (percent != 0)
				f = score * percent / 10;
			float sco = (float) (Math.round(f * 1000)) / 1000;// ������λС��
			member.setPer(percent);
			member.setScore(sco);
			if ((user.getKdId() != null && user.getDept() != null && user.getDept().getKdNumber().equals(WkTDept.guanlidept)) || user.getKdId() == null)
				member.setAssignDep(deptList.get(0).getKdName());
			else
				member.setAssignDep(user.getDept().getKdName());
			j++;
			mlist.add(member);
		}
		fruit.setFruitMember(mlist);
		int i = 1;
		if (fruit.getFruitDept() != null) {
			dlist = fruit.getFruitDept();
			jxkhFruitService.deleteAll(dlist);
			dlist.clear();
		}
		for (WkTDept wktDept : deptList) {
			Jxkh_FruitDept dept = new Jxkh_FruitDept();
			dept.setFruit(fruit);
			dept.setName(wktDept.getKdName());
			dept.setDeptId(wktDept.getKdNumber());
			dept.setRank(i);
			i++;
			// ����Ĭ�ϵķ�ֵ0503
			float fen = 0.0f;
			for (int g = 0; g < mlist.size(); g++) {
				Jxkh_FruitMember m = mlist.get(g);
				if (m.getAssignDep().equals(dept.getName())) {
					fen += m.getScore();
				}
			}
			float scor = (float) (Math.round(fen * 1000)) / 1000;// ������λС��
			dept.setScore(scor);
			dlist.add(dept);
		}
		fruit.setFruitDept(dlist);
		// �µĸ������棨20120306��
		List<String[]> arrList = new ArrayList<String[]>();
		Set<Jxkh_FruitFile> fset = new HashSet<Jxkh_FruitFile>();
		Set<Jxkh_FruitFile> oldFile = new HashSet<Jxkh_FruitFile>();
		oldFile = fruit.getFruitFile();
		if (oldFile != null) {
			for (Object o : oldFile.toArray()) {
				Jxkh_FruitFile f = (Jxkh_FruitFile) o;
				if (f != null)
					jxkhFruitService.delete(f);
			}
		}
		if (fruit.getFruitFile() != null)
			fruit.getFruitFile().clear();
		if (arrList1.size() != 0 || arrList1 != null)
			arrList.addAll(arrList1);
		if (arrList2.size() != 0 || arrList2 != null)
			arrList.addAll(arrList2);
		if (arrList3.size() != 0 || arrList3 != null)
			arrList.addAll(arrList3);
		for (int r = 0; r < arrList.size(); r++) {
			String[] s = arrList.get(r);
			Jxkh_FruitFile file = new Jxkh_FruitFile();
			file.setFruit(fruit);
			file.setPath(s[0]);
			file.setName(s[1]);
			file.setDate(s[2]);
			file.setBelongType(s[3]);
			fset.add(file);
		}
		fruit.setFruitFile(fset);
	}

	/*
	 * public void onClick$chooseProject() throws SuspendNotAllowedException,
	 * InterruptedException { final ChooserProjectWin win = (ChooserProjectWin)
	 * Executions .createComponents(
	 * "/admin/jxkh/busiAudit/fruit/chooseProject.zul", null, null);
	 * win.addEventListener(Events.ON_CHANGE, new EventListener() { public void
	 * onEvent(Event arg0) throws Exception { project = win.getProject();
	 * name.setValue(project.getName()); win.detach(); } }); win.doModal();
	 * 
	 * }
	 */

	// ����ѡ���Ա��ť����������ѡ���Աҳ���¼�
	public void onClick$chooseMember() throws SuspendNotAllowedException, InterruptedException {
		final ChooseMemberWin win = (ChooseMemberWin) Executions.createComponents("/admin/jxkh/busiAudit/award/choosemember.zul", null, null);
		win.setMemberList(memberList);
		win.initWindow();
		win.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				memberList = win.getMemberList();
				String members = "";
				for (WkTUser user : memberList) {
					members += user.getKuName() + ",";
				}
				if (members.endsWith(",")) {
					members = members.substring(0, members.length() - 1);
				}
				fruitMember.setValue(members);
				win.detach();
			}
		});
		win.doModal();

		// ����ѡ�����Ա�������Զ���֮��Ӧ ��2012��03��07�š�
		deptList.clear();
		for (int i = 0; i < memberList.size(); i++) {
			int k = 0;// ����Ա���ڵĲ��ź�ǰ���ظ���k=1
			WkTUser ui = (WkTUser) memberList.get(i);
			for (int j = 0; j < i; j++) {
				WkTUser uj = (WkTUser) memberList.get(j);
				if (ui.getDept().getKdId().equals(uj.getDept().getKdId())) {
					k = 1;
				}
			}
			if (k == 0 && ui.getType() != 1)
				deptList.add(ui.getDept());
		}
		String depts = "";
		for (WkTDept dept : deptList) {
			depts += dept.getKdName() + ",";
		}
		if (depts.endsWith(",")) {
			depts = depts.substring(0, depts.length() - 1);
		}
		fruitDept.setValue(null);
		fruitDept.setValue(depts);
	}

	// ����ѡ���Ű�ť����������ѡ���Աҳ���¼�
	public void onClick$chooseDept() throws SuspendNotAllowedException, InterruptedException {
		final ChooseDeptWin win = (ChooseDeptWin) Executions.createComponents("/admin/jxkh/busiAudit/award/choosedept.zul", null, null);
		win.setDeptList(deptList);
		win.initWindow();
		win.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				deptList = win.getDeptList();
				String depts = "";
				for (WkTDept dept : deptList) {
					depts += dept.getKdName() + ",";
				}
				if (depts.endsWith(",")) {
					depts = depts.substring(0, depts.length() - 1);
				}
				fruitDept.setValue(depts);
				win.detach();
			}
		});
		win.doModal();
	}

	public Jxkh_Fruit getFruit() {
		return fruit;
	}

	public void setFruit(Jxkh_Fruit fruit) {
		this.fruit = fruit;
	}

	// �ɹ�ˮƽ��Ⱦ��
	public class fruitRankRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			Jxkh_BusinessIndicator fruitRank = (Jxkh_BusinessIndicator) data;
			item.setValue(fruitRank);
			Listcell c0 = new Listcell();
			if (fruitRank.getKbName() == null) {
				c0.setLabel("--��ѡ��--");
			} else {
				c0.setLabel(fruitRank.getKbName());
			}
			item.appendChild(c0);
			if (item.getIndex() == 0)
				item.setSelected(true);
			if (fruit != null && fruitRank.equals(fruit.getAppraiseRank())) {
				item.setSelected(true);
			}
		}
	}

	public class receiveRankRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			Jxkh_BusinessIndicator acceptRank = (Jxkh_BusinessIndicator) data;
			item.setValue(acceptRank);
			Listcell c0 = new Listcell();
			if (acceptRank.getKbName() == null) {
				c0.setLabel("--��ѡ��--");
			} else {
				c0.setLabel(acceptRank.getKbName());
			}
			item.appendChild(c0);
			if (item.getIndex() == 0)
				item.setSelected(true);
			if (fruit != null && acceptRank.equals(fruit.getAcceptRank())) {
				item.setSelected(true);
			}
		}
	}

	// ��������
	public class indentRankRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			Jxkh_BusinessIndicator indentRank = (Jxkh_BusinessIndicator) data;
			item.setValue(indentRank);
			Listcell c0 = new Listcell();
			if (indentRank.getKbName() == null) {
				c0.setLabel("--��ѡ��--");
			} else {
				c0.setLabel(indentRank.getKbName());
			}
			item.appendChild(c0);
			if (item.getIndex() == 0)
				item.setSelected(true);
			if (fruit != null && indentRank.equals(fruit.getIndentRank())) {
				item.setSelected(true);
			}
		}
	}

	/**
	 * <li>�����������رյ�ǰ���ڡ�
	 */
	public void onClick$close() {
		this.onClose();
	}

	// 20120306
	private List<String[]> arrList1 = new ArrayList<String[]>();

	public void onClick$ups1() {
		final UpfileWindow w = (UpfileWindow) Executions.createComponents("/admin/jxkh/busiAudit/meeting/upfile.zul", null, null);
		w.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				String filePath = (String) Executions.getCurrent().getAttribute("path");
				String fileName = (String) Executions.getCurrent().getAttribute("title");
				String upTime = (String) Executions.getCurrent().getAttribute("upTime");
				applyList1.setItemRenderer(new FilesRenderer1());
				String[] arr = new String[4];
				arr[0] = filePath;
				arr[1] = fileName;
				arr[2] = upTime;
				arr[3] = "����͸��˳ɹ�֤��";
				arrList1.add(arr);
				applyList1.setModel(new ListModelList(arrList1));
			}
		});
		try {
			w.doModal();
		} catch (SuspendNotAllowedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <li>�����������ĵ���Ϣ��listbox(20120306)
	 */
	public class FilesRenderer1 implements ListitemRenderer {

		@Override
		public void render(Listitem arg0, Object arg1) throws Exception {
			final String[] str = (String[]) arg1;
			arg0.setValue(str);
			Listcell c1 = new Listcell(arg0.getIndex() + 1 + "");
			Listcell c2 = new Listcell(str[1]);
			Listcell c3 = new Listcell(str[2]);
			Listcell c4 = new Listcell();
			Toolbarbutton downlowd = new Toolbarbutton();
			downlowd.setImage("/css/default/images/button/down.gif");
			downlowd.setParent(c4);
			downlowd.addEventListener(Events.ON_CLICK, new EventListener() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					download(str[0], str[1]);
				}
			});
			Toolbarbutton del = new Toolbarbutton();
			del.setImage("/css/default/images/button/del.gif");
			del.setParent(c4);
			del.addEventListener(Events.ON_CLICK, new EventListener() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					Messagebox.show("ȷ��ɾ����?", "ȷ��", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
						public void onEvent(Event evt) throws InterruptedException {
							if (evt.getName().equals("onOK")) {
								arrList1.remove(str);
								applyList1.setModel(new ListModelList(arrList1));
							}
						}
					});
				}
			});
			arg0.appendChild(c1);
			arg0.appendChild(c2);
			arg0.appendChild(c3);
			arg0.appendChild(c4);
		}
	}

	// 20120306
	private List<String[]> arrList2 = new ArrayList<String[]>();

	public void onClick$ups2() {
		final UpfileWindow w = (UpfileWindow) Executions.createComponents("/admin/jxkh/busiAudit/meeting/upfile.zul", null, null);
		w.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				String filePath = (String) Executions.getCurrent().getAttribute("path");
				String fileName = (String) Executions.getCurrent().getAttribute("title");
				String upTime = (String) Executions.getCurrent().getAttribute("upTime");
				applyList2.setItemRenderer(new FilesRenderer2());
				String[] arr = new String[4];
				arr[0] = filePath;
				arr[1] = fileName;
				arr[2] = upTime;
				arr[3] = "���ղ���";
				arrList2.add(arr);
				applyList2.setModel(new ListModelList(arrList2));
			}
		});
		try {
			w.doModal();
		} catch (SuspendNotAllowedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <li>�����������ĵ���Ϣ��listbox(20120306)
	 */
	public class FilesRenderer2 implements ListitemRenderer {

		@Override
		public void render(Listitem arg0, Object arg1) throws Exception {
			final String[] str = (String[]) arg1;
			arg0.setValue(str);
			Listcell c1 = new Listcell(arg0.getIndex() + 1 + "");
			Listcell c2 = new Listcell(str[1]);
			Listcell c3 = new Listcell(str[2]);
			Listcell c4 = new Listcell();
			Toolbarbutton downlowd = new Toolbarbutton();
			downlowd.setImage("/css/default/images/button/down.gif");
			downlowd.setParent(c4);
			downlowd.addEventListener(Events.ON_CLICK, new EventListener() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					download(str[0], str[1]);
				}
			});
			Toolbarbutton del = new Toolbarbutton();
			del.setImage("/css/default/images/button/del.gif");
			del.setParent(c4);
			del.addEventListener(Events.ON_CLICK, new EventListener() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					Messagebox.show("ȷ��ɾ����?", "ȷ��", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
						public void onEvent(Event evt) throws InterruptedException {
							if (evt.getName().equals("onOK")) {
								arrList2.remove(str);
								applyList2.setModel(new ListModelList(arrList2));
							}
						}
					});
				}
			});
			arg0.appendChild(c1);
			arg0.appendChild(c2);
			arg0.appendChild(c3);
			arg0.appendChild(c4);
		}
	}

	// 20120306
	private List<String[]> arrList3 = new ArrayList<String[]>();

	public void onClick$ups3() {
		final UpfileWindow w = (UpfileWindow) Executions.createComponents("/admin/jxkh/busiAudit/meeting/upfile.zul", null, null);
		w.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				String filePath = (String) Executions.getCurrent().getAttribute("path");
				String fileName = (String) Executions.getCurrent().getAttribute("title");
				String upTime = (String) Executions.getCurrent().getAttribute("upTime");
				applyList3.setItemRenderer(new FilesRenderer3());
				String[] arr = new String[4];
				arr[0] = filePath;
				arr[1] = fileName;
				arr[2] = upTime;
				arr[3] = "����֤��";
				arrList3.add(arr);
				applyList3.setModel(new ListModelList(arrList3));
			}
		});
		try {
			w.doModal();
		} catch (SuspendNotAllowedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <li>�����������ĵ���Ϣ��listbox(20120306)
	 */
	public class FilesRenderer3 implements ListitemRenderer {

		@Override
		public void render(Listitem arg0, Object arg1) throws Exception {
			final String[] str = (String[]) arg1;
			arg0.setValue(str);
			Listcell c1 = new Listcell(arg0.getIndex() + 1 + "");
			Listcell c2 = new Listcell(str[1]);
			Listcell c3 = new Listcell(str[2]);
			Listcell c4 = new Listcell();
			Toolbarbutton downlowd = new Toolbarbutton();
			downlowd.setImage("/css/default/images/button/down.gif");
			downlowd.setParent(c4);
			downlowd.addEventListener(Events.ON_CLICK, new EventListener() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					download(str[0], str[1]);
				}
			});
			Toolbarbutton del = new Toolbarbutton();
			del.setImage("/css/default/images/button/del.gif");
			del.setParent(c4);
			del.addEventListener(Events.ON_CLICK, new EventListener() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					Messagebox.show("ȷ��ɾ����?", "ȷ��", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
						public void onEvent(Event evt) throws InterruptedException {
							if (evt.getName().equals("onOK")) {
								arrList3.remove(str);
								applyList3.setModel(new ListModelList(arrList3));
							}
						}
					});
				}
			});
			arg0.appendChild(c1);
			arg0.appendChild(c2);
			arg0.appendChild(c3);
			arg0.appendChild(c4);
		}
	}

	public void download(String fpath, String fname) throws InterruptedException {
		File file = new File(UploadUtil.getRootPath() + fpath);
		if (file.exists()) {
			try {
				Filedownload.save(file, fname);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			Messagebox.show("�޷����أ���������Ϊ�ļ�û�б��ϴ��� ", "����", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/** ���˼�����Ⱦ�� */
	public class personScoreRenderer implements ListitemRenderer {

		@Override
		public void render(Listitem item, Object data) throws Exception {

			if (data == null)
				return;
			final Jxkh_FruitMember member = (Jxkh_FruitMember) data;
			item.setValue(member);
			Listcell c1 = new Listcell(item.getIndex() + 1 + "");
			Listcell c2 = new Listcell(member.getName());
			Listcell c3 = new Listcell();
			if (member.getType() == 1) {
				c3 = new Listcell("Ժ��");
			} else {
				c3 = new Listcell("Ժ��");
			}
			Listcell c4 = new Listcell();
			if (member.getType() == 1) {
				c4 = new Listcell("Ժ��");
			} else {
				c4 = new Listcell(member.getDept());
			}
			Listcell c5 = new Listcell();
			c5.setTooltiptext("����������");
			Textbox t = new Textbox();
			t.setParent(c5);
			if (member.getPer() != null)
				t.setValue(member.getPer() + "");
			Listcell c6 = new Listcell();
			Toolbarbutton bar = new Toolbarbutton();
			if (member.getAssignDep() == null || member.getAssignDep().equals("")) {
				bar.setLabel("ָ��");
				bar.setStyle("color:blue");
			} else {
				bar.setLabel(member.getAssignDep());
			}
			c6.appendChild(bar);
			Listcell c7 = new Listcell();
			if (member.getScore() != null)
				c7.setLabel(member.getScore() + "");

			final List<Jxkh_FruitDept> temp = jxkhFruitService.findFruitDepByFruitId(fruit);
			// ����ָ������ҳ��
			bar.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event event) throws Exception {

					AssignDeptWindow w = (AssignDeptWindow) Executions.createComponents("/admin/jxkh/busiAudit/meeting/assignDept.zul", null, null);
					try {
						w.setFlag("FRUIT");
						w.setDept(temp);
						w.setMember(member);
						w.initWindow();
						w.doModal();
						// ָ��������ɺ��Զ�����÷�
						w.addEventListener(Events.ON_CHANGE, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								List<Jxkh_FruitMember> tempMemberList = fruit.getFruitMember();
								personScore.setModel(new ListModelList(tempMemberList));
								for (int k = 0; k < fruitDeptList.size(); k++) {
									Jxkh_FruitDept d = fruitDeptList.get(k);
									float f = 0.0f;
									for (int i = 0; i < tempMemberList.size(); i++) {
										Jxkh_FruitMember m = tempMemberList.get(i);
										if (m.getAssignDep() == null || m.getAssignDep().equals("")) {
											if (m.getDept().equals(d.getName())) {
												f += m.getScore();
											}
										} else if (m.getAssignDep().equals(d.getName())) {
											f += m.getScore();
										}
									}
									d.setScore(f);
									jxkhFruitService.update(d);
								}
								List<Jxkh_FruitDept> tempDeptList = fruit.getFruitDept();
								departmentScore.setModel(new ListModelList(tempDeptList));
							}
						});
					} catch (SuspendNotAllowedException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
			item.appendChild(c1);
			item.appendChild(c2);
			item.appendChild(c3);
			item.appendChild(c4);
			item.appendChild(c5);
			item.appendChild(c6);
			item.appendChild(c7);
		}
	}

	/** ���ż����б���Ⱦ�� */
	public class departmentScoreRenderer implements ListitemRenderer {

		@Override
		public void render(Listitem arg0, Object arg1) throws Exception {
			if (arg0 == null)
				return;
			final Jxkh_FruitDept dept = (Jxkh_FruitDept) arg1;
			arg0.setValue(dept);
			Listcell c1 = new Listcell(arg0.getIndex() + 1 + "");
			Listcell c2 = new Listcell(dept.getName());
			Listcell c3 = new Listcell();
			if (dept.getScore() != null)
				c3.setLabel(dept.getScore() + "");
			arg0.appendChild(c1);
			arg0.appendChild(c2);
			arg0.appendChild(c3);
		}
	}

	// ������Ϣ�ı��水ť
	public void onClick$submitScore() {
		float a = 0.0f;
		for (int i = 0; i < fruitMemberList.size(); i++) {
			Listitem item = personScore.getItemAtIndex(i);
			Listcell lc = (Listcell) item.getChildren().get(4);
			Textbox temp = (Textbox) lc.getChildren().get(0);
			if (temp.getValue() != null && temp.getValue() != "")
				try {
					a += Float.parseFloat(temp.getValue());
				} catch (Exception e) {
					e.printStackTrace();
					try {
						Messagebox.show("ֻ���������֣�", "��ʾ", Messagebox.OK, Messagebox.EXCLAMATION);
						return;
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}

		}
		if (a != 10.0) {
			try {
				Messagebox.show("������Ա��ռ�������ܺ��Ƿ�Ϊ10.0��", "��ʾ", Messagebox.OK, Messagebox.EXCLAMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		for (int i = 0; i < fruitMemberList.size(); i++) {
			Listitem item = personScore.getItemAtIndex(i);
			Listcell lc = (Listcell) item.getChildren().get(4);
			Textbox temp = (Textbox) lc.getChildren().get(0);
			float s = 0.0f;// ����
			float f = 0.0f;// ��ֵ
			if (temp.getValue() != null && temp.getValue() != "")
				s = Float.parseFloat(temp.getValue());
			if (fruit.getScore() != null)
				f = s * fruit.getScore() / 10;
			float score = (float) (Math.round(f * 1000)) / 1000;
			Jxkh_FruitMember member = (Jxkh_FruitMember) item.getValue();
			member.setPer(s);
			member.setScore(score);
			jxkhFruitService.update(member);
		}
		List<Jxkh_FruitMember> tempMemberList = fruit.getFruitMember();
		personScore.setModel(new ListModelList(tempMemberList));
		for (int k = 0; k < fruitDeptList.size(); k++) {
			Jxkh_FruitDept d = fruitDeptList.get(k);
			float f = 0.0f;
			for (int i = 0; i < tempMemberList.size(); i++) {
				Jxkh_FruitMember m = tempMemberList.get(i);
				if (m.getAssignDep() == null || m.getAssignDep().equals("")) {
					if (m.getDept().equals(d.getName())) {
						f += m.getScore();
					}
				} else if (m.getAssignDep().equals(d.getName())) {
					f += m.getScore();
				}
			}
			d.setScore(f);
			jxkhFruitService.update(d);
		}
		try {
			Messagebox.show("������Ϣ����ɹ���", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Jxkh_FruitDept> tempDeptList = fruit.getFruitDept();
		departmentScore.setModel(new ListModelList(tempDeptList));
	}

	public Float computeScore(Jxkh_BusinessIndicator ftRank, Jxkh_BusinessIndicator ftIndent, Jxkh_BusinessIndicator atRank) {
		float ftRankScore = 0f, atRankScore = 0f, result = 0f;
		List<Jxkh_BusinessIndicator> rankList = jxkhAwardService.findRank(appraise);
		List<Jxkh_BusinessIndicator> acceptList = jxkhAwardService.findRank(accept);
		if (cooperationTrue.isChecked() && firstSignFalse.isChecked()) {
			// Ժ��������Ҳ��ǵ�һ��������Ҫ����
			// �����������
			if (ftIndent.getKbId() != null) {
				if (ftIndent.getKbValue().equals(Jxkh_Fruit.GJJJD)) {
					// ���Ҽ�����
					Jxkh_BusinessIndicator bi = rankList.get(1);
					ftRankScore = bi.getKbIndex() * bi.getKbScore();
				} else if (ftIndent.getKbValue().equals(Jxkh_Fruit.SJJD)) {
					// ʡ�����������м�����
					if (ftRank.getKbValue().equals(rankList.get(1).getKbValue())) {
						ftRankScore = ftRank.getKbIndex() * ftRank.getKbScore();
					} else {
						Jxkh_BusinessIndicator bi = rankList.get(1);
						ftRankScore = bi.getKbIndex() * bi.getKbScore() * 0.5f;
					}
				} else if (ftIndent.getKbValue().equals(Jxkh_Fruit.TJJD)) {
					int index = rankList.indexOf(ftRank);
					if (index == -1) {

					} else if (index == rankList.size() - 1) {
						ftRankScore = ftRank.getKbIndex() * ftRank.getKbScore() * 0.5f;
					} else {
						Jxkh_BusinessIndicator bi = rankList.get(index + 1);
						ftRankScore = bi.getKbIndex() * bi.getKbScore();
					}
				}
			}
			// �������ջ���
			if (atRank.getKbId() != null) {
				int index = acceptList.indexOf(atRank);
				if (index == -1) {

				} else if (index == acceptList.size() - 1) {
					atRankScore = atRank.getKbIndex() * atRank.getKbScore() * 0.5f;
				} else {
					Jxkh_BusinessIndicator bi = acceptList.get(index + 1);
					atRankScore = bi.getKbIndex() * bi.getKbScore();
				}
			}
			if (this.isChooseFruit) {
				result = (atRankScore - ftRankScore) > 0 ? atRankScore - ftRankScore : 0;
			} else {
				result = Math.max(atRankScore, ftRankScore);
			}
			fruit.setComputeType(result == atRankScore ? ftRank.getKbId() : atRank.getKbId());
		} else {
			// ��һ��������Ҫ����
			// �����������
			if (ftIndent.getKbId() != null) {
				if (ftIndent.getKbValue().equals(Jxkh_Fruit.GJJJD)) {
					// ���Ҽ�����
					Jxkh_BusinessIndicator bi = rankList.get(0);
					ftRankScore = bi.getKbIndex() * bi.getKbScore();
				} else if (ftIndent.getKbValue().equals(Jxkh_Fruit.SJJD)) {
					// ʡ�����������м�����
					if (ftRank.getKbValue().equals(rankList.get(0).getKbValue())) {
						ftRankScore = ftRank.getKbIndex() * ftRank.getKbScore();
					} else {
						Jxkh_BusinessIndicator bi = rankList.get(1);
						ftRankScore = bi.getKbIndex() * bi.getKbScore();
					}
				} else if (ftIndent.getKbValue().equals(Jxkh_Fruit.TJJD)) {
					ftRankScore = ftRank.getKbIndex() * ftRank.getKbScore();
				}
			}
			// �������ջ���
			if (atRank.getKbId() != null) {
				atRankScore = atRank.getKbIndex() * atRank.getKbScore();
			}
			if (this.isChooseFruit) {
				result = (atRankScore - ftRankScore) > 0 ? atRankScore - ftRankScore : 0;
			} else {
				result = Math.max(atRankScore, ftRankScore);
			}
			fruit.setComputeType(result == atRankScore ? ftRank.getKbId() : atRank.getKbId());
		}
		return result;
	}

	// ����ѡ��ɹ���ť����������ѡ���Աҳ���¼�
	public void onClick$chooseFruit() throws SuspendNotAllowedException, InterruptedException {
		final ChooseFruitWin win = (ChooseFruitWin) Executions.createComponents("/admin/jxkh/busiAudit/fruit/choosefruit.zul", null, null);
		win.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				fruit = win.getFruit();
				isChooseFruit = true;
				initWindow();
				initComponent();
				win.detach();
			}
		});
		win.doModal();
	}

	/**
	 * ѡ��ɹ�ʱ��ʼ��ҳ��������Ѿ���ֵ�Ĳ����޸�
	 */
	public void initComponent() {
		this.name.setDisabled(true);
		this.chooseDept.setDisabled(true);
		this.chooseMember.setDisabled(true);
		this.cooperationFalse.setDisabled(true);
		this.cooperationTrue.setDisabled(true);
		this.firstSignFalse.setDisabled(true);
		this.firstSignTrue.setDisabled(true);
		this.coCompany.setDisabled(true);
		this.fruitRank.setDisabled(true);
		if (this.appraiseCode.getValue() != null || !this.appraiseCode.getValue().equals("")) {
			this.appraiseCode.setDisabled(true);
		}
		if (this.appraiseType.getValue() != null || !this.appraiseType.getValue().equals("")) {
			this.appraiseType.setDisabled(true);
		}
		this.appraiseDate.setDisabled(true);
		if (this.organAppraiseCompany.getValue() != null || !this.organAppraiseCompany.getValue().equals("")) {
			this.organAppraiseCompany.setDisabled(true);
		}
		if (this.holdAppraiseCompany.getValue() != null || !this.holdAppraiseCompany.getValue().equals("")) {
			this.holdAppraiseCompany.setDisabled(true);
		}
		this.rate.setDisabled(true);
		this.scoreTab.setDisabled(true);
	}

	// ������Ϣ�Ĺرհ�ť
	public void onClick$closeScore() {
		this.onClose();
	}
}
