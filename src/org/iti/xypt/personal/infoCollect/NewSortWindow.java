package org.iti.xypt.personal.infoCollect;
/**
 * ����·���
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
	//ʹ���Զ����listbox���������ѡ�񸸷���
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
	
   //���������ӵķ�����Ϣ
	public void onClick$save() throws InterruptedException, IOException{
		if(sortname.getValue().equals(""))
		{
			Messagebox.show("�������������");
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
		mlogService.saveMLog(WkTMlog.FUNC_CMS, "�½����࣬id:"+tt.getKtaId(), user);
		try 
		{
			Messagebox.show("����ɹ���", "Information", Messagebox.OK,
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
    //���reset��ť�����ķ���
	public void onClick$reset()
	{
		sortname.setRawValue(null);
		temp.setRawValue(null);
		desc.setRawValue(null);
		orderno.setValue(Integer.parseInt("10"));
		sortlist.initAllTaskSortSelect(null,null);
			
	}
	//�ر�
	public void onClick$back()
	{
		newsort.detach();
	}
	//ˢ�����վ����
	public void refreshTree()
	{
        Events.postEvent(Events.ON_CHANGE, this, null);
    }
}
