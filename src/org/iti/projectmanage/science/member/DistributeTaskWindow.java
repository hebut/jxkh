package org.iti.projectmanage.science.member;

import org.iti.gh.entity.GhJsxm;
import org.iti.gh.entity.GhXm;
import org.iti.projectmanage.science.member.service.ProjectMemberService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Label;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTUser;

public class DistributeTaskWindow extends Window implements AfterCompose {

	GhJsxm member;
    Label memberName;
    Textbox task;
    ProjectMemberService projectmemberService;
    Toolbarbutton save;
	public void afterCompose() {

		
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
	}
	
	
	public void initWindow(GhJsxm member)
	{
		WkTUser user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		this.member=member;
		GhXm xm=projectmemberService.findXM(member.getKyId());
		if(!user.getKuName().trim().equals(xm.getKyProman().trim()))
		{
		task.setReadonly(true);
		save.setVisible(false);
		}
		memberName.setValue(member.getKyMemberName());
		task.setValue(member.getKyTaskDesc());
	}
	public void onClick$save()
	{
		member.setKyTaskDesc(task.getValue());
		projectmemberService.update(member);
		Events.postEvent(Events.ON_CHANGE, this, null);
		this.detach();
	}
	
	public void onClick$close()
	{
		this.detach();
	}


}
