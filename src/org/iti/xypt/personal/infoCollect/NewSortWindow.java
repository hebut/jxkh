package org.iti.xypt.personal.infoCollect;
/**
 * 添加新分类
 */
import java.io.IOException;

import org.iti.xypt.personal.infoCollect.entity.WkTTasktype;
import org.iti.xypt.personal.infoCollect.service.TaskService;
import org.zkoss.zul.Window;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.uniwin.framework.entity.WkTMlog;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.MLogService;




public class NewSortWindow extends Window implements AfterCompose {


	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//使用自定义的listbox组件，用于选择父分类
	TaskListbox sortlist;
	Textbox sortname,temp,desc;
	Intbox orderno;
	Listitem yes,no;
	 WkTUser user;
	 Window newsort;
	TaskService taskService;
	MLogService mlogService;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user=(WkTUser)Sessions.getCurrent().getAttribute("user");
		
	}
	
	protected void initWindow(WkTTasktype t)
	{
		sortlist.initAllTaskSortSelect(t,null);
	}
	
   //保存新增加的分类信息
	public void onClick$save() throws InterruptedException, IOException{
		if(sortname.getValue().equals(""))
		{
			Messagebox.show("请输入分类名称");
		}
		else
		{
		WkTTasktype tt=new WkTTasktype();
		tt.setKtaName(sortname.getValue());
		tt.setKtaOrdno(Long.parseLong(orderno.getValue().toString()));
		if(yes.isSelected())
		{
			tt.setKtaAudit(Long.parseLong("1"));
		}
		else if(no.isSelected())
		{
			tt.setKtaAudit(Long.parseLong("0"));
		}
		else
		{
			tt.setKtaAudit(Long.parseLong("1"));
		}
		tt.setKtaDesc(desc.getValue());
		Listitem item=sortlist.getSelectedItem();
		if(item!=null)
		{
		WkTTasktype t=(WkTTasktype) item.getValue();
		tt.setKtaPid(t.getKtaId());
		}
		else tt.setKtaPid(Long.parseLong("1"));
		tt.setKtaTemp(temp.getValue());
		taskService.save(tt);
		mlogService.saveMLog(WkTMlog.FUNC_CMS, "新建分类，id:"+tt.getKtaId(), user);
		try 
		{
			Messagebox.show("保存成功！", "Information", Messagebox.OK,
					Messagebox.INFORMATION);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		refreshTree();
		this.detach();
		}
	}
    //点击reset按钮触发的方法
	public void onClick$reset()
	{
		sortname.setRawValue(null);
		temp.setRawValue(null);
		desc.setRawValue(null);
		orderno.setValue(Integer.parseInt("10"));
		sortlist.initAllTaskSortSelect(null,null);
			
	}
	//关闭
	public void onClick$back()
	{
		newsort.detach();
	}
	//刷新左侧站点树
	public void refreshTree()
	{
        Events.postEvent(Events.ON_CHANGE, this, null);
    }
}
