package org.iti.xypt.personal.infoCollect.newsaudit;
/**
 * <li>站点列表组件，根据页面使用参数配置，可以是列表或者下拉列表
 * @author whm
 * @2010-7-20
 */

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.iti.xypt.personal.infoCollect.entity.WkTWebsite;
import org.iti.xypt.personal.infoCollect.service.WebsiteService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.RoleService;





public class WebsiteListbox extends Listbox implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	WebsiteService websiteService;
	RoleService roleService;
	ListModelList wmodelList;
	WkTWebsite website;
	String TopName;
	public void afterCompose() {
		Components.wireVariables(this, this);
	}
  public void initNewWebsiteSelect(final WkTWebsite arg,WkTUser user,List deptList){
	    	wmodelList=new ListModelList();
	    	WkTWebsite w=new WkTWebsite();
			w.setKwId(0L);
			w.setkwName(getTopName());
			w.setDep(0);
			wmodelList.add(w);
		   final List alist= websiteService.getWebsiteOfUserManage(user, deptList);
		   final List wlist=websiteService.findAll(WkTWebsite.class);
		  if(wlist==null||wlist.size()==0){
			 addWebsiteListBoxItem(wmodelList,Long.parseLong("0"),0,w);
		  }else {
			  addWebsiteListBoxItem(wmodelList,Long.parseLong("0"),0,null);
		  }
			this.setModel(wmodelList);
			this.setItemRenderer(new ListitemRenderer(){
		        public void render(Listitem item, Object data) throws Exception {
		        	WkTWebsite w=(WkTWebsite)data;
						item.setValue(w);
						int dep=w.getDep();
						String bla="";
						while(dep>0){
						    bla+="　";
							dep--;
						}
						if(arg!=null&&w.getKwId().intValue()==arg.getKwId().intValue()){
							item.setSelected(true);
						}else if(arg==null&&(wlist==null||wlist.size()==0)){
							item.setSelected(true);
						}
						item.setLabel(bla+w.getkwName());
						 int count=0;
						 for(int i=0;i<alist.size();i++)
						    {
		    				  WkTWebsite web=(WkTWebsite)alist.get(i);
						    	if(w.getKwId().toString().trim().equals(web.getKwId().toString().trim()))
						    		count++;	
						    }
						 WkTWebsite website=(WkTWebsite) item.getValue();
		 				  if(count==0 && !website.getkwName().trim().equals("顶级")&& !website.getkwName().trim().equals("全部"))
		 				  { 
		 					item.setDisabled(true);
		 					}
					}
				});
	    }
  /**
   * <li>功能描述：初始化站点组选择下拉框
     * @param arg
     * 初始化父站点选择下拉框，下拉框不应该包括当前站点及子站点，且默认选中当前编辑栏目arg的父站点
     * @author whm
     * @2010-7-20
     */
  public void initPWebsiteSelect(final WkTWebsite arg){    	
	   wmodelList=new ListModelList();
	   WkTWebsite w=new WkTWebsite();
		w.setKwId(0L);
		w.setkwName(getTopName());
		w.setDep(0);
		wmodelList.add(w);
		addWebsiteListBoxItem(wmodelList,Long.parseLong("0"),0,arg);
		this.setModel(wmodelList);
		this.setItemRenderer(new ListitemRenderer(){
	        public void render(Listitem item, Object data) throws Exception {
	        	WkTWebsite w=(WkTWebsite)data;
					item.setValue(w);	
					int dep=w.getDep();
					String bla="";
					while(dep>0){
					    bla+="　";
						dep--;
					}
					if(arg!=null&&w.getKwId().intValue()==arg.getkwPid().intValue()){
						item.setSelected(true);
					}
					item.setLabel(bla+w.getkwName().trim());
				}
			});
    }
  public void addWebsiteListBoxItem(ListModelList cml,Long pid,int dep,WkTWebsite website){
  	List wList;
  	if(website==null){
  		wList=websiteService.getChildWebsite(pid);		
  	}else{
  		wList=websiteService.getChildWebsite(pid,website.getKwId());
  	}    	
		for(int i=0;i<wList.size();i++){		
			WkTWebsite w=(WkTWebsite)wList.get(i);
			w.setDep(dep);
			cml.add(w);
			addWebsiteListBoxItem(cml,w.getKwId(),dep+1,website);
		}
  }
  
  public void initWebsiteSelect(WkTUser user,List deptList, final WkTWebsite arg){
		 
	  	wmodelList=new ListModelList();
	  	List rlist=roleService.getUserRoleId(user.getKuId());
	  	List wlist=websiteService.getWebsiteOfManage(user, deptList,rlist);
	  	for(int i=0;i<wlist.size();i++)
	  		{WkTWebsite w=(WkTWebsite)wlist.get(i);
			wmodelList.add(w);}
			this.setModel(wmodelList);
			this.setItemRenderer(new ListitemRenderer(){
		        public void render(Listitem item, Object data) throws Exception {
		        	WkTWebsite w=(WkTWebsite)data;
						item.setValue(w);
						item.setLabel(w.getkwName());	
						if(arg!=null&&w.getKwId().intValue()==arg.getKwId().intValue()){
							item.setSelected(true);
						}
				}
	  });
  }
  public void initCWebsiteSelect(WkTUser user,List deptList, final WkTWebsite arg){
		 
	  	wmodelList=new ListModelList();
	    WkTWebsite web=new WkTWebsite();
		web.setKwId(0L);
		web.setkwName("全部");
		web.setDep(0);
		wmodelList.add(web);
		List rlist=roleService.getUserRoleId(user.getKuId());
	  	List wlist=websiteService.getWebsiteOfManage(user, deptList,rlist);
	  	for(int i=0;i<wlist.size();i++)
	  		{WkTWebsite w=(WkTWebsite)wlist.get(i);
			wmodelList.add(w);}
			this.setModel(wmodelList);
			this.setItemRenderer(new ListitemRenderer(){
		        public void render(Listitem item, Object data) throws Exception {
		        	WkTWebsite w=(WkTWebsite)data;
						item.setValue(w);
						item.setLabel(w.getkwName());	
						if(arg!=null&&w.getKwId().intValue()==arg.getKwId().intValue()){
							item.setSelected(true);
						}
				}
	  });
}
 
  public void initAllWebsiteSelect(final WkTWebsite arg){
  	wmodelList=new ListModelList();
  	WkTWebsite w=new WkTWebsite();
		w.setKwId(0L);
		w.setDep(0);
	   final List wlist= websiteService.findAll(WkTWebsite.class);
	  if(wlist==null||wlist.size()==0){
		 addWebsiteListBoxItem(wmodelList,Long.parseLong("0"),0,w);
	  }else {
		  addWebsiteListBoxItem(wmodelList,Long.parseLong("0"),0,null);
	  }
		this.setModel(wmodelList);
		this.setItemRenderer(new ListitemRenderer(){
	        public void render(Listitem item, Object data) throws Exception {
	        	WkTWebsite w=(WkTWebsite)data;
					item.setValue(w);
					int dep=w.getDep();
					String bla="";
					while(dep>0){
					    bla+="　";
						dep--;
					}
					if(arg!=null&&w.getKwId().intValue()==arg.getKwId().intValue()){
						item.setSelected(true);
					}else if(arg==null&&(wlist==null||wlist.size()==0)){
						item.setSelected(true);
					}
					item.setLabel(bla+w.getkwName());
				}
			});
  }
  public void initFileName(WkTWebsite website,final File arg)
  {
		String path0=Executions.getCurrent().getDesktop().getWebApp().getRealPath("/admin/styles");
	    File cssFolder = new File(path0);
		File[] folderList = cssFolder.listFiles();
		List fList = new ArrayList();
		if(folderList!=null&&folderList.length>0)
		{  
			for(int i=0;i<folderList.length;i++)
			{ 
				if(folderList[i].exists()&&folderList[i].isDirectory()){
				    fList.add(folderList[i]);
			}	
		}
		}
		ListModelList fListModel = new ListModelList(fList);
		this.setModel(fListModel);
		this.setItemRenderer(new ListitemRenderer(){
	        public void render(Listitem item, Object data) throws Exception {
	        	File f=(File)data;
	        	item.setValue(f);
	        	item.setLabel(f.getName());
	        	if(arg!=null&&f.getName().trim().equals(arg.getName().trim())){
					item.setSelected(true);
				}
	        	else if(arg==null)
	        		item.setSelected(true);
	        }
		});
  }
  public void setWebsite(WkTWebsite website) 
  {
		this.website = website;
	}

	public WkTWebsite getWebsite()
	{
		return website;
	} 
	public String getTopName() {
		if(TopName==null){
			return "顶级";
		}
		return TopName;
	}
	public void setTopName(String topName) {
		this.TopName = topName;
	}
}