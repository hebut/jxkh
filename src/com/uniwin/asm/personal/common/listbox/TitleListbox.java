package com.uniwin.asm.personal.common.listbox;

/**
 * <li>标题列表组件，根据页面使用参数配置，可以是列表或者下拉列表
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
	// 标题数据访问
	TitleService titleService;
	// 标题模型列表
	ListModelList tmodelList;
	// Listbox 第一项控制，添加的一字符串
	String topTitle;
	// session中存储登陆用户的权限列表,在登陆组件中设置的
	List ulist;
	// 存放当前登录用户拥有的标题
	List tList = new ArrayList();

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		ulist = (List) Sessions.getCurrent().getAttribute("ulist");
		titleService = (TitleService) SpringUtil.getBean("titleService");
	}

	/**
	 * <li>功能描述：添加收藏时,标题只显示到第三级。线面初始化一级标题
	 * 
	 * @param ListModelList
	 *            rml
	 * @param Long
	 *            pid Title表的部门号
	 * @author bobo
	 * @2010-4-16
	 */
	public void addTitleListBoxItem(ListModelList rml, Long pid) {
		List olist = titleService.getChildTitle(pid);
		// 初始化一级标题
		for (int i = 0; i < olist.size(); i++) {
			WkTTitle title = (WkTTitle) olist.get(i);
			// 判断用户是否有此权限显示此标题
			if (checkTitle(title)) {
				Long ktid = title.getKtId();
				title.setDep(1);
				rml.add(title);
				appendTwoTitle(rml, ktid);
			}
		}
	}

	/**
	 * <li>功能描述：添加二级标题
	 * 
	 * @param ListModelList
	 *            rml
	 * @param Long
	 *            pid Title表的部门号
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
	 * <li>功能描述：添加三级标题
	 * 
	 * @param ListModelList
	 *            rml
	 * @param Long
	 *            pid Title表的部门号
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
	 * <li>功能描述：显示判断用户是否有此标题权限
	 * 
	 * @param title标题对象
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
	 * <li>功能描述：初始化列表组件，对应标题编辑中的父标题选择，不包含arg标题，默认选择其父标题。
	 * 
	 * @param arg
	 *            当前编辑标题，默认选择其父标题 void
	 * @author DaLei
	 * @2010-3-16
	 */
	public void initPTitleSelect(final WkTTitle arg) {
		tmodelList = new ListModelList();
		if (getTopTitle() != null) { // 用于控制LIstbox表第一列，为空不显示。
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

	public String getTopTitle() {
		return topTitle;
	}

	public void setTopRole(String topTitle) {
		this.topTitle = topTitle;
	}
}
