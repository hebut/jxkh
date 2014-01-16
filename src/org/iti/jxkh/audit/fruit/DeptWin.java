package org.iti.jxkh.audit.fruit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import jxl.write.WriteException;

import org.iti.gh.common.util.ExportExcel;
import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxkh.business.meeting.DownloadWindow;
import org.iti.jxkh.deptbusiness.fruit.AddFruitWin;
import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.entity.Jxkh_Fruit;
import org.iti.jxkh.entity.Jxkh_FruitDept;
import org.iti.jxkh.entity.Jxkh_FruitFile;
import org.iti.jxkh.entity.Jxkh_FruitMember;
import org.iti.jxkh.service.JXKHMeetingService;
import org.iti.jxkh.service.JxkhFruitService;
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
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.iti.common.util.ConvertUtil;
import com.uniwin.framework.entity.WkTUser;

public class DeptWin extends Window implements AfterCompose {

	/**
	 * @author ZhangyuGuang
	 */
	private static final long serialVersionUID = 5401490265863483105L;
	private Textbox fruitName;
	private Listbox fruitListbox, auditState, rank;
	private Groupbox cxtj;
	private YearListbox year;
	private Long indicatorId;
	private Paging fruitPaging;
	private List<Jxkh_Fruit> fruitList = new ArrayList<Jxkh_Fruit>();
	private Set<Jxkh_FruitFile> filesList;
	private JxkhFruitService jxkhFruitService;
	private WkTUser user;
	private boolean isQuery = false;
	private String nameSearch, yearSearch;
	private Short auditStateSearch;
	private JXKHMeetingService jxkhMeetingService;
	public static final Integer qikan = 61;

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		year.initYearListbox("");
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");// 获取当前登录用户
		fruitListbox.setItemRenderer(new FruitRenderer());
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
		fruitPaging.addEventListener("onPaging", new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				fruitList = jxkhFruitService.findFruitByKbNumberAndPaging2(user
						.getDept().getKdNumber(), fruitPaging.getActivePage(),
						fruitPaging.getPageSize());
				fruitListbox.setModel(new ListModelList(fruitList));
			}
		});
		int totalNum = jxkhFruitService.findFruitByKbNumber2(user.getDept()
				.getKdNumber());
		fruitPaging.setTotalSize(totalNum);
		fruitList = jxkhFruitService.findFruitByKbNumberAndPaging2(user
				.getDept().getKdNumber(), fruitPaging.getActivePage(),
				fruitPaging.getPageSize());
		fruitListbox.setModel(new ListModelList(fruitList));
		String[] a = { "", "待审核","部门审核中", "部门通过",  "部门不通过" };
		List<String> auditStateList = new ArrayList<String>();
		for (int i = 0; i < 5; i++) {
			auditStateList.add(a[i]);
		}
		auditState.setModel(new ListModelList(auditStateList));
		auditState.setSelectedIndex(0);
	}

	// 成果列表渲染器
	public class FruitRenderer implements ListitemRenderer {

		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			final Jxkh_Fruit fruit = (Jxkh_Fruit) data;
			List<Jxkh_FruitMember> memberList = fruit.getFruitMember();
			String member = "";
			item.setValue(fruit);
			Listcell c0 = new Listcell();
			Listcell c1 = new Listcell(item.getIndex() + 1 + "");
			Listcell c2 = new Listcell(fruit.getName().length() <= 14?fruit.getName():fruit.getName().substring(0, 14) + "...");
			c2.setTooltiptext("点击查看鉴定成果信息");
			c2.setStyle("color:blue");
			c2.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event event) throws Exception {
					Listitem item = (Listitem) event.getTarget().getParent();
					Jxkh_Fruit fruit = (Jxkh_Fruit) item.getValue();
					AddFruitWin w = (AddFruitWin) Executions
							.createComponents(
									"/admin/personal/deptbusinessdata/fruit/addfruit.zul",
									null, null);
					try {
						w.setFruit(fruit);
						w.setAudit("AUDIT");
						w.initWindow();
						w.doModal();
					} catch (SuspendNotAllowedException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});

			Listcell c3 = new Listcell("");
			if (fruit.getAppraiseRank() != null)
				c3 = new Listcell(fruit.getAppraiseRank().getKbName());
			Listcell c4 = new Listcell("");
			// if (fruit.getAcceptRank() != null)
			// c4 = new Listcell(fruit.getAcceptRank().getKbName());
			if (fruit.getjxYear() != null)
				c4 = new Listcell(fruit.getjxYear());
			Listcell c5 = new Listcell();
			c5.setTooltiptext("下载文档");
			Toolbarbutton downlowd = new Toolbarbutton();
			downlowd.setImage("/css/default/images/button/down.gif");
			downlowd.setParent(c5);
			downlowd.setHeight("20px");
			downlowd.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					DownloadWindow win = (DownloadWindow) Executions
							.createComponents(
									"/admin/personal/businessdata/meeting/download.zul",
									null, null);

					filesList = fruit.getFruitFile();
					win.setFiles(filesList);
					win.setFlag("FRUIT");
					win.initWindow();
					win.doModal();
				}
			});
			//Listcell c6 = new Listcell();// (fruit.getScore() == null ? "" :
			// fruit.getScore().toString());
			Listcell c6 = new Listcell(fruit.getScore() == null ? "0" : fruit.getScore().toString());
			Listcell c7 = new Listcell();
			c7.setTooltiptext("点击填写审核意见");
			if (fruit.getState() == null || fruit.getState() == 0) {
				c7.setLabel("待审核");
				c7.setStyle("color:red");
			} else {
				switch (fruit.getState()) {
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
				case 7:
					c7.setLabel("业务办暂缓通过");
					c7.setStyle("color:red");
					break;
				}
			}
			c7.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event event) throws Exception {
					AdviceWin w = (AdviceWin) Executions.createComponents(
							"/admin/jxkh/audit/fruit/advice.zul", null, null);
					try {
						w.setMeeting(fruit);
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

	public void onClick$passAll() {
		if (fruitListbox.getSelectedItems() == null
				|| fruitListbox.getSelectedItems().size() <= 0) {
			try {
				Messagebox.show("请选择要审核成果！", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		Iterator<Listitem> items = fruitListbox.getSelectedItems().iterator();
		List<Jxkh_Fruit> fruitList = new ArrayList<Jxkh_Fruit>();
		Jxkh_Fruit fruit = new Jxkh_Fruit();
		while (items.hasNext()) {

			fruit = (Jxkh_Fruit) items.next().getValue();
			int rank = 0, index = 0;
			List<Jxkh_FruitDept> meetingDepList = fruit.getFruitDept();
			for (int t = 0; t < meetingDepList.size(); t++) {
				Jxkh_FruitDept dep = meetingDepList.get(t);
				if (dep.getDeptId().equals(user.getDept().getKdNumber())) {
					rank = dep.getRank();
					index = t;
				}
			}
			if ((rank == 1 || fruit.getState() == Jxkh_Fruit.First_Dept_Pass)
					&& fruit.getTempState().charAt(index) == '0') {
				fruitList.add(fruit);
			}

		}
		BatchAuditWin win = (BatchAuditWin) Executions.createComponents(
				"/admin/jxkh/audit/fruit/batchAudit.zul", null, null);
		try {
			win.setFruitList(fruitList);
			win.doModal();

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

	public void onClick$query() {
		nameSearch = fruitName.getValue();
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

		fruitPaging.addEventListener("onPaging", new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				fruitList = jxkhFruitService.findFruitByConditionAndPaging(
						nameSearch, auditStateSearch, indicatorId, yearSearch,
						user.getDept().getKdNumber(),
						fruitPaging.getActivePage(), fruitPaging.getPageSize());
				fruitListbox.setModel(new ListModelList(fruitList));
			}
		});

		int totalNum = jxkhFruitService.findFruitByCondition(nameSearch,
				auditStateSearch, indicatorId, yearSearch, user.getDept()
						.getKdNumber());
		fruitPaging.setTotalSize(totalNum);
		fruitList = jxkhFruitService.findFruitByConditionAndPaging(nameSearch,
				auditStateSearch, indicatorId, yearSearch, user.getDept()
						.getKdNumber(), fruitPaging.getActivePage(),
				fruitPaging.getPageSize());
		fruitListbox.setModel(new ListModelList(fruitList));
	}

	public void onClick$searchcbutton() {
		if (cxtj.isVisible()) {
			cxtj.setVisible(false);
		} else {
			cxtj.setVisible(true);
		}

	}

	public void onClick$reset() {
		fruitName.setValue("");
		auditState.getItemAtIndex(0).setSelected(true);
	}

	// 导出
	public void onClick$expert() throws WriteException, IOException {
		if (fruitListbox.getSelectedCount() == 0) {
			try {
				Messagebox.show("提示请选择要导出的数据", "提示", Messagebox.OK,
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
			String Title = "成果情况";
			List<String> titlelist = new ArrayList<String>();
			titlelist.add("序号");
			titlelist.add("成果名称");
			titlelist.add("完成人");
			titlelist.add("院内部门");
			titlelist.add("院外部门");
			titlelist.add("成果水平");
			titlelist.add("鉴定号");
			titlelist.add("鉴定形式");
			titlelist.add("鉴定日期");
			titlelist.add("组织鉴定单位");
			titlelist.add("主持鉴定单位");
			titlelist.add("验收等级");
			titlelist.add("验收号");
			titlelist.add("验收组织部门");
			titlelist.add("验收日期");
			titlelist.add("信息填写人");
			String c[][] = new String[expertlist.size()][titlelist.size()];
			// 从SQL中读数据
			for (int j = 0; j < expertlist.size(); j++) {
				Jxkh_Fruit fruit = (Jxkh_Fruit) expertlist.get(j);
				List<Jxkh_FruitMember> mlist = fruit.getFruitMember();
				String member = "";
				c[j][0] = j + 1 + "";
				c[j][1] = fruit.getName();
				if (mlist.size() != 0) {
					for (int m = 0; m < mlist.size(); m++) {
						Jxkh_FruitMember mem = (Jxkh_FruitMember) mlist.get(m);
						member += mem.getName() + "、";
					}
					c[j][2] = member.substring(0, member.length() - 1);
				}
				List<Jxkh_FruitDept> dlist = fruit.getFruitDept();
				String dept = "";
				if (dlist.size() != 0) {
					for (int m = 0; m < dlist.size(); m++) {
						Jxkh_FruitDept de = (Jxkh_FruitDept) dlist.get(m);
						dept += de.getName() + "、";
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
