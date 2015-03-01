package org.tww.hibernate;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

/**
 * 从hibernate的配置文件中导出DDL语句
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings("deprecation")
public class ExportToDDL {

	public static void exportByConfiguration(boolean isPrintOnConsole,
			boolean isExecute) {
		export(new Configuration().configure(), isPrintOnConsole, isExecute);
	}

	public static void exportByAnnotationConfiguration(
			boolean isPrintOnConsole, boolean isExecute) {
		export(new AnnotationConfiguration().configure(), isPrintOnConsole,
				isExecute);
	}

	private static void export(Configuration configuration,
			boolean isPrintOnConsole, boolean isExecute) {
		SchemaExport export = new SchemaExport(configuration);
		export.create(isPrintOnConsole, isExecute);
	}

	public static void main(String[] args) {
		exportByAnnotationConfiguration(true, false);
	}

}
