package org.iti.jxkh.audit.fruit;

import java.util.ArrayList;
import java.util.List;

import org.iti.jxkh.entity.JXKH_MEETING;
import org.iti.jxkh.entity.Jxkh_Fruit;
import org.iti.jxkh.entity.Jxkh_FruitDept;
import org.iti.jxkh.service.JxkhFruitService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTUser;

public class BatchAuditWin extends Window implements AfterCompose {

	/**
	 * @author ZhangyuGuang C
	 */
	private static final long serialVersionUID = 6301144035734171088L;
	private List<Jxkh_Fruit> fruitList = new ArrayList<Jxkh_Fruit>();
	private List<Jxkh_FruitDept> meetDeptList = new ArrayList<Jxkh_FruitDept>();
	private Radio pass, back;
	private WkTUser user;
	private JxkhFruitService jxkhFruitService;

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");// ��ȡ��ǰ��¼�û�
		pass.setChecked(true);
	}

	public void onClick$submit() {
		Jxkh_Fruit meeting = new Jxkh_Fruit();
		for (int i = 0; i < fruitList.size(); i++) {
			meeting = fruitList.get(i);
			meetDeptList = jxkhFruitService.findFruitDeptByFruitId(meeting);
			int index = 0;
			for (int t = 1; t <= meetDeptList.size(); t++) {
				Jxkh_FruitDept dept = (Jxkh_FruitDept) meetDeptList.get(t - 1);
				if (user.getDept().getKdName().equals(dept.getName())) {
					index = dept.getRank();
				}
			}
			String s = meeting.getTempState();
			char[] myStr = s.toCharArray();
			if (pass.isChecked()) {
				myStr[index - 1] = '1';
				String s0 = new String(myStr);
				meeting.setTempState(s0);
				meeting.setState(JXKH_MEETING.First_Dept_Pass);
				jxkhFruitService.saveOrUpdate(meeting);

				int k = 1;
				String str = meeting.getTempState();
				for (int j = 0; j < meetDeptList.size(); j++) {
					if (str.charAt(j) != '1')
						k = 0;
				}
				if (k == 1)
					meeting.setState(JXKH_MEETING.DEPT_PASS);
			} else if (back.isChecked()) {
				myStr[index - 1] = '2';
				String s0 = new String(myStr);
				meeting.setTempState(s0);
				meeting.setState(JXKH_MEETING.DEPT_NOT_PASS);
			}
			jxkhFruitService.saveOrUpdate(meeting);
		}

		try {
			Messagebox.show("������˳ɹ���", "��ʾ", Messagebox.OK,
					Messagebox.INFORMATION);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				Messagebox.show("�������ʧ�ܣ������ԣ�", "��ʾ", Messagebox.OK,
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
	 * <li>�����������رյ�ǰ���ڡ�
	 */
	public void onClick$close() {
		this.onClose();
	}
}
