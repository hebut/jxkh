package org.iti.gh.service;

import java.util.List;

import org.iti.gh.entity.GhPx;

import com.uniwin.basehs.service.BaseService;

public interface PxService extends BaseService {
	/**
	 * �����û��ı����������ѵ
	 * @param kuId
	 * @return
	 */
	List findByKuid(Long kuId);
	/**
	 * ���ݵ�λ���ű�Ų��Ҹõ�λ���Ž�ʦ��ѵ
	 * @param kdid
	 * @return
	 */
	public List findByKdId(long kdid);
	/**
	 * ���ݵ�λ���ű�Ų��Ҹõ�λ���Ž�ʦ��ѵ��ͬ����Ŀ�ϲ�
	 * @param kdid
	 * @return
	 */
	public List findSumKdId(long kdid,Short state);
	/**
	 * ����������������ѵ
	 * @param kuId
	 * @return
	 */
	List findByMc(String name);
	/**
	 * �����û�����id�����״̬��ѯָ��ѧ�����
	 * @param kdid
	 * @param state
	 * @return
	 */
	public List findByKdidAndState(Long kdid,Short state);
	/**
	 * ����������������ѵ 
	 * @param kuid
	 * @param mc
	 * @return
	 */
	public GhPx findByKuidAndMc(Long kuid,String mc,String time);
}
