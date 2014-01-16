package org.iti.gh.shxkjs.kyqk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.entity.GhCg;
import org.iti.gh.entity.GhFmzl;
import org.iti.gh.entity.GhHylw;
import org.iti.gh.entity.GhJlhz;
import org.iti.gh.entity.GhJxbg;
import org.iti.gh.entity.GhQklw;
import org.iti.gh.entity.GhXm;
import org.iti.gh.entity.GhXshy;
import org.iti.gh.entity.GhZz;
import org.iti.gh.service.CgService;
import org.iti.gh.service.FmzlService;
import org.iti.gh.service.HylwService;
import org.iti.gh.service.JlhzService;
import org.iti.gh.service.JxbgService;
import org.iti.gh.service.LwzlService;
import org.iti.gh.service.PxService;
import org.iti.gh.service.QklwService;
import org.iti.gh.service.RjzzService;
import org.iti.gh.service.SkService;
import org.iti.gh.service.XmService;
import org.iti.gh.service.XshyService;
import org.iti.gh.service.ZzService;
import org.iti.gh.shxkjs.jyqk.JxjlWindow;
import org.iti.gh.shxkjs.jyqk.ShjcqkWindow;
import org.iti.gh.shxkjs.jyqk.ShzdxsWindow;
import org.iti.gh.shxkjs.xsjl.ShjlhzWindow;
import org.iti.gh.shxkjs.xsjl.ShjxbgWindow;
import org.iti.gh.shxkjs.xsjl.ShxsjlWindow;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Toolbarbutton;

import com.uniwin.framework.entity.WkTUser;

public class ShxkjsWindow extends BaseWindow {

	// 科研情况
	Toolbarbutton hylw, qklw, xszz, rjzz, hjxm, fmzl, kjjl, cg;
	XmService xmService;
	CgService cgService;
	// LwzlService lwzlService;
	RjzzService rjzzService;
	FmzlService fmzlService;
	SkService skService;
	PxService pxService;
	XshyService xshyService;
	JxbgService jxbgService;
	JlhzService jlhzService;
	HylwService hylwService;
	QklwService qklwService;
	ZzService zzService;
	ShkyxmWindow sw;
	Row ky, jy, jl;
	WkTUser user;

