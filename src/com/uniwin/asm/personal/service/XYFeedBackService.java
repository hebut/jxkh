package com.uniwin.asm.personal.service;

import java.util.List;

import com.uniwin.asm.personal.entity.XYFeedBackReply;
import com.uniwin.basehs.service.BaseService;

public interface XYFeedBackService extends BaseService {
	/**
	 * 根据用户编号查找其发布的反馈记录
	 * 
	 * @param kuid
	 * @return 反馈记录
	 * @author DATIAN
	 */
	List findByKuid(Long kuid);

	/**
	 * 获得所有反馈记录，按照回复与否和时间进行排序
	 * 
	 * @return 反馈记录
	 * @author DATIAN
	 */
	List getAll();

	/**
	 * 根据反馈编号获得所有反馈答复记录
	 * 
	 * @return 反馈记录
	 * @author DATIAN
	 */
	List findByFbid(Long fbId);

	/**
	 * 根据反馈编号和答复人的编号获得反馈答复记录
	 * 
	 * @return 反馈记录
	 * @author DATIAN
	 */
	XYFeedBackReply findByFbidAndKuid(Long fbId, Long kuId);
}
