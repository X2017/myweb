<!DOCTYPE html>
<html>
	<head>
		<title>终端管理</title>
		<meta charset="UTF-8">
		<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
		<%@ include file="/WEB-INF/jsp/include/taglib.jsp"%>
		<%@ include file="/WEB-INF/jsp/include/easyui.jsp"%>
		<!-- EasyUI datagrid表格和表单CSS-->
		<link href="${path}/js/datagridForm.css" rel="stylesheet" type="text/css"/>
		<link rel="stylesheet" type="text/css" href="${path}/js/select2-4.0.3/dist/css/select2.min.css" />
		<style type="text/css">
			.input{width:290px;height:30px}
		</style>
	</head>
	<body class="easyui-layout" onselectstart="javascript:return false;">
		<!-- 内容加载 -->
		<table id="dg" class="easyui-datagrid" toolbar="#toolbar"></table>
		<!-- 功能 -->
		<div id="tool">
			<div>
				<a href="#" id="add" class="easyui-linkbutton" data-options=" iconCls:'icon-add', plain:true ">新增集中器</a>
				<a href="#" onclick="obj.edit()" class="easyui-linkbutton" data-options=" iconCls:'icon-edit', plain:true ">编辑集中器(黄色背景行)</a>
				<a href="#" onclick="obj.button_delete()" class="easyui-linkbutton" data-options=" iconCls:'icon-remove', plain:true">删除集中器(所有勾选行)</a>
			</div>
			<!-- 搜索功能 -->
			<div class="input_search">
				<label for="search_man">搜索创建人<input type="text" id="search_man" name="search_man" class="input"></label>
				<label for="search_ip">IP地址<input type="text" id="search_ip" name="search_ip" class="input"></label>
				<label for="search_date" style="margin-right:15px">创建时间<input type="text" id="search_date" name="search_date" class="easyui-datebox posi" data-options="editable:false"></label>
				<a href="#" id="search" class="easyui-linkbutton" iconCls="icon-search" >搜索</a>
			</div>
			<!-- 内容区右键功能 -->
			<div id="rightHand" class="easyui-menu" style="width:120px">
				<div id="right_add" iconCls="icon-add" style="height:25px;">新增集中器</div>
				<div onclick="obj.edit()" iconCls="icon-edit" style="height:25px;">编辑集中器</div>
				<div onclick="obj.right_delete()" iconCls="icon-remove" style="height:25px;">删除集中器</div>
			</div>
		</div>
		<!-- 新增采集器表单 修改数据也使用这个表单 -->
		<form id="add_edit_form" action="">
			<div class="add_edit_form_left">
				<div><label for="term_addr">逻辑地址</label><input type="text" id="term_addr" name="term_addr"  class="easyui-validatebox input" required="true" placeholder="此项必填"></div>
				<div><label for="term_name">终端名称</label><input type="text" id="term_name" name="term_name"  class="easyui-validatebox input" required="true" placeholder="此项必填"></div>
				<div><label for="phoneNo">终端sim卡号码</label><input type="text" id="phoneNo" name="phoneNo" class="input"></div>
				<div><label for="simNo">终端sim卡序列号</label><input type="text" id="simNo" name="simNo" class="input"></div>
				<div><label for="ip">终端IP地址</label><input type="text" id="ip" name="ip" class="input"></div>
				<div>
					<label for="term_status">终端状态</label>
					<input type="text" id="term_status" name="term_status" class="input">
				</div>
				<div><label for="create_man">创建人</label><input type="text" id="create_man" name="create_man" class="input"></div>
				<div id="date"><label for="create_date">创建时间</label><input type="text" id="create_date" name="create_date" data-options="editable:false" class="input date"></div>
			</div>
			<div class="add_edit_form_right">
				<div>
					<label for="is_valid">是否有效</label>
					<input type="text" id="is_valid" name="is_valid" class="input">
				</div>
				<div><label for="term_asset_no">终端资产编号</label><input type="text" id="term_asset_no" name="term_asset_no" class="input"></div>
				<div><label for="term_manufacturer">终端型号</label><input type="text" id="term_manufacturer" name="term_manufacturer" class="input"></div>
				<div>
					<label for="term_pro">终端通信规约</label>
					<input type="text" id="term_pro" name="term_pro" class="input">
				</div>
				<div>
					<label for="_easyui_textbox_input4">接线方式</label>
					<input type="text" id="phase" name="phase" class="input">
				</div>
				<div><label for="remarks">备注</label><input type="text" id="remarks" name="remarks" class="input"></div>
				<div>
					<!-- <label for="customer_id">所属用户ID</label>
					<select id="customer_id" name="customer_id" class="input" style="width:215px;height:30px;"></select> -->
					<label for="pulldown">所属客户</label>
					<select id="pulldown" name="pulldown" style="width: 300px;"></select>
					<input id="customer_id" name="customer_id" style="display:none;">
				</div>
			</div>
		</form>
		<!-- easyui-datagrid 通用设置和方法-->
		<script src="${path}/js/datagrid.js" type="text/javascript"></script>
		<script type="text/javascript" src="${path}/js/select2-4.0.3/dist/js/select2.full.min.js"></script>
		<script type="text/javascript" src="${path}/js/select2-4.0.3/dist/js/i18n/zh-CN.js"></script>
		<script type="text/javascript">
			//DOM后加载数据
			var data = ${enterpriseinfo};
			$(function(){
				$('#dg').datagrid({
				    url:'${path}/terminal/datagrid',
				    pageSize:10,//显示数据大小 page
			    	pageList:[10,20,30],//下拉分页 rows
				    columns:[[
				        {field:'term_id',title:'终端编号',sortable:true,width:100,checkbox:true},
				        {field:'term_addr',title:'逻辑地址',sortable:true,width:100},
				        {field:'term_name',title:'终端名称',sortable:true,width:100},
				        {field:'phoneNo',title:'终端sim卡号码',sortable:true,width:100},
				        {field:'simNo',title:'终端sim卡序列号',sortable:true,width:150},
				        {field:'ip',title:'终端IP地址',sortable:true,width:100},
				        {field:'term_status',title:'终端状态',sortable:true,width:100,formatter:function(row,index,value){
					    	if(row==1){//1未运行 2运行 3报停  3销户'
					    		return "未运行";
					    	}else if(row==2){
					    		return"正在运行";
					    	}else if(row==3){
					    		return"报停";
					    	}else if(row==4){
					    		return"拆除";
					    	}
					    }},
				        {field:'create_man',title:'创建人',sortable:true,width:100},
				        {field:'create_date',title:'创建时间',sortable:true,width:100},
				        {field:'is_valid',title:'是否有效',sortable:true,width:100,formatter:function(row,index,value){
					    	if(row==0){
					    	return "无效";
					    	}else if(row ==1){
					    	return "有效";
					    	}
					    }},
				        {field:'term_asset_no',title:'终端资产编号',sortable:true,width:100},
				        {field:'term_manufacturer',title:'终端型号',sortable:true,width:100},
				        {field:'term_pro',title:'终端通信规约',sortable:true,width:100,formatter:function(row,index,value){
					    	if(row==1){//1未运行 2运行 3报停  3销户'
					    		return "电力能效3-1规约";
					    	}else if(row==2){
					    		return"耐奇Modbus规约";
					    	}else if(row==3){
					    		return"国网698规约";
					    	}else if(row==4){
					    		return"广电0903规约";
					    	}
					    }},
				        {field:'phase',title:'接线方式',sortable:true,width:100,formatter:function(row,index,value){
					    	if(row==1){
					    		return "三相三线";
					    	}else if(row ==2){
					    		return "三相四线";
					    	}
					    }},
				        {field:'remarks',title:'备注',sortable:true,width:100},
				        {field:'customer_id',title:'所属客户',sortable:true,width:100,formatter:function(row,index,value){
					   		for(var i=0;i<data.length;i++){
					   			if(data[i].id==row){
					   				return data[i].text;
					   			}
					   		}
					   	 }}
				    ]],
				    onRowContextMenu:function (e,rowIndex,rowData) {// 显示右键功能
						e.preventDefault();
						$('#rightHand').menu('show',{
							left:e.pageX,
							top:e.pageY
						});
						obj.right_delete = function () {
							$.messager.confirm('删除提示','是否删除终端名称为 <strong>'+rowData.term_name+'</strong> 的数据?',function (flag) {
								if (flag) {
									obj.delete_ajax('${path}/terminal/delete',rowData.term_id);//右键获取ID上传删除
								}
							});
						};
					},
				});
				//表单下拉框
			    $('#term_status').combobox({valueField:'id',textField:'text',height:'30',editable:false,data:[{"id":01,"text":"1:未运行"},{"id":02,"text":"2:正在运行"},{"id":03,"text":"3:报停"},{"id":04,"text":"4:拆除"}]});
				$('#is_valid').combobox({valueField:'id',textField:'text',height:'30',editable:false,data:[{"id":1,"text":"1:有效"},{"id":0,"text":"0:无效"}]});
				$('#term_pro').combobox({valueField:'id',textField:'text',height:'30',editable:false,data:[{"id":01,"text":"1：电力能效3-1规约"},{"id":02,"text":"2：耐奇Modbus规约"},{"id":03,"text":"3：国网698规约"},{"id":04,"text":"4：广电0903规约"}]});
				$('#phase').combobox({valueField:'id',textField:'text',height:'30',editable:false,data:[{"id":01,"text":"1：三相三线"},{"id":02,"text":"2：三相四线"}]});
				//输入搜索框
				all_obj.inputSearch("#pulldown","enterpriseinfo/info");
				//按钮新增和右键新增
			    $('#add,#right_add').click(function(){
			    	all_obj.add('新增采集器','${path}/terminal/add',function(){
			    		if($("#pulldown").select2("val") != null){
			    			$("#customer_id").val($("#pulldown").select2("val")[0]);
			    		}
			    	},function(){
			    		 $("#pulldown").empty();
			    	});
			    });
			    //搜索
			    $('#search').click(function(){
			    	if($('#search_ip').val() != '' && !/^[0-9\.]+$/.test($('input[name="search_ip"]').val())){//判断IP是否填写错误
						$.messager.alert('IP地址错误提示','<strong style="font-size:15px;">IP地址错误,请重新填写!</strong>','warning');
					}else if ($('#search_man').val() != '' || $('input[name="search_date"]').val() != '' || /^[0-9\.]+$/.test($('input[name="search_ip"]').val())){
						all_obj.search('#search_man','#search_ip','input[name="search_date"]');//传ID 时间name
					}else {//搜索框全部为空时 搜索全部
						$('#dg').datagrid('load',{search_val_1:''});
					}
			    });
			    //表单验证
			    $('#phoneNo').validatebox({validType:'number_'});
			    $('#simNo').validatebox({validType:'letter'});
			    $('#ip').validatebox({validType:'ip'});
			    $('#date .validatebox-text').validatebox({required:true});
			    
			});
			//编辑和删除方法
			var obj={
				// 打开采集器表单 修改
				edit:function(url,id){
					var rows = $('#dg').datagrid('getSelections');
					if (rows.length > 1) {
						$.messager.alert('编辑提示','只能选择一条数据编辑!','warning');
					}else if (rows.length == 1){
						if($('#add_edit_form').find('.id').size() <= 0){
							$($('#add_edit_form').find('.add_edit_form_left').find('div').get(0)).before('<div class="id"><label for="term_id">终端ID编号<span class=read>(只读)</span></label><input type="text" id="term_id" name="term_id" class="input" readonly="true"></div>');
							$('.id').find('.input').css({background:'#ccc',color:'#575757'});
						}
						//编辑时获取行数据
						$('#add_edit_form #term_id').val(rows[0].term_id);
						$('#add_edit_form #term_addr').val(rows[0].term_addr);
						$('#add_edit_form #term_name').val(rows[0].term_name);
						$('#add_edit_form #phoneNo').val(rows[0].phoneNo);
						$('#add_edit_form #simNo').val(rows[0].simNo);
						$('#add_edit_form #ip').val(rows[0].ip);
						$('#add_edit_form #term_status').combobox('setValue',rows[0].term_status);
						$('#add_edit_form #create_man').val(rows[0].create_man);
						$('#add_edit_form .date').datetimebox('setValue',rows[0].create_date);//日期
						$('#add_edit_form input[name="create_date"]').val(rows[0].create_date);//日期
						$('#add_edit_form #is_valid').combobox('setValue',rows[0].is_valid);
						$('#add_edit_form #term_asset_no').val(rows[0].term_asset_no);
						$('#add_edit_form #term_manufacturer').val(rows[0].term_manufacturer);
						$('#add_edit_form #term_pro').combobox('setValue',rows[0].term_pro);
						$('#add_edit_form #phase').combobox('setValue',rows[0].phase);
						$('#add_edit_form #remarks').val(rows[0].remarks);
					    $('#add_edit_form #customer_id').val(rows[0].customer_id);//所属终端ID从服务器获取
					    if(rows[0].customer_id != ""){
						    $("#pulldown").html("<option id=customer_id_select value="+rows[0].customer_id+" selected=selected>"+all_obj.getText(data,rows[0].customer_id)+"</option>");
					    }
						all_obj.update('编辑终端资料','${path}/terminal/update',function(){
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
				// 按钮删除和右键删除调用此方法
				delete_ajax:function(url,id){//传删除地址和获取的ID
					$.ajax({
						type:'post',
						dataType:'json',
						url:url,
						data:{
							delTermainlIdArray:id
						},
						beforeSend:function (jqXHR,setting) {
							$('#dg').datagrid('loading');
						},
						success:function (data) {
							$('#dg').datagrid('loaded');
							if (data.success) {
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
									select_id.push(checked[i].term_id);//取ID
								}
								obj.delete_ajax('${path}/terminal/delete',select_id.join(','));//上传ID删除
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