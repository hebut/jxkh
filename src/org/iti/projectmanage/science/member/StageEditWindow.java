package org.iti.projectmanage.science.member;


import org.iti.gh.entity.GhJsxm;
import org.iti.gh.entity.GhXm;
import java.util.List;
import org.iti.projectmanage.science.member.service.ProjectMemberService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;
import com.uniwin.framework.entity.WkTUser;


public class StageEditWindow extends Window implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	 Window stageedit;
     Div stageinfo;

     
     GhXm xmqk;
     Button order;
     ProjectMemberService projectmemberService;
     Listbox stage;
     WkTUser user;
     Toolbarbutton addmember;
     Paging cyPaging;
	public void afterCompose() {

		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		 user = (WkTUser) Sessions.getCurrent().getAttribute("user");
	}
	
	public void initWindow(final GhXm xm)
	{
		
		this.xmqk=xm;
		 if(!xmqk.getKyProman().trim().equals(user.getKuName().trim()))
			{
				addmember.setVisible(false);	
				order.setVisible(false);
			}
		List mlist=projectmemberService.findByKyXmId(xm.getKyId());
		List xmlist =projectmemberService.findByKyId(xm.getKyId(),cyPaging.getActivePage(), cyPaging.getPageSize());
		cyPaging.setTotalSize(mlist.size());	
		stage.setModel(new ListModelList(xmlist));
		cyPaging.addEventListener("onPaging", new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				List xm1list =projectmemberService.findByKyId(xm.getKyId(),cyPaging.getActivePage(), cyPaging.getPageSize());
				stage.setModel(new ListModelList(xm1list));				
			}
		});
		if(xmlist.size()!=0)
		{
			
			stage.setItemRenderer(new ListitemRenderer() {
				public void render(Listitem arg0, Object arg1) throws Exception {
					final GhJsxm member = (GhJsxm) arg1;
						Listcell c0 = new Listcell();
						Listcell c1 = new Listcell(arg0.getIndex()+1+"");
						Listcell c2 = new Listcell(member.getKyMemberName());
						c2.setStyle("color:blue");
						Listcell c3 = new Listcell();
						if(member.getKyTaskDesc().trim().length()>25)
						{
							c3.setLabel(member.getKyTaskDesc().substring(0, 25)+"...");
						}
						else
						{
							c3.setLabel(member.getKyTaskDesc());
						}
						c3.setTooltiptext(member.getKyTaskDesc());
						Listcell c4=new Listcell();
						Hbox hbox = new Hbox();
						 Toolbarbutton btWx = new Toolbarbutton();//查看文献
						 btWx.setImage("/css/sat/sear_ico.jpg");
						 btWx.addEventListener(Events.ON_CLICK, new EventListener() {

								public void onEvent(Event arg0) throws Exception {
									viewWx(member);
								}
								
							});
						 btWx.setTooltiptext("查看参考文献");
						 btWx.setParent(hbox);
						 c4.appendChild(hbox);	
						 Listcell c5=new Listcell();
							Hbox hbox1 = new Hbox();
							 Toolbarbutton btJdbg = new Toolbarbutton();//查看阶段 报告
							 btJdbg.setImage("/css/sat/edit.gif");
							 btJdbg.addEventListener(Events.ON_CLICK, new EventListener() {

									public void onEvent(Event arg0) throws Exception {
										stagereport(member);
									}
									
								});
							 btJdbg.setTooltiptext("查看阶段报告");
							 btJdbg.setParent(hbox1);
							 c5.appendChild(hbox1);	
							
							 Listcell c6=new Listcell(); 
							 Vbox vb = new Vbox();
							 Hbox hbox2 = new Hbox();
							 Toolbarbutton task= new Toolbarbutton();//编辑任务
							 task.setImage("/css/sat/issue_ico.gif");
							 task.addEventListener(Events.ON_CLICK, new EventListener() {
									public void onEvent(Event arg0) throws Exception {
										DistributeTaskWindow w=(DistributeTaskWindow)Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/member/distributeTask.zul", null, null);
										w.initWindow(member);
										
										w.addEventListener(Events.ON_CHANGE, new EventListener() {
											public void onEvent(Event arg0) throws Exception {
												initWindow(xmqk);
											}
										});
										w.doModal();
									}
									
								});
							 task.setTooltiptext("查看/编辑任务");
							 Toolbarbutton delete= new Toolbarbutton();//删除成员	
							 delete.setTooltiptext("删除成员");
							 delete.addEventListener(Events.ON_CLICK, new EventListener() {
									public void onEvent(Event arg0) throws Exception {
										if(Messagebox.show("确定要删除该成员？", "确认", 
												Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.OK)
										{  
											projectmemberService.delete(member);
											initWindow(xmqk);
										}
									}
									
								});
							 delete.setImage("/css/sat/del.gif");
							 if(!xmqk.getKyProman().trim().equals(user.getKuName().trim()))
								{
									delete.setVisible(false);			
								}
							 hbox2.appendChild(task);
							 hbox2.appendChild(delete);
							 vb.appendChild(hbox2);
							 c6.appendChild(vb);	
						arg0.setValue(member);
						arg0.appendChild(c0);
						arg0.appendChild(c1);
						arg0.appendChild(c2);
						arg0.appendChild(c3);
						arg0.appendChild(c4);
						arg0.appendChild(c5);
						arg0.appendChild(c6);
						
						
		}
	});
		}
		
	}
	//返回
	public void onClick$back()
	{
		Events.postEvent(Events.ON_CHANGE, this, null);
		stageedit.detach();
	}
	//查看文献
	public void viewWx(GhJsxm member)
	{
		stageinfo.setVisible(false);
		ArticalListWindow w=(ArticalListWindow)Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/member/articalist.zul", null, null);
		w.setXm(xmqk);
		w.setMember(member);
		w.initWindow();
		w.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				initShow();
			}
		});
		stageedit.appendChild(w);
	}
	public void initShow()
	{
		stageinfo.setVisible(true);
	}
	//添加成员
	public void onClick$addmember() throws SuspendNotAllowedException, Exception
	{
		StaffAddWindow add=(StaffAddWindow)Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/member/addStaff.zul", null, null);
		add.initWindow(xmqk);
	    add.addEventListener(Events.ON_CHANGE, new EventListener(){
		public void onEvent(Event arg0) throws Exception {			
			initWindow(xmqk);
		}	   
	   });
		add.doModal();
	}
	//阶段报告
	public void stagereport(GhJsxm member)
	{
		stageinfo.setVisible(false);
		
		StageReportWindow w = (StageReportWindow)Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/member/stageReport.zul", null, null);
		w.setXm(xmqk);
		w.setMember(member);
		w.initWindow();
		//w.initShow();
		
		w.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				initShow();
			}
		});
		stageedit.appendChild(w);
	}
	//成员排序
	 public void onClick$order() throws SuspendNotAllowedException, InterruptedException
	 {
		 MemberSortWindow w=(MemberSortWindow)Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/member/sort.zul", null, null);
		w.initWindow(xmqk);
	
		 w.addEventListener(Events.ON_CHANGE, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					initWindow(xmqk);
				}
			});
			w.doModal();
	 }
}