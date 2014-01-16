package org.iti.bysj.service;

import java.util.List;

import org.iti.bysj.entity.BsDbchoose;

import com.uniwin.basehs.service.BaseService;

public interface DbchooseService extends BaseService {

	/**
	 * <li>功能描述:根据主键查询双选记录
	 * 
	 * @param bbid
	 *            批次编号
	 * @return 双选记录列表
	 */
	BsDbchoose findByBdbid(long bdbid);

	/**
	 * <li>功能描述:根据毕设单位，是否接受查询双选记录
	 * 
	 * @param buid
	 *            毕设单位编号
	 * @param ifaccept
	 *            是否接受
	 * @return 双选记录列表
	 */
	List findByBuidAndIfaccept(long buid, short ifaccept);

	/**
	 * <li>功能描述:根据批次编号，是否接受查询双选记录
	 * 
	 * @param bbid
	 *            批次编号
	 * @param ifaccept
	 *            是否接受
	 * @return 双选记录列表
	 * 
	 */
	List findByBuidAndIfacceptks(long buid, short ifaccept);
	/**
	 * <li>功能描述:根据批次编号，是否接受查询双选记录(可快速查找)
	 * 
	 * @param bbid
	 *            批次编号
	 * @param ifaccept
	 *            是否接受
	 * @return 双选记录列表
	 * 
	 */
	List findByBbidAndIfaccept(long bbid, short ifaccept);

	// /**
	// * <li>功能描述:根据教师编号，查询学生列表
	// * @param btid 教师编号
	// * @return
	// */
	// List findByBtid(long btid);

	/**
	 * <li>功能描述:根据课题编号 查找课题是否有备选学生
	 * 
	 * @param bprid
	 *            课题编号
	 * @return
	 */
	List findByBprid(long bprid);

	/**
	 * <li>功能描述:根据教师编号，是否接受，查询学生列表
	 * 
	 * @param btid
	 *            教师编号
	 * @param ifaccept
	 *            是否接受
	 * @return
	 */
	List findByBtidAndIfaccept(long btid, short ifaccept);

	/**
	 * <li>功能描述：根据教师编号，批次编号，是否接受查询学生列表
	 * 
	 * @param btid
	 *            教师编号
	 * @param ifaccept
	 *            是否接受
	 * @param bbid
	 *            批次编号
	 * @return
	 */
	List findByBtidAndBbidAndIfaccept(long btid, long bbid, short ifaccept);

	/**
	 * <li>功能描述:根据课题编号 是否接受查找课题是否有备选学生
	 * 
	 * @param bprid
	 *            课题编号
	 * @param ifaccept
	 * @return
	 */
	List findByBpridAndIfaccept(long bprid, short ifaccept);

	/**
	 * <li>功能描述:根据学生编号 查找学生是否有备选课题
	 * 
	 * @param bsid
	 *            学生编号
	 * @return
	 */
	List findByBsid(long bsid);

	/**
	 *<li>功能描述: 根据学生编号，是否接受 查找学生是否有备选课题
	 * 
	 * @param bsid
	 *            学生编号
	 * @param ifaccept
	 *            是否接受
	 * @return
	 */
	List findByBsidAndIfaccept(long bsid, short ifaccept);

	/**
	 * <li>功能描述：根据用户编号，单位编号 查找双向指导
	 * 
	 * @param buid单位编号
	 * @param kuid用户编号
	 * @param ifaccept是否接受
	 * @return
	 */
	//List findByBuidAndKunameAndIfaccept(long buid, String kuname, short ifaccept);

	/**
	 * <li>功能描述：根据指导教师编号查找双向指导列表
	 * 
	 * @param btid指导教师编号
	 * @return 双向指导列表
	 * @autor lys
	 */
	List findByBtid(long btid);

	/**
	 * <li>功能描述：根据毕设单位编号查找双向指导列表
	 * 
	 * @param buid毕设单位编号
	 * @return 双向指导列表
	 * @autor lys
	 */
	List findByBuid(long buid);

	/**
	 * <li>功能描述：根据批次编号查找双向指导列表
	 * 
	 * @param bbid批次编号
	 * @return 双向指导列表
	 * @autor lys
	 */
	List findByBbid(long bbid);

	/**
	 * <li>功能描述：根据批次编号和教师编号查找双向指导列表
	 * 
	 * @param bbid批次编号
	 * @param btid教师编号
	 * @return 双向指导列表
	 * @author DATIAN
	 */
	List findByBbidAndBtid(long bbid, long btid);

	/**
	 * <li>功能描述：根据毕设单位编号和子阶段编号和文档状态，查询没有提交文档或者文档尚已通过的双向关系列表
	 * 
	 * @param buid毕设单位编号
	 * @param bcpid子阶段编号
	 * @param ifstate
	 *            文档状态
	 * @return 双向指导列表
	 * @author lys
	 */
	List findByIfacceptAndBcpidAndStateInPcontrol(short ifaccept, Long bcpid, short state);

	/**
	 * <li>功能描述：根据毕设单位编号和子阶段编号和文档状态，查询没有提交文档或者文档尚已通过的双向关系列表
	 * 
	 * @param buid毕设单位编号
	 * @param bcpid子阶段编号
	 * @param ifstate
	 *            文档状态
	 * @return 双向指导列表
	 * @author lys
	 */
	List findByIfacceptAndBcpidAndStateAndIfcqInPcontrol(short ifaccept, Long bcpid, short state, short ifcq);

