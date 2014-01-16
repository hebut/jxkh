package org.iti.gh.service;

import java.util.List;

import org.iti.gh.entity.GhXshy;

import com.uniwin.basehs.service.BaseService;

public interface XshyService extends BaseService {
	/**
	 * ���ݵ�λ���ű�Ų��Ҹõ�λ���Ž�ʦѧ������
	 * @param kdid
	 * @return
	 */
	public List findByKdId(long kdid,Short ifcj);
	/**
	 * ���ݵ�λ���ű�Ų��Ҹõ�λ���Ž�ʦѧ�����飬ͬ����Ŀ�ϲ�
	 * @param kdid
	 * @return
	 */
	public List findSumKdId(long kdid,Short ifcj,Short state);
	/**
	 * �����û����Ҹý�ʦ��ѧ������
	 * @param kuid
	 * @return
	 */
	public List findByKuid(long kuid,Short ifcj);
	/**
	 * �������Ʋ��Ҹý�ʦ��ѧ������
	 * @param kuid
	 * @return
	 */
	public List findByMc(String name,Short ifcj, Short state);
	
	/**
	 * <li>��������:�����û����ű�źͻ�������
	 * @param kdid
	 * @param state
	 * @return
	 */
	public List findByKdidAndState(Long kdid,Short ifcj, Short state);
	
	/**
	 * ����
	 * @param xshy
	 * @param name
	 * @param ifcj
	 * @return
	 */
	public boolean CheckOnlyOne(GhXshy xshy,String name,Short ifcj,Long kuid);
	
	/**
	 * 
	 * @param kdid
	 * @param year
	 * @param kuid
	 * @param ifcj
	 * @param lx
	 * @param hyef
	 * @return
	 */
	 public List findByKdidAndYearAndKuidAndIfcjAndLx(String year,Long kuid,Short ifcj,Short lx,String hyef);
}
