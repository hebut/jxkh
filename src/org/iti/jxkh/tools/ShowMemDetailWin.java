package org.iti.jxkh.tools;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jxl.write.WriteException;

import org.iti.gh.common.util.ExportExcel;
import org.iti.jxkh.entity.jxkh_ComputDeptTools;
import org.iti.jxkh.service.AuditResultService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;
import org.zkoss.zul.api.Decimalbox;

import com.iti.common.util.ConvertUtil;
import com.uniwin.framework.entity.WkTUser;

public class ShowMemDetailWin extends Window implements AfterCompose{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5642197098568117345L;

	private Label deptName;
	private Decimalbox UpMemNum;
	private Textbox UpMember;
	private Toolbarbutton chooseMem;
	private Label perBasicAcount;
	private Listbox showMemAcount;
	private Double totalNum;//某业务部门绩效工资总额
	private Long deptId;//某业务部门编号
	private Integer grade;//部门档次
	
	private List<WkTUser> user = new ArrayList<WkTUser>();
	private List<WkTUser> oldUser = new ArrayList<WkTUser>();
	private List<WkTUser> upUser = new ArrayList<WkTUser>();
	private List<WkTUser> list = new ArrayList<WkTUser>();
	
	private AuditResultService auditResultService;
	
	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		
		showMemAcount.setItemRenderer(new ListitemRenderer() {
			
			@Override
			public void render(Listitem arg0, Object arg1) throws Exception {
				if(arg1==null) return;
				final WkTUser person=(WkTUser)arg1;
				arg0.setValue(arg1);
				Listcell c0=new Listcell(arg0.getIndex()+1+"");
				Listcell c1=new Listcell(person.getStaffId());//员工ID
				Listcell c2=new Listcell(person.getKuName());//员工姓名
				Listcell c4=new Listcell(person.getKuAnswer());//员工档次
				Listcell c5=new Listcell(person.getKuAutoshow());//员工工资数
				 
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c4);
				arg0.appendChild(c5);
			}
		});
		
		initWindow();
	}

	public void initWindow(){
		chooseMem.addEventListener(Events.ON_CLICK, new EventListener() {
			
			@Override
			public void onEvent(Event arg0) throws Exception {
				final chooseMemWin win = (chooseMemWin) Executions.createComponents("/admin/jxkh/tools/bonusDistribute/chooseMem.zul", null, null);
				user = auditResultService.findUserByDept(String.valueOf(getDeptId()));
				if(oldUser.size() == 0){
					oldUser.addAll(user);
				}
				win.setManagerList(user);
				win.setNumber(UpMemNum.intValue());
				win.initWindow();
				win.addEventListener(Events.ON_CHANGE, new EventListener() {
					
					@Override
					public void onEvent(Event arg0) throws Exception {
						user.clear();
						for(WkTUser u: win.getManagerList()){
							user.add(u);
						}
						
						String depts = "";
						for (WkTUser dept : user) {
							depts += dept.getKuName() + ",";
						}
						if (depts.endsWith(",")) {
							depts = depts.substring(0, depts.length() - 1);
						}
						UpMember.setValue(depts);
						upUser.clear();
						upUser.addAll(win.getManagerList());
						win.detach();
					}
				});
				win.doHighlighted();
				win.doModal();
			}
		});
	}
	
	public void onClick$compute(){

		list.clear();
		for(WkTUser u:upUser){
			WkTUser user1 = new WkTUser();
			user1.setStaffId(u.getStaffId());
			user1.setKuName(u.getKuName());
			//员工档次
			String str = jxkh_ComputDeptTools.getEnum(getGrade() + 1).getName();
			user1.setKuAnswer(str);
			//计算工资，调用方法
			Double computeNum = compute(Double.valueOf(getPerBasicAcount()), getGrade() + 1);
			user1.setKuAutoshow(String.valueOf(computeNum));
			
			list.add(user1);
		}
		
		oldUser.removeAll(upUser);
		for(WkTUser u: oldUser){
			WkTUser user2 = new WkTUser();
			user2.setStaffId(u.getStaffId());
			user2.setKuName(u.getKuName());
			//员工档次
			String str = jxkh_ComputDeptTools.getEnum(getGrade()).getName();
			user2.setKuAnswer(str);
			//计算工资，调用方法
			Double computeNum = compute(Double.valueOf(getPerBasicAcount()), getGrade());
			user2.setKuAutoshow(String.valueOf(computeNum));
			
			list.add(user2);
		}
		
		showMemAcount.setModel(new ListModelList(list));
	}

	public Double compute(Double perbBasicAcount, Integer i){
		Double gradeNum = (double) 0;
		switch (i) {
		case 1:
			gradeNum = (double) 1;
			break;
		case 2:
			gradeNum = 1.3;
			break;
		case 3:
			gradeNum = 1.6;
			break;
		case 4:
			gradeNum = (double) 2;
			break;

		default:
			break;
		}
		Double computeNum = perbBasicAcount*gradeNum;
		return computeNum;
		
	}

	public String getPerBasicAcount() {
		return perBasicAcount.getValue();
	}

	public void setPerBasicAcount(String str) {
		perBasicAcount.setValue(str);
	}

	public void setUpMemNum(Double upMemNum) {
		UpMemNum.setValue(BigDecimal.valueOf(upMemNum));
	}

	public void setDeptName(String str) {
		deptName.setValue(str);
	}

	public String getDeptName() {
		return deptName.getValue();
	}

	public void setTotalNum(Double totalNum) {
		this.totalNum = totalNum;
	}

	public Double getTotalNum() {
		return totalNum;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	public void onClick$export() throws WriteException, IOException{
		Date now=new Date ();
		String fileName = ConvertUtil.convertDateString(now) + deptName.getValue() + 
				"人员奖励性绩效工资分配详细"+".xls";
		String Title = deptName.getValue() + "人员奖励性绩效工资分配详细";
		List<String> titlelist = new ArrayList<String>();
		titlelist.add("姓名");
		titlelist.add("四档");
		titlelist.add("三档");
		titlelist.add("二档");
		titlelist.add("一档"); 
		titlelist.add("基数");
		titlelist.add("金额");
		
		String c[][]=new String[list.size()][titlelist.size()];
		for(int j = 0; j < list.size(); j ++){
			WkTUser result = (WkTUser)list.get(j);
			c[j][0] = result.getKuName();
			if(result.getKuAnswer().equals("四档")){
				c[j][1] = "1";
				c[j][5] = "2";
			}else if(result.getKuAnswer().equals("三档")){
				c[j][2] = "1";
				c[j][5] = "1.6";
			}else if(result.getKuAnswer().equals("二档")){
				c[j][3] = "1";
				c[j][5] = "1.3";
			}else if(result.getKuAnswer().equals("一档")){
				c[j][4] = "1";
				c[j][5] = "1";
			}
			c[j][6] = result.getKuAutoshow();
		}
		ExportExcel ee=new ExportExcel();
		ee.exportExcel(fileName, Title, titlelist, list.size(), c);
	}
}
