package org.iti.bysj.service;

import java.util.List;

import org.iti.bysj.entity.BsNsource;

import com.uniwin.basehs.service.BaseService;

public interface NsourceService extends BaseService {

	/**
	 * <li>�������������ݱ��赥λ��ź����Ͳ�ѯ�������ʻ�����Դ�б�
	 * @param buid ���赥λ���
	 * @param type ���ʻ�Դ�ı�־
	 * @return ���ʻ���Դ�б�
	 */
	public List findByBuid(Long buid,short type );
	public List findByBuId(Long buid);
	
	public BsNsource findByBnsid(Long bnsid);
}
