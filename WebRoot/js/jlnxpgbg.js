$(function(){
	//交流能效评估报告
	var myChartgl;
	var timeTicketgl;
	var pointNum = 180; //轴上点个数
	var pointTime = 10000; //数据更新时间ms
	var Mp_idSearch; //查询对应测量点数据

	function getLocalTime(nS){ //时间格式转化
		return new Date(parseInt(nS)).toLocaleTimeString().replace(/^\D*/,'');
	}

	//图表初始化数据源、坐标轴时间初始化
	function axisDate(){
		var now = new Date();
		var res = [];
		var len = pointNum;
		while(len--){
			res.unshift(now.toLocaleTimeString().replace(/^\D*/,''));
			now = new Date(now - pointTime);
		}
		return res;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	var data0 = new Array(210,412,611,554,560,730,610,652,731,832,654,422);
	var data1 = new Array(30,182,434,491,390,130,160,112,114,342,321,276);
	var data2 = new Array(120,112,501,234,120,190,120,231,321,112,142,154);
	var data3 = new Array(112,106,301,254,220,164,118,218,251,162,132,144);
	function DataEchart(){
		$.ajax({
			url:"queryElecData/todayPowerData",
			type:"post",
			dataType:"text",
			success:function(data){
				console.log(data);
			},
			error:function(){ //服务器出错之后的处理逻辑。
				console.log("请求路径失败！");
			}
		});
		
		require.config({
			paths:{echarts:path+'/js/Echart/build/dist'}
		});
		require([
			'echarts',
			'echarts/chart/line',
			'echarts/chart/bar'
		],function(ec){
			myChartgl = ec.init(document.getElementById('gl'),'macarons'); //功率统计图
			optiongl = {
			    title : {
			        text: '空调系统实时功率曲线',
			        subtext: ''
			    },
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			        data:['调整前','优化1','优化2','优化3']
			    },
			    toolbox: {
			        show : false,
			        feature : {
			            mark : {show: false},
			            dataView : {show: false, readOnly: false},
			            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    calculable : true,
			    xAxis : [
			        {
			            type : 'category',
			            boundaryGap : false,
			            data : ['01','02','03','04','05','06','07','08','09','10','11','12']
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value'
			        }
			    ],
			    series : [
			        {
			            name:'调整前',
			            type:'line',
			            smooth:true,
			            itemStyle: {normal: {areaStyle: {type: 'default'}}},
			            data:data0
			        },{
			            name:'优化1',
			            type:'line',
			            smooth:true,
			            itemStyle: {normal: {areaStyle: {type: 'default'}}},
			            data:data1
			        },{
			            name:'优化2',
			            type:'line',
			            smooth:true,
			            itemStyle: {normal: {areaStyle: {type: 'default'}}},
			            data:data2
			        },{
			            name:'优化3',
			            type:'line',
			            smooth:true,
			            itemStyle: {normal: {areaStyle: {type: 'default'}}},
			            data:data3
			        }
			    ]
			};
			myChartgl.setOption(optiongl);
		});
	}

	window.onload = function(){
		DataEchart();
	};

	var newSeriesData = [];
	function handleAddTemp(){
		for(var i = 0; i < data0.length; i ++){
			data0[i] = data0[i]*(1-0.045);
			data1[i] = data1[i]*(1-0.0426);
			data2[i] = data2[i]*(1-0.0431);
			data3[i] = data3[i]*(1-0.0445);
		}
		newSeriesData.splice(0,newSeriesData.length);
		newSeriesData.push({
		    name:'调整前',
		    type:'line',
		    smooth:true,
		    itemStyle:{normal:{areaStyle:{type:'default'}}},
		    data:data0
		},{
		    name:'优化1',
		    type:'line',
		    smooth:true,
		    itemStyle:{normal:{areaStyle:{type:'default'}}},
		    data:data1
		},{
		    name:'优化2',
		    type:'line',
		    smooth:true,
		    itemStyle:{normal:{areaStyle:{type:'default'}}},
		    data:data2
		},{
		    name:'优化3',
		    type:'line',
		    smooth:true,
		    itemStyle:{normal:{areaStyle:{type:'default'}}},
		    data:data3
		});
		myChartgl.setSeries(newSeriesData);
	}
	function handleSubTemp(){
		for(var i = 0; i < data0.length; i ++){
			data0[i] = data0[i]*(1+0.045);
			data1[i] = data1[i]*(1+0.0426);
			data2[i] = data2[i]*(1+0.0431);
			data3[i] = data3[i]*(1+0.0445);
		}
		newSeriesData.splice(0,newSeriesData.length);
		newSeriesData.push({
		    name:'调整前',
		    type:'line',
		    smooth:true,
		    itemStyle:{normal:{areaStyle:{type:'default'}}},
		    data:data0
		},{
		    name:'优化1',
		    type:'line',
		    smooth:true,
		    itemStyle:{normal:{areaStyle:{type:'default'}}},
		    data:data1
		},{
		    name:'优化2',
		    type:'line',
		    smooth:true,
		    itemStyle:{normal:{areaStyle:{type:'default'}}},
		    data:data2
		},{
		    name:'优化3',
		    type:'line',
		    smooth:true,
		    itemStyle:{normal:{areaStyle:{type:'default'}}},
		    data:data3
		});
		myChartgl.setSeries(newSeriesData);
	}
	function handleAddPower(){
		for(var i = 0; i < data0.length; i ++){
			data0[i] = data0[i]*(1+0.065);
			data1[i] = data1[i]*(1+0.0526);
			data2[i] = data2[i]*(1+0.0531);
			data3[i] = data3[i]*(1+0.0545);
		}
		newSeriesData.splice(0,newSeriesData.length);
		newSeriesData.push({
		    name:'调整前',
		    type:'line',
		    smooth:true,
		    itemStyle:{normal:{areaStyle:{type:'default'}}},
		    data:data0
		},{
		    name:'优化1',
		    type:'line',
		    smooth:true,
		    itemStyle:{normal:{areaStyle:{type:'default'}}},
		    data:data1
		},{
		    name:'优化2',
		    type:'line',
		    smooth:true,
		    itemStyle:{normal:{areaStyle:{type:'default'}}},
		    data:data2
		},{
		    name:'优化3',
		    type:'line',
		    smooth:true,
		    itemStyle:{normal:{areaStyle:{type:'default'}}},
		    data:data3
		});
		myChartgl.setSeries(newSeriesData);
	}
	function handleSubPower(){
		for(var i = 0; i < data0.length; i ++){
			data0[i] = data0[i]*(1-0.065);
			data1[i] = data1[i]*(1-0.0526);
			data2[i] = data2[i]*(1-0.0531);
			data3[i] = data3[i]*(1-0.0545);
		}
		newSeriesData.splice(0,newSeriesData.length);
		newSeriesData.push({
		    name:'调整前',
		    type:'line',
		    smooth:true,
		    itemStyle:{normal:{areaStyle:{type:'default'}}},
		    data:data0
		},{
		    name:'优化1',
		    type:'line',
		    smooth:true,
		    itemStyle:{normal:{areaStyle:{type:'default'}}},
		    data:data1
		},{
		    name:'优化2',
		    type:'line',
		    smooth:true,
		    itemStyle:{normal:{areaStyle:{type:'default'}}},
		    data:data2
		},{
		    name:'优化3',
		    type:'line',
		    smooth:true,
		    itemStyle:{normal:{areaStyle:{type:'default'}}},
		    data:data3
		});
		myChartgl.setSeries(newSeriesData);
	}
});