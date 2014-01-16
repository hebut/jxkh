package org.iti.bysj.service;

import java.util.List;

import org.iti.bysj.entity.BsBatch;

import com.uniwin.basehs.service.BaseService;

public interface BatchService extends BaseService {

	/**
	 * <li>功能描述：根据毕设单位编号查找单位批次
	 * 
	 * @param BuId
	 *            毕设单位编号
	 * @return
	 */
	List findByBuId(Long BuId);

	/**
	 * <li>功能描述：根据学生编号查找批次
	 * 
	 * @param BsId
	 * @return
	 */
	BsBatch findByBsId(Long BsId);

	/**
	 * <li>功能描述：根据批次名称查找批次
	 * 
	 * @param Buid
	 * @param Bbname
	 * @return ZM
	 */
	BsBatch findByBuidAndBbname(Long Buid, String Bbname);

	/**
	 * <li>功能描述：根据批次名称查找批次
	 * 
	 * @param Buid
	 * @param Bbname
	 * @return ZM
	 */
	List findByBuidandBbname(Long Buid, String Bbname);
}
