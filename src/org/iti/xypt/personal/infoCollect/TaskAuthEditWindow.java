package org.iti.xypt.personal.infoCollect;

/**
 * <li>վ��Ȩ�ޱ༭�������Ӧ����admin.content.website.websiteauthedit.zul
 * @author whm
 * @2010-7-19
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.iti.xypt.personal.infoCollect.entity.WkTExtractask;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Window;

import com.uniwin.common.util.IPUtil;
import com.uniwin.framework.common.listbox.DeptListbox;
import com.uniwin.framework.common.listbox.DeptRoleListbox;
import com.uniwin.framework.common.listbox.RoleListbox;
import com.uniwin.framework.common.listbox.WebsiteAuthListbox;
import com.uniwin.framework.entity.WkTAuth;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTMlog;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.AuthService;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.service.MLogService;
import com.uniwin.framework.service.RoleService;

public class TaskAuthEditWindow extends Window implements AfterCompose {

	private static final long serialVersionUID = 6646263082699757069L;
	WkTUser user;
	// ��ɫ�������
	Combobox ktrole;
	// �����������
	Combobox ktdept;
	RoleListbox krole;
	// ��ɫ���ݷ��ʽӿ�
	RoleService roleService;
//	ChanelService chanelService;
	AuthService authService;
	DepartmentService departmentService;
	ListModel rlistModel;
	//��ǰ�û������б�
	List userDeptList;
	// Ҫ�༭Ȩ�޵������б�
	List cList = new ArrayList();
	
	//��ɫ�б����	
	DeptRoleListbox rolelist;
	//��֯����ѡ�����
	DeptListbox deptlist;
	// Ȩ���б�
	WebsiteAuthListbox hasAuth;
	//����ѡ��ť���������ѡ��������
	Toolbarbutton taskChoose;
	// ҳ����ʾ����������
	Textbox ktname, authIp;
	// ��ǰȨ��
	Label curauth;	
	// �������õ�Ȩ��
	Checkbox  managerAuth,auditAuth;
	// ����Ȩ�޵�����
	Radio totalSet, addSet;
	public static final Short check = 1, uncheck = 0;
	List rids, dids;
	//��־���ݷ��ʽӿ�
	MLogService mlogService;
	WkTExtractask editTask;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		this.setTitle("�༭����Ȩ��");
		user=(WkTUser)Sessions.getCurrent().getAttribute("user");
		userDeptList=(List)Sessions.getCurrent().getAttribute("userDeptList");

		// ��ѡ���б����ʱ�����½�ɫ�Զ�ѡ��
		ktrole.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event event) throws Exception {
				Comboitem it = ktrole.getSelectedItem();
				if (it != null) {
					WkTRole sr = (WkTRole) it.getValue();
					setRoleListBoxSelected(sr.getKrId());
				}
			}

		});
		

		// ѡ������Ȩ�޵Ĳ���
		ktdept.addEventListener(Events.ON_SELECT, new EventListener() {

			public void onEvent(Event event) throws Exception {
				Comboitem it = ktdept.getSelectedItem();
				if (it != null) {
					Long did = (Long) it.getValue();
					setDeptListBoxSelected(did);
				}

			}
		});

		// ����ѡ��ť�������¼����������ѡ����༰��������Ĵ������
		taskChoose.addEventListener(Events.ON_CLICK, new EventListener() {
			public void onEvent(Event event) throws Exception {
				TaskAuditMulSelecteWindow w = (TaskAuditMulSelecteWindow) Executions
						.createComponents(
								"/admin/personal/infoExtract/task/wMulWindow.zul", null,
								null);
				w.doHighlighted();
				w.initWindow();
				w.addEventListener(Events.ON_CHANGE, new EventListener() {

					public void onEvent(Event event) throws Exception {
						TaskAuditMulSelecteWindow w = (TaskAuditMulSelecteWindow)event.getTarget();
						Set sel = w.getTree().getSelectedItems();
						Iterator it = sel.iterator();
						String tname = "";
						while (it.hasNext()) {
							Treeitem item = (Treeitem) it.next();
							WkTExtractask ti = (WkTExtractask) item.getValue();
							cList.add(ti);
							tname = tname + ti.getKeName().trim() + ",";
						}
						ktname.setValue(tname);
						w.detach();
					}

				});
			}
		});
	}
	
	/**
	 * <li>������������ʼ������Ȩ�޽���
	 */
