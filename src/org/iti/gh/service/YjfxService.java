package org.iti.gh.service;

import java.util.List;

import org.iti.gh.entity.GhYjfx;

import com.uniwin.basehs.service.BaseService;

public interface YjfxService extends BaseService {

	/**
	 * ͨ��ѧԺ��Ų������е��о�����
	 * @param kuId
	 * @return �о�����
	 */
	public List<GhYjfx> findByKuid(Long kuId);
	List<GhYjfx> findByKdid(Long kdId);
	List<GhYjfx> findByKdidAndGyname(Long kdid,String gyname);
	List<GhYjfx> findByKdidAndGynameAndNotGyid(Long kdid,String gyname,Long gyid);
	
	
	
}
