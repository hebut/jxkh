package com.uniwin.asm.personal.ui.data.teacherinfo.kyqk;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.log4j.Logger;
import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.common.util.ExportExcel;
import org.iti.gh.entity.GH_PHASEREPORT;
import org.iti.gh.entity.GH_PROMEMBER;
import org.iti.gh.entity.GhFile;
import org.iti.gh.entity.GhJsxm;
import org.iti.gh.entity.GhXm;
import org.iti.gh.entity.GhXmzz;
import org.iti.gh.service.CgService;
import org.iti.gh.service.GH_PHASEREPORTService;
import org.iti.gh.service.GhFileService;
import org.iti.gh.service.JsxmService;
import org.iti.gh.service.XmService;
import org.iti.gh.service.XmzzService;
import org.iti.gh.service.YjfxService;
import org.iti.projectmanage.science.member.service.ProjectMemberService;
import org.iti.projectmanage.science.project.KyHProWindow;
import org.iti.projectmanage.science.project.KycgYhdWindow;
import org.iti.xypt.ui.base.BaseWindow;
import org.mvel.optimizers.impl.refl.Fold;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
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
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.uniwin.asm.personal.ui.data.teacherinfo.CkshyjWindow;
import com.uniwin.framework.common.fileuploadex.UploadFJ;
import com.uniwin.framework.entity.WkTUser;

public class KyxmWindow extends BaseWindow {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8887043916596977149L;
	private Logger  log  = Logger.getLogger(KyxmWindow.class);

	//	/*�������*/�ѻ�õĿ��гɹ������ջ������/Ŀǰ�е�����Ŀ���
	Toolbarbutton button1;//��Ӱ�ť
	Listbox zxListbox,hxxmListbox;
	Toolbarbutton submit1add,reset1add,cancel1add;	
	//�񽱿��гɹ�
	Button button2;//��Ӱ�ť
	
	//��������������
	Button button3;
	Listbox listbox3;
	
	//�����ڿ��������
	Button button31;
	Listbox listbox31;
	
	//����ѧ��ר�����
	Button button4;
	Listbox listbox4;	
	
	//����Ȩ����ר�����
	Button button5;
	Listbox listbox5;	
	
	//����
	Button button6;
	Listbox listbox6;	
	
/*	//Ŀǰ�е�����Ŀ���
	Button button7;
	Listbox listbox7;*/
	
	//�������
	Button button16;
	Listbox listbox16;
	
	//δ��������мƻ�
	Grid grid7;
	Textbox yjnr,jjwt,yqcg;
	XmService xmService;
	XmzzService xmzzService;
	CgService cgService;
	YjfxService yjfxService;
	GhFileService ghfileService;
	GH_PHASEREPORTService gh_phasereportService;
	ProjectMemberService projectmemberService;
	WkTUser user;	
	JsxmService jsxmService;

