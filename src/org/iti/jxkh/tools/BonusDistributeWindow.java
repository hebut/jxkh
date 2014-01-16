package org.iti.jxkh.tools;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jxl.write.WriteException;

import org.iti.gh.common.util.ExportExcel;
import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxkh.service.AuditConfigService;
import org.iti.jxkh.entity.jxkh_ComputDeptTools;
import org.iti.jxkh.service.AuditResultService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.iti.common.util.ConvertUtil;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;

public class BonusDistributeWindow extends Window implements AfterCompose{

	/**
	 * 
	 * 
	 */
	private static final long serialVersionUID = 1606611870404692535L;
	
	//EXPORT
	//private 
	
	//基本信息变量
	private Tab oprateDept,manageDept,president;
	private org.zkoss.zul.Decimalbox totalAcount;
	private Decimalbox DeptNum;//业务部门人数
	private Decimalbox ManageNum;//管理部门人数
	private Decimalbox LeaderNum;//院领导人数

	//业务部门变量
	
	private YearListbox yearlist;
	private Decimalbox DeptTotalAcount;//业务部门奖励性绩效工资总额
	private Grid setDeptNum;//设置各部门人数
	private Rows rows;
	private Textbox oprateDept3,oprateDept2,oprateDept1;//选择各部门档次
	private List<WkTDept> DeptList3 = new ArrayList<WkTDept>();//三档次部门
	private List<WkTDept> DeptList2 = new ArrayList<WkTDept>();//二档次部门
	private List<WkTDept> DeptList1 = new ArrayList<WkTDept>();//一档次部门
	private Label perBasicAcount;//一个基数额度
	private Listbox computeListbox;
	private List<jxkh_ComputDeptTools> jxkh = new ArrayList<jxkh_ComputDeptTools>();
	
	private AuditResultService auditResultService;
	
	private List<WkTDept> computeList = new ArrayList<WkTDept>();
	private List<WkTDept> deptList = new ArrayList<WkTDept>();
	private List<WkTDept> OldDeptList = new ArrayList<WkTDept>();
	
	
	/*****************************************************************************************/
	//管理人员变量设置
	private Label manageTotalAcount,perBasicAcountGl;//管理部门总工资数，管理人员基数额度
	private Decimalbox oneGradePeo,twoGradePeo,threeGradePeo,fourGradePeo;//各个档次人数
	private Textbox fourTxt,threeTxt,twoTxt,oneTxt;//选择管理人员档次
	private List<WkTUser>managerPeoList=new ArrayList<WkTUser>();
	private List<WkTUser>oldmanagerPeoList=new ArrayList<WkTUser>();
	private List<WkTDept>manageDeptList=new ArrayList<WkTDept>();
	private List<WkTUser>managerPeoList4=new ArrayList<WkTUser>();
	private List<WkTUser>managerPeoList3=new ArrayList<WkTUser>();
	private List<WkTUser>managerPeoList2=new ArrayList<WkTUser>();
	private List<WkTUser>managerPeoList1=new ArrayList<WkTUser>();
	private List<WkTUser>resultgl=new ArrayList<WkTUser>();
	private Listbox  reSultGlListbox;//管理人员结果
	private YearListbox yearListGl;
	/*****************************************************************************************************************************/
	//院领导变量
	private Label  leaderTotalAcount,perBasicAcountLd;//院领导总工资，院领导基数额度
	private Decimalbox yxGradePeo,hgGradePeo;//个档次人数
	private YearListbox yearListLd;
	private List<WkTUser>leaderPeoList=new ArrayList<WkTUser>();
	private List<WkTUser>oldleaderPeoList=new ArrayList<WkTUser>();
	private List<WkTUser>leaderPeoList2=new ArrayList<WkTUser>();
	private List<WkTUser>leaderPeoList1=new ArrayList<WkTUser>();
	private List<WkTUser>resultLeader=new ArrayList<WkTUser>();//院领导工资结果
	private AuditConfigService auditConfigService;
	private Textbox yxTxt,hgTxt;
	private Listbox reSultLdListbox;//院领导工资结果列表
	
	/******************************************************************************************************************************/
	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		
		//tab
		oprateDept.addEventListener(Events.ON_CLICK, new EventListener() {
			
			@Override
			public void onEvent(Event arg0) throws Exception {
				manageDept.setSelected(false);
				president.setSelected(false);
				oprateDept.setSelected(true);
			}
		});
		manageDept.addEventListener(Events.ON_CLICK, new EventListener() {
			
			@Override
			public void onEvent(Event arg0) throws Exception {
				oprateDept.setSelected(false);
				president.setSelected(false);
				manageDept.setSelected(true);
			}
		});
		president.addEventListener(Events.ON_CLICK, new EventListener() {
			
			@Override
			public void onEvent(Event arg0) throws Exception {
				oprateDept.setSelected(false);
				manageDept.setSelected(false);
				president.setSelected(true);
				//setLeaderTotalNum
				if(!perBasicAcountGl.getValue().isEmpty() && LeaderNum.getValue() != null && !perBasicAcount.getValue().isEmpty() ){
					float leaderTotalMoney1=(float) (Float.parseFloat(perBasicAcountGl.getValue())*1.6* ((LeaderNum.getValue().doubleValue())/2));//管理部门基数额度*1.6*院领导人数/2
					float leaderTotalMoney2=(float) (Float.parseFloat(perBasicAcount.getValue())*1.6* ((LeaderNum.getValue().doubleValue())/2));//业务部门基数额度*1.6*院领导人数/2
				    float leaderTotalMoney=leaderTotalMoney1+leaderTotalMoney2;
				    String str = String.valueOf(df(leaderTotalMoney));
				    if(str != null){
				    	leaderTotalAcount.setValue(str);//院领导总工资数
				    }
				}
			}
		});
		yearlist.initYearListbox("");
		computeList = auditResultService.findWorkDept();//查找业务部门
		reSultGlListbox.setItemRenderer(new resultglRenderer());
		reSultLdListbox.setItemRenderer(new reSultLdRenderer());
		yearListGl.initYearListbox("");
		yearListLd.initYearListbox("");
		initRows(computeList);
		
