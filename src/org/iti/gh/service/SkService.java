package org.iti.gh.service;

import java.util.List;

import org.iti.gh.entity.GhSk;

import com.uniwin.basehs.service.BaseService;

public interface SkService extends BaseService {
	/**
	 * ���ݵ�λ���ű�Ų��Ҹõ�λ���Ž�ʦ�ڿ����
	 * @param kdid
	 * @return
	 */
	public List findByKdId(long kdid);
	public List findByKdIdAudit(long kdid);
	/**
	 * ���ݵ�λ���ű�Ų��Ҹõ�λ���Ž�ʦ�ڿ�������ϲ�ͬ����Ŀ
	 * @param kdid
	 * @return
	 */
	public Long findSumKdId(long kdid);
	/**
	 * �����û����Ҹý�ʦ���ڿ����
	 * @param kuid
	 * @return
	 */
	public List findByKuid(long kuid);
	/**
	 * �������Ʋ��Ҹý�ʦ���ڿ����
	 * @param kuid
	 * @return
	 */
	public List findByMc(String name);
	
	public List<GhSk> findBySkIds(String ids);
	
}
