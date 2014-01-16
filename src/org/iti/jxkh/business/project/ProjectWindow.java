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
import org.iti.jxkh.audit.project.CPWindow;
import org.iti.jxkh.audit.project.HPWindow;
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
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.iti.common.util.ConvertUtil;
import com.uniwin.framework.entity.WkTUser;

public class ProjectWindow extends Window implements AfterCompose {

	/**
	 * @author SongYu
	 */
	private static final long serialVersionUID = -4018673049983784473L;
	private Listbox zxListbox, hxListbox, zsListbox;
	WkTUser user;
	private JxkhProjectService jxkhProjectService;
	private Textbox name, name2, name3;
	private YearListbox year, year2, year3;
	private String nameSearch, yearSearch, nameSearch2, yearSearch2,
			nameSearch3, yearSearch3;
	private Listbox auditState, auditState2, auditState3;
	private Integer auditStateSearch, auditStateSearch2, auditStateSearch3;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");// 获取当前登录用户
		zxListbox.setItemRenderer(new ZProjectRenderer());
		hxListbox.setItemRenderer(new HProjectRenderer());
		zsListbox.setItemRenderer(new CProjectRenderer());
		year.initYearListbox("");
		year2.initYearListbox("");
		year3.initYearListbox("");
		initShow();
		String[] s = { "-请选择-", "填写中","待审核","部门审核中", "部门通过", "部门不通过","业务办暂缓通过", "业务办通过", "业务办不通过",
		"归档" };
		List<String> lwjbList = new ArrayList<String>();
		for (int i = 0; i < 8; i++) {
			lwjbList.add(s[i]);
		}
		auditState.setModel(new ListModelList(lwjbList));
		auditState.setSelectedIndex(0);
		auditState2.setModel(new ListModelList(lwjbList));
		auditState2.setSelectedIndex(0);
		auditState3.setModel(new ListModelList(lwjbList));
		auditState3.setSelectedIndex(0);
	}

	public void initShow() {
		List<Jxkh_Project> zProjectList = jxkhProjectService
				.findZPBymemberId(user.getKuLid());
		zxListbox.setModel(new ListModelList(zProjectList));
		List<Jxkh_Project> hProjectList = jxkhProjectService
				.findHPBymemberId(user.getKuLid());
		hxListbox.setModel(new ListModelList(hProjectList));
		List<Jxkh_Project> cProjectList = jxkhProjectService
				.findCPBymemberId(user.getKuLid());
		zsListbox.setModel(new ListModelList(cProjectList));
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
			Listcell c2 = new Listcell(project.getName());
			c2.setStyle("color:blue");

			if (user.getKuLid().equals(project.getInfoWriter())) {
				if (project.getState() == Jxkh_Project.NOT_AUDIT
						|| project.getState() == Jxkh_Project.DEPT_NOT_PASS
						|| project.getState() == Jxkh_Project.BUSINESS_NOT_PASS) {

					c2.setTooltiptext("点击编辑纵向项目信息");
					c2.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event arg0) throws Exception {

							AddZPWindow w = (AddZPWindow) Executions
									.createComponents(
											"/admin/personal/businessdata/project/addZP.zul",
											null, null);
							try {
								w.setTitle("编辑项目信息");
								w.setProject(project);
//								w.initShow();
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

							ZPWindow w = (ZPWindow) Executions
									.createComponents(
											"/admin/personal/businessdata/project/showZP.zul",
											null, null);
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

						ZPWindow w = (ZPWindow) Executions
								.createComponents(
										"/admin/personal/businessdata/project/showZP.zul",
										null, null);
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
					DownloadWindow win = (DownloadWindow) Executions
							.createComponents(
									"/admin/personal/businessdata/meeting/download.zul",
									null, null);
					win.setFiles(project.getProjectFile());
					win.setFlag("zp");
					win.initWindow();
					win.doModal();
				}
			});
			c5.appendChild(download);
			Listcell c6 = new Listcell();
			if(project.getScore() != null) {
				BigDecimal bd = new BigDecimal(project.getScore());
				bd.setScale(1, RoundingMode.HALF_UP);
				c6.setLabel(Float.valueOf(bd.floatValue()).toString());
			}else {
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
					AdviceWindow pa = (AdviceWindow) Executions
							.createComponents(
									"/admin/personal/businessdata/project/advice.zul",
									null, null);
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

	public class HProjectRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			final Jxkh_Project project = (Jxkh_Project) data;
			item.setValue(project);
			Listcell c0 = new Listcell();
			Listcell c1 = new Listcell(item.getIndex() + 1 + "");
			Listcell c2 = new Listcell(project.getName());
			c2.setStyle("color:blue");

			if (user.getKuLid().equals(project.getInfoWriter())) {
				if (project.getState() == Jxkh_Project.NOT_AUDIT
						|| project.getState() == Jxkh_Project.DEPT_NOT_PASS
						|| project.getState() == Jxkh_Project.BUSINESS_NOT_PASS) {

					c2.setTooltiptext("点击编辑横向项目信息");
					c2.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event arg0) throws Exception {

							AddHPWindow w = (AddHPWindow) Executions
									.createComponents(
											"/admin/personal/businessdata/project/addHP.zul",
											null, null);
							try {
								w.setTitle("编辑横向项目信息");
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
				} else {
					c2.setTooltiptext("点击查看横向项目信息");
					c2.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event arg0) throws Exception {

							HPWindow w = (HPWindow) Executions
									.createComponents(
											"/admin/personal/businessdata/project/showHP.zul",
											null, null);
							try {
								w.setTitle("查看横向项目信息");
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

				c2.setTooltiptext("点击查看横向项目信息");
				c2.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {

						HPWindow w = (HPWindow) Executions
								.createComponents(
										"/admin/personal/businessdata/project/showHP.zul",
										null, null);
						try {
							w.setTitle("查看横向项目信息");
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
			Listcell c5 = new Listcell(project.getBeginDate());
			Listcell c6 = new Listcell();
			Image download = new Image("/css/default/images/button/down.gif");
			download.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					DownloadWindow win = (DownloadWindow) Executions
							.createComponents(
									"/admin/personal/businessdata/meeting/download.zul",
									null, null);
					win.setFiles(project.getProjectFile());
					win.setFlag("hp");
					win.initWindow();
					win.doModal();
				}
			});
			c6.appendChild(download);
			Listcell c7 = new Listcell();
			if(project.getScore() != null) {
				BigDecimal bd = new BigDecimal(project.getScore());
				bd.setScale(1, RoundingMode.HALF_UP);
				c7.setLabel(Float.valueOf(bd.floatValue()).toString());
			}else {
				c7.setLabel("0");
			}
			Listcell c8 = new Listcell("");
			List<Jxkh_ProjectMember> mlist = jxkhProjectService.findProjectMember(project);
			for (int j = 0; j < mlist.size(); j++) {
				Jxkh_ProjectMember m = mlist.get(j);
				if (user.getKuName().equals(m.getName())) {
					if (m.getScore() != null && !m.getScore().equals("")) {
						BigDecimal bd = new BigDecimal(m.getScore());
						bd.setScale(1, RoundingMode.HALF_UP);
						c8.setLabel(Float.valueOf(bd.floatValue()).toString());						
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
			Listcell c9 = new Listcell(strC8);
			c9.setStyle("color:red");
			c9.setTooltiptext("点击查看审核结果");
			c9.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					AdviceWindow pa = (AdviceWindow) Executions
							.createComponents(
									"/admin/personal/businessdata/project/advice.zul",
									null, null);
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
			item.appendChild(c5);
			item.appendChild(c6);
			item.appendChild(c7);
			item.appendChild(c8);
			item.appendChild(c9);
		}
	}

	public class CProjectRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			final Jxkh_Project project = (Jxkh_Project) data;
			item.setValue(project);
			Listcell c0 = new Listcell();
			Listcell c1 = new Listcell(item.getIndex() + 1 + "");
			Listcell c2 = new Listcell(project.getName());
			c2.setStyle("color:blue");

			if (user.getKuLid().equals(project.getInfoWriter())) {
				if (project.getState() == Jxkh_Project.NOT_AUDIT
						|| project.getState() == Jxkh_Project.DEPT_NOT_PASS
						|| project.getState() == Jxkh_Project.BUSINESS_NOT_PASS) {

					c2.setTooltiptext("点击编辑自设项目信息");
					c2.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event arg0) throws Exception {

							AddCPWindow w = (AddCPWindow) Executions
									.createComponents(
											"/admin/personal/businessdata/project/addCP.zul",
											null, null);
							try {
								w.setTitle("编辑自设项目信息");
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

				} else {

					c2.setTooltiptext("点击查看自设项目信息");
					c2.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event arg0) throws Exception {

							CPWindow w = (CPWindow) Executions
									.createComponents(
											"/admin/personal/businessdata/project/showCP.zul",
											null, null);
							try {
								w.setTitle("查看自设项目信息");
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

				c2.setTooltiptext("点击查看自设项目信息");
				c2.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {

						CPWindow w = (CPWindow) Executions
								.createComponents(
										"/admin/personal/businessdata/project/showCP.zul",
										null, null);
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
			Listcell c4 = new Listcell(project.getHeader());
			Listcell c5 = new Listcell(project.getBeginDate());
			Listcell c6 = new Listcell();
			Image download = new Image("/css/default/images/button/down.gif");
			download.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					DownloadWindow win = (DownloadWindow) Executions
							.createComponents(
									"/admin/personal/businessdata/meeting/download.zul",
									null, null);
					win.setFiles(project.getProjectFile());
					win.setFlag("cp");
					win.initWindow();
					win.doModal();
				}
			});
			c6.appendChild(download);
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
					AdviceWindow pa = (AdviceWindow) Executions
							.createComponents(
									"/admin/personal/businessdata/project/advice.zul",
									null, null);
					pa.setMeeting(project);
					pa.initWindow();
					pa.doModal();
				}
			});
			item.appendChild(c0);
			item.appendChild(c1);
			item.appendChild(c2);
			item.appendChild(c4);
			item.appendChild(c5);
			item.appendChild(c6);
			item.appendChild(c8);
		}
	}

	/**
	 * <li>功能描述：添加纵向项目。 void
	 * 
	 * @author YuSong
	 */
	public void onClick$add1() {
		AddZPWindow w = (AddZPWindow) Executions.createComponents(
				"/admin/personal/businessdata/project/addZP.zul", null, null);
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
		if (zxListbox.getSelectedItems() == null
				|| zxListbox.getSelectedItems().size() <= 0) {
			try {
				Messagebox.show("请选择要删除的纵向项目！", "提示", Messagebox.OK,
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
							Iterator<?> it = zxListbox
									.getSelectedItems().iterator();
							while (it.hasNext()) {
								Listitem item = (Listitem) it.next();
								Jxkh_Project project = (Jxkh_Project) item.getValue();
								if (!user.getKuLid().equals(
										project.getInfoWriter())) {
									try {
										Messagebox.show("不是本人提交的不能删除！", "提示",
												Messagebox.OK,
												Messagebox.INFORMATION);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									return;
								}
								if (project.getState() == Jxkh_Project.DEPT_PASS
										|| project.getState() == Jxkh_Project.First_Dept_Pass
										|| project.getState() == Jxkh_Project.BUSINESS_PASS || project.getState().equals(JXKH_MEETING.BUSINESS_TEMP_PASS)
										|| project.getState() == Jxkh_Project.SAVE_RECORD) {
									try {
										Messagebox.show("部门或业务办审核通过的不能删除！",
												"提示", Messagebox.OK,
												Messagebox.INFORMATION);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									return;
								}
								jxkhProjectService.delete(project);

							}
							try {
								Messagebox.show("纵向项目删除成功！", "提示",
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
	 * <li>功能描述：添加横向项目。 void
	 * 
	 * @author YuSong
	 */
	public void onClick$add2() {
		AddHPWindow w = (AddHPWindow) Executions.createComponents(
				"/admin/personal/businessdata/project/addHP.zul", null, null);
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
	 * <li>功能描述：删除横向项目。 void
	 * 
	 * @author YuSong
	 */
	public void onClick$del2() throws InterruptedException {
		if (hxListbox.getSelectedItems() == null
				|| hxListbox.getSelectedItems().size() <= 0) {
			try {
				Messagebox.show("请选择要删除的横向项目！", "提示", Messagebox.OK,
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
							Iterator<?> it = hxListbox
									.getSelectedItems().iterator();
							while (it.hasNext()) {
								Listitem item = (Listitem) it.next();
								Jxkh_Project project = (Jxkh_Project) item.getValue();

								if (!user.getKuLid().equals(
										project.getInfoWriter())) {
									try {
										Messagebox.show("不是本人提交的不能删除！", "提示",
												Messagebox.OK,
												Messagebox.INFORMATION);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									return;
								}
								if (project.getState() == Jxkh_Project.DEPT_PASS
										|| project.getState() == Jxkh_Project.First_Dept_Pass
										|| project.getState() == Jxkh_Project.BUSINESS_PASS || project.getState().equals(JXKH_MEETING.BUSINESS_TEMP_PASS)
										|| project.getState() == Jxkh_Project.SAVE_RECORD) {
									try {
										Messagebox.show("部门或业务办审核通过的不能删除！",
												"提示", Messagebox.OK,
												Messagebox.INFORMATION);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									return;
								}
								jxkhProjectService.delete(project);

							}
							try {
								Messagebox.show("横向项目删除成功！", "提示",
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
	 * <li>功能描述：添加自设项目。 void
	 * 
	 * @author YuSong
	 */
	public void onClick$add3() {
		AddCPWindow w = (AddCPWindow) Executions.createComponents(
				"/admin/personal/businessdata/project/addCP.zul", null, null);
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
	 * <li>功能描述：删除自设项目。 void
	 * 
	 * @author YuSong
	 */
	public void onClick$del3() throws InterruptedException {
		if (zsListbox.getSelectedItems() == null
				|| zsListbox.getSelectedItems().size() <= 0) {
			try {
				Messagebox.show("请选择要删除的自设项目！", "提示", Messagebox.OK,
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
							Iterator<?> it = zsListbox
									.getSelectedItems().iterator();
							while (it.hasNext()) {
								Listitem item = (Listitem) it.next();
								Jxkh_Project project = (Jxkh_Project) item.getValue();
								if (!user.getKuLid().equals(
										project.getInfoWriter())) {
									try {
										Messagebox.show("不是本人提交的不能删除！", "提示",
												Messagebox.OK,
												Messagebox.INFORMATION);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									return;
								}
								if (project.getState() == Jxkh_Project.DEPT_PASS
										|| project.getState() == Jxkh_Project.First_Dept_Pass
										|| project.getState() == Jxkh_Project.BUSINESS_PASS || project.getState().equals(JXKH_MEETING.BUSINESS_TEMP_PASS)
										|| project.getState() == Jxkh_Project.SAVE_RECORD) {
									try {
										Messagebox.show("部门或业务办审核通过的不能删除！",
												"提示", Messagebox.OK,
												Messagebox.INFORMATION);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									return;
								}

								jxkhProjectService.delete(project);

							}
							try {
								Messagebox.show("自设项目删除成功！", "提示",
										Messagebox.OK, Messagebox.INFORMATION);
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
		}else if(auditState.getSelectedItem().getValue().equals("业务办暂缓通过")) {
			auditStateSearch = 7;
		}else if(auditState.getSelectedItem().getValue().equals("填写中")) {
			auditStateSearch = 8;
		}
		if (year.getSelectedIndex() != 0 && year.getSelectedItem() != null)
			yearSearch = year.getSelectedItem().getValue() + "";

		List<Jxkh_Project> meetingList = jxkhProjectService.findZPByCondition(
				nameSearch, auditStateSearch, yearSearch, user.getKuLid());
		zxListbox.setModel(new ListModelList(meetingList));
	}

	public void onClick$reset() {
		name.setValue("");
		auditState.getItemAtIndex(0).setSelected(true);
		year.getItemAtIndex(0).setSelected(true);
	}

	public void onClick$query2() {
		nameSearch = null;
		auditStateSearch = null;
		yearSearch = null;
		nameSearch2 = name2.getValue();
		if (auditState2.getSelectedItem().getValue().equals("-请选择-")) {
			auditStateSearch2 = null;
		} else if (auditState2.getSelectedItem().getValue().equals("待审核")) {
			auditStateSearch2 = 0;
		} else if (auditState2.getSelectedItem().getValue().equals("部门通过")) {
			auditStateSearch2 = 1;
		} else if (auditState2.getSelectedItem().getValue().equals("部门审核中")) {
			auditStateSearch2 = 2;
		} else if (auditState2.getSelectedItem().getValue().equals("部门不通过")) {
			auditStateSearch2 = 3;
		} else if (auditState2.getSelectedItem().getValue().equals("业务办通过")) {
			auditStateSearch2 = 4;
		} else if (auditState2.getSelectedItem().getValue().equals("业务办不通过")) {
			auditStateSearch2 = 5;
		} else if (auditState2.getSelectedItem().getValue().equals("归档")) {
			auditStateSearch2 = 6;
		}else if(auditState2.getSelectedItem().getValue().equals("业务办暂缓通过")) {
			auditStateSearch2 = 7;
		}else if(auditState2.getSelectedItem().getValue().equals("填写中")) {
			auditStateSearch2 = 8;
		}
		if (year2.getSelectedIndex() != 0 && year2.getSelectedItem() != null)
			yearSearch2 = year2.getSelectedItem().getValue() + "";

		List<Jxkh_Project> meetingList = jxkhProjectService.findHPByCondition(
				nameSearch2, auditStateSearch2, yearSearch2, user.getKuLid());
		hxListbox.setModel(new ListModelList(meetingList));
	}

	public void onClick$reset2() {
		name2.setValue("");
		auditState2.getItemAtIndex(0).setSelected(true);
		year2.getItemAtIndex(0).setSelected(true);
	}

	public void onClick$query3() {
		nameSearch = null;
		auditStateSearch = null;
		yearSearch = null;
		nameSearch3 = name3.getValue();
		if (auditState3.getSelectedItem().getValue().equals("-请选择-")) {
			auditStateSearch3 = null;
		} else if (auditState3.getSelectedItem().getValue().equals("待审核")) {
			auditStateSearch3 = 0;
		} else if (auditState3.getSelectedItem().getValue().equals("部门通过")) {
			auditStateSearch3 = 1;
		} else if (auditState3.getSelectedItem().getValue().equals("部门审核中")) {
			auditStateSearch3 = 2;
		} else if (auditState3.getSelectedItem().getValue().equals("部门不通过")) {
			auditStateSearch3 = 3;
		} else if (auditState3.getSelectedItem().getValue().equals("业务办通过")) {
			auditStateSearch3 = 4;
		} else if (auditState3.getSelectedItem().getValue().equals("业务办不通过")) {
			auditStateSearch3 = 5;
		} else if (auditState3.getSelectedItem().getValue().equals("归档")) {
			auditStateSearch3 = 6;
		}else if(auditState3.getSelectedItem().getValue().equals("业务办暂缓通过")) {
			auditStateSearch3 = 7;
		}else if(auditState3.getSelectedItem().getValue().equals("填写中")) {
			auditStateSearch3 = 8;
		}
		if (year3.getSelectedIndex() != 0 && year3.getSelectedItem() != null)
			yearSearch3 = year3.getSelectedItem().getValue() + "";

		List<Jxkh_Project> meetingList = jxkhProjectService.findZPByCondition(
				nameSearch3, auditStateSearch3, yearSearch3, user.getKuLid());
		zsListbox.setModel(new ListModelList(meetingList));
	}

	public void onClick$reset3() {
		name3.setValue("");
		auditState3.getItemAtIndex(0).setSelected(true);
		year3.getItemAtIndex(0).setSelected(true);
	}

	// 导出
	public void onClick$export1() throws WriteException, IOException {
		if (zxListbox.getSelectedCount() == 0) {
			try {
				Messagebox.show("提示请选择要导出的数据", "提示", Messagebox.OK,
						Messagebox.EXCLAMATION);
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
			String fileName = ConvertUtil.convertDateString(now)
					+ "zongxiangxiangmu" + ".xls";
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
				List<Jxkh_ProjectMember> mlist = jxkhProjectService
						.findProjectMember(award);
				if (mlist.size() != 0) {
					for (int m = 0; m < mlist.size(); m++) {
						Jxkh_ProjectMember mem = (Jxkh_ProjectMember) mlist
								.get(m);
						member += mem.getName() + "、";
					}
					c[j][2] = member.substring(0, member.length() - 1);
				}
				List<Jxkh_ProjectDept> dlist = jxkhProjectService
						.findProjectDept(award);
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

	// 导出
	public void onClick$export2() throws WriteException, IOException {
		if (hxListbox.getSelectedCount() == 0) {
			try {
				Messagebox.show("提示请选择要导出的数据", "提示", Messagebox.OK,
						Messagebox.EXCLAMATION);
			} catch (InterruptedException e) {
				// ignore
			}
			return;
		} else {
			int i = 0;
			ArrayList<Jxkh_Project> expertlist = new ArrayList<Jxkh_Project>();
			@SuppressWarnings("unchecked")
			Set<Listitem> sel = hxListbox.getSelectedItems();
			Iterator<Listitem> it = sel.iterator();
			while (it.hasNext()) {
				Listitem item = (Listitem) it.next();
				Jxkh_Project p = (Jxkh_Project) item.getValue();
				expertlist.add(i, p);
				i++;
			}
			Date now = new Date();
			String fileName = ConvertUtil.convertDateString(now)
					+ "hengxiangxiangmu" + ".xls";
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
				List<Jxkh_ProjectMember> mlist = jxkhProjectService
						.findProjectMember(award);
				if (mlist.size() != 0) {
					for (int m = 0; m < mlist.size(); m++) {
						Jxkh_ProjectMember mem = (Jxkh_ProjectMember) mlist
								.get(m);
						member += mem.getName() + "、";
					}
					c[j][2] = member.substring(0, member.length() - 1);
				}
				List<Jxkh_ProjectDept> dlist = jxkhProjectService
						.findProjectDept(award);
				String dept = "";
				if (dlist.size() != 0) {
					for (int m = 0; m < dlist.size(); m++) {
						Jxkh_ProjectDept de = (Jxkh_ProjectDept) dlist.get(m);
						dept += de.getName() + "、";
					}
					c[j][3] = dept.substring(0, dept.length() - 1);
				}
				c[j][4] = "横向项目";
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

	// 导出
	public void onClick$export3() throws WriteException, IOException {
		if (zsListbox.getSelectedCount() == 0) {
			try {
				Messagebox.show("提示请选择要导出的数据", "提示", Messagebox.OK,
						Messagebox.EXCLAMATION);
			} catch (InterruptedException e) {
				// ignore
			}
			return;
		} else {
			int i = 0;
			ArrayList<Jxkh_Project> expertlist = new ArrayList<Jxkh_Project>();
			@SuppressWarnings("unchecked")
			Set<Listitem> sel = zsListbox.getSelectedItems();
			Iterator<Listitem> it = sel.iterator();
			while (it.hasNext()) {
				Listitem item = (Listitem) it.next();
				Jxkh_Project p = (Jxkh_Project) item.getValue();
				expertlist.add(i, p);
				i++;
			}
			Date now = new Date();
			String fileName = ConvertUtil.convertDateString(now)
					+ "zishexiangmu" + ".xls";
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
				List<Jxkh_ProjectMember> mlist = jxkhProjectService
						.findProjectMember(award);
				if (mlist.size() != 0) {
					for (int m = 0; m < mlist.size(); m++) {
						Jxkh_ProjectMember mem = (Jxkh_ProjectMember) mlist
								.get(m);
						member += mem.getName() + "、";
					}
					c[j][2] = member.substring(0, member.length() - 1);
				}
				List<Jxkh_ProjectDept> dlist = jxkhProjectService
						.findProjectDept(award);
				String dept = "";
				if (dlist.size() != 0) {
					for (int m = 0; m < dlist.size(); m++) {
						Jxkh_ProjectDept de = (Jxkh_ProjectDept) dlist.get(m);
						dept += de.getName() + "、";
					}
					c[j][3] = dept.substring(0, dept.length() - 1);
				}
				c[j][4] = "院内自设项目";
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
