package org.iti.jxkh.indicator.businessdept;

import java.util.Date;
import java.util.List;

import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.service.BusinessIndicatorService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.iti.common.util.ConvertUtil;

public class BusinessNewWindow extends Window implements AfterCompose {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 Textbox name,num,desc,index,size;//指标名称、指标序号、指标系数、指标描述、指标量化属性
	 Intbox mark;//量化分值
	 Row bindex;
	 private Radio open,stop;
	 Label pselect;
	 private Toolbarbutton submit,reset,close;
	    
	Jxkh_BusinessIndicator business = null;
	
	BusinessIndicatorService businessIndicatorService;

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);		
//		pselect.setItemRenderer(new ListitemRenderer() {
//			public void render(Listitem arg0, Object arg1) throws Exception {
//				Jxkh_BusinessIndicator business = (Jxkh_BusinessIndicator)arg1;
//				arg0.setValue(business);
//				arg0.setLabel(business.getKbName());
//			}
//		});	
	}
	
	public void initWindow(Jxkh_BusinessIndicator business) {
		this.business = business;
		//实现pselect的父指标的数据注入??????????????????????????????		
		/**
		 * 待完成
		 */
		if(business != null) {
			mark.setReadonly(true);
			size.setReadonly(true);
			mark.setValue(business.getKbScore());
			size.setValue(business.getKbMeasure());
			List<Jxkh_BusinessIndicator> blist = businessIndicatorService.findIndicator(business.getKbPid());
			  if(blist.size() != 0) {
				  Jxkh_BusinessIndicator b = blist.get(0);
				  pselect.setValue(b.getKbName());
			  }
		}		
	}
	
	public void onClick$submit() throws InterruptedException {
		Jxkh_BusinessIndicator newIndicator = new Jxkh_BusinessIndicator();
		if(name.getValue() == null || "".equals(name.getValue())) {
			throw new WrongValueException(name, "指标名称必填，请填写！");
		}
		if(index.getValue() == null || "".equals(index.getValue())) {
			throw new WrongValueException(index, "指标系数必填，请填写！");
		}
		if(mark.getValue() == null) {
			throw new WrongValueException(mark, "量化分值必填，请填写！");
		}
		if(size.getValue() == null || "".equals(size.getValue())) {
			throw new WrongValueException(size, "指标分类属性必填，请填写！");
		}
		if(business == null) {
			//一级指标的保存
			newIndicator.setKbName(name.getValue().trim());
			newIndicator.setKbDesc(desc.getValue());
			newIndicator.setKbIndex(Float.valueOf(index.getValue().trim()));
			newIndicator.setKbLock(Jxkh_BusinessIndicator.LOCK_NO);
			newIndicator.setKbMeasure(size.getValue().trim());
			if(num.getValue() != null) {
				newIndicator.setKbOrdno(Long.valueOf(num.getValue()));
			}			
			newIndicator.setKbPid(0L);
			newIndicator.setKbScore(mark.getValue());
			if(open.isChecked() || !stop.isChecked()) {
				newIndicator.setKbStatus(Jxkh_BusinessIndicator.USE_YES);
			}else {
				newIndicator.setKbStatus(Jxkh_BusinessIndicator.USE_NO);
			}
			newIndicator.setKbTime(ConvertUtil.convertDateString(new Date()));
			List<Jxkh_BusinessIndicator> bulist = businessIndicatorService.getChildBusiness(0L);	
			//获得指标的属性值
			newIndicator.setKbValue(bulist.size()+1+"");
		}else {
			//二级及多级指标的保存
			newIndicator.setKbName(name.getValue().trim());
			newIndicator.setKbDesc(desc.getValue());
			newIndicator.setKbIndex(Float.valueOf(index.getValue().trim()));
			newIndicator.setKbLock(Jxkh_BusinessIndicator.LOCK_NO);
			newIndicator.setKbMeasure(business.getKbMeasure());
			if(num.getValue() != null) {
				newIndicator.setKbOrdno(Long.valueOf(num.getValue()));
			}
			newIndicator.setKbPid(business.getKbId());
			newIndicator.setKbScore(business.getKbScore());
			if(open.isChecked() || !stop.isChecked()) {
				newIndicator.setKbStatus(Jxkh_BusinessIndicator.USE_YES);
			}else {
				newIndicator.setKbStatus(Jxkh_BusinessIndicator.USE_NO);
			}
			newIndicator.setKbTime(ConvertUtil.convertDateString(new Date()));
			//获得指标的属性值
			List childlist = businessIndicatorService.getChildBusiness(business.getKbId());
			StringBuffer bf = new StringBuffer();			
			bf.append(business.getKbValue());
			bf.append(String.valueOf(childlist.size()+1));
			newIndicator.setKbValue(bf.toString());
			//newIndicator.setKbValue(business.getKbValue()+String.valueOf(childlist.size()+1));
		}
		businessIndicatorService.save(newIndicator);
		if (Messagebox.show("保存成功,是否更新左侧树?", "新建指标", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == 1) {
			Events.postEvent(Events.ON_CHANGE, this, null);
		} else {
			this.detach();
		}
	}
	
	public void onClick$reset() {
		initWindow(business);
	}
	
	public void onClick$close() throws InterruptedException {
		this.detach();
	}

}
