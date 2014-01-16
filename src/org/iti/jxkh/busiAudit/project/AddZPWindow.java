package org.iti.jxkh.busiAudit.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxkh.business.award.ChooseDeptWin;
import org.iti.jxkh.business.award.ChooseMemberWin;
import org.iti.jxkh.business.meeting.AssignDeptWindow;
import org.iti.jxkh.business.meeting.UpfileWindow;
import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.entity.Jxkh_Patent;
import org.iti.jxkh.entity.Jxkh_Project;
import org.iti.jxkh.entity.Jxkh_ProjectDept;
import org.iti.jxkh.entity.Jxkh_ProjectFile;
import org.iti.jxkh.entity.Jxkh_ProjectFund;
import org.iti.jxkh.entity.Jxkh_ProjectMember;
import org.iti.jxkh.service.BusinessIndicatorService;
import org.iti.jxkh.service.JXKHMeetingService;
import org.iti.jxkh.service.JxkhProjectService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.iti.common.util.ConvertUtil;
import com.iti.common.util.PropertiesLoader;
import com.iti.common.util.UploadUtil;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.DepartmentService;

public class AddZPWindow extends Window implements AfterCompose {

	/**
	 * @author SongYu
	 */
	private static final long serialVersionUID = -3664613977051724505L;
	private Tab /*baseTab,*/ funds, fileTab, scoreTab;
	private Listbox personScore, departmentScore;
//	private Toolbarbutton submit, close;
	private Toolbarbutton ups1, ups2;
	WkTUser user;
	private JxkhProjectService jxkhProjectService;
	private JXKHMeetingService jxkhMeetingService;

	private Textbox projectName, projectMember, department, coUnit, sumFund, projecCode, header, standDept;// takeCompany,
	private Label /*yearFund,*/ writer;
	private Datebox begin, end/*, standYear*/;
	private Listbox rank, progress, fundsListbox1, fundsListbox2;
	private Radiogroup ifCoo, ifHum, ifSoft;
	private Row coUnitRow, /*setInfo,*/ recordlabel;
	private Button print;
	private YearListbox jiFenTime;
	private Jxkh_Project project;
	// private String subnum, outnum, innum;
	private String dept = "";
	private String dept1 = "";
	private List<WkTUser> memberList = new ArrayList<WkTUser>();
	private List<WkTDept> deptList = new ArrayList<WkTDept>();
	private List<Jxkh_ProjectMember> projectMemberList = new ArrayList<Jxkh_ProjectMember>();
	private List<Jxkh_ProjectDept> projectDeptList = new ArrayList<Jxkh_ProjectDept>();
	private Radio firstSignTrue, firstSignFalse;
	// private Label recordlabel;
	private Hbox recordhbox;
	private Textbox record;
	private Listbox applyList1, applyList2, applyList3/*, applyList4*/;
	private Set<Jxkh_ProjectFile> fileList1;

	private DepartmentService departmentService;
	private BusinessIndicatorService businessIndicatorService;

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");// ��ȡ��ǰ��¼�û�
		jiFenTime.initYearListbox("");
		rank.setItemRenderer(new RankRenderer());
		personScore.setItemRenderer(new personScoreRenderer());
		departmentScore.setItemRenderer(new departmentScoreRenderer());
		progress.setItemRenderer(new TypeRenderer());
		fundsListbox1.setItemRenderer(new FundsRenderer());
		fundsListbox2.setItemRenderer(new FundsRenderer());

