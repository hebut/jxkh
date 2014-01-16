package org.iti.gh.service;

import java.util.List;

import org.iti.gh.entity.GhQklw;

import com.uniwin.basehs.service.BaseService;

public interface QklwService extends BaseService {
	
	/**
	 * 根据用户ID查询所有期刊论文
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
	 * 根据论文名称发表刊物和Issn判重
	 * 
	 * @param name
	 * @param lx
	 * @param Issn
	 * @return
	 */
	public boolean CheckByNameAndLxAndIssn(GhQklw qklw, String name, Short lx, String Issn);

	/**
	 * 
	 * @param kdid
	 * @param lx
	 * @param state
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
	public List findByMCAndKW(String lwmc, String lwkw);

	/**
	 * 根据用户的编号、用户名称、论文种类（1科研论文,教研论文）、论文名称、论文刊物
	 * 
	 */
	public List findbyMcOrKw(Long kuId, String kuname, Short lx, Short jslwtyype, String mc, String kw);
	/**
	 * 
	 * @param year
	 * @param kuid
	 * @param record
	 * @param hx
	 * @param jslwtyype
	 * @return
	 */
	public List findByKdidAndYearAndKuidAndTypeAndRecordAndHx(String year,Long kuid,String record,Short hx,  Short jslwtyype);
	/**
	 * 
	 * @param year
	 * @param kuid
	 * @param jslwtyype
	 * @return
	 */
	public List findQtlw(String year,Long kuid,Short jslwtyype);
	
	/**
	 * 根据用户ID、索引类别、用户的作者排名查询符合条件的记录总数
	 * @param kuid 用户ID
	 * @param record 索引类别
	 * @param selfs 作者排名,0表示全部
	 * @return
	 */
	public List<Long> findCountByKuidRecordSelfs(Long kuid,String record,int selfs);
	
	/**
	 * 根据用户ID、是否核心、作者排名查询符合条件的记录总数
	 * @param kuid 用户ID
	 * @param center 是否核心，0：否，1：是
	 * @param selfs 作者排名 o表示全部
	 * @return
	 */
	public List<Long> findCountByKuidCenterSelfs(Long kuid,Short center,int selfs);
	public List findByKuidAndType(Long kuId,String name);
	
	/**
	 * 根据索引类别分类统计指定用户所承担字数总数
	 * @param kuId
	 * @param record
	 * @return
	 */
	public int getWordsByKuidRecord(Long kuId,String record);
	
	/**
	 * 根据是否核心分类统计指定用户所承担字数的总数
	 * @param kuid
	 * @param center
	 * @return
	 */
	public int getWordsByKuidIsCenter(Long kuid, Short center);
	
	/**
	 * 根据论文ID查询列表
	 * @param ids
	 * @return
	 */
	public List<GhQklw> findByLwIds(String ids);
	
}
