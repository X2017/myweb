package com.nk.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.nk.pojo.DI_DEF;
import com.nk.pojo.FrontAndTerm;
import com.nk.pojo.IF_INF_MAINTAIN;
import com.nk.pojo.INF_ALARM_RECORD;
import com.nk.pojo.INF_CUSTOMER;
import com.nk.pojo.INF_FRONT_MACHINE;
import com.nk.pojo.INF_MANUFACTURER;
import com.nk.pojo.INF_MP;
import com.nk.pojo.INF_TERM;
import com.nk.pojo.INF_TERMINAL_ACCOUNT;
import com.nk.pojo.INF_TERMINAL_MANAGEMENT;
import com.nk.pojo.INF_TERMINAL_ONLINE_RATE;
import com.nk.pojo.INF_TRANSFORMER;
import com.nk.pojo.MeasureDevCustomer;
import com.nk.pojo.TASK_RECORD;
import com.nk.pojo.TASK_TEMPLATE;
import com.nk.vo.INF_TERM_VO;


public interface ArchiveMapper{
	
	public int test();
	public List<String> getAllTemplateId();
	public int queryFrontMachineByCode(String code);
	public List<INF_MP> getAllInfMp();
	public List<TASK_TEMPLATE> getTaskTemplateById(String id);
	
	public int getCustomerTotal(Map<String, Object> param);
	public List<INF_CUSTOMER> getPageCustomer(Map<String, Object> param);
	public void addCustomer(INF_CUSTOMER customer);
	public void updateCustomer(INF_CUSTOMER customer);
	public void delCustomer(@Param("delCustomerIdArray")int delCustomerIdArray[]);
	
	public int getTerminalTotal(Map<String, Object> param);
	public List<INF_TERM> getPageTerminal(Map<String, Object> param);
	public List<INF_TERM> getTermByaddrAndCustId(@Param("addr")String term_addr,
			@Param("id")Integer customer_id);
	public void addTerminal(INF_TERM terminal);
	public void updateTerminal(INF_TERM terminal);
	public void delTerminal(@Param("delTerminalIdArray")int delTerminalIdArray[]);
	public List<INF_CUSTOMER> getCustomepList();
	public void addTermBat(@Param("terms")List<INF_TERM> aterm);
	public void updateTermBat(@Param("terms")List<INF_TERM_VO> updterm);
	
	public int getMeterTotal(Map<String,Object> param);
	public List<INF_MP> getPageMeter(Map<String,Object> param);
	public INF_MP getMeterBympNo(String mp_no);
	public void addMeter(INF_MP meter);
	public void updateMeter(INF_MP meter);
	public void delMeter(@Param("delMeterIdArray")int delMeterIdArray[]);
	
	public int getDiDefTotal(Map<String, Object> param);
	public List<DI_DEF> getPageDiDef(Map<String, Object> param);
	public void addDiDef(DI_DEF diDef);
	public void updateDiDef(DI_DEF diDef);
	public void delDiDef(@Param("delDiDefIdArray")String delDiDefIdArray[]);
	
	public int getTaskTemplateTotal(Map<String, Object> param);
	public List<TASK_TEMPLATE> getPageTaskTemplate(Map<String, Object> param);
	public void addTaskTemplate(TASK_TEMPLATE taskTemplate);
	public void updateTaskTemplate(TASK_TEMPLATE taskTemplate);
	public void delTaskTemplate(@Param("delTaskTemplateIdArray")int delTaskTemplateIdArray[]);
	
	public int getTaskRecordTotal(Map<String, Object> param);
	public List<TASK_RECORD> getPageTaskRecord(Map<String, Object> param);
	public void addTaskRecord(TASK_RECORD taskRecord);
	public void updateTaskRecord(TASK_RECORD taskRecord);
	public void delTaskRecord(@Param("delTaskRecordIdArray")int delTaskRecordIdArray[]);

