package org.iti.jxkh.deptbusiness.award;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import jxl.write.WriteException;
import org.iti.gh.common.util.ExportExcel;
import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxkh.business.award.AdviceWin;
import org.iti.jxkh.entity.JXKH_MEETING;
import org.iti.jxkh.entity.Jxkh_Award;
import org.iti.jxkh.entity.Jxkh_AwardDept;
import org.iti.jxkh.entity.Jxkh_AwardMember;
import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.service.JXKHMeetingService;
import org.iti.jxkh.service.JxkhAwardService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Groupbox;
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

public class PersonalWin extends Window implements AfterCompose {

	/**
	 * @author ZhangyuGuang
	 */
	private static final long serialVersionUID = 424055975325539799L;
	private Listbox awardListbox;
	private Groupbox cxtj;
	private boolean isQuery = false;
	private Textbox awardName;
	private String nameSearch, yearSearch;
	private YearListbox year;
	private Long indicatorId;
	private Short auditStateSearch;
	private Listbox auditState, rank;
	private JxkhAwardService jxkhAwardService;
	private List<Jxkh_Award> awardList = new ArrayList<Jxkh_Award>();
	private Paging awardPaging;
	private WkTUser user;
	private JXKHMeetingService jxkhMeetingService;
	public static final Integer qikan = 31;

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		year.initYearListbox("");
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");// 获取当前登录用户
		awardListbox.setItemRenderer(new AwardRenderer());
		auditState.setItemRenderer(new auditStateRenderer());
		initWindow();
		rank.setItemRenderer(new qkTypeRenderer());
		List<Jxkh_BusinessIndicator> holdList = new ArrayList<Jxkh_BusinessIndicator>();
		holdList.add(new Jxkh_BusinessIndicator());
		if (jxkhMeetingService.findRank(qikan) != null) {
			holdList.addAll(jxkhMeetingService.findRank(qikan));
		}
		rank.setModel(new ListModelList(holdList));
		rank.setSelectedIndex(0);
	}

	/** 类别列表渲染器 */
	public class qkTypeRenderer implements ListitemRenderer {
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
		}
	}

	public void initWindow() {
		awardPaging.addEventListener("onPaging", new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				awardList = jxkhAwardService.findAwardByKdNumberAll(user
						.getDept().getKdNumber(), awardPaging.getActivePage(),
						awardPaging.getPageSize());
				awardListbox.setModel(new ListModelList(awardList));
			}
		});
		int totalNum = jxkhAwardService.findAwardByKdNumberAll(user.getDept()
				.getKdNumber());
		awardPaging.setTotalSize(totalNum);
		awardList = jxkhAwardService.findAwardByKdNumberAll(user.getDept()
				.getKdNumber(), awardPaging.getActivePage(), awardPaging
				.getPageSize());
		awardListbox.setModel(new ListModelList(awardList));
		String[] a = { "","填写中", "待审核", "部门审核中", "部门通过", "部门不通过","业务办暂缓通过", "业务办通过", "业务办不通过",
		"归档" };
		List<String> auditStateList = new ArrayList<String>();
		for (int i = 0; i < 8; i++) {
			auditStateList.add(a[i]);
		}
		auditState.setModel(new ListModelList(auditStateList));
		auditState.setSelectedIndex(0);
	}

	/** 状态列表渲染器 */
	public class auditStateRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			String auditState = (String) data;
			item.setValue(auditState);
			Listcell c0 = new Listcell();
			if (auditState == null || auditState.equals(""))
				c0.setLabel("--请选择--");
			else {
				c0.setLabel(auditState);
			}
			item.appendChild(c0);
		}
	}

	// 奖励列表渲染器
	public class AwardRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			final Jxkh_Award award = (Jxkh_Award) data;
			item.setValue(award);
			Listcell c0 = new Listcell();
			Listcell c1 = new Listcell(item.getIndex() + 1 + "");
			Listcell c2 = new Listcell(award.getName().length()>10?award.getName().substring(0, 10)+"...":award.getName());
			c2.setTooltiptext(award.getName());
			c2.setStyle("color:blue");

			c2.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event event) throws Exception {
					Listitem item = (Listitem) event.getTarget().getParent();
					Jxkh_Award award = (Jxkh_Award) item.getValue();
					AddAwardWin w = (AddAwardWin) Executions
							.createComponents(
									"/admin/personal/deptbusinessdata/award/addaward.zul",
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
					if (isQuery) {
						onClick$query();
					} else {
						initWindow();
					}
				}
			});
			//奖励级别
			Listcell c3 = new Listcell();
			if (award.getRank() != null) {
				c3 = new Listcell(award.getRank().getKbName());
			} else {
				c3 = new Listcell("");
			}
			//积分年度
			Listcell c4 = new Listcell();
			if (award.getjxYear() != null)
				c4 = new Listcell(award.getjxYear());
			//该项得分
			Listcell c5 = new Listcell(award.getScore() == null ? "" : award
					.getScore().toString());
			//填写人
			Listcell c6 = new Listcell();
			c6.setLabel(award.getSubmitName());
			//审核状态
			Listcell c7 = new Listcell();
			c7.setTooltiptext("点击查看审核结果");
			if (award.getState() == null || award.getState() == 0) {
				c7.setLabel("待审核");
				c7.setStyle("color:red");
			} else {
				switch (award.getState()) {
				case 0:
					break;
				case 1:
					c7.setLabel("部门通过");
					c7.setStyle("color:red");
					break;
				case 2:
					c7.setLabel("部门审核中");
					c7.setStyle("color:red");
					break;
				case 3:
					c7.setLabel("部门不通过");
					c7.setStyle("color:red");
					break;
				case 4:
					c7.setLabel("业务办通过");
					c7.setStyle("color:red");
					break;
				case 5:
					c7.setLabel("业务办不通过");
					c7.setStyle("color:red");
					break;
				case 6:
					c7.setLabel("归档");
					c7.setStyle("color:red");
					break;
				case JXKH_MEETING.WRITING:
					c7.setLabel("填写中");
					c7.setStyle("color:red");
					break;
				case JXKH_MEETING.BUSINESS_TEMP_PASS:
					c7.setLabel("业务办暂缓通过");
					c7.setStyle("color:red");
					break;
				}
			}
			// 弹出审核意见事件
			c7.addEventListener(Events.ON_CLICK, new EventListener() {
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
		}
	}

	// 单击添加按钮，弹出添加奖励界面
	public void onClick$add() {
		AddAwardWin win = (AddAwardWin) Executions.createComponents(
				"/admin/personal/deptbusinessdata/award/addaward.zul", null,
				null);
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

	// 删除是弹出确定删除框
	public void onClick$del() throws InterruptedException {
		if (awardListbox.getSelectedItems() == null
				|| awardListbox.getSelectedItems().size() <= 0) {
			try {
				Messagebox.show("请选择要删除的奖励！", "提示", Messagebox.OK,
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
							@SuppressWarnings("unchecked")
							Iterator<Listitem> items = awardListbox
									.getSelectedItems().iterator();
							List<Jxkh_Award> awardList = new ArrayList<Jxkh_Award>();
							Jxkh_Award award = new Jxkh_Award();
							while (items.hasNext()) {
								award = (Jxkh_Award) items.next().getValue();
								awardList.add(award);
								if (award.getState() == Jxkh_Award.DEPT_PASS
										|| award.getState() == Jxkh_Award.First_Dept_Pass
										|| award.getState() == Jxkh_Award.BUSINESS_PASS || award.getState().shortValue() == JXKH_MEETING.BUSINESS_TEMP_PASS
										|| award.getState() == Jxkh_Award.SAVE_RECORD) {
									try {
										Messagebox.show(
												"部门或业务办审核通过或归档的奖励不能删除！", "提示",
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
								Messagebox.show("奖励删除成功！", "提示", Messagebox.OK,
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
		nameSearch = awardName.getValue();
		auditStateSearch = null;
		isQuery = true;
		if (auditState.getSelectedItem().getValue().equals("")) {
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
		}else if (auditState.getSelectedItem().getValue().equals("填写中")) {
			auditStateSearch = JXKH_MEETING.WRITING;
		}else if (auditState.getSelectedItem().getValue().equals("业务办暂缓通过")) {
			auditStateSearch = JXKH_MEETING.BUSINESS_TEMP_PASS;
		}
		indicatorId = null;
		if (rank.getSelectedIndex() != 0) {
			Jxkh_BusinessIndicator qkType = (Jxkh_BusinessIndicator) rank
					.getSelectedItem().getValue();
			indicatorId = qkType.getKbId();
		}
		yearSearch = null;
		if (year.getSelectedIndex() != 0 && year.getSelectedItem() != null)
			yearSearch = year.getSelectedItem().getValue() + "";

		awardPaging.addEventListener("onPaging", new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				awardList = jxkhAwardService.findAwardByConditionAndPaging(
						nameSearch, auditStateSearch, indicatorId, yearSearch,
						user.getDept().getKdNumber(),
						awardPaging.getActivePage(), awardPaging.getPageSize());
				awardListbox.setModel(new ListModelList(awardList));
			}
		});

		int totalNum = jxkhAwardService.findAwardByCondition(nameSearch,
				auditStateSearch, indicatorId, yearSearch, user.getDept()
						.getKdNumber());
		awardPaging.setTotalSize(totalNum);
		awardList = jxkhAwardService.findAwardByConditionAndPaging(nameSearch,
				auditStateSearch, indicatorId, yearSearch, user.getDept()
						.getKdNumber(), awardPaging.getActivePage(),
				awardPaging.getPageSize());
		awardListbox.setModel(new ListModelList(awardList));

	}

	public void onClick$searchcbutton() {
		if (cxtj.isVisible()) {
			cxtj.setVisible(false);
		} else {
			cxtj.setVisible(true);
		}
	}

	public void onClick$reset() {
		awardName.setValue("");
		auditState.getItemAtIndex(0).setSelected(true);
		rank.getItemAtIndex(0).setSelected(true);
		year.getItemAtIndex(0).setSelected(true);
	}

	// 导出
	public void onClick$expert() throws WriteException, IOException {
		if (awardListbox.getSelectedCount() == 0) {
			try {
				Messagebox.show("提示请选择要导出的数据", "提示", Messagebox.OK,
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
			String Title = "奖励情况";
			List<String> titlelist = new ArrayList<String>();
			titlelist.add("序号");
			titlelist.add("奖励名称");
			titlelist.add("完成人");
			titlelist.add("院内部门");
			titlelist.add("院外部门");
			titlelist.add("奖励级别");
			titlelist.add("奖励证书号");
			titlelist.add("获奖时间");
			titlelist.add("授奖单位");
			titlelist.add("信息填写人");
			String c[][] = new String[expertlist.size()][titlelist.size()];
			// 从SQL中读数据
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
						member += mem.getName() + "、";
					}
					c[j][2] = member.substring(0, member.length() - 1);
				}
				List<Jxkh_AwardDept> dlist = jxkhAwardService
						.findAwardDeptByAwardId(award);
				String dept = "";
				if (dlist.size() != 0) {
					for (int m = 0; m < dlist.size(); m++) {
						Jxkh_AwardDept de = (Jxkh_AwardDept) dlist.get(m);
						dept += de.getName() + "、";
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
