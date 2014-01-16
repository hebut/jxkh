package org.iti.xypt.service;

import java.util.List;
import org.iti.xypt.entity.Yjs;
import com.uniwin.basehs.service.BaseService;

public interface YjsService extends BaseService {

	public List findBydeplistAndrid(List dlist, Long rid, Integer grade, String tname, String tno);

	public List findBydeplistAndrid(List dlist, Long rid, Integer grade, String tname, String tno, String isguofang);

	// public List findGrade(String kdid);
	public List findgradeByKdid(Long kdid);

	public List findBygradeAndkdidAndNameAndStidNotInDb(Integer grade, Long kdid, String name, String stid);

	public List findzy();

	/**
	 * <li>功能描述:根据用户id查询研究生对象
	 * @param kuid 用户id
	 * @return
	 */
	public Yjs findByKuid(Long kuid);

	/**
	 * 
	 * @param grade
	 * @param kdid
	 * @return
	 */
	public List findByGradeAndKdid(Integer grade, Long kdid);

	// 通过学校编号查找所有研究生届数
	public List findgradebykdid(Long kdid);

	/**
	 * <li>功能描述:根据专业id查询今年的研究生
	 * @param kdid 专业id
	 * @return
	 */
	public List findNotinDbByKdid(Long kdid, Integer grade);

	public List findInDbByKdid(Long kdid, Integer grade,Long kuid);
	/**
	 * 查询没有选择自己的双选
	 * @param kdid
	 * @param grade
	 * @param kuid
	 * @return
	 */
	public List findNoChoiceByKdidGradeAndKuid(Long kdid, Integer grade,Long kuid);
}
