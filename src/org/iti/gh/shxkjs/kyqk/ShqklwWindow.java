package org.iti.gh.shxkjs.kyqk;

import java.util.ArrayList;
import java.util.List;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.entity.GhFile;
import org.iti.gh.entity.GhQklw;
import org.iti.gh.service.GhFileService;
import org.iti.gh.service.LwzlService;
import org.iti.gh.service.QklwService;
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

public class ShqklwWindow extends BaseWindow {

	Listbox qklwlist;
	Grid auditqklw;
	GhQklw Lwzl;
	Label cgmc,kanwu,shijian,all,num,pages,location,factor,issn;
	Listbox record;
	Row zdqk,rowFile;
	QklwService qklwService;
	Long kdid=(Long)Executions.getCurrent().getArg().get("kdid");
	Short lx=(Short)Executions.getCurrent().getArg().get("lx");
	Textbox reason;
	Radiogroup center,audit,zd;
	WkTUser user;
	GhFileService ghfileService;
	ListModelList fileModel=new ListModelList();
	Listbox upList;
	Button downFileZip;
	@Override
	public void initShow() {
		qklwlist.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhQklw lw = (GhQklw) arg1;
				//���
				Listcell c1 = new Listcell(arg0.getIndex()+1+"");
				//��������
				Listcell c2 = new Listcell();
				String str0 = null;
				if (lw.getLwMc() == null) {
					str0 = "";
				} else {
					str0 =lw.getLwMc();
					int len = str0.trim().length();
					if (len > 11) {
						str0 = str0.substring(0, 10) + "...";
					} else {
						str0 =lw.getLwMc();
					}
				}
				c2.setLabel(str0);
				//��������
				Listcell c3 = new Listcell(lw.getLwKw());
				String str1 = null;
				if (lw.getLwKw() == null) {
					str1 = "";
				} else {
					str1 =lw.getLwKw();
					int len = str1.trim().length();
					if (len > 11) {
						str1 = str1.substring(0, 10) + "...";
					} else {
						str1 =lw.getLwKw();
					}
				}
				c3.setLabel(str1);
				//����ʱ��
				Listcell c4 = new Listcell(lw.getLwFbsj());
				//ҳ�뷶Χ
				Listcell c5 = new Listcell(lw.getLwAll());
//				if(lw.getLwCenter().shortValue() == GhQklw.LWHX_NO.shortValue() ){
//					c5.setLabel("��");
//				}else{
//					c5.setLabel("��");
//				}
				//����
				Listcell c6= new Listcell(lw.getLwPages());
//				//ISSN/CN
//				Listcell c7 = new Listcell(lw.getLwIssn());
//				//��Ŀ����
				Listcell c8= new Listcell(lw.getUser().getKuName());
//			    InnerButton bt=new InnerButton("�鿴");
//			    
//				 bt.addEventListener(Events.ON_CLICK, new EventListener(){
//					public void onEvent(Event arg0) throws Exception {
//						 KyxmzzlistWindow cgWin = (KyxmzzlistWindow) Executions.createComponents
//							("/admin/personal/data/teacherinfo/kyxmzzlist.zul", null, null);
//						 	cgWin.setKuid(lw.getKuId());
//							cgWin.setQklw(lw);
//							cgWin.initWindow();
//							cgWin.doHighlighted();
//					}});
//				 c8.appendChild(bt);
					
				//����
				Listcell c9 = new Listcell();
				InnerButton gn = new InnerButton("���");
				c9.appendChild(gn);
				gn.addEventListener(Events.ON_CLICK, new EventListener(){

					public void onEvent(Event arg0) throws Exception {
						Lwzl=lw;
						qklwlist.setVisible(false);
						auditqklw.setVisible(true);
						//initAuditQk(lw);
					}
					
				});
				arg0.appendChild(c1); arg0.appendChild(c2); arg0.appendChild(c3);
				arg0.appendChild(c4); arg0.appendChild(c5); arg0.appendChild(c6); 
//				arg0.appendChild(c7);
				arg0.appendChild(c8);arg0.appendChild(c9); 
			}
		});

	}
	@Override
	public void initWindow() {
		// TODO Auto-generated method stub
		
	}


