package org.iti.xypt.service;

import java.util.List;

import com.uniwin.basehs.service.BaseService;

public interface FudaoService extends BaseService {
	/**
	 *<li>���������������û���Ų�ѯ����İ༶
	 */
	List findClassByKuIdAndSchid(Long KuId,Long Schid);

	/**
	 *<li>����������������֯���ű�Ų�ѯ�����İ༶
	 */
	List findClassByKdId(Long KdId);
	/**
	 *<li>���������������û���Ų�ѯ����İ༶
	 */
	List findClassByKuIdAndKdid(Long KuId,Long Kdid);
	/**
	 * �����û���š�ѧУid���꼶��ѯ����İ༶
	 * @param KuId
	 * @param Schid
	 * @param grade
	 * @return
	 */
	public List findByKuidAndSchidAndGrade(Long KuId,Long Schid,Integer grade);
}
