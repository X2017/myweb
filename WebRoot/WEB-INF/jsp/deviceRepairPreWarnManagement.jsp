<!DOCTYPE html>
<html>
	<head>
		<title>设备维护预警管理</title>
		<meta charset="utf-8">
		<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
		<%@ include file="/WEB-INF/jsp/include/taglib.jsp"%>
		<%@ include file="/WEB-INF/jsp/include/easyui.jsp"%>
		<!-- EasyUI datagrid表格和表单CSS-->
		<link href="${path}/js/datagridForm.css" rel="stylesheet" type="text/css"/>
	</head>
	<body class="easyui-layout" onselectstart="javascript:return false;">
		<table id="dg" class="easyui-datagrid" toolbar="#toolbar"></table>
		<!-- 功能 -->
		<div id="tool">
			<!-- <div>
				<a href="#" id="add" class="easyui-linkbutton" data-options=" iconCls:'icon-add', plain:true ">新增设备维护预警</a>
				<a href="#" onclick="obj.edit()" class="easyui-linkbutton" data-options=" iconCls:'icon-edit', plain:true ">编辑设备维护预警(黄色背景行)</a>
				<a href="#" onclick="obj.button_delete()" class="easyui-linkbutton" data-options=" iconCls:'icon-remove', plain:true">删除设备维护预警(所有勾选行)</a>
			</div> -->
			<!-- 搜索功能 -->
			<div class="input_search">
				<label for="search_equipment_name">设备名称<input type="text" id="search_equipment_name" name="search_equipment_name" class="input"></label>
				<!-- <label for="search_create_man">创建人<input type="text" id="search_create_man" name="search_create_man" class="input"></label>
				<label for="search_create_date"  style="margin-right:15px">创建时间<input type="text" id="search_create_date" class="easyui-datebox" name="search_create_date" data-options="editable:false"></label> -->
				<a href="#" id="search" class="easyui-linkbutton" iconCls="icon-search" >搜索</a>
			</div>
			<!-- 内容区右键功能 -->
			<!-- <div id="rightHand" class="easyui-menu" style="width:130px;">
				<div id="right_add" iconCls="icon-add" style="height:25px;">新增设备维护预警</div>
				<div onclick="obj.edit()" iconCls="icon-edit" style="height:25px;">编辑设备维护预警</div>
				<div onclick="obj.right_delete()" iconCls="icon-remove" style="height:25px;">删除设备维护预警</div>
			</div> -->
		</div>
		<!-- 新增表单 修改数据也使用这个表单  -->
		<form id="add_edit_form" action="">
			<div class="add_edit_form_left">
				<div><label for="terminal_ormeterid">设备ID</label><input type="text" id="terminal_ormeterid" name="terminal_ormeterid" class="input"></div>
				<div><label for="equipment_name">设备名称</label><input type="text" id="equipment_name" name="equipment_name" class="input"></div>
				<div><label for="customer_id">客户ID</label><input type="text" id="customer_id" name="customer_id" class="input"></div>
				<div id="record_time"><label for="start_time">预警时间</label><input type="text" id="record_time" name="record_time" data-options="editable:false" class="input date"></div>
				<div><label for="alarm_code">告警编号</label><input type="text" id="alarm_code" name="alarm_code" class="input"></div>
				<div><label for="alarm_name">告警名称</label><input type="text" id="alarm_name" name="alarm_name" class="input"></div>
				<div><label for="alarm_way">预警方式</label><input type="text" id="alarm_way" name="alarm_way" class="input"></div>
				<div>
					<label for="alarm_state">预警状态</label>
					<input type="text" id="alarm_state" name="alarm_state" class="input">
				</div>
			</div>
		</form>
		<!-- easyui-datagrid 通用设置和方法-->
		<script src="${path}/js/datagrid.js" type="text/javascript"></script>
		<script type="text/javascript">
			//DOM后加载数据
			$(function(){
				$('#dg').datagrid({
				    url:'${path}/deviceRepairPreWarnManagement/datagrid',
				    columns:[[
				        {field:'id',title:'设备维护预警ID',sortable:true,width:100,checkbox:true},
					    {field:'terminal_ormeterid',title:'设备ID',sortable:true,width:100},
					    {field:'equipment_name',title:'设备名称',sortable:true,width:100},
					    {field:'customer_name',title:'所属客户',sortable:true,width:100},
					    {field:'record_time',title:'记录时间',sortable:true,width:100},
					    {field:'alarm_code',title:'告警编号',sortable:true,width:100},
					    {field:'alarm_name',title:'告警名称',sortable:true,width:100},
					    {field:'alarm_way',title:'预警方式(1短信、2声音、3邮件、4底部滚动)',sortable:true,width:110},
					    {field:'alarm_state',title:'预警状态',sortable:true,width:100,formatter:function(row,index,value){
					    	if(row==1){
					    		return "正在运行";
					    	}else if(row ==0){
					    		return "停止运行";
					    	}
					    }},
				    ]],
				    onRowContextMenu:function (e,rowIndex,rowData) {//显示右键功能
						e.preventDefault();
						$('#rightHand').menu('show',{
							left:e.pageX,
							top:e.pageY
						});
						obj.right_delete = function () {
							$.messager.confirm('删除提示','是否删除设备维护预警名称为 <strong>'+rowData.alarm_name+'</strong> 的数据?',function (flag) {
								if (flag) {
									all_obj.delete_ajax('${path}/deviceRepairPreWarnManagement/delete',rowData.id);//右键获取ID上传删除
								}
							});
						}
					},
				});
				
				
				//按钮新增和右键新增
			    $('#add,#right_add').click(function(){
			    	all_obj.add('新增设备维护预警','${path}/deviceRepairPreWarnManagement/add');
			    });
			    //搜索
			    $('#search').click(function(){
			    	if($('#search_equipment_name').val() != '' ){//判断
						$('#dg').datagrid('load',{
						search_val_1:$.trim($('#search_equipment_name').val())
						});
					}else {//搜索框全部为空时 搜索全部
						$('#dg').datagrid('load',{search_val_1:''});
					}
			    });
			    $('#alarm_way').combobox({valueField:'id',textField:'text',height:'30',editable:false,data:[{"id":"01","text":"1:短信"},{"id":"02","text":"02:声音"},{"id":"03","text":"03:邮件"},{"id":"04","text":"04:底部滚动"}]});
			    $('#alarm_state').combobox({valueField:'id',textField:'text',height:'30',editable:false,data:[{"id":"01","text":"1:正在运行"},{"id":"0","text":"0:停止"}]});
			    //表单验证
			});
			//编辑和删除方法
			var obj={
				//打开表 获取修改数据
				edit:function(){
					var rows = $('#dg').datagrid('getSelections');
					if (rows.length > 1) {
						$.messager.alert('编辑提示','只能选择一条数据编辑!','warning');
					}else if (rows.length == 1){
						if($('#add_edit_form').find('.id').size() <= 0){
							$($('#add_edit_form').find('.add_edit_form_left').find('div').get(0)).before('<div class="id"><label for="deviceRepairPreWarnManagement_id">维护预警ID<span class=read>(只读)</span></label><input type="text" id="id" name="id" class="input" readonly="true"></div>');
							$('.id').find('.input').css({background:'#ccc',color:'#575757'});
						}
						//编辑时获取行数据
						$('#add_edit_form #id').val(rows[0].id);
						$('#add_edit_form #terminal_ormeterid').val(rows[0].terminal_ormeterid);
					    $('#add_edit_form #equipment_name').val(rows[0].equipment_name);
					    $('#add_edit_form #customer_id').val(rows[0].customer_id);
						$('#add_edit_form .date').datetimebox('setValue',rows[0].record_time);//日期    
						$('#add_edit_form input[name=record_time]').val(rows[0].record_time);//日期    
					    $('#add_edit_form #alarm_code').val(rows[0].alarm_code);
					    $('#add_edit_form #alarm_name').val(rows[0].alarm_name);
					    $('#add_edit_form #alarm_way').val(rows[0].alarm_way);
					    $('#add_edit_form #alarm_state').combobox('setValue',rows[0].alarm_state);
						all_obj.update('编辑设备维护预警资料','${path}/deviceRepairPreWarnManagement/update');//上传编辑资料
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
									select_id.push(checked[i].id);//取ID
								}
								all_obj.delete_ajax('${path}/deviceRepairPreWarnManagement/delete',select_id.join(','));//上传ID删除
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