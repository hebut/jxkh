package org.iti.gh.service;

import java.util.List;
import org.iti.gh.entity.GhXm;
import com.uniwin.basehs.service.BaseService;

public interface XmService extends BaseService {

	/**
	 * 根据用户的编号来查找其所有的已获得科研成果/目前承担项目/已完成教研/正在进行教研情况
	 * @param kuId
	 * @return
	 */
	List findByKuid(Long kuId,Short lx);
	/**
	 * 根据单位部门编号查找该单位部门教师科研或者教研情况
	 * @param kdid
	 * @return
	 */
	public List findByKdId(long kdid,Short lx,Short state);
	/**
	 * 根据单位部门编号查找该单位部门教师科研或者教研情况，同类项目合并
	 * @param kdid
	 * @return
	 */
	public List findSumKdId(long kdid,Short lx,String progress,Short state);
	/**
	 * 根据项目名称来查找其所有的已获得科研成果/目前承担项目/已完成教研/正在进行教研情况
	 * @param kuId
	 * @return
	 */
	List findByMc(String name,Short lx);
	
	/**
	 * <li>功能描述：根据项目主键查找姓名
	 *  
	 * @param kyid  项目编号主键
	 *
	 * @return 项目列表
	 */
	List findByKyid(Long kyid);
	/**
	 * 非本论文的资助项目
	 * @param kuId
	 * @param lx
	 * @param lwType
	 * @param jsxmType
	 * @param lwid
	 * @return
	 */
	List findByKuidAndLwidNotZz(Long kuId,Short lx,Short lwType, Integer jsxmType,Long lwid);

	/**
	 * <li>功能描述：查询某一个用户参与的并非该用户添加的项目名称
	 * @return
	 */
	List findAllname(Long kuId,String kuname,Short lx,Integer type);
	/**
	 * <li>功能描述：查询某一个用户参与的并非该用户添加的未获奖项目名称
	 * @param kuId
	 * @param kuname
	 * @param lx
	 * @param hj
	 * @return
	 */
	List findAllXmname(Long kuid,String kuname,Integer lx);
	
	/**
	 * <li>功能描述：根据用户的编号来查找其所有的已获得科研成果/目前承担项目/已完成教研/正在进行教研情况
	 * @param kuid
	 * @param type
	 * @return
	 */
	List findByKuidAndType(Long kuid,Integer type);
	public List findCountByKuidAndType(Long kuid,Integer type);
	
	/**
	 * <li>功能描述：根据用户的编号来查找其所有的已获得科研成果/目前承担项目/已完成教研/正在进行教研情况
	 * @param kuid 用户ID
	 * @param type 已获得科研成果/目前承担项目/已完成教研/正在进行教研情况
	 * @param proType 1-横向，2-纵向
	 * @return
	 */
	List findByKuidAndTypeAndKyclass(Long kuid,Integer type,String kyClass);
	
	/**
	 * <li>功能描述：根据用户的编号来查找其所有的已获得科研成果/目前承担项目/已完成教研/正在进行教研情况
	 * @param kuid 用户ID
	 * @param type 已获得科研成果/目前承担项目/已完成教研/正在进行教研情况
	 * @param proType 1-横向，2-纵向
	 * @param xmid 项目ID
	 * @return
	 */
	List findByKuidAndTypeAndKyclassAndXmid(String kyClass,Long xmid);
	/**
	 * 根据单位id，项目类型（科研项目），审核状态查询某一单位科研情况
	 * @param kdid
	 * @param lx
	 * @param State
	 * @return
	 */
	
	public List findByKdidAndLxAndTypeAndState(Long kdid,Short lx,Short State);
	
