package com.nk.service;

import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.SheetSettings;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nk.dao.ArchiveMapper;
import com.nk.model.easyui.PageHelper;
import com.nk.pojo.DEVICESTATERANK;
import com.nk.pojo.DI_DEF;
import com.nk.pojo.FrontAndTerm;
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
import com.nk.pojo.INF_TERMINAL_ONLINE_RATE;
import com.nk.pojo.INF_TRANSFORMER;
import com.nk.pojo.MeasureDevCustomer;
import com.nk.pojo.TASK_RECORD;
import com.nk.pojo.TASK_TEMPLATE;
import com.nk.pojo.TableColumnItem;
import com.nk.util.SharpUtil;
import com.nk.vo.INF_MP_VO;
import com.nk.vo.INF_TERM_VO;

@Service
public class SystemService{
	@Resource
	private ArchiveMapper userMapper;

	public int testMyBatis(){
		return userMapper.test();
	}

	public Map<String,List<INF_MP>> getAllInfMp(){
		List<INF_MP> allInfMp = userMapper.getAllInfMp();
		List<INF_MP> mp1 = new ArrayList<INF_MP>();
		List<INF_MP> mp2 = new ArrayList<INF_MP>();
		List<INF_MP> mp3 = new ArrayList<INF_MP>();
		List<INF_MP> mp4 = new ArrayList<INF_MP>();
		for(INF_MP m:allInfMp){
			String type = m.getMp_type();
			if(StringUtils.isBlank(type)){
				mp4.add(m);
			}else{
				if(type.equals("1")){
					mp1.add(m);
				}else if(type.equals("2")){
					mp2.add(m);
				}else if(type.equals("3")){
					mp3.add(m);
				}
			}
		}
		Map<String,List<INF_MP>> mps = new HashMap<String,List<INF_MP>>();
		mps.put("生产用电类型",mp1);
		mps.put("生产辅助用电类型",mp2);
		mps.put("办公生活用电类型",mp3);
		mps.put("其他用电类型",mp4);
		return mps;
	}

	public int getCustomerTotal(String c_date,String c_no,String c_man){
		Map<String,Object> param = new HashMap<>();
		param.put("c_date",c_date);
		param.put("c_no",c_no);
		param.put("c_man",c_man);
		return userMapper.getCustomerTotal(param);
	}

	public List<INF_CUSTOMER> getPageCustomer(PageHelper page,String c_date,String c_no,String c_man){
		page.setStart((page.getPage()-1)*page.getRows());
		page.setEnd(page.getRows()); // page.getPage()*page.getRows()
		Map<String,Object> param = new HashMap<>();
		param.put("c_date",c_date);
		param.put("c_no",c_no);
		param.put("c_man",c_man);
		param.put("order",page.getOrder());
		param.put("sort",page.getSort());
		param.put("start",page.getStart());
		param.put("end",page.getEnd());
		return userMapper.getPageCustomer(param);
	}

