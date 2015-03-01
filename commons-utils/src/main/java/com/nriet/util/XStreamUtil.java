package com.nriet.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * xstream辅助�?
 * 
 * @author tangweiwei
 *
 */
public class XStreamUtil {

	public static XStream xstream;

	static {
		xstream = new XStream(new DomDriver());
	//	xstream.alias("requestparam", RequestParam.class);
		//xstream.registerConverter(new DateConverter());
		// xstream.alias("element", WhereItem.class);
		// xstream.alias("operation", Function.class);
		// xstream.alias("order", OrderItem.class);
		xstream.autodetectAnnotations(false);
	}

	/**
	 * 对象编组为xml文件
	 * 
	 * @param obj
	 * @param xmlFile
	 * @throws IOException
	 */
	public static void objectToXml(Object obj, File xmlFile) throws IOException {
		xstream.toXML(obj, new FileWriter(xmlFile));
	}

	/**
	 * xml文件解组为对�?
	 * 
	 * @param xmlFile
	 * @return
	 */

	public static Object xmlToObject(File xmlFile) throws FileNotFoundException {
		return xstream.fromXML(new FileReader(xmlFile));
	}

	/**
	 * xml文件流解组为对象,默认使用UTF-8进行解组，解组不成功，会尝试使用GBK进行解组，还是不成功则返回null
	 * 
	 * @param stream
	 * @return
	 */
	public static Object xmlToObject(byte[] stream) {
		try {
			return xstream.fromXML(new ByteArrayInputStream(stream));
		} catch (Exception e) {
			try {
				stream = new String(stream, "GBK").getBytes("UTF-8");
				return xstream.fromXML(new ByteArrayInputStream(stream));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return null;
	}
}