	 /**
	  * <li>根据名称、类别和项目来源判断项目是否唯一
	  * @param xm
	  * @param lx
	  * @param ly
	  * @return
	  */
	public boolean CheckOnlyOne(String xm, Short lx,String ly,String fzr);
	/**
	 * <li>根据名称、类别和项目来源查询项目if return true，说明没有与xm的名称、类型（教研/科研），来源相同的
	 * 返回false，说明有
	 * @param xm
	 * @param mc
	 * @param lx
	 * @param ly
	 * @return
	 */
	public boolean findByNameAndLxAndLy(GhXm xm,String mc, Short lx,String ly,String fzr);
	/**
	 * 
	 * @param mc
	 * @param lx
	 * @param ly
	 * @param fzr
	 * @return
	 */
	public GhXm findByNameAndLyAndLxAndFzr(String mc, Short lx,String ly,String fzr);
	/**
	 * 根据条件检索未建立关系的项目
	 * @param mc
	 * @param lx
	 * @param ly
	 * @param fzr
	 * @return
	 */
	public List findByMcAndLyAndFzr(Long kuid,String mc,Short lx,String ly,String fzr,Integer type);
	
	/**
	 * 
	 * @param kdid
	 * @param year
	 * @param kuid
	 * @param type
	 * @param jb
	 * @return
	 */
	public List findByKdidAndYearAndKuidAndTypeAndJb(Long kdid,String year,Long kuid,Integer type,String jb,String hx);
	/**
	 * 
	 * @param kdid
	 * @param year
	 * @param kuid
	 * @param type
	 * @param jb
	 * @param hx
	 * @return
	 */
	public List findQtByKdidAndYearAndKuid(Long kdid,String year,Long kuid,Integer type);
	
	/**
     * <li>功能描述：获取所有并分页。
     * @param pageNum 当前页数
     * @param pageSize 每页条数
     */
    public List<GhXm> getListPageByKuidAndTypeAndKyclass(Long kuid,Integer type,String kyClass,int pageNum, int pageSize) ;
    
    /**
     * <li>功能描述：获取所有并分页。
     * @param pageNum 当前页数
     * @param pageSize 每页条数
     */
    public List<GhXm> getListPageByKuidAndType(Long kuid,Integer type,int pageNum, int pageSize);

    
    /**
	 * <li>功能描述：根据用户的编号、类型统计其所有的已获得科研成果/目前承担项目/已完成教研/正在进行教研情况
	 * @param kuid 用户ID
	 * @param type 已获得科研成果/目前承担项目/已完成教研/正在进行教研情况
	 * @param proType 1-横向，2-纵向
	 * @return
	 */
	public List findCountByKuidAndTypeAndKyclass(Long kuid,Integer type,String kyClass);
	
	 /**
	 * <li>功能描述：根据用户的编号、类型、级别统计其所有的已获得科研成果/目前承担项目/已完成教研/正在进行教研情况
	 * @param kuid 用户ID
	 * @param type 已获得科研成果/目前承担项目/已完成教研/正在进行教研情况
	 * @param proType 2-国家级，3-省部级
	 * @return
	 */
	public List findCountByKuidAndTypeAndKyScale(Long kuid,Integer type,String kyScale);
	
   /**
    * @param pageNum 当前页数
    * @param pageSize  每页条数
    * @return  成员项目列表分页
    */
    public List findByKuidAndType(Long kuid, Integer type,int pageNum, int pageSize ) ;
	public List findXmByKuidAndType(Long kuid,Integer type);
	
	/**
	 * 根据用户ID、项目级别和贡献排名查询获奖项目的记录总数
	 * @param kuid
	 * @param kyScale
	 * @param gx
	 * @return
	 */
	public int getHjCountByKuidGx(Long kuid,String kyScale, int gx);

	/**
	 * 根据用户ID、项目级别和贡献排名查询项目记录总数
	 * @param kuid
	 * @param kyScale
	 * @param gx
	 * @return
	 */
	public int getCountByKuidGx(Long kuid,String kyScale, int gx);
	
	/**
	 * 根据项目ID查询列表
	 * @param ids
	 * @param lx
	 * @return
	 */
	public List<GhXm> findByKyIdsType(String ids);
}
