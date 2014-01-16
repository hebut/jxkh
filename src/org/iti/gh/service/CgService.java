package org.iti.gh.service;

import java.util.List;

import org.iti.gh.entity.GhCg;

import com.uniwin.basehs.service.BaseService;

public interface CgService extends BaseService {

	/**
	 * 通过用户编号查找所有的获奖科研或教研成果
	 * @param kuId
	 * @return 获奖科研成果
	 */
	List findByKuid(Long kuId,Short lx,Integer type);
	/**
	 * 根据单位部门编号查找该单位部门教师科研或者教研情况
	 * @param kdid
	 * @return
	 */
	public List findByKdId(long kdid,Short lx);
	/**
	 * 根据单位部门编号查找该单位部门教师科研或者教研情况，同类项目合并
	 * @param kdid
	 * @return
	 */
	public List findSumKdId(long kdid,Short lx,Short state);
	/**
	 * 根据名称查找教师和教师贡献
	 * @param name
	 * @param lx
	 * @return
	 */
	public List findByMc(String name,Short lx);
	/**
	 * 根据项目编号查看教研或科研获奖
	 * @param kynumber
	 * @param hlj
	 * @return
	 */
	public List findByKyNumAndKuId(String kynumber,Long kuId);
	/**
	 * 根据项目号查看教研或科研获奖
	 * @param kyxmid
	 * @param hlj
	 * @return
	 */
	public List findByKyXmidAndKuId(Long kyxmid,Long kuId);
	
	/**
	 * <li>功能描述：根据项目id查询获奖记录
	 * @param kyxmid
	 * @return
	 */
	public List findByKyXmid(Long kyxmid);
	
	/**
	 * 查询包含该用户的获奖成果
	 * @param kuid
	 * @param uname
	 * @param lx
	 * @param type
	 * @return
	 */
	public List findByKuidAndKunameAndLxAndType(Long kuid,String uname,Short lx,Integer type);
	/**
	 * 查询获奖成果
	 * @param kdid
	 * @param lx
	 * @param state
	 * @return
	 */
	public List findByKdidAndLxAndState(Long kdid,Short lx,Short state);
	
	/**
	 * 判重返回true，已经有，返回false，还没有
	 * @param name
	 * @param lx
	 * @param Dj
	 * @return
	 */
	public boolean CheckByNameAndLxAndDj(GhCg cg,String name,Short lx,String ly,String Dj);

	/**
	 * 模糊查询
	 * @param name
	 * @param lx
	 * @param ly
	 * @return
	 */
	public List findByNameAndLxAndDj(String name, Short lx, String ly,Long kuid,Integer type);
	/**
	 *精确查找
	 * @param name
	 * @param lx
	 * @param ly
	 * @return
	 */
	public GhCg  findByNameAndLxAndly(String name, Short lx, String ly);
	
	/**
	 * 
	 * @param kdid
	 * @param year
	 * @param kuid
	 * @return
	 */
	public List findByKdidAndYearAndKuid(String year,Long kuid,Integer type,Short jb);
	/**
	 * 
	 * @param kdid
	 * @param year
	 * @param kuid
	 * @param type
	 * @param jb
	 * @param cjmc
	 * @return
	 */
	public List findBykdidAndYearAndKuidAndCgmcAndType(Long kdid, String year, Long kuid,Integer type,Short jb,String cjmc);
	
	
	/**
	 * 统计获奖成果
	 * @param kuId
	 * @return 获奖科研成果
	 */
	public List findCountByKuidAndCgkyAndHjky(Long kuId,Short lx,Integer type,Short fruitLevel);
	
	
	public List findByYearAndKuidAndCgmcAndType(String year, Long kuid,Integer type,Short jb);
	
	/**
	 * 根据成果ID查询列表
	 * @param ids
	 * @return
	 */
	public List<GhCg> findByKyIds(String ids);
}
