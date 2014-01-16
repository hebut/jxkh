package org.iti.cqgl.service;

import org.iti.cqgl.entity.CqCqcs;

import java.util.List;


import com.uniwin.basehs.service.BaseService;

public interface CqcsService extends BaseService {

	/**
	 * <li>功能描述：根据校历编号查询学期出勤次数
	 * @param xlId
	 * @return
	 */
	public CqCqcs findByXlid(Long xlId);
	/**
	 * <li>功能描述：根据学年、学期、学校id查询出勤次数的设定
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
