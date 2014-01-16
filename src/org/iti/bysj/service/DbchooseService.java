package org.iti.bysj.service;

import java.util.List;

import org.iti.bysj.entity.BsDbchoose;

import com.uniwin.basehs.service.BaseService;

public interface DbchooseService extends BaseService {

	/**
	 * <li>��������:����������ѯ˫ѡ��¼
	 * 
	 * @param bbid
	 *            ���α��
	 * @return ˫ѡ��¼�б�
	 */
	BsDbchoose findByBdbid(long bdbid);

	/**
	 * <li>��������:���ݱ��赥λ���Ƿ���ܲ�ѯ˫ѡ��¼
	 * 
	 * @param buid
	 *            ���赥λ���
	 * @param ifaccept
	 *            �Ƿ����
	 * @return ˫ѡ��¼�б�
	 */
	List findByBuidAndIfaccept(long buid, short ifaccept);

	/**
	 * <li>��������:�������α�ţ��Ƿ���ܲ�ѯ˫ѡ��¼
	 * 
	 * @param bbid
	 *            ���α��
	 * @param ifaccept
	 *            �Ƿ����
	 * @return ˫ѡ��¼�б�
	 * 
	 */
	List findByBuidAndIfacceptks(long buid, short ifaccept);
	/**
	 * <li>��������:�������α�ţ��Ƿ���ܲ�ѯ˫ѡ��¼(�ɿ��ٲ���)
	 * 
	 * @param bbid
	 *            ���α��
	 * @param ifaccept
	 *            �Ƿ����
	 * @return ˫ѡ��¼�б�
	 * 
	 */
	List findByBbidAndIfaccept(long bbid, short ifaccept);

	// /**
	// * <li>��������:���ݽ�ʦ��ţ���ѯѧ���б�
	// * @param btid ��ʦ���
	// * @return
	// */
	// List findByBtid(long btid);

	/**
	 * <li>��������:���ݿ����� ���ҿ����Ƿ��б�ѡѧ��
	 * 
	 * @param bprid
	 *            ������
	 * @return
	 */
	List findByBprid(long bprid);

	/**
	 * <li>��������:���ݽ�ʦ��ţ��Ƿ���ܣ���ѯѧ���б�
	 * 
	 * @param btid
	 *            ��ʦ���
	 * @param ifaccept
	 *            �Ƿ����
	 * @return
	 */
	List findByBtidAndIfaccept(long btid, short ifaccept);

	/**
	 * <li>�������������ݽ�ʦ��ţ����α�ţ��Ƿ���ܲ�ѯѧ���б�
	 * 
	 * @param btid
	 *            ��ʦ���
	 * @param ifaccept
	 *            �Ƿ����
	 * @param bbid
	 *            ���α��
	 * @return
	 */
	List findByBtidAndBbidAndIfaccept(long btid, long bbid, short ifaccept);

	/**
	 * <li>��������:���ݿ����� �Ƿ���ܲ��ҿ����Ƿ��б�ѡѧ��
	 * 
	 * @param bprid
	 *            ������
	 * @param ifaccept
	 * @return
	 */
	List findByBpridAndIfaccept(long bprid, short ifaccept);

	/**
	 * <li>��������:����ѧ����� ����ѧ���Ƿ��б�ѡ����
	 * 
	 * @param bsid
	 *            ѧ�����
	 * @return
	 */
	List findByBsid(long bsid);

	/**
	 *<li>��������: ����ѧ����ţ��Ƿ���� ����ѧ���Ƿ��б�ѡ����
	 * 
	 * @param bsid
	 *            ѧ�����
	 * @param ifaccept
	 *            �Ƿ����
	 * @return
	 */
	List findByBsidAndIfaccept(long bsid, short ifaccept);

	/**
	 * <li>���������������û���ţ���λ��� ����˫��ָ��
	 * 
	 * @param buid��λ���
	 * @param kuid�û����
	 * @param ifaccept�Ƿ����
	 * @return
	 */
	//List findByBuidAndKunameAndIfaccept(long buid, String kuname, short ifaccept);

	/**
	 * <li>��������������ָ����ʦ��Ų���˫��ָ���б�
	 * 
	 * @param btidָ����ʦ���
	 * @return ˫��ָ���б�
	 * @autor lys
	 */
	List findByBtid(long btid);

