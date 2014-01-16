package com.uniwin.asm.personal.ui.data.teacherinfo.qtqk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.common.util.ExportExcel;
import org.iti.jxkh.business.meeting.DownloadWindow;
import org.iti.jxkh.entity.Jxkh_Honour;
import org.iti.jxkh.entity.Jxkh_HonourFile;
import org.iti.jxkh.honour.AuditAdviceWindow;
import org.iti.jxkh.service.RychService;
import org.iti.xypt.entity.Teacher;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Toolbarbutton;

import com.uniwin.asm.personal.ui.data.teacherinfo.RychWindow;
import com.uniwin.framework.entity.WkTUser;
 

public class RychviewWindow extends BaseWindow {

	//�����ƺ�
	Button button3,delete,button3out;
	Listbox listbox3;
	RychService rychService;
	WkTUser user;
	Teacher teacher;
	Long kuid;
	@Override
	public void initShow() {
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		kuid=user.getKuId();
		initWindow();
		listbox3.setItemRenderer(new ListitemRenderer(){
			public void render(Listitem arg0, Object arg1) throws Exception {
				final Jxkh_Honour ch = (Jxkh_Honour) arg1;
				arg0.setValue(ch);
				Listcell c0 = new Listcell();
				//���
				Listcell c1 = new Listcell(arg0.getIndex()+1+"");
				//�������ƣ�����������Ƹ��ݲ�ͬ�Ľ�ɫ���Բ鿴��ϸ�����б༭��ϸҳ
				Listcell c2 = new Listcell();
				InnerButton ib = new InnerButton();
				ib.setLabel(ch.getRyName());
				ib.addEventListener(Events.ON_CLICK, new EventListener(){

					public void onEvent(Event arg0) throws Exception {

						final RychWindow  w = (RychWindow ) Executions.createComponents
						("/admin/personal/basicdata/honour/addHounour.zul", null, null);
						w.setKuid(user.getKuId());
						w.setRych(ch);						
						w.initWindow();
						w.setUser(user);
						w.addEventListener(Events.ON_CHANGE, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								 initWindow();
							}
						});
						w.doModal();
					}
				});
				c2.appendChild(ib);
				//���
				Listcell c3 = new Listcell();
				c3.setLabel(ch.getRyYear());
				//�䷢��λ
				Listcell c4 = new Listcell();
				c4.setLabel(ch.getRyDep());
				//����
				Listcell c5 = new Listcell();				
				Toolbarbutton tb = new Toolbarbutton();				
				tb.setImage("/css/default/images/button/down.gif");
				tb.setTooltiptext("������ظ���");
				tb.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						DownloadWindow w = (DownloadWindow)Executions.createComponents
								("/admin/personal/basicdata/honour/download.zul", null, null);
						Set<Jxkh_HonourFile> flist = ch.getFile();
						w.setFiles(flist);
						w.setFlag("honour");
						w.initWindow();						
						w.doModal();						
					}
				});
				tb.setParent(c5);
				//���״̬
				Listcell c6 = new Listcell();
				c6.setStyle("color:red");
				switch(ch.getState()) {
				case Jxkh_Honour.NONE:
					c6.setLabel("δ���");
					break;
				case Jxkh_Honour.BUSI_PASS:
					c6.setLabel("ͨ��");
					break;
				case Jxkh_Honour.BUSI_OUT:
					c6.setLabel("�˻�");
					break;
				case Jxkh_Honour.RECORD_YES:
					c6.setLabel("�ѹ鵵");
					break;
				}
				c6.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						final AuditAdviceWindow win = (AuditAdviceWindow)Executions.createComponents("/admin/personal/basicdata/honour/advice.zul", null, null);
						win.initWindow(ch,ch.getKuId());
						win.save.setVisible(false);
						win.doModal();
					}
				});
				//����
				Listcell c7 =  new Listcell();
				Hbox hb = new Hbox();
				Toolbarbutton tb1 = new Toolbarbutton();
				tb1.setImage("/css/default/images/button/actEdit.gif");
				tb1.setTooltiptext("�༭");
				tb1.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						final RychWindow  w = (RychWindow ) Executions.createComponents
								("/admin/personal/basicdata/honour/addHounour.zul", null, null);
								w.setKuid(user.getKuId());
								w.setRych(ch);						
								w.initWindow();
								w.addEventListener(Events.ON_CHANGE, new EventListener() {
									public void onEvent(Event arg0) throws Exception {
										 initWindow();
									}
								});
								w.doModal();
					}					
				});
				Toolbarbutton tb2 = new Toolbarbutton();
				tb2.setImage("/css/default/images/button/del.gif");
				tb2.setTooltiptext("ɾ��");
				tb2.addEventListener(Events.ON_CLICK, new EventListener(){
					public void onEvent(Event arg0) throws Exception {
						/*if(ch.getState()== Jxkh_Honour.BUSI_PASS || ch.getState() == Jxkh_Honour.DEPART_PASS || ch.getState() == Jxkh_Honour.RECORD_YES) {
							org.zkoss.zul.Messagebox.show("")
						}*/
						if(ch.getState()== Jxkh_Honour.BUSI_PASS || ch.getState() == Jxkh_Honour.RECORD_YES) {
						switch(ch.getState()) {						
						case Jxkh_Honour.BUSI_PASS:
							org.zkoss.zul.Messagebox.show("���´������ͨ�������ܽ���ɾ����");
							break;
						case Jxkh_Honour.RECORD_YES:
							org.zkoss.zul.Messagebox.show("�ѹ鵵�����ܽ���ɾ����");
							break;
						}
						return;
						}						
						
						if(Messagebox.show("ȷ��ɾ����?", "��ʾ", Messagebox.OK|Messagebox.CANCEL,
								Messagebox.QUESTION)==1){
							rychService.delete(ch);
							List<Jxkh_HonourFile> flist = rychService.findFileByHonour(ch);
							if(flist.size() != 0) {
								for(int m=0;m<flist.size();m++) {
									Jxkh_HonourFile file = flist.get(m);
									rychService.delete(file);
								}
							}
							/**
							 * �����ƽ����ɾ���������ϵ�����ļ�
							 */
							initWindow();
						}
					}
				});
				tb1.setParent(hb);
				tb2.setParent(hb);
				c7.appendChild(hb);
				arg0.appendChild(c0);
				arg0.appendChild(c1); arg0.appendChild(c2); arg0.appendChild(c3);
				arg0.appendChild(c4); arg0.appendChild(c5); arg0.appendChild(c6); 
				arg0.appendChild(c7); 
			}
		});

	}
	/**
	 * ��ʼ��ҳ��
	 */
	@Override
	public void initWindow() {		
		List chlist =rychService.FindByKuid(kuid);
		listbox3.setModel(new ListModelList(chlist));

	}
	/**
	 * ��������ƺ�
	 * @throws SuspendNotAllowedException
	 * @throws InterruptedException
	 */
	public void onClick$button3() throws SuspendNotAllowedException, InterruptedException {
		final RychWindow cgWin = (RychWindow) Executions.createComponents
		("/admin/personal/basicdata/honour/addHounour.zul", null, null);
		cgWin.setKuid(user.getKuId());		
		cgWin.initWindow();
		cgWin.addEventListener(Events.ON_CHANGE, new EventListener(){
			public void onEvent(Event arg0)
			throws Exception {
				 initWindow();
				cgWin.detach();
			}
		});
		cgWin.doModal();
	}
	/**
	 * ����ɾ��
	 */
	public void onClick$delete() {		
		int count = 0;
		if(listbox3.getSelectedItems() != null) {
			Set delset = listbox3.getSelectedItems();
			Iterator iterator = delset.iterator();
			while(iterator.hasNext()) {
				Listitem item = (Listitem)iterator.next();
				Jxkh_Honour honour = (Jxkh_Honour)item.getValue();
				if(honour.getState() != Jxkh_Honour.BUSI_PASS) {
					rychService.delete(honour);
					List<Jxkh_HonourFile> flist = rychService.findFileByHonour(honour);
					if(flist.size() != 0) {
						for(int m=0;m<flist.size();m++) {
							Jxkh_HonourFile file = flist.get(m);
							rychService.delete(file);
						}
					}
					initWindow();
				}else {
					count++;
				}
			}
			if(count >0) {
				try {
	 				Messagebox.show("���²������ͨ���Ĳ���ɾ����", "��ʾ", Messagebox.OK,Messagebox.INFORMATION);
	 			} catch (InterruptedException e) {
	 				e.printStackTrace();
	 			}
	 			  return;
			}
		}else {
			try {
 				Messagebox.show("��ѡ��Ҫɾ�������ݣ�", "��ʾ", Messagebox.OK,Messagebox.INFORMATION);
 			} catch (InterruptedException e) {
 				e.printStackTrace();
 			}
 			  return;
		}
	}
	/**
	 * ����
	 * @throws IOException
	 * @throws WriteException
	 */
	public void onClick$button3out()  throws IOException, WriteException{
		//�����ƺ�
		List list3 =rychService.FindByKuid(kuid);
		if(list3 == null || list3.size() == 0){
			 try {
	 				Messagebox.show("�����ݣ���������", "����", Messagebox.OK,Messagebox.INFORMATION);
	 			} catch (InterruptedException e) {
	 				e.printStackTrace();
	 			}
	 			  return;
		}else {
			String state = "";
			String fileName = "�����ƺ�.xls";
			String Title = "�����ƺ� ";
			WritableWorkbook workbook;
			List titlelist=new ArrayList();
			titlelist.add("���");
			titlelist.add("��������");
			titlelist.add("���");
			titlelist.add("�䷢��λ");	
			titlelist.add("״̬");
			String c[][]=new String[list3.size()][titlelist.size()];
			//��SQL�ж�����
			for(int j=0;j<list3.size();j++){
				Jxkh_Honour ch =(Jxkh_Honour)list3.get(j);
			    c[j][0]=j+1+"";
				c[j][1]=ch.getRyName();
			    c[j][2]=ch.getRyYear();
				c[j][3]=ch.getRyDep();
				switch(ch.getState()) {
				case Jxkh_Honour.NONE:
					state = "δ���";
					break;
				case Jxkh_Honour.BUSI_PASS:
					state = "���´�ͨ��";
					break;
				case Jxkh_Honour.BUSI_OUT:
					state = "���´��˻�";
					break;
				case Jxkh_Honour.RECORD_YES:
					state = "�ѹ鵵";
					break;
				}
				c[j][4]=state;
			}
	         ExportExcel ee=new ExportExcel();
			ee.exportExcel(fileName, Title, titlelist, list3.size(), c);
			
		}
	}

}
