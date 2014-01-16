package com.uniwin.asm.personal.ui.data.teacherinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iti.gh.entity.GhFile;
import org.iti.gh.entity.GhFmzl;
import org.iti.gh.entity.GhJslw;
import org.iti.gh.entity.GhYjfx;
import org.iti.gh.service.FmzlService;
import org.iti.gh.service.GhFileService;
import org.iti.gh.service.JslwService;
import org.iti.gh.service.YjfxService;
import org.iti.gh.ui.listbox.YjfxListbox;
import org.iti.xypt.ui.base.FrameworkWindow;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.api.Intbox;

import com.uniwin.asm.personal.ui.data.teacherinfo.jyqk.SelectJcWindow;
import com.uniwin.asm.personal.ui.data.teacherinfo.kyqk.SelectFmWindow;
import com.uniwin.framework.common.fileuploadex.DownloadFJ;
import com.uniwin.framework.common.fileuploadex.IsZipExists;
import com.uniwin.framework.common.fileuploadex.UploadFJ;
import com.uniwin.framework.entity.WkTUser;

public class FmzlqkWindow extends FrameworkWindow {

	Textbox  shijian, kanwu, country, requesino, requestdate, reqpublino,inventor;
	Textbox cgmc;
	Label rank,writer;
	Listbox types;
	YjfxListbox research;
	private Radiogroup status;
	GhFmzl fm;
	Long kuid;
	// 暂存附件
	private Row rowFile;
	Listbox upList;
	ListModelList fileModel;

	FmzlService fmzlService;
	YjfxService yjfxService;
	GhFileService ghfileService;
	JslwService jslwService;
	WkTUser user;
	Button downFile,deUpload,upFile,downFileZip,submit;

	@Override
	public void initShow() {

	}

