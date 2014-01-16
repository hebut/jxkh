package org.iti.jxkh.business.meeting;

import java.util.List;
import org.iti.jxkh.entity.JXKH_MEETING;
import org.iti.jxkh.entity.JXKH_MeetingDept;
import org.iti.jxkh.service.JXKHMeetingService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;
import com.uniwin.framework.entity.WkTDept;

public class ChooseDeptWindow extends Window implements AfterCompose {

	/**
	 * @author CuiXiaoxin
	 */
	private static final long serialVersionUID = 3083865288199392300L;

	private Listbox listbox1, listbox2;
	private Button add, delete;
	private Textbox depName, code;
	private Toolbarbutton search;
	private JXKHMeetingService jxkhMeetingService;
	private JXKH_MEETING meeting;

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);

	}

	public void initWindow() {
	}

	/** 所有部门列表 */
	public void initAllDept() {
		List<WkTDept> deplist = jxkhMeetingService.findAllDep();
		if (deplist.size() != 0) {
			listbox1.setModel(new ListModelList(deplist));
			listbox1.setItemRenderer(new ListitemRenderer() {
				public void render(Listitem arg0, Object arg1) throws Exception {
					WkTDept dep = (WkTDept) arg1;
					List<JXKH_MeetingDept> mtdeplist = jxkhMeetingService
							.findMeetingDeptByMeetingId(meeting);
					int count = 0;
					for (int i = 0; i < mtdeplist.size(); i++) {
						JXKH_MeetingDept mem = (JXKH_MeetingDept) mtdeplist
								.get(i);
						if (dep.getKdName().trim().equals(mem.getName().trim()))
							count++;
					}
					if (count == 0) {
						Listcell c0 = new Listcell();
						Listcell c1 = new Listcell(arg0.getIndex() + 1 + "");
						Listcell c2 = new Listcell(dep.getKdNumber());
						Listcell c3 = new Listcell(dep.getKdName());
						arg0.setValue(dep);
						arg0.appendChild(c0);
						arg0.appendChild(c1);
						arg0.appendChild(c2);
						arg0.appendChild(c3);
					} else
						arg0.setVisible(false);
				}
			});
		}
	}

	/** 已选部门列表 */
	public void initCheckDept() throws InterruptedException {
		List<JXKH_MeetingDept> member = jxkhMeetingService
				.findMeetingDeptByMeetingId(meeting);
		if (member.size() != 0) {
			listbox2.setModel(new ListModelList(member));
			listbox2.setItemRenderer(new ListitemRenderer() {
				public void render(Listitem arg0, Object arg1) throws Exception {
					JXKH_MeetingDept member = (JXKH_MeetingDept) arg1;
					arg0.setValue(member);
					Listcell c0 = new Listcell();
					Listcell c1 = new Listcell(arg0.getIndex() + 1 + "");
					Listcell c2 = new Listcell(member.getDepId());
					Listcell c3 = new Listcell(member.getName());
					arg0.appendChild(c0);
					arg0.appendChild(c1);
					arg0.appendChild(c2);
					arg0.appendChild(c3);
				}
			});
		}
	}
}
