package com.nk.archive.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nk.common.CommClassId;
import com.nk.common.Communication;
import com.nk.common.DataItem;
import com.nk.common.Frame;
import com.nk.common.Json;
import com.nk.common.StaticVariable;
import com.nk.common.TmFrame;
import com.nk.model.easyui.DataGrid;
import com.nk.model.easyui.PageHelper;
import com.nk.pojo.DEVICESTATERANK;
import com.nk.pojo.DI_DEF;
import com.nk.pojo.IF_INF_MAINTAIN;
import com.nk.pojo.INF_ALARM_RECORD;
import com.nk.pojo.INF_CUSTOMER;
import com.nk.pojo.INF_FRONT_MACHINE;
import com.nk.pojo.INF_MANUFACTURER;
import com.nk.pojo.INF_MP;
import com.nk.pojo.INF_ONLLIERATE;
import com.nk.pojo.INF_TERM;
import com.nk.pojo.INF_TERMINAL_ACCOUNT;
import com.nk.pojo.INF_TERMINAL_MANAGEMENT;
import com.nk.pojo.INF_TRANSFORMER;
import com.nk.pojo.TASK_RECORD;
import com.nk.pojo.TASK_TEMPLATE;
import com.nk.protocol_cfg.NecessaryProtocolCfg;
import com.nk.service.SystemService;
import com.nk.util.SharpUtil;
import com.nk.vo.DEVICESTATERANK_VO;
import com.nk.vo.DI_DEF_VO;
import com.nk.vo.IF_INF_MAINTAIN_VO;
import com.nk.vo.INF_ALARM_RECORD_VO;
import com.nk.vo.INF_CUSTOMER_VO;
import com.nk.vo.INF_FRONT_MACHINE_VO;
import com.nk.vo.INF_MANUFACTURER_VO;
import com.nk.vo.INF_MP_VO;
import com.nk.vo.INF_ONLLIERATE_VO;
import com.nk.vo.INF_TERMINAL_ACCOUNT_VO;
import com.nk.vo.INF_TERMINAL_MANAGEMENT_VO;
import com.nk.vo.INF_TERM_VO;
import com.nk.vo.INF_TRANSFORMER_VO;
import com.nk.vo.TASK_RECORD_VO;
import com.nk.vo.TASK_TEMPLATE_VO;

@Controller
public class SystemController {
	@Resource
	private SystemService sysService;

	@RequestMapping(value = "/")
	public String test() {
		return "index";
	}

	@RequestMapping(value = "/customer")
	public String customerManager() {
		return "customer";
	}

	@ResponseBody
	@RequestMapping(value = "/customer/datagrid")
	public DataGrid datagridCustomer(PageHelper page, HttpServletRequest request) {
		String c_date = request.getParameter("search_date");
		String c_no = request.getParameter("search_val_1");
		String c_man = request.getParameter("search_val_2");
		// System.out.println(c_no+"+"+c_date+"+"+c_man);
		int totalSize = sysService.getCustomerTotal(c_date, c_no, c_man);
		DataGrid dg = new DataGrid();
		dg.setTotal(Long.valueOf(totalSize));
		List<INF_CUSTOMER> tempList = sysService.getPageCustomer(page, c_date,
				c_no, c_man);
		List<INF_CUSTOMER_VO> voList = new ArrayList<INF_CUSTOMER_VO>();
		for (int i = 0; i < tempList.size(); i++) {
			INF_CUSTOMER_VO v = new INF_CUSTOMER_VO();
			voList.add(v);
		}
		SharpUtil.formatObjectForPage(tempList, voList);
		dg.setRows(voList);
		return dg;
	}

	@ResponseBody
	@RequestMapping(value = "/customer/add")
	public Json addCustomer(INF_CUSTOMER_VO customer) {
		Json j = new Json();
		List<INF_CUSTOMER_VO> voList = new ArrayList<INF_CUSTOMER_VO>();
		voList.add(customer);
		List<INF_CUSTOMER> pojoList = new ArrayList<INF_CUSTOMER>();
		INF_CUSTOMER pojo = new INF_CUSTOMER();
		pojoList.add(pojo);
		SharpUtil.formatPageObjectToPojo(voList, pojoList);
		if (sysService.addCustomer(pojoList.get(0))) {
			j.setSuccess(true);
			j.setMsg("增加客户成功!");
		} else {
			j.setSuccess(false);
			j.setMsg("增加客户失败!");
		}
		return j;
	}

	@ResponseBody
	@RequestMapping(value = "/customer/update")
	public Json updateCustomer(INF_CUSTOMER_VO customer) {
		Json j = new Json();
		List<INF_CUSTOMER_VO> voList = new ArrayList<INF_CUSTOMER_VO>();
		voList.add(customer);
		List<INF_CUSTOMER> pojoList = new ArrayList<INF_CUSTOMER>();
		INF_CUSTOMER pojo = new INF_CUSTOMER();
		pojoList.add(pojo);
		SharpUtil.formatPageObjectToPojo(voList, pojoList);
		if (sysService.updateCustomer(pojoList.get(0))) {
			j.setSuccess(true);
			j.setMsg("更新客户成功!");
		} else {
			j.setSuccess(false);
			j.setMsg("更新客户失败!");
		}
		return j;
	}

	@ResponseBody
	@RequestMapping(value = "/customer/delete")
	public Json deleteCustomer(HttpServletRequest req) {
		Json j = new Json();
		String delCustomerIdArrayStr = req.getParameter("delCustomerIdArray");
		if (delCustomerIdArrayStr.length() > 0) {
			String delCustomerIdArray[] = delCustomerIdArrayStr.split(",");
			if (sysService.delCustomer(delCustomerIdArray)) {
				j.setSuccess(true);
				j.setMsg("删除客户成功!");
				return j;
			}
		}
		j.setSuccess(false);
		j.setMsg("删除客户失败!");
		return j;
	}

	@RequestMapping(value = "/terminal")
	public String terminalManager(ModelMap map) {
		List<Map<String, Object>> queryEnterpriseinfo = sysService.queryEnterpriseinfo(null,"0",false);
		map.put("enterpriseinfo", com.alibaba.fastjson.JSONObject.toJSON(queryEnterpriseinfo));
		return "terminal";
	}

	@ResponseBody
	@RequestMapping(value = "/terminal/all")
	public List<INF_TERM> terminalAll() {
		return sysService.getAllTerminal();
	}

	@ResponseBody
	@RequestMapping(value = "/terminal/datagrid")
	public DataGrid datagridTerminal(PageHelper page, HttpServletRequest request) {
		String c_man = request.getParameter("search_val_1");
		String ip = request.getParameter("search_val_2");
		String c_date = request.getParameter("search_date");
		String temp = request.getParameter("search");
		int totalSize = sysService.getTerminalTotal(c_man, ip, c_date, temp);
		DataGrid dg = new DataGrid();
		dg.setTotal(Long.valueOf(totalSize));
		List<INF_TERM> tempList = sysService.getPageTerminal(page, c_man, ip,
				c_date, temp);
		List<INF_TERM_VO> voList = new ArrayList<INF_TERM_VO>();
		for (int i = 0; i < tempList.size(); i++) {
			INF_TERM_VO v = new INF_TERM_VO();
			voList.add(v);
		}
		SharpUtil.formatObjectForPage(tempList, voList);
		dg.setRows(voList);
		return dg;
	}

	@ResponseBody
	@RequestMapping(value = "/terminal/getCurUserTerminalList")
	public String getCurUserTerminalList(HttpServletRequest req) {
		// List<INF_TERM> tempList = sysService.getCurUserTerminalList();
		// //应先获取当前登录用户拥有权限的终端列表,这里直接获取全部终端
		List<INF_CUSTOMER> templist = sysService.getCustomepList();
		JSONArray jsonArr = new JSONArray();
		for (INF_CUSTOMER t : templist) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("id", t.getCustomer_id());
			jsonObj.put("name", t.getCustomer_name());
			jsonArr.add(jsonObj);
		}
		return jsonArr.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/terminal/add")
	public Json addTerminal(INF_TERM_VO terminal) {
		Json j = new Json();
		List<INF_TERM_VO> voList = new ArrayList<INF_TERM_VO>();
		voList.add(terminal);
		List<INF_TERM> pojoList = new ArrayList<INF_TERM>();
		INF_TERM pojo = new INF_TERM();
		pojoList.add(pojo);
		SharpUtil.formatPageObjectToPojo(voList, pojoList);
		if (sysService.addTerminal(pojoList.get(0))) {
			j.setSuccess(true);
			j.setMsg("增加终端成功!");
		} else {
			j.setSuccess(false);
			j.setMsg("增加终端失败!");
		}
		return j;
	}

	@ResponseBody
	@RequestMapping(value = "/terminal/update")
	public Json updateTerminal(INF_TERM_VO terminal) {
		Json j = new Json();
		List<INF_TERM_VO> voList = new ArrayList<INF_TERM_VO>();
		voList.add(terminal);
		List<INF_TERM> pojoList = new ArrayList<INF_TERM>();
		INF_TERM pojo = new INF_TERM();
		pojoList.add(pojo);
		SharpUtil.formatPageObjectToPojo(voList, pojoList);
		if (sysService.updateTerminal(pojoList.get(0))) {
			j.setSuccess(true);
			j.setMsg("更新终端成功!");
		} else {
			j.setSuccess(false);
			j.setMsg("更新终端失败!");
		}
		return j;
	}

	@ResponseBody
	@RequestMapping(value = "/terminal/delete")
	public Json deleteTerminal(HttpServletRequest req) {
		Json j = new Json();
		String delTermainlIdArrayStr = req.getParameter("delTermainlIdArray");
		if (delTermainlIdArrayStr.length() > 0) {
			String delTermainlIdArray[] = delTermainlIdArrayStr.split(",");
			if (delTermainlIdArray.length > 0
					&& sysService.delTerminal(delTermainlIdArray)) {
				j.setSuccess(true);
				j.setMsg("删除终端成功!");
				return j;
			}
		}
		j.setSuccess(false);
		j.setMsg("删除终端失败!");
		return j;
	}

