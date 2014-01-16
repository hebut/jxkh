package com.uniwin.asm.personal.ui.data.teacherinfo;

import java.util.ArrayList;
import java.util.List;

import org.iti.gh.entity.GhJlhz;
import org.iti.gh.service.JlhzService;
import org.iti.xypt.ui.base.FrameworkWindow;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

public class CdgjhzWindow extends FrameworkWindow{

	Textbox  cgmc,Kssj,Jssj,duixiang,remarks;
	
	GhJlhz jl;
	Long kuid;
	Listbox nature;
	JlhzService jlhzService;
	@Override
	public void initShow() {
		
	}

	@Override
	public void initWindow() {
		
		List naturelist=new ArrayList();
		String[] nlist = {"国内","国际"};
		for(int i = 0;i < nlist.length; i++){
			naturelist.add(nlist[i]);
		}
		nature.setModel(new ListModelList(naturelist));
		nature.setSelectedIndex(0);
		if(jl!=null){
			
			//国际交流合作项目名称
			cgmc.setValue(jl.getHzMc());
			if(jl.getHzLx()!=null&&jl.getHzLx().shortValue()==GhJlhz.GJHZ){
				nature.setSelectedIndex(1);
			}else{
				nature.setSelectedIndex(0);
			}
			//项目开始时间
			if(jl.getHzKssj()!=null){
				Kssj.setValue(jl.getHzKssj());
			}
			//项目结束时间
			if(jl.getHzJssj()!=null){
				Jssj.setValue(jl.getHzJssj());
			}
			//合作对象
			if(jl.getHzDx()!=null){
				duixiang.setValue(jl.getHzDx());
			}
			if(jl.getHzRemark() != null){
			remarks.setValue(jl.getHzRemark());
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
		String sj=Kssj.getValue()!=null?Kssj.getValue():""+"-"+Jssj.getValue()!=null?Jssj.getValue():"";
		jl.setHzSj(sj.trim());
		jl.setHzKssj(Kssj.getValue().trim());
		jl.setHzJssj(Jssj.getValue().trim());
		jl.setHzDx(duixiang.getValue().trim());
		jl.setHzRemark(remarks.getValue().trim());
		jl.setHzLx(Short.parseShort(nature.getSelectedIndex()+""));
		if(index){
			if(jlhzService.CheckOnlyOne(null, cgmc.getValue().trim(), duixiang.getValue().trim(), GhJlhz.IFCJ_YES,kuid)){
				Messagebox.show("您提交的合作信息已存在，禁止重复添加！","警告",Messagebox.OK,Messagebox.EXCLAMATION);
				jl=null;
				return;
			}
			jl.setHzIfcj(GhJlhz.IFCJ_YES);
			jl.setKuId(kuid);
			jlhzService.save(jl);
			Messagebox.show("保存成功！","提示",Messagebox.OK,Messagebox.INFORMATION);
			
		}else{
			if(jlhzService.CheckOnlyOne(jl, cgmc.getValue().trim(), duixiang.getValue().trim(), GhJlhz.IFCJ_YES,kuid)){
				Messagebox.show("您提交的合作信息已存在，禁止重复添加！","警告",Messagebox.OK,Messagebox.EXCLAMATION);
				return;
			}
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
