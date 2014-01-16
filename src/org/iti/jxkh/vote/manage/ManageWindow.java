package org.iti.jxkh.vote.manage;

import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxkh.common.ShowVoterWindow;
import org.iti.jxkh.common.ShowWindow;
import org.iti.jxkh.entity.JXKH_VoteConfig;
import org.iti.jxkh.service.VoteConfigService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Toolbarbutton;

public class ManageWindow extends BaseWindow {

	private static final long serialVersionUID = -2032464190190651247L;
	private Listbox votelist;
	private YearListbox yearlist;
	private VoteConfigService voteConfigService;

	public void initShow() {
		/*-----------------changed--------------*/
		yearlist.initYearListbox("");
		/*-----------------changed--------------*/
		yearlist.getItemAtIndex(0).setSelected(true);
		votelist.setItemRenderer(new ListitemRenderer() {

			public void render(Listitem arg0, Object arg1) throws Exception {
				final JXKH_VoteConfig vc = (JXKH_VoteConfig) arg1;
				Listcell c0 = new Listcell(arg0.getIndex() + 1 + "");
				Listcell c1 = new Listcell(vc.getVcName());
				Listcell c2 = new Listcell(vc.getVcStart().substring(0, 10));
				Listcell c3 = new Listcell(vc.getVcEnd().substring(0, 10));
				Listcell c4 = new Listcell();
				Toolbarbutton object = new Toolbarbutton("查看");
				final short btype = 0;// 表示投票对象
				object.setStyle("color:blue");
				object.addEventListener(Events.ON_CLICK, new EventListener() {

					public void onEvent(Event arg0) throws Exception {
						ShowWindow win = (ShowWindow) Executions.createComponents("/admin/jxkh/common/show.zul", null, null);
						win.initWindow(vc);
						win.doModal();
					}
				});
				c4.appendChild(object);
				Listcell c5 = new Listcell();
				Toolbarbutton range = new Toolbarbutton("查看");
				@SuppressWarnings("unused")
				final short ttype = 1;// 表示投票者
				range.setStyle("color:blue");
				range.addEventListener(Events.ON_CLICK, new EventListener() {

					public void onEvent(Event arg0) throws Exception {
						ShowVoterWindow win = (ShowVoterWindow) Executions.createComponents("/admin/jxkh/common/showVoter.zul", null, null);
						win.initWindow(vc);
						win.doModal();
					}
				});
				c5.appendChild(range);
				Listcell c6 = new Listcell();
				Toolbarbutton edit = new Toolbarbutton("修改");
				edit.setStyle("color:blue");
				edit.addEventListener(Events.ON_CLICK, new EventListener() {

					public void onEvent(Event arg0) throws Exception {
						final AddWindow aw = (AddWindow) Executions.createComponents("/admin/jxkh/vote/manage/add.zul", null, null);
						aw.initWindow(vc);
						aw.addEventListener(Events.ON_CHANGE, new EventListener() {

							public void onEvent(Event arg0) throws Exception {
								aw.detach();
								initWindow();
							}
						});
						aw.doModal();
					}
				});
				Label space = new Label("/");
				Toolbarbutton delete = new Toolbarbutton("删除");
				delete.setStyle("color:blue");
				delete.addEventListener(Events.ON_CLICK, new EventListener() {

					public void onEvent(Event arg0) throws Exception {
						voteConfigService.delete(vc);
						initWindow();
					}
				});
				c6.appendChild(edit);
				c6.appendChild(space);
				c6.appendChild(delete);
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
				arg0.appendChild(c5);
				arg0.appendChild(c6);
			}
		});
//		initWindow();
	}

	public void initWindow() {
		/*JxYear jy = (JxYear)yearlist.getSelectedItem().getValue();
		String year = jy.getYears();*/
		/*-----------------changed----------------*/
		String year = yearlist.getSelectYear();
		/*-----------------changed----------------*/
		votelist.setModel(new ListModelList(voteConfigService.findConfigByYear(year)));
	}

	public void onClick$add() throws SuspendNotAllowedException, InterruptedException {
		final AddWindow win = (AddWindow) Executions.createComponents("/admin/jxkh/vote/manage/add.zul", null, null);
		win.initWindow(null);
		win.addEventListener(Events.ON_CHANGE, new EventListener() {

			public void onEvent(Event arg0) throws Exception {
				win.detach();
				initWindow();
			}
		});
		win.doModal();
	}
	public void onSelect$yearlist() {
		initWindow();
	}
}
