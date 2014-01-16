package org.iti.projectmanage.science.member;

import java.util.List;

import org.iti.gh.entity.GhJsxm;
import org.iti.gh.entity.GhOutMember;
import org.iti.gh.entity.GhXm;
import org.iti.gh.entity.GhYjfx;
import org.iti.projectmanage.science.member.service.ProjectMemberService;
import org.iti.xypt.entity.Teacher;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;
import com.uniwin.framework.common.listbox.DeptListbox;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

public class StaffAddWindow extends Window implements AfterCompose{

	Listitem cout;
	Listbox range,listbox1,listbox2;
	DeptListbox dept;
	Toolbarbutton  view,outadd;
	Button add,delete;
	UserService userService;
	ProjectMemberService projectmemberService;
	Checkbox teacher,master,student;
	Textbox name;
	WkTUser user; 
	 GhXm xm;
	public void afterCompose() {
		
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user=(WkTUser)Sessions.getCurrent().getAttribute("user");
		//选择人员范围触发事件
		range.addEventListener(Events.ON_SELECT, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					if(cout.isSelected())
					{
					   dept.setDisabled(true);
					   teacher.setDisabled(true);
					   master.setDisabled(true);
					   student.setDisabled(true);
					   name.setDisabled(true);
					   view.setVisible(false);
					   outadd.setVisible(true);
					   initOutMember();
					  
					}
					else
					{
						 dept.setDisabled(false);
						   teacher.setDisabled(false);
						   master.setDisabled(false);
						   student.setDisabled(false);
						   name.setDisabled(false);
						   view.setVisible(true);		
						   outadd.setVisible(false);					}
				}				
				});
		
	}
	
  public void initWindow(GhXm xm)
  {
	  WkTUser user = (WkTUser) Sessions.getCurrent().getAttribute("user");
	  this.xm=xm;
	  dept.setRootDept(null);
	  dept.setRootID(0L);
	  dept.initNewDeptSelect(user.getDept()); 
	  initMember();
  }
  
   //添加外部成员
   public void onClick$outadd() throws SuspendNotAllowedException, InterruptedException
   {
	   AddOutMemberWindow w=(AddOutMemberWindow)
	   Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/member/addoutmember.zul", null, null);
	   w.initWindow(xm);
	   w.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				initMember();
				initFirst();
			}
		});
	   w.doModal();
   }
   
   public void initFirst()
   {
	   Events.postEvent(Events.ON_CHANGE, this,null); 
   }
   //初始化外部成员列表
  public void initOutMember()
  {
	  List outmlist=projectmemberService.findOutMemberByuid(user.getKuId());
	  if(outmlist.size()!=0)
	  {
		  listbox1.setModel(new ListModelList(outmlist));  
		  listbox1.setItemRenderer(new ListitemRenderer() {
				public void render(Listitem arg0, Object arg1) throws Exception {
					GhOutMember member = (GhOutMember) arg1;
					  List memberlist=projectmemberService.findByKyXmId(xm.getKyId());
					   int count=0;
					  for(int i=0;i<memberlist.size();i++)
					  {
						  GhJsxm jsxm=(GhJsxm) memberlist.get(i);
						 String mid="-"+member.getmId().toString();
						  if(mid.trim().equals(jsxm.getKuId().toString().trim()))  count++;
					  }
					  if(count==0)
					  {
						Listcell c0 = new Listcell();
						Listcell c1 = new Listcell("xw"+member.getmId().toString());
						Listcell c2 = new Listcell(member.getOutMemberName());
						Listcell c3 = new Listcell(member.getDept());
						arg0.setValue(member);
						arg0.appendChild(c0);
						arg0.appendChild(c1);
						arg0.appendChild(c2);
						arg0.appendChild(c3);
					  }
					  else arg0.setVisible(false);
					}
		  });
	  }
  }
  //初始化项目组成员列表
  public void initMember()
  {
	 
	  List member=projectmemberService.findByKyXmId(xm.getKyId());
	  if(member.size()!=0)
	  {
	  listbox2.setModel(new ListModelList(member));
	  
		  listbox2.setItemRenderer(new ListitemRenderer() {
				public void render(Listitem arg0, Object arg1) throws Exception {
					GhJsxm member = (GhJsxm) arg1;
					if(member.getKuId().toString().startsWith("-"))
					{
						String smid=member.getKuId().toString();
						Long mid=Long.parseLong(smid.substring(1, smid.length()));
						List memlist=projectmemberService.findByMid(mid);
						if(memlist.size()!=0)
						{
							GhOutMember mem=(GhOutMember) memlist.get(0);
							Listcell c0 = new Listcell();
							Listcell c1 = new Listcell("xw"+member.getKuId().toString().substring(1, member.getKuId().toString().length()));
							Listcell c2 = new Listcell(member.getKyMemberName());
							Listcell c3 = new Listcell(mem.getDept());
							arg0.setValue(member);
							arg0.appendChild(c0);
							arg0.appendChild(c1);
							arg0.appendChild(c2);
							arg0.appendChild(c3);
						}
					}
					else
					{
						WkTUser user=(WkTUser)userService.getUserByuid(member.getKuId());
						Listcell c0 = new Listcell();
						Listcell c1 = new Listcell(user.getKuLid());
						Listcell c2 = new Listcell(user.getKuName());
						Listcell c3 = new Listcell(user.getDept().getKdName());
						arg0.setValue(user);
						arg0.appendChild(c0);
						arg0.appendChild(c1);
						arg0.appendChild(c2);
						arg0.appendChild(c3);
					}
					
				}
			});
  }
  }
  
  //查询按钮
  public void onClick$view()
  { 
	  WkTDept deptment = (WkTDept) dept.getSelectedItem().getValue();
	   List<WkTUser> userlist =userService.findUserForGroupByKdIdAndName(deptment.getKdId(), name.getValue(), teacher.isChecked(), student.isChecked(), master.isChecked());	
		listbox1.setModel(new ListModelList(userlist));
		listbox1.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				WkTUser user = (WkTUser) arg1;
				List mlist=projectmemberService.findByKyXmId(xm.getKyId());
				int count=0;
				if(mlist.size()!=0)
				{	
					for(int i=0;i<mlist.size();i++)
					{
						GhJsxm member = (GhJsxm)mlist.get(i);
						if(member.getKuId().equals(user.getKuId()))
							count++;
					}
				}
				if(count==0)
				{
				Listcell c0 = new Listcell();
				Listcell c1 = new Listcell(user.getKuLid());
				Listcell c2 = new Listcell(user.getKuName());
				Listcell c3 = new Listcell(user.getDept().getKdName());
				arg0.setValue(user);
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				}
				else
				{
					arg0.setVisible(false);
				}
			}
		});
 }
  //添加成员
  public void onClick$add()
  {

		while (listbox1.getSelectedItem() != null) {
			Listitem item = listbox1.getSelectedItem();
			if(item.getValue() instanceof WkTUser)
			{
			WkTUser user = (WkTUser) item.getValue();
			if(projectmemberService.findByKyIdAndKuId(xm.getKyId(), user.getKuId()).size()!=0)
			{
			return;
			}
			else
			{
			  GhJsxm member = new GhJsxm();
			  List mem=projectmemberService.findByKyXmId(xm.getKyId());
				member.setKuId(user.getKuId());
				member.setKyMemberName(user.getKuName());
				member.setJsxmType(GhJsxm.KYXM);
				member.setKyCdrw("2");
				member.setKyTaskDesc("");
				member.setKyId(xm.getKyId());
				member.setKyGx(mem.size()+1);
				projectmemberService.save(member);
				
				Events.postEvent(Events.ON_CHANGE, this,null);
				
				
				if (listbox1.getItemCount() != 0)
					listbox2.insertBefore(item, listbox1.getItemAtIndex(0));
				else
					item.setParent(listbox2);
			item.setSelected(false);
		}
			}
			else
			{
				GhOutMember mem = (GhOutMember) item.getValue();
				  GhJsxm member = new GhJsxm();
				  List memlist=projectmemberService.findByKyXmId(xm.getKyId());
					member.setKuId(Long.parseLong("-"+mem.getmId().toString()));
					member.setKyMemberName(mem.getOutMemberName());
					member.setJsxmType(GhJsxm.KYXM);
					member.setKyCdrw("2");
					member.setKyTaskDesc("");
					member.setKyId(xm.getKyId());
					member.setKyGx(memlist.size()+1);
					projectmemberService.save(member);
					
					Events.postEvent(Events.ON_CHANGE, this,null);
					
					if (listbox1.getItemCount() != 0)
						listbox2.insertBefore(item, listbox1.getItemAtIndex(0));
					else
						item.setParent(listbox2);
				item.setSelected(false);
			}
		
		}
		
  }
  
  
  
  
