package org.iti.jxgl.service;

import java.util.List;

import org.iti.jxgl.entity.JxYear;
import com.uniwin.basehs.service.BaseService;

public interface YearService extends BaseService {
	/**
	 * <li>������������ȡ���е����
	 * 
	 * @return ����б�
	 */
	public List getAllYear();

	/**
	 * <li>������������ȡ���е��꼶
	 * 
	 * @return �꼶�б�
	 */
	public List getAllGrade();
}
