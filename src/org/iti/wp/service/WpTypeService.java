package org.iti.wp.service;

import java.util.List;

import com.uniwin.basehs.service.BaseService;

public interface WpTypeService extends BaseService {
   public List findbykdid(Long kdid);
   public List findbykdidAndname(Long kdid,String name);

}
