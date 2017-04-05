package com.nk.report;

import java.io.InputStreamReader;
import java.io.LineNumberReader;

import javax.servlet.ServletContext;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class BuilReportUtil{
	public static boolean buildReport(String fileName,String index,String cover,String logo){
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext.getServletContext();
		String mysqlPath = servletContext.getRealPath("/export/tools"); //ConfigUtil.getProp("pdftools");
		mysqlPath = mysqlPath.replaceAll(" ","\" \"");
		fileName = fileName.replaceAll(" ","\" \"");
		Runtime runtime = Runtime.getRuntime();
		String cmd = "cmd /c "+mysqlPath+"/pdf.exe --margin-top 18 --encoding \"utf-8\" --header-spacing 3 --header-line --footer-center \"[page]\" --footer-line --toc --toc-l1-font-size 15 --toc-l2-font-size 15 --toc-header-text \"目录\" --cover \""+cover+"\" --header-html \""+logo+"\" \""+index+"\" "+fileName;
		System.out.println("命令："+cmd);
		try{
			Process process = runtime.exec(cmd);
			InputStreamReader inputStreamReader = new InputStreamReader(process.getErrorStream());
			LineNumberReader lineNumberReader = new LineNumberReader(inputStreamReader);
			String line;
			while((line = lineNumberReader.readLine()) != null){
				System.out.println(line+"----------------");
			}
			int result = process.waitFor();
			if(result == 0){
				System.out.println("生成报表成功");
				return true;
			}else{
				System.out.println("生成报表失败");
				return false;
			}
		}catch(Exception e){
			System.out.println("生成报表失败");
			e.printStackTrace();
			return false;
		}
	}
}