		computeListbox.setModel(new ListModelList(computeList));
		computeListbox.setItemRenderer(new listRenderer());

		
	} 
	
	public  class listRenderer implements ListitemRenderer {//业务部门渲染器
		
			public void render(Listitem arg0, Object arg1) throws Exception {
				final WkTDept wkt = (WkTDept) arg1;
				arg0.setValue(wkt);
				//业务部门名称
				final Listcell c1 = new Listcell();
				final Label l = new Label();
				l.setValue(wkt.getKdName());
				l.setId(wkt.getKdNumber());
				l.setParent(c1);

				//各业务部门人数
				final Listcell c2 = new Listcell();
				Label DeptPersonNum = new Label();
				DeptPersonNum.setParent(c2);
				DeptPersonNum.setId(wkt.getKdNumber() + "1");
				
				//四档次人数
				Listcell c3 = new Listcell();
				Decimalbox forthNum = new Decimalbox();
				forthNum.setParent(c3);
				forthNum.setId(wkt.getKdNumber() + "2");
				forthNum.setWidth("55%");
				
				//三档次人数
				Listcell c4 = new Listcell();
				Decimalbox thirdNum = new Decimalbox();
				thirdNum.setParent(c4);
				thirdNum.setId(wkt.getKdNumber() + "3");
				thirdNum.setWidth("55%");
				
				//二档次人数
				Listcell c5 = new Listcell();
				Decimalbox secondNum = new Decimalbox();
				secondNum.setParent(c5);
				secondNum.setId(wkt.getKdNumber() + "4");
				secondNum.setWidth("55%");
				
				//一档次人数
				Listcell c6 = new Listcell();
				Decimalbox firstNum = new Decimalbox();
				firstNum.setParent(c6);
				firstNum.setId(wkt.getKdNumber() + "5");
				firstNum.setWidth("55%");
				
				//基数
				Listcell c7 = new Listcell();
				Label perBasicNum = new Label();
				perBasicNum.setParent(c7);
				perBasicNum.setId(wkt.getKdNumber() + "6");
				
				Space space = new Space();
				space.setWidth("30%");
				space.setParent(c7);
				Button computePer = new Button();
				computePer.setId(wkt.getKdNumber() + "66");
				computePer.setLabel("计算");
				computePer.setParent(c7);
				computePer.addEventListener(Events.ON_CLICK, new EventListener() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						computeDeptNum(wkt.getKdNumber());
					}
				});
				//总数
				Listcell c8 = new Listcell();
				Label totalNum = new Label();
				totalNum.setParent(c8);
				totalNum.setId(wkt.getKdNumber() + "7");
				
				//人员详细
				Listcell c9 = new Listcell();
				final Toolbarbutton showDetail = new Toolbarbutton();
				showDetail.setParent(c9);
				showDetail.setLabel("查看");
				showDetail.setStyle("color:blue");
				showDetail.setId(wkt.getKdNumber() + "8");
			
				showDetail.addEventListener(Events.ON_CLICK, new EventListener() {
					
					@Override
					public void onEvent(Event arg0) throws Exception {
						ShowMemDetailWin win = (ShowMemDetailWin) Executions.createComponents(
								"/admin/jxkh/tools/bonusDistribute/ShowMemDetail.zul",
								null, null);
						String s = showDetail.getId().substring(0, wkt.getKdNumber().length());
						Label l = (Label) c1.getFellow(s);
						win.setDeptName(l.getValue());
						if(jxkh.size() == 0){
							initWindow();
						}
						for(jxkh_ComputDeptTools cList:jxkh){
							if(cList.getDeptName().equals(win.getDeptName())){
								Double numAll = cList.getPersonNum();
								Double UpMemNum = Double.valueOf(String.valueOf(Math.round(numAll*0.3) + 1));
								win.setUpMemNum(UpMemNum);
								win.setDeptId(cList.getDeptId());
								win.setTotalNum(cList.getTotalNum());
								String str = String.valueOf(cList.getBasicNum());
								win.setPerBasicAcount(str);
								win.setGrade(cList.getDeptGrade());//传递部门档次
							}
						}
						win.doHighlighted();
					}
				});
				
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
				arg0.appendChild(c5);
				arg0.appendChild(c6);
				arg0.appendChild(c7);
				arg0.appendChild(c8);
				arg0.appendChild(c9);
			}
	}

	/**
	 * 初始化grid
	 * @param list
	 */
	private void initRows(List<WkTDept> list) {
		int num = 0;
		num = (int) Math.ceil(list.size() / 2);
		for(int i=0;i<num;i++) {
			Row row = new Row();
			
			WkTDept d1 = list.get(2*i);
			Label label1 = new Label();
			label1.setValue(d1.getKdName() + "人数：");
			Decimalbox tb1 = new Decimalbox();
			tb1.setId(d1.getKdNumber() + "00");
			row.appendChild(label1);
			row.appendChild(tb1);
			
			WkTDept d2 = new WkTDept();
			if((2*i+1) <= list.size() - 1){
				d2 = list.get(2*i+1);
				
				Label label2 = new Label();
				label2.setValue(d2.getKdName() + "人数：");
				Decimalbox tb2 = new Decimalbox();
				tb2.setId(d2.getKdNumber() + "00");
				
				row.appendChild(label2);
				row.appendChild(tb2);
			}
			row.setParent(rows);
		}
	}

	/**
	 * 比较总人数是否和设置值相等:获取各档次人数，与总人数比较
	 * @param Num1
	 * @param Num2
	 * @param Num3
	 * @param Num4
	 * @param NumSet
	 * @throws InterruptedException
	 */
	public void compareEqual(Decimalbox Num1, Decimalbox Num2, Decimalbox Num3, Decimalbox Num4, 
			Label NumSet) 
			throws InterruptedException{
		double numAll = 0,numSet = 0;
		if((Num1.getValue() == null) || (Num2.getValue() == null) 
				|| (Num3.getValue() == null) || (Num4.getValue() == null) 
				|| (NumSet.getValue().length() < 1)){
			throw new WrongValueException(Num4, "该行人数不能为空！");
		}else{
			numAll = Num1.doubleValue() + Num2.doubleValue()
					+ Num3.doubleValue() + Num4.doubleValue();
			numSet = Double.valueOf(NumSet.getValue());
		}
		if(numAll != numSet){
			throw new WrongValueException(NumSet, "各档次人数加和与该部门总人数不相等");
		}
	}
	
	/**
	 * 设置setBox为tBox值；将输入的设置值传到列表中,否则抛出异常
	 * @param tBox
	 * @param setBox
	 */
	public void ThrowExceptionAndSet(Decimalbox tBox, Label setBox){
		if(tBox.getValue() == null){
			throw new WrongValueException(tBox, "部门人数不能为空！");
		}else{
			setBox.setValue(String.valueOf(tBox.doubleValue()));
		}
	}
	
	/**
	 * 设置各档次人数的值
	 * @param four
	 * @param three
	 * @param two
	 * @param one
	 * @param fourNum
	 * @param threeNum
	 * @param twoNum
	 * @param oneNum
	 */
	public void setGradeNum(Decimalbox four, Decimalbox three, Decimalbox two, Decimalbox one, 
			String fourNum, String threeNum, String twoNum, String oneNum){
		if(four.getValue() == null){
			four.setValue(BigDecimal.valueOf(Double.valueOf(fourNum)));
		}
		if(three.getValue() == null){
			three.setValue(BigDecimal.valueOf(Double.valueOf(threeNum)));
		}
		if(two.getValue() == null){
			two.setValue(BigDecimal.valueOf(Double.valueOf(twoNum)));
		}
		if(one.getValue() == null){
			one.setValue(BigDecimal.valueOf(Double.valueOf(oneNum)));
		}
	}
	
	/**
	 * 判断部门档次并计算三档次人数
	 * @param str
	 */
	public void matchDeptAndSetThree(String str){
		for(WkTDept d: computeList){
			Label deptName = (Label) computeListbox.getFellow(d.getKdNumber());
			Decimalbox personNum =  (Decimalbox) setDeptNum.getFellow(d.getKdNumber() + "00");
			Decimalbox forth =  (Decimalbox) computeListbox.getFellow(d.getKdNumber() + "2");
			Decimalbox third =  (Decimalbox) computeListbox.getFellow(d.getKdNumber() + "3");
			Decimalbox second =  (Decimalbox) computeListbox.getFellow(d.getKdNumber() + "4");
			Decimalbox first =  (Decimalbox) computeListbox.getFellow(d.getKdNumber() + "5");
			if(str.equals(deptName.getValue())){
				double numAll = personNum.doubleValue();
				//num4代表四档人数,以此类推
				double num4 = Math.round(numAll*0.3) + 1;
				double num3 = numAll - num4;
				setGradeNum(forth, third, second, first, 
						String.valueOf(num4), String.valueOf(num3), "0", "0");
			}
		}
		
	}
	
	/**
	 * 判断部门档次并计算二档次人数
	 * @param str
	 */
		public void matchDeptAndSetSecond(String str){
			for(WkTDept d: computeList){
				Label deptName = (Label) computeListbox.getFellow(d.getKdNumber());
				Decimalbox personNum = (Decimalbox) setDeptNum.getFellow(d.getKdNumber() + "00");
				Decimalbox forth = (Decimalbox) computeListbox.getFellow(d.getKdNumber() + "2");
				Decimalbox third = (Decimalbox) computeListbox.getFellow(d.getKdNumber() + "3");
				Decimalbox second = (Decimalbox) computeListbox.getFellow(d.getKdNumber() + "4");
				Decimalbox first = (Decimalbox) computeListbox.getFellow(d.getKdNumber() + "5");
				if(str.equals(deptName.getValue())){
					double numAll = personNum.doubleValue();
					//num4代表四档人数,以此类推
					double num3 = Math.round(numAll*0.3) + 1;
					double num2 = numAll - num3;
					setGradeNum(forth, third, second, first, 
							"0", String.valueOf(num3), String.valueOf(num2), "0");
				}
			}
			
		}
		
		/**
		 * 判断部门档次并计算一档次人数
		 * @param str
		 */
		public void matchDeptAndSetOne(String str){
			for(WkTDept d: computeList){
				Label deptName = (Label) computeListbox.getFellow(d.getKdNumber());
				Decimalbox personNum = (Decimalbox) setDeptNum.getFellow(d.getKdNumber() + "00");
				Decimalbox forth = (Decimalbox) computeListbox.getFellow(d.getKdNumber() + "2");
				Decimalbox third = (Decimalbox) computeListbox.getFellow(d.getKdNumber() + "3");
				Decimalbox second = (Decimalbox) computeListbox.getFellow(d.getKdNumber() + "4");
				Decimalbox first = (Decimalbox) computeListbox.getFellow(d.getKdNumber() + "5");
				if(str.equals(deptName.getValue())){
					double numAll = personNum.doubleValue();
					//num4代表四档人数,以此类推
					double num2 = Math.round(numAll*0.3) + 1;
					double num1 = numAll - num2;
					setGradeNum(forth, third, second, first, 
							"0", "0", String.valueOf(num2), String.valueOf(num1));
				}
			}
			
		}
		
	public void initWindow() throws InterruptedException{
		
		if(LeaderNum.getValue() == null){
			throw new WrongValueException(LeaderNum, "院领导人数不能为空!");
		}else if(DeptTotalAcount.getValue() == null){
			throw new WrongValueException(DeptTotalAcount, "业务部门总额不能为空!");
		}else{									
		/*设置各部门人数*/
		for(WkTDept d:computeList){
			Label PersonNum = (Label) computeListbox.getFellow(d.getKdNumber() + "1");
			Decimalbox personNum =  (Decimalbox) setDeptNum.getFellow(d.getKdNumber() + "00");
			ThrowExceptionAndSet(personNum, PersonNum);
		}
		
		//首先获取各部门档次,再设置各业务部门各档次人数
		for(WkTDept Dept3: DeptList3){
			String str = Dept3.getKdName();
			matchDeptAndSetThree(str);
		}
		for(WkTDept Dept2: DeptList2){
			String str = Dept2.getKdName();
			matchDeptAndSetSecond(str);
		}
		for(WkTDept Dept1: DeptList1){
			String str = Dept1.getKdName();
			matchDeptAndSetOne(str);
		}
		
		//保存各部门基数至list
		List<Double> list = new ArrayList<Double>();
		if(jxkh.size() != 0){
			jxkh.clear();
		}
	    
		
		for(WkTDept d: computeList){
			Label deptName = (Label) computeListbox.getFellow(d.getKdNumber());
			Label PersonNum = (Label) computeListbox.getFellow(d.getKdNumber() + "1");
			Decimalbox forth = (Decimalbox) computeListbox.getFellow(d.getKdNumber() + "2");
			Decimalbox third = (Decimalbox) computeListbox.getFellow(d.getKdNumber() + "3");
			Decimalbox second = (Decimalbox) computeListbox.getFellow(d.getKdNumber() + "4");
			Decimalbox first = (Decimalbox) computeListbox.getFellow(d.getKdNumber() + "5");
			Label perBasicNum = (Label) computeListbox.getFellow(d.getKdNumber() + "6");
			
			compareEqual(forth, third, second, first, PersonNum);//判断每一行人数之和与总人数是否相等
			perBasicNum.setValue(String.valueOf(computeBasicAcount(
					String.valueOf(forth.doubleValue()), 
					String.valueOf(third.doubleValue()),
					String.valueOf(second.doubleValue()),
					String.valueOf(first.doubleValue()))));			
			//判断各业务部门基数是否为空（有问题不过暂时还可以用但是得修改）
			if(perBasicNum.getValue() != null){
				list.add(Double.valueOf(perBasicNum.getValue()));
			}
			jxkh_ComputDeptTools jxkhCompute = new jxkh_ComputDeptTools();//定义实体
			jxkhCompute.setDeptName(deptName.getValue());
			jxkhCompute.setDeptId(d.getKdId());
			jxkhCompute.setPersonNum(Double.valueOf(PersonNum.getValue()));
			jxkhCompute.setAcountForth(forth.doubleValue());
			jxkhCompute.setAcountThird(third.doubleValue());
			jxkhCompute.setAcountSecond(second.doubleValue());
			jxkhCompute.setAcountFirst(first.doubleValue());
			//set年度
			String g1=yearlist.getSelectYear();
			jxkhCompute.setYear(g1);
			jxkh.add(jxkhCompute);
		}
		for(WkTDept d: computeList){
			Label deptName = (Label) computeListbox.getFellow(d.getKdNumber());
			Label perBasicNum = (Label) computeListbox.getFellow(d.getKdNumber() + "6");
			Label totalNum = (Label) computeListbox.getFellow(d.getKdNumber() + "7");
			double leaderNum = LeaderNum.doubleValue();
		
			totalNum.setValue(String.valueOf(
					computeTotalAcount(DeptTotalAcount.doubleValue(),
							Double.valueOf(perBasicNum.getValue()), list, leaderNum)));
			for(jxkh_ComputDeptTools comList:jxkh){
				if(comList.getDeptName().equals(deptName.getValue())){
					comList.setTotalNum(df(Double.valueOf(totalNum.getValue())));
					comList.setBasicNum(df(Double.valueOf(getPerBasicAcount().getValue())));
					comList.setPerBasicAcount(df(Double.valueOf(perBasicAcount.getValue())));
				}
			}
		}
		
		//set各部门档次
		for(jxkh_ComputDeptTools comList:jxkh){
			for(WkTDept Dept3: DeptList3){
				String str = Dept3.getKdName();
				if(comList.getDeptName().equals(str)){
					comList.setDeptGrade(jxkh_ComputDeptTools.GRADE_THIRD);
				}
			}
			
			for(WkTDept Dept2: DeptList2){
				String str = Dept2.getKdName();
				if(comList.getDeptName().equals(str)){
					comList.setDeptGrade(jxkh_ComputDeptTools.GRADE_SECOND);
				}
			}
			
			for(WkTDept Dept1: DeptList1){
				String str = Dept1.getKdName();
				if(comList.getDeptName().equals(str)){
					comList.setDeptGrade(jxkh_ComputDeptTools.GRADE_FIRST);
					auditResultService.save(comList);
				}
			}
		}
		
	  }
	}
	
	/**
	 * 计算某业务部门基数
	 * @param forth
	 * @param third
	 * @param second
	 * @param first
	 * @return
	 * @throws InterruptedException
	 */
	public double computeBasicAcount(String forth, String third, String second, String first) 
			throws InterruptedException{
		//基数
		double basciAc = 0;
		if(forth.equals("") || third.equals("") || second.equals("") || first.equals("") ){
			Messagebox.show("人数不能为空！", "提示", Messagebox.OK,
					Messagebox.INFORMATION);
		}else{
			double Forth1 = Double.valueOf(forth);
			double Third1 = Double.valueOf(third);
			double Second1 = Double.valueOf(second);
			double First1 = Double.valueOf(first);
			//计算基数并设置
			basciAc =  Forth1*2 + Third1*1.6 + Second1*1.3 + First1*1;
		}
		basciAc = subNum(basciAc);
		return basciAc;
	}
	
	/**
	 * 计算某业务部门绩效总额
	 * @param DeptTotalNum
	 * @param basicAc
	 * @param basicAc1
	 * @param basicAc2
	 * @param basicAc3
	 * @param basicAc4
	 * @param basicAc5
	 * @param basicAc6
	 * @param basicAc7
	 * @param leaderNum
	 * @return
	 */
	public double computeTotalAcount(double DeptTotalNum, double basicAc, List<Double> list, double leaderNum){
		
		double TotalbasicAc = 0;
		for(int i=0; i<list.size();i++){
			
			TotalbasicAc = TotalbasicAc + list.get(i);
		}
		/*double TotalbasicAc = basicAc1 + basicAc2 + basicAc3
				+ basicAc4 + basicAc5 + basicAc6 + basicAc7;*/
		
		//leaderNum为院领导数,DeptTotalNum为业务部门总额
		double perbasicAc = (DeptTotalNum/(TotalbasicAc + leaderNum*1.6/2));
		perbasicAc = subNum(perbasicAc);
		perBasicAcount.setValue(String.valueOf(perbasicAc));
		
		double deptPerbasicAc = basicAc*perbasicAc;
		deptPerbasicAc = subNum(deptPerbasicAc);
		return deptPerbasicAc;
	}
	
	//计算并更新业务部门、管理部门、院长总额
	public void onClick$saveInfo(){
		//按照百分比计算各部门奖励性绩效工资额度
		if(DeptNum.getValue() == null){
			throw new WrongValueException(DeptNum, "业务部门人数不能为空！");
		}else if(ManageNum.getValue() == null){
			throw new WrongValueException(ManageNum, "管理部门人数不能为空！");
		}else if(LeaderNum.getValue() == null){
			throw new WrongValueException(LeaderNum, "院领导人数不能为空！");
		}else if(totalAcount.getValue() == null){
			throw new WrongValueException(totalAcount, "绩效工资总额不能为空！");
		}
		else{
			double addAllNum = DeptNum.doubleValue() + ManageNum.doubleValue()+ LeaderNum.doubleValue();
			//总额度
			double TotalAcount = totalAcount.doubleValue();
			//业务部门+院领导数/2
			double addDeptNum = DeptNum.doubleValue() + LeaderNum.doubleValue()/2;
			//设置业务部门总额度
			DeptTotalAcount.setValue(BigDecimal.valueOf(TotalAcount*addDeptNum/addAllNum));
			double addManageNum = ManageNum.doubleValue() + LeaderNum.doubleValue()/2;//管理人员总数+院领导数/2
			double managerTotalNum = ((TotalAcount*addManageNum/addAllNum));//管理人员总工资数
			manageTotalAcount.setValue(String.valueOf(df(managerTotalNum)));
		}
		//。。。
		//更新院长总额
		//。。。
	}

	public void onClick$compute() throws InterruptedException{
		/*computeListbox.setModel(new ListModelList(computeList));
		computeListbox.setItemRenderer(new listRenderer());*/
		initWindow();
	}
	/************************************************************************************************************/
	public Double perBasicAcountLd(double totalmoney,double yxpeo,double hgpeo){//院领导一个基数额度
		double perBasicAcountLd;
		perBasicAcountLd=(totalmoney)/(hgpeo+yxpeo*1.3);
		return perBasicAcountLd;
	}
	public void onClick$computeLd()throws InterruptedException{//计算院领导绩效工资
		//院领导总额度
		if("".equals(perBasicAcount.getValue())){//业务部门基数额度不为空
			Messagebox.show("业务部门基数额度还未计算！");
			return;
		}
		else if("".equals(perBasicAcountGl.getValue())){//管理部门基数额度不为空
			Messagebox.show("管理部门部门基数额度还未计算！");
			return;
		}else{
		float leaderTotalMoney1=(float) (Float.parseFloat(perBasicAcountGl.getValue())*1.6* ((LeaderNum.getValue().doubleValue())/2));//管理部门基数额度*1.6*院领导人数/2
		float leaderTotalMoney2=(float) (Float.parseFloat(perBasicAcount.getValue())*1.6* ((LeaderNum.getValue().doubleValue())/2));//业务部门基数额度*1.6*院领导人数/2
	    float leaderTotalMoney=leaderTotalMoney1+leaderTotalMoney2;
	    leaderTotalAcount.setValue(String.valueOf(df(leaderTotalMoney)));//院领导总工资数
		if(yxGradePeo.getValue()==null){
			throw new WrongValueException(yxGradePeo,"优秀档人数不能为空！");
		}
		if(hgGradePeo.getValue()==null){
			throw new WrongValueException(hgGradePeo,"合格档人数不能为空！");
		}
	 }
		double a=perBasicAcountLd(Double.parseDouble(leaderTotalAcount.getValue()),yxGradePeo.getValue().doubleValue(),hgGradePeo.getValue().doubleValue());
		perBasicAcountLd.setValue( String.valueOf(df(a)));//一个基数额度
		resultLeader.clear();
		leaderPeoList=auditConfigService.findLeaderList();//查找院领导
		for(int i=0;i<leaderPeoList2.size();i++){//计算四档人员的工资
			WkTUser four=new WkTUser();
			four.setStaffId(leaderPeoList2.get(i).getStaffId());
			four.setKuName(leaderPeoList2.get(i).getKuName());
			four.setKuCompany(leaderPeoList2.get(i).getDept().getKdName());
			four.setKuAnswer("优秀");
			four.setKuAutoshow(String.valueOf(df(a*1.3)));
			resultLeader.add(four);
		}
		for(int i=0;i<leaderPeoList1.size();i++){//计算三档人员的工资
			WkTUser four=new WkTUser();
			four.setStaffId(leaderPeoList1.get(i).getStaffId());
			four.setKuName(leaderPeoList1.get(i).getKuName());
			four.setKuCompany(leaderPeoList1.get(i).getDept().getKdName());
			four.setKuAnswer("合格");
			four.setKuAutoshow(String.valueOf(df(a)));
			resultLeader.add(four);
		 }
		reSultLdListbox.setModel(new ListModelList(resultLeader));
     }
	public void onClick$chooseLeaderPeoYx() throws SuspendNotAllowedException,InterruptedException{
		if(yxGradePeo.getValue()==null){
			throw new WrongValueException(yxGradePeo,"优秀档次人员不能为空！");
		}else{
			if(leaderPeoList2.size()!=0){
				leaderPeoList2.clear();
			}
			else if(leaderPeoList.size()==0||oldleaderPeoList.size()==0){
				leaderPeoList=auditConfigService.findLeaderList();
				oldleaderPeoList.addAll(leaderPeoList);
			}
			else{
				leaderPeoList.clear();
				leaderPeoList.addAll(oldleaderPeoList);
			}
			final chooseLeaderWindow leader = (chooseLeaderWindow) Executions.createComponents(
					"/admin/jxkh/tools/bonusDistribute/chooseLeaPeo.zul", null, null);
			if(leaderPeoList1.size()!=0){
				leaderPeoList.removeAll(leaderPeoList1);
			}
			leader.setNumber(Integer.parseInt(yxGradePeo.getValue().toString()));
			leader.setManagerList(leaderPeoList);
			leader.initWindow();
			leader.addEventListener(Events.ON_CHANGE, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					leaderPeoList2.addAll(leader.getManagerList());//managerPeoList4用来保存四档人员
					leaderPeoList = leader.getManagerList();
					String depts = "";
					for (WkTUser dept : leaderPeoList) {
						depts += dept.getKuName() + ",";
					}
					if (depts.endsWith(",")) {
						depts = depts.substring(0, depts.length() - 1);
					}
					yxTxt.setValue(depts);
					leader.detach();
				}
			});
			leader.doModal();
		}
	}
	public void onClick$chooseLeaderPeohg() throws SuspendNotAllowedException,InterruptedException{
		if(hgGradePeo.getValue()==null){
			throw new WrongValueException(hgGradePeo,"合格档次人员不能为空！");
		}else{
		if(leaderPeoList1.size()!=0){
			leaderPeoList1.clear();
		}
		else if(leaderPeoList.size()==0||oldleaderPeoList.size()==0){
			leaderPeoList=auditConfigService.findLeaderList();
			oldleaderPeoList.addAll(leaderPeoList);
		}
		else{
			leaderPeoList.clear();
			leaderPeoList.addAll(oldleaderPeoList);
		}
		final chooseLeaderWindow leader = (chooseLeaderWindow) Executions.createComponents(
				"/admin/jxkh/tools/bonusDistribute/chooseLeaPeo.zul", null, null);
		if(leaderPeoList2.size()!=0){
			leaderPeoList.removeAll(leaderPeoList2);
		}
		leader.setNumber(Integer.parseInt(hgGradePeo.getValue().toString()));
		leader.setManagerList(leaderPeoList);
		leader.initWindow();
		leader.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				leaderPeoList1.addAll(leader.getManagerList());
				leaderPeoList = leader.getManagerList();
				String depts = "";
				for (WkTUser dept : leaderPeoList) {
					depts += dept.getKuName() + ",";
				}
				if (depts.endsWith(",")) {
					depts = depts.substring(0, depts.length() - 1);
				}
				hgTxt.setValue(depts);
				leader.detach();
			}
		});
		leader.doModal();
		}
	}
	public  class reSultLdRenderer implements ListitemRenderer{//院领导结果渲染器
		@Override
		public void render(Listitem item, Object data) throws Exception {
			if(data==null) return;
			final WkTUser person=(WkTUser)data;
			item.setValue(data);
			Listcell c0=new Listcell(item.getIndex()+1+"");
			Listcell c1=new Listcell(person.getStaffId());//员工ID
			Listcell c2=new Listcell(person.getKuName());//员工姓名
			Listcell c3=new Listcell(person.getKuCompany());//员工部门
			Listcell c4=new Listcell(person.getKuAnswer());//员工档次
			Listcell c5=new Listcell(person.getKuAutoshow());//员工工资数
			 
			item.appendChild(c0);
			item.appendChild(c1);
			item.appendChild(c2);
			item.appendChild(c3);
			item.appendChild(c4);
			item.appendChild(c5);
		}
		
	}
	
	/****************************************************************/
	public class resultglRenderer implements ListitemRenderer{//管理人员结果渲染器
		@Override
		public void render(Listitem item, Object data) throws Exception {
			if(data==null) return;
			final WkTUser person=(WkTUser)data;
			item.setValue(data);
			Listcell c0=new Listcell(item.getIndex()+1+"");
			Listcell c1=new Listcell(person.getStaffId());//员工ID
			Listcell c2=new Listcell(person.getKuName());//员工姓名
			Listcell c3=new Listcell(person.getKuCompany());//员工部门
			Listcell c4=new Listcell(person.getKuAnswer());//员工档次
			Listcell c5=new Listcell(person.getKuAutoshow());//员工工资数
			 
			item.appendChild(c0);
			item.appendChild(c1);
			item.appendChild(c2);
			item.appendChild(c3);
			item.appendChild(c4);
			item.appendChild(c5);
		}
		
	}
	public Double perBasicAcountGl(double one,double two,double three,double four,double leader ,double totalaccount){//计算管理部门人员一个基数额度
		double  perbasicacountgl;
		perbasicacountgl=totalaccount/(four*2+(three+leader/2)*1.6+two*1.3+one);
		return perbasicacountgl;
	}
	public double df (double p){//截取小数点后两位
		double a1;
		DecimalFormat df = new DecimalFormat("#.##");   
		a1=Double.parseDouble(df.format(p)); 
		return a1;
	}
	public void onClick$computeGl()throws InterruptedException{
		if(fourGradePeo.getValue()==null){
			throw new WrongValueException(fourGradePeo, "四档人数不能为空！");
		}
		if(threeGradePeo.getValue()==null){
			throw new WrongValueException(threeGradePeo, "三档人数不能为空！");
		}
		if(twoGradePeo.getValue()==null){
			throw new WrongValueException(twoGradePeo, "二档人数不能为空！");
		}
		if(oneGradePeo.getValue()==null){
			throw new WrongValueException(oneGradePeo, "一档人数不能为空！");
		}
		else{
		resultgl.clear();
		double  perbasicacountgl=perBasicAcountGl(oneGradePeo.getValue().doubleValue(), twoGradePeo.getValue().doubleValue(), threeGradePeo.getValue().doubleValue(), fourGradePeo.getValue().doubleValue(),LeaderNum.doubleValue(),Double.valueOf( manageTotalAcount.getValue()));
		perBasicAcountGl.setValue( String.valueOf(df(perbasicacountgl)));
		
		manageDeptList=auditResultService.findManageDept();	//查找管理部门
		String sb="";
		if(manageDeptList.size()!=0){
			for(int i=0;i<manageDeptList.size();i++){
				WkTDept dep=(WkTDept)manageDeptList.get(i);
				sb+=dep.getKdId()+",";	
			}
		}
		if(sb.endsWith(",")){
		sb = sb.substring(0, sb.length() - 1);}
		managerPeoList=auditResultService.findUserByDept(String.valueOf(sb));//查找管理部门人员
		for(int i=0;i<managerPeoList4.size();i++){//计算四档人员的工资
			WkTUser four=new WkTUser();
			four.setStaffId(managerPeoList4.get(i).getStaffId());
			four.setKuName(managerPeoList4.get(i).getKuName());
			four.setKuCompany(managerPeoList4.get(i).getDept().getKdName());
			four.setKuAnswer("四档");
			four.setKuAutoshow(String.valueOf(df(perbasicacountgl*2)));
			resultgl.add(four);
		}
		for(int i=0;i<managerPeoList3.size();i++){//计算三档人员的工资
			WkTUser four=new WkTUser();
			four.setStaffId(managerPeoList3.get(i).getStaffId());
			four.setKuName(managerPeoList3.get(i).getKuName());
			four.setKuCompany(managerPeoList3.get(i).getDept().getKdName());
			four.setKuAnswer("三档");
			four.setKuAutoshow(String.valueOf(df(perbasicacountgl*1.6)));
			resultgl.add(four);
		 }
		for(int i=0;i<managerPeoList2.size();i++){//计算二档人员的工资
			WkTUser four=new WkTUser();
			four.setStaffId(managerPeoList2.get(i).getStaffId());
			four.setKuName(managerPeoList2.get(i).getKuName());
			four.setKuCompany(managerPeoList2.get(i).getDept().getKdName());
			four.setKuAnswer("二档");
			four.setKuAutoshow(String.valueOf(df(perbasicacountgl*1.3)));
			resultgl.add(four);
		}
		
		for(int i=0;i<managerPeoList1.size();i++){//计算一档人员的工资
			WkTUser four=new WkTUser();
			four.setStaffId(managerPeoList1.get(i).getStaffId());
			four.setKuName(managerPeoList1.get(i).getKuName());
			four.setKuCompany(managerPeoList1.get(i).getDept().getKdName());
			four.setKuAnswer("一档");
			four.setKuAutoshow(String.valueOf(df(perbasicacountgl*1)));
			resultgl.add(four);
		}
		reSultGlListbox.setModel(new ListModelList(resultgl));
		}
	}
	public void onClick$chooseManagerPeoFour()throws SuspendNotAllowedException,InterruptedException{
		if(fourGradePeo.getValue()==null){
			throw new WrongValueException(fourGradePeo, "四档人数不能为空！");
		}
		
		else{
	    if(managerPeoList4.size()!=0){
        managerPeoList4.clear();
        }
		if(managerPeoList.size()==0||oldmanagerPeoList.size()==0){
		    manageDeptList=auditResultService.findManageDept();	//查找管理部门
			String sb="";
			if(manageDeptList.size()!=0){
				for(int i=0;i<manageDeptList.size();i++){
					WkTDept dep=(WkTDept)manageDeptList.get(i);
					sb+=dep.getKdId()+",";	
				}
			}
			if(sb.endsWith(",")){
			sb = sb.substring(0, sb.length() - 1);}
			managerPeoList=auditResultService.findUserByDept(String.valueOf(sb));//查找管理部门人员
			oldmanagerPeoList.addAll(managerPeoList);
		}
		else{
			managerPeoList.clear();
			managerPeoList.addAll(oldmanagerPeoList);
		}
		final chooseMagePeoWindow manager = (chooseMagePeoWindow) Executions.createComponents(
				"/admin/jxkh/tools/bonusDistribute/chooseMagePeo.zul", null, null);
		if(managerPeoList3.size()!=0){
			managerPeoList.removeAll(managerPeoList3);
		}
		if(managerPeoList2.size()!=0){
			managerPeoList.removeAll(managerPeoList2);
		}
		if(managerPeoList1.size()!=0){
			managerPeoList.removeAll(managerPeoList1);
		}
		manager.setNumber(Integer.parseInt(fourGradePeo.getValue().toString()));
		manager.setManagerList(managerPeoList);
		manager.initWindow();
		manager.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				managerPeoList4.addAll(manager.getManagerList());//managerPeoList4用来保存四档人员
				managerPeoList = manager.getManagerList();
				String depts = "";
				for (WkTUser dept : managerPeoList) {
					depts += dept.getKuName() + ",";
				}
				if (depts.endsWith(",")) {
					depts = depts.substring(0, depts.length() - 1);
				}
				fourTxt.setValue(depts);
				manager.detach();
			}
		});
		manager.doModal();
		}
	}
	public void onClick$chooseManagerPeoThree()throws SuspendNotAllowedException,InterruptedException{
		if(threeGradePeo.getValue()==null){
			throw new WrongValueException(threeGradePeo, "三档人数不能为空！");
		}else{
		if(managerPeoList3.size()!=0){
			managerPeoList3.clear();
		}
		if(managerPeoList.size()==0||oldmanagerPeoList.size()==0){
		    manageDeptList=auditResultService.findManageDept();	//查找管理部门
			String sb="";
			if(manageDeptList.size()!=0){
				for(int i=0;i<manageDeptList.size();i++){
					WkTDept dep=(WkTDept)manageDeptList.get(i);
					sb+=dep.getKdId()+",";	
				}
			}
			if(sb.endsWith(",")){
			sb = sb.substring(0, sb.length() - 1);}
			managerPeoList=auditResultService.findUserByDept(String.valueOf(sb));//查找管理部门人员
			oldmanagerPeoList.addAll(managerPeoList);
		
		}
		else{
			managerPeoList.clear();
			managerPeoList.addAll(oldmanagerPeoList);
		}
		final chooseMagePeoWindow manager = (chooseMagePeoWindow) Executions.createComponents(
				"/admin/jxkh/tools/bonusDistribute/chooseMagePeo.zul", null, null);
		if(managerPeoList4.size()!=0){
			managerPeoList.removeAll(managerPeoList4);
		}
		if(managerPeoList2.size()!=0){
			managerPeoList.removeAll(managerPeoList2);
		}
		if(managerPeoList1.size()!=0){
			managerPeoList.removeAll(managerPeoList1);
		}
		manager.setNumber(Integer.parseInt(threeGradePeo.getValue().toString()));
		manager.setManagerList(managerPeoList);
		manager.initWindow();
		manager.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				managerPeoList3.addAll(manager.getManagerList());
				managerPeoList = manager.getManagerList();
				String depts = "";
				for (WkTUser dept : managerPeoList) {
					depts += dept.getKuName() + ",";
				}
				if (depts.endsWith(",")) {
					depts = depts.substring(0, depts.length() - 1);
				}
				threeTxt.setValue(depts);
				manager.detach();
			}
		});
		manager.doModal();
		}
	}
	public void onClick$chooseManagerPeoTwo()throws SuspendNotAllowedException,InterruptedException{
		if(twoGradePeo.getValue()==null){
			throw new WrongValueException(twoGradePeo, "二档人数不能为空！");
		}else{
		if(managerPeoList2.size()!=0){
			managerPeoList2.clear();
		}
		if(managerPeoList.size()==0||oldmanagerPeoList.size()==0){
		    manageDeptList=auditResultService.findManageDept();	//查找管理部门
			String sb="";
			if(manageDeptList.size()!=0){
				for(int i=0;i<manageDeptList.size();i++){
					WkTDept dep=(WkTDept)manageDeptList.get(i);
					sb+=dep.getKdId()+",";	
				}
			}
			if(sb.endsWith(",")){
			sb = sb.substring(0, sb.length() - 1);}
			managerPeoList=auditResultService.findUserByDept(String.valueOf(sb));//查找管理部门人员
			oldmanagerPeoList.addAll(managerPeoList);
		
		}
		else{
			managerPeoList.clear();
			managerPeoList.addAll(oldmanagerPeoList);
		}
		final chooseMagePeoWindow manager = (chooseMagePeoWindow) Executions.createComponents(
				"/admin/jxkh/tools/bonusDistribute/chooseMagePeo.zul", null, null);
		if(managerPeoList4.size()!=0){
			managerPeoList.removeAll(managerPeoList4);
		}
		if(managerPeoList3.size()!=0){
			managerPeoList.removeAll(managerPeoList3);
		}
		if(managerPeoList1.size()!=0){
			managerPeoList.removeAll(managerPeoList1);
		}
		manager.setNumber(Integer.parseInt(twoGradePeo.getValue().toString()));
		manager.setManagerList(managerPeoList);
		manager.initWindow();
		manager.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				managerPeoList2.addAll(manager.getManagerList());
				managerPeoList = manager.getManagerList();
				String depts = "";
				for (WkTUser dept : managerPeoList) {
					depts += dept.getKuName() + ",";
				}
				if (depts.endsWith(",")) {
					depts = depts.substring(0, depts.length() - 1);
				}
				twoTxt.setValue(depts);
				manager.detach();
			}
		});
		manager.doModal();
		}
	}
	public void onClick$chooseManagerPeoOne()throws SuspendNotAllowedException,InterruptedException{
		if(oneGradePeo.getValue()==null){
			throw new WrongValueException(oneGradePeo, "一档人数不能为空！");
		}else{
		if(managerPeoList1.size()!=0){
			managerPeoList1.clear();
		}
		if(managerPeoList.size()==0||oldmanagerPeoList.size()==0){
		    manageDeptList=auditResultService.findManageDept();	//查找管理部门
			String sb="";
			if(manageDeptList.size()!=0){
				for(int i=0;i<manageDeptList.size();i++){
					WkTDept dep=(WkTDept)manageDeptList.get(i);
					sb+=dep.getKdId()+",";	
				}
			}
			if(sb.endsWith(",")){
			sb = sb.substring(0, sb.length() - 1);}
			managerPeoList=auditResultService.findUserByDept(String.valueOf(sb));//查找管理部门人员
			oldmanagerPeoList.addAll(managerPeoList);
		
		}
		else{
			managerPeoList.clear();
			managerPeoList.addAll(oldmanagerPeoList);
		}
		final chooseMagePeoWindow manager = (chooseMagePeoWindow) Executions.createComponents(
				"/admin/jxkh/tools/bonusDistribute/chooseMagePeo.zul", null, null);
		if(managerPeoList4.size()!=0){
			managerPeoList.removeAll(managerPeoList4);
		}
		if(managerPeoList3.size()!=0){
			managerPeoList.removeAll(managerPeoList3);
		}
		if(managerPeoList2.size()!=0){
			managerPeoList.removeAll(managerPeoList2);
		}
		manager.setNumber(Integer.parseInt(oneGradePeo.getValue().toString()));
		manager.setManagerList(managerPeoList);
		manager.initWindow();
		manager.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				managerPeoList1.addAll(manager.getManagerList());
				managerPeoList = manager.getManagerList();
				String depts = "";
				for (WkTUser dept : managerPeoList) {
					depts += dept.getKuName() + ",";
				}
				if (depts.endsWith(",")) {
					depts = depts.substring(0, depts.length() - 1);
				}
				oneTxt.setValue(depts);
				manager.detach();
			}
		});
		manager.doModal();
		}
	}
	
	/**********************************************************************************************************************/
	public void onClick$chooseDept3() throws SuspendNotAllowedException, InterruptedException{
		if(deptList.size() == 0 || OldDeptList.size() == 0){
			deptList = auditResultService.findWorkDept();
			OldDeptList.addAll(deptList);
		}else{
			deptList.clear();
			deptList.addAll(OldDeptList);
		}
		
		final chooseDeptWindow cDept = (chooseDeptWindow) Executions.createComponents(
				"/admin/jxkh/tools/bonusDistribute/chooseDept.zul", null, null);
		if(DeptList2.size() != 0){
			deptList.removeAll(DeptList2);
		}
		if(DeptList1.size() != 0){
			deptList.removeAll(DeptList1);
		}
		cDept.setDeptList(deptList);
		cDept.initWindow();
		cDept.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				DeptList3.addAll(cDept.getDeptList());
				deptList = cDept.getDeptList();
				String depts = "";
				for (WkTDept dept : deptList) {
					depts += dept.getKdName() + ",";
				}
				if (depts.endsWith(",")) {
					depts = depts.substring(0, depts.length() - 1);
				}
				oprateDept3.setValue(depts);
				cDept.detach();
			}
		});
		cDept.doModal();
	}

	public void onClick$chooseDept2() throws SuspendNotAllowedException, InterruptedException{
		if(deptList.size() == 0 || OldDeptList.size() == 0){
			deptList = auditResultService.findWorkDept();
			OldDeptList.addAll(deptList);
		}else{
			deptList.clear();
			deptList.addAll(OldDeptList);
		}
		final chooseDeptWindow cDept = (chooseDeptWindow) Executions.createComponents(
				"/admin/jxkh/tools/bonusDistribute/chooseDept.zul", null, null);
		if(DeptList3.size() != 0){
			deptList.removeAll(DeptList3);
		}
		if(DeptList1.size() != 0){
			deptList.removeAll(DeptList1);
		}
		cDept.setDeptList(deptList);
		cDept.initWindow();
		cDept.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				DeptList2.addAll(cDept.getDeptList());
				deptList = cDept.getDeptList();
				String depts = "";
				for (WkTDept dept : deptList) {
					depts += dept.getKdName() + ",";
				}
				if (depts.endsWith(",")) {
					depts = depts.substring(0, depts.length() - 1);
				}
				oprateDept2.setValue(depts);
				cDept.detach();
			}
		});
		cDept.doModal();
	}

	public void onClick$chooseDept1() throws SuspendNotAllowedException, InterruptedException{
		if(deptList.size() == 0 || OldDeptList.size() == 0){
			deptList = auditResultService.findWorkDept();
			OldDeptList.addAll(deptList);
		}else{
			deptList.clear();
			deptList.addAll(OldDeptList);
		}
		final chooseDeptWindow cDept = (chooseDeptWindow) Executions.createComponents(
				"/admin/jxkh/tools/bonusDistribute/chooseDept.zul", null, null);
		if(DeptList2.size() != 0){
			deptList.removeAll(DeptList2);
		}
		if(DeptList3.size() != 0){
			deptList.removeAll(DeptList3);
		}
		cDept.setDeptList(deptList);
		cDept.initWindow();
		cDept.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				DeptList1.addAll(cDept.getDeptList());
				deptList = cDept.getDeptList();
				String depts = "";
				for (WkTDept dept : deptList) {
					depts += dept.getKdName() + ",";
				}
				if (depts.endsWith(",")) {
					depts = depts.substring(0, depts.length() - 1);
				}
				oprateDept1.setValue(depts);
				cDept.detach();
			}
		});
		cDept.doModal();
	}
	
	/**
	 * 计算某部门的基数
	 * @param str
	 * @throws InterruptedException
	 */
	public void computeDeptNum(String str) throws InterruptedException{//有问题
		
		Label PersonNum = (Label) computeListbox.getFellow(str + "1");
		Decimalbox forth = (Decimalbox) computeListbox.getFellow(str + "2");
		Decimalbox third = (Decimalbox) computeListbox.getFellow(str + "3");
		Decimalbox second = (Decimalbox) computeListbox.getFellow(str + "4");
		Decimalbox first = (Decimalbox) computeListbox.getFellow(str + "5");
		Label perBasicNum = (Label) computeListbox.getFellow(str + "6");
		if("".equals(forth.getValue())){
			throw new WrongValueException(forth,"此处不能为空！");
		}else if("".equals(third.getValue())){
			throw new WrongValueException(third,"此处不能为空！");
		}
		else if("".equals(second.getValue())){
			throw new WrongValueException(second,"此处不能为空！");
		}
		else if("".equals(first.getValue())){
			throw new WrongValueException(first,"此处不能为空！");
		}
		else{
		compareEqual(forth, third, second, first, PersonNum);//比较人数是否相等
		perBasicNum.setValue(String.valueOf(computeBasicAcount(String.valueOf(forth.doubleValue()), String.valueOf(third.doubleValue()),String.valueOf(second.doubleValue()),String.valueOf(first.doubleValue()))));
	}
	}
	public Label getPerBasicAcount() {
		return perBasicAcount;
	}	
	/**
	 * 截取小数点后两位
	 * @param p
	 * @return
	 */
	public double subNum (double p){
		double a1;
		DecimalFormat df = new DecimalFormat("#.##");   
		a1=Double.parseDouble(df.format(p)); 
		return a1;
	}
	
	public void onClick$exportDept() throws WriteException, IOException{
		//deptDistribute...
		Date now=new Date ();
		String fileName = ConvertUtil.convertDateString(now)+"业务部门奖励性绩效工资分配方案"+".xls";
		String Title = "业务部门奖励性绩效工资基数及总额";
		List<String> titlelist = new ArrayList<String>();
		titlelist.add("部门");
		titlelist.add("档次");
		titlelist.add("四档人数");
		titlelist.add("三档人数");
		titlelist.add("二档人数");
		titlelist.add("一档人数"); 
		titlelist.add("基数");
		titlelist.add("总额");
		
		String c[][]=new String[jxkh.size()][titlelist.size()];
		for(int j = 0; j < jxkh.size(); j ++){
			jxkh_ComputDeptTools result = (jxkh_ComputDeptTools)jxkh.get(j);
			c[j][0] = result.getDeptName();
			c[j][1] = String.valueOf(result.getDeptGrade());
			c[j][2] = String.valueOf( result.getAcountForth());
			c[j][3] = String.valueOf(result.getAcountThird());
			c[j][4] = String.valueOf(result.getAcountSecond());
			c[j][5] = String.valueOf(result.getAcountFirst());
			c[j][6] = String.valueOf(result.getBasicNum());
			c[j][7] = String.valueOf(result.getTotalNum());
		}
		ExportExcel ee=new ExportExcel();
		ee.exportExcel(fileName, Title, titlelist, jxkh.size(), c);		
	}
	
	public void onClick$exportManage() throws WriteException, IOException{
		//manageDistribute...
		Date now=new Date ();
		String fileName = ConvertUtil.convertDateString(now)+"管理部门奖励性绩效工资分配方案"+".xls";
		String Title = "管理部门奖励性绩效工资基数及总额";
		List<String> titlelist = new ArrayList<String>();
		titlelist.add("姓名");
		titlelist.add("四档");
		titlelist.add("三档");
		titlelist.add("二档");
		titlelist.add("一档"); 
		titlelist.add("基数");
		titlelist.add("金额");
		
		String c[][]=new String[resultgl.size()][titlelist.size()];
		for(int j = 0; j < resultgl.size(); j ++){
			WkTUser result = (WkTUser)resultgl.get(j);
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
		ee.exportExcel(fileName, Title, titlelist, resultgl.size(), c);					
	}
	
	public void onClick$exportLeader() throws WriteException, IOException{
		//leaderDistribute...
		Date now=new Date ();
		String fileName = ConvertUtil.convertDateString(now)+"院领导奖励性绩效工资分配方案"+".xls";
		String Title = "院领导奖励性绩效工资基数及总额";
		List<String> titlelist = new ArrayList<String>();
		titlelist.add("姓名");
		titlelist.add("优秀档");
		titlelist.add("合格档");
		titlelist.add("基数");
		titlelist.add("金额");
		
		String c[][]=new String[resultLeader.size()][titlelist.size()];
		for(int j = 0; j < resultLeader.size(); j ++){
			WkTUser result = (WkTUser)resultLeader.get(j);
			c[j][0] = result.getKuName();
			if(result.getKuAnswer().equals("优秀")){
				c[j][1] = "1";
				c[j][3] = "1.3";
			}else if(result.getKuAnswer().equals("合格")){
				c[j][2] = "1";
				c[j][3] = "1";
			}
			c[j][4] = result.getKuAutoshow();
		}
		ExportExcel ee=new ExportExcel();
		ee.exportExcel(fileName, Title, titlelist, resultLeader.size(), c);				
	}
	
	/*public void onClick$export() throws WriteException, IOException{
		//totalDistribute------error！
		Date now=new Date ();
		String fileName = ConvertUtil.convertDateString(now)+"奖励性绩效工资分配方案"+".xls";
		String Title = "绩效工资总额分配";
		List<String>titlelist = new ArrayList<String>();
		titlelist.add("奖励性绩效工资总量：");
		titlelist.add("业务部门奖励性绩效工资总额（含院长部分额度）：");
		titlelist.add("业务部门1个基数奖励性绩效工资额度：");
		titlelist.add("管理岗位奖励性绩效工资总额（含院长部分额度）：");
		titlelist.add("管理部门1个基数奖励性绩效工资额度：");
		titlelist.add("院长奖励性绩效工资总额:"); 
		titlelist.add("院长1个基数奖励性绩效工资额度：");
	}*/
}
