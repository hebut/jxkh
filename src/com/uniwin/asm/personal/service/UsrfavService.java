package com.uniwin.asm.personal.service;

import java.util.List;

import com.uniwin.basehs.service.BaseService;

public interface UsrfavService extends BaseService {
	public List getUsrfavList(Long kuid);// 根据登录用户获得该用户收藏列表

	public List getUsrfavTitleList(Long kuid);// 根据登录用户获得该用户标题列表

	public List getUsrfavDiyList(Long kuid);// 根据登录用户获得该用户自定义列表
}
