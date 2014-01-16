package org.iti.jxkh.service.impl;

import java.util.List;

import org.iti.jxkh.entity.Jxkh_DataFile;
import org.iti.jxkh.entity.Jxkh_STitle;
import org.iti.jxkh.entity.Jxkh_UserDetail;
import org.iti.jxkh.service.UserDetailService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class UserDetailServiceImpl extends BaseServiceImpl implements UserDetailService {

	@SuppressWarnings("unchecked")
	@Override
	public List<Jxkh_UserDetail> findDetailByKuid(Long kuId) {
		String queryString = "from Jxkh_UserDetail as jud where jud.kuId = ?";
		return getHibernateTemplate().find(queryString,new Object[]{kuId});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Jxkh_DataFile> getFileByUser(Long userId,Integer fileType) {
		String queryString = "from Jxkh_DataFile as df where df.userId = ? and df.fileType = ?";
		return getHibernateTemplate().find(queryString,new Object[]{userId,fileType});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Jxkh_STitle> getUserSelectedTilte(Long userId) {
		String queryString = "from Jxkh_STitle as st where st.userId = ?";
		return getHibernateTemplate().find(queryString,new Object[]{userId});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void save(Long titleId, Long userId, Object entity){
		String queryString = "from Jxkh_STitle as st where st.titleId = ? and st.userId = ?";
		List<Jxkh_STitle> s = getHibernateTemplate().find(queryString,new Object[]{titleId, userId});
		if( s.size() == 0){
			getHibernateTemplate().save(entity);
		}
	}
}
