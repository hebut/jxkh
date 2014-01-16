package com.uniwin.asm.personal.ui.data.teacherinfo.kyqk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.common.util.ExportExcel;
import org.iti.gh.entity.GhFile;
import org.iti.gh.entity.GhJslw;
import org.iti.gh.entity.GhRjzz;
import org.iti.gh.entity.GhXmzz;
import org.iti.gh.entity.GhYjfx;
import org.iti.gh.service.CgService;
import org.iti.gh.service.FmzlService;
import org.iti.gh.service.GhFileService;
import org.iti.gh.service.JlhzService;
import org.iti.gh.service.JslwService;
import org.iti.gh.service.JsxmService;
import org.iti.gh.service.JxbgService;
import org.iti.gh.service.KyjhService;
import org.iti.gh.service.LwzlService;
import org.iti.gh.service.PxService;
import org.iti.gh.service.QkdcService;
import org.iti.gh.service.QtService;
import org.iti.gh.service.RjzzService;
import org.iti.gh.service.SkService;
import org.iti.gh.service.XmService;
import org.iti.gh.service.XmzzService;
import org.iti.gh.service.XshyService;
import org.iti.gh.service.YjfxService;
import org.iti.gh.service.ZsService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.uniwin.asm.personal.ui.data.teacherinfo.CkshyjWindow;
import com.uniwin.asm.personal.ui.data.teacherinfo.KyrjzzWindow;
import com.uniwin.asm.personal.ui.data.teacherinfo.KyxmzzlistWindow;
import com.uniwin.framework.common.fileuploadex.UploadFJ;
import com.uniwin.framework.entity.WkTUser;

public class RjzzWindow extends BaseWindow {

	//�������
	Toolbarbutton button16;
	Listbox listbox16;
	
	//δ��������мƻ�
	Grid grid7;
	Textbox yjnr,jjwt,yqcg;


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
	RjzzService rjzzService;
	YjfxService yjfxService;
	GhFileService ghfileService;
	WkTUser user;
	JslwService jslwService;
	JsxmService jsxmService;
	@Override
	public void initShow() {
	
		initWindow();
		final Desktop desktop=this.getDesktop();
		listbox16.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhRjzz rjzz = (GhRjzz) arg1;

				//���
				Listcell c1 = new Listcell(arg0.getIndex()+1+"");

				//����
				Listcell c2 = new Listcell();
				InnerButton ib = new InnerButton();
				ib.setLabel(rjzz.getRjName());
				ib.addEventListener(Events.ON_CLICK, new EventListener(){

					public void onEvent(Event arg0) throws Exception {

						final KyrjzzWindow cgWin = (KyrjzzWindow) Executions.createComponents
						("/admin/personal/data/teacherinfo/kyqk/rjzz/kyrjzz.zul", null, null);
						cgWin.setKuid(user.getKuId());
						cgWin.setRjzz(rjzz);
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
				c2.appendChild(ib);

				//�״η���ʱ��
				Listcell c3 = new Listcell(rjzz.getRjFirtime());
				//�ǼǺ�
				Listcell c4 = new Listcell(rjzz.getRjRegisno());
				//�Ǽ�ʱ��
				Listcell c5 = new Listcell(rjzz.getRjTime());
				//�����ǼǺ�
				Listcell c6 = new Listcell(rjzz.getRjSoftno());
				//��Ŀ����
				Listcell c7 = new Listcell();
				InnerButton bj = new InnerButton("�鿴/�༭");
				c7.appendChild(bj);
				bj.addEventListener(Events.ON_CLICK, new EventListener(){
					public void onEvent(Event arg0) throws Exception {
						 KyxmzzlistWindow cgWin = (KyxmzzlistWindow) Executions.createComponents
							("/admin/personal/data/teacherinfo/kyqk/hylw/kyxmzzlist.zul", null, null);
						 	cgWin.setKuid(rjzz.getKuId());
							cgWin.setRjzz(rjzz);
							cgWin.initWindow();
							cgWin.doHighlighted();
					}});
				
				//����
				Listcell c8 = new Listcell();
				InnerButton gn = new InnerButton();

				gn.setLabel("ɾ��");
				gn.addEventListener(Events.ON_CLICK, new EventListener(){

					public void onEvent(Event arg0) throws Exception {
						if(rjzz.getKuId().intValue()!=user.getKuId().intValue()){
							Messagebox.show("�����Ǵ���Ŀ�ύ�ˣ��ʲ���ɾ����","��ʾ", Messagebox.OK,Messagebox.EXCLAMATION);
						}else{
						if(Messagebox.show("ȷ��ɾ����?", "��ʾ", Messagebox.OK|Messagebox.CANCEL,
								Messagebox.QUESTION)==1){
							//ɾ������
							List list=ghfileService.findByFxmIdandFType(rjzz.getRjId(), 6);
							if(list.size()>0){
								//���ڸ���
								//List[] fjList=new List[list.size()];
								for(int i=0;i<list.size();i++){
									UploadFJ ufj=new UploadFJ(false);
									ufj.ReadFJ(desktop, (GhFile) list.get(i));
									ufj.DeleteFJ();
								}
							}
							List xmzzlist =	xmzzService.findByLwidAndKuid(rjzz.getRjId(),GhXmzz.RJZZ);
							if(xmzzlist != null && xmzzlist.size() !=0){
								for(int i = 0;i < xmzzlist.size();i++){
									GhXmzz xmzz = (GhXmzz) xmzzlist.get(i);
									xmzzService.delete(xmzz);
								}
							}
							List cglist =jslwService.findByLwidAndType(rjzz.getRjId(), GhJslw.RJZZ);
							if(cglist != null && cglist.size() != 0){
								for(int i=0;i<cglist.size();i++){
									GhJslw jslw=(GhJslw)cglist.get(i);
									jslwService.delete(jslw);
								}
							}
							//
							rjzzService.delete(rjzz);
							initWindow();
						}
						}
					}
				});
				c8.appendChild(gn);
				Listcell c9=new Listcell();
				InnerButton In=new InnerButton();
			    if(rjzz.getAuditState()==null){
			    	c9.setLabel("δ���");
			    }else if(rjzz.getAuditState()!=null&&rjzz.getAuditState().shortValue()==GhRjzz.AUDIT_NO){
			    	In.setLabel("δͨ��");
			    	c9.appendChild(In);
			    	In.addEventListener(Events.ON_CLICK, new EventListener(){

						public void onEvent(Event arg0) throws Exception {
						 Map arg=new HashMap();
						 arg.put("type", 6);
						 arg.put("id",rjzz.getRjId());
						 CkshyjWindow cw=(CkshyjWindow)Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/ckpsyj.zul", null, arg);
						 cw.initWindow();
						 cw.doHighlighted();
						}
						
					});
			    }else if(rjzz.getAuditState()!=null&&rjzz.getAuditState().shortValue()==GhRjzz.AUDIT_YES){
			    	In.setLabel("ͨ��");
			    	c9.appendChild(In);
			    	In.addEventListener(Events.ON_CLICK, new EventListener(){

						public void onEvent(Event arg0) throws Exception {
						 Map arg=new HashMap();
						 arg.put("type", 6);
						 arg.put("id",rjzz.getRjId());
						 CkshyjWindow cw=(CkshyjWindow)Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/ckpsyj.zul", null, arg);
						 cw.initWindow();
						 cw.doHighlighted();
						}
						
					});
			     }
				arg0.appendChild(c1); arg0.appendChild(c2); arg0.appendChild(c3);
				arg0.appendChild(c4); arg0.appendChild(c5); arg0.appendChild(c6);
				arg0.appendChild(c7); arg0.appendChild(c8); arg0.appendChild(c9); 
			}

		});
	}

