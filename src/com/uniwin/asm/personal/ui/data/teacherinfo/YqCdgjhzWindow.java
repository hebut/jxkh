package com.uniwin.asm.personal.ui.data.teacherinfo;

import org.iti.gh.entity.GhJlhz;
import org.iti.gh.service.JlhzService;
import org.iti.gh.service.XmService;
import org.iti.xypt.ui.base.FrameworkWindow;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

public class YqCdgjhzWindow extends FrameworkWindow{

	Textbox cgmc,Kssj,Jssj,duixiang,remarks;
	
	GhJlhz jl;
	Long kuid;
	
	JlhzService jlhzService;
	Button submit;
	@Override
	public void initShow() {
		
	}

	@Override
	public void initWindow() {
		
		if(jl!=null){
			
			//国际交流合作项目名称
			if(jl.getHzMc() != null){
				cgmc.setValue(jl.getHzMc());
			}else {
				cgmc.setValue("");
			}
		
			
			//项目开始时间
			if(jl.getHzKssj()!=null){
				Kssj.setValue(jl.getHzKssj());
			}else{
				Kssj.setValue("");
			}
			if(jl.getHzJssj()!=null){
				Jssj.setValue(jl.getHzJssj());
			}else{
				Jssj.setValue("");
			}
			
			//合作对象
			if(jl.getHzDx()!=null){
				duixiang.setValue(jl.getHzDx());
			}else{
				duixiang.setValue("");
			}
			if(jl.getHzRemark() != null){
				remarks.setValue(jl.getHzRemark());	
			}else{
				remarks.setValue("");	
			}
		if(jl.getAuditState()!=null&&jl.getAuditState().shortValue()==GhJlhz.AUDIT_YES){
			submit.setDisabled(true);
		}
		}else{
			cgmc.setValue("");
			Kssj.setValue("");
			Jssj.setValue("");
			duixiang.setValue("");
			remarks.setValue("");
		}
	}
	
	public void onClick$submit() throws InterruptedException{
	
		//国际交流合作项目名称
		if(cgmc.getValue()==null||"".equals(cgmc.getValue().trim())){
			throw new WrongValueException(cgmc, "您还没有填写国际交流合作项目名称！");
		}
		//项目起止时间
		if(Kssj.getValue()==null||"".equals(Kssj.getValue().trim())){
			throw new WrongValueException(Kssj, "您还没有填写时间，格式如2008/9/28、2008、2008/9！");
		
		}else{
			try{
				String str = Kssj.getValue().trim();
				if(str.length()<4){
					throw new WrongValueException(Kssj, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
				}
				else if(str.length()==4||'/'==str.charAt(4)){
					String s[] = str.split("/");
					if(s.length==1||s.length==2||s.length==3){
						for(int i=0;i<s.length;i++){
							String si = s[i];
							Integer.parseInt(si);
						}
					}else{
						throw new WrongValueException(Kssj, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
					}
				}else{
					throw new WrongValueException(Kssj, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
					
				}
			}catch(NumberFormatException e){
				throw new WrongValueException(Kssj, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
			}
		
		}
		// 结束时间
		if (Jssj.getValue() == null || "".equals(Jssj.getValue().trim())) {
			throw new WrongValueException(Jssj, "您还没有填写结束时间，格式如2008/9/28、2008、2008/9！");
		} else {
			try {
				String str = Jssj.getValue().trim();
				if (str.length() < 4) {
					throw new WrongValueException(Jssj, "您输入的结束时间格式有错误，格式如2008/9/28、2008、2008/9！");
				} else if (str.length() == 4 || '/' == str.charAt(4)) {
					String s[] = str.split("/");
					if (s.length == 1 || s.length == 2 || s.length == 3) {
						for (int i = 0; i < s.length; i++) {
							String si = s[i];
							Integer.parseInt(si);
						}
					} else {
						throw new WrongValueException(Jssj, "您输入的结束时间格式有错误，格式如2008/9/28、2008、2008/9！");
					}
				} else {
					throw new WrongValueException(Jssj, "您输入的结束时间格式有错误，格式如2008/9/28、2008、2008/9！");
				}
			} catch (NumberFormatException e) {
				throw new WrongValueException(Jssj, "您输入的结束时间格式有错误，格式如2008/9/28、2008、2008/9！");
			}
		}
		//合作对象
		if(duixiang.getValue()==null||"".equals(duixiang.getValue().trim())){
			throw new WrongValueException(duixiang, "您还没有填写合作对象！");
		}
	
		
		//false表示是修改，true表示是新建
		boolean index = false; 
		if(jl==null){//说明这是新建的一个项目
			jl = new GhJlhz();
			index = true;
		}
		jl.setHzMc(cgmc.getValue().trim());
		jl.setHzKssj(Kssj.getValue().trim());
		jl.setHzJssj(Jssj.getValue().trim());
		jl.setHzDx(duixiang.getValue().trim());
		jl.setHzRemark(remarks.getValue().trim());
		if(index){
//			if(jlhzService.CheckOnlyOne(null, cgmc.getValue().trim(), duixiang.getValue().trim(), GhJlhz.IFCJ_NO)){
//				Messagebox.show("您提交的合作信息已存在，请关闭窗口再继续！","警告",Messagebox.OK,Messagebox.EXCLAMATION);
//				jl=null;
//			   return;
//			}
			jl.setHzIfcj(GhJlhz.IFCJ_NO);
			jl.setKuId(kuid);
			jlhzService.save(jl);
			Messagebox.show("保存成功！","提示",Messagebox.OK,Messagebox.INFORMATION);
			
		}else{
//			if(jlhzService.CheckOnlyOne(jl, cgmc.getValue().trim(), duixiang.getValue().trim(), GhJlhz.IFCJ_NO)){
//				Messagebox.show("您提交的合作信息已存在，请关闭窗口再继续！","警告",Messagebox.OK,Messagebox.EXCLAMATION);
//				return;
//			}
			jl.setAuditState(null);
			jl.setAuditUid(null);
			jl.setReason(null);
			jlhzService.update(jl);
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

	public GhJlhz getJl() {
		return jl;
	}

	public void setJl(GhJlhz jl) {
		this.jl = jl;
	}

}
