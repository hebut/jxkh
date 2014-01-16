package org.iti.gh.service;

import java.util.List;

import org.iti.gh.entity.GhHylw;

import com.uniwin.basehs.service.BaseService;

public interface HylwService extends BaseService {
	

	/**
	 * �����û�ID��ѯ���еĻ��������
	 * @param kuid
	 * @return
	 */
	public List findByKuid(Long kuid);
	
	/**
	 * �����û��ı�š��û����ƣ��������ࣨ1��������,�������ģ�
	 * 
	 * @param kuId
	 * @param kuname
	 * @param lx
	 * @param jslwtyype
	 * @return
	 */
	public List findAllname(Long kuId, String kuname, Short lx, Short jslwtyype);

	/**
	 * �����û��ı�������������е��ѻ�ÿ������ġ������������������ġ��̲�
	 * 
	 * @param kuId
	 * @return
	 */
	List findByKuidAndType(Long kuId, Short lx, Short jslwtyype);

	/**
	 * �����������ƣ��ڿ��ͻ�������
	 * 
	 * @param mc
	 * @param kw
	 * @param hymc
	 * @return
	 */
	public boolean CheckOnlyOne(GhHylw hylw, String mc, Short lx, String kw);

	/**
	 * ���ݵ�λ���ű�Ų��Ҹõ�λ���Ž�ʦ���л��߽��������ͬ����Ŀ�ϲ�
	 * 
	 * @param kdid
	 * @return
	 */
	public List findSumKdId(long kdid, Short lx, Short state);

	/**
	 * <li>����������������Ŀ�������������
	 * 
	 * @param lwmc
	 * @param lwkw
	 * @return
	 */
	public List findByMCAndKW(String lwmc, String lwkw, Short lx);

	/**
	 * �����û��ı�š��û����ơ��������ࣨ1��������,�������ģ����������ơ����Ŀ���
	 * 
	 */
	public List findbyMcOrKw(Long kuId, String kuname, Short lx, Short jslwtyype, String mc, String kw);
	/**
	 * ������ȡ��������ࣨ1��������,�������ģ�����ʦ����SCI/EI/ISTP/���ģ�����Ŀ������ͳ��������Ŀ
	 * 
	 */
	public List findtjlw(String year, Short jslwtyype,String record, Long kuid);
	
	/**
	 * 
	 * @param year
	 * @param jslwtyype
	 * @param kuid
	 * @return
	 */
	public List findQtlw( String year, Short jslwtyype,Long kuid);
	
	/**
	 * ��������ID��ѯ�б�
	 * @param ids
	 * @return
	 */
	public List<GhHylw> findByLwIds(String ids);
}