public void initWindow(WkTExtractask editTask) {
		
		WkTDept pdept=(WkTDept)departmentService.get(WkTDept.class, user.getKdId());
		deptlist.setRootDept(pdept);
		deptlist.setRootID(user.getKdId());		
		deptlist.initNewDeptSelect(deptlist.getRootDept());	
		if(user.getKdId().intValue()!=0){
        WkTDept rootDept=new WkTDept();
		rootDept.setKdName(" ");
	    rootDept.setKdId(0L);	
	    rootDept.setDep(0);
		deptlist.getDmodelList().add(0, rootDept);
		deptlist.setRootDept(rootDept);	
		}else{
			deptlist.getRootDept().setKdName(" ");
		}
		rolelist.setRootRole(null);
		rolelist.setDeptList(userDeptList);
		rolelist.initRoleSelect(0L);		
		rids=rolelist.getRidList();
		dids=deptlist.getDidList();
		cList.clear();
		if(editTask!=null){
		 hasAuth.initAuthList(editTask.getKeId(), rids, dids);
		 cList.add(editTask);		
		 this.editTask = editTask;
		 this.setTitle("�༭����Ȩ�ޣ�"+editTask.getKeName());
		//���±�������
		 ktname.setValue(editTask.getKeName());
		}
		curauth.setValue("");
		managerAuth.setChecked(false);
	    authIp.setValue("");
	}
	
