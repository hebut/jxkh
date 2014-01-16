package org.iti.jxkh.score.setup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxkh.entity.JXKH_AppraisalMember;
import org.iti.jxkh.entity.JXKH_AuditConfig;
import org.iti.jxkh.service.AuditConfigService;
import org.iti.jxkh.service.JxkhAwardService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;

public class StepupNewWindow extends Window implements AfterCompose  {

	/**
	 * @author WXY
	 */
	private static final long serialVersionUID = -8211295795490841685L;
/******************************������Ա�趨******************************************/
	private YearListbox yearlist;
	private Listbox deptListbox1,deptListbox2,listbox1,listbox2;
	private JxkhAwardService jxkhAwardService;
	private String userIdSearch = "";
	private List<WkTUser> userlist = new ArrayList<WkTUser>();
	private Textbox userName,staffId;
	List<JXKH_AppraisalMember> tempMem = new ArrayList<JXKH_AppraisalMember>();
/**************************���˲����趨***************************************************/
	private JXKH_AuditConfig ac;
	private AuditConfigService auditConfigService;
	
	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		yearlist.initYearListbox("");//����б�
		deptListbox1.setItemRenderer(new deptRenderer());
		deptListbox2.setItemRenderer(new deptRenderer());
	    listbox1.setItemRenderer(new managerRenderer());//��Ա�б���Ⱦ��
	    listbox2.setItemRenderer(new choosedManagerRenderer());//��ѡ��Ա�б���Ⱦ��
	    initDeptWindow();
	    initListWindow();
	   
	}
	public void onSelect$yearlist() {
		String year =(String) yearlist.getSelectedItem().getValue();
		ac = auditConfigService.findByYear(year);
	}
	public void onClick$save() {
		if (yearlist.getSelectedIndex() == 0||yearlist.getSelectedItem()==null) {
			try {
				Messagebox.show("��ѡ����ݣ�");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		if (ac == null) {
			ac = new JXKH_AuditConfig();
		}
		String year =(String) yearlist.getSelectedItem().getValue();
		ac.setAcYear(year);
		
		auditConfigService.saveOrUpdate(ac);
		try {
			Messagebox.show("����ɹ���", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/*************************************������Ա�趨*****************************************/
	public void onSelect$deptListbox2(){//��������б�
		WkTDept deptment = (WkTDept) deptListbox2.getSelectedItem().getValue();
		List<JXKH_AppraisalMember>dlist=jxkhAwardService.findpeoByDept(deptment.getKdName());
		listbox2.setModel(new ListModelList(dlist));
	}
	public void onClick$add() throws InterruptedException{//��Ӱ�ť
		if(yearlist.getSelectYear()==null){
			Messagebox.show("����ѡ����ݣ�");
		}else
		{
		if(listbox1.getSelectedItems()==null||listbox1.getSelectedItems().size()<=0){
			try {
				Messagebox.show("��ѡ��Ҫ��ӵ���Ա��", "��ʾ", Messagebox.OK,
						Messagebox.EXCLAMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}else{
			@SuppressWarnings("unchecked")
			Iterator<Listitem> items = listbox1.getSelectedItems().iterator();
			WkTUser m = new WkTUser();
			while (items.hasNext()) {
				m = (WkTUser) items.next().getValue();
				JXKH_AppraisalMember appraisalMember = new JXKH_AppraisalMember();
				appraisalMember.setName(m.getKuName());
				appraisalMember.setPersonId(m.getKuLid());
				appraisalMember.setKuId(m.getKuId());
				appraisalMember.setDept(m.getDept().getKdName());
				appraisalMember.setKuType(m.getKuType());
				appraisalMember.setYear(yearlist.getSelectYear());
				appraisalMember.setDeptId(m.getDept().getKdId());//����id
				jxkhAwardService.saveOrUpdate(appraisalMember);
			}
		    try {
				Messagebox.show("������Ա��ӳɹ�,��ˢ�£�", "��ʾ", Messagebox.OK,
						Messagebox.INFORMATION);
			} catch (Exception e) {
				e.printStackTrace();
				try {
					Messagebox.show("������Ա���ʧ�ܣ������ԣ�", "��ʾ", Messagebox.OK,
							Messagebox.ERROR);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			initListWindow();
		}
		}
	}
	public void onClick$delete() throws InterruptedException{//ɾ����ť
		if(listbox2.getSelectedItems()==null||listbox2.getSelectedItems().size()<=0){
			try {
				Messagebox.show("��ѡ��Ҫɾ���Ŀ�����Ա��", "��ʾ", Messagebox.OK,
						Messagebox.EXCLAMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		Messagebox.show("ȷ��ɾ����?", "ȷ��", Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						if (evt.getName().equals("onOK")) {
							@SuppressWarnings("unchecked")
							Iterator<Listitem> items = listbox2
									.getSelectedItems().iterator();
							while (items.hasNext()) {
								JXKH_AppraisalMember mem = (JXKH_AppraisalMember) items.next().getValue();
								jxkhAwardService.delete(mem);
							}
							initListWindow();
					        try {
								Messagebox.show("������Աɾ���ɹ���", "��ʾ",
										Messagebox.OK, Messagebox.INFORMATION);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				});
	}
	public void initListWindow(){
		deptListbox1.setSelectedIndex(0);
		deptListbox2.setSelectedIndex(0);
		 //��ʼ����ѡ��Ա�б�
		List<JXKH_AppraisalMember> setMem = jxkhAwardService.findAllAppraisalMember();
		listbox2.setModel(new ListModelList(setMem));
	    //��ʼ����Ա�б�
		if (setMem.size() != 0) {
			for (int i = 0; i < setMem.size(); i++) {
			JXKH_AppraisalMember user = (JXKH_AppraisalMember) setMem.get(i);
			userIdSearch += "'" + user.getPersonId() + "',";
			}
			if (userIdSearch.endsWith(",")){
			userIdSearch = userIdSearch.substring(0,userIdSearch.length() - 1);}
		}
		userlist = jxkhAwardService.findWkTUserNotInListBox2(userIdSearch);
	    listbox1.setModel(new ListModelList(userlist));
	    userIdSearch="";//
	}
	public void initDeptWindow(){
		List<WkTDept> deptlist = new ArrayList<WkTDept>();
		WkTDept dept1 = new WkTDept();
		deptlist.add(dept1);
		List<WkTDept> dlist = jxkhAwardService.findDeptAll();
		deptlist.addAll(dlist);
		deptListbox1.setModel(new ListModelList(deptlist));
		deptListbox1.setSelectedIndex(0);
		deptListbox2.setModel(new ListModelList(deptlist));
		deptListbox2.setSelectedIndex(0);
		
	}
	public class deptRenderer implements ListitemRenderer{//�����б���Ⱦ��
		public void render(Listitem arg0, Object arg1) throws Exception {
			WkTDept dept = (WkTDept) arg1;
			arg0.setValue(dept);
			Listcell c0 = new Listcell();
			if (dept.getKdName() == null) {
				c0 = new Listcell("--��ѡ��--");
			} else {
				c0 = new Listcell(dept.getKdName());
			}
			arg0.appendChild(c0);
		}
	}
    public class managerRenderer implements ListitemRenderer{//��Ա�б���Ⱦ��
		public void render(Listitem item, Object data) throws Exception {
			if(data==null)return;
			WkTUser user = (WkTUser) data;
			item.setValue(user);
			Listcell c0 = new Listcell();
			Listcell c1 = new Listcell(user.getKuName());//��Ա����
			Listcell c2 = new Listcell(user.getStaffId());//��Ա���
			Listcell c3 = new Listcell();//��Ա����
			if(user.getKuType()==0){
				c3.setLabel("�ڱ�");
			}
			else if(user.getKuType()==1){
				c3.setLabel("��Ƹ");
			}
			Listcell c4 = new Listcell(user.getDept1() == null ? "" : user.getDept1().getKdName());
			item.appendChild(c0);
			item.appendChild(c1);
			item.appendChild(c2);
			item.appendChild(c3);
			item.appendChild(c4);
		}
    }
    public class choosedManagerRenderer implements ListitemRenderer{//��ѡ��Ա�б���Ⱦ��
	   public void render(Listitem item, Object data) throws Exception {
		if(data==null)return;
		JXKH_AppraisalMember user = (JXKH_AppraisalMember) data;
		item.setValue(user);
		Listcell c0 = new Listcell();
		Listcell c1 = new Listcell(user.getName());//��Ա����
		Listcell c2 = new Listcell(user.getPersonId());//��Ա���
		Listcell c3 = new Listcell();//��Ա����
		if(user.getKuType()==0){
			c3.setLabel("��ְ");
		}
		else if(user.getKuType()==1){
			c3.setLabel("��Ƹ");
		}
		else{
			c3.setLabel(String.valueOf(user.getKuType()));
		}
		Listcell c4 = new Listcell(user.getDept());//���ڲ���
		item.appendChild(c0);
		item.appendChild(c1);
		item.appendChild(c2);
		item.appendChild(c3);
		item.appendChild(c4);
	}
    }
    public void onClick$view(){//��ѯ��ť
    	
    	/*List<JXKH_AppraisalMember> setMem = jxkhAwardService.findAllAppraisalMember();
    	jxkhAwardService.deleteAll(setMem);*/
    	
    	WkTDept deptment = (WkTDept) deptListbox1.getSelectedItem().getValue();
		List<WkTUser> dlist = jxkhAwardService.findWkTUserByConditions(staffId.getValue(), userName.getValue(), deptment.getKdId(),"");
		listbox1.setModel(new ListModelList(dlist));
    }
}
