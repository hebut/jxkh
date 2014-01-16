package org.iti.jxkh.service;

import java.util.List;

import org.iti.jxkh.entity.Jxkh_DataFile;
import org.iti.jxkh.entity.Jxkh_STitle;
import org.iti.jxkh.entity.Jxkh_UserDetail;

import com.uniwin.basehs.service.BaseService;

public interface UserDetailService extends BaseService {
	/**
	 * 根据kuId找该用户的详细信息
	 * @param Long kuId
	 */
	public List<Jxkh_UserDetail> findDetailByKuid(Long kuId);
	/**
	 * 查找某位用户基本信息的附件
	 * @param userId
	 * @return
	 */
	public List<Jxkh_DataFile> getFileByUser(Long userId,Integer fileType);
	/**
	 * 查找用户在主页所选择的标题
	 * @param userId
	 * @return
	 */
	public List<Jxkh_STitle> getUserSelectedTilte(Long userId);
	/**
	 * 添加用户标题
	 */
	public void save(Long titleId, Long userId, Object entity );
}
