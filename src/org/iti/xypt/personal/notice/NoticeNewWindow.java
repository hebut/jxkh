package org.iti.xypt.personal.notice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iti.xypt.entity.Student;
import org.iti.xypt.entity.XyClass;
import org.iti.xypt.entity.XyMessage;
import org.iti.xypt.entity.XyNReceiver;
import org.iti.xypt.entity.XyNReceiverId;
import org.iti.xypt.entity.XyUserrole;
import org.iti.xypt.service.FudaoService;
import org.iti.xypt.service.MessageService;
import org.iti.xypt.service.XyUserRoleService;
import org.zkforge.fckez.FCKeditor;
import org.zkoss.io.Files;
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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.RoleService;

public class NoticeNewWindow extends Window implements AfterCompose {
	public static final Long TITIE_ID = 54L;
	// 消息数据接口
	private MessageService messageService;
	// 消息输入框组件
	private Textbox mlSubject;
	private FCKeditor mlContent;
	// 暂存收件人角色列表
	List receRolelist = new ArrayList();
	private Row rowFile, chooseRow;
	Listbox upList;
	ListModelList fileModel;
	WkTUser wkTUser;
	// 公告修改的按钮
	Button addRole, addDept;
	XyUserRoleService xyUserRoleService;
	RoleService roleService;
	Listbox chooseRoleGroup, chooseRole;
	// 新增加的选择单位功能
	Map rdeptMap = new HashMap();
	Listbox roleListbox;
	Row roleDeptRow, roleRow;
	Textbox roleDept;
	EventListener eventListener;// 当前添加部门班级所用的事件监听
	LeaderDeptListener leaderlistener = new LeaderDeptListener();
	RkjsClassAddListener rkjslistener = new RkjsClassAddListener();
	FdyClassAddListener fdylistener = new FdyClassAddListener();
	FudaoService fudaoService;
	// 新增加关键词2010年8月27日19:51:58
	Combobox keyword;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		wkTUser = (WkTUser) Sessions.getCurrent().getAttribute("user");
		if (wkTUser.getKdId().longValue() == 0) {
			// 超级管理员再此
			addRole.setDisabled(true);
			chooseRow.setVisible(false);
			roleDept.setValue("全体用户");
		}
		upList.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				Media name = (Media) arg1;
				arg0.setValue(arg1);
				arg0.setLabel(name.getName());
			}
		});
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
				changeXyUserroleList();
			}
		});
		chooseRole.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				changeTarget();
			}
		});
		roleListbox.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				arg0.setValue(arg1);
				WkTRole r = (WkTRole) arg1;
				if (r != null)
					arg0.setLabel(r.getKrName());
			}
		});
		roleListbox.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				initRoleDept();
			}
		});
		keyword.setModel(new ListModelList(messageService.getKeywords(wkTUser.getKuId())));
		initWindow();
	}

	/**
	 * 当用户选择发送信息的角色后，如果用户选择的是某些领导角色、任课角色、辅导员、班干部
	 */
	private void changeTarget() {
		WkTRole r = getSelectXyUserRole().getRole();
		if (r.getKrArgs(WkTRole.INDEX_TYPE) == WkTRole.TYPE_LD) {
			// 领导
			roleDept.setRawValue(null);
			receRolelist = new ArrayList();
			roleListbox.setModel(new ListModelList(receRolelist));
			roleRow.setVisible(true);
			roleDeptRow.setVisible(false);
			addDept.setVisible(true);
			if (eventListener != null) {
				addDept.removeEventListener(Events.ON_CLICK, eventListener);
			}
			eventListener = leaderlistener;
			addDept.addEventListener(Events.ON_CLICK, eventListener);
		} else if (r.getKrArgs(WkTRole.INDEX_TYPE) == WkTRole.TYPE_XSGB) {
			// 学生干部,特指班级干部
			WkTRole stu = (WkTRole) xyUserRoleService.get(WkTRole.class, xyUserRoleService.getRoleId("学生", getGroupRole().getKdId()));
			receRolelist = new ArrayList();
			receRolelist.add(stu);
			roleListbox.setModel(new ListModelList(receRolelist));
			List clist = (List) rdeptMap.get(r.getKrId());
			if (clist == null || clist.size() == 0) {
				Student student = (Student) xyUserRoleService.get(Student.class, wkTUser.getKuId());
				List cllist = new ArrayList();
				cllist.add(student.getXyClass());
				rdeptMap.put(stu.getKrId(), cllist);
			}
			roleRow.setVisible(false);
			roleDeptRow.setVisible(true);
			addDept.setVisible(false);
			initRoleDept();
		} else if (r.getKrArgs(WkTRole.INDEX_TYPE) == WkTRole.TYPE_FDY) {
			// 辅导员
			if (eventListener != null) {
				addDept.removeEventListener(Events.ON_CLICK, eventListener);
			}
			eventListener = fdylistener;
			addDept.addEventListener(Events.ON_CLICK, eventListener);
			WkTRole stu = (WkTRole) xyUserRoleService.get(WkTRole.class, xyUserRoleService.getRoleId("学生", getGroupRole().getKdId()));
			receRolelist = new ArrayList();
			receRolelist.add(stu);
			roleListbox.setModel(new ListModelList(receRolelist));
			roleDept.setRawValue(null);
			roleRow.setVisible(false);
			roleDeptRow.setVisible(true);
			addDept.setVisible(true);
		} else if (r.getKrName().trim().equalsIgnoreCase("任课教师")) {
			if (eventListener != null) {
				addDept.removeEventListener(Events.ON_CLICK, eventListener);
			}
			eventListener = rkjslistener;
			addDept.addEventListener(Events.ON_CLICK, eventListener);
			roleDeptRow.setVisible(true);
			WkTRole stu = (WkTRole) xyUserRoleService.get(WkTRole.class, xyUserRoleService.getRoleId("学生", getGroupRole().getKdId()));
			receRolelist = new ArrayList();
			receRolelist.add(stu);
			roleListbox.setModel(new ListModelList(receRolelist));
			roleDept.setRawValue(null);
			roleDeptRow.setVisible(true);
			roleRow.setVisible(false);
			// rdeptMap.put(stu.getKrId(), new ArrayList());
		}
	}

	public void onClick$addRole() {
		// List roleList = roleService.findSelectAdmins(getSelectXyUserRole().getRole().getKdId(), getSelectXyUserRole().getRole().getKrArgs(WkTRole.INDEX_GRADE));
		List roleList = new ArrayList();
		String admins = getSelectXyUserRole().getRole().getKrAdmins();
		while (admins.indexOf(",") != -1) {
			WkTRole role = (WkTRole) roleService.get(WkTRole.class, Long.parseLong(admins.substring(0, admins.indexOf(","))));
			roleList.add(role);
			admins = admins.substring(admins.indexOf(",") + 1);
		}
		if (admins != null && admins.toString().trim().length() != 0) {
			WkTRole role = (WkTRole) roleService.get(WkTRole.class, Long.parseLong(admins));
			roleList.add(role);
		}
		//导师
		roleList.add(roleService.find("from WkTRole as model where model.krName = ? and model.kdId = ? ", new Object[]{"导师",getSelectXyUserRole().getRole().getKdId()}).get(0));
		final NoticeRoleSelectWindow rsw = (NoticeRoleSelectWindow) Executions.createComponents("/admin/personal/notice/new/addRole.zul", null, null);
		rsw.doHighlighted();
		rsw.initWindow(roleList, receRolelist);
		rsw.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				addReceiver(rsw.getSelectedRoleList());
				rsw.detach();
			}
		});
	}

	private void initWindow() {
		roleRow.setVisible(false);
		roleDeptRow.setVisible(false);
		List grolelist = roleService.getProleOfUser(wkTUser.getKuId(), TITIE_ID);
		chooseRoleGroup.setModel(new ListModelList(grolelist));
		changeXyUserroleList();
		fileModel = new ListModelList(new ArrayList());
		upList.setModel(fileModel);
		if (wkTUser.getKdId().longValue() == 0) {
			roleRow.setVisible(false);
		} else {
			changeTarget();
		}
	}

	private void changeXyUserroleList() {
		chooseRole.setModel(new ListModelList(xyUserRoleService.getUserRoleOfTitle(wkTUser.getKuId(), TITIE_ID, getGroupRole().getKrId())));
	}

	private WkTRole getGroupRole() {
		if (chooseRoleGroup.getSelectedItem() == null) {
			return (WkTRole) chooseRoleGroup.getModel().getElementAt(0);
		} else {
			return (WkTRole) chooseRoleGroup.getSelectedItem().getValue();
		}
	}

	private XyUserrole getSelectXyUserRole() {
		if (chooseRole.getSelectedItem() == null) {
			return (XyUserrole) chooseRole.getModel().getElementAt(0);
		} else {
			return (XyUserrole) chooseRole.getSelectedItem().getValue();
		}
	}

	/**
	 * 当选中某个角色，然后选择角色对应的单位时调用
	 * 
	 * @param roleList 部门及班级列表
	 */
	public void addReceiver(List roleList) {
		receRolelist = roleList;
		roleListbox.setModel(new ListModelList(receRolelist));
		for (int i = 0; i < receRolelist.size(); i++) {
			WkTRole r = (WkTRole) receRolelist.get(i);
			List dlist = (List) rdeptMap.get(r.getKrId());
			if (dlist == null) {
				dlist = new ArrayList();
				dlist.add(getSelectXyUserRole().getDept());
				rdeptMap.put(r.getKrId(), dlist);
			}
		}
		initRoleDept();
		roleDeptRow.setVisible(true);
	}

	private WkTRole getRoleSelect() {
		if (roleListbox.getSelectedItem() == null) {
			return (WkTRole) roleListbox.getModel().getElementAt(0);
		} else {
			return (WkTRole) roleListbox.getSelectedItem().getValue();
		}
	}

	private void initRoleDept() {
		WkTRole r;
		WkTRole wkTRole = getSelectXyUserRole().getRole();
		if (wkTRole.getKrName().trim().equalsIgnoreCase("任课教师")) {
			r = (WkTRole) xyUserRoleService.get(WkTRole.class, xyUserRoleService.getRoleId("学生", getGroupRole().getKdId()));
		} else {
			r = getRoleSelect();
		}
		List dlist = (List) rdeptMap.get(r.getKrId());
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < dlist.size(); i++) {
			if (dlist.get(i) instanceof WkTDept) {
				WkTDept d = (WkTDept) dlist.get(i);
				sb.append(d.getKdName().trim());
			} else if (dlist.get(i) instanceof XyClass) {
				XyClass cl = (XyClass) dlist.get(i);
				sb.append(cl.getClName());
			}
			if (i < (dlist.size() - 1)) {
				sb.append(",");
			}
		}
		roleDept.setValue(sb.toString());
	}

	/**
	 * <li>新消息的 “发送” 功能
	 * 
	 * @author bobo 2010-03-01
	 */
	public void onClick$save() throws IOException, InterruptedException {
		if (mlSubject.getValue().equals("") || mlSubject.getValue().length() > 500) {
			throw new WrongValueException(mlSubject, "请您检查标题，标题必须填写，并且不能超过500个汉字！");
		}
		if (roleDept.getValue() == null || roleDept.getValue().length() == 0) {
			if (receRolelist == null || receRolelist.size() == 0) {
				throw new WrongValueException(roleListbox, "请选择角色");
			} else {
				throw new WrongValueException(addDept, "请选择");
			}
		}
		try{
			WkTRole wkTRole = getSelectXyUserRole().getRole();
			if (wkTRole.getKrName().trim().equalsIgnoreCase("任课教师")) {
				WkTRole role = (WkTRole) xyUserRoleService.get(WkTRole.class, xyUserRoleService.getRoleId("学生", getGroupRole().getKdId()));
				receRolelist.clear();
				receRolelist.add(role);
			}
		}catch(java.lang.IndexOutOfBoundsException e)
		{
			
		}
		
		XyMessage msg = new XyMessage();
		msg.setXmKeyword(keyword.getValue());
		msg.setKuId(wkTUser.getKuId());
		msg.setXmSubject(mlSubject.getText());
		msg.setXmSender(wkTUser.getKuName());
		msg.setXmContent(mlContent.getValue());
		msg.setXmReceivers("");
		if (wkTUser.getKdId().longValue() == 0L) {
			msg.setXmType(XyMessage.TYPE_ALL);
			msg.setXmReceivers("全体用户");
		} else {
			msg.setXmType(XyMessage.TYPE_NOTICE);
			msg.setKrId(getSelectXyUserRole().getId().getKrId());
			msg.setKdId(getSelectXyUserRole().getKdId());
		}
		if (fileModel.getInnerList().size() > 0) {
			msg.setXmHfile(XyMessage.HFILE_YES);
		}
		messageService.save(msg);
		if (wkTUser.getKdId().longValue() != 0L) {
			StringBuffer sb = new StringBuffer("");
			for (int i = 0; i < receRolelist.size(); i++) {
				WkTRole r = (WkTRole) receRolelist.get(i);
				sb.append(r.getKrName() + "(");
				List dlist = (List) rdeptMap.get(r.getKrId());
				for (int j = 0; j < dlist.size(); j++) {
					if (dlist.get(j) instanceof WkTDept) {
						WkTDept d = (WkTDept) dlist.get(j);
						XyNReceiverId nrid = new XyNReceiverId(msg.getXmId(), r.getKrId(), d.getKdId(), XyNReceiver.TYPE_KDID);
						XyNReceiver nr = new XyNReceiver(nrid);
						messageService.save(nr);
						sb.append(d.getKdName().trim());
					} else if (dlist.get(j) instanceof XyClass) {
						XyClass cl = (XyClass) dlist.get(j);
						XyNReceiverId nrid = new XyNReceiverId(msg.getXmId(), r.getKrId(), cl.getClId(), XyNReceiver.TYPE_CLID);
						XyNReceiver nr = new XyNReceiver(nrid);
						messageService.save(nr);
						sb.append(cl.getClName());
					}
					if (j < (dlist.size() - 1)) {
						sb.append(",");
					}
				}
				sb.append(")");
				if (i < (receRolelist.size() - 1)) {
					sb.append(",");
				}
			}
			msg.setXmReceivers(sb.toString());
			messageService.update(msg);
		}
		List flist = fileModel.getInnerList();
		for (int i = 0; i < flist.size(); i++) {
			Media media = (Media) flist.get(i);
			saveToFile(media, msg);
		}
		Messagebox.show("发送成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
		onClick$reSend();
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	public void onClick$upFile() throws InterruptedException {
		Object media = Fileupload.get();
		if (media == null) {
			return;
		}
		rowFile.setVisible(true);
		fileModel.add(media);
	}

	/**
	 * <li>使用ZK上传组件，将上传的文件保存到硬盘,获取消息ID号创建文件夹
	 * 
	 * @param Media media 附件
	 * @param String mlid 消息的Id号
	 * @author bobo 2010-4-11
	 */
	public void saveToFile(Media media, XyMessage message) throws IOException {
		if (media != null) {
			String fileName = media.getName();
			String basePath = Executions.getCurrent().getDesktop().getWebApp().getRealPath(XyMessage.BASE_FILE);
			File folder = new File(basePath + "\\" + message.getReleativeFilePath());
			// 如果Mlid文件夹不存在，用于在upload文件下创建Mlid号的文件夹
			if (!folder.exists()) {
				folder.mkdirs();
			}
			String path = folder.getAbsolutePath() + "\\" + fileName;
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
				out.close();
				objin.close();
			}
		}
	}

	/**
	 * <li>删除上传的文件，重新选择
	 * 
	 * @author bobo 2010-4-11
	 */
	public void onClick$deUpload() {
		Listitem it = upList.getSelectedItem();
		if (it == null) {
			if (fileModel.getSize() > 0) {
				it = upList.getItemAtIndex(0);
			}
		}
		if (fileModel.getSize() == 1) {
			fileModel.remove(it.getValue());
			rowFile.setVisible(false);
		} else if (fileModel.getSize() > 1) {
			fileModel.remove(it.getValue());
		}
	}

	/**
	 * <li>页面输入框进行重置
	 * 
	 * @author bobo 2010-4-11
	 */
	public void onClick$reSend() {
		initWindow();
	}

	class FdyClassAddListener implements EventListener {
		public void onEvent(Event arg0) throws Exception {
			List clist = fudaoService.findClassByKuIdAndSchid(wkTUser.getKuId(), getRoleSelect().getKdId());
			final NoticeFdyClassAddWindow nts = (NoticeFdyClassAddWindow) Executions.createComponents("/admin/personal/notice/new/fdyClass.zul", null, null);
			nts.doHighlighted();
			nts.initWindow(clist, (List) rdeptMap.get(getRoleSelect().getKrId()));
			nts.addEventListener(Events.ON_CHANGE, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					rdeptMap.put(getRoleSelect().getKrId(), nts.getSelectedClassList());
					initRoleDept();
					nts.detach();
				}
			});
		}
	}

	/**
	 * 任课教师发送通知，打开班级
	 * 
	 * @author Administrator
	 */
	class RkjsClassAddListener implements EventListener {
		public void onEvent(Event arg0) throws Exception {
			if (getRoleSelect() == null) {
				Messagebox.show("暂无班级！", "提示", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
			final NoticeRkjsTargetSelectWindow nts = (NoticeRkjsTargetSelectWindow) Executions.createComponents("/admin/personal/notice/new/rkjsClass.zul", null, null);
			nts.doHighlighted();
			nts.initWindow((List) rdeptMap.get(getRoleSelect().getKrId()), getSelectXyUserRole());
			nts.addEventListener(Events.ON_CHANGE, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					WkTRole role = (WkTRole) xyUserRoleService.get(WkTRole.class, xyUserRoleService.getRoleId("学生", getGroupRole().getKdId()));
					rdeptMap.put(role.getKrId(), nts.getCllist());
					initRoleDept();
					nts.detach();
				}
			});
		}
	}

	/**
	 * 领导之类发送通知
	 * 
	 * @author Administrator
	 */
	class LeaderDeptListener implements EventListener {
		public void onEvent(Event arg0) throws Exception {
			final NoticeTargetSelectWindow nts = (NoticeTargetSelectWindow) Executions.createComponents("/admin/personal/notice/new/addTarget.zul", null, null);
			nts.doHighlighted();
			nts.initWindow(getSelectXyUserRole(), getRoleSelect(), (List) rdeptMap.get(getRoleSelect().getKrId()));
			nts.addEventListener(Events.ON_CHANGE, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					rdeptMap.put(getRoleSelect().getKrId(), nts.getDeptList());
					initRoleDept();
					nts.detach();
				}
			});
		}
	}
}
