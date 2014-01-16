package org.iti.xypt.service;

import java.util.List;
import org.iti.kyjf.entity.Zbteacher;
import org.iti.xypt.entity.Teacher;
import com.uniwin.basehs.service.BaseService;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;

public interface TeacherService extends BaseService {

	public List FindBykuId(Long kuId);

	/**
	 * 
	 * <li>ͨ����ʦ��ź�ѧУ��Ų��ҽ�ʦ
	 * 
	 * @author Chen Li
	 * @param thid
	 * @param sid
	 * @return ��ʦ 2010-7-11
	 */
	public List FindTeacherByTidAndKdid(String thid, Long sid);

	/**
	 * 
	 * <li>ͨ����ʦ��ź�ѧУ��Ų��ҽ�ʦ
	 * 
	 * @author XiaoMa
	 * @param thid
	 * @param sid
	 * @return ��ʦ 20100909
	 */
	public List FindTeacherByKuidAndKdid(Long kuid, Long sid);

	/**
	 * 
	 * <li>ͨ����ʦ��Ų��ҽ�ʦ
	 * 
	 * @author Chen Li
	 * @param thid
	 * @param sid
	 * @return ��ʦ 2010-7-11
	 */
	public List FindTeacherByThid(String thid);

	/**
	 * 
	 * <li>ͨ��kdid������֯��λ����
	 * 
	 * @author XiaoMa
	 * @param kdid
	 * @return wktuser
	 */
	public WkTDept findWktuserByKdid(Long kdid);

	/**
	 * 
	 * <li>ͨ��kuname����kuid
	 * 
	 * @author XiaoMa
	 * @param kdid
	 * @return wktuser
	 */
	public WkTUser findUserByKuname(String kuname);

	/**
	 * <li>���������������û���Ų��ҽ�ʦ
	 * 
	 * @param kuId
	 * @return
	 */
	public Teacher findBtKuid(Long kuId);

	/**
	 * 
	 * <li>ͨ����¼�Ų��ҽ�ʦ���
	 * 
	 * @author Li Wei
	 * @param kuid
	 * @return ��ʦ��� 2010-7-12
	 */
	public String FindTeacherByKuid(Long kuid);

	/**
	 * 
	 * <li>ͨ���û���Ų��ҽ�ʦ
	 * 
	 * @author zhangxue
	 * @param kuid
	 * @return ��ʦ 2010-7-12
	 */
	public Teacher findBykuid(Long kuid);

	/**
	 * 
	 * <li>ͨ����֯��λ�б�ͽ�ɫ��Ų��ҽ�ʦ
	 * 
	 * @author zhangxue
	 * @param kuid
	 * @return ��ʦ 2010-7-12
	 */
	public List findBydeplistAndrid(List deplist, Long rid);

	/**
	 * 
	 * <li>ͨ����֯��λ�б�ͽ�ɫ��Ų��ҽ�ʦ
	 * 
	 * @author zhangxue
	 * @param kuid
	 * @return ��ʦ 2010-7-12
	 */
	public List findBydeplistAndrid(List deplist, Long rid, String tname, String tno);

	/**
	 * <li>������������ѯĳЩ���ž���ĳ��ɫ���û��б��û����ܾ���norid��ɫ��
	 * 
	 * @param deplist
	 * @param rid
	 * @param norid
	 * @return List
	 * @author DaLei
	 */
	public List findBydeplistAndrid(List deplist, Long rid, Long norid);

	/**
	 * <li>������������ѯĳЩ���ž���ĳ��ɫ���û��б��û����ܾ���norid��ɫ��
	 * 
	 * @param deplist
	 * @param rid
	 * @param norid
	 * @return List
	 * @author DaLei
	 */
	public List findBydeplistAndrid(List deplist, Long rid, Long norid, String tname, String to);

