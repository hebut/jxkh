<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.uniwin.basehs.service.BaseService"%>
<%@ page import="com.iti.common.util.BeanFactory"%>
<%@ page import="org.iti.jxkh.entity.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>打印横向项目信息</title>
<style media="print">
.Noprint {
	display: none;
}

.PageNext {
	page-break-after: always;
}
</style>
<style>
.tdp {
	border-bottom: 1 solid #000000;
	border-left: 1 solid #000000;
	border-right: 0 solid #ffffff;
	border-top: 0 solid #ffffff;
}

.tabp {
	border-color: #000000 #000000 #000000 #000000;
	border-style: solid;
	border-top-width: 2px;
	border-right-width: 2px;
	border-bottom-width: 1px;
	border-left-width: 1px;
}

.NOPRINT {
	font-family: "宋体";
	font-size: 9pt;
}

.out {
	border-top: 40px #D6D3D6 solid;
	width: 0px;
	height: 0px;
	border-left: 80px #BDBABD solid;
	position: relative;
}
</style>
<script type="text/javascript">
	function pconfirm() {
		alert("为了确保您的打印效果，打印时，在菜单栏 文件 按钮中，页面设置 方向请选择纵打印；页眉页脚选项清空，页边距（毫米）设置：左右均为9.91，上为20，下为5.08。（注意：打印效果与您的打印机设置有关，请根据需要调整。）");
	}
</script>
</head>
<body>
	<center class="Noprint">
		<table width="700">
			<tr align="right">
				<td><input type="button" value="打印说明"
					onclick="return pconfirm();" />&nbsp;&nbsp; <input type="button"
					value="打印 " onclick="window.print();" /></td>
			</tr>
		</table>
	</center>
	<p align="center" >
		<strong style="font-size: 30px">河北省科学技术情报院</strong>
		<br/>
		<strong style="font-size: 25px">横向项目详细信息</strong>
	</p>
	<div align="center">
		<table width="700" border="1" align="center" cellspacing="0">
			<tr height="40">
				<td width="150"><strong>项目名称</strong></td>
				<td width="200"><s:property value="#request.zp.name" /></td>
				<td width="150"><strong>项目编号</strong></td>
				<td width="200"><s:property value="#request.zp.projecCode" />
				</td>
			</tr>

			<tr height="40">
				<td width="150"><strong>项目成员及排序</strong></td>
				<td width="550" align="left" colspan="3"><s:property
						value="#request.zpmember" /></td>
			</tr>

			<tr height="40">
				<td width="150"><strong>完成部门及排序</strong></td>
				<td width="550" align="left" colspan="3"><s:property
						value="#request.zpdept" /></td>
			</tr>

			<tr height="40">
				<td width="150"><strong>合作单位</strong></td>
				<td width="550" colspan="3"><s:property value="#request.zpcoo" /></td>
			</tr>
			<tr height="40">
				<td width="150"><strong>项目级别</strong></td>
				<td width="200">横向项目</td>
				<td width="150"><strong>项目负责人 </strong></td>
				<td width="200"><s:property value="#request.zp.header" /></td>
			</tr>
			<tr height="40">
				<td width="150"><strong>经费总额(万元)</strong></td>
				<td width="200"><s:property value="#request.zp.sumFund" /></td>
				<td width="150"><strong>今年到款经费<br />总额(万元) </strong></td>
				<td width="200"><s:property value="#request.zpfund" /></td>
			</tr>

			<tr height="40">

				<td width="150"><strong>是否为委托方</strong></td>
				<td width="200"><s:property value="#request.zpifentr" /></td>
				<td width="150"><strong><s:property
							value="#request.zpentr" /></strong></td>
				<td width="200"><s:property value="#request.zp.entruster" /></td>
			</tr>

			<tr height="40">

				<td width="150"><strong>合同始期 </strong></td>
				<td width="200"><s:property value="#request.zp.beginDate" /></td>
				<td width="150"><strong>合同终期</strong></td>
				<td width="200"><s:property value="#request.zp.endDate" /></td>
			</tr>
			<tr height="40">
				<td width="150"><strong>信息填写人</strong></td>
				<td width="200"><s:property value="#request.zpw" /></td>
				<td />
				<td />
			</tr>
			<tr height="120">
				<td width="150"><strong>项目组成员签字</strong></td>
				<td colspan="3" align="left" valign="top"><a>请核对信息，确认无误后请签字：</a>
				</td>
			</tr>
			<tr height="120">
				<td width="150"><strong>承担部门签字</strong></td>
				<td colspan="3" align="left" valign="top"><a>请核对信息，确认无误后请签字：</a>
				</td>
			</tr>
			<tr height="120">
				<td width="150"><strong>部门审核意见</strong></td>
				<td colspan="3" align="left">
					<%
						Jxkh_Project aw1 = (Jxkh_Project) request.getAttribute("zp");
					%> <%
 	String a1 = " ";
 	if (aw1.getbAdvice() != null) {
 		a1 = aw1.getbAdvice();
 	}
 %> <%=a1%></td>

			</tr>
			<tr height="120">
				<td width="150"><strong>业务办审核意见</strong></td>
				<td colspan="3" align="left">
					<%
						Jxkh_Project aw = (Jxkh_Project) request.getAttribute("zp");
					%> <%
 	String a = " ";
 	if (aw.getbAdvice() != null) {
 		a = aw.getbAdvice();
 	}
 %> <%=a%></td>

			</tr>

		</table>
	</div>

</body>
</html>