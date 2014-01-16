package org.iti.jxkh.honour;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.common.util.ExportExcel;
import org.iti.jxkh.business.meeting.DownloadWindow;
import org.iti.jxkh.entity.Jxkh_Honour;
import org.iti.jxkh.entity.Jxkh_HonourFile;
import org.iti.jxkh.service.RychService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

public class HonourAuditWindow extends Window implements AfterCompose {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Listbox honourList,auditState;
	private Paging zxPaging;
	private Groupbox condition;
	private Textbox honourName;	
	
	private RychService rychService;
	private UserService userService;
	
	private List<Jxkh_Honour> list = new ArrayList<Jxkh_Honour>();
	
	WkTUser user;

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);	
		
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		auditState.setSelectedIndex(0);
		honourList.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				final Jxkh_Honour ch = (Jxkh_Honour) arg1;
				Listcell c0 = new Listcell();
				//���
				Listcell c1 = new Listcell(arg0.getIndex()+1+"");
				//�������ƣ�����������Ƹ��ݲ�ͬ�Ľ�ɫ���Բ鿴��ϸ�����б༭��ϸҳ
				Listcell c2 = new Listcell();
				InnerButton ib = new InnerButton();
				ib.setLabel(ch.getRyName());
				ib.addEventListener(Events.ON_CLICK, new EventListener(){

					public void onEvent(Event arg0) throws Exception {

						final AddHonourWindow  w = (AddHonourWindow ) Executions.createComponents
						("/admin/personal/basicdata/honour/addAuditHonour.zul", null, null);
						w.setKuid(ch.getKuId());
						w.setRych(ch);						
						w.initWindow();
						w.doModal();
					}
				});
				c2.appendChild(ib);
				//�����û�
				Listcell c3 = new Listcell();
				WkTUser u = userService.getUserBykuid(ch.getKuId());				
				c3.setLabel(u == null?"":u.getKuName());
				//�䷢��λ
				Listcell c4 = new Listcell();
				c4.setLabel(ch.getRyDep());
				//����
				Listcell c5 = new Listcell();
				Toolbarbutton tb = new Toolbarbutton();
				tb.setImage("/css/default/images/button/down.gif");
				tb.setTooltiptext("������ص�֤������");
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
				Listcell c6 = new Listcell();
				Toolbarbutton tb1 = new Toolbarbutton();
				tb1.setImage("/css/default/images/button/actEdit.gif");
				tb1.setTooltiptext("��д������");
				tb1.setParent(c6);				
				tb1.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						if(ch.getState() == Jxkh_Honour.BUSI_PASS || ch.getState() == Jxkh_Honour.RECORD_YES) {
							RecordWindow rw = (RecordWindow)Executions.createComponents("/admin/jxkh/staffAudit/honour/recordcode.zul", null, null);
							rw.initWindow(ch);
							rw.addEventListener(Events.ON_CHANGE, new EventListener() {
								public void onEvent(Event arg0) throws Exception {
									initWindow();
								}
							});
							rw.doModal();
						}else {
							Messagebox.show("�˼�¼δ�鵵�����ܽ��в鿴��");
							return;
						}
						
					}
				});			
				
				//���״̬
				Listcell c7 = new Listcell();
				c7.setStyle("color:red");
				switch(ch.getState()) {
				case Jxkh_Honour.NONE:
					c7.setLabel("δ���");
					break;
				case Jxkh_Honour.BUSI_PASS:
					c7.setLabel("ͨ��");
					break;
				case Jxkh_Honour.BUSI_OUT:
					c7.setLabel("�˻�");
					break;
				case Jxkh_Honour.RECORD_YES:
					c7.setLabel("�ѹ鵵");
					break;
				}
				c7.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						final AuditAdviceWindow win = (AuditAdviceWindow)Executions.createComponents("/admin/jxkh/staffAudit/honour/advice.zul", null, null);
						win.initWindow(ch,user.getKuId());
						win.addEventListener(Events.ON_CHANGE, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								win.detach();
								Messagebox.show("����ѹ鵵����㡰���������е�ͼ�갴ť��д�����ţ�");
								initWindow();
							}
						});
						win.doModal();
					}
				});
				arg0.appendChild(c0);
				arg0.appendChild(c1); arg0.appendChild(c2); arg0.appendChild(c3);
				arg0.appendChild(c4); arg0.appendChild(c5);	arg0.appendChild(c6);							
				arg0.appendChild(c7);
				
			}
		});
		initWindow();		
	}
	
	private void initWindow() {
		List<Jxkh_Honour> hlist = new ArrayList();
		hlist = rychService.findAllHonour();
		honourList.setModel(new ListModelList(hlist));
		list.clear();
		list = hlist;
	}
	
	public void onClick$passAll() throws SuspendNotAllowedException, InterruptedException {
		if(honourList.getSelectedItems()==null||honourList.getSelectedItems().size()<=0){
			try {
				Messagebox.show("��ѡ��Ҫ��˵������ƺţ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		List<Jxkh_Honour> honourlist = new ArrayList();
		honourlist.clear();
		ListModel model=honourList.getModel();		
		for(int i=0;i<model.getSize();i++){
			honourlist.add((Jxkh_Honour) model.getElementAt(i));
		}
		final BatchAuditWindow win = (BatchAuditWindow)Executions.createComponents("/admin/jxkh/staffAudit/honour/batchAudit.zul", null, null);
		win.iniWindow(honourlist);
		win.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				win.detach();
				initWindow();
			}
		});
		win.doModal();
	}
	
	
	/*private void onClick$exportExcel() throws WriteException, IOException {
		//�����ƺ�
				List<Jxkh_Honour> list3 =rychService.findAllHonour();
				if(list3 == null || list3.size() == 0){
					 try {
			 				Messagebox.show("�����ݣ���������", "����", Messagebox.OK,Messagebox.INFORMATION);
			 			} catch (InterruptedException e) {
			 				e.printStackTrace();
			 			}
			 			  return;
				}else {
					String fileName = "�����ƺ�.xls";
					String Title = "�����ƺ� ";
					WritableWorkbook workbook;
					List titlelist=new ArrayList();
					titlelist.add("���");
					titlelist.add("��������");
					titlelist.add("���");
					titlelist.add("�䷢��λ");			
					String c[][]=new String[list3.size()][titlelist.size()];
					//��SQL�ж�����
					for(int j=0;j<list3.size();j++){
						Jxkh_Honour ch =(Jxkh_Honour)list3.get(j);
					    c[j][0]=j+1+"";
						c[j][1]=ch.getRyName();
					    c[j][2]=ch.getRyYear();
						c[j][3]=ch.getRyDep();
					
					}
			         ExportExcel ee=new ExportExcel();
					ee.exportExcel(fileName, Title, titlelist, list3.size(), c);
					
				}
	}*/

	public void onClick$searchcbutton() {
		if(condition.isVisible()) {
			condition.setVisible(false);
		}else {
			condition.setVisible(true);
		}
	}
	
	public void onClick$query() {
		if("".equals(honourName.getValue()) && auditState.getSelectedIndex() == 0) {
			try {
				Messagebox.show("��ѡ���ѯ������", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		String name = honourName.getValue();
		int index = auditState.getSelectedIndex();
		List<Jxkh_Honour> hlist = rychService.findHonourByCondition(name, index);
		honourList.setModel(new ListModelList(hlist));		
		list.clear();
		list = hlist;
	}
	
	public void onClick$reset() {
		honourName.setValue("");
		auditState.setSelectedIndex(0);
	}
	
	public void onClick$exportExcel() {
		List<Jxkh_Honour> list3 =this.list;		
		if(list3 == null || list3.size() == 0){
			 try {
	 				Messagebox.show("�����ݣ���������", "����", Messagebox.OK,Messagebox.INFORMATION);
	 			} catch (InterruptedException e) {
	 				e.printStackTrace();
	 			}
	 			  return;
		}else {
			String fileName = "�����ƺ�.xls";
			String Title = "�����ƺ� ";
			String state = "";
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
			try {
				ee.exportExcel(fileName, Title, titlelist, list3.size(), c);
			} catch (WriteException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}

}
