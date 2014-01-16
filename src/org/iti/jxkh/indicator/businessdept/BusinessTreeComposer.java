package org.iti.jxkh.indicator.businessdept;


 
import java.util.ArrayList;
import java.util.List;
import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.service.BusinessIndicatorService;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTMlog;
import com.uniwin.framework.entity.WkTTitle;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.MLogService;
import com.uniwin.framework.ui.login.BaseLeftTreeComposer;


public class BusinessTreeComposer extends BaseLeftTreeComposer {
	
	private static final long serialVersionUID = 1L;
	
	Tree tree;	
	BusinessTreeModel ntm;
	Panel businesspanel;	
	Menuitem add,edit,use,drop,sort;
	List nlist=new ArrayList();
	
	WkTUser user;
	
	BusinessIndicatorService businessIndicatorService;
	MLogService mlogService;
	Treeitem item;
	public void doAfterCompose(Component comp) throws Exception {		
		super.doAfterCompose(comp);
		user=(WkTUser)Sessions.getCurrent().getAttribute("user");
		inittree();
		tree.setTreeitemRenderer(new TreeitemRenderer() 
		{
			public void render(final Treeitem item, Object data) throws Exception {
				Jxkh_BusinessIndicator b=(Jxkh_BusinessIndicator)data;
				item.setValue(b);
				item.setLabel(b.getKbName());
				item.setOpen(true);
				
			}
		});
		//������������Ӧ�¼�
		tree.addEventListener(Events.ON_SELECT, new EventListener(){
			public void onEvent(Event event) throws Exception 
			{
				 Treeitem it=tree.getSelectedItem();
				 //�ж�Listitem��value�Ƿ���Jxkh_BusinessIndicator��һ��ʵ��
				 if(it.getValue() instanceof Jxkh_BusinessIndicator)
				 {
				 	 openEditWindow();		
				 }
			}			
		});
		
		/**
		 * ��ݲ˵��еġ���ӡ������¼�
		 */
		add.addEventListener(Events.ON_CLICK, new EventListener(){
			public void onEvent(Event arg0) throws Exception {
				item=tree.getSelectedItem();
				Jxkh_BusinessIndicator business = (Jxkh_BusinessIndicator)item.getValue();
				if(business.getKbLock() == Jxkh_BusinessIndicator.LOCK_YES) {
					Messagebox.show("ָ�������������ܽ��в��������Ƚ�����");
					return;
				}
				 //window w=new Window("business", "ҵ����ָ��", "/admin/jxkh/indicator/businessdept/editbusiness.zul",businesspanel);
				final BusinessNewWindow w=(BusinessNewWindow) Executions.createComponents("/admin/jxkh/indicator/businessdept/addbusiness.zul", null, null);
				if(business.getKbId() != 0L) {
					w.initWindow(business);
				}
				w.addEventListener(Events.ON_CHANGE, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						inittree();
						openTree(tree.getTreechildren());
						w.detach();
					}
				});
			    w.setClosable(true);
			    w.doModal();
			}
			
		});
		/**
		 * ��ݲ˵��еġ��༭�������¼�
		 */
		edit.addEventListener(Events.ON_CLICK, new EventListener(){	
			public void onEvent(Event arg0) throws Exception {
				Treeitem it=tree.getSelectedItem();
				 Jxkh_BusinessIndicator business=(Jxkh_BusinessIndicator)it.getValue();	
				 if(business.getKbLock() == Jxkh_BusinessIndicator.LOCK_YES) {
						Messagebox.show("ָ�������������ܽ��в��������Ƚ�����");
						return;
					}
				 if(business.getKbId().toString().equals("0"))
				 {
					 return;
				 }
				 else
				 {
				 Component w=creatTab("business", "ҵ����ָ��", "/admin/jxkh/indicator/businessdept/editbusiness.zul",businesspanel);
				 if(w!=null){					
					 BusinessEditWindow t=(BusinessEditWindow)w;
					 t.initWindow(business);
					 t.addEventListener(Events.ON_CHANGE, new EventListener() {
						public void onEvent(Event arg0) throws Exception {
							inittree();
							openTree(tree.getTreechildren());
						}
					});
				 }
				 }
			}
			
		});
		/**
		 * ��ݲ˵��еġ�ɾ���������¼�
		 */
