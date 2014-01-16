package com.uniwin.framework.uploaddoc;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
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
import com.uniwin.framework.ui.title.TitleTreeModel;

public class DocumentManageLeftComposer extends BaseLeftTreeComposer {

	private static final long serialVersionUID = 1L;
	// ���������,��ҳ���ж����
	Tree tree;
	// ����ģ�����
	TitleTreeModel tmd;
	// �������ݷ��ʽӿ�
	TitleService titleService;
	// ��ǰ�����������ĸ�������
	Long tid;
	// ����session�е��û�Ȩ���б�
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
					Executions.getCurrent().setAttribute("wktTitle", t);
					creatTab("doucmentmanage", "�ĵ�����", t.getKtContent() + "index.zul", titleleft, true);
				}
			}
		});
		tree.setModel(tmd);
		// �������ж�Ȩ�ޣ�˳��ȫ������
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
			System.out.println("�޺�����ʧ��:" + t.getKtName());
			item.detach();
			return;
		}
		List<Treeitem> dlist = chi.getChildren();
		for (int i = 0; i < dlist.size(); i++) {
			Treeitem it = (Treeitem) dlist.get(i);
			it.setOpen(false);
			detachTree(it);
		}
		System.out.println("��ʧ��:" + t.getKtName());
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