//	public void initWindow() {
//		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
//		qklwlist.setVisible(true);
//		auditqklw.setVisible(false);
//		reason.setValue("");
//		audit.setSelectedIndex(0);
//		if(kdid==null){
//			kdid=getXyUserRole().getKdId();
//		}	
//		List lwlist=qklwService.findSumKdId(kdid, lx, null);
//		if(lwlist!=null&&lwlist.size()>0){
//			qklwlist.setModel(new ListModelList(lwlist));
//		}else{
//			qklwlist.setModel(new ListModelList());
//		}
//		
//	}
//   public void initAuditQk(GhQklw lwzl){
//	   fileModel.clear();
//	   if(lx==GhQklw.JYLW){
//		   zdqk.setVisible(true);
//	   }else if(lx==GhQklw.KYLW){
//		   zdqk.setVisible(false);
//	   }
//	   List recordtype = new ArrayList();
//		String[] Recordtype = {"-��ѡ��-","SCI(��ѧ��������)��¼","EI(��������)��¼","ISTP(�Ƽ�����¼���� )��¼","CSCD(�й���ѧ�������ݿ�)��¼","CSSCI(��������ѧ��������)��¼","SSCI(����ѧ��������)��¼","A&HCI(���������Ŀ�ѧ��������)��¼","���»���ժ�� ȫ��ת��","���й���ѧ��ժ��ȫ��ת��","���й�����ѧ��ժ��ȫ��ת��","���й���ѧ��ȫ��ת��","���й��˴�ӡ���ϡ�ȫ��ת��","��ȫ���ߵ�ԺУ�Ŀ�ѧ����ժ��ȫ��ת��", "���»���ժ��ժҪת��","���й�����ѧ��ժ��ժҪת��","���й��˴�ӡ���ϡ�ժҪת��" ,"���й���ѧ��ժҪת��"};
//		for(int i = 0;i < Recordtype.length; i++){
//			recordtype.add(Recordtype[i]);
//		}
//		record.setModel(new ListModelList(recordtype));
//		if(Lwzl.getLwRecord() == null || Lwzl.getLwRecord() == ""){
//			record.setSelectedIndex(0);
//		}else {
//			record.setSelectedIndex(Integer.valueOf(Lwzl.getLwRecord().trim()));
//		}
//		 cgmc.setValue(lwzl.getLwMc());
//		 kanwu.setValue(lwzl.getLwKw());
//		 shijian.setValue(lwzl.getLwFbsj());
////		 host.setValue(lwzl.get);
//		 all.setValue(lwzl.getLwAll());
//		 num.setValue(lwzl.getLwNum());
//		 pages.setValue(lwzl.getLwPages());
//		 location.setValue(lwzl.getLwLocation());
//		 factor.setValue(lwzl.getLwFactor());
//		 issn.setValue(lwzl.getLwIssn());
//		 if(lx==GhQklw.JYLW){
//				if (lwzl.getLwZd().shortValue() == (GhQklw.LWZDXS.shortValue())) { // ��ʦ���Ļ���ָ��ѧ������
//					zd.setSelectedIndex(0);
//				} else {
//					zd.setSelectedIndex(1);
//				}
//			}
//		List fjList=new ArrayList();
//		if (lx == GhQklw.JYLW) {
//			fjList = ghfileService.findByFxmIdandFType(lwzl.getLwId(), 14);
//		} else if (lx == GhQklw.KYLW) {
//			fjList = ghfileService.findByFxmIdandFType(lwzl.getLwId(), 4);
//		}
//		 if (fjList==null||fjList!=null&&fjList.size() == 0)  {// �޸���
//			rowFile.setVisible(false);
//			downFileZip.setDisabled(true);
//		} else {// �и���
//			downFileZip.setDisabled(false);
//			// ��ʼ������
//			for (int i = 0; i < fjList.size(); i++) {
//				UploadFJ ufj = new UploadFJ(false);
//				try {
//					ufj.ReadFJ(getDesktop(), (GhFile) fjList.get(i));
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				fileModel.add(ufj);
//			}
//			upList.setModel(fileModel);
//			rowFile.setVisible(true);
//		}
//   }
//   public void onClick$submit(){
//	   Lwzl.setAuditState(Short.parseShort(audit.getSelectedIndex()+""));
//	   Lwzl.setAuditUid(user.getKuId());
//	   Lwzl.setAuditReason(reason.getValue());
//	   qklwService.update(Lwzl);
//	   try {
//			Messagebox.show("��˳ɹ���","��ʾ",Messagebox.OK,Messagebox.INFORMATION);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		initWindow();
//   }
 //������ظ���
//	public void onClick$downFileZip(){
//		DownloadFJ.ListModelListDownloadCommand(this.getDesktop(), Lwzl.getLwId(), fileModel);
//	}
	//�����ļ�����
//	public void onClick$downFile(){
//		Listitem it = upList.getSelectedItem();
//		if (it == null) {
//			if (fileModel.getSize() > 0) {
//				it = upList.getItemAtIndex(0);
//			}
//		}
//		UploadFJ temp = (UploadFJ) it.getValue();
//		DownloadFJ.DownloadCommand(temp);
//	}
//   public void onClick$close(){
//	   	initWindow();
//	   }
//   public void onClick$back() {
//		this.detach();
//		Events.postEvent(Events.ON_CHANGE, this, null);
//	}
}
