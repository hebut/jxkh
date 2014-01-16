package com.uniwin.asm.personal.ui.data.teacherinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iti.gh.entity.GhCg;
import org.iti.gh.entity.GhFile;
import org.iti.gh.entity.GhJslw;
import org.iti.gh.entity.GhJsxm;
import org.iti.gh.entity.GhRjzz;
import org.iti.gh.entity.GhYjfx;
import org.iti.gh.service.GhFileService;
import org.iti.gh.service.JslwService;
import org.iti.gh.service.RjzzService;
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
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

import com.uniwin.asm.personal.ui.data.teacherinfo.jyqk.SelectRjWindow;
import com.uniwin.asm.personal.ui.data.teacherinfo.kyqk.SelectcgWindow;
import com.uniwin.framework.common.fileuploadex.DownloadFJ;
import com.uniwin.framework.common.fileuploadex.UploadFJ;
import com.uniwin.framework.entity.WkTUser;

public class KyrjzzWindow extends FrameworkWindow{

	Textbox dengjino,dengjisj,rjno,menbers,firtime;
	Label rank,writer;
	Button submit,reset,close;
	YjfxListbox research;
	Textbox xmmc;
	Long kuid;
	GhRjzz rjzz;
	
	//暂存附件
	private Row rowFile;
	Listbox upList;
	ListModelList fileModel;
	RjzzService rjzzService;
	YjfxService yjfxService;
	GhFileService ghfileService;
	JslwService jslwService;
	WkTUser user;
	Button downFile,deUpload,upFile,downFileZip;
	@Override
	public void initShow() {
	
		
	}