//删除成员
  public void onClick$delete() throws InterruptedException
  {

		while (listbox2.getSelectedItem() != null) {
			Listitem item = listbox2.getSelectedItem();
			if(item.getValue() instanceof WkTUser)
			{
			WkTUser user = (WkTUser) item.getValue();
			if(user.getKuName().equals(xm.getKyProman()))
			{
				 if(Messagebox.show("该成员为项目负责人，是否删除？", "确认", 
							Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.OK)
					{  
					 List memberlist = projectmemberService.findByKyIdAndKuId(xm.getKyId(), user.getKuId());
						if (memberlist.size() != 0) {
							GhJsxm member = (GhJsxm) memberlist.get(0);
							projectmemberService.delete(member);
							if (listbox2.getItemCount() != 0)
								listbox1.insertBefore(item, listbox2.getItemAtIndex(0));
							else
								item.setParent(listbox1);
							item.setSelected(false);
						}
					}
				 else return;
			}
			else
			{
				List memberlist = projectmemberService.findByKyIdAndKuId(xm.getKyId(), user.getKuId());
				if (memberlist.size() != 0) {
					GhJsxm member = (GhJsxm) memberlist.get(0);
				projectmemberService.delete(member);
				if (listbox2.getItemCount() != 0)
					listbox1.insertBefore(item, listbox2.getItemAtIndex(0));
				else
					item.setParent(listbox1);
				item.setSelected(false);
			}
			}
			}
			else
			{
				GhJsxm mem=(GhJsxm) item.getValue();
				projectmemberService.delete(mem);
				if (listbox2.getItemCount() != 0)
					listbox1.insertBefore(item, listbox2.getItemAtIndex(0));
				else
					item.setParent(listbox1);
				item.setSelected(false);
			}	
		
		}
		Events.postEvent(Events.ON_CHANGE, this, null);	
  }
  //关闭
  public void onClick$close() 
    {
		this.detach();
	}
}
