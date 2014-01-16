package org.iti.jxgl.service;

import java.util.List;

import org.iti.jxgl.entity.JxTask;
import org.iti.jxgl.entity.JxCourse;
import org.iti.jxgl.entity.JxTeachplan;

import com.uniwin.basehs.service.BaseService;

public interface TaskService extends BaseService {

	/**
	 * <li>����������������ݣ�ѧ�ڣ���ʦ��¼�š��γ̱�ţ�ѧԺ��Ų�ѯ������ʵ���б�
	 * 
	 * @author XiaoMa
	 * @param year
	 * @param term
	 * @param tid
	 * @param jcid
	 * @return
	 */
	public List<JxTask> FindTaskByYTT(String year, short term, Long kuid, Long jcid, Long kdid);

	/**
	 * <li>����������������ݣ�ѧ�ڣ���ʦ���б��ڿμƻ��б��ѯ������ʵ���б�
	 * 
	 * @author XiaoMa
	 * @param year
	 * @param term
	 * @param tid
	 * @param jcid
	 * @return
	 */
	public List<JxTask> FindTaskByYTTP(String year, short term, Long kdId, List thidlist, List planlist);

	/**
	 * <li>����������������ݣ�ѧ�ڣ�kdid��filelist,�ڿμƻ��б��ѯ������ʵ���б�
	 * 
	 * @author XiaoMa
	 * @param year
	 * @param term
	 * @param kdid
	 * @param jcid
	 *            list��Ϊ��ʱ
	 * @return
	 */
	public List FindTaskByYTTF(String year, short term, Long kdId, List filelist,List filelist1);

	/**
	 * <li>����������������ݣ�ѧ�ڣ�kdid��filelist,�ڿμƻ��б��ѯ������ʵ���б�
	 * 
	 * @author XiaoMa
	 * @param year
	 * @param term
	 * @param kdid
	 * @param jcid
	 *            listΪ��ʱ
	 * @return
	 */
	public List FindTaskByYTKd(String year, short term, Long kdId);
	
	/**
	 * <li>����������������ݣ�ѧ�ڣ���ʦ�š��༶��ѯ������ʵ�壨��õ�Ψһ�Ŀγ�id��
	 * 
	 * @author XiaoMa
	 * @param year
	 * @param term
	 * @param tid
	 * @param jtclass
	 * @return
	 */
	public JxTask FindTaskYTTC(String year, short term, Long kuId, String jtclass);

	/**
	 * <li>����������������ݣ�ѧ�ڣ���ʦ�š��ϰ������ѯ������ʵ��
	 * 
	 * @author XiaoMa
	 * @param year
	 * @param term
	 * @param tid
	 * @param jtsumstate
	 * @return
	 */
	public JxTask FindTaskYTTS(String year, short term, String thid, Integer jtsumstate);

	/**
	 * <li>����������������ݣ�ѧ�ڣ���ʦ�Ų�ѯ������ʵ���б�
	 * 
	 * @author XiaoMa
	 * @param year
	 * @param term
	 * @param tid
	 * @return
	 */
	public List<JxTask> FindTaskByYearTermThid(String year, short term, Long kuId, Long kdId);

	/**
	 * <li>����������������ݣ�ѧ�ڣ���ʦ�Ų�ѯ������ʵ��
	 * 
	 * @author XiaoMa
	 * @param year
	 * @param term
	 * @param tid
	 * @return
	 */
	public JxTask FindTaskEntity(String year, short term, String thid);

	/**
	 * <li>����������������ݣ�ѧ�ڣ���ʦ��¼�Ų�ѯ�γ̱�ţ����ظ���
	 * 
	 * @author XiaoMa
	 * @param year ���
	 * @param term ѧ��
	 * @param tid ��ʦ��¼��
	 * @return
	 */
	public List FindAllCourseId(String year, short term, Long thid);

	/**
	 * <li>�������������ݿγ̺Ų�ѯ��ʦ��ţ����ظ���
	 * 
	 * @author XiaoMa
	 * @param jcid
	 * @return
	 */
	public List FindByJcid(Long jcid, String year, short term, Long kdid);

