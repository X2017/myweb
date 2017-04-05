//序列化JSON
$.fn.serializeJson=function(){
	var serializeObj={};
	$(this.serializeArray()).each(function(){serializeObj[this.name]=this.value;});
	return serializeObj;
};
//日期选择增加清除功能
$(function(){  
    var buttons = $.extend([], $.fn.datebox.defaults.buttons);
    buttons.splice(1,0,{
        text:'清除',
        handler: function (target) {$(target).datebox("setValue","");}  
    });
    $('.easyui-datebox').datebox({buttons:buttons});
});
//DOM加载 所有表格通用设置
$(function(){
	$("#dg").datagrid({
		pagination:true,
	    showHeader:true,
	    fitColumns:true,
	    rownumbers:true,
	    border:false,
	    nowrap:false,
	    striped:true,//斑马线效果
		scrollbarSize:0,//滚动条大小
	    toolbar:'#tool',
	    singleSelect:true,//单一选择
	    checkOnSelect:false,
		selectOnCheck:false,//行选择 结合上面的设置可以勾选多个 但是不能选择多行
		onClickRow:function(rowIndex,rowData){//限制单击只能选择一行 删除可以选择多行
			setTimeout(function(){$('#dg').datagrid('selectRow',rowIndex);},100);
	   	},
	});
	setTimeout(function(){//如果不延时底部分布不能显示
		var pager=$("#dg").datagrid("getPager");
        pager.pagination({layout:['list','sep','first','prev','links','next','last','manual','refresh']});//底部分布设置
        //下拉框提示信息 
        $('.textbox-prompt').tooltip({
        	content:'请点击输入框选择，禁止键盘输入',
        	position:'top'
        });
	},100);
    //时间输入框
    $('.date').datetimebox({height:'28px'});
});
 //所有datagrid表格配置和新增修改方法
