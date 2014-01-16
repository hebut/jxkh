package org.iti.gh.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zkoss.zk.ui.Executions;

import com.uniwin.framework.entity.WkTUser;

public class KyglControlUtil {
	public static final String SPT = System.getProperty("file.separator");

	/** 
     * <li>功能描述：检查user是否登录。
     * @param HttpServletRequest
     * @param httpservletrequest
     * @author bobo
     * @serialData 2010-7-21    
     * @return
     */
	public static boolean checkSession(HttpServletRequest httpservletrequest){
		
		return httpservletrequest.getSession().getAttribute("user") != null;
	}	

	/** 
     * <li>功能描述：获得session用户。
     * @param HttpServletRequest
     * @param httpservletrequest
     * @author bobo
     * @serialData 2010-7-21    
     * @return
     */
	public static WkTUser getUser(HttpServletRequest httpservletrequest){
		
		return (WkTUser)httpservletrequest.getSession().getAttribute("user");
	}
	
	/** 
     * <li>功能描述：获得系统当前路径。
     * @param HttpServletRequest
     * @param httpservletrequest
     * @author bobo
     * @serialData 2010-7-21    
     * @return
     */
	public static String getPath(String path){
		String s = Executions.getCurrent().getDesktop().getWebApp().getRealPath(path);
		if (!s.endsWith(SPT)){
			s = s + SPT;
		}
		return s;
	}
	
	/**
     * 
     * <li>功能描述：读取cookie中指定的值。
     * @author bobo
     * @serialData 2010-7-21
     * @param pname
     * @return
     */
	public static String getProOfCookie(String pname){
		Cookie[] cookies = ((HttpServletRequest)Executions.getCurrent().getNativeRequest()).getCookies();
		String fs = "";
		if(cookies!=null){
			for(int i=0;i<cookies.length;i++){
				if(pname.equals(cookies[i].getName())){
					fs = cookies[i].getValue();
					
				}
			}
		}
		return fs;
	}
	
	/**
     * 
     * <li>功能描述：将指定的变量名和对应的变量值设置到cookie中。
     * @author bobo
     * @serialData 2010-7-21
     * @param pname
     * @param fs
     */
	public static void setProOfCookie(String pname,String fs){
		Cookie cookie = new Cookie(pname,fs);
	//	cookie.setMaxAge(60*60*24*30);//store 30 days
		String cp = Executions.getCurrent().getContextPath();
		cookie.setPath(cp);
		((HttpServletResponse)Executions.getCurrent().getNativeResponse()).addCookie(cookie);
	}
	
}
