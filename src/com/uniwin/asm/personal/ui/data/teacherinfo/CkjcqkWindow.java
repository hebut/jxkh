package com.uniwin.asm.personal.ui.data.teacherinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iti.gh.entity.GhFile;
import org.iti.gh.entity.GhJsxm;
import org.iti.gh.entity.GhJszz;
import org.iti.gh.entity.GhXm;
import org.iti.gh.entity.GhZz;
import org.iti.gh.service.GhFileService;
import org.iti.gh.service.JszzService;
import org.iti.gh.service.LwzlService;
import org.iti.gh.service.ZzService;
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
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

import com.uniwin.asm.personal.ui.data.teacherinfo.jyqk.SelectJcWindow;
import com.uniwin.framework.common.fileuploadex.DownloadFJ;
import com.uniwin.framework.common.fileuploadex.UploadFJ;
import com.uniwin.framework.entity.WkTUser;

public class CkjcqkWindow extends FrameworkWindow{

	Label first,second,more,shuming,writer;
	Textbox kanwu,publitime,editionno,isbn,nature,remark;
//	Textbox namesort;
	Textbox zb,fzb,bz;
	Intbox words;
	Listbox worktype,subjetype;
	GhZz jc;
	Long kuid;
	Textbox cgmc;
	
	//暂存附件
	private Row rowFile;
	Listbox upList;
	ListModelList fileModel;
	GhFileService ghfileService;
	JszzService jszzService;
	ZzService zzService;
	WkTUser user;
	Button deUpload,upFile,downFileZip,submit;
	final KyxszzqkWindow kw=new KyxszzqkWindow();
	@Override
	public void initShow() {
		
	}

