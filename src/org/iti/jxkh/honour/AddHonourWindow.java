package org.iti.jxkh.honour;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.iti.gh.common.util.MessageBoxshow;
import org.iti.jxkh.business.meeting.UploadBox;
import org.iti.jxkh.entity.Jxkh_Honour;
import org.iti.jxkh.entity.Jxkh_HonourFile;
import org.iti.jxkh.service.RychService;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.iti.common.util.ConvertUtil;
import com.iti.common.util.UploadUtil;
import com.uniwin.framework.entity.WkTUser;

public class AddHonourWindow extends Window implements AfterCompose {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Textbox name,year,dep;
	public Toolbarbutton submit,reset,close;
	private Div upload;
	private Long kuid;
	private List<?> flist = null;
	private Set<Jxkh_HonourFile> fset = new HashSet<Jxkh_HonourFile>();
	UploadBox uploadbox  =  (UploadBox) Executions.createComponents(
			"/admin/personal/basicdata/honour/upload.zul", null, null);	
//	private Component uploadbox1	=  Executions.createComponents(
//			"/admin/personal/basicdata/honour/upload.zul", null, null);	
//	private UploadBox uploadbox;
	short type;
	String mediaName;
	RychService rychService;
	
	Jxkh_Honour rych;
	WkTUser user;
	
	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);		
