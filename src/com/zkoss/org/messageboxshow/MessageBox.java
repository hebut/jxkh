package com.zkoss.org.messageboxshow;

import org.zkoss.zul.Messagebox;

public class MessageBox {

	public static void showInfo(String value) {
  
		try {
			Messagebox.show(value, "��ʾ��Ϣ", Messagebox.OK, Messagebox.INFORMATION);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static int showQuestion(String value) {
		try {
			return Messagebox.show(value, "ѯ����Ϣ", Messagebox.YES | Messagebox.NO,
					Messagebox.QUESTION);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return Messagebox.NO;
	}

	public static void showWarning(String value) {
		try {
			Messagebox.show(value, "������Ϣ", Messagebox.OK, Messagebox.EXCLAMATION);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void showError(String value) {
		try {
			Messagebox.show(value, "������Ϣ", Messagebox.OK, Messagebox.ERROR);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
