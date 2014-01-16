package org.iti.gh.service;

import java.util.List;

import com.uniwin.basehs.service.BaseService;

public interface QkdcService extends BaseService {

	List findByKuid(Long kuid);
	/**
	 * 根据用户编号查找情况调查表记录
	 * @param kuId
	 * @return
	 * @author DATIAN
	 */
	List findByKuid(Long kuId,String str);
	/**
	 * 删除该教师的所有记录
	 * @param kuId
	 */
	void deleteByUser(Long kuId);
	
	
	/**
	 * 查询某个单位下全部填写此表的教师
	 * @param kdid
	 * @return
	 */
	List getKuidByKdid(Long kdid);

}
