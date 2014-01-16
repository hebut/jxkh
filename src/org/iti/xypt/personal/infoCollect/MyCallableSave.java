package org.iti.xypt.personal.infoCollect;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.iti.xypt.personal.infoCollect.entity.WkTExtractask;
import org.iti.xypt.personal.infoCollect.entity.WkTGuidereg;
import org.iti.xypt.personal.infoCollect.entity.WkTLinkurl;
import org.iti.xypt.personal.infoCollect.entity.WkTOriInfocntId;
import org.iti.xypt.personal.infoCollect.entity.WkTOrinfo;
import org.iti.xypt.personal.infoCollect.entity.WkTOrinfocnt;
import org.iti.xypt.personal.infoCollect.entity.WkTPickreg;
import org.iti.xypt.personal.infoCollect.service.InfoService;
import org.iti.xypt.personal.infoCollect.service.LinkService;
import org.zkoss.zul.Listcell;



public class MyCallableSave {

	private WkTExtractask eTask;
	private List<WkTGuidereg> gList;
	private List<WkTPickreg> pList;
	LinkService linkService;
	InfoService infoService;
	private Listcell _cell;
	
	LinkCollection linkCollection;
	
	public MyCallableSave(WkTExtractask eTask,List<WkTGuidereg> gList,List<WkTPickreg> pList,LinkService linkService,InfoService infoService){
		super();
		this.eTask=eTask;
		this.gList=gList;
		this.pList=pList;
		this.linkService=linkService;
		this.infoService=infoService;
	}
	
