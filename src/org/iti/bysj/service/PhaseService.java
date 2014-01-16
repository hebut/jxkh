package org.iti.bysj.service;


import java.util.List;


import com.uniwin.basehs.service.BaseService;

public interface PhaseService extends BaseService {

	/**
	 * <li>功能描述：根据毕设单位编号和顺序号查找阶段
	 * @param buid 毕设单位编号
	 * @param order 顺序号
	 * @return 阶段列表
	 */
	List findByBuIdAndOrder(Long buid,int order);
	
	/**
	 * <li>功能描述：根据毕设批次编号和顺序号查找阶段
	 * @param bbid 毕设批次编号
	 * @param order 顺序号
	 * @return 阶段列表
	 */
	List findByBbIdAndOrder(Long bbid,int order);
	
	/**
	 * <li>功能描述：根据毕设批次编号查找阶段
	 * @param bbid 毕设单位编号
	 * @return 阶段列表
	 */
	
	List findByBbId(Long bbid);
	
	List findByBuId(Long buid);
    List findByBbIdHaveRc(Long bbid);
	
	List findByBuIdHaveRc(Long buid);


	/**
	 * <li>功能描述：根据毕设单位编号和批次编号查找阶段
	 * @author DATIAN
	 * @param buId 毕设单位编号
	 * @param bbId 批次编号
	 * @return 阶段列表
	 */
	List findByBuIdAndBbId(Long buId,Long bbId);
	/**
	 * <li>功能描述：根据毕设单位编号和顺序号查找最大结束时间
	 * @author DATIAN
	 * @param buId 毕设单位编号
	 * @param bbId 批次编号
	 * @return 阶段列表
	 */
	Long getMaxEndAndBuidAndOder(Long buId,int order);
	/**
	 * <li>功能描述：根据毕设单位编号和顺序号查找最小开始时间
	 * @author DATIAN
	 * @param buId 毕设单位编号
	 * @param bbId 批次编号
	 * @return 阶段列表
	 */
	Long getMinStartAndBuidAndOder(Long buId,int order);
	/**
	 * <li>功能描述：根据用户编号和时间，查找该用户为专业的角色时在该时间所处的阶段
	 * @author lys
	 * @param kuId 毕设单位编号
	 * @param time  时间
	 * @return 阶段列表
	 */
	List findByKuidAndTime(Long kuid,Long time);
	/**
	 * <li>功能描述：根据用户编号和时间，查找该用户为专业或者学院的角色时在该时间所处的阶段
	 * @author lys
	 * @param kuId 毕设单位编号
	 * @param time  时间
	 * @return 阶段列表
	 */
	List findByKuidAndTime2(Long kuid,Long time);
	/**
	 * <li>功能描述：根据用户编号和时间，查找该用户为专业或者学院或者学校的角色时在该时间所处的阶段
	 * @author lys
	 * @param kuId 毕设单位编号
	 * @param time  时间
	 * @return 阶段列表
	 */
	List findByKuidAndTime3(Long kuid,Long time);
	/**
	 * 
	 * @param kuid
	 * @param time
	 * @return
	 */
	List findByKuidAndNow(Long kuid,Long time);
	
}