	/**
	 * <li>����������������ݡ�ѧ�ڡ��γ̺š����ε�λ��ѯ�������б� ѧ���鿴�ڿμƻ�
	 * 
	 * @author XiaoMa
	 * @param jcid
	 * @return
	 */
	public List<JxTask> FindTaskByYTKJ(String year, short term, Long kdid, Long jcid);

	/**
	 * <li>����������������ݡ�ѧ�ڡ��γ̺š����ε�λ��ѯ�������б� ѧ���鿴�ڿμƻ�
	 * 
	 * @author XiaoMa
	 * @param year,term,kdid,jcidlst
	 * @return
	 */
	public List FindTaskByYTKJlst(String year, short term, Long kdid, List jcidlst);
	/**
	 * <li>����������������ݡ�ѧ�ڡ��γ̺š����ε�λ\��ʦ�� ��ѯ�������б� �ڿ��ļ����γ̺ϰ�
	 * 
	 * @author XiaoMa
	 * @param jcid
	 * @return
	 */
	public List<JxTask> FindTaskByYTKJK(String year, short term, Long kdid, Long jcid,Long kuId);
	
	/**
	 * <li>����������������ݡ�ѧ�ڡ��γ̺š����ε�λ���༶���Ʋ�ѯ�������б� ѧ���鿴�ڿμƻ�
	 * 
	 * @author XiaoMa
	 * @param jcid
	 * @return
	 */
	public List<JxTask> FindTaskByYTKJC(String year, short term, Long kdid, Long jcid, String jtClass, String jtClass1);

	/**
	 * <li>��������������ִ�мƻ��Ų�ѯ�������б�
	 * 
	 * @author XiaoMa
	 * @param jeid
	 * @return
	 */
	public List<JxTask> FindTaskByJeid(Long jeId);

	/**
	 * <li>ͨ�����ѧ�ڲ��Ų� ��������
	 * 
	 * @author Chen Li
	 * @param year
	 * @param term
	 * @param kdid
	 * @return �������б� 2010-7-11
	 */
	public List<JxTask> FindTaskByYTK(String year, short term, Long kdid);

	/**
	 *<li>���������� ��ѯ�����������еĿγ̱��
	 * 
	 * @param kdid
	 * @return
	 */
	public List FindAllCourseFromTask(Long kdid);

	/**
	 * <li>������������ѯ��������ĳѧ��ĳѧ�����еĿγ̱��
	 * 
	 * @param year
	 * @param term
	 * @param kdid
	 * @return
	 */
	public List FindCourseFromTask(String year, short term, Long kdid);
	/**
	 * <li>������������ѯ��������ĳѧ��ĳѧ�����еĽ�ʦ���
	 * ����
	 * @param year
	 * @param term
	 * @param kdid
	 * @return
	 */
	public List FindTeacheridFromTask(String year, short term, Long kdid);
	/**
	 * <li>������������ѯ�����������еĽ�ʦ���
	 * 
	 * @param kdid
	 * @return
	 */
	public List FindTeacherFromTask(Long kdid);

	/**
	 * <li>�������������������ѯ������
	 * 
	 * @param year
	 * @param term
	 * @param cid
	 * @param kuid
	 * @param deplist
	 * @return
	 */
	public List FindTask(String year, short term, Long cid, Long kuid, List deplist);

	/**
	 * �����Ӳ�ѯ������û���Ϊ�� ��cl
	 * 
	 * @param year
	 * @param term
	 * @param thid
	 * @param cid
	 * @param courseid
	 * @return
	 */
	public List findTaskByYearTermThidKdidcourseid(String year, short term, Long kuid, Long cid, Long courseid);

	/**
	 * �����Ӳ�ѯ������û���Ϊ�� ���γ̱��Ϊ��xiaoma
	 * 
	 * @param year
	 * @param term
	 * @param thid
	 * 
	 * @param courseid
	 * @return
	 */
	public List findTaskByYearTermThid(String year, short term, String thid, Long cid);

