package org.iti.feedback.ui.manager;

/**
 * <li>标题管理左侧树组件解释器，对应的页面为admin\system\title\index.zul
 * @author DaLei
 * @2010-3-15
 */
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericAutowireComposer;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;

import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTTitle;
import com.uniwin.framework.service.TitleService;
import com.uniwin.framework.ui.dept.DepartmentEditWindow;
import com.uniwin.framework.ui.login.BaseLeftTreeComposer;
import com.uniwin.framework.ui.login.TitleItemRenderer;
import com.uniwin.framework.ui.title.TitleTreeModel;


public class TitleTreeComposer extends BaseLeftTreeComposer {
	
	//页面中树组件
	Tree titleTree;	
	
	//标题数据访问jiek
	TitleService titleService;
	
	//标题数据模型
	TitleTreeModel ttm;
	
	//点击标题树节点默认打开标题编辑窗口
	 
	
	Panel titlePanel;
	
	public void doAfterCompose(Component comp) throws Exception {		
		super.doAfterCompose(comp);	
		
		titleTree.setTreeitemRenderer(new TreeitemRenderer(){
			public void render(Treeitem item, Object data) throws Exception {
				WkTTitle title=(WkTTitle)data;
				item.setValue(title);
				Treecell tc1=new Treecell(title.getKtName());
				Treecell tc2=new Treecell("2");
				Treerow row=new Treerow();
				row.appendChild(tc1);	row.appendChild(tc2);	
				item.appendChild(row);
			}
		});
		titleTree.addEventListener(Events.ON_SELECT, new EventListener(){
			public void onEvent(Event event) throws Exception {
			   openEditWindow();			  
			}			
		});
		//默认打开标题管理总页面
		//TitltTotalWindow wTotal=(TitltTotalWindow)creatTab("titleEdit", "意见管理", "/admin/system/title/ttotal.zul",titlePanel);
		//默认结束
		
		loadTree();
		//openTree(titleTree.getTreechildren());
	}
	
	private void openEditWindow(){
		 Treeitem it=titleTree.getSelectedItem();
		 WkTTitle t=(WkTTitle)it.getValue();				 
		 Component c=creatTab("titleEdit", "意见管理", "/admin/feedback/manager/feedAll.zul",titlePanel);
		 
	}
	
	private void loadTree(){
		List tlist=titleService.getChildTitle(Long.parseLong("0"));
		ttm=new TitleTreeModel(tlist,titleService);
		titleTree.setModel(ttm);
	}
}
