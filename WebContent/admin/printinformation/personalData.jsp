<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.uniwin.basehs.service.BaseService"%>
<%@ page import="org.iti.jxkh.service.JxkhAwardService"%>
<%@ page import="com.iti.common.util.BeanFactory"%>
<%@ page import="com.uniwin.framework.entity.WkTUser"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>打印个人基本信息</title>
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
  <script type="text/javascript">
	function pconfirm()
	{
		alert("为了确保您的打印效果，打印时，在菜单栏 文件 按钮中，页面设置 方向请选择纵打印；页眉页脚选项清空，页边距（毫米）设置：左右均为9.91，上为20，下为5.08。（注意：打印效果与您的打印机设置有关，请根据需要调整。）")
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
	<p align="center" >
		<strong style="font-size: 30px">河北省科学技术情报院</strong>
		<br/>
		<strong style="font-size: 25px">个人基本信息</strong>
	</p>
	<div align="center">
		<table width="700" border="1" align="center" cellspacing="0">
			<tr height="43">
				<td width="130"><strong>姓名</strong>
				</td>
				<td width="152">
				<s:property value="#request.user.kuName"/>				
				</td>
				<td width="130"><strong>性别</strong>
				</td>
				<td width="152">
				<s:property value="#request.sex"/>				
				</td>
				<td colspan="2" rowspan="4">
					<!-- <img alt="image" src="#request.image"> -->
				</td>
			</tr>

			<tr height="43">
				<td width="130"><strong>曾用名</strong>
				</td>
				<td width="152">
				<s:property value="#request.usedname"/>
				</td>
				<td width="130"><strong>民族</strong>
				</td>
				<td width="152">
				<s:property value="#request.nation"/>				
				</td>
							
			</tr>
			
			<tr height="43">
				<td width="130"><strong>政治面貌</strong>
				</td>
				<td width="152">
				<s:property value="#request.polity"/>
				</td>
				<td width="130"><strong>入党时间</strong>
				</td>
				<td width="152">
				<s:property value="#request.partyTime"/>
				</td>				
			</tr>
			
			<tr height="43">
				<td width="130"><strong>出生年月</strong>
				</td>
				<td width="152">
				<s:property value="#request.birthday"/>
				</td>	
				<td width="130"><strong>婚姻状况</strong>
				</td>
				<td width="152">
				<s:property value="#request.marry"/>
				</td>							
			</tr>
			
			<tr height="43">
				<td width="130"><strong>外语水平</strong>
				</td>
				<td colspan="5">
				<s:property value="#request.language"/>
				</td>							
			</tr>
			
			<tr height="43">
				<td width="130"><strong>身份证号</strong>
				</td>
				<td colspan="5">
				<s:property value="#request.identy"/>
				</td>							
			</tr>
			
			<tr height="43">
				<td width="130"><strong>籍贯</strong>
				</td>
				<td colspan="5">
				<s:property value="#request.nativeplace"/>
				</td>							
			</tr>
			
			<tr height="43">
				<td width="130"><strong>员工编号</strong>
				</td>
				<td width="152">
				<s:property value="#request.staffid"/>
				</td>	
				<td width="130"><strong>所属部门</strong>
				</td>
				<td colspan="3">
				<s:property value="#request.dept"/>
				</td>							
			</tr>
			
			<tr height="43">
				<td width="130"><strong>手机号</strong>
				</td>
				<td width="152">
				<s:property value="#request.phone"/>
				</td>					
				<td width="130"><strong>电子邮箱</strong>
				</td>
				<td width="152">
				<s:property value="#request.email"/>
				</td>	
				<td width="110"><strong>工作电话</strong>
				</td>
				<td width="126">
				<s:property value="#request.workphone"/>
				</td>						
			</tr>
			
			<tr height="400">
				<td width="120"><strong>个人简介</strong>
				</td>
				<td colspan="5" align="left" valign="top">
				<s:property value="#request.info"/>
				</td>							
			</tr>
			
		</table>
	</div>
</body>
</html>