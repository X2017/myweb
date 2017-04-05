<!DOCTYPE html>
<html>
	<head>
		<title>变压器管理</title>
		<meta charset="utf-8">
		<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
		<%@ include file="/WEB-INF/jsp/include/taglib.jsp"%>
		<%@ include file="/WEB-INF/jsp/include/easyui.jsp"%>
		<!-- EasyUI datagrid表格和表单CSS-->
		<link href="${path}/js/datagridForm.css" rel="stylesheet" type="text/css"/>
		<link rel="stylesheet" type="text/css" href="${path}/js/select2-4.0.3/dist/css/select2.min.css" />
		<style type="text/css">
			/*表单样式*/
			#add_edit_form{overflow:hidden;}
			#add_edit_form div{margin:0 10px 10px 0;}
			.add_edit_form_left div>label{width:140px;}
			.add_edit_form_right label{width:125px;}
			#add_edit_form .input{width:200px;height:28px;font-size:15px;}
		</style>
	</head>
	<body class="easyui-layout" onselectstart="javascript:return false;">
		<table id="dg" class="easyui-datagrid" toolbar="#toolbar"></table>
		<!-- 功能 -->
		<div id="tool">
			<div>
				<a href="#" id="add" class="easyui-linkbutton" data-options=" iconCls:'icon-add', plain:true ">新增变压器</a>
				<a href="#" onclick="obj.edit()" class="easyui-linkbutton" data-options=" iconCls:'icon-edit', plain:true ">编辑变压器(黄色背景行)</a>
				<a href="#" onclick="obj.button_delete()" class="easyui-linkbutton" data-options=" iconCls:'icon-remove', plain:true">删除变压器(所有勾选行)</a>
			</div>
			<!-- 搜索功能 -->
			<div class="input_search">
				<label for="search_transformer_no">变压器编号<input type="text" id="search_transformer_no" name="search_transformer_no"  class="input"></label>
				<label for="search_transformer_name">变压器名称<input type="text" id="search_transformer_name" name="search_transformer_name" class="input"></label>
				<label for="search_create_date"  style="margin-right:15px">创建时间<input type="text" id="search_create_date" name="search_create_date" class="easyui-datebox" data-options="editable:false"></label>
				<a href="#" id="search" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
			</div>
			<!-- 内容区右键功能 -->
			<div id="rightHand" class="easyui-menu" style="width:120px">
				<div id="right_add" iconCls="icon-add" style="height:25px;">新增变压器</div>
				<div onclick="obj.edit()" iconCls="icon-edit" style="height:25px;">编辑变压器</div>
				<div onclick="obj.right_delete()" iconCls="icon-remove" style="height:25px;">删除变压器</div>
			</div>
		</div>
		<!-- 新增变压器表单 修改数据也使用这个表单 -->
		<form id="add_edit_form" action="">
			<div class="add_edit_form_left">
