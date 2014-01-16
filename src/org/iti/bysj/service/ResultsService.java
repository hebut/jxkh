package org.iti.bysj.service;

import java.util.List;

import org.iti.bysj.entity.BsResults;

import com.uniwin.basehs.service.BaseService;

public interface ResultsService extends BaseService {

	/**
	 * <li>功能描述：根据毕设学生编号、成绩子项编号、答辩批次，查询某学生的某项子成绩
	 * 
	 * @param bsid
	 *            毕设学生编号
	 * @param bscid
	 *            成绩子项编号
	 * @param brbatch
	 *            答辩批次
	 * @return 子成绩
	 */
	List findByBsidAndBscidAndBrbatch(Long bsid, Long bscid, Short brbatch);

	Double findAvgByBsidAndBscidAndBrbatch(Long bsid, Long bscid, Short brbatch);

	List findByBbId(Long bbid);

	List findByBuid(Long buid);

	/**
	 * <li>功能描述：根据毕设学生编号、成绩子项编号、答辩批次、 成绩评阅人，查询某学生的某项子成绩
	 * 
	 * @param bsid
	 *            毕设学生编号
	 * @param bscid
	 *            成绩子项编号
	 * @param brbatch
	 *            答辩批次
	 * @param kuid
	 *            成绩评阅人
	 * @return 子成绩 hlj
	 */
	BsResults findByBsidAndBscidAndBrbatchAndKuid(Long bsid, Long bscid, Short brbatch, Long kuid);

	/**
	 * 根据同行评阅表主键查找同行评阅教师成绩
	 * 
	 * @param BPV_ID
	 * @return 成绩
	 * @author 张雪
	 */
	public Double findSumBypeer(Long kuid, Long bdbid);

	/**
	 * <li>功能描述：根据毕设单位编号、给出分数用户、答辩批次、所属毕设编号、成绩类型，查询某学生的某项子成绩
	 * 
	 * @param buid
	 *            毕设单位编号
	 * @param kuid
	 *            用户编号
	 * @param batchid
	 *            答辩批次
	 * @param bgid
	 *            所属毕设编号
	 * @param bsfrom
	 *            成绩类型
	 * @author DATIAN
	 * @return 子成绩列表
	 */
	List findByBuidAndKuidAndBatchidAndBgidAndBsfrom(Long buid, Long kuid, Short batchid, Long bgid, Short bsfrom);
	/**
	 * <li>功能描述：查找当前专业编号，答辩批次 和成绩来源查找是否有成绩列表
	 * @param buid 专业编号
	 * @param batchid 答辩批次
	 * @param bgid 毕设过程
	 * @param bsfrom 成绩来源
	 * @return
	 */
	List findByBuidAndBatchidAndBgidAndBsfrom(Long buid,Short batchid, Long bgid, Short bsfrom);
	/**
	 * <li>功能描述：根据用户编号、学生编号、成绩子项编号查询
	 * 
	 */
	List findByKuidAndBsidAndBscid(Long kuid, Long bsid, Long bscid);

	/**
	 * <li>功能描述：根据学生编号、成绩子项编号查询
	 * 
	 */
	List findByBsidAndBscid(Long bsid, Long bscid);
}
