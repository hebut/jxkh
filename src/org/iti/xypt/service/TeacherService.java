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
	 * <li>通过教师编号和学校编号查找教师
	 * 
	 * @author Chen Li
	 * @param thid
	 * @param sid
	 * @return 教师 2010-7-11
	 */
	public List FindTeacherByTidAndKdid(String thid, Long sid);

	/**
	 * 
	 * <li>通过教师编号和学校编号查找教师
	 * 
	 * @author XiaoMa
	 * @param thid
	 * @param sid
	 * @return 教师 20100909
	 */
	public List FindTeacherByKuidAndKdid(Long kuid, Long sid);

	/**
	 * 
	 * <li>通过教师编号查找教师
	 * 
	 * @author Chen Li
	 * @param thid
	 * @param sid
	 * @return 教师 2010-7-11
	 */
	public List FindTeacherByThid(String thid);

	/**
	 * 
	 * <li>通过kdid查找组织单位名称
	 * 
	 * @author XiaoMa
	 * @param kdid
	 * @return wktuser
	 */
	public WkTDept findWktuserByKdid(Long kdid);

	/**
	 * 
	 * <li>通过kuname查找kuid
	 * 
	 * @author XiaoMa
	 * @param kdid
	 * @return wktuser
	 */
	public WkTUser findUserByKuname(String kuname);

	/**
	 * <li>功能描述：根据用户编号查找教师
	 * 
	 * @param kuId
	 * @return
	 */
	public Teacher findBtKuid(Long kuId);

	/**
	 * 
	 * <li>通过登录号查找教师编号
	 * 
	 * @author Li Wei
	 * @param kuid
	 * @return 教师编号 2010-7-12
	 */
	public String FindTeacherByKuid(Long kuid);

	/**
	 * 
	 * <li>通过用户编号查找教师
	 * 
	 * @author zhangxue
	 * @param kuid
	 * @return 教师 2010-7-12
	 */
	public Teacher findBykuid(Long kuid);

	/**
	 * 
	 * <li>通过组织单位列表和角色编号查找教师
	 * 
	 * @author zhangxue
	 * @param kuid
	 * @return 教师 2010-7-12
	 */
	public List findBydeplistAndrid(List deplist, Long rid);

	/**
	 * 
	 * <li>通过组织单位列表和角色编号查找教师
	 * 
	 * @author zhangxue
	 * @param kuid
	 * @return 教师 2010-7-12
	 */
	public List findBydeplistAndrid(List deplist, Long rid, String tname, String tno);

	/**
	 * <li>功能描述：查询某些部门具有某角色的用户列表，用户不能具有norid角色。
	 * 
	 * @param deplist
	 * @param rid
	 * @param norid
	 * @return List
	 * @author DaLei
	 */
	public List findBydeplistAndrid(List deplist, Long rid, Long norid);

	/**
	 * <li>功能描述：查询某些部门具有某角色的用户列表，用户不能具有norid角色。
	 * 
	 * @param deplist
	 * @param rid
	 * @param norid
	 * @return List
	 * @author DaLei
	 */
	public List findBydeplistAndrid(List deplist, Long rid, Long norid, String tname, String to);

	/**
	 * 查询某些部门中不是buid毕设单位指导教师的教师列表
	 * 
	 * @param deplist 部门列表
	 * @param rid 教师角色编号
	 * @param buid 毕设单位编号
	 * @return
	 */
	public List findNoBsTeacher(List deplist, Long rid, Long buid, String tname, String to);

	public List findNoYjsTeacherAndKdid(List deplist, Long rid, Long kdid, String tname, String to);

	public List findNoCqTeacherAndSchid(List deplist, Long rid, Long schid, String tname, String to);

	/**
	 * 查询某些部门中某个学生的指导教师也不是该学生的同行评阅教师的教师列表
	 * 
	 * @param deplist 部门列表
	 * @param rid 教师角色编号
	 * @param kuid 指导教师的用户编号
	 * @param bdbId 学生所在双向关系编号
	 * @return
	 */
	public List findNoBsPeerview(List deplist, Long rid, Long kuid, Long bdbId);

	public List findNoBsPeerview(List deplist, Long rid, Long kuid, Long bdbId, String tname, String tno);

	/**
	 * 查询某学校的教师号的教师
	 * 
	 * @param thid
	 * @param kdid
	 * @return
	 */
	public List findByThidAndKdid(String thid, Long kdid);

	public Teacher findByThid(String thid);

	/**
	 * <li>功能描述：根据教师编号、组织单位编号列表查找教师
	 * 
	 * @author zhangli
	 * @param thid
	 * @param kdid
	 * @return 教师实体列表
	 */
	public List findTeacherByThidAndKdid(List list);

	/**
	 * <li>功能描述：根据kuid列表查找wtkuser
	 * 
	 * @author XiaoMa
	 * @param kuid
	 * @return wtkuser
	 */
	public WkTUser findByUserByKuid(Long kuid);

	/**
	 * @author <li>功能描述：根据教师编号、组织部门编号查找教师
	 * @param i 教师编号
	 * @param d 组织部门编号
	 * @return 教师实体
	 */
	public Teacher fingTeacherByTidKdid(String i, Long d);

	/**
	 * 根据部门列表，返回所有部门的教师信息
	 * 
	 * @param deplist 部门列表
	 * @param buid 毕设单位编号
	 * @param rbatch 答辩批次编号
	 * @return 教师对象列表
	 * @author DATIAN
	 */
	public List findBydeplistNotInReply(List deplist, Long buid, Short rbatch);

	/**
	 * 根据部门列表，教师名称和编号来查找这个教师记录
	 * @param deplist 部门列表
	 * @param tname 教师名称
	 * @param tno 教师编号
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
	 * 查询未添加到科研经费指标分解的教师
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
