package org.iti.gh.service;

import java.util.List;

import org.iti.gh.entity.GH_ARCHIVE;

import com.uniwin.basehs.service.BaseService;

public interface ArchiveService extends BaseService {

	/**
	 * ������ĿID���û�ID�������������Ŀ�µ���������
	 * @param kyId ��ĿID
	 * @param kuId �û�ID
	 * @param category ��Ŀ���ͣ�1��������Ŀ��2��������Ŀ
	 * @return
	 */
	public List<GH_ARCHIVE> findByKyId(Long kyId,Long kuId,short category);
	
	/**
	 * ������ĿID���û�ID����ҳ�����������Ŀ�µ���������
	 * @param kyId ��ĿID
	 * @param kuId �û�ID
	 * @param category ��Ŀ���ͣ�1��������Ŀ��2��������Ŀ
	 * @param pageNum ��ǰҳ��
	 * @param pageSize ÿҳ����
	 * @return
	 */
	public List<GH_ARCHIVE> findByKyIdAndUserIdAndPage(Long kyId,Long kuId,short category, int pageNum,int pageSize);
	
	/**
	 * ������ĿID���û�ID����ѯ������Ŀ�µ����׼�¼����
	 * @param kyId ��ĿID
	 * @param kuId �û�ID
	 * @param category ��Ŀ���ͣ�1��������Ŀ��2��������Ŀ
	 * @return ���������ļ�¼����
	 */
	public List<Long> getCountByKyIdAndKuId(Long kyId,Long kuId,short category);
	
}
