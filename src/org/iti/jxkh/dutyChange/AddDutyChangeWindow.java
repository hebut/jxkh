package org.iti.jxkh.dutyChange;

import java.util.List;

import org.iti.jxkh.entity.JXKH_AuditResult;
import org.iti.jxkh.entity.JXKH_HULWMember;
import org.iti.jxkh.entity.JXKH_MeetingMember;
import org.iti.jxkh.entity.JXKH_MultiDept;
import org.iti.jxkh.entity.JXKH_PerTrans;
import org.iti.jxkh.entity.JXKH_QKLWMember;
import org.iti.jxkh.entity.Jxkh_AwardMember;
import org.iti.jxkh.entity.Jxkh_FruitMember;
import org.iti.jxkh.entity.Jxkh_PatentInventor;
import org.iti.jxkh.entity.Jxkh_ProjectMember;
import org.iti.jxkh.entity.Jxkh_ReportMember;
import org.iti.jxkh.entity.Jxkh_VideoMember;
import org.iti.jxkh.entity.Jxkh_Writer;
import org.iti.jxkh.service.AuditResultService;
import org.iti.jxkh.service.DutyChangeService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.iti.common.util.ConvertUtil;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;

public class AddDutyChangeWindow extends Window implements AfterCompose {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Textbox name,oldPost,newDepart,newPost,content;//oldDepart;
	private Datebox changetime;
//	private Toolbarbutton nameChoose,newChoose;//oldChoose;
	public Toolbarbutton save,reset,close;
	private Label olddept;
	
