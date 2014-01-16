package org.iti.gh.service;

import java.util.List;

import com.uniwin.basehs.service.BaseService;

public interface XmzzService extends BaseService {
	
	/**
	 * 根据论文编号查找论文的资助
	 * @param lwId
	 * @return
	 */
	List findByLwidAndKuid(Long lwid,Short lwType);
	/**
	 * 根据项目编号查找论文的资助
	 * @param lwId
	 * @return
	 */
	List findByKyidAndKuidAndType(Long kyid,Long kuid,Short lwType);
	/**
	 * 根据项目编号饿论文编号查找论文的资助
	 * @param lwId
	 * @return
	 */
	List findByKyidAndKuidAndLwidAndType(Long kyid,Long kuid,Long lwid,Short lwType);
}