	/**
	 * <li>功能描述：根据毕设单位编号和子阶段编号和文档状态，查询没有提交文档或者文档尚未通过的双向关系列表
	 * 
	 * @param buid毕设单位编号
	 * @param bcpid子阶段编号
	 * @param ifstate
	 *            文档状态
	 * @return 双向指导列表
	 * @author lys
	 */
	List findByBuidAndIfacceptAndBcpidAndStateNotInPcontrol(Long buid, short ifaccept, Long bcpid, short state);

	/**
	 * <li>功能描述：根据毕设单位编号和子阶段编号和文档状态，查询没有提交文档或者文档尚未通过的双向关系列表
	 * 
	 * @param buid毕设单位编号
	 * @param bcpid子阶段编号
	 * @param ifstate
	 *            文档状态
	 * @return 双向指导列表
	 * @author lys
	 */
	List findByBbidAndIfacceptAndBcpidAndStateNotInPcontrol(Long buid, short ifaccept, Long bcpid, short state);

	/**
	 * <li>功能描述：根据毕设单位编号和子阶段编号和文档状态，查询没有提交文档或者文档尚已通过的双向关系列表
	 * 
	 * @param buid毕设单位编号
	 * @param bcpid子阶段编号
	 * @param ifstate
	 *            文档状态
	 * @return 双向指导列表
	 * @author lys
	 */
	List findByIfacceptAndBcpidInPcontrol(short ifaccept, Long bcpid);

	/**
	 * <li>功能描述：根据毕设单位编号和子阶段编号和文档状态，查询没有提交文档或者文档尚已通过的双向关系列表
	 * 
	 * @param buid毕设单位编号
	 * @param bcpid子阶段编号
	 * @param ifstate
	 *            文档状态
	 * @return 双向指导列表
	 * @author lys
	 */
	List findByIfacceptAndBcpidAndIfcqInPcontrol(short ifaccept, Long bcpid, short ifcq);

	/**
	 * <li>功能描述：根据毕设单位编号和子阶段编号，查询没有提交文档的双向关系列表
	 * 
	 * @param buid毕设单位编号
	 * @param bcpid子阶段编号
	 * @param ifstate
	 *            文档状态
	 * @return 双向指导列表
	 * @author lys
	 */
	List findByBuidAndIfacceptAndBcpidNotInPcontrol(Long buid, short ifaccept, Long bcpid);

	/**
	 * <li>功能描述：根据毕设单位编号和子阶段编号，查询没有提交文档的双向关系列表
	 * 
	 * @param buid毕设单位编号
	 * @param bcpid子阶段编号
	 * @param ifstate
	 *            文档状态
	 * @return 双向指导列表
	 * @author lys
	 */
	List findByBbidAndIfacceptAndBcpidNotInPcontrol(Long buid, short ifaccept, Long bcpid);
	/**
	 * <li>功能描述：根据毕设单位编号,是否接受和课题类型查找双向关系列表
	 * 
	 * @param buid毕设单位编号
	 * @param ifaccept是否接受
	 * @param bprtype
	 *            课题类型
	 * @return 双向指导列表
	 * @author lys
	 */
	List findByBuidAndIfacceptAndBprtype(Long buid, short ifaccept, short bprtype);

	/**
	 * 查找当前单位的参加一辩的学生名单
	 * 
	 * @param buid
	 * @return
	 * @author zhangxue
	 */
	List findOnebatchByBuid(Long buid);

	/**
	 * 查找不能参加二辩的学生名单，就是未答辩学生
	 * 
	 * @param buid
	 *            答辩单位编号
	 * @return
	 */
	List findNotDb2ByBuid(Long buid);

	/**
	 * 根据毕设单位编号和是否接受查找已经有教师评阅成绩的双选关系列表
	 * 
	 * @param buid
	 *            毕设单位编号
	 * @return
	 * @author lys
	 */
	List findByBuidAndIfacceptHaveTScore(Long buid, short ifaccept);

	/**
	 * 根据毕设单位编号和是否接受查找已经有同行评阅成绩的双选关系列表
	 * 
	 * @param buid
	 *            毕设单位编号
	 * @return
	 * @author lys
	 */
	List findByBuidAndIfacceptHaveReview(Long buid, short ifaccept);

	/**
	 * 根据毕设单位编号和是否接受查找已经有答辩成绩的双选关系列表
	 * 
	 * @param buid
	 *            毕设单位编号
	 * @return
	 * @author lys
	 */
	List findByBuidAndIfacceptHaveReplyOrTworeply(Long buid, short ifaccept);

	/**
	 * 查找题目性质相同的学生列表
	 */
	List findSameNature(Long buid, Long bnsid);

	/**
	 * <li>功能描述:根据教师编号，是否接受，是否通过一辩，查询学生列表
	 * 
	 * @param btid
	 *            教师编号
	 * @param ifaccept
	 *            是否接受
	 * @return
	 */
	List findByBtidAndIfacceptAndIfpass(long btid, short ifaccept, short ispassone);

	List findBybbid(long bbid);
	/**
	 * <li>功能描述：根据学生编号，姓名，单位编号 查找学生课题
	 * 
	 * @param buid单位编号
	 * @param bsid 学生编号
	 * @param name 学生姓名
	 * @param ifaccept是否接受
	 * @return
	 */
	
	List findByBuidAndIfacceptAndStidOrName(long buid, short ifaccept,String stid, String name);
	/**
	 * 根据毕设单位编号查找已有一辩资格尚未有答辩成绩的学生
	 * 
	 * @return
	 */
	List findBuIdNotDbcj(Long buid);
	/**
	 * 根据毕设单位编号查找已有二辩资格尚未有答辩成绩的学生
	 * 
	 * @return
	 */
	List findBuIdNotTwoDbcj(Long buid);

	
}
