package org.iti.jxkh.business.writing;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxkh.business.award.ChooseDeptWin;
import org.iti.jxkh.business.award.ChooseMemberWin;
import org.iti.jxkh.business.meeting.AssignDeptWindow;
import org.iti.jxkh.business.meeting.UpfileWindow;
import org.iti.jxkh.entity.JXKH_MEETING;
import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.entity.Jxkh_Project;
import org.iti.jxkh.entity.Jxkh_Writer;
import org.iti.jxkh.entity.Jxkh_Writing;
import org.iti.jxkh.entity.Jxkh_WritingDept;
import org.iti.jxkh.entity.Jxkh_WritingFile;
import org.iti.jxkh.service.BusinessIndicatorService;
import org.iti.jxkh.service.JXKHMeetingService;
import org.iti.jxkh.service.JxkhProjectService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Groupbox;
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

public class AddWritingWindow extends Window implements AfterCompose {

	/**
	 * @author SongYu
	 */
	private static final long serialVersionUID = -3664613977051724505L;
	private Tab baseTab, fileTab, scoreTab;
	private Listbox personScore, departmentScore;
	private Toolbarbutton tempSave, submit, close, submitScore, ups1, ups2, ups3/*, ups4, ups5*/;
	WkTUser user;
	private JxkhProjectService jxkhProjectService;
	private JXKHMeetingService jxkhMeetingService;
	private Textbox projectName, projectMember, department, coUnit, header, memProp, contentProp;
	private Label writer;
	//private Groupbox identGroupbox;
	private YearListbox jiFenTime;
	private Datebox end;
	private Listbox rank, computMethod;
	private Radiogroup ifCoo, ifPub;
	private Row coUnitRow, memRow, contentRow;
	private Button print;
	private Jxkh_Writing project;
	private String dept = "";
	private String dept1 = "";
	private Radio firstSignTrue, firstSignFalse;
	private List<WkTUser> memberList = new ArrayList<WkTUser>();
	private List<WkTDept> deptList = new ArrayList<WkTDept>();
	private List<Jxkh_Writer> projectMemberList = new ArrayList<Jxkh_Writer>();
	private List<Jxkh_WritingDept> projectDeptList = new ArrayList<Jxkh_WritingDept>();
	private BusinessIndicatorService businessIndicatorService;

	private Listbox applyList1, applyList2, applyList3/* , applyList4, applyList5 */;
	private Set<Jxkh_WritingFile> fileList1;
	private String detail;// ���ڱ�ʶ����Ŀ������Ա�����ǲ�����Ϣ��д��

	private String audit;// �����ֵ���ͱ�ʾ�ǲ�����˵��������̨���䱣�水ť������

