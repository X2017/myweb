<!DOCTYPE html>
<html>
	<head>
		<title>设备维保记录管理</title>
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
				<a href="#" id="add" class="easyui-linkbutton" data-options=" iconCls:'icon-add', plain:true ">新增维保记录</a>
				<a href="#" onclick="obj.edit()" class="easyui-linkbutton" data-options=" iconCls:'icon-edit', plain:true ">编辑维保记录(黄色背景行)</a>
				<a href="#" onclick="obj.button_delete()" class="easyui-linkbutton" data-options=" iconCls:'icon-remove', plain:true">删除维保记录(所有勾选行)</a>
			</div>
			<!-- 搜索功能 -->
			<div class="input_search">
				<label for="search_equipment_name">设备名称<input type="text" id="search_equipment_name" name="search_equipment_name" class="input"></label>
				<!-- <label for="search_maintain_man">维修人<input type="text" id="search_maintain_man" name="search_maintain_man"  class="input"></label>
				<label for="search_maintain_date" style="margin-right:15px">供货时间时间<input type="text" id="search_maintain_date" name="search_maintain_date" class="easyui-datetimebox" data-options="editable:false"></label> -->
				<a href="#" id="search" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
			</div>
			<!-- 内容区右键功能 -->
			<div id="rightHand" class="easyui-menu" style="width:120px">
				<div id="right_add" iconCls="icon-add" style="height:25px;">新增维保记录</div>
				<div onclick="obj.edit()" iconCls="icon-edit" style="height:25px;">编辑维保记录</div>
				<div onclick="obj.right_delete()" iconCls="icon-remove" style="height:25px;">删除维保记录</div>
			</div>
		</div>
		<!-- 新增表单 修改数据也使用这个表单 -->
		<form id="add_edit_form" action="">
			<div class="add_edit_form_left">
				<div><label for="terminal_ormeterid">设备ID</label><input type="text" id="terminal_ormeterid" name="terminal_ormeterid" class="easyui-validatebox input" required="true" validtype="number_"></div>
				<div><label for="equipment_name">设备名称</label><input type="text" id="equipment_name" name="equipment_name" class="easyui-validatebox input" required="true"></div>
				<div id="date"><label for="maintain_date">维保时间</label><input type="text" id="maintain_date" name="maintain_date" data-options="editable:false" class="input date"></div>
				<div><label for="maintain_man">维保人</label><input placeholder="此项必填"  class="easyui-validatebox input" required="true" type="text" id="maintain_man" name="maintain_man"></div>
				<div><label for="maintain_record">维保记录</label><input type="text" id="maintain_record" name="maintain_record" class="easyui-validatebox input" required="true"></div>
				<div><label for="next_maintain_man">下次维保人</label><input type="text" id="next_maintain_man" name="next_maintain_man" class="easyui-validatebox input" required="true"></div>
				<div id="date"><label for="next_maintain_date">下次维保时间</label><input type="text" id="next_maintain_date" name="next_maintain_date" data-options="editable:false" class="input date"></div>
				<div><label for="maintain_content">维保内容</label><input type="text" id="maintain_content" name="maintain_content" class="easyui-validatebox input" required="true" ></div>
			</div>
		</form>
		<!-- easyui-datagrid 通用设置和方法-->
		<script src="${path}/js/datagrid.js" type="text/javascript"></script>
		<script type="text/javascript">
			//DOM后加载数据
			$(function(){
				$('#dg').datagrid({
				    url:'${path}/deviceRepairManagement/datagrid',
					columns:[[
				        {field:'terminal_ormeterid',title:'设备ID',sortable:true,width:100,checkbox:true},
				        {field:'equipment_name',title:'设备名称',sortable:true,width:100},
				        {field:'maintain_date',title:'维保时间',sortable:true,width:100},
				        {field:'maintain_man',title:'维保人',sortable:true,width:100},
				        {field:'maintain_record',title:'维保记录',sortable:true,width:100},
				        {field:'next_maintain_date',title:'下次维保时间',sortable:true,width:100},
				        {field:'next_maintain_man',title:'下次维保人',sortable:true,width:100},
				        {field:'maintain_content',title:'维保内容',sortable:true,width:100},
				    ]],
				    onRowContextMenu:function (e,rowIndex,rowData) {//显示右键功能
						e.preventDefault();
						$('#rightHand').menu('show',{
							left:e.pageX,
							top:e.pageY,
							hideOnUnhover:false
						});
						obj.right_delete = function () {
							$.messager.confirm('删除提示','是否删除维保记录名称为 <strong>'+rowData.equipment_name+'</strong> 的数据?',function (flag) {
								if (flag) {
									all_obj.delete_ajax('${path}/deviceRepairManagement/delete',rowData.terminal_ormeterid);//右键获取ID上传删除
								}
							});
						};
					},
				});
				// 增加 修改 计量点表单
			    $('#add,#right_add').click(function(){
			    	$('#add_edit_form #terminal_ormeterid').attr('readonly',false);
			    	all_obj.add('新增维保记录','${path}/deviceRepairManagement/add');
			    });
			    //搜索
			    $('#search').click(function(){
				   	if($('#search_maintain_man').val() != '' || $('#search_equipment_name').val() != ''  || $('input[name="search_maintain_date"]').val() != ''){//判断
						$('#dg').datagrid('load',{
							search_val_1:$.trim($('#search_equipment_name').val()),
							search_val_2:$.trim($('#search_equipment_name').val()),
							search_date:$('input[name="search_maintain_date"]').val()
						});
					}else{ //搜索框全部为空时 搜索全部
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
							$($('#add_edit_form').find('.add_edit_form_left').find('div').get(0)).before();
							$('.id').find('.input').css({background:'#ccc',color:'#575757'});
						}
						//编辑时获取行数据
			 			$('#add_edit_form #terminal_ormeterid').val(rows[0].terminal_ormeterid).attr('readonly',true);//ID 
						$('#add_edit_form #maintain_man').val(rows[0].maintain_man);//维保人
						$('#add_edit_form #equipment_name').val(rows[0].equipment_name);//设备名称
						$('#add_edit_form #maintain_date').datetimebox('setValue',rows[0].maintain_date);//维护时间 
						$('#add_edit_form #maintain_record').val(rows[0].maintain_record);//维护记录
						$('#add_edit_form #next_maintain_date').datetimebox('setValue',rows[0].next_maintain_date);
						$('#add_edit_form #next_maintain_man').val(rows[0].next_maintain_man);//下次为华人
						$('#add_edit_form #maintain_content').val(rows[0].maintain_content);//维护内容
						//获取编辑资料
						all_obj.update('编辑维保记录资料','${path}/deviceRepairManagement/update');//上传编辑资料
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
									select_id.push(checked[i].terminal_ormeterid);//取ID
								}
								all_obj.delete_ajax('${path}/deviceRepairManagement/delete',select_id.join(','));//上传ID删除
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