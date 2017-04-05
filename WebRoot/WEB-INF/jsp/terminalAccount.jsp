<!DOCTYPE html>
<html>
	<head>
		<title>设备台账管理</title>
		<meta charset="utf-8">
		<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
		<%@ include file="/WEB-INF/jsp/include/taglib.jsp"%>
		<%@ include file="/WEB-INF/jsp/include/easyui.jsp"%>
		<!-- EasyUI datagrid表格和表单CSS-->
		<link href="${path}/js/datagridForm.css" rel="stylesheet" type="text/css"/>
		<!-- 下拉多选框CSS文件-->
		<link href="${path}/js/multiple-select/multiple-select.css" rel="stylesheet" type="text/css"/>
		<!-- 图片裁剪CSS文件-->
		<link href="${path}/js/photoCut/cropper.css" rel="stylesheet" type="text/css"/>
		<link href="${path}/js/photoCut/myCrop.css" rel="stylesheet" type="text/css"/>
		<style type="text/css">
			#add_edit_form{padding:10px 0 0 30px;}
			#add_edit_form div>label{width:110px;}
			#add_edit_form .input{margin-right:20px;}
			.ms-drop.bottom{height:580px;}
			.ms-drop ul > li{height:20px;font-size:13px;}
			.ms-drop ul > li.multiple{line-height:20px;}
			#add_edit_form .add_edit_form_right div{margin-right:130px;}
			.photo {width:200px;height:100px;}
			#containerDiv{padding: 0;margin: 0;position:fixed;display: none;top: 0;left: 0;z-index: 2000000000000;}
			#actions{width:100%;position:fixed;bottom:60px;}
			.photoButton{width:80px;height:35px;border-radius:5px;font-size:15px;margin-left:80px;cursor:pointer;border:1px solid #2693CD;background:linear-gradient(#2693CD,#1B9AF7);background:-webkit-gradient(linear,left top,left bottom,from(#2693CD),to(#1B9AF7));background:linear-gradient(#2693CD,#1B9AF7);box-shadow:inset 0 1px 0 #2693CD,0 1px 2px #1B9AF7;color:#fff}
			#actions{text-align:center;}
		</style>
	</head>
	<body class="easyui-layout" onselectstart="javascript:return false;">
		<table id="dg" class="easyui-datagrid" toolbar="#toolbar"></table>
		<!-- 功能 -->
		<div id="tool">
			<div>
				<a href="#" id="add" class="easyui-linkbutton" data-options=" iconCls:'icon-add', plain:true ">新增设备台账</a>
				<a href="#" onclick="obj.edit()" class="easyui-linkbutton" data-options=" iconCls:'icon-edit', plain:true ">编辑设备台账(黄色背景行)</a>
				<a href="#" onclick="obj.button_delete()" class="easyui-linkbutton" data-options=" iconCls:'icon-remove', plain:true">删除设备台账(所有勾选行)</a>
			</div>
			<!-- 搜索功能 -->
			<div class="input_search">
				<label for="search_1">设备ID<input type="text" id="search_1" name="search_1"  class="input"></label>
				<label for="search_2">设备名称<input type="text" id="search_2" name="search_2" class="input"></label>
				<a href="#" id="search" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
			</div>
			<!-- 内容区右键功能
			<div id="rightHand" class="easyui-menu" style="width:120px">
				<div id="right_add" iconCls="icon-add" style="height:25px;">新增设备台账</div>
				<div onclick="obj.edit()" iconCls="icon-edit" style="height:25px;">编辑设备台账</div>
				<div onclick="obj.right_delete()" iconCls="icon-remove" style="height:25px;">删除设备台账</div>
			</div> -->
		</div>
		<!-- 新增表单 修改数据也使用这个表单 -->
		<form id="add_edit_form" action="">
			<div class="add_edit_form_left">
				<div><label for="terminal_ormeterid">设备ID</label><input type="text" id="terminal_ormeterid" name="terminal_ormeterid" class="input"></div>
				<div><label for="equipment_name">设备名称</label><input type="text" id="equipment_name" name="equipment_name" class="input"></div>
				<div><label for="rated_voltage">额定电压</label><input type="text" id="rated_voltage" name="rated_voltage" class="input"></div>
				<div><label for="rated_current">额定电流</label><input type="text" id="rated_current" name="rated_current" class="input"></div>
				<div>
					<label for="mp_id">关联监测点</label>
					<!-- 取计量点ID -->
					<input style="display:none" type="text" id="mp_id" name="mp_id" class="input">
				</div>
			</div>
			<div class="add_edit_form_right">
				<div><label for="rated_power">额定功率</label><input type="text" id="rated_power" name="rated_power" class="input"></div>
				<div>
					<label for="url">现场图片</label>
					<input type="text" id="url" name="url" class="input" style="display:none;">
					<input type="file" id="inputImage" style="cursor:pointer;color:blue;"/>
				</div>
				<div><label for="remarks">备注</label><input type="text" id="remarks" name="remarks" class="input"></div>
			</div>
			<div>
				<!--下拉多选框  取计量点ID -->
				<select id="auto" multiple="multiple" style="width:820px;"></select>
			</div>
		</form>
		<!-- 图片截取 生成Base64赋给img -->
		<div style="display:none;">
			<img  id="showImg" />
		</div>
		<div id="containerDiv">
			<!-- 图片截取 -->
		    <div class="row" style="display: none;" id="imgEdit">
		      <div class="col-md-9">
		        <div class="img-container">
		          <img src="" alt="Picture">
		        </div>
		      </div>
		    </div>
		    <!-- 图片截取 取消  确定-->
		    <div id="actions">
		      <div class="docs-buttons">
		          <button type="button" class="photoButton" data-method="destroy" title="Destroy">
		            <span class="docs-tooltip" data-toggle="tooltip" >
		              <span class="fa fa-power-off" >取消</span>
		            </span>
		          </button>
		          <button type="button" id="imgCutConfirm" class="photoButton" data-method="getCroppedCanvas" style="margin-right: 17px;">
		            <span class="docs-tooltip" data-toggle="tooltip">确认</span>
		          </button>
		      </div>
		    </div>
	  	</div>
		<!-- easyui-datagrid 通用设置和方法-->
		<script src="${path}/js/datagrid.js" type="text/javascript"></script>
		<!-- 下拉多选框JS文件 -->
		<script src="${path}/js/multiple-select/multiple-select.js" type="text/javascript"></script>
		<!--图片裁剪JS文件 -->
		<script src="${path}/js/photoCut/cropper.js" type="text/javascript"></script>
		<script src="${path}/js/photoCut/myCrop.js" type="text/javascript"></script>
		<script type="text/javascript">
			var fileImg = "";
			//DOM后加载数据
			$(function(){
				$('#dg').datagrid({
				    url:'${path}/terminalAccount/datagrid',
					columns:[[
				        {field:'terminal_ormeterid',title:'设备ID',sortable:true,width:100},
				        {field:'equipment_name',title:'设备名称',sortable:true,width:100},
				        {field:'dev_type',title:'设备类型',sortable:true,width:100,formatter:function(value,row,index){//1前置机 2 集中器 3计量点表 4变压器'
				        			if(value==1){return "前置机";}
				        			else if(value==2){return "集中器";}
				        			else if(value==3){return "计量点表";}
				        			else if(value==4){return "变压器";}
				        }},
				        {field:'rated_voltage',title:'额定电压',sortable:true,width:100},
				        {field:'rated_current',title:'额定电流',sortable:true,width:100},
				        {field:'rated_power',title:'额定功率',sortable:true,width:100},
				        {field:'mp_id',title:'关联监测点(计量点ID)',sortable:true,width:100},
				        {field:'url',title:'现场图片',sortable:true,width:100,
							formatter:function(row,index,value){
				          		if(index.url != ''){
									return '<img class=photo src='+index.url+' >';
				          		}
							} 
				        },
				        {field:'remarks',title:'备注',sortable:true,width:100},
				    ]],
				});
				// 增加
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
						height:890,
						modal:true,
						iconCls:'icon-add',
						title:'新增设备台账',
						buttons:[{
				    		text:'<div style="padding:3px 15px;font-size:15px;">提交</div>',
				    		handler:function (){
				    			if($('#showImg').attr('src') != undefined){
				    				$('#url').val($('#showImg').attr('src'));
				    			}
				    			if($('#auto').val() == null){
				    				$.messager.alert('操作提示','请选择关联监测点!','warning');
				    			}
				    			try{
				    				if($('#add_edit_form').form('validate') && $('#auto').val().length > 0 && $('#auto').val().length <= 30){//多选框验证
						    			all_obj.ajax('${path}/terminalAccount/add');//新增地址
					    			}else {
					    				$.messager.alert('操作提示','请输入任务模板名称和包括的数据项,数据项选择范围在30个内!','warning');
					    			}
				    			}catch(error){}//如果不选择时提交会出错
				    		}
				    	}]
					});
					$($('#add_edit_form').find('input').get(0)).focus();
			    });
			    //搜索
			    $('#search').click(function(){
				   	if($('#search_1').val() != '' || $('#search_2').val() != '' ){//判断
						$('#dg').datagrid('load',{
							search_val_1:$.trim($('#search_1').val()),
							search_val_2:$.trim($('#search_2').val()),
						});
					}else {//搜索框全部为空时 搜索全部
						$('#dg').datagrid('load',{search_val_1:''});
					}
			    });
			  	//mp_id 取计量点ID
			    $.ajax({
					url:'${path}/terminalAccount/allInfMp',
					type:'post',
					success:function(data){
						all_obj.multiSelect(data,24,25);//只要mp_id和mp_name
					},
					error:function(){
						$('#dg').datagrid('loaded');
						$.messager.alert('数据项获取失败提示','数据项获取失败,找不到地址或网络错误,请刷新重试!','warning');
					}
				});
			  	//图片裁剪
				$("#imgCutConfirm").on("click",function(){
					 $("#containerDiv").hide();
		         	 $("#imgEdit").hide();
				});
			});
			//编辑和删除方法
			var obj={
				//修改采集器表单
				edit:function(){
					var rows = $('#dg').datagrid('getSelections');
					if (rows.length > 1) {
						$.messager.alert('编辑提示','只能选择一条数据编辑!','warning');
					}else if (rows.length == 1){
						//编辑时获取行数据
						$('#add_edit_form #terminal_ormeterid').val(rows[0].terminal_ormeterid);//ID
						$('#add_edit_form #rated_voltage').val(rows[0].rated_voltage);//额定电压
						$('#add_edit_form #equipment_name').val(rows[0].equipment_name);//设备名称
						$('#add_edit_form #mp_id').val(rows[0].mp_id).show();//常用计量点IP
						$('#add_edit_form #rated_current').val(rows[0].rated_current);//额定电流
						$('#add_edit_form #url').val(rows[0].url);//图片地址
						$('#add_edit_form #rated_power').val(rows[0].rated_power);//额定功率
						$('#add_edit_form #remarks').val(rows[0].remarks);//备注
						$('#add_edit_form').css({
							opacity:1,
							filter:'alpha(opacity=100)'
						}).dialog({
							closed:false,
							width:900,
							height:890,
							iconCls:'icon-edit',
							title:'编辑设备台账',
							buttons:[{
				    			text:'<div style="padding:3px 15px;font-size:15px;">提交</div>',
				    			handler:function(){
				    				if($('#showImg').attr('src') != undefined){
				    					$('#url').val($('#showImg').attr('src'));
				    				}
				    				all_obj.ajax('${path}/terminalAccount/update');
				    			}
				    		},{
				    			text:'<div style="padding:3px 15px;font-size:15px;">取消</div>',
				    			handler : function () {
									$.messager.confirm('编辑取消提示','取消会清除正在编辑的数据,是否取消?',function(flag){
										if(flag){
											$('#add_edit_form').dialog('close').form('reset');
											all_obj.clearMultiSelect();
										}
									});
								},
				    		}],
				    		onClose:function(){
				    			all_obj.clearMultiSelect();
				    		}
						});
					}else if (rows.length == 0) {
			    		$.messager.alert('错误提示','请选择要编辑的数据,只能选择一条!','warning');
					}
				},
				//按钮删除
				button_delete:function () {
					var checked = $('#dg').datagrid('getSelections');//getSelections只能得到选中的行! getChecked能得到全部勾行的行!
					if (checked.length > 0) {
						$.messager.confirm('数据删除提示','是否删除选中的数据?',function (flag) {
							if (flag) {
								var select_id = [];
								for (var i = 0; i < checked.length; i++) {
									select_id.push(checked[i].terminal_ormeterid);//取ID
								}
								all_obj.delete_ajax('${path}/terminalAccount/delete',select_id.join(','));//上传ID删除
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