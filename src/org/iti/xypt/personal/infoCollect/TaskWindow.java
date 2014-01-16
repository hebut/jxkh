package org.iti.xypt.personal.infoCollect;

/**
 * <li>站点管理打开主页显示的各个站点权限分配情况的窗口
 * @author whm
 * @2010-07-20
 */

import java.util.ArrayList;
import java.util.List;

import org.iti.xypt.personal.infoCollect.entity.WkTExtractask;
import org.iti.xypt.personal.infoCollect.service.TaskService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTAuth;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTMlog;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.AuthService;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.service.MLogService;
import com.uniwin.framework.service.RoleService;
import com.uniwin.framework.service.WebsiteService;

public class TaskWindow extends Window implements AfterCompose {

	private static final long serialVersionUID = 1L;
    //站点权限列表
	private Listbox wlist;
	private ListModelList wmodellist;
	// 页面中显示标题的父站点编号，0则全部显示
	private Long wpid;
	// 权限数据访问接口
	private AuthService authService;
	TaskService taskService;
	//站点数据访问接口
	private WebsiteService websiteService;
	//部门数据访问接口
	DepartmentService departmentService;
	//管理日志访问接口
	private MLogService mlogService;
	// 获取编辑的站点列表
	List cklist = new ArrayList();
	// 用户所在部门及下级部门列表
	private List userDeptList;
	RoleService roleService;
   //增加、权限、删除按钮
	private Toolbarbutton addnew,auth,delete;
	private Button newcom;
//	private WkTWebsite editwebsite;
	private WkTUser user;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);		
		user=(WkTUser)Sessions.getCurrent().getAttribute("user");
		userDeptList=(List)Sessions.getCurrent().getAttribute("userDeptList");
		
		//初始化站点权限搜索下拉框
		wmodellist = new ListModelList();
		wlist.setItemRenderer(new TaskAuthListRenderer());
		wlist.setModel(wmodellist);
	
		newcom.addEventListener(Events.ON_CLICK, new EventListener()
		{
			public void onEvent(Event event) throws Exception {
				
				Window w = (Window) Executions.createComponents("/admin/personal/infoExtract/task/newcommit.zul", null,null);
		        w.doHighlighted();
			}
		});
	}
	
   

	
   /**初始化任务管理首页窗口*/
	public void initWindow(Long wpId) {
		List cList;
		wmodellist.clear();
		cklist.clear();
		this.wpid = wpId;
		if(wpId.intValue()==0){
			cList=taskService.findAll(WkTExtractask.class);	
		}else{
			cList=taskService.getChildTask(wpId);	
		}
		if(cList!=null&&cList.size()!=0){
			List rlist=roleService.getUserRoleId(user.getKuId());
			wmodellist.addAll(taskService.getTaskOfAllManage());
		}
	}

	/** 任务分类权限列表数据绑定类*/
	class TaskAuthListRenderer implements ListitemRenderer {

		public void render(Listitem item, Object data) throws Exception {

			final WkTExtractask w = (WkTExtractask) data;
			item.setValue(w);
			item.setHeight("25px");
			List cauth = authService.getAuthOfTask(userDeptList, w.getKeId());
			if (cauth.size() == 0) {
				item.setVisible(false);
			}
			Listcell c0 = new Listcell(item.getIndex() + 1 + "");
			Listcell c1 = new Listcell(taskService.getTpyeById(w.getKtaId()).getKtaName());
			Listcell c2 = new Listcell();
			Vbox vb1 = new Vbox();
			Checkbox cb1 = new Checkbox();
			cb1.setLabel(w.getKeName());
			vb1.appendChild(cb1);
			c2.appendChild(vb1);
			Listcell c3 = new Listcell();
			Listcell c4 = new Listcell();
			Listcell c5 = new Listcell();
			Listcell c6 = new Listcell();
			Listcell c7= new Listcell();
			Vbox vb2 = new Vbox();
			c3.appendChild(vb2);
			Vbox vb3 = new Vbox();
			vb3.setSpacing("10");
			c4.appendChild(vb3);
			Vbox vb4 = new Vbox();
			Vbox vb5 = new Vbox();
			Vbox vb6 = new Vbox();
			c5.appendChild(vb4);
			c6.appendChild(vb5);
			c7.appendChild(vb6);
			// 角色/部门/IP列数据
			final List cboxs = new ArrayList();
			for (int i = 0; i < cauth.size(); i++)
			{
				WkTAuth au = (WkTAuth)cauth.get(i);
				Checkbox cb2 = new Checkbox();
				cb2.setLabel(getAuthRoleName(au.getKrId()) + "/"+ getAuthDeptName(au.getKdId()) + "/" + au.getIP());
				vb2.appendChild(cb2);
				cb2.setAttribute("auth", au);
				cklist.add(cb2);
				cboxs.add(cb2);
				// 权限列
				StringBuffer sb = new StringBuffer("");
				if (au.getKaCode2() != null && au.getKaCode2().intValue() == 1) 
				{
					sb.append("管理  ");
				}
				if (au.getKaCode3() != null && au.getKaCode3().intValue() == 1)
				{
					sb.append("审核  ");
				}
				vb3.appendChild(new Label(sb.toString()));
				Hbox hb = new Hbox();
				Hbox hb1 = new Hbox();
				Hbox hb2 = new Hbox();
				Toolbarbutton b1 = new Toolbarbutton();
				b1.setImage("/css/img/issue_ico.gif");
				b1.setTooltiptext("点击增加新权限");
				// 权限列表中每一行中的增加按钮触发的事件
				b1.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event event) throws Exception {

						WkTAuth auth = new WkTAuth();
						auth.setKrId(0L);
						auth.setKdId(0L);
						auth.setKaRid(w.getKeId());
						auth.setKaCode1(TaskAuthEditWindow.uncheck);
						auth.setKaCode2(TaskAuthEditWindow.uncheck);
						auth.setKaCode3(TaskAuthEditWindow.uncheck);
						final TaskAuthUpdateWindow w = (TaskAuthUpdateWindow) Executions
								.createComponents(
										"/admin/personal/infoExtract/task/taskauthupdate.zul",
										null, null);
						w.doHighlighted();
						w.ininWindow(auth);
						w.addEventListener(Events.ON_CHANGE,
								new EventListener() {
									public void onEvent(Event event)
											throws Exception {
										initWindow(wpid);
										refleshTree();
										w.detach();
									}
								});
					}
				});

				Toolbarbutton b2 = new Toolbarbutton();
				b2.setImage("/css/img/del.gif");
				b2.setAttribute("auth", au);
				b2.setTooltiptext("点击删除当前权限");
				// 权限列表中每一行中的删除按钮触发的事件
				b2.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event event) throws Exception {
						if (Messagebox.show("您确定要删除吗?", "请确认", Messagebox.OK|Messagebox.CANCEL, Messagebox.QUESTION) == 1) {
							Toolbarbutton bu = (Toolbarbutton) event.getTarget();
							WkTAuth au = (WkTAuth) bu.getAttribute("auth");
							authService.delete(au);
							mlogService.saveMLog(WkTMlog.FUNC_CMS,"删除任务权限，id: " + au.getKaId(), user);
							initWindow(wpid);
							refleshTree();
						}
					}
				});
				Toolbarbutton b3 = new Toolbarbutton();
				b3.setImage("/css/img/modify_ico.gif");
				b3.setAttribute("auth", au);
				b3.setTooltiptext("点击修改当前权限");
				// 权限列表中每一行中的修改按钮触发的事件
				b3.addEventListener(Events.ON_CLICK, new EventListener() {

					public void onEvent(Event event) throws Exception {
						Toolbarbutton bu = (Toolbarbutton) event.getTarget();
						WkTAuth au = (WkTAuth) bu.getAttribute("auth");
						final TaskAuthUpdateWindow w = (TaskAuthUpdateWindow) Executions.createComponents(
										"/admin/personal/infoExtract/task/taskauthupdate.zul",null, null);
						w.doHighlighted();
						w.ininWindow(au);
						w.addEventListener(Events.ON_CHANGE,
								new EventListener() {
									public void onEvent(Event event)
											throws Exception {
										initWindow(wpid);
										refleshTree();
										w.detach();
									}
								});
					}
				});
				hb.appendChild(b1);
				hb1.appendChild(b2);
				hb2.appendChild(b3);
				vb4.appendChild(hb);
				vb5.appendChild(hb1);
				vb6.appendChild(hb2);
			}
			// 权限列表中，选择站点名称时，相对应的角色/部门/IP也同时被选定
			cb1.addEventListener(Events.ON_CHECK, new EventListener() {
				public void onEvent(Event event) throws Exception {
					Checkbox ck = (Checkbox) event.getTarget();
					for (int i = 0; i < cboxs.size(); i++) {
						Checkbox c = (Checkbox) cboxs.get(i);
						c.setChecked(ck.isChecked());
					}
				}
			});
			item.appendChild(c0);
			item.appendChild(c1);
			item.appendChild(c2);
			item.appendChild(c3);
			item.appendChild(c4);
			item.appendChild(c5);
			item.appendChild(c6);
			item.appendChild(c7);
		}
	}

	/** 删除某一任务的所有权限*/
	public void onClick$delete() throws InterruptedException {
		        List cbox = new ArrayList();
				for (int i = 0; i < cklist.size(); i++) 
				{
					Checkbox cb = (Checkbox) cklist.get(i);
					if(cb.isChecked()){
						cbox.add(cb);
						}
					}
				if(cbox.size()==0)
				{
					Messagebox.show("请选择权限！", "提示", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
				else
				{
					if (Messagebox.show("您确定要删除吗?", "请确认", Messagebox.OK|Messagebox.CANCEL, Messagebox.QUESTION) == 1) {
						for(int j=0;j<cbox.size();j++){
							Checkbox cb1 = (Checkbox) cbox.get(j);
						    WkTAuth au = (WkTAuth) cb1.getAttribute("auth");
						    authService.delete(au);
						    initWindow(Long.parseLong("0"));
						    refleshTree();
						    }
						}
					}
	}

	/**获取具有某种角色的部门名称*/
	public String getAuthRoleName(Long rid) 
	{
		if (rid.intValue() == 0)
			return "";
		WkTRole r = (WkTRole) authService.get(WkTRole.class, rid);
		return r.getKrName();
	}

	/**获取具有某种权限的部门名称*/
	public String getAuthDeptName(Long did) 
	{
		if (did.intValue() == 0)
			return "";
		WkTDept r = (WkTDept) authService.get(WkTDept.class, did);
		return r.getKdName();
	}
public void onClick$addnew()
{
			NewSortWindow w=(NewSortWindow) Executions.createComponents("/admin/personal/infoExtract/task/newsort.zul", null,null);
			w.doHighlighted();
            w.initWindow(null);
			w.addEventListener(Events.ON_CHANGE, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					refleshTree();
				}
			});
}
	/** 点击 权限按钮出发的事件*/
	public void onClick$auth() 
	{
		TaskAuthEditWindow w = (TaskAuthEditWindow) Executions.createComponents("/admin/personal/infoExtract/task/taskauthedit.zul", null, null);
		w.doHighlighted();
		w.initWindow(null);
		w.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event event) throws Exception {
				initWindow(Long.parseLong("0"));
				refleshTree();
			}
		});
	}
	/**刷新树*/
	public void refleshTree() 
	{
		Events.postEvent(Events.ON_CHANGE, this, null);
	}
	
}
