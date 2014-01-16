package org.iti.gh.service;

import java.util.List;

import com.uniwin.basehs.service.BaseService;

public interface LwzlService extends BaseService {

	/**
	 * 根据用户的编号来查找其所有的已获得科研论文、科研著作、教研论文、教材
	 * @param kuId
	 * @return
	 */
	List findByKuid(Long kuId,Short lx);
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
	public List findSumKdId(long kdid,Short lx, Short type,Short state);
	/**
	 * 根据名称查找该单位部门教师科研或者教研情况
	 * @param kdid
	 * @return
	 */
	public List findByMc(String name,Short lx);


	/**
	 * 根据用户的编号来查找其所有的已获得科研论文、科研著作、教研论文、教材
	 * @param kuId
	 * @return
	 */
	List findByKuidAndType(Long kuId,Short lx,Short type,Short jslwtyype);
	
/**
 * 根据用户的编号、用户名称，论文种类（1科研论文 2 科研专著 3 教学论文 4 教材），论文性质（0-期刊论文，1-会议论文）），教师论文类型（1科研会议，
 * 2科研期刊，3 教研会议，4教研期刊）
 * @param kuId
 * @param kuname
 * @param lx
 * @param type
 * @param jslwtyype
 * @return
 */
	List findAllname(Long kuId,String kuname,Short lx,Short type,Short jslwtyype);
	
	/**
	 * <li>功能描述：根据学院id，论文类型（教研和科研），审核状态查询论文
	 * @param kdid
	 * @param lx
	 * @param state
	 * @return
	 */
	public List findByKdidAndLxAndState(Long kdid,Short lx,Short type,Short state);
}
