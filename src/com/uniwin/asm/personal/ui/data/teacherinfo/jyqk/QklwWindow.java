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
import org.iti.gh.entity.GhJslw;
import org.iti.gh.entity.GhQklw;
import org.iti.gh.entity.GhXm;
import org.iti.gh.entity.GhXmzz;
import org.iti.gh.entity.GhYjfx;
import org.iti.gh.service.CgService;
import org.iti.gh.service.FmzlService;
import org.iti.gh.service.GhFileService;
import org.iti.gh.service.JlhzService;
import org.iti.gh.service.JslwService;
import org.iti.gh.service.JsxmService;
import org.iti.gh.service.JxbgService;
import org.iti.gh.service.KyjhService;
import org.iti.gh.service.LwzlService;
import org.iti.gh.service.PkpyService;
import org.iti.gh.service.PxService;
import org.iti.gh.service.QkdcService;
import org.iti.gh.service.QklwService;
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
import com.uniwin.asm.personal.ui.data.teacherinfo.JyqklwqkWindow;
import com.uniwin.asm.personal.ui.data.teacherinfo.JyxmzzlistWindow;
import com.uniwin.framework.common.fileuploadex.UploadFJ;
import com.uniwin.framework.entity.WkTUser;

public class QklwWindow extends BaseWindow {
	// �ڿ��������
	Toolbarbutton button101;
	Listbox listbox101;
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
	JslwService jslwService;
	JsxmService jsxmService;
	QklwService qklwService;

	@Override
	public void initShow() {
		initWindow();
		listbox101.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhQklw lw = (GhQklw) arg1;
				// ���
				Listcell c1 = new Listcell(arg0.getIndex() + 1 + "");
				// ��������
				Listcell c2 = new Listcell();
				InnerButton ib = new InnerButton();
				ib.setLabel(lw.getLwMc());
				ib.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						final JyqklwqkWindow cgWin = (JyqklwqkWindow) Executions.createComponents("/admin/personal/data/teacherinfo/jxqk/jyqklwqk.zul", null, null);
						cgWin.setLw(lw);
						cgWin.setKuid(lw.getKuId());
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
				// ��������
				Listcell c3 = new Listcell(lw.getLwKw());
				// ����ʱ��
				Listcell c4 = new Listcell(lw.getLwFbsj());
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
//				// �Ƿ�����ڿ�
//				if (lw.getLwCenter().shortValue() == GhQklw.LWHX_NO.shortValue()) {
//					Listcell c5 = new Listcell("��");
//					arg0.appendChild(c5);
//				} else {
//					Listcell c5 = new Listcell("��");
//					arg0.appendChild(c5);
//				}
//				// ����Ӱ������
//				Listcell c6 = new Listcell(lw.getLwFactor());
//				// ISSN/CN
				Listcell c7 = new Listcell(lw.getLwPages());
				// ��Ŀ����
				Listcell c8 = new Listcell();
				InnerButton bt = new InnerButton("�鿴/�༭");
				bt.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						JyxmzzlistWindow cgWin = (JyxmzzlistWindow) Executions.createComponents("/admin/personal/data/teacherinfo/jxqk/jyxmzzlist.zul", null, null);
						cgWin.setKuid(lw.getKuId());
						cgWin.setQklw(lw);
						cgWin.initWindow();
						cgWin.doHighlighted();
					}
				});
				c8.appendChild(bt);
				// ����
				Listcell c9 = new Listcell();
				InnerButton gn = new InnerButton();
				gn.setLabel("ɾ��");
				gn.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						if (lw.getKuId().intValue() != user.getKuId().intValue()) {
							Messagebox.show("�����Ǵ���Ŀ�ύ�ˣ��ʲ���ɾ����","��ʾ", Messagebox.OK,Messagebox.EXCLAMATION);
						} else {
							if (Messagebox.show("ȷ��ɾ����?", "��ʾ", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == 1) {
								// ɾ������
								List list = ghfileService.findByFxmIdandFType(lw.getLwId(), 14);
								if (list.size() > 0) {
									// ���ڸ���
									for (int i = 0; i < list.size(); i++) {
										UploadFJ ufj = new UploadFJ(false);
										ufj.ReadFJ(getDesktop(), (GhFile) list.get(i));
										ufj.DeleteFJ();
									}
								}
								List xmzzlist = xmzzService.findByLwidAndKuid(lw.getLwId(), GhXmzz.QKLW);
								if (xmzzlist != null && xmzzlist.size() != 0) {
									for (int i = 0; i < xmzzlist.size(); i++) {
										GhXmzz xmzz = (GhXmzz) xmzzlist.get(i);
										xmzzService.delete(xmzz);
									}
								}
								List cglist = jslwService.findByLwidAndType(lw.getLwId(), GhJslw.JYQK);
								if (cglist != null && cglist.size() != 0) {
									for (int i = 0; i < cglist.size(); i++) {
										GhJslw jsxm = (GhJslw) cglist.get(i);
										jslwService.delete(jsxm);
									}
								}
								xmService.delete(lw);
								initWindow();
							}
						}
					}
				});
				c9.appendChild(gn);
				Listcell c10 = new Listcell();
				InnerButton IB = new InnerButton();
				if (lw.getAuditState() == null) {
					c10.setLabel("δ���");
				} else if (lw.getAuditState() != null && lw.getAuditState().shortValue() == GhXm.AUDIT_NO) {
					ib.setLabel("δͨ��");
					c10.appendChild(ib);
					ib.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event arg0) throws Exception {
							Map arg = new HashMap();
							arg.put("type", 14);
							arg.put("id", lw.getLwId());
							CkshyjWindow cw = (CkshyjWindow) Executions.createComponents("/admin/personal/data/teacherinfo/jxqk/ckpsyj.zul", null, arg);
							cw.initWindow();
							cw.doHighlighted();
						}
					});
				} else if (lw.getAuditState() != null && lw.getAuditState().shortValue() == GhXm.AUDIT_YES) {
					ib.setLabel("ͨ��");
					c10.appendChild(ib);
					ib.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event arg0) throws Exception {
							Map arg = new HashMap();
							arg.put("type", 14);
							arg.put("id", lw.getLwId());
							CkshyjWindow cw = (CkshyjWindow) Executions.createComponents("/admin/personal/data/teacherinfo/jxqk/ckpsyj.zul", null, arg);
							cw.initWindow();
							cw.doHighlighted();
						}
					});
				}
