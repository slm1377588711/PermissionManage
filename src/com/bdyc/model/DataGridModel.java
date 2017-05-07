package com.bdyc.model;

import java.util.ArrayList;
import java.util.List;

public class DataGridModel {
	private long total;
	private List rows = new ArrayList();
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
	@Override
	public String toString() {
		return "DataGridModel [total=" + total + ", rows=" + rows + "]";
	}
	public DataGridModel() {
		super();
	}
	public DataGridModel(long total, List rows) {
		super();
		this.total = total;
		this.rows = rows;
	}
	
}
