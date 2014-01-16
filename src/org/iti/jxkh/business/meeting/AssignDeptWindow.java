package org.iti.jxkh.business.meeting;

import java.util.ArrayList;
import java.util.List;

import org.iti.jxkh.entity.JXKH_HULWMember;
import org.iti.jxkh.entity.JXKH_HYLWDept;
import org.iti.jxkh.entity.JXKH_MeetingDept;
import org.iti.jxkh.entity.JXKH_MeetingMember;
import org.iti.jxkh.entity.JXKH_QKLWDept;
import org.iti.jxkh.entity.JXKH_QKLWMember;
import org.iti.jxkh.entity.Jxkh_AwardDept;
import org.iti.jxkh.entity.Jxkh_AwardMember;
import org.iti.jxkh.entity.Jxkh_FruitDept;
import org.iti.jxkh.entity.Jxkh_FruitMember;
import org.iti.jxkh.entity.Jxkh_PatentDept;
import org.iti.jxkh.entity.Jxkh_PatentInventor;
import org.iti.jxkh.entity.Jxkh_ProjectDept;
import org.iti.jxkh.entity.Jxkh_ProjectMember;
import org.iti.jxkh.entity.Jxkh_ReportDept;
import org.iti.jxkh.entity.Jxkh_ReportMember;
import org.iti.jxkh.entity.Jxkh_VideoDept;
import org.iti.jxkh.entity.Jxkh_VideoMember;
import org.iti.jxkh.entity.Jxkh_Writer;
import org.iti.jxkh.entity.Jxkh_WritingDept;
import org.iti.jxkh.service.JXKHMeetingService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

public class AssignDeptWindow extends Window implements AfterCompose {

	/**
	 * @author CXX
	 */
	private static final long serialVersionUID = 5629119187289466990L;
	private Listbox deptListbox;
	private List<?> dept;
	private String flag = "";
	private Object member;
	private JXKHMeetingService jxkhMeetingService;
	private Toolbarbutton save;
	private short state;

