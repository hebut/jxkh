package org.iti.xypt.personal.infoCollect;

import java.util.List;

import org.iti.xypt.personal.infoCollect.entity.WkTExtractask;
import org.iti.xypt.personal.infoCollect.entity.WkTGuidereg;
import org.iti.xypt.personal.infoCollect.entity.WkTPickreg;
import org.iti.xypt.personal.infoCollect.service.InfoService;
import org.iti.xypt.personal.infoCollect.service.LinkService;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Tabbox;




public class MyCallable  {

	private WkTExtractask eTask;
	private List<WkTGuidereg> gList;
	private List<WkTPickreg> pList;
	LinkService linkService;
	InfoService infoService;
	Tabbox _box;
	Listcell _cell;
	
	LinkCollection collection;
	
	public MyCallable(Listcell cell,Tabbox box, WkTExtractask eTask,List<WkTGuidereg> gList,List<WkTPickreg> pList,LinkService linkService,InfoService infoService){
		super();
		this._cell=cell;
		this._box=box;
		this.eTask=eTask;
		this.gList=gList;
		this.pList=pList;
		this.linkService=linkService;
		this.infoService=infoService;
	}

	public void call(){
		
		String cirSign = null;
		String urlTags=eTask.getKeBeginurl();
		AnalyBegUrl analyBegUrl=new AnalyBegUrl();
		List<String> bList=analyBegUrl.checkBeginUrl(urlTags);
		
//		WebEncoding wEncoding=new WebEncoding();
//		String encond=wEncoding.AnalyEnconding(bList.get(0));
		
		String encond1=eTask.getKeUrlencond();
		
		for(String u:bList){
			collection.addUnvisitedUrl(u,0);
		}
		
		String reg = null,regular;
		String urlBegin,urlEnd;
		String pageSign;Integer pageConut;
		String gModel;String gLevel;
		String gCirculate;
		
		WkTGuidereg gReg;
		PageNext pNext;
		for(int g=0;g<gList.size();g++){
			
			gReg=(WkTGuidereg)gList.get(g);
			urlBegin=gReg.getKgUrlbegin();
			urlEnd=gReg.getKgUrlend();
			pageSign=gReg.getKgPagesign();
			pageConut=gReg.getKgPagecount();
			gModel=gReg.getKgModel();
			gLevel=gReg.getKgLevel().trim();
			gCirculate=gReg.getKgCirculate();
			regular=gReg.getKgRegular().trim();
			//提取下一页导航
			if((pageSign!=null && !pageSign.equals("")) && pageConut!=null && gReg.getKgNextpage().trim().equals("true")){
//				if(gLevel.equals("false")){
					pNext=new PageNext();
					pNext.extractNextPage(collection,encond1, pageSign, pageConut);
				/*}else{
					PageMerage merage=new PageMerage();
					merage.Merage();
				}*/
			}
			
			//网页模板url导航
			if(!gModel.trim().equals("") && gLevel.trim().equals("false")){
				int unVisitCon=LinkCollection.getunVisitedUrlNum();
				
				if(regular.equals("false")){
					reg=gReg.getKgModel().replace("*",".*").replace("?","\\?").trim();
				}else{
					reg=gReg.getKgModel().trim();
				}
				
				for(int w=0;w<unVisitCon;w++){
				
					HtmlParserLinks hParserLinks=new HtmlParserLinks();
					String u=collection.unVisitedUrlDeQueue();
					List<String> list=hParserLinks.extractUrl(u,encond1, reg,urlBegin,urlEnd);
					int k=1;
					k++;
					String unVisitUrl;
					for(int v=0;v<list.size();v++){
						unVisitUrl=list.get(v);
						collection.addUnvisitedUrl(unVisitUrl,2);
					}
				}
			}else if(gModel!=null && gModel.trim().equals("") && gLevel.trim().equals("true") && (gCirculate==null || gCirculate.equals(""))){
				
				
				break;
				//最终页面
			} if(/*(gModel==null || gModel.equals("")) &&*/(pageSign!=null && !pageSign.equals("")) && pageConut!=null && gReg.getKgNextpage().trim().equals("true") && 
					gLevel.equals("true")){
				
				
			} if((gModel==null || gModel.equals(""))&& gCirculate!=null && !gCirculate.equals("")){
				//循环采集
				CirPush cirPush=new CirPush(_box);
				cirPush.initData(encond1,pList,gList,gCirculate);
				cirPush.start();
				cirSign=gCirculate;
			}
			
		}//for
			
			System.out.println("提取网址数目为： "+collection.getunVisitedUrlNum());
			
			//信息采集
				if(cirSign==null){
					
					String encond2=eTask.getKeConencond();
					ServerPush push = new ServerPush(_box);
					push.initData(collection,encond2,eTask,pList,gList,_cell);
					push.start();
				}
				
			
	}
	
	
}
