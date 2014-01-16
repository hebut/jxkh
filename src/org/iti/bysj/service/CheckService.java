package org.iti.bysj.service;

import java.util.List;

import com.uniwin.basehs.service.BaseService;

public interface CheckService extends BaseService{

	
	/**
	 * ���ݷ����˵ı�źͽ�ɫ��ź�ѧ����ź��ӽ׶α������ѯ��������
	 * @param kuid �����˵ı��
	 * @param krid �����˵Ľ�ɫ���
	 * @param bsid ѧ�����
	 * @param bcpid �ӽ׶α��
	 * @return ��������
	 * @author DATIAN
	 */
	List findByKuidAndKridAndBsidAndBcpid(Long kuid,Long krid,Long bsid,Long bcpid);

	
	
	/**
	 * ���ݷ����˵ı�źͽ�ɫ��źͼ���ӽ׶α������ѯ��������
	 * @param kuid �����˵ı��
	 * @param krid �����˵Ľ�ɫ���
	 * @param bcpid ����ӽ׶α��
	 * @return ��������
	 * @author DATIAN
	 */
	List findByKuidAndKridAndBcpid(Long kuid,Long krid,Long bcpid);
	
	
	/**
	 * ���ݷ����˵ı���䷢���ĵ�������
	 * @param kuid �����˵ı��
	 * @return ��������
	 * @author DATIAN
	 */
//	List findByKuid(Long kuid);
	
	
	/**
	 * ���ݷ����˵ı�źͽ�ɫ��źͼ���ӽ׶α������ѯ��������
	 * @param krid �����˵Ľ�ɫ���
	 * @param kuid �����˵ı��
	 * @param bcpid ����ӽ׶α��
	 * @param ifck �Ƿ���й��ۺϼ��
	 * @return ��������
	 * @author DATIAN
	 */
	List findByKridAndKuidAndBcpidAndIfck(Long krid,Long kuid,Long bcpid,Short ifck);
	
	/**
	 * ���ݽ�ɫ��źͱ��赥λ��źͷ����˵ı�ź�״̬λ����ѯ��������
	 * @param kuid �����˵ı��
	 * @param krid �����˵Ľ�ɫ���
	 * @param buid ���赥λ���
	 * @param ifck �ж�״̬
	 * @return ��������
	 * @author DATIAN
	 */
	List findByKridAndBuidAndKuidAndIfck(Long krid,Long buid,Long kuid,Long ifck);
	
	
	/**
	 * ���ݵ������Ų������鵥����������
	 * @param bcsid �������� 
	 * @return ��鷴�������б�
	 * @author DATIAN
	 */
	List findbyBcsidFromCS(Long bscid);
	
	
	
	/**
	 * ���ݵ������Ų������鷴������
	 * @param bcsid �������� 
	 * @return ��鷴�������б�
	 * @author DATIAN
	 */
	List findByBcsid(Long bcsid);


	


	/**
	 * ���ݵ������źͽ�ɫ��Ų������鷴������
	 * @param bcsid ��������
	 * @param krid  ��ɫ���
	 * @return ��鷴������
	 * @author DATIAN
	 */
	List findByBcsidAndKrid(Long bcsid,Long krid);


	/**
	 * ���ݽ�ɫ��ź�ѧ����Ų���ĳ��ѧ���ļ�鷴��
	 * @param krid ��ɫ���
	 * @param bsid  ѧ�����
	 * @return ��鷴������
	 * @author DATIAN
	 */
	List findByKridAndBsid(Long krid,Long bsid);


	/**
	 * ���ݽ�ɫ��ź�ѧ����Ų���ĳ��ѧ���ļ�鷴��
	 * @param bsid  ��ʦ���
	 * @param krid ��ɫ���
	 * @return ��鷴������
	 * @author DATIAN
	 */
	List findByBtidAndKrid(Long btid,Long krid);



	/**
	 * ���ݽ�ɫ��źͱ��赥λ��Ų���ĳ���쵼�ļ�鷴��
	 * @param krid ��ɫ���
	 * @param buid ���赥λ���
	 * @return ��鷴������
	 * @author DATIAN
	 */
	List findByKridAndBuid(Long krid,Long buid);


	
	/**
	 * ���ݲ��ű�ź��û���źͱ�����̱�Ų���ĳ���������ۺϷ���
	 * @param kdid ���ű��
	 * @param kuid �û����
	 * @param bgid ������̱��
	 * @return �ۺϷ�������
	 * @author DATIAN
	 */
	List findByKdidAndKuidAndBgidAndKrid(Long kdid,Long kuid,Long bgid,Long krid);



