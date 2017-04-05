<!DOCTYPE html>
<html>
	<head>
		<title>任务定义</title> <!-- 	2.3.2.	任务模板表TASK_TEMPLATE  -->
		<meta charset="utf-8">
		<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
		<%@ include file="/WEB-INF/jsp/include/taglib.jsp"%>
		<%@ include file="/WEB-INF/jsp/include/easyui.jsp"%>
		<!-- EasyUI datagrid表格和表单CSS-->
		<link href="${path}/js/datagridForm.css" rel="stylesheet" type="text/css"/>
		<!-- 下拉多选框CSS文件-->
		<link href="${path}/js/multiple-select/multiple-select.css" rel="stylesheet" type="text/css"/>
		<style type="text/css">
			.add_edit_form_left div>label{width:153px;}
			#add_edit_form .ms-drop.bottom{width:820px;}
			#add_edit_form div>label{width:155px;}
		</style>
	</head>
	<body class="easyui-layout" onselectstart="javascript:return false;">
		<table id="dg" class="easyui-datagrid" toolbar="#toolbar"></table>
		<!-- 功能 -->
		<div id="tool">
			<div>
				<a href="#" id="add" class="easyui-linkbutton" data-options=" iconCls:'icon-add', plain:true ">新增任务模板</a>
				<a href="#" onclick="obj.edit()" class="easyui-linkbutton" data-options=" iconCls:'icon-edit', plain:true ">编辑任务模板(黄色背景行)</a>
				<a href="#" onclick="obj.button_delete()" class="easyui-linkbutton" data-options=" iconCls:'icon-remove', plain:true">删除任务模板(所有勾选行)</a>
			</div>
			<!-- 搜索功能 -->
			<div class="input_search">
				<label for="task_template_name">任务模板名称<input type="text" id="task_template_name" name="task_template_name"  class="input"></label>
				<a href="#" id="search" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
			</div>
			<!-- 内容区右键功能 -->
			<div id="rightHand" class="easyui-menu" style="width:120px">
				<div id="right_add" iconCls="icon-add" style="height:25px;">新增任务模板</div>
				<div onclick="obj.edit()" iconCls="icon-edit" style="height:25px;">编辑任务模板</div>
				<div onclick="obj.right_delete()" iconCls="icon-remove" style="height:25px;">删除任务模板</div>
			</div>
		</div>
		<!-- 新增表单 修改数据也使用这个表单 -->
		<form id="add_edit_form" action="">
			<div class="add_edit_form_left">
				<div><label for="task_template_name">任务模板名称</label><input placeholder="此项必填"  class="easyui-validatebox input" required="true" type="text" id="task_template_name" name="task_template_name"></div>
				<div>
					<label for="di_items">任务模板包括的数据项</label>
					<input style="display:none;" class="input" type="text" id="di_items" name="di_items">
					<!--下拉多选框  取数据项定义名称 -->
					<select id="auto" multiple="multiple" style="width:820px;"></select>
				</div>
			</div>
		</form>
		<!-- 下拉多选框JS文件 -->
		<script src="${path}/js/multiple-select/multiple-select.js" type="text/javascript"></script>
		<!-- easyui-datagrid 通用设置和方法-->
		<script src="${path}/js/datagrid.js" type="text/javascript"></script>
		<script type="text/javascript">
			//DOM后加载数据
			$(function(){
				$('#dg').datagrid({
				    url:'${path}/taskDefine/datagrid',
					columns:[[
				        {field:'task_template_id',title:'任务模板ID',sortable:true,width:100},
				        {field:'task_template_name',title:'任务模板名称',sortable:true,width:100},
				        {field:'di_items',title:'任务模板包括的数据项',sortable:true,width:100},
				    ]],
				    onRowContextMenu:function (e,rowIndex,rowData) {//显示右键功能
						e.preventDefault();
						$('#rightHand').menu('show',{
							left:e.pageX,
							top:e.pageY,
							hideOnUnhover:false
						});
						obj.right_delete = function () {
							$.messager.confirm('删除提示','是否删除任务模板名称为 <strong>'+rowData.task_template_name+'</strong> 的数据?',function (flag) {
								if (flag) {
									obj.delete_ajax('${path}/taskDefine/delete',rowData.task_template_id);//右键获取ID上传删除
								}
							});
						};
					},
				});
				
				
				//搜索
		    	$('#search').click(function(){
		    		if($('#task_template_name').val() != ''){//判断
						$('#dg').datagrid('load',{
							search_val_1:$.trim($('#task_template_name').val()),
						});
					}else {//搜索框全部为空时 搜索全部
						$('#dg').datagrid('load',{search_val_1:''});
					}
		    	});
				//按钮新增和右键新增
			    $('#add,#right_add').click(function(){
			    	$('#di_items').hide();
			    	$('.ms-drop.bottom').show();
			    	if($('#add_edit_form').find('.id').size() >= 1){
						$($('#add_edit_form').find('.add_edit_form_left').find('.id')).hide();
					}
			    	$.each($('.selected').find('input[type=checkbox]'),function(index,value){
			    		$(value).attr('checked',false);
			    	});
			    	$('.selected').css('color','black');
			    	$('.infoCount').html('');
					$('#add_edit_form').css({
						opacity:1,
						filter:'alpha(opacity=100)'
					}).form('reset').dialog({
						closed:false,
						width:900,
						height:800,
						modal:true,
						iconCls:'icon-add',
						title:'新增任务模板',
						buttons:[{
				    		text:'<div style="padding:3px 15px;font-size:15px;">提交</div>',
				    		handler:function (){
				    			if($('#auto').val() == null){
				    				$.messager.alert('操作提示','请选择数据,选择范围在30个内!','warning');
				    			}
				    			try{
				    				if($('#add_edit_form').form('validate') && $('#auto').val().length > 0 && $('#auto').val().length <= 30){//多选框验证
						    			all_obj.ajax('${path}/taskDefine/add');//新增地址
					    			}else {
					    				$.messager.alert('操作提示','请输入任务模板名称和包括的数据项,数据项选择范围在30个内!','warning');
					    			}
				    			}catch(error){}//如果不选择时提交会出错
				    		}
				    	}]
					});
					$($('#add_edit_form').find('input').get(0)).focus();
			    });
				//下拉多选框 任务模板包括的数据项
				$.ajax({
					url:'${path}/taskDefine/auto',
					type:'post',
					success:function(data){
				    	all_obj.multiSelect(data,4,3);//只要di_item_no和di_item_name 下标 4 3
					},
					error:function(){
						$('#dg').datagrid('loaded');
						$.messager.alert('数据项获取失败提示','数据项获取失败,找不到地址或网络错误,请刷新重试!','warning');
					}
				});
	       		$('.window-shadow').hide();
			});
			//编辑和删除方法
			var obj={
				//修改采集器表单
				edit:function(){
					var rows = $('#dg').datagrid('getSelections');
					if (rows.length > 1) {
						$.messager.alert('编辑提示','只能选择一条数据编辑!','warning');
					}else if (rows.length == 1){
						//勾选已经选择的值
						$('#add_edit_form').dialog({
							closed:false,
							width:900,
							height:820,
							modal:true,
							onClose:function(){
								all_obj.clearMultiSelect();
							}
						});
						var value = rows[0].di_items.split(',');
						$.each($('#auto').find('option'),function(index2,value2){
							if(all_obj.inArray(value,$(value2).val())){
								$(value2).attr('selected','selected');
							}
						});
						$('#auto').multipleSelect({});
						if($('#add_edit_form').find('.id').size() <= 0){
							$($('#add_edit_form').find('.add_edit_form_left').find('div').get(0)).before('<div class="id"><label for="task_template_id">任务模板ID<span class=read>(只读)</span></label><input type="text" id="task_template_id" name="task_template_id" class="input" readonly="true"></div>');
							$('.id').find('.input').css({background:'#ccc',color:'#575757'});
						}
						$('.ms-drop.bottom').show();
						//编辑时获取行数据
						$('#add_edit_form #task_template_id').val(rows[0].task_template_id);
						$('#add_edit_form #task_template_name').val(rows[0].task_template_name);
						all_obj.update('编辑任务资料','${path}/taskDefine/update');//上传编辑资料
					}else if (rows.length == 0) {
			    		$.messager.alert('错误提示','请选择要编辑的数据,只能选择一条!','warning');
					}
				},
				//按钮删除和右键删除调用此方法
				delete_ajax:function(url,id){//传删除地址和获取的ID
					$.ajax({
						type:'post',
						dataType:'json',
						url:url,
						data:{
							delTaskDefineIdArray:id
						},
						beforeSend:function (jqXHR,setting) {
							$('#dg').datagrid('loading');
						},
						success:function (data) {
							$('#dg').datagrid('load');
							if (data.success) {
								$('#dg').datagrid('loaded');
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
									select_id.push(checked[i].task_template_id);//取ID
								}
								obj.delete_ajax('${path}/taskDefine/delete',select_id.join(','));//上传ID删除
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