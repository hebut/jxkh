package org.iti.jxkh.service;

import java.util.List;

import org.iti.jxkh.entity.JXKH_JFRESULT;
import com.uniwin.basehs.service.BaseService;
public interface JxkhJfResultService  extends BaseService {
	/**
	 * @author WXY
	 */
	/**
	 * ͨ����ݲ����ܻ��ּ�¼����
	 * @param year
	 */
	public List<JXKH_JFRESULT>findJfByYear(String year);
	public int findJfResult(String year);
}
