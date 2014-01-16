package org.iti.jxkh.service;

import java.util.List;

import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.entity.Jxkh_IndicatorHistory;
import com.uniwin.basehs.service.BaseService;

public interface BusinessIndicatorService extends BaseService {
	/**
	 * 
	 * @param ptid
	 *            ��ָ��ID
	 * @return ������ָ��
	 */
	public List<Jxkh_BusinessIndicator> getChildBusiness(Long ptid);
	
	public List<Jxkh_BusinessIndicator> getUseChild(Long ptid);
	
	public List<Jxkh_BusinessIndicator> getChild(Long ptid,Long tid);
	/**
	 * ���ҳ���ǰ���е�ָ��
	 * @return
	 */
	public List<Jxkh_BusinessIndicator> findAll();
	/**
	 * �ҳ����е�һ��ָ��
	 * @return
	 */
	public List<Jxkh_BusinessIndicator> findFirstIndicator();
	/**
	 * ����pid�ҳ���Ӧ�ĸ�ָ��
	 * @param pid
	 * @return
	 */
	public List<Jxkh_BusinessIndicator> findIndicator(Long pid);
	/**
	 * ����id��������Ӧ��ָ��
	 * @param kbId
	 * @return
	 */
	public List<Jxkh_BusinessIndicator> findById(Long kbId);
	 
	public List<Jxkh_IndicatorHistory> findByid(Long kbId);
//	/**
//	 * ���ݲ��ҵ�ǰ��ָ��
//	 * @param jihTime
//	 * @return
//	 */
//	public List<Jxkh_BusinessIndicator> findNowByTime(String jihTime);
//	public List<Jxkh_BusinessIndicator> findNowFirstByTime(String jihTime);
//	public List<Jxkh_BusinessIndicator> findNowSecondByTime(String jihTime,Long pid);
//	public List<Jxkh_BusinessIndicator> findNowThirdByTime(String jihTime,Long pid);
	/**
	 * ���ݸ���ʱ����Ҷ�Ӧ��ָ����ʷ
	 * @param jihTime
	 * @return
	 */
	public List<Jxkh_IndicatorHistory> findIndicatorByTime(String jihTime);
	/**
	 * ���ݸ���ʱ����Ҷ�Ӧ��һ��ָ����ʷ
	 * @param jihTime
	 * @return
	 */
	public List<Jxkh_IndicatorHistory> findHistoryByTimeAndPid(String jihTime,Long pid);
//	/**
//	 * ���ݸ���ʱ����Ҷ�Ӧ�Ķ�����ʷ
//	 * @param jihTime
//	 * @return
//	 */
//	public List<Jxkh_IndicatorHistory> findSecondByTime(String jihTime,Long pid);
//	/**
//	 * ���ݸ���ʱ����Ҷ�Ӧ������ָ����ʷ
//	 * @param jihTime
//	 * @return
//	 */
//	public List<Jxkh_IndicatorHistory> findThirdByTime(String jihTime,Long pid);	


	/**
	 * �������ƻ��ʵ��
	 * 
	 * @param name
	 * @return
	 */
	public Jxkh_BusinessIndicator getEntityByName(String name);
	
	public Jxkh_BusinessIndicator findBykbValue(String kbvalue); 
}
