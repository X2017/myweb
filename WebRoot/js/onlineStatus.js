//设备在线率统计 厂家设备状态在线率排名 共同JS
$(function(){
	$('#dg').datagrid({
		fitColumns:true,
	    striped:true,//斑马线效果
		scrollbarSize:0,//滚动条大小
		showHeader:true,
	    fitColumns:true,
	    rownumbers:true,
	    border:false,
	    nowrap:false,
	    toolbar:'#tool'
	});
	/* 搜索 */
	$('#search').click(function(){
		var begin=$('input[name="begin"]').val();
		var end=$('input[name="end"]').val();
		var reg=/[^0-9]/ig;
		if(begin != '' && end != '' && begin.replace(reg,'') < end.replace(reg,'')){
			$('#dg').datagrid('load',{
				begin:begin,
				endTime:end
			});
		}else{
			$.messager.alert('操作提示','<span style=font-size:15px;>请选择开始时间和结束时间! 开始时间不能大于结束时间!</span>','warning');
		}
	});
	//日期选择增加清除功能
	$(function(){
	    var buttons = $.extend([], $.fn.datebox.defaults.buttons);
	    buttons.splice(1,0,{
	        text:'清除',
	        handler: function (target) {$(target).datebox("setValue","");}  
	    });
	    $('.easyui-datebox').datebox({buttons:buttons});
	});
});