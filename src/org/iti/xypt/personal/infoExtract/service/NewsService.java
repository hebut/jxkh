package org.iti.xypt.personal.infoExtract.service;
/**
 * 2010-3-15
 * @author 王泓淼
 */

import java.util.List;
import com.uniwin.basehs.service.BaseService;
import org.iti.xypt.personal.infoExtract.entity.WkTExtractask;
import com.uniwin.framework.entity.WkTUser;
import org.iti.xypt.personal.infoExtract.entity.WkTChanel;
import org.iti.xypt.personal.infoExtract.entity.WkTDistribute;
import org.iti.xypt.personal.infoExtract.entity.WkTInfo;
import org.iti.xypt.personal.infoExtract.entity.WkTInfoscore;


public interface NewsService extends BaseService {
	
	/**
	 * <li>功能描述：查找所有已发布的信息，按发布时间倒序排列。
	 * @return
	 * List 
	 * @author Administrator
	 */
	public List getNewsOfAllChanelFB();
	
/**
 * 
 * @param ptid 栏目父ID
 * @return 子栏目
 */
	public List getChildNews(Long ptid);
	public List getChildNews(Long ptid,Long kwid);
/**
 * 
 * @param ptid 用户Id
 * @return  该用户具有管理权限的栏目列表
 */
	public List getChildNewsown(Long ptid,Long did);
	/**
	 * 
	 * @param user 用户
	 * @param deptList  部门列表
	 * @return  该用户有权限的栏目列表
	 */
	public List getChanelOfUserManage(WkTUser user,List deptList);
	public List getChanelOfManage(WkTUser user,List deptList,List rlist);
	public List getChanelOfAudit(WkTUser user,List deptList,List rlist);
	/**
	 * 
	 * @param ptid 用户Id
	 * @return 该用户具有审核权限的栏目列表
	 */
	public List getChildNewsaudit(Long ptid,Long did);
	/**
	 * @param qtid 信息的ID
	 * @return  获得该信息的文档内容
	 * @author 
	 */
	public List getChildNewsContent(Long qtid);
/**
 * 
 * @param qtid 信息ID
 * @return  信息所属的栏目
 */
	public WkTChanel getNewsOfChanel(Long qtid);
	/**
	 * 
	 * @param ktaid 分类ID 
	 * @return  分类下的所有信息
	 */
	public List getNewsOfinfo(Long ktaid);
	
/**
 * 
 * @param did 栏目ID
 * @return  相应栏目不同状态的信息列表（撰稿、退回、送审、已阅、已发布）
 */
	public List getNewsOfChanelZG(Long did,Long pid);
	public List getNewsOfChanelTH(Long did,Long pid);
	public List getNewsOfChanelSS(Long did,Long pid);
	public List getNewsOfChanelYY(Long did,Long pid);
	public List getNewsOfChanelFB(Long did,Long pid);
	public List getNewsOfChanelSS(Long did);
	public List getNewsOfChanelYY(Long did);
	public List getNewsOfChanelFB(Long did);
	public List getCountbyciid(Long kcid,Long status, String db, String db1);
	public List getSumByciid(Long kcid, String db, String db1);
	/**
	 * 
	 * @param did 栏目ID  搜索关键字
	 * @param key 获取含有关键字的信息搜索列表
	 * @return
	 */
	public List getNewsOfChanelFB(Long did,Long pid, String key);
	public List getNewsOfChanelZG(Long did, Long pid,String key);
	public List getNewsOfChanelTH(Long did, Long pid,String key);
	public List getNewsOfChanelSS(Long did, Long pid,String key);
	public List getNewsOfChanelYY(Long did, Long pid,String key);
	public List getNewsOfChanelFB(Long did, String key);
	public List getNewsOfChanelSS(Long did,String key);
	public List getNewsOfChanelYY(Long did,String key);

	/**
	 * 
	 * @param pid  频道ID
	 * @return  频道状态（是否需要审核）
	 */
	public WkTExtractask getChanelState(Long pid);
	/**
	 * 
	 * @param pid 信息ID
	 * @return  信息发布记录
	 */
	public List getDistribute(Long pid);
	public List getDistribute(Long pid,Long did);
	public List getDistributeShare(Long pid);
	public WkTDistribute getWktDistribute(Long pid);
	/**
	 * 
	 * @param kbid 发布ID
	 * @return 发布记录
	 */
	public WkTDistribute getDistriBybid(Long kbid);
	/**
	 * 
	 * @param kiid 信息ID
	 * @param kcid 栏目ID
	 * @return  发布记录
	 */
	public WkTDistribute getDistri(Long kiid,Long kcid);
	/**
	 * 
	 * @param pid 信息ID
	 * @return  信息及其共享信息列表
	 */
	public List getDistributeList(Long pid);
	/**
	 * 
	 * @param pid 信息ID
	 * @return  信息的内容（删除信息）
	 */
	public List getInfocnt(Long pid);
	
