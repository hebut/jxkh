package org.iti.jxkh.audit.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.iti.jxkh.business.project.AddFundWindow;
import org.iti.jxkh.business.project.EditFundWindow;
import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.entity.Jxkh_Project;
import org.iti.jxkh.entity.Jxkh_ProjectDept;
import org.iti.jxkh.entity.Jxkh_ProjectFile;
import org.iti.jxkh.entity.Jxkh_ProjectFund;
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
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
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

public class ZPWindow extends Window implements AfterCompose {

	/**
	 * @author SongYu
	 */
	private static final long serialVersionUID = -3664613977051724505L;
	WkTUser user;
	private JxkhProjectService jxkhProjectService;
	private Tab funds;
	private Textbox projectName, projectMember, department, coUnit, sumFund,
			projecCode, takeCompany, header, standDept;
	private Label yearFund, writer;
	private Datebox begin, end;
	private Listbox rank, progress, fundsListbox1, fundsListbox2;
	private Radiogroup ifCoo, ifHum, ifSoft;
	private Row coUnitRow;
	private Button print;
	private Jxkh_Project project;
	private String subnum, outnum, innum;
	private String dept = "";
	private Toolbarbutton addFund1, delFund1, addFund2, delFund2;
	private List<WkTUser> memberList = new ArrayList<WkTUser>();
	private List<WkTDept> deptList = new ArrayList<WkTDept>();
	private List<Jxkh_ProjectMember> projectMemberList = new ArrayList<Jxkh_ProjectMember>();
	private List<Jxkh_ProjectDept> projectDeptList = new ArrayList<Jxkh_ProjectDept>();
	private Radio firstSignTrue, firstSignFalse;
	/**
	 * 添加文档和积分选项卡
	 */
	private Listbox applyList1, applyList2, applyList3;
	private Listbox personScore, departmentScore;
	private Set<Jxkh_ProjectFile> fileList1;
	private List<String[]> arrList1 = new ArrayList<String[]>();
	private List<String[]> arrList2 = new ArrayList<String[]>();
	private List<String[]> arrList3 = new ArrayList<String[]>();
	
	private DepartmentService departmentService;

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");// 获取当前登录用户
		writer.setValue(user.getKuName());
		rank.setItemRenderer(new RankRenderer());
		progress.setItemRenderer(new TypeRenderer());
		fundsListbox1.setItemRenderer(new FundsRenderer());
		fundsListbox2.setItemRenderer(new FundsRenderer());
		
