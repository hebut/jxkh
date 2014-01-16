package org.iti.bysj.service;

import java.util.List;

import org.iti.bysj.entity.BsGpunit;

import com.uniwin.basehs.service.BaseService;

/**
 * @author FengXinhong
 * 2010-07-08
 */
public interface GpunitService extends BaseService {

	/**
	 * <li>功能描述：根据毕设单位查找分批情况
	 * @param buid 毕设单位编号
	 * @return 是否分批
	 * @author FengXinhong
     * 2010-07-08
	 */	
	Short findByBuid(Long Buid);
	

	/**
	 * <li>功能描述：系编号及毕设过程编号查询毕设单位列表 。
	 * @param kdId  系编号
	 * @param gpid  毕设过程编号
	 * @return
	 * BsGpunit 
	 * @author DaLei
	 */
	public BsGpunit  findByKdidAndGpid(Long kdId,Long gpid);

	/**
	 * <li>功能描述：根据毕设单位编号查询一辩学生的分组方式
	 * @param buid 毕设单位编号
	 * @return 分组方式
	 * @author FengXinhong
     * 2010-07-08
	 */
	Short findPacketwayByBuid(Long buid);
	/**
	 * <li>功能描述：根据毕设单位查询二辩学生的分组方式
	 * @param buid 毕设单位编号
	 * @return 分组方式
	 * @author FengXinhong
     * 2010-07-08
	 */
	Short findSpacketByBuid(long buid);
	/**
	 * <li>功能描述:根据毕设单位查询答辩资格确认
	 * @param buid 毕设单位编号
	 * @return 
	 * @author FengXinhong
     * 2010-07-08
	 */
	Short findDbzgByBuid(long buid);
	
	/**
	 * <li>功能描述:根据指导教师编号查找毕设单位
	 * @param BtId 指导教师编号
	 * @return 
	 * @author FengXinhong
     * 2010-07-08
	 */
	List findByBtId(Long BtId);
	
	/**
	 * <li>功能描述:根据毕设过程编号查询毕设单位
	 * @param bgid 毕设过程编号
	 * @return 毕设单位列表
	 * @author FengXinhong
     * 2010-07-08
	 */
	List findByBgid(Long bgid);


	/**
	 * <li>功能描述：查询教师用户参与某个毕设过程的全部毕设单位列表
	 * @param kuid 教师用户的编号
	 * @param bgid 毕设过程编号
	 * @return
	 * List 
	 * @author DaLei
	 */
	public List findByKuidAndBgid(Long kuid,Long bgid);

	/**
	 * <li>功能描述：根据毕设单位，查询是否计算过成果成绩
	 * @param buid 毕设单位编号
	 * @return 
	 * @author FengXinhong
	 * 2010-07-08
	 */
	Short isUpdateFruit(Long buid);
	
	/**
	 * <li>功能描述：查询某学院下某毕设过程全部毕设单位列表。
	 * @param kdid 学院的编号
	 * @param bgid 毕设过程编号
	 * @return
	 * List  毕设单位列表
	 * @author DaLei
	 */
	List findByPkdidAndBgid(Long kdid,Long bgid);
	
	
	List findByPeerviewKuidAndBgid(Long kuid,Long bgid);
	/**
	 * 毕设单位数量
	 * @return
	 * * @author lly
	 */
	public Integer countBsGpunit(Long bgid);
	/**
	 * 毕设单位中最大人数设置为0的数量
	 * @return
	 * * @author lly
	 */
	public Integer countBUMAX(Long bgid);
}
