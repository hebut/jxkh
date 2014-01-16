package org.iti.bysj.service;

import java.util.List;

import org.iti.bysj.entity.BsTeacher;
import com.uniwin.basehs.service.BaseService;

/**
 * @author FengXinhong
 * 2010-07-08
 */
public interface BsTeacherService extends BaseService {
	/**
	 * <li>功能描述:根据毕设单位编号查询该毕设单位的所有指导教师 
	 * @param buid 毕设单位编号
	 * @return 指导教师列表
	 * @author FengXinhong
     * 2010-07-08
	 */

	List findByBuid(Long buid);
	/**
	 * <li>功能描述:根据同行评阅编号、学生编号查找该学生课题的非同行评阅教师
	 * @param Kuid 同行评阅人编号
	 * @param bsid 学生编号
	 * @return 教师列表
	 * @author FengXinhong
	 * 2010-07-08
	 */
	List findByKuidAndBsid(Long Kuid,Long bsid);
//	/**
//	 * <li>功能描述:根据批次编号查找未带满学生的教师
//	 * @param BbId 批次编号
//	 * @return 教师列表
//	 * @author FengXinhong
//	 * 2010-07-08
//	 */
//	List findByBbId(Long BbId);
	
	/**
	 * <li>功能描述:根据用户编号、毕设过程编号查找指导教师
	 * @param kuid 用户编号
	 * @param gpid 毕设过程编号
	 * @return 指导教师列表
	 * @author FengXinhong
	 * 2010-07-08
	 */
	public List findByKuidAndGpid(Long kuid,Long gpid);
	/**
	 * <li>功能描述：根据职称级别和毕设单位查询教师列表
	 * @param title 职称级别
	 * @param buid 毕设单位
	 * @return 教师列表
	 * @author FengXinhong
	 * 2010-07-08
	 */
	List findByTitleAndBuid(int title,Long buid);
	/**
	 *  <li>功能描述：根据答辩分组编号，查找该组教师
	 * @param brpid 答辩分组编号
	 * @return 教师列表
	 * @author FengXinhong
	 */

	List findByBrpid (Long brpid);
	
	/**
	 * 查询毕设教师功能
	 * @param buid 毕设单位
	 * @param name 教师姓名
	 * @param tno  教师号
	 * @param kdid 部门编号
	 * @return
	 */
	List findByBuidAndNameAndTno(Long buid,String name,String tno,Long kdid);
	/**
	 * 查询毕设教师功能
	 * @param btid 教师编号
	 * @return
	 * ZM
	 */
	BsTeacher findBybtid(Long btId);
	/**
	 * 根据用户编号和毕设单位编号来查找指导教师
	 * @param kuid 用户编号
	 * @param buid 毕设单位编号
	 * @return BsTeacher
	 * zx
	 */
	BsTeacher findByKuidAndBuid(long kuid,long buid);
	
	/**
	 * 根据用户编号查询毕设教师，以此来判断
	 * @param kuid 用户编号
	 * @param kdid 学校编号
	 * @return
	 */
	public List findBsTeacher(Long kuid,Long kdid);
	/**
	 * 根据毕设过程编号以及部门编号查找毕设教师的kuid
	 * @param bgid 毕设过程编号
	 * @param kdpid 上级部门编号
	 * @return
	 * lly
	 */
	List findKuidByBgidAndkdpid(Long bgid,Long kdpid);
	/**
	 * 根据毕设过程编号以及部门编号查找毕设教师
	 * @param bgid
	 * @param kdpid
	 * @param kuid
	 * @return
	 * lly 
	 */
	List findBybgidandkdidandkuid(Long bgid,Long kdpid,Long kuid);
	/**
	 * 
	 * @param kuid
	 * @return
	 */
	public List findByKuid(Long kuid);
}
