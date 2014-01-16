package org.iti.jxkh.statistics;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.iti.gh.common.util.ExportExcel;
import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxgl.entity.JxYear;
import org.iti.jxkh.entity.JXKH_AuditConfig;
import org.iti.jxkh.entity.JXKH_AuditResult;
import org.iti.jxkh.entity.JXKH_HYLW;
import org.iti.jxkh.entity.JXKH_MEETING;
import org.iti.jxkh.entity.JXKH_QKLW;
import org.iti.jxkh.entity.Jxkh_Award;
import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.entity.Jxkh_Fruit;
import org.iti.jxkh.entity.Jxkh_Patent;
import org.iti.jxkh.entity.Jxkh_Project;
import org.iti.jxkh.entity.Jxkh_ProjectFund;
import org.iti.jxkh.entity.Jxkh_Video;
import org.iti.jxkh.entity.Jxkh_Writing;
import org.iti.jxkh.service.AuditConfigService;
import org.iti.jxkh.service.AuditResultService;
import org.iti.jxkh.service.BusinessIndicatorService;
import org.iti.jxkh.service.JxkhAwardService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;

import com.iti.common.util.PropertiesLoader;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;

public class AllWindow extends BaseWindow {
	private static final long serialVersionUID = -2803419586964832849L;
	private Row listrow;
	private YearListbox yearlist;
	private Listbox listbox;//, yearlist;
	private AuditConfigService auditConfigService;
	private AuditResultService auditResultService;
	private BusinessIndicatorService businessIndicatorService;
	private String year;
	private Listbox dept;
	private JxkhAwardService jxkhAwardService;
	WkTDept deptment;
	private List<JXKH_AuditResult> exportlist = new ArrayList<JXKH_AuditResult>();

	public void initShow() {
		
		yearlist.initYearListbox("");
		
		dept.setItemRenderer(new ListitemRenderer() {
			@Override
			public void render(Listitem arg0, Object arg1) throws Exception {
				WkTDept dept = (WkTDept) arg1;
				arg0.setValue(dept);
				Listcell c0 = new Listcell();
				if (dept.getKdName() == null) {
					c0 = new Listcell("--请选择--");
				} else {
					c0 = new Listcell(dept.getKdName());
				}
				arg0.appendChild(c0);
			}

		});
		List<WkTDept> deptlist = new ArrayList<WkTDept>();
		WkTDept dept1 = new WkTDept();
		deptlist.add(dept1);
		List<WkTDept> dlist = jxkhAwardService.findDept1();
		deptlist.addAll(dlist);
		dept.setModel(new ListModelList(deptlist));
		dept.setSelectedIndex(0);
		deptment = (WkTDept) dept.getSelectedItem().getValue();
		System.out.println(deptment);

		
		listbox.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				JXKH_AuditResult res = (JXKH_AuditResult) arg1;
				Listcell c0 = new Listcell(arg0.getIndex() + 1 + "");
				WkTDept dept = auditResultService.findDepname(res.getKdId());
				Listcell c1 = new Listcell(dept.getKdName());
				Listcell c2 = new Listcell(res.getArYear());
				WkTUser user = auditResultService.findUser(res.getKuId());
				// JXKH_AuditResult res =
				// auditResultService.findWorkDeptByKdIdAndYear(dept.getKdId(),
				// year + "");
				Listcell c3 = new Listcell(user == null ? "" : user.getKuName());
				Listcell c4 = new Listcell(user == null ? "" : user.getKuLid());
				Listcell c6 = new Listcell(res.getArLevel() == null ? "" : res.getArLevel() + "");
				Listcell c7 = new Listcell(res.getArMoney() == null ? "" : res.getArMoney() + "");
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
				arg0.appendChild(c6);
				arg0.appendChild(c7);
			}
		});
	}

	public void initWindow() {
	}

	public void onClick$compute() {
		/*JxYear jy = (JxYear)yearlist.getSelectedItem().getValue();
		String year = jy.getYears();*/
		String year = yearlist.getSelectYear();
		exportlist.clear();
		WkTDept deptment = (WkTDept) dept.getSelectedItem().getValue();
		System.out.println(deptment);
		List<JXKH_AuditResult> ar = auditResultService.findAll(year, deptment.getKdId());
		exportlist.addAll(ar);
		if (ar != null) {
			listbox.setModel(new ListModelList(ar));
			listrow.setVisible(true);
		}else {
			try {
				Messagebox.show("无当年考核结果！");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void onClick$export() throws WriteException, IOException {
		/*JxYear jy = (JxYear)yearlist.getSelectedItem().getValue();
		year = jy.getYears();*/
		year = yearlist.getSelectYear();
		List<JXKH_AuditResult> list3 = new ArrayList<JXKH_AuditResult>();
		list3.clear();
		list3.addAll(exportlist);
		if(list3 == null || list3.size() == 0){
			 try {
	 				Messagebox.show("空数据，导出错误！", "错误", Messagebox.OK,Messagebox.INFORMATION);
	 			} catch (InterruptedException e) {
	 				e.printStackTrace();
	 			}
	 			  return;
		}else {
			String state = "";
			String fileName = year+"部门绩效考核结果.xls";
			String Title = year+"部门绩效考核结果";
			WritableWorkbook workbook;
			List titlelist=new ArrayList();
			titlelist.add("序号");
			titlelist.add("所属部门");
			titlelist.add("年度");				
			titlelist.add("姓名");//例如：120/篇
			titlelist.add("人员编号");
			titlelist.add("所在档次");
			titlelist.add("绩效工资（元）");
			String c[][]=new String[list3.size()][titlelist.size()];
			//从SQL中读数据
			for(int j=0;j<list3.size();j++){
				JXKH_AuditResult res = list3.get(j);	
				WkTDept dept = auditResultService.findDepname(res.getKdId());
			    c[j][0]=j+1+"";
				c[j][1]=dept.getKdName();
				int num = auditConfigService.findDeptMember(dept.getKdId());
			    c[j][2]=res.getArYear();
			    WkTUser user = auditResultService.findUser(res.getKuId());
				c[j][3]=user == null ? "" : user.getKuName();
				c[j][4]=user == null ? "" : user.getKuLid();								
				c[j][5]=res.getArLevel() + "";
				c[j][6]=res.getArMoney() + "";
			}
	         ExportExcel ee=new ExportExcel();
			ee.exportExcel(fileName, Title, titlelist, list3.size(), c);
			
		}
	}
}