	/**
	 * ���������ѯ���û�Ϊ�գ�ѡ��ȫѧԺ��ʦ
	 * 
	 * @param year
	 * @param term
	 * @param cid
	 * @param courseid
	 * @return
	 */
	public List findByYearAndTermAndkdidAndCourseid(String year, short term, Long cid, Long courseid);
	
	
	/**
	 * 
	 * 
	 * @param year
	 * @param term
	 * @param cid
	 * @param courseid
	 * @return����--���������õ�
	 */
	public List findByYearAndTermAndkdid2(String year, short term, Long cid, Long courseid);
	/**
	 * ���������ѯ���û�Ϊ�գ�ѡ��ĳϵ��ʦ
	 * 
	 * @param year
	 * @param term
	 * @param teacherlist
	 * @param cid
	 * @param courseid
	 * @return
	 */
	public List findByYearAndTermAndTKuidAndSchid(String year, short term, Long kuid, Long kdid);
	public List findByYearAndTermAndTeacherlistAndCourseid(String year, short term, Long cid, List teacherlist, List courseidlist);

	/**
	 * <li>�������������������ѯ������
	 * 
	 * @param year
	 * @param term
	 * @param cid
	 * @param kuid
	 * @param deplist
	 * @return
	 */
	public List FindTaskBySum(String year, short term, Long kdid, List tasklist);

	/**
	 * <li>������������ѯ�ϰ������ͬ���������б�
	 * 
	 * @param sumstate
	 * @return
	 */
	public List FindTaskBySumState(int sumstate);

	

	/**
	 * <li>��������:�������ѧ�ڽ�ʦ��Ų�ѯ�ý�ʦ������
	 * 
	 * @param year
	 * @param term
	 * @param tid
	 * @return
	 */
	public List findByYearAndTermAndTid(String year, short term, String tid);

	/**
	 * <li>��������:������ݡ�ѧ�ڡ���ʦ��¼�š��γ̱�Ų�ѯ�ý�ʦ������
	 * 
	 * @param year
	 *            XiaoMa
	 * @param term
	 * @param tid
	 * @param jcid
	 * @return
	 */
	public List findByYearTermTidCid(String year, short term, Long thid, Long jcid);

	/**
	 * <li>���������� ������ݡ�ѧ�ڡ�רҵ�б����������
	 * 
	 * @param year
	 * @param term
	 * @param deplist
	 * @return
	 */
	public List findByDeplistAndYearAndTerm(String year, short term, List deplist);

	/**
	 * <li>���������� ����רҵ�б���ҽ�ʦ����б�
	 * 
	 * @param deplist
	 * @return
	 */
	public List FindTeacherBydeplist(List deplist);

	/**
	 * 
	 * <li>��������������ѧ�ꡢѧ�ڲ�ѯ������ ���е� �γ̱��
	 * 
	 * @author zhangli
	 * @param year
	 *            ѧ��
	 * @param term
	 *            ѧ��
	 * @return �γ̱���б�
	 */
	public List findjcByYearAndTerm(String year, short term);

	/**
	 * <li>��������:������ݡ�ѧ�ڡ���ʦ��š�ѧԺ��Ų�ѯ���ý�ʦ����Ľ�ʦ������ĺϰ����
	 * 
	 * @author liwei
	 * @param year
	 * @param term
	 * @param tid
	 * @return
	 */
	public List findByYearAndTermAndkdidAndT(String year, short term, Long kdid, Long kuid);

	/**
	 * <li>��������:������ݡ�ѧ�ڡ���ʦ��š�ѧԺ��Ų�ѯ�ý�ʦ������ĺϰ����
	 * 
	 * @author liwei
	 * @param year
	 * @param term
	 * @param tid
	 * @return
	 */
	public List findtaskbyyearAndtermAndtAndkdid(String year, short term, Long kuid, Long kdid);

