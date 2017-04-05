package com.nk.vo;

import java.io.Serializable;

public class INF_MP_VO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String mp_id;
	private String mp_no;
	private String comm_no;
	private String mp_name;
	private String mp_type;
	private String industry_type;
	private String mp_class;
	private String main_mp_id;
	private String ct;
	private String pt;
	
	private String ct_pt;
	private String create_man;
	private String create_date;
	private String mp_status;
	private String mp_asset_no;
	
	private String amm_protocol_addr;
	private String amm_protocol;
	private String rated_voltage;
	private String rated_current;
	private String amm_class;
	
	private String amm_manufacturer;
	private String amm_type;
	private String mp_addr;
	private String phase;
	private String customer_id;
	private String customer_name;
	private String term_id;
	private String transformer_id;
	private String remarks;
	private String standard_factor_s;
	private String mp_p_e;
	
	private String dl_ti;
	private String equipment_no;
	private String sys_no;
	private String group_id;
	private String is_main_obj;
	private String check_demand;
	private String is_public;
	private String imgPath;
	
	
	public INF_MP_VO() {
		super();
	}
	public INF_MP_VO(String mp_id, String mp_no, String comm_no,
			String mp_name, String mp_type, String industry_type,
			String mp_class, String main_mp_id, String ct, String pt,
			String ct_pt, String create_man, String create_date,
			String mp_status, String mp_asset_no, String amm_protocol_addr,
			String amm_protocol, String rated_voltage, String rated_current,
			String amm_class, String amm_manufacturer, String amm_type,
			String mp_addr, String phase, String customer_id, String term_id,
			String transformer_id, String remarks, String standard_factor_s,
			String mp_p_e, String dl_ti, String equipment_no, String sys_no,
			String group_id, String is_main_obj, String check_demand,
			String is_public, String imgPath) {
		super();
		this.mp_id = mp_id;
		this.mp_no = mp_no;
		this.comm_no = comm_no;
		this.mp_name = mp_name;
		this.mp_type = mp_type;
		this.industry_type = industry_type;
		this.mp_class = mp_class;
		this.main_mp_id = main_mp_id;
		this.ct = ct;
		this.pt = pt;
		this.ct_pt = ct_pt;
		this.create_man = create_man;
		this.create_date = create_date;
		this.mp_status = mp_status;
		this.mp_asset_no = mp_asset_no;
		this.amm_protocol_addr = amm_protocol_addr;
		this.amm_protocol = amm_protocol;
		this.rated_voltage = rated_voltage;
		this.rated_current = rated_current;
		this.amm_class = amm_class;
		this.amm_manufacturer = amm_manufacturer;
		this.amm_type = amm_type;
		this.mp_addr = mp_addr;
		this.phase = phase;
		this.customer_id = customer_id;
		this.term_id = term_id;
		this.transformer_id = transformer_id;
		this.remarks = remarks;
		this.standard_factor_s = standard_factor_s;
		this.mp_p_e = mp_p_e;
		this.dl_ti = dl_ti;
		this.equipment_no = equipment_no;
		this.sys_no = sys_no;
		this.group_id = group_id;
		this.is_main_obj = is_main_obj;
		this.check_demand = check_demand;
		this.is_public = is_public;
		this.imgPath = imgPath;
	}
	
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public String getMp_id(){
		return mp_id;
	}
	public void setMp_id(String mp_id){
		this.mp_id = mp_id;
	}
	public String getMp_no(){
		return mp_no;
	}
	public void setMp_no(String mp_no){
		this.mp_no = mp_no;
	}
	public String getComm_no(){
		return comm_no;
	}
	public void setComm_no(String comm_no){
		this.comm_no = comm_no;
	}
	public String getMp_name(){
		return mp_name;
	}
	public void setMp_name(String mp_name){
		this.mp_name = mp_name;
	}
	public String getMp_type(){
		return mp_type;
	}
	public void setMp_type(String mp_type){
		this.mp_type = mp_type;
	}
	public String getIndustry_type(){
		return industry_type;
	}
	public void setIndustry_type(String industry_type){
		this.industry_type = industry_type;
	}
	public String getMp_class(){
		return mp_class;
	}
	public void setMp_class(String mp_class){
		this.mp_class = mp_class;
	}
	public String getMain_mp_id(){
		return main_mp_id;
	}
	public void setMain_mp_id(String main_mp_id){
		this.main_mp_id = main_mp_id;
	}
	public String getCt(){
		return ct;
	}
	public void setCt(String ct){
		this.ct = ct;
	}
	public String getPt(){
		return pt;
	}
	public void setPt(String pt){
		this.pt = pt;
	}
	public String getCt_pt(){
		return ct_pt;
	}
	public void setCt_pt(String ct_pt){
		this.ct_pt = ct_pt;
	}
	public String getCreate_man(){
		return create_man;
	}
	public void setCreate_man(String create_man){
		this.create_man = create_man;
	}
	public String getCreate_date(){
		return create_date;
	}
	public void setCreate_date(String create_date){
		this.create_date = create_date;
	}
	public String getMp_status(){
		return mp_status;
	}
	public void setMp_status(String mp_status){
		this.mp_status = mp_status;
	}
	public String getMp_asset_no(){
		return mp_asset_no;
	}
	public void setMp_asset_no(String mp_asset_no){
		this.mp_asset_no = mp_asset_no;
	}
	public String getAmm_protocol_addr(){
		return amm_protocol_addr;
	}
	public void setAmm_protocol_addr(String amm_protocol_addr){
		this.amm_protocol_addr = amm_protocol_addr;
	}
	public String getAmm_protocol(){
		return amm_protocol;
	}
	public void setAmm_protocol(String amm_protocol){
		this.amm_protocol = amm_protocol;
	}
	public String getRated_voltage(){
		return rated_voltage;
	}
	public void setRated_voltage(String rated_voltage){
		this.rated_voltage = rated_voltage;
	}
	public String getRated_current(){
		return rated_current;
	}
	public void setRated_current(String rated_current){
		this.rated_current = rated_current;
	}
	public String getAmm_class(){
		return amm_class;
	}
	public void setAmm_class(String amm_class){
		this.amm_class = amm_class;
	}
	public String getAmm_manufacturer(){
		return amm_manufacturer;
	}
	public void setAmm_manufacturer(String amm_manufacturer){
		this.amm_manufacturer = amm_manufacturer;
	}
	public String getAmm_type(){
		return amm_type;
	}
	public void setAmm_type(String amm_type){
		this.amm_type = amm_type;
	}
	public String getMp_addr(){
		return mp_addr;
	}
	public void setMp_addr(String mp_addr){
		this.mp_addr = mp_addr;
	}
	public String getPhase(){
		return phase;
	}
	public void setPhase(String phase){
		this.phase = phase;
	}
	public String getCustomer_id(){
		return customer_id;
	}
	public void setCustomer_id(String customer_id){
		this.customer_id = customer_id;
	}
	public String getTerm_id(){
		return term_id;
	}
	public void setTerm_id(String term_id){
		this.term_id = term_id;
	}
	public String getTransformer_id(){
		return transformer_id;
	}
	public void setTransformer_id(String transformer_id){
		this.transformer_id = transformer_id;
	}
	public String getRemarks(){
		return remarks;
	}
	public void setRemarks(String remarks){
		this.remarks = remarks;
	}
	public String getStandard_factor_s(){
		return standard_factor_s;
	}
	public void setStandard_factor_s(String standard_factor_s){
		this.standard_factor_s = standard_factor_s;
	}
	public String getMp_p_e(){
		return mp_p_e;
	}
	public void setMp_p_e(String mp_p_e){
		this.mp_p_e = mp_p_e;
	}
	public String getDl_ti(){
		return dl_ti;
	}
	public void setDl_ti(String dl_ti){
		this.dl_ti = dl_ti;
	}
	public String getEquipment_no(){
		return equipment_no;
	}
	public void setEquipment_no(String equipment_no){
		this.equipment_no = equipment_no;
	}
	public String getSys_no(){
		return sys_no;
	}
	public void setSys_no(String sys_no){
		this.sys_no = sys_no;
	}
	public String getGroup_id(){
		return group_id;
	}
	public void setGroup_id(String group_id){
		this.group_id = group_id;
	}
	public String getIs_main_obj(){
		return is_main_obj;
	}
	public void setIs_main_obj(String is_main_obj){
		this.is_main_obj = is_main_obj;
	}
	public String getCheck_demand(){
		return check_demand;
	}
	public void setCheck_demand(String check_demand){
		this.check_demand = check_demand;
	}
	public String getIs_public(){
		return is_public;
	}
	public void setIs_public(String is_public){
		this.is_public = is_public;
	}
	public String getImgPath(){
		return imgPath;
	}
	public void setImgPath(String imgPath){
		this.imgPath = imgPath;
	}
}





