package org.iti.gh.shxkjs.kyqk;

import java.util.ArrayList;
import java.util.List;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.entity.GhCg;
import org.iti.gh.entity.GhFile;
import org.iti.gh.entity.GhXm;
import org.iti.gh.service.CgService;
import org.iti.gh.service.GhFileService;
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

import com.uniwin.framework.common.fileuploadex.DownloadFJ;
import com.uniwin.framework.common.fileuploadex.UploadFJ;
import com.uniwin.framework.entity.WkTUser;

public class ShkycgWindow extends BaseWindow {

	Listbox hjcglist;
	Grid auditcg;
	CgService cgService;
	Long kdid = (Long) Executions.getCurrent().getArg().get("kdid");
	Short type = (Short) Executions.getCurrent().getArg().get("type");
	GhCg kycg;
	Label number, cgmc, ly, hjmc, dengji, shijian, prizenum, prizedep,
			prizepersons;
	Textbox reason;
	Radiogroup audit;
	WkTUser user;
	Row rowFile;
	GhFileService ghfileService;
	ListModelList fileModel = new ListModelList();
	Listbox upList, jb;
	Button downFileZip;

	@Override
	public void initShow() {
		hjcglist.setItemRenderer(new ListitemRenderer() {

			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhCg cg = (GhCg) arg1;
				arg0.setValue(cg);
				Listcell c0 = new Listcell(arg0.getIndex() + 1 + "");
				Listcell c1 = new Listcell(cg.getKyMc());
				// Listcell c2=new Listcell(cg.getKyLy()!=null?cg.getKyLy():"");
				Listcell c3 = new Listcell(cg.getKySj());
				Listcell c4 = new Listcell(cg.getKyHjmc());
				Listcell c5 = new Listcell(cg.getKyDj());
				Listcell c6 = new Listcell(cg.getKyPrizeper());
				Listcell c7 = new Listcell(cg.getUser().getKuName());
				Listcell c8 = new Listcell();
				InnerButton ib = new InnerButton("审核");
				ib.addEventListener(Events.ON_CLICK, new EventListener() {

					public void onEvent(Event arg0) throws Exception {
						kycg = cg;
						hjcglist.setVisible(false);
						auditcg.setVisible(true);
						initAuditcg(cg);
					}

				});
				c8.appendChild(ib);
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				// arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
				arg0.appendChild(c5);
				arg0.appendChild(c6);
				arg0.appendChild(c7);
				arg0.appendChild(c8);

			}

		});
	}

	@Override
	public void initWindow() {
		// user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		// hjcglist.setVisible(true);
		// auditcg.setVisible(false);
		// reason.setValue("");
		// audit.setSelectedIndex(0);
		// String[] jbn={"-请选择-","国家级","省部级","其他"};
		// List jblist=new ArrayList();
		// for(int i=0;i<jbn.length;i++){
		// jblist.add(jbn[i]);
		// }
		// jb.setModel(new ListModelList(jblist));
		// if(kdid==null){
		// kdid=getXyUserRole().getKdId();
		// }
		// List cglist=cgService.findByKdidAndLxAndState(kdid,type, null);
		// if(cglist!=null&&cglist.size()>0){
		// hjcglist.setModel(new ListModelList(cglist));
		// }else{
		// hjcglist.setModel(new ListModelList());
		// }
		//
	}

	public void initAuditcg(GhCg cg) {
		fileModel.clear();
		// 处理附件
		List fjList = new ArrayList();
		if (type == GhCg.CG_KY) {
			fjList = ghfileService.findByFxmIdandFType(cg.getKyId(), 2);
		} else if (type == GhCg.CG_JY) {
			fjList = ghfileService.findByFxmIdandFType(cg.getKyId(), 11);
		}
		if (fjList == null || fjList != null && fjList.size() == 0) {// 无附件
			rowFile.setVisible(false);
			downFileZip.setDisabled(true);
		} else {// 有附件
			downFileZip.setDisabled(false);
			// 初始化附件
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
		// 初始化各个属性
		number.setValue(cg.getKyNumber());
		cgmc.setValue(cg.getKyMc());
		ly.setValue(cg.getKyLy());
		hjmc.setValue(cg.getKyHjmc());
		dengji.setValue(cg.getKyDj());
		shijian.setValue(cg.getKySj());
		prizenum.setValue(cg.getKyPrizenum());
		prizedep.setValue(cg.getKyPrizedep());
		prizepersons.setValue(cg.getKyPrizeper());
		if (cg.getKyJb() != null && cg.getKyJb().shortValue() == GhCg.CG_GJ) {
			jb.setSelectedIndex(1);
		} else if (cg.getKyJb() != null
				&& cg.getKyJb().shortValue() == GhCg.CG_SB) {
			jb.setSelectedIndex(2);
		} else if (cg.getKyJb() != null
				&& cg.getKyJb().shortValue() == GhCg.CG_QT) {
			jb.setSelectedIndex(3);
		} else {
			jb.setSelectedIndex(0);
		}
		jb.setDisabled(true);

	}

	// 打包下载附件
	public void onClick$downFileZip() {
		DownloadFJ.ListModelListDownloadCommand(this.getDesktop(),
				kycg.getKyId(), fileModel);
	}

	// 单个文件下载
	public void onClick$downFile() {
		Listitem it = upList.getSelectedItem();
		if (it == null) {
			if (fileModel.getSize() > 0) {
				it = upList.getItemAtIndex(0);
			}
		}
		UploadFJ temp = (UploadFJ) it.getValue();
		DownloadFJ.DownloadCommand(temp);
	}

	public void onClick$submit() {
	}

	public void onClick$close() {
		initWindow();
	}

	public void onClick$back() {
		this.detach();
		Events.postEvent(Events.ON_CHANGE, this, null);
	}
}
