package com.uniwin.asm.personal.ui.data.teacherinfo;

import java.util.ArrayList;
import java.util.List;

import org.iti.gh.entity.GhXshy;
import org.iti.gh.service.XshyService;
import org.iti.xypt.ui.base.FrameworkWindow;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

public class YqGjxshyWindow extends FrameworkWindow{

	Textbox cgmc,shijian,cgzt,cgdd,remarks;
	Intbox zrs,jwrs;
	Listbox cdrw;
	
	GhXshy xs;
	Long kuid;
	
	XshyService xshyService;
	Button submit;
	@Override
	public void initShow() {
		
	}

	@Override
	public void initWindow() {
		List subcdrw = new ArrayList();
		String[] Subcdrw = {"--请选择--","主持","参加","独立"};
		for(int i = 0;i < Subcdrw.length; i++){
			subcdrw.add(Subcdrw[i]);
		}
		//本人承担任务或作用
		cdrw.setModel(new ListModelList(subcdrw));
		
		if(xs!=null){
			
			//名称
			if(xs.getHyMc() != null){
				cgmc.setValue(xs.getHyMc());
			}else{
				cgmc.setValue("");
			}
			//举办时间
			if(xs.getHySj()!=null){
				shijian.setValue(xs.getHySj());
			}else{
				shijian.setValue("");
			}
			
			//总人数
			if(xs.getHyZrs()!=null){
				zrs.setValue(xs.getHyZrs());
			}else{
				zrs.setValue(null);
			}
			//境外人数
			if(xs.getHyJwrs()!=null){
				jwrs.setValue(xs.getHyJwrs());
			}else{
				jwrs.setValue(null);
			}
			//本人承担任务或作用
			if(xs.getHyEffect() == null || xs.getHyEffect() == ""){
				cdrw.setSelectedIndex(0);
			}else {
				cdrw.setSelectedIndex(Integer.valueOf(xs.getHyEffect().trim()));
			}
			if(xs.getHyTheme() != null){
				cgzt.setValue(xs.getHyTheme());	
			}else{
				cgzt.setValue("");	
			}
			if(xs.getHyPlace() != null){
				cgdd.setValue(xs.getHyPlace());	
			}else{
				cgdd.setValue("");	
			}
			if(xs.getHyRemarks() != null){
				remarks.setValue(xs.getHyRemarks());	
			}else{
				remarks.setValue("");
			}
			if(xs.getAuditState()!=null&&xs.getAuditState().shortValue()==GhXshy.AUDIT_YES){
				submit.setDisabled(true);
			}
		
		}else{
			cgmc.setValue("");
			shijian.setValue("");
			zrs.setValue(null);
			jwrs.setValue(null);
			remarks.setValue("");
			cgdd.setValue("");
			cgzt.setValue("");
		}
	}
	
	public void onClick$submit() throws InterruptedException{
		
		//会议名称
		if(cgmc.getValue()==null||"".equals(cgmc.getValue().trim())){
			throw new WrongValueException(cgmc, "您还没有填写会议名称！");
		}
		//举办时间
		if(shijian.getValue()==null||"".equals(shijian.getValue().trim())){
			throw new WrongValueException(shijian, "您还没有填写时间，格式如2008/9/28、2008、2008/9！");
		
		}else{
			try{
				String str = shijian.getValue().trim();
				if(str.length()<4){
					throw new WrongValueException(shijian, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
				}
				else if(str.length()==4||'/'==str.charAt(4)){
					String s[] = str.split("/");
					if(s.length==1||s.length==2||s.length==3){
						for(int i=0;i<s.length;i++){
							String si = s[i];
							Integer.parseInt(si);
						}
					}else{
						throw new WrongValueException(shijian, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
					}
				}else{
					throw new WrongValueException(shijian, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
				}
			}catch(NumberFormatException e){
				throw new WrongValueException(shijian, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
			}
		}
		//总人数
		if(zrs.getValue()==null){
			throw new WrongValueException(zrs, "您还没有填写总人数！");
		}
		//境外人员数
		if(jwrs.getValue()==null){
			throw new WrongValueException(jwrs, "您还没有填写境外人员数！");
		}
		if(zrs.getValue()<jwrs.getValue())
		{
			throw new WrongValueException(zrs, "请注意总人数和境外人员数，请填写符合逻辑的人数！");
		}
		//false表示是修改，true表示是新建
		boolean index = false; 
		if(xs==null){//说明这是新建的一个项目
			xs = new GhXshy();
			index = true;
		}
		xs.setHyMc(cgmc.getValue());
		xs.setHySj(shijian.getValue());
		xs.setHyZrs(zrs.getValue());
		xs.setHyJwrs(jwrs.getValue());
		xs.setHyEffect(String.valueOf(cdrw.getSelectedIndex()));
		xs.setHyRemarks(remarks.getValue());
		xs.setHyPlace(cgdd.getValue());
		xs.setHyTheme(cgzt.getValue());
		
		if(index){
			xs.setHyIfcj(GhXshy.IFCJ_NO);
			xs.setKuId(kuid);
			xshyService.save(xs);
			Messagebox.show("保存成功！","提示",Messagebox.OK,Messagebox.INFORMATION);
		}else{
			xs.setAuditState(null);
			xs.setAuditUid(null);
			xs.setReason(null);
			xshyService.update(xs);
			Messagebox.show("保存成功！","提示",Messagebox.OK,Messagebox.INFORMATION);
		}
		Events.postEvent(Events.ON_CHANGE, this, null);
	}
	
	public void onClick$reset(){
		initWindow();
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

	public GhXshy getXs() {
		return xs;
	}

	public void setXs(GhXshy xs) {
		this.xs = xs;
	}



}
