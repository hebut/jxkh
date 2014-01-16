package org.iti.jxkh.business.award;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.iti.jxkh.entity.Jxkh_AwardMember;
import org.iti.jxkh.service.JxkhAwardService;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;

public class ChooseMemberWin extends Window implements AfterCompose {

	/**
	 * @author ZhangyuGuang
	 */
	private static final long serialVersionUID = -5847256695635389492L;
	private Hbox out, in;
	private Listitem cout;
	private Textbox name, outname;
	private Listbox range, listbox1, listbox2, dept;
	private Toolbarbutton outadd;
	private JxkhAwardService jxkhAwardService;
	private List<WkTUser> memberList = new ArrayList<WkTUser>();
	private List<WkTUser> oldMemberList = new ArrayList<WkTUser>();

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		this.addEventListener(Events.ON_CLOSE, new EventListener() {
			
			@Override
			public void onEvent(Event arg0) throws Exception {
				memberList.clear();
				memberList.addAll(oldMemberList);
			}
		});
		range.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				if (cout.isSelected()) {
					out.setVisible(true);
					outadd.setVisible(true);
					in.setVisible(false);

				} else {
					out.setVisible(false);
					outadd.setVisible(false);
					in.setVisible(true);
				}
			}
		});
		listbox1.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				WkTUser user = (WkTUser) arg1;
				Listcell c0 = new Listcell();
				Listcell c1 = new Listcell(arg0.getIndex() + 1 + "");
				Listcell c2 = new Listcell(user.getKuName());
				Listcell c3 = new Listcell(user.getStaffId());
				Listcell c4 = new Listcell(user.getDept1() == null ? "" : user
						.getDept1().getKdName());
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
				arg0.setValue(arg1);
			}
		});
		listbox2.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				WkTUser user = (WkTUser) arg1;
				Listcell c0 = new Listcell();
				Listcell c1 = new Listcell(arg0.getIndex() + 1 + "");
				Listcell c2 = new Listcell(user.getKuName());
				Listcell c3 = new Listcell();
				if (user.getType() == 1) {
					c3 = new Listcell("院外");
				} else {
					c3 = new Listcell(user.getStaffId());
				}
				Listcell c4 = new Listcell();
				if (user.getType() == 1) {
					c4 = new Listcell("院外");
				} else {
					c4 = new Listcell(user.getDept().getKdName());
				}
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
				arg0.setValue(arg1);
				arg0.setDraggable("true");
				arg0.setDroppable("true");
				arg0.addEventListener(Events.ON_DROP, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						DropEvent event = (DropEvent) arg0;
						Component self = (Component) arg0.getTarget();
						Listitem dragged = (Listitem) event.getDragged();
						if (self instanceof Listitem) {
							self.getParent().insertBefore(dragged,
									self.getNextSibling());
							initListbox2();
//							listbox2.onInitRender();
						} else {
							self.appendChild(dragged);
						}
					}
				});
			}
		});
		List<WkTUser> ulist = jxkhAwardService.findUser();
		for (WkTUser user : ulist) {
			user.setType((short) 1);
		}
		listbox1.setModel(new ListModelList(ulist));
		dept.setItemRenderer(new ListitemRenderer() {
			@Override
			public void render(Listitem arg0, Object arg1) throws Exception {
				WkTDept dept = (WkTDept) arg1;
				arg0.setValue(dept);
				Listcell c0 = new Listcell();
				if (dept.getKdName() == null) {
					c0 = new Listcell("--请选择--");
				} else {
					c0 = new Listcell(dept.getKdName());
				}
				arg0.appendChild(c0);
			}

		});
		List<WkTDept> deptlist = new ArrayList<WkTDept>();
		WkTDept dept1 = new WkTDept();
		deptlist.add(dept1);
		List<WkTDept> dlist = jxkhAwardService.findDept();
		deptlist.addAll(dlist);
		dept.setModel(new ListModelList(deptlist));
		dept.setSelectedIndex(0);
	}

	public void initWindow() {
		if (memberList.size() != 0){
			listbox2.setModel(new ListModelList(memberList));
			oldMemberList.addAll( memberList);
		}
		initFirstListbox();
	}
	public void initListbox2(){
		Iterator <Listitem> items2 = listbox2.getItems().iterator();
		memberList.clear();
		while(items2.hasNext()){
			memberList.add((WkTUser) items2.next().getValue());
		}
		listbox2.setModel(new ListModelList(memberList));
	}
	public void onClick$add() {
		/*
		 * Object[] items = this.listbox1.getSelectedItems().toArray(); for (int
		 * i = 0; i < items.length; i++) { Listitem item = (Listitem) items[i];
		 * if (memberList.contains(item.getValue())) { continue; } if
		 * (listbox2.getItemCount() == 0) { listbox2.appendChild(item); } else {
		 * Listitem topItem = (Listitem) listbox2.getItems().get(0);
		 * listbox2.insertBefore(item, topItem); } } initListbox1();
		 * initListbox2(); rebuild();
		 */
		initListbox(this.listbox1, this.dlist, this.listbox2, this.memberList);
	}

	public void onClick$delete() {
		initListbox(this.listbox2, this.memberList, this.listbox1, this.dlist);
		/*
		 * for (int i = 0; i < items.length; i++) { Listitem item = (Listitem)
		 * items[i]; if (listbox1.getItemCount() == 0) {
		 * listbox1.appendChild(item); } else { Listitem topItem = (Listitem)
		 * listbox1.getItems().get(0); listbox1.insertBefore(item, topItem); } }
		 */
		/*
		 * initListbox1(); initListbox2();
		 */
		// rebuild();
	}

	private void initListbox(Listbox sourceListbox, List<WkTUser> sourceList,
			Listbox targetListbox, List<WkTUser> targetList) {
		Set<?> items = sourceListbox.getSelectedItems();
		for (Object o : items) {
			Listitem item = (Listitem) o;
			WkTUser u = (WkTUser) item.getValue();
			if (!targetList.contains(u))
				targetList.add(u);
			if (sourceList.contains(u))
				sourceList.remove(u);
		}
		sourceListbox.setModel(new ListModelList(sourceList));
		targetListbox.setModel(new ListModelList(targetList));
		targetListbox.onInitRender();
		sourceListbox.onInitRender();
	}

	public void onClick$outadd() {
		if(outname.getValue()==null||outname.getValue()=="")
		{
			try {
				Messagebox.show("请输入院外成员姓名！", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		rebuild();
		WkTUser out = new WkTUser();
		WkTDept d = new WkTDept();
		d.setKdName("校外");
		out.setDept(d);
		out.setKuId(new Random().nextLong());
		out.setKuLid("校外");
		out.setKuName(outname.getValue());
		out.setType((short) 1);
		// Listitem item=new Listitem();
		// Listcell cell0=new Listcell("");
		// Listcell cell1=new Listcell(item.getIndex()+"");
		// Listcell cell2=new Listcell(out.getKuName());
		// Listcell cell3=new Listcell("院外");
		// Listcell cell4=new Listcell("院外");
		// item.appendChild(cell0);item.appendChild(cell1);item.appendChild(cell2);
		// item.appendChild(cell3);item.appendChild(cell4);
		// item.setValue(out);
		// listbox2.appendChild(item);
		memberList.add(out);
		listbox2.setModel(new ListModelList(memberList));
		listbox2.onInitRender();
	}

	/*
	 * private void initListbox1(){ Iterator <Listitem> items1 =
	 * listbox1.getItems().iterator(); memberList.clear();
	 * while(items1.hasNext()){ memberList.add((WkTUser)
	 * items1.next().getValue()); } listbox1.setModel(new
	 * ListModelList(memberList)); } private void initListbox2(){ Iterator
	 * <Listitem> items2 = listbox2.getItems().iterator(); memberList.clear();
	 * while(items2.hasNext()){ memberList.add((WkTUser)
	 * items2.next().getValue()); } listbox2.setModel(new
	 * ListModelList(memberList)); }
	 */

	public void rebuild() {
		memberList.clear();
		Iterator<?> it = listbox2.getItems().iterator();
		while (it.hasNext()) {
			memberList.add((WkTUser) ((Listitem) it.next()).getValue());
		}
		// onClick$search();
	}

	List<WkTUser> dlist = null;

	public void initFirstListbox() {
		String userIdSearch = "";
		if (memberList.size() != 0) {
			for (int i = 0; i < memberList.size(); i++) {
				WkTUser user = (WkTUser) memberList.get(i);
				if (user.getType() != WkTUser.TYPE_OUT)
					userIdSearch += "'"+user.getKuLid() + "',";
			}
			if (userIdSearch.endsWith(","))
				userIdSearch = userIdSearch.substring(0,
						userIdSearch.length() - 1);
		}
		if(dept.getSelectedIndex() != 0) {
			WkTDept d = (WkTDept) dept.getSelectedItem().getValue();
			dlist = jxkhAwardService.findWkTUserNotInListBox2ByDept(userIdSearch,d.getKdId());
		}else {
			dlist = jxkhAwardService.findWkTUserNotInListBox2(userIdSearch);
		}		
		listbox1.setModel(new ListModelList(dlist));
	}

	public void onClick$submit() {
		rebuild();
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	public List<WkTUser> getMemberList() {
		return memberList;
	}

	public void setMemberList(List<WkTUser> memberList) {
		this.memberList = memberList;
	}

	public void onClick$view() {
		String userIdSearch = "";
		List<WkTUser> memberList = new ArrayList<WkTUser>();
		for (WkTUser user : memberList) {
			user.setKuStyle("1");
		}
		Iterator<?> items2 = listbox2.getItems().iterator();
		while (items2.hasNext()) {
			Listitem item = (Listitem) items2.next();
			memberList.add((WkTUser) item.getValue());
		}
		if (memberList.size() != 0) {
			for (int i = 0; i < memberList.size(); i++) {
				WkTUser user = (WkTUser) memberList.get(i);
				userIdSearch += "'"+user.getKuLid() + "',";
			}
			userIdSearch = userIdSearch.substring(0, userIdSearch.length() - 1);
		}
		WkTDept deptment = (WkTDept) dept.getSelectedItem().getValue();
		List<WkTUser> dlist = jxkhAwardService.findWkTUserByCondition(
				name.getValue(), deptment.getKdId(), userIdSearch);
		listbox1.setModel(new ListModelList(dlist));
	}

	public void onClick$close() {
		memberList.clear();
		memberList.addAll(oldMemberList);
		this.detach();
	}

}
