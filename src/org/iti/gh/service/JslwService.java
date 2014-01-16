package org.iti.gh.service;

import java.util.List;

import org.iti.gh.entity.GhJslw;

import com.uniwin.basehs.service.BaseService;

public interface JslwService extends BaseService {

	/**
	 * <li>����������������Ŀid���û�id���������ࣨ��������ģ��ڿ����ĵȣ�
	 * @param kuid
	 * @param lwid
	 * @param type
	 * @return
	 */
	public GhJslw findByKuidAndLwidAndType(Long kuid,Long lwid,Short type);
	
	/**
	 *  <li>����������������Ŀid���������ࣨ��������ģ��ڿ����ĵȣ�
	 * @param lwid
	 * @param type
	 * @return
	 */
	public List findByLwidAndType(Long lwid,Short type);
}
