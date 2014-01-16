package org.iti.jxkh.score.setup;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxkh.entity.JXKH_AuditConfig;
import org.iti.jxkh.service.AuditConfigService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;

import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.service.DepartmentService;

public class SetupWindow extends BaseWindow {
	private static final long serialVersionUID = -8709901246443015596L;
	private JXKH_AuditConfig ac;
	private YearListbox yearlist;//年份列表
	private Label showRate1, showRate2;
	private Decimalbox rate, money;
	private Intbox weight1, weight2, weight3, weight4;
	private Intbox third, second, first;
	private double workNum, manageNum, leadNum, factor;
	private AuditConfigService auditConfigService;
	private DepartmentService departmentService;
//	private Button save;

	public void initShow() {
		yearlist.initYearListbox("");
		workNum = (double) auditConfigService.findWorker();
		manageNum = (double) auditConfigService.findManager();
		leadNum = (double) auditConfigService.findLeader();
		factor = (workNum + leadNum / 2) / (workNum + manageNum + leadNum);
		third.addEventListener(Events.ON_BLUR, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				List<WkTDept> deptlist = departmentService.getChildrenbyPtidForWork(1L);
				int thirdNum = third.getValue().intValue();
				second.setValue(Integer.valueOf(deptlist.size() - thirdNum));
			}
		});
		second.addEventListener(Events.ON_BLUR, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				List<WkTDept> deptlist = departmentService.getChildrenbyPtidForWork(1L);
				int thirdNum = third.getValue().intValue();
				int secondNum = second.getValue().intValue();
				first.setValue(Integer.valueOf(deptlist.size() - thirdNum - secondNum));
			}
		});
	}

	public void computeRate() {
		DecimalFormat df = new DecimalFormat("########.## ");
		showRate1.setValue(df.format(factor * money.getValue().doubleValue()));
		showRate2.setValue(df.format((1 - factor) * money.getValue().doubleValue()));
	}

	public void refresh() {
		if (ac != null) {
			rate.setValue(BigDecimal.valueOf((int) (ac.getAcBFloat() * 100)));
			String[] strArray = ac.getAcMWeight().split("-");
			weight1.setValue(Integer.parseInt(strArray[0]));
			weight2.setValue(Integer.parseInt(strArray[1]));
			weight3.setValue(Integer.parseInt(strArray[2]));
			weight4.setValue(Integer.parseInt(strArray[3]));
			money.setValue(BigDecimal.valueOf(ac.getAcWage()));
			third.setValue(ac.getThird());
			second.setValue(ac.getSecond());
			first.setValue(ac.getFirst());
			computeRate();
		} else {
			rate.setValue(BigDecimal.valueOf(30));
			weight1.setValue(3);
			weight2.setValue(3);
			weight3.setValue(2);
			weight4.setValue(1);
			third.setValue(1);
			second.setValue(2);
			List<WkTDept> deptlist = departmentService.getChildrenbyPtidForWork(1L);
			first.setValue(deptlist.size() - 1 - 2);
			money.setValue(BigDecimal.valueOf(0));
			showRate1.setValue("0");
			showRate2.setValue("0");
		}
	}

	public void initWindow() {
	}

	public void onChange$money() {
		computeRate();
	}

	public void onSelect$yearlist() {
		String year =(String) yearlist.getSelectedItem().getValue();
		ac = auditConfigService.findByYear(year);
		/**
		 * 考核参数设定，只能设定当前年的，之前已设定好的不能修改
		 */
		/*if(!year.equals(new Year()+""))   save.setDisabled(true);
		else  save.setDisabled(false);*/
		refresh();
	}

	public void onClick$save() {
		if (yearlist.getSelectedIndex() == 0||yearlist.getSelectedItem()==null) {
			try {
				Messagebox.show("请选择年份！");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		if (ac == null) {
			ac = new JXKH_AuditConfig();
		}
		String year =(String) yearlist.getSelectedItem().getValue();
		ac.setAcYear(year);
		ac.setAcBFloat(rate.getValue().floatValue() / 100f);
		ac.setAcMWeight(weight1.getValue().toString().trim() + "-" + weight2.getValue().toString().trim() + "-" + weight3.getValue().toString().trim() + "-" + weight4.getValue().toString().trim());
		ac.setAcWage(money.getValue().floatValue());
		ac.setAcMoney(Float.parseFloat(showRate1.getValue()));
		ac.setAcMoney2(Float.parseFloat(showRate2.getValue()));
		ac.setThird(third.getValue());
		ac.setSecond(second.getValue());
		ac.setFirst(first.getValue());
		auditConfigService.saveOrUpdate(ac);
		try {
			Messagebox.show("保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
