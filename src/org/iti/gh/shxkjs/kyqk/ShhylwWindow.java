package org.iti.gh.shxkjs.kyqk;

import java.util.ArrayList;
import java.util.List;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.entity.GhFile;
import org.iti.gh.entity.GhHylw;
import org.iti.gh.service.GhFileService;
import org.iti.gh.service.HylwService;
import org.iti.gh.service.LwzlService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

import com.uniwin.asm.personal.ui.data.teacherinfo.KyxmzzlistWindow;
import com.uniwin.framework.common.fileuploadex.DownloadFJ;
import com.uniwin.framework.common.fileuploadex.UploadFJ;
import com.uniwin.framework.entity.WkTUser;

public class ShhylwWindow extends BaseWindow {

	Listbox hylwlist;
	Grid shhylw;
	Label cgmc,kanwu,shijian,time,palce,host,all,publish,num,pages;
	Listbox record;
	Row zdqk,rowFile;
	HylwService hylwService;
	Long kdid=(Long)Executions.getCurrent().getArg().get("kdid");
	Short lx=(Short)Executions.getCurrent().getArg().get("lx");
//	Short type=(Short)Executions.getCurrent().getArg().get("type");
	GhHylw Lwzl;
	Textbox reason;
	Radiogroup audit,zd;
	WkTUser user;
	GhFileService ghfileService;
	ListModelList fileModel=new ListModelList();
	Listbox upList;
	Button downFileZip;
	@Override
	public void initShow() {
		hylwlist.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhHylw lwzl=(GhHylw)arg1;
				Listcell c0=new Listcell(1+"");
				Listcell c1=new Listcell("��Ч���˹���ϵͳ���о���ʵ��");
				
				Listcell c2=new Listcell("2010-06");
				Listcell c3=new Listcell("�������ѧ�빤��");
				
				Listcell c4=new Listcell("2011-02");
				Listcell c5=new Listcell("����������");
//				InnerButton bt=new InnerButton("�鿴");
//				bt.addEventListener(Events.ON_CLICK, new EventListener(){
//					public void onEvent(Event arg0) throws Exception {
//						 KyxmzzlistWindow cgWin = (KyxmzzlistWindow) Executions.createComponents
//							("/admin/personal/data/teacherinfo/kyxmzzlist.zul", null, null);
//						 	cgWin.setKuid(lwzl.getUser().getKuId());
//							cgWin.setLw(lwzl);
//							cgWin.initWindow();
//							cgWin.doHighlighted();
//					}});
//				c5.appendChild(bt);	
				Listcell c6=new Listcell("EI��¼");
				Listcell c7=new Listcell();
				InnerButton ib=new InnerButton("���");
				c7.appendChild(ib);
				ib.addEventListener(Events.ON_CLICK, new EventListener(){

					public void onEvent(Event arg0) throws Exception {
						hylwlist.setVisible(false);
						shhylw.setVisible(true);
					}
					
				});
				arg0.appendChild(c0);arg0.appendChild(c1);arg0.appendChild(c2);
				arg0.appendChild(c3);arg0.appendChild(c4);arg0.appendChild(c5);
				arg0.appendChild(c6); arg0.appendChild(c7);
			}
			
		});
	}

	@Override
	public void initWindow() {
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		hylwlist.setVisible(true);
		shhylw.setVisible(false);
		reason.setValue("");
		audit.setSelectedIndex(0);
		if(kdid==null){
			kdid=getXyUserRole().getKdId();
		}	
		List lwlist=hylwService.findSumKdId(kdid, lx, null);
		if(lwlist!=null&&lwlist.size()>0){
			hylwlist.setModel(new ListModelList(lwlist));
		}else{
			hylwlist.setModel(new ListModelList());
		}
		
		
	}
   public void ininGrid(GhHylw lwzl){
	   fileModel.clear();
	   List fjList=new ArrayList();
	   if(lx==GhHylw.JYLW){
		   zdqk.setVisible(true);
		   fjList = ghfileService.findByFxmIdandFType(lwzl.getLwId(), 13);
	   }else if(lx==GhHylw.KYLW){
		   zdqk.setVisible(false);
		   fjList = ghfileService.findByFxmIdandFType(lwzl.getLwId(), 3);
	   }
	   if (fjList==null||fjList!=null&&fjList.size() == 0) {// �޸���
			rowFile.setVisible(false);
			downFileZip.setDisabled(true);
		} else {// �и���
			rowFile.setVisible(true);
			downFileZip.setDisabled(false);
			// ��ʼ������
			for (int i = 0; i < fjList.size(); i++) {
				UploadFJ ufj = new UploadFJ(false);
				try {
					ufj.ReadFJ(getDesktop(), (GhFile) fjList.get(i));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				fileModel.add(ufj);
			}
			upList.setModel(fileModel);
			rowFile.setVisible(true);
		}
	   List recordtype = new ArrayList();
		String[] Recordtype = {"-��ѡ��-","SCI(��ѧ��������)��¼","EI(��������)��¼","ISTP(�Ƽ�����¼���� )��¼","CSCD(�й���ѧ�������ݿ�)��¼","CSSCI(��������ѧ��������)��¼","SSCI(����ѧ��������)��¼","A&HCI(���������Ŀ�ѧ��������)��¼","���»���ժ�� ȫ��ת��","���й���ѧ��ժ��ȫ��ת��","���й�����ѧ��ժ��ȫ��ת��","���й���ѧ��ȫ��ת��","���й��˴�ӡ���ϡ�ȫ��ת��","��ȫ���ߵ�ԺУ�Ŀ�ѧ����ժ��ȫ��ת��", "���»���ժ��ժҪת��","���й�����ѧ��ժ��ժҪת��","���й��˴�ӡ���ϡ�ժҪת��" ,"���й���ѧ��ժҪת��"};
		for(int i = 0;i < Recordtype.length; i++){
			recordtype.add(Recordtype[i]);
		}
		record.setModel(new ListModelList(recordtype));
		if(Lwzl.getLwRecord() == null || Lwzl.getLwRecord() == ""){
			record.setSelectedIndex(0);
		}else {
			record.setSelectedIndex(Integer.valueOf(Lwzl.getLwRecord().trim()));
		}
		cgmc.setValue(lwzl.getLwMc());
		kanwu.setValue(lwzl.getLwKw());
		shijian.setValue(lwzl.getLwFbsj());
		time.setValue(lwzl.getLwTime());
		palce.setValue(lwzl.getLwPlace());
		host.setValue(lwzl.getLwHost());
		all.setValue(lwzl.getLwAll());
		publish.setValue(lwzl.getLwPublish());
		num.setValue(lwzl.getLwNum());
		pages.setValue(lwzl.getLwPages());
		if(lx==GhHylw.JYLW){
			if (lwzl.getLwZd().shortValue() == (GhHylw.LWZDXS.shortValue())) { // ��ʦ���Ļ���ָ��ѧ������
				zd.setSelectedIndex(0);
			} else {
				zd.setSelectedIndex(1);
			}
		}
		
   }
 //������ظ���
	public void onClick$downFileZip(){
		DownloadFJ.ListModelListDownloadCommand(this.getDesktop(), Lwzl.getLwId(), fileModel);
	}
	//�����ļ�����
	public void onClick$downFile(){
		Listitem it = upList.getSelectedItem();
		if (it == null) {
			if (fileModel.getSize() > 0) {
				it = upList.getItemAtIndex(0);
			}
		}
		UploadFJ temp = (UploadFJ) it.getValue();
		DownloadFJ.DownloadCommand(temp);
	}
//   public void onClick$submit(){
//	   Lwzl.setAuditState(Short.parseShort(audit.getSelectedIndex()+""));
//	   Lwzl.setAuditUid(user.getKuId());
//	   Lwzl.setAuditReason(reason.getValue());
//	   hylwService.update(Lwzl);
//	   try {
//			Messagebox.show("��˳ɹ���","��ʾ",Messagebox.OK,Messagebox.INFORMATION);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		initWindow();
//   }
//   public void onClick$close(){
//   	initWindow();
////   	Events.postEvent(Events.ON_CHANGE, this, null);
//   }
   public void onClick$back(){
   	this.detach();
   	Events.postEvent(Events.ON_CHANGE, this, null);
   }
}
