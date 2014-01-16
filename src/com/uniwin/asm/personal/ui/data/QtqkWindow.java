package com.uniwin.asm.personal.ui.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.common.util.ExportExcel;
import org.iti.gh.common.util.MessageBoxshow;
import org.iti.gh.entity.GhSgcf;
import org.iti.gh.entity.GhShjz;
import org.iti.gh.service.CgService;
import org.iti.gh.service.FmzlService;
import org.iti.gh.service.JlhzService;
import org.iti.gh.service.JxbgService;
import org.iti.gh.service.KyjhService;
import org.iti.gh.service.LwzlService;
import org.iti.gh.service.PxService;
import org.iti.gh.service.QkdcService;
import org.iti.gh.service.QtService;
import org.iti.gh.service.SgcfService;
import org.iti.gh.service.ShjzService;
import org.iti.gh.service.SkService;
import org.iti.gh.service.XmService;
import org.iti.gh.service.XshyService;
import org.iti.gh.service.ZsService;
import org.iti.jxkh.entity.Jxkh_Honour;
import org.iti.jxkh.service.RychService;
import org.iti.xypt.entity.Teacher;
import org.iti.xypt.service.TeacherService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.io.Files;
import org.zkoss.util.media.Media;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;
import com.uniwin.asm.personal.ui.data.teacherinfo.RychWindow;
import com.uniwin.asm.personal.ui.data.teacherinfo.SgcfWindow;
import com.uniwin.asm.personal.ui.data.teacherinfo.XsttWindow;
import com.uniwin.framework.entity.WkTUser;
/*其他功能类*/
public class QtqkWindow extends BaseWindow{

	/*其他情况*/

	//学术团体
	Button button1;
	Listbox listbox1;
	//学术社会团体
	Button button2;
	Listbox listbox2;

