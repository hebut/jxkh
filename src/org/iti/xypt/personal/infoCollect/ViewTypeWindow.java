package org.iti.xypt.personal.infoCollect;
/**
 * <li>批量上传浏览效果
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.FileReader;

import org.iti.xypt.personal.infoCollect.entity.WkTTasktype;
import org.iti.xypt.personal.infoCollect.service.TaskService;
import org.iti.xypt.personal.infoCollect.service.WebsiteService;
import org.zkoss.io.Files;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTAuth;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.entity.WkTUserole;

public class ViewTypeWindow extends Window implements AfterCompose {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	WkTUser user;
	String filename;
	String path,path2;
	TaskService taskService;
	WebsiteService websiteService;
	WkTTasktype type;
	List clist;
	 public static final Short check=1,uncheck=0;
	TasktypeTreeModel ctm;
	List chlist = new ArrayList();
    Date da=new Date();
	Tree tree;
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		System.out.println("1=");
  	    type=(WkTTasktype)Sessions.getCurrent().getAttribute("type");
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		path = (String) Sessions.getCurrent().getAttribute("path");
		path2= (String) Sessions.getCurrent().getAttribute("path2");
		if(path!=null){			
			try {
				System.out.println("2=");
				ToChanel(ReadFile(path));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
			
				e.printStackTrace();
			}
		}
		else if(path2!=null)
		{
			try {
				System.out.println("3=");
				ToChanel(ReadFile(path2));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("==");
		//列表显示分类上传的效果
		initWindow(chlist);
		
	}

	public void initWindow(List chlist){
		System.out.println("=-");
    	 clist=taskService.getChildType(Long.parseLong("0"));	
		ctm=new TasktypeTreeModel(clist,taskService);
		tree.setModel(ctm);
		tree.setTreeitemRenderer(new TreeitemRenderer(){
			public void render(Treeitem item, Object data) throws Exception {
				WkTTasktype t=(WkTTasktype)data;
				item.setValue(t);
				item.setLabel(t.getKtaName());
				item.setOpen(true);
			}			
		});
		System.out.println("--");
	}

  /**
   * <li>功能描述：根据路径将txt文件中的分类名称读到一个临时列表中
   */
	public List ReadFile(String path) throws IOException {
		System.out.println("++");
		List templist = new ArrayList();
		FileReader file = new FileReader(path);
		BufferedReader buff = new BufferedReader(file);	
		boolean eof = false;
		while (!eof) {	
			String line = buff.readLine();
			if (line == null) {
				eof = true;
				break;
			} else {
				Temp temp = new Temp();		
				int i = 0;
				for (int k = 0; ;k++) {
					if(line.substring(k).startsWith(" ")){
					i=k;
					}else{
						break;
					}
				}
				String cn = line.trim();
				temp.c = i;
				temp.name = cn;
				templist.add(temp);
			}
		}
		return templist;
	}
