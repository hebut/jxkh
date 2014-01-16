package com.uniwin.asm.personal.ui.data.teacherinfo;
import org.iti.gh.common.util.MessageBoxshow;
import org.iti.gh.entity.GhSgcf;
import org.iti.gh.service.SgcfService;
import org.iti.xypt.ui.base.FrameworkWindow;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import com.uniwin.framework.entity.WkTUser;


public class SgcfWindow extends FrameworkWindow{
Textbox year,result,dept,yuanyin;
Button submit;
Long kuid;
SgcfService sgcfService;
GhSgcf sgcf;


	@Override
	public void initShow() {
	}

	@Override
	public void initWindow() {
		if(sgcf != null){
			if(sgcf.getSgName() != null){
				yuanyin.setValue(sgcf.getSgName());
			}else{
				yuanyin.setValue("");
			}
			if(sgcf.getSgYear() != null){
				year.setValue(sgcf.getSgYear());
			}else{
				year.setValue("");
			}
			if(sgcf.getSgDep() != null){
				dept.setValue(sgcf.getSgDep());
			}else{
				dept.setValue("");
			}
			if(sgcf.getSgReason() != null){
				result.setValue(sgcf.getSgReason());
			}else{
				result.setValue("");
			}
		}else{
			yuanyin.setValue("");
			year.setValue("");
			dept.setValue("");
			result.setValue("");
		}
	
	}

	public void onClick$submit(){
		if(yuanyin.getValue().equalsIgnoreCase("") ||yuanyin.getValue() == null){
			throw new WrongValueException(yuanyin, "请填写事故名称！");
		}
		
		//时间
		if(year.getValue()==null||"".equals(year.getValue().trim())){
			throw new WrongValueException(year, "您还没有填写时间，格式如2008/9/28、2008、2008/9、2007/2/3-2007/3/5！");
		}else{
			try{
				String strr = year.getValue().trim();
				if(strr.length()<4){
					throw new WrongValueException(year, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9、2007/2/3-2007/3/5！");
				}else {
				String sj[] = strr.split("-");
				for(int i=0;i<sj.length;i++){
					String str = sj[i].trim();
					if(str.length()==4||'/'==str.charAt(4)){
						String s[] = str.split("/");
						if(s.length==1||s.length==2||s.length==3){
							for(int j=0;j<s.length;j++){
								String si = s[j].trim();
								Integer.parseInt(si);
							}
						}else{
							throw new WrongValueException(year, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9、2007/2/3-2007/3/5！");
							
						}
					}else{
						throw new WrongValueException(year, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9、2007/2/3-2007/3/5！");
						
					}
				}
			}
				}catch(NumberFormatException e){
				throw new WrongValueException(year, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9、2007/2/3-2007/3/5！");
				
			}
		
		}
		if(dept.getValue().equalsIgnoreCase("") ||dept.getValue() == null){
			throw new WrongValueException(dept, "请填写处分单位！");
		}
		//false表示是修改，true表示是新建
		boolean index = false; 
		if(sgcf==null){//说明这是新建的一个项目
			sgcf = new GhSgcf();
			index = true;
		}
	
		sgcf.setSgDep(dept.getValue());
		sgcf.setSgName(result.getValue());
		sgcf.setSgReason(yuanyin.getValue());
		sgcf.setSgYear(year.getValue());
		if(index){
			sgcf.setKuId(kuid);
			sgcfService.save(sgcf);
		}else {
			sgcfService.update(sgcf);
		}
	  
		MessageBoxshow.showInfo("添加成功！");
		 this.detach();
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

	public GhSgcf getSgcf() {
		return sgcf;
	}

	public void setSgcf(GhSgcf sgcf) {
		this.sgcf = sgcf;
	}
}
