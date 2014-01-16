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
	 * <li>ͨ�����ű�Ų���״̬Ϊδͨ��������ͨ����δͨ���ĳɹ�
	 */
	public int findFruitByKbNumber(String kbNumber);

	public List<Jxkh_Fruit> findFruitByKbNumberAndPaging(String kbNumber,
			int pageNum, int pageSize);

	/**
	 * <li>ͨ�����ű�Ų���״̬Ϊδͨ��������ͨ����δͨ���������е�����Ϊ�õ�λ�ĳɹ�
	 */
	public int findFruitByKbNumber2(String kbNumber);

	public List<Jxkh_Fruit> findFruitByKbNumberAndPaging2(String kbNumber,
			int pageNum, int pageSize);

	/**
	 * <li>ͨ�����ű�Ų��ҳɹ�
	 */
	public int findFruitByKbNumberAll(String kbNumber);

	public List<Jxkh_Fruit> findFruitByKbNumberAll(String kbNumber,
			int pageNum, int pageSize);

	/**
	 * <li>ҵ����ʼ��
	 */
	public int findFruitByState();

	public List<Jxkh_Fruit> findFruitByState(int pageNum, int pageSize);

	/**
	 * <li>ҵ�����ҷ������������гɹ�
	 */
	public int findFruitByCondition(String nameSearch, Short stateSearch,
			Long type, String auditDep);

	public List<Jxkh_Fruit> findFruitByCondition(String nameSearch,
			Short stateSearch, Long type, String auditDep, int pageNum,
			int pageSize);

	/**
	 * <li>���Ų��ҷ������������б����ųɹ�
	 */
	public int findFruitByCondition(String nameSearch, Short stateSearch,
			Long type, String year, String kbNumber);

	public List<Jxkh_Fruit> findFruitByConditionAndPaging(String nameSearch,
			Short stateSearch, Long type, String year, String kbNumber,
			int pageNum, int pageSize);

	public List<Jxkh_FruitMember> findFruitMemberByFruitId(Jxkh_Fruit fruit);

	public List<Jxkh_FruitDept> findFruitDeptByFruitId(Jxkh_Fruit fruit);
	
	/**
	 * ���ݳɹ�id������������ţ����˿Ƽ��鱨�о�Ժ������Ϣ���ҳ��ͼ���ָ��ҳ��Ҫ�õ�
	 */
	public List<Jxkh_FruitDept> findFruitDepByFruitId(Jxkh_Fruit fruit);

	/**
	 * <li>���ݵ�¼��Ա�������ű�źͳɹ�����Jxkh_FruitDept(�������ҵ��ʱ����ҳ���õ�service)
	 */
	public Jxkh_FruitDept findFruitDept(Jxkh_Fruit m, String wktDeptId);
}
