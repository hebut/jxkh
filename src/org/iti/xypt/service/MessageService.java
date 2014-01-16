package org.iti.xypt.service;

import java.util.List;

import org.iti.xypt.entity.XyMessage;
import org.iti.xypt.entity.XyUserrole;

import com.uniwin.basehs.service.BaseService;

public interface MessageService extends BaseService {
	/**
	 * 查询用户的未删除的自己发送的信息
	 * 
	 * @param kuid
	 * @return 信息列表
	 */
	public List findMessageByKuid(Long kuid);

	/**
	 * 查询用户的未删除的自己发送的信息
	 * 
	 * @param kuid
	 * @return 信息列表
	 */
	public List findMessageByKuid(Long kuid, Long btime, Long etime);

	/**
	 * 查询用户发布的通知
	 * 
	 * @param kuid
	 * @return
	 */
	public List findNoticeByKuid(Long kuid);

	/**
	 * 查询用户发布的通知
	 * 
	 * @param kuid
	 * @return
	 */
	public List findNoticeByKuid(Long kuid, Long btime, Long etime);

	/**
	 * 查询用户收到信息
	 * 
	 * @param kuid
	 * @return
	 */
	public List findUserMessage(Long kuid);

	/**
	 * 查询用户收到信息
	 * 
	 * @param kuid
	 * @return
	 */
	public List findUserMessage(Long kuid, Long btime, Long etime);

	/**
	 * 查看用户收到的通知
	 * 
	 * @param kuid
	 * @return
	 */
	public List findUserNotice(Long kuid);

	public List findUserNotice(Long kuid, Long btime, Long etime);

	public void saveUrdByKuid(Long kuid);

	public List findAllNotice();

	public List findAllNotice(Long btime, Long etime);

	public void updateNotice(XyMessage message);

	public void deleteNotice(XyMessage message);

	public void deleteMessage(XyMessage message, Long kuid);

	/**
	 * 根据xyu更新通知关系
	 * 
	 * @param xyu
	 */
	public void saveXyNUrByXyUserrole(XyUserrole xyu);

	public void deleteXyNUrByXyUserrole(XyUserrole xyu);

	public List findXyNReceByKridAndXmid(Long krid, Long xmid);

	/**
	 * 查询某个用户的常用关键字列表
	 * 
	 * @param kuid
	 * @return
	 */
	public List getKeywords(Long kuid);

	/**
	 * 查询最近联系人
	 */
	public List findLastMessage(Long kuid);
}
