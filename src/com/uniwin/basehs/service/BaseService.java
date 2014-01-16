package com.uniwin.basehs.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.uniwin.basehs.util.PageBean;
import com.uniwin.basehs.util.PageResult;

public interface BaseService {
	public void save(Object entity);

	public void update(Object entity);

	public void delete(Object entity);

	public void deleteAll(Collection c);

	public Object get(Class clazz, Serializable id);

	public List findAll(Class clazz);

	public List findByNamedQuery(String query, Object[] parameters);

	public List find(String query, Object[] parameters);

	public List findAllByCriteria(DetachedCriteria detachedCriteria);

	public int getCountByCriteria(DetachedCriteria detachedCriteria);

	public PageResult find(PageBean pageBean, String query, Object[] parameters);
	
	public List findByPrimaryKey(String keyName,String keyType,Object keyValue,String entity,Map params,String others);
	
	public void updatesql(String sql,Object[] o);
	public void saveOrUpdate(Object entity);
	public List executeHql(String hql);
	public List listAll(String clazz,int pageNo,int pageSize);
	public List query(String hql,int pageNo,int pageSize);
	public int countQuery(String hql);
}
