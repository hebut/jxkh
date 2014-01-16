package com.uniwin.asm.personal.ui.data.teacherinfo;

import java.util.ArrayList;
import java.util.List;

import org.iti.gh.entity.GhJxbg;
import org.iti.gh.service.JxbgService;
import org.iti.xypt.ui.base.FrameworkWindow;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

public class GwjxbgWindow extends FrameworkWindow{

	Textbox cgmc,shijian, huiyi,baogao,zhuti,didian,remarks;
	
	GhJxbg bg;
	Long kuid;
	Listbox nature;
	JxbgService jxbgService;
	Button submit;
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
		if(bg!=null){
			
			//讲学或报告人姓名
			cgmc.setValue(bg.getJxJxmc());
			
			//国外大学名称或国际会议名称
			if(bg.getJxHymc()!=null){
				huiyi.setValue(bg.getJxHymc());
			}
			
			//时间
			if(bg.getJxSj()!=null){
				shijian.setValue(bg.getJxSj());
			}
			//讲学或报告名称
			if(bg.getJxBgmc()!=null){
				baogao.setValue(bg.getJxBgmc());
			}
			if(bg.getJxSubject() != null){
				zhuti.setValue(bg.getJxSubject());	
			}else {
				zhuti.setValue("");	
			}
			if(bg.getJxPlace() != null){
				didian.setValue(bg.getJxPlace());
			}else {
				didian.setValue("");
			}
			if(bg.getJxRemarks() != null){
				remarks.setValue(bg.getJxRemarks());
			}else{
				remarks.setValue("");
			}
			if(bg.getJxLx()!=null&&bg.getJxLx().shortValue()==GhJxbg.GJJX){
				nature.setSelectedIndex(1);
			}else {
				nature.setSelectedIndex(0);
			}
//			if(bg.getAuditState()!=null&&bg.getAuditState().shortValue()==GhJxbg.AUDIT_YES){
//				submit.setDisabled(true);
//			}
		}else{
			cgmc.setValue("");
			shijian.setValue("");
			huiyi.setValue("");
			baogao.setValue("");
			zhuti.setValue("");
			didian.setValue("");
			remarks.setValue("");
		}
	}
	
	public void onClick$submit() throws InterruptedException{
		
		//讲学或报告人姓名
		if(cgmc.getValue()==null||"".equals(cgmc.getValue().trim())){
			throw new WrongValueException(cgmc, "您还没有填写汇报人名称！");
		}
		//国外大学名称或国际会议名称
		if(huiyi.getValue()==null||"".equals(huiyi.getValue().trim())){
			throw new WrongValueException(huiyi, "您还没有填写会议名称！");
		}
		//讲学或报告名称
		if(baogao.getValue()==null||"".equals(baogao.getValue().trim())){
			throw new WrongValueException(baogao, "您还没有填写讲学或报告名称！");
		}
		//时间
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
		
		
		//false表示是修改，true表示是新建
		boolean index = false; 
		if(bg==null){//说明这是新建的一个项目
			bg = new GhJxbg();
			index = true;
		}
		bg.setJxSj(shijian.getValue().trim());
		bg.setJxJxmc(cgmc.getValue().trim());
		bg.setJxHymc(huiyi.getValue().trim());
		bg.setJxBgmc(baogao.getValue().trim());
		bg.setJxPlace(didian.getValue().trim());
		bg.setJxRemarks(remarks.getValue().trim());
		bg.setJxSubject(zhuti.getValue().trim());
		bg.setJxLx(Short.parseShort(nature.getSelectedIndex()+""));
		
		if(index){
			if(jxbgService.CheckOnlyOne(null, cgmc.getValue().trim(), huiyi.getValue().trim(), GhJxbg.IFCJ_YES,kuid)){
				Messagebox.show("您提交的报告信息已存在，不可以重复添加 ！","警告",Messagebox.OK,Messagebox.EXCLAMATION);
				bg=null;
				return;
			}
			bg.setJxIfcj(GhJxbg.IFCJ_YES);
			bg.setKuId(kuid);
			jxbgService.save(bg);
			Messagebox.show("保存成功！","提示",Messagebox.OK,Messagebox.INFORMATION);
			
		}else{
			if(jxbgService.CheckOnlyOne(bg, cgmc.getValue().trim(), huiyi.getValue().trim(), GhJxbg.IFCJ_YES,kuid)){
				Messagebox.show("您提交的报告信息已存在，不予保存更新的信息 ！","警告",Messagebox.OK,Messagebox.EXCLAMATION);
				return;
			}
			bg.setAuditState(null);
			bg.setAuditUid(null);
			bg.setReason(null);
			jxbgService.update(bg);
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

	public GhJxbg getBg() {
		return bg;
	}

	public void setBg(GhJxbg bg) {
		this.bg = bg;
	}



}
