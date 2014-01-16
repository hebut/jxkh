package org.iti.bysj.service;


import java.util.List;


import com.uniwin.basehs.service.BaseService;

public interface PhaseService extends BaseService {

	/**
	 * <li>�������������ݱ��赥λ��ź�˳��Ų��ҽ׶�
	 * @param buid ���赥λ���
	 * @param order ˳���
	 * @return �׶��б�
	 */
	List findByBuIdAndOrder(Long buid,int order);
	
	/**
	 * <li>�������������ݱ������α�ź�˳��Ų��ҽ׶�
	 * @param bbid �������α��
	 * @param order ˳���
	 * @return �׶��б�
	 */
	List findByBbIdAndOrder(Long bbid,int order);
	
	/**
	 * <li>�������������ݱ������α�Ų��ҽ׶�
	 * @param bbid ���赥λ���
	 * @return �׶��б�
	 */
	
	List findByBbId(Long bbid);
	
	List findByBuId(Long buid);
    List findByBbIdHaveRc(Long bbid);
	
	List findByBuIdHaveRc(Long buid);


	/**
	 * <li>�������������ݱ��赥λ��ź����α�Ų��ҽ׶�
	 * @author DATIAN
	 * @param buId ���赥λ���
	 * @param bbId ���α��
	 * @return �׶��б�
	 */
	List findByBuIdAndBbId(Long buId,Long bbId);
	/**
	 * <li>�������������ݱ��赥λ��ź�˳��Ų���������ʱ��
	 * @author DATIAN
	 * @param buId ���赥λ���
	 * @param bbId ���α��
	 * @return �׶��б�
	 */
	Long getMaxEndAndBuidAndOder(Long buId,int order);
	/**
	 * <li>�������������ݱ��赥λ��ź�˳��Ų�����С��ʼʱ��
	 * @author DATIAN
	 * @param buId ���赥λ���
	 * @param bbId ���α��
	 * @return �׶��б�
	 */
	Long getMinStartAndBuidAndOder(Long buId,int order);
	/**
	 * <li>���������������û���ź�ʱ�䣬���Ҹ��û�Ϊרҵ�Ľ�ɫʱ�ڸ�ʱ�������Ľ׶�
	 * @author lys
	 * @param kuId ���赥λ���
	 * @param time  ʱ��
	 * @return �׶��б�
	 */
	List findByKuidAndTime(Long kuid,Long time);
	/**
	 * <li>���������������û���ź�ʱ�䣬���Ҹ��û�Ϊרҵ����ѧԺ�Ľ�ɫʱ�ڸ�ʱ�������Ľ׶�
	 * @author lys
	 * @param kuId ���赥λ���
	 * @param time  ʱ��
	 * @return �׶��б�
	 */
	List findByKuidAndTime2(Long kuid,Long time);
	/**
	 * <li>���������������û���ź�ʱ�䣬���Ҹ��û�Ϊרҵ����ѧԺ����ѧУ�Ľ�ɫʱ�ڸ�ʱ�������Ľ׶�
	 * @author lys
	 * @param kuId ���赥λ���
	 * @param time  ʱ��
	 * @return �׶��б�
	 */
	List findByKuidAndTime3(Long kuid,Long time);
	/**
	 * 
	 * @param kuid
	 * @param time
	 * @return
	 */
	List findByKuidAndNow(Long kuid,Long time);
	
}
