package com.uniwin.framework.service;

import java.util.List;

import com.uniwin.basehs.service.BaseService;
import com.uniwin.framework.entity.WkTMlog;
import com.uniwin.framework.entity.WkTUser;

public interface MLogService extends BaseService {
	public List<WkTMlog> findLogsByTimes(String btime, String etime);

	/**
	 * <li>������������������¼
	 * 
	 * @param func
	 *            ����ģ��
	 * @param desc
	 *            ����ժҪ
	 * @param douser
	 *            ��ǰ�������û� void
	 * @author DaLei
	 */
	public void saveMLog(String func, String desc, WkTUser douser);
}
