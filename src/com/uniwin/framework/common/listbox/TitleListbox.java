package com.uniwin.framework.common.listbox;

/**
 * <li>�����б����������ҳ��ʹ�ò������ã��������б���������б�
 * @author DaLei
 * @2010-3-16
 */
import java.util.List;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import com.uniwin.framework.entity.WkTTitle;
import com.uniwin.framework.service.TitleService;

public class TitleListbox extends Listbox implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	TitleService titleService;
	ListModelList tmodelList;
	String topName;

	public void afterCompose() {
		Components.wireVariables(this, this);
	}

	/**
	 * <li>����������������б������ӱ��⡣
	 * 
	 * @param rml
	 *            �����б�
	 * @param pid
	 *            ������
	 * @param dep
	 *            ������
	 * @param ctit
	 *            ������ctit�����ӱ��� void
	 * @author DaLei
	 * @2010-3-16
	 */
	public void addTitleListBoxItem(ListModelList rml, Long pid, int dep, WkTTitle ctit) {
		List<WkTTitle> tList;
		if (ctit == null) {
			tList = titleService.getChildTitle(pid);
		} else {
			tList = titleService.getChildTitle(pid, ctit.getKtId());
		}
		for (int i = 0; i < tList.size(); i++) {
			WkTTitle t = (WkTTitle) tList.get(i);
			t.setDep(dep);
			rml.add(t);
			addTitleListBoxItem(rml, t.getKtId(), dep + 1, ctit);
		}
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
		WkTTitle t = new WkTTitle();
		t.setKtId(0L);
		t.setKtName(getTopName());
		t.setDep(0);
		tmodelList.add(t);
		addTitleListBoxItem(tmodelList, Long.parseLong("0"), 0, arg);
		this.setModel(tmodelList);
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

	/**
	 * <li>������������ʼ�������б������������ʾȫ�����⣬ͬʱѡ��arg���⡣
	 * 
	 * @param arg
	 *            void
	 * @author DaLei
	 * @2010-3-16
	 */
	public void initNewTitleSelect(final WkTTitle arg) {
		tmodelList = new ListModelList();
		WkTTitle t = new WkTTitle();
		t.setKtId(0L);
		t.setKtName(getTopName());
		t.setDep(0);
		tmodelList.add(t);
		addTitleListBoxItem(tmodelList, Long.parseLong("0"), 0, null);
		this.setModel(tmodelList);
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
				if (arg != null && t.getKtId().intValue() == arg.getKtId().intValue()) {
					item.setSelected(true);
				}
				item.setLabel(bla + t.getKtName());
			}
		});
	}

	public String getTopName() {
		if (topName == null) {
			return "����";
		}
		return topName;
	}

	public void setTopName(String topName) {
		this.topName = topName;
	}
}
