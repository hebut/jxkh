package org.iti.gh.service;

 

import java.util.List;

import org.iti.gh.entity.GhJszz;

import com.uniwin.basehs.service.BaseService;

public interface JszzService extends BaseService {

	/**
	 * <li>����������������Ŀid���û�id���������ࣨ��������ģ��ڿ����ĵȣ�
	 * @param kuid
	 * @param lwid
	 * @param type
	 * @return
	 */
	public GhJszz findByKuidAndLwidAndType(Long kuid,Long zzid,Short type);
	
	/**
	 * 
	 * @param zzid
	 * @param type
	 * @return
	 */
	public List<Long> findByLwidAndType(Long zzid,Short type);
	
	/**
	 * <li>���������������û�ID���������ࡢ�������������ѯ���������ļ�¼������
	 * @param kuid �û�ID
	 * @param type ��������
	 * @param selfs �����������
	 * @return
	 */
	public List<Long> findCountByKuidAndType(Long kuid,Short type, String selfs);

	/**
	 * <li>�������������������������ͳ��ָ���û�����д����������
	 * @param kuid �û�ID
	 * @param type ��������
	 * @return
	 */
	public int getWordsByKuidType(Long kuid,Short type);
}