	private JXKH_PerTrans jp;
	private short deptType;//0��ʾ�²��ţ�1��ʾ�ɲ���
	
	
	private DutyChangeService dutyChangeService;
	private AuditResultService auditResultService;
	private WkTUser user;
	private WkTDept dept;
	private WkTDept newDept,oldDept;
	

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
	}
	/**
	 * ҳ���ʼ��
	 * @param jp
	 */
	public void initWindow(JXKH_PerTrans jp,WkTDept dept) {
		this.jp = jp;
		this.dept = dept;
		if(jp == null) {
			olddept.setValue(dept.getKdName());
		}else {
			reloadWindow();
		}		
	}
	/**
	 * ҳ��ļ���
	 */
	public void reloadWindow() {	
		newDept = jp.getNewDept();
		dept = jp.getOldDept();
		olddept.setValue(jp.getOldDept().getKdName());
		name.setValue(jp.getUser().getKuName());
//		oldDepart.setValue(jp.getOldDept().getKdName());
		oldPost.setValue(jp.getOldPost());
		newDepart.setValue(jp.getNewDept().getKdName());
		newPost.setValue(jp.getNewPost());
		content.setValue(jp.getReason());	
		changetime.setValue(ConvertUtil.convertDate(jp.getPtDate()));
	}
	/**
	 * ����ҳ��
	 */
	public void resetWindow() {
		name.setValue("");
//		oldDepart.setValue("");
		oldPost.setValue("");
		newDepart.setValue("");
		newPost.setValue("");
		content.setValue("");	
		changetime.setValue(null);
	}
	/**
	 * ����
	 */
	public void onClick$save() {
		if(name.getValue() == null || "".equals(name.getValue())) {
			try {
				Messagebox.show("��Ա���Ʋ���Ϊ�գ�������ѡ��", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		if(oldPost.getValue() == null || "".equals(oldPost.getValue())) {
			try {
				Messagebox.show("ԭְλ����Ϊ�գ���������д��", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		if(newDepart.getValue() == null || "".equals(newDepart.getValue())) {
			try {
				Messagebox.show("�²��Ų���Ϊ�գ�������ѡ��", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		if(newPost.getValue() == null || "".equals(newPost.getValue())) {
			try {
				Messagebox.show("��ְλ����Ϊ�գ���������д��", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		if(changetime.getValue() == null) {
			try {
				Messagebox.show("����ʱ�䲻��Ϊ�գ���������д��", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		if(jp == null) {
			/**
			 * �����һ����Ա������Ϣ
			 */
			JXKH_PerTrans jpt = new JXKH_PerTrans();			
			jpt.setKuId(user.getKuId());
			jpt.setNewKdId(newDept.getKdId());
			jpt.setNewPost(newPost.getValue().trim());
			jpt.setOldKdId(dept.getKdId());
			jpt.setOldPost(oldPost.getValue().trim());
			jpt.setPtDate(ConvertUtil.convertDateString(changetime.getValue()));
			jpt.setReason(content.getValue());			
			dutyChangeService.save(jpt);
			
			// ��һ��ͽ���Ļ��ֱ䶯			 
			changeScore();
			
			user.setKdId(newDept.getKdId());
			dutyChangeService.update(user);
			try {
				Messagebox.show("��Ա������Ϣ����ɹ���", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Events.postEvent(Events.ON_CHANGE, this, null);	
			this.detach();
		}else {
			
			jp.setKuId(user.getKuId());
			jp.setNewKdId(newDept.getKdId());
			jp.setNewPost(newPost.getValue().trim());
			jp.setOldPost(oldPost.getValue().trim());
			jp.setPtDate(ConvertUtil.convertDateString(changetime.getValue()));
			jp.setReason(content.getValue());
			dutyChangeService.update(jp); 
			
			// ��һ��ͽ���Ļ��ֱ䶯			 
			changeScore();
			
			user.setKdId(newDept.getKdId());
			dutyChangeService.update(user);
			/**
			 * ����Ա�Ļ��ִӾɲ��Ŵ����²���
			 */
			try {
				Messagebox.show("��Ա������Ϣ����ɹ���", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Events.postEvent(Events.ON_CHANGE, this, null);	
			this.detach();
		}
	}
	/**
	 * ��һ��ͽ���Ļ��ֱ䶯
	 */
	private void changeScore() {
		float lastYearScore = 0,nowYearScore = 0, 
				hylwScore = 0,qklwScore = 0,writingScore = 0,awardScore = 0,
				videoScore = 0,projectScore = 0,patentScore = 0,fruitScore = 0,meetingScore = 0,reportScore = 0;
		float hylwNowScore = 0,qklwNowScore = 0,writingNowScore = 0,awardNowScore = 0,
				videoNowScore = 0,projectNowScore = 0,patentNowScore = 0,fruitNowScore = 0,
				meetingNowScore = 0,reportNowScore = 0;
		/**
		 * ���������һ�����ԭ���ŵķ�
		 */
		hylwScore = dutyChangeService.getAllScoreByDeptAndUserAndYear(dept.getKdNumber(), user.getStaffId(), Integer.valueOf(Integer.valueOf(ConvertUtil.convertDateString(changetime.getValue()).substring(0, 4)).intValue()-1).toString(), JXKH_MultiDept.HYLW);
		qklwScore = dutyChangeService.getAllScoreByDeptAndUserAndYear(dept.getKdNumber(), user.getStaffId(), Integer.valueOf(Integer.valueOf(ConvertUtil.convertDateString(changetime.getValue()).substring(0, 4)).intValue()-1).toString(), JXKH_MultiDept.QKLW);
		writingScore = dutyChangeService.getAllScoreByDeptAndUserAndYear(dept.getKdNumber(), user.getStaffId(), Integer.valueOf(Integer.valueOf(ConvertUtil.convertDateString(changetime.getValue()).substring(0, 4)).intValue()-1).toString(), JXKH_MultiDept.ZZ);
		awardScore = dutyChangeService.getAllScoreByDeptAndUserAndYear(dept.getKdNumber(), user.getStaffId(), Integer.valueOf(Integer.valueOf(ConvertUtil.convertDateString(changetime.getValue()).substring(0, 4)).intValue()-1).toString(), JXKH_MultiDept.JL);
		videoScore = dutyChangeService.getAllScoreByDeptAndUserAndYear(dept.getKdNumber(), user.getStaffId(), Integer.valueOf(Integer.valueOf(ConvertUtil.convertDateString(changetime.getValue()).substring(0, 4)).intValue()-1).toString(), JXKH_MultiDept.SP);
		projectScore = dutyChangeService.getAllScoreByDeptAndUserAndYear(dept.getKdNumber(), user.getStaffId(), Integer.valueOf(Integer.valueOf(ConvertUtil.convertDateString(changetime.getValue()).substring(0, 4)).intValue()-1).toString(), JXKH_MultiDept.XM);
		patentScore = dutyChangeService.getAllScoreByDeptAndUserAndYear(dept.getKdNumber(), user.getStaffId(), Integer.valueOf(Integer.valueOf(ConvertUtil.convertDateString(changetime.getValue()).substring(0, 4)).intValue()-1).toString(), JXKH_MultiDept.ZL);
		fruitScore = dutyChangeService.getAllScoreByDeptAndUserAndYear(dept.getKdNumber(), user.getStaffId(), Integer.valueOf(Integer.valueOf(ConvertUtil.convertDateString(changetime.getValue()).substring(0, 4)).intValue()-1).toString(), JXKH_MultiDept.CG);
		meetingScore = dutyChangeService.getAllScoreByDeptAndUserAndYear(dept.getKdNumber(), user.getStaffId(), Integer.valueOf(Integer.valueOf(ConvertUtil.convertDateString(changetime.getValue()).substring(0, 4)).intValue()-1).toString(), JXKH_MultiDept.HY);
		reportScore = dutyChangeService.getAllScoreByDeptAndUserAndYear(dept.getKdNumber(), user.getStaffId(), Integer.valueOf(Integer.valueOf(ConvertUtil.convertDateString(changetime.getValue()).substring(0, 4)).intValue()-1).toString(), JXKH_MultiDept.BG);
		//��һ�����ԭ���ŵ�ҵ���ܷ�
		lastYearScore = hylwScore + qklwScore + writingScore + awardScore + videoScore + 
				projectScore + patentScore + fruitScore + meetingScore + reportScore;
		//����һ��ԭ�����ܷ��м�ȥ�ò��ַ�ֵ
		JXKH_AuditResult lastOldAudit = auditResultService.findWorkDeptByKdIdAndYear(dept.getKdId(), Integer.valueOf(Integer.valueOf(ConvertUtil.convertDateString(changetime.getValue()).substring(0, 4)).intValue()-1).toString());
		if(lastOldAudit != null && lastOldAudit.getArScore() != null) {
			lastOldAudit.setNewArScore(lastOldAudit.getArScore().floatValue() - lastYearScore);
			auditResultService.update(lastOldAudit);
		}
		//����һ���²����ܷ��м��ϸò��ַ�ֵ
		JXKH_AuditResult lastNewAudit = auditResultService.findWorkDeptByKdIdAndYear(newDept.getKdId(), Integer.valueOf(Integer.valueOf(ConvertUtil.convertDateString(changetime.getValue()).substring(0, 4)).intValue()-1).toString());
		if(lastNewAudit != null && lastNewAudit.getArScore() != null) {
			lastNewAudit.setNewArScore(lastNewAudit.getArScore().floatValue() + lastYearScore);
			auditResultService.update(lastNewAudit);
		}
		/**
		 * ������˽������ԭ���ŵķ�
		 */		
		//�ӽ����ܲ����ܷ��м�ȥ�������ԭ���ŵķ�
		JXKH_AuditResult nowAudit = auditResultService.findWorkDeptByKdIdAndYear(dept.getKdId(), ConvertUtil.convertDateString(changetime.getValue()).substring(0, 4));
		/**
		 * �����Ѿ����������
		 */
		if(nowAudit != null) {
			hylwNowScore = dutyChangeService.getAllScoreByDeptAndUserAndYear(dept.getKdNumber(), user.getStaffId(), ConvertUtil.convertDateString(changetime.getValue()).substring(0, 4), JXKH_MultiDept.HYLW);
			qklwNowScore = dutyChangeService.getAllScoreByDeptAndUserAndYear(dept.getKdNumber(), user.getStaffId(), ConvertUtil.convertDateString(changetime.getValue()).substring(0, 4), JXKH_MultiDept.QKLW);
			writingNowScore = dutyChangeService.getAllScoreByDeptAndUserAndYear(dept.getKdNumber(), user.getStaffId(), ConvertUtil.convertDateString(changetime.getValue()).substring(0, 4), JXKH_MultiDept.ZZ);
			awardNowScore = dutyChangeService.getAllScoreByDeptAndUserAndYear(dept.getKdNumber(), user.getStaffId(), ConvertUtil.convertDateString(changetime.getValue()).substring(0, 4), JXKH_MultiDept.JL);
			videoNowScore = dutyChangeService.getAllScoreByDeptAndUserAndYear(dept.getKdNumber(), user.getStaffId(), ConvertUtil.convertDateString(changetime.getValue()).substring(0, 4), JXKH_MultiDept.SP);
			projectNowScore = dutyChangeService.getAllScoreByDeptAndUserAndYear(dept.getKdNumber(), user.getStaffId(), ConvertUtil.convertDateString(changetime.getValue()).substring(0, 4), JXKH_MultiDept.XM);
			patentNowScore = dutyChangeService.getAllScoreByDeptAndUserAndYear(dept.getKdNumber(), user.getStaffId(), ConvertUtil.convertDateString(changetime.getValue()).substring(0, 4), JXKH_MultiDept.ZL);
			fruitNowScore = dutyChangeService.getAllScoreByDeptAndUserAndYear(dept.getKdNumber(), user.getStaffId(), ConvertUtil.convertDateString(changetime.getValue()).substring(0, 4), JXKH_MultiDept.CG);
			meetingNowScore = dutyChangeService.getAllScoreByDeptAndUserAndYear(dept.getKdNumber(), user.getStaffId(), ConvertUtil.convertDateString(changetime.getValue()).substring(0, 4), JXKH_MultiDept.HY);
			reportNowScore = dutyChangeService.getAllScoreByDeptAndUserAndYear(dept.getKdNumber(), user.getStaffId(), ConvertUtil.convertDateString(changetime.getValue()).substring(0, 4), JXKH_MultiDept.BG);
			//�������ԭ���ŵ�ҵ���ܷ�
			nowYearScore = hylwNowScore + qklwNowScore + writingNowScore + awardNowScore +
					videoNowScore + projectNowScore + patentNowScore + fruitNowScore + 
					meetingNowScore + reportNowScore;
			nowAudit.setNewArScore(nowAudit.getArScore().floatValue() - nowYearScore);
			auditResultService.update(nowAudit);
			//���µĲ���
			JXKH_AuditResult nowNewAudit = auditResultService.findWorkDeptByKdIdAndYear(newDept.getKdId(), ConvertUtil.convertDateString(changetime.getValue()).substring(0, 4));
			if(nowNewAudit != null && nowNewAudit.getArScore() != null) {
				nowNewAudit.setNewArScore(nowNewAudit.getArScore().floatValue() + nowYearScore);
				auditResultService.update(nowNewAudit);
			}				
		}
		/**
		 * ���껹δ���й����֣�����Ա��ָ��������ԭ���Ŵ�Ϊ�²���
		 */
		else {
			List<Object> hylwList = dutyChangeService.getAllMemberByDeptAndUserAndYear(dept.getKdNumber(), user.getStaffId(), ConvertUtil.convertDateString(changetime.getValue()).substring(0, 4), JXKH_MultiDept.HYLW);
			if(hylwList != null && hylwList.size() > 0) {
				for(Object o : hylwList) {
					JXKH_HULWMember mem = (JXKH_HULWMember) o;
					mem.setAssignDep(newDept.getKdNumber());
					auditResultService.update(mem);
				}
			}
			List<Object> qklwList = dutyChangeService.getAllMemberByDeptAndUserAndYear(dept.getKdNumber(), user.getStaffId(), ConvertUtil.convertDateString(changetime.getValue()).substring(0, 4), JXKH_MultiDept.QKLW);
			if(qklwList != null && qklwList.size() > 0) {
				for(Object o : qklwList) {
					JXKH_QKLWMember mem = (JXKH_QKLWMember) o;
					mem.setAssignDep(newDept.getKdNumber());
					auditResultService.update(mem);
				}
			}
			List<Object> writingList = dutyChangeService.getAllMemberByDeptAndUserAndYear(dept.getKdNumber(), user.getStaffId(), ConvertUtil.convertDateString(changetime.getValue()).substring(0, 4), JXKH_MultiDept.ZZ);
			if(writingList != null && writingList.size() > 0) {
				for(Object o : writingList) {
					Jxkh_Writer mem = (Jxkh_Writer) o;
					mem.setAssignDep(newDept.getKdNumber());
					auditResultService.update(mem);
				}
			}
			List<Object> awardList = dutyChangeService.getAllMemberByDeptAndUserAndYear(dept.getKdNumber(), user.getStaffId(), ConvertUtil.convertDateString(changetime.getValue()).substring(0, 4), JXKH_MultiDept.JL);
			if(awardList != null && awardList.size() > 0) {
				for(Object o : awardList) {
					Jxkh_AwardMember mem = (Jxkh_AwardMember) o;
					mem.setAssignDep(newDept.getKdNumber());
					auditResultService.update(mem);
				}
			}
			List<Object> videoList = dutyChangeService.getAllMemberByDeptAndUserAndYear(dept.getKdNumber(), user.getStaffId(), ConvertUtil.convertDateString(changetime.getValue()).substring(0, 4), JXKH_MultiDept.SP);
			if(videoList != null && videoList.size() > 0) {
				for(Object o : videoList) {
					Jxkh_VideoMember mem = (Jxkh_VideoMember) o;
					mem.setAssignDep(newDept.getKdNumber());
					auditResultService.update(mem);
				}
			}
			List<Object> projectList = dutyChangeService.getAllMemberByDeptAndUserAndYear(dept.getKdNumber(), user.getStaffId(), ConvertUtil.convertDateString(changetime.getValue()).substring(0, 4), JXKH_MultiDept.XM);
			if(projectList != null && projectList.size() > 0) {
				for(Object o : projectList) {
					Jxkh_ProjectMember mem = (Jxkh_ProjectMember) o;
					mem.setAssignDep(newDept.getKdNumber());
					auditResultService.update(mem);
				}
			}
			List<Object> patentList = dutyChangeService.getAllMemberByDeptAndUserAndYear(dept.getKdNumber(), user.getStaffId(), ConvertUtil.convertDateString(changetime.getValue()).substring(0, 4), JXKH_MultiDept.ZL);
			if(patentList != null && patentList.size() > 0) {
				for(Object o : patentList) {
					Jxkh_PatentInventor mem = (Jxkh_PatentInventor) o;
					mem.setAssignDep(newDept.getKdNumber());
					auditResultService.update(mem);
				}
			}
			List<Object> fruitList = dutyChangeService.getAllMemberByDeptAndUserAndYear(dept.getKdNumber(), user.getStaffId(), ConvertUtil.convertDateString(changetime.getValue()).substring(0, 4), JXKH_MultiDept.CG);
			if(fruitList != null && fruitList.size() > 0) {
				for(Object o : fruitList) {
					Jxkh_FruitMember mem = (Jxkh_FruitMember) o;
					mem.setAssignDep(newDept.getKdNumber());
					auditResultService.update(mem);
				}
			}
			List<Object> meetingList = dutyChangeService.getAllMemberByDeptAndUserAndYear(dept.getKdNumber(), user.getStaffId(), ConvertUtil.convertDateString(changetime.getValue()).substring(0, 4), JXKH_MultiDept.HY);
			if(meetingList != null && meetingList.size() > 0) {
				for(Object o : meetingList) {
					JXKH_MeetingMember mem = (JXKH_MeetingMember) o;
					mem.setAssignDep(newDept.getKdNumber());
					auditResultService.update(mem);
				}
			}
			List<Object> reportList = dutyChangeService.getAllMemberByDeptAndUserAndYear(dept.getKdNumber(), user.getStaffId(), ConvertUtil.convertDateString(changetime.getValue()).substring(0, 4), JXKH_MultiDept.BG);
			if(reportList != null && reportList.size() > 0) {
				for(Object o : reportList) {
					Jxkh_ReportMember mem = (Jxkh_ReportMember) o;
					mem.setAssignDep(newDept.getKdNumber());
					auditResultService.update(mem);
				}
			}
		}
	}
	/**
	 * ����
	 */
	public void onClick$reset() {
		resetWindow();
	}
	
	/**
	 * ������Ա���ڵ�ԭ���ţ�����
	 * @throws InterruptedException 
	 * @throws SuspendNotAllowedException 
	 */
	public void onClick$oldChoose() throws SuspendNotAllowedException, InterruptedException {
		deptType = 1;
		final ChooseDeptWin cmw = (ChooseDeptWin)Executions.createComponents("/admin/jxkh/dutyChange/chooseDept.zul", null, null);
		cmw.initWindow(jp, deptType);		
		cmw.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				oldDept = cmw.getDept();
//				oldDepart.setValue(oldDept.getKdName());
				cmw.detach();
			}
		});
		cmw.doModal();
	}
	/**
	 * ������Ա���������²���
	 * @throws InterruptedException 
	 * @throws SuspendNotAllowedException 
	 */
	public void onClick$newChoose() throws SuspendNotAllowedException, InterruptedException {
		deptType = 0;
		final ChooseDeptWin cmw = (ChooseDeptWin)Executions.createComponents("/admin/jxkh/dutyChange/chooseDept.zul", null, null);
		cmw.initWindow(jp, deptType);		
		cmw.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				newDept = cmw.getDept();
				newDepart.setValue(newDept.getKdName());
				cmw.detach();
			}
		});
		cmw.doModal();
	}
	/**
	 * ��������Ա
	 * @throws InterruptedException 
	 * @throws SuspendNotAllowedException 
	 */
	public void onClick$nameChoose() throws SuspendNotAllowedException, InterruptedException {
		final ChooseMemberWin cmw = (ChooseMemberWin)Executions.createComponents("/admin/jxkh/dutyChange/chooseMember.zul", null, null);
		cmw.initWindow(jp,dept);		
		cmw.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				user = cmw.getUser();
				name.setValue(user.getKuName());
				cmw.detach();
			}
		});
		cmw.doModal();
	}
	/**
	 * �ر�ҳ��
	 */
	public void onClick$close() {
		this.detach();
	}
	
	public void setUser(WkTUser user) {
		this.user = user;
	}
}
