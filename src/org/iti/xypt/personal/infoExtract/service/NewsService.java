package org.iti.xypt.personal.infoExtract.service;
/**
 * 2010-3-15
 * @author �����
 */

import java.util.List;
import com.uniwin.basehs.service.BaseService;
import org.iti.xypt.personal.infoExtract.entity.WkTExtractask;
import com.uniwin.framework.entity.WkTUser;
import org.iti.xypt.personal.infoExtract.entity.WkTChanel;
import org.iti.xypt.personal.infoExtract.entity.WkTDistribute;
import org.iti.xypt.personal.infoExtract.entity.WkTInfo;
import org.iti.xypt.personal.infoExtract.entity.WkTInfoscore;


public interface NewsService extends BaseService {
	
	/**
	 * <li>�������������������ѷ�������Ϣ��������ʱ�䵹�����С�
	 * @return
	 * List 
	 * @author Administrator
	 */
	public List getNewsOfAllChanelFB();
	
/**
 * 
 * @param ptid ��Ŀ��ID
 * @return ����Ŀ
 */
	public List getChildNews(Long ptid);
	public List getChildNews(Long ptid,Long kwid);
/**
 * 
 * @param ptid �û�Id
 * @return  ���û����й���Ȩ�޵���Ŀ�б�
 */
	public List getChildNewsown(Long ptid,Long did);
	/**
	 * 
	 * @param user �û�
	 * @param deptList  �����б�
	 * @return  ���û���Ȩ�޵���Ŀ�б�
	 */
	public List getChanelOfUserManage(WkTUser user,List deptList);
	public List getChanelOfManage(WkTUser user,List deptList,List rlist);
	public List getChanelOfAudit(WkTUser user,List deptList,List rlist);
	/**
	 * 
	 * @param ptid �û�Id
	 * @return ���û��������Ȩ�޵���Ŀ�б�
	 */
	public List getChildNewsaudit(Long ptid,Long did);
	/**
	 * @param qtid ��Ϣ��ID
	 * @return  ��ø���Ϣ���ĵ�����
	 * @author 
	 */
	public List getChildNewsContent(Long qtid);
/**
 * 
 * @param qtid ��ϢID
 * @return  ��Ϣ��������Ŀ
 */
	public WkTChanel getNewsOfChanel(Long qtid);
	/**
	 * 
	 * @param ktaid ����ID 
	 * @return  �����µ�������Ϣ
	 */
	public List getNewsOfinfo(Long ktaid);
	
/**
 * 
 * @param did ��ĿID
 * @return  ��Ӧ��Ŀ��ͬ״̬����Ϣ�б�׫�塢�˻ء��������ġ��ѷ�����
 */
	public List getNewsOfChanelZG(Long did,Long pid);
	public List getNewsOfChanelTH(Long did,Long pid);
	public List getNewsOfChanelSS(Long did,Long pid);
	public List getNewsOfChanelYY(Long did,Long pid);
	public List getNewsOfChanelFB(Long did,Long pid);
	public List getNewsOfChanelSS(Long did);
	public List getNewsOfChanelYY(Long did);
	public List getNewsOfChanelFB(Long did);
	public List getCountbyciid(Long kcid,Long status, String db, String db1);
	public List getSumByciid(Long kcid, String db, String db1);
	/**
	 * 
	 * @param did ��ĿID  �����ؼ���
	 * @param key ��ȡ���йؼ��ֵ���Ϣ�����б�
	 * @return
	 */
	public List getNewsOfChanelFB(Long did,Long pid, String key);
	public List getNewsOfChanelZG(Long did, Long pid,String key);
	public List getNewsOfChanelTH(Long did, Long pid,String key);
	public List getNewsOfChanelSS(Long did, Long pid,String key);
	public List getNewsOfChanelYY(Long did, Long pid,String key);
	public List getNewsOfChanelFB(Long did, String key);
	public List getNewsOfChanelSS(Long did,String key);
	public List getNewsOfChanelYY(Long did,String key);

