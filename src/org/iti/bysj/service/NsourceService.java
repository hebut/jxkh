package org.iti.bysj.service;

import java.util.List;

import org.iti.bysj.entity.BsNsource;

import com.uniwin.basehs.service.BaseService;

public interface NsourceService extends BaseService {

	/**
	 * <li>功能描述：根据毕设单位编号和类型查询课题性质或者来源列表
	 * @param buid 毕设单位编号
	 * @param type 性质或开源的标志
	 * @return 性质或来源列表
	 */
	public List findByBuid(Long buid,short type );
	public List findByBuId(Long buid);
	
	public BsNsource findByBnsid(Long bnsid);
}
