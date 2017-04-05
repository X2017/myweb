package com.nk.report;

import java.io.File;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

@Service
public class BuildMonthReportTask {

	public void job(){
		/*
		List<InfCustomer> list = customerDao.gatAllInfCustomer();
		if(list.size()<=0)
			return;
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		String today = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
		String year = today.substring(0, 4);
		String mon = today.substring(5, 7);
		String lowFileName = "低压运行月报";
		String highFileName = "高压运行月报";
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        ServletContext servletContext = webApplicationContext.getServletContext();
        String s = servletContext.getRealPath("/").replaceAll("\\\\", "/");
        if(!s.isEmpty()){
        	s = s.substring(0, s.length() - 1);
        	int pos = s.lastIndexOf("/");
        	if(pos >= 0)
        		s = s.substring(pos);
        	if(servletContext.getAttribute("path") == null)
        		servletContext.setAttribute("path", s);
        }
        try{
        	MBeanServer mBeanServer = null;
        	ArrayList<MBeanServer> mBeanServers = MBeanServerFactory.findMBeanServer(null);
        	if(!mBeanServers.isEmpty())
        		mBeanServer = mBeanServers.get(0);
        	if(mBeanServer != null){
        		Set<ObjectName> objectNames = mBeanServer.queryNames(new ObjectName("Catalina:type=Connector,*"), null);
        		if(objectNames != null){
        			for(ObjectName objectName : objectNames){
        				String protocol = (String) mBeanServer.getAttribute(objectName, "protocol");
        				if(protocol.equalsIgnoreCase("HTTP/1.1")){
	        				int port = (Integer) mBeanServer.getAttribute(objectName, "port");
	        				Object o = servletContext.getAttribute("port");
	        				if(o == null)
	        					servletContext.setAttribute("port", port);
        				}
        			}
        		}
        	}

        }catch(Exception e){
        }
        String host = "localhost";//(String)servletContext.getAttribute("host");
        int port = (Integer)servletContext.getAttribute("port");
        String path = (String)servletContext.getAttribute("path");
        String url = "http://"+host+":"+port+path;
        String _cover = url+"/export/cover";
		String logo = url+"/export/logo";
		String _index = url+"/export/index";
		String export = servletContext.getRealPath("/export");
		export = export.replaceAll("\\\\", "/");
		boolean result = false;
		lowFileName = lowFileName+year+mon;
		highFileName = highFileName+year+mon;
		for(InfCustomer customer:list){
			String filePath = export+"/"+customer.getCustomerId()+"/"+year+"/"+mon;
			File file = new File(filePath);
			if(!file.exists()){ // 假如目标路径不存在, 则新建该路径  
				file.mkdirs();
	        }
			String index = _index+"?year="+year+"&month="+mon+"&customerId="+customer.getCustomerId()+"&mpAddr=";
			String cover = _cover+"?year="+year+"&month="+mon+"&customerId="+customer.getCustomerId()+"&mpAddr=";
			//生成低压报告
			result = BuilReportUtil.buildReport(filePath+"/"+lowFileName+".pdf", index+"2", cover+"2", logo);
			if(result){
				NkReport lowReport = new NkReport();
				lowReport.setDateType("2");
				lowReport.setName(lowFileName);
				lowReport.setReportTime(cal.getTime());
				lowReport.setSavePath(filePath+"/"+lowFileName+".pdf");
				lowReport.setType("2");
				lowReport.setCustomerId(customer.getCustomerId());
				customerDao.saveObject(lowReport);
			}
			//生成高压报告
			result = BuilReportUtil.buildReport(filePath+"/"+highFileName+".pdf", index+"1", cover+"1", logo);
			if(result){
				NkReport highReport = new NkReport();
				highReport.setDateType("2");
				highReport.setName(highFileName);
				highReport.setReportTime(cal.getTime());
				highReport.setSavePath(filePath+"/"+highFileName+".pdf");
				highReport.setType("1");
				highReport.setCustomerId(customer.getCustomerId());
				customerDao.saveObject(highReport);
			}
			if(!StringUtils.isEmpty(customer.getEmailaddr()))
				emailService.sendEmail("月度电气运行报告生成通知", "您的月度电气运行报告生成通知已生成，请登录系统下载。", MailUtil.getEmailAddr(), customer.getEmailaddr(), "1");
		}
		*/
	}
	
	public boolean rebuildMonthReport(Long reportId, String reportName, Long custoemrId, boolean isHightReport){
		String year = reportName.substring(6,10);
		String mon = reportName.substring(10,12);
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        ServletContext servletContext = webApplicationContext.getServletContext();
		String host = "localhost"; //(String)servletContext.getAttribute("host");
		int port = (Integer)servletContext.getAttribute("port");
		String path = (String)servletContext.getAttribute("path");
		String url = "http://"+host+":"+port+path;
		String _cover = url+"/export/cover";
		String logo = url+"/export/logo";
//		String _index = url+"/export/index";
		String _index = url+"/export/jlnxpgbg"; //之前是上一行代码，现测试用此行
		String export = servletContext.getRealPath("/export");
		String filePath = export+"/"+custoemrId+"/"+year+"/"+mon;
		File file = new File(filePath);
		if(!file.exists()){ // 假如目标路径不存在, 则新建该路径
			file.mkdirs();
		}
		String index = _index+"?year="+year+"&month="+mon+"&customerId="+custoemrId+"&reportId="+reportId+"&mpAddr=";
		String cover = _cover+"?year="+year+"&month="+mon+"&customerId="+custoemrId+"&reportId="+reportId+"&mpAddr=";
		boolean result = false;
		if(isHightReport){ //生成高压报告
			result = BuilReportUtil.buildReport(filePath+"/"+reportName+".pdf", index+"1", cover+"1", logo);
		}else{ //生成低压报告
			result = BuilReportUtil.buildReport(filePath+"/"+reportName+".pdf", index+"2", cover+"2", logo);
		}
		return result;
	}
}










