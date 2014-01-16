package org.iti.bysj.service;

import java.util.List;

import org.iti.bysj.entity.BsStudent;

import com.uniwin.basehs.service.BaseService;

public interface BsStudentService extends BaseService {

	/**
	 * <li>�������������ݱ��赥λ��Ų�ѯ�ñ��赥λ�����б���ѧ��
	 * 
	 * @param buid
	 *            ���赥λ���
	 * @return ����ѧ���б�
	 */
	public List findByBuid(Long buid);

	/**
	 * <li>�������������ݱ��赥λ��š�һ���ʸ�����״̬��ѯѧ���б�
	 * 
	 * @param buid
	 *            ���赥λ���
	 * @param ispassone
	 *            һ���ʸ�����״̬
	 * @return ����ѧ���б�
	 */
	public List findByBuidAndBstispassone(Long buid, short ispassone);

	/**
	 * <li>�������������ݱ��赥λ��š������ʸ�����״̬��ѯѧ���б�
	 * 
	 * @param buid
	 *            ���赥λ���
	 * @param ispassone
	 *            �����ʸ�����״̬
	 * @return ����ѧ���б�
	 */
	public List findByBuidAndBstispasstwo(Long buid, short ispasstwo);

	/**
	 * <li>��������������ĳ��ѧ���û���źͱ��赥λ��Ų�ѯѧ��
	 * 
	 * @param bsid
	 *            ѧ���û����
	 * @param buid
	 * @return
	 */
	public BsStudent findByKuidAndBuid(Long kuid, Long buid);
	/**
	 * 
	 * @param kuid
	 * @param bbid
	 * @return
	 */
	public BsStudent findByKuidAndBbid(Long kuid,Long bbid);

	// /**
	// * <li>�������������ݱ���ѧ����ţ��������޷����ѧ��
	// *
	// * @param bsid
	// * @return ����ѧ���б�
	// */
	// public List findByBsidNotInBssgroup(Long bsid);

	/**
	 * <li>�������������ݱ���ѧ����š������ʸ�����״̬��ѯѧ���б�
	 * 
	 * @param bsid
	 *            ����ѧ�����
	 * @param ispasstwo
	 *            �����ʸ�����״̬
	 * @return ѧ���б�
	 */
	public List findByBsidAndBstispasstwo(Long bsid, short ispasstwo);

	/**
	 * <li>�����������������α�Ų���ѧ��
	 * 
	 * @param BbId
	 *            ���α��
	 * @return ѧ���б�
	 */
	public List findByBbId(Long BbId);

	/**
	 * <li>�������������ݱ��赥λ��Ų��ұ��赥λ��û��ѡ���ѧ��
	 * 
	 * @param buid
	 *            ���赥λ���
	 * @return ѧ���б�
	 */
	public List findBuIdNotInDbChoose(Long buid);

	/**
	 * <li>�����������������α�Ų��ұ��赥λ��û��ѡ���ѧ��
	 * 
	 * @param bbid
	 *            �������α��
	 * @return ѧ���б�
	 */
	public List findBbIdNotInDbChoose(Long bbid);

	// /**
	// * <li>�����������������α�Ų���ѧ����Ŀ
	// *
	// * @param bbid
	// * ���α��
	// * @return ѧ����Ŀ
	// */
	// public Long countByBBid(Long bbid);

	// /**
	// * <li>������������ѯ���赥λ��δ������ѧ��
	// *
	// * @param buid
	// * ���赥λ���
	// * @return ѧ���б�
	// */
	// public List findNotBatch(Long buid);

	/**
	 * <li>�������������ݴ������ţ����Ҹ�����ѧ��
	 * 
	 * @param brpid
	 * @return �÷������д��ѧ����Ա�б�
	 * @author FengXinhong 2010-07-08
	 */
	public List findByBrpid(Long brpid);

	/**
	 * ��ѯ����ѧ������
	 * 
	 * @param buid
	 *            ���赥λ
	 * @param name
	 *            ѧ������
	 * @param tno
	 *            ѧ��
	 * @param kdid
	 *            ���ű��
	 * @return
	 */
	List findByBuidAndNameAndstid(Long buid, String name, String tno);

