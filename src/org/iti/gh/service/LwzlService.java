package org.iti.gh.service;

import java.util.List;

import com.uniwin.basehs.service.BaseService;

public interface LwzlService extends BaseService {

	/**
	 * �����û��ı�������������е��ѻ�ÿ������ġ������������������ġ��̲�
	 * @param kuId
	 * @return
	 */
	List findByKuid(Long kuId,Short lx);
	/**
	 * ���ݵ�λ���ű�Ų��Ҹõ�λ���Ž�ʦ���л��߽������
	 * @param kdid
	 * @return
	 */
	public List findByKdId(long kdid,Short lx);
	/**
	 * ���ݵ�λ���ű�Ų��Ҹõ�λ���Ž�ʦ���л��߽��������ͬ����Ŀ�ϲ�
	 * @param kdid
	 * @return
	 */
	public List findSumKdId(long kdid,Short lx, Short type,Short state);
	/**
	 * �������Ʋ��Ҹõ�λ���Ž�ʦ���л��߽������
	 * @param kdid
	 * @return
	 */
	public List findByMc(String name,Short lx);


	/**
	 * �����û��ı�������������е��ѻ�ÿ������ġ������������������ġ��̲�
	 * @param kuId
	 * @return
	 */
	List findByKuidAndType(Long kuId,Short lx,Short type,Short jslwtyype);
	
/**
 * �����û��ı�š��û����ƣ��������ࣨ1�������� 2 ����ר�� 3 ��ѧ���� 4 �̲ģ����������ʣ�0-�ڿ����ģ�1-�������ģ�������ʦ�������ͣ�1���л��飬
 * 2�����ڿ���3 ���л��飬4�����ڿ���
 * @param kuId
 * @param kuname
 * @param lx
 * @param type
 * @param jslwtyype
 * @return
 */
	List findAllname(Long kuId,String kuname,Short lx,Short type,Short jslwtyype);
	
	/**
	 * <li>��������������ѧԺid���������ͣ����кͿ��У������״̬��ѯ����
	 * @param kdid
	 * @param lx
	 * @param state
	 * @return
	 */
	public List findByKdidAndLxAndState(Long kdid,Short lx,Short type,Short state);
}
