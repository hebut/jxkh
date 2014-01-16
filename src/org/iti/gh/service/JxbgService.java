package org.iti.gh.service;

import java.util.List;

import org.iti.gh.entity.GhJxbg;

import com.uniwin.basehs.service.BaseService;

public interface JxbgService extends BaseService {

	/**
	 * ���ݵ�λ���ű�Ų��Ҹõ�λ���Ž�ʦ��ѧ�������
	 * @param kdid
	 * @return
	 */
	public List findByKdIdAndCj(long kdid,Short ifcj,Short state);
	/**
	 * �����û����Ҹý�ʦ�Ľ�ѧ����
	 * @param kuid
	 * @return
	 */
	public List findByKuidAndCj(long kuid,Short ifcj);
	/**
	 * �����û�����id���Ƿ�μӣ����״̬��ѯ��ѧ����
	 * @param kdid
	 * @param ifcj
	 * @param state
	 * @return
	 */
	public List findByKdidAndCjAndState(Long kdid,Short ifcj,Short state);
	
	/**
	 * 
	 * @param jxbg
	 * @param bgname
	 * @param jxhymc
	 * @param ifcj
	 * @return
	 */
	public boolean CheckOnlyOne(GhJxbg jxbg, String bgname,String jxhymc,Short ifcj,Long kuid);
	/**
	 * 
	 * @param kdid
	 * @param year
	 * @param kuid
	 * @param ifcj
	 * @param lx
	 * @return
	 */
	public List findByKdidAndYearAndKuidAndIfcj(String year,Long kuid,Short ifcj,Short lx);
}