	public int getDeviceFactoryManagementTotal(Map<String, Object> param);
	public List<INF_MANUFACTURER> getDeviceFactoryManagement(Map<String, Object> param);
	public void addManufacturer(INF_MANUFACTURER inf_manufacturer);
	public void updateManufacturer(INF_MANUFACTURER inf_manufacturer);
	public void delManufacturer(@Param("delIdArrayInt")int[] delIdArrayInt);

	public int getDeviceOnlineRateManagementTotal(Map<String, Object> param);
	public List<INF_TERMINAL_ONLINE_RATE> getDeviceOnlineRateManagement(
			Map<String, Object> param);
	public void addTerminaLonlineRate(
			INF_TERMINAL_ONLINE_RATE inf_terminal_online_rate);
	public void updateTerminaLonlineRate(
			INF_TERMINAL_ONLINE_RATE inf_terminal_online_rate);
	public void delTerminaLonlineRate(@Param("delIdArrayInt")int[] delIdArrayInt);

	public List<DI_DEF> autoDef();

	public int getFrontMachineTotal(Map<String, Object> param);
	public List<INF_FRONT_MACHINE> getFrontMachine(Map<String, Object> param);
	public INF_FRONT_MACHINE getFrontByCode(String front_code);
	public void addFrontMachine(INF_FRONT_MACHINE inf_front_machine); 
	public void updateFrontMachine(INF_FRONT_MACHINE inf_front_machine);
	public void deleteFrontMachine(@Param("delIdArrayInt")int[] delIdArrayInt);
	
	public int getTerminalAccountTotal(Map<String, Object> param);
	public List<INF_TERMINAL_ACCOUNT> getTerminalAccount(
			Map<String, Object> param);
	public void addTerminalAccount(INF_TERMINAL_ACCOUNT inf_terminal_account);
	public void updateTerminalAccount(INF_TERMINAL_ACCOUNT inf_terminal_account);
	public void deleteTerminalAccount(@Param("delIdArrayInt")int[] delIdArrayInt);
	public List<INF_TERM> getAllTerminal();

	public int getTransformerTotal(Map<String, Object> param);
	public List<INF_TRANSFORMER> getTransformer(Map<String, Object> param);
	public void addTransformer(INF_TRANSFORMER inf_transformer);
	public void updateTransformer(INF_TRANSFORMER inf_transformer);
	public void deleteTransformer(@Param("delIdArrayInt")int[] delIdArrayInt);
	
	int getTerminalInstallRunDropManagementTotal(Map<String, Object> param);
	List<INF_TERMINAL_MANAGEMENT> getTerminalInstallRunDropManagement(
			Map<String, Object> param);
	void addTerminalInstallRunDropManagement(INF_TERMINAL_MANAGEMENT t);
	void updateTerminalInstallRunDropManagement(INF_TERMINAL_MANAGEMENT i);
	void deleteTerminalInstallRunDropManagement(@Param("delIdArray")int[] delIdArrayInt);

	


	
	int getDeviceRepairPreWarnManagementTotal(Map<String, Object> param);
	List<INF_ALARM_RECORD> getDeviceRepairPreWarnManagement(Map<String, Object> param);
	void addDeviceRepairPreWarnManagement(INF_ALARM_RECORD t);
	void deleteDeviceRepairPreWarnManagement(@Param("delIdArray")int[] delIdArrayInt);
	void updateDeviceRepairPreWarnManagement(INF_ALARM_RECORD i);
	
	public int existInfMp(String mp_id);
	public int existInfTerm(String term_id);
	
