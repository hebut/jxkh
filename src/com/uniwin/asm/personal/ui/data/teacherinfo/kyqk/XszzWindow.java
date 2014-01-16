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
import org.iti.gh.entity.GhJszz;
import org.iti.gh.entity.GhXmzz;
import org.iti.gh.entity.GhZz;
import org.iti.gh.service.CgService;
import org.iti.gh.service.FmzlService;
import org.iti.gh.service.GhFileService;
import org.iti.gh.service.JlhzService;
import org.iti.gh.service.JsxmService;
import org.iti.gh.service.JszzService;
import org.iti.gh.service.JxbgService;
import org.iti.gh.service.KyjhService;
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
import org.iti.gh.service.ZzService;
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

import com.uniwin.asm.personal.ui.data.teacherinfo.CkshyjWindow;
import com.uniwin.asm.personal.ui.data.teacherinfo.JyxmzzlistWindow;
import com.uniwin.asm.personal.ui.data.teacherinfo.KyxmzzlistWindow;
import com.uniwin.asm.personal.ui.data.teacherinfo.KyxszzqkWindow;
import com.uniwin.framework.common.fileuploadex.UploadFJ;
import com.uniwin.framework.entity.WkTUser;

public class XszzWindow extends BaseWindow {

	//����ѧ��ר�����
	Toolbarbutton button4;
	Listbox listbox4;
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
	RjzzService rjzzService;
	YjfxService yjfxService;
	GhFileService ghfileService;
	WkTUser user;
	JsxmService jsxmService;
	ZzService zzService;
	JszzService jszzService;
	@Override
	public void initShow() {
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		initWindow();
		
		listbox4.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhZz zz = (GhZz) arg1;

				//���
				Listcell c1 = new Listcell(arg0.getIndex()+1+"");

				//ר������
				Listcell c2 = new Listcell();
				InnerButton ib = new InnerButton();
				ib.setLabel(zz.getZzMc());
				ib.addEventListener(Events.ON_CLICK, new EventListener(){
					public void onEvent(Event arg0) throws Exception {
						final KyxszzqkWindow cgWin = (KyxszzqkWindow) Executions.createComponents
							("/admin/personal/data/teacherinfo/kyqk/kyzz/kyxszzqk.zul", null, null);
						 	cgWin.setKuid(zz.getKuId());
							cgWin.setZz(zz);
							cgWin.initWindow();
							cgWin.doHighlighted();
							cgWin.addEventListener(Events.ON_CHANGE, new EventListener(){

								public void onEvent(Event arg0)
								throws Exception {
									initWindow();
									cgWin.detach();
								}

							});
					}});
				c2.appendChild(ib);
			
				//���浥λ��ISBN
				Listcell c3 = new Listcell(zz.getZzKw());

				//����ʱ��
				Listcell c4 = new Listcell(zz.getZzPublitime());
				
				//���
				Listcell c5 = new Listcell(zz.getZzEditionno());
				Listcell c6 = new Listcell();
				InnerButton ib1 = new InnerButton("�鿴/�༭");
				c6.appendChild(ib1);
				ib1.addEventListener(Events.ON_CLICK, new EventListener(){
					public void onEvent(Event arg0) throws Exception {
						 JyxmzzlistWindow cgWin = (JyxmzzlistWindow) Executions.createComponents
							("/admin/personal/data/teacherinfo/jxqk/jyxmzzlist.zul", null, null);
						 	cgWin.setKuid(zz.getKuId());
							cgWin.setZz(zz);
							cgWin.initWindow();
							cgWin.doHighlighted();
					}});
				//����
				Listcell c7 = new Listcell();
				InnerButton gn = new InnerButton();
				gn.setLabel("ɾ��");
				gn.addEventListener(Events.ON_CLICK, new EventListener(){

					public void onEvent(Event arg0) throws Exception {
						if(zz.getKuId().intValue()!=user.getKuId().intValue()){
							Messagebox.show("�����Ǵ���Ŀ�ύ�ˣ��ʲ���ɾ����","��ʾ", Messagebox.OK,Messagebox.EXCLAMATION);
						}else{
						if(Messagebox.show("ȷ��ɾ����?", "��ʾ", Messagebox.OK|Messagebox.CANCEL,
								Messagebox.QUESTION)==1){
							//ɾ������
							List list=ghfileService.findByFxmIdandFType(zz.getZzId(), 5);
							if(list.size()>0){
								//���ڸ���
								//List[] fjList=new List[list.size()];
								for(int i=0;i<list.size();i++){
									UploadFJ ufj=new UploadFJ(false);
									ufj.ReadFJ(getDesktop(), (GhFile) list.get(i));
									ufj.DeleteFJ();
								}
							}
							List xmzzlist =	xmzzService.findByLwidAndKuid(zz.getZzId(),GhXmzz.KYZZ);
							if(xmzzlist != null && xmzzlist.size() !=0){
								for(int i = 0;i < xmzzlist.size();i++){
									GhXmzz xmzz = (GhXmzz) xmzzlist.get(i);
									xmzzService.delete(xmzz);
								}
							}
							List cglist =jszzService.findByLwidAndType(zz.getZzId(), GhJszz.ZZ);
							if(cglist != null && cglist.size() != 0){
								for(int i=0;i<cglist.size();i++){
									GhJszz jszz=(GhJszz)cglist.get(i);
									jszzService.delete(jszz);
								}
							}
							//
							xmService.delete(zz);
							initWindow();
						}
						}
					}
				});
				c7.appendChild(gn);
			    Listcell c8=new Listcell();
			    InnerButton Ib=new InnerButton();
		        if(zz.getAuditState()==null){
		             c8.setLabel("δ���");
		        }else if(zz.getAuditState()!=null&&zz.getAuditState().shortValue()==GhZz.AUDIT_NO){
		        	Ib.setLabel("δͨ��");
		        	c8.appendChild(Ib);
	            	Ib.addEventListener(Events.ON_CLICK, new EventListener(){

						public void onEvent(Event arg0) throws Exception {
						 Map arg=new HashMap();
						 arg.put("type", 5);
						 arg.put("id",zz.getZzId());
						 CkshyjWindow cw=(CkshyjWindow)Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/ckpsyj.zul", null, arg);
						 cw.initWindow();
						 cw.doHighlighted();
						}
						
					});
		        }else if(zz.getAuditState()!=null&&zz.getAuditState().shortValue()==GhZz.AUDIT_YES){
		        	Ib.setLabel("ͨ��");
		        	c8.appendChild(Ib);
	            	Ib.addEventListener(Events.ON_CLICK, new EventListener(){

						public void onEvent(Event arg0) throws Exception {
						 Map arg=new HashMap();
						 arg.put("type", 5);
						 arg.put("id",zz.getZzId());
						 CkshyjWindow cw=(CkshyjWindow)Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/ckpsyj.zul", null, arg);
						 cw.initWindow();
						 cw.doHighlighted();
						}
						
					});
		        }
				arg0.appendChild(c1); arg0.appendChild(c2); arg0.appendChild(c3);
				arg0.appendChild(c4); arg0.appendChild(c5); arg0.appendChild(c6);
				 arg0.appendChild(c7);  arg0.appendChild(c8);
			}

		});

	}

	@Override
	public void initWindow() {
		//�������ר��
		List list4 = zzService.findByKuidAndType(user.getKuId(), GhZz.ZZ,GhJszz.ZZ);
		listbox4.setModel(new ListModelList(list4));

	}
	public void onClick$button4(){

		final KyxszzqkWindow cgWin = (KyxszzqkWindow) Executions.createComponents
		("/admin/personal/data/teacherinfo/kyqk/kyzz/kyxszzqk.zul", null, null);
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
		 	//�������ר��
			List list4 = zzService.findByKuidAndType(user.getKuId(), GhZz.ZZ,GhJszz.ZZ);
			 if(list4.size()==0){
				 try {
		 				Messagebox.show("�����ݣ���������", "����", Messagebox.OK,Messagebox.INFORMATION);
		 			} catch (InterruptedException e) {
		 				e.printStackTrace();
		 			}
		 			  return;
			}else{
		    	Date now=new Date();
		    	String fileName = "����ѧ��ר��.xls";
		    	String Title = "����ѧ��ר��";
		    	WritableWorkbook workbook;
				List titlelist=new ArrayList();
				titlelist.add("���");
				titlelist.add("��������");
				titlelist.add("��������");
				titlelist.add("ѧ�Ʒ���");
				titlelist.add("���浥λ");
				titlelist.add("����ʱ�䣨��ʽ��2010/3/9�� ");
				titlelist.add("���");
				titlelist.add("ISBN��");
				titlelist.add("���б༭��");
				titlelist.add("��������");
				titlelist.add("�е�����");
				titlelist.add("��ע");
				String c[][]=new String[list4.size()][titlelist.size()];
				//��SQL�ж�����
				for(int j=0;j<list4.size();j++){
					GhZz zz = (GhZz)list4.get(j);
				    c[j][0]=j+1+"";
					c[j][1]=zz.getZzMc();
					if(zz.getZzWorktype() == null ||zz.getZzWorktype().equals("") || zz.getZzWorktype().trim().equalsIgnoreCase("-1")){
						c[j][2]="";	
					}else if(zz.getZzWorktype().equalsIgnoreCase("1")){
						c[j][2]="����";
					}else if(zz.getZzWorktype().equalsIgnoreCase("2")){
						c[j][2]="����";
					}else if(zz.getZzWorktype().equalsIgnoreCase("3")){
						c[j][2]="ר��";
					}else if(zz.getZzWorktype().equalsIgnoreCase("4")){
						c[j][2]="����";
					}else if(zz.getZzWorktype().equalsIgnoreCase("5")){
						c[j][2]="�̲�";
					}else if(zz.getZzWorktype().equalsIgnoreCase("6")){
						c[j][2]="�ο���򹤾���";
					}else if(zz.getZzWorktype().equalsIgnoreCase("7")){
						c[j][2]="���ļ�";
					}else if(zz.getZzWorktype().equalsIgnoreCase("8")){
						c[j][2]="����";
					}else if(zz.getZzWorktype().equalsIgnoreCase("9")){
						c[j][2]="���鱨��";
					}
					//ѧ�Ʒ���
					if(zz.getZzSubjetype() == null || zz.getZzSubjetype().equals("") || zz.getZzSubjetype().trim().equalsIgnoreCase("-1")){
						c[j][3]="";
					}else if(zz.getZzSubjetype().equalsIgnoreCase( "1")){
						c[j][3]="��Ȼ��ѧ";
					}else if(zz.getZzSubjetype().equalsIgnoreCase( "2")){
						c[j][3]="����ѧ";
					}else if(zz.getZzSubjetype().equalsIgnoreCase( "3")){
						c[j][3]="����";
					}
					
					c[j][4]=zz.getZzKw();
					c[j][5]=zz.getZzPublitime();
					c[j][6]=zz.getZzEditionno();
					c[j][7]=zz.getZzIsbn();
				    c[j][8]=zz.getZzZb()!=null?zz.getZzZb():""+zz.getZzFzb()!=null?zz.getZzFzb():""+zz.getZzBz()!=null?zz.getZzBz():"";
				    GhJszz jslw=jszzService.findByKuidAndLwidAndType(user.getKuId(), zz.getZzId(), GhJszz.ZZ);
				    if(jslw!=null&&jslw.getZzSelfs()!=null){
				    	 c[j][9] =jslw.getZzSelfs()+"";
				    }else{
				    	 c[j][9] ="";
				    
				    }
				    if(jslw!=null&&jslw.getZzWords()!=null){
				    	 c[j][10]=jslw.getZzWords();
				    }else{
				   	 c[j][10]="";
				    }
					c[j][11]=zz.getZzRemark();
				}
		         ExportExcel ee=new ExportExcel();
				ee.exportExcel(fileName, Title, titlelist, list4.size(), c);
		    	}
		     }

}
