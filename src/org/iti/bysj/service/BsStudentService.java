package org.iti.bysj.service;

import java.util.List;

import org.iti.bysj.entity.BsStudent;

import com.uniwin.basehs.service.BaseService;

public interface BsStudentService extends BaseService {

	/**
	 * <li>功能描述：根据毕设单位编号查询该毕设单位的所有毕设学生
	 * 
	 * @param buid
	 *            毕设单位编号
	 * @return 毕设学生列表
	 */
	public List findByBuid(Long buid);

	/**
	 * <li>功能描述：根据毕设单位编号、一辩资格审批状态查询学生列表
	 * 
	 * @param buid
	 *            毕设单位编号
	 * @param ispassone
	 *            一辩资格审批状态
	 * @return 毕设学生列表
	 */
	public List findByBuidAndBstispassone(Long buid, short ispassone);

	/**
	 * <li>功能描述：根据毕设单位编号、二辩资格审批状态查询学生列表
	 * 
	 * @param buid
	 *            毕设单位编号
	 * @param ispassone
	 *            二辩资格审批状态
	 * @return 毕设学生列表
	 */
	public List findByBuidAndBstispasstwo(Long buid, short ispasstwo);

	/**
	 * <li>功能描述：根据某个学生用户编号和毕设单位编号查询学生
	 * 
	 * @param bsid
	 *            学生用户编号
	 * @param buid
	 * @return
	 */
	public BsStudent findByKuidAndBuid(Long kuid, Long buid);
	/**
	 * 
	 * @param kuid
	 * @param bbid
	 * @return
	 */
	public BsStudent findByKuidAndBbid(Long kuid,Long bbid);

	// /**
	// * <li>功能描述：根据毕设学生编号，查找尚无分组的学生
	// *
	// * @param bsid
	// * @return 毕设学生列表
	// */
	// public List findByBsidNotInBssgroup(Long bsid);

	/**
	 * <li>功能描述：根据毕设学生编号、二辩资格审批状态查询学生列表
	 * 
	 * @param bsid
	 *            毕设学生编号
	 * @param ispasstwo
	 *            二辩资格审批状态
	 * @return 学生列表
	 */
	public List findByBsidAndBstispasstwo(Long bsid, short ispasstwo);

	/**
	 * <li>功能描述：根据批次编号查找学生
	 * 
	 * @param BbId
	 *            批次编号
	 * @return 学生列表
	 */
	public List findByBbId(Long BbId);

	/**
	 * <li>功能描述：根据毕设单位编号查找毕设单位中没有选题的学生
	 * 
	 * @param buid
	 *            毕设单位编号
	 * @return 学生列表
	 */
	public List findBuIdNotInDbChoose(Long buid);

	/**
	 * <li>功能描述：根据批次编号查找毕设单位中没有选题的学生
	 * 
	 * @param bbid
	 *            毕设批次编号
	 * @return 学生列表
	 */
	public List findBbIdNotInDbChoose(Long bbid);

	// /**
	// * <li>功能描述：根据批次编号查找学生数目
	// *
	// * @param bbid
	// * 批次编号
	// * @return 学生数目
	// */
	// public Long countByBBid(Long bbid);

	// /**
	// * <li>功能描述：查询毕设单位中未分批次学生
	// *
	// * @param buid
	// * 毕设单位编号
	// * @return 学生列表
	// */
	// public List findNotBatch(Long buid);

	/**
	 * <li>功能描述：根据答辩分组编号，查找该组答辩学生
	 * 
	 * @param brpid
	 * @return 该分组所有答辩学生成员列表
	 * @author FengXinhong 2010-07-08
	 */
	public List findByBrpid(Long brpid);

	/**
	 * 查询毕设学生功能
	 * 
	 * @param buid
	 *            毕设单位
	 * @param name
	 *            学生姓名
	 * @param tno
	 *            学号
	 * @param kdid
	 *            部门编号
	 * @return
	 */
	List findByBuidAndNameAndstid(Long buid, String name, String tno);

