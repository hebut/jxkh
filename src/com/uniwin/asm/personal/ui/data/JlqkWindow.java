package com.uniwin.asm.personal.ui.data;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.common.util.ExportExcel;
import org.iti.gh.entity.GhJlhz;
import org.iti.gh.entity.GhJxbg;
import org.iti.gh.entity.GhXm;
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
import org.zkoss.io.Files;
import org.zkoss.util.media.Media;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import com.uniwin.asm.personal.ui.data.teacherinfo.CdgjhzWindow;
import com.uniwin.asm.personal.ui.data.teacherinfo.CkshyjWindow;
import com.uniwin.asm.personal.ui.data.teacherinfo.GjxshyWindow;
import com.uniwin.asm.personal.ui.data.teacherinfo.GwjxbgWindow;
import com.uniwin.asm.personal.ui.data.teacherinfo.YqCdgjhzWindow;
import com.uniwin.asm.personal.ui.data.teacherinfo.YqGjxshyWindow;
import com.uniwin.asm.personal.ui.data.teacherinfo.YqGwjxbgWindow;
import com.uniwin.framework.entity.WkTUser;

public class JlqkWindow extends BaseWindow {
	/* ѧ��������� */
	// ���ʹ���ѧ�����飨�Ѳμӣ�
	Button button17;
	Listbox listbox17;

	// ���ʹ���ѧ�����飨Ԥ�ڲμӣ�

	Button button18;
	Listbox listbox18;

	// ���⽲ѧ�ͱ���������ѽ��У�

	Button button19;
	Listbox listbox19;

	// ���⽲ѧ�ͱ��������Ԥ�ڽ��У�

	Button button20;
	Listbox listbox20;

	// �е����ʽ���������Ŀ���ѿ�չ��

	Button button21;
	Listbox listbox21;

	// �е����ʽ���������Ŀ��Ԥ�ڿ�չ��

