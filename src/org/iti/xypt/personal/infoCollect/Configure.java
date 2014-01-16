package org.iti.xypt.personal.infoCollect;
/**
 * 新闻抽取配置模块
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.iti.xypt.personal.infoCollect.entity.WKTHtmlSign;
import org.iti.xypt.personal.infoCollect.entity.WkTExtractask;
import org.iti.xypt.personal.infoCollect.entity.WkTGuidereg;
import org.iti.xypt.personal.infoCollect.entity.WkTPickreg;
import org.iti.xypt.personal.infoCollect.entity.WkTTasktype;
import org.iti.xypt.personal.infoCollect.service.GuideService;
import org.iti.xypt.personal.infoCollect.service.PickService;
import org.iti.xypt.personal.infoCollect.service.SignService;
import org.iti.xypt.personal.infoCollect.service.TaskService;
import org.iti.xypt.personal.infoCollect.service.TreeService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Window;

import com.zkoss.org.messageboxshow.MessageBox;


public class Configure extends Window implements AfterCompose{

	private static final long serialVersionUID = -7492597995260256709L;
	Combobox level;
	Textbox urlModel,bUrl,regName,task,Sign,imageLocation;
	Textbox scopeUrlBegin,scopeUrlEnd,regBegin,regEnd;
	Textbox remark;Intbox time;
	Listbox dataSource;
	Textbox scopeConBegin,scopeConEnd,circulTags,mergeSign;
	Checkbox next,Isextract,extractByReg,extractImage,timePick,merge;
	Listbox guideList,regList,burlList;
	Listhead guideHead,regHead;
	ListModelList guideModelList,pickModelList;
	List<WkTGuidereg> list=new ArrayList<WkTGuidereg>();
	List<WkTPickreg> rList=new ArrayList<WkTPickreg>();
	Radio middle,finalWeb,temporary,publish,everyTime;//,every
	Intbox pageCount;
	Tree loadTree;
	TreeService treeService;GuideService guideService;
	PickService pickService;TaskService taskService;
	SignService signService;
	Button makesure,guideUp,guideDown,pRegUp,pRegDown,edit,view,open;
	Label number;
	Textbox oldValue,newValue;//采集结果替换
	
	Intbox hour,minunte;
	Radio localTime;
	Listbox urlEncondList,conEncondList;//编码
	Listitem gbItem,utfItem,gbkItem,isoItem,gbconItem,utfconItem,gbkconItem,isoconItem;
	
	private WkTTasktype entity;
	public WkTTasktype getEntity() {
		return entity;
	}
	public void setEntity(WkTTasktype entity) {
		this.entity = entity;
	}

	private WkTExtractask editTask;
	public WkTExtractask getEditTask() {
		return editTask;
	}
	public void setEditTask(WkTExtractask editTask) {
		this.editTask = editTask;
	}
	Grid retainList;
	Rows retainRows;
	Checkbox checkbox = null;
	List<Checkbox> checkList=new ArrayList<Checkbox>();
	List<WKTHtmlSign> sList=new ArrayList<WKTHtmlSign>();
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this,this);
		if(guideList.getSelectedItem()==null){
			guideDown.setDisabled(true);
			guideUp.setDisabled(true);
		}
		if(regList.getSelectedItem()==null){
			pRegDown.setDisabled(true);
			pRegUp.setDisabled(true);
		}
		loadUrlList();
		sList=signService.findAll();
		//html保留标记生成
		loadSignList(sList);
	}
	
	List<String> urlCollection=new ArrayList<String>();
	public void onClick$add(){
		String u=bUrl.getValue().trim();
		if(u.equals("")){
			MessageBox.showWarning("请输入网址！");
		}else /*if(u.matches("http://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?"))*/{
			bUrl.setValue("");
			urlCollection.add(u);
			loadUrlList();
		}/*else{
			MessageBox.showWarning("网址不合法！");
		}*/
		
	}
	
	public void onClick$edit(){
		if(burlList.getSelectedItem()==null){
			edit.setDisabled(true);
		}else{
			urlCollection.set(burlList.getSelectedItem().getIndex(), bUrl.getValue());
			loadUrlList();
		}
	}
	
	public void loadUrlList(){
		ListModelList modelList=new ListModelList(urlCollection);
		burlList.setModel(modelList);
		burlList.setItemRenderer(new ListitemRenderer(){
			public void render(final Listitem item, Object arg1) throws Exception {
				String value=(String)arg1;
				item.setValue(value);
				if(value.length()>=60){
					value=value.substring(0,60)+"...";
				}
				item.setLabel(value);
				item.addEventListener(Events.ON_CLICK, new EventListener(){
					public void onEvent(Event arg0) throws Exception {
						bUrl.setValue(item.getValue().toString());
					}
					
				});
			}
		});
		number.setValue(burlList.getItemCount()+"");
	}
	
	public void onClick$delete(){
		if(burlList.getSelectedItem()==null){
			MessageBox.showWarning("请选择删除项！");
		}else{
			String u=(String) burlList.getSelectedItem().getValue();
			urlCollection.remove(burlList.getSelectedItem().getIndex());
			loadUrlList();
		}
		
	}
	
	public void onClick$clear(){
		try {
			if(Messagebox.show("确认删除信息?","提示信息",Messagebox.YES|Messagebox.NO,Messagebox.QUESTION)==Messagebox.YES){
				bUrl.setValue("");
				urlCollection.clear();
				loadUrlList();
			}else{
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void initWindow(WkTTasktype Entity) {
		this.entity=Entity;
	}
	
	/* 导航规则列表显示 */
	public void showGuideList(){
		guideHead.setVisible(true);
		guideModelList=new ListModelList(list);
		guideList.setModel(guideModelList);
		guideList.setItemRenderer(new ListitemRenderer(){
			public void render(final Listitem item, Object obj) throws Exception {
				final WkTGuidereg result=(WkTGuidereg)obj;
				item.setValue(result);
				Listcell c1=new Listcell(item.getIndex()+1+"");
				Listcell c2=new Listcell(result.getKgName());
				Listcell c4=new Listcell(result.getKgLevel());
				Listcell c8=new Listcell(result.getKgNextlevel());
				Listcell c5=new Listcell(result.getKgModel());
				Listcell c3=new Listcell(result.getKgNextpage());
				item.appendChild(c1);item.appendChild(c2);item.appendChild(c4);
				item.appendChild(c8);item.appendChild(c5);item.appendChild(c3);
				
				item.addEventListener(Events.ON_CLICK,new EventListener(){
					public void onEvent(Event arg0) throws Exception {
						level.setValue(result.getKgName());
						if(result.getKgLevel().trim().equals("false")){
							middle.setChecked(true);
							circulTags.setDisabled(true);
							urlModel.setValue(result.getKgModel());}
						else{
							finalWeb.setChecked(true);
							circulTags.setDisabled(false);
							urlModel.setValue(""); 
						}
						if((item.getIndex()+1)==1){
							guideUp.setDisabled(true);
							guideDown.setDisabled(false);
						}else if((item.getIndex()+1)==list.size()|| item==null){
							guideDown.setDisabled(true);
							guideUp.setDisabled(false);
						}else{
							guideDown.setDisabled(false);
							guideUp.setDisabled(false);
						}
							if(result.getKgNextpage().trim().equals("true")){
								next.setChecked(true);
								Sign.setDisabled(false);
								pageCount.setDisabled(false);
							}else{
								next.setChecked(false);
								Sign.setDisabled(true);
								pageCount.setDisabled(true);
							}
							if(result.getKgRegular().equals("true")){
								extractByReg.setChecked(true);
							}else{
								extractByReg.setChecked(false);
							}
							Sign.setValue(result.getKgPagesign());
							pageCount.setValue(result.getKgPagecount());
							scopeUrlBegin.setValue(result.getKgUrlbegin());
							scopeUrlEnd.setValue(result.getKgUrlend());
							scopeConBegin.setValue(result.getKgConbegin());
							scopeConEnd.setValue(result.getKgConend());
							circulTags.setValue(result.getKgCirculate());
					}
				});
			}
		});
	}
	
	/* 下载图片 */
	public void onCheck$extractImage(){
		Checkbox box;
		String checkValue;
		for(int i=0;i<checkList.size();i++){
			box=(Checkbox)checkList.get(i);
			checkValue=(String) box.getAttribute("checkValue");
			if(checkValue.trim().equals("IMG")){
				if(extractImage.isChecked()==true){
					box.setChecked(true);
				}else{
					box.setChecked(false);
				}
			}
		}
	}
	
	/* 采集规则列表显示 */
	public void showPickRegList(){
		regHead.setVisible(true);
		pickModelList=new ListModelList(rList);
		regList.setModel(pickModelList);
		regList.setItemRenderer(new ListitemRenderer(){
			public void render(final Listitem item, Object obj) throws Exception {
				final WkTPickreg result=(WkTPickreg)obj;
				item.setValue(result);
				Listcell c1=new Listcell(item.getIndex()+1+"");
				Listcell c2=new Listcell(result.getKpRegname());
				Listcell c3=new Listcell(result.getKpReglevel());
				Listcell c4=new Listcell(result.getKpDataname());
				Listcell c5,c6;
				String rbegin,rend;
				if(result.getKpRegbegin().length()<=30){
					c5=new Listcell(result.getKpRegbegin());
				}else{
					rbegin=result.getKpRegbegin().substring(0,30)+"...";
					c5=new Listcell(rbegin);
				}if(result.getKpRegend().length()<=30){
					c6=new Listcell(result.getKpRegend());
				}else{
					rend=result.getKpRegend().substring(0,30)+"...";
					c6=new Listcell(rend);
				}
				item.appendChild(c1);item.appendChild(c2);item.appendChild(c3);
				item.appendChild(c4);item.appendChild(c5);item.appendChild(c6);
			
				item.addEventListener(Events.ON_CLICK,new EventListener(){
					public void onEvent(Event arg0) throws Exception {
						regName.setValue(result.getKpRegname());
						regBegin.setValue(result.getKpRegbegin());
						regEnd.setValue(result.getKpRegend());
						String imageDown=result.getKpLoadimag();
						if(dataSource.hasFellow(result.getKpDataname())==true){
							Listitem selListitem=(Listitem) dataSource.getFellow(result.getKpDataname());
							selListitem.setSelected(true);
						}else{
							dataSource.setSelectedIndex(0);
						}
						if((item.getIndex()+1)==1){
							pRegUp.setDisabled(true);
							pRegDown.setDisabled(false);
						}else if((item.getIndex()+1)==rList.size()){
							pRegDown.setDisabled(true);
							pRegUp.setDisabled(false);
						}else{
							pRegDown.setDisabled(false);
							pRegUp.setDisabled(false);
						}
						if(imageDown.equals("false")){
							extractImage.setChecked(false);
//							imageLocation.setDisabled(true);
//							view.setDisabled(true);open.setDisabled(true);
						}else{
							extractImage.setChecked(true);
//							imageLocation.setDisabled(false);
//							view.setDisabled(false);open.setDisabled(false);
						}
						oldValue.setValue(result.getKpOldvalue());
						newValue.setValue(result.getKpNewvalue());
						
						String htmlTags=result.getKpRetainTags();
						if(htmlTags!=null){//选择
					
						HtmlTags tags=new HtmlTags();
						List<String> tList=tags.analyTagsValue(htmlTags);
						String choose,checkValue;
						Checkbox box;
						for(int i=0;i<checkList.size();i++){
							box=(Checkbox)checkList.get(i);
							checkValue=(String) box.getAttribute("checkValue");
							for(int j=0;j<tList.size();j++){
								choose=tList.get(j);
								if(choose.trim().equals(checkValue.trim()) ||( extractImage.isChecked()==true && checkValue.trim().equals("IMG")) ){
									box.setChecked(true);
								}
							}
						}
					  }else{//没选择
						  Checkbox box;
						  String checkValue;
						  for(int j=0;j<checkList.size();j++){
							  box=(Checkbox)checkList.get(j);
							  checkValue=(String) box.getAttribute("checkValue");
							  if(extractImage.isChecked()==true && checkValue.trim().equals("IMG")){
								  checkList.get(j).setChecked(true);
							  }else{
								  checkList.get(j).setChecked(false);
							  }
							  
						  }
					  }
					}
				});
			}
		});
		
	}
	
	private void loadSignList(List<WKTHtmlSign> hList) {
		// TODO Auto-generated method stub
		int count=(double)hList.size()/(double)4>(hList.size()/4)?hList.size()/4+1:hList.size()/4;
		if(hList.size()%4==0){
			int jshu=0;
			for(int r=0;r<count;r++){
				Row row=new Row();
				for(int c=0;c<4;c++){
					checkbox=new Checkbox();
					checkbox.setAttribute("checkValue", hList.get(jshu).getKsValue());
					checkList.add(checkbox);
					checkbox.setLabel(hList.get(jshu).getKsName());
					row.appendChild(checkbox);
					jshu++;
				}
				retainRows.appendChild(row);
			}
			retainList.appendChild(retainRows);
		}else{
			int jshu=0;
			Row row;
			for(int r=0;r<count-1;r++){
				row=new Row();
				for(int c=0;c<4;c++){
					checkbox=new Checkbox();
					checkbox.setAttribute("checkValue", hList.get(jshu).getKsValue());
					checkList.add(checkbox);
					checkbox.setLabel(hList.get(jshu).getKsName());
					row.appendChild(checkbox);
					jshu++;
				}
				retainRows.appendChild(row);
			}
			Row row2=new Row();
			Checkbox cbox;
			for(int left=0;left<hList.size()%4;left++){
				cbox=new Checkbox(hList.get(jshu).getKsName());
				cbox.setAttribute("checkValue", hList.get(jshu).getKsValue());
				checkList.add(cbox);
				row2.appendChild(cbox);
				retainRows.appendChild(row2);
				jshu++;
			}
			retainList.appendChild(retainRows);
		}
		
	}
	
	/**
	 * 导航规则保存
	 * @param pageChoose 是否提取下一页
	 * @param pageSign 提取下一页的标志
	 * @param pCount 提取页数
	 * @param fPage 所属层次
	 * @param nextLevel 提取下一层
	 * @param uModel 网址模板
	 * @param gRegular 正则表达式
	 */
	public void onClick$guidSave(){
	
		boolean pageChoose=next.isChecked();
		String pageSign=Sign.getValue();
		Integer pCount=pageCount.getValue();
		String gRegular=extractByReg.isChecked()+"";
		
		if(level.getValue()==null || level.getValue().trim().equals("")){
			MessageBox.showInfo("请设置层次名称！");
		}/*else if(pageChoose==true &&(pageSign==null || pCount==null)){
				MessageBox.showWarning("下一页标志不能为空！");
		}*/else{
			
		if(middle.isChecked()==true && urlModel.getValue().trim().equals("")){
			MessageBox.showInfo("请输入下一层网址模板！");
		}else{
			String l=level.getValue().trim();//层次名称
			String fPage;
			String nextLevel;
			String cirExtract = null;
			String conBegin=null;
			String conEnd=null;
			if(middle.isChecked()==true){
				fPage="false";
				nextLevel="true";
			}else{
				fPage="true";
				nextLevel="false";
				Pattern pattern=Pattern.compile("\r|\n");
				conBegin=scopeConBegin.getValue().trim();
				conEnd=scopeConEnd.getValue().trim();
				Matcher matcher4=pattern.matcher(conBegin);
			 	Matcher matcher5=pattern.matcher(conEnd);
			 	conBegin=matcher4.replaceAll("");
				conEnd=matcher5.replaceAll("");
				cirExtract=circulTags.getValue().trim();
//				if(!cirExtract.equals("") && cirExtract!=null){
					Matcher matcher3=pattern.matcher(cirExtract);
					cirExtract=matcher3.replaceAll("");
//				}
			}
			String uModel=urlModel.getValue().trim();
			String nPage=pageChoose+"";
			
			String urlBegin=scopeUrlBegin.getValue().trim();
			String urlEnd=scopeUrlEnd.getValue().trim();
			
			Pattern pattern=Pattern.compile("\r|\n");
	 	    Matcher matcher=pattern.matcher(urlBegin);
	 	    Matcher matcher2=pattern.matcher(urlEnd);
	 	    urlBegin=matcher.replaceAll("");
	 	    urlEnd=matcher2.replaceAll("");
	 	    
			List<String> nameList=new ArrayList<String>();
			WkTGuidereg show = null;
			for(int m=0;m<list.size();m++){
				show=list.get(m);
				nameList.add(show.getKgName());
			}
				if(guideList.getSelectedItem()==null){
					WkTGuidereg guideReg=new WkTGuidereg();
					guideReg.setKgName(l);
					guideReg.setKgLevel(fPage);
					guideReg.setKgModel(uModel);
					guideReg.setKgNextlevel(nextLevel);
					guideReg.setKgNextpage(nPage);
					guideReg.setKgPagesign(pageSign);
					guideReg.setKgPagecount(pCount);
					guideReg.setKgUrlbegin(urlBegin);
					guideReg.setKgUrlend(urlEnd);
					guideReg.setKgCirculate(cirExtract);
					guideReg.setKgConbegin(conBegin);
					guideReg.setKgConend(conEnd);
					guideReg.setKgRegular(gRegular);
					if(nameList.contains(l)){
						MessageBox.showError("命名重复！");
					}else{
						list.add(guideReg);
						MessageBox.showInfo("导航规则暂存成功！");
					}
				}else{
				
				WkTGuidereg guideReg=(WkTGuidereg) guideList.getSelectedItem().getValue();
				WkTGuidereg guideReg2;
				for(int i=0;i<list.size();i++){
					guideReg2=list.get(i);
					if(guideReg2.getKgName().equals(guideReg.getKgName())){
						
						guideReg.setKgName(l);
						guideReg.setKgLevel(fPage);
						guideReg.setKgNextlevel(nextLevel);
						guideReg.setKgModel(uModel);
						guideReg.setKgNextpage(nPage);
						guideReg.setKgPagesign(pageSign);
						guideReg.setKgPagecount(pCount);
						guideReg.setKgUrlbegin(urlBegin);
						guideReg.setKgUrlend(urlEnd);
						guideReg.setKgCirculate(cirExtract);
						guideReg.setKgConbegin(conBegin);
						guideReg.setKgConend(conEnd);
						guideReg.setKgRegular(gRegular);
						guideReg2=guideReg;
							MessageBox.showInfo("导航规则修改成功！");
						
					}
				}
			}
		}
			loadGuideNew();
			showGuideList();
	}
		
}
	
	/* 采集结果替换  */
	/*public void onClick$replace(){
		ResultReplace rep=(ResultReplace)Executions.createComponents("/admin/content/task/replace.zul", null, null);
		rep.doHighlighted();
	}*/
	
	
	
	/* 采集规则辅助工具 */
	/*public void onClick$ieWindow(){
		IeExtract iExtract=(IeExtract)Executions.createComponents("/admin/content/task/ie.zul", null, null);
		iExtract.doOverlapped();
	}*/
	
	/*采集规则存储 */
	public void onClick$saveReg(){
		
		String rName=regName.getValue().trim();
		String rBegin=regBegin.getValue().trim();
		String rEnd=regEnd.getValue().trim();
		String data = null;
		String downImage=extractImage.isChecked()+"";
		String level="最终页面";
		
		String oValue=oldValue.getValue();
		String nValue=newValue.getValue();
		
		StringBuffer sb=new StringBuffer();
		for(int p=0;p<checkList.size();p++){
			checkbox=(Checkbox)checkList.get(p);
			if(checkbox.isChecked()==true){
				sb.append(checkbox.getAttribute("checkValue")+",");
			}else{
				sb.append("false"+",");
			}
		}
		if(rName.equals("")||rBegin.equals("") || rEnd.equals("")){
			MessageBox.showWarning("请将信息填充完整！");
		}else if(dataSource.getSelectedItem().getValue()==null){
			MessageBox.showWarning("请选择数据库字段！");
		}else{
			//浏览器问题！
			Pattern pattern = Pattern.compile("\r|\n");
	 	    Matcher matcher=pattern.matcher(rBegin);
	 	    Matcher matcher2=pattern.matcher(rEnd);
			rBegin=matcher.replaceAll("");
			rEnd=matcher2.replaceAll("");
			data=(String) dataSource.getSelectedItem().getValue();
			
			List<String> rnameList=new ArrayList<String>();
			List<String> dataList=new ArrayList<String>();
			WkTPickreg show = null;
			for(int m=0;m<rList.size();m++){
				show=(WkTPickreg) rList.get(m);
				rnameList.add(show.getKpRegname());
				dataList.add(show.getKpDataname());
			}
			if(regList.getSelectedItem()==null){//新建
			WkTPickreg pReg=new WkTPickreg();
			pReg.setKpRegname(rName);
			pReg.setKpReglevel(level);
			pReg.setKpDataname(data);
			pReg.setKpRegbegin(rBegin);
			pReg.setKpRegend(rEnd);
			pReg.setKpLoadimag(downImage);
			pReg.setKpOldvalue(oValue);
			pReg.setKpNewvalue(nValue);
			if(sb.toString().replace("false,", "").trim().equals("")){
				pReg.setKpRetainTags(null);
			}else{
				pReg.setKpRetainTags(sb.toString());
			}
			if(rnameList.contains(rName)|| dataList.contains(data)){
				MessageBox.showError("规则或数据库字段命名重复！");
			}else{
				rList.add(pReg);
				MessageBox.showInfo("采集规则暂存成功！");
			}
		}else{//编辑
			WkTPickreg pickReg=(WkTPickreg) regList.getSelectedItem().getValue();
			WkTPickreg picReg2;
			for(int i=0;i<rList.size();i++){
				picReg2=rList.get(i);
				if(picReg2.getKpRegname().equals(pickReg.getKpRegname())){
					pickReg.setKpRegname(rName);
					pickReg.setKpReglevel(level);
					pickReg.setKpDataname(data);
					pickReg.setKpRegbegin(rBegin);
					pickReg.setKpRegend(rEnd);
					pickReg.setKpLoadimag(downImage);
					pickReg.setKpOldvalue(oValue);
					pickReg.setKpNewvalue(nValue);
					if(sb.toString().replace("false,", "").trim().equals("")){
						pickReg.setKpRetainTags(null);
					}else{
						pickReg.setKpRetainTags(sb.toString());
					}
					picReg2=pickReg;
					MessageBox.showInfo("采集规则修改成功！");
				}
			}
			
		}
			loadPickNew();
			showPickRegList();
	}
		
}
	/* 双击规则初始化 */
	public void doInit(WkTExtractask editTask) {
		this.editTask=editTask;
		list.clear();rList.clear();
		task.setValue(editTask.getKeName());
		remark.setValue(editTask.getKeRemk());
		
		if(editTask.getKeUrlencond().equals("gb2312"))
			gbItem.setSelected(true);
		else if(editTask.getKeUrlencond().equals("gbk"))
			gbkItem.setSelected(true);
		else if(editTask.getKeUrlencond().equals("ISO8859-1"))
			isoItem.setSelected(true);
		else
			utfItem.setSelected(true);
		
		if(editTask.getKeConencond().equals("gb2312"))
			gbconItem.setSelected(true);
		else if(editTask.getKeConencond().equals("gbk"))
			gbkconItem.setSelected(true);
		else if(editTask.getKeConencond().equals("ISO8859-1"))
			isoconItem.setSelected(true);
		else
			utfconItem.setSelected(true);
		
		if(editTask.getKeDefinite()==null || editTask.getKeDefinite().equals("")){
			hour.setDisabled(true);
			minunte.setDisabled(true);
			timePick.setChecked(false);
		}else{
			Integer h = 0,m = 0;
			if(editTask.getKeDefinitetype().equals("0")){
				everyTime.setChecked(true);
				h=Integer.valueOf((editTask.getKeDefinite()/3600)+"");
				m=Integer.valueOf((editTask.getKeDefinite()/60-h*60)+"");
			}else{
				localTime.setChecked(true);
				Date d=new Date(editTask.getKeDefinite());
				h=d.getHours();
				m=d.getMinutes();
			}
			hour.setValue(h);
			minunte.setValue(m);
			timePick.setChecked(true);
		}
		
		String urls=editTask.getKeBeginurl();
		BeginUrl bUrl=new BeginUrl();
		urlCollection=bUrl.analyUrl(urls);
		loadUrlList();
		List<WkTGuidereg> gList=guideService.findGuideListById(editTask.getKeId());
		List<WkTPickreg> pList=pickService.findpickReg(editTask.getKeId());
		
		list=gList;
		showGuideList();
		rList=pList;
		showPickRegList();
		if(editTask.getKePubtype()==Long.parseLong("0")){
			temporary.setChecked(true);
		}else{
			publish.setChecked(true);
//			timePick.setChecked(true);
		}
		
		
	}
	
	
	private void loadGuideNew(){
		
		level.setValue("");
		urlModel.setValue("");
		extractByReg.setChecked(false);
		Isextract.setChecked(false);
		
		next.setChecked(false);
		Sign.setConstraint("");
		Sign.setValue("");
		pageCount.setConstraint("");
		pageCount.setValue(null);
		
		
		guideList.setSelectedItem(null);
		scopeUrlBegin.setValue("");
		scopeUrlEnd.setValue("");
		circulTags.setValue("");
		
		scopeConBegin.setValue("");
		scopeConEnd.setValue("");
	}
	
	private void loadPickNew(){
		
		regName.setValue("");
		regBegin.setValue("");
		regEnd.setValue("");
		dataSource.setSelectedIndex(0);
		regList.setSelectedItem(null);
		oldValue.setValue("");
		newValue.setValue("");
	}
		
	/* 新建导航规则 */
	public void onClick$guideNew(){
		loadGuideNew();
	}
	
	/* 删除导航规则 */
	public void onClick$guideDele(){
		if(guideList.getSelectedItem()==null){
			MessageBox.showWarning("请选择删除项！");
		}else{
			WkTGuidereg gReg=(WkTGuidereg) guideList.getSelectedItem().getValue();
			if(editTask!=null){//数据库中存在
				try {
					if(Messagebox.show("确认删除信息？","询问信息",Messagebox.YES|Messagebox.NO,Messagebox.QUESTION)==Messagebox.YES){
						guideService.delete(gReg);
						List<WkTGuidereg> gList=guideService.findGuideListById(editTask.getKeId());
						list=gList;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}else{
				for(int i=0;i<list.size();i++){
					WkTGuidereg gReg2=list.get(i);
					if(gReg2.getKgName().equals(gReg.getKgName())){
						list.remove(gReg2);
					}
				}
			}
			showGuideList();
		}
	}
	/* 导航规则上移  */
	public void onClick$guideUp(){
		if(guideList.getSelectedItem()==null){
			MessageBox.showWarning("请选择移动对象！");
		}else{
			WkTGuidereg guideReg=(WkTGuidereg) guideList.getSelectedItem().getValue();
			Integer cId=guideList.getSelectedItem().getIndex();
			WkTGuidereg gReg=list.get(cId-1);
			list.set(cId-1,guideReg);
			list.set(cId, gReg);
			showGuideList();
		}
	}
	/* 导航规则下移 */
	public void onClick$guideDown(){
		if(guideList.getSelectedItem()==null){
			MessageBox.showWarning("请选择移动对象！");
		}else{
			WkTGuidereg guideReg=(WkTGuidereg) guideList.getSelectedItem().getValue();
			WkTGuidereg gReg2=guideService.findById(editTask.getKeId(),guideReg.getKgOrderid()+1);
			Integer cId=guideList.getSelectedItem().getIndex();
			WkTGuidereg gReg=list.get(cId+1);
			list.set(cId+1,guideReg);
			list.set(cId, gReg);
			showGuideList();
		}
	}
	
	/* 新建采集规则 */
	public void onClick$newPReg(){
		loadPickNew();
	}
	
	/*删除采集规则 */  //貌似有bug
	public void onClick$deletePReg(){
		if(regList.getSelectedItem()==null){
			MessageBox.showWarning("请选择删除项！");
		}else{
			WkTPickreg pReg=(WkTPickreg) regList.getSelectedItem().getValue();
			if(editTask!=null){
				try {
					if(Messagebox.show("确认删除任务信息？","询问信息",Messagebox.YES|Messagebox.NO,Messagebox.QUESTION)==Messagebox.YES){
						pickService.delete(pReg);
						List<WkTPickreg> regList=pickService.findpickReg(editTask.getKeId());
						rList=regList;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}else{
				for(int i=0;i<rList.size();i++){
					WkTPickreg pReg2=rList.get(i);
					if(pReg2.getKpRegname().equals(pReg.getKpRegname())){
						rList.remove(pReg2);
					}
				}
			}
			showPickRegList();
		}
	}
	
	/* 采集规则上移 */
	public void onClick$pRegUp(){
		if(regList.getSelectedItem()==null){
			try {
				Messagebox.show("");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}else{
			WkTPickreg pickReg=(WkTPickreg) regList.getSelectedItem().getValue();
			Integer cId=regList.getSelectedItem().getIndex();
			WkTPickreg pReg=rList.get(cId-1);
			rList.set(cId-1,pickReg);
			rList.set(cId, pReg);
			showPickRegList();
		}
	}
	
	/* 采集规则下移 */
	public void onClick$pRegDown(){
		if(regList.getSelectedItem()==null){
			try {
				Messagebox.show("");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}else{
			WkTPickreg pickReg=(WkTPickreg) regList.getSelectedItem().getValue();
			Integer cId=regList.getSelectedItem().getIndex();
			WkTPickreg pReg=rList.get(cId+1);
			rList.set(cId+1,pickReg);
			rList.set(cId, pReg);
			showPickRegList();
		}
	}
	
	/* 确定保存 */
	public void onClick$makesure(){
		
		if(task.getValue().equals("")){
			MessageBox.showInfo("请输入提取任务名称！");
		}else if(urlCollection.size()==0){
			MessageBox.showInfo("请输入起始网址！");
		}else if(list.size()==0 ||rList.size()==0){
			MessageBox.showInfo("请输入规则！");
		}else{
		String taskName=task.getValue().trim();
		String taskRemark=remark.getValue();
		
		String definiteType = null;
		Long min = null,h,date = null;
		String urlencond=(String) urlEncondList.getSelectedItem().getValue();
		String conEncond=(String)conEncondList.getSelectedItem().getValue();
		
		Long type,status;
		status=Long.parseLong("0");
		if(temporary.isChecked()==true){
			type=Long.parseLong("0");
		}else{
			type=Long.parseLong("1");
			if(timePick.isChecked()==true){
				if(everyTime.isChecked()==true){
					
					if(hour.getValue()!=null && !hour.getValue().equals(""))
						h=Long.valueOf(hour.getValue()*3600+"");
					else
						h=Long.parseLong(0+"");
					
					if(minunte.getValue()!=null && !minunte.getValue().equals(""))
						min=Long.valueOf(minunte.getValue()*60+"");
					else
						min=Long.parseLong(0+"");
					
					date=h+min;
					definiteType="0";
					status=Long.parseLong("2");
					
				}else if(localTime.isChecked()==true){
					
					Date d=new Date();
					d.setHours(hour.getValue());
					d.setMinutes(minunte.getValue());
					date=d.getTime();
					definiteType="1";
					status=Long.parseLong("2");
				}
			}
		}
		StringBuffer sb=new StringBuffer();
		for(int b=0;b<urlCollection.size();b++){
			sb.append(urlCollection.get(b));
			sb.append("$");
		}
		if(editTask!=null){//编辑了
			editTask.setKeName(taskName);
			editTask.setKeBeginurl(sb.toString());
			editTask.setKeBegincount(urlCollection.size()+"");
			editTask.setKePubtype(type);
			editTask.setKeRemk(taskRemark);
			editTask.setKeDefinite(date);
			editTask.setKeStatus(status);
			editTask.setKeUrlencond(urlencond);
			editTask.setKeConencond(conEncond);
			editTask.setKeDefinitetype(definiteType);
			taskService.update(editTask);
			WkTGuidereg guide;
			Long d=editTask.getKeId();
			for(int i=0;i<list.size();i++){
				guide=(WkTGuidereg)list.get(i);
				guide.setKgName(guide.getKgName());
				guide.setKgNextpage(guide.getKgNextpage());
				guide.setKgPagesign(guide.getKgPagesign());
				guide.setKgLevel(guide.getKgLevel());
				guide.setKgNextlevel(guide.getKgNextlevel());
				guide.setKgModel(guide.getKgModel());
				guide.setKgPagecount(guide.getKgPagecount());
				guide.setKgOrderid(d);
				guide.setKgUrlbegin(guide.getKgUrlbegin());
				guide.setKgUrlend(guide.getKgUrlend());
				guide.setKgCirculate(guide.getKgCirculate());
				guide.setKgConbegin(guide.getKgConbegin());
				guide.setKgConend(guide.getKgConend());
				guide.setKgRegular(guide.getKgRegular());
				d++;
				if(guide.getKgId()==null){
					guide.setKeId(editTask.getKeId());
					guideService.save(guide);
				}else{
				guideService.update(guide);}
			}
			WkTPickreg pick;
			Long c=editTask.getKeId();
			for(int j=0;j<rList.size();j++){
				pick=(WkTPickreg)rList.get(j);
				pick.setKpRegname(pick.getKpRegname());
				pick.setKpReglevel(pick.getKpReglevel());
				pick.setKpDataname(pick.getKpDataname());
				pick.setKpRegbegin(pick.getKpRegbegin());
				pick.setKpRegend(pick.getKpRegend());
				pick.setKpOrderid(c);
				pick.setKpLoadimag(pick.getKpLoadimag());
				pick.setKpRetainTags(pick.getKpRetainTags());
				pick.setKpOldvalue(pick.getKpOldvalue());
				pick.setKpNewvalue(pick.getKpNewvalue());
				c++;
				if(pick.getKpId()==null){
					pick.setKeId(editTask.getKeId());
					pickService.save(pick);
				}else{
				pickService.update(pick);}
			}
			
		}else{//新建
			WkTExtractask task=new WkTExtractask();
			task.setKeName(taskName);
			task.setKeRemk(taskRemark);
			task.setKePubtype(type);
			task.setKeBeginurl(sb.toString());
			task.setKeBegincount(urlCollection.size()+"");
			task.setKtaId(entity.getKtaId());
			task.setKeDefinite(date);
			if(timePick.isChecked()==true && time.getValue()==null && time.getValue().equals("")){
				status=Long.parseLong("2");
			}
			task.setKeStatus(status);
			task.setKeUrlencond(urlencond);
			task.setKeConencond(conEncond);
			task.setKeDefinitetype(definiteType);
			taskService.save(task);
		
			WkTGuidereg guide;WkTGuidereg gReg=null;
			Long orderId=task.getKeId();
			for(int i=0;i<list.size();i++){
				guide=(WkTGuidereg)list.get(i);
				gReg=new WkTGuidereg();
				gReg.setKeId(task.getKeId());
				gReg.setKgName(guide.getKgName());
				gReg.setKgNextpage(guide.getKgNextpage());
				gReg.setKgPagesign(guide.getKgPagesign());
				gReg.setKgLevel(guide.getKgLevel());
				gReg.setKgNextlevel(guide.getKgNextlevel());
				gReg.setKgModel(guide.getKgModel());
				gReg.setKgPagecount(guide.getKgPagecount());
				gReg.setKgOrderid(orderId);
				gReg.setKgUrlbegin(guide.getKgUrlbegin());
				gReg.setKgUrlend(guide.getKgUrlend());
				gReg.setKgCirculate(guide.getKgCirculate());
				gReg.setKgConbegin(guide.getKgConbegin());
				gReg.setKgConend(guide.getKgConend());
				gReg.setKgRegular(guide.getKgRegular());
				orderId++;
				guideService.save(gReg);
			}
			WkTPickreg pick;WkTPickreg pReg=null;
			Long orderId2=task.getKeId();
			for(int j=0;j<rList.size();j++){
				pick=(WkTPickreg)rList.get(j);
				pReg=new WkTPickreg();
				pReg.setKeId(task.getKeId());
				pReg.setKpRegname(pick.getKpRegname());
				pReg.setKpReglevel(pick.getKpReglevel());
				pReg.setKpDataname(pick.getKpDataname());
				pReg.setKpRegbegin(pick.getKpRegbegin());
				pReg.setKpRegend(pick.getKpRegend());
				pReg.setKpOrderid(orderId2);
				pReg.setKpLoadimag(pick.getKpLoadimag());
				pReg.setKpRetainTags(pick.getKpRetainTags());
				pReg.setKpOldvalue(pick.getKpOldvalue());
				pReg.setKpNewvalue(pick.getKpNewvalue());
				orderId2++;
				pickService.save(pReg);
			}
		}
			this.detach();
			Events.postEvent(Events.ON_CHANGE, this, null);
	  }	
 }
	
	public void onClick$canel(){
			list.clear();
			rList.clear();
			this.detach();
			guideDown.setDisabled(true);
			guideUp.setDisabled(true);
	}

	
}
