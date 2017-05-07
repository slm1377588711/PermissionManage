package com.bdyc.util;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;


public class JdbcUtil {
	private static DataSource ds = null;
	static{
		System.out.println("正在加载配置文件。。。。");
		//如果没有configName参数，去类加载路径读取c3p0-config.xml的默认配置 <default-config>
		//configName：<named-config name="mysql">
		ds = new ComboPooledDataSource();
	}
	
	/**获取数据连接池
	 * 
	 * @return
	 */
	public static DataSource getDataSource(){
		return ds;
	}
}
