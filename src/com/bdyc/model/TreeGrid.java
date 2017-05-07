package com.bdyc.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
public class TreeGrid {

	/*id：节点ID，对加载远程数据很重要。
	text：显示节点文本。
	state：节点状态，'open' 或 'closed'，默认：'open'。如果为'closed'的时候，将不自动展开该节点。
	checked：表示该节点是否被选中。
	attributes: 被添加到节点的自定义属性。
	children: 一个节点数组声明了若干节点。*/
	private Integer id;		//节点ID，对加载远程数据很重要。
	private String text;	//显示节点文本
	private String state;	//open:closed
	private Boolean checked;//表示该节点是否被选中。
	private String iconCls;	//图标
	private Map<String,Object> attributes = new HashMap<String, Object>();
	private List<TreeGrid> children = new ArrayList<TreeGrid>();
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public List<TreeGrid> getChildren() {
		return children;
	}

	public void setChildren(List<TreeGrid> children) {
		this.children = children;
	}

	public TreeGrid(Integer id, String text, String state, Boolean checked,
			Map<String, Object> attributes, List<TreeGrid> children) {
		super();
		this.id = id;
		this.text = text;
		this.state = state;
		this.checked = checked;
		this.attributes = attributes;
		this.children = children;
	}

	public TreeGrid() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
