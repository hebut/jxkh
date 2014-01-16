package org.iti.projectmanage.science.reference.service;

import java.util.List;

import org.iti.gh.entity.GH_ARCHIVE;

import com.uniwin.basehs.service.BaseService;

public interface ReferenceService extends BaseService
{
	/**
	 * ����������Դ������ױ��⡢���ߡ�������Դ������š�����ʱ���ѯ�ο������б�
	 * @param sourceType ������Դ���
	 * 		0��ȫ����1��CNKI�ڿ�ȫ�Ŀ⣬2���й���Ҫ�������Ŀ⣬3��EI��4������
	 * @param searchFiled ��ѯ�ֶΣ�
	 * 		0��������1���ؼ��ʣ�2�����ߣ�3��������Դ��4�������
	 * @param content ��ѯ����
	 * @param startTime ��ѯ��ʼ����
	 * @param endTime ��ѯ��������
	 * @param category ��Ŀ���ͣ�1��������Ŀ��2��������Ŀ
	 * @return
	 */
	public List<GH_ARCHIVE> findByTKAPSF(int sourceType,int searchFiled,String content,
			String startTime,String endTime,String clcString, String issueString,short category);
	
	/**
	 * ����������Դ������ױ��⡢���ߡ�������Դ������š�����ʱ��,��ҳ��ѯ�ο������б�
	 * @param sourceType ������Դ���
	 * 		0��ȫ����1��CNKI�ڿ�ȫ�Ŀ⣬2���й���Ҫ�������Ŀ⣬3��EI��4������
	 * @param searchFiled ��ѯ�ֶΣ�
	 * 		0��������1���ؼ��ʣ�2�����ߣ�3��������Դ��4�������
	 * @param content ��ѯ����
	 * @param startTime ��ѯ��ʼ����
	 * @param endTime ��ѯ��������
	 * @param category ��Ŀ���ͣ�1��������Ŀ��2��������Ŀ
	 * @param pageNum ��ǰҳ��
	 * @param pageSize ÿҳ����
	 * @return
	 */
	public List<GH_ARCHIVE> findByTKAPSFAndPage(int sourceType,int searchFiled,	String content,String startTime,
			String endTime,String clcString, String issueString,short category,int pageNum, int pageSize);
	
	/**
	 * ����������Դ������ױ��⡢���ߡ�������Դ������š�����ʱ���ѯ���������Ĳο����׼�¼����
	 * @param sourceType ������Դ���
	 * 		0��ȫ����1��CNKI�ڿ�ȫ�Ŀ⣬2���й���Ҫ�������Ŀ⣬3��EI��4������
	 * @param searchFiled ��ѯ�ֶΣ�
	 * 		0��������1���ؼ��ʣ�2�����ߣ�3��������Դ��4�������
	 * @param content ��ѯ����
	 * @param startTime ��ѯ��ʼ����
	 * @param endTime ��ѯ��������
	 * @param category ��Ŀ���ͣ�1��������Ŀ��2��������Ŀ
	 * @return ���������ļ�¼����
	 */
	public List<Long> findCountByTKAPSF(int sourceType,int searchFiled,	String content,String startTime,
			String endTime,String clcString, String issueString,short category);
}