	/**
	 * 
	 * @param pid  Ƶ��ID
	 * @return  Ƶ��״̬���Ƿ���Ҫ��ˣ�
	 */
	public WkTExtractask getChanelState(Long pid);
	/**
	 * 
	 * @param pid ��ϢID
	 * @return  ��Ϣ������¼
	 */
	public List getDistribute(Long pid);
	public List getDistribute(Long pid,Long did);
	public List getDistributeShare(Long pid);
	public WkTDistribute getWktDistribute(Long pid);
	/**
	 * 
	 * @param kbid ����ID
	 * @return ������¼
	 */
	public WkTDistribute getDistriBybid(Long kbid);
	/**
	 * 
	 * @param kiid ��ϢID
	 * @param kcid ��ĿID
	 * @return  ������¼
	 */
	public WkTDistribute getDistri(Long kiid,Long kcid);
	/**
	 * 
	 * @param pid ��ϢID
	 * @return  ��Ϣ���乲����Ϣ�б�
	 */
	public List getDistributeList(Long pid);
	/**
	 * 
	 * @param pid ��ϢID
	 * @return  ��Ϣ�����ݣ�ɾ����Ϣ��
	 */
	public List getInfocnt(Long pid);
	
	/**
	 * 
	 * @param did ��Ϣ��
	 * @return  �����Ϣ������
	 */
	public List getNewsOfcomment(Long did);
	/**
	 * 
	 * @param ��ϢID
	 * @return  �����Ϣ�Ĵ������
	 */
	public List getflog(Long did);
	/**
	 * 
	 * @param did ��ϢID
	 * @return  ��ȡ��Ӧ��Ϣ�ĸ����б�
	 */
	public List getAnnex(Long did);
	/**
	 * 
	 * @param info ��ȡ������Ϣ��Դ�б�
	 */
	public List getInfo();
	/**
	 * @param did ��ϢID
	 * @return �����Ϣ����������ֵ
	 */
	public WkTInfo getWkTInfo(Long did);
	/**
	 * 
	 * @param did ��ϢID
	 * @return ��Ϣ�Ĺ�����Ŀ
	 */
	public List getNewsOfShareChanel(Long did);
	public List getNewsOfShare(Long did);
	public List getNewsOfShareNew(Long did);
	/**
	 * 
	 * @param did  ��ϢID
	 * @return  ��Ϣ�����б�
	 */
	public List getFile(Long did);
	public List getOrifile(Long did);
	/**
	 * 
	 * @param kbid ��Ϣ����ID
	 * @return  ��Ϣ�����б�
	 */
	public List getSiteFile(Long kbid);
	/**
	 * <li>���������������û�id���ĵ��ķ���״̬�Լ�ʱ������ͳ��
	 * @param kuid
	 * @param status
	 * @param db
	 * @param db1
	 * @return List
	 * @author FengXinhong
	 * 2010-4-13
	 */
	public List getCountInfor(Long kuid,String status,String db,String db1);
	/**
	 * <li>���������������û���id��ʱ������ͳ����Ϣ
	 * @param kuid
	 * @param db
	 * @param db1
	 * @return List
	 * @author FengXinhong
	 * 2010-4-13
	 */
	public List getSumbyuid(Long kuid,String db,String db1);
	/**
	 * <li>��������������վ���id����Ϣ�ķ���״̬��ʱ������ͳ����Ϣ
	 * @param kwid
	 * @param status
	 * @param db
	 * @param db1
	 * @return List
	 * @author whm
	 * 2010-8-04
	 */
	public List getCountbywid(Long kwid,String status,String db,String db1);
	/**
	 * <li>����������������Ŀ��id����Ϣ�ķ���״̬��ʱ������ͳ����Ϣ
	 * @param kcid
	 * @param status
	 * @param db
	 * @param db1
	 * @return List
	 * @author whm
	 * 2010-8-04
	 */
	public List getCountbycid(Long kcid,String status,String db,String db1);
	/**
	 * <li>��������������վ���id����Ϣ�ķ���״̬��ʱ������ͳ����Ϣ
	 * @param kwid
	 * @param db
	 * @param db1
	 * @return List
	 * @author whm
	 * 2010-8-04
	 */
	public List getSumBywid(Long kwid,String db,String db1);
	/**
	 * <li>����������������Ŀ��id����Ϣ�ķ���״̬��ʱ������ͳ����Ϣ
	 * @param kcid
	 * @param db
	 * @param db1
	 * @return List
	 * @author whm
	 * 2010-8-04
	 */
	public List getSumBycid(Long kcid,String db,String db1);
	/**
	 * <li>�������������ݲ���id����Ϣ�ķ���״̬��ʱ������ͳ����Ϣ
	 * @param kdid
	 * @param rname
	 * @param status
	 * @param db
	 * @param db1
	 * @return List
	 * @author FengXinhong
	 * 2010-43-14
	 */
	public List getCountByDid(Long kdid,String rname,String status,String db,String db1);
	