	@Override
	public void initShow() {
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");

		// 科研会议论文
		hylw.addEventListener(Events.ON_CLICK, new EventListener() {

			public void onEvent(Event arg0) throws Exception {
				if (hylw.getLabel().trim().equals("0项")) {
					Messagebox.show("当前没有需要审核的会议论文！", "提示", Messagebox.OK,
							Messagebox.INFORMATION);
					return;
				} else {
					// Map paras=new HashMap();
					// paras.put("kdid", getXyUserRole().getKdId());
					// paras.put("lx", GhHylw.KYLW);
					// paras.put("type", GhLwzl.LWHY);
					ShhylwWindow sw3 = (ShhylwWindow) Executions
							.createComponents(
									"/admin/xkgl/shxkjs/ckjsqk/shhylw.zul",
									null, null);
					sw3.doModal();
					// sw3.setShadow(false);
					// sw3.initWindow();
					// sw3.addEventListener(Events.ON_CHANGE, new
					// EventListener(){
					//
					// public void onEvent(Event arg0) throws Exception {
					// initWindow();
					// }
					//
					// });
				}
			}

		});

		// 科研期刊论文
		qklw.addEventListener(Events.ON_CLICK, new EventListener() {

			public void onEvent(Event arg0) throws Exception {
				if (hylw.getLabel().trim().equals("0项")) {
					Messagebox.show("当前没有需要审核的期刊论文！", "提示", Messagebox.OK,
							Messagebox.INFORMATION);
					return;
				} else {
					// Map paras=new HashMap();
					// paras.put("kdid", getXyUserRole().getKdId());
					// paras.put("lx", GhHylw.KYLW);
					// paras.put("type", GhLwzl.LWHY);
					ShqklwWindow sw3 = (ShqklwWindow) Executions
							.createComponents(
									"/admin/xkgl/shxkjs/ckjsqk/shqklw.zul",
									null, null);
					sw3.doModal();
					// sw3.setShadow(false);
					// sw3.initWindow();
					// sw3.addEventListener(Events.ON_CHANGE, new
					// EventListener(){
					//
					// public void onEvent(Event arg0) throws Exception {
					// initWindow();
					// }
					//
					// });
				}
			}

		});
		// 著作
		xszz.addEventListener(Events.ON_CLICK, new EventListener() {

			public void onEvent(Event arg0) throws Exception {
				if (xszz.getLabel().trim().equals("0")) {
					Messagebox.show("当前没有需要审核的著作！", "提示", Messagebox.OK,
							Messagebox.INFORMATION);
					return;
				} else {
					ShrjzzWindow sw3 = (ShrjzzWindow) Executions
							.createComponents(
									"/admin/xkgl/shxkjs/ckjsqk/shrjzz.zul",
									null, null);
					sw3.doModal();
				}
			}

		});
		// 科技奖励
		kjjl.addEventListener(Events.ON_CLICK, new EventListener() {

			public void onEvent(Event arg0) throws Exception {
				if (kjjl.getLabel().trim().equals("0")) {
					Messagebox.show("当前没有需要审核的科技奖励！", "提示", Messagebox.OK,
							Messagebox.INFORMATION);
					return;
				} else {
					ShkjjlWindow sw = (ShkjjlWindow) Executions
							.createComponents(
									"/admin/xkgl/shxkjs/ckjsqk/shkjjl.zul",
									null, null);
					sw.doModal();
				}
			}

		});
		// 争取经费
		hjxm.addEventListener(Events.ON_CLICK, new EventListener() {

			public void onEvent(Event arg0) throws Exception {
				if (hjxm.getLabel().trim().equals("0")) {
					Messagebox.show("当前没有需要审核的科技奖励！", "提示", Messagebox.OK,
							Messagebox.INFORMATION);
					return;
				} else {
					ShzqjfWindow sw = (ShzqjfWindow) Executions
							.createComponents(
									"/admin/xkgl/shxkjs/ckjsqk/shzqjf.zul",
									null, null);
					sw.doModal();
				}
			}

		});
		// 学术会议
		fmzl.addEventListener(Events.ON_CLICK, new EventListener() {

			public void onEvent(Event arg0) throws Exception {
				if (fmzl.getLabel().trim().equals("0")) {
					Messagebox.show("当前没有需要审核的学术会议！", "提示", Messagebox.OK,
							Messagebox.INFORMATION);
					return;
				} else {
					ShxsjlWindow sw = (ShxsjlWindow) Executions
							.createComponents(
									"/admin/xkgl/shxkjs/ckjsqk/shjlhy.zul",
									null, null);
					sw.doModal();
				}
			}

		});
		// 发明专利及软件
		rjzz.addEventListener(Events.ON_CLICK, new EventListener() {

			public void onEvent(Event arg0) throws Exception {
				if (rjzz.getLabel().trim().equals("0")) {
					Messagebox.show("当前没有需要审核的专利及软件！", "提示", Messagebox.OK,
							Messagebox.INFORMATION);
					return;
				} else {
					ShfmzlWindow sw3 = (ShfmzlWindow) Executions
							.createComponents(
									"/admin/xkgl/shxkjs/ckjsqk/Shfmzl.zul",
									null, null);
					sw3.doModal();
				}
			}

		});
		// 获奖成果
		cg.addEventListener(Events.ON_CLICK, new EventListener() {

			public void onEvent(Event arg0) throws Exception {
				if (cg.getLabel().trim().equals("0")) {
					Messagebox.show("当前没有需要审核的获奖成果！", "提示", Messagebox.OK,
							Messagebox.INFORMATION);
					return;
				} else {
					ShkycgWindow sw3 = (ShkycgWindow) Executions
							.createComponents(
									"/admin/xkgl/shxkjs/ckjsqk/shhjcg.zul",
									null, null);
					sw3.doModal();
				}
			}

		});
	}

	@Override
	public void initWindow() {
	}

}
