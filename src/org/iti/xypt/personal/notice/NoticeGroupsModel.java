package org.iti.xypt.personal.notice;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.iti.xypt.service.MessageService;
import org.zkoss.zul.GroupsModel;
import org.zkoss.zul.GroupsModelExt;
import org.zkoss.zul.event.GroupsDataListener;

import com.uniwin.framework.entity.WkTUser;

public class NoticeGroupsModel implements GroupsModel, GroupsModelExt {
	public static final int TYPE_N_RECEIVER = 1, TYPE_N_SEND = 2, TYPE_M_RECEIVER = 3, TYPE_M_SEND = 4;
	WkTUser user;
	MessageService messageService;
	List<LongTime> timeList;
	int type;

	public NoticeGroupsModel(WkTUser user, MessageService messageService, int type) {
		this.user = user;
		this.messageService = messageService;
		timeList = new ArrayList<LongTime>();
		this.type = type;
	}

	public void addGroupsDataListener(GroupsDataListener arg0) {
		// TODO Auto-generated method stub
	}

	public Object getChild(int arg0, int arg1) {
		return timeList.get(arg0).getMlist().get(arg1);
	}

	public int getChildCount(int arg0) {
		return timeList.get(arg0).getMlist().size();
	}

	public Object getGroup(int arg0) {
		return timeList.get(arg0);
	}

	public int getGroupCount() {
		List longList = LongTimeFactory.getLongTimes();
		for (int i = 0; i < longList.size(); i++) {
			LongTime lt = (LongTime) longList.get(i);
			if (type == TYPE_N_RECEIVER) {// 查收通知
				if (user.getKdId().longValue() == 0) {
					lt.setMlist(messageService.findAllNotice(lt.getBeginTime(), lt.getEndTime()));
				} else {
					lt.setMlist(messageService.findUserNotice(user.getKuId(), lt.getBeginTime(), lt.getEndTime()));
				}
			} else if (type == TYPE_N_SEND) {// 查看已发通知
				lt.setMlist(messageService.findNoticeByKuid(user.getKuId(), lt.getBeginTime(), lt.getEndTime()));
			} else if (type == TYPE_M_RECEIVER) {// 查收信息
				lt.setMlist(messageService.findUserMessage(user.getKuId(), lt.getBeginTime(), lt.getEndTime()));
			} else if (type == TYPE_M_SEND) {// 查询用户发送消息
				lt.setMlist(messageService.findMessageByKuid(user.getKuId(), lt.getBeginTime(), lt.getEndTime()));
			}
			if (lt.getMlist().size() > 0) {
				timeList.add(lt);
			}
		}
		return timeList.size();
	}

	public Object getGroupfoot(int arg0) {
		return null;
	}

	public boolean hasGroupfoot(int arg0) {
		return false;
	}

	public void removeGroupsDataListener(GroupsDataListener arg0) {
		// TODO Auto-generated method stub
	}

	public void group(Comparator arg0, boolean arg1, int arg2) {
		// TODO Auto-generated method stub
	}

	public void sort(Comparator arg0, boolean arg1, int arg2) {
		// TODO Auto-generated method stub
	}
}