	@RequestMapping(value = "/meter")
	public String meterManager(ModelMap map) {
		List<Map<String, Object>> queryEnterpriseinfo = sysService.queryEnterpriseinfo(null,"0",false);
		map.put("enterpriseInfo", com.alibaba.fastjson.JSONObject.toJSON(queryEnterpriseinfo));
		List<Map<String, Object>> queryTerminfo = sysService. queryTermInfo(null,"0",false);
		map.put("termInfo", com.alibaba.fastjson.JSONObject.toJSON(queryTerminfo));
		List<Map<String, Object>> queryTransformerinfo = sysService. queryTransformerInfo(null,"0",false);
		map.put("transformerInfo", com.alibaba.fastjson.JSONObject.toJSON(queryTransformerinfo));
		return "meter";
	}

	@ResponseBody
	@RequestMapping(value = "/meter/datagrid")
	public DataGrid datagridMeter(PageHelper page, HttpServletRequest request) {
		String CREATE_DATE = request.getParameter("search_date");
		String CREATE_MAN = request.getParameter("search_val_3");
		String MP_NAME = request.getParameter("search_val_2");
		String MP_NO = request.getParameter("search_val_1");
		int totalSize = sysService.getMeterTotal(MP_NO, MP_NAME, CREATE_MAN,
				CREATE_DATE);
		DataGrid dg = new DataGrid();
		dg.setTotal(Long.valueOf(totalSize));
		List<INF_MP> tempList = sysService.getPageMeter(page, MP_NO, MP_NAME,
				CREATE_MAN, CREATE_DATE);
		List<INF_MP_VO> voList = new ArrayList<INF_MP_VO>();
		for (int i = 0; i < tempList.size(); i++) {
			INF_MP_VO v = new INF_MP_VO();
			voList.add(v);
		}
		SharpUtil.formatObjectForPage(tempList, voList);
		dg.setRows(voList);
		return dg;
	}

	@ResponseBody
	@RequestMapping(value = "/meter/add")
	public Json addMeter(INF_MP_VO meter) {
		Json j = new Json();
		List<INF_MP_VO> voList = new ArrayList<INF_MP_VO>();
		voList.add(meter);
		List<INF_MP> pojoList = new ArrayList<INF_MP>();
		INF_MP pojo = new INF_MP();
		pojoList.add(pojo);
		SharpUtil.formatPageObjectToPojo(voList, pojoList);

		if (sysService.addMeter(pojoList.get(0))) {
			j.setSuccess(true);
			j.setMsg("增加表计成功!");
		} else {
			j.setSuccess(false);
			j.setMsg("增加表计失败!");
		}
		return j;
	}

	@ResponseBody
	@RequestMapping(value = "/meter/update")
	public Json updateMeter(INF_MP_VO meter) {
		Json j = new Json();
		List<INF_MP_VO> voList = new ArrayList<INF_MP_VO>();
		voList.add(meter);
		List<INF_MP> pojoList = new ArrayList<INF_MP>();
		INF_MP pojo = new INF_MP();
		pojoList.add(pojo);
		SharpUtil.formatPageObjectToPojo(voList, pojoList);
		if (sysService.updateMeter(pojoList.get(0))) {
			j.setSuccess(true);
			j.setMsg("更新表计成功!");
		} else {
			j.setSuccess(false);
			j.setMsg("更新表计失败!");
		}
		return j;
	}

	@ResponseBody
	@RequestMapping(value = "/meter/delete")
	public Json deleteMeter(HttpServletRequest req) {
		Json j = new Json();
		String delMeterIdArrayStr = req.getParameter("delMeterIdArray");
		if (delMeterIdArrayStr != null) {
			String delMeterIdArray[] = delMeterIdArrayStr.split(",");
			if (delMeterIdArray.length > 0
					&& sysService.delMeter(delMeterIdArray)) {
				j.setSuccess(true);
				j.setMsg("删除表计成功!");
				return j;
			}
		}
		j.setSuccess(false);
		j.setMsg("删除表计失败!");
		return j;
	}

	@RequestMapping(value = "/remoteTermParamManagement")
	public String remoteTermParamManagement() {
		return "remoteTermParamManagement";
	}

	@RequestMapping(value = "/dataItemDefine")
	public String dataItemDefine() {
		return "dataItemDefine";
	}

	@ResponseBody
	@RequestMapping(value = "/dataItemDefine/datagrid")
	public DataGrid datagridDataItemDefine(PageHelper page,
			HttpServletRequest request) {
		String name = request.getParameter("search_val_1");
		DataGrid dg = new DataGrid();
		int totalSize = sysService.getDiDefTotal(name);
		dg.setTotal(Long.valueOf(totalSize));
		List<DI_DEF> tempList = sysService.getPageDiDef(page, name);
		List<DI_DEF_VO> voList = new ArrayList<DI_DEF_VO>();
		for (int i = 0; i < tempList.size(); i++) {
			DI_DEF_VO v = new DI_DEF_VO();
			voList.add(v);
		}
		SharpUtil.formatObjectForPage(tempList, voList);
		dg.setRows(voList);
		return dg;
	}

	@ResponseBody
	@RequestMapping(value = "/dataItemDefine/add")
	public Json addDataItemDefine(DI_DEF_VO diDefVo) {
		Json j = new Json();
		List<DI_DEF_VO> voList = new ArrayList<DI_DEF_VO>();
		voList.add(diDefVo);
		List<DI_DEF> pojoList = new ArrayList<DI_DEF>();
		DI_DEF pojo = new DI_DEF();
		pojoList.add(pojo);
		SharpUtil.formatPageObjectToPojo(voList, pojoList);
		if (sysService.addDiDef(pojoList.get(0))) {
			j.setSuccess(true);
			j.setMsg("增加数据项成功!");
		} else {
			j.setSuccess(false);
			j.setMsg("增加数据项失败!");
		}
		return j;
	}

	@ResponseBody
	@RequestMapping(value = "/dataItemDefine/update")
	public Json updateDataItemDefine(DI_DEF_VO diDefVo) {
		Json j = new Json();
		List<DI_DEF_VO> voList = new ArrayList<DI_DEF_VO>();
		voList.add(diDefVo);
		List<DI_DEF> pojoList = new ArrayList<DI_DEF>();
		DI_DEF pojo = new DI_DEF();
		pojoList.add(pojo);
		SharpUtil.formatPageObjectToPojo(voList, pojoList);
		if (sysService.updateDiDef(pojoList.get(0))) {
			j.setSuccess(true);
			j.setMsg("更新数据项成功!");
		} else {
			j.setSuccess(false);
			j.setMsg("更新数据项失败!");
		}
		return j;
	}

	@ResponseBody
	@RequestMapping(value = "/dataItemDefine/delete")
	public Json deleteDataItemDefine(HttpServletRequest req) {
		Json j = new Json();
		String delDataItemDefineIdArrayStr = req
				.getParameter("delDataItemDefineIdArray");
		if (delDataItemDefineIdArrayStr != null) {
			String delDataItemDefineIdArray[] = delDataItemDefineIdArrayStr
					.split(",");
			if (delDataItemDefineIdArray.length > 0
					&& sysService.delDiDef(delDataItemDefineIdArray)) {
				j.setSuccess(true);
				j.setMsg("删除数据项成功!");
				return j;
			}
		}
		j.setSuccess(false);
		j.setMsg("删除数据项失败!");
		return j;
	}

	@RequestMapping(value = "/taskDefine")
	public String taskDefine() {
		return "taskDefine";
	}

	@ResponseBody
	@RequestMapping(value = "/taskDefine/datagrid")
	public DataGrid datagridTaskTemplate(PageHelper page,
			HttpServletRequest request) {
		String name = request.getParameter("search_val_1");
		DataGrid dg = new DataGrid();
		int totalSize = sysService.getTaskTemplateTotal(name);
		dg.setTotal(Long.valueOf(totalSize));
		List<TASK_TEMPLATE> tempList = sysService.getPageTaskTemplate(page,
				name);
		List<TASK_TEMPLATE_VO> voList = new ArrayList<TASK_TEMPLATE_VO>();
		for (int i = 0; i < tempList.size(); i++) {
			TASK_TEMPLATE_VO v = new TASK_TEMPLATE_VO();
			voList.add(v);
		}
		SharpUtil.formatObjectForPage(tempList, voList);
		dg.setRows(voList);
		return dg;
	}

	@ResponseBody
	@RequestMapping(value = "/taskDefine/auto")
	public Map<String, List<DI_DEF>> autoDEF(HttpServletRequest request) {
		Map<String, List<DI_DEF>> map = sysService.autoDef();
		// String json = com.alibaba.fastjson.JSONObject.toJSONString(names);
		return map;
	}

	@ResponseBody
	@RequestMapping(value = "/taskDefine/add")
	public Json addTaskTemplate(TASK_TEMPLATE_VO taskTemplateVo) {
		Json j = new Json();
		List<TASK_TEMPLATE_VO> voList = new ArrayList<TASK_TEMPLATE_VO>();
		voList.add(taskTemplateVo);
		List<TASK_TEMPLATE> pojoList = new ArrayList<TASK_TEMPLATE>();
		TASK_TEMPLATE pojo = new TASK_TEMPLATE();
		pojoList.add(pojo);
		SharpUtil.formatPageObjectToPojo(voList, pojoList);
		if (sysService.addTaskTemplate(pojoList.get(0))) {
			j.setSuccess(true);
			j.setMsg("增加任务模板成功!");
		} else {
			j.setSuccess(false);
			j.setMsg("增加任务模板失败!");
		}
		return j;
	}

