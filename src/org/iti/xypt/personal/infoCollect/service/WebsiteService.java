package org.iti.xypt.personal.infoCollect.service;


import java.util.List;

import org.iti.xypt.personal.infoCollect.entity.WkTTotal;
import org.iti.xypt.personal.infoCollect.entity.WkTWebsite;

import com.uniwin.basehs.service.BaseService;
import com.uniwin.framework.entity.WkTSite;
import com.uniwin.framework.entity.WkTUser;





public interface WebsiteService extends BaseService {
	
	/**
	 * 
	 * @param ptid ��վ��ID
	 * @return  ������վ��
	 */
	public List getChildWebsite(Long ptid);
	public List getChildUsersort(Long ptid);
	public List getChildWebsite(Long ptid,Long did);
/**
 * 
 * @param kwid  վ��ID
 * @return  վ��
 */
	public WkTWebsite findBykwid(Long kwid);
	public WkTSite findByKsid(Long ksid) ;
	/**
	 * ��ȡվ���Ȩ��
	 */
	public List findAuthOfWebsite(Long kwid);
	/**
	 * 
	 * @param pid  ��վ��ID
	 * @return  ��վ����Ϣ
	 */
	public List findByKwPid(Long pid);
	/**
	 * <li>��þ��й���Ȩ�޵�վ���б�
	 * @param user
	 * @param deptList
	 * @param titleList
	 * @return վ���б�
	 * 2010-7-20
	 */
	public List getWebsiteOfUserManage(WkTUser user,List deptList,List titleList);
	public List getWebsiteOfUserManage(WkTUser user,List deptList);
	public List getWebsiteOfManage(WkTUser user,List deptList,List rlist);
	/**
	 * @param kwid վ��ID
	 * @return վ��Ȩ��
	 */
	public List getAuth(Long kwid);
	/**
	 * 
	 * @param kuid �û�ID
	 * @return �û���ɫ
	 */
	public List getRole(Long kuid);
	/**
	 * 
	 * @param kwid  վ��ID
	 * @return վ�����ͳ�����
	 */
	public WkTTotal getTotal(Long kwid);
	public List getChildNum(Long kusid);
}
