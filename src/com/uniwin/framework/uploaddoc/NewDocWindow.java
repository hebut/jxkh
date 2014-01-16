package com.uniwin.framework.uploaddoc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.iti.xypt.entity.XyUserrole;
import org.iti.xypt.service.XyUserRoleService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.io.Files;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTDoc;
import com.uniwin.framework.entity.WkTDocType;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.entity.WkTTitle;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.DocService;
import com.uniwin.framework.service.DocTypeService;
import com.uniwin.framework.service.RoleService;

public class NewDocWindow extends BaseWindow {

	private static final long serialVersionUID = 1L;
	private WkTUser user;
	Listbox doctype,rolechoose,doclevel;
	List<WkTDept> kudeptlist=new ArrayList<WkTDept>();
	List<Integer> doclevellist;
	Listbox chooseRoleGroup, chooseRole;
	Textbox docinfo;
	Button upload,addtype,deltype;
	private RoleService roleService;
	private XyUserRoleService xyUserRoleService;
	private DocService docService;
	private WkTTitle t;
	Media media;
	private WkTRole wktRole;
	private XyUserrole xyuserrole;
	Label fileup;
	private DocTypeService doctypeService;
	private String realpath;
	private Long filelength=0L;

	public void initShow() {
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		if(t==null){
			t =(WkTTitle) Executions.getCurrent().getAttribute("wktTitle");
			Executions.getCurrent().removeAttribute("wktTitle");
		}
		initnomal();
		initWindow();
	}