//		delete.addEventListener(Events.ON_CLICK, new EventListener() {
//			public void onEvent(Event arg0) throws Exception {
//				Treeitem it=tree.getSelectedItem();
//				Jxkh_BusinessIndicator business=(Jxkh_BusinessIndicator)it.getValue();	
//				if(business.getKbLock() == Jxkh_BusinessIndicator.LOCK_YES) {
//					Messagebox.show("ָ�������������ܽ��в��������Ƚ�����");
//					return;
//				}
//				List blist = businessIndicatorService.getChildBusiness(business.getKbId());
//				if(blist.size() != 0) {
//					if (Messagebox.show("������ָ�꣬��һ��ɾ����ȷ��Ҫɾ����", "ע��", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == 1) {
//						deleteTitle(business, blist);
//					} else {
//						return;
//					}
//				}else {
//					if (Messagebox.show("��ȷ��Ҫɾ����?", "��ȷ��", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == 1) {
//						deleteTitle(business, null);
//					}
//				}
//				inittree();
//			}
//		});
		/**
		 * ��ݲ˵��еġ����á������¼�
		 */
		use.addEventListener(Events.ON_CLICK, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				Treeitem it=tree.getSelectedItem();
				Jxkh_BusinessIndicator business=(Jxkh_BusinessIndicator)it.getValue();	
				if(business.getKbLock() == Jxkh_BusinessIndicator.LOCK_YES) {
					Messagebox.show("ָ�������������ܽ��в��������Ƚ�����");
					return;
				}
				if(business.getKbStatus() != Jxkh_BusinessIndicator.USE_YES) {
					business.setKbStatus(Jxkh_BusinessIndicator.USE_YES);
					businessIndicatorService.update(business);
					if (Messagebox.show("���óɹ�,�Ƿ���������?", "����ָ��", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == 1) {
						 inittree();
					} 
				}
			}
		});
		/**
		 * ��ݲ˵��еġ�ͣ�á������¼�
		 */
		drop.addEventListener(Events.ON_CLICK, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				Treeitem it=tree.getSelectedItem();
				Jxkh_BusinessIndicator business=(Jxkh_BusinessIndicator)it.getValue();	
				if(business.getKbLock() == Jxkh_BusinessIndicator.LOCK_YES) {
					Messagebox.show("ָ�������������ܽ��в��������Ƚ�����");
					return;
				}
				if(business.getKbStatus() != Jxkh_BusinessIndicator.USE_NO) {
					business.setKbStatus(Jxkh_BusinessIndicator.USE_NO);
					businessIndicatorService.update(business);
					if (Messagebox.show("ͣ�óɹ�,�Ƿ���������?", "����ָ��", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == 1) {
						inittree();
					}
				}
			}
		});
	}
	/**
	 * ����ɾ�����⣬����ʵ��
	 * @param business
	 * @param blist
	 */
	public void deleteTitle(Jxkh_BusinessIndicator business,List blist) {
		if (blist != null)
			for (int i = 0; i < blist.size(); i++) {
				Jxkh_BusinessIndicator b=(Jxkh_BusinessIndicator)blist.get(i);
				List bslist = businessIndicatorService.getChildBusiness(b.getKbId());
				deleteTitle(b, bslist);
			}
		mlogService.saveMLog(WkTMlog.FUNC_ADMIN, "ɾ�����⣬id:" + business.getKbId(), user);
		businessIndicatorService.delete(business);
	}
	
	public void inittree()
	{
//		nlist=businessIndicatorService.getUseChild(Long.parseLong("0"));
		nlist = businessIndicatorService.findById(0L);
//		if(blist.size() != 0) {
//			nlist.addAll(blist);
//		}			
		//nlist=businessIndicatorService.findAll();
		ntm=new BusinessTreeModel(nlist,businessIndicatorService);
		tree.setModel(ntm);
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
	
	//�򿪴���
	public void openEditWindow() {
		 Treeitem it=tree.getSelectedItem();
		 Jxkh_BusinessIndicator business=(Jxkh_BusinessIndicator)it.getValue();	
		 if(business.getKbId().toString().equals("0"))
		 {
			 return;
		 }
		 else
		 {
		 Component w=creatTab("business", "ҵ����ָ��", "/admin/jxkh/indicator/businessdept/editbusiness.zul",businesspanel);
		 if(w!=null){					
			 BusinessEditWindow t=(BusinessEditWindow)w;
			 t.initWindow(business);
			 t.addEventListener(Events.ON_CHANGE, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					inittree();
					openTree(tree.getTreechildren());
				}
			});
		 }
		 }
	}
	
}