	public boolean addCustomer(INF_CUSTOMER customer){
		try{
			userMapper.addCustomer(customer);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean updateCustomer(INF_CUSTOMER customer){
		try{
			userMapper.updateCustomer(customer);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean delCustomer(String[] delCustomerIdArray){
		try{
			int delCustomerIdArrayInt[] = new int[delCustomerIdArray.length];
			for(int i = 0; i<delCustomerIdArray.length; i++){
				delCustomerIdArrayInt[i] = Integer.parseInt(delCustomerIdArray[i]);
			}
			userMapper.delCustomer(delCustomerIdArrayInt);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public int getTerminalTotal(String c_man,String ip,String c_date,String temp){
		Map<String,Object> param = new HashMap<>();
		param.put("create_man",c_man);
		param.put("ip",ip);
		param.put("create_date",c_date);
		param.put("temp",temp);
		return userMapper.getTerminalTotal(param);
	}

	/**
	 * 终端表用户下拉框
	 * 
	 * @return
	 */
	public List<INF_CUSTOMER> getCustomepList(){
		return userMapper.getCustomepList();
	}

	public List<INF_TERM> getPageTerminal(PageHelper page,String c_man,String ip,String c_date,String temp){
		page.setStart((page.getPage()-1)*page.getRows());
		page.setEnd(page.getRows()); // page.getPage()*page.getRows()
		Map<String,Object> param = new HashMap<>();
		param.put("create_man",c_man);
		param.put("ip",ip);
		param.put("create_date",c_date);
		param.put("temp",temp);
		param.put("order",page.getOrder());
		param.put("sort",page.getSort());
		param.put("start",page.getStart());
		param.put("end",page.getEnd());
		return userMapper.getPageTerminal(param);
	}

	public boolean addTerminal(INF_TERM infT){
		try{
			userMapper.addTerminal(infT);
			// 获取存储的记录
			List<INF_TERM> newT = userMapper.getTermByaddrAndCustId(infT.getTerm_addr(),infT.getCustomer_id());
			INF_TERM inf = newT.get(0);
			// 添加时同步添加到设备台账
			INF_TERMINAL_ACCOUNT account = new INF_TERMINAL_ACCOUNT();
			account.setTerminal_ormeterid(inf.getTerm_id().longValue());
			account.setEquipment_name(inf.getTerm_name());
			account.setRemarks(inf.getRemarks());
			account.setDev_type(2);
			addTerminalAccount(account);
			//userMapper.addTerminalAccount(account);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean updateTerminal(INF_TERM inf){
		try{
			userMapper.updateTerminal(inf);
			// 添加时同步添加到设备台账
			INF_TERMINAL_ACCOUNT account = new INF_TERMINAL_ACCOUNT();
			account.setTerminal_ormeterid(inf.getTerm_id().longValue());
			account.setEquipment_name(inf.getTerm_name());
			account.setRemarks(inf.getRemarks());
			userMapper.updateTerminalAccount(account);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean delTerminal(String[] delTerminalIdArray){
		try{
			int delTerminalIdArrayInt[] = new int[delTerminalIdArray.length];
			for(int i = 0; i<delTerminalIdArray.length; i++){
				delTerminalIdArrayInt[i] = Integer.parseInt(delTerminalIdArray[i]);
			}
			userMapper.delTerminal(delTerminalIdArrayInt);
			userMapper.deleteTerminalAccount(delTerminalIdArrayInt);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public int getMeterTotal(String mp_no,String mp_name,String create_man,String create_date){
		Map<String,Object> param = new HashMap<>();
		String times = null;
		param.put("mp_no",mp_no);
		param.put("mp_name",mp_name);
		param.put("create_man",create_man);
		if(!StringUtils.isBlank(create_date)&&create_date!=null){
			times = create_date.substring(0,10);
		}
		param.put("create_date",times);
		return userMapper.getMeterTotal(param);
	}

	public List<INF_MP> getPageMeter(PageHelper page,String mp_no,String mp_name,String create_man,String create_date){
		page.setStart((page.getPage()-1)*page.getRows());
		page.setEnd(page.getRows()); // page.getPage()*page.getRows()
		page.setSort("mp_id");
		Map<String,Object> param = new HashMap<>();
		String times = null;
		param.put("mp_no",mp_no);
		param.put("mp_name",mp_name);
		param.put("create_man",create_man);
		if(!StringUtils.isBlank(create_date)&&create_date!=null){
			times = create_date.substring(0,10);
		}
		param.put("create_date",times);
		param.put("order",page.getOrder());
		param.put("sort",page.getSort());
		param.put("start",page.getStart());
		param.put("end",page.getEnd());
		return userMapper.getPageMeter(param);
	}

	public boolean addMeter(INF_MP meter){
		try{
			userMapper.addMeter(meter);
			// 获取新增的计量点对象
			INF_MP inf = userMapper.getMeterBympNo(meter.getMp_no());
			// 同步添加到设备台账表
			INF_TERMINAL_ACCOUNT account = new INF_TERMINAL_ACCOUNT();
			account.setTerminal_ormeterid(inf.getMp_id());
			account.setEquipment_name(inf.getMp_name());
			account.setRated_current(inf.getRated_current());
			account.setRated_voltage(inf.getRated_voltage());
			account.setRated_power(inf.getRated_current()*inf.getRated_voltage());
			account.setRemarks(inf.getRemarks());
			account.setDev_type(3);
			addTerminalAccount(account);
			//userMapper.addTerminalAccount(account);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean updateMeter(INF_MP inf){
		try{
			userMapper.updateMeter(inf);
			// 同步添加到设备台账表
			INF_TERMINAL_ACCOUNT account = new INF_TERMINAL_ACCOUNT();
			account.setTerminal_ormeterid(inf.getMp_id());
			account.setEquipment_name(inf.getMp_name());
			account.setRated_current(inf.getRated_current());
			account.setRated_voltage(inf.getRated_voltage());
			account.setRated_power(inf.getRated_current()*inf.getRated_voltage());
			account.setRemarks(inf.getRemarks());
			userMapper.updateTerminalAccount(account);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean delMeter(String[] delMeterIdArray){
		try{
			int delMeterIdArrayInt[] = new int[delMeterIdArray.length];
			for(int i = 0; i<delMeterIdArray.length; i++){
				delMeterIdArrayInt[i] = Integer.parseInt(delMeterIdArray[i]);
			}
			userMapper.delMeter(delMeterIdArrayInt);
			userMapper.deleteTerminalAccount(delMeterIdArrayInt);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public int getDiDefTotal(String name){
		Map<String,Object> param = new HashMap<>();
		param.put("name",name);
		return userMapper.getDiDefTotal(param);
	}

	public List<DI_DEF> getPageDiDef(PageHelper page,String name){
		page.setStart((page.getPage()-1)*page.getRows());
		page.setEnd(page.getRows());
		Map<String,Object> param = new HashMap<>();
		param.put("name",name);
		param.put("order",page.getOrder());
		param.put("sort",page.getSort());
		param.put("start",page.getStart());
		param.put("end",page.getEnd());
		return userMapper.getPageDiDef(param);
	}

	public boolean addDiDef(DI_DEF diDef){
		try{
			userMapper.addDiDef(diDef);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean updateDiDef(DI_DEF diDef){
		try{
			userMapper.updateDiDef(diDef);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean delDiDef(String[] delDiDefIdArray){
		try{
			userMapper.delDiDef(delDiDefIdArray);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public int getTaskTemplateTotal(String name){
		Map<String,Object> param = new HashMap<>();
		param.put("name",name);
		return userMapper.getTaskTemplateTotal(param);
	}

	public List<TASK_TEMPLATE> getPageTaskTemplate(PageHelper page,String name){
		page.setStart((page.getPage()-1)*page.getRows());
		page.setEnd(page.getRows());
		Map<String,Object> param = new HashMap<>();
		param.put("name",name);
		param.put("order",page.getOrder());
		param.put("sort",page.getSort());
		param.put("start",page.getStart());
		param.put("end",page.getEnd());
		return userMapper.getPageTaskTemplate(param);
	}

	public boolean addTaskTemplate(TASK_TEMPLATE taskTemplate){
		try{
			userMapper.addTaskTemplate(taskTemplate);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean updateTaskTemplate(TASK_TEMPLATE taskTemplate){
		try{
			userMapper.updateTaskTemplate(taskTemplate);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean delTaskTemplate(String[] delTaskTemplateIdArray){
		try{
			int delTaskTemplateIdArrayInt[] = new int[delTaskTemplateIdArray.length];
			for(int i = 0; i<delTaskTemplateIdArray.length; i++){
				delTaskTemplateIdArrayInt[i] = Integer.parseInt(delTaskTemplateIdArray[i]);
			}
			userMapper.delTaskTemplate(delTaskTemplateIdArrayInt);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public int getTaskRecordTotal(String connNo,String jn){
		Map<String,Object> param = new HashMap<>();
		param.put("connNo",connNo);
		param.put("jn",jn);
		return userMapper.getTaskRecordTotal(param);
	}

	public List<TASK_RECORD> getPageTaskRecord(PageHelper page,String connNo,String jn){
		page.setStart((page.getPage()-1)*page.getRows());
		page.setEnd(page.getRows());
		Map<String,Object> param = new HashMap<>();
		param.put("connNo",connNo);
		param.put("jn",jn);
		param.put("order",page.getOrder());
		param.put("sort",page.getSort());
		param.put("start",page.getStart());
		param.put("end",page.getEnd());
		return userMapper.getPageTaskRecord(param);
	}

	public boolean addTaskRecord(TASK_RECORD taskRecord){
		try{
			userMapper.addTaskRecord(taskRecord);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean updateTaskRecord(TASK_RECORD taskRecord){
		try{
			userMapper.updateTaskRecord(taskRecord);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean delTaskRecord(String[] delTaskRecordIdArray){
		try{
			int delTaskRecordIdArrayInt[] = new int[delTaskRecordIdArray.length];
			for(int i = 0; i<delTaskRecordIdArray.length; i++){
				delTaskRecordIdArrayInt[i] = Integer.parseInt(delTaskRecordIdArray[i]);
			}
			userMapper.delTaskRecord(delTaskRecordIdArrayInt);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 获取设备在线率数据
	 */
	public int getDeviceFactoryManagementTotal(String start_time,String manufacturer_no,String manufacturer_name){
		Map<String,Object> param = new HashMap<>();
		param.put("start_time",start_time);
		param.put("manufacturer_no",manufacturer_no);
		param.put("manufacturer_name",manufacturer_name);
		return userMapper.getDeviceFactoryManagementTotal(param);
	}

	public List<INF_MANUFACTURER> getDeviceFactoryManagement(PageHelper page,String start_time,String manufacturer_no,String manufacturer_name){
		page.setStart((page.getPage()-1)*page.getRows());
		page.setEnd(page.getRows());
		Map<String,Object> param = new HashMap<>();
		param.put("start_time",start_time);
		param.put("manufacturer_no",manufacturer_no);
		param.put("manufacturer_name",manufacturer_name);
		param.put("order",page.getOrder());
		param.put("sort",page.getSort());
		param.put("start",page.getStart());
		param.put("end",page.getEnd());
		return userMapper.getDeviceFactoryManagement(param);
	}

	public boolean addManufacturer(INF_MANUFACTURER inf_manufacturer){
		try{
			userMapper.addManufacturer(inf_manufacturer);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean updateManufacturer(INF_MANUFACTURER inf_manufacturer){
		try{
			userMapper.updateManufacturer(inf_manufacturer);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean delManufacturer(String[] delIdArray){
		try{
			int delIdArrayInt[] = new int[delIdArray.length];
			for(int i = 0; i<delIdArray.length; i++){
				delIdArrayInt[i] = Integer.parseInt(delIdArray[i]);
			}
			userMapper.delManufacturer(delIdArrayInt);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public int getDeviceOnlineRateManagementTotal(String equipment_name,String terminal_ormeterid){
		Map<String,Object> param = new HashMap<>();
		param.put("equipment_name",equipment_name);
		param.put("terminal_ormeterid",terminal_ormeterid);
		return userMapper.getDeviceOnlineRateManagementTotal(param);
	}

	public List<INF_TERMINAL_ONLINE_RATE> getDeviceOnlineRateManagement(PageHelper page,String equipment_name,String terminal_ormeterid){
		page.setStart((page.getPage()-1)*page.getRows());
		page.setEnd(page.getRows());
		Map<String,Object> param = new HashMap<>();
		param.put("time",new Date());
		param.put("equipment_name",equipment_name);
		param.put("terminal_ormeterid",terminal_ormeterid);
		param.put("order",page.getOrder());
		param.put("sort",page.getSort());
		param.put("start",page.getStart());
		param.put("end",page.getEnd());
		return userMapper.getDeviceOnlineRateManagement(param);
	}

	public boolean addTerminaLonlineRate(INF_TERMINAL_ONLINE_RATE inf_terminal_online_rate){
		try{
			userMapper.addTerminaLonlineRate(inf_terminal_online_rate);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean updateTerminaLonlineRate(INF_TERMINAL_ONLINE_RATE inf_terminal_online_rate){
		try{
			userMapper.updateTerminaLonlineRate(inf_terminal_online_rate);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean delTerminaLonlineRate(String[] delIdArray){
		try{
			int delIdArrayInt[] = new int[delIdArray.length];
			for(int i = 0; i<delIdArray.length; i++){
				delIdArrayInt[i] = Integer.parseInt(delIdArray[i]);
			}
			userMapper.delTerminaLonlineRate(delIdArrayInt);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Map<String,List<DI_DEF>> autoDef(){
		// 返回所有的数据项名称和ID
		// 新建一个返回集合
		Map<String,List<DI_DEF>> map = new HashMap<>();
		List<DI_DEF> defs = userMapper.autoDef();
		for(DI_DEF def:defs){
			DI_DEF d = new DI_DEF();
			d.setDi_item_no(def.getDi_item_no());
			d.setDi_item_name(def.getDi_item_name());
			String name = def.getDi_name();
			if(map.keySet().contains(name)){
				map.get(name).add(d);
			}else{
				List<DI_DEF> l = new ArrayList<DI_DEF>();
				l.add(d);
				map.put(name,l);
			}
		}
		return map;
	}

	public int getFrontMachineTotal(String front_code,String front_name){
		Map<String,Object> param = new HashMap<>();
		param.put("front_code",front_code);
		param.put("front_name",front_name);
		return userMapper.getFrontMachineTotal(param);
	}

	public List<INF_FRONT_MACHINE> getFrontMachine(PageHelper page,String front_code,String front_name){
		page.setStart((page.getPage()-1)*page.getRows());
		page.setEnd(page.getRows());
		Map<String,Object> param = new HashMap<>();
		param.put("front_code",front_code);
		param.put("front_name",front_name);
		param.put("order",page.getOrder());
		param.put("sort",page.getSort());
		param.put("start",page.getStart());
		param.put("end",page.getEnd());
		return userMapper.getFrontMachine(param);
	}

	public boolean updateFrontMachine(INF_FRONT_MACHINE inf){
		try{
			userMapper.updateFrontMachine(inf);
			INF_TERMINAL_ACCOUNT account = new INF_TERMINAL_ACCOUNT();
			account.setTerminal_ormeterid(inf.getFront_id().longValue());
			account.setEquipment_name(inf.getFront_name());
			userMapper.updateTerminalAccount(account);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean addFrontMachine(INF_FRONT_MACHINE inf){
		try{
			inf.setFront_code(getFrontCode());
			userMapper.addFrontMachine(inf);
			// 从数据库获取刚保存的对象
			INF_FRONT_MACHINE in = userMapper.getFrontByCode(inf.getFront_code());
			// 同步添加到设备台账
			INF_TERMINAL_ACCOUNT account = new INF_TERMINAL_ACCOUNT();
			account.setTerminal_ormeterid(in.getFront_id().longValue());
			account.setEquipment_name(in.getFront_name());
			account.setDev_type(1);
			addTerminalAccount(account);
			//userMapper.addTerminalAccount(account);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private String getFrontCode(){
		String code = SharpUtil.randomHexString(16);
		if(userMapper.queryFrontMachineByCode(code)>0){
			getFrontCode();
		}
		return code;
	}

	public boolean deleteFrontMachine(String[] delIdArray){
		try{
			int delIdArrayInt[] = new int[delIdArray.length];
			for(int i = 0; i<delIdArray.length; i++){
				delIdArrayInt[i] = Integer.parseInt(delIdArray[i]);
			}
			userMapper.deleteFrontMachine(delIdArrayInt);
			userMapper.deleteTerminalAccount(delIdArrayInt);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public int getTerminalAccountTotal(String terminal_ormeterid,String equipment_name){
		Map<String,Object> param = new HashMap<>();
		param.put("terminal_ormeterid",terminal_ormeterid);
		param.put("equipment_name",equipment_name);
		return userMapper.getTerminalAccountTotal(param);
	}

	public List<INF_TERMINAL_ACCOUNT> getTerminalAccount(PageHelper page,String terminal_ormeterid,String equipment_name){
		page.setStart((page.getPage()-1)*page.getRows());
		page.setEnd(page.getRows());
		Map<String,Object> param = new HashMap<>();
		param.put("terminal_ormeterid",terminal_ormeterid);
		param.put("equipment_name",equipment_name);
		param.put("order",page.getOrder());
		param.put("sort",page.getSort());
		param.put("start",page.getStart());
		param.put("end",page.getEnd());
		return userMapper.getTerminalAccount(param);
	}

	public boolean addTerminalAccount(INF_TERMINAL_ACCOUNT inf_terminal_account){
		try{
			int size =0;
			Long id = inf_terminal_account.getTerminal_ormeterid();
			String name = inf_terminal_account.getEquipment_name();
			if(id!=null&&name!=null){
				size= userMapper.queryTerminalAccountIsExist(id,name);
			}
			if(size==0){
				userMapper.addTerminalAccount(inf_terminal_account);
			}else{
				System.out.println("主键冲突");
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean updateTerminalAccount(INF_TERMINAL_ACCOUNT inf_terminal_account){
		try{
			userMapper.updateTerminalAccount(inf_terminal_account);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean deleteTerminalAccount(String[] delIdArray){
		try{
			int delIdArrayInt[] = new int[delIdArray.length];
			for(int i = 0; i<delIdArray.length; i++){
				delIdArrayInt[i] = Integer.parseInt(delIdArray[i]);
			}
			userMapper.deleteTerminalAccount(delIdArrayInt);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public int getTransformerTotal(String transformer_no,String transformer_name,String create_date){
		Map<String,Object> param = new HashMap<>();
		param.put("transformer_no",transformer_no);
		param.put("transformer_name",transformer_name);
		param.put("create_date",create_date);
		return userMapper.getTransformerTotal(param);
	}

	public List<INF_TRANSFORMER> getTransformer(PageHelper page,String transformer_no,String transformer_name,String create_date){
		page.setStart((page.getPage()-1)*page.getRows());
		page.setEnd(page.getRows());
		Map<String,Object> param = new HashMap<>();
		param.put("transformer_no",transformer_no);
		param.put("transformer_name",transformer_name);
		param.put("create_date",create_date);
		param.put("order",page.getOrder());
		param.put("sort",page.getSort());
		param.put("start",page.getStart());
		param.put("end",page.getEnd());
		return userMapper.getTransformer(param);
	}

	public boolean addTransformer(INF_TRANSFORMER inf){
		try{
			int size = userMapper.queryTransformerIsExist(inf.getTransformer_id());
			if(size>0){
				return false;
			}
			userMapper.addTransformer(inf);
			// 添加时同步添加设备台账信息
			INF_TERMINAL_ACCOUNT account = new INF_TERMINAL_ACCOUNT();
			account.setTerminal_ormeterid(inf.getTransformer_id().longValue());
			account.setEquipment_name(inf.getTransformer_name());
			account.setRated_current(inf.getRated_current());
			account.setRated_voltage(inf.getRated_voltage());
			account.setRated_power(inf.getRated_current()*inf.getRated_voltage());
			account.setRemarks(inf.getRemarks());
			account.setDev_type(4);
			addTerminalAccount(account);
			//userMapper.addTerminalAccount(account);

		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean updateTransformer(INF_TRANSFORMER inf){
		try{
			userMapper.updateTransformer(inf);
			// 修改时同步修改设备台账信息
			INF_TERMINAL_ACCOUNT account = new INF_TERMINAL_ACCOUNT();
			account.setTerminal_ormeterid(inf.getTransformer_id().longValue());
			account.setEquipment_name(inf.getTransformer_name());
			account.setRated_current(inf.getRated_current());
			account.setRated_voltage(inf.getRated_voltage());
			account.setRated_power(inf.getRated_current()*inf.getRated_voltage());
			account.setRemarks(inf.getRemarks());
			userMapper.updateTerminalAccount(account);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;

	}

	public boolean deleteTransformer(String[] delIdArray){
		try{
			int delIdArrayInt[] = new int[delIdArray.length];
			for(int i = 0; i<delIdArray.length; i++){
				delIdArrayInt[i] = Integer.parseInt(delIdArray[i]);
			}
			userMapper.deleteTransformer(delIdArrayInt);
			userMapper.deleteTerminalAccount(delIdArrayInt);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public List<String> getAllTemplateId(){
		return userMapper.getAllTemplateId();
	}

	public List<TASK_TEMPLATE> getTaskTemplate(String id){
		return userMapper.getTaskTemplateById(id);
	}

	public int getTerminalInstallRunDropManagementTotal(String equipment_name){
		Map<String,Object> param = new HashMap<>();
		param.put("equipment_name",equipment_name);
		return userMapper.getTerminalInstallRunDropManagementTotal(param);
	}

	public List<INF_TERMINAL_MANAGEMENT> getTerminalInstallRunDropManagement(PageHelper page,String equipment_name){
		page.setStart((page.getPage()-1)*page.getRows());
		page.setEnd(page.getRows());
		Map<String,Object> param = new HashMap<>();
		param.put("equipment_name",equipment_name);
		param.put("order",page.getOrder());
		param.put("sort",page.getSort());
		param.put("start",page.getStart());
		param.put("end",page.getEnd());
		return userMapper.getTerminalInstallRunDropManagement(param);
	}

	public boolean addTerminalInstallRunDropManagement(INF_TERMINAL_MANAGEMENT t){
		try{
			userMapper.addTerminalInstallRunDropManagement(t);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean updateTerminalInstallRunDropManagement(INF_TERMINAL_MANAGEMENT i){
		try{
			userMapper.updateTerminalInstallRunDropManagement(i);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean deleteTerminalInstallRunDropManagement(String[] delIdArray){
		try{
			int delIdArrayInt[] = new int[delIdArray.length];
			for(int i = 0; i<delIdArray.length; i++){
				delIdArrayInt[i] = Integer.parseInt(delIdArray[i]);
			}
			userMapper.deleteTerminalInstallRunDropManagement(delIdArrayInt);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public int getDeviceRepairManagementTotal(String equipment_name){
		Map<String,Object> param = new HashMap<>();
		param.put("equipment_name",equipment_name);
		return userMapper.getDeviceRepairManagementTotal(param);
	}

	public List<IF_INF_MAINTAIN> getDeviceRepairManagement(PageHelper page,String equipment_name){
		page.setStart((page.getPage()-1)*page.getRows());
		page.setEnd(page.getRows());
		Map<String,Object> param = new HashMap<>();
		param.put("equipment_name",equipment_name);
		param.put("order",page.getOrder());
		param.put("sort",page.getSort());
		param.put("start",page.getStart());
		param.put("end",page.getEnd());
		return userMapper.getDeviceRepairManagement(param);
	}

	public boolean addDeviceRepairManagement(IF_INF_MAINTAIN i){
		try{
			userMapper.addDeviceRepairManagement(i);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean updateDeviceRepairManagement(IF_INF_MAINTAIN i){
		try{
			userMapper.updateDeviceRepairManagement(i);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean deleteDeviceRepairManagement(String[] delIdArray){
		try{
			int delIdArrayInt[] = new int[delIdArray.length];
			for(int i = 0; i<delIdArray.length; i++){
				delIdArrayInt[i] = Integer.parseInt(delIdArray[i]);
			}
			userMapper.deleteDeviceRepairManagement(delIdArrayInt);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public int getDeviceRepairPreWarnManagementTotal(String equipment_name){
		Map<String,Object> param = new HashMap<>();
		param.put("equipment_name",equipment_name);
		return userMapper.getDeviceRepairPreWarnManagementTotal(param);
	}

	public List<INF_ALARM_RECORD> getDeviceRepairPreWarnManagement(PageHelper page,String equipment_name){
		page.setStart((page.getPage()-1)*page.getRows());
		page.setEnd(page.getRows());
		Map<String,Object> param = new HashMap<>();
		param.put("equipment_name",equipment_name);
		param.put("order",page.getOrder());
		param.put("sort",page.getSort());
		param.put("start",page.getStart());
		param.put("end",page.getEnd());
		return userMapper.getDeviceRepairPreWarnManagement(param);
	}

	public boolean addDeviceRepairPreWarnManagement(INF_ALARM_RECORD t){
		try{
			userMapper.addDeviceRepairPreWarnManagement(t);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean deleteDeviceRepairPreWarnManagement(String[] delIdArray){
		try{
			int delIdArrayInt[] = new int[delIdArray.length];
			for(int i = 0; i<delIdArray.length; i++){
				delIdArrayInt[i] = Integer.parseInt(delIdArray[i]);
			}
			userMapper.deleteDeviceRepairPreWarnManagement(delIdArrayInt);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean updateDeviceRepairPreWarnManagement(INF_ALARM_RECORD i){
		try{
			userMapper.updateDeviceRepairPreWarnManagement(i);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public List<INF_TERM> getAllTerminal(){
		return userMapper.getAllTerminal();
	}

	// -------excel数据表存储-------
	// 将字符串转成MAP
	public Map<String,Object> excelString2Map(String tableData){
		// 字符串转成JSONObject
		List<JSONObject> list = JSON.parseArray(tableData,JSONObject.class);
		// 将JSON对象转换成MAP
		// 将放到一个新的Map集合中
		Map<String,Object> alls = new HashMap<String,Object>();
		for(JSONObject jo:list){
			Map<String,Object> map = (Map<String,Object>)jo;
			Set<String> keySet = map.keySet();
			for(String s:keySet){
				alls.put(s,map.get(s));
			}
		}
		return alls;
	}

	public boolean addExcelDataMeter(String tableData,String rows){
		Integer row = 0;
		if(rows!=null){
			row = Integer.valueOf(rows);
		}
		Map<String,Object> alls = excelString2Map(tableData);
		List<INF_MP_VO> addMp = new ArrayList<INF_MP_VO>();
		List<INF_MP_VO> update = new ArrayList<INF_MP_VO>();
		for(int i = 1; i<row; i++){
			String mp_id = StringUtils.isNotBlank((String)alls.get(i+"_0_1_1_0"))?(String)alls.get(i+"_0_1_1_0"):null;
			String mp_no = StringUtils.isNotBlank((String)alls.get(i+"_1_1_1_0"))?(String)alls.get(i+"_1_1_1_0"):null;
			String comm_no = StringUtils.isNotBlank((String)alls.get(i+"_2_1_1_0"))?(String)alls.get(i+"_2_1_1_0"):null;
			String mp_name = StringUtils.isNotBlank((String)alls.get(i+"_3_1_1_0"))?(String)alls.get(i+"_3_1_1_0"):null;
			String mp_type = StringUtils.isNotBlank((String)alls.get(i+"_4_1_1_0"))?(String)alls.get(i+"_4_1_1_0"):null;

			String industry_type = StringUtils.isNotBlank((String)alls.get(i+"_5_1_1_0"))?(String)alls.get(i+"_5_1_1_0"):null;
			String mp_class = StringUtils.isNotBlank((String)alls.get(i+"_6_1_1_0"))?(String)alls.get(i+"_6_1_1_0"):null;
			String main_mp_id = StringUtils.isNotBlank((String)alls.get(i+"_7_1_1_0"))?(String)alls.get(i+"_7_1_1_0"):null;
			String ct = StringUtils.isNotBlank((String)alls.get(i+"_8_1_1_0"))?(String)alls.get(i+"_8_1_1_0"):null;
			String pt = StringUtils.isNotBlank((String)alls.get(i+"_9_1_1_0"))?(String)alls.get(i+"_9_1_1_0"):null;

			String ct_pt = StringUtils.isNotBlank((String)alls.get(i+"_10_1_1_0"))?(String)alls.get(i+"_10_1_1_0"):null;
			String create_man = StringUtils.isNotBlank((String)alls.get(i+"_11_1_1_0"))?(String)alls.get(i+"_11_1_1_0"):null;
			String create_date = StringUtils.isNotBlank((String)alls.get(i+"_12_1_1_0"))?(String)alls.get(i+"_12_1_1_0")+" 00:00:00":null;
			String mp_status = StringUtils.isNotBlank((String)alls.get(i+"_13_1_1_0"))?(String)alls.get(i+"_13_1_1_0"):null;
			String mp_asset_no = StringUtils.isNotBlank((String)alls.get(i+"_14_1_1_0"))?(String)alls.get(i+"_14_1_1_0"):null;

			String amm_protocol_addr = StringUtils.isNotBlank((String)alls.get(i+"_15_1_1_0"))?(String)alls.get(i+"_15_1_1_0"):null;
			String amm_protocol = StringUtils.isNotBlank((String)alls.get(i+"_16_1_1_0"))?(String)alls.get(i+"_16_1_1_0"):null;
			String rated_voltage = StringUtils.isNotBlank((String)alls.get(i+"_17_1_1_0"))?(String)alls.get(i+"_17_1_1_0"):null;
			String rated_current = StringUtils.isNotBlank((String)alls.get(i+"_18_1_1_0"))?(String)alls.get(i+"_18_1_1_0"):null;
			String amm_class = StringUtils.isNotBlank((String)alls.get(i+"_19_1_1_0"))?(String)alls.get(i+"_19_1_1_0"):null;

			String amm_manufacturer = StringUtils.isNotBlank((String)alls.get(i+"_20_1_1_0"))?(String)alls.get(i+"_20_1_1_0"):null;
			String amm_type = StringUtils.isNotBlank((String)alls.get(i+"_21_1_1_0"))?(String)alls.get(i+"_21_1_1_0"):null;
			String mp_addr = StringUtils.isNotBlank((String)alls.get(i+"_22_1_1_0"))?(String)alls.get(i+"_22_1_1_0"):null;
			String phase = StringUtils.isNotBlank((String)alls.get(i+"_23_1_1_0"))?(String)alls.get(i+"_23_1_1_0"):null;
			String customer_id = StringUtils.isNotBlank((String)alls.get(i+"_24_1_1_0"))?(String)alls.get(i+"_24_1_1_0"):null;

			String term_id = StringUtils.isNotBlank((String)alls.get(i+"_25_1_1_0"))?(String)alls.get(i+"_25_1_1_0"):null;
			String transformer_id = StringUtils.isNotBlank((String)alls.get(i+"_26_1_1_0"))?(String)alls.get(i+"_26_1_1_0"):null;
			String remarks = StringUtils.isNotBlank((String)alls.get(i+"_27_1_1_0"))?(String)alls.get(i+"_27_1_1_0"):null;
			String standard_factor_s = StringUtils.isNotBlank((String)alls.get(i+"_28_1_1_0"))?(String)alls.get(i+"_28_1_1_0"):null;
			String mp_p_e = StringUtils.isNotBlank((String)alls.get(i+"_29_1_1_0"))?(String)alls.get(i+"_29_1_1_0"):null;

			String dl_ti = StringUtils.isNotBlank((String)alls.get(i+"_30_1_1_0"))?(String)alls.get(i+"_30_1_1_0"):null;
			String equipment_no = StringUtils.isNotBlank((String)alls.get(i+"_31_1_1_0"))?(String)alls.get(i+"_31_1_1_0"):null;
			String sys_no = StringUtils.isNotBlank((String)alls.get(i+"_32_1_1_0"))?(String)alls.get(i+"_32_1_1_0"):null;
			String group_id = StringUtils.isNotBlank((String)alls.get(i+"_33_1_1_0"))?(String)alls.get(i+"_33_1_1_0"):null;
			String is_main_obj = StringUtils.isNotBlank((String)alls.get(i+"_34_1_1_0"))?(String)alls.get(i+"_34_1_1_0"):null;

			String check_demand = StringUtils.isNotBlank((String)alls.get(i+"_35_1_1_0"))?(String)alls.get(i+"_35_1_1_0"):null;
			String is_public = StringUtils.isNotBlank((String)alls.get(i+"_36_1_1_0"))?(String)alls.get(i+"_36_1_1_0"):null;
			String imgPath = StringUtils.isNotBlank((String)alls.get(i+"_37_1_1_0"))?(String)alls.get(i+"_37_1_1_0"):null;
			INF_MP_VO vo = new INF_MP_VO(mp_id,mp_no,comm_no,mp_name,mp_type,industry_type,mp_class,main_mp_id,ct,pt,ct_pt,create_man,create_date,mp_status,mp_asset_no,amm_protocol_addr,amm_protocol,rated_voltage,rated_current,amm_class,amm_manufacturer,amm_type,mp_addr,phase,customer_id,term_id,transformer_id,remarks,standard_factor_s,mp_p_e,dl_ti,equipment_no,sys_no,group_id,is_main_obj,check_demand,is_public,imgPath);
			if(userMapper.existInfMp(mp_id)==0){
				addMp.add(vo);
			}else{
				update.add(vo);
			}
		}
		// 将集合中的数据存到数据库中
		// 不存在ID,保存
		List<INF_MP> addI = new ArrayList<INF_MP>();
		for(int j = 0; j<addMp.size(); j++){
			INF_MP i = new INF_MP();
			addI.add(i);
		}
		SharpUtil.formatPageObjectToPojo(addMp,addI);

		// 有相同ID,修改
		List<INF_MP> updateI = new ArrayList<INF_MP>();
		for(int j = 0; j<update.size(); j++){
			INF_MP i = new INF_MP();
			updateI.add(i);
		}
		SharpUtil.formatPageObjectToPojo(update,updateI);
		try{
			for(INF_MP m:addI){
				userMapper.addMeter(m);
			}
			for(INF_MP m:updateI){
				userMapper.updateMeter(m);
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean addExcelDataTerminal(String tableData,String rows){
		Integer row = 0;
		if(rows!=null){
			row = Integer.valueOf(rows);
		}
		Map<String,Object> alls = excelString2Map(tableData);
		List<INF_TERM_VO> addterm = new ArrayList<INF_TERM_VO>();
		List<INF_TERM_VO> updterm = new ArrayList<INF_TERM_VO>();
		for(int i = 1; i<row; i++){
			String term_id = StringUtils.isNotBlank((String)alls.get(i+"_0_1_1_0"))?(String)alls.get(i+"_0_1_1_0"):null;
			String term_addr = StringUtils.isNotBlank((String)alls.get(i+"_1_1_1_0"))?(String)alls.get(i+"_1_1_1_0"):null;
			String term_name = StringUtils.isNotBlank((String)alls.get(i+"_2_1_1_0"))?(String)alls.get(i+"_2_1_1_0"):null;
			String phoneNo = StringUtils.isNotBlank((String)alls.get(i+"_3_1_1_0"))?(String)alls.get(i+"_3_1_1_0"):null;
			String simNo = StringUtils.isNotBlank((String)alls.get(i+"_4_1_1_0"))?(String)alls.get(i+"_4_1_1_0"):null;
			String ip = StringUtils.isNotBlank((String)alls.get(i+"_5_1_1_0"))?(String)alls.get(i+"_5_1_1_0"):null;
			String term_status = StringUtils.isNotBlank((String)alls.get(i+"_6_1_1_0"))?(String)alls.get(i+"_6_1_1_0"):null;
			String create_man = StringUtils.isNotBlank((String)alls.get(i+"_7_1_1_0"))?(String)alls.get(i+"_7_1_1_0"):null;
			String create_date = StringUtils.isNotBlank((String)alls.get(i+"_8_1_1_0"))?(String)alls.get(i+"_8_1_1_0")+" 00:00:00":null;
			String is_valid = StringUtils.isNotBlank((String)alls.get(i+"_9_1_1_0"))?(String)alls.get(i+"_9_1_1_0"):null;
			String term_asset_no = StringUtils.isNotBlank((String)alls.get(i+"_10_1_1_0"))?(String)alls.get(i+"_10_1_1_0"):null;
			String term_manufacturer = StringUtils.isNotBlank((String)alls.get(i+"_11_1_1_0"))?(String)alls.get(i+"_11_1_1_0"):null;
			String phase = StringUtils.isNotBlank((String)alls.get(i+"_12_1_1_0"))?(String)alls.get(i+"_12_1_1_0"):null;
			String remarks = StringUtils.isNotBlank((String)alls.get(i+"_13_1_1_0"))?(String)alls.get(i+"_13_1_1_0"):null;
			String customer_id = StringUtils.isNotBlank((String)alls.get(i+"_14_1_1_0"))?(String)alls.get(i+"_14_1_1_0"):null;
			String term_pro = StringUtils.isNotBlank((String)alls.get(i+"_15_1_1_0"))?(String)alls.get(i+"_15_1_1_0"):null;
			String term_model = StringUtils.isNotBlank((String)alls.get(i+"_16_1_1_0"))?(String)alls.get(i+"_16_1_1_0"):null;

			INF_TERM_VO vo = new INF_TERM_VO(term_id,term_addr,term_name,phoneNo,simNo,ip,term_status,create_man,create_date,is_valid,term_asset_no,term_manufacturer,phase,remarks,customer_id,term_pro,term_model);
			int e = userMapper.existInfTerm(term_id);
			if(e==0){
				addterm.add(vo);
			}else{
				updterm.add(vo);
			}
		}

		// 将新增的对象进行存储
		List<INF_TERM> aterm = new ArrayList<INF_TERM>();
		List<INF_TERM> uterm = new ArrayList<INF_TERM>();
		for(int i = 0; i<addterm.size(); i++){
			INF_TERM t = new INF_TERM();
			aterm.add(t);
		}
		SharpUtil.formatPageObjectToPojo(addterm,aterm);

		for(int i = 0; i<updterm.size(); i++){
			INF_TERM t = new INF_TERM();
			uterm.add(t);
		}
		SharpUtil.formatPageObjectToPojo(addterm,aterm);
		SharpUtil.formatPageObjectToPojo(updterm,uterm);
		try{
			for(int i = 0; i<aterm.size(); i++){
				userMapper.addTerminal(aterm.get(i));
			}
			for(int i = 0; i<uterm.size(); i++){
				userMapper.updateTerminal(uterm.get(i));
			}
			/*
			 * userMapper.addTermBat(aterm); userMapper.updateTermBat(updterm);
			 */
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 设备厂家在线率管理
	 * 
	 * @return
	 */
	/*
	 * public List<DEVICESTATERANK> getDeviceRank() { //获取设备在线率
	 * List<INF_ONLLIERATE> rate = getOnlineRate(); List<INF_ONLLIERATE> hasRate
	 * = new ArrayList<INF_ONLLIERATE>(); //获取只有在线率的 for (int i = 0; i <
	 * rate.size(); i++) { INF_ONLLIERATE inf = rate.get(i);
	 * if(inf.getTerminal_ormeterid()!=null&&inf.getDev_type()!=null){
	 * hasRate.add(inf); } } Map<String ,DEVICESTATERANK> des = new
	 * HashMap<String, DEVICESTATERANK>(); for (INF_ONLLIERATE i : hasRate) {
	 * //写道这，根据ID和类型,找到对应的工厂，记录设置在线总时长 Integer type = i.getDev_type(); Integer
	 * id = i.getTerminal_ormeterid();
	 * 
	 * //多次查询，不行 INF_MANUFACTURER manu =
	 * userMapper.getManufacturerByTypeAndId(type,id); if(manu==null){ continue;
	 * } String name = manu.getManufacturer_name();
	 * if(des.keySet().contains(name)){ DEVICESTATERANK dev = des.get(name);
	 * dev.setTime(dev.getTime()+Integer.valueOf(i.getOnlineLength()));
	 * des.put(name, dev); }else{ DEVICESTATERANK dev = new DEVICESTATERANK();
	 * dev.setDevices(name); dev.setTime(i.getTime()); des.put(name, dev); }
	 * 
	 * } List<DEVICESTATERANK> devs = new ArrayList<DEVICESTATERANK>();
	 * Set<String> keySet = des.keySet(); for (String s : keySet) {
	 * DEVICESTATERANK devicestaterank = des.get(s); devs.add(devicestaterank);
	 * } //对存在的工厂进行排名 List<DEVICESTATERANK> dRank = new
	 * ArrayList<DEVICESTATERANK>(); int rank=1; for (int i = devs.size(); i >0;
	 * i--) { int x = 0; Long max=0L; max = devs.get(0).getTime(); for(int j =
	 * 1;j<i;j++){ if(devs.get(j).getTime()>max){ max = devs.get(j).getTime();
	 * x=j; } } DEVICESTATERANK d = devs.get(x); d.setRank(rank); rank++;
	 * dRank.add(d); devs.remove(x); } return dRank; }
	 */
	// 设备厂家在线排行
	public List<DEVICESTATERANK> getDeviceRank(String beginTime,String endTime){
		// 对参数进行处理 格式（开始：2016-12-10 00:00:00,结束：2016-12-10 23:59:59）
		List<INF_TERMINAL_ONLINE_RATE> fbList = new ArrayList<INF_TERMINAL_ONLINE_RATE>();
		List<INF_TERMINAL_ONLINE_RATE> tbList = new ArrayList<INF_TERMINAL_ONLINE_RATE>();
		if(StringUtils.isNotBlank(beginTime)){
			beginTime = beginTime+" 00:00:00";
			fbList = addFront(beginTime,1);
			tbList = addFront(beginTime,0);
		}
		if(StringUtils.isNotBlank(endTime)){
			endTime = endTime+" 23:59:59";
		}
		// 1、一条语句查询出所有厂家
		List<INF_MANUFACTURER> allManu = userMapper.getAllManufacturer();
		List<DEVICESTATERANK> notRate = new ArrayList<DEVICESTATERANK>();
		List<DEVICESTATERANK> devs = new ArrayList<DEVICESTATERANK>();
		// 2.查询所有由厂家的集中器，按厂家分组
		List<INF_TERM> allTerm = userMapper.getAllTermByManufacturer();
		Map<String,List<INF_TERM>> termList = new HashMap<String,List<INF_TERM>>();
		for(int i = 0; i<allTerm.size(); i++){
			INF_TERM t = allTerm.get(i);
			if(termList.keySet().contains(t.getTerm_manufacturer())){
				termList.get(t.getTerm_manufacturer()).add(t);
			}else{
				List<INF_TERM> term = new ArrayList<>();
				term.add(t);
				termList.put(t.getTerm_manufacturer(),term);
			}
		}
		// 查询前置机与设备中间表
		long[] ids = new long[allTerm.size()];// 终端id
		for(int j = 0; j<allTerm.size(); j++){
			ids[j] = allTerm.get(j).getTerm_id();
		}
		List<FrontAndTerm> allFt = userMapper.getAllFrontAndTerm(ids);
		int[] fs = new int[allFt.size()];
		for(int i = 0; i<allFt.size(); i++){
			fs[i] = allFt.get(i).getFront_id();
		}
		// 查询前置机、设备的在线记录
		List<INF_TERMINAL_ONLINE_RATE> fronts = userMapper.getFrontOnlineRateByDate(fs,beginTime,endTime);
		fronts.addAll(fbList);
		List<INF_TERMINAL_ONLINE_RATE> trems = userMapper.getTremsOnlineRateByDate(ids,beginTime,endTime);
		trems.addAll(tbList);
		for(int i = 0; i<allManu.size(); i++){
			// 添加对没有在线率的工厂排行对象
			DEVICESTATERANK dev = new DEVICESTATERANK();
			dev.setDevices(allManu.get(i).getManufacturer_name());
			dev.setTime(0L);
			dev.setRate(0.00);
			// dev.setRank(0);
			notRate.add(dev);
			// 2、找出厂家、集中器关联关系 term_manufacturer = 厂家名
			List<INF_TERM> ts = termList.get(allManu.get(i).getManufacturer_name());
			// List<INF_TERM> ts =
			// userMapper.getTermBymanufacturer(allManu.get(i).getManufacturer_name());
			if(ts==null){
				continue;
			}
			// 3、查询在线前置机、集中器关联表
			int[] tids = new int[ts.size()];// 终端id
			for(int j = 0; j<ts.size(); j++){
				tids[j] = ts.get(j).getTerm_id();
			}
			// List<FrontAndTerm> ft =
			// userMapper.getFrontAndTermBytermId(ids);//设备id
			// 4、查询指定指定时段内的前置机、集中器上下线记录
			List<FrontAndTerm> ft = new ArrayList<FrontAndTerm>();
			for(int k = 0; k<tids.length; k++){
				for(int j = 0; j<allFt.size(); j++){
					if(allFt.get(j).getTerminal_ormeterid()==tids[k]){
						ft.add(allFt.get(j));
						break;
					}
				}
			}
			// 获取在线记录
			int[] fts = new int[ft.size()];// 前置机id
			for(int j = 0; j<ft.size(); j++){
				fts[j] = ft.get(j).getFront_id();
			}
			List<INF_TERMINAL_ONLINE_RATE> front = new ArrayList<>();
			for(int j = 0; j<fts.length; j++){
				for(int k = 0; k<fronts.size(); k++){
					if(fronts.get(k).getTerminal_ormeterid().intValue()==fts[j]){
						front.add(fronts.get(k));
					}
				}
			}
			List<INF_TERMINAL_ONLINE_RATE> trem = new ArrayList<>();
			for(int j = 0; j<tids.length; j++){
				for(int k = 0; k<trems.size(); k++){
					if(trems.get(k).getTerminal_ormeterid().intValue()==tids[j]){
						trem.add(trems.get(k));
					}
				}
			}
			if(trem.size()==0||front.size()==0){
				continue;
			}
			// 5、按厂家统计出各集中器平均在线率 设备在线总时长，减去前置机在线总时长
			long frontTime = getFrontTime(front,beginTime,endTime);
			long termTime = getTermTime(trem,ft,front,beginTime,endTime);
			Double rate = (double)termTime/frontTime*100;
			DEVICESTATERANK device = new DEVICESTATERANK();
			device.setDevices(allManu.get(i).getManufacturer_name());
			device.setTime(termTime);
			device.setRate(rate);
			devs.add(device);
			notRate.remove(dev);// 有在线率时移除空的对象
		}
		// 6、并排名
		List<DEVICESTATERANK> dRank = new ArrayList<DEVICESTATERANK>();
		int rank = 1;
		for(int i = devs.size(); i>0; i--){
			int x = 0;
			Double max = 0.00;
			max = devs.get(0).getRate();
			for(int j = 1; j<i; j++){
				if(devs.get(j).getRate()>max){
					max = devs.get(j).getRate();
					x = j;
				}
			}
			DEVICESTATERANK d = devs.get(x);
			d.setRank(rank);
			rank++;
			dRank.add(d);
			devs.remove(x);
		}
		dRank.addAll(notRate);
		return dRank;
	}

	public long getTermTime(List<INF_TERMINAL_ONLINE_RATE> terms,List<FrontAndTerm> ft,List<INF_TERMINAL_ONLINE_RATE> fronts,String beginTime,String endTime){
		Date begin = null,end = null;
		if(StringUtils.isNotBlank(beginTime)&&StringUtils.isNotBlank(endTime)){
			try{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				begin = sdf.parse(beginTime);
				end = sdf.parse(endTime);
			}catch(ParseException e1){
				System.out.println("查询结束时间转换异常（终端在线记录）");
				e1.printStackTrace();
			}
		}
		long tertotal = 0L;// 终端在线时长；
		// 获取每一个设备的在线时间
		Map<Long,List<INF_TERMINAL_ONLINE_RATE>> termMap = new HashMap<>();
		for(int i = 0; i<terms.size(); i++){
			Long id = terms.get(i).getTerminal_ormeterid();
			if(termMap.keySet().contains(id)){
				termMap.get(id).add(terms.get(i));
			}else{
				List<INF_TERMINAL_ONLINE_RATE> term = new ArrayList<INF_TERMINAL_ONLINE_RATE>();
				term.add(terms.get(i));
				termMap.put(id,term);
			}
		}
		// 分别计算每一个设备的在线时间
		Set<Long> keySet = termMap.keySet();
		for(Long id:keySet){
			List<INF_TERMINAL_ONLINE_RATE> onlines = termMap.get(id);
			for(int i = 0; i<onlines.size(); i++){
				INF_TERMINAL_ONLINE_RATE tOnlineRate = onlines.get(i);
				int k = (i+1==onlines.size()?onlines.size()-1:i+1);// 下一条记录，如果没有下一条则取当前记录
				INF_TERMINAL_ONLINE_RATE preOnlineRate = onlines.get(k);
				// 终端上下线时间
				Date terminalStart = null,terminalEnd = null;
				// 动作类型 1登录 2退出
				if(tOnlineRate.getAction_type()==1){
					terminalStart = tOnlineRate.getHappen_time();
					// 如果设备上线时间小于开始时间，上线时间等于开始试讲
					// -------------------------------
					if(begin!=null&&terminalStart.before(begin)){
						terminalStart = begin;
					}
					// -------------------------------
					if(preOnlineRate.getAction_type()==2){
						terminalEnd = preOnlineRate.getHappen_time();
						if(end!=null&&end.before(terminalEnd)){
							terminalEnd = end;
						}
						i++;
					}else{
						// 不存在对应的下线时间时,先找有没有掉线的记录，没有则找前置机在线时间
						Long tid = tOnlineRate.getTerminal_ormeterid();// 设备id
						Integer fid = 0;// 前置机id
						boolean unreal = true;
						// 查找最近的前置机时间
						for(int j = 0; j<ft.size(); j++){
							if(ft.get(j).getTerminal_ormeterid().equals(tid)){
								fid = ft.get(j).getFront_id();
								break;
							}
						}
						for(int j = 0; j<fronts.size(); j++){
							INF_TERMINAL_ONLINE_RATE rate = fronts.get(j);
							if(rate.getTerminal_ormeterid().equals(fid)// 对应的前置机
									&&rate.getAction_type()==2&&terminalStart.before(rate.getHappen_time())// 前置季结束在上线之后
							){
								if(end!=null){
									terminalEnd = end.compareTo(fronts.get(j).getHappen_time())<0?end:fronts.get(j).getHappen_time();
								}else{
									terminalEnd = fronts.get(j).getHappen_time();
								}
								unreal = false;
								break;
							}
						}
						if(unreal){
							terminalEnd = new Date();
							if(end!=null){
								terminalEnd = end;
							}
							long e = terminalEnd.getTime();
							long s = terminalStart.getTime();
							tertotal += e-s;
							continue;
						}
					}
				}else{
					if(i==0&&StringUtils.isNotBlank(beginTime)){
						// 第一条是下线记录
						long e = tOnlineRate.getHappen_time().getTime();
						long s = begin.getTime();
						tertotal += e-s;
					}
					continue;
				}
				// 设备在线时间
				long e = terminalEnd.getTime();
				long s = terminalStart.getTime();
				tertotal += e-s;
			}
		}
		return tertotal;
	}

	public long getFrontTime(List<INF_TERMINAL_ONLINE_RATE> FrontOnlines,String beginTime,String endTime){
		Date begin = null,end = null;
		if(StringUtils.isNotBlank(beginTime)&&StringUtils.isNotBlank(endTime)){
			try{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				begin = sdf.parse(beginTime);
				end = sdf.parse(endTime);
			}catch(ParseException e){
				e.printStackTrace();
			}
		}

		long frontTotal = 0L;
		// 将不同的前置机分开；
		Map<Long,List<INF_TERMINAL_ONLINE_RATE>> fs = new HashMap<>();
		for(int i = 0; i<FrontOnlines.size(); i++){
			Long id = FrontOnlines.get(i).getTerminal_ormeterid();
			if(fs.keySet().contains(id)){
				fs.get(id).add(FrontOnlines.get(i));
			}else{
				List<INF_TERMINAL_ONLINE_RATE> f = new ArrayList<INF_TERMINAL_ONLINE_RATE>();
				f.add(FrontOnlines.get(i));
				fs.put(id,f);
			}
		}
		// 别计算前置机的在线时长，再累加
		Set<Long> keySet = fs.keySet();
		for(Long id:keySet){
			List<INF_TERMINAL_ONLINE_RATE> ft = fs.get(id);
			for(int j = 0; j<ft.size(); j++){
				int k = (j+1==ft.size()?ft.size()-1:j+1);
				long on = 0L;
				long off = 0L;
				INF_TERMINAL_ONLINE_RATE onter = ft.get(j);
				INF_TERMINAL_ONLINE_RATE offter = ft.get(k);
				if(onter.getAction_type()==1){
					on = onter.getHappen_time().getTime();
					// ------------------------
					if(begin!=null&&onter.getHappen_time().before(begin)){
						on = begin.getTime();
					}
					// --------------------------
					if(offter.getAction_type()==2){
						off = offter.getHappen_time().getTime();
						if(end!=null&&end.before(offter.getHappen_time())){
							off = end.getTime();
						}
						j++;
					}else{
						if(end!=null){
							off = end.getTime();
						}else{
							off = new Date().getTime();
						}
						// 当只有开始没结束时，认为是最后一条记录
						frontTotal += off-on;
						break;
					}
				}else{
					// 当第一条记录是结束时，
					if(j==0&&begin!=null){
						on = begin.getTime();
						off = onter.getHappen_time().getTime();
						frontTotal += off-on;
					}
					continue;
				}
				frontTotal += off-on;
			}
		}
		return frontTotal;
	}

	/**
	 * 设备在线率
	 * 
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public List<INF_ONLLIERATE> getOnlineRate(String beginTime,String endTime){
		List<INF_TERMINAL_ONLINE_RATE> frontList = new ArrayList<INF_TERMINAL_ONLINE_RATE>();
		List<INF_TERMINAL_ONLINE_RATE> terList = new ArrayList<INF_TERMINAL_ONLINE_RATE>();
		// 对参数进行处理 格式（开始：2016-12-10 00:00:00,结束：2016-12-10 23:59:59）
		Date beginDate = null,endDate = null;
		if(StringUtils.isNotBlank(beginTime)&&StringUtils.isNotBlank(endTime)){
			beginTime = beginTime+" 00:00:00";
			frontList = addFront(beginTime,1);
			terList = addFront(beginTime,0);
			endTime = endTime+" 23:59:59";
			try{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				beginDate = sdf.parse(beginTime);
				endDate = sdf.parse(endTime);
			}catch(Exception e){
				e.printStackTrace();
			}

		}
		// 返回的数据集合
		List<INF_ONLLIERATE> notRate = new ArrayList<>();
		// 有在线率的集合
		List<INF_ONLLIERATE> hasRate = new ArrayList<>();
		// 1.获取所有的设备，除了前置机
		List<INF_TERMINAL_ACCOUNT> terminas = userMapper.getTerminalAccounts();
		// 由设备id获取对应所有的前置机
		// 获取对应的中间表信息
		long[] ids = new long[terminas.size()]; // 对应设备的id
		for(int i = 0; i<ids.length; i++){
			ids[i] = terminas.get(i).getTerminal_ormeterid();
		}
		List<FrontAndTerm> fts = userMapper.getAllFrontAndTerm(ids);
		List<INF_FRONT_MACHINE> fronts = userMapper.getFrontMachineByonline();
		// 前置机的在线记录，按前置机id分组
		List<INF_TERMINAL_ONLINE_RATE> frontOnlines = userMapper.getFrontOnlineOnlineRateByDates(beginTime,endTime);
		// 数据补充
		frontOnlines.addAll(frontList);
		Map<Long,List<INF_TERMINAL_ONLINE_RATE>> frontByid = new HashMap<>();
		for(int i = 0; i<frontOnlines.size(); i++){
			INF_TERMINAL_ONLINE_RATE f = frontOnlines.get(i);
			Long terminal_ormeterid = f.getTerminal_ormeterid();
			if(frontByid.keySet().contains(terminal_ormeterid)){
				frontByid.get(f.getTerminal_ormeterid()).add(f);
			}else{
				List<INF_TERMINAL_ONLINE_RATE> fList = new ArrayList<>();
				fList.add(f);
				frontByid.put(f.getTerminal_ormeterid(),fList);
			}
		}
		// 设备的在线记录，按设备分组
		List<INF_TERMINAL_ONLINE_RATE> terOnline = userMapper.getTerminalOnlineRateByDates(beginTime,endTime);
		// --补充
		terOnline.addAll(terList);
		Map<Long,List<INF_TERMINAL_ONLINE_RATE>> termByid = new HashMap<>();
		for(int i = 0; i<terOnline.size(); i++){
			INF_TERMINAL_ONLINE_RATE f = terOnline.get(i);
			if(termByid.keySet().contains(f.getTerminal_ormeterid())){
				termByid.get(f.getTerminal_ormeterid()).add(f);
			}else{
				List<INF_TERMINAL_ONLINE_RATE> fList = new ArrayList<>();
				fList.add(f);
				termByid.put(f.getTerminal_ormeterid(),fList);
			}
		}
		for(int i = 0; i<terminas.size(); i++){
			INF_TERMINAL_ACCOUNT account = terminas.get(i);
			INF_ONLLIERATE rate = new INF_ONLLIERATE();
			rate.setFront_name(null);
			rate.setEquipment_name(account.getEquipment_name());
			rate.setDev_type(account.getDev_type());
			rate.setOnlineLength(0L);
			rate.setOnlinerate(0D);
			// 终端和前置机的在线时长；
			long tertotal = 0L,frontTotal = 0L;
			// 获取设备对应的前置机
			INF_FRONT_MACHINE front = null;
			for(int j = 0; j<fts.size(); j++){
				Integer tid = fts.get(j).getTerminal_ormeterid();
				Long oid = account.getTerminal_ormeterid();
				if(tid.toString().equals(oid.toString())){// 找到对应的前置机id
					Integer front_id = fts.get(j).getFront_id();
					for(int k = 0; k<fronts.size(); k++){
						if(front_id.equals(fronts.get(k).getFront_id())){// 设备对应的前置机
							front = fronts.get(k);
							break;
						}
					}
					break;
				}
			}
			if(front==null){
				rate.setFront_name("警告：未分配前置机,不能计算在线率");
				notRate.add(rate);
				continue;
			}
			// 前置机在线时间 注意格式
			List<INF_TERMINAL_ONLINE_RATE> FrontOnlines = frontByid.get(Long.valueOf(front.getFront_id().toString()));
			if(FrontOnlines==null){
				rate.setFront_name(front.getFront_name());
				notRate.add(rate);
				continue;
			}
			// 查询对应设备的在线记录，获取每一条在线记录
			List<INF_TERMINAL_ONLINE_RATE> onlines = termByid.get(account.getTerminal_ormeterid());
			if(onlines==null){
				rate.setFront_name(front.getFront_name());
				notRate.add(rate);
				continue;
			}
			for(int j = 0; j<onlines.size(); j++){
				INF_TERMINAL_ONLINE_RATE tOnlineRate = onlines.get(j);
				int k = (j+1==onlines.size()?onlines.size()-1:j+1);// 下一条记录，如果没有下一条则取当前记录
				INF_TERMINAL_ONLINE_RATE preOnlineRate = onlines.get(k);
				// 终端上下线时间
				Date terminalStart = null,terminalEnd = null;
				// 动作类型 1登录 2退出
				if(tOnlineRate.getAction_type()==1){
					terminalStart = tOnlineRate.getHappen_time();
					if(beginDate!=null&&terminalStart.before(beginDate)){
						terminalStart = beginDate;
					}
					if(preOnlineRate.getAction_type()==2){
						terminalEnd = preOnlineRate.getHappen_time();
						j++;
					}else{// 不存在对应的下线时间时,先找有没有掉线的记录，没有则找前置机在线时间
							// 找前置机的下线时间(前置机Id,在什么时间之后的，动作类型)
						boolean notEnd = true;
						for(int l = 0; l<FrontOnlines.size(); l++){
							INF_TERMINAL_ONLINE_RATE f = FrontOnlines.get(l);
							if(f.getAction_type().equals(2)&&tOnlineRate.getHappen_time().before(f.getHappen_time())){
								terminalEnd = f.getHappen_time();
								notEnd = false;
								break;
							}
						}
						if(notEnd){
							// 如果没有，设置当前时间为下线时间，结束循环
							terminalEnd = new Date();
							if(endDate!=null&&endDate.before(terminalEnd)){
								terminalEnd = endDate;
							}
							long e = terminalEnd.getTime();
							long s = terminalStart.getTime();
							tertotal += e-s;
							break;
						}
					}
				}else{
					// 当没有开始只有结束时，
					if(j==0&&beginDate!=null){
						// 第一条是下线记录
						long e = tOnlineRate.getHappen_time().getTime();
						long s = beginDate.getTime();
						tertotal += e-s;
					}
					continue;
				}
				// 设备在线时间
				long e = terminalEnd.getTime();
				long s = terminalStart.getTime();
				tertotal += e-s;
			}
			for(int j = 0; j<FrontOnlines.size(); j++){
				int k = (j+1==FrontOnlines.size()?FrontOnlines.size()-1:j+1);
				long on = 0L;
				long off = 0L;
				INF_TERMINAL_ONLINE_RATE onter = FrontOnlines.get(j);
				INF_TERMINAL_ONLINE_RATE offter = FrontOnlines.get(k);
				if(onter.getAction_type()==1){
					on = onter.getHappen_time().getTime();
					if(beginDate!=null&&onter.getHappen_time().before(beginDate)){
						on = beginDate.getTime();
					}
					if(offter.getAction_type()==2){
						off = offter.getHappen_time().getTime();
						j++;
					}else{
						off = new Date().getTime();
						if(endDate!=null&&endDate.getTime()<off){
							off = endDate.getTime();
						}
						// 当只有开始没结束时，认为是最后一条记录
						frontTotal += off-on;
						break;
					}
				}else{
					// 当第一条记录是结束时，
					if(j==0&&beginDate!=null){
						on = beginDate.getTime();
						off = onter.getHappen_time().getTime();
						frontTotal += off-on;
					}
					continue;
				}
				frontTotal += off-on;
			}
			// 在线率处理
			double tRate = ((double)tertotal/frontTotal)*100;
			INF_ONLLIERATE online = new INF_ONLLIERATE();
			online.setTerminal_ormeterid(onlines.get(0).getTerminal_ormeterid());
			online.setOnlinerate(tRate);
			online.setFront_name(front.getFront_name());
			online.setEquipment_name(account.getEquipment_name());
			online.setDev_type(account.getDev_type());
			online.setOnlineLength(tertotal);
			hasRate.add(online);
		}
		// 排名处理
		List<INF_ONLLIERATE> dRank = new ArrayList<INF_ONLLIERATE>();
		for(int i = hasRate.size(); i>0; i--){
			int x = 0;
			Double max = 0.00;
			max = hasRate.get(0).getOnlinerate();
			for(int j = 1; j<i; j++){
				if(hasRate.get(j).getOnlinerate()>max){
					max = hasRate.get(j).getOnlinerate();
					x = j;
				}
			}
			INF_ONLLIERATE d = hasRate.get(x);
			dRank.add(d);
			hasRate.remove(x);
		}
		dRank.addAll(notRate);
		List<MeasureDevCustomer> measureDevCustomerList = new ArrayList<MeasureDevCustomer>();
		List<MeasureDevCustomer> tmDevCustomerList = userMapper.getAllTerminalDevCustomer();
		for(MeasureDevCustomer t:tmDevCustomerList){
			t.setType((short)1);
		}
		List<MeasureDevCustomer> meterDevCustomerList = userMapper.getAllMeterDevCustomer();
		for(MeasureDevCustomer m:meterDevCustomerList){
			m.setType((short)2);
		}
		measureDevCustomerList.addAll(tmDevCustomerList);
		measureDevCustomerList.addAll(meterDevCustomerList);
		for(INF_ONLLIERATE o:dRank){
			for(MeasureDevCustomer d:measureDevCustomerList){
				if(d.getMeasureDevId().equals(o.getTerminal_ormeterid())){
					o.setCustomer_name(d.getCustomerName());
					break;
				}
			}
		}
		return dRank;
	}

	private List<INF_TERMINAL_ONLINE_RATE> addFront(String bt,int type){
		if(StringUtils.isBlank(bt)){
			return null;
		}
		List<INF_TERMINAL_ONLINE_RATE> frontOnlines = userMapper.getOnlineByBt(bt,type);
		Map<Long,List<INF_TERMINAL_ONLINE_RATE>> frontByid = new HashMap<>();
		for(int i = 0; i<frontOnlines.size(); i++){
			INF_TERMINAL_ONLINE_RATE f = frontOnlines.get(i);
			if(frontByid.keySet().contains(f.getTerminal_ormeterid())){
				frontByid.get(f.getTerminal_ormeterid()).add(f);
			}else{
				List<INF_TERMINAL_ONLINE_RATE> fList = new ArrayList<>();
				fList.add(f);
				frontByid.put(f.getTerminal_ormeterid(),fList);
			}
		}
		List<INF_TERMINAL_ONLINE_RATE> result = new ArrayList<>();
		for(Long key:frontByid.keySet()){
			if(frontByid.get(key).get(0).getAction_type().equals(1)){
				result.add(frontByid.get(key).get(0));
			}
		}
		return result;
	}

	Map<String,Object> workBookMap = new HashMap<String,Object>();

	/**
	 * exxcel表的导入导出
	 * 
	 * @param request
	 * @param response
	 * @param array
	 * @return
	 */
	public String saveReportDataInMemory(HttpServletRequest request,HttpServletResponse response,JSONArray JSONData){
		try{
			String fileName = "采集终端资料表";
			String sessionId = request.getSession().getId();
			// 每一个单元格的位置及合并信息
			List<TableColumnItem> columnItemList = new ArrayList<TableColumnItem>();
			// int index = -1;
			// 每一列的列宽
			Map<Integer,Integer> columnWidth = new HashMap<Integer,Integer>();
			for(Object data:JSONData){
				// index++;
				@SuppressWarnings("unchecked")
				Map<String,Object> item = (Map<String,Object>)data;
				for(Entry<String,Object> et:item.entrySet()){
					// 数组元素0、1、2、3、4=行下标、列下标、合并行数、合并列数 、是否标题（1为true）
					String[] cell = et.getKey().split("_");
					TableColumnItem columnItem = new TableColumnItem();
					columnItem.setRowIndex(Integer.parseInt(cell[0]));
					columnItem.setCloumnIndex(Integer.parseInt(cell[1]));
					columnItem.setRows(Integer.parseInt(cell[2]));
					columnItem.setCloumns(Integer.parseInt(cell[3]));
					columnItem.isTitle("1".equals(cell[4]));
					columnItem.setValue(et.getValue().toString().equals("\"null\"")?"":et.getValue().toString());
					columnItemList.add(columnItem);
					/*
					 * if (index==0) { fileName=et.getValue().toString(); break;
					 * }
					 */
					// 除头标题外，指定列列宽
					int column = Integer.parseInt(cell[1]);
					Integer size = columnWidth.get(column);
					if(size==null){
						size = et.getValue().toString().length()*2+4;
					}else if(size<(et.getValue().toString().length()*2+4)){
						size = et.getValue().toString().length()*2+4;
					}
					columnWidth.put(column,size);
				}
			}
			Map<String,Object> excelData = new HashMap<String,Object>();
			excelData.put("data",columnItemList);
			excelData.put("columnWidth",columnWidth);
			workBookMap.put(sessionId,excelData);
			return "{\"fileName\":\""+fileName+"\"}";
		}catch(Exception e){
			e.printStackTrace();
		}
		return "{\"error\":\"导出失败\"}";
	}

	/**
	 * 导出报表
	 * 
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	public void exportExcel(HttpServletRequest request,HttpServletResponse response,String fileName){
		try{
			String sessionId = request.getSession().getId();
			Map<String,Object> excelData = (Map<String,Object>)workBookMap.get(sessionId);
			if(excelData!=null){
				List<TableColumnItem> columnItemList = (List<TableColumnItem>)excelData.get("data");
				Map<Integer,Integer> columnWidth = (Map<Integer,Integer>)excelData.get("columnWidth");
				fileName += "("+DateFormatUtils.format(new Date(),"yyyy-MM-dd")+")";
				OutputStream os = response.getOutputStream();// 取得输出流
				response.reset();// 清空输出流
				response.setHeader("Content-disposition","attachment; filename="+new String(fileName.getBytes("utf-8"),"iso8859-1")+".xls");// 设定输出文件头
				response.setContentType("application/msexcel");// 定义输出类型
				WritableWorkbook wbook = Workbook.createWorkbook(os);
				WritableSheet wsheet = wbook.createSheet(fileName,0);
				SheetSettings ss = wsheet.getSettings();
				ss.setDefaultColumnWidth(18);
				// 头标题样式
				// WritableFont titleFont = new
				// WritableFont(WritableFont.createFont("黑体"),24,WritableFont.BOLD,false,jxl.format.UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.BLACK);
				WritableFont titleFont = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,false,jxl.format.UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.BLACK);
				WritableCellFormat titleFormat = new WritableCellFormat(titleFont);
				titleFormat.setAlignment(Alignment.CENTRE);
				titleFormat.setBackground(Colour.WHITE);
				titleFormat.setBorder(Border.ALL,BorderLineStyle.THIN);
				titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
				// 子标题样式
				WritableFont subWfont_1 = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,false,jxl.format.UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.BLACK); // 设置Excel字体
				WritableCellFormat culumnTitleFormat = new WritableCellFormat(subWfont_1);
				culumnTitleFormat.setAlignment(Alignment.CENTRE);
				culumnTitleFormat.setBackground(Colour.WHITE);
				culumnTitleFormat.setBorder(Border.ALL,BorderLineStyle.THIN);
				culumnTitleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
				// WritableFont subWfont_2 = new
				// WritableFont(WritableFont.ARIAL,14,WritableFont.BOLD,false,jxl.format.UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.BLACK);
				// WritableCellFormat subTitleFormat_2 = new
				// WritableCellFormat(subWfont_2);
				// subTitleFormat_2.setBackground(jxl.format.Colour.YELLOW);
				WritableCellFormat wcf = new WritableCellFormat(); // 左对齐样式
				wcf.setAlignment(jxl.format.Alignment.CENTRE);
				wcf.setBorder(Border.ALL,BorderLineStyle.THIN);
				wcf.setBackground(Colour.WHITE);
				wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
				int index = -1;
				for(TableColumnItem item:columnItemList){
					index++;
					// System.out.println(item.getRowIndex()+"_"+item.getCloumnIndex()+"_"+item.getRows()+"_"+item.getCloumns()+":"+item.getValue());
					if(item.getRows()!=1||item.getCloumns()!=1){
						wsheet.mergeCells(item.getCloumnIndex(),item.getRowIndex(),item.getCloumnIndex()+item.getCloumns()-1,item.getRowIndex()+item.getRows()-1);
					}
					if(index!=0){
						if(item.isTitle()){
							wsheet.addCell(new Label(item.getCloumnIndex(),item.getRowIndex(),item.getValue(),culumnTitleFormat));
						}else{
							wsheet.addCell(new Label(item.getCloumnIndex(),item.getRowIndex(),item.getValue(),wcf));
						}
					}else{
						wsheet.addCell(new Label(item.getCloumnIndex(),item.getRowIndex(),item.getValue(),wcf));
						wsheet.setRowView(item.getRowIndex(),600);
					}
				}
				for(Entry<Integer,Integer> width:columnWidth.entrySet()){
					wsheet.setColumnView(width.getKey(),width.getValue());
				}
				wbook.write(); // 写入文件
				wbook.close();
				workBookMap.remove(sessionId);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public List<INF_TERM> getAllTerm(){
		return userMapper.getAllTerms();
	}

	public List<INF_MP> getAllMps(){
		return userMapper.getAllMps();
	}
	/**
	 * 查询queryenterpriseinfo表
	 * @param param
	 * @param limit 
	 * @return
	 */
	public List<Map<String,Object>> queryEnterpriseinfo(String param,String page, boolean limit) {
		boolean exist = StringUtils.isNotBlank(param);
		List<Map<String,Object>> data = userMapper.queryEnterpriseinfo(param,exist,page,limit);
		return data;
	}
	/**
	 * 查询终端信息（id,name）
	 * @param param
	 * @param page 
	 * @return
	 */
	public List<Map<String,Object>> queryTermInfo(String param, String page,boolean limit) {
		boolean exist = StringUtils.isNotBlank(param);
		List<Map<String,Object>> data = userMapper.queryTermInfo(param,exist,page,limit);
		return data;
	}

	public List<Map<String,Object>> queryTransformerInfo(String param,String page,boolean limit) {
		boolean exist = StringUtils.isNotBlank(param);
		List<Map<String,Object>> data = userMapper.queryTransformerInfo(param,exist,page,limit);
		return data;
	}

	public Map<String, Object> queryEnterprise(String param, String page,boolean limit) {
		Map<String,Object> result = new HashMap<>();
		boolean exist = StringUtils.isNotBlank(param);
		List<Map<String,Object>> data = userMapper.queryEnterpriseinfo(param,exist,page,limit);
		int total = userMapper.queryEnterpriseinfoTotal(param,exist);
		result.put("data",data);
		result.put("total",total);
		return result;
	}
	public Map<String, Object> queryTerm(String param, String page,boolean limit) {
		Map<String,Object> result = new HashMap<>();
		boolean exist = StringUtils.isNotBlank(param);
		List<Map<String,Object>> data = userMapper.queryTermInfo(param,exist,page,limit);
		int total = userMapper.queryTermTotal(param,exist);
		result.put("data",data);
		result.put("total",total);
		return result;
	}
	public Map<String, Object> queryTransformer(String param, String page,boolean limit) {
		Map<String,Object> result = new HashMap<>();
		boolean exist = StringUtils.isNotBlank(param);
		List<Map<String,Object>> data = userMapper.queryTransformerInfo(param,exist,page,limit);
		int total = userMapper.queryTransformerTotal(param,exist);
		result.put("data",data);
		result.put("total",total);
		return result;
	}
	/**
	 * 判断记录是否存在
	 * @param amm_protocol_addr 
	 * @param mp_no 
	 * @return
	 */
	public String queryMeterIsExist(String data) {
				Integer isExist = userMapper.queryMeterIsExist(data);
				if(isExist>0){
					return "false";
				}
				return "true";
	}
}
