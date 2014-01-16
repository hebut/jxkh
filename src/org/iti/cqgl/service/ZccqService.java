package org.iti.cqgl.service;

import java.util.Date;
import java.util.List;

import com.uniwin.basehs.service.BaseService;

public interface ZccqService extends BaseService {

	

	/**
	 * <li>��������:����ѧ��ѧ��ѧУid��ѯ
	 * @param kuid
	 * @param year
	 * @param term
	 * @param schid
	 * @return
	 */
	public List findBykuIdAndYearAndTermAndSchid(Long kuid,String year,short term,Long schid);
	/**
	 * <li>��������:����ѧ��ѧ��ѧУid,ѧԺ��רҵ���༶��ѧ�š�������������ѯ������Ϣ
	 * @param year
	 * @param term
	 * @param schid
	 * @param xyid
	 * @param zyid
	 * @param cla
	 * @param sid
	 * @param sname
	 * @return
	 */
	public List findByYearAndTermAndschidAndXyidAndZyidAndGradeAndClassAndSidAndName(String year,short term,Long schid,Long xyid,Long zyid,Integer grade,String cla,String sid,String sname);
	
	/**
	 * <li>��������:��ѯĳһ��ѧ���û���ѧ�ڳ��ڴ���
	 * @param kuid
	 * @param xlid
	 * @return
	 */
	public Integer findByKuidAndXlid(Long kuid,Long xlid);
	
	/**
	 * <li>��������:��ѯĳһ��ѧ���û���ѧ�ڳ�������
	 * @param kuid
	 * @param xlid
	 * @return
	 */
	public List findByKuIdAndXlId(Long kuid,Long xlid);

	/**
	 * 
	 * @param year
	 * @param term
	 * @param schid
	 * @param xyid
	 * @param grade
	 * @param zczscs
	 * @return
	 */
	public List findHgrenshu(String year,Short term,Long schid,Long xyid,Integer[] grade,Long zczscs);
	/**
	 * 
	 * @param year
	 * @param term
	 * @param schid
	 * @param zyid
	 * @param grade
	 * @param zczscs
	 * @return
	 */
	public List findZyHgrenshu(String year,Short term,Long schid,Long zyid,Integer[] grade,Long zczscs);
	/**
	 * 
	 * @param year
	 * @param term
	 * @param schid
	 * @param classid
	 * @param grade
	 * @param zczscs
	 * @return
	 */
	public List findClassHgrenshu(String year,Short term,Long schid,Long classid,Integer[] grade,Long zczscs);
	public List findByClassHgrenshu(String year,Short term,Long schid,Long classid,Long zczscs);
	/**
	 * 
	 * @param year
	 * @param term
	 * @param schid
	 * @param xyid
	 * @param grade
	 * @param zczscs
	 * @return
	 */
	public List findWhgrenshu(String year,Short term,Long schid,Long xyid,Integer[] grade,Long zczscs);
	/**
	 * 
	 * @param year
	 * @param term
	 * @param schid
	 * @param zyid
	 * @param grade
	 * @param zczscs
	 * @return
	 */
	public List findZyWhgrenshu(String year,Short term,Long schid,Long zyid,Integer[] grade,Long zczscs);
	/**
	 * 
	 * @param year
	 * @param term
	 * @param schid
	 * @param classid
	 * @param grade
	 * @param zczscs
	 * @return
	 */
	public List findClassWhgrenshu(String year,Short term,Long schid,Long classid,Integer[] grade,Long zczscs);
	public List findByClassWhgrenshu(String year,Short term,Long schid,Long classid,Long zczscs);
	
	/**
	  * <li>���������������û�id�������жϸ��û��Ƿ��Ѿ��г�����Ϣ
	  * @param kuid
	  * @param time
	  * @return
	  */
	   public  boolean checkIfExist(Long kuid,Date time);
	   /**
	    * 
	    * @param year
	    * @param term
	    * @param schid
	    * @param kuid
	    * @return
	    */
	   public List findbykuidAndyearAndterm(String year,Short term,Long schid,Long kuid);
	   /**
	    * 
	    * @return
	    */
	   public Long findMaxctZid();
	   /**
	    * 
	    * @param kuid
	    * @param time
	    * @return
	    */
	   public List findbyKuidAndLiketime(Long kuid,String time);
	   /**
	    * 
	    * @param year
	    * @param term
	    * @param schid
	    * @param classid
	    * @param grade
	    * @return
	    */
	   public List findClassStuWhg(String year,Short term,Long schid,Long classid,Integer[] grade);
	   public List findByClassStuWhg(String year,Short term,Long schid,Long classid);
	   /**
	    * 
	    * @param year
	    * @param term
	    * @param schid
	    * @param zyid
	    * @param grade
	    * @return
	    */
	   public List findZystuWhg(String year,Short term,Long schid,Long zyid,Integer[] grade);
	   /**
	    * 
	    * @param year
	    * @param term
	    * @param schid
	    * @param xyid
	    * @param grade
	    * @return
	    */
	   public List findstuWhgrenshu(String year,Short term,Long schid,Long xyid,Integer[] grade);
	   /**
	    * 
	    * @param kdid
	    * @return
	    */
	   public Integer findXynianzhi(Long kdid);
	   /**
	    * 
	    * @param kdid
	    * @return
	    */
	   public Integer findZynianzhi(Long kdid);
	   /**
	    * 
	    * @param kdid
	    * @return
	    */
	   public Integer findClassnianzhi(Long clId);
	   /**
	    * ��ѯĳһʱ�������ڳ������
	    * @param start starttime ��ʼʱ��
	    * @param end endtime ����ʱ��
	    * @param kdid ѧԺid��רҵid
	    * @return
	    */
	   public List findByStartAndEndAndKdid(Date start,Date end,Long kdid);
	   /**
	    * ��ѯĳһʱ��������ĳ�԰༶ѧ���������
	    * @param Start
	    * @param end
	    * @param clid
	    * @return
	    */
	   public List findByStartAndEndAndClid(Date Start,Date end,Long clid);
	   /**
	    * ��ѯĳһʱ��������ĳһ�꼶��ѧ���������
	    * @param start
	    * @param end
	    * @param kdid
	    * @param grade
	    * @return
	    */
	   public List findByStartAndEndAndKdidAndGrade(Date start,Date end,Long kdid,Integer grade);
	   
}
