package org.iti.jxkh.busiAudit.fruit;

import java.util.ArrayList;
import java.util.List;

import org.iti.jxkh.entity.JXKH_QKLW;
import org.iti.jxkh.entity.Jxkh_Fruit;
import org.iti.jxkh.service.JxkhFruitService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Window;

public class BatchAuditWin extends Window implements AfterCompose {

	/**
	 * @author ZhangyuGuang
	 */
	private static final long serialVersionUID = -1217283978824057625L;
	private List<Jxkh_Fruit> fruitList = new ArrayList<Jxkh_Fruit>();
	private Radio pass,temp;
	private JxkhFruitService jxkhFruitService;

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		pass.setChecked(true);
	}

	public void onClick$submit() {
		Jxkh_Fruit fruit = new Jxkh_Fruit();
		Short state = null;		
		if (pass.isChecked()) {
			state = JXKH_QKLW.BUSINESS_PASS;
		}else if(temp.isChecked()) {
			state = JXKH_QKLW.BUSINESS_TEMP_PASS;
		}else {
			state = JXKH_QKLW.BUSINESS_NOT_PASS;
		}
		for (int i = 0; i < fruitList.size(); i++) {
			fruit = fruitList.get(i);
			fruit.setState(state);
			jxkhFruitService.saveOrUpdate(fruit);
		}
		try {
			Messagebox.show("批量审核成功！", "提示", Messagebox.OK,
					Messagebox.INFORMATION);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				Messagebox.show("批量审核失败，请重试！", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		this.onClose();
	}

	public List<Jxkh_Fruit> getFruitList() {
		return fruitList;
	}

	public void setFruitList(List<Jxkh_Fruit> fruitList) {
		this.fruitList = fruitList;
	}

	/**
	 * <li>功能描述：关闭当前窗口。
	 */
	public void onClick$close() {
		this.onClose();
	}
}
