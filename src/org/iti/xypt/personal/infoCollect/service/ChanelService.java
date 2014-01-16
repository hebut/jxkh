package org.iti.xypt.personal.infoCollect.service;

import java.util.List;

import org.iti.xypt.personal.infoCollect.entity.WkTChanel;

import com.uniwin.basehs.service.BaseService;
import com.uniwin.framework.entity.WkTUser;




public interface ChanelService extends BaseService {
	/**
	 * 
	 * @param ptid  ����ĿID
	 * @return  ��������Ŀ
	 */
	public List getChildChanel(Long ptid);
	/**
	 * 
	 * @param chanelname  ��Ŀ����
	 * @return  �����ƶ�Ӧ����Ŀ
	 */
	public List findByChanelname(String chanelname);
	/**
	 * 
	 * @param pid ����ĿID
	 * @return  ĳ��Ŀ�ĸ���Ŀ
	 */
	public List findByKcPid(Long pid);
	/**
	 * 
	 * @param pcid ����ĿID
	 * @param cid  ��ĿID
	 * @return  ��������Ŀ�������������Ŀ
	 */
	public List getChildChanel(Long pcid,Long cid);
	/**
	 *
	 * <li>��þ��й���Ȩ�޵���Ŀ�б�
	 * @author FengXinhong
	 * @param user
	 * @param deptList
	 * @param titleList
	 * @return ��Ŀ�б�
	 */
	public List getChanelsOfUserManage(WkTUser user,List deptList,List titleList);
	public List getChanelsOfUserAccess(WkTUser user,Long kcpid);
	/**
	 * 
	 * @param kcid  ��ĿID
	 * @return  ��ID��Ӧ����Ŀ
	 */
	public WkTChanel findBykcid(Long kcid);
	public List getChanelByKwid(Long kwid);
	/**
	 * 
	 * @param pid  ����ĿID
	 * @param kwid  վ��ID
	 * @return  ��վ���¶�Ӧ��Ŀ����������Ŀ
	 */
	public List getChanelByKwid(Long pid,Long kwid);
	/**
	 * 
	 * @param kcid ��ĿID
	 * @return ������Ŀ�б�
	 */
	public List getSiteChanel(Long kcid);
	/**
	 * 
	 * @param kcid  ��ĿID
	 * @return ��������Ŀ�б�
	 */
	public List getSite(Long kcid);
	/**
	 * 
	 * @return  ����Ŀ�����ڵ���Ŀ
	 */
	public List getNoPcid();
	/**
	 * 
	 * @param kwid վ��ID
	 * @return ��վ���µĶ�����Ŀ
	 */
	public List getChanel(Long kwid);
	/**
	 * 
	 * @param kiid ��ϢID
	 * @return ������Ŀ
	 */
	public List getSchanel(Long kiid);
	/**
	 * 
	 * @param cid ��ĿID
	 * @return ������Ŀ
	 */
	public List getSiteCha(Long cid);
}
