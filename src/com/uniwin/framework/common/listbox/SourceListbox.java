package com.uniwin.framework.common.listbox;
/**2010-4-7
 * ��ʼ����Ϣ��Դ�б�
 * @author whm
 */

import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;

import org.iti.xypt.personal.infoExtract.service.NewsService;


public class SourceListbox extends Listbox implements AfterCompose {
	
	private static final long serialVersionUID = 1L;
	NewsService newsService;
	ListModelList cmodelList;
	public void afterCompose() {
		Components.wireVariables(this, this);
	}
	  public void addsourceListBoxItem(ListModelList cml){
		  List cList=new ArrayList();
		  cList=newsService.getInfo();
		  for(int i=0;i<cList.size();i++)
		  {
			  cml.add(cList.get(i));
	      }
	  }		 
	  //�鿴��Ϣ����ʱ��ʼ����Դ�б�
	  public void initSourceSelect()
	  {    	
		   cmodelList=new ListModelList();
		   cmodelList.add("��Դ�б�");
			addsourceListBoxItem(cmodelList);
			this.setModel(cmodelList);
	    }
}
