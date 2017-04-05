<!DOCTYPE html>
<html>
	<head>
		<title>客户管理</title>
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
				<a href="#" id="add" class="easyui-linkbutton" data-options=" iconCls:'icon-add', plain:true ">新增客户</a>
				<a href="#" onclick="obj.edit()" class="easyui-linkbutton" data-options=" iconCls:'icon-edit', plain:true ">编辑客户(黄色背景行)</a>
				<a href="#" onclick="obj.button_delete()" class="easyui-linkbutton" data-options=" iconCls:'icon-remove', plain:true">删除客户(所有勾选行)</a>
			</div>
			<!-- 搜索功能 -->
			<div class="input_search">
				<label for="search_mp_no">客户编号<input type="text" id="search_mp_no" name="search_mp_no" class="input"></label>
				<label for="search_create_man">创建人<input type="text" id="search_create_man" name="search_create_man" class="input"></label>
				<label for="search_create_date"  style="margin-right:15px">创建时间<input type="text" id="search_create_date" class="easyui-datebox" name="search_create_date" data-options="editable:false"></label>
				<a href="#" id="search" class="easyui-linkbutton" iconCls="icon-search" >搜索</a>
			</div>
			<!-- 内容区右键功能 -->
			<div id="rightHand" class="easyui-menu" style="width:130px;">
				<div id="right_add" iconCls="icon-add" style="height:25px;">新增客户</div>
				<div onclick="obj.edit()" iconCls="icon-edit" style="height:25px;">编辑客户</div>
				<div onclick="obj.right_delete()" iconCls="icon-remove" style="height:25px;">删除客户</div>
			</div>
		</div>
		<!-- 新增表单 修改数据也使用这个表单  -->
		<form id="add_edit_form" action="">
			<div class="add_edit_form_left">
				<div><label for="customer_no">客户编号</label><input class="easyui-validatebox input" required="true" type="text" id="customer_no" name="customer_no" placeholder="此项必填"></div>
				<div><label for="customer_name">客户名称</label><input class="easyui-validatebox input" required="true" type="text" id="customer_name" name="customer_name" placeholder="此项必填"></div>
				<div>
					<label for="customer_type">客户类别</label>
					<input type="text" id="customer_type" name="customer_type" class="input">
				</div>
				<div><label for="para_id">上级客户ID</label><input type="text" id="para_id" name="para_id" class="input"></div>
				<div>
					<label for="industry_type">行业类别</label>
					<input type="text" id="industry_type" name="industry_type" class="input">
				</div>
				<div><label for="customer_capacity">用户容量</label><input type="text" id="customer_capacity" name="customer_capacity" class="input"></div>
				<div>
					<label for="customer_status">客户状态</label>
					<input type="text" id="customer_status" name="customer_status" class="input">
				</div>
				<div><label for="man">联系人</label><input type="text" id="man" name="man" class="input"></div>
				<div><label for="tel">联系电话</label><input type="text" id="tel" name="tel" class="input"></div>
				<div><label for="emailAddr">电子邮箱</label><input type="text" id="emailAddr" name="emailAddr" class="input"></div>
				<div><label for="address">联系地址</label><input type="text" id="address" name="address" class="input"></div>
			</div>
			<div class="add_edit_form_right">
				<div>
					<label for="is_valid">是否有效</label>
					<input type="text" id="is_valid" name="is_valid" class="input">
				</div>
				<div><label for="create_man">创建人</label><input type="text" id="create_man" name="create_man" class="input"></div>
				<div id="date"><label for="created_date">创建日期</label><input type="text" data-options="editable:false" id="created_date" name="created_date" class="input date" ></div>
				<div><label for="area">客户所属区域</label><input type="text" id="area" name="area" class="input"></div>
				<div><label for="power_org">电网管理机构</label><input type="text" id="power_org" name="power_org" class="input"></div>
				<div><label for="remarks">备注</label><input type="text" id="remarks" name="remarks" class="input"></div>
				<div><label for="customer_status_name">用户状态名称</label><input type="text" id="customer_status_name" name="customer_status_name" class="input"></div>
				<div><label for="customer_type_name">用户类型名称</label><input type="text" id="customer_type_name" name="customer_type_name" class="input"></div>
				<div><label for="industry_name">用户行业名称</label><input type="text" id="industry_name" name="industry_name" class="input"></div>
				<div><label for="company_id">所属部门ID</label><input type="text" id="company_id" name="company_id" class="input"></div>
			</div>
		</form>
		<!-- easyui-datagrid 通用设置和方法-->
		<script src="${path}/js/datagrid.js" type="text/javascript"></script>
		<script type="text/javascript">
			//DOM后加载数据
			$(function(){
				$('#dg').datagrid({
				    url:'${path}/customer/datagrid',
				    columns:[[
				        {field:'customer_id',title:'客户ID',sortable:true,width:100,checkbox:true},
					    {field:'customer_no',title:'客户编号',sortable:true,width:100},
					    {field:'customer_name',title:'客户名称',sortable:true,width:100},
					    {field:'customer_type',title:'客户类别',sortable:true,width:100,formatter:function(row,index,value){
					    	if(row==1){
					    		return "大客户";
					    	}else if(row==2){
					    		return"客户子组";
					    	}
					    }},
					    {field:'para_id',title:'上级客户ID',sortable:true,width:100},
					    //{"id":"X0000","text":"1：社会各行业用电"},{"id":"X0100","text":"2：第一产业"},
					    //{"id":"X0200","text":"3：第二产业"},{"id":"X0300","text":"4：第三产业"},{"id":"Z000","text":"5：全社会用电总计"}
					    {field:'industry_type',title:'行业类别',sortable:true,width:100,formatter:function(row,index,value){
					    	if(row=="X0000"){//1未运行 2运行 3报停  3销户'
					    		return "社会各行业用电";
					    	}else if(row=="X0100"){
					    		return"第一产业";
					    	}else if(row=="X0200"){
					    		return"第二产业";
					    	}else if(row=="X0300"){
					    		return"第三产业";
					    	}else if(row=="Z000"){
					    		return"全社会用电总计";
					    	}
					    
					    }},
					    {field:'customer_capacity',title:'用户容量',sortable:true,width:100},
					    {field:'customer_status',title:'客户状态',sortable:true,width:100,formatter:function(row,index,value){
					    	if(row==1){//1未运行 2运行 3报停  3销户'
					    		return "未运行";
					    	}else if(row==2){
					    		return"运行";
					    	}else if(row==3){
					    		return"报停";
					    	}else if(row==4){
					    		return"销户";
					    	}
					    }},
					    {field:'man',title:'联系人',sortable:true,width:100},
					    {field:'tel',title:'联系电话',sortable:true,width:100},
					    {field:'emailAddr',title:'电子邮箱',sortable:true,width:100},
					    {field:'address',title:'联系地址',sortable:true,width:100},
					    {field:'is_valid',title:'是否有效',sortable:true,width:100,formatter:function(row,index,value){
					    	if(row==0){
					    	return "无效";
					    	}else if(row ==1){
					    	return "有效";
					    	}
					    }},
					    {field:'create_man',title:'创建人',sortable:true,width:100},
					    {field:'created_date',title:'创建日期',sortable:true,width:100},
					    {field:'area',title:'客户所属区域',sortable:true,width:100},
					    {field:'power_org',title:'电网管理机构',sortable:true,width:100},
					    {field:'remarks',title:'备注',sortable:true,width:100},
					    {field:'customer_status_name',sortable:true,title:'用户状态名称',width:100},
					    {field:'customer_type_name',sortable:true,title:'用户类型名称',width:100},
					    {field:'industry_name',sortable:true,title:'用户行业名称',width:100},
					    {field:'company_id',sortable:true,title:'所属部门ID',width:100}
				    ]],
				    onRowContextMenu:function (e,rowIndex,rowData) {//显示右键功能
						e.preventDefault();
						$('#rightHand').menu('show',{
							left:e.pageX,
							top:e.pageY
						});
						obj.right_delete = function () {
							$.messager.confirm('删除提示','是否删除客户名称为 <strong>'+rowData.customer_name+'</strong> 的数据?',function (flag) {
								if (flag) {
									obj.delete_ajax('${path}/customer/delete',rowData.customer_id);//右键获取ID上传删除
								}
							});
						}
					},
				});
				
				
				//下拉框设置
			    $('#customer_type').combobox({valueField:'id',textField:'text',height:'30',editable:false,data:[{"id":01,"text":"1：大客户"},{"id":02,"text":"2：客户子组"}]});
			    $('#industry_type').combobox({valueField:'id',textField:'text',height:'30',editable:false,data:[{"id":"X0000","text":"1：社会各行业用电"},{"id":"X0100","text":"2：第一产业"},{"id":"X0200","text":"3：第二产业"},{"id":"X0300","text":"4：第三产业"},{"id":"Z000","text":"5：全社会用电总计"}]});
			    $('#customer_status').combobox({valueField:'id',textField:'text',height:'30',editable:false,data:[{"id":"01","text":"1：未运行"},{"id":"02","text":"2：运行"},{"id":"03","text":"3：报停"},{"id":"04","text":"4：销户"}]});
			    $('#is_valid').combobox({valueField:'id',textField:'text',height:'30',editable:false,data:[{"id":"01","text":"1：有效"},{"id":"0","text":"2: 无效"}]});
				//按钮新增和右键新增
			    $('#add,#right_add').click(function(){
			    	all_obj.add('新增客户','${path}/customer/add');
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
		        $('#tel').validatebox({validType:'phone'});
		        $('#emailAddr').validatebox({validType:'email'});
		        $('#date .validatebox-text').validatebox({required:true});
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
							$($('#add_edit_form').find('.add_edit_form_left').find('div').get(0)).before('<div class="id"><label for="customer_id">客户ID<span class=read>(只读)</span></label><input type="text" id="customer_id" name="customer_id" class="input" readonly="true"></div>');
							$('.id').find('.input').css({background:'#ccc',color:'#575757'});
						}
						//编辑时获取行数据
						$('#add_edit_form #customer_id').val(rows[0].customer_id);
					    $('#add_edit_form #customer_no').val(rows[0].customer_no);
					    $('#add_edit_form #customer_name').val(rows[0].customer_name);
					    $('#add_edit_form #customer_type').combobox('setValue',rows[0].customer_type);
					    $('#add_edit_form #para_id').val(rows[0].para_id);
					    $('#add_edit_form #industry_type').combobox('setValue',rows[0].industry_type);
					    $('#add_edit_form #customer_capacity').val(rows[0].customer_capacity);
					    $('#add_edit_form #customer_status').combobox('setValue',rows[0].customer_status);
					    $('#add_edit_form #man').val(rows[0].man);
					    $('#add_edit_form #tel').val(rows[0].tel);
					    $('#add_edit_form #emailAddr').val(rows[0].emailAddr);
					    $('#add_edit_form #address').val(rows[0].address);
					    $('#add_edit_form #is_valid').combobox('setValue',rows[0].is_valid);
					    $('#add_edit_form #create_man').val(rows[0].create_man);
						$('#add_edit_form .date').datetimebox('setValue',rows[0].created_date);//日期
					    $('#add_edit_form input[name="created_date"]').val(rows[0].created_date);//日期
					    $('#add_edit_form #area').val(rows[0].area);
					    $('#add_edit_form #power_org').val(rows[0].power_org);
					    $('#add_edit_form #remarks').val(rows[0].remarks);
					    $('#add_edit_form #customer_status_name').val(rows[0].customer_status_name);
					    $('#add_edit_form #customer_type_name').val(rows[0].customer_type_name);
					    $('#add_edit_form #industry_name').val(rows[0].industry_name);
					    $('#add_edit_form #company_id').val(rows[0].company_id);
						all_obj.update('编辑客户资料','${path}/customer/update');//上传编辑资料
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
							delCustomerIdArray:id
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
									select_id.push(checked[i].customer_id);//取ID
								}
								obj.delete_ajax('${path}/customer/delete',select_id.join(','));//上传ID删除
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