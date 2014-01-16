package org.iti.jxkh.busiAudit.video;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;

import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxkh.business.award.ChooseDeptWin;
import org.iti.jxkh.business.award.ChooseMemberWin;
import org.iti.jxkh.business.meeting.AssignDeptWindow;
import org.iti.jxkh.business.meeting.UpfileWindow;
import org.iti.jxkh.entity.JXKH_MeetingDept;
import org.iti.jxkh.entity.JXKH_MeetingMember;
import org.iti.jxkh.entity.Jxkh_Award;
import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.entity.Jxkh_Video;
import org.iti.jxkh.entity.Jxkh_VideoDept;
import org.iti.jxkh.entity.Jxkh_VideoFile;
import org.iti.jxkh.entity.Jxkh_VideoMember;
import org.iti.jxkh.service.BusinessIndicatorService;
import org.iti.jxkh.service.JxkhAwardService;
import org.iti.jxkh.service.JxkhVideoService;
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
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
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

public class AddVideoWin extends Window implements AfterCompose {

	/**
	 * @author ZhangyuGuang
	 */
	private static final long serialVersionUID = -543905249206757346L;
	private Tab baseTab, fileTab, scoreTab;
	private Listbox personScore, departmentScore;
	private Toolbarbutton save, close;
	private Textbox name, videoMember, videoDept, coCompany, longTime, media, record;
	private Listbox type, leader, rank;
	private Row outDeptRow, recordlabel;
	private Datebox shootDate, playDate;
	private Button print;
	private YearListbox jiFenTime;
	private Label submitName;
	private Hbox recordhbox;
	private Radio cooperationTrue, cooperationFalse, firstSignTrue, firstSignFalse;
	private Jxkh_Video video;
	List<Jxkh_BusinessIndicator> rankList = new ArrayList<Jxkh_BusinessIndicator>();
	private JxkhVideoService jxkhVideoService;
	private JxkhAwardService jxkhAwardService;
	private BusinessIndicatorService businessIndicatorService;
	private WkTUser user;
	private List<WkTUser> memberList = new ArrayList<WkTUser>();
	private List<WkTDept> deptList = new ArrayList<WkTDept>();
	private List<Jxkh_VideoMember> videoMemberList = new ArrayList<Jxkh_VideoMember>();
	private List<Jxkh_VideoDept> videoDeptList = new ArrayList<Jxkh_VideoDept>();
	public static final Long awardRank = (long) 73;
	public static final Long newsRank = (long) 101;
	public static final Long lead = (long) 54;
	private Jxkh_VideoFile file;
	private Listbox applyList1, applyList2;

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		longTime.setValue("0.0");
		jiFenTime.initYearListbox("");
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");// ��ȡ��ǰ��¼�û�
		personScore.setItemRenderer(new personScoreRenderer());
		departmentScore.setItemRenderer(new departmentScoreRenderer());
		leader.setItemRenderer(new videoLeaderRenderer());
		rank.setItemRenderer(new videoRankRenderer());
		type.setItemRenderer(new videoTypeRenderer());
		// ���radio"cooperationTrue"�����¼�
		cooperationTrue.addEventListener(Events.ON_CHECK, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				outDeptRow.setVisible(true);
			}
		});
		// ���radio"cooperationFalse"�����¼�
		cooperationFalse.addEventListener(Events.ON_CHECK, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				outDeptRow.setVisible(false);
			}
		});

		rankList.add(new Jxkh_BusinessIndicator());
		rankList.addAll(jxkhAwardService.findRank(awardRank));
		rank.setModel(new ListModelList(rankList));
		rank.setSelectedIndex(0);
		String[] video_Types = { "", "��Ƶ��Ƭ", "�Ƽ�����ר��Ƭ", "���ս���ר��Ƭ", "���Ųɷ�" };
		List<String> videoTpyeList = new ArrayList<String>();
		for (int i = 0; i < 5; i++) {
			videoTpyeList.add(video_Types[i]);
		}
		type.setModel(new ListModelList(videoTpyeList));
		type.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event event) throws Exception {
				String t = (String) type.getSelectedItem().getValue();
				if (t.equals("���Ųɷ�")) {
					rankList.clear();
					rankList.add(new Jxkh_BusinessIndicator());
					rankList.addAll(jxkhAwardService.findRank(newsRank));
					rank.setModel(new ListModelList(rankList));
					rank.setSelectedIndex(0);
				} else {
					rankList.clear();
					rankList.add(new Jxkh_BusinessIndicator());
					rankList.addAll(jxkhAwardService.findRank(awardRank));
					rank.setModel(new ListModelList(rankList));
					rank.setSelectedIndex(0);
				}
			}
		});
		// �������ҳ��ʱ������Ϣҳ����ĵ���Ϣҳ��ı�����˳���ť����
		scoreTab.addEventListener(Events.ON_CLICK, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				save.setVisible(false);
				close.setVisible(false);
			}
		});
		baseTab.addEventListener(Events.ON_CLICK, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				save.setVisible(true);
				close.setVisible(true);
			}
		});
		fileTab.addEventListener(Events.ON_CLICK, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				save.setVisible(true);
				close.setVisible(true);
			}
		});
	}

	public void initWindow() {
		String memberName = "";
		String deptName = "";
		if (video == null) {
			cooperationFalse.setChecked(true);
			submitName.setValue(user.getKuName());
			scoreTab.setDisabled(true);
			fileTab.setDisabled(true);
		} else {
			scoreTab.setDisabled(false);
			fileTab.setDisabled(false);
			print.setHref("/print.action?n=video&id=" + video.getId());
			name.setValue(video.getName());
			if (video.getFirstSign() == 1) {
				firstSignTrue.setChecked(true);
				firstSignFalse.setChecked(false);
			} else {
				firstSignTrue.setChecked(false);
				firstSignFalse.setChecked(true);
			}
			if (video.getCoState() == 1) {
				cooperationTrue.setChecked(true);
				cooperationFalse.setChecked(false);
				outDeptRow.setVisible(true);
				coCompany.setValue(video.getCoCompany());
			} else {
				cooperationTrue.setChecked(false);
				cooperationFalse.setChecked(true);
			}
			if (video.getState() == 6) {
				record.setVisible(true);
				recordlabel.setVisible(true);
				recordhbox.setVisible(true);
				record.setValue(video.getRecordCode());
			}
			if (video.getShootTime() != null && !"".equals(video.getShootTime()))
				shootDate.setValue(ConvertUtil.convertDate(video.getShootTime()));
			if (video.getPlayTime() != null && !"".equals(video.getPlayTime()))
				playDate.setValue(ConvertUtil.convertDate(video.getPlayTime()));
			longTime.setValue(video.getLongTime() + "");
			submitName.setValue(video.getSubmitName());
			media.setValue(video.getMedia());
			videoMemberList = video.getVideoMember();
			for (int i = 0; i < videoMemberList.size(); i++) {
				Jxkh_VideoMember mem = (Jxkh_VideoMember) videoMemberList.get(i);
				memberName += mem.getName() + "��";
				if (mem.getType() == 0) {
					WkTUser user = jxkhAwardService.findWktUserByMemberUserId(mem.getPersonId());
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
			videoMember.setValue(memberName.substring(0, memberName.length() - 1));

			videoDeptList = video.getVideoDept();
			for (int i = 0; i < videoDeptList.size(); i++) {
				Jxkh_VideoDept dept = (Jxkh_VideoDept) videoDeptList.get(i);
				deptName += dept.getName() + "��";
				WkTDept depts = jxkhAwardService.findWkTDeptByDept(dept.getDeptId());
				deptList.add(depts);
			}
			videoDept.setValue(deptName.substring(0, deptName.length() - 1));

			jiFenTime.initYearListbox(video.getjxYear());
			// �����б�
			arrList1.clear();
			arrList2.clear();
			Set<?> files = video.getVideoFile();
			Object[] file = files.toArray();
			for (int j = 0; j < file.length; j++) {
				Jxkh_VideoFile f = (Jxkh_VideoFile) file[j];

				if (f.getBelongType().equals("����֤��")) {
					String[] arr = new String[4];
					arr[0] = f.getPath();
					arr[1] = f.getName();
					arr[2] = f.getDate();
					arr[3] = f.getBelongType();
					arrList1.add(arr);
				}
				if (f.getBelongType().equals("�쵼��ʾ")) {
					String[] arr = new String[4];
					arr[0] = f.getPath();
					arr[1] = f.getName();
					arr[2] = f.getDate();
					arr[3] = f.getBelongType();
					arrList2.add(arr);
				}
			}
			applyList1.setItemRenderer(new FilesRenderer1());
			applyList1.setModel(new ListModelList(arrList1));
			applyList2.setItemRenderer(new FilesRenderer2());
			applyList2.setModel(new ListModelList(arrList2));

			// 20120412
			if (video.getType().equals("���Ųɷ�")) {
				rankList.clear();
				rankList.add(new Jxkh_BusinessIndicator());
				rankList.addAll(jxkhAwardService.findRank(newsRank));
				rank.setModel(new ListModelList(rankList));
			}
		}
		initListbox();
	}

	private void initListbox() {
		List<Jxkh_BusinessIndicator> leaderList = new ArrayList<Jxkh_BusinessIndicator>();
		leaderList.add(new Jxkh_BusinessIndicator());
		leaderList.addAll(jxkhAwardService.findRank(lead));
		leader.setModel(new ListModelList(leaderList));
		leader.setSelectedIndex(0);
		personScore.setModel(new ListModelList(videoMemberList));
		departmentScore.setModel(new ListModelList(videoDeptList));
	}

	public void onClick$save() {
		try {
			if (videoMember.getValue() == null || videoMember.getValue().equals("")) {
				Messagebox.show("��ѡ��Ӱ�ӳ�Ա��", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
				return;
			}
			if (videoDept.getValue() == null || videoDept.getValue().equals("")) {
				Messagebox.show("��ѡ��Ӱ�Ӳ��ţ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
				return;
			}
			if (type.getSelectedItem().getIndex() == 0) {
				Messagebox.show("��ѡ��Ӱ�����࣡", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
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
			if (video == null) {
				video = new Jxkh_Video();
				setEntity();
			} else {
				setEntity();
			}
			jxkhVideoService.saveOrUpdate(video);
			Messagebox.show("Ӱ�ӱ���ɹ���", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			scoreTab.setDisabled(false);
			fileTab.setDisabled(false);
			List<Jxkh_VideoMember> mlist = video.getVideoMember();
			List<Jxkh_VideoDept> dlist = video.getVideoDept();
			personScore.setModel(new ListModelList(mlist));
			departmentScore.setModel(new ListModelList(dlist));
		} catch (Exception e) {
			e.printStackTrace();
			try {
				Messagebox.show("Ӱ�ӱ���ʧ�ܣ������ԣ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}

	private Float computeScore(Jxkh_Video video, Short sign) {
		float m = Float.parseFloat(longTime.getValue());
		video.setLongTime(m);
		float result = 0f;
		String type = "";
		float res = 0f;
		Properties property = PropertiesLoader.loader("title", "title.properties");
		float videonation2 = 0, videoprovince2 = 0;
		float videonation = 0, videoprovince = 0, videoweb = 0, goveragree = 0, tleaderagree = 0, tchuagree = 0;
		Map<String, Float> map = new HashMap<String, Float>();
		if (video.getRank() != null) {
			if (video.getRank().getKbName().equals(property.getProperty("videonation"))
					|| video.getRank().getKbName().equals(property.getProperty("centertv"))) {
				if (video.getFirstSign().shortValue() == Jxkh_Video.YES) {
					if (video.getType().equals(Jxkh_Video.TYPE4))
						videonation2 = 0.1f;
					else
						videonation2 = new BigDecimal(video.getLongTime() / 10f).setScale(0, BigDecimal.ROUND_DOWN).intValue();
					map.put("videonation", videonation2);
				} else if (video.getFirstSign().shortValue() == Jxkh_Video.NO) {
					if (video.getType().equals(Jxkh_Video.TYPE4))
						videoprovince2 = 0f;
					else
						videoprovince2 = new BigDecimal(video.getLongTime() / 10f).setScale(0, BigDecimal.ROUND_DOWN).intValue();
					map.put("videoprovince", videoprovince2);
				}
			} else if (video.getRank().getKbName().equals(property.getProperty("videoprovince"))
					|| video.getRank().getKbName().equals(property.getProperty("hbtv"))) {
				if (video.getFirstSign().shortValue() == Jxkh_Video.YES) {
					if (video.getType().equals(Jxkh_Video.TYPE4))
						videoprovince2 = 0.2f;
					else
						videoprovince2 = new BigDecimal(video.getLongTime() / 10f).setScale(0, BigDecimal.ROUND_DOWN).intValue();
					map.put("videoprovince", videoprovince2);
				} else if (video.getFirstSign().shortValue() == Jxkh_Video.NO) {
					if (video.getType().equals(Jxkh_Video.TYPE4))
						videoprovince2 = 0f;
					else
						videoprovince2 = new BigDecimal(video.getLongTime() / 10f).setScale(0, BigDecimal.ROUND_DOWN).intValue() * 0.5f;
					map.put("videoprovince", videoprovince2);
				}
			}
		}
		if (video.getLeader() != null && video.getType().equals(Jxkh_Video.TYPE4)) {
			float goveragree2 = 0, tleaderagree2 = 0, tchuagree2 = 0;
			if (video.getLeader() != null && video.getLeader().getKbName().equals(property.getProperty("goveragree"))) {
				if (video.getFirstSign().shortValue() == Jxkh_Video.YES) {
					goveragree2 = 1f;
					map.put("goveragree", goveragree2);
				} else if (video.getFirstSign().shortValue() == Jxkh_Video.NO) {
					tleaderagree2 = 1f;
					map.put("tleaderagree", tleaderagree2);
				}
			} else if (video.getLeader() != null && video.getLeader().getKbName().equals(property.getProperty("tleaderagree"))) {
				if (video.getFirstSign().shortValue() == Jxkh_Video.YES) {
					tleaderagree2 = 1f;
					map.put("tleaderagree", tleaderagree2);
				} else if (video.getFirstSign().shortValue() == Jxkh_Video.NO) {
					tchuagree2 = 1f;
					map.put("tchuagree", tchuagree2);
				}
			} else if (video.getLeader() != null && video.getLeader().getKbName().equals(property.getProperty("tchuagree"))) {
				if (video.getFirstSign().shortValue() == Jxkh_Video.YES) {
					tchuagree2 = 1f;
					map.put("tchuagree", tchuagree2);
				} else if (video.getFirstSign().shortValue() == Jxkh_Video.NO) {
					tchuagree2 = 0.5f;
					map.put("tchuagree", tchuagree2);
				}
			}
			float max = Float.MIN_VALUE;
			String key = "";
			for (Entry<String, Float> entry : map.entrySet()) {
				if (entry.getValue() > max) {
					max = entry.getValue();
					key = entry.getKey();
				}
			}
			if (key.equals("videonation")) {
				videonation += map.get("videonation");
				result = videonation;
				type = property.getProperty("videonation");
			} else if (key.equals("videoprovince")) {
				videoprovince += map.get("videoprovince");
				result = videoprovince;
				type = property.getProperty("videoprovince");
			} else if (key.equals("videoweb")) {
				videoweb += map.get("videoweb");
				result = videoweb;
				type = property.getProperty("videoweb");
			} else if (key.equals("goveragree")) {
				goveragree += map.get("goveragree");
				result = goveragree;
				type = property.getProperty("goveragree");
			} else if (key.equals("tleaderagree")) {
				tleaderagree += map.get("tleaderagree");
				result = tleaderagree;
				type = property.getProperty("tleaderagree");
			} else if (key.equals("tchuagree")) {
				tchuagree += map.get("tchuagree");
				result = tchuagree;
				type = property.getProperty("tchuagree");
			}
		} else {
			videonation += videonation2;
			videoprovince += videoprovince2;
			// videoweb += videoweb2;
			if (videonation != 0) {
				result = videonation;
				type = property.getProperty("videonation");
			} else if (videoprovince != 0) {
				result = videoprovince;
				type = property.getProperty("videoprovince");
			} else if (videoweb != 0) {
				result = videoweb;
				type = property.getProperty("videoweb");
			}
		}
		Jxkh_BusinessIndicator bi;
		if (type.equals(property.getProperty("videoprovince"))) {
			bi = (Jxkh_BusinessIndicator) businessIndicatorService.findBykbValue(Jxkh_BusinessIndicator.VEDIO_PROVENCE);
		} else if (type.equals(property.getProperty("videonation"))) {
			bi = (Jxkh_BusinessIndicator) businessIndicatorService.findBykbValue(Jxkh_BusinessIndicator.VEDIO_NATION);
		} else {
			bi = (Jxkh_BusinessIndicator) businessIndicatorService.getEntityByName(type);
		}

		if (bi == null) {
			res += result * 0;
		} else {
			res += result * bi.getKbScore() * bi.getKbIndex();
			this.video.setComputeType(bi.getKbId());
		}
		return Float.valueOf(res);

	}

	public void setEntity() {
		Short sign;
		video.setName(name.getValue());
		video.setjxYear(jiFenTime.getSelectedItem().getValue() + "");
		if ( leader.getSelectedIndex() != 0 && (Jxkh_BusinessIndicator) leader.getSelectedItem().getValue() != null) {
			video.setLeader((Jxkh_BusinessIndicator) leader.getSelectedItem().getValue());
		}
		if (rank.getSelectedIndex() != 0) {
			video.setRank((Jxkh_BusinessIndicator) rank.getSelectedItem().getValue());
		} else {
			video.setRank(null);
		}
		if ((String) type.getSelectedItem().getValue() != null) {
			video.setType((String) type.getSelectedItem().getValue());
		}
		if (cooperationTrue.isChecked()) {
			video.setCoState(Jxkh_Video.YES);
			video.setCoCompany(coCompany.getValue());
		} else {
			video.setCoState(Jxkh_Video.NO);
			video.setCoCompany(null);
		}
		if (firstSignTrue.isChecked()) {
			video.setFirstSign(Jxkh_Award.YES);
			sign = Jxkh_Award.YES;
		} else {
			video.setFirstSign(Jxkh_Award.NO);
			sign = Jxkh_Award.NO;
		}
		if (shootDate.getValue() != null)
			video.setShootTime(ConvertUtil.convertDateString(shootDate.getValue()));
		if (playDate.getValue() != null)
			video.setPlayTime(ConvertUtil.convertDateString(playDate.getValue()));
		video.setLongTime(Float.parseFloat(longTime.getValue()));
		video.setMedia(media.getValue());
		video.setRecordCode(record.getValue());
		Float score = computeScore(video, sign);
		video.setScore(score);
		int j = 1;
		List<Jxkh_VideoMember> mlist = new ArrayList<Jxkh_VideoMember>();
		List<Jxkh_VideoDept> dlist = new ArrayList<Jxkh_VideoDept>();
		if (video.getVideoMember() != null) {
			mlist = video.getVideoMember();
			jxkhVideoService.deleteAll(mlist);
			mlist.clear();
		}
		for (WkTUser user : memberList) {
			Jxkh_VideoMember member = new Jxkh_VideoMember();
			member.setVideo(video);
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
			if ((user.getKdId() != null && user.getDept() != null && user.getDept().getKdNumber().equals(WkTDept.guanlidept)) || user.getKdId() == null)
				member.setAssignDep(deptList.get(0).getKdName());
			else
				member.setAssignDep(user.getDept().getKdName());
			j++;
			mlist.add(member);
		}
		video.setVideoMember(mlist);
		int i = 1;
		if (video.getVideoDept() != null) {
			dlist = video.getVideoDept();
			jxkhVideoService.deleteAll(dlist);
			dlist.clear();
		}
		for (WkTDept wktDept : deptList) {
			Jxkh_VideoDept dept = new Jxkh_VideoDept();
			dept.setVideo(video);
			dept.setName(wktDept.getKdName());
			dept.setDeptId(wktDept.getKdNumber());
			dept.setRank(i);
			i++;
			// ����Ĭ�ϵķ�ֵ0503
			float fen = 0.0f;
			for (int g = 0; g < mlist.size(); g++) {
				Jxkh_VideoMember m = mlist.get(g);
				if (m.getAssignDep().equals(dept.getName())) {
					fen += m.getScore();
				}
			}
			float scor = (float) (Math.round(fen * 1000)) / 1000;// ������λС��
			dept.setScore(scor);
			dlist.add(dept);
		}
		video.setVideoDept(dlist);

		// ��ɾ��ԭ��fileʵ��
		List<String[]> arrList = new ArrayList<String[]>();
		Set<Jxkh_VideoFile> fset=new HashSet<Jxkh_VideoFile>();
		Set<Jxkh_VideoFile> oldFile = video.getVideoFile();
		if (oldFile != null) {
			for(Object o : oldFile.toArray()) {
				Jxkh_VideoFile f = (Jxkh_VideoFile) o;
				if(f != null)
					jxkhVideoService.delete(f);
			}
		}		
		if (video.getId() == null)
			jxkhVideoService.save(video);
		if(video.getVideoFile() != null)
			video.getVideoFile().clear();
		// ���渽������
		if (arrList1.size() != 0 || arrList1 != null)
			arrList.addAll(arrList1);
		if (arrList2.size() != 0 || arrList2 != null)
			arrList.addAll(arrList2);
		for (int r = 0; r < arrList.size(); r++) {
			String[] s = arrList.get(r);
			file = new Jxkh_VideoFile();
			file.setVideo(video);
			file.setPath(s[0]);
			file.setName(s[1]);
			file.setDate(s[2]);
			file.setBelongType(s[3]);
			fset.add(file);
		}
		video.setVideoFile(fset);

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
				videoMember.setValue(members);
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
				if (ui.getDept().getKdId().equals(uj.getDept().getKdId())) {
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
		videoDept.setValue(null);
		videoDept.setValue(depts);
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
				videoDept.setValue(depts);
				win.detach();
			}
		});
		win.doModal();
	}

	// Ӱ���쵼��Ⱦ��
	public class videoLeaderRenderer implements ListitemRenderer {

		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			Jxkh_BusinessIndicator leader = (Jxkh_BusinessIndicator) data;
			item.setValue(leader);
			Listcell c0 = new Listcell();
			if (leader.getKbName() == null) {
				c0.setLabel("--��ѡ��--");
			} else {
				c0.setLabel(leader.getKbName());
			}
			item.appendChild(c0);
			if (item.getIndex() == 0)
				item.setSelected(true);
			if (video != null && video.getLeader() != null && leader.equals(video.getLeader())) {
				item.setSelected(true);
			}
		}
	}

	// Ӱ���쵼��Ⱦ��
	public class videoRankRenderer implements ListitemRenderer {

		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			Jxkh_BusinessIndicator rank = (Jxkh_BusinessIndicator) data;
			item.setValue(rank);
			Listcell c0 = new Listcell();
			if (rank.getKbName() == null) {
				c0.setLabel("--��ѡ��--");
			} else {
				c0.setLabel(rank.getKbName());
			}
			item.appendChild(c0);
			if (item.getIndex() == 0)
				item.setSelected(true);
			if (video != null && rank.equals(video.getRank())) {
				item.setSelected(true);
			}
		}
	}

	// Ӱ��������Ⱦ��
	public class videoTypeRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {
			String video_Type = (String) data;
			item.setValue(video_Type);
			Listcell c0 = new Listcell();
			if (video_Type == null || video_Type.equals(""))
				c0.setLabel("--��ѡ��--");
			else {
				c0.setLabel(video_Type);
			}
			item.appendChild(c0);
			if (item.getIndex() == 0)
				item.setSelected(true);
			if (video != null && video_Type.equals(video.getType())) {
				item.setSelected(true);
			}
		}
	}

	public void onClick$close() {
		this.detach();
	}

	public Jxkh_Video getVideo() {
		return video;
	}

	public void setVideo(Jxkh_Video video) {
		this.video = video;
	}

	// 20120318
	private List<String[]> arrList1 = new ArrayList<String[]>();

	public void onClick$ups1() {
		final UpfileWindow w = (UpfileWindow) Executions.createComponents("/admin/jxkh/busiAudit/meeting/upfile.zul", null, null);
		w.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				String filePath = (String) Executions.getCurrent().getAttribute("path");
				System.out.println("����ļ�·����path=" + filePath);
				String fileName = (String) Executions.getCurrent().getAttribute("title");
				System.out.println("������ļ�������title=" + fileName);
				String upTime = (String) Executions.getCurrent().getAttribute("upTime");
				System.out.println("����ļ����ϴ�ʱ��time=" + upTime);
				applyList1.setItemRenderer(new FilesRenderer1());
				String[] arr = new String[4];
				arr[0] = filePath;
				arr[1] = fileName;
				arr[2] = upTime;
				arr[3] = "����֤��";
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
				arr[3] = "�쵼��ʾ";
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

	public void download(String fpath, String fname) throws InterruptedException {
		System.out.println("20120304path=" + UploadUtil.getRootPath() + fpath);
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
			final Jxkh_VideoMember member = (Jxkh_VideoMember) data;
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

			final List<Jxkh_VideoDept> temp = jxkhVideoService.findVideoDeprByVideoId(video);
			// ����ָ������ҳ��
			bar.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event event) throws Exception {

					AssignDeptWindow w = (AssignDeptWindow) Executions.createComponents("/admin/jxkh/busiAudit/meeting/assignDept.zul", null, null);
					try {
						w.setFlag("VIDEO");
						w.setDept(temp);
						w.setMember(member);
						w.initWindow();
						w.doModal();

						// ָ��������ɺ��Զ�����÷�
						w.addEventListener(Events.ON_CHANGE, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								List<Jxkh_VideoMember> tempMemberList = video.getVideoMember();
								personScore.setModel(new ListModelList(tempMemberList));
								for (int k = 0; k < videoDeptList.size(); k++) {
									Jxkh_VideoDept d = videoDeptList.get(k);
									float f = 0.0f;
									for (int i = 0; i < tempMemberList.size(); i++) {
										Jxkh_VideoMember m = tempMemberList.get(i);
										if (m.getAssignDep() == null || m.getAssignDep().equals("")) {
											if (m.getDept().equals(d.getName())) {
												f += m.getScore();
											}
										} else if (m.getAssignDep().equals(d.getName())) {
											f += m.getScore();
										}
									}
									d.setScore(f);
									jxkhVideoService.update(d);
								}
								List<Jxkh_VideoDept> tempDeptList = video.getVideoDept();
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
			final Jxkh_VideoDept dept = (Jxkh_VideoDept) arg1;
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
		for (int i = 0; i < videoMemberList.size(); i++) {
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
		for (int i = 0; i < videoMemberList.size(); i++) {
			Listitem item = personScore.getItemAtIndex(i);
			Listcell lc = (Listcell) item.getChildren().get(4);
			Textbox temp = (Textbox) lc.getChildren().get(0);
			float s = 0.0f;// ����
			float f = 0.0f;// ��ֵ
			if (temp.getValue() != null && temp.getValue() != "")
				s = Float.parseFloat(temp.getValue());
			if (video.getScore() != null)
				f = s * video.getScore() / 10;
			float score = (float) (Math.round(f * 1000)) / 1000;
			Jxkh_VideoMember member = (Jxkh_VideoMember) item.getValue();
			member.setPer(s);
			member.setScore(score);
			jxkhVideoService.update(member);
		}
		List<Jxkh_VideoMember> tempMemberList = video.getVideoMember();
		personScore.setModel(new ListModelList(tempMemberList));
		for (int k = 0; k < videoDeptList.size(); k++) {
			Jxkh_VideoDept d = videoDeptList.get(k);
			float f = 0.0f;
			for (int i = 0; i < tempMemberList.size(); i++) {
				Jxkh_VideoMember m = tempMemberList.get(i);
				if (m.getAssignDep() == null || m.getAssignDep().equals("")) {
					if (m.getDept().equals(d.getName())) {
						f += m.getScore();
					}
				} else if (m.getAssignDep().equals(d.getName())) {
					f += m.getScore();
				}
			}
			d.setScore(f);
			jxkhVideoService.update(d);
		}
		try {
			Messagebox.show("������Ϣ����ɹ���", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Jxkh_VideoDept> tempDeptList = video.getVideoDept();
		departmentScore.setModel(new ListModelList(tempDeptList));
	}

	// ������Ϣ�Ĺرհ�ť
	public void onClick$closeScore() {
		this.onClose();
	}
}
