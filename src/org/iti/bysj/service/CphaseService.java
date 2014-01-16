package org.iti.bysj.service;

import java.util.List;

import org.iti.bysj.entity.BsCphase;

import com.uniwin.basehs.service.BaseService;

public interface CphaseService extends BaseService {

	/**
	 *<li>功能描述：根据毕设单位编号查找子阶段
	 * 
	 * @param BuId
	 *            毕设单位编号
	 * @return
	 */
	List findByBuId(Long BuId);

	/**
	 * *<li>功能描述：根据毕设批次编号查找子阶段
	 * 
	 * @param bbid
	 *            毕设批次编号
	 * @return 子阶段日程列表
	 */
	List findByBbId(Long bbid);

	/**
	 * <li>功能描述：根据课题编号查询子阶段列表
	 * 
	 * @param bprid
	 *            课题
	 * @return 该课题参与的子阶段列表
	 */
	List findByBprid(Long bprid);

	/**
	 * <li>功能描述：根据毕设单位编号、上传用户查询教师或学生提交文档子阶段
	 * 
	 * @param buid
	 *            毕设单位编号
	 * @param upuser
	 *            上传用户
	 * @return 子阶段列表
	 */
	List findByBuidAndUpuser(Long buid, short upuser);

	/**
	 * <li>功能描述：根据批次编号查询教师或学生提交文档子阶段
	 * 
	 * @param bbid
	 *            批次编号
	 * @param upuser
	 * @return
	 */
	List findByBbidAndUpuser(long bbid, short upuser);

	/**
	 * <li>功能描述：根据批次编号查询教师或学生提交文档子阶段
	 * 
	 * @param bbid
	 *            批次编号
	 * @param upuser
	 * @return
	 */
	List findByBuIdAndBbId(long buid, long bbid);

//	/**
//	 * <li>功能描述：查询当前处于哪个子阶段（未分批）
//	 */
//	Long findWhenNowNB(Long now, Long buid);
//
//	/**
//	 * <li>功能描述：查询当前处于哪个子阶段（分批）
//	 */
//	Long findWhenNowYB(Long now, Long bbid);
	public Long getMinStartAndBuid(Long buId);
	public Long getMaxEndAndBbid(Long bbId);
	public Long getMinStartAndBbid(Long bbId);
	public Long getMaxEndAndBuid(Long buId);
}
