package com.uniwin.asm.personal.ui.data;

/**
 * <li>�û���¼���,��Ӧҳ��admin/personal/data/register/index.zul
 * @author bobo
 * @date 2010-3-15
 */
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import com.uniwin.common.util.EncodeUtil;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

public class ResetPassword1 extends GenericForwardComposer {

	private static final long serialVersionUID = -164191727309417123L;
	// �û����ݷ��ʽӿ�
	private UserService userService;
	// �û���������
	private Textbox old_password, new_password, sure_password;
	private Label user_to;
	// �û�ʵ��
	private WkTUser wkTUser;
	// ��������û�����������ܺ��ַ���
	private String password;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		userService = (UserService) SpringUtil.getBean("userService");
		wkTUser = (WkTUser) Sessions.getCurrent().getAttribute("user");
		user_to.setValue(wkTUser.getKuLid());
		password = wkTUser.getKuPasswd();
	}

	/**
	 * <li>�����������û��޸����빦�ܡ� void
	 * 
	 * @author bobo
	 * @2010-3-13
	 */
	public void onClick$submit() {
		if (!EncodeUtil.encodeByDES(old_password.getValue().trim()).equals(password.trim())) {
			try {
				Messagebox.show("�����������;����벻һ�£�", "��ʾ", Messagebox.OK, Messagebox.ERROR);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else if (!new_password.getValue().trim().equals(sure_password.getValue().trim())) {
			alert("������������벻һ�£�");
		} else {
			wkTUser.setKuPasswd(EncodeUtil.encodeByDES(new_password.getValue()));
			userService.update(wkTUser);
			try {
				Messagebox.show("�����޸ĳɹ���", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * <li>����������ҳ��������������á� void
	 * 
	 * @author bobo
	 * @2010-3-13
	 */
	public void onClick$reset() {
		old_password.setValue("");
		new_password.setValue("");
		sure_password.setValue("");
	}
}
