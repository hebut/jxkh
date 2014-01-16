package org.iti.xypt.personal.group;

import java.util.List;

import org.iti.bysj.ui.base.InnerButton;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.uniwin.asm.personal.entity.QzGroup;
import com.uniwin.asm.personal.entity.QzMember;
import com.uniwin.asm.personal.service.GroupService;
import com.uniwin.asm.personal.service.MemberService;
import com.uniwin.framework.entity.WkTUser;

public class SearchWindow extends Window implements AfterCompose {
	Textbox name;
	Button query;
	Listbox typeList, groupList;
	GroupService groupService;
	MemberService memberService;
	Long KuId;

	public void setKuId(Long kuId) {
		KuId = kuId;
	}

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
	}

	public void onClick$query() {
		final String[] groupArray = { "普通群组", "兴趣联盟", "组织机构", "好友交流" };
		List grouplist = groupService.findGroupByTypeOrName(typeList.getSelectedIndex() - 1, name.getValue());
		groupList.setModel(new ListModelList(grouplist));
		groupList.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				final QzGroup group = (QzGroup) arg1;
				Listcell c0 = new Listcell(arg0.getIndex() + 1 + "");
				Listcell c1 = new Listcell(group.getQzName());
				Listcell c2 = new Listcell(groupArray[group.getQzType()]);
				WkTUser user = (WkTUser) groupService.get(WkTUser.class, group.getQzUser());
				Listcell c3 = new Listcell(user.getKuName());
				Listcell c4 = new Listcell(user.getDept().getKdName());
				Listcell c5 = new Listcell();
				Listcell c6 = new Listcell();
				List memberList = memberService.findByQzIdAndKuId(group.getQzId(), KuId);
				if (memberList.size() == 0) {
					c5.setLabel("未加入");
					InnerButton ibt = new InnerButton("申请加入");
					ibt.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event arg0) throws Exception {
							QzMember member = new QzMember();
							member.setQzId(group.getQzId());
							member.setMbMember(KuId);
							member.setMbAccept(QzMember.ACCEPT_NULL);
							member.setMbAgree(QzMember.AGREE_NO);
							member.setMbRole(QzMember.NULL);
							memberService.save(member);
							onClick$query();
						}
					});
					c6.appendChild(ibt);
				} else {
					final QzMember member = (QzMember) memberList.get(0);
					if (member.getMbAccept() == QzMember.ACCEPT_NO.shortValue()) {
						c5.setLabel("已被邀请");
						c6.setLabel("--");
					} else if (member.getMbAgree() == QzMember.AGREE_NO.shortValue()) {
						c5.setLabel("已申请");
						InnerButton ibt = new InnerButton("取消申请");
						ibt.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								memberService.delete(member);
								onClick$query();
							}
						});
						c6.appendChild(ibt);
					} else {
						c5.setLabel("已加入");
						c6.setLabel("--");
					}
				}
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
