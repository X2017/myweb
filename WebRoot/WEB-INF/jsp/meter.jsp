<!DOCTYPE html>
<html>
	<head>
		<title>计量点管理</title>
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
			.add_edit_form_left label{width:120px;}
			.add_edit_form_right label{width:125px;}
			#add_edit_form .input{width:200px;height:25px;font-size:15px;}
		</style>
	</head>
	<body class="easyui-layout" onselectstart="javascript:return false;">
		<table id="dg" class="easyui-datagrid" toolbar="#toolbar"></table>
		<!-- 功能 -->
		<div id="tool">
			<div>
				<a href="#" id="add" class="easyui-linkbutton" data-options=" iconCls:'icon-add', plain:true ">新增计量点</a>
				<a href="#" onclick="obj.edit()" class="easyui-linkbutton" data-options=" iconCls:'icon-edit', plain:true ">编辑计量点(黄色背景行)</a>
				<a href="#" onclick="obj.button_delete()" class="easyui-linkbutton" data-options=" iconCls:'icon-remove', plain:true">删除计量点(所有勾选行)</a>
			</div>
			<!-- 搜索功能 -->
			<div class="input_search">
				<label for="search_mp_no">计量点编号<input type="text" id="search_mp_no" name="search_mp_no"  class="input"></label>
				<label for="search_name">计量点名称<input type="text" id="search_name" name="search_name" class="input"></label>
				<label for="search_create_man">创建人<input type="text" id="search_create_man" name="search_create_man" class="input"></label>
				<label for="search_create_date"  style="margin-right:15px">创建时间<input type="text" id="search_create_date" name="search_create_date" class="easyui-datebox" data-options="editable:false"></label>
				<a href="#" id="search" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
			</div>
			<!-- 内容区右键功能 -->
			<div id="rightHand" class="easyui-menu" style="width:120px">
				<div id="right_add" iconCls="icon-add" style="height:25px;">新增计量点</div>
				<div onclick="obj.edit()" iconCls="icon-edit" style="height:25px;">编辑计量点</div>
				<div onclick="obj.right_delete()" iconCls="icon-remove" style="height:25px;">删除计量点</div>
			</div>
		</div>
		<!-- 新增计量点表单 修改数据也使用这个表单 -->
		<form id="add_edit_form" action="">
			<div class="add_edit_form_left">
				<div><label for="mp_no">计量点编号</label><input placeholder="此项必填"  class="easyui-validatebox input" required="true" type="text" id="mp_no" name="mp_no"></div>
				<div><label for="comm_no">顺序号</label><input placeholder="此项必填" class="easyui-validatebox input" required="true" type="text" id="comm_no" name="comm_no"></div>
				<div><label for="mp_name">计量点名称</label><input type="text" id="mp_name" name="mp_name" class="input"></div>
				<div>
					<label for="mp_type">计量点类型</label>
					<input type="text" id="mp_type" name="mp_type" class="input">
				</div>
				<div>
					<label for="industry_type">行业类别</label>
					<input type="text" id="industry_type" name="industry_type" class="input">
				</div>
				<div>
					<label for="mp_class">计量点分类</label>
					<input type="text" id="mp_class" name="mp_class" class="input">
				</div>
				<div><label for="main_mp_id">主计量点ID</label><input type="text" id="main_mp_id" name="main_mp_id" class="input"></div>
				<div><label for="ct">CT变比</label><input placeholder="此项必填" value="1/1" class="easyui-validatebox input" required="true" type="text" id="ct" name="ct"></div>
				<div><label for="pt">PT变比</label><input placeholder="此项必填" value="1/1" class="easyui-validatebox input" required="true" type="text" id="pt" name="pt"></div>
				<div><label for="ct_pt">综合倍率</label><input placeholder="此项自动计算CT和PT值" value="1" class="input" readonly="true" type="text" id="ct_pt" name="ct_pt"></div>
				<div><label for="create_man">创建人</label><input type="text" id="create_man" name="create_man" class="input"></div>
				<div id="date"><label for="create_date">创建时间</label><input type="text" id="create_date" name="create_date" data-options="editable:false" class="input date"></div>
				<div><label for="mp_status">计量点状态</label><input type="text" class="easyui-validatebox input" required="true" id="mp_status" name="mp_status"></div>
				<div><label for="mp_asset_no">计量点资产编号</label><input type="text" id="mp_asset_no" name="mp_asset_no" class="input"></div>
				<div><label for="amm_protocol_addr">电表通讯地址</label><input placeholder="此项必填" type="text" id="amm_protocol_addr" name="amm_protocol_addr" class="easyui-validatebox input" required="true"></div>
				<div>
					<label for="amm_protocol">电表通讯规约</label>
					<input type="text" id="amm_protocol" name="amm_protocol" class="input">
				</div>
				<div><label for="rated_voltage">额定电压</label><input type="text" id="rated_voltage" name="rated_voltage" class="input"></div>
			</div>
			<div class="add_edit_form_right">
				<div><label for="rated_current">额定电流</label><input type="text" id="rated_current" name="rated_current" class="input"></div>
				<div>
					<label for="amm_class">电表类别</label>
					<input type="text" id="amm_class" name="amm_class" class="input">
				</div>
				<div>
					<label for="amm_manufacturer">电表厂家</label>
					<input type="text" id="amm_manufacturer" name="amm_manufacturer" class="input">
				</div>
				<div>
					<label for="amm_type">电表型号</label>
					<input type="text" id="amm_type" name="amm_type" class="input">
				</div>
				<div>
					<label for="mp_addr">计量点安装位置</label>
					<input type="text" id="mp_addr" name="mp_addr" class="input">
				</div>
				<div>
					<label for="phase">接线方式</label>
					<input type="text" id="phase" name="phase" class="input">
				</div>
				<div>
					<label for="customer_id">所属客户</label>
					<input type="text" id="customer_id" name="customer_id" class="input" style="display:none;">
					<select id="pulldown" name="pulldown" style="width: 210px;"></select>
				</div>
				<div>
					<label for="term_id">所属终端</label>
					<select id="pulldown_term_id" name="pulldown_2" style="width: 210px;"></select>
					<input type="text" id="term_id" name="term_id" class="input" style="display:none;"></div>
				<div>
					<label for="transformer_id">所属变压器</label>
					<select id="pulldown_transformer_id" name="pulldown_2" style="width: 210px;"></select>
					<input type="text" id="transformer_id" name="transformer_id" class="input" style="display:none;"></div>
				<div><label for="remarks">备注</label><input type="text" id="remarks" name="remarks" class="input"></div>
				<div><label for="check_demand">核定需量</label><input type="text" id="check_demand" name="check_demand" class="input"></div>
				<div><label for="mp_p_e">起始表底值</label><input type="text" id="mp_p_e" name="mp_p_e" class="input"></div>
				<div>
					<label for="dl_ti">电量采集间隔</label>
					<input type="text" id="dl_ti" name="dl_ti" class="input">
				</div>
				<div>
					<label for="equipment_no">所属监测设备</label>
					<input type="text" id="equipment_no" name="equipment_no" class="input">
				</div>
				<div><label for="group_id">所属组</label><input type="text" id="group_id" name="group_id" class="input"></div>
				<div>
					<label for="is_main_obj">是否重点能耗监测</label>
					<input type="text" id="is_main_obj" name="is_main_obj" class="input">
				</div>
			</div>
		</form>
		<!-- easyui-datagrid 通用设置和方法-->
		<script src="${path}/js/datagrid.js" type="text/javascript"></script>
		<script type="text/javascript" src="${path}/js/select2-4.0.3/dist/js/select2.full.min.js"></script>
		<script type="text/javascript" src="${path}/js/select2-4.0.3/dist/js/i18n/zh-CN.js"></script>
		<script type="text/javascript">
			//DOM后加载数据
			var termInfo= ${termInfo};
			var transformerInfo= ${transformerInfo};
			$(function(){
				//输入搜索框
				all_obj.inputSearch("#pulldown","enterpriseinfo/info");
				all_obj.inputSearch("#pulldown_term_id","term/info");//终端
				all_obj.inputSearch("#pulldown_transformer_id","transformer/info");//变压器
				var customer_id,term_id,transformer_id;
				$('#dg').datagrid({
				    url:'${path}/meter/datagrid',
				    columns:[[
				        {field:'mp_id',title:'ID',sortable:true,width:100,checkbox:true},
				        {field:'mp_no',title:'编号',sortable:true,width:180},
				        {field:'comm_no',title:'顺序号',sortable:true,width:0},
				        {field:'mp_name',title:'名称',sortable:true,width:300},
				        {field:'mp_type',title:'用电类型',sortable:true,width:0,formatter:function(row,index,value){//:生产用电 2:生产辅助用电 3:办公生活用电
					   			if(1 == row){return "生产用电";}
					   			else if(2 == row){return "生产辅助用电";}
					   			else if(3 == row){return "办公生活用电";}
					   			else {return "其他用电类型"}
					   	 }},
				        {field:'industry_type',title:'行业类别',sortable:true,width:100},
				        {field:'mp_class',title:'分类',sortable:true,width:0},
				        {field:'main_mp_id',title:'主计量点ID',sortable:true,width:100},
				        {field:'ct',title:'CT变比',sortable:true,width:80},
				        {field:'pt',title:'PT变比',sortable:true,width:80},
				        {field:'ct_pt',title:'综合倍率',sortable:true,width:100},
				        {field:'create_man',title:'创建人',sortable:true,width:80},
				        {field:'create_date',title:'创建时间',sortable:true},
				        {field:'mp_status',title:'状态',sortable:true,width:0},
				        {field:'mp_asset_no',title:'资产编号',sortable:true,width:100},
				        {field:'amm_protocol_addr',title:'通讯地址',sortable:true,width:100},
				        {field:'amm_protocol',title:'通讯规约',sortable:true,width:100},
				        {field:'rated_voltage',title:'额定电压',sortable:true,width:100},
				        {field:'rated_current',title:'额定电流',sortable:true,width:100},
				        {field:'amm_class',title:'电表类别',sortable:true,width:100},
				        {field:'amm_manufacturer',title:'电表厂家',sortable:true,width:100},
				        {field:'amm_type',title:'电表型号',sortable:true,width:100},
				        {field:'mp_addr',title:'安装位置',sortable:true,width:100},
				        {field:'phase',title:'接线方式',sortable:true,width:100},
				        {field:'customer_name',title:'所属客户',sortable:true,width:150},
				        {field:'customer_id',title:'所属客户',sortable:true,width:150,hidden:true},
				        {field:'term_id',title:'所属终端',sortable:true,width:100,formatter:function(row,index,value){
					   		for(var i=0;i<termInfo.length;i++){
					   			if(termInfo[i].id==row){
					   				return termInfo[i].text;
					   			}
					   		}
					   	 }},
				        {field:'transformer_id',title:'所属变压器',sortable:true,width:100,formatter:function(row,index,value){
					   		for(var i=0;i<transformerInfo.length;i++){
					   			if(transformerInfo[i].id==row){
					   				return transformerInfo[i].text;
					   			}
					   		}
					   	 }},
				        {field:'remarks',title:'备注',sortable:true,width:0,hidden:true},
				        {field:'mp_p_e',title:'起始表底值',sortable:true,width:100},
				        {field:'dl_ti',title:'采集间隔',sortable:true,width:100},
				        {field:'equipment_no',title:'监测设备',sortable:true,width:100},
				        {field:'group_id',title:'所属组',sortable:true,width:80,hidden:true},
				        {field:'is_main_obj',title:'是否重点能耗监测',sortable:true,width:180,hidden:true},
				        {field:'check_demand',title:'核定需量',sortable:true,width:100,hidden:true}
				    ]],
				    onRowContextMenu:function(e,rowIndex,rowData){// 显示右键功能
						e.preventDefault();
						$('#rightHand').menu('show',{
							left:e.pageX,
							top:e.pageY
						});
						obj.right_delete=function(){
							$.messager.confirm('删除提示','是否删除计量点名称为 <strong>'+rowData.mp_name+'</strong> 的数据?',function (flag) {
								if (flag) {
									obj.delete_ajax('${path}/meter/delete',rowData.mp_id);//右键获取ID上传删除
								}
							});
						};
					},
				});
				//表单下拉框
				$('#mp_type').combobox({valueField:'id',textField:'text',height:'30',editable:false,data:[{"id":01,"text":"1、生产用电 "},{"id":02,"text":"2、生产辅助用电 "},{"id":03,"text":"3、办公生活用电"}]});
				$('#industry_type').combobox({valueField:'id',textField:'text',height:'30',editable:false,data:[{"id":"Z0000","text":"Z0000全社会用电总计"},{"id":"X000","text":"X0000社会各行业用电"},{"id":"X0100","text":"X0100第一产业"},{"id":"X0200","text":"X0200第二产业"},{"id":"X0300","text":"X0300第三产业"},{"id":"Y0000","text":"Y0000城乡居民生活用电量"}]});
				$('#mp_class').combobox({valueField:'id',textField:'text',height:'30',editable:false,data:[{"id":01,"text":"1、主表"},{"id":02,"text":"2、子表"}]});
				$('#amm_protocol').combobox({valueField:'id',textField:'text',height:'30',editable:false,data:[{"id":01,"text":"1、电力能效终端4-1协议"},{"id":02,"text":"2、耐奇Modbus规约"}]});
				$('#amm_class').combobox({valueField:'id',textField:'text',height:'30',editable:false,data:[{"id":01,"text":"1、AMM_CLASS_1"},{"id":02,"text":"2、AMM_CLASS_2"}]});
				$('#amm_manufacturer').combobox({valueField:'id',textField:'text',height:'30',editable:false,data:[{"id":01,"text":"1、电表厂家1"},{"id":02,"text":"2、电表"}]});
				$('#amm_type').combobox({valueField:'id',textField:'text',height:'30',editable:false,data:[{"id":01,"text":"1、电表类型1"},{"id":02,"text":"2、电表类型2"}]});
				$('#mp_addr').combobox({valueField:'id',textField:'text',height:'30',editable:false,data:[{"id":01,"text":"1、高压"},{"id":02,"text":"2、低压"}]});
				$('#phase').combobox({valueField:'id',textField:'text',height:'30',editable:false,data:[{"id":01,"text":"1、三相三线"},{"id":02,"text":"2、三相四线"}]});
				$('#dl_ti').combobox({valueField:'id',textField:'text',height:'30',editable:false,data:[{"id":01,"text":"1秒"}]});
				$('#equipment_no').combobox({valueField:'id',textField:'text',height:'30',editable:false,data:[{"id":01,"text":"1：设备1"},{"id":02,"text":"1：设备2"}]});
				$('#is_main_obj').combobox({valueField:'id',textField:'text',height:'30',editable:false,value:01,data:[{"id":01,"text":"1：是"},{"id":0,"text":"1：否"}]});
				// 增加 修改 计量点表单
			    $('#add,#right_add').click(function(){
			    	$("#amm_protocol_addr,#mp_no").validatebox({
				    	validType:"vaCheckValue"
				    });
			    	all_obj.add('新增计量点','${path}/meter/add',function(){
			    		if($("#pulldown").select2("val") != null){
			    			$("#customer_id").val($("#pulldown").select2("val")[0]);
			    		}
			    		if($("#pulldown_term_id").select2("val") != null){//终端
			    			$("#term_id").val($("#pulldown_term_id").select2("val")[0]);
			    		}
			    		if($("#pulldown_transformer_id").select2("val") != null){//变压器
			    			$("#transformer_id").val($("#pulldown_transformer_id").select2("val")[0]);
			    		}
			    	},function(){
			    		$("#pulldown").empty();
						$("#pulldown_term_id").empty();
						$("#pulldown_transformer_id").empty();
						$("#amm_protocol_addr,#mp_no").validatebox({
					    	validType:"empty"
					    });
			    	});
			    });
			    //搜索
		    	$('#search').click(function(){
		    		if($('#search_mp_no').val() != '' || $('#search_name').val() != '' || $('#search_create_man').val() != '' || $('input[name="search_create_date"]').val() != ''){//判断
						$('#dg').datagrid('load',{
							search_val_1:$.trim($('#search_mp_no').val()),
							search_val_2:$.trim($('#search_name').val()),
							search_val_3:$.trim($('#search_create_man').val()),
							search_date:$('input[name="search_create_date"]').val()
						});
					}else {//搜索框全部为空时 搜索全部
						$('#dg').datagrid('load',{search_val_1:''});
					}
		    	});
		    	//表单验证
			    $('#comm_no').validatebox({validType:'number'});
			    $('#main_mp_id').validatebox({validType:'number'});
			    $('#customer_id').validatebox({validType:'number'});
			    $('#term_id').validatebox({validType:'number'});
			    $('#transformer_id').validatebox({validType:'number'});
			    //$('#ct_pt').validatebox({validType:'reg_short'});
			    $('#date .validatebox-text').validatebox({required:true});
			    $("#ct,#pt").validatebox({
			    	validType:"divi"
			    }).blur(function(){
			    	var divi = /[1-9]\d*\/[1-9]\d*/;
			    	var num = /^\d+(\d+)?$/;
			    	var _this = $(this).val();
			    	if(_this != "" && divi.test(_this)){
			    		var arr = $(this).val().split('/');
			    		$(this).attr("title",arr[0] / arr[1]);
			    	}else {
			    		if(num.test(_this)){
			    			$(this).attr("title",_this);
			    		}
			    	}
			    	var reg = /^[0-9]+\.?[0-9]*/;
			    	var ct = $("#ct").attr("title");
			    	var pt = $("#pt").attr("title");
			    	if(reg.test(ct) && reg.test(pt)){
			    		var val = $("#ct").attr("title") * $("#pt").attr("title");
			    		$('#ct_pt').val(parseInt(val));
			    	}
			    });
			});
			//扩展验证方法  
			$.extend($.fn.validatebox.defaults.rules, {  
			    vaCheckValue:{
			        validator: function (value) {  
			            var checkR=$.ajax({  
			                async : false,    
			                cache : false,  
			                type : "post",    
			                url : "meter/exist",
			                data : {    
			                    "data": value
			                }   
			            }).responseText;
			            return $.parseJSON(checkR) === "true";
			        },  
			        message: '数据库已存在, 请重新输入'
			    },
			});
			//编辑和删除方法
			var obj={
				// 修改采集器表单
				edit:function(){
					var rows = $('#dg').datagrid('getSelections');
					if (rows.length > 1) {
						$.messager.alert('编辑提示','只能选择一条数据编辑!','warning');
					}else if (rows.length == 1){
						if($('#add_edit_form').find('.id').size() <= 0){
							$($('#add_edit_form').find('.add_edit_form_left').find('div').get(0)).before('<div class="id"><label for="mp_id">计量点ID<span class=read>(只读)</span></label><input type="text" id="mp_id" name="mp_id" class="input" readonly="true"></div>');
							$('.id').find('.input').css({background:'#ccc',color:'#575757'});
						}
					    $("#amm_protocol_addr,#mp_no").change(function(){
					    	$(this).validatebox({
						    	validType:"vaCheckValue"
						    });
					    });
						//编辑时获取行数据
						$('#add_edit_form #mp_id').val(rows[0].mp_id);//ID
						$('#add_edit_form #mp_no').val(rows[0].mp_no);//计量点编号
						$('#add_edit_form #comm_no').val(rows[0].comm_no);//顺序号
						$('#add_edit_form #mp_name').val(rows[0].mp_name);//计量点名称
						$('#add_edit_form #mp_type').combobox('setValue',rows[0].mp_type);//计量点类型
						$('#add_edit_form #industry_type').combobox('setValue',rows[0].industry_type);//行业类别
						$('#add_edit_form #mp_class').combobox('setValue',rows[0].mp_class);//计量点分类
						$('#add_edit_form #main_mp_id').val(rows[0].main_mp_id);//主计量点ID
						$('#add_edit_form .date').datetimebox('setValue',rows[0].create_date);//创建时间
						$('#add_edit_form input[name="create_date"]').val(rows[0].create_date);//创建时间
						$('#add_edit_form #mp_status').val(rows[0].mp_status);//计量点状态
						$('#add_edit_form #mp_asset_no').val(rows[0].mp_asset_no);//计量点资产编号
						$('#add_edit_form #amm_protocol_addr').val(rows[0].amm_protocol_addr);//电表通讯地址
						$('#add_edit_form #amm_protocol').combobox('setValue',rows[0].amm_protocol);//电表通讯规约
						$('#add_edit_form #rated_voltage').val(rows[0].rated_voltage);//额定电压
						$('#add_edit_form #rated_current').val(rows[0].rated_current);//额定电流
						$('#add_edit_form #amm_class').combobox('setValue',rows[0].amm_class);//电表类别
						$('#add_edit_form #amm_manufacturer').combobox('setValue',rows[0].amm_manufacturer);//电表厂家
						$('#add_edit_form #amm_type').combobox('setValue',rows[0].amm_type);//电表型号
						$('#add_edit_form #mp_addr').combobox('setValue',rows[0].mp_addr);//计量点安装位置
						$('#add_edit_form #phase').combobox('setValue',rows[0].phase);//接线方式
						$('#add_edit_form #customer_id').val(rows[0].customer_id);//所属用户ID
						$('#add_edit_form #term_id').val(rows[0].term_id);//所属终端ID
						$('#add_edit_form #transformer_id').val(rows[0].transformer_id);//所属变压器ID
						$('#add_edit_form #remarks').val(rows[0].remarks);//备注
						$('#add_edit_form #mp_p_e').val(rows[0].mp_p_e);//起始表底值
						$('#add_edit_form #dl_ti').combobox('setValue',rows[0].dl_ti);//电量采集间隔
						$('#add_edit_form #equipment_no').combobox('setValue',rows[0].equipment_no);//所属监测设备
						$('#add_edit_form #group_id').val(rows[0].group_id);//所属组
						$('#add_edit_form #is_main_obj').combobox('setValue',rows[0].is_main_obj);//是否重点能耗监测
						$('#add_edit_form #check_demand').val(rows[0].check_demand);//核定需量
						$('#add_edit_form #ct').val(rows[0].ct);//CT变比
						$('#add_edit_form #pt').val(rows[0].pt);//PT变比
						$('#add_edit_form #ct_pt').val(rows[0].ct_pt);//综合倍率
						if(rows[0].customer_id != ""){
							$("#pulldown").html("<option id=customer_id_select value="+rows[0].customer_id+" selected=selected>"+rows[0].customer_name+"</option>");
						}
						if(rows[0].term_id != ""){
							$("#pulldown_term_id").html("<option id=term_id_select value="+rows[0].term_id+" selected=selected>"+all_obj.getText(termInfo,rows[0].term_id)+"</option>");
						}
						if(rows[0].transformer_id != ""){
							$("#pulldown_transformer_id").html("<option id=transformer_id_select value="+rows[0].transformer_id+" selected=selected>"+all_obj.getText(transformerInfo,rows[0].transformer_id)+"</option>");
						}
						all_obj.update('编辑计量点资料','${path}/meter/update',function(){
							if($("#pulldown").select2("val") != null){
				    			$("#customer_id").val($("#pulldown").select2("val")[0]);
				    		}
				    		if($("#pulldown_term_id").select2("val") != null){//终端
				    			$("#term_id").val($("#pulldown_term_id").select2("val")[0]);
				    		}
				    		if($("#pulldown_transformer_id").select2("val") != null){//变压器
				    			$("#transformer_id").val($("#pulldown_transformer_id").select2("val")[0]);
				    		}
						},function(){
							$("#pulldown").empty();
							$("#pulldown_term_id").empty();
							$("#pulldown_transformer_id").empty();
						});////上传编辑资料
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
							delMeterIdArray:id
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
									select_id.push(checked[i].mp_id);//取ID
								}
								obj.delete_ajax('${path}/meter/delete',select_id.join(','));//上传ID删除
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