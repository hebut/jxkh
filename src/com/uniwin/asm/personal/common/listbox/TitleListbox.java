package com.uniwin.asm.personal.common.listbox;

/**
 * <li>�����б����������ҳ��ʹ�ò������ã��������б���������б�
 * @author bobo
 * @2010-3-30
 */
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import com.uniwin.framework.entity.WkTTitle;
import com.uniwin.framework.service.TitleService;

public class TitleListbox extends Listbox implements AfterCompose {
	// �������ݷ���
	TitleService titleService;
	// ����ģ���б�
	ListModelList tmodelList;
	// Listbox ��һ����ƣ���ӵ�һ�ַ���
	String topTitle;
	// session�д洢��½�û���Ȩ���б�,�ڵ�½��������õ�
	List ulist;
	// ��ŵ�ǰ��¼�û�ӵ�еı���
	List tList = new ArrayList();

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		ulist = (List) Sessions.getCurrent().getAttribute("ulist");
		titleService = (TitleService) SpringUtil.getBean("titleService");
	}

	/**
	 * <li>��������������ղ�ʱ,����ֻ��ʾ���������������ʼ��һ������
	 * 
	 * @param ListModelList
	 *            rml
	 * @param Long
	 *            pid Title��Ĳ��ź�
	 * @author bobo
	 * @2010-4-16
	 */
	public void addTitleListBoxItem(ListModelList rml, Long pid) {
		List olist = titleService.getChildTitle(pid);
		// ��ʼ��һ������
		for (int i = 0; i < olist.size(); i++) {
			WkTTitle title = (WkTTitle) olist.get(i);
			// �ж��û��Ƿ��д�Ȩ����ʾ�˱���
			if (checkTitle(title)) {
				Long ktid = title.getKtId();
				title.setDep(1);
				rml.add(title);
				appendTwoTitle(rml, ktid);
			}
		}
	}

	/**
	 * <li>������������Ӷ�������
	 * 
	 * @param ListModelList
	 *            rml
	 * @param Long
	 *            pid Title��Ĳ��ź�
	 * @author bobo
	 * @2010-4-16
	 */
	public void appendTwoTitle(ListModelList rml, Long pid) {
		List twolist = titleService.getChildTitle(pid);
		for (int i = 0; i < twolist.size(); i++) {
			WkTTitle title = (WkTTitle) twolist.get(i);
			if (checkTitle(title)) {
				title.setDep(2);
				rml.add(title);
				appendThreeTitle(rml, title.getKtId());
			}
		}
	}

	/**
	 * <li>���������������������
	 * 
	 * @param ListModelList
	 *            rml
	 * @param Long
	 *            pid Title��Ĳ��ź�
	 * @author bobo
	 * @2010-4-16
	 */
	public void appendThreeTitle(ListModelList rml, Long pid) {
		List threelist = titleService.getChildTitle(pid);
		for (int i = 0; i < threelist.size(); i++) {
			WkTTitle title = (WkTTitle) threelist.get(i);
			if (checkTitle(title)) {
				title.setDep(3);
				rml.add(title);
			}
		}
	}

	/**
	 * <li>������������ʾ�ж��û��Ƿ��д˱���Ȩ��
	 * 
	 * @param title�������
	 * @return boolean
	 * @author DaLei
	 */
	public boolean checkTitle(WkTTitle title) {
		boolean flag = false;
		for (int j = 0; j < ulist.size(); j++) {
			WkTTitle ti = (WkTTitle) ulist.get(j);
			if (ti.getKtId().intValue() == title.getKtId().intValue()) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	/**
	 * <li>������������ʼ���б��������Ӧ����༭�еĸ�����ѡ�񣬲�����arg���⣬Ĭ��ѡ���丸���⡣
	 * 
	 * @param arg
	 *            ��ǰ�༭���⣬Ĭ��ѡ���丸���� void
	 * @author DaLei
	 * @2010-3-16
	 */
	public void initPTitleSelect(final WkTTitle arg) {
		tmodelList = new ListModelList();
		if (getTopTitle() != null) { // ���ڿ���LIstbox���һ�У�Ϊ�ղ���ʾ��
			WkTTitle t = new WkTTitle();
			t.setKtId(0L);
			t.setKtName(getTopTitle());
			t.setDep(0);
			tmodelList.add(t);
		}
		addTitleListBoxItem(tmodelList, Long.parseLong("0"));
		this.setModel(tmodelList);
		this.setSelectedIndex(0);
		this.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception {
				WkTTitle t = (WkTTitle) data;
				item.setValue(t);
				int dep = t.getDep();
				String bla = "";
				while (dep > 0) {
					bla += "��";
					dep--;
				}
				if (arg != null && t.getKtId().intValue() == arg.getKtPid().intValue()) {
					item.setSelected(true);
				}
				item.setLabel(bla + t.getKtName());
			}
		});
	}

	public String getTopTitle() {
		return topTitle;
	}

	public void setTopRole(String topTitle) {
		this.topTitle = topTitle;
	}
}
