package org.iti.jxkh.busiAudit.patent;

import java.util.ArrayList;
import java.util.List;

import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.entity.Jxkh_Patent;
import org.iti.jxkh.entity.Jxkh_PatentDept;
import org.iti.jxkh.entity.Jxkh_PatentInventor;
import org.iti.jxkh.service.JxkhProjectService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Sessions;
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
	private Textbox projectName, projectMember, department, coUnit, projecCode,
			header;
	private Label writer;
	private Datebox begin, end;
	private Listbox rank;
	private Radiogroup ifCoo;
	private Row coUnitRow, recordlabel;
	private Button print;
	private YearListbox jiFenTime;
	private Jxkh_Patent project;
	// private Label recordlabel;
	private Hbox recordhbox;
	private Textbox record;

	private List<WkTUser> memberList = new ArrayList<WkTUser>();
	private List<WkTDept> deptList = new ArrayList<WkTDept>();
	private List<Jxkh_PatentInventor> projectMemberList = new ArrayList<Jxkh_PatentInventor>();
	private List<Jxkh_PatentDept> projectDeptList = new ArrayList<Jxkh_PatentDept>();

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
		if (project.getState() == Jxkh_Patent.SAVE_RECORD) {
			record.setVisible(true);
			recordlabel.setVisible(true);
			recordhbox.setVisible(true);
			record.setValue(project.getRecordCode());
		}
		jiFenTime.initYearListbox(project.getjxYear());
		projectName.setValue(project.getName());
		projecCode.setValue(project.getGrantCode());
		header.setValue(project.getOwner());
		writer.setValue(project.getInfoWriter());
		if (project.getApplyDate() == null
				|| project.getApplyDate().length() == 0) {
			begin.setValue(null);
		} else {
			begin.setValue(ConvertUtil.convertDate(project.getApplyDate()));
		}
		if (project.getGrantDate() == null
				|| project.getGrantDate().length() == 0) {
			end.setValue(null);
		} else {
			end.setValue(ConvertUtil.convertDate(project.getGrantDate()));
		}

		if (project.getCooState() != null) {
			if (project.getCooState() == Jxkh_Patent.YES) {
				ifCoo.setSelectedIndex(0);
				coUnitRow.setVisible(true);
				coUnit.setValue(project.getCooCompany());
			} else {
				ifCoo.setSelectedIndex(1);
			}
		}

		String memberName = "";
		String deptName = "";
		// 成员 部门
		projectMemberList = jxkhProjectService.findPatentMember(project);
		if (projectMemberList != null && projectMemberList.size() != 0) {
			for (int i = 0; i < projectMemberList.size(); i++) {
				Jxkh_PatentInventor mem = (Jxkh_PatentInventor) projectMemberList
						.get(i);
				memberName += mem.getName() + "、";
				WkTUser user = jxkhProjectService.findWktUserByMemberUserId(mem
						.getPersonId());
				memberList.add(user);
			}
			projectMember.setValue(memberName.substring(0,
					memberName.length() - 1));
		}
		projectDeptList = jxkhProjectService.findPatentDept(project);
		if (projectDeptList != null && projectDeptList.size() != 0) {
			for (int i = 0; i < projectDeptList.size(); i++) {
				Jxkh_PatentDept dept = (Jxkh_PatentDept) projectDeptList.get(i);
				deptName += dept.getName() + "、";
				WkTDept depts = jxkhProjectService.findWkTDeptByDept(dept
						.getDeptId());
				deptList.add(depts);
			}
			department.setValue(deptName.substring(0, deptName.length() - 1));
		}
	}

	public void initShow() {
		print.setHref("/print.action?n=patent&id=" + project.getId());
		List<Jxkh_BusinessIndicator> typeList = new ArrayList<Jxkh_BusinessIndicator>();
		typeList.add(new Jxkh_BusinessIndicator());
		typeList.addAll(jxkhProjectService.findSort());
		rank.setModel(new ListModelList(typeList));
	}

	/**
	 * <li>功能描述：关闭当前窗口。 void
	 * 
	 * @author songyu
	 */
	public void onClick$close() {
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

	public Jxkh_Patent getProject() {
		return project;
	}

	public void setProject(Jxkh_Patent project) {
		this.project = project;
	}
}
