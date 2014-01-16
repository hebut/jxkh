<%@ page language="java"  pageEncoding="UTF-8" import="java.util.*,org.iti.xypt.entity.Student,org.iti.xypt.service.*,org.iti.bysj.entity.*,org.iti.bysj.service.*,com.uniwin.common.util.*,com.uniwin.basehs.util.*,com.uniwin.framework.entity.*"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//String content = (String) request.getAttribute("content");
 PhaseService bsphaseService=(PhaseService)BeanFactory.getBean("bsphaseService");
 GpunitService gpunitService=(GpunitService)BeanFactory.getBean("gpunitService");
 BatchService batchService=(BatchService)BeanFactory.getBean("batchService");
 BsTeacherService bsteacherService=(BsTeacherService)BeanFactory.getBean("bsteacherService");
 BsStudentService  bsStudentService=(BsStudentService)BeanFactory.getBean("bsStudentService");
 CphaseService cphaseService=(CphaseService)BeanFactory.getBean("cphaseService");
 StudentService  studentService=(StudentService)BeanFactory.getBean("studentService");
 TeacherService  teacherService=(TeacherService)BeanFactory.getBean("teacherService");
boolean flag= Boolean.parseBoolean(request.getAttribute("flag").toString());
boolean joinbs= Boolean.parseBoolean(request.getAttribute("joinbs").toString());
List plist=(List)request.getAttribute("plist");
WkTUser user=(WkTUser)request.getAttribute("user");



%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>
<head>
 