//				arg0.appendChild(c6);
				arg0.appendChild(c7);
				arg0.appendChild(c8);
				arg0.appendChild(c9);
				arg0.appendChild(c10);
			}
		});
	}

	@Override
	public void initWindow() {
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		// ��ѧ�ڿ��������
		List list101 = qklwService.findByKuidAndType(user.getKuId(), GhQklw.JYLW, GhJslw.JYQK);
		listbox101.setModel(new ListModelList(list101));
	}

	public void onClick$button101() {
		final JyqklwqkWindow cgWin = (JyqklwqkWindow) Executions.createComponents("/admin/personal/data/teacherinfo/jxqk/jyqklwqk.zul", null, null);
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

	public void onClick$button101out() throws IOException, WriteException {
		// ��ѧ�ڿ��������
		List list10 = qklwService.findByKuidAndType(user.getKuId(), GhQklw.JYLW, GhJslw.JYQK);
		if (list10 == null || list10.size() == 0) {
			try {
				Messagebox.show("�����ݣ���������", "����", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		} else {
			String fileName = "����������ģ��ڿ����ģ����.xls";
			String Title = "����������ģ��ڿ����ģ����";
			WritableWorkbook workbook;
			List titlelist = new ArrayList();
			titlelist.add("���");
			titlelist.add("��������");
			titlelist.add("��������");
			titlelist.add("����ʱ��");
			titlelist.add("�Ƿ�����ڿ�");
			titlelist.add("����Ӱ������");
			titlelist.add("ISSN/CN");
			titlelist.add("�����ڿ�Ŀ¼λ��");
			titlelist.add("��/��/ҳ��");
			titlelist.add("ȫ������");
			titlelist.add("����Ϊ�ڼ�����");
			// titlelist.add("���쵥λ");
			titlelist.add("��¼���");
			titlelist.add("��¼���");
			titlelist.add("��Ŀ����");
			titlelist.add("�о�����");
			titlelist.add("��ע");
			String c[][] = new String[list10.size()][titlelist.size()];
			// ��SQL�ж�����
			for (int j = 0; j < list10.size(); j++) {
				GhQklw lw = (GhQklw) list10.get(j);
				c[j][0] = j + 1 + "";
				c[j][1] = lw.getLwMc();
				c[j][2] = lw.getLwKw();
				c[j][3] = lw.getLwFbsj();
				// �Ƿ�����ڿ�
				if (lw.getLwCenter() == null || lw.getLwCenter().equals("") || lw.getLwCenter().equals(GhQklw.LWHX_NO.shortValue())) {
					c[j][4] = "��";
				} else {
					c[j][4] = "��";
				}
				c[j][5] = lw.getLwFactor();
				c[j][6] = lw.getLwIssn();
				c[j][7] = lw.getLwLocation();
				c[j][8] = lw.getLwPages();
				c[j][9] = lw.getLwAll();
				GhJslw jslw = jslwService.findByKuidAndLwidAndType(user.getKuId(), lw.getLwId(), GhJslw.JYQK);
				if (jslw != null && jslw.getLwSelfs() != null) {
					c[j][10] = jslw.getLwSelfs().toString();
				} else {
					c[j][10] = "";
				}
				// c[j][11]=lw.getLwHost();
				// ��¼�б�
				if (lw.getLwRecord() == null || lw.getLwRecord().equals("")) {
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
				// ��Ŀ����
				String xmname = "";
				List xmzzlist = xmzzService.findByLwidAndKuid(lw.getLwId(), GhXmzz.QKLW);
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
				c[j][13] = xmname;
				// �о�����
				if (jslw != null && jslw.getYjId() != null) {
					c[j][14] = jslw.getYjfx().getGyName();
				} else {
					c[j][14] = "";
				}
				c[j][15] = lw.getLwRemark();
			}
			ExportExcel ee = new ExportExcel();
			ee.exportExcel(fileName, Title, titlelist, list10.size(), c);
		}
	}
}
