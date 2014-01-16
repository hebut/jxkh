package org.iti.jxkh.service;

import java.util.List;

import org.iti.jxkh.entity.Jxkh_Video;
import org.iti.jxkh.entity.Jxkh_VideoDept;
import org.iti.jxkh.entity.Jxkh_VideoMember;

import com.uniwin.basehs.service.BaseService;

public interface JxkhVideoService extends BaseService {

	public List<Jxkh_Video> findVideoByKuLid(String nameSearch,
			Integer stateSearch, String type, String kuLid);

	public int findVideoByKbNumber(String kbNumber);

	public List<Jxkh_Video> findVideoByKbNumber(String kbNumber, int pageNum,
			int pageSize);

	public int findVideoByKbNumber2(String kbNumber);

	public List<Jxkh_Video> findVideoByKbNumber2(String kbNumber, int pageNum,
			int pageSize);

	public int findVideoByKbNumberAll(String kbNumber);

	public List<Jxkh_Video> findVideoByKbNumberAll(String kbNumber,
			int pageNum, int pageSize);

	public int findVideoByState();

	public List<Jxkh_Video> findVideoByState(int pageNum, int pageSize);

	// 业务办查找
	public int findVideoByCondition(String nameSearch, Short stateSearch,
			String type, String auditDep);

	public List<Jxkh_Video> findVideoByCondition(String nameSearch,
			Short stateSearch, String type, String auditDep, int pageNum,
			int pageSize);

	// 部门--查找
	public int findVideoByCondition(String nameSearch, Short stateSearch,
			String type, String year, String kbNumber);

	public List<Jxkh_Video> findVideoByCondition(String nameSearch,
			Short stateSearch, String type, String year, String kbNumber,
			int pageNum, int pageSize);

	public List<Jxkh_VideoMember> findVideoMemberByVideoId(Jxkh_Video video);

	public List<Jxkh_VideoDept> findVideoDeptrByVideoId(Jxkh_Video video);
	
	/**
	 * 根据影视的Id查找其二级部门（除了河北省科技研究情报员）——信息审核页和绩分指定页要用到
	 */
	public List<Jxkh_VideoDept> findVideoDeprByVideoId(Jxkh_Video video);

	/**
	 * <li>根据登录人员所属部门编号和影视查找Jxkh_VideoDept(部门添加业务时绩分页面用的service)
	 */
	public Jxkh_VideoDept findVideoDept(Jxkh_Video m, String wktDeptId);
}
