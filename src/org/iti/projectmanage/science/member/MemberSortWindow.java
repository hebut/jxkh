package org.iti.projectmanage.science.member;

/**
 * <li>成员排序
 */

import java.util.EventListener;
import java.util.List;

import org.iti.gh.entity.GH_PROMEMBER;
import org.iti.gh.entity.GhJsxm;
import org.iti.gh.entity.GhXm;
import org.iti.projectmanage.science.member.service.ProjectMemberService;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTUser;



public class MemberSortWindow extends Window implements AfterCompose {
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	WkTUser user; 
	GhXm xmqk;
	ProjectMemberService projectmemberService;
	Listbox sortList;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);	
		user=(WkTUser)Sessions.getCurrent().getAttribute("user");
	}
  public void initWindow(GhXm xm)
  {
	  this.xmqk=xm;
	  List mlist=projectmemberService.findByKyXmId(xmqk.getKyId());
	  sortList.setModel(new ListModelList(mlist));
	  sortList.setItemRenderer(new ListitemRenderer()
	  {
		public void render(Listitem arg0, Object arg1) throws Exception {
			GhJsxm member = (GhJsxm) arg1;
			Listcell c0=new Listcell();
			c0.setLabel(member.getKyMemberName());
			arg0.setValue(member);
			arg0.appendChild(c0);
			arg0.setHeight("25px");
			arg0.setDraggable("true");
			arg0.setDroppable("true");
		}
		  
	  });
  }	
  //保存排序结果
  public void onClick$submit()
  {
	  List itemList=sortList.getItems();
		StringBuffer sb=new StringBuffer("编辑成员顺序,ids:");
		if(itemList.size()>0){
		  for(int i=0;i<itemList.size();i++){
			Listitem item=(Listitem)itemList.get(i);
			GhJsxm m=(GhJsxm)item.getValue();
			int j=i+1;
			m.setKyGx(j);
			projectmemberService.update(m);
		  }
		}
		Events.postEvent(Events.ON_CHANGE, this, null);
		this.detach();
  }
  
  public void onClick$close()
  {
	  this.detach();
  }
}
