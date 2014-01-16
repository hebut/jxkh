package org.iti.jxgl.service;

import java.util.List;

import org.iti.jxgl.entity.JxYear;
import com.uniwin.basehs.service.BaseService;

public interface YearService extends BaseService {
	/**
	 * <li>功能描述：获取所有的年份
	 * 
	 * @return 年份列表
	 */
	public List getAllYear();

	/**
	 * <li>功能描述：获取所有的年级
	 * 
	 * @return 年级列表
	 */
	public List getAllGrade();
}
