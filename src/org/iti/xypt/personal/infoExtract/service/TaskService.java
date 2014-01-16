package org.iti.xypt.personal.infoExtract.service;



import java.util.List;

import org.iti.xypt.personal.infoExtract.entity.WkTExtractask;
import org.iti.xypt.personal.infoExtract.entity.WkTTasktype;

import com.uniwin.basehs.service.BaseService;
import com.uniwin.framework.entity.WkTUser;




public interface TaskService extends BaseService
{
	/**
	 * @param ptid ������ID
	 * @return  ����������
	 */
	public List getChildType(Long ktid);
	/**
	 * <li>��þ��й���/���Ȩ�޵������б�
	 * @param user
	 * @param deptList
	 * @param titleList
	 * @return �����б�
	 */
	public List getTaskOfManage(WkTUser user,List deptList,List titleList);
	public List getTaskOfAuditManage(WkTUser user,List deptList,List rlist);
	public List getTaskOfAllManage();
	/**
	 * ����ID
	 * @return �����µ������б�
	 */
	public List getChildTask(Long ktaid);
	/**
	 * 
	 * @param pid ��ID
	 * @param taid ����ID
	 * @return  ���˸÷�������з���
	 */
	public List getChildTasktype(Long pid,Long taid);
	/**
	 * 
	 * @param ktaid  ����ID
	 * @return ��Ӧ�ķ���
	 */
	public WkTTasktype getTpyeById(Long ktaid);
	/**
	 * 
	 * @param ktaid ����ID
	 * @return  ����Ȩ��
	 */
	public  List getTypeAuth(Long ktaid);
	/**
	 * 
	 * @param ktaid ����ID
	 * @return �����µ�����
	 */
	public List  getTaskByKtaid(Long ktaid);
	/**
	 * 
	 * @param keid ����ID
	 * @return ��ȡ�����µ���Ϣ
	 */
	public List getInfoBykeid(Long keid);
	public List getUserSort(Long kusid);
	/**
	 * 
	 * @param keid ����ID
	 * @return  ������Ϣ
	 */
	public List getTaskBykeId(Long keid);
	
	//��ȡ�½�service
	public List findAllTask();
	public List findByFolderId(Long folderId);
	public WkTTasktype findByFolderID(Long id);
	public WkTExtractask findById(Long id);
}