	/**
	 * �����û���źͱ�����̱�źͽ�ɫ��Ų���ĳ���������ۺϷ���
	 * @param kuid �û����
	 * @param bgid ������̱��
	 * @param krid �û���ɫ���
	 * @return �ۺϷ�������
	 * @author DATIAN
	 */
	List findByKuidAndBgidAndKrid(Long kuid,Long bgid,Long krid);



	/**
	 * ���ݲ��ű�ź��û���źͱ�����̱�Ų���ĳ���������ۺϷ���
	 * @param kuid �û����
	 * @param krid �û���ɫ���
	 * @param bgid ������̱��
	 * @return �ۺϷ�������
	 * @author DATIAN
	 */
	List findByKdidAndKridAndBgid(Long kdid,Long krid,Long bgid);



	/**
	 * �����û���Ų����为�𶽵���ѧԺ
	 * @param kuid �û����
	 * @param kdid ѧУ���
	 * @return �������ѧԺ
	 * @author DATIAN
	 */
	List findKdidByKuidAndKdid(Long kuid ,Long kdid);
	


	/**
	 * �����û���źͽ�ɫ��������ҵ��������
	 * @param kuid �û����
	 * @param krid ��ɫ���
	 * @return ���������
	 * @author DATIAN
	 */
	Long countByKuidAndKrid(Long kuid,Long krid);



	/**
	 * ����ѧԺ��Ų��Ҹ��𶽵��Ķ���
	 * @param kuid �û����
	 * @return ������Ա�б�
	 * @author DATIAN
	 */
	List findByKdid(Long kdid);



	/**
	 * ���ݱ��赥λ��ź��û���ɫ��������ҵ��������
	 * @param buid ���赥λ���
	 * @param krid ��ɫ���
	 * @return ���������
	 * @author DATIAN
	 */
	Long countByBuidAndKrid(Long buid,Long krid);



	/**
	 * ����ѧԺ��źͽ�ɫ����������ۺϼ�����
	 * @param kdid ���赥λ���
	 * @param krid ��ɫ���
	 * @param bgid ������̱��
	 * @return �ۺϼ�����
	 * @author DATIAN
	 */
	Long countByKdidAndKridAndBgid(Long kdid,Long krid,Long bgid);



	/**
	 * �����û���źͽ�ɫ��ź��������ۺϼ�����
	 * @param kuid �û����
	 * @param krid ��ɫ���
	 * @return �ۺϼ�����
	 * @author DATIAN
	 */
	Long countComByKuidAndKrid(Long kuid,Long krid);

	/**
	 * �����ӽ׶α�ź�ѧ����Ų��ҵ�������
	 * @param bcpid �ӽ׶α��
	 * @param bsid ѧ�����
	 * @return ��������
	 * @author DATIAN
	 */



   List findByBcpidAndBsid(Long bcpid,Long bsid);


	/**
	 * �����û���źͽ�ɫ��ź������ҵ�������
	 * @param kuid �û����
	 * @param krid ��ɫ���
	 * @return ������
	 * @author DATIAN
	 */
	List findByKuidAndKrid(Long kuId, Long krId);


	/**
	 * �����ۺϼ�����������ۺϼ��׶���Ŀ
	 * @param bccid �ۺϼ����
	 * @return �ۺϼ��׶���Ŀ
	 * @author DATIAN
	 */
	List findByBccid(Long bccid);



	/**
	 * �����û���ź��û���ɫ��źͱ��赥λ��������ҵ��������
	 * @param kuid �û����
	 * @param krid ��ɫ���
	 * @param kdid ���赥λ�б�
	 * @return ���������
	 * @author DATIAN
	 */
	List findByKuidAndKridAndKdid(Long kuId, Long krid, List deplist);



	/**
	 * �����û���ź��û���ɫ��źͱ�����̱�źͱ��赥λ�б��������ۺϼ�����
	 * @param kuid �û����
	 * @param krid ��ɫ���
	 * @param kdid ���赥λ�б�
	 * @return �ۺϼ�����
	 * @author DATIAN
	 */
	List findComByKuidAndKridAndKdidAndBgid(Long kuId, Long krid,
			List deplist,Long bgid);



}
