package com.nriet.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;

public class DataBaseUtil {

	/**
	 * Oracle数据库中的数据类型转换为java中的数据类型<br />
	 * 转换规则为<br />
	 * 1.char.varchar2类型转换为java.lang.String <br />
	 * 2.date类型转换为java.util.Date类型 <br />
	 * 3.number类型，若精度为0，尺寸小于等于4转为int，若精度为0，尺寸大于4转为long，若精度大于0转为double <br />
	 * 
	 * @param dbType
	 * @param scale
	 * @param precision
	 * @return
	 */
	public static String dbTypeToJavaType(String dbType, int scale, int precision) {
		dbType = dbType.toLowerCase();
		String javaType = dbType;
		if (dbType.indexOf("char") >= 0) {
			javaType = "java.lang.String";
		}
		if (dbType.indexOf("date") >= 0) {
			javaType = "java.util.Date";
		}
		if (dbType.equals("number")) {
			if (precision == 0) {
				if (scale <= 4)
					javaType = "java.lang.Integer";
				else
					javaType = "java.lang.Long";
			} else
				javaType = "java.lang.Double";
		}
		return javaType;
	}

	/**
	 * 将Oracle中个一张表中的所有字段转为java实体类的源码. <br />
	 * 转换规则:将字段小写作为java类的属性,并生成相应的getter和setter方法
	 * 
	 * @param conn
	 * @param tableName
	 * @param javaName
	 * @return
	 */
	public static String toEntitySource(Connection conn, String tableName, String javaName) {
		StringBuffer sb = new StringBuffer();
		try {
			DatabaseMetaData dbmd = conn.getMetaData();
			ResultSet rs = dbmd.getColumns(null, "%", tableName.toUpperCase(), "%");
			sb.append("public class " + javaName + "{\n");
			while (rs.next()) {
				String columnName = rs.getString("COLUMN_NAME").toLowerCase();
				// 注释
				// String comment = rs.getString("REMARKS");
				// System.out.println(comment);
				String dbType = rs.getString("TYPE_NAME");
				int scale = rs.getInt("COLUMN_SIZE");
				int precision = rs.getInt("DECIMAL_DIGITS");
				// System.out.println(columnName + "===>" + scale + "==>" +
				// precision);
				String columnJavaType = dbTypeToJavaType(dbType, scale, precision);
				sb.append("\tprivate " + columnJavaType + " " + columnName + ";\n");
				sb.append("\tpublic " + columnJavaType + " get" + StringUtil.initCaps(columnName) + "(){\n");
				sb.append("\t\treturn this." + columnName + ";\n");
				sb.append("\t}\n");
				sb.append("\tpublic void set" + StringUtil.initCaps(columnName) + "(" + columnJavaType + " "
						+ columnName + "){\n");
				sb.append("\t\tthis." + columnName + "=" + columnName + ";\n");
				sb.append("\t}\n");
			}
			sb.append("}\n");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		DataSource ds = SpringUtil.getBean("spring-server.xml", DataSource.class);
		Connection conn = null;
		conn = DataSourceUtils.getConnection(ds);
		String result = toEntitySource(conn, "t_war_imd_aws_elewarn", "SignificantWeather");
		System.out.println(result);
		DataSourceUtils.releaseConnection(conn, ds);
		// getComment(conn, "T_JS_OBT_MIN_CLOUD");
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void getComment(Connection conn, String tableName) {
		String sql = "select * from " + tableName + " t where rownum = 1";
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				ResultSetMetaData rsmd = rs.getMetaData();
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					System.out.println(rsmd.getColumnLabel(i + 1));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取表中的每一个字段的java类型
	 * 
	 * @param tableName
	 * @return
	 */
	public static Map<String, String> getTypeMap(String tableName) {
		Map<String, String> fieldTypeMap = new HashMap<String, String>();

		DataSource ds = SpringUtil.getBean("spring-server.xml", DataSource.class);
		Connection conn = null;
		conn = DataSourceUtils.getConnection(ds);
		ResultSet rs = null;
		try {
			DatabaseMetaData dbmd = conn.getMetaData();
			rs = dbmd.getColumns(null, "%", tableName.toUpperCase(), "%");
			while (rs.next()) {
				String columnName = rs.getString("COLUMN_NAME");
				String dbType = rs.getString("TYPE_NAME");
				int scale = rs.getInt("COLUMN_SIZE");
				int precision = rs.getInt("DECIMAL_DIGITS");
				String columnJavaType = dbTypeToJavaType(dbType, scale, precision);
				fieldTypeMap.put(columnName, columnJavaType);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			DataSourceUtils.releaseConnection(conn, ds);
		}

		return fieldTypeMap;
	}
}
