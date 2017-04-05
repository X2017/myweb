<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/taglib.jsp"%>
<%@ include file="/WEB-INF/jsp/include/easyui.jsp"%>
<%@ include file="/WEB-INF/jsp/include/echart.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title>交流能效评估报告</title>
		<style>
			html,body{
				margin: 0 0;
				width: 1250px;
			}
			img{
				max-width:100%
			}
		</style>
		<%-- 
		<script language="JavaScript" type="text/javascript" src="<%=request.getContextPath()%>/js/ListBox.js"></script>
		--%>
		<script type="text/javascript">
			var path = '<%=request.getContextPath()%>';
			/*
			var oListBox;
			var OnClick = function(Sender,EventArgs){
			}
			window.onunload = function(){
		    	oListBox.Dispose();
		    }
			*/
		</script>
		<script type="text/javascript" src="${path}/js/jlnxpgbg.js"></script>
	</head>
	<body class="easyui-layout">
		<%-- 
		<center>
			<table style="width:1000px;height:200px;" border="1">
				<tr>
					<td>1</td>
					<td>2</td>
					<td>3</td>
				</tr>
				<tr>
					<td>4</td>
					<td>5</td>
					<td>6</td>
				</tr>
				<tr>
					<td>7</td>
					<td>8</td>
					<td>9</td>
				</tr>
			</table>
		</center>
		--%>
		
		<%-- 
		<table border="0" width="80%">
			<tr>
				<td>
					<font size="5pt">
						&nbsp;&nbsp;通过统计交流电耗及其对应的影响因子信息（包括气温、降雨量、湿度、昼夜长短等），采用智能化分析手段，<br>
						提取出交流电耗的依赖关（待定）系，	实现地铁车站的智能化能耗管理。交流能耗评估报告包括:<br>
						1)	车站照明、动力、环控及商业能效评估报告<br>
						2)	电量平衡及用能漏洞排查报告（包括用电量变化因素分析）<br>
						3)	目标化管理及考核报告
					</font>
				</td>
			</tr>
		</table>
		左侧是列表框包含空调系统、照明系统、动力系统等
		中间绘制负荷曲线、仿真负荷曲线、参量因子输入、节能量计算
		右边包含主机1、优化方案1、优化方案2
		--%>
		
		<%-- 
		<div data-options="region:'west',title:'能效系统模型',collapsible:false" style="width:15%;">
			<div id="base" style="width:100%;height:100%;"></div>
			<div id="DivMessage"></div>
			<script language="JavaScript">
				var availWidth = window.screen.availWidth;
				var Arguments = {
					Base: document.getElementById('base'),
					Rows: 32, //此处可调整高度
					Width: 252, //(availWidth-160)*0.15-getScrollBarWidth().horizontal
					NormalItemColor: null,
					NormalItemBackColor: null,
					AlternateItemColor: null,
					AlternateItemBackColor: null,
					SelectedItemColor: null,
					SelectedIItemBackColor: null,
					HoverItemColor: null,
					HoverItemBackColor: null,
					HoverBorderdColor: null,
					ClickEventHandler: OnClick
		        };
				oListBox = new ListBox(Arguments);
				oListBox.AddItem('空调系统','1');
				oListBox.AddItem('照明系统','2');
				oListBox.AddItem('动力系统','3');
				oListBox.SelectItem(0);
			</script>
		</div>
		--%>
		<div data-options="region:'center',border:true" style="width:1200px;">
			<div id="imgDiv" data-options="fit:true">
				<div id="chart" style="width:100%;">
					<h1>交流能效评估报告</h1>
					<div id="gl" class="fl" style="width:100%;height:500px"></div>
				</div>
				<div id="inputDiv">
					<table border="1">
						<tr>
							<th>调节温度</th>
							<th>调节功率</th>
							<th>优化1今日节能量统计(kWh)</th>
							<th>优化2今日节能量统计(kWh)</th>
							<th>优化3今日节能量统计(kWh)</th>
						</tr>
						<tr height="40px">
							<td align="center" width="35%">
								<input type="radio" name="workType" value="1" checked="checked"/>致冷
								<input type="radio" name="workType" value="2"/>加热
								<input type="button" value="增加一度" onclick="handleAddTemp()"/>&nbsp;
								<input type="button" value="减低一度" onclick="handleSubTemp()"/>
							</td>
							<td align="center" width="35%">
								<input type="button" value="增加一千瓦" onclick="handleAddPower()"/>&nbsp;
								<input type="button" value="减低一千瓦" onclick="handleSubPower()"/>
							</td>
							<td align="center" width="10%">10</td>
							<td align="center" width="10%">9.6</td>
							<td align="center" width="10%">10.8</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		<div data-options="region:'east',border:true" style="width:200px;">
			<table class="easyui-datagrid" data-options="singleSelect:true,border:false">
				<thead>
			        <tr>
			            <th data-options="field:'name',align:'left'" width="40%"><strong>名称</strong></th>
			            <th data-options="field:'setLowValueColumn',align:'center'" width="60%"><strong>节能措施</strong></th>
			        </tr>
			    </thead>
			    <tbody>
					<tr>
						<td>主机1(风机)</td>
						<td><a href="#">优化1</a>&nbsp;&nbsp;<a href="#">优化2</a>&nbsp;&nbsp;<a href="#">优化3</a>&nbsp;&nbsp;</td>
					</tr>
					<tr>
						<td>主机2(水泵)</td>
						<td><a href="#">优化1</a>&nbsp;&nbsp;<a href="#">优化2</a>&nbsp;&nbsp;<a href="#">优化3</a>&nbsp;&nbsp;</td>
					</tr>
					<tr>
						<td>主机3(压缩机)</td>
						<td><a href="#">优化1</a>&nbsp;&nbsp;<a href="#">优化2</a>&nbsp;&nbsp;<a href="#">优化3</a>&nbsp;&nbsp;</td>
					</tr>
					<tr>
						<td>主机4</td>
						<td><a href="#">优化1</a>&nbsp;&nbsp;<a href="#">优化2</a>&nbsp;&nbsp;<a href="#">优化3</a>&nbsp;&nbsp;</td>
					</tr>
				</tbody>
			</table>
		</div>
	</body>
</html>





