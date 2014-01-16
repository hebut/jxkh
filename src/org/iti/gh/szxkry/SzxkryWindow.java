package org.iti.gh.szxkry;

import java.util.List;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.entity.GhUserdept;
import org.iti.gh.entity.GhUserdeptId;
import org.iti.gh.service.UserdeptService;
import org.iti.xypt.entity.Teacher;
import org.iti.xypt.service.TeacherService;
import org.iti.xypt.service.XyUserRoleService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;

import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.entity.WkTUser;

public class SzxkryWindow extends BaseWindow {
	Listheader gradeTwoLabel, gradeThrLabel;
	Listbox teacherlist;
	TeacherService teacherService;
	UserdeptService userdeptService;
	XyUserRoleService xyUserRoleService;
	@Override
	public void initShow() {
		teacherlist.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhUserdept userdept=(GhUserdept)arg1;
				Teacher bst = (Teacher)teacherService.get(Teacher.class, userdept.getId().getKuId());
				Listcell c1 = new Listcell();
				c1.setLabel(arg0.getIndex() + 1 + "");
				// 指导教师编号
				Listcell c2 = new Listcell(bst.getThId());
				// 通过用户编号（KUID）得到用户对象（WKTUSER）
				Listcell c3 = new Listcell(bst.getUser().getKuName());
				Listcell c4 = new Listcell(bst.getTitle().getTiName());
				Listcell c5 = new Listcell(bst.getXiDept(bst.getKdId()));
				Listcell c6 = new Listcell(bst.getYuDept(bst.getKdId()));
				Listcell c7 = new Listcell();
					InnerButton bt1 = new InnerButton("删除");
					bt1.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event arg0) throws Exception {
							if (Messagebox.show("确定删除吗?", "提示", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == 1) {
								userdeptService.delete(userdept);
								initWindow();
							}
						}
					});
					c7.appendChild(bt1);
				
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
				arg0.appendChild(c5);
				arg0.appendChild(c6);
				arg0.appendChild(c7);
			}
		});

	}

	@Override
	public void initWindow() {
		List listtea=teacherService.findBykdidAndnotinUserdept(getXyUserRole().getKdId());
		if(listtea!=null&& listtea.size()!=0){
			for(int i=0;i<listtea.size();i++){
				WkTUser user=(WkTUser) listtea.get(i);
				GhUserdept dept =new GhUserdept();
				GhUserdeptId id=new GhUserdeptId();
				id.setKdId(getXyUserRole().getKdId());
				id.setKuId(user.getKuId());
				dept.setId(id);
				userdeptService.save(dept);
			}
		}
		gradeTwoLabel.setLabel(getSchoolDept().getGradeName(WkTDept.GRADE_YUAN));
		gradeThrLabel.setLabel(getSchoolDept().getGradeName(WkTDept.GRADE_XI));
		teacherlist.setModel(new ListModelList(userdeptService.findByKdId(getXyUserRole().getKdId())));

	}
	public void onClick$addTeacher() {
		final SelteaWindow win = (SelteaWindow) Executions.createComponents("/admin/xkgl/glyjfx/szxkry/szxkry.zul", null, null);
		win.setRole((WkTRole) xyUserRoleService.get(WkTRole.class, Teacher.getRoleId(getSchoolDept().getKdId())));
		win.setRootKdId(getSchoolDept().getKdId());
		win.setXykdid(getXyUserRole().getKdId());
		win.setTitle("选择学科人员");
		win.doHighlighted();
		win.initWindow();
		win.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				List slist = win.getSelectUser();
				for (int i = 0; i < slist.size(); i++) {
					Teacher t = (Teacher) slist.get(i);
					GhUserdept newdept=new GhUserdept();
					GhUserdeptId newdeptid = new GhUserdeptId();
					newdeptid.setKdId(getXyUserRole().getKdId());
					newdeptid.setKuId(t.getKuId());
					newdept.setId(newdeptid);
					userdeptService.save(newdept);
				}
				win.detach();
				initWindow();
			}
		});
	}

}