	@Override
	public void initWindow() {
		menbers.addEventListener(Events.ON_CHANGE, new EventListener(){

			public void onEvent(Event arg0) throws Exception {
				List namelist=new ArrayList();
				String str=menbers.getValue().trim();
				if(str.contains("，")||str.contains(",")){
					throw new WrongValueException(menbers, "项目组人员填写错误，请选择顿号！");
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
		xmmc.addEventListener(Events.ON_CHANGE, new EventListener(){

			public void onEvent(Event arg0) throws Exception {
				initRjWindow(xmmc.getValue().trim(),rjno.getValue().trim());
			}
			
		});
		rjno.addEventListener(Events.ON_CHANGE, new EventListener(){

			public void onEvent(Event arg0) throws Exception {
				initRjWindow(xmmc.getValue().trim(),rjno.getValue().trim());
			}
			
		});
		// 研究方向列表
		research.initYjfzList(user.getKuId(), null);
		
		if(rjzz != null){
			if(rjzz.getRjName() != null){
				xmmc.setValue(rjzz.getRjName());xmmc.setDisabled(true);	
			}else{
				xmmc.setValue("");	
			}
			if(rjzz.getRjRegisno() != null){
				dengjino.setValue(rjzz.getRjRegisno());
			}else{
				dengjino.setValue("");
			}
			if(rjzz.getRjFirtime() != null){
				firtime.setValue(rjzz.getRjFirtime());
			}else {
				firtime.setValue("");
			}
			if(rjzz.getRjSoftno() != null){
				rjno.setValue(rjzz.getRjSoftno());
			}else {
				rjno.setValue("");
			}
			if(rjzz.getRjTime() != null){
				dengjisj.setValue(rjzz.getRjTime());
			}else {
				dengjisj.setValue("");
			}
			if(rjzz.getRjPerson() != null){
				menbers.setValue(rjzz.getRjPerson());
			}else{
				menbers.setValue("");
			}
			writer.setValue(rjzz.getUser().getKuName());
			
			GhJslw jslw=jslwService.findByKuidAndLwidAndType(user.getKuId(), rjzz.getRjId(), GhJslw.RJZZ);
			if(jslw!=null){
				if(jslw.getLwSelfs() != null){
					rank.setValue(jslw.getLwSelfs()+"");
//					rank.setDisabled(true);
				}else{
					List namelist=new ArrayList();
					String str=menbers.getValue().trim();
					if(str.contains("，")||str.contains(",")){
						throw new WrongValueException(menbers, "项目组人员填写错误，请选择半角逗号！");
					}else{
					while (str.contains("、")) {
							Label lb = new Label(str.substring(0,str.indexOf("、")));
							namelist.add(lb.getValue().trim());
							str = str.substring(str.indexOf("、") + 1, str.length());
						
					}
					namelist.add(str.trim());
					rank.setValue(namelist.indexOf(user.getKuName())+1+"");
//					rank.setDisabled(true);
				}
				}
				
				//研究方向
				research.initYjfzList(user.getKuId(), jslw.getYjId());
			}else{
				research.initYjfzList(user.getKuId(), null);
				List namelist=new ArrayList();
				String str=menbers.getValue().trim();
				if(str.contains("，")||str.contains(",")){
					throw new WrongValueException(menbers, "项目组人员填写错误，请选择顿号！");
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
			if(user.getKuId().intValue()!=rjzz.getKuId().intValue()){
				dengjino.setDisabled(true);
				firtime.setDisabled(true);
				rjno.setDisabled(true);
				dengjisj.setDisabled(true);
				menbers.setDisabled(true);
				//附件情况
				deUpload.setDisabled(true);
				upFile.setDisabled(true);
			}
//			if(rjzz.getAuditState()!=null&&rjzz.getAuditState().shortValue()==GhRjzz.AUDIT_YES){
//				  
//				submit.setDisabled(true);
//				  upFile.setDisabled(true);
//			}
		}else{
			xmmc.setValue("");
			dengjino.setValue("");
			firtime.setValue("");
			rjno.setValue("");
			dengjisj.setValue("");
			menbers.setValue("");
			rank.setValue("0");
			research.initYjfzList(user.getKuId(), null);
			writer.setValue(user.getKuName());
			deUpload.setDisabled(true);
			downFileZip.setDisabled(true);
			downFile.setDisabled(true);
			// 选择项目名称
//			if (xmmc.getModel().getSize() > 0) {
//				xmmc.addEventListener(Events.ON_SELECT, new EventListener() {
//
//					public void onEvent(Event arg0) throws Exception {
//						if(xmmc.getSelectedItem()!=null){
//						Object [] mc=(Object [])xmmc.getSelectedItem().getValue();
//						GhRjzz zz=(GhRjzz)rjzzService.get(GhRjzz.class, (Long)mc[1]);
//						dengjino.setValue(zz.getRjRegisno()!=null?zz.getRjRegisno():"");dengjino.setDisabled(true);
//						firtime.setValue(zz.getRjFirtime()!=null?zz.getRjFirtime():"");firtime.setDisabled(true);
//						rjno.setValue(zz.getRjSoftno()!=null?zz.getRjSoftno():"");rjno.setDisabled(true);
//						dengjisj.setValue(zz.getRjTime()!=null?zz.getRjTime():"");dengjisj.setDisabled(true);
//						menbers.setValue(zz.getRjPerson()!=null?zz.getRjPerson():"");menbers.setDisabled(true);
//						List namelist=new ArrayList();
//						String str=menbers.getValue().trim();
//						if(str.contains("，")){
//							throw new WrongValueException(menbers, "项目组人员填写错误，请选择顿号！");
//						}else{
//						while (str.contains("、")) {
//								Label lb = new Label(str.substring(0,str.indexOf("、")));
//								namelist.add(lb.getValue());
//								str = str.substring(str.indexOf("、") + 1, str.length());
//							
//						}
//						namelist.add(str);
//						rank.setValue(namelist.indexOf(user.getKuName())+1+"");
//						rank.setDisabled(true);
//						//附件情况
//						deUpload.setDisabled(true);
//						upFile.setDisabled(true);
//						List fjList = ghfileService.findByFxmIdandFType(zz.getRjId(), 6);
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
//									// TODO Auto-generated catch block
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
//					}
//				});
//			}
		}
		// 附件
		if (rjzz == null) {// 新添加
			rowFile.setVisible(false);
			
		} else {// 修改
			List fjList = ghfileService.findByFxmIdandFType(rjzz.getRjId(), 6);
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
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					fileModel.add(ufj);
				}
				upList.setModel(fileModel);
				rowFile.setVisible(true);
			}
		}
		//
	
	}
	
	public void onClick$submit() throws InterruptedException{
	
		//专著名称
		if(xmmc.getValue()==null||"".equals(xmmc.getValue().trim())){
			throw new WrongValueException(xmmc, "您还没有填写专著名称！");
		}
		//软著登记号
		if(rjno.getValue()==null||"".equals(rjno.getValue().trim())){
			throw new WrongValueException(firtime, "您还没有填写软著登记号！");
		}
		//登记号
		if(dengjino.getValue()==null||"".equals(dengjino.getValue().trim())){
			throw new WrongValueException(firtime, "您还没有填写登记号！");
		}
		//登记时间
		if(dengjisj.getValue()==null||"".equals(dengjisj.getValue().trim())){
			throw new WrongValueException(dengjisj, "您还没有填写时间，格式如2008/9/28、2008、2008/9！");
			}else{
			try{
				String str = dengjisj.getValue().trim();
				if(str.length() < 4){
					throw new WrongValueException(dengjisj, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
					
				}
				else if(str.length()==4||'/'==str.charAt(4)){
					String s[] = str.split("/");
					if(s.length==1||s.length==2||s.length==3){
						for(int i=0;i<s.length;i++){
							String si = s[i];
							Integer.parseInt(si);
						}
					}else{
						throw new WrongValueException(dengjisj, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
						
					}
				}else{
					throw new WrongValueException(dengjisj, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
					
				}
			}catch(NumberFormatException e){
				throw new WrongValueException(dengjisj, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
			}
		}
		//首次发表时间
		if(firtime.getValue()==null||"".equals(firtime.getValue().trim())){
			throw new WrongValueException(firtime, "您还没有填写时间，格式如2008/9/28、2008、2008/9！");
			}else{
			try{
				String str = dengjisj.getValue().trim();
				if(str.length() < 4){
					throw new WrongValueException(firtime, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
					
				}
				else if(str.length()==4||'/'==str.charAt(4)){
					String s[] = str.split("/");
					if(s.length==1||s.length==2||s.length==3){
						for(int i=0;i<s.length;i++){
							String si = s[i];
							Integer.parseInt(si);
						}
					}else{
						throw new WrongValueException(firtime, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
						
					}
				}else{
					throw new WrongValueException(firtime, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
					
				}
			}catch(NumberFormatException e){
				throw new WrongValueException(firtime, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
			}
		}
	
		//false表示是修改，true表示是新建
		boolean index = false,owner=false; 
		if(rjzz==null){//说明这是新建的一个项目
			rjzz = new GhRjzz();
			index = true;
			if(!dengjisj.isDisabled()){
				owner=true;
			}
		}
//		if(xmmc.getSelectedItem()==null){
//			//没有选择项目名称，该项目属于该用户
//			owner=true;
//		}else{
//			owner=false;
//		}
		
		if(index){
			if(owner){
				if(!menbers.getValue().trim().contains(user.getKuName())){
					Messagebox.show("项目组成员不包括您的姓名！", "警告", Messagebox.OK, Messagebox.EXCLAMATION);
					rjzz=null;
					return;
				}
				//创建新软件记录：（一）输入的项目名称，保存软件著作表和教师论文表，（二）选择他人创建的记录，只保存教师论文表
				if(rjzzService.CheckOnlyOne(null, xmmc.getValue().trim(), rjno.getValue().trim())){
					Messagebox.show("软件著作已存在！", "警告", Messagebox.OK, Messagebox.EXCLAMATION);
					rjzz=null;
					return;
				}
				rjzz.setRjName(xmmc.getValue());
				rjzz.setRjTime(dengjisj.getValue());
				rjzz.setRjFirtime(firtime.getValue());
				rjzz.setRjSoftno(rjno.getValue());
				rjzz.setRjRegisno(dengjino.getValue());
				rjzz.setRjPerson(menbers.getValue());
				rjzz.setKuId(kuid);
				rjzzService.save(rjzz);
				// 附件存储
				UploadFJ.ListModelListUploadCommand(fileModel, rjzz.getRjId());
				//
				GhJslw jslw=new GhJslw();
				jslw.setLwzlId(rjzz.getRjId());
				jslw.setJslwtype(GhJslw.RJZZ);
				jslw.setKuId(user.getKuId());
				jslw.setLwSelfs(Integer.parseInt(rank.getValue().trim()));
				jslw.setYjId(((GhYjfx)research.getSelectedItem().getValue()).getGyId());
				jslwService.save(jslw);
			}else{
				if(!menbers.getValue().trim().contains(user.getKuName())){
					Messagebox.show("项目组成员不包括您的姓名！", "警告", Messagebox.OK, Messagebox.EXCLAMATION);
					rjzz=null;
					return;
				}
				GhJslw jslw=new GhJslw();
				jslw.setLwzlId(rjzz.getRjId());
				jslw.setJslwtype(GhJslw.RJZZ);
				jslw.setKuId(user.getKuId());
				jslw.setLwSelfs(Integer.parseInt(rank.getValue().trim()));
				jslw.setYjId(((GhYjfx)research.getSelectedItem().getValue()).getGyId());
				jslwService.save(jslw);
			}
			Messagebox.show("保存成功！","提示",Messagebox.OK,Messagebox.INFORMATION);
		}else{
			//修改记录：（一）属于该用户创建的项目：更新软件著作表和教师论文表（二）不属于该用户创建的，只更新教师论文表
			if(user.getKuId().intValue()==rjzz.getKuId().intValue()){
				if(rjzzService.CheckOnlyOne(rjzz, xmmc.getValue().trim(), rjno.getValue().trim())){
					Messagebox.show("软件著作已存在！", "警告", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
				rjzz.setRjTime(dengjisj.getValue());
				rjzz.setRjFirtime(firtime.getValue());
				rjzz.setRjSoftno(rjno.getValue());
				rjzz.setRjRegisno(dengjino.getValue());
				rjzz.setRjPerson(menbers.getValue());
				rjzz.setAuditState(null);
				rjzz.setAuditUid(null);
				rjzz.setReason(null);
				rjzzService.update(rjzz);
				// 附件存储
				UploadFJ.ListModelListUploadCommand(fileModel, rjzz.getRjId());
				GhJslw jslw=jslwService.findByKuidAndLwidAndType(user.getKuId(), rjzz.getRjId(), GhJslw.RJZZ);
				if(jslw!=null){
					jslw.setLwSelfs(Integer.parseInt(rank.getValue().trim()));
					jslw.setYjId(((GhYjfx)research.getSelectedItem().getValue()).getGyId());
					jslwService.update(jslw);
				}else{
					jslw=new GhJslw();
					jslw.setLwzlId(rjzz.getRjId());
					jslw.setJslwtype(GhJslw.RJZZ);
					jslw.setKuId(user.getKuId());
					jslw.setLwSelfs(Integer.parseInt(rank.getValue().trim()));
					jslw.setYjId(((GhYjfx)research.getSelectedItem().getValue()).getGyId());
					jslwService.save(jslw);
				}
				Messagebox.show("保存成功！","提示",Messagebox.OK,Messagebox.INFORMATION);
				
			}else{
				if(!menbers.getValue().trim().contains(user.getKuName())){
					Messagebox.show("项目组成员不包括您的姓名！", "警告", Messagebox.OK, Messagebox.EXCLAMATION);
					rjzz=null;
					return;
				}
				GhJslw jslw=jslwService.findByKuidAndLwidAndType(user.getKuId(), rjzz.getRjId(), GhJslw.RJZZ);
				if(jslw!=null){
					jslw.setLwSelfs(Integer.parseInt(rank.getValue().trim()));
					jslw.setYjId(((GhYjfx)research.getSelectedItem().getValue()).getGyId());
					jslwService.update(jslw);
				}else{
					jslw=new GhJslw();
					jslw.setLwzlId(rjzz.getRjId());
					jslw.setJslwtype(GhJslw.RJZZ);
					jslw.setKuId(user.getKuId());
					jslw.setLwSelfs(Integer.parseInt(rank.getValue().trim()));
					jslw.setYjId(((GhYjfx)research.getSelectedItem().getValue()).getGyId());
					jslwService.save(jslw);
				}
			}
			Messagebox.show("保存成功！","提示",Messagebox.OK,Messagebox.INFORMATION);
		}
		Events.postEvent(Events.ON_CHANGE, this, null);
	}
	
	
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		fileModel = new ListModelList(new ArrayList());
		upList.setModel(fileModel);
//		xmmc.setModel(new ListModelList(rjzzService.findByKuidAndUname(user.getKuId(),user.getKuName())));
//		xmmc.setItemRenderer(new ComboitemRenderer(){
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
	public void initRjWindow(String mc,String softno){
		GhRjzz Rjzz=rjzzService.findBynameAndSoftno(mc, softno);
		if(Rjzz!=null){
			rjzz=Rjzz;
			dengjino.setValue(rjzz.getRjRegisno()!=null?rjzz.getRjRegisno():"");dengjino.setDisabled(true);
			firtime.setValue(rjzz.getRjFirtime()!=null?rjzz.getRjFirtime():"");firtime.setDisabled(true);
		//	rjno.setValue(rjzz.getRjSoftno()!=null?rjzz.getRjSoftno():"");rjno.setDisabled(true);
			dengjisj.setValue(rjzz.getRjTime()!=null?rjzz.getRjTime():"");dengjisj.setDisabled(true);
			menbers.setValue(rjzz.getRjPerson()!=null?rjzz.getRjPerson():"");menbers.setDisabled(true);
			List namelist=new ArrayList();
			String str=menbers.getValue().trim();
			if(str.contains("，")||str.contains(",")){
				throw new WrongValueException(menbers, "项目组人员填写错误，请选择顿号！");
			}else{
			while (str.contains("、")) {
					Label lb = new Label(str.substring(0,str.indexOf("、")));
					namelist.add(lb.getValue().trim());
					str = str.substring(str.indexOf("、") + 1, str.length());
				
			}
			namelist.add(str.trim());
			rank.setValue(namelist.indexOf(user.getKuName())+1+"");
			}
			writer.setValue(rjzz.getUser().getKuName());
		}
		else{
			rjzz=null;
			dengjino.setValue("");dengjino.setDisabled(false);
			firtime.setValue("");firtime.setDisabled(false);
//			rjno.setValue("");
			rjno.setDisabled(false);
			dengjisj.setValue("");dengjisj.setDisabled(false);
			menbers.setValue("");menbers.setDisabled(false);
			rank.setValue("0"); writer.setValue(user.getKuName());
		}
	}
	public void onClick$choice(){
		Map args=new HashMap();
    	args.put("user", user);
//    	args.put("lx", GhCg.CG_JY);
//    	args.put("type", GhJsxm.HJJY);
    	SelectRjWindow srw=(SelectRjWindow)Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/rjzz/selectrj.zul", null, args);
    	srw.initShow();
    	srw.doHighlighted();
    	srw.addEventListener(Events.ON_CHANGE, new EventListener(){

			public void onEvent(Event arg0) throws Exception {
				SelectRjWindow srw=(SelectRjWindow)arg0.getTarget();
				if(srw.getRjlist().getSelectedItem()!=null){
					GhRjzz  zz=(GhRjzz)srw.getRjlist().getSelectedItem().getValue();
					if(!zz.getRjPerson().contains(user.getKuName())){
						Messagebox.show("您不是项目组成员，请联系项目填写人申请加入！", "提示：", Messagebox.OK, Messagebox.EXCLAMATION);
					}
					else{
					rjzz= zz;
					initWindow();
//					dengjino.setValue(zz.getRjRegisno()!=null?zz.getRjRegisno():"");dengjino.setDisabled(true);
//					firtime.setValue(zz.getRjFirtime()!=null?zz.getRjFirtime():"");firtime.setDisabled(true);
//					rjno.setValue(zz.getRjSoftno()!=null?zz.getRjSoftno():"");rjno.setDisabled(true);
//					dengjisj.setValue(zz.getRjTime()!=null?zz.getRjTime():"");dengjisj.setDisabled(true);
//					menbers.setValue(zz.getRjPerson()!=null?zz.getRjPerson():"");menbers.setDisabled(true);
//					List namelist=new ArrayList();
//					String str=menbers.getValue().trim();
//					if(str.contains("，")){
//						throw new WrongValueException(menbers, "项目组人员填写错误，请选择顿号！");
//					}else{
//					while (str.contains("、")) {
//							Label lb = new Label(str.substring(0,str.indexOf("、")));
//							namelist.add(lb.getValue());
//							str = str.substring(str.indexOf("、") + 1, str.length());
//						
//					}
//					namelist.add(str);
//					rank.setValue(namelist.indexOf(user.getKuName())+1+"");
//					writer.setValue(rjzz.getUser().getKuName());
//					} 	 
					}
				srw.detach();
				}}
			});
	}
	public void onClick$upFile() throws InterruptedException {
		/*Object media = Fileupload.get();
		if (media == null) {
			return;
		}
		rowFile.setVisible(true);
		fileModel.add(media);*/
		UploadFJ ufj = new UploadFJ(true);
		Media media = ufj.Upload(this.getDesktop(),6, 10, "附件不能超过10MB",
				"软件著作附件------附件不能超过10MB");
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
			temp.DeleteFJ();
			fileModel.remove(it.getValue());
			rowFile.setVisible(false);
		} else if (fileModel.getSize() > 1) {
			temp.DeleteFJ();
			fileModel.remove(it.getValue());
		}
	}
	//打包下载附件
	public void onClick$downFileZip(){
		DownloadFJ.ListModelListDownloadCommand(this.getDesktop(), this.rjzz.getRjId(), fileModel);
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
	public void onClick$close(){
		this.detach();
	}
	
	public Long getKuid() {
		return kuid;
	}

	public void setKuid(Long kuid) {
		this.kuid = kuid;
	}
	public GhRjzz getRjzz() {
		return rjzz;
	}

	public void setRjzz(GhRjzz rjzz) {
		this.rjzz = rjzz;
	}

}
