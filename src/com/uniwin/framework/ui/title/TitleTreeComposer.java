package com.uniwin.framework.ui.title;

/**
 * <li>�������������������������Ӧ��ҳ��Ϊadmin\system\title\index.zul
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
	// ҳ���������
	Tree titleTree;
	// �������ݷ���jiek
	TitleService titleService;
	// ��������ģ��
	TitleTreeModel ttm;
	// ����������ڵ�Ĭ�ϴ򿪱���༭����
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
		// Ĭ�ϴ򿪱��������ҳ��
		TitltTotalWindow wTotal = (TitltTotalWindow) creatTab("titleEdit", "�������", "/admin/system/title/ttotal.zul", titlePanel);
		wTotal.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				loadTree();
				openTree(titleTree.getTreechildren());
			}
		});
		// Ĭ�Ͻ���
		loadTree();
		openTree(titleTree.getTreechildren());
	}

	private void openEditWindow() {
		Treeitem it = titleTree.getSelectedItem();
		WkTTitle t = (WkTTitle) it.getValue();
		Component c = creatTab("titleEdit", "�������", "/admin/system/title/tedit.zul", titlePanel);
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
	 * <li>���������������ڵ�չ������Ϊչ��һ��
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
//	 * <li>���������������ڵ�չ����Ĭ�ϴ�ĳ������༭��
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
