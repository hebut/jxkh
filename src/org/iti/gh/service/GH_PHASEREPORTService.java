package org.iti.gh.service;

import java.util.List;


import com.uniwin.basehs.service.BaseService;

public interface GH_PHASEREPORTService extends BaseService {
	/**
	 * 通过项目编号、用户登录名获得项目的阶段报告列表
	 * @param kyId
	 * @param kuLid
	 * @return 阶段报告列表
	 */
	public List findStageByProidAndKulid(Long kyId,Long kuId);
	/**
	 * 通过项目编号、用户登录名分页获得项目的阶段报告列表
	 * @param kyId
	 * @param kuLid
	 * @param pageNum
	 * @param pageSize
	 * @return 阶段报告列表
	 */
	public List findStageByProidAndKulid(Long kyId,Long kuId,int pageNum, int pageSize);
	/**
	 * 通过项目编号、用户登录名获得项目的阶段报告数
	 * @param kyId
	 * @param kuLid	
	 * @return 阶段报告数
	 */
	public List findPageCount(Long kyId,Long kuId);
	/**
	 * 通过项目编号该获得项目的所有阶段报告总数
	 * @param kyId	 
	 * @return 阶段报告总数
	 */
	public List findReportSum(Long kyId);
	/**
	 * 通过项目编号该获得项目的所有阶段报告列表
	 * @param kyId	 
	 * @return 阶段报告列表
	 */
	public List findByKyxmId(Long kyId);
	/**
	 * 通过项目编号分页获得项目的阶段报告列表
	 * @param kyId
	 * @param pageNum
	 * @param pageSize
	 * @return 阶段报告列表
	 */
	public List findByKyxmId(Long kyId,int pageNum, int pageSize);
	/**
	 * 通过项目编号、阶段报告名称分页获得项目的阶段报告列表
	 * @param kyId
	 * @param phRepoName
	 * @param pageNum
	 * @param pageSize
	 * @return 阶段报告列表
	 */
	public List findByKyxmIdAndReportName(Long kyId,String phRepoName,int pageNum, int pageSize);
	/**
	 * 通过项目编号、阶段报告名称获得项目的所有阶段报告总数
	 * @param kyId
	 * @param phRepoName	 
	 * @return 阶段报告总数
	 */
	public List findReportTotalSum(Long kyId,String phRepoName);	
	/**
	 * 通过项目编号、阶段报告关键词分页获得项目的阶段报告列表
	 * @param kyId
	 * @param keyWord
	 * @param pageNum
	 * @param pageSize
	 * @return 阶段报告列表
	 */
	public List findByKyxmIdAndKeyWord(Long kyId,String keyWord,int pageNum, int pageSize);
	/**
	 * 通过项目编号、阶段报告关键词获得项目的所有阶段报告总数
	 * @param kyId
	 * @param keyWord	 
	 * @return 阶段报告总数
	 */
	public List findReportTotalSumByKeyWord(Long kyId,String keyWord);
	
}
