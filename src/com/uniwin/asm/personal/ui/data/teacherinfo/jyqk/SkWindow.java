package com.uniwin.asm.personal.ui.data.teacherinfo.jyqk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.common.util.ExportExcel;
import org.iti.gh.entity.GhFile;
import org.iti.gh.entity.GhSk;
import org.iti.gh.service.CgService;
import org.iti.gh.service.FmzlService;
import org.iti.gh.service.GhFileService;
import org.iti.gh.service.JlhzService;
import org.iti.gh.service.JsxmService;
import org.iti.gh.service.JxbgService;
import org.iti.gh.service.KyjhService;
import org.iti.gh.service.LwzlService;
import org.iti.gh.service.PkpyService;
import org.iti.gh.service.PxService;
import org.iti.gh.service.QkdcService;
import org.iti.gh.service.QtService;
import org.iti.gh.service.SkService;
import org.iti.gh.service.XmService;
import org.iti.gh.service.XmzzService;
import org.iti.gh.service.XshyService;
import org.iti.gh.service.YjfxService;
import org.iti.gh.service.ZsService;
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
import org.zkoss.zul.Toolbarbutton;

import com.uniwin.asm.personal.ui.data.teacherinfo.SkqkWindow;
import com.uniwin.framework.common.fileuploadex.UploadFJ;
import com.uniwin.framework.entity.WkTUser;

public class SkWindow extends BaseWindow {

	//�ڿ����
	Toolbarbutton button13;
	Listbox listbox13;
	FmzlService fmzlService;
	QtService qtService;
	KyjhService kyjhService;
	PxService pxService;
	XshyService xshyService;
	SkService skService;
	ZsService zsService;
	JlhzService jlhzService;
	JxbgService jxbgService;
	XmService xmService;
	XmzzService xmzzService;
	QkdcService qkdcService;
	CgService cgService;
	LwzlService lwzlService;
	PkpyService pkpyService;
	YjfxService yjfxService;
	GhFileService ghfileService;
	WkTUser user;
	JsxmService jsxmService;
	@Override
	public void initShow() {
		initWindow();
		listbox13.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhSk sk = (GhSk) arg1;

				//���
				Listcell c0 = new Listcell(arg0.getIndex()+1+"");

				//�γ�����
				Listcell c1 = new Listcell();
				InnerButton ib = new InnerButton();
				ib.setLabel(sk.getSkMc());
				ib.addEventListener(Events.ON_CLICK, new EventListener(){

					public void onEvent(Event arg0) throws Exception {

						final SkqkWindow cgWin = (SkqkWindow) Executions.createComponents
						("/admin/personal/data/teacherinfo/jxqk/skqk.zul", null, null);
						cgWin.setSk(sk);
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
				});
				c1.appendChild(ib);
				//�ڿζ���
				Listcell c2 = new Listcell(sk.getSkGrade());
				//רҵ
				Listcell c3 = new Listcell(sk.getSkZy());
				
				//ʱ��
				Listcell c4 = new Listcell(sk.getSkYear());
				
				//ѧʱ������+���飩
				Listcell c5 = new Listcell(sk.getSkXs());

				//������
				Listcell c6 = new Listcell(sk.getThWork());
				
				//���״̬
				String c7String;
				if(sk.getAuditState()!=null) {
					if(sk.getAuditState() == 0) {
						c7String="δ���";
					}else {
						c7String="�����";
					}
				}else {					
					c7String="δ���";
					Short state = 0;
					sk.setAuditState(state);
				}
				
				Listcell c7 = new Listcell(c7String);

				//���� 
				Listcell c8 = new Listcell();
				InnerButton gn = new InnerButton();

				gn.setLabel("ɾ��");
				gn.addEventListener(Events.ON_CLICK, new EventListener(){

					public void onEvent(Event arg0) throws Exception {
						if(Messagebox.show("ȷ��ɾ����?", "��ʾ", Messagebox.OK|Messagebox.CANCEL,
								Messagebox.QUESTION)==1){
							//ɾ������
							List list=ghfileService.findByFxmIdandFType(sk.getSkId(),15);
							if(list.size()>0){
								//���ڸ���
								for(int i=0;i<list.size();i++){
									UploadFJ ufj=new UploadFJ(false);
									ufj.ReadFJ(getDesktop(), (GhFile) list.get(i));
									ufj.DeleteFJ();
								}
							}
							xmService.delete(sk);
							initWindow();
						}
					}
				});
				c8.appendChild(gn);
				arg0.appendChild(c0);arg0.appendChild(c1); arg0.appendChild(c2);
				arg0.appendChild(c3);arg0.appendChild(c4); arg0.appendChild(c5);
				arg0.appendChild(c6);arg0.appendChild(c7); arg0.appendChild(c8); 
			}
		});

	}

	@Override
	public void initWindow() {
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		//�ڿ����
		List list13 = skService.findByKuid(user.getKuId());
		listbox13.setModel(new ListModelList(list13));

	}
	public void onClick$button13(){

		final SkqkWindow cgWin = (SkqkWindow) Executions.createComponents
		("/admin/personal/data/teacherinfo/jxqk/skqk.zul", null, null);
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
	public void onClick$button13out()  throws IOException, WriteException{
		//�ڿ����
		List list13 = skService.findByKuid(user.getKuId());
		if(list13 == null || list13.size() == 0){
			 try {
	 				Messagebox.show("�����ݣ���������", "����", Messagebox.OK,Messagebox.INFORMATION);
	 			} catch (InterruptedException e) {
	 				e.printStackTrace();
	 			}
	 			  return;
		}else {
			String fileName = "�ڿ����.xls";
			String Title = "�ڿ����";
			WritableWorkbook workbook;
			List titlelist=new ArrayList();
			titlelist.add("���");
			titlelist.add("�γ�����");
			titlelist.add("רҵ");
			titlelist.add("ѧʱ������+���飩 ");
			titlelist.add("������ ");
			titlelist.add("�ڿΰ༶ ");
			titlelist.add("��� ");
			String c[][]=new String[list13.size()][titlelist.size()];
			//��SQL�ж�����
			for(int j=0;j<list13.size();j++){
				GhSk sk = (GhSk)list13.get(j);
			    c[j][0]=j+1+"";
				c[j][1]=sk.getSkMc();
			    c[j][2]=sk.getSkZy();
				c[j][3]=sk.getSkXs();
				c[j][4]=sk.getThWork();
				c[j][5]=sk.getSkGrade();
				c[j][6]=sk.getSkYear();
			}
	         ExportExcel ee=new ExportExcel();
			ee.exportExcel(fileName, Title, titlelist, list13.size(), c);
			
		}
	}

}
