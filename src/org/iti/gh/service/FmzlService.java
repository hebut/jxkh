package org.iti.gh.service;

import java.util.List;

import org.iti.gh.entity.GhFmzl;

import com.uniwin.basehs.service.BaseService;

public interface FmzlService extends BaseService {
	/**
	 * �����û��ı�������ҿ��з���ר��
	 * @param kuId
	 * @return
	 */
	List findByKuid(Long kuId);
	/**
	 * ���ݵ�λ���ű�Ų��Ҹõ�λ���Ž�ʦ���п��з���ר��
	 * @param kdid
	 * @return
	 */
	public List findByKdId(long kdid);
	/**
	 * ���ݵ�λ���ű�Ų��Ҹõ�λ���Ž�ʦ���п��з���ר����ͬ����Ŀ�ϲ�
	 * @param kdid
	 * @return
	 */
	public List findSumKdId(long kdid,Short state);
	/**
	 * �������Ʋ��ҽ�ʦ�ͽ�ʦ����
	 * @param name
	 * @param lx
	 * @return
	 */
	public List findByMc(String name);
	
	/**
	 * �����û�id���������ҿ�������û��йص�ר��
	 * @param kuid
	 * @param name
	 * @return
	 */
	public List findByKuidAndUname(Long kuid,String name);
	
	/**
	 * <li>��������������kdid�����״̬��ѯ����ר��
	 * @param kdid
	 * @param state
	 * @return
	 */
	public List findByKdidAndState(Long kdid,Short state);
	
	/**
	 * ����
	 * @param fmzl
	 * @param mc
	 * @param zlh
	 * @return
	 */
	public boolean CheckOnlyOne(GhFmzl fmzl,String mc,String zlh);
	/**
	 * ���ݷ������ƺͷ�����ģ����ѯר��
	 * @param fmmc
	 * @param uname
	 * @return
	 */
	public List findByFmmcAndFmrname(String fmmc,String uname,Long kuid);
	/**
	 * 
	 * @param fmmc
	 * @return
	 */
	public GhFmzl finByFmmc(String fmmc);
	/**
	 * 
	 * @param year
	 * @param kuid
	 * @return
	 */
	  public List findBykdidAndYearAndKuid(String year,Long kuid,String type);
	  
	  /**
	   * �����û�ID���û�������ѯ���������ļ�¼����
	   * @param kuid
	   * @param selfs
	   * @return
	   */
	 public int getCountBykuidSelfs(Long kuid, int selfs);
	 
	 public List<GhFmzl> findByFmIds(String ids);
}
