package com.uniwin.asm.personal.ui.data.teacherinfo;

import java.util.ArrayList;
import java.util.List;

import org.iti.gh.entity.GhFile;
import org.iti.gh.entity.GhHylw;
import org.iti.gh.entity.GhJslw;
import org.iti.gh.entity.GhYjfx;
import org.iti.gh.service.GhFileService;
import org.iti.gh.service.HylwService;
import org.iti.gh.service.JslwService;
import org.iti.gh.service.LwzlService;
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

public class JylwqkWindow extends FrameworkWindow {
	Textbox shijian, kanwu, time, palce, host, all, publish, num, pages, remark, cgmc,ename;
	Label selfs,writer;
	Listbox record;
	YjfxListbox research;
	GhHylw lw;
	Long kuid;
	private Radiogroup zd;
	// �ݴ渽��
	private Row rowFile;
	Listbox upList;
	ListModelList fileModel;
	XmzzService xmzzService;
	HylwService hylwService;
	YjfxService yjfxService;
	GhFileService ghfileService;
	JslwService jslwService;
	WkTUser user;
	Button downFile, deUpload, upFile, downFileZip, submit;

	public void initShow() {
	}

	public void initWindow() {
		List recordtype = new ArrayList();
		String[] Recordtype = { "-��ѡ��-", "SCI(��ѧ��������)��¼", "EI(��������)��¼", "ISTP(�Ƽ�����¼���� )��¼", "CSCD(�й���ѧ�������ݿ�)��¼", "CSSCI(��������ѧ��������)��¼", "SSCI(����ѧ��������)��¼", "A&HCI(���������Ŀ�ѧ��������)��¼", "���»���ժ�� ȫ��ת��", "���й���ѧ��ժ��ȫ��ת��)", "���й�����ѧ��ժ��ȫ��ת��", "���й���ѧ��ȫ��ת��", "���й��˴�ӡ���ϡ�ȫ��ת��", "��ȫ���ߵ�ԺУ�Ŀ�ѧ����ժ��ȫ��ת��", "���»���ժ��ժҪת��", "���й�����ѧ��ժ��ժҪת��", "���й��˴�ӡ���ϡ�ժҪת��", "���й���ѧ��ժҪת��" };
		for (int i = 0; i < Recordtype.length; i++) {
			recordtype.add(Recordtype[i]);
		}
		record.setModel(new ListModelList(recordtype));
		// �о������б�
		List yjfxlist = yjfxService.findByKuid(kuid);
		// if(yjfxlist != null && yjfxlist.size() !=0 ){
		// GhYjfx yjfx = (GhYjfx) yjfxlist.get(0);
		// String[] Yjfx = {"-��ѡ��-",yjfx.getYjResearch1(),yjfx.getYjResearch2(),yjfx.getYjResearch3(),yjfx.getYjResearch4(),yjfx.getYjResearch5()};
		// research.setModel(new ListModelList(Yjfx));
		// }
		research.initYjfzList(user.getKdId(), null);
		all.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				List namelist = new ArrayList();
				String str = all.getValue().trim();
				if (str.contains("��")||str.contains(",")) {
					throw new WrongValueException(all, "��Ŀ����Ա��д������ѡ��ٺţ�");
				} else {
					while (str.contains("��")) {
						Label lb = new Label(str.substring(0, str.indexOf("��")));
						namelist.add(lb.getValue().trim());
						str = str.substring(str.indexOf("��") + 1, str.length());
					}
					namelist.add(str.trim());
					selfs.setValue(namelist.indexOf(user.getKuName()) + 1+"");
				}
			}
		});
		if (lw != null) {
			// ����
			if (lw.getLwMc() != null) {
				cgmc.setValue(lw.getLwMc());
				cgmc.setDisabled(true);
			} else {
				cgmc.setValue("");
			}
			// ����ʱ��
			if (lw.getLwFbsj() != null) {
				shijian.setValue(lw.getLwFbsj());
			} else {
				shijian.setValue("");
			}
			// ����Ŀ���
			if (lw.getLwKw() != null) {
				kanwu.setValue(lw.getLwKw());
			} else {
				kanwu.setValue("");
			}
			if (lw.getLwTime() != null) {
				time.setValue(lw.getLwTime());
			} else {
				time.setValue("");
			}
			if (lw.getLwPlace() != null) {
				palce.setValue(lw.getLwPlace());
			} else {
				palce.setValue("");
			}
			if (lw.getLwHost() != null) {
				host.setValue(lw.getLwHost());
			} else {
				host.setValue("");
			}
			if (lw.getLwEname() != null) {
				ename.setValue(lw.getLwEname());
			}else{
				ename.setValue("");
			}
			if (lw.getLwAll() != null) {
				all.setValue(lw.getLwAll());
			} else {
				all.setValue("");
			}
			if (lw.getLwPublish() != null) {
				publish.setValue(lw.getLwPublish());
			} else {
				publish.setValue("");
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
			if (lw.getLwZd()!=null&&lw.getLwZd().shortValue() == (GhHylw.LWZDXS.shortValue())) { // ��ʦ���Ļ���ָ��ѧ������
				zd.setSelectedIndex(0);
			} else {
				zd.setSelectedIndex(1);
			}
			if (lw.getLwRemark() != null) {
				remark.setValue(lw.getLwRemark());
			} else {
				remark.setValue("");
			}
			writer.setValue(lw.getUser().getKuName());
			GhJslw gj = jslwService.findByKuidAndLwidAndType(user.getKuId(), lw.getLwId(), GhJslw.JYHY);
			if (gj != null) {
				research.initYjfzList(user.getKdId(), gj.getYjId());
				if (gj.getLwSelfs() != null) {
					selfs.setValue(gj.getLwSelfs()+"");
				} else {
					List namelist = new ArrayList();
					String str = all.getValue().trim();
					if (str.contains("��")||str.contains(",")) {
						throw new WrongValueException(all, "��Ŀ����Ա��д������ѡ��ٺţ�");
					} else {
						while (str.contains("��")) {
							Label lb = new Label(str.substring(0, str.indexOf("��")));
							namelist.add(lb.getValue().trim());
							str = str.substring(str.indexOf("��") + 1, str.length());
						}
						namelist.add(str.trim());
						selfs.setValue(namelist.indexOf(user.getKuName()) + 1+"");
					}
				}
				// �о�����
				// if (gj.getYjId() == null || gj.getYjId() == "") {
				// research.setSelectedIndex(0);
				// } else {
				// research.setSelectedIndex(Integer.valueOf(gj.getYjId().trim()));
				// }
			} else {
				List namelist = new ArrayList();
				String str = all.getValue().trim();
				if (str.contains("��")||str.contains(",")) {
					throw new WrongValueException(all, "��Ŀ����Ա��д������ѡ��ٺţ�");
				} else {
					while (str.contains("��")) {
						Label lb = new Label(str.substring(0, str.indexOf("��")));
						namelist.add(lb.getValue().trim());
						str = str.substring(str.indexOf("��") + 1, str.length());
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
				publish.setDisabled(true);
				num.setDisabled(true);
				pages.setDisabled(true);
				// �������
				deUpload.setDisabled(true);
				upFile.setDisabled(true);
			}
			if (lw.getAuditState() != null && lw.getAuditState().shortValue() == GhHylw.AUDIT_YES) {
				submit.setDisabled(true);
				upFile.setDisabled(true);
			}
		} else {
			writer.setValue(user.getKuName());
			cgmc.setValue("");
			shijian.setValue("");
			kanwu.setValue("");
			palce.setValue("");
			time.setValue("");
			host.setValue("");
			ename.setValue("");
			all.setValue("");
			selfs.setValue(null);
			publish.setValue("");
			num.setValue("");
			pages.setValue("");
			remark.setValue("");
			downFileZip.setDisabled(true);
			downFile.setDisabled(true);
			// ѡ����������
			// if (cgmc.getModel().getSize() > 0) {
			// cgmc.addEventListener(Events.ON_SELECT, new EventListener() {
			// public void onEvent(Event arg0) throws Exception {
			// Object[] mc = (Object[]) cgmc.getSelectedItem().getValue();
			// GhHylw lw = (GhHylw) hylwService.get(GhHylw.class, (Long) mc[1]);
			// shijian.setValue(lw.getLwFbsj() != null ? lw.getLwFbsj() : "");
			// shijian.setDisabled(true);
			// kanwu.setValue(lw.getLwKw() != null ? lw.getLwKw() : "");
			// kanwu.setDisabled(true);
			// palce.setValue(lw.getLwPlace() != null ? lw.getLwPlace() : "");
			// palce.setDisabled(true);
			// time.setValue(lw.getLwTime() != null ? lw.getLwTime() : "");
			// time.setDisabled(true);
			// host.setValue(lw.getLwHost() != null ? lw.getLwHost() : "");
			// host.setDisabled(true);
			// all.setValue(lw.getLwAll() != null ? lw.getLwAll() : "");
			// all.setDisabled(true);
			// publish.setValue(lw.getLwPublish() != null ? lw.getLwPublish() : "");
			// publish.setDisabled(true);
			// num.setValue(lw.getLwNum() != null ? lw.getLwNum() : "");
			// num.setDisabled(true);
			// pages.setValue(lw.getLwPages() != null ? lw.getLwPages() : "");
			// pages.setDisabled(true);
			// remark.setValue(lw.getLwRemark() != null ? lw.getLwRemark() : "");
			// remark.setDisabled(true);
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
			// } else {
			// record.setSelectedIndex(Integer.valueOf(lw.getLwRecord().trim()));
			// }
			// record.setDisabled(true);
			// if (lw.getLwZd().shortValue() == (GhHylw.LWZDXS.shortValue())) { // ��ʦ���Ļ���ָ��ѧ������
			// zd.setSelectedIndex(0);
			// } else {
			// zd.setSelectedIndex(1);
			// }
			// // �������
			// deUpload.setDisabled(true);
			// upFile.setDisabled(true);
			// List fjList = ghfileService.findByFxmIdandFType(lw.getLwId(), 13);
			// if (fjList.size() == 0) {// �޸���
			// rowFile.setVisible(false);
			// downFileZip.setDisabled(true);
			// } else {// �и���
			// // ��ʼ������
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
		// ����
		if (lw == null) {// �����
			rowFile.setVisible(false);
		} else {// �޸�
			List fjList = ghfileService.findByFxmIdandFType(lw.getLwId(), 13);
			if (fjList.size() == 0) {// �޸���
				rowFile.setVisible(false);
				downFileZip.setDisabled(true);
			} else {// �и���
				// ��ʼ������
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
		// �ж�ȫ������
		Boolean flag = false;
		String[] author = all.getValue().toString().trim().split("��");
		for (int i = 0; i < author.length; i++) {
			if (author[i].trim().equalsIgnoreCase(user.getKuName().trim())) {
				flag = true;
			}
		}
		if (!flag) {
			throw new WrongValueException(all, "������û������������");
		}
		// ������������Ƽ������Ż�ISSN
		if (cgmc.getValue() == null || "".equals(cgmc.getValue().trim())) {
			throw new WrongValueException(cgmc, "����û����д�������ƣ�");
		}
		// ������������Ƽ������Ż�ISSN
		if (kanwu.getValue() == null || "".equals(kanwu.getValue().trim())) {
			throw new WrongValueException(kanwu, "�����������ļ����ƣ�");
		}
		// ����ʱ��
		if (time.getValue() == null || "".equals(time.getValue().trim())) {
			throw new WrongValueException(time, "����û����дʱ�䣬��ʽ��2008/9/28��2008��2008/9��");
		} else {
			try {
				String str = time.getValue().trim();
				if (str.length() < 4) {
					throw new WrongValueException(time, "�������ʱ���ʽ�д�������������д����ʽ��2008/9/28��2008��2008/9��");
				} else if (str.length() == 4 || '/' == str.charAt(4)) {
					String s[] = str.split("/");
					if (s.length == 1 || s.length == 2 || s.length == 3) {
						for (int i = 0; i < s.length; i++) {
							String si = s[i];
							Integer.parseInt(si);
						}
					} else {
						throw new WrongValueException(time, "�������ʱ���ʽ�д�������������д����ʽ��2008/9/28��2008��2008/9��");
					}
				} else {
					throw new WrongValueException(time, "�������ʱ���ʽ�д�������������д����ʽ��2008/9/28��2008��2008/9��");
				}
			} catch (NumberFormatException e) {
				throw new WrongValueException(time, "�������ʱ���ʽ�д�������������д����ʽ��2008/9/28��2008��2008/9��");
			}
		}
		// false��ʾ���޸ģ�true��ʾ���½�
		boolean index = false, owner = false;
		if (lw == null) {// ˵�������½���һ����Ŀ
			lw = new GhHylw();
			index = true;
		}
		if (!shijian.isDisabled()) {
			// û��ѡ����Ŀ���ƣ�����Ŀ���ڸ��û�
			owner = true;
		} else {
			owner = false;
		}
		if (index) {
			// �½�����,�����������һ����ǰ�û��������������ƣ����浽���ı�ͽ�ʦ���ı����������û�ѡ����������ƣ�ֻ�����ʦ���ı�
			if (owner) {
				if (hylwService.CheckOnlyOne(null, cgmc.getValue().trim(), GhHylw.JYLW, kanwu.getValue().trim())) {
					Messagebox.show("���ύ�Ļ���������Ϣ�Ѵ��ڣ��������ظ���ӣ�", "����", Messagebox.OK, Messagebox.EXCLAMATION);
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
				if (zd.getSelectedIndex() == 0) {// �� 1������2�����ǡ�
					lw.setLwZd(GhHylw.LWZDXS.shortValue());
				} else {
					lw.setLwZd(GhHylw.LWZDJS.shortValue());
				}
				lw.setLwLx(GhHylw.JYLW);
				lw.setKuId(kuid);
				hylwService.save(lw);
				// �����洢
				UploadFJ.ListModelListUploadCommand(fileModel, lw.getLwId());
				GhJslw jslw = new GhJslw();
				jslw.setJslwtype(GhJslw.JYHY);
				jslw.setKuId(user.getKuId());
				jslw.setLwSelfs(Integer.parseInt(selfs.getValue()));
				GhYjfx fx = (GhYjfx) research.getSelectedItem().getValue();
				jslw.setYjId(fx.getGyId());
				jslw.setLwzlId(lw.getLwId());
				jslwService.save(jslw);
				//
				Messagebox.show("����ɹ���", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			} else {
				GhJslw jslw = new GhJslw();
				jslw.setJslwtype(GhJslw.JYHY);
				jslw.setKuId(user.getKuId());
				jslw.setLwSelfs(Integer.parseInt(selfs.getValue()));
				GhYjfx fx = (GhYjfx) research.getSelectedItem().getValue();
				jslw.setYjId(fx.getGyId());
				jslw.setLwzlId(lw.getLwId());
				jslwService.save(jslw);
				//
				Messagebox.show("����ɹ���", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			}
			Events.postEvent(Events.ON_CHANGE, this, null);
		} else {
			if (user.getKuId().intValue() == lw.getKuId().intValue()) {
				if (hylwService.CheckOnlyOne(lw, cgmc.getValue().trim(), GhHylw.JYLW, kanwu.getValue().trim())) {
					Messagebox.show("���ύ�Ļ���������Ϣ�Ѵ��ڣ��˴��޸Ĳ��豣�棡", "����", Messagebox.OK, Messagebox.EXCLAMATION);
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
				if (zd.getSelectedIndex() == 0) {// ָ�����
					lw.setLwZd(GhHylw.LWZDXS.shortValue());
				} else {
					lw.setLwZd(GhHylw.LWZDJS.shortValue());
				}
				lw.setAuditState(null);
				lw.setAuditUid(null);
				lw.setAuditReason(null);
				hylwService.update(lw);
				GhJslw jslw = jslwService.findByKuidAndLwidAndType(user.getKuId(), lw.getLwId(), GhJslw.JYHY);
				if (jslw != null) {
					jslw.setLwSelfs(Integer.parseInt(selfs.getValue()));
					GhYjfx fx = (GhYjfx) research.getSelectedItem().getValue();
					jslw.setYjId(fx.getGyId());
					jslwService.update(jslw);
				} else {
					jslw = new GhJslw();
					jslw.setJslwtype(GhJslw.JYHY);
					jslw.setKuId(user.getKuId());
					jslw.setLwSelfs(Integer.parseInt(selfs.getValue()));
					GhYjfx fx = (GhYjfx) research.getSelectedItem().getValue();
					jslw.setYjId(fx.getGyId());
					jslw.setLwzlId(lw.getLwId());
					jslwService.save(jslw);
				}
				Messagebox.show("����ɹ���", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			} else {
				GhJslw jslw = jslwService.findByKuidAndLwidAndType(user.getKuId(), lw.getLwId(), GhJslw.JYHY);
				if (jslw != null) {
					jslw.setLwSelfs(Integer.parseInt(selfs.getValue()));
					GhYjfx fx = (GhYjfx) research.getSelectedItem().getValue();
					jslw.setYjId(fx.getGyId());
					jslwService.update(jslw);
				} else {
					jslw = new GhJslw();
					jslw.setJslwtype(GhJslw.JYHY);
					jslw.setKuId(user.getKuId());
					jslw.setLwSelfs(Integer.parseInt(selfs.getValue()));
					GhYjfx fx = (GhYjfx) research.getSelectedItem().getValue();
					jslw.setYjId(fx.getGyId());
					jslw.setLwzlId(lw.getLwId());
					jslwService.save(jslw);
				}
				Messagebox.show("����ɹ���", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
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
		// cgmc.setModel(new ListModelList(hylwService.findAllname(user.getKuId(), user.getKuName(), GhHylw.JYLW, GhJslw.JYHY)));
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
		Media media = ufj.Upload(this.getDesktop(), 13, 10, "�������ܳ���10MB", "���������������------�������ܳ���10MB");
		/* Object media = Fileupload.get(); */
		if (media == null) {
			return;
		}
		rowFile.setVisible(true);
		fileModel.add(ufj);
	}

	/**
	 * <li>ɾ���ϴ����ļ�������ѡ��
	 * 
	 * @author bobo 2010-4-11
	 */
	public void onClick$deUpload() {
		// ȥ���ɵĴ������
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

	public void onClick$reset() {
		// ȥ���ɵĴ������
		IsZipExists.delZipFile(this.getDesktop().getWebApp().getRealPath("/upload/xkjs/").trim() + "\\" + "_" + this.lw.getLwId() + "_" + ".zip");
		List list = fileModel.getInnerList();
		for (int i = 0; i < list.size(); i++) {
			((UploadFJ) list.get(i)).DeleteFJ();
		}
		initWindow();
		fileModel.clear();
		rowFile.setVisible(false);
	}

	// ������ظ���
	public void onClick$downFileZip() {
		DownloadFJ.ListModelListDownloadCommand(this.getDesktop(), this.lw.getLwId(), fileModel);
	}

	// �����ļ�����
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

	public void onClick$choice() {
		final SelectHylwWindow win = (SelectHylwWindow) Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/hylw/selectlw.zul", null, null);
		win.doHighlighted();
		win.setP1(GhHylw.JYLW);
		win.setP2(GhJslw.JYHY);
		win.initWindow();
		win.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				GhHylw hylw = win.getHylw();
				if(!hylw.getLwAll().contains(user.getKuName())){
					Messagebox.show("��������Ŀ���Ա������ϵ��Ŀ��д��������룡", "��ʾ��", Messagebox.OK, Messagebox.EXCLAMATION);
				}
				else{
				lw = hylw;
				cgmc.setValue(hylw.getLwMc());
				kanwu.setValue(hylw.getLwKw());
				kanwu.setReadonly(true);
				cgmc.setReadonly(true);
				shijian.setValue(hylw.getLwFbsj() != null ? hylw.getLwFbsj() : "");
				shijian.setReadonly(true);
				palce.setValue(hylw.getLwPlace() != null ? hylw.getLwPlace() : "");
				palce.setReadonly(true);
				time.setValue(hylw.getLwTime() != null ? hylw.getLwTime() : "");
				time.setReadonly(true);
				host.setValue(hylw.getLwHost() != null ? hylw.getLwHost() : "");
				host.setReadonly(true);
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
				while (str.contains("��")) {
					Label lb = new Label(str.substring(0, str.indexOf("��")));
					namelist.add(lb.getValue().trim());
					str = str.substring(str.indexOf("��") + 1, str.length());
				}
				namelist.add(str.trim());
				selfs.setValue(namelist.indexOf(user.getKuName()) + 1+"");
				if (hylw.getLwRecord() == null || hylw.getLwRecord() == "") {
					record.setSelectedIndex(0);
				} else {
					record.setSelectedIndex(Integer.valueOf(hylw.getLwRecord().trim()));
				}
				record.setDisabled(true);
				// �������
				deUpload.setDisabled(true);
				upFile.setDisabled(true);
				List fjList = ghfileService.findByFxmIdandFType(hylw.getLwId(), 3);
				if (fjList.size() == 0) {// �޸���
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

	public void onChange$cgmc() {
		List list = hylwService.findByMCAndKW(cgmc.getValue(), kanwu.getValue(), GhHylw.JYLW);
		if (list.size() != 0) {
			GhHylw hylw = (GhHylw) list.get(0);
			lw = hylw;
			kanwu.setReadonly(true);
			cgmc.setReadonly(true);
			writer.setValue(lw.getUser().getKuName());
			shijian.setValue(lw.getLwFbsj() != null ? lw.getLwFbsj() : "");
			shijian.setDisabled(true);
			palce.setValue(lw.getLwPlace() != null ? lw.getLwPlace() : "");
			palce.setDisabled(true);
			time.setValue(lw.getLwTime() != null ? lw.getLwTime() : "");
			time.setDisabled(true);
			host.setValue(lw.getLwHost() != null ? lw.getLwHost() : "");
			host.setDisabled(true);
			ename.setValue(lw.getLwEname()!=null?lw.getLwEname():"");
			ename.setDisabled(true);
			all.setValue(lw.getLwAll() != null ? lw.getLwAll() : "");
			all.setDisabled(true);
			publish.setValue(lw.getLwPublish() != null ? lw.getLwPublish() : "");
			publish.setDisabled(true);
			num.setValue(lw.getLwNum() != null ? lw.getLwNum() : "");
			num.setDisabled(true);
			pages.setValue(lw.getLwPages() != null ? lw.getLwPages() : "");
			pages.setDisabled(true);
			remark.setValue(lw.getLwRemark() != null ? lw.getLwRemark() : "");
			remark.setDisabled(true);
			List namelist = new ArrayList();
			String str = lw.getLwAll().trim();
			while (str.contains("��")) {
				Label lb = new Label(str.substring(0, str.indexOf("��")));
				namelist.add(lb.getValue().trim());
				str = str.substring(str.indexOf("��") + 1, str.length());
			}
			namelist.add(str.trim());
			selfs.setValue(namelist.indexOf(user.getKuName()) + 1+"");
			if (lw.getLwRecord() == null || lw.getLwRecord() == "") {
				record.setSelectedIndex(0);
			} else {
				record.setSelectedIndex(Integer.valueOf(lw.getLwRecord().trim()));
			}
			record.setDisabled(true);
			if (lw.getLwZd().shortValue() == (GhHylw.LWZDXS.shortValue())) { // ��ʦ���Ļ���ָ��ѧ������
				zd.setSelectedIndex(0);
			} else {
				zd.setSelectedIndex(1);
			}
			research.setDisabled(true);
			deUpload.setDisabled(true);
			upFile.setDisabled(true);
			List fjList = ghfileService.findByFxmIdandFType(lw.getLwId(), 13);
			if (fjList.size() == 0) {// �޸���
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
		} /*else {
			writer.setValue(user.getKuName());
			shijian.setValue("");
			shijian.setDisabled(false);
			palce.setValue("");
			palce.setDisabled(false);
			time.setValue("");
			time.setDisabled(false);
			host.setValue("");
			host.setDisabled(false);
			selfs.setValue("0");
			ename.setValue("");
			ename.setDisabled(false);
			all.setValue("");
			all.setDisabled(false);
			publish.setValue("");
			publish.setDisabled(false);
			num.setValue("");
			num.setDisabled(false);
			pages.setValue("");
			pages.setDisabled(false);
			remark.setValue("");
			remark.setDisabled(false);
			record.setDisabled(false);
			research.setDisabled(false);
			upFile.setDisabled(false);
		}*/
	}

	public void onChange$kanwu() {
		onChange$cgmc();
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

	public GhHylw getLw() {
		return lw;
	}

	public void setLw(GhHylw lw) {
		this.lw = lw;
	}
}
