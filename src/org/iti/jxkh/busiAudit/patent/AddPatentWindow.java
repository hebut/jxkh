package org.iti.jxkh.busiAudit.patent;

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
import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.entity.Jxkh_Patent;
import org.iti.jxkh.entity.Jxkh_PatentDept;
import org.iti.jxkh.entity.Jxkh_PatentFile;
import org.iti.jxkh.entity.Jxkh_PatentInventor;
import org.iti.jxkh.service.BusinessIndicatorService;
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
import com.iti.common.util.PropertiesLoader;
import com.iti.common.util.UploadUtil;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;

public class AddPatentWindow extends Window implements AfterCompose {

	/**
	 * @author SongYu
	 */
	private static final long serialVersionUID = -3664613977051724505L;
	private Tab baseTab, fileTab, scoreTab;
	private Listbox personScore, departmentScore;
	private Toolbarbutton submit, close/* , submitScore */;
	WkTUser user;
	private JxkhProjectService jxkhProjectService;
	private BusinessIndicatorService businessIndicatorService;
	private Textbox projectName, projectMember, department, coUnit, projecCode, header;
	private Label writer;
	private Datebox begin, end;
	private Listbox rank;
	private Radiogroup ifCoo;
	private Row coUnitRow, recordlabel;
	private Button print;
	private YearListbox jiFenTime;
	private Jxkh_Patent project;
	private String dept = "";
	private String dept1 = "";
	private Hbox recordhbox;
	private Textbox record;
	private Radio firstSignTrue, firstSignFalse;
	private List<WkTUser> memberList = new ArrayList<WkTUser>();
	private List<WkTDept> deptList = new ArrayList<WkTDept>();
	private List<Jxkh_PatentInventor> projectMemberList = new ArrayList<Jxkh_PatentInventor>();
	private List<Jxkh_PatentDept> projectDeptList = new ArrayList<Jxkh_PatentDept>();

