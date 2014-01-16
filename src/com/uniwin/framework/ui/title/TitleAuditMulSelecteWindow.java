package com.uniwin.framework.ui.title;

/**
 * <li>多选标题树组件，标题权限管理时候，可以重新选择标题并多选标题。对应admin/system/title/tMulSelected.zul
 * @author DaLei
 * @2010-3-16
 */
import java.util.List;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Window;
import com.uniwin.framework.entity.WkTTitle;
import com.uniwin.framework.service.TitleService;

public class TitleAuditMulSelecteWindow extends Window implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 页面树组件
	Tree tree;
	// 标题数据访问接口
	TitleService titleService;
	// 标题树数据模型
	TitleTreeModel ttm;
	Button choosed;
	// 默认选择的标题列表
	List<WkTTitle> hasTlist;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		choosed.addForward(Events.ON_CLICK, this, Events.ON_CHANGE);
		this.setTitle("选择标题");
	}

	public void onClick$close() {
		this.detach();
	}

	public Tree getTree() {
		return tree;
	}

	public void setTree(Tree tree) {
		this.tree = tree;
	}

	public void initWindow(List<WkTTitle> tlist) {
		this.hasTlist = tlist;
		List<WkTTitle> rlist = titleService.getChildTitle(Long.parseLong("0"));
		ttm = new TitleTreeModel(rlist, titleService);
		tree.setModel(ttm);
		tree.setTreeitemRenderer(new TreeitemRenderer() {
			public void render(Treeitem item, Object data) throws Exception {
				WkTTitle title = (WkTTitle) data;
				item.setValue(title);
				item.setLabel(title.getKtName());
				item.setOpen(true);
				for (int i = 0; i < hasTlist.size(); i++) {
					WkTTitle t = (WkTTitle) hasTlist.get(i);
					if (t.getKtId().intValue() == title.getKtId().intValue()) {
						item.setSelected(true);
					}
				}
			}
		});
	}
}
