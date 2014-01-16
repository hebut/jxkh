package org.iti.cqgl.service;

import java.util.List;

import com.uniwin.basehs.service.BaseService;

public interface NstudentService extends BaseService {

	/**
	 * <li>��������������ѧ�ꡢѧ�ڡ�ѧУid��ѯ����ѧ��
	 * @param year
	 * @param term
	 * @param schid
	 * @return
	 */
	public List findByYearAndTerm(String year,Short term,Long schid,String name, String xuehao);
	/**
	 * 
	 * @param year
	 * @param term
	 * @param schid
	 * @param grade
	 * @return
	 */
	public List findByYearAndTermAndgrade(String year,Short term,Long schid ,Long xykdid,Integer[] grade);
	/**
	 * 
	 * @param year
	 * @param term
	 * @param schid
	 * @param zykdid
	 * @param grade
	 * @return
	 */
	public List findZyByYearAndTermAndgrade(String year,Short term,Long schid ,Long zykdid,Integer[] grade);
	/**
	 * 
	 * @param year
	 * @param term
	 * @param schid
	 * @param classid
	 * @param grade
	 * @return
	 */
	public List findClassByYearAndTermAndgrade(String year,Short term,Long schid ,Long classid,Integer[] grade);
	public List findClassByYearAndTerm(String year,Short term,Long schid ,Long classid);
	/**
	 * �ж��Ƿ�Ϊ����ѧ��
	 * @param xlid
	 * @param kuid
	 * @return
	 */
	public boolean CheckIf(Long xlid,Long kuid);
}
