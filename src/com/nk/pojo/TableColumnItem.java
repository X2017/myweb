package com.nk.pojo;
import java.util.ArrayList;
import java.util.List;

public class TableColumnItem{
	/**
	 * 行下标
	 */
	private Integer rowIndex;
	/**
	 * 列下标
	 */
	private Integer cloumnIndex;
	/**
	 * 合并行数
	 */
	private Integer rows;
	/**
	 * 合并列数
	 */
	private Integer cloumns;
	/**
	 * 单元格内容
	 */
	private String value;
	/**
	 * 是否为标题
	 */
	private boolean isTitle;
	private List<TableColumnItem> subTitleList = new ArrayList<TableColumnItem>();
	
	public Integer getRowIndex(){
		return rowIndex;
	}
	public void setRowIndex(Integer rowIndex){
		this.rowIndex = rowIndex;
	}
	public Integer getCloumnIndex(){
		return cloumnIndex;
	}
	public void setCloumnIndex(Integer cloumnIndex){
		this.cloumnIndex = cloumnIndex;
	}
	public Integer getRows(){
		return rows;
	}
	public void setRows(Integer rows){
		this.rows = rows;
	}
	public Integer getCloumns(){
		return cloumns;
	}
	public void setCloumns(Integer cloumns){
		this.cloumns = cloumns;
	}
	public List<TableColumnItem> getSubTitleList(){
		return subTitleList;
	}
	public void setSubTitleList(List<TableColumnItem> subTitleList){
		this.subTitleList = subTitleList;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public boolean isTitle() {
		return isTitle;
	}
	public void isTitle(boolean isTitle) {
		this.isTitle = isTitle;
	}
	public static void main(String args[]) throws Exception{
//		String filePath = "d:\\testExcel.xls";
//		String subDocTitle = "用水量统计";
		List<TableColumnItem> columnItemList = new ArrayList<TableColumnItem>();
//		int nColumnItemRows = 3; //要保证各个item的总行数相同
		TableColumnItem item1 = new TableColumnItem();
		item1.setRowIndex(0);
		item1.setCloumnIndex(0);
		item1.setRows(1);
		item1.setCloumns(2);
		item1.setValue("第1行第1、2列");
		TableColumnItem subItem11 = new TableColumnItem();
		subItem11.setRowIndex(1);
		subItem11.setCloumnIndex(0);
		subItem11.setRows(2);
		subItem11.setCloumns(1);
		subItem11.setValue("第2、3行第1列");
		TableColumnItem subItem12 = new TableColumnItem();
		subItem12.setRowIndex(1);
		subItem12.setCloumnIndex(1);
		subItem12.setRows(2);
		subItem12.setCloumns(1);
		subItem12.setValue("第2、3行第2列");
		item1.getSubTitleList().add(subItem11);
		item1.getSubTitleList().add(subItem12);
		
		TableColumnItem item2 = new TableColumnItem();
		item2.setRowIndex(0);
		item2.setCloumnIndex(2);
		item2.setRows(2);
		item2.setCloumns(2);
		item2.setValue("第1、2行第3、4列");
		TableColumnItem subItem21 = new TableColumnItem();
		subItem21.setRowIndex(2);
		subItem21.setCloumnIndex(2);
		subItem21.setRows(1);
		subItem21.setCloumns(1);
		subItem21.setValue("第3行第3列");
		TableColumnItem subItem22 = new TableColumnItem();
		subItem22.setRowIndex(2);
		subItem22.setCloumnIndex(3);
		subItem22.setRows(1);
		subItem22.setCloumns(1);
		subItem22.setValue("第3行第4列");
		item2.getSubTitleList().add(subItem21);
		item2.getSubTitleList().add(subItem22);
		
		columnItemList.add(item1);
		columnItemList.add(item2);
		
		List<String> row1 = new ArrayList<String>();
		row1.add("aaaa");
		row1.add("bbbb");
		row1.add("cccc");
		row1.add("dddd");
		List<String> row2 = new ArrayList<String>();
		row2.add("eeee");
		row2.add("ffff");
		row2.add("gggg");
		row2.add("hhhh");
		List<String> row3 = new ArrayList<String>();
		row3.add("jjjj");
		row3.add("kkkk");
		row3.add("llll");
		row3.add("mmmm");
		List<List<String>> rowDataList = new ArrayList<List<String>>();
		rowDataList.add(row1);
		rowDataList.add(row2);
		rowDataList.add(row3);
		
//		SharpUtil.exportExcel(filePath,subDocTitle,columnItemList,nColumnItemRows,rowDataList);
	}
}

