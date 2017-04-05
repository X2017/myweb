<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
	<head>
		<title>厂家自组报文下发</title>
		<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
		<%@ include file="/WEB-INF/jsp/include/taglib.jsp"%>
		<%@ include file="/WEB-INF/jsp/include/easyui.jsp"%>
		<!-- 下拉多选框CSS文件-->
		<link href="${path}/js/multiple-select/multiple-select.css" rel="stylesheet" type="text/css"/>
		<style style="text/css">
			#box{width:80%;margin:0 auto;position:relative;}
			.textBox{margin:0 0 15px 0;}
			#tool input{margin:0 10px 10px 0;width:100px;height:40px;cursor:pointer;font-size:15px;background:#fafafa;background-repeat:repeat-x;border:1px solid #bbb;background:-webkit-linear-gradient(top,#fff 0,#eee 100%);background:-moz-linear-gradient(top,#fff 0,#eee 100%);background:-o-linear-gradient(top,#fff 0,#eee 100%);background:linear-gradient(to bottom,#fff 0,#eee 100%);background-repeat:repeat-x;filter:progid:DXImageTransform.Microsoft.gradient(startColorstr=#ffffff, endColorstr=#eeeeee, GradientType=0);-moz-border-radius:5px 5px 5px 5px;-webkit-border-radius:5px 5px 5px 5px;border-radius:5px 5px 5px 5px}
			#tool input:hover{background:#CFF;}
			.textBox textarea{resize: none;width:800px;height:200px;border:1px solid #999;border-radius:3px;padding:10px;}
			fieldset{width:820px;border:1px solid black;border-radius:3px;}
			#left{width:250px;position:absolute;top:0;left:0;}
			#right{width:80%;height:50px;position:absolute;top:0;left:250px;padding:0 0 0 30px;}
			#left .ms-drop.bottom{width:250px;height:560px;font-size:13px;letter-spacing:2px;}
			#left .ms-drop.bottom ul{height:590px;}
			.ms-choice{color:blue;}
			.ms-drop ul > li label{height:25px;line-height:25px;}
		</style>
	</head>
	<body>
		<div id="box">
			<div id="left">
				<select id="termList" multiple="multiple" style="width:250px;"></select>
			</div>
			<div id="right">
				<div id="tool">
					<input type="button" id="send" value="发送">
					<input type="button" id="toBlank" value="加空格">
					<input type="button" id="noBlank" value="去空格">
					<input type="button" id="upper" value="转大写">
					<input type="button" id="lower" value="转小写">
					<input type="button" id="clearText" value="清空文本">
				</div>
				<div class="textBox">
					<form id="sendForm">
						<fieldset>
							<legend>发送区域</legend>
							<input style="display:none" type="text" id="terminalId" name="terminalId">
							<textarea id="sendHexFrame" name="sendHexFrame">68 4A 00 4A 00 68 4B 22 22 65 00 64 09 E0 00 00 01 00 21 00 00 00 00 00 63 16</textarea>
						</fieldset>
					</form>
				</div>
				<div id="getBox" class="textBox">
					<fieldset>
						<legend>接收区域</legend>
						<textarea id="getMess" name="getMess"></textarea>
					</fieldset>
				</div>
			</div>
		</div>
		<!-- 下拉多选框JS文件 -->
		<script src="${path}/js/multiple-select/multiple-select.js" type="text/javascript"></script>
		<script type="text/javascript">
			$(function(){
				$('#sendHexFrame').on('keyup',function(e){
					toAry(this);
				}).on('paste',function(e){
					var _this=this;
					setTimeout(function(){
						toAry(_this);
						$(_this).val(toBlank($(_this).val()));
					},200);
				}).on('blur',function(){
					var arr=$(this).val();
					if(arr[2] != ' '){
						$(this).val(toBlank($(this).val()));
					}
				});
				$('#toBlank').click(function(){
					$('#sendHexFrame').val(toBlank($('#sendHexFrame').val()));
				});
				$('#noBlank').click(function(){
					$('#sendHexFrame').val(trim($('#sendHexFrame').val(),0));
				});
				$('#upper').click(function(){
					$('#sendHexFrame').val($('#sendHexFrame').val().toUpperCase());
				});
				$('#lower').click(function(){
					$('#sendHexFrame').val($('#sendHexFrame').val().toLowerCase());
				});
				$('#clearText').click(function(){
					$('#sendHexFrame').val('');
				});
				//接收集中器
				$.ajax({
			        type:'post',
			        url:'${path}/terminal/all',
			        beforeSend:function (jqXHR,setting) {},
			        success:function (data) {
			        	var json=$.parseJSON(data);
			        	if(json.length > 0){
			        		var termList=['电力能效规约','耐奇Modbus规约','国网698规约','广电0903规约']; // 1 2 3 4 其它保持原来数值
				        	for(var i=0;i<json.length;i++){
				        		$('#termList').append('<option value='+json[i].term_id+'>'+json[i].term_name+'</option>');
				        	}
				        	$('#termList').multipleSelect({
					            filter: true,
					            single: true,
					            placeholder: "请在下面选择"
					        });
					    	$('.ms-search input').attr('placeholder','请在这里搜索...').css('color','#0E5698');
					    	$('.ms-drop.bottom').show();
					    	for(var i=0;i<$('.ms-drop.bottom').find('li').size()-1;i++){
					    		
					    		$($('.ms-drop.bottom').find('li')[i]).find('label').append('<span style=float:right; class=termPro>'+json[i].term_pro+'</span>');//集中器后面添加规约
					    	}
					    	for(var i=0;i<json.length;i++){//规约转中文
					    		var value=$($('.termPro')[i]).text();
					    		var ele=$('.termPro')[i];
					    		if(value == 1){
					    			$(ele).text(termList[0]);
					    		}else if(value == 2){
					    			$(ele).text(termList[1]);
					    		}else if(value == 3){
					    			$(ele).text(termList[2]);
					    		}else if(value == 4){
					    			$(ele).text(termList[3]);
					    		}
					    	}
			        	}
			        },
			        error:function(){
			            alert('接收失败,找不到地址或网络错误,请重试!');
			        }
			    });
				$('#termList').change(function(){
		            if($(this).val() != null){
		            	$('#terminalId').val($(this).val());
		            }
		        });
				//发送报文
				$('#send').click(function(){
					if($('#terminalId').val() != '' && trim($('#sendHexFrame').val(),0).length % 2 == 0){
						var termArr=$('#terminalId').val().split(',');
						$.ajax({
							url:'${path}/remoteTermFactFrameSendAndRecv/sendHexFrame',
							type:'post',
							data:{
								terminalId:termArr[0],
								term_proCode:termArr[1],
								sendHexFrame:trim($('#sendHexFrame').val(),0)
							},
							beforeSend:function(){
								$.messager.progress({text:'正在下发中...',});
							},
							success:function(data){
								$.messager.progress('close');
								var strJSON=$.parseJSON(data);
								var obj = new Function("return" + strJSON)();
								var dataListName=[]; //dataList 先取第一个,取val
								var itemListName=[]; //itemList
								var valName=[]; //val 第三个
								for(var i in obj){
									dataListName.push(i);
								}
								for(var i in obj[dataListName[0]][0]){
									itemListName.push(i);	
								}
								for(var i in obj[dataListName[0]][0][itemListName[3]][1]){
									valName.push(i);
								}
								$('#getMess').val(toBlank(obj[dataListName[0]][0][itemListName[3]][1][valName[2]]));
							},
							error:function(){
								$.messager.progress('close');
								$.messager.alert('操作失败提示','找不到地址或网络错误,请刷新重试!','warning');
							}
						});
					}else {
						$.messager.alert('操作提示','报文格式错误或没有选择集中器!','warning');
					}
				});
			});
			//空格
			function trim(str) {
				if (arguments.length == 2) {
					return str.replace(/\s/g,'');
				}else {
					return str.replace(/(^\s*)|(\s*$)/g,'');
				}
			}
			//加空格
			function toBlank(str){
				var result = "";
				for(var i=0,len=str.length;i<len;i++){
				    result += str[i];
				    if(i % 2 == 1){
				    	result += ' ';
				    }
				}
				return result;
			} 
			//16进制
			function toAry(_this){
		        _this.value = _this.value.replace(/[^0-9a-fA-F]/g,"");
		        _this.value = _this.value.toUpperCase();
		    }
		</script>
	</body>
</html>
