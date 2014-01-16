package com.uniwin.asm.personal.ui.data.teacherinfo.kyqk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import org.iti.gh.service.PxService;
import org.iti.gh.service.QkdcService;
import org.iti.gh.service.QtService;
import org.iti.gh.service.RjzzService;
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
import com.uniwin.asm.personal.ui.data.teacherinfo.FblwqkWindow;
import com.uniwin.asm.personal.ui.data.teacherinfo.KyxmzzlistWindow;
import com.uniwin.framework.common.fileuploadex.UploadFJ;
import com.uniwin.framework.entity.WkTUser;

public class HylwWindow extends BaseWindow {
	// ��������������
	Toolbarbutton button3;
	Listbox listbox3;
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
	RjzzService rjzzService;
	YjfxService yjfxService;
	GhFileService ghfileService;
	WkTUser user;
	JslwService jslwService;
	JsxmService jsxmService;
	HylwService hylwService;

	@Override
	public void initShow() {
		initWindow();
		listbox3.setItemRenderer(new ListitemRenderer() {
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
						final FblwqkWindow cgWin = (FblwqkWindow) Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/hylw/fblwqk.zul", null, null);
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
						KyxmzzlistWindow cgWin = (KyxmzzlistWindow) Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/hylw/kyxmzzlist.zul", null, null);
						cgWin.setKuid(lw.getKuId());
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
								List xmzzlist = xmzzService.findByLwidAndKuid(lw.getLwId(), GhXmzz.HYLW);
								if (xmzzlist != null && xmzzlist.size() != 0) {
									for (int i = 0; i < xmzzlist.size(); i++) {
										GhXmzz xmzz = (GhXmzz) xmzzlist.get(i);
										xmzzService.delete(xmzz);
									}
								}
								List cglist = jslwService.findByLwidAndType(lw.getLwId(), GhJslw.KYHY);
								if (cglist != null && cglist.size() != 0) {
									for (int i = 0; i < cglist.size(); i++) {
										GhJslw jslw = (GhJslw) cglist.get(i);
										jslwService.delete(jslw);
									}
								}
								// ɾ������
								List list = ghfileService.findByFxmIdandFType(lw.getLwId(), 3);
								if (list.size() > 0) {
									// ���ڸ���
									for (int i = 0; i < list.size(); i++) {
										UploadFJ ufj = new UploadFJ(false);
										ufj.ReadFJ(getDesktop(), (GhFile) list.get(i));
										ufj.DeleteFJ();
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
				InnerButton i = new InnerButton();
				if (lw.getAuditState() == null) {
					c8.setLabel("δ���");
				} else if (lw.getAuditState() != null && lw.getAuditState().shortValue() == GhHylw.AUDIT_NO) {
					i.setLabel("δͨ��");
					c8.appendChild(i);
					i.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event arg0) throws Exception {
							Map arg = new HashMap();
							arg.put("type", 3);
							arg.put("id", lw.getLwId());
							CkshyjWindow cw = (CkshyjWindow) Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/ckpsyj.zul", null, arg);
							cw.initWindow();
							cw.doHighlighted();
						}
					});
				} else if (lw.getAuditState() != null && lw.getAuditState().shortValue() == GhHylw.AUDIT_YES) {
					i.setLabel("ͨ��");
					c8.appendChild(i);
					i.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event arg0) throws Exception {
							Map arg = new HashMap();
							arg.put("type", 3);
							arg.put("id", lw.getLwId());
							CkshyjWindow cw = (CkshyjWindow) Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/ckpsyj.zul", null, arg);
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
		// ������л����������
		List list3 = hylwService.findByKuidAndType(user.getKuId(), GhHylw.KYLW, GhJslw.KYHY);
		listbox3.setModel(new ListModelList(list3));
	}

	public void onClick$button3() {
		final FblwqkWindow cgWin = (FblwqkWindow) Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/hylw/fblwqk.zul", null, null);
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

	public void onClick$button3out() throws IOException, WriteException {
		// ��������������
		List list3 = hylwService.findByKuidAndType(user.getKuId(), GhHylw.KYLW, GhJslw.KYHY);
		if (list3.size() == 0) {
			try {
				Messagebox.show("�����ݣ���������", "����", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		} else {
			Date now = new Date();
			String fileName = "����������ģ��������ģ����.xls";
			String Title = "����������ģ��������ģ����";
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
			titlelist.add("��Ŀ����");
			titlelist.add("�о�����");
			titlelist.add("��ע");
			String c[][] = new String[list3.size()][titlelist.size()];
			// ��SQL�ж�����
			for (int j = 0; j < list3.size(); j++) {
				GhHylw lw = (GhHylw) list3.get(j);
				c[j][0] = j + 1 + "";
				c[j][1] = lw.getLwMc();
				c[j][2] = lw.getLwFbsj();
				c[j][3] = lw.getLwKw();
				c[j][4] = lw.getLwTime();
				c[j][5] = lw.getLwPlace();
				c[j][6] = lw.getLwHost();
				GhJslw gj = jslwService.findByKuidAndLwidAndType(user.getKuId(), lw.getLwId(), GhJslw.KYHY);
				if (gj != null && gj.getLwSelfs() != null) {
					c[j][7] = gj.getLwSelfs().toString();
				} else {
					c[j][7] = "";
				}
				c[j][8] = lw.getLwAll();
				c[j][9] = lw.getLwPages();
				c[j][10] = lw.getLwPublish();
				// ��¼�б�
				if (lw.getLwRecord() == null || lw.getLwRecord().equals("") || lw.getLwRecord().trim().equalsIgnoreCase("-1")) {
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
				c[j][13] = xmname;
				if (gj != null && gj.getYjId() != null && gj.getYjId().intValue() != 0) {
					c[j][14] = gj.getYjfx().getGyName();
				} else {
					c[j][14] = "";
				}
				c[j][15] = lw.getLwRemark();
			}
			ExportExcel ee = new ExportExcel();
			ee.exportExcel(fileName, Title, titlelist, list3.size(), c);
		}
	}
}
