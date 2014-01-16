package org.iti.xypt.personal.group;

import java.util.List;

import org.iti.bysj.ui.base.InnerButton;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Label;
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

public class InviteWindow extends Window implements AfterCompose {
	Listbox inviteList;
	MemberService memberService;
	Long KuId;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
	}

	public void setKuId(Long kuId) {
		KuId = kuId;
	}

	public void initWindow() {
		final String[] groupArray = { "普通群组", "兴趣联盟", "组织机构", "好友交流" };
		List invitelist = memberService.findGroup(KuId, QzMember.ACCEPT_NO, QzMember.AGREE_NULL);
		inviteList.setModel(new ListModelList(invitelist));
		inviteList.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				final QzMember member = (QzMember) arg1;
				Listcell c0 = new Listcell(arg0.getIndex() + 1 + "");
				QzGroup group = (QzGroup) memberService.get(QzGroup.class, member.getQzId());
				Listcell c1 = new Listcell(group.getQzName());
				Listcell c2 = new Listcell(groupArray[group.getQzType()]);
				WkTUser user = (WkTUser) memberService.get(WkTUser.class, group.getQzUser());
				Listcell c3 = new Listcell(user.getKuName());
				Listcell c4 = new Listcell(user.getDept().getKdName());
				Listcell c5 = new Listcell();
				InnerButton accept = new InnerButton("接受");
				accept.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						member.setMbAccept(QzMember.ACCEPT_YES);
						member.setMbAgree(QzMember.AGREE_YES);
						member.setMbRole(QzMember.Normal);
						memberService.update(member);
						initWindow();
						refresh();
					}
				});
				c5.appendChild(accept);
				c5.appendChild(new Label(" / "));
				InnerButton refuse = new InnerButton("拒绝");
				refuse.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						memberService.delete(member);
						initWindow();
					}
				});
				c5.appendChild(refuse);
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
				arg0.appendChild(c5);
			}
		});
	}

	public void refresh() {
		Events.postEvent(Events.ON_CHANGE, this, null);
	}
}