	/**
	 * <li>�������������ݱ��赥λ��Ų���˫��ָ���б�
	 * 
	 * @param buid���赥λ���
	 * @return ˫��ָ���б�
	 * @autor lys
	 */
	List findByBuid(long buid);

	/**
	 * <li>�����������������α�Ų���˫��ָ���б�
	 * 
	 * @param bbid���α��
	 * @return ˫��ָ���б�
	 * @autor lys
	 */
	List findByBbid(long bbid);

	/**
	 * <li>�����������������α�źͽ�ʦ��Ų���˫��ָ���б�
	 * 
	 * @param bbid���α��
	 * @param btid��ʦ���
	 * @return ˫��ָ���б�
	 * @author DATIAN
	 */
	List findByBbidAndBtid(long bbid, long btid);

	/**
	 * <li>�������������ݱ��赥λ��ź��ӽ׶α�ź��ĵ�״̬����ѯû���ύ�ĵ������ĵ�����ͨ����˫���ϵ�б�
	 * 
	 * @param buid���赥λ���
	 * @param bcpid�ӽ׶α��
	 * @param ifstate
	 *            �ĵ�״̬
	 * @return ˫��ָ���б�
	 * @author lys
	 */
	List findByIfacceptAndBcpidAndStateInPcontrol(short ifaccept, Long bcpid, short state);

	/**
	 * <li>�������������ݱ��赥λ��ź��ӽ׶α�ź��ĵ�״̬����ѯû���ύ�ĵ������ĵ�����ͨ����˫���ϵ�б�
	 * 
	 * @param buid���赥λ���
	 * @param bcpid�ӽ׶α��
	 * @param ifstate
	 *            �ĵ�״̬
	 * @return ˫��ָ���б�
	 * @author lys
	 */
	List findByIfacceptAndBcpidAndStateAndIfcqInPcontrol(short ifaccept, Long bcpid, short state, short ifcq);

	/**
	 * <li>�������������ݱ��赥λ��ź��ӽ׶α�ź��ĵ�״̬����ѯû���ύ�ĵ������ĵ���δͨ����˫���ϵ�б�
	 * 
	 * @param buid���赥λ���
	 * @param bcpid�ӽ׶α��
	 * @param ifstate
	 *            �ĵ�״̬
	 * @return ˫��ָ���б�
	 * @author lys
	 */
	List findByBuidAndIfacceptAndBcpidAndStateNotInPcontrol(Long buid, short ifaccept, Long bcpid, short state);

	/**
	 * <li>�������������ݱ��赥λ��ź��ӽ׶α�ź��ĵ�״̬����ѯû���ύ�ĵ������ĵ���δͨ����˫���ϵ�б�
	 * 
	 * @param buid���赥λ���
	 * @param bcpid�ӽ׶α��
	 * @param ifstate
	 *            �ĵ�״̬
	 * @return ˫��ָ���б�
	 * @author lys
	 */
	List findByBbidAndIfacceptAndBcpidAndStateNotInPcontrol(Long buid, short ifaccept, Long bcpid, short state);

	/**
	 * <li>�������������ݱ��赥λ��ź��ӽ׶α�ź��ĵ�״̬����ѯû���ύ�ĵ������ĵ�����ͨ����˫���ϵ�б�
	 * 
	 * @param buid���赥λ���
	 * @param bcpid�ӽ׶α��
	 * @param ifstate
	 *            �ĵ�״̬
	 * @return ˫��ָ���б�
	 * @author lys
	 */
	List findByIfacceptAndBcpidInPcontrol(short ifaccept, Long bcpid);

	/**
	 * <li>�������������ݱ��赥λ��ź��ӽ׶α�ź��ĵ�״̬����ѯû���ύ�ĵ������ĵ�����ͨ����˫���ϵ�б�
	 * 
	 * @param buid���赥λ���
	 * @param bcpid�ӽ׶α��
	 * @param ifstate
	 *            �ĵ�״̬
	 * @return ˫��ָ���б�
	 * @author lys
	 */
	List findByIfacceptAndBcpidAndIfcqInPcontrol(short ifaccept, Long bcpid, short ifcq);