	private Listbox applyList1, applyList2, applyList3;
	private List<Jxkh_PatentFile> fileList1;
	private List<Jxkh_PatentFile> fileList2;
	private List<Jxkh_PatentFile> fileList3;

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");// 获取当前登录用户
		rank.setItemRenderer(new RankRenderer());
		personScore.setItemRenderer(new personScoreRenderer());
		departmentScore.setItemRenderer(new departmentScoreRenderer());
		jiFenTime.initYearListbox("");
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
		fileTab.addEventListener(Events.ON_CLICK, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				submit.setVisible(true);
				close.setVisible(true);
			}
		});
	}

	public void initWindow() {
		projectName.setValue(project.getName());
		projecCode.setValue(project.getGrantCode());
		header.setValue(project.getOwner());
		WkTUser infoWriter = jxkhProjectService.findWktUserByMemberUserId(project.getInfoWriter());
		writer.setValue(infoWriter.getKuName());
		if (project.getApplyDate() == null || project.getApplyDate().length() == 0) {
			begin.setValue(null);
		} else {
			begin.setValue(ConvertUtil.convertDate(project.getApplyDate()));
		}
		if (project.getGrantDate() == null || project.getGrantDate().length() == 0) {
			end.setValue(null);
		} else {
			end.setValue(ConvertUtil.convertDate(project.getGrantDate()));
		}
		jiFenTime.initYearListbox(project.getjxYear());
		if (project.getCooState() != null) {
			if (project.getCooState() == Jxkh_Patent.YES) {
				ifCoo.setSelectedIndex(0);
				coUnitRow.setVisible(true);
				coUnit.setValue(project.getCooCompany());
			} else {
				ifCoo.setSelectedIndex(1);
			}
		}
		if (project.getFirstSign() != null) {
			if (project.getFirstSign() == Jxkh_Patent.YES) {
				firstSignTrue.setChecked(true);
				firstSignFalse.setChecked(false);
			} else {
				firstSignTrue.setChecked(false);
				firstSignFalse.setChecked(true);
			}
		}
		if (project.getState() == Jxkh_Patent.SAVE_RECORD) {
			record.setVisible(true);
			recordlabel.setVisible(true);
			recordhbox.setVisible(true);
			record.setValue(project.getRecordCode());
		}
		String memberName = "";
		String deptName = "";
		// 成员 部门
		projectMemberList = jxkhProjectService.findPatentMember(project);
		if (projectMemberList != null && projectMemberList.size() != 0) {
			for (int i = 0; i < projectMemberList.size(); i++) {
				Jxkh_PatentInventor mem = (Jxkh_PatentInventor) projectMemberList.get(i);
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
		projectDeptList = jxkhProjectService.findPatentDept(project);
		if (projectDeptList != null && projectDeptList.size() != 0) {
			for (int i = 0; i < projectDeptList.size(); i++) {
				Jxkh_PatentDept dept = (Jxkh_PatentDept) projectDeptList.get(i);
				deptName += dept.getName() + "、";
				WkTDept depts = jxkhProjectService.findWkTDeptByDept(dept.getDeptId());
				deptList.add(depts);
			}
			department.setValue(deptName.substring(0, deptName.length() - 1));
		}
		// 新的附件初始化（20120306）
		fileList1 = jxkhProjectService.findFilesByIdAndType(project, "申请通知书");
		arrList1.clear();
		for (int x = 0; x < fileList1.size(); x++) {
			Jxkh_PatentFile f = fileList1.get(x);
			String[] arr = new String[4];
			arr[0] = f.getPath();
			arr[1] = f.getName();
			arr[2] = f.getSubmitDate();
			arr[3] = f.getBelongType();
			arrList1.add(arr);
		}
		applyList1.setItemRenderer(new FilesRenderer1());
		applyList1.setModel(new ListModelList(arrList1));

		fileList2 = jxkhProjectService.findFilesByIdAndType(project, "授权通知书");
		arrList2.clear();
		for (int x = 0; x < fileList2.size(); x++) {
			Jxkh_PatentFile f = fileList2.get(x);
			String[] arr = new String[4];
			arr[0] = f.getPath();
			arr[1] = f.getName();
			arr[2] = f.getSubmitDate();
			arr[3] = f.getBelongType();
			arrList2.add(arr);
		}
		applyList2.setItemRenderer(new FilesRenderer2());
		applyList2.setModel(new ListModelList(arrList2));

		fileList3 = jxkhProjectService.findFilesByIdAndType(project, "授权证书");
		arrList3.clear();
		for (int x = 0; x < fileList3.size(); x++) {
			Jxkh_PatentFile f = fileList3.get(x);
			String[] arr = new String[4];
			arr[0] = f.getPath();
			arr[1] = f.getName();
			arr[2] = f.getSubmitDate();
			arr[3] = f.getBelongType();
			arrList3.add(arr);
		}
		applyList3.setItemRenderer(new FilesRenderer3());
		applyList3.setModel(new ListModelList(arrList3));

		personScore.setModel(new ListModelList(projectMemberList));
		departmentScore.setModel(new ListModelList(projectDeptList));
	}

	public void initShow() {
		if (project == null) {
			print.setVisible(false);
			scoreTab.setDisabled(true);
			fileTab.setDisabled(true);
		} else {
			// documents.setDisabled(false);
			print.setHref("/print.action?n=patent&id=" + project.getId());
			scoreTab.setDisabled(false);
			fileTab.setDisabled(false);
		}

		List<Jxkh_BusinessIndicator> typeList = new ArrayList<Jxkh_BusinessIndicator>();
		typeList.add(new Jxkh_BusinessIndicator());
		typeList.addAll(jxkhProjectService.findSort());
		rank.setModel(new ListModelList(typeList));

	}

	/**
	 * <li>功能描述：保存纵向项目。 void
	 * 
	 * @author songyu
	 * @throws InterruptedException
	 */
	public void onClick$submit() throws InterruptedException {
		if (project == null) {
			project = new Jxkh_Patent();
			writer.setValue(user.getKuName());
		}
		if (projectName.getValue().equals("")) {
			Messagebox.show("专利(软件)名称不能为空！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			projectName.focus();
			return;
		}
		if (rank.getSelectedItem() == null || rank.getSelectedItem().getIndex() == 0) {
			Messagebox.show("专利(软件)类型不能为空！", "提示", Messagebox.OK, Messagebox.INFORMATION);
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
		if (project != null && project.getId() != null) {
			if (arrList1.size() <= 0) {
				Messagebox.show("申请通知书必须上传！");
				return;
			}
			if (arrList2.size() <= 0 && arrList3.size() <= 0) {
				Messagebox.show("授权通知书和授权证书至少上传一项！");
				return;
			}
		}
		Short sign;
		Jxkh_BusinessIndicator kdRank = new Jxkh_BusinessIndicator();
		project.setName(projectName.getValue());
		project.setjxYear(jiFenTime.getSelectedItem().getValue() + "");
		project.setCooCompany(coUnit.getValue());
		project.setGrantCode(projecCode.getValue());
		project.setOwner(header.getValue());
		if (rank.getSelectedItem() != null)
			project.setSort((Jxkh_BusinessIndicator) rank.getSelectedItem().getValue());
		if (ifCoo.getSelectedIndex() == 0) {
			project.setCooState(Jxkh_Patent.YES);
		} else {
			project.setCooState(Jxkh_Patent.NO);
			firstSignTrue.setChecked(true);
		}
		if (firstSignTrue.isChecked()) {
			project.setFirstSign(Jxkh_Patent.YES);
			sign = Jxkh_Patent.YES;
		} else {
			project.setFirstSign(Jxkh_Patent.NO);
			sign = Jxkh_Patent.NO;
		}
		if (rank.getSelectedItem() != null) {
			project.setSort((Jxkh_BusinessIndicator) rank.getSelectedItem().getValue());
			kdRank = (Jxkh_BusinessIndicator) rank.getSelectedItem().getValue();
		}
		Float score = computeScore(kdRank, sign);
		project.setScore(score);
		if (begin.getValue() != null)
			project.setApplyDate(ConvertUtil.convertDateString(begin.getValue()));
		if (end.getValue() != null)
			project.setGrantDate(ConvertUtil.convertDateString(end.getValue()));

		int j = 1;
		List<Jxkh_PatentInventor> mlist = new ArrayList<Jxkh_PatentInventor>();
		List<Jxkh_PatentDept> dlist = new ArrayList<Jxkh_PatentDept>();
		if (project.getId() != null) {
			projectMemberList = jxkhProjectService.findPatentMember(project);
			if (projectMemberList != null && projectMemberList.size() != 0) {
				jxkhProjectService.deleteAll(projectMemberList);
			}
		}
		if (memberList != null && memberList.size() != 0) {
			for (WkTUser user : memberList) {
				Jxkh_PatentInventor member = new Jxkh_PatentInventor();
				member.setPatent(project);
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
				if ((user.getKdId() != null && user.getDept() != null && user.getDept().getKdNumber().equals(WkTDept.guanlidept)) || user.getKdId() == null)
					member.setAssignDep(deptList.get(0).getKdName());
				else
					member.setAssignDep(user.getDept().getKdName());
				j++;
				mlist.add(member);
			}
			project.setPatentInventor(mlist);
		}
		int i = 1;
		if (project.getId() != null) {
			projectDeptList = jxkhProjectService.findPatentDept(project);
			if (projectDeptList != null && projectDeptList.size() != 0) {
				jxkhProjectService.deleteAll(projectDeptList);
			}
		}
		if (deptList != null && deptList.size() != 0) {
			for (WkTDept wktDept : deptList) {
				Jxkh_PatentDept dept = new Jxkh_PatentDept();
				dept.setPatent(project);
				dept.setDeptId(wktDept.getKdNumber());
				dept.setName(wktDept.getKdName());
				dept.setRank(i);
				i++;
				// 部门默认的分值0503
				float fen = 0.0f;
				for (int g = 0; g < mlist.size(); g++) {
					Jxkh_PatentInventor m = mlist.get(g);
					if (m.getAssignDep().equals(dept.getName())) {
						fen += m.getScore();
					}
				}
				float scor = (float) (Math.round(fen * 1000)) / 1000;// 保留三位小数
				dept.setScore(scor);
				dlist.add(dept);
			}
			project.setPatentDept(dlist);
		}
		List<String[]> arrList = new ArrayList<String[]>();
		Set<Jxkh_PatentFile> fset = new HashSet<Jxkh_PatentFile>();
		if (project.getPatentFile() != null) {
			for (Object o : project.getPatentFile().toArray()) {
				Jxkh_PatentFile f = (Jxkh_PatentFile) o;
				if (f != null)
					jxkhProjectService.delete(f);
			}
		}
		if (project.getPatentFile() != null)
			project.getPatentFile().clear();
		if (arrList1 != null && arrList1.size() != 0)
			arrList.addAll(arrList1);
		if (arrList2 != null && arrList2.size() != 0)
			arrList.addAll(arrList2);
		if (arrList3 != null && arrList3.size() != 0)
			arrList.addAll(arrList3);
		for (int r = 0; r < arrList.size(); r++) {
			String[] s = arrList.get(r);
			Jxkh_PatentFile file = new Jxkh_PatentFile();
			file.setPatent(project);
			file.setPath(s[0]);
			file.setName(s[1]);
			file.setSubmitDate(s[2]);
			file.setBelongType(s[3]);
			fset.add(file);
		}
		project.setPatentFile(fset);

		try {
			jxkhProjectService.saveOrUpdate(project);
			Messagebox.show("专利(软件)保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			scoreTab.setDisabled(false);
			fileTab.setDisabled(false);
			personScore.setModel(new ListModelList(mlist));
			departmentScore.setModel(new ListModelList(dlist));
		} catch (Exception e) {
			e.printStackTrace();
			try {
				Messagebox.show("专利(软件)保存失败，请重试！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		initShow();
	}

	private Float computeScore(Jxkh_BusinessIndicator kdRank, Short sign) {
		float result = 0f;
		String type = "";
		float res = 0f;
		Properties property = PropertiesLoader.loader("title", "title.properties");
		float foreigninvent = 0, invent = 0, applied = 0, software = 0;

		if (kdRank.getKbName().equals(property.getProperty("foreigninvent"))) {
			if (sign.shortValue() == Jxkh_Patent.YES) {
				foreigninvent += 1f;
				result = foreigninvent;
				type = property.getProperty("foreigninvent");
			} else if (sign.shortValue() == Jxkh_Patent.NO) {
				invent += 1f;
				result = invent;
				type = property.getProperty("invent");
			}
		} else if (kdRank.getKbName().equals(property.getProperty("invent"))) {
			if (sign.shortValue() == Jxkh_Patent.YES) {
				invent += 1f;
				result = invent;
				type = property.getProperty("invent");
			} else if (sign.shortValue() == Jxkh_Patent.NO) {
				applied += 1f;
				result = applied;
				type = property.getProperty("applied");
			}
		} else if (kdRank.getKbName().equals(property.getProperty("applied"))) {
			if (sign.shortValue() == Jxkh_Patent.YES) {
				applied += 1f;
				result = applied;
				type = property.getProperty("applied");
			} else if (sign.shortValue() == Jxkh_Patent.NO) {
				software += 1f * 0.5f;
				result = software;
				type = property.getProperty("applied");
			}
		} else if (kdRank.getKbName().equals(property.getProperty("software"))) {
			if (sign.shortValue() == Jxkh_Patent.YES) {
				software += 1f;
				result = software;
				type = property.getProperty("software");
			} else if (sign.shortValue() == Jxkh_Patent.NO) {
				software += 1f * 0.5f;
				result = software;
				type = property.getProperty("software");
			}
		}

		Jxkh_BusinessIndicator bi = (Jxkh_BusinessIndicator) businessIndicatorService.getEntityByName(type);
		if (bi == null) {
			res += result * 0;
		} else {
			res += result * bi.getKbScore() * bi.getKbIndex();
		}
		return Float.valueOf(res);

	}

	/**
	 * <li>功能描述：关闭当前窗口。 void
	 * 
	 * @author songyu
	 */
	public void onClick$close() {
		this.onClose();
	}

	/** 项目级别列表渲染器 */
	public class RankRenderer implements ListitemRenderer {
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
			if (project != null && type.equals(project.getSort())) {
				item.setSelected(true);
			}
		}
	}

	// 单击选择成员按钮，触发弹出选择成员页面事件
	public void onClick$chooseMember() throws SuspendNotAllowedException, InterruptedException {
		final ChooseMemberWin win = (ChooseMemberWin) Executions.createComponents("/admin/jxkhl/busiAudit/award/choosemember.zul", null, null);
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
				arr[3] = "申请通知书";
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
				arr[3] = "授权通知书";
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
				arr[3] = "授权证书";
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
	 * <li>功能描述：文档信息的listbox(20120306)
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
					Messagebox.show("确定删除吗?", "确定", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
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
			Messagebox.show("无法下载，可能是因为文件没有被上传！ ", "错误", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public Jxkh_Patent getProject() {
		return project;
	}

	public void setProject(Jxkh_Patent project) {
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

	/** 个人绩分渲染器 */
	public class personScoreRenderer implements ListitemRenderer {

		@Override
		public void render(Listitem item, Object data) throws Exception {

			if (data == null)
				return;
			final Jxkh_PatentInventor member = (Jxkh_PatentInventor) data;
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

			final List<Jxkh_PatentDept> temp = jxkhProjectService.findPatentDep(project);
			// 弹出指定部门页面
			bar.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event event) throws Exception {

					AssignDeptWindow w = (AssignDeptWindow) Executions.createComponents("/admin/jxkh/busiAudit/meeting/assignDept.zul", null, null);
					try {
						w.setFlag("patent");
						w.setDept(temp);
						w.setMember(member);
						w.initWindow();
						w.doModal();

						// 指定部门完成后自动计算得分
						w.addEventListener(Events.ON_CHANGE, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								List<Jxkh_PatentInventor> tempMemberList = jxkhProjectService.findPatentMember(project);
								personScore.setModel(new ListModelList(tempMemberList));
								for (int k = 0; k < projectDeptList.size(); k++) {
									Jxkh_PatentDept d = projectDeptList.get(k);
									float f = 0.0f;
									for (int i = 0; i < tempMemberList.size(); i++) {
										Jxkh_PatentInventor m = tempMemberList.get(i);
										if (m.getAssignDep() == null || m.getAssignDep().equals("")) {
											if (m.getDept().equals(d.getName())) {
												f += m.getScore();
											}
										} else if (m.getAssignDep().equals(d.getName())) {
											f += m.getScore();
										}
									}
									d.setScore(f);
									jxkhProjectService.update(d);
								}
								List<Jxkh_PatentDept> tempDeptList = jxkhProjectService.findPatentDept(project);
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

	/** 部门绩分列表渲染器 */
	public class departmentScoreRenderer implements ListitemRenderer {

		@Override
		public void render(Listitem arg0, Object arg1) throws Exception {
			if (arg0 == null)
				return;
			final Jxkh_PatentDept dept = (Jxkh_PatentDept) arg1;
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
			Jxkh_PatentInventor member = (Jxkh_PatentInventor) item.getValue();
			member.setPer(s);
			member.setScore(score);
			jxkhProjectService.update(member);
		}
		List<Jxkh_PatentInventor> tempMemberList = jxkhProjectService.findPatentMember(project);
		personScore.setModel(new ListModelList(tempMemberList));
		/*for (int k = 0; k < projectDeptList.size(); k++) {
			Jxkh_PatentDept d = projectDeptList.get(k);
			float f = 0.0f;
			for (int i = 0; i < tempMemberList.size(); i++) {
				Jxkh_PatentInventor m = tempMemberList.get(i);
				if (m.getAssignDep() == null || m.getAssignDep().equals("")) {
					if (m.getDept().equals(d.getName())) {
						f += m.getScore();
					}
				} else if (m.getAssignDep().equals(d.getName())) {
					f += m.getScore();
				}
			}
			d.setScore(f);
			jxkhProjectService.update(d);
		}*/
		try {
			Messagebox.show("绩分信息保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		List<Jxkh_PatentDept> tempDeptList = jxkhProjectService.findPatentDept(project);
		departmentScore.setModel(new ListModelList(tempDeptList));
	}

	// 绩分信息的关闭按钮
	public void onClick$closeScore() {
		this.onClose();
	}
}
