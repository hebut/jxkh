package com.uniwin.basehs.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.uniwin.basehs.service.AnnBaseService;

@Component("annbaseService")
public class AnnBaseServiceImpl implements AnnBaseService {
	public JdbcTemplate jdbcTemplate;
	public HibernateTemplate hibernateTemplate;
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	@Resource(name="hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	@Resource
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	public JdbcTemplate getJdbcTemplate(){
		return jdbcTemplate;
	}
	public void excuteSql(String sql, Object... object) {
		jdbcTemplate.update(sql, object);
	}
	public int countAll(String clazz) {
		final String hql="select count(*) from "+clazz+" as a ";
		Long count=(Long)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				return session.createQuery(hql).setMaxResults(1).uniqueResult();
			}
		});
		return count.intValue();
	}

	public int countQuery(String hql) {
		final String counthql=hql;
		Long count=(Long)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				return session.createQuery(counthql).setMaxResults(1).uniqueResult();
			}
		});
		return count.intValue();
	}

	public void delById(Class clazz, Serializable id) {
		getHibernateTemplate().delete(getHibernateTemplate().load(clazz, id));
	}

	public void delete(Object entity) {
		getHibernateTemplate().delete(entity);
	}

	public void deleteAll(Collection c) {
		getHibernateTemplate().deleteAll(c);
	}

	public List listAll(Class clazz) {
		return getHibernateTemplate().find("from "+clazz+" as a order by a.id desc");
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

	public Object loadById(Class clazz, Serializable id) {
		return getHibernateTemplate().get(clazz, id);
	}

	public Object loadObject(String hql) {
		final String newhql=hql;
		Object obj=null;
		List list=getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				return session.createQuery(newhql).list();
			}
		});
		if(list.size()>0)obj=list.get(0);
		return obj;
	}

	public List query(String hql, Object... parameters) {
		return getHibernateTemplate().find(hql, parameters);
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

	public void save(Object entity) {
		getHibernateTemplate().save(entity);
	}

	public void saveOrUpdate(Object entity) {
		getHibernateTemplate().saveOrUpdate(entity);
	}

	public void update(Object entity) {
		getHibernateTemplate().update(entity);
	}

	public int update(String hql) {
		final String finalhql=hql;
		return ((Integer)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				return session.createQuery(finalhql).executeUpdate();
			}
		})).intValue();
	}
	public Object loadObject(String hql, Object... parameters) {
		Object obj=null;
		List list=getHibernateTemplate().find(hql, parameters);
		if(list.size()>0)obj=list.get(0);
		return obj;
	}
	public Iterator iterator(String hql,Object...objects) {
		return getHibernateTemplate().iterate(hql, objects);
	}
}

