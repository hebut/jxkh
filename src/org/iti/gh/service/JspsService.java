package org.iti.gh.service;

import java.util.List;

import org.iti.gh.entity.GhJsps;

import com.uniwin.basehs.service.BaseService;

public interface JspsService extends BaseService {

	/**
	 * �����û�ID��ѯ�û���֧��������Ϣ�б�
	 * @param kuid
	 * @return
	 */
	public List<GhJsps> findByKuid(Long kuid);
	
	/**
	 * �����û�ID��������ݻ��������Ϣ
	 * @param kuid
	 * @param year
	 * @return
	 */
	public GhJsps getByKuidYear(Long kuid,Integer year);
	
	/**
	 * ������ݲ�ѯ�������ְ����������Ľ�ʦ�б�
	 * @param year
	 * @return
	 */
	public List<GhJsps> findByYear(Integer year);
	
	/**
	 * ������ݷ�ҳ��ѯ�������ְ����������Ľ�ʦ�б�
	 * @param year
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<GhJsps> findByYearAndPage(Integer year,int pageNum,int pageSize);
}
