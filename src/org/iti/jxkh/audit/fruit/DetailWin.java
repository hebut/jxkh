package org.iti.jxkh.audit.fruit;

import java.util.ArrayList;
import java.util.List;

import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.entity.Jxkh_Fruit;
import org.iti.jxkh.entity.Jxkh_FruitDept;
import org.iti.jxkh.entity.Jxkh_FruitMember;
import org.iti.jxkh.service.JxkhAwardService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.iti.common.util.ConvertUtil;

public class DetailWin extends Window implements AfterCompose {

	/**
	 * @author ZhangyuGuang
	 */
	private static final long serialVersionUID = -752708094650509893L;
	private Textbox name, fruitMember, fruitDept, coCompany, 
			appraiseCode, acceptCode, appraiseType,// record,
			organAppraiseCompany, holdAppraiseCompany, acceptDept;
	private Listbox fruitRank, acceptRank, rate;
	private Radio cooperationTrue, cooperationFalse, firstSignTrue,
			firstSignFalse;
	private Row outDeptRow;
	private YearListbox jiFenTime;
	private Datebox acceptDate, appraiseDate;
	private Label submitName;
	private Button print;
	// private Hbox recordhbox;
	private Jxkh_Fruit fruit;
	private List<Jxkh_FruitMember> fruitMemberList = new ArrayList<Jxkh_FruitMember>();
	private List<Jxkh_FruitDept> fruitDeptList = new ArrayList<Jxkh_FruitDept>();
	private JxkhAwardService jxkhAwardService;

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		fruitRank.setItemRenderer(new fruitRankRenderer());
		acceptRank.setItemRenderer(new receiveRankRenderer());
		rate.setItemRenderer(new indentRankRenderer());
		jiFenTime.initYearListbox("");
	}

	public void initWindow() {
		String memberName = "";
		String deptName = "";
		print.setHref("/print.action?n=fruit&id=" + fruit.getId());
		name.setValue(fruit.getName());
		if (fruit.getFirstSign() == 1) {
			firstSignTrue.setChecked(true);
			firstSignFalse.setChecked(false);
		} else {
			firstSignTrue.setChecked(false);
			firstSignFalse.setChecked(true);
		}
		if (fruit.getCoState() == 1) {
			cooperationTrue.setChecked(true);
			cooperationFalse.setChecked(false);
			outDeptRow.setVisible(true);
			coCompany.setValue(fruit.getCoCompany());
		} else {
			cooperationTrue.setChecked(false);
			cooperationFalse.setChecked(true);
		}
		if (fruit.getAcceptDate() != null)
			acceptDate.setValue(ConvertUtil.convertDate(fruit.getAcceptDate()));
		if (fruit.getAppraiseDate() != null)
			appraiseDate.setValue(ConvertUtil.convertDate(fruit
					.getAppraiseDate()));
		submitName.setValue(fruit.getSubmitName());
		// if(fruit.getState()==5){
		// record.setVisible(true);
		// recordlabel.setVisible(true);
		// recordhbox.setVisible(true);
		// record.setValue(fruit.getRecordCode());
		// }
		jiFenTime.initYearListbox(fruit.getjxYear());
		appraiseCode.setValue(fruit.getAppraiseCode());
		appraiseType.setValue(fruit.getAppraiseType());
		organAppraiseCompany.setValue(fruit.getOrganAppraiseCompany());
		holdAppraiseCompany.setValue(fruit.getHoldAppraiseCompany());
		acceptCode.setValue(fruit.getAcceptCode());
		acceptDept.setValue(fruit.getAcceptDetp());

		fruitMemberList = fruit.getFruitMember();
		for (int i = 0; i < fruitMemberList.size(); i++) {
			Jxkh_FruitMember mem = (Jxkh_FruitMember) fruitMemberList.get(i);
			memberName += mem.getName() + "、";
		}
		fruitMember.setValue(memberName.substring(0, memberName.length() - 1));

		fruitDeptList = fruit.getFruitDept();
		for (int i = 0; i < fruitDeptList.size(); i++) {
			Jxkh_FruitDept dept = (Jxkh_FruitDept) fruitDeptList.get(i);
			deptName += dept.getName() + "、";
		}
		fruitDept.setValue(deptName.substring(0, deptName.length() - 1));

		initListbox();
	}

	private void initListbox() {
		List<Jxkh_BusinessIndicator> rankList = new ArrayList<Jxkh_BusinessIndicator>();
		rankList.add(new Jxkh_BusinessIndicator());
		rankList.addAll(jxkhAwardService.findAllBusinessIndicator());

		fruitRank.setModel(new ListModelList(rankList));
		fruitRank.setSelectedIndex(0);

		acceptRank.setModel(new ListModelList(rankList));
		acceptRank.setSelectedIndex(0);

		rate.setModel(new ListModelList(rankList));
		rate.setSelectedIndex(0);

	}

	// 成果水平渲染器
	public class fruitRankRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			Jxkh_BusinessIndicator fruitRank = (Jxkh_BusinessIndicator) data;
			item.setValue(fruitRank);
			Listcell c0 = new Listcell();
			if (fruitRank.getKbName() == null) {
				c0.setLabel("--请选择--");
			} else {
				c0.setLabel(fruitRank.getKbName());
			}
			item.appendChild(c0);
			if (item.getIndex() == 0)
				item.setSelected(true);
			if (fruit != null && fruitRank.equals(fruit.getAppraiseRank())) {
				item.setSelected(true);
			}
		}
	}

	public class receiveRankRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			Jxkh_BusinessIndicator acceptRank = (Jxkh_BusinessIndicator) data;
			item.setValue(acceptRank);
			Listcell c0 = new Listcell();
			if (acceptRank.getKbName() == null) {
				c0.setLabel("--请选择--");
			} else {
				c0.setLabel(acceptRank.getKbName());
			}
			item.appendChild(c0);
			if (item.getIndex() == 0)
				item.setSelected(true);
			if (fruit != null && acceptRank.equals(fruit.getAcceptRank())) {
				item.setSelected(true);
			}
		}
	}

	// 鉴定级别
	public class indentRankRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			Jxkh_BusinessIndicator indentRank = (Jxkh_BusinessIndicator) data;
			item.setValue(indentRank);
			Listcell c0 = new Listcell();
			if (indentRank.getKbName() == null) {
				c0.setLabel("--请选择--");
			} else {
				c0.setLabel(indentRank.getKbName());
			}
			item.appendChild(c0);
			if (item.getIndex() == 0)
				item.setSelected(true);
			if (fruit != null && indentRank.equals(fruit.getIndentRank())) {
				item.setSelected(true);
			}
		}
	}

	public Jxkh_Fruit getFruit() {
		return fruit;
	}

	public void setFruit(Jxkh_Fruit fruit) {
		this.fruit = fruit;
	}

	/**
	 * <li>功能描述：关闭当前窗口。
	 */
	public void onClick$close() {
		this.onClose();
	}
}
