package org.iti.xypt.personal.infoCollect;
/**
 * <li>任务列表组件，根据页面使用参数配置，可以是列表或者下拉列表
 */
import java.util.List;

import org.iti.xypt.personal.infoCollect.entity.WkTTasktype;
import org.iti.xypt.personal.infoCollect.service.TaskService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;






public class TaskListbox extends Listbox implements AfterCompose {

	ListModelList wmodelList;
	TaskService taskService;
	
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
	}
	
	 public void addTaskListBoxItem(ListModelList cml,Long pid,int dep,WkTTasktype tt){
		  	List wList;
		  	if(tt==null){
		  		wList=taskService.getChildType(pid);		
		  	}else{
		  		wList=taskService.getChildTasktype(pid,tt.getKtaId());
		  	}    	
				for(int i=0;i<wList.size();i++){		
					WkTTasktype w=(WkTTasktype)wList.get(i);
					w.setDep(dep);
					cml.add(w);
					addTaskListBoxItem(cml,w.getKtaId(),dep+1,tt);
				}
		  }
	  public void initAllTaskSortSelect(final WkTTasktype arg,final WkTTasktype a){
		  	wmodelList=new ListModelList();
		  	WkTTasktype t=new WkTTasktype();
		  	t.setDep(0);
			 final List wlist= taskService.findAll(WkTTasktype.class);
			  if(wlist==null||wlist.size()==0){
				 addTaskListBoxItem(wmodelList,Long.parseLong("0"),0,arg);
			  }else {
				  addTaskListBoxItem(wmodelList,Long.parseLong("0"),0,null);
			  }
				this.setModel(wmodelList);
				this.setItemRenderer(new ListitemRenderer(){
			        public void render(Listitem item, Object data) throws Exception {
			        	WkTTasktype w=(WkTTasktype)data;		         
			        	   item.setValue(w);
				        	int dep=w.getDep();
							String bla="";
							while(dep>0){
							    bla+="　";
								dep--;
							}
							if(arg!=null&&w.getKtaId().intValue()==arg.getKtaId().intValue()){
								item.setSelected(true);
							}
							if(a!=null&&w.getKtaId().intValue()==a.getKtaId().intValue())
							{
								 item.setStyle("color:#e0e0e0");
							}
							item.setLabel(bla+w.getKtaName());
			        }
					});
		  }

}