package org.iti.xypt.personal.group;

import java.util.List;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Window;

import com.uniwin.asm.personal.entity.QzGroup;
import com.uniwin.asm.personal.entity.QzMember;
import com.uniwin.asm.personal.service.MemberService;
import com.uniwin.framework.entity.WkTUser;

public class MemberWindow extends Window implements AfterCompose {
	Listbox memberList;
	MemberService memberService;
	QzGroup group;

	public void setGroup(QzGroup group) {
		this.group = group;
	}

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
	}

	public void initWindow() {
		final String[] SexArray = { "", "男", "女" };
		final String[] RoleArray = { "普通成员", "管理员" };
		List memberlist = memberService.findJoinGroup(group.getQzId());
		memberList.setModel(new ListModelList(memberlist));
		memberList.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				QzMember member = (QzMember) arg1;
				Listcell c0 = new Listcell(arg0.getIndex() + 1 + "");
				WkTUser user = (WkTUser) memberService.get(WkTUser.class, member.getMbMember());
				Listcell c1 = new Listcell(user.getKuName());
				Listcell c2 = new Listcell(SexArray[Integer.parseInt(user.getKuSex().trim())]);
				Listcell c3 = new Listcell(user.getDept().getKdName());
				Listcell c4 = new Listcell(RoleArray[member.getMbRole()].toString());
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
			}
		});
	}
}
