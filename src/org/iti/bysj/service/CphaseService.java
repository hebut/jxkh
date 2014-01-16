package org.iti.bysj.service;

import java.util.List;

import org.iti.bysj.entity.BsCphase;

import com.uniwin.basehs.service.BaseService;

public interface CphaseService extends BaseService {

	/**
	 *<li>�������������ݱ��赥λ��Ų����ӽ׶�
	 * 
	 * @param BuId
	 *            ���赥λ���
	 * @return
	 */
	List findByBuId(Long BuId);

	/**
	 * *<li>�������������ݱ������α�Ų����ӽ׶�
	 * 
	 * @param bbid
	 *            �������α��
	 * @return �ӽ׶��ճ��б�
	 */
	List findByBbId(Long bbid);

	/**
	 * <li>�������������ݿ����Ų�ѯ�ӽ׶��б�
	 * 
	 * @param bprid
	 *            ����
	 * @return �ÿ��������ӽ׶��б�
	 */
	List findByBprid(Long bprid);

	/**
	 * <li>�������������ݱ��赥λ��š��ϴ��û���ѯ��ʦ��ѧ���ύ�ĵ��ӽ׶�
	 * 
	 * @param buid
	 *            ���赥λ���
	 * @param upuser
	 *            �ϴ��û�
	 * @return �ӽ׶��б�
	 */
	List findByBuidAndUpuser(Long buid, short upuser);

	/**
	 * <li>�����������������α�Ų�ѯ��ʦ��ѧ���ύ�ĵ��ӽ׶�
	 * 
	 * @param bbid
	 *            ���α��
	 * @param upuser
	 * @return
	 */
	List findByBbidAndUpuser(long bbid, short upuser);

	/**
	 * <li>�����������������α�Ų�ѯ��ʦ��ѧ���ύ�ĵ��ӽ׶�
	 * 
	 * @param bbid
	 *            ���α��
	 * @param upuser
	 * @return
	 */
	List findByBuIdAndBbId(long buid, long bbid);

//	/**
//	 * <li>������������ѯ��ǰ�����ĸ��ӽ׶Σ�δ������
//	 */
//	Long findWhenNowNB(Long now, Long buid);
//
//	/**
//	 * <li>������������ѯ��ǰ�����ĸ��ӽ׶Σ�������
//	 */
//	Long findWhenNowYB(Long now, Long bbid);
	public Long getMinStartAndBuid(Long buId);
	public Long getMaxEndAndBbid(Long bbId);
	public Long getMinStartAndBbid(Long bbId);
	public Long getMaxEndAndBuid(Long buId);
}