	/**
	 * <li>��������������ѧ�ꡢѧ�ڡ��γ̱�Ų���������
	 * 
	 * @author zhangli
	 * @param yearѧ��
	 * @param termѧ��
	 *            *@param Cid�γ̱�
	 * @return �����飨��ʦ��ź���֯��λ���б�
	 */
	public List findTaskByYearAndTermAndCid(String yaer, Short term, Long jcid);

	/**
	 * <li>��������������ѧ�꣬ѧ�ڣ��γ�id��ѯ�����ſε�������
	 * 
	 * @param year
	 *            ѧ��
	 * @param term
	 *            ѧ��
	 * @param jcid
	 *            �γ�id
	 * @return �������б�
	 * @author FengXinhong 2010-7-18
	 */
	public List findThidByYearAndTermAndJcId(String year, short term, Long jcid);

	/**
	 * <li>��������������ѧ�꣬ѧ�ڡ�jcid��kdid��ѯ������ XiaoMa
	 * 
	 * @param year
	 * @param term
	 * @return
	 */
	// public List findThidByYearTermJcIdKdid(String year, short term, Long jcid,Long kdid);
	/**
	 * <li>��������������ѧ�꣬ѧ�ڲ�ѯ������
	 * 
	 * @param year
	 * @param term
	 * @return
	 */
	public List findByYearAndTerm(String year, short term);

	/**
	 * <li>����������������ʱ����������м�¼
	 * 
	 * @param year
	 * @param term
	 * @return
	 */
	public List findAllTemptask();

	/**
	 * <li>���������� ������ݡ�ѧ�ڡ�רҵ�б���ҿγ̱�� * * @author duqing
	 * 
	 * @param year
	 * @param term
	 * @param deplist
	 * @return
	 */
	public List FindCourseFromTask(String year, short term, List deplist,Long kuid);

	/**
	 * <li>���������� ������ݡ�ѧ�ڡ�רҵ�б���Ҳ�ͬ�ĺϰ� *
	 * 
	 * @author XiaoMa
	 * 
	 * @param year
	 * @param term
	 * @param deplist
	 * @return
	 */
	public List FindSumstateFromTask(String year, short term, List deplist);

	/**
	 * <li>���������� ������ݡ�ѧ�ڡ�רҵ�б���Ҳ�ͬ�Ŀγ� *ͳ���ڿομ��ϴ������
	 * 
	 * @author XiaoMa
	 * 
	 * @param year
	 * @param term
	 * @param deplist
	 * @return
	 */
	public List FindJcidFromTask(String year, short term, Long kdid);

	/**
	 * <li>�������������ݿγ̱�Ų����������пγ̵Ŀ�����𣨿���/���飩 * @author duqing
	 * 
	 * @param year
	 *            �γ̱��
	 * @return �������б�
	 */

	public List findCidinTaskNotinExecute(String year, short term, Long kdid, Long fromid, String eyear, short eterm, List deplist);

	/**
	 * <li>������������ѯ�������д��ڵ���ѧУ����ϵִ�мƻ��п��ε�λΪ��ѧԺ�����ڵĿγ���Ϣ
	 */
	public List findCidinExecuteNotinTask(List deplist, String eyear, short eterm, List cidlist, Long kdid);

	/**
	 * <li>��������������ѧ�ꡢѧ�ڡ���ʦ��¼�š��γ̱�š�ѧԺ��Ų���������ʵ��
	 * 
	 * @author zhangli
	 * @param year
	 *            ѧ��
	 * @param term
	 *            ѧ��
	 * @param thid
	 *            ��ʦ��¼��(����Ϊ��)
	 * @param cid
	 *            �γ̱��
	 * @return ������ʵ���б�
	 */
	public List findTaskByYearTermThidCid(String year, Short term, Long thid, Long cid,Long kdid);

	/**
	 * <li>��������������ѧ�� ѧ�ڲ��������飨�ҿγ̱�ţ�
	 * 
	 * @author zhangli
	 * @param y
	 *            ѧ��
	 * @param t
	 *            ѧ��
	 * @return �γ̱���б�
	 */

	public List findJcidByYearTern(String y, Short t);