<!-- 				<div><label for="transformer_id">变压器ID<span id="read" style="display:none;color:red;font-size:13px;">(只读)</span></label><input placeholder="此项必填"  class="easyui-validatebox input" required="true" type="text" id="transformer_id" name="transformer_id"></div>
 -->				<div><label for="transformer_no">变压器编号</label><input placeholder="此项必填"  class="easyui-validatebox input" required="true" type="text" id="transformer_no" name="transformer_no"></div>
				<div><label for="transformer_name">变压器名称</label><input type="text" id="transformer_name" name="transformer_name" class="input"></div>
				<div>
					<label for="customer_id">所属客户</label>
					<input type="text" id="customer_id" name="customer_id" class="input" style="display:none">
					<select id="pulldown" name="pulldown" style="width: 210px;"></select>
				</div>
				<div><label for="transformer_capacity">变压器容量<span style="color:red;font-size:13px;">(单位:KVA)</span></label><input type="text" id="transformer_capacity" name="transformer_capacity" class="input"></div>
				<div>
					<label for="transformer_model">变压器型号</label>
					<input type="text" id="transformer_model" name="transformer_model" class="input">
				</div>
				<div>
					<label for="rated_voltage">额定电压</label>
					<input type="text" id="rated_voltage" name="rated_voltage" class="input">
				</div>
				<div>
					<label for="rated_current">额定电流</label>
					<input type="text" id="rated_current" name="rated_current" class="input">
				</div>
				<div><label for="rated_frequency">额定频率</label><input  class="easyui-validatebox input" required="true" type="text" id="rated_frequency" name="rated_frequency"></div>
				<div><label for="noload_current">空载电流</label><input  class="easyui-validatebox input" required="true" type="text" id="noload_current" name="noload_current"></div>
				<div><label for="noload_loss">空载损耗</label><input class="easyui-validatebox input" required="true" type="text" id="noload_loss" name="noload_loss" class="input"></div>
				<div><label for="load_loss">负载损耗</label><input type="text" class="easyui-validatebox input" required="true" id="load_loss" name="load_loss"></div>
			</div>
			<div class="add_edit_form_right">
				<div><label for="transformer_status">变压器状态</label><input type="text" id="transformer_status" name="transformer_status" class="input"></div>
				<div><label for="short_ci">短路阻抗</label><input type="text" id="short_ci" name="short_ci" class="input"></div>
				<div><label for="serial_num">出厂编号</label><input  type="text" id="serial_num" name="serial_num" class="easyui-validatebox input" ></div>
				<div>
					<label for="max_vol">高压侧电压等级</label>
					<input type="text" id="max_vol" name="max_vol" class="input">
				</div>
				<div><label for="min_vol">低压侧电压等级</label><input type="text" id="min_vol" name="min_vol" class="input"></div>
				<div>
					<label for="is_valid">是否有效</label>
					<input type="text" id="is_valid" name="is_valid" class="input">
				</div>
				<div>
					<label for="transformer_asset_no">变压器资产编号</label>
					<input type="text" id="transformer_asset_no" name="transformer_asset_no" class="input">
				</div>
				<div>
					<label for="transformer_manufacturer">变压器生成厂家</label>
					<input type="text" id="transformer_manufacturer" name="transformer_manufacturer" class="input">
				</div>
				<div>
					<label for="term_manufacturer">终端厂家</label>
					<input type="text" id="term_manufacturer" name="term_manufacturer" class="input">
				</div>
				<div><label for="create_man">建档人</label><input type="text" id="create_man" name="create_man" class="input"></div>
				<div id="created_date">
					<label for="created_date">建档日期</label>
					<input type="text" id="created_date" name="created_date" data-options="editable:false" class="input date">
				</div>
				<div><label for="remarks">备注</label><input type="text" id="remarks" name="remarks" class="input"></div>
			</div>
		</form>
		<!-- easyui-datagrid 通用设置和方法-->
		<script src="${path}/js/datagrid.js" type="text/javascript"></script>
		<script type="text/javascript" src="${path}/js/select2-4.0.3/dist/js/select2.full.min.js"></script>
		<script type="text/javascript" src="${path}/js/select2-4.0.3/dist/js/i18n/zh-CN.js"></script>
		<script type="text/javascript">
			//DOM后加载数据
			var enterpriseInfo = ${enterpriseInfo};
			$(function(){
				all_obj.inputSearch("#pulldown","enterpriseinfo/info");
				$('#dg').datagrid({
				    url:'${path}/transformer/datagrid',
				    columns:[[
				        {field:'transformer_id',title:'变压器ID',width:100,checkbox:true},
				        {field:'transformer_no',title:'变压器编号',sortable:true,width:180},
				        {field:'transformer_name',title:'变压器名称',sortable:true,width:300},
				        {field:'customer_id',title:'所属客户',sortable:true,width:150,formatter:function(row,index,value){
					   		for(var i=0;i<enterpriseInfo.length;i++){
					   			if(enterpriseInfo[i].id==row){
					   				return enterpriseInfo[i].text;
					   			}
					   		}
					   	 }},
				        {field:'transformer_model',title:'变压器型号',sortable:true,width:0},
				        {field:'transformer_capacity',title:'变压器容量',sortable:true,width:100},
				        {field:'rated_voltage',title:'额定电压',sortable:true,width:100},
				        {field:'rated_current',title:'额定电流',sortable:true,width:100},
				        {field:'rated_frequency',title:'额定频率',sortable:true,width:0},
				        {field:'noload_current',title:'空载电流',sortable:true,width:100},        
				        {field:'noload_loss',title:'空载损耗',sortable:true,width:80},
				        {field:'load_loss',title:'负载损耗',sortable:true,width:80},
				        {field:'short_ci',title:'短路阻抗',sortable:true,width:100},
				        {field:'serial_num',title:'出厂编号',sortable:true,width:80},
				        {field:'max_vol',title:'高压侧电压等级',sortable:true},
				        {field:'min_vol',title:'低压侧电压等级',sortable:true,width:0},
				        {field:'transformer_status',title:'变压器状态',sortable:true,width:100,formatter:function(row,index,value){
					    	if(row==1){
					    		return "未运行";
					    	}else if(row ==2){
					    		return "运行";
					    	}else if(row ==3){
					    		return "报停";
					    	}else if(row ==4){
					    		return "拆除";
					    	}
					    }},
				        {field:'is_valid',title:'是否有效',sortable:true,width:100,formatter:function(row,index,value){
					    	if(row==1){
					    		return "有效";
					    	}else if(row ==0){
					    		return "无效";
					    	}
					    }},
				        {field:'create_man',title:'建档人',sortable:true,width:100},
				        {field:'created_date',title:'建档日期',sortable:true,width:100}, 
				        {field:'transformer_asset_no',title:'变压器资产编号',sortable:true,width:100},
				        {field:'transformer_manufacturer',title:'变压器生成厂家',sortable:true,width:100},
				        {field:'remarks',title:'备注',sortable:true,width:0,hidden:true},
				        {field:'term_manufacturer',title:'终端厂家',sortable:true,width:100},
				    ]],
				    onRowContextMenu:function(e,rowIndex,rowData){// 显示右键功能
						e.preventDefault();
						$('#rightHand').menu('show',{
							left:e.pageX,
							top:e.pageY
						});
						obj.right_delete=function(){
							$.messager.confirm('删除提示','是否删除变压器名称为 <strong>'+rowData.transformer_name+'</strong> 的数据?',function (flag) {
								if (flag) {
									obj.delete_ajax('${path}/transformer/delete',rowData.transformer_id);//右键获取ID上传删除
								}
							});
						};
					},
				});
				//customer_id
				
				//表单下拉框1 有效；0 无效'
				$('#is_valid').combobox({valueField:'id',textField:'text',height:'30',editable:false,data:[{"id":1,"text":"有效"},{"id":0,"text":"无效"}]});
				$('#transformer_status').combobox({valueField:'id',textField:'text',height:'30',editable:false,data:[{"id":1,"text":"未运行"},{"id":2,"text":"运行"},{"id":3,"text":"报停"},{"id":4,"text":"拆除"}]});
				// 增加 修改 变压器表单
			    $('#add,#right_add').click(function(){
			    	$('#add_edit_form #transformer_id').css({
						background:'white',
						color:'black'
					}).attr('readonly',false);//变压器ID
					$('#read').hide();
			    	all_obj.add('新增变压器','${path}/transformer/add',function(){
			    		if($("#pulldown").select2("val") != null){
			    			$("#customer_id").val($("#pulldown").select2("val")[0]);
			    		}
			    	},function(){
			    		$("#pulldown").empty();
			    	});
			    });
			    //搜索
		    	$('#search').click(function(){
		    		if($('#search_transformer_no').val() != '' || $('#search_transformer_name').val() != '' || $('input[name="search_create_date"]').val() != ''){//判断
						$('#dg').datagrid('load',{
							search_val_1:$.trim($('#search_transformer_no').val()),
							search_val_2:$.trim($('#search_transformer_name').val()),
							search_date:$('input[name="search_create_date"]').val()
						});
					}else {//搜索框全部为空时 搜索全部
						$('#dg').datagrid('load',{search_val_1:''});
					}
		    	});
		    	//表单验证
			    $('#comm_no').validatebox({validType:'number'});
			    $('#transformer_capacity').validatebox({validType:'number'});
			    $('#customer_id').validatebox({validType:'number'});
			    $('#term_id').validatebox({validType:'number'});
			    $('#transformer_id').validatebox({validType:'number'});
			    $('#noload_loss').validatebox({validType:'number'});
			    $('#date .validatebox-text').validatebox({required:true});
			});
			//编辑和删除方法
			var flag = true;
			var obj={
				// 修改采集器表单
				edit:function(){
					var rows = $('#dg').datagrid('getSelections');
					if (rows.length > 1) {
						$.messager.alert('编辑提示','只能选择一条数据编辑!','warning');
					}else if (rows.length == 1){
						//编辑时获取行数据
						$('#add_edit_form #transformer_id').val(rows[0].transformer_id).css({
							background:'#ccc',
							color:'#575757'
						}).attr('readonly',true);//变压器ID
						$('#read').show();
						$('#add_edit_form #transformer_no').val(rows[0].transformer_no);//变压器编号
						$('#add_edit_form #transformer_name').val(rows[0].transformer_name);//变压器名称
						$('#add_edit_form #customer_id').val(rows[0].customer_id);//所属客户ID
						$('#add_edit_form #transformer_model').val(rows[0].transformer_model);
						$('#add_edit_form #transformer_capacity').val(rows[0].transformer_capacity);//变压器容量
						$('#add_edit_form #rated_current').val(rows[0].rated_current);//额定电流
						$('#add_edit_form #rated_voltage').val(rows[0].rated_voltage);//额定电压
						$('#add_edit_form #noload_current').val(rows[0].noload_current);//空载电流
						$('#add_edit_form #rated_frequency').val(rows[0].rated_frequency);//额定频率
						$('#add_edit_form #load_loss').val(rows[0].load_loss);
						$('#add_edit_form #noload_loss').val(rows[0].noload_loss);
						$('#add_edit_form #transformer_asset_no').val(rows[0].transformer_asset_no);
						$('#add_edit_form #create_man').val(rows[0].create_man);//创建人
						$('#add_edit_form .date').datetimebox('setValue',rows[0].created_date);//日期
						$('#add_edit_form input[name=created_date]').val(rows[0].created_date);//日期
						$('#add_edit_form #short_ci').val(rows[0].short_ci);
						$('#add_edit_form #serial_num').val(rows[0].serial_num);//出厂编号
						$('#add_edit_form #transformer_id').val(rows[0].transformer_id);//所属变压器ID
						$('#add_edit_form #remarks').val(rows[0].remarks);//备注
						$('#add_edit_form #min_vol').val(rows[0].min_vol);
						$('#add_edit_form #max_vol').val(rows[0].max_vol);
						$('#add_edit_form #transformer_manufacturer').val(rows[0].transformer_manufacturer);
						$('#add_edit_form #term_manufacturer').val(rows[0].term_manufacturer);//备注
						$('#add_edit_form #is_valid').combobox('setValue',rows[0].is_valid);//是否有效
						$('#add_edit_form #transformer_status').combobox('setValue',rows[0].transformer_status);//状态
						if(rows[0].customer_id != ""){
							$("#pulldown").html("<option id=customer_id_select value="+rows[0].customer_id+" selected=selected>"+all_obj.getText(enterpriseInfo,rows[0].customer_id)+"</option>");
						}
						all_obj.update('编辑变压器资料','${path}/transformer/update',function(){
							if($("#pulldown").select2("val") != null){//终端
				    			$("#customer_id").val($("#pulldown").select2("val")[0]);
				    		}
						},function(){
							 $("#pulldown").empty();
						});//上传编辑资料
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
							delIdArray:id
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
				// 按钮删除
				button_delete:function () {
					var checked = $('#dg').datagrid('getChecked');//getSelections只能得到选中的行! getChecked能得到全部勾行的行!
					if (checked.length > 0) {
						$.messager.confirm('数据删除提示','是否删除选中的数据?',function (flag) {
							if (flag) {
								var select_id = [];
								for (var i = 0; i < checked.length; i++) {
									select_id.push(checked[i].transformer_id);//取ID
								}
								obj.delete_ajax('${path}/transformer/delete',select_id.join(','));//上传ID删除
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