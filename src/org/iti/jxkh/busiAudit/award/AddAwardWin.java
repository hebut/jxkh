package org.iti.jxkh.busiAudit.award;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxkh.business.award.ChooseDeptWin;
import org.iti.jxkh.business.award.ChooseMemberWin;
import org.iti.jxkh.business.meeting.AssignDeptWindow;
import org.iti.jxkh.business.meeting.UpfileWindow;
import org.iti.jxkh.entity.Jxkh_Award;
import org.iti.jxkh.entity.Jxkh_AwardDept;
import org.iti.jxkh.entity.Jxkh_AwardFile;
import org.iti.jxkh.entity.Jxkh_AwardMember;
import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.service.BusinessIndicatorService;
import org.iti.jxkh.service.JxkhAwardService;
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
import com.iti.common.util.PropertiesLoader;
import com.iti.common.util.UploadUtil;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;

public class AddAwardWin extends Window implements AfterCompose {

	/**
	 * @author ZhangYuguang
	 */
	private static final long serialVersionUID = 219405956076286163L;
	private Tab baseTab, fileTab, scoreTab;
	private Listbox personScore, departmentScore;
	private Toolbarbutton save, close;
	private Textbox name, awardMember, awardDept, coCompany, authorizeCompany,
			registerCode, record;
	private Label submitName;
	private Listbox rank;
	private Row outDeptRow;
	private Radio cooperationTrue, cooperationFalse, firstSignTrue,
			firstSignFalse;
	private Datebox date;
	private Button print;
	private YearListbox jiFenTime;
	private Row recordlabel;
	private Hbox recordhbox;
	private Jxkh_Award award;
	private JxkhAwardService jxkhAwardService;
	private WkTUser user;
	private List<WkTUser> memberList = new ArrayList<WkTUser>();
	private List<WkTDept> deptList = new ArrayList<WkTDept>();
	private List<Jxkh_AwardMember> awardMemberList = new ArrayList<Jxkh_AwardMember>();
	private List<Jxkh_AwardDept> awardDeptList = new ArrayList<Jxkh_AwardDept>();
	public static final Long awardRank = (long) 41;

	private BusinessIndicatorService businessIndicatorService;

