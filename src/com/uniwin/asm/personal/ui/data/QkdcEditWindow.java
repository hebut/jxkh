package com.uniwin.asm.personal.ui.data;

import java.util.List;

import org.iti.gh.entity.GhQkdc;
import org.iti.gh.service.QkdcService;
import org.iti.xypt.ui.base.FrameworkWindow;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Column;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import com.uniwin.framework.entity.WkTUser;

public class QkdcEditWindow extends FrameworkWindow {
	Column col;
	Label leibie;
	Textbox qk;
	WkTUser user;
	String strCol;
	String strlb;
	int index;
	GhQkdc dc;
	QkdcService qkdcService;

	@Override
	public void initShow() {
	}

	@Override
	public void initWindow() {
		col.setLabel(strCol);
		leibie.setValue(strlb);
		List list = qkdcService.findByKuid(user.getKuId(), strCol);
		if (list.size() > 0) {
			dc = (GhQkdc) list.get(0);
		}
		if (dc != null) {
			switch (index) {
			case 1:
				if (dc.getKyxmGj() != null) {
					qk.setValue(dc.getKyxmGj());
				} else {
					qk.setValue("");
				}
				break;
			case 2:
				if (dc.getKyxmSb() != null) {
					qk.setValue(dc.getKyxmSb());
				} else {
					qk.setValue("");
				}
				break;
			case 3:
				if (dc.getKyxmHx() != null) {
					qk.setValue(dc.getKyxmHx());
				} else {
					qk.setValue("");
				}
				break;
			case 4:
				if (dc.getKyxmQt() != null) {
					qk.setValue(dc.getKyxmQt());
				} else {
					qk.setValue("");
				}
				break;
			case 5:
				if (dc.getKycgLw() != null) {
					qk.setValue(dc.getKycgLw());
				} else {
					qk.setValue("");
				}
				break;
			case 6:
				if (dc.getKycgZl() != null) {
					qk.setValue(dc.getKycgZl());
				} else {
					qk.setValue("");
				}
				break;
			case 7:
				if (dc.getKyjlGj() != null) {
					qk.setValue(dc.getKyjlGj());
				} else {
					qk.setValue("");
				}
				break;
			case 8:
				if (dc.getKyjlSb() != null) {
					qk.setValue(dc.getKyjlSb());
				} else {
					qk.setValue("");
				}
				break;
			case 9:
				if (dc.getKyjlQt() != null) {
					qk.setValue(dc.getKyjlQt());
				} else {
					qk.setValue("");
				}
				break;
			case 10:
				if (dc.getKyhzGj() != null) {
					qk.setValue(dc.getKyhzGj());
				} else {
					qk.setValue("");
				}
				break;
			case 11:
				if (dc.getKyhzGn() != null) {
					qk.setValue(dc.getKyhzGn());
				} else {
					qk.setValue("");
				}
				break;
			case 12:
				if (dc.getKyhzHf() != null) {
					qk.setValue(dc.getKyhzHf());
				} else {
					qk.setValue("");
				}
				break;
			case 13:
				if (dc.getKyhzQt() != null) {
					qk.setValue(dc.getKyhzQt());
				} else {
					qk.setValue("");
				}
				break;
			case 14:
				if (dc.getJxcgGj() != null) {
					qk.setValue(dc.getJxcgGj());
				} else {
					qk.setValue("");
				}
				break;
			case 15:
				if (dc.getJxcgSb() != null) {
					qk.setValue(dc.getJxcgSb());
				} else {
					qk.setValue("");
				}
				break;
			case 16:
				if (dc.getJxcgCb() != null) {
					qk.setValue(dc.getJxcgCb());
				} else {
					qk.setValue("");
				}
				break;
			case 17:
				if (dc.getJxcgQt() != null) {
					qk.setValue(dc.getJxcgQt());
				} else {
					qk.setValue("");
				}
				break;
			}
		} else {
			qk.setValue("");
		}
	}

	public String getStrCol() {
		return strCol;
	}

	public void setStrCol(String strCol) {
		this.strCol = strCol;
	}

	public String getStrlb() {
		return strlb;
	}

	public void setStrlb(String strlb) {
		this.strlb = strlb;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public WkTUser getUser() {
		return user;
	}

	public void setUser(WkTUser user) {
		this.user = user;
	}

	public void onClick$submit() throws InterruptedException {
		if (qk.getValue() != null && !("".equals(qk.getValue().trim()))) {
			if (dc != null) {
				switch (index) {
				case 1:
					dc.setKyxmGj(qk.getValue());
					break;
				case 2:
					dc.setKyxmSb(qk.getValue());
					break;
				case 3:
					dc.setKyxmHx(qk.getValue());
					break;
				case 4:
					dc.setKyxmQt(qk.getValue());
					break;
				case 5:
					dc.setKycgLw(qk.getValue());
					break;
				case 6:
					dc.setKycgZl(qk.getValue());
					break;
				case 7:
					dc.setKyjlGj(qk.getValue());
					break;
				case 8:
					dc.setKyjlSb(qk.getValue());
					break;
				case 9:
					dc.setKyjlQt(qk.getValue());
					break;
				case 10:
					dc.setKyhzGj(qk.getValue());
					break;
				case 11:
					dc.setKyhzGn(qk.getValue());
					break;
				case 12:
					dc.setKyhzHf(qk.getValue());
					break;
				case 13:
					dc.setKyhzQt(qk.getValue());
					break;
				case 14:
					dc.setJxcgGj(qk.getValue());
					break;
				case 15:
					dc.setJxcgSb(qk.getValue());
					break;
				case 16:
					dc.setJxcgCb(qk.getValue());
					break;
				case 17:
					dc.setJxcgQt(qk.getValue());
					break;
				}
				qkdcService.update(dc);
			} else {
				dc = new GhQkdc();
				switch (index) {
				case 1:
					dc.setKyxmGj(qk.getValue());
					break;
				case 2:
					dc.setKyxmSb(qk.getValue());
					break;
				case 3:
					dc.setKyxmHx(qk.getValue());
					break;
				case 4:
					dc.setKyxmQt(qk.getValue());
					break;
				case 5:
					dc.setKycgLw(qk.getValue());
					break;
				case 6:
					dc.setKycgZl(qk.getValue());
					break;
				case 7:
					dc.setKyjlGj(qk.getValue());
					break;
				case 8:
					dc.setKyjlSb(qk.getValue());
					break;
				case 9:
					dc.setKyjlQt(qk.getValue());
					break;
				case 10:
					dc.setKyhzGj(qk.getValue());
					break;
				case 11:
					dc.setKyhzGn(qk.getValue());
					break;
				case 12:
					dc.setKyhzHf(qk.getValue());
					break;
				case 13:
					dc.setKyhzQt(qk.getValue());
					break;
				case 14:
					dc.setJxcgGj(qk.getValue());
					break;
				case 15:
					dc.setJxcgSb(qk.getValue());
					break;
				case 16:
					dc.setJxcgCb(qk.getValue());
					break;
				case 17:
					dc.setJxcgQt(qk.getValue());
					break;
				}
				qkdcService.save(dc);
			}
			Events.postEvent(Events.ON_CHANGE, this, null);
		} else {
			Messagebox.show("您还没有输入内容");
			return;
		}
	}

	public void onClick$reset() {
		initWindow();
	}

	public void onClick$close() {
		this.detach();
	}
}
