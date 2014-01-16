package org.iti.jxkh.service.impl;

import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.iti.jxkh.entity.Jxkh_Video;
import org.iti.jxkh.entity.Jxkh_VideoDept;
import org.iti.jxkh.entity.Jxkh_VideoFile;
import org.iti.jxkh.entity.Jxkh_VideoMember;
import org.iti.jxkh.service.JxkhVideoService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class JxkhVideoServiceImp extends BaseServiceImpl implements
		JxkhVideoService {

	@Override
	public List<Jxkh_Video> findVideoByKuLid(String nameSearch,
			Integer stateSearch, String year, String kuLid) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Video as a where 1=1");
		// if (kuLid != null && !kuLid.equals(""))
		// queryString.append("and a.submitId='" + kuLid + "'");
		queryString
				.append("and a.id in (select distinct m.video from Jxkh_VideoMember as m where m.personId='"
						+ kuLid + "')");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (stateSearch != null && !stateSearch.equals(""))
			queryString.append(" and a.state = '" + stateSearch + "'");
		if (year != null && !year.equals(""))
			queryString.append(" and a.jxYear = '" + year + "'");
		queryString.append(" order by a.jxYear desc, a.state asc,a.playTime desc ");
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Video> list = query.list();
		for (Jxkh_Video video : list) {
			List<Jxkh_VideoMember> videoMembers = video.getVideoMember();
			List<Jxkh_VideoDept> videoDepts = video.getVideoDept();
			Set<Jxkh_VideoFile> videoFile = video.getVideoFile();
			video.setVideoMember(videoMembers);
			video.setVideoDept(videoDepts);
			video.setVideoFile(videoFile);
			logger.debug(videoMembers.isEmpty());
			logger.debug(videoDepts.isEmpty());
			logger.debug(videoFile.isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public int findVideoByKbNumber(String kbNumber) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from Jxkh_Video as a where a.state in (0,1,2,3) and a.id in (select distinct m.video from Jxkh_VideoDept as m where m.deptId='"
						+ kbNumber + "')");
		session.close();
		return countQuery(queryString.toString());
	}

	@Override
	public int findVideoByState() {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Video as a where  a.state in (1,4,5,6) ");
		session.close();
		return countQuery(queryString.toString());
	}

	@Override
	public int findVideoByCondition(String nameSearch, Short stateSearch,
			String type, String depName) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Video as a where 1=1");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (stateSearch != null && !stateSearch.equals(""))
			queryString.append(" and a.state = '" + stateSearch + "'");
		if (type != null && !type.equals(""))
			queryString.append(" and a.type = '" + type + "'");
		if (depName != null && !depName.equals(""))
			queryString
					.append(" and a.id in (select distinct m.video from Jxkh_VideoDept as m where m.name='"
							+ depName + "')");
		session.close();
		return countQuery(queryString.toString());
	}

	@Override
	public int findVideoByCondition(String nameSearch, Short stateSearch,
			String type, String year, String kbNumber) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Video as a where 1=1");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (stateSearch != null && !stateSearch.equals(""))
			queryString.append(" and a.state = '" + stateSearch + "'");
		if (type != null && !type.equals(""))
			queryString.append(" and a.type = '" + type + "'");
		if (year != null && !year.equals(""))
			queryString.append(" and a.jxYear = '" + year + "'");
		queryString
				.append("and a.id in (select distinct m.video from Jxkh_VideoDept as m where m.deptId='"
						+ kbNumber + "')");
		session.close();
		return countQuery(queryString.toString());
	}

	@Override
	public List<Jxkh_VideoMember> findVideoMemberByVideoId(Jxkh_Video video) {
		String queryString = "from Jxkh_VideoMember as ad where ad.video=? order by rank";
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, video);
		List<Jxkh_VideoMember> result = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return result;
	}

	@Override
	public List<Jxkh_VideoDept> findVideoDeptrByVideoId(Jxkh_Video video) {
		String queryString = "from Jxkh_VideoDept as ad where ad.video=? order by rank";
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, video);
		List<Jxkh_VideoDept> result = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return result;
	}

	@Override
	public List<Jxkh_VideoDept> findVideoDeprByVideoId(Jxkh_Video video) {
		String queryString = "from Jxkh_VideoDept as ad where ad.deptId!='000' and ad.video=? order by rank";
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, video);
		List<Jxkh_VideoDept> result = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return result;
	}
	
	@Override
	public int findVideoByKbNumberAll(String kbNumber) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from Jxkh_Video as a where a.id in (select distinct m.video from Jxkh_VideoDept as m where m.deptId='"
						+ kbNumber + "')");
		session.close();
		return countQuery(queryString.toString());
	}

	@Override
	public List<Jxkh_Video> findVideoByKbNumber(String kbNumber, int pageNum,
			int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from Jxkh_Video as a where a.state in (0,1,2,3) and a.id in (select distinct m.video from Jxkh_VideoDept as m where m.deptId='"
						+ kbNumber + "')");
		queryString.append(" order by a.state,a.submitTime ");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Video> list = query.list();
		for (Jxkh_Video video : list) {
			List<Jxkh_VideoMember> videoMembers = video.getVideoMember();
			List<Jxkh_VideoDept> videoDepts = video.getVideoDept();
			Set<Jxkh_VideoFile> videoFile = video.getVideoFile();
			video.setVideoMember(videoMembers);
			video.setVideoDept(videoDepts);
			video.setVideoFile(videoFile);
			logger.debug(videoMembers.isEmpty());
			logger.debug(videoDepts.isEmpty());
			logger.debug(videoFile.isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Video> findVideoByKbNumberAll(String kbNumber,
			int pageNum, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from Jxkh_Video as a where a.id in (select distinct m.video from Jxkh_VideoDept as m where m.deptId='"
						+ kbNumber + "')");
		queryString.append(" order by a.state asc,a.jxYear desc,a.submitTime desc");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Video> list = query.list();
		for (Jxkh_Video video : list) {
			List<Jxkh_VideoMember> videoMembers = video.getVideoMember();
			List<Jxkh_VideoDept> videoDepts = video.getVideoDept();
			Set<Jxkh_VideoFile> videoFile = video.getVideoFile();
			video.setVideoMember(videoMembers);
			video.setVideoDept(videoDepts);
			video.setVideoFile(videoFile);
			logger.debug(videoMembers.isEmpty());
			logger.debug(videoDepts.isEmpty());
			logger.debug(videoFile.isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Video> findVideoByState(int pageNum, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Video as a where  a.state in (1,4,5,6,7) ");
		queryString.append(" order by a.jxYear desc, a.state,a.submitTime ");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Video> list = query.list();
		for (Jxkh_Video video : list) {
			List<Jxkh_VideoMember> videoMembers = video.getVideoMember();
			List<Jxkh_VideoDept> videoDepts = video.getVideoDept();
			Set<Jxkh_VideoFile> videoFile = video.getVideoFile();
			video.setVideoMember(videoMembers);
			video.setVideoDept(videoDepts);
			video.setVideoFile(videoFile);
			logger.debug(videoMembers.isEmpty());
			logger.debug(videoDepts.isEmpty());
			logger.debug(videoFile.isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Video> findVideoByCondition(String nameSearch,
			Short stateSearch, String type, String depName, int pageNum,
			int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Video as a where 1=1");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (stateSearch != null && !stateSearch.equals(""))
			queryString.append(" and a.state = '" + stateSearch + "'");
		if (type != null && !type.equals(""))
			queryString.append(" and a.type = '" + type + "'");
		if (depName != null && !depName.equals(""))
			queryString
					.append(" and a.id in (select distinct m.video from Jxkh_VideoDept as m where m.name='"
							+ depName + "')");
		queryString.append(" order by a.jxYear desc, a.state,a.submitTime ");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Video> list = query.list();
		for (Jxkh_Video video : list) {
			List<Jxkh_VideoMember> videoMembers = video.getVideoMember();
			List<Jxkh_VideoDept> videoDepts = video.getVideoDept();
			Set<Jxkh_VideoFile> videoFile = video.getVideoFile();
			video.setVideoMember(videoMembers);
			video.setVideoDept(videoDepts);
			video.setVideoFile(videoFile);
			logger.debug(videoMembers.isEmpty());
			logger.debug(videoDepts.isEmpty());
			logger.debug(videoFile.isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Video> findVideoByCondition(String nameSearch,
			Short stateSearch, String type, String year, String kbNumber,
			int pageNum, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Video as a where 1=1");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (stateSearch != null && !stateSearch.equals(""))
			queryString.append(" and a.state = '" + stateSearch + "'");
		if (type != null && !type.equals(""))
			queryString.append(" and a.type = '" + type + "'");
		if (year != null && !year.equals(""))
			queryString.append(" and a.jxYear = '" + year + "'");
		queryString
				.append("and a.id in (select distinct m.video from Jxkh_VideoDept as m where m.deptId='"
						+ kbNumber + "') order by a.state asc,a.jxYear desc,a.submitTime desc");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Video> list = query.list();
		for (Jxkh_Video video : list) {
			List<Jxkh_VideoMember> videoMembers = video.getVideoMember();
			List<Jxkh_VideoDept> videoDepts = video.getVideoDept();
			Set<Jxkh_VideoFile> videoFile = video.getVideoFile();
			video.setVideoMember(videoMembers);
			video.setVideoDept(videoDepts);
			video.setVideoFile(videoFile);
			logger.debug(videoMembers.isEmpty());
			logger.debug(videoDepts.isEmpty());
			logger.debug(videoFile.isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public int findVideoByKbNumber2(String kbNumber) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from Jxkh_Video as a where a.state in (0,1,2,3) and a.id in (select distinct m.video from Jxkh_VideoDept as m where m.deptId='"
						+ kbNumber + "')");// and m.rank=1
		session.close();
		return countQuery(queryString.toString());
	}

	@Override
	public List<Jxkh_Video> findVideoByKbNumber2(String kbNumber, int pageNum,
			int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from Jxkh_Video as a where a.state in (0,1,2,3) and a.id in (select distinct m.video from Jxkh_VideoDept as m where m.deptId='"
						+ kbNumber + "')");// and m.rank=1
		queryString.append(" order by a.state asc,a.jxYear desc,a.submitTime desc");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Video> list = query.list();
		for (Jxkh_Video video : list) {
			List<Jxkh_VideoMember> videoMembers = video.getVideoMember();
			List<Jxkh_VideoDept> videoDepts = video.getVideoDept();
			Set<Jxkh_VideoFile> videoFile = video.getVideoFile();
			video.setVideoMember(videoMembers);
			video.setVideoDept(videoDepts);
			video.setVideoFile(videoFile);
			logger.debug(videoMembers.isEmpty());
			logger.debug(videoDepts.isEmpty());
			logger.debug(videoFile.isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public Jxkh_VideoDept findVideoDept(Jxkh_Video m, String wktDeptId) {
		String queryString = "from Jxkh_VideoDept where video=? and deptId=? ";
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, m);
		query.setParameter(1, wktDeptId);
		@SuppressWarnings("unchecked")
		List<Jxkh_VideoDept> resultList = query.list();
		Jxkh_VideoDept result = null;
		if (resultList.size() != 0) {
			result = resultList.get(0);
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return result;
	}
}
