package com.uniwin.asm.personal.service;

import java.util.List;

import com.uniwin.asm.personal.entity.XYFeedBackReply;
import com.uniwin.basehs.service.BaseService;

public interface XYFeedBackService extends BaseService {
	/**
	 * �����û���Ų����䷢���ķ�����¼
	 * 
	 * @param kuid
	 * @return ������¼
	 * @author DATIAN
	 */
	List findByKuid(Long kuid);

	/**
	 * ������з�����¼�����ջظ�����ʱ���������
	 * 
	 * @return ������¼
	 * @author DATIAN
	 */
	List getAll();

	/**
	 * ���ݷ�����Ż�����з����𸴼�¼
	 * 
	 * @return ������¼
	 * @author DATIAN
	 */
	List findByFbid(Long fbId);

	/**
	 * ���ݷ�����źʹ��˵ı�Ż�÷����𸴼�¼
	 * 
	 * @return ������¼
	 * @author DATIAN
	 */
	XYFeedBackReply findByFbidAndKuid(Long fbId, Long kuId);
}
