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

	//出版学术专著情况
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

				//序号
				Listcell c1 = new Listcell(arg0.getIndex()+1+"");

				//专著名称
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
			
				//出版单位及ISBN
				Listcell c3 = new Listcell(zz.getZzKw());

				//出版时间
				Listcell c4 = new Listcell(zz.getZzPublitime());
				
				//版次
				Listcell c5 = new Listcell(zz.getZzEditionno());
				Listcell c6 = new Listcell();
				InnerButton ib1 = new InnerButton("查看/编辑");
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
				//功能
				Listcell c7 = new Listcell();
				InnerButton gn = new InnerButton();
				gn.setLabel("删除");
				gn.addEventListener(Events.ON_CLICK, new EventListener(){

					public void onEvent(Event arg0) throws Exception {
						if(zz.getKuId().intValue()!=user.getKuId().intValue()){
							Messagebox.show("您不是此项目提交人，故不能删除！","提示", Messagebox.OK,Messagebox.EXCLAMATION);
						}else{
						if(Messagebox.show("确定删除吗?", "提示", Messagebox.OK|Messagebox.CANCEL,
								Messagebox.QUESTION)==1){
							//删除附件
							List list=ghfileService.findByFxmIdandFType(zz.getZzId(), 5);
							if(list.size()>0){
								//存在附件
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
		             c8.setLabel("未审核");
		        }else if(zz.getAuditState()!=null&&zz.getAuditState().shortValue()==GhZz.AUDIT_NO){
		        	Ib.setLabel("未通过");
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
		        	Ib.setLabel("通过");
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
		//出版科研专著
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
		 	//出版科研专著
			List list4 = zzService.findByKuidAndType(user.getKuId(), GhZz.ZZ,GhJszz.ZZ);
			 if(list4.size()==0){
				 try {
		 				Messagebox.show("空数据，导出错误！", "错误", Messagebox.OK,Messagebox.INFORMATION);
		 			} catch (InterruptedException e) {
		 				e.printStackTrace();
		 			}
		 			  return;
			}else{
		    	Date now=new Date();
		    	String fileName = "出版学术专著.xls";
		    	String Title = "出版学术专著";
		    	WritableWorkbook workbook;
				List titlelist=new ArrayList();
				titlelist.add("序号");
				titlelist.add("著作名称");
				titlelist.add("著作类型");
				titlelist.add("学科分类");
				titlelist.add("出版单位");
				titlelist.add("出版时间（格式：2010/3/9） ");
				titlelist.add("版次");
				titlelist.add("ISBN号");
				titlelist.add("所有编辑人");
				titlelist.add("作者署名");
				titlelist.add("承担字数");
				titlelist.add("备注");
				String c[][]=new String[list4.size()][titlelist.size()];
				//从SQL中读数据
				for(int j=0;j<list4.size();j++){
					GhZz zz = (GhZz)list4.get(j);
				    c[j][0]=j+1+"";
					c[j][1]=zz.getZzMc();
					if(zz.getZzWorktype() == null ||zz.getZzWorktype().equals("") || zz.getZzWorktype().trim().equalsIgnoreCase("-1")){
						c[j][2]="";	
					}else if(zz.getZzWorktype().equalsIgnoreCase("1")){
						c[j][2]="独著";
					}else if(zz.getZzWorktype().equalsIgnoreCase("2")){
						c[j][2]="合著";
					}else if(zz.getZzWorktype().equalsIgnoreCase("3")){
						c[j][2]="专著";
					}else if(zz.getZzWorktype().equalsIgnoreCase("4")){
						c[j][2]="编著";
					}else if(zz.getZzWorktype().equalsIgnoreCase("5")){
						c[j][2]="教材";
					}else if(zz.getZzWorktype().equalsIgnoreCase("6")){
						c[j][2]="参考书或工具书";
					}else if(zz.getZzWorktype().equalsIgnoreCase("7")){
						c[j][2]="论文集";
					}else if(zz.getZzWorktype().equalsIgnoreCase("8")){
						c[j][2]="译著";
					}else if(zz.getZzWorktype().equalsIgnoreCase("9")){
						c[j][2]="调查报告";
					}
					//学科分类
					if(zz.getZzSubjetype() == null || zz.getZzSubjetype().equals("") || zz.getZzSubjetype().trim().equalsIgnoreCase("-1")){
						c[j][3]="";
					}else if(zz.getZzSubjetype().equalsIgnoreCase( "1")){
						c[j][3]="自然科学";
					}else if(zz.getZzSubjetype().equalsIgnoreCase( "2")){
						c[j][3]="社会科学";
					}else if(zz.getZzSubjetype().equalsIgnoreCase( "3")){
						c[j][3]="其他";
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