	private Paging zxPaging,hxPaging;
	private int   zxRowNum = 0;
	List zxProlist= new ArrayList(); //����������Ŀ��ѯ�Ľ����
	List hxProlist=new ArrayList();  //���غ�����Ŀ��ѯ�Ľ����
	/**
	 * ���ܣ���ʼ������
	 * @Data  2011-5-18
	 * @param null
	 * @author bobo
	 */
	public void initShow() {
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		initWindow();
		initHxProWindow();
		
		zxProlist = xmService.findCountByKuidAndTypeAndKyclass(user.getKuId(), GhJsxm.KYXM,"2");
		int totalZxSize = 0;
        if (zxProlist.size() > 0){            	
        	totalZxSize = ((Long) zxProlist.get(0)).intValue();
        }
		zxPaging.setTotalSize(totalZxSize);	
		zxPaging.addEventListener("onPaging", new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				List<GhXm> xm1list =xmService.getListPageByKuidAndTypeAndKyclass(user.getKuId(), GhJsxm.KYXM,"2",zxPaging.getActivePage(), zxPaging.getPageSize());			
				zxListbox.setModel(new ListModelList(xm1list));				
			}
		});
		
		hxProlist = xmService.findCountByKuidAndTypeAndKyclass(user.getKuId(), GhJsxm.KYXM,"1");
		int totalHxSize = 0;
        if (hxProlist.size() > 0){            	
        	totalHxSize = ((Long) hxProlist.get(0)).intValue();
        }
		hxPaging.setTotalSize(totalHxSize);		
		zxPaging.addEventListener("onPaging", new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				List<GhXm> xm2list =xmService.getListPageByKuidAndTypeAndKyclass(user.getKuId(), GhJsxm.KYXM,"1",zxPaging.getActivePage(), zxPaging.getPageSize());					
				hxxmListbox.setModel(new ListModelList(xm2list));			
			}
		});
		
		zxListbox.setItemRenderer(new ListitemRenderer(){
			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhXm xm = (GhXm) arg1;
				zxRowNum = zxPaging.getActivePage() * zxPaging.getPageSize() + arg0.getIndex() + 1;
				Listcell c1 = new Listcell(zxRowNum+"");
				InnerButton xmc;
				//��Ŀ����
				Listcell c2 = new Listcell();
				if(xm.getKyMc()!=null){
					if(xm.getKyMc().trim().length()>12){
						String mc=xm.getKyMc().substring(0, 12);
						xmc= new InnerButton(mc+"...");
					}
					else{
						xmc = new InnerButton(xm.getKyMc());
					}
					xmc.addEventListener(Events.ON_CLICK, new EventListener(){
						public void onEvent(Event arg0) throws Exception {
							Map arg=new HashMap();
							arg.put("zxxm", xm);
							arg.put("useKuid",user.getKuId());
							final KycgYhdWindow cgWin = (KycgYhdWindow) Executions.createComponents
							("/admin/personal/data/teacherinfo/kyqk/kyxm/kycgyhd.zul", null, arg);							
							cgWin.initWindow();
							cgWin.doModal();
							cgWin.addEventListener(Events.ON_CHANGE, new EventListener(){
								public void onEvent(Event arg0)
								throws Exception {
									initWindow();
									cgWin.initWindow();
									cgWin.detach();
								}
							});
						}
					});
					c2.appendChild(xmc);
					c2.setTooltiptext(xm.getKyMc());
				}
				//��Ŀ��Դ
				Listcell c3 = new Listcell();
				if(xm.getKyLy()!=null){
					if(xm.getKyLy().trim().length()>8){
						c3.setLabel(xm.getKyLy().substring(0, 8)+"...");
						c3.setTooltiptext(xm.getKyLy());
					}
					else{
					c3.setLabel(xm.getKyLy());
					c3.setTooltiptext(xm.getKyLy());
					}
				}
				//������
				Listcell c4 = new Listcell();
				if(xm.getKyProman()!=null){
					c4.setLabel(xm.getKyProman()+"");
				}
				//���˹���
				Listcell c5 = new Listcell();
				List manager= projectmemberService.findByKyIdAndKuId(xm.getKyId(),user.getKuId());
				if(manager.size()!=0)
				{
					GhJsxm man=(GhJsxm) manager.get(0);
					c5.setLabel(man.getKyGx().toString());
				}
				else
				{
					c5.setLabel("1");
				}
//				List manager= projectmemberService.findByKyIdAndKuId(xm.getKyId(),user.getKuLid());
//				if(manager.size()!=0){
//					GH_PROMEMBER man=(GH_PROMEMBER) manager.get(0);
//					c5.setLabel(man.getContribBank().toString());
//				}
//				else{
//					c5.setLabel("1");
//				}		
				
				Listcell c6=new Listcell();
				InnerButton ib = new InnerButton();
				if(xm.getAuditState()==null){
					c6.setLabel("δ���");
				}else if(xm.getAuditState()!=null&&xm.getAuditState().shortValue()==GhXm.AUDIT_NO){
					ib.setLabel("δͨ��");
					c6.appendChild(ib);
					ib.addEventListener(Events.ON_CLICK, new EventListener(){
						public void onEvent(Event arg0) throws Exception {
						 Map arg=new HashMap();
						 arg.put("type", 1);
						 arg.put("id",xm.getKyId());
						 CkshyjWindow cw=(CkshyjWindow)Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/ckpsyj.zul", null, arg);
						 cw.initWindow();
						 cw.doHighlighted();
						}						
					});
				}else if(xm.getAuditState()!=null&&xm.getAuditState().shortValue()==GhXm.AUDIT_YES){
					ib.setLabel("ͨ��");
					c6.appendChild(ib);
					ib.addEventListener(Events.ON_CLICK, new EventListener(){
						public void onEvent(Event arg0) throws Exception {
						 Map arg=new HashMap();
						 arg.put("type", 1);
						 arg.put("id",xm.getKyId());
						 CkshyjWindow cw=(CkshyjWindow)Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/ckpsyj.zul", null, arg);
						 cw.initWindow();
						 cw.doHighlighted();
						}						
					});
				}
				//����
				Listcell c7 =  new Listcell();
				InnerButton gn = new InnerButton();
				gn.setImage("/css/sat/actDel.gif");
				gn.addEventListener(Events.ON_CLICK, new EventListener(){
					public void onEvent(Event arg0) throws Exception {
						if(xm.getKuId().intValue()!=user.getKuId().intValue()){
							Messagebox.show("�����Ǵ���Ŀ�ύ�ˣ��ʲ���ɾ����","��ʾ", Messagebox.OK,Messagebox.EXCLAMATION);
						}else{
						if(Messagebox.show("ȷ��ɾ����?", "��ʾ", Messagebox.OK|Messagebox.CANCEL,
								Messagebox.QUESTION)==1){
							List xmzzlist =	xmzzService.findByKyidAndKuidAndType(xm.getKyId(), user.getKuId(),GhJsxm.KYXM.shortValue());
							if(xmzzlist != null && xmzzlist.size() !=0){
								for(int i = 0;i < xmzzlist.size();i++){
									GhXmzz xmzz = (GhXmzz) xmzzlist.get(i);
									xmzzService.delete(xmzz);
								}
							}
							List cglist =jsxmService.findByXmidAndtype(xm.getKyId(), GhJsxm.KYXM);
							if(cglist != null && cglist.size() != 0){
								for(int i=0;i<cglist.size();i++){
									GhJsxm jsxm=(GhJsxm)cglist.get(i);
									jsxmService.delete(jsxm);
								}
							}
							//ɾ������
							List list=ghfileService.findByFxmIdandFType(xm.getKyId(),1);
							if(list.size()>0){
								//���ڸ���
								for(int i=0;i<list.size();i++){
									UploadFJ ufj=new UploadFJ(false);
									ufj.ReadFJ(getDesktop(), (GhFile) list.get(i));
									ufj.DeleteFJ();
								}
							}
							xmService.delete(xm);
							/**
							 * ����ɾ������Ŀ���г�Ա�Ľ׶α����¼
							 */							
							List reportList = gh_phasereportService.findByKyxmId(xm.getKyId());
							if(reportList.size()!=0) {
								for(int i=0;i<reportList.size();i++) {
									GH_PHASEREPORT phaseReport = (GH_PHASEREPORT)reportList.get(i);
									//ɾ�����ݿ��и���Ŀ�����г�Ա�׶α����¼
									gh_phasereportService.delete(phaseReport);
									//ɾ�������������ϴ������ڸ���Ŀ�����н׶α���
									String archivePath =Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/report")
									+ File.separator+xm.getKyId() + File.separator + phaseReport.getPhRepoPath();
									File archiveFile = new File(archivePath);						
									boolean isDel = false;
									if(archiveFile.exists())
									{
										isDel = archiveFile.delete();
									}else{
										isDel = true;
									}
								}
							}
							/**
							 * 
							 */
							initWindow();
						}
						}
					}
				});
				c7.appendChild(gn);
				arg0.appendChild(c1); arg0.appendChild(c2); arg0.appendChild(c3);
				arg0.appendChild(c4); 
				arg0.appendChild(c5); arg0.appendChild(c6); arg0.appendChild(c7); 
			}
		});

		//������Ŀ�б�
		hxxmListbox.setItemRenderer(new ListitemRenderer(){
			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhXm xm = (GhXm) arg1;
				Listcell c1 = new Listcell(arg0.getIndex()+1+"");
				InnerButton xmc;
				Listcell c2 = new Listcell();
				if(xm.getKyMc()!=null){
					if(xm.getKyMc().trim().length()>12){
						String mc=xm.getKyMc().substring(0, 12);
						xmc= new InnerButton(mc+"...");
					}
					else{
						xmc = new InnerButton(xm.getKyMc());
					}
					xmc.addEventListener(Events.ON_CLICK, new EventListener(){
						public void onEvent(Event arg0) throws Exception {
							Map arg=new HashMap();
							arg.put("hxxm", xm);
							arg.put("useKuid",user.getKuId());
							final KyHProWindow cgWin = (KyHProWindow) Executions.createComponents
											("/admin/personal/data/teacherinfo/kyqk/kyxm/hengxiang.zul", null, arg);							
							cgWin.initWindow();
							cgWin.doModal();
							cgWin.addEventListener(Events.ON_CHANGE, new EventListener(){
								public void onEvent(Event arg0)
								throws Exception {
									initHxProWindow();
									cgWin.initWindow();
									cgWin.detach();
								}
							});
						}
					});
					
					c2.appendChild(xmc);
					c2.setTooltiptext(xm.getKyMc());
				}
				//��Ŀ��Դ
				Listcell c3 = new Listcell();
				if(xm.getKyLy()!=null){
					if(xm.getKyLy().trim().length()>8){
						c3.setLabel(xm.getKyLy().substring(0, 8)+"...");
						c3.setTooltiptext(xm.getKyLy());
					}
					else{
					c3.setLabel(xm.getKyLy());
					c3.setTooltiptext(xm.getKyLy());
					}
				}
				//������
				Listcell c4 = new Listcell();
				if(xm.getKyProman()!=null){
					c4.setLabel(xm.getKyProman()+"");
				}
				//���˹���
				Listcell c5 = new Listcell();
				GhJsxm jsxm=jsxmService.findByXmidAndKuidAndType(xm.getKyId(),user.getKuId(),GhJsxm.KYXM);
				if(jsxm!=null&&jsxm.getKyGx()!=null){
					c5.setLabel(jsxm.getKyGx()+"");
				}
		
								
				Listcell c6=new Listcell();
				InnerButton ib = new InnerButton();
				if(xm.getAuditState()==null){
					c6.setLabel("δ���");
				}else if(xm.getAuditState()!=null&&xm.getAuditState().shortValue()==GhXm.AUDIT_NO){
					ib.setLabel("δͨ��");
					c6.appendChild(ib);
					ib.addEventListener(Events.ON_CLICK, new EventListener(){
						public void onEvent(Event arg0) throws Exception {
						 Map arg=new HashMap();
						 arg.put("type", 1);
						 arg.put("id",xm.getKyId());
						 CkshyjWindow cw=(CkshyjWindow)Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/ckpsyj.zul", null, arg);
						 cw.initWindow();
						 cw.doHighlighted();
						}
						
					});
				}else if(xm.getAuditState()!=null&&xm.getAuditState().shortValue()==GhXm.AUDIT_YES){
					ib.setLabel("ͨ��");
					c6.appendChild(ib);
					ib.addEventListener(Events.ON_CLICK, new EventListener(){
						public void onEvent(Event arg0) throws Exception {
						 Map arg=new HashMap();
						 arg.put("type", 1);
						 arg.put("id",xm.getKyId());
						 CkshyjWindow cw=(CkshyjWindow)Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/ckpsyj.zul", null, arg);
						 cw.initWindow();
						 cw.doHighlighted();
						}
					});
				}
				
				//����
				Listcell c7 =  new Listcell();
				InnerButton gn = new InnerButton();
				gn.setImage("/css/sat/actDel.gif");
				gn.addEventListener(Events.ON_CLICK, new EventListener(){
					public void onEvent(Event arg0) throws Exception {
						if(xm.getKuId().intValue()!=user.getKuId().intValue()){
							Messagebox.show("�����Ǵ���Ŀ�ύ�ˣ��ʲ���ɾ����","��ʾ", Messagebox.OK,Messagebox.EXCLAMATION);
						}else{
						if(Messagebox.show("ȷ��ɾ����?", "��ʾ", Messagebox.OK|Messagebox.CANCEL,
								Messagebox.QUESTION)==1){
							List xmzzlist =	xmzzService.findByKyidAndKuidAndType(xm.getKyId(), user.getKuId(),GhJsxm.KYXM.shortValue());
							if(xmzzlist != null && xmzzlist.size() !=0){
								for(int i = 0;i < xmzzlist.size();i++){
									GhXmzz xmzz = (GhXmzz) xmzzlist.get(i);
									xmzzService.delete(xmzz);
								}
							}
							List cglist =jsxmService.findByXmidAndtype(xm.getKyId(), GhJsxm.KYXM);
							if(cglist != null && cglist.size() != 0){
								for(int i=0;i<cglist.size();i++){
									GhJsxm jsxm=(GhJsxm)cglist.get(i);
									jsxmService.delete(jsxm);
								}
							}
							//ɾ������
							List list=ghfileService.findByFxmIdandFType(xm.getKyId(),1);
							if(list.size()>0){
								//���ڸ���
								for(int i=0;i<list.size();i++){
									UploadFJ ufj=new UploadFJ(false);
									ufj.ReadFJ(getDesktop(), (GhFile) list.get(i));
									ufj.DeleteFJ();
								}
							}
							xmService.delete(xm);
							/**
							 * ����ɾ������Ŀ���г�Ա�Ľ׶α����¼
							 */							
							List reportList = gh_phasereportService.findByKyxmId(xm.getKyId());
							if(reportList.size()!=0) {
								for(int i=0;i<reportList.size();i++) {
									GH_PHASEREPORT phaseReport = (GH_PHASEREPORT)reportList.get(i);
									//ɾ�����ݿ��и���Ŀ�����г�Ա�׶α����¼
									gh_phasereportService.delete(phaseReport);
									//ɾ�������������ϴ������ڸ���Ŀ�����н׶α���
									String archivePath =Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/report")
									+ File.separator+xm.getKyId() + File.separator + phaseReport.getPhRepoPath();
									File archiveFile = new File(archivePath);						
									boolean isDel = false;
									if(archiveFile.exists())
									{
										isDel = archiveFile.delete();
									}else{
										isDel = true;
									}
								}
							}
							/**
							 * 
							 */
							initHxProWindow();
						}
						}
					}
				});
				c7.appendChild(gn);
				arg0.appendChild(c1); arg0.appendChild(c2); arg0.appendChild(c3);
				arg0.appendChild(c4); 
				arg0.appendChild(c5); arg0.appendChild(c6); arg0.appendChild(c7); 
			}
		});
	}
	/**
	 * ���ܣ���ѯ������Ŀ�б�
	 * @Data  2011-5-18
	 * @param null
	 * @author bobo
	 */
	public void initWindow() {
		List<GhXm> zxlist = xmService.getListPageByKuidAndTypeAndKyclass(user.getKuId(), GhJsxm.KYXM,"2",zxPaging.getActivePage(), zxPaging.getPageSize());			
		zxListbox.setModel(new ListModelList(zxlist));
	}
	
	/**
	 * ���ܣ���ѯ������Ŀ�б�
	 * @Data  2011-5-18
	 * @param null
	 * @author bobo
	 */
	public void initHxProWindow() {
		List hxProlist = xmService.getListPageByKuidAndTypeAndKyclass(user.getKuId(), GhJsxm.KYXM,"1",zxPaging.getActivePage(), zxPaging.getPageSize());			
		hxxmListbox.setModel(new ListModelList(hxProlist));
	}
	
	/**
	 * ���ܣ����������Ŀ
	 * @Data  2011-5-18
	 * @param null
	 * @author bobo
	 */
	public void onClick$button1(){
		Map arg=new HashMap();
		arg.put("useKuid",user.getKuId());
		final KycgYhdWindow cgWin = (KycgYhdWindow) Executions.createComponents
									("/admin/personal/data/teacherinfo/kyqk/kyxm/kycgyhd.zul", null, arg);
		cgWin.initWindow();
		try {
			cgWin.doModal();
		} catch (SuspendNotAllowedException e) {
			log.error("��ǰϵͳʱ�䣺"+ new Date().toLocaleString()
                    + " "+ "���������Ŀ����"+ e.getMessage());
			e.printStackTrace();
		} catch (InterruptedException e) {
			log.error("��ǰϵͳʱ�䣺"+ new Date().toLocaleString()
                    + " "+ "���������Ŀ����"+ e.getMessage());
			e.printStackTrace();
		}
		cgWin.addEventListener(Events.ON_CHANGE, new EventListener(){
			public void onEvent(Event arg0)
			throws Exception {
				initWindow();
				cgWin.detach();
			}
		});
		initShow();
	}	
	/**
	 * ���ܣ���Ӻ�����Ŀ
	 * @Data  2011-5-18
	 * @param null
	 * @author bobo
	 */
	public void onClick$button2(){
		Map arg=new HashMap();
		arg.put("useKuid",user.getKuId());
		final KyHProWindow cgWin = (KyHProWindow) Executions.createComponents
									("/admin/personal/data/teacherinfo/kyqk/kyxm/hengxiang.zul", null, arg);
		cgWin.initWindow();		
		try {
			cgWin.doModal();
		} catch (SuspendNotAllowedException e) {
			log.error("��ǰϵͳʱ�䣺"+ new Date().toLocaleString()
                    + " "+ "��Ӻ�����Ŀ����"+ e.getMessage());
			e.printStackTrace();
		} catch (InterruptedException e) {
			log.error("��ǰϵͳʱ�䣺"+ new Date().toLocaleString()
                    + " "+ "��Ӻ�����Ŀ����"+ e.getMessage());
			e.printStackTrace();
		}
		cgWin.addEventListener(Events.ON_CHANGE, new EventListener(){
			public void onEvent(Event arg0)
			throws Exception {
				initHxProWindow();
				cgWin.detach();
			}
		});
		initShow();
	}
	
	/**
	 * ������Ŀ��Ϣ ����
	 * @throws IOException
	 * @throws WriteException
	 */
	 public void onClick$button1out()  throws IOException, WriteException{
			//���гɹ������ջ������
			List list1 = xmService.findByKuidAndTypeAndKyclass(user.getKuId(), GhJsxm.KYXM,"2");
			 if(list1.size()==0){
				 try {
		 				Messagebox.show("�����ݣ���������", "����", Messagebox.OK,Messagebox.INFORMATION);
		 			} catch (InterruptedException e) {
		 				e.printStackTrace();
		 			}
		 			  return;
			}else{
		    	Date now=new Date();
		    	String fileName = "���������Ŀ.xls";
		    	String Title = "���������Ŀ";
		    	WritableWorkbook workbook;
				List titlelist=new ArrayList();
				titlelist.add("���");
				titlelist.add("��Ŀ���");
				titlelist.add("��Ŀ����");
				titlelist.add("��Ŀ��Դ");
				titlelist.add("��ʼʱ��");
				titlelist.add("����ʱ��");
				titlelist.add("��Ŀ���ѣ���Ԫ��");
				titlelist.add("���˹�������");
				titlelist.add("�о�����");
				titlelist.add("ѧ�Ʒ���");
				titlelist.add("��Ŀ������");
				titlelist.add("��Ŀ����Ա");
				titlelist.add("�걨��Ա");
				titlelist.add("��Ŀ���");
				titlelist.add("��Ŀ����");
				titlelist.add("��Ŀ��չ");
				titlelist.add("��Ŀָ��");
				titlelist.add("��Ŀ���������գ�ʱ��");
				titlelist.add("����ˮƽ");
//				titlelist.add("�Ƿ��");
				titlelist.add("���˳е����������");
				titlelist.add("��Ŀ�ڲ����");
				String c[][]=new String[list1.size()][titlelist.size()];
				//��SQL�ж�����
				for(int j=0;j<list1.size();j++){
					GhXm xm  =(GhXm)list1.get(j);
				    c[j][0]=j+1+"";
				    c[j][1]=xm.getKyNumber();
					c[j][2]=xm.getKyMc();
				    c[j][3]=xm.getKyLy();
					c[j][4]=xm.getKyKssj();
					c[j][5]=xm.getKyJssj();
					c[j][6]=xm.getKyJf()!=null?xm.getKyJf().toString():"";
					GhJsxm jsxm=jsxmService.findByXmidAndKuidAndType(xm.getKyId(),user.getKuId(),GhJsxm.KYXM);
					if(jsxm!=null&&jsxm.getKyGx()!=null){
						c[j][7]=jsxm.getKyGx()+"";						
					}else{
						c[j][7]="";
					}
					if(jsxm!=null&&jsxm.getYjId()!=null&&jsxm.getYjfx()!=null){
						c[j][8]=jsxm.getYjfx().getGyName();
					}else{
						c[j][8]="";
					}
					//ѧ�Ʒ���
					if(xm.getKySubjetype() == null ||xm.getKySubjetype().equals("") || xm.getKySubjetype().trim().equalsIgnoreCase("-1")){
						c[j][9]="";
					}else if(xm.getKySubjetype().equalsIgnoreCase( "1")){
						c[j][9]="��Ȼ��ѧ";
					}else if(xm.getKySubjetype().equalsIgnoreCase( "2")){
						c[j][9]="����ѧ";
					}else if(xm.getKySubjetype().equalsIgnoreCase( "3")){
						c[j][9]="����";
					}					
					c[j][10]=xm.getKyProman();
					c[j][11]=xm.getKyProstaffs();
					c[j][12]=xm.getKyRegister();
					//��Ŀ���
					if(xm.getKyClass() == null ||xm.getKyClass().equals("") || xm.getKyClass().trim().equalsIgnoreCase("-1")){
						c[j][13]="";
					}else {if(xm.getKyClass().trim().equalsIgnoreCase( "1")){
						c[j][13]="����";
					}else if(xm.getKyClass().trim().equalsIgnoreCase( "2")){
						c[j][13]="����";
					}}
					//��Ŀ����
					if(xm.getKyScale() == null || xm.getKyScale().equals("") || xm.getKyScale().trim() == "-1"){
						c[j][14]="";
					}else{ if(xm.getKyScale().trim().equalsIgnoreCase( "1")){
						c[j][14]="���ʺ�����Ŀ";
					}else if(xm.getKyScale().trim().equalsIgnoreCase( "2")){
						c[j][14]="���Ҽ���Ŀ";
					}else if(xm.getKyScale().trim().equalsIgnoreCase( "3")){
						c[j][14]="����ί��ʡ������Ŀ";
					}else if(xm.getKyScale().trim().equalsIgnoreCase( "4")){
						c[j][14]="��������Ŀ";
					}else if(xm.getKyScale().trim().equalsIgnoreCase( "5")){
						c[j][14]="ί�м�������Ŀ";
					}else if(xm.getKyScale().trim().equalsIgnoreCase( "6")){
						c[j][14]="ѧУ������Ŀ";
					}else {
						c[j][14]="����";
					}}
					//��Ŀ��չ
					if(xm.getKyProgress() == null ||xm.getKyProgress().equals("") || xm.getKyProgress().trim() ==""){
						c[j][15]="";
					}else if(xm.getKyProgress().trim().equalsIgnoreCase( "1")){
						c[j][15]="������";
					}else if(xm.getKyProgress().trim().equalsIgnoreCase( "2")){
						c[j][15]="�����";
					}else if(xm.getKyProgress().trim().equalsIgnoreCase( "3")){
						c[j][15]="����";
					}					
					c[j][16]=xm.getKyTarget();
					c[j][17]=xm.getKyIdenttime();
					c[j][18]=xm.getKyLevel();
					
//					//��Ŀ��
//					if(xm.getKyPrize() == null || xm.getKyPrize().trim() == "" ||xm.getKyPrize().trim().equalsIgnoreCase( "1")){
//							c[j][19]="��";
//
//					}else if(xm.getKyPrize().trim().equalsIgnoreCase( "2")){
//						c[j][19]="��";
//					}
					//���˳е����������
					if(jsxm.getKyCdrw() == null || jsxm.getKyCdrw().equals("") || jsxm.getKyCdrw().trim().equalsIgnoreCase("-1")){
						c[j][19]="";
					}else if(jsxm.getKyCdrw().trim().equalsIgnoreCase( "1")){
						c[j][19]="����";
					}else if(jsxm.getKyCdrw().trim().equalsIgnoreCase( "2")){
						c[j][19]="�μ�";
					}else if(jsxm.getKyCdrw().trim().equalsIgnoreCase( "3")){
						c[j][19]="����";
					}	
					if(xm.getInternalNum()==null||xm.getInternalNum().equals("")){
						c[j][20]="";
					}else{
						c[j][20]=xm.getInternalNum().toString();
					}					
				}
		         ExportExcel ee=new ExportExcel();
				ee.exportExcel(fileName, Title, titlelist, list1.size(), c);
		    	}
		     }
	 
	 /**
		 * ������Ŀ��Ϣ ����
		 * @throws IOException
		 * @throws WriteException
		 */
		 public void onClick$button2out()  throws IOException, WriteException{
				List list1 = xmService.findByKuidAndTypeAndKyclass(user.getKuId(), GhJsxm.KYXM,"1");
				 if(list1.size()==0){
					 try {
			 				Messagebox.show("�����ݣ���������", "����", Messagebox.OK,Messagebox.INFORMATION);
			 			} catch (InterruptedException e) {
			 				e.printStackTrace();
			 			}
			 			  return;
				}else{
			    	Date now=new Date();
			    	String fileName = "���������Ŀ.xls";
			    	String Title = "���������Ŀ";
			    	WritableWorkbook workbook;
					List titlelist=new ArrayList();
					titlelist.add("���");
					titlelist.add("��Ŀ���");
					titlelist.add("��Ŀ����");
					titlelist.add("��Ŀ��Դ");
					titlelist.add("��ʼʱ��");
					titlelist.add("����ʱ��");
					titlelist.add("��Ŀ���ѣ���Ԫ��");
					titlelist.add("���˹�������");
					titlelist.add("�о�����");
					titlelist.add("ѧ�Ʒ���");
					titlelist.add("��Ŀ������");
					titlelist.add("��Ŀ����Ա");
					titlelist.add("�걨��Ա");
					titlelist.add("��Ŀ���");
					titlelist.add("��Ŀ����");
					titlelist.add("��Ŀ��չ");
					titlelist.add("��Ŀָ��");
					titlelist.add("��Ŀ���������գ�ʱ��");
					titlelist.add("����ˮƽ");
//					titlelist.add("�Ƿ��");
					titlelist.add("���˳е����������");
					titlelist.add("��Ŀ�ڲ����");
					String c[][]=new String[list1.size()][titlelist.size()];
					//��SQL�ж�����
					for(int j=0;j<list1.size();j++){
						GhXm xm  =(GhXm)list1.get(j);
					    c[j][0]=j+1+"";
					    c[j][1]=xm.getKyNumber();
						c[j][2]=xm.getKyMc();
					    c[j][3]=xm.getKyLy();
						c[j][4]=xm.getKyKssj();
						c[j][5]=xm.getKyJssj();
						c[j][6]=xm.getKyJf()!=null?xm.getKyJf().toString():"";
						GhJsxm jsxm=jsxmService.findByXmidAndKuidAndType(xm.getKyId(),user.getKuId(),GhJsxm.KYXM);
						if(jsxm!=null&&jsxm.getKyGx()!=null){
							c[j][7]=jsxm.getKyGx()+"";
							
						}else{
							c[j][7]="";
						}
						if(jsxm!=null&&jsxm.getYjId()!=null&&jsxm.getYjfx()!=null){
							c[j][8]=jsxm.getYjfx().getGyName();
						}else{
							c[j][8]="";
						}
						//ѧ�Ʒ���
						if(xm.getKySubjetype() == null ||xm.getKySubjetype().equals("") || xm.getKySubjetype().trim().equalsIgnoreCase("-1")){
							c[j][9]="";
						}else if(xm.getKySubjetype().equalsIgnoreCase( "1")){
							c[j][9]="��Ȼ��ѧ";
						}else if(xm.getKySubjetype().equalsIgnoreCase( "2")){
							c[j][9]="����ѧ";
						}else if(xm.getKySubjetype().equalsIgnoreCase( "3")){
							c[j][9]="����";
						}
						
						c[j][10]=xm.getKyProman();
						c[j][11]=xm.getKyProstaffs();
						c[j][12]=xm.getKyRegister();
						//��Ŀ���
						if(xm.getKyClass() == null ||xm.getKyClass().equals("") || xm.getKyClass().trim().equalsIgnoreCase("-1")){
							c[j][13]="";
						}else {if(xm.getKyClass().trim().equalsIgnoreCase( "1")){
							c[j][13]="����";
						}else if(xm.getKyClass().trim().equalsIgnoreCase( "2")){
							c[j][13]="����";
						}}
						
						//��Ŀ����
						if(xm.getKyScale() == null || xm.getKyScale().equals("") || xm.getKyScale().trim() == "-1"){
							c[j][14]="";
						}else{ if(xm.getKyScale().trim().equalsIgnoreCase( "1")){
							c[j][14]="���ʺ�����Ŀ";
						}else if(xm.getKyScale().trim().equalsIgnoreCase( "2")){
							c[j][14]="���Ҽ���Ŀ";
						}else if(xm.getKyScale().trim().equalsIgnoreCase( "3")){
							c[j][14]="����ί��ʡ������Ŀ";
						}else if(xm.getKyScale().trim().equalsIgnoreCase( "4")){
							c[j][14]="��������Ŀ";
						}else if(xm.getKyScale().trim().equalsIgnoreCase( "5")){
							c[j][14]="ί�м�������Ŀ";
						}else if(xm.getKyScale().trim().equalsIgnoreCase( "6")){
							c[j][14]="ѧУ������Ŀ";
						}else {
							c[j][14]="����";
						}}
						//��Ŀ��չ
						if(xm.getKyProgress() == null ||xm.getKyProgress().equals("") || xm.getKyProgress().trim() ==""){
							c[j][15]="";
						}else if(xm.getKyProgress().trim().equalsIgnoreCase( "1")){
							c[j][15]="������";
						}else if(xm.getKyProgress().trim().equalsIgnoreCase( "2")){
							c[j][15]="�����";
						}else if(xm.getKyProgress().trim().equalsIgnoreCase( "3")){
							c[j][15]="����";
						}
						
						c[j][16]=xm.getKyTarget();
						c[j][17]=xm.getKyIdenttime();
						c[j][18]=xm.getKyLevel();
						
//						//��Ŀ��
//						if(xm.getKyPrize() == null || xm.getKyPrize().trim() == "" ||xm.getKyPrize().trim().equalsIgnoreCase( "1")){
//								c[j][19]="��";
	//
//						}else if(xm.getKyPrize().trim().equalsIgnoreCase( "2")){
//							c[j][19]="��";
//						}
						//���˳е����������
						if(jsxm.getKyCdrw() == null || jsxm.getKyCdrw().equals("") || jsxm.getKyCdrw().trim().equalsIgnoreCase("-1")){
							c[j][19]="";
						}else if(jsxm.getKyCdrw().trim().equalsIgnoreCase( "1")){
							c[j][19]="����";
						}else if(jsxm.getKyCdrw().trim().equalsIgnoreCase( "2")){
							c[j][19]="�μ�";
						}else if(jsxm.getKyCdrw().trim().equalsIgnoreCase( "3")){
							c[j][19]="����";
						}
						//��Ŀ�ڲ����
						if(xm.getInternalNum()==null||xm.getInternalNum().equals("")){
							c[j][20]="";
						}else{
							c[j][20]=xm.getInternalNum().toString();
						}	
						
					}
			         ExportExcel ee=new ExportExcel();
					ee.exportExcel(fileName, Title, titlelist, list1.size(), c);
			    	}
			     }
}