	/**
	 * 
	 * @param did 信息号
	 * @return  获得信息的评论
	 */
	public List getNewsOfcomment(Long did);
	/**
	 * 
	 * @param 信息ID
	 * @return  获得信息的处理意见
	 */
	public List getflog(Long did);
	/**
	 * 
	 * @param did 信息ID
	 * @return  获取相应信息的附件列表
	 */
	public List getAnnex(Long did);
	/**
	 * 
	 * @param info 获取所有信息来源列表
	 */
	public List getInfo();
	/**
	 * @param did 信息ID
	 * @return 获得信息的所有属性值
	 */
	public WkTInfo getWkTInfo(Long did);
	/**
	 * 
	 * @param did 信息ID
	 * @return 信息的共享栏目
	 */
	public List getNewsOfShareChanel(Long did);
	public List getNewsOfShare(Long did);
	public List getNewsOfShareNew(Long did);
	/**
	 * 
	 * @param did  信息ID
	 * @return  信息附件列表
	 */
	public List getFile(Long did);
	public List getOrifile(Long did);
	/**
	 * 
	 * @param kbid 信息发布ID
	 * @return  信息附件列表
	 */
	public List getSiteFile(Long kbid);
	/**
	 * <li>功能描述：根据用户id和文档的发布状态以及时间区间统计
	 * @param kuid
	 * @param status
	 * @param db
	 * @param db1
	 * @return List
	 * @author FengXinhong
	 * 2010-4-13
	 */
	public List getCountInfor(Long kuid,String status,String db,String db1);
	/**
	 * <li>功能描述：根据用户的id和时间区间统计信息
	 * @param kuid
	 * @param db
	 * @param db1
	 * @return List
	 * @author FengXinhong
	 * 2010-4-13
	 */
	public List getSumbyuid(Long kuid,String db,String db1);
	/**
	 * <li>功能描述：根据站点的id，信息的发布状态和时间区间统计信息
	 * @param kwid
	 * @param status
	 * @param db
	 * @param db1
	 * @return List
	 * @author whm
	 * 2010-8-04
	 */
	public List getCountbywid(Long kwid,String status,String db,String db1);
	/**
	 * <li>功能描述：根据栏目的id，信息的发布状态和时间区间统计信息
	 * @param kcid
	 * @param status
	 * @param db
	 * @param db1
	 * @return List
	 * @author whm
	 * 2010-8-04
	 */
	public List getCountbycid(Long kcid,String status,String db,String db1);
	/**
	 * <li>功能描述：根据站点的id，信息的发布状态和时间区间统计信息
	 * @param kwid
	 * @param db
	 * @param db1
	 * @return List
	 * @author whm
	 * 2010-8-04
	 */
	public List getSumBywid(Long kwid,String db,String db1);
	/**
	 * <li>功能描述：根据栏目的id，信息的发布状态和时间区间统计信息
	 * @param kcid
	 * @param db
	 * @param db1
	 * @return List
	 * @author whm
	 * 2010-8-04
	 */
	public List getSumBycid(Long kcid,String db,String db1);
	/**
	 * <li>功能描述：根据部门id，信息的发布状态和时间区间统计信息
	 * @param kdid
	 * @param rname
	 * @param status
	 * @param db
	 * @param db1
	 * @return List
	 * @author FengXinhong
	 * 2010-43-14
	 */
	public List getCountByDid(Long kdid,String rname,String status,String db,String db1);
	
	/**
	 * <li>功能描述：根据部门id和时间区间统计信息总数量
	 * @param kdid
	 * @param status
	 * @param db
	 * @param db1
	 * @return List
	 * @author FengXinhong
	 * 2010-4-14
	 */
	public List getSumBydid(Long kdid,String rname,String db,String db1);
	
	/**
	 * <li>功能描述：根据栏目id查询发布在这个栏目中的所有发布记录
	 * @param kcid
	 * @return 
	 * @author FengXinhong
	 * 2010-4-17
	 */
	public List getDistriByCid(Long kcid);
	/**
	 * <li>功能描述：获取最近对外发布的新闻
	 * @param top
	 * @return
	 */
	public List getNewstop();
	/**
	 * 
	 * @param kiid 信息发布id
	 * @param db  开始时间
	 * @param db1  终止时间
	 * @return  该信息下所有评论
	 */
	public List getSumBywbid(Long kiid, String db, String db1) ;
/**
 * 
 * @param kiid 信息id
 * @param status 评论状态
 * @param db 开始时间
 * @param db1 终止时间
 * @return  该信息下相关状态的评论
 */
	public List getCountbywiid(Long kiid,Long  status, String db, String db1);
	/**
	 * 
	 * @param kcid 栏目ID
	 * @return  该栏目下所有已对外发布信息
	 */
	public List getInfoByCid(Long kcid);
	/**
	 * 
	 * @param kcid 栏目ID
	 * @return  引用该栏目的栏目列表
	 */
	public List getChanelSite(Long kcid);
	public List getCsDistribute(Long kcid,Long kiid);
	/**
	 * 
	 * @param kcid 栏目ID
	 * @return  该栏目下所发布的信息
	 */
	public List getInfoBycid(Long kcid);
	/**
	 * 
	 * @param kitype  信息类型
	 * @return  该信息分值
	 */
	public WkTInfoscore getScore(String kitype);
	/**
	 * 
	 * @param kdid 部门ID
	 * @return  该部门下所有信息员
	 */
	public List getInfodep(Long kdid);
	/**
	 * 
	 * @param kbid 信息的发布ID
	 * @return  信息的引用次数统计
	 */
	 public List getInfoSiteTime(String kbid);
	 /**
	  * 
	  * @param kcid 栏目ID
	  * @return  该栏目
	  */
	 public WkTChanel getChanel(Long kcid);
	 public List NewsSearch(Long keid,Long status,String bt,String et,String flag,String k,String s);
		public List OriNewsSearch(Long keid,String bt,String et,String flag,String k,String s);
	 /**
		 * 
		 * <li>功能描述：返回发布信息列表，并按发布时间的指定方式排序。
		 * @param cid
		 * 			栏目ID
		 * @param orderType
		 * 			排序方式
		 * @return
		 * List 
		 * @author Administrator
		 */
		public List getOrderedNewsOfChanelFB(Long cid, String orderType);
		public List getRMKByKiid(Long kiid);
		 public WkTInfo getInfobyBid(Long bid);
		 /**
		  * 
		  * @return 所有已发布信息
		  */
		 public List getPubInfo(Integer  pubId);
		 public List getPubInfo();
}
