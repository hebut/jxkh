package org.iti.jxkh.busiAudit.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxkh.business.award.ChooseDeptWin;
import org.iti.jxkh.business.award.ChooseMemberWin;
import org.iti.jxkh.business.meeting.AssignDeptWindow;
import org.iti.jxkh.business.meeting.UpfileWindow;
import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.entity.Jxkh_Patent;
import org.iti.jxkh.entity.Jxkh_Project;
import org.iti.jxkh.entity.Jxkh_ProjectDept;
import org.iti.jxkh.entity.Jxkh_ProjectFile;
import org.iti.jxkh.entity.Jxkh_ProjectFund;
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
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.iti.common.util.ConvertUtil;
import com.iti.common.util.UploadUtil;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.DepartmentService;

public class AddHPWindow extends Window implements AfterCompose {

	/**
	 * @author SongYu
	 */
	private static final long serialVersionUID = -3664613977051724505L;
	private Tab baseTab, funds, fileTab, scoreTab;
	private Listbox personScore, departmentScore;
	private Toolbarbutton submit, close;
	WkTUser user;
	private JXKHMeetingService jxkhMeetingService;
	private JxkhProjectService jxkhProjectService;
	private Textbox projectName, projectMember, department, coUnit, sumFund, projecCode, header, entruster;
	private Label /*yearFund,*/ writer, entrLabel;
	private Datebox begin, end;
	private Listbox fundsListbox1;
	private Radiogroup ifCoo, ifEntruster;
	private Row coUnitRow;
	private YearListbox jiFenTime;
	private Label recordlabel;
	private Hbox recordhbox;
	private Textbox record;

	private Button print;
	private Jxkh_Project project;
	private String dept = "";
	private String dept1 = "";
	private List<WkTUser> memberList = new ArrayList<WkTUser>();
	private List<WkTDept> deptList = new ArrayList<WkTDept>();
	private List<Jxkh_ProjectMember> projectMemberList = new ArrayList<Jxkh_ProjectMember>();
	private List<Jxkh_ProjectDept> projectDeptList = new ArrayList<Jxkh_ProjectDept>();
	private Radio firstSignTrue, firstSignFalse;
	private Listbox applyList1, applyList2;
	private Set<Jxkh_ProjectFile> fileList1;

	private DepartmentService departmentService;

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");// 获取当前登录用户
		jiFenTime.initYearListbox("");
		fundsListbox1.setItemRenderer(new FundsRenderer());
		personScore.setItemRenderer(new personScoreRenderer());
		departmentScore.setItemRenderer(new departmentScoreRenderer());

