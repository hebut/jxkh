package com.uniwin.asm.personal.ui.data.teacherinfo;

import java.util.ArrayList;
import java.util.List;

import org.iti.gh.entity.GhFile;
import org.iti.gh.entity.GhHylw;
import org.iti.gh.entity.GhJslw;
import org.iti.gh.entity.GhQklw;
import org.iti.gh.service.GhFileService;
import org.iti.gh.service.HylwService;
import org.iti.gh.service.JslwService;
import org.iti.gh.service.XmzzService;
import org.iti.gh.service.YjfxService;
import org.iti.gh.ui.listbox.YjfxListbox;
import org.iti.xypt.ui.base.FrameworkWindow;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import com.uniwin.framework.common.fileuploadex.DownloadFJ;
import com.uniwin.framework.common.fileuploadex.IsZipExists;
import com.uniwin.framework.common.fileuploadex.UploadFJ;
import com.uniwin.framework.entity.WkTUser;

public class FblwqkWindow extends FrameworkWindow {
	Textbox shijian, kanwu, time, palce, host, all, ename, publish, num, pages, remark, cgmc;
	Label selfs,writer;
	Listbox record;
	YjfxListbox research;
	GhHylw lw;
	Long kuid;
	// 暂存附件
	private Row rowFile;
	Listbox upList;
	ListModelList fileModel;
	XmzzService xmzzService;
	YjfxService yjfxService;
	GhFileService ghfileService;
	JslwService jslwService;
	HylwService hylwService;
	WkTUser user;
	Button downFile, deUpload, upFile, downFileZip, submit;
    Radiogroup zd;
	public void initShow() {
	}

