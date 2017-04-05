<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
	<head>
		<title>Excel表导入导出</title>
		<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
		 <style type="text/css">
	    	#box{width: 90%;padding:20px 0 0 0;margin: 0 auto;}
	    	#xlf{width: 350px;padding: 6px 0;border: 1px solid silver;cursor: pointer;color: blue;border-radius: 2px;}
	    	.table{font-size:15px;margin:10px 0 20px 0;border-right: 1px solid #9f9f9f;border-bottom: 1px solid #9f9f9f;}
	    	.table tr:nth-child(2n){background-color: #fafafa;}
	    	.table tr:hover{background-color: #eee;}
	    	.table td{height: 25px;text-align: center;border: 1px solid #9f9f9f;padding: 0 5px;border-right: 0 none;border-bottom: 0 none;}
	    	ol li{text-indent: 20px;line-height: 25px;}
	    	.button{cursor: pointer;height: 40px;padding: 0 10px;}
	    	#box .l-btn-text{font-size:15px;}
	    	#succSearBox{width:743px;line-height:40px;}
	    	.search{float:right;}
	    	.succSearBox{line-height:40px;}
    	</style>
	</head>
	<body>
		<div id="box">
			<div id="mainLeft" style="display: none;">
				<div>
					<select name="format">
				        <option value="json" selected>JSON格式</option> 
				        <!-- <option value="csv">CSV逗号分隔文本格式</option> -->
				        <!-- <option value="form">Excel公式格式</option> -->
				    </select>
				</div>
				<div id="drop" style="display:none">拖动Excel表到这里也可以上传</div>
		    	<div>
		    		<!-- checked默认配置workers多线程 -->
	    			Web Workers: <input type="checkbox" name="useworker">
	    			数据格式转换: <input type="checkbox" name="xferable" >
					二进制工作簿: <input type="checkbox" name="userabs" >
		    	</div>
		    	<pre id="out"></pre>
			</div>
			<div style="margin-bottom:5px;">
				<strong>请选择Excel表上传:</strong>
			</div>
        	<input type="file" name="xlfile" id="xlf"/>
        	<a href="#" id="updateMeter" class="easyui-linkbutton" iconCls="icon-back" title="计量点数据表" style="padding:7px 5px;width:190px;">导入到计量点数据表</a>
        	<a href="#" id="updateTerminal" class="easyui-linkbutton" iconCls="icon-back" title="终端数据表" style="padding:7px 5px;width:190px;">导入到终端数据表</a>
        	<a href="#" id="downExcel" class="easyui-linkbutton" iconCls="icon-sum" style="padding:7px 5px;width:190px;display:none;">下载表格</a>
        	<div id="succSearBox" style="margin-top:10px;">
        		<strong id="success" style="color:green;"></strong>
        		<div class="search">
        			<a href="#" id="meter" class="easyui-linkbutton" iconCls="icon-search" title="计量点数据表" style="padding:7px 5px;width:190px;">查询计量点数据</a>
        			<a href="#" id="terminal" class="easyui-linkbutton" iconCls="icon-search" title="终端数据表" style="padding:7px 5px;width:190px;">查询终端数据</a>
        		</div>
        		<br style="clear: both;">
        	</div>
			<div id="tableContent" style="margin-top:20px;">
				
			</div>
			<br style="clear: both;">
		</div>
		<%@ include file="/WEB-INF/jsp/include/taglib.jsp"%>
		<%@ include file="/WEB-INF/jsp/include/easyui.jsp"%>
		<!-- 读取本地Excel数据 生成tabel 上传table数据 -->
		<script type="text/javascript" src="${path}/js/dist/shim.js"></script>
		<script type="text/javascript" src="${path}/js/dist/jszip.js"></script>
		<script type="text/javascript" src="${path}/js/dist/xlsx.min.js"></script>
		<script type="text/javascript" src="${path}/js/dist/ods.js"></script>
		<script type="text/javascript">
			var X = XLSX;
			// 配置web Worker多线程文件
			/*var XW ={
			    msg: 'xlsx',
			    rABS: '${path}/js/xlsxworker2.js',
			    norABS: '${path}/js/xlsxworker1.js',
			    noxfer: '${path}/js/xlsxworker.js'
			};*/
			function getName(name){
				return document.getElementsByName(name);
			}
			// 是否支持 FileReader 对象, FileReader异步读取存储在用户计算机上的文件
			var rABS = typeof FileReader !== "undefined" && typeof FileReader.prototype !== "undefined" && typeof FileReader.prototype.readAsBinaryString !== "undefined";
			if(!rABS){
			    getName("userabs")[0].disabled = true;
			    getName("userabs")[0].checked = false;
			}
			// 是否支持html5 Web Worker 多线程对象
			var use_worker = typeof Worker !== 'undefined';
			if(!use_worker){ //支持就不执行 不支持执行设为false
			    getName("useworker")[0].disabled = true;
			    getName("useworker")[0].checked = false;
			}
			// 是否数据转换
			var transferable = use_worker;
			if(!transferable){
			    getName("xferable")[0].disabled = true;
			    getName("xferable")[0].checked = false;
			}
			// base64标记
			var wtf_mode = false;
			// 选取数据 转换Unicode码 
			function fixdata(data){
			    var o = "",l = 0,w = 10240;
			    for(; l < data.byteLength / w; ++l) o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w, l * w + w)));
			    o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w)));
			    return o;
			}
			function ab2str(data){
			    var o = "",l = 0,w = 10240;
			    for(; l < data.byteLength / w; ++l) o += String.fromCharCode.apply(null, new Uint16Array(data.slice(l * w, l * w + w)));
			    o += String.fromCharCode.apply(null, new Uint16Array(data.slice(l * w)));
			    return o;
			}
			// 把Array对象转换成无符号数组
			function s2ab(s){
			    var b = new ArrayBuffer(s.length * 2), //每个字符占用2个字节
			        v = new Uint16Array(b);
			    for(var i = 0; i != s.length; ++i) v[i] = s.charCodeAt(i);
			    return [v, b];
			}
			//调用xlsxworkd读取
			function xw_noxfer(data, cb){
			    var worker = new Worker(XW.noxfer);
			    worker.onmessage = function(e){
			        switch(e.data.t){
			            case 'ready':
			                break;
			            case 'e':
			                console.error(e.data.d);
			                break;
			            case XW.msg:
			                cb(JSON.parse(e.data.d));
			                break;
			        }
			    };
			    var arr = rABS ? data : btoa(fixdata(data));
			    worker.postMessage({
			        d: arr,
			        b: rABS
			    });
			}
			//调用xlsxworkd读取
			function xw_xfer(data, cb){
			    var worker = new Worker(rABS ? XW.rABS : XW.norABS);
			    worker.onmessage = function(e){
			        switch(e.data.t){
			            case 'ready':
			                break;
			            case 'e':
			                alert('读取Excel文件失败,请确保是Excel文件!'+'\n'+e.data.d);
			                break;
			            default:
			                xx = ab2str(e.data).replace(/\n/g, "\\n").replace(/\r/g, "\\r");
			                cb(JSON.parse(xx));//转成JSON
			                break;
			        }
			    };
			    if(rABS){
			        var val = s2ab(data);
			        worker.postMessage(val[1], [val[1]]);
			    } else{
			        worker.postMessage(data, [data]);
			    }
			}
			// 格式转换
			function xw(data, cb){
			    transferable = getName("xferable")[0].checked;
			    if(transferable){
			    	xw_xfer(data, cb);
			    } else{
			    	xw_noxfer(data, cb);
			    }
			}
			// 要转换成的文件格式
			function get_radio_value(radioName){
			    var radios = getName(radioName);
			    for(var i = 0; i < radios.length; i++){
			        if(radios[i].checked || radios.length === 1){
			            return radios[i].value;
			        }
			    }
			}
			// 转成JSON
			function to_json(workbook){
			    var result ={};
			    workbook.SheetNames.forEach(function(sheetName){
			        var roa = X.utils.sheet_to_row_object_array(workbook.Sheets[sheetName]);
			        if(roa.length > 0){
			            result[sheetName] = roa;
			        }
			    });
			    return result;
			}
			// 转成逗号隔开的文本
			function to_csv(workbook){
			    var result = [];
			    workbook.SheetNames.forEach(function(sheetName){
			        var csv = X.utils.sheet_to_csv(workbook.Sheets[sheetName]);
			        if(csv.length > 0){
			            result.push("SHEET: " + sheetName);
			            result.push("");
			            result.push(csv);
			        }
			    });
			    return result.join("\n");
			}
			// 转成Excel公式表
			function to_formulae(workbook){
			    var result = [];
			    workbook.SheetNames.forEach(function(sheetName){
			        var formulae = X.utils.get_formulae(workbook.Sheets[sheetName]);
			        if(formulae.length > 0){
			            result.push("SHEET: " + sheetName);
			            result.push("");
			            result.push(formulae.join("\n"));
			        }
			    });
			    return result.join("\n");
			}
			// 拖拽上传
			var drop = document.getElementById('drop');
			// 读取拖拽上传的文件
			function handleDrop(e){
			    e.stopPropagation();
			    e.preventDefault();
			   rABS = getName("userabs")[0].checked;
			    use_worker = getName("useworker")[0].checked;
			    var files = e.dataTransfer.files;
			    var f = files[0];{
			        var reader = new FileReader();
			        var name = f.name;
			        reader.onload = function(e){
			            var data = e.target.result;
			            if(use_worker){
			                xw(data, process_wb);
			            } else{
			                var wb;
			                if(rABS){
			                    wb = X.read(data,{
			                        type: 'binary'
			                    });
			                } else{
			                    var arr = fixdata(data);
			                    wb = X.read(btoa(arr),{
			                        type: 'base64'
			                    });
			                }
			                process_wb(wb);
			            }
			        };
			        if(rABS){
			        	reader.readAsBinaryString(f);
			        }else{
			        	reader.readAsArrayBuffer(f);
			        }
			    }
			}
			// 拖拽上传阻止默认事件
			function handleDragover(e){
			    e.stopPropagation();
			    e.preventDefault();
			    e.dataTransfer.dropEffect = 'copy';
			}
			// 拖拽上传的事件
			if(drop.addEventListener){
			    drop.addEventListener('dragenter', handleDragover, false);
			    drop.addEventListener('dragover', handleDragover, false);
			    drop.addEventListener('drop', handleDrop, false);
			}
			// 浏览上传
			var xlf = document.getElementById('xlf');
			var getData = document.getElementById('getData');
			// 读取拖浏览上传的文件
			function handleFile(e){
			    rABS = getName("userabs")[0].checked;
			    use_worker = getName("useworker")[0].checked;
			    var files = e.target.files;
			    var f = files[0];{
			        var reader = new FileReader();//FileReader对象,web应用程序可以异步的读取存储在用户计算机上的文件(或者原始数据缓冲)内容
			        var name = f.name;
			        reader.onload = function(e){
			            var data = e.target.result;
			            if(use_worker){
			                xw(data, process_wb);
			            } else{
			                var wb;
			                if(rABS){
			                    wb = X.read(data,{
			                        type: 'binary'
			                    });
			                } else{
			                    var arr = fixdata(data);
			                    wb = X.read(btoa(arr),{
			                        type: 'base64'
			                    });
			                }
			                process_wb(wb);
			            }
			        };
			        if(rABS){
			        	reader.readAsBinaryString(f);
			        } else{
			        	reader.readAsArrayBuffer(f);
			        }
			    }
			}
			// 浏览控件上传的事件 改变了文件就读取数据
			if(xlf.addEventListener){
				xlf.addEventListener('change', handleFile, false);
			}
			 // 读取数据处理 生成表
			function process_wb(wb){
			    var output = "";
			    switch(get_radio_value("format")){
			        case "json":
			            output = JSON.stringify(to_json(wb), 2, 2);
			            break;
			        case "form":
			            output = to_formulae(wb);
			            break;
			        default:
			            output = to_csv(wb);
			    }
				//成功读取数据
				$('#success').text('浏览器成功读取Excel表数据');
				$('#tableContent').html('');
				var value = $.parseJSON(output); //JSON数组对象
				var sheet = []; //每个表名 因为一个工作表里面还有表
				var head = []; //每个表的表头 根据这个表头访问每行的数据
				var count = 0; //和表数量次数一样 通过count记录和访问表名和添加tabel ID
				var tableData = []; //每张表的全部数据
			    $('#tableContent').append('<table id='+count+' class="table" border="0" cellpadding="0" cellspacing="0"></table>'); //添加一个table
				for(var i in value){ //第一个循环
					sheet.push(i); //保存表名
			        // for(var j =0; j<sheet.length;j++){ //循环表名数量 保存全部表头
					for(var j =0; j<1;j++){ //只读取Excel第一张表
			    		head[j] = [];//一个数组代表一张表 保存每张表的第一行表头
			    		for(var k in value[sheet[j]][0]){ //以每张表的第一行做为表头
			    			if(j == j){
			        			head[j].push(k); //保存表头
			    			}
			    		}
			    	}
			    	//$('#tableContent').append('<strong>'+sheet[count]+'</strong><table id='+count+' class="table" border="0" cellpadding="0" cellspacing="0"></table>'); //添加多个table
			   		for(var j = 0; j < value[sheet[count]].length+1; j++){ //sheet保存的是表头名 通过表头名访问有多少值 加1表头占一行
			   			$('#'+count).append('<tr></tr>');
			   		}
			   		tableData.push(value[sheet[count]]); //保存每张表的数据
			    	count++;
				}
				for(var i = 0; i < head.length; i++){ 
					for(var j = 0; j < head[i].length; j++){
			   			$($('#'+i).find('tr')[0]).append('<td>'+head[i][j]+'</td>'); // 添加表头
			   		}
					for(var j = 0; j < value[sheet[i]].length; j++){ //每张表的行数
						for(var k = 0; k < head[i].length; k++){ //表头 每张表列数
			   				$($('#'+i).find('tr')[j+1]).append('<td>'+tableData[i][j][head[i][k]]+'</td>'); // i表数量 j表行数 k表头表列数 添加td 第一行开始 
						}
					}
				}
				$.each($('#0').find('td'),function(index,value){
					if($(value).text() == 'undefined'){
						$(value).text('');
					}
				});
			}
			//上传表数据 计量点和终端数据库
			$('#updateTerminal,#updateMeter').click(function(){
				var _this=this;
				if($('#tableContent').find('table').size() > 0){
					$.messager.confirm("上传提示","<p style=font-size:16px;><span style=color:red;>是否确定导入到 "+$(_this).attr('title')+" ？ </span>如果数据库没有该表内容会新增数据到"+$(_this).attr('title')+"，如果有该表会覆盖之前的数据。</p>",function (flag) {
						if(flag){
							update($(_this).attr('id'),$('#0').find('tr').size(),$($('#0').find('tr')[0]).find('td').size(),getTableData('0'));
						}
					});
				}else{
					$.messager.alert('操作提示','请选择Excel表上传','warning');
				}
			});
			//得到表格行列数据
			function getTableData(id){
				var data = [];
				/*上传多张表
			    $.each($('.table'),function (index1,value1) {
			        data[index1] = []; 
					$.each($(value1).find('td'),function (index2,value2) {
			            var key = value2.parentNode.rowIndex+"_"+value2.cellIndex+"_"+value2.rowSpan+"_"+value2.colSpan+"_0";
			            var keyData = {};
			                keyData[''+key+''] = value2.innerText;
			            data[index1].push(keyData);
			        });
				});*/
			     $.each($('#'+id+'').find('td'),function (index2,value2) { //只上传一张表
			        var key = value2.parentNode.rowIndex+"_"+value2.cellIndex+"_"+value2.rowSpan+"_"+value2.colSpan+"_0";
			        var keyData = {};
			            keyData[''+key+''] = value2.innerText;
			        data.push(keyData);
			    });
				return data;
			}
			//导入表格ajax
			function update(id,row,cell,data){
				$.ajax({
			        type:'post',
			        url:'${path}/excelImportDocInfo/data',
					dataType:"json",
			        data:{
			       		id:id,
			       		rowCount:row,
			       		cellCount:cell,
			        	tableData:JSON.stringify(data)
			        },
			        beforeSend:function (jqXHR,setting) {
			        	$.messager.progress({text:'正在导入...'});
			        },
			        success:function (data) {
			        	$.messager.progress('close');
			        	
			        },
			        error:function(){
			        	$.messager.progress('close');
			        }
			    });
			}
			//查询表格
			$('#terminal,#meter').click(function(){
				$('#tableContent').html('');
				if($(this).attr('id') == 'terminal'){
					searchExcel($(this).attr('id'),$(this).attr('title'),'${path}/excelImportDocInfo/getAllTerm');
				}else{
					searchExcel($(this).attr('id'),$(this).attr('title'),'${path}/excelImportDocInfo/getAllMp');
				}
			});
			//下载Excel表格
			$('#downExcel').click(function(){
				if($('#tableContent').find('table').size() > 0){
					$.ajax({
						type:'post',
						url:'${path}/excelImportDocInfo/saveReportData',
				        data:{tableData:JSON.stringify(getTableData('table'))},
				        dataType:"json",
				        beforeSend:function (jqXHR,setting) {
				        	$.messager.progress({text:'正在下载...'});
				        },
				        success:function (data) {
				        	$.messager.progress('close');
				        	if(data.fileName != ''){
								var winFt = 'fullscreen=0,height=140,left=4,location=0,menubar=0,resizable=0,scrollbars=0,status=0,titlebar=0,toolbar=0,top=4,width=300';
								window.open("excelImportDocInfo/export?excel="+$('#table').attr('title'),'',winFt);
							}else{
								$.messager.alert('操作提示','下载失败,请重试！','warning');
							}
				        }, 
				        error:function(){
				        	$.messager.progress('close');
				        }
				    });
				}
			});
			//查询之后表头按照数组顺序填充
			var terminal=['创建时间','创建人','所属用户ID','终端IP地址','是否有效',
			              '接线方式','终端SIM卡号码','备注','终端SIM卡序列号', '逻辑地址',
			              '终端资产编号','终端ID','终端厂家','终端型号','终端名称',
			              '终端通信规约','终端状态'];
			var meter=['电表类别','电表厂家','电表通讯规约','电表通讯规约地址','电表型号',
			           '核定需量','顺序号','创建时间','创建人','CT变比',
			           '综合倍率','所属用户ID','电量采集间隔','所属监测设备','所属组',
			           '存储图片路径','行业类别','是否重点能耗监测','是否公用','主计量点ID',
			           '计量点安装位置','计量点资产编号','计量点分类','计量点ID','计量点名称',
			           '计量点编号','起始表底值','计量点状态','用电类别','接线方式','PT变比',
			           '额定电流','额定电压','备注','力率执行标准','所属监测系统','所属终端ID','所属变压器ID'];
			/* 查询表格 */
			function searchExcel(id,title,url){
				$.ajax({
			        type:'post',
			        url:url,
			        beforeSend:function (jqXHR,setting) {$.messager.progress({text:'正在查询...'});},
			        success:function (data) {
			        	$.messager.progress('close');
			        	var json=$.parseJSON(data);
			        	var dataName=[];
			        	
			        	if(json.length > 0){
			        		$('#tableContent').append('<table id=table class=table title='+title+' border=0 cellpadding=0 cellspacing=0></table>');
			        		for(var i in json[0])dataName.push(i);
			        		for(var i=0;i<json.length+1;i++){
								$('#table').append('<tr></tr>');
			        		}
			        		if(id == 'terminal'){//终端
			        			$('#success').text('终端数据表');
			        			createTableHead(terminal);
			        			createTable(terminal);
			        		}else if(id == 'meter'){//计量点
			        			createTableHead(meter);
			        			createTable(meter);
			        		}
			        		$('#downExcel').show();
			        		function createTableHead(ele){//填充表头
			        			for(var i=0;i<ele.length;i++){
					        		$('#table').find($('tr')[0]).append('<td>'+ele[i]+'</td>');
					        	}
			        		}
			        		function createTable(ele){//填充表格数据
			        			for(var i=0;i<ele.length;i++){
			        				for(var j=0;j<json.length;j++){
				        				$('#table').find($('tr')[j+1]).append('<td>'+json[j][dataName[i]]+'</td>');//从第二行开始填充数据
				        			}
				        		}
			        		}
			        	}
			        },
			        error:function(){
			        	$.messager.progress('close');
			        }
			    });
			}
		</script>
	</body>
</html>