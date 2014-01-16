<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*" 
    import="com.uniwin.framework.entity.*,com.uniwin.common.util.*,
    org.iti.gh.entity.*,org.iti.xypt.entity.*,org.iti.jxkh.entity.Jxkh_Honour" 
%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	GregorianCalendar calendar=new GregorianCalendar(); 
	int curYear = calendar.get(calendar.YEAR);//当前年份
	WkTUser user = (WkTUser)request.getAttribute("wktUser");//用户
	Teacher teacher = (Teacher)request.getAttribute("teacher");//教师
	GhJsps jsps = (GhJsps)request.getAttribute("ghJsps");//申请职务
	String education = (String)request.getAttribute("education");//最高学历
	String hDegree = (String)request.getAttribute("hDegree");//学位
	String hEduYear = (String)request.getAttribute("hEduYear");//修业年限
	String teacherTitle = (String)request.getAttribute("teacherTitle");//当前职务
	List skList = (List)request.getAttribute("skList");
	String total = (String)request.getAttribute("total");
	String average = (String)request.getAttribute("average");
	List zdxsList = (List)request.getAttribute("zdxsList");
	List cglist = (List)request.getAttribute("cglist");
	List xmlist = (List)request.getAttribute("xmlist");
	List softAuthlist = (List)request.getAttribute("softAuthlist");
	List magaPapelist = (List)request.getAttribute("magaPapelist");
	List hylwList = (List)request.getAttribute("hylwList");
	List pubtealist = (List)request.getAttribute("pubtealist");
	String qualifications = (String)request.getAttribute("qualifications");
	List rychList = (List)request.getAttribute("rychList");
	Map countMap = (Map)request.getAttribute("countMap"); 
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=teacher.getSchDept(teacher.getKdId()).trim()+curYear%>年职称评审基本信息表</title>
<style   media="print">     
  .Noprint{display:none;}     
  .PageNext{page-break-after:   always;}     
  </style>     
  <style>     
  .tdp     
  {     
  border-bottom:   1   solid   #000000;     
  border-left:   1   solid   #000000;     
  border-right:   0   solid   #ffffff;     
  border-top:   0   solid   #ffffff;     
  }     
  .tabp     
  {     
  border-color:   #000000   #000000   #000000   #000000;     
  border-style:   solid;     
  border-top-width:   2px;     
  border-right-width:   2px;     
  border-bottom-width:   1px;     
  border-left-width:   1px;     
  }     
  .NOPRINT   {     
  font-family:   "宋体";     
  font-size:   9pt;     
  }   
  .out{
   border-top:40px #D6D3D6 solid;
   width:0px;
   height:0px;
   border-left:80px #BDBABD solid;    
   position:relative;
  }
    
  </style>  
  <link href="admin/css/grzx/indicator.css" rel="stylesheet" type="text/css" />
  <script type="text/javascript">
	function pconfirm()
	{
		alert("为了确保您的打印效果，打印时，在菜单栏 文件 按钮中，页面设置 方向请选择横向打印；页眉页脚选项清空。（注意：打印效果与您的打印机设置有关，请根据需要调整。）")
	}
