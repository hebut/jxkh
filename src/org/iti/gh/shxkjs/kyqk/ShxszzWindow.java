package org.iti.gh.shxkjs.kyqk;

import java.util.ArrayList;
import java.util.List;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.entity.GhFile;
import org.iti.gh.entity.GhZz;
import org.iti.gh.service.GhFileService;
import org.iti.gh.service.LwzlService;
import org.iti.gh.service.ZzService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zhtml.Messagebox;
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
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

import com.uniwin.asm.personal.ui.data.teacherinfo.KyxszzqkWindow;
import com.uniwin.framework.common.fileuploadex.DownloadFJ;
import com.uniwin.framework.common.fileuploadex.UploadFJ;
import com.uniwin.framework.entity.WkTUser;

public class ShxszzWindow extends BaseWindow {

	Listbox xszzlist,worktype,subjetype;
    Grid auditxszz;
    Label cgmc,kanwu,publitime,editionno,isbn,zb;
//    fzb,bz;
    Long kdid=(Long)Executions.getCurrent().getArg().get("kdid");
	Short lx=(Short)Executions.getCurrent().getArg().get("lx");
	WkTUser user;
	GhFileService ghfileService;
	ZzService zzService;
	ListModelList fileModel=new ListModelList();
	Listbox upList;
	Button downFileZip;
	GhZz Lwzl;
	Row rowFile;
	Textbox reason;
	Radiogroup audit;
	@Override
	public void initShow() {
		xszzlist.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhZz zz = (GhZz) arg1;
				//���
				Listcell c1 = new Listcell(arg0.getIndex()+1+"");
				//ר������
				Listcell c2 = new Listcell(zz.getZzMc());
				//���
				Listcell c3 = new Listcell(zz.getZzEditionno());
				//���浥λ��ISBN
				Listcell c4 = new Listcell(zz.getZzKw());
				//����ʱ��
				Listcell c5 = new Listcell(zz.getZzPublitime());
//				//����
//				Listcell c6 = new Listcell(zz.getZzZb());
				//��д��
				Listcell c6 = new Listcell(zz.getUser().getKuName());
				//����
				Listcell c7 = new Listcell();
				InnerButton gn = new InnerButton();

				gn.setLabel("���");
				gn.addEventListener(Events.ON_CLICK, new EventListener(){

					public void onEvent(Event arg0) throws Exception {
						Lwzl=zz;
						xszzlist.setVisible(false);
					    auditxszz.setVisible(true);
					    initAuditXszz(zz);
					}
				});
				c7.appendChild(gn);

				arg0.appendChild(c1); arg0.appendChild(c2); arg0.appendChild(c3);
				arg0.appendChild(c4); arg0.appendChild(c5); arg0.appendChild(c6);
				arg0.appendChild(c7);
				
			}

		});
	}

	@Override
	public void initWindow() {
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		xszzlist.setVisible(true);
	    auditxszz.setVisible(false);
		reason.setValue("");
		audit.setSelectedIndex(0);
		List lwlist=zzService.findSumKdId(kdid, GhZz.ZZ, null);
		if(lwlist!=null&&lwlist.size()>0){
			xszzlist.setModel(new ListModelList(lwlist));
		}else{
			xszzlist.setModel(new ListModelList());
		}
		
	}
   public void initAuditXszz(GhZz lwzl){
	   fileModel.clear();
	   List subtype = new ArrayList();
		List subworktype = new ArrayList();
		String[] Subtype = {"--��ѡ��--","��Ȼ��ѧ","����ѧ","����"};
		for(int i = 0;i < Subtype.length; i++){
			subtype.add(Subtype[i]);
		}
		String[] Subworktype = {"--��ѡ��--","����","����","ר��","����","�̲�","�ο���򹤾���" ,"���ļ�","����","���鱨��","����"};
		for(int i = 0;i < Subworktype.length; i++){
			subworktype.add(Subworktype[i]);
		}
		//ѧ�Ʒ���
	   subjetype.setModel(new ListModelList(subtype));
	   worktype.setModel(new ListModelList(subworktype));
	   cgmc.setValue(lwzl.getZzMc());
	   kanwu.setValue(lwzl.getZzKw());
	   publitime.setValue(lwzl.getZzPublitime());
	   editionno.setValue(lwzl.getZzEditionno());
	   isbn.setValue(lwzl.getZzIsbn());
	   zb.setValue(lwzl.getZzZb()!=null?lwzl.getZzZb():"");
//		fzb.setValue(lwzl.getZzFzb()!=null?lwzl.getZzFzb():"");
//		bz.setValue(lwzl.getZzBz()!=null?lwzl.getZzBz():"");
	 //ѧ�Ʒ���
		if(lwzl.getZzSubjetype() == null || lwzl.getZzSubjetype() == ""){
			subjetype.setSelectedIndex(0);
		}else {
			subjetype.setSelectedIndex(Integer.valueOf(lwzl.getZzSubjetype().trim()));
		}
		//��������
		if(lwzl.getZzWorktype() == null || lwzl.getZzWorktype() == ""){
			worktype.setSelectedIndex(0);
		}else {
			worktype.setSelectedIndex(Integer.valueOf(lwzl.getZzWorktype().trim()));
		}
		List fjList = ghfileService.findByFxmIdandFType(lwzl.getZzId(), 5);
		if (fjList.size() == 0) {// �޸���
			rowFile.setVisible(false);
			downFileZip.setDisabled(true);
		} else {// �и���
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
   }
 //������ظ���
	public void onClick$downFileZip(){
		DownloadFJ.ListModelListDownloadCommand(this.getDesktop(), Lwzl.getZzId(), fileModel);
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
	
	public void onClick$submit(){
		  Lwzl.setAuditState(Short.parseShort(audit.getSelectedIndex()+""));
		   Lwzl.setAuditUid(user.getKuId());
		   Lwzl.setAuditReason(reason.getValue());
		   zzService.update(Lwzl);
		   try {
				Messagebox.show("��˳ɹ���","��ʾ",Messagebox.OK,Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			initWindow();
	}
	  public void onClick$close(){
		   	initWindow();
		   }
	  public void onClick$back() {
			this.detach();
			Events.postEvent(Events.ON_CHANGE, this, null);
		}
}