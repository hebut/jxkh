package com.uniwin.asm.personal.ui.data;

/**
 * ���ڶ�colNew.zulҳ�������ղ�
 * @author bobo
 * @date �������� Mar 23, 2010 
 * 
 */
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import com.uniwin.asm.personal.common.listbox.TitleListbox;
import com.uniwin.asm.personal.entity.WkTUsrfav;
import com.uniwin.asm.personal.service.UsrfavService;
import com.uniwin.framework.entity.WkTTitle;
import com.uniwin.framework.entity.WkTUser;

public class CollectionNewWin extends Window implements AfterCompose {
	private static final long serialVersionUID = 5239542173592270242L;
	// �����ղ����ݷ��ʽӿ�
	private UsrfavService usrfavService;
	// �ı������
	private Textbox kufName, kufUrl;
	// ѡ�����
	private Radiogroup cType;
	// ��ǰ�༭�ı������
	WkTTitle editTitle;
	// ������ѡ�����
	TitleListbox pselect;
	// ������
	private Button save;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		usrfavService = (UsrfavService) SpringUtil.getBean("usrfavService");
		pselect.initPTitleSelect(editTitle);
		this.addForward(Events.ON_OK, save, Events.ON_CLICK);
	}

	/**
	 * <Li>���ܣ���������ղ�
	 * 
	 * @author bobo
	 * @throws InterruptedException
	 * @date �������� Mar 23, 2010
	 * 
	 */
	public void onClick$save() throws InterruptedException {
		WkTUser wkTUser = (WkTUser) Sessions.getCurrent().getAttribute("user");
		WkTUsrfav fav = new WkTUsrfav();
		fav.setKuId(wkTUser.getKuId());
		fav.setKufName(kufName.getText());
		if (cType.getSelectedIndex() == 0) {
			if (kufUrl.getValue().equals("") && kufUrl.getValue() == null) {
				Messagebox.show("�����ַ������Ϊ�գ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
				kufUrl.focus();
			} else {
				if (IsUrl(kufUrl.getValue())) {
					fav.setKufUrl(kufUrl.getValue());
					fav.setKtId("0"); // �Զ���Ϊ0
					usrfavService.save(fav);
					Messagebox.show("����ɹ���", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
					this.detach();
				} else {
					Messagebox.show("������ĵ�ַ��Ч,���������룡", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
				}
			}
		} else {
			if (kufUrl.getValue().equals("") && kufUrl.getValue() == null && pselect.getSelectedItem().getValue() == null && pselect.getSelectedIndex() == 0 && pselect.getSelectedItem().getValue() == "") {
				Messagebox.show("ѡ�����������Ϊ�գ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
				kufUrl.focus();
			} else {
				WkTTitle d = (WkTTitle) pselect.getSelectedItem().getValue();
				fav.setKufUrl(d.getKtContent());
				fav.setKtId(d.getKtId().toString()); // ����ID
				Sessions.getCurrent().setAttribute("titleAdd", d);
				usrfavService.save(fav);
				Messagebox.show("����ɹ���", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
				this.detach();
			}
		}
		Events.postEvent(Events.ON_CHANGE, this, null);
		// Executions.getCurrent().sendRedirect("/admin/index.zul");
	}

	/**
	 * <Li>���ܣ�����ղ�ʱ�� �����á����� void
	 * 
	 * @author bobo
	 * @date �������� Mar 23, 2010
	 * 
	 */
	public void onClick$reSend() {
		kufName.setRawValue("");
		kufUrl.setValue("");
		pselect.initPTitleSelect(editTitle); // ��ʼ��Listbox
	}

	/**
	 * <Li>���ܣ���֤URL��ַ�Ƿ�ɷ���
	 * 
	 * @param String
	 *            strURL
	 * @author bobo
	 * @date �������� 2010-4-18
	 * 
	 */
	public static boolean IsUrl(String strURL) {
		boolean flag = false;
		try {
			URL url = new URL(strURL);
			URLConnection connection = url.openConnection();
			HttpURLConnection httpConn = (HttpURLConnection) connection;
			int code = httpConn.getResponseCode();
			if (code == 200) {
				flag = true;
			} else {
				flag = false;
			}
		} catch (Exception e) {
			
		}
		return flag;
	}

	public WkTTitle getEditTitle() {
		return editTitle;
	}
}
