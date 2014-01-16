package com.uniwin.framework.ui.dept;

/**
 * <li>组织结构编辑窗口，对应的页面为admin/system/organize/deptEdit.zul
 * @author DaLei
 * @2010-3-17
 */
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import javax.imageio.ImageIO;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Window;
import com.uniwin.framework.common.listbox.DeptListbox;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTMlog;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.service.MLogService;
import com.uniwin.framework.service.UserService;

public class DepartmentEditWindow extends Window implements AfterCompose {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	WkTDept dept;

	Textbox dname, ddesc;

	Radio bumen, danwei, work, manage;

	DepartmentService departmentService;

	Button addDept, sortDept, deleteDept, upload;
	private Button submit,reset;

	org.zkoss.zul.Image img;

	UserService userService;

	// 上级部门选择组件
	DeptListbox pselect;

	// 部门左侧树
	Tree deptTree;

	// 当前登陆用户
	WkTUser user;

	MLogService mlogService;

	// 毕设新加
	Textbox dnumber, gradeOne, gradeTwo, gradeThr;

	Row gradeNameRow, descRow, logoRow;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
	}

	// 根据父部门控制 单位和部门 单选框
	public void onSelect$pselect() {
		WkTDept sd = (WkTDept) pselect.getSelectedItem().getValue();
		if (sd.getKdType().equalsIgnoreCase(WkTDept.BUMEN)) {
			bumen.setChecked(true);
			danwei.setDisabled(true);
		} else {
			danwei.setDisabled(false);
		}
		if (sd.getKdLevel().intValue() == 0) {
			descRow.setVisible(false);
			gradeNameRow.setVisible(true);
		} else {
			descRow.setVisible(true);
			gradeNameRow.setVisible(false);
		}
	}

	/**
	 * <li>功能描述：保存页面事件处理。 void
	 * 
	 * @author DaLei
	 * @throws InterruptedException
	 */
	public void onClick$submit() throws InterruptedException {
		dept.setKdName(dname.getValue());
		dept.setKdNumber(dnumber.getValue());
		WkTDept pde = (WkTDept) pselect.getSelectedItem().getValue();
		dept.setKdPid(pde.getKdId());
		if (bumen.isChecked()) {
			dept.setKdType(WkTDept.BUMEN);
		} else if (danwei.isChecked()) {
			dept.setKdType(WkTDept.DANWEI);
		} else {
			dept.setKdType(WkTDept.XUEKE);
		}
		if (work.isChecked()) {
			dept.setKdClassify(WkTDept.WORK);
		} else if (manage.isChecked()) {
			dept.setKdClassify(WkTDept.MANAGE);
		}
		if (pde.getKdLevel().longValue() == 0L) {
			if (gradeThr.getValue() == null || gradeThr.getValue().length() == 0) {
				throw new WrongValueException(gradeThr, "不能为空");
			} else if (gradeTwo.getValue() == null || gradeTwo.getValue().length() == 0) {
				throw new WrongValueException(gradeTwo, "不能为空");
			} else if (gradeOne.getValue() == null || gradeOne.getValue().length() == 0) {
				throw new WrongValueException(gradeOne, "不能为空");
			}
			String desc = "," + gradeOne.getValue() + "," + gradeTwo.getValue() + "," + gradeThr.getValue();
			dept.setKdDesc(desc);
			dept.setKdSchid(dept.getKdId());
		} else {
			dept.setKdDesc(ddesc.getValue());
			dept.setKdSchid(pde.getKdSchid());
			WkTDept numDept = departmentService.findByKdnumberAndKdSchid(dnumber.getValue().trim(), dept.getKdSchid());
			if (numDept != null && numDept.getKdId().longValue() != dept.getKdId().longValue()) {
				throw new WrongValueException(dnumber, "编号已经存在,与" + numDept.getKdName() + "相同");
			}
		}
		boolean updateLevel = false;
		if (pde.getKdLevel().intValue() != (dept.getKdLevel().intValue() - 1)) {
			dept.setKdLevel(pde.getKdLevel() + 1);
			updateLevel = true;
		}
		departmentService.update(dept);
		if (updateLevel) {
			releateUpdateDeptLevel(dept);
		}
		if (dept.getKdType().trim().equalsIgnoreCase(WkTDept.BUMEN)) {
			releateUpdateDept(departmentService.getChildDepartment(dept.getKdId(), WkTDept.QUANBU));
		}
		mlogService.saveMLog(WkTMlog.FUNC_ADMIN, "编辑部门，id：" + dept.getKdId(), user);
		Messagebox.show("保存成功!", "编辑部门", Messagebox.OK, Messagebox.INFORMATION);
		sendEvents();
	}

	/**
	 * <li>功能描述：更新列表中组织部门级别。 void
	 * 
	 * @author DaLei
	 */
	private void releateUpdateDeptLevel(WkTDept pdept) {
		List<WkTDept> cdlist = departmentService.getChildDepartment(pdept.getKdId(), WkTDept.QUANBU);
		for (int i = 0; i < cdlist.size(); i++) {
			WkTDept d = (WkTDept) cdlist.get(i);
			d.setKdLevel(pdept.getKdLevel() + 1);
			departmentService.update(d);
			releateUpdateDeptLevel(d);
		}
	}

	/**
	 * <li>功能描述：更新列表中部门及下级单位全部类型为部门。 void
	 * 
	 * @author DaLei
	 */
	private void releateUpdateDept(List<WkTDept> dlist) {
		for (int i = 0; i < dlist.size(); i++) {
			WkTDept d = (WkTDept) dlist.get(i);
			if (d.getKdType().trim().equalsIgnoreCase(WkTDept.DANWEI)) {
				d.setKdType(WkTDept.BUMEN);
				departmentService.update(d);
			}
			releateUpdateDept(departmentService.getChildDepartment(d.getKdId(), WkTDept.QUANBU));
		}
	}

	/**
	 * <li>功能描述：添加组织事件处理 void
	 * 
	 * @author DaLei
	 */
	public void onClick$addDept() {
		final DepartmentNewWindow w = (DepartmentNewWindow) Executions.createComponents("/admin/system/organize/deptNew.zul", null, null);
		w.doHighlighted();
		w.initWindow(getDept());
		w.addEventListener(Events.ON_CHANGE, new EventListener() {

			public void onEvent(Event arg0) throws Exception {
				sendEvents();
				w.detach();
			}
		});
	}

	public void onClick$reset() {
		initWindow(getDept());
	}

	/**
	 * <li>功能描述：调用此方法，则左侧树会刷新，并重新打开当前编辑dept窗口 void
	 * 
	 * @author DaLei
	 */
	public void sendEvents() {
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	/**
	 * <li>功能描述：组织排序事件处理。 void
	 * 
	 * @author DaLei
	 */
	public void onClick$sortDept() {
		final DepartmentSortWindow w = (DepartmentSortWindow) Executions.createComponents("/admin/system/organize/deptSort.zul", null, null);
		w.doHighlighted();
		w.initWindow(getDept());
		w.addEventListener(Events.ON_CHANGE, new EventListener() {

			public void onEvent(Event arg0) throws Exception {
				sendEvents();
				w.detach();
			}
		});
	}

	/**
	 * <li>功能描述：删除组织事件处理函数。 void
	 * 
	 * @author DaLei
	 */
	public void onClick$deleteDept() {
		try {
			if (Messagebox.show("确定删除此部门吗？", "确认", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK) {
				if (departmentService.getChildDepartment(getDept().getKdId(), WkTDept.QUANBU).size() > 0) {
					Messagebox.show("单位包含子单位不能删除", "删除失败", Messagebox.OK, Messagebox.EXCLAMATION);
				} else {
					WkTDept pdept = (WkTDept) departmentService.get(WkTDept.class, dept.getKdPid());
					dept.setKdState(WkTDept.USE_NO);
					dept.setKdPid(5L);
					departmentService.update(dept);
//					departmentService.delete(dept);
					mlogService.saveMLog(WkTMlog.FUNC_ADMIN, "删除组织,id:" + dept.getKdId(), user);
					this.dept = pdept;
					sendEvents();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public WkTDept getDept() {
		return dept;
	}

	/**
	 * <li>功能描述：编辑窗口页面初始化组件
	 * 
	 * @param dept
	 *            void
	 * @author DaLei
	 * @2010-3-17
	 */
	public void initWindow(WkTDept dept) {
		this.dept = dept;
		if(dept.getKdState()==WkTDept.USE_NO) {
			sortDept.setVisible(true);
			deleteDept.setVisible(false);
			addDept.setVisible(false);
			reset.setVisible(false);
			submit.setDisabled(true);
		}else {
			sortDept.setVisible(true);
			deleteDept.setVisible(true);
			addDept.setVisible(true);
			reset.setVisible(true);
			submit.setDisabled(false);
		}
		this.setTitle("编辑组织:" + dept.getKdName());
		dname.setValue(dept.getKdName().trim());
		ddesc.setValue(dept.getKdDesc());
		dnumber.setValue(dept.getKdNumber());
		List listuser = userService.findUserBykdid(dept.getKdId());
		// System.out.println(listuser.size());
		if(deleteDept.isVisible()) {
			if (listuser != null && listuser.size() != 0) {
				deleteDept.setDisabled(true);
			} else {
				deleteDept.setDisabled(false);
			}
		}
		
		WkTDept pdept = (WkTDept) departmentService.get(WkTDept.class, dept.getKdPid());
		if (pdept == null) {
			danwei.setDisabled(false);
		} else if (pdept.getKdType().trim().equalsIgnoreCase(WkTDept.BUMEN)) {
			danwei.setDisabled(true);
		} else {
			danwei.setDisabled(false);
		}
		// 如果选择组织为组织树中根组织，则初始化父组织名称为顶级
		if (dept.getKdId().intValue() == user.getKdId().intValue()) {
			if (pdept != null) {
				pdept.setKdName("顶级");
			}
		} else {
			pdept = (WkTDept) departmentService.get(WkTDept.class, user.getKdId());
		}
		pselect.setRootDept(pdept);
		pselect.setRootID(user.getKdId());
		pselect.initPDeptSelect(dept);
		if (dept.getKdLevel().intValue() == 1 || dept.getKdLevel().intValue() == 2) {
			logoRow.setVisible(true);
		} else {
			logoRow.setVisible(false);
		}
		if (dept.getKdType().equalsIgnoreCase(WkTDept.DANWEI)) {
			danwei.setChecked(true);
			bumen.setChecked(false);
		} else if (dept.getKdType().equalsIgnoreCase(WkTDept.XUEKE)) {
			bumen.setChecked(false);
			danwei.setChecked(false);
		} else {
			bumen.setChecked(true);
			danwei.setChecked(false);
		}
		if (dept.getKdClassify().equalsIgnoreCase(WkTDept.WORK)) {
			work.setChecked(true);
			manage.setChecked(false);
		} else if (dept.getKdClassify().equalsIgnoreCase(WkTDept.MANAGE)) {
			work.setChecked(false);
			manage.setChecked(true);
		}
		if(deleteDept.isVisible()) {
			if (user.getKdId().intValue() == dept.getKdId().intValue()) {
				deleteDept.setVisible(false);				
			} else {
				deleteDept.setVisible(true);				
			}
		}
		if (user.getKdId().intValue() == dept.getKdId().intValue()) {			
			sortDept.setVisible(false);
		} else {			
			sortDept.setVisible(true);
		}
		
		if (dept.getKdLevel().intValue() == 1) {
			descRow.setVisible(false);
			gradeNameRow.setVisible(true);
			if(dept.getKdDesc() != null && !"".equals(dept.getKdDesc())) {
				String[] gradeName = dept.getKdDesc().split(",");
				if (gradeName == null) {
					gradeOne.setValue("");
					gradeTwo.setValue("");
					gradeThr.setValue("");
				} else {
					gradeOne.setValue(gradeName[1]);
					gradeTwo.setValue(gradeName[2]);
					gradeThr.setValue(gradeName[3]);
				}
			}
			
		} else {
			descRow.setVisible(true);
			gradeNameRow.setVisible(false);
		}
		File fileLoge = new File(this.getDesktop().getWebApp().getRealPath("/logo/" + dept.getKdId() + "L.jpg"));
		File fileSimple = new File(this.getDesktop().getWebApp().getRealPath("/logo/" + dept.getKdId() + "S.jpg"));
		String Path = fileSimple.getPath().replace("\\", "/");
		if (fileLoge.exists() && fileSimple.exists()) {
			img.setVisible(true);
			img.setSrc(Path);
			upload.setLabel("重传");
		} else {
			img.setVisible(false);
			upload.setLabel("上传");
		}
	}

	public void onClick$upload() throws InterruptedException, IOException {
		String Path, Type;
		Media media = Fileupload.get();
		if (media != null) {
			Path = this.getDesktop().getWebApp().getRealPath("/logo/" + media.getName());
			Type = media.getName().substring(media.getName().lastIndexOf("."), media.getName().length());
			if (Type.equals(".jpg")) {
				File file = new File(Path);
				InputStream ins = media.getStreamData();
				OutputStream out = new FileOutputStream(file);
				byte[] buf = new byte[1024];
				int len;
				while ((len = ins.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				out.close();
				ins.close();
			} else {
				Messagebox.show("文件类型只能为JPG格式！", "提示", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
		} else {
			Messagebox.show("文件不存在！", "提示", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		File srcfile = new File(Path);
		if (!srcfile.exists()) {
			return;
		}
		Image src = ImageIO.read(srcfile);
		BufferedImage logo = new BufferedImage(1400, 80, BufferedImage.TYPE_INT_RGB);
		logo.getGraphics().drawImage(src.getScaledInstance(1400, 80, Image.SCALE_SMOOTH), 0, 0, null);
		ImageIO.write(logo, Type.substring(1, Type.length()), new File(this.getDesktop().getWebApp().getRealPath("/logo/" + dept.getKdId() + "L.jpg")));
		BufferedImage simple = new BufferedImage(200, 18, BufferedImage.TYPE_INT_RGB);
		simple.getGraphics().drawImage(src.getScaledInstance(200, 18, Image.SCALE_SMOOTH), 0, 0, null);
		ImageIO.write(simple, Type.substring(1, Type.length()), new File(this.getDesktop().getWebApp().getRealPath("/logo/" + dept.getKdId() + "S.jpg")));
		File file = new File(Path);
		if (file.exists())
			file.delete();
		initWindow(dept);
	}
}
