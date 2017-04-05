<!DOCTYPE html>
<html>
	<head>
		<title>前置机管理</title>
		<meta charset="utf-8">
		<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
		<%@ include file="/WEB-INF/jsp/include/taglib.jsp"%>
		<%@ include file="/WEB-INF/jsp/include/easyui.jsp"%>
		<!-- EasyUI datagrid表格和表单CSS-->
		<link href="${path}/js/datagridForm.css" rel="stylesheet" type="text/css"/>
		<style type="text/css">
			#add_edit_form label{width:130px;}
		</style>
	</head>
	<body class="easyui-layout" onselectstart="javascript:return false;">
		<table id="dg" class="easyui-datagrid" toolbar="#toolbar"></table>
		<!-- 功能 -->
		<div id="tool">
			<div>
				<a href="#" id="add" class="easyui-linkbutton" data-options=" iconCls:'icon-add', plain:true ">新增前置机</a>
				<a href="#" onclick="obj.edit()" class="easyui-linkbutton" data-options=" iconCls:'icon-edit', plain:true ">编辑前置机(黄色背景行)</a>
				<a href="#" onclick="obj.button_delete()" class="easyui-linkbutton" data-options=" iconCls:'icon-remove', plain:true">删除前置机(所有勾选行)</a>
			</div>
			<!-- 搜索功能 -->
			<div class="input_search">
				<!-- <label for="search_front_code">前置机编码<input type="text" id="search_front_code" name="search_front_code" class="input"></label> -->
				<label for="search_front_name">前置机名称<input type="text" id="search_front_name" name="search_front_name" class="input"></label>
				<a href="#" id="search" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
			</div>
			<!-- 内容区右键功能 -->
			<div id="rightHand" class="easyui-menu" style="width:120px">
				<div id="right_add" iconCls="icon-add" style="height:25px;">新增前置机</div>
				<div onclick="obj.edit()" iconCls="icon-edit" style="height:25px;">编辑前置机</div>
				<div onclick="obj.right_delete()" iconCls="icon-remove" style="height:25px;">删除前置机</div>
			</div>
		</div>
		<!-- 新增表单 修改数据也使用这个表单 -->
		<form id="add_edit_form" action="">
			<div class="add_edit_form_left">
				<!-- <div><label for="front_code">前置机编号</label><input placeholder="此项必填" class="easyui-validatebox input" required="true" type="text" id="front_code" name="front_code"></div> -->
				<input type="hidden" id="front_id" name="front_id" class="easyui-validatebox input" >
				<div><label for="front_name">前置机名称</label><input type="text" id="front_name" name="front_name" class="easyui-validatebox input" required="true"></div>
				<div><label for="common_log_ip">常用登录IP</label><input type="text" id="common_log_ip" name="common_log_ip" class="easyui-validatebox input" required="true" validtype="ip"></div>
				<div><label for="webserver_ip">web服务器IP</label><input type="text" id="webserver_ip" name="webserver_ip" class="easyui-validatebox input" required="true" validtype="ip"></div>
				<div><label for="webserver_port">web服务器端口号</label><input type="text" id="webserver_port" name="webserver_port" class="easyui-validatebox input" required="true" validtype="number_"></div>
				<div id="date"><label for="last_date">最后一次登录时间</label><input type="text" id="last_date" name="last_date" data-options="editable:false" class="input date"></div>
				<div>
					<label for="state">登陆状态</label>
					<input type="text" id="state" name="state" class="input">
				</div>
			</div>
		</form>
		<!-- easyui-datagrid 通用设置和方法-->
		<script src="${path}/js/datagrid.js" type="text/javascript"></script>
		<script type="text/javascript">
			//DOM后加载数据
			$(function(){
				$('#dg').datagrid({
				    url:'${path}/frontMachine/datagrid',
					columns:[[
				        {field:'front_id',title:'前置机ID',sortable:true,checkbox:true,width:100},
				        {field:'front_code',title:'前置机编码',sortable:true,width:100},
				        {field:'front_name',title:'前置机名称',sortable:true,width:100},
				        {field:'common_log_ip',title:'常用登录IP',sortable:true,width:100},
				        {field:'webserver_ip',title:'web服务器IP',sortable:true,width:100},
				        {field:'webserver_port',title:'web服务器端口号',sortable:true,width:100},
				        {field:'last_date',title:'最后一次登录时间',sortable:true,width:100},
				        {field:'state',title:'登陆状态',sortable:true,width:100,formatter:function(row,index,value){
					    	if(row==0){
					    	return "离线";
					    	}else if(row ==1){
					    	return "在线";
					    	}
					    }},
				    ]],
				    onRowContextMenu:function (e,rowIndex,rowData) {//显示右键功能
						e.preventDefault();
						$('#rightHand').menu('show',{
							left:e.pageX,
							top:e.pageY,
							hideOnUnhover:false
						});
						obj.right_delete = function () {
							$.messager.confirm('删除提示','是否删除前置机名称为 <strong>'+rowData.front_name+'</strong> 的数据?',function (flag) {
								if (flag) {
									all_obj.delete_ajax('${path}/frontMachine/delete',rowData.front_id);//右键获取ID上传删除
								}
							});
						};
					},
				});
				// 增加 修改 计量点表单
			    $('#add,#right_add').click(function(){
			    	all_obj.add('新增前置机','${path}/frontMachine/add');
			    });
			    //搜索
			    $('#search').click(function(){
				   	if($('#search_front_code').val() != '' || $('#search_front_name').val() != ''  || $('input[name="search_last_date"]').val() != ''){//判断
						$('#dg').datagrid('load',{
							search_val_1:$.trim($('#search_front_code').val()),
							search_val_2:$.trim($('#search_front_name').val()),
							search_date:$('input[name="search_last_date"]').val()
						});
					}else {//搜索框全部为空时 搜索全部
						$('#dg').datagrid('load',{search_val_1:''});
					}
			    });
			    $('#state').combobox({valueField:'id',textField:'text',height:'30',editable:false,data:[{"id":01,"text":"在线"},{"id":0,"text":"离线"}]});
			    $('#date .validatebox-text').validatebox({required:true});
			});
			//编辑和删除方法
			var obj={
				//修改采集器表单
				edit:function(){
					var rows = $('#dg').datagrid('getSelections');
					console.log(rows);
					if (rows.length > 1) {
						$.messager.alert('编辑提示','只能选择一条数据编辑!','warning');
					}else if (rows.length == 1){
						if($('#add_edit_form').find('.id').size() <= 0){
							$($('#add_edit_form').find('.add_edit_form_left').find('div').get(0)).before('<div class="id"><label for="front_code">前置机编号<span class=read>(只读)</span></label><input type="text" id="front_code" name="front_code" class="input" readonly="true"></div>');
							$('.id').find('.input').css({background:'#ccc',color:'#575757'});
						}
						//编辑时获取行数据
						$('#add_edit_form #front_id').val(rows[0].front_id);
						$('#add_edit_form #front_code').val(rows[0].front_code);//前置机编码
						$('#add_edit_form #front_name').val(rows[0].front_name);//前置机名称
						$('#add_edit_form #common_log_ip').val(rows[0].common_log_ip);//常用登陆IP
						$('#add_edit_form #last_date').datetimebox('setValue',rows[0].last_date);//最后登录时间
						$('#add_edit_form #webserver_port').val(rows[0].webserver_port);//web服务器端口号
						$('#add_edit_form #state').val(rows[0].state);//登录状态
						$('#add_edit_form #webserver_ip').val(rows[0].webserver_ip);//web服务器IP
						//获取编辑资料
						all_obj.update('编辑前置机资料','${path}/frontMachine/update');//上传编辑资料
					}else if (rows.length == 0) {
			    		$.messager.alert('错误提示','请选择要编辑的数据,只能选择一条!','warning');
					}
				},
				//按钮删除
				button_delete:function () {
					var checked = $('#dg').datagrid('getChecked');//getSelections只能得到选中的行! getChecked能得到全部勾行的行!
					if (checked.length > 0) {
						$.messager.confirm('数据删除提示','是否删除选中的数据?',function (flag) {
							if (flag) {
								var select_id = [];
								for (var i = 0; i < checked.length; i++) {
									select_id.push(checked[i].front_id);//取ID
								}
								all_obj.delete_ajax('${path}/frontMachine/delete',select_id.join(','));//上传ID删除
							}
						});
					}else {
						$.messager.alert('删除提示','<strong style="font-size:15px;">请选择要删除的数据</strong>','warning');
					}
				},
			};
		</script>
	</body>
</html>