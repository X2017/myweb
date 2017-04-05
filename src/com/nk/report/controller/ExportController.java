package com.nk.report.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nk.model.easyui.PageHelper;
import com.nk.pojo.INF_CUSTOMER;
import com.nk.pojo.INF_MP;
import com.nk.report.BuildMonthReportTask;
import com.nk.service.SystemService;

@Controller
public class ExportController{
	@Resource
	private SystemService sysService;
	
	@RequestMapping("export/index")
	public String index(HttpServletRequest request,HttpServletResponse response,ModelMap map) throws Exception{
		String reportId = request.getParameter("reportId");
		if(reportId=="")
			reportId = null;
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String day = request.getParameter("day");
		String dateType = "0";
		if(day==null || day.equals("")){
			dateType = "2";
			day = "1";
		}
		String addr = request.getParameter("mpAddr");
		if(addr==null || addr.equals(""))
			addr = "2";
		String customerId = request.getParameter("customerId");
		if(customerId==null || customerId.equals(""))
			customerId = "1"; //实际要改成根据当前用户来取值
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String fromDate = "";
		String toDate = "";
		int y = Integer.parseInt(year);
		int m = Integer.parseInt(month)-1;
		int d = Integer.parseInt(day);
		Calendar cal = Calendar.getInstance();
		cal.set(y,m,d,0,0,0);
		fromDate = sdf.format(cal.getTime());
		if(dateType.equals("0")){
			cal.set(y,m,d,23,59,59);
			toDate = sdf.format(cal.getTime());
		}else{
			cal.set(y,m,d,23,59,59);
			cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
			toDate = sdf.format(cal.getTime());
		}
		String dltj = "";
		String dytj = "";
		String glystj = "";
		String ygtj = "";
		String xbtj = "";
		String pbtj = "";
		INF_MP vo = new INF_MP();
		vo.setCustomer_id(Integer.parseInt(customerId)); //vo.setCustomerId(Long.parseLong(customerId));
		vo.setMp_addr(addr); //vo.setMpAddr(addr);
		PageHelper ph = new PageHelper();
		ph.setPage(1);
		ph.setRows(sysService.getMeterTotal(null,null,null,null));
		List<INF_MP> list = sysService.getPageMeter(ph,null,null,null,null); //mpService.getInfMpByCustomerId(vo);
		if(list.size() > 0){
			String[] id = new String[list.size()];
			String[] name = new String[list.size()];
			for(int i = 0; i<list.size(); i++){
				INF_MP mp = list.get(i);
				id[i] = Long.toString(mp.getMp_id());
				name[i] = mp.getMp_no()+":"+mp.getMp_name();
			}
			dltj = "电量统计分析"; //lowService.getDltjDataB(customerId,id,name,addr,fromDate,toDate,dateType);
			dytj = "电压统计分析"; //lowService.getDytjDataB(customerId,id,name,addr,fromDate,toDate,dateType);
			glystj = "功率因数统计分析"; //lowService.getGlystjDataB(customerId,id,name,addr,fromDate,toDate,dateType);
			ygtj = "有功功率统计分析"; //lowService.getYgtjDataB(customerId,id,name,addr,fromDate,toDate,dateType);
			xbtj = "谐波与畸变率统计-畸变率统计、各次谐波含有率/含量统计"; //lowService.getXbtjDataB(customerId,id,name,addr,fromDate,toDate,dateType,"2");
			pbtj = "谐波与畸变率统计-电压频谱"; //lowService.getXbtjDataBP(customerId,id,name,addr,fromDate,toDate,dateType,null);
		}
//		NkReport rp = null;
//		if(reportId != null){
//			rp = rpService.getNkReportById(Long.parseLong(reportId));
//		}
		map.put("dltj",dltj);
		map.put("dytj",dytj);
		map.put("glystj",glystj);
		map.put("ygtj",ygtj);
		map.put("xbtj",xbtj);
		map.put("pbtj",pbtj);
		map.put("checkTime",fromDate+"~"+toDate);
		map.put("addr",addr);
//		map.put("rp",rp);
		return "export/index";
	}

	@RequestMapping("export/logo")
	public String logo(HttpServletRequest request,HttpServletResponse response,ModelMap map) throws Exception{
		return "export/logo";
	}

	@RequestMapping("export/cover")
	public String cover(HttpServletRequest request,HttpServletResponse response,ModelMap map) throws Exception{
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String day = request.getParameter("day");
		String dateType = "0";
		if(day==null || day.equals("")){
			dateType = "2";
			day = "1";
		}
		String addr = request.getParameter("mpAddr");
		if(addr==null || addr.equals(""))
			addr = "2";
		String customerId = request.getParameter("customerId");
		if(customerId==null || customerId.equals(""))
			customerId = "1"; //实际要改成根据当前用户来取值
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String fromDate = "";
		String toDate = "";
		int y = Integer.parseInt(year);
		int m = Integer.parseInt(month)-1;
		int d = Integer.parseInt(day);
		Calendar cal = Calendar.getInstance();
		cal.set(y,m,d,0,0,0);
		fromDate = sdf.format(cal.getTime());
		if(dateType.equals("0")){
			cal.set(y,m,d,23,59,59);
			toDate = sdf.format(cal.getTime());
		}else{
			cal.set(y,m,d,23,59,59);
			cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
			toDate = sdf.format(cal.getTime());
		}
//		InfCustomer customer = customerService.getInfCustomerById(Long.parseLong(customerId));
		INF_CUSTOMER customer = new INF_CUSTOMER();
		customer.setCustomer_name("测试客户");
		cal.setTime(new Date());
		String today = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		map.put("checkTime",fromDate+" 至 "+toDate);
		map.put("today",today);
		map.put("customer",customer);
		map.put("addr",addr);
		map.put("type",dateType);
		return "export/cover";
	}
	
	@ResponseBody
	@RequestMapping(value = "/export/queryElecData/todayPowerData")
	public String todayPowerData(HttpServletRequest request){
		String echartsData = "[{\"a\":\"1\",\"b\":\"2\",\"c\":\"3\"},{\"a\":\"4\",\"b\":\"5\",\"c\":\"6\"}]";
		return echartsData;
	}
	
	@RequestMapping(value = "/export/jlnxpgbg")
	public String testEcharts(HttpServletRequest request){
		return "export/jlnxpgbg";
	}
	
	@RequestMapping(value = "/export/rebuild")
	public String rebuild(HttpServletRequest request,HttpServletResponse response,ModelMap map) throws Exception{
		try{
			String reportId = request.getParameter("reportId");
			if(reportId == null || "".equals(reportId)){
				reportId = String.valueOf(Calendar.getInstance().getTime().getTime());
			}
			request.getServletContext().setAttribute("port",request.getServerPort());
			request.getServletContext().setAttribute("path",request.getContextPath());
			String customerId = request.getParameter("customerId");
			String year = request.getParameter("year");
			String mon = request.getParameter("month");
			String type = request.getParameter("type");
			String reportName = "低压运行月报";
			boolean isHightReport = false;
			if("1".equals(type)){
				reportName = "高压运行月报";
				isHightReport = true;
			}
			BuildMonthReportTask task = new BuildMonthReportTask();
			boolean result = task.rebuildMonthReport(Long.parseLong(reportId),reportName+year+mon,Long.parseLong(customerId),isHightReport);
			response.getWriter().print(result);
			response.getWriter().flush();
		}catch(Exception e){
			e.printStackTrace();
		}
		return "export/jlnxpgbg";
	}
}










