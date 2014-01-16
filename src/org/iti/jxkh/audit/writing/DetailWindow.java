package org.iti.jxkh.audit.writing;

import java.util.ArrayList;
import java.util.List;

import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.entity.Jxkh_Writer;
import org.iti.jxkh.entity.Jxkh_Writing;
import org.iti.jxkh.entity.Jxkh_WritingDept;
import org.iti.jxkh.service.JxkhProjectService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.iti.common.util.ConvertUtil;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;

public class DetailWindow extends Window implements AfterCompose {

	/**
	 * @author SongYu
	 */
	private static final long serialVersionUID = -3664613977051724505L;
	WkTUser user;
	private JxkhProjectService jxkhProjectService;
	private Textbox projectName, projectMember, department, coUnit, header,
			memProp, contentProp;
	private Label writer;
	private Groupbox identGroupbox;
	private YearListbox jiFenTime;
	private Datebox end;
	private Listbox rank, computMethod;
	private Radiogroup ifCoo, ifPub;
	private Row coUnitRow, memRow, contentRow;
	private Button print;
	private Jxkh_Writing project;
	private Radio firstSignTrue, firstSignFalse;
	private Label recordlabel;
	private Hbox recordhbox;
	private Textbox record;

	private List<WkTUser> memberList = new ArrayList<WkTUser>();
	private List<WkTDept> deptList = new ArrayList<WkTDept>();
	private List<Jxkh_Writer> projectMemberList = new ArrayList<Jxkh_Writer>();
	private List<Jxkh_WritingDept> projectDeptList = new ArrayList<Jxkh_WritingDept>();

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");// 获取当前登录用户
		writer.setValue(user.getKuName());
		rank.setItemRenderer(new RankRenderer());
		jiFenTime.initYearListbox("");
	}

	public void initWindow() {
		// if(project.getState()==Jxkh_Patent.SAVE_RECORD){
		// record.setVisible(true);
		// recordlabel.setVisible(true);
		// recordhbox.setVisible(true);
		// record.setValue(project.getRecordCode());
		// }
		jiFenTime.initYearListbox(project.getjxYear());
		projectName.setValue(project.getName());
		header.setValue(project.getPublishName());
		WkTUser infoWriter = jxkhProjectService
				.findWktUserByMemberUserId(project.getInfoWriter());
		writer.setValue(infoWriter.getKuName());
		if (project.getPublishDate() == null
				|| project.getPublishDate().length() == 0) {
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

		String memberName = "";
		String deptName = "";
		// 成员 部门
		projectMemberList = jxkhProjectService.findWritingMember(project);
		if (projectMemberList != null && projectMemberList.size() != 0) {
			for (int i = 0; i < projectMemberList.size(); i++) {
				Jxkh_Writer mem = (Jxkh_Writer) projectMemberList.get(i);
				memberName += mem.getName() + "、";
				WkTUser user = jxkhProjectService.findWktUserByMemberUserId(mem
						.getPersonId());
				memberList.add(user);
			}
			projectMember.setValue(memberName.substring(0,
					memberName.length() - 1));
		}
		projectDeptList = jxkhProjectService.findWritingDept(project);
		if (projectDeptList != null && projectDeptList.size() != 0) {
			for (int i = 0; i < projectDeptList.size(); i++) {
				Jxkh_WritingDept dept = (Jxkh_WritingDept) projectDeptList
						.get(i);
				deptName += dept.getName() + "、";
				WkTDept depts = jxkhProjectService.findWkTDeptByDept(dept
						.getDeptId());
				deptList.add(depts);
			}
			department.setValue(deptName.substring(0, deptName.length() - 1));
		}

		// 20120416
		String way = project.getScoreWay();
		if (way.equals("--请选择--")) {
			memRow.setVisible(false);
			contentRow.setVisible(false);
		}
		if (way.equals("默认比例")) {
			memRow.setVisible(false);
			contentRow.setVisible(false);
		}
		if (way.equals("参编人员比例")) {
			memRow.setVisible(true);
			contentRow.setVisible(false);
			memProp.setValue(project.getMemPercent());
		}
		if (way.equals("编写内容比例")) {
			memRow.setVisible(false);
			contentRow.setVisible(true);
			contentProp.setValue(project.getContentPercent());
		}
		if (project.getSort().getKbName().equals("省、院汇编"))
			identGroupbox.setVisible(true);
	}

	public void initShow() {
		print.setHref("/print.action?n=writing&id=" + project.getId());
		List<Jxkh_BusinessIndicator> typeList = new ArrayList<Jxkh_BusinessIndicator>();
		typeList.add(new Jxkh_BusinessIndicator());
		typeList.addAll(jxkhProjectService.findWSort());
		rank.setModel(new ListModelList(typeList));

		computMethod.setItemRenderer(new methodRenderer());
		String[] s = { "--请选择--", "默认比例", "参编人员比例", "编写内容比例" };
		List<String> methodList = new ArrayList<String>();
		for (int i = 0; i < 4; i++) {
			methodList.add(s[i]);
		}
		computMethod.setModel(new ListModelList(methodList));
		computMethod.setSelectedIndex(0);
	}

	/**
	 * <li>功能描述：关闭当前窗口。 void
	 * 
	 * @author songyu
	 */
	public void onClick$close() {
		this.onClose();
	}

	public void onClick$closeAudit() {
		this.onClose();
	}

	public void onClick$closeScore() {
		this.onClose();
	}

	/** 项目级别列表渲染器 */
	public class RankRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			Jxkh_BusinessIndicator type = (Jxkh_BusinessIndicator) data;
			item.setValue(type);
			Listcell c0 = new Listcell();
			if (type.getKbName() == null) {
				c0.setLabel("--请选择--");
			} else {
				c0.setLabel(type.getKbName());
			}
			item.appendChild(c0);
			if (project != null && type.equals(project.getSort())) {
				item.setSelected(true);
			}
		}
	}

	/** 绩分方式列表渲染器 */
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

	public Jxkh_Writing getProject() {
		return project;
	}

	public void setProject(Jxkh_Writing project) {
		this.project = project;
	}
}