		// 点击绩分页面时会议信息页面和文档信息页面的保存和退出按钮隐藏
		scoreTab.addEventListener(Events.ON_CLICK, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				submit.setVisible(false);
				close.setVisible(false);
			}
		});
		baseTab.addEventListener(Events.ON_CLICK, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				submit.setVisible(true);
				close.setVisible(true);
			}
		});
		funds.addEventListener(Events.ON_CLICK, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				submit.setVisible(false);
				close.setVisible(false);
			}
		});
		fileTab.addEventListener(Events.ON_CLICK, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				submit.setVisible(true);
				close.setVisible(true);
			}
		});
	}

	public void initWindow() {
		if (project.getState() == Jxkh_Patent.SAVE_RECORD) {
			record.setVisible(true);
			recordlabel.setVisible(true);
			recordhbox.setVisible(true);
			record.setValue(project.getRecordCode());
		}
		jiFenTime.initYearListbox(project.getjxYear());
		projectName.setValue(project.getName());
		WkTUser infoWriter = jxkhProjectService.findWktUserByMemberUserId(project.getInfoWriter());
		writer.setValue(infoWriter.getKuName());
		sumFund.setValue(project.getSumFund().toString());
		projecCode.setValue(project.getProjecCode());
		header.setValue(project.getHeader());
		entruster.setValue(project.getEntruster());
		if (project.getBeginDate() == null || project.getBeginDate().length() == 0) {
			begin.setValue(null);
		} else {
			begin.setValue(ConvertUtil.convertDate(project.getBeginDate()));
		}
		if (project.getEndDate() == null || project.getEndDate().length() == 0) {
			end.setValue(null);
		} else {
			end.setValue(ConvertUtil.convertDate(project.getEndDate()));
		}

		if (project.getCooState() != null) {
			if (project.getCooState() == Jxkh_Project.YES) {
				ifCoo.setSelectedIndex(0);
				coUnitRow.setVisible(true);
				coUnit.setValue(project.getCooCompany());
			} else {
				ifCoo.setSelectedIndex(1);
			}
		}
		if (project.getFirstSign() != null) {
			if (project.getFirstSign() == Jxkh_Project.YES) {
				firstSignTrue.setChecked(true);
				firstSignFalse.setChecked(false);
			} else {
				firstSignTrue.setChecked(false);
				firstSignFalse.setChecked(true);
			}
		}
		if (project.getIfEntruster() != null) {
			if (project.getIfEntruster() == Jxkh_Project.YES) {
				ifEntruster.setSelectedIndex(0);
				entrLabel.setValue("受托方：");
			} else {
				ifEntruster.setSelectedIndex(1);
				entrLabel.setValue("委托方:");
			}
		}

		String memberName = "";
		String deptName = "";
		// 成员 部门
		projectMemberList = jxkhProjectService.findProjectMember(project);
		if (projectMemberList != null && projectMemberList.size() != 0) {
			for (int i = 0; i < projectMemberList.size(); i++) {
				Jxkh_ProjectMember mem = (Jxkh_ProjectMember) projectMemberList.get(i);
				memberName += mem.getName() + "、";
				if (mem.getType() == 0) {
					WkTUser user = jxkhProjectService.findWktUserByMemberUserId(mem.getPersonId());
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
			projectMember.setValue(memberName.substring(0, memberName.length() - 1));
		}
		projectDeptList = jxkhProjectService.findProjectDept(project);
		if (projectDeptList != null && projectDeptList.size() != 0) {
			for (int i = 0; i < projectDeptList.size(); i++) {
				Jxkh_ProjectDept dept = (Jxkh_ProjectDept) projectDeptList.get(i);
				deptName += dept.getName() + "、";
				WkTDept depts = jxkhProjectService.findWkTDeptByDept(dept.getDeptId());
				deptList.add(depts);
			}

			department.setValue(deptName.substring(0, deptName.length() - 1));
		}
		jiFenTime.initYearListbox(project.getjxYear());
		// 新的附件初始化20120305
		arrList1.clear();
		arrList2.clear();
		fileList1 = project.getProjectFile();
		Object[] file = fileList1.toArray();
		for (int j = 0; j < file.length; j++) {
			Jxkh_ProjectFile f = (Jxkh_ProjectFile) file[j];

			if (f.getBelongType().equals("项目合同书")) {
				String[] arr = new String[4];
				arr[0] = f.getPath();
				arr[1] = f.getName();
				arr[2] = f.getSubmitDate();
				arr[3] = f.getBelongType();
				arrList1.add(arr);
			}
			if (f.getBelongType().equals("年度进度文档")) {
				String[] arr = new String[4];
				arr[0] = f.getPath();
				arr[1] = f.getName();
				arr[2] = f.getSubmitDate();
				arr[3] = f.getBelongType();
				arrList2.add(arr);
			}
		}
		applyList1.setItemRenderer(new FilesRenderer1());
		applyList1.setModel(new ListModelList(arrList1));
		applyList2.setItemRenderer(new FilesRenderer2());
		applyList2.setModel(new ListModelList(arrList2));

		// 基金
		List<Jxkh_ProjectFund> fundList1 = jxkhProjectService.findFunds(project, Jxkh_ProjectFund.ZXF);
		fundsListbox1.setModel(new ListModelList(fundList1));
		// personScore.setModel(new ListModelList(projectMemberList));
		// departmentScore.setModel(new ListModelList(projectDeptList));
	}

	public void initShow() {
		if (project == null) {
			funds.setDisabled(true);
			fileTab.setDisabled(true);
			scoreTab.setDisabled(true);
			// documents.setDisabled(true);
			print.setVisible(false);
		} else {
			funds.setDisabled(false);
			fileTab.setDisabled(false);
			scoreTab.setDisabled(false);
			print.setVisible(false);
			// documents.setDisabled(false);
			print.setHref("/print.action?n=hp&id=" + project.getId());

			List<Jxkh_ProjectMember> tempMemberList = jxkhProjectService.findProjectMember(project);
			personScore.setModel(new ListModelList(tempMemberList));
			List<Jxkh_ProjectDept> tempDeptList = jxkhProjectService.findProjectDept(project);
			departmentScore.setModel(new ListModelList(tempDeptList));
		}

	}

	/**
	 * 横向项目积分计算
	 */
	private void computeScore() {
		
		float pscore = 0f, hrank = 1f;
		/**
		 * 计算项目基本得分
		 */
		if (project.getRank() != null) {
			if (project.getFirstSign() != null && project.getFirstSign().equals(Jxkh_Project.NO)) {
				hrank = 0.5f;
			}
		}
		pscore = hrank * project.getRank().getKbIndex() * project.getRank().getKbScore();
		if (project.getIfHumanities() != null && project.getIfHumanities().equals(Jxkh_Project.YES)) {
			pscore = pscore * 3;
		} else if (project.getIfSoftScience() != null && project.getIfSoftScience().equals(Jxkh_Project.YES)) {
			pscore = pscore * 2;
		}
		/**
		 * 根据当年到院经费计算各部门得分和人员得分
		 */
		if (project.getId() != null) {
			String year = project.getjxYear();
			List<Jxkh_ProjectFund> flist = jxkhProjectService.getFundByYearAndProject(project, year);
			Set<Long> fset = new HashSet<Long>();
			for (Jxkh_ProjectFund f : flist) {
				if (f.getDeptNum() != null)
					fset.add(f.getDeptNum());
			}
			// 存放部门，部门对应的经费，String表示部门的编号，Long表示经费
			Map<Long, Float> dScoreA = new HashMap<Long, Float>();
			List<Jxkh_ProjectDept> dlist = jxkhProjectService.findProjectDep(project);
			for (Jxkh_ProjectDept d : dlist) {
				List<WkTDept> dt = departmentService.getDeptByNum(d.getDeptId());
				dScoreA.put(dt.get(0).getKdId(), 0f);
			}
			for (Jxkh_ProjectFund f : flist) {
				for (int i = 0; i < fset.toArray().length; i++) {
					Long dn = (Long) fset.toArray()[i];
					if (f.getDeptNum().equals(dn)) {
						float s = 0f;
						if (f.getType().equals(Jxkh_ProjectFund.IN)) {
							s = dScoreA.get(dn).floatValue() + f.getMoney().floatValue();
						} else {
							s = dScoreA.get(dn).floatValue() - f.getMoney().floatValue();
						}
						dScoreA.remove(dn);
						dScoreA.put(dn, s);
					}
				}
			}
			// 更新各部门得分
			float totalFund = 0f;
			for (Jxkh_ProjectDept d : dlist) {
				List<WkTDept> dt = departmentService.getDeptByNum(d.getDeptId());
				totalFund += dScoreA.get(dt.get(0).getKdId());
				d.setScore(dScoreA.get(dt.get(0).getKdId()) * pscore);
				jxkhProjectService.update(d);
			}
			List<Jxkh_ProjectMember> mlist = jxkhProjectService.findProjectMember(project);
			/**
			 * 更新人员的比例和分值
			 */
			float percent = 0f;
			int len = mlist.size();
			for (int j = 0; j < mlist.size(); j++) {
				Jxkh_ProjectMember m = mlist.get(j);
				switch (len) {
				case 1:
					percent = 10;
					break;
				case 2:
					if (j == 0)
						percent = 7f;
					else if (j == 1)
						percent = 3f;
					break;
				case 3:
					if (j == 0)
						percent = 6f;
					else if (j == 1)
						percent = 3f;
					else if (j == 2)
						percent = 1f;
					break;
				case 4:
					if (j == 0)
						percent = 5f;
					else if (j == 1)
						percent = 3f;
					else if (j == 2)
						percent = 1f;
					else if (j == 3)
						percent = 1f;
					break;
				case 5:
					if (j == 0)
						percent = 5f;
					else if (j == 1)
						percent = 2f;
					else if (j == 2)
						percent = 1f;
					else if (j == 3)
						percent = 1f;
					else if (j == 4)
						percent = 1f;
					break;
				case 6:
					if (j == 0)
						percent = 5f;
					else if (j == 1)
						percent = 2f;
					else if (j == 2)
						percent = 1f;
					else if (j == 3)
						percent = 1f;
					else if (j == 4)
						percent = 0.5f;
					else if (j == 5)
						percent = 0.5f;
					break;
				case 7:
					if (j == 0)
						percent = 5f;
					else if (j == 1)
						percent = 1.5f;
					else if (j == 2)
						percent = 1f;
					else if (j == 3)
						percent = 1f;
					else if (j == 4)
						percent = 0.5f;
					else if (j == 5)
						percent = 0.5f;
					else if (j == 6)
						percent = 0.5f;
					break;
				default:
					if (j == 0)
						percent = 5f;
					else if (j == 1)
						percent = 1.5f;
					else if (j == 2)
						percent = 1f;
					else if (j == 3)
						percent = 1f;
					else if (j == 4)
						percent = 0.5f;
					else if (j == 5)
						percent = 0.5f;
					else if (j == 6)
						percent = 0.5f;
					else
						percent = 0;
					break;

				}
				float f = 0;
				if (percent != 0)
					f = totalFund * pscore * percent / 10;
				m.setPer(percent);
				m.setScore(f);
				jxkhProjectService.update(m);
			}
			// 更新项目的得分
			project.setScore(totalFund * pscore);
			jxkhProjectService.update(project);
		}
	}

	/**
	 * <li>功能描述：保存纵向项目。 void
	 * 
	 * @author songyu
	 * @throws InterruptedException
	 */
	public void onClick$submit() throws InterruptedException {
		if (project == null)
			project = new Jxkh_Project();
		if (projectName.getValue().equals("")) {
			Messagebox.show("项目名称不能为空！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			projectName.focus();
			return;
		}
		if (projectMember.getValue() == null || projectMember.getValue().equals("")) {
			Messagebox.show("请选择成员！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		if (department.getValue() == null || department.getValue().equals("")) {
			Messagebox.show("请选择部门！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}

		if (jiFenTime.getSelectedIndex() == 0 || jiFenTime.getSelectedItem() == null) {
			try {
				Messagebox.show("请选择绩分年度！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		project.setjxYear(jiFenTime.getSelectedItem().getValue() + "");
		project.setName(projectName.getValue());
		project.setCooCompany(coUnit.getValue());
		project.setSumFund(Float.parseFloat(sumFund.getValue()));
		project.setProjecCode(projecCode.getValue());
		project.setHeader(header.getValue());
		project.setEntruster(entruster.getValue());
		project.setSort(Jxkh_Project.HP);
		project.setRecordCode(record.getValue());
		project.setRank((Jxkh_BusinessIndicator) jxkhProjectService.get(Jxkh_BusinessIndicator.class, Long.parseLong("94")));
		if (ifCoo.getSelectedIndex() == 0) {
			project.setCooState(Jxkh_Project.YES);
		} else {
			project.setCooState(Jxkh_Project.NO);
			firstSignTrue.setChecked(true);
		}
		if (firstSignTrue.isChecked()) {
			project.setFirstSign(Jxkh_Project.YES);
		} else {
			project.setFirstSign(Jxkh_Project.NO);
		}
		if (ifEntruster.getSelectedIndex() == 0) {
			project.setIfEntruster(Jxkh_Project.YES);
		} else {
			project.setIfEntruster(Jxkh_Project.NO);
		}

		if (begin.getValue() != null)
			project.setBeginDate(ConvertUtil.convertDateString(begin.getValue()));
		if (end.getValue() != null)
			project.setEndDate(ConvertUtil.convertDateString(end.getValue()));

		int j = 1;
		List<Jxkh_ProjectMember> mlist = new ArrayList<Jxkh_ProjectMember>();
		List<Jxkh_ProjectDept> dlist = new ArrayList<Jxkh_ProjectDept>();
		
		List<Jxkh_ProjectMember> templist = new ArrayList<Jxkh_ProjectMember>();
		
		if (project.getId() != null) {
			projectMemberList = jxkhProjectService.findProjectMember(project);
			if (projectMemberList != null && projectMemberList.size() != 0) {
				
				for(Jxkh_ProjectMember m : projectMemberList){
					Jxkh_ProjectMember mem = new Jxkh_ProjectMember();
					mem.setPer(m.getPer());
					mem.setScore(m.getScore());
					mem.setPersonId(m.getPersonId());
					templist.add(mem);
				}
				
				jxkhProjectService.deleteAll(projectMemberList);
			}
		}
		if (memberList != null && memberList.size() != 0) {
			for (WkTUser user : memberList) {
				Jxkh_ProjectMember member = new Jxkh_ProjectMember();

				member.setProject(project);
				member.setDept(user.getDept().getKdName());
				member.setName(user.getKuName());
				member.setPersonId(user.getKuLid());
				member.setRank(j);
				
				if(templist.size() != 0 && templist != null){
					for(Jxkh_ProjectMember m :templist){
						if(member.getPersonId() != null && m.getPersonId() != null 
								&& member.getPersonId().equals(m.getPersonId())){
							member.setPer(m.getPer());
							member.setScore(m.getScore());
						}
					}
				}
				
				switch (user.getType()) {
				case WkTUser.TYPE_IN:
					member.setType(Short.valueOf(WkTUser.TYPE_IN));
					break;
				case WkTUser.TYPE_OUT:
					member.setType(Short.valueOf(WkTUser.TYPE_OUT));
					break;
				}

				j++;
				mlist.add(member);
			}
			project.setProjectMember(mlist);
		}
		int i = 1;
		List<Jxkh_ProjectDept> tempDlist = new ArrayList<Jxkh_ProjectDept>();
		if (project.getId() != null) {
			projectDeptList = jxkhProjectService.findProjectDept(project);
			if (projectDeptList != null && projectDeptList.size() != 0) {
				
				for(Jxkh_ProjectDept d: projectDeptList){
					if(d != null && d.getProject() != null && d.getScore() != null){
						Jxkh_ProjectDept dept = new Jxkh_ProjectDept();
						dept.setProject(d.getProject());
						dept.setScore(d.getScore());
						tempDlist.add(dept);
					}
					
				}
				
				jxkhProjectService.deleteAll(projectDeptList);
			}
		}
		if (deptList != null && deptList.size() != 0) {
			for (WkTDept wktDept : deptList) {
				Jxkh_ProjectDept dept = new Jxkh_ProjectDept();
				dept.setProject(project);
				dept.setDeptId(wktDept.getKdNumber());
				dept.setName(wktDept.getKdName());
				dept.setRank(i);
				
				for(Jxkh_ProjectDept  d:tempDlist){
					if(d.getProject().getId().equals(dept.getProject().getId())){
						dept.setScore(d.getScore());
					}
				}
				
				i++;
				dlist.add(dept);
			}
			project.setProjectDept(dlist);
		}
		project.setjxYear(jiFenTime.getSelectedItem().getValue() + "");
		try {
			jxkhProjectService.saveOrUpdate(project);
			
			projectMemberList = jxkhProjectService.findProjectMember(project);
			personScore.setModel(new ListModelList(projectMemberList));
			
			
			Messagebox.show("横向项目保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			if (funds.isDisabled())
				funds.setDisabled(false);
			fileTab.setDisabled(false);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				Messagebox.show("横向项目保存失败，请重试！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		// initShow();
	}

	/**
	 * <li>功能描述：关闭当前窗口。 void
	 * 
	 * @author songyu
	 */
	public void onClick$close() {
		this.onClose();
	}

	public void onClick$close1() {
		this.onClose();
	}

	/** 基金列表渲染器 */
	public class FundsRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			Jxkh_ProjectFund fund = (Jxkh_ProjectFund) data;
			item.setValue(fund);
			Listcell c0 = new Listcell();
			Listcell c1 = new Listcell(item.getIndex() + 1 + "");
			Listcell c2 = new Listcell(fund.getMoney() + "");
			c2.setTooltiptext("点击金额编辑基金信息");
			c2.setStyle("color:blue");
			c2.addEventListener(Events.ON_CLICK, new EditListener());
			String strC3 = null;
			switch (fund.getType()) {
			case Jxkh_ProjectFund.IN:
				strC3 = "经费收入";
				break;
			case Jxkh_ProjectFund.OUT:
				strC3 = "院外拨款支出";
				break;
			// default:strC3 = "未审核";break;
			}
			Listcell c3 = new Listcell(strC3);
			Listcell c5 = new Listcell();
			if (fund.getDate() == null || fund.getDate().length() == 0) {
				c5.setLabel("");
			} else {
				c5.setLabel(fund.getDate());
			}
			Listcell c6 = new Listcell(fund.getjxYear());
			Listcell c07 = new Listcell();
			if (fund.getDeptNum() != null) {
				WkTDept dept = (WkTDept) departmentService.get(WkTDept.class, fund.getDeptNum());
				c07.setLabel(dept.getKdName());
			}
			Listcell c7 = new Listcell(fund.getTransactor());
			item.appendChild(c0);
			item.appendChild(c1);
			item.appendChild(c2);
			item.appendChild(c3);
			item.appendChild(c5);
			item.appendChild(c6);
			item.appendChild(c07);
			item.appendChild(c7);
		}
	}

	/** 添加专项经费 */
	public void onClick$addFund1() {
		AddFundWindow w = (AddFundWindow) Executions.createComponents("/admin/jxkh/busiAudit/project/addFund.zul", null, null);
		try {
			w.setProject(project);
			w.initListbox();
			w.setSort(Jxkh_ProjectFund.ZXF);
			w.addEventListener(Events.ON_CHANGE, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					// 基金
					List<Jxkh_ProjectFund> fundList1 = jxkhProjectService.findFunds(project, Jxkh_ProjectFund.ZXF);
					fundsListbox1.setModel(new ListModelList(fundList1));
					List<Jxkh_ProjectMember> mlist = jxkhProjectService.findProjectMember(project);
					if(scoreTab.isDisabled()) 
						scoreTab.setDisabled(false);
					personScore.setModel(new ListModelList(mlist));
					List<Jxkh_ProjectDept> dlist = jxkhProjectService.findProjectDep(project);
					departmentScore.setModel(new ListModelList(dlist));
					initShow();
				}
			});
			w.doModal();
		} catch (SuspendNotAllowedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	/** 监听经费金额列的单击事件 */
	public class EditListener implements EventListener {
		@Override
		public void onEvent(Event event) throws Exception {
			Listitem item = (Listitem) event.getTarget().getParent();
			Jxkh_ProjectFund fund = (Jxkh_ProjectFund) item.getValue();
			EditFundWindow w = (EditFundWindow) Executions.createComponents("/admin/jxkh/busiAudit/project/editFund.zul", null, null);
			try {
				w.setFund(fund);
				w.initListbox();
				w.addEventListener(Events.ON_CHANGE, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						if (scoreTab.isDisabled()) {
							scoreTab.setDisabled(false);
						}
						List<Jxkh_ProjectMember> mlist = jxkhProjectService.findProjectMember(project);
						personScore.setModel(new ListModelList(mlist));
						List<Jxkh_ProjectDept> dlist = jxkhProjectService.findProjectDep(project);
						departmentScore.setModel(new ListModelList(dlist));
						initShow();
					}
				});
				w.doModal();
			} catch (SuspendNotAllowedException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}

	/**
	 * <li>功能描述：删除专项经费 void
	 * 
	 * @author songyu
	 */
	public void onClick$delFund1() throws InterruptedException {
		if (fundsListbox1.getSelectedItems() == null || fundsListbox1.getSelectedItems().size() <= 0) {
			try {
				Messagebox.show("请选择要删除的经费信息！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		Messagebox.show("确定删除吗?", "确定", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
			public void onEvent(Event evt) throws InterruptedException {
				if (evt.getName().equals("onOK")) {
					Iterator<?> items = fundsListbox1.getSelectedItems().iterator();
					List<Jxkh_ProjectFund> fundList = new ArrayList<Jxkh_ProjectFund>();
					Jxkh_ProjectFund fund = new Jxkh_ProjectFund();
					while (items.hasNext()) {
						Listitem item = (Listitem) items.next();
						fund = (Jxkh_ProjectFund) item.getValue();
						fundList.add(fund);
					}
					jxkhProjectService.deleteAll(fundList);
					List<Jxkh_ProjectFund> fundList1 = jxkhProjectService.findFunds(project, Jxkh_ProjectFund.ZXF);
					fundsListbox1.setModel(new ListModelList(fundList1));
					//计算分值
					computeScore();
					List<Jxkh_ProjectMember> mlist = jxkhProjectService.findProjectMember(project);
					personScore.setModel(new ListModelList(mlist));
					List<Jxkh_ProjectDept> dlist = jxkhProjectService.findProjectDep(project);
					departmentScore.setModel(new ListModelList(dlist));
					try {
						Messagebox.show("经费信息删除成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		initShow();
	}

	/**
	 * <li>功能描述：删除自筹经费 void
	 * 
	 * @author songyu
	 */

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

	// 单击选择成员按钮，触发弹出选择成员页面事件
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
				projectMember.setValue(members);
				win.detach();
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
				if (ui.getDept().getKdName() == "校外" || ui.getDept().getKdId().equals(uj.getDept().getKdId())) {
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
		department.setValue(null);
		department.setValue(depts);
	}

	// 单击选择部门按钮，触发弹出选择成员页面事件
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
				department.setValue(depts);
				win.detach();
			}
		});
		win.doModal();
	}

	public Jxkh_Project getProject() {
		return project;
	}

	public void setProject(Jxkh_Project project) {
		this.project = project;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getDept1() {
		return dept1;
	}

	public void setDept1(String dept1) {
		this.dept1 = dept1;
	}

	// 20120318
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
				arr[3] = "项目合同书";
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
	 * <li>功能描述：文档信息的listbox(20120305)
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
					Messagebox.show("确定删除吗?", "确定", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
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

	// 20120305
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
				arr[3] = "年度进度文档";
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
	 * <li>功能描述：文档信息的listbox(20120305)
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
					Messagebox.show("确定删除吗?", "确定", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
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

	public void download(String fpath, String fname) throws InterruptedException {
		File file = new File(UploadUtil.getRootPath() + fpath);
		if (file.exists()) {
			try {
				Filedownload.save(file, fname);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			Messagebox.show("无法下载，可能是因为文件没有被上传！ ", "错误", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/** 个人绩分渲染器 */
	public class personScoreRenderer implements ListitemRenderer {

		@Override
		public void render(Listitem item, Object data) throws Exception {

			if (data == null)
				return;
			final Jxkh_ProjectMember member = (Jxkh_ProjectMember) data;
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
			if (member.getAssignDep() == null || member.getAssignDep().equals("")) {
				bar.setLabel("指定");
				bar.setStyle("color:blue");
			} else {
				bar.setLabel(member.getAssignDep());
			}
			c6.appendChild(bar);
			Listcell c7 = new Listcell();
			if (member.getScore() != null)
				c7.setLabel(member.getScore() + "");

			final List<Jxkh_ProjectDept> temp = jxkhProjectService.findProjectDep(project);
			// 弹出指定部门页面
			bar.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event event) throws Exception {

					AssignDeptWindow w = (AssignDeptWindow) Executions.createComponents("/admin/personal/businessdata/meeting/assignDept.zul", null, null);
					try {
						w.setFlag("hp");
						w.setState(project.getState());
						w.setDept(temp);
						w.setMember(member);
						w.initWindow();
						w.doModal();
						// 指定部门完成后自动计算得分
						w.addEventListener(Events.ON_CHANGE, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								List<Jxkh_ProjectMember> tempMemberList = jxkhProjectService.findProjectMember(project);
								personScore.setModel(new ListModelList(tempMemberList));
								for (int k = 0; k < projectDeptList.size(); k++) {
									Jxkh_ProjectDept d = projectDeptList.get(k);
									float f = 0.0f;
									for (int i = 0; i < tempMemberList.size(); i++) {
										Jxkh_ProjectMember m = tempMemberList.get(i);
										if (m.getAssignDep() == null || m.getAssignDep().equals("")) {
											if (m.getDept().equals(d.getName())) {
												f += m.getScore();
											}
										} else if (m.getAssignDep().equals(d.getName())) {
											f += m.getScore();
										}
									}
									d.setScore(f);
									jxkhMeetingService.update(d);
								}
								List<Jxkh_ProjectDept> tempDeptList = jxkhProjectService.findProjectDept(project);
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
			// item.appendChild(c6);
			item.appendChild(c7);
		}
	}

	/** 部门绩分列表渲染器 */
	public class departmentScoreRenderer implements ListitemRenderer {
		public void render(Listitem arg0, Object arg1) throws Exception {
			if (arg0 == null)
				return;
			final Jxkh_ProjectDept dept = (Jxkh_ProjectDept) arg1;
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
		for (int i = 0; i < projectMemberList.size(); i++) {
			Listitem item = personScore.getItemAtIndex(i);
			Listcell lc = (Listcell) item.getChildren().get(4);
			Textbox temp = (Textbox) lc.getChildren().get(0);
			if (temp.getValue() != null && temp.getValue() != "")
				try {
					a += Float.parseFloat(temp.getValue());
				} catch (Exception e) {
					e.printStackTrace();
					try {
						Messagebox.show("只能输入数字！", "提示", Messagebox.OK, Messagebox.EXCLAMATION);
						return;
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}

		}
		if (a != 10.0) {
			try {
				Messagebox.show("请检查人员所占比例的总和是否为10.0！", "提示", Messagebox.OK, Messagebox.EXCLAMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		for (int i = 0; i < projectMemberList.size(); i++) {
			Listitem item = personScore.getItemAtIndex(i);
			Listcell lc = (Listcell) item.getChildren().get(4);
			Textbox temp = (Textbox) lc.getChildren().get(0);
			float s = 0.0f;// 比例
			float f = 0.0f;// 分值
			if (temp.getValue() != null && temp.getValue() != "")
				s = Float.parseFloat(temp.getValue());
			if (project.getScore() != null)
				f = s * project.getScore() / 10;
			float score = (float) (Math.round(f * 1000)) / 1000;
			Jxkh_ProjectMember member = (Jxkh_ProjectMember) item.getValue();
			member.setPer(s);
			member.setScore(score);
			jxkhProjectService.update(member);
		}
		List<Jxkh_ProjectMember> mlist = jxkhProjectService.findProjectMember(project);
		personScore.setModel(new ListModelList(mlist));
		try {
			Messagebox.show("绩分信息保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// 绩分信息的关闭按钮
	public void onClick$closeScore() {
		this.onClose();
	}

	public void onClick$submitFund() {
		computeScore();
		if (scoreTab.isDisabled()) {
			scoreTab.setDisabled(false);
		}
		scoreTab.setSelected(true);
		List<Jxkh_ProjectMember> mlist = jxkhProjectService.findProjectMember(project);
		personScore.setModel(new ListModelList(mlist));
		List<Jxkh_ProjectDept> dlist = jxkhProjectService.findProjectDep(project);
		departmentScore.setModel(new ListModelList(dlist));
	}

	public void onClick$closeFund() {
		this.detach();
	}
	
	public void onClick$fileSubmit() {
		// 新的附件保存20120306
		List<String[]> arrList = new ArrayList<String[]>();
		Set<Jxkh_ProjectFile> fset=new HashSet<Jxkh_ProjectFile>();
		if (project.getProjectFile() != null) {
			for (Object o : project.getProjectFile().toArray()) {
				Jxkh_ProjectFile f = (Jxkh_ProjectFile) o;
				if (f != null)
					jxkhProjectService.delete(f);
			}
		}
		if(project.getProjectFile() != null)
			project.getProjectFile().clear();
		if (arrList1 != null && arrList1.size() != 0)
			arrList.addAll(arrList1);
		if (arrList2 != null && arrList2.size() != 0)
			arrList.addAll(arrList2);
		for (int r = 0; r < arrList.size(); r++) {
			String[] s = arrList.get(r);
			Jxkh_ProjectFile file = new Jxkh_ProjectFile();
			file.setProject(project);
			file.setPath(s[0]);
			file.setName(s[1]);
			file.setSubmitDate(s[2]);
			file.setBelongType(s[3]);
			fset.add(file);
		}
		if (fset != null && fset.size() > 0) {
			project.setProjectFile(fset);
			try {
				jxkhProjectService.update(project);
				Messagebox.show("保存成功！");
			} catch (InterruptedException e) {
				try {
					Messagebox.show("保存失败！");
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		} else {
			try {
				Messagebox.show("请上传文件");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void onClick$fileClose() {
		this.detach();
	}
}
