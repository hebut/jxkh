package org.iti.bysj.service;

import java.util.List;

import com.uniwin.basehs.service.BaseService;

public interface CheckService extends BaseService{

	
	/**
	 * 根据发布人的编号和角色编号和学生编号和子阶段编号来查询单项检查结果
	 * @param kuid 发布人的编号
	 * @param krid 发布人的角色编号
	 * @param bsid 学生编号
	 * @param bcpid 子阶段编号
	 * @return 单项检查结果
	 * @author DATIAN
	 */
	List findByKuidAndKridAndBsidAndBcpid(Long kuid,Long krid,Long bsid,Long bcpid);

	
	
	/**
	 * 根据发布人的编号和角色编号和检查子阶段编号来查询单项检查结果
	 * @param kuid 发布人的编号
	 * @param krid 发布人的角色编号
	 * @param bcpid 检查子阶段编号
	 * @return 单项检查结果
	 * @author DATIAN
	 */
	List findByKuidAndKridAndBcpid(Long kuid,Long krid,Long bcpid);
	
	
	/**
	 * 根据发布人的编号其发布的单项检查结果
	 * @param kuid 发布人的编号
	 * @return 单项检查结果
	 * @author DATIAN
	 */
//	List findByKuid(Long kuid);
	
	
	/**
	 * 根据发布人的编号和角色编号和检查子阶段编号来查询单项检查结果
	 * @param krid 发布人的角色编号
	 * @param kuid 发布人的编号
	 * @param bcpid 检查子阶段编号
	 * @param ifck 是否进行过综合检查
	 * @return 单项检查结果
	 * @author DATIAN
	 */
	List findByKridAndKuidAndBcpidAndIfck(Long krid,Long kuid,Long bcpid,Short ifck);
	
	/**
	 * 根据角色编号和毕设单位编号和发布人的编号和状态位来查询单项检查结果
	 * @param kuid 发布人的编号
	 * @param krid 发布人的角色编号
	 * @param buid 毕设单位编号
	 * @param ifck 判断状态
	 * @return 单项检查结果
	 * @author DATIAN
	 */
	List findByKridAndBuidAndKuidAndIfck(Long krid,Long buid,Long kuid,Long ifck);
	
	
	/**
	 * 根据单项检查编号查找其检查单项检查结果对象
	 * @param bcsid 单项检查编号 
	 * @return 检查反馈对象列表
	 * @author DATIAN
	 */
	List findbyBcsidFromCS(Long bscid);
	
	
	
	/**
	 * 根据单项检查编号查找其检查反馈对象
	 * @param bcsid 单项检查编号 
	 * @return 检查反馈对象列表
	 * @author DATIAN
	 */
	List findByBcsid(Long bcsid);


	


	/**
	 * 根据单项检查编号和角色编号查找其检查反馈对象
	 * @param bcsid 单项检查编号
	 * @param krid  角色编号
	 * @return 检查反馈对象
	 * @author DATIAN
	 */
	List findByBcsidAndKrid(Long bcsid,Long krid);


	/**
	 * 根据角色编号和学生编号查找某个学生的检查反馈
	 * @param krid 角色编号
	 * @param bsid  学生编号
	 * @return 检查反馈对象
	 * @author DATIAN
	 */
	List findByKridAndBsid(Long krid,Long bsid);


	/**
	 * 根据角色编号和学生编号查找某个学生的检查反馈
	 * @param bsid  教师编号
	 * @param krid 角色编号
	 * @return 检查反馈对象
	 * @author DATIAN
	 */
	List findByBtidAndKrid(Long btid,Long krid);



	/**
	 * 根据角色编号和毕设单位编号查找某个领导的检查反馈
	 * @param krid 角色编号
	 * @param buid 毕设单位编号
	 * @return 检查反馈对象
	 * @author DATIAN
	 */
	List findByKridAndBuid(Long krid,Long buid);


	
	/**
	 * 根据部门编号和用户编号和毕设过程编号查找某个督导的综合反馈
	 * @param kdid 部门编号
	 * @param kuid 用户编号
	 * @param bgid 毕设过程编号
	 * @return 综合反馈对象
	 * @author DATIAN
	 */
	List findByKdidAndKuidAndBgidAndKrid(Long kdid,Long kuid,Long bgid,Long krid);