		end.addEventListener(Events.ON_CHANGE, new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				String nowDate = ConvertUtil.convertDateString(new Date());
				String chooseDate = ConvertUtil.convertDateString(end.getValue());
				int result = chooseDate.compareTo(nowDate);
				if (result < 0) {
					progress.setSelectedIndex(2);
				} else {
					progress.setSelectedIndex(0);
				}
			}
		});
	}

	public void initWindow() {
		print.setVisible(false);
		if (project == null) {
			funds.setDisabled(true);
			fileTab.setDisabled(true);
			scoreTab.setDisabled(true);
		} else {
			funds.setDisabled(false);
			fileTab.setDisabled(false);
			scoreTab.setDisabled(false);
		}
		projectName.setValue(project.getName());
		jiFenTime.initYearListbox(project.getjxYear());
		// ��ʼ����Ŀ�����б�
		List<Jxkh_BusinessIndicator> typeList = new ArrayList<Jxkh_BusinessIndicator>();
		typeList.add(new Jxkh_BusinessIndicator());
		typeList.addAll(jxkhProjectService.findRank());
		rank.setModel(new ListModelList(typeList));
		// ��ʼ����Ŀ��չ�б�
		String[] t = { "--��ѡ��--", "����", "����", "����" };
		List<String> progressList = new ArrayList<String>();
		for (int i = 0; i < 4; i++) {
			progressList.add(t[i]);
		}
		progress.setModel(new ListModelList(progressList));
		WkTUser infoWriter = jxkhProjectService.findWktUserByMemberUserId(project.getInfoWriter());
		writer.setValue(infoWriter.getKuName());
		sumFund.setValue(project.getSumFund().toString());
		projecCode.setValue(project.getProjecCode());
		// takeCompany.setValue(project.getTakeCompany());
		header.setValue(project.getHeader());
		standDept.setValue(project.getStandDept());
		if (project.getState() == Jxkh_Patent.SAVE_RECORD) {
			record.setVisible(true);
			recordlabel.setVisible(true);
			recordhbox.setVisible(true);
			record.setValue(project.getRecordCode());
		}
		if (project.getBeginDate() == null || project.getBeginDate().length() == 0) {
			begin.setValue(null);
		} else {
			begin.setValue(ConvertUtil.convertDate(project.getBeginDate()));
		}
		if (project.getEndDate() == null || project.getEndDate().length() == 0) {
			end.setValue(null);
		} else {
			end.setValue(ConvertUtil.convertDate(project.getEndDate()));
		}
		// if (project.getStandYear() == null
		// || project.getStandYear().length() == 0) {
		// standYear.setValue(null);
		// } else {
		// standYear.setValue(ConvertUtil.convertDate(project.getStandYear()));
		// }

		// if (project.getProgress().equals("����")) {
		// setInfo.setVisible(true);
		// }

		if (project.getCooState() != null) {
			if (project.getCooState() == Jxkh_Project.YES) {
				ifCoo.setSelectedIndex(0);
				coUnitRow.setVisible(true);
				coUnit.setValue(project.getCooCompany());
			} else {
				ifCoo.setSelectedIndex(1);
			}
		}
		if (project.getFirstSign() != null) {
			if (project.getFirstSign() == Jxkh_Project.YES) {
				firstSignTrue.setChecked(true);
				firstSignFalse.setChecked(false);
			} else {
				firstSignTrue.setChecked(false);
				firstSignFalse.setChecked(true);
			}
		}
		if (project.getIfHumanities() != null) {
			if (project.getIfHumanities() == Jxkh_Project.YES) {
				ifHum.setSelectedIndex(0);
			} else {
				ifHum.setSelectedIndex(1);
			}
		}
		if (project.getIfSoftScience() != null) {
			if (project.getIfSoftScience() == Jxkh_Project.YES) {
				ifSoft.setSelectedIndex(0);
			} else {
				ifSoft.setSelectedIndex(1);
			}
		}

		String memberName = "";
		String deptName = "";
		// ��Ա ����
		projectMemberList = jxkhProjectService.findProjectMember(project);
		if (projectMemberList != null && projectMemberList.size() != 0) {
			for (int i = 0; i < projectMemberList.size(); i++) {
				Jxkh_ProjectMember mem = (Jxkh_ProjectMember) projectMemberList.get(i);
				memberName += mem.getName() + "��";
				if (mem.getType() == 0) {
					WkTUser user = jxkhProjectService.findWktUserByMemberUserId(mem.getPersonId());
					memberList.add(user);
				} else {
					WkTUser out = new WkTUser();
					WkTDept d = new WkTDept();
					d.setKdName("У��");
					out.setDept(d);
					out.setKuId(new Random().nextLong());
					out.setKuLid("У��");
					out.setKuName(mem.getName());
					out.setType((short) 1);
					memberList.add(out);
				}
			}
			projectMember.setValue(memberName.substring(0, memberName.length() - 1));
		}
		projectDeptList = jxkhProjectService.findProjectDept(project);
		if (projectDeptList != null && projectDeptList.size() != 0) {
			for (int i = 0; i < projectDeptList.size(); i++) {
				Jxkh_ProjectDept dept = (Jxkh_ProjectDept) projectDeptList.get(i);
				deptName += dept.getName() + "��";
				WkTDept depts = jxkhProjectService.findWkTDeptByDept(dept.getDeptId());
				deptList.add(depts);
			}
			department.setValue(deptName.substring(0, deptName.length() - 1));
		}
		// �µĸ�����ʼ��20120318
		arrList1.clear();
		arrList2.clear();
		arrList3.clear();
		arrList4.clear();
		fileList1 = project.getProjectFile();
		Object[] file = fileList1.toArray();
		for (int j = 0; j < file.length; j++) {
			Jxkh_ProjectFile f = (Jxkh_ProjectFile) file[j];
			if (f.getBelongType().equals("��Ŀ������")) {
				String[] arr = new String[4];
				arr[0] = f.getPath();
				arr[1] = f.getName();
				arr[2] = f.getSubmitDate();
				arr[3] = f.getBelongType();
				arrList1.add(arr);
			}
			if (f.getBelongType().equals("��Ŀ��ͬ��")) {
				String[] arr = new String[4];
				arr[0] = f.getPath();
				arr[1] = f.getName();
				arr[2] = f.getSubmitDate();
				arr[3] = f.getBelongType();
				arrList2.add(arr);
			}
			if (f.getBelongType().equals("��Ƚ����ĵ�")) {
				String[] arr = new String[4];
				arr[0] = f.getPath();
				arr[1] = f.getName();
				arr[2] = f.getSubmitDate();
				arr[3] = f.getBelongType();
				arrList3.add(arr);
			}
			if (f.getBelongType().equals("��Ŀ����֤��")) {
				String[] arr = new String[4];
				arr[0] = f.getPath();
				arr[1] = f.getName();
				arr[2] = f.getSubmitDate();
				arr[3] = f.getBelongType();
				arrList4.add(arr);
			}
		}
		applyList1.setItemRenderer(new FilesRenderer1());
		applyList1.setModel(new ListModelList(arrList1));
		applyList2.setItemRenderer(new FilesRenderer2());
		applyList2.setModel(new ListModelList(arrList2));
		applyList3.setItemRenderer(new FilesRenderer3());
		applyList3.setModel(new ListModelList(arrList3));
//		applyList4.setItemRenderer(new FilesRenderer4());
//		applyList4.setModel(new ListModelList(arrList4));

		// ����
		List<Jxkh_ProjectFund> fundList1 = jxkhProjectService.findFunds(project, Jxkh_ProjectFund.ZXF);
		fundsListbox1.setModel(new ListModelList(fundList1));
		// ����
		List<Jxkh_ProjectFund> fundList2 = jxkhProjectService.findFunds(project, Jxkh_ProjectFund.ZCF);
		fundsListbox2.setModel(new ListModelList(fundList2));

		personScore.setModel(new ListModelList(projectMemberList));
		departmentScore.setModel(new ListModelList(projectDeptList));
	}

	public void initShow() {
		print.setVisible(false);
		if (project == null) {
			funds.setDisabled(true);
			fileTab.setDisabled(true);
			scoreTab.setDisabled(true);
			// documents.setDisabled(true);
			print.setVisible(false);
		} else {
			funds.setDisabled(false);
			fileTab.setDisabled(false);
			scoreTab.setDisabled(false);
			// documents.setDisabled(false);
			print.setHref("/print.action?n=zp&id=" + project.getId());
			// ����
			// List<Jxkh_ProjectFund> fundList1 = jxkhProjectService.findFunds(
			// project, Jxkh_ProjectFund.ZXF);
			// fundsListbox1.setModel(new ListModelList(fundList1));
			// List<Jxkh_ProjectFund> fundList2 = jxkhProjectService.findFunds(
			// project, Jxkh_ProjectFund.ZCF);
			// fundsListbox2.setModel(new ListModelList(fundList2));
			// Float score = computeScore();
			// project.setScore(score);
			// System.out.println(score + "");
			// jxkhProjectService.saveOrUpdate(project);
			//
			// for (int i = 0; i < projectMemberList.size(); i++) {
			// Listitem item = personScore.getItemAtIndex(i);
			// Jxkh_ProjectMember member = (Jxkh_ProjectMember) item
			// .getValue();
			// float s = 0.0f;// ����
			// float f = 0.0f;// ��ֵ
			// s = member.getPer();
			// if (project.getScore() != null)
			// f = s * project.getScore() / 10;
			// float pscore = (float) (Math.round(f * 1000)) / 1000;
			// member.setScore(pscore);
			// jxkhProjectService.update(member);
			// }
			// List<Jxkh_ProjectMember> tempMemberList = jxkhProjectService
			// .findProjectMember(project);
			// personScore.setModel(new ListModelList(tempMemberList));
			// for (int k = 0; k < projectDeptList.size(); k++) {
			// Jxkh_ProjectDept d = projectDeptList.get(k);
			// float f = 0.0f;
			// for (int i = 0; i < tempMemberList.size(); i++) {
			// Jxkh_ProjectMember m = tempMemberList.get(i);
			// if (m.getAssignDep() == null || m.getAssignDep().equals("")) {
			// if (m.getDept().equals(d.getName())) {
			// f += m.getScore();
			// }
			// } else if (m.getAssignDep().equals(d.getName())) {
			// f += m.getScore();
			// }
			// }
			// d.setScore(f);
			// jxkhMeetingService.update(d);
			// }
			// List<Jxkh_ProjectDept> tempDeptList = jxkhProjectService
			// .findProjectDept(project);
			// departmentScore.setModel(new ListModelList(tempDeptList));

		}

		/*jiFenTime.initYearlist();*/
		jiFenTime.initYearListbox("");

		List<Jxkh_BusinessIndicator> typeList = new ArrayList<Jxkh_BusinessIndicator>();
		typeList.add(new Jxkh_BusinessIndicator());
		typeList.addAll(jxkhProjectService.findRank());
		rank.setModel(new ListModelList(typeList));

		String[] t = { "--��ѡ��--", "����", "����", "����" };
		List<String> progressList = new ArrayList<String>();
		for (int i = 0; i < 4; i++) {
			progressList.add(t[i]);
		}
		progress.setModel(new ListModelList(progressList));

	}

	/**
	 * ������Ŀ���ּ���
	 */
	private void computeScore() {
		// float res = 0f;
		// float result = 0f;
		// float rank = 1f;
		// if (project.getIfHumanities().shortValue() == Jxkh_Project.YES) {
		// rank = 3f * rank;
		// }
		// if (project.getIfSoftScience().shortValue() == Jxkh_Project.YES) {
		// rank = 2f * rank;
		// }
		// if (project.getId() != null) {
		// // ��ֵ���㲿��
		// // List<Jxkh_ProjectFund> fundList1 = jxkhProjectService.findFunds(
		// // project, Jxkh_ProjectFund.ZXF);
		// // fundsListbox1.setModel(new ListModelList(fundList1));
		//
		// String year = project.getjxYear();
		// List outNum = jxkhProjectService.sumOut(project, year);
		// List inNum = jxkhProjectService.sumIn(project, year);
		// if (outNum.size() == 0 || outNum.get(0) == null) {
		// outnum = "0.0";
		// } else {
		// outnum = outNum.get(0).toString();
		// }
		// if (inNum.size() == 0 || inNum.get(0) == null) {
		// innum = "0.0";
		// } else {
		// innum = inNum.get(0).toString();
		// }
		// subnum = String.valueOf(Float.parseFloat(innum) -
		// Float.parseFloat(outnum));
		// yearFund.setValue(subnum);
		// result = Float.parseFloat(innum) - Float.parseFloat(outnum);
		//
		// if (project.getRank() == null) {
		// res += result * 0f;
		// } else if (project.getRank().getKbStatus() ==
		// Jxkh_BusinessIndicator.USE_NO) {
		// res += result * 0f;
		// } else {
		// res += rank * result * project.getRank().getKbScore() *
		// project.getRank().getKbIndex();
		// }
		// return Float.valueOf(res);
		// } else {
		// return Float.valueOf(res);
		// }
		float res = 0f;
		float result = 0f;
		String type = "";
		float nationwork = 0f, provincestudy = 0f, provincework = 0f, citystudy = 0f, hproject = 0f;
		Properties property = PropertiesLoader.loader("title", "title.properties");
		/**
		 * ��һ����������Ŀ�Ļ����÷�
		 */
		// ���ǵ�һ����
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
			// �ǵ�һ����
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
		 * �ڶ�������������ŵĵ÷֡���Ա�ĵ÷�
		 */
		if (project.getId() != null) {
			String year = project.getjxYear();
			List<Jxkh_ProjectFund> flist = jxkhProjectService.getFundByYearAndProject(project, year);
			Set<Long> fset = new HashSet<Long>();
			for (Jxkh_ProjectFund f : flist) {
				if (f.getDeptNum() != null)
					fset.add(f.getDeptNum());
			}
			// ��Ų��ţ����Ŷ�Ӧ�ľ��ѣ�String��ʾ���ŵı�ţ�Long��ʾ����
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
			// ���¸����ŵ÷�
			float totalFund = 0f;
			for (Jxkh_ProjectDept d : dlist) {
				List<WkTDept> dt = departmentService.getDeptByNum(d.getDeptId());
				totalFund += dScoreA.get(dt.get(0).getKdId());
				d.setScore(dScoreA.get(dt.get(0).getKdId()) * res);
				jxkhProjectService.update(d);
			}
			List<Jxkh_ProjectMember> mlist = jxkhProjectService.findProjectMember(project);
			/**
			 * ������Ա�ı����ͷ�ֵ
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
			// ������Ŀ�ĵ÷�
			project.setScore(totalFund * res);
			jxkhProjectService.update(project);

			// List<?> outNum = jxkhProjectService.sumOut(project, year);
			// List<?> inNum = jxkhProjectService.sumIn(project, year);
			// if (outNum.size() == 0 || outNum.get(0) == null) {
			// outnum = "0.0";
			// } else {
			// outnum = outNum.get(0).toString();
			// }
			// if (inNum.size() == 0 || inNum.get(0) == null) {
			// innum = "0.0";
			// } else {
			// innum = inNum.get(0).toString();
			// }
			// result = Float.parseFloat(innum) - Float.parseFloat(outnum);
			// if (project.getRank() == null) {
			// res += result * 0f;
			// } else if (project.getRank().getKbStatus() ==
			// Jxkh_BusinessIndicator.USE_NO) {
			// res += result * 0f;
			// } else {
			// res += rank * result * project.getRank().getKbScore()
			// * project.getRank().getKbIndex();
			// }
			// return Float.valueOf(res);
		}

	}

	/**
	 * <li>��������������������Ŀ�� void
	 * 
	 * @author songyu
	 * @throws InterruptedException
	 */
	public void onClick$submit() throws InterruptedException {
		if (project == null)
			project = new Jxkh_Project();
		if (projectName.getValue().equals("")) {
			Messagebox.show("��Ŀ���Ʋ���Ϊ�գ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			projectName.focus();
			return;
		}
		if (rank.getSelectedItem() == null || rank.getSelectedItem().getIndex() == 0) {
			Messagebox.show("��Ŀ������Ϊ�գ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		if (projectMember.getValue() == null || projectMember.getValue().equals("")) {
			Messagebox.show("��ѡ���Ա��", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		if (department.getValue() == null || department.getValue().equals("")) {
			Messagebox.show("��ѡ���ţ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}

		if (jiFenTime.getSelectedIndex() == 0 || jiFenTime.getSelectedItem() == null) {
			try {
				Messagebox.show("��ѡ�񼨷���ȣ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		project.setjxYear(jiFenTime.getSelectedItem().getValue() + "");
		project.setName(projectName.getValue());
		// if (dept != "dept") {
		// project.setInfoWriter(user.getKuLid());
		// }
		project.setRecordCode(record.getValue());
		project.setCooCompany(coUnit.getValue());
		project.setSumFund(Float.parseFloat(sumFund.getValue()));
		project.setProjecCode(projecCode.getValue());
		// project.setTakeCompany(takeCompany.getValue());
		project.setHeader(header.getValue());
		project.setStandDept(standDept.getValue());
		project.setSort(Jxkh_Project.ZP);
		// if (dept1 != "dept") {
		// project.setState(Jxkh_Project.NOT_AUDIT);
		// }else{
		// project.setState(Jxkh_Project.DEPT_PASS);
		// }
		if (rank.getSelectedItem() != null)
			project.setRank((Jxkh_BusinessIndicator) rank.getSelectedItem().getValue());
		if (progress.getSelectedItem() != null)
			project.setProgress(progress.getSelectedItem().getValue().toString());
		if (ifCoo.getSelectedIndex() == 0) {
			project.setCooState(Jxkh_Project.YES);
		} else {
			project.setCooState(Jxkh_Project.NO);
			firstSignTrue.setChecked(true);
		}
		if (firstSignTrue.isChecked()) {
			project.setFirstSign(Jxkh_Project.YES);
		} else {
			project.setFirstSign(Jxkh_Project.NO);
		}
		if (ifHum.getSelectedIndex() == 0) {
			project.setIfHumanities(Jxkh_Project.YES);
		} else {
			project.setIfHumanities(Jxkh_Project.NO);
		}
		if (ifSoft.getSelectedIndex() == 0) {
			project.setIfSoftScience(Jxkh_Project.YES);
		} else {
			project.setIfSoftScience(Jxkh_Project.NO);
		}
		if (begin.getValue() != null)
			project.setBeginDate(ConvertUtil.convertDateString(begin.getValue()));
		if (end.getValue() != null)
			project.setEndDate(ConvertUtil.convertDateString(end.getValue()));

		int j = 1;
		List<Jxkh_ProjectMember> mlist = new ArrayList<Jxkh_ProjectMember>();
		List<Jxkh_ProjectMember> templist = new ArrayList<Jxkh_ProjectMember>();
		List<Jxkh_ProjectDept> dlist = new ArrayList<Jxkh_ProjectDept>();
		if (project.getId() != null) {
			projectMemberList = jxkhProjectService.findProjectMember(project);
			if (projectMemberList != null && projectMemberList.size() != 0) {
				
					for(Jxkh_ProjectMember m : projectMemberList){
						Jxkh_ProjectMember mem = new Jxkh_ProjectMember();
						mem.setPer(m.getPer());
						mem.setScore(m.getScore());
						mem.setPersonId(m.getPersonId());
						templist.add(mem);
					}
					
					jxkhProjectService.deleteAll(projectMemberList);
			}
		}
		if (memberList != null && memberList.size() != 0) {
			for (WkTUser user : memberList) {
				Jxkh_ProjectMember member = new Jxkh_ProjectMember();

				member.setProject(project);
				member.setDept(user.getDept().getKdName());
				member.setName(user.getKuName());
				member.setPersonId(user.getKuLid());
				member.setRank(j);
				
				if(templist.size() != 0 && templist != null){
					for(Jxkh_ProjectMember m :templist){
						if(member.getPersonId() != null && m.getPersonId() != null 
								&& member.getPersonId().equals(m.getPersonId())){
							member.setPer(m.getPer());
							member.setScore(m.getScore());
						}
					}
				}
				
				
				switch (user.getType()) {
				case WkTUser.TYPE_IN:
					member.setType(Short.valueOf(WkTUser.TYPE_IN));
					break;
				case WkTUser.TYPE_OUT:
					member.setType(Short.valueOf(WkTUser.TYPE_OUT));
					break;
				}
				mlist.add(member);
			}
			project.setProjectMember(mlist);
		}
		int i = 1;
		
		List<Jxkh_ProjectDept> tempDlist = new ArrayList<Jxkh_ProjectDept>();
		
		if (project.getId() != null) {
			projectDeptList = jxkhProjectService.findProjectDept(project);
			if (projectDeptList != null && projectDeptList.size() != 0) {
				
				for(Jxkh_ProjectDept d: projectDeptList){
					if(d != null && d.getProject() != null && d.getScore() != null){
						Jxkh_ProjectDept dept = new Jxkh_ProjectDept();
						dept.setProject(d.getProject());
						dept.setScore(d.getScore());
						tempDlist.add(dept);
					}
					
				}
				
				jxkhProjectService.deleteAll(projectDeptList);
			}
		}
		if (deptList != null && deptList.size() != 0) {
			for (WkTDept wktDept : deptList) {
				Jxkh_ProjectDept dept = new Jxkh_ProjectDept();
				dept.setProject(project);
				dept.setDeptId(wktDept.getKdNumber());
				dept.setName(wktDept.getKdName());
				dept.setRank(i);
				
				for(Jxkh_ProjectDept  d:tempDlist){
					if(d.getProject().getId().equals(dept.getProject().getId())){
						dept.setScore(d.getScore());
					}
				}
				
				i++;
				dlist.add(dept);
			}
			project.setProjectDept(dlist);
		}		
		try {
			jxkhProjectService.saveOrUpdate(project);
			Messagebox.show("������Ŀ����ɹ���", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			
			projectMemberList = jxkhProjectService.findProjectMember(project);
			personScore.setModel(new ListModelList(projectMemberList));
			
			funds.setDisabled(false);
			fileTab.setDisabled(false);
//			funds.setSelected(true);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				Messagebox.show("������Ŀ����ʧ�ܣ������ԣ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		// initShow();
	}

	/**
	 * <li>�����������رյ�ǰ���ڡ� void
	 * 
	 * @author songyu
	 */
	public void onClick$close() {
		this.onClose();
	}

	public void onClick$close1() {
		this.onClose();
	}

	/** ��Ŀ�����б���Ⱦ�� */
	public class RankRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			Jxkh_BusinessIndicator type = (Jxkh_BusinessIndicator) data;
			item.setValue(type);
			Listcell c0 = new Listcell();
			if (type.getKbName() == null) {
				c0.setLabel("--��ѡ��--");
			} else {
				c0.setLabel(type.getKbName());
			}
			item.appendChild(c0);
			if (project != null && type.equals(project.getRank())) {
				item.setSelected(true);
			}
		}
	}

	/** ��Ŀ��չ�б���Ⱦ�� */
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
			if (project != null && type.equals(project.getProgress())) {
				item.setSelected(true);
			}
		}
	}

	/** �����б���Ⱦ�� */
	public class FundsRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			Jxkh_ProjectFund fund = (Jxkh_ProjectFund) data;
			item.setValue(fund);
			Listcell c0 = new Listcell();
			Listcell c1 = new Listcell(item.getIndex() + 1 + "");
			Listcell c2 = new Listcell(fund.getMoney() + "");
			c2.setTooltiptext("������༭������Ϣ");
			c2.setStyle("color:blue");
			c2.addEventListener(Events.ON_CLICK, new EditListener());
			String strC3 = null;
			switch (fund.getType()) {
			case Jxkh_ProjectFund.IN:
				strC3 = "��������";
				break;
			case Jxkh_ProjectFund.OUT:
				strC3 = "Ժ�Ⲧ��֧��";
				break;
			// default:strC3 = "δ���";break;
			}
			Listcell c3 = new Listcell(strC3);
			Listcell c5 = new Listcell();
			if (fund.getDate() == null || fund.getDate().length() == 0) {
				c5.setLabel("");
			} else {
				c5.setLabel(fund.getDate());
			}
			Listcell c6 = new Listcell(fund.getjxYear());
			Listcell c07 = new Listcell();
			if (fund.getDeptNum() != null) {
				WkTDept dept = (WkTDept) departmentService.get(WkTDept.class, fund.getDeptNum());
				c07.setLabel(dept.getKdName());
			}
			Listcell c7 = new Listcell(fund.getTransactor());
			item.appendChild(c0);
			item.appendChild(c1);
			item.appendChild(c2);
			item.appendChild(c3);
			item.appendChild(c5);
			item.appendChild(c6);
			item.appendChild(c07);
			item.appendChild(c7);
		}
	}

	/** ����ר��� */
	public void onClick$addFund1() {
		AddFundWindow w = (AddFundWindow) Executions.createComponents("/admin/jxkh/busiAudit/project/addFund.zul", null, null);
		try {
			w.setProject(project);
			w.initListbox();
			w.setSort(Jxkh_ProjectFund.ZXF);
			w.addEventListener(Events.ON_CHANGE, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					if(scoreTab.isDisabled())
						scoreTab.setDisabled(false);
					List<Jxkh_ProjectMember> mlist = jxkhProjectService.findProjectMember(project);
					personScore.setModel(new ListModelList(mlist));
					List<Jxkh_ProjectDept> dlist = jxkhProjectService.findProjectDep(project);
					departmentScore.setModel(new ListModelList(dlist));
				}
			});
			w.doModal();
		} catch (SuspendNotAllowedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// ����
		List<Jxkh_ProjectFund> fundList1 = jxkhProjectService.findFunds(project, Jxkh_ProjectFund.ZXF);
		fundsListbox1.setModel(new ListModelList(fundList1));		
	}

	/** �����Գﾭ�� */
	public void onClick$addFund2() {
		AddFundWindow w = (AddFundWindow) Executions.createComponents("/admin/jxkh/busiAudit/project/addFund.zul", null, null);
		try {
			w.setProject(project);
			w.setSort(Jxkh_ProjectFund.ZCF);
			w.doModal();
		} catch (SuspendNotAllowedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// ����
		List<Jxkh_ProjectFund> fundList1 = jxkhProjectService.findFunds(project, Jxkh_ProjectFund.ZCF);
		fundsListbox2.setModel(new ListModelList(fundList1));
	}

	/** �������ѽ���еĵ����¼� */
	public class EditListener implements EventListener {
		@Override
		public void onEvent(Event event) throws Exception {
			Listitem item = (Listitem) event.getTarget().getParent();
			Jxkh_ProjectFund fund = (Jxkh_ProjectFund) item.getValue();
			EditFundWindow w = (EditFundWindow) Executions.createComponents("/admin/jxkh/busiAudit/project/editFund.zul", null, null);
			try {
				w.setFund(fund);
				w.initListbox();
				w.addEventListener(Events.ON_CHANGE, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						if (scoreTab.isDisabled()) {
							scoreTab.setDisabled(false);
						}
						List<Jxkh_ProjectMember> mlist = jxkhProjectService.findProjectMember(project);
						personScore.setModel(new ListModelList(mlist));
						List<Jxkh_ProjectDept> dlist = jxkhProjectService.findProjectDep(project);
						departmentScore.setModel(new ListModelList(dlist));
					}
				});
				w.doModal();
			} catch (SuspendNotAllowedException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			initShow();
		}
	}

	/**
	 * <li>����������ɾ��ר��� void
	 * 
	 * @author songyu
	 */
	public void onClick$delFund1() throws InterruptedException {
		if (fundsListbox1.getSelectedItems() == null || fundsListbox1.getSelectedItems().size() <= 0) {
			try {
				Messagebox.show("��ѡ��Ҫɾ���ľ�����Ϣ��", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		Messagebox.show("ȷ��ɾ����?", "ȷ��", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
			public void onEvent(Event evt) throws InterruptedException {
				if (evt.getName().equals("onOK")) {
					Iterator<?> items = fundsListbox1.getSelectedItems().iterator();
					List<Jxkh_ProjectFund> fundList = new ArrayList<Jxkh_ProjectFund>();
					Jxkh_ProjectFund fund = new Jxkh_ProjectFund();
					while (items.hasNext()) {
						Listitem item = (Listitem) items.next();
						fund = (Jxkh_ProjectFund) item.getValue();
						fundList.add(fund);
					}
					jxkhProjectService.deleteAll(fundList);
					List<Jxkh_ProjectFund> fundList1 = jxkhProjectService.findFunds(project, Jxkh_ProjectFund.ZXF);
					fundsListbox1.setModel(new ListModelList(fundList1));
					//�����ֵ
					computeScore();
					List<Jxkh_ProjectMember> mlist = jxkhProjectService.findProjectMember(project);
					personScore.setModel(new ListModelList(mlist));
					List<Jxkh_ProjectDept> dlist = jxkhProjectService.findProjectDep(project);
					departmentScore.setModel(new ListModelList(dlist));
					try {
						Messagebox.show("������Ϣɾ���ɹ���", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		initShow();
	}

	/**
	 * <li>����������ɾ���Գﾭ�� void
	 * 
	 * @author songyu
	 */
	public void onClick$delFund2() throws InterruptedException {
		if (fundsListbox2.getSelectedItems() == null || fundsListbox2.getSelectedItems().size() <= 0) {
			try {
				Messagebox.show("��ѡ��Ҫɾ���ľ�����Ϣ��", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		Messagebox.show("ȷ��ɾ����?", "ȷ��", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
			public void onEvent(Event evt) throws InterruptedException {
				if (evt.getName().equals("onOK")) {
					Iterator<?> items = fundsListbox2.getSelectedItems().iterator();
					List<Jxkh_ProjectFund> fundList = new ArrayList<Jxkh_ProjectFund>();
					Jxkh_ProjectFund fund = new Jxkh_ProjectFund();
					while (items.hasNext()) {
						Listitem item = (Listitem) items.next();
						fund = (Jxkh_ProjectFund) item.getValue();
						fundList.add(fund);
					}
					jxkhProjectService.deleteAll(fundList);
					List<Jxkh_ProjectFund> fundList1 = jxkhProjectService.findFunds(project, Jxkh_ProjectFund.ZCF);
					fundsListbox2.setModel(new ListModelList(fundList1));
					try {
						Messagebox.show("������Ϣɾ���ɹ���", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		initShow();
	}

	/** �����б���Ⱦ�� */
	public class FileRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			String name = (String) data;
			item.setValue(name);
			item.setLabel(name);
			if (item.getIndex() == 0)
				item.setSelected(true);
		}
	}

	// ����ѡ���Ա��ť����������ѡ���Աҳ���¼�
	public void onClick$chooseMember() throws SuspendNotAllowedException, InterruptedException {
		final ChooseMemberWin win = (ChooseMemberWin) Executions.createComponents("/admin/jxkh/busiAudit/award/choosemember.zul", null, null);
		win.setMemberList(memberList);
		win.initWindow();
		win.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				memberList = win.getMemberList();
				String members = "";
				for (WkTUser user : memberList) {
					members += user.getKuName() + ",";
				}
				if (members.endsWith(",")) {
					members = members.substring(0, members.length() - 1);
				}
				projectMember.setValue(members);
				win.detach();
			}
		});
		win.doModal();
		// ����ѡ�����Ա�������Զ���֮��Ӧ ��2012��03��07�š�
		deptList.clear();
		for (int i = 0; i < memberList.size(); i++) {
			int k = 0;// ����Ա���ڵĲ��ź�ǰ���ظ���k=1
			WkTUser ui = (WkTUser) memberList.get(i);
			for (int j = 0; j < i; j++) {
				WkTUser uj = (WkTUser) memberList.get(j);
				if (ui.getDept().getKdName() == "У��" || ui.getDept().getKdId().equals(uj.getDept().getKdId())) {
					k = 1;
				}
			}
			if (k == 0 && ui.getType() != 1)
				deptList.add(ui.getDept());
		}
		String depts = "";
		for (WkTDept dept : deptList) {
			depts += dept.getKdName() + ",";
		}
		if (depts.endsWith(",")) {
			depts = depts.substring(0, depts.length() - 1);
		}
		department.setValue(null);
		department.setValue(depts);
	}

	// ����ѡ���Ű�ť����������ѡ���Աҳ���¼�
	public void onClick$chooseDept() throws SuspendNotAllowedException, InterruptedException {
		final ChooseDeptWin win = (ChooseDeptWin) Executions.createComponents("/admin/jxkh/busiAudit/award/choosedept.zul", null, null);
		win.setDeptList(deptList);
		win.initWindow();
		win.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				deptList = win.getDeptList();
				String depts = "";
				for (WkTDept dept : deptList) {
					depts += dept.getKdName() + ",";
				}
				if (depts.endsWith(",")) {
					depts = depts.substring(0, depts.length() - 1);
				}
				department.setValue(depts);
				standDept.setValue(deptList.get(0).getKdName());
				win.detach();
			}
		});
		win.doModal();
	}

	public Jxkh_Project getProject() {
		return project;
	}

	public void setProject(Jxkh_Project project) {
		this.project = project;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getDept1() {
		return dept1;
	}

	public void setDept1(String dept1) {
		this.dept1 = dept1;
	}

	// 20120305
	private List<String[]> arrList1 = new ArrayList<String[]>();

	public void onClick$ups1() {
		final UpfileWindow w = (UpfileWindow) Executions.createComponents("/admin/jxkh/busiAudit/meeting/upfile.zul", null, null);
		w.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				String filePath = (String) Executions.getCurrent().getAttribute("path");
				String fileName = (String) Executions.getCurrent().getAttribute("title");
				String upTime = (String) Executions.getCurrent().getAttribute("upTime");
				applyList1.setItemRenderer(new FilesRenderer1());
				String[] arr = new String[4];
				arr[0] = filePath;
				arr[1] = fileName;
				arr[2] = upTime;
				arr[3] = "��Ŀ������";
				arrList1.add(arr);
				applyList1.setModel(new ListModelList(arrList1));
			}
		});
		try {
			w.doModal();
		} catch (SuspendNotAllowedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <li>�����������ĵ���Ϣ��listbox(20120305)
	 */
	public class FilesRenderer1 implements ListitemRenderer {

		@Override
		public void render(Listitem arg0, Object arg1) throws Exception {
			final String[] str = (String[]) arg1;
			arg0.setValue(str);
			Listcell c1 = new Listcell(arg0.getIndex() + 1 + "");
			Listcell c2 = new Listcell(str[1]);
			Listcell c3 = new Listcell(str[2]);
			Listcell c4 = new Listcell();
			Toolbarbutton downlowd = new Toolbarbutton();
			downlowd.setImage("/css/default/images/button/down.gif");
			downlowd.setParent(c4);
			downlowd.addEventListener(Events.ON_CLICK, new EventListener() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					download(str[0], str[1]);
				}
			});
			Toolbarbutton del = new Toolbarbutton();
			del.setImage("/css/default/images/button/del.gif");
			del.setParent(c4);
			del.addEventListener(Events.ON_CLICK, new EventListener() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					Messagebox.show("ȷ��ɾ����?", "ȷ��", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
						public void onEvent(Event evt) throws InterruptedException {
							if (evt.getName().equals("onOK")) {
								arrList1.remove(str);
								applyList1.setModel(new ListModelList(arrList1));
							}
						}
					});
				}
			});
			arg0.appendChild(c1);
			arg0.appendChild(c2);
			arg0.appendChild(c3);
			arg0.appendChild(c4);
		}
	}

	// 20120305
	private List<String[]> arrList2 = new ArrayList<String[]>();

	public void onClick$ups2() {
		final UpfileWindow w = (UpfileWindow) Executions.createComponents("/admin/jxkh/busiAudit/meeting/upfile.zul", null, null);
		w.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				String filePath = (String) Executions.getCurrent().getAttribute("path");
				String fileName = (String) Executions.getCurrent().getAttribute("title");
				String upTime = (String) Executions.getCurrent().getAttribute("upTime");
				applyList2.setItemRenderer(new FilesRenderer2());
				String[] arr = new String[4];
				arr[0] = filePath;
				arr[1] = fileName;
				arr[2] = upTime;
				arr[3] = "��Ŀ��ͬ��";
				arrList2.add(arr);
				applyList2.setModel(new ListModelList(arrList2));
			}
		});
		try {
			w.doModal();
		} catch (SuspendNotAllowedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <li>�����������ĵ���Ϣ��listbox(20120305)
	 */
	public class FilesRenderer2 implements ListitemRenderer {

		@Override
		public void render(Listitem arg0, Object arg1) throws Exception {
			final String[] str = (String[]) arg1;
			arg0.setValue(str);
			Listcell c1 = new Listcell(arg0.getIndex() + 1 + "");
			Listcell c2 = new Listcell(str[1]);
			Listcell c3 = new Listcell(str[2]);
			Listcell c4 = new Listcell();
			Toolbarbutton downlowd = new Toolbarbutton();
			downlowd.setImage("/css/default/images/button/down.gif");
			downlowd.setParent(c4);
			downlowd.addEventListener(Events.ON_CLICK, new EventListener() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					download(str[0], str[1]);
				}
			});
			Toolbarbutton del = new Toolbarbutton();
			del.setImage("/css/default/images/button/del.gif");
			del.setParent(c4);
			del.addEventListener(Events.ON_CLICK, new EventListener() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					Messagebox.show("ȷ��ɾ����?", "ȷ��", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
						public void onEvent(Event evt) throws InterruptedException {
							if (evt.getName().equals("onOK")) {
								arrList2.remove(str);
								applyList2.setModel(new ListModelList(arrList2));
							}
						}
					});
				}
			});
			arg0.appendChild(c1);
			arg0.appendChild(c2);
			arg0.appendChild(c3);
			arg0.appendChild(c4);
		}
	}

	// 20120305
	private List<String[]> arrList3 = new ArrayList<String[]>();

	public void onClick$ups3() {
		final UpfileWindow w = (UpfileWindow) Executions.createComponents("/admin/jxkh/busiAudit/meeting/upfile.zul", null, null);
		w.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				String filePath = (String) Executions.getCurrent().getAttribute("path");
				String fileName = (String) Executions.getCurrent().getAttribute("title");
				String upTime = (String) Executions.getCurrent().getAttribute("upTime");
				applyList3.setItemRenderer(new FilesRenderer3());
				String[] arr = new String[4];
				arr[0] = filePath;
				arr[1] = fileName;
				arr[2] = upTime;
				arr[3] = "��Ƚ����ĵ�";
				arrList3.add(arr);
				applyList3.setModel(new ListModelList(arrList3));
			}
		});
		try {
			w.doModal();
		} catch (SuspendNotAllowedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <li>�����������ĵ���Ϣ��listbox(20120305)
	 */
	public class FilesRenderer3 implements ListitemRenderer {

		@Override
		public void render(Listitem arg0, Object arg1) throws Exception {
			final String[] str = (String[]) arg1;
			arg0.setValue(str);
			Listcell c1 = new Listcell(arg0.getIndex() + 1 + "");
			Listcell c2 = new Listcell(str[1]);
			Listcell c3 = new Listcell(str[2]);
			Listcell c4 = new Listcell();
			Toolbarbutton downlowd = new Toolbarbutton();
			downlowd.setImage("/css/default/images/button/down.gif");
			downlowd.setParent(c4);
			downlowd.addEventListener(Events.ON_CLICK, new EventListener() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					download(str[0], str[1]);
				}
			});
			Toolbarbutton del = new Toolbarbutton();
			del.setImage("/css/default/images/button/del.gif");
			del.setParent(c4);
			del.addEventListener(Events.ON_CLICK, new EventListener() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					Messagebox.show("ȷ��ɾ����?", "ȷ��", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
						public void onEvent(Event evt) throws InterruptedException {
							if (evt.getName().equals("onOK")) {
								arrList3.remove(str);
								applyList3.setModel(new ListModelList(arrList3));
							}
						}
					});
				}
			});
			arg0.appendChild(c1);
			arg0.appendChild(c2);
			arg0.appendChild(c3);
			arg0.appendChild(c4);
		}
	}

	// 20120305
	private List<String[]> arrList4 = new ArrayList<String[]>();

