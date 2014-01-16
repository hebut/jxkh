package com.uniwin.framework.ui.title;

/**
 * <li>标题管理左侧树组件解释器，对应的页面为admin\system\title\index.zul
 * @author DaLei
 * @2010-3-15
 */
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;

import com.uniwin.framework.entity.WkTTitle;
import com.uniwin.framework.service.TitleService;
import com.uniwin.framework.ui.login.BaseLeftTreeComposer;
import com.uniwin.framework.ui.login.TitleItemRenderer;

public class TitleTreeComposer extends BaseLeftTreeComposer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 页面中树组件
	Tree titleTree;
	// 标题数据访问jiek
	TitleService titleService;
	// 标题数据模型
	TitleTreeModel ttm;
	// 点击标题树节点默认打开标题编辑窗口
	TitleEditWindow tWindow;
	Panel titlePanel;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		titleTree.setTreeitemRenderer(new TitleItemRenderer());
		titleTree.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event event) throws Exception {
				openEditWindow();
			}
		});
		// 默认打开标题管理总页面
		TitltTotalWindow wTotal = (TitltTotalWindow) creatTab("titleEdit", "标题管理", "/admin/system/title/ttotal.zul", titlePanel);
		wTotal.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				loadTree();
				openTree(titleTree.getTreechildren());
			}
		});
		// 默认结束
		loadTree();
		openTree(titleTree.getTreechildren());
	}

	private void openEditWindow() {
		Treeitem it = titleTree.getSelectedItem();
		WkTTitle t = (WkTTitle) it.getValue();
		Component c = creatTab("titleEdit", "标题管理", "/admin/system/title/tedit.zul", titlePanel);
		if (c != null) {
			tWindow = (TitleEditWindow) c;
		}
		tWindow.initWindow(t);
		tWindow.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				loadTree();
				openTree(titleTree.getTreechildren());
			}
		});
	}

	private void loadTree() {
		List<WkTTitle> tlist = titleService.getChildTitle(Long.parseLong("0"));
		ttm = new TitleTreeModel(tlist, titleService);
		titleTree.setModel(ttm);
	}

	/**
	 * <li>功能描述：将树节点展开。改为展开一层
	 * 
	 * @param chi
	 *            void
	 * @author DaLei
	 */
	@SuppressWarnings("unchecked")
	private void openTree(Treechildren chi) {
		if (chi == null)
			return;
		List<Treeitem> tlist = chi.getChildren();
		for (int i = 0; i < tlist.size(); i++) {
			Treeitem item = (Treeitem) tlist.get(i);
			item.setOpen(true);
			//openTree(item.getTreechildren());
		}
	}

//	/**
//	 * <li>功能描述：将树节点展开并默认打开某个标题编辑。
//	 * 
//	 * @param chi
//	 * @param dept
//	 *            void
//	 * @author DaLei
//	 */
//	@SuppressWarnings("unchecked")
//	private void openTree(Treechildren chi, WkTTitle tit) {
//		if (chi == null)
//			return;
//		if (tit == null) {
//			closeTabs();
//			return;
//		}
//		List<Treeitem> tlist = chi.getChildren();
//		for (int i = 0; i < tlist.size(); i++) {
//			Treeitem item = (Treeitem) tlist.get(i);
//			WkTTitle t = (WkTTitle) item.getValue();
//			if (t.getKtId().intValue() == tit.getKtId().intValue()) {
//				titleTree.setSelectedItem(item);
//				tWindow.initWindow(t);
//			}
//			item.setOpen(true);
//			openTree(item.getTreechildren(), tit);
//		}
//	}
}
