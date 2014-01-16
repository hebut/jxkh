package org.iti.projectmanage.science.reference.service.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.iti.gh.entity.GH_ARCHIVE;
import org.iti.projectmanage.science.reference.service.ReferenceService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class ReferenceServiceImpl extends BaseServiceImpl implements ReferenceService
{
	public List<GH_ARCHIVE> findByTKAPSF(int sourceType, int searchFiled,String content, 
			String startTime, String endTime,String clcString, String issueString,short category) {
		StringBuilder sb = new StringBuilder();
		sb.append("from GH_ARCHIVE as ar where ar.arType = 0 ");
		if(sourceType!=0)
			sb.append(" and ar.arSourceType = "+sourceType);
		if(searchFiled==0){
			sb.append(" and ar.arName ");
		}else if(searchFiled==1){
			sb.append(" and ar.arKeyWord ");
		}else if(searchFiled==2){
			sb.append(" and ar.arAuthor ");
		}else if(searchFiled==3){
			sb.append(" and ar.arSource ");
		}else if(searchFiled==4){
			sb.append(" and ar.arFundNum ");
		}
		sb.append(" like '%"+content+"%'");
		if(null!=startTime&&!"".equals(startTime))
		{
			sb.append(" and ar.arPostDate >"+startTime);
		}
		if(null!=endTime&&!"".equals(endTime))
		{
			sb.append(" and ar.arPostDate <"+endTime);
		}
		if(null!=clcString&&!"".equals(clcString))
		{
			sb.append(" and ar.arCLC like '%"+clcString+"%'");
		}
		if(null!=issueString&&!"".equals(issueString))
		{
			sb.append(" and ar.arIssue like '%"+issueString+"%'");
		}
		sb.append("and ar.arCategory = ? order by ar.arPostDate desc");
		return getHibernateTemplate().find(sb.toString(),new Object[]{category});
	}

	public List<GH_ARCHIVE> findByTKAPSFAndPage(int sourceType,	int searchFiled, String content, 
			String startTime, String endTime,String clcString, String issueString,short category,int pageNum, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		StringBuilder sb = new StringBuilder();
		sb.append("from GH_ARCHIVE as ar where ar.arType = 0 ");
		if(sourceType!=0)
			sb.append(" and ar.arSourceType = "+sourceType);
		if(searchFiled==0){
			sb.append(" and ar.arName ");
		}else if(searchFiled==1){
			sb.append(" and ar.arKeyWord ");
		}else if(searchFiled==2){
			sb.append(" and ar.arAuthor ");
		}else if(searchFiled==3){
			sb.append(" and ar.arSource ");
		}else if(searchFiled==4){
			sb.append(" and ar.arFundNum ");
		}
		sb.append(" like '%"+content+"%'");
		if(null!=startTime&&!"".equals(startTime))
		{
			sb.append(" and ar.arPostDate >"+startTime);
		}
		if(null!=endTime&&!"".equals(endTime))
		{
			sb.append(" and ar.arPostDate <"+endTime);
		}
		if(null!=clcString&&!"".equals(clcString))
		{
			sb.append(" and ar.arCLC like '%"+clcString+"%'");
		}
		if(null!=issueString&&!"".equals(issueString))
		{
			sb.append(" and ar.arIssue like '%"+issueString+"%'");
		}
		sb.append(" and ar.arCategory = ? order by ar.arPostDate desc");
		Query query = session.createQuery(sb.toString());
		query.setShort(0, category);
        query.setFirstResult(pageNum*pageSize);
        query.setMaxResults(pageSize);
        List<GH_ARCHIVE>  list = query.list();
        session.close();
        return list;
	}

	public List<Long> findCountByTKAPSF(int sourceType, int searchFiled, String content,
			String startTime, String endTime,String clcString, String issueString,short category) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		StringBuilder sb = new StringBuilder();
		sb.append("select count(ar.arId) from GH_ARCHIVE as ar where ar.arType = 0 ");
		if(sourceType!=0)
			sb.append(" and ar.arSourceType = "+sourceType);
		if(searchFiled==0){
			sb.append(" and ar.arName ");
		}else if(searchFiled==1){
			sb.append(" and ar.arKeyWord ");
		}else if(searchFiled==2){
			sb.append(" and ar.arAuthor ");
		}else if(searchFiled==3){
			sb.append(" and ar.arSource ");
		}else if(searchFiled==4){
			sb.append(" and ar.arFundNum ");
		}
		sb.append(" like '%"+content+"%'");
		if(null!=startTime&&!"".equals(startTime))
		{
			sb.append(" and ar.arPostDate >"+startTime);
		}
		if(null!=endTime&&!"".equals(endTime))
		{
			sb.append(" and ar.arPostDate <"+endTime);
		}
		if(null!=clcString&&!"".equals(clcString))
		{
			sb.append(" and ar.arCLC like '%"+clcString+"%'");
		}
		if(null!=issueString&&!"".equals(issueString))
		{
			sb.append(" and ar.arIssue like '%"+issueString+"%'");
		}
		sb.append(" and ar.arCategory = ?");
		Query query = session.createQuery(sb.toString());
		query.setShort(0, category);
		List<Long>  list = query.list();
        session.close();
        return list;
       
	}
	
}
