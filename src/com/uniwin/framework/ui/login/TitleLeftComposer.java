package com.uniwin.framework.ui.login;

/**
 * <li>左侧三级及下级标题树的页面解释器，对应页面为admin/left.zul
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
import com.uniwin.framework.ui.title.TitleTreeModel;

public class TitleLeftComposer extends BaseLeftTreeComposer {
	private static final long serialVersionUID = 987741978951566698L;
	// 标题树组件,与页面中定义的
	Tree tree;
	// 树的模型组件
	TitleTreeModel tmd;
	// 标题数据访问接口
	TitleService titleService;
	// 当前三级标题树的父标题编号
	Long tid;
	// 存在session中的用户权限列表
	List<WkTTitle> ulist;
	Panel titleleft;

	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		ulist = (List<WkTTitle>) session.getAttribute("ulist");
		tid = (Long) centerTabbox.getAttribute("tid");
		tmd = new TitleTreeModel(titleService.getChildTitle(tid), titleService);
		tree.setTreeitemRenderer(new TitleItemRenderer());
		tree.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event event) throws Exception {
				Treeitem item = tree.getSelectedItem();
				if (item != null) {
					WkTTitle t = (WkTTitle) item.getValue();
					if (item.getTreechildren() != null) {
						return;
					}
					creatTab("title", "个人中心", t.getKtContent() + "index.zul", titleleft, true);
				}
			}
		});
		tree.setModel(tmd);
		// 遍历树判断权限，顺便全部打开树
		checkTree(tree.getTreechildren());
	}

	@SuppressWarnings("unchecked")
	public void checkTree(Treechildren chi) {
		if (chi == null)
			return;
		List<Treeitem> tlist = chi.getChildren();
		for (int i = 0; i < tlist.size(); i++) {
			Treeitem item = (Treeitem) tlist.get(i);
			WkTTitle t = (WkTTitle) item.getValue();
			if (checkTitle(t)) {
				item.setOpen(false);
				checkTree(item.getTreechildren());
			} else {
				item.setVisible(false);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void detachTree(Treeitem item) {
		WkTTitle t = (WkTTitle) item.getValue();
		Treechildren chi = item.getTreechildren();
		if (chi == null) {
			System.out.println("无孩子消失吧:" + t.getKtName());
			item.detach();
			return;
		}
		List<Treeitem> dlist = chi.getChildren();
		for (int i = 0; i < dlist.size(); i++) {
			Treeitem it = (Treeitem) dlist.get(i);
			it.setOpen(false);
			detachTree(it);
		}
		System.out.println("消失吧:" + t.getKtName());
		item.detach();
	}

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
}