	/**
	 * ��ѯĳЩ�����в���buid���赥λָ����ʦ�Ľ�ʦ�б�
	 * 
	 * @param deplist �����б�
	 * @param rid ��ʦ��ɫ���
	 * @param buid ���赥λ���
	 * @return
	 */
	public List findNoBsTeacher(List deplist, Long rid, Long buid, String tname, String to);

	public List findNoYjsTeacherAndKdid(List deplist, Long rid, Long kdid, String tname, String to);

	public List findNoCqTeacherAndSchid(List deplist, Long rid, Long schid, String tname, String to);

	/**
	 * ��ѯĳЩ������ĳ��ѧ����ָ����ʦҲ���Ǹ�ѧ����ͬ�����Ľ�ʦ�Ľ�ʦ�б�
	 * 
	 * @param deplist �����б�
	 * @param rid ��ʦ��ɫ���
	 * @param kuid ָ����ʦ���û����
	 * @param bdbId ѧ������˫���ϵ���
	 * @return
	 */
	public List findNoBsPeerview(List deplist, Long rid, Long kuid, Long bdbId);

	public List findNoBsPeerview(List deplist, Long rid, Long kuid, Long bdbId, String tname, String tno);

	/**
	 * ��ѯĳѧУ�Ľ�ʦ�ŵĽ�ʦ
	 * 
	 * @param thid
	 * @param kdid
	 * @return
	 */
	public List findByThidAndKdid(String thid, Long kdid);

	public Teacher findByThid(String thid);

	/**
	 * <li>�������������ݽ�ʦ��š���֯��λ����б���ҽ�ʦ
	 * 
	 * @author zhangli
	 * @param thid
	 * @param kdid
	 * @return ��ʦʵ���б�
	 */
	public List findTeacherByThidAndKdid(List list);

	/**
	 * <li>��������������kuid�б����wtkuser
	 * 
	 * @author XiaoMa
	 * @param kuid
	 * @return wtkuser
	 */
	public WkTUser findByUserByKuid(Long kuid);

	/**
	 * @author <li>�������������ݽ�ʦ��š���֯���ű�Ų��ҽ�ʦ
	 * @param i ��ʦ���
	 * @param d ��֯���ű��
	 * @return ��ʦʵ��
	 */
	public Teacher fingTeacherByTidKdid(String i, Long d);

	/**
	 * ���ݲ����б��������в��ŵĽ�ʦ��Ϣ
	 * 
	 * @param deplist �����б�
	 * @param buid ���赥λ���
	 * @param rbatch ������α��
	 * @return ��ʦ�����б�
	 * @author DATIAN
	 */
	public List findBydeplistNotInReply(List deplist, Long buid, Short rbatch);

	/**
	 * ���ݲ����б���ʦ���ƺͱ�������������ʦ��¼
	 * @param deplist �����б�
	 * @param tname ��ʦ����
	 * @param tno ��ʦ���
	 * @return
	 */
	public List findBydeplistNotInGhuserdept(List deplist, String tname, String to, Long rid, Long kdid);

	public List findBydeplistAndrid(List deplist, String tname, String tno);

	public List findBydeplistNotInYjsReply(List deplist, Long ypid, Long kdid, Integer grade);

	public List findBydeplistAndridAndGradeAndKdid(List deplist, String tname, String tno, Integer grade, Long kdid, Long ypid);

	public List findNoFxfzrTeacher(List deplist, Long rid, String tname, String to);

	public List findBykdidAndnotinUserdept(Long kdid);

	public List findBykridAndnotinpsrelation(Long kuid, Long wpid, List deptlist, Long rid, String tno, String tname);

	/**
	 * ��ѯδ��ӵ����о���ָ��ֽ�Ľ�ʦ
	 * @param year
	 * @param deplist
	 * @param krid
	 * @param thid
	 * @param name
	 * @return
	 */
	public List<Zbteacher> findNoByYear(Integer year, List deplist, Long krid, String thid, String name);

	public List findBydeplistAndrole(List deplist, Long rid, Long kdid, Long nrid, String thid, String name);
	public List findBytiId(String tiId) ;
}
