package org.iti.xypt.personal.infoCollect;
/**
 * <li>վ��Ȩ�޸��½���,������ϸ�ĵĵ���Ȩ��Ȩ������,��Ӧҳ��admin/content/website/websiteauthupdate.zul
 * @author whm
 * @2010-7-20
 */
import java.util.List;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.uniwin.common.util.IPUtil;
import com.uniwin.framework.common.listbox.DeptListbox;
import com.uniwin.framework.common.listbox.DeptRoleListbox;
import com.uniwin.framework.entity.WkTAuth;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTMlog;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.AuthService;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.service.MLogService;


public class TaskAuthUpdateWindow extends Window implements AfterCompose {

	DeptRoleListbox roleSelect;
	DeptListbox deptSelect;
	List userDeptList;
	WkTUser user;
	WkTAuth auth;
	DepartmentService departmentService;
	AuthService authService;
	//IP��ַ�������
	Textbox IPAddr;
	//Ȩ�����õĶ������
	Checkbox  managec, audit;
	
	//��־���ݷ��ʽӿ�
	MLogService mlogService;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user=(WkTUser)Sessions.getCurrent().getAttribute("user");
		userDeptList=(List)Sessions.getCurrent().getAttribute("userDeptList");
	}
//��ʼ�����´���
	public void ininWindow(WkTAuth auth) {

		this.auth = auth;
		roleSelect.setRootRole(null);
		roleSelect.setDeptList(userDeptList);
		roleSelect.initRoleSelect(auth.getKrId());

		WkTDept pdept = (WkTDept) departmentService.get(WkTDept.class, user.getKdId());
		deptSelect.setRootDept(pdept);
		deptSelect.setRootID(user.getKdId());
		if (auth.getKdId() == null) {
			deptSelect.initNewDeptSelect(deptSelect.getRootDept());
		} else {
			WkTDept dept = (WkTDept) departmentService.get(WkTDept.class, auth.getKdId());
			if (dept == null) {
				deptSelect.initNewDeptSelect(deptSelect.getRootDept());
			} else {
				deptSelect.initNewDeptSelect(dept);
			}
		}
		if (user.getKdId().intValue() != 0) {
			WkTDept rootDept = new WkTDept();
			rootDept.setKdName(" ");
			rootDept.setKdId(0L);
			rootDept.setDep(0);
			deptSelect.getDmodelList().add(0, rootDept);
			deptSelect.setRootDept(rootDept);
		} else {
			deptSelect.getRootDept().setKdName(" ");
		}

		IPAddr.setValue(auth.getIP());
		// ��ʼ������Ȩ��
		if (auth.getKaCode2().intValue() == 1) {
			managec.setChecked(true);
		} else {
			managec.setChecked(false);
		}
		if (auth.getKaCode3().intValue() == 1) {
			audit.setChecked(true);
		} else {
			audit.setChecked(false);
		}

	}
	//������ĿȨ�޵ı༭�����½����
   public void onClick$submit()throws InterruptedException{
	   WkTRole role=(WkTRole)roleSelect.getSelectedItem().getValue();
		WkTDept dept=(WkTDept)deptSelect.getSelectedItem().getValue();	
			if(!managec.isChecked())
			{
				if(!audit.isChecked())
				{
			Messagebox.show("������Ȩ�����", "������ʾ", Messagebox.OK, Messagebox.INFORMATION);
			return;
				}
			}
		//Ȩ����������Ϊ��Ŀ
		auth.setKaType(WkTAuth.TYPE_TASK);
		auth.setKrId(role.getKrId());
		auth.setKdId(dept.getKdId());
		
		if(IPAddr.getValue().length()>0){
		  IPUtil.setIP(auth, IPAddr.getValue());
		}
		
	
		
		if(role.getKrPid()!=null&&role.getKrPid().intValue()==0) {
			return;
		}
		
		if(role.getKrId().intValue()==0&&dept.getKdId().intValue()==0){
			if(user.getKdId().intValue()!=0){
				return;
			}
		}
		//����Ȩ�޵�����
		if(managec.isChecked()){
			auth.setKaCode2(TaskAuthEditWindow.check);
		}else{
			auth.setKaCode2(TaskAuthEditWindow.uncheck);
		}
		if(audit.isChecked()){
			auth.setKaCode3(TaskAuthEditWindow.check);
		}else{
			auth.setKaCode3(TaskAuthEditWindow.uncheck);
		}
		auth.setKaCode(auth.getKaCode2()+""+auth.getKaCode3());
		
		//���º�����Ȩ�޹�����δ���
		if(auth.getKaId()==null){
			authService.save(auth);
			mlogService.saveMLog(WkTMlog.FUNC_CMS,"�½�վ��Ȩ�ޣ�id: "+auth.getKaId(), user);
		}else{
			authService.update(auth);
			mlogService.saveMLog(WkTMlog.FUNC_CMS, "�޸�վ��Ȩ�ޣ�id��"+auth.getKaId(), user);
		}
		
		Events.postEvent(Events.ON_CHANGE, this, null);
	   
   }
   //������ð�ť--��ʼ����ǰ����
	public void onClick$reset() {
		ininWindow(auth);
	}
   //����--�رյ�ǰ����
	public void onClick$close() {
		this.detach();
	}
   
}