	public void initWindow() {
		List recordtype = new ArrayList();
		String[] Recordtype = { "-请选择-", "SCI(科学引文索引)收录", "EI(工程索引)收录", "ISTP(科技会议录索引 )收录", "CSCD(中国科学引文数据库)收录", "CSSCI(中文社会科学引文索引)收录", "SSCI(社会科学引文索引)收录", "A&HCI(艺术与人文科学引文索引)收录", "《新华文摘》 全文转载", "《中国数学文摘》全文转载", "《中国社会科学文摘》全文转载", "《中国数学》全文转载", "《中国人大复印资料》全文转载", "《全国高等院校文科学报文摘》全文转载", "《新华文摘》摘要转载", "《中国社会科学文摘》摘要转载", "《中国人大复印资料》摘要转载", "《中国数学》摘要转载" };
		for (int i = 0; i < Recordtype.length; i++) {
			recordtype.add(Recordtype[i]);
		}
		record.setModel(new ListModelList(recordtype));
		// 研究方向列表
		// List yjfxlist = yjfxService.findByKuid(user.getKuId());
		// List fx = new ArrayList();
		// if (yjfxlist != null && yjfxlist.size() != 0) {
		// GhYjfx yjfx = (GhYjfx) yjfxlist.get(0);
		// String[] Yjfx = { "--请选择--", yjfx.getYjResearch1(), yjfx.getYjResearch2(), yjfx.getYjResearch3(), yjfx.getYjResearch4(), yjfx.getYjResearch5() };
		// for (int i = 0; i < Yjfx.length; i++) {
		// fx.add(Yjfx[i]);
		// }
		// } else {
		// fx.add("--请选择--");
		// }
		// research.setModel(new ListModelList(fx));
		all.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				List namelist = new ArrayList();
				String str = all.getValue().trim();
				if (str.contains("，")||str.contains(",")) {
					throw new WrongValueException(all, "项目组人员填写错误，请选择顿号！");
				} else {
					while (str.contains("、")) {
						Label lb = new Label(str.substring(0, str.indexOf("、")));
						namelist.add(lb.getValue().trim());
						str = str.substring(str.indexOf("、") + 1, str.length());
					}
					namelist.add(str.trim());
					selfs.setValue(namelist.indexOf(user.getKuName()) + 1+"");
				}
			}
		});
		if (lw != null) {
			// 名称
			cgmc.setValue(lw.getLwMc());
			cgmc.setDisabled(true);
			// 发表时间
			if (lw.getLwFbsj() != null) {
				shijian.setValue(lw.getLwFbsj());
			}
			// 发表的刊物
			if (lw.getLwKw() != null) {
				kanwu.setValue(lw.getLwKw());
			}
			if (lw.getLwTime() != null) {
				time.setValue(lw.getLwTime());
			}
			if (lw.getLwPlace() != null) {
				palce.setValue(lw.getLwPlace());
			}
			if (lw.getLwHost() != null) {
				host.setValue(lw.getLwHost());
			}
			if (lw.getLwEname() != null) {
				ename.setValue(lw.getLwEname());
			}
			if (lw.getLwAll() != null) {
				all.setValue(lw.getLwAll());
			}
			if (lw.getLwPublish() != null) {
				publish.setValue(lw.getLwPublish());
			}
			if (lw.getLwRecord() == null || lw.getLwRecord() == "") {
				record.setSelectedIndex(0);
			} else {
				record.setSelectedIndex(Integer.valueOf(lw.getLwRecord().trim()));
			}
			if (lw.getLwNum() != null) {
				num.setValue(lw.getLwNum());
			} else {
				num.setValue("");
			}
			if (lw.getLwPages() != null) {
				pages.setValue(lw.getLwPages());
			} else {
				pages.setValue("");
			}
			if (lw.getLwRemark() != null) {
				remark.setValue(lw.getLwRemark());
			} else {
				remark.setValue("");
			}
			writer.setValue(lw.getUser().getKuName()); 
			GhJslw gj = jslwService.findByKuidAndLwidAndType(user.getKuId(), lw.getLwId(), GhJslw.KYHY);
			if (gj != null) {
				research.initYjfzList(user.getKuId(), gj.getYjId());
				if (gj.getLwSelfs() != null) {
					selfs.setValue(gj.getLwSelfs()+"");
				} else {
					List namelist = new ArrayList();
					String str = all.getValue().trim();
					while (str.contains("、")) {
						Label lb = new Label(str.substring(0, str.indexOf("、")));
						namelist.add(lb.getValue().trim());
						str = str.substring(str.indexOf("、") + 1, str.length());
					}
					namelist.add(str.trim());
					selfs.setValue(namelist.indexOf(user.getKuName()) + 1+"");
				}
				// 研究方向
				if (gj.getYjId() == null) {
					research.setSelectedIndex(0);
				} else {
					research.setSelectedIndex(Integer.parseInt(gj.getYjId().toString()));
				}
			} else {
				List namelist = new ArrayList();
				String str = all.getValue().trim();
				if (str.contains("，")||str.contains(",")) {
					throw new WrongValueException(all, "项目组人员填写错误，请选择顿号！");
				} else {
					while (str.contains("、")) {
						Label lb = new Label(str.substring(0, str.indexOf("、")));
						namelist.add(lb.getValue().trim());
						str = str.substring(str.indexOf("、") + 1, str.length());
					}
					namelist.add(str.trim());
					selfs.setValue(namelist.indexOf(user.getKuName()) + 1+"");
				}
				research.setSelectedIndex(0);
			}
			if (user.getKuId().intValue() != lw.getKuId().intValue()) {
				shijian.setDisabled(true);
				kanwu.setDisabled(true);
				palce.setDisabled(true);
				time.setDisabled(true);
				host.setDisabled(true);
				all.setDisabled(true);
				ename.setDisabled(true);
				publish.setDisabled(true);
				record.setDisabled(true);
				num.setDisabled(true);
				pages.setDisabled(true);
				deUpload.setDisabled(true);
				upFile.setDisabled(true);
			}
			// if(lw.getAuditState()!=null&&lw.getAuditState().shortValue()==GhLwzl.AUDIT_YES){
			// submit.setDisabled(true);
			// upFile.setDisabled(true);
			// }
		} else {
			cgmc.setValue("");
			shijian.setValue("");
			kanwu.setValue("");
			palce.setValue("");
			time.setValue("");
			host.setValue("");
			all.setValue("");
			selfs.setValue(null);
			research.initYjfzList(user.getKuId(), null);
			publish.setValue("");
			num.setValue("");
			pages.setValue("");
			ename.setValue("");
			remark.setValue(""); 
			downFile.setDisabled(true);
			downFileZip.setDisabled(true);
			deUpload.setDisabled(true);
			writer.setValue(user.getKuName());
		}
		// 附件
		if (lw == null) {// 新添加
			rowFile.setVisible(false);
		} else {// 修改
			List fjList = ghfileService.findByFxmIdandFType(lw.getLwId(), 3);
			if (fjList.size() == 0) {// 无附件
				rowFile.setVisible(false);
				downFileZip.setDisabled(true);
			} else {// 有附件
				// 初始化附件
				downFileZip.setDisabled(false);
				for (int i = 0; i < fjList.size(); i++) {
					UploadFJ ufj = new UploadFJ(false);
					try {
						ufj.ReadFJ(this.getDesktop(), (GhFile) fjList.get(i));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					fileModel.add(ufj);
				}
				upList.setModel(fileModel);
				rowFile.setVisible(true);
			}
		}
		//
	}

	public void onClick$submit() throws InterruptedException {
		if (cgmc.getValue() == null || "".equals(cgmc.getValue().trim())) {
			throw new WrongValueException(cgmc, "您还没有填写论文名称！");
		}
		if (kanwu.getValue() == null || "".equals(kanwu.getValue().trim())) {
			throw new WrongValueException(kanwu, "刊物或会议论文集名称！");
		}
		// 判断全部作者
		Boolean flag = false;
		String[] author = all.getValue().toString().trim().split("、");
		for (int i = 0; i < author.length; i++) {
			if (author[i].trim().equalsIgnoreCase(user.getKuName().trim())) {
				flag = true;
			}
		}
		if (!flag) {
			throw new WrongValueException(all, "作者中没有您的姓名！");
		}
		// 会议时间
		if (time.getValue() == null || "".equals(time.getValue().trim())) {
			throw new WrongValueException(time, "您还没有填写时间，格式如2008/9/28、2008、2008/9！");
		} else {
			try {
				String str = time.getValue().trim();
				if (str.length() < 4) {
					throw new WrongValueException(time, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
				} else if (str.length() == 4 || '/' == str.charAt(4)) {
					String s[] = str.split("/");
					if (s.length == 1 || s.length == 2 || s.length == 3) {
						for (int i = 0; i < s.length; i++) {
							String si = s[i];
							Integer.parseInt(si);
						}
					} else {
						throw new WrongValueException(time, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
					}
				} else {
					throw new WrongValueException(time, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
				}
			} catch (NumberFormatException e) {
				throw new WrongValueException(time, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
			}
		}
		// false表示是修改，true表示是新建
		boolean index = false, owner = false;
		if (lw == null) {// 说明这是新建的一个项目
			lw = new GhHylw();
			index = true;
		}
		if (!time.isDisabled()) {
			// 没有选择项目名称，该项目属于该用户
			owner = true;
		} else {
			owner = false;
		}
		if (index) {
			// 新建论文,两种情况：（一）当前用户输入了论文名称，保存到论文表和教师论文表；（二），用户选择的论文名称，只保存教师论文表
			if (owner) {
				if (hylwService.CheckOnlyOne(null, cgmc.getValue().trim(), GhHylw.KYLW, kanwu.getValue().trim())) {
					Messagebox.show("您提交的会议论文信息已存在，不可以重复添加！", "警告", Messagebox.OK, Messagebox.EXCLAMATION);
					lw = null;
					return;
				}
				lw.setLwMc(cgmc.getValue().trim());
				lw.setLwKw(kanwu.getValue().trim());
				lw.setLwFbsj(shijian.getValue().trim());
				lw.setLwTime(time.getValue().trim());
				lw.setLwPlace(palce.getValue().trim());
				lw.setLwHost(host.getValue().trim());
				lw.setLwAll(all.getValue().trim());
				lw.setLwPublish(publish.getValue().trim());
				lw.setLwRecord(String.valueOf(record.getSelectedIndex()));
				lw.setLwNum(num.getValue().trim());
				lw.setLwPages(pages.getValue().trim());
				lw.setLwRemark(remark.getValue().trim());
				lw.setLwLx(GhHylw.KYLW);
				lw.setKuId(user.getKuId());
				lw.setLwEname(ename.getValue().trim());
				hylwService.save(lw);
				// 附件存储
				UploadFJ.ListModelListUploadCommand(fileModel, lw.getLwId());
				GhJslw jslw = new GhJslw();
				jslw.setJslwtype(GhJslw.KYHY);
				jslw.setKuId(user.getKuId());
				jslw.setLwSelfs(Integer.parseInt(selfs.getValue()));
				jslw.setYjId(Long.valueOf(research.getSelectedIndex()));
				jslw.setLwzlId(lw.getLwId());
				jslwService.save(jslw);
				//
				Messagebox.show("保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			} else {
				GhJslw jslw = new GhJslw();
				jslw.setJslwtype(GhJslw.KYHY);
				jslw.setKuId(user.getKuId());
				jslw.setLwSelfs(Integer.parseInt(selfs.getValue()));
				jslw.setYjId(Long.valueOf(research.getSelectedIndex()));
				jslw.setLwzlId(lw.getLwId());
				jslwService.save(jslw);
				//
				Messagebox.show("保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			}
			Events.postEvent(Events.ON_CHANGE, this, null);
		} else {
			if (user.getKuId().intValue() == lw.getKuId().intValue()) {
				if (hylwService.CheckOnlyOne(lw, cgmc.getValue().trim(), GhHylw.KYLW, kanwu.getValue().trim())) {
					Messagebox.show("您提交的会议论文信息已存在，不可以重复添加！", "警告", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
				lw.setLwKw(kanwu.getValue().trim());
				lw.setLwFbsj(shijian.getValue().trim());
				lw.setLwTime(time.getValue().trim());
				lw.setLwPlace(palce.getValue().trim());
				lw.setLwHost(host.getValue().trim());
				lw.setLwAll(all.getValue().trim());
				lw.setLwPublish(publish.getValue().trim());
				lw.setLwRecord(String.valueOf(record.getSelectedIndex()));
				lw.setLwNum(num.getValue().trim());
				lw.setLwPages(pages.getValue().trim());
				lw.setLwRemark(remark.getValue().trim());
				lw.setLwEname(ename.getValue().trim());
				lw.setAuditState(null);
				lw.setAuditUid(null);
				lw.setAuditReason(null);
				hylwService.update(lw);
				// 附件存储
				UploadFJ.ListModelListUploadCommand(fileModel, lw.getLwId());
				GhJslw jslw = jslwService.findByKuidAndLwidAndType(user.getKuId(), lw.getLwId(), GhJslw.KYHY);
				if (jslw != null) {
					jslw.setLwSelfs(Integer.parseInt(selfs.getValue()));
					jslw.setYjId(Long.valueOf(research.getSelectedIndex()));
					jslwService.update(jslw);
				} else {
					jslw = new GhJslw();
					jslw.setJslwtype(GhJslw.KYHY);
					jslw.setKuId(user.getKuId());
					jslw.setLwSelfs(Integer.parseInt(selfs.getValue()));
					jslw.setYjId(Long.valueOf(research.getSelectedIndex()));
					jslw.setLwzlId(lw.getLwId());
					jslwService.save(jslw);
				}
				Messagebox.show("保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			} else {
				GhJslw jslw = jslwService.findByKuidAndLwidAndType(user.getKuId(), lw.getLwId(), GhJslw.KYHY);
				if (jslw != null) {
					jslw.setLwSelfs(Integer.parseInt(selfs.getValue()));
					jslw.setYjId(Long.valueOf(research.getSelectedIndex()));
					jslwService.update(jslw);
				} else {
					jslw = new GhJslw();
					jslw.setJslwtype(GhJslw.KYHY);
					jslw.setKuId(user.getKuId());
					jslw.setLwSelfs(Integer.parseInt(selfs.getValue()));
					jslw.setYjId(Long.valueOf(research.getSelectedIndex()));
					jslw.setLwzlId(lw.getLwId());
					jslwService.save(jslw);
				}
				Messagebox.show("保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			}
			Events.postEvent(Events.ON_CHANGE, this, null);
		}
	}

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		fileModel = new ListModelList(new ArrayList());
		upList.setModel(fileModel);
		// cgmc.setModel(new ListModelList(hylwService.findAllname(user.getKuId(), user.getKuName(), GhHylw.KYLW, GhJslw.KYHY)));
		// cgmc.setItemRenderer(new ComboitemRenderer() {
		// public void render(Comboitem arg0, Object arg1) throws Exception {
		// Object[] name = (Object[]) arg1;
		// arg0.setWidth("200px");
		// arg0.setValue(name);
		// arg0.setLabel((String) name[0]);
		// }
		// });
	}

	public void onClick$upFile() throws InterruptedException {
		UploadFJ ufj = new UploadFJ(true);
		Media media = ufj.Upload(this.getDesktop(), 3, 10, "附件不能超过10MB", "发表论文情况附件------附件不能超过10MB");
		if (media == null) {
			return;
		}
		rowFile.setVisible(true);
		fileModel.add(ufj);
	}

	/**
	 * <li>删除上传的文件，重新选择
	 */
	public void onClick$deUpload() {
		// 去掉旧的打包附件
		IsZipExists.delZipFile(this.getDesktop().getWebApp().getRealPath("/upload/xkjs/").trim() + "\\" + "_" + this.lw.getLwId() + "_" + ".zip");
		Listitem it = upList.getSelectedItem();
		if (it == null) {
			if (fileModel.getSize() > 0) {
				it = upList.getItemAtIndex(0);
			}
		}
		//
		UploadFJ temp = (UploadFJ) it.getValue();
		//
		if (fileModel.getSize() == 1) {
			//
			temp.DeleteFJ();
			//
			fileModel.remove(it.getValue());
			rowFile.setVisible(false);
		} else if (fileModel.getSize() > 1) {
			//
			temp.DeleteFJ();
			//
			fileModel.remove(it.getValue());
		}
	}

	public void onClick$reset() {
		// 去掉旧的打包附件
		IsZipExists.delZipFile(this.getDesktop().getWebApp().getRealPath("/upload/xkjs/").trim() + "\\" + "_" + this.lw.getLwId() + "_" + ".zip");
		List list = fileModel.getInnerList();
		for (int i = 0; i < list.size(); i++) {
			((UploadFJ) list.get(i)).DeleteFJ();
		}
		initWindow();
		fileModel.clear();
		rowFile.setVisible(false);
	}

	// 打包下载附件
	public void onClick$downFileZip() {
		DownloadFJ.ListModelListDownloadCommand(this.getDesktop(), this.lw.getLwId(), fileModel);
	}

	// 单个文件下载
	public void onClick$downFile() {
		Listitem it = upList.getSelectedItem();
		if (it == null) {
			if (fileModel.getSize() > 0) {
				it = upList.getItemAtIndex(0);
			}
		}
		UploadFJ temp = (UploadFJ) it.getValue();
		DownloadFJ.DownloadCommand(temp);
	}

	public void onChange$cgmc() {
		List list = hylwService.findByMCAndKW(cgmc.getValue(), kanwu.getValue(), GhHylw.KYLW);
		if (list.size() != 0) {
			GhHylw hylw = (GhHylw) list.get(0);
			lw = hylw;
			cgmc.setReadonly(true);
			kanwu.setReadonly(true);
			shijian.setValue(hylw.getLwFbsj() != null ? hylw.getLwFbsj() : "");
			shijian.setReadonly(true);
			palce.setValue(hylw.getLwPlace() != null ? hylw.getLwPlace() : "");
			palce.setReadonly(true);
			time.setValue(hylw.getLwTime() != null ? hylw.getLwTime() : "");
			time.setReadonly(true);
			host.setValue(hylw.getLwHost() != null ? hylw.getLwHost() : "");
			host.setReadonly(true);
			ename.setValue(hylw.getLwEname() != null ? hylw.getLwEname() : "");
			ename.setReadonly(true);
			all.setValue(hylw.getLwAll() != null ? hylw.getLwAll() : "");
			all.setReadonly(true);
			publish.setValue(hylw.getLwPublish() != null ? hylw.getLwPublish() : "");
			publish.setReadonly(true);
			num.setValue(hylw.getLwNum() != null ? hylw.getLwNum() : "");
			num.setReadonly(true);
			pages.setValue(hylw.getLwPages() != null ? hylw.getLwPages() : "");
			pages.setReadonly(true);
			remark.setValue(hylw.getLwRemark() != null ? hylw.getLwRemark() : "");
			remark.setReadonly(true);
			writer.setValue(hylw.getUser().getKuName());
			List namelist = new ArrayList();
			String str = hylw.getLwAll().trim();
			while (str.contains("、")) {
				Label lb = new Label(str.substring(0, str.indexOf("、")));
				namelist.add(lb.getValue().trim());
				str = str.substring(str.indexOf("、") + 1, str.length());
			}
			namelist.add(str.trim());
			selfs.setValue(namelist.indexOf(user.getKuName()) + 1+"");
			if (hylw.getLwRecord() == null || hylw.getLwRecord() == "") {
				record.setSelectedIndex(0);
			} else {
				record.setSelectedIndex(Integer.valueOf(hylw.getLwRecord().trim()));
			}
			record.setDisabled(true);
			// 附件情况
			deUpload.setDisabled(true);
			upFile.setDisabled(true);
			List fjList = ghfileService.findByFxmIdandFType(hylw.getLwId(), 3);
			if (fjList.size() == 0) {// 无附件
				rowFile.setVisible(false);
				upFile.setDisabled(true);
				downFileZip.setDisabled(true);
			} else {
				for (int i = 0; i < fjList.size(); i++) {
					UploadFJ ufj = new UploadFJ(false);
					try {
						ufj.ReadFJ(getDesktop(), (GhFile) fjList.get(i));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					fileModel.add(ufj);
				}
				upList.setModel(fileModel);
				rowFile.setVisible(true);
				upFile.setDisabled(true);
				downFileZip.setDisabled(false);
			}
		} /*else {
			shijian.setValue("");
			shijian.setReadonly(false);
			palce.setValue("");
			palce.setReadonly(false);
			time.setValue("");
			time.setReadonly(false);
			host.setValue("");
			host.setReadonly(false);
			all.setValue("");
			all.setReadonly(false);
			selfs.setValue(null);
			publish.setValue("");
			publish.setReadonly(false);
			num.setValue("");
			num.setReadonly(false);
			pages.setValue("");
			pages.setReadonly(false);
			ename.setValue("");
			ename.setReadonly(false);
			remark.setValue("");
			remark.setReadonly(false);
			record.setSelectedIndex(0);
			record.setDisabled(false);
			upList.getChildren().clear();
			rowFile.setVisible(false);
			upFile.setDisabled(false);
			downFileZip.setDisabled(true);
		}*/
	}

	public void onChange$kanwu() {
		onChange$cgmc();
	}

	public void onClick$close() {
		this.detach();
	}

	public void onClick$choice() {
		final SelectHylwWindow win = (SelectHylwWindow) Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/hylw/selectlw.zul", null, null);
		win.doHighlighted();
		win.setP1(GhHylw.KYLW);
		win.setP2(GhJslw.KYHY);
		win.initWindow();
		win.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				GhHylw hylw = win.getHylw();
				if(!hylw.getLwAll().contains(user.getKuName())){
					Messagebox.show("您不是项目组成员，请联系项目填写人申请加入！", "提示：", Messagebox.OK, Messagebox.EXCLAMATION);
				}
				else{
				lw = hylw;
				cgmc.setValue(hylw.getLwMc());
				kanwu.setValue(hylw.getLwKw());
				cgmc.setReadonly(true);
				kanwu.setReadonly(true);
				shijian.setValue(hylw.getLwFbsj() != null ? hylw.getLwFbsj() : "");
				shijian.setReadonly(true);
				palce.setValue(hylw.getLwPlace() != null ? hylw.getLwPlace() : "");
				palce.setReadonly(true);
				time.setValue(hylw.getLwTime() != null ? hylw.getLwTime() : "");
				time.setReadonly(true);
				host.setValue(hylw.getLwHost() != null ? hylw.getLwHost() : "");
				host.setReadonly(true);
				ename.setValue(hylw.getLwEname() != null ? hylw.getLwEname() : "");
				ename.setReadonly(true);
				all.setValue(hylw.getLwAll() != null ? hylw.getLwAll() : "");
				all.setReadonly(true);
				publish.setValue(hylw.getLwPublish() != null ? hylw.getLwPublish() : "");
				publish.setReadonly(true);
				num.setValue(hylw.getLwNum() != null ? hylw.getLwNum() : "");
				num.setReadonly(true);
				pages.setValue(hylw.getLwPages() != null ? hylw.getLwPages() : "");
				pages.setReadonly(true);
				remark.setValue(hylw.getLwRemark() != null ? hylw.getLwRemark() : "");
				remark.setReadonly(true);
				writer.setValue(hylw.getUser().getKuName());
				List namelist = new ArrayList();
				String str = hylw.getLwAll().trim();
				while (str.contains("、")) {
					Label lb = new Label(str.substring(0, str.indexOf("、")));
					namelist.add(lb.getValue().trim());
					str = str.substring(str.indexOf("、") + 1, str.length());
				}
				namelist.add(str.trim());
				selfs.setValue(namelist.indexOf(user.getKuName()) + 1+"");
				if (hylw.getLwRecord() == null || hylw.getLwRecord() == "") {
					record.setSelectedIndex(0);
				} else {
					record.setSelectedIndex(Integer.valueOf(hylw.getLwRecord().trim()));
				}
				record.setDisabled(true);
				// 附件情况
				deUpload.setDisabled(true);
				upFile.setDisabled(true);
				List fjList = ghfileService.findByFxmIdandFType(hylw.getLwId(), 3);
				if (fjList.size() == 0) {// 无附件
					rowFile.setVisible(false);
					upFile.setDisabled(true);
					downFileZip.setDisabled(true);
				} else {
					for (int i = 0; i < fjList.size(); i++) {
						UploadFJ ufj = new UploadFJ(false);
						try {
							ufj.ReadFJ(getDesktop(), (GhFile) fjList.get(i));
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						fileModel.add(ufj);
					}
					upList.setModel(fileModel);
					rowFile.setVisible(true);
					upFile.setDisabled(true);
					downFileZip.setDisabled(false);
				}
				win.detach();
			}
		
			}});
	}

	public Long getKuid() {
		return kuid;
	}

	public void setKuid(Long kuid) {
		this.kuid = kuid;
	}

	public GhHylw getLw() {
		return lw;
	}

	public void setLw(GhHylw lw) {
		this.lw = lw;
	}
}
