package org.iti.xypt.personal.infoCollect.newspub;
/**
 * <li>��װ�Ļ���������������Ҫ��װ�˶���Ϣ����ҳ���б�ǩҳ�Ĳ���,���̳�֮��
 * @2010-3-16
 * @author whm
 */
 
import java.util.List;

import org.iti.xypt.personal.infoCollect.BaseLeftTreeComposer;
import org.iti.xypt.personal.infoCollect.entity.WkTExtractask;
import org.iti.xypt.personal.infoCollect.entity.WkTTasktype;
import org.iti.xypt.personal.infoCollect.newsaudit.WebsiteListbox;
import org.iti.xypt.personal.infoCollect.service.ChanelService;
import org.iti.xypt.personal.infoCollect.service.NewsService;
import org.iti.xypt.personal.infoCollect.service.TaskService;
import org.iti.xypt.personal.infoCollect.service.WebsiteService;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;

import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.RoleService;



public class NewsTreeComposer extends BaseLeftTreeComposer {
	
	private static final long serialVersionUID = 1L;
	//��Ŀ�����,��ҳ���ж����
	Tree tree;	
	//��Ŀ��Ϣ���ݷ��ʽӿ�
	NewsService newsService;
	TaskService taskService;
	ChanelService chanelService;
	RoleService roleService;
	//����ģ�����
	NewsTreeModel ntm;
	//��Ϣ������ʼ������
	 NewspubEditWindow npWindow;
	 WebsiteService websiteService;
	 Panel newspubpanel;
	 WkTUser user;
	 List userDeptList,nlist;
	 WebsiteListbox list;
	public void doAfterCompose(Component comp) throws Exception {		
		super.doAfterCompose(comp);
		user=(WkTUser)Sessions.getCurrent().getAttribute("user");
		userDeptList=(List)Sessions.getCurrent().getAttribute("userDeptList");	
		inittree();
		//������������Ӧ�¼�
		tree.addEventListener(Events.ON_SELECT, new EventListener(){
			public void onEvent(Event event) throws Exception 
			{
				 Treeitem it=tree.getSelectedItem();
				 if(it.getValue() instanceof WkTExtractask)
				 {
				 WkTExtractask etask=(WkTExtractask) it.getValue();
				 if(it.getValue() instanceof WkTExtractask)
				 {		 
					 Component c=creatTab("news", "��Ϣ����", "/admin/personal/infoExtract/newspub/pub.zul",newspubpanel);
					 if(c!=null){					
						 npWindow=(NewspubEditWindow)c;	
						 npWindow.initWindow(etask);
					 }
				 }
				 }
			}			
		});
		
	}
	public void inittree()
	{
		nlist=taskService.getChildType(Long.parseLong("0"));
		ntm=new NewsTreeModel(nlist,taskService);
		tree.setModel(ntm);
		tree.setTreeitemRenderer(new NewsItemRenderer()
		{public void render(Treeitem item, Object data) throws Exception {
			if(data instanceof WkTExtractask)
			{
			WkTExtractask et=(WkTExtractask)data;
			//�ж��û�Ȩ��
			List rlist=roleService.getUserRoleId(user.getKuId());
			List alist=taskService.getTaskOfManage(user,userDeptList,rlist);
			int count=0;
			for(int i=0;i<alist.size();i++)
			{
			WkTExtractask etask=(WkTExtractask) alist.get(i);
			if(etask.getKeId().toString().trim().equals(et.getKeId().toString().trim()))
			{
				count++;
			}
			}
			if(count==0)
			{
				item.setValue(et);
				item.setDisabled(true);
				item.setLabel(et.getKeName());
			}
			else if(count!=0)
			{
				item.setValue(et);
				item.setLabel(et.getKeName());
			}
			}
			else if(data instanceof WkTTasktype)
			{
				WkTTasktype tt=(WkTTasktype)data;
				item.setValue(tt);
				item.setLabel(tt.getKtaName());
				item.setOpen(true);
			}
			}
		});
	}

}
