package org.iti.bysj.service;

import java.util.List;

import org.iti.bysj.entity.BsGpunit;

import com.uniwin.basehs.service.BaseService;

/**
 * @author FengXinhong
 * 2010-07-08
 */
public interface GpunitService extends BaseService {

	/**
	 * <li>�������������ݱ��赥λ���ҷ������
	 * @param buid ���赥λ���
	 * @return �Ƿ����
	 * @author FengXinhong
     * 2010-07-08
	 */	
	Short findByBuid(Long Buid);
	

	/**
	 * <li>����������ϵ��ż�������̱�Ų�ѯ���赥λ�б� ��
	 * @param kdId  ϵ���
	 * @param gpid  ������̱��
	 * @return
	 * BsGpunit 
	 * @author DaLei
	 */
	public BsGpunit  findByKdidAndGpid(Long kdId,Long gpid);

	/**
	 * <li>�������������ݱ��赥λ��Ų�ѯһ��ѧ���ķ��鷽ʽ
	 * @param buid ���赥λ���
	 * @return ���鷽ʽ
	 * @author FengXinhong
     * 2010-07-08
	 */
	Short findPacketwayByBuid(Long buid);
	/**
	 * <li>�������������ݱ��赥λ��ѯ����ѧ���ķ��鷽ʽ
	 * @param buid ���赥λ���
	 * @return ���鷽ʽ
	 * @author FengXinhong
     * 2010-07-08
	 */
	Short findSpacketByBuid(long buid);
	/**
	 * <li>��������:���ݱ��赥λ��ѯ����ʸ�ȷ��
	 * @param buid ���赥λ���
	 * @return 
	 * @author FengXinhong
     * 2010-07-08
	 */
	Short findDbzgByBuid(long buid);
	
	/**
	 * <li>��������:����ָ����ʦ��Ų��ұ��赥λ
	 * @param BtId ָ����ʦ���
	 * @return 
	 * @author FengXinhong
     * 2010-07-08
	 */
	List findByBtId(Long BtId);
	
	/**
	 * <li>��������:���ݱ�����̱�Ų�ѯ���赥λ
	 * @param bgid ������̱��
	 * @return ���赥λ�б�
	 * @author FengXinhong
     * 2010-07-08
	 */
	List findByBgid(Long bgid);


	/**
	 * <li>������������ѯ��ʦ�û�����ĳ��������̵�ȫ�����赥λ�б�
	 * @param kuid ��ʦ�û��ı��
	 * @param bgid ������̱��
	 * @return
	 * List 
	 * @author DaLei
	 */
	public List findByKuidAndBgid(Long kuid,Long bgid);

	/**
	 * <li>�������������ݱ��赥λ����ѯ�Ƿ������ɹ��ɼ�
	 * @param buid ���赥λ���
	 * @return 
	 * @author FengXinhong
	 * 2010-07-08
	 */
	Short isUpdateFruit(Long buid);
	
	/**
	 * <li>������������ѯĳѧԺ��ĳ�������ȫ�����赥λ�б�
	 * @param kdid ѧԺ�ı��
	 * @param bgid ������̱��
	 * @return
	 * List  ���赥λ�б�
	 * @author DaLei
	 */
	List findByPkdidAndBgid(Long kdid,Long bgid);
	
	
	List findByPeerviewKuidAndBgid(Long kuid,Long bgid);
	/**
	 * ���赥λ����
	 * @return
	 * * @author lly
	 */
	public Integer countBsGpunit(Long bgid);
	/**
	 * ���赥λ�������������Ϊ0������
	 * @return
	 * * @author lly
	 */
	public Integer countBUMAX(Long bgid);
}