	@Override
	public void initWindow() {
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		//�������
		List list16 = rjzzService.findByKuid(user.getKuId());
		listbox16.setModel(new ListModelList(list16));

	}
	public void onClick$button16(){

		final KyrjzzWindow cgWin = (KyrjzzWindow) Executions.createComponents
		("/admin/personal/data/teacherinfo/kyqk/rjzz/kyrjzz.zul", null, null);
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
	 public void onClick$button16out()  throws IOException, WriteException{
		 	//�������
		 	List list16 = rjzzService.findByKuid(user.getKuId());
			 if(list16.size()==0){
				 try {
		 				Messagebox.show("�����ݣ���������", "����", Messagebox.OK,Messagebox.INFORMATION);
		 			} catch (InterruptedException e) {
		 				e.printStackTrace();
		 			}
		 			  return;
			}else{
		    	Date now=new Date();
		    	String fileName = "�������.xls";
		    	String Title = "�������";
		    	WritableWorkbook workbook;
				List titlelist=new ArrayList();
				titlelist.add("���");
				titlelist.add("���Ŀ����");
				titlelist.add("�״η���ʱ��");
				titlelist.add("�ǼǺ�");
				titlelist.add("�Ǽ�ʱ��");
				titlelist.add("�����ǼǺ�");
				titlelist.add("��Ա���");
				titlelist.add("��������");
				titlelist.add("�о�����");
				String c[][]=new String[list16.size()][titlelist.size()];
				//��SQL�ж�����
				for(int j=0;j<list16.size();j++){
					GhRjzz rjzz  =(GhRjzz)list16.get(j);
					c[j][0]=j+1+"";
					c[j][1]=rjzz.getRjName();
					c[j][2]=rjzz.getRjFirtime();
				    c[j][3]=rjzz.getRjRegisno();
					c[j][4]=rjzz.getRjTime();
					c[j][5]=rjzz.getRjSoftno();
					c[j][6]=rjzz.getRjPerson();
					GhJslw jslw=jslwService.findByKuidAndLwidAndType(user.getKuId(), rjzz.getRjId(), GhJslw.RJZZ);
					if(jslw!=null&&jslw.getLwSelfs()!=null){
						c[j][7]=jslw.getLwSelfs()+"";
					}else{
						c[j][7]="";
					}
					  //�о�����
					if(jslw!=null&&jslw.getYjId()!=null&&jslw.getYjId()!=0L){
						c[j][8]=jslw.getYjfx().getGyName();
					}else{
						c[j][8]="";
					}
				}
		         ExportExcel ee=new ExportExcel();
				ee.exportExcel(fileName, Title, titlelist, list16.size(), c);
		    	}
		     }

}
