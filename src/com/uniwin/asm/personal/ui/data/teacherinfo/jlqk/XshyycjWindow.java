package com.uniwin.asm.personal.ui.data.teacherinfo.jlqk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.common.util.ExportExcel;
import org.iti.gh.entity.GhXshy;
import org.iti.gh.service.CgService;
import org.iti.gh.service.FmzlService;
import org.iti.gh.service.JlhzService;
import org.iti.gh.service.JxbgService;
import org.iti.gh.service.KyjhService;
import org.iti.gh.service.LwzlService;
import org.iti.gh.service.PxService;
import org.iti.gh.service.QkdcService;
import org.iti.gh.service.QtService;
import org.iti.gh.service.SkService;
import org.iti.gh.service.XmService;
import org.iti.gh.service.XshyService;
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

import com.uniwin.asm.personal.ui.data.teacherinfo.CkshyjWindow;
import com.uniwin.asm.personal.ui.data.teacherinfo.YqGjxshyWindow;
import com.uniwin.framework.entity.WkTUser;

public class XshyycjWindow extends BaseWindow {

	// ���ʹ���ѧ�����飨Ԥ�ڲμӣ�

	Button button18;
	Listbox listbox18;
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
	QkdcService qkdcService;
	CgService cgService;
	LwzlService lwzlService;
	WkTUser user;
	@Override
	public void initShow() {
		initWindow();
		listbox18.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhXshy xs = (GhXshy) arg1;
				// ���
				Listcell c1 = new Listcell(arg0.getIndex() + 1 + "");
				// ��������
				Listcell c2 = new Listcell();
				InnerButton ib = new InnerButton();
				ib.setLabel(xs.getHyMc());
				ib.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						final YqGjxshyWindow cgWin = (YqGjxshyWindow) Executions
								.createComponents(
										"/admin/personal/data/teacherinfo/yqgjxshy.zul",
										null, null);
						cgWin.setXs(xs);
						cgWin.doHighlighted();
						cgWin.initWindow();
						cgWin.addEventListener(Events.ON_CHANGE,
								new EventListener() {

									public void onEvent(Event arg0)
											throws Exception {
										initWindow();
										cgWin.detach();
									}
								});
					}
				});
				c2.appendChild(ib);

				// �ٰ����ʱ��

				Listcell c3 = new Listcell(xs.getHySj());

				// ����������
				Listcell c4 = new Listcell();
				if (xs.getHyZrs() != null) {
					c4.setLabel(xs.getHyZrs() + "");

				}

				// ������Ա��
				Listcell c5 = new Listcell();
				if (xs.getHyJwrs() != null) {
					c5.setLabel(xs.getHyJwrs() + "");
				} else {
					c5.setLabel("0");
				}

				// ����
				Listcell c6 = new Listcell();

				InnerButton gn = new InnerButton();
				gn.setLabel("ɾ��");
				gn.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						if (Messagebox.show("ȷ��ɾ����?", "��ʾ", Messagebox.OK
								| Messagebox.CANCEL, Messagebox.QUESTION) == 1) {
							xmService.delete(xs);
							initWindow();
						}
					}
				});
				c6.appendChild(gn);
				Listcell c7=new Listcell();
				InnerButton IB = new InnerButton();
				if(xs.getAuditState()==null){
					c7.setLabel("δ���");
				}else if(xs.getAuditState()!=null&&xs.getAuditState().shortValue()==GhXshy.AUDIT_NO){
					ib.setLabel("δͨ��");
					c7.appendChild(ib);
					ib.addEventListener(Events.ON_CLICK, new EventListener(){

						public void onEvent(Event arg0) throws Exception {
						 Map arg=new HashMap();
						 arg.put("type", 19);
						 arg.put("id",xs.getHyId());
						 CkshyjWindow cw=(CkshyjWindow)Executions.createComponents("/admin/personal/data/teacherinfo/ckpsyj.zul", null, arg);
						 cw.initWindow();
						 cw.doHighlighted();
						}
						
					});
				}else if(xs.getAuditState()!=null&&xs.getAuditState().shortValue()==GhXshy.AUDIT_YES){
					ib.setLabel("ͨ��");
					c7.appendChild(ib);
					ib.addEventListener(Events.ON_CLICK, new EventListener(){

						public void onEvent(Event arg0) throws Exception {
						 Map arg=new HashMap();
						 arg.put("type", 19);
						 arg.put("id",xs.getHyId());
						 CkshyjWindow cw=(CkshyjWindow)Executions.createComponents("/admin/personal/data/teacherinfo/ckpsyj.zul", null, arg);
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

			}
		});

	}

	@Override
	public void initWindow() {
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		// ���ʹ���ѧ�����飨Ԥ�ڲμӣ�
		List list18 = xshyService.findByKuid(user.getKuId(), GhXshy.IFCJ_NO);
		listbox18.setModel(new ListModelList(list18));

	}
	public void onClick$button18() {
		final YqGjxshyWindow cgWin = (YqGjxshyWindow) Executions
				.createComponents(
						"/admin/personal/data/teacherinfo/yqgjxshy.zul", null,
						null);
		cgWin.setKuid(user.getKuId());
		cgWin.doHighlighted();
		
		cgWin.addEventListener(Events.ON_CHANGE, new EventListener() {

			public void onEvent(Event arg0) throws Exception {
				initWindow();
				cgWin.detach();
			}
		});
		cgWin.initWindow();

	}
	public void onClick$button18out() throws IOException, WriteException {
		// ���ʹ���ѧ�����飨Ԥ�ڲμӣ�
		List list18 = xshyService.findByKuid(user.getKuId(), GhXshy.IFCJ_NO);
		if (list18 == null || list18.size() == 0) {
			try {
				Messagebox.show("�����ݣ���������", "����", Messagebox.OK,
						Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		} else {
			String fileName = "���ʹ���ѧ�����飨Ԥ�ڲμӣ�.xls";
			String Title = "���ʹ���ѧ�����飨Ԥ�ڲμӣ� ";
			WritableWorkbook workbook;
			List titlelist = new ArrayList();
			titlelist.add("���");
			titlelist.add("��������");

			titlelist.add("����ʱ��");
			titlelist.add("����ص�");
			titlelist.add("��������");
			titlelist.add("��������");
			titlelist.add("��������");
			titlelist.add("�������ã�����/���֣�");
			titlelist.add("��ע");
			String c[][] = new String[list18.size()][titlelist.size()];
			// ��SQL�ж�����
			for (int j = 0; j < list18.size(); j++) {
				GhXshy xs = (GhXshy) list18.get(j);
				c[j][0] = j + 1 + "";
				c[j][1] = xs.getHyMc();
				c[j][2] = xs.getHySj();
				c[j][3] = xs.getHyPlace();
				c[j][4] = xs.getHyPlace();
				c[j][5] = xs.getHyZrs().toString();
				c[j][6] = xs.getHyJwrs().toString();
				if (xs.getHyEffect() == GhXshy.EFF_CJ) {
					c[j][7] = "�μ�";
				} else if (xs.getHyEffect() == GhXshy.EFF_DL) {
					c[j][7] = "����";
				} else {
					c[j][7] = "����";
				}

				c[j][8] = xs.getHyRemarks();

			}
			ExportExcel ee = new ExportExcel();
			ee.exportExcel(fileName, Title, titlelist, list18.size(), c);
		}
	}


}
