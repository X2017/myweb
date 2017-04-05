<!DOCTYPE html>
<html>
	<head>
		<title>厂家设备状态在线率排名</title>
		<meta charset="utf-8">
		<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
		<%@ include file="/WEB-INF/jsp/include/taglib.jsp"%>
		<%@ include file="/WEB-INF/jsp/include/easyui.jsp"%>
		<style type="text/css">
			.datagrid-pager.pagination{display:none;}
			#tool .input_search label{display: inline-block;margin-left:10px;}
			.datagrid-body{overflow:hidden;}
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
				    url:'${path}/deviceStateRanking/datagrid',
					columns:[[
				        {field:'devices',title:'厂家名称',sortable:true,width:100},
				        {field:'time',title:'厂家设备总在线时长',sortable:true,width:100,
				        	formatter:function(row,index,value){//毫秒转换
				        		return parseInt(row/(1000*60*60*24))+'天'+parseInt((row%(1000*60*60*24))/(1000*60*60))+'时'+parseInt((row%(1000*60*60))/(1000*60))+'分'+parseInt((row%(1000*60))/1000)+'秒';
				        	}
				        },
				        {field:'rate',title:'厂家设备总在线率',sortable:true,width:100,formatter:function(row,index,value){return row+"%";}},
				        {field:'rank',title:'排名',sortable:true,width:100,formatter:function(row,index,value){//排名为0时不显示
				        	if(row==0){
				        		return '';
				        	}else{
				        		return row;
				        	}
				        }}
				    ]]
				});
			});
		</script>
	</body>
</html>