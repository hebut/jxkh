package com.uniwin.asm.personal.ui.data.teacherinfo.kyqk;

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
import org.iti.gh.service.PxService;
import org.iti.gh.service.QkdcService;
import org.iti.gh.service.QklwService;
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
import com.uniwin.asm.personal.ui.data.teacherinfo.KyqklwqkWindow;
import com.uniwin.asm.personal.ui.data.teacherinfo.KyxmzzlistWindow;
import com.uniwin.framework.common.fileuploadex.UploadFJ;
import com.uniwin.framework.entity.WkTUser;

public class QklwWindow extends BaseWindow {
	// �����ڿ��������
	Toolbarbutton button31;
	Listbox listbox31;
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
	QklwService qklwService;

	public void initShow() {
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		initWindow();
		listbox31.setItemRenderer(new ListitemRenderer() {
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
						final KyqklwqkWindow cgWin = (KyqklwqkWindow) Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/qklw/kyqklwqk.zul", null, null);
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
				// ISSN/CN
				Listcell c7 = new Listcell(lw.getLwPages());
				// ��Ŀ����
				Listcell c8 = new Listcell();
				InnerButton bt = new InnerButton("�鿴/�༭");
				bt.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						KyxmzzlistWindow cgWin = (KyxmzzlistWindow) Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/qklw/kyxmzzlist.zul", null, null);
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

						if(lw.getKuId().intValue()!=user.getKuId().intValue()){
							Messagebox.show("�����Ǵ���Ŀ�ύ�ˣ��ʲ���ɾ����","��ʾ", Messagebox.OK,Messagebox.EXCLAMATION);
						}else{
						if(Messagebox.show("ȷ��ɾ����?", "��ʾ", Messagebox.OK|Messagebox.CANCEL,
								Messagebox.QUESTION)==1){
							//ɾ������
							List list=ghfileService.findByFxmIdandFType(lw.getLwId(), 4);
							if(list.size()>0){
								//���ڸ���
								//List[] fjList=new List[list.size()];
								for(int i=0;i<list.size();i++){
									UploadFJ ufj=new UploadFJ(false);
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
							List cglist =jslwService.findByLwidAndType(lw.getLwId(), GhJslw.KYQK);
							if(cglist != null && cglist.size() != 0){
								for(int i=0;i<cglist.size();i++){
									GhJslw jslw=(GhJslw)cglist.get(i);
									jslwService.delete(jslw);
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
				InnerButton Ib = new InnerButton();
				if (lw.getAuditState() == null) {
					c10.setLabel("δ���");
				} else if (lw.getAuditState() != null && lw.getAuditState().shortValue() == GhQklw.AUDIT_NO) {
					Ib.setLabel("δͨ��");
					c10.appendChild(Ib);
					Ib.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event arg0) throws Exception {
							Map arg = new HashMap();
							arg.put("type", 4);
							arg.put("id", lw.getLwId());
							CkshyjWindow cw = (CkshyjWindow) Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/ckpsyj.zul", null, arg);
							cw.initWindow();
							cw.doHighlighted();
						}
					});
				} else if (lw.getAuditState() != null && lw.getAuditState().shortValue() == GhQklw.AUDIT_YES) {
					Ib.setLabel("ͨ��");
					c10.appendChild(Ib);
					Ib.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event arg0) throws Exception {
							Map arg = new HashMap();
							arg.put("type", 4);
							arg.put("id", lw.getLwId());
							CkshyjWindow cw = (CkshyjWindow) Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/ckpsyj.zul", null, arg);
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

	public void initWindow() {
		// ��������ڿ��������
		List list31 = qklwService.findByKuidAndType(user.getKuId(), GhQklw.KYLW, GhJslw.KYQK);
		listbox31.setModel(new ListModelList(list31));
	}

	public void onClick$button31() {
		// ��������ڿ��������
		final KyqklwqkWindow cgWin = (KyqklwqkWindow) Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/qklw/kyqklwqk.zul", null, null);
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

	public void onClick$button31out() throws IOException, WriteException {
		// �����ڿ��������
		List list31 = qklwService.findByKuidAndType(user.getKuId(), GhQklw.KYLW, GhJslw.KYQK);
		if (list31 == null || list31.size() == 0) {
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
			titlelist.add("��¼���");
			titlelist.add("��¼���");
			titlelist.add("��Ŀ����");
			titlelist.add("�о�����");
			titlelist.add("��ע");
			String c[][] = new String[list31.size()][titlelist.size()];
			// ��SQL�ж�����
			for (int j = 0; j < list31.size(); j++) {
				GhQklw lw = (GhQklw) list31.get(j);
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
				GhJslw jslw = jslwService.findByKuidAndLwidAndType(user.getKuId(), lw.getLwId(), GhJslw.KYQK);
				if (jslw != null && jslw.getLwSelfs() != null) {
					c[j][10] = jslw.getLwSelfs().toString();
				} else if (jslw.getLwSelfs() != null) {
					c[j][10] = "";
				}
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
				List xmzzlist = xmzzService.findByLwidAndKuid(lw.getLwId(), GhXmzz.QKLW);
				if (xmzzlist == null && xmzzlist.size() == 0) {
					c[j][13] = xmname;
				} else if (xmzzlist != null && xmzzlist.size() != 0) {
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
					c[j][13] = xmname;
				}
				if (jslw != null && jslw.getYjId() != null && jslw.getYjId() != 0L) {
					c[j][14] = jslw.getYjfx().getGyName();
				} else {
					c[j][14] = "";
				}
				c[j][15] = lw.getLwRemark();
			}
			ExportExcel ee = new ExportExcel();
			ee.exportExcel(fileName, Title, titlelist, list31.size(), c);
		}
	}
}