	/**
	 * <li>功能描述：根据学生编号，查找学生记录集
	 * 
	 * @param bsid
	 * @return
	 */
	public BsStudent findByBsid(Long bsid);

	/**
	 * <li>功能描述：查找毕设单位中没有分批的学生
	 * 
	 * @param buid
	 * @return
	 */

	public List findNoBatchByBuId(Long buId);

	/**
	 * 根据毕设单位编号、姓名或者学号查询不在双向关系中的毕设学生列表\
	 * 
	 * @param buid
	 *            毕设单位编号
	 * @param name
	 *            学生姓名
	 * @param stid
	 *            学号
	 * @return 毕设学生列表
	 * @author lys
	 */
	List findByBuidAndNameAndStidNotInDbchoose(Long buid, String name, String stid);

	/**
	 * 根据毕设单位编号、姓名或者学号查询不在双向关系中,尚无指定教师的毕设学生列表\
	 * 
	 * @param buid
	 *            毕设单位编号
	 * @param name
	 *            学生姓名
	 * @param stid
	 *            学号
	 * @return 毕设学生列表
	 * @author lys
	 */
	List findByBuidAndNameAndStidNotInDb(Long buid, String name, String stid);

	/**
	 * 根据批次编号、姓名或者学号查询不在双向关系中，尚无指定教师的毕设学生列表\
	 * 
	 * @param bbid
	 *            毕设单位编号
	 * @param name
	 *            学生姓名
	 * @param stid
	 *            学号
	 * @return 毕设学生列表
	 * @author lys
	 */
	List findByBbidAndNameAndStidNotInDb(Long bbid, String name, String stid);

	/**
	 * 根据批次编号、姓名或者学号查询不在双向关系中的毕设学生列表\
	 * 
	 * @param bbid
	 *            毕设单位编号
	 * @param name
	 *            学生姓名
	 * @param stid
	 *            学号
	 * @return 毕设学生列表
	 * @author lys
	 */
	List findByBbidAndNameAndStidNotInDbchoose(Long bbid, String name, String stid);

	/**
	 * 更新学生的是否允许参加一辩，查找不在延期表的记录
	 * 
	 * @param buid
	 */
	public void updateStuPassone(long buid);

	/**
	 * 根据毕设单位编号、答辩资格审批情况来查找二辩尚无分组的学生
	 * 
	 * @return
	 */
	public List findBuIdNotInSgroup(Long buid, Short ispasstwo);

	/**
	 * 根据毕设单位编号查找已有一辩资格尚未有答辩成绩的学生
	 * 
	 * @return
	 */
	public List findBuIdNotDbcj(Long buid);

	/**
	 * 根据毕设单位编号查找已有二辩资格尚未有答辩成绩的学生
	 * 
	 * @return
	 */
	public List findBuIdNotTwoDbcj(Long buid);

	/**
	 * 根据毕设单位编号查找具有一辩资格且有答辩成绩的学生
	 * 
	 * @return
	 */
	public List findBuIdDbcj(Long buid);

	/**
	 * 根据毕设单位编号查找仅有二辩资格且有答辩成绩的学生
	 * 
	 * @return
	 */
	public List findBuIdTwoDbcj(Long buid);

	/**
	 * 根据学号和姓名查询学生
	 * 
	 * @return
	 */
	public List findBySidOrName(String sid, String name, Long buId);

	/**
	 * 根据用户编号和毕设过程编号查找毕设学生
	 * 
	 * @param kuid
	 * @return
	 */
	public List findByKuid(Long kuid);

	/**
	 * 根据学号、姓名查询学生
	 */
	public List findBySidorName(String sid, String name, Long buId, Short ispassone);

	/**
	 * 根据学号、姓名查询学生
	 */
	public List findBysidorname(String sid, String name, Long buId, Short ispassone);

	public List findBysidorname2(String sid, String name, Long buId, Short ispasstwo);
	public List findByBuidINPassoneOrInPasstwo(Long buId);
}