	Button button22;
	Listbox listbox22;

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
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		initXsjl();
		listbox17.setItemRenderer(new ListitemRenderer() {
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
						final GjxshyWindow cgWin = (GjxshyWindow) Executions
								.createComponents(
										"/admin/personal/data/teacherinfo/gjxshy.zul",
										null, null);
						cgWin.setXs(xs);
						cgWin.doHighlighted();
						cgWin.initWindow();
						cgWin.addEventListener(Events.ON_CHANGE,
								new EventListener() {
									public void onEvent(Event arg0)
											throws Exception {
										initXsjl();
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
							initXsjl();
						}
					}
				});
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
				c6.appendChild(gn);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
				arg0.appendChild(c5);
				arg0.appendChild(c6);
				arg0.appendChild(c7);

			}
		});

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
										initXsjl();
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
							initXsjl();
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
		listbox19.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhJxbg bg = (GhJxbg) arg1;
				// ���
				Listcell c1 = new Listcell(arg0.getIndex() + 1 + "");
				// ��ѧ�򱨸�������
				Listcell c2 = new Listcell();
				InnerButton ib = new InnerButton();
				ib.setLabel(bg.getJxJxmc());
				ib.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						final GwjxbgWindow cgWin = (GwjxbgWindow) Executions
								.createComponents(
										"/admin/personal/data/teacherinfo/gwjxbg.zul",
										null, null);
						cgWin.setBg(bg);
						cgWin.doHighlighted();
						cgWin.initWindow();
						cgWin.addEventListener(Events.ON_CHANGE,
								new EventListener() {
									public void onEvent(Event arg0)
											throws Exception {
										initXsjl();
										cgWin.detach();
									}
								});
					}
				});
				c2.appendChild(ib);
				// �����ѧ���ƻ���ʻ�������
				Listcell c3 = new Listcell(bg.getJxHymc());

				// ��ѧ�򱨸�����
				Listcell c4 = new Listcell();
				if (bg.getJxBgmc() != null) {
					c4.setLabel(bg.getJxBgmc());
				}

				// ����ٰ�ʱ��
				Listcell c5 = new Listcell(bg.getJxSj());
				// ����
				Listcell c6 = new Listcell();

				InnerButton gn = new InnerButton();
				gn.setLabel("ɾ��");
				gn.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						if (Messagebox.show("ȷ��ɾ����?", "��ʾ", Messagebox.OK
								| Messagebox.CANCEL, Messagebox.QUESTION) == 1) {
							xmService.delete(bg);
							initXsjl();
						}
					}
				});
			
				c6.appendChild(gn);
				Listcell c7=new Listcell();
				InnerButton IB = new InnerButton();
				if(bg.getAuditState()==null){
					c7.setLabel("δ���");
				}else if(bg.getAuditState()!=null&&bg.getAuditState().shortValue()==GhJxbg.AUDIT_NO){
					ib.setLabel("δͨ��");
					c7.appendChild(ib);
					ib.addEventListener(Events.ON_CLICK, new EventListener(){

						public void onEvent(Event arg0) throws Exception {
						 Map arg=new HashMap();
						 arg.put("type", 20);
						 arg.put("id",bg.getJxId());
						 CkshyjWindow cw=(CkshyjWindow)Executions.createComponents("/admin/personal/data/teacherinfo/ckpsyj.zul", null, arg);
						 cw.initWindow();
						 cw.doHighlighted();
						}
						
					});
				}else if(bg.getAuditState()!=null&&bg.getAuditState().shortValue()==GhJxbg.AUDIT_YES){
					ib.setLabel("ͨ��");
					c7.appendChild(ib);
					ib.addEventListener(Events.ON_CLICK, new EventListener(){

						public void onEvent(Event arg0) throws Exception {
						 Map arg=new HashMap();
						 arg.put("type", 20);
						 arg.put("id",bg.getJxId());
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
		listbox20.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhJxbg bg = (GhJxbg) arg1;
				// ���
				Listcell c1 = new Listcell(arg0.getIndex() + 1 + "");
				// ��ѧ�򱨸�������
				Listcell c2 = new Listcell();
				InnerButton ib = new InnerButton();
				ib.setLabel(bg.getJxJxmc());
				ib.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						final YqGwjxbgWindow cgWin = (YqGwjxbgWindow) Executions
								.createComponents(
										"/admin/personal/data/teacherinfo/yqgwjxbg.zul",
										null, null);
						cgWin.setBg(bg);
						cgWin.doHighlighted();
						cgWin.initWindow();
						cgWin.addEventListener(Events.ON_CHANGE,
								new EventListener() {
									public void onEvent(Event arg0)
											throws Exception {
										initXsjl();
										cgWin.detach();
									}
								});
					}
				});
				c2.appendChild(ib);
				// �����ѧ���ƻ���ʻ�������
				Listcell c3 = new Listcell(bg.getJxHymc());

				// ��ѧ�򱨸�����
				Listcell c4 = new Listcell();
				if (bg.getJxBgmc() != null) {
					c4.setLabel(bg.getJxBgmc());
				}

				// ����ٰ�ʱ��
				Listcell c5 = new Listcell(bg.getJxSj());

				// ����
				Listcell c6 = new Listcell();

				InnerButton gn = new InnerButton();
				gn.setLabel("ɾ��");
				gn.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						if (Messagebox.show("ȷ��ɾ����?", "��ʾ", Messagebox.OK
								| Messagebox.CANCEL, Messagebox.QUESTION) == 1) {
							xmService.delete(bg);
							initXsjl();
						}
					}
				});
				Listcell c7=new Listcell();
				InnerButton IB = new InnerButton();
				if(bg.getAuditState()==null){
					c7.setLabel("δ���");
				}else if(bg.getAuditState()!=null&&bg.getAuditState().shortValue()==GhJxbg.AUDIT_NO){
					ib.setLabel("δͨ��");
					c7.appendChild(ib);
					ib.addEventListener(Events.ON_CLICK, new EventListener(){

						public void onEvent(Event arg0) throws Exception {
						 Map arg=new HashMap();
						 arg.put("type", 20);
						 arg.put("id",bg.getJxId());
						 CkshyjWindow cw=(CkshyjWindow)Executions.createComponents("/admin/personal/data/teacherinfo/ckpsyj.zul", null, arg);
						 cw.initWindow();
						 cw.doHighlighted();
						}
						
					});
				}else if(bg.getAuditState()!=null&&bg.getAuditState().shortValue()==GhJxbg.AUDIT_YES){
					ib.setLabel("ͨ��");
					c7.appendChild(ib);
					ib.addEventListener(Events.ON_CLICK, new EventListener(){

						public void onEvent(Event arg0) throws Exception {
						 Map arg=new HashMap();
						 arg.put("type", 20);
						 arg.put("id",bg.getJxId());
						 CkshyjWindow cw=(CkshyjWindow)Executions.createComponents("/admin/personal/data/teacherinfo/ckpsyj.zul", null, arg);
						 cw.initWindow();
						 cw.doHighlighted();
						}
						
					});
				}
				c6.appendChild(gn);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
				arg0.appendChild(c5);
				arg0.appendChild(c6);
				arg0.appendChild(c7);
			}
		});
		listbox21.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhJlhz jl = (GhJlhz) arg1;
				// ���
				Listcell c1 = new Listcell(arg0.getIndex() + 1 + "");
				// ���ʽ���������Ŀ����
				Listcell c2 = new Listcell();
				InnerButton ib = new InnerButton();
				ib.setLabel(jl.getHzMc());
				ib.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						final CdgjhzWindow cgWin = (CdgjhzWindow) Executions
								.createComponents(
										"/admin/personal/data/teacherinfo/cdgjhz.zul",
										null, null);
						cgWin.setJl(jl);
						cgWin.doHighlighted();
						cgWin.initWindow();
						cgWin.addEventListener(Events.ON_CHANGE,
								new EventListener() {
									public void onEvent(Event arg0)
											throws Exception {
										initXsjl();
										cgWin.detach();
									}
								});
					}
				});
				c2.appendChild(ib);
				// ��Ŀ��ֹʱ��
				Listcell c3 = new Listcell(jl.getHzKssj());
				Listcell c31 = new Listcell(jl.getHzJssj());
				// ��������
				Listcell c4 = new Listcell();
				if (jl.getHzDx() != null) {
					c4.setLabel(jl.getHzDx());
				}

				// ����
				Listcell c5 = new Listcell();

				InnerButton gn = new InnerButton();
				gn.setLabel("ɾ��");
				gn.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						if (Messagebox.show("ȷ��ɾ����?", "��ʾ", Messagebox.OK
								| Messagebox.CANCEL, Messagebox.QUESTION) == 1) {
							xmService.delete(jl);
							initXsjl();
						}
					}
				});

				c5.appendChild(gn);
				Listcell c6=new Listcell();
				InnerButton IB = new InnerButton();
				if(jl.getAuditState()==null){
					c6.setLabel("δ���");
				}else if(jl.getAuditState()!=null&&jl.getAuditState().shortValue()==GhJlhz.AUDIT_NO){
					ib.setLabel("δͨ��");
					c6.appendChild(ib);
					ib.addEventListener(Events.ON_CLICK, new EventListener(){

						public void onEvent(Event arg0) throws Exception {
						 Map arg=new HashMap();
						 arg.put("type", 21);
						 arg.put("id",jl.getHzId());
						 CkshyjWindow cw=(CkshyjWindow)Executions.createComponents("/admin/personal/data/teacherinfo/ckpsyj.zul", null, arg);
						 cw.initWindow();
						 cw.doHighlighted();
						}
						
					});
				}else if(jl.getAuditState()!=null&&jl.getAuditState().shortValue()==GhJlhz.AUDIT_YES){
					ib.setLabel("ͨ��");
					c6.appendChild(ib);
					ib.addEventListener(Events.ON_CLICK, new EventListener(){

						public void onEvent(Event arg0) throws Exception {
						 Map arg=new HashMap();
						 arg.put("type", 21);
						 arg.put("id",jl.getHzId());
						 CkshyjWindow cw=(CkshyjWindow)Executions.createComponents("/admin/personal/data/teacherinfo/ckpsyj.zul", null, arg);
						 cw.initWindow();
						 cw.doHighlighted();
						}
						
					});
				}
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c31);
				arg0.appendChild(c4);
				arg0.appendChild(c5);
				arg0.appendChild(c6);

			}
		});
		listbox22.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhJlhz jl = (GhJlhz) arg1;
				// ���
				Listcell c1 = new Listcell(arg0.getIndex() + 1 + "");
				// ���ʽ���������Ŀ����
				Listcell c2 = new Listcell();
				InnerButton ib = new InnerButton();
				ib.setLabel(jl.getHzMc());
				ib.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						final YqCdgjhzWindow cgWin = (YqCdgjhzWindow) Executions
								.createComponents(
										"/admin/personal/data/teacherinfo/yqcdgjhz.zul",
										null, null);
						cgWin.setJl(jl);
						cgWin.doHighlighted();
						cgWin.initWindow();
						cgWin.addEventListener(Events.ON_CHANGE,
								new EventListener() {
									public void onEvent(Event arg0)
											throws Exception {
										initXsjl();
										cgWin.detach();
									}
								});
					}
				});
				c2.appendChild(ib);
				// ��Ŀ��ֹʱ��
				Listcell c3 = new Listcell(jl.getHzKssj());
				Listcell c31 = new Listcell(jl.getHzJssj());
				// ��������
				Listcell c4 = new Listcell();
				if (jl.getHzDx() != null) {
					c4.setLabel(jl.getHzDx());
				}

				// ����
				Listcell c5 = new Listcell();

				InnerButton gn = new InnerButton();
				gn.setLabel("ɾ��");
				gn.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						if (Messagebox.show("ȷ��ɾ����?", "��ʾ", Messagebox.OK
								| Messagebox.CANCEL, Messagebox.QUESTION) == 1) {
							xmService.delete(jl);
							initXsjl();
						}
					}
				});

				c5.appendChild(gn);
				Listcell c6=new Listcell();
				InnerButton IB = new InnerButton();
				if(jl.getAuditState()==null){
					c6.setLabel("δ���");
				}else if(jl.getAuditState()!=null&&jl.getAuditState().shortValue()==GhJlhz.AUDIT_NO){
					ib.setLabel("δͨ��");
					c6.appendChild(ib);
					ib.addEventListener(Events.ON_CLICK, new EventListener(){

						public void onEvent(Event arg0) throws Exception {
						 Map arg=new HashMap();
						 arg.put("type", 21);
						 arg.put("id",jl.getHzId());
						 CkshyjWindow cw=(CkshyjWindow)Executions.createComponents("/admin/personal/data/teacherinfo/ckpsyj.zul", null, arg);
						 cw.initWindow();
						 cw.doHighlighted();
						}
						
					});
				}else if(jl.getAuditState()!=null&&jl.getAuditState().shortValue()==GhJlhz.AUDIT_YES){
					ib.setLabel("ͨ��");
					c6.appendChild(ib);
					ib.addEventListener(Events.ON_CLICK, new EventListener(){

						public void onEvent(Event arg0) throws Exception {
						 Map arg=new HashMap();
						 arg.put("type", 21);
						 arg.put("id",jl.getHzId());
						 CkshyjWindow cw=(CkshyjWindow)Executions.createComponents("/admin/personal/data/teacherinfo/ckpsyj.zul", null, arg);
						 cw.initWindow();
						 cw.doHighlighted();
						}
						
					});
				}
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c31);
				arg0.appendChild(c4);
				arg0.appendChild(c5);
				arg0.appendChild(c6);
			}
		});
	}

	@Override
	public void initWindow() {
	}

	public void initXsjl() {

		// ���ʹ���ѧ�����飨�Ѳμӣ�

		List list17 = xshyService.findByKuid(user.getKuId(), GhXshy.IFCJ_YES);
		listbox17.setModel(new ListModelList(list17));
		// ���ʹ���ѧ�����飨Ԥ�ڲμӣ�
		List list18 = xshyService.findByKuid(user.getKuId(), GhXshy.IFCJ_NO);
		listbox18.setModel(new ListModelList(list18));
		// ���⽲ѧ�ͱ���������ѽ��У�
		List list19 = jxbgService.findByKuidAndCj(user.getKuId(),
				GhJxbg.IFCJ_YES);
		listbox19.setModel(new ListModelList(list19));
		// ���⽲ѧ�ͱ��������Ԥ�ڽ��У�
		List list20 = jxbgService.findByKuidAndCj(user.getKuId(),
				GhJxbg.IFCJ_NO);
		listbox20.setModel(new ListModelList(list20));
		// �е����ʽ���������Ŀ���ѿ�չ��
		List list21 = jlhzService.findByKuid(user.getKuId(), GhJlhz.IFCJ_YES);
		listbox21.setModel(new ListModelList(list21));
		// �е����ʽ���������Ŀ��Ԥ�ڿ�չ��
		List list22 = jlhzService.findByKuid(user.getKuId(), GhJlhz.IFCJ_NO);
		listbox22.setModel(new ListModelList(list22));
	}

	// //////////////////////////////////////////////////////////////////////////
	public void onClick$button17() {
		final GjxshyWindow cgWin = (GjxshyWindow) Executions.createComponents(
				"/admin/personal/data/teacherinfo/gjxshy.zul", null, null);
		cgWin.setKuid(user.getKuId());
		cgWin.doHighlighted();
		cgWin.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				initXsjl();
				cgWin.detach();
			}
		});
		cgWin.initWindow();
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
				initXsjl();
				cgWin.detach();
			}
		});
		cgWin.initWindow();

	}

	public void onClick$button19() {

		final GwjxbgWindow cgWin = (GwjxbgWindow) Executions.createComponents(
				"/admin/personal/data/teacherinfo/gwjxbg.zul", null, null);
		cgWin.setKuid(user.getKuId());
		cgWin.doHighlighted();
		
		cgWin.addEventListener(Events.ON_CHANGE, new EventListener() {

			public void onEvent(Event arg0) throws Exception {
				initXsjl();
				cgWin.detach();
			}
		});
		cgWin.initWindow();
	}

	public void onClick$button20() {

		final YqGwjxbgWindow cgWin = (YqGwjxbgWindow) Executions
				.createComponents(
						"/admin/personal/data/teacherinfo/yqgwjxbg.zul", null,
						null);
		cgWin.setKuid(user.getKuId());
		cgWin.doHighlighted();
		
		cgWin.addEventListener(Events.ON_CHANGE, new EventListener() {

			public void onEvent(Event arg0) throws Exception {
				initXsjl();
				cgWin.detach();
			}
		});
		cgWin.initWindow();
	}

	public void onClick$button21() {

		final CdgjhzWindow cgWin = (CdgjhzWindow) Executions.createComponents(
				"/admin/personal/data/teacherinfo/cdgjhz.zul", null, null);
		cgWin.setKuid(user.getKuId());
		cgWin.doHighlighted();
		
		cgWin.addEventListener(Events.ON_CHANGE, new EventListener() {

			public void onEvent(Event arg0) throws Exception {
				initXsjl();
				cgWin.detach();
			}
		});
		cgWin.initWindow();

	}

	public void onClick$button22() {

		final YqCdgjhzWindow cgWin = (YqCdgjhzWindow) Executions
				.createComponents(
						"/admin/personal/data/teacherinfo/yqcdgjhz.zul", null,
						null);
		cgWin.setKuid(user.getKuId());
		cgWin.doHighlighted();
		
		cgWin.addEventListener(Events.ON_CHANGE, new EventListener() {

			public void onEvent(Event arg0) throws Exception {
				initXsjl();
				cgWin.detach();
			}
		});
		cgWin.initWindow();

	}

	public void onClick$button17out() throws IOException, WriteException {
		// ���ʹ���ѧ�����飨�Ѳμӣ�

		List list17 = xshyService.findByKuid(user.getKuId(), GhXshy.IFCJ_YES);
		if (list17 == null || list17.size() == 0) {
			try {
				Messagebox.show("�����ݣ���������", "����", Messagebox.OK,
						Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		} else {
			String fileName = "���ʹ���ѧ�����飨�Ѳμӣ�.xls";
			String Title = "���ʹ���ѧ�����飨�Ѳμӣ� ";
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
			String c[][] = new String[list17.size()][titlelist.size()];
			// ��SQL�ж�����
			for (int j = 0; j < list17.size(); j++) {
				GhXshy xs = (GhXshy) list17.get(j);
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
			ee.exportExcel(fileName, Title, titlelist, list17.size(), c);
		}
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

	public void onClick$button19out() throws IOException, WriteException {
		// ���⽲ѧ�ͱ���������ѽ��У�
		List list19 = jxbgService.findByKuidAndCj(user.getKuId(),
				GhJxbg.IFCJ_YES);
		if (list19 == null || list19.size() == 0) {
			try {
				Messagebox.show("�����ݣ���������", "����", Messagebox.OK,
						Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		} else {
			String fileName = "���⽲ѧ�ͱ���������ѽ��У�.xls";
			String Title = "���⽲ѧ�ͱ���������ѽ��У� ";
			WritableWorkbook workbook;
			List titlelist = new ArrayList();
			titlelist.add("���");
			titlelist.add("��ѧ�򱨸�������");
			titlelist.add("�����ѧ���ƻ���ʻ�������");
			titlelist.add("��ѧ�򱨸�����");
			titlelist.add("��������");
			titlelist.add("����ٰ�ʱ��");
			titlelist.add("����ٰ�ص�");
			titlelist.add("��ע");
			String c[][] = new String[list19.size()][titlelist.size()];
			// ��SQL�ж�����
			for (int j = 0; j < list19.size(); j++) {
				GhJxbg bg = (GhJxbg) list19.get(j);
				c[j][0] = j + 1 + "";
				c[j][1] = bg.getJxJxmc();
				c[j][2] = bg.getJxHymc();
				c[j][3] = bg.getJxBgmc();
				c[j][4] = bg.getJxSubject();
				c[j][5] = bg.getJxSj();
				c[j][6] = bg.getJxPlace();
				c[j][7] = bg.getJxRemarks();

			}
			ExportExcel ee = new ExportExcel();
			ee.exportExcel(fileName, Title, titlelist, list19.size(), c);
		}
	}

	public void onClick$button20out() throws IOException, WriteException {
		// ���⽲ѧ�ͱ��������Ԥ�ڽ��У�
		List list20 = jxbgService.findByKuidAndCj(user.getKuId(),
				GhJxbg.IFCJ_NO);
		if (list20 == null || list20.size() == 0) {
			try {
				Messagebox.show("�����ݣ���������", "����", Messagebox.OK,
						Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		} else {
			String fileName = "���⽲ѧ�ͱ��������Ԥ�ڽ��У�.xls";
			String Title = "���⽲ѧ�ͱ��������Ԥ�ڽ��У� ";
			WritableWorkbook workbook;
			List titlelist = new ArrayList();
			titlelist.add("���");
			titlelist.add("��ѧ�򱨸�������");
			titlelist.add("�����ѧ���ƻ���ʻ�������");
			titlelist.add("��ѧ�򱨸�����");
			titlelist.add("��������");
			titlelist.add("����ٰ�ʱ��");
			titlelist.add("����ٰ�ص�");
			titlelist.add("��ע");
			String c[][] = new String[list20.size()][titlelist.size()];
			// ��SQL�ж�����
			for (int j = 0; j < list20.size(); j++) {
				GhJxbg bg = (GhJxbg) list20.get(j);
				c[j][0] = j + 1 + "";
				c[j][1] = bg.getJxJxmc();
				c[j][2] = bg.getJxHymc();
				c[j][3] = bg.getJxBgmc();
				c[j][4] = bg.getJxSubject();
				c[j][5] = bg.getJxSj();
				c[j][6] = bg.getJxPlace();
				c[j][7] = bg.getJxRemarks();
			}
			ExportExcel ee = new ExportExcel();
			ee.exportExcel(fileName, Title, titlelist, list20.size(), c);
		}
	}

	public void onClick$button21out() throws IOException, WriteException {
		// �е����ʽ���������Ŀ���ѿ�չ��
		List list21 = jlhzService.findByKuid(user.getKuId(), GhJlhz.IFCJ_YES);
		if (list21 == null || list21.size() == 0) {
			try {
				Messagebox.show("�����ݣ���������", "����", Messagebox.OK,
						Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		} else {
			String fileName = "�е����ʽ���������Ŀ���ѿ�չ����.xls";
			String Title = "�е����ʽ���������Ŀ���ѿ�չ�� ";
			WritableWorkbook workbook;
			List titlelist = new ArrayList();
			titlelist.add("���");
			titlelist.add("���ʽ���������Ŀ����");
			titlelist.add("��Ŀ��ʼʱ��");
			titlelist.add("��Ŀ����ʱ��");
			titlelist.add("��������");
			titlelist.add("��ע");
			String c[][] = new String[list21.size()][titlelist.size()];
			// ��SQL�ж�����
			for (int j = 0; j < list21.size(); j++) {
				GhJlhz jl = (GhJlhz) list21.get(j);
				c[j][0] = j + 1 + "";
				c[j][1] = jl.getHzMc();
				c[j][2] = jl.getHzKssj();
				c[j][3] = jl.getHzJssj();
				c[j][4] = jl.getHzDx();
				c[j][5] = jl.getHzRemark();

			}
			ExportExcel ee = new ExportExcel();
			ee.exportExcel(fileName, Title, titlelist, list21.size(), c);
		}
	}

	public void onClick$button22out() throws IOException, WriteException {
		// �е����ʽ���������Ŀ��Ԥ�ڿ�չ��
		List list22 = jlhzService.findByKuid(user.getKuId(), GhJlhz.IFCJ_NO);
		if (list22 == null || list22.size() == 0) {
			try {
				Messagebox.show("�����ݣ���������", "����", Messagebox.OK,
						Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		} else {
			String fileName = "�е����ʽ���������Ŀ��Ԥ�ڿ�չ��.xls";
			String Title = "�е����ʽ���������Ŀ��Ԥ�ڿ�չ�� ";
			WritableWorkbook workbook;
			List titlelist = new ArrayList();
			titlelist.add("���");
			titlelist.add("���ʽ���������Ŀ����");
			titlelist.add("��Ŀ��ʼʱ��");
			titlelist.add("��Ŀ����ʱ��");
			titlelist.add("��������");
			titlelist.add("��ע");
			String c[][] = new String[list22.size()][titlelist.size()];
			// ��SQL�ж�����
			for (int j = 0; j < list22.size(); j++) {
				GhJlhz jl = (GhJlhz) list22.get(j);
				c[j][0] = j + 1 + "";
				c[j][1] = jl.getHzMc();
				c[j][2] = jl.getHzKssj();
				c[j][3] = jl.getHzJssj();
				c[j][4] = jl.getHzDx();
				c[j][5] = jl.getHzRemark();

			}
			ExportExcel ee = new ExportExcel();
			ee.exportExcel(fileName, Title, titlelist, list22.size(), c);
		}
	}
}
