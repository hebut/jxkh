<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>打印投票表</title>
</head>
<body>
	<input type="button" value="打印 " onclick="window.print();" />
	<input name="button" type="submit" onclick="javascript:window.close()" value="关 闭">
	<table width="800px" border="1px" cellpadding="0" cellspacing="0" style="border: 1px solid;">
		<thead>
			<tr>
				<th width="10%">序号</th>
				<th width="30%">部门</th>
				<th width="15%">职工号</th>
				<th width="20%">姓名</th>
				<th width="10%">性别</th>
				<th width="10%">所得分数</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="#request.vrlist" id="vr" status="st">
				<tr>
					<td><s:property value="#st.index+1" /></td>
					<td><s:property value="#vr.user.dept.kdName" /></td>
					<td><s:property value="#vr.user.kuLid" /></td>
					<td><s:property value="#vr.user.kuName" /></td>
					<td><s:if test="#vr.user.kuSex==1">
							男
						</s:if> <s:else>
							女
						</s:else>
					</td>
					<td><s:property value="#vr.vrNumber" /></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</body>
</html>