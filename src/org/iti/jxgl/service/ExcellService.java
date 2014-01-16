package org.iti.jxgl.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;


import com.uniwin.basehs.service.BaseService;

public interface ExcellService extends BaseService {
	/**
	 * ����ֹ��ת��Ϊ01��ʽ
	 * @param week
	 * @return
	 */
	
     public String changeweekToweeks(String week);
     /**
      * �ҵ��������кϰ����������
      * @return
      */
     public Integer findMaxSumstate();
     /**
      * �ҵ�ѧ��ѧԺ�������кϰ����������
      * @return
      */
     public Integer findStudentMaxSumstate();
     /**
      * �ж������鵼���ʽ����ͷ�Ƿ���ȷ��
      * @param filename
      * @param years
      * @param term
      * @param IsIni
      * @return
     * @throws IOException 
     * @throws FileNotFoundException 
      */
     public String is_format(String filename,List btlist) throws FileNotFoundException, IOException;
     /**
      * ����ʱ�������е�����ȫ��ɾ��
      */
     public void deletetemptask();
     public void deletetempSche(Long kdid);
     
    ////////////�����ĵ�
     public void deletetemptask2();
    // public List findAllTemptask();
     /**
      * ������ʱ��2�е�ĳ��ѧԺ�����м�¼
      */
     public List findTemptask2Bykdid(Long kdid);
     
     /**
      * ������ʱ��3�е�ĳ��ѧԺ�����м�¼
      */
     public List findTemptaskBykdid(Long kdid);
     /**
      * �������������кϰ���������б�
      * @param year
      * @param term
      * @param cid
      * @param courseid
      * @return
      */
     public List findSumByYearAndTermAndkdidAndCourseid(String year, short term, Long cid, Long courseid);
     /**
      * �������������޺ϰ���������б�
      * @param year
      * @param term
      * @param cid
      * @param courseid
      * @return
      */
     public List findNoSumByYearAndTermAndkdidAndCourseid(String year, short term, Long cid, Long courseid);
     
     /**
      * ������ʱ������4�е�ѧ��ѧԺ������
      * @param kdid
      * @return
      */
     
     public List findTemptask4Bykdid(Long kdid);
     /**
      * ����ѧ��ѧԺ���������ڿε�λΪĳ��ѧԺ�����пγ���Ϣ
      * @param fromid
      * @param year
      * @param term
      * @param kdid
      * @return
      */
     
     public List findCoursefromTaskByfromid(String year,short term,Long kdid,Long fromid);
     /**
 	 * <li>ͨ�����ѧ�ڲ��Ų� ��������
 	 * 
 	 * @author Chen Li
 	 * @param year
 	 * @param term
 	 * @param kdid
 	 * @return �������б� 2010-7-11
 	 */
 	public List FindTaskByYTK(String year, short term, Long kdid);
 	/**
	 * <li>������������ѯѧ��ѧԺ��������ĳѧ��ĳѧ�����еĿγ̱��cl
	 * 
	 * @param year
	 * @param term
	 * @param kdid
	 * @return
	 */
	public List FindCourseFromTask(String year, short term, Long kdid);
	/**
	 * ����ѧ�������������кϰ���б�
	 * @param year
	 * @param term
	 * @param kdid
	 * @param fromid
	 * @return
	 */
	public List findSum(String year, short term, Long kdid,Long fromid);
	/**
	 * ����ѧ����������û�кϰ���б�
	 * @param year
	 * @param term
	 * @param kdid
	 * @param fromid
	 * @return
	 */
	public List findNotSum(String year, short term, Long kdid,Long fromid);
	/**
	 * <li>������������ѯ�ϰ������ͬ���������б�
	 * 
	 * @param sumstate
	 * @return
	 */
	public List FindTaskBySumState(int sumstate);
	
	public List Findbkmd(Integer year,short term,Long kdid);
	public List FindbyYTKD(String year,short term,Long kdid);
	public List FindbyYTSidCname(Integer year,short term,String sid,String cname);
     

}
