package org.iti.gh.service;

import java.util.List;

import org.iti.gh.entity.GhQklw;

import com.uniwin.basehs.service.BaseService;

public interface QklwService extends BaseService {
	
	/**
	 * �����û�ID��ѯ�����ڿ�����
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
	 * �����������Ʒ������Issn����
	 * 
	 * @param name
	 * @param lx
	 * @param Issn
	 * @return
	 */
	public boolean CheckByNameAndLxAndIssn(GhQklw qklw, String name, Short lx, String Issn);

	/**
	 * 
	 * @param kdid
	 * @param lx
	 * @param state
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
	public List findByMCAndKW(String lwmc, String lwkw);

	/**
	 * �����û��ı�š��û����ơ��������ࣨ1��������,�������ģ����������ơ����Ŀ���
	 * 
	 */
	public List findbyMcOrKw(Long kuId, String kuname, Short lx, Short jslwtyype, String mc, String kw);
	/**
	 * 
	 * @param year
	 * @param kuid
	 * @param record
	 * @param hx
	 * @param jslwtyype
	 * @return
	 */
	public List findByKdidAndYearAndKuidAndTypeAndRecordAndHx(String year,Long kuid,String record,Short hx,  Short jslwtyype);
	/**
	 * 
	 * @param year
	 * @param kuid
	 * @param jslwtyype
	 * @return
	 */
	public List findQtlw(String year,Long kuid,Short jslwtyype);
	
	/**
	 * �����û�ID����������û�������������ѯ���������ļ�¼����
	 * @param kuid �û�ID
	 * @param record �������
	 * @param selfs ��������,0��ʾȫ��
	 * @return
	 */
	public List<Long> findCountByKuidRecordSelfs(Long kuid,String record,int selfs);
	
	/**
	 * �����û�ID���Ƿ���ġ�����������ѯ���������ļ�¼����
	 * @param kuid �û�ID
	 * @param center �Ƿ���ģ�0����1����
	 * @param selfs �������� o��ʾȫ��
	 * @return
	 */
	public List<Long> findCountByKuidCenterSelfs(Long kuid,Short center,int selfs);
	public List findByKuidAndType(Long kuId,String name);
	
	/**
	 * ��������������ͳ��ָ���û����е���������
	 * @param kuId
	 * @param record
	 * @return
	 */
	public int getWordsByKuidRecord(Long kuId,String record);
	
	/**
	 * �����Ƿ���ķ���ͳ��ָ���û����е�����������
	 * @param kuid
	 * @param center
	 * @return
	 */
	public int getWordsByKuidIsCenter(Long kuid, Short center);
	
	/**
	 * ��������ID��ѯ�б�
	 * @param ids
	 * @return
	 */
	public List<GhQklw> findByLwIds(String ids);
	
}
