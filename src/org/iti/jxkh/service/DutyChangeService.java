package org.iti.jxkh.service;

import java.util.List;

import org.iti.jxkh.entity.JXKH_PerTrans;

import com.uniwin.basehs.service.BaseService;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;

public interface DutyChangeService extends BaseService {
	/**
	 * ���ҳ�ĳ�������е���Ա������Ϣ
	 * @param Long dept
	 * @author WeifangWang
	 */
	public List<JXKH_PerTrans> findDutyChangeByDept(Long dept);
	/**
	 * ��ҳ��ѯĳ�������е���Ա������Ϣ
	 * @param Long dept
	 * @param int pageNum
	 * @param int pageSize
	 * @author WeifangWang
	 */
	public List<JXKH_PerTrans> findChangeByPage(Long dept,int pageNum, int pageSize);
	/**
	 * ���ҳ�ĳ�˵���ص�����ʷ��Ϣ
	 * @param kuId
	 * @return
	 */
	public List<JXKH_PerTrans> findChangeByKuid(Long kuId);
	/**
	 * ��ҳ���������û�
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<WkTUser> findUser(String kuName,String staffId,Long kdId);
	public List<WkTUser> findUserByPage(int pageNum, int pageSize);
	/**
	 * �������Ʋ��Ҳ���
	 * @param deptName
	 * @return
	 */
	public List<WkTDept> findAllDept(String deptName);
	/**
	 * ������û���ĳ��ĳҵ�����ĳ���ŵķ�ֵ
	 * @param deptNum
	 * @param userNum
	 * @param year
	 * @param type
	 * @return
	 */
	public float getAllScoreByDeptAndUserAndYear(String deptNum,String userNum,String year,short type);
	/**
	 * �õ����û���ĳ�����ĳ���ŵ�����ҵ���Ա��¼
	 * @param deptNum
	 * @param userNum
	 * @param year
	 * @param type
	 * @return
	 */
	public List<Object> getAllMemberByDeptAndUserAndYear(String deptNum,String userNum,String year,short type);
}
