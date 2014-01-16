package com.uniwin.basehs.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;

public interface AnnBaseService {
	public void save(Object entity);

	public void update(Object entity);

	public void delete(Object entity);
	
	public void delById(Class clazz,Serializable id);

	public void deleteAll(Collection c);

	public Object loadById(Class clazz, Serializable id);

	public List listAll(Class clazz);
	
	public Object loadObject(String hql);
	
	public Object loadObject(String hql,Object... parameters);
	
	public void saveOrUpdate(Object entity);
	
	public List listAll(String clazz,int pageNo,int pageSize);
	
	public int countAll(String clazz);
	
	public List query(String hql, Object... parameters);
	
	public List query(String hql,int pageNo,int pageSize);
	
	public int countQuery(String hql);
	
	public int update(String hql);

	public void excuteSql(String sql, Object... object);
	/**
	 *  返回一个HibernateTemplate实例
	 * @return
	 */
	public HibernateTemplate getHibernateTemplate();
	public Iterator iterator(String hql,Object...objects);
	/**
	 * 返回一个JdbcTemplate实例
	 * @return
	 */
	public JdbcTemplate getJdbcTemplate();
	
}
