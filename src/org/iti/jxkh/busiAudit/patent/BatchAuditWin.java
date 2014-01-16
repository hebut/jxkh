package org.iti.jxkh.busiAudit.patent;

import java.util.ArrayList;
import java.util.List;

import org.iti.jxkh.entity.JXKH_QKLW;
import org.iti.jxkh.entity.Jxkh_Patent;
import org.iti.jxkh.service.JxkhProjectService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Window;

public class BatchAuditWin extends Window implements AfterCompose {

	/**
	 * @author ZhangyuGuang
	 */
	private static final long serialVersionUID = 5151969361938519733L;
	private List<Jxkh_Patent> awardList = new ArrayList<Jxkh_Patent>();
	private Radio pass,temp;
	private JxkhProjectService jxkhProjectService;

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		pass.setChecked(true);

	}

	public void onClick$submit() {
		Jxkh_Patent award = new Jxkh_Patent();
		Short state = null;		
		if (pass.isChecked()) {
			state = JXKH_QKLW.BUSINESS_PASS;
		}else if(temp.isChecked()) {
			state = JXKH_QKLW.BUSINESS_TEMP_PASS;
		}else {
			state = JXKH_QKLW.BUSINESS_NOT_PASS;
		}
		for (int i = 0; i < awardList.size(); i++) {
			award = awardList.get(i);
			award.setState(state);
			jxkhProjectService.saveOrUpdate(award);
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

	public List<Jxkh_Patent> getAwardList() {
		return awardList;
	}

	public void setAwardList(List<Jxkh_Patent> awardList) {
		this.awardList = awardList;
	}

	/**
	 * <li>功能描述：关闭当前窗口。
	 */
	public void onClick$close() {
		this.onClose();
	}
}
