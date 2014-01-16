package org.iti.jxkh.selecttitle;

import java.util.ArrayList;
import java.util.List;

import org.iti.jxkh.entity.Jxkh_STitle;
import org.iti.jxkh.service.AuditConfigService;
import org.iti.jxkh.service.UserDetailService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;

import com.uniwin.framework.entity.WkTTitle;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.TitleService;

public class SelectTitleWindow extends BaseWindow{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3489988561086463936L;

	private Tree titleTree;
	private Listbox titleListbox;
	
	private AuditConfigService auditConfigService;
	private TitleService titleService;
	private UserDetailService userDetailService;	
	
	private WkTUser user;
	private List<WkTTitle> sTlist = new ArrayList<WkTTitle>();
	private List<Jxkh_STitle> stlist = null;
	private List<WkTTitle> tlist = null;
	List<WkTTitle> uFirstTlist = new ArrayList<WkTTitle>();
	
	@SuppressWarnings("unchecked")
	@Override
	public void initShow() {
		try {
			Messagebox.show("��ѡ�����ʼ����Ҫһ��ʱ�䣬�����ĵȴ�...");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//��ǰ�û��ı���Ȩ��		
		tlist = (List<WkTTitle>) Sessions.getCurrent().getAttribute("ulist");
		titleTree.setTreeitemRenderer(new TreeitemRenderer() {
			public void render(Treeitem arg0, Object arg1) throws Exception {
				WkTTitle t = (WkTTitle) arg1;
				if(t != null && checkTitle(t)) {
					arg0.setValue(t);
					arg0.setLabel(t.getKtName()!=null?t.getKtName():"");
					if(sTlist.contains(t))
						arg0.setDisabled(true);
					else 
						arg0.setDisabled(false);
					arg0.setOpen(true);
				}
			}
		});
		titleListbox.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				WkTTitle t = (WkTTitle) arg1;
				if(t != null) {
					arg0.setValue(t);
					Listcell c00 = new Listcell();
					Listcell c0 = new Listcell(Integer.valueOf(arg0.getIndex()+1).toString());
					Listcell c1 = new Listcell(t.getKtName()!=null?t.getKtName():"");
					Listcell c2 = new Listcell();
					if(t.getKtPid() != null) {
						WkTTitle pt = (WkTTitle) auditConfigService.loadById(WkTTitle.class, t.getKtPid());
						c2.setLabel(pt!=null&&pt.getKtName()!=null?pt.getKtName():"��");
					}
					arg0.appendChild(c00);
					arg0.appendChild(c0);
					arg0.appendChild(c1);
					arg0.appendChild(c2);
				}
			}
		});
		initWindow();
	}	
	
	@Override
	public void initWindow() {
		// ��ȡ��ǰ��¼�û�
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		//��ʼ����ǰ�û���ӵ�еı���Ȩ��
		initTitle();
		//��ʼ����ǰ�û���ѡ�ı���
		initSTitle();
		
	}
	
	
	private void initTitle() {
		
		// ���һ�������б�
		List<WkTTitle> firstTlist = titleService.getChildTitle(Long.parseLong("1"));		
		for(WkTTitle ft : firstTlist) {
			if(checkTitle(ft))
				uFirstTlist.add(ft);
		}
		JxTitleTreeModel tmodel = new JxTitleTreeModel(uFirstTlist, titleService);
		titleTree.setModel(tmodel);
	}
	
	/**
	 * <li>������������ʾ�ж��û��Ƿ��д˱���Ȩ��
	 */
	public boolean checkTitle(WkTTitle title) {
		boolean flag = false;
		for (int j = 0; j < tlist.size(); j++) {
			WkTTitle ti = tlist.get(j);
			if (ti.getKtId().intValue() == title.getKtId().intValue()) {
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	private void initSTitle() {
		stlist = userDetailService.getUserSelectedTilte(user.getKuId());
		if(stlist.size() > 0) {
			for(Jxkh_STitle st : stlist) {
				if(st != null && st.getTitleId() != null) {
					WkTTitle t = (WkTTitle) auditConfigService.loadById(WkTTitle.class, st.getTitleId());
					if(t != null)
						sTlist.add(t);
				}
			}
		}
		titleListbox.setModel(new ListModelList(sTlist));
	}
	
	public void onClick$addBn() {
		if(titleTree.getSelectedCount() == 0)
			throw new WrongValueException(titleTree,"��ѡ��Ҫ��ӵı��⣡");
		for(Object o : titleTree.getSelectedItems().toArray()) {
			if(o != null) {
				Treeitem item = (Treeitem) o;
				WkTTitle t = (WkTTitle) item.getValue();
				if(sTlist.contains(t)) {
					throw new WrongValueException(titleTree,"��ѡ�������б����ѱ�ѡ����");
				}
			}
		}
		for(Object o : titleTree.getSelectedItems().toArray()) {
			if(o != null) {
				Treeitem item = (Treeitem) o;
				WkTTitle t = (WkTTitle) item.getValue();
				if(!t.getKtContent().equals("/admin/left.zul") && !t.getKtContent().equals("")){
					sTlist.add(t);
				}else{
					throw new WrongValueException(titleTree,"�ñ����޷���ӣ���ѡ����ѡ��Ŀ�µ��ӱ��⣡");
				}
				
			}
		}
		titleListbox.setModel(new ListModelList(sTlist));
//		JxTitleTreeModel tmodel = new JxTitleTreeModel(uFirstTlist, titleService);
//		titleTree.setModel(tmodel);
	}
	
	public void onClick$deleteBn() {
		if(titleListbox.getSelectedCount() == 0)
			throw new WrongValueException(titleTree,"��ѡ��Ҫɾ���ı��⣡");
		for(Object o : titleListbox.getSelectedItems()) {
			if(o != null) {
				Listitem item = (Listitem) o;
				WkTTitle t = (WkTTitle) item.getValue();
				sTlist.remove(t);
			}
		}
		titleListbox.setModel(new ListModelList(sTlist));
//		JxTitleTreeModel tmodel = new JxTitleTreeModel(uFirstTlist, titleService);
//		titleTree.setModel(tmodel);
	}
	
	public void onClick$sortBn() {
		if(titleListbox.getItemCount() == 0) {
			throw new WrongValueException(titleListbox,"��ǰ����ѡ����");
		}
		final SortTitleWindow stw = (SortTitleWindow) Executions.createComponents("/admin/jxkh/select_title/sort_title.zul", null, null);
		stw.setTitleList(sTlist);
		stw.initWindow();
		stw.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				stw.detach();
				sTlist = stw.getTitleList();
				titleListbox.setModel(new ListModelList(sTlist));
			}
		});
		try {
			stw.doModal();
		} catch (SuspendNotAllowedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void onClick$saveBn() {
		//ɾ��ԭ����ѡ�ı���
		if(stlist != null) {
			for(Jxkh_STitle st : stlist) {
				if(st != null)
					auditConfigService.delete(st);
			}
		}
		//������ѡ�ı���
		for(WkTTitle t : sTlist) {
			if(t != null) {
				Jxkh_STitle s = new Jxkh_STitle();
				s.setTitleId(t.getKtId());
				s.setUserId(user.getKuId());
				auditConfigService.save(s);
			}
		}
		try {
			Messagebox.show("����ɹ����´ε�¼ʱ������Ч��");
			this.detach();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
