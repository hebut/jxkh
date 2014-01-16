package org.iti.xypt.personal.infoCollect.service;



import java.util.List;

import org.iti.xypt.personal.infoCollect.entity.WkTExtractask;
import org.iti.xypt.personal.infoCollect.entity.WkTOrinfo;

import com.uniwin.basehs.service.BaseService;




public interface OriNewsService extends BaseService
{
	/**
	 * 
	 * @param keid ����ID
	 * @return  �����µ�ԭʼ��Ϣ
	 */
	public List getNewsOfOrinfo(Long keid);
	/**
	 * 
	 * @param koid ԭʼ��ϢID
	 * @return ��Ӧ����Ϣ������¼
	 */
	public WkTOrinfo getOriInfo(Long koid);
	/**
	 * 
	 * @param koid ԭʼ��ϢID
	 * @return  ��Ϣ����
	 */
	public List getOriInfocnt(Long koid);
	/**
	 * 
	 * @param d1 ��ʼʱ��
	 * @param d2 ��ֹʱ��
	 * @param sign �ؼ��ֶ�
	 * @param keys ��ѯ�ؼ���
	 * @param sources ��Ϣ��Դ
	 * @return   ��ϲ�ѯ��Ϣ�б�
	 */
	public List getSearchInfo(Long keid,String d1,String d2,String sign,String keys,String sources);
	/**
	 * 
	 * @param koid ԭʼ��ϢID
	 * @return ��Ϣ�����б�
	 */
	public List getOrifile(Long koid);
	public WkTExtractask getTask(Long keid);
	public List findAllInfo(Long keid);
	public List findReadInfo(Long keid,Long kuid);
}