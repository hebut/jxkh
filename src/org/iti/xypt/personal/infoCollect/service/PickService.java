package org.iti.xypt.personal.infoCollect.service;

import java.util.List;

import org.iti.xypt.personal.infoCollect.entity.WkTPickreg;

import com.uniwin.basehs.service.BaseService;



public interface PickService extends BaseService{

	public List findpickReg(Long id);
	public WkTPickreg findByPidAndOrderId(Long pid,Long orderId);
}