</head>
<body>
<marquee behavior="scroll"  direction="left" scrollamount="3" scrolldelay="0"   loop="-1"  onmouseover="this.stop()" onmouseout="this.start()"> 
<table    width="2880px">
<tr  width="2880px" >
 <td >
 <%if(!joinbs&&flag||!joinbs&&teacherService.findBtKuid(user.getKuId())!=null||user.getKdId()==0L){%>
	<a><font color="#000000"  style="font-size: 12px">欢迎使用本系统!</font></a>
	<%} else if(joinbs){ 
if(plist!=null&&plist.size()>0){ 
	for(int j=0;j<plist.size();j++){
		Long pid=(Long)plist.get(j);
		BsPhase bp=(BsPhase)bsphaseService.get(BsPhase.class, pid);
		BsGpunit Bg=(BsGpunit)gpunitService.get(BsGpunit.class, bp.getBuId());
		BsTeacher bsteacher=bsteacherService.findByKuidAndBuid(user.getKuId(),bp.getBuId());
		BsStudent student=null;
		List xslist=new ArrayList();
		List jslist=new ArrayList();
		if(Bg.getBuIfbatch()==BsGpunit.IFBATCH_YES){
		    BsBatch bb= (BsBatch)batchService.get(BsBatch.class,bp.getBbId());
		    student=bsStudentService.findByKuidAndBbid(user.getKuId(),bp.getBbId());
		    xslist=cphaseService.findByBbidAndUpuser(bp.getBbId(),BsCphase.UPUSER_XS);
		    jslist=cphaseService.findByBbidAndUpuser(bp.getBbId(),BsCphase.UPUSER_JS);
		    %>
		    <a><font color="#000000"  style="font-size: 12px"><%=Bg.getGprocess().getBgJc().trim()+Bg.getDept().getKdName().trim()+"专业"+bb.getBbName().trim()+"正处于"%></font></a>
		    <a><font color="#FF0000"  style="font-size: 12px"><%=bp.getBpName()+","%></font></a>
		<% }else{
			student=bsStudentService.findByKuidAndBuid(user.getKuId(),bp.getBuId());
			xslist=cphaseService.findByBuidAndUpuser(bp.getBuId(),BsCphase.UPUSER_XS);
			jslist=cphaseService.findByBuidAndUpuser(bp.getBbId(),BsCphase.UPUSER_JS);
		%>
		<a><font color="#000000"  style="font-size: 12px"><%= Bg.getGprocess().getBgJc().trim()+Bg.getDept().getKdName().trim()+"专业"+"正处于"%></font></a>
		<a><font color="#FF0000"  style="font-size: 12px"><%=bp.getBpName()+","%></font></a>
		 <%}%>
		<a><font color="#000000"  style="font-size: 12px"> 此阶段的时间是</font></a>
		<a><font color="#FF0000"  style="font-size: 12px"> <%=ConvertUtil.convertString(new Date(bp.getBpStart()))+"到"+ConvertUtil.convertString(new Date(bp.getBpEnd()))+","%></font></a>
		<% 
		if(bsteacher!=null){
		//String nm=Bg.getDept().getGradeName(2);
		if(bp.getBpName().trim().equals("选题阶段")){%>
			<a><font color="#000000"  style="font-size: 12px">请您按照时间安排及时申报课题; </font></a>
		 <%} 
		if(bp.getBpName().trim().equals("过程(设计)阶段")){
			if(jslist.size()>0){
				%>
				 <a><font color="#000000"  style="font-size: 12px">需要您提交文档的阶段有：</font></a>
				<%
				for(int i=0;i<jslist.size();i++){
					BsCphase bc=(BsCphase)jslist.get(i);
		%>
		   
		    <a><font color="#FF0000"  style="font-size: 12px"><%=bc.getBcpName()%></font></a>
		 	<a><font color="#000000"  style="font-size: 12px">提交时间是：</font></a>
		 	<a><font color="#FF0000"  style="font-size: 12px"> <%=ConvertUtil.convertString(new Date(bc.getBcpStart()))+"到"+ConvertUtil.convertString(new Date(bc.getBcpEnd()))+",  "%></font></a>
		 	
		<%}}%>
		 <a><font color="#000000"  style="font-size: 12px">请您按时审批学生阶段文档; </font></a>
		<%}
		if(bp.getBpName().trim().equals("成绩评审和答辩阶段")){%>
			<a><font color="#000000"  style="font-size: 12px">注意查看答辩信息; </font></a>
		<%}
		if(bp.getBpName().trim().equals("评优管理阶段")){%>
			<a><font color="#000000"  style="font-size: 12px">请查看优秀论文; </font></a>
		<%}%>
		<%}%>
		<% if(student!=null){
			if(bp.getBpName().trim().equals("选题阶段")){%>
			<a><font color="#000000"  style="font-size: 12px">请您按照时间安排及时选择课题;</font></a>
			<%}
			if(bp.getBpName().trim().equals("过程(设计)阶段")){
				if(xslist.size()>0){%>
				 <a><font color="#000000"  style="font-size: 12px">需要您提交文档的阶段有：</font></a>
					<%
					for(int k=0;k<xslist.size();k++){
						BsCphase phase=(BsCphase)xslist.get(k);
						 %>		
		    <a><font color="#FF0000"  style="font-size: 12px"><%=phase.getBcpName()%></font></a>
		 	<a><font color="#000000"  style="font-size: 12px">提交时间是：</font></a>
		 	<a><font color="#FF0000"  style="font-size: 12px"> <%=ConvertUtil.convertString(new Date(phase.getBcpStart()))+"到"+ConvertUtil.convertString(new Date(phase.getBcpEnd()))+",   "%></font></a>
						 <% 		
						 }
				}
			%>
			<a><font color="#000000"  style="font-size: 12px">请您按时提交阶段文档; </font></a>
			<%}if(bp.getBpName().trim().equals("成绩评审和答辩阶段")){%>
			<a><font color="#000000"  style="font-size: 12px">注意查看答辩信息; </font></a>
		<%}if(bp.getBpName().trim().equals("评优管理阶段")){%>
			<a><font color="#000000"  style="font-size: 12px">请查看优秀论文; </font></a>
			<%} %>
		<%} %>
<%}%>
<%}else{%>
		<a><font color="#000000"  style="font-size: 12px">毕设过程不处于任何阶段......</font></a>
		<%}} if(!flag&&studentService.findByKuid(user.getKuId())!=null){
			Integer zccqcs= Integer.parseInt(request.getAttribute("zccqcs").toString());
			Integer weizccqcs=Integer.parseInt(request.getAttribute("weizccqcs").toString());
			Integer khcqcs=Integer.parseInt(request.getAttribute("khcqcs").toString());
			Integer weikhcs=Integer.parseInt(request.getAttribute("weikhcs").toString());
			Integer zcdays=Integer.parseInt(request.getAttribute("zcdays").toString());
			Integer khdays=Integer.parseInt(request.getAttribute("khdays").toString());
		%>
		 
		<a><font color="#000000"  style="font-size: 12px">您本学期出操 </font></a>
		<a><font color="#FF0000"  style="font-size: 12px"><%=zccqcs%></font></a>
		<a><font color="#000000"  style="font-size: 12px">次 ，还差</font></a>
		<a><font color="#FF0000"  style="font-size: 12px"><%=weizccqcs%></font></a>
		<a><font color="#000000"  style="font-size: 12px">次及格，离结束早操截止日期还有</font></a>
		<a><font color="#FF0000"  style="font-size: 12px"><%=zcdays%> </font></a>
		<a><font color="#000000"  style="font-size: 12px">天；本学期参加活动</font></a>
		<a><font color="#FF0000"  style="font-size: 12px"><%=khcqcs%></font></a>
		<a><font color="#000000"  style="font-size: 12px">次 ，还差</font></a>
		<a><font color="#FF0000"  style="font-size: 12px"><%=weikhcs%></font></a>
		<a><font color="#000000"  style="font-size: 12px">次及格，离结束活动截止日期还有</font></a>
		<a><font color="#FF0000"  style="font-size: 12px"><%=khdays%> </font></a>
		<a><font color="#000000"  style="font-size: 12px">天。请把握机会出操和参加活动</font></a>
		<%}%>
		</td>
		</tr>	 
</table>
</marquee>
</body>
</html>