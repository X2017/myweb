<!DOCTYPE html>
<html>
	<head>
		<title>pdf</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
	</head>
	<body>
		<table style="width:100%; border-bottom: 2px solid black;">
			<%
				String path = request.getContextPath();  
				String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
			%>
			<tr>
				<td><img src="<%=basePath %>/img/logo.png" alt="logo" align="right" absalign="middle"/></td>
			</tr>
		</table>
	</body>
</html>



