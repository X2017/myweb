<!DOCTYPE html>
<html>
	<head>
		<title>任务配置及下发</title>
		<meta charset="utf-8">
		<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
		<%@ include file="/WEB-INF/jsp/include/taglib.jsp"%>
		<%@ include file="/WEB-INF/jsp/include/easyui.jsp"%>
		<!-- EasyUI datagrid表格和表单CSS-->
		<link href="${path}/js/datagridForm.css" rel="stylesheet" type="text/css"/>
		<!-- 下拉多选框CSS文件-->
		<link href="${path}/js/multiple-select/multiple-select.css" rel="stylesheet" type="text/css"/>
		<style type="text/css">
			#add_edit_form label{width:125px;}
			#downForm{padding:30px;font-size:15px;overflow:hidden;opacity:0;}
			#downForm div>label{width:173px;display:inline-block;}
			#downForm>div{margin-bottom:15px;}
			#downForm .ms-drop.bottom{width:205px;height:390px;}
			#downForm .readInput,#di_items2{background:#ccc;color:#575757;}
			#downForm #di_items{height:100px;}
			#dataItemListStr2{width:213px;height:100px;resize:none;border: 1px solid #95B8E7;border-radius:3px;}
			.ms-drop ul > li{height:20px;font-size:13px;}
			.ms-drop ul > li.multiple{line-height:20px;}
			.multiple {margin-left:0px;}
		</style>
	</head>
	<body class="easyui-layout" onselectstart="javascript:return false;">
		<table id="dg" class="easyui-datagrid" toolbar="#toolbar"></table>
		<!-- 功能 -->
		<div id="tool">
			<div>
				<a href="#" id="add" class="easyui-linkbutton" data-options=" iconCls:'icon-add', plain:true ">新增任务配置</a>
				<!-- <a href="#" onclick="obj.edit()" class="easyui-linkbutton" data-options=" iconCls:'icon-edit', plain:true ">编辑任务配置(黄色背景行)</a> -->
				<a href="#" onclick="obj.downForm()" class="easyui-linkbutton" data-options=" iconCls:'icon-tip', plain:true ">下发任务(黄色背景行)</a>
				<a href="#" onclick="obj.button_delete()" class="easyui-linkbutton" data-options=" iconCls:'icon-remove', plain:true">删除任务配置(所有勾选行)</a>
			</div>
			<!-- 搜索功能 -->
			<div class="input_search">
				<label for="comm_no">表计号<input type="text" id="comm_no" name="comm_no"  class="input"></label>
				<label for="jn">任务号<input type="text" id="jn" name="jn" class="input"></label>
				<a href="#" id="search" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
			</div>
			<!-- 内容区右键功能 -->
			<div id="rightHand" class="easyui-menu" style="width:120px">
				<div id="right_add" iconCls="icon-add" style="height:25px;">新增任务配置</div>
				<!-- <div onclick="obj.edit()" iconCls="icon-edit" style="height:25px;">编辑任务配置</div> -->
				<div onclick="obj.right_delete()" iconCls="icon-remove" style="height:25px;">删除任务配置</div>
			</div>
		</div>
		<!-- 新增表单 修改数据也使用这个表单 -->
		<form id="add_edit_form" action="">
			<div class="add_edit_form_left">
				<div><label for="ddp_id">终端ID</label><input placeholder="此项必填" class="easyui-validatebox input" required="true" validtype="check_nine" type="text" id="ddp_id" name="ddp_id"></div>
				<div><label for="comm_no">表计号</label><input placeholder="此项必填" class="easyui-validatebox input comm_no" required="true" validtype="length[1,8]" type="text" id="comm_no" name="comm_no"></div>
				<div><label for="jn">任务号</label><input placeholder="此项必填" class="easyui-validatebox input" required="true" validtype="length[1,4]" type="text" id="jn" name="jn"></div>
				<div><label for="tt">任务类型</label><input placeholder="此项必填" class="easyui-validatebox input" required="true" validtype="length[1,2]" type="text" id="tt" name="tt"></div>
				<div><label for="ts">采集基准时间</label><input placeholder="此项必填" class="date input" data-options="editable:false"  type="text" id="ts" name="ts"></div>
				<div>
					<label for="ts_unit">采集基准时间单位</label><input placeholder="此项必填" class="input" type="text" id="ts_unit" name="ts_unit">
				</div>
			</div>
			<div class="add_edit_form_left">
				<div><label for="ti">采集间隔时间</label><input placeholder="此项必填" class="easyui-validatebox input" required="true" validtype="length[1,4]" type="text" id="ti" name="ti"></div>
				<div><label for="rs">上送基准时间</label><input placeholder="此项必填" data-options="editable:false" class="date input" type="text" id="rs" name="rs"></div>
				<div>
					<label for="rs_unit">上送基准时间单位</label><input placeholder="此项必填" class="input" type="text" id="rs_unit" name="rs_unit">
				</div>
				<div><label for="di_items">上送间隔时间</label><input placeholder="此项必填" class="easyui-validatebox input" required="true" validtype="length[1,4]" type="text" id="ri" name="ri"></div>
				<div>
					<label for="task_template_id">任务模板ID</label>
					<!-- <input placeholder="此项必填" class="easyui-validatebox input" required="true" validtype="check_nine" type="text" id="task_template_id" name="task_template_id"> -->
					<select id="task_template_id" name="task_template_id" class="input" style="width:205px;height:30px;"></select>
				</div>
				<div>
					<label for="exe_status">任务执行状态</label><input placeholder="此项必填" class="input" type="text" id="exe_status" name="exe_status">
				</div>
			</div>
		</form>
		<form id="downForm" action="">
			<div style="margin-bottom:0px;">
				<label for="terminalIdSelArrayStr"  style="position:relative;top:-10px;width:165px;">集中器ID<span style="font-size:13px;color:blue;">(点击文本框选择)</span></label>
				<input style="display:none" class="input" type="text" id="terminalIdSelArrayStr" name="terminalIdSelArrayStr">
				<!--下拉多选框  取数据项定义名称 -->
				<select id="term" multiple="multiple" style="width:210px;"></select>
			</div>
			<div><label for="meterNo">表计号</label><input placeholder="此项必填" style="margin-top:8px;" class="easyui-validatebox input" required="true" validtype="length[1,8]" type="text" id="meterNo" name="meterNo"></div>
				<div><label for="taskNo">任务号</label><input placeholder="此项必填" class="easyui-validatebox input" required="true" validtype="length[1,4]" type="text" id="taskNo" name="taskNo"></div>
				<div><label for="taskType">任务类型</label><input placeholder="此项必填" class="easyui-validatebox input" required="true" validtype="length[1,2]" type="text" id="taskType" name="taskType"></div>
				<div><label for="gatherStandardTime">采集基准时间</label><input placeholder="此项必填" class="date input" data-options="editable:false"  type="text" id="gatherStandardTime" name="gatherStandardTime"></div>
				<div>
					<label for="gatherStandardTimeUnit">采集基准时间单位</label><input placeholder="此项必填" class="input" type="text" id="gatherStandardTimeUnit" name="gatherStandardTimeUnit">
				</div>
			</div>
			<div><label for="gatherInterval">采集间隔时间</label><input placeholder="此项必填" class="easyui-validatebox input" required="true" validtype="length[1,4]" type="text" id="gatherInterval" name="gatherInterval"></div>
			<div><label for="reportStandardTime">上送基准时间</label><input placeholder="此项必填" data-options="editable:false" class="date input" type="text" id="reportStandardTime" name="reportStandardTime"></div>
			<div>
				<label for="reportStandardTimeUnit">上送基准时间单位</label><input placeholder="此项必填" class="input" type="text" id="reportStandardTimeUnit" name="reportStandardTimeUnit">
			</div>
			<div><label for="reportInterval">上送间隔时间</label><input placeholder="此项必填" class="easyui-validatebox input" required="true" validtype="length[1,4]" type="text" id="reportInterval" name="reportInterval"></div>
			<div><label for="task_template_id">任务模板ID</label><input readonly="readonly" class="input readInput" type="text" id="task_template_id"></div>
			<div><label for="task_template_name">任务模板名称</label><input readonly="readonly" class="input readInput" type="text" id="task_template_name"></div>
			<div>
				<label for="dataItemListStr" style="position:relative;top:-25px;width:164px;">任务模板包括的数据项</label>
				<input style="display:none;" readonly="readonly" class="input readInput" type="text" id="dataItemListStr" name="dataItemListStr">
				<textarea id="dataItemListStr2" readonly="readonly"></textarea>
			</div>
		</form>
		<!-- easyui-datagrid 通用设置和方法-->
		<script src="${path}/js/datagrid.js" type="text/javascript"></script>
		<!-- 下拉多选框JS文件 -->
		<script src="${path}/js/multiple-select/multiple-select.js" type="text/javascript"></script>
		<script type="text/javascript">
			//DOM后加载数据
			$(function(){
				$('#dg').datagrid({
				    url:'${path}/taskConfig/datagrid',
					columns:[[
				         {field:'ddp_id',title:'终端ID',sortable:true,width:100},
						 {field:'comm_no',title:'表计号',sortable:true,width:100},
						 {field:'jn',title:'任务号',sortable:true,width:100},
						 {field:'tt',title:'任务类型',sortable:true,width:100},
						 {field:'ts',title:'采集基准时间',sortable:true,width:100},
						 {field:'ts_unit',title:'采集基准时间单位',sortable:true,width:100,formatter:function(row,index,value){
					    	if(row==1){return "月";
					    	}else if(row ==2){
					    		return "日";
					    	}else if(row ==3){
					    		return "时";
					    	}else if(row ==4){
					    		return "分";
					    	}else if(row ==5){
					    		return "秒";
					    	}else if(row ==6){
					    		return "毫秒";
					    	}
					    }},
						 {field:'ti',title:'采集间隔时间',sortable:true,width:100},
						 {field:'rs',title:'上送基准时间',sortable:true,width:100},
						 {field:'rs_unit',title:'上送基准时间单位',sortable:true,width:100,formatter:function(row,index,value){
					    	if(row==1){
					    		return "月";
					    	}else if(row ==2){
					    		return "日";
					    	}else if(row ==3){
					    		return "时";
					    	}else if(row ==4){
					    		return "分";
					    	}else if(row ==5){
					    		return "秒";
					    	}else if(row ==6){
					    		return "毫秒";
					    	}
					    }},
						 {field:'ri',title:'上送间隔时间',sortable:true,width:100},
						 {field:'task_template_id',title:'任务模板ID',sortable:true,width:100},
						 {field:'exe_status',title:'任务执行状态',sortable:true,width:100,formatter:function(row,index,value){
					    	if(row==0){
					    		return "成功";
					    	}else if(row ==-5){
					    		return "终端离线";
					    	}else if(row ==-7){
					    		return "设置超时";
					    	}else if(row ==-217){
					    		return "密码权限不足";
					    	}else if(row ==-1){
					    		return "下发失败";
					    	}
					    }}
				    ]],
				    onRowContextMenu:function (e,rowIndex,rowData) {//显示右键功能
						e.preventDefault();
						$('#rightHand').menu('show',{
							left:e.pageX,
							top:e.pageY,
							hideOnUnhover:false
						});
						obj.right_delete = function () {
							$.messager.confirm('删除提示','是否删除表计号为 <strong>'+rowData.ddp_id+'</strong> 的数据?',function (flag) {
								if (flag) {
									obj.delete_ajax('${path}/taskConfig/delete',rowData.ddp_id);//右键获取ID上传删除
								}
							});
						};
					},
				});
				
				
				//采集基准时间单位//上送基准时间单位//任务执行状态
				$('#ts_unit').combobox({valueField:'id',textField:'text',height:'30',required:true,editable:false,data:[{"id":"01","text":"01：月"},{"id":"02","text":"02：日"},{"id":"03","text":"03：时"},{"id":"04","text":"04：分"},{"id":"05","text":"05：秒"},{"id":"06","text":"06：毫秒"}]});
				$('#rs_unit').combobox({valueField:'id',textField:'text',height:'30',required:true,editable:false,data:[{"id":"01","text":"01：月"},{"id":"02","text":"02：日"},{"id":"03","text":"03：时"},{"id":"04","text":"04：分"},{"id":"05","text":"05：秒"},{"id":"06","text":"06：毫秒"}]});
				$('#exe_status').combobox({valueField:'id',textField:'text',height:'30',required:true,editable:false,data:[{"id":"0","text":"0：成功"},{"id":"-5","text":"-5：终端离线"},{"id":"-7","text":"-7：设置超时"},{"id":"-217","text":"-217：密码权限不足"},{"id":"-1","text":"-1：下分失败"}]});
				//按钮新增和右键新增
			    $('#add,#right_add').click(function(){
			    	all_obj.add('新增任务配置','${path}/taskConfig/add');
			    });
			  	//搜索 
		    	$('#search').click(function(){
		    		if($('#comm_no').val() != '' || $('#jn').val() != ''){//判断
						$('#dg').datagrid('load',{
							search_val_1:$.trim($('#comm_no').val()),
							search_val_2:$.trim($('#jn').val())
						});
					}else {//搜索框全部为空时 搜索全部
						$('#dg').datagrid('load',{search_val_1:''});
					}
		    	});
		    	//新增任务模板ID从任务定义获取
			    $.ajax({
			    	url:'${path}/taskConfig/allTemplateId',
			    	type:'post',
			    	beforeSend:function(jqXHR,setting){},
			    	success:function(data){
			    		if(data){
			    			var strJSON = $.parseJSON(data);//得到的JSON
							var obj = new Function("return" + strJSON)();//转换后的JSON对象
							$.each(obj,function(i){
								$('#task_template_id').append('<option value="'+obj[i]+'">'+obj[i]+'</option>');
							});
			    		}else{
			    			$.messager.alert('所属终端ID获取失败提示','所属终端ID从服务器获取失败','warning');
			    		}
			    	},
					error:function(){
						$('#dg').datagrid('loaded');
						$.messager.alert('操作失败提示','任务模板ID,找不到地址或网络错误,请刷新重试!','warning');
					}
			    });
			  	//任务下发集中器ID
			    $.ajax({
			    	url:'${path}/terminal/all',
			    	type:'post',
			    	beforeSend:function(jqXHR,setting){},
			    	success:function(data){
				    	if(data){
				    		var json=$.parseJSON(data);//得到的JSON
				    		var term=[];//term_id term_name 11 14
				    		for(var i in json[0]){
				    			term.push(i);
				    		}
							for(var i=0;i<json.length;i++){
								$('#term').append('<option value="'+json[i][term[11]]+'">'+json[i][term[14]]+'</option>');
							}
				    	}
				    	$('#term').change(function() {
				            $('#terminalIdSelArrayStr').val($(this).val());//下拉框选择时赋值
				        }).multipleSelect({
				            filter: true,
				            multiple: true,
				            multipleWidth: 200,
				            maxHeight: 200,
				        });
				    	$('.ms-search input').attr('placeholder','请在这里搜索...').css('color','#0E5698');
			    	},
					error:function(){
						$.messager.alert('操作失败提示','找不到地址或网络错误,请刷新重试!','warning');
					}
			    });
			  	$('#downForm').find('#comm_no').on('keypress',function(){
			  		var _this=this;
			  		setTimeout(function(){
			  			$('#downForm #jn').val($(_this).val());
						$('#downForm #tt').val($(_this).val());
			  		},500);
			  	});
		    	$('#downForm').dialog({
		    		title:'下发任务',
		    		closed:true,
					iconCls:'icon-tip',
					width:530,
					height:760,
					modal:true,
					buttons:[{
			    		text:'<div style="padding:3px 15px;font-size:15px;">提交</div>',
			    		handler:function(){
			    			//下发任务提交
			    			if($('#terminalIdSelArrayStr').val() != ''){
			    				$.ajax({
			    					url:'${path}/taskConfig/sendTaskConfigFrameToRemoteTerm',
			    					type:'post',
			    					data:$('#downForm').serialize(),
			    					beforeSend:function (jqXHR,setting) {
			    						$.messager.progress({text:'正在提交中...'});//加载状态
			    					},
			    					success:function (data) {
		    							$.messager.progress('close');
		    							var json=$.parseJSON(data);
		    							var obj=new Function("return"+json)();
		    							var dataListName=[];//0下标 返回的信息
		    							var itemListName=[];//itemList 3下标 //val
		    							var valName=[];
		    							for(var i in obj){//得到全部 val在0下标
		    								dataListName.push(i);
		    							}
		    							for(var i in obj[dataListName[0]][0]){//得到val父层
		    								itemListName.push(i);
		    							}
		    							for(var i in obj[dataListName[0]][0][itemListName[3]][0]){//得到val
		    								valName.push(i);
		    							}
		    							if(obj[dataListName[0]][0][itemListName[3]][0][valName[2]] == 0){
		    								$.messager.show({
		    									title:'成功提示',
		    									msg:'<span style="color:green;font-size:15px;">任务配置成功!</span>'
		    								});
		    								$('#downForm').dialog('close');
		    								all_obj.clearMultiSelect();
		    							}else {
		    								$.messager.show({
		    									title:'失败提示',
		    									msg:'<span style="color:red;font-size:15px;">任务配置失败!</span>'
		    								});
		    							}
			    					},
			    					error:function(){
			    						$.messager.progress('close');
			    						$.messager.alert('操作失败提示','找不到地址或网络错误,请刷新重试!','warning');
			    					}
			    				});
			    			}else {
			    				$.messager.alert('操作提示','请选择集中器ID!','warning');
			    			}
			    		}
			    	}],
			    	onClose:function(){
			    		all_obj.clearMultiSelect();
			    	}
		    	});
			});
			//编辑和删除方法
			var obj={
				downForm:function(){
					var rows = $('#dg').datagrid('getSelections');
					if (rows.length > 1) {
						$.messager.alert('操作提示','只能下发一个任务','warning');
					}else if (rows.length == 1){
						$.ajax({
					        type:'post',
					        url:'${path}/taskConfig/getTaskTemplate',
					        data:{
					        	id:rows[0].task_template_id
					        },
					        beforeSend:function (jqXHR,setting) {
					        	$.messager.progress({text:'正在接收数据项...',});
					        },
					        success:function (data) {
					        	$.messager.progress('close');
					        	var json=$.parseJSON(data);
					        	var obj = new Function("return" + json)();
					        	$('#downForm #meterNo').val(rows[0].comm_no);
								$('#downForm #taskNo').val(rows[0].jn);
								$('#downForm #taskType').val(rows[0].tt);
								$('#downForm .date').eq(0).datetimebox('setValue',rows[0].ts);//ts date
								$('#downForm #gatherStandardTimeUnit').val(rows[0].ts_unit);
								$('#downForm #gatherInterval').val(rows[0].ti);
								$('#downForm .date').eq(1).datetimebox('setValue',rows[0].rs);//rs date
								$('#downForm #reportStandardTimeUnit').val(rows[0].rs_unit);
								$('#downForm #reportInterval').val(rows[0].ri);
					        	if(obj.length > 0){
					        		var tempName=[];
						        	for(var i in obj[0]){
						        		tempName.push(i);
						        	}
						        	$('#downForm #task_template_id').val(obj[0][tempName[1]]);
						        	$('#downForm #task_template_name').val(obj[0][tempName[2]]);
						        	$('#downForm #dataItemListStr').val(obj[0][tempName[0]]);
						        	$('#downForm #dataItemListStr2').val(obj[0][tempName[0]]);
					        	}else {
					        		$.messager.alert('操作提示','没有匹配的任务模版ID!','warning');
					        	}
					        },
					        error:function(){
					        	$.messager.progress('close');
					        	$.messager.alert('操作失败提示','数据项接收失败,找不到地址或网络错误,请刷新重试!','warning');
					        }
					    });
						$('#downForm').css({
							opacity:1,
							filter:'alpha(opacity=100)'
						}).dialog('open');
					}else if (rows.length == 0) {
						$.messager.alert('操作提示','请选择要下发的任务','warning');
					}
				},
				//修改采集器表单
				edit:function(){
					var rows = $('#dg').datagrid('getSelections');
					if (rows.length > 1) {
						$.messager.alert('编辑提示','只能选择一条数据编辑!','warning');
					}else if (rows.length == 1){
						//编辑时获取行数据
						$('#add_edit_form #ddp_id').val(rows[0].ddp_id);
						$('#add_edit_form #comm_no').val(rows[0].comm_no);
						$('#add_edit_form #jn').val(rows[0].jn);
						$('#add_edit_form #tt').val(rows[0].tt);
						$('#add_edit_form .date').eq(0).datetimebox('setValue',rows[0].ts);
						$('#add_edit_form #ts_unit').val(rows[0].ts_unit);
						$('#add_edit_form #ti').val(rows[0].ti);
						$('#add_edit_form .date').eq(1).datetimebox('setValue',rows[0].rs);
						$('#add_edit_form #rs_unit').val(rows[0].rs_unit);
						$('#add_edit_form #ri').val(rows[0].ri);
						$('#add_edit_form #task_template_id').val(rows[0].task_template_id);
						$('#add_edit_form #exe_status').combobox('setValue',rows[0].exe_status);
						all_obj.update('编辑任务配置及下发','${path}/taskConfig/update');//上传编辑资料
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
							delTaskConfigIdArray:id
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
					var checked = $('#dg').datagrid('getSelections');//getSelections只能得到选中的行! getChecked能得到全部勾行的行!
					if (checked.length > 0) {
						$.messager.confirm('数据删除提示','是否删除选中的数据?',function (flag) {
							if (flag) {
								var select_id = [];
								for (var i = 0; i < checked.length; i++) {
									select_id.push(checked[i].ddp_id);//取ID
								}
								obj.delete_ajax('${path}/taskConfig/delete',select_id.join(','));//上传ID删除
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