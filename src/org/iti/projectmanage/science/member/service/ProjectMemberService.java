package org.iti.projectmanage.science.member.service;

import java.util.List;

import org.iti.gh.entity.GhXm;
import org.iti.xypt.entity.Teacher;

import com.uniwin.basehs.service.BaseService;
import com.uniwin.framework.entity.WkTUser;

public interface ProjectMemberService extends BaseService {
	
	/**
	 * ��ѯ��Ŀ��ĳ����Ա
	 */
	public List findByKyIdAndKuId(Long kyId, Long kuId);
	/**
	 * ��ѯ��Ŀ��ȫ����Ա
	 */
	public List findByKyId(Long kyId,int pageNum, int pageSize );
	/**
	 * ��ѯУ����Ա
	 */
	public String  findOutMember();
	public List findOutMemberByuid(Long kuid);
	public List findByMid(Long mid);
	/**
	 * ��ѯ��Ŀ
	 */
	public  GhXm findXM(Long kyid);
	/**
	 * 
	 */
	public  List findteacher(Long kuid);
	/**
	 * �о�����
	 */
	public  List findYjfx(Long gyid);
   /**
    * 
    * @return ��Ŀ����Ա
    */
	public List findByKyXmId(Long kyid);
	/**
	 * 
	 * @param kdid ����ID
	 * @param name �û�����
	 * @param pageNum ҳ��
	 * @param pageSize ҳ���С
	 * @return
	 */
	public List<WkTUser> findUserForGroupByKdIdAndName1(Long kdid, String name, Boolean teacher, Boolean student, Boolean graduate,int pageNum, int pageSize);
/**
 * ͳ�ƽ�ʦ��Ŀ���𡢽�չ
 */
	public List<GhXm> findByKdidAndYearAndKuidAndTypeAndJb(Long kuid,String year,Integer type, String jb,String hx,String jz);
/**
 * ͳ������������Ŀ
 */
	public List findQtByYearAndKuid(String year, Long kuid,Integer type,String hx);
	/**
	 * 
	 * @param name ��Ŀ����
	 * @return ��Ŀ
	 */
	public GhXm findByName(String name);
	/**
	 * 
	 * @param xmid ��ĿID
	 * @param kuid �û�ID
 	 * @param type ��������
	 * @return �����б�
	 */
	 public List findHyLwByXm(Long xmid,Long kuid,Short type);
	 public List findQkLwByXm(Long xmid,Long kuid,Short type);
	 /**
	  * 
	  * @param xmid
	  * @param kuid
	  * @param type
	  * @return �������
	  */
	 public List findRjzzByXm(Long xmid,Long kuid,Short type);
	 /**
	  * 
	  * @param xmid
	  * @param kuid
	  * @param type
	  * @return ����ר��
	  */
	 public List findFmzlByXm(Long xmid,Long kuid,Short type);
	/**
	 * 
	 * @param kyid
	 * @return ��Ŀ
	 */
	 public List findXmByid(Long kyid);
	 
	 public List findZzByXm(Long xmid,Long kuid,Short type);
}
