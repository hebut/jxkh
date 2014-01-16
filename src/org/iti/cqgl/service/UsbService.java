package org.iti.cqgl.service;

import org.iti.cqgl.entity.CqUsb;
import java.util.List;
import com.uniwin.basehs.service.BaseService;

public interface UsbService extends BaseService {
	List findBykuid(Long kuid);
	List findByCuidNotcuid(String cuId, String cuid);

	/**
	 * <li>功能描述：查询某一用户的U盘
	 * @param kuid
	 * @return
	 */
	public CqUsb findByKuid(Long kuid );
	
	/**
	 * <li>功能描述：查询某一用户的U盘
	 * @param kuid
	 * @param ip
	 * @return
	 */
	public boolean checkIfExsit(Long kuid,String ip);
	
	/**
	 * <li>功能描述：查询某一用户的U盘
	 * @param kuid
	 * @param ip
	 * @return
	 */
	public List findBykuIdAndIp(Long kuid,String ip);
	public List findkuIdAndIp(Long kuid,String ip);
}
