package org.iti.jxgl.exam.examplan;
/*
 * 封装MessageBox--弹出对话框的信息种类
 * */
import org.zkoss.zul.Messagebox;

public class MessageBoxshow {
	//显示信息
	public static void showInfo(String value) { 
        try { 
         Messagebox.show(value, "提示", Messagebox.OK, Messagebox.INFORMATION); 
        } catch (InterruptedException e) { 
         e.printStackTrace(); 
        } 
    } 
}
