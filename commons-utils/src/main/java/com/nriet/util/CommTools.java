package com.nriet.util;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommTools {
	private static List<String> lst = new ArrayList<String>();

	@SuppressWarnings("unchecked")
	public static <T> T getValue(String str, Object f) {
		Field[] fields = f.getClass().getDeclaredFields();
		for (int i = 0, len = fields.length; i < len; i++) {
			if (str.equals(fields[i].getName())) {
				try {
					// fields[i].setFloat(f, 1.1f);
					return (T) fields[i].get(f);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		Object o = fields.length;
		return (T) o;
	}

	public static List<String> getList(Object f) {
		Field[] fields = f.getClass().getDeclaredFields();

		for (int i = 0, len = fields.length; i < len; i++) {
			try {
				// try {
				// System.out.println("l."+fields[i].getName()+"="+fields[i].get(f));
				// } catch (IllegalAccessException e) {
				// e.printStackTrace();
				// }
				lst.add(fields[i].getName());
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
		return lst;
	}

	@SuppressWarnings("unchecked")
	public static <T> T setValue(Object str) {
		try {
			Object o = str;
			return (T) o;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return (T) null;
		}
	}

	/*
	 * 将对象f中的所有值赋值给ps，适用于所有字段均需要入库的情况
	 */
	public static void setAllps(Object f, PreparedStatement ps) {
		Field[] fields = f.getClass().getDeclaredFields();
		try {
			for (int i = 0, len = fields.length; i < len; i++) {
				if ("int".equals(fields[i].getType().getName())) {
					ps.setInt(i + 1, (Integer) fields[i].get(f));
				} else if ("java.lang.String".equals(fields[i].getType().getName())) {
					ps.setString(i + 1, (String) fields[i].get(f));
				} else if ("boolean".equals(fields[i].getType().getName())) {
					ps.setBoolean(i + 1, (Boolean) fields[i].get(f));
				} else if ("java.lang.Float".equals(fields[i].getType().getName())) {
					ps.setFloat(i + 1, (Float) fields[i].get(f));
				} else if ("long".equals(fields[i].getType().getName())) {
					ps.setLong(i + 1, (Long) fields[i].get(f));
				} else if ("double".equals(fields[i].getType().getName())) {
					ps.setDouble(i + 1, (Double) fields[i].get(f));
				} else if ("java.util.Date".equals(fields[i].getType().getName())) {
					java.util.Date d = (java.util.Date) fields[i].get(f);
					ps.setTimestamp(i + 1, new java.sql.Timestamp(d.getTime()));
				} else {
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 对象f为全要素结构，将对象t中的值赋值给ps，适用于所有字段均需要入库的情况
	 */
	public static void setAnotherps(Object f, Object t, PreparedStatement ps) {
		Field[] fields = f.getClass().getDeclaredFields();
		Field[] fieldt = t.getClass().getDeclaredFields();
		try {
			for (int j = 0, lenj = fieldt.length; j < lenj; j++)
				for (int i = j, len = fields.length; i < len; i++) {
					if (fieldt[j].getName() == fields[i].getName()) {
						if ("int".equals(fields[i].getType().getName())) {
							ps.setInt(j + 1, (Integer) fields[i].get(f));
							continue;
						} else if ("java.lang.String".equals(fields[i].getType().getName())) {
							ps.setString(j + 1, (String) fields[i].get(f));
							continue;
						} else if ("boolean".equals(fields[i].getType().getName())) {
							ps.setBoolean(j + 1, (Boolean) fields[i].get(f));
							continue;
						} else if ("java.lang.Float".equals(fields[i].getType().getName())) {
							ps.setFloat(j + 1, (Float) fields[i].get(f));
							continue;
						} else if ("long".equals(fields[i].getType().getName())) {
							ps.setLong(j + 1, (Long) fields[i].get(f));
							continue;
						} else if ("double".equals(fields[i].getType().getName())) {
							ps.setDouble(j + 1, (Double) fields[i].get(f));
							continue;
						} else if ("java.util.Date".equals(fields[i].getType().getName())) {
							Date d = (Date) fields[i].get(f);
							ps.setTimestamp(j + 1, new Timestamp(d.getTime()));
							continue;
						} else {
						}
					}
				}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}