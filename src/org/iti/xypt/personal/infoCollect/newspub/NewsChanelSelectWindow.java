package org.iti.xypt.personal.infoCollect.newspub;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.iti.xypt.personal.infoCollect.entity.WkTChanel;
import org.iti.xypt.personal.infoCollect.entity.WkTWebsite;
import org.iti.xypt.personal.infoCollect.service.ChanelService;
import org.iti.xypt.personal.infoCollect.service.NewsService;
import org.iti.xypt.personal.infoCollect.service.TaskService;
import org.iti.xypt.personal.infoCollect.service.WebsiteService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.RoleService;



public class NewsChanelSelectWindow extends Window implements AfterCompose {

	private static final long serialVersionUID = 1L;
	//��Ŀ��
	Tree tree;
	//���ݷ��ʽӿ�
	NewsService newsService;
	WebsiteService websiteService;
	ChanelService chanelService;
	TaskService taskService;
	RoleService roleService;
	List slist,userDeptList;
	WkTUser user;
	ArrayList rlist= new ArrayList();
	Window select;
	int n=0;
	public void afterCompose() {		
		Components.wireVariables(this, this);
		Components.addForwards(this, this);		
		user=(WkTUser)Sessions.getCurrent().getAttribute("user");
		userDeptList=(List)Sessions.getCurrent().getAttribute("userDeptList");
		Sessions.getCurrent().setAttribute("newsService",newsService);
		Sessions.getCurrent().setAttribute("roleService",roleService);
	}
	//��ʼ����Ŀ������
	public void initWindow(List slist){
		this.slist=slist;
		tree.setTreeitemRenderer(new NewsChanelSelectItemRenderer(slist));
		List rlist=roleService.getUserRoleId(user.getKuId());
		List wlist=websiteService.getWebsiteOfManage(user, userDeptList, rlist);
		for(int i=0;i<wlist.size();i++)
		{
			WkTWebsite web=(WkTWebsite)wlist.get(i);
			rlist.add(n,web);
			n++;
			List list=chanelService.getChanelByKwid(Long.parseLong("0"),web.getKwId());
			for(int j=0;j<list.size();j++)
			{
				WkTChanel chanel=(WkTChanel)list.get(j);
				rlist.add(n,chanel);
				n++;
			}
		}
		tree.setModel(new NewsTreeModel(wlist,taskService));
	}
	//ѡ����Ŀȷ���ύ
	public void onClick$submit() throws InterruptedException{
		Set set=tree.getSelectedItems();
		Iterator it=set.iterator();
		slist=new ArrayList();
		while(it.hasNext()){
			Treeitem titem=(Treeitem)it.next();
			Object o=titem.getValue();
			if(o instanceof WkTChanel){
				List rlist=roleService.getUserRoleId(user.getKuId());
				 final List list=newsService.getChanelOfManage(user, userDeptList, rlist);//����Ȩ�޵���Ŀ�б�
				 int count=0;
				 for(int i=0;i<list.size();i++)
				   {
				   	WkTChanel chanel=(WkTChanel)list.get(i);
				   	if(chanel.getKcId().toString().trim().equals(((WkTChanel)o).getKcId().toString().trim()))
				   		count++;	
				   }
					if(count==0)
						Messagebox.show("��û�ж�("+((WkTChanel)o).getKcName().toString()+")�Ĺ���Ȩ�ޣ�");
						else
			        slist.add(o);
			}
		}
		Events.postEvent(Events.ON_CHANGE, this, null);
		select.detach();
	}
	/**չ�����ڵ�*/
	public void onClick$open()
	{
			openTree(tree.getTreechildren());
	}
	private void openTree(Treechildren treec)
	{
		if(treec==null) return;
		List clist=treec.getChildren();
		for(int i=0;i<clist.size();i++){
			Treeitem item=(Treeitem)clist.get(i);			
			item.setOpen(true);
			openTree(item.getTreechildren());
		}	
	}
	/**�ϲ����ڵ�*/
	public void onClick$clo()
	{
		closeTree(tree.getTreechildren());
	}
	private void closeTree(Treechildren treec)
	{
		if(treec==null) return;
		List clist=treec.getChildren();
		for(int i=0;i<clist.size();i++){
			Treeitem item=(Treeitem)clist.get(i);			
			item.setOpen(false);
			openTree(item.getTreechildren());
		}	
	}

	
	public List getSlist() {
		return slist;
	}

	public void setSlist(List slist) {
		this.slist = slist;
	}
}
