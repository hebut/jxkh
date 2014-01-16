package org.iti.jxkh.score.setup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxkh.entity.JXKH_AppraisalMember;
import org.iti.jxkh.entity.JXKH_AuditConfig;
import org.iti.jxkh.service.AuditConfigService;
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

public class StepupNewWindow extends Window implements AfterCompose  {

	/**
	 * @author WXY
	 */
	private static final long serialVersionUID = -8211295795490841685L;
/******************************考核人员设定******************************************/
	private YearListbox yearlist;
	private Listbox deptListbox1,deptListbox2,listbox1,listbox2;
	private JxkhAwardService jxkhAwardService;
	private String userIdSearch = "";
	private List<WkTUser> userlist = new ArrayList<WkTUser>();
	private Textbox userName,staffId;
	List<JXKH_AppraisalMember> tempMem = new ArrayList<JXKH_AppraisalMember>();
/**************************考核参数设定***************************************************/
	private JXKH_AuditConfig ac;
	private AuditConfigService auditConfigService;
	
	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		yearlist.initYearListbox("");//年度列表
		deptListbox1.setItemRenderer(new deptRenderer());
		deptListbox2.setItemRenderer(new deptRenderer());
	    listbox1.setItemRenderer(new managerRenderer());//人员列表渲染器
	    listbox2.setItemRenderer(new choosedManagerRenderer());//已选人员列表渲染器
	    initDeptWindow();
	    initListWindow();
	   
	}
	public void onSelect$yearlist() {
		String year =(String) yearlist.getSelectedItem().getValue();
		ac = auditConfigService.findByYear(year);
	}
	public void onClick$save() {
		if (yearlist.getSelectedIndex() == 0||yearlist.getSelectedItem()==null) {
			try {
				Messagebox.show("请选择年份！");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		if (ac == null) {
			ac = new JXKH_AuditConfig();
		}
		String year =(String) yearlist.getSelectedItem().getValue();
		ac.setAcYear(year);
		
		auditConfigService.saveOrUpdate(ac);
		try {
			Messagebox.show("保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/*************************************考核人员设定*****************************************/
	public void onSelect$deptListbox2(){//点击部门列表
		WkTDept deptment = (WkTDept) deptListbox2.getSelectedItem().getValue();
		List<JXKH_AppraisalMember>dlist=jxkhAwardService.findpeoByDept(deptment.getKdName());
		listbox2.setModel(new ListModelList(dlist));
	}
	public void onClick$add() throws InterruptedException{//添加按钮
		if(yearlist.getSelectYear()==null){
			Messagebox.show("请先选择年份！");
		}else
		{
		if(listbox1.getSelectedItems()==null||listbox1.getSelectedItems().size()<=0){
			try {
				Messagebox.show("请选择要添加的人员！", "提示", Messagebox.OK,
						Messagebox.EXCLAMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}else{
			@SuppressWarnings("unchecked")
			Iterator<Listitem> items = listbox1.getSelectedItems().iterator();
			WkTUser m = new WkTUser();
			while (items.hasNext()) {
				m = (WkTUser) items.next().getValue();
				JXKH_AppraisalMember appraisalMember = new JXKH_AppraisalMember();
				appraisalMember.setName(m.getKuName());
				appraisalMember.setPersonId(m.getKuLid());
				appraisalMember.setKuId(m.getKuId());
				appraisalMember.setDept(m.getDept().getKdName());
				appraisalMember.setKuType(m.getKuType());
				appraisalMember.setYear(yearlist.getSelectYear());
				appraisalMember.setDeptId(m.getDept().getKdId());//部门id
				jxkhAwardService.saveOrUpdate(appraisalMember);
			}
		    try {
				Messagebox.show("考核人员添加成功,已刷新！", "提示", Messagebox.OK,
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
			initListWindow();
		}
		}
	}
	public void onClick$delete() throws InterruptedException{//删除按钮
		if(listbox2.getSelectedItems()==null||listbox2.getSelectedItems().size()<=0){
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
								jxkhAwardService.delete(mem);
							}
							initListWindow();
					        try {
								Messagebox.show("考核人员删除成功！", "提示",
										Messagebox.OK, Messagebox.INFORMATION);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				});
	}
	public void initListWindow(){
		deptListbox1.setSelectedIndex(0);
		deptListbox2.setSelectedIndex(0);
		 //初始化已选人员列表
		List<JXKH_AppraisalMember> setMem = jxkhAwardService.findAllAppraisalMember();
		listbox2.setModel(new ListModelList(setMem));
	    //初始化人员列表
		if (setMem.size() != 0) {
			for (int i = 0; i < setMem.size(); i++) {
			JXKH_AppraisalMember user = (JXKH_AppraisalMember) setMem.get(i);
			userIdSearch += "'" + user.getPersonId() + "',";
			}
			if (userIdSearch.endsWith(",")){
			userIdSearch = userIdSearch.substring(0,userIdSearch.length() - 1);}
		}
		userlist = jxkhAwardService.findWkTUserNotInListBox2(userIdSearch);
	    listbox1.setModel(new ListModelList(userlist));
	    userIdSearch="";//
	}
	public void initDeptWindow(){
		List<WkTDept> deptlist = new ArrayList<WkTDept>();
		WkTDept dept1 = new WkTDept();
		deptlist.add(dept1);
		List<WkTDept> dlist = jxkhAwardService.findDeptAll();
		deptlist.addAll(dlist);
		deptListbox1.setModel(new ListModelList(deptlist));
		deptListbox1.setSelectedIndex(0);
		deptListbox2.setModel(new ListModelList(deptlist));
		deptListbox2.setSelectedIndex(0);
		
	}
	public class deptRenderer implements ListitemRenderer{//部门列表渲染器
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
	}
    public class managerRenderer implements ListitemRenderer{//人员列表渲染器
		public void render(Listitem item, Object data) throws Exception {
			if(data==null)return;
			WkTUser user = (WkTUser) data;
			item.setValue(user);
			Listcell c0 = new Listcell();
			Listcell c1 = new Listcell(user.getKuName());//人员姓名
			Listcell c2 = new Listcell(user.getStaffId());//人员编号
			Listcell c3 = new Listcell();//人员类型
			if(user.getKuType()==0){
				c3.setLabel("在编");
			}
			else if(user.getKuType()==1){
				c3.setLabel("外聘");
			}
			Listcell c4 = new Listcell(user.getDept1() == null ? "" : user.getDept1().getKdName());
			item.appendChild(c0);
			item.appendChild(c1);
			item.appendChild(c2);
			item.appendChild(c3);
			item.appendChild(c4);
		}
    }
    public class choosedManagerRenderer implements ListitemRenderer{//已选人员列表渲染器
	   public void render(Listitem item, Object data) throws Exception {
		if(data==null)return;
		JXKH_AppraisalMember user = (JXKH_AppraisalMember) data;
		item.setValue(user);
		Listcell c0 = new Listcell();
		Listcell c1 = new Listcell(user.getName());//人员姓名
		Listcell c2 = new Listcell(user.getPersonId());//人员编号
		Listcell c3 = new Listcell();//人员类型
		if(user.getKuType()==0){
			c3.setLabel("在职");
		}
		else if(user.getKuType()==1){
			c3.setLabel("外聘");
		}
		else{
			c3.setLabel(String.valueOf(user.getKuType()));
		}
		Listcell c4 = new Listcell(user.getDept());//所在部门
		item.appendChild(c0);
		item.appendChild(c1);
		item.appendChild(c2);
		item.appendChild(c3);
		item.appendChild(c4);
	}
    }
    public void onClick$view(){//查询按钮
    	
    	/*List<JXKH_AppraisalMember> setMem = jxkhAwardService.findAllAppraisalMember();
    	jxkhAwardService.deleteAll(setMem);*/
    	
    	WkTDept deptment = (WkTDept) deptListbox1.getSelectedItem().getValue();
		List<WkTUser> dlist = jxkhAwardService.findWkTUserByConditions(staffId.getValue(), userName.getValue(), deptment.getKdId(),"");
		listbox1.setModel(new ListModelList(dlist));
    }
}
