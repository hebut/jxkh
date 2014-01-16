package org.iti.jxkh.service;

import java.util.List;

import org.iti.jxkh.entity.Jxkh_DataFile;
import org.iti.jxkh.entity.Jxkh_STitle;
import org.iti.jxkh.entity.Jxkh_UserDetail;

import com.uniwin.basehs.service.BaseService;

public interface UserDetailService extends BaseService {
	/**
	 * ����kuId�Ҹ��û�����ϸ��Ϣ
	 * @param Long kuId
	 */
	public List<Jxkh_UserDetail> findDetailByKuid(Long kuId);
	/**
	 * ����ĳλ�û�������Ϣ�ĸ���
	 * @param userId
	 * @return
	 */
	public List<Jxkh_DataFile> getFileByUser(Long userId,Integer fileType);
	/**
	 * �����û�����ҳ��ѡ��ı���
	 * @param userId
	 * @return
	 */
	public List<Jxkh_STitle> getUserSelectedTilte(Long userId);
	/**
	 * ����û�����
	 */
	public void save(Long titleId, Long userId, Object entity );
}