	public int getDeviceRepairManagementTotal(Map<String, Object> param);
	public List<IF_INF_MAINTAIN> getDeviceRepairManagement(
			Map<String, Object> param);
	public void addDeviceRepairManagement(IF_INF_MAINTAIN i);
	public void updateDeviceRepairManagement(IF_INF_MAINTAIN i);
	public void deleteDeviceRepairManagement(@Param("delIdArray")int[] delIdArrayInt);
	
	
	
	
	public List<INF_TERMINAL_ONLINE_RATE> getTerminalOnline();
	public List<INF_TERMINAL_ACCOUNT> getTerminalAccounts();
	public INF_FRONT_MACHINE getFrontMachineByterminal_ormeterid(
			Integer terminal_ormeterid);
	public List<INF_TERMINAL_ONLINE_RATE> getTerminalOnlineRateByTERMINAL_ORMETERID(@Param("id")
			Integer id);
	public List<INF_TERMINAL_ONLINE_RATE> getTerminalOnlineRateByDate(
			@Param("id")Integer front_id,@Param("date") Date happen_time);
	public List<INF_TERMINAL_ONLINE_RATE> getFrontOnlineOnlineRateByTERMINAL_ORMETERID(
			Integer front_id);
	
	public List<INF_TERM> getAllTerms();
	public List<INF_MP> getAllMps();
	
	//-------------------厂家排行率（新）--------------------------------------
	public List<INF_MANUFACTURER> getAllManufacturer();
	public List<INF_TERM> getAllTermByManufacturer();
	public List<FrontAndTerm> getAllFrontAndTerm(@Param("ids")long[] ids);
	public List<INF_TERMINAL_ONLINE_RATE> getFrontOnlineRateByDate(@Param("ids")int[] ids,
			@Param("begin")String beginTime, @Param("end")String endTime);
	public List<INF_TERMINAL_ONLINE_RATE> getTremsOnlineRateByDate(@Param("ids")long[] ids,
			@Param("begin")String beginTime, @Param("end")String endTime);
	
	public List<MeasureDevCustomer> getAllTerminalDevCustomer();
	public List<MeasureDevCustomer> getAllMeterDevCustomer();

//	public INF_MANUFACTURER getManufacturerByTypeAndId(@Param("type")Integer type,@Param("id")Integer id);
//	public List<INF_TERM> getTermBymanufacturer(String manufacturer_name);
//	public List<FrontAndTerm> getFrontAndTermBytermId(@Param("ids")int[] ids);
	
	
	public List<INF_FRONT_MACHINE> getFrontMachineByonline();
	public List<INF_TERMINAL_ONLINE_RATE> getFrontOnlineOnlineRateByDates(
				@Param("begin") String beginTime, 	@Param("end")String endTime);
	public List<INF_TERMINAL_ONLINE_RATE> getTerminalOnlineRateByDates(
			@Param("begin") String beginTime, 	@Param("end")String endTime);
	public List<INF_TERMINAL_ONLINE_RATE> getOnlineByBt(@Param("begin")String bt,@Param("type") int type);
	public List<Map<String, Object>> queryEnterpriseinfo(@Param("param")String param,@Param("exist") boolean exist,@Param("page") String page, @Param("limit")boolean limit);
	public List<Map<String, Object>> queryTermInfo(@Param("param")String param,@Param("exist") boolean exist,@Param("page") String page,@Param("limit")boolean limit);
	public List<Map<String, Object>> queryTransformerInfo(@Param("param")String param,@Param("exist") boolean exist,@Param("page") String page,@Param("limit")boolean limit);
	public int queryEnterpriseinfoTotal(@Param("param")String param,@Param("exist") boolean exist);
	public int queryTermTotal(@Param("param")String param,@Param("exist") boolean exist);
	public int queryTransformerTotal(@Param("param")String param,@Param("exist") boolean exist);
	/**
	 * 判断设备台账信息是否存在
	 * @param id
	 * @param name
	 * @return
	 */
	public int queryTerminalAccountIsExist(@Param("id")Long id,@Param("name") String name);
	public int queryTransformerIsExist(@Param("id")Integer transformer_id);
	public Integer queryMeterIsExist(@Param("data")String data);
	


	
	
}







