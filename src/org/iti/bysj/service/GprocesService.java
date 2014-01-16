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
	 * <li>功能描述：获得用户参与的毕业设计过程列表
	 * @param kuid用户编号
	 * @return
	 * List 
	 * @author DaLei
	 */
	public List findByUser(Long kuid);
	/**
	 * <li>功能描述：根据学校编号查询该学校所属的所有毕设过程
	 * @param kdId 学校（单位组织）编号
	 * @return List 毕设过程列表
	 * @author FengXinhong
	 */
	public List findByKdId(Long kdId);
	/**
	 * <li>功能描述：根据学校编号和届数查询该学校所属的所有毕设过程
	 * @param kdId 学校（单位组织）编号
	 * @param sgrade 届数
	 * @return List 毕设过程列表
	 * @author lys
	 */
	public List findByKdIdAndSgrade(Long kdId,Integer sgrade);
}
