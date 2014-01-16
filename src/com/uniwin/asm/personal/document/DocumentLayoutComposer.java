package com.uniwin.asm.personal.document;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.iti.bysj.ui.base.InnerButton;
import org.zkoss.io.Files;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zkex.zul.West;
import org.zkoss.zul.Button;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Window;

import com.uniwin.asm.personal.entity.DocList;
import com.uniwin.asm.personal.entity.DocTree;
import com.uniwin.asm.personal.service.DocListService;
import com.uniwin.asm.personal.service.DocTreeService;
import com.uniwin.common.util.DateUtil;
import com.uniwin.framework.entity.WkTUser;


public class DocumentLayoutComposer extends Window implements AfterCompose  {

	private static Log log=LogFactory.getLog(DocumentLayoutComposer.class);
	private static final long serialVersionUID = 1L;
	West dLeft;
	Tree dTree;
	Panel dpanel;
	Listbox dlist;
	DocTree dt;
	Button newdocument,deldocument,document,edit,submit;
	WkTUser user;
	DocTreeService doctreeService;
	DocListService doclistService;
	DocumentTreeModel dtm;
	Media media;
	Textbox dinfo,nameSearch;
	private Long filelength=0L;
	private String realpath;
	
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		dlist.setPageSize(12);
		dlist.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception {
				final DocList docList=(DocList)data;
				Integer i=item.getIndex()+1;
				Listcell c1 = new Listcell(i.toString());
				Listcell c2= new Listcell(docList.getDlName());
				Listcell c3= new Listcell(((int)(docList.getDlSize()/1024))+"KB");
				Listcell c4=new Listcell(DateUtil.getDate(new Date(docList.getDlTime())));
				Listcell c5=new Listcell();
				InnerButton del = new InnerButton("删除");
				del.addEventListener(Events.ON_CLICK, new EventListener(){
					public void onEvent(Event arg0) throws Exception {
						if(	Messagebox.show("确定删除吗?", "提示", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==1){
							deleteFile(docList.getDlPath());
							dt.setTotleDocument(dt.getTotleDocument()>0?(dt.getTotleDocument()-1):0);
							dt.setTotleSize(dt.getTotleSize()>0?(dt.getTotleSize()-docList.getDlSize()):0L);
							doctreeService.update(dt);
							DocTree ndt=(DocTree)dTree.getSelectedItem().getValue();
							if(dt.getDtId()!=ndt.getDtId()){
								ndt.setTotleDocument(ndt.getTotleDocument()>0?(ndt.getTotleDocument()-1):0);
								ndt.setTotleSize(ndt.getTotleSize()>0?(ndt.getTotleSize()-docList.getDlSize()):0L);
								doctreeService.update(ndt);
							}
							doclistService.delete(docList);
						}
						initWindow();
					}
				});
				Label label=new Label("/");
				Label label2=new Label("/");
				InnerButton down = new InnerButton("下载");
				down.addEventListener(Events.ON_CLICK, new EventListener(){
					public void onEvent(Event arg0) throws Exception {
						downloadFile(docList.getDlPath(),docList.getDlName());
					}
				});
				InnerButton edit=new InnerButton("改名");
				edit.addEventListener(Events.ON_CLICK, new EventListener(){
					public void onEvent(Event arg0) throws Exception {
						Executions.getCurrent().setAttribute("docList", docList);
						NewDocNameWindow nw=(NewDocNameWindow)Executions.createComponents("/admin/personaldocument/editname.zul",null,null);
						nw.addEventListener(Events.ON_CHANGE, new EventListener() {
								public void onEvent(Event arg0) throws Exception {
									initWindow();
								}
							});
						
						nw.doHighlighted();
					}
				});
				c5.appendChild(del);
				c5.appendChild(label);
				c5.appendChild(down);
				c5.appendChild(label2);
				c5.appendChild(edit);
				item.appendChild(c1);
				item.appendChild(c2);
				item.appendChild(c4);
				item.appendChild(c3);
				item.appendChild(c5);
			}
		});
		this.addForward(Events.ON_OK, submit, Events.ON_CLICK);
		initShow();
		initWindow();
	}
	
	public void initShow() {
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		dt = doctreeService.getRoot(user.getKuId());
		if (dt.getDtId() == null) {
			dt.setDtKuid(user.getKuId());
			dt.setDtName(user.getKuName() + "的网盘");
			dt.setDtBz(user.getKuName() + "的网盘");
			dt.setDtpId(Long.valueOf("0"));
			dt.setTotleDocument(0);
			dt.setTotleSize(0L);
			doctreeService.save(dt);
		}
		List<DocTree> list=new ArrayList<DocTree>();
		list.add(dt);
		dTree.setTreeitemRenderer(new DocumentItemRenderer());
		dtm = new DocumentTreeModel(list, doctreeService);
		dTree.setModel(dtm);
		dTree.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				initWindow();
			}
		});
	}
	private void initWindow() {
		DocTree ndt=(DocTree)dTree.getSelectedItem().getValue();
		List<DocList> rootlist=doclistService.findByDtid(ndt.getDtId());
		dlist.setModel(new ListModelList(rootlist));
		dinfo.setValue(ndt.getDtBz());
		edit.setVisible(true);
	}
	private void deleteFile(String relativePath) {
		String filePath = this.getDesktop().getWebApp().getRealPath(relativePath);
		File f = new File(filePath);
		if (f.exists()) {
			f.delete();
		}
	}
	public void downloadFile(String relativePath, String fileName) throws InterruptedException {
		java.io.InputStream is = this.getDesktop().getWebApp().getResourceAsStream(relativePath);
		if (is != null) {
			Filedownload.save(is, "text/html", fileName);
		} else {
			Messagebox.show("服务器不存在此文件 ", "错误", Messagebox.OK, Messagebox.ERROR);
		}
	}
	public void onClick$document(){
		try {
			media = Fileupload.get();
			if(media!=null){
				Date date=new Date();
				saveToFile(media,date);
				DocList doclist=new DocList();
				DocTree ndt=(DocTree)dTree.getSelectedItem().getValue();
				doclist.setDlName(media.getName());
				doclist.setDlTime(date.getTime());
				doclist.setDtId(ndt.getDtId());
				doclist.setDlPath(realpath);
				doclist.setDlSize(filelength);
				doclist.setDlKuid(user.getKuId());
				doclistService.save(doclist);
				dt.setTotleDocument(dt.getTotleDocument()+1);
				dt.setTotleSize(dt.getTotleSize()+doclist.getDlSize());
				doctreeService.update(dt);
				if(dt.getDtId()!=ndt.getDtId()){
					ndt.setTotleDocument(ndt.getTotleDocument()+1);
					ndt.setTotleSize(ndt.getTotleSize()+doclist.getDlSize());
					doctreeService.update(ndt);
				}
				
				Messagebox.show("上传成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
				initWindow();
			}
		} catch (Exception e) {
			try {
				Messagebox.show("上传失败，请联系管理员！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	public void saveToFile(Media media,Date date) throws IOException{
		String fileName = media.getName();
		String basePath = Executions.getCurrent().getDesktop().getWebApp().getRealPath(DocTree.BASE_FILE+"/"+user.getKuId());
		File folder = new File(basePath);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		String newfilename=fileName.substring(0, fileName.lastIndexOf(".")).trim()
			+date.getTime()+fileName.substring(fileName.lastIndexOf("."));
		String path = folder.getAbsolutePath() + "/" + newfilename;
		realpath=DocTree.BASE_FILE+ "/"+user.getKuId()+"/" +newfilename;
		File newFile = new File(path);
		if (newFile.exists()) {
			newFile.delete();
		} else {
			newFile.createNewFile();
		}
		if (fileName.endsWith(".txt") || fileName.endsWith(".project")) {
			Reader r = media.getReaderData();
			File f = new File(path);
			Files.copy(f, r, null);
			Files.close(r);
			filelength=f.length();
		} else {
			OutputStream out = new FileOutputStream(newFile);
			InputStream objin = media.getStreamData();
			byte[] buf = new byte[1024];
			int len;
			long finallen = 0L;
			while ((len = objin.read(buf)) > 0) {
				out.write(buf, 0, len);
				finallen = finallen + len;
			}
			filelength=finallen;
			out.close();
			objin.close();
		}
	}
	public void onClick$edit() throws InterruptedException{
		DocTree ndt=(DocTree)dTree.getSelectedItem().getValue();
		ndt.setDtBz(dinfo.getValue());
		doctreeService.update(ndt);
		Messagebox.show("修改成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
	}
	public void onClick$submit() throws InterruptedException{
		String name=nameSearch.getValue().trim();
		if(name.length()!=0){
			List<DocList> list=doclistService.findByDtnameAndKuid(name, user.getKuId());
			dlist.setModel(new ListModelList(list));
			dinfo.setValue("");
			edit.setVisible(false);
		}else{
			initWindow();
		}
	}
	public void onClick$newdocument(){
		DocTree ndt=(DocTree)dTree.getSelectedItem().getValue();
		NewDocTreeWindow nw=(NewDocTreeWindow)Executions.createComponents("/admin/personaldocument/add.zul",null,null);
		nw.addEventListener(Events.ON_CHANGE, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					initShow();
					initWindow();
				}
			});
		nw.setPid(ndt.getDtId());
		nw.setKuid(user.getKuId());
		nw.doHighlighted();
		
	}
	public void onClick$deldocument() throws InterruptedException{
		DocTree ndt=(DocTree)dTree.getSelectedItem().getValue();
		if(ndt.getDtpId()==0){
			Messagebox.show("根目录不可删除！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}else{
			if (Messagebox.show("您确定删除吗？这个操作会联动删除子文件夹和文件夹下的所有文件（请谨慎使用）",
					"请注意", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == 1) {
				log.info("删除文件");
				deletedir(ndt);
				Messagebox.show("删除成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
				initShow();
				initWindow();
			}else{
				return;
			}
		}
		
	}

	private void deletedir(DocTree ndt) {
		List<DocTree> doctreelist=doctreeService.getChildernDocTree(ndt.getDtId());
		for(int i=0;i<doctreelist.size();i++){
			DocTree doctree=doctreelist.get(i);
			deletedir(doctree);
		}
		List<DocList> doclists=ndt.getDocList();
		for(int i=0;i<doclists.size();i++){
			DocList doclist=doclists.get(i);
			deleteFile(doclist.getDlPath());
			doclistService.delete(doclist);
		}
		doctreeService.delete(ndt);
	}
}
