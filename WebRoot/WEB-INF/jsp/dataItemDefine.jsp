<!DOCTYPE html>
<html>
	<head>
		<title>数据项定义</title>
		<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
		<%@ include file="/WEB-INF/jsp/include/taglib.jsp"%>
		<%@ include file="/WEB-INF/jsp/include/easyui.jsp"%>
		<!-- EasyUI datagrid表格和表单CSS-->
		<link href="${path}/js/datagridForm.css" rel="stylesheet" type="text/css"/>
		<style type="text/css">
			.add_edit_form_left label{width:120px;}
		</style>
	</head>
	<body class="easyui-layout" onselectstart="javascript:return false;">
		<table id="dg" class="easyui-datagrid" toolbar="#toolbar"></table>
		<!-- 功能 -->
		<div id="tool">
			<div>
				<a href="#" id="add" class="easyui-linkbutton" data-options=" iconCls:'icon-add', plain:true ">新增数据项</a>
				<a href="#" onclick="obj.edit()" class="easyui-linkbutton" data-options=" iconCls:'icon-edit', plain:true ">编辑数据项(黄色背景行)</a>
				<a href="#" onclick="obj.button_delete()" class="easyui-linkbutton" data-options=" iconCls:'icon-remove', plain:true">删除数据项(所有勾选行)</a>
			</div>
			<!-- 搜索功能 -->
			<div class="input_search">
				<label for="di_group_name">数据项组名称<input type="text" id="di_group_name" name="di_group_name" class="input"></label>
				<a href="#" id="search" class="easyui-linkbutton" iconCls="icon-search" >搜索</a>
			</div>
			<!-- 内容区右键功能 -->
			<div id="rightHand" class="easyui-menu" style="width:130px;">
				<div id="right_add" iconCls="icon-add" style="height:25px;">新增数据项</div>
				<div onclick="obj.edit()" iconCls="icon-edit" style="height:25px;">编辑数据项</div>
				<div onclick="obj.right_delete()" iconCls="icon-remove" style="height:25px;">删除数据项</div>
			</div>
		</div>
		<!-- 新增表单 修改数据也使用这个表单  -->
		<form id="add_edit_form" action="">
			<div class="add_edit_form_left">
				<div><label for="di_item_no">数据项编码</label><input class="easyui-validatebox input" required="true" validtype="check_nine" type="text" id="di_item_no" name="di_item_no"></div>
				<div><label for="di_no">数据类型编码</label><input class="easyui-validatebox input" required="true" validtype="check_nine" type="text" id="di_no" name="di_no"></div>
				<div><label for="di_name">数据类型名称</label><input class="easyui-validatebox input" required="true" type="text" id="di_name" name="di_name" ></div>
				<div><label for="di_group_name">数据项组名称</label><input class="input" type="text" id="di_group_name" name="di_group_name"></div>
				<div><label for="di_item_name">数据项名称</label><input class="easyui-validatebox input" required="true" type="text" id="di_item_name" name="di_item_name"></div>
				<div><label for="di_item_format">数据项格式</label><input class="easyui-validatebox input" required="true" type="text" id="di_item_format" name="di_item_format"></div>
				<div><label for="di_item_length">数据项长度</label><input class="easyui-validatebox input" required="true" validtype="length[1,3]" type="text" id="di_item_length" name="di_item_length"></div>
				<div><label for="use_unit">数据项长度单位</label><input class="easyui-validatebox input" required="true" type="text" id="use_unit" name="use_unit"></div>
			</div>
		</form>
		<!-- easyui-datagrid 通用设置和方法-->
		<script src="${path}/js/datagrid.js" type="text/javascript"></script>
		<script  type="text/javascript">
			//DOM后加载数据
			$(function(){
				$('#dg').datagrid({
				    url:'${path}/dataItemDefine/datagrid',
				    columns:[[
				        {field:'di_item_no',title:'数据项编码',sortable:true,width:100,checkbox:true},
					    {field:'di_no',title:'数据类型编码',sortable:true,width:100},
					    {field:'di_name',title:'数据类型名称',sortable:true,width:100},
					    {field:'di_group_name',title:'数据项组名称',sortable:true,width:100},
					    {field:'di_item_name',title:'数据项名称',sortable:true,width:100},
					    {field:'di_item_format',title:'数据项格式',sortable:true,width:100},
					    {field:'di_item_length',title:'数据项长度',sortable:true,width:100},
					    {field:'use_unit',title:'数据项长度单位',sortable:true,width:100},
				    ]],
				    onRowContextMenu:function (e,rowIndex,rowData) {//显示右键功能
						e.preventDefault();
						$('#rightHand').menu('show',{
							left:e.pageX,
							top:e.pageY
						});
						obj.right_delete = function(){
							$.messager.confirm('删除提示','是否删除数据类型名称为 <strong>'+rowData.di_name+'</strong> 的数据?',function (flag) {
								if (flag) {
									obj.delete_ajax('${path}/dataItemDefine/delete',rowData.di_item_no);//右键获取ID上传删除
								}
							});
						}
					},
				});
				
				
				//按钮新增和右键新增
			    $('#add,#right_add').click(function(){
			    	all_obj.add('新增数据项','${path}/dataItemDefine/add');
			    });
			  	//搜索
		    	$('#search').click(function(){
		    		if($('#di_group_name').val() != ''){//判断
						$('#dg').datagrid('load',{
							search_val_1:$.trim($('#di_group_name').val()),
						});
					}else {//搜索框全部为空时 搜索全部
						$('#dg').datagrid('load',{search_val_1:''});
					}
		    	});
			});
			var obj={
				//打开表 获取修改数据
				edit:function(){
					var rows = $('#dg').datagrid('getSelections');
					if (rows.length > 1) {
						$.messager.alert('编辑提示','只能选择一条数据编辑!','warning');
					}else if (rows.length == 1){
						//编辑时获取行数据
						$('#add_edit_form #di_item_no').val(rows[0].di_item_no);
						$('#add_edit_form #di_no').val(rows[0].di_no);
					    $('#add_edit_form #di_name').val(rows[0].di_name);
					    $('#add_edit_form #di_group_name').val(rows[0].di_group_name);
					    $('#add_edit_form #di_item_name').val(rows[0].di_item_name);
					    $('#add_edit_form #di_item_format').val(rows[0].di_item_format);
					    $('#add_edit_form #di_item_length').val(rows[0].di_item_length);
					    $('#add_edit_form #use_unit').val(rows[0].use_unit);
						all_obj.update('编辑数据项资料','${path}/dataItemDefine/update');//上传编辑资料
					}else if (rows.length == 0) {
			    		$.messager.alert('错误提示','请选择要编辑的数据,只能选择一条!','warning');
					}
				},
				//按钮删除和右键删除调用此方法
				delete_ajax:function(url,id){//传删除地址和获取的ID
					$.ajax({
						type:'post',
						url:url,
						data:{
							delDataItemDefineIdArray:id
						},
						beforeSend:function (jqXHR,setting) {
							$('#dg').datagrid('loading');
						},
						success:function (data) {
							$('#dg').datagrid('loaded');
							if ($.parseJSON(data).success) {
								$('#dg').datagrid('load');
								$('#dg').datagrid('unselectAll');
								$.messager.show({
									title:'删除成功提示',
									msg:'数据已经成功删除'
								});
							}else {
								$.messager.alert('操作失败提示','找不到地址或网络错误,请刷新重试!','warning');
							}
						},
						error:function(){
							$('#dg').datagrid('loaded');
							$.messager.alert('操作失败提示','找不到地址或网络错误,请刷新重试!','warning');
						}
					});
				},
				//按钮删除
				button_delete:function () {
					var checked = $('#dg').datagrid('getChecked');//getSelections只能得到选中的行! getChecked能得到全部勾行的行!
					if (checked.length > 0) {
						$.messager.confirm('数据删除提示','是否删除选中的数据?',function (flag) {
							if (flag) {
								var select_id = [];
								for (var i = 0; i < checked.length; i++) {
									select_id.push(checked[i].di_item_no);//取ID
								}
								obj.delete_ajax('${path}/dataItemDefine/delete',select_id.join(','));//上传ID删除
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