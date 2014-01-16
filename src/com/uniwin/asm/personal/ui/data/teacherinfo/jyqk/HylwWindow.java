package com.uniwin.asm.personal.ui.data.teacherinfo.jyqk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.common.util.ExportExcel;
import org.iti.gh.entity.GhFile;
import org.iti.gh.entity.GhHylw;
import org.iti.gh.entity.GhJslw;
import org.iti.gh.entity.GhJsxm;
import org.iti.gh.entity.GhXm;
import org.iti.gh.entity.GhXmzz;
import org.iti.gh.entity.GhYjfx;
import org.iti.gh.service.CgService;
import org.iti.gh.service.FmzlService;
import org.iti.gh.service.GhFileService;
import org.iti.gh.service.HylwService;
import org.iti.gh.service.JlhzService;
import org.iti.gh.service.JslwService;
import org.iti.gh.service.JsxmService;
import org.iti.gh.service.JxbgService;
import org.iti.gh.service.KyjhService;
import org.iti.gh.service.PkpyService;
import org.iti.gh.service.PxService;
import org.iti.gh.service.QkdcService;
import org.iti.gh.service.QtService;
import org.iti.gh.service.SkService;
import org.iti.gh.service.XmService;
import org.iti.gh.service.XmzzService;
import org.iti.gh.service.XshyService;
import org.iti.gh.service.YjfxService;
import org.iti.gh.service.ZsService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Toolbarbutton;

import com.uniwin.asm.personal.ui.data.teacherinfo.CkshyjWindow;
import com.uniwin.asm.personal.ui.data.teacherinfo.JylwqkWindow;
import com.uniwin.asm.personal.ui.data.teacherinfo.JyxmzzlistWindow;
import com.uniwin.framework.common.fileuploadex.UploadFJ;
import com.uniwin.framework.entity.WkTUser;

public class HylwWindow extends BaseWindow {
	// �����������
	Toolbarbutton button10;
	Listbox listbox10;
	FmzlService fmzlService;
	QtService qtService;
	KyjhService kyjhService;
	PxService pxService;
	XshyService xshyService;
	SkService skService;
	ZsService zsService;
	JlhzService jlhzService;
	JxbgService jxbgService;
	XmService xmService;
	XmzzService xmzzService;
	QkdcService qkdcService;
	CgService cgService;
	PkpyService pkpyService;
	YjfxService yjfxService;
	GhFileService ghfileService;
	WkTUser user;
	JsxmService jsxmService;
	JslwService jslwService;
	HylwService hylwService;

