<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>pdf</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
	</head>
	<body>
		<p>
		<table style="width:100%;height:800px">
		    <tr><td>&nbsp;</td></tr> 
		    <tr><td>&nbsp;</td></tr> 
		    <tr><td>&nbsp;</td></tr> 
		    <tr>
		    	<td align="center" style="font-size:68px">
				    <c:if test="${type=='0'}">
					    <c:if test="${addr=='2'}">
							低压系统日运行报告
					    </c:if>
					    <c:if test="${addr=='1'}">
							高压系统日运行报告
					    </c:if>
				    </c:if>
				    <c:if test="${type!='0'}">
					    <c:if test="${addr=='2'}">
							低压系统月运行报告
					    </c:if>
					    <c:if test="${addr=='1'}">
							高压系统月运行报告
					    </c:if>
				    </c:if>
		    	</td>
	    	</tr>
		    <tr><td>&nbsp;</td></tr>
		    <tr><td align="center" style="font-size:42px">${customer.customer_name}</td></tr>
		    <tr><td>&nbsp;</td></tr>
		    <tr><td>&nbsp;</td></tr>
		    <tr><td align="center"  style="font-size:28px">统计周期： ${checkTime}</td></tr>
		    <tr><td align="center" style="font-size:28px">生成报告日期: ${today}</td></tr>
		    <tr><td></td></tr>
		</table>
	</body>
</html>







