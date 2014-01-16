package org.iti.jxgl.service;

import java.util.List;

import org.iti.jxgl.entity.JxCoursetype;
import org.iti.jxgl.entity.JxviewCoursetype;

import com.uniwin.basehs.service.BaseService;

public interface CoursetypeService extends BaseService {
	
	/**
	 * <li>功能描述：获得一级标题（课程大类）的名称和相应的编码，主要用于初始化查询条件
	 * 
	 * @return List
	 * @author wx
	 * @2010-7-9
	 */
	public List getCoursetypeFirst();

	/**
	 * <li>功能描述：根据二级标题的名称，查找二级标题对应的ID（映射关系）
	 * 
	 * @param name
	 *            二级标题名称
	 * @return String
	 * @author wx
	 * @2010-7-9
	 */
	public String FindCtidbyName(String name);

	/**
	 * <li>功能描述：根据一级标题查找下面的二级标题列表
	 * 
	 * @param firsttype
	 *            一级标题编号
	 * @return List
	 * @author wx
	 * @2010-7-9
	 */
	public List FindsecondtypebyFirst(String firsttype);

	/**
	 * <li>功能描述：根据类别编号查找某一个类别
	 * 
	 * @param Jctid
	 * @return 一个课程类别对象
	 * @author FengXinhong 2010-7-20
	 */

    public JxCoursetype findByJctid(String Jctid);
	/**
	 * <li>功能描述：根据类别编号查找某一个类别的孩子
	 * @param Jctid
	 * @return  一个课程类别对象
	 * @author FengXinhong
	 * 2010-7-20
	 */
    public List getChildByJctFid(String jctFid);
	/**
	 * <li>功能描述：得到新的jctid
	 * @param 
	 * @return  一个最大的Jctid+1
	 * @author 王星
	 * 2010-8-11
	 */
    public String getNewJctid();
	/**
	 * <li>功能描述：查重
	 * @param 一级标题编号，二级标题名称
	 * @return  存在则返回true，否则返回false
	 * @author 王星
	 * 2010-8-11
	 */
    public boolean CheckDuplicat(String jctFid,String jctName);

	/**
	 * <li>功能描述:检查excel表的课程大类和课程小类数据库里是否存在
	 * @param 一级标题名称，二级标题名称
	 * @return  不存在则返回标题名称，否则返回null
	 * @author 王星
	 * 2010-8-11
	 */
    public String[] CheckExit(String firstname,String secondName);
    public List checkScheExcel();
	/**
	 * <li>功能描述:删除jctFid下面的所有的课程类别
	 * @param 一级标题id
	 * @return  void
	 * @author 王星
	 * 2010-8-11
	 */
    public void DelTypeByFirst(String jctFid);
    /**
	 * <li>功能描述：查重
	 * @param 标题编号，标题名称
	 * @return  返回类型名称
	 * @author 贺亚
	 * 2010-10-25
	 */
    public String CheckDubiaoti(String jctFid,String jctName);
    /**
	 * <li>功能描述：查找类型名称
	 * @param 类型号
	 * @return  存在则返回true，否则返回false
	 * @author 王星
	 * 2010-8-11
	 */
    public String findprename(String jcid);
}
