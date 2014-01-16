package com.uniwin.framework.common.listbox;

/**
 * <li>标题列表组件，根据页面使用参数配置，可以是列表或者下拉列表
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
	 * <li>功能描述：向标题列表中增加标题。
	 * 
	 * @param rml
	 *            标题列表
	 * @param pid
	 *            父标题
	 * @param dep
	 *            标题层次
	 * @param ctit
	 *            不增加ctit及其子标题 void
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
	 * <li>功能描述：初始化列表组件，对应标题编辑中的父标题选择，不包含arg标题，默认选择其父标题。
	 * 
	 * @param arg
	 *            当前编辑标题，默认选择其父标题 void
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
					bla += "　";
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
	 * <li>功能描述：初始化标题列表组件，用来显示全部标题，同时选择arg标题。
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
					bla += "　";
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
			return "顶级";
		}
		return topName;
	}

	public void setTopName(String topName) {
		this.topName = topName;
	}
}
