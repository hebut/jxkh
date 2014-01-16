package com.uniwin.asm.personal.ui.data.teacherinfo;

import org.iti.gh.entity.GhXm;
import org.iti.gh.service.XmService;
import org.iti.xypt.ui.base.FrameworkWindow;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

public class JyxmWindow extends FrameworkWindow{

	Textbox cgmc,kaishi,jieshu,jingfei,gongxian;
	
	GhXm xm;
	Long kuid;
	
	XmService xmService;
	@Override
	public void initShow() {
		
	}

	@Override
	public void initWindow() {
		
		if(xm!=null){
			
			//名称
			cgmc.setValue(xm.getKyMc());

			//开始时间
			if(xm.getKyKssj()!=null){
				kaishi.setValue(xm.getKyKssj());
			}
			//结束时间
			if(xm.getKyJssj()!=null){
				jieshu.setValue(xm.getKyJssj());
			}
			
			//经费
			if(xm.getKyJf()!=null){
				jingfei.setValue(xm.getKyJf()+"");
			}
			//本人贡献
			if(xm.getKyGx()!=null){
				gongxian.setValue(xm.getKyGx()+"");
			}
		}else{
			cgmc.setValue("");
			kaishi.setValue("");
			jieshu.setValue("");
			jingfei.setValue("");
			gongxian.setValue("");
		}
	}
	
	public void onClick$submit() throws InterruptedException{
		//false表示是修改，true表示是新建
		boolean index = false; 
		if(xm==null){//说明这是新建的一个项目
			xm = new GhXm();
			index = true;
		}
		//项目名称
		if(cgmc.getValue()==null||"".equals(cgmc.getValue().trim())){
			Messagebox.show("您还没有填写项目名称");
			if(index){
				xm = null;
			}
			return;
		}else{
			xm.setKyMc(cgmc.getValue());
		}
		//开始时间
		if(kaishi.getValue()==null||"".equals(kaishi.getValue().trim())){
			Messagebox.show("您还没有填写开始时间");
			if(index){
				xm = null;
			}
			return;
		}else{
			try{
				String str = kaishi.getValue().trim();
				if(str.length()<4){
					Messagebox.show("您输入的开始时间格式有错误，请您重新填写。");
					if(index){
						xm = null;
					}
					return;
				}
				else if(str.length()==4||'/'==str.charAt(4)){
					String s[] = str.split("/");
					if(s.length==1||s.length==2||s.length==3){
						for(int i=0;i<s.length;i++){
							String si = s[i];
							Integer.parseInt(si);
						}
					}else{
						Messagebox.show("您输入的开始时间格式有错误，请您重新填写。");
						if(index){
							xm = null;
						}
						return;
					}
				}else{
					Messagebox.show("您输入的开始时间格式有错误，请您重新填写。");
					if(index){
						xm = null;
					}
					return;
				}
			}catch(NumberFormatException e){
				Messagebox.show("您输入的开始时间格式有错误，请您重新填写。");
				if(index){
					xm = null;
				}
				return;
			}
			xm.setKyKssj(kaishi.getValue());
		}
		//结束时间
		if(jieshu.getValue()==null||"".equals(jieshu.getValue().trim())){
			Messagebox.show("您还没有填写结束时间");
			if(index){
				xm = null;
			}
			return;
		}else{
			try{
				String str = jieshu.getValue().trim();
				if(str.length()<4){
					Messagebox.show("您输入的结束时间格式有错误，请您重新填写。");
					if(index){
						xm = null;
					}
					return;
				}
				else
				if(str.length()==4||'/'==str.charAt(4)){
					String s[] = str.split("/");
					if(s.length==1||s.length==2||s.length==3){
						for(int i=0;i<s.length;i++){
							String si = s[i];
							Integer.parseInt(si);
						}
					}else{
						Messagebox.show("您输入的结束时间格式有错误，请您重新填写。");
						if(index){
							xm = null;
						}
						return;
					}
				}else{
					Messagebox.show("您输入的结束时间格式有错误，请您重新填写。");
					if(index){
						xm = null;
					}
					return;
				}
			}catch(NumberFormatException e){
				Messagebox.show("您输入的结束时间格式有错误，请您重新填写。");
				if(index){
					xm = null;
				}
				return;
			}
			xm.setKyJssj(jieshu.getValue());
		}
		//经费
		if(jingfei.getValue()==null||"".equals(jingfei.getValue().trim())){
			Messagebox.show("您还没有填写经费数目");
			if(index){
				xm = null;
			}
			return;
		}else{
			Float kyJf = 0F;
			try{
				kyJf = Float.parseFloat(jingfei.getValue());
			}catch(NumberFormatException e){
				Messagebox.show("您输入的经费并不是一个规范的数字！");
				if(index){
					xm=null;
				}
				return;
			}
			xm.setKyJf(kyJf);
		}
		//本人贡献
		if(gongxian.getValue()==null||"".equals(gongxian.getValue().trim())){
			Messagebox.show("您还没有填写贡献情况");
			if(index){
				xm = null;
			}
			return;
		}else{
			Integer kyGx = 0;
			try{
				kyGx = Integer.parseInt(gongxian.getValue());
			}catch(NumberFormatException e){
				Messagebox.show("您输入的贡献情况并不是一个规范的数字！");
				if(index){
					xm=null;
				}
				return;
			}
			xm.setKyGx(kyGx);
		}
		if(index){
			xm.setKyLx(GhXm.JYDQ);
			xm.setKuId(kuid);
			xmService.save(xm);
			Messagebox.show("保存成功");
		}else{
			xmService.update(xm);
			Messagebox.show("保存成功");
		}
		Events.postEvent(Events.ON_CHANGE, this, null);
	}
	
	public void onClick$reset(){
		initWindow();
	}
	
	public void onClick$close(){
		this.detach();
	}

	public GhXm getXm() {
		return xm;
	}

	public void setXm(GhXm xm) {
		this.xm = xm;
	}

	public Long getKuid() {
		return kuid;
	}

	public void setKuid(Long kuid) {
		this.kuid = kuid;
	}

}
