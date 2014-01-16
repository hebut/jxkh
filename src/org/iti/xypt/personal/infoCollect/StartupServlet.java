package org.iti.xypt.personal.infoCollect;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;

import org.iti.xypt.personal.infoCollect.entity.WkTExtractask;
import org.iti.xypt.personal.infoCollect.service.TaskService;
import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;

import com.uniwin.basehs.util.BeanFactory;


public class StartupServlet extends HttpServlet  {

	public void init(ServletConfig cfg) throws javax.servlet.ServletException

	{

		try {
			initScheduler(cfg);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}


	}


	protected void initScheduler(ServletConfig cfg)throws SchedulerException

	{
	  
		/*SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		Scheduler scheduler = schedulerFactory.getScheduler(); 
		
		for(int i=0;i<3;i++){
			System.out.print("ç¬? "+i+" å¼?å§?");
		    JobDetail jobDetail = new JobDetail("jobDetail2"+i, "jobDetailGroup2"+i, SimpleQuartzJob.class);
		    jobDetail.getJobDataMap().put("extract", "æˆ‘æ˜¯hhh");   
		    long s=10000;
			long startTime = System.currentTimeMillis() + 5000L; 
		    SimpleTrigger simpleTrigger=new SimpleTrigger("myTrigger"+i,null,new Date(startTime),null,SimpleTrigger.REPEAT_INDEFINITELY,s);
		    scheduler.scheduleJob(jobDetail, simpleTrigger);
		    scheduler.start();
		}*/

	    SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		Scheduler scheduler = schedulerFactory.getScheduler(); 
	      
	    TaskService taskService=(TaskService) BeanFactory.getBean("taskService");
			List<WkTExtractask> tList=taskService.findAllTask();
				for(int t=0;t<tList.size();t++){
					WkTExtractask extractask=tList.get(t);
					if(extractask.getKeDefinite()!=null && !extractask.getKeDefinite().equals("") && extractask.getKePubtype()==Long.parseLong("1")
							&& extractask.getKeDefinite()>0){
						 
					    JobDetail jobDetail = new JobDetail("jobDetail2"+t, "jobDetailGroup2"+t, SimpleQuartzJob.class);
					    jobDetail.getJobDataMap().put("extract", extractask);   
					    if(extractask.getKeDefinitetype().equals("0")){
					    	long period=extractask.getKeDefinite()*1000;
							long startTime = System.currentTimeMillis() + period; 
						    SimpleTrigger simpleTrigger=new SimpleTrigger("mySimTrigger"+t,null,new Date(startTime),null,SimpleTrigger.REPEAT_INDEFINITELY,period);
						    scheduler.scheduleJob(jobDetail, simpleTrigger);
						    
					    }else{
					    	
					    	Date d=new Date(extractask.getKeDefinite());
					    	int h=d.getHours();
					    	int m=d.getMinutes();
					    	
					    	try {
					    		
					    		 CronExpression expression=new CronExpression("0 "+m+" "+h+" * * ?");
								 CronTrigger cronTrigger=new CronTrigger("myCronTrigger"+t,null,expression.getCronExpression());
								 scheduler.scheduleJob(jobDetail, cronTrigger);
							
					    	} catch (ParseException e) {
								e.printStackTrace();
							}
					    	
					    }
						
					    scheduler.start();
					    
					}
					
				}
	       
	       
	}

	
}

