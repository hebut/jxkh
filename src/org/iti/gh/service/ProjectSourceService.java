package org.iti.gh.service;

import java.util.List;

import org.iti.gh.entity.GH_PROJECTSOURCE;

import com.uniwin.basehs.service.BaseService;

public interface ProjectSourceService extends BaseService {

	/**
	 * ������Դ���Ʋ�����Ŀ��Դ�б�
	 * @param name
	 * @return
	 */
	public List<GH_PROJECTSOURCE> findByName(String name);
	
	/**
	 * ��÷��������ļ�¼��
	 * @param name
	 * @return
	 */
	public List<Long> getCountByName(String name);
	
	/**
	 * ������Դ���Ʒ�ҳ��ѯ��Ŀ��Դ�б�
	 * @param name
	 * @return
	 */
	public List<GH_PROJECTSOURCE> findByNameAndPaging(String name,int pageNum,int pageSize);
	
	/**
	 * �жϵ�ǰ��Ŀ��Դ�Ƿ��Ѵ���
	 * @param name
	 * @return true:�����ظ��ļ�¼
	 * 		   false:�������ظ��ļ�¼
	 */
	public boolean isNameRepeat(String name);
}