	@ResponseBody
	@RequestMapping(value = "/taskDefine/update")
	public Json updateTaskTemplate(TASK_TEMPLATE_VO taskTemplateVo) {
		Json j = new Json();
		List<TASK_TEMPLATE_VO> voList = new ArrayList<TASK_TEMPLATE_VO>();
		voList.add(taskTemplateVo);
		List<TASK_TEMPLATE> pojoList = new ArrayList<TASK_TEMPLATE>();
		TASK_TEMPLATE pojo = new TASK_TEMPLATE();
		pojoList.add(pojo);
		SharpUtil.formatPageObjectToPojo(voList, pojoList);
		if (sysService.updateTaskTemplate(pojoList.get(0))) {
			j.setSuccess(true);
			j.setMsg("更新任务模板成功!");
		} else {
			j.setSuccess(false);
			j.setMsg("更新任务模板失败!");
		}
		return j;
	}

	@ResponseBody
	@RequestMapping(value = "/taskDefine/delete")
	public Json deleteTaskTemplate(HttpServletRequest req) {
		Json j = new Json();
		String delTaskDefineIdArrayStr = req
				.getParameter("delTaskDefineIdArray");
		if (delTaskDefineIdArrayStr != null) {
			String delTaskDefineIdArray[] = delTaskDefineIdArrayStr.split(",");
			if (delTaskDefineIdArray.length > 0
					&& sysService.delTaskTemplate(delTaskDefineIdArray)) {
				j.setSuccess(true);
				j.setMsg("删除任务模板成功!");
				return j;
			}
		}
		j.setSuccess(false);
		j.setMsg("删除任务模板失败!");
		return j;
	}

	@RequestMapping(value = "/taskConfig")
	public String taskConfig() {
		return "taskConfig";
	}

	@ResponseBody
	@RequestMapping(value = "/taskConfig/allTemplateId")
	public String getAllTemplateId() {
		List<String> ids = sysService.getAllTemplateId();
		String json = com.alibaba.fastjson.JSONObject.toJSONString(ids);
		return json;
	}

	@ResponseBody
	@RequestMapping(value = "/taskConfig/getTaskTemplate")
	public String getTaskTemplate(HttpServletRequest request) {
		String id = request.getParameter("id");
		List<TASK_TEMPLATE> ts = sysService.getTaskTemplate(id);
		String json = com.alibaba.fastjson.JSONObject.toJSONString(ts);
		return json;
	}

	@ResponseBody
	@RequestMapping(value = "/taskConfig/datagrid")
	public DataGrid datagridTaskRecord(PageHelper page,
			HttpServletRequest request) {
		String connNo = request.getParameter("search_val_1");
		String jn = request.getParameter("search_val_2");
		DataGrid dg = new DataGrid();
		int totalSize = sysService.getTaskRecordTotal(connNo, jn);
		dg.setTotal(Long.valueOf(totalSize));
		List<TASK_RECORD> tempList = sysService.getPageTaskRecord(page, connNo,
				jn);
		List<TASK_RECORD_VO> voList = new ArrayList<TASK_RECORD_VO>();
		for (int i = 0; i < tempList.size(); i++) {
			TASK_RECORD_VO v = new TASK_RECORD_VO();
			voList.add(v);
		}
		SharpUtil.formatObjectForPage(tempList, voList);
		dg.setRows(voList);
		return dg;
	}

	@ResponseBody
	@RequestMapping(value = "/taskConfig/add")
	public Json addTaskRecord(TASK_RECORD_VO taskRecordVo) {
		Json j = new Json();
		List<TASK_RECORD_VO> voList = new ArrayList<TASK_RECORD_VO>();
		voList.add(taskRecordVo);
		List<TASK_RECORD> pojoList = new ArrayList<TASK_RECORD>();
		TASK_RECORD pojo = new TASK_RECORD();
		pojoList.add(pojo);
		SharpUtil.formatPageObjectToPojo(voList, pojoList);
		if (sysService.addTaskRecord(pojoList.get(0))) {
			j.setSuccess(true);
			j.setMsg("增加任务配置成功!");
		} else {
			j.setSuccess(false);
			j.setMsg("增加任务配置失败!");
		}
		return j;
	}

	@ResponseBody
	@RequestMapping(value = "/taskConfig/update")
	public Json updateTaskRecord(TASK_RECORD_VO taskRecordVo) {
		Json j = new Json();
		List<TASK_RECORD_VO> voList = new ArrayList<TASK_RECORD_VO>();
		voList.add(taskRecordVo);
		List<TASK_RECORD> pojoList = new ArrayList<TASK_RECORD>();
		TASK_RECORD pojo = new TASK_RECORD();
		pojoList.add(pojo);
		SharpUtil.formatPageObjectToPojo(voList, pojoList);
		if (sysService.updateTaskRecord(pojoList.get(0))) {
			j.setSuccess(true);
			j.setMsg("更新任务配置成功!");
		} else {
			j.setSuccess(false);
			j.setMsg("更新任务配置失败!");
		}
		return j;
	}

	@ResponseBody
	@RequestMapping(value = "/taskConfig/delete")
	public Json deleteTaskRecord(HttpServletRequest req) {
		Json j = new Json();
		String delTaskConfigIdArrayStr = req
				.getParameter("delTaskConfigIdArray");
		if (delTaskConfigIdArrayStr != null) {
			String delTaskRecordIdArray[] = delTaskConfigIdArrayStr.split(",");
			if (delTaskRecordIdArray.length > 0
					&& sysService.delTaskRecord(delTaskRecordIdArray)) {
				j.setSuccess(true);
				j.setMsg("删除任务配置成功!");
				return j;
			}
		}
		j.setSuccess(false);
		j.setMsg("删除任务配置失败!");
		return j;
	}

	@RequestMapping(value = "/deviceFactoryManagement")
	public String deviceFactoryManagement() {
		return "deviceFactoryManagement";
	}

	@ResponseBody
	@RequestMapping(value = "/deviceFactoryManagement/datagrid")
	public DataGrid datagridDeviceFactoryManagement(PageHelper page,
			HttpServletRequest request) {
		String start_time = StringUtils.isBlank(request
				.getParameter("search_date")) ? "" : request.getParameter(
				"search_date").split(" ")[0];
		String manufacturer_no = request.getParameter("search_val_1");
		String manufacturer_name = request.getParameter("search_val_2");
		DataGrid dg = new DataGrid();
		int totalSize = sysService.getDeviceFactoryManagementTotal(start_time,
				manufacturer_no, manufacturer_name);
		dg.setTotal(Long.valueOf(totalSize));
		List<INF_MANUFACTURER> tempList = sysService
				.getDeviceFactoryManagement(page, start_time, manufacturer_no,
						manufacturer_name);
		List<INF_MANUFACTURER_VO> voList = new ArrayList<INF_MANUFACTURER_VO>();
		for (int i = 0; i < tempList.size(); i++) {
			INF_MANUFACTURER_VO v = new INF_MANUFACTURER_VO();
			voList.add(v);
		}
		SharpUtil.formatObjectForPage(tempList, voList);
		dg.setRows(voList);
		return dg;
	}

	@ResponseBody
	@RequestMapping(value = "/deviceFactoryManagement/add")
	public Json addDeviceFactoryManagement(INF_MANUFACTURER_VO manufacturerVO) {
		Json j = new Json();
		List<INF_MANUFACTURER_VO> voList = new ArrayList<INF_MANUFACTURER_VO>();
		voList.add(manufacturerVO);
		List<INF_MANUFACTURER> pojoList = new ArrayList<INF_MANUFACTURER>();
		INF_MANUFACTURER pojo = new INF_MANUFACTURER();
		pojoList.add(pojo);
		SharpUtil.formatPageObjectToPojo(voList, pojoList);
		if (sysService.addManufacturer(pojoList.get(0))) {
			j.setSuccess(true);
			j.setMsg("增加设备厂家成功!");
		} else {
			j.setSuccess(false);
			j.setMsg("增加设备厂家失败!");
		}
		return j;
	}

	@ResponseBody
	@RequestMapping(value = "/deviceFactoryManagement/update")
	public Json updateDeviceFactoryManagement(INF_MANUFACTURER_VO manufacturerVO) {
		Json j = new Json();
		List<INF_MANUFACTURER_VO> voList = new ArrayList<INF_MANUFACTURER_VO>();
		voList.add(manufacturerVO);
		List<INF_MANUFACTURER> pojoList = new ArrayList<INF_MANUFACTURER>();
		INF_MANUFACTURER pojo = new INF_MANUFACTURER();
		pojoList.add(pojo);
		SharpUtil.formatPageObjectToPojo(voList, pojoList);
		if (sysService.updateManufacturer(pojoList.get(0))) {
			j.setSuccess(true);
			j.setMsg("更新任务配置成功!");
		} else {
			j.setSuccess(false);
			j.setMsg("更新任务配置失败!");
		}
		return j;
	}

	@ResponseBody
	@RequestMapping(value = "/deviceFactoryManagement/delete")
	public Json deleteDeviceFactoryManagement(HttpServletRequest req) {
		Json j = new Json();
		String delIdArrayStr = req.getParameter("delIdArray");
		if (delIdArrayStr != null) {
			String delIdArray[] = delIdArrayStr.split(",");
			if (delIdArray.length > 0 && sysService.delManufacturer(delIdArray)) {
				j.setSuccess(true);
				j.setMsg("删除任务配置成功!");
				return j;
			}
		}
		j.setSuccess(false);
		j.setMsg("删除任务配置失败!");
		return j;
	}