	//荣誉称号
	Button button3;
	Listbox listbox3;
	//发生事故处分
	Button button4;
	Listbox listbox4;
	//个人年度思想总结
	Button submit;
	Grid grid1;
	Textbox thought,education,school,research,others;
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
	QkdcService qkdcService;
	CgService cgService;
	LwzlService lwzlService;
	TeacherService teacherService;
	ShjzService shjzService;
	RychService rychService;
	SgcfService sgcfService;
	WkTUser user;
	Teacher teacher;
	long kuid;
	@Override
	public void initShow() {
		
		//测试 word
		//
		
		initXsjl();
		listbox1.setItemRenderer(new ListitemRenderer(){
			public void render(Listitem arg0, Object arg1) throws Exception {
			final GhShjz jz = (GhShjz) arg1;
				//序号
				Listcell c1 = new Listcell(arg0.getIndex()+1+"");
				//团体名称
				Listcell c2 = new Listcell();
				InnerButton ib = new InnerButton();
				ib.setLabel(jz.getJzName());
				ib.addEventListener(Events.ON_CLICK, new EventListener(){
					public void onEvent(Event arg0) throws Exception {

						final XsttWindow w = (XsttWindow) Executions.createComponents
						("/admin/personal/data/teacherinfo/qtqk/xstt.zul", null, null);
						w.setKuid(user.getKuId());
						w.setShjz(jz);
						w.doHighlighted();
						w.initWindow();
						w.addEventListener(Events.ON_CHANGE, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								loadshjz();
								
							}
						});
					}
				});
				c2.appendChild(ib);

				//担任职务
				Listcell c3 = new Listcell();
				c3.setLabel(jz.getJzPosition());
				//任职时间
				Listcell c4 = new Listcell("");
				c4.setLabel(jz.getJzTime());
				//功能
				Listcell c5 =  new Listcell();
				InnerButton gn = new InnerButton();

				gn.setLabel("删除");
				gn.addEventListener(Events.ON_CLICK, new EventListener(){

					public void onEvent(Event arg0) throws Exception {
						if(Messagebox.show("确定删除吗?", "提示", Messagebox.OK|Messagebox.CANCEL,
								Messagebox.QUESTION)==1){
							shjzService.delete(jz);
							loadshjz();
						}
					}
				});
				c5.appendChild(gn);
				arg0.appendChild(c1); arg0.appendChild(c2); arg0.appendChild(c3);
				arg0.appendChild(c4); arg0.appendChild(c5);
			}
		});
		
		listbox3.setItemRenderer(new ListitemRenderer(){
			public void render(Listitem arg0, Object arg1) throws Exception {
				final Jxkh_Honour ch = (Jxkh_Honour) arg1;
				//序号
				Listcell c1 = new Listcell(arg0.getIndex()+1+"");
				//荣誉名称
				Listcell c2 = new Listcell();
				InnerButton ib = new InnerButton();
				ib.setLabel(ch.getRyName());
				ib.addEventListener(Events.ON_CLICK, new EventListener(){

					public void onEvent(Event arg0) throws Exception {

						final RychWindow  w = (RychWindow ) Executions.createComponents
						("/admin/personal/data/teacherinfo/qtqk/rych.zul", null, null);
						w.setKuid(user.getKuId());
						w.setRych(ch);
						w.doHighlighted();
						w.initWindow();
						w.addEventListener(Events.ON_CHANGE, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								loadrych();
							}
						});

					}
				});
				c2.appendChild(ib);
				//年度
				Listcell c3 = new Listcell();
				c3.setLabel(ch.getRyYear());
				//颁发单位
				Listcell c4 = new Listcell();
				c4.setLabel(ch.getRyDep());

				//功能
				Listcell c5 =  new Listcell();
				InnerButton gn = new InnerButton();

				gn.setLabel("删除");
				gn.addEventListener(Events.ON_CLICK, new EventListener(){

					public void onEvent(Event arg0) throws Exception {
						if(Messagebox.show("确定删除吗?", "提示", Messagebox.OK|Messagebox.CANCEL,
								Messagebox.QUESTION)==1){
							xmService.delete(ch);
							loadrych();
						}
					}
				});
				c5.appendChild(gn);
				arg0.appendChild(c1); arg0.appendChild(c2); arg0.appendChild(c3);
				arg0.appendChild(c4); arg0.appendChild(c5); 
			}
		});

		listbox4.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhSgcf cf = (GhSgcf) arg1;

				//序号
				Listcell c1 = new Listcell(arg0.getIndex()+1+"");

				//事故原因
				Listcell c2 = new Listcell();
				InnerButton ib = new InnerButton();
				ib.setLabel(cf.getSgReason());
				ib.addEventListener(Events.ON_CLICK, new EventListener(){
					public void onEvent(Event arg0) throws Exception {
						final SgcfWindow w = (SgcfWindow) Executions.createComponents
						("/admin/personal/data/teacherinfo/qtqk/sgcf.zul", null, null);
						w.setKuid(user.getKuId());
						w.setSgcf(cf);
						w.doHighlighted();
						w.initWindow();
						w.addEventListener(Events.ON_CHANGE, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								loadsgcf();
							}
						});
					}
				});
				c2.appendChild(ib);

				//年度
				Listcell c3 = new Listcell();
				c3.setLabel(cf.getSgYear());
				//处分单位
				Listcell c4 = new Listcell();
				c4.setLabel(cf.getSgDep());
				//功能
				Listcell c5 =  new Listcell();
				InnerButton gn = new InnerButton();
				gn.setLabel("删除");
				gn.addEventListener(Events.ON_CLICK, new EventListener(){
					public void onEvent(Event arg0) throws Exception {
						if(Messagebox.show("确定删除吗?", "提示", Messagebox.OK|Messagebox.CANCEL,
								Messagebox.QUESTION)==1){
							xmService.delete(cf);
							loadsgcf();
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
		teacher = teacherService.findBykuid(user.getKuId());
		kuid=user.getKuId();
		thought.setValue(teacher.getThThought()!=null?teacher.getThThought():"");
		this.education.setValue(teacher.getThEducation()!=null?teacher.getThEducation():"");
		this.school.setValue(teacher.getThSchool()!=null?teacher.getThSchool():"");
		this.research.setValue(teacher.getThResearch()!=null?teacher.getThResearch():"");
		this.others.setValue(teacher.getThOthers()!=null?teacher.getThOthers():"");
	}

	public void initXsjl(){
		initWindow();
		//学术团体+学术社会团体
		loadshjz();
		//荣誉称号
		loadrych();
		//发生事故处分
		loadsgcf();
	
	}
	
	public void onClick$button1(){

		final XsttWindow cgWin = (XsttWindow) Executions.createComponents
		("/admin/personal/data/teacherinfo/qtqk/xstt.zul", null, null);
		cgWin.setKuid(user.getKuId());
		cgWin.doHighlighted();
		cgWin.initWindow();
		cgWin.addEventListener(Events.ON_CHANGE, new EventListener(){

			public void onEvent(Event arg0)
			throws Exception {
				initXsjl();
				cgWin.detach();
			}
		});
	}

	public void onClick$button3(){
		

		final RychWindow cgWin = (RychWindow) Executions.createComponents
		("/admin/personal/data/teacherinfo/qtqk/Rych.zul", null, null);
		cgWin.setKuid(user.getKuId());
		cgWin.doHighlighted();
		cgWin.initWindow();
		cgWin.addEventListener(Events.ON_CHANGE, new EventListener(){

			public void onEvent(Event arg0)
			throws Exception {
				initXsjl();
				cgWin.detach();
			}
		});
	
	}
	
	
	public void onClick$button4(){
		
		final SgcfWindow cgWin = (SgcfWindow) Executions.createComponents
		("/admin/personal/data/teacherinfo/qtqk/sgcf.zul", null, null);
		cgWin.setKuid(user.getKuId());
		cgWin.doHighlighted();
		cgWin.initWindow();
		cgWin.addEventListener(Events.ON_CHANGE, new EventListener(){

			public void onEvent(Event arg0)
			throws Exception {
				initXsjl();
				cgWin.detach();
			}
		});
	
	}
	
	
	public void onClick$button1out()  throws IOException, WriteException{
		//学术团体（已参加）
		List list1 =shjzService.FindByKuid(kuid);
		
		if(list1 == null || list1.size() == 0){
			 try {
	 				Messagebox.show("空数据，导出错误！", "错误", Messagebox.OK,Messagebox.INFORMATION);
	 			} catch (InterruptedException e) {
	 				e.printStackTrace();
	 			}
	 			  return;
		}else {
			String fileName = "学术团体.xls";
			String Title = "学术团体 ";
			WritableWorkbook workbook;
			List titlelist=new ArrayList();
			titlelist.add("序号");
			titlelist.add("团体名称");
			titlelist.add("担任职务");
			titlelist.add("任职时间");
		
			String c[][]=new String[list1.size()][titlelist.size()];
			//从SQL中读数据
			for(int j=0;j<list1.size();j++){
				GhShjz jz=(GhShjz)list1.get(j);
			    c[j][0]=j+1+"";
				c[j][1]=jz.getJzName();
			    c[j][2]=jz.getJzPosition();
			    c[j][3]=jz.getJzTime();
			 
			}
	         ExportExcel ee=new ExportExcel();
			ee.exportExcel(fileName, Title, titlelist, list1.size(), c);
			
		}
	}
	
	public void onClick$button3out()  throws IOException, WriteException{
		//荣誉称号
		List list3 =rychService.FindByKuid(kuid);
		if(list3 == null || list3.size() == 0){
			 try {
	 				Messagebox.show("空数据，导出错误！", "错误", Messagebox.OK,Messagebox.INFORMATION);
	 			} catch (InterruptedException e) {
	 				e.printStackTrace();
	 			}
	 			  return;
		}else {
			String fileName = "荣誉称号.xls";
			String Title = "荣誉称号 ";
			WritableWorkbook workbook;
			List titlelist=new ArrayList();
			titlelist.add("序号");
			titlelist.add("荣誉名称");
			titlelist.add("年度");
			titlelist.add("颁发单位");
			
			String c[][]=new String[list3.size()][titlelist.size()];
			//从SQL中读数据
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
	}
	public void onClick$button4out()  throws IOException, WriteException{
		//发生事故处分
		List list4 =sgcfService.FindByKuid(kuid);
		if(list4 == null || list4.size() == 0){
			 try {
	 				Messagebox.show("空数据，导出错误！", "错误", Messagebox.OK,Messagebox.INFORMATION);
	 			} catch (InterruptedException e) {
	 				e.printStackTrace();
	 			}
	 			  return;
		}else {
			String fileName = "发生事故处分.xls";
			String Title = "发生事故处分） ";
			WritableWorkbook workbook;
			List titlelist=new ArrayList();
			titlelist.add("序号");
			titlelist.add("事故原因");
			titlelist.add("年度");
			titlelist.add("事故处分结果");
			titlelist.add("处分单位");
			
			String c[][]=new String[list4.size()][titlelist.size()];
			//从SQL中读数据
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
	//添加个人思想总结
	public void onClick$submit(){
	 
		teacher.setThThought(thought.getValue());
		teacher.setThEducation(education.getValue());
		teacher.setThSchool(school.getValue());
		teacher.setThResearch(research.getValue());
		teacher.setThOthers(others.getValue());
		teacherService.update(teacher);
		MessageBoxshow.showInfo("添加成功！");
	}
	
	//载入社会兼职
	public void loadshjz() {
		List jzlist =shjzService.FindByKuid(kuid);
		listbox1.setModel(new ListModelList(jzlist));
	}
	//载入荣誉称号
	public void loadrych() {
		List chlist =rychService.FindByKuid(kuid);
		listbox3.setModel(new ListModelList(chlist));
	}
	//载入事故处分
	public void loadsgcf() {
		List cflist =sgcfService.FindByKuid(kuid);
		listbox4.setModel(new ListModelList(cflist));
	}
}



























