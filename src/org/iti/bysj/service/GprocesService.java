/**
 * 
 */
package org.iti.bysj.service;

import java.util.List;

import com.uniwin.basehs.service.BaseService;

/**
 * @author DaLei
 * @version $Id: GprocesService.java,v 1.1 2011/08/31 07:03:02 ljb Exp $
 */
public interface GprocesService extends BaseService {

	/**
	 * <li>��������������û�����ı�ҵ��ƹ����б�
	 * @param kuid�û����
	 * @return
	 * List 
	 * @author DaLei
	 */
	public List findByUser(Long kuid);
	/**
	 * <li>��������������ѧУ��Ų�ѯ��ѧУ���������б������
	 * @param kdId ѧУ����λ��֯�����
	 * @return List ��������б�
	 * @author FengXinhong
	 */
	public List findByKdId(Long kdId);
	/**
	 * <li>��������������ѧУ��źͽ�����ѯ��ѧУ���������б������
	 * @param kdId ѧУ����λ��֯�����
	 * @param sgrade ����
	 * @return List ��������б�
	 * @author lys
	 */
	public List findByKdIdAndSgrade(Long kdId,Integer sgrade);
}
