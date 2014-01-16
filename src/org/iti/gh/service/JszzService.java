package org.iti.gh.service;

 

import java.util.List;

import org.iti.gh.entity.GhJszz;

import com.uniwin.basehs.service.BaseService;

public interface JszzService extends BaseService {

	/**
	 * <li>功能描述：根据项目id，用户id和论文种类（如会议论文，期刊论文等）
	 * @param kuid
	 * @param lwid
	 * @param type
	 * @return
	 */
	public GhJszz findByKuidAndLwidAndType(Long kuid,Long zzid,Short type);
	
	/**
	 * 
	 * @param zzid
	 * @param type
	 * @return
	 */
	public List<Long> findByLwidAndType(Long zzid,Short type);
	
	/**
	 * <li>功能描述：根据用户ID、著作种类、本人署名情况查询符合条件的记录总数，
	 * @param kuid 用户ID
	 * @param type 著作种类
	 * @param selfs 本人署名情况
	 * @return
	 */
	public List<Long> findCountByKuidAndType(Long kuid,Short type, String selfs);

	/**
	 * <li>功能描述：根据著作种类分类统计指定用户所编写的字数总数
	 * @param kuid 用户ID
	 * @param type 著作种类
	 * @return
	 */
	public int getWordsByKuidType(Long kuid,Short type);
}