var all_obj = {
	//输入搜索框
	inputSearch:function(ele,url){
		$(ele).select2({
            multiple:true,
            language:"zh-CN",
            maximumSelectionLength:1,
            ajax: {
                url: url,
                delay: 250,
                dataType:"json",
                type:"POST",
                data: function (params) {
                  	return {
                    	name: params.term,
                    	page: params.page
                 	};
                },
                processResults: function (data,params) {
                  params.page = params.page || 1;
					return {
						results: data.data,
						pagination: {
							more: (params.page * 10) < data.total
						}
					};
            },
                cache: true
            },
            escapeMarkup: function(markup) {
                return markup;
            },
            minimumInputLength: 1,
            templateResult: function (repo){
				return repo.text
			}, 
            templateSelection: function (repo){
            	return repo.text
            }
        });
	},
	//下拉多选框清除已经的数据
	clearMultiSelect:function(){
		$('#downForm').form('clear');
		$.each($('.selected').find('input[type=checkbox]'),function(index,value){
    		$(value).attr('checked',false);
    	});
		$('.ms-choice').find('span').text('');
	},
	//下拉多选框分组
	multiSelect:function(data,indexOne,indexTwo){
		var json=$.parseJSON(data);
		var groupName=[];//分组名称
		var valueTextName=[]; 
		for(var i in json){//添加分组
			$('#auto').append('<optgroup label='+i+'></optgroup>');
			groupName.push(i);
		}
		for(var i in json[groupName[0]][0]){// 得到全部返回的数据名称
			valueTextName.push(i);
		}
		for(var i=0;i<groupName.length;i++){
			for(var j=0;j<json[groupName[i]].length;j++){
				$($('#auto').find($('optgroup')[i])).append('<option value="'+json[groupName[i]][j][valueTextName[indexOne]]+'">'+json[groupName[i]][j][valueTextName[indexTwo]]+'</option>');
			}
		}
		$('#auto').change(function() {
            $('#di_items').attr('value',$(this).val());//下拉框选择时赋值
        }).multipleSelect({
            filter: true,
            multiple: true,
            placeholder: "请在下面选择数据项"
        });
    	$('.ms-search input').attr('placeholder','请在这里搜索...').css('color','#0E5698');
    	$('.ms-drop.bottom').show();
	},
	//某一个值是否存在某一个数组中
	inArray:function(array, value) {
		for (var i in array) {
			if (array[i] === value){
				return true;
			}
		}
		return false;
	},
	// 新增和修改使用此表单, 上传地址按需传参
	add:function(title,url,func,clo){//调用这个方法要把ID 设置为 add_edit_form title是标题  url是的地址
		if($('#add_edit_form').find('.id').size() >= 1){
			$($('#add_edit_form').find('.add_edit_form_left').find('.id')).hide();
		}
		$('#add_edit_form').css({
			opacity:1,
			filter:'alpha(opacity=100)'
		}).form('reset').dialog({
			closed:false,
			iconCls:'icon-add',
			title:title,
			modal:true,
			onClose:function(){
				try{
					clo();
				}catch(err){}
			},
			buttons:[{
	    		text:'<div style="padding:3px 15px;font-size:15px;">提交</div>',
	    		handler:function () {
	    			try{
		    			func();
					}catch(err){}
	    			if($('#add_edit_form').form('validate')){//验证成功
		    			all_obj.ajax(url);
	    			}
	    		}
	    	}]
		});
		$($('#add_edit_form').find('input').get(0)).focus();
	},
	ajax:function(url){
		$.ajax({
			url:url,//新增提交地址
			type:'post',
			data:$('#add_edit_form').serializeJson(),
			beforeSend:function (jqXHR,setting) {
				$.messager.progress({text:'正在提交中...'});//加载状态
			},
			success:function (data) {
				$.messager.progress('close');
				if ($.parseJSON(data).success) {
					$('#dg').datagrid('load');//刷新内容
					$('#add_edit_form').dialog('close').form('reset');//新增成功清除表单内容
					$.messager.show({
						title:'数据新增成功提示',
						msg:'数据新增成功'
					});
				}else{
					$.messager.alert('提交失败提示','提交失败,请重新提交!','warning');
				}
			},
			error:function(){
				$.messager.progress('close');
				$('#dg').datagrid('loaded');
				$.messager.alert('操作失败提示','找不到地址或网络错误,请刷新重试!','warning');
			}
		});
	},
	//搜索方法, 所有表格搜索数据调用此方法,上传地址按需传参
	search:function(element1,element2,date){ //element1 传的是输入框的ID
		var val_1 = $.trim($(element1).val());//value 1
		var val_2 = $.trim($(element2).val());//value 2
		var search_date = $(date).val();//最后一个是时间,传输入框name
		$('#dg').datagrid('load',{search_val_1:val_1,search_val_2:val_2,search_date:search_date});
	},
	// 客户ID取中文值
	getText:function(all,id){
		var getValue;
		for(var i=0;i<all.length;i++){
   			if(all[i].id == id){
   				getValue = all[i].text;
   			}
	   	}
	   	return getValue;
	},
	//编辑,更新方法,所有表格编辑数据上传时调用此方法,上传地址按需传参
	update:function(title,url,func,clo){//调用这个方法要把ID 设置为 add_edit_form title是标题  url是的地址
		if($('.id').size() > 0){
			$('.id').show();
		}
		setTimeout(function(){
			$('#add_edit_form').css({
				opacity:1,
				filter:'alpha(opacity=100)'
			}).dialog({
				closed:false,
				iconCls:'icon-edit',
				title:title,
				modal:true,
				onClose:function(){
					try{
						clo();
					}catch(err){}
				},
				buttons : [{
					text : '<div style="padding:3px 15px 3px 0;font-size:15px;">提交</div>',
					iconCls : 'icon-edit-new',
					handler : function () {
						try{
							func();
						}catch(err){}
						//任务定义的数据项的修改验证
						if($('#add_edit_form #auto').size() >= 0 && $('#add_edit_form #di_items').size() && $('#auto').val() != null){
							$('#add_edit_form #di_items').val($('#auto').val());
							$('#di_items').hide();
						}
						if ($('#add_edit_form').form('validate')) {
							$.ajax({
								url : url,
								type : 'post',
								data:$('#add_edit_form').serializeJson(),//编辑的数据
								beforeSend : function () {
									$.messager.progress({text:'正在修改中...',});
								},
								success : function (data, response, status) {
									$.messager.progress('close');
									if ($.parseJSON(data).success){
										$.messager.show({
											title : '提示',
											msg : '修改管理成功',
										});
										$('#add_edit_form').dialog('close').form('reset');
										$('#dg').datagrid('reload');
									} else {
										$.messager.alert('修改失败！', $.parseJSON(data).msg+' 没有修改内容! ','warning');
									}
								},
								error:function(){
									$.messager.progress('close');
									$('#dg').datagrid('loaded');
									$.messager.alert('操作失败提示','找不到地址或网络错误,请刷新重试!','warning');
								}
							});
						}
					},
				},{
					text : '<div style="padding:3px 15px;font-size:15px;">取消</div>',
					handler : function () {
						$.messager.confirm('编辑取消提示','取消会清除正在编辑的数据,是否取消?',function(flag){
							if(flag){
								$('#add_edit_form').dialog('close').form('reset');
							}
						});
					},
				}]
			});
		},200);
	},
	//注意: 这个ajax删除方法只适用于ID为 delIdArray的字段   删除按钮删除和右键删除调用此方法
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
				if (data) {
					$('#dg').datagrid('loaded');
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
};
//表单验证方法
$.extend($.fn.validatebox.defaults.rules,{
	phone:{//验证手机号码 
        validator:function (value) {
            return /^(13|15|18|14|17)\d{9}$/i.test(value);
        },
        message: '手机号码格式不正确'
    },
    email:{//验证邮箱
        validator:function(value){
            return /^[\w\-\.]+@[\w\-]+(\.[a-zA-Z]{2,4}){1,2}$/i.test(value); 
        },
        message: '邮箱格式不正确'
    },
    number_:{//验证整数
       validator:function(value){
           return /^\d+(\d+)?$/i.test(value);
       },
       message: '请输入数字'
    },
    ip:{//验证IP地址
        validator:function (value){
            return /^(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])$/.test(value);
        },
        message:'IP地址格式不正确'
    },
    letter:{//验证数字和字母
        validator:function(value){
            return /[0-9a-zA-Z]/i.test(value);//^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$
        },
        message: '请输入字母或数字'
     },
     reg_short:{//匹配short
    	 validator:function(value){
    		 var reg = /^[0-9]{1,6}$/;
             return reg.test(value);
         },
         message: '请输入数字,长度不大于6位'
     },
     check_nine:{//验证数字不超过9位
    	 validator:function(value){
    		 var reg = /^[0-9]{1,9}$/;
             return reg.test(value);
         },
         message: '请输入数字,长度不大于9位'
     },
     divi:{//验证除法 比如: 20/2
    	 validator:function(value){
    		 var reg = /[1-9]\d*\/[1-9]\d*/;
    		 var num = /^\d+(\d+)?$/;
             return reg.test(value) || num.test(value);
         },
         message: '正确格式: <strong style="color:red">10/2 或者整数: 10</strong>, 除数不能为0'
     },
});

