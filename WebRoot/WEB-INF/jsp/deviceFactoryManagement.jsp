<!DOCTYPE html>
<html>
	<head>
		<title>设备厂家管理</title>
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
				<a href="#" id="add" class="easyui-linkbutton" data-options=" iconCls:'icon-add', plain:true ">新增设备厂家</a>
				<a href="#" onclick="obj.edit()" class="easyui-linkbutton" data-options=" iconCls:'icon-edit', plain:true ">编辑设备厂家(黄色背景行)</a>
				<a href="#" onclick="obj.button_delete()" class="easyui-linkbutton" data-options=" iconCls:'icon-remove', plain:true">删除设备厂家(所有勾选行)</a>
			</div>
			<!-- 搜索功能 -->
			<div class="input_search">
				<label for="search_manufacturer_no">厂家编号<input type="text" id="search_manufacturer_no" name="search_manufacturer_no"  class="input"></label>
				<label for="search_manufacturer_name">厂家名称<input type="text" id="search_manufacturer_name" name="search_manufacturer_name" class="input"></label>
				<label for="search_start_time" style="margin-right:15px">供货时间时间<input type="text" id="search_start_time" name="search_start_time" class="easyui-datetimebox" data-options="editable:false"></label>
				<a href="#" id="search" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
			</div>
			<!-- 内容区右键功能 -->
			<div id="rightHand" class="easyui-menu" style="width:120px">
				<div id="right_add" iconCls="icon-add" style="height:25px;">新增设备厂家</div>
				<div onclick="obj.edit()" iconCls="icon-edit" style="height:25px;">编辑设备厂家</div>
				<div onclick="obj.right_delete()" iconCls="icon-remove" style="height:25px;">删除设备厂家</div>
			</div>
		</div>
		<!-- 新增表单 修改数据也使用这个表单 -->
		<form id="add_edit_form" action="">
			<div class="add_edit_form_left">
				<div><label for="manufacturer_no">厂家编号</label><input placeholder="此项必填"  class="easyui-validatebox input" required="true" type="text" id="manufacturer_no" name="manufacturer_no"></div>
				<div><label for="manufacturer_name">厂家名称</label><input type="text" id="manufacturer_name" name="manufacturer_name" class="easyui-validatebox input" required="true"></div>
				<div><label for="address">厂家地址</label><input type="text" id="address" name="address" class="easyui-validatebox input" required="true"></div>
				<div><label for="man">厂家联系人</label><input type="text" id="man" name="man" class="easyui-validatebox input" required="true"></div>
				<div><label for="phoneno">联系人电话</label><input type="text" id="phoneno" name="phoneno" class="easyui-validatebox input" required="true" validtype="phone"></div>
				<div id="date"><label for="start_time">厂家供货开始时间</label><input type="text" id="start_time" name="start_time" data-options="editable:false" class="input date"></div>
				<div><label for="terminal_number">厂家终端数量</label><input type="text" class="easyui-validatebox input" required="true" validtype="number_" id="terminal_number" name="terminal_number"></div>
			</div>
		</form>
		<!-- easyui-datagrid 通用设置和方法-->
		<script src="${path}/js/datagrid.js" type="text/javascript"></script>
		<script type="text/javascript">
			//DOM后加载数据
			$(function(){
				$('#dg').datagrid({
				    url:'${path}/deviceFactoryManagement/datagrid',
					columns:[[
				        {field:'manufacturer_id',title:'厂家ID',sortable:true,checkbox:true,width:100},
				        {field:'manufacturer_no',title:'厂家编号',sortable:true,width:100},
				        {field:'manufacturer_name',title:'厂家名称',sortable:true,width:100},
				        {field:'address',title:'厂家地址',sortable:true,width:100},
				        {field:'start_time',title:'厂家供货开始时间',sortable:true,width:100},
				        {field:'man',title:'厂家联系人',sortable:true,width:100},
				        {field:'phoneno',title:'厂家联系人电话',sortable:true,width:100},
				        {field:'terminal_number',title:'厂家终端数量',sortable:true,width:100},
				    ]],
				    onRowContextMenu:function (e,rowIndex,rowData) {//显示右键功能
						e.preventDefault();
						$('#rightHand').menu('show',{
							left:e.pageX,
							top:e.pageY,
							hideOnUnhover:false
						});
						obj.right_delete = function () {
							$.messager.confirm('删除提示','是否删除设备厂家名称为 <strong>'+rowData.manufacturer_name+'</strong> 的数据?',function (flag) {
								if (flag) {
									all_obj.delete_ajax('${path}/deviceFactoryManagement/delete',rowData.manufacturer_id);//右键获取ID上传删除
								}
							});
						};
					},
				});
				// 增加 修改 计量点表单
			    $('#add,#right_add').click(function(){
			    	all_obj.add('新增设备厂家','${path}/deviceFactoryManagement/add');
			    });
			    //搜索
			    $('#search').click(function(){
				   	if($('#search_manufacturer_no').val() != '' || $('#search_manufacturer_name').val() != ''  || $('input[name="search_start_time"]').val() != ''){//判断
						$('#dg').datagrid('load',{
							search_val_1:$.trim($('#search_manufacturer_no').val()),
							search_val_2:$.trim($('#search_manufacturer_name').val()),
							search_date:$('input[name="search_start_time"]').val()
						});
					}else {//搜索框全部为空时 搜索全部
						$('#dg').datagrid('load',{search_val_1:''});
					}
				});
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
							$($('#add_edit_form').find('.add_edit_form_left').find('div').get(0)).before('<div class="id"><label for="manufacturer_id">设备厂家ID<span class=read>(只读)</span></label><input type="text" id="manufacturer_id" name="manufacturer_id" class="input" readonly="true"></div>');
							$('.id').find('.input').css({background:'#ccc',color:'#575757'});
						}
						//编辑时获取行数据
						$('#add_edit_form #manufacturer_id').val(rows[0].manufacturer_id);//ID
						$('#add_edit_form #manufacturer_no').val(rows[0].manufacturer_no);//设备厂家编号
						$('#add_edit_form #manufacturer_name').val(rows[0].manufacturer_name);//厂家名称
						$('#add_edit_form #address').val(rows[0].address);//设备厂家名称
						$('#add_edit_form #start_time').datetimebox('setValue',rows[0].start_time);//供货时间 
						$('#add_edit_form #man').val(rows[0].man);//厂家联系人
						$('#add_edit_form #phoneno').val(rows[0].phoneno);//联系人电话
						$('#add_edit_form #terminal_number').val(rows[0].terminal_number);//设备厂家终端号
						//获取编辑资料
						all_obj.update('编辑设备厂家资料','${path}/deviceFactoryManagement/update');//上传编辑资料
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
									select_id.push(checked[i].manufacturer_id);//取ID
								}
								all_obj.delete_ajax('${path}/deviceFactoryManagement/delete',select_id.join(','));//上传ID删除
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