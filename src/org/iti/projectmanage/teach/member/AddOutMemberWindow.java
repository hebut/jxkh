package org.iti.projectmanage.teach.member;


import java.util.List;

import org.iti.gh.entity.GhJsxm;
import org.iti.gh.entity.GhOutMember;
import org.iti.gh.entity.GhXm;
import org.iti.projectmanage.science.member.service.ProjectMemberService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import com.uniwin.framework.entity.WkTUser;

public class AddOutMemberWindow extends Window implements AfterCompose{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	WkTUser user; 
	Textbox name,dept,phone,address;
	ProjectMemberService projectmemberService;
	GhXm xm;
	List mem;
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user=(WkTUser)Sessions.getCurrent().getAttribute("user");	
	}
	public void initWindow(GhXm kyxm)
	{
		this.xm=kyxm;
		mem=projectmemberService.findByKyXmId(xm.getKyId());
	}
	public void onClick$submit()
	{
		if (name.getValue() == null || "".equals(name.getValue().trim())) {
			throw new WrongValueException(name, "您还没有填写成员名称！");
		}
		if (dept.getValue() == null || "".equals(dept.getValue().trim())) {
			throw new WrongValueException(dept, "您还没有填写成员部门！");
		}
		GhOutMember out=new GhOutMember();
		out.setAddress(address.getValue());
		out.setDept(dept.getValue());
		out.setOutMemberName(name.getValue());
		out.setPhone(phone.getValue());
		out.setKuId(user.getKuId());
		projectmemberService.save(out);	
		  GhJsxm member = new GhJsxm();
			member.setKuId(Long.parseLong("-"+out.getmId().toString()));
			member.setKyMemberName(name.getValue());
			member.setJsxmType(GhJsxm.JYXM);
			member.setKyCdrw("2");
			member.setKyTaskDesc("");
			member.setKyId(xm.getKyId());
			member.setKyGx(mem.size()+1);
			projectmemberService.save(member);	
			Events.postEvent(Events.ON_CHANGE, this, null);
			this.detach();	
	
	}
  //关闭
  public void onClick$back() 
    {
		this.detach();
	}
}
