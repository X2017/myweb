package com.nk.pojo;

import java.io.Serializable;
import java.util.Date;

public class INF_TRANSFORMER implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer transformer_id;
	private String transformer_no;
	private String transformer_name;
	private Integer customer_id;
	private String transformer_model;
	private Integer transformer_capacity;
	private Float rated_voltage;
	private Float rated_current;
	private Float rated_frequency;
	private Float noload_current;
	private Float noload_loss;
	private Float load_loss;
	private Float short_ci;
	private String serial_num;
	private String max_vol;
	private String min_vol;
	private String transformer_status;
	private Byte is_valid;
	private String create_man;
	private Date created_date; 
	private String transformer_asset_no;
	private String transformer_manufacturer;
	private String remarks;
	private Integer term_manufacturer;
	public Integer getTransformer_id() {
		return transformer_id;
	}
	public void setTransformer_id(Integer transformer_id) {
		this.transformer_id = transformer_id;
	}
	public String getTransformer_no() {
		return transformer_no;
	}
	public void setTransformer_no(String transformer_no) {
		this.transformer_no = transformer_no;
	}
	public String getTransformer_name() {
		return transformer_name;
	}
	public void setTransformer_name(String transformer_name) {
		this.transformer_name = transformer_name;
	}
	public Integer getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}
	public String getTransformer_model() {
		return transformer_model;
	}
	public void setTransformer_model(String transformer_model) {
		this.transformer_model = transformer_model;
	}
	public Integer getTransformer_capacity() {
		return transformer_capacity;
	}
	public void setTransformer_capacity(Integer transformer_capacity) {
		this.transformer_capacity = transformer_capacity;
	}
	public Float getRated_voltage() {
		return rated_voltage;
	}
	public void setRated_voltage(Float rated_voltage) {
		this.rated_voltage = rated_voltage;
	}
	public Float getRated_current() {
		return rated_current;
	}
	public void setRated_current(Float rated_current) {
		this.rated_current = rated_current;
	}
	public Float getRated_frequency() {
		return rated_frequency;
	}
	public void setRated_frequency(Float rated_frequency) {
		this.rated_frequency = rated_frequency;
	}
	public Float getNoload_current() {
		return noload_current;
	}
	public void setNoload_current(Float noload_current) {
		this.noload_current = noload_current;
	}
	public Float getNoload_loss() {
		return noload_loss;
	}
	public void setNoload_loss(Float noload_loss) {
		this.noload_loss = noload_loss;
	}
	public Float getLoad_loss() {
		return load_loss;
	}
	public void setLoad_loss(Float load_loss) {
		this.load_loss = load_loss;
	}
	public Float getShort_ci() {
		return short_ci;
	}
	public void setShort_ci(Float short_ci) {
		this.short_ci = short_ci;
	}
	public String getSerial_num() {
		return serial_num;
	}
	public void setSerial_num(String serial_num) {
		this.serial_num = serial_num;
	}
	public String getMax_vol() {
		return max_vol;
	}
	public void setMax_vol(String max_vol) {
		this.max_vol = max_vol;
	}
	public String getMin_vol() {
		return min_vol;
	}
	public void setMin_vol(String min_vol) {
		this.min_vol = min_vol;
	}
	public String getTransformer_status() {
		return transformer_status;
	}
	public void setTransformer_status(String transformer_status) {
		this.transformer_status = transformer_status;
	}
	public Byte getIs_valid() {
		return is_valid;
	}
	public void setIs_valid(Byte is_valid) {
		this.is_valid = is_valid;
	}
	public String getCreate_man() {
		return create_man;
	}
	public void setCreate_man(String create_man) {
		this.create_man = create_man;
	}
	public Date getCreated_date() {
		return created_date;
	}
	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}
	public String getTransformer_asset_no() {
		return transformer_asset_no;
	}
	public void setTransformer_asset_no(String transformer_asset_no) {
		this.transformer_asset_no = transformer_asset_no;
	}
	public String getTransformer_manufacturer() {
		return transformer_manufacturer;
	}
	public void setTransformer_manufacturer(String transformer_manufacturer) {
		this.transformer_manufacturer = transformer_manufacturer;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getTerm_manufacturer() {
		return term_manufacturer;
	}
	public void setTerm_manufacturer(Integer term_manufacturer) {
		this.term_manufacturer = term_manufacturer;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
