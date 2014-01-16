package org.iti.jxkh.busiAudit.award;

import java.util.ArrayList;
import java.util.List;

import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxkh.entity.Jxkh_Award;
import org.iti.jxkh.entity.Jxkh_AwardDept;
import org.iti.jxkh.entity.Jxkh_AwardMember;
import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.service.JxkhAwardService;
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
	 * @author ZhangYuguang
	 */
	private static final long serialVersionUID = 6321953986958497460L;
	private Textbox name,awardMember,awardDept,coCompany,authorizeCompany,registerCode,record;
	private Listbox rank;
	private Row outDeptRow;
	private Radio cooperationTrue, cooperationFalse,firstSignTrue,firstSignFalse;
	private Datebox date;
	private Button print;
	private YearListbox jiFenTime;
	private Label recordlabel,submitName;
	private Hbox recordhbox;
	private Jxkh_Award award;
	private JxkhAwardService jxkhAwardService;
	private List<Jxkh_AwardMember> awardMemberList = new ArrayList<Jxkh_AwardMember>();
	private List<Jxkh_AwardDept> awardDeptList = new ArrayList<Jxkh_AwardDept>();
	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		rank.setItemRenderer(new awardRankRenderer());
		jiFenTime.initYearListbox("");
	}
	public void initWindow() {
		String memberName="";
		String deptName="";
		print.setHref("/print.action?n=award&id="+award.getId());
		jiFenTime.initYearListbox(award.getjxYear());
		name.setValue(award.getName());
		if(award.getFirstSign() == 1){
			firstSignTrue.setChecked(true);
			firstSignFalse.setChecked(false);
		}else{
			firstSignTrue.setChecked(false);
			firstSignFalse.setChecked(true);
		}
		if(award.getCoState()==1){
			cooperationTrue.setChecked(true);
			cooperationFalse.setChecked(false);
			outDeptRow.setVisible(true);
			coCompany.setValue(award.getCoCompany());
		}else{
			cooperationTrue.setChecked(false);
			cooperationFalse.setChecked(true);
		}
		registerCode.setValue(award.getRegisterCode());
		if(award.getState()==6){
			record.setVisible(true);
			recordlabel.setVisible(true);
			recordhbox.setVisible(true);
			record.setValue(award.getRecordCode());
		}
		date.setValue(ConvertUtil.convertDate(award.getDate()));
		authorizeCompany.setValue(award.getAuthorizeCompany());
		submitName.setValue(award.getSubmitName());
		awardMemberList=award.getAwardMember();
		for(int i=0;i<awardMemberList.size();i++)
		{
			Jxkh_AwardMember mem=(Jxkh_AwardMember) awardMemberList.get(i);
			memberName+=mem.getName()+"、";		
		}
		awardMember.setValue(memberName.substring(0, memberName.length()-1));
		awardDeptList=jxkhAwardService.findAwardDeptByAwardId(award);
		for(int i=0;i<awardDeptList.size();i++)
		{
			Jxkh_AwardDept dept=(Jxkh_AwardDept) awardDeptList.get(i);
			deptName+=dept.getName()+"、";	
		}
		awardDept.setValue(deptName.substring(0,deptName.length()-1));
		initListbox();
	}

	private void initListbox() {
		List <Jxkh_BusinessIndicator> rankList=new ArrayList<Jxkh_BusinessIndicator>();
		rankList.add(new Jxkh_BusinessIndicator());
		rankList.addAll(jxkhAwardService.findAllBusinessIndicator());
		rank.setModel(new ListModelList(rankList));
		rank.setSelectedIndex(0);
	}

	/** 奖励级别列表渲染器 */
	public class awardRankRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {
			if(data==null) return;
			Jxkh_BusinessIndicator type = (Jxkh_BusinessIndicator)data;
			item.setValue(type);
			Listcell c0 = new Listcell();
			if(type.getKbName()==null){
				c0.setLabel("--请选择--");
			}else{
				c0.setLabel(type.getKbName());
			}
			item.appendChild(c0);
			if(item.getIndex()==0)
				item.setSelected(true);
			if(award!=null&&type.equals(award.getRank())){
				item.setSelected(true);
			}
		}
	}
	
	public Jxkh_Award getAward() {
		return award;
	}
	public void setAward(Jxkh_Award award) {
		this.award = award;
	}
	/**
	 * <li>功能描述：关闭当前窗口。
	 */
	public void onClick$close(){
		this.onClose();
	}
}
