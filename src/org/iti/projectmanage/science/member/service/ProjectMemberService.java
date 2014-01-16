package org.iti.projectmanage.science.member.service;

import java.util.List;

import org.iti.gh.entity.GhXm;
import org.iti.xypt.entity.Teacher;

import com.uniwin.basehs.service.BaseService;
import com.uniwin.framework.entity.WkTUser;

public interface ProjectMemberService extends BaseService {
	
	/**
	 * 查询项目组某个成员
	 */
	public List findByKyIdAndKuId(Long kyId, Long kuId);
	/**
	 * 查询项目组全部成员
	 */
	public List findByKyId(Long kyId,int pageNum, int pageSize );
	/**
	 * 查询校外人员
	 */
	public String  findOutMember();
	public List findOutMemberByuid(Long kuid);
	public List findByMid(Long mid);
	/**
	 * 查询项目
	 */
	public  GhXm findXM(Long kyid);
	/**
	 * 
	 */
	public  List findteacher(Long kuid);
	/**
	 * 研究方向
	 */
	public  List findYjfx(Long gyid);
   /**
    * 
    * @return 项目组人员
    */
	public List findByKyXmId(Long kyid);
	/**
	 * 
	 * @param kdid 部门ID
	 * @param name 用户姓名
	 * @param pageNum 页码
	 * @param pageSize 页面大小
	 * @return
	 */
	public List<WkTUser> findUserForGroupByKdIdAndName1(Long kdid, String name, Boolean teacher, Boolean student, Boolean graduate,int pageNum, int pageSize);
/**
 * 统计教师项目级别、进展
 */
	public List<GhXm> findByKdidAndYearAndKuidAndTypeAndJb(Long kuid,String year,Integer type, String jb,String hx,String jz);
/**
 * 统计其它级别项目
 */
	public List findQtByYearAndKuid(String year, Long kuid,Integer type,String hx);
	/**
	 * 
	 * @param name 项目名称
	 * @return 项目
	 */
	public GhXm findByName(String name);
	/**
	 * 
	 * @param xmid 项目ID
	 * @param kuid 用户ID
 	 * @param type 论文类型
	 * @return 论文列表
	 */
	 public List findHyLwByXm(Long xmid,Long kuid,Short type);
	 public List findQkLwByXm(Long xmid,Long kuid,Short type);
	 /**
	  * 
	  * @param xmid
	  * @param kuid
	  * @param type
	  * @return 软件著作
	  */
	 public List findRjzzByXm(Long xmid,Long kuid,Short type);
	 /**
	  * 
	  * @param xmid
	  * @param kuid
	  * @param type
	  * @return 发明专利
	  */
	 public List findFmzlByXm(Long xmid,Long kuid,Short type);
	/**
	 * 
	 * @param kyid
	 * @return 项目
	 */
	 public List findXmByid(Long kyid);
	 
	 public List findZzByXm(Long xmid,Long kuid,Short type);
}
