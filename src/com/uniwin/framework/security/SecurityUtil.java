package com.uniwin.framework.security;

/**
 * <li>权限过滤组件
 * @author DaLei
 * @2010-3-16
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.util.AntUrlPathMatcher;
import org.springframework.security.util.UrlMatcher;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.common.util.ConvertUtil;
import com.uniwin.framework.entity.WkTTitle;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.TitleService;

//import com.uniwin.framework.service.UserService;
public class SecurityUtil {
	private static Log log = LogFactory.getLog(SecurityUtil.class);
	// 用户数据访问接口
	// public static UserService userService;
	// 标题数据访问组件
	public static TitleService titleService;
	// 系统默认的访问的URL地址
	public static String[] defaultURL = { "/admin/jxkh/common/**", "/admin/login.zul", "/admin/pingyoushow.zul", "/admin/xueyuan.jsp", "/admin/pingyouxi.jsp", "/index.zul", "/alogin.zul", "/admin/common/**", "/timeout.zul", "/error.zul", "~./**", "/admin/resetPassword/**" };

	/**
	 * <li>功能描述：session中的用户是否有权限访问URL
	 * 
	 * @param url
	 *            用户访问的URL地址
	 * @param session
	 *            用户存在其中
	 * @return boolean
	 * @author DaLei
	 * @2010-3-16
	 */
	public static boolean checkPermission(String url, HttpSession session) {
		if (url.endsWith("zkau")) {
			return true;
		}
		WkTUser user = (WkTUser) session.getAttribute("user");
		@SuppressWarnings("unchecked")
		List<WkTTitle> rlist = (List<WkTTitle>) session.getAttribute("ulist");
		if (rlist == null) {
			rlist = getUserTitleList(user);
			if (rlist.size() > 0)
				session.setAttribute("ulist", rlist);
		}
		return checkURL(rlist, url, user);
	}

	// /**
	// * <li>功能描述：session中的用户是否有权限访问URL
	// * @param url 用户访问的URL地址
	// * @param session 用户存在其中
	// * @return
	// * boolean
	// * @author DaLei
	// * @2010-3-16
	// */
	// public static boolean checkPermission(String url,Session session){
	// if(url.endsWith("zkau")){
	// return true;
	// }
	// //System.out.println("Executions open:"+url);
	// WkTUser user=(WkTUser)session.getAttribute("user");
	// List rlist=(List)session.getAttribute("ulist");
	// if(rlist==null){
	// rlist=getUserTitleList(user);
	// session.setAttribute("ulist", rlist);
	// }
	// return checkURL(rlist,url,user);
	// }
	/**
	 * <li>功能描述：判断url是否在rlist中标题。
	 * 
	 * @param rlist
	 *            用户权限列表
	 * @param url
	 *            用户访问URL
	 * @param user
	 *            当前访问用户，use用来记录改用户操作
	 * @return boolean
	 * @author DaLei
	 * @2010-3-16
	 */
	public static boolean checkURL(List<WkTTitle> rlist, String url, WkTUser user) {
		UrlMatcher urlMatcher = new AntUrlPathMatcher();
		for (int i = 0; i < rlist.size(); i++) {
			WkTTitle t = (WkTTitle) rlist.get(i);
			if (t.getKtContent() == null || t.getKtContent().trim().length() == 0) {
				continue;
			}
			// if(!url.startsWith("/")){
			// url="/"+url;
			// }
			if (urlMatcher.pathMatchesUrl(t.getKtContent().trim() + "**", url)) {
				log.info(user.getKuName() + " " + t.getKtName() + " " + ConvertUtil.convertTimeString(new Date()));
				return true;
			}
		}
		for (int i = 0; i < defaultURL.length; i++) {
			if (urlMatcher.pathMatchesUrl(defaultURL[i], url)) {
				return true;
			}
		}
		if (user != null)
			log.info(user.getKuId() + user.getKuName());
		log.info("用户没有访问:" + url + "的权限");
		return false;
	}

	/**
	 * <li>功能描述：获得当前用户的权限列表，如果用户未登陆则返回空
	 * 
	 * @param user
	 *            当前用户
	 * @return List 用户具有权限列表
	 * @author DaLei
	 * @2010-3-16
	 */
	public static List<WkTTitle> getUserTitleList(WkTUser user) {
		// if (userService == null) {
		// userService = (UserService) BeanFactory.getBean("userService");
		// }
		if (titleService == null) {
			titleService = (TitleService) BeanFactory.getBean("titleService");
		}
		List<WkTTitle> returnlist = new ArrayList<WkTTitle>();
		if (user != null) {
			returnlist = titleService.getTitlesOfUserAccess(user);
		}
		return returnlist;
	}
}
