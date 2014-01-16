package org.iti.xypt.personal.infoCollect.service;



import java.util.List;

import org.iti.xypt.personal.infoCollect.entity.WkTExtractask;
import org.iti.xypt.personal.infoCollect.entity.WkTOrinfo;

import com.uniwin.basehs.service.BaseService;




public interface OriNewsService extends BaseService
{
	/**
	 * 
	 * @param keid 任务ID
	 * @return  任务下的原始信息
	 */
	public List getNewsOfOrinfo(Long keid);
	/**
	 * 
	 * @param koid 原始信息ID
	 * @return 对应的信息存贮记录
	 */
	public WkTOrinfo getOriInfo(Long koid);
	/**
	 * 
	 * @param koid 原始信息ID
	 * @return  信息内容
	 */
	public List getOriInfocnt(Long koid);
	/**
	 * 
	 * @param d1 开始时间
	 * @param d2 截止时间
	 * @param sign 关键字段
	 * @param keys 查询关键字
	 * @param sources 信息来源
	 * @return   组合查询信息列表
	 */
	public List getSearchInfo(Long keid,String d1,String d2,String sign,String keys,String sources);
	/**
	 * 
	 * @param koid 原始信息ID
	 * @return 信息附件列表
	 */
	public List getOrifile(Long koid);
	public WkTExtractask getTask(Long keid);
	public List findAllInfo(Long keid);
	public List findReadInfo(Long keid,Long kuid);
}