package org.iti.jxgl.exam.examplan;
/*
 * ��װMessageBox--�����Ի������Ϣ����
 * */
import org.zkoss.zul.Messagebox;

public class MessageBoxshow {
	//��ʾ��Ϣ
	public static void showInfo(String value) { 
        try { 
         Messagebox.show(value, "��ʾ", Messagebox.OK, Messagebox.INFORMATION); 
        } catch (InterruptedException e) { 
         e.printStackTrace(); 
        } 
    } 
}
