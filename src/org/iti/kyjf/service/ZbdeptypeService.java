package org.iti.kyjf.service;

import java.util.List;

import org.iti.kyjf.entity.Zbdeptype;

import com.uniwin.basehs.service.BaseService;

public interface ZbdeptypeService extends BaseService {
	/**
	 * query some department's  science type via Kdid
	 * ����Kdid��ѯĳһѧԺ��ѧ������
	 * @param Kdid
	 * @return
	 */
public List queryByKdid(Long Kdid);

public Zbdeptype queryByYearAndKdid(Integer year,Long kdid);
}
