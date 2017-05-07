package com.bdyc.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User implements Serializable {
	private Integer userId; // 用户ID
	private String username; // 用户名
	private String password; // 密码
	private String deptName; // 单位名称
	private String province; // 省
	private String city; // 市
	private String county; // 县
	private Date createtime; // 创建时间
	private Date updatetime; // 修改时间
	private List<Role> rolelist = new ArrayList<Role>();
	public List<Role> getRolelist() {
		return rolelist;
	}

	public void setRolelist(List<Role> rolelist) {
		this.rolelist = rolelist;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public User(Integer userId, String username, String password,
			String deptName, String province, String city, String county,
			Date createtime, Date updatetime) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.deptName = deptName;
		this.province = province;
		this.city = city;
		this.county = county;
		this.createtime = createtime;
		this.updatetime = updatetime;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

}
