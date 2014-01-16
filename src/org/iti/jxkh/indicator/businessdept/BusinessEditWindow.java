package org.iti.jxkh.indicator.businessdept;


import java.util.Date;
import java.util.List;

import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.service.BusinessIndicatorService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.iti.common.util.ConvertUtil;
import com.uniwin.framework.entity.WkTMlog;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.MLogService;


public class BusinessEditWindow extends Window implements AfterCompose{	
	private static final long serialVersionUID = -1397177361099476338L;
      
    Textbox name,num,desc,index,size;//指标名称、指标序号、指标系数、指标描述、指标量化属性
    Intbox mark;//量化分值
    Row bindex;
    private Radio open,stop;
    Label pselect;
    private Toolbarbutton submit,add,reset,delete;
    
    WkTUser user;
    Jxkh_BusinessIndicator business;
    
    BusinessIndicatorService businessIndicatorService;
    MLogService mlogService;
    
	public void afterCompose(){
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");//获取当前登录用户	
//		pselect.setItemRenderer(new ListitemRenderer() {
//			public void render(Listitem arg0, Object arg1) throws Exception {
//				Jxkh_BusinessIndicator business = (Jxkh_BusinessIndicator)arg1;
//				arg0.setValue(business);
//				arg0.setLabel(business.getKbName());
//			}
//		});		
	}
	/**
	 * 初始化窗体
	 * @param business
	 */
  public void initWindow(Jxkh_BusinessIndicator business)
  {
	  this.business=business;
	//  if(business.getKbPid().toString().equals("0"))  bindex.setVisible(false);
	  if(business.getKbValue().trim().length() > 1) {
		  mark.setReadonly(true);
		  size.setReadonly(true);
	  }
	  name.setValue(business.getKbName());
	  num.setValue(business.getKbOrdno()+"");
	  desc.setValue(business.getKbDesc());
	  mark.setValue(business.getKbScore());
	  index.setValue(business.getKbIndex().toString());
	  size.setValue(business.getKbMeasure());
	  List<Jxkh_BusinessIndicator> blist = businessIndicatorService.findIndicator(business.getKbPid());
	  if(blist.size() != 0) {
		  Jxkh_BusinessIndicator b = blist.get(0);
		  pselect.setValue(b.getKbName());
	  }
	 
	  //实现所属上级指标的数据注入????????????????????????????????????????待解决
	  /**
	   * 待解决
	   */
//	  pselect.initPIndicatorSelect(business);
  }
  /**
   * 重置后初始化窗体
   * @param business
   */
  public void reloadWindow(Jxkh_BusinessIndicator business) {
	  if(business.getKbValue().trim().length() > 1) {
		  mark.setValue(business.getKbScore());		  
		  size.setValue(business.getKbMeasure());
	  }
  }
  	/**
  	 * 添加新指标
  	 * @throws SuspendNotAllowedException
  	 * @throws InterruptedException
  	 */
	public void onClick$add() throws SuspendNotAllowedException, InterruptedException
	{	if(business.getKbLock() == Jxkh_BusinessIndicator.LOCK_YES) {
			Messagebox.show("指标已锁定，不能进行操作，请先解锁！");
			return;
		}
		final BusinessNewWindow w=(BusinessNewWindow) Executions.createComponents("/admin/jxkh/indicator/businessdept/addbusiness.zul", null, null);
		if(business.getKbId() != 0L) {
			w.initWindow(business);
		}
		w.setClosable(true);  
		w.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				refreshTree();
				w.detach();
			}
		});
		w.doModal();
		  
	}	
	/**
	 * 保存
	 * @throws InterruptedException 
	 */
	public void onClick$submit() throws InterruptedException {
		//判断指标是否锁定，如果锁定便不能进行编辑
		if(business.getKbLock() == Jxkh_BusinessIndicator.LOCK_YES) {
			try {
				Messagebox.show("指标已锁定，不能进行操作，请先解锁！");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}else {
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
			business.setKbName(name.getValue().trim());
			business.setKbDesc(desc.getValue());
			business.setKbIndex(Float.valueOf(index.getValue().trim()));
			business.setKbMeasure(size.getValue().trim());
			business.setKbScore(mark.getValue());
			business.setKbTime(ConvertUtil.convertDateString(new Date()));
			if(num.getValue() != null) {
				business.setKbOrdno(Long.valueOf(num.getValue()));
			}
			businessIndicatorService.update(business);
			if (Messagebox.show("保存成功,是否更新左侧树?", "更新指标", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == 1) {
				 refreshTree();
			} else {
				this.detach();
			}
			
		}
	}
	/**
	 * 重置
	 */
	public void onClick$reset() {
		if(business.getKbLock() == Jxkh_BusinessIndicator.LOCK_YES) {
			try {
				Messagebox.show("指标已锁定，不能进行操作，请先解锁！");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		initWindow(business);
	}
	/**
	 * 删除
	 */
	public void onClick$delete() {
		if(business.getKbLock() == Jxkh_BusinessIndicator.LOCK_YES) {
			try {
				Messagebox.show("指标已锁定，不能进行操作，请先解锁！");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		List blist = businessIndicatorService.getChildBusiness(business.getKbId());
		if(blist.size() != 0) {
			try {
				if (Messagebox.show("含有子指标，将一起删除，确定要删除？", "注意", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == 1) {
					deleteTitle(business, blist);
				} else {
					return;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}else {
			try {
				if (Messagebox.show("您确定要删除吗?", "请确认", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == 1) {					
					deleteTitle(business, null);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		refreshTree();
	}
	/**
	 * 启用
	 * @throws InterruptedException 
	 */
	public void onClick$open() throws InterruptedException {
		business.setKbStatus(Jxkh_BusinessIndicator.USE_YES);
		businessIndicatorService.update(business);
		if (Messagebox.show("启用成功,是否更新左侧树?", "更新指标", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == 1) {
			 refreshTree();
		} 
	}
	/**
	 * 停用
	 * @throws InterruptedException 
	 */
	public void onClick$stop() throws InterruptedException {
		business.setKbStatus(Jxkh_BusinessIndicator.USE_NO);
		businessIndicatorService.update(business);
		if (Messagebox.show("停用成功,是否更新左侧树?", "更新指标", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == 1) {
			 refreshTree();
		} 
	}
	
	/**
	 * 级联删除标题，迭代实现
	 * @param business
	 * @param blist
	 */
	public void deleteTitle(Jxkh_BusinessIndicator business,List blist) {
		if (blist != null)
			for (int i = 0; i < blist.size(); i++) {
				Jxkh_BusinessIndicator b=(Jxkh_BusinessIndicator)blist.get(i);
				List bslist = businessIndicatorService.getChildBusiness(b.getKbId());
				deleteTitle(b, bslist);
			}
		mlogService.saveMLog(WkTMlog.FUNC_ADMIN, "删除标题，id:" + business.getKbId(), user);
		businessIndicatorService.delete(business);
	}
	/**
	 * 重新加载树事件
	 */
	private void refreshTree() {
		Events.postEvent(Events.ON_CHANGE, this, null);
	}
	
}