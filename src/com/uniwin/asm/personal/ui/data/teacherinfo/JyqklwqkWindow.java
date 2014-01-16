package com.uniwin.asm.personal.ui.data.teacherinfo;

import java.util.ArrayList;
import java.util.List;

import org.iti.gh.entity.GhFile;
import org.iti.gh.entity.GhJslw;
import org.iti.gh.entity.GhQklw;
import org.iti.gh.entity.GhYjfx;
import org.iti.gh.service.GhFileService;
import org.iti.gh.service.JslwService;
import org.iti.gh.service.QklwService;
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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import com.uniwin.framework.common.fileuploadex.DownloadFJ;
import com.uniwin.framework.common.fileuploadex.IsZipExists;
import com.uniwin.framework.common.fileuploadex.UploadFJ;
import com.uniwin.framework.entity.WkTUser;

public class JyqklwqkWindow extends FrameworkWindow {
	Textbox shijian, kanwu, location, factor, issn, all, ename, num, pages, projectname, fund, remark, cgmc;
	Label selfs,writer;
	Listbox record;
	YjfxListbox research;
	private Radiogroup center;
	GhQklw lw;
	Long kuid;
	private Radiogroup zd;
	// 暂存附件
	private Row rowFile;
	Listbox upList;
	ListModelList fileModel;
	YjfxService yjfxService;
	GhFileService ghfileService;
	JslwService jslwService;
	QklwService qklwService;
	WkTUser user;
	Button downFile, deUpload, upFile, downFileZip, submit;

	public void initShow() {
	}