	@SuppressWarnings("unchecked")
	@Override
	public void initWindow() {
		List fmtype = new ArrayList();
		List fx = new ArrayList();
		String[] Fmtype = { "-请选择-", "发明", "实用新型", "外观设计" };
		for (int i = 0; i < Fmtype.length; i++) {
			fmtype.add(Fmtype[i]);
		}
		types.setModel(new ListModelList(fmtype));
		// 研究方向列表
		research.initYjfzList(user.getKuId(), null);
		inventor.addEventListener(Events.ON_CHANGE, new EventListener(){

			public void onEvent(Event arg0) throws Exception {
				List namelist=new ArrayList();
				String str=inventor.getValue().trim();
				if(str.contains("，")||str.contains(",")){
					throw new WrongValueException(inventor, "项目组人员填写错误，请选择顿号！");
				}else{
				while (str.contains("、")) {
						Label lb = new Label(str.substring(0,str.indexOf("、")));
						namelist.add(lb.getValue().trim());
						str = str.substring(str.indexOf("、") + 1, str.length());
					
				}
				namelist.add(str.trim());
				rank.setValue(namelist.indexOf(user.getKuName())+1+"");
			}
			}
		});
		
		if (fm != null) {
			// 名称
			cgmc.setValue(fm.getFmMc());cgmc.setDisabled(true);
			writer.setValue(fm.getUser().getKuName());
			// 授权时间
			if (fm.getFmSj() != null) {
				shijian.setValue(fm.getFmSj());
			} else {
				shijian.setValue("");
			}

			// 发表的刊物
			if (fm.getFmSqh() != null) {
				kanwu.setValue(fm.getFmSqh());
			} else {
				kanwu.setValue("");
			}
			// 国别
			if (fm.getFmCountry() != null) {
				country.setValue(fm.getFmCountry());
			} else {
				country.setValue("");
			}
			// 申请号
			if (fm.getFmRequestno() != null) {
				requesino.setValue(fm.getFmRequestno());
			} else {
				requesino.setValue("");
			}
			if (fm.getFmRequestdate() != null) {
				requestdate.setValue(fm.getFmRequestdate());
			} else {
				requestdate.setValue("");
			}
			if (fm.getFmReqpublino() != null) {
				reqpublino.setValue(fm.getFmReqpublino());
			} else {
				reqpublino.setValue("");
			}
			if (fm.getFmInventor() != null) {
				inventor.setValue(fm.getFmInventor());
			} else {
				inventor.setValue("");
			}
			

			if (fm.getFmTypes() == null || fm.getFmTypes() == "") {
				types.setSelectedIndex(0);
			} else {
				types.setSelectedIndex(Integer.valueOf(fm.getFmTypes().trim()));
			}
			if (fm.getFmStatus().shortValue() == (GhFmzl.STA_NO.shortValue())) { // 是否有效专利
				status.setSelectedIndex(0);
			} else {
				status.setSelectedIndex(1);
			}
			GhJslw jslw=jslwService.findByKuidAndLwidAndType(user.getKuId(), fm.getFmId(), GhJslw.FMZL);
			if(jslw!=null){
				//个人排名
				if (jslw.getLwSelfs() != null) {
					rank.setValue(jslw.getLwSelfs()+"");
				} else {
					List namelist=new ArrayList();
					String str=inventor.getValue().trim();
					if(str.contains("，")||str.contains(",")){
						throw new WrongValueException(inventor, "项目组人员填写错误，请选择顿号！");
					}else{
					while (str.contains("、")) {
						Label lb = new Label(str.substring(0,str.indexOf("、")));
						namelist.add(lb.getValue().trim());
						str = str.substring(str.indexOf("、") + 1, str.length());
						
					}
					namelist.add(str.trim());
					rank.setValue(namelist.indexOf(user.getKuName())+1+"");
				}
				}
				// 研究方向
				research.initYjfzList(kuid, jslw.getYjId());
				
			}else{
				List namelist=new ArrayList();
				String str=inventor.getValue().trim();
				if(str.contains("，")||str.contains(",")){
					throw new WrongValueException(inventor, "项目组人员填写错误，请选择半角顿号！");
				}else{
				while (str.contains("、")) {
					Label lb = new Label(str.substring(0,str.indexOf("、")));
					namelist.add(lb.getValue().trim());
					str = str.substring(str.indexOf("、") + 1, str.length());
					
				}
				namelist.add(str.trim());
				rank.setValue(namelist.indexOf(user.getKuName())+1+"");
			}
				research.setSelectedIndex(0);
			}
			if(user.getKuId().intValue()!=fm.getKuId().intValue()){
				shijian.setDisabled(true); kanwu.setDisabled(true); country.setDisabled(true);
				requesino.setDisabled(true);requestdate.setDisabled(true); reqpublino.setDisabled(true);
				inventor.setDisabled(true);types.setDisabled(true);
				//附件情况
				deUpload.setDisabled(true);
				upFile.setDisabled(true);
			} 
//			if(fm.getAuditState()!=null&&fm.getAuditState().shortValue()==GhFmzl.AUDIT_YES){
//				 submit.setDisabled(true);
//		    	 upFile.setDisabled(true);
//			}
		} else {
			cgmc.addEventListener(Events.ON_CHANGE, new EventListener(){

				public void onEvent(Event arg0) throws Exception {
					initFmWindow(cgmc.getValue().trim());
				}
			});
			cgmc.setValue("");
			shijian.setValue("");
			kanwu.setValue("");
			country.setValue("");
			requesino.setValue("");
			requestdate.setValue("");
			reqpublino.setValue("");
			inventor.setValue("");
			rank.setValue(null);
			deUpload.setDisabled(true);
			downFileZip.setDisabled(true);
			downFile.setDisabled(true);
			writer.setValue(user.getKuName());
//			if(cgmc.getModel().getSize()>0){
//			cgmc.addEventListener(Events.ON_SELECT, new EventListener() {
//
//					public void onEvent(Event arg0) throws Exception {
//						if(cgmc.getSelectedItem()!=null){
//						Object[] mc = (Object[]) cgmc.getSelectedItem()
//								.getValue();
//						GhFmzl fm = (GhFmzl) fmzlService.get(GhFmzl.class,(Long) mc[1]);
//						shijian.setDisabled(true);
//						kanwu.setDisabled(true);
//						country.setDisabled(true);
//						requesino.setDisabled(true);
//						requestdate.setDisabled(true);
//						reqpublino.setDisabled(true);
//						inventor.setDisabled(true);
//						types.setDisabled(true);
//						// 授权时间
//						if (fm.getFmSj() != null) {
//							shijian.setValue(fm.getFmSj());
//						} else {
//							shijian.setValue("");
//						}
//
//						// 发表的刊物
//						if (fm.getFmSqh() != null) {
//							kanwu.setValue(fm.getFmSqh());
//						} else {
//							kanwu.setValue("");
//						}
//						// 国别
//						if (fm.getFmCountry() != null) {
//							country.setValue(fm.getFmCountry());
//						} else {
//							country.setValue("");
//						}
//						// 申请号
//						if (fm.getFmRequestno() != null) {
//							requesino.setValue(fm.getFmRequestno());
//						} else {
//							requesino.setValue("");
//						}
//						if (fm.getFmRequestdate() != null) {
//							requestdate.setValue(fm.getFmRequestdate());
//						} else {
//							requestdate.setValue("");
//						}
//						if (fm.getFmReqpublino() != null) {
//							reqpublino.setValue(fm.getFmReqpublino());
//						} else {
//							reqpublino.setValue("");
//						}
//						if (fm.getFmInventor() != null) {
//							inventor.setValue(fm.getFmInventor());
//						} else {
//							inventor.setValue("");
//						}
//						if (fm.getFmTypes() == null || fm.getFmTypes() == "") {
//							types.setSelectedIndex(0);
//						} else {
//							types.setSelectedIndex(Integer.valueOf(fm.getFmTypes().trim()));
//						}
//						if (fm.getFmStatus().shortValue() == (GhFmzl.STA_NO.shortValue())) { // 是否有效专利
//							status.setSelectedIndex(0);
//						} else {
//							status.setSelectedIndex(1);
//						}
//						List namelist = new ArrayList();
//						String str = inventor.getValue().trim();
//						if (str.contains("，")) {
//							throw new WrongValueException(inventor,
//									"项目组人员填写错误，请选择半角逗号！");
//						} else {
//							while (str.contains(",")) {
//								Label lb = new Label(str.substring(0, str.indexOf(",")));
//								namelist.add(lb.getValue());
//								str = str.substring(str.indexOf(",") + 1, str.length());
//
//							}
//							namelist.add(str);
//							rank.setValue(namelist.indexOf(user.getKuName()) + 1);
//							rank.setDisabled(true);
//						}
//						deUpload.setDisabled(true);upFile.setDisabled(true);
//						List fjList = ghfileService.findByFxmIdandFType(fm.getFmId(), 7);
//						if (fjList.size() == 0) {// 无附件
//							rowFile.setVisible(false);
//							downFileZip.setDisabled(true);
//						} else {// 有附件
//							// 初始化附件
//							downFileZip.setDisabled(false);
//							for (int i = 0; i < fjList.size(); i++) {
//								UploadFJ ufj = new UploadFJ(false);
//								try {
//									ufj.ReadFJ(getDesktop(), (GhFile) fjList.get(i));
//								} catch (InterruptedException e) {
//								 
//									e.printStackTrace();
//								}
//								fileModel.add(ufj);
//							}
//							upList.setModel(fileModel);
//							rowFile.setVisible(true);
//						}
//
//					}
//					}
//				});
//			}
		}
		// 附件
		if (fm == null) {// 新添加
			rowFile.setVisible(false);
		} else {// 修改
			List fjList = ghfileService.findByFxmIdandFType(fm.getFmId(), 7);
			if (fjList.size() == 0) {// 无附件
				rowFile.setVisible(false);
				downFileZip.setDisabled(true);
			} else {// 有附件
				// 初始化附件
				downFileZip.setDisabled(false);
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
	}

	public void onClick$submit() throws InterruptedException {
		// 专利名称
		if (cgmc.getValue() == null || "".equals(cgmc.getValue().trim())) {
			throw new WrongValueException(cgmc, "您还没有填写名称！");
		}
		// 授权时间
		if (shijian.getValue() == null || "".equals(shijian.getValue().trim())) {
			throw new WrongValueException(shijian,
					"您还没有填写时间，格式如2008/9/28、2008、2008/9！");
		} else {
			try {
				String str = shijian.getValue().trim();
				if (str.length() < 4) {
					throw new WrongValueException(shijian,
							"您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");

				} else if (str.length() == 4 || '/' == str.charAt(4)) {
					String s[] = str.split("/");
					if (s.length == 1 || s.length == 2 || s.length == 3) {
						for (int i = 0; i < s.length; i++) {
							String si = s[i];
							Integer.parseInt(si);
						}
					} else {
						throw new WrongValueException(shijian,
								"您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");

					}
				} else {
					throw new WrongValueException(shijian,
							"您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");

				}
			} catch (NumberFormatException e) {
				throw new WrongValueException(shijian,
						"您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
			}
		}

		// 专利授权号
		if (kanwu.getValue() == null || "".equals(kanwu.getValue().trim())) {
			throw new WrongValueException(kanwu, "您还没有填写专利授权号！");
		}
		boolean index = false,owner=false;  // false表示是修改，true表示是新建
		if (fm == null) {// 说明这是新建的一个项目
			fm = new GhFmzl();
			index = true;
		}
		if(!shijian.isDisabled()){
			//没有选择项目名称，该项目属于该用户
			owner=true;
		}else{
			owner=false;
		}
		//
		if (index) {
			if(owner){
				if(!inventor.getValue().contains(user.getKuName())){
					Messagebox.show("发明人不包括您的姓名，不可以添加！","警告",Messagebox.OK,Messagebox.EXCLAMATION);
					fm=null;
					return;
				}
				if(fmzlService.CheckOnlyOne(null, cgmc.getValue().trim(), null)){
					Messagebox.show("您提交的发明信息已存在，不可以重复添加！","警告",Messagebox.OK,Messagebox.EXCLAMATION);
					fm=null;
					return;
				}
				fm.setFmMc(cgmc.getValue());
				fm.setFmSj(shijian.getValue());
				fm.setFmSqh(kanwu.getValue());
				fm.setFmCountry(country.getValue());
				fm.setFmRequestno(requesino.getValue());
				fm.setFmRequestdate(requestdate.getValue());
				fm.setFmReqpublino(reqpublino.getValue());
				fm.setFmInventor(inventor.getValue());
//				fm.setFmRank(rank.getValue());
				fm.setFmTypes(String.valueOf(types.getSelectedIndex()));
//				fm.setYjId(String.valueOf(research.getSelectedIndex()));
				if (status.getSelectedIndex() == 0) {
					fm.setFmStatus(GhFmzl.STA_NO.shortValue());// 无效专利
				} else {
					fm.setFmStatus(GhFmzl.STA_YES.shortValue());// 有效专利
				}
				fm.setKuId(kuid);
				fmzlService.save(fm);
				// 附件存储
				UploadFJ.ListModelListUploadCommand(fileModel, fm.getFmId());
				GhJslw jslw=new GhJslw();
				jslw.setLwzlId(fm.getFmId());
				jslw.setJslwtype(GhJslw.FMZL);
				jslw.setKuId(user.getKuId());
				jslw.setLwSelfs(Integer.parseInt(rank.getValue()));
				jslw.setYjId(((GhYjfx)research.getSelectedItem().getValue()).getGyId());
				jslwService.save(jslw);
				Messagebox.show("保存成功！","提示",Messagebox.OK,Messagebox.INFORMATION);
			}else{
				if(!inventor.getValue().contains(user.getKuName())){
					Messagebox.show("发明人不包括您的姓名，不可以添加！","警告",Messagebox.OK,Messagebox.EXCLAMATION);
					fm=null;
					return;
				}
				GhJslw jslw=new GhJslw();
				jslw.setLwzlId(fm.getFmId());
				jslw.setJslwtype(GhJslw.FMZL);
				jslw.setKuId(user.getKuId());
				jslw.setLwSelfs(Integer.parseInt(rank.getValue()));
				jslw.setYjId(((GhYjfx)research.getSelectedItem().getValue()).getGyId());
				jslwService.save(jslw);
				Messagebox.show("保存成功！","提示",Messagebox.OK,Messagebox.INFORMATION);
			}
			Events.postEvent(Events.ON_CHANGE, this, null);
			
		} else {
			if(fm.getKuId()!=null&&!fm.getKuId().equals("")){
		
				if(user.getKuId()!=fm.getKuId()){//只更新教师论文表
					GhJslw jslw=jslwService.findByKuidAndLwidAndType(user.getKuId(), fm.getFmId(), GhJslw.FMZL);
					if(jslw!=null){
						jslw.setLwSelfs(Integer.parseInt(rank.getValue()));
						jslw.setYjId(((GhYjfx)research.getSelectedItem().getValue()).getGyId());
						jslwService.update(jslw);
					}else{
						jslw=new GhJslw();
						jslw.setLwzlId(fm.getFmId());
						jslw.setJslwtype(GhJslw.FMZL);
						jslw.setKuId(user.getKuId());
						jslw.setLwSelfs(Integer.parseInt(rank.getValue()));
						jslw.setYjId(((GhYjfx)research.getSelectedItem().getValue()).getGyId());
						jslwService.save(jslw);
					}
					Messagebox.show("保存成功！","提示",Messagebox.OK,Messagebox.INFORMATION);
				}else{
					if(!inventor.getValue().contains(user.getKuName())){
						Messagebox.show("发明人不包括您的姓名，不可以添加！","警告",Messagebox.OK,Messagebox.EXCLAMATION);
						return;
					}
	//				同时更新发明专利表和教师论文表
					if(fmzlService.CheckOnlyOne(fm, cgmc.getValue().trim(), null)){
						Messagebox.show("您提交的发明信息已存在，更新信息不可以保存！","警告",Messagebox.OK,Messagebox.EXCLAMATION);
						return;
					}
					fm.setFmSj(shijian.getValue());
					fm.setFmSqh(kanwu.getValue());
					fm.setFmCountry(country.getValue());
					fm.setFmRequestno(requesino.getValue());
					fm.setFmRequestdate(requestdate.getValue());
					fm.setFmReqpublino(reqpublino.getValue());
					fm.setFmInventor(inventor.getValue());
					fm.setFmTypes(String.valueOf(types.getSelectedIndex()));
					if (status.getSelectedIndex() == 0) {
						fm.setFmStatus(GhFmzl.STA_NO.shortValue());// 无效专利
					} else {
						fm.setFmStatus(GhFmzl.STA_YES.shortValue());// 有效专利
					}
					fm.setAuditState(null);
					fm.setAuditUid(null);
					fm.setReason(null);
					fmzlService.update(fm);
					List jslwlist=jslwService.findByLwidAndType(fm.getFmId(), GhJslw.FMZL);
					if(jslwlist!=null&&jslwlist.size()>0){
						for(int i=0;i<jslwlist.size();i++){
							GhJslw jslw=(GhJslw)jslwlist.get(i);
							jslwService.delete(jslw);
						}
					}
					// 附件存储
					UploadFJ.ListModelListUploadCommand(fileModel, fm.getFmId());
					GhJslw jslw=jslwService.findByKuidAndLwidAndType(user.getKuId(), fm.getFmId(), GhJslw.FMZL);
					if(jslw!=null){
						jslw.setLwSelfs(Integer.parseInt(rank.getValue()));
						jslw.setYjId(((GhYjfx)research.getSelectedItem().getValue()).getGyId());
						jslwService.update(jslw);
					}else{
						jslw=new GhJslw();
						jslw.setLwzlId(fm.getFmId());
						jslw.setJslwtype(GhJslw.FMZL);
						jslw.setKuId(user.getKuId());
						jslw.setLwSelfs(Integer.parseInt(rank.getValue()));
						jslw.setYjId(((GhYjfx)research.getSelectedItem().getValue()).getGyId());
						jslwService.save(jslw);
					}
					Messagebox.show("保存成功！","提示",Messagebox.OK,Messagebox.INFORMATION);
				}
				Events.postEvent(Events.ON_CHANGE, this, null);
			}
		}
	}

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		fileModel = new ListModelList(new ArrayList());
		upList.setModel(fileModel);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
//		cgmc.setModel(new ListModelList(fmzlService.findByKuidAndUname(user.getKuId(),user.getKuName())));
//		cgmc.setItemRenderer(new ComboitemRenderer(){
//
//			public void render(Comboitem arg0, Object arg1) throws Exception {
//				Object[] name=(Object[])arg1;
//				arg0.setWidth("200px");
//				arg0.setValue(name);
//				arg0.setLabel((String)name[0]);
//			}
//			
//		});
	}
	public void initFmWindow(String fmname){
		GhFmzl Fmzl=fmzlService.finByFmmc(fmname);
		if(Fmzl!=null){
			fm=Fmzl;
			cgmc.setDisabled(true);
			shijian.setDisabled(true);
			kanwu.setDisabled(true);
			country.setDisabled(true);
			requesino.setDisabled(true);
			requestdate.setDisabled(true);
			reqpublino.setDisabled(true);
			inventor.setDisabled(true);
			types.setDisabled(true);
			// 授权时间
			if (fm.getFmSj() != null) {
				shijian.setValue(fm.getFmSj());
			} else {
				shijian.setValue("");
			}

			// 发表的刊物
			if (fm.getFmSqh() != null) {
				kanwu.setValue(fm.getFmSqh());
			} else {
				kanwu.setValue("");
			}
			// 国别
			if (fm.getFmCountry() != null) {
				country.setValue(fm.getFmCountry());
			} else {
				country.setValue("");
			}
			// 申请号
			if (fm.getFmRequestno() != null) {
				requesino.setValue(fm.getFmRequestno());
			} else {
				requesino.setValue("");
			}
			if (fm.getFmRequestdate() != null) {
				requestdate.setValue(fm.getFmRequestdate());
			} else {
				requestdate.setValue("");
			}
			if (fm.getFmReqpublino() != null) {
				reqpublino.setValue(fm.getFmReqpublino());
			} else {
				reqpublino.setValue("");
			}
			if (fm.getFmInventor() != null) {
				inventor.setValue(fm.getFmInventor());
			} else {
				inventor.setValue("");
			}
			if (fm.getFmTypes() == null || fm.getFmTypes() == "") {
				types.setSelectedIndex(0);types.setDisabled(true);
			} else {
				types.setSelectedIndex(Integer.valueOf(fm.getFmTypes().trim()));
			}
			if (fm.getFmStatus().shortValue() == (GhFmzl.STA_NO.shortValue())) { // 是否有效专利
				status.setSelectedIndex(0);
			} else {
				status.setSelectedIndex(1);
			}
			List namelist = new ArrayList();
			String str = inventor.getValue().trim();
			if (str.contains("，")||str.contains(",")) {
				throw new WrongValueException(inventor,
						"项目组人员填写错误，请选择顿号！");
			} else {
				while (str.contains("、")) {
					Label lb = new Label(str.substring(0, str.indexOf("、")));
					namelist.add(lb.getValue().trim());
					str = str.substring(str.indexOf("、") + 1, str.length());

				}
				namelist.add(str.trim());
				rank.setValue(namelist.indexOf(user.getKuName()) + 1+"");
			}
			
		}else{
			fm=null;
//			cgmc.setValue("");
			/*shijian.setValue("");shijian.setDisabled(false);
			kanwu.setValue("");kanwu.setDisabled(false);
			country.setValue("");country.setDisabled(false);
			requesino.setValue("");requesino.setDisabled(false);
			requestdate.setValue("");requestdate.setDisabled(false);
			reqpublino.setValue("");reqpublino.setDisabled(false);
			inventor.setValue("");inventor.setDisabled(false);
			types.setSelectedIndex(0);types.setDisabled(false);
			rank.setValue(null);
			deUpload.setDisabled(true);
			downFileZip.setDisabled(true);
			downFile.setDisabled(true);*/
 
		}
	}
	
	public void onClick$choice(){
		Map arg=new HashMap();
		arg.put("user", user);
		SelectFmWindow sfw=(SelectFmWindow)Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/fmzl/selectfm.zul", null, arg);
		sfw.doHighlighted();
		sfw.initWindow();
		sfw.addEventListener(Events.ON_CHANGE,  new EventListener(){

			public void onEvent(Event arg0) throws Exception {
				SelectFmWindow sfw=(SelectFmWindow)arg0.getTarget();
				if(sfw.getXmlist().getSelectedItem()!=null){
					GhFmzl fmzl=(GhFmzl)sfw.getXmlist().getSelectedItem().getValue();
					if(!fmzl.getFmInventor().contains(user.getKuName())){
						Messagebox.show("您不是项目组成员，请联系项目填写人申请加入！", "提示：", Messagebox.OK, Messagebox.EXCLAMATION);
					}
					else{
					fm=fmzl;
					cgmc.setValue(fm.getFmMc());
					cgmc.setDisabled(true);
					shijian.setDisabled(true);
					kanwu.setDisabled(true);
					country.setDisabled(true);
					requesino.setDisabled(true);
					requestdate.setDisabled(true);
					reqpublino.setDisabled(true);
					inventor.setDisabled(true);
					types.setDisabled(true);
					// 授权时间
					if (fm.getFmSj() != null) {
						shijian.setValue(fm.getFmSj());
					} else {
						shijian.setValue("");
					}

					// 发表的刊物
					if (fm.getFmSqh() != null) {
						kanwu.setValue(fm.getFmSqh());
					} else {
						kanwu.setValue("");
					}
					// 国别
					if (fm.getFmCountry() != null) {
						country.setValue(fm.getFmCountry());
					} else {
						country.setValue("");
					}
					// 申请号
					if (fm.getFmRequestno() != null) {
						requesino.setValue(fm.getFmRequestno());
					} else {
						requesino.setValue("");
					}
					if (fm.getFmRequestdate() != null) {
						requestdate.setValue(fm.getFmRequestdate());
					} else {
						requestdate.setValue("");
					}
					if (fm.getFmReqpublino() != null) {
						reqpublino.setValue(fm.getFmReqpublino());
					} else {
						reqpublino.setValue("");
					}
					if (fm.getFmInventor() != null) {
						inventor.setValue(fm.getFmInventor());
					} else {
						inventor.setValue("");
					}
					if (fm.getFmTypes() == null || fm.getFmTypes() == "") {
						types.setSelectedIndex(0);
					} else {
						types.setSelectedIndex(Integer.valueOf(fm.getFmTypes().trim()));
					}
					if (fm.getFmStatus().shortValue() == (GhFmzl.STA_NO.shortValue())) { // 是否有效专利
						status.setSelectedIndex(0);
					} else {
						status.setSelectedIndex(1);
					}
					List namelist = new ArrayList();
					String str = inventor.getValue().trim();
					if (str.contains("，")||str.contains(",")) {
						throw new WrongValueException(inventor,
								"项目组人员填写错误，请选择顿号！");
					} else {
						while (str.contains("、")) {
							Label lb = new Label(str.substring(0, str.indexOf("、")));
							namelist.add(lb.getValue().trim());
							str = str.substring(str.indexOf("、") + 1, str.length());

						}
						namelist.add(str.trim());
						rank.setValue(namelist.indexOf(user.getKuName()) + 1+"");
					}
					sfw.detach();
				}
				}}});
	}
	// 2010-1104
	// magicube

	public void onClick$upFile() throws InterruptedException {
		UploadFJ ufj = new UploadFJ(true);
		Media media = ufj.Upload(this.getDesktop(), 7, 10, "附件不能超过10MB",
				"获授权发明专利附件------附件不能超过10MB");
		/* Object media = Fileupload.get(); */
		if (media == null) {
			return;
		}
		rowFile.setVisible(true);
		fileModel.add(ufj);
	}

	/**
	 * <li>删除上传的文件，重新选择
	 * 
	 * @author bobo 2010-4-11
	 */
	public void onClick$deUpload() {
		Listitem it = upList.getSelectedItem();
		if (it == null) {
			if (fileModel.getSize() > 0) {
				it = upList.getItemAtIndex(0);
			}
		}
		//
		UploadFJ temp = (UploadFJ) it.getValue();
		//
		if (fileModel.getSize() == 1) {
			//
			temp.DeleteFJ();
			//
			fileModel.remove(temp);
			rowFile.setVisible(false);
		} else if (fileModel.getSize() > 1) {
			//
			temp.DeleteFJ();
			//
			fileModel.remove(temp);
		}
	}
	
	
	//打包下载附件
	public void onClick$downFileZip(){
		
		/*//测试 word
		DocumentImpl doc=new DocumentImpl();
		doc.CreateDocument(1l, null, this.getDesktop().getWebApp().getRealPath("/upload/word/aaa.docx"),this.getDesktop().getWebApp().getRealPath("/upload/"), kuid);
		//
*/		
		
		
		DownloadFJ.ListModelListDownloadCommand(this.getDesktop(), this.fm.getFmId(), fileModel);
	}
	//单个文件下载
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
		this.detach();
	}

	public Long getKuid() {
		return kuid;
	}

	public void setKuid(Long kuid) {
		this.kuid = kuid;
	}

	public GhFmzl getFm() {
		return fm;
	}

	public void setFm(GhFmzl fm) {
		this.fm = fm;
	}

}