	@Override
	public void initWindow() {
		List subtype = new ArrayList();
		List subworktype = new ArrayList();
		String[] Subtype = {"-请选择-","自然科学","社会科学","其他"};
		for(int i = 0;i < Subtype.length; i++){
			subtype.add(Subtype[i]);
		}
		String[] Subworktype = {"-请选择-","独著","合著","专著","编著","教材","参考书或工具书" ,"论文集","译著","调查报告","其他"};
		for(int i = 0;i < Subworktype.length; i++){
			subworktype.add(Subworktype[i]);
		}
		//学科分类
		subjetype.setModel(new ListModelList(subtype));
		worktype.setModel(new ListModelList(subworktype));
		
//		cgmc.addEventListener(Events.ON_CHANGE, new EventListener(){
//
//			public void onEvent(Event arg0) throws Exception {
//				initJcwindow(cgmc.getValue().trim(),zb.getValue().trim(),GhZz.JC);
//			}
//			
//		});
//		//署名情况
//		 zb.addEventListener(Events.ON_CHANGE, new EventListener(){
//
//				public void onEvent(Event arg0) throws Exception {
//					initJcwindow(cgmc.getValue().trim(),zb.getValue().trim(),GhZz.JC);
//					int i=kw.Shuming(user.getKuName(),zb.getValue(),fzb.getValue(),bz.getValue());
//					switch(i){
//					case 0:shuming.setValue("");break;
//					case 1:shuming.setValue(first.getValue());break;
//					case 2:shuming.setValue(second.getValue());break;
//					case 3:shuming.setValue(more.getValue());break;
//					}
//					
//				}
//		    	 
//		     });
		 fzb.addEventListener(Events.ON_CHANGE, new EventListener(){

				public void onEvent(Event arg0) throws Exception {
					int i=kw.Shuming(user.getKuName(),zb.getValue(),fzb.getValue(),bz.getValue());
					switch(i){
					case 0:shuming.setValue("");break;
					case 1:shuming.setValue(first.getValue());break;
					case 2:shuming.setValue(second.getValue());break;
					case 3:shuming.setValue(more.getValue());break;
					}
					
				}
		    	 
		     });
		 bz.addEventListener(Events.ON_CHANGE, new EventListener(){

				public void onEvent(Event arg0) throws Exception {
					
					int i=kw.Shuming(user.getKuName(),zb.getValue(),fzb.getValue(),bz.getValue());
					switch(i){
					case 0:shuming.setValue("");break;
					case 1:shuming.setValue(first.getValue());break;
					case 2:shuming.setValue(second.getValue());break;
					case 3:shuming.setValue(more.getValue());break;
					}
					
				}
		    	 
		     });
//		all.addEventListener(Events.ON_CHANGE, new EventListener(){
//
//			public void onEvent(Event arg0) throws Exception {
//				List namelist=new ArrayList();
//				String str=all.getValue().trim();
//				if(str.contains("，")){
//					throw new WrongValueException(all, "全部作者填写错误，请选择半角逗号！");
//				}else{
//				while (str.contains(",")) {
//						Label lb = new Label(str.substring(0,str.indexOf(",")));
//						namelist.add(lb.getValue());
//						str = str.substring(str.indexOf(",") + 1, str.length());
//					
//				}
//				namelist.add(str);
//				namesort.setValue(namelist.indexOf(user.getKuName())+1+"");
//				namesort.setDisabled(true);
//			}
//			}
//			
//		});
		if(jc!=null){
			 zb.addEventListener(Events.ON_CHANGE, new EventListener(){

					public void onEvent(Event arg0) throws Exception {
//						initJcwindow(cgmc.getValue().trim(),zb.getValue().trim(),GhZz.JC);
						int i=kw.Shuming(user.getKuName(),zb.getValue(),fzb.getValue(),bz.getValue());
						switch(i){
						case 0:shuming.setValue("");break;
						case 1:shuming.setValue(first.getValue());break;
						case 2:shuming.setValue(second.getValue());break;
						case 3:shuming.setValue(more.getValue());break;
						}
						
					}
			    	 
			     });
			
			//名称
			cgmc.setValue(jc.getZzMc());cgmc.setDisabled(true);
		
			//发表的刊物
			if(jc.getZzKw()!=null){
				kanwu.setValue(jc.getZzKw());
			}else{
				kanwu.setValue("");
			}
			
			//学科分类
			if(jc.getZzSubjetype() == null || jc.getZzSubjetype() == ""){
				subjetype.setSelectedIndex(0);
			}else {
				subjetype.setSelectedIndex(Integer.valueOf(jc.getZzSubjetype().trim()));
			}
			//著作类型
			if(jc.getZzWorktype() == null || jc.getZzWorktype() == ""){
				worktype.setSelectedIndex(0);
			}else {
				worktype.setSelectedIndex(Integer.valueOf(jc.getZzWorktype().trim()));
			}
			if(jc.getZzPublitime() != null){
				publitime.setValue(jc.getZzPublitime());
			}else{
				publitime.setValue("");
			}
			if(jc.getZzEditionno() != null){
				editionno.setValue(jc.getZzEditionno());
			}else {
				editionno.setValue("");
			}
			if(jc.getZzIsbn() != null){
				isbn.setValue(jc.getZzIsbn());
			}else {
				isbn.setValue("");
			}
		    if(jc.getZzNature()!=null){
		    	nature.setValue(jc.getZzNature());
		    }else{
		    	nature.setValue("");
		    }
			if(jc.getZzZb()!=null){
				zb.setValue(jc.getZzZb());
			}else{
				zb.setValue("");
			}
			if(jc.getZzFzb()!=null){
				fzb.setValue(jc.getZzFzb());
			}else{
				fzb.setValue("");
			}
			if(jc.getZzBz()!=null){
				bz.setValue(jc.getZzBz());
			}else{
				bz.setValue("");
			}
			int i=kw.Shuming(user.getKuName(),zb.getValue(),fzb.getValue(),bz.getValue());
			switch(i){
			case 0:shuming.setValue("");break;
			case 1:shuming.setValue(first.getValue());break;
			case 2:shuming.setValue(second.getValue());break;
			case 3:shuming.setValue(more.getValue());break;
			}
			if(jc.getZzRemark() != null){
				remark.setValue(jc.getZzRemark());
			}else {
				remark.setValue("");
			}
			GhJszz gj=jszzService.findByKuidAndLwidAndType(user.getKuId(), jc.getZzId(), GhJszz.JC);
			if(gj!=null){
				if(gj.getZzWords() != null){
					words.setValue(Integer.valueOf(gj.getZzWords().trim()));
				}else {
					words.setValue(0);
				}
				
				if(gj.getZzSelfs()!=null ){
					shuming.setValue(gj.getZzSelfs());
				}else{
					shuming.setValue("");
				}
			}else{

//				List namelist=new ArrayList();
//				String str=all.getValue().trim();
//				if(str.contains("，")){
//					throw new WrongValueException(all, "全部作者填写错误，请选择半角逗号！");
//				}else{
//				while (str.contains(",")) {
//						Label lb = new Label(str.substring(0,str.indexOf(",")));
//						namelist.add(lb.getValue());
//						str = str.substring(str.indexOf(",") + 1, str.length());
//					
//				}
//				namelist.add(str);
//				namesort.setValue(namelist.indexOf(user.getKuName())+1+"");
//				namesort.setDisabled(true);
//				}
				words.setValue(0);
			}
			writer.setValue(jc.getUser().getKuName());
			if(user.getKuId().intValue()!=jc.getKuId().intValue()){
				kanwu.setDisabled(true);publitime.setDisabled(true);editionno.setDisabled(true);
				isbn.setDisabled(true); zb.setDisabled(true);fzb.setDisabled(true);
				bz.setDisabled(true);nature.setDisabled(true);remark.setDisabled(true);
				subjetype.setDisabled(true);worktype.setDisabled(true);
				//附件情况
				deUpload.setDisabled(true);
				upFile.setDisabled(true);
				downFileZip.setDisabled(true);
			}
//			if(jc.getAuditState()!=null&&jc.getAuditState().shortValue()==GhZz.AUDIT_YES){
//				 submit.setDisabled(true);
//    	    	 upFile.setDisabled(true);
//			}
			
		}else{
			cgmc.addEventListener(Events.ON_CHANGE, new EventListener(){

				public void onEvent(Event arg0) throws Exception {
					initJcwindow(cgmc.getValue().trim(),zb.getValue().trim(),GhZz.JC);
				}
				
			});
			//署名情况
			 zb.addEventListener(Events.ON_CHANGE, new EventListener(){

					public void onEvent(Event arg0) throws Exception {
						initJcwindow(cgmc.getValue().trim(),zb.getValue().trim(),GhZz.JC);
						int i=kw.Shuming(user.getKuName(),zb.getValue(),fzb.getValue(),bz.getValue());
						switch(i){
						case 0:shuming.setValue("");break;
						case 1:shuming.setValue(first.getValue());break;
						case 2:shuming.setValue(second.getValue());break;
						case 3:shuming.setValue(more.getValue());break;
						}
						
					}
			    	 
			     });
			cgmc.setValue("");
			publitime.setValue("");
			kanwu.setValue("");
			editionno.setValue("");
			isbn.setValue("");
			nature.setValue("");
//			namesort.setValue("");
			words.setName("0");
			remark.setValue("");
			shuming.setValue("");
			subjetype.setSelectedIndex(0);
			worktype.setSelectedIndex(0);
			writer.setValue(user.getKuName());
			//选择论文名称
//			if(cgmc.getModel().getSize()>0){
//			cgmc.addEventListener(Events.ON_SELECT, new EventListener(){
//
//				public void onEvent(Event arg0) throws Exception {
//					if(cgmc.getSelectedItem()!=null){
//					Object [] mc=(Object [])cgmc.getSelectedItem().getValue();
//					GhZz jc=(GhZz)zzService.get(GhZz.class,(Long)mc[1]);
//					//学科分类
//					if(jc.getZzSubjetype() == null || jc.getZzSubjetype() == ""){
//						subjetype.setSelectedIndex(0);subjetype.setDisabled(true);
//					}else {
//						subjetype.setSelectedIndex(Integer.valueOf(jc.getZzSubjetype().trim()));subjetype.setDisabled(true);
//					}
//					//著作类型
//					if(jc.getZzWorktype() == null || jc.getZzWorktype() == ""){
//						worktype.setSelectedIndex(0);worktype.setDisabled(true);
//					}else {
//						worktype.setSelectedIndex(Integer.valueOf(jc.getZzWorktype().trim()));worktype.setDisabled(true);
//					}
//					if(jc.getZzZb()!=null){
//						zb.setValue(jc.getZzZb());
//					}else{
//						zb.setValue("");
//					}
//					if(jc.getZzFzb()!=null){
//						fzb.setValue(jc.getZzFzb());
//					}else{
//						fzb.setValue("");
//					}
//					if(jc.getZzBz()!=null){
//						bz.setValue(jc.getZzBz());
//					}else{
//						bz.setValue("");
//					}
//					  if(jc.getZzNature()!=null){
//					    	nature.setValue(jc.getZzNature());
//					    }else{
//					    	nature.setValue("");
//					    }
//				    nature.setDisabled(true);
//					zb.setDisabled(true);fzb.setDisabled(true);bz.setDisabled(true);
//					kanwu.setValue(jc.getZzKw()!=null?jc.getZzKw():"");kanwu.setDisabled(true);
//					editionno.setValue(jc.getZzEditionno()!=null?jc.getZzEditionno():"");editionno.setDisabled(true);
//					isbn.setValue(jc.getZzIsbn()!=null?jc.getZzIsbn():"");isbn.setDisabled(true);
//					remark.setValue(jc.getZzRemark()!=null?jc.getZzRemark():"");remark.setDisabled(true);
//					publitime.setValue(jc.getZzPublitime()!=null?jc.getZzPublitime():"");publitime.setDisabled(true);
//					//附件情况
//					deUpload.setDisabled(true);
//					upFile.setDisabled(true);
//					List fjList = ghfileService.findByFxmIdandFType(jc.getZzId(), 15);
//					if (fjList.size() == 0) {// 无附件
//						rowFile.setVisible(false);
//						downFileZip.setDisabled(true);
//					} else {// 有附件
//						// 初始化附件
//						downFileZip.setDisabled(false);
//						for (int i = 0; i < fjList.size(); i++) {
//							UploadFJ ufj = new UploadFJ(false);
//							try {
//								ufj.ReadFJ(getDesktop(), (GhFile) fjList.get(i));
//							} catch (InterruptedException e) {
//								e.printStackTrace();
//							}
//							fileModel.add(ufj);
//						}
//						upList.setModel(fileModel);
//						rowFile.setVisible(true);
//					}
//				}
//				}
//				
//			});
//			}
		}
		// 附件
		if (jc== null) {// 新添加
			rowFile.setVisible(false);
			downFileZip.setDisabled(true);
		} else {// 修改
			List fjList = ghfileService.findByFxmIdandFType(jc.getZzId(),15);
			if (fjList==null||fjList!=null&&fjList.size() == 0) {// 无附件
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
		//
	}
	
	public void onClick$submit() throws InterruptedException{
		
		//教材名
		if(cgmc.getValue()==null||"".equals(cgmc.getValue().trim())){
			throw new WrongValueException(cgmc, "您还没有填写教材名称！");
		}
		//主编名称
		if(zb.getValue()==null||"".equals(zb.getValue().trim())){
			throw new WrongValueException(zb, "您还没有填写主编名称！");
		}
		//出版单位
		if(kanwu.getValue()==null||"".equals(kanwu.getValue().trim())){
			throw new WrongValueException(kanwu, "您还没有填写出版单位！");
		}
		//出版时间
		if(publitime.getValue()==null||"".equals(publitime.getValue().trim())){
			throw new WrongValueException(publitime, "您还没有填写时间，格式如2008/9/28、2008、2008/9！");
		
		}else{
			try{
				String str = publitime.getValue().trim();
				if(str.length()<4){
					throw new WrongValueException(publitime, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
				}
				else if(str.length()==4||'/'==str.charAt(4)){
					String s[] = str.split("/");
					if(s.length==1||s.length==2||s.length==3){
						for(int i=0;i<s.length;i++){
							String si = s[i];
							Integer.parseInt(si);
						}
					}else{
						throw new WrongValueException(publitime, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
					}
				}else{
					throw new WrongValueException(publitime, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
					
				}
			}catch(NumberFormatException e){
				throw new WrongValueException(publitime, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
			}
		
		}
			
		//ISBN
		if(isbn.getValue()==null||"".equals(isbn.getValue().trim())){
			throw new WrongValueException(isbn, "您还没有填写ISBN号！");
		}
		
		
//		//本人署名情况
//		int zrs = 0; int pm = 0;
//		if(shuming.getValue()==null||"".equals(shuming.getValue().trim())){
//			throw new WrongValueException(shuming, "您还没有填写本人署名情况！");
//		}else{
//			try{
//				String str = shuming.getValue();
//				String s[] = str.split("/");
//				if(s.length==2){
//					pm = Integer.parseInt(s[0]);
//					zrs = Integer.parseInt(s[1]);
//				}else{
//					throw new WrongValueException(shuming, "您输入的名次格式有错误，请您重新填写！");
//				}
//			}catch(NumberFormatException e){
//				throw new WrongValueException(shuming, "您输入的名次格式有错误，请您重新填写！");
//			}
//			
//		}
		
		
		//false表示是修改，true表示是新建
		boolean index = false,owner=false; 
		if(jc==null){//说明这是新建的一个项目
			if(!editionno.isDisabled()){
				owner=true;
			}
			jc = new GhZz();
			index = true;
		}
//		if(cgmc.getSelectedItem()==null){
//			//没有选择项目名称，该项目属于该用户
//			owner=true;
//		}else{
//			owner=false;
//		}
		if(index){
			if(owner){
				//新建教材,两种情况：（一）当前用户输入了教材名称，保存到论文表和教师论文表；（二），用户选择的教材名称，只保存教师论文表
//				if(zzService.CheckOnlyOne(null, cgmc.getValue().trim(), GhZz.JC, zb.getValue().trim())){
//					Messagebox.show("您提交的教材信息已存在，不可以重复添加,请关闭窗口再继续！","警告",Messagebox.OK,Messagebox.EXCLAMATION);
//					jc=null;
//					return;
//				}
				if(!zb.getValue().contains(user.getKuName())&&!fzb.getValue().contains(user.getKuName())&&!bz.getValue().contains(user.getKuName())){
					Messagebox.show("编委会不包括您的姓名，请联系教材信息添加人！", "警告", Messagebox.OK, Messagebox.EXCLAMATION);
					jc=null;
					return;
				}
				jc.setZzMc(cgmc.getValue().trim());
				jc.setZzKw(kanwu.getValue().trim());
				jc.setZzPublitime(publitime.getValue().trim());
//				jc.setLwGrpm(pm); jc.setLwZrs(zrs);
				jc.setZzSubjetype(String.valueOf(subjetype.getSelectedIndex()));
				jc.setZzWorktype(String.valueOf(worktype.getSelectedIndex()));
				jc.setZzEditionno(editionno.getValue().trim());
				jc.setZzIsbn(isbn.getValue().trim());
				jc.setZzRemark(remark.getValue().trim());
				jc.setZzZb(zb.getValue().trim());
				jc.setZzNature(nature.getValue().trim());
				jc.setZzFzb(fzb.getValue().trim());
				jc.setZzBz(bz.getValue().trim());
				jc.setZzLx(GhZz.JC);
				jc.setKuId(kuid);
				zzService.save(jc);
				// 附件存储
				UploadFJ.ListModelListUploadCommand(fileModel, jc.getZzId());
				GhJszz jszz=new GhJszz();
				jszz.setKuId(user.getKuId());
				jszz.setZzId(jc.getZzId());
				//教材编者排名问题如何显示有待解决
				jszz.setZzSelfs(shuming.getValue());	
				jszz.setZzWords(String.valueOf(words.getValue()));
				jszz.setJszzType(GhJszz.JC);
				jszzService.save(jszz);
				Messagebox.show("保存成功！","提示",Messagebox.OK,Messagebox.INFORMATION);
			}else{
				if(!zb.getValue().contains(user.getKuName())&&!fzb.getValue().contains(user.getKuName())&&!bz.getValue().contains(user.getKuName())){
					Messagebox.show("编委会不包括您的姓名，请联系教材信息添加人！", "警告", Messagebox.OK, Messagebox.EXCLAMATION);
					jc=null;
					return;
				}
				GhJszz jszz=new GhJszz();
				jszz.setKuId(user.getKuId());
				jszz.setZzId(jc.getZzId());
				//教材编者排名问题如何显示有待解决
				jszz.setZzSelfs(shuming.getValue());	
				jszz.setZzWords(String.valueOf(words.getValue()));
				jszz.setJszzType(GhJszz.JC);
				jszzService.save(jszz);
				Messagebox.show("保存成功！","提示",Messagebox.OK,Messagebox.INFORMATION);
			}
			//
			
			Events.postEvent(Events.ON_CHANGE, this, null);
			
		}else{
			if(user.getKuId().intValue()==jc.getKuId().intValue()){
				if(!zb.getValue().contains(user.getKuName())&&!fzb.getValue().contains(user.getKuName())&&!bz.getValue().contains(user.getKuName())){
					Messagebox.show("编委会不包括您的姓名，请联系教材信息添加人！", "警告", Messagebox.OK, Messagebox.EXCLAMATION);
					jc=null;
					return;
				}
				if(zzService.CheckOnlyOne(jc, cgmc.getValue().trim(), GhZz.JC, zb.getValue().trim())){
					Messagebox.show("您提交的教材信息已存在，请关闭窗口再继续！","警告",Messagebox.OK,Messagebox.EXCLAMATION);
					return;
				}
			
				jc.setZzKw(kanwu.getValue().trim());
				jc.setZzPublitime(publitime.getValue().trim());
//				jc.setZzGrpm(pm); jc.setLwZrs(zrs);
				jc.setZzSubjetype(String.valueOf(subjetype.getSelectedIndex()));
				jc.setZzWorktype(String.valueOf(worktype.getSelectedIndex()));
				jc.setZzZb(zb.getValue().trim());
				jc.setZzFzb(fzb.getValue().trim());
				jc.setZzBz(bz.getValue().trim());
				jc.setZzEditionno(editionno.getValue().trim());
				jc.setZzNature(nature.getValue().trim());
				jc.setZzIsbn(isbn.getValue().trim());
				jc.setZzRemark(remark.getValue().trim());
				jc.setAuditState(null);
				jc.setAuditUid(null);
				jc.setAuditReason(null);
				zzService.update(jc);
				// 附件存储
				UploadFJ.ListModelListUploadCommand(fileModel, jc.getZzId());
				GhJszz jszz=jszzService.findByKuidAndLwidAndType(user.getKuId(), jc.getZzId(), GhJszz.JC);
				if(jszz!=null){
					//教材编者排名问题如何显示有待解决
					jszz.setZzSelfs(shuming.getValue());	
					jszz.setZzWords(String.valueOf(words.getValue()));
					jszzService.update(jszz);
					Messagebox.show("更新成功！","提示",Messagebox.OK,Messagebox.INFORMATION);
				}else{
					jszz=new GhJszz();
					jszz.setKuId(user.getKuId());
					jszz.setZzId(jc.getZzId());
					//教材编者排名问题如何显示有待解决
					jszz.setZzSelfs(shuming.getValue());	
					jszz.setZzWords(String.valueOf(words.getValue()));
					jszz.setJszzType(GhJszz.JC);
					jszzService.save(jszz);
					Messagebox.show("保存成功！","提示",Messagebox.OK,Messagebox.INFORMATION);
				}
				
			}else{
				if(!zb.getValue().contains(user.getKuName())&&!fzb.getValue().contains(user.getKuName())&&!bz.getValue().contains(user.getKuName())){
					Messagebox.show("编委会不包括您的姓名，请联系教材信息添加人！", "警告", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
				GhJszz jsjc=jszzService.findByKuidAndLwidAndType(user.getKuId(), jc.getZzId(), GhJszz.JC);
				if(jsjc!=null){
					//教材编者排名问题如何显示有待解决
					jsjc.setZzSelfs(shuming.getValue());	
					jsjc.setZzWords(String.valueOf(words.getValue()));
					jszzService.update(jsjc);
					Messagebox.show("更新成功！","提示",Messagebox.OK,Messagebox.INFORMATION);
				}else{
					jsjc=new GhJszz();
					jsjc.setKuId(user.getKuId());
					jsjc.setZzId(jc.getZzId());
					//教材编者排名问题如何显示有待解决
					jsjc.setZzSelfs(shuming.getValue());	
					jsjc.setZzWords(String.valueOf(words.getValue()));
					jsjc.setJszzType(GhJszz.JC);
					jszzService.save(jsjc);
				}
				Messagebox.show("保存成功！","提示",Messagebox.OK,Messagebox.INFORMATION);
			}
			
		}
		Events.postEvent(Events.ON_CHANGE, this, null);
	}
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		fileModel = new ListModelList(new ArrayList());
		upList.setModel(fileModel);
		user=(WkTUser)Sessions.getCurrent().getAttribute("user");
//		cgmc.setModel(new ListModelList(zzService.findAllname(user.getKuId(),user.getKuName(),GhZz.JC,GhJszz.JC)));
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
	public void initJcwindow(String jxmc,String zbj,Short lx){
		GhZz Jc=(GhZz)zzService.findByJcnameAndZbAndLx(jxmc, GhZz.JC, zbj);
		if(Jc!=null){
			jc=Jc;cgmc.setDisabled(true);zb.setDisabled(true);
			//学科分类
			if(jc.getZzSubjetype() == null || jc.getZzSubjetype() == ""){
				subjetype.setSelectedIndex(0);subjetype.setDisabled(true);
			}else {
				subjetype.setSelectedIndex(Integer.valueOf(jc.getZzSubjetype().trim()));subjetype.setDisabled(true);
			}
			//著作类型
			if(jc.getZzWorktype() == null || jc.getZzWorktype() == ""){
				worktype.setSelectedIndex(0);worktype.setDisabled(true);
			}else {
				worktype.setSelectedIndex(Integer.valueOf(jc.getZzWorktype().trim()));worktype.setDisabled(true);
			}
			if(jc.getZzZb()!=null){
				zb.setValue(jc.getZzZb());
			}else{
				zb.setValue("");
			}
			if(jc.getZzFzb()!=null){
				fzb.setValue(jc.getZzFzb());
			}else{
				fzb.setValue("");
			}
			if(jc.getZzBz()!=null){
				bz.setValue(jc.getZzBz());
			}else{
				bz.setValue("");
			}
			  if(jc.getZzNature()!=null){
			    	nature.setValue(jc.getZzNature());
			    }else{
			    	nature.setValue("");
			    }
			  int j=kw.Shuming(user.getKuName(),zb.getValue(),fzb.getValue(),bz.getValue());
				switch(j){
				case 0:shuming.setValue("");break;
				case 1:shuming.setValue(first.getValue());break;
				case 2:shuming.setValue(second.getValue());break;
				case 3:shuming.setValue(more.getValue());break;
				}
		    nature.setDisabled(true);
//			zb.setDisabled(true);
			fzb.setDisabled(true);bz.setDisabled(true);
			kanwu.setValue(jc.getZzKw()!=null?jc.getZzKw():"");kanwu.setDisabled(true);
			editionno.setValue(jc.getZzEditionno()!=null?jc.getZzEditionno():"");editionno.setDisabled(true);
			isbn.setValue(jc.getZzIsbn()!=null?jc.getZzIsbn():"");isbn.setDisabled(true);
			remark.setValue(jc.getZzRemark()!=null?jc.getZzRemark():"");remark.setDisabled(true);
			publitime.setValue(jc.getZzPublitime()!=null?jc.getZzPublitime():"");publitime.setDisabled(true);
			writer.setValue(jc.getUser().getKuName());
			//附件情况
			deUpload.setDisabled(true);
			upFile.setDisabled(true);
			List fjList = ghfileService.findByFxmIdandFType(jc.getZzId(), 15);
			if (fjList.size() == 0) {// 无附件
				rowFile.setVisible(false);
				downFileZip.setDisabled(true);
			} else {// 有附件
				// 初始化附件
				downFileZip.setDisabled(false);
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
		}else{
			jc=null;
//			publitime.setValue("");publitime.setDisabled(false);
//			kanwu.setValue("");kanwu.setDisabled(false);
//			editionno.setValue("");editionno.setDisabled(false);
//			isbn.setValue("");   isbn.setDisabled(false);
//			nature.setValue("");nature.setDisabled(false);
////			namesort.setValue("");
////			words.setName("0");
//			remark.setValue("");remark.setDisabled(false);
//			shuming.setValue("");
//			writer.setValue(user.getKuName());
//			subjetype.setSelectedIndex(0);subjetype.setDisabled(false);
//			worktype.setSelectedIndex(0);worktype.setDisabled(false);
//			zb.setDisabled(false);fzb.setDisabled(false);bz.setDisabled(false);
		}
	}
	 public void onClick$choice(){
	    	Map args=new HashMap();
	    	args.put("user", user);
	    	args.put("lx", GhZz.JC);
	    	args.put("type", GhJszz.JC);
	    	SelectJcWindow sjw=(SelectJcWindow)Executions.createComponents("/admin/personal/data/teacherinfo/jxqk/selectjc.zul", null, args);
	    	sjw.initShow();
	    	sjw.doHighlighted();
	    	sjw.addEventListener(Events.ON_CHANGE,  new EventListener(){

				public void onEvent(Event arg0) throws Exception {
					SelectJcWindow sjw=(SelectJcWindow)arg0.getTarget();
					if(sjw.getJclist().getSelectedItem()!=null){
					GhZz zz=(GhZz)sjw.getJclist().getSelectedItem().getValue();
					if(!zz.getZzBz().contains(user.getKuName())){
						Messagebox.show("您不是项目组成员，请联系项目填写人申请加入！", "提示：", Messagebox.OK, Messagebox.EXCLAMATION);
					}
					else{
					jc=zz;
					cgmc.setValue(zz.getZzMc());
					//学科分类
					if(jc.getZzSubjetype() == null || jc.getZzSubjetype() == ""){
						subjetype.setSelectedIndex(0);subjetype.setDisabled(true);
					}else {
						subjetype.setSelectedIndex(Integer.valueOf(jc.getZzSubjetype().trim()));subjetype.setDisabled(true);
					}
					//著作类型
					if(jc.getZzWorktype() == null || jc.getZzWorktype() == ""){
						worktype.setSelectedIndex(0);worktype.setDisabled(true);
					}else {
						worktype.setSelectedIndex(Integer.valueOf(jc.getZzWorktype().trim()));worktype.setDisabled(true);
					}
					if(jc.getZzZb()!=null){
						zb.setValue(jc.getZzZb());
					}else{
						zb.setValue("");
					}
					if(jc.getZzFzb()!=null){
						fzb.setValue(jc.getZzFzb());
					}else{
						fzb.setValue("");
					}
					if(jc.getZzBz()!=null){
						bz.setValue(jc.getZzBz());
					}else{
						bz.setValue("");
					}
					  if(jc.getZzNature()!=null){
					    	nature.setValue(jc.getZzNature());
					    }else{
					    	nature.setValue("");
					    }
					 int j=kw.Shuming(user.getKuName(),zb.getValue(),fzb.getValue(),bz.getValue());
						switch(j){
						case 0:shuming.setValue("");break;
						case 1:shuming.setValue(first.getValue());break;
						case 2:shuming.setValue(second.getValue());break;
						case 3:shuming.setValue(more.getValue());break;
						}  
				    nature.setDisabled(true);
				    
					zb.setDisabled(true);fzb.setDisabled(true);bz.setDisabled(true);
					kanwu.setValue(jc.getZzKw()!=null?jc.getZzKw():"");kanwu.setDisabled(true);
					editionno.setValue(jc.getZzEditionno()!=null?jc.getZzEditionno():"");editionno.setDisabled(true);
					isbn.setValue(jc.getZzIsbn()!=null?jc.getZzIsbn():"");isbn.setDisabled(true);
					remark.setValue(jc.getZzRemark()!=null?jc.getZzRemark():"");remark.setDisabled(true);
					publitime.setValue(jc.getZzPublitime()!=null?jc.getZzPublitime():"");publitime.setDisabled(true);
					writer.setValue(jc.getUser().getKuName());
					//附件情况
					deUpload.setDisabled(true);
					upFile.setDisabled(true);
					List fjList = ghfileService.findByFxmIdandFType(jc.getZzId(), 15);
					if (fjList.size() == 0) {// 无附件
						rowFile.setVisible(false);
						downFileZip.setDisabled(true);
					} else {// 有附件
						// 初始化附件
						downFileZip.setDisabled(false);
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
					sjw.detach();
					
				}
					}}	
	    	});
	 }
	 
	public void onClick$upFile() throws InterruptedException {
	/*	Object media = Fileupload.get();
		if (media == null) {
			return;
		}
		rowFile.setVisible(true);
		fileModel.add(media);*/
		UploadFJ ufj = new UploadFJ(true);
		Media media = ufj.Upload(this.getDesktop(),15, 10, "附件不能超过10MB",
				"教材情况附件------附件不能超过10MB");
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
		DownloadFJ.ListModelListDownloadCommand(this.getDesktop(), this.jc.getZzId(), fileModel);
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
	@SuppressWarnings("unchecked")
	public void onClick$reset(){
		List list = fileModel.getInnerList();
		for (int i = 0; i < list.size(); i++) {
			((UploadFJ) list.get(i)).DeleteFJ();
		}
		initWindow();
		fileModel.clear();
		rowFile.setVisible(false);
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

	public GhZz getJc() {
		return jc;
	}

	public void setJc(GhZz jc) {
		this.jc = jc;
	}

	


}
