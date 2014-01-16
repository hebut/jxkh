package org.iti.gh.service;

import java.util.List;

import org.iti.gh.entity.GhYjfx;

import com.uniwin.basehs.service.BaseService;

public interface YjfxService extends BaseService {

	/**
	 * 通过学院编号查找所有的研究方向
	 * @param kuId
	 * @return 研究方向
	 */
	public List<GhYjfx> findByKuid(Long kuId);
	List<GhYjfx> findByKdid(Long kdId);
	List<GhYjfx> findByKdidAndGyname(Long kdid,String gyname);
	List<GhYjfx> findByKdidAndGynameAndNotGyid(Long kdid,String gyname,Long gyid);
	
	
	
}
