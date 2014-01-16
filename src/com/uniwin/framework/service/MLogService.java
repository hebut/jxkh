package com.uniwin.framework.service;

import java.util.List;

import com.uniwin.basehs.service.BaseService;
import com.uniwin.framework.entity.WkTMlog;
import com.uniwin.framework.entity.WkTUser;

public interface MLogService extends BaseService {
	public List<WkTMlog> findLogsByTimes(String btime, String etime);

	/**
	 * <li>功能描述：保存管理记录
	 * 
	 * @param func
	 *            功能模块
	 * @param desc
	 *            操作摘要
	 * @param douser
	 *            当前操作的用户 void
	 * @author DaLei
	 */
	public void saveMLog(String func, String desc, WkTUser douser);
}
