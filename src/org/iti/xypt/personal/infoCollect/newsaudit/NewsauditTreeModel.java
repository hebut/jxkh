package org.iti.xypt.personal.infoCollect.newsaudit;
/**
 * <li>��ʼ��������Ŀ��
 * @2010-3-17
 * @author whm
 */
import java.util.List;

import org.iti.xypt.personal.infoCollect.entity.WkTExtractask;
import org.iti.xypt.personal.infoCollect.entity.WkTTasktype;
import org.iti.xypt.personal.infoCollect.service.TaskService;
import org.zkoss.zul.AbstractTreeModel;



public class NewsauditTreeModel  extends AbstractTreeModel{	
	
	TaskService taskService;
	public NewsauditTreeModel(Object root,TaskService taskService) {
		super(root);		
		this.taskService=taskService;	 	
	}
	private static final long serialVersionUID = 5665716328057383820L;
	//���˸��ڵ�󣬻�ú��ӽڵ㣬indexΪ�ڼ�������
	public Object getChild(Object parent, int index) {
		if(parent instanceof WkTTasktype)
		{
			WkTTasktype n=(WkTTasktype)parent;
			if(taskService.getChildType(n.getKtaId()).size()==0)
			{
			return taskService.getChildTask(n.getKtaId()).get(index);
			}
			else if (taskService.getChildType(n.getKtaId()).size()!=0)
			{
				return taskService.getChildType(n.getKtaId()).get(index);
			}
		}
		else if(parent instanceof WkTExtractask)
		{
			return null;
		}
		else if(parent instanceof List){
			List clist=(List)parent;
			return clist.get(index);
		}
		return null;
	}
	 //���ظ��ڵ�ĺ��ӽڵ���Ŀ
	public int getChildCount(Object parent) {
		if(parent instanceof WkTTasktype)
		{
			WkTTasktype n=(WkTTasktype)parent;
			if(taskService.getChildType(n.getKtaId()).size()==0)
			{
			return taskService.getChildTask(n.getKtaId()).size();
			}
			else if(taskService.getChildType(n.getKtaId()).size()!=0)
			{
				return taskService.getChildType(n.getKtaId()).size();
			}
		}
		else if(parent instanceof WkTExtractask)
		{
			return 0;
		}
		else if(parent instanceof List){
			List clist=(List)parent;
			return clist.size();
		}
		return 0;	
	}
	//�ж�ĳ�ڵ��Ƿ�ΪҶ�ӽڵ�
	public boolean isLeaf(Object node) {	
		
		 if(node instanceof WkTTasktype){
			 WkTTasktype n=(WkTTasktype)node;
			 if(taskService.getChildType(n.getKtaId()).size()==0)
			 {
				 return taskService.getChildTask(n.getKtaId()).size()>0?false:true;
			 }
				
				else if(taskService.getChildType(n.getKtaId()).size()!=0)
				{
					return taskService.getChildType(n.getKtaId()).size()>0?false:true;
				}
		 }
		 else if(node instanceof WkTExtractask)
			{
				return true;
			}
		 else if(node instanceof List){
			List clist=(List)node;
			return clist.size()>0?false:true;
		}
		return true;
	}
}
