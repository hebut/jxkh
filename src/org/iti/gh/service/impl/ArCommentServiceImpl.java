package org.iti.gh.service.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.iti.gh.entity.GH_ARCHIVECOMMENT;
import org.iti.gh.service.ArCommentService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class ArCommentServiceImpl extends BaseServiceImpl implements ArCommentService {

	public List<GH_ARCHIVECOMMENT> findByArId(Long arId) {
		String queryString = "from GH_ARCHIVECOMMENT as comment where comment.arId = ? order by arId.commDate";
		return getHibernateTemplate().find(queryString,new Object[]{arId});
	}

	public boolean deleteCommentsByArchive(Long arId) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		String queryString = "delete from GH_ARCHIVECOMMENT as comment where comment.arId = ?";
		Query dAuth = session.createQuery(queryString.toString());
        dAuth.setParameter(0, arId);
        if (dAuth.executeUpdate() < 0)
        {
            session.close();
            return false;
        } else
        {
            session.close();
            return true;
        }
	}
}
