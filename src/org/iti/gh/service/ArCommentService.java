package org.iti.gh.service;

import java.util.List;

import org.iti.gh.entity.GH_ARCHIVECOMMENT;

import com.uniwin.basehs.service.BaseService;

public interface ArCommentService extends BaseService {

	/**
	 * ������ĿID�����������е�����
	 * @param kuId
	 * @return
	 */
	public List<GH_ARCHIVECOMMENT> findByArId(Long arId);
	
	/**
	 * ��������ID����ɾ���òο����׵��������ۼ�¼
	 * @param arId �ο�����ID
	 */
	public boolean deleteCommentsByArchive(Long arId);
	
}
