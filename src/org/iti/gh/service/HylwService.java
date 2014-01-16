package org.iti.gh.service;

import java.util.List;

import org.iti.gh.entity.GhHylw;

import com.uniwin.basehs.service.BaseService;

public interface HylwService extends BaseService {
	

	/**
	 * 根据用户ID查询所有的会议的论文
	 * @param kuid
	 * @return
	 */
	public List findByKuid(Long kuid);
	
	/**
	 * 根据用户的编号、用户名称，论文种类（1科研论文,教研论文）
	 * 
	 * @param kuId
	 * @param kuname
	 * @param lx
	 * @param jslwtyype
	 * @return
	 */
	public List findAllname(Long kuId, String kuname, Short lx, Short jslwtyype);

	/**
	 * 根据用户的编号来查找其所有的已获得科研论文、科研著作、教研论文、教材
	 * 
	 * @param kuId
	 * @return
	 */
	List findByKuidAndType(Long kuId, Short lx, Short jslwtyype);

	/**
	 * 根据论文名称，期刊和会议名称
	 * 
	 * @param mc
	 * @param kw
	 * @param hymc
	 * @return
	 */
	public boolean CheckOnlyOne(GhHylw hylw, String mc, Short lx, String kw);

	/**
	 * 根据单位部门编号查找该单位部门教师科研或者教研情况，同类项目合并
	 * 
	 * @param kdid
	 * @return
	 */
	public List findSumKdId(long kdid, Short lx, Short state);

	/**
	 * <li>功能描述：根据题目，刊物查找论文
	 * 
	 * @param lwmc
	 * @param lwkw
	 * @return
	 */
	public List findByMCAndKW(String lwmc, String lwkw, Short lx);

	/**
	 * 根据用户的编号、用户名称、论文种类（1科研论文,教研论文）、论文名称、论文刊物
	 * 
	 */
	public List findbyMcOrKw(Long kuId, String kuname, Short lx, Short jslwtyype, String mc, String kw);
	/**
	 * 根据年度、论文种类（1科研论文,教研论文）、教师、（SCI/EI/ISTP/核心）、项目类型来统计论文数目
	 * 
	 */
	public List findtjlw(String year, Short jslwtyype,String record, Long kuid);
	
	/**
	 * 
	 * @param year
	 * @param jslwtyype
	 * @param kuid
	 * @return
	 */
	public List findQtlw( String year, Short jslwtyype,Long kuid);
	
	/**
	 * 根据论文ID查询列表
	 * @param ids
	 * @return
	 */
	public List<GhHylw> findByLwIds(String ids);
}