/**
 * <li>功能描述：将临时列表中的信息转化成分类对象并保存到分类列表中
 */
	public List ToChanel(List templist) throws InterruptedException {
		System.out.println("===");
		if (type instanceof WkTTasktype) {
			type.setDep(0);
			chlist.add(type);	
			}
		for (int i = 0; i < templist.size(); i++) {
			Temp t = (Temp) templist.get(i);
			WkTTasktype nc = new WkTTasktype();
			nc.setKtaName(t.name.trim());
		
			nc.setKtaOrdno(Long.parseLong("10"));
			nc.setKtaAudit(Long.parseLong("1"));
			chlist.add(nc);			
			if (i == 0 && t.c == 0) {
				if(type instanceof WkTTasktype){
				nc.setKtaPid(type.getKtaId());
				nc.setDep(type.getDep());
				taskService.save(nc);
				}
			} else if (i == 0 && t.c>0) {
				if(type instanceof WkTTasktype){
				nc.setKtaPid(type.getKtaId());
				nc.setDep(type.getDep() + 1);
				taskService.save(nc);
				}else{
					 Messagebox.show("第一行不能以空格开头，请检查！", "操作失败", Messagebox.OK, Messagebox.EXCLAMATION);
					 this.detach();
				}
			}else if (i > 0) {
				Temp c = (Temp) templist.get(i - 1);
				WkTTasktype lc=new WkTTasktype();
				if(type instanceof WkTTasktype){
				lc = (WkTTasktype) chlist.get(i);
				}else{
					lc=(WkTTasktype) chlist.get(i-1);
				}
				if (t.c > c.c) {
					//子分类
					nc.setKtaPid(lc.getKtaId());	
					nc.setDep(lc.getDep()+1);
					taskService.save(nc);
				} else if (t.c == c.c) {
					//兄弟分类
					nc.setKtaPid(lc.getKtaPid());
					nc.setDep(lc.getDep());
					taskService.save(nc);
				} else if (t.c < c.c) {
					for (int j = i - 2; j >= 0; j--) {
						Temp sc = (Temp) templist.get(j);
						if (t.c == sc.c&&t.c!=0) {
							//只有找兄弟节点了
							WkTTasktype s=new WkTTasktype();
							if(type instanceof WkTTasktype){
							     s = (WkTTasktype) chlist.get(j+1);
							 }else{
								 s = (WkTTasktype) chlist.get(j);
							 }
							nc.setKtaPid(s.getKtaPid());
							nc.setDep(s.getDep());
							taskService.save(nc);
							break;
						}else if(t.c==0){
							nc.setKtaPid(Long.parseLong("0"));
							nc.setDep(0);
							taskService.save(nc);
							break;						
						}
					}
				}

			}
			System.out.println("=+");
			List rlist=websiteService.getRole(user.getKuId());
			for(int m=0;m<rlist.size();m++)
			{
			WkTAuth au=new WkTAuth();
			au.setKaRid(nc.getKtaId());
			WkTUserole ur=(WkTUserole) rlist.get(m);
			au.setKrId(ur.getId().getKrId());
			au.setKdId(user.getKdId());
			au.setKaType(WkTAuth.TYPE_TASK);
			au.setKaCode1(this.uncheck);
			au.setKaCode2(this.check);
			au.setKaCode3(this.uncheck);
			au.setKaCode(au.getKaCode1() + "" + au.getKaCode2()+""+au.getKaCode3());
			taskService.save(au);
			}
		}
		return chlist;
	}
/**
 * 自定义的一个临时对象，第一个属性代表分类名称之前的空格的数量，第二个属性是分类名称
 */
		private class Temp {
			int c;
			String name;

			public int getC() {
				return c;
			}

			public void setC(int c) {
				this.c = c;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public Temp(int c, String name) {
				super();
				this.c = c;
				this.name = name;

			}

			public Temp() 
			{
				super();
			}

		}
	//"取消"按钮对应的事件，将此次上传的分类删除
	public void onClick$cancel() throws InterruptedException{	
		if(chlist.size()>1){
			//删除保存起来的分类，除当前编辑的以外。
			for(int i=1;i<chlist.size();i++){
				WkTTasktype ct=(WkTTasktype)chlist.get(i);
				taskService.delete(ct);
			}
		}
		if(path!=null)
		{deleteTepfile(path);}
		else if(path2!=null)
		{
			deleteTepfile(path2);
		}
		this.detach();
		Messagebox.show("取消分类导入", "提示：", Messagebox.OK, Messagebox.INFORMATION);
	}
	
	//点击"完成"按钮，当前分类浏览的页面关闭，提示分类上传成功，并刷新左侧树，立即可以看到上传的分类
	public void onClick$finish() throws InterruptedException{
		
		this.detach();
		Messagebox.show("分类导入成功", "提示：", Messagebox.OK, Messagebox.INFORMATION);
		//上传成功，临时文件删除
		if(path!=null)
		{deleteTepfile(path);}
		else if(path2!=null)
		{
			deleteTepfile(path2);
		}
		Events.postEvent(Events.ON_CHANGE,this,null);
		
		
	}
	public void deleteTepfile(String path){
		File f = new File(path);
		Files.deleteAll(f);
		
	}

}
