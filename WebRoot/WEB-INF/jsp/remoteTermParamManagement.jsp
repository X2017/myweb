<!DOCTYPE html>
<html>
	<head>
		<title>现场终端参数管理</title>
		<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
		<%@ include file="/WEB-INF/jsp/include/taglib.jsp"%>
		<%@ include file="/WEB-INF/jsp/include/easyui.jsp"%>
		<style type="text/css">
			*{box-sizing: border-box;margin:0;padding:0;list-style:none;}
			body{font-family: "Helvetica Neue", Helvetica, Arial, "Microsoft Yahei UI", "Microsoft YaHei", SimHei, "\5B8B\4F53", simsun, sans-serif;}
			#box{position: relative;overflow:hidden;}
			#table{position: absolute;top:-1px;left: 196px;font-size: 13px;color: #000000;border-collapse:collapse;}
			#table tr{height: 25px;}
			#table td,#table th{border:1px dotted #CCCCCC;padding: 0 8px;}
			.first_td{background: #F6F6F6;}
			#header{width:70%;padding:20px;}
			.header_left{float:left;width:220px;}
			header_left_top{position:relative;}
			.header_right{float:left;position:relative;}
			#check{font-size:14px;margin-top:0px;cursor: pointer;position:relative;width:150px;height:30px;background:url('${path}/img/a2.png');color:white;line-height:30px;border-radius:5px;padding-left:5px;}
			#nav{position:absolute;top:30px;left:0;z-index:9999999;}
			#nav li{background:url('${path}/img/a2.png');width:150px;height:30px;padding-left:5px;border-radius:2px;}
			#nav .active{background:url('${path}/img/a1.png');}
			.down{position:absolute;top:10px;right:10px;width: 0;height: 0;border-left: 6px solid transparent;border-right: 6px solid transparent; border-top: 12px solid white;}
			.header_left_top{margin-bottom:15px;}
			.header_left_bottom button{cursor: pointer;padding:8px;border: 0 none;background:#31B790;font-size:14px;font-weight:bold;color:white;border-radius:5px;}
			.button_active{background:#127155;}
			#table th:nth-child(even),.datagrid-header-row td:nth-child(odd){background:#71B997;}
			#table th:nth-child(odd),.datagrid-header-row td:nth-child(even){background:#73C3D1;}
			.datagrid-btable td:nth-child(even),#table td:nth-child(odd){background:#BCF1FA;}
			.datagrid-btable td:nth-child(odd),#table td:nth-child(even){background:#CBFDE5;}
			#table th{font-size:13px;}#table td{font-size:13px;}
			.datagrid-header-check input{display:none;}
			.search_box{display:inline-block;margin-right:40px;}
			.search_box input{width:200px;height:30px;border:0 none;border-radius:15px;background:black;color:white;padding:5px 10px;}
			#search_button{cursor: pointer;background:url('${path}/img/a7.png');width:20px;height:18px;position:absolute;top:7px;left:170px;border:0 none;}
			.pagination-info{display:none;}
			/* #box .panel.datagrid{position:relative;top:1px;} */
		</style>
	</head>
	<body class="easyui-layout" onselectstart="javascript:return false;">
		<div id="header">
			<div class="header_left">
				<div id="check">
					<strong>集中器</strong>
					<span class="down"></span>
					<ul id="nav" style="display:none">
						<li id="dianbiao">电表</li>
						<li id="caijiqi" class="active">集中器</li>
					</ul>
				</div>
			</div>
			<div class="header_right">
				<div class="header_left_bottom">
					<div class="search_box">
						<input type="text" id="search" name="search" onkeypress="if(event.keyCode==13) {search_fn();document.getElementById('table').innerHTML=''}" placeholder="请输入搜索内容...">
						<button id="search_button"></button>
					</div>
					<button id="button_tx">召测通信参数</button>
					<button id="button_rw">召测任务参数</button>
					<button>召测抄表参数</button>
					<button>召测配置参数</button>
				</div>
			</div>
			<div style="clear:both"></div>
		</div>
		<div id="box">
			<table id="dg"  style="width:600px;" class="easyui-datagrid" toolbar="#toolbar"></table>
			<!-- 召测之后右边生成表单 -->
			<table id="table"></table>
		</div>
		<!-- easyui-datagrid 通用设置和方法-->
		<script src="${path}/js/datagrid.js" type="text/javascript"></script>
		<script type="text/javascript">
			$(function(){
				$('#dg').datagrid({
					url:'${path}/terminal/datagrid',
					striped:false,//斑马线效果
					onCheck : function (rowIndex, rowData) {//限制只能勾选一行
						var dg = $('#dg').datagrid('getChecked');
						for(var i=0;i<dg.length;i++){
							var data=dg[i];
							var index=$('#dg').datagrid('getRowIndex',data);//得到勾选的行
							if(rowIndex != index){
								$('#dg').datagrid('uncheckRow',index);
							}
						}
					},
				    columns:[[
				        {field:'term_addr',title:'逻辑地址',checkbox:true},
				        {field:'term_id',title:'设备ID',width:100},
				        {field:'term_name',title:'设备名称',width:100}
				    ]]
				});
				
				
				//切换按钮
				$('#check').mouseover(function(){
					$('#nav').show();
				}).mouseout(function(){
					$('#nav').hide();
				});
				$('#nav li').click(function(){
					$('#nav li').removeClass('active');
					$(this).addClass('active');
					$('#check strong').text($(this).text());
					$('#table').html('');
				});
				//点击切换表格数据
				$('#dianbiao').click(function(){
					arr_id = [];
					$('#dg').datagrid({
						url:'${path}/meter/datagrid',
				    	columns:[[
				        	{field:'mp_no',title:'设备编号',checkbox:true},
				        	{field:'mp_id',title:'设备ID'},
				       	 	{field:'mp_name',title:'设备名称',width:200}
				    	]]
					}).datagrid('load');
				});
				var arr_id = [];
				$('#caijiqi').click(function(){
					arr_id = [];
					$('#dg').datagrid({
						url:'${path}/terminal/datagrid',
				    	columns:[[
				        	{field:'term_addr',title:'逻辑地址',checkbox:true},
				        	{field:'term_id',title:'设备ID',width:100},
				        	{field:'term_name',title:'设备名称',width:100}
				    	]]
					}).datagrid('load');
				});
				//探索时清空右边的表格数据
				$('#search_button').click(function(){
					search_fn();
					$('#table').html('');
				});
				//召测通信参数 发送ID到前置机接收数据
				$('#button_tx').click(function(){
					$('#table td').html('');
					button_order("${path}/getWebStationNetParam");
				});
			});
			//搜索
			function search_fn(){
				if($.trim($('#search').val()) != ''){
					$('#dg').datagrid('load',{
						search:$.trim($('#search').val()),
					});
				}else {//搜索框全部为空时 搜索全部
					$('#dg').datagrid('load',{search_val_1:''});
				}
			}
			//按钮操作
			function button_order(url){
				arr_id = [];
				var dg = $('#dg').datagrid('getChecked');
				var rows = $('#dg').datagrid('getData');
				if(dg.length > 0){
					for(var i=0;i<dg.length;i++){
						var data=dg[i];
						var index=$('#dg').datagrid('getRowIndex',data);//得到勾选的行
						//判断表格数据是集中器还是电表
						if($('#check strong').text() == '集中器'){
							arr_id.push(rows.rows[index].term_id);
						}else if($('#check strong').text() == '电表'){
							arr_id.push(rows.rows[index].mp_id);
						}
					}
					$.ajax({
						url:url,//集中器和电表使用同五个地址
						type:'post',
						data:{
							id:arr_id.join(',')
						},
						beforeSend:function(jqXHR,setting){
				    		$.messager.progress({text:'正在加载...'});
				    	},
				    	success:function(data){
				    		$.messager.progress('close');
				    		if(data){
				    			var strJSON = $.parseJSON(data);//得到的JSON
								var json = new Function("return" + strJSON)();//转换后的JSON对象
								create_table(json);//生成表格的方法
				    		}else {
								$.messager.alert('操作失败提示','找不到地址或网络错误,请刷新重试!','warning');
							}
				    	},
						error:function(){
							$.messager.progress('close');
							$.messager.alert('操作失败提示','找不到地址或网络错误,请刷新重试!','warning');
						}
					});
				}
			}
			//点击召测生成表格
			function create_table(json){
				var rows = $('#dg').datagrid('getData');
				$('#table').css('left',($('.panel.datagrid').width() - 2)+'px');
				//$('#table td').css('height',($('.datagrid-btable td').height()-1)+'px');
				//生成多少行
				for (var i = 0; i < rows.rows.length + 1; i++) {
					$('#table').append('<tr></tr>');
				}
				var table = $('#table').get(0);
				//第一行表头
				for(var i=0;i<json.dataList[0].itemList.length;i++){
					if($(table.tBodies[0].rows[0]).find('th').size() < json.dataList[0].itemList.length){
						$(table.tBodies[0].rows[0]).append('<th class="first_td" style="font-weight: normal;">'+json.dataList[0].itemList[i].name+'</th>');
					}
				}
				// 行内生成td
				for (var i = 1; i < rows.rows.length + 1; i++) {
					for (var j = 0; j < json.dataList[0].itemList.length; j++) {
						if($('#table').find('td').size() < json.dataList[0].itemList.length * rows.rows.length){
							$(table.tBodies[0].rows[i]).append("<td style=height:"+($('.datagrid-btable td').height())+"px;!important></td>");
						}
					}
				}
				var dg = $('#dg').datagrid('getChecked');
				for(var i=0;i<dg.length;i++){
					var data=dg[i];
					var index=$('#dg').datagrid('getRowIndex',data);//得到勾选的行
					try{
						if(rows.rows[index].term_id == json.dataList[i].rtua){//判断ID相同就把数据填充在生成的表格后面
							for (var j = 0; j < json.dataList[i].itemList.length; j++) {
								$(table.tBodies[0].rows[index+1].cells[j]).text(json.dataList[i].itemList[j].val);
							}
						}else if(rows.rows[index].mp_id == json.dataList[i].rtua){
							for (var j = 0; j < json.dataList[i].itemList.length; j++) {
								$(table.tBodies[0].rows[index+1].cells[j]).text(json.dataList[i].itemList[j].val);
							}
						}
					}catch(error){}
				}
			}
		</script>
	</body>
</html>
