package org.iti.xypt.service.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.iti.xypt.entity.XyMReceiver;
import org.iti.xypt.entity.XyMReceiverId;
import org.iti.xypt.entity.XyMessage;
import org.iti.xypt.entity.XyNUrd;
import org.iti.xypt.entity.XyUserrole;
import org.iti.xypt.service.MessageService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class MessageServiceImpl extends BaseServiceImpl implements MessageService {
	public List findMessageByKuid(Long kuid) {
		String query = "from XyMessage as xym where xym.xmType=? and xym.kuId=? and xym.xmState!=? order by xym.xmSndtime desc";
		return find(query, new Object[] { XyMessage.TYPE_MESSAGE, kuid, XyMessage.STATE_DELETE });
	}

	public List findMessageByKuid(Long kuid, Long btime, Long etime) {
		String query = "from XyMessage as xym where xym.xmType=? and xym.kuId=? and xym.xmState!=? and xym.xmSndtime>=? and xym.xmSndtime<=? order by xym.xmSndtime desc";
		return find(query, new Object[] { XyMessage.TYPE_MESSAGE, kuid, XyMessage.STATE_DELETE, btime, etime });
	}

	public List findNoticeByKuid(Long kuid) {
		String query = "from XyMessage as xym where xym.kuId=? and xym.xmType!=? order by xym.xmSndtime desc";
		return find(query, new Object[] { kuid, XyMessage.TYPE_MESSAGE });
	}

	public List findNoticeByKuid(Long kuid, Long btime, Long etime) {
		String query = "from XyMessage as xym where xym.kuId=? and xym.xmType!=?  and xym.xmSndtime>=? and xym.xmSndtime<=? order by xym.xmSndtime desc";
		return find(query, new Object[] { kuid, XyMessage.TYPE_MESSAGE, btime, etime });
	}

	public List findUserMessage(Long kuid) {
		String query = "from XyMessage as xym where xym.xmType=? and xym.xmId in(select xmr.id.xmId " + "from XyMReceiver as xmr where xmr.id.kuId=? and xmr.xmrState!=?)  order by xym.xmSndtime desc";
		return find(query, new Object[] { XyMessage.TYPE_MESSAGE, kuid, XyMReceiver.STATE_DELETE });
	}

	public List findUserMessage(Long kuid, Long btime, Long etime) {
		String query = "from XyMessage as xym where xym.xmType=? and xym.xmId in(select xmr.id.xmId " + "from XyMReceiver as xmr where xmr.id.kuId=? and xmr.xmrState!=?)  and xym.xmSndtime>=? and xym.xmSndtime<=? order by xym.xmSndtime desc";
		return find(query, new Object[] { XyMessage.TYPE_MESSAGE, kuid, XyMReceiver.STATE_DELETE, btime, etime });
	}

	public List findUserNotice(Long kuid) {
		String query = "from XyMessage as xym where xym.xmType=? or ( xym.xmType=? and xym.xmId in (select xnr.id.xmId from " + "XyNReceiver as xnr,XyNUrd as xnurd where xnr.id.krId=xnurd.id.krId and xnr.id.kdId=xnurd.id.kdId and xnr.id.xmType=xnurd.id.xnuType and xnurd.id.kuId=?)) order by xym.xmSndtime desc";
		return find(query, new Object[] { XyMessage.TYPE_ALL, XyMessage.TYPE_NOTICE, kuid });
	}

	public void saveUrdByKuid(Long kuid) {
//		String query = "from XyNUrd as xnurd where xnurd.id.kuId=?";
//		List nurdlist = find(query, new Object[] { kuid });
		//if (nurdlist.size() == 0) {
		String query = "from XyUserrole as xyu where xyu.id.kuId=?";
			List xyulist = find(query, new Object[] { kuid });
			for (int i = 0; i < xyulist.size(); i++) {
				XyUserrole xyu = (XyUserrole) xyulist.get(i);
				while (xyu.getKdId().longValue() != 0L) {
					XyNUrd nurd = new XyNUrd(xyu);
					getHibernateTemplate().saveOrUpdate(nurd);
					xyu = new XyUserrole(xyu.getId(), xyu.getDept().getKdPid());
				}
			}
		//}
	}

	public List findAllNotice() {
		String query = "from XyMessage as xym where xym.xmType!=? order by xym.xmSndtime desc";
		return find(query, new Object[] { XyMessage.TYPE_MESSAGE });
	}

	// 更新消息或者通知
	public void updateNotice(XyMessage message) {
		// 收信设置消息更新时间
		Date d = new Date();
		message.setXmSndtime(d.getTime());
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String deleteMR = "delete XyMReceiver as xmr where xmr.id.xmId=?";
		Query deleteMRQ = session.createQuery(deleteMR);
		deleteMRQ.setLong(0, message.getXmId());
		deleteMRQ.executeUpdate();
		getHibernateTemplate().update(message);
	}

	public void deleteNotice(XyMessage message) {
		if (message == null) {
			return;
		}
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String deleteMR = "delete XyMReceiver as xmr where xmr.id.xmId=?";
		Query deleteMRQ = session.createQuery(deleteMR);
		deleteMRQ.setLong(0, message.getXmId());
		deleteMRQ.executeUpdate();
		String deleteNR = "delete XyNReceiver as xnr where xnr.id.xmId=?";
		Query deleteNRQ = session.createQuery(deleteNR);
		deleteNRQ.setLong(0, message.getXmId());
		deleteNRQ.executeUpdate();
		getHibernateTemplate().delete(message);
	}

	public void deleteMessage(XyMessage message, Long kuid) {
		if (message == null) {
			return;
		}
		;
		if (message.getKuId().longValue() == kuid.longValue()) {
			message.setXmState(XyMessage.STATE_DELETE);
			getHibernateTemplate().update(message);
		}
		XyMReceiverId xmrid = new XyMReceiverId(message.getXmId(), kuid);
		XyMReceiver xmr = (XyMReceiver) get(XyMReceiver.class, xmrid);
		if (xmr != null) {
			xmr.setXmrState(XyMReceiver.STATE_DELETE);
			getHibernateTemplate().update(xmr);
		}
		if (message.getXmState().shortValue() == XyMessage.STATE_DELETE.shortValue()) {
			String query = "from XyMReceiver as xmr where xmr.id.xmId=? and xmr.xmrState!=?";
			List xmrlist = find(query, new Object[] { message.getXmId(), XyMReceiver.STATE_DELETE });
			if (xmrlist.size() == 0) {
				Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
				session.beginTransaction();
				String deleteMR = "delete XyMReceiver as xmr where xmr.id.xmId=?";
				Query deleteMRQ = session.createQuery(deleteMR);
				deleteMRQ.setLong(0, message.getXmId());
				deleteMRQ.executeUpdate();
				getHibernateTemplate().delete(message);
			}
		}
	}

	public List findUserNotice(Long kuid, Long btime, Long etime) {
		String query = "from XyMessage as xym where ( xym.xmType=? or ( xym.xmType=? and xym.xmId in (select xnr.id.xmId from " + "XyNReceiver as xnr,XyNUrd as xnurd where xnr.id.krId=xnurd.id.krId and xnr.id.kdId=xnurd.id.kdId and xnr.id.xmType=xnurd.id.xnuType  and xnurd.id.kuId=?))" + ") and xym.xmSndtime>=? and xym.xmSndtime<=? order by xym.xmSndtime desc";
		return find(query, new Object[] { XyMessage.TYPE_ALL, XyMessage.TYPE_NOTICE, kuid, btime, etime });
	}

	public List findAllNotice(Long btime, Long etime) {
		String query = "from XyMessage as xym where xym.xmType!=?  and xym.xmSndtime>=? and xym.xmSndtime<=? order by xym.xmSndtime desc";
		return find(query, new Object[] { XyMessage.TYPE_MESSAGE, btime, etime });
	}

	public void saveXyNUrByXyUserrole(XyUserrole xyu) {
		while (xyu.getKdId().longValue() != 0L) {
			XyNUrd nurd = new XyNUrd(xyu);
			getHibernateTemplate().saveOrUpdate(nurd);
			xyu = new XyUserrole(xyu.getId(), xyu.getDept().getKdPid());
		}
	}

	public void deleteXyNUrByXyUserrole(XyUserrole xyu) {
		// 删除用户角色通知关系
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		session.beginTransaction();
		String deleteNR = "delete XyNUrd as xnurd where xnurd.id.kuId=? and xnurd.id.krId=?";
		Query deleteNRQ = session.createQuery(deleteNR);
		deleteNRQ.setLong(0, xyu.getId().getKuId());
		deleteNRQ.setLong(1, xyu.getId().getKrId());
		deleteNRQ.executeUpdate();
		session.close();
	}

	public List findXyNReceByKridAndXmid(Long krid, Long xmid) {
		String query = "from XyNReceiver as xnr where xnr.id.krId=? and xnr.id.xmId=?";
		return find(query, new Object[] { krid, xmid });
	}

	public List getKeywords(Long kuid) {
		String query = "select distinct xym.xmKeyword from XyMessage as xym where xym.kuId=?";
		return find(query, new Object[] { kuid });
	}

	public List findLastMessage(Long kuid) {
		String query = "from WkTUser where kuId in (select distinct mr.id.kuId from XyMReceiver as mr where mr.id.xmId in (select xmId from XyMessage where kuId=?))";
		return find(query, new Object[] { kuid });
	}
}