</script>
</head>
<body>
	<center class="Noprint">  
		<table width="800">
			<tr align="right">
				<td><input type="button" value="打印说明" onclick="return pconfirm();"/>&nbsp;&nbsp;
					<input type="button" value="打印 " onclick="window.print();"/>
				</td>
			</tr>
		</table>
	</center>
	<center style="font-size:25px; font-weight:bold;"><%=teacher.getSchDept(teacher.getKdId()).trim()+curYear%>年职称评审基本信息表</center>
	<div>&nbsp;</div>
	<div align="center">
		<table width="800">
			<tr align="left">
				<td align="right" width="40px">姓名：</td>
				<td><%=user.getKuName() %></td>
				<td align="right" width="40px">单位：</td>
				<td><%=teacher.getYuDept(teacher.getKdId()) %></td>
				<td align="right" width="120px">申报专业一级学科：</td>
				<td><%=jsps.getJspsSubject() %></td>
				<td align="right" width="80px">晋升方式：</td>
				<td width="40px"><%=jsps.getJspsType() %></td>
			</tr>
		</table>
		<table width="800" border="1" cellspacing="0" style="border-collapse: collapse" cellpadding="0">
			<tr align="left">
				<td colspan="2">申报专业技术职务</td>
				<td colspan="2"><%=jsps.getJspsPosition() %></td>
				<td>工作时间</td>
				<td>
					<%if(null!=teacher.getThStart()&&!"".equals(teacher.getThStart())){ %>
						<%=DateUtil.getMonthString(teacher.getThStart())%>
					<%}%>
				</td>
				<td>出生年月</td>
				<td>
					<%if(null!=user.getKuBirthday()&&!"".equals(user.getKuBirthday())){ %>
						<%=DateUtil.getMonthString(user.getKuBirthday()) %>
					<%}%>
				</td>
			</tr>
			<tr align="left" >
				<td>最高学历</td>
				<td><%=education %></td>
				<td>学位</td>
				<td><%=hDegree %></td>
				<td>毕业院校及专业</td>
				<td colspan="3">
					<%if(null!=teacher.getThSupschool()&&!"".equals(teacher.getThSupschool())){ %>
						<%=teacher.getThSupschool()+" "%>
					<%} %>
					<%if(null!=teacher.getThSupsub()&&!"".equals(teacher.getThSupsub())){ %>
						<%=teacher.getThSupsub()+" "%>
					<%} %>
				</td>
			</tr>
			<tr align="left">
				<td width="100px">毕业时间</td>
				<td width="100px">
					<%if(teacher.getThSupgratime()!=null&&!"".equals(teacher.getThSupgratime())){ %>
						<%=DateUtil.getMonthString(teacher.getThSupgratime()) %>
					<%} %>
				</td>
				<td width="90px">修业年限</td>
				<td width="90px"><%=hEduYear %></td>
				<td width="110px">现技术职务</td>
				<td width="110px"><%=teacherTitle %></td>
				<td width="100px">资格时间</td>
				<td width="100px">
					<%if(null!=teacher.getThEmplTime()&&!"".equals(teacher.getThEmplTime())){ %>
						<%=DateUtil.getMonthString(teacher.getThEmplTime()) %>
					<%} %>
				</td>
			</tr>
		</table>
		<table width="800px" border="1" cellspacing="0" style="border-collapse: collapse" cellpadding="0">
			<tr>
				<td width="80px">评分项目</td>
				<td width="720px">评 分 内 容</td>
			</tr>
			<tr>
				<td valign="middle">
					<div>教学</div>
					<div>工作</div>
				</td>
				<td align="left">
					<div>1、教学工作量</div>
					<%for(int i=0;i<skList.size();i++){ 
						GhSk gs = (GhSk)skList.get(i);
						if(null==gs.getSkYear()||"".equals(gs.getSkYear())){
					%>
						<div>&nbsp;</div>
					<%}else{ %>
						<div><%=gs.getSkYear() %></div>
					<%} %>
					 	<div>&nbsp;&nbsp;&nbsp;&nbsp;<%=gs.getSkMc().trim() %>
					 		<%if(null!=gs.getSkGrade()&&!"".equals(gs.getSkGrade())){ %>
					 			<%="，"+gs.getSkGrade().trim() %>
					 		<%} %>
					 		<%if(null!=gs.getThWork()&&!"".equals(gs.getThWork())){ %>
					 			<%="，"+gs.getThWork().trim()+"学时" %>
					 		<%} %>
					 		
					 	</div>
					<%} %>
					<div>共计<%=total %>学时，年均学时数为<%=average %>学时</div>
					<%for(int i=0;i<zdxsList.size();i++){
						GhPx px = (GhPx)zdxsList.get(i); %>
						<div><%=i+2+"、"+px.getPxBrzy()+"参加"+px.getPxMc()+"获"+px.getPxJx() %></div>
					<%} %>
					<table width="720px" border="1" cellspacing="0" style="border-collapse: collapse;" cellpadding="0">
						<tr>
							<td align="center" colspan="2"><div align="left">以下内容由各职能部门填写：</div></td>
						</tr>
						<tr>
							<td width="80px" align="center">研究生学院</td>
							<td width="640px">
								<table width="640px" border="1" cellspacing="0" style="border-collapse: collapse" cellpadding="0">
									<tr>
										<td width="80px" align="center">年均课时</td>
										<td width="560px" align="left"><%=average %></td>
									</tr>
									<tr align="center">
										<td>签字</td>
										<td></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr align="center">
							<td align="center">教务处</td>
							<td>
								<table width="640px" border="1" cellspacing="0" style="border-collapse: collapse;" cellpadding="0">
									<tr>
										<td width="82px" align="center">年均课时</td>
										<td width="558px" align="left"><%=average %></td>
									</tr>
									<tr>
										<td align="center">教学奖</td>
										<td>
											<table width="558px" border="1" cellspacing="1" style="border-collapse: collapse" cellpadding="0">
												<tr >
													<td width="110px" align="center">
														<div>
            												
        												</div>
													</td>
													<td width="112px" align="center">国家级</td>
													<td width="112px" align="center">省级</td>
													<td width="112px" align="center">厅级</td>
													<td width="112px" align="center">校级</td>
												</tr>
												<tr align="center">
													<td>1</td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
												<tr align="center">
													<td>2</td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
												<tr align="center">
													<td>3</td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
											</table>
										</td>
									</tr>
									<tr align="center">
										<td>签字</td>
										<td></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td valign="middle">
					<div>教学</div>
					<div>科研</div>
					<div>与技</div>
					<div>术推</div>
					<div>广的</div>
					<div>成果</div>
				</td>
				<td align="left">
					<div>一、获奖</div>
					<%for(int i=0;i<cglist.size();i++){
						GhCg cg = (GhCg)cglist.get(i); %>
						<div>&nbsp;&nbsp;&nbsp;&nbsp;<%="["+ (i+1)+ "] "+cg.getKyMc().trim()+"，"+cg.getKySj().trim()+"，"+cg.getKyHjmc().trim()+"，"+cg.getKyDj().trim()%></div>
					<%} %>
					<div>二、科研项目</div>
					<%for(int i=0;i<xmlist.size();i++){
						GhXm xm = (GhXm)xmlist.get(i);%>
						<div>&nbsp;&nbsp;&nbsp;&nbsp;<%="["+ (i+1)+ "] "+xm.getKyMc().trim()+"，"+xm.getKyLy().trim()+"，"+xm.getKyNumber().trim()%></div>
					<%} %>
					<table align="center" width="720px" border="1" cellspacing="1" style="border-collapse: collapse;" cellpadding="0">
						  <tr>
						    <td colspan="20" align="center"><div align="left">以下内容由各职能部门填写：</div></td>
						  </tr>
						  <tr align="center">
						    <td width="36" rowspan="3" align="center">&nbsp;</td>
						    <td colspan="7" align="center">科研处</td>
						    <td colspan="6" align="center">教务处</td>
						    <td colspan="6" align="center">学校办公室</td>
						  </tr>
						  <tr align="center">
						    <td colspan="2" align="center">国家级</td>
						    <td colspan="2" align="center">省级</td>
						    <td colspan="2" align="center">厅级</td>
						    <td width="24" rowspan="2" align="center">专利</td>
						    <td colspan="2" align="center">国家级</td>
						    <td colspan="2" align="center">省级</td>
						    <td colspan="2" align="center">厅级</td>
						    <td colspan="2" align="center">国家级</td>
						    <td colspan="2" align="center">省级</td>
						    <td colspan="2" align="center">厅级</td>
						  </tr>
						  <tr align="center">
						    <td width="36">项目</td>
						    <td width="36">获奖</td>
						    <td width="36">项目</td>
						    <td width="36">获奖</td>
						    <td width="36">项目</td>
						    <td width="36">获奖</td>
						    <td width="36">项目</td>
						    <td width="36">获奖</td>
						    <td width="36">项目</td>
						    <td width="36">获奖</td>
						    <td width="36">项目</td>
						    <td width="36">获奖</td>
						    <td width="36">项目</td>
						    <td width="36">获奖</td>
						    <td width="36">项目</td>
						    <td width="36">获奖</td>
						    <td width="36">项目</td>
						    <td width="36">获奖</td>
						  </tr>
						  <tr align="center">
						    <td>1</td>
						    <td><%=countMap.get("firGjjCount") %></td>
						    <td><%=countMap.get("firGjjHjCount") %></td>
						    <td><%=countMap.get("firSbjCount") %></td>
						    <td><%=countMap.get("firSbjHjCount") %></td>
						    <td><%=countMap.get("firStjCount") %></td>
						    <td><%=countMap.get("firStjHjCount") %></td>
						    <td><%=countMap.get("firFmzlCount") %></td>
						    <td><%=countMap.get("firGjjCount") %></td>
						    <td><%=countMap.get("firGjjHjCount") %></td>
						    <td><%=countMap.get("firSbjCount") %></td>
						    <td><%=countMap.get("firSbjHjCount") %></td>
						    <td><%=countMap.get("firStjCount") %></td>
						    <td><%=countMap.get("firStjHjCount") %></td>
						    <td><%=countMap.get("firGjjCount") %></td>
						    <td><%=countMap.get("firGjjHjCount") %></td>
						    <td><%=countMap.get("firSbjCount") %></td>
						    <td><%=countMap.get("firSbjHjCount") %></td>
						    <td><%=countMap.get("firStjCount") %></td>
						    <td><%=countMap.get("firStjHjCount") %></td>
						  </tr>
						  <tr align="center">
						    <td>2</td>
						   <td><%=countMap.get("secGjjCount") %></td>
						    <td><%=countMap.get("secGjjHjCount") %></td>
						    <td><%=countMap.get("secSbjCount") %></td>
						    <td><%=countMap.get("secSbjHjCount") %></td>
						    <td><%=countMap.get("secStjCount") %></td>
						    <td><%=countMap.get("secStjHjCount") %></td>
						    <td><%=countMap.get("secFmzlCount") %></td>
						    <td><%=countMap.get("secGjjCount") %></td>
						    <td><%=countMap.get("secGjjHjCount") %></td>
						    <td><%=countMap.get("secSbjCount") %></td>
						    <td><%=countMap.get("secSbjHjCount") %></td>
						    <td><%=countMap.get("secStjCount") %></td>
						    <td><%=countMap.get("secStjHjCount") %></td>
						    <td><%=countMap.get("secGjjCount") %></td>
						    <td><%=countMap.get("secGjjHjCount") %></td>
						    <td><%=countMap.get("secSbjCount") %></td>
						    <td><%=countMap.get("secSbjHjCount") %></td>
						    <td><%=countMap.get("secStjCount") %></td>
						    <td><%=countMap.get("secStjHjCount") %></td>
						  </tr>
						  <tr align="center">
						    <td>3</td>
						    <td><%=countMap.get("thiGjjCount") %></td>
						    <td><%=countMap.get("thiGjjHjCount") %></td>
						    <td><%=countMap.get("thiSbjCount") %></td>
						    <td><%=countMap.get("thiSbjHjCount") %></td>
						    <td><%=countMap.get("thiStjCount") %></td>
						    <td><%=countMap.get("thiStjHjCount") %></td>
						    <td><%=countMap.get("thiFmzlCount") %></td>
						    <td><%=countMap.get("thiGjjCount") %></td>
						    <td><%=countMap.get("thiGjjHjCount") %></td>
						    <td><%=countMap.get("thiSbjCount") %></td>
						    <td><%=countMap.get("thiSbjHjCount") %></td>
						    <td><%=countMap.get("thiStjCount") %></td>
						    <td><%=countMap.get("thiStjHjCount") %></td>
						    <td><%=countMap.get("thiGjjCount") %></td>
						    <td><%=countMap.get("thiGjjHjCount") %></td>
						    <td><%=countMap.get("thiSbjCount") %></td>
						    <td><%=countMap.get("thiSbjHjCount") %></td>
						    <td><%=countMap.get("thiStjCount") %></td>
						    <td><%=countMap.get("thiStjHjCount") %></td>
						  </tr>
						  <tr align="center">
						    <td>4</td>
						   <td><%=countMap.get("fouGjjCount") %></td>
						    <td><%=countMap.get("fouGjjHjCount") %></td>
						    <td><%=countMap.get("fouSbjCount") %></td>
						    <td><%=countMap.get("fouSbjHjCount") %></td>
						    <td><%=countMap.get("fouStjCount") %></td>
						    <td><%=countMap.get("fouStjHjCount") %></td>
						    <td><%=countMap.get("fouFmzlCount") %></td>
						    <td><%=countMap.get("fouGjjCount") %></td>
						    <td><%=countMap.get("fouGjjHjCount") %></td>
						    <td><%=countMap.get("fouSbjCount") %></td>
						    <td><%=countMap.get("fouSbjHjCount") %></td>
						    <td><%=countMap.get("fouStjCount") %></td>
						    <td><%=countMap.get("fouStjHjCount") %></td>
						    <td><%=countMap.get("fouGjjCount") %></td>
						    <td><%=countMap.get("fouGjjHjCount") %></td>
						    <td><%=countMap.get("fouSbjCount") %></td>
						    <td><%=countMap.get("fouSbjHjCount") %></td>
						    <td><%=countMap.get("fouStjCount") %></td>
						    <td><%=countMap.get("fouStjHjCount") %></td>
						  </tr>
						  <tr align="center">
						    <td>5</td>
						    <td><%=countMap.get("fivGjjCount") %></td>
						    <td><%=countMap.get("fivGjjHjCount") %></td>
						    <td><%=countMap.get("fivSbjCount") %></td>
						    <td><%=countMap.get("fivSbjHjCount") %></td>
						    <td><%=countMap.get("fivStjCount") %></td>
						    <td><%=countMap.get("fivStjHjCount") %></td>
						    <td><%=countMap.get("fivFmzlCount") %></td>
						    <td><%=countMap.get("fivGjjCount") %></td>
						    <td><%=countMap.get("fivGjjHjCount") %></td>
						    <td><%=countMap.get("fivSbjCount") %></td>
						    <td><%=countMap.get("fivSbjHjCount") %></td>
						    <td><%=countMap.get("fivStjCount") %></td>
						    <td><%=countMap.get("fivStjHjCount") %></td>
						    <td><%=countMap.get("fivGjjCount") %></td>
						    <td><%=countMap.get("fivGjjHjCount") %></td>
						    <td><%=countMap.get("fivSbjCount") %></td>
						    <td><%=countMap.get("fivSbjHjCount") %></td>
						    <td><%=countMap.get("fivStjCount") %></td>
						    <td><%=countMap.get("fivStjHjCount") %></td>
						  </tr>
						  <tr align="center">
						    <td>其它</td>
						    <td><%=countMap.get("elseGjjCount") %></td>
						    <td><%=countMap.get("elseGjjHjCount") %></td>
						    <td><%=countMap.get("elseSbjCount") %></td>
						    <td><%=countMap.get("elseSbjHjCount") %></td>
						    <td><%=countMap.get("elseStjCount") %></td>
						    <td><%=countMap.get("elseStjHjCount") %></td>
						    <td><%=countMap.get("elseFmzlCount") %></td>
						    <td><%=countMap.get("elseGjjCount") %></td>
						    <td><%=countMap.get("elseGjjHjCount") %></td>
						    <td><%=countMap.get("elseSbjCount") %></td>
						    <td><%=countMap.get("elseSbjHjCount") %></td>
						    <td><%=countMap.get("elseStjCount") %></td>
						    <td><%=countMap.get("elseStjHjCount") %></td>
						    <td><%=countMap.get("elseGjjCount") %></td>
						    <td><%=countMap.get("elseGjjHjCount") %></td>
						    <td><%=countMap.get("elseSbjCount") %></td>
						    <td><%=countMap.get("elseSbjHjCount") %></td>
						    <td><%=countMap.get("elseStjCount") %></td>
						    <td><%=countMap.get("elseStjHjCount") %></td>
						  </tr>
						  <tr align="center">
						    <td>签字</td>
						    <td colspan="7">&nbsp;</td>
						    <td colspan="6">&nbsp;</td>
						    <td colspan="6">&nbsp;</td>
						    </tr>
						</table>
				</td>
			</tr>
			<tr>
				<td>
					<div>专业</div>
					<div>理论</div>
					<div>学术</div>
					<div>水平</div>
					<div>(论文</div>
					<div>论著)</div>
				</td>
				<td align="left">
					<div>一、软件著作权</div>
						<%for(int i=0;i<softAuthlist.size();i++){
							GhRjzz soft = (GhRjzz)softAuthlist.get(i); %>
							<div>&nbsp;&nbsp;&nbsp;&nbsp;<%="["+(i+1)+"] "+soft.getRjName().trim()+"，"+soft.getRjFirtime().trim()+"，"+soft.getRjRegisno().trim()+"，"+soft.getRjTime().trim() %></div>
						<%} %>
					<div>二、论文</div>
					<%for(int i=0;i<magaPapelist.size();i++){
						GhQklw maga = (GhQklw)magaPapelist.get(i); %>
						<div>&nbsp;&nbsp;&nbsp;&nbsp;<%="["+(i+1)+"] "+maga.getLwMc().trim()+"，"+maga.getLwKw().trim()+"，"+maga.getLwFbsj().trim()+"，"+maga.getLwPages().trim() %></div>
					<%} %>
					<%int lwNum = magaPapelist.size(); %>
					<%for(int i=0;i<hylwList.size();i++){
						GhHylw lw = (GhHylw)hylwList.get(i); %>
						<div>&nbsp;&nbsp;&nbsp;&nbsp;<%="["+(i+lwNum+1)+"] "+lw.getLwMc().trim()+"，"+lw.getLwKw().trim()+"，"+lw.getLwFbsj().trim()+"，"+lw.getLwPages().trim() %></div>
					<%} %>
					<div>三、教材</div>
					<% for(int i=0;i<pubtealist.size();i++){
						GhZz zz= (GhZz)pubtealist.get(i);%>
						<div>&nbsp;&nbsp;&nbsp;&nbsp;<%="["+(i+1)+"] "+zz.getZzMc().trim()+"，"+zz.getZzKw().trim()+"，"+zz.getZzPublitime().trim() %></div>
					<%} %>
					<table align="center" width="720px" border="1" cellspacing="1"	style="border-collapse: collapse" cellpadding="0">
						  <tr align="center">
						    <td colspan="7" align="center"><div align="left">以下内容由人事处填写：</div></td>
						  </tr>
						  <tr>
						    <td rowspan="2" align="center">&nbsp;</td>
						    <td colspan="2" align="center">核心期刊</td>
						    <td rowspan="2" align="center">一般期刊</td>
						    <td rowspan="2" align="center">教材</td>
						    <td rowspan="2" align="center">论著</td>
						    <td rowspan="2" align="center">签字</td>
						  </tr>
						  <tr>
						    <td align="center">三大索引</td>
						    <td align="center">其它</td>
						  </tr>
						  <tr align="center">
						    <td width="120">1</td>
						    <td width="100"><%=countMap.get("fMCenterQkCount") %></td>
						    <td width="100"><%=countMap.get("fCenterQkCount") %></td>
						    <td width="100"><%=countMap.get("fQkCount") %></td>
						    <td width="100"><%=countMap.get("zbjcCount") %></td>
						    <td width="100"><%=countMap.get("firstzzCount") %></td>
						    <td rowspan="5" width="100">&nbsp;</td>
						  </tr>
						  <tr align="center">
						    <td>2</td>
						    <td><%=countMap.get("sMCenterQkCount") %></td>
						    <td><%=countMap.get("sCenterQkCount") %></td>
						    <td><%=countMap.get("sQkCount") %></td>
						    <td><%=countMap.get("fzbjcCount") %></td>
						    <td><%=countMap.get("secondzzCount") %></td>
						  </tr>
						  <tr align="center">
						    <td>3</td>
						    <td><%=countMap.get("tMCenterQkCount") %></td>
						    <td><%=countMap.get("tCenterQkCount") %></td>
						    <td><%=countMap.get("tQkCount") %></td>
						    <td><%=countMap.get("cbjcCount") %></td>
						    <td><%=countMap.get("thirdzzCount") %></td>
						  </tr>
						  <tr align="center">
						    <td>其它</td>
						    <td><%=countMap.get("elseMCenterQkCount") %></td>
						    <td><%=countMap.get("elseCenterQkCount") %></td>
						    <td><%=countMap.get("elseQkCount") %></td>
						    <td>0</td>
						    <td>0</td>
						  </tr>
						  <tr align="center">
						    <td>字数</td>
						    <td><%=countMap.get("mCenterLwWords") %></td>
						    <td><%=countMap.get("elseCenterLwWords") %></td>
						    <td><%=countMap.get("lwWords") %></td>
						    <td><%=countMap.get("jcWords") %></td>
						    <td><%=countMap.get("zzWords") %></td>
						  </tr>
						</table>
				</td>
			</tr>
			<tr>
				<td>任职资历</td>
				<td align="left">
					<%if(null==teacher.getThEnter()||"".equals(teacher.getThEnter())){%>
						1
					<%}else{ 
						int enterYear = Integer.valueOf(teacher.getThEnter().substring(0, 4));
					%>
						<%=curYear-enterYear+1 %>
					<%} %>
				</td>
			</tr>
			<tr>
				<td>年度考核</td>
				<td align="left"><%=jsps.getJspsAnnualAssessment() %></td>
			</tr>
			<tr>
				<td>荣誉称号</td>
				<td align="left">
					<%for(int i=0;i<rychList.size();i++){
						Jxkh_Honour rych = (Jxkh_Honour)rychList.get(i);
					%>
						<div><%=rych.getRyYear() + "年" +rych.getRyName()%></div>
					<%} %>
				</td>
			</tr>
			<tr>
				<td>学历</td>
				<td align="left"><%=hDegree %></td>
			</tr>
			<tr>
				<td>考试</td>
				<td align="left">外语考试（<%=jsps.getJspsForeign() %>）    计算机考试（<%=jsps.getJspsComputer() %>）</td>
			</tr>
		</table>
		
		<table width="800px" border="1" cellspacing="0" 
				style="border-collapse: collapse; " cellpadding="0">
			<tr>
				<td width="120px">单位鉴定意见</td>
				<td width="200px"></td>
				<td width="120px">
					<div>单位负责人签字</div>
					<div>（单位公章）</div>
				</td>
				<td width="358px"></td>
			</tr>
		</table>
	</div>
	<dir>&nbsp;</dir>
</body>
</html>