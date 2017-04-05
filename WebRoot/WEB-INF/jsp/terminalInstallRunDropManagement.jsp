<!DOCTYPE html>
<html>
	<head>
		<title>设备安装维护管理</title>
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
			<div>
				<a href="#" id="add" class="easyui-linkbutton" data-options=" iconCls:'icon-add', plain:true ">新增设备安装维护</a>
				<a href="#" onclick="obj.edit()" class="easyui-linkbutton" data-options=" iconCls:'icon-edit', plain:true ">编辑设备安装维护(黄色背景行)</a>
				<a href="#" onclick="obj.button_delete()" class="easyui-linkbutton" data-options=" iconCls:'icon-remove', plain:true">删除设备安装维护(所有勾选行)</a>
			</div>
			<!-- 搜索功能 -->
			<div class="input_search">
				<label for="search_mp_no">设备安装维护编号<input type="text" id="search_mp_no" name="search_mp_no" class="input"></label>
			<!-- 	<label for="search_create_man">创建人<input type="text" id="search_create_man" name="search_create_man" class="input"></label>
				<label for="search_create_date"  style="margin-right:15px">创建时间<input type="text" id="search_create_date" class="easyui-datebox" name="search_create_date" data-options="editable:false"></label> -->
				<a href="#" id="search" class="easyui-linkbutton" iconCls="icon-search" >搜索</a>
			</div>
			<!-- 内容区右键功能 -->
			<div id="rightHand" class="easyui-menu" style="width:130px;">
				<div id="right_add" iconCls="icon-add" style="height:25px;">新增设备安装维护</div>
				<div onclick="obj.edit()" iconCls="icon-edit" style="height:25px;">编辑设备安装维护</div>
				<div onclick="obj.right_delete()" iconCls="icon-remove" style="height:25px;">删除设备安装维护</div>
			</div>
		</div>
		<!-- 新增表单 修改数据也使用这个表单  -->
		<form id="add_edit_form" action="">
			<div class="add_edit_form_left">
				<!-- <div><label for="terminal_ormeterid">设备ID</label><input class="easyui-validatebox input" required="true" type="text" id="terminal_ormeterid" name="terminal_ormeterid" placeholder="此项必填"></div> -->
				<div><label for="equipment_name">设备名称</label><input class="easyui-validatebox input" required="true" type="text" id="equipment_name" name="equipment_name" placeholder="此项必填"></div>
				<div><label for="terminal_asset_no">设备资产编号</label><input type="text" id="customer_type" name="customer_type" class="input"></div>
				<div><label for="terminal_model">设备型号</label><input type="text" id="terminal_model" name="terminal_model" class="input"></div>
				<div><label for="terminal_manufacturer">设备厂家</label><input type="text" id="terminal_manufacturer" name="terminal_manufacturer" class="input"></div>
				<div><label for="install_date">安装日期</label><input type="text" data-options="editable:false" id="install_date" name="install_date" class="input date"></div>
				<div><label for="install_man">安装人</label><input type="text" id="install_man" name="install_man" class="input"></div>
				<div><label for="install_add">安装地点</label><input type="text" id="install_add" name="install_add" class="input"></div>
				<div><label for="operation_date">投运日期</label><input type="text" data-options="editable:false" id="operation_date" name="operation_date" class="input date"></div>
			</div>
			<div class="add_edit_form_right">	
				<div><label for="operation_man">投运操作人</label><input type="text" id="operation_man" name="operation_man" class="input"></div>
				<div><label for="operation_explain">投运说明</label><input type="text" id="operation_explain" name="operation_explain" class="input"></div>
				<div><label for="operation_time">投运时长(天)</label><input type="text" id="operation_time" name="operation_time" class="input"></div>
				<div><label for="address">消退日期</label><input type="text" data-options="editable:false" id="address" name="address" class="input date"></div>
				<div><label for="fade_man">消退操作人</label><input type="text"  data-options="editable:false" id="fade_man" name="fade_man" data-options="editable:false" class="input" ></div>
				<div><label for="fade_reason">消退原因</label><input type="text" id="fade_reason" name="fade_reason" class="input"></div>
				<div>
					<label for="terminal_state">运行状态</label>
					<input type="text" id="terminal_state" name="terminal_state" class="input">
				</div>
			</div>
		</form>
		<!-- easyui-datagrid 通用设置和方法-->
		<script src="${path}/js/datagrid.js" type="text/javascript"></script>
		<script type="text/javascript">
			//DOM后加载数据
			$(function(){
				$('#dg').datagrid({
				    url:'${path}/terminalInstallRunDropManagement/datagrid',
				    columns:[[
				        {field:'terminal_ormeterid',title:'设备ID',sortable:true,width:100,checkbox:true},
					    {field:'equipment_name',title:'设备名称',sortable:true,width:100},
					    {field:'terminal_asset_no',title:'设备资产编号',sortable:true,width:100},
					    {field:'terminal_model',title:'设备型号',sortable:true,width:100},
					    {field:'terminal_manufacturer',title:'设备厂家',sortable:true,width:100},  
					    {field:'install_date',title:'安装日期',sortable:true,width:100},
					    {field:'install_man',title:'安装人',sortable:true,width:100},
					    {field:'install_add',title:'安装地点',sortable:true,width:100},
					    {field:'operation_date',title:'投运日期',sortable:true,width:100},
					    {field:'operation_man',title:'投运操作人',sortable:true,width:100},   
					  	{field:'operation_explain',title:'投运说明',sortable:true,width:100},
					    {field:'operation_time',title:'投运时长',sortable:true,width:100},
					    {field:'address',title:'消退日期',sortable:true,width:100},
					    {field:'fade_man',title:'消退操作人',sortable:true,width:100},
					    {field:'fade_reason',title:'消退原因',sortable:true,width:100},
					    {field:'terminal_state',title:'运行状态',sortable:true,width:100,formatter:function(row,index,value){
					    	if(row==0){
					    	return "安装阶段";
					    	}else if(row ==1){
					    	return "投运阶段";
					    	}else if(row ==2){
					    	return "消退阶段";
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
							$.messager.confirm('删除提示','是否删除设备安装维护名称为 <strong>'+rowData.customer_name+'</strong> 的数据?',function (flag) {
								if (flag) {
									all_obj.delete_ajax('${path}/terminalInstallRunDropManagement/delete',rowData.terminal_ormeterid);//右键获取ID上传删除
								}
							});
						}
					},
				});
				
				
				//下拉框设置
			    $('#terminal_state').combobox({valueField:'id',textField:'text',height:'30',editable:false,data:[{"id":"00","text":"00：安装阶段"},{"id":"01","text":"01: 投运阶段"},{"id":"02","text":"02:消退阶段"}]});
				//按钮新增和右键新增
			    $('#add,#right_add').click(function(){
			    	all_obj.add('新增设备安装维护','${path}/terminalInstallRunDropManagement/add');
			    });
			    //搜索
			    $('#search').click(function(){
			    	if($('#search_mp_no').val() != '' || $('#search_create_man').val() != '' || $('input[name="search_create_date"]').val() != ''){//判断
						all_obj.search('#search_mp_no','#search_create_man','input[name="search_create_date"]');//传ID 时间name
					}else {//搜索框全部为空时 搜索全部
						$('#dg').datagrid('load',{search_val_1:''});
					}
			    });
			    //表单验证
		       /* $('#tel').validatebox({validType:'phone'});
		        $('#emailAddr').validatebox({validType:'email'});
		        $('#date .validatebox-text').validatebox({required:true});*/
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
							$($('#add_edit_form').find('.add_edit_form_left').find('div').get(0)).before('<div class="id"><label for="customer_id">设备ID<span class=read>(只读)</span></label><input type="text" id="terminal_ormeterid" name="terminal_ormeterid" class="input" readonly="true"></div>');
							$('.id').find('.input').css({background:'#ccc',color:'#575757'});
						}
						//编辑时获取行数据
						$('#add_edit_form #terminal_ormeterid').val(rows[0].terminal_ormeterid);
						$('#add_edit_form #equipment_name').val(rows[0].equipment_name);
						$('#add_edit_form #terminal_asset_no').val(rows[0].terminal_asset_no);
						$('#add_edit_form #terminal_model').val(rows[0].terminal_model);
						$('#add_edit_form #terminal_manufacturer').val(rows[0].terminal_manufacturer);	
						$('#add_edit_form .date').eq(0).datetimebox('setValue',rows[0].install_date);
						$('#add_edit_form input[name="install_date"]').val(rows[0].install_date);
						$('#add_edit_form #install_man').val(rows[0].install_man);
						$('#add_edit_form #install_add').val(rows[0].install_add);
						$('#add_edit_form .date').eq(1).datetimebox('setValue',rows[0].operation_date);
						$('#add_edit_form input[name="operation_date"]').val(rows[0].operation_date);
						$('#add_edit_form #operation_man').val(rows[0].operation_man);
						$('#add_edit_form #operation_explain').val(rows[0].operation_explain);
						$('#add_edit_form #operation_time').val(rows[0].operation_time);
						$('#add_edit_form .date').eq(2).datetimebox('setValue',rows[0].address);
						$('#add_edit_form input[name="address"]').val(rows[0].address);
						$('#add_edit_form #fade_man').val(rows[0].fade_man);
						$('#add_edit_form #fade_reason').val(rows[0].fade_reason);
						$('#add_edit_form #terminal_state').combobox('setValue',rows[0].terminal_state);
						all_obj.update('编辑设备安装维护资料','${path}/terminalInstallRunDropManagement/update');//上传编辑资料
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
								for (var i = 0; i < checked.length;i++) {
									select_id.push(checked[i].terminal_ormeterid);//取ID
								}
								all_obj.delete_ajax('${path}/terminalInstallRunDropManagement/delete',select_id.join(','));//上传ID删除
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