//����Ȩ������
	public void onClick$submit() throws InterruptedException {

		if (cList.size() == 0) {
			Messagebox.show("��ѡ������", "������ʾ", Messagebox.OK,
					Messagebox.INFORMATION);
			return;
		}
		if(hasAuth.getItemCount()==0)
		{
			Messagebox.show("�����Ȩ��", "������ʾ", Messagebox.OK,
					Messagebox.INFORMATION);
			return;
		}
		List aulist = hasAuth.getAmodelList().getInnerList();
		StringBuffer sb=new StringBuffer( "�༭����Ȩ�ޣ�id:");
		if (totalSet.isChecked()) {// ��ȫ���ã���Ҫ��ԭ�� ��ɾ��
			for (int i = 0; i < cList.size(); i++) {
				WkTExtractask w = (WkTExtractask) cList.get(i);
				authService.deleteAuthOfTask(w.getKeId(), rids, dids);
				for (int j = 0; j < aulist.size(); j++) {
					WkTAuth au = (WkTAuth) aulist.get(j);
					au.setKaRid(w.getKeId());
					authService.save(au);
				}
				  sb.append(w.getKeId()+",");
			}
		} else { // �������ã�������Ҫ�ж�����Ȩ�����Ƿ��Ѿ�������ͬȨ����
			for (int i = 0; i < cList.size(); i++)
			{
				WkTExtractask w = (WkTExtractask) cList.get(i);
				  sb.append(w.getKeId()+",");
				for (int j = 0; j < aulist.size(); j++) {
					WkTAuth au = (WkTAuth) aulist.get(j);
					au.setKaRid(w.getKeId());
					List mlist = authService.findByExample(au);
					if (mlist.size() == 0) {
						authService.save(au);
					}
				}
			}
		}
		//������־��Ϣ
		mlogService.saveMLog(WkTMlog.FUNC_CMS, sb.toString(), user);
		Messagebox.show("����ɹ���", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
		Events.postEvent(Events.ON_CHANGE, this, null);
		this.detach();
	}
//����Ȩ��
	public void onClick$addAuth() throws InterruptedException {

		if (!(managerAuth.isChecked()||auditAuth.isChecked())) {
			Messagebox.show("������Ȩ�����", "������ʾ", Messagebox.OK,
					Messagebox.INFORMATION);
			return;
		}
		Set rset = rolelist.getSelectedItems();
		Set dset = deptlist.getSelectedItems();

		List rlist = new ArrayList(rset);
		List dlist = new ArrayList(dset);

		for (int i = 0; i < rlist.size(); i++) {
			Listitem ritem = (Listitem) rlist.get(i);
			WkTRole role = (WkTRole) ritem.getValue();
			if (role.getKrPid() != null && role.getKrPid().intValue() == 0)
				continue;
			for (int j = 0; j < dlist.size(); j++) {
				Listitem ditem = (Listitem) dlist.get(j);
				WkTDept dept = (WkTDept) ditem.getValue();
				WkTAuth au = new WkTAuth();
				au.setKaType(WkTAuth.TYPE_TASK);
				au.setKrId(role.getKrId());
				au.setKdId(dept.getKdId());
				if (managerAuth.isChecked()) {
					au.setKaCode2(this.check);
				} else {
					au.setKaCode2(this.uncheck);
				}
				if(auditAuth.isChecked()){
					au.setKaCode3(this.check);
				} else {
					au.setKaCode3(this.uncheck);
				}
				au.setKaCode(au.getKaCode2()+""+au.getKaCode3());
				try {
					if (authIp.getValue().length() > 0)
						IPUtil.setIP(au, authIp.getValue());
				} catch (Exception e) {
					Messagebox.show("IP��ַ����(��ȷ��ʽ��:192.168.*.100-255)", "������ʾ",
							Messagebox.OK, Messagebox.INFORMATION);
					return;
				}
				au.setKaType(WkTAuth.TYPE_TASK + " ");
				hasAuth.getAmodelList().add(au);
			}
		}
	}
//ɾ��Ȩ��
	public void onClick$deleteAuth() {
		Set sitems = hasAuth.getSelectedItems();
		Iterator it = sitems.iterator();
		List dlist = new ArrayList();
		while (it.hasNext()) {
			Listitem item = (Listitem) it.next();
			dlist.add(item.getValue());
		}
		hasAuth.getAmodelList().removeAll(dlist);
	}
//���Ȩ��
	public void onClick$clearAuth() {
		hasAuth.getAmodelList().clear();
	}
	
	public void onSelect$hasAuth(){
		Listitem se=hasAuth.getSelectedItem();
		WkTAuth au=(WkTAuth)se.getValue();
		setRoleListBoxSelected(au.getKrId());
		setDeptListBoxSelected(au.getKdId());
		curauth.setValue(se.getLabel());
		authIp.setValue(au.getIP());		
		if(au.getKaCode2().intValue()==1){
			managerAuth.setChecked(true);
		}else{
			managerAuth.setChecked(false);
		}
		if(au.getKaCode3().intValue()==1){
			auditAuth.setChecked(true);
		}else{
			auditAuth.setChecked(false);
		}
	}
	/**
	 * <li>����������ѡ�н�ɫ�б���һ�
	 * @param rid ��ɫ���
	 * void 
	 * @author FengXinhong
	 */
	public void setRoleListBoxSelected(Long r) {
		List rlist = rolelist.getChildren();
		for (int i = 0; i < rlist.size(); i++) {
			Listitem it = (Listitem) rlist.get(i);
			it.setSelected(false);
			WkTRole rl = (WkTRole) it.getValue();
			if (r.intValue() == rl.getKrId().intValue()) {
				it.setSelected(true);
			}
		}
	}

	// Ϊ��ɫ�б������½ڵ�
	public void addRoleListBoxItem(ListModelList rml, Long pid) {
		List roleList = roleService.getChildRole(pid);
		for (int i = 0; i < roleList.size(); i++) {
			WkTRole r = (WkTRole) roleList.get(i);
			rml.add(r);
			addRoleListBoxItem(rml, r.getKrId());
		}
	}

	/**
	 * <li>����������ѡ�в����б���һ�
	 * @param did ���ű��
	 * void 
	 * @author FengXinhong
	 */
	private void setDeptListBoxSelected(Long did) {
		List dlist = deptlist.getChildren();
		for (int i = 0; i < dlist.size(); i++) {
			Listitem it = (Listitem) dlist.get(i);
			it.setSelected(false);
			WkTDept dl = (WkTDept) it.getValue();
			if (did.intValue() ==dl.getKdId().intValue()) {
				it.setSelected(true);
			}
		}

	}

	private void addDeptListBoxitem(ListModelList drolelist, long did) {
		List deptlist = departmentService.getChildDepartment(did);
		for (int i = 0; i < deptlist.size(); i++) {
			WkTDept d = (WkTDept) deptlist.get(i);
			drolelist.add(d);
			addDeptListBoxitem(drolelist, d.getKdId());
		}
	}
	public WkTExtractask getEditTask() {
		return editTask;
	}
	
	
//������ð�ť���� ���¼�
public void onClick$reset(){
	initWindow(getEditTask());
	ktname.setValue("");
}
//���ذ�ť
public void onClick$close(){
	this.detach();
}
}
