package org.iti.gh.service;

import java.util.List;

import org.iti.gh.entity.GhJsps;

import com.uniwin.basehs.service.BaseService;

public interface JspsService extends BaseService {

	/**
	 * 根据用户ID查询用户的支撑申请信息列表
	 * @param kuid
	 * @return
	 */
	public List<GhJsps> findByKuid(Long kuid);
	
	/**
	 * 根据用户ID、申请年份获得申请信息
	 * @param kuid
	 * @param year
	 * @return
	 */
	public GhJsps getByKuidYear(Long kuid,Integer year);
	
	/**
	 * 根据年份查询所有提出职称评审申请的教师列表
	 * @param year
	 * @return
	 */
	public List<GhJsps> findByYear(Integer year);
	
	/**
	 * 根据年份分页查询所有提出职称评审申请的教师列表
	 * @param year
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<GhJsps> findByYearAndPage(Integer year,int pageNum,int pageSize);
}