	// ---------------------------------设备在线率
	@RequestMapping(value = "/deviceOnlineRateManagement")
	public String deviceOnlineRateManagement() {
		return "deviceOnlineRateManagement";
	}

	@ResponseBody
	@RequestMapping(value = "/deviceOnlineRateManagement/data")
	public List<INF_ONLLIERATE> chartOnline(HttpServletRequest request) {
		String beginTime = request.getParameter("begin");
		String endTime = request.getParameter("endTime");
		List<INF_ONLLIERATE> o = sysService.getOnlineRate(beginTime, endTime);
		return o;
	}

	@ResponseBody
	@RequestMapping(value = "/deviceOnlineRateManagement/datagrid")
	public DataGrid datagridDeviceOnlineRateManagement(PageHelper page,
			HttpServletRequest request) {
		String beginTime = request.getParameter("begin");
		String endTime = request.getParameter("endTime");
		DataGrid dg = new DataGrid();
		List<INF_ONLLIERATE> tempList = sysService.getOnlineRate(beginTime,
				endTime);
		dg.setTotal(Long.valueOf(tempList.size()));
		List<INF_ONLLIERATE_VO> voList = new ArrayList<INF_ONLLIERATE_VO>();
		for (int i = 0; i < tempList.size(); i++) {
			INF_ONLLIERATE_VO v = new INF_ONLLIERATE_VO();
			voList.add(v);
		}
		SharpUtil.formatObjectForPage(tempList, voList);
		dg.setRows(voList);
		return dg;
	}

	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = "/deviceOnlineRateManagement/add") public Json
	 * addDeviceOnlineRateManagement(INF_TERMINAL_ONLINE_RATE_VO VO){ Json j =
	 * new Json(); System.out.println(JSONArray.fromObject(VO));
	 * List<INF_TERMINAL_ONLINE_RATE_VO> voList = new
	 * ArrayList<INF_TERMINAL_ONLINE_RATE_VO>(); voList.add(VO);
	 * List<INF_TERMINAL_ONLINE_RATE> pojoList = new
	 * ArrayList<INF_TERMINAL_ONLINE_RATE>(); INF_TERMINAL_ONLINE_RATE pojo =
	 * new INF_TERMINAL_ONLINE_RATE(); pojoList.add(pojo);
	 * SharpUtil.formatPageObjectToPojo(voList,pojoList);
	 * if(sysService.addTerminaLonlineRate(pojoList.get(0))){
	 * j.setSuccess(true); j.setMsg("增加设备成功!"); }else{ j.setSuccess(false);
	 * j.setMsg("增加设备失败!"); } return j; }
	 */

	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = "/deviceOnlineRateManagement/update") public Json
	 * updateDeviceOnlineRateManagement(INF_TERMINAL_ONLINE_RATE_VO VO){ Json j
	 * = new Json(); List<INF_TERMINAL_ONLINE_RATE_VO> voList = new
	 * ArrayList<INF_TERMINAL_ONLINE_RATE_VO>(); voList.add(VO);
	 * List<INF_TERMINAL_ONLINE_RATE> pojoList = new
	 * ArrayList<INF_TERMINAL_ONLINE_RATE>(); INF_TERMINAL_ONLINE_RATE pojo =
	 * new INF_TERMINAL_ONLINE_RATE(); pojoList.add(pojo);
	 * SharpUtil.formatPageObjectToPojo(voList,pojoList);
	 * if(sysService.updateTerminaLonlineRate(pojoList.get(0))){
	 * j.setSuccess(true); j.setMsg("更新设备配置成功!"); }else{ j.setSuccess(false);
	 * j.setMsg("更新设备配置失败!"); } return j; }
	 */
	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = "/deviceOnlineRateManagement/delete") public Json
	 * deleteDeviceOnlineRateManagement(HttpServletRequest req){ Json j = new
	 * Json(); String delIdArrayStr = req.getParameter("delIdArray");
	 * if(delIdArrayStr != null){ String delIdArray[] =
	 * delIdArrayStr.split(","); if(delIdArray.length > 0 &&
	 * sysService.delTerminaLonlineRate(delIdArray)){ j.setSuccess(true);
	 * j.setMsg("删除设备信息成功!"); return j; } } j.setSuccess(false);
	 * j.setMsg("删除设备信息失败!"); return j; }
	 */

	@ResponseBody
	@RequestMapping(value = "/getWebStationNetParam")
	public String getWebStationNetParam(HttpServletRequest req) {
		// String sendFrameStr =
		// "{\"frontPCNo\":\"1\",\"reqOrRespNo\":\"1001\",\"dataType\":\"0A\",\"dataList\":[{\"frameId\":\"22\",\"tmUUID\":\"{aec70cc2-0cf1-4eda-be57-caa661b33d08}\",\"proto\":\"3-1\",\"rtua\":\"440116000001\",\"getTime\":\"20160824164350\",\"fn\":\"2\",\"pn\":\"0\",\"itemList\":[{\"no\":\"01\",\"val\":\"11\"}]}]}";
		// JSONObject obj = JSONObject.fromObject(sendFrameStr);
		// Map<String,Class> classMap = new HashMap<String,Class>();
		// classMap.put("dataList",TmFrame.class);
		// classMap.put("itemList",DataItem.class);
		// Frame frame = (Frame)JSONObject.toBean(obj,Frame.class,classMap);

		String idArrayStr = req.getParameter("id");
		if (idArrayStr != null) {
			String idArray[] = idArrayStr.split(",");
			if (idArray.length == 0) {
				// System.out.println("前端传入参数有误!");
				return "test";
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String proto = "3-1";
			String dataType = "0A";
			String tmIdStr = idArray[0];
			List<TmFrame> dataList = new ArrayList<TmFrame>();
			TmFrame tm = new TmFrame("", "", proto, tmIdStr,
					sdf.format(Calendar.getInstance().getTime()), "2", "0",
					null);
			dataList.add(tm);
			Frame frame = new Frame();
			frame.setDataType(dataType);
			frame.setDataList(dataList);
			List<TmFrame> tmFrameList = frame.getDataList();
			if (tmFrameList.size() > 0) {
				TmFrame tmFrame = tmFrameList.get(0); // 暂时只支持单个终端下发
				Long tmId = Long.parseLong(tmFrame.getRtua());
				Communication gCommunicationInstance = (Communication) req
						.getServletContext().getAttribute(
								"gCommunicationInstance");
				if (gCommunicationInstance == null) {
					// System.out.println("StaticVariable.gCommunicationInstance为空!");
					return "test";
				}
				CommClassId cid = gCommunicationInstance.getCommClassId(tmId);
				if (cid != null) {
					frame.setFrontPCNo(cid.getFpcId() + "");
					frame.setReqOrRespNo(cid.getReqNo() + "");
					Communication.sendNoAndFrameMap.put(cid, frame);
				} else {
					// System.out.println("cid为空，当前没有包含"+tmId+"终端的前置机在线!");
					return "test";
				}
				long timeoutMills = StaticVariable.maxTimeoutSeconds * 1000;
				long maxWaitMills = Calendar.getInstance().getTimeInMillis()
						+ timeoutMills;
				Frame respFrame = null;
				while (null == (respFrame = gCommunicationInstance
						.getRespMsgFromFPC(cid.getFpcId(), cid.getReqNo()))) {
					long curMills = Calendar.getInstance().getTimeInMillis();
					if (curMills >= maxWaitMills) {
						break;
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if (respFrame != null) {
					String dir = "1"; // 1为前置机发过来的数据包
					List<TmFrame> respTmFrameList = respFrame.getDataList();
					for (int i = 0; i < respTmFrameList.size(); i++) {
						TmFrame tempTmFrame = respTmFrameList.get(i);
						String fn = "F" + tempTmFrame.getFn();
						List<NecessaryProtocolCfg> npcList = StaticVariable
								.getNecessaryProtocolCfgFromDbCfg(dir,
										respFrame.getDataType(), fn); // select
																		// *from
																		// di0di1config
																		// where
																		// dir=1
																		// and
																		// afn='0A'
																		// and
																		// fn='F2';
						// System.out.println("npcList.size():"+npcList.size());
						List<DataItem> tempDataItemList = tempTmFrame
								.getItemList();
						for (int j = 0; j < tempDataItemList.size(); j++) {
							DataItem di = tempDataItemList.get(j);
							String diId = di.getNo();
							String diName = StaticVariable.getDiNameByDiId(dir,
									diId, npcList);
							if (diName != null) {
								di.setName(diName);
							}
						}
					}
					JSONObject jObject = JSONObject.fromObject(respFrame);
					// System.out.println("getWebStationNetParam接收:"+jObject.toString());
					req.setAttribute("getWebStationNetParam",
							jObject.toString());
					return jObject.toString();
				} else {
					System.out.println("getWebStationNetParam等待前置机返回超时!");
				}
			}
		} else {
			System.out.println("getWebStationNetParam前端请求未带参数!");
		}
		return "test";
	}

	// -------------------------------------------
	@RequestMapping(value = "/frontMachine")
	public String frontMachine() {
		return "frontMachine";
	}

	@ResponseBody
	@RequestMapping(value = "/frontMachine/datagrid")
	public DataGrid datagridFrontMachine(PageHelper page,
			HttpServletRequest request) {
		String front_code = request.getParameter("search_val_1");
		String front_name = request.getParameter("search_val_2");
		DataGrid dg = new DataGrid();
		int totalSize = sysService.getFrontMachineTotal(front_code, front_name);
		dg.setTotal(Long.valueOf(totalSize));
		List<INF_FRONT_MACHINE> tempList = sysService.getFrontMachine(page,
				front_code, front_name);
		List<INF_FRONT_MACHINE_VO> voList = new ArrayList<INF_FRONT_MACHINE_VO>();
		for (int i = 0; i < tempList.size(); i++) {
			INF_FRONT_MACHINE_VO v = new INF_FRONT_MACHINE_VO();
			voList.add(v);
		}
		SharpUtil.formatObjectForPage(tempList, voList);
		dg.setRows(voList);
		return dg;
	}

	@ResponseBody
	@RequestMapping(value = "/frontMachine/add")
	public Json addFrontMachine(INF_FRONT_MACHINE_VO VO) {
		Json j = new Json();
		List<INF_FRONT_MACHINE_VO> voList = new ArrayList<INF_FRONT_MACHINE_VO>();
		voList.add(VO);
		List<INF_FRONT_MACHINE> pojoList = new ArrayList<INF_FRONT_MACHINE>();
		INF_FRONT_MACHINE pojo = new INF_FRONT_MACHINE();
		pojoList.add(pojo);
		SharpUtil.formatPageObjectToPojo(voList, pojoList);
		if (sysService.addFrontMachine(pojoList.get(0))) {
			j.setSuccess(true);
			j.setMsg("增加前置机成功!");
		} else {
			j.setSuccess(false);
			j.setMsg("增加前置机失败!");
		}
		return j;
	}

	@ResponseBody
	@RequestMapping(value = "/frontMachine/update")
	public Json updateFrontMachine(INF_FRONT_MACHINE_VO VO) {
		Json j = new Json();
		List<INF_FRONT_MACHINE_VO> voList = new ArrayList<INF_FRONT_MACHINE_VO>();
		voList.add(VO);
		List<INF_FRONT_MACHINE> pojoList = new ArrayList<INF_FRONT_MACHINE>();
		INF_FRONT_MACHINE pojo = new INF_FRONT_MACHINE();
		pojoList.add(pojo);
		SharpUtil.formatPageObjectToPojo(voList, pojoList);
		if (sysService.updateFrontMachine(pojoList.get(0))) {
			j.setSuccess(true);
			j.setMsg("更新前置机配置成功!");
		} else {
			j.setSuccess(false);
			j.setMsg("更新前置机配置失败!");
		}
		return j;
	}

	@ResponseBody
	@RequestMapping(value = "/frontMachine/delete")
	public Json deleteFrontMachine(HttpServletRequest req) {
		Json j = new Json();
		String delIdArrayStr = req.getParameter("delIdArray");
		if (delIdArrayStr != null) {
			String delIdArray[] = delIdArrayStr.split(",");
			if (delIdArray.length > 0
					&& sysService.deleteFrontMachine(delIdArray)) {
				j.setSuccess(true);
				j.setMsg("删除前置机信息成功!");
				return j;
			}
		}
		j.setSuccess(false);
		j.setMsg("删除前置机信息失败!");
		return j;
	}

	// ------------------------------------------------------------
	@RequestMapping(value = "/terminalAccount")
	public String terminalAccount() {
		return "terminalAccount";
	}

	// 获取所有的计量点
	@ResponseBody
	@RequestMapping(value = "/terminalAccount/allInfMp")
	public Map<String, List<INF_MP>> getAllInfMp() {
		Map<String, List<INF_MP>> retMap = new LinkedHashMap<String, List<INF_MP>>();
		try {
			retMap = sysService.getAllInfMp();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println("retMap.size():"+retMap.size());
		return retMap;
	}

	@ResponseBody
	@RequestMapping(value = "/terminalAccount/datagrid")
	public DataGrid datagridTerminalAccount(PageHelper page,
			HttpServletRequest request) {
		String terminal_ormeterid = request.getParameter("search_val_1");
		String equipment_name = request.getParameter("search_val_2");
		DataGrid dg = new DataGrid();
		int totalSize = sysService.getTerminalAccountTotal(terminal_ormeterid,
				equipment_name);
		dg.setTotal(Long.valueOf(totalSize));
		List<INF_TERMINAL_ACCOUNT> tempList = sysService.getTerminalAccount(
				page, terminal_ormeterid, equipment_name);
		List<INF_TERMINAL_ACCOUNT_VO> voList = new ArrayList<INF_TERMINAL_ACCOUNT_VO>();
		for (int i = 0; i < tempList.size(); i++) {
			INF_TERMINAL_ACCOUNT_VO v = new INF_TERMINAL_ACCOUNT_VO();
			voList.add(v);
		}
		SharpUtil.formatObjectForPage(tempList, voList);
		dg.setRows(voList);
		return dg;
	}

	@ResponseBody
	@RequestMapping(value = "/terminalAccount/add")
	public Json addTerminalAccount(INF_TERMINAL_ACCOUNT_VO VO) {
		Json j = new Json();
		List<INF_TERMINAL_ACCOUNT_VO> voList = new ArrayList<INF_TERMINAL_ACCOUNT_VO>();
		voList.add(VO);
		List<INF_TERMINAL_ACCOUNT> pojoList = new ArrayList<INF_TERMINAL_ACCOUNT>();
		INF_TERMINAL_ACCOUNT pojo = new INF_TERMINAL_ACCOUNT();
		pojoList.add(pojo);
		SharpUtil.formatPageObjectToPojo(voList, pojoList);
		if (sysService.addTerminalAccount(pojoList.get(0))) {
			j.setSuccess(true);
			j.setMsg("增加设备台账成功!");
		} else {
			j.setSuccess(false);
			j.setMsg("增加设备台账失败!");
		}
		return j;
	}

	@ResponseBody
	@RequestMapping(value = "/terminalAccount/update")
	public Json updateTerminalAccount(INF_TERMINAL_ACCOUNT_VO VO) {
		Json j = new Json();
		List<INF_TERMINAL_ACCOUNT_VO> voList = new ArrayList<INF_TERMINAL_ACCOUNT_VO>();
		voList.add(VO);
		List<INF_TERMINAL_ACCOUNT> pojoList = new ArrayList<INF_TERMINAL_ACCOUNT>();
		INF_TERMINAL_ACCOUNT pojo = new INF_TERMINAL_ACCOUNT();
		pojoList.add(pojo);
		SharpUtil.formatPageObjectToPojo(voList, pojoList);
		if (sysService.updateTerminalAccount(pojoList.get(0))) {
			j.setSuccess(true);
			j.setMsg("更新设备台账配置成功!");
		} else {
			j.setSuccess(false);
			j.setMsg("更新设备台账配置失败!");
		}
		return j;
	}

	@ResponseBody
	@RequestMapping(value = "/terminalAccount/delete")
	public Json deleteTerminalAccount(HttpServletRequest req) {
		Json j = new Json();
		String delIdArrayStr = req.getParameter("delIdArray");
		if (delIdArrayStr != null) {
			String delIdArray[] = delIdArrayStr.split(",");
			if (delIdArray.length > 0
					&& sysService.deleteTerminalAccount(delIdArray)) {
				j.setSuccess(true);
				j.setMsg("删除设备台账信息成功!");
				return j;
			}
		}
		j.setSuccess(false);
		j.setMsg("删除设备台账信息失败!");
		return j;
	}

	// -------------------------------------------变压器
	@RequestMapping(value = "/transformer")
	public String transformer(ModelMap map) {
		List<Map<String, Object>> queryEnterpriseinfo = sysService.queryEnterpriseinfo(null,"0",false);
		map.put("enterpriseInfo", com.alibaba.fastjson.JSONObject.toJSON(queryEnterpriseinfo));
		return "transformer";
	}

	@ResponseBody
	@RequestMapping(value = "/transformer/datagrid")
	public DataGrid datagridTransformer(PageHelper page,
			HttpServletRequest request) {
		String transformer_no = request.getParameter("search_val_1");
		String transformer_name = request.getParameter("search_val_2");
		String create_date = request.getParameter("search_date");
		DataGrid dg = new DataGrid();
		int totalSize = sysService.getTransformerTotal(transformer_no,
				transformer_name, create_date);
		dg.setTotal(Long.valueOf(totalSize));
		List<INF_TRANSFORMER> tempList = sysService.getTransformer(page,
				transformer_no, transformer_name, create_date);
		List<INF_TRANSFORMER_VO> voList = new ArrayList<INF_TRANSFORMER_VO>();
		for (int i = 0; i < tempList.size(); i++) {
			INF_TRANSFORMER_VO v = new INF_TRANSFORMER_VO();
			voList.add(v);
		}
		SharpUtil.formatObjectForPage(tempList, voList);
		dg.setRows(voList);
		return dg;
	}

	@ResponseBody
	@RequestMapping(value = "/transformer/add")
	public Json addTransformer(INF_TRANSFORMER_VO VO) {
		Json j = new Json();
		List<INF_TRANSFORMER_VO> voList = new ArrayList<INF_TRANSFORMER_VO>();
		voList.add(VO);
		List<INF_TRANSFORMER> pojoList = new ArrayList<INF_TRANSFORMER>();
		INF_TRANSFORMER pojo = new INF_TRANSFORMER();
		pojoList.add(pojo);
		SharpUtil.formatPageObjectToPojo(voList, pojoList);
		if (sysService.addTransformer(pojoList.get(0))) {
			j.setSuccess(true);
			j.setMsg("增加变压器成功!");
		} else {
			j.setSuccess(false);
			j.setMsg("增加变压器失败!");
		}
		return j;
	}

	@ResponseBody
	@RequestMapping(value = "/transformer/update")
	public Json updateTransformer(INF_TRANSFORMER_VO VO) {
		Json j = new Json();
		List<INF_TRANSFORMER_VO> voList = new ArrayList<INF_TRANSFORMER_VO>();
		voList.add(VO);
		List<INF_TRANSFORMER> pojoList = new ArrayList<INF_TRANSFORMER>();
		INF_TRANSFORMER pojo = new INF_TRANSFORMER();
		pojoList.add(pojo);
		SharpUtil.formatPageObjectToPojo(voList, pojoList);
		if (sysService.updateTransformer(pojoList.get(0))) {
			j.setSuccess(true);
			j.setMsg("更新变压器配置成功!");
		} else {
			j.setSuccess(false);
			j.setMsg("更新变压器配置失败!");
		}
		return j;
	}

	@ResponseBody
	@RequestMapping(value = "/transformer/delete")
	public Json deleteTransformer(HttpServletRequest req) {
		Json j = new Json();
		String delIdArrayStr = req.getParameter("delIdArray");
		// System.out.println(delIdArrayStr);
		if (delIdArrayStr != null) {
			String delIdArray[] = delIdArrayStr.split(",");
			if (delIdArray.length > 0
					&& sysService.deleteTransformer(delIdArray)) {
				j.setSuccess(true);
				j.setMsg("删除变压器信息成功!");
				return j;
			}
		}
		j.setSuccess(false);
		j.setMsg("删除变压器信息失败!");
		return j;
	}

	// 设备安装-投运-消退管理
	@RequestMapping(value = "/terminalInstallRunDropManagement")
	public String terminalInstallRunDropManagement() {
		return "terminalInstallRunDropManagement";
	}

	@ResponseBody
	@RequestMapping(value = "/terminalInstallRunDropManagement/datagrid")
	public DataGrid datagridterminalInstallRunDropManagement(PageHelper page,
			HttpServletRequest request) {
		String equipment_name = request.getParameter("search_val_1");
		/*
		 * String deviceRepairPreWarnManagement_name =
		 * request.getParameter("search_val_2"); String create_date =
		 * request.getParameter("search_date");
		 */
		DataGrid dg = new DataGrid();
		int totalSize = sysService
				.getTerminalInstallRunDropManagementTotal(equipment_name);
		dg.setTotal(Long.valueOf(totalSize));
		List<INF_TERMINAL_MANAGEMENT> tempList = sysService
				.getTerminalInstallRunDropManagement(page, equipment_name);
		List<INF_TERMINAL_MANAGEMENT_VO> voList = new ArrayList<INF_TERMINAL_MANAGEMENT_VO>();
		for (int i = 0; i < tempList.size(); i++) {
			INF_TERMINAL_MANAGEMENT_VO v = new INF_TERMINAL_MANAGEMENT_VO();
			voList.add(v);
		}
		SharpUtil.formatObjectForPage(tempList, voList);
		dg.setRows(voList);
		return dg;
	}

	@ResponseBody
	@RequestMapping(value = "/terminalInstallRunDropManagement/add")
	public Json addterminalInstallRunDropManagement(
			INF_TERMINAL_MANAGEMENT_VO VO) {
		Json j = new Json();
		List<INF_TERMINAL_MANAGEMENT_VO> voList = new ArrayList<INF_TERMINAL_MANAGEMENT_VO>();
		voList.add(VO);
		List<INF_TERMINAL_MANAGEMENT> pojoList = new ArrayList<INF_TERMINAL_MANAGEMENT>();
		INF_TERMINAL_MANAGEMENT pojo = new INF_TERMINAL_MANAGEMENT();
		pojoList.add(pojo);
		SharpUtil.formatPageObjectToPojo(voList, pojoList);
		if (sysService.addTerminalInstallRunDropManagement(pojoList.get(0))) {
			j.setSuccess(true);
			j.setMsg("增加设备安装管理记录成功!");
		} else {
			j.setSuccess(false);
			j.setMsg("增加设备安装管理记录失败!");
		}
		return j;
	}

	@ResponseBody
	@RequestMapping(value = "/terminalInstallRunDropManagement/update")
	public Json updateTerminalInstallRunDropManagement(
			INF_TERMINAL_MANAGEMENT_VO VO) {
		Json j = new Json();
		List<INF_TERMINAL_MANAGEMENT_VO> voList = new ArrayList<INF_TERMINAL_MANAGEMENT_VO>();
		voList.add(VO);
		List<INF_TERMINAL_MANAGEMENT> pojoList = new ArrayList<INF_TERMINAL_MANAGEMENT>();
		INF_TERMINAL_MANAGEMENT pojo = new INF_TERMINAL_MANAGEMENT();
		pojoList.add(pojo);
		SharpUtil.formatPageObjectToPojo(voList, pojoList);
		if (sysService.updateTerminalInstallRunDropManagement(pojoList.get(0))) {
			j.setSuccess(true);
			j.setMsg("更新设备安装管理记录配置成功!");
		} else {
			j.setSuccess(false);
			j.setMsg("更新设备安装管理记录配置失败!");
		}
		return j;
	}

	@ResponseBody
	@RequestMapping(value = "/terminalInstallRunDropManagement/delete")
	public Json deleteTerminalInstallRunDropManagement(HttpServletRequest req) {
		Json j = new Json();
		String delIdArrayStr = req.getParameter("delIdArray");
		// System.out.println(delIdArrayStr);
		if (delIdArrayStr != null) {
			String delIdArray[] = delIdArrayStr.split(",");
			if (delIdArray.length > 0
					&& sysService
							.deleteTerminalInstallRunDropManagement(delIdArray)) {
				j.setSuccess(true);
				j.setMsg("删除设备安装管理记录信息成功!");
				return j;
			}
		}
		j.setSuccess(false);
		j.setMsg("删除设备安装管理记录信息失败!");
		return j;
	}

	// 设备维保管理
	@RequestMapping(value = "/deviceRepairManagement")
	public String deviceRepairManagement() {
		return "deviceRepairManagement";
	}

	@ResponseBody
	@RequestMapping(value = "/deviceRepairManagement/datagrid")
	public DataGrid datagridDeviceRepairManagement(PageHelper page,
			HttpServletRequest request) {
		String equipment_name = request.getParameter("search_val_1");
		DataGrid dg = new DataGrid();
		int totalSize = sysService
				.getDeviceRepairManagementTotal(equipment_name);
		dg.setTotal(Long.valueOf(totalSize));
		List<IF_INF_MAINTAIN> tempList = sysService.getDeviceRepairManagement(
				page, equipment_name);
		List<IF_INF_MAINTAIN_VO> voList = new ArrayList<IF_INF_MAINTAIN_VO>();
		for (int i = 0; i < tempList.size(); i++) {
			IF_INF_MAINTAIN_VO v = new IF_INF_MAINTAIN_VO();
			voList.add(v);
		}
		SharpUtil.formatObjectForPage(tempList, voList);
		dg.setRows(voList);
		return dg;
	}

	@ResponseBody
	@RequestMapping(value = "/deviceRepairManagement/add")
	public Json addDeviceRepairManagement(IF_INF_MAINTAIN_VO VO) {
		Json j = new Json();
		List<IF_INF_MAINTAIN_VO> voList = new ArrayList<IF_INF_MAINTAIN_VO>();
		voList.add(VO);
		List<IF_INF_MAINTAIN> pojoList = new ArrayList<IF_INF_MAINTAIN>();
		IF_INF_MAINTAIN pojo = new IF_INF_MAINTAIN();
		pojoList.add(pojo);
		SharpUtil.formatPageObjectToPojo(voList, pojoList);
		if (sysService.addDeviceRepairManagement(pojoList.get(0))) {
			j.setSuccess(true);
			j.setMsg("增加设备维保记录成功!");
		} else {
			j.setSuccess(false);
			j.setMsg("增加设备维保记录失败!");
		}
		return j;
	}

	@ResponseBody
	@RequestMapping(value = "/deviceRepairManagement/update")
	public Json updateDeviceRepairManagement(IF_INF_MAINTAIN_VO VO) {
		Json j = new Json();
		List<IF_INF_MAINTAIN_VO> voList = new ArrayList<IF_INF_MAINTAIN_VO>();
		voList.add(VO);
		List<IF_INF_MAINTAIN> pojoList = new ArrayList<IF_INF_MAINTAIN>();
		IF_INF_MAINTAIN pojo = new IF_INF_MAINTAIN();
		pojoList.add(pojo);
		SharpUtil.formatPageObjectToPojo(voList, pojoList);
		if (sysService.updateDeviceRepairManagement(pojoList.get(0))) {
			j.setSuccess(true);
			j.setMsg("更新设备维保记录配置成功!");
		} else {
			j.setSuccess(false);
			j.setMsg("更新设备维保记录配置失败!");
		}
		return j;
	}

	@ResponseBody
	@RequestMapping(value = "/deviceRepairManagement/delete")
	public Json deleteDeviceRepairManagement(HttpServletRequest req) {
		Json j = new Json();
		String delIdArrayStr = req.getParameter("delIdArray");
		// System.out.println(delIdArrayStr);
		if (delIdArrayStr != null) {
			String delIdArray[] = delIdArrayStr.split(",");
			if (delIdArray.length > 0
					&& sysService.deleteDeviceRepairManagement(delIdArray)) {
				j.setSuccess(true);
				j.setMsg("删除设备维保记录信息成功!");
				return j;
			}
		}
		j.setSuccess(false);
		j.setMsg("删除设备维保记录信息失败!");
		return j;
	}

	// 设备维保预警管理CRUD
	@RequestMapping(value = "/deviceRepairPreWarnManagement")
	public String deviceRepairPreWarnManagement() {
		return "deviceRepairPreWarnManagement";
	}

	@ResponseBody
	@RequestMapping(value = "/deviceRepairPreWarnManagement/datagrid")
	public DataGrid datagridDeviceRepairPreWarnManagement(PageHelper page,
			HttpServletRequest request) {
		String equipment_name = request.getParameter("search_val_1");
		DataGrid dg = new DataGrid();
		int totalSize = sysService
				.getDeviceRepairPreWarnManagementTotal(equipment_name);
		dg.setTotal(Long.valueOf(totalSize));
		List<INF_ALARM_RECORD> tempList = sysService
				.getDeviceRepairPreWarnManagement(page, equipment_name);
		List<INF_ALARM_RECORD_VO> voList = new ArrayList<INF_ALARM_RECORD_VO>();
		for (int i = 0; i < tempList.size(); i++) {
			INF_ALARM_RECORD_VO v = new INF_ALARM_RECORD_VO();
			voList.add(v);
		}
		SharpUtil.formatObjectForPage(tempList, voList);
		dg.setRows(voList);
		return dg;
	}

	@ResponseBody
	@RequestMapping(value = "/deviceRepairPreWarnManagement/add")
	public Json addDeviceRepairPreWarnManagement(INF_ALARM_RECORD_VO VO) {
		Json j = new Json();
		List<INF_ALARM_RECORD_VO> voList = new ArrayList<INF_ALARM_RECORD_VO>();
		voList.add(VO);
		List<INF_ALARM_RECORD> pojoList = new ArrayList<INF_ALARM_RECORD>();
		INF_ALARM_RECORD pojo = new INF_ALARM_RECORD();
		pojoList.add(pojo);
		SharpUtil.formatPageObjectToPojo(voList, pojoList);
		if (sysService.addDeviceRepairPreWarnManagement(pojoList.get(0))) {
			j.setSuccess(true);
			j.setMsg("增加设备维保预警记录成功!");
		} else {
			j.setSuccess(false);
			j.setMsg("增加设备维保预警记录失败!");
		}
		return j;
	}

	@ResponseBody
	@RequestMapping(value = "/deviceRepairPreWarnManagement/update")
	public Json updateDeviceRepairPreWarnManagement(INF_ALARM_RECORD_VO VO) {
		Json j = new Json();
		List<INF_ALARM_RECORD_VO> voList = new ArrayList<INF_ALARM_RECORD_VO>();
		voList.add(VO);
		List<INF_ALARM_RECORD> pojoList = new ArrayList<INF_ALARM_RECORD>();
		INF_ALARM_RECORD pojo = new INF_ALARM_RECORD();
		pojoList.add(pojo);
		SharpUtil.formatPageObjectToPojo(voList, pojoList);
		if (sysService.updateDeviceRepairPreWarnManagement(pojoList.get(0))) {
			j.setSuccess(true);
			j.setMsg("更新设备维保预警记录配置成功!");
		} else {
			j.setSuccess(false);
			j.setMsg("更新设备维保预警记录配置失败!");
		}
		return j;
	}

	@ResponseBody
	@RequestMapping(value = "/deviceRepairPreWarnManagement/delete")
	public Json deleteDeviceRepairPreWarnManagement(HttpServletRequest req) {
		Json j = new Json();
		String delIdArrayStr = req.getParameter("delIdArray");
		// System.out.println(delIdArrayStr);
		if (delIdArrayStr != null) {
			String delIdArray[] = delIdArrayStr.split(",");
			if (delIdArray.length > 0
					&& sysService
							.deleteDeviceRepairPreWarnManagement(delIdArray)) {
				j.setSuccess(true);
				j.setMsg("删除设备维保预警记录信息成功!");
				return j;
			}
		}
		j.setSuccess(false);
		j.setMsg("删除设备维保预警记录信息失败!");
		return j;
	}

	// 厂家设备排名
	@RequestMapping(value = "/deviceStateRanking")
	public String deviceStateRanking() {
		return "deviceStateRanking";
	}

	@RequestMapping(value = "/deviceStateRanking/datagrid")
	@ResponseBody
	public DataGrid getDeviceStateRanking(HttpServletRequest request) {
		String begin = request.getParameter("begin");
		String end = request.getParameter("endTime");
		List<DEVICESTATERANK> tempList = sysService.getDeviceRank(begin, end);
		DataGrid dg = new DataGrid();
		int totalSize = tempList.size();
		dg.setTotal(Long.valueOf(totalSize));
		List<DEVICESTATERANK_VO> voList = new ArrayList<DEVICESTATERANK_VO>();
		for (int i = 0; i < totalSize; i++) {
			DEVICESTATERANK_VO v = new DEVICESTATERANK_VO();
			voList.add(v);
		}
		SharpUtil.formatObjectForPage(tempList, voList);
		dg.setRows(voList);
		return dg;
	}

	@RequestMapping(value = "/remoteTermFactFrameSendAndRecv")
	public String remoteTermFactFrameSendAndRecv() {
		return "remoteTermFactFrameSendAndRecv";
	}

	@ResponseBody
	@RequestMapping(value = "/taskConfig/sendTaskConfigFrameToRemoteTerm")
	public String sendTaskConfigFrameToRemoteTerm(HttpServletRequest request) {
		String terminalIdSelArrayStr = request
				.getParameter("terminalIdSelArrayStr");
		String term_proCode = "0"; // request.getParameter("term_proCode");
		String meterNo = request.getParameter("meterNo");
		String taskNo = request.getParameter("taskNo");
		String taskType = request.getParameter("taskType");
		String gatherStandardTime = request.getParameter("gatherStandardTime");
		String gatherStandardTimeUnit = request
				.getParameter("gatherStandardTimeUnit");
		String gatherInterval = request.getParameter("gatherInterval");
		String reportStandardTime = request.getParameter("reportStandardTime");
		String reportStandardTimeUnit = request
				.getParameter("reportStandardTimeUnit");
		String reportInterval = request.getParameter("reportInterval");
		String dataItemListStr = request.getParameter("dataItemListStr");
		// System.out.println("terminalId:"+terminalId);
		// System.out.println("dataItemListStr:"+dataItemListStr);
		if (terminalIdSelArrayStr == null || term_proCode == null
				|| meterNo == null || taskNo == null || taskType == null
				|| gatherStandardTime == null || gatherStandardTimeUnit == null
				|| gatherInterval == null || reportStandardTime == null
				|| reportStandardTimeUnit == null || reportInterval == null
				|| dataItemListStr == null) {
			System.out
					.println("/taskConfig/sendTaskConfigFrameToRemoteTerm请求参数不全!");
			return "taskConfig";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String dataType = "04"; // 协议文档中定义04为前置机下发参数配置
		String fn = "1";
		String pn = "0";
		String terminalIdSelArray[] = terminalIdSelArrayStr.split(",");
		Frame frame = new Frame();
		frame.setDataType(dataType);
		List<TmFrame> dataList = new ArrayList<TmFrame>();
		for (int i = 0; i < terminalIdSelArray.length; i++) {
			String terminalId = terminalIdSelArray[i];
			TmFrame tm = new TmFrame("", "", term_proCode, terminalId,
					sdf.format(Calendar.getInstance().getTime()), fn, pn, null);
			List<DataItem> itemList = new ArrayList<DataItem>();
			DataItem i1 = new DataItem("040100", terminalId);
			DataItem i2 = new DataItem("040101", meterNo);
			DataItem i3 = new DataItem("040102", taskNo);
			DataItem i4 = new DataItem("040103", taskType);
			DataItem i5 = new DataItem("040104", gatherStandardTime);
			DataItem i6 = new DataItem("040105", gatherStandardTimeUnit);
			DataItem i7 = new DataItem("040106", gatherInterval);
			DataItem i8 = new DataItem("040107", reportStandardTime);
			DataItem i9 = new DataItem("040108", reportStandardTimeUnit);
			DataItem i10 = new DataItem("040109", reportInterval);
			DataItem i11 = new DataItem("04010A", dataItemListStr);
			itemList.add(i1);
			itemList.add(i2);
			itemList.add(i3);
			itemList.add(i4);
			itemList.add(i5);
			itemList.add(i6);
			itemList.add(i7);
			itemList.add(i8);
			itemList.add(i9);
			itemList.add(i10);
			itemList.add(i11);
			tm.setItemList(itemList);
			dataList.add(tm);
		}
		frame.setDataList(dataList);
		List<TmFrame> tmFrameList = frame.getDataList();
		if (tmFrameList.size() > 0) {
			TmFrame tmFrame = tmFrameList.get(0); // 暂时只支持单个终端下发
			Long tmId = Long.parseLong(tmFrame.getRtua());
			Communication gCommunicationInstance = (Communication) request
					.getServletContext().getAttribute("gCommunicationInstance");
			if (gCommunicationInstance == null) {
				System.out.println("StaticVariable.gCommunicationInstance为空!");
				return "taskConfig";
			}
			CommClassId cid = gCommunicationInstance.getCommClassId(tmId);
			if (cid != null) {
				frame.setFrontPCNo(cid.getFpcId() + "");
				frame.setReqOrRespNo(cid.getReqNo() + "");
				Communication.sendNoAndFrameMap.put(cid, frame);
			} else {
				System.out.println("cid为空，当前没有包含" + tmId + "终端的前置机在线!");
				return "taskConfig";
			}
			long timeoutMills = StaticVariable.maxTimeoutSeconds * 1000;
			long maxWaitMills = Calendar.getInstance().getTimeInMillis()
					+ timeoutMills;
			Frame respFrame = null;
			while (null == (respFrame = gCommunicationInstance
					.getRespMsgFromFPC(cid.getFpcId(), cid.getReqNo()))) {
				long curMills = Calendar.getInstance().getTimeInMillis();
				if (curMills >= maxWaitMills) {
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (respFrame != null) {
				String dir = "1"; // 1为前置机发过来的数据包
				List<TmFrame> respTmFrameList = respFrame.getDataList();
				for (int i = 0; i < respTmFrameList.size(); i++) {
					TmFrame tempTmFrame = respTmFrameList.get(i);
					String tempFn = "F" + tempTmFrame.getFn();
					List<NecessaryProtocolCfg> npcList = StaticVariable
							.getNecessaryProtocolCfgFromDbCfg(dir,
									respFrame.getDataType(), tempFn); // select
																		// *from
																		// di0di1config
																		// where
																		// dir=1
																		// and
																		// afn='00'
																		// and
																		// fn='F1';
					// System.out.println("npcList.size():"+npcList.size());
					List<DataItem> tempDataItemList = tempTmFrame.getItemList();
					for (int j = 0; j < tempDataItemList.size(); j++) {
						DataItem di = tempDataItemList.get(j);
						String diId = di.getNo();
						String diName = StaticVariable.getDiNameByDiId(dir,
								diId, npcList);
						if (diName != null) {
							di.setName(diName);
						}
					}
				}
				JSONObject jObject = JSONObject.fromObject(respFrame);
				System.out.println("sendTaskConfigFrameToRemoteTerm接收:"
						+ jObject.toString());
				request.setAttribute("sendTaskConfigFrameToRemoteTerm",
						jObject.toString());
				return jObject.toString();
			} else {
				System.out.println("sendTaskConfigFrameToRemoteTerm等待前置机返回超时!");
			}
		}
		return "taskConfig";
	}

	@ResponseBody
	@RequestMapping(value = "/remoteTermFactFrameSendAndRecv/sendHexFrame")
	public String sendHexFrameToRemoteTerm(HttpServletRequest request) {
		String terminalId = request.getParameter("terminalId");
		String term_proCode = request.getParameter("term_proCode");
		String sendHexFrame = request.getParameter("sendHexFrame");
		// System.out.println("terminalId:"+terminalId);
		// System.out.println("sendHexFrame:"+sendHexFrame);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String proto = term_proCode;
		String dataType = "10"; // 协议文档中定义10为前置机透明转发
		String fn = "1";
		String pn = "0";
		List<TmFrame> dataList = new ArrayList<TmFrame>();
		TmFrame tm = new TmFrame("", "", proto, terminalId, sdf.format(Calendar
				.getInstance().getTime()), fn, pn, null);
		List<DataItem> itemList = new ArrayList<DataItem>();
		DataItem i1 = new DataItem("100100", String.valueOf(sendHexFrame
				.length() / 2)); // 透明转发字节数
		DataItem i2 = new DataItem("100101", sendHexFrame); // 透明转发内容
		itemList.add(i1);
		itemList.add(i2);
		tm.setItemList(itemList);
		dataList.add(tm);
		Frame frame = new Frame();
		frame.setDataType(dataType);
		frame.setDataList(dataList);
		// JSONObject jObjectFrame = JSONObject.fromObject(frame);
		// String frameStr = jObjectFrame.toString();
		// System.out.println("sendHexFrameToRemoteTerm:"+frameStr);
		List<TmFrame> tmFrameList = frame.getDataList();
		if (tmFrameList.size() > 0) {
			TmFrame tmFrame = tmFrameList.get(0); // 暂时只支持单个终端下发
			Long tmId = Long.parseLong(tmFrame.getRtua());
			Communication gCommunicationInstance = (Communication) request
					.getServletContext().getAttribute("gCommunicationInstance");
			if (gCommunicationInstance == null) {
				System.out.println("StaticVariable.gCommunicationInstance为空!");
				return "remoteTermFactFrameSendAndRecv";
			}
			CommClassId cid = gCommunicationInstance.getCommClassId(tmId);
			if (cid != null) {
				frame.setFrontPCNo(cid.getFpcId() + "");
				frame.setReqOrRespNo(cid.getReqNo() + "");
				Communication.sendNoAndFrameMap.put(cid, frame);
			} else {
				System.out.println("cid为空，当前没有包含" + tmId + "终端的前置机在线!");
				return "remoteTermFactFrameSendAndRecv";
			}
			long timeoutMills = StaticVariable.maxTimeoutSeconds * 1000;
			long maxWaitMills = Calendar.getInstance().getTimeInMillis()
					+ timeoutMills;
			Frame respFrame = null;
			while (null == (respFrame = gCommunicationInstance
					.getRespMsgFromFPC(cid.getFpcId(), cid.getReqNo()))) {
				long curMills = Calendar.getInstance().getTimeInMillis();
				if (curMills >= maxWaitMills) {
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (respFrame != null) {
				String dir = "1"; // 1为前置机发过来的数据包
				List<TmFrame> respTmFrameList = respFrame.getDataList();
				for (int i = 0; i < respTmFrameList.size(); i++) {
					TmFrame tempTmFrame = respTmFrameList.get(i);
					String tempFn = "F" + tempTmFrame.getFn();
					List<NecessaryProtocolCfg> npcList = StaticVariable
							.getNecessaryProtocolCfgFromDbCfg(dir,
									respFrame.getDataType(), tempFn); // select
																		// *from
																		// di0di1config
																		// where
																		// dir=1
																		// and
																		// afn='10'
																		// and
																		// fn='F1';
					// System.out.println("npcList.size():"+npcList.size());
					List<DataItem> tempDataItemList = tempTmFrame.getItemList();
					for (int j = 0; j < tempDataItemList.size(); j++) {
						DataItem di = tempDataItemList.get(j);
						String diId = di.getNo();
						String diName = StaticVariable.getDiNameByDiId(dir,
								diId, npcList);
						if (diName != null) {
							di.setName(diName);
						}
					}
				}
				JSONObject jObject = JSONObject.fromObject(respFrame);
				System.out.println("sendHexFrameToRemoteTerm接收:"
						+ jObject.toString());
				request.setAttribute("sendHexFrameToRemoteTerm",
						jObject.toString());
				return jObject.toString();
			} else {
				System.out.println("sendHexFrameToRemoteTerm等待前置机返回超时!");
			}
		}
		return "remoteTermFactFrameSendAndRecv";
	}

	// excel文件内容存数据库
	@RequestMapping(value = "/excelImportDocInfo")
	public String excelImportDocInfo() {
		return "excelImportDocInfo";
	}

	@RequestMapping(value = "/excelImportDocInfo/data")
	@ResponseBody
	public Json excelDocInfo(HttpServletRequest request) {
		Json j = new Json();
		String id = request.getParameter("id");
		String tableData = request.getParameter("tableData");
		String rows = request.getParameter("rowCount");
		boolean result = false;
		if (id.equalsIgnoreCase("updateMeter")) {
			result = sysService.addExcelDataMeter(tableData, rows);
			if (!result) {
				j.setMsg("更新的数据存在问题，数据之间存在冲突");
				j.setSuccess(result);
				return j;
			}
		} else if (id.equalsIgnoreCase("updateTerminal")) {
			result = sysService.addExcelDataTerminal(tableData, rows);
			if (!result) {
				j.setMsg("更新的数据存在问题，数据之间存在冲突");
				j.setSuccess(result);
				return j;
			}
		}
		j.setSuccess(result);
		j.setMsg("数据更新成功");
		return j;
	}

	// excel导入导出
	@ResponseBody
	@RequestMapping(value = "/excelImportDocInfo/getAllTerm")
	public List<INF_TERM_VO> getAllTermAndMp(HttpServletRequest request) {
		List<INF_TERM> allTerm = sysService.getAllTerm();
		List<INF_TERM_VO> allVO = new ArrayList<INF_TERM_VO>();
		for (int i = 0; i < allTerm.size(); i++) {
			INF_TERM_VO vo = new INF_TERM_VO();
			allVO.add(vo);
		}
		SharpUtil.formatObjectForPage(allTerm, allVO);
		return allVO;
	}

	@ResponseBody
	@RequestMapping(value = "/excelImportDocInfo/getAllMp")
	public List<INF_MP_VO> getAllMp(HttpServletRequest request) {
		List<INF_MP> allMp = sysService.getAllMps();
		List<INF_MP_VO> allVO = new ArrayList<INF_MP_VO>();
		for (int i = 0; i < allMp.size(); i++) {
			INF_MP_VO vo = new INF_MP_VO();
			allVO.add(vo);
		}
		SharpUtil.formatObjectForPage(allMp, allVO);
		return allVO;
	}

	@ResponseBody
	@RequestMapping(value = "/excelImportDocInfo/saveReportData")
	public String exportNyxfjgb(HttpServletRequest request,
			HttpServletResponse response) {
		String tableData = request.getParameter("tableData");
		JSONArray array = JSONArray.fromObject(tableData);
		return sysService.saveReportDataInMemory(request, response, array);
	}

	/**
	 * 导出报表
	 */
	@RequestMapping(value = "/excelImportDocInfo/export")
	public void export(HttpServletRequest request, HttpServletResponse response) {
		try {
			String excel = request.getParameter("excel");
			if (StringUtils.isNotBlank(excel)) {
				sysService.exportExcel(request, response, excel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 返回tbl_biz_enterpriseinfo表id,name的集合
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/enterpriseinfo/info")
	public Map<String,Object> queryEnterpriseinfo(HttpServletRequest request) {
		try {
			String param = request.getParameter("name");
			String page = request.getParameter("page");
			if(StringUtils.isBlank(page)){
				page="0";
			}
			return sysService.queryEnterprise(param,page,true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 返回终端表的id,name的集合
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/term/info")
	public Map<String,Object> queryTermInfo(HttpServletRequest request) {
		try {
			String param = request.getParameter("name");
			String page = request.getParameter("page");
			if(StringUtils.isBlank(page)){
				page="0";
			}
			return sysService.queryTerm(param, page,true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 返回变压器表的Id,name集合
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/transformer/info")
	public Map<String,Object> queryTransformerInfo(HttpServletRequest request){
		try {
			String param = request.getParameter("name");
			String page = request.getParameter("page");
			if(StringUtils.isBlank(page)){
				page="0";
			}
			return sysService.queryTransformer(param,page,true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@RequestMapping("meter/exist")
	@ResponseBody
	public String queryMeterIsExist(HttpServletRequest request){
			String data = request.getParameter("data");
			return sysService.queryMeterIsExist(data);
	}
	
	
	
}
