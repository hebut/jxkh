package com.uniwin.framework.ui.parameter;

/**
 * <li>����������ʵ����վ��Ϣ�޸ĺ�ı��������
 * ��Ӧ��ҳ��Ϊadmin/system/parameters/websiteInfo/index.zul
 * @author fang
 * @2010.3
 */
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.uniwin.basehs.service.BaseService;
import com.uniwin.framework.entity.WkTSite;

public class WebsiteInfoEditWindow extends Window implements AfterCompose {
	private static final long serialVersionUID = -2518102178114497440L;
	private Textbox enterpriseName; // ��λ����
	private Textbox legalPerson; // ���˴���
	private Textbox telephone; // �绰
	private Textbox fax; // ����
	private Textbox email; // E-MAIL��ַ
	private Textbox address; // ��ַ
	private Textbox postCoder; // �ʱ�
	private Listbox style; // Ĭ�Ͻ�����
	Button sava, reset; // ���������
	WkTSite website; // ��վ��Ϣ���ݷ��ʽӿ�
	BaseService baseService; // ��վ��Ϣ���ݷ��ʽӿ�

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		this.initializeWindow();
	}

	/**
	 * <li>������������ʼ����վ��Ϣ���棬�����ݿ��ж��������Ϣ
	 */
	public void initializeWindow() {
		website = (WkTSite) baseService.get(WkTSite.class, 1L); // �����ݿ��ж�����һ����¼
		enterpriseName.setValue(website.getKsEpname());
		legalPerson.setValue(website.getKsGenmgr());
		telephone.setValue(website.getKsPhone());
		fax.setValue(website.getKsFax());
		email.setValue(website.getKsEmail());
		address.setValue(website.getKsAddress());
		postCoder.setValue(website.getKsPostid());
		if (website.getKsStyle().trim().equalsIgnoreCase("Ĭ��")) {
			style.setSelectedIndex(0);
		} // ������ݿ��н������ŵ��ǡ�Ĭ�ϡ�����򿪽���ʱ��ʾ��Ĭ�ϡ�
		else if (website.getKsStyle().trim().equalsIgnoreCase("ˮ����")) {
			style.setSelectedIndex(1);
		} // ������ݿ��н������ŵ��ǡ�ˮ����������򿪽���ʱ��ʾ��ˮ������
		else {
			style.setSelectedIndex(2);
		} // ���һ�������������Ϊ��red��
	}

	/**
	 * <li>����������ʵ����վ��Ϣ�޸ĺ�ı��湦�� ��ҳ���ڻ�ȡ�����ݴ�ŵ����ݿ��Ӧ��λ��
	 * 
	 * @throws InterruptedException
	 */
	public void onClick$save() throws InterruptedException {
		website.setKsEpname(enterpriseName.getValue());
		website.setKsGenmgr(legalPerson.getValue());
		website.setKsPhone(telephone.getValue());
		website.setKsFax(fax.getValue());
		website.setKsEmail(email.getValue());
		if (postCoder.getValue().length() > 6) {
			Messagebox.show("�����������!", "������ʾ", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		website.setKsPostid(postCoder.getValue());
		website.setKsAddress(address.getValue());
		website.setKsStyle(style.getSelectedItem().getLabel());
		baseService.update(website);
		Messagebox.show("����ɹ���", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);// ��������ɹ�����ʾ������ɹ�������
	}

	/**
	 *<li>����������ʵ���޸���վ��Ϣ�����ù��� ������ú�ҳ�潫��ʾԭ����������ݿ��е�����
	 */
	public void onClick$reset() {
		enterpriseName.setValue(website.getKsEpname());
		legalPerson.setValue(website.getKsGenmgr());
		telephone.setValue(website.getKsPhone());
		fax.setValue(website.getKsFax());
		email.setValue(website.getKsEmail());
		address.setValue(website.getKsAddress());
		postCoder.setValue(website.getKsPostid());
	}
}
