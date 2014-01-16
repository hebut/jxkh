package org.iti.xypt.ui.base;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.iti.bysj.entity.BsDbchoose;
import org.iti.bysj.entity.BsStudent;
import org.iti.bysj.service.BsStudentService;
import org.iti.bysj.service.DbchooseService;
import org.iti.bysj.ui.base.ErrorWindow;
import org.iti.wp.entity.WpPhase;
import org.iti.wp.service.WpPhaseService;
import org.iti.xypt.entity.Student;
import org.iti.xypt.service.StudentService;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.common.util.ConvertUtil;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.service.UserService;


public abstract class FrameworkWindow extends Window implements AfterCompose {
	UserService userService=(UserService)BeanFactory.getBean("userService");
	DepartmentService departmentService=(DepartmentService)BeanFactory.getBean("departmentService");
	StudentService studentService;
	WpPhaseService wpPhaseService=(WpPhaseService)BeanFactory.getBean("wpPhaseService");
	DbchooseService dbchooseService=(DbchooseService)BeanFactory.getBean("dbchooseService");
	BsStudentService bsStudentService=(BsStudentService)BeanFactory.getBean("bsStudentService");
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		initShow();
	}

	public abstract void initShow();

	public abstract void initWindow();

	private boolean rightType(String fileType, String ftype) {
		if (fileType == null || fileType.length() == 0) {
			return true;
		}
		String[] fileTypes = fileType.split(",");
		for (int i = 0; i < fileTypes.length; i++) {
			if (fileTypes[i].equalsIgnoreCase(ftype)) {
				return true;
			}
		}
		return false;
	}

	public void deleteFile(String relativePath) {
		String filePath = this.getDesktop().getWebApp().getRealPath(relativePath);
		File f = new File(filePath);
		if (f.exists()) {
			f.delete();
		}
	}

	/**
	 * 下载文件,默认下载时候显示文件名称
	 * 
	 * @param relativePath文件的相对路径
	 *            ，如upload/files/56/教学大纲.doc
	 * @throws InterruptedException
	 */
	public void downloadFile(String relativePath) throws InterruptedException {	
		File file=new File(this.getDesktop().getWebApp().getRealPath(relativePath));
	String fileName = "";
	if (file.exists()) {
		int i = relativePath.lastIndexOf("\\");
		if (i <= 0) {
			i = relativePath.lastIndexOf("/");
		}
		fileName = relativePath.substring(i + 1);
//		String[] a=fileName.split("_");	
//		if(a.length!=0){
//			fileName=fileName.substring(0, fileName.lastIndexOf("_"))+a[a.length-1].substring(13, a[a.length-1].length());
//		}
		try {
			Filedownload.save(file, fileName);
		} catch (Exception e) {
			
			e.printStackTrace();
			
			System.out.println("111111111111111FrameworkWindow 出力异常");
		}
	} else {
		Messagebox.show("服务器不存在此文件 ", "错误", Messagebox.OK, Messagebox.ERROR);
	}
	}
	/**
	 * 下载文件,默认下载时候显示文件名称
	 * 
	 * @param relativePath文件的相对路径
	 *            ，如upload/files/56/教学大纲.doc
	 * @throws InterruptedException
	 */
	public void downloadFilebs(String relativePath) throws InterruptedException {
		File file=new File(this.getDesktop().getWebApp().getRealPath(relativePath));
		String fileName = "";
		if (file.exists()) {
			int i = relativePath.lastIndexOf("\\");
			if (i <= 0) {
				i = relativePath.lastIndexOf("/");
			}
			fileName = relativePath.substring(i + 1);
			String[] a=fileName.split("_");	
			if(a.length!=0){
    			fileName=fileName.substring(0, fileName.lastIndexOf("_"))+a[a.length-1].substring(13, a[a.length-1].length());
   		}
//			班级+学号+姓名+阶段名称+日期 
    		String b[]=fileName.split("_");
    		List slist=userService.getUsersByLogin(b[b.length-1].substring(0, 6).trim());
//    		System.out.println(userService+"|||"+slist.size()+"HHHjjj");
    		WkTUser student=(WkTUser)slist.get(0);
    		BsStudent stu=(BsStudent)bsStudentService.findByKuid(student.getKuId()).get(0);
    		BsDbchoose dbchoose =(BsDbchoose) dbchooseService.findByBsid(stu.getBsId()).get(0);
    		Student s=studentService.findByKuid(student.getKuId());
    		fileName=student.getKuName()+"_"+b[b.length-1].substring(0, 6)+"_"+dbchoose.getProject().getBprName().trim()+b[0]+a[a.length-1].substring(13, a[a.length-1].length());
//			Filedownload.save(is, "text/html", fileName);
			try {
//				System.out.println(fileName);
//				AMedia media=new AMedia(fileName,"",a[a.length-1].substring(13, a[a.length-1].length()),file,"utf-8");
				Filedownload.save(file, fileName);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		} else {
			Messagebox.show("服务器不存在此文件 ", "错误", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	public void downloadFilewp(String relativePath,Long kdid) throws InterruptedException {
		java.io.InputStream is = this.getDesktop().getWebApp().getResourceAsStream(relativePath);
		String fileName = "";
		if (is != null) {
			int i = relativePath.lastIndexOf("\\");
			if (i <= 0) {
				i = relativePath.lastIndexOf("/");
			}
			fileName = relativePath.substring(i + 1);
			String[] a=fileName.split("_");	
			if(a.length!=0){
    			fileName=fileName.substring(0, fileName.lastIndexOf("_"))+a[a.length-1].substring(13, a[a.length-1].length());
   		} 
//			学校+评审类别+文档名称+提交人 
    		String b[]=fileName.split("_"); 
    		
    		Long wpid=Long.valueOf(b[1].toString().trim());
			WpPhase wp=(WpPhase)wpPhaseService.get(WpPhase.class, wpid);
			WkTDept dept=(WkTDept)departmentService.findByKdnumberAndKdSchid(b[0].trim(), kdid);
			//System.out.println(b[3].trim().substring(0, b[3].trim().lastIndexOf(".")));
			String kulid= b[3].trim().substring(0, b[3].trim().lastIndexOf("."));
			List userlist=userService.loginUser(kulid);
			WkTUser user=(WkTUser)userlist.get(0);
			String hou=b[3].substring(b[3].trim().lastIndexOf(".")); 
			fileName=dept.getKdName().trim()+"_"+wp.getWpName()+"_"+b[2]+"_"+user.getKuName()+hou;
			//System.out.println(fileName);
			try {
				Filedownload.save(is, "text/html", fileName);
			} catch (Exception e) {
				
				e.printStackTrace();
				
				//System.out.println("22222222222222FrameworkWindow files ==== 出力异常");
			}
		} else {
			Messagebox.show("服务器不存在此文件 ", "错误", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * 下载文件
	 * 
	 * @param relativePath文件的相对路径
	 *            ，如upload/files/56/教学大纲.doc
	 * @throws InterruptedException
	 */
	public void downloadFile(String relativePath, String fileName) throws InterruptedException {
		java.io.InputStream is = this.getDesktop().getWebApp().getResourceAsStream(relativePath);
		if (is != null) {
//			Filedownload.save(is, "text/html", fileName);
			
			try {
				Filedownload.save(is, "text/html", fileName);
			} catch (Exception e) {
				
				e.printStackTrace();
				
				System.out.println("333333333333333FrameworkWindow files ==== 出力异常");
			}
		} else {
			Messagebox.show("服务器不存在此文件 ", "错误", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void showErrorPanel(String width, String title, String errorMessage) {
		ErrorWindow ewin = (ErrorWindow) Executions.createComponents("/admin/bysj/common/error.zul", this, null);
		ewin.setTitle(title);
		if (width != null)
			ewin.setWidth(width);
		ewin.setErrorMessage(errorMessage);
	}

	public void showErrorPanel(String width, String title, String errorMessage, Component targetBefore) {
		if (targetBefore == null) {
			showErrorPanel(width, title, errorMessage);
		}
		ErrorWindow ewin = (ErrorWindow) Executions.createComponents("/admin/bysj/common/error.zul", null, null);
		ewin.setTitle(title);
		if (width != null)
			ewin.setWidth(width);
		ewin.setErrorMessage(errorMessage);
		this.insertBefore(ewin, targetBefore);
	}

	/**
	 * 上传文件
	 * 
	 * @param relativePath文件需要保存的路径
	 *            ，如upload/files/56/
	 * @param fileName
	 *            文件的保存名称
	 * @param fileType
	 *            文件的类型限制，以","隔开，如pdf,doc,docx
	 * @param maxSize
	 *            文件大小限制，为M值。目前未实行
	 * @return
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public String uploadFile(String relativePath, String fileName, String fileType, Integer maxSize) throws InterruptedException, IOException {
		if (maxSize == null) {
			Messagebox.show("请设置上传文件最大值！", "注意", Messagebox.OK, Messagebox.ERROR);
			return "";
		}
		Media[] media = Fileupload.get("上传文件类型必须为:" + fileType + ",文件最大" + maxSize + "M", "请选择上传文件", 1);
		if (media != null) {
			String filename = media[0].getName();
			String ftype = "";
			if ((filename != null) && (filename.length() > 0)) {
				int i = filename.lastIndexOf('.');
				if ((i > 0) && (i < (filename.length() - 1))) {
					ftype = filename.substring(i + 1);
				}
			}
			if (!rightType(fileType, ftype)) {
				Messagebox.show("上传文件类型错误！只能为" + fileType.toString(), "注意", Messagebox.OK, Messagebox.ERROR);
				return "";
			}
			InputStream ins = media[0].getStreamData();
			String filePath = this.getDesktop().getWebApp().getRealPath(relativePath);
			File folder = new File(filePath);
			if (!folder.exists()) {
				folder.mkdirs();
			}
			File newFile = new File(filePath + "\\" + fileName + "." + ftype);
			if (newFile.exists()) {
				newFile.delete();
			}
			OutputStream out = new FileOutputStream(newFile);
			byte[] buf = new byte[1024];
			int len;
			long finallen = 0L;
			while ((len = ins.read(buf)) > 0) {
				out.write(buf, 0, len);
				finallen = finallen + len;
			}
			out.close();
			ins.close();
			if (finallen > maxSize * 1024 * 1024) {

				Messagebox.show("上传文件超过最大限制！" + ",文件最大" + maxSize + "M", "注意", Messagebox.OK, Messagebox.ERROR);
				File tempFile = new File(filePath + "\\" + fileName + "." + ftype);
				if (tempFile.exists()) {
					tempFile.delete();
				}
				return "";
			}
			return relativePath + fileName + "." + ftype;
		}
		return "";
	}
}
