package org.iti.jxkh.indicator.businessdept;

import java.util.List;

import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.service.BusinessIndicatorService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;


public class IndicatorListbox extends Listbox implements AfterCompose {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String topName;
	ListModelList tmodelList;
	
	Jxkh_BusinessIndicator business;
	 BusinessIndicatorService businessIndicatorService;
	
	@Override
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
	public void addTitleListBoxItem(ListModelList rml, Long pid, int dep, Jxkh_BusinessIndicator ctit) {
		List<Jxkh_BusinessIndicator> tList;
		if (ctit == null) {
			tList = businessIndicatorService.getChildBusiness(pid);
		} else {
			tList = businessIndicatorService.getChild(pid, ctit.getKbId());
		}
		for (int i = 0; i < tList.size(); i++) {
			Jxkh_BusinessIndicator t = (Jxkh_BusinessIndicator) tList.get(i);
			rml.add(t);
			addTitleListBoxItem(rml, t.getKbId(), dep + 1, ctit);
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
	public void initPIndicatorSelect(final Jxkh_BusinessIndicator arg) {
		tmodelList = new ListModelList();
		Jxkh_BusinessIndicator t = new Jxkh_BusinessIndicator();
		t.setKbId(0L);
		t.setKbName(getTopName());
		tmodelList.add(t);
		addTitleListBoxItem(tmodelList, Long.parseLong("0"), 0, arg);
		this.setModel(tmodelList);
		this.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception {
				Jxkh_BusinessIndicator t = (Jxkh_BusinessIndicator)data;
				item.setValue(t);
				if (arg != null && t.getKbId().intValue() == arg.getKbPid().intValue()) {
					item.setSelected(true);
				}
				item.setLabel(t.getKbName());
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
