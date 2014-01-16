package com.uniwin.framework.ui.login;

/**
 * <li>�û���¼���,��Ӧҳ��admin/login.zul
 * @author DaLei
 * @2010-3-15
 */
import java.awt.Font;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Captcha;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import com.uniwin.common.util.DateUtil;
import com.uniwin.common.util.EncodeUtil;
import com.uniwin.framework.common.fileuploadex.SetFileUploadError;
import com.uniwin.framework.entity.WkTMlog;
import com.uniwin.framework.entity.WkTSite;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.MLogService;
import com.uniwin.framework.service.UserService;
import com.uniwin.framework.service.WebsiteService;

public class UserLogin2Window extends Window implements AfterCompose {

	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(UserLogin3Window.class);
	// �û���������
	Textbox j_username, j_password, cpatb;
	// �����֤��
	Captcha cpa;
	// �û����ݷ��ʽӿ�
	UserService userService;
	// ��������û�����������ܺ��ַ���
	String password;
	// �û���½IP
	String loginIP;
	MLogService mlogService;
	WebsiteService websiteService;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		// �����ļ��ϴ��������Ϊ30M
		Integer maxSize = 30 * 1024;
		this.getDesktop().getWebApp().getConfiguration().setMaxUploadSize(maxSize.intValue());
		String fileTooLarge = "�ϴ����ļ�����,�ļ���СӦС��30M";
		String confPath = Sessions.getCurrent().getWebApp().getRealPath("/") + "/WEB-INF/classes/conf.properties";
		SetFileUploadError.SetErrorToConf(fileTooLarge, confPath, "fileTooLarge");
		// ������֤����ʾ
		cpa.addFont(new Font("TimesNewRoman", Font.BOLD, 20));
		cpa.setWidth("100px");
		cpa.setHeight("25px");
		// cpa.randomValue();
		cpa.setValue(String.valueOf(Math.random() * 9000 + 1000).substring(0, 4));
		cpa.addEventListener(Events.ON_CLICK, new EventListener() {

			public void onEvent(Event arg0) throws Exception {
				cpa.setValue(String.valueOf(Math.random() * 9000 + 1000).substring(0, 4));
			}
		});
		loginIP = Executions.getCurrent().getRemoteAddr();
		// �Զ���½
		String uanme = getProOfCookie("webkeyName");
		String passw = getProOfCookie("webkeyPass");
		if (uanme.length() > 0) {
			List<WkTUser> ulist = userService.loginUser(uanme, passw);
			if (ulist == null || ulist.size() == 0) {
			} else {
				WkTUser user = (WkTUser) ulist.get(0);
				if (user.getKuAutoenter().trim().equalsIgnoreCase("1")) {
					try {
						checkLogin(user);
						loginSucess(user);
						Sessions.getCurrent().removeAttribute("ulist");
						return;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		// System.out.println(EncodeUtil.encodeByDES(new Date().getTime()));
		// ʹ�û��ڽ�������س���½
		this.addEventListener(Events.ON_OK, new EventListener() {

			public void onEvent(Event arg0) throws Exception {
				// loginButton.setSrc("/images/Login/login2.png");
				onClick$loginButton();
			}
		});
	}

	// ��ȡ��������mac��ַ
	private static String getMacOnWindow() {
		String s = "";
		try {
			String s1 = "ipconfig /all";
			Process process = Runtime.getRuntime().exec(s1);
			BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String nextLine;
			for (String line = bufferedreader.readLine(); line != null; line = nextLine) {
				nextLine = bufferedreader.readLine();
				if (line.indexOf("Physical Address") <= 0) {
					continue;
				}
				int i = line.indexOf("Physical Address") + 36;
				s = line.substring(i);
				break;
			}
			bufferedreader.close();
			process.waitFor();
		} catch (Exception exception) {
			s = "";
		}
		return s.trim();
	}

	/**
	 * <li>�����������û��û�����������ȷ֮��һЩ�����жϡ�
	 * 
	 * @param user
	 * @throws Exception void
	 * @author DaLei
	 * @2010-3-24
	 */
	public void checkLogin(WkTUser user) throws Exception {
		if (user.getKuLimit() != null && user.getKuLimit().intValue() > 0) {
			if (user.getKuLtimes().intValue() > user.getKuLimit().intValue()) {
				throw new WrongValueException(j_username, "���û���½��������");
			}
		}
		if (user.getKuBindtype() != null && user.getKuBindtype().trim().equalsIgnoreCase("1")) {
			if (!loginIP.equalsIgnoreCase(user.getKuBindaddr().trim())) {
				throw new WrongValueException("��½IP��ַ������:" + loginIP);
			}
		}
		if (!user.getKuStatus().trim().equalsIgnoreCase("1")) {
			throw new WrongValueException(j_username, "���û����˺�״̬������");
		}
	}

	/**
	 * <li>����������user��½�ɹ���һЩ����
	 * 
	 * @param user void
	 * @author DaLei
	 * @2010-3-24
	 */
	public void loginSucess(WkTUser user) {
		Sessions.getCurrent().setAttribute("lastIP", user.getKuLastaddr());
		user.setKuLastaddr(loginIP);
		user.setKuLtimes(user.getKuLtimes() + 1);
		user.setKuLtime(DateUtil.getDateTimeString(new Date()));
		userService.update(user);
		((HttpSession) Sessions.getCurrent().getNativeSession()).setAttribute("user", user);
		log.info("[�û���½] ID:" + user.getKuLid().trim() + "  IP:" + loginIP);
		// mlogService.saveMLog(WkTMlog.FUNC_ADMIN, "�û���½�ɹ����û���:" + user.getKuLid() + "���û�:" + user.getKuName(), user);
		// dongyf 2011-3-19 ���Ӳ���id
		mlogService.saveMLog(WkTMlog.FUNC_ADMIN, "�û���:" + user.getKuLid() + "���û�:" + user.getKuName() + "�����룺" + user.getKuPasswd() + "����:" + user.getYuDept(), user);
		Executions.getCurrent().sendRedirect("/admin/index.zul");
	}

	public void onClick$loginButton() throws Exception {
		if (j_username.getValue().length() == 0) {
			return;
		}
		if (!cpatb.getValue().toLowerCase().equals(cpa.getValue().toLowerCase())) {
			throw new WrongValueException(cpa, "��֤�����!");
		}
		password = EncodeUtil.encodeByMD5(j_password.getValue().toString().trim());
		List<WkTUser> ulist = userService.loginUser(j_username.getValue().toString().trim());
		String mac = EncodeUtil.encodeByDES(getMacOnWindow().trim());
		List lists = websiteService.findByMac(mac);
		if (lists.size() == 0) {
			throw new WrongValueException(j_username, "����վ��δ��Ȩ������ϵ����Ա");
		} else {
			WkTSite site = (WkTSite) lists.get(0);
			Long endtime = Long.valueOf(EncodeUtil.decodeByDESStr(site.getKsEndtime()));
			Date now = new Date();
			//System.out.println(EncodeUtil.encodeByDES(now.getTime()));
			if (endtime + 24 * 3600 * 1000 < now.getTime()) {
				throw new WrongValueException(j_username, "����վ��Ȩʱ���ѵ�������ϵ����Ա");
			} else {
				if (ulist == null || ulist.size() == 0) {
					throw new WrongValueException(j_username, "�û������������");
				}
				if (ulist.size() > 1) {
					throw new WrongValueException(j_username, "�˻��쳣������ϵ����Ա");
				}
				List<WkTUser> ulist0 = userService.loginUser(j_username.getValue(), password);
				if (ulist0 == null || ulist0.size() == 0) {
					throw new WrongValueException(j_password, "�û������������");
				}
				WkTUser user = (WkTUser) ulist0.get(0);
				setProOfCookie("webkeyName", j_username.getValue());
				setProOfCookie("webkeyPass", password);
				checkLogin(user);
				loginSucess(user);
				Sessions.getCurrent().removeAttribute("ulist");
			}
		}
	}

	// �����¼�
	public void onClick$resetButton() {
		j_username.setRawValue(null);
		j_password.setRawValue(null);
		cpatb.setRawValue(null);
		// cpa.randomValue();
		cpa.setValue(String.valueOf(Math.random() * 9000 + 1000).substring(0, 4));
	}

	/**
	 * <li>�����������û���������ʱ�����ת�Ƶ�������ʾ����ҳ�档 void
	 * 
	 * @author bobo
	 * @2010-4-16
	 */
	public void onClick$reRegistButton() {
		Executions.getCurrent().sendRedirect("/admin/resetPassword/UnameLogin.zul");
	}

	/**
	 * <li>�������������cookie��ĳ���Ե�ֵ��
	 * 
	 * @param pname
	 * @return String
	 * @author DaLei
	 * @2010-3-24
	 */
	public String getProOfCookie(String pname) {
		Cookie[] cookies = ((HttpServletRequest) Executions.getCurrent().getNativeRequest()).getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (pname.equals(cookies[i].getName())) {
					String fs = cookies[i].getValue();
					return fs;
				}
			}
		}
		return "";
	}

	/**
	 * <li>������������cookie��д��ֵ��
	 * 
	 * @param pname
	 * @param fs void
	 * @author DaLei
	 * @2010-3-24
	 */
	public void setProOfCookie(String pname, String fs) {
		Cookie cookie = new Cookie(pname, fs);
		cookie.setMaxAge(60 * 60 * 24 * 30);// store 30 days
		String cp = Executions.getCurrent().getContextPath();
		cookie.setPath(cp);
		((HttpServletResponse) Executions.getCurrent().getNativeResponse()).addCookie(cookie);
	}
}