	public synchronized void call(){
		
		linkCollection=new LinkCollection();
		
		String cirSign = null;
		String urlTags=eTask.getKeBeginurl();
		AnalyBegUrl analyBegUrl=new AnalyBegUrl();
		List<String> bList=analyBegUrl.checkBeginUrl(urlTags);
		
		String encond1=eTask.getKeUrlencond();
		
		for(String u:bList){
			linkCollection.addUnvisitedUrl(u,0);
		}
		
		WkTGuidereg gReg;
		String reg;
		String urlBegin,urlEnd;
		String gLevel,gModel;
		String gCirculate;
		
		for(int g=0;g<gList.size();g++){
			
			gReg=(WkTGuidereg)gList.get(g);
			gLevel=gReg.getKgLevel();
			gModel=gReg.getKgModel();
			gCirculate=gReg.getKgCirculate();
			urlBegin=gReg.getKgUrlbegin();
			urlEnd=gReg.getKgUrlend();
			//提取下一页导航
			if(gReg.getKgPagesign()!=null && gReg.getKgPagecount()!=null && gReg.getKgNextpage().trim().equals("true")){
				String pageSign=gReg.getKgPagesign();
				Integer pageCount=gReg.getKgPagecount();
				PageNext pNext=new PageNext();
				pNext.extractNextPage(linkCollection,encond1, pageSign, pageCount);
			}
			
			//网页模板url导航
			if(!gReg.getKgModel().trim().equals("") && gLevel.trim().equals("false")){
				int unVisitCon=LinkCollection.getunVisitedUrlNum();
				reg=gReg.getKgModel().replace("*",".*").replace("?","\\?").trim();
				for(int w=0;w<unVisitCon;w++){
				
					HtmlParserLinks hParserLinks=new HtmlParserLinks();
					String u=linkCollection.unVisitedUrlDeQueue();
					if(u==null){
						break;
					}
					
					List<String> list=hParserLinks.extractUrl(u,encond1, reg,urlBegin,urlEnd);
					int k=1;
					k++;
					String unVisitUrl;
					for(int v=0;v<list.size();v++){
						unVisitUrl=list.get(v);
//						if(linkService.findbyUrl(unVisitUrl).size()==0){
						if(linkService.findByIdAndUrl(unVisitUrl, eTask.getKeId()).size()==0){	
							WkTLinkurl linkurl=new WkTLinkurl();
							linkurl.setKeId(eTask.getKeId());
							linkurl.setKlUrl(unVisitUrl);
							linkurl.setKlStatus(Long.parseLong("0"));
							linkService.save(linkurl);
						}
						linkCollection.addUnvisitedUrl(unVisitUrl,2);
					}
				
			}
		}
			if((gModel==null || gModel.equals(""))&& gCirculate!=null && !gCirculate.equals("")){
				CirPick cirPick;
				String u;
				String[] extractResult;
				while (!LinkCollection.unVisitedUrlsEmpty() && LinkCollection.getVisitedUrlNum()<=1000) {
					u=LinkCollection.unVisitedUrlDeQueue();
					cirPick=new CirPick();
					CirEntity cirEntity;
					List<CirEntity> cirList=cirPick.cirExtract(u, gCirculate, pList);
					extractResult=new String[pList.size()];
					for(int cir=0;cir<cirList.size();cir++){
						cirEntity=cirList.get(cir);
						extractResult=cirEntity.getCEntity();
					}
				}
			
			}
			cirSign=gCirculate;
		
   }//glist外层for
		
		//信息采集
		infoPick pick=new infoPick();
//		List urlList=linkService.findByKeId(eTask.getKtaId());
		List urlList=linkService.findByIdAndStatus(eTask.getKeId(), Long.parseLong("0"));
		WkTLinkurl linkurl;
		String content = null;
		WkTOrinfo orinfo;
		String encond2=eTask.getKeConencond();
		
		DateFormat dateform=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=new Date();
		boolean hasContent=false;
		for(int p=0;p<urlList.size();p++){
			linkurl=(WkTLinkurl)urlList.get(p);
			String[] extractResult=new String[pList.size()];
			extractResult=pick.extractByTags(linkurl.getKlUrl(),encond2,eTask,pList,gList.get(gList.size()-1));
			if(extractResult!=null){//linkurl.getKlStatus()==Long.parseLong("0") &&

				String Ctime=dateform.format(date);
				orinfo=new WkTOrinfo();
				orinfo.setKeId(eTask.getKeId());
				orinfo.setKoiStatus(Long.parseLong("0"));
				orinfo.setKoiUrl(linkurl.getKlUrl());
				orinfo.setKoiCtime(Ctime);
				WkTPickreg pickreg = null;
				for(int f=0;f<pList.size();f++){
					pickreg=(WkTPickreg)pList.get(f);
					if(pickreg.getKpDataname().equals("title")){
						orinfo.setKoiTitle(extractResult[f]);
					}else if(pickreg.getKpDataname().equals("title2")){
						orinfo.setKoiTitle2(extractResult[f]);
					}else if(pickreg.getKpDataname().equals("source")){
						orinfo.setKoiSource(extractResult[f]);
					}else if(pickreg.getKpDataname().equals("koiAuthname")){
						orinfo.setKoiAuthname(extractResult[f]);
					}else if(pickreg.getKpDataname().equals("pTime")){
						orinfo.setKoiPtime(extractResult[f]);
					}else if(pickreg.getKpDataname().equals("content")){
						content=extractResult[f];
						hasContent=true;
					}
				}
				
				try {
					infoService.save(orinfo);
				} catch (Exception e) {
					System.out.println("采集字段过长，信息存储失败！");
				}
				
				linkurl.setKlStatus(Long.parseLong("1"));
				linkService.update(linkurl);
				
				if(hasContent){
					  Long len=3000L;
					  Long max=content.length()/len+1;
					  for(Long i=0L;i<max;i++){
					    	 WkTOrinfocnt infocnt=new WkTOrinfocnt();
					    	 WkTOriInfocntId infocntid=new WkTOriInfocntId(orinfo.getKoiId(),i+1);
					    	 infocnt.setId(infocntid);
						     Long be=i*len;
						     if(i==(max-1)){
						    	 infocnt.setKoiContent(content.substring(be.intValue())); 
						     }else{
						       Long en=(i+1)*len;
						       infocnt.setKoiContent(content.substring(be.intValue(),en.intValue()));
						     }
						     
						     try {
						    	 infoService.save(infocnt);
							} catch (Exception e) {
								System.out.println("内容存储失败！");
							}
						     
					     }	 
					  
				}
			}else{
				linkurl.setKlStatus(Long.parseLong("2"));
				linkService.update(linkurl);
			}
			
		}
		
			linkCollection.getUnVisitedUrl().deleteAll();
			linkCollection.getVisitedUrl().clear();
			System.out.println("采集结束！");
	
	}

	
}
