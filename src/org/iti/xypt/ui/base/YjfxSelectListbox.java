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
	
		
		String[] fx = {"-请选择-","智能信息处理与数据库系统","图形图像处理与计算机视觉","软件工程与软件理论","嵌入式系统与网络分布式计算","认知科学与网络智能的应用","计算机与信息安全"};
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
