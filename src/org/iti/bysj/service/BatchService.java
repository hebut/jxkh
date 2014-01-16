package org.iti.bysj.service;

import java.util.List;

import org.iti.bysj.entity.BsBatch;

import com.uniwin.basehs.service.BaseService;

public interface BatchService extends BaseService {

	/**
	 * <li>�������������ݱ��赥λ��Ų��ҵ�λ����
	 * 
	 * @param BuId
	 *            ���赥λ���
	 * @return
	 */
	List findByBuId(Long BuId);

	/**
	 * <li>��������������ѧ����Ų�������
	 * 
	 * @param BsId
	 * @return
	 */
	BsBatch findByBsId(Long BsId);

	/**
	 * <li>���������������������Ʋ�������
	 * 
	 * @param Buid
	 * @param Bbname
	 * @return ZM
	 */
	BsBatch findByBuidAndBbname(Long Buid, String Bbname);

	/**
	 * <li>���������������������Ʋ�������
	 * 
	 * @param Buid
	 * @param Bbname
	 * @return ZM
	 */
	List findByBuidandBbname(Long Buid, String Bbname);
}