	/**
	 * 根据用户编号和毕设过程编号和角色编号查找某个督导的综合反馈
	 * @param kuid 用户编号
	 * @param bgid 毕设过程编号
	 * @param krid 用户角色编号
	 * @return 综合反馈对象
	 * @author DATIAN
	 */
	List findByKuidAndBgidAndKrid(Long kuid,Long bgid,Long krid);



	/**
	 * 根据部门编号和用户编号和毕设过程编号查找某个督导的综合反馈
	 * @param kuid 用户编号
	 * @param krid 用户角色编号
	 * @param bgid 毕设过程编号
	 * @return 综合反馈对象
	 * @author DATIAN
	 */
	List findByKdidAndKridAndBgid(Long kdid,Long krid,Long bgid);



	/**
	 * 根据用户编号查找其负责督导的学院
	 * @param kuid 用户编号
	 * @param kdid 学校编号
	 * @return 所负责的学院
	 * @author DATIAN
	 */
	List findKdidByKuidAndKdid(Long kuid ,Long kdid);
	


	/**
	 * 根据用户编号和角色编号来查找单项检查份数
	 * @param kuid 用户编号
	 * @param krid 角色编号
	 * @return 单项检查份数
	 * @author DATIAN
	 */
	Long countByKuidAndKrid(Long kuid,Long krid);



	/**
	 * 根据学院编号查找负责督导的督导
	 * @param kuid 用户编号
	 * @return 督导人员列表
	 * @author DATIAN
	 */
	List findByKdid(Long kdid);



	/**
	 * 根据毕设单位编号和用户角色编号来查找单项检查份数
	 * @param buid 毕设单位编号
	 * @param krid 角色编号
	 * @return 单项检查份数
	 * @author DATIAN
	 */
	Long countByBuidAndKrid(Long buid,Long krid);



	/**
	 * 根据学院编号和角色编号来查找综合检查份数
	 * @param kdid 毕设单位编号
	 * @param krid 角色编号
	 * @param bgid 毕设过程编号
	 * @return 综合检查份数
	 * @author DATIAN
	 */
	Long countByKdidAndKridAndBgid(Long kdid,Long krid,Long bgid);



	/**
	 * 根据用户编号和角色编号和来查找综合检查份数
	 * @param kuid 用户编号
	 * @param krid 角色编号
	 * @return 综合检查份数
	 * @author DATIAN
	 */
	Long countComByKuidAndKrid(Long kuid,Long krid);

	/**
	 * 根据子阶段编号和学生编号查找单项检查结果
	 * @param bcpid 子阶段编号
	 * @param bsid 学生编号
	 * @return 单项检查结果
	 * @author DATIAN
	 */



   List findByBcpidAndBsid(Long bcpid,Long bsid);


	/**
	 * 根据用户编号和角色编号和来查找单项检查结果
	 * @param kuid 用户编号
	 * @param krid 角色编号
	 * @return 单项检查
	 * @author DATIAN
	 */
	List findByKuidAndKrid(Long kuId, Long krId);


	/**
	 * 根据综合检查编号来查找综合检查阶段数目
	 * @param bccid 综合检查编号
	 * @return 综合检查阶段数目
	 * @author DATIAN
	 */
	List findByBccid(Long bccid);



	/**
	 * 根据用户编号和用户角色编号和毕设单位编号来查找单项检查份数
	 * @param kuid 用户编号
	 * @param krid 角色编号
	 * @param kdid 毕设单位列表
	 * @return 单项检查份数
	 * @author DATIAN
	 */
	List findByKuidAndKridAndKdid(Long kuId, Long krid, List deplist);



	/**
	 * 根据用户编号和用户角色编号和毕设过程编号和毕设单位列表来查找综合检查份数
	 * @param kuid 用户编号
	 * @param krid 角色编号
	 * @param kdid 毕设单位列表
	 * @return 综合检查份数
	 * @author DATIAN
	 */
	List findComByKuidAndKridAndKdidAndBgid(Long kuId, Long krid,
			List deplist,Long bgid);



}