	public void initWindow() {
		List recordtype = new ArrayList();
		String[] Recordtype = { "-请选择-", "SCI(科学引文索引)收录", "EI(工程索引)收录", "ISTP(科技会议录索引 )收录", "CSCD(中国科学引文数据库)收录", "CSSCI(中文社会科学引文索引)收录", "SSCI(社会科学引文索引)收录", "A&HCI(艺术与人文科学引文索引)收录", "《新华文摘》 全文转载", "《中国数学文摘》全文转载)", "《中国社会科学文摘》全文转载", "《中国数学》全文转载", "《中国人大复印资料》全文转载", "《全国高等院校文科学报文摘》全文转载", "《新华文摘》摘要转载", "《中国社会科学文摘》摘要转载", "《中国人大复印资料》摘要转载", "《中国数学》摘要转载" };
		for (int i = 0; i < Recordtype.length; i++) {
			recordtype.add(Recordtype[i]);
		}
		record.setModel(new ListModelList(recordtype));
		// 研究方向列表
		research.initYjfzList(user.getKuId(), null);
		all.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				List namelist = new ArrayList();
				String str = all.getValue().trim();
				if (str.contains("，")||str.contains(",")) {
					throw new WrongValueException(all, "全部作者填写错误，请选择顿号！");
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
			if (lw.getLwMc() != null) {
				cgmc.setValue(lw.getLwMc());
				cgmc.setDisabled(true);
			} else {
				cgmc.setValue("");
			}
			// 发表时间
			if (lw.getLwFbsj() != null) {
				shijian.setValue(lw.getLwFbsj());
			} else {
				shijian.setValue("");
			}
			// 发表的刊物
			if (lw.getLwKw() != null) {
				kanwu.setValue(lw.getLwKw());
			} else {
				kanwu.setValue("");
			}
			if (lw.getLwEname() != null) {
				ename.setValue(lw.getLwEname());
			} else {
				ename.setValue("");
			}
			if (lw.getLwAll() != null) {
				all.setValue(lw.getLwAll());
			} else {
				all.setValue("");
			}
			if (lw.getLwRecord() == null || lw.getLwRecord() == "") {
				record.setSelectedIndex(0);
			} else {
				record.setSelectedIndex(Integer.valueOf(lw.getLwRecord().trim()));
			}
			if (lw.getLwLocation() != null) {
				location.setValue(lw.getLwLocation());
			} else {
				location.setValue("");
			}
			if (lw.getLwFactor() != null) {
				factor.setValue(lw.getLwFactor());
			} else {
				factor.setValue("");
			}
			if (lw.getLwIssn() != null) {
				issn.setValue(lw.getLwIssn());
			} else {
				issn.setValue("");
			}
			if (lw.getLwNum() != null) {
				num.setValue(lw.getLwNum());
			} else {
				num.setValue("");
			}
			if ((lw.getLwPages() != null)) {
				pages.setValue(lw.getLwPages());
			} else {
				pages.setValue("");
			}
			if (lw.getLwZd().shortValue() == (GhQklw.LWZDXS.shortValue())) { // 教师论文还是指导学生论文
				zd.setSelectedIndex(0);
			} else {
				zd.setSelectedIndex(1);
			}
			if (lw.getLwCenter().shortValue() == GhQklw.LWHX_NO.shortValue()) { // 核心期刊
				center.setSelectedIndex(0);
			} else {
				center.setSelectedIndex(1);
			}
			if (lw.getLwRemark() != null) {
				remark.setValue(lw.getLwRemark());
			} else {
				remark.setValue("");
			}
			writer.setValue(lw.getUser().getKuName());
			GhJslw jslw = jslwService.findByKuidAndLwidAndType(user.getKuId(), lw.getLwId(), GhJslw.JYQK);
			if (jslw != null) {
				if (jslw.getLwSelfs() != null) {
					selfs.setValue(jslw.getLwSelfs()+"");
				} else {
					List namelist = new ArrayList();
					String str = all.getValue().trim();
					if (str.contains("，")||str.contains(",")) {
						throw new WrongValueException(all, "全部作者填写错误，请选择顿号！");
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
				// 研究方向
				research.initYjfzList(user.getKuId(), jslw.getYjId());
			} else {
				List namelist = new ArrayList();
				String str = all.getValue().trim();
				if (str.contains("，")||str.contains(",")) {
					throw new WrongValueException(all, "全部作者填写错误，请选择顿号！");
				} else {
					while (str.contains("、")) {
						Label lb = new Label(str.substring(0, str.indexOf("、")));
						namelist.add(lb.getValue().trim());
						str = str.substring(str.indexOf("、") + 1, str.length());
					}
					namelist.add(str.trim());
					selfs.setValue(namelist.indexOf(user.getKuName()) + 1+"");
					research.setSelectedIndex(0);
				}
			}
			// 非本人创建的论文记录部分项是不可以进行编辑的
			if (user.getKuId().intValue() != lw.getKuId()) {
				shijian.setDisabled(true);
				kanwu.setDisabled(true);
				all.setDisabled(true);
				ename.setDisabled(true);
				record.setDisabled(true);
				location.setDisabled(true);
				factor.setDisabled(true);
				issn.setDisabled(true);
				num.setDisabled(true);
				pages.setDisabled(true);
				remark.setDisabled(true);
				// 附件情况
				deUpload.setDisabled(true);
				upFile.setDisabled(true);
			}
			// if(lw.getAuditState()!=null&&lw.getAuditState().shortValue()==GhQklw.AUDIT_YES){
			// submit.setDisabled(true);
			// upFile.setDisabled(true);
			// }
		} else {
			cgmc.setValue("");
			shijian.setValue("");
			kanwu.setValue("");
			all.setValue("");
			selfs.setValue(null);
			num.setValue("");
			pages.setValue("");
			remark.setValue("");
			writer.setValue(user.getKuName());
			downFileZip.setDisabled(true);
			downFile.setDisabled(true);
			// 选择论文名称
			// if (cgmc.getModel().getSize() > 0) {
			// cgmc.addEventListener(Events.ON_SELECT, new EventListener() {
			// public void onEvent(Event arg0) throws Exception {
			// Object[] mc = (Object[]) cgmc.getSelectedItem().getValue();
			// GhQklw lw = (GhQklw) qklwService.get(GhQklw.class, (Long) mc[1]);
			// // 发表时间
			// if (lw.getLwFbsj() != null) {
			// shijian.setValue(lw.getLwFbsj());
			// ;
			// shijian.setDisabled(true);
			// } else {
			// shijian.setValue("");
			// shijian.setDisabled(true);
			// }
			// if (lw.getLwKw() != null) {
			// kanwu.setValue(lw.getLwKw());
			// kanwu.setDisabled(true);
			// } else {
			// kanwu.setValue("");
			// kanwu.setDisabled(true);
			// }
			// if (lw.getLwEname() != null) {
			// ename.setValue(lw.getLwEname());
			// ename.setDisabled(true);
			// } else {
			// ename.setValue("");
			// }
			// if (lw.getLwAll() != null) {
			// all.setValue(lw.getLwAll());
			// all.setDisabled(true);
			// }
			// if (lw.getLwNum() != null) {
			// num.setValue(lw.getLwNum());
			// num.setDisabled(true);
			// } else {
			// num.setValue("");
			// num.setDisabled(true);
			// }
			// if (lw.getLwPages() != null) {
			// pages.setValue(lw.getLwPages());
			// pages.setDisabled(true);
			// } else {
			// pages.setValue("");
			// pages.setDisabled(true);
			// }
			// if (lw.getLwRemark() != null) {
			// remark.setValue(lw.getLwRemark());
			// remark.setDisabled(true);
			// } else {
			// remark.setValue("");
			// remark.setDisabled(true);
			// }
			// List namelist = new ArrayList();
			// String str = lw.getLwAll().trim();
			// while (str.contains(",")) {
			// Label lb = new Label(str.substring(0, str.indexOf(",")));
			// namelist.add(lb.getValue());
			// str = str.substring(str.indexOf(",") + 1, str.length());
			// }
			// namelist.add(str);
			// selfs.setValue(namelist.indexOf(user.getKuName()) + 1);
			// selfs.setDisabled(true);
			// if (lw.getLwRecord() == null || lw.getLwRecord() == "") {
			// record.setSelectedIndex(0);
			// record.setDisabled(true);
			// } else {
			// record.setSelectedIndex(Integer.valueOf(lw.getLwRecord().trim()));
			// record.setDisabled(true);
			// }
			// if (lw.getLwLocation() != null) {
			// location.setValue(lw.getLwLocation());
			// location.setDisabled(true);
			// } else {
			// location.setValue("");
			// location.setDisabled(true);
			// }
			// if (lw.getLwFactor() != null) {
			// factor.setValue(lw.getLwFactor());
			// factor.setDisabled(true);
			// } else {
			// factor.setValue("");
			// factor.setDisabled(true);
			// }
			// if (lw.getLwIssn() != null) {
			// issn.setValue(lw.getLwIssn());
			// issn.setDisabled(true);
			// } else {
			// issn.setValue("");
			// issn.setDisabled(true);
			// }
			// if (lw.getLwNum() != null) {
			// num.setValue(lw.getLwNum());
			// num.setDisabled(true);
			// } else {
			// num.setValue("");
			// num.setDisabled(true);
			// }
			// if ((lw.getLwPages() != null)) {
			// pages.setValue(lw.getLwPages());
			// pages.setDisabled(true);
			// } else {
			// pages.setValue("");
			// pages.setDisabled(true);
			// }
			// if (lw.getLwZd().shortValue() == (GhQklw.LWZDXS.shortValue())) { // 教师论文还是指导学生论文
			// zd.setSelectedIndex(0);
			// } else {
			// zd.setSelectedIndex(1);
			// }
			// if (lw.getLwCenter().shortValue() == GhQklw.LWHX_NO.shortValue()) { // 核心期刊
			// center.setSelectedIndex(0);
			// } else {
			// center.setSelectedIndex(1);
			// }
			// if (lw.getLwRemark() != null) {
			// remark.setValue(lw.getLwRemark());
			// } else {
			// remark.setValue("");
			// }
			// // 附件情况
			// deUpload.setDisabled(true);
			// upFile.setDisabled(true);
			// List fjList = ghfileService.findByFxmIdandFType(lw.getLwId(), 14);
			// if (fjList.size() == 0) {// 无附件
			// rowFile.setVisible(false);
			// downFileZip.setDisabled(true);
			// } else {// 有附件
			// // 初始化附件
			// downFileZip.setDisabled(false);
			// for (int i = 0; i < fjList.size(); i++) {
			// UploadFJ ufj = new UploadFJ(false);
			// try {
			// ufj.ReadFJ(getDesktop(), (GhFile) fjList.get(i));
			// } catch (InterruptedException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// fileModel.add(ufj);
			// }
			// upList.setModel(fileModel);
			// rowFile.setVisible(true);
			// }
			// }
			// });
			// }
		}
		// 附件
		if (lw == null) {// 新添加
			rowFile.setVisible(false);
		} else {// 修改
			List fjList = ghfileService.findByFxmIdandFType(lw.getLwId(), 14);
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
		// 论文名称
		if (cgmc.getValue() == null || "".equals(cgmc.getValue().trim())) {
			throw new WrongValueException(cgmc, "您还没有填写论文名称！");
		}
		// 发表时间
		if (shijian.getValue() == null || "".equals(shijian.getValue().trim())) {
			throw new WrongValueException(shijian, "您还没有填写时间，格式如2008/9/28、2008、2008/9！");
		} else {
			try {
				String str = shijian.getValue().trim();
				if (str.length() < 4) {
					throw new WrongValueException(shijian, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
				} else if (str.length() == 4 || '/' == str.charAt(4)) {
					String s[] = str.split("/");
					if (s.length == 1 || s.length == 2 || s.length == 3) {
						for (int i = 0; i < s.length; i++) {
							String si = s[i];
							Integer.parseInt(si);
						}
					} else {
						throw new WrongValueException(shijian, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
					}
				} else {
					throw new WrongValueException(shijian, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
				}
			} catch (NumberFormatException e) {
				throw new WrongValueException(shijian, "您输入的时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
			}
		}
		// 发表刊物、会议名称及检索号或ISSN
		if (kanwu.getValue() == null || "".equals(kanwu.getValue().trim())) {
			throw new WrongValueException(kanwu, "您还没有填写发表刊物、会议名称！");
		}
		// false表示是修改，true表示是新建
		boolean index = false, owner = false;// 标识是非本人首创建论文信息
		if (lw == null) {// 说明这是新建的一个项目
			lw = new GhQklw();
			index = true;
		}
		if (!shijian.isDisabled()) {
			// 没有选择项目名称，该项目属于该用户
			owner = true;
		} else {
			owner = false;
		}
		if (index) {
			if (owner) {
				if (qklwService.CheckByNameAndLxAndIssn(null, cgmc.getValue().trim(), GhQklw.JYLW, issn.getValue().trim())) {
					Messagebox.show("您提交的期刊论文信息已存在，不可以重复添加！", "警告", Messagebox.OK, Messagebox.EXCLAMATION);
					lw = null;
					return;
				}
				lw.setLwMc(cgmc.getValue().trim());
				lw.setLwFbsj(shijian.getValue().trim());
				lw.setLwKw(kanwu.getValue().trim());
				lw.setLwIssn(issn.getValue().trim());
				lw.setLwFactor(factor.getValue().trim());
				lw.setLwLocation(location.getValue().trim());
				lw.setLwEname(ename.getValue());
				lw.setLwAll(all.getValue().trim());
				lw.setLwRecord(String.valueOf(record.getSelectedIndex()));
				lw.setLwNum(num.getValue().trim());
				lw.setLwPages(pages.getValue().trim());
				lw.setLwRemark(remark.getValue().trim());
				if (center.getSelectedIndex() == 0) {
					lw.setLwCenter(GhQklw.LWHX_NO.shortValue());// 核心期刊情况
				} else {
					lw.setLwCenter(GhQklw.LWHX_YES.shortValue());
				}
				if (zd.getSelectedIndex() == 0) {// 获奖 1代表“否”2代表“是”
					lw.setLwZd(GhQklw.LWZDXS.shortValue());
				} else {
					lw.setLwZd(GhQklw.LWZDJS.shortValue());
				}
				lw.setLwLx(GhQklw.JYLW);
				lw.setKuId(kuid);
				qklwService.save(lw);
				// 附件存储
				UploadFJ.ListModelListUploadCommand(fileModel, lw.getLwId());
				GhJslw jslw = new GhJslw();
				jslw.setLwzlId(lw.getLwId());
				jslw.setJslwtype(GhJslw.JYQK);
				jslw.setKuId(user.getKuId());
				jslw.setLwSelfs(Integer.parseInt(selfs.getValue()));
				jslw.setYjId(((GhYjfx) research.getSelectedItem().getValue()).getGyId());
				jslwService.save(jslw);
				Messagebox.show("保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			} else {
				GhJslw jslw = new GhJslw();
				jslw.setLwzlId(lw.getLwId());
				jslw.setJslwtype(GhJslw.JYQK);
				jslw.setKuId(user.getKuId());
				jslw.setLwSelfs(Integer.parseInt(selfs.getValue()));
				jslw.setYjId(((GhYjfx) research.getSelectedItem().getValue()).getGyId());
				jslwService.save(jslw);
				Messagebox.show("保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			}
			Events.postEvent(Events.ON_CHANGE, this, null);
		} else if (!index) {
			if (user.getKuId().intValue() == lw.getKuId().intValue()) {
				if (qklwService.CheckByNameAndLxAndIssn(lw, cgmc.getValue().trim(), GhQklw.JYLW, issn.getValue().trim())) {
					Messagebox.show("您提交的期刊论文信息已存在，此次修改不予保存！", "警告", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
				lw.setLwFbsj(shijian.getValue());
				lw.setLwKw(kanwu.getValue());
				lw.setLwIssn(issn.getValue());
				lw.setLwFactor(factor.getValue());
				lw.setLwLocation(location.getValue());
				lw.setLwEname(ename.getValue());
				lw.setLwAll(all.getValue());
				lw.setLwRecord(String.valueOf(record.getSelectedIndex()));
				lw.setLwNum(num.getValue());
				lw.setLwPages(pages.getValue());
				lw.setLwRemark(remark.getValue());
				if (center.getSelectedIndex() == 0) {
					lw.setLwCenter(GhQklw.LWHX_NO.shortValue());// 核心期刊情况
				} else {
					lw.setLwCenter(GhQklw.LWHX_YES.shortValue());
				}
				if (zd.getSelectedIndex() == 0) {// 获奖 1代表“否”2代表“是”
					lw.setLwZd(GhQklw.LWZDXS.shortValue());
				} else {
					lw.setLwZd(GhQklw.LWZDJS.shortValue());
				}
				lw.setAuditState(null);
				lw.setAuditUid(null);
				lw.setAuditReason(null);
				qklwService.update(lw);
				// 附件存储
				UploadFJ.ListModelListUploadCommand(fileModel, lw.getLwId());
				GhJslw jslw = jslwService.findByKuidAndLwidAndType(user.getKuId(), lw.getLwId(), GhJslw.JYQK);
				if (jslw != null) {
					jslw.setLwSelfs(Integer.parseInt(selfs.getValue()));
					jslw.setYjId(((GhYjfx) research.getSelectedItem().getValue()).getGyId());
					jslwService.update(jslw);
				} else {
					jslw = new GhJslw();
					jslw.setJslwtype(GhJslw.JYQK);
					jslw.setKuId(user.getKuId());
					jslw.setLwzlId(lw.getLwId());
					jslw.setLwSelfs(Integer.parseInt(selfs.getValue()));
					jslw.setYjId(((GhYjfx) research.getSelectedItem().getValue()).getGyId());
					jslwService.save(jslw);
				}
				Messagebox.show("保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			} else {
				GhJslw jslw = jslwService.findByKuidAndLwidAndType(user.getKuId(), lw.getLwId(), GhJslw.JYQK);
				if (jslw != null) {
					jslw.setLwSelfs(Integer.parseInt(selfs.getValue()));
					jslw.setYjId(((GhYjfx) research.getSelectedItem().getValue()).getGyId());
					jslwService.update(jslw);
				} else {
					jslw = new GhJslw();
					jslw.setJslwtype(GhJslw.JYQK);
					jslw.setKuId(user.getKuId());
					jslw.setLwzlId(lw.getLwId());
					jslw.setLwSelfs(Integer.parseInt(selfs.getValue()));
					jslw.setYjId(((GhYjfx) research.getSelectedItem().getValue()).getGyId());
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
		// cgmc.setModel(new ListModelList(qklwService.findAllname(user.getKuId(), user.getKuName(), GhQklw.JYLW, GhJslw.JYQK)));
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
		/*
		 * Object media = Fileupload.get(); if (media == null) { return; } rowFile.setVisible(true); fileModel.add(media);
		 */
		UploadFJ ufj = new UploadFJ(true);
		Media media = ufj.Upload(this.getDesktop(), 14, 10, "附件不能超过10MB", "期刊论文情况附件------附件不能超过10MB");
		/* Object media = Fileupload.get(); */
		if (media == null) {
			return;
		}
		rowFile.setVisible(true);
		fileModel.add(ufj);
	}

	public void onChange$cgmc() {
		onChange$kanwu();
	}

	public void onChange$kanwu() {
		List lwlist = qklwService.findByMCAndKW(cgmc.getValue(), kanwu.getValue());
		if (lwlist.size() != 0) {
			GhQklw qklw = (GhQklw) lwlist.get(0);
			lw = qklw;
			cgmc.setReadonly(true);
			kanwu.setReadonly(true);
			shijian.setValue(lw.getLwFbsj());
			shijian.setDisabled(true);
			ename.setValue(lw.getLwEname());
			ename.setDisabled(true);
			all.setValue(lw.getLwAll());
			all.setDisabled(true);
			writer.setValue(lw.getUser().getKuName());
			pages.setValue(lw.getLwPages());
			pages.setDisabled(true);
			remark.setValue(lw.getLwRemark());
			remark.setDisabled(true);
			List namelist = new ArrayList();
			String str = lw.getLwAll().trim();
			while (str.contains("、")) {
				Label lb = new Label(str.substring(0, str.indexOf("、")));
				namelist.add(lb.getValue().trim());
				str = str.substring(str.indexOf("、") + 1, str.length());
			}
			namelist.add(str.trim());
			selfs.setValue(namelist.indexOf(user.getKuName()) + 1+"");
			if (lw.getLwRecord() == null || lw.getLwRecord() == "") {
				record.setSelectedIndex(0);
				record.setDisabled(true);
			} else {
				record.setSelectedIndex(Integer.valueOf(lw.getLwRecord().trim()));
				record.setDisabled(true);
			}
			location.setValue(lw.getLwLocation());
			location.setDisabled(true);
			factor.setValue(lw.getLwFactor());
			factor.setDisabled(true);
			issn.setValue(lw.getLwIssn());
			issn.setDisabled(true);
			num.setValue(lw.getLwNum());
			num.setDisabled(true);
			pages.setValue(lw.getLwPages());
			pages.setDisabled(true);
			if (lw.getLwZd()!=null&&lw.getLwZd().shortValue() == (GhQklw.LWZDXS.shortValue())) { // 教师论文还是指导学生论文
				zd.setSelectedIndex(0);
			} else {
				zd.setSelectedIndex(1);
			}
			if (lw.getLwCenter()!=null&&lw.getLwCenter().shortValue() == GhQklw.LWHX_NO.shortValue()) { // 核心期刊
				center.setSelectedIndex(0);
			} else {
				center.setSelectedIndex(1);
			}
			remark.setValue(lw.getLwRemark());
			deUpload.setDisabled(true);
			upFile.setDisabled(true);
			List fjList = ghfileService.findByFxmIdandFType(lw.getLwId(), 14);
			if (fjList.size() == 0) {// 无附件
				rowFile.setVisible(false);
				downFileZip.setDisabled(true);
			} else {
				downFileZip.setDisabled(false);
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
			}
			upFile.setDisabled(true);
		} /*else {
			shijian.setValue("");
			shijian.setDisabled(false);
			ename.setValue("");
			ename.setDisabled(false);
			all.setValue("");
			all.setDisabled(false);
			pages.setValue("");
			pages.setDisabled(false);
			remark.setValue("");
			remark.setDisabled(false);
			location.setValue("");
			location.setDisabled(false);
			record.setSelectedIndex(0);
			record.setDisabled(false);
			selfs.setValue("0");
			writer.setValue(user.getKuName());
			factor.setValue("");
			factor.setDisabled(false);
			issn.setValue("");
			issn.setDisabled(false);
			num.setValue("");
			num.setDisabled(false);
			pages.setValue("");
			pages.setDisabled(false);
			upFile.setDisabled(false);
		}*/
	}

	public void onClick$choice() {
		final SelectQklwWindow win = (SelectQklwWindow) Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/qklw/selectlw.zul", null, null);
		win.doHighlighted();
		win.setP1(GhQklw.JYLW);
		win.setP2(GhJslw.JYQK);
		win.initWindow();
		win.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				GhQklw qklw = win.getQklw();
				if(!qklw.getLwAll().contains(user.getKuName())){
					Messagebox.show("您不是项目组成员，请联系项目填写人申请加入！", "提示：", Messagebox.OK, Messagebox.EXCLAMATION);
				}
				else{
				lw = qklw;
				cgmc.setValue(lw.getLwMc());
				kanwu.setValue(lw.getLwKw());
				cgmc.setReadonly(true);
				kanwu.setReadonly(true);
				shijian.setValue(lw.getLwFbsj());
				shijian.setDisabled(true);
				ename.setValue(lw.getLwEname());
				ename.setDisabled(true);
				all.setValue(lw.getLwAll());
				all.setDisabled(true);
				pages.setValue(lw.getLwPages());
				pages.setDisabled(true);
				remark.setValue(lw.getLwRemark());
				remark.setDisabled(true);
				List namelist = new ArrayList();
				String str = lw.getLwAll().trim();
				while (str.contains("、")) {
					Label lb = new Label(str.substring(0, str.indexOf("、")));
					namelist.add(lb.getValue().trim());
					str = str.substring(str.indexOf("、") + 1, str.length());
				}
				namelist.add(str.trim());
				selfs.setValue(namelist.indexOf(user.getKuName()) + 1+"");
				if (lw.getLwRecord() == null || lw.getLwRecord() == "") {
					record.setSelectedIndex(0);
					record.setDisabled(true);
				} else {
					record.setSelectedIndex(Integer.valueOf(lw.getLwRecord().trim()));
					record.setDisabled(true);
				}
				location.setValue(lw.getLwLocation());
				location.setDisabled(true);
				factor.setValue(lw.getLwFactor());
				factor.setDisabled(true);
				issn.setValue(lw.getLwIssn());
				issn.setDisabled(true);
				num.setValue(lw.getLwNum());
				num.setDisabled(true);
				pages.setValue(lw.getLwPages());
				pages.setDisabled(true);
				if (lw.getLwZd().shortValue() == (GhQklw.LWZDXS.shortValue())) { // 教师论文还是指导学生论文
					zd.setSelectedIndex(0);
				} else {
					zd.setSelectedIndex(1);
				}
				if (lw.getLwCenter().shortValue() == GhQklw.LWHX_NO.shortValue()) { // 核心期刊
					center.setSelectedIndex(0);
				} else {
					center.setSelectedIndex(1);
				}
				remark.setValue(lw.getLwRemark());
				writer.setValue(lw.getUser().getKuName());
				deUpload.setDisabled(true);
				upFile.setDisabled(true);
				List fjList = ghfileService.findByFxmIdandFType(lw.getLwId(), 14);
				if (fjList.size() == 0) {// 无附件
					rowFile.setVisible(false);
					downFileZip.setDisabled(true);
				} else {
					downFileZip.setDisabled(false);
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
				}
				upFile.setDisabled(true);
				win.detach();
			}
			}});
	}

	/**
	 * <li>删除上传的文件，重新选择
	 * 
	 * @author bobo 2010-4-11
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
			temp.DeleteFJ();
			fileModel.remove(it.getValue());
			rowFile.setVisible(false);
		} else if (fileModel.getSize() > 1) {
			temp.DeleteFJ();
			fileModel.remove(it.getValue());
		}
	}

	@SuppressWarnings("unchecked")
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

	public void onClick$close() {
		this.detach();
	}

	public Long getKuid() {
		return kuid;
	}

	public void setKuid(Long kuid) {
		this.kuid = kuid;
	}

	public GhQklw getLw() {
		return lw;
	}

	public void setLw(GhQklw lw) {
		this.lw = lw;
	}
}
