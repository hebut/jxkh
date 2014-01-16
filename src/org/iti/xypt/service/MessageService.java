package org.iti.xypt.service;

import java.util.List;

import org.iti.xypt.entity.XyMessage;
import org.iti.xypt.entity.XyUserrole;

import com.uniwin.basehs.service.BaseService;

public interface MessageService extends BaseService {
	/**
	 * ��ѯ�û���δɾ�����Լ����͵���Ϣ
	 * 
	 * @param kuid
	 * @return ��Ϣ�б�
	 */
	public List findMessageByKuid(Long kuid);

	/**
	 * ��ѯ�û���δɾ�����Լ����͵���Ϣ
	 * 
	 * @param kuid
	 * @return ��Ϣ�б�
	 */
	public List findMessageByKuid(Long kuid, Long btime, Long etime);

	/**
	 * ��ѯ�û�������֪ͨ
	 * 
	 * @param kuid
	 * @return
	 */
	public List findNoticeByKuid(Long kuid);

	/**
	 * ��ѯ�û�������֪ͨ
	 * 
	 * @param kuid
	 * @return
	 */
	public List findNoticeByKuid(Long kuid, Long btime, Long etime);

	/**
	 * ��ѯ�û��յ���Ϣ
	 * 
	 * @param kuid
	 * @return
	 */
	public List findUserMessage(Long kuid);

	/**
	 * ��ѯ�û��յ���Ϣ
	 * 
	 * @param kuid
	 * @return
	 */
	public List findUserMessage(Long kuid, Long btime, Long etime);

	/**
	 * �鿴�û��յ���֪ͨ
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
	 * ����xyu����֪ͨ��ϵ
	 * 
	 * @param xyu
	 */
	public void saveXyNUrByXyUserrole(XyUserrole xyu);

	public void deleteXyNUrByXyUserrole(XyUserrole xyu);

	public List findXyNReceByKridAndXmid(Long krid, Long xmid);

	/**
	 * ��ѯĳ���û��ĳ��ùؼ����б�
	 * 
	 * @param kuid
	 * @return
	 */
	public List getKeywords(Long kuid);

	/**
	 * ��ѯ�����ϵ��
	 */
	public List findLastMessage(Long kuid);
}