//		uploadbox=(UploadBox) uploadbox1;
		uploadbox.initWindow();// 把上传的功能加载进来
		upload.appendChild(uploadbox);
		
	}
	
	
	/**
	 * 初始化页面
	 */	
	public void initWindow() {
		List filelist = new ArrayList();
		filelist.clear();
		//如果库中有当前荣誉，则进行编辑初始化页面，否则，进行添加
		/*if(rych != null){
			submit.setVisible(false);
			if(rych.getRyName() != null){
				name.setValue(rych.getRyName() );				
			}else{
				name.setValue("");
			}
			if(rych.getRyYear() != null){
				year.setValue(rych.getRyYear());	
			}else{
				year.setValue("");
			}
			if(rych.getRyDep() != null){
				dep.setValue(rych.getRyDep());	
			}else{
				dep.setValue("");
			}
			
			//初始化集体个人奖励证书附件列表
			Set<?> files = rych.getFile();
			Object[] file = files.toArray();
			ListModelList fileModel1 = new ListModelList(new ArrayList<Object>());
			for (int i = 0; i < file.length; i++) {
				Jxkh_HonourFile f = (Jxkh_HonourFile) file[i];
				File fileout = new File(Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/")+  f.getFilePath());
				System.out.println(fileout.exists());
				try {					
						Media media = new AMedia(fileout, null, "UTF-8");
						fileModel1.add(media);					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			uploadbox.setFileModel(fileModel1);
			uploadbox.initShow();
			uploadbox.up.setVisible(false);
			uploadbox.del.setVisible(false);
//			uploadbox.down.setVisible(true);
			
			
			
			
//			List<Jxkh_HonourFile> list = rychService.findFileByHonour(rych);
//			if(list.size() != 0) {
//				for(int i=0;i<list.size();i++) {
//					Jxkh_HonourFile rfile = list.get(i);
//					filelist.add(rfile);
//				}
//			}
//			file.setModel(new ListModelList(filelist));
			
		}else */
		if(rych == null){
			name.setValue("");
			year.setValue("");
			dep.setValue("");	
//			filelist.clear();
//			file.setModel(new ListModelList(filelist));			
		}else {
			if(rych.getRyName() != null){
				name.setValue(rych.getRyName() );				
			}else{
				name.setValue("");
			}
			if(rych.getRyYear() != null){
				year.setValue(rych.getRyYear());	
			}else{
				year.setValue("");
			}
			if(rych.getRyDep() != null){
				dep.setValue(rych.getRyDep());	
			}else{
				dep.setValue("");
			}
			
			//初始化集体个人奖励证书附件列表
			Set<?> files = rych.getFile();
			Object[] file = files.toArray();
			ListModelList fileModel1 = new ListModelList(new ArrayList<Object>());
			for (int i = 0; i < file.length; i++) {
				Jxkh_HonourFile f = (Jxkh_HonourFile) file[i];
				File fileout = new File(Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/")+  f.getFilePath());
				System.out.println(fileout.exists());
				try {					
						Media media = new AMedia(fileout, null, "UTF-8");
						fileModel1.add(media);					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			uploadbox.setFileModel(fileModel1);
			uploadbox.initShow();
		}
		//file.setSelectedIndex(0);
	}
	/**
	 * 重置
	 */
	public void onClick$reset(){
		initWindow();
	}
//	/**
//	 * 上传文件，只有当点击保存时才将文件上传至服务器上
//	 */	
//	public void onClick$upload() {
//		
//	}
//	/**
//	 * 将文件上传至服务器上
//	 */
//	private void saveFile(Media media) throws InterruptedException, IOException {		
//		if(null==media)
//		{
//			return;
//		}
//		
//		
//		/**
//		 * 
//		 */
//
//		String reportPath = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/honour")
//		+ File.separator+rych.getGhId() ;//荣誉的存储路径，具体为：存储跟目录+荣誉ID
//
//		File pathDir = new File(reportPath);
//		if(!(pathDir.exists()))
//		{
//			boolean isCreate = pathDir.mkdirs();
//			if(isCreate==false)
//			{
//				Messagebox.show("参考文献存储路径创建失败！", "提示", Messagebox.OK, Messagebox.INFORMATION);
//				return;
//			}
//		}	
//		
//		String path = reportPath+ File.separator +mediaName;
//		File archiveFile = new File(path);
//		if(mediaName.endsWith(".txt")||mediaName.endsWith(".project"))
//		{
//			Reader reader = media.getReaderData();
//			Files.copy(archiveFile, reader, "GBK");
//			Files.close(reader);
//		}else{
//			InputStream mediaInput = media.getStreamData(); 
//			FileOutputStream fileOutput = new FileOutputStream(path);
//		    DataOutputStream dataOutput = new DataOutputStream(fileOutput); 
//			Files.copy(dataOutput,mediaInput);
//			if(dataOutput!=null){
//				dataOutput.close();
//			}	
//			if(fileOutput!=null){
//				fileOutput.close();
//			}
//			if(mediaInput!=null){
//				mediaInput.close();
//			}
//		}
//		
//	}
	/**
	 * 下载文件
	 */
//	public void onClick$download() {	
////		if(file.getSelectedItem() != null) {
//			String filename = file.getSelectedItem().getValue().toString().trim();
//			List<Jxkh_HonourFile> list = rychService.findFileByFilename(filename);
//			if(list.size() != 0) {
//				Jxkh_HonourFile rfile = list.get(0);
//				String filepath = rfile.getFilePath();
//				String reportPath =Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/honour")
//						+ File.separator+rych.getGhId() + File.separator + filepath;
//					File reportFile = new File(reportPath);
//					try 
//					{
//						if(!reportFile.exists())
//						{
//							Messagebox.show("文件已经被删除，服务器上已不存在此文件！", "提示", Messagebox.OK, Messagebox.INFORMATION);
//							return;
//						}
//						Filedownload.save(reportFile,null);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					} catch (FileNotFoundException e) {
//						e.printStackTrace();
//					}
//			}			
//		}
//		else {
//			try {
//				Messagebox.show("服务器上已不存在此文件，无法下载！", "提示", Messagebox.OK, Messagebox.INFORMATION);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			return;
//		}
//	}
//	/**
//	 * 删除文件
//	 */
//	public void onClick$delet() {
//		
//	}
	/**
	 * 保存
	 */
	public void onClick$submit(){
		//判断名称是否为空
		if(name.getValue().equalsIgnoreCase("") || name.getValue() == null){
			throw new WrongValueException(name, "请填写荣誉名称！");
		}		
		//判断时间是否为空,以及格式是否正确
		if(year.getValue()==null||"".equals(year.getValue().trim())){
			throw new WrongValueException(year, "您还没有填写年份，格式如2008！");
		}		
		else{
			try{
				String strr = year.getValue().trim();
				if(strr.length() != 4){
					throw new WrongValueException(year, "您输入的时间格式有错误，请您重新填写，格式如2008！");
				}
				}catch(NumberFormatException e){
				throw new WrongValueException(year, "您输入的时间格式有错误，请您重新填写，格式如2008！");
				
			}
		
		}
		List flist = new ArrayList();
		Set<Jxkh_HonourFile> fset = new HashSet<Jxkh_HonourFile>();
		
//		//判断证明材料是否上传
//		if(file.getSelectedItem().getValue() == null) {
//			throw new WrongValueException(file, "您还没有上传荣誉证明材料，请上传！");
//		}
		//false表示是修改，true表示是新建
		boolean index = false; 
		if(rych==null){//说明这是新建的一个项目
			rych = new Jxkh_Honour();
			index = true;
		}
		/*if ((flist = uploadbox.getFileModel().getInnerList()) != null) {
			Jxkh_HonourFile file = new Jxkh_HonourFile();
			for (int n = 0; n < flist.size(); n++) {
				Media media = (Media) flist.get(n);
				if (media != null) {
					try {
						String path = UploadUtil
								.saveFile(
										media,
										"\\jxkh\\honour\\" + rych.getGhId(),
										media.getName().substring(
												0,
												media.getName()
														.lastIndexOf(".")),
										null);
						file.setFileName(media.getName());
						file.setFilePath(path);
						file.setHonour(rych);
						file.setFileDate(ConvertUtil.convertDateString(new Date()));
//						rychService.save(file);
						fset.add(file);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			rych.setFile(fset);
		}*/
		rych.setRyName(name.getValue());
		rych.setRyYear(year.getValue());
		rych.setRyDep(dep.getValue());
		rych.setState(Jxkh_Honour.NONE);
		if(index){
			saveFile(rych,uploadbox,flist,fset);
			rych.setKuId(kuid);
			rychService.saveOrUpdate(rych);
		}else{
			Set<Jxkh_HonourFile> oldFile = rych.getFile();
			if(oldFile != null)
				rychService.deleteAll(oldFile);
			saveFile(rych,uploadbox,flist,fset);
			rychService.update(rych);
		}
		MessageBoxshow.showInfo("添加成功！");
		 this.detach();
		 Events.postEvent(Events.ON_CHANGE, this, null);
	}
	
	private void saveFile(Jxkh_Honour rych,UploadBox uploadbox,List flist,Set<Jxkh_HonourFile> fset) {
		if(rych.getGhId() == null) 
			rychService.save(rych);
		if ((flist = uploadbox.getFileModel().getInnerList()) != null) {
			Jxkh_HonourFile file = new Jxkh_HonourFile();
			for (int n = 0; n < flist.size(); n++) {
				Media media = (Media) flist.get(n);
				if (media != null) {
					try {
						String path = UploadUtil
								.saveFile(
										media,
										"\\jxkh\\honour\\" + rych.getGhId(),
										media.getName().substring(
												0,
												media.getName()
														.lastIndexOf(".")),
										null);
						file.setFileName(media.getName());
						file.setFilePath(path);
						file.setHonour(rych);
						file.setFileDate(ConvertUtil.convertDateString(new Date()));
//						rychService.save(file);
						fset.add(file);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			rych.setFile(fset);
		}
	}
	
	/**
	 * 关闭页面
	 */
	public void onClick$close(){
		this.detach();
	}
	/**
	 * get、set方法
	 * @return
	 */
	public Jxkh_Honour getRych() {
		return rych;
	}

	public void setRych(Jxkh_Honour rych) {
		this.rych = rych;
	}
	public Long getKuid() {
		return kuid;
	}

	public void setKuid(Long kuid) {
		this.kuid = kuid;
	}


	public WkTUser getUser() {
		return user;
	}


	public void setUser(WkTUser user) {
		this.user = user;
	}

}
