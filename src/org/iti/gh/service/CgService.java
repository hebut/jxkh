package org.iti.gh.service;

import java.util.List;

import org.iti.gh.entity.GhCg;

import com.uniwin.basehs.service.BaseService;

public interface CgService extends BaseService {

	/**
	 * ͨ���û���Ų������еĻ񽱿��л���гɹ�
	 * @param kuId
	 * @return �񽱿��гɹ�
	 */
	List findByKuid(Long kuId,Short lx,Integer type);
	/**
	 * ���ݵ�λ���ű�Ų��Ҹõ�λ���Ž�ʦ���л��߽������
	 * @param kdid
	 * @return
	 */
	public List findByKdId(long kdid,Short lx);
	/**
	 * ���ݵ�λ���ű�Ų��Ҹõ�λ���Ž�ʦ���л��߽��������ͬ����Ŀ�ϲ�
	 * @param kdid
	 * @return
	 */
	public List findSumKdId(long kdid,Short lx,Short state);
	/**
	 * �������Ʋ��ҽ�ʦ�ͽ�ʦ����
	 * @param name
	 * @param lx
	 * @return
	 */
	public List findByMc(String name,Short lx);
	/**
	 * ������Ŀ��Ų鿴���л���л�
	 * @param kynumber
	 * @param hlj
	 * @return
	 */
	public List findByKyNumAndKuId(String kynumber,Long kuId);
	/**
	 * ������Ŀ�Ų鿴���л���л�
	 * @param kyxmid
	 * @param hlj
	 * @return
	 */
	public List findByKyXmidAndKuId(Long kyxmid,Long kuId);
	
	/**
	 * <li>����������������Ŀid��ѯ�񽱼�¼
	 * @param kyxmid
	 * @return
	 */
	public List findByKyXmid(Long kyxmid);
	
	/**
	 * ��ѯ�������û��Ļ񽱳ɹ�
	 * @param kuid
	 * @param uname
	 * @param lx
	 * @param type
	 * @return
	 */
	public List findByKuidAndKunameAndLxAndType(Long kuid,String uname,Short lx,Integer type);
	/**
	 * ��ѯ�񽱳ɹ�
	 * @param kdid
	 * @param lx
	 * @param state
	 * @return
	 */
	public List findByKdidAndLxAndState(Long kdid,Short lx,Short state);
	
	/**
	 * ���ط���true���Ѿ��У�����false����û��
	 * @param name
	 * @param lx
	 * @param Dj
	 * @return
	 */
	public boolean CheckByNameAndLxAndDj(GhCg cg,String name,Short lx,String ly,String Dj);

	/**
	 * ģ����ѯ
	 * @param name
	 * @param lx
	 * @param ly
	 * @return
	 */
	public List findByNameAndLxAndDj(String name, Short lx, String ly,Long kuid,Integer type);
	/**
	 *��ȷ����
	 * @param name
	 * @param lx
	 * @param ly
	 * @return
	 */
	public GhCg  findByNameAndLxAndly(String name, Short lx, String ly);
	
	/**
	 * 
	 * @param kdid
	 * @param year
	 * @param kuid
	 * @return
	 */
	public List findByKdidAndYearAndKuid(String year,Long kuid,Integer type,Short jb);
	/**
	 * 
	 * @param kdid
	 * @param year
	 * @param kuid
	 * @param type
	 * @param jb
	 * @param cjmc
	 * @return
	 */
	public List findBykdidAndYearAndKuidAndCgmcAndType(Long kdid, String year, Long kuid,Integer type,Short jb,String cjmc);
	
	
	/**
	 * ͳ�ƻ񽱳ɹ�
	 * @param kuId
	 * @return �񽱿��гɹ�
	 */
	public List findCountByKuidAndCgkyAndHjky(Long kuId,Short lx,Integer type,Short fruitLevel);
	
	
	public List findByYearAndKuidAndCgmcAndType(String year, Long kuid,Integer type,Short jb);
	
	/**
	 * ���ݳɹ�ID��ѯ�б�
	 * @param ids
	 * @return
	 */
	public List<GhCg> findByKyIds(String ids);
}
