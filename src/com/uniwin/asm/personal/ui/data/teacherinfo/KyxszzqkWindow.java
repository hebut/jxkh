package com.uniwin.asm.personal.ui.data.teacherinfo;

import java.util.ArrayList;
import java.util.List;

import org.iti.gh.entity.GhFile;
import org.iti.gh.entity.GhJszz;
import org.iti.gh.entity.GhZz;
import org.iti.gh.service.GhFileService;
import org.iti.gh.service.JszzService;
import org.iti.gh.service.ZzService;
import org.iti.xypt.ui.base.FrameworkWindow;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

import com.uniwin.framework.common.fileuploadex.DownloadFJ;
import com.uniwin.framework.common.fileuploadex.UploadFJ;
import com.uniwin.framework.entity.WkTUser;

public class KyxszzqkWindow extends FrameworkWindow{
    
	private static final long serialVersionUID = 1670373775683291955L;
	Label first,second,more,shuming,writer;
	Textbox publitime,kanwu,editionno,isbn,nature,remark;
	Textbox zb,fzb,bz;
	Intbox words;
	Listbox worktype,subjetype;
	GhZz zz;
	Long kuid;
	
	//暂存附件
	private Row rowFile;
	Listbox upList;
	ListModelList fileModel;
	GhFileService ghfileService;
	JszzService jszzService;
	ZzService zzService;
	WkTUser user;
	Button downFile,deUpload,upFile,downFileZip,submit;
	Textbox cgmc;
	@Override
	public void initShow() {
		
	}

