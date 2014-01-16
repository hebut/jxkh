package org.iti.gh.service;

import java.util.List;

import org.iti.gh.entity.GH_ARCHIVECOMMENT;

import com.uniwin.basehs.service.BaseService;

public interface ArCommentService extends BaseService {

	/**
	 * 根据项目ID来查找其所有的文献
	 * @param kuId
	 * @return
	 */
	public List<GH_ARCHIVECOMMENT> findByArId(Long arId);
	
	/**
	 * 根据文献ID批量删除该参考文献的所有评论记录
	 * @param arId 参考文献ID
	 */
	public boolean deleteCommentsByArchive(Long arId);
	
}