	private Listbox applyList1, applyList2;

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");// 获取当前登录用户
		rank.setItemRenderer(new awardRankRenderer());
		personScore.setItemRenderer(new personScoreRenderer());
		departmentScore.setItemRenderer(new departmentScoreRenderer());
		jiFenTime.initYearListbox("");
		// 点击radio"cooperationTrue"触发事件
		cooperationTrue.addEventListener(Events.ON_CHECK, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				outDeptRow.setVisible(true);
			}
		});
		// 点击radio"cooperationFalse"触发事件
		cooperationFalse.addEventListener(Events.ON_CHECK, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				outDeptRow.setVisible(false);
			}
		});

		// 点击绩分页面时会议信息页面和文档信息页面的保存和退出按钮隐藏
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
		if (award == null) {
			cooperationFalse.setChecked(true);
			submitName.setValue(user.getKuName());
			fileTab.setDisabled(true);
			scoreTab.setDisabled(true);
		} else {
			jiFenTime.initYearListbox(award.getjxYear());
			print.setHref("/print.action?n=award&id=" + award.getId());
			name.setValue(award.getName());
			if (award.getFirstSign() == 1) {
				firstSignTrue.setChecked(true);
				firstSignFalse.setChecked(false);
			} else {
				firstSignTrue.setChecked(false);
				firstSignFalse.setChecked(true);
			}
			if (award.getCoState() == 1) {
				cooperationTrue.setChecked(true);
				cooperationFalse.setChecked(false);
				outDeptRow.setVisible(true);
				coCompany.setValue(award.getCoCompany());
			} else {
				cooperationTrue.setChecked(false);
				cooperationFalse.setChecked(true);
			}
			if (award.getState() == 6) {
				record.setVisible(true);
				recordlabel.setVisible(true);
				recordhbox.setVisible(true);
				record.setValue(award.getRecordCode());
			}
			registerCode.setValue(award.getRegisterCode());
			date.setValue(ConvertUtil.convertDate(award.getDate()));
			authorizeCompany.setValue(award.getAuthorizeCompany());
			submitName.setValue(award.getSubmitName());
			awardMemberList = award.getAwardMember();
			for (int i = 0; i < awardMemberList.size(); i++) {
				Jxkh_AwardMember mem = (Jxkh_AwardMember) awardMemberList
						.get(i);
				memberName += mem.getName() + "、";
				if (mem.getType() == 0) {
					WkTUser user = jxkhAwardService
							.findWktUserByMemberUserId(mem.getPersonId());
					memberList.add(user);
				} else {
					WkTUser out = new WkTUser();
					WkTDept d = new WkTDept();
					d.setKdName("校外");
					out.setDept(d);
					out.setKuId(new Random().nextLong());
					out.setKuLid("校外");
					out.setKuName(mem.getName());
					out.setType((short) 1);
					memberList.add(out);
				}
			}
			awardMember.setValue(memberName.substring(0,
					memberName.length() - 1));
			awardDeptList = jxkhAwardService.findAwardDeptByAwardId(award);
			for (int i = 0; i < awardDeptList.size(); i++) {
				Jxkh_AwardDept dept = (Jxkh_AwardDept) awardDeptList.get(i);
				deptName += dept.getName() + "、";
				WkTDept depts = jxkhAwardService.findWkTDeptByDept(dept
						.getDeptId());
				deptList.add(depts);
			}
			awardDept.setValue(deptName.substring(0, deptName.length() - 1));

			// 新的附件上传初始化（20120306）
			arrList1.clear();
			arrList2.clear();
			Set<?> files = award.getAwardFile();
			Object[] file = files.toArray();
			for (int i = 0; i < file.length; i++) {
				Jxkh_AwardFile f = (Jxkh_AwardFile) file[i];
				if (f.getBelongType().equals("集体（个人）奖励证书")) {
					String[] arr = new String[4];
					arr[0] = f.getPath();
					arr[1] = f.getName();
					arr[2] = f.getDate();
					arr[3] = f.getBelongType();
					arrList1.add(arr);
				}
				if (f.getBelongType().equals("申报材料")) {
					String[] arr = new String[4];
					arr[0] = f.getPath();
					arr[1] = f.getName();
					arr[2] = f.getDate();
					arr[3] = f.getBelongType();
					arrList2.add(arr);
				}
			}
			applyList1.setItemRenderer(new FilesRenderer1());
			applyList1.setModel(new ListModelList(arrList1));
			applyList2.setItemRenderer(new FilesRenderer2());
			applyList2.setModel(new ListModelList(arrList2));
		}
		initListbox();
	}

	private void initListbox() {
		List<Jxkh_BusinessIndicator> rankList = new ArrayList<Jxkh_BusinessIndicator>();
		rankList.add(new Jxkh_BusinessIndicator());
		rankList.addAll(jxkhAwardService.findRank(awardRank));
		rank.setModel(new ListModelList(rankList));
		rank.setSelectedIndex(0);

		personScore.setModel(new ListModelList(awardMemberList));
		departmentScore.setModel(new ListModelList(awardDeptList));
	}

	// 单击保存按钮触发保存事件
	public void onClick$save() throws InterruptedException {
		if (award == null) {
			award = new Jxkh_Award();
			setEntity();
		} else {
			setEntity();
			// jxkhAwardService.saveOrUpdate(award);
		}
		try {
			if (awardMember.getValue() == null
					|| awardMember.getValue().equals("")) {
				Messagebox.show("请选择奖励成员！", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				return;
			}
			if (awardDept.getValue() == null || awardDept.getValue().equals("")) {
				Messagebox.show("请选择奖励部门！", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				return;
			}
			if (rank.getSelectedItem().getIndex() == 0) {
				Messagebox.show("请选择奖励等级！", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				return;
			}
			if (jiFenTime.getSelectedIndex() == 0
					|| jiFenTime.getSelectedItem() == null) {
				try {
					Messagebox.show("请选择绩分年度！", "提示", Messagebox.OK,
							Messagebox.INFORMATION);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return;
			}

			jxkhAwardService.saveOrUpdate(award);
			Messagebox.show("奖励保存成功！", "提示", Messagebox.OK,
					Messagebox.INFORMATION);
			scoreTab.setDisabled(false);
			fileTab.setDisabled(false);
			List<Jxkh_AwardMember> mlist = award.getAwardMember();
			List<Jxkh_AwardDept> dlist = award.getAwardDept();
			personScore.setModel(new ListModelList(mlist));
			departmentScore.setModel(new ListModelList(dlist));	
		} catch (Exception e) {
			e.printStackTrace();
			try {
				Messagebox.show("奖励保存失败，请重试！", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}

	private Float computeScore(Jxkh_BusinessIndicator kdRank, Short sign) {
		float result = 0f;
		String type = "";
		float res = 0f;
		Properties property = PropertiesLoader.loader("title",
				"title.properties");
		float nation1 = 0, nation2 = 0, province1 = 0, province2 = 0, province3 = 0, city1 = 0, city2 = 0, city3 = 0;
		if (kdRank.getKbName().equals(property.getProperty("nation1"))) {
			if (sign.shortValue() == Jxkh_Award.YES) {
				nation1 += 1f;
				result = nation1;
				type = property.getProperty("nation1");
			} else if (sign.shortValue() == Jxkh_Award.NO) {
				nation2 += 1f;
				result = nation2;
				type = property.getProperty("nation2");
			}

		} else if (kdRank.getKbName().equals(property.getProperty("nation2"))) {
			if (sign.shortValue() == Jxkh_Award.YES) {
				nation2 += 1f;
				result = nation2;
				type = property.getProperty("nation2");
			} else if (sign.shortValue() == Jxkh_Award.NO) {
				province1 += 1f;
				result = province1;
				type = property.getProperty("province1");
			}
		} else if (kdRank.getKbName().equals(property.getProperty("province1"))) {
			if (sign.shortValue() == Jxkh_Award.YES) {
				province1 += 1f;
				result = province1;
				type = property.getProperty("province1");
			} else if (sign.shortValue() == Jxkh_Award.NO) {
				province2 += 1f;
				result = province2;
				type = property.getProperty("province2");
			}
		} else if (kdRank.getKbName().equals(property.getProperty("province2"))) {
			if (sign.shortValue() == Jxkh_Award.YES) {
				province2 += 1f;
				result = province2;
				type = property.getProperty("province2");
			} else if (sign.shortValue() == Jxkh_Award.NO) {
				province3 += 1f;
				result = province3;
				type = "province3";
			}
		} else if (kdRank.getKbName().equals(property.getProperty("province3"))) {
			if (sign.shortValue() == Jxkh_Award.YES) {
				province3 += 1f;
				result = province3;
				type = property.getProperty("province3");
			} else if (sign.shortValue() == Jxkh_Award.NO) {
				city1 += 1f;
				result = city1;
				type = property.getProperty("city1");
			}
		} else if (kdRank.getKbName().equals(property.getProperty("city1"))) {
			if (sign.shortValue() == Jxkh_Award.YES) {
				city1 += 1f;
				result = city1;
				type = property.getProperty("city1");
			} else if (sign.shortValue() == Jxkh_Award.NO) {
				city2 += 1f;
				result = city2;
				type = property.getProperty("city2");
			}
		} else if (kdRank.getKbName().equals(property.getProperty("city2"))) {
			if (sign.shortValue() == Jxkh_Award.YES) {
				city2 += 1f;
				result = city2;
				type = property.getProperty("city2");
			} else if (sign.shortValue() == Jxkh_Award.NO) {
				city3 += 1f;
				result = city3;
				type = property.getProperty("city3");
			}
		} else if (kdRank.getKbName().equals(property.getProperty("city3"))) {
			if (sign.shortValue() == Jxkh_Award.YES) {
				city3 += 1f;
				result = city3;
				type = property.getProperty("city3");
			} else if (sign.shortValue() == Jxkh_Award.NO) {
				city3 += 1f * 0.5f;
				result = city3;
				type = property.getProperty("city3");
			}
		}
		Jxkh_BusinessIndicator bi = (Jxkh_BusinessIndicator) businessIndicatorService
				.getEntityByName(type);
		if (bi == null) {
			res += result * 0;
		} else {
			res += result * bi.getKbScore() * bi.getKbIndex();
		}
		return Float.valueOf(res);

	}

	public void setEntity() throws InterruptedException {
		Short sign;
		Jxkh_BusinessIndicator kdRank = new Jxkh_BusinessIndicator();
		award.setName(name.getValue());
		if (firstSignTrue.isChecked()) {
			award.setFirstSign(Jxkh_Award.YES);
			sign = Jxkh_Award.YES;
		} else {
			award.setFirstSign(Jxkh_Award.NO);
			sign = Jxkh_Award.NO;
		}
		if (rank.getSelectedItem().getIndex() != 0) {
			award.setRank((Jxkh_BusinessIndicator) rank.getSelectedItem()
					.getValue());
			kdRank = (Jxkh_BusinessIndicator) rank.getSelectedItem().getValue();
		}
		Float score = computeScore(kdRank, sign);
		award.setScore(score);

		award.setjxYear(jiFenTime.getSelectedItem().getValue() + "");
		/*
		 * if (firstSignTrue.isChecked()) { award.setFirstSign(Jxkh_Award.YES);
		 * } else { award.setFirstSign(Jxkh_Award.NO); }
		 */
		if (cooperationTrue.isChecked()) {
			award.setCoState(Jxkh_Award.YES);
			award.setCoCompany(coCompany.getValue());
		} else {
			award.setCoState(Jxkh_Award.NO);
			award.setCoCompany(null);
		}
		award.setRegisterCode(registerCode.getValue());
		award.setRecordCode(record.getValue());
		award.setDate(ConvertUtil.convertDateString(date.getValue()));
		award.setAuthorizeCompany(authorizeCompany.getValue());
		// award.setSubmitId(user.getKuLid());
		// award.setSubmitName(user.getKuName());
		int j = 1;
		List<Jxkh_AwardMember> mlist = new ArrayList<Jxkh_AwardMember>();
		List<Jxkh_AwardDept> dlist = new ArrayList<Jxkh_AwardDept>();
		if (award.getAwardMember() != null) {
			mlist = award.getAwardMember();
			jxkhAwardService.deleteAll(mlist);
			mlist.clear();
		}
		for (WkTUser user : memberList) {
			// Short a=0;
			// Short b=1;
			Jxkh_AwardMember member = new Jxkh_AwardMember();
			member.setAward(award);
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

			// 人员的比例和分值0503
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
			float f = 0;
			if (percent != 0)
				f = score * percent / 10;
			float sco = (float) (Math.round(f * 1000)) / 1000;// 保留三位小数
			member.setPer(percent);
			member.setScore(sco);
			if((user.getKdId() != null && user.getDept() != null && user.getDept().getKdNumber().equals(WkTDept.guanlidept)) || user.getKdId() == null)
				member.setAssignDep(deptList.get(0).getKdName());
			else
				member.setAssignDep(user.getDept().getKdName());
			j++;
			mlist.add(member);
		}
		award.setAwardMember(mlist);
		int i = 1;
		if (award.getAwardDept() != null) {
			dlist = award.getAwardDept();
			jxkhAwardService.deleteAll(dlist);
			dlist.clear();
		}
		for (WkTDept wktDept : deptList) {
			Jxkh_AwardDept dept = new Jxkh_AwardDept();
			dept.setAward(award);
			dept.setName(wktDept.getKdName());
			dept.setDeptId(wktDept.getKdNumber());
			dept.setRank(i);
			i++;

			// 部门默认的分值0503
			float fen = 0.0f;
			for (int g = 0; g < mlist.size(); g++) {
				Jxkh_AwardMember m = mlist.get(g);
				if (m.getAssignDep().equals(dept.getName())) {
					fen += m.getScore();
				}
			}
			float scor = (float) (Math.round(fen * 1000)) / 1000;// 保留三位小数
			dept.setScore(scor);
			dlist.add(dept);
		}
		award.setAwardDept(dlist);

		// 新的附件保存（20120306）
		List<String[]> arrList = new ArrayList<String[]>();
		Set<Jxkh_AwardFile> fset=new HashSet<Jxkh_AwardFile>();
		Set<Jxkh_AwardFile> oldFile = new HashSet<Jxkh_AwardFile>();
		oldFile = award.getAwardFile();
		if (oldFile != null) {
			for(Object o : oldFile.toArray()) {
				Jxkh_AwardFile f = (Jxkh_AwardFile) o;
				if(f != null)
					jxkhAwardService.delete(f);
			}
		}
		if(award.getAwardFile() != null)
			award.getAwardFile().clear();
		if (arrList1.size() != 0 || arrList1 != null)
			arrList.addAll(arrList1);
		if (arrList2.size() != 0 || arrList2 != null)
			arrList.addAll(arrList2);
		for (int r = 0; r < arrList.size(); r++) {
			String[] s = arrList.get(r);
			Jxkh_AwardFile file = new Jxkh_AwardFile();
			file.setAward(award);
			file.setPath(s[0]);
			file.setName(s[1]);
			file.setDate(s[2]);
			file.setBelongType(s[3]);
			fset.add(file);
		}
		award.setAwardFile(fset);
	}

	// 单击选择成员按钮，触发弹出选择成员页面事件
	public void onClick$chooseMember() throws SuspendNotAllowedException,
			InterruptedException {
		final ChooseMemberWin win = (ChooseMemberWin) Executions
				.createComponents(
						"/admin/jxkh/busiAudit/award/choosemember.zul", null,
						null);
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
				awardMember.setValue(members);
			}
		});
		win.doModal();
		// 根据选择的人员，部门自动与之对应 《2012年03月07号》
		deptList.clear();
		for (int i = 0; i < memberList.size(); i++) {
			int k = 0;// 当人员所在的部门和前面重复，k=1
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
		awardDept.setValue(null);
		awardDept.setValue(depts);
	}

	// 单击选择部门按钮，触发弹出选择成员页面事件
	public void onClick$chooseDept() throws SuspendNotAllowedException,
			InterruptedException {
		final ChooseDeptWin win = (ChooseDeptWin) Executions.createComponents(
				"/admin/jxkh/busiAudit/award/choosedept.zul", null, null);
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
				awardDept.setValue(depts);
				win.detach();
			}
		});
		win.doModal();
	}

	/** 奖励级别列表渲染器 */
	public class awardRankRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			Jxkh_BusinessIndicator type = (Jxkh_BusinessIndicator) data;
			item.setValue(type);
			Listcell c0 = new Listcell();
			if (type.getKbName() == null) {
				c0.setLabel("--请选择--");
			} else {
				c0.setLabel(type.getKbName());
			}
			item.appendChild(c0);
			if (item.getIndex() == 0)
				item.setSelected(true);
			if (award != null && type.equals(award.getRank())) {
				item.setSelected(true);
			}
		}
	}

	/** 附件列表渲染器 */
	public class FileRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			String name = (String) data;
			item.setValue(name);
			item.setLabel(name);
			if (item.getIndex() == 0)
				item.setSelected(true);
		}
	}

	public Jxkh_Award getAward() {
		return award;
	}

	public void setAward(Jxkh_Award award) {
		this.award = award;
	}

	/**
	 * <li>功能描述：关闭当前窗口。
	 */
	public void onClick$close() {
		this.onClose();
	}

	// 20120306
	private List<String[]> arrList1 = new ArrayList<String[]>();

	public void onClick$ups1() {
		final UpfileWindow w = (UpfileWindow) Executions.createComponents(
				"/admin/jxkh/busiAudit/meeting/upfile.zul", null, null);
		w.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				String filePath = (String) Executions.getCurrent()
						.getAttribute("path");
				System.out.println("输出文件路径是path=" + filePath);
				String fileName = (String) Executions.getCurrent()
						.getAttribute("title");
				System.out.println("输出的文件标题是title=" + fileName);
				String upTime = (String) Executions.getCurrent().getAttribute(
						"upTime");
				System.out.println("输出文件的上传时间time=" + upTime);
				applyList1.setItemRenderer(new FilesRenderer1());
				String[] arr = new String[4];
				arr[0] = filePath;
				arr[1] = fileName;
				arr[2] = upTime;
				arr[3] = "集体（个人）奖励证书";
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
	 * <li>功能描述：文档信息的listbox(20120306)
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
					Messagebox.show("确定删除吗?", "确定", Messagebox.OK
							| Messagebox.CANCEL, Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener() {
								public void onEvent(Event evt)
										throws InterruptedException {
									if (evt.getName().equals("onOK")) {
										arrList1.remove(str);
										applyList1.setModel(new ListModelList(
												arrList1));
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
		final UpfileWindow w = (UpfileWindow) Executions.createComponents(
				"/admin/jxkh/busiAudit/meeting/upfile.zul", null, null);
		w.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				String filePath = (String) Executions.getCurrent()
						.getAttribute("path");
				String fileName = (String) Executions.getCurrent()
						.getAttribute("title");
				String upTime = (String) Executions.getCurrent().getAttribute(
						"upTime");
				applyList2.setItemRenderer(new FilesRenderer2());
				String[] arr = new String[4];
				arr[0] = filePath;
				arr[1] = fileName;
				arr[2] = upTime;
				arr[3] = "申报材料";
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
	 * <li>功能描述：文档信息的listbox(20120306)
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
					Messagebox.show("确定删除吗?", "确定", Messagebox.OK
							| Messagebox.CANCEL, Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener() {
								public void onEvent(Event evt)
										throws InterruptedException {
									if (evt.getName().equals("onOK")) {
										arrList2.remove(str);
										applyList2.setModel(new ListModelList(
												arrList2));
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

	public void download(String fpath, String fname)
			throws InterruptedException {
		File file = new File(UploadUtil.getRootPath() + fpath);
		if (file.exists()) {
			try {
				Filedownload.save(file, fname);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			Messagebox.show("无法下载，可能是因为文件没有被上传！ ", "错误", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	/** 个人绩分渲染器 */
	public class personScoreRenderer implements ListitemRenderer {

		@Override
		public void render(Listitem item, Object data) throws Exception {

			if (data == null)
				return;
			final Jxkh_AwardMember member = (Jxkh_AwardMember) data;
			item.setValue(member);
			Listcell c1 = new Listcell(item.getIndex() + 1 + "");
			Listcell c2 = new Listcell(member.getName());
			Listcell c3 = new Listcell();
			if (member.getType() == 1) {
				c3 = new Listcell("院外");
			} else {
				c3 = new Listcell("院内");
			}
			Listcell c4 = new Listcell();
			if (member.getType() == 1) {
				c4 = new Listcell("院外");
			} else {
				c4 = new Listcell(member.getDept());
			}
			Listcell c5 = new Listcell();
			c5.setTooltiptext("请输入数字");
			Textbox t = new Textbox();
			t.setParent(c5);
			if (member.getPer() != null)
				t.setValue(member.getPer() + "");
			Listcell c6 = new Listcell();
			Toolbarbutton bar = new Toolbarbutton();
			if (member.getAssignDep() == null
					|| member.getAssignDep().equals("")) {
				bar.setLabel("指定");
				bar.setStyle("color:blue");
			} else {
				bar.setLabel(member.getAssignDep());
			}
			c6.appendChild(bar);
			Listcell c7 = new Listcell();
			if (member.getScore() != null)
				c7.setLabel(member.getScore() + "");

			final List<Jxkh_AwardDept> temp=jxkhAwardService.findAwardDepByAwardId(award);
			// 弹出指定部门页面
			bar.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event event) throws Exception {

					AssignDeptWindow w = (AssignDeptWindow) Executions
							.createComponents(
									"/admin/jxkh/busiAudit/meeting/assignDept.zul",
									null, null);
					try {
						w.setFlag("AWARD");
						w.setDept(temp);
						w.setMember(member);
						w.initWindow();
						w.doModal();

						// 指定部门完成后自动计算得分
						w.addEventListener(Events.ON_CHANGE,
								new EventListener() {
									public void onEvent(Event arg0)
											throws Exception {
										List<Jxkh_AwardMember> tempMemberList = award
												.getAwardMember();
										personScore.setModel(new ListModelList(
												tempMemberList));
										for (int k = 0; k < awardDeptList
												.size(); k++) {
											Jxkh_AwardDept d = awardDeptList
													.get(k);
											float f = 0.0f;
											for (int i = 0; i < tempMemberList
													.size(); i++) {
												Jxkh_AwardMember m = tempMemberList
														.get(i);
												if (m.getAssignDep() == null
														|| m.getAssignDep()
																.equals("")) {
													if (m.getDept().equals(
															d.getName())) {
														f += m.getScore();
													}
												} else if (m.getAssignDep()
														.equals(d.getName())) {
													f += m.getScore();
												}
											}
											d.setScore(f);
											jxkhAwardService.update(d);
										}
										List<Jxkh_AwardDept> tempDeptList = jxkhAwardService
												.findAwardDeptByAwardId(award);
										departmentScore
												.setModel(new ListModelList(
														tempDeptList));
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

	/** 部门绩分列表渲染器 */
	public class departmentScoreRenderer implements ListitemRenderer {

		@Override
		public void render(Listitem arg0, Object arg1) throws Exception {
			if (arg0 == null)
				return;
			final Jxkh_AwardDept dept = (Jxkh_AwardDept) arg1;
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

	// 绩分信息的保存按钮
	public void onClick$submitScore() {
		float a = 0.0f;
		for (int i = 0; i < awardMemberList.size(); i++) {
			Listitem item = personScore.getItemAtIndex(i);
			Listcell lc = (Listcell) item.getChildren().get(4);
			Textbox temp = (Textbox) lc.getChildren().get(0);
			if (temp.getValue() != null && temp.getValue() != "")
				try {
					a += Float.parseFloat(temp.getValue());
				} catch (Exception e) {
					e.printStackTrace();
					try {
						Messagebox.show("只能输入数字！", "提示", Messagebox.OK,
								Messagebox.EXCLAMATION);
						return;
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}

		}
		if (a != 10.0) {
			try {
				Messagebox.show("请检查人员所占比例的总和是否为10.0！", "提示", Messagebox.OK,
						Messagebox.EXCLAMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		for (int i = 0; i < awardMemberList.size(); i++) {
			Listitem item = personScore.getItemAtIndex(i);
			Listcell lc = (Listcell) item.getChildren().get(4);
			Textbox temp = (Textbox) lc.getChildren().get(0);
			float s = 0.0f;// 比例
			float f = 0.0f;// 分值
			if (temp.getValue() != null && temp.getValue() != "")
				s = Float.parseFloat(temp.getValue());
			if (award.getScore() != null)
				f = s * award.getScore() / 10;
			float score = (float) (Math.round(f * 1000)) / 1000;
			Jxkh_AwardMember member = (Jxkh_AwardMember) item.getValue();
			member.setPer(s);
			member.setScore(score);
			jxkhAwardService.update(member);
		}
		List<Jxkh_AwardMember> tempMemberList = award.getAwardMember();
		personScore.setModel(new ListModelList(tempMemberList));
		/*for (int k = 0; k < awardDeptList.size(); k++) {
			Jxkh_AwardDept d = awardDeptList.get(k);
			float f = 0.0f;
			for (int i = 0; i < tempMemberList.size(); i++) {
				Jxkh_AwardMember m = tempMemberList.get(i);
				if (m.getAssignDep() == null || m.getAssignDep().equals("")) {
					if (m.getDept().equals(d.getName())) {
						f += m.getScore();
					}
				} else if (m.getAssignDep().equals(d.getName())) {
					f += m.getScore();
				}
			}
			d.setScore(f);
			jxkhAwardService.update(d);
		}*/
		try {
			Messagebox.show("绩分信息保存成功！", "提示", Messagebox.OK,
					Messagebox.INFORMATION);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Jxkh_AwardDept> tempDeptList = jxkhAwardService
				.findAwardDeptByAwardId(award);
		departmentScore.setModel(new ListModelList(tempDeptList));
	}

	// 绩分信息的关闭按钮
	public void onClick$closeScore() {
		this.onClose();
	}
}
