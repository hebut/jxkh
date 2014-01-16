package org.iti.gh.service;

import java.util.List;

import org.iti.gh.entity.GhZz;

import com.uniwin.basehs.service.BaseService;

public interface ZzService extends BaseService {

	/**
	 * �����û��ı�š��û����ƣ��������ࣨ1�̲�,2ר����
	 * @param kuId
	 * @param kuname
	 * @param lx
	 * @param jslwtyype
	 * @return
	 */
	public 	List findAllname(Long kuId,String kuname,Short lx ,Short jszztyype);
	
	/**
	 * �����û��ı�š��û����ƣ��������ࣨ1�̲�,2ר������ʦ���������ͣ�1 �̲ģ�2ר����
	 * @param kuId
	 * @param lx
	 * @param jszztyype
	 * @return
	 */
	public List findByKuidAndType(Long kuId,Short lx,Short jszztyype);
	
	/**
	 * ����
	 * @param name
	 * @param lx
	 * @param isbn
	 * @return
	 */
	public boolean CheckOnlyOne(GhZz zz,String name,Short lx,String zb);
	
	/**
	 * 
	 * @param kdid
	 * @param lx
	 * @param state
	 * @return
	 */
	public List findSumKdId(long kdid, Short lx, Short state);
	/**
	 * 
	 * @param name
	 * @param lx
	 * @param zb
	 * @return
	 */
	public GhZz findByJcnameAndZbAndLx(String name, Short lx, String zb);
	
	/**
	 * �����û�id���̲����ƺ�������������ѯû�н�����ϵ�Ľ̲�
	 * @param kuid
	 * @param jcname
	 * @param zb
	 * @param lx
	 * @param type
	 * @return
	 */
	public List findByKuidAndJcmcAndZbAndLxAndType(Long kuid,String jcname,String zb,Short lx,Short type );
	/**
	 *  ��ѯ�����ͨ���Ľ̲Ļ�ר��
	 * @param kuid
	 * @param year 
	 * @param lx
	 * @param kuid
	 * @return
	 */
	public List findtjzj(Long kdid, String year, Short lx,Long kuid) ;
	
	/**
	 * ��������ID��ѯ�б�
	 * @param ids
	 * @return
	 */
	public List<GhZz> findByZzIds(String ids);
}
