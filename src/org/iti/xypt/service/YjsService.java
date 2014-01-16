package org.iti.xypt.service;

import java.util.List;
import org.iti.xypt.entity.Yjs;
import com.uniwin.basehs.service.BaseService;

public interface YjsService extends BaseService {

	public List findBydeplistAndrid(List dlist, Long rid, Integer grade, String tname, String tno);

	public List findBydeplistAndrid(List dlist, Long rid, Integer grade, String tname, String tno, String isguofang);

	// public List findGrade(String kdid);
	public List findgradeByKdid(Long kdid);

	public List findBygradeAndkdidAndNameAndStidNotInDb(Integer grade, Long kdid, String name, String stid);

	public List findzy();

	/**
	 * <li>��������:�����û�id��ѯ�о�������
	 * @param kuid �û�id
	 * @return
	 */
	public Yjs findByKuid(Long kuid);

	/**
	 * 
	 * @param grade
	 * @param kdid
	 * @return
	 */
	public List findByGradeAndKdid(Integer grade, Long kdid);

	// ͨ��ѧУ��Ų��������о�������
	public List findgradebykdid(Long kdid);

	/**
	 * <li>��������:����רҵid��ѯ������о���
	 * @param kdid רҵid
	 * @return
	 */
	public List findNotinDbByKdid(Long kdid, Integer grade);

	public List findInDbByKdid(Long kdid, Integer grade,Long kuid);
	/**
	 * ��ѯû��ѡ���Լ���˫ѡ
	 * @param kdid
	 * @param grade
	 * @param kuid
	 * @return
	 */
	public List findNoChoiceByKdidGradeAndKuid(Long kdid, Integer grade,Long kuid);
}
