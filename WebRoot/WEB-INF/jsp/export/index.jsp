<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/include/taglib.jsp"%>
<%  
	String path = request.getContextPath();  
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title></title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<%-- 
		<link rel="stylesheet" type="text/css" href="<%=basePath %>/static/easyui/themes/gray/easyui.css">
		<link rel="stylesheet" type="text/css" href="<%=basePath %>/static/easyui/themes/icon.css">
		<link rel="stylesheet" type="text/css" href="<%=basePath %>/static/common/base.css">
		<script type="text/javascript" src="<%=basePath %>/static/easyui/jquery.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>/static/easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>/static/easyui/locale/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="<%=basePath %>/static/jquery/jquery.cookie.js"></script>
		<script type="text/javascript" src="<%=basePath %>/static/jquery/json2.js"></script>
		<script type="text/javascript" src="<%=basePath %>/static/common/base.js"></script>
		<script type="text/javascript" src="<%=basePath %>/static/jquery/jquery.form.js"></script>
		<script type="text/javascript" src="<%=basePath %>/static/My97DatePicker/WdatePicker.js"></script>
		--%>
	</head>
	<body>
		<p>这是一个测试</p>
		
		
		<%-- 
	  	<h1 style="font-size:28px">1 常规电参量统计分析</h1><br/>
	  	<h2 style="font-size:28px">1.1 电压统计分析</h2><br/>
	  	<div id="dytj" style="width:1280px"></div>
	  	<c:if test="${rp!=null}">
	  	<p style="font-size:28px">${rp.memo1}</p><br/>
	  	</c:if>
	  	
		<h2 style="font-size:28px">1.2 电流统计分析</h2><br/>
	  	<div id="dltj" style="width:1280px"></div>
	  	<c:if test="${rp!=null}">
	  	<p style="font-size:28px">${rp.memo2}</p><br/>
	  	</c:if>
	  	
	  	<h2 style="font-size:28px">1.3 有功功率统计分析</h2><br/>
	  	<div id="ygtj" style="width:1280px"></div>
	  	<c:if test="${rp!=null}">
	  	<p style="font-size:28px">${rp.memo3}</p><br/>
	  	</c:if>
	  	
	  	<c:if test="${addr=='1'}">
		  	<h2 style="font-size:28px">1.4 频率统计分析</h2><br/>
		  	<div id="pltj" style="width:1280px"></div>
		  	<c:if test="${rp!=null}">
		  	<p style="font-size:28px">${rp.memo4}</p><br/>
		  	</c:if>
		  	
		  	<h2 style="font-size:28px">1.5 功率因素统计分析</h2><br/>
		  	<div id="glystj" style="width:1280px"></div>
		  	<c:if test="${rp!=null}">
		  	<p style="font-size:28px">${rp.memo5}</p><br/>
		  	</c:if>
	  	</c:if>
	  	
	  	<h1 style="font-size:28px">2 电能质量指标统计分析</h1><br/>
	  	<h2 style="font-size:28px">2.1 畸变与谐波统计分析</h2><br/>
	  	<h2 style="font-size:28px">2.1.1 电压电流畸变统计分析</h2><br/>
	  	<div id="jbtj" style="width:1280px"></div>
	  	<c:if test="${rp!=null && addr=='1'}">
	  	<p style="font-size:28px">${rp.memo6}</p><br/>
	  	</c:if>
	  	<c:if test="${rp!=null && addr=='2'}">
	  	<p style="font-size:28px">${rp.memo4}</p><br/>
	  	</c:if>
	  	
		<h2 style="font-size:28px">2.1.2 电压电流频谱统计分析</h2><br/>
	  	<div id="pptj" style="width:1280px"></div>
	  	<c:if test="${rp!=null && addr=='1'}">
	  	<p style="font-size:28px">${rp.memo7}</p><br/>
	  	</c:if>
	  	<c:if test="${rp!=null && addr=='2'}">
	  	<p style="font-size:28px">${rp.memo5}</p><br/>
	  	</c:if>
	  	<c:if test="${addr=='1'}">
	  	<h1 style="font-size:28px">3 进线电度行度抄表数据</h1><br/>
	  	<div id="hdgrid" style="width:1280px"></div>
	  	
	  	</c:if>
	  	<h1 style="font-size:28px">附表1 最大最小电压及发生时间统计</h1><br/>
	  	<div id="dygrid" style="width:1280px"></div>
	  	<h1 style="font-size:28px">附表2 最大电流统计</h1><br/>
	  	<div id="dlgrid" style="width:1280px"></div>
	  	<h1 style="font-size:28px">附表3 最大有功功率及视在功率统计</h1><br/>
	  	<div id="yggrid" style="width:1280px"></div>
	  	<c:if test="${addr=='1'}">
	  	<h1 style="font-size:28px">附表4 最大最小功率因素统计</h1><br/>
	  	<div id="glysgrid" style="width:1280px"></div>
	  	</c:if>
		<script type="text/javascript" src="<%=basePath %>/static/highchats/highcharts.js"></script>	
	    <script type="text/javascript">
			Highcharts.setOptions({  
			      global: {
			           useUTC: false
			        }
			   });
			
			
			function getDLConfig(data, timeType) {
				var _count_deviceId = data.length;
				var l = [];
				for (var w = 0; w < _count_deviceId; w++) {
					var s = data[w].name;
					l.push(s)
				}
				var deviceName = l.join(",");
	            var _array_deviceName = deviceName.split(',');
	            var _yAxis = [];
	            var series = [];
	            
	            if (_count_deviceId > 0) {
	                for (var i = 0; i < _count_deviceId; i++) {
	                    var s = 3;
	                    var _height = (100 - s * (_count_deviceId - 1)) / _count_deviceId + '%';
	                    var _top = ((100 - s * (_count_deviceId - 1)) / _count_deviceId + s) * i + '%';
	                    var _obj_yAxis = {
	                        height: _height,
	                        top: _top,
	                        offset: 0,
	                        min: 0,
	                        opposite: false,
	                        lineWidth: 2,
	                        lineColor: 'black',
	                        title: {
	                            text: _array_deviceName[i],
	                            rotation: 0,
	                            align: 'high',
	                            offset: -200,
	                            y: 10,
	                            
	                        },
	                        labels: {
	                            x: -5,
	                            formatter: function () {
	                                return this.value + 'A';
	                            }
	                        },
	                        plotLines: [{
	                            color: '#EE1289',
	                            width: 1,
	                            dashStyle: 'Dot',
	                            label: {
	                                text: '',
	                                y: -10
	                            }
	                        }, {
	                            color: '#EE1289',
	                            width: 1,
	                            dashStyle: 'Dot',
	                            label: {
	                                text: '电流上限    ' + 'A',
	                                y: -10
	                            }
	                        }]
	                    };
	                    _yAxis.push(_obj_yAxis);
	                }
	            }
	            
	            for (var w = 0; w < _count_deviceId; w++) {
					series.push({
					type: "spline",
					color: "#BB8E09",
					yAxis: w,
					data: data[w].A,animation:false
					}, {
						type: "spline",
						color: "#004000",
						yAxis: w,
						data: data[w].B,animation:false
					}, {
						type: "spline",
						color: "#FF0000",
						yAxis: w,
						data: data[w].C,animation:false
					})
				}
	            
	            return {
	                colors: ['#7cb5ec', '#90ed7d', '#f7a35c', '#8085e9', '#f15c80', '#e4d354', '#8000e8', '#8d4653', '#91e8e1', '#000000', '#560f23'],
	                series:series,
	                chart: {
	                    marginTop: 80,
	                    marginBottom: 110,
	                    marginRight: 10,
	                    marginLeft: 60,
	                    plotBorderWidth: 1,
	                    animation: false
	                },
	                rangeSelector: {
	                    enabled: false
	                },
	                title: {
	                    text: '电流曲线',
	                    x: -20, 
	                    y: 30
	                },
	                subtitle: {
	                    text: '考核区间：${checkTime}',
	                    x: -20,
	                    y: 50
	                },
	                xAxis: [{
	                    title: {
	                        rotation: 0,
	                        align: 'high'
	                    },
	                    type: 'datetime',
	                    labels: {
	                        rotation: -25,
	                        y: 10,
	                        taggerLines: 1,
	                        formatter: function () {                        	
	                            if (timeType == 'd') {
	                                return Highcharts.dateFormat('%H:%M:%S', this.value);
	                            } else {
	                                return Highcharts.dateFormat('%m/%d  %H:%M', this.value);
	                            }
	                        }
	                    }
	                }],
	                yAxis: _yAxis,
	                plotOptions: {
	                    series: {
	                        enableMouseTracking : false,
	                        marker: {
	                            radius: 0
	                        }
	                    }
	                },
	                credits: {
	                    enabled: false
	                },
	                legend: {
	                    enabled: false,
	                    borderWidth: 0
	                }
	            };
	        }
	        
	        
	        function getDYConfig(data, timeType, limit) {
	            var deviceNum = data.length;
				var l = [];
				for (var w = 0; w < deviceNum; w++) {
					var s = data[w].name;
					l.push(s)
				}
				var deviceName = l.join(",");
	            var _array_deviceName = deviceName.split(',');
	            var _yAxis = [];
	            var series = [];
	            
	            var _height = 100 / deviceNum + (-3) + '%';
	            if (deviceNum > 0) {
	                for (var i = 0; i < deviceNum; i++) {
	                	var upLimit = limit[i].data[0]==null?220 * 1.07:limit[i].data[0];
	                	var downLimit = limit[i].data[1]==null?220 * 0.93:limit[i].data[1];
	                    var _obj_yAxis = {
	                        height: _height,
	                        top: 100 * i / deviceNum + '%',
	                        offset: 0,
	                        title: {
	                            text:_array_deviceName[i], 
	                            rotation: 0,
	                            align: 'high',
	                            offset: -200,
	                            y: 10
	                        },
	                        labels: {
	                            formatter: function () {
	                                if (this.value >= 0)
	                                    return this.value + 'V';
	                            }
	                        },
	                        opposite: false,
	                        lineWidth: 2,
	                        lineColor: 'black',
	                        plotLines: [{
	                            value: upLimit,
	                            color: '#8085e9',
	                            width: 2,
	                            dashStyle: 'shortDash',
	                            label: {
	                                text: '电压上限'
	                            }
	                        }, {
	                            value: downLimit,
	                            color: '#f15c80',
	                            width: 2,
	                            dashStyle: 'shortDash',
	                            label: {
	                                text: '电压下限'
	                            }
	                        }]
	                    };
	                    _yAxis.push(_obj_yAxis);
	                };
	            }
	            
	            var F = ["#BB8E09", "#004000", "#FF0000", "#8085e9", "#f15c80"];
	            for (var E = 0; E < deviceNum; E++) {
					var r = {
						type: "spline",
						data: data[E].A,
						color: F[0],
						yAxis: E,animation:false
					};
					var p = {
						type: "spline",
						data: data[E].B,
						color: F[1],
						yAxis: E,animation:false
					};
					var n = {
						type: "spline",
						data: data[E].C,
						color: F[2],
						yAxis: E,animation:false
					};
					series.push(r);
					series.push(p);
					series.push(n)
				}
	            
	            return {series:series,
	                chart: {
	                    type: 'spline',
	                    marginTop: 80,
	                    marginBottom: 110,
	                    marginLeft: 60,
	                    plotBorderWidth: 1,
	                    animation:false
	                },
	                title: {
	                    text : '电压统计',
	                    x: -20, 
	                    y: 30
	                },
	                subtitle: {
	                    text: '考核区间：${checkTime}',
	                    x: -20,
	                    y: 50
	                },
	                xAxis: [{
	                    title: {
	                        align: 'high',
	                        margin: 0
	                    },
	                    type: 'datetime',
	                    labels: {
	                        rotation: -25,
	                        staggerLines: 1,
	                        formatter: function () {
	                            if (timeType == 'd') {
	                                return Highcharts.dateFormat('%H:%M:%S', this.value);
	                            } else {
	                                return Highcharts.dateFormat('%m/%d  %H:%M', this.value);
	                            }
	                        }
	                    }
	                }],
	                yAxis: _yAxis,
	                plotOptions: {
	                    series: {
	                        enableMouseTracking : false,
	                        marker: {
	                            radius: 0
	                        }
	                    }
	
	                },
	                credits: {
	                    enabled: false
	                },
	                legend: {
	                    enabled: false,
	                    borderWidth: 0
	                },
	                rangeSelector: {
	                    enabled: false
	                }
	            };
	        }
	        
	        
	        function getYGConfig(data, timeType) {
	            var _count_deviceId = data.length;
				var l = [];
				for (var w = 0; w < _count_deviceId; w++) {
					var s = data[w].name;
					l.push(s)
				}
				var deviceName = l.join(",");
	            var _array_deviceName = deviceName.split(',');
	            var _yAxis = [];
	            var series = [];
	            
	            if (_count_deviceId > 0) {
	                for (var i = 0; i < _count_deviceId; i++) {
	                    var s = 3;
	                    var _height = (100 - s * (_count_deviceId - 1)) / _count_deviceId + '%';
	                    var _top = ((100 - s * (_count_deviceId - 1)) / _count_deviceId + s) * i + '%';
	                    var _obj_yAxis = {
	                        height: _height,
	                        top: _top,
	                        min: 0,
	                        offset: 0,
	                        opposite: false,
	                        lineWidth: 2,
	                        lineColor: 'black',
	                        title: {
	                            text: _array_deviceName[i],
	                            rotation: 0,
	                            align: 'high',
	                            offset: -200,
	                            y: 10
	                        },
	                        labels: {
	                            x: -5,
	                            formatter: function () {
	                                return this.value + 'kW';
	                            }
	                        },
	                        plotLines: [{
	                            color: '#EE1289',
	                            width: 1,
	                            dashStyle: 'Dot',
	                            label: {
	                                text: '',
	                                y: -10
	                            }
	                        }]
	                    };
	                    _yAxis.push(_obj_yAxis);
	                }
	            }
	            
	            
	            for (var y = 0; y < _count_deviceId; y++) {
					series.push({
						type: "spline",
						yAxis: y,
						name: data[y].name,
						data: data[y].data,animation:false
					})
				}
	            
	            return {
	                colors: ['#7cb5ec', '#90ed7d', '#f7a35c', '#8085e9', '#f15c80', '#e4d354', '#8000e8', '#8d4653', '#91e8e1', '#000000', '#560f23'],
	                series:series,
	                chart: {
	                    type: 'spline',
	                    marginTop: 80,
	                    marginBottom: 110,
	                    marginRight: 10,
	                    marginLeft: 60,
	                    plotBorderWidth: 1,
	                    animation: false
	                },
	                rangeSelector: {
	                    enabled: false
	                },
	                title: {
	                    text: '有功功率曲线',
	                    x: -20, 
	                    y: 30
	                },
	                subtitle: {
	                    text: '考核区间：${checkTime}',
	                    x: -20,
	                    y: 50
	                },
	                xAxis: [{
	                    title: {
	                        rotation: 0,
	                        align: 'high',
	                        style: {
	                            color: 'black',
	                            font: '14px/normal 黑体'
	                        }
	                    },
	                    type: 'datetime',
	                    labels: {
	                        rotation: -25,
	                        y: 10,
	                        taggerLines: 1,
	                        formatter: function () {
	                            if (timeType == 'd') {
	                                return Highcharts.dateFormat('%H:%M:%S', this.value);
	                            } else {
	                                return Highcharts.dateFormat('%m/%d  %H:%M', this.value);
	                            }
	                        }
	                    }
	                }],
	                yAxis: _yAxis,
	                plotOptions: {
	                    series: {
	                        enableMouseTracking : false,
	                        marker: {
	                            radius: 0
	                        }
	                    }
	                },
	                credits: {
	                    enabled: false
	                },
	                legend: {
	                    enabled: false,
	                    borderWidth: 0
	                }
	            };
	        }
	        
	        
	        function getJBConfig(data, timeType) {
				var _count_deviceId = data.length;
				var t = [];
				for (var w = 0; w < _count_deviceId; w++) {
					var s = data[w].name;
					t.push(s)
				}
				var deviceName = t.join(",");
	            var _array_deviceName = deviceName.split(',');
	            var _yAxis = [];
	            var series = [];
	            
	            
	            var o = _count_deviceId;
	            if (_count_deviceId > 0) {
	                for (var p = 0; p < 2 * (o); p++) {
						var z = 2;
						var y = "";
						if (p == 0) {
							y = "电压畸变率@" + t[0]
						} else {
							if (p == 1) {
								y = "电流畸变率@" + t[0]
							} else {
								if (p == 2) {
									y = "电压畸变率@" + t[1]
								} else {
									if (p == 3) {
										y = "电流畸变率@" + t[1]
									} else {
										if (p == 4) {
											y = "电压畸变率@" + t[2]
										} else {
											if (p == 5) {
												y = "电流畸变率@" + t[2]
											}
										}
									}
								}
							}
						}
						var n = (100 - z * (2 * (o) - 1)) / (2 * (o)) + "%";
						var x = ((100 - z * (2 * (o) - 1)) / (2 * (o)) + z) * p + "%";
						var j = {
							height: n,
							top: x,
							offset: 0,
							min: 0,
							opposite: false,
							lineWidth: 2,
							lineColor: "black",
							title: {
								text: y,
								rotation: 0,
								align: "high",
								offset: -300,
								y: 15
							},
							labels: {
								formatter: function() {
									return this.value + "%"
								}
							}
						};
						_yAxis.push(j)
					}
	            }
	            
	
				for (var p = 0; p < _count_deviceId; p++) {
					series.push({
						type: "spline",
						color: "#BB8E09",
						lineWidth: 1,
						yAxis: (2 * p),
						name: "A相电压畸变率(" + data[p].name + ")",
						data: data[p].data.Ua,animation:false
					}, {
						type: "spline",
						color: "#004000",
						lineWidth: 1,
						yAxis: (2 * p),
						name: "B相电压畸变率(" + data[p].name + ")",
						data: data[p].data.Ub,animation:false
					}, {
						type: "spline",
						color: "#FF0000",
						lineWidth: 1,
						yAxis: (2 * p),
						name: "C相电压畸变率(" + data[p].name + ")",
						data: data[p].data.Uc,animation:false
					}, {
						type: "spline",
						color: "#BB8E09",
						lineWidth: 1,
						yAxis: (2 * p) + 1,
						name: "A相电流畸变率(" + data[p].name + ")",
						data: data[p].data.Ia,animation:false
					}, {
						type: "spline",
						color: "#004000",
						lineWidth: 1,
						yAxis: (2 * p) + 1,
						name: "B相电流畸变率(" + data[p].name + ")",
						data: data[p].data.Ib,animation:false
					}, {
						type: "spline",
						color: "#FF0000",
						lineWidth: 1,
						yAxis: (2 * p) + 1,
						name: "C相电流畸变率(" + data[p].name + ")",
						data: data[p].data.Ic,animation:false
					})
				}
	            
	            return {
	            	series:series,
	                chart: {
	                    marginTop: 80,
	                    marginBottom: 100,
	                    marginRight: 100,
	                    marginLeft: 60,
	                    plotBorderWidth: 1,
	                    animation:false
	                },
	                title: {
	                	text:'畸变率曲线',
	                    x: -20, 
	                    y: 30
	                },
	                subtitle: {
	                    text: '考核区间：${checkTime}',
	                    x: -20,
	                    y: 50
	                },
	                yAxis: _yAxis,
	                plotOptions: {
	                    series: {
	                        enableMouseTracking : false,
	                        marker: {
	                            radius: 0
	                        }
	                    }
	                },
	                xAxis: [{
	                    title: {
	                        rotation: 0,
	                        align: 'high'
	                    },
	                    type: 'datetime',
	                    labels: {
	                        rotation: -25,
	                        y: 10,
	                        taggerLines: 1,
	                        // format: '{value:%m月%d日  %H:%M:%S}',
	                        formatter: function () {
	                            if (timeType == 'd') {
	                                return Highcharts.dateFormat('%H:%M:%S', this.value);
	                            } else {
	                                return Highcharts.dateFormat('%m/%d  %H:%M', this.value);
	                            }
	                        }
	                    }
	                }],
	                colors: ['#7cb5ec', '#434348', '#90ed7d', '#f7a35c', '#8085e9', '#f15c80', '#e4d354', '#8085e8', '#8d4653', '#91e8e1', '#000000', '#560f23'],
	                credits: {
	                    enabled: false
	                },
	                legend: {
	                    enabled: false,
	                    borderWidth: 0
	                },
	                rangeSelector: {
	                    enabled: false
	                }
	            };
	        }
	        
	        
	        function getPPConfig(data, timeType) {
	        	
	        	var _count_deviceId = data.length;
				var t = [];
				for (var w = 0; w < _count_deviceId; w++) {
					var s = data[w].name;
					t.push(s)
				}
				var deviceName = t.join(",");
	            var _array_deviceName = deviceName.split(',');
	            var _yAxis = [];
	            var series = [];
	        	
	        	var j = [];
	            
	            for (var o = 0; o < _count_deviceId; o++) {
	                var x = 4;
	                var m = (100 - x * (_count_deviceId - 1)) / _count_deviceId + "%";
	                var w = ((100 - x * (_count_deviceId - 1)) / _count_deviceId + x) * o + "%";
	                var h = {
	                    height: m,
	                    top: w,
	                    offset: 0,
	                    opposite: false,
	                    lineWidth: 2,
	                    lineColor: "black",
	                    title: {
	                        text: "电压谐波含有率@" + t[o],
	                        rotation: 0,
	                        align: "high",
	                        offset: -300,
	                        y: 15
	                    },
	                    labels: {
	                        format: "{value}V"
	                    }
	                };
	                _yAxis.push(h)
	            }
	            
	
	            for (var o = 0; o < _count_deviceId; o++) {
	            	
	                series.push({
	                    type: "column",
	                    color: "#BB8E09",
	                    yAxis: o,
	                    name: "A相(" + data[o].name + ")",
	                    data: data[o].data['Ua'],animation:false
	                },
	                {
	                    type: "column",
	                    yAxis: o,
	                    color: "#004000",
	                    name: "B相(" + data[o].name + ")",
	                    data: data[o].data['Ub'],animation:false
	                },
	                {
	                    type: "column",
	                    yAxis: o,
	                    color: "#FF0000",
	                    name: "C相(" + data[o].name + ")",
	                    data: data[o].data['Uc'],animation:false
	                })
	            }
	        
	        	return {
	        		series:series,
	                chart: {
	                    marginTop: 80,
	                    marginRight: 70,
	                    marginLeft: 60,
	                    marginBottom: 80,
	                    plotBorderWidth: 1,
	                    animation:false
	                },
	                title: {
	                    text: '电压谐波频谱',
	                    y: 30
	                },
	                subtitle: {
	                	text: '考核时间：${checkTime}',
	                    y: 50
	                },
	                xAxis: {
	                    title: {
	                        text: '谐波次数',
	                        align: 'high'
	                    },
	                    categories: []
	                },
	                yAxis: _yAxis,
	                plotOptions: {
	                    column: {
	                        pointStart: 2
	                    },
	                     series: {
	                        enableMouseTracking : false,
	                        marker: {
	                            radius: 0
	                        }
	                     }
	                },
	                colors: ['#7cb5ec', '#90ed7d','#f7a35c'],
	                credits: {
	                    enabled: false
	                },
	                legend: {
	                    enabled: false,
	                    borderWidth: 0
	                }
	            };
	        }
	        
	        
	        
	        function getGLYSConfig(data,timeType,limit){
					var _count_deviceId = data.length;
					var l = [];
					for (var w = 0; w < _count_deviceId; w++) {
						var s = data[w].name;
						l.push(s)
					}
					var deviceName = l.join(",");
		            var _array_deviceName = deviceName.split(',');
		            var series = [];
					var B = _count_deviceId;
					var t = [];
							if (B > 0) {
								for (var A = 0; A < B; A++) {
									
									var pFactorLimit = limit[A].data[0]==null?0:limit[A].data[0];
	                				var cFactorLimit = limit[A].data[1]==null?0:limit[A].data[1];
	                				
									var r = 3;
									var F = (100 - r * (B - 1)) / B + "%";
									var x = ((100 - r * (B - 1)) / B + r) * A + "%";
									var E = {
										min: -0.25,
										max: 0.25,
										height: F,
										top: x,
										offset: 0,
										opposite: false,
										lineWidth: 2,
										lineColor: "black",
										title: {
											text:  _array_deviceName[A],
											rotation: 0,
											align: "high",
											offset: -200,
											y: 10
										},
										labels: {
											formatter: function() {
												var s;
												if (this.value >= 0) {
													s = (1-this.value).toFixed(3)
												} else {
													s = (-(0 - this.value-1)).toFixed(3)
												}
												return s !== "undefined" ? s : this.value
											}
										},
										plotLines: [{
											color: "#8085e9",
											value: (1-pFactorLimit),
											width: 2,
											dashStyle: "shortDash",
											label: {
												text: pFactorLimit
											}
										}, {
											color: "#f15c80",
											value: -(1-cFactorLimit),
											width: 2,
											dashStyle: "shortDash",
											label: {
												text: cFactorLimit
											}
										}, {
											color: "grey",
											value: 0,
											width: 2,
										}]
									};
									t.push(E)
								}
							}
							
					for (var A = 0; A < B; A++) {
						var m = {
							type: "spline",
							yAxis: A,
							name: data[A].name,
							data: data[A].data,animation:false
						};
						series.push(m);
					}		
							
	               return{series:series,
	                   chart : {
	                    marginTop: 100,
	                    marginBottom: 110,
	                    //marginRight:30,
	                    marginLeft: 60,
	                    plotBorderWidth: 1,
	                    animation:false
	                }
	                /*,
	                labels: {
	                    items: [{
	                        html: 'c',
	                        style: {
	                             top: 320,
	                            left: -40
	                        }
	                    }, {
	                        html: '(感性)',
	                        style: {
	                            top: -20,
	                            left: -40
	                        }
	                    }]
	                }*/,
	                title: {
	                    text:'功率因数曲线',
	                    x: -20, //center
	                    y: 40
	                },
	                subtitle: {
	                    text: '考核区间：${checkTime}',
	                    x: -20,
	                    y: 60
	                },
	                xAxis: [{
	                    title: {
	                        text: '日期/时间',
	                        rotation: 0,
	                        align: 'high'
	                    },
	                    type: 'datetime',
	                    labels: {
	                        rotation: -25,
	                        y: 10,
	                        taggerLines: 1,
	                        formatter: function () {
	                            if (timeType == 'd') {
	                                return Highcharts.dateFormat('%H:%M:%S', this.value);
	                            } else {
	                                return Highcharts.dateFormat('%m/%d  %H:%M', this.value);
	                            }
	                        }
	                    }
	                }],
	                yAxis: t,
	                plotOptions: {
	                    series: {
	                        enableMouseTracking : false,
	                        marker: {
	                            radius: 0
	                        }
	                    }
	                },
	                credits: {
	                    enabled: false
	                },
	                legend: {
	                    enabled: false,
	                    borderWidth: 0
	                },
	                rangeSelector: {
	                    enabled: false
	                },
	                navigator: {
	                    margin: 50
	                }
	            };
	        }
	        
	        
	        $(document).ready(function(){
	        	var dytjData = ${dytj};
				var dytjConfig = getDYConfig(dytjData.line,'m',dytjData.limitLine);
				$('#dytj').highcharts(dytjConfig);
				
	        	var dltjData = ${dltj};
				var dltjConfig = getDLConfig(dltjData.line,'m');
				$('#dltj').highcharts(dltjConfig);
				
				var ygtjData = ${ygtj};
				var ygtjConfig = getYGConfig(ygtjData.line,'m');
				$('#ygtj').highcharts(ygtjConfig);
				
				
				var xbtjData = ${xbtj};
				var jbtjConfig = getJBConfig(xbtjData.line1,'m');
				$('#jbtj').highcharts(jbtjConfig);
				
				var pbtjData = ${pbtj};
				var pptjConfig = getPPConfig(pbtjData.line2,'m');
				$('#pptj').highcharts(pptjConfig);
				
				<c:if test="${addr=='1'}">
				var glystjData = ${glystj};
				var glystjConfig = getGLYSConfig(glystjData.line,'m',glystjData.limitLine);
				$('#glystj').highcharts(glystjConfig);
				</c:if>
	
				$('#dygrid').datagrid({   
							pagination:false,
							data:dytjData.grid.rows,
							columns:[[
								{field:'name',title:'监测点',width:300,align:'center'},
					         	{field:'phase',title:'相序',width:100,align:'center'},
					         	{field:'max',title:'最大电压(V)',width:100,align:'center'},
					         	{field:'maxtime',title:'最大电压发生时间',width:140,align:'center'},
					         	{field:'min',title:'最小电压(V)',width:100,align:'center'},
					         	{field:'mintime',title:'最小电压发生时间',width:140,align:'center'}
						    ]]   
				});  
				
				$('#dlgrid').datagrid({   
							pagination:false,
							data:dltjData.grid.rows,
							columns:[[
								{field:'name',title:'监测点',width:300,align:'center'},
					         	{field:'phase',title:'相序',width:140,align:'center'},
					         	{field:'reading',title:'最大电流值(A)',width:100,align:'center'},
					         	{field:'readtime',title:'最大电流发生时间',width:140,align:'center'}
						    ]]   
				});
				
				$('#yggrid').datagrid({   
							pagination:false,
							data:ygtjData.grid.rows,
							columns:[[
								{field:'name',title:'监测点',width:300,align:'center'},
					         	{field:'maxp',title:'最大有功功率值(kW)',width:100,align:'center'},
					         	{field:'maxptime',title:'最大有功发生时间',width:140,align:'center'},
					         	{field:'maxq',title:'最大视在功率值(kVA)',width:100,align:'center'},
					         	{field:'maxqtime',title:'最大视在功率发生时间',width:140,align:'center'}
						    ]]   
				});
				
				<c:if test="${addr=='1'}">
				$('#glysgrid').datagrid({   
							pagination:false,
							data:glystjData.grid.rows,
							columns:[[
								{field:'name',title:'监测点',width:300,align:'center'},
					         	{field:'max',title:'最大功率因数值',width:100,align:'center'},
					         	{field:'maxtime',title:'最大功率因数发生时间',width:140,align:'center'},
					         	{field:'min',title:'最小功率因数值',width:100,align:'center'},
					         	{field:'mintime',title:'最小功率因数发生时间',width:140,align:'center'}
						    ]]   
				});
				</c:if>
			});
	    </script>
	    --%>
	</body>
</html>







