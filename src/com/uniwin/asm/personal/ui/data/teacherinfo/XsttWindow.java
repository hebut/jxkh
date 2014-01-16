package com.uniwin.asm.personal.ui.data.teacherinfo;
/*社会兼职功能类*/

import java.util.ArrayList;
import java.util.List;

import org.iti.gh.common.util.MessageBoxshow;
import org.iti.gh.entity.GhShjz;
import org.iti.gh.service.ShjzService;
import org.iti.xypt.ui.base.FrameworkWindow;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

import com.uniwin.framework.entity.WkTUser;


public class XsttWindow extends FrameworkWindow{
Textbox name,position,time;
Listbox marrystate;
Button submit;
Long kuid;

short type;
ShjzService shjzService;
GhShjz shjz;

	@Override
	public void initShow() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initWindow() {
		List state = new ArrayList();
		String[] State = {"-请选择-","学术团体","社会团体"};
		for(int i = 0; i < State.length; i++){
			state.add(State[i]);
		}
		marrystate.setModel(new ListModelList(state));
		if(shjz !=null){
			name.setValue(shjz.getJzName());
			position.setValue(shjz.getJzPosition());
			time.setValue(shjz.getJzTime());
			if(shjz.getJzCharacter() == null || shjz.getJzCharacter() == "" ){
				marrystate.setSelectedIndex(0);
			}else{
				marrystate.setSelectedIndex(Integer.valueOf(shjz.getJzCharacter().trim()));
			}
		}else{
			name.setValue("");
			position.setValue("");
			time.setValue("");
		}
	}
	public void onClick$reset(){
		initWindow();
	}
	
	public void onClick$submit(){
		if(name.getValue().equalsIgnoreCase("") ||name.getValue() == null){
			throw new WrongValueException(name, "请填写团体名称！");
		}
		if(position.getValue().equalsIgnoreCase("") || position.getValue() == null){
			throw new WrongValueException(position, "请填写担任职务！");
				
		}
		
		//举办时间
		if(time.getValue()==null||"".equals(time.getValue().trim())){
			throw new WrongValueException(time, "您还没有填写时间，格式如2008/9/28、2008、2008/9、2007/2/3-2007/3/5！");
		}else{
			try{
				String strr = time.getValue().trim();
				if(strr.length()<4){
					throw new WrongValueException(time, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9、2007/2/3-2007/3/5！");
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
							throw new WrongValueException(time, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9、2007/2/3-2007/3/5！");
							
						}
					}else{
						throw new WrongValueException(time, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9、2007/2/3-2007/3/5！");
						
					}
				}
			}
				}catch(NumberFormatException e){
				throw new WrongValueException(time, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9、2007/2/3-2007/3/5！");
				
			}
		
		}
	//false表示是修改，true表示是新建
	boolean index = false; 
	if(shjz==null){//说明这是新建的一个项目
		shjz = new GhShjz();
		index = true;
	}
	
	shjz.setJzName(name.getValue());
	shjz.setJzPosition(position.getValue());
	shjz.setJzTime(time.getValue());
	shjz.setJzCharacter(String.valueOf(marrystate.getSelectedIndex()));
	if(index){
		shjz.setKuId(kuid);
		shjzService.save(shjz);
	}else{
		shjzService.update(shjz);
	}
	
	MessageBoxshow.showInfo("添加成功！");
	 this.detach();
	 Events.postEvent(Events.ON_CHANGE, this, null);
	}
	public void onClick$close(){
		this.detach();
	}
	public GhShjz getShjz() {
		return shjz;
	}

	public void setShjz(GhShjz shjz) {
		this.shjz = shjz;
	}
	public Long getKuid() {
		return kuid;
	}

	public void setKuid(Long kuid) {
		this.kuid = kuid;
	}

}
