package org.iti.gh.service;

import java.util.List;

import org.iti.gh.entity.GhFmzl;

import com.uniwin.basehs.service.BaseService;

public interface FmzlService extends BaseService {
	/**
	 * 根据用户的编号来查找科研发明专利
	 * @param kuId
	 * @return
	 */
	List findByKuid(Long kuId);
	/**
	 * 根据单位部门编号查找该单位部门教师科研科研发明专利
	 * @param kdid
	 * @return
	 */
	public List findByKdId(long kdid);
	/**
	 * 根据单位部门编号查找该单位部门教师科研科研发明专利，同类项目合并
	 * @param kdid
	 * @return
	 */
	public List findSumKdId(long kdid,Short state);
	/**
	 * 根据名称查找教师和教师贡献
	 * @param name
	 * @param lx
	 * @return
	 */
	public List findByMc(String name);
	
	/**
	 * 根据用户id和姓名查找可能与该用户有关的专利
	 * @param kuid
	 * @param name
	 * @return
	 */
	public List findByKuidAndUname(Long kuid,String name);
	
	/**
	 * <li>功能描述：根据kdid，审核状态查询发明专利
	 * @param kdid
	 * @param state
	 * @return
	 */
	public List findByKdidAndState(Long kdid,Short state);
	
	/**
	 * 判重
	 * @param fmzl
	 * @param mc
	 * @param zlh
	 * @return
	 */
	public boolean CheckOnlyOne(GhFmzl fmzl,String mc,String zlh);
	/**
	 * 根据发明名称和发明人模糊查询专利
	 * @param fmmc
	 * @param uname
	 * @return
	 */
	public List findByFmmcAndFmrname(String fmmc,String uname,Long kuid);
	/**
	 * 
	 * @param fmmc
	 * @return
	 */
	public GhFmzl finByFmmc(String fmmc);
	/**
	 * 
	 * @param year
	 * @param kuid
	 * @return
	 */
	  public List findBykdidAndYearAndKuid(String year,Long kuid,String type);
	  
	  /**
	   * 根据用户ID和用户排名查询符合条件的记录总数
	   * @param kuid
	   * @param selfs
	   * @return
	   */
	 public int getCountBykuidSelfs(Long kuid, int selfs);
	 
	 public List<GhFmzl> findByFmIds(String ids);
}