	@Override
	public void initWindow() {
		List subtype = new ArrayList();
		List subworktype = new ArrayList();
		String[] Subtype = {"--请选择--","自然科学","社会科学"};
		for(int i = 0;i < Subtype.length; i++){
			subtype.add(Subtype[i]);
		}
		String[] Subworktype = {"--请选择--","独著","合著","专著","编著","教材","参考书或工具书" ,"论文集","译著","调查报告","其他"};
		for(int i = 0;i < Subworktype.length; i++){
			subworktype.add(Subworktype[i]);
		}
		//学科分类
		subjetype.setModel(new ListModelList(subtype));
		worktype.setModel(new ListModelList(subworktype));
	    zb.addEventListener(Events.ON_CHANGE, new EventListener(){

				public void onEvent(Event arg0) throws Exception {
					int i=Shuming(user.getKuName(),zb.getValue(),fzb.getValue(),bz.getValue());
					switch(i){
					case 0:shuming.setValue("");break;
					case 1:shuming.setValue(first.getValue());break;
					case 2:shuming.setValue(second.getValue());break;
					case 3:shuming.setValue(more.getValue());break;
					}
					
				}
		    	 
		     });
		 fzb.addEventListener(Events.ON_CHANGE, new EventListener(){

				public void onEvent(Event arg0) throws Exception {
					int i=Shuming(user.getKuName(),zb.getValue(),fzb.getValue(),bz.getValue());
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
					int i=Shuming(user.getKuName(),zb.getValue(),fzb.getValue(),bz.getValue());
					switch(i){
					case 0:shuming.setValue("");break;
					case 1:shuming.setValue(first.getValue());break;
					case 2:shuming.setValue(second.getValue());break;
					case 3:shuming.setValue(more.getValue());break;
					}
					
				}
		    	 
		     });
		if (zz != null) {
			zb.addEventListener(Events.ON_CHANGE, new EventListener() {

				public void onEvent(Event arg0) throws Exception {
					int i = Shuming(user.getKuName(), zb.getValue(), fzb.getValue(), bz.getValue());
					switch (i) {
					case 0:
						shuming.setValue("");
						break;
					case 1:
						shuming.setValue(first.getValue());
						break;
					case 2:
						shuming.setValue(second.getValue());
						break;
					case 3:
						shuming.setValue(more.getValue());
						break;
					}

				}

			});
			fzb.addEventListener(Events.ON_CHANGE, new EventListener() {

				public void onEvent(Event arg0) throws Exception {
					int i = Shuming(user.getKuName(), zb.getValue(), fzb.getValue(), bz.getValue());
					switch (i) {
					case 0:
						shuming.setValue("");
						break;
					case 1:
						shuming.setValue(first.getValue());
						break;
					case 2:
						shuming.setValue(second.getValue());
						break;
					case 3:
						shuming.setValue(more.getValue());
						break;
					}

				}

			});
			bz.addEventListener(Events.ON_CHANGE, new EventListener() {

				public void onEvent(Event arg0) throws Exception {
					int i = Shuming(user.getKuName(), zb.getValue(), fzb.getValue(), bz.getValue());
					switch (i) {
					case 0:
						shuming.setValue("");
						break;
					case 1:
						shuming.setValue(first.getValue());
						break;
					case 2:
						shuming.setValue(second.getValue());
						break;
					case 3:
						shuming.setValue(more.getValue());
						break;
					}

				}

			});
			// 专著名称
			if (zz.getZzMc() != null) {
				cgmc.setValue(zz.getZzMc());
				cgmc.setDisabled(true);
			} else {
				cgmc.setValue("");
			}

			// 出版时间
			if (zz.getZzPublitime() != null) {
				publitime.setValue(zz.getZzPublitime());
			} else {
				publitime.setValue("");
			}

			// 出版单位及ISBN
			if (zz.getZzKw() != null) {
				kanwu.setValue(zz.getZzKw());
			} else {
				kanwu.setValue("");
			}

			if (zz.getZzZb() != null) {
				zb.setValue(zz.getZzZb());
			} else {
				zb.setValue("");
			}

			if (zz.getZzFzb() != null) {
				fzb.setValue(zz.getZzFzb());
			} else {
				fzb.setValue("");
			}
			if (zz.getZzBz() != null) {
				bz.setValue(zz.getZzBz());
			} else {
				bz.setValue("");
			}
			if (zz.getZzNature() != null) {
				nature.setValue(zz.getZzNature());
			} else {
				nature.setValue("");
			}

			// 学科分类
			if (zz.getZzSubjetype() == null || zz.getZzSubjetype() == "") {
				subjetype.setSelectedIndex(0);
			} else {
				subjetype.setSelectedIndex(Integer.valueOf(zz.getZzSubjetype()
						.trim()));
			}
			// 著作类型
			if (zz.getZzWorktype() == null || zz.getZzWorktype() == "") {
				worktype.setSelectedIndex(0);
			} else {
				worktype.setSelectedIndex(Integer.valueOf(zz.getZzWorktype()
						.trim()));
			}
			if (zz.getZzEditionno() != null) {
				editionno.setValue(zz.getZzEditionno());
			} else {
				editionno.setValue("");
			}
			if (zz.getZzIsbn() != null) {
				isbn.setValue(zz.getZzIsbn());
			} else {
				isbn.setValue("");
			}

			int i = Shuming(user.getKuName(), zb.getValue(), fzb.getValue(), bz.getValue());
			switch (i) {
			case 0:
				shuming.setValue("");
				break;
			case 1:
				shuming.setValue(first.getValue());
				break;
			case 2:
				shuming.setValue(second.getValue());
				break;
			case 3:
				shuming.setValue(more.getValue());
				break;
			}
			if (zz.getZzRemark() != null) {
				remark.setValue(zz.getZzRemark());
			} else {
				remark.setValue("");
			}
			GhJszz jszz = jszzService.findByKuidAndLwidAndType(user.getKuId(),zz.getZzId(), GhJszz.ZZ);
			if (jszz != null) {
				if (jszz.getZzWords() != null && !"".equals(jszz.getZzWords())) {
					words.setValue(Integer.valueOf(jszz.getZzWords()));
				} else {
					words.setValue(0);
				}
//				if (jszz.getZzSelfs() != null) {
//					shuming.setValue(jszz.getZzSelfs());
//				} else {
//					shuming.setValue("");
//				}
				// 附件
				if (zz == null) {// 新添加
					rowFile.setVisible(false);
				} else {// 修改
					List fjList = ghfileService.findByFxmIdandFType(zz.getZzId(), 5);
					if (fjList.size() == 0) {// 无附件
						rowFile.setVisible(false);
						downFileZip.setDisabled(true);
					} else {// 有附件
						// 初始化附件
						downFileZip.setDisabled(false);
						for (int j = 0; j < fjList.size(); j++) {
							UploadFJ ufj = new UploadFJ(false);
							try {
								ufj.ReadFJ(this.getDesktop(), (GhFile) fjList.get(j));
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							fileModel.add(ufj);
						}
						upList.setModel(fileModel);
						rowFile.setVisible(true);
					}
				}
			} else {
				words.setValue(0);
			}
			writer.setValue(zz.getUser().getKuName());
			if (user.getKuId().intValue() != zz.getKuId().intValue()) {
				kanwu.setDisabled(true);
				publitime.setDisabled(true);
				editionno.setDisabled(true);
				isbn.setDisabled(true);
				zb.setDisabled(true);
				fzb.setDisabled(true);
				bz.setDisabled(true);
				nature.setDisabled(true);
				remark.setDisabled(true);
				subjetype.setDisabled(true);
				worktype.setDisabled(true);
				// 附件情况
				deUpload.setDisabled(true);
				upFile.setDisabled(true);
				downFileZip.setDisabled(true);
			}
		}else{
			cgmc.addEventListener(Events.ON_CHANGE, new EventListener(){

				public void onEvent(Event arg0) throws Exception {
					initZzWindow(cgmc.getValue().trim(),zb.getValue().trim());
				}
				
			});
			zb.addEventListener(Events.ON_CHANGE, new EventListener() {

				public void onEvent(Event arg0) throws Exception {
					int i = Shuming(user.getKuName(), zb.getValue(), fzb.getValue(), bz.getValue());
					switch (i) {
					case 0:
						shuming.setValue("");
						break;
					case 1:
						shuming.setValue(first.getValue());
						break;
					case 2:
						shuming.setValue(second.getValue());
						break;
					case 3:
						shuming.setValue(more.getValue());
						break;
					}

				}

			});
			fzb.addEventListener(Events.ON_CHANGE, new EventListener() {

				public void onEvent(Event arg0) throws Exception {
					int i = Shuming(user.getKuName(), zb.getValue(), fzb.getValue(), bz.getValue());
					switch (i) {
					case 0:
						shuming.setValue("");
						break;
					case 1:
						shuming.setValue(first.getValue());
						break;
					case 2:
						shuming.setValue(second.getValue());
						break;
					case 3:
						shuming.setValue(more.getValue());
						break;
					}

				}

			});
			bz.addEventListener(Events.ON_CHANGE, new EventListener() {

				public void onEvent(Event arg0) throws Exception {
					int i = Shuming(user.getKuName(), zb.getValue(), fzb.getValue(), bz.getValue());
					switch (i) {
					case 0:
						shuming.setValue("");
						break;
					case 1:
						shuming.setValue(first.getValue());
						break;
					case 2:
						shuming.setValue(second.getValue());
						break;
					case 3:
						shuming.setValue(more.getValue());
						break;
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
			// 选择项目名称
//			if (cgmc.getModel().getSize() > 0) {
//				cgmc.addEventListener(Events.ON_SELECT, new EventListener() {
//
//					public void onEvent(Event arg0) throws Exception {
//						if(cgmc.getSelectedItem()!=null){
//						Object [] mc=(Object [])cgmc.getSelectedItem().getValue();
//						GhZz lw=(GhZz)zzService.get(GhZz.class, (Long)mc[1]);
//						publitime.setDisabled(true);kanwu.setDisabled(true);
//						subjetype.setDisabled(true);worktype.setDisabled(true);
//						isbn.setDisabled(true);nature.setDisabled(true);
//						publitime.setDisabled(true);
//						//出版时间
//						if(lw.getZzPublitime()!=null){
//							publitime.setValue(lw.getZzPublitime());
//						}else{
//							publitime.setValue("");
//						}
//						if(lw.getZzZb()!=null){
//							zb.setValue(lw.getZzZb());
//						}else{
//							zb.setValue("");
//						}
////						if(lw.getZzFzb()!=null){
////							fzb.setValue(lw.getZzFzb());
////						}else{
////							fzb.setValue("");
////						}
////						if(lw.getZzBz()!=null){
////							bz.setValue(lw.getZzBz());
////						}else{
////							bz.setValue("");
////						}
//						if(lw.getZzNature()!=null){
//						    nature.setValue(lw.getZzNature());
//						}else{
//						    nature.setValue("");
//						}
//						//出版单位及ISBN
//						if(lw.getZzKw()!=null){
//							kanwu.setValue(lw.getZzKw());
//						}else{
//							kanwu.setValue("");
//						}
//						//学科分类
//						if(lw.getZzSubjetype() == null || lw.getZzSubjetype() == ""){
//							subjetype.setSelectedIndex(0);
//						}else {
//							subjetype.setSelectedIndex(Integer.valueOf(lw.getZzSubjetype().trim()));
//						}
//						//著作类型
//						if(lw.getZzWorktype() == null || lw.getZzWorktype() == ""){
//							worktype.setSelectedIndex(0);
//						}else {
//							worktype.setSelectedIndex(Integer.valueOf(lw.getZzWorktype().trim()));
//						}
//						if(lw.getZzEditionno() != null){
//							editionno.setValue(lw.getZzEditionno());
//						}else{
//							editionno.setValue("");
//						}
//						if(lw.getZzIsbn() != null){
//							isbn.setValue(lw.getZzIsbn());
//						}else {
//							isbn.setValue("");
//						}
//						deUpload.setDisabled(true);upFile.setDisabled(true);
//						List fjList = ghfileService.findByFxmIdandFType(lw.getZzId(), 5);
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
//		}
		}
		// 附件
		if (zz == null) {// 新添加
			rowFile.setVisible(false);
		} else {// 修改
			List fjList = ghfileService.findByFxmIdandFType(zz.getZzId(), 5);
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
		//
	}
	
	public void onClick$submit() throws InterruptedException{
		
		//专著名称
		if(cgmc.getValue()==null||"".equals(cgmc.getValue().trim())){
			throw new WrongValueException(cgmc, "您还没有填写名称！");
		}
		if(zb.getValue()==null||"".equals(zb.getValue().trim())){
			throw new WrongValueException(zb, "您还没有填写主编名称！");
		}
//		//出版单位及ISBN
		if(kanwu.getValue()==null||"".equals(kanwu.getValue().trim())){
			throw new WrongValueException(kanwu, "您还没有填写出版单位 ！");
		}
		//出版时间
		if(publitime.getValue()==null||"".equals(publitime.getValue().trim())){
			throw new WrongValueException(publitime, "您还没有填写时间，格式如2008/9/28、2008、2008/9！");
			
		}else{
			try{
				String str = publitime.getValue().trim();
				if(str.length() < 4){
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
		//false表示是修改，true表示是新建
		boolean index = false,owner=false; 
		if(zz==null){//说明这是新建的一个项目
			if(!editionno.isDisabled()){
				owner=true;
			}
			zz = new GhZz();
			index = true;
		}
//		if(cgmc.getSelectedItem()==null){
//			//没有选择项目名称，该项目属于该用户
//			owner=true;
//		}else{
//			owner=false;
//		}
//		
		if(index){
			if(owner){
			  if(zzService.CheckOnlyOne(null, cgmc.getValue().trim(), GhZz.ZZ, zb.getValue().trim())){
					Messagebox.show("您提交的专著信息已存在，不可以重复添加！","警告",Messagebox.OK,Messagebox.EXCLAMATION);
					zz=null;
					return;
				}
			  if(!zb.getValue().contains(user.getKuName())&&!fzb.getValue().contains(user.getKuName())&&!bz.getValue().contains(user.getKuName())){
					Messagebox.show("编委会不包括您的姓名，请联系专著信息添加人！", "警告", Messagebox.OK, Messagebox.EXCLAMATION);
					zz=null;
					return;
				}
				zz.setZzMc(cgmc.getValue());
				zz.setZzPublitime(publitime.getValue());
				zz.setZzKw(kanwu.getValue());
				zz.setZzSubjetype(String.valueOf(subjetype.getSelectedIndex()));
				zz.setZzWorktype(String.valueOf(worktype.getSelectedIndex()));
				zz.setZzEditionno(editionno.getValue());
				zz.setZzIsbn(isbn.getValue());
				zz.setZzRemark(remark.getValue());
				zz.setZzLx(GhZz.ZZ);
				zz.setZzNature(nature.getValue());
				zz.setZzZb(zb.getValue());
				zz.setZzFzb(fzb.getValue());
				zz.setZzBz(bz.getValue());
				zz.setKuId(kuid);
				zzService.save(zz);
				// 附件存储
				UploadFJ.ListModelListUploadCommand(fileModel, zz.getZzId());
				//
				GhJszz jslw=new GhJszz();
				jslw.setZzId(zz.getZzId());
				jslw.setJszzType(GhJszz.ZZ);
				jslw.setKuId(user.getKuId());
				jslw.setZzSelfs( shuming.getValue());
				jslw.setZzWords(words.getValue()+"");
				jszzService.save(jslw);
			Messagebox.show("保存成功！","提示",Messagebox.OK,Messagebox.INFORMATION);
			}else{
				if(!zb.getValue().contains(user.getKuName())&&!fzb.getValue().contains(user.getKuName())&&!bz.getValue().contains(user.getKuName())){
					Messagebox.show("编委会不包括您的姓名，请联系教材信息添加人！", "警告", Messagebox.OK, Messagebox.EXCLAMATION);
					zz=null;
					return;
				}
				GhJszz jslw=new GhJszz();
				jslw.setKuId(user.getKuId());
				jslw.setZzId(zz.getZzId());
				jslw.setZzSelfs(shuming.getValue());	
				jslw.setZzWords(String.valueOf(words.getValue()));
				jslw.setJszzType(GhJszz.ZZ);
				jszzService.save(jslw);
				Messagebox.show("保存成功！","提示",Messagebox.OK,Messagebox.INFORMATION);
			}
			Events.postEvent(Events.ON_CHANGE, this, null);
		}else{
//			if(user.getKuId().intValue()==zz.getKuId().intValue()){
				if(zzService.CheckOnlyOne(zz, cgmc.getValue().trim(), GhZz.ZZ, zb.getValue().trim())){
					Messagebox.show("您提交的专著信息已存在，不可以重复添加！","警告",Messagebox.OK,Messagebox.EXCLAMATION);
					return;
				}
				if(!zb.getValue().contains(user.getKuName())&&!fzb.getValue().contains(user.getKuName())&&!bz.getValue().contains(user.getKuName())){
					Messagebox.show("编委会不包括您的姓名，请联系专著信息添加人！", "警告", Messagebox.OK, Messagebox.EXCLAMATION);
					zz=null;
					return;
				}
				zz.setZzPublitime(publitime.getValue());
				zz.setZzKw(kanwu.getValue());
				zz.setZzSubjetype(String.valueOf(subjetype.getSelectedIndex()));
				zz.setZzWorktype(String.valueOf(worktype.getSelectedIndex()));
				zz.setZzEditionno(editionno.getValue());
				zz.setZzIsbn(isbn.getValue());
				zz.setZzZb(zb.getValue());
				zz.setZzFzb(fzb.getValue());
				zz.setZzBz(bz.getValue());
				zz.setZzNature(nature.getValue());
				zz.setZzRemark(remark.getValue());
				zz.setAuditState(null);
				zz.setAuditUid(null);
				zz.setAuditReason(null);
				zzService.update(zz);
				// 附件存储
				UploadFJ.ListModelListUploadCommand(fileModel, zz.getZzId());
				//
				GhJszz jslw=jszzService.findByKuidAndLwidAndType(user.getKuId(), zz.getZzId(), GhJszz.ZZ);
				if(jslw!=null){
					jslw.setZzSelfs(shuming.getValue());
					jslw.setZzWords(words.getValue()+"");
					jszzService.update(jslw);
					Messagebox.show("保存成功！","提示",Messagebox.OK,Messagebox.INFORMATION);
					Events.postEvent(Events.ON_CHANGE, this, null);
				}else{
					jslw=new GhJszz();
					jslw.setZzId(zz.getZzId());
					jslw.setJszzType(GhJszz.ZZ);
					jslw.setKuId(user.getKuId());
					jslw.setZzSelfs(shuming.getValue());
					jslw.setZzWords(words.getValue()+"");
					jszzService.update(jslw);
					Messagebox.show("保存成功！","提示",Messagebox.OK,Messagebox.INFORMATION);
					Events.postEvent(Events.ON_CHANGE, this, null);
				}
				
//			}else{
//				GhJszz jslw=jszzService.findByKuidAndLwidAndType(user.getKuId(), zz.getZzId(), GhJszz.ZZ);
//				if(jslw!=null){
////					jslw.setZzSelfs(shuming.getValue());
//					jslw.setZzWords(words.getValue()+"");
//					jszzService.update(jslw);
//				}else{
//					jslw=new GhJszz();
//					jslw.setZzId(zz.getZzId());
//					jslw.setJszzType(GhJszz.ZZ);
//					jslw.setKuId(user.getKuId());
////					jslw.setZzSelfs(shuming.getValue());
//					jslw.setZzWords(words.getValue()+"");
//					jszzService.save(jslw);
//				}
//				Messagebox.show("保存成功！","提示",Messagebox.OK,Messagebox.INFORMATION);
//				Events.postEvent(Events.ON_CHANGE, this, null);
//			}	
		}
//		Events.postEvent(Events.ON_CHANGE, this, null);
	}
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		fileModel = new ListModelList(new ArrayList());
		upList.setModel(fileModel);
//		cgmc.setModel(new ListModelList(zzService.findAllname(user.getKuId(),user.getKuName(),GhZz.ZZ, GhJszz.ZZ)));
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
	public void onClick$upFile() throws InterruptedException {
		/*Object media = Fileupload.get();
		if (media == null) {
			return;
		}
		rowFile.setVisible(true);
		fileModel.add(media);*/
		UploadFJ ufj = new UploadFJ(true);
		Media media = ufj.Upload(this.getDesktop(), 5, 10, "附件不能超过10MB",
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
			fileModel.remove(it.getValue());
			rowFile.setVisible(false);
		} else if (fileModel.getSize() > 1) {
			//
			temp.DeleteFJ();
			//
			fileModel.remove(it.getValue());
		}
	}

	//打包下载附件
	public void onClick$downFileZip(){
		DownloadFJ.ListModelListDownloadCommand(this.getDesktop(), this.zz.getZzId(), fileModel);
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

	public GhZz getZz() {
		return zz;
	}

	public void setZz(GhZz zz) {
		this.zz = zz;
	}
     public int Shuming(String username,String zhubian,String fubian,String canbian){ 
    	Boolean isContains1=zhubian.contains(username);
    	Boolean isContains2=fubian.contains(username);
    	Boolean isContains3=canbian.contains(username);
    	if(isContains1==true){
    		return 1;
    	}else if(isContains2==true){
    		return 2;
    	}else if(isContains3==true){
    		return 3;
    	}else{
    		return 0;
    	}
	
     }
	public void initZzWindow(String zzname,String zbname){
		GhZz ZZ=zzService.findByJcnameAndZbAndLx(zzname, GhZz.ZZ, zbname);
		if(ZZ!=null){
			zz=ZZ;
			cgmc.setDisabled(true);zb.setDisabled(true);
			fzb.setDisabled(true);bz.setDisabled(true);
			publitime.setDisabled(true);kanwu.setDisabled(true);
			subjetype.setDisabled(true);worktype.setDisabled(true);
			isbn.setDisabled(true);nature.setDisabled(true);
			editionno.setDisabled(true);remark.setDisabled(true);
			//出版时间
			if(zz.getZzPublitime()!=null){
				publitime.setValue(zz.getZzPublitime());
			}else{
				publitime.setValue("");
			}
			if(zz.getZzZb()!=null){
				zb.setValue(zz.getZzZb());
			}else{
				zb.setValue("");
			}
			if(zz.getZzFzb()!=null){
				fzb.setValue(zz.getZzFzb());
			}else{
				fzb.setValue("");
			}
			if(zz.getZzBz()!=null){
				bz.setValue(zz.getZzBz());
			}else{
				bz.setValue("");
			}
			if(zz.getZzNature()!=null){
			    nature.setValue(zz.getZzNature());
			}else{
			    nature.setValue("");
			}
			int j=Shuming(user.getKuName(),zb.getValue(),fzb.getValue(),bz.getValue());
			switch(j){
				case 0:shuming.setValue("");break;
				case 1:shuming.setValue(first.getValue());break;
				case 2:shuming.setValue(second.getValue());break;
				case 3:shuming.setValue(more.getValue());break;
			}
			//出版单位及ISBN
			if(zz.getZzKw()!=null){
				kanwu.setValue(zz.getZzKw());
			}else{
				kanwu.setValue("");
			}
			//学科分类
			if(zz.getZzSubjetype() == null || zz.getZzSubjetype() == ""){
				subjetype.setSelectedIndex(0);
			}else {
				subjetype.setSelectedIndex(Integer.valueOf(zz.getZzSubjetype().trim()));
			}
			//著作类型
			if(zz.getZzWorktype() == null || zz.getZzWorktype() == ""){
				worktype.setSelectedIndex(0);
			}else {
				worktype.setSelectedIndex(Integer.valueOf(zz.getZzWorktype().trim()));
			}
			if(zz.getZzEditionno() != null){
				editionno.setValue(zz.getZzEditionno());
			}else{
				editionno.setValue("");
			}
			if(zz.getZzIsbn() != null){
				isbn.setValue(zz.getZzIsbn());
			}else {
				isbn.setValue("");
			}
			remark.setValue(zz.getZzRemark()!=null?zz.getZzRemark():"");
			writer.setValue(zz.getUser().getKuName());
			//附件情况
			deUpload.setDisabled(true);
			upFile.setDisabled(true);
			List fjList = ghfileService.findByFxmIdandFType(zz.getZzId(), 15);
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
			zz=null;
			publitime.setValue("");publitime.setDisabled(false);
			kanwu.setValue("");kanwu.setDisabled(false);
			subjetype.setSelectedIndex(0);subjetype.setDisabled(false);
			worktype.setSelectedIndex(0);worktype.setDisabled(false);
			isbn.setValue("");isbn.setDisabled(false);
			nature.setValue("");nature.setDisabled(false);
		}
	}


}