	public void setState(short state) {
		this.state = state;
	}//传过来一个状态值，根据其状态值，显隐保存按钮。业务办一直保持着最高权限，审核状态不影响保存按钮的显隐性

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		deptListbox.setItemRenderer(new deptRenderer());
	}

	public void initWindow() {
		if (state == 1 || state == 2 || state == 4 || state == 6)
			save.setVisible(false);
		List<String> list = new ArrayList<String>();
		list.add("");
		if (flag.trim().equals("M")) {
			for (int i = 0; i < dept.size(); i++) {
				JXKH_MeetingDept d = (JXKH_MeetingDept) dept.get(i);
				list.add(d.getName());
			}
			deptListbox.setModel(new ListModelList(list));
		} else if (flag.trim().equals("AWARD")) {
			for (int i = 0; i < dept.size(); i++) {
				Jxkh_AwardDept d = (Jxkh_AwardDept) dept.get(i);
				list.add(d.getName());
			}
			deptListbox.setModel(new ListModelList(list));
		} else if (flag.trim().equals("FRUIT")) {
			for (int i = 0; i < dept.size(); i++) {
				Jxkh_FruitDept d = (Jxkh_FruitDept) dept.get(i);
				list.add(d.getName());
			}
			deptListbox.setModel(new ListModelList(list));
		} else if (flag.trim().equals("REPORT")) {
			for (int i = 0; i < dept.size(); i++) {
				Jxkh_ReportDept d = (Jxkh_ReportDept) dept.get(i);
				list.add(d.getName());
			}
			deptListbox.setModel(new ListModelList(list));
		} else if (flag.trim().equals("VIDEO")) {
			for (int i = 0; i < dept.size(); i++) {
				Jxkh_VideoDept d = (Jxkh_VideoDept) dept.get(i);
				list.add(d.getName());
			}
			deptListbox.setModel(new ListModelList(list));
		} else if (flag.trim().equals("QKLW")) {
			for (int i = 0; i < dept.size(); i++) {
				JXKH_QKLWDept d = (JXKH_QKLWDept) dept.get(i);
				list.add(d.getName());
			}
			deptListbox.setModel(new ListModelList(list));
		} else if (flag.trim().equals("HYLW")) {
			for (int i = 0; i < dept.size(); i++) {
				JXKH_HYLWDept d = (JXKH_HYLWDept) dept.get(i);
				list.add(d.getName());
			}
			deptListbox.setModel(new ListModelList(list));
		} else if (flag.trim().equals("zp")) {
			for (int i = 0; i < dept.size(); i++) {
				Jxkh_ProjectDept d = (Jxkh_ProjectDept) dept.get(i);
				list.add(d.getName());
			}
			deptListbox.setModel(new ListModelList(list));
		} else if (flag.trim().equals("hp")) {
			for (int i = 0; i < dept.size(); i++) {
				Jxkh_ProjectDept d = (Jxkh_ProjectDept) dept.get(i);
				list.add(d.getName());
			}
			deptListbox.setModel(new ListModelList(list));
		} else if (flag.trim().equals("cp")) {
			for (int i = 0; i < dept.size(); i++) {
				Jxkh_ProjectDept d = (Jxkh_ProjectDept) dept.get(i);
				list.add(d.getName());
			}
			deptListbox.setModel(new ListModelList(list));
		} else if (flag.trim().equals("patent")) {
			for (int i = 0; i < dept.size(); i++) {
				Jxkh_PatentDept d = (Jxkh_PatentDept) dept.get(i);
				list.add(d.getName());
			}
			deptListbox.setModel(new ListModelList(list));
		} else if (flag.trim().equals("writing")) {
			for (int i = 0; i < dept.size(); i++) {
				Jxkh_WritingDept d = (Jxkh_WritingDept) dept.get(i);
				list.add(d.getName());
			}
			deptListbox.setModel(new ListModelList(list));
		}
	}

	public void onClick$save() {
		if (member instanceof JXKH_MeetingMember) {
			JXKH_MeetingMember m = (JXKH_MeetingMember) member;
			m.setAssignDep((String) deptListbox.getSelectedItem().getValue());
			jxkhMeetingService.update(m);
		} else if (member instanceof Jxkh_AwardMember) {
			Jxkh_AwardMember m = (Jxkh_AwardMember) member;
			m.setAssignDep((String) deptListbox.getSelectedItem().getValue());
			jxkhMeetingService.update(m);
		} else if (member instanceof Jxkh_FruitMember) {
			Jxkh_FruitMember m = (Jxkh_FruitMember) member;
			m.setAssignDep((String) deptListbox.getSelectedItem().getValue());
			jxkhMeetingService.update(m);
		} else if (member instanceof Jxkh_ReportMember) {
			Jxkh_ReportMember m = (Jxkh_ReportMember) member;
			m.setAssignDep((String) deptListbox.getSelectedItem().getValue());
			jxkhMeetingService.update(m);
		} else if (member instanceof Jxkh_VideoMember) {
			Jxkh_VideoMember m = (Jxkh_VideoMember) member;
			m.setAssignDep((String) deptListbox.getSelectedItem().getValue());
			jxkhMeetingService.update(m);
		} else if (member instanceof JXKH_HULWMember) {
			JXKH_HULWMember m = (JXKH_HULWMember) member;
			m.setAssignDep((String) deptListbox.getSelectedItem().getValue());
			jxkhMeetingService.update(m);
		} else if (member instanceof JXKH_QKLWMember) {
			JXKH_QKLWMember m = (JXKH_QKLWMember) member;
			m.setAssignDep((String) deptListbox.getSelectedItem().getValue());
			jxkhMeetingService.update(m);
		} else if (member instanceof Jxkh_ProjectMember) {
			Jxkh_ProjectMember m = (Jxkh_ProjectMember) member;
			m.setAssignDep((String) deptListbox.getSelectedItem().getValue());
			jxkhMeetingService.update(m);
		} else if (member instanceof Jxkh_PatentInventor) {
			Jxkh_PatentInventor m = (Jxkh_PatentInventor) member;
			m.setAssignDep((String) deptListbox.getSelectedItem().getValue());
			jxkhMeetingService.update(m);
		} else if (member instanceof Jxkh_Writer) {
			Jxkh_Writer m = (Jxkh_Writer) member;
			m.setAssignDep((String) deptListbox.getSelectedItem().getValue());
			jxkhMeetingService.update(m);
		}
		try {
			Messagebox.show("保存成功！", "提示", Messagebox.OK,
					Messagebox.INFORMATION);
			Events.postEvent(Events.ON_CHANGE, this, null);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.detach();
	}

	/** 论文等级列表渲染器 */
	public class deptRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			String rank = (String) data;
			item.setValue(rank);
			// if (rank == null || rank == "")
			// rank = "--请选择--";
			// item.setLabel(rank);
			Listcell c0 = new Listcell();
			if (rank == null || rank.equals(""))
				c0.setLabel("--请选择--");
			else {
				c0.setLabel(rank);
			}
			item.appendChild(c0);
			if (item.getIndex() == 0)
				item.setSelected(true);
			if (member instanceof JXKH_MeetingMember) {
				JXKH_MeetingMember m = (JXKH_MeetingMember) member;
				if (m.getAssignDep() != null && m.getAssignDep().equals(rank))
					item.setSelected(true);
			} else if (member instanceof Jxkh_AwardMember) {
				Jxkh_AwardMember m = (Jxkh_AwardMember) member;
				if (m.getAssignDep() != null && m.getAssignDep().equals(rank))
					item.setSelected(true);
			} else if (member instanceof Jxkh_FruitMember) {
				Jxkh_FruitMember m = (Jxkh_FruitMember) member;
				if (m.getAssignDep() != null && m.getAssignDep().equals(rank))
					item.setSelected(true);
			} else if (member instanceof Jxkh_ReportMember) {
				Jxkh_ReportMember m = (Jxkh_ReportMember) member;
				if (m.getAssignDep() != null && m.getAssignDep().equals(rank))
					item.setSelected(true);
			} else if (member instanceof Jxkh_VideoMember) {
				Jxkh_VideoMember m = (Jxkh_VideoMember) member;
				if (m.getAssignDep() != null && m.getAssignDep().equals(rank))
					item.setSelected(true);
			} else if (member instanceof JXKH_HULWMember) {
				JXKH_HULWMember m = (JXKH_HULWMember) member;
				if (m.getAssignDep() != null && m.getAssignDep().equals(rank))
					item.setSelected(true);
			} else if (member instanceof JXKH_QKLWMember) {
				JXKH_QKLWMember m = (JXKH_QKLWMember) member;
				if (m.getAssignDep() != null && m.getAssignDep().equals(rank))
					item.setSelected(true);
			} else if (member instanceof Jxkh_ProjectMember) {
				Jxkh_ProjectMember m = (Jxkh_ProjectMember) member;
				if (m.getAssignDep() != null && m.getAssignDep().equals(rank))
					item.setSelected(true);
			} else if (member instanceof Jxkh_PatentInventor) {
				Jxkh_PatentInventor m = (Jxkh_PatentInventor) member;
				if (m.getAssignDep() != null && m.getAssignDep().equals(rank))
					item.setSelected(true);
			} else if (member instanceof Jxkh_Writer) {
				Jxkh_Writer m = (Jxkh_Writer) member;
				if (m.getAssignDep() != null && m.getAssignDep().equals(rank))
					item.setSelected(true);
			}
		}
	}

	public void onClick$close() {
		this.onClose();
	}

	public void setDept(List<?> dept) {
		this.dept = dept;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public void setMember(Object member) {
		this.member = member;
	}
}
