package org.iti.jxkh.audit.project;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.iti.jxkh.business.project.AddFundWindow;
import org.iti.jxkh.business.project.EditFundWindow;
import org.iti.jxkh.business.project.AddHPWindow.EditListener;
import org.iti.jxkh.entity.Jxkh_Project;
import org.iti.jxkh.entity.Jxkh_ProjectDept;
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
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.DepartmentService;

public class HPWindow extends Window implements AfterCompose {

	/**
	 * @author SongYu
	 */
	private static final long serialVersionUID = -3664613977051724505L;
	WkTUser user;
	private JxkhProjectService jxkhProjectService;
	private Tab funds;
	private Textbox projectName, projectMember, department, coUnit, sumFund,
			projecCode, header, entruster;
	private Label yearFund, writer, entrLabel;
	private Datebox begin, end;
	private Listbox fundsListbox1;
	private Radiogroup ifCoo, ifEntruster;
	private Row coUnitRow;
	private Button print;
	private Jxkh_Project project;
	private String subnum, outnum, innum;
	private Label recordlabel;
	private Hbox recordhbox;
	private Textbox record;
	private String dept = "";
	private Toolbarbutton addFund1, delFund1;
	private List<WkTUser> memberList = new ArrayList<WkTUser>();
	private List<WkTDept> deptList = new ArrayList<WkTDept>();
	private List<Jxkh_ProjectMember> projectMemberList = new ArrayList<Jxkh_ProjectMember>();
	private List<Jxkh_ProjectDept> projectDeptList = new ArrayList<Jxkh_ProjectDept>();
	private Radio firstSignTrue, firstSignFalse;
	
	private DepartmentService departmentService;
	
	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");// 获取当前登录用户
		writer.setValue(user.getKuName());
		fundsListbox1.setItemRenderer(new FundsRenderer());

	}

	public void initWindow() {

		if (dept.equals("dept")) {
			addFund1.setVisible(true);
			delFund1.setVisible(true);
		}
//		if (project.getState() == Jxkh_Patent.SAVE_RECORD) {
//			record.setVisible(true);
//			recordlabel.setVisible(true);
//			recordhbox.setVisible(true);
//			record.setValue(project.getRecordCode());
//		}

		projectName.setValue(project.getName());
		WkTUser infoWriter = jxkhProjectService
				.findWktUserByMemberUserId(project.getInfoWriter());
		writer.setValue(infoWriter.getKuName());
		sumFund.setValue(project.getSumFund().toString());
		projecCode.setValue(project.getProjecCode());
		header.setValue(project.getHeader());
		entruster.setValue(project.getEntruster());
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
		funds.setDisabled(false);
		print.setHref("/print.action?n=hp&id=" + project.getId());
		// 基金
		List<Jxkh_ProjectFund> fundList1 = jxkhProjectService.findFunds(
				project, Jxkh_ProjectFund.ZXF);
		fundsListbox1.setModel(new ListModelList(fundList1));

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

	}

	/**
	 * <li>功能描述：关闭当前窗口。 void
	 * 
	 * @author songyu
	 */
	public void onClick$close() {
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
//			c2.setTooltiptext("点击金额编辑基金信息");
//			c2.setStyle("color:blue");
//			c2.addEventListener(Events.ON_CLICK, new EditListener());
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

	/** 监听经费金额列的单击事件 */
//	public class EditListener implements EventListener {
//		@Override
//		public void onEvent(Event event) throws Exception {
//			Listitem item = (Listitem) event.getTarget().getParent();
//			Jxkh_ProjectFund fund = (Jxkh_ProjectFund) item.getValue();
//			EditFundWindow w = (EditFundWindow) Executions.createComponents(
//					"/admin/personal/businessdata/project/editFund.zul", null,
//					null);
//			try {
//				w.setFund(fund);
//				w.initListbox();
//
//				w.doModal();
//			} catch (SuspendNotAllowedException e) {
//				e.printStackTrace();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			initShow();
//		}
//	}

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

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public Jxkh_Project getProject() {
		return project;
	}

	public void setProject(Jxkh_Project project) {
		this.project = project;
	}
}
