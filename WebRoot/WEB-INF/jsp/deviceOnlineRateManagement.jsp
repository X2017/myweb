<!DOCTYPE html>
<html>
	<head>
		<title>设备在线率统计</title>
		<meta charset="utf-8">
		<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
		<%@ include file="/WEB-INF/jsp/include/taglib.jsp"%>
		<%@ include file="/WEB-INF/jsp/include/easyui.jsp"%>
		<style type="text/css">
			.datagrid-pager.pagination{display:none;}
		</style>
	</head>
	<body class="easyui-layout" onselectstart="javascript:return false;">
		<table id="dg" class="easyui-datagrid" toolbar="#toolbar"></table>
		<div id="tool">
			<!-- 搜索功能 -->
			<div class="input_search">
				<label for="begin" style="margin-right:10px">开始时间范围<input type="text" id="begin" name="begin" class="easyui-datebox" data-options="editable:false"></label>
				<label for="end" style="margin-right:10px">结束时间范围<input type="text" id="end" name="end" class="easyui-datebox" data-options="editable:false"></label>
				<a href="#" id="search" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
			</div>
		</div>
		<script type="text/javascript" src="${path}/js/onlineStatus.js"></script>
		<script type="text/javascript">
			//DOM后加载数据
			$(function(){
				$('#dg').datagrid({
				    url:'${path}/deviceOnlineRateManagement/datagrid',
					columns:[[
				        {field:'front_name',title:'前置机名称',sortable:true,width:100},
				        {field:'equipment_name',title:'设备名称',sortable:true,width:100},
				        {field:'customer_name',title:'所属客户名称',sortable:true,width:100},
				        {field:'dev_type',title:'设备类型',sortable:true,width:100},
				        {field:'onlineLength',title:'在线时长',sortable:true,width:100,
				        	formatter:function(row,index,value){//毫秒转换
				        		return parseInt(row/(1000*60*60*24))+'天'+parseInt((row%(1000*60*60*24))/(1000*60*60))+'时'+parseInt((row%(1000*60*60))/(1000*60))+'分'+parseInt((row%(1000*60))/1000)+'秒';
				        	}
				        },
				        {field:'onlinerate',title:'在线率',sortable:true,width:100,formatter:function(row,index,value){return row+"%";}}
				    ]]
				});
			});
		</script>
	</body>
</html>