	/**
	 * <li>�������������ݱ��赥λ��ź��ӽ׶α�ţ���ѯû���ύ�ĵ���˫���ϵ�б�
	 * 
	 * @param buid���赥λ���
	 * @param bcpid�ӽ׶α��
	 * @param ifstate
	 *            �ĵ�״̬
	 * @return ˫��ָ���б�
	 * @author lys
	 */
	List findByBuidAndIfacceptAndBcpidNotInPcontrol(Long buid, short ifaccept, Long bcpid);

	/**
	 * <li>�������������ݱ��赥λ��ź��ӽ׶α�ţ���ѯû���ύ�ĵ���˫���ϵ�б�
	 * 
	 * @param buid���赥λ���
	 * @param bcpid�ӽ׶α��
	 * @param ifstate
	 *            �ĵ�״̬
	 * @return ˫��ָ���б�
	 * @author lys
	 */
	List findByBbidAndIfacceptAndBcpidNotInPcontrol(Long buid, short ifaccept, Long bcpid);
	/**
	 * <li>�������������ݱ��赥λ���,�Ƿ���ܺͿ������Ͳ���˫���ϵ�б�
	 * 
	 * @param buid���赥λ���
	 * @param ifaccept�Ƿ����
	 * @param bprtype
	 *            ��������
	 * @return ˫��ָ���б�
	 * @author lys
	 */
	List findByBuidAndIfacceptAndBprtype(Long buid, short ifaccept, short bprtype);

	/**
	 * ���ҵ�ǰ��λ�Ĳμ�һ���ѧ������
	 * 
	 * @param buid
	 * @return
	 * @author zhangxue
	 */
	List findOnebatchByBuid(Long buid);

	/**
	 * ���Ҳ��ܲμӶ����ѧ������������δ���ѧ��
	 * 
	 * @param buid
	 *            ��絥λ���
	 * @return
	 */
	List findNotDb2ByBuid(Long buid);

	/**
	 * ���ݱ��赥λ��ź��Ƿ���ܲ����Ѿ��н�ʦ���ĳɼ���˫ѡ��ϵ�б�
	 * 
	 * @param buid
	 *            ���赥λ���
	 * @return
	 * @author lys
	 */
	List findByBuidAndIfacceptHaveTScore(Long buid, short ifaccept);

	/**
	 * ���ݱ��赥λ��ź��Ƿ���ܲ����Ѿ���ͬ�����ĳɼ���˫ѡ��ϵ�б�
	 * 
	 * @param buid
	 *            ���赥λ���
	 * @return
	 * @author lys
	 */
	List findByBuidAndIfacceptHaveReview(Long buid, short ifaccept);

	/**
	 * ���ݱ��赥λ��ź��Ƿ���ܲ����Ѿ��д��ɼ���˫ѡ��ϵ�б�
	 * 
	 * @param buid
	 *            ���赥λ���
	 * @return
	 * @author lys
	 */
	List findByBuidAndIfacceptHaveReplyOrTworeply(Long buid, short ifaccept);

	/**
	 * ������Ŀ������ͬ��ѧ���б�
	 */
	List findSameNature(Long buid, Long bnsid);

	/**
	 * <li>��������:���ݽ�ʦ��ţ��Ƿ���ܣ��Ƿ�ͨ��һ�磬��ѯѧ���б�
	 * 
	 * @param btid
	 *            ��ʦ���
	 * @param ifaccept
	 *            �Ƿ����
	 * @return
	 */
	List findByBtidAndIfacceptAndIfpass(long btid, short ifaccept, short ispassone);

	List findBybbid(long bbid);
	/**
	 * <li>��������������ѧ����ţ���������λ��� ����ѧ������
	 * 
	 * @param buid��λ���
	 * @param bsid ѧ�����
	 * @param name ѧ������
	 * @param ifaccept�Ƿ����
	 * @return
	 */
	
	List findByBuidAndIfacceptAndStidOrName(long buid, short ifaccept,String stid, String name);
	/**
	 * ���ݱ��赥λ��Ų�������һ���ʸ���δ�д��ɼ���ѧ��
	 * 
	 * @return
	 */
	List findBuIdNotDbcj(Long buid);
	/**
	 * ���ݱ��赥λ��Ų������ж����ʸ���δ�д��ɼ���ѧ��
	 * 
	 * @return
	 */
	List findBuIdNotTwoDbcj(Long buid);

	
}
