package com.uniwin.basehs.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.uniwin.basehs.service.BaseService;
import com.uniwin.basehs.util.PageBean;
import com.uniwin.basehs.util.PageResult;

public class BaseServiceImpl extends PageSupport implements BaseService {
	public JdbcTemplate jdbcTemplate;

	public void updatesql(String sql,Object[] o) {
		jdbcTemplate.update(sql, o);
	}

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	
	public void save(final Object entity) {
		getHibernateTemplate().save(entity);
	}

	public void update(final Object entity) {
		getHibernateTemplate().update(entity);
	}

	public void delete(final Object entity) {
		getHibernateTemplate().delete(entity);
	}

	public void deleteAll(Collection c) {
		getHibernateTemplate().deleteAll(c);
	}

	public Object get(final Class entity, final Serializable id) {
		return (Object) getHibernateTemplate().get(entity, id);
	}

	public List findAll(final Class entity) {
		return getHibernateTemplate().find("from " + entity.getName());
	}

	public List findByNamedQuery(final String query, final Object[] parameters) {
		return getHibernateTemplate().findByNamedQuery(query, parameters);
	}

	public List find(final String query) {
		return getHibernateTemplate().find(query);
	}

	public List find(final String query, final Object[] parameters) {
		return getHibernateTemplate().find(query, parameters);
	}

	public List findByNamedQueryAndValueBean(final String queryName, final Object valueBean) {
		return getHibernateTemplate().findByNamedQueryAndValueBean(queryName, valueBean);
	}

	public List findAllByCriteria(final DetachedCriteria detachedCriteria) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Criteria criteria = detachedCriteria.getExecutableCriteria(session);
				return criteria.list();
			}
		});
	}

	public int getCountByCriteria(final DetachedCriteria detachedCriteria) {
		Integer count = (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Criteria criteria = detachedCriteria.getExecutableCriteria(session);
				return criteria.setProjection(Projections.rowCount()).uniqueResult();
			}
		});
		return count.intValue();
	}

	public PageResult find(PageBean pageBean, String query, Object[] parameters) {
		return this.getPageTemplate().find(query, parameters, pageBean);
	}
	
	public List findByPrimaryKey(String keyName,String keyType,Object keyValue,String entity,Map params,String others){
		String queryString="from "+entity+" as entity where entity."+keyName+"=?";
		String o="";
		if (others==null){
			o="";
		}else{
			o=others;
		}
		try {
			if(!(params==null)){
				queryString+=" and entity."+(String)params.get("typeColName")+"=?";
				queryString=queryString+" "+o;
				return getHibernateTemplate().find(queryString, new Object[]{Class.forName(keyType).cast(keyValue),params.get("typeColValue")});
			}
			else{
				queryString=queryString+" "+o;
				return getHibernateTemplate().find(queryString, new Object[]{Class.forName(keyType).cast(keyValue)});
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void saveOrUpdate(Object entity) {
		getHibernateTemplate().saveOrUpdate(entity);
	}

	public List executeHql(String hql) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List list=session.createQuery(hql).list();
		Hibernate.initialize(list);
		session.getTransaction().commit();
		session.close();
		return list;
	}
	
	public List listAll(String clazz, int pageNo, int pageSize) {
		final int pNo=pageNo;
		final int pSize=pageSize;
		final String hql ="from "+clazz+" as a order by a.id desc";
		List list=getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				List result=session.createQuery(hql).setMaxResults(pSize).setFirstResult((pNo-1)*pSize).list();
				if(!Hibernate.isInitialized(result))Hibernate.initialize(result);
				return result;
			}
		});
		return list;
	}
	
	public List query(String hql, int pageNo, int pageSize) {
		final int pNo=pageNo;
		final int pSize=pageSize;
		final String finalhql =hql;
		List list=getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				List result=session.createQuery(finalhql).setMaxResults(pSize).setFirstResult((pNo-1)*pSize).list();
				if(!Hibernate.isInitialized(result))Hibernate.initialize(result);
				return result;
			}
		});
		return list;
	}

	public int countQuery(String hql) {
		final String counthql="select count(*)"+hql;
		Long count=(Long)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				return session.createQuery(counthql).setMaxResults(1).uniqueResult();
			}
		});
		return count.intValue();
	}
	
	public Session getHibernateSession() {
		return getHibernateTemplate().getSessionFactory().getCurrentSession();
	}
}
