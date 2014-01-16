package com.uniwin.asm.personal.ui.data.teacherinfo.qtqk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.common.util.ExportExcel;
import org.iti.gh.entity.GhSgcf;
import org.iti.gh.service.SgcfService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.uniwin.asm.personal.ui.data.teacherinfo.SgcfWindow;
import com.uniwin.framework.entity.WkTUser;

public class SgCfWindow extends BaseWindow {

	//�����¹ʴ���
	Button button4;
	Listbox listbox4;
	SgcfService sgcfService;
	WkTUser user;
	long kuid;
	@Override
	public void initShow() {
		
		initWindow();
		listbox4.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhSgcf cf = (GhSgcf) arg1;

				//���
				Listcell c1 = new Listcell(arg0.getIndex()+1+"");

				//�¹�ԭ��
				Listcell c2 = new Listcell();
				InnerButton ib = new InnerButton();
				ib.setLabel(cf.getSgReason());
				ib.addEventListener(Events.ON_CLICK, new EventListener(){
					public void onEvent(Event arg0) throws Exception {
						final SgcfWindow w = (SgcfWindow) Executions.createComponents
						("/admin/personal/data/teacherinfo/qtqk/sgcf/sgcf.zul", null, null);
						w.setKuid(user.getKuId());
						w.setSgcf(cf);
						w.doHighlighted();
						w.initWindow();
						w.addEventListener(Events.ON_CHANGE, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								initWindow();
							}
						});
					}
				});
				c2.appendChild(ib);

				//���
				Listcell c3 = new Listcell();
				c3.setLabel(cf.getSgYear());
				//���ֵ�λ
				Listcell c4 = new Listcell();
				c4.setLabel(cf.getSgDep());
				//����
				Listcell c5 =  new Listcell();
				InnerButton gn = new InnerButton();
				gn.setLabel("ɾ��");
				gn.addEventListener(Events.ON_CLICK, new EventListener(){
					public void onEvent(Event arg0) throws Exception {
						if(Messagebox.show("ȷ��ɾ����?", "��ʾ", Messagebox.OK|Messagebox.CANCEL,
								Messagebox.QUESTION)==1){
							sgcfService.delete(cf);
							initWindow();
						}
					}
				});
				c5.appendChild(gn);
				arg0.appendChild(c1); arg0.appendChild(c2); arg0.appendChild(c3);
				arg0.appendChild(c4); arg0.appendChild(c5);
				
			}
		});

	}

	@Override
	public void initWindow() {
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		kuid=user.getKuId();
		List cflist =sgcfService.FindByKuid(kuid);
		listbox4.setModel(new ListModelList(cflist));

	}
	public void onClick$button4(){
		
		final SgcfWindow cgWin = (SgcfWindow) Executions.createComponents
		("/admin/personal/data/teacherinfo/qtqk/sgcf/sgcf.zul", null, null);
		cgWin.setKuid(user.getKuId());
		cgWin.doHighlighted();
		cgWin.initWindow();
		cgWin.addEventListener(Events.ON_CHANGE, new EventListener(){

			public void onEvent(Event arg0)
			throws Exception {
				initWindow();
				cgWin.detach();
			}
		});
	
	}
	public void onClick$button4out()  throws IOException, WriteException{
		//�����¹ʴ���
		List list4 =sgcfService.FindByKuid(kuid);
		if(list4 == null || list4.size() == 0){
			 try {
	 				Messagebox.show("�����ݣ���������", "����", Messagebox.OK,Messagebox.INFORMATION);
	 			} catch (InterruptedException e) {
	 				e.printStackTrace();
	 			}
	 			  return;
		}else {
			String fileName = "�����¹ʴ���.xls";
			String Title = "�����¹ʴ��֣� ";
			WritableWorkbook workbook;
			List titlelist=new ArrayList();
			titlelist.add("���");
			titlelist.add("�¹�ԭ��");
			titlelist.add("���");
			titlelist.add("�¹ʴ��ֽ��");
			titlelist.add("���ֵ�λ");
			
			String c[][]=new String[list4.size()][titlelist.size()];
			//��SQL�ж�����
			for(int j=0;j<list4.size();j++){
				GhSgcf cf =(GhSgcf)list4.get(j);
				  c[j][0]=j+1+"";
					c[j][1]=cf.getSgReason();
				    c[j][2]=cf.getSgYear();
					c[j][3]=cf.getSgName();
					c[j][4]=cf.getSgDep();
			}
	         ExportExcel ee=new ExportExcel();
			ee.exportExcel(fileName, Title, titlelist, list4.size(), c);
			
		}
	}
}
