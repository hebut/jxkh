package org.iti.xypt.personal.infoCollect.newspub;
/**
 * <li>��Ŀ�б����������ҳ��ʹ�ò������ã��������б���������б�
 * @author FengXinhong
 * @2010-3-17
 */

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.iti.xypt.personal.infoCollect.entity.WkTChanel;
import org.iti.xypt.personal.infoCollect.entity.WkTWebsite;
import org.iti.xypt.personal.infoCollect.service.ChanelService;
import org.iti.xypt.personal.infoCollect.service.NewsService;
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




public class ChanelListbox extends Listbox implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ChanelService chanelService;
	WebsiteService websiteService;
	NewsService newsService;
	RoleService roleService;
	ListModelList cmodelList;
	WkTChanel chanel;
	Long chanelID;
	String TopName;
	public void afterCompose() {
		Components.wireVariables(this, this);
	}
	/**
	 * <li>��������������Ŀ�б��������Ŀ��
	 * @param cml ��Ŀ�б�
	 * @param pid ����Ŀ���
	 * @param dep ��Ŀ������
	 * @param chanel  ҳ���е�ǰ��Ŀ�����������ʾȫ����Ŀ������ֻ��ʾһ����Ŀ
	 * void 
	 * @author FengXinhong
	 * @2010-3-17
	 */
	  public void addChanelListBoxItem(ListModelList cml,Long pid,int dep,WkTChanel chanel){
	    	List cList;
	    	if(chanel==null){
	    		cList=chanelService.getChildChanel(pid);		
	    	}else{
	    		cList=chanelService.getChildChanel(pid,chanel.getKcId());
	    	}    	
			for(int i=0;i<cList.size();i++){		
				WkTChanel c=(WkTChanel)cList.get(i);
				c.setDep(dep);
				cml.add(c);
				addChanelListBoxItem(cml,c.getKcId(),dep+1,chanel);
			}
	    }
	  /**
	   * 
	   * @param cml ��Ŀ�б�
	   * @param pid ����Ŀ���
	   * @param dep ��Ŀ������
	   * @param website ��Ŀ����վ��
	   * @author whm
	   * @2010-8-07
	   */
	  public void addChanelListBoxItem1(ListModelList cml,Long pid,int dep,WkTWebsite website){
	    	List cList;
	    	cList=chanelService.getChanelByKwid(pid,website.getKwId());
			for(int i=0;i<cList.size();i++){		
				WkTChanel c=(WkTChanel)cList.get(i);
				c.setDep(dep);
				cml.add(c);
				addChanelListBoxItem1(cml,c.getKcId(),dep+1,website);
			}
	    }
	  /**
	   * <li>������������ʼ����Ŀ��ѡ��������
	     * @param arg
	     * ��ʼ������Ŀѡ��������������Ӧ�ð�����ǰ��Ŀ������Ŀ����Ĭ��ѡ�е�ǰ�༭��Ŀarg�ĸ���Ŀ
	     * @author whm
	     * @2010-8-07
	     */
	  public void initPChanelSelect(final WkTChanel arg){    	
		   cmodelList=new ListModelList();
		   WkTChanel c=new WkTChanel();
			c.setKcId(0L);
			c.setKcName(getTopName());
			c.setDep(0);
			cmodelList.add(c);
			WkTWebsite web=websiteService.findBykwid(arg.getKwId());
			addChanelListBoxItem1(cmodelList,Long.parseLong("0"),0,web);
			this.setModel(cmodelList);
			this.setItemRenderer(new ListitemRenderer(){
		        public void render(Listitem item, Object data) throws Exception {
		        	WkTChanel c=(WkTChanel)data;
						item.setValue(c);	
						int dep=c.getDep();
						String bla="";
						while(dep>0){
						    bla+="��";
							dep--;
						}
						if(arg!=null&&c.getKcId().intValue()==arg.getKcPid().intValue()){
							item.setSelected(true);
						}
						item.setLabel(bla+c.getKcName().trim());
					}
				});
	    }
	  /**
	     * <li>�����������鿴��Ϣʱ��ʼ������Ŀѡ��������ͬʱѡ��arg��Ŀ��
	     * @author whm
	     * @2010-3-20
	     */
	  public void initZChanelSelect(final WkTChanel arg){    	
		   cmodelList=new ListModelList();
		   WkTChanel c=new WkTChanel();
			c.setKcName("��ѡ��");
			c.setDep(0);
			cmodelList.add(c);
			addChanelListBoxItem(cmodelList,Long.parseLong("0"),0,null);
			this.setModel(cmodelList);
			this.setItemRenderer(new ListitemRenderer(){
		        public void render(Listitem item, Object data) throws Exception {
		        	WkTChanel c=(WkTChanel)data;
						item.setValue(c);
						if(arg!=null&&arg.getKcId().equals(c.getKcId()))
						{
							item.setSelected(true);
						}
						int dep=c.getDep();
						String bla="";
						while(dep>0){
						    bla+="��";
							dep--;
						}
						item.setLabel(bla+c.getKcName());
					}
				});
	    }
	  /**
	     * <li>��������������Ŀѡ��������ͬʱѡ��arg��Ŀ��
	     * @author whm
	     * @2010-3-20
	     */
	  public void initZnewChanelSelect(final WkTChanel arg){    	
		   cmodelList=new ListModelList();
		   chanel.setDep(0);
			cmodelList.add(chanel);
			addChanelListBoxItem(cmodelList,Long.parseLong("0"),0,null);
			this.setModel(cmodelList);
			this.setItemRenderer(new ListitemRenderer(){
		        public void render(Listitem item, Object data) throws Exception {
		        	WkTChanel c=(WkTChanel)data;
						item.setValue(c);
						int dep=c.getDep();
						String bla="";
						while(dep>0){
						    bla+="��";
							dep--;
						}
						if(arg!=null&&c.getKcId().intValue()==arg.getKcId().intValue())
						{
							item.setSelected(true);
						}
						item.setLabel(bla+c.getKcName().trim());
					}
				});
	    }
	  public void setChanel(WkTChanel chanel) {
			
			this.chanel = chanel;
		}

		public void setChanelID(Long chanelID) {
			this.chanelID =chanelID;
		} 
		public WkTChanel getChanel() {
			return chanel;
		}
	    /**
	     * <li>������������Ŀѡ��������ͬʱѡ��arg��ɫ�顣
	     * @param arg ��Ϊ��ʱ�����½���Ŀ����Ŀ��Ϊ��ʱ������ʾȫ����Ŀ�б�
	     * void 
	     * @author FengXinhong
	     * @2010-3-17
	     */
	    public void initNewChanelSelect(final WkTChanel arg){
	    	cmodelList=new ListModelList();
	    	WkTChanel c=new WkTChanel();
			c.setKcId(0L);
			c.setKcName(getTopName());
			c.setDep(0);
			cmodelList.add(c);
		  final List clist= chanelService.findAll(WkTChanel.class);
		  if(clist==null||clist.size()==0){
			 addChanelListBoxItem(cmodelList,Long.parseLong("0"),0,c);
		  }else {
			addChanelListBoxItem(cmodelList,Long.parseLong("0"),0,null);
		  }
			this.setModel(cmodelList);
			this.setItemRenderer(new ListitemRenderer(){
		        public void render(Listitem item, Object data) throws Exception {
		        	WkTChanel c=(WkTChanel)data;
						item.setValue(c);
						int dep=c.getDep();
						String bla="";
						while(dep>0){
						    bla+="��";
							dep--;
						}
						if(arg!=null&&c.getKcId().intValue()==arg.getKcId().intValue()){
							item.setSelected(true);
						}else if(arg==null&&(clist==null||clist.size()==0)){
							item.setSelected(true);
						}
						item.setLabel(bla+c.getKcName());
					}
				});
	    }
	    public void initSChanelSelect(WkTWebsite arg,final WkTChanel cc){    	
			   cmodelList=new ListModelList();
			   WkTChanel c=new WkTChanel();
				c.setDep(0);
				addChanelListBoxItem1(cmodelList,Long.parseLong("0"),0,arg);
				this.setModel(cmodelList);
				this.setItemRenderer(new ListitemRenderer(){
			        public void render(Listitem item, Object data) throws Exception {
			        	WkTChanel c=(WkTChanel)data;
							item.setValue(c);
							int dep=c.getDep();
							String bla="";
							while(dep>0){
							    bla+="��";
								dep--;
							}
							if(cc!=null&&c.getKcId().intValue()==cc.getKcId().intValue())
							{
								item.setSelected(true);
							}
							else 	
								{
								List wlist=chanelService.getChanelByKwid(Long.parseLong("0"),c.getKwId());
								WkTChanel cha=(WkTChanel) wlist.get(0);
								if(cha!=null&&c.getKcId().intValue()==cha.getKcId().intValue())
								{
									item.setSelected(true);
								}
								}
							item.setLabel(bla+c.getKcName());
						}
					});
		    }
	    public void initAuthChanelSelect(WkTWebsite arg,final WkTChanel cc,final WkTUser user,final List userDeptList){    	
			   cmodelList=new ListModelList();
			   WkTChanel c=new WkTChanel();
				c.setDep(0);
				addChanelListBoxItem1(cmodelList,Long.parseLong("0"),0,arg);
				this.setModel(cmodelList);
				this.setItemRenderer(new ListitemRenderer(){
			        public void render(final Listitem item, Object data) throws Exception {
			        	WkTChanel c=(WkTChanel)data;
							item.setValue(c);
							int dep=c.getDep();
							String bla="";
							while(dep>0){
							    bla+="��";
								dep--;
							}
							if(cc!=null&&c.getKcId().intValue()==cc.getKcId().intValue())
							{
								item.setSelected(true);
							}
							List rlist=roleService.getUserRoleId(user.getKuId());
							List list=newsService.getChanelOfManage(user, userDeptList,rlist);//����Ȩ�޵���Ŀ�б�
							int count=0;
							for(int i=0;i<list.size();i++)
							{
							WkTChanel chanel=(WkTChanel) list.get(i);
						 	if(chanel.getKcId().toString().trim().equals(c.getKcId().toString().trim()))
						 		count++;
							}
						 	if(count==0)
							{  item.setLabel(bla+c.getKcName());
						        item.setStyle("color:#e0e0e0");
							}
						 	else item.setLabel(bla+c.getKcName());
						   }
						});
		    }
	    public void initSNewChanelSelect(WkTWebsite arg,final WkTChanel  chanel){    	
			   cmodelList=new ListModelList();
			   WkTChanel c=new WkTChanel();
			   c.setKcId(0L);
				c.setKcName(getTopName());
				c.setDep(0);
				cmodelList.add(c);
				addChanelListBoxItem1(cmodelList,Long.parseLong("0"),0,arg);
				this.setModel(cmodelList);
				this.setItemRenderer(new ListitemRenderer(){
			        public void render(Listitem item, Object data) throws Exception {
			        	WkTChanel c=(WkTChanel)data;
							item.setValue(c);
							int dep=c.getDep();
							String bla="";
							while(dep>0){
							    bla+="��";
								dep--;
							}
							item.setLabel(bla+c.getKcName());
							if(chanel!=null&&c.getKcId().intValue()==chanel.getKcId().intValue())
							{
								item.setSelected(true);
							}
						}
					});
		    }
	    /**
	     * 
	     * @return ��Ŀģ���б�
	     */
	    public void initChanelTemp(WkTWebsite website,final File arg,final File carg)
	    {

			String path0=Executions.getCurrent().getDesktop().getWebApp().getRealPath("/admin/styles"+"\\"+arg.getName().trim());
		    File cssFolder = new File(path0);
			File[] folderList = cssFolder.listFiles();
			List fList = new ArrayList();
			if(folderList!=null&&folderList.length>0)
			{  
				for(int i=0;i<folderList.length;i++)
				{ 
					if(folderList[i].exists()&&folderList[i].isFile()){
						String fileName = folderList[i].getName();
						if(fileName.endsWith(".wkt"))
						{
					    fList.add(folderList[i]);
						}
				}	
			}
			}
			ListModelList fListModel = new ListModelList(fList);
			this.setModel(fListModel);
			this.setItemRenderer(new ListitemRenderer(){
		        public void render(Listitem item, Object data) throws Exception {
		        	File f=(File)data;
		        	Integer endIndex = f.getName().indexOf(".wkt");
		        	String wktFilename =f.getName().substring(0,endIndex);
		        	item.setValue(f);
		        	item.setLabel(wktFilename);
		        	if(carg!=null&&wktFilename.equals(carg.getName().trim().substring(0,endIndex))){
						item.setSelected(true);
					}
		        	else 
		        		item.setSelected(true);
		        }
			});
	  
	    }
	    public void initNewsExportChanelSelect(WkTWebsite arg,final WkTChanel  chanel){    	
			   cmodelList=new ListModelList();
				addChanelListBoxItem1(cmodelList,Long.parseLong("0"),0,arg);
				this.setModel(cmodelList);
				this.setItemRenderer(new ListitemRenderer(){
			        public void render(Listitem item, Object data) throws Exception {
			        	WkTChanel c=(WkTChanel)data;
							item.setValue(c);
							int dep=c.getDep();
							String bla="";
							while(dep>0){
							    bla+="��";
								dep--;
							}
							item.setLabel(bla+c.getKcName());
							if(chanel!=null&&c.getKcId().intValue()==chanel.getKcId().intValue())
							{
								item.setStyle("color:#e0e0e0");
							}
							else 
							{

								List wlist=chanelService.getChanelByKwid(Long.parseLong("0"),c.getKwId());
								WkTChanel cha=(WkTChanel) wlist.get(0);
								if(cha!=null&&c.getKcId().intValue()==cha.getKcId().intValue())
								{
									item.setSelected(true);
								}
								
							}
						}
					});
		    }
      //�������Ȩ����Ŀ�б�
	    public void initAuthChanelAudit(WkTWebsite arg,final WkTChanel cc,final WkTUser user,final List userDeptList){    	
			   cmodelList=new ListModelList();
			   WkTChanel c=new WkTChanel();
				c.setDep(0);
				addChanelListBoxItem1(cmodelList,Long.parseLong("0"),0,arg);
				this.setModel(cmodelList);
				this.setItemRenderer(new ListitemRenderer(){
			        public void render(final Listitem item, Object data) throws Exception {
			        	WkTChanel c=(WkTChanel)data;
							item.setValue(c);
							int dep=c.getDep();
							String bla="";
							while(dep>0){
							    bla+="��";
								dep--;
							}
							if(cc!=null&&c.getKcId().intValue()==cc.getKcId().intValue())
							{
								item.setSelected(true);
							}
							List rlist=roleService.getUserRoleId(user.getKuId());
							List list=newsService.getChanelOfAudit(user, userDeptList,rlist);//����Ȩ�޵���Ŀ�б�
							int count=0;
							for(int i=0;i<list.size();i++)
							{
							WkTChanel chanel=(WkTChanel) list.get(i);
						 	if(chanel.getKcId().toString().trim().equals(c.getKcId().toString().trim()))
						 		count++;
							}
						 	if(count==0)
							{  item.setLabel(bla+c.getKcName());
						        item.setStyle("color:#e0e0e0");
							}
						 	else item.setLabel(bla+c.getKcName());
						   }
						});
		    }
		public String getTopName() {
			if(TopName==null){
				return "����";
			}
			return TopName;
		}
		public void setTopName(String topName) {
			this.TopName = topName;
		}
		
	    
}