		personScore.setItemRenderer(new ListitemRenderer() {
			
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
				Textbox t = new Textbox();
				t.setDisabled(true);
				t.setParent(c5);
				if (member.getPer() != null)
					t.setValue(member.getPer() + "");
				//指定积分归属
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

				item.appendChild(c1);
				item.appendChild(c2);
				item.appendChild(c3);
				item.appendChild(c4);
				item.appendChild(c5);
				item.appendChild(c6);
				item.appendChild(c7);
			}
		});
		
		departmentScore.setItemRenderer(new ListitemRenderer() {
			
			@Override
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
		});
		
		applyList1.setItemRenderer(new FilesRenderer1());
		applyList2.setItemRenderer(new FilesRenderer2());
		applyList3.setItemRenderer(new FilesRenderer3());

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
			
			arg0.appendChild(c1);
			arg0.appendChild(c2);
			arg0.appendChild(c3);
			arg0.appendChild(c4);
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
			
			arg0.appendChild(c1);
			arg0.appendChild(c2);
			arg0.appendChild(c3);
			arg0.appendChild(c4);
		}
	}
	
	/**
	 * <li>功能描述：文档信息的listbox(20120305)
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

	public void initWindow() {
		projectMemberList = jxkhProjectService.findProjectMember(project);
		personScore.setModel(new ListModelList(projectMemberList));
		List<Jxkh_ProjectDept> tempDeptList = jxkhProjectService.findProjectDept(project);
		departmentScore.setModel(new ListModelList(tempDeptList));
		
		fileList1 = project.getProjectFile();
		Object[] file = fileList1.toArray();
		for (int j = 0; j < file.length; j++) {
			Jxkh_ProjectFile f = (Jxkh_ProjectFile) file[j];
			if (f.getBelongType().equals("项目申请书")) {
				String[] arr = new String[4];
				arr[0] = f.getPath();
				arr[1] = f.getName();
				arr[2] = f.getSubmitDate();
				arr[3] = f.getBelongType();
				arrList1.add(arr);
			}
			if (f.getBelongType().equals("项目合同书")) {
				String[] arr = new String[4];
				arr[0] = f.getPath();
				arr[1] = f.getName();
				arr[2] = f.getSubmitDate();
				arr[3] = f.getBelongType();
				arrList2.add(arr);
			}
			if (f.getBelongType().equals("年度进度文档")) {
				String[] arr = new String[4];
				arr[0] = f.getPath();
				arr[1] = f.getName();
				arr[2] = f.getSubmitDate();
				arr[3] = f.getBelongType();
				arrList3.add(arr);
			}

		}
		
		applyList1.setModel(new ListModelList(arrList1));
		applyList2.setModel(new ListModelList(arrList2));
		applyList3.setModel(new ListModelList(arrList3));
		
		if (dept.equals("dept")) {
			addFund1.setVisible(true);
			delFund1.setVisible(true);
			addFund2.setVisible(true);
			delFund2.setVisible(true);
		}

		projectName.setValue(project.getName());
		WkTUser infoWriter = jxkhProjectService
				.findWktUserByMemberUserId(project.getInfoWriter());
		writer.setValue(infoWriter.getKuName());
		sumFund.setValue(project.getSumFund().toString());
		projecCode.setValue(project.getProjecCode());
		takeCompany.setValue(project.getTakeCompany());
		header.setValue(project.getHeader());
		standDept.setValue(project.getStandDept());

		if (project.getBeginDate() == null
				|| project.getBeginDate().length() == 0) {
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
		if (project.getIfHumanities() != null) {
			if (project.getIfHumanities() == Jxkh_Project.YES) {
				ifHum.setSelectedIndex(0);
			} else {
				ifHum.setSelectedIndex(1);
			}
		}
		if (project.getIfSoftScience() != null) {
			if (project.getIfSoftScience() == Jxkh_Project.YES) {
				ifSoft.setSelectedIndex(0);
			} else {
				ifSoft.setSelectedIndex(1);
			}
		}

		String memberName = "";
		String deptName = "";
		// 成员 部门
		projectMemberList = jxkhProjectService.findProjectMember(project);
		if (projectMemberList != null && projectMemberList.size() != 0) {
			for (int i = 0; i < projectMemberList.size(); i++) {
				Jxkh_ProjectMember mem = (Jxkh_ProjectMember) projectMemberList
						.get(i);
				memberName += mem.getName() + "、";
				WkTUser user = jxkhProjectService.findWktUserByMemberUserId(mem
						.getPersonId());
				memberList.add(user);
			}
			projectMember.setValue(memberName.substring(0,
					memberName.length() - 1));
		}
		projectDeptList = jxkhProjectService.findProjectDept(project);
		if (projectDeptList != null && projectDeptList.size() != 0) {
			for (int i = 0; i < projectDeptList.size(); i++) {
				Jxkh_ProjectDept dept = (Jxkh_ProjectDept) projectDeptList
						.get(i);
				deptName += dept.getName() + "、";
				WkTDept depts = jxkhProjectService.findWkTDeptByDept(dept
						.getDeptId());
				deptList.add(depts);
			}
			department.setValue(deptName.substring(0, deptName.length() - 1));
		}

	}

	public void initShow() {
		print.setHref("/print.action?n=zp&id=" + project.getId());
		funds.setDisabled(false);

		// 基金
		List<Jxkh_ProjectFund> fundList1 = jxkhProjectService.findFunds(
				project, Jxkh_ProjectFund.ZXF);
		fundsListbox1.setModel(new ListModelList(fundList1));
		List<Jxkh_ProjectFund> fundList2 = jxkhProjectService.findFunds(
				project, Jxkh_ProjectFund.ZCF);
		fundsListbox2.setModel(new ListModelList(fundList2));

		String year = ConvertUtil.convertDateString(new Date()).substring(0, 4);
		List outNum = jxkhProjectService.sumOut(project, year);
		List inNum = jxkhProjectService.sumIn(project, year);
		if (outNum.size() == 0 || outNum.get(0) == null) {
			outnum = "0.0";
		} else {
			outnum = outNum.get(0).toString();
		}
		if (inNum.size() == 0 || inNum.get(0) == null) {
			innum = "0.0";
		} else {
			innum = inNum.get(0).toString();
		}
		subnum = String.valueOf(Float.parseFloat(innum)
				- Float.parseFloat(outnum));
		yearFund.setValue(subnum);

		List<Jxkh_BusinessIndicator> typeList = new ArrayList();
		typeList.add(new Jxkh_BusinessIndicator());
		typeList.addAll(jxkhProjectService.findRank());
		rank.setModel(new ListModelList(typeList));

		String[] t = { "--请选择--", "在研", "延期", "结题" };
		List<String> progressList = new ArrayList();
		for (int i = 0; i < 4; i++) {
			progressList.add(t[i]);
		}
		progress.setModel(new ListModelList(progressList));

	}

	/**
	 * <li>功能描述：关闭当前窗口。 void
	 * 
	 * @author songyu
	 */
	public void onClick$close() {
		this.onClose();
	}

	public void onClick$closeScore() {
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
			if (project != null && type.equals(project.getRank())) {
				item.setSelected(true);
			}
		}
	}

	/** 项目进展列表渲染器 */
	public class TypeRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			String type = (String) data;
			item.setValue(type);
			item.setLabel(type);
			if (item.getIndex() == 0)
				item.setSelected(true);
			if (project != null && type.equals(project.getProgress())) {
				item.setSelected(true);
			}
		}
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
			Listcell c2 = new Listcell(fund.getMoney().toString());
//			Toolbarbutton tb = new Toolbarbutton();
//			tb.setLabel(fund.getMoney().toString());
//			tb.setParent(c2);
//			c2.setTooltiptext("点击金额编辑基金信息");
//			tb.setStyle("color:blue");
//			tb.addEventListener(Events.ON_CLICK, new EditListener());
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
			if(fund.getDeptNum() != null) {
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
		AddFundWindow w = (AddFundWindow) Executions.createComponents(
				"/admin/personal/businessdata/project/addFund.zul", null, null);
		try {
			w.setProject(project);
			w.setSort(Jxkh_ProjectFund.ZXF);
			w.doModal();
		} catch (SuspendNotAllowedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		initShow();
	}

	/** 添加自筹经费 */
	public void onClick$addFund2() {
		AddFundWindow w = (AddFundWindow) Executions.createComponents(
				"/admin/personal/businessdata/project/addFund.zul", null, null);
		try {
			w.setProject(project);
			w.setSort(Jxkh_ProjectFund.ZCF);
			w.doModal();
		} catch (SuspendNotAllowedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		initShow();
	}

	/** 监听经费金额列的单击事件 */
	public class EditListener implements EventListener {
		@Override
		public void onEvent(Event event) throws Exception {
			Listitem item = (Listitem) event.getTarget().getParent();
			Jxkh_ProjectFund fund = (Jxkh_ProjectFund) item.getValue();
			EditFundWindow w = (EditFundWindow) Executions.createComponents(
					"/admin/personal/businessdata/project/editFund.zul", null,
					null);
			try {
				w.setFund(fund);
				w.initListbox();

				w.doModal();
			} catch (SuspendNotAllowedException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			initShow();
		}
	}

	/**
	 * <li>功能描述：删除专项经费 void
	 * 
	 * @author songyu
	 */
	public void onClick$delFund1() throws InterruptedException {
		if (fundsListbox1.getSelectedItems() == null
				|| fundsListbox1.getSelectedItems().size() <= 0) {
			try {
				Messagebox.show("请选择要删除的经费信息！", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		Messagebox.show("确定删除吗?", "确定", Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						if (evt.getName().equals("onOK")) {
							Iterator<Listitem> items = fundsListbox1
									.getSelectedItems().iterator();
							List<Jxkh_ProjectFund> fundList = new ArrayList();
							Jxkh_ProjectFund fund = new Jxkh_ProjectFund();
							while (items.hasNext()) {
								fund = (Jxkh_ProjectFund) items.next()
										.getValue();
								fundList.add(fund);
							}
							jxkhProjectService.deleteAll(fundList);
							project.setState(Jxkh_Project.NOT_AUDIT);
							project.setTempState("0000000");// 为临时审核状态赋初值
							project.setDep1Reason("");
							project.setDep2Reason("");
							project.setDep3Reason("");
							project.setDep4Reason("");
							project.setDep5Reason("");
							project.setDep6Reason("");
							project.setDep7Reason("");
							project.setbAdvice("");
							jxkhProjectService.saveOrUpdate(project);
							try {
								Messagebox.show("经费信息删除成功！", "提示",
										Messagebox.OK, Messagebox.INFORMATION);
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
	public void onClick$delFund2() throws InterruptedException {
		if (fundsListbox2.getSelectedItems() == null
				|| fundsListbox2.getSelectedItems().size() <= 0) {
			try {
				Messagebox.show("请选择要删除的经费信息！", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		Messagebox.show("确定删除吗?", "确定", Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						if (evt.getName().equals("onOK")) {
							Iterator<Listitem> items = fundsListbox2
									.getSelectedItems().iterator();
							List<Jxkh_ProjectFund> fundList = new ArrayList();
							Jxkh_ProjectFund fund = new Jxkh_ProjectFund();
							while (items.hasNext()) {
								fund = (Jxkh_ProjectFund) items.next()
										.getValue();
								fundList.add(fund);
							}
							jxkhProjectService.deleteAll(fundList);
							project.setState(Jxkh_Project.NOT_AUDIT);
							project.setTempState("0000000");// 为临时审核状态赋初值
							project.setDep1Reason("");
							project.setDep2Reason("");
							project.setDep3Reason("");
							project.setDep4Reason("");
							project.setDep5Reason("");
							project.setDep6Reason("");
							project.setDep7Reason("");
							project.setbAdvice("");
							jxkhProjectService.saveOrUpdate(project);
							try {
								Messagebox.show("经费信息删除成功！", "提示",
										Messagebox.OK, Messagebox.INFORMATION);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				});
		initShow();
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
}
