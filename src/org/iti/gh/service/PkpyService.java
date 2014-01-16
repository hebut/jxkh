package org.iti.gh.service;

import java.util.List;

import org.iti.gh.entity.GhPkpy;

import com.uniwin.basehs.service.BaseService;

public interface PkpyService extends BaseService {
	/**
	 * ͨ���û���Ų������еĻ�����ѡ�š�����������
	 * @param kuId
	 * @return ������ѡ�š�����������
	 */
	List findByKuid(Long kuId);
	 
	/**
	 * 
	 * @param kuId
	 * @param mc
	 * @return
	 */
	public GhPkpy findBykuidAndMc(Long kuId,String mc);

}
