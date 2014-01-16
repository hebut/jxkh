package com.uniwin.asm.personal.ui.data.teacherinfo.jyqk;


import org.iti.gh.entity.GhZs;
import org.iti.gh.service.ZsService;
import org.iti.gh.ui.listbox.GradeListbox;
import org.iti.xypt.ui.base.FrameworkWindow;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

public class Addwindow extends FrameworkWindow {

	Textbox na,no;
	Label ye;
	Button submit,reset,back;
	ZsService zsService;
	Long uid;
	String nian;
	GradeListbox gradelist;
	public Long getUid(){
		return uid;
	}
	public void setUid(Long uid){
		this.uid=uid;
	}


	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this); 
	}
	@Override
	public void initShow() {


	}

	@Override
	public void initWindow() {
		gradelist.initZsGradelist(uid);
		na.addEventListener(Events.ON_CHANGE, new EventListener(){

			public void onEvent(Event arg0) throws Exception {
				no.setValue(Jisuan(na.getValue().toString().trim())+"");

			}

		});
	}
	public int Jisuan(String name){
		String[] ming=name.split("、");
		int n=ming.length;
		return n;
	}
	public void onClick$submit() throws InterruptedException{
		String str = na.getValue().trim();
		if (str.contains("，")||str.contains(",")) {
			throw new WrongValueException(na, "学生名单填写错误，请选择顿号！");
		} else {
//		List list = zsService.findByKuidAndYear(uid,gradelist.getSelectedItem().getLabel().substring(0, 4));
		GhZs zs =  new GhZs();
		zs.setYear(gradelist.getSelectedItem().getLabel().substring(0, 4));
		zs.setKuId(uid);
		zs.setNum(Integer.parseInt(no.getValue()));
		zs.setName(na.getValue().trim());
		zsService.save(zs);
		Messagebox.show("保存成功！","提示",Messagebox.OK,Messagebox.INFORMATION);
		Events.postEvent(Events.ON_CHANGE, this, null);
//		if(list.size()>0){
//			if(Messagebox.show("此学年招生情况已存在！是否保存修改？","提示信息", Messagebox.OK | Messagebox.NO, Messagebox.QUESTION) == Messagebox.OK){
//				zs = (GhZs) list.get(0);
//				zs.setYear(gradelist.getSelectedItem().getLabel().substring(0, 4));
//				zs.setKuId(uid);
//				zs.setNum(Integer.parseInt(no.getValue()));
//				zs.setName(na.getValue().trim());
//				zsService.update(zs);
//				Messagebox.show("更新成功！","提示",Messagebox.OK,Messagebox.INFORMATION);
//				Events.postEvent(Events.ON_CHANGE, this, null);
//			}else{
//				
//			}
//
//		}
		}
	}
	public void onClick$reset(){
		gradelist.initGradelist();
		na.setValue(" ");no.setValue(null);
	}
	public void onClick$back(){
		this.detach();

	}

}
