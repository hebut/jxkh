package com.uniwin.framework.security;

/**
 * <li>Ȩ�޹������
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
	// �û����ݷ��ʽӿ�
	// public static UserService userService;
	// �������ݷ������
	public static TitleService titleService;
	// ϵͳĬ�ϵķ��ʵ�URL��ַ
	public static String[] defaultURL = { "/admin/jxkh/common/**", "/admin/login.zul", "/admin/pingyoushow.zul", "/admin/xueyuan.jsp", "/admin/pingyouxi.jsp", "/index.zul", "/alogin.zul", "/admin/common/**", "/timeout.zul", "/error.zul", "~./**", "/admin/resetPassword/**" };

	/**
	 * <li>����������session�е��û��Ƿ���Ȩ�޷���URL
	 * 
	 * @param url
	 *            �û����ʵ�URL��ַ
	 * @param session
	 *            �û���������
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
	// * <li>����������session�е��û��Ƿ���Ȩ�޷���URL
	// * @param url �û����ʵ�URL��ַ
	// * @param session �û���������
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
	 * <li>�����������ж�url�Ƿ���rlist�б��⡣
	 * 
	 * @param rlist
	 *            �û�Ȩ���б�
	 * @param url
	 *            �û�����URL
	 * @param user
	 *            ��ǰ�����û���use������¼���û�����
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
		log.info("�û�û�з���:" + url + "��Ȩ��");
		return false;
	}

	/**
	 * <li>������������õ�ǰ�û���Ȩ���б�����û�δ��½�򷵻ؿ�
	 * 
	 * @param user
	 *            ��ǰ�û�
	 * @return List �û�����Ȩ���б�
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
