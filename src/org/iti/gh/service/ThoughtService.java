package org.iti.gh.service;

import org.iti.gh.entity.TeaThought;

import com.uniwin.basehs.service.BaseService;

public interface ThoughtService extends BaseService {

	/**
	 * <li>���������������û�id����Ȳ�ѯ�û����˼���ܽ�
	 * @param kuid
	 * @param year
	 * @return
	 */
	public TeaThought findByKuidAndYear(Long kuid,String year);
}
