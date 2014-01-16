package org.iti.bysj.service;

import java.util.List;

import org.iti.bysj.entity.BsResults;

import com.uniwin.basehs.service.BaseService;

public interface ResultsService extends BaseService {

	/**
	 * <li>�������������ݱ���ѧ����š��ɼ������š�������Σ���ѯĳѧ����ĳ���ӳɼ�
	 * 
	 * @param bsid
	 *            ����ѧ�����
	 * @param bscid
	 *            �ɼ�������
	 * @param brbatch
	 *            �������
	 * @return �ӳɼ�
	 */
	List findByBsidAndBscidAndBrbatch(Long bsid, Long bscid, Short brbatch);

	Double findAvgByBsidAndBscidAndBrbatch(Long bsid, Long bscid, Short brbatch);

	List findByBbId(Long bbid);

	List findByBuid(Long buid);

	/**
	 * <li>�������������ݱ���ѧ����š��ɼ������š�������Ρ� �ɼ������ˣ���ѯĳѧ����ĳ���ӳɼ�
	 * 
	 * @param bsid
	 *            ����ѧ�����
	 * @param bscid
	 *            �ɼ�������
	 * @param brbatch
	 *            �������
	 * @param kuid
	 *            �ɼ�������
	 * @return �ӳɼ� hlj
	 */
	BsResults findByBsidAndBscidAndBrbatchAndKuid(Long bsid, Long bscid, Short brbatch, Long kuid);

	/**
	 * ����ͬ�����ı���������ͬ�����Ľ�ʦ�ɼ�
	 * 
	 * @param BPV_ID
	 * @return �ɼ�
	 * @author ��ѩ
	 */
	public Double findSumBypeer(Long kuid, Long bdbid);

	/**
	 * <li>�������������ݱ��赥λ��š����������û���������Ρ����������š��ɼ����ͣ���ѯĳѧ����ĳ���ӳɼ�
	 * 
	 * @param buid
	 *            ���赥λ���
	 * @param kuid
	 *            �û����
	 * @param batchid
	 *            �������
	 * @param bgid
	 *            ����������
	 * @param bsfrom
	 *            �ɼ�����
	 * @author DATIAN
	 * @return �ӳɼ��б�
	 */
	List findByBuidAndKuidAndBatchidAndBgidAndBsfrom(Long buid, Long kuid, Short batchid, Long bgid, Short bsfrom);
	/**
	 * <li>�������������ҵ�ǰרҵ��ţ�������� �ͳɼ���Դ�����Ƿ��гɼ��б�
	 * @param buid רҵ���
	 * @param batchid �������
	 * @param bgid �������
	 * @param bsfrom �ɼ���Դ
	 * @return
	 */
	List findByBuidAndBatchidAndBgidAndBsfrom(Long buid,Short batchid, Long bgid, Short bsfrom);
	/**
	 * <li>���������������û���š�ѧ����š��ɼ������Ų�ѯ
	 * 
	 */
	List findByKuidAndBsidAndBscid(Long kuid, Long bsid, Long bscid);

	/**
	 * <li>��������������ѧ����š��ɼ������Ų�ѯ
	 * 
	 */
	List findByBsidAndBscid(Long bsid, Long bscid);
}
