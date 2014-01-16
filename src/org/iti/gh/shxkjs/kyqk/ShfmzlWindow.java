package org.iti.gh.shxkjs.kyqk;

import java.util.ArrayList;
import java.util.List;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.entity.GhFile;
import org.iti.gh.entity.GhFmzl;
import org.iti.gh.service.FmzlService;
import org.iti.gh.service.GhFileService;
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

import com.uniwin.asm.personal.ui.data.teacherinfo.FmzlqkWindow;
import com.uniwin.framework.common.fileuploadex.DownloadFJ;
import com.uniwin.framework.common.fileuploadex.UploadFJ;
import com.uniwin.framework.entity.WkTUser;

public class ShfmzlWindow extends BaseWindow {

	Listbox fmzllist,types;
	Grid auditFmzl;
	Label cgmc,shijian,kanwu,country,requesino,requestdate,reqpublino,inventor;
	Textbox reason;
	Radiogroup audit,status;
	WkTUser user;
	Long kdid=(Long)Executions.getCurrent().getArg().get("kdid");
	Listbox upList;
	ListModelList fileModel=new ListModelList();
	Button downFileZip;
	Row rowFile;
	FmzlService fmzlService;
	GhFileService ghfileService;
	GhFmzl Fmzl;
	@Override
	public void initShow() {
	 
		fmzllist.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhFmzl fm = (GhFmzl) arg1;

				//���
				Listcell c1 = new Listcell(arg0.getIndex()+1+"");

				//����ר������
				Listcell c2 = new Listcell(fm.getFmMc());

				//��Ȩʱ��
				Listcell c3 = new Listcell(fm.getFmSj());

				//ר����Ȩ��
				Listcell c4 = new Listcell(fm.getFmSqh());
			   //������
				Listcell c5 = new Listcell(fm.getUser().getKuName());
				//����
				Listcell c6 = new Listcell();
				InnerButton gn = new InnerButton();

				gn.setLabel("���");
				
				gn.addEventListener(Events.ON_CLICK, new EventListener(){

					public void onEvent(Event arg0) throws Exception {
						Fmzl=fm;
						fmzllist.setVisible(false);
						auditFmzl.setVisible(true);
						initAuditFmzl(fm);
						
					}
				});
				c6.appendChild(gn);

				arg0.appendChild(c1); arg0.appendChild(c2); arg0.appendChild(c3);
				arg0.appendChild(c4); arg0.appendChild(c5);arg0.appendChild(c6);
			}

		});
	}

	@Override
	public void initWindow() {}
    public void initAuditFmzl(GhFmzl fmzl){
    	fileModel.clear();
    	List fmtype = new ArrayList();
		List fx = new ArrayList();
		String[] Fmtype = { "-��ѡ��-", "����", "ʵ������", "������" };
		for (int i = 0; i < Fmtype.length; i++) {
			fmtype.add(Fmtype[i]);
		}
		types.setModel(new ListModelList(fmtype));
		if (fmzl.getFmTypes() == null || fmzl.getFmTypes() == "") {
			types.setSelectedIndex(0);
		} else {
			types.setSelectedIndex(Integer.valueOf(fmzl.getFmTypes().trim()));
		}
		if (fmzl.getFmStatus().shortValue() == (GhFmzl.STA_NO.shortValue())) { // �Ƿ���Чר��
			status.setSelectedIndex(0);
		} else {
			status.setSelectedIndex(1);
		}
		types.setDisabled(true);
		cgmc.setValue(fmzl.getFmMc());
		shijian.setValue(fmzl.getFmSj());
		kanwu.setValue(fmzl.getFmSqh());
		country.setValue(fmzl.getFmCountry());
		requesino.setValue(fmzl.getFmRequestno());
		requestdate.setValue(fmzl.getFmRequestdate());
		reqpublino.setValue(fmzl.getFmReqpublino());
		inventor.setValue(fmzl.getFmInventor());
		// �޸�
		List fjList = ghfileService.findByFxmIdandFType(fmzl.getFmId(), 7);
		if (fjList.size() == 0) {// �޸���
			rowFile.setVisible(false);
			downFileZip.setDisabled(true);
		} else {// �и���
			// ��ʼ������
			for (int i = 0; i < fjList.size(); i++) {
				UploadFJ ufj = new UploadFJ(false);
				try {
					ufj.ReadFJ(this.getDesktop(), (GhFile) fjList.get(i));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				fileModel.add(ufj);
			}
			upList.setModel(fileModel);
			rowFile.setVisible(true);
		}
    }
    
    public void onClick$submit(){}
  //������ظ���
	public void onClick$downFileZip(){
		DownloadFJ.ListModelListDownloadCommand(this.getDesktop(), Fmzl.getFmId(), fileModel);
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
	public void onClick$close() {
		initWindow();
	}

	public void onClick$back() {
		this.detach();
		Events.postEvent(Events.ON_CHANGE, this, null);
	}
}
