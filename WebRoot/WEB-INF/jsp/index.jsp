<!DOCTYPE html>
<html>
	<head>
		<title>科腾主页</title>
		<meta charset="utf-8">
		<base target="_blank" />
		<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
		<%@ include file="/WEB-INF/jsp/include/taglib.jsp"%>
		<%@ include file="/WEB-INF/jsp/include/easyui.jsp"%>
		<style type="text/css">
			ol{max-width:70%;margin:20px auto;}
			ol li{float:left;width:200px;margin:10px 0;font-size:14px;}
			ol li a{text-decoration: none;}
		</style>
	</head>
	<body class="easyui-layout" onselectstart="javascript:return false;">
		<ol>
			<li><a href="${path}/customer">客户管理</a></li>
			<li><a href="${path}/terminal">终端管理</a></li>
			<li><a href="${path}/meter">计量点管理</a></li>
			<li><a href="${path}/transformer">变压器管理</a></li>
			<li><a href="${path}/remoteTermParamManagement">现场终端参数管理</a></li>
			<li><a href="${path}/remoteTermFactFrameSendAndRecv">厂家自组报文下发</a></li>
			<li><a href="${path}/dataItemDefine">数据项定义</a></li>
			<li><a href="${path}/taskDefine">任务定义</a></li>
			<li><a href="${path}/taskConfig">任务配置及下发</a></li>
			<li><a href="${path}/deviceFactoryManagement">设备厂家管理</a></li>
			<li><a href="${path}/frontMachine">前置机管理</a></li>
			<li><a href="${path}/terminalAccount">设备台账管理</a></li>
			<li><a href="${path}/terminalInstallRunDropManagement">设备安装投运消退管理</a></li>
			<li><a href="${path}/deviceOnlineRateManagement">设备在线率统计</a></li>
			<li><a href="${path}/deviceStateRanking">厂家设备状态在线率排名</a></li>
			<li><a href="${path}/deviceRepairManagement">设备维保管理</a></li>
			<li><a href="${path}/deviceRepairPreWarnManagement">设备维保预警管理</a></li>
			<li><a href="${path}/excelImportDocInfo">Excel表导入导出</a></li>
			<li><a href="${path}/export/jlnxpgbg">测试echarts</a></li>
			<li><a href="${path}/export/rebuild?customerId=1&year=2016&month=12&type=1">测试导出pdf</a></li>
			<br style="clear:both;">
		</ol>
	</body>
</html>