	/**
	 * <li>�������������ݿγ̱�Ų����������пγ̵Ŀ�����𣨿���/���飩 * @author duqing
	 * 
	 * @param year
	 *            �γ̱��
	 * @return �������б�
	 */

	/**
	 * <li>��������������ִ�мƻ���Ų�ѯ������
	 * 
	 * @param jeid
	 *            ִ�мƻ����
	 * @return �������б�
	 */
	public JxTask FindFromExectution(Long jeid);

	/**
	 * <li>��������������ѧ�� ѧ�� �γ̱�Ų���������
	 * 
	 * @param y
	 *            ѧ��
	 * @param t
	 *            ѧ��
	 * @param cid
	 *            �γ̱��
	 * @return ������ʵ���б�
	 */

	public List findTaskByYearTermJcid(String y, Short t, Long cid);

	public List FindClassByYearAndTermAndCid(String year, short term, Long cid,Long kdid);

	/**
	 * <li>�������������ݿγ̱�Ų����������пγ̵Ŀ�����𣨿���/���飩 * @author duqing
	 * 
	 * @param year
	 *            �γ̱��
	 * @return �������б�
	 */
	public JxTask FindTypeFromTask(Long cid);

	/**
	 * <li>��������������kdid��sumstate��ѯ������
	 * 
	 * @param kdid
	 *            sumstate XiaoMa
	 * 
	 * @return �������б�
	 */
	public List<JxTask> FindTaskByKdidSumstate(Long kdid, Integer jtsumstate);

	/**
	 * <li>�����������ж���ĳ���������кϰ����һ�µ��������Ƿ������б���
	 * 
	 * @param task
	 * @param tlist
	 * @return
	 * @author FengXinhong
	 */
	public boolean CheckIfInTasklist(JxTask task, List tlist);

	/**
	 * <li>��������������kdid��sumstate��ѯ������
	 * 
	 * @param kdid
	 *            sumstate XiaoMa
	 * 
	 * @return �������б�
	 */
	public List<JxTask> FindTaskBySumstate(Integer jtsumstate);

	/**
	 * <li>��������������ѧ�ꡢѧ�ڡ���ʦ��¼�š�ѧԺ��Ų���������
	 * 
	 * @author zhangli 2010-8-3
	 * @param year
	 *            ѧ��
	 * @param term
	 *            ѧ��
	 * @param t
	 *            ��ʦ��¼��
	 * @param kdid
	 *            ѧԺ���
	 * @return �γ̱���б�
	 */
	public List findTaskByYearTermTidKdid(String year, short term, Long t, Long kdid);

	/**
	 * <li>������������ѯ�Ƿ��Ѿ���������רҵ��������
	 */
	public List FindLeadInOrNot(String year, short term, Long kdid);

