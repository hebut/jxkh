package org.iti.gh.service;

import java.util.List;

import org.iti.gh.entity.GH_ARCHIVE;

import com.uniwin.basehs.service.BaseService;

public interface ArchiveService extends BaseService {

	/**
	 * 根据项目ID和用户ID来查找其科研项目下的所有文献
	 * @param kyId 项目ID
	 * @param kuId 用户ID
	 * @param category 项目类型，1：科研项目，2：教研项目
	 * @return
	 */
	public List<GH_ARCHIVE> findByKyId(Long kyId,Long kuId,short category);
	
	/**
	 * 根据项目ID和用户ID来分页查找其科研项目下的所有文献
	 * @param kyId 项目ID
	 * @param kuId 用户ID
	 * @param category 项目类型，1：科研项目，2：教研项目
	 * @param pageNum 当前页数
	 * @param pageSize 每页条数
	 * @return
	 */
	public List<GH_ARCHIVE> findByKyIdAndUserIdAndPage(Long kyId,Long kuId,short category, int pageNum,int pageSize);
	
	/**
	 * 根据项目ID和用户ID来查询科研项目下的文献记录总数
	 * @param kyId 项目ID
	 * @param kuId 用户ID
	 * @param category 项目类型，1：科研项目，2：教研项目
	 * @return 符合条件的记录总数
	 */
	public List<Long> getCountByKyIdAndKuId(Long kyId,Long kuId,short category);
	
}
