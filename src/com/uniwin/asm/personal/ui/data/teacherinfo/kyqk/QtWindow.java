package com.uniwin.asm.personal.ui.data.teacherinfo.kyqk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.common.util.ExportExcel;
import org.iti.gh.entity.GhFile;
import org.iti.gh.entity.GhQt;
import org.iti.gh.service.CgService;
import org.iti.gh.service.FmzlService;
import org.iti.gh.service.GhFileService;
import org.iti.gh.service.JlhzService;
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
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Toolbarbutton;

import com.uniwin.asm.personal.ui.data.teacherinfo.QtqkWindow;
import com.uniwin.framework.common.fileuploadex.UploadFJ;
import com.uniwin.framework.entity.WkTUser;

public class QtWindow extends BaseWindow {

	//其他
	Toolbarbutton button6;
	Listbox listbox6;

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
	
	JsxmService jsxmService;

	@Override
	public void initShow() {
		initWindow();

		final Desktop desktop=this.getDesktop();
		listbox6.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhQt qt = (GhQt) arg1;

				//序号
				Listcell c1 = new Listcell(arg0.getIndex()+1+"");

				//名称
				Listcell c2 = new Listcell();
				InnerButton ib = new InnerButton();
				ib.setLabel(qt.getQtMc());
				ib.addEventListener(Events.ON_CLICK, new EventListener(){

					public void onEvent(Event arg0) throws Exception {

						final QtqkWindow cgWin = (QtqkWindow) Executions.createComponents
						("/admin/personal/data/teacherinfo/kyqk/qtqk/qtqk.zul", null, null);
						cgWin.setQt(qt);
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

				//时间
				Listcell c3 = new Listcell(qt.getQtSj());

				//备注（证书编号）
				Listcell c4 = new Listcell(qt.getQtBz());

				//功能
				Listcell c5 = new Listcell();
				InnerButton gn = new InnerButton();

				gn.setLabel("删除");
				gn.addEventListener(Events.ON_CLICK, new EventListener(){

					public void onEvent(Event arg0) throws Exception {
						if(Messagebox.show("确定删除吗?", "提示", Messagebox.OK|Messagebox.CANCEL,
								Messagebox.QUESTION)==1){
							//删除附件
							List list=ghfileService.findByFxmIdandFType(qt.getQtId(), 9);
							if(list.size()>0){
								//存在附件
								//List[] fjList=new List[list.size()];
								for(int i=0;i<list.size();i++){
									UploadFJ ufj=new UploadFJ(false);
									ufj.ReadFJ(desktop, (GhFile) list.get(i));
									ufj.DeleteFJ();
								}
							}
							//
							xmService.delete(qt);
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
		//其他
		List list6 = qtService.findByKuid(user.getKuId());
		listbox6.setModel(new ListModelList(list6));


	}
	public void onClick$button6(){
		
		final QtqkWindow cgWin = (QtqkWindow) Executions.createComponents
		("/admin/personal/data/teacherinfo/kyqk/qtqk/qtqk.zul", null, null);
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
	 public void onClick$button6out()  throws IOException, WriteException{
		 	//其他
			List list6 = qtService.findByKuid(user.getKuId());
			 if(list6.size()==0){
				 try {
		 				Messagebox.show("空数据，导出错误！", "错误", Messagebox.OK,Messagebox.INFORMATION);
		 			} catch (InterruptedException e) {
		 				e.printStackTrace();
		 			}
		 			  return;
			}else{
		    	Date now=new Date();
		    	String fileName = "其他.xls";
		    	String Title = "其他";
		    	WritableWorkbook workbook;
				List titlelist=new ArrayList();
				titlelist.add("序号");
				titlelist.add("名称");
				titlelist.add("时间");
				titlelist.add("备注（证书编号）");
				String c[][]=new String[list6.size()][titlelist.size()];
				//从SQL中读数据
				for(int j=0;j<list6.size();j++){
					GhQt qt  =(GhQt)list6.get(j);
				    c[j][0]=j+1+"";
					c[j][1]=qt.getQtMc();
				    c[j][2]=qt.getQtSj();
					c[j][3]=qt.getQtBz();
					
				}
		         ExportExcel ee=new ExportExcel();
				ee.exportExcel(fileName, Title, titlelist, list6.size(), c);
		    	}
		     }
}