	@Override
	public void initShow() {
		initWindow();
		listbox10.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhHylw lw = (GhHylw) arg1;
				// ���
				Listcell c1 = new Listcell(arg0.getIndex() + 1 + "");
				// ��������
				Listcell c2 = new Listcell();
				InnerButton ib = new InnerButton();
				ib.setLabel(lw.getLwMc());
				ib.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						final JylwqkWindow cgWin = (JylwqkWindow) Executions.createComponents("/admin/personal/data/teacherinfo/jxqk/jylwqk.zul", null, null);
						cgWin.setLw(lw);
						cgWin.setKuid(user.getKuId());
						cgWin.doHighlighted();
						cgWin.initWindow();
						cgWin.addEventListener(Events.ON_CHANGE, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								initWindow();
								cgWin.detach();
							}
						});
					}
				});
				c2.appendChild(ib);
				 
				// ������
				Listcell c3 = new Listcell(lw.getLwKw());
				//��������
				String name=lw.getLwAll();
				String na[]=name.split("��");
				Listcell c4 = new Listcell(na[0]);
				// ����ʱ��
				Listcell c5 = new Listcell(lw.getLwTime()); 
				// ��Ŀ����
				Listcell c6 = new Listcell();
				InnerButton bt = new InnerButton("�鿴/�༭");
				bt.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						JyxmzzlistWindow cgWin = (JyxmzzlistWindow) Executions.createComponents("/admin/personal/data/teacherinfo/jxqk/jyxmzzlist.zul", null, null);
						cgWin.setKuid(user.getKuId());
						cgWin.setLw(lw);
						cgWin.initWindow();
						cgWin.doHighlighted();
					}
				});
				c6.appendChild(bt);
				// ����
				Listcell c7 = new Listcell();
				InnerButton gn = new InnerButton();
				gn.setLabel("ɾ��");
				gn.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						if (lw.getKuId().intValue() != user.getKuId().intValue()) {
							Messagebox.show("�����Ǵ���Ŀ�ύ�ˣ��ʲ���ɾ����","��ʾ", Messagebox.OK,Messagebox.EXCLAMATION);
						} else {
							if (Messagebox.show("ȷ��ɾ����?", "��ʾ", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == 1) {
								// ɾ������
								List list = ghfileService.findByFxmIdandFType(lw.getLwId(), 13);
								if (list.size() > 0) {
									// ���ڸ���
									for (int i = 0; i < list.size(); i++) {
										UploadFJ ufj = new UploadFJ(false);
										ufj.ReadFJ(getDesktop(), (GhFile) list.get(i));
										ufj.DeleteFJ();
									}
								}
								List cglist = jslwService.findByLwidAndType(lw.getLwId(), GhJslw.JYHY);
								if (cglist != null && cglist.size() != 0) {
									for (int i = 0; i < cglist.size(); i++) {
										GhJslw jsxm = (GhJslw) cglist.get(i);
										jslwService.delete(jsxm);
									}
								}
								List xmzzlist = xmzzService.findByLwidAndKuid(lw.getLwId(), GhXmzz.HYLW);
								if (xmzzlist != null && xmzzlist.size() != 0) {
									for (int i = 0; i < xmzzlist.size(); i++) {
										GhXmzz xmzz = (GhXmzz) xmzzlist.get(i);
										xmzzService.delete(xmzz);
									}
								}
								xmService.delete(lw);
								initWindow();
							}
						}
					}
				});
				c7.appendChild(gn);
				Listcell c8 = new Listcell();
				InnerButton IB = new InnerButton();
				if (lw.getAuditState() == null) {
					c8.setLabel("δ���");
				} else if (lw.getAuditState() != null && lw.getAuditState().shortValue() == GhXm.AUDIT_NO) {
					ib.setLabel("δͨ��");
					c8.appendChild(ib);
					ib.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event arg0) throws Exception {
							Map arg = new HashMap();
							arg.put("type", 13);
							arg.put("id", lw.getLwId());
							CkshyjWindow cw = (CkshyjWindow) Executions.createComponents("/admin/personal/data/teacherinfo/jxqk/ckpsyj.zul", null, arg);
							cw.initWindow();
							cw.doHighlighted();
						}
					});
				} else if (lw.getAuditState() != null && lw.getAuditState().shortValue() == GhXm.AUDIT_YES) {
					ib.setLabel("ͨ��");
					c8.appendChild(ib);
					ib.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event arg0) throws Exception {
							Map arg = new HashMap();
							arg.put("type", 13);
							arg.put("id", lw.getLwId());
							CkshyjWindow cw = (CkshyjWindow) Executions.createComponents("/admin/personal/data/teacherinfo/jxqk/ckpsyj.zul", null, arg);
							cw.initWindow();
							cw.doHighlighted();
						}
					});
				}
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
				arg0.appendChild(c5);
				arg0.appendChild(c6);
				arg0.appendChild(c7);
				arg0.appendChild(c8);
			}
		});
	}

	@Override
	public void initWindow() {
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		// ��ѧ�����������
		List list10 = hylwService.findByKuidAndType(user.getKuId(), GhHylw.JYLW, GhJslw.JYHY);
		listbox10.setModel(new ListModelList(list10));
	}

	public void onClick$button10() {
		final JylwqkWindow cgWin = (JylwqkWindow) Executions.createComponents("/admin/personal/data/teacherinfo/jxqk/jylwqk.zul", null, null);
		cgWin.setKuid(user.getKuId());
		cgWin.doHighlighted();
		cgWin.initWindow();
		cgWin.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				initWindow();
				cgWin.detach();
			}
		});
	}

	public void onClick$button10out() throws IOException, WriteException {
		// ��ѧ�����������
		List list10 = hylwService.findByKuidAndType(user.getKuId(), GhHylw.JYLW, GhJslw.JYHY);
		if (list10 == null || list10.size() == 0) {
			try {
				Messagebox.show("�����ݣ���������", "����", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		} else {
			String fileName = "ʵ���ѧ�ĸ��о����ģ��������ģ����.xls";
			String Title = "ʵ���ѧ�ĸ��о����ģ��������ģ����";
			WritableWorkbook workbook;
			List titlelist = new ArrayList();
			titlelist.add("���");
			titlelist.add("��������");
			titlelist.add("����ʱ��");
			titlelist.add("�����������ļ�����");
			titlelist.add("����ʱ��");
			titlelist.add("����ص�");
			titlelist.add("���쵥λ");
			titlelist.add("����Ϊ�ڼ�����");
			titlelist.add("ȫ������");
			titlelist.add("��/��/ҳ��");
			titlelist.add("���浥λ");
			titlelist.add("��¼���");
			titlelist.add("��¼���");
			titlelist.add("����ָ��");
			titlelist.add("��Ŀ����");
			titlelist.add("�о�����");
			titlelist.add("��ע");
			String c[][] = new String[list10.size()][titlelist.size()];
			// ��SQL�ж�����
			for (int j = 0; j < list10.size(); j++) {
				GhHylw lw = (GhHylw) list10.get(j);
				c[j][0] = j + 1 + "";
				c[j][1] = lw.getLwMc();
				c[j][2] = lw.getLwFbsj();
				c[j][3] = lw.getLwKw();
				c[j][4] = lw.getLwTime();
				c[j][5] = lw.getLwPlace();
				c[j][6] = lw.getLwHost();
				GhJslw jslw = jslwService.findByKuidAndLwidAndType(user.getKuId(), lw.getLwId(), GhJslw.JYHY);
				if (jslw.getLwSelfs() != null) {
					c[j][7] = jslw.getLwSelfs().toString();
				} else {
					c[j][7] = "";
				}
				c[j][8] = lw.getLwAll();
				c[j][9] = lw.getLwPages();
				c[j][10] = lw.getLwPublish();
				if (lw.getLwRecord() == null || lw.getLwRecord().trim().equalsIgnoreCase("-1")) {
					c[j][11] = "";
				} else if (lw.getLwRecord().equalsIgnoreCase("1")) {
					c[j][11] = "SCI(��ѧ��������)��¼";
				} else if (lw.getLwRecord().equalsIgnoreCase("2")) {
					c[j][11] = "EI(��������)��¼";
				} else if (lw.getLwRecord().equalsIgnoreCase("3")) {
					c[j][11] = "ISTP(�Ƽ�����¼���� )��¼";
				} else if (lw.getLwRecord().equalsIgnoreCase("4")) {
					c[j][11] = "CSCD(�й���ѧ�������ݿ�)��¼";
				} else if (lw.getLwRecord().equalsIgnoreCase("5")) {
					c[j][11] = "CSSCI(��������ѧ��������)��¼";
				} else if (lw.getLwRecord().equalsIgnoreCase("6")) {
					c[j][11] = "SSCI(����ѧ��������)��¼";
				} else if (lw.getLwRecord().equalsIgnoreCase("7")) {
					c[j][11] = "A&HCI(���������Ŀ�ѧ��������)��¼";
				} else if (lw.getLwRecord().equalsIgnoreCase("8")) {
					c[j][11] = "���»���ժ�� ȫ��ת��";
				} else if (lw.getLwRecord().equalsIgnoreCase("9")) {
					c[j][11] = "���й���ѧ��ժ��ȫ��ת��";
				} else if (lw.getLwRecord().equalsIgnoreCase("10")) {
					c[j][11] = "���й�����ѧ��ժ��ȫ��ת��";
				} else if (lw.getLwRecord().equalsIgnoreCase("11")) {
					c[j][11] = "���й���ѧ��ȫ��ת��";
				} else if (lw.getLwRecord().equalsIgnoreCase("12")) {
					c[j][11] = "���й��˴�ӡ���ϡ�ȫ��ת��";
				} else if (lw.getLwRecord().equalsIgnoreCase("13")) {
					c[j][11] = "��ȫ���ߵ�ԺУ�Ŀ�ѧ����ժ��ȫ��ת��";
				} else if (lw.getLwRecord().equalsIgnoreCase("14")) {
					c[j][11] = "���»���ժ��ժҪת��";
				} else if (lw.getLwRecord().equalsIgnoreCase("15")) {
					c[j][11] = "���й�����ѧ��ժ��ժҪת��";
				} else if (lw.getLwRecord().equalsIgnoreCase("16")) {
					c[j][11] = "���й��˴�ӡ���ϡ�ժҪת��";
				} else if (lw.getLwRecord().equalsIgnoreCase("17")) {
					c[j][11] = "���й���ѧ��ժҪת��";
				} else {
					c[j][11] = "";
				}
				c[j][12] = lw.getLwNum();
				// ָ�����
				if ((lw.getLwZd() == null || lw.getLwZd().equals("")) || (lw.getLwZd().equals(GhHylw.LWZDXS.shortValue()))) {
					c[j][13] = "ָ��ѧ��";
				} else {
					c[j][13] = "��ʦ����";
				}
				// ��Ŀ����
				String xmname = "";
				List xmzzlist = xmzzService.findByLwidAndKuid(lw.getLwId(), GhXmzz.HYLW);
				if (xmzzlist != null && xmzzlist.size() != 0) {
					if (xmzzlist.size() != 1) {
						for (int i = 0; i < xmzzlist.size() - 1; i++) {
							GhXmzz xmzz = (GhXmzz) xmzzlist.get(i);
							GhXm xm = (GhXm) xmService.get(GhXm.class, xmzz.getKyId());
							xmname = xmname + xm.getKyMc() + ",";
						}
						GhXmzz xmzzz = (GhXmzz) xmzzlist.get(xmzzlist.size() - 1);
						GhXm xm = (GhXm) xmService.get(GhXm.class, xmzzz.getKyId());
						xmname = xmname + xm.getKyMc();
					} else {
						GhXmzz xmzz = (GhXmzz) xmzzlist.get(0);
						GhXm xm = (GhXm) xmService.get(GhXm.class, xmzz.getKyId());
						xmname = xmname + xm.getKyMc();
					}
				}
				c[j][14] = xmname;
				// �о�����
				if (jslw != null && jslw.getYjId() != null && jslw.getYjId().intValue() != 0) {
					c[j][15] = jslw.getYjfx().getGyName();
				} else {
					c[j][15] = "";
				}
				c[j][16] = lw.getLwRemark();
			}
			ExportExcel ee = new ExportExcel();
			ee.exportExcel(fileName, Title, titlelist, list10.size(), c);
		}
	}
}
