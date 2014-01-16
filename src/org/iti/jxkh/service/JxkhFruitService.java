package org.iti.jxkh.service;

import java.util.List;

import org.iti.jxkh.entity.Jxkh_Fruit;
import org.iti.jxkh.entity.Jxkh_FruitDept;
import org.iti.jxkh.entity.Jxkh_FruitMember;

import com.uniwin.basehs.service.BaseService;

public interface JxkhFruitService extends BaseService {

	public List<Jxkh_Fruit> findFruitByKuLid(String nameSearch,
			Integer stateSearch, String year, String kuLid);

	/**
	 * <li>通过部门编号查找状态为未通过、部门通过和未通过的成果
	 */
	public int findFruitByKbNumber(String kbNumber);

	public List<Jxkh_Fruit> findFruitByKbNumberAndPaging(String kbNumber,
			int pageNum, int pageSize);

	/**
	 * <li>通过部门编号查找状态为未通过、部门通过和未通过的且主承担部门为该单位的成果
	 */
	public int findFruitByKbNumber2(String kbNumber);

	public List<Jxkh_Fruit> findFruitByKbNumberAndPaging2(String kbNumber,
			int pageNum, int pageSize);

	/**
	 * <li>通过部门编号查找成果
	 */
	public int findFruitByKbNumberAll(String kbNumber);

	public List<Jxkh_Fruit> findFruitByKbNumberAll(String kbNumber,
			int pageNum, int pageSize);

	/**
	 * <li>业务办初始化
	 */
	public int findFruitByState();

	public List<Jxkh_Fruit> findFruitByState(int pageNum, int pageSize);

	/**
	 * <li>业务办查找符合条件的所有成果
	 */
	public int findFruitByCondition(String nameSearch, Short stateSearch,
			Long type, String auditDep);

	public List<Jxkh_Fruit> findFruitByCondition(String nameSearch,
			Short stateSearch, Long type, String auditDep, int pageNum,
			int pageSize);

	/**
	 * <li>部门查找符合条件的所有本部门成果
	 */
	public int findFruitByCondition(String nameSearch, Short stateSearch,
			Long type, String year, String kbNumber);

	public List<Jxkh_Fruit> findFruitByConditionAndPaging(String nameSearch,
			Short stateSearch, Long type, String year, String kbNumber,
			int pageNum, int pageSize);

	public List<Jxkh_FruitMember> findFruitMemberByFruitId(Jxkh_Fruit fruit);

	public List<Jxkh_FruitDept> findFruitDeptByFruitId(Jxkh_Fruit fruit);
	
	/**
	 * 根据成果id查找其二级部门（除了科技情报研究院），信息审核页面和绩分指定页面要用到
	 */
	public List<Jxkh_FruitDept> findFruitDepByFruitId(Jxkh_Fruit fruit);

	/**
	 * <li>根据登录人员所属部门编号和成果查找Jxkh_FruitDept(部门添加业务时绩分页面用的service)
	 */
	public Jxkh_FruitDept findFruitDept(Jxkh_Fruit m, String wktDeptId);
}