	public void setAudit(String audit) {
		this.audit = audit;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");// ��ȡ��ǰ��¼�û�
		jiFenTime.initYearListbox("");
		writer.setValue(user.getKuName());
		personScore.setItemRenderer(new personScoreRenderer());
		departmentScore.setItemRenderer(new departmentScoreRenderer());
		rank.setItemRenderer(new RankRenderer());
		List<Jxkh_BusinessIndicator> typeList = new ArrayList<Jxkh_BusinessIndicator>();
		typeList.add(new Jxkh_BusinessIndicator());
		typeList.addAll(jxkhProjectService.findWSort());
		rank.setModel(new ListModelList(typeList));
		/*rank.addEventListener(Events.ON_SELECT, new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {// 20120416
				Jxkh_BusinessIndicator s = (Jxkh_BusinessIndicator) rank.getSelectedItem().getValue();
				if (s.getKbName().equals("ʡ��Ժ���"))
					identGroupbox.setVisible(true);
				else
					identGroupbox.setVisible(false);
			}
		});*/
		// fileListbox1.setItemRenderer(new FileRenderer());
		// fileListbox2.setItemRenderer(new FileRenderer());
		// fileListbox3.setItemRenderer(new FileRenderer());
		// fileListbox4.setItemRenderer(new FileRenderer());
		// fileListbox5.setItemRenderer(new FileRenderer());
		computMethod.setItemRenderer(new methodRenderer());
		String[] s = { "--��ѡ��--", "Ĭ�ϱ���", "�α���Ա����", "��д���ݱ���" };
		List<String> methodList = new ArrayList<String>();
		for (int i = 0; i < 4; i++) {
			methodList.add(s[i]);
		}
		computMethod.setModel(new ListModelList(methodList));
		computMethod.setSelectedIndex(0);
		computMethod.addEventListener(Events.ON_SELECT, new EventListener() {

			@Override
			public void onEvent(Event arg0) throws Exception {

				String way = (String) computMethod.getSelectedItem().getValue();
				if (way.equals("--��ѡ��--")) {
					memRow.setVisible(false);
					contentRow.setVisible(false);
				}
				if (way.equals("Ĭ�ϱ���")) {
					memRow.setVisible(false);
					contentRow.setVisible(false);
				}
				if (way.equals("�α���Ա����")) {
					memRow.setVisible(true);
					contentRow.setVisible(false);
				}
				if (way.equals("��д���ݱ���")) {
					memRow.setVisible(false);
					contentRow.setVisible(true);
				}
			}
		});
		// �������ҳ��ʱ������Ϣҳ����ĵ���Ϣҳ��ı�����˳���ť����
		scoreTab.addEventListener(Events.ON_CLICK, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				submit.setVisible(false);
				tempSave.setVisible(false);
				close.setVisible(false);
				if (project != null) {

					if (dept1 == "dept" || dept == "dept") {// ������ӻ��߲��ű༭_
															// �жϵ�ǰ��¼��Ա�Ƿ�����Ϣ��д�˵Ĳ��Ÿ�����
						List<WkTUser> wktUser = jxkhMeetingService.findWkTUserByManId(project.getInfoWriter());
						WkTUser u = wktUser.get(0);
						Jxkh_WritingDept d = jxkhProjectService.findWritingDept(project, user.getDept().getKdNumber());
						if (user.getDept().getKdNumber().equals(u.getDept().getKdNumber()) || d.getRank() == 1) {
							if (project.getState() == null || project.getState() == 0 || project.getState() == 3 || project.getState() == 5) {
								submitScore.setVisible(true);
							} else {
								submitScore.setVisible(false);
							}
						} else
							submitScore.setVisible(false);
					} else {// ����ҵ��
						if (detail == "Detail")
							submitScore.setVisible(false);
					}
				}
				if (audit == "AUDIT")
					submitScore.setVisible(false);
			}
		});
		// ���������Ϣҳ����ĵ���Ϣҳ��ʱ�������״̬�����Ʊ�����˳���ť��������
		baseTab.addEventListener(Events.ON_CLICK, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				submit.setVisible(true);
				tempSave.setVisible(true);
				close.setVisible(true);
				if (project != null) {
					if (project.getState() == null || project.getState() == 0 || project.getState() == 3 || project.getState() == 5 || project.getState() == 8) {
						submit.setVisible(true);
						tempSave.setVisible(true);
					} else {
						submit.setVisible(false);
						tempSave.setVisible(false);
					}
					if (dept1 == "dept" || dept == "dept") {// ������ӻ��߲��ű༭_
															// �жϵ�ǰ��¼��Ա�Ƿ�����Ϣ��д�˵Ĳ��Ÿ�����
						List<WkTUser> wktUser = jxkhMeetingService.findWkTUserByManId(project.getInfoWriter());
						WkTUser u = wktUser.get(0);
						if (!user.getDept().getKdNumber().equals(u.getDept().getKdNumber())) {
							submit.setVisible(false);
							tempSave.setVisible(false);
						}
					} else {// ����ҵ��
						if (detail == "Detail") {
							submit.setVisible(false);
							tempSave.setVisible(false);
						}
					}
				}
				if (audit == "AUDIT") {
					submit.setVisible(false);
					tempSave.setVisible(false);
				}
			}
		});
		fileTab.addEventListener(Events.ON_CLICK, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				submit.setVisible(true);
				tempSave.setVisible(true);
				close.setVisible(true);
				if (project != null) {
					if (project.getState() == null || project.getState() == 0 || project.getState() == 3 || project.getState() == 5 || project.getState() == 8) {
						submit.setVisible(true);
						tempSave.setVisible(true);
					} else {
						submit.setVisible(false);
						tempSave.setVisible(false);
					}
					if (dept1 == "dept" || dept == "dept") {// ������ӻ��߲��ű༭_
															// �жϵ�ǰ��¼��Ա�Ƿ�����Ϣ��д�˵Ĳ��Ÿ�����
						List<WkTUser> wktUser = jxkhMeetingService.findWkTUserByManId(project.getInfoWriter());
						WkTUser u = wktUser.get(0);
						if (!user.getDept().getKdNumber().equals(u.getDept().getKdNumber())) {
							submit.setVisible(false);
							tempSave.setVisible(false);
						}
					} else {// ����ҵ��
						if (detail == "Detail") {
							submit.setVisible(false);

						}

					}
				}
				if (audit == "AUDIT") {
					submit.setVisible(false);
					tempSave.setVisible(false);
				}
			}
		});
	}

	public void initWindow() {
		// �ж������δ���״̬���ݴ水ť����ʾ
		if (project.getState() == null || project.getState() == JXKH_MEETING.WRITING || project.getState() == Jxkh_Project.BUSINESS_NOT_PASS || project.getState() == Jxkh_Project.DEPT_NOT_PASS)
			tempSave.setVisible(true);
		else
			tempSave.setVisible(false);
		if (project.getState() == Jxkh_Writing.NOT_AUDIT || project.getState() == Jxkh_Writing.DEPT_NOT_PASS || project.getState() == Jxkh_Writing.BUSINESS_NOT_PASS || project.getState() == JXKH_MEETING.WRITING) {
			submit.setVisible(true);
			ups1.setDisabled(false);
			ups2.setDisabled(false);
			ups3.setDisabled(false);
//			ups4.setDisabled(false);
//			ups5.setDisabled(false);
		} else {
			submit.setVisible(false);
			ups1.setDisabled(true);
			ups2.setDisabled(true);
			ups3.setDisabled(true);
//			ups5.setDisabled(true);
		}
		if (dept1 == "dept" || dept == "dept") {// ������ӻ��߲��ű༭_
												// �жϵ�ǰ��¼��Ա�Ƿ�����Ϣ��д�˵Ĳ��Ÿ�����
			List<WkTUser> wktUser = jxkhMeetingService.findWkTUserByManId(project.getInfoWriter());
			WkTUser u = wktUser.get(0);
			if (!user.getDept().getKdNumber().equals(u.getDept().getKdNumber())) {
				submit.setVisible(false);
				tempSave.setVisible(false);
				ups1.setDisabled(true);
				ups2.setDisabled(true);
				ups3.setDisabled(true);
//				ups4.setDisabled(true);
//				ups5.setDisabled(true);
			}
		} else {// ����ҵ��
			if (detail == "Detail") {
				submit.setVisible(false);
				tempSave.setVisible(false);
				ups1.setDisabled(true);
				ups2.setDisabled(true);
				ups3.setDisabled(true);
//				ups4.setDisabled(true);
//				ups5.setDisabled(true);
			}
		}
		if (audit == "AUDIT") {
			submit.setVisible(false);
			tempSave.setVisible(false);
			ups1.setDisabled(true);
			ups2.setDisabled(true);
			ups3.setDisabled(true);
//			ups4.setDisabled(true);
//			ups5.setDisabled(true);
		}
		projectName.setValue(project.getName());
		WkTUser infoWriter = jxkhProjectService.findWktUserByMemberUserId(project.getInfoWriter());
		writer.setValue(infoWriter.getKuName());
		header.setValue(project.getPublishName());

		if (project.getPublishDate() == null || project.getPublishDate().length() == 0) {
			end.setValue(null);
		} else {
			end.setValue(ConvertUtil.convertDate(project.getPublishDate()));
		}

		if (project.getCooState() != null) {
			if (project.getCooState() == Jxkh_Writing.YES) {
				ifCoo.setSelectedIndex(0);
				coUnitRow.setVisible(true);
				coUnit.setValue(project.getCooCompany());
			} else {
				ifCoo.setSelectedIndex(1);
			}
		}
		if (project.getFirstSign() != null) {
			if (project.getFirstSign() == Jxkh_Writing.YES) {
				firstSignTrue.setChecked(true);
				firstSignFalse.setChecked(false);
			} else {
				firstSignTrue.setChecked(false);
				firstSignFalse.setChecked(true);
			}
		}
		if (project.getIfPublish() != null) {
			if (project.getIfPublish() == Jxkh_Writing.YES) {
				ifPub.setSelectedIndex(0);
			} else {
				ifPub.setSelectedIndex(1);
			}
		}
		jiFenTime.initYearListbox(project.getjxYear());
		String memberName = "";
		String deptName = "";
		// ��Ա ����
		projectMemberList = jxkhProjectService.findWritingMember(project);
		if (projectMemberList != null && projectMemberList.size() != 0) {
			for (int i = 0; i < projectMemberList.size(); i++) {
				Jxkh_Writer mem = (Jxkh_Writer) projectMemberList.get(i);
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

				// WkTUser user =
				// jxkhProjectService.findWktUserByMemberUserId(mem
				// .getPersonId());
				// memberList.add(user);
			}
			projectMember.setValue(memberName.substring(0, memberName.length() - 1));
		}
		projectDeptList = jxkhProjectService.findWritingDept(project);
		if (projectDeptList != null && projectDeptList.size() != 0) {
			for (int i = 0; i < projectDeptList.size(); i++) {
				Jxkh_WritingDept dept = (Jxkh_WritingDept) projectDeptList.get(i);
				deptName += dept.getName() + "��";
				WkTDept depts = jxkhProjectService.findWkTDeptByDept(dept.getDeptId());
				deptList.add(depts);
			}
			department.setValue(deptName.substring(0, deptName.length() - 1));
		}
		// �µĸ�����ʼ��20120305
		arrList1.clear();
		arrList2.clear();
		arrList3.clear();
		// arrList4.clear();
		// arrList5.clear();
		fileList1 = project.getWritingFile();
		Object[] file = fileList1.toArray();
		for (int j = 0; j < file.length; j++) {
			Jxkh_WritingFile f = (Jxkh_WritingFile) file[j];

			if (f.getBelongType().equals("����")) {
				String[] arr = new String[4];
				arr[0] = f.getPath();
				arr[1] = f.getName();
				arr[2] = f.getSubmitDate();
				arr[3] = f.getBelongType();
				arrList1.add(arr);
			}
			if (f.getBelongType().equals("����֤��")) {
				String[] arr = new String[4];
				arr[0] = f.getPath();
				arr[1] = f.getName();
				arr[2] = f.getSubmitDate();
				arr[3] = f.getBelongType();
				arrList2.add(arr);
			}
			if (f.getBelongType().equals("���ݵ�һҳ")) {
				String[] arr = new String[4];
				arr[0] = f.getPath();
				arr[1] = f.getName();
				arr[2] = f.getSubmitDate();
				arr[3] = f.getBelongType();
				arrList3.add(arr);
			}
			// if (f.getBelongType().equals("�������һҳ")) {
			// String[] arr = new String[4];
			// arr[0] = f.getPath();
			// arr[1] = f.getName();
			// arr[2] = f.getSubmitDate();
			// arr[3] = f.getBelongType();
			// arrList4.add(arr);
			// }
			// if (f.getBelongType().equals("�������쵼��Ժ�쵼ȷ�ϵ�֤��")) {
			// String[] arr = new String[4];
			// arr[0] = f.getPath();
			// arr[1] = f.getName();
			// arr[2] = f.getSubmitDate();
			// arr[3] = f.getBelongType();
			// arrList5.add(arr);
			// }
		}
		applyList1.setItemRenderer(new FilesRenderer1());
		applyList1.setModel(new ListModelList(arrList1));
		applyList2.setItemRenderer(new FilesRenderer2());
		applyList2.setModel(new ListModelList(arrList2));
		applyList3.setItemRenderer(new FilesRenderer3());
		applyList3.setModel(new ListModelList(arrList3));
		// applyList4.setItemRenderer(new FilesRenderer4());
		// applyList4.setModel(new ListModelList(arrList4));
		// applyList5.setItemRenderer(new FilesRenderer5());
		// applyList5.setModel(new ListModelList(arrList5));

		// 20120416
		String way = project.getScoreWay();
		if (way.equals("--��ѡ��--")) {
			memRow.setVisible(false);
			contentRow.setVisible(false);
		}
		if (way.equals("Ĭ�ϱ���")) {
			memRow.setVisible(false);
			contentRow.setVisible(false);
		}
		if (way.equals("�α���Ա����")) {
			memRow.setVisible(true);
			contentRow.setVisible(false);
			memProp.setValue(project.getMemPercent());
		}
		if (way.equals("��д���ݱ���")) {
			memRow.setVisible(false);
			contentRow.setVisible(true);
			contentProp.setValue(project.getContentPercent());
		}
		/*if (project.getSort().getKbName().equals("ʡ��Ժ���"))
			identGroupbox.setVisible(true);*/

		personScore.setModel(new ListModelList(projectMemberList));
		departmentScore.setModel(new ListModelList(projectDeptList));
	}

	public void initShow() {
		if (project == null) {
			print.setVisible(false);
			scoreTab.setDisabled(true);
			fileTab.setDisabled(true);
		} else {
			scoreTab.setDisabled(false);
			fileTab.setDisabled(false);
			print.setHref("/print.action?n=writing&id=" + project.getId());
		}
	}


	/**
	 * <li>��������������������Ŀ�� void
	 * 
	 * @author songyu
	 * @throws InterruptedException
	 */
	public void onClick$submit() throws InterruptedException {
		if (projectName.getValue().equals("")) {
			Messagebox.show("�������Ʋ���Ϊ�գ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			projectName.focus();
			return;
		}
		if (rank.getSelectedItem() == null || rank.getSelectedItem().getIndex() == 0) {
			Messagebox.show("�������Ͳ���Ϊ�գ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
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
		if (jiFenTime.getSelectedItem() == null || jiFenTime.getSelectedItem().getIndex() == 0) {
			Messagebox.show("��ѡ�񼨷���ȣ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		if (computMethod.getSelectedItem() == null || computMethod.getSelectedItem().getIndex() == 0) {
			Messagebox.show("���ַ�ʽ����Ϊ�գ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}

		// �ж��Ƿ��ǳ�Ա�����ǳ�Ա���ܽ���¼��
		if (dept1 == "dept" || dept == "dept") {// ������ӻ��߲��ű༭
		} else {
			String member1 = "";
			int t = 0;
			for (WkTUser user : memberList) {
				member1 = user.getKuName();
				if (this.user.getKuName().equals(member1)) {
					t = 1;
				}
			}
			if (t == 0) {
				try {
					Messagebox.show("������ȫ�������е�һԱ�����ܽ���¼�룡", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return;
			}
		}
		if (project != null && project.getId() != null) {
			if (arrList1.size() <= 0) {
				Messagebox.show("��������ϴ���");
				return;
			}
			if (arrList2.size() <= 0) {
				Messagebox.show("׫д����֤�������ϴ���");
				return;
			}
			if (arrList3.size() <= 0) {
				Messagebox.show("׫д���ݱ����ϴ���");
				return;
			}
		}
		if (project == null) {
			project = new Jxkh_Writing();
			project.setInfoWriter(user.getKuLid());
			writer.setValue(user.getKuName());
			project.setSubmitTime(ConvertUtil.convertDateAndTimeString(new Date()));
		}

		Short sign;
		Jxkh_BusinessIndicator kdRank = new Jxkh_BusinessIndicator();

		project.setName(projectName.getValue());
		project.setCooCompany(coUnit.getValue());
		project.setPublishName(header.getValue());
		if (dept1 != "dept") {
			project.setState(Jxkh_Writing.NOT_AUDIT);
		} else {
			project.setState(Jxkh_Writing.NOT_AUDIT);// Jxkh_Writing.BUSINESS_PASS
		}
		project.setTempState("0000000");// Ϊ��ʱ״̬����ֵ
		project.setDep1Reason("");
		project.setDep2Reason("");
		project.setDep3Reason("");
		project.setDep4Reason("");
		project.setDep5Reason("");
		project.setDep6Reason("");
		project.setDep7Reason("");
		project.setbAdvice("");
		project.setjxYear(jiFenTime.getSelectedItem().getValue() + "");
		if (ifCoo.getSelectedIndex() == 0) {
			project.setCooState(Jxkh_Writing.YES);
		} else {
			project.setCooState(Jxkh_Writing.NO);
			firstSignTrue.setChecked(true);
		}
		if (firstSignTrue.isChecked()) {
			project.setFirstSign(Jxkh_Writing.YES);
			sign = Jxkh_Writing.YES;
		} else {
			project.setFirstSign(Jxkh_Writing.NO);
			sign = Jxkh_Writing.NO;
		}
		if (rank.getSelectedItem() != null) {
			project.setSort((Jxkh_BusinessIndicator) rank.getSelectedItem().getValue());
			kdRank = (Jxkh_BusinessIndicator) rank.getSelectedItem().getValue();
		}
		Float score = computeScore(kdRank, sign);
		project.setScore(score);

		if (ifPub.getSelectedIndex() == 0) {
			project.setIfPublish(Jxkh_Writing.YES);
		} else {
			project.setIfPublish(Jxkh_Writing.NO);
		}

		if (end.getValue() != null)
			project.setPublishDate(ConvertUtil.convertDateString(end.getValue()));

		int j = 1;
		List<Jxkh_Writer> mlist = new ArrayList<Jxkh_Writer>();
		List<Jxkh_WritingDept> dlist = new ArrayList<Jxkh_WritingDept>();
		if (project.getId() != null) {
			projectMemberList = jxkhProjectService.findWritingMember(project);
			if (projectMemberList != null && projectMemberList.size() != 0) {
				jxkhProjectService.deleteAll(projectMemberList);
			}
		}
		if (memberList != null && memberList.size() != 0) {
			for (WkTUser user : memberList) {
				Jxkh_Writer member = new Jxkh_Writer();
				member.setWriting(project);
				member.setDept(user.getDept().getKdName());
				member.setName(user.getKuName());
				member.setPersonId(user.getKuLid());
				member.setRank(j);
				switch (user.getType()) {
				case WkTUser.TYPE_IN:
					member.setType(Short.valueOf(WkTUser.TYPE_IN));
					break;
				case WkTUser.TYPE_OUT:
					member.setType(Short.valueOf(WkTUser.TYPE_OUT));
					break;
				}

				// ��ͬ���ַ�ʽ�����������α��������
				// ���÷�Ĭ�ϻ��ַ�ʽʱ�����˱���Ĭ��Ϊ�Ƽ�Ժ�α����/�α�����
				int membernumber = 0;
				int inmembernumber = 0;
				int outmembernumber = 0;
				for (int i = 0; i < memberList.size(); i++) {

					WkTUser ui = (WkTUser) memberList.get(i);
					if (ui.getDept().getKdName() != "У��") {
						inmembernumber++;
					} else {
						outmembernumber++;
					}
				}

				float oldpercent = 0f;
				if (computMethod.getSelectedItem().getValue().equals("�α���Ա����")) {
					project.setMemPercent(memProp.getValue());
					if (user.getDept().getKdName() == "У��") {
						membernumber = outmembernumber;
						oldpercent = 100f - Float.parseFloat(project.getMemPercent());
					} else {
						membernumber = inmembernumber;
						oldpercent = Float.parseFloat(project.getMemPercent());
					}
				} else if (computMethod.getSelectedItem().getValue().equals("��д���ݱ���")) {
					project.setContentPercent(contentProp.getValue());
					if (user.getDept().getKdName() == "У��") {
						membernumber = outmembernumber;
						oldpercent = 100f - Float.parseFloat(project.getContentPercent());
					} else {
						membernumber = inmembernumber;
						oldpercent = Float.parseFloat(project.getContentPercent());
					}
				}

				project.setScoreWay((String) computMethod.getSelectedItem().getValue());
				if (computMethod.getSelectedItem().getValue().equals("�α���Ա����")) {

					float number = score * oldpercent / 100;// �����˵÷�
					float sco = number / membernumber;// �����˾��÷�
					float percent = oldpercent / (membernumber * 10f);
					member.setPer(percent);
					member.setScore(sco);
				} else if (computMethod.getSelectedItem().getValue().equals("��д���ݱ���")) {

					float number = score * oldpercent / 100;
					float sco = number / membernumber;
					float percent = oldpercent / (membernumber * 10f);
					member.setPer(percent);
					member.setScore(sco);
				} else {
					// ��Ա�ı����ͷ�ֵ0503
					float percent = 0;
					int len = memberList.size();
					switch (len) {
					case 1:
						percent = 10;
						break;
					case 2:
						if (j == 1)
							percent = 7;
						else if (j == 2)
							percent = 3;
						break;
					case 3:
						if (j == 1)
							percent = 6;
						else if (j == 2)
							percent = 3;
						else if (j == 3)
							percent = 1;
						break;
					case 4:
						if (j == 1)
							percent = 5;
						else if (j == 2)
							percent = 3;
						else if (j == 3)
							percent = 1;
						else if (j == 4)
							percent = 1;
						break;
					case 5:
						if (j == 1)
							percent = 5;
						else if (j == 2)
							percent = 2;
						else if (j == 3)
							percent = 1;
						else if (j == 4)
							percent = 1;
						else if (j == 5)
							percent = 1;
						break;
					case 6:
						if (j == 1)
							percent = 5;
						else if (j == 2)
							percent = 2;
						else if (j == 3)
							percent = 1;
						else if (j == 4)
							percent = 1;
						else if (j == 5)
							percent = 0.5f;
						else if (j == 6)
							percent = 0.5f;
						break;
					case 7:
						if (j == 1)
							percent = 5;
						else if (j == 2)
							percent = 1.5f;
						else if (j == 3)
							percent = 1;
						else if (j == 4)
							percent = 1;
						else if (j == 5)
							percent = 0.5f;
						else if (j == 6)
							percent = 0.5f;
						else if (j == 7)
							percent = 0.5f;
						break;
					default:
						if (j == 1)
							percent = 5;
						else if (j == 2)
							percent = 1.5f;
						else if (j == 3)
							percent = 1;
						else if (j == 4)
							percent = 1;
						else if (j == 5)
							percent = 0.5f;
						else if (j == 6)
							percent = 0.5f;
						else if (j == 7)
							percent = 0.5f;
						else
							percent = 0;
						break;

					}
					float f = 0;
					if (percent != 0)
						f = score * percent / 10;
					float sco = (float) (Math.round(f * 1000)) / 1000;// ������λС��

					member.setPer(percent);
					member.setScore(sco);
				}
				j++;
				mlist.add(member);
			}
			project.setWriter(mlist);
		}
		int i = 1;
		if (project.getId() != null) {
			projectDeptList = jxkhProjectService.findWritingDept(project);
			if (projectDeptList != null && projectDeptList.size() != 0) {
				jxkhProjectService.deleteAll(projectDeptList);
			}
		}
		if (deptList != null && deptList.size() != 0) {
			for (WkTDept wktDept : deptList) {
				Jxkh_WritingDept dept = new Jxkh_WritingDept();
				dept.setWriting(project);
				dept.setDeptId(wktDept.getKdNumber());
				dept.setName(wktDept.getKdName());
				dept.setRank(i);
				i++;
				// ����Ĭ�ϵķ�ֵ0503
				float fen = 0.0f;
				for (int g = 0; g < mlist.size(); g++) {
					Jxkh_Writer m = mlist.get(g);
					if (m.getDept().equals(wktDept.getKdName())) {
						fen += m.getScore();
					}
				}
				float scor = (float) (Math.round(fen * 1000)) / 1000;// ������λС��
				dept.setScore(scor);
				dlist.add(dept);
			}
			project.setWritingDept(dlist);
		}
		// 20120416 ���Ƶ����˵÷ּ��㲿��
		// �µĸ�������20120306
		List<String[]> arrList = new ArrayList<String[]>();
		Set<Jxkh_WritingFile> fset = new HashSet<Jxkh_WritingFile>();
		if (project.getWritingFile() != null) {
			for (Object o : project.getWritingFile().toArray()) {
				Jxkh_WritingFile f = (Jxkh_WritingFile) o;
				if (f != null)
					jxkhProjectService.delete(f);
			}
			project.getWritingFile().clear();
		}
		if (arrList1 != null && arrList1.size() != 0)
			arrList.addAll(arrList1);
		if (arrList2 != null && arrList2.size() != 0)
			arrList.addAll(arrList2);
		if (arrList3 != null && arrList3.size() != 0)
			arrList.addAll(arrList3);
		// if (arrList4 != null && arrList4.size() != 0)
		// arrList.addAll(arrList4);
		// if (arrList5 != null && arrList5.size() != 0)
		// arrList.addAll(arrList5);
		for (int r = 0; r < arrList.size(); r++) {
			String[] s = arrList.get(r);
			Jxkh_WritingFile file = new Jxkh_WritingFile();
			file.setWriting(project);
			file.setPath(s[0]);
			file.setName(s[1]);
			file.setSubmitDate(s[2]);
			file.setBelongType(s[3]);
			fset.add(file);
		}
		project.setWritingFile(fset);

		try {
			jxkhProjectService.saveOrUpdate(project);
			Messagebox.show("�������ύ�����ţ������ĵȴ����Ÿ�������ˣ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			projectDeptList = jxkhProjectService.findWritingDept(project);
			scoreTab.setDisabled(false);
			fileTab.setDisabled(false);
			personScore.setModel(new ListModelList(mlist));
			departmentScore.setModel(new ListModelList(dlist));
		} catch (Exception e) {
			e.printStackTrace();
			try {
				Messagebox.show("��������ʧ�ܣ������ԣ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		initShow();
	}

	public void onClick$tempSave() throws InterruptedException {
		if (projectName.getValue().equals("")) {
			Messagebox.show("�������Ʋ���Ϊ�գ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			projectName.focus();
			return;
		}
		if (rank.getSelectedItem() == null || rank.getSelectedItem().getIndex() == 0) {
			Messagebox.show("�������Ͳ���Ϊ�գ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
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
		if (jiFenTime.getSelectedItem() == null || jiFenTime.getSelectedItem().getIndex() == 0) {
			Messagebox.show("��ѡ�񼨷���ȣ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		if (computMethod.getSelectedItem() == null || computMethod.getSelectedItem().getIndex() == 0) {
			Messagebox.show("���ַ�ʽ����Ϊ�գ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}

		// �ж��Ƿ��ǳ�Ա�����ǳ�Ա���ܽ���¼��
		if (dept1 == "dept" || dept == "dept") {// ������ӻ��߲��ű༭
		} else {
			String member1 = "";
			int t = 0;
			for (WkTUser user : memberList) {
				member1 = user.getKuName();
				if (this.user.getKuName().equals(member1)) {
					t = 1;
				}
			}
			if (t == 0) {
				try {
					Messagebox.show("������ȫ�������е�һԱ�����ܽ���¼�룡", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return;
			}
		}
		if (project != null && project.getId() != null) {
			if (arrList1.size() <= 0) {
				Messagebox.show("��������ϴ���");
				return;
			}
			if (arrList2.size() <= 0) {
				Messagebox.show("׫д����֤�������ϴ���");
				return;
			}
			if (arrList3.size() <= 0) {
				Messagebox.show("׫д���ݱ����ϴ���");
				return;
			}
		}
		if (project == null) {
			project = new Jxkh_Writing();
			project.setInfoWriter(user.getKuLid());
			writer.setValue(user.getKuName());
			project.setSubmitTime(ConvertUtil.convertDateAndTimeString(new Date()));
		}

		Short sign;
		Jxkh_BusinessIndicator kdRank = new Jxkh_BusinessIndicator();

		project.setName(projectName.getValue());
		project.setCooCompany(coUnit.getValue());
		project.setPublishName(header.getValue());
		if (dept1 != "dept") {
			project.setState(JXKH_MEETING.WRITING);
		} else {
			project.setState(JXKH_MEETING.WRITING);
		}
		project.setTempState("0000000");// Ϊ��ʱ״̬����ֵ
		project.setDep1Reason("");
		project.setDep2Reason("");
		project.setDep3Reason("");
		project.setDep4Reason("");
		project.setDep5Reason("");
		project.setDep6Reason("");
		project.setDep7Reason("");
		project.setbAdvice("");
		project.setjxYear(jiFenTime.getSelectedItem().getValue() + "");
		if (ifCoo.getSelectedIndex() == 0) {
			project.setCooState(Jxkh_Writing.YES);
		} else {
			project.setCooState(Jxkh_Writing.NO);
			firstSignTrue.setChecked(true);
		}
		if (firstSignTrue.isChecked()) {
			project.setFirstSign(Jxkh_Writing.YES);
			sign = Jxkh_Writing.YES;
		} else {
			project.setFirstSign(Jxkh_Writing.NO);
			sign = Jxkh_Writing.NO;
		}
		if (rank.getSelectedItem() != null) {
			project.setSort((Jxkh_BusinessIndicator) rank.getSelectedItem().getValue());
			kdRank = (Jxkh_BusinessIndicator) rank.getSelectedItem().getValue();
		}
		Float score = computeScore(kdRank, sign);
		project.setScore(score);

		if (ifPub.getSelectedIndex() == 0) {
			project.setIfPublish(Jxkh_Writing.YES);
		} else {
			project.setIfPublish(Jxkh_Writing.NO);
		}

		if (end.getValue() != null)
			project.setPublishDate(ConvertUtil.convertDateString(end.getValue()));

		int j = 1;
		List<Jxkh_Writer> mlist = new ArrayList<Jxkh_Writer>();
		List<Jxkh_WritingDept> dlist = new ArrayList<Jxkh_WritingDept>();
		if (project.getId() != null) {
			projectMemberList = jxkhProjectService.findWritingMember(project);
			if (projectMemberList != null && projectMemberList.size() != 0) {
				jxkhProjectService.deleteAll(projectMemberList);
			}
		}
		if (memberList != null && memberList.size() != 0) {
			for (WkTUser user : memberList) {
				Jxkh_Writer member = new Jxkh_Writer();
				member.setWriting(project);
				member.setDept(user.getDept().getKdName());
				member.setName(user.getKuName());
				member.setPersonId(user.getKuLid());
				member.setRank(j);
				switch (user.getType()) {
				case WkTUser.TYPE_IN:
					member.setType(Short.valueOf(WkTUser.TYPE_IN));
					break;
				case WkTUser.TYPE_OUT:
					member.setType(Short.valueOf(WkTUser.TYPE_OUT));
					break;
				}

				// ��ͬ���ַ�ʽ�����������α��������
				// ���÷�Ĭ�ϻ��ַ�ʽʱ�����˱���Ĭ��Ϊ�Ƽ�Ժ�α����/�α�����
				int membernumber = 0;
				int inmembernumber = 0;
				int outmembernumber = 0;
				for (int i = 0; i < memberList.size(); i++) {

					WkTUser ui = (WkTUser) memberList.get(i);
					if (ui.getDept().getKdName() != "У��") {
						inmembernumber++;
					} else {
						outmembernumber++;
					}
				}

				float oldpercent = 0f;
				if (computMethod.getSelectedItem().getValue().equals("�α���Ա����")) {
					project.setMemPercent(memProp.getValue());
					if (user.getDept().getKdName() == "У��") {
						membernumber = outmembernumber;
						oldpercent = 100f - Float.parseFloat(project.getMemPercent());
					} else {
						membernumber = inmembernumber;
						oldpercent = Float.parseFloat(project.getMemPercent());
					}
				} else if (computMethod.getSelectedItem().getValue().equals("��д���ݱ���")) {
					project.setContentPercent(contentProp.getValue());
					if (user.getDept().getKdName() == "У��") {
						membernumber = outmembernumber;
						oldpercent = 100f - Float.parseFloat(project.getContentPercent());
					} else {
						membernumber = inmembernumber;
						oldpercent = Float.parseFloat(project.getContentPercent());
					}
				}

				project.setScoreWay((String) computMethod.getSelectedItem().getValue());
				if (computMethod.getSelectedItem().getValue().equals("�α���Ա����")) {

					float number = score * oldpercent / 100;// �����˵÷�
					float sco = number / membernumber;// �����˾��÷�
					float percent = oldpercent / (membernumber * 10f);
					member.setPer(percent);
					member.setScore(sco);
				} else if (computMethod.getSelectedItem().getValue().equals("��д���ݱ���")) {

					float number = score * oldpercent / 100;
					float sco = number / membernumber;
					float percent = oldpercent / (membernumber * 10f);
					member.setPer(percent);
					member.setScore(sco);
				} else {
					// ��Ա�ı����ͷ�ֵ0503
					float percent = 0;
					int len = memberList.size();
					switch (len) {
					case 1:
						percent = 10;
						break;
					case 2:
						if (j == 1)
							percent = 7;
						else if (j == 2)
							percent = 3;
						break;
					case 3:
						if (j == 1)
							percent = 6;
						else if (j == 2)
							percent = 3;
						else if (j == 3)
							percent = 1;
						break;
					case 4:
						if (j == 1)
							percent = 5;
						else if (j == 2)
							percent = 3;
						else if (j == 3)
							percent = 1;
						else if (j == 4)
							percent = 1;
						break;
					case 5:
						if (j == 1)
							percent = 5;
						else if (j == 2)
							percent = 2;
						else if (j == 3)
							percent = 1;
						else if (j == 4)
							percent = 1;
						else if (j == 5)
							percent = 1;
						break;
					case 6:
						if (j == 1)
							percent = 5;
						else if (j == 2)
							percent = 2;
						else if (j == 3)
							percent = 1;
						else if (j == 4)
							percent = 1;
						else if (j == 5)
							percent = 0.5f;
						else if (j == 6)
							percent = 0.5f;
						break;
					case 7:
						if (j == 1)
							percent = 5;
						else if (j == 2)
							percent = 1.5f;
						else if (j == 3)
							percent = 1;
						else if (j == 4)
							percent = 1;
						else if (j == 5)
							percent = 0.5f;
						else if (j == 6)
							percent = 0.5f;
						else if (j == 7)
							percent = 0.5f;
						break;
					default:
						if (j == 1)
							percent = 5;
						else if (j == 2)
							percent = 1.5f;
						else if (j == 3)
							percent = 1;
						else if (j == 4)
							percent = 1;
						else if (j == 5)
							percent = 0.5f;
						else if (j == 6)
							percent = 0.5f;
						else if (j == 7)
							percent = 0.5f;
						else
							percent = 0;
						break;

					}
					float f = 0;
					if (percent != 0)
						f = score * percent / 10;
					float sco = (float) (Math.round(f * 1000)) / 1000;// ������λС��

					member.setPer(percent);
					member.setScore(sco);
				}
				j++;
				mlist.add(member);
			}
			project.setWriter(mlist);
		}
		int i = 1;
		if (project.getId() != null) {
			projectDeptList = jxkhProjectService.findWritingDept(project);
			if (projectDeptList != null && projectDeptList.size() != 0) {
				jxkhProjectService.deleteAll(projectDeptList);
			}
		}
		if (deptList != null && deptList.size() != 0) {
			for (WkTDept wktDept : deptList) {
				Jxkh_WritingDept dept = new Jxkh_WritingDept();
				dept.setWriting(project);
				dept.setDeptId(wktDept.getKdNumber());
				dept.setName(wktDept.getKdName());
				dept.setRank(i);
				i++;
				// ����Ĭ�ϵķ�ֵ0503
				float fen = 0.0f;
				for (int g = 0; g < mlist.size(); g++) {
					Jxkh_Writer m = mlist.get(g);
					if (m.getDept().equals(wktDept.getKdName())) {
						fen += m.getScore();
					}
				}
				float scor = (float) (Math.round(fen * 1000)) / 1000;// ������λС��
				dept.setScore(scor);
				dlist.add(dept);
			}
			project.setWritingDept(dlist);
		}
		// 20120416 ���Ƶ����˵÷ּ��㲿��
		// �µĸ�������20120306
		List<String[]> arrList = new ArrayList<String[]>();
		Set<Jxkh_WritingFile> fset = new HashSet<Jxkh_WritingFile>();
		if (project.getWritingFile() != null) {
			for (Object o : project.getWritingFile().toArray()) {
				Jxkh_WritingFile f = (Jxkh_WritingFile) o;
				if (f != null)
					jxkhProjectService.delete(f);
			}
			project.getWritingFile().clear();
		}
		if (arrList1 != null && arrList1.size() != 0)
			arrList.addAll(arrList1);
		if (arrList2 != null && arrList2.size() != 0)
			arrList.addAll(arrList2);
		if (arrList3 != null && arrList3.size() != 0)
			arrList.addAll(arrList3);
		// if (arrList4 != null && arrList4.size() != 0)
		// arrList.addAll(arrList4);
		// if (arrList5 != null && arrList5.size() != 0)
		// arrList.addAll(arrList5);
		for (int r = 0; r < arrList.size(); r++) {
			String[] s = arrList.get(r);
			Jxkh_WritingFile file = new Jxkh_WritingFile();
			file.setWriting(project);
			file.setPath(s[0]);
			file.setName(s[1]);
			file.setSubmitDate(s[2]);
			file.setBelongType(s[3]);
			fset.add(file);
		}
		project.setWritingFile(fset);

		try {
			jxkhProjectService.saveOrUpdate(project);
			Messagebox.show("��������ɹ�����������ύ�����ţ��������ύ����ť��", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			projectDeptList = jxkhProjectService.findWritingDept(project);
			scoreTab.setDisabled(false);
			fileTab.setDisabled(false);
			personScore.setModel(new ListModelList(mlist));
			departmentScore.setModel(new ListModelList(dlist));
		} catch (Exception e) {
			e.printStackTrace();
			try {
				Messagebox.show("��������ʧ�ܣ������ԣ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		initShow();
	}

	private Float computeScore(Jxkh_BusinessIndicator kdRank, Short sign) {
		float result = 0f;
		String type = "";
		float res = 0f;
		Properties property = PropertiesLoader.loader("title", "title.properties");
		float zz = 0, bzyz = 0, hb = 0, nbqk = 0;
		if (kdRank.getKbName().equals(property.getProperty("zz"))) {
			if (sign.shortValue() == Jxkh_Writing.YES) {
				zz += 1f;
				result = zz;
				type = property.getProperty("zz");
			} else if (sign.shortValue() == Jxkh_Writing.NO) {
				bzyz += 1f;
				result = bzyz;
				type = property.getProperty("bz");
			}
		} else if (kdRank.getKbName().equals(property.getProperty("bz")) || kdRank.getKbName().equals(property.getProperty("yz"))) {
			if (sign.shortValue() == Jxkh_Writing.YES) {
				bzyz += 1f;
				result = bzyz;
			} else if (sign.shortValue() == Jxkh_Writing.NO) {
				bzyz += 0.5f;
				result = bzyz;
			}
			if (kdRank.getKbName().equals(property.getProperty("bz")))
				type = property.getProperty("bz");
			else
				type = property.getProperty("yz");
		} else if (kdRank.getKbName().equals(property.getProperty("hb"))) {
			if (sign.shortValue() == Jxkh_Writing.YES) {
				hb += 1f;
				result = hb;
			} else if (sign.shortValue() == Jxkh_Writing.NO) {
				hb += 0.5f;
				result = hb;
			}
			type = property.getProperty("hb");
		} else if (kdRank.getKbName().equals(property.getProperty("nbqk"))) {
			if (sign.shortValue() == Jxkh_Writing.YES) {
				nbqk += 1f;
				result = nbqk;
			} else if (sign.shortValue() == Jxkh_Writing.NO) {
				nbqk += 0.5f;
				result = nbqk;
			}
			type = property.getProperty("nbqk");
		}
		Jxkh_BusinessIndicator bi = (Jxkh_BusinessIndicator) businessIndicatorService.getEntityByName(type);
		if (bi == null) {
			res += result * 0;
		} else {
			res += result * bi.getKbScore() * bi.getKbIndex();
			res = (float) (Math.round(res * 1000)) / 1000;// ������λС��
		}
		return Float.valueOf(res);

	}

	/**
	 * <li>�����������رյ�ǰ���ڡ� void
	 * 
	 * @author songyu
	 */
	public void onClick$close() {
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
			if (project != null && type.equals(project.getSort())) {
				item.setSelected(true);
			}
		}
	}

	/** ���ַ�ʽ�б���Ⱦ�� */
	public class methodRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			String rank = (String) data;
			item.setValue(rank);
			item.setLabel(rank);
			if (item.getIndex() == 0)
				item.setSelected(true);
			if (project != null && project.getScoreWay().equals(rank))
				item.setSelected(true);
		}
	}

	// ����ѡ���Ա��ť����������ѡ���Աҳ���¼�
	public void onClick$chooseMember() throws SuspendNotAllowedException, InterruptedException {
		final ChooseMemberWin win = (ChooseMemberWin) Executions.createComponents("/admin/personal/businessdata/award/choosemember.zul", null, null);
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

		// ����ѡ�����Ա�������Զ���֮��Ӧ ��2012��03��01�š�
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
		final ChooseDeptWin win = (ChooseDeptWin) Executions.createComponents("/admin/personal/businessdata/award/choosedept.zul", null, null);
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
				win.detach();
			}
		});
		win.doModal();
	}

	public Jxkh_Writing getProject() {
		return project;
	}

	public void setProject(Jxkh_Writing project) {
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
		final UpfileWindow w = (UpfileWindow) Executions.createComponents("/admin/personal/businessdata/meeting/upfile.zul", null, null);
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
				arr[3] = "����";
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
		final UpfileWindow w = (UpfileWindow) Executions.createComponents("/admin/personal/businessdata/meeting/upfile.zul", null, null);
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
				arr[3] = "����֤��";
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
		final UpfileWindow w = (UpfileWindow) Executions.createComponents("/admin/personal/businessdata/meeting/upfile.zul", null, null);
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
				arr[3] = "���ݵ�һҳ";
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
	// private List<String[]> arrList4 = new ArrayList<String[]>();

	// public void onClick$ups4() {
	// final UpfileWindow w = (UpfileWindow) Executions.createComponents(
	// "/admin/personal/businessdata/meeting/upfile.zul", null, null);
	// w.addEventListener(Events.ON_CHANGE, new EventListener() {
	// public void onEvent(Event arg0) throws Exception {
	// String filePath = (String) Executions.getCurrent()
	// .getAttribute("path");
	// String fileName = (String) Executions.getCurrent()
	// .getAttribute("title");
	// String upTime = (String) Executions.getCurrent().getAttribute(
	// "upTime");
	// applyList4.setItemRenderer(new FilesRenderer4());
	// String[] arr = new String[4];
	// arr[0] = filePath;
	// arr[1] = fileName;
	// arr[2] = upTime;
	// arr[3] = "�������һҳ";
	// arrList4.add(arr);
	// applyList4.setModel(new ListModelList(arrList4));
	// }
	// });
	// try {
	// w.doModal();
	// } catch (SuspendNotAllowedException e) {
	// e.printStackTrace();
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// }
	//
	// /**
	// * <li>�����������ĵ���Ϣ��listbox(20120305)
	// */
	// public class FilesRenderer4 implements ListitemRenderer {
	//
	// @Override
	// public void render(Listitem arg0, Object arg1) throws Exception {
	// final String[] str = (String[]) arg1;
	// arg0.setValue(str);
	// Listcell c1 = new Listcell(arg0.getIndex() + 1 + "");
	// Listcell c2 = new Listcell(str[1]);
	// Listcell c3 = new Listcell(str[2]);
	// Listcell c4 = new Listcell();
	// Toolbarbutton downlowd = new Toolbarbutton();
	// downlowd.setImage("/css/default/images/button/down.gif");
	// downlowd.setParent(c4);
	// downlowd.addEventListener(Events.ON_CLICK, new EventListener() {
	// @Override
	// public void onEvent(Event arg0) throws Exception {
	// download(str[0], str[1]);
	// }
	// });
	// Toolbarbutton del = new Toolbarbutton();
	// del.setImage("/css/default/images/button/del.gif");
	// del.setParent(c4);
	// del.addEventListener(Events.ON_CLICK, new EventListener() {
	// @Override
	// public void onEvent(Event arg0) throws Exception {
	// Messagebox.show("ȷ��ɾ����?", "ȷ��", Messagebox.OK
	// | Messagebox.CANCEL, Messagebox.QUESTION,
	// new org.zkoss.zk.ui.event.EventListener() {
	// public void onEvent(Event evt)
	// throws InterruptedException {
	// if (evt.getName().equals("onOK")) {
	// arrList4.remove(str);
	// applyList4.setModel(new ListModelList(
	// arrList4));
	// }
	// }
	// });
	// }
	// });
	// arg0.appendChild(c1);
	// arg0.appendChild(c2);
	// arg0.appendChild(c3);
	// arg0.appendChild(c4);
	// }
	// }
	//
	// // 20120305
	// // private List<String[]> arrList5 = new ArrayList<String[]>();
	//
	// public void onClick$ups5() {
	// final UpfileWindow w = (UpfileWindow) Executions.createComponents(
	// "/admin/personal/businessdata/meeting/upfile.zul", null, null);
	// w.addEventListener(Events.ON_CHANGE, new EventListener() {
	// public void onEvent(Event arg0) throws Exception {
	// String filePath = (String) Executions.getCurrent()
	// .getAttribute("path");
	// String fileName = (String) Executions.getCurrent()
	// .getAttribute("title");
	// String upTime = (String) Executions.getCurrent().getAttribute(
	// "upTime");
	// applyList5.setItemRenderer(new FilesRenderer5());
	// String[] arr = new String[4];
	// arr[0] = filePath;
	// arr[1] = fileName;
	// arr[2] = upTime;
	// arr[3] = "�������쵼��Ժ�쵼ȷ�ϵ�֤��";
	// arrList5.add(arr);
	// applyList5.setModel(new ListModelList(arrList5));
	// }
	// });
	// try {
	// w.doModal();
	// } catch (SuspendNotAllowedException e) {
	// e.printStackTrace();
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// }
	//
	// /**
	// * <li>�����������ĵ���Ϣ��listbox(20120305)
	// */
	// public class FilesRenderer5 implements ListitemRenderer {
	//
	// @Override
	// public void render(Listitem arg0, Object arg1) throws Exception {
	// final String[] str = (String[]) arg1;
	// arg0.setValue(str);
	// Listcell c1 = new Listcell(arg0.getIndex() + 1 + "");
	// Listcell c2 = new Listcell(str[1]);
	// Listcell c3 = new Listcell(str[2]);
	// Listcell c4 = new Listcell();
	// Toolbarbutton downlowd = new Toolbarbutton();
	// downlowd.setImage("/css/default/images/button/down.gif");
	// downlowd.setParent(c4);
	// downlowd.addEventListener(Events.ON_CLICK, new EventListener() {
	// @Override
	// public void onEvent(Event arg0) throws Exception {
	// download(str[0], str[1]);
	// }
	// });
	// Toolbarbutton del = new Toolbarbutton();
	// del.setImage("/css/default/images/button/del.gif");
	// del.setParent(c4);
	// del.addEventListener(Events.ON_CLICK, new EventListener() {
	// @Override
	// public void onEvent(Event arg0) throws Exception {
	// Messagebox.show("ȷ��ɾ����?", "ȷ��", Messagebox.OK
	// | Messagebox.CANCEL, Messagebox.QUESTION,
	// new org.zkoss.zk.ui.event.EventListener() {
	// public void onEvent(Event evt)
	// throws InterruptedException {
	// if (evt.getName().equals("onOK")) {
	// arrList5.remove(str);
	// applyList5.setModel(new ListModelList(
	// arrList5));
	// }
	// }
	// });
	// }
	// });
	// arg0.appendChild(c1);
	// arg0.appendChild(c2);
	// arg0.appendChild(c3);
	// arg0.appendChild(c4);
	// }
	// }

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
			final Jxkh_Writer member = (Jxkh_Writer) data;
			item.setValue(member);
			Listcell c1 = new Listcell(item.getIndex() + 1 + "");
			Listcell c2 = new Listcell(member.getName());
			Listcell c3 = new Listcell();
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

			final List<Jxkh_WritingDept> temp = jxkhProjectService.findWritingDep(project);
			// ����ָ������ҳ��
			bar.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event event) throws Exception {

					AssignDeptWindow w = (AssignDeptWindow) Executions.createComponents("/admin/personal/businessdata/meeting/assignDept.zul", null, null);
					try {
						w.setFlag("writing");
						w.setState(project.getState());
						w.setDept(temp);
						w.setMember(member);
						w.initWindow();
						w.doModal();

						// ָ��������ɺ��Զ�����÷�
						w.addEventListener(Events.ON_CHANGE, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								List<Jxkh_Writer> tempMemberList = jxkhProjectService.findWritingMember(project);
								for (int k = 0; k < projectDeptList.size(); k++) {
									Jxkh_WritingDept d = projectDeptList.get(k);
									float f = 0.0f;
									for (int i = 0; i < tempMemberList.size(); i++) {
										Jxkh_Writer m = tempMemberList.get(i);
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
								personScore.setModel(new ListModelList(tempMemberList));
								List<Jxkh_WritingDept> tempDeptList = jxkhProjectService.findWritingDept(project);
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
			item.appendChild(c6);
			item.appendChild(c7);
		}
	}

	/** ���ż����б���Ⱦ�� */
	public class departmentScoreRenderer implements ListitemRenderer {

		@Override
		public void render(Listitem arg0, Object arg1) throws Exception {
			if (arg0 == null)
				return;
			final Jxkh_WritingDept dept = (Jxkh_WritingDept) arg1;
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
		/*if(project.getContentPercent() != null && computMethod.getSelectedItem().getValue().equals("��д���ݱ���")){
			if (a * 10 != Float.valueOf(project.getContentPercent())) {
				BigDecimal bd = new BigDecimal(Float.valueOf(project.getContentPercent()).doubleValue() / 10);
				bd.setScale(3, BigDecimal.ROUND_HALF_UP);
				String s = "������Ա��ռ�������ܺ��Ƿ�Ϊ" + bd.doubleValue();
				try {
					Messagebox.show(s, "��ʾ", Messagebox.OK, Messagebox.EXCLAMATION);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return;
			}
		}*/
		if(project.getContentPercent() != null && computMethod.getSelectedItem().getValue().equals("��д���ݱ���")){
			if (a * 10 != Float.valueOf(project.getContentPercent())) {
				BigDecimal bd = new BigDecimal(Float.valueOf(project.getContentPercent()).doubleValue() / 10);
				bd.setScale(3, BigDecimal.ROUND_HALF_UP);
				String s = "������Ա��ռ�������ܺ��Ƿ�Ϊ" + bd.doubleValue();
				try {
					Messagebox.show(s, "��ʾ", Messagebox.OK, Messagebox.EXCLAMATION);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return;
			}
		}else if(project.getMemPercent() != null && computMethod.getSelectedItem().getValue().equals("�α���Ա����")){
			if (a * 10 != Float.valueOf(project.getMemPercent())) {
				BigDecimal bd = new BigDecimal(Float.valueOf(project.getMemPercent()).doubleValue() / 10);
				bd.setScale(3, BigDecimal.ROUND_HALF_UP);
				String s = "������Ա��ռ�������ܺ��Ƿ�Ϊ" + bd.doubleValue();
				try {
					Messagebox.show(s, "��ʾ", Messagebox.OK, Messagebox.EXCLAMATION);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return;
			}
		}else{
			if (a * 10 != Float.valueOf("100")) {
				BigDecimal bd = new BigDecimal(Float.valueOf("100").doubleValue() / 10);
				bd.setScale(3, BigDecimal.ROUND_HALF_UP);
				String s = "������Ա��ռ�������ܺ��Ƿ�Ϊ" + bd.doubleValue();
				try {
					Messagebox.show(s, "��ʾ", Messagebox.OK, Messagebox.EXCLAMATION);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return;
			}
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
			Jxkh_Writer member = (Jxkh_Writer) item.getValue();
			member.setPer(s);
			member.setScore(score);
			jxkhMeetingService.update(member);
		}
		List<Jxkh_Writer> tempMemberList = jxkhProjectService.findWritingMember(project);
		personScore.setModel(new ListModelList(tempMemberList));
		for (int k = 0; k < projectDeptList.size(); k++) {
			Jxkh_WritingDept d = projectDeptList.get(k);
			float f = 0.0f;
			for (int i = 0; i < tempMemberList.size(); i++) {
				Jxkh_Writer m = tempMemberList.get(i);
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
		}
		try {
			Messagebox.show("������Ϣ����ɹ���", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		List<Jxkh_WritingDept> tempDeptList = jxkhProjectService.findWritingDept(project);
		departmentScore.setModel(new ListModelList(tempDeptList));
	}

	// ������Ϣ�Ĺرհ�ť
	public void onClick$closeScore() {
		this.onClose();
	}
}
