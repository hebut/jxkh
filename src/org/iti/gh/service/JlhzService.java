package org.iti.gh.service;

import java.util.List;

import org.iti.gh.entity.GhJlhz;

import com.uniwin.basehs.service.BaseService;

public interface JlhzService extends BaseService {

	/**
	 * ���ݵ�λ���ű�Ų��Ҹõ�λ���Ž�ʦ�����������
	 * @param kdid
	 * @return
	 */
	public List findByKdId(long kdid,Short ifcj,Short state);
	/**
	 * �����û����Ҹý�ʦ�ĺ�����Ŀ
	 * @param kuid
	 * @return
	 */
	public List findByKuid(long kuid,Short ifcj);
	/**
	 * ���ݵ�λ���ű�Ų��Ҹõ�λ���Ž�ʦ�������������ͬ����Ŀ�ϲ�
	 * @param kdid
	 * @return
	 */
	public Long findSumKdId(long kdid,Short ifcj,Short state);
	/**
	 * �������Ʋ��Ҹý�ʦ�ĺ�����Ŀ
	 * @param kuid
	 * @return
	 */
	public List findByMc(String name,Short ifcj, Short state);
	
	/**
	 * ���ݽ�ʦ�û���λid���Ƿ�μӣ����״̬
	 * @param kdid
	 * @param ifcj
	 * @param state
	 * @return
	 */
	public List findByKdidAndCjAndState(Long kdid,Short ifcj,Short state);
	
	/**
	 * ����
	 * @param jlhz
	 * @param name
	 * @param ifcj
	 * @return
	 */
	public boolean CheckOnlyOne(GhJlhz jlhz,String name,String hzdx,Short ifcj,Long kuid);
/**
 * 
 * @param kdid
 * @param year
 * @param kuid
 * @param ifcj
 * @param lx
 * @return
 */
	public List findBykdidAndYearAndKuidAndifcj(String year,Long kuid,Short ifcj,Short lx);
}
