package org.iti.xypt.ui.base;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class YjfxSelectListbox extends Listbox implements AfterCompose{

	public void afterCompose() {
		Components.wireVariables(this, this);
	}
   
	public void initFxlist(final String yjfx){
		List fxlist=new ArrayList();
	
		
		String[] fx = {"-��ѡ��-","������Ϣ���������ݿ�ϵͳ","ͼ��ͼ�����������Ӿ�","����������������","Ƕ��ʽϵͳ������ֲ�ʽ����","��֪��ѧ���������ܵ�Ӧ��","���������Ϣ��ȫ"};
		for(int i=0;i<fx.length;i++){
			fxlist.add(fx[i]);
		}
		this.setModel(new ListModelList(fxlist));
		this.setSelectedIndex(0);
		this.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
			String fx=(String)arg1;
			arg0.setLabel(fx);
			if(yjfx!=null&&yjfx.equals(fx)){
				arg0.setSelected(true);
			}else if(arg0.getIndex()==0){
				arg0.setSelected(true);
			}
			}
			
		});
		
	}
	
}
