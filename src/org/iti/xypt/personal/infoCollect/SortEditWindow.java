package org.iti.xypt.personal.infoCollect;
/**
 * 编辑分类
 */
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.iti.xypt.personal.infoCollect.entity.WkTTasktype;
import org.iti.xypt.personal.infoCollect.service.TaskService;
import org.zkoss.zul.Window;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;

import com.uniwin.framework.entity.WkTMlog;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.MLogService;




public class SortEditWindow extends Window implements AfterCompose {


	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//使用自定义的listbox组件，用于选择父分类
	TaskListbox sortlist;
	Textbox name,temp,desc;
	Intbox orderno;
	Listitem yes,no;
	 WkTUser user;
	 Window sortedit;
	TaskService taskService;
	MLogService mlogService;
	WkTTasktype t;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user=(WkTUser)Sessions.getCurrent().getAttribute("user");
	}
	
	public void initWindow(WkTTasktype t)
	{
		this.t=t;
		WkTTasktype e=taskService.getTpyeById(t.getKtaPid());
		sortlist.initAllTaskSortSelect(e,t);
		name.setValue(t.getKtaName());
		temp.setValue(t.getKtaTemp());
		orderno.setValue(Integer.parseInt(t.getKtaOrdno().toString()));
		if(t.getKtaAudit().toString().trim().equals("0"))
		{
			no.setSelected(true);
		}
		else if(t.getKtaAudit().toString().trim().equals("1"))
		{
			no.setSelected(false);
		}
		desc.setValue(t.getKtaDesc());
	}
	public void onSelect$sortlist() throws InterruptedException
	{
		Listitem item=sortlist.getSelectedItem();
		WkTTasktype task=(WkTTasktype) item.getValue();
		if(task.getKtaId().toString().trim().equals(t.getKtaId().toString().trim()))
		{
//			Messagebox.show("请重新选择！");
			WkTTasktype e=taskService.getTpyeById(t.getKtaPid());
			sortlist.initAllTaskSortSelect(e,t);
		}
	}
	
   //保存分类信息
	public void onClick$save() throws InterruptedException, IOException{
		if(name.getValue().equals(""))
		{
			Messagebox.show("请输入分类名称");
		}
		else
		{
		t.setKtaName(name.getValue());
		t.setKtaOrdno(Long.parseLong(orderno.getValue().toString()));
		if(yes.isSelected())
		{
			t.setKtaAudit(Long.parseLong("1"));
		}
		else if(no.isSelected())
		{
			t.setKtaAudit(Long.parseLong("0"));
		}
		else
		{
			t.setKtaAudit(Long.parseLong("1"));
		}
		t.setKtaDesc(desc.getValue());
		Listitem item=sortlist.getSelectedItem();
		WkTTasktype ta=(WkTTasktype) item.getValue();
		t.setKtaPid(ta.getKtaId());
		t.setKtaTemp(temp.getValue());
		taskService.update(t);
		mlogService.saveMLog(WkTMlog.FUNC_CMS, "编辑分类，id:"+t.getKtaId(), user);
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
		initWindow(t);
	}
	//关闭
	public void onClick$back()
	{
		sortedit.detach();
	}
	//刷新左侧站点树
	public void refreshTree()
	{
        Events.postEvent(Events.ON_CHANGE, this, null);
    }
}