	/**
	 * <li>������������ѯĳ��ѧ���ϵĿγ�
	 */
	public List FindCourseForStu(String year, short term, Long kuid);
	
	
	/**
	 * <li>����������������ݣ�ѧ�ڣ���ʦ��,�γ����Ʋ�ѯ������ʵ��
	 * 
	 * @author ����
	 * @param year
	 * @param term
	 * @param tid
	 * * @param cid
	 * @return
	 */
	public JxTask FindTasks(List cids,String year, short term, String thname,Long kdid);
	/**
	 * <li>�������������ݿγ����Ʋ�ѯ�γ�id
	 * 
	 * @author ����
	 
	 * * @param cname
	 * @return
	 */
	public  List fidjcids(String cname);
	/**
	 * <li>����������������ݣ�ѧ�ڣ��γ�id�����༶��ѯ������ʵ��
	 * 
	 * @author ����
	 * @param year
	 * @param term
	 * @param class
	 * * @param cids
	 * @return
	 */
	public JxTask skjhfindtask(List cids,String year, short term, String classname,Long kdid);
	/**
	 * <li>����������������ݣ�ѧ�ڣ��γ�id�����༶,ѧУ����ѧԺ��ѯ������ʵ��
	 * 
	 * @author ����
	 * @param year
	 * @param term
	 * @param class
	 * * @param cids
	 * @return
	 */
	public JxTask skjhfindtask(List cids,String year, short term, String classname,List kdid);
	/**
	 * <li>����������������ݣ�ѧ�ڣ�ѧԺ��ѯ������ʵ��
	 * 
	 * @author ����
	 * @param year
	 * @param term
	 * @param class
	 * * @param cids
	 * @return
	 */
	public List<JxTask> skwjfindtask(String year, short term, List kdid);
	/**
	 * <li>����������������ݣ�ѧ�ڣ�ѧԺ,��ʦ��Ų�ѯ��ʦ�γ��б�
	 * 
	 * @author ����
	 * @param year
	 * @param term
	 * @param kdid
	 * * @param  kuid
	 * @return
	 */
	public List<JxCourse> paperfindcourse (String year, short term, Long kdid,Long kuid);
	/**
	 * <li>��������������ѧ�ꡢѧ�ڡ���ʦ��¼�š��γ̱�š�ѧԺ��Ų���������ʵ��
	 * 
	 * @author zhangli
	 * @param year
	 *            ѧ��
	 * @param term
	 *            ѧ��
	 * @param thid
	 *            ��ʦ��¼��(����Ϊ��)
	 * @param cid
	 *            �γ̱��
	 * @return ������ʵ���б�
	 */
	public List findTaskByYearTermThidCidcname(String year, Short term, Long thid, Long cid,Long kdid,String claname);

	
	/**
	/**
	 * <li>����������������ݡ�ѧ�ڡ��γ̺š����ε�λ���༶���Ʋ�ѯ�������б� ѧ���鿴�ڿμƻ�
	 * 
	 * @author XiaoMa
	 * @param jcid
	 * @return
	 */
	public List<JxTask> FindTaskByYTKJC(String year, short term, Long kdid,  String jtClass, String jtClass1);
	
	/**
	/**
	 * <li>����������������ݡ�ѧ�ڡ��ڿε�λ����ʦid��ѯ�γ�ʵ��
	 
	 */
	public List<JxCourse> findcourselist(String year, short term,Long kdid,Long kuid);
	/**
	/**
	 * <li>����������������ݡ�ѧ�ڡ��ڿε�λ����ʦid��ѯ�γ�ʵ��
	 
	 */
	public List<JxCourse> findbyYTK(String year, short term,Long kdid);
	/**
	/**
	 * <li>����������������ݡ�ѧ�ڡ��ڿε�λ����ʦid��ѯ�ϰ�״̬
	 
	 */
	public List findtasklist(String year, short term,Long kdid,Long kuid);
	/**
	/**
	 * <li>����������������ݡ�ѧ�ڡ��ڿε�λ����ʦid���γ̱�Ų�ѯ�ϰ�״̬
	 
	 */
	public List findtasklist(String year, short term,Long kdid,Long kuid,Long jcid);
	/**
	/**
	 * <li>�������������ݺϰ�״����ѯ������ʵ��
	 
	 */
	public List<JxTask> findtaskbysumstate(int sumstate);
	
	public List findByYearAndTermAndTeacherlistAndCourseid1(String year, short term, Long cid, List teacherlist, Long courseid);

	public List findTaskByYearTermCidThname(Long jcid,String tname,Integer jtsumstate );
	/**
	/**
	 * <li>������������ѯѧ����ѯӦ��ʵ��γ��б�
	 
	 */
	public List<JxCourse> findtaskbyYTKU(String year, short term,Long kuid);

    /**
     * <li>����ѧ��ѧ�ڿγ̺Ű༶����ѧԺ���2011-4-22
     * @param year ѧ��
     * @param term ѧ��
     * @param cid �γ̺�
     * @param classname �༶����
     * @param kdid ѧԺ���
     * @return ���������
     */
    public  JxTask findTaskByClassname(String year, short term,Long cid, String classname,Long kdid);
    /**
     * 
     * @param year
     * @param term
     * @param schid
     * @param type
     * @param claname
     * @return
     */
    public List findByYearAndTermAndKdidAndVAndClass(String year, short term, Long schid,String type,String claname);
}
