package com.uniwin.asm.personal.ui.data.teacherinfo.jyqk;

import java.io.IOException;
import java.util.ArrayList;
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
import org.iti.gh.entity.GhXm;
import org.iti.gh.entity.GhXmzz;
import org.iti.gh.entity.GhZz;
import org.iti.gh.service.CgService;
import org.iti.gh.service.FmzlService;
import org.iti.gh.service.GhFileService;
import org.iti.gh.service.JlhzService;
import org.iti.gh.service.JslwService;
import org.iti.gh.service.JsxmService;
import org.iti.gh.service.JszzService;
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

import com.uniwin.asm.personal.ui.data.teacherinfo.CkjcqkWindow;
import com.uniwin.asm.personal.ui.data.teacherinfo.CkshyjWindow;
import com.uniwin.framework.common.fileuploadex.UploadFJ;
import com.uniwin.framework.entity.WkTUser;

public class JcqkWindow extends BaseWindow {

	
	//�̲����
	Toolbarbutton button11;
	Listbox listbox11;
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
	PkpyService pkpyService;
	YjfxService yjfxService;
	GhFileService ghfileService;
	WkTUser user;
	JslwService jslwService;
	JsxmService jsxmService;
	ZzService zzService;
	JszzService jszzService;
	@Override
	public void initShow() {
		initWindow();
		listbox11.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhZz zz = (GhZz) arg1;

				//���
				Listcell c1 = new Listcell(arg0.getIndex()+1+"");

				//�̲�����
				Listcell c2 = new Listcell();
				InnerButton ib = new InnerButton();
				ib.setLabel(zz.getZzMc());
				ib.addEventListener(Events.ON_CLICK, new EventListener(){

					public void onEvent(Event arg0) throws Exception {
						
						final CkjcqkWindow cgWin = (CkjcqkWindow) Executions.createComponents
						("/admin/personal/data/teacherinfo/jxqk/ckjcqk.zul", null, null);
						cgWin.setJc(zz);
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
				//���浥λ��ISBN
				Listcell c3 = new Listcell(zz.getZzKw());

				//����ʱ��
				Listcell c4 = new Listcell(zz.getZzPublitime());
				//�����������
				GhJszz jszz=jszzService.findByKuidAndLwidAndType(user.getKuId(), zz.getZzId(), GhJszz.JC);
				Listcell c5 = new Listcell();
				if(jszz!=null&&jszz.getZzSelfs()!=null){
					c5.setLabel(jszz.getZzSelfs());
				}else{
					c5.setLabel("");
				}
				//����
				Listcell c6 = new Listcell();
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
							List list=ghfileService.findByFxmIdandFType(zz.getZzId(),15);
							if(list.size()>0){
								//���ڸ���
								for(int i=0;i<list.size();i++){
									UploadFJ ufj=new UploadFJ(false);
									ufj.ReadFJ(getDesktop(), (GhFile) list.get(i));
									ufj.DeleteFJ();
								}
							}
							List xmzzlist =	xmzzService.findByLwidAndKuid(zz.getZzId(),GhXmzz.JYJC);
							if(xmzzlist != null && xmzzlist.size() !=0){
								for(int i = 0;i < xmzzlist.size();i++){
									GhXmzz xmzz = (GhXmzz) xmzzlist.get(i);
									xmzzService.delete(xmzz);
								}
							}
							List cglist =jszzService.findByLwidAndType(zz.getZzId(), GhJszz.JC);
							if(cglist != null && cglist.size() != 0){
								for(int i=0;i<cglist.size();i++){
									GhJszz jsxm=(GhJszz)cglist.get(i);
									jszzService.delete(jsxm);
								}
							}
							xmService.delete(zz);
							initWindow();
						}
						}
					}
				});
				c6.appendChild(gn);
				Listcell c7=new Listcell();
				InnerButton IB = new InnerButton();
				if(zz.getAuditState()==null){
					c7.setLabel("δ���");
				}else if(zz.getAuditState()!=null&&zz.getAuditState().shortValue()==GhXm.AUDIT_NO){
					IB.setLabel("δͨ��");
					c7.appendChild(IB);
					IB.addEventListener(Events.ON_CLICK, new EventListener(){

						public void onEvent(Event arg0) throws Exception {
						 Map arg=new HashMap();
						 arg.put("type", 15);
						 arg.put("id",zz.getZzId());
						 CkshyjWindow cw=(CkshyjWindow)Executions.createComponents("/admin/personal/data/teacherinfo/jxqk/ckpsyj.zul", null, arg);
						 cw.initWindow();
						 cw.doHighlighted();
						}
						
					});
				}else if(zz.getAuditState()!=null&&zz.getAuditState().shortValue()==GhXm.AUDIT_YES){
					IB.setLabel("ͨ��");
					c7.appendChild(IB);
					IB.addEventListener(Events.ON_CLICK, new EventListener(){

						public void onEvent(Event arg0) throws Exception {
						 Map arg=new HashMap();
						 arg.put("type", 15);
						 arg.put("id",zz.getZzId());
						 CkshyjWindow cw=(CkshyjWindow)Executions.createComponents("/admin/personal/data/teacherinfo/jxqk/ckpsyj.zul", null, arg);
						 cw.initWindow();
						 cw.doHighlighted();
						}
						
					});
				}
				arg0.appendChild(c1); arg0.appendChild(c2); arg0.appendChild(c3);
				arg0.appendChild(c4); arg0.appendChild(c5); arg0.appendChild(c6);
				arg0.appendChild(c7);

			}
		});

	}

	@Override
	public void initWindow() {
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		//�̲����
		List list11 = zzService.findByKuidAndType(user.getKuId(),GhZz.JC,GhJszz.JC);
		listbox11.setModel(new ListModelList(list11));
	}
	public void onClick$button11(){
		final CkjcqkWindow cgWin = (CkjcqkWindow) Executions.createComponents
		("/admin/personal/data/teacherinfo/jxqk/ckjcqk.zul", null, null);
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
	public void onClick$button11out()  throws IOException, WriteException{
		//�̲����
		List list11 = zzService.findByKuidAndType(user.getKuId(),GhZz.JC,GhJszz.JC);
		if(list11 == null || list11.size() == 0){
			 try {
	 				Messagebox.show("�����ݣ���������", "����", Messagebox.OK,Messagebox.INFORMATION);
	 			} catch (InterruptedException e) {
	 				e.printStackTrace();
	 			}
	 			  return;
		}else {
			String fileName = "�̲����.xls";
			String Title = "�̲����";
			WritableWorkbook workbook;
			List titlelist=new ArrayList();
			titlelist.add("���");
			titlelist.add("�̲���");
			titlelist.add("��������");
			titlelist.add("ѧ�Ʒ���");
			titlelist.add("���浥λ");
			titlelist.add("����ʱ�䣨��ʽ��2010/3/9�� ");
			titlelist.add("���");
			titlelist.add("ISBN��");
			titlelist.add("���б༭��");
			titlelist.add("����������� ");
			titlelist.add("�е�����");
			titlelist.add("��ע");
			String c[][]=new String[list11.size()][titlelist.size()];
			//��SQL�ж�����
			for(int j=0;j<list11.size();j++){
				GhZz zz = (GhZz)list11.get(j);
			    c[j][0]=j+1+"";
				c[j][1]=zz.getZzMc();
				if(zz.getZzWorktype() == null || zz.getZzWorktype().equals("")){
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
				if(zz.getZzSubjetype() == null ||zz.getZzSubjetype().equals("")){
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
//			
			   GhJszz jszz=jszzService.findByKuidAndLwidAndType(user.getKuId(), zz.getZzId(), GhJszz.JC);
			    if(jszz!=null&&jszz.getZzSelfs()!=null){
			    	 c[j][9] =jszz.getZzSelfs()+"";
			    }else{
			    	 c[j][9] ="";
			    }
			    if(jszz!=null&&jszz.getZzWords()!=null){
			    	 c[j][10]=jszz.getZzWords();
			    }else{
			   	 c[j][10]="";
			    }
				c[j][11]=zz.getZzRemark();
			}
	         ExportExcel ee=new ExportExcel();
			ee.exportExcel(fileName, Title, titlelist, list11.size(), c);
			
		}
	}
}
