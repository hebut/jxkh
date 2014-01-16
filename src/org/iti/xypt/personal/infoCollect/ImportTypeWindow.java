package org.iti.xypt.personal.infoCollect;
/**
 * <li>��������:�����ϴ�����
 */


import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.iti.xypt.personal.infoCollect.entity.WkTTasktype;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Window;

import com.uniwin.common.util.DateUtil;
import com.uniwin.framework.entity.WkTUser;

public class ImportTypeWindow extends Window implements AfterCompose {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	WkTChanel chanel;
	WkTTasktype type;
	//����ʵ������
	Textbox clist;
	//������
	Tree tree;
	//��ģ��
	TasktypeTreeModel ctm;
	WkTUser user;
	boolean signal=false;	
	Media media;
	WkTUser wkTUser;
	List Medialist,zlist,alist,userDeptList;
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);	
		user=(WkTUser)Sessions.getCurrent().getAttribute("user");	
		StringBuffer buffer=new StringBuffer("����");
		buffer.append("\r\n");		
		buffer.append("  ���˲���");
		buffer.append("\r\n");	
		buffer.append("  ���ײ���");
		//ʾ�����ʼ��
		clist.setValue(buffer.toString());
		clist.addEventListener(Events.ON_CHANGE, new EventListener(){
           
			public void onEvent(Event arg0) throws Exception {
		     signal=true;	
		     }
			});
	}

	public void initWindow(WkTTasktype type) {
		this.type = type;
	}

	public WkTTasktype getEditType() {

		return type;
	}
	public void setMedia(Media media){
		this.media=media;
	}
	public Media getMedia(){
		
	  return media;
	}
	public void setSignal(boolean s){
		this.signal=s;
	}
	
	//�����һ�������Ч��
	public void onClick$next() throws InterruptedException, IOException{

	Sessions.getCurrent().setAttribute("type",type);
		if(signal==false){
			Messagebox.show("�����ʾ���е����ݣ�", "�ϴ�ʧ��", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		String path1=(String)Sessions.getCurrent().getAttribute("path");
		if(path1==null)
		{  
			String path2=Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/task")+"//"+DateUtil.getDateTimeString(new Date())+"_"+"task.txt";
	        FileWriter fw=new FileWriter(path2);//�ļ�������
	       for(int i=0;i<clist.getValue().length();i++){	 
            fw.write(clist.getValue().charAt(i));
	       }
           Sessions.getCurrent().setAttribute("path2",Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/task/"+"//"+DateUtil.getDateTimeString(new Date())+"_"+"task.txt"));
	       fw.close();
		 }
		Executions.getCurrent().setAttribute("type", type);
		this.detach();
		System.out.println("=");
		ViewTypeWindow vw=(ViewTypeWindow)Executions.createComponents("/admin/content/task/view.zul", null, null);
		vw.doHighlighted();	
		vw.addEventListener(Events.ON_CHANGE, new EventListener(){
			public void onEvent(Event arg0) throws Exception {
				refleshTree();			
			}
			
		});
	}
	
	//ȡ�������ϴ�
	public void onClick$cancel(){
		this.detach();
		refleshTree();
		
	}
	//ˢ����
	public void refleshTree(){
		Events.postEvent(Events.ON_CHANGE, this, null);
	}
}
