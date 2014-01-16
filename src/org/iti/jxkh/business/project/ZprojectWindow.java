package org.iti.jxkh.business.project;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import jxl.write.WriteException;

import org.iti.gh.common.util.ExportExcel;
import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxkh.audit.project.ZPWindow;
import org.iti.jxkh.business.meeting.DownloadWindow;
import org.iti.jxkh.entity.JXKH_MEETING;
import org.iti.jxkh.entity.Jxkh_Project;
import org.iti.jxkh.entity.Jxkh_ProjectDept;
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
import org.zkoss.zul.Image;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.iti.common.util.ConvertUtil;
import com.uniwin.framework.entity.WkTUser;

public class ZprojectWindow extends Window implements AfterCompose {
	/**
	 * @author SongYu
	 */
	private static final long serialVersionUID = -4018673049983784473L;
	private Listbox zxListbox;
	private Paging zxPaging;
	private WkTUser user;
	private JxkhProjectService jxkhProjectService;
	private Textbox name;
	private YearListbox year;
	private String nameSearch, yearSearch;
	private Listbox auditState;
	private Integer auditStateSearch;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");// 获取当前登录用户
		zxListbox.setItemRenderer(new ZProjectRenderer());
		year.initYearListbox("");
		initShow();
		String[] s = { "-请选择-", "填写中", "待审核", "部门审核中", "部门通过", "部门不通过", "业务办暂缓通过", "业务办通过", "业务办不通过", "归档" };
		List<String> lwjbList = new ArrayList<String>();
		for (int i = 0; i < 8; i++) {
			lwjbList.add(s[i]);
		}
		auditState.setModel(new ListModelList(lwjbList));
		auditState.setSelectedIndex(0);
	}

	public void initShow() {
		zxPaging.addEventListener("onPaging", new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				List<Jxkh_Project> list = jxkhProjectService.findProjectBySortAndMemberIdAndPaging(zxPaging.getActivePage(), zxPaging.getPageSize(), Jxkh_Project.ZP, user.getKuLid());
				zxListbox.setModel(new ListModelList(list));
			}
		});
		List<Jxkh_Project> zProjectList = jxkhProjectService.findZPBymemberId(user.getKuLid());
		zxPaging.setTotalSize(zProjectList.size());
		List<Jxkh_Project> list = jxkhProjectService.findProjectBySortAndMemberIdAndPaging(zxPaging.getActivePage(), zxPaging.getPageSize(), Jxkh_Project.ZP, user.getKuLid());
		zxListbox.setModel(new ListModelList(list));
	}

	/**
	 * <li>功能描述：纵向项目列表渲染器 。 void
	 * 
	 * @author YuSong
	 */
	public class ZProjectRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			final Jxkh_Project project = (Jxkh_Project) data;
			item.setValue(project);
			Listcell c0 = new Listcell();
			Listcell c1 = new Listcell(item.getIndex() + 1 + "");
			Listcell c2 = new Listcell(project.getName().length() <= 11?
					project.getName():project.getName().substring(0, 11) + "...");
			c2.setTooltiptext("点击查看纵向项目信息");
			c2.setStyle("color:blue");

			if (user.getKuLid().equals(project.getInfoWriter())) {
				if (project.getState() == JXKH_MEETING.WRITING || project.getState() == Jxkh_Project.NOT_AUDIT || project.getState() == Jxkh_Project.DEPT_NOT_PASS || project.getState() == Jxkh_Project.BUSINESS_NOT_PASS) {

					c2.setTooltiptext("点击编辑纵向项目信息");
					c2.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event arg0) throws Exception {

							AddZPWindow w = (AddZPWindow) Executions.createComponents("/admin/personal/businessdata/project/addZP.zul", null, null);
							try {
								w.setTitle("编辑项目信息");
								w.setProject(project);
								// w.initShow();
								w.initWindow();
								w.doModal();
							} catch (SuspendNotAllowedException e) {
								e.printStackTrace();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							initShow();
						}
					});

				} else {

					c2.setTooltiptext("点击查看纵向项目信息");
					c2.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event arg0) throws Exception {

							ZPWindow w = (ZPWindow) Executions.createComponents("/admin/personal/businessdata/project/showZP.zul", null, null);
							try {
								w.setTitle("查看项目信息");
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
							initShow();
						}
					});
				}
			} else {

				c2.setTooltiptext("点击查看纵向项目信息");
				c2.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {

						ZPWindow w = (ZPWindow) Executions.createComponents("/admin/personal/businessdata/project/showZP.zul", null, null);
						try {
							w.setTitle("查看项目信息");
							w.setProject(project);
							w.initShow();
							w.initWindow();
							w.doModal();
						} catch (SuspendNotAllowedException e) {
							e.printStackTrace();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						initShow();
					}
				});
			}
			Listcell c3 = new Listcell(project.getRank().getKbName());
			Listcell c4 = new Listcell(project.getjxYear());
			Listcell c9 = new Listcell(project.getBeginDate());
			Listcell c5 = new Listcell();
			Image download = new Image("/css/default/images/button/down.gif");
			download.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					DownloadWindow win = (DownloadWindow) Executions.createComponents("/admin/personal/businessdata/meeting/download.zul", null, null);
					win.setFiles(project.getProjectFile());
					win.setFlag("zp");
					win.initWindow();
					win.doModal();
				}
			});
			c5.appendChild(download);
			Listcell c6 = new Listcell();
			if (project.getScore() != null) {
				BigDecimal bd = new BigDecimal(project.getScore());
				bd.setScale(1, RoundingMode.HALF_UP);
				c6.setLabel(Float.valueOf(bd.floatValue()).toString());
			} else {
				c6.setLabel("0");
			}
			Listcell c7 = new Listcell("");
			List<Jxkh_ProjectMember> mlist = jxkhProjectService.findProjectMember(project);
			for (int j = 0; j < mlist.size(); j++) {
				Jxkh_ProjectMember m = mlist.get(j);
				if (user.getKuName().equals(m.getName())) {
					if (m.getScore() != null && !m.getScore().equals("")) {
						BigDecimal b = new BigDecimal(m.getScore());
						b.setScale(1, RoundingMode.HALF_UP);
						c7.setLabel(Float.valueOf(b.floatValue()).toString());
					}
				}
			}
			String strC8;
			switch (project.getState()) {
			case Jxkh_Project.NOT_AUDIT:
				strC8 = "待审核";
				break;
			case Jxkh_Project.DEPT_PASS:
				strC8 = "部门通过";
				break;
			case Jxkh_Project.First_Dept_Pass:
				strC8 = "部门审核中";
				break;
			case Jxkh_Project.DEPT_NOT_PASS:
				strC8 = "部门不通过";
				break;
			case Jxkh_Project.BUSINESS_PASS:
				strC8 = "业务办通过";
				break;
			case Jxkh_Project.BUSINESS_NOT_PASS:
				strC8 = "业务办不通过";
				break;
			case 7:
				strC8 = "业务办暂缓通过";
				break;
			case 8:
				strC8 = "填写中";
				break;
			case 9:
				strC8 = "部门审核中";
				break;
			default:
				strC8 = "归档";
				break;
			}
			Listcell c8 = new Listcell(strC8);
			c8.setStyle("color:red");
			c8.setTooltiptext("点击查看审核结果");
			c8.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					AdviceWindow pa = (AdviceWindow) Executions.createComponents("/admin/personal/businessdata/project/advice.zul", null, null);
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
			item.appendChild(c9);
			item.appendChild(c5);
			item.appendChild(c6);
			item.appendChild(c7);
			item.appendChild(c8);
		}
	}

	/**
	 * <li>功能描述：添加纵向项目。 void
	 * 
	 * @author YuSong
	 */
	public void onClick$add1() {
		AddZPWindow w = (AddZPWindow) Executions.createComponents("/admin/personal/businessdata/project/addZP.zul", null, null);
		try {
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
	 * <li>功能描述：删除纵向项目。 void
	 * 
	 * @author YuSong
	 */
	public void onClick$del1() throws InterruptedException {
		if (zxListbox.getSelectedItems() == null || zxListbox.getSelectedItems().size() <= 0) {
			try {
				Messagebox.show("请选择要删除的纵向项目！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		Messagebox.show("确定删除吗?", "确定", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
			public void onEvent(Event evt) throws InterruptedException {
				if (evt.getName().equals("onOK")) {
					Iterator<?> it = zxListbox.getSelectedItems().iterator();
					while (it.hasNext()) {
						Listitem item = (Listitem) it.next();
						Jxkh_Project project = (Jxkh_Project) item.getValue();
						if (!user.getKuLid().equals(project.getInfoWriter())) {
							try {
								Messagebox.show("不是本人提交的不能删除！", "提示", Messagebox.OK, Messagebox.INFORMATION);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							return;
						}
						if (project.getState() == Jxkh_Project.DEPT_PASS || project.getState() == Jxkh_Project.First_Dept_Pass || project.getState() == Jxkh_Project.BUSINESS_PASS || project.getState().equals(JXKH_MEETING.BUSINESS_TEMP_PASS)
								|| project.getState() == Jxkh_Project.SAVE_RECORD) {
							try {
								Messagebox.show("部门或业务办审核通过的不能删除！", "提示", Messagebox.OK, Messagebox.INFORMATION);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							return;
						}
						jxkhProjectService.delete(project);

					}
					try {
						Messagebox.show("纵向项目删除成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		initShow();
	}

	public void onClick$query() {
		nameSearch = null;
		auditStateSearch = null;
		yearSearch = null;
		nameSearch = name.getValue();
		if (auditState.getSelectedItem().getValue().equals("-请选择-")) {
			auditStateSearch = null;
		} else if (auditState.getSelectedItem().getValue().equals("待审核")) {
			auditStateSearch = 0;
		} else if (auditState.getSelectedItem().getValue().equals("部门通过")) {
			auditStateSearch = 1;
		} else if (auditState.getSelectedItem().getValue().equals("部门审核中")) {
			auditStateSearch = 2;
		} else if (auditState.getSelectedItem().getValue().equals("部门不通过")) {
			auditStateSearch = 3;
		} else if (auditState.getSelectedItem().getValue().equals("业务办通过")) {
			auditStateSearch = 4;
		} else if (auditState.getSelectedItem().getValue().equals("业务办不通过")) {
			auditStateSearch = 5;
		} else if (auditState.getSelectedItem().getValue().equals("归档")) {
			auditStateSearch = 6;
		} else if (auditState.getSelectedItem().getValue().equals("业务办暂缓通过")) {
			auditStateSearch = 7;
		} else if (auditState.getSelectedItem().getValue().equals("填写中")) {
			auditStateSearch = 8;
		}
		if (year.getSelectedIndex() != 0 && year.getSelectedItem() != null)
			yearSearch = year.getSelectedItem().getValue() + "";

		zxPaging.addEventListener("onPaging", new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				List<Jxkh_Project> meetingList = jxkhProjectService.findZPByCondition(nameSearch, auditStateSearch.shortValue(), zxPaging.getActivePage(), zxPaging.getPageSize());
				zxListbox.setModel(new ListModelList(meetingList));
			}
		});
		
		List<Jxkh_Project> meetingList = jxkhProjectService.findZPByCondition(nameSearch, auditStateSearch, yearSearch, user.getKuLid());
		zxPaging.setTotalSize(meetingList.size());
		List<Jxkh_Project> list = jxkhProjectService.findZPByCondition(nameSearch, auditStateSearch != null?auditStateSearch.shortValue():null, zxPaging.getActivePage(), zxPaging.getPageSize());
		//List<Jxkh_Project> list = jxkhProjectService.findZPByCondition(nameSearch, auditStateSearch.shortValue(), zxPaging.getActivePage(), zxPaging.getPageSize());
		zxListbox.setModel(new ListModelList(list));
	}

	public void onClick$reset() {
		name.setValue("");
		auditState.getItemAtIndex(0).setSelected(true);
		year.getItemAtIndex(0).setSelected(true);
	}

	// 导出
	public void onClick$export1() throws WriteException, IOException {
		if (zxListbox.getSelectedCount() == 0) {
			try {
				Messagebox.show("提示请选择要导出的数据", "提示", Messagebox.OK, Messagebox.EXCLAMATION);
			} catch (InterruptedException e) {
			}
			return;
		} else {
			int i = 0;
			ArrayList<Jxkh_Project> expertlist = new ArrayList<Jxkh_Project>();
			@SuppressWarnings("unchecked")
			Set<Listitem> sel = zxListbox.getSelectedItems();
			Iterator<Listitem> it = sel.iterator();
			while (it.hasNext()) {
				Listitem item = (Listitem) it.next();
				Jxkh_Project p = (Jxkh_Project) item.getValue();
				expertlist.add(i, p);
				i++;
			}
			Date now = new Date();
			String fileName = ConvertUtil.convertDateString(now) + "zongxiangxiangmu" + ".xls";
			String Title = "奖励情况";
			List<String> titlelist = new ArrayList<String>();
			titlelist.add("序号");
			titlelist.add("项目名称");
			titlelist.add("项目成员");
			titlelist.add("院内部门");

			titlelist.add("项目级别");
			titlelist.add("项目负责人");
			titlelist.add("合同始期");
			titlelist.add("信息填写人");
			titlelist.add("审核状态");
			String c[][] = new String[expertlist.size()][titlelist.size()];
			// 从SQL中读数据
			for (int j = 0; j < expertlist.size(); j++) {
				Jxkh_Project award = (Jxkh_Project) expertlist.get(j);
				String member = "";
				c[j][0] = j + 1 + "";
				c[j][1] = award.getName();
				List<Jxkh_ProjectMember> mlist = jxkhProjectService.findProjectMember(award);
				if (mlist.size() != 0) {
					for (int m = 0; m < mlist.size(); m++) {
						Jxkh_ProjectMember mem = (Jxkh_ProjectMember) mlist.get(m);
						member += mem.getName() + "、";
					}
					c[j][2] = member.substring(0, member.length() - 1);
				}
				List<Jxkh_ProjectDept> dlist = jxkhProjectService.findProjectDept(award);
				String dept = "";
				if (dlist.size() != 0) {
					for (int m = 0; m < dlist.size(); m++) {
						Jxkh_ProjectDept de = (Jxkh_ProjectDept) dlist.get(m);
						dept += de.getName() + "、";
					}
					c[j][3] = dept.substring(0, dept.length() - 1);
				}
				c[j][4] = award.getRank().getKbName();
				c[j][5] = award.getHeader();
				c[j][6] = award.getBeginDate();
				c[j][7] = award.getInfoWriter();
				String strC8;
				switch (award.getState()) {
				case Jxkh_Project.NOT_AUDIT:
					strC8 = "待审核";
					break;
				case Jxkh_Project.DEPT_PASS:
					strC8 = "部门通过";
					break;
				case Jxkh_Project.First_Dept_Pass:
					strC8 = "部门审核中";
					break;
				case Jxkh_Project.DEPT_NOT_PASS:
					strC8 = "部门不通过";
					break;
				case Jxkh_Project.BUSINESS_PASS:
					strC8 = "业务办通过";
					break;
				case Jxkh_Project.BUSINESS_NOT_PASS:
					strC8 = "业务办不通过";
					break;
				case Jxkh_Project.SAVE_RECORD:
					strC8 = "归档";
					break;
				case 7:
					strC8 = "业务办暂缓通过";
					break;
				case 8:
					strC8 = "填写中";
					break;
				default:
					strC8 = "待审核";
					break;
				}
				c[j][8] = strC8;
			}
			ExportExcel ee = new ExportExcel();
			ee.exportExcel(fileName, Title, titlelist, expertlist.size(), c);
		}
	}

}
