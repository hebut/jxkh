package org.iti.cqgl.service;

import org.iti.cqgl.entity.CqCqcs;

import java.util.List;


import com.uniwin.basehs.service.BaseService;

public interface CqcsService extends BaseService {

	/**
	 * <li>��������������У����Ų�ѯѧ�ڳ��ڴ���
	 * @param xlId
	 * @return
	 */
	public CqCqcs findByXlid(Long xlId);
	/**
	 * <li>��������������ѧ�ꡢѧ�ڡ�ѧУid��ѯ���ڴ������趨
	 * @param year
	 * @param term
	 * @param schid
	 * @return
	 */
	public List findByYearAndTerm(String year,Short term,Long schid);
    CqCqcs findByXlId(Long xlId);
    public Long findByxlYearAndTerm(String year,Short term,Long schid);
    public Long findKyByxlYearAndTerm(String year,Short term,Long schid);
}
