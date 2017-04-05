package com.nk.vo;

import java.io.Serializable;

public class INF_TRANSFORMER_VO  implements Serializable{
	private static final long serialVersionUID = 1L;
	private String transformer_id;
	private String transformer_no;
	private String transformer_name;
	private String customer_id;
	private String transformer_model;
	private String transformer_capacity;
	private String rated_voltage;
	private String rated_current;
	private String rated_frequency;
	private String noload_current;
	private String noload_loss;
	private String load_loss;
	private String short_ci;
	private String serial_num;
	private String max_vol;
	private String min_vol;
	private String transformer_status;
	private String is_valid;
	private String create_man;
	private String created_date; 
	private String transformer_asset_no;
	private String transformer_manufacturer;
	private String remarks;
	private String term_manufacturer;
	
	public String getTransformer_id() {
		return transformer_id;
	}

	public void setTransformer_id(String transformer_id) {
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

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public String getTransformer_model() {
		return transformer_model;
	}

	public void setTransformer_model(String transformer_model) {
		this.transformer_model = transformer_model;
	}

	public String getTransformer_capacity() {
		return transformer_capacity;
	}

	public void setTransformer_capacity(String transformer_capacity) {
		this.transformer_capacity = transformer_capacity;
	}

	public String getRated_voltage() {
		return rated_voltage;
	}

	public void setRated_voltage(String rated_voltage) {
		this.rated_voltage = rated_voltage;
	}

	public String getRated_current() {
		return rated_current;
	}

	public void setRated_current(String rated_current) {
		this.rated_current = rated_current;
	}

	public String getRated_frequency() {
		return rated_frequency;
	}

	public void setRated_frequency(String rated_frequency) {
		this.rated_frequency = rated_frequency;
	}

	public String getNoload_current() {
		return noload_current;
	}

	public void setNoload_current(String noload_current) {
		this.noload_current = noload_current;
	}

	public String getNoload_loss() {
		return noload_loss;
	}

	public void setNoload_loss(String noload_loss) {
		this.noload_loss = noload_loss;
	}

	public String getLoad_loss() {
		return load_loss;
	}

	public void setLoad_loss(String load_loss) {
		this.load_loss = load_loss;
	}

	public String getShort_ci() {
		return short_ci;
	}

	public void setShort_ci(String short_ci) {
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

	public String getIs_valid() {
		return is_valid;
	}

	public void setIs_valid(String is_valid) {
		this.is_valid = is_valid;
	}

	public String getCreate_man() {
		return create_man;
	}

	public void setCreate_man(String create_man) {
		this.create_man = create_man;
	}

	public String getCreated_date() {
		return created_date;
	}

	public void setCreated_date(String created_date) {
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

	public String getTerm_manufacturer() {
		return term_manufacturer;
	}

	public void setTerm_manufacturer(String term_manufacturer) {
		this.term_manufacturer = term_manufacturer;
	}

	@Override
	public String toString() {
		return "INF_TRANSFORMER_VO [transformer_id=" + transformer_id
				+ ", transformer_no=" + transformer_no + ", transformer_name="
				+ transformer_name + ", customer_id=" + customer_id
				+ ", transformer_model=" + transformer_model
				+ ", transformer_capacity=" + transformer_capacity
				+ ", rated_voltage=" + rated_voltage + ", rated_current="
				+ rated_current + ", rated_frequency=" + rated_frequency
				+ ", noload_current=" + noload_current + ", noload_loss="
				+ noload_loss + ", load_loss=" + load_loss + ", short_ci="
				+ short_ci + ", serial_num=" + serial_num + ", max_vol="
				+ max_vol + ", min_vol=" + min_vol + ", transformer_status="
				+ transformer_status + ", is_valid=" + is_valid
				+ ", create_man=" + create_man + ", created_date="
				+ created_date + ", transformer_asset_no="
				+ transformer_asset_no + ", transformer_manufacturer="
				+ transformer_manufacturer + ", remarks=" + remarks
				+ ", term_manufacturer=" + term_manufacturer + "]";
	}
	
}