	/**
	 * <li>�������������ݲ���id��ʱ������ͳ����Ϣ������
	 * @param kdid
	 * @param status
	 * @param db
	 * @param db1
	 * @return List
	 * @author FengXinhong
	 * 2010-4-14
	 */
	public List getSumBydid(Long kdid,String rname,String db,String db1);
	
	/**
	 * <li>����������������Ŀid��ѯ�����������Ŀ�е����з�����¼
	 * @param kcid
	 * @return 
	 * @author FengXinhong
	 * 2010-4-17
	 */
	public List getDistriByCid(Long kcid);
	/**
	 * <li>������������ȡ������ⷢ��������
	 * @param top
	 * @return
	 */
	public List getNewstop();
	/**
	 * 
	 * @param kiid ��Ϣ����id
	 * @param db  ��ʼʱ��
	 * @param db1  ��ֹʱ��
	 * @return  ����Ϣ����������
	 */
	public List getSumBywbid(Long kiid, String db, String db1) ;
/**
 * 
 * @param kiid ��Ϣid
 * @param status ����״̬
 * @param db ��ʼʱ��
 * @param db1 ��ֹʱ��
 * @return  ����Ϣ�����״̬������
 */
	public List getCountbywiid(Long kiid,Long  status, String db, String db1);
	/**
	 * 
	 * @param kcid ��ĿID
	 * @return  ����Ŀ�������Ѷ��ⷢ����Ϣ
	 */
	public List getInfoByCid(Long kcid);
	/**
	 * 
	 * @param kcid ��ĿID
	 * @return  ���ø���Ŀ����Ŀ�б�
	 */
	public List getChanelSite(Long kcid);
	public List getCsDistribute(Long kcid,Long kiid);
	/**
	 * 
	 * @param kcid ��ĿID
	 * @return  ����Ŀ������������Ϣ
	 */
	public List getInfoBycid(Long kcid);
	/**
	 * 
	 * @param kitype  ��Ϣ����
	 * @return  ����Ϣ��ֵ
	 */
	public WkTInfoscore getScore(String kitype);
	/**
	 * 
	 * @param kdid ����ID
	 * @return  �ò�����������ϢԱ
	 */
	public List getInfodep(Long kdid);
	/**
	 * 
	 * @param kbid ��Ϣ�ķ���ID
	 * @return  ��Ϣ�����ô���ͳ��
	 */
	 public List getInfoSiteTime(String kbid);
	 /**
	  * 
	  * @param kcid ��ĿID
	  * @return  ����Ŀ
	  */
	 public WkTChanel getChanel(Long kcid);
	 public List NewsSearch(Long keid,Long status,String bt,String et,String flag,String k,String s);
		public List OriNewsSearch(Long keid,String bt,String et,String flag,String k,String s);
	 /**
		 * 
		 * <li>�������������ط�����Ϣ�б���������ʱ���ָ����ʽ����
		 * @param cid
		 * 			��ĿID
		 * @param orderType
		 * 			����ʽ
		 * @return
		 * List 
		 * @author Administrator
		 */
		public List getOrderedNewsOfChanelFB(Long cid, String orderType);
		public List getRMKByKiid(Long kiid);
		 public WkTInfo getInfobyBid(Long bid);
		 /**
		  * 
		  * @return �����ѷ�����Ϣ
		  */
		 public List getPubInfo(Integer  pubId);
		 public List getPubInfo();
}