	private void intitdoclevel() {
		xyuserrole= (XyUserrole) xyUserRoleService.findByKridAndKuid(wktRole.getKrId(),user.getKuId()).get(0);
		doclevellist=new ArrayList<Integer>();
		if(xyuserrole.getDept().getKdLevel().intValue()==1){
			doclevellist.add(WkTDoc.ULevel);
		}else{
			doclevellist.add(WkTDoc.ULevel);
			doclevellist.add(WkTDoc.CLevel);
			doclevellist.add(WkTDoc.DLevel);
		}
		
		doclevel.setModel(new ListModelList(doclevellist));
		doclevel.setItemRenderer(new ListitemRenderer(){
			public void render(Listitem item, Object data) throws Exception {
				Integer level=(Integer)data;
				switch(level.intValue()){
				case 0: item.setLabel("校级文档");
						item.setSelected(true);
						break;
				case 1: item.setLabel("院级文档");
						break;
				case 2: item.setLabel("系级文档");
						break;
				}
				item.setValue(level);
			}
		});
	}
	private void intitdoctype(){
		List<WkTDocType> list =doctypeService.findByKdid(xyuserrole.getDept().getKdId());
		if(list.size()==0){
			WkTDocType wktdocType1=new WkTDocType();
			wktdocType1.setDoctName("毕设文档");
			wktdocType1.setDoctKdid(xyuserrole.getDept().getKdId());
			doctypeService.save(wktdocType1);
			WkTDocType wktdocType2=new WkTDocType();
			wktdocType2.setDoctName("教学文档");
			wktdocType2.setDoctKdid(xyuserrole.getDept().getKdId());
			doctypeService.save(wktdocType2);
			list.add(wktdocType1);
			list.add(wktdocType2);
		}
		doctype.setModel(new ListModelList(list));
		doctype.setItemRenderer(new ListitemRenderer(){
			public void render(Listitem item, Object data) throws Exception {
				WkTDocType doctype=(WkTDocType)data;
				item.setValue(doctype);
				item.setLabel(doctype.getDoctName());
			}
		});
		doctype.setSelectedIndex(0);
	}
	public void initnomal(){
		rolechoose.setItemRenderer(new ListitemRenderer(){
			public void render(Listitem item, Object data) throws Exception {
				WkTRole wktrolep=(WkTRole)data;
				item.setValue(wktrolep);
				item.setLabel(wktrolep.getKrName());
			}
		});
		
		List grolelist = roleService.getProleOfUser(user.getKuId(), t.getKtId());
		chooseRoleGroup.setModel(new ListModelList(grolelist));
		chooseRoleGroup.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				arg0.setValue(arg1);
				WkTRole role = (WkTRole) arg1;
				arg0.setLabel(role.getKrName());
			}
		});
		
		chooseRole.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				arg0.setValue(arg1);
				XyUserrole xyu = (XyUserrole) arg1;
				arg0.setLabel(xyu.getRole().getKrName());
			}
		});
		chooseRoleGroup.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				changeXyUserroleList(t);
				changeTarget();
				chooseRole.setSelectedIndex(0);
				intitdoclevel();
				intitdoctype();
			}
		});
		chooseRole.setModel(new ListModelList(xyUserRoleService.getUserRoleOfTitle(user.getKuId(), t.getKtId(), getGroupRole().getKrId())));
		chooseRole.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				changeTarget();
				intitdoclevel();
				intitdoctype();
			}
		});
	}
	@Override
	public void initWindow() {
		changeTarget();
		chooseRoleGroup.setSelectedIndex(0);
		chooseRole.setSelectedIndex(0);
		intitdoclevel();
		intitdoctype();
	}
	public void resetWindow(){
		chooseRoleGroup.setSelectedIndex(0);
		Events.postEvent(new Event(Events.ON_SELECT, chooseRoleGroup));
	}
	private void changeXyUserroleList(WkTTitle t) {
		chooseRole.setModel(new ListModelList(xyUserRoleService.getUserRoleOfTitle(user.getKuId(), t.getKtId(), getGroupRole().getKrId())));
	}

	private WkTRole getGroupRole() {
		if (chooseRoleGroup.getSelectedItem() == null) {
			return (WkTRole) chooseRoleGroup.getModel().getElementAt(0);
		} else {
			return (WkTRole) chooseRoleGroup.getSelectedItem().getValue();
		}
	}
	private XyUserrole getRole() {
		if (chooseRole.getSelectedItem() == null) {
			return (XyUserrole) chooseRole.getModel().getElementAt(0);
		} else {
			return (XyUserrole) chooseRole.getSelectedItem().getValue();
		}
	}
	private WkTDocType getType() {
		if (doctype.getSelectedItem() == null) {
			return (WkTDocType) doctype.getModel().getElementAt(0);
		} else {
			return (WkTDocType) doctype.getSelectedItem().getValue();
		}
	}
	/**
	 * 当用户选择发送信息的角色后，如果用户选择的是某些领导角色、任课角色、辅导员、班干部
	 */
	private void changeTarget() {
		wktRole=getRole().getRole();
		wktRole.getKrAdmins();
		List<WkTRole> rolelist=roleService.findbyKrAdmins(wktRole.getKrAdmins());
		rolelist.add((WkTRole) roleService.find("from WkTRole as model where model.krName = ? and model.kdId = ? ", new Object[]{"导师",getRole().getDept().getKdSchid()}).get(0));
		rolechoose.setModel(new ListModelList(rolelist));
	}
	public void onClick$reset() {
		resetWindow();
	}
	public void onClick$upload() throws InterruptedException{
		media = Fileupload.get();
		if(media!=null){
			fileup.setValue(media.getName()+"上传成功");
			fileup.setStyle("color:red");
			fileup.setVisible(true);
			//Messagebox.show("发送成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
		}
	}
	public void onClick$submit() throws InterruptedException, IOException{
		try{
			if(media==null){
				Messagebox.show("请选择要上传的文档！", "提示", Messagebox.OK, Messagebox.INFORMATION);
				return;
			}
			if(rolechoose.getSelectedCount()==0){
				Messagebox.show("请选择文档权限！", "提示", Messagebox.OK, Messagebox.INFORMATION);
				return;
			}
			if(docinfo==null||docinfo.getValue().trim().length()==0){
				Messagebox.show("请填写文档名称！", "提示", Messagebox.OK, Messagebox.INFORMATION);
				return;
			}
			Date date=new Date();
			WkTDoc wt=new WkTDoc();
			wt.setDocKuid(user.getKuId());
			wt.setDocInfo(docinfo.getValue());
			WkTDocType wktdoctype=getType();
			wt.setWktdoctype(wktdoctype);
			Set<WkTRole> wktroles=new HashSet<WkTRole>();
			Iterator items=rolechoose.getSelectedItems().iterator();
			while(items.hasNext()){
				wktroles.add((WkTRole) ((Listitem)items.next()).getValue());
			}
			wt.setDocKdid(xyuserrole.getDept().getKdId());
			wt.setDocLevel((Integer) doclevel.getSelectedItem().getValue());
			wt.setWktroles(wktroles);
			saveToFile(media, date);
			wt.setDocName(media.getName());
			wt.setDocPath(realpath);
			wt.setDocSize(filelength);
			wt.setDoctime(date.getTime());
			wt.setDocKuname(user.getKuName());
			docService.save(wt);
			Messagebox.show("保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			Events.postEvent(new Event(Events.ON_CHANGE, this));
			this.detach();
		}catch(Exception e){
			e.printStackTrace();
			Messagebox.show("保存失败请联系管理员！", "提示", Messagebox.OK, Messagebox.INFORMATION);
		}
		
	}
	public void saveToFile(Media media,Date date) throws IOException{
			String fileName = media.getName();
			String basePath = Executions.getCurrent().getDesktop().getWebApp().getRealPath(WkTDoc.BASE_FILE);
			File folder = new File(basePath);
			if (!folder.exists()) {
				folder.mkdirs();
			}
			String newfilename=fileName.substring(0, fileName.lastIndexOf(".")).trim()
				+date.getTime()+fileName.substring(fileName.lastIndexOf("."));
			String path = folder.getAbsolutePath() + "/" + newfilename;
			realpath=WkTDoc.BASE_FILE+ "/" + newfilename;
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
	public void onClick$deltype(){
		Listitem listitem=doctype.getSelectedItem();
		if(listitem!=null){
			WkTDocType doctype=(WkTDocType)listitem.getValue();
			doctypeService.delete(doctype);
			intitdoctype();
		}
	}
	public void onClick$addtype(){
		NewDocTypeWindow nw=(NewDocTypeWindow)Executions.createComponents("/admin/uploadtempdoc/upload/add/add.zul",null,null);
		nw.setXyUserRole(xyuserrole);
		nw.doHighlighted();
		nw.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				intitdoctype();
			}
		});
	}
}
