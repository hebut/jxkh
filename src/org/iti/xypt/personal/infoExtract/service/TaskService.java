package org.iti.xypt.personal.infoExtract.service;



import java.util.List;

import org.iti.xypt.personal.infoExtract.entity.WkTExtractask;
import org.iti.xypt.personal.infoExtract.entity.WkTTasktype;

import com.uniwin.basehs.service.BaseService;
import com.uniwin.framework.entity.WkTUser;




public interface TaskService extends BaseService
{
	/**
	 * @param ptid 父类型ID
	 * @return  所有子类型
	 */
	public List getChildType(Long ktid);
	/**
	 * <li>获得具有管理/审核权限的任务列表
	 * @param user
	 * @param deptList
	 * @param titleList
	 * @return 任务列表
	 */
	public List getTaskOfManage(WkTUser user,List deptList,List titleList);
	public List getTaskOfAuditManage(WkTUser user,List deptList,List rlist);
	public List getTaskOfAllManage();
	/**
	 * 分类ID
	 * @return 分类下的任务列表
	 */
	public List getChildTask(Long ktaid);
	/**
	 * 
	 * @param pid 父ID
	 * @param taid 分类ID
	 * @return  除了该分类的所有分类
	 */
	public List getChildTasktype(Long pid,Long taid);
	/**
	 * 
	 * @param ktaid  分类ID
	 * @return 对应的分类
	 */
	public WkTTasktype getTpyeById(Long ktaid);
	/**
	 * 
	 * @param ktaid 分类ID
	 * @return  分类权限
	 */
	public  List getTypeAuth(Long ktaid);
	/**
	 * 
	 * @param ktaid 分类ID
	 * @return 分类下的任务
	 */
	public List  getTaskByKtaid(Long ktaid);
	/**
	 * 
	 * @param keid 任务ID
	 * @return 获取任务下的信息
	 */
	public List getInfoBykeid(Long keid);
	public List getUserSort(Long kusid);
	/**
	 * 
	 * @param keid 任务ID
	 * @return  任务信息
	 */
	public List getTaskBykeId(Long keid);
	
	//抽取新建service
	public List findAllTask();
	public List findByFolderId(Long folderId);
	public WkTTasktype findByFolderID(Long id);
	public WkTExtractask findById(Long id);
}