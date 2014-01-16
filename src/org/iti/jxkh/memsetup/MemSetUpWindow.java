package org.iti.jxkh.memsetup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.iti.jxkh.entity.JXKH_AppraisalMember;
import org.iti.jxkh.service.JxkhAwardService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;

public class MemSetUpWindow extends Window implements AfterCompose {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2923889292560302215L;

	private Textbox staffId, name;
	private Listbox listbox1, listbox2, dept;
	private JxkhAwardService jxkhAwardService;
	private List<WkTUser> memberList = new ArrayList<WkTUser>();
	private String userIdSearch = "";
	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		
		listbox1.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				WkTUser user = (WkTUser) arg1;
				Listcell c0 = new Listcell();
				Listcell c1 = new Listcell(arg0.getIndex() + 1 + "");
				Listcell c2 = new Listcell(user.getKuName());
				Listcell c3 = new Listcell(user.getStaffId());
				Listcell c4 = new Listcell(user.getDept1() == null ? "" : user
						.getDept1().getKdName());
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
				arg0.setValue(arg1);
			}
		});
		listbox2.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				JXKH_AppraisalMember user = (JXKH_AppraisalMember) arg1;
				arg0.setValue(user);
				Listcell c0 = new Listcell();
				Listcell c1 = new Listcell(arg0.getIndex() + 1 + "");
				Listcell c2 = new Listcell(user.getName());
				Listcell c3 = new Listcell(user.getPersonId());
				Listcell c4 = new Listcell(user.getDept());
				
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
			}
		});
		
		dept.setItemRenderer(new ListitemRenderer() {
			@Override
			public void render(Listitem arg0, Object arg1) throws Exception {
				WkTDept dept = (WkTDept) arg1;
				arg0.setValue(dept);
				Listcell c0 = new Listcell();
				if (dept.getKdName() == null) {
					c0 = new Listcell("--请选择--");
				} else {
					c0 = new Listcell(dept.getKdName());
				}
				arg0.appendChild(c0);
			}

		});
		List<WkTDept> deptlist = new ArrayList<WkTDept>();
		WkTDept dept1 = new WkTDept();
		deptlist.add(dept1);
		List<WkTDept> dlist = jxkhAwardService.findDept();
		deptlist.addAll(dlist);
		dept.setModel(new ListModelList(deptlist));
		dept.setSelectedIndex(0);
		initWindow();
	}

	public void initWindow() {
		// 初始化已选人员
		List<JXKH_AppraisalMember> setMem = jxkhAwardService.findAllAppraisalMember();
		listbox2.setModel(new ListModelList(setMem));
		// 初始化第一个listbox
		if (setMem.size() != 0) {
			for (int i = 0; i < setMem.size(); i++) {
				JXKH_AppraisalMember user = (JXKH_AppraisalMember) setMem.get(i);
				userIdSearch += "'" + user.getPersonId() + "',";
			}
			if (userIdSearch.endsWith(","))
				userIdSearch = userIdSearch.substring(0,userIdSearch.length() - 1);
		}
		dlist = jxkhAwardService.findWkTUserNotInListBox2(userIdSearch);
		listbox1.setModel(new ListModelList(dlist));
	}
	public void onClick$add() {//添加按钮
		if (listbox1.getSelectedItems() == null
				|| listbox1.getSelectedItems().size() <= 0) {
			try {
				Messagebox.show("请选择要添加的人员！", "提示", Messagebox.OK,
						Messagebox.EXCLAMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		@SuppressWarnings("unchecked")
		Iterator<Listitem> items = listbox1.getSelectedItems().iterator();
		WkTUser m = new WkTUser();
		while (items.hasNext()) {
			m = (WkTUser) items.next().getValue();
			JXKH_AppraisalMember appraisalMember = new JXKH_AppraisalMember();
			appraisalMember.setName(m.getKuName());
			appraisalMember.setPersonId(m.getKuLid());
			appraisalMember.setDept(m.getDept().getKdName());
			jxkhAwardService.saveOrUpdate(appraisalMember);
		}
		try {
			Messagebox.show("考核人员添加成功！", "提示", Messagebox.OK,
					Messagebox.INFORMATION);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				Messagebox.show("考核人员添加失败，请重试！", "提示", Messagebox.OK,
						Messagebox.ERROR);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		initWindow();
	}

	public void onClick$delete() throws InterruptedException {//删除按钮
		if (listbox2.getSelectedItems() == null
				|| listbox2.getSelectedItems().size() <= 0) {
			try {
				Messagebox.show("请选择要删除的考核人员！", "提示", Messagebox.OK,
						Messagebox.EXCLAMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}

		Messagebox.show("确定删除吗?", "确定", Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						if (evt.getName().equals("onOK")) {
							@SuppressWarnings("unchecked")
							Iterator<Listitem> items = listbox2
									.getSelectedItems().iterator();
							while (items.hasNext()) {
								JXKH_AppraisalMember mem = (JXKH_AppraisalMember) items.next().getValue();
								System.out.println(mem);
								jxkhAwardService.delete(mem);
							}
							try {
								Messagebox.show("考核人员删除成功！", "提示",
										Messagebox.OK, Messagebox.INFORMATION);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							initWindow();
						}
					}
				});
	}

	public void rebuild() {
		memberList.clear();
		Iterator<?> it = listbox2.getItems().iterator();
		while (it.hasNext()) {
			memberList.add((WkTUser) ((Listitem) it.next()).getValue());
		}
	}
	List<WkTUser> dlist = new ArrayList<WkTUser>();
	public void onClick$submit() {//（没用）
		rebuild();

		List<JXKH_AppraisalMember> mems = jxkhAwardService
				.findAllAppraisalMember();
		jxkhAwardService.deleteAll(mems);
		for (int j = 0; j < memberList.size(); j++) {
			WkTUser m = memberList.get(j);
			JXKH_AppraisalMember appraisalMember = new JXKH_AppraisalMember();
			appraisalMember.setName(m.getKuName());
			appraisalMember.setPersonId(m.getKuLid());
			appraisalMember.setDept(m.getDept().getKdName());
			jxkhAwardService.saveOrUpdate(appraisalMember);
		}

		try {
			Messagebox.show("考核人员保存成功！", "提示", Messagebox.OK,
					Messagebox.INFORMATION);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				Messagebox.show("考核人员保存失败，请重试！", "提示", Messagebox.OK,
						Messagebox.ERROR);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void onClick$query() {//查询按钮
		WkTDept deptment = (WkTDept) dept.getSelectedItem().getValue();
		List<WkTUser> dlist = jxkhAwardService.findWkTUserByConditions(staffId.getValue(), name.getValue(), deptment.getKdId(),userIdSearch);
		listbox1.setModel(new ListModelList(dlist));
	}

	public void onClick$reset() {//重置按钮
		memberList.clear();
		dept.setSelectedIndex(0);
		staffId.setValue("");
		name.setValue("");
	}
}
