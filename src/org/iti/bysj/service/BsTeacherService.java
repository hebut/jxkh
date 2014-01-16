package org.iti.bysj.service;

import java.util.List;

import org.iti.bysj.entity.BsTeacher;
import com.uniwin.basehs.service.BaseService;

/**
 * @author FengXinhong
 * 2010-07-08
 */
public interface BsTeacherService extends BaseService {
	/**
	 * <li>��������:���ݱ��赥λ��Ų�ѯ�ñ��赥λ������ָ����ʦ 
	 * @param buid ���赥λ���
	 * @return ָ����ʦ�б�
	 * @author FengXinhong
     * 2010-07-08
	 */

	List findByBuid(Long buid);
	/**
	 * <li>��������:����ͬ�����ı�š�ѧ����Ų��Ҹ�ѧ������ķ�ͬ�����Ľ�ʦ
	 * @param Kuid ͬ�������˱��
	 * @param bsid ѧ�����
	 * @return ��ʦ�б�
	 * @author FengXinhong
	 * 2010-07-08
	 */
	List findByKuidAndBsid(Long Kuid,Long bsid);
//	/**
//	 * <li>��������:�������α�Ų���δ����ѧ���Ľ�ʦ
//	 * @param BbId ���α��
//	 * @return ��ʦ�б�
//	 * @author FengXinhong
//	 * 2010-07-08
//	 */
//	List findByBbId(Long BbId);
	
	/**
	 * <li>��������:�����û���š�������̱�Ų���ָ����ʦ
	 * @param kuid �û����
	 * @param gpid ������̱��
	 * @return ָ����ʦ�б�
	 * @author FengXinhong
	 * 2010-07-08
	 */
	public List findByKuidAndGpid(Long kuid,Long gpid);
	/**
	 * <li>��������������ְ�Ƽ���ͱ��赥λ��ѯ��ʦ�б�
	 * @param title ְ�Ƽ���
	 * @param buid ���赥λ
	 * @return ��ʦ�б�
	 * @author FengXinhong
	 * 2010-07-08
	 */
	List findByTitleAndBuid(int title,Long buid);
	/**
	 *  <li>�������������ݴ������ţ����Ҹ����ʦ
	 * @param brpid ��������
	 * @return ��ʦ�б�
	 * @author FengXinhong
	 */

	List findByBrpid (Long brpid);
	
	/**
	 * ��ѯ�����ʦ����
	 * @param buid ���赥λ
	 * @param name ��ʦ����
	 * @param tno  ��ʦ��
	 * @param kdid ���ű��
	 * @return
	 */
	List findByBuidAndNameAndTno(Long buid,String name,String tno,Long kdid);
	/**
	 * ��ѯ�����ʦ����
	 * @param btid ��ʦ���
	 * @return
	 * ZM
	 */
	BsTeacher findBybtid(Long btId);
	/**
	 * �����û���źͱ��赥λ���������ָ����ʦ
	 * @param kuid �û����
	 * @param buid ���赥λ���
	 * @return BsTeacher
	 * zx
	 */
	BsTeacher findByKuidAndBuid(long kuid,long buid);
	
	/**
	 * �����û���Ų�ѯ�����ʦ���Դ����ж�
	 * @param kuid �û����
	 * @param kdid ѧУ���
	 * @return
	 */
	public List findBsTeacher(Long kuid,Long kdid);
	/**
	 * ���ݱ�����̱���Լ����ű�Ų��ұ����ʦ��kuid
	 * @param bgid ������̱��
	 * @param kdpid �ϼ����ű��
	 * @return
	 * lly
	 */
	List findKuidByBgidAndkdpid(Long bgid,Long kdpid);
	/**
	 * ���ݱ�����̱���Լ����ű�Ų��ұ����ʦ
	 * @param bgid
	 * @param kdpid
	 * @param kuid
	 * @return
	 * lly 
	 */
	List findBybgidandkdidandkuid(Long bgid,Long kdpid,Long kuid);
	/**
	 * 
	 * @param kuid
	 * @return
	 */
	public List findByKuid(Long kuid);
}