	/**
	 * <li>��������������ѧ����ţ�����ѧ����¼��
	 * 
	 * @param bsid
	 * @return
	 */
	public BsStudent findByBsid(Long bsid);

	/**
	 * <li>�������������ұ��赥λ��û�з�����ѧ��
	 * 
	 * @param buid
	 * @return
	 */

	public List findNoBatchByBuId(Long buId);

	/**
	 * ���ݱ��赥λ��š���������ѧ�Ų�ѯ����˫���ϵ�еı���ѧ���б�\
	 * 
	 * @param buid
	 *            ���赥λ���
	 * @param name
	 *            ѧ������
	 * @param stid
	 *            ѧ��
	 * @return ����ѧ���б�
	 * @author lys
	 */
	List findByBuidAndNameAndStidNotInDbchoose(Long buid, String name, String stid);

	/**
	 * ���ݱ��赥λ��š���������ѧ�Ų�ѯ����˫���ϵ��,����ָ����ʦ�ı���ѧ���б�\
	 * 
	 * @param buid
	 *            ���赥λ���
	 * @param name
	 *            ѧ������
	 * @param stid
	 *            ѧ��
	 * @return ����ѧ���б�
	 * @author lys
	 */
	List findByBuidAndNameAndStidNotInDb(Long buid, String name, String stid);

	/**
	 * �������α�š���������ѧ�Ų�ѯ����˫���ϵ�У�����ָ����ʦ�ı���ѧ���б�\
	 * 
	 * @param bbid
	 *            ���赥λ���
	 * @param name
	 *            ѧ������
	 * @param stid
	 *            ѧ��
	 * @return ����ѧ���б�
	 * @author lys
	 */
	List findByBbidAndNameAndStidNotInDb(Long bbid, String name, String stid);

	/**
	 * �������α�š���������ѧ�Ų�ѯ����˫���ϵ�еı���ѧ���б�\
	 * 
	 * @param bbid
	 *            ���赥λ���
	 * @param name
	 *            ѧ������
	 * @param stid
	 *            ѧ��
	 * @return ����ѧ���б�
	 * @author lys
	 */
	List findByBbidAndNameAndStidNotInDbchoose(Long bbid, String name, String stid);

	/**
	 * ����ѧ�����Ƿ�����μ�һ�磬���Ҳ������ڱ�ļ�¼
	 * 
	 * @param buid
	 */
	public void updateStuPassone(long buid);

	/**
	 * ���ݱ��赥λ��š�����ʸ�������������Ҷ������޷����ѧ��
	 * 
	 * @return
	 */
	public List findBuIdNotInSgroup(Long buid, Short ispasstwo);

	/**
	 * ���ݱ��赥λ��Ų�������һ���ʸ���δ�д��ɼ���ѧ��
	 * 
	 * @return
	 */
	public List findBuIdNotDbcj(Long buid);

	/**
	 * ���ݱ��赥λ��Ų������ж����ʸ���δ�д��ɼ���ѧ��
	 * 
	 * @return
	 */
	public List findBuIdNotTwoDbcj(Long buid);

	/**
	 * ���ݱ��赥λ��Ų��Ҿ���һ���ʸ����д��ɼ���ѧ��
	 * 
	 * @return
	 */
	public List findBuIdDbcj(Long buid);

	/**
	 * ���ݱ��赥λ��Ų��ҽ��ж����ʸ����д��ɼ���ѧ��
	 * 
	 * @return
	 */
	public List findBuIdTwoDbcj(Long buid);

	/**
	 * ����ѧ�ź�������ѯѧ��
	 * 
	 * @return
	 */
	public List findBySidOrName(String sid, String name, Long buId);

	/**
	 * �����û���źͱ�����̱�Ų��ұ���ѧ��
	 * 
	 * @param kuid
	 * @return
	 */
	public List findByKuid(Long kuid);

	/**
	 * ����ѧ�š�������ѯѧ��
	 */
	public List findBySidorName(String sid, String name, Long buId, Short ispassone);

	/**
	 * ����ѧ�š�������ѯѧ��
	 */
	public List findBysidorname(String sid, String name, Long buId, Short ispassone);

	public List findBysidorname2(String sid, String name, Long buId, Short ispasstwo);
	public List findByBuidINPassoneOrInPasstwo(Long buId);
}
