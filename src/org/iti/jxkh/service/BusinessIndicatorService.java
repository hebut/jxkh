package org.iti.jxkh.service;

import java.util.List;

import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.entity.Jxkh_IndicatorHistory;
import com.uniwin.basehs.service.BaseService;

public interface BusinessIndicatorService extends BaseService {
	/**
	 * 
	 * @param ptid
	 *            父指标ID
	 * @return 所有子指标
	 */
	public List<Jxkh_BusinessIndicator> getChildBusiness(Long ptid);
	
	public List<Jxkh_BusinessIndicator> getUseChild(Long ptid);
	
	public List<Jxkh_BusinessIndicator> getChild(Long ptid,Long tid);
	/**
	 * 查找出当前所有的指标
	 * @return
	 */
	public List<Jxkh_BusinessIndicator> findAll();
	/**
	 * 找出所有的一级指标
	 * @return
	 */
	public List<Jxkh_BusinessIndicator> findFirstIndicator();
	/**
	 * 根据pid找出相应的父指标
	 * @param pid
	 * @return
	 */
	public List<Jxkh_BusinessIndicator> findIndicator(Long pid);
	/**
	 * 根据id查找所对应的指标
	 * @param kbId
	 * @return
	 */
	public List<Jxkh_BusinessIndicator> findById(Long kbId);
	 
	public List<Jxkh_IndicatorHistory> findByid(Long kbId);
//	/**
//	 * 根据查找当前的指标
//	 * @param jihTime
//	 * @return
//	 */
//	public List<Jxkh_BusinessIndicator> findNowByTime(String jihTime);
//	public List<Jxkh_BusinessIndicator> findNowFirstByTime(String jihTime);
//	public List<Jxkh_BusinessIndicator> findNowSecondByTime(String jihTime,Long pid);
//	public List<Jxkh_BusinessIndicator> findNowThirdByTime(String jihTime,Long pid);
	/**
	 * 根据更改时间查找对应的指标历史
	 * @param jihTime
	 * @return
	 */
	public List<Jxkh_IndicatorHistory> findIndicatorByTime(String jihTime);
	/**
	 * 根据更改时间查找对应的一级指标历史
	 * @param jihTime
	 * @return
	 */
	public List<Jxkh_IndicatorHistory> findHistoryByTimeAndPid(String jihTime,Long pid);
//	/**
//	 * 根据更改时间查找对应的二级历史
//	 * @param jihTime
//	 * @return
//	 */
//	public List<Jxkh_IndicatorHistory> findSecondByTime(String jihTime,Long pid);
//	/**
//	 * 根据更改时间查找对应的三级指标历史
//	 * @param jihTime
//	 * @return
//	 */
//	public List<Jxkh_IndicatorHistory> findThirdByTime(String jihTime,Long pid);	


	/**
	 * 根据名称获得实体
	 * 
	 * @param name
	 * @return
	 */
	public Jxkh_BusinessIndicator getEntityByName(String name);
	
	public Jxkh_BusinessIndicator findBykbValue(String kbvalue); 
}
