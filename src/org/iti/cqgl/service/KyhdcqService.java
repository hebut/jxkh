package org.iti.cqgl.service;

import java.util.Date;
import java.util.List;

import com.uniwin.basehs.service.BaseService;

public interface KyhdcqService extends BaseService {

	/**
	 * <li>��������������ѧ�ꡢѧ�ڡ�ѧУid��ѧԺid��רҵid���꼶���༶��ѧ�š�������ѯ�����������
	 * @param year
	 * @param term
	 * @param schid
	 * @param xyid
	 * @param zyid
	 * @param grade
	 * @param cla
	 * @param sid
	 * @param sname
	 * @return
	 */
   public  List findByYearAndTermAndschidAndXyidAndZyidAndGradeAndClassAndSidAndName(String year,short term,Long schid,Long xyid,Long zyid,Integer grade,String cla,String sid,String sname);
  
 /**
  * <li>���������������û�id��У��id��ѯѧ����������
  * @param kuid
  * @param xlid
  * @return
  */
   public  List findByKuIdAndXlId(Long kuid,Long xlid);
   
   /**
    * <li>�ж��û���ĳһ�εĿ������Ƿ��Ѿ��г���
    * @param kuid
    * @param start
    * @param end
    * @return
    */
   public boolean checkIfExsit(Long kuid,Date start);
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
	 * 
	 * @param year
	 * @param term
	 * @param schid
	 * @param kuid
	 * @return
	 */
	public List findKybykuidAndyearAndterm(String year,Short term,Long schid,Long kuid);
	/**
	 * 
	 * @return
	 */
	public Long findMaxctZid();
	/**
	 * 
	 * @param kuid
	 * @param time
	 * @param end
	 * @return
	 */
	public List findbyKuidAndLiketime(Long kuid, String time, String end);
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
	    * @param start
	    * @param end
	    * @param kdid
	    * @return
	    */
	   public List findByStartAndEndAndKdid(Date start,Date end,Long kdid);
	   /**
	    * 
	    * @param Start
	    * @param end
	    * @param clid
	    * @return
	    */
	   public List findByStartAndEndAndClid(Date Start, Date end, Long clid) ;
	   /**
	    * 
	    * @param start
	    * @param end
	    * @param kdid
	    * @param grade
	    * @return
	    */
	   public List findByStartAndEndAndKdidAndGrade(Date start, Date end,
				Long kdid, Integer grade);
 
}
