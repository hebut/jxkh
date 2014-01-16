package org.iti.jxkh.audit.video;

import java.util.ArrayList;
import java.util.List;

import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.entity.Jxkh_Video;
import org.iti.jxkh.entity.Jxkh_VideoDept;
import org.iti.jxkh.entity.Jxkh_VideoMember;
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

public class DetailWin extends Window implements AfterCompose {

	/**
	 * @author ZhangyuGuang
	 */
	private static final long serialVersionUID = 333479762422168296L;
	private Textbox name, videoMember, videoDept, coCompany, 
			longTime, media, record;
	private Listbox type, leader, rank;
	private Row outDeptRow;
	private Datebox shootDate, playDate;
	private Radio cooperationTrue, cooperationFalse, firstSignTrue,
			firstSignFalse;
	private Label submitName;
	private Button print;
	private YearListbox jiFenTime;
	private Hbox recordhbox;
	private Jxkh_Video video;
	private JxkhAwardService jxkhAwardService;
	private List<Jxkh_VideoMember> videoMemberList = new ArrayList<Jxkh_VideoMember>();
	private List<Jxkh_VideoDept> videoDeptList = new ArrayList<Jxkh_VideoDept>();

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		jiFenTime.initYearListbox("");
		leader.setItemRenderer(new videoLeaderRenderer());
		rank.setItemRenderer(new videoRankRenderer());
		type.setItemRenderer(new videoTypeRenderer());
	}

	public void initWindow() {
		String memberName = "";
		String deptName = "";
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
		// if(video.getState()==5){
		// record.setVisible(true);
		// recordlabel.setVisible(true);
		// recordhbox.setVisible(true);
		// record.setValue(video.getRecordCode());
		// }
		jiFenTime.initYearListbox(video.getjxYear());
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
			memberName += mem.getName() + "、";
		}
		videoMember.setValue(memberName.substring(0, memberName.length() - 1));

		videoDeptList = video.getVideoDept();
		for (int i = 0; i < videoDeptList.size(); i++) {
			Jxkh_VideoDept dept = (Jxkh_VideoDept) videoDeptList.get(i);
			deptName += dept.getName() + "、";
		}
		videoDept.setValue(deptName.substring(0, deptName.length() - 1));
		initListbox();
	}

	private void initListbox() {
		List<Jxkh_BusinessIndicator> rankList = new ArrayList<Jxkh_BusinessIndicator>();
		rankList.add(new Jxkh_BusinessIndicator());
		rankList.addAll(jxkhAwardService.findAllBusinessIndicator());
		leader.setModel(new ListModelList(rankList));
		leader.setSelectedIndex(0);
		rank.setModel(new ListModelList(rankList));
		leader.setSelectedIndex(0);

		String[] video_Types = { "", "视频短片", "科技宣传专题片", "科普教育专题片", "新闻采访" };
		List<String> videoTpyeList = new ArrayList<String>();
		for (int i = 0; i < 5; i++) {
			videoTpyeList.add(video_Types[i]);
		}
		type.setModel(new ListModelList(videoTpyeList));
	}

	// 影视领导渲染器
	public class videoLeaderRenderer implements ListitemRenderer {

		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			Jxkh_BusinessIndicator leader = (Jxkh_BusinessIndicator) data;
			item.setValue(leader);
			Listcell c0 = new Listcell();
			if (leader.getKbName() == null) {
				c0.setLabel("--请选择--");
			} else {
				c0.setLabel(leader.getKbName());
			}
			item.appendChild(c0);
			if (item.getIndex() == 0)
				item.setSelected(true);
			if (video != null && leader.equals(video.getLeader())) {
				item.setSelected(true);
			}
		}
	}

	// 影视领导渲染器
	public class videoRankRenderer implements ListitemRenderer {

		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			Jxkh_BusinessIndicator rank = (Jxkh_BusinessIndicator) data;
			item.setValue(rank);
			Listcell c0 = new Listcell();
			if (rank.getKbName() == null) {
				c0.setLabel("--请选择--");
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

	// 影视种类渲染器
	public class videoTypeRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {
			String video_Type = (String) data;
			item.setValue(video_Type);
			Listcell c0 = new Listcell();
			if (video_Type == null || video_Type.equals(""))
				c0.setLabel("--请选择--");
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
}
