package org.iti.jxkh.indicator.businessdept;

import java.util.Date;
import java.util.List;

import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.entity.Jxkh_IndicatorHistory;
import org.iti.jxkh.service.BusinessIndicatorService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.iti.common.util.ConvertUtil;

public class LockWindow extends Window implements AfterCompose {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Radio lock,unlock;
	private Toolbarbutton submit,reset;
	
	BusinessIndicatorService businessIndicatorService;
	
	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);	
		initWindow();
	}
	//判断默认的初始设置值
	public void initWindow() {
		List<Jxkh_BusinessIndicator> list = businessIndicatorService.getChildBusiness(0L);
		if(list.size() != 0) {
			Jxkh_BusinessIndicator b = list.get(0);
			switch(b.getKbLock()) {
			case Jxkh_BusinessIndicator.LOCK_YES:lock.setChecked(true);break;
			case Jxkh_BusinessIndicator.LOCK_NO:unlock.setChecked(true);break;
			}			
		}		
	}
	
	public void onClick$submit() {
		List<Jxkh_BusinessIndicator> list = businessIndicatorService.findAll();		
		if(lock.isChecked() || !unlock.isChecked()) {
			if(list.size() != 0) {
				for(int i=0;i<list.size();i++) {
					Jxkh_BusinessIndicator indicator = list.get(i);
					indicator.setKbLock(Jxkh_BusinessIndicator.LOCK_YES);
					businessIndicatorService.update(indicator);
				}
			}
		}else {
			if(list.size() != 0) {
				for(int i=0;i<list.size();i++) {
					Jxkh_BusinessIndicator indicator = list.get(i);
					indicator.setKbLock(Jxkh_BusinessIndicator.LOCK_NO);
					businessIndicatorService.update(indicator);
				}
			}
			/**
			 * 把指标信息移到指标历史表中
			 */
			Date date = new Date();
			String changeTime = ConvertUtil.convertDateString(date);
			String time = changeTime.substring(0, 4);
			List<Jxkh_IndicatorHistory> historyList = businessIndicatorService.findIndicatorByTime(time);
			
			/**
			 * 如果历史表中已经有当年的数据，则对当年的数据进行更新，否则直接插入进去			
			 */
			//历史表中已有当年数据,先把历史表中的数据删除，然后再将当前数据复制到历史表中
			if(historyList.size() != 0) {
				for(int i=0;i<historyList.size();i++) {
					Jxkh_IndicatorHistory ih = historyList.get(i);					
					businessIndicatorService.delete(ih);					
				}
				insertData(list,changeTime);
			}else {
				//历史表中没有当年数据，直接复制
				insertData(list,changeTime);
			}				
		}
		try {
			Messagebox.show("保存成功");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 将当前所有指标复制到指标历史表中
	 * @param list
	 * @param changeTime
	 */
	public void insertData(List<Jxkh_BusinessIndicator> list,String changeTime) {
		for(int i=0;i<list.size();i++) {
			Jxkh_IndicatorHistory ih = new Jxkh_IndicatorHistory();
			Jxkh_BusinessIndicator jb = list.get(i);
			ih.setJihTime(changeTime);
			ih.setKbId(jb.getKbId());
			ih.setKbPid(jb.getKbPid());
			ih.setKbIndex(jb.getKbIndex());
			ih.setKbMeasure(jb.getKbMeasure());
			ih.setKbName(jb.getKbName());
			ih.setKbScore(jb.getKbScore());
			ih.setKbValue(jb.getKbValue());
			ih.setKbTime(jb.getKbTime());
			businessIndicatorService.save(ih);
		}
	}
	
	public void onClick$reset() {
		lock.setChecked(true);
	}

}
