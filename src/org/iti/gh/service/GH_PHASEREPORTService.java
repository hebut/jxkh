package org.iti.gh.service;

import java.util.List;


import com.uniwin.basehs.service.BaseService;

public interface GH_PHASEREPORTService extends BaseService {
	/**
	 * ͨ����Ŀ��š��û���¼�������Ŀ�Ľ׶α����б�
	 * @param kyId
	 * @param kuLid
	 * @return �׶α����б�
	 */
	public List findStageByProidAndKulid(Long kyId,Long kuId);
	/**
	 * ͨ����Ŀ��š��û���¼����ҳ�����Ŀ�Ľ׶α����б�
	 * @param kyId
	 * @param kuLid
	 * @param pageNum
	 * @param pageSize
	 * @return �׶α����б�
	 */
	public List findStageByProidAndKulid(Long kyId,Long kuId,int pageNum, int pageSize);
	/**
	 * ͨ����Ŀ��š��û���¼�������Ŀ�Ľ׶α�����
	 * @param kyId
	 * @param kuLid	
	 * @return �׶α�����
	 */
	public List findPageCount(Long kyId,Long kuId);
	/**
	 * ͨ����Ŀ��Ÿû����Ŀ�����н׶α�������
	 * @param kyId	 
	 * @return �׶α�������
	 */
	public List findReportSum(Long kyId);
	/**
	 * ͨ����Ŀ��Ÿû����Ŀ�����н׶α����б�
	 * @param kyId	 
	 * @return �׶α����б�
	 */
	public List findByKyxmId(Long kyId);
	/**
	 * ͨ����Ŀ��ŷ�ҳ�����Ŀ�Ľ׶α����б�
	 * @param kyId
	 * @param pageNum
	 * @param pageSize
	 * @return �׶α����б�
	 */
	public List findByKyxmId(Long kyId,int pageNum, int pageSize);
	/**
	 * ͨ����Ŀ��š��׶α������Ʒ�ҳ�����Ŀ�Ľ׶α����б�
	 * @param kyId
	 * @param phRepoName
	 * @param pageNum
	 * @param pageSize
	 * @return �׶α����б�
	 */
	public List findByKyxmIdAndReportName(Long kyId,String phRepoName,int pageNum, int pageSize);
	/**
	 * ͨ����Ŀ��š��׶α������ƻ����Ŀ�����н׶α�������
	 * @param kyId
	 * @param phRepoName	 
	 * @return �׶α�������
	 */
	public List findReportTotalSum(Long kyId,String phRepoName);	
	/**
	 * ͨ����Ŀ��š��׶α���ؼ��ʷ�ҳ�����Ŀ�Ľ׶α����б�
	 * @param kyId
	 * @param keyWord
	 * @param pageNum
	 * @param pageSize
	 * @return �׶α����б�
	 */
	public List findByKyxmIdAndKeyWord(Long kyId,String keyWord,int pageNum, int pageSize);
	/**
	 * ͨ����Ŀ��š��׶α���ؼ��ʻ����Ŀ�����н׶α�������
	 * @param kyId
	 * @param keyWord	 
	 * @return �׶α�������
	 */
	public List findReportTotalSumByKeyWord(Long kyId,String keyWord);
	
}
