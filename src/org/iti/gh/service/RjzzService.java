package org.iti.gh.service;

import java.util.List;

import org.iti.gh.entity.GhRjzz;

import com.uniwin.basehs.service.BaseService;

public interface RjzzService extends BaseService {
	/**
	 * ͨ���û���Ų������е��������
	 * @param kuId
	 * @return �������
	 */
	List findByKuid(Long kuId);
	/**
	 * ͨ���û���ź��û����Ʋ����и��û��йص����Ǹ��û���ӵ��������
	 * @param kuid
	 * @param kuname
	 * @return
	 */
	List findByKuidAndUname(Long kuid,String kuname);
	
	/**
	 * <li>�������������ݵ�λid��������������״̬��ѯ�������
	 * @param kdid
	 * @param state
	 * @return
	 */
	List findByKdidAndState(Long kdid,Short state);
	/**
	 * 
	 * @param rjzz
	 * @param mc
	 * @param softno
	 * @return
	 */
	boolean CheckOnlyOne(GhRjzz rjzz,String mc,String softno);
	/**
	 * 
	 * @param mc
	 * @param softno
	 * @return
	 */
	public List findByRjnameAndDjh(String mc, String softno);
	/**
	 * 
	 * @param mc
	 * @param softno
	 * @return
	 */
	public GhRjzz findBynameAndSoftno(String mc, String softno);
	
	/**
	 * 
	 * @param kdid
	 * @param year
	 * @param kuid
	 * @return
	 */
	public List findBykdidAndYearAndKuid(String year,Long kuid);
	
	/**
	 * �����������ID��ѯ�б�
	 * @param ids
	 * @return
	 */
	public List<GhRjzz> findByRjIds(String ids);
}
