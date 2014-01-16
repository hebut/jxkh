package org.iti.jxkh.busiAudit.report;

import java.util.ArrayList;
import java.util.List;

import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.entity.Jxkh_Report;
import org.iti.jxkh.entity.Jxkh_ReportDept;
import org.iti.jxkh.entity.Jxkh_ReportMember;
import org.iti.jxkh.service.JxkhAwardService;
import org.iti.jxkh.service.JxkhReportService;
import org.zkoss.zk.ui.Components;
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
import org.zkoss.zul.Radio;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.iti.common.util.ConvertUtil;

public class DetailWin extends Window implements AfterCompose{

	/**
	 * @author ZhangyuGuang
	 */
	private static final long serialVersionUID = 6265602003382888819L;
	private Textbox name,reportMember,reportDept,coCompany,filed,record;
	private Listbox type,leader;
	private Row outDeptRow,recordlabel;
	private Button print;
	private Radio cooperationTrue,cooperationFalse;
	private Datebox date;
	private Label submitName;
	private YearListbox jiFenTime;
	private Hbox recordhbox;
	private Jxkh_Report report;
	private List<Jxkh_ReportMember> reportMemberList = new ArrayList<Jxkh_ReportMember>();
	private List<Jxkh_ReportDept> reportDeptList = new ArrayList<Jxkh_ReportDept>();
	private JxkhReportService jxkhReportService;
	private JxkhAwardService jxkhAwardService;
	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		leader.setItemRenderer(new reportLeaderRenderer());
		type.setItemRenderer(new reportTypeRenderer());
		jiFenTime.initYearListbox("");
	}

	//报告领导渲染器
		public class reportLeaderRenderer implements ListitemRenderer {

			@Override
			public void render(Listitem item, Object data) throws Exception {
				if(data==null) return;
				Jxkh_BusinessIndicator leader = (Jxkh_BusinessIndicator)data;
				item.setValue(leader);
				Listcell c0 = new Listcell();
				if(leader.getKbName()==null){
					c0.setLabel("--请选择--");
				}else{
					c0.setLabel(leader.getKbName());
				}
				item.appendChild(c0);
				if(item.getIndex()==0)
					item.setSelected(true);
				if(report!=null&&leader.equals(report.getLeader())){
					item.setSelected(true);
				}
			}
		}
		//报告种类渲染器
		public class reportTypeRenderer implements ListitemRenderer {
			@Override
			public void render(Listitem item, Object data) throws Exception {
				String report_Type = (String)data;
				item.setValue(report_Type);
				Listcell c0 = new Listcell();
				if(report_Type==null||report_Type.equals(""))
					c0.setLabel("--请选择--");
				else{
					c0.setLabel(report_Type);				
				}
				item.appendChild(c0);
				if(item.getIndex()==0)
					item.setSelected(true);
				if(report!=null&&report_Type.equals(report.getType())){
					item.setSelected(true);
				}
			}
		}
		public void initWindow(){
			String memberName="";
			String deptName="";
			print.setHref("/print.action?n=report&id="+report.getId());
			jiFenTime.initYearListbox(report.getjxYear());
				name.setValue(report.getName());
				if(report.getCoState()==1){
					cooperationTrue.setChecked(true);
					cooperationFalse.setChecked(false);
					outDeptRow.setVisible(true);
					coCompany.setValue(report.getCoCompany());
				}else{
					cooperationTrue.setChecked(false);
					cooperationFalse.setChecked(true);
				}
				if(report.getState()==6){
					record.setVisible(true);
					recordlabel.setVisible(true);
					recordhbox.setVisible(true);
					record.setValue(report.getRecordCode());
				}
				date.setValue(ConvertUtil.convertDate(report.getDate()));
				filed.setValue(report.getFiled());
				submitName.setValue(report.getSubmitName());
				reportMemberList=report.getReportMember();
				for(int i=0;i<reportMemberList.size();i++)
				{
					Jxkh_ReportMember mem=(Jxkh_ReportMember) reportMemberList.get(i);
					memberName+=mem.getName()+"、";		
				}
				reportMember.setValue(memberName.substring(0, memberName.length()-1));
				
				reportDeptList=report.getReprotDept();
				for(int i=0;i<reportDeptList.size();i++)
				{
					Jxkh_ReportDept dept=(Jxkh_ReportDept) reportDeptList.get(i);
					deptName+=dept.getName()+"、";	
				}
				reportDept.setValue(deptName.substring(0,deptName.length()-1));
				initListbox();
		}
		private void initListbox() {
			List <Jxkh_BusinessIndicator> rankList=new ArrayList<Jxkh_BusinessIndicator>();
			rankList.add(new Jxkh_BusinessIndicator());
			rankList.addAll(jxkhAwardService.findAllBusinessIndicator());
			leader.setModel(new ListModelList(rankList));
			leader.setSelectedIndex(0);
			
			String[] report_Types={"","调研报告","分析报告"};
			List<String> reportTpyeList= new ArrayList<String>();
			for(int i = 0; i < 3; i++)
			{
				reportTpyeList.add(report_Types[i]);
			}
			type.setModel(new ListModelList(reportTpyeList));
		}
		
		public Jxkh_Report getReport() {
			return report;
		}
		public void setReport(Jxkh_Report report) {
			this.report = report;
		}
		/**
		 * <li>功能描述：关闭当前窗口。
		 */
		public void onClick$close(){
			this.onClose();
		}
}
