package org.iti.xypt.personal.infoCollect;

import java.util.List;

import org.iti.xypt.personal.infoCollect.entity.WkTExtractask;
import org.iti.xypt.personal.infoCollect.entity.WkTGuidereg;
import org.iti.xypt.personal.infoCollect.entity.WkTPickreg;
import org.iti.xypt.personal.infoCollect.service.GuideService;
import org.iti.xypt.personal.infoCollect.service.InfoService;
import org.iti.xypt.personal.infoCollect.service.LinkService;
import org.iti.xypt.personal.infoCollect.service.PickService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.uniwin.basehs.util.BeanFactory;


public class SimpleQuartzJob implements Job{

	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
				
				/*JobDataMap dataMap = context.getJobDetail().getJobDataMap();              
				String jobSays = dataMap.getString("extract");   
				System.out.println(jobSays);*/
			
			GuideService guideService=(GuideService) BeanFactory.getBean("guideService");
			PickService pickService=(PickService) BeanFactory.getBean("pickService");
			LinkService linkService=(LinkService) BeanFactory.getBean("linkService");
			InfoService infoService=(InfoService) BeanFactory.getBean("infoService");
			
			JobDataMap dataMap = context.getJobDetail().getJobDataMap(); 	
			WkTExtractask extractask=(WkTExtractask) dataMap.get("extract");
			
			System.out.println("定时采集开始--任务--"+extractask.getKeName()+"开始采集");
			
			Long taskId=extractask.getKeId();
			List<WkTGuidereg> guideList=guideService.findGuideListById(taskId);
			List<WkTPickreg> pickRegList=pickService.findpickReg(taskId);
			MyCallableSave mSave=new MyCallableSave(extractask,guideList,pickRegList,linkService,infoService);
			mSave.call();
		
	}
	
	
}