//	public void onClick$ups4() {
//		final UpfileWindow w = (UpfileWindow) Executions.createComponents("/admin/jxkh/busiAudit/meeting/upfile.zul", null, null);
//		w.addEventListener(Events.ON_CHANGE, new EventListener() {
//			public void onEvent(Event arg0) throws Exception {
//				String filePath = (String) Executions.getCurrent().getAttribute("path");
//				String fileName = (String) Executions.getCurrent().getAttribute("title");
//				String upTime = (String) Executions.getCurrent().getAttribute("upTime");
//				applyList4.setItemRenderer(new FilesRenderer4());
//				String[] arr = new String[4];
//				arr[0] = filePath;
//				arr[1] = fileName;
//				arr[2] = upTime;
//				arr[3] = "��Ŀ����֤��";
//				arrList4.add(arr);
//				applyList4.setModel(new ListModelList(arrList4));
//			}
//		});
//		try {
//			w.doModal();
//		} catch (SuspendNotAllowedException e) {
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * <li>�����������ĵ���Ϣ��listbox(20120305)
//	 */
//	public class FilesRenderer4 implements ListitemRenderer {
//
//		@Override
//		public void render(Listitem arg0, Object arg1) throws Exception {
//			final String[] str = (String[]) arg1;
//			arg0.setValue(str);
//			Listcell c1 = new Listcell(arg0.getIndex() + 1 + "");
//			Listcell c2 = new Listcell(str[1]);
//			Listcell c3 = new Listcell(str[2]);
//			Listcell c4 = new Listcell();
//			Toolbarbutton downlowd = new Toolbarbutton();
//			downlowd.setImage("/css/default/images/button/down.gif");
//			downlowd.setParent(c4);
//			downlowd.addEventListener(Events.ON_CLICK, new EventListener() {
//				@Override
//				public void onEvent(Event arg0) throws Exception {
//					download(str[0], str[1]);
//				}
//			});
//			Toolbarbutton del = new Toolbarbutton();
//			del.setImage("/css/default/images/button/del.gif");
//			del.setParent(c4);
//			del.addEventListener(Events.ON_CLICK, new EventListener() {
//				@Override
//				public void onEvent(Event arg0) throws Exception {
//					Messagebox.show("ȷ��ɾ����?", "ȷ��", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
//						public void onEvent(Event evt) throws InterruptedException {
//							if (evt.getName().equals("onOK")) {
//								arrList4.remove(str);
//								applyList4.setModel(new ListModelList(arrList4));
//							}
//						}
//					});
//				}
//			});
//			arg0.appendChild(c1);
//			arg0.appendChild(c2);
//			arg0.appendChild(c3);
//			arg0.appendChild(c4);
//		}
//	}

	public void download(String fpath, String fname) throws InterruptedException {
		File file = new File(UploadUtil.getRootPath() + fpath);
		if (file.exists()) {
			try {
				Filedownload.save(file, fname);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			Messagebox.show("�޷����أ���������Ϊ�ļ�û�б��ϴ��� ", "����", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/** ���˼�����Ⱦ�� */
	public class personScoreRenderer implements ListitemRenderer {

		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			final Jxkh_ProjectMember member = (Jxkh_ProjectMember) data;
			item.setValue(member);
			Listcell c1 = new Listcell(item.getIndex() + 1 + "");
			Listcell c2 = new Listcell(member.getName());
			Listcell c3 = new Listcell();
			// System.out.println(member.getType().equals(1));
			// System.out.println("22222222222" + member.getType());
			// System.out.println(member.getType() == 1);
			if (member.getType() == 1) {
				c3 = new Listcell("Ժ��");
			} else {
				c3 = new Listcell("Ժ��");
			}
			Listcell c4 = new Listcell();
			if (member.getType() == 1) {
				c4 = new Listcell("Ժ��");
			} else {
				c4 = new Listcell(member.getDept());
			}
			Listcell c5 = new Listcell();
			c5.setTooltiptext("����������");
			Textbox t = new Textbox();
			t.setParent(c5);
			if (member.getPer() != null)
				t.setValue(member.getPer() + "");
			Listcell c6 = new Listcell();
			Toolbarbutton bar = new Toolbarbutton();
			if (member.getAssignDep() == null || member.getAssignDep().equals("")) {
				bar.setLabel("ָ��");
				bar.setStyle("color:blue");
			} else {
				bar.setLabel(member.getAssignDep());
			}
			c6.appendChild(bar);
			Listcell c7 = new Listcell();
			if (member.getScore() != null)
				c7.setLabel(member.getScore() + "");

			final List<Jxkh_ProjectDept> temp = jxkhProjectService.findProjectDep(project);
			// ����ָ������ҳ��
			bar.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event event) throws Exception {

					AssignDeptWindow w = (AssignDeptWindow) Executions.createComponents("/admin/personal/businessdata/meeting/assignDept.zul", null, null);
					try {
						w.setFlag("zp");
						w.setState(project.getState());
						w.setDept(temp);
						w.setMember(member);
						w.initWindow();
						w.doModal();

						// ָ��������ɺ��Զ�����÷�
						w.addEventListener(Events.ON_CHANGE, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								List<Jxkh_ProjectMember> tempMemberList = jxkhProjectService.findProjectMember(project);
								personScore.setModel(new ListModelList(tempMemberList));
								for (int k = 0; k < projectDeptList.size(); k++) {
									Jxkh_ProjectDept d = projectDeptList.get(k);
									float f = 0.0f;
									for (int i = 0; i < tempMemberList.size(); i++) {
										Jxkh_ProjectMember m = tempMemberList.get(i);
										if (m.getAssignDep() == null || m.getAssignDep().equals("")) {
											if (m.getDept().equals(d.getName())) {
												f += m.getScore();
											}
										} else if (m.getAssignDep().equals(d.getName())) {
											f += m.getScore();
										}
									}
									d.setScore(f);
									jxkhMeetingService.update(d);
								}
								List<Jxkh_ProjectDept> tempDeptList = jxkhProjectService.findProjectDept(project);
								departmentScore.setModel(new ListModelList(tempDeptList));
							}
						});
					} catch (SuspendNotAllowedException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
			item.appendChild(c1);
			item.appendChild(c2);
			item.appendChild(c3);
			item.appendChild(c4);
			item.appendChild(c5);
			// item.appendChild(c6);
			item.appendChild(c7);
		}
	}

	/** ���ż����б���Ⱦ�� */
	public class departmentScoreRenderer implements ListitemRenderer {

		@Override
		public void render(Listitem arg0, Object arg1) throws Exception {
			if (arg0 == null)
				return;
			final Jxkh_ProjectDept dept = (Jxkh_ProjectDept) arg1;
			arg0.setValue(dept);
			Listcell c1 = new Listcell(arg0.getIndex() + 1 + "");
			Listcell c2 = new Listcell(dept.getName());
			Listcell c3 = new Listcell();
			if (dept.getScore() != null)
				c3.setLabel(dept.getScore() + "");
			arg0.appendChild(c1);
			arg0.appendChild(c2);
			arg0.appendChild(c3);
		}
	}

	// ������Ϣ�ı��水ť
	public void onClick$submitScore() {
		float a = 0.0f;
		for (int i = 0; i < projectMemberList.size(); i++) {
			Listitem item = personScore.getItemAtIndex(i);
			Listcell lc = (Listcell) item.getChildren().get(4);
			Textbox temp = (Textbox) lc.getChildren().get(0);
			if (temp.getValue() != null && temp.getValue() != "")
				try {
					a += Float.parseFloat(temp.getValue());
				} catch (Exception e) {
					e.printStackTrace();
					try {
						Messagebox.show("ֻ���������֣�", "��ʾ", Messagebox.OK, Messagebox.EXCLAMATION);
						return;
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}

		}
		if (a != 10.0) {
			try {
				Messagebox.show("������Ա��ռ�������ܺ��Ƿ�Ϊ10.0��", "��ʾ", Messagebox.OK, Messagebox.EXCLAMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		for (int i = 0; i < projectMemberList.size(); i++) {
			Listitem item = personScore.getItemAtIndex(i);
			Listcell lc = (Listcell) item.getChildren().get(4);
			Textbox temp = (Textbox) lc.getChildren().get(0);
			float s = 0.0f;// ����
			float f = 0.0f;// ��ֵ
			if (temp.getValue() != null && temp.getValue() != "")
				s = Float.parseFloat(temp.getValue());
			if (project.getScore() != null)
				f = s * project.getScore() / 10;
			float score = (float) (Math.round(f * 1000)) / 1000;
			Jxkh_ProjectMember member = (Jxkh_ProjectMember) item.getValue();
			member.setPer(s);
			member.setScore(score);
			jxkhProjectService.update(member);
		}
		List<Jxkh_ProjectMember> tempMemberList = jxkhProjectService.findProjectMember(project);
		personScore.setModel(new ListModelList(tempMemberList));
		/*for (int k = 0; k < projectDeptList.size(); k++) {
			Jxkh_ProjectDept d = projectDeptList.get(k);
			float f = 0.0f;
			for (int i = 0; i < tempMemberList.size(); i++) {
				Jxkh_ProjectMember m = tempMemberList.get(i);
				if (m.getAssignDep() == null || m.getAssignDep().equals("")) {
					if (m.getDept().equals(d.getName())) {
						f += m.getScore();
					}
				} else if (m.getAssignDep().equals(d.getName())) {
					f += m.getScore();
				}
			}
			d.setScore(f);
			jxkhProjectService.update(d);
		}*/
		try {
			Messagebox.show("������Ϣ����ɹ���", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Jxkh_ProjectDept> tempDeptList = jxkhProjectService.findProjectDept(project);
		departmentScore.setModel(new ListModelList(tempDeptList));
	}

	// ������Ϣ�Ĺرհ�ť
	public void onClick$closeScore() {
		this.onClose();
	}

//	public void onClick$submitFund() {
//		computeScore();
//		if (scoreTab.isDisabled()) {
//			scoreTab.setDisabled(false);
//		}
//		scoreTab.setSelected(true);
//		List<Jxkh_ProjectMember> mlist = jxkhProjectService.findProjectMember(project);
//		personScore.setModel(new ListModelList(mlist));
//		List<Jxkh_ProjectDept> dlist = jxkhProjectService.findProjectDep(project);
//		departmentScore.setModel(new ListModelList(dlist));
//	}
//
//	public void onClick$closeFund() {
//		this.detach();
//	}
	
	public void onClick$fileSubmit() {
		// �µĸ�������20120306
		if (arrList1.size() <= 0)
			throw new WrongValueException(ups1,"��Ŀ����������ϴ�");
		if (arrList2.size() <= 0)
			throw new WrongValueException(ups2,"��Ŀ��ͬ������ϴ�");
		List<String[]> arrList = new ArrayList<String[]>();
		Set<Jxkh_ProjectFile> fset=new HashSet<Jxkh_ProjectFile>();
		if (project.getProjectFile() != null) {
			for (Object o : project.getProjectFile().toArray()) {
				Jxkh_ProjectFile f = (Jxkh_ProjectFile) o;
				if (f != null)
					jxkhProjectService.delete(f);
			}
		}
		if(project.getProjectFile() != null)
			project.getProjectFile().clear();
		if (arrList1 != null && arrList1.size() != 0)
			arrList.addAll(arrList1);
		if (arrList2 != null && arrList2.size() != 0)
			arrList.addAll(arrList2);
		if (arrList3 != null && arrList3.size() != 0)
			arrList.addAll(arrList3);
		if (arrList4 != null && arrList4.size() != 0)
			arrList.addAll(arrList4);
		for (int r = 0; r < arrList.size(); r++) {
			String[] s = arrList.get(r);
			Jxkh_ProjectFile file = new Jxkh_ProjectFile();
			file.setProject(project);
			file.setPath(s[0]);
			file.setName(s[1]);
			file.setSubmitDate(s[2]);
			file.setBelongType(s[3]);
			fset.add(file);
		}
		if (fset != null && fset.size() > 0) {
			project.setProjectFile(fset);
			try {
				jxkhProjectService.update(project);
				Messagebox.show("����ɹ���");
			} catch (InterruptedException e) {
				try {
					Messagebox.show("����ʧ�ܣ�");
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		} else {
			try {
				Messagebox.show("���ϴ��ļ�");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void onClick$fileClose() {
		this.detach();
	}
}