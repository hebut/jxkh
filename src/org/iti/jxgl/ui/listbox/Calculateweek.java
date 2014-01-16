package org.iti.jxgl.ui.listbox;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date ;
import org.iti.jxgl.entity.JxCalendar;
import org.iti.jxgl.service.CalendarService;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.common.util.ConvertUtil;

import com.uniwin.common.util.DateUtil;
/**将具体时间转换成相对应的周次星期。
 * 
 */
public class Calculateweek {
	
	CalendarService calendarService;
	int wee;
	 
	public static void main(String[] args) throws IOException{

	} 
	//计算输入的日期是第几周
	public int countWeeks(String startTime,String endTime) throws ParseException{
		DateFormat df = DateFormat.getDateInstance();
		Date starttime=df.parse(startTime);
		int xq=getWeekOfDate(starttime);//开学的第一天是星期几
		if(xq==0){
			xq=7;
		}

		long nu;
		Date dd = null;
		nu=(long)7-xq+1;
		dd=DateUtil.addDate(starttime,nu);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		String subS2=dateFormat.format(dd);
		if(endTime.compareTo(subS2)<0&&endTime.compareTo(startTime)>=0){
			  return 1;
		}
		else{
		int[] start = split(subS2);
		int[] end = split(endTime);
		int num = 0;
		num=Math.abs(daynum(start[0],start[1],start[2])-daynum(end[0],end[1],end[2]));
		int zhouci=(num+1)/7;
		int wee=(num+1)%7;
		if(wee==0){
			return zhouci+1;
		}
		return zhouci+2;
		}
		 
	}
	//计算某时间(年月日)是星期几
	public   static   int   getWeekOfDate(Date   dt){ 

		Calendar   cal=Calendar.getInstance(); 
		cal.setTime(dt); 

		int   w=cal.get(Calendar.DAY_OF_WEEK)-1; 
		if(w <0)w=0; 
		return   w; 

	} 
	//用 split方法 处理输入的日期格式 
	public int[] split(String times){
		int year;
		int month;
		int day;
		String[] ymd = times.split("-");
		year = Integer.parseInt(ymd[0]);
		month = Integer.parseInt(ymd[1]);
		day = Integer.parseInt(ymd[2]);
		int[] aa = {year,month,day};
		return aa;
	}
	//判断年份 是否为。。
	public int leap(int year)
	{
		if((year%4==0&&year%100!=0)||year%400==0)
			return 1;
		else return 0;
	}

	//计算天数到月份
	public int   MD_num(int year,int month,int day)
	{
		int num=0,i;
		num=num+day-1;
		for(i=1;i<month;i++)
		{
			switch(i)
			{
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:      
			case 10:
			case 12: num+=31;break;
			case 4:
			case 6:
			case 9:
			case 11:num+=30;break;
			default: if(leap(year)==1) num+=29;
			else num+=28;
			break;
			}
		}
		return num;
	}
	//计算天数 到年份
	public int daynum(int year,int month,int day)
	{
		int num=0,y,i;
		y=year ;
		num=MD_num(year,month,day);
		if(y>=2000)
			for(i=2000;i<y;i++)
				if(leap(i)==1) num+=366;
				else num+=365;

		else for(i=y;i<2000;i++)
		{
			if(leap(i)==1) num+=366;
			else num+=365;
			num=num-MD_num(year,month,day);       
		}
		num=(-1)*num;
		return num;
	}
	/**
	 * 获取当前时间是本学期的第几周
	 * @return 周次
	 * @throws ParseException
	 */
	public int  getCurrentWeek(Long sid) throws ParseException{
		calendarService=(CalendarService)BeanFactory.getBean("calendarService");
		String[] YearTerm = calendarService.getNowYearTerm();
		JxCalendar calendar	=(JxCalendar)calendarService.findByCyearAndTermAndKdid(YearTerm[0],(Short.parseShort(YearTerm[1])),sid);
		Date now =new Date();
		Integer num=0;
		if(calendar!=null){
			num=countWeeks(ConvertUtil.convertString(calendar.getStarttime()),ConvertUtil.convertString(now));
		}		
		return num;
	}
	/**
	 * 获取该时间是本学期的第几周
	 * @return 周次
	 * @throws ParseException
	 */
	public int  getCurrentWeek(Long sid,Date da) throws ParseException{
		calendarService=(CalendarService)BeanFactory.getBean("calendarService");
		String[] YearTerm = calendarService.getNowYearTerm();
		JxCalendar calendar	=(JxCalendar)calendarService.findByCyearAndTermAndKdid(YearTerm[0],(Short.parseShort(YearTerm[1])),sid);
		//Date now =new Date();
		Integer num=0;
		if(calendar!=null){
			num=countWeeks(ConvertUtil.convertString(calendar.getStarttime()),ConvertUtil.convertString(da));
		}		
		return num;
	}
}
