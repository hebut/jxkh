package org.iti.jxkh.business.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxkh.entity.JXKH_MEETING;
import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.entity.Jxkh_Project;
import org.iti.jxkh.entity.Jxkh_ProjectDept;
import org.iti.jxkh.entity.Jxkh_ProjectFund;
import org.iti.jxkh.entity.Jxkh_ProjectMember;
import org.iti.jxkh.service.BusinessIndicatorService;
import org.iti.jxkh.service.JxkhProjectService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.iti.common.util.ConvertUtil;
import com.iti.common.util.PropertiesLoader;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.service.DepartmentService;

public class AddFundWindow extends Window implements AfterCompose {

	/**
	 * @author SongYu
	 */
	private static final long serialVersionUID = -812786509750478800L;

	private Textbox money, operator, remark;
	private Listbox type;
	private Listbox toDeptListbox;
	private Datebox date;
	private YearListbox jiFenTime;
	private JxkhProjectService jxkhProjectService;
	private DepartmentService departmentService;
	private BusinessIndicatorService businessIndicatorService;
	private Jxkh_Project project;
	private Short sort;

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		type.setItemRenderer(new TypeRenderer());
		toDeptListbox.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				if(arg1 instanceof Jxkh_ProjectDept) {
					Jxkh_ProjectDept dept = (Jxkh_ProjectDept) arg1;
					if(dept != null) {
						arg0.setLabel(dept.getName());
						arg0.setValue(dept);
					}
				}else {
					arg0.setLabel("-请选择-");
					arg0.setSelected(true);
				}				
			}
		});
		jiFenTime.initYearListbox("");
		initListbox();
	}

	public void initListbox() {
		String[] t = { "经费收入", "院外拨款支出" };
		List<String> typeList = new ArrayList<String>();
		for (int i = 0; i < 2; i++) {
			typeList.add(t[i]);
		}
		type.setModel(new ListModelList(typeList));		
		initDeptListbox();
	}
	
	public void initDeptListbox() {
		List<Object> olist = new ArrayList<Object>();
		olist.add(-1L);
		List<Jxkh_ProjectDept> dlist = jxkhProjectService.findProjectDep(project);
		olist.addAll(dlist);
		toDeptListbox.setModel(new ListModelList(olist));
		toDeptListbox.getItemAtIndex(0).setSelected(true);
	}

	public void onClick$submit() throws InterruptedException {
		Jxkh_ProjectFund fund = new Jxkh_ProjectFund();

		if (date.getValue() == null) {
			Messagebox.show("日期不能为空！", "提示", Messagebox.OK,
					Messagebox.INFORMATION);
			date.focus();
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
		if(toDeptListbox.getSelectedIndex() == 0) {
			Messagebox.show("所属部门必须选择！", "提示", Messagebox.OK,
					Messagebox.INFORMATION);
			date.focus();
			return;
		}

		fund.setProject(project);
		if (date.getValue() != null)
			fund.setDate(ConvertUtil.convertDateString(date.getValue()));
		fund.setMoney(Float.parseFloat(money.getValue()));
		fund.setRemark(remark.getValue());
		fund.setSort(sort);
		fund.setTransactor(operator.getValue());
		fund.setjxYear(jiFenTime.getSelectedItem().getValue() + "");
		List<WkTDept> dlist = departmentService.getDeptByNum(((Jxkh_ProjectDept)toDeptListbox.getSelectedItem().getValue()).getDeptId());
		if(dlist.size() > 0 && dlist.get(0) != null) {
			fund.setDeptNum(dlist.get(0).getKdId());
		}

		if ((String) type.getSelectedItem().getValue() == "经费收入") {
			fund.setType(Jxkh_ProjectFund.IN);
		} else if ((String) type.getSelectedItem().getValue() == "院外拨款支出") {
			fund.setType(Jxkh_ProjectFund.OUT);
		}
		try {
			jxkhProjectService.saveOrUpdate(fund);
//			List<Jxkh_ProjectFund> fundList = jxkhProjectService.findFunds(project, null);
//			fundList.add(fund);
//			project.setProjectFund(fundList);
			project.setTempState("0000000");// 为临时状态赋初值
			project.setDep1Reason("");
			project.setDep2Reason("");
			project.setDep3Reason("");
			project.setDep4Reason("");
			project.setDep5Reason("");
			project.setDep6Reason("");
			project.setDep7Reason("");
			project.setbAdvice("");
			if(project.getState().shortValue() != JXKH_MEETING.WRITING)
				project.setState(Jxkh_Project.NOT_AUDIT);			
			if(sort != null && sort.equals(Jxkh_ProjectFund.ZXF)) {
				//计算部门和个人得分
				computeScore();	
			}
			Events.postEvent(Events.ON_CHANGE, this, null);
			Messagebox.show("经费信息保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				Messagebox.show("经费信息保存失败，请重试！", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		this.detach();
	}

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
		}
	}

	public void onClick$close() {
		this.onClose();
	}

	public Jxkh_Project getProject() {
		return project;
	}

	public void setProject(Jxkh_Project project) {
		this.project = project;
		initDeptListbox();
	}

	public Short getSort() {
		return sort;
	}

	public void setSort(Short sort) {
		this.sort = sort;
	}
	
	private void computeScore() {
		float res = 0f;
		float result = 0f;
		String type = "";
		float nationwork = 0f, provincestudy = 0f, provincework = 0f, citystudy = 0f, hproject = 0f;
		Properties property = PropertiesLoader.loader("title", "title.properties");
		/**
		 * 第一步：计算项目的基本得分
		 */
		// 不是第一署名
		if (project.getRank() != null) {
			if (project.getFirstSign() != null && project.getFirstSign().equals(Jxkh_Project.NO)) {
				if (project.getRank().getKbName().equals(property.getProperty("nationstudy"))) {
					nationwork += 1f;
					result = nationwork;
					type = property.getProperty("nationwork");
				} else if (project.getRank().getKbName().equals(property.getProperty("nationwork"))) {
					provincestudy += 1f;
					result = provincestudy;
					type = property.getProperty("provincestudy");
				} else if (project.getRank().getKbName().equals(property.getProperty("provincestudy"))) {
					provincework += 1f;
					result = provincework;
					type = property.getProperty("provincework");
				} else if (project.getRank().getKbName().equals(property.getProperty("provincework"))) {
					citystudy += 1f;
					result = citystudy;
					type = property.getProperty("citystudy");
				} else if (project.getRank().getKbName().equals(property.getProperty("citystudy"))) {
					hproject += 1f;
					result = hproject;
					type = property.getProperty("hproject");
				} else if (project.getRank().getKbName().equals(property.getProperty("hproject"))) {
					hproject += 0.51f;
					result = hproject;
					type = property.getProperty("hproject");
				}
				Jxkh_BusinessIndicator bi = (Jxkh_BusinessIndicator) businessIndicatorService.getEntityByName(type);
				if (bi != null) {
					res = result * bi.getKbIndex() * bi.getKbScore();
				} else {
					res = 0f;
				}
			}
			// 是第一署名
			else {
				res = project.getRank().getKbIndex() * project.getRank().getKbScore();
			}
			if (project.getIfHumanities() != null && project.getIfHumanities().shortValue() == Jxkh_Project.YES) {
				res = 3 * res;
			} else if (project.getIfSoftScience() != null && project.getIfSoftScience().shortValue() == Jxkh_Project.YES) {
				res = 2 * res;
			}
		}
		/**
		 * 第二步：计算各部门的得分、人员的得分
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
				d.setScore(dScoreA.get(dt.get(0).getKdId()) * res);
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
					f = totalFund * res * percent / 10;
				m.setPer(percent);
				m.setScore(f);
				jxkhProjectService.update(m);
			}
			List<Jxkh_ProjectMember> list = jxkhProjectService.findProjectMember(project);
			project.setProjectMember(list);
			List<Jxkh_ProjectDept> dlist2 = jxkhProjectService.findProjectDep(project);
			project.setProjectDept(dlist2);
			// 更新项目的得分
			project.setScore(totalFund * res);
			jxkhProjectService.update(project);
		}
	}
}
