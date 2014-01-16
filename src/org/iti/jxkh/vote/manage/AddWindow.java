package org.iti.jxkh.vote.manage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.iti.jxkh.entity.JXKH_VoteConfig;
import org.iti.jxkh.entity.JXKH_VoteResult;
import org.iti.jxkh.service.AuditResultService;
import org.iti.jxkh.service.VoteConfigService;
import org.iti.jxkh.service.VoteResultService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;


import com.iti.common.util.ConvertUtil;
import com.uniwin.framework.entity.WkTUser;

public class AddWindow extends Window implements AfterCompose {
	private static final long serialVersionUID = -2619948856660042952L;
	private Textbox object, voter;//投票对象、投票者
	private String userId = "";
	private Datebox startTime, endTime;//开始时间、结束时间
	private Textbox name, first, second, third;//投票名称，档次
	private VoteConfigService voteConfigService;
	private VoteResultService voteResultService;
	private List<WkTUser> voterlist = new ArrayList<WkTUser>();
	private List<WkTUser> voterlist1 = new ArrayList<WkTUser>();
	
	private AuditResultService auditResultService;

	JXKH_VoteConfig config;
	
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		first.addEventListener(Events.ON_BLUR, new EventListener() {//一档
			public void onEvent(Event arg0) throws Exception {				
				int firstNum = (Integer.valueOf(first.getValue())).intValue();
				second.setValue((Integer.valueOf(100-firstNum)).toString());
			}
		});
		second.addEventListener(Events.ON_BLUR, new EventListener() {//二档
			public void onEvent(Event arg0) throws Exception {				
				int firstNum = (Integer.valueOf(first.getValue())).intValue();
				int secondNum = (Integer.valueOf(second.getValue())).intValue();
				third.setValue((Integer.valueOf(100-firstNum-secondNum)).toString());
			}
		});
	}
	
	public void initWindow(JXKH_VoteConfig config) {
		if(config != null) {
			this.config = config;
			String value = config.getVcWeight();
			String[] valuelist = value.split("-");
			first.setValue(valuelist[0]);
			second.setValue(valuelist[1]);
			third.setValue(valuelist[2]);
			name.setValue(config.getVcName());
			startTime.setValue(ConvertUtil.convertDate(config.getVcStart()));
			endTime.setValue(ConvertUtil.convertDate(config.getVcEnd()));
			initVote();
			initVoter();			
		}
	}
	
	private void initVoter() {
		String kuList = config.getVcVoter();
		String voterName="";
		String[] kuidList = kuList.split("-");
		for(int i=1;i<kuidList.length;i++) {
			if(kuidList[i] != null || !"".equals(kuidList[i])) {
				WkTUser user = (WkTUser) auditResultService.loadById(WkTUser.class, Long.valueOf(kuidList[i]));
				voterName += user.getKuName()+",";
				userId = "-";				
				userId += user.getKuId() + "-";
			}
		}
		voter.setValue(voterName);
	}

	private void initVote(){
		String voteName="";
		List<JXKH_VoteResult> rlist = new ArrayList<JXKH_VoteResult>();
		List<JXKH_VoteConfig> vlist = voteConfigService.findConfig(config.getVcId());
		if(vlist.size() != 0) {
			JXKH_VoteConfig result = vlist.get(0);
			rlist = result.getVcObject();		
		}		
		if(rlist.size() != 0) {
			this.voterlist.clear();
			for(int i=0;i<rlist.size();i++) {
				JXKH_VoteResult jv = rlist.get(i);
				voteName += jv.getUser().getKuName()+",";
				this.voterlist.add(jv.getUser());
			}
			object.setValue(voteName);
		}
	}

	public void onClick$objectSelect() {
		final AddMemberWindow win = (AddMemberWindow) Executions.createComponents("/admin/jxkh/vote/manage/selectMember.zul",
				null, null);
		win.setTitle("选择投票对象");
		short type = 0;
		List<WkTUser> userList = new ArrayList<WkTUser>();
		if(config == null) {
			win.initWindow(voterlist,type);			
		}else {
			List<JXKH_VoteConfig> vlist = voteConfigService.findConfig(config.getVcId());
			if(vlist.size() != 0) {
				JXKH_VoteConfig result = vlist.get(0);
				List<JXKH_VoteResult> rlist = result.getVcObject();	
				if(rlist.size() != 0) {
					for(int j=0;j<rlist.size();j++) {
						JXKH_VoteResult r = rlist.get(j);
						WkTUser user = (WkTUser)voteConfigService.loadById(WkTUser.class, r.getKuId());
						userList.add(user);
					}
				}
			}	
			win.initWindow(userList,type);
		}
		
		win.doHighlighted();
		win.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				voterlist.clear();
				voterlist = win.getVoteList();
				String userNames = "";
				for (WkTUser user : win.getUlist()) {
					userNames += user.getKuName() + ",";
				}
				if (userNames.length() != 0) {
					userNames = userNames.substring(0, userNames.length() - 1);
				}
				object.setDisabled(false);
				object.setValue(userNames);
				object.setDisabled(true);
				win.detach();
			}
		});
	}

	public void onClick$voterSelect() {
		userId = "";
		final AddMemberWindow win = (AddMemberWindow) Executions.createComponents("/admin/jxkh/vote/manage/selectMember.zul",
				null, null);
		win.setTitle("选择投票者");
		short type = 1;
		List<WkTUser> userList = new ArrayList<WkTUser>();
		if(config == null) {
			win.initWindow1(voterlist1,type);
		}else {
			String kuList = config.getVcVoter();
			String[] kuidList = kuList.split("-");
			for(int i=1;i<kuidList.length;i++) {
				if(kuidList[i] != null || !"".equals(kuidList[i])) {
					WkTUser user = (WkTUser) auditResultService.loadById(WkTUser.class, Long.valueOf(kuidList[i]));
					userList.add(user);
				}
			}
			win.initWindow(userList,type);
		}		
		win.doHighlighted();
		win.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				voterlist1 =  win.getVoterList();
				String userNames = "";
				userId = "-";
				for (WkTUser user : win.getVoterList()) {
					userNames += user.getKuName() + ",";
					userId += user.getKuId() + "-";
				}
				if (userNames.length() != 0) {
					userNames = userNames.substring(0, userNames.length() - 1);
				}
				voter.setDisabled(false);
				voter.setValue(userNames);
				voter.setDisabled(true);
				win.detach();
			}
		});
	}

	public void onClick$submit() {	
		if("".equals(name.getValue())){
			throw new WrongValueException(name, "投票名称不能为空，请填写！");
		}else if("".equals(startTime.getValue())){
			throw new WrongValueException(startTime, "投票开始时间不能为空，请选择！");
		}else if("".equals(name.getValue())){
			throw new WrongValueException(endTime, "投票结束时间不能为空，请选择！");
		}else if("".equals(object.getValue())){
			throw new WrongValueException(object, "投票对象不能为空，请选择！");
		}else if("".equals(voter.getValue())){
			throw new WrongValueException(voter, "投票者不能为空，请选择！");
		}
		else{
		if(config == null) {
			int index = 0;
			JXKH_VoteConfig config = new JXKH_VoteConfig();
			config.setVcName(name.getValue());
			String start = com.iti.common.util.TimeConvert.format_5.format(startTime.getValue());
			config.setVcStart(start);
			String end = com.iti.common.util.TimeConvert.format_5.format(endTime.getValue());
			config.setVcEnd(end);
			config.setVcYear(Calendar.getInstance().get(Calendar.YEAR) + "");
			config.setVcVoter(userId);
			config.setVcWeight(first.getValue().trim() + "-" + second.getValue().trim() + "-" + third.getValue().trim());
			voteConfigService.save(config);
			for (WkTUser user : voterlist) {
				JXKH_VoteResult res = new JXKH_VoteResult();
				res.setKuId(user.getKuId());
				res.setVrNumber(0);
				res.setVrYear(config.getVcYear());
				res.setConfig(config);
				res.setVrRecord("");
				List<JXKH_VoteResult> list = voteResultService.findByYearAndKuId(config.getVcYear(), user.getKuId());
				if(list.size() ==0) {
					voteConfigService.save(res);
				}					
				else {
					index++;
				}				
			}
			if(index != 0) {
				try {
					Messagebox.show("同一年度投票中不允许重复选被投票人员，系统已自动排除！");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}else {
			int index=0;
			config.setVcName(name.getValue());
			String start = com.iti.common.util.TimeConvert.format_5.format(startTime.getValue());
			config.setVcStart(start);
			String end = com.iti.common.util.TimeConvert.format_5.format(
					endTime.getValue());
			config.setVcEnd(end);
			config.setVcYear(Calendar.getInstance().get(Calendar.YEAR) + "");
			config.setVcVoter(userId);
			config.setVcWeight(first.getValue().trim() + "-" + second.getValue().trim() + "-" + third.getValue().trim());
			voteConfigService.update(config);
			List<JXKH_VoteConfig> vlist = voteConfigService.findConfig(config.getVcId());
			if(vlist.size() != 0) {
				JXKH_VoteConfig result = vlist.get(0);
				List<JXKH_VoteResult> rlist = result.getVcObject();	
				if(rlist.size() != 0) {
					for(int j=0;j<rlist.size();j++) {
						JXKH_VoteResult r = rlist.get(j);
						voteConfigService.delete(r);
					}
				}
			}	
			for (WkTUser user : voterlist) {				
				JXKH_VoteResult res = new JXKH_VoteResult();
				res.setKuId(user.getKuId());
				res.setVrNumber(0);
				res.setVrYear(config.getVcYear());
				res.setConfig(config);
				res.setVrRecord("");
				List<JXKH_VoteResult> list = voteResultService.findByYearAndKuId(config.getVcYear(), user.getKuId());
				if(list.size() ==0) {
					voteConfigService.save(res);
				}					
				else {
					index++;
				}				
			}
			
			if(index != 0) {
				try {
					Messagebox.show("同一年度投票中不允许重复选被投票人员，系统已自动排除！");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		Events.postEvent(Events.ON_CHANGE, this, null);
	}
	}
		
	
	public void onClick$third() {
		third.setRawValue(100-Long.valueOf(first.getValue())-Long.valueOf(second.getValue()));
	}

	public void onClick$close() {
		this.detach();
	}
}
