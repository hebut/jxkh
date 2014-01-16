package org.iti.projectmanage.science.reference.service;

import java.util.List;

import org.iti.gh.entity.GH_ARCHIVE;

import com.uniwin.basehs.service.BaseService;

public interface ReferenceService extends BaseService
{
	/**
	 * 根据文献来源类别、文献标题、作者、文献来源、基金号、发表时间查询参考文献列表
	 * @param sourceType 文献来源类别；
	 * 		0：全部，1：CNKI期刊全文库，2：中国重要会议论文库，3：EI，4：其他
	 * @param searchFiled 查询字段；
	 * 		0：题名，1：关键词，2：作者，3：文献来源，4：基金号
	 * @param content 查询内容
	 * @param startTime 查询开始日期
	 * @param endTime 查询结束日期
	 * @param category 项目类型，1：科研项目，2：教研项目
	 * @return
	 */
	public List<GH_ARCHIVE> findByTKAPSF(int sourceType,int searchFiled,String content,
			String startTime,String endTime,String clcString, String issueString,short category);
	
	/**
	 * 根据文献来源类别、文献标题、作者、文献来源、基金号、发表时间,分页查询参考文献列表
	 * @param sourceType 文献来源类别；
	 * 		0：全部，1：CNKI期刊全文库，2：中国重要会议论文库，3：EI，4：其他
	 * @param searchFiled 查询字段；
	 * 		0：题名，1：关键词，2：作者，3：文献来源，4：基金号
	 * @param content 查询内容
	 * @param startTime 查询开始日期
	 * @param endTime 查询结束日期
	 * @param category 项目类型，1：科研项目，2：教研项目
	 * @param pageNum 当前页数
	 * @param pageSize 每页条数
	 * @return
	 */
	public List<GH_ARCHIVE> findByTKAPSFAndPage(int sourceType,int searchFiled,	String content,String startTime,
			String endTime,String clcString, String issueString,short category,int pageNum, int pageSize);
	
	/**
	 * 根据文献来源类别、文献标题、作者、文献来源、基金号、发表时间查询符合条件的参考文献记录总数
	 * @param sourceType 文献来源类别；
	 * 		0：全部，1：CNKI期刊全文库，2：中国重要会议论文库，3：EI，4：其他
	 * @param searchFiled 查询字段；
	 * 		0：题名，1：关键词，2：作者，3：文献来源，4：基金号
	 * @param content 查询内容
	 * @param startTime 查询开始日期
	 * @param endTime 查询结束日期
	 * @param category 项目类型，1：科研项目，2：教研项目
	 * @return 符合条件的记录总数
	 */
	public List<Long> findCountByTKAPSF(int sourceType,int searchFiled,	String content,String startTime,
			String endTime,String clcString, String issueString,short category